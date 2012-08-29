/*     */ package com.jidesoft.plaf.basic;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.swing.FontUtils;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import com.jidesoft.swing.StyleRange;
/*     */ import com.jidesoft.swing.StyledLabel;
/*     */ import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
/*     */ import java.awt.Color;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Stroke;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.SwingConstants;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicLabelUI;
/*     */ import javax.swing.text.View;
/*     */ 
/*     */ public class BasicStyledLabelUI extends BasicLabelUI
/*     */   implements SwingConstants
/*     */ {
/*     */   public static Comparator<StyleRange> _comparator;
/*  29 */   protected static BasicStyledLabelUI styledLabelUI = new BasicStyledLabelUI();
/*     */ 
/*  50 */   private final List<StyledText> _styledTexts = new ArrayList();
/*     */ 
/*     */   public static ComponentUI createUI(JComponent c)
/*     */   {
/*  33 */     return styledLabelUI;
/*     */   }
/*     */ 
/*     */   public void propertyChange(PropertyChangeEvent e)
/*     */   {
/*  54 */     super.propertyChange(e);
/*  55 */     if ("styleRange".equals(e.getPropertyName())) {
/*  56 */       synchronized (this._styledTexts) {
/*  57 */         this._styledTexts.clear();
/*     */       }
/*  59 */       if ((e.getSource() instanceof StyledLabel)) {
/*  60 */         ((StyledLabel)e.getSource()).revalidate();
/*  61 */         ((StyledLabel)e.getSource()).repaint();
/*     */       }
/*     */     }
/*  64 */     else if (("ignoreColorSettings".equals(e.getPropertyName())) && 
/*  65 */       ((e.getSource() instanceof StyledLabel))) {
/*  66 */       ((StyledLabel)e.getSource()).repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void paintEnabledText(JLabel l, Graphics g, String s, int textX, int textY)
/*     */   {
/*  73 */     View v = l != null ? (View)l.getClientProperty("html") : null;
/*  74 */     if (v != null) {
/*  75 */       super.paintEnabledText(l, g, s, textX, textY);
/*     */     }
/*     */     else
/*  78 */       paintStyledText((StyledLabel)l, g, textX, textY);
/*     */   }
/*     */ 
/*     */   protected void paintDisabledText(JLabel l, Graphics g, String s, int textX, int textY)
/*     */   {
/*  84 */     View v = l != null ? (View)l.getClientProperty("html") : null;
/*  85 */     if (v != null) {
/*  86 */       super.paintDisabledText(l, g, s, textX, textY);
/*     */     }
/*     */     else
/*  89 */       paintStyledText((StyledLabel)l, g, textX, textY);
/*     */   }
/*     */ 
/*     */   protected void buildStyledText(StyledLabel label)
/*     */   {
/*  94 */     synchronized (this._styledTexts) {
/*  95 */       this._styledTexts.clear();
/*  96 */       StyleRange[] styleRanges = label.getStyleRanges();
/*  97 */       if (_comparator == null) {
/*  98 */         _comparator = new Comparator() {
/*     */           public int compare(StyleRange r1, StyleRange r2) {
/* 100 */             if (r1.getStart() < r2.getStart()) {
/* 101 */               return -1;
/*     */             }
/* 103 */             if (r1.getStart() > r2.getStart()) {
/* 104 */               return 1;
/*     */             }
/*     */ 
/* 107 */             return 0;
/*     */           }
/*     */         };
/*     */       }
/* 112 */       Arrays.sort(styleRanges, _comparator);
/*     */ 
/* 114 */       String s = label.getText();
/* 115 */       if ((s != null) && (s.length() > 0)) {
/* 116 */         int index = 0;
/* 117 */         for (StyleRange styleRange : styleRanges) {
/* 118 */           if (styleRange.getStart() > index) {
/* 119 */             this._styledTexts.add(new StyledText(s.substring(index, styleRange.getStart())));
/* 120 */             index = styleRange.getStart();
/*     */           }
/*     */ 
/* 123 */           if (styleRange.getStart() == index) {
/* 124 */             if (styleRange.getLength() == -1) {
/* 125 */               this._styledTexts.add(new StyledText(s.substring(index), styleRange));
/* 126 */               index = s.length();
/*     */             }
/*     */             else {
/* 129 */               this._styledTexts.add(new StyledText(s.substring(index, Math.min(index + styleRange.getLength(), s.length())), styleRange));
/* 130 */               index += styleRange.getLength();
/*     */             }
/*     */           }
/* 133 */           else if (styleRange.getStart() >= index) {
/*     */               continue;
/*     */             }
/*     */         }
/* 137 */         if (index < s.length())
/* 138 */           this._styledTexts.add(new StyledText(s.substring(index, s.length())));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected String layoutCL(JLabel label, FontMetrics fontMetrics, String text, Icon icon, Rectangle viewR, Rectangle iconR, Rectangle textR)
/*     */   {
/* 146 */     Dimension size = getPreferredSize((StyledLabel)label);
/* 147 */     textR.width = size.width;
/* 148 */     textR.height = size.height;
/*     */ 
/* 150 */     return layoutCompoundLabel(label, fontMetrics, text, icon, label.getVerticalAlignment(), label.getHorizontalAlignment(), label.getVerticalTextPosition(), label.getHorizontalTextPosition(), viewR, iconR, textR, label.getIconTextGap());
/*     */   }
/*     */ 
/*     */   protected Dimension getPreferredSize(StyledLabel label)
/*     */   {
/* 166 */     buildStyledText(label);
/*     */ 
/* 168 */     int width = 0;
/* 169 */     Font font = getFont(label);
/* 170 */     FontMetrics fm = label.getFontMetrics(font);
/*     */ 
/* 172 */     int lineHeight = 0;
/* 173 */     int defaultFontSize = font.getSize();
/* 174 */     synchronized (this._styledTexts) {
/* 175 */       StyledText[] texts = (StyledText[])this._styledTexts.toArray(new StyledText[this._styledTexts.size()]);
/* 176 */       for (int i = texts.length - 1; i >= 0; i--) {
/* 177 */         StyledText styledText = texts[i];
/* 178 */         StyleRange style = styledText.styleRange;
/* 179 */         int size = (style != null) && ((style.isSuperscript()) || (style.isSubscript())) ? Math.round(defaultFontSize / style.getFontShrinkRatio()) : defaultFontSize;
/*     */ 
/* 181 */         font = getFont(label);
/* 182 */         if ((style != null) && (((style.getFontStyle() != -1) && (font.getStyle() != style.getFontStyle())) || (font.getSize() != size))) {
/* 183 */           font = FontUtils.getCachedDerivedFont(font, style.getFontStyle() == -1 ? font.getStyle() : style.getFontStyle(), size);
/* 184 */           FontMetrics fm2 = label.getFontMetrics(font);
/* 185 */           width += fm2.stringWidth(styledText.text);
/*     */         }
/*     */         else
/*     */         {
/* 189 */           width += fm.stringWidth(styledText.text);
/*     */         }
/*     */ 
/* 192 */         if (style != null) {
/* 193 */           if ((style.isUnderlined()) && (lineHeight < 2)) {
/* 194 */             lineHeight = 2;
/*     */           }
/* 196 */           if ((style.isDotted()) && (lineHeight < 3)) {
/* 197 */             lineHeight = 3;
/*     */           }
/* 199 */           if ((style.isWaved()) && (lineHeight < 4)) {
/* 200 */             lineHeight = 4;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 206 */     int fontHeight = fm.getHeight();
/* 207 */     return new Dimension(width, fontHeight + lineHeight);
/*     */   }
/*     */ 
/*     */   protected Font getFont(StyledLabel label)
/*     */   {
/* 217 */     Font font = label.getFont();
/* 218 */     if (font == null) {
/* 219 */       font = UIDefaultsLookup.getFont("Label.font");
/*     */     }
/* 221 */     return font;
/*     */   }
/*     */ 
/*     */   protected void paintStyledText(StyledLabel label, Graphics g, int textX, int textY) {
/* 225 */     int x = textX < label.getInsets().left ? label.getInsets().left : textX;
/*     */ 
/* 227 */     int mnemonicIndex = label.getDisplayedMnemonicIndex();
/* 228 */     if (((UIManager.getLookAndFeel() instanceof WindowsLookAndFeel)) && (WindowsLookAndFeel.isMnemonicHidden()))
/*     */     {
/* 230 */       mnemonicIndex = -1;
/*     */     }
/*     */ 
/* 233 */     buildStyledText(label);
/*     */ 
/* 235 */     Color oldColor = g.getColor();
/*     */ 
/* 237 */     int charDisplayed = 0;
/*     */ 
/* 239 */     int mneIndex = 0;
/* 240 */     Font font = getFont(label);
/* 241 */     FontMetrics fm = label.getFontMetrics(font);
/*     */ 
/* 243 */     FontMetrics nextFm2 = null;
/* 244 */     int defaultFontSize = font.getSize();
/*     */ 
/* 246 */     synchronized (this._styledTexts)
/*     */     {
/* 248 */       for (int i = 0; i < this._styledTexts.size(); i++) {
/* 249 */         StyledText styledText = (StyledText)this._styledTexts.get(i);
/* 250 */         StyleRange style = styledText.styleRange;
/*     */         boolean displayMnemonic;
/* 252 */         if ((mnemonicIndex >= 0) && (styledText.text.length() > mnemonicIndex - charDisplayed)) {
/* 253 */           boolean displayMnemonic = true;
/* 254 */           mneIndex = mnemonicIndex - charDisplayed;
/*     */         }
/*     */         else {
/* 257 */           displayMnemonic = false;
/*     */         }
/* 259 */         charDisplayed += styledText.text.length();
/*     */ 
/* 261 */         int y = textY;
/*     */         FontMetrics fm2;
/*     */         FontMetrics fm2;
/* 263 */         if (nextFm2 == null) {
/* 264 */           int size = (style != null) && ((style.isSuperscript()) || (style.isSubscript())) ? Math.round(defaultFontSize / style.getFontShrinkRatio()) : defaultFontSize;
/*     */ 
/* 267 */           font = getFont(label);
/*     */           FontMetrics fm2;
/* 268 */           if ((style != null) && (((style.getFontStyle() != -1) && (font.getStyle() != style.getFontStyle())) || (font.getSize() != size))) {
/* 269 */             font = FontUtils.getCachedDerivedFont(font, style.getFontStyle() == -1 ? font.getStyle() : style.getFontStyle(), size);
/* 270 */             fm2 = label.getFontMetrics(font);
/*     */           }
/*     */           else {
/* 273 */             fm2 = fm;
/*     */           }
/*     */         }
/*     */         else {
/* 277 */           fm2 = nextFm2;
/*     */         }
/*     */ 
/* 280 */         g.setFont(font);
/*     */ 
/* 282 */         String s = styledText.text;
/*     */ 
/* 284 */         int strWidth = fm2.stringWidth(s);
/*     */ 
/* 286 */         boolean stop = false;
/* 287 */         int widthLeft = label.getWidth() - x;
/* 288 */         if (widthLeft < strWidth)
/*     */         {
/* 290 */           s = SwingUtilities.layoutCompoundLabel(label, fm2, s, null, label.getVerticalAlignment(), label.getHorizontalAlignment(), label.getVerticalTextPosition(), label.getHorizontalTextPosition(), new Rectangle(x, y, widthLeft, label.getHeight()), new Rectangle(), new Rectangle(), 0);
/*     */ 
/* 292 */           strWidth = fm2.stringWidth(s);
/* 293 */           stop = true;
/*     */         }
/* 295 */         else if (i < this._styledTexts.size() - 1) {
/* 296 */           StyledText nextStyledText = (StyledText)this._styledTexts.get(i + 1);
/* 297 */           String nextText = nextStyledText.text;
/* 298 */           StyleRange nextStyle = nextStyledText.styleRange;
/* 299 */           int size = (nextStyle != null) && ((nextStyle.isSuperscript()) || (nextStyle.isSubscript())) ? Math.round(defaultFontSize / nextStyle.getFontShrinkRatio()) : defaultFontSize;
/*     */ 
/* 302 */           font = getFont(label);
/* 303 */           if ((nextStyle != null) && (((nextStyle.getFontStyle() != -1) && (font.getStyle() != nextStyle.getFontStyle())) || (font.getSize() != size))) {
/* 304 */             font = FontUtils.getCachedDerivedFont(font, nextStyle.getFontStyle() == -1 ? font.getStyle() : nextStyle.getFontStyle(), size);
/* 305 */             nextFm2 = label.getFontMetrics(font);
/*     */           }
/*     */           else {
/* 308 */             nextFm2 = fm;
/*     */           }
/* 310 */           if (nextFm2.stringWidth(nextText) > widthLeft - strWidth) {
/* 311 */             String nextS = SwingUtilities.layoutCompoundLabel(label, nextFm2, nextText, null, label.getVerticalAlignment(), label.getHorizontalAlignment(), label.getVerticalTextPosition(), label.getHorizontalTextPosition(), new Rectangle(x + strWidth, y, widthLeft - strWidth, label.getHeight()), new Rectangle(), new Rectangle(), 0);
/*     */ 
/* 313 */             if (nextFm2.stringWidth(nextS) > widthLeft - strWidth) {
/* 314 */               s = SwingUtilities.layoutCompoundLabel(label, fm2, s, null, label.getVerticalAlignment(), label.getHorizontalAlignment(), label.getVerticalTextPosition(), label.getHorizontalTextPosition(), new Rectangle(x, y, strWidth - 1, label.getHeight()), new Rectangle(), new Rectangle(), 0);
/*     */ 
/* 316 */               strWidth = fm2.stringWidth(s);
/* 317 */               stop = true;
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 322 */         if ((style != null) && (style.isSuperscript())) {
/* 323 */           y -= fm.getHeight() - fm2.getHeight();
/*     */         }
/*     */ 
/* 326 */         if ((style != null) && (style.getBackgroundColor() != null)) {
/* 327 */           g.setColor(style.getBackgroundColor());
/* 328 */           g.fillRect(x, y - fm2.getHeight(), strWidth, fm2.getHeight() + 4);
/*     */         }
/*     */ 
/* 331 */         Color textColor = (style != null) && (!label.isIgnoreColorSettings()) && (style.getFontColor() != null) ? style.getFontColor() : label.getForeground();
/* 332 */         if (!label.isEnabled()) {
/* 333 */           textColor = UIDefaultsLookup.getColor("Label.disabledForeground");
/*     */         }
/* 335 */         g.setColor(textColor);
/*     */ 
/* 337 */         if (displayMnemonic) {
/* 338 */           JideSwingUtilities.drawStringUnderlineCharAt(label, g, s, mneIndex, x, y);
/*     */         }
/*     */         else {
/* 341 */           JideSwingUtilities.drawString(label, g, s, x, y);
/*     */         }
/*     */ 
/* 344 */         if (style != null) {
/* 345 */           Stroke oldStroke = ((Graphics2D)g).getStroke();
/* 346 */           if (style.getLineStroke() != null) {
/* 347 */             ((Graphics2D)g).setStroke(style.getLineStroke());
/*     */           }
/*     */ 
/* 350 */           if ((!label.isIgnoreColorSettings()) && (style.getLineColor() != null)) {
/* 351 */             g.setColor(style.getLineColor());
/*     */           }
/*     */ 
/* 354 */           if (style.isStrikethrough()) {
/* 355 */             int lineY = y + (fm2.getDescent() - fm2.getAscent()) / 2;
/* 356 */             g.drawLine(x, lineY, x + strWidth - 1, lineY);
/*     */           }
/* 358 */           if (style.isDoublestrikethrough()) {
/* 359 */             int lineY = y + (fm2.getDescent() - fm2.getAscent()) / 2;
/* 360 */             g.drawLine(x, lineY - 1, x + strWidth - 1, lineY - 1);
/* 361 */             g.drawLine(x, lineY + 1, x + strWidth - 1, lineY + 1);
/*     */           }
/* 363 */           if (style.isUnderlined()) {
/* 364 */             int lineY = y + 1;
/* 365 */             g.drawLine(x, lineY, x + strWidth - 1, lineY);
/*     */           }
/* 367 */           if (style.isDotted()) {
/* 368 */             int dotY = y + 1;
/* 369 */             for (int dotX = x; dotX < x + strWidth; dotX += 4) {
/* 370 */               g.drawRect(dotX, dotY, 1, 1);
/*     */             }
/*     */           }
/* 373 */           if (style.isWaved()) {
/* 374 */             int waveY = y + 1;
/* 375 */             for (int waveX = x; waveX < x + strWidth; waveX += 4) {
/* 376 */               if (waveX + 2 <= x + strWidth - 1)
/* 377 */                 g.drawLine(waveX, waveY + 2, waveX + 2, waveY);
/* 378 */               if (waveX + 4 <= x + strWidth - 1)
/* 379 */                 g.drawLine(waveX + 3, waveY + 1, waveX + 4, waveY + 2);
/*     */             }
/*     */           }
/* 382 */           if (style.getLineStroke() != null) {
/* 383 */             ((Graphics2D)g).setStroke(oldStroke);
/*     */           }
/*     */         }
/*     */ 
/* 387 */         if (stop)
/*     */         {
/*     */           break;
/*     */         }
/* 391 */         x += strWidth;
/*     */       }
/*     */     }
/*     */ 
/* 395 */     g.setColor(oldColor);
/*     */   }
/*     */ 
/*     */   public static String layoutCompoundLabel(JComponent c, FontMetrics fm, String text, Icon icon, int verticalAlignment, int horizontalAlignment, int verticalTextPosition, int horizontalTextPosition, Rectangle viewR, Rectangle iconR, Rectangle textR, int textIconGap)
/*     */   {
/* 430 */     boolean orientationIsLeftToRight = true;
/* 431 */     int hAlign = horizontalAlignment;
/* 432 */     int hTextPos = horizontalTextPosition;
/*     */ 
/* 434 */     if ((c != null) && 
/* 435 */       (!c.getComponentOrientation().isLeftToRight())) {
/* 436 */       orientationIsLeftToRight = false;
/*     */     }
/*     */ 
/* 442 */     switch (horizontalAlignment) {
/*     */     case 10:
/* 444 */       hAlign = orientationIsLeftToRight ? 2 : 4;
/* 445 */       break;
/*     */     case 11:
/* 447 */       hAlign = orientationIsLeftToRight ? 4 : 2;
/*     */     }
/*     */ 
/* 453 */     switch (horizontalTextPosition) {
/*     */     case 10:
/* 455 */       hTextPos = orientationIsLeftToRight ? 2 : 4;
/* 456 */       break;
/*     */     case 11:
/* 458 */       hTextPos = orientationIsLeftToRight ? 4 : 2;
/*     */     }
/*     */ 
/* 462 */     return layoutCompoundLabelImpl(c, fm, text, icon, verticalAlignment, hAlign, verticalTextPosition, hTextPos, viewR, iconR, textR, textIconGap);
/*     */   }
/*     */ 
/*     */   public static String layoutCompoundLabel(FontMetrics fm, String text, Icon icon, int verticalAlignment, int horizontalAlignment, int verticalTextPosition, int horizontalTextPosition, Rectangle viewR, Rectangle iconR, Rectangle textR, int textIconGap)
/*     */   {
/* 508 */     return layoutCompoundLabelImpl(null, fm, text, icon, verticalAlignment, horizontalAlignment, verticalTextPosition, horizontalTextPosition, viewR, iconR, textR, textIconGap);
/*     */   }
/*     */ 
/*     */   private static String layoutCompoundLabelImpl(JComponent c, FontMetrics fm, String text, Icon icon, int verticalAlignment, int horizontalAlignment, int verticalTextPosition, int horizontalTextPosition, Rectangle viewR, Rectangle iconR, Rectangle textR, int textIconGap)
/*     */   {
/* 554 */     if (icon != null) {
/* 555 */       iconR.width = icon.getIconWidth();
/* 556 */       iconR.height = icon.getIconHeight();
/*     */     }
/*     */     else {
/* 559 */       iconR.width = (iconR.height = 0);
/*     */     }
/*     */ 
/* 567 */     boolean textIsEmpty = (text == null) || (text.equals(""));
/* 568 */     int lsb = 0;
/*     */     int gap;
/*     */     int gap;
/* 575 */     if (textIsEmpty) {
/* 576 */       textR.width = (textR.height = 0);
/* 577 */       text = "";
/* 578 */       gap = 0;
/*     */     }
/*     */     else
/*     */     {
/* 582 */       gap = icon == null ? 0 : textIconGap;
/*     */       int availTextWidth;
/*     */       int availTextWidth;
/* 584 */       if (horizontalTextPosition == 0) {
/* 585 */         availTextWidth = viewR.width;
/*     */       }
/*     */       else {
/* 588 */         availTextWidth = viewR.width - (iconR.width + gap);
/*     */       }
/* 590 */       View v = c != null ? (View)c.getClientProperty("html") : null;
/* 591 */       if (v != null) {
/* 592 */         textR.width = Math.min(availTextWidth, (int)v.getPreferredSpan(0));
/* 593 */         textR.height = (int)v.getPreferredSpan(1);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 625 */     if (verticalTextPosition == 1) {
/* 626 */       if (horizontalTextPosition != 0) {
/* 627 */         textR.y = 0;
/*     */       }
/*     */       else {
/* 630 */         textR.y = (-(textR.height + gap));
/*     */       }
/*     */     }
/* 633 */     else if (verticalTextPosition == 0) {
/* 634 */       textR.y = (iconR.height / 2 - textR.height / 2);
/*     */     }
/* 637 */     else if (horizontalTextPosition != 0) {
/* 638 */       textR.y = (iconR.height - textR.height);
/*     */     }
/*     */     else {
/* 641 */       textR.y = (iconR.height + gap);
/*     */     }
/*     */ 
/* 645 */     if (horizontalTextPosition == 2) {
/* 646 */       textR.x = (-(textR.width + gap));
/*     */     }
/* 648 */     else if (horizontalTextPosition == 0) {
/* 649 */       textR.x = (iconR.width / 2 - textR.width / 2);
/*     */     }
/*     */     else {
/* 652 */       textR.x = (iconR.width + gap);
/*     */     }
/*     */ 
/* 662 */     int labelR_x = Math.min(iconR.x, textR.x);
/* 663 */     int labelR_width = Math.max(iconR.x + iconR.width, textR.x + textR.width) - labelR_x;
/*     */ 
/* 665 */     int labelR_y = Math.min(iconR.y, textR.y);
/* 666 */     int labelR_height = Math.max(iconR.y + iconR.height, textR.y + textR.height) - labelR_y;
/*     */     int dy;
/*     */     int dy;
/* 671 */     if (verticalAlignment == 1) {
/* 672 */       dy = viewR.y - labelR_y;
/*     */     }
/*     */     else
/*     */     {
/*     */       int dy;
/* 674 */       if (verticalAlignment == 0) {
/* 675 */         dy = viewR.y + viewR.height / 2 - (labelR_y + labelR_height / 2);
/*     */       }
/*     */       else
/* 678 */         dy = viewR.y + viewR.height - (labelR_y + labelR_height);
/*     */     }
/*     */     int dx;
/*     */     int dx;
/* 681 */     if (horizontalAlignment == 2) {
/* 682 */       dx = viewR.x - labelR_x;
/*     */     }
/*     */     else
/*     */     {
/*     */       int dx;
/* 684 */       if (horizontalAlignment == 4) {
/* 685 */         dx = viewR.x + viewR.width - (labelR_x + labelR_width);
/*     */       }
/*     */       else {
/* 688 */         dx = viewR.x + viewR.width / 2 - (labelR_x + labelR_width / 2);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 695 */     textR.x += dx;
/* 696 */     textR.y += dy;
/*     */ 
/* 698 */     iconR.x += dx;
/* 699 */     iconR.y += dy;
/*     */ 
/* 701 */     if (lsb < 0)
/*     */     {
/* 704 */       textR.x -= lsb;
/*     */     }
/*     */ 
/* 707 */     return text;
/*     */   }
/*     */ 
/*     */   class StyledText
/*     */   {
/*     */     StyleRange styleRange;
/*     */     String text;
/*     */ 
/*     */     public StyledText(String text)
/*     */     {
/*  41 */       this.text = text;
/*     */     }
/*     */ 
/*     */     public StyledText(String text, StyleRange styleRange) {
/*  45 */       this.text = text;
/*  46 */       this.styleRange = styleRange;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.BasicStyledLabelUI
 * JD-Core Version:    0.6.0
 */
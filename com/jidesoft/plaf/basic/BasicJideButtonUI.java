/*     */ package com.jidesoft.plaf.basic;
/*     */ 
/*     */ import com.jidesoft.icons.IconsFactory;
/*     */ import com.jidesoft.plaf.JideButtonUI;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.swing.ComponentStateSupport;
/*     */ import com.jidesoft.swing.JideButton;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import com.jidesoft.utils.SecurityUtils;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.basic.BasicButtonListener;
/*     */ import javax.swing.plaf.basic.BasicGraphicsUtils;
/*     */ import javax.swing.plaf.basic.BasicHTML;
/*     */ import javax.swing.text.View;
/*     */ 
/*     */ public class BasicJideButtonUI extends JideButtonUI
/*     */ {
/*     */   protected int defaultTextIconGap;
/*  34 */   private int shiftOffset = 0;
/*     */   protected int defaultTextShiftOffset;
/*  38 */   private boolean defaults_initialized = false;
/*     */   private static final String propertyPrefix = "JideButton.";
/*     */   protected ThemePainter _painter;
/*     */   protected Color _shadowColor;
/*     */   protected Color _darkShadowColor;
/*     */   protected Color _highlight;
/*     */   protected Color _lightHighlightColor;
/*     */   protected Color _focusColor;
/*  51 */   protected boolean _isFloatingIcon = false;
/*     */ 
/* 235 */   protected static Rectangle viewRect = new Rectangle();
/* 236 */   protected static Rectangle textRect = new Rectangle();
/* 237 */   protected static Rectangle iconRect = new Rectangle();
/*     */ 
/*     */   public static ComponentUI createUI(JComponent c)
/*     */   {
/*  59 */     return new BasicJideButtonUI();
/*     */   }
/*     */ 
/*     */   protected String getPropertyPrefix() {
/*  63 */     return "JideButton.";
/*     */   }
/*     */ 
/*     */   public void installUI(JComponent c)
/*     */   {
/*  73 */     installDefaults((AbstractButton)c);
/*  74 */     installListeners((AbstractButton)c);
/*  75 */     installKeyboardActions((AbstractButton)c);
/*  76 */     BasicHTML.updateRenderer(c, ((AbstractButton)c).getText());
/*     */   }
/*     */ 
/*     */   protected void installDefaults(AbstractButton b)
/*     */   {
/*  81 */     this._painter = ((ThemePainter)UIDefaultsLookup.get("Theme.painter"));
/*     */ 
/*  83 */     String pp = getPropertyPrefix();
/*  84 */     if (!this.defaults_initialized) {
/*  85 */       this.defaultTextIconGap = UIDefaultsLookup.getInt(pp + "textIconGap");
/*  86 */       this.defaultTextShiftOffset = UIDefaultsLookup.getInt(pp + "textShiftOffset");
/*     */ 
/*  88 */       this._focusColor = UIDefaultsLookup.getColor("Button.focus");
/*     */ 
/*  96 */       this._shadowColor = UIDefaultsLookup.getColor("JideButton.shadow");
/*  97 */       this._darkShadowColor = UIDefaultsLookup.getColor("JideButton.darkShadow");
/*  98 */       this._highlight = UIDefaultsLookup.getColor("JideButton.highlight");
/*  99 */       this._lightHighlightColor = UIDefaultsLookup.getColor("JideButton.light");
/*     */ 
/* 101 */       this.defaults_initialized = true;
/*     */     }
/*     */ 
/* 112 */     updateMargin(b);
/*     */ 
/* 140 */     LookAndFeel.installColorsAndFont(b, pp + "background", pp + "foreground", pp + "font");
/* 141 */     LookAndFeel.installBorder(b, pp + "border");
/*     */ 
/* 143 */     this._isFloatingIcon = UIDefaultsLookup.getBoolean("Icon.floating");
/*     */   }
/*     */ 
/*     */   protected void installListeners(AbstractButton b) {
/* 147 */     BasicButtonListener listener = createButtonListener(b);
/* 148 */     if (listener != null)
/*     */     {
/* 151 */       b.putClientProperty(this, listener);
/*     */ 
/* 153 */       b.addMouseListener(listener);
/* 154 */       b.addMouseMotionListener(listener);
/* 155 */       b.addFocusListener(listener);
/* 156 */       b.addPropertyChangeListener(listener);
/* 157 */       b.addChangeListener(listener);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void installKeyboardActions(AbstractButton b) {
/* 162 */     BasicButtonListener listener = (BasicButtonListener)b.getClientProperty(this);
/* 163 */     if (listener != null)
/* 164 */       listener.installKeyboardActions(b);
/*     */   }
/*     */ 
/*     */   public void uninstallUI(JComponent c)
/*     */   {
/* 175 */     uninstallKeyboardActions((AbstractButton)c);
/* 176 */     uninstallListeners((AbstractButton)c);
/* 177 */     uninstallDefaults((AbstractButton)c);
/* 178 */     BasicHTML.updateRenderer(c, "");
/*     */   }
/*     */ 
/*     */   protected void uninstallKeyboardActions(AbstractButton b) {
/* 182 */     BasicButtonListener listener = (BasicButtonListener)b.getClientProperty(this);
/* 183 */     if (listener != null)
/* 184 */       listener.uninstallKeyboardActions(b);
/*     */   }
/*     */ 
/*     */   protected void uninstallListeners(AbstractButton b)
/*     */   {
/* 189 */     BasicButtonListener listener = (BasicButtonListener)b.getClientProperty(this);
/* 190 */     b.putClientProperty(this, null);
/* 191 */     if (listener != null) {
/* 192 */       b.removeMouseListener(listener);
/* 193 */       b.removeMouseMotionListener(listener);
/* 194 */       b.removeFocusListener(listener);
/* 195 */       b.removeChangeListener(listener);
/* 196 */       b.removePropertyChangeListener(listener);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void uninstallDefaults(AbstractButton b)
/*     */   {
/* 202 */     this._painter = null;
/* 203 */     this._focusColor = null;
/*     */ 
/* 206 */     this._shadowColor = null;
/* 207 */     this._highlight = null;
/* 208 */     this._lightHighlightColor = null;
/* 209 */     this._darkShadowColor = null;
/* 210 */     this.defaults_initialized = false;
/*     */   }
/*     */ 
/*     */   protected BasicButtonListener createButtonListener(AbstractButton b)
/*     */   {
/* 218 */     return new BasicJideButtonListener(b);
/*     */   }
/*     */ 
/*     */   public int getDefaultTextIconGap(AbstractButton b)
/*     */   {
/* 223 */     return this.defaultTextIconGap;
/*     */   }
/*     */ 
/*     */   protected Color getFocusColor() {
/* 227 */     return this._focusColor;
/*     */   }
/*     */ 
/*     */   public void paint(Graphics g, JComponent c)
/*     */   {
/* 245 */     AbstractButton b = (AbstractButton)c;
/* 246 */     boolean isHorizontal = true;
/*     */ 
/* 248 */     if (JideSwingUtilities.getOrientationOf(c) == 1) {
/* 249 */       isHorizontal = false;
/*     */     }
/*     */ 
/* 253 */     FontMetrics fm = g.getFontMetrics();
/*     */ 
/* 255 */     Insets i = c.getInsets();
/*     */ 
/* 257 */     viewRect.x = i.left;
/* 258 */     viewRect.y = i.top;
/* 259 */     viewRect.width = (b.getWidth() - (i.right + viewRect.x));
/* 260 */     viewRect.height = (b.getHeight() - (i.bottom + viewRect.y));
/*     */ 
/* 262 */     textRect.x = (textRect.y = textRect.width = textRect.height = 0);
/* 263 */     iconRect.x = (iconRect.y = iconRect.width = iconRect.height = 0);
/*     */ 
/* 265 */     paintBackground(g, b);
/*     */ 
/* 267 */     Font f = c.getFont();
/* 268 */     g.setFont(f);
/*     */ 
/* 271 */     String text = JideSwingUtilities.layoutCompoundLabel(c, fm, b.getText(), b.getIcon(), isHorizontal, b.getVerticalAlignment(), b.getHorizontalAlignment(), b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewRect, iconRect, textRect, b.getText() == null ? 0 : b.getIconTextGap());
/*     */ 
/* 280 */     clearTextShiftOffset();
/*     */ 
/* 282 */     paintIcon(b, g);
/*     */ 
/* 284 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/* 285 */     if ((highContrast.booleanValue()) && (JideSwingUtilities.getButtonState(b) == 1)) {
/* 286 */       textRect.x += 1;
/* 287 */       textRect.y += 1;
/*     */     }
/* 289 */     if ((text != null) && (!text.equals(""))) {
/* 290 */       View v = (View)c.getClientProperty("html");
/* 291 */       if (v != null) {
/* 292 */         v.paint(g, textRect);
/*     */       }
/*     */       else
/* 295 */         paintText(g, b, textRect, text);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void paintIcon(AbstractButton b, Graphics g)
/*     */   {
/* 302 */     if (b.getIcon() != null) {
/* 303 */       Icon icon = getIcon(b);
/*     */ 
/* 305 */       ButtonModel model = b.getModel();
/* 306 */       if (icon != null)
/* 307 */         if ((isFloatingIcon()) && (model.isEnabled())) {
/* 308 */           if ((model.isRollover()) && (!model.isPressed()) && (!model.isSelected())) {
/* 309 */             if ((!"true".equals(SecurityUtils.getProperty("shadingtheme", "false"))) && ((b instanceof JideButton)) && (((JideButton)b).getButtonStyle() == 0)) {
/* 310 */               if ((icon instanceof ImageIcon)) {
/* 311 */                 ImageIcon shadow = IconsFactory.createGrayImage(((ImageIcon)icon).getImage());
/* 312 */                 shadow.paintIcon(b, g, iconRect.x + 1, iconRect.y + 1);
/*     */               }
/*     */               else {
/* 315 */                 ImageIcon shadow = IconsFactory.createGrayImage(b, icon);
/* 316 */                 shadow.paintIcon(b, g, iconRect.x + 1, iconRect.y + 1);
/*     */               }
/* 318 */               icon.paintIcon(b, g, iconRect.x - 1, iconRect.y - 1);
/*     */             }
/*     */             else {
/* 321 */               icon.paintIcon(b, g, iconRect.x, iconRect.y);
/*     */             }
/*     */           }
/*     */           else {
/* 325 */             icon.paintIcon(b, g, iconRect.x, iconRect.y);
/*     */           }
/*     */         }
/*     */         else
/* 329 */           icon.paintIcon(b, g, iconRect.x, iconRect.y);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Icon getIcon(AbstractButton b)
/*     */   {
/* 336 */     ButtonModel model = b.getModel();
/* 337 */     Icon icon = b.getIcon();
/* 338 */     Icon tmpIcon = null;
/* 339 */     if (!model.isEnabled()) {
/* 340 */       if (model.isSelected()) {
/* 341 */         tmpIcon = b.getDisabledSelectedIcon();
/*     */       }
/*     */       else {
/* 344 */         tmpIcon = b.getDisabledIcon();
/*     */       }
/*     */ 
/* 348 */       if (tmpIcon == null) {
/* 349 */         if ((icon instanceof ImageIcon)) {
/* 350 */           icon = IconsFactory.createGrayImage(((ImageIcon)icon).getImage());
/*     */         }
/*     */         else {
/* 353 */           icon = IconsFactory.createGrayImage(b, icon);
/*     */         }
/*     */       }
/*     */     }
/* 357 */     else if ((model.isPressed()) && (model.isArmed())) {
/* 358 */       tmpIcon = b.getPressedIcon();
/* 359 */       if (tmpIcon != null)
/*     */       {
/* 361 */         clearTextShiftOffset();
/*     */       }
/*     */     }
/* 364 */     else if ((b.isRolloverEnabled()) && (model.isRollover())) {
/* 365 */       if (model.isSelected()) {
/* 366 */         tmpIcon = b.getRolloverSelectedIcon();
/*     */       }
/*     */       else {
/* 369 */         tmpIcon = b.getRolloverIcon();
/*     */       }
/*     */     }
/* 372 */     else if (model.isSelected()) {
/* 373 */       tmpIcon = b.getSelectedIcon();
/*     */     }
/*     */ 
/* 376 */     if (tmpIcon != null) {
/* 377 */       icon = tmpIcon;
/*     */     }
/* 379 */     return icon;
/*     */   }
/*     */ 
/*     */   protected boolean isFloatingIcon() {
/* 383 */     return this._isFloatingIcon;
/*     */   }
/*     */ 
/*     */   protected void paintText(Graphics g, JComponent c, Rectangle textRect, String text)
/*     */   {
/* 396 */     AbstractButton b = (AbstractButton)c;
/* 397 */     boolean isHorizontal = true;
/* 398 */     if (JideSwingUtilities.getOrientationOf(c) == 1) {
/* 399 */       isHorizontal = false;
/*     */     }
/*     */ 
/* 402 */     ButtonModel model = b.getModel();
/* 403 */     FontMetrics fm = g.getFontMetrics();
/*     */ 
/* 406 */     int mnemonicIndex = b.getDisplayedMnemonicIndex();
/*     */ 
/* 408 */     if (!isHorizontal) {
/* 409 */       Graphics2D g2d = (Graphics2D)g.create();
/* 410 */       g2d.rotate(1.570796326794897D);
/* 411 */       g2d.translate(0, -c.getWidth() + 1);
/*     */ 
/* 414 */       if (model.isEnabled())
/*     */       {
/* 416 */         g2d.setColor(getForegroundOfState(b));
/*     */ 
/* 420 */         JideSwingUtilities.drawStringUnderlineCharAt(b, g2d, text, mnemonicIndex, textRect.y + getTextShiftOffset(), textRect.x + fm.getAscent() + getTextShiftOffset());
/*     */ 
/* 423 */         if (((b instanceof JideButton)) && (((JideButton)b).getButtonStyle() == 3) && ((((JideButton)b).isAlwaysShowHyperlink()) || (b.getModel().isRollover())))
/*     */         {
/* 425 */           g.drawLine(textRect.x, textRect.y, textRect.x, textRect.y + textRect.height);
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 431 */         Color color = UIDefaultsLookup.getColor("Button.disabledForeground");
/* 432 */         g2d.setColor(color == null ? b.getBackground().darker() : color);
/*     */ 
/* 436 */         JideSwingUtilities.drawStringUnderlineCharAt(b, g2d, text, mnemonicIndex, textRect.y, textRect.x + fm.getAscent());
/*     */       }
/*     */ 
/* 440 */       g2d.dispose();
/*     */     }
/*     */     else
/*     */     {
/* 444 */       Color old = g.getColor();
/* 445 */       if (model.isEnabled())
/*     */       {
/* 447 */         g.setColor(getForegroundOfState(b));
/*     */ 
/* 451 */         JideSwingUtilities.drawStringUnderlineCharAt(b, g, text, mnemonicIndex, textRect.x + getTextShiftOffset(), textRect.y + fm.getAscent() + getTextShiftOffset());
/*     */ 
/* 454 */         if (((b instanceof JideButton)) && (((JideButton)b).getButtonStyle() == 3) && ((((JideButton)b).isAlwaysShowHyperlink()) || (b.getModel().isRollover())))
/*     */         {
/* 456 */           g.drawLine(textRect.x, textRect.y + textRect.height - 2, textRect.x + textRect.width, textRect.y + textRect.height - 2);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 461 */         Color color = UIDefaultsLookup.getColor("Button.disabledForeground");
/* 462 */         g.setColor(color == null ? b.getBackground().darker() : color);
/*     */ 
/* 466 */         JideSwingUtilities.drawStringUnderlineCharAt(b, g, text, mnemonicIndex, textRect.x, textRect.y + fm.getAscent());
/*     */       }
/*     */ 
/* 469 */       g.setColor(old);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Color getForegroundOfState(AbstractButton b) {
/* 474 */     int state = JideSwingUtilities.getButtonState(b);
/* 475 */     Color foreground = null;
/* 476 */     if ((b instanceof ComponentStateSupport)) {
/* 477 */       foreground = ((ComponentStateSupport)b).getForegroundOfState(state);
/*     */     }
/* 479 */     if ((foreground == null) || ((foreground instanceof UIResource))) {
/* 480 */       foreground = b.getForeground();
/*     */     }
/* 482 */     return foreground;
/*     */   }
/*     */ 
/*     */   protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text)
/*     */   {
/* 496 */     paintText(g, b, textRect, text);
/*     */   }
/*     */ 
/*     */   protected void paintBackground(Graphics g, AbstractButton b) {
/* 500 */     boolean paintDefaultBorder = true;
/*     */ 
/* 502 */     Object o = b.getClientProperty("JideButton.paintDefaultBorder");
/* 503 */     if ((o instanceof Boolean)) {
/* 504 */       paintDefaultBorder = ((Boolean)o).booleanValue();
/*     */     }
/* 506 */     o = b.getClientProperty("JideButton.alwaysPaintBackground");
/*     */     boolean paintBackground;
/*     */     boolean paintBackground;
/* 507 */     if ((o instanceof Boolean)) {
/* 508 */       paintBackground = ((Boolean)o).booleanValue();
/*     */     }
/*     */     else {
/* 511 */       paintBackground = b.isOpaque();
/*     */     }
/*     */ 
/* 514 */     if (paintBackground) {
/* 515 */       g.setColor(b.getBackground());
/* 516 */       g.fillRect(0, 0, b.getWidth(), b.getHeight());
/*     */     }
/*     */ 
/* 519 */     if (b.isContentAreaFilled())
/* 520 */       if (((b instanceof JideButton)) && (((JideButton)b).getButtonStyle() == 0)) {
/* 521 */         Rectangle rect = new Rectangle(0, 0, b.getWidth(), b.getHeight());
/* 522 */         int state = JideSwingUtilities.getButtonState(b);
/* 523 */         if (state != 0) {
/* 524 */           getPainter().paintButtonBackground(b, g, rect, 0, state);
/*     */         }
/* 527 */         else if (paintBackground) {
/* 528 */           getPainter().paintButtonBackground(b, g, rect, JideSwingUtilities.getOrientationOf(b), state);
/* 529 */           if ("true".equals(SecurityUtils.getProperty("shadingtheme", "false"))) {
/* 530 */             JideSwingUtilities.fillGradient(g, rect, JideSwingUtilities.getOrientationOf(b));
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/* 535 */       else if (((b instanceof JideButton)) && (((JideButton)b).getButtonStyle() == 2)) {
/* 536 */         paintBackgroundInFlatStyle(g, b, paintBackground);
/*     */       }
/* 538 */       else if (((b instanceof JideButton)) && (((JideButton)b).getButtonStyle() == 1)) {
/* 539 */         paintBackgroundInToolboxStyle(g, b, paintBackground, paintDefaultBorder);
/*     */       }
/*     */   }
/*     */ 
/*     */   private void paintBackgroundInFlatStyle(Graphics g, AbstractButton b, boolean paintBackground)
/*     */   {
/* 545 */     Rectangle rect = new Rectangle(0, 0, b.getWidth(), b.getHeight());
/* 546 */     int state = JideSwingUtilities.getButtonState(b);
/* 547 */     switch (state) {
/*     */     case 3:
/* 549 */       JideSwingUtilities.paintBackground(g, rect, this._highlight, this._highlight);
/* 550 */       g.setColor(this._shadowColor);
/* 551 */       g.drawLine(rect.x, rect.y, rect.width - 1, rect.y);
/* 552 */       g.drawLine(rect.x, rect.y, rect.x, rect.height - 1);
/*     */ 
/* 554 */       g.setColor(this._lightHighlightColor);
/* 555 */       g.drawLine(rect.x, rect.height - 1, rect.width - 1, rect.height - 1);
/* 556 */       g.drawLine(rect.width - 1, rect.y, rect.width - 1, rect.height - 1);
/* 557 */       break;
/*     */     case 1:
/* 559 */       JideSwingUtilities.paintBackground(g, rect, this._highlight, this._highlight);
/* 560 */       g.setColor(this._shadowColor);
/* 561 */       g.drawLine(rect.x, rect.y, rect.width - 1, rect.y);
/* 562 */       g.drawLine(rect.x, rect.y, rect.x, rect.height - 1);
/*     */ 
/* 564 */       g.setColor(this._lightHighlightColor);
/* 565 */       g.drawLine(rect.x, rect.height - 1, rect.width - 1, rect.height - 1);
/* 566 */       g.drawLine(rect.width - 1, rect.y, rect.width - 1, rect.height - 1);
/* 567 */       break;
/*     */     case 2:
/* 569 */       JideSwingUtilities.paintBackground(g, rect, this._highlight, this._highlight);
/* 570 */       g.setColor(this._lightHighlightColor);
/* 571 */       g.drawLine(rect.x, rect.y, rect.width - 1, rect.y);
/* 572 */       g.drawLine(rect.x, rect.y, rect.x, rect.height - 1);
/*     */ 
/* 574 */       g.setColor(this._shadowColor);
/* 575 */       g.drawLine(rect.x, rect.height - 1, rect.width - 1, rect.height - 1);
/* 576 */       g.drawLine(rect.width - 1, rect.y, rect.width - 1, rect.height - 1);
/* 577 */       break;
/*     */     case 4:
/*     */     case 5:
/* 580 */       if (!paintBackground) break;
/* 581 */       JideSwingUtilities.paintBackground(g, rect, this._highlight, this._highlight);
/* 582 */       g.setColor(this._lightHighlightColor);
/* 583 */       g.drawLine(rect.x, rect.y, rect.width - 1, rect.y);
/* 584 */       g.drawLine(rect.x, rect.y, rect.x, rect.height - 1);
/*     */ 
/* 586 */       g.setColor(this._shadowColor);
/* 587 */       g.drawLine(rect.x, rect.height - 1, rect.width - 1, rect.height - 1);
/* 588 */       g.drawLine(rect.width - 1, rect.y, rect.width - 1, rect.height - 1); break;
/*     */     case 0:
/* 592 */       if (!paintBackground) break;
/* 593 */       getPainter().paintButtonBackground(b, g, rect, JideSwingUtilities.getOrientationOf(b), state);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void paintBackgroundInToolboxStyle(Graphics g, AbstractButton b, boolean paintBackground, boolean paintDefaultBorder)
/*     */   {
/* 600 */     Rectangle rect = new Rectangle(0, 0, b.getWidth(), b.getHeight());
/* 601 */     if (b.getModel().isPressed()) {
/* 602 */       getPainter().paintButtonBackground(b, g, rect, JideSwingUtilities.getOrientationOf(b), 1);
/* 603 */       if (paintDefaultBorder) {
/* 604 */         g.setColor(this._darkShadowColor);
/* 605 */         g.drawLine(0, 0, b.getWidth() - 2, 0);
/* 606 */         g.drawLine(0, 0, 0, b.getHeight() - 2);
/*     */ 
/* 608 */         g.setColor(this._shadowColor);
/* 609 */         g.drawLine(1, 1, b.getWidth() - 3, 1);
/* 610 */         g.drawLine(1, 1, 1, b.getHeight() - 3);
/*     */ 
/* 612 */         g.setColor(this._lightHighlightColor);
/* 613 */         g.drawLine(0, b.getHeight() - 1, b.getWidth() - 1, b.getHeight() - 1);
/* 614 */         g.drawLine(b.getWidth() - 1, 0, b.getWidth() - 1, b.getHeight() - 1);
/*     */       }
/*     */     }
/* 617 */     else if ((b.getModel().isSelected()) && (b.getModel().isRollover())) {
/* 618 */       getPainter().paintButtonBackground(b, g, rect, JideSwingUtilities.getOrientationOf(b), 1);
/* 619 */       if (paintDefaultBorder) {
/* 620 */         g.setColor(this._darkShadowColor);
/* 621 */         g.drawLine(0, 0, b.getWidth() - 2, 0);
/* 622 */         g.drawLine(0, 0, 0, b.getHeight() - 2);
/*     */ 
/* 624 */         g.setColor(this._shadowColor);
/* 625 */         g.drawLine(1, 1, b.getWidth() - 3, 1);
/* 626 */         g.drawLine(1, 1, 1, b.getHeight() - 3);
/*     */ 
/* 628 */         g.setColor(this._lightHighlightColor);
/* 629 */         g.drawLine(0, b.getHeight() - 1, b.getWidth() - 1, b.getHeight() - 1);
/* 630 */         g.drawLine(b.getWidth() - 1, 0, b.getWidth() - 1, b.getHeight() - 1);
/*     */       }
/*     */     }
/* 633 */     else if (b.getModel().isSelected()) {
/* 634 */       getPainter().paintButtonBackground(b, g, rect, JideSwingUtilities.getOrientationOf(b), 3);
/* 635 */       if (paintDefaultBorder) {
/* 636 */         g.setColor(this._darkShadowColor);
/* 637 */         g.drawLine(0, 0, b.getWidth() - 2, 0);
/* 638 */         g.drawLine(0, 0, 0, b.getHeight() - 2);
/*     */ 
/* 640 */         g.setColor(this._shadowColor);
/* 641 */         g.drawLine(1, 1, b.getWidth() - 3, 1);
/* 642 */         g.drawLine(1, 1, 1, b.getHeight() - 3);
/*     */ 
/* 644 */         g.setColor(this._lightHighlightColor);
/* 645 */         g.drawLine(0, b.getHeight() - 1, b.getWidth() - 1, b.getHeight() - 1);
/* 646 */         g.drawLine(b.getWidth() - 1, 0, b.getWidth() - 1, b.getHeight() - 1);
/*     */       }
/*     */     }
/* 649 */     else if ((b.getModel().isRollover()) || ((b.hasFocus()) && (b.isFocusPainted()))) {
/* 650 */       getPainter().paintButtonBackground(b, g, rect, JideSwingUtilities.getOrientationOf(b), 2);
/* 651 */       if (paintDefaultBorder) {
/* 652 */         g.setColor(this._lightHighlightColor);
/* 653 */         g.drawLine(0, 0, b.getWidth() - 1, 0);
/* 654 */         g.drawLine(0, 0, 0, b.getHeight() - 1);
/*     */ 
/* 656 */         g.setColor(this._shadowColor);
/* 657 */         g.drawLine(1, b.getHeight() - 2, b.getWidth() - 2, b.getHeight() - 2);
/* 658 */         g.drawLine(b.getWidth() - 2, 1, b.getWidth() - 2, b.getHeight() - 2);
/*     */ 
/* 660 */         g.setColor(this._darkShadowColor);
/* 661 */         g.drawLine(0, b.getHeight() - 1, b.getWidth() - 1, b.getHeight() - 1);
/* 662 */         g.drawLine(b.getWidth() - 1, 0, b.getWidth() - 1, b.getHeight() - 1);
/*     */       }
/*     */ 
/*     */     }
/* 666 */     else if (paintBackground) {
/* 667 */       getPainter().paintButtonBackground(b, g, rect, JideSwingUtilities.getOrientationOf(b), 0);
/*     */     }
/*     */     else {
/* 670 */       g.setColor(this._lightHighlightColor);
/* 671 */       g.drawLine(0, 0, b.getWidth() - 1, 0);
/* 672 */       g.drawLine(0, 0, 0, b.getHeight() - 1);
/*     */ 
/* 674 */       g.setColor(this._shadowColor);
/* 675 */       g.drawLine(0, b.getHeight() - 1, b.getWidth() - 1, b.getHeight() - 1);
/* 676 */       g.drawLine(b.getWidth() - 1, 0, b.getWidth() - 1, b.getHeight() - 1);
/*     */     }
/*     */ 
/* 680 */     if (paintBackground) {
/* 681 */       g.setColor(this._lightHighlightColor);
/* 682 */       g.drawLine(0, 0, b.getWidth() - 1, 0);
/* 683 */       g.drawLine(0, 0, 0, b.getHeight() - 1);
/*     */ 
/* 685 */       g.setColor(this._shadowColor);
/* 686 */       g.drawLine(0, b.getHeight() - 1, b.getWidth() - 1, b.getHeight() - 1);
/* 687 */       g.drawLine(b.getWidth() - 1, 0, b.getWidth() - 1, b.getHeight() - 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void clearTextShiftOffset() {
/* 692 */     this.shiftOffset = 0;
/*     */   }
/*     */ 
/*     */   protected void setTextShiftOffset() {
/* 696 */     this.shiftOffset = this.defaultTextShiftOffset;
/*     */   }
/*     */ 
/*     */   protected int getTextShiftOffset() {
/* 700 */     return this.shiftOffset;
/*     */   }
/*     */ 
/*     */   public Dimension getMinimumSize(JComponent c)
/*     */   {
/* 709 */     Dimension d = getPreferredSize(c);
/* 710 */     View v = (View)c.getClientProperty("html");
/* 711 */     if (v != null)
/* 712 */       if (JideSwingUtilities.getOrientationOf(c) == 0)
/*     */       {
/*     */         Dimension tmp28_27 = d; tmp28_27.width = (int)(tmp28_27.width - (v.getPreferredSpan(0) - v.getMinimumSpan(0)));
/*     */       }
/*     */       else
/*     */       {
/*     */         Dimension tmp53_52 = d; tmp53_52.height = (int)(tmp53_52.height - (v.getPreferredSpan(0) - v.getMinimumSpan(0)));
/*     */       }
/* 717 */     return d;
/*     */   }
/*     */ 
/*     */   public Dimension getPreferredSize(JComponent c)
/*     */   {
/* 722 */     AbstractButton b = (AbstractButton)c;
/*     */ 
/* 724 */     Dimension d = BasicGraphicsUtils.getPreferredButtonSize(b, b.getIconTextGap());
/* 725 */     if (JideSwingUtilities.getOrientationOf(c) == 0) {
/* 726 */       return d;
/*     */     }
/*     */ 
/* 729 */     return new Dimension(d.height, d.width);
/*     */   }
/*     */ 
/*     */   public Dimension getMaximumSize(JComponent c)
/*     */   {
/* 735 */     Dimension d = getPreferredSize(c);
/* 736 */     View v = (View)c.getClientProperty("html");
/* 737 */     if (v != null)
/*     */     {
/*     */       Dimension tmp21_20 = d; tmp21_20.width = (int)(tmp21_20.width + (v.getMaximumSpan(0) - v.getPreferredSpan(0)));
/*     */     }
/* 740 */     return d;
/*     */   }
/*     */ 
/*     */   public ThemePainter getPainter() {
/* 744 */     return this._painter;
/*     */   }
/*     */ 
/*     */   protected void updateMargin(AbstractButton b) {
/* 748 */     String pp = getPropertyPrefix();
/* 749 */     if ((b.getMargin() == null) || ((b.getMargin() instanceof UIResource)))
/* 750 */       if (shouldWrapText(b)) {
/* 751 */         b.setMargin(UIDefaultsLookup.getInsets(pp + "margin.vertical"));
/*     */       }
/*     */       else
/* 754 */         b.setMargin(UIDefaultsLookup.getInsets(pp + "margin"));
/*     */   }
/*     */ 
/*     */   public static boolean shouldWrapText(Component c)
/*     */   {
/* 769 */     return false;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.BasicJideButtonUI
 * JD-Core Version:    0.6.0
 */
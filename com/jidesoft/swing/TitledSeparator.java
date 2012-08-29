/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import java.awt.Color;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.CompoundBorder;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ 
/*     */ public class TitledSeparator extends JPanel
/*     */ {
/*     */   public static final int TYPE_PARTIAL_ETCHED = 0;
/*     */   public static final int TYPE_PARTIAL_LINE = 1;
/*     */   public static final int TYPE_PARTIAL_GRADIENT_LINE = 2;
/*     */   private int _textAlignment;
/*     */   private int _barAlignment;
/*     */   private JComponent _labelComponent;
/*     */   private Border _border;
/*     */   public static final String PROPERTY_LABEL = "label";
/*     */   public static final String PROPERTY_SEPARATOR_BORDER = "separatorBorder";
/*     */   public static final String PROPERTY_BAR_ALIGNMENT = "barAlignment";
/*     */   public static final String PROPERTY_TEXT_ALIGNMENT = "textAlignment";
/*     */ 
/*     */   public TitledSeparator()
/*     */   {
/* 126 */     this("");
/*     */   }
/*     */ 
/*     */   public TitledSeparator(String text)
/*     */   {
/* 135 */     this(text, 0, 2);
/*     */   }
/*     */ 
/*     */   public TitledSeparator(String text, int textAlignment)
/*     */   {
/* 147 */     this(new JLabel(text), 0, textAlignment);
/*     */   }
/*     */ 
/*     */   public TitledSeparator(String text, int type, int textAlignment)
/*     */   {
/* 162 */     this(new JLabel(text), type, textAlignment);
/*     */   }
/*     */ 
/*     */   public TitledSeparator(JComponent labelComponent, int textAlignment)
/*     */   {
/* 174 */     this(labelComponent, 0, textAlignment);
/*     */   }
/*     */ 
/*     */   public TitledSeparator(JComponent labelComponent, int type, int textAlignment)
/*     */   {
/* 189 */     this(labelComponent, type, textAlignment, 0);
/*     */   }
/*     */ 
/*     */   public TitledSeparator(JComponent labelComponent, Border border, int textAlignment)
/*     */   {
/* 202 */     this(labelComponent, border, textAlignment, 0);
/*     */   }
/*     */ 
/*     */   public TitledSeparator(JComponent labelComponent, int type, int textAlignment, int barAlignment)
/*     */   {
/* 224 */     int side = 2;
/* 225 */     int thickness = 1;
/*     */ 
/* 227 */     Color color = labelComponent.getBackground();
/* 228 */     if (color == null)
/* 229 */       color = UIDefaultsLookup.getColor("Label.background");
/*     */     Border border;
/* 231 */     switch (type) {
/*     */     case 1:
/* 233 */       Color c = color.darker();
/* 234 */       border = new PartialLineBorder(c, thickness, side);
/* 235 */       break;
/*     */     case 2:
/* 238 */       Color startColor = color.darker();
/* 239 */       Color finishColor = color.brighter();
/* 240 */       Color[] colors = { startColor, finishColor };
/* 241 */       border = new PartialGradientLineBorder(colors, thickness, side);
/* 242 */       break;
/*     */     case 0:
/*     */     default:
/* 246 */       border = new PartialEtchedBorder(side);
/*     */     }
/*     */ 
/* 250 */     this._labelComponent = labelComponent;
/* 251 */     this._border = border;
/* 252 */     this._textAlignment = textAlignment;
/* 253 */     this._barAlignment = barAlignment;
/* 254 */     validateTitledSeparator();
/*     */   }
/*     */ 
/*     */   public TitledSeparator(JComponent labelComponent, Border border, int textAlignment, int barAlignment)
/*     */   {
/* 270 */     this._labelComponent = labelComponent;
/* 271 */     this._border = border;
/* 272 */     this._textAlignment = textAlignment;
/* 273 */     this._barAlignment = barAlignment;
/* 274 */     validateTitledSeparator();
/*     */   }
/*     */ 
/*     */   public void setLabelComponent(JComponent labelComponent)
/*     */   {
/* 288 */     JComponent oldValue = this._labelComponent;
/* 289 */     if (!JideSwingUtilities.equals(oldValue, labelComponent)) {
/* 290 */       this._labelComponent = labelComponent;
/* 291 */       firePropertyChange("label", oldValue, this._labelComponent);
/* 292 */       validateTitledSeparator();
/* 293 */       repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   public JComponent getLabelComponent()
/*     */   {
/* 303 */     return this._labelComponent;
/*     */   }
/*     */ 
/*     */   public void setSeparatorBorder(Border border)
/*     */   {
/* 314 */     Border oldValue = this._border;
/* 315 */     if (!JideSwingUtilities.equals(oldValue, border)) {
/* 316 */       this._border = border;
/* 317 */       firePropertyChange("separatorBorder", oldValue, this._border);
/* 318 */       validateTitledSeparator();
/* 319 */       repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Border getSeparatorBorder()
/*     */   {
/* 331 */     return this._border;
/*     */   }
/*     */ 
/*     */   public void setTextAlignment(int textAlignment)
/*     */   {
/* 342 */     int oldValue = this._textAlignment;
/* 343 */     if (textAlignment != oldValue) {
/* 344 */       this._textAlignment = textAlignment;
/* 345 */       firePropertyChange("textAlignment", oldValue, this._textAlignment);
/* 346 */       validateTitledSeparator();
/* 347 */       repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getTextAlignment()
/*     */   {
/* 358 */     return this._textAlignment;
/*     */   }
/*     */ 
/*     */   public void setBarAlignment(int barAlignment)
/*     */   {
/* 368 */     int oldValue = this._barAlignment;
/* 369 */     if (barAlignment != oldValue) {
/* 370 */       this._barAlignment = barAlignment;
/* 371 */       firePropertyChange("barAlignment", oldValue, this._barAlignment);
/* 372 */       validateTitledSeparator();
/* 373 */       repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getBarAlignment()
/*     */   {
/* 384 */     return this._barAlignment;
/*     */   }
/*     */ 
/*     */   public void validateTitledSeparator()
/*     */   {
/* 403 */     if (this._labelComponent == null) {
/* 404 */       throw new NullPointerException("Component must not be null.");
/*     */     }
/*     */ 
/* 407 */     if (this._border == null) {
/* 408 */       throw new NullPointerException("border must not be null.");
/*     */     }
/*     */ 
/* 411 */     if ((this._textAlignment != 2) && (this._textAlignment != 4) && (this._textAlignment != 0) && (this._textAlignment != 10) && (this._textAlignment != 11))
/*     */     {
/* 417 */       boolean ltr = getComponentOrientation().isLeftToRight();
/*     */ 
/* 419 */       if (ltr) {
/* 420 */         this._textAlignment = 2;
/*     */       }
/*     */       else {
/* 423 */         this._textAlignment = 4;
/*     */       }
/*     */     }
/*     */ 
/* 427 */     if ((this._textAlignment == 10) || (this._textAlignment == 11))
/*     */     {
/* 429 */       boolean LTR = getComponentOrientation().isLeftToRight();
/* 430 */       if (LTR) {
/* 431 */         this._textAlignment = 2;
/*     */       }
/*     */       else {
/* 434 */         this._textAlignment = 4;
/*     */       }
/*     */     }
/*     */ 
/* 438 */     if ((this._barAlignment != 1) && (this._barAlignment != 0) && (this._barAlignment != 3))
/*     */     {
/* 442 */       this._barAlignment = 0;
/*     */     }
/*     */ 
/* 448 */     removeAll();
/*     */ 
/* 453 */     if ((this._labelComponent instanceof JLabel)) {
/* 454 */       ((JLabel)this._labelComponent).setVerticalAlignment(3);
/*     */     }
/*     */ 
/* 457 */     int top = 0; int left = 0; int bottom = 0; int right = 0;
/* 458 */     Dimension compDimension = this._labelComponent.getPreferredSize();
/* 459 */     int preferredHeight = compDimension.height;
/* 460 */     int separatorThickness = 2;
/*     */     try {
/* 462 */       separatorThickness = this._border.getBorderInsets(null).bottom;
/*     */     }
/*     */     catch (NullPointerException e)
/*     */     {
/*     */     }
/*     */ 
/* 468 */     if (this._textAlignment == 2) {
/* 469 */       left = 4;
/*     */     }
/* 471 */     else if (this._textAlignment == 4) {
/* 472 */       right = 4;
/*     */     }
/* 474 */     else if (this._textAlignment == 0) {
/* 475 */       left = 4;
/* 476 */       right = 4;
/*     */     }
/*     */ 
/* 479 */     if (this._barAlignment == 0) {
/* 480 */       bottom = preferredHeight / 2 - separatorThickness / 2;
/*     */     }
/* 482 */     else if (this._barAlignment == 1) {
/* 483 */       bottom = preferredHeight - separatorThickness;
/*     */     }
/* 485 */     else if (this._barAlignment == 3) {
/* 486 */       bottom = 0;
/*     */     }
/*     */ 
/* 489 */     Border margin = new EmptyBorder(top, left, bottom, right);
/* 490 */     JComponent separator = new JPanel();
/* 491 */     separator.setBorder(new CompoundBorder(margin, this._border));
/*     */ 
/* 497 */     setLayout(new JideBoxLayout(this, 0));
/* 498 */     setOpaque(false);
/*     */ 
/* 500 */     if (this._textAlignment == 2)
/*     */     {
/* 502 */       add(this._labelComponent);
/* 503 */       add(separator, "vary");
/*     */     }
/* 505 */     else if (this._textAlignment == 4)
/*     */     {
/* 507 */       add(separator, "vary");
/* 508 */       add(this._labelComponent);
/*     */     }
/*     */     else
/*     */     {
/* 512 */       JComponent separator2 = new JPanel();
/* 513 */       separator2.setBorder(new CompoundBorder(margin, this._border));
/*     */ 
/* 515 */       add(separator, "flexible");
/* 516 */       add(this._labelComponent, "fix");
/* 517 */       add(separator2, "flexible");
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.TitledSeparator
 * JD-Core Version:    0.6.0
 */
/*     */ package com.jidesoft.tree;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.swing.StyledLabel;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.FontUIResource;
/*     */ import javax.swing.plaf.basic.BasicGraphicsUtils;
/*     */ import javax.swing.tree.TreeCellRenderer;
/*     */ 
/*     */ public class StyledTreeCellRenderer extends StyledLabel
/*     */   implements TreeCellRenderer
/*     */ {
/*     */   private JTree tree;
/*     */   protected boolean selected;
/*     */   protected boolean hasFocus;
/*     */   private boolean drawsFocusBorderAroundIcon;
/*     */   private boolean drawDashedFocusIndicator;
/*     */   private Color treeBGColor;
/*     */   private Color focusBGColor;
/*     */   protected transient Icon closedIcon;
/*     */   protected transient Icon leafIcon;
/*     */   protected transient Icon openIcon;
/*     */   protected Color textSelectionColor;
/*     */   protected Color textNonSelectionColor;
/*     */   protected Color backgroundSelectionColor;
/*     */   protected Color backgroundNonSelectionColor;
/*     */   protected Color borderSelectionColor;
/*     */ 
/*     */   public StyledTreeCellRenderer()
/*     */   {
/* 104 */     setLeafIcon(UIDefaultsLookup.getIcon("Tree.leafIcon"));
/* 105 */     setClosedIcon(UIDefaultsLookup.getIcon("Tree.closedIcon"));
/* 106 */     setOpenIcon(UIDefaultsLookup.getIcon("Tree.openIcon"));
/*     */ 
/* 108 */     setTextSelectionColor(UIDefaultsLookup.getColor("Tree.selectionForeground"));
/* 109 */     setTextNonSelectionColor(UIDefaultsLookup.getColor("Tree.textForeground"));
/* 110 */     setBackgroundSelectionColor(UIDefaultsLookup.getColor("Tree.selectionBackground"));
/* 111 */     setBackgroundNonSelectionColor(UIDefaultsLookup.getColor("Tree.textBackground"));
/* 112 */     setBorderSelectionColor(UIDefaultsLookup.getColor("Tree.selectionBorderColor"));
/* 113 */     Object value = UIDefaultsLookup.get("Tree.drawsFocusBorderAroundIcon");
/* 114 */     this.drawsFocusBorderAroundIcon = ((value != null) && (((Boolean)value).booleanValue()));
/* 115 */     value = UIDefaultsLookup.get("Tree.drawDashedFocusIndicator");
/* 116 */     this.drawDashedFocusIndicator = ((value != null) && (((Boolean)value).booleanValue()));
/*     */   }
/*     */ 
/*     */   public Icon getDefaultOpenIcon()
/*     */   {
/* 124 */     return UIDefaultsLookup.getIcon("Tree.openIcon");
/*     */   }
/*     */ 
/*     */   public Icon getDefaultClosedIcon()
/*     */   {
/* 131 */     return UIDefaultsLookup.getIcon("Tree.closedIcon");
/*     */   }
/*     */ 
/*     */   public Icon getDefaultLeafIcon()
/*     */   {
/* 138 */     return UIDefaultsLookup.getIcon("Tree.leafIcon");
/*     */   }
/*     */ 
/*     */   public void setOpenIcon(Icon newIcon)
/*     */   {
/* 145 */     this.openIcon = newIcon;
/*     */   }
/*     */ 
/*     */   public Icon getOpenIcon()
/*     */   {
/* 152 */     return this.openIcon;
/*     */   }
/*     */ 
/*     */   public void setClosedIcon(Icon newIcon)
/*     */   {
/* 159 */     this.closedIcon = newIcon;
/*     */   }
/*     */ 
/*     */   public Icon getClosedIcon()
/*     */   {
/* 166 */     return this.closedIcon;
/*     */   }
/*     */ 
/*     */   public void setLeafIcon(Icon newIcon)
/*     */   {
/* 173 */     this.leafIcon = newIcon;
/*     */   }
/*     */ 
/*     */   public Icon getLeafIcon()
/*     */   {
/* 180 */     return this.leafIcon;
/*     */   }
/*     */ 
/*     */   public void setTextSelectionColor(Color newColor)
/*     */   {
/* 187 */     this.textSelectionColor = newColor;
/*     */   }
/*     */ 
/*     */   public Color getTextSelectionColor()
/*     */   {
/* 194 */     return this.textSelectionColor;
/*     */   }
/*     */ 
/*     */   public void setTextNonSelectionColor(Color newColor)
/*     */   {
/* 201 */     this.textNonSelectionColor = newColor;
/*     */   }
/*     */ 
/*     */   public Color getTextNonSelectionColor()
/*     */   {
/* 208 */     return this.textNonSelectionColor;
/*     */   }
/*     */ 
/*     */   public void setBackgroundSelectionColor(Color newColor)
/*     */   {
/* 215 */     this.backgroundSelectionColor = newColor;
/*     */   }
/*     */ 
/*     */   public Color getBackgroundSelectionColor()
/*     */   {
/* 223 */     return this.backgroundSelectionColor;
/*     */   }
/*     */ 
/*     */   public void setBackgroundNonSelectionColor(Color newColor)
/*     */   {
/* 230 */     this.backgroundNonSelectionColor = newColor;
/*     */   }
/*     */ 
/*     */   public Color getBackgroundNonSelectionColor()
/*     */   {
/* 237 */     return this.backgroundNonSelectionColor;
/*     */   }
/*     */ 
/*     */   public void setBorderSelectionColor(Color newColor)
/*     */   {
/* 244 */     this.borderSelectionColor = newColor;
/*     */   }
/*     */ 
/*     */   public Color getBorderSelectionColor()
/*     */   {
/* 251 */     return this.borderSelectionColor;
/*     */   }
/*     */ 
/*     */   public void setFont(Font font)
/*     */   {
/* 262 */     if ((font instanceof FontUIResource))
/* 263 */       font = null;
/* 264 */     super.setFont(font);
/*     */   }
/*     */ 
/*     */   public Font getFont()
/*     */   {
/* 274 */     Font font = super.getFont();
/*     */ 
/* 276 */     if ((font == null) && (this.tree != null))
/*     */     {
/* 279 */       font = this.tree.getFont();
/*     */     }
/* 281 */     return font;
/*     */   }
/*     */ 
/*     */   public void setBackground(Color color)
/*     */   {
/* 292 */     if ((color instanceof ColorUIResource))
/* 293 */       color = null;
/* 294 */     super.setBackground(color);
/*     */   }
/*     */ 
/*     */   public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
/*     */   {
/* 308 */     setOpaque(false);
/*     */ 
/* 310 */     if (!tree.isEnabled()) {
/* 311 */       setEnabled(false);
/* 312 */       if (leaf) {
/* 313 */         setDisabledIcon(getLeafIcon());
/*     */       }
/* 315 */       else if (expanded) {
/* 316 */         setDisabledIcon(getOpenIcon());
/*     */       }
/*     */       else
/* 319 */         setDisabledIcon(getClosedIcon());
/*     */     }
/*     */     else
/*     */     {
/* 323 */       setEnabled(true);
/* 324 */       if (leaf) {
/* 325 */         setIcon(getLeafIcon());
/*     */       }
/* 327 */       else if (expanded) {
/* 328 */         setIcon(getOpenIcon());
/*     */       }
/*     */       else {
/* 331 */         setIcon(getClosedIcon());
/*     */       }
/*     */     }
/*     */ 
/* 335 */     setIgnoreColorSettings(sel);
/* 336 */     customizeStyledLabel(tree, value, sel, expanded, leaf, row, hasFocus);
/*     */ 
/* 338 */     this.tree = tree;
/* 339 */     this.hasFocus = hasFocus;
/* 340 */     if (sel)
/* 341 */       setForeground(getTextSelectionColor());
/*     */     else {
/* 343 */       setForeground(getTextNonSelectionColor());
/*     */     }
/* 345 */     applyComponentOrientation(tree.getComponentOrientation());
/*     */ 
/* 347 */     this.selected = sel;
/*     */ 
/* 349 */     return this;
/*     */   }
/*     */ 
/*     */   protected void customizeStyledLabel(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
/*     */   {
/* 364 */     String stringValue = tree.convertValueToText(value, sel, expanded, leaf, row, hasFocus);
/*     */ 
/* 366 */     clearStyleRanges();
/* 367 */     setText(stringValue);
/*     */   }
/*     */ 
/*     */   public void paint(Graphics g)
/*     */   {
/*     */     Color bColor;
/*     */     Color bColor;
/* 377 */     if (this.selected) {
/* 378 */       bColor = getBackgroundSelectionColor();
/*     */     }
/*     */     else {
/* 381 */       bColor = getBackgroundNonSelectionColor();
/* 382 */       if (bColor == null)
/* 383 */         bColor = getBackground();
/*     */     }
/* 385 */     int imageOffset = -1;
/*     */ 
/* 387 */     if (((this.selected) || (isOpaque())) && 
/* 388 */       (bColor != null)) {
/* 389 */       imageOffset = getLabelStart();
/* 390 */       g.setColor(bColor);
/* 391 */       if (getComponentOrientation().isLeftToRight()) {
/* 392 */         g.fillRect(imageOffset, 0, getWidth() - imageOffset, getHeight());
/*     */       }
/*     */       else {
/* 395 */         g.fillRect(0, 0, getWidth() - imageOffset, getHeight());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 400 */     super.paint(g);
/*     */ 
/* 402 */     if (this.hasFocus) {
/* 403 */       if (this.drawsFocusBorderAroundIcon) {
/* 404 */         imageOffset = 0;
/*     */       }
/* 406 */       else if (imageOffset == -1) {
/* 407 */         imageOffset = getLabelStart();
/*     */       }
/* 409 */       if (getComponentOrientation().isLeftToRight()) {
/* 410 */         paintFocus(g, imageOffset, 0, getWidth() - imageOffset, getHeight());
/*     */       }
/*     */       else
/*     */       {
/* 414 */         paintFocus(g, 0, 0, getWidth() - imageOffset, getHeight());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void paintFocus(Graphics g, int x, int y, int w, int h) {
/* 420 */     Color bsColor = getBorderSelectionColor();
/*     */ 
/* 422 */     if ((bsColor != null) && ((this.selected) || (!this.drawDashedFocusIndicator))) {
/* 423 */       g.setColor(bsColor);
/* 424 */       g.drawRect(x, y, w - 1, h - 1);
/*     */     }
/* 426 */     if (this.drawDashedFocusIndicator)
/*     */     {
/*     */       Color color;
/*     */       Color color;
/* 428 */       if (this.selected) {
/* 429 */         color = getBackgroundSelectionColor();
/*     */       }
/*     */       else {
/* 432 */         color = getBackgroundNonSelectionColor();
/* 433 */         if (color == null) {
/* 434 */           color = getBackground();
/*     */         }
/*     */       }
/*     */ 
/* 438 */       if (this.treeBGColor != color) {
/* 439 */         this.treeBGColor = color;
/* 440 */         this.focusBGColor = new Color(color.getRGB() ^ 0xFFFFFFFF);
/*     */       }
/* 442 */       g.setColor(this.focusBGColor);
/* 443 */       BasicGraphicsUtils.drawDashedRect(g, x, y, w, h);
/*     */     }
/*     */   }
/*     */ 
/*     */   private int getLabelStart() {
/* 448 */     Icon icon = getIcon();
/* 449 */     if ((icon != null) && (getText().trim().length() != 0)) {
/* 450 */       return icon.getIconWidth() + Math.max(0, getIconTextGap());
/*     */     }
/* 452 */     return 0;
/*     */   }
/*     */ 
/*     */   public Dimension getPreferredSize()
/*     */   {
/* 460 */     Dimension retDimension = super.getPreferredSize();
/*     */ 
/* 462 */     if (retDimension != null) {
/* 463 */       retDimension = new Dimension(retDimension.width + 3, retDimension.height);
/*     */     }
/* 465 */     return retDimension;
/*     */   }
/*     */ 
/*     */   public void validate()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void invalidate()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void revalidate()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void repaint(long tm, int x, int y, int width, int height)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void repaint(Rectangle r)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void repaint()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void firePropertyChange(String propertyName, Object oldValue, Object newValue)
/*     */   {
/* 520 */     if (propertyName.equals("text"))
/* 521 */       super.firePropertyChange(propertyName, oldValue, newValue);
/*     */   }
/*     */ 
/*     */   public void firePropertyChange(String propertyName, byte oldValue, byte newValue)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void firePropertyChange(String propertyName, char oldValue, char newValue)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void firePropertyChange(String propertyName, short oldValue, short newValue)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void firePropertyChange(String propertyName, int oldValue, int newValue)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void firePropertyChange(String propertyName, long oldValue, long newValue)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void firePropertyChange(String propertyName, float oldValue, float newValue)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void firePropertyChange(String propertyName, double oldValue, double newValue)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue)
/*     */   {
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.tree.StyledTreeCellRenderer
 * JD-Core Version:    0.6.0
 */
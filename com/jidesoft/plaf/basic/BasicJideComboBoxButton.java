/*     */ package com.jidesoft.plaf.basic;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.CellRendererPane;
/*     */ import javax.swing.DefaultButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JList;
/*     */ 
/*     */ public class BasicJideComboBoxButton extends JButton
/*     */ {
/*     */   protected JComboBox _comboBox;
/*     */   protected JList _listBox;
/*     */   protected CellRendererPane _rendererPane;
/*     */   protected Icon _comboIcon;
/*  13 */   protected boolean _iconOnly = false;
/*     */ 
/*     */   public final JComboBox getComboBox() {
/*  16 */     return this._comboBox;
/*     */   }
/*     */ 
/*     */   public final void setComboBox(JComboBox cb) {
/*  20 */     this._comboBox = cb;
/*     */   }
/*     */ 
/*     */   public final Icon getComboIcon() {
/*  24 */     return this._comboIcon;
/*     */   }
/*     */ 
/*     */   public final void setComboIcon(Icon i) {
/*  28 */     this._comboIcon = i;
/*     */   }
/*     */ 
/*     */   public final boolean isIconOnly() {
/*  32 */     return this._iconOnly;
/*     */   }
/*     */ 
/*     */   public final void setIconOnly(boolean isIconOnly) {
/*  36 */     this._iconOnly = isIconOnly;
/*     */   }
/*     */ 
/*     */   public BasicJideComboBoxButton()
/*     */   {
/*  41 */     super("");
/*  42 */     addMouseListener(new BasicJideButtonListener(this));
/*     */ 
/*  45 */     DefaultButtonModel model = new DefaultButtonModel() {
/*     */       private static final long serialVersionUID = -5866286842846125926L;
/*     */ 
/*     */       public void setArmed(boolean armed) {
/*  50 */         super.setArmed((isPressed()) || (armed));
/*     */       }
/*     */     };
/*  53 */     setModel(model);
/*  54 */     customizeButton();
/*     */   }
/*     */ 
/*     */   public BasicJideComboBoxButton(JComboBox cb, Icon i, CellRendererPane pane, JList list)
/*     */   {
/*  60 */     this();
/*  61 */     this._comboBox = cb;
/*  62 */     this._comboIcon = i;
/*  63 */     this._rendererPane = pane;
/*  64 */     this._listBox = list;
/*  65 */     setEnabled(this._comboBox.isEnabled());
/*     */   }
/*     */ 
/*     */   protected void customizeButton() {
/*  69 */     setFocusable(false);
/*  70 */     setBorderPainted(false);
/*  71 */     setRequestFocusEnabled(false);
/*     */   }
/*     */ 
/*     */   public BasicJideComboBoxButton(JComboBox comboBox, Icon icon, boolean editable, CellRendererPane currentValuePane, JList listBox)
/*     */   {
/*  76 */     this(comboBox, icon, currentValuePane, listBox);
/*  77 */     this._iconOnly = editable;
/*     */   }
/*     */ 
/*     */   public boolean isFocusTraversable()
/*     */   {
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */   protected void paintComponent(Graphics g)
/*     */   {
/*  87 */     Color old = g.getColor();
/*  88 */     ThemePainter painter = (ThemePainter)UIDefaultsLookup.get("Theme.painter");
/*  89 */     if ((getModel().isSelected()) || (getModel().isPressed()) || (this._comboBox.isPopupVisible()))
/*     */     {
/*  91 */       painter.paintButtonBackground(this, g, new Rectangle(0, 0, getWidth(), getHeight()), 0, 1, false);
/*     */     }
/*  93 */     else if ((getModel().isRollover()) || (((BasicJideComboBoxUI)this._comboBox.getUI()).isRollOver()))
/*     */     {
/*  95 */       painter.paintButtonBackground(this, g, new Rectangle(0, 0, getWidth(), getHeight()), 0, 2, false);
/*     */     }
/*     */     else
/*     */     {
/*  99 */       painter.paintButtonBackground(this, g, new Rectangle(0, 0, getWidth(), getHeight()), 0, 0, false);
/*     */     }
/* 101 */     if ((((BasicJideComboBoxUI)this._comboBox.getUI()).isRollOver()) || (this._comboBox.isPopupVisible()))
/*     */     {
/* 103 */       g.setColor(painter.getMenuItemBorderColor());
/* 104 */       g.drawLine(0, 0, 0, getHeight());
/*     */     }
/*     */ 
/* 107 */     paintIcon(g);
/* 108 */     g.setColor(old);
/*     */   }
/*     */ 
/*     */   protected void paintIcon(Graphics g) {
/* 112 */     Insets insets = getInsets();
/*     */ 
/* 114 */     int width = getWidth() - (insets.left + insets.right);
/* 115 */     int height = getHeight() - (insets.top + insets.bottom);
/*     */ 
/* 117 */     if ((height <= 0) || (width <= 0)) {
/* 118 */       return;
/*     */     }
/*     */ 
/* 121 */     int left = insets.left;
/* 122 */     int top = insets.top;
/* 123 */     int bottom = top + (height - 1);
/*     */ 
/* 129 */     if (this._comboIcon != null) {
/* 130 */       int iconWidth = this._comboIcon.getIconWidth();
/* 131 */       int iconHeight = this._comboIcon.getIconHeight();
/*     */       int iconTop;
/*     */       int iconLeft;
/*     */       int iconTop;
/* 134 */       if (this._iconOnly) {
/* 135 */         int iconLeft = getWidth() / 2 - iconWidth / 2;
/* 136 */         iconTop = getHeight() / 2 - iconHeight / 2 - 1;
/*     */       }
/*     */       else {
/* 139 */         iconLeft = left;
/* 140 */         iconTop = top + (bottom - top) / 2 - iconHeight / 2;
/*     */       }
/* 142 */       this._comboIcon.paintIcon(this, g, iconLeft, iconTop);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.BasicJideComboBoxButton
 * JD-Core Version:    0.6.0
 */
/*     */ package com.jidesoft.plaf.basic;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Container;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.border.AbstractBorder;
/*     */ import javax.swing.event.PopupMenuEvent;
/*     */ import javax.swing.event.PopupMenuListener;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.metal.MetalComboBoxUI;
/*     */ import javax.swing.plaf.metal.MetalComboBoxUI.MetalComboBoxLayoutManager;
/*     */ 
/*     */ public class BasicJideComboBoxUI extends MetalComboBoxUI
/*     */ {
/*     */   private boolean _rollOver;
/*     */   protected RolloverListener _rolloverListener;
/*     */ 
/*     */   public BasicJideComboBoxUI()
/*     */   {
/* 171 */     this._rollOver = false;
/*     */   }
/*     */ 
/*     */   public static BasicJideComboBoxUI createUI(JComponent c)
/*     */   {
/*  22 */     return new BasicJideComboBoxUI();
/*     */   }
/*     */ 
/*     */   protected void installDefaults()
/*     */   {
/*  27 */     super.installDefaults();
/*  28 */     JideSwingUtilities.installBorder(this.comboBox, createComboBoxBorder());
/*     */   }
/*     */ 
/*     */   protected BasicJideComboBoxBorder createComboBoxBorder() {
/*  32 */     return new BasicJideComboBoxBorder();
/*     */   }
/*     */ 
/*     */   protected void uninstallDefaults()
/*     */   {
/*  38 */     super.uninstallDefaults();
/*  39 */     LookAndFeel.uninstallBorder(this.comboBox);
/*     */   }
/*     */ 
/*     */   protected void installListeners()
/*     */   {
/*  44 */     super.installListeners();
/*  45 */     if (this._rolloverListener == null) {
/*  46 */       this._rolloverListener = createRolloverListener();
/*     */     }
/*  48 */     this.comboBox.addMouseListener(this._rolloverListener);
/*     */   }
/*     */ 
/*     */   protected void uninstallListeners()
/*     */   {
/*  53 */     super.uninstallListeners();
/*  54 */     this.comboBox.removeMouseListener(this._rolloverListener);
/*  55 */     this._rolloverListener = null;
/*     */   }
/*     */ 
/*     */   protected RolloverListener createRolloverListener() {
/*  59 */     return new RolloverListener();
/*     */   }
/*     */ 
/*     */   protected JButton createArrowButton()
/*     */   {
/*  64 */     JButton button = new BasicJideComboBoxButton(this.comboBox, new BasicJideComboBoxIcon(), this.comboBox.isEditable(), this.currentValuePane, this.listBox);
/*     */ 
/*  68 */     button.setMargin(new Insets(1, 3, 0, 4));
/*  69 */     button.setFocusPainted(this.comboBox.isEditable());
/*  70 */     button.addMouseListener(this._rolloverListener);
/*  71 */     return button;
/*     */   }
/*     */ 
/*     */   public void unconfigureArrowButton()
/*     */   {
/*  76 */     super.unconfigureArrowButton();
/*  77 */     this.arrowButton.removeMouseListener(this._rolloverListener);
/*     */   }
/*     */ 
/*     */   public void unconfigureEditor()
/*     */   {
/*  82 */     super.unconfigureEditor();
/*  83 */     this.editor.removeMouseListener(this._rolloverListener);
/*  84 */     this.editor.removeFocusListener(this._rolloverListener);
/*     */   }
/*     */ 
/*     */   public void configureEditor()
/*     */   {
/*  89 */     if ((this.editor instanceof JComponent)) {
/*  90 */       ((JComponent)this.editor).setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
/*     */     }
/*  92 */     this.editor.addMouseListener(this._rolloverListener);
/*  93 */     this.editor.addFocusListener(this._rolloverListener);
/*     */   }
/*     */ 
/*     */   public void layoutComboBox(Container parent, MetalComboBoxUI.MetalComboBoxLayoutManager manager)
/*     */   {
/* 101 */     if (this.arrowButton != null) {
/* 102 */       if ((this.arrowButton instanceof BasicJideComboBoxButton)) {
/* 103 */         Icon icon = ((BasicJideComboBoxButton)this.arrowButton).getComboIcon();
/* 104 */         Insets buttonInsets = this.arrowButton.getInsets();
/* 105 */         Insets insets = this.comboBox.getInsets();
/* 106 */         int buttonWidth = icon.getIconWidth() + buttonInsets.left + buttonInsets.right;
/*     */ 
/* 108 */         this.arrowButton.setBounds(this.comboBox.getComponentOrientation().isLeftToRight() ? this.comboBox.getWidth() - insets.right - buttonWidth : insets.left, insets.top, buttonWidth, this.comboBox.getHeight() - insets.top - insets.bottom);
/*     */       }
/*     */       else
/*     */       {
/* 116 */         Insets insets = this.comboBox.getInsets();
/* 117 */         int width = this.comboBox.getWidth();
/* 118 */         int height = this.comboBox.getHeight();
/* 119 */         this.arrowButton.setBounds(insets.left, insets.top, width - (insets.left + insets.right), height - (insets.top + insets.bottom));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 126 */     if (this.editor != null) {
/* 127 */       Rectangle cvb = rectangleForCurrentValue();
/* 128 */       this.editor.setBounds(cvb);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isRollOver() {
/* 133 */     return (this._rollOver) || (this.editor != null ? this.editor.hasFocus() : this.hasFocus);
/*     */   }
/*     */ 
/*     */   public void setRollOver(boolean rollOver) {
/* 137 */     this._rollOver = rollOver;
/*     */   }
/*     */ 
/*     */   protected class BasicJideComboBoxIcon
/*     */     implements Icon
/*     */   {
/*     */     protected BasicJideComboBoxIcon()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void paintIcon(Component c, Graphics g, int x, int y)
/*     */     {
/* 225 */       JideSwingUtilities.paintArrow(g, c.getForeground(), x, y, 5, 0);
/*     */     }
/*     */ 
/*     */     public int getIconWidth() {
/* 229 */       return 5;
/*     */     }
/*     */ 
/*     */     public int getIconHeight() {
/* 233 */       return 3;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected class RolloverListener extends MouseAdapter
/*     */     implements FocusListener
/*     */   {
/*     */     protected RolloverListener()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void mouseEntered(MouseEvent e)
/*     */     {
/* 177 */       BasicJideComboBoxUI.this.setRollOver(true);
/* 178 */       BasicJideComboBoxUI.this.comboBox.repaint();
/*     */     }
/*     */ 
/*     */     public void mouseExited(MouseEvent e)
/*     */     {
/* 183 */       if (BasicJideComboBoxUI.this.comboBox.isPopupVisible())
/*     */       {
/* 192 */         PopupMenuListener l = new PopupMenuListener()
/*     */         {
/*     */           public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
/*     */           }
/*     */ 
/*     */           public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
/* 198 */             BasicJideComboBoxUI.this.setRollOver(false);
/* 199 */             ((JPopupMenu)BasicJideComboBoxUI.this.popup).removePopupMenuListener(this);
/*     */           }
/*     */ 
/*     */           public void popupMenuCanceled(PopupMenuEvent e)
/*     */           {
/*     */           }
/*     */         };
/* 205 */         ((JPopupMenu)BasicJideComboBoxUI.this.popup).addPopupMenuListener(l);
/* 206 */         BasicJideComboBoxUI.this.setRollOver(true);
/*     */       }
/*     */       else {
/* 209 */         BasicJideComboBoxUI.this.setRollOver(false);
/*     */       }
/* 211 */       BasicJideComboBoxUI.this.comboBox.repaint();
/*     */     }
/*     */ 
/*     */     public void focusGained(FocusEvent e) {
/* 215 */       BasicJideComboBoxUI.this.comboBox.repaint();
/*     */     }
/*     */ 
/*     */     public void focusLost(FocusEvent e) {
/* 219 */       BasicJideComboBoxUI.this.comboBox.repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected class BasicJideComboBoxBorder extends AbstractBorder
/*     */     implements UIResource
/*     */   {
/*     */     private static final long serialVersionUID = 4633179647696691207L;
/*     */ 
/*     */     protected BasicJideComboBoxBorder()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
/*     */     {
/* 145 */       Color old = g.getColor();
/* 146 */       JComboBox box = (JComboBox)c;
/* 147 */       if ((box.isPopupVisible()) || (BasicJideComboBoxUI.this.isRollOver())) {
/* 148 */         g.setColor(UIDefaultsLookup.getColor("JideButton.borderColor"));
/*     */       }
/*     */       else {
/* 151 */         g.setColor(UIDefaultsLookup.getColor("TextField.background"));
/*     */       }
/*     */ 
/* 154 */       g.drawRect(x, y, width - 1, height - 1);
/*     */ 
/* 156 */       g.setColor(old);
/*     */     }
/*     */ 
/*     */     public Insets getBorderInsets(Component c)
/*     */     {
/* 161 */       return new Insets(1, 1, 1, 1);
/*     */     }
/*     */ 
/*     */     public Insets getBorderInsets(Component c, Insets insets)
/*     */     {
/* 166 */       insets.left = (insets.right = insets.top = insets.bottom = 1);
/* 167 */       return insets;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.BasicJideComboBoxUI
 * JD-Core Version:    0.6.0
 */
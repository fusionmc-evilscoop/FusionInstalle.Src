/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Font;
/*    */ import java.awt.Rectangle;
/*    */ import javax.swing.JTextArea;
/*    */ import javax.swing.LookAndFeel;
/*    */ import javax.swing.UIManager;
/*    */ import javax.swing.plaf.UIResource;
/*    */ import javax.swing.text.DefaultCaret;
/*    */ 
/*    */ public class MultilineLabel extends JTextArea
/*    */ {
/*    */   public MultilineLabel()
/*    */   {
/* 18 */     initComponents();
/*    */   }
/*    */ 
/*    */   public MultilineLabel(String s) {
/* 22 */     super(s);
/* 23 */     initComponents();
/*    */   }
/*    */ 
/*    */   private void initComponents() {
/* 27 */     adjustUI();
/*    */   }
/*    */ 
/*    */   public void updateUI()
/*    */   {
/* 36 */     super.updateUI();
/* 37 */     adjustUI();
/*    */   }
/*    */ 
/*    */   protected void adjustUI()
/*    */   {
/* 44 */     setLineWrap(true);
/* 45 */     setWrapStyleWord(true);
/* 46 */     setEditable(false);
/* 47 */     setRequestFocusEnabled(false);
/* 48 */     setFocusable(false);
/* 49 */     JideSwingUtilities.setTextComponentTransparent(this);
/*    */ 
/* 51 */     setCaret(new DefaultCaret()
/*    */     {
/*    */       private static final long serialVersionUID = 1242467463492127346L;
/*    */ 
/*    */       protected void adjustVisibility(Rectangle nloc)
/*    */       {
/*    */       }
/*    */     });
/* 59 */     LookAndFeel.installBorder(this, "Label.border");
/* 60 */     Color fg = getForeground();
/* 61 */     if ((fg == null) || ((fg instanceof UIResource))) {
/* 62 */       setForeground(UIManager.getColor("Label.foreground"));
/*    */     }
/* 64 */     Font f = getFont();
/* 65 */     if ((f == null) || ((f instanceof UIResource))) {
/* 66 */       setFont(UIManager.getFont("Label.font"));
/*    */     }
/* 68 */     setBackground(null);
/*    */   }
/*    */ 
/*    */   public Dimension getMinimumSize()
/*    */   {
/* 79 */     return getPreferredSize();
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.MultilineLabel
 * JD-Core Version:    0.6.0
 */
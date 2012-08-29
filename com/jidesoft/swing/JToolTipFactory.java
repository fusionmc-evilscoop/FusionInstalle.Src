/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JToolTip;
/*    */ 
/*    */ public class JToolTipFactory
/*    */ {
/*    */   private static JToolTipFactory _tooltipFactory;
/*    */ 
/*    */   public JToolTip createToolTip(JComponent c)
/*    */   {
/* 26 */     return createToolTip(c, false);
/*    */   }
/*    */ 
/*    */   public JToolTip createToolTip(JComponent c, boolean overlapping)
/*    */   {
/* 38 */     JToolTip tt = new JToolTip();
/* 39 */     if (c != null) {
/* 40 */       tt.setComponent(c);
/* 41 */       if (overlapping) {
/* 42 */         if (c.getBackground() != null) {
/* 43 */           Color bg = c.getBackground();
/* 44 */           if (bg.getAlpha() != 255) {
/* 45 */             bg = new Color(bg.getRed(), bg.getGreen(), bg.getBlue());
/*    */           }
/* 47 */           tt.setBackground(bg);
/*    */         }
/* 49 */         if (c.getForeground() != null) {
/* 50 */           tt.setForeground(c.getForeground());
/*    */         }
/* 52 */         if (c.getFont() != null) {
/* 53 */           tt.setFont(c.getFont());
/*    */         }
/*    */       }
/*    */     }
/* 57 */     return tt;
/*    */   }
/*    */ 
/*    */   public static void setSharedInstance(JToolTipFactory factory)
/*    */   {
/* 68 */     if (factory == null) {
/* 69 */       throw new IllegalArgumentException("JToolTipFactory can not be null");
/*    */     }
/*    */ 
/* 72 */     _tooltipFactory = factory;
/*    */   }
/*    */ 
/*    */   public static JToolTipFactory getSharedInstance()
/*    */   {
/* 81 */     if (_tooltipFactory == null) {
/* 82 */       _tooltipFactory = new JToolTipFactory();
/*    */     }
/*    */ 
/* 85 */     return _tooltipFactory;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.JToolTipFactory
 * JD-Core Version:    0.6.0
 */
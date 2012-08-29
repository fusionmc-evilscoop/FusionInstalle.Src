/*    */ package com.jidesoft.plaf.xerto;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Insets;
/*    */ import javax.swing.border.Border;
/*    */ import javax.swing.plaf.UIResource;
/*    */ 
/*    */ class XertoFrameBorder
/*    */   implements Border, UIResource
/*    */ {
/* 16 */   private static Color[] _colors = { new Color(142, 145, 128), new Color(172, 172, 153), new Color(203, 198, 181), new Color(213, 207, 188) };
/*    */   private Insets _insets;
/*    */ 
/*    */   public XertoFrameBorder(Insets insets)
/*    */   {
/* 26 */     this._insets = insets;
/*    */   }
/*    */ 
/*    */   public Insets getBorderInsets(Component c)
/*    */   {
/* 35 */     return this._insets;
/*    */   }
/*    */ 
/*    */   public boolean isBorderOpaque()
/*    */   {
/* 44 */     return true;
/*    */   }
/*    */ 
/*    */   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
/*    */   {
/* 50 */     for (int i = 0; i < _colors.length; i++) {
/* 51 */       g.setColor(_colors[i]);
/* 52 */       g.drawRect(x + i, y + i, width - i * 2 - 1, height - i * 2 - 1);
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.xerto.XertoFrameBorder
 * JD-Core Version:    0.6.0
 */
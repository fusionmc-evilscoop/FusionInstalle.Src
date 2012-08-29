/*    */ package com.jidesoft.plaf.xerto;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Insets;
/*    */ import javax.swing.border.Border;
/*    */ import javax.swing.plaf.UIResource;
/*    */ 
/*    */ public class StatusBarBorder
/*    */   implements Border, UIResource
/*    */ {
/*    */   public Insets getBorderInsets(Component c)
/*    */   {
/* 21 */     return new Insets(6, 0, 2, 0);
/*    */   }
/*    */ 
/*    */   public boolean isBorderOpaque()
/*    */   {
/* 29 */     return false;
/*    */   }
/*    */ 
/*    */   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
/*    */   {
/* 43 */     g.setColor(XertoUtils.getControlDarkShadowColor());
/* 44 */     g.drawLine(x, y + 2, x + width, y + 2);
/* 45 */     g.setColor(XertoUtils.getControlMidShadowColor());
/* 46 */     g.drawLine(x, y + 3, x + width, y + 3);
/* 47 */     g.drawLine(x, y + height - 1, x + width, y + height - 1);
/* 48 */     g.setColor(XertoUtils.getControlLightShadowColor());
/* 49 */     g.drawLine(x, y + 4, x + width, y + 4);
/* 50 */     g.drawLine(x, y + height - 2, x + width, y + height - 2);
/* 51 */     g.setColor(XertoUtils.getControlVeryLightShadowColor());
/* 52 */     g.drawLine(x, y + 5, x + width, y + 5);
/* 53 */     g.drawLine(x, y + height - 3, x + width, y + height - 3);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.xerto.StatusBarBorder
 * JD-Core Version:    0.6.0
 */
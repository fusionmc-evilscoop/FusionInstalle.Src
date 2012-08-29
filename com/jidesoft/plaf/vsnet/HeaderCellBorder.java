/*    */ package com.jidesoft.plaf.vsnet;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Insets;
/*    */ import javax.swing.border.Border;
/*    */ import javax.swing.plaf.UIResource;
/*    */ 
/*    */ public class HeaderCellBorder
/*    */   implements Border, UIResource
/*    */ {
/*    */   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
/*    */   {
/* 20 */     g.setColor(new Color(0, 0, 0, 27));
/* 21 */     g.drawLine(0, height - 3, width, height - 3);
/*    */ 
/* 23 */     g.setColor(new Color(0, 0, 0, 37));
/* 24 */     g.drawLine(0, height - 2, width, height - 2);
/*    */ 
/* 26 */     g.setColor(new Color(0, 0, 0, 52));
/* 27 */     g.drawLine(0, height - 1, width, height - 1);
/* 28 */     g.drawLine(width - 1, 0, width - 1, height - 1);
/*    */   }
/*    */ 
/*    */   public Insets getBorderInsets(Component c) {
/* 32 */     return new Insets(0, 0, 3, 1);
/*    */   }
/*    */ 
/*    */   public boolean isBorderOpaque() {
/* 36 */     return false;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.vsnet.HeaderCellBorder
 * JD-Core Version:    0.6.0
 */
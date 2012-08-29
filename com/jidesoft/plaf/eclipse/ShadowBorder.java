/*    */ package com.jidesoft.plaf.eclipse;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Insets;
/*    */ import javax.swing.border.Border;
/*    */ import javax.swing.plaf.UIResource;
/*    */ 
/*    */ public class ShadowBorder
/*    */   implements Border, UIResource
/*    */ {
/*    */   protected Color _highlight;
/*    */   protected Color _lightHighlight;
/*    */   protected Color _shadow;
/*    */   protected Color _darkShadow;
/*    */   protected Insets _insets;
/*    */ 
/*    */   public ShadowBorder(Color highlight, Color lightHighlight, Color shadow, Color darkShadow, Insets insets)
/*    */   {
/* 24 */     this._highlight = highlight;
/* 25 */     this._lightHighlight = lightHighlight;
/* 26 */     this._shadow = shadow;
/* 27 */     this._darkShadow = darkShadow;
/* 28 */     this._insets = insets;
/*    */   }
/*    */ 
/*    */   public Insets getBorderInsets(Component c)
/*    */   {
/* 37 */     return this._insets;
/*    */   }
/*    */ 
/*    */   public boolean isBorderOpaque()
/*    */   {
/* 46 */     return true;
/*    */   }
/*    */ 
/*    */   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
/*    */   {
/* 51 */     if ((this._insets.top <= 0) || (
/* 53 */       (this._insets.left <= 0) || 
/* 55 */       (this._insets.bottom > 0))) {
/* 56 */       g.setColor(this._darkShadow);
/* 57 */       g.drawLine(x + 1, y + height - 2, x + width - 2, y + height - 2);
/* 58 */       g.setColor(this._shadow);
/* 59 */       g.drawLine(x + 2, y + height - 1, x + width - 2, y + height - 1);
/*    */     }
/* 61 */     if (this._insets.right > 0) {
/* 62 */       g.setColor(this._darkShadow);
/* 63 */       g.drawLine(x + width - 2, y + 1, x + width - 2, y + height - 2);
/* 64 */       g.setColor(this._shadow);
/* 65 */       g.drawLine(x + width - 1, y + 2, x + width - 1, y + height - 2);
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.eclipse.ShadowBorder
 * JD-Core Version:    0.6.0
 */
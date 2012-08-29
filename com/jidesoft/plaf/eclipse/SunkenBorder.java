/*    */ package com.jidesoft.plaf.eclipse;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Insets;
/*    */ import javax.swing.border.Border;
/*    */ import javax.swing.plaf.UIResource;
/*    */ 
/*    */ public class SunkenBorder
/*    */   implements Border, UIResource
/*    */ {
/*    */   protected Color _highlight;
/*    */   protected Color _lightHighlight;
/*    */   protected Color _shadow;
/*    */   protected Color _darkShadow;
/*    */   protected Insets _insets;
/*    */ 
/*    */   public SunkenBorder(Color highlight, Color lightHighlight, Color shadow, Color darkShadow, Insets insets)
/*    */   {
/* 21 */     this._highlight = highlight;
/* 22 */     this._lightHighlight = lightHighlight;
/* 23 */     this._shadow = shadow;
/* 24 */     this._darkShadow = darkShadow;
/* 25 */     this._insets = insets;
/*    */   }
/*    */ 
/*    */   public Insets getBorderInsets(Component c)
/*    */   {
/* 34 */     return this._insets;
/*    */   }
/*    */ 
/*    */   public boolean isBorderOpaque()
/*    */   {
/* 43 */     return true;
/*    */   }
/*    */ 
/*    */   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
/*    */   {
/* 48 */     if (this._insets.top > 0) {
/* 49 */       g.setColor(this._shadow);
/* 50 */       g.drawLine(x, y, x + width, y);
/*    */     }
/* 52 */     if (this._insets.left > 0) {
/* 53 */       g.setColor(this._shadow);
/* 54 */       g.drawLine(x, y, x, y + height);
/*    */     }
/* 56 */     if (this._insets.bottom > 0) {
/* 57 */       g.setColor(this._lightHighlight);
/* 58 */       g.drawLine(x, y + height - 1, x + width, y + height - 1);
/*    */     }
/* 60 */     if (this._insets.right > 0) {
/* 61 */       g.setColor(this._lightHighlight);
/* 62 */       g.drawLine(x + width - 1, y, x + width - 1, y + height);
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.eclipse.SunkenBorder
 * JD-Core Version:    0.6.0
 */
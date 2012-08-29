/*    */ package com.jidesoft.plaf.eclipse;
/*    */ 
/*    */ import com.jidesoft.plaf.XPUtils;
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Insets;
/*    */ import javax.swing.border.Border;
/*    */ import javax.swing.plaf.UIResource;
/*    */ 
/*    */ public class RaisedBorder
/*    */   implements Border, UIResource
/*    */ {
/*    */   protected Color _highlight;
/*    */   protected Color _lightHighlight;
/*    */   protected Color _shadow;
/*    */   protected Color _darkShadow;
/*    */   protected Insets _insets;
/*    */ 
/*    */   public RaisedBorder(Color highlight, Color lightHighlight, Color shadow, Color darkShadow, Insets insets)
/*    */   {
/* 23 */     this._highlight = highlight;
/* 24 */     this._lightHighlight = lightHighlight;
/* 25 */     this._shadow = shadow;
/* 26 */     this._darkShadow = darkShadow;
/* 27 */     this._insets = insets;
/*    */   }
/*    */ 
/*    */   public Insets getBorderInsets(Component c)
/*    */   {
/* 36 */     return this._insets;
/*    */   }
/*    */ 
/*    */   public boolean isBorderOpaque()
/*    */   {
/* 45 */     return true;
/*    */   }
/*    */ 
/*    */   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
/*    */   {
/* 50 */     if (XPUtils.isXPStyleOn()) {
/* 51 */       if (this._insets.top > 0) {
/* 52 */         g.setColor(this._shadow);
/* 53 */         g.drawLine(x, y, x + width, y);
/*    */       }
/* 55 */       if (this._insets.left > 0) {
/* 56 */         g.setColor(this._shadow);
/* 57 */         g.drawLine(x, y, x, y + height);
/*    */       }
/* 59 */       if (this._insets.bottom > 0) {
/* 60 */         g.setColor(this._shadow);
/* 61 */         g.drawLine(x, y + height - 1, x + width, y + height - 1);
/*    */       }
/* 63 */       if (this._insets.right > 0) {
/* 64 */         g.setColor(this._shadow);
/* 65 */         g.drawLine(x + width - 1, y, x + width - 1, y + height);
/*    */       }
/*    */     }
/*    */     else {
/* 69 */       if (this._insets.top > 0) {
/* 70 */         g.setColor(this._highlight);
/* 71 */         g.drawLine(x, y, x + width, y);
/* 72 */         g.setColor(this._lightHighlight);
/* 73 */         g.drawLine(x + 1, y + 1, x + width - 1, y + 1);
/*    */       }
/* 75 */       if (this._insets.left > 0) {
/* 76 */         g.setColor(this._highlight);
/* 77 */         g.drawLine(x, y, x, y + height);
/* 78 */         g.setColor(this._lightHighlight);
/* 79 */         g.drawLine(x + 1, y + 1, x + 1, y + height - 1);
/*    */       }
/* 81 */       if (this._insets.bottom > 0) {
/* 82 */         g.setColor(this._shadow);
/* 83 */         g.drawLine(x + 1, y + height - 2, x + width - 1, y + height - 2);
/* 84 */         g.setColor(this._darkShadow);
/* 85 */         g.drawLine(x, y + height - 1, x + width, y + height - 1);
/*    */       }
/* 87 */       if (this._insets.right > 0) {
/* 88 */         g.setColor(this._shadow);
/* 89 */         g.drawLine(x + width - 2, y + 1, x + width - 2, y + height - 2);
/* 90 */         g.setColor(this._darkShadow);
/* 91 */         g.drawLine(x + width - 1, y, x + width - 1, y + height);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.eclipse.RaisedBorder
 * JD-Core Version:    0.6.0
 */
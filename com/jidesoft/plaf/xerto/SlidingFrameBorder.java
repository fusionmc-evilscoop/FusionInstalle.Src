/*     */ package com.jidesoft.plaf.xerto;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.UIResource;
/*     */ 
/*     */ public class SlidingFrameBorder
/*     */   implements Border, UIResource
/*     */ {
/*     */   public static final int SHADOW_SIZE = 10;
/*     */   protected Color _highlight;
/*     */   protected Color _lightHighlight;
/*     */   protected Color _shadow;
/*     */   protected Color _darkShadow;
/*     */   protected Insets _insets;
/*  22 */   private static double LOG10 = Math.log(10.0D);
/*     */ 
/*     */   public SlidingFrameBorder(Color highlight, Color lightHighlight, Color shadow, Color darkShadow, Insets insets)
/*     */   {
/*  26 */     this._highlight = highlight;
/*  27 */     this._lightHighlight = lightHighlight;
/*  28 */     this._shadow = shadow;
/*  29 */     this._darkShadow = darkShadow;
/*  30 */     this._insets = insets;
/*     */   }
/*     */ 
/*     */   public Insets getBorderInsets(Component c)
/*     */   {
/*  39 */     return this._insets;
/*     */   }
/*     */ 
/*     */   public boolean isBorderOpaque()
/*     */   {
/*  47 */     return true;
/*     */   }
/*     */ 
/*     */   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
/*     */   {
/*  52 */     if (this._insets.top >= 10) {
/*  53 */       g.setColor(XertoUtils.getFrameBorderColor());
/*  54 */       g.drawRect(x, y + this._insets.top - 1, width, height - this._insets.top + 1);
/*     */ 
/*  56 */       g.setColor(this._highlight);
/*  57 */       g.drawLine(x, y + this._insets.top - 5, x + width, y + this._insets.top - 5);
/*  58 */       g.setColor(this._lightHighlight);
/*  59 */       g.drawLine(x, y + this._insets.top - 4, x + width, y + this._insets.top - 4);
/*  60 */       g.setColor(this._highlight);
/*  61 */       g.drawLine(x, y + this._insets.top - 3, x + width, y + this._insets.top - 3);
/*  62 */       g.drawLine(x, y + this._insets.top - 2, x + width, y + this._insets.top - 2);
/*     */ 
/*  64 */       paintGradient(g, new Rectangle(x, y, width, this._insets.top - 5), true, 40, false);
/*     */     }
/*     */ 
/*  67 */     if (this._insets.left >= 10) {
/*  68 */       g.setColor(XertoUtils.getFrameBorderColor());
/*  69 */       g.drawRect(x + this._insets.left - 1, y, width - this._insets.left, height);
/*     */ 
/*  71 */       g.setColor(this._highlight);
/*  72 */       g.drawLine(x + this._insets.left - 5, y, x + this._insets.left - 5, y + height);
/*  73 */       g.setColor(this._lightHighlight);
/*  74 */       g.drawLine(x + this._insets.left - 4, y, x + this._insets.left - 4, y + height);
/*  75 */       g.setColor(this._highlight);
/*  76 */       g.drawLine(x + this._insets.left - 3, y, x + this._insets.left - 3, y + height);
/*  77 */       g.drawLine(x + this._insets.left - 2, y, x + this._insets.left - 2, y + height);
/*     */ 
/*  79 */       paintGradient(g, new Rectangle(x, y, this._insets.left - 5, height), false, 40, false);
/*     */     }
/*     */ 
/*  82 */     if (this._insets.bottom >= 10) {
/*  83 */       g.setColor(XertoUtils.getFrameBorderColor());
/*  84 */       g.drawRect(x, y, width, height - this._insets.bottom);
/*     */ 
/*  86 */       g.setColor(this._highlight);
/*  87 */       g.drawLine(x, y + height - this._insets.bottom + 1, x + width, y + height - this._insets.bottom + 1);
/*  88 */       g.drawLine(x, y + height - this._insets.bottom + 2, x + width, y + height - this._insets.bottom + 2);
/*  89 */       g.setColor(this._shadow);
/*  90 */       g.drawLine(x, y + height - this._insets.bottom + 3, x + width, y + height - this._insets.bottom + 3);
/*  91 */       g.setColor(this._darkShadow);
/*  92 */       g.drawLine(x, y + height - this._insets.bottom + 4, x + width, y + height - this._insets.bottom + 4);
/*     */ 
/*  94 */       paintGradient(g, new Rectangle(x, y + height - this._insets.bottom + 4, width, this._insets.bottom - 5), true, 100, true);
/*     */     }
/*     */ 
/*  97 */     if (this._insets.right >= 10) {
/*  98 */       g.setColor(XertoUtils.getFrameBorderColor());
/*  99 */       g.drawRect(x, y, width - this._insets.right, height);
/*     */ 
/* 101 */       g.setColor(this._highlight);
/* 102 */       g.drawLine(x + width - this._insets.right + 1, y, x + width - this._insets.right + 1, y + height);
/* 103 */       g.drawLine(x + width - this._insets.right + 2, y, x + width - this._insets.right + 2, y + height);
/* 104 */       g.setColor(this._shadow);
/* 105 */       g.drawLine(x + width - this._insets.right + 3, y, x + width - this._insets.right + 3, y + height);
/* 106 */       g.setColor(this._darkShadow);
/* 107 */       g.drawLine(x + width - this._insets.right + 4, y, x + width - this._insets.right + 4, y + height);
/*     */ 
/* 109 */       paintGradient(g, new Rectangle(x + width - this._insets.right + 4, y, this._insets.right - 5, height), false, 100, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void paintGradient(Graphics g, Rectangle rect, boolean isVertical, int darkness, boolean lighter) {
/* 114 */     if (isVertical) {
/* 115 */       for (int i = 1; i < rect.height; i++) {
/* 116 */         int iAlpha = (int)((1.0D - Math.log(i) / LOG10) * darkness);
/* 117 */         g.setColor(new Color(0, 0, 0, iAlpha));
/* 118 */         if (lighter) {
/* 119 */           g.drawLine(rect.x, rect.y + i, rect.x + rect.width, rect.y + i);
/*     */         }
/*     */         else {
/* 122 */           g.drawLine(rect.x, rect.y + rect.height - i, rect.x + rect.width, rect.y + rect.height - i);
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/* 127 */       for (int i = 1; i < rect.width; i++) {
/* 128 */         int iAlpha = (int)((1.0D - Math.log(i) / LOG10) * darkness);
/* 129 */         g.setColor(new Color(0, 0, 0, iAlpha));
/* 130 */         if (lighter) {
/* 131 */           g.drawLine(rect.x + i, rect.y, rect.x + i, rect.y + rect.height);
/*     */         }
/*     */         else
/* 134 */           g.drawLine(rect.x + rect.width - i, rect.y, rect.x + rect.width - i, rect.y + rect.height);
/*     */       }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.xerto.SlidingFrameBorder
 * JD-Core Version:    0.6.0
 */
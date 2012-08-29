/*     */ package com.jidesoft.plaf.vsnet;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.UIResource;
/*     */ 
/*     */ public class ResizeFrameBorder
/*     */   implements Border, UIResource
/*     */ {
/*     */   protected Color _highlight;
/*     */   protected Color _lightHighlight;
/*     */   protected Color _shadow;
/*     */   protected Color _darkShadow;
/*     */   protected Insets _insets;
/*     */ 
/*     */   public ResizeFrameBorder(Color highlight, Color lightHighlight, Color shadow, Color darkShadow, Insets insets)
/*     */   {
/*  24 */     this._highlight = highlight;
/*  25 */     this._lightHighlight = lightHighlight;
/*  26 */     this._shadow = shadow;
/*  27 */     this._darkShadow = darkShadow;
/*  28 */     this._insets = insets;
/*     */   }
/*     */ 
/*     */   public Insets getBorderInsets(Component c)
/*     */   {
/*  37 */     return this._insets;
/*     */   }
/*     */ 
/*     */   public boolean isBorderOpaque()
/*     */   {
/*  46 */     return true;
/*     */   }
/*     */ 
/*     */   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
/*     */   {
/*  51 */     if (this._insets.top > 0) {
/*  52 */       g.setColor(this._highlight);
/*  53 */       g.drawLine(x, y, x + width, y);
/*     */ 
/*  55 */       g.setColor(this._lightHighlight);
/*  56 */       g.drawLine(x + 1, y + 1, x + width - 2, y + 1);
/*  57 */       if (this._insets.left == 0) {
/*  58 */         g.drawLine(x, y + 1, x + 1, y + 1);
/*     */       }
/*  60 */       if (this._insets.right == 0) {
/*  61 */         g.drawLine(x + width - 2, y + 1, x + width - 1, y + 1);
/*     */       }
/*     */ 
/*  64 */       g.setColor(this._highlight);
/*  65 */       g.drawLine(x + 2, y + 2, x + width - 4, y + 2);
/*  66 */       g.drawLine(x + 2, y + 3, x + width - 4, y + 3);
/*     */ 
/*  68 */       if (this._insets.left == 0) {
/*  69 */         g.drawLine(x, y + 2, x + 2, y + 2);
/*  70 */         g.drawLine(x, y + 3, x + 2, y + 3);
/*     */       }
/*  72 */       if (this._insets.right == 0) {
/*  73 */         g.drawLine(x + width - 4, y + 2, x + width - 1, y + 2);
/*  74 */         g.drawLine(x + width - 4, y + 3, x + width - 1, y + 3);
/*     */       }
/*     */     }
/*     */ 
/*  78 */     if (this._insets.left > 0) {
/*  79 */       g.setColor(this._highlight);
/*  80 */       g.drawLine(x, y, x, y + height);
/*     */ 
/*  82 */       g.setColor(this._lightHighlight);
/*  83 */       g.drawLine(x + 1, y + 1, x + 1, y + height - 2);
/*     */ 
/*  85 */       if (this._insets.top == 0) {
/*  86 */         g.drawLine(x + 1, y, x + 1, y + 1);
/*     */       }
/*  88 */       if (this._insets.bottom == 0) {
/*  89 */         g.drawLine(x + 1, y + height - 2, x + 1, y + height - 1);
/*     */       }
/*     */ 
/*  92 */       g.setColor(this._highlight);
/*  93 */       g.drawLine(x + 2, y + 2, x + 2, y + height - 4);
/*  94 */       g.drawLine(x + 3, y + 2, x + 3, y + height - 4);
/*  95 */       if (this._insets.top == 0) {
/*  96 */         g.drawLine(x + 2, y, x + 2, y + 2);
/*  97 */         g.drawLine(x + 3, y, x + 3, y + 2);
/*     */       }
/*  99 */       if (this._insets.bottom == 0) {
/* 100 */         g.drawLine(x + 2, y + height - 4, x + 2, y + height - 1);
/* 101 */         g.drawLine(x + 3, y + height - 4, x + 3, y + height - 1);
/*     */       }
/*     */     }
/*     */ 
/* 105 */     if (this._insets.bottom > 0) {
/* 106 */       g.setColor(this._darkShadow);
/* 107 */       g.drawLine(x, y + height - 1, x + width, y + height - 1);
/*     */ 
/* 109 */       g.setColor(this._shadow);
/* 110 */       g.drawLine(x + 1, y + height - 2, x + width - 2, y + height - 2);
/* 111 */       if (this._insets.left == 0) {
/* 112 */         g.drawLine(x, y + height - 2, x + 1, y + height - 2);
/*     */       }
/* 114 */       if (this._insets.right == 0) {
/* 115 */         g.drawLine(x + width - 2, y + height - 2, x + width - 1, y + height - 2);
/*     */       }
/*     */ 
/* 118 */       g.setColor(this._highlight);
/* 119 */       g.drawLine(x + 2, y + height - 3, x + width - 4, y + height - 3);
/* 120 */       g.drawLine(x + 2, y + height - 4, x + width - 4, y + height - 4);
/* 121 */       if (this._insets.left == 0) {
/* 122 */         g.drawLine(x, y + height - 3, x + 2, y + height - 3);
/* 123 */         g.drawLine(x, y + height - 4, x + 2, y + height - 4);
/*     */       }
/* 125 */       if (this._insets.right == 0) {
/* 126 */         g.drawLine(x + width - 4, y + height - 3, x + width - 1, y + height - 3);
/* 127 */         g.drawLine(x + width - 4, y + height - 4, x + width - 1, y + height - 4);
/*     */       }
/*     */     }
/*     */ 
/* 131 */     if (this._insets.right > 0) {
/* 132 */       g.setColor(this._darkShadow);
/* 133 */       g.drawLine(x + width - 1, y, x + width - 1, y + height);
/*     */ 
/* 135 */       g.setColor(this._shadow);
/* 136 */       g.drawLine(x + width - 2, y + 1, x + width - 2, y + height - 2);
/* 137 */       if (this._insets.top == 0) {
/* 138 */         g.drawLine(x + width - 2, y, x + width - 2, y + 1);
/*     */       }
/* 140 */       if (this._insets.bottom == 0) {
/* 141 */         g.drawLine(x + width - 2, y + height - 2, x + width - 2, y + height - 1);
/*     */       }
/*     */ 
/* 144 */       g.setColor(this._highlight);
/* 145 */       g.drawLine(x + width - 3, y + 2, x + width - 3, y + height - 4);
/* 146 */       g.drawLine(x + width - 4, y + 2, x + width - 4, y + height - 4);
/* 147 */       if (this._insets.top == 0) {
/* 148 */         g.drawLine(x + width - 3, y, x + width - 3, y + 2);
/* 149 */         g.drawLine(x + width - 4, y, x + width - 4, y + 2);
/*     */       }
/* 151 */       if (this._insets.bottom == 0) {
/* 152 */         g.drawLine(x + width - 3, y + height - 4, x + width - 3, y + height - 1);
/* 153 */         g.drawLine(x + width - 4, y + height - 4, x + width - 4, y + height - 1);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.vsnet.ResizeFrameBorder
 * JD-Core Version:    0.6.0
 */
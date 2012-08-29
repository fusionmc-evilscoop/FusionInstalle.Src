/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.border.EtchedBorder;
/*     */ 
/*     */ public class PartialEtchedBorder extends EtchedBorder
/*     */   implements PartialSide
/*     */ {
/*     */   private int _sides;
/*     */ 
/*     */   public PartialEtchedBorder()
/*     */   {
/*  13 */     this(15);
/*     */   }
/*     */ 
/*     */   public PartialEtchedBorder(int sides) {
/*  17 */     this._sides = sides;
/*     */   }
/*     */ 
/*     */   public PartialEtchedBorder(int etchType, int sides) {
/*  21 */     super(etchType);
/*  22 */     this._sides = sides;
/*     */   }
/*     */ 
/*     */   public PartialEtchedBorder(Color highlight, Color shadow, int sides) {
/*  26 */     super(highlight, shadow);
/*  27 */     this._sides = sides;
/*     */   }
/*     */ 
/*     */   public PartialEtchedBorder(int etchType, Color highlight, Color shadow, int sides) {
/*  31 */     super(etchType, highlight, shadow);
/*  32 */     this._sides = sides;
/*     */   }
/*     */ 
/*     */   public int getSides() {
/*  36 */     return this._sides;
/*     */   }
/*     */ 
/*     */   public void setSides(int sides) {
/*  40 */     this._sides = sides;
/*     */   }
/*     */ 
/*     */   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
/*     */   {
/*  45 */     int w = width;
/*  46 */     int h = height;
/*     */ 
/*  48 */     g.translate(x, y);
/*     */ 
/*  50 */     if (c.getBackground() == null) {
/*  51 */       c.setBackground(Color.GRAY);
/*     */     }
/*     */ 
/*  54 */     Color shadowColor = getShadowColor(c);
/*  55 */     Color highlightColor = getHighlightColor(c);
/*     */ 
/*  57 */     if (this._sides == 15) {
/*  58 */       g.setColor(this.etchType == 1 ? shadowColor : highlightColor);
/*  59 */       g.drawRect(0, 0, w - 2, h - 2);
/*     */ 
/*  61 */       g.setColor(this.etchType == 1 ? highlightColor : shadowColor);
/*  62 */       g.drawLine(1, h - 3, 1, 1);
/*  63 */       g.drawLine(1, 1, w - 3, 1);
/*     */ 
/*  65 */       g.drawLine(0, h - 1, w - 1, h - 1);
/*  66 */       g.drawLine(w - 1, h - 1, w - 1, 0);
/*     */     }
/*     */     else {
/*  69 */       if ((this._sides & 0x1) != 0) {
/*  70 */         g.setColor(this.etchType == 1 ? shadowColor : highlightColor);
/*  71 */         g.drawLine(0, 0, w - 2, 0);
/*  72 */         g.setColor(this.etchType == 1 ? highlightColor : shadowColor);
/*  73 */         g.drawLine(1, 1, w - 2, 1);
/*     */       }
/*  75 */       if ((this._sides & 0x2) != 0) {
/*  76 */         g.setColor(this.etchType == 1 ? shadowColor : highlightColor);
/*  77 */         g.drawLine(0, h - 2, w - 1, h - 2);
/*  78 */         g.setColor(this.etchType == 1 ? highlightColor : shadowColor);
/*  79 */         g.drawLine(0, h - 1, w - 1, h - 1);
/*     */       }
/*  81 */       if ((this._sides & 0x8) != 0) {
/*  82 */         g.setColor(this.etchType == 1 ? shadowColor : highlightColor);
/*  83 */         g.drawLine(0, h - 2, 0, 0);
/*  84 */         g.setColor(this.etchType == 1 ? highlightColor : shadowColor);
/*  85 */         g.drawLine(1, h - 3, 1, 1);
/*     */       }
/*  87 */       if ((this._sides & 0x4) != 0) {
/*  88 */         g.setColor(this.etchType == 1 ? shadowColor : highlightColor);
/*  89 */         g.drawLine(w - 2, h - 2, w - 2, 0);
/*  90 */         g.setColor(this.etchType == 1 ? highlightColor : shadowColor);
/*  91 */         g.drawLine(w - 1, h - 1, w - 1, 0);
/*     */       }
/*     */     }
/*  94 */     g.translate(-x, -y);
/*     */   }
/*     */ 
/*     */   public Insets getBorderInsets(Component c)
/*     */   {
/*  99 */     Insets borderInsets = super.getBorderInsets(c);
/* 100 */     if ((this._sides & 0x1) == 0) {
/* 101 */       borderInsets.top = 0;
/*     */     }
/* 103 */     if ((this._sides & 0x2) == 0) {
/* 104 */       borderInsets.bottom = 0;
/*     */     }
/* 106 */     if ((this._sides & 0x8) == 0) {
/* 107 */       borderInsets.left = 0;
/*     */     }
/* 109 */     if ((this._sides & 0x4) == 0) {
/* 110 */       borderInsets.right = 0;
/*     */     }
/* 112 */     return borderInsets;
/*     */   }
/*     */ 
/*     */   public Insets getBorderInsets(Component c, Insets insets)
/*     */   {
/* 117 */     Insets borderInsets = super.getBorderInsets(c, insets);
/* 118 */     if ((this._sides & 0x1) == 0) {
/* 119 */       borderInsets.top = 0;
/*     */     }
/* 121 */     if ((this._sides & 0x2) == 0) {
/* 122 */       borderInsets.bottom = 0;
/*     */     }
/* 124 */     if ((this._sides & 0x8) == 0) {
/* 125 */       borderInsets.left = 0;
/*     */     }
/* 127 */     if ((this._sides & 0x4) == 0) {
/* 128 */       borderInsets.right = 0;
/*     */     }
/* 130 */     return borderInsets;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.PartialEtchedBorder
 * JD-Core Version:    0.6.0
 */
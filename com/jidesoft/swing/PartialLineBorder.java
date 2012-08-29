/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.border.LineBorder;
/*     */ 
/*     */ public class PartialLineBorder extends LineBorder
/*     */   implements PartialSide
/*     */ {
/*  12 */   private int _sides = 15;
/*  13 */   private int _roundedCornerSize = 5;
/*     */ 
/*     */   public PartialLineBorder(Color color) {
/*  16 */     super(color);
/*     */   }
/*     */ 
/*     */   public PartialLineBorder(Color color, int thickness) {
/*  20 */     super(color, thickness);
/*     */   }
/*     */ 
/*     */   public PartialLineBorder(Color color, int thickness, boolean roundedCorners) {
/*  24 */     super(color, thickness, roundedCorners);
/*     */   }
/*     */ 
/*     */   public PartialLineBorder(Color color, int thickness, boolean roundedCorners, int roundedCornerSize) {
/*  28 */     super(color, thickness, roundedCorners);
/*  29 */     this._roundedCornerSize = roundedCornerSize;
/*     */   }
/*     */ 
/*     */   public PartialLineBorder(Color color, int thickness, int side) {
/*  33 */     super(color, thickness);
/*  34 */     this._sides = side;
/*     */   }
/*     */ 
/*     */   public int getSides() {
/*  38 */     return this._sides;
/*     */   }
/*     */ 
/*     */   public void setSides(int sides) {
/*  42 */     this._sides = sides;
/*     */   }
/*     */ 
/*     */   public int getRoundedCornerSize() {
/*  46 */     return this._roundedCornerSize;
/*     */   }
/*     */ 
/*     */   public void setRoundedCornerSize(int roundedCornerSize) {
/*  50 */     this._roundedCornerSize = roundedCornerSize;
/*     */   }
/*     */ 
/*     */   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
/*     */   {
/*  55 */     Color oldColor = g.getColor();
/*     */ 
/*  58 */     g.setColor(this.lineColor);
/*  59 */     for (int i = 0; i < this.thickness; i++) {
/*  60 */       if (this._sides == 15) {
/*  61 */         if (!this.roundedCorners) {
/*  62 */           g.drawRect(x + i, y + i, width - i - i - 1, height - i - i - 1);
/*     */         } else {
/*  64 */           Object o = JideSwingUtilities.setupShapeAntialiasing(g);
/*  65 */           g.drawRoundRect(x + i, y + i, width - i - i - 1, height - i - i - 1, this._roundedCornerSize, this._roundedCornerSize);
/*  66 */           JideSwingUtilities.restoreShapeAntialiasing(g, o);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*  71 */         if ((this._sides & 0x1) != 0) {
/*  72 */           g.drawLine(x, y + i, x + width - 1, y + i);
/*     */         }
/*  74 */         if ((this._sides & 0x2) != 0) {
/*  75 */           g.drawLine(x, y + height - i - 1, x + width - 1, y + height - i - 1);
/*     */         }
/*  77 */         if ((this._sides & 0x8) != 0) {
/*  78 */           g.drawLine(x + i, y, x + i, y + height - 1);
/*     */         }
/*  80 */         if ((this._sides & 0x4) != 0) {
/*  81 */           g.drawLine(x + width - i - 1, y, x + width - i - 1, y + height - 1);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  86 */     g.setColor(oldColor);
/*     */   }
/*     */ 
/*     */   public Insets getBorderInsets(Component c)
/*     */   {
/*  91 */     Insets borderInsets = super.getBorderInsets(c);
/*  92 */     if ((this._sides & 0x1) == 0) {
/*  93 */       borderInsets.top = 0;
/*     */     }
/*  95 */     if ((this._sides & 0x2) == 0) {
/*  96 */       borderInsets.bottom = 0;
/*     */     }
/*  98 */     if ((this._sides & 0x8) == 0) {
/*  99 */       borderInsets.left = 0;
/*     */     }
/* 101 */     if ((this._sides & 0x4) == 0) {
/* 102 */       borderInsets.right = 0;
/*     */     }
/* 104 */     return borderInsets;
/*     */   }
/*     */ 
/*     */   public Insets getBorderInsets(Component c, Insets insets)
/*     */   {
/* 109 */     Insets borderInsets = super.getBorderInsets(c, insets);
/* 110 */     if ((this._sides & 0x1) == 0) {
/* 111 */       borderInsets.top = 0;
/*     */     }
/* 113 */     if ((this._sides & 0x2) == 0) {
/* 114 */       borderInsets.bottom = 0;
/*     */     }
/* 116 */     if ((this._sides & 0x8) == 0) {
/* 117 */       borderInsets.left = 0;
/*     */     }
/* 119 */     if ((this._sides & 0x4) == 0) {
/* 120 */       borderInsets.right = 0;
/*     */     }
/* 122 */     return borderInsets;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.PartialLineBorder
 * JD-Core Version:    0.6.0
 */
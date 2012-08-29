/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.border.AbstractBorder;
/*     */ 
/*     */ public class PartialGradientLineBorder extends AbstractBorder
/*     */   implements PartialSide
/*     */ {
/*  16 */   private int _sides = 15;
/*     */   private Color[] _colors;
/*     */   protected int _thickness;
/*     */ 
/*     */   public PartialGradientLineBorder(Color[] colors)
/*     */   {
/*  21 */     this(colors, 1);
/*     */   }
/*     */ 
/*     */   public PartialGradientLineBorder(Color[] colors, int thickness) {
/*  25 */     this(colors, thickness, 15);
/*     */   }
/*     */ 
/*     */   public PartialGradientLineBorder(Color[] colors, int thickness, int sides) {
/*  29 */     if (colors.length < 2) {
/*  30 */       throw new IllegalArgumentException("Array \"colors\" should have at least 2 elements.");
/*     */     }
/*  32 */     this._colors = colors;
/*  33 */     this._thickness = thickness;
/*  34 */     this._sides = sides;
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
/*     */   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
/*     */   {
/*  47 */     Color oldColor = g.getColor();
/*  48 */     Graphics2D g2d = (Graphics2D)g;
/*  49 */     int i = 0;
/*  50 */     if ((this._sides & 0x1) != 0) {
/*  51 */       JideSwingUtilities.fillGradient(g2d, new Rectangle(x, y, width, this._thickness), this._colors[(i++)], this._colors[(i++)], false);
/*     */     }
/*  53 */     if ((this._sides & 0x2) != 0) {
/*  54 */       if (i >= this._colors.length) {
/*  55 */         i -= 2;
/*     */       }
/*  57 */       JideSwingUtilities.fillGradient(g2d, new Rectangle(x, y + height - this._thickness, width, this._thickness), this._colors[(i++)], this._colors[(i++)], false);
/*     */     }
/*  59 */     if ((this._sides & 0x8) != 0) {
/*  60 */       if (i >= this._colors.length) {
/*  61 */         i -= 2;
/*     */       }
/*  63 */       JideSwingUtilities.fillGradient(g2d, new Rectangle(x, y, this._thickness, height), this._colors[(i++)], this._colors[(i++)], true);
/*     */     }
/*  65 */     if ((this._sides & 0x4) != 0) {
/*  66 */       if (i >= this._colors.length) {
/*  67 */         i -= 2;
/*     */       }
/*  69 */       JideSwingUtilities.fillGradient(g2d, new Rectangle(x + width - this._thickness, y, this._thickness, height), this._colors[(i++)], this._colors[i], true);
/*     */     }
/*  71 */     g.setColor(oldColor);
/*     */   }
/*     */ 
/*     */   public Insets getBorderInsets(Component c)
/*     */   {
/*  76 */     Insets borderInsets = super.getBorderInsets(c);
/*  77 */     if ((this._sides & 0x1) == 0) {
/*  78 */       borderInsets.top = 0;
/*     */     }
/*  80 */     if ((this._sides & 0x2) == 0) {
/*  81 */       borderInsets.bottom = 0;
/*     */     }
/*  83 */     if ((this._sides & 0x8) == 0) {
/*  84 */       borderInsets.left = 0;
/*     */     }
/*  86 */     if ((this._sides & 0x4) == 0) {
/*  87 */       borderInsets.right = 0;
/*     */     }
/*  89 */     return borderInsets;
/*     */   }
/*     */ 
/*     */   public Insets getBorderInsets(Component c, Insets insets)
/*     */   {
/*  94 */     Insets borderInsets = super.getBorderInsets(c, insets);
/*  95 */     if ((this._sides & 0x1) == 0) {
/*  96 */       borderInsets.top = 0;
/*     */     }
/*  98 */     if ((this._sides & 0x2) == 0) {
/*  99 */       borderInsets.bottom = 0;
/*     */     }
/* 101 */     if ((this._sides & 0x8) == 0) {
/* 102 */       borderInsets.left = 0;
/*     */     }
/* 104 */     if ((this._sides & 0x4) == 0) {
/* 105 */       borderInsets.right = 0;
/*     */     }
/* 107 */     return borderInsets;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.PartialGradientLineBorder
 * JD-Core Version:    0.6.0
 */
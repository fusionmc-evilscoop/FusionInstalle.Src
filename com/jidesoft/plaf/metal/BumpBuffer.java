/*     */ package com.jidesoft.plaf.metal;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Image;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.IndexColorModel;
/*     */ 
/*     */ class BumpBuffer
/*     */ {
/*     */   static final int IMAGE_SIZE = 64;
/* 146 */   static Dimension imageSize = new Dimension(64, 64);
/*     */   transient Image image;
/*     */   Color topColor;
/*     */   Color shadowColor;
/*     */   Color backColor;
/*     */   private GraphicsConfiguration gc;
/*     */ 
/*     */   public BumpBuffer(GraphicsConfiguration gc, Color aTopColor, Color aShadowColor, Color aBackColor)
/*     */   {
/* 156 */     this.gc = gc;
/* 157 */     this.topColor = aTopColor;
/* 158 */     this.shadowColor = aShadowColor;
/* 159 */     this.backColor = aBackColor;
/* 160 */     createImage();
/* 161 */     fillBumpBuffer();
/*     */   }
/*     */ 
/*     */   public boolean hasSameConfiguration(GraphicsConfiguration gc, Color aTopColor, Color aShadowColor, Color aBackColor)
/*     */   {
/* 167 */     if (this.gc != null) {
/* 168 */       if (!this.gc.equals(gc)) {
/* 169 */         return false;
/*     */       }
/*     */     }
/* 172 */     else if (gc != null) {
/* 173 */       return false;
/*     */     }
/*     */ 
/* 176 */     if (this.backColor != null ? !this.backColor.equals(aBackColor) : aBackColor != null) return false;
/* 177 */     if (this.shadowColor != null ? !this.shadowColor.equals(aShadowColor) : aShadowColor != null) return false;
/* 178 */     return this.topColor != null ? this.topColor.equals(aTopColor) : aTopColor == null;
/*     */   }
/*     */ 
/*     */   public Image getImage()
/*     */   {
/* 188 */     return this.image;
/*     */   }
/*     */ 
/*     */   public Dimension getImageSize() {
/* 192 */     return imageSize;
/*     */   }
/*     */ 
/*     */   private void fillBumpBuffer()
/*     */   {
/* 199 */     Graphics g = this.image.getGraphics();
/*     */ 
/* 201 */     g.setColor(this.backColor);
/* 202 */     g.fillRect(0, 0, 64, 64);
/*     */ 
/* 204 */     g.setColor(this.topColor);
/* 205 */     for (int x = 0; x < 64; x += 4) {
/* 206 */       for (int y = 0; y < 64; y += 4) {
/* 207 */         g.drawLine(x, y, x, y);
/* 208 */         g.drawLine(x + 2, y + 2, x + 2, y + 2);
/*     */       }
/*     */     }
/*     */ 
/* 212 */     g.setColor(this.shadowColor);
/* 213 */     for (int x = 0; x < 64; x += 4) {
/* 214 */       for (int y = 0; y < 64; y += 4) {
/* 215 */         g.drawLine(x + 1, y + 1, x + 1, y + 1);
/* 216 */         g.drawLine(x + 3, y + 3, x + 3, y + 3);
/*     */       }
/*     */     }
/* 219 */     g.dispose();
/*     */   }
/*     */ 
/*     */   private void createImage()
/*     */   {
/* 227 */     if (this.gc != null) {
/* 228 */       this.image = this.gc.createCompatibleImage(64, 64, this.backColor != MetalBumps.ALPHA ? 1 : 2);
/*     */     }
/*     */     else
/*     */     {
/* 233 */       int[] cmap = { this.backColor.getRGB(), this.topColor.getRGB(), this.shadowColor.getRGB() };
/*     */ 
/* 235 */       IndexColorModel icm = new IndexColorModel(8, 3, cmap, 0, false, this.backColor == MetalBumps.ALPHA ? 0 : -1, 0);
/*     */ 
/* 238 */       this.image = new BufferedImage(64, 64, 13, icm);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.metal.BumpBuffer
 * JD-Core Version:    0.6.0
 */
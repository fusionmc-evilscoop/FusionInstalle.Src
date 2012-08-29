/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.GradientPaint;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.awt.image.BufferedImage;
/*     */ 
/*     */ class FastGradientPainter
/*     */ {
/*  14 */   private static GradientCache gradientCache = new GradientCache();
/*     */ 
/*     */   public static void clearGradientCache()
/*     */   {
/*  24 */     gradientCache.clear();
/*     */   }
/*     */ 
/*     */   public static void drawGradient(Graphics2D g2, Shape s, Color startColor, Color endColor, boolean isVertical)
/*     */   {
/*  39 */     Rectangle r = s.getBounds();
/*  40 */     if ((r.height <= 0) || (r.width <= 0)) return;
/*     */ 
/*  42 */     int length = isVertical ? r.height : r.width;
/*  43 */     GradientInfo info = new GradientInfo(g2.getDeviceConfiguration(), length, startColor, endColor, isVertical);
/*     */ 
/*  45 */     BufferedImage gradient = gradientCache.retrieve(info);
/*  46 */     if (gradient == null) {
/*  47 */       gradient = createGradientTile(info);
/*  48 */       gradientCache.store(info, gradient);
/*     */     }
/*     */ 
/*  51 */     Shape prevClip = null;
/*  52 */     boolean nonRectangular = false;
/*  53 */     if (!r.equals(s)) {
/*  54 */       nonRectangular = true;
/*  55 */       prevClip = g2.getClip();
/*  56 */       g2.clip(s);
/*     */     }
/*  58 */     if (isVertical) {
/*  59 */       int w = gradient.getWidth();
/*  60 */       int loops = r.width / w;
/*  61 */       for (int i = 0; i < loops; i++)
/*  62 */         g2.drawImage(gradient, r.x + i * w, r.y, null);
/*  63 */       int rem = r.width % w;
/*  64 */       if (rem > 0)
/*  65 */         g2.drawImage(gradient, r.x + loops * w, r.y, r.x + loops * w + rem, r.y + length, 0, 0, rem, length, null);
/*     */     }
/*     */     else
/*     */     {
/*  69 */       int h = gradient.getHeight();
/*  70 */       int loops = r.height / h;
/*  71 */       for (int i = 0; i < loops; i++)
/*  72 */         g2.drawImage(gradient, r.x, r.y + i * h, null);
/*  73 */       int rem = r.height % h;
/*  74 */       if (rem > 0) {
/*  75 */         g2.drawImage(gradient, r.x, r.y + loops * h, r.x + length, r.y + loops * h + rem, 0, 0, length, rem, null);
/*     */       }
/*     */     }
/*  78 */     if (nonRectangular)
/*  79 */       g2.setClip(prevClip);
/*     */   }
/*     */ 
/*     */   private static BufferedImage createGradientTile(GradientInfo info)
/*     */   {
/*  84 */     boolean t = (info.startColor.getTransparency() > 1) || (info.endColor.getTransparency() > 1);
/*     */     int w;
/*     */     int dx;
/*     */     int w;
/*     */     int dy;
/*     */     int h;
/*  87 */     if (info.isVertical) {
/*  88 */       int dx = 0;
/*     */       int dy;
/*  89 */       int h = dy = info.length;
/*  90 */       w = 32;
/*     */     }
/*     */     else {
/*  93 */       w = dx = info.length;
/*  94 */       dy = 0;
/*  95 */       h = 32;
/*     */     }
/*  97 */     BufferedImage img = info.gfxConfig.createCompatibleImage(w, h, t ? 3 : 1);
/*  98 */     Paint gp = new GradientPaint(0.0F, 0.0F, info.startColor, dx, dy, info.endColor);
/*     */ 
/* 100 */     Graphics2D g = img.createGraphics();
/* 101 */     g.setPaint(gp);
/* 102 */     g.fillRect(0, 0, w, h);
/* 103 */     g.dispose();
/* 104 */     return img;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.FastGradientPainter
 * JD-Core Version:    0.6.0
 */
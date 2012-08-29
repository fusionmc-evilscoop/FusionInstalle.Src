/*     */ package com.jidesoft.icons;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.image.FilteredImageSource;
/*     */ import java.awt.image.ImageProducer;
/*     */ import java.awt.image.RGBImageFilter;
/*     */ 
/*     */ public class ColorFilter extends RGBImageFilter
/*     */ {
/*     */   private boolean _brighter;
/*  18 */   private static int _percent = 30;
/*     */   private static ColorFilter _colorFilter;
/*     */ 
/*     */   public static ColorFilter getInstance(boolean brighter, int percent)
/*     */   {
/*  22 */     if (_colorFilter == null) {
/*  23 */       _colorFilter = new ColorFilter(brighter, percent);
/*     */     }
/*     */     else {
/*  26 */       _colorFilter.setBrighter(brighter);
/*  27 */       setPercent(percent);
/*     */     }
/*  29 */     return _colorFilter;
/*     */   }
/*     */ 
/*     */   public void setBrighter(boolean brighter) {
/*  33 */     this._brighter = brighter;
/*     */   }
/*     */ 
/*     */   public static void setPercent(int percent) {
/*  37 */     _percent = percent;
/*     */   }
/*     */ 
/*     */   public static Image createBrighterImage(Image i)
/*     */   {
/*  48 */     ColorFilter filter = getInstance(true, _percent);
/*  49 */     ImageProducer prod = new FilteredImageSource(i.getSource(), filter);
/*  50 */     return Toolkit.getDefaultToolkit().createImage(prod);
/*     */   }
/*     */ 
/*     */   public static Image createBrighterImage(Image i, int p)
/*     */   {
/*  62 */     setPercent(p);
/*  63 */     return createBrighterImage(i);
/*     */   }
/*     */ 
/*     */   public static Image createDarkerImage(Image i)
/*     */   {
/*  75 */     ColorFilter filter = getInstance(false, _percent);
/*  76 */     ImageProducer prod = new FilteredImageSource(i.getSource(), filter);
/*  77 */     return Toolkit.getDefaultToolkit().createImage(prod);
/*     */   }
/*     */ 
/*     */   public static Image createDarkerImage(Image i, int p)
/*     */   {
/*  90 */     setPercent(p);
/*  91 */     return createDarkerImage(i);
/*     */   }
/*     */ 
/*     */   public ColorFilter(boolean b, int p)
/*     */   {
/* 105 */     this._brighter = b;
/* 106 */     _percent = p;
/* 107 */     this.canFilterIndexColorModel = true;
/*     */   }
/*     */ 
/*     */   public int filterRGB(int x, int y, int rgb)
/*     */   {
/* 115 */     int r = rgb >> 16 & 0xFF;
/* 116 */     int g = rgb >> 8 & 0xFF;
/* 117 */     int b = rgb & 0xFF;
/*     */ 
/* 119 */     return rgb & 0xFF000000 | convert(r) << 16 | convert(g) << 8 | convert(b);
/*     */   }
/*     */ 
/*     */   private int convert(int color) {
/* 123 */     if (this._brighter)
/* 124 */       color += (255 - color) * _percent / 100;
/*     */     else {
/* 126 */       color -= (255 - color) * _percent / 100;
/*     */     }
/* 128 */     if (color < 0) color = 0;
/* 129 */     if (color > 255) color = 255;
/* 130 */     return color;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.icons.ColorFilter
 * JD-Core Version:    0.6.0
 */
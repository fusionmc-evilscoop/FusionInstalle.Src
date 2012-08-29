/*     */ package com.jidesoft.converter;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class RgbColorConverter extends ColorConverter
/*     */ {
/*  20 */   private boolean _alphaIncluded = false;
/*     */ 
/*     */   public RgbColorConverter()
/*     */   {
/*     */   }
/*     */ 
/*     */   public RgbColorConverter(boolean alphaIncluded)
/*     */   {
/*  34 */     this._alphaIncluded = alphaIncluded;
/*     */   }
/*     */ 
/*     */   public boolean isAlphaIncluded()
/*     */   {
/*  48 */     return this._alphaIncluded;
/*     */   }
/*     */ 
/*     */   public void setAlphaIncluded(boolean alphaIncluded)
/*     */   {
/*  59 */     this._alphaIncluded = alphaIncluded;
/*     */   }
/*     */ 
/*     */   public String toString(Object object, ConverterContext context) {
/*  63 */     if ((object instanceof Color)) {
/*  64 */       Color color = (Color)object;
/*  65 */       StringBuffer colorText = new StringBuffer();
/*  66 */       colorText.append(color.getRed()).append(", ");
/*  67 */       colorText.append(color.getGreen()).append(", ");
/*  68 */       colorText.append(color.getBlue());
/*  69 */       if (isAlphaIncluded()) {
/*  70 */         colorText.append(", ").append(color.getAlpha());
/*     */       }
/*  72 */       return new String(colorText);
/*     */     }
/*     */ 
/*  75 */     return "";
/*     */   }
/*     */ 
/*     */   public boolean supportToString(Object object, ConverterContext context)
/*     */   {
/*  80 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean supportFromString(String string, ConverterContext context) {
/*  84 */     return true;
/*     */   }
/*     */ 
/*     */   public Object fromString(String string, ConverterContext context) {
/*  88 */     if ((string == null) || (string.trim().length() == 0)) {
/*  89 */       return null;
/*     */     }
/*  91 */     StringTokenizer token = new StringTokenizer(string, ",; ");
/*  92 */     int r = 0; int g = 0; int b = 0; int a = 255;
/*  93 */     if (token.hasMoreTokens()) {
/*  94 */       String s = token.nextToken();
/*     */       try {
/*  96 */         r = Integer.parseInt(s, 10) % 256;
/*  97 */         if (r < 0)
/*  98 */           r += 256;
/*     */       }
/*     */       catch (NumberFormatException e)
/*     */       {
/* 102 */         return null;
/*     */       }
/*     */     }
/* 105 */     if (token.hasMoreTokens()) {
/* 106 */       String s = token.nextToken();
/*     */       try {
/* 108 */         g = Integer.parseInt(s, 10) % 256;
/* 109 */         if (g < 0)
/* 110 */           g += 256;
/*     */       }
/*     */       catch (NumberFormatException e)
/*     */       {
/* 114 */         return null;
/*     */       }
/*     */     }
/* 117 */     if (token.hasMoreTokens()) {
/* 118 */       String s = token.nextToken();
/*     */       try {
/* 120 */         b = Integer.parseInt(s, 10) % 256;
/* 121 */         if (b < 0)
/* 122 */           b += 256;
/*     */       }
/*     */       catch (NumberFormatException e)
/*     */       {
/* 126 */         return null;
/*     */       }
/*     */     }
/* 129 */     if ((isAlphaIncluded()) && (token.hasMoreTokens())) {
/* 130 */       String s = token.nextToken();
/*     */       try {
/* 132 */         a = Integer.parseInt(s, 10) % 256;
/* 133 */         if (a < 0)
/* 134 */           a += 256;
/*     */       }
/*     */       catch (NumberFormatException e)
/*     */       {
/* 138 */         return null;
/*     */       }
/*     */     }
/*     */ 
/* 142 */     return new Color(r, g, b, a);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.RgbColorConverter
 * JD-Core Version:    0.6.0
 */
/*     */ package com.jidesoft.utils;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ 
/*     */ public class ColorUtils
/*     */ {
/*     */   static final float OFFSET_180 = 180.0F;
/*     */   static final float OFFSET_100 = 100.0F;
/*     */ 
/*     */   public static Color getDerivedColor(Color color, float ratio)
/*     */   {
/*  27 */     if (color != null) {
/*  28 */       float[] hsl = RGBtoHSL(color);
/*  29 */       if (hsl[2] < 0.4D) {
/*  30 */         hsl[2] = 0.4F;
/*     */       }
/*  32 */       if (ratio > 0.5D)
/*     */       {
/*     */         int tmp36_35 = 2;
/*     */         float[] tmp36_34 = hsl; tmp36_34[tmp36_35] = (float)(tmp36_34[tmp36_35] + (1.0F - hsl[2]) * 2.0F * (ratio - 0.5D));
/*     */       }
/*     */       else
/*     */       {
/*     */         int tmp62_61 = 2;
/*     */         float[] tmp62_60 = hsl; tmp62_60[tmp62_61] = (float)(tmp62_60[tmp62_61] - hsl[2] * 2.0F * (0.5D - ratio));
/*     */       }
/*  38 */       int colorRGB = HSLtoRGB(hsl);
/*  39 */       return new ColorUIResource(colorRGB);
/*     */     }
/*     */ 
/*  42 */     return null;
/*     */   }
/*     */ 
/*     */   public static float[] RGBtoHSL(Color colorRGB)
/*     */   {
/*  54 */     float r = colorRGB.getRed() / 256.0F;
/*  55 */     float g = colorRGB.getGreen() / 256.0F;
/*  56 */     float b = colorRGB.getBlue() / 256.0F;
/*     */ 
/*  62 */     float maxColor = Math.max(r, Math.max(g, b));
/*  63 */     float minColor = Math.min(r, Math.min(g, b));
/*     */     float l;
/*     */     float l;
/*     */     float s;
/*     */     float h;
/*  70 */     if ((r == g) && (g == b)) {
/*  71 */       float h = 0.0F;
/*  72 */       float s = 0.0F;
/*  73 */       l = r;
/*     */     }
/*     */     else
/*     */     {
/*  83 */       l = (minColor + maxColor) / 2.0F;
/*     */       float s;
/*  85 */       if (l < 0.5D) s = (maxColor - minColor) / (maxColor + minColor); else
/*  86 */         s = (maxColor - minColor) / (2.0F - maxColor - minColor);
/*  88 */       float h;
/*  88 */       if (r == maxColor) { h = (g - b) / (maxColor - minColor);
/*     */       }
/*     */       else
/*     */       {
/*  89 */         float h;
/*  89 */         if (g == maxColor) h = 2.0F + (b - r) / (maxColor - minColor); else
/*  90 */           h = 4.0F + (r - g) / (maxColor - minColor);
/*     */       }
/*  92 */       h /= 6.0F;
/*  93 */       if (h < 0.0F) h += 1.0F;
/*     */ 
/*     */     }
/*     */ 
/*  99 */     float[] hsl = new float[3];
/* 100 */     hsl[0] = h;
/* 101 */     hsl[1] = s;
/* 102 */     hsl[2] = l;
/* 103 */     return hsl;
/*     */   }
/*     */ 
/*     */   public static int HSLtoRGB(float[] hsl)
/*     */   {
/* 115 */     float h = hsl[0];
/* 116 */     float s = hsl[1];
/* 117 */     float l = hsl[2];
/*     */     float r;
/*     */     float r;
/*     */     float g;
/*     */     float b;
/* 123 */     if (s == 0.0F)
/*     */     {
/*     */       float b;
/*     */       float g;
/* 124 */       r = g = b = l;
/*     */     }
/*     */     else
/*     */     {
/* 131 */       float temp2;
/*     */       float temp2;
/* 131 */       if (l < 0.5D) temp2 = l * (1.0F + s); else
/* 132 */         temp2 = l + s - l * s;
/* 133 */       float temp1 = 2.0F * l - temp2;
/* 134 */       float tempr = h + 0.3333333F;
/* 135 */       if (tempr > 1.0F) tempr -= 1.0F;
/* 136 */       float tempg = h;
/* 137 */       float tempb = h - 0.3333333F;
/* 138 */       if (tempb < 0.0F) tempb += 1.0F;
/* 141 */       float r;
/* 141 */       if (tempr < 0.1666666666666667D) { r = temp1 + (temp2 - temp1) * 6.0F * tempr;
/*     */       }
/*     */       else
/*     */       {
/* 142 */         float r;
/* 142 */         if (tempr < 0.5D) { r = temp2;
/*     */         }
/*     */         else
/*     */         {
/* 143 */           float r;
/* 143 */           if (tempr < 0.6666666666666666D) r = temp1 + (temp2 - temp1) * (0.6666667F - tempr) * 6.0F; else
/* 144 */             r = temp1;
/*     */         }
/*     */       }
/* 147 */       float g;
/* 147 */       if (tempg < 0.1666666666666667D) { g = temp1 + (temp2 - temp1) * 6.0F * tempg;
/*     */       }
/*     */       else
/*     */       {
/* 148 */         float g;
/* 148 */         if (tempg < 0.5D) { g = temp2;
/*     */         }
/*     */         else
/*     */         {
/* 149 */           float g;
/* 149 */           if (tempg < 0.6666666666666666D) g = temp1 + (temp2 - temp1) * (0.6666667F - tempg) * 6.0F; else
/* 150 */             g = temp1;
/*     */         }
/*     */       }
/* 153 */       float b;
/* 153 */       if (tempb < 0.1666666666666667D) { b = temp1 + (temp2 - temp1) * 6.0F * tempb;
/*     */       }
/*     */       else
/*     */       {
/* 154 */         float b;
/* 154 */         if (tempb < 0.5D) { b = temp2;
/*     */         }
/*     */         else
/*     */         {
/* 155 */           float b;
/* 155 */           if (tempb < 0.6666666666666666D) b = temp1 + (temp2 - temp1) * (0.6666667F - tempb) * 6.0F; else {
/* 156 */             b = temp1;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 161 */     int result = 0;
/* 162 */     result += (((int)(r * 255.0F) & 0xFF) << 16);
/* 163 */     result += (((int)(g * 255.0F) & 0xFF) << 8);
/* 164 */     result += ((int)(b * 255.0F) & 0xFF);
/*     */ 
/* 166 */     return result;
/*     */   }
/*     */ 
/*     */   public static int[] calculateDifferent(float[] from, float[] to)
/*     */   {
/* 173 */     int[] diff = new int[3];
/* 174 */     diff[0] = floatToInteger(from[0], to[0], 180.0F, true);
/* 175 */     diff[1] = floatToInteger(from[1], to[1], 100.0F, false);
/* 176 */     diff[2] = floatToInteger(from[2], to[2], 100.0F, false);
/* 177 */     return diff;
/*     */   }
/*     */ 
/*     */   public static float[] applyDifference(float[] from, int[] diff) {
/* 181 */     float[] to = new float[3];
/* 182 */     to[0] = integerToFloat(from[0], diff[0], 180.0F, true);
/* 183 */     to[1] = integerToFloat(from[1], diff[1], 100.0F, false);
/* 184 */     to[2] = integerToFloat(from[2], diff[2], 100.0F, false);
/* 185 */     return to;
/*     */   }
/*     */ 
/*     */   private static int floatToInteger(float f, float f2, float offset, boolean rotate) {
/* 189 */     if (rotate) {
/* 190 */       int i = (int)((f2 - f) * 2.0F * offset);
/* 191 */       if (i > offset) {
/* 192 */         return i - (int)(2.0F * offset);
/*     */       }
/* 194 */       if (i < -offset) {
/* 195 */         return i + (int)(2.0F * offset);
/*     */       }
/*     */ 
/* 198 */       return i;
/*     */     }
/*     */ 
/* 202 */     if (f != 0.0F) {
/* 203 */       return (int)((f2 - f) * offset / f);
/*     */     }
/*     */ 
/* 206 */     return (int)((f2 - f) * offset);
/*     */   }
/*     */ 
/*     */   private static float integerToFloat(float f, int i, float offset, boolean rotate)
/*     */   {
/* 212 */     if (rotate) {
/* 213 */       float v = f + i / (2.0F * offset);
/* 214 */       if (v < 0.0F) {
/* 215 */         return v + 1.0F;
/*     */       }
/* 217 */       if (v > 1.0F) {
/* 218 */         return v - 1.0F;
/*     */       }
/*     */ 
/* 221 */       return v;
/*     */     }
/*     */ 
/* 225 */     if (i > 0) {
/* 226 */       return f + (1.0F - f) * i / offset;
/*     */     }
/*     */ 
/* 229 */     return f + f * i / offset;
/*     */   }
/*     */ 
/*     */   public static Color[] toColors(boolean hasAlpha, int[] colors)
/*     */   {
/* 242 */     Color[] result = new Color[colors.length];
/* 243 */     for (int i = 0; i < colors.length; i++) {
/* 244 */       result[i] = new Color(colors[i], hasAlpha);
/*     */     }
/* 246 */     return result;
/*     */   }
/*     */ 
/*     */   public static Color toGrayscale(Color c)
/*     */   {
/* 256 */     int gray = (int)(c.getRed() * 0.3D + c.getGreen() * 0.59D + c.getBlue() * 0.11D);
/* 257 */     return new Color(gray, gray, gray);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.ColorUtils
 * JD-Core Version:    0.6.0
 */
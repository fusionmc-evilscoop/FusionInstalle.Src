/*     */ package com.jidesoft.converter;
/*     */ 
/*     */ import java.awt.Color;
/*     */ 
/*     */ public class HexColorConverter extends ColorConverter
/*     */ {
/*  17 */   private boolean _alphaIncluded = false;
/*     */ 
/*     */   public HexColorConverter()
/*     */   {
/*     */   }
/*     */ 
/*     */   public HexColorConverter(boolean alphaIncluded)
/*     */   {
/*  31 */     this._alphaIncluded = alphaIncluded;
/*     */   }
/*     */ 
/*     */   public boolean isAlphaIncluded()
/*     */   {
/*  45 */     return this._alphaIncluded;
/*     */   }
/*     */ 
/*     */   public void setAlphaIncluded(boolean alphaIncluded)
/*     */   {
/*  56 */     this._alphaIncluded = alphaIncluded;
/*     */   }
/*     */ 
/*     */   protected String getHexString(int color) {
/*  60 */     String value = Integer.toHexString(color).toUpperCase();
/*  61 */     if (value.length() == 1) {
/*  62 */       value = "0" + value;
/*     */     }
/*  64 */     return value;
/*     */   }
/*     */ 
/*     */   public String toString(Object object, ConverterContext context) {
/*  68 */     if ((object instanceof Color)) {
/*  69 */       Color color = (Color)object;
/*  70 */       StringBuffer colorText = new StringBuffer("#");
/*  71 */       if (isAlphaIncluded()) {
/*  72 */         colorText.append(getHexString(color.getAlpha()));
/*     */       }
/*  74 */       colorText.append(getHexString(color.getRed()));
/*  75 */       colorText.append(getHexString(color.getGreen()));
/*  76 */       colorText.append(getHexString(color.getBlue()));
/*  77 */       return new String(colorText);
/*     */     }
/*     */ 
/*  80 */     return "";
/*     */   }
/*     */ 
/*     */   public boolean supportToString(Object object, ConverterContext context)
/*     */   {
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean supportFromString(String string, ConverterContext context) {
/*  89 */     return true;
/*     */   }
/*     */ 
/*     */   public Object fromString(String string, ConverterContext context) {
/*  93 */     if ((string == null) || (string.trim().length() == 0)) {
/*  94 */       return null;
/*     */     }
/*  96 */     if (string.startsWith("#")) {
/*  97 */       string = string.substring(1);
/*     */     }
/*  99 */     if (isAlphaIncluded()) {
/* 100 */       if (string.length() > 8) {
/* 101 */         string = string.substring(string.length() - 8);
/*     */       }
/*     */ 
/*     */     }
/* 105 */     else if (string.length() > 6)
/* 106 */       string = string.substring(string.length() - 6);
/*     */     long value;
/*     */     try
/*     */     {
/* 111 */       value = Long.parseLong(string, 16);
/*     */     }
/*     */     catch (NumberFormatException e) {
/* 114 */       return null;
/*     */     }
/* 116 */     return new Color((int)value, isAlphaIncluded());
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.HexColorConverter
 * JD-Core Version:    0.6.0
 */
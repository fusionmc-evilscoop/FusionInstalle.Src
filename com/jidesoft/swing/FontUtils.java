/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class FontUtils
/*     */ {
/*     */   private static Map<FontAttribute, Font> _fontCache;
/*     */ 
/*     */   public static int getDerivedFontCacheSize()
/*     */   {
/*  72 */     return _fontCache != null ? _fontCache.size() : 0;
/*     */   }
/*     */ 
/*     */   public static void clearDerivedFontCache()
/*     */   {
/*  79 */     if (_fontCache != null) {
/*  80 */       _fontCache.clear();
/*  81 */       _fontCache = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Font getCachedDerivedFont(Font font, int style, int size)
/*     */   {
/*  95 */     if (_fontCache == null) {
/*  96 */       _fontCache = new SoftHashMap();
/*     */     }
/*  98 */     FontAttribute attribute = new FontAttribute(font, style, size);
/*  99 */     Font derivedFont = (Font)_fontCache.get(attribute);
/* 100 */     if (derivedFont == null) {
/* 101 */       derivedFont = font.deriveFont(style, size);
/* 102 */       _fontCache.put(attribute, derivedFont);
/*     */     }
/* 104 */     return derivedFont;
/*     */   }
/*     */ 
/*     */   private static class FontAttribute
/*     */   {
/*     */     private Font _font;
/*     */     private int _style;
/*     */     private float _size;
/*     */ 
/*     */     FontAttribute(Font font, int style, float size)
/*     */     {
/*  27 */       this._font = font;
/*  28 */       this._style = style;
/*  29 */       this._size = size;
/*     */     }
/*     */ 
/*     */     public boolean equals(Object o) {
/*  33 */       if (this == o) {
/*  34 */         return true;
/*     */       }
/*  36 */       if (!(o instanceof FontAttribute)) {
/*  37 */         return false;
/*     */       }
/*     */ 
/*  40 */       FontAttribute that = (FontAttribute)o;
/*     */ 
/*  42 */       if (Float.compare(that._size, this._size) != 0) {
/*  43 */         return false;
/*     */       }
/*  45 */       if (this._style != that._style) {
/*  46 */         return false;
/*     */       }
/*     */ 
/*  49 */       return (this._font != null) && (this._font.equals(that._font));
/*     */     }
/*     */ 
/*     */     public int hashCode()
/*     */     {
/*  57 */       int result = this._font.hashCode();
/*  58 */       result = 31 * result + this._style;
/*  59 */       result = 31 * result + (this._size != 0.0F ? Float.floatToIntBits(this._size) : 0);
/*  60 */       return result;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.FontUtils
 * JD-Core Version:    0.6.0
 */
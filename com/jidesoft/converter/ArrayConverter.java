/*     */ package com.jidesoft.converter;
/*     */ 
/*     */ public abstract class ArrayConverter
/*     */   implements ObjectConverter
/*     */ {
/*     */   private String _separator;
/*     */   private int _size;
/*     */   private Class<?> _elementClass;
/*     */   private Class<?>[] _elementClasses;
/*     */ 
/*     */   public ArrayConverter(String separator, int size, Class<?> elementClass)
/*     */   {
/*  32 */     this._separator = separator;
/*  33 */     this._size = size;
/*  34 */     this._elementClass = elementClass;
/*     */   }
/*     */ 
/*     */   public ArrayConverter(String separator, int size, Class<?>[] elementClasses)
/*     */   {
/*  46 */     if ((separator == null) || (separator.trim().length() == 0)) {
/*  47 */       throw new IllegalArgumentException("separator cannot be empty.");
/*     */     }
/*  49 */     if (elementClasses == null) {
/*  50 */       throw new IllegalArgumentException("elementClasses cannot be null.");
/*     */     }
/*  52 */     if (size != elementClasses.length) {
/*  53 */       throw new IllegalArgumentException("size must be equal to the length of elementClasses.");
/*     */     }
/*  55 */     this._separator = separator;
/*  56 */     this._size = size;
/*  57 */     this._elementClasses = elementClasses;
/*     */   }
/*     */ 
/*     */   public String arrayToString(Object[] objects, ConverterContext context)
/*     */   {
/*  68 */     StringBuffer buffer = new StringBuffer();
/*  69 */     for (int i = 0; i < objects.length; i++) {
/*  70 */       Object o = objects[i];
/*  71 */       buffer.append(toString(i, o, context));
/*  72 */       if (i != objects.length - 1) {
/*  73 */         buffer.append(this._separator);
/*     */       }
/*     */     }
/*  76 */     return new String(buffer);
/*     */   }
/*     */ 
/*     */   protected String toString(int i, Object o, ConverterContext context) {
/*  80 */     return this._elementClass != null ? ObjectConverterManager.toString(o, this._elementClass, context) : ObjectConverterManager.toString(o, this._elementClasses[i], context);
/*     */   }
/*     */ 
/*     */   public Object[] arrayFromString(String string, ConverterContext context)
/*     */   {
/*  91 */     if ((string == null) || (string.trim().length() == 0)) {
/*  92 */       return null;
/*     */     }
/*     */ 
/*  95 */     String[] ss = string.split(this._separator);
/*     */ 
/*  97 */     Object[] objects = new Object[this._size != -1 ? this._size : ss.length];
/*  98 */     for (int i = 0; i < objects.length; i++) {
/*  99 */       String s = ss[i].trim();
/* 100 */       objects[i] = fromString(i, s, context);
/*     */     }
/* 102 */     return objects;
/*     */   }
/*     */ 
/*     */   protected Object fromString(int i, String s, ConverterContext context) {
/* 106 */     return this._elementClass != null ? ObjectConverterManager.fromString(s, this._elementClass, context) : ObjectConverterManager.fromString(s, this._elementClasses[i], context);
/*     */   }
/*     */ 
/*     */   public Class<?> getElementClass()
/*     */   {
/* 115 */     return this._elementClass;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.ArrayConverter
 * JD-Core Version:    0.6.0
 */
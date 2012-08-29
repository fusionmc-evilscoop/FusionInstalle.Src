/*     */ package com.jidesoft.converter;
/*     */ 
/*     */ public class EnumConverter
/*     */   implements ObjectConverter
/*     */ {
/*     */   private String _name;
/*     */   private Object _default;
/*     */   private Class<?> _type;
/*     */   private Object[] _objects;
/*     */   private String[] _strings;
/*  34 */   private boolean _strict = true;
/*     */   private transient ConverterContext _context;
/*     */ 
/*     */   public EnumConverter(String name, Object[] values, String[] strings)
/*     */   {
/*  37 */     this(name, values[0].getClass(), values, strings);
/*     */   }
/*     */ 
/*     */   public EnumConverter(String name, Class<?> type, Object[] values, String[] strings) {
/*  41 */     this(name, type, values, strings, null);
/*     */   }
/*     */ 
/*     */   public EnumConverter(String name, Class<?> type, Object[] objects, String[] strings, Object defaultValue)
/*     */   {
/*  59 */     if ((name == null) || (name.trim().length() == 0)) {
/*  60 */       throw new IllegalArgumentException("The \"name\" parameter cannot be null or empty. Please use a unique string to represent the name of the converter.");
/*     */     }
/*  62 */     this._name = name;
/*  63 */     if (objects == null) {
/*  64 */       throw new IllegalArgumentException("The \"objects\" parameter cannot be null.");
/*     */     }
/*  66 */     if (strings == null) {
/*  67 */       throw new IllegalArgumentException("The \"strings\" parameter cannot be null.");
/*     */     }
/*  69 */     if (strings.length != objects.length) {
/*  70 */       throw new IllegalArgumentException("The \"objects\" and \"strings\" parameters should have the same length.");
/*     */     }
/*  72 */     this._type = type;
/*  73 */     this._objects = objects;
/*  74 */     this._strings = strings;
/*  75 */     this._default = defaultValue;
/*     */   }
/*     */ 
/*     */   public ConverterContext getContext()
/*     */   {
/*  87 */     if (this._context == null) {
/*  88 */       this._context = new ConverterContext(this._name);
/*     */     }
/*  90 */     return this._context;
/*     */   }
/*     */ 
/*     */   public String toString(Object object, ConverterContext context)
/*     */   {
/* 103 */     for (int i = 0; i < this._objects.length; i++) {
/* 104 */       if (((this._objects[i] == null) && (object == null)) || ((this._objects[i] != null) && (this._objects[i].equals(object)) && 
/* 105 */         (i < this._strings.length))) {
/* 106 */         return this._strings[i];
/*     */       }
/*     */     }
/*     */ 
/* 110 */     return "" + object;
/*     */   }
/*     */ 
/*     */   public boolean supportToString(Object object, ConverterContext context) {
/* 114 */     return true;
/*     */   }
/*     */ 
/*     */   public Object fromString(String string, ConverterContext context)
/*     */   {
/* 127 */     for (int i = 0; i < this._strings.length; i++) {
/* 128 */       if ((this._strings[i].equals(string)) && 
/* 129 */         (i < this._objects.length)) {
/* 130 */         return this._objects[i];
/*     */       }
/*     */     }
/*     */ 
/* 134 */     return isStrict() ? this._default : string;
/*     */   }
/*     */ 
/*     */   public boolean supportFromString(String string, ConverterContext context) {
/* 138 */     return true;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 147 */     return this._name;
/*     */   }
/*     */ 
/*     */   public Class<?> getType()
/*     */   {
/* 156 */     return this._type;
/*     */   }
/*     */ 
/*     */   public Object getDefault()
/*     */   {
/* 165 */     return this._default;
/*     */   }
/*     */ 
/*     */   public Object[] getObjects()
/*     */   {
/* 174 */     return this._objects;
/*     */   }
/*     */ 
/*     */   public String[] getStrings()
/*     */   {
/* 183 */     return this._strings;
/*     */   }
/*     */ 
/*     */   public static String[] toStrings(Object[] values)
/*     */   {
/* 201 */     return toStrings(values, null);
/*     */   }
/*     */ 
/*     */   public static String[] toStrings(Object[] values, ConverterContext converterContext)
/*     */   {
/* 212 */     String[] s = new String[values.length];
/* 213 */     for (int i = 0; i < s.length; i++) {
/* 214 */       s[i] = ObjectConverterManager.toString(values[i], values[i].getClass(), converterContext);
/*     */     }
/* 216 */     return s;
/*     */   }
/*     */ 
/*     */   public boolean isStrict()
/*     */   {
/* 227 */     return this._strict;
/*     */   }
/*     */ 
/*     */   public void setStrict(boolean strict)
/*     */   {
/* 236 */     this._strict = strict;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.EnumConverter
 * JD-Core Version:    0.6.0
 */
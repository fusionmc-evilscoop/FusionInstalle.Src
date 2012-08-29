/*     */ package com.jidesoft.converter;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.File;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class ObjectConverterManager
/*     */ {
/*  26 */   private static CacheMap<ObjectConverter, ConverterContext> _cache = new CacheMap(ConverterContext.DEFAULT_CONTEXT);
/*     */ 
/*  28 */   private static ObjectConverter _defaultConverter = new DefaultObjectConverter();
/*     */ 
/* 215 */   private static boolean _inited = false;
/* 216 */   private static boolean _initing = false;
/* 217 */   private static boolean _autoInit = true;
/*     */ 
/*     */   public static void registerConverter(Class<?> clazz, ObjectConverter converter, ConverterContext context)
/*     */   {
/*  38 */     if (clazz == null) {
/*  39 */       throw new IllegalArgumentException("Parameter class cannot be null");
/*     */     }
/*     */ 
/*  42 */     if (context == null) {
/*  43 */       context = ConverterContext.DEFAULT_CONTEXT;
/*     */     }
/*     */ 
/*  46 */     if ((isAutoInit()) && (!_initing)) {
/*  47 */       initDefaultConverter();
/*     */     }
/*     */ 
/*  50 */     _cache.register(clazz, converter, context);
/*     */   }
/*     */ 
/*     */   public static void registerConverter(Class<?> clazz, ObjectConverter converter)
/*     */   {
/*  60 */     registerConverter(clazz, converter, ConverterContext.DEFAULT_CONTEXT);
/*     */   }
/*     */ 
/*     */   public static void unregisterConverter(Class<?> clazz, ConverterContext context)
/*     */   {
/*  70 */     if (context == null) {
/*  71 */       context = ConverterContext.DEFAULT_CONTEXT;
/*     */     }
/*     */ 
/*  74 */     if ((isAutoInit()) && (!_initing)) {
/*  75 */       initDefaultConverter();
/*     */     }
/*     */ 
/*  78 */     _cache.unregister(clazz, context);
/*     */   }
/*     */ 
/*     */   public static void unregisterConverter(Class<?> clazz)
/*     */   {
/*  87 */     unregisterConverter(clazz, ConverterContext.DEFAULT_CONTEXT);
/*     */   }
/*     */ 
/*     */   public static void unregisterAllConverters(Class<?> clazz)
/*     */   {
/*  96 */     _cache.remove(clazz);
/*     */   }
/*     */ 
/*     */   public static void unregisterAllConverters()
/*     */   {
/* 103 */     _cache.clear();
/*     */   }
/*     */ 
/*     */   public static ObjectConverter getConverter(Class<?> clazz, ConverterContext context)
/*     */   {
/* 114 */     if (isAutoInit()) {
/* 115 */       initDefaultConverter();
/*     */     }
/*     */ 
/* 118 */     if (context == null) {
/* 119 */       context = ConverterContext.DEFAULT_CONTEXT;
/*     */     }
/*     */ 
/* 122 */     ObjectConverter converter = (ObjectConverter)_cache.getRegisteredObject(clazz, context);
/* 123 */     if (converter != null) {
/* 124 */       return converter;
/*     */     }
/*     */ 
/* 127 */     return _defaultConverter;
/*     */   }
/*     */ 
/*     */   public static ObjectConverter getConverter(Class<?> clazz)
/*     */   {
/* 138 */     return getConverter(clazz, ConverterContext.DEFAULT_CONTEXT);
/*     */   }
/*     */ 
/*     */   public static String toString(Object object)
/*     */   {
/* 148 */     if (object != null) {
/* 149 */       return toString(object, object.getClass(), ConverterContext.DEFAULT_CONTEXT);
/*     */     }
/* 151 */     return "";
/*     */   }
/*     */ 
/*     */   public static String toString(Object object, Class<?> clazz)
/*     */   {
/* 162 */     return toString(object, clazz, ConverterContext.DEFAULT_CONTEXT);
/*     */   }
/*     */ 
/*     */   public static String toString(Object object, Class<?> clazz, ConverterContext context)
/*     */   {
/* 174 */     ObjectConverter converter = getConverter(clazz, context);
/* 175 */     if ((converter != null) && (converter.supportToString(object, context))) {
/* 176 */       return converter.toString(object, context);
/*     */     }
/* 178 */     if (object == null) {
/* 179 */       return "";
/*     */     }
/*     */ 
/* 182 */     return object.toString();
/*     */   }
/*     */ 
/*     */   public static Object fromString(String string, Class<?> clazz)
/*     */   {
/* 194 */     return fromString(string, clazz, ConverterContext.DEFAULT_CONTEXT);
/*     */   }
/*     */ 
/*     */   public static Object fromString(String string, Class<?> clazz, ConverterContext context)
/*     */   {
/* 206 */     ObjectConverter converter = getConverter(clazz, context);
/* 207 */     if ((converter != null) && (converter.supportFromString(string, context))) {
/* 208 */       return converter.fromString(string, context);
/*     */     }
/*     */ 
/* 211 */     return null;
/*     */   }
/*     */ 
/*     */   public static boolean isAutoInit()
/*     */   {
/* 227 */     return _autoInit;
/*     */   }
/*     */ 
/*     */   public static void setAutoInit(boolean autoInit)
/*     */   {
/* 242 */     _autoInit = autoInit;
/*     */   }
/*     */ 
/*     */   public static void addRegistrationListener(RegistrationListener l)
/*     */   {
/* 251 */     _cache.addRegistrationListener(l);
/*     */   }
/*     */ 
/*     */   public static void removeRegistrationListener(RegistrationListener l)
/*     */   {
/* 260 */     _cache.removeRegistrationListener(l);
/*     */   }
/*     */ 
/*     */   public static RegistrationListener[] getRegistrationListeners()
/*     */   {
/* 273 */     return _cache.getRegistrationListeners();
/*     */   }
/*     */ 
/*     */   public static ConverterContext[] getConverterContexts(Class<?> clazz)
/*     */   {
/* 283 */     return (ConverterContext[])_cache.getKeys(clazz, new ConverterContext[0]);
/*     */   }
/*     */ 
/*     */   public static void initDefaultConverter()
/*     */   {
/* 310 */     if (_inited) {
/* 311 */       return;
/*     */     }
/*     */ 
/* 314 */     _initing = true;
/*     */     try
/*     */     {
/* 317 */       registerConverter(String.class, new DefaultObjectConverter());
/* 318 */       registerConverter([C.class, new PasswordConverter(), PasswordConverter.CONTEXT);
/*     */ 
/* 320 */       DoubleConverter fractionConverter = new DoubleConverter();
/* 321 */       fractionConverter.setFractionDigits(2, 2);
/* 322 */       registerConverter(Number.class, fractionConverter, NumberConverter.CONTEXT_FRACTION_NUMBER);
/*     */ 
/* 324 */       IntegerConverter integerConverter = new IntegerConverter();
/* 325 */       registerConverter(Integer.class, integerConverter);
/* 326 */       registerConverter(Integer.TYPE, integerConverter);
/*     */ 
/* 328 */       NaturalNumberConverter naturalNumberConverter = new NaturalNumberConverter();
/* 329 */       registerConverter(Integer.class, naturalNumberConverter, NaturalNumberConverter.CONTEXT);
/* 330 */       registerConverter(Integer.TYPE, naturalNumberConverter, NaturalNumberConverter.CONTEXT);
/*     */ 
/* 332 */       LongConverter longConverter = new LongConverter();
/* 333 */       registerConverter(Long.class, longConverter);
/* 334 */       registerConverter(Long.TYPE, longConverter);
/*     */ 
/* 336 */       DoubleConverter doubleConverter = new DoubleConverter();
/* 337 */       registerConverter(Double.class, doubleConverter);
/* 338 */       registerConverter(Double.TYPE, doubleConverter);
/*     */ 
/* 340 */       FloatConverter floatConverter = new FloatConverter();
/* 341 */       registerConverter(Float.class, floatConverter);
/* 342 */       registerConverter(Float.TYPE, floatConverter);
/*     */ 
/* 344 */       ShortConverter shortConverter = new ShortConverter();
/* 345 */       registerConverter(Short.class, shortConverter);
/* 346 */       registerConverter(Short.TYPE, shortConverter);
/*     */ 
/* 348 */       ByteConverter byteConverter = new ByteConverter();
/* 349 */       registerConverter(Byte.class, byteConverter);
/* 350 */       registerConverter(Byte.TYPE, byteConverter);
/*     */ 
/* 352 */       registerConverter(Rectangle.class, new RectangleConverter());
/* 353 */       registerConverter(Point.class, new PointConverter());
/* 354 */       registerConverter(Insets.class, new InsetsConverter());
/* 355 */       registerConverter(Dimension.class, new DimensionConverter());
/*     */ 
/* 357 */       BooleanConverter booleanConverter = new BooleanConverter();
/* 358 */       registerConverter(Boolean.class, booleanConverter);
/* 359 */       registerConverter(Boolean.TYPE, booleanConverter);
/*     */ 
/* 361 */       registerConverter(File.class, new FileConverter());
/* 362 */       registerConverter(String.class, new FontNameConverter(), FontNameConverter.CONTEXT);
/*     */ 
/* 364 */       DateConverter dateConverter = new DateConverter();
/* 365 */       registerConverter(Date.class, dateConverter);
/* 366 */       registerConverter(Date.class, dateConverter, DateConverter.DATETIME_CONTEXT);
/* 367 */       registerConverter(Date.class, dateConverter, DateConverter.TIME_CONTEXT);
/*     */ 
/* 369 */       CalendarConverter calendarConverter = new CalendarConverter();
/* 370 */       registerConverter(Calendar.class, calendarConverter);
/* 371 */       registerConverter(Calendar.class, calendarConverter, DateConverter.DATETIME_CONTEXT);
/* 372 */       registerConverter(Calendar.class, calendarConverter, DateConverter.TIME_CONTEXT);
/*     */ 
/* 374 */       registerConverter(Calendar.class, new MonthConverter(), MonthConverter.CONTEXT_MONTH);
/* 375 */       registerConverter(Color.class, new RgbColorConverter());
/* 376 */       registerConverter(Color.class, new HexColorConverter(), ColorConverter.CONTEXT_HEX);
/* 377 */       registerConverter(Color.class, new RgbColorConverter(true), ColorConverter.CONTEXT_RGBA);
/* 378 */       registerConverter(Color.class, new HexColorConverter(true), ColorConverter.CONTEXT_HEX_WITH_ALPHA);
/*     */ 
/* 380 */       registerConverter([Ljava.lang.String.class, new StringArrayConverter());
/*     */ 
/* 382 */       QuarterNameConverter quarterNameConverter = new QuarterNameConverter();
/* 383 */       registerConverter(Integer.TYPE, quarterNameConverter, QuarterNameConverter.CONTEXT);
/* 384 */       registerConverter(Integer.class, quarterNameConverter, QuarterNameConverter.CONTEXT);
/*     */ 
/* 386 */       registerConverter(Font.class, new FontConverter());
/* 387 */       registerConverter(String.class, new MultilineStringConverter(), MultilineStringConverter.CONTEXT);
/*     */ 
/* 389 */       CurrencyConverter currencyConverter = new CurrencyConverter();
/* 390 */       registerConverter(Float.class, currencyConverter, CurrencyConverter.CONTEXT);
/* 391 */       registerConverter(Float.TYPE, currencyConverter, CurrencyConverter.CONTEXT);
/* 392 */       registerConverter(Double.class, currencyConverter, CurrencyConverter.CONTEXT);
/* 393 */       registerConverter(Double.TYPE, currencyConverter, CurrencyConverter.CONTEXT);
/*     */ 
/* 395 */       PercentConverter percentConverter = new PercentConverter();
/* 396 */       registerConverter(Float.class, percentConverter, PercentConverter.CONTEXT);
/* 397 */       registerConverter(Float.TYPE, percentConverter, PercentConverter.CONTEXT);
/* 398 */       registerConverter(Double.class, percentConverter, PercentConverter.CONTEXT);
/* 399 */       registerConverter(Double.TYPE, percentConverter, PercentConverter.CONTEXT);
/*     */ 
/* 401 */       MonthNameConverter monthNameConverter = new MonthNameConverter();
/* 402 */       registerConverter(Integer.class, monthNameConverter, MonthNameConverter.CONTEXT);
/* 403 */       registerConverter(Integer.TYPE, monthNameConverter, MonthNameConverter.CONTEXT);
/*     */ 
/* 405 */       YearNameConverter yearNameConverter = new YearNameConverter();
/* 406 */       registerConverter(Integer.class, yearNameConverter, YearNameConverter.CONTEXT);
/* 407 */       registerConverter(Integer.TYPE, yearNameConverter, YearNameConverter.CONTEXT);
/*     */ 
/* 409 */       registerConverter([I.class, new DefaultArrayConverter("; ", Integer.TYPE));
/* 410 */       registerConverter([Ljava.lang.Object.class, new DefaultArrayConverter("; ", Object.class));
/* 411 */       registerConverter([Ljava.lang.Enum.class, new DefaultArrayConverter("; ", Enum.class));
/* 412 */       registerConverter([Ljava.lang.String.class, new DefaultArrayConverter("; ", String.class));
/* 413 */       registerConverter([Ljava.util.Date.class, new DefaultArrayConverter("; ", Date.class));
/* 414 */       registerConverter([Ljava.util.Calendar.class, new DefaultArrayConverter("; ", Calendar.class));
/* 415 */       registerConverter([Ljava.lang.Number.class, new DefaultArrayConverter("; ", Number.class));
/* 416 */       registerConverter([Ljava.lang.Integer.class, new DefaultArrayConverter("; ", Integer.class));
/* 417 */       registerConverter([Ljava.lang.Float.class, new DefaultArrayConverter("; ", Float.class));
/* 418 */       registerConverter([Ljava.lang.Double.class, new DefaultArrayConverter("; ", Double.class));
/* 419 */       registerConverter([Ljava.lang.Long.class, new DefaultArrayConverter("; ", Long.class));
/* 420 */       registerConverter([Ljava.lang.Short.class, new DefaultArrayConverter("; ", Short.class));
/* 421 */       registerConverter([I.class, new DefaultArrayConverter("; ", Integer.TYPE));
/* 422 */       registerConverter([F.class, new DefaultArrayConverter("; ", Float.TYPE));
/* 423 */       registerConverter([D.class, new DefaultArrayConverter("; ", Double.TYPE));
/* 424 */       registerConverter([J.class, new DefaultArrayConverter("; ", Long.TYPE));
/* 425 */       registerConverter([S.class, new DefaultArrayConverter("; ", Short.TYPE));
/*     */ 
/* 427 */       registerConverter(BigDecimal.class, new BigDecimalConverter());
/*     */     }
/*     */     finally {
/* 430 */       _initing = false;
/* 431 */       _inited = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void resetInit()
/*     */   {
/* 442 */     _inited = false;
/*     */   }
/*     */ 
/*     */   public static void clear()
/*     */   {
/* 470 */     resetInit();
/* 471 */     _cache.clear();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.ObjectConverterManager
 * JD-Core Version:    0.6.0
 */
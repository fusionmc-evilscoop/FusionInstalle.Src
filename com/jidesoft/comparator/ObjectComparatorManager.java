/*     */ package com.jidesoft.comparator;
/*     */ 
/*     */ import com.jidesoft.converter.CacheMap;
/*     */ import com.jidesoft.converter.RegistrationListener;
/*     */ import java.text.Collator;
/*     */ import java.util.Calendar;
/*     */ import java.util.Comparator;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class ObjectComparatorManager
/*     */ {
/*  21 */   private static final CacheMap<Comparator<?>, ComparatorContext> _cache = new CacheMap(new ComparatorContext(""));
/*     */ 
/*  23 */   private static final Comparator<Object> _defaultComparator = new DefaultComparator();
/*     */ 
/* 234 */   private static boolean _inited = false;
/* 235 */   private static boolean _initing = false;
/* 236 */   private static boolean _autoInit = true;
/*     */ 
/*     */   public static void registerComparator(Class<?> clazz, Comparator comparator)
/*     */   {
/*  26 */     registerComparator(clazz, comparator, ComparatorContext.DEFAULT_CONTEXT);
/*     */   }
/*     */ 
/*     */   public static void registerComparator(Class<?> clazz, Comparator comparator, ComparatorContext context)
/*     */   {
/*  37 */     if (clazz == null) {
/*  38 */       throw new IllegalArgumentException("Parameter clazz cannot be null");
/*     */     }
/*  40 */     if (context == null) {
/*  41 */       context = ComparatorContext.DEFAULT_CONTEXT;
/*     */     }
/*     */ 
/*  44 */     if ((isAutoInit()) && (!_initing)) {
/*  45 */       initDefaultComparator();
/*     */     }
/*     */ 
/*  48 */     _cache.register(clazz, comparator, context);
/*     */   }
/*     */ 
/*     */   public static void unregisterComparator(Class<?> clazz)
/*     */   {
/*  57 */     _cache.unregister(clazz, ComparatorContext.DEFAULT_CONTEXT);
/*     */   }
/*     */ 
/*     */   public static void unregisterComparator(Class<?> clazz, ComparatorContext context)
/*     */   {
/*  67 */     if (context == null) {
/*  68 */       context = ComparatorContext.DEFAULT_CONTEXT;
/*     */     }
/*  70 */     _cache.unregister(clazz, context);
/*     */   }
/*     */ 
/*     */   public static void unregisterAllComparators(Class<?> clazz)
/*     */   {
/*  79 */     _cache.remove(clazz);
/*     */   }
/*     */ 
/*     */   public static void unregisterAllComparators()
/*     */   {
/*  86 */     _cache.clear();
/*     */   }
/*     */ 
/*     */   public static Comparator getComparator(Class<?> clazz)
/*     */   {
/*  96 */     return getComparator(clazz, ComparatorContext.DEFAULT_CONTEXT);
/*     */   }
/*     */ 
/*     */   public static Comparator getComparator(Class<?> clazz, ComparatorContext context)
/*     */   {
/* 107 */     if (isAutoInit()) {
/* 108 */       initDefaultComparator();
/*     */     }
/*     */ 
/* 111 */     if (context == null) {
/* 112 */       context = ComparatorContext.DEFAULT_CONTEXT;
/*     */     }
/* 114 */     Comparator object = (Comparator)_cache.getRegisteredObject(clazz, context);
/* 115 */     if (object != null) {
/* 116 */       return object;
/*     */     }
/*     */ 
/* 119 */     return _defaultComparator;
/*     */   }
/*     */ 
/*     */   public static int compare(Object o1, Object o2)
/*     */   {
/* 133 */     return compare(o1, o2, ComparatorContext.DEFAULT_CONTEXT);
/*     */   }
/*     */ 
/*     */   public static int compare(Object o1, Object o2, ComparatorContext context)
/*     */   {
/* 147 */     if ((o1 == null) && (o2 == null)) {
/* 148 */       return 0;
/*     */     }
/* 150 */     if (o1 == null) {
/* 151 */       return -1;
/*     */     }
/* 153 */     if (o2 == null) {
/* 154 */       return 1;
/*     */     }
/*     */ 
/* 160 */     Class clazz1 = o1.getClass();
/* 161 */     Class clazz2 = o2.getClass();
/*     */     Class clazz;
/*     */     Class clazz;
/* 162 */     if (clazz1 == clazz2) {
/* 163 */       clazz = clazz1;
/*     */     }
/*     */     else
/*     */     {
/*     */       Class clazz;
/* 165 */       if (clazz1.isAssignableFrom(clazz2)) {
/* 166 */         clazz = clazz1;
/*     */       }
/*     */       else
/*     */       {
/*     */         Class clazz;
/* 168 */         if (clazz2.isAssignableFrom(clazz1)) {
/* 169 */           clazz = clazz2;
/*     */         }
/*     */         else
/*     */         {
/*     */           Class clazz;
/* 171 */           if ((clazz1.isAssignableFrom(Comparable.class)) && (clazz2.isAssignableFrom(Comparable.class))) {
/* 172 */             clazz = Comparable.class;
/*     */           }
/*     */           else
/* 175 */             clazz = Object.class; 
/*     */         }
/*     */       }
/*     */     }
/* 178 */     return compare(o1, o2, clazz, context);
/*     */   }
/*     */ 
/*     */   public static int compare(Object o1, Object o2, Class<?> clazz)
/*     */   {
/* 194 */     return compare(o1, o2, clazz, ComparatorContext.DEFAULT_CONTEXT);
/*     */   }
/*     */ 
/*     */   public static int compare(Object o1, Object o2, Class<?> clazz, ComparatorContext context)
/*     */   {
/* 209 */     Comparator comparator = getComparator(clazz, context);
/* 210 */     if (comparator != null) {
/*     */       try {
/* 212 */         return comparator.compare(o1, o2);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/*     */     }
/* 218 */     if (o1 == o2) {
/* 219 */       return 0;
/*     */     }
/*     */ 
/* 222 */     if (o1 == null) {
/* 223 */       return -1;
/*     */     }
/* 225 */     if (o2 == null) {
/* 226 */       return 1;
/*     */     }
/*     */ 
/* 229 */     return o1.toString().compareTo(o2.toString());
/*     */   }
/*     */ 
/*     */   public static boolean isAutoInit()
/*     */   {
/* 246 */     return _autoInit;
/*     */   }
/*     */ 
/*     */   public static void setAutoInit(boolean autoInit)
/*     */   {
/* 262 */     _autoInit = autoInit;
/*     */   }
/*     */ 
/*     */   public static void addRegistrationListener(RegistrationListener l)
/*     */   {
/* 271 */     _cache.addRegistrationListener(l);
/*     */   }
/*     */ 
/*     */   public static void removeRegistrationListener(RegistrationListener l)
/*     */   {
/* 280 */     _cache.removeRegistrationListener(l);
/*     */   }
/*     */ 
/*     */   public static RegistrationListener[] getRegistrationListeners()
/*     */   {
/* 293 */     return _cache.getRegistrationListeners();
/*     */   }
/*     */ 
/*     */   public static ComparatorContext[] getComparatorContexts(Class<?> clazz)
/*     */   {
/* 303 */     return (ComparatorContext[])_cache.getKeys(clazz, new ComparatorContext[0]);
/*     */   }
/*     */ 
/*     */   public static void initDefaultComparator()
/*     */   {
/* 311 */     if (_inited) {
/* 312 */       return;
/*     */     }
/*     */ 
/* 315 */     _initing = true;
/*     */     try
/*     */     {
/* 318 */       registerComparator(Object.class, new DefaultComparator());
/* 319 */       registerComparator(Boolean.class, new BooleanComparator());
/* 320 */       registerComparator(Calendar.class, new CalendarComparator());
/* 321 */       registerComparator(Date.class, new DateComparator());
/*     */ 
/* 323 */       NumberComparator numberComparator = new NumberComparator();
/* 324 */       registerComparator(Number.class, numberComparator);
/* 325 */       registerComparator(Double.TYPE, numberComparator);
/* 326 */       registerComparator(Float.TYPE, numberComparator);
/* 327 */       registerComparator(Long.TYPE, numberComparator);
/* 328 */       registerComparator(Integer.TYPE, numberComparator);
/* 329 */       registerComparator(Short.TYPE, numberComparator);
/* 330 */       registerComparator(Byte.TYPE, numberComparator);
/*     */ 
/* 332 */       NumberComparator absoluteNumberComparator = new NumberComparator();
/* 333 */       absoluteNumberComparator.setAbsolute(true);
/* 334 */       registerComparator(Number.class, absoluteNumberComparator, NumberComparator.CONTEXT_ABSOLUTE);
/* 335 */       registerComparator(Double.TYPE, absoluteNumberComparator, NumberComparator.CONTEXT_ABSOLUTE);
/* 336 */       registerComparator(Float.TYPE, absoluteNumberComparator, NumberComparator.CONTEXT_ABSOLUTE);
/* 337 */       registerComparator(Long.TYPE, absoluteNumberComparator, NumberComparator.CONTEXT_ABSOLUTE);
/* 338 */       registerComparator(Integer.TYPE, absoluteNumberComparator, NumberComparator.CONTEXT_ABSOLUTE);
/* 339 */       registerComparator(Short.TYPE, absoluteNumberComparator, NumberComparator.CONTEXT_ABSOLUTE);
/* 340 */       registerComparator(Byte.TYPE, absoluteNumberComparator, NumberComparator.CONTEXT_ABSOLUTE);
/*     */ 
/* 342 */       registerComparator(Comparable.class, new FastComparableComparator());
/* 343 */       registerComparator(String.class, Collator.getInstance());
/* 344 */       Collator caseInsensitiveCollator = Collator.getInstance();
/* 345 */       caseInsensitiveCollator.setStrength(0);
/* 346 */       registerComparator(String.class, caseInsensitiveCollator, new ComparatorContext("Ignorecase"));
/* 347 */       registerComparator(CharSequence.class, new CharSequenceComparator(), CharSequenceComparator.CONTEXT);
/* 348 */       registerComparator(CharSequence.class, new CharSequenceComparator(false), CharSequenceComparator.CONTEXT_IGNORE_CASE);
/* 349 */       registerComparator(CharSequence.class, new AlphanumComparator(), AlphanumComparator.CONTEXT);
/* 350 */       registerComparator(CharSequence.class, new AlphanumComparator(false), AlphanumComparator.CONTEXT_IGNORE_CASE);
/*     */     }
/*     */     finally {
/* 353 */       _initing = false;
/* 354 */       _inited = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void resetInit()
/*     */   {
/* 365 */     _inited = false;
/*     */   }
/*     */ 
/*     */   public static void clear() {
/* 369 */     resetInit();
/* 370 */     _cache.clear();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.comparator.ObjectComparatorManager
 * JD-Core Version:    0.6.0
 */
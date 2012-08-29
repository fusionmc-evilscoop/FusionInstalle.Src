/*     */ package com.jidesoft.grouper;
/*     */ 
/*     */ import com.jidesoft.converter.CacheMap;
/*     */ import com.jidesoft.converter.RegistrationListener;
/*     */ import com.jidesoft.grouper.date.DateMonthGrouper;
/*     */ import com.jidesoft.grouper.date.DateYearGrouper;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class ObjectGrouperManager
/*     */ {
/*  21 */   private static CacheMap<ObjectGrouper, GrouperContext> _cache = new CacheMap(GrouperContext.DEFAULT_CONTEXT);
/*     */ 
/*  23 */   private static ObjectGrouper _defaultGrouper = null;
/*     */ 
/* 170 */   private static boolean _inited = false;
/* 171 */   private static boolean _initing = false;
/* 172 */   private static boolean _autoInit = true;
/*     */ 
/*     */   public static void registerGrouper(Class<?> clazz, ObjectGrouper grouper, GrouperContext context)
/*     */   {
/*  33 */     if (clazz == null) {
/*  34 */       throw new IllegalArgumentException("Parameter class cannot be null");
/*     */     }
/*     */ 
/*  37 */     if (context == null) {
/*  38 */       context = GrouperContext.DEFAULT_CONTEXT;
/*     */     }
/*     */ 
/*  41 */     if ((isAutoInit()) && (!_initing)) {
/*  42 */       initDefaultGrouper();
/*     */     }
/*     */ 
/*  45 */     _cache.register(clazz, grouper, context);
/*     */   }
/*     */ 
/*     */   public static void registerGrouper(Class<?> clazz, ObjectGrouper grouper)
/*     */   {
/*  55 */     registerGrouper(clazz, grouper, GrouperContext.DEFAULT_CONTEXT);
/*     */   }
/*     */ 
/*     */   public static void unregisterGrouper(Class<?> clazz, GrouperContext context)
/*     */   {
/*  65 */     if (context == null) {
/*  66 */       context = GrouperContext.DEFAULT_CONTEXT;
/*     */     }
/*  68 */     _cache.unregister(clazz, context);
/*     */   }
/*     */ 
/*     */   public static void unregisterGrouper(Class<?> clazz)
/*     */   {
/*  77 */     unregisterGrouper(clazz, GrouperContext.DEFAULT_CONTEXT);
/*     */   }
/*     */ 
/*     */   public static void unregisterAllGroupers(Class<?> clazz)
/*     */   {
/*  86 */     _cache.remove(clazz);
/*     */   }
/*     */ 
/*     */   public static void unregisterAllGroupers()
/*     */   {
/*  93 */     _cache.clear();
/*     */   }
/*     */ 
/*     */   public static ObjectGrouper getGrouper(Class<?> clazz, GrouperContext context)
/*     */   {
/* 104 */     if (isAutoInit()) {
/* 105 */       initDefaultGrouper();
/*     */     }
/*     */ 
/* 108 */     if (context == null) {
/* 109 */       context = GrouperContext.DEFAULT_CONTEXT;
/*     */     }
/* 111 */     ObjectGrouper object = (ObjectGrouper)_cache.getRegisteredObject(clazz, context);
/* 112 */     if (object != null) {
/* 113 */       return object;
/*     */     }
/*     */ 
/* 116 */     return _defaultGrouper;
/*     */   }
/*     */ 
/*     */   public static ObjectGrouper getGrouper(Class<?> clazz)
/*     */   {
/* 127 */     return getGrouper(clazz, GrouperContext.DEFAULT_CONTEXT);
/*     */   }
/*     */ 
/*     */   public static Object getGroupValue(Object object)
/*     */   {
/* 137 */     if (object != null) {
/* 138 */       return getGroupValue(object, object.getClass(), GrouperContext.DEFAULT_CONTEXT);
/*     */     }
/* 140 */     return null;
/*     */   }
/*     */ 
/*     */   public static Object getGroupValue(Object object, Class<?> clazz)
/*     */   {
/* 151 */     return getGroupValue(object, clazz, GrouperContext.DEFAULT_CONTEXT);
/*     */   }
/*     */ 
/*     */   public static Object getGroupValue(Object object, Class<?> clazz, GrouperContext context)
/*     */   {
/* 163 */     ObjectGrouper grouper = getGrouper(clazz, context);
/* 164 */     if (grouper != null) {
/* 165 */       return grouper.getValue(object);
/*     */     }
/* 167 */     return null;
/*     */   }
/*     */ 
/*     */   public static boolean isAutoInit()
/*     */   {
/* 182 */     return _autoInit;
/*     */   }
/*     */ 
/*     */   public static void setAutoInit(boolean autoInit)
/*     */   {
/* 197 */     _autoInit = autoInit;
/*     */   }
/*     */ 
/*     */   public static void addRegistrationListener(RegistrationListener l)
/*     */   {
/* 206 */     _cache.addRegistrationListener(l);
/*     */   }
/*     */ 
/*     */   public static void removeRegistrationListener(RegistrationListener l)
/*     */   {
/* 215 */     _cache.removeRegistrationListener(l);
/*     */   }
/*     */ 
/*     */   public static RegistrationListener[] getRegistrationListeners()
/*     */   {
/* 228 */     return _cache.getRegistrationListeners();
/*     */   }
/*     */ 
/*     */   public static GrouperContext[] getGrouperContexts(Class<?> clazz)
/*     */   {
/* 238 */     return (GrouperContext[])_cache.getKeys(clazz, new GrouperContext[0]);
/*     */   }
/*     */ 
/*     */   public static void initDefaultGrouper()
/*     */   {
/* 256 */     if (_inited) {
/* 257 */       return;
/*     */     }
/*     */ 
/* 260 */     _initing = true;
/*     */     try
/*     */     {
/* 263 */       DateYearGrouper dateYearGrouper = new DateYearGrouper();
/* 264 */       registerGrouper(Date.class, dateYearGrouper, DateYearGrouper.CONTEXT);
/* 265 */       registerGrouper(Calendar.class, dateYearGrouper, DateYearGrouper.CONTEXT);
/* 266 */       registerGrouper(Long.class, dateYearGrouper, DateYearGrouper.CONTEXT);
/*     */ 
/* 268 */       DateMonthGrouper dateMonthGrouper = new DateMonthGrouper();
/* 269 */       registerGrouper(Date.class, dateMonthGrouper, DateMonthGrouper.CONTEXT);
/* 270 */       registerGrouper(Calendar.class, dateMonthGrouper, DateMonthGrouper.CONTEXT);
/* 271 */       registerGrouper(Long.class, dateMonthGrouper, DateMonthGrouper.CONTEXT);
/*     */     }
/*     */     finally {
/* 274 */       _initing = false;
/* 275 */       _inited = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void resetInit()
/*     */   {
/* 286 */     _inited = false;
/*     */   }
/*     */ 
/*     */   public static void clear() {
/* 290 */     resetInit();
/* 291 */     _cache.clear();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.grouper.ObjectGrouperManager
 * JD-Core Version:    0.6.0
 */
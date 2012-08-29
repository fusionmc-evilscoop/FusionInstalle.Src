/*     */ package com.jidesoft.plaf;
/*     */ 
/*     */ import com.jidesoft.converter.ObjectConverterManager;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Insets;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import sun.reflect.Reflection;
/*     */ 
/*     */ public class UIDefaultsLookup
/*     */ {
/*  28 */   private static Logger LOGGER = Logger.getLogger(UIDefaultsLookup.class.getName());
/*     */ 
/*  30 */   private static boolean _debug = false;
/*  31 */   private static boolean _trace = false;
/*     */ 
/*     */   public static void setDebug(boolean debug)
/*     */   {
/*  39 */     _debug = debug;
/*     */   }
/*     */ 
/*     */   public static void setTrace(boolean trace)
/*     */   {
/*  49 */     _trace = trace;
/*     */   }
/*     */ 
/*     */   public static void put(UIDefaults table, String key, Object value) {
/*  53 */     Object v = table.get(key);
/*  54 */     if ((v == null) || (!(v instanceof Map))) {
/*  55 */       v = new HashMap();
/*  56 */       table.put(key, v);
/*     */     }
/*  58 */     Object cl = UIManager.get("ClassLoader");
/*  59 */     if (!(cl instanceof ClassLoader)) {
/*  60 */       cl = value.getClass().getClassLoader();
/*     */     }
/*  62 */     if (LOGGER.isLoggable(Level.FINE)) {
/*  63 */       LOGGER.fine("Put " + key + " " + value + " using ClassLoader: " + cl);
/*     */     }
/*  65 */     ((Map)v).put(cl, value);
/*     */   }
/*     */ 
/*     */   static ClassLoader getCallerClassLoader()
/*     */   {
/*  73 */     Object cl = UIManager.get("ClassLoader");
/*  74 */     if ((cl instanceof ClassLoader)) {
/*  75 */       return (ClassLoader)cl;
/*     */     }
/*     */ 
/*  79 */     Class caller = Reflection.getCallerClass(3);
/*     */ 
/*  81 */     if (caller == null) {
/*  82 */       return null;
/*     */     }
/*     */ 
/*  85 */     return caller.getClassLoader();
/*     */   }
/*     */ 
/*     */   public static Object get(Object key) {
/*  89 */     Object value = UIManager.get(key);
/*  90 */     log(value, key, null);
/*  91 */     if (((value instanceof Map)) && ("Theme.painter".equals(key))) {
/*  92 */       Map map = (Map)value;
/*     */       Iterator i$;
/*  93 */       if (LOGGER.isLoggable(Level.FINE)) {
/*  94 */         LOGGER.fine("Getting " + key + " from a map");
/*  95 */         for (i$ = map.keySet().iterator(); i$.hasNext(); ) { Object o = i$.next();
/*  96 */           LOGGER.fine("\t" + o + " => " + map.get(o)); }
/*     */       }
/*     */       try
/*     */       {
/* 100 */         ClassLoader classLoader = getCallerClassLoader();
/* 101 */         Object o = map.get(classLoader);
/* 102 */         if ((o != null) && 
/* 103 */           (LOGGER.isLoggable(Level.FINE))) {
/* 104 */           LOGGER.fine("\tGetting " + o + " using CallerClassLoader" + classLoader);
/*     */         }
/*     */ 
/* 107 */         while ((o == null) && (classLoader.getParent() != null)) {
/* 108 */           classLoader = classLoader.getParent();
/* 109 */           o = map.get(classLoader);
/* 110 */           if (o != null) {
/* 111 */             if (!LOGGER.isLoggable(Level.FINE)) break;
/* 112 */             LOGGER.fine("\tGetting " + o + " using one of the parent ClassLoader " + classLoader);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 117 */         if (o != null) return o;
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/* 122 */       if (map.size() == 1) {
/* 123 */         Object o = map.values().iterator().next();
/* 124 */         if (LOGGER.isLoggable(Level.FINE)) {
/* 125 */           LOGGER.fine("Failed...getting the only one " + o);
/*     */         }
/* 127 */         return o;
/*     */       }
/*     */ 
/* 130 */       Object o = map.get(LookAndFeelFactory.getUIManagerClassLoader());
/* 131 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 132 */         LOGGER.fine("Failed...getting " + o + " using UIManagerClassLoader " + LookAndFeelFactory.getUIManagerClassLoader());
/*     */       }
/* 134 */       return o;
/*     */     }
/*     */ 
/* 137 */     return value;
/*     */   }
/*     */ 
/*     */   public static Object get(Object key, Locale l) {
/* 141 */     Object value = UIManager.get(key, l);
/* 142 */     log(value, key, l);
/* 143 */     return value;
/*     */   }
/*     */ 
/*     */   private static void log(Object value, Object key, Locale l) {
/* 147 */     if ((_debug) && (value == null)) {
/* 148 */       System.out.println("\"" + key + (l == null ? "" : l.toString()) + " \" ==> null ------------------------");
/*     */     }
/* 150 */     else if (_trace)
/* 151 */       if (value == null) {
/* 152 */         System.out.println("\"" + key + (l == null ? "" : l.toString()) + " \" ==> null ------------------------");
/*     */       }
/*     */       else
/* 155 */         System.out.println("\"" + key + (l == null ? "" : l.toString()) + " \" ==> " + value.getClass().getName() + "(" + ObjectConverterManager.toString(value) + ")");
/*     */   }
/*     */ 
/*     */   public static Font getFont(Object key)
/*     */   {
/* 168 */     Object value = get(key);
/* 169 */     return (value instanceof Font) ? (Font)value : null;
/*     */   }
/*     */ 
/*     */   public static Font getFont(Object key, Locale l)
/*     */   {
/* 185 */     Object value = get(key, l);
/* 186 */     return (value instanceof Font) ? (Font)value : null;
/*     */   }
/*     */ 
/*     */   public static Color getColor(Object key)
/*     */   {
/* 197 */     Object value = get(key);
/* 198 */     return (value instanceof Color) ? (Color)value : null;
/*     */   }
/*     */ 
/*     */   public static Color getColor(Object key, Locale l)
/*     */   {
/* 214 */     Object value = get(key, l);
/* 215 */     return (value instanceof Color) ? (Color)value : null;
/*     */   }
/*     */ 
/*     */   public static Icon getIcon(Object key)
/*     */   {
/* 227 */     Object value = get(key);
/* 228 */     return (value instanceof Icon) ? (Icon)value : null;
/*     */   }
/*     */ 
/*     */   public static Icon getIcon(Object key, Locale l)
/*     */   {
/* 244 */     Object value = get(key, l);
/* 245 */     return (value instanceof Icon) ? (Icon)value : null;
/*     */   }
/*     */ 
/*     */   public static Border getBorder(Object key)
/*     */   {
/* 257 */     Object value = get(key);
/* 258 */     return (value instanceof Border) ? (Border)value : null;
/*     */   }
/*     */ 
/*     */   public static Border getBorder(Object key, Locale l)
/*     */   {
/* 274 */     Object value = get(key, l);
/* 275 */     return (value instanceof Border) ? (Border)value : null;
/*     */   }
/*     */ 
/*     */   public static String getString(Object key)
/*     */   {
/* 287 */     Object value = get(key);
/* 288 */     return (value instanceof String) ? (String)value : null;
/*     */   }
/*     */ 
/*     */   public static String getString(Object key, Locale l)
/*     */   {
/* 303 */     Object value = get(key, l);
/* 304 */     return (value instanceof String) ? (String)value : null;
/*     */   }
/*     */ 
/*     */   public static int getInt(Object key)
/*     */   {
/* 314 */     Object value = get(key);
/* 315 */     return (value instanceof Integer) ? ((Integer)value).intValue() : 0;
/*     */   }
/*     */ 
/*     */   public static int getInt(Object key, Locale l)
/*     */   {
/* 331 */     Object value = get(key, l);
/* 332 */     return (value instanceof Integer) ? ((Integer)value).intValue() : 0;
/*     */   }
/*     */ 
/*     */   public static boolean getBoolean(Object key)
/*     */   {
/* 345 */     Object value = get(key);
/* 346 */     return (value instanceof Boolean) ? ((Boolean)value).booleanValue() : false;
/*     */   }
/*     */ 
/*     */   public static boolean getBoolean(Object key, boolean defaultValue)
/*     */   {
/* 357 */     Object value = get(key);
/* 358 */     return (value instanceof Boolean) ? ((Boolean)value).booleanValue() : defaultValue;
/*     */   }
/*     */ 
/*     */   public static boolean getBoolean(Object key, Locale l)
/*     */   {
/* 374 */     Object value = get(key, l);
/* 375 */     return (value instanceof Boolean) ? ((Boolean)value).booleanValue() : false;
/*     */   }
/*     */ 
/*     */   public static Insets getInsets(Object key)
/*     */   {
/* 387 */     Object value = get(key);
/* 388 */     return (value instanceof Insets) ? (Insets)value : null;
/*     */   }
/*     */ 
/*     */   public static Insets getInsets(Object key, Locale l)
/*     */   {
/* 404 */     Object value = get(key, l);
/* 405 */     return (value instanceof Insets) ? (Insets)value : null;
/*     */   }
/*     */ 
/*     */   public static Dimension getDimension(Object key)
/*     */   {
/* 417 */     Object value = get(key);
/* 418 */     return (value instanceof Dimension) ? (Dimension)value : null;
/*     */   }
/*     */ 
/*     */   public static Dimension getDimension(Object key, Locale l)
/*     */   {
/* 434 */     Object value = get(key, l);
/* 435 */     return (value instanceof Dimension) ? (Dimension)value : null;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.UIDefaultsLookup
 * JD-Core Version:    0.6.0
 */
/*     */ package org.apache.log4j.helpers;
/*     */ 
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ 
/*     */ public class Loader
/*     */ {
/*     */   static final String TSTR = "Caught Exception while in Loader.getResource. This may be innocuous.";
/*  36 */   private static boolean java1 = true;
/*     */ 
/*  38 */   private static boolean ignoreTCL = false;
/*     */ 
/*     */   /** @deprecated */
/*     */   public static URL getResource(String resource, Class clazz)
/*     */   {
/*  64 */     return getResource(resource);
/*     */   }
/*     */ 
/*     */   public static URL getResource(String resource)
/*     */   {
/*  87 */     ClassLoader classLoader = null;
/*  88 */     URL url = null;
/*     */     try
/*     */     {
/*  91 */       if (!java1) {
/*  92 */         classLoader = getTCL();
/*  93 */         if (classLoader != null) {
/*  94 */           LogLog.debug("Trying to find [" + resource + "] using context classloader " + classLoader + ".");
/*     */ 
/*  96 */           url = classLoader.getResource(resource);
/*  97 */           if (url != null) {
/*  98 */             return url;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 105 */       classLoader = Loader.class.getClassLoader();
/* 106 */       if (classLoader != null) {
/* 107 */         LogLog.debug("Trying to find [" + resource + "] using " + classLoader + " class loader.");
/*     */ 
/* 109 */         url = classLoader.getResource(resource);
/* 110 */         if (url != null)
/* 111 */           return url;
/*     */       }
/*     */     }
/*     */     catch (Throwable t) {
/* 115 */       LogLog.warn("Caught Exception while in Loader.getResource. This may be innocuous.", t);
/*     */     }
/*     */ 
/* 122 */     LogLog.debug("Trying to find [" + resource + "] using ClassLoader.getSystemResource().");
/*     */ 
/* 124 */     return ClassLoader.getSystemResource(resource);
/*     */   }
/*     */ 
/*     */   public static boolean isJava1()
/*     */   {
/* 133 */     return java1;
/*     */   }
/*     */ 
/*     */   private static ClassLoader getTCL()
/*     */     throws IllegalAccessException, InvocationTargetException
/*     */   {
/* 146 */     Method method = null;
/*     */     try {
/* 148 */       method = Thread.class.getMethod("getContextClassLoader", null);
/*     */     }
/*     */     catch (NoSuchMethodException e) {
/* 151 */       return null;
/*     */     }
/*     */ 
/* 154 */     return (ClassLoader)method.invoke(Thread.currentThread(), null);
/*     */   }
/*     */ 
/*     */   public static Class loadClass(String clazz)
/*     */     throws ClassNotFoundException
/*     */   {
/* 169 */     if ((java1) || (ignoreTCL))
/* 170 */       return Class.forName(clazz);
/*     */     try
/*     */     {
/* 173 */       return getTCL().loadClass(clazz);
/*     */     }
/*     */     catch (Throwable e)
/*     */     {
/*     */     }
/* 178 */     return Class.forName(clazz);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  41 */     String prop = OptionConverter.getSystemProperty("java.version", null);
/*     */ 
/*  43 */     if (prop != null) {
/*  44 */       int i = prop.indexOf('.');
/*  45 */       if ((i != -1) && 
/*  46 */         (prop.charAt(i + 1) != '1')) {
/*  47 */         java1 = false;
/*     */       }
/*     */     }
/*  50 */     String ignoreTCLProp = OptionConverter.getSystemProperty("log4j.ignoreTCL", null);
/*  51 */     if (ignoreTCLProp != null)
/*  52 */       ignoreTCL = OptionConverter.toBoolean(ignoreTCLProp, true);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.helpers.Loader
 * JD-Core Version:    0.6.0
 */
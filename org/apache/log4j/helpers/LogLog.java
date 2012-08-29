/*     */ package org.apache.log4j.helpers;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ public class LogLog
/*     */ {
/*     */   public static final String DEBUG_KEY = "log4j.debug";
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final String CONFIG_DEBUG_KEY = "log4j.configDebug";
/*  59 */   protected static boolean debugEnabled = false;
/*     */ 
/*  64 */   private static boolean quietMode = false;
/*     */   private static final String PREFIX = "log4j: ";
/*     */   private static final String ERR_PREFIX = "log4j:ERROR ";
/*     */   private static final String WARN_PREFIX = "log4j:WARN ";
/*     */ 
/*     */   public static void setInternalDebugging(boolean enabled)
/*     */   {
/*  88 */     debugEnabled = enabled;
/*     */   }
/*     */ 
/*     */   public static void debug(String msg)
/*     */   {
/*  98 */     if ((debugEnabled) && (!quietMode))
/*  99 */       System.out.println("log4j: " + msg);
/*     */   }
/*     */ 
/*     */   public static void debug(String msg, Throwable t)
/*     */   {
/* 110 */     if ((debugEnabled) && (!quietMode)) {
/* 111 */       System.out.println("log4j: " + msg);
/* 112 */       if (t != null)
/* 113 */         t.printStackTrace(System.out);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void error(String msg)
/*     */   {
/* 126 */     if (quietMode)
/* 127 */       return;
/* 128 */     System.err.println("log4j:ERROR " + msg);
/*     */   }
/*     */ 
/*     */   public static void error(String msg, Throwable t)
/*     */   {
/* 139 */     if (quietMode) {
/* 140 */       return;
/*     */     }
/* 142 */     System.err.println("log4j:ERROR " + msg);
/* 143 */     if (t != null)
/* 144 */       t.printStackTrace();
/*     */   }
/*     */ 
/*     */   public static void setQuietMode(boolean quietMode)
/*     */   {
/* 157 */     quietMode = quietMode;
/*     */   }
/*     */ 
/*     */   public static void warn(String msg)
/*     */   {
/* 167 */     if (quietMode) {
/* 168 */       return;
/*     */     }
/* 170 */     System.err.println("log4j:WARN " + msg);
/*     */   }
/*     */ 
/*     */   public static void warn(String msg, Throwable t)
/*     */   {
/* 180 */     if (quietMode) {
/* 181 */       return;
/*     */     }
/* 183 */     System.err.println("log4j:WARN " + msg);
/* 184 */     if (t != null)
/* 185 */       t.printStackTrace();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  71 */     String key = OptionConverter.getSystemProperty("log4j.debug", null);
/*     */ 
/*  73 */     if (key == null) {
/*  74 */       key = OptionConverter.getSystemProperty("log4j.configDebug", null);
/*     */     }
/*     */ 
/*  77 */     if (key != null)
/*  78 */       debugEnabled = OptionConverter.toBoolean(key, true);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.helpers.LogLog
 * JD-Core Version:    0.6.0
 */
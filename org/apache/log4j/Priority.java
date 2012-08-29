/*     */ package org.apache.log4j;
/*     */ 
/*     */ public class Priority
/*     */ {
/*     */   transient int level;
/*     */   transient String levelStr;
/*     */   transient int syslogEquivalent;
/*     */   public static final int OFF_INT = 2147483647;
/*     */   public static final int FATAL_INT = 50000;
/*     */   public static final int ERROR_INT = 40000;
/*     */   public static final int WARN_INT = 30000;
/*     */   public static final int INFO_INT = 20000;
/*     */   public static final int DEBUG_INT = 10000;
/*     */   public static final int ALL_INT = -2147483648;
/*     */ 
/*     */   /** @deprecated */
/*  44 */   public static final Priority FATAL = new Level(50000, "FATAL", 0);
/*     */ 
/*     */   /** @deprecated */
/*  49 */   public static final Priority ERROR = new Level(40000, "ERROR", 3);
/*     */ 
/*     */   /** @deprecated */
/*  54 */   public static final Priority WARN = new Level(30000, "WARN", 4);
/*     */ 
/*     */   /** @deprecated */
/*  59 */   public static final Priority INFO = new Level(20000, "INFO", 6);
/*     */ 
/*     */   /** @deprecated */
/*  64 */   public static final Priority DEBUG = new Level(10000, "DEBUG", 7);
/*     */ 
/*     */   protected Priority()
/*     */   {
/*  71 */     this.level = 10000;
/*  72 */     this.levelStr = "DEBUG";
/*  73 */     this.syslogEquivalent = 7;
/*     */   }
/*     */ 
/*     */   protected Priority(int level, String levelStr, int syslogEquivalent)
/*     */   {
/*  81 */     this.level = level;
/*  82 */     this.levelStr = levelStr;
/*  83 */     this.syslogEquivalent = syslogEquivalent;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/*  92 */     if ((o instanceof Priority)) {
/*  93 */       Priority r = (Priority)o;
/*  94 */       return this.level == r.level;
/*     */     }
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */   public final int getSyslogEquivalent()
/*     */   {
/* 106 */     return this.syslogEquivalent;
/*     */   }
/*     */ 
/*     */   public boolean isGreaterOrEqual(Priority r)
/*     */   {
/* 122 */     return this.level >= r.level;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public static Priority[] getAllPossiblePriorities()
/*     */   {
/* 134 */     return new Priority[] { FATAL, ERROR, Level.WARN, INFO, DEBUG };
/*     */   }
/*     */ 
/*     */   public final String toString()
/*     */   {
/* 145 */     return this.levelStr;
/*     */   }
/*     */ 
/*     */   public final int toInt()
/*     */   {
/* 154 */     return this.level;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public static Priority toPriority(String sArg)
/*     */   {
/* 163 */     return Level.toLevel(sArg);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public static Priority toPriority(int val)
/*     */   {
/* 172 */     return toPriority(val, DEBUG);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public static Priority toPriority(int val, Priority defaultPriority)
/*     */   {
/* 181 */     return Level.toLevel(val, (Level)defaultPriority);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public static Priority toPriority(String sArg, Priority defaultPriority)
/*     */   {
/* 190 */     return Level.toLevel(sArg, (Level)defaultPriority);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.Priority
 * JD-Core Version:    0.6.0
 */
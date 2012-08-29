/*     */ package org.apache.log4j.lf5.util;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import org.apache.log4j.lf5.LogLevel;
/*     */ import org.apache.log4j.lf5.LogRecord;
/*     */ 
/*     */ public class AdapterLogRecord extends LogRecord
/*     */ {
/*  44 */   private static LogLevel severeLevel = null;
/*     */ 
/*  46 */   private static StringWriter sw = new StringWriter();
/*  47 */   private static PrintWriter pw = new PrintWriter(sw);
/*     */ 
/*     */   public void setCategory(String category)
/*     */   {
/*  60 */     super.setCategory(category);
/*  61 */     super.setLocation(getLocationInfo(category));
/*     */   }
/*     */ 
/*     */   public boolean isSevereLevel() {
/*  65 */     if (severeLevel == null) return false;
/*  66 */     return severeLevel.equals(getLevel());
/*     */   }
/*     */ 
/*     */   public static void setSevereLevel(LogLevel level) {
/*  70 */     severeLevel = level;
/*     */   }
/*     */ 
/*     */   public static LogLevel getSevereLevel() {
/*  74 */     return severeLevel;
/*     */   }
/*     */ 
/*     */   protected String getLocationInfo(String category)
/*     */   {
/*  81 */     String stackTrace = stackTraceToString(new Throwable());
/*  82 */     String line = parseLine(stackTrace, category);
/*  83 */     return line;
/*     */   }
/*     */ 
/*     */   protected String stackTraceToString(Throwable t) {
/*  87 */     String s = null;
/*     */ 
/*  89 */     synchronized (sw) {
/*  90 */       t.printStackTrace(pw);
/*  91 */       s = sw.toString();
/*  92 */       sw.getBuffer().setLength(0);
/*     */     }
/*     */ 
/*  95 */     return s;
/*     */   }
/*     */ 
/*     */   protected String parseLine(String trace, String category) {
/*  99 */     int index = trace.indexOf(category);
/* 100 */     if (index == -1) return null;
/* 101 */     trace = trace.substring(index);
/* 102 */     trace = trace.substring(0, trace.indexOf(")") + 1);
/* 103 */     return trace;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.util.AdapterLogRecord
 * JD-Core Version:    0.6.0
 */
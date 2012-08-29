/*     */ package org.apache.log4j.lf5.util;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Toolkit;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.lf5.LogLevel;
/*     */ import org.apache.log4j.lf5.LogRecord;
/*     */ import org.apache.log4j.lf5.viewer.LogBrokerMonitor;
/*     */ 
/*     */ public class LogMonitorAdapter
/*     */ {
/*     */   public static final int LOG4J_LOG_LEVELS = 0;
/*     */   public static final int JDK14_LOG_LEVELS = 1;
/*     */   private LogBrokerMonitor _logMonitor;
/*  48 */   private LogLevel _defaultLevel = null;
/*     */ 
/*     */   private LogMonitorAdapter(List userDefinedLevels)
/*     */   {
/*  56 */     this._defaultLevel = ((LogLevel)userDefinedLevels.get(0));
/*  57 */     this._logMonitor = new LogBrokerMonitor(userDefinedLevels);
/*     */ 
/*  59 */     this._logMonitor.setFrameSize(getDefaultMonitorWidth(), getDefaultMonitorHeight());
/*     */ 
/*  61 */     this._logMonitor.setFontSize(12);
/*  62 */     this._logMonitor.show();
/*     */   }
/*     */ 
/*     */   public static LogMonitorAdapter newInstance(int loglevels)
/*     */   {
/*     */     LogMonitorAdapter adapter;
/*  77 */     if (loglevels == 1) {
/*  78 */       adapter = newInstance(LogLevel.getJdk14Levels());
/*  79 */       adapter.setDefaultLevel(LogLevel.FINEST);
/*  80 */       adapter.setSevereLevel(LogLevel.SEVERE);
/*     */     } else {
/*  82 */       adapter = newInstance(LogLevel.getLog4JLevels());
/*  83 */       adapter.setDefaultLevel(LogLevel.DEBUG);
/*  84 */       adapter.setSevereLevel(LogLevel.FATAL);
/*     */     }
/*  86 */     return adapter;
/*     */   }
/*     */ 
/*     */   public static LogMonitorAdapter newInstance(LogLevel[] userDefined)
/*     */   {
/*  98 */     if (userDefined == null) {
/*  99 */       return null;
/*     */     }
/* 101 */     return newInstance(Arrays.asList(userDefined));
/*     */   }
/*     */ 
/*     */   public static LogMonitorAdapter newInstance(List userDefinedLevels)
/*     */   {
/* 113 */     return new LogMonitorAdapter(userDefinedLevels);
/*     */   }
/*     */ 
/*     */   public void addMessage(LogRecord record)
/*     */   {
/* 122 */     this._logMonitor.addMessage(record);
/*     */   }
/*     */ 
/*     */   public void setMaxNumberOfRecords(int maxNumberOfRecords)
/*     */   {
/* 131 */     this._logMonitor.setMaxNumberOfLogRecords(maxNumberOfRecords);
/*     */   }
/*     */ 
/*     */   public void setDefaultLevel(LogLevel level)
/*     */   {
/* 141 */     this._defaultLevel = level;
/*     */   }
/*     */ 
/*     */   public LogLevel getDefaultLevel()
/*     */   {
/* 150 */     return this._defaultLevel;
/*     */   }
/*     */ 
/*     */   public void setSevereLevel(LogLevel level)
/*     */   {
/* 159 */     AdapterLogRecord.setSevereLevel(level);
/*     */   }
/*     */ 
/*     */   public LogLevel getSevereLevel()
/*     */   {
/* 168 */     return AdapterLogRecord.getSevereLevel();
/*     */   }
/*     */ 
/*     */   public void log(String category, LogLevel level, String message, Throwable t, String NDC)
/*     */   {
/* 183 */     AdapterLogRecord record = new AdapterLogRecord();
/* 184 */     record.setCategory(category);
/* 185 */     record.setMessage(message);
/* 186 */     record.setNDC(NDC);
/* 187 */     record.setThrown(t);
/*     */ 
/* 189 */     if (level == null)
/* 190 */       record.setLevel(getDefaultLevel());
/*     */     else {
/* 192 */       record.setLevel(level);
/*     */     }
/*     */ 
/* 195 */     addMessage(record);
/*     */   }
/*     */ 
/*     */   public void log(String category, String message)
/*     */   {
/* 205 */     log(category, null, message);
/*     */   }
/*     */ 
/*     */   public void log(String category, LogLevel level, String message, String NDC)
/*     */   {
/* 217 */     log(category, level, message, null, NDC);
/*     */   }
/*     */ 
/*     */   public void log(String category, LogLevel level, String message, Throwable t)
/*     */   {
/* 230 */     log(category, level, message, t, null);
/*     */   }
/*     */ 
/*     */   public void log(String category, LogLevel level, String message)
/*     */   {
/* 241 */     log(category, level, message, null, null);
/*     */   }
/*     */ 
/*     */   protected static int getScreenWidth()
/*     */   {
/*     */     try
/*     */     {
/* 254 */       return Toolkit.getDefaultToolkit().getScreenSize().width; } catch (Throwable t) {
/*     */     }
/* 256 */     return 800;
/*     */   }
/*     */ 
/*     */   protected static int getScreenHeight()
/*     */   {
/*     */     try
/*     */     {
/* 267 */       return Toolkit.getDefaultToolkit().getScreenSize().height; } catch (Throwable t) {
/*     */     }
/* 269 */     return 600;
/*     */   }
/*     */ 
/*     */   protected static int getDefaultMonitorWidth()
/*     */   {
/* 274 */     return 3 * getScreenWidth() / 4;
/*     */   }
/*     */ 
/*     */   protected static int getDefaultMonitorHeight() {
/* 278 */     return 3 * getScreenHeight() / 4;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.util.LogMonitorAdapter
 * JD-Core Version:    0.6.0
 */
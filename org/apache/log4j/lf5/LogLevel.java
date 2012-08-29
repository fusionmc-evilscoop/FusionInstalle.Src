/*     */ package org.apache.log4j.lf5;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class LogLevel
/*     */   implements Serializable
/*     */ {
/*  44 */   public static final LogLevel FATAL = new LogLevel("FATAL", 0);
/*  45 */   public static final LogLevel ERROR = new LogLevel("ERROR", 1);
/*  46 */   public static final LogLevel WARN = new LogLevel("WARN", 2);
/*  47 */   public static final LogLevel INFO = new LogLevel("INFO", 3);
/*  48 */   public static final LogLevel DEBUG = new LogLevel("DEBUG", 4);
/*     */ 
/*  51 */   public static final LogLevel SEVERE = new LogLevel("SEVERE", 1);
/*  52 */   public static final LogLevel WARNING = new LogLevel("WARNING", 2);
/*  53 */   public static final LogLevel CONFIG = new LogLevel("CONFIG", 4);
/*  54 */   public static final LogLevel FINE = new LogLevel("FINE", 5);
/*  55 */   public static final LogLevel FINER = new LogLevel("FINER", 6);
/*  56 */   public static final LogLevel FINEST = new LogLevel("FINEST", 7);
/*     */   protected String _label;
/*     */   protected int _precedence;
/*     */   private static LogLevel[] _log4JLevels;
/*     */   private static LogLevel[] _jdk14Levels;
/*     */   private static LogLevel[] _allDefaultLevels;
/*     */   private static Map _logLevelMap;
/*     */   private static Map _logLevelColorMap;
/*  71 */   private static Map _registeredLogLevelMap = new HashMap();
/*     */ 
/*     */   public LogLevel(String label, int precedence)
/*     */   {
/*  96 */     this._label = label;
/*  97 */     this._precedence = precedence;
/*     */   }
/*     */ 
/*     */   public String getLabel()
/*     */   {
/* 108 */     return this._label;
/*     */   }
/*     */ 
/*     */   public boolean encompasses(LogLevel level)
/*     */   {
/* 119 */     return level.getPrecedence() <= getPrecedence();
/*     */   }
/*     */ 
/*     */   public static LogLevel valueOf(String level)
/*     */     throws LogLevelFormatException
/*     */   {
/* 135 */     LogLevel logLevel = null;
/* 136 */     if (level != null) {
/* 137 */       level = level.trim().toUpperCase();
/* 138 */       logLevel = (LogLevel)_logLevelMap.get(level);
/*     */     }
/*     */ 
/* 142 */     if ((logLevel == null) && (_registeredLogLevelMap.size() > 0)) {
/* 143 */       logLevel = (LogLevel)_registeredLogLevelMap.get(level);
/*     */     }
/*     */ 
/* 146 */     if (logLevel == null) {
/* 147 */       StringBuffer buf = new StringBuffer();
/* 148 */       buf.append("Error while trying to parse (" + level + ") into");
/* 149 */       buf.append(" a LogLevel.");
/* 150 */       throw new LogLevelFormatException(buf.toString());
/*     */     }
/* 152 */     return logLevel;
/*     */   }
/*     */ 
/*     */   public static LogLevel register(LogLevel logLevel)
/*     */   {
/* 162 */     if (logLevel == null) return null;
/*     */ 
/* 165 */     if (_logLevelMap.get(logLevel.getLabel()) == null) {
/* 166 */       return (LogLevel)_registeredLogLevelMap.put(logLevel.getLabel(), logLevel);
/*     */     }
/*     */ 
/* 169 */     return null;
/*     */   }
/*     */ 
/*     */   public static void register(LogLevel[] logLevels) {
/* 173 */     if (logLevels != null)
/* 174 */       for (int i = 0; i < logLevels.length; i++)
/* 175 */         register(logLevels[i]);
/*     */   }
/*     */ 
/*     */   public static void register(List logLevels)
/*     */   {
/* 181 */     if (logLevels != null) {
/* 182 */       Iterator it = logLevels.iterator();
/* 183 */       while (it.hasNext())
/* 184 */         register((LogLevel)it.next());
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/* 190 */     boolean equals = false;
/*     */ 
/* 192 */     if (((o instanceof LogLevel)) && 
/* 193 */       (getPrecedence() == ((LogLevel)o).getPrecedence()))
/*     */     {
/* 195 */       equals = true;
/*     */     }
/*     */ 
/* 200 */     return equals;
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/* 204 */     return this._label.hashCode();
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 208 */     return this._label;
/*     */   }
/*     */ 
/*     */   public void setLogLevelColorMap(LogLevel level, Color color)
/*     */   {
/* 214 */     _logLevelColorMap.remove(level);
/*     */ 
/* 216 */     if (color == null) {
/* 217 */       color = Color.black;
/*     */     }
/* 219 */     _logLevelColorMap.put(level, color);
/*     */   }
/*     */ 
/*     */   public static void resetLogLevelColorMap()
/*     */   {
/* 224 */     _logLevelColorMap.clear();
/*     */ 
/* 227 */     for (int i = 0; i < _allDefaultLevels.length; i++)
/* 228 */       _logLevelColorMap.put(_allDefaultLevels[i], Color.black);
/*     */   }
/*     */ 
/*     */   public static List getLog4JLevels()
/*     */   {
/* 237 */     return Arrays.asList(_log4JLevels);
/*     */   }
/*     */ 
/*     */   public static List getJdk14Levels() {
/* 241 */     return Arrays.asList(_jdk14Levels);
/*     */   }
/*     */ 
/*     */   public static List getAllDefaultLevels() {
/* 245 */     return Arrays.asList(_allDefaultLevels);
/*     */   }
/*     */ 
/*     */   public static Map getLogLevelColorMap() {
/* 249 */     return _logLevelColorMap;
/*     */   }
/*     */ 
/*     */   protected int getPrecedence()
/*     */   {
/* 257 */     return this._precedence;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  77 */     _log4JLevels = new LogLevel[] { FATAL, ERROR, WARN, INFO, DEBUG };
/*  78 */     _jdk14Levels = new LogLevel[] { SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST };
/*     */ 
/*  80 */     _allDefaultLevels = new LogLevel[] { FATAL, ERROR, WARN, INFO, DEBUG, SEVERE, WARNING, CONFIG, FINE, FINER, FINEST };
/*     */ 
/*  83 */     _logLevelMap = new HashMap();
/*  84 */     for (int i = 0; i < _allDefaultLevels.length; i++) {
/*  85 */       _logLevelMap.put(_allDefaultLevels[i].getLabel(), _allDefaultLevels[i]);
/*     */     }
/*     */ 
/*  89 */     _logLevelColorMap = new HashMap();
/*  90 */     for (int i = 0; i < _allDefaultLevels.length; i++)
/*  91 */       _logLevelColorMap.put(_allDefaultLevels[i], Color.black);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.LogLevel
 * JD-Core Version:    0.6.0
 */
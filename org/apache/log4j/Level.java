/*     */ package org.apache.log4j;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Level extends Priority
/*     */   implements Serializable
/*     */ {
/*     */   public static final int TRACE_INT = 5000;
/*  50 */   public static final Level OFF = new Level(2147483647, "OFF", 0);
/*     */ 
/*  56 */   public static final Level FATAL = new Level(50000, "FATAL", 0);
/*     */ 
/*  61 */   public static final Level ERROR = new Level(40000, "ERROR", 3);
/*     */ 
/*  66 */   public static final Level WARN = new Level(30000, "WARN", 4);
/*     */ 
/*  72 */   public static final Level INFO = new Level(20000, "INFO", 6);
/*     */ 
/*  78 */   public static final Level DEBUG = new Level(10000, "DEBUG", 7);
/*     */ 
/*  85 */   public static final Level TRACE = new Level(5000, "TRACE", 7);
/*     */ 
/*  91 */   public static final Level ALL = new Level(-2147483648, "ALL", 7);
/*     */   static final long serialVersionUID = 3491141966387921974L;
/*     */ 
/*     */   protected Level(int level, String levelStr, int syslogEquivalent)
/*     */   {
/* 103 */     super(level, levelStr, syslogEquivalent);
/*     */   }
/*     */ 
/*     */   public static Level toLevel(String sArg)
/*     */   {
/* 114 */     return toLevel(sArg, DEBUG);
/*     */   }
/*     */ 
/*     */   public static Level toLevel(int val)
/*     */   {
/* 125 */     return toLevel(val, DEBUG);
/*     */   }
/*     */ 
/*     */   public static Level toLevel(int val, Level defaultLevel)
/*     */   {
/* 135 */     switch (val) { case -2147483648:
/* 136 */       return ALL;
/*     */     case 10000:
/* 137 */       return DEBUG;
/*     */     case 20000:
/* 138 */       return INFO;
/*     */     case 30000:
/* 139 */       return WARN;
/*     */     case 40000:
/* 140 */       return ERROR;
/*     */     case 50000:
/* 141 */       return FATAL;
/*     */     case 2147483647:
/* 142 */       return OFF;
/*     */     case 5000:
/* 143 */       return TRACE; }
/* 144 */     return defaultLevel;
/*     */   }
/*     */ 
/*     */   public static Level toLevel(String sArg, Level defaultLevel)
/*     */   {
/* 156 */     if (sArg == null) {
/* 157 */       return defaultLevel;
/*     */     }
/* 159 */     String s = sArg.toUpperCase();
/*     */ 
/* 161 */     if (s.equals("ALL")) return ALL;
/* 162 */     if (s.equals("DEBUG")) return DEBUG;
/* 163 */     if (s.equals("INFO")) return INFO;
/* 164 */     if (s.equals("WARN")) return WARN;
/* 165 */     if (s.equals("ERROR")) return ERROR;
/* 166 */     if (s.equals("FATAL")) return FATAL;
/* 167 */     if (s.equals("OFF")) return OFF;
/* 168 */     if (s.equals("TRACE")) return TRACE;
/* 169 */     return defaultLevel;
/*     */   }
/*     */ 
/*     */   private void readObject(ObjectInputStream s)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 179 */     s.defaultReadObject();
/* 180 */     this.level = s.readInt();
/* 181 */     this.syslogEquivalent = s.readInt();
/* 182 */     this.levelStr = s.readUTF();
/* 183 */     if (this.levelStr == null)
/* 184 */       this.levelStr = "";
/*     */   }
/*     */ 
/*     */   private void writeObject(ObjectOutputStream s)
/*     */     throws IOException
/*     */   {
/* 194 */     s.defaultWriteObject();
/* 195 */     s.writeInt(this.level);
/* 196 */     s.writeInt(this.syslogEquivalent);
/* 197 */     s.writeUTF(this.levelStr);
/*     */   }
/*     */ 
/*     */   private Object readResolve()
/*     */     throws ObjectStreamException
/*     */   {
/* 210 */     if (getClass() == Level.class) {
/* 211 */       return toLevel(this.level);
/*     */     }
/*     */ 
/* 216 */     return this;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.Level
 * JD-Core Version:    0.6.0
 */
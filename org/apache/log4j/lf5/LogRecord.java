/*     */ package org.apache.log4j.lf5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Serializable;
/*     */ import java.io.StringWriter;
/*     */ 
/*     */ public abstract class LogRecord
/*     */   implements Serializable
/*     */ {
/*  40 */   protected static long _seqCount = 0L;
/*     */   protected LogLevel _level;
/*     */   protected String _message;
/*     */   protected long _sequenceNumber;
/*     */   protected long _millis;
/*     */   protected String _category;
/*     */   protected String _thread;
/*     */   protected String _thrownStackTrace;
/*     */   protected Throwable _thrown;
/*     */   protected String _ndc;
/*     */   protected String _location;
/*     */ 
/*     */   public LogRecord()
/*     */   {
/*  64 */     this._millis = System.currentTimeMillis();
/*  65 */     this._category = "Debug";
/*  66 */     this._message = "";
/*  67 */     this._level = LogLevel.INFO;
/*  68 */     this._sequenceNumber = getNextId();
/*  69 */     this._thread = Thread.currentThread().toString();
/*  70 */     this._ndc = "";
/*  71 */     this._location = "";
/*     */   }
/*     */ 
/*     */   public LogLevel getLevel()
/*     */   {
/*  86 */     return this._level;
/*     */   }
/*     */ 
/*     */   public void setLevel(LogLevel level)
/*     */   {
/*  97 */     this._level = level;
/*     */   }
/*     */ 
/*     */   public abstract boolean isSevereLevel();
/*     */ 
/*     */   public boolean hasThrown()
/*     */   {
/* 110 */     Throwable thrown = getThrown();
/* 111 */     if (thrown == null) {
/* 112 */       return false;
/*     */     }
/* 114 */     String thrownString = thrown.toString();
/* 115 */     return (thrownString != null) && (thrownString.trim().length() != 0);
/*     */   }
/*     */ 
/*     */   public boolean isFatal()
/*     */   {
/* 122 */     return (isSevereLevel()) || (hasThrown());
/*     */   }
/*     */ 
/*     */   public String getCategory()
/*     */   {
/* 133 */     return this._category;
/*     */   }
/*     */ 
/*     */   public void setCategory(String category)
/*     */   {
/* 155 */     this._category = category;
/*     */   }
/*     */ 
/*     */   public String getMessage()
/*     */   {
/* 165 */     return this._message;
/*     */   }
/*     */ 
/*     */   public void setMessage(String message)
/*     */   {
/* 175 */     this._message = message;
/*     */   }
/*     */ 
/*     */   public long getSequenceNumber()
/*     */   {
/* 187 */     return this._sequenceNumber;
/*     */   }
/*     */ 
/*     */   public void setSequenceNumber(long number)
/*     */   {
/* 199 */     this._sequenceNumber = number;
/*     */   }
/*     */ 
/*     */   public long getMillis()
/*     */   {
/* 211 */     return this._millis;
/*     */   }
/*     */ 
/*     */   public void setMillis(long millis)
/*     */   {
/* 222 */     this._millis = millis;
/*     */   }
/*     */ 
/*     */   public String getThreadDescription()
/*     */   {
/* 235 */     return this._thread;
/*     */   }
/*     */ 
/*     */   public void setThreadDescription(String threadDescription)
/*     */   {
/* 248 */     this._thread = threadDescription;
/*     */   }
/*     */ 
/*     */   public String getThrownStackTrace()
/*     */   {
/* 269 */     return this._thrownStackTrace;
/*     */   }
/*     */ 
/*     */   public void setThrownStackTrace(String trace)
/*     */   {
/* 279 */     this._thrownStackTrace = trace;
/*     */   }
/*     */ 
/*     */   public Throwable getThrown()
/*     */   {
/* 290 */     return this._thrown;
/*     */   }
/*     */ 
/*     */   public void setThrown(Throwable thrown)
/*     */   {
/* 303 */     if (thrown == null) {
/* 304 */       return;
/*     */     }
/* 306 */     this._thrown = thrown;
/* 307 */     StringWriter sw = new StringWriter();
/* 308 */     PrintWriter out = new PrintWriter(sw);
/* 309 */     thrown.printStackTrace(out);
/* 310 */     out.flush();
/* 311 */     this._thrownStackTrace = sw.toString();
/*     */     try {
/* 313 */       out.close();
/* 314 */       sw.close();
/*     */     }
/*     */     catch (IOException e) {
/*     */     }
/* 318 */     out = null;
/* 319 */     sw = null;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 326 */     StringBuffer buf = new StringBuffer();
/* 327 */     buf.append("LogRecord: [" + this._level + ", " + this._message + "]");
/* 328 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public String getNDC()
/*     */   {
/* 337 */     return this._ndc;
/*     */   }
/*     */ 
/*     */   public void setNDC(String ndc)
/*     */   {
/* 346 */     this._ndc = ndc;
/*     */   }
/*     */ 
/*     */   public String getLocation()
/*     */   {
/* 355 */     return this._location;
/*     */   }
/*     */ 
/*     */   public void setLocation(String location)
/*     */   {
/* 364 */     this._location = location;
/*     */   }
/*     */ 
/*     */   public static synchronized void resetSequenceNumber()
/*     */   {
/* 372 */     _seqCount = 0L;
/*     */   }
/*     */ 
/*     */   protected static synchronized long getNextId()
/*     */   {
/* 380 */     _seqCount += 1L;
/* 381 */     return _seqCount;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.LogRecord
 * JD-Core Version:    0.6.0
 */
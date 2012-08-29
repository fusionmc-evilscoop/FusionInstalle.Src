/*     */ package org.apache.log4j;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ import org.apache.log4j.spi.ErrorHandler;
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ public class DailyRollingFileAppender extends FileAppender
/*     */ {
/*     */   static final int TOP_OF_TROUBLE = -1;
/*     */   static final int TOP_OF_MINUTE = 0;
/*     */   static final int TOP_OF_HOUR = 1;
/*     */   static final int HALF_DAY = 2;
/*     */   static final int TOP_OF_DAY = 3;
/*     */   static final int TOP_OF_WEEK = 4;
/*     */   static final int TOP_OF_MONTH = 5;
/* 152 */   private String datePattern = "'.'yyyy-MM-dd";
/*     */   private String scheduledFilename;
/* 168 */   private long nextCheck = System.currentTimeMillis() - 1L;
/*     */ 
/* 170 */   Date now = new Date();
/*     */   SimpleDateFormat sdf;
/* 174 */   RollingCalendar rc = new RollingCalendar();
/*     */ 
/* 176 */   int checkPeriod = -1;
/*     */ 
/* 179 */   static final TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
/*     */ 
/*     */   public DailyRollingFileAppender()
/*     */   {
/*     */   }
/*     */ 
/*     */   public DailyRollingFileAppender(Layout layout, String filename, String datePattern)
/*     */     throws IOException
/*     */   {
/* 195 */     super(layout, filename, true);
/* 196 */     this.datePattern = datePattern;
/* 197 */     activateOptions();
/*     */   }
/*     */ 
/*     */   public void setDatePattern(String pattern)
/*     */   {
/* 206 */     this.datePattern = pattern;
/*     */   }
/*     */ 
/*     */   public String getDatePattern()
/*     */   {
/* 211 */     return this.datePattern;
/*     */   }
/*     */ 
/*     */   public void activateOptions() {
/* 215 */     super.activateOptions();
/* 216 */     if ((this.datePattern != null) && (this.fileName != null)) {
/* 217 */       this.now.setTime(System.currentTimeMillis());
/* 218 */       this.sdf = new SimpleDateFormat(this.datePattern);
/* 219 */       int type = computeCheckPeriod();
/* 220 */       printPeriodicity(type);
/* 221 */       this.rc.setType(type);
/* 222 */       File file = new File(this.fileName);
/* 223 */       this.scheduledFilename = (this.fileName + this.sdf.format(new Date(file.lastModified())));
/*     */     }
/*     */     else {
/* 226 */       LogLog.error("Either File or DatePattern options are not set for appender [" + this.name + "].");
/*     */     }
/*     */   }
/*     */ 
/*     */   void printPeriodicity(int type)
/*     */   {
/* 232 */     switch (type) {
/*     */     case 0:
/* 234 */       LogLog.debug("Appender [" + this.name + "] to be rolled every minute.");
/* 235 */       break;
/*     */     case 1:
/* 237 */       LogLog.debug("Appender [" + this.name + "] to be rolled on top of every hour.");
/*     */ 
/* 239 */       break;
/*     */     case 2:
/* 241 */       LogLog.debug("Appender [" + this.name + "] to be rolled at midday and midnight.");
/*     */ 
/* 243 */       break;
/*     */     case 3:
/* 245 */       LogLog.debug("Appender [" + this.name + "] to be rolled at midnight.");
/*     */ 
/* 247 */       break;
/*     */     case 4:
/* 249 */       LogLog.debug("Appender [" + this.name + "] to be rolled at start of week.");
/*     */ 
/* 251 */       break;
/*     */     case 5:
/* 253 */       LogLog.debug("Appender [" + this.name + "] to be rolled at start of every month.");
/*     */ 
/* 255 */       break;
/*     */     default:
/* 257 */       LogLog.warn("Unknown periodicity for appender [" + this.name + "].");
/*     */     }
/*     */   }
/*     */ 
/*     */   int computeCheckPeriod()
/*     */   {
/* 272 */     RollingCalendar rollingCalendar = new RollingCalendar(gmtTimeZone, Locale.ENGLISH);
/*     */ 
/* 274 */     Date epoch = new Date(0L);
/* 275 */     if (this.datePattern != null) {
/* 276 */       for (int i = 0; i <= 5; i++) {
/* 277 */         SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.datePattern);
/* 278 */         simpleDateFormat.setTimeZone(gmtTimeZone);
/* 279 */         String r0 = simpleDateFormat.format(epoch);
/* 280 */         rollingCalendar.setType(i);
/* 281 */         Date next = new Date(rollingCalendar.getNextCheckMillis(epoch));
/* 282 */         String r1 = simpleDateFormat.format(next);
/*     */ 
/* 284 */         if ((r0 != null) && (r1 != null) && (!r0.equals(r1))) {
/* 285 */           return i;
/*     */         }
/*     */       }
/*     */     }
/* 289 */     return -1;
/*     */   }
/*     */ 
/*     */   void rollOver()
/*     */     throws IOException
/*     */   {
/* 298 */     if (this.datePattern == null) {
/* 299 */       this.errorHandler.error("Missing DatePattern option in rollOver().");
/* 300 */       return;
/*     */     }
/*     */ 
/* 303 */     String datedFilename = this.fileName + this.sdf.format(this.now);
/*     */ 
/* 307 */     if (this.scheduledFilename.equals(datedFilename)) {
/* 308 */       return;
/*     */     }
/*     */ 
/* 312 */     closeFile();
/*     */ 
/* 314 */     File target = new File(this.scheduledFilename);
/* 315 */     if (target.exists()) {
/* 316 */       target.delete();
/*     */     }
/*     */ 
/* 319 */     File file = new File(this.fileName);
/* 320 */     boolean result = file.renameTo(target);
/* 321 */     if (result)
/* 322 */       LogLog.debug(this.fileName + " -> " + this.scheduledFilename);
/*     */     else {
/* 324 */       LogLog.error("Failed to rename [" + this.fileName + "] to [" + this.scheduledFilename + "].");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 330 */       setFile(this.fileName, false, this.bufferedIO, this.bufferSize);
/*     */     }
/*     */     catch (IOException e) {
/* 333 */       this.errorHandler.error("setFile(" + this.fileName + ", false) call failed.");
/*     */     }
/* 335 */     this.scheduledFilename = datedFilename;
/*     */   }
/*     */ 
/*     */   protected void subAppend(LoggingEvent event)
/*     */   {
/* 347 */     long n = System.currentTimeMillis();
/* 348 */     if (n >= this.nextCheck) {
/* 349 */       this.now.setTime(n);
/* 350 */       this.nextCheck = this.rc.getNextCheckMillis(this.now);
/*     */       try {
/* 352 */         rollOver();
/*     */       }
/*     */       catch (IOException ioe) {
/* 355 */         LogLog.error("rollOver() failed.", ioe);
/*     */       }
/*     */     }
/* 358 */     super.subAppend(event);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.DailyRollingFileAppender
 * JD-Core Version:    0.6.0
 */
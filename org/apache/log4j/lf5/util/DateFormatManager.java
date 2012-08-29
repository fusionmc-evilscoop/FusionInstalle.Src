/*     */ package org.apache.log4j.lf5.util;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ public class DateFormatManager
/*     */ {
/*  49 */   private TimeZone _timeZone = null;
/*  50 */   private Locale _locale = null;
/*     */ 
/*  52 */   private String _pattern = null;
/*  53 */   private DateFormat _dateFormat = null;
/*     */ 
/*     */   public DateFormatManager()
/*     */   {
/*  60 */     configure();
/*     */   }
/*     */ 
/*     */   public DateFormatManager(TimeZone timeZone)
/*     */   {
/*  66 */     this._timeZone = timeZone;
/*  67 */     configure();
/*     */   }
/*     */ 
/*     */   public DateFormatManager(Locale locale)
/*     */   {
/*  73 */     this._locale = locale;
/*  74 */     configure();
/*     */   }
/*     */ 
/*     */   public DateFormatManager(String pattern)
/*     */   {
/*  80 */     this._pattern = pattern;
/*  81 */     configure();
/*     */   }
/*     */ 
/*     */   public DateFormatManager(TimeZone timeZone, Locale locale)
/*     */   {
/*  87 */     this._timeZone = timeZone;
/*  88 */     this._locale = locale;
/*  89 */     configure();
/*     */   }
/*     */ 
/*     */   public DateFormatManager(TimeZone timeZone, String pattern)
/*     */   {
/*  95 */     this._timeZone = timeZone;
/*  96 */     this._pattern = pattern;
/*  97 */     configure();
/*     */   }
/*     */ 
/*     */   public DateFormatManager(Locale locale, String pattern)
/*     */   {
/* 103 */     this._locale = locale;
/* 104 */     this._pattern = pattern;
/* 105 */     configure();
/*     */   }
/*     */ 
/*     */   public DateFormatManager(TimeZone timeZone, Locale locale, String pattern)
/*     */   {
/* 111 */     this._timeZone = timeZone;
/* 112 */     this._locale = locale;
/* 113 */     this._pattern = pattern;
/* 114 */     configure();
/*     */   }
/*     */ 
/*     */   public synchronized TimeZone getTimeZone()
/*     */   {
/* 122 */     if (this._timeZone == null) {
/* 123 */       return TimeZone.getDefault();
/*     */     }
/* 125 */     return this._timeZone;
/*     */   }
/*     */ 
/*     */   public synchronized void setTimeZone(TimeZone timeZone)
/*     */   {
/* 130 */     this._timeZone = timeZone;
/* 131 */     configure();
/*     */   }
/*     */ 
/*     */   public synchronized Locale getLocale() {
/* 135 */     if (this._locale == null) {
/* 136 */       return Locale.getDefault();
/*     */     }
/* 138 */     return this._locale;
/*     */   }
/*     */ 
/*     */   public synchronized void setLocale(Locale locale)
/*     */   {
/* 143 */     this._locale = locale;
/* 144 */     configure();
/*     */   }
/*     */ 
/*     */   public synchronized String getPattern() {
/* 148 */     return this._pattern;
/*     */   }
/*     */ 
/*     */   public synchronized void setPattern(String pattern)
/*     */   {
/* 155 */     this._pattern = pattern;
/* 156 */     configure();
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public synchronized String getOutputFormat()
/*     */   {
/* 165 */     return this._pattern;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public synchronized void setOutputFormat(String pattern)
/*     */   {
/* 173 */     this._pattern = pattern;
/* 174 */     configure();
/*     */   }
/*     */ 
/*     */   public synchronized DateFormat getDateFormatInstance() {
/* 178 */     return this._dateFormat;
/*     */   }
/*     */ 
/*     */   public synchronized void setDateFormatInstance(DateFormat dateFormat) {
/* 182 */     this._dateFormat = dateFormat;
/*     */   }
/*     */ 
/*     */   public String format(Date date)
/*     */   {
/* 187 */     return getDateFormatInstance().format(date);
/*     */   }
/*     */ 
/*     */   public String format(Date date, String pattern) {
/* 191 */     DateFormat formatter = null;
/* 192 */     formatter = getDateFormatInstance();
/* 193 */     if ((formatter instanceof SimpleDateFormat)) {
/* 194 */       formatter = (SimpleDateFormat)formatter.clone();
/* 195 */       ((SimpleDateFormat)formatter).applyPattern(pattern);
/*     */     }
/* 197 */     return formatter.format(date);
/*     */   }
/*     */ 
/*     */   public Date parse(String date)
/*     */     throws ParseException
/*     */   {
/* 204 */     return getDateFormatInstance().parse(date);
/*     */   }
/*     */ 
/*     */   public Date parse(String date, String pattern)
/*     */     throws ParseException
/*     */   {
/* 211 */     DateFormat formatter = null;
/* 212 */     formatter = getDateFormatInstance();
/* 213 */     if ((formatter instanceof SimpleDateFormat)) {
/* 214 */       formatter = (SimpleDateFormat)formatter.clone();
/* 215 */       ((SimpleDateFormat)formatter).applyPattern(pattern);
/*     */     }
/* 217 */     return formatter.parse(date);
/*     */   }
/*     */ 
/*     */   private synchronized void configure()
/*     */   {
/* 228 */     this._dateFormat = DateFormat.getDateTimeInstance(0, 0, getLocale());
/*     */ 
/* 231 */     this._dateFormat.setTimeZone(getTimeZone());
/*     */ 
/* 233 */     if (this._pattern != null)
/* 234 */       ((SimpleDateFormat)this._dateFormat).applyPattern(this._pattern);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.util.DateFormatManager
 * JD-Core Version:    0.6.0
 */
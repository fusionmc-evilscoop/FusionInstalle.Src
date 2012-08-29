/*     */ package org.apache.log4j.helpers;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.text.FieldPosition;
/*     */ import java.text.ParsePosition;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ public class AbsoluteTimeDateFormat extends DateFormat
/*     */ {
/*     */   public static final String ABS_TIME_DATE_FORMAT = "ABSOLUTE";
/*     */   public static final String DATE_AND_TIME_DATE_FORMAT = "DATE";
/*     */   public static final String ISO8601_DATE_FORMAT = "ISO8601";
/*     */   private static long previousTime;
/*  69 */   private static char[] previousTimeWithoutMillis = new char[9];
/*     */ 
/*     */   public AbsoluteTimeDateFormat()
/*     */   {
/*  60 */     setCalendar(Calendar.getInstance());
/*     */   }
/*     */ 
/*     */   public AbsoluteTimeDateFormat(TimeZone timeZone)
/*     */   {
/*  65 */     setCalendar(Calendar.getInstance(timeZone));
/*     */   }
/*     */ 
/*     */   public StringBuffer format(Date date, StringBuffer sbuf, FieldPosition fieldPosition)
/*     */   {
/*  83 */     long now = date.getTime();
/*  84 */     int millis = (int)(now % 1000L);
/*     */ 
/*  86 */     if (now - millis != previousTime)
/*     */     {
/*  91 */       this.calendar.setTime(date);
/*     */ 
/*  93 */       int start = sbuf.length();
/*     */ 
/*  95 */       int hour = this.calendar.get(11);
/*  96 */       if (hour < 10) {
/*  97 */         sbuf.append('0');
/*     */       }
/*  99 */       sbuf.append(hour);
/* 100 */       sbuf.append(':');
/*     */ 
/* 102 */       int mins = this.calendar.get(12);
/* 103 */       if (mins < 10) {
/* 104 */         sbuf.append('0');
/*     */       }
/* 106 */       sbuf.append(mins);
/* 107 */       sbuf.append(':');
/*     */ 
/* 109 */       int secs = this.calendar.get(13);
/* 110 */       if (secs < 10) {
/* 111 */         sbuf.append('0');
/*     */       }
/* 113 */       sbuf.append(secs);
/* 114 */       sbuf.append(',');
/*     */ 
/* 117 */       sbuf.getChars(start, sbuf.length(), previousTimeWithoutMillis, 0);
/*     */ 
/* 119 */       previousTime = now - millis;
/*     */     }
/*     */     else {
/* 122 */       sbuf.append(previousTimeWithoutMillis);
/*     */     }
/*     */ 
/* 127 */     if (millis < 100)
/* 128 */       sbuf.append('0');
/* 129 */     if (millis < 10) {
/* 130 */       sbuf.append('0');
/*     */     }
/* 132 */     sbuf.append(millis);
/* 133 */     return sbuf;
/*     */   }
/*     */ 
/*     */   public Date parse(String s, ParsePosition pos)
/*     */   {
/* 141 */     return null;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.helpers.AbsoluteTimeDateFormat
 * JD-Core Version:    0.6.0
 */
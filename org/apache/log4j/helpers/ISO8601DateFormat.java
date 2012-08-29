/*     */ package org.apache.log4j.helpers;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.text.FieldPosition;
/*     */ import java.text.ParsePosition;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ public class ISO8601DateFormat extends AbsoluteTimeDateFormat
/*     */ {
/*     */   private static long lastTime;
/*  53 */   private static char[] lastTimeString = new char[20];
/*     */ 
/*     */   public ISO8601DateFormat()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ISO8601DateFormat(TimeZone timeZone)
/*     */   {
/*  49 */     super(timeZone);
/*     */   }
/*     */ 
/*     */   public StringBuffer format(Date date, StringBuffer sbuf, FieldPosition fieldPosition)
/*     */   {
/*  65 */     long now = date.getTime();
/*  66 */     int millis = (int)(now % 1000L);
/*     */ 
/*  68 */     if (now - millis != lastTime)
/*     */     {
/*  73 */       this.calendar.setTime(date);
/*     */ 
/*  75 */       int start = sbuf.length();
/*     */ 
/*  77 */       int year = this.calendar.get(1);
/*  78 */       sbuf.append(year);
/*     */       String month;
/*  81 */       switch (this.calendar.get(2)) { case 0:
/*  82 */         month = "-01-"; break;
/*     */       case 1:
/*  83 */         month = "-02-"; break;
/*     */       case 2:
/*  84 */         month = "-03-"; break;
/*     */       case 3:
/*  85 */         month = "-04-"; break;
/*     */       case 4:
/*  86 */         month = "-05-"; break;
/*     */       case 5:
/*  87 */         month = "-06-"; break;
/*     */       case 6:
/*  88 */         month = "-07-"; break;
/*     */       case 7:
/*  89 */         month = "-08-"; break;
/*     */       case 8:
/*  90 */         month = "-09-"; break;
/*     */       case 9:
/*  91 */         month = "-10-"; break;
/*     */       case 10:
/*  92 */         month = "-11-"; break;
/*     */       case 11:
/*  93 */         month = "-12-"; break;
/*     */       default:
/*  94 */         month = "-NA-";
/*     */       }
/*  96 */       sbuf.append(month);
/*     */ 
/*  98 */       int day = this.calendar.get(5);
/*  99 */       if (day < 10)
/* 100 */         sbuf.append('0');
/* 101 */       sbuf.append(day);
/*     */ 
/* 103 */       sbuf.append(' ');
/*     */ 
/* 105 */       int hour = this.calendar.get(11);
/* 106 */       if (hour < 10) {
/* 107 */         sbuf.append('0');
/*     */       }
/* 109 */       sbuf.append(hour);
/* 110 */       sbuf.append(':');
/*     */ 
/* 112 */       int mins = this.calendar.get(12);
/* 113 */       if (mins < 10) {
/* 114 */         sbuf.append('0');
/*     */       }
/* 116 */       sbuf.append(mins);
/* 117 */       sbuf.append(':');
/*     */ 
/* 119 */       int secs = this.calendar.get(13);
/* 120 */       if (secs < 10) {
/* 121 */         sbuf.append('0');
/*     */       }
/* 123 */       sbuf.append(secs);
/*     */ 
/* 125 */       sbuf.append(',');
/*     */ 
/* 128 */       sbuf.getChars(start, sbuf.length(), lastTimeString, 0);
/* 129 */       lastTime = now - millis;
/*     */     }
/*     */     else {
/* 132 */       sbuf.append(lastTimeString);
/*     */     }
/*     */ 
/* 136 */     if (millis < 100)
/* 137 */       sbuf.append('0');
/* 138 */     if (millis < 10) {
/* 139 */       sbuf.append('0');
/*     */     }
/* 141 */     sbuf.append(millis);
/* 142 */     return sbuf;
/*     */   }
/*     */ 
/*     */   public Date parse(String s, ParsePosition pos)
/*     */   {
/* 150 */     return null;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.helpers.ISO8601DateFormat
 * JD-Core Version:    0.6.0
 */
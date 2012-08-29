/*     */ package com.jidesoft.utils;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ 
/*     */ public class DateUtils
/*     */ {
/*     */   private static final long DAY_IN_MS = 86400000L;
/*     */ 
/*     */   public static boolean isToday(Calendar cal)
/*     */   {
/*  27 */     Calendar today = Calendar.getInstance();
/*  28 */     return (today.get(1) == cal.get(1)) && (today.get(6) == cal.get(6));
/*     */   }
/*     */ 
/*     */   public static boolean isThisWeek(Calendar cal)
/*     */   {
/*  38 */     Calendar today = Calendar.getInstance();
/*  39 */     return (today.get(1) == cal.get(1)) && (today.get(3) == cal.get(3));
/*     */   }
/*     */ 
/*     */   public static boolean isThisMonth(Calendar cal)
/*     */   {
/*  49 */     Calendar today = Calendar.getInstance();
/*  50 */     return (today.get(1) == cal.get(1)) && (today.get(2) == cal.get(2));
/*     */   }
/*     */ 
/*     */   public static boolean isThisQuarter(Calendar cal)
/*     */   {
/*  60 */     Calendar today = Calendar.getInstance();
/*  61 */     return (today.get(1) == cal.get(1)) && (today.get(2) / 3 == cal.get(2) / 3);
/*     */   }
/*     */ 
/*     */   public static boolean isThisYear(Calendar cal)
/*     */   {
/*  71 */     Calendar today = Calendar.getInstance();
/*  72 */     return today.get(1) == cal.get(1);
/*     */   }
/*     */ 
/*     */   public static boolean isYesterday(Calendar cal)
/*     */   {
/*  83 */     Calendar yesterday = adjustDate(Calendar.getInstance(), -1);
/*  84 */     return (yesterday.get(1) == cal.get(1)) && (yesterday.get(6) == cal.get(6));
/*     */   }
/*     */ 
/*     */   public static boolean isLastWeek(Calendar cal)
/*     */   {
/*  94 */     Calendar lastWeek = adjustDate(Calendar.getInstance(), -7);
/*  95 */     return (lastWeek.get(1) == cal.get(1)) && (lastWeek.get(3) == cal.get(3));
/*     */   }
/*     */ 
/*     */   public static boolean isLastMonth(Calendar cal)
/*     */   {
/* 105 */     Calendar today = Calendar.getInstance();
/* 106 */     int thisMonth = today.get(2);
/* 107 */     if (thisMonth > 1) {
/* 108 */       return (today.get(1) == cal.get(1)) && (thisMonth - 1 == cal.get(2));
/*     */     }
/*     */ 
/* 111 */     return (today.get(1) - 1 == cal.get(1)) && (today.getActualMaximum(2) == cal.get(2));
/*     */   }
/*     */ 
/*     */   public static boolean isLastQuarter(Calendar cal)
/*     */   {
/* 122 */     Calendar today = Calendar.getInstance();
/* 123 */     int thisQuarter = today.get(2) / 3;
/* 124 */     if (thisQuarter > 1) {
/* 125 */       return (today.get(1) == cal.get(1)) && (thisQuarter - 1 == cal.get(2) / 3);
/*     */     }
/*     */ 
/* 128 */     return (today.get(1) - 1 == cal.get(1)) && (today.getActualMaximum(2) / 3 == cal.get(2) / 3);
/*     */   }
/*     */ 
/*     */   public static boolean isLastYear(Calendar cal)
/*     */   {
/* 139 */     Calendar today = Calendar.getInstance();
/* 140 */     return today.get(1) - 1 == cal.get(1);
/*     */   }
/*     */ 
/*     */   public static boolean isTomorrow(Calendar cal)
/*     */   {
/* 151 */     Calendar tomorrow = adjustDate(Calendar.getInstance(), 1);
/* 152 */     return (tomorrow.get(1) == cal.get(1)) && (tomorrow.get(6) == cal.get(6));
/*     */   }
/*     */ 
/*     */   public static boolean isNextWeek(Calendar cal)
/*     */   {
/* 162 */     Calendar nextWeek = adjustDate(Calendar.getInstance(), 7);
/* 163 */     return (nextWeek.get(1) == cal.get(1)) && (nextWeek.get(3) == cal.get(3));
/*     */   }
/*     */ 
/*     */   public static boolean isNextMonth(Calendar cal)
/*     */   {
/* 173 */     Calendar today = Calendar.getInstance();
/* 174 */     int thisMonth = today.get(2);
/* 175 */     if (thisMonth < today.getActualMaximum(2)) {
/* 176 */       return (today.get(1) == cal.get(1)) && (thisMonth + 1 == cal.get(2));
/*     */     }
/*     */ 
/* 179 */     return (today.get(1) + 1 == cal.get(1)) && (today.getMinimum(2) == cal.get(2));
/*     */   }
/*     */ 
/*     */   public static boolean isNextQuarter(Calendar cal)
/*     */   {
/* 190 */     Calendar today = Calendar.getInstance();
/* 191 */     int thisQuarter = today.get(2) / 3;
/* 192 */     if (thisQuarter < today.getActualMaximum(2) / 3) {
/* 193 */       return (today.get(1) == cal.get(1)) && (thisQuarter + 1 == cal.get(2) / 3);
/*     */     }
/*     */ 
/* 196 */     return (today.get(1) + 1 == cal.get(1)) && (today.getActualMinimum(2) / 3 == cal.get(2) / 3);
/*     */   }
/*     */ 
/*     */   public static boolean isNextYear(Calendar cal)
/*     */   {
/* 207 */     Calendar today = Calendar.getInstance();
/* 208 */     return today.get(1) + 1 == cal.get(1);
/*     */   }
/*     */ 
/*     */   public static boolean isAtMonth(Calendar cal, int month)
/*     */   {
/* 221 */     return cal.get(2) == month;
/*     */   }
/*     */ 
/*     */   public static boolean isAtQuarter(Calendar cal, int quarter)
/*     */   {
/* 232 */     return cal.get(2) / 3 + 1 == quarter;
/*     */   }
/*     */ 
/*     */   public static Calendar adjustDate(Calendar calendar, int differenceInDay)
/*     */   {
/* 243 */     calendar.setTimeInMillis(calendar.getTimeInMillis() + 86400000L * differenceInDay);
/* 244 */     return calendar;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static Date min(List<Date> dates)
/*     */   {
/* 257 */     return minDates(dates);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static Date max(List<Date> dates)
/*     */   {
/* 270 */     return maxDates(dates);
/*     */   }
/*     */ 
/*     */   public static Date minDates(List<Date> dates)
/*     */   {
/* 279 */     long min = 9223372036854775807L;
/* 280 */     Date minDate = null;
/* 281 */     for (Date value : dates) {
/* 282 */       long v = value.getTime();
/* 283 */       if (v < min) {
/* 284 */         min = v;
/* 285 */         minDate = value;
/*     */       }
/*     */     }
/* 288 */     return minDate;
/*     */   }
/*     */ 
/*     */   public static Date maxDates(List<Date> dates)
/*     */   {
/* 298 */     long max = -9223372036854775808L;
/* 299 */     Date maxDate = null;
/* 300 */     for (Date value : dates) {
/* 301 */       long v = value.getTime();
/* 302 */       if (v > max) {
/* 303 */         max = v;
/* 304 */         maxDate = value;
/*     */       }
/*     */     }
/* 307 */     return maxDate;
/*     */   }
/*     */ 
/*     */   public static Calendar minCalendars(List<Calendar> calendars)
/*     */   {
/* 317 */     long min = 9223372036854775807L;
/* 318 */     Calendar minCalendar = null;
/* 319 */     for (Calendar value : calendars) {
/* 320 */       long v = value.getTimeInMillis();
/* 321 */       if (v < min) {
/* 322 */         min = v;
/* 323 */         minCalendar = value;
/*     */       }
/*     */     }
/* 326 */     return minCalendar;
/*     */   }
/*     */ 
/*     */   public static Calendar maxCalendars(List<Calendar> calendars)
/*     */   {
/* 336 */     long max = -9223372036854775808L;
/* 337 */     Calendar maxCalendar = null;
/* 338 */     for (Calendar value : calendars) {
/* 339 */       long v = value.getTimeInMillis();
/* 340 */       if (v > max) {
/* 341 */         max = v;
/* 342 */         maxCalendar = value;
/*     */       }
/*     */     }
/* 345 */     return maxCalendar;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.DateUtils
 * JD-Core Version:    0.6.0
 */
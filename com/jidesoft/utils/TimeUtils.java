/*     */ package com.jidesoft.utils;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public class TimeUtils
/*     */ {
/*  17 */   private static final Logger logger = Logger.getLogger(TimeUtils.class.getName());
/*     */ 
/*     */   public static Date createTime(String timeString)
/*     */     throws ParseException
/*     */   {
/*  30 */     DateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
/*  31 */     return format.parse(timeString);
/*     */   }
/*     */ 
/*     */   public static Integer mostSignificantDifference(Calendar c1, Calendar c2)
/*     */   {
/*  41 */     if (!isSameYear(c1, c2)) {
/*  42 */       logger.info("YEAR");
/*  43 */       return Integer.valueOf(1);
/*     */     }
/*  45 */     if (!isSameMonth(c1, c2)) {
/*  46 */       logger.info("Month");
/*  47 */       return Integer.valueOf(2);
/*     */     }
/*  49 */     if (!isSameWeek(c1, c2)) {
/*  50 */       logger.info("WEEK_OF_YEAR");
/*  51 */       return Integer.valueOf(3);
/*     */     }
/*  53 */     if (!isSameDay(c1, c2)) {
/*  54 */       logger.info("DAY_OF_MONTH");
/*  55 */       return Integer.valueOf(5);
/*     */     }
/*  57 */     if (!isSameHour(c1, c2)) {
/*  58 */       logger.info("HOUR_OF_DAY");
/*  59 */       return Integer.valueOf(11);
/*     */     }
/*  61 */     if (!isSameMinute(c1, c2)) {
/*  62 */       logger.info("MINUTE");
/*  63 */       return Integer.valueOf(12);
/*     */     }
/*  65 */     if (!isSameSecond(c1, c2)) {
/*  66 */       logger.info("SECOND");
/*  67 */       return Integer.valueOf(13);
/*     */     }
/*  69 */     if (!isSameMillis(c1, c2)) {
/*  70 */       logger.info("MILLISECOND");
/*  71 */       return Integer.valueOf(14);
/*     */     }
/*     */ 
/*  74 */     return null;
/*     */   }
/*     */ 
/*     */   public static boolean isSameYear(Calendar c1, Calendar c2)
/*     */   {
/*  79 */     return isSameInField(1, c1, c2);
/*     */   }
/*     */ 
/*     */   public static boolean isSameMonth(Calendar c1, Calendar c2) {
/*  83 */     return isSameInField(2, c1, c2);
/*     */   }
/*     */ 
/*     */   public static boolean isSameWeek(Calendar c1, Calendar c2) {
/*  87 */     return isSameInField(3, c1, c2);
/*     */   }
/*     */ 
/*     */   public static boolean isSameDay(Calendar c1, Calendar c2) {
/*  91 */     return isSameInField(5, c1, c2);
/*     */   }
/*     */ 
/*     */   public static boolean isSameHour(Calendar c1, Calendar c2) {
/*  95 */     return isSameInField(11, c1, c2);
/*     */   }
/*     */ 
/*     */   public static boolean isSameMinute(Calendar c1, Calendar c2) {
/*  99 */     return isSameInField(12, c1, c2);
/*     */   }
/*     */ 
/*     */   public static boolean isSameSecond(Calendar c1, Calendar c2) {
/* 103 */     return isSameInField(13, c1, c2);
/*     */   }
/*     */ 
/*     */   public static boolean isSameMillis(Calendar c1, Calendar c2) {
/* 107 */     return isSameInField(14, c1, c2);
/*     */   }
/*     */ 
/*     */   public static boolean isSameInField(int field, Calendar c1, Calendar c2) {
/* 111 */     int field1 = c1.get(field);
/* 112 */     int field2 = c2.get(field);
/* 113 */     return field1 == field2;
/*     */   }
/*     */ 
/*     */   public static double yearsDiff(Calendar c1, Calendar c2) {
/* 117 */     return weeksDiff(c1, c2) / 52.0D;
/*     */   }
/*     */ 
/*     */   public static double weeksDiff(Calendar c1, Calendar c2) {
/* 121 */     return daysDiff(c1, c2) / 7.0D;
/*     */   }
/*     */ 
/*     */   public static double daysDiff(Calendar c1, Calendar c2) {
/* 125 */     return hoursDiff(c1, c2) / 24.0D;
/*     */   }
/*     */ 
/*     */   public static double hoursDiff(Calendar c1, Calendar c2) {
/* 129 */     return minutesDiff(c1, c2) / 60.0D;
/*     */   }
/*     */ 
/*     */   public static double minutesDiff(Calendar c1, Calendar c2) {
/* 133 */     return secondsDiff(c1, c2) / 60.0D;
/*     */   }
/*     */ 
/*     */   public static double secondsDiff(Calendar c1, Calendar c2) {
/* 137 */     return millisDiff(c1, c2) / 1000.0D;
/*     */   }
/*     */ 
/*     */   public static long millisDiff(Calendar c1, Calendar c2) {
/* 141 */     long time1 = c1.getTimeInMillis();
/* 142 */     long time2 = c2.getTimeInMillis();
/* 143 */     return Math.abs(time1 - time2);
/*     */   }
/*     */ 
/*     */   public static Calendar min(Calendar c1, Calendar c2)
/*     */   {
/* 148 */     Calendar result = Calendar.getInstance();
/* 149 */     result.setTimeInMillis(c2.getTimeInMillis());
/* 150 */     if (yearsDiff(c1, c2) > 1.0D) {
/* 151 */       result.set(3, 0);
/* 152 */       result.set(2, 0);
/* 153 */       result.set(5, 0);
/* 154 */       result.set(11, 0);
/* 155 */       result.set(12, 0);
/* 156 */       result.set(13, 0);
/* 157 */       result.set(14, 0);
/*     */     }
/* 159 */     else if (weeksDiff(c1, c2) > 1.0D) {
/* 160 */       result.set(11, 0);
/* 161 */       result.set(12, 0);
/* 162 */       result.set(13, 0);
/* 163 */       result.set(14, 0);
/*     */     } else {
/* 165 */       if (daysDiff(c1, c2) > 1.0D) {
/* 166 */         result.set(11, 0);
/* 167 */         result.set(12, 0);
/* 168 */         result.set(13, 0);
/* 169 */         result.set(14, 0);
/* 170 */         return result;
/*     */       }
/* 172 */       if (hoursDiff(c1, c2) > 1.0D) {
/* 173 */         result.set(12, 0);
/* 174 */         result.set(13, 0);
/* 175 */         result.set(14, 0);
/*     */       }
/* 177 */       else if (minutesDiff(c1, c2) > 1.0D) {
/* 178 */         result.set(13, 0);
/* 179 */         result.set(14, 0);
/*     */       }
/* 181 */       else if (secondsDiff(c1, c2) > 1.0D) {
/* 182 */         result.set(14, 0);
/* 183 */         result.add(13, 1);
/*     */       }
/*     */     }
/* 185 */     return result;
/*     */   }
/*     */ 
/*     */   public static Calendar max(Calendar c1, Calendar c2)
/*     */   {
/* 190 */     Calendar result = Calendar.getInstance();
/* 191 */     result.setTimeInMillis(c2.getTimeInMillis());
/* 192 */     if (yearsDiff(c1, c2) > 1.0D) {
/* 193 */       result.set(3, 0);
/* 194 */       result.set(2, 0);
/* 195 */       result.set(5, 0);
/* 196 */       result.set(11, 0);
/* 197 */       result.set(12, 0);
/* 198 */       result.set(13, 0);
/* 199 */       result.set(14, 0);
/* 200 */       result.add(1, 1);
/*     */     }
/* 202 */     else if (weeksDiff(c1, c2) > 1.0D) {
/* 203 */       result.set(11, 0);
/* 204 */       result.set(12, 0);
/* 205 */       result.set(13, 0);
/* 206 */       result.set(14, 0);
/* 207 */       result.add(5, 1);
/*     */     }
/* 209 */     else if (daysDiff(c1, c2) > 1.0D) {
/* 210 */       result.set(11, 0);
/* 211 */       result.set(12, 0);
/* 212 */       result.set(13, 0);
/* 213 */       result.set(14, 0);
/* 214 */       result.add(5, 1);
/*     */     }
/* 216 */     else if (hoursDiff(c1, c2) > 1.0D) {
/* 217 */       result.set(12, 0);
/* 218 */       result.set(13, 0);
/* 219 */       result.set(14, 0);
/* 220 */       result.add(11, 1);
/*     */     }
/* 222 */     else if (minutesDiff(c1, c2) > 1.0D) {
/* 223 */       result.set(13, 0);
/* 224 */       result.set(14, 0);
/* 225 */       result.add(12, 1);
/*     */     }
/* 227 */     else if (secondsDiff(c1, c2) > 1.0D) {
/* 228 */       result.set(14, 0);
/* 229 */       result.add(13, 1);
/*     */     }
/* 231 */     return result;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.TimeUtils
 * JD-Core Version:    0.6.0
 */
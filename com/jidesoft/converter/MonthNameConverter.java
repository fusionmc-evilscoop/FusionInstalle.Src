/*     */ package com.jidesoft.converter;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class MonthNameConverter
/*     */   implements ObjectConverter
/*     */ {
/*  23 */   public static ConverterContext CONTEXT = new ConverterContext("MonthName");
/*     */ 
/*  28 */   public static final DateFormat CONCISE_FORMAT = new SimpleDateFormat("M");
/*     */ 
/*  33 */   public static final DateFormat SHORT_FORMAT = new SimpleDateFormat("MM");
/*     */ 
/*  38 */   public static final DateFormat MEDIUM_FORMAT = new SimpleDateFormat("MMM");
/*     */ 
/*  43 */   public static final DateFormat LONG_FORMAT = new SimpleDateFormat("MMMMM");
/*     */ 
/*  45 */   private DateFormat _defaultFormat = MEDIUM_FORMAT;
/*     */ 
/*  62 */   static final Calendar CAL = Calendar.getInstance();
/*     */ 
/*     */   public String toString(Object object, ConverterContext context)
/*     */   {
/*  54 */     if ((object == null) || (!(object instanceof Integer))) {
/*  55 */       return "";
/*     */     }
/*     */ 
/*  58 */     return this._defaultFormat.format(getCalendarByMonth(((Integer)object).intValue()).getTime());
/*     */   }
/*     */ 
/*     */   protected Calendar getCalendarByMonth(int month)
/*     */   {
/*  69 */     CAL.set(2, month);
/*  70 */     return CAL;
/*     */   }
/*     */ 
/*     */   public boolean supportToString(Object object, ConverterContext context) {
/*  74 */     return true;
/*     */   }
/*     */ 
/*     */   public Object fromString(String string, ConverterContext context) {
/*  78 */     Calendar calendar = Calendar.getInstance();
/*     */     try {
/*  80 */       Date time = this._defaultFormat.parse(string);
/*  81 */       calendar.setTime(time);
/*     */     }
/*     */     catch (ParseException e1) {
/*     */       try {
/*  85 */         Date time = SHORT_FORMAT.parse(string);
/*  86 */         calendar.setTime(time);
/*     */       }
/*     */       catch (ParseException e2) {
/*     */         try {
/*  90 */           Date time = MEDIUM_FORMAT.parse(string);
/*  91 */           calendar.setTime(time);
/*     */         }
/*     */         catch (ParseException e3) {
/*     */           try {
/*  95 */             Date time = LONG_FORMAT.parse(string);
/*  96 */             calendar.setTime(time);
/*     */           }
/*     */           catch (ParseException e4) {
/*     */             try {
/* 100 */               Date time = CONCISE_FORMAT.parse(string);
/* 101 */               calendar.setTime(time);
/*     */             }
/*     */             catch (ParseException e5) {
/* 104 */               return null;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 110 */     return Integer.valueOf(calendar.get(2));
/*     */   }
/*     */ 
/*     */   public boolean supportFromString(String string, ConverterContext context) {
/* 114 */     return true;
/*     */   }
/*     */ 
/*     */   public DateFormat getDefaultFormat()
/*     */   {
/* 123 */     return this._defaultFormat;
/*     */   }
/*     */ 
/*     */   public void setDefaultFormat(DateFormat defaultFormat)
/*     */   {
/* 132 */     this._defaultFormat = defaultFormat;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 136 */     MonthNameConverter converter = new MonthNameConverter();
/* 137 */     converter.setDefaultFormat(LONG_FORMAT);
/* 138 */     for (int i = 0; i < 12; i++) {
/* 139 */       String str = converter.toString(new Integer(i), null);
/* 140 */       System.out.println(str);
/* 141 */       System.out.println(converter.fromString(str, null));
/*     */     }
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  65 */     CAL.set(5, 1);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.MonthNameConverter
 * JD-Core Version:    0.6.0
 */
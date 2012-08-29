/*     */ package com.jidesoft.converter;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ public class DateConverter
/*     */   implements ObjectConverter
/*     */ {
/*  20 */   public static final ConverterContext DATETIME_CONTEXT = new ConverterContext("DateTime");
/*  21 */   public static final ConverterContext TIME_CONTEXT = new ConverterContext("Time");
/*  22 */   public static final ConverterContext DATE_CONTEXT = new ConverterContext("Date");
/*     */ 
/*  24 */   private DateFormat _shortFormat = SimpleDateFormat.getDateInstance(3);
/*  25 */   private DateFormat _mediumFormat = SimpleDateFormat.getDateInstance(2);
/*  26 */   private DateFormat _longFormat = SimpleDateFormat.getDateInstance(1);
/*     */ 
/*  28 */   private DateFormat _defaultFormat = SimpleDateFormat.getDateInstance(2);
/*     */ 
/*  30 */   private DateFormat _shortDatetimeFormat = SimpleDateFormat.getDateTimeInstance(3, 3);
/*  31 */   private DateFormat _mediumDatetimeFormat = SimpleDateFormat.getDateTimeInstance(2, 2);
/*  32 */   private DateFormat _longDatetimeFormat = SimpleDateFormat.getDateTimeInstance(1, 1);
/*     */ 
/*  34 */   private DateFormat _defaultDatetimeFormat = SimpleDateFormat.getDateTimeInstance(2, 2);
/*     */ 
/*  36 */   private DateFormat _shortTimeFormat = SimpleDateFormat.getTimeInstance(3);
/*  37 */   private DateFormat _mediumTimeFormat = SimpleDateFormat.getTimeInstance(2);
/*  38 */   private DateFormat _longTimeFormat = SimpleDateFormat.getTimeInstance(1);
/*     */ 
/*  40 */   private DateFormat _defaultTimeFormat = SimpleDateFormat.getTimeInstance(2);
/*     */ 
/*     */   public synchronized String toString(Object object, ConverterContext context)
/*     */   {
/*  59 */     if (object == null)
/*  60 */       return "";
/*     */     TimeZone timeZone;
/*  64 */     if ((object instanceof Calendar)) {
/*  65 */       TimeZone timeZone = ((Calendar)object).getTimeZone();
/*  66 */       object = ((Calendar)object).getTime();
/*     */     }
/*     */     else
/*     */     {
/*     */       TimeZone timeZone;
/*  68 */       if ((object instanceof Date)) {
/*  69 */         Calendar cal = Calendar.getInstance();
/*  70 */         cal.setTime((Date)object);
/*  71 */         timeZone = cal.getTimeZone();
/*     */       }
/*     */       else {
/*  74 */         timeZone = TimeZone.getDefault();
/*     */       }
/*     */     }
/*  77 */     if (((object instanceof Date)) || ((object instanceof Number))) {
/*  78 */       if ((context != null) && ((context.getUserObject() instanceof DateFormat))) {
/*  79 */         return ((DateFormat)context.getUserObject()).format(object);
/*     */       }
/*  81 */       if (DATETIME_CONTEXT.equals(context)) {
/*  82 */         this._defaultDatetimeFormat.setTimeZone(timeZone);
/*  83 */         return this._defaultDatetimeFormat.format(object);
/*     */       }
/*  85 */       if (TIME_CONTEXT.equals(context)) {
/*  86 */         this._defaultTimeFormat.setTimeZone(timeZone);
/*  87 */         return this._defaultTimeFormat.format(object);
/*     */       }
/*     */ 
/*  90 */       this._defaultFormat.setTimeZone(timeZone);
/*  91 */       return this._defaultFormat.format(object);
/*     */     }
/*     */ 
/*  94 */     if ((object instanceof String)) {
/*  95 */       return (String)object;
/*     */     }
/*     */ 
/*  98 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean supportToString(Object object, ConverterContext context)
/*     */   {
/* 104 */     return true;
/*     */   }
/*     */ 
/*     */   public synchronized Object fromString(String string, ConverterContext context)
/*     */   {
/* 116 */     if (string == null) {
/* 117 */       return null;
/*     */     }
/* 119 */     string = string.trim();
/* 120 */     if (string.length() == 0) {
/* 121 */       return null;
/*     */     }
/*     */     try
/*     */     {
/* 125 */       Object userObject = context != null ? context.getUserObject() : null;
/* 126 */       if ((userObject instanceof DateFormat)) {
/* 127 */         return ((DateFormat)userObject).parse(string);
/*     */       }
/* 129 */       if (DATETIME_CONTEXT.equals(context)) {
/* 130 */         return this._defaultDatetimeFormat.parse(string);
/*     */       }
/* 132 */       if (TIME_CONTEXT.equals(context)) {
/* 133 */         return this._defaultTimeFormat.parse(string);
/*     */       }
/*     */ 
/* 136 */       return this._defaultFormat.parse(string);
/*     */     }
/*     */     catch (ParseException e1)
/*     */     {
/* 140 */       if (DATETIME_CONTEXT.equals(context)) {
/*     */         try {
/* 142 */           return this._shortDatetimeFormat.parse(string);
/*     */         }
/*     */         catch (ParseException e2) {
/*     */           try {
/* 146 */             return this._mediumDatetimeFormat.parse(string);
/*     */           }
/*     */           catch (ParseException e3) {
/*     */             try {
/* 150 */               return this._longDatetimeFormat.parse(string);
/*     */             }
/*     */             catch (ParseException e4) {
/* 153 */               return string;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 158 */       if (TIME_CONTEXT.equals(context)) {
/*     */         try {
/* 160 */           return this._shortTimeFormat.parse(string);
/*     */         }
/*     */         catch (ParseException e2) {
/*     */           try {
/* 164 */             return this._mediumTimeFormat.parse(string);
/*     */           }
/*     */           catch (ParseException e3) {
/*     */             try {
/* 168 */               return this._longTimeFormat.parse(string);
/*     */             }
/*     */             catch (ParseException e4) {
/* 171 */               return string;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       try
/*     */       {
/* 178 */         return this._shortFormat.parse(string);
/*     */       }
/*     */       catch (ParseException e2) {
/*     */         try {
/* 182 */           return this._mediumFormat.parse(string);
/*     */         }
/*     */         catch (ParseException e3) {
/*     */           try {
/* 186 */             return this._longFormat.parse(string); } catch (ParseException e4) {
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 189 */     return string;
/*     */   }
/*     */ 
/*     */   public boolean supportFromString(String string, ConverterContext context)
/*     */   {
/* 198 */     return true;
/*     */   }
/*     */ 
/*     */   public DateFormat getDefaultFormat()
/*     */   {
/* 207 */     return this._defaultFormat;
/*     */   }
/*     */ 
/*     */   public void setDefaultFormat(DateFormat defaultFormat)
/*     */   {
/* 216 */     this._defaultFormat = defaultFormat;
/*     */   }
/*     */ 
/*     */   public DateFormat getDefaultTimeFormat()
/*     */   {
/* 225 */     return this._defaultTimeFormat;
/*     */   }
/*     */ 
/*     */   public void setDefaultTimeFormat(DateFormat defaultTimeFormat)
/*     */   {
/* 234 */     this._defaultTimeFormat = defaultTimeFormat;
/*     */   }
/*     */ 
/*     */   public DateFormat getDefaultDatetimeFormat()
/*     */   {
/* 243 */     return this._defaultDatetimeFormat;
/*     */   }
/*     */ 
/*     */   public void setDefaultDatetimeFormat(DateFormat defaultDatetimeFormat)
/*     */   {
/* 252 */     this._defaultDatetimeFormat = defaultDatetimeFormat;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.DateConverter
 * JD-Core Version:    0.6.0
 */
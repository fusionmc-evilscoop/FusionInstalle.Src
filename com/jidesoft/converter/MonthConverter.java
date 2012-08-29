/*     */ package com.jidesoft.converter;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class MonthConverter
/*     */   implements ObjectConverter
/*     */ {
/*  22 */   public static ConverterContext CONTEXT_MONTH = new ConverterContext("Calendar.Month");
/*     */ 
/*  24 */   private DateFormat _conciseFormat = new SimpleDateFormat("MMyy");
/*  25 */   private DateFormat _shortFormat = new SimpleDateFormat("MM/yy");
/*  26 */   private DateFormat _mediumFormat = new SimpleDateFormat("MM, yyyy");
/*  27 */   private DateFormat _longFormat = new SimpleDateFormat("MMMMM, yyyy");
/*     */ 
/*  29 */   private DateFormat _defaultFormat = this._shortFormat;
/*     */ 
/*     */   public String toString(Object object, ConverterContext context)
/*     */   {
/*  38 */     if ((object == null) || (!(object instanceof Calendar))) {
/*  39 */       return "";
/*     */     }
/*     */ 
/*  42 */     return this._defaultFormat.format(((Calendar)object).getTime());
/*     */   }
/*     */ 
/*     */   public boolean supportToString(Object object, ConverterContext context)
/*     */   {
/*  47 */     return true;
/*     */   }
/*     */ 
/*     */   public Object fromString(String string, ConverterContext context) {
/*  51 */     Calendar calendar = Calendar.getInstance();
/*     */     try {
/*  53 */       Date time = this._defaultFormat.parse(string);
/*  54 */       calendar.setTime(time);
/*     */     }
/*     */     catch (ParseException e1) {
/*     */       try {
/*  58 */         Date time = this._shortFormat.parse(string);
/*  59 */         calendar.setTime(time);
/*     */       }
/*     */       catch (ParseException e2) {
/*     */         try {
/*  63 */           Date time = this._mediumFormat.parse(string);
/*  64 */           calendar.setTime(time);
/*     */         }
/*     */         catch (ParseException e3) {
/*     */           try {
/*  68 */             Date time = this._longFormat.parse(string);
/*  69 */             calendar.setTime(time);
/*     */           }
/*     */           catch (ParseException e4) {
/*     */             try {
/*  73 */               Date time = this._conciseFormat.parse(string);
/*  74 */               calendar.setTime(time);
/*     */             }
/*     */             catch (ParseException e5) {
/*  77 */               return null;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*  83 */     return calendar;
/*     */   }
/*     */ 
/*     */   public boolean supportFromString(String string, ConverterContext context) {
/*  87 */     return true;
/*     */   }
/*     */ 
/*     */   public DateFormat getDefaultFormat()
/*     */   {
/*  96 */     return this._defaultFormat;
/*     */   }
/*     */ 
/*     */   public void setDefaultFormat(DateFormat defaultFormat)
/*     */   {
/* 105 */     this._defaultFormat = defaultFormat;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.MonthConverter
 * JD-Core Version:    0.6.0
 */
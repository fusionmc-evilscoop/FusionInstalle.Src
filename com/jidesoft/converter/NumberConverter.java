/*     */ package com.jidesoft.converter;
/*     */ 
/*     */ import com.jidesoft.utils.SystemInfo;
/*     */ import java.math.RoundingMode;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.Currency;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public abstract class NumberConverter
/*     */   implements ObjectConverter
/*     */ {
/*     */   private NumberFormat _numberFormat;
/*  24 */   public static final ConverterContext CONTEXT_FRACTION_NUMBER = new ConverterContext("Fraction Number");
/*     */ 
/* 117 */   private static boolean _groupingUsed = true;
/*     */ 
/*     */   public NumberConverter()
/*     */   {
/*     */   }
/*     */ 
/*     */   public NumberConverter(NumberFormat format)
/*     */   {
/*  38 */     this._numberFormat = format;
/*     */   }
/*     */ 
/*     */   public String toString(Object object, ConverterContext context)
/*     */   {
/*     */     try {
/*  44 */       if ((context == null) || (context.getUserObject() == null) || (!(context.getUserObject() instanceof NumberFormat))) {
/*  45 */         if (((object instanceof Number)) && (((Number)object).doubleValue() == (0.0D / 0.0D))) {
/*  46 */           return "";
/*     */         }
/*     */ 
/*  49 */         return getNumberFormat().format(object);
/*     */       }
/*     */ 
/*  53 */       NumberFormat format = (NumberFormat)context.getUserObject();
/*  54 */       return format.format(object);
/*     */     }
/*     */     catch (IllegalArgumentException e) {
/*     */     }
/*  58 */     return "";
/*     */   }
/*     */ 
/*     */   public boolean supportToString(Object object, ConverterContext context)
/*     */   {
/*  63 */     return true;
/*     */   }
/*     */ 
/*     */   public void setNumberFormat(NumberFormat numberFormat) {
/*  67 */     this._numberFormat = numberFormat;
/*     */   }
/*     */ 
/*     */   protected NumberFormat getNumberFormat()
/*     */   {
/*  76 */     if (this._numberFormat == null) {
/*  77 */       this._numberFormat = DecimalFormat.getInstance();
/*  78 */       this._numberFormat.setGroupingUsed(isGroupingUsed());
/*     */     }
/*  80 */     return this._numberFormat;
/*     */   }
/*     */ 
/*     */   protected NumberFormat getDefaultNumberFormat()
/*     */   {
/*  89 */     NumberFormat format = DecimalFormat.getInstance(Locale.US);
/*  90 */     format.setGroupingUsed(isGroupingUsed());
/*  91 */     return format;
/*     */   }
/*     */ 
/*     */   protected Number parseNumber(String string)
/*     */   {
/*     */     Number number;
/*     */     try
/*     */     {
/* 104 */       number = getNumberFormat().parse(string);
/*     */     }
/*     */     catch (ParseException e) {
/*     */       try {
/* 108 */         number = getDefaultNumberFormat().parse(string);
/*     */       }
/*     */       catch (ParseException e1) {
/* 111 */         number = null;
/*     */       }
/*     */     }
/* 114 */     return number;
/*     */   }
/*     */ 
/*     */   public static boolean isGroupingUsed()
/*     */   {
/* 125 */     return _groupingUsed;
/*     */   }
/*     */ 
/*     */   public static void setGroupingUsed(boolean groupingUsed)
/*     */   {
/* 136 */     _groupingUsed = groupingUsed;
/*     */   }
/*     */ 
/*     */   public void setFractionDigits(int minDigits, int maxDigits)
/*     */   {
/* 146 */     NumberFormat numberFormat = getNumberFormat();
/* 147 */     numberFormat.setMinimumFractionDigits(minDigits);
/* 148 */     numberFormat.setMaximumFractionDigits(maxDigits);
/*     */   }
/*     */ 
/*     */   public void setCurrency(Currency currency)
/*     */   {
/* 157 */     NumberFormat numberFormat = getNumberFormat();
/* 158 */     numberFormat.setCurrency(currency);
/*     */   }
/*     */ 
/*     */   public void setIntegerDigits(int minDigits, int maxDigits)
/*     */   {
/* 168 */     NumberFormat numberFormat = getNumberFormat();
/* 169 */     numberFormat.setMinimumIntegerDigits(minDigits);
/* 170 */     numberFormat.setMaximumIntegerDigits(maxDigits);
/*     */   }
/*     */ 
/*     */   public void setRoundingMode(RoundingMode mode)
/*     */   {
/* 179 */     if (SystemInfo.isJdk6Above()) {
/* 180 */       NumberFormat numberFormat = getNumberFormat();
/* 181 */       numberFormat.setRoundingMode(mode);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.NumberConverter
 * JD-Core Version:    0.6.0
 */
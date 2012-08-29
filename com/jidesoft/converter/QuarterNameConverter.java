/*     */ package com.jidesoft.converter;
/*     */ 
/*     */ import java.text.MessageFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
/*     */ 
/*     */ public class QuarterNameConverter
/*     */   implements ObjectConverter
/*     */ {
/*  22 */   public static ConverterContext CONTEXT = new ConverterContext("QuarterName");
/*     */   private static String _quarterNamePattern;
/*     */ 
/*     */   public String toString(Object object, ConverterContext context)
/*     */   {
/*  32 */     if ((object == null) || (!(object instanceof Number))) {
/*  33 */       return "";
/*     */     }
/*     */ 
/*  36 */     int qty = ((Number)object).intValue();
/*  37 */     if ((qty >= 0) && (qty < 4)) {
/*  38 */       return MessageFormat.format(getQuarterNamePattern(), new Object[] { Integer.valueOf(qty + 1) });
/*     */     }
/*     */ 
/*  41 */     return "";
/*     */   }
/*     */ 
/*     */   public boolean supportToString(Object object, ConverterContext context)
/*     */   {
/*  47 */     return true;
/*     */   }
/*     */ 
/*     */   public Object fromString(String string, ConverterContext context) {
/*  51 */     String quarterNamePattern = getQuarterNamePattern();
/*     */     try {
/*  53 */       Object[] values = new MessageFormat(quarterNamePattern).parse(string);
/*  54 */       if (values.length > 0) {
/*  55 */         return Integer.valueOf(Integer.parseInt("" + values[0]) - 1);
/*     */       }
/*     */     }
/*     */     catch (ParseException e)
/*     */     {
/*     */     }
/*  61 */     return Integer.valueOf(0);
/*     */   }
/*     */ 
/*     */   public boolean supportFromString(String string, ConverterContext context) {
/*  65 */     return true;
/*     */   }
/*     */ 
/*     */   public String getQuarterNamePattern()
/*     */   {
/*  84 */     if (_quarterNamePattern == null) {
/*  85 */       return getResourceString("Quarter.quarter");
/*     */     }
/*  87 */     return _quarterNamePattern;
/*     */   }
/*     */ 
/*     */   public void setQuarterNamePattern(String quarterName)
/*     */   {
/*  97 */     _quarterNamePattern = quarterName;
/*     */   }
/*     */ 
/*     */   protected String getResourceString(String key) {
/* 101 */     ResourceBundle resourceBundle = Resource.getResourceBundle(Locale.getDefault());
/* 102 */     return resourceBundle.getString(key);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.QuarterNameConverter
 * JD-Core Version:    0.6.0
 */
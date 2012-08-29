/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ import java.math.BigInteger;
/*    */ import java.text.DecimalFormat;
/*    */ 
/*    */ public class BigDecimalConverter extends NumberFormatConverter
/*    */ {
/*    */   public BigDecimalConverter()
/*    */   {
/* 13 */     super(new DecimalFormat("#,##0.00"));
/*    */   }
/*    */ 
/*    */   public Object fromString(String string, ConverterContext context)
/*    */   {
/* 18 */     Object value = super.fromString(string, context);
/* 19 */     if ((value instanceof Double)) {
/* 20 */       return new BigDecimal(((Double)value).doubleValue());
/*    */     }
/* 22 */     if ((value instanceof Long)) {
/* 23 */       return new BigDecimal(((Long)value).longValue());
/*    */     }
/* 25 */     if ((value instanceof Integer)) {
/* 26 */       return new BigDecimal(((Integer)value).intValue());
/*    */     }
/* 28 */     if ((value instanceof BigInteger)) {
/* 29 */       return new BigDecimal((BigInteger)value);
/*    */     }
/* 31 */     return value;
/*    */   }
/*    */ 
/*    */   public String toString(Object obj, ConverterContext convertercontext)
/*    */   {
/* 36 */     if ((obj instanceof BigDecimal)) {
/* 37 */       BigDecimal decimal = (BigDecimal)obj;
/* 38 */       if (decimal.doubleValue() == (0.0D / 0.0D))
/* 39 */         return "";
/* 40 */       return super.toString(decimal, convertercontext);
/*    */     }
/* 42 */     return "";
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.BigDecimalConverter
 * JD-Core Version:    0.6.0
 */
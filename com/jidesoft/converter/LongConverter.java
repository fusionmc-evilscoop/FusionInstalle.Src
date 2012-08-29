/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ import java.text.DecimalFormat;
/*    */ import java.text.NumberFormat;
/*    */ 
/*    */ public class LongConverter extends NumberConverter
/*    */ {
/*    */   public LongConverter()
/*    */   {
/* 17 */     this(DecimalFormat.getIntegerInstance());
/*    */   }
/*    */ 
/*    */   public LongConverter(NumberFormat format) {
/* 21 */     super(format);
/*    */   }
/*    */ 
/*    */   public Object fromString(String string, ConverterContext context) {
/* 25 */     Number number = parseNumber(string);
/* 26 */     return number != null ? Long.valueOf(number.longValue()) : null;
/*    */   }
/*    */ 
/*    */   public boolean supportFromString(String string, ConverterContext context) {
/* 30 */     return true;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.LongConverter
 * JD-Core Version:    0.6.0
 */
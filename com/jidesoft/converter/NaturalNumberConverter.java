/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ import java.text.DecimalFormat;
/*    */ import java.text.NumberFormat;
/*    */ 
/*    */ public class NaturalNumberConverter extends NumberConverter
/*    */ {
/* 19 */   public static ConverterContext CONTEXT = new ConverterContext("Natural Nunber");
/*    */ 
/*    */   public NaturalNumberConverter() {
/* 22 */     this(DecimalFormat.getIntegerInstance());
/*    */   }
/*    */ 
/*    */   public NaturalNumberConverter(NumberFormat format) {
/* 26 */     super(format);
/*    */   }
/*    */ 
/*    */   public Object fromString(String string, ConverterContext context) {
/* 30 */     Number number = parseNumber(string);
/* 31 */     return number != null ? Integer.valueOf(number.intValue() < 0 ? 0 : number.intValue()) : null;
/*    */   }
/*    */ 
/*    */   public boolean supportFromString(String string, ConverterContext context) {
/* 35 */     return true;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.NaturalNumberConverter
 * JD-Core Version:    0.6.0
 */
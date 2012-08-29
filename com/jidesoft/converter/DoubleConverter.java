/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ import java.text.NumberFormat;
/*    */ 
/*    */ public class DoubleConverter extends NumberConverter
/*    */ {
/*    */   public DoubleConverter()
/*    */   {
/*    */   }
/*    */ 
/*    */   public DoubleConverter(NumberFormat format)
/*    */   {
/* 19 */     super(format);
/*    */   }
/*    */ 
/*    */   public Object fromString(String string, ConverterContext context) {
/* 23 */     Number number = parseNumber(string);
/* 24 */     return number != null ? Double.valueOf(number.doubleValue()) : null;
/*    */   }
/*    */ 
/*    */   public boolean supportFromString(String string, ConverterContext context) {
/* 28 */     return true;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.DoubleConverter
 * JD-Core Version:    0.6.0
 */
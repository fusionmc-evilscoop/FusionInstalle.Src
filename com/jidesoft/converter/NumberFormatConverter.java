/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ import java.text.NumberFormat;
/*    */ 
/*    */ public abstract class NumberFormatConverter extends NumberConverter
/*    */ {
/*    */   public NumberFormatConverter(NumberFormat format)
/*    */   {
/* 16 */     super(format);
/*    */   }
/*    */ 
/*    */   public Object fromString(String string, ConverterContext context) {
/* 20 */     return parseNumber(string);
/*    */   }
/*    */ 
/*    */   public boolean supportFromString(String string, ConverterContext context) {
/* 24 */     return true;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.NumberFormatConverter
 * JD-Core Version:    0.6.0
 */
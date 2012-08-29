/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ import java.text.NumberFormat;
/*    */ 
/*    */ public class CurrencyConverter extends NumberFormatConverter
/*    */ {
/* 15 */   public static ConverterContext CONTEXT = new ConverterContext("Currency");
/*    */ 
/*    */   public CurrencyConverter() {
/* 18 */     this(NumberFormat.getCurrencyInstance());
/*    */   }
/*    */ 
/*    */   public CurrencyConverter(NumberFormat format) {
/* 22 */     super(format);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.CurrencyConverter
 * JD-Core Version:    0.6.0
 */
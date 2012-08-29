/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ import java.text.NumberFormat;
/*    */ 
/*    */ public class PercentConverter extends NumberFormatConverter
/*    */ {
/* 15 */   public static ConverterContext CONTEXT = new ConverterContext("Percent");
/*    */ 
/*    */   public PercentConverter() {
/* 18 */     this(NumberFormat.getPercentInstance());
/*    */   }
/*    */ 
/*    */   public PercentConverter(NumberFormat format) {
/* 22 */     super(format);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.PercentConverter
 * JD-Core Version:    0.6.0
 */
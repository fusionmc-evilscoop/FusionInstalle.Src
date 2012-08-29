/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ public class MultilineStringConverter
/*    */   implements ObjectConverter
/*    */ {
/* 14 */   public static final ConverterContext CONTEXT = new ConverterContext("MultilineString");
/*    */ 
/*    */   public String toString(Object object, ConverterContext context) {
/* 17 */     if ((object instanceof String)) {
/* 18 */       return ((String)object).replaceAll("\n", "\\\\n").replaceAll("\r", "\\\\r");
/*    */     }
/* 20 */     if (object == null) {
/* 21 */       return "";
/*    */     }
/*    */ 
/* 24 */     return "" + object;
/*    */   }
/*    */ 
/*    */   public boolean supportToString(Object object, ConverterContext context)
/*    */   {
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */   public Object fromString(String string, ConverterContext context) {
/* 33 */     if (string != null) {
/* 34 */       return string.replaceAll("\\\\n", "\n").replaceAll("\\\\r", "\r");
/*    */     }
/*    */ 
/* 37 */     return null;
/*    */   }
/*    */ 
/*    */   public boolean supportFromString(String string, ConverterContext context)
/*    */   {
/* 42 */     return true;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.MultilineStringConverter
 * JD-Core Version:    0.6.0
 */
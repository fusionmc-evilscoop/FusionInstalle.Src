/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ public class YearNameConverter
/*    */   implements ObjectConverter
/*    */ {
/* 18 */   public static ConverterContext CONTEXT = new ConverterContext("YearName");
/*    */ 
/*    */   public String toString(Object object, ConverterContext context)
/*    */   {
/* 27 */     if ((object == null) || (!(object instanceof Number))) {
/* 28 */       return "";
/*    */     }
/*    */ 
/* 31 */     return object.toString();
/*    */   }
/*    */ 
/*    */   public boolean supportToString(Object object, ConverterContext context)
/*    */   {
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */   public Object fromString(String string, ConverterContext context) {
/*    */     try {
/* 41 */       return Integer.valueOf(Integer.parseInt(string));
/*    */     } catch (NumberFormatException e) {
/*    */     }
/* 44 */     return string;
/*    */   }
/*    */ 
/*    */   public boolean supportFromString(String string, ConverterContext context)
/*    */   {
/* 49 */     return true;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.YearNameConverter
 * JD-Core Version:    0.6.0
 */
/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ import java.util.Calendar;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class CalendarConverter extends DateConverter
/*    */ {
/*    */   public String toString(Object object, ConverterContext context)
/*    */   {
/* 24 */     if (object == null) {
/* 25 */       return "";
/*    */     }
/* 27 */     if (!(object instanceof Calendar)) {
/* 28 */       return object.toString();
/*    */     }
/*    */ 
/* 31 */     return super.toString(object, context);
/*    */   }
/*    */ 
/*    */   public Object fromString(String string, ConverterContext context)
/*    */   {
/* 45 */     if ((string == null) || (string.trim().length() == 0)) {
/* 46 */       return null;
/*    */     }
/*    */ 
/* 49 */     Object date = super.fromString(string, context);
/* 50 */     Calendar calendar = Calendar.getInstance();
/* 51 */     if ((date instanceof Date)) {
/* 52 */       calendar.setTime((Date)date);
/* 53 */       return calendar;
/*    */     }
/*    */ 
/* 56 */     return string;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.CalendarConverter
 * JD-Core Version:    0.6.0
 */
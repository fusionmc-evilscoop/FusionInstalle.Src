/*    */ package com.jidesoft.grouper.date;
/*    */ 
/*    */ import com.jidesoft.grouper.AbstractObjectGrouper;
/*    */ import java.util.Calendar;
/*    */ import java.util.Date;
/*    */ 
/*    */ public abstract class DateGrouper extends AbstractObjectGrouper
/*    */ {
/* 14 */   protected static Calendar INSTANCE = Calendar.getInstance();
/*    */ 
/*    */   public static Object getCalendarField(Object value, int field) {
/* 17 */     if ((value instanceof Date)) {
/* 18 */       INSTANCE.setTime((Date)value);
/* 19 */       return Integer.valueOf(INSTANCE.get(field));
/*    */     }
/* 21 */     if ((value instanceof Long)) {
/* 22 */       INSTANCE.setTime(new Date(((Long)value).longValue()));
/* 23 */       return Integer.valueOf(INSTANCE.get(field));
/*    */     }
/* 25 */     if ((value instanceof Calendar)) {
/* 26 */       return Integer.valueOf(((Calendar)value).get(field));
/*    */     }
/* 28 */     if (value == null) {
/* 29 */       return null;
/*    */     }
/*    */ 
/* 32 */     throw new IllegalArgumentException("Type incompatible");
/*    */   }
/*    */ 
/*    */   public static int getCalendarFieldAsInt(Object value, int field)
/*    */   {
/* 37 */     if ((value instanceof Date)) {
/* 38 */       INSTANCE.setTime((Date)value);
/* 39 */       return INSTANCE.get(field);
/*    */     }
/* 41 */     if ((value instanceof Long)) {
/* 42 */       INSTANCE.setTime(new Date(((Long)value).longValue()));
/* 43 */       return INSTANCE.get(field);
/*    */     }
/* 45 */     if ((value instanceof Calendar)) {
/* 46 */       return ((Calendar)value).get(field);
/*    */     }
/* 48 */     if (value == null) {
/* 49 */       return -1;
/*    */     }
/*    */ 
/* 52 */     throw new IllegalArgumentException("Type incompatible");
/*    */   }
/*    */ 
/*    */   public Class<?> getType()
/*    */   {
/* 57 */     return Integer.TYPE;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.grouper.date.DateGrouper
 * JD-Core Version:    0.6.0
 */
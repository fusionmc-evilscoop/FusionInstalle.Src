/*    */ package com.jidesoft.grouper.date;
/*    */ 
/*    */ import com.jidesoft.grouper.GroupResources;
/*    */ import com.jidesoft.grouper.GrouperContext;
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public class DateDayOfYearGrouper extends DateGrouper
/*    */ {
/* 10 */   public static GrouperContext CONTEXT = new GrouperContext("DateDayOfYear");
/*    */ 
/*    */   public Object getValue(Object value) {
/* 13 */     return getCalendarField(value, 6);
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 17 */     return GroupResources.getResourceBundle(Locale.getDefault()).getString("Date.dayOfYear");
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.grouper.date.DateDayOfYearGrouper
 * JD-Core Version:    0.6.0
 */
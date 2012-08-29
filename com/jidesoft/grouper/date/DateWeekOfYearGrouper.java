/*    */ package com.jidesoft.grouper.date;
/*    */ 
/*    */ import com.jidesoft.grouper.GroupResources;
/*    */ import com.jidesoft.grouper.GrouperContext;
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public class DateWeekOfYearGrouper extends DateGrouper
/*    */ {
/* 16 */   public static GrouperContext CONTEXT = new GrouperContext("DateWeekOfYear");
/*    */ 
/*    */   public Object getValue(Object value) {
/* 19 */     return getCalendarField(value, 3);
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 23 */     return GroupResources.getResourceBundle(Locale.getDefault()).getString("Date.weekOfYear");
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.grouper.date.DateWeekOfYearGrouper
 * JD-Core Version:    0.6.0
 */
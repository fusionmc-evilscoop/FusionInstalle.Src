/*    */ package com.jidesoft.grouper.date;
/*    */ 
/*    */ import com.jidesoft.grouper.GroupResources;
/*    */ import com.jidesoft.grouper.GrouperContext;
/*    */ import java.util.Calendar;
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public class DateDayOfWeekInMonthGrouper extends DateGrouper
/*    */ {
/* 16 */   public static GrouperContext CONTEXT = new GrouperContext("DateDayOfWeekInMonth");
/*    */ 
/* 18 */   private static Object[] _groups = null;
/*    */ 
/*    */   public static Object[] getAvailableGroups() {
/* 21 */     if (_groups == null) {
/* 22 */       Calendar cal = Calendar.getInstance();
/* 23 */       cal.set(8, 0);
/* 24 */       _groups = new Object[cal.getMaximum(8)];
/* 25 */       for (int i = 0; i < _groups.length; i++) {
/* 26 */         _groups[i] = getCalendarField(cal, 8);
/* 27 */         cal.roll(8, 1);
/*    */       }
/*    */     }
/* 30 */     return _groups;
/*    */   }
/*    */ 
/*    */   public Object getValue(Object value) {
/* 34 */     Object field = getCalendarField(value, 8);
/* 35 */     if (((field instanceof Integer)) && (((Integer)field).intValue() >= 0) && (((Integer)field).intValue() < getAvailableGroups().length)) {
/* 36 */       return getAvailableGroups()[((Integer)field).intValue()];
/*    */     }
/*    */ 
/* 39 */     return null;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 44 */     return GroupResources.getResourceBundle(Locale.getDefault()).getString("Date.dayOfWeekInMonth");
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.grouper.date.DateDayOfWeekInMonthGrouper
 * JD-Core Version:    0.6.0
 */
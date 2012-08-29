/*    */ package com.jidesoft.grouper.date;
/*    */ 
/*    */ import com.jidesoft.grouper.GroupResources;
/*    */ import com.jidesoft.grouper.GrouperContext;
/*    */ import java.util.Calendar;
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public class DateDayOfMonthGrouper extends DateGrouper
/*    */ {
/* 10 */   public static GrouperContext CONTEXT = new GrouperContext("DateDayOfMonth");
/*    */ 
/* 12 */   private static Object[] _groups = null;
/*    */ 
/*    */   public static Object[] getAvailableGroups() {
/* 15 */     if (_groups == null) {
/* 16 */       Calendar cal = Calendar.getInstance();
/* 17 */       cal.set(2, 0);
/* 18 */       cal.set(5, 1);
/* 19 */       _groups = new Object[cal.getMaximum(5)];
/* 20 */       for (int i = 0; i < _groups.length; i++) {
/* 21 */         _groups[i] = getCalendarField(cal, 5);
/* 22 */         cal.roll(5, 1);
/*    */       }
/*    */     }
/* 25 */     return _groups;
/*    */   }
/*    */ 
/*    */   public Object getValue(Object value) {
/* 29 */     Object field = getCalendarField(value, 5);
/* 30 */     if (((field instanceof Integer)) && (((Integer)field).intValue() >= 1) && (((Integer)field).intValue() <= getAvailableGroups().length)) {
/* 31 */       return getAvailableGroups()[(((Integer)field).intValue() - 1)];
/*    */     }
/*    */ 
/* 34 */     return null;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 39 */     return GroupResources.getResourceBundle(Locale.getDefault()).getString("Date.dayOfMonth");
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.grouper.date.DateDayOfMonthGrouper
 * JD-Core Version:    0.6.0
 */
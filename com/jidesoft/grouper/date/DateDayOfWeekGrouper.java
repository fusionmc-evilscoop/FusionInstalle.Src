/*    */ package com.jidesoft.grouper.date;
/*    */ 
/*    */ import com.jidesoft.grouper.GroupResources;
/*    */ import com.jidesoft.grouper.GrouperContext;
/*    */ import java.util.Calendar;
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public class DateDayOfWeekGrouper extends DateGrouper
/*    */ {
/* 11 */   public static GrouperContext CONTEXT = new GrouperContext("DateDayOfWeek");
/*    */ 
/* 13 */   private static Object[] _groups = null;
/*    */ 
/*    */   public static Object[] getAvailableGroups() {
/* 16 */     if (_groups == null) {
/* 17 */       Calendar cal = Calendar.getInstance();
/* 18 */       cal.set(7, 0);
/* 19 */       _groups = new Object[cal.getMaximum(7)];
/* 20 */       for (int i = 0; i < _groups.length; i++) {
/* 21 */         _groups[i] = Integer.valueOf(i + 1);
/* 22 */         cal.roll(7, 1);
/*    */       }
/*    */     }
/* 25 */     return _groups;
/*    */   }
/*    */ 
/*    */   public Object getValue(Object value) {
/* 29 */     Object field = getCalendarField(value, 7);
/* 30 */     if ((field instanceof Integer)) {
/* 31 */       int intValue = ((Integer)field).intValue();
/* 32 */       if ((intValue >= 1) && (intValue < getAvailableGroups().length + 1)) {
/* 33 */         return getAvailableGroups()[(intValue - 1)];
/*    */       }
/*    */     }
/* 36 */     return null;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 40 */     return GroupResources.getResourceBundle(Locale.getDefault()).getString("Date.dayOfWeek");
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.grouper.date.DateDayOfWeekGrouper
 * JD-Core Version:    0.6.0
 */
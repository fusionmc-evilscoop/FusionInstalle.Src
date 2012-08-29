/*    */ package com.jidesoft.grouper.date;
/*    */ 
/*    */ import com.jidesoft.grouper.GroupResources;
/*    */ import com.jidesoft.grouper.GrouperContext;
/*    */ import java.util.Calendar;
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public class DateWeekOfMonthGrouper extends DateGrouper
/*    */ {
/* 10 */   public static GrouperContext CONTEXT = new GrouperContext("DateWeekOfMonth");
/*    */ 
/* 13 */   private static Object[] _groups = null;
/*    */ 
/*    */   public static Object[] getAvailableGroups() {
/* 16 */     if (_groups == null) {
/* 17 */       Calendar cal = Calendar.getInstance();
/* 18 */       cal.set(1, 2010);
/* 19 */       cal.set(2, 0);
/* 20 */       cal.set(5, 1);
/* 21 */       _groups = new Object[6];
/* 22 */       for (int i = 0; i < _groups.length; i++) {
/* 23 */         _groups[i] = getCalendarField(cal, 4);
/* 24 */         cal.roll(4, 1);
/*    */       }
/*    */     }
/* 27 */     return _groups;
/*    */   }
/*    */ 
/*    */   public Object getValue(Object value) {
/* 31 */     Object field = getCalendarField(value, 4);
/* 32 */     if (((field instanceof Integer)) && (((Integer)field).intValue() > 0) && (((Integer)field).intValue() <= getAvailableGroups().length)) {
/* 33 */       return getAvailableGroups()[(((Integer)field).intValue() - 1)];
/*    */     }
/*    */ 
/* 36 */     return null;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 41 */     return GroupResources.getResourceBundle(Locale.getDefault()).getString("Date.weekOfMonth");
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.grouper.date.DateWeekOfMonthGrouper
 * JD-Core Version:    0.6.0
 */
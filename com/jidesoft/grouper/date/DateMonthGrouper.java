/*    */ package com.jidesoft.grouper.date;
/*    */ 
/*    */ import com.jidesoft.converter.ConverterContext;
/*    */ import com.jidesoft.converter.MonthNameConverter;
/*    */ import com.jidesoft.grouper.GroupResources;
/*    */ import com.jidesoft.grouper.GrouperContext;
/*    */ import java.util.Calendar;
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public class DateMonthGrouper extends DateGrouper
/*    */ {
/* 12 */   public static GrouperContext CONTEXT = new GrouperContext("DateMonth");
/*    */ 
/* 14 */   private static Object[] _groups = null;
/*    */ 
/*    */   public static Object[] getAvailableGroups() {
/* 17 */     if (_groups == null) {
/* 18 */       Calendar cal = Calendar.getInstance();
/* 19 */       cal.set(2, 0);
/* 20 */       _groups = new Object[cal.getMaximum(2) + 1];
/* 21 */       for (int i = 0; i < _groups.length; i++) {
/* 22 */         _groups[i] = getCalendarField(cal, 2);
/* 23 */         cal.roll(2, 1);
/*    */       }
/*    */     }
/* 26 */     return _groups;
/*    */   }
/*    */ 
/*    */   public Object getValue(Object value) {
/* 30 */     Object field = getCalendarField(value, 2);
/* 31 */     if (((field instanceof Integer)) && (((Integer)field).intValue() >= 0) && (((Integer)field).intValue() < getAvailableGroups().length)) {
/* 32 */       return getAvailableGroups()[((Integer)field).intValue()];
/*    */     }
/*    */ 
/* 35 */     return null;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 41 */     return GroupResources.getResourceBundle(Locale.getDefault()).getString("Date.month");
/*    */   }
/*    */ 
/*    */   public ConverterContext getConverterContext()
/*    */   {
/* 46 */     return MonthNameConverter.CONTEXT;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.grouper.date.DateMonthGrouper
 * JD-Core Version:    0.6.0
 */
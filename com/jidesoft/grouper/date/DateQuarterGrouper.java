/*    */ package com.jidesoft.grouper.date;
/*    */ 
/*    */ import com.jidesoft.converter.ConverterContext;
/*    */ import com.jidesoft.converter.QuarterNameConverter;
/*    */ import com.jidesoft.grouper.GroupResources;
/*    */ import com.jidesoft.grouper.GrouperContext;
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public class DateQuarterGrouper extends DateGrouper
/*    */ {
/* 18 */   public static GrouperContext CONTEXT = new GrouperContext("DateQuarter");
/*    */ 
/* 20 */   private static Object[] _groups = null;
/*    */ 
/*    */   public static Object[] getAvailableGroups() {
/* 23 */     if (_groups == null) {
/* 24 */       _groups = new Object[4];
/* 25 */       for (int i = 0; i < _groups.length; i++) {
/* 26 */         _groups[i] = Integer.valueOf(i);
/*    */       }
/*    */     }
/* 29 */     return _groups;
/*    */   }
/*    */ 
/*    */   public Object getValue(Object value) {
/* 33 */     Object dateField = getCalendarField(value, 2);
/* 34 */     if ((dateField instanceof Integer)) {
/* 35 */       return getAvailableGroups()[(((Integer)dateField).intValue() / 3)];
/*    */     }
/*    */ 
/* 38 */     return null;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 43 */     return GroupResources.getResourceBundle(Locale.getDefault()).getString("Date.quarter");
/*    */   }
/*    */ 
/*    */   public ConverterContext getConverterContext()
/*    */   {
/* 48 */     return QuarterNameConverter.CONTEXT;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.grouper.date.DateQuarterGrouper
 * JD-Core Version:    0.6.0
 */
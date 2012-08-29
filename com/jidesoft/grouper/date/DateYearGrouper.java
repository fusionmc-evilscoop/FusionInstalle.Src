/*    */ package com.jidesoft.grouper.date;
/*    */ 
/*    */ import com.jidesoft.converter.ConverterContext;
/*    */ import com.jidesoft.converter.YearNameConverter;
/*    */ import com.jidesoft.grouper.GroupResources;
/*    */ import com.jidesoft.grouper.GrouperContext;
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public class DateYearGrouper extends DateGrouper
/*    */ {
/* 14 */   public static GrouperContext CONTEXT = new GrouperContext("DateYear");
/*    */ 
/*    */   public Object getValue(Object value) {
/* 17 */     return getCalendarField(value, 1);
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 21 */     return GroupResources.getResourceBundle(Locale.getDefault()).getString("Date.year");
/*    */   }
/*    */ 
/*    */   public ConverterContext getConverterContext()
/*    */   {
/* 36 */     return YearNameConverter.CONTEXT;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.grouper.date.DateYearGrouper
 * JD-Core Version:    0.6.0
 */
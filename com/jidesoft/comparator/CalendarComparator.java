/*    */ package com.jidesoft.comparator;
/*    */ 
/*    */ import java.util.Calendar;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public class CalendarComparator
/*    */   implements Comparator
/*    */ {
/* 11 */   private static CalendarComparator singleton = null;
/*    */ 
/*    */   public static CalendarComparator getInstance()
/*    */   {
/* 28 */     if (singleton == null)
/* 29 */       singleton = new CalendarComparator();
/* 30 */     return singleton;
/*    */   }
/*    */ 
/*    */   public int compare(Object o1, Object o2)
/*    */   {
/* 41 */     if ((o1 == null) && (o2 == null)) {
/* 42 */       return 0;
/*    */     }
/* 44 */     if (o1 == null) {
/* 45 */       return -1;
/*    */     }
/* 47 */     if (o2 == null) {
/* 48 */       return 1;
/*    */     }
/*    */ 
/* 51 */     if ((o1 instanceof Calendar)) {
/* 52 */       if ((o2 instanceof Calendar)) {
/* 53 */         Calendar l = (Calendar)o1;
/* 54 */         Calendar r = (Calendar)o2;
/*    */ 
/* 56 */         if (l.before(r))
/* 57 */           return -1;
/* 58 */         if (l.equals(r)) {
/* 59 */           return 0;
/*    */         }
/* 61 */         return 1;
/*    */       }
/*    */ 
/* 65 */       throw new ClassCastException("The first argument of this method was not a Calendar but " + o2.getClass().getName());
/*    */     }
/*    */ 
/* 71 */     if ((o2 instanceof Calendar))
/*    */     {
/* 73 */       throw new ClassCastException("The second argument of this method was not a Calendar but " + o1.getClass().getName());
/*    */     }
/*    */ 
/* 80 */     throw new ClassCastException("Both arguments of this method were not Calendars. They are " + o1.getClass().getName() + " and " + o2.getClass().getName());
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.comparator.CalendarComparator
 * JD-Core Version:    0.6.0
 */
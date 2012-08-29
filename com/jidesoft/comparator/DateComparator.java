/*    */ package com.jidesoft.comparator;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class DateComparator
/*    */   implements Comparator
/*    */ {
/* 16 */   private static DateComparator singleton = null;
/*    */ 
/*    */   public static DateComparator getInstance()
/*    */   {
/* 33 */     if (singleton == null)
/* 34 */       singleton = new DateComparator();
/* 35 */     return singleton;
/*    */   }
/*    */ 
/*    */   public int compare(Object o1, Object o2)
/*    */   {
/* 46 */     if ((o1 == null) && (o2 == null)) {
/* 47 */       return 0;
/*    */     }
/* 49 */     if (o1 == null) {
/* 50 */       return -1;
/*    */     }
/* 52 */     if (o2 == null) {
/* 53 */       return 1;
/*    */     }
/*    */ 
/* 56 */     if ((o1 instanceof Date)) {
/* 57 */       if ((o2 instanceof Date)) {
/* 58 */         Date l = (Date)o1;
/* 59 */         Date r = (Date)o2;
/*    */ 
/* 61 */         if (l.before(r))
/* 62 */           return -1;
/* 63 */         if (l.equals(r)) {
/* 64 */           return 0;
/*    */         }
/* 66 */         return 1;
/*    */       }
/*    */ 
/* 70 */       throw new ClassCastException("The first argument of this method was not a Date but " + o2.getClass().getName());
/*    */     }
/*    */ 
/* 76 */     if ((o2 instanceof Date))
/*    */     {
/* 78 */       throw new ClassCastException("The second argument of this method was not a Date but " + o1.getClass().getName());
/*    */     }
/*    */ 
/* 85 */     throw new ClassCastException("Both arguments of this method were not Dates. They are " + o1.getClass().getName() + " and " + o2.getClass().getName());
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.comparator.DateComparator
 * JD-Core Version:    0.6.0
 */
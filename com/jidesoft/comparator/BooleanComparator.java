/*    */ package com.jidesoft.comparator;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public class BooleanComparator
/*    */   implements Comparator
/*    */ {
/* 10 */   private static BooleanComparator singleton = null;
/*    */ 
/*    */   public static BooleanComparator getInstance()
/*    */   {
/* 27 */     if (singleton == null)
/* 28 */       singleton = new BooleanComparator();
/* 29 */     return singleton;
/*    */   }
/*    */ 
/*    */   public int compare(Object o1, Object o2)
/*    */   {
/* 40 */     if ((o1 == null) && (o2 == null)) {
/* 41 */       return 0;
/*    */     }
/* 43 */     if (o1 == null) {
/* 44 */       return -1;
/*    */     }
/* 46 */     if (o2 == null) {
/* 47 */       return 1;
/*    */     }
/*    */ 
/* 50 */     if ((o1 instanceof Boolean)) {
/* 51 */       if ((o2 instanceof Boolean)) {
/* 52 */         boolean b1 = ((Boolean)o1).booleanValue();
/* 53 */         boolean b2 = ((Boolean)o2).booleanValue();
/*    */ 
/* 55 */         if (b1 == b2)
/* 56 */           return 0;
/* 57 */         if (b1) {
/* 58 */           return 1;
/*    */         }
/* 60 */         return -1;
/*    */       }
/*    */ 
/* 64 */       throw new ClassCastException("The first argument of this method was not a Boolean but " + o2.getClass().getName());
/*    */     }
/*    */ 
/* 70 */     if ((o2 instanceof Boolean))
/*    */     {
/* 72 */       throw new ClassCastException("The second argument of this method was not a Boolean but " + o1.getClass().getName());
/*    */     }
/*    */ 
/* 79 */     throw new ClassCastException("Both arguments of this method were not Booleans. They are " + o1.getClass().getName() + " and " + o2.getClass().getName());
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.comparator.BooleanComparator
 * JD-Core Version:    0.6.0
 */
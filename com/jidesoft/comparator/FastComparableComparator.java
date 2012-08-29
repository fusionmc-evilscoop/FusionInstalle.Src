/*    */ package com.jidesoft.comparator;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public class FastComparableComparator
/*    */   implements Comparator, Serializable
/*    */ {
/* 24 */   private static final FastComparableComparator instance = new FastComparableComparator();
/*    */ 
/*    */   public static FastComparableComparator getInstance()
/*    */   {
/* 35 */     return instance;
/*    */   }
/*    */ 
/*    */   public int compare(Object o1, Object o2)
/*    */   {
/* 45 */     if ((o1 == null) && (o2 == null)) {
/* 46 */       return 0;
/*    */     }
/* 48 */     if (o1 == null) {
/* 49 */       return -1;
/*    */     }
/* 51 */     if (o2 == null) {
/* 52 */       return 1;
/*    */     }
/*    */ 
/* 55 */     if ((o1 instanceof Comparable)) {
/* 56 */       if ((o2 instanceof Comparable)) {
/* 57 */         return ((Comparable)o1).compareTo(o2);
/*    */       }
/*    */ 
/* 61 */       throw new ClassCastException("The second argument of this method was not a Comparable: " + o2.getClass().getName());
/*    */     }
/*    */ 
/* 64 */     if ((o2 instanceof Comparable))
/*    */     {
/* 66 */       throw new ClassCastException("The first argument of this method was not a Comparable: " + o1.getClass().getName());
/*    */     }
/*    */ 
/* 70 */     throw new ClassCastException("Both arguments of this method were not Comparables: " + o1.getClass().getName() + " and " + o2.getClass().getName());
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.comparator.FastComparableComparator
 * JD-Core Version:    0.6.0
 */
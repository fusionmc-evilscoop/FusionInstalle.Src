/*    */ package com.jidesoft.comparator;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public class DefaultComparator
/*    */   implements Comparator
/*    */ {
/* 10 */   private static DefaultComparator singleton = null;
/*    */ 
/*    */   public static DefaultComparator getInstance()
/*    */   {
/* 27 */     if (singleton == null)
/* 28 */       singleton = new DefaultComparator();
/* 29 */     return singleton;
/*    */   }
/*    */ 
/*    */   public int compare(Object o1, Object o2)
/*    */   {
/* 42 */     if ((o1 == null) && (o2 == null)) {
/* 43 */       return 0;
/*    */     }
/* 45 */     if (o1 == null) {
/* 46 */       return -1;
/*    */     }
/* 48 */     if (o2 == null) {
/* 49 */       return 1;
/*    */     }
/*    */ 
/* 52 */     String s1 = o1.toString();
/* 53 */     String s2 = o2.toString();
/* 54 */     return s1.compareTo(s2);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.comparator.DefaultComparator
 * JD-Core Version:    0.6.0
 */
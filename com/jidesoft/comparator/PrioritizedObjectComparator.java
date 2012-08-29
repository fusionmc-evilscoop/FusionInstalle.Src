/*    */ package com.jidesoft.comparator;
/*    */ 
/*    */ import com.jidesoft.swing.Prioritized;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public class PrioritizedObjectComparator
/*    */   implements Comparator
/*    */ {
/* 13 */   private static PrioritizedObjectComparator singleton = null;
/*    */ 
/*    */   public static PrioritizedObjectComparator getInstance()
/*    */   {
/* 24 */     if (singleton == null) {
/* 25 */       singleton = new PrioritizedObjectComparator();
/*    */     }
/* 27 */     return singleton;
/*    */   }
/*    */ 
/*    */   public int compare(Object o1, Object o2) {
/* 31 */     int p1 = 0;
/* 32 */     if ((o1 instanceof Prioritized)) {
/* 33 */       p1 = ((Prioritized)o1).getPriority();
/*    */     }
/* 35 */     int p2 = 0;
/* 36 */     if ((o2 instanceof Prioritized)) {
/* 37 */       p2 = ((Prioritized)o2).getPriority();
/*    */     }
/* 39 */     return p1 - p2;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.comparator.PrioritizedObjectComparator
 * JD-Core Version:    0.6.0
 */
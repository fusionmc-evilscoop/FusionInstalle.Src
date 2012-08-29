/*    */ package org.apache.log4j.helpers;
/*    */ 
/*    */ import java.util.Hashtable;
/*    */ 
/*    */ public final class ThreadLocalMap extends InheritableThreadLocal
/*    */ {
/*    */   public final Object childValue(Object parentValue)
/*    */   {
/* 34 */     Hashtable ht = (Hashtable)parentValue;
/* 35 */     if (ht != null) {
/* 36 */       return ht.clone();
/*    */     }
/* 38 */     return null;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.helpers.ThreadLocalMap
 * JD-Core Version:    0.6.0
 */
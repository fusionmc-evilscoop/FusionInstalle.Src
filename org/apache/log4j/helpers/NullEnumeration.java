/*    */ package org.apache.log4j.helpers;
/*    */ 
/*    */ import java.util.Enumeration;
/*    */ import java.util.NoSuchElementException;
/*    */ 
/*    */ public class NullEnumeration
/*    */   implements Enumeration
/*    */ {
/* 30 */   private static final NullEnumeration instance = new NullEnumeration();
/*    */ 
/*    */   public static NullEnumeration getInstance()
/*    */   {
/* 37 */     return instance;
/*    */   }
/*    */ 
/*    */   public boolean hasMoreElements()
/*    */   {
/* 42 */     return false;
/*    */   }
/*    */ 
/*    */   public Object nextElement()
/*    */   {
/* 47 */     throw new NoSuchElementException();
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.helpers.NullEnumeration
 * JD-Core Version:    0.6.0
 */
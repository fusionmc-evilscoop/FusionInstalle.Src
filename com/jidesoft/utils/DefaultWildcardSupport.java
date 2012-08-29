/*    */ package com.jidesoft.utils;
/*    */ 
/*    */ public class DefaultWildcardSupport extends AbstractWildcardSupport
/*    */ {
/*    */   private static final long serialVersionUID = -5528733766095113518L;
/*    */ 
/*    */   public char getZeroOrOneQuantifier()
/*    */   {
/* 18 */     return '?';
/*    */   }
/*    */ 
/*    */   public char getZeroOrMoreQuantifier() {
/* 22 */     return '*';
/*    */   }
/*    */ 
/*    */   public char getOneOrMoreQuantifier() {
/* 26 */     return '+';
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.DefaultWildcardSupport
 * JD-Core Version:    0.6.0
 */
/*    */ package org.apache.log4j.or;
/*    */ 
/*    */ class DefaultRenderer
/*    */   implements ObjectRenderer
/*    */ {
/*    */   public String doRender(Object o)
/*    */   {
/* 35 */     return o.toString();
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.or.DefaultRenderer
 * JD-Core Version:    0.6.0
 */
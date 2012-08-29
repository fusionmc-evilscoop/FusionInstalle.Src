/*    */ package org.apache.log4j.config;
/*    */ 
/*    */ public class PropertySetterException extends Exception
/*    */ {
/*    */   protected Throwable rootCause;
/*    */ 
/*    */   public PropertySetterException(String msg)
/*    */   {
/* 31 */     super(msg);
/*    */   }
/*    */ 
/*    */   public PropertySetterException(Throwable rootCause)
/*    */   {
/* 38 */     this.rootCause = rootCause;
/*    */   }
/*    */ 
/*    */   public String getMessage()
/*    */   {
/* 46 */     String msg = super.getMessage();
/* 47 */     if ((msg == null) && (this.rootCause != null)) {
/* 48 */       msg = this.rootCause.getMessage();
/*    */     }
/* 50 */     return msg;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.config.PropertySetterException
 * JD-Core Version:    0.6.0
 */
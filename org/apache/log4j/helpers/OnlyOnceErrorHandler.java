/*    */ package org.apache.log4j.helpers;
/*    */ 
/*    */ import org.apache.log4j.Appender;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.spi.ErrorHandler;
/*    */ import org.apache.log4j.spi.LoggingEvent;
/*    */ 
/*    */ public class OnlyOnceErrorHandler
/*    */   implements ErrorHandler
/*    */ {
/* 40 */   final String WARN_PREFIX = "log4j warning: ";
/* 41 */   final String ERROR_PREFIX = "log4j error: ";
/*    */ 
/* 43 */   boolean firstTime = true;
/*    */ 
/*    */   public void setLogger(Logger logger)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void activateOptions()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void error(String message, Exception e, int errorCode)
/*    */   {
/* 67 */     error(message, e, errorCode, null);
/*    */   }
/*    */ 
/*    */   public void error(String message, Exception e, int errorCode, LoggingEvent event)
/*    */   {
/* 76 */     if (this.firstTime) {
/* 77 */       LogLog.error(message, e);
/* 78 */       this.firstTime = false;
/*    */     }
/*    */   }
/*    */ 
/*    */   public void error(String message)
/*    */   {
/* 89 */     if (this.firstTime) {
/* 90 */       LogLog.error(message);
/* 91 */       this.firstTime = false;
/*    */     }
/*    */   }
/*    */ 
/*    */   public void setAppender(Appender appender)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void setBackupAppender(Appender appender)
/*    */   {
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.helpers.OnlyOnceErrorHandler
 * JD-Core Version:    0.6.0
 */
/*    */ package org.apache.log4j.lf5;
/*    */ 
/*    */ import org.apache.log4j.spi.ThrowableInformation;
/*    */ 
/*    */ public class Log4JLogRecord extends LogRecord
/*    */ {
/*    */   public boolean isSevereLevel()
/*    */   {
/* 66 */     boolean isSevere = false;
/*    */ 
/* 68 */     if ((LogLevel.ERROR.equals(getLevel())) || (LogLevel.FATAL.equals(getLevel())))
/*    */     {
/* 70 */       isSevere = true;
/*    */     }
/*    */ 
/* 73 */     return isSevere;
/*    */   }
/*    */ 
/*    */   public void setThrownStackTrace(ThrowableInformation throwableInfo)
/*    */   {
/* 87 */     String[] stackTraceArray = throwableInfo.getThrowableStrRep();
/*    */ 
/* 89 */     StringBuffer stackTrace = new StringBuffer();
/*    */ 
/* 92 */     for (int i = 0; i < stackTraceArray.length; i++) {
/* 93 */       String nextLine = stackTraceArray[i] + "\n";
/* 94 */       stackTrace.append(nextLine);
/*    */     }
/*    */ 
/* 97 */     this._thrownStackTrace = stackTrace.toString();
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.Log4JLogRecord
 * JD-Core Version:    0.6.0
 */
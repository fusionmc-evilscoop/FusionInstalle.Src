/*    */ package org.apache.log4j;
/*    */ 
/*    */ import org.apache.log4j.spi.LoggingEvent;
/*    */ import org.apache.log4j.spi.OptionHandler;
/*    */ 
/*    */ public abstract class Layout
/*    */   implements OptionHandler
/*    */ {
/* 33 */   public static final String LINE_SEP = System.getProperty("line.separator");
/* 34 */   public static final int LINE_SEP_LEN = LINE_SEP.length();
/*    */ 
/*    */   public abstract String format(LoggingEvent paramLoggingEvent);
/*    */ 
/*    */   public String getContentType()
/*    */   {
/* 50 */     return "text/plain";
/*    */   }
/*    */ 
/*    */   public String getHeader()
/*    */   {
/* 58 */     return null;
/*    */   }
/*    */ 
/*    */   public String getFooter()
/*    */   {
/* 66 */     return null;
/*    */   }
/*    */ 
/*    */   public abstract boolean ignoresThrowable();
/*    */ 
/*    */   public abstract void activateOptions();
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.Layout
 * JD-Core Version:    0.6.0
 */
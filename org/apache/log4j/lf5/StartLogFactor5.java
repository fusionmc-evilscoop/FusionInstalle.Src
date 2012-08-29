/*    */ package org.apache.log4j.lf5;
/*    */ 
/*    */ import org.apache.log4j.lf5.viewer.LogBrokerMonitor;
/*    */ 
/*    */ public class StartLogFactor5
/*    */ {
/*    */   public static final void main(String[] args)
/*    */   {
/* 56 */     LogBrokerMonitor monitor = new LogBrokerMonitor(LogLevel.getLog4JLevels());
/*    */ 
/* 59 */     monitor.setFrameSize(LF5Appender.getDefaultMonitorWidth(), LF5Appender.getDefaultMonitorHeight());
/*    */ 
/* 61 */     monitor.setFontSize(12);
/* 62 */     monitor.show();
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.StartLogFactor5
 * JD-Core Version:    0.6.0
 */
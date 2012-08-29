/*    */ package org.apache.log4j.lf5;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import org.apache.log4j.lf5.viewer.LogBrokerMonitor;
/*    */ 
/*    */ public class AppenderFinalizer
/*    */ {
/* 40 */   protected LogBrokerMonitor _defaultMonitor = null;
/*    */ 
/*    */   public AppenderFinalizer(LogBrokerMonitor defaultMonitor)
/*    */   {
/* 51 */     this._defaultMonitor = defaultMonitor;
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 65 */     System.out.println("Disposing of the default LogBrokerMonitor instance");
/* 66 */     this._defaultMonitor.dispose();
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.AppenderFinalizer
 * JD-Core Version:    0.6.0
 */
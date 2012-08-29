/*    */ package com.jidesoft.utils;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.logging.Handler;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.LogRecord;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ public class LoggerUtils
/*    */ {
/*    */   public static Handler enableLogger(String loggerName, Level level)
/*    */   {
/* 14 */     Logger log = Logger.getLogger(loggerName);
/* 15 */     log.setLevel(level);
/* 16 */     Handler handler = new Handler()
/*    */     {
/*    */       public void publish(LogRecord record)
/*    */       {
/* 20 */         System.err.print(record.getMessage());
/* 21 */         Object[] params = record.getParameters();
/* 22 */         if (params != null) {
/* 23 */           if (params.length > 0)
/* 24 */             System.err.print("= ");
/* 25 */           for (int i = 0; i < params.length; i++) {
/* 26 */             System.err.print(params[i]);
/* 27 */             if (i < params.length - 1)
/* 28 */               System.err.print(", ");
/*    */           }
/*    */         }
/* 31 */         System.err.println();
/*    */       }
/*    */ 
/*    */       public void flush()
/*    */       {
/*    */       }
/*    */ 
/*    */       public void close()
/*    */         throws SecurityException
/*    */       {
/*    */       }
/*    */     };
/* 42 */     log.addHandler(handler);
/* 43 */     return handler;
/*    */   }
/*    */ 
/*    */   public static void disableLogger(String loggerName, Handler handler) {
/* 47 */     Logger log = Logger.getLogger(loggerName);
/* 48 */     log.setLevel(null);
/* 49 */     log.removeHandler(handler);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.LoggerUtils
 * JD-Core Version:    0.6.0
 */
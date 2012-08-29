/*    */ package org.apache.log4j;
/*    */ 
/*    */ public class BasicConfigurator
/*    */ {
/*    */   public static void configure()
/*    */   {
/* 45 */     Logger root = Logger.getRootLogger();
/* 46 */     root.addAppender(new ConsoleAppender(new PatternLayout("%r [%t] %p %c %x - %m%n")));
/*    */   }
/*    */ 
/*    */   public static void configure(Appender appender)
/*    */   {
/* 57 */     Logger root = Logger.getRootLogger();
/* 58 */     root.addAppender(appender);
/*    */   }
/*    */ 
/*    */   public static void resetConfiguration()
/*    */   {
/* 70 */     LogManager.resetConfiguration();
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.BasicConfigurator
 * JD-Core Version:    0.6.0
 */
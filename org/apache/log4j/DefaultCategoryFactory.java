/*    */ package org.apache.log4j;
/*    */ 
/*    */ import org.apache.log4j.spi.LoggerFactory;
/*    */ 
/*    */ class DefaultCategoryFactory
/*    */   implements LoggerFactory
/*    */ {
/*    */   public Logger makeNewLoggerInstance(String name)
/*    */   {
/* 28 */     return new Logger(name);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.DefaultCategoryFactory
 * JD-Core Version:    0.6.0
 */
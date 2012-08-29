/*    */ package org.apache.log4j.varia;
/*    */ 
/*    */ import org.apache.log4j.AppenderSkeleton;
/*    */ import org.apache.log4j.spi.LoggingEvent;
/*    */ 
/*    */ public class NullAppender extends AppenderSkeleton
/*    */ {
/* 34 */   private static NullAppender instance = new NullAppender();
/*    */ 
/*    */   public void activateOptions()
/*    */   {
/*    */   }
/*    */ 
/*    */   public NullAppender getInstance()
/*    */   {
/* 50 */     return instance;
/*    */   }
/*    */ 
/*    */   public void close()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void doAppend(LoggingEvent event)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void append(LoggingEvent event)
/*    */   {
/*    */   }
/*    */ 
/*    */   public boolean requiresLayout()
/*    */   {
/* 72 */     return false;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.varia.NullAppender
 * JD-Core Version:    0.6.0
 */
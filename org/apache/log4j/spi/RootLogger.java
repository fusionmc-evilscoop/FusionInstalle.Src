/*    */ package org.apache.log4j.spi;
/*    */ 
/*    */ import org.apache.log4j.Category;
/*    */ import org.apache.log4j.Level;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.helpers.LogLog;
/*    */ 
/*    */ public final class RootLogger extends Logger
/*    */ {
/*    */   public RootLogger(Level level)
/*    */   {
/* 43 */     super("root");
/* 44 */     setLevel(level);
/*    */   }
/*    */ 
/*    */   public final Level getChainedLevel()
/*    */   {
/* 52 */     return this.level;
/*    */   }
/*    */ 
/*    */   public final void setLevel(Level level)
/*    */   {
/* 61 */     if (level == null) {
/* 62 */       LogLog.error("You have tried to set a null level to root.", new Throwable());
/*    */     }
/*    */     else
/* 65 */       this.level = level;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.spi.RootLogger
 * JD-Core Version:    0.6.0
 */
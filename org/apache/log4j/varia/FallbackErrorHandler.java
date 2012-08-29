/*     */ package org.apache.log4j.varia;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import org.apache.log4j.Appender;
/*     */ import org.apache.log4j.Category;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ import org.apache.log4j.spi.ErrorHandler;
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ public class FallbackErrorHandler
/*     */   implements ErrorHandler
/*     */ {
/*     */   Appender backup;
/*     */   Appender primary;
/*     */   Vector loggers;
/*     */ 
/*     */   public void setLogger(Logger logger)
/*     */   {
/*  55 */     LogLog.debug("FB: Adding logger [" + logger.getName() + "].");
/*  56 */     if (this.loggers == null) {
/*  57 */       this.loggers = new Vector();
/*     */     }
/*  59 */     this.loggers.addElement(logger);
/*     */   }
/*     */ 
/*     */   public void activateOptions()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void error(String message, Exception e, int errorCode)
/*     */   {
/*  76 */     error(message, e, errorCode, null);
/*     */   }
/*     */ 
/*     */   public void error(String message, Exception e, int errorCode, LoggingEvent event)
/*     */   {
/*  85 */     LogLog.debug("FB: The following error reported: " + message, e);
/*  86 */     LogLog.debug("FB: INITIATING FALLBACK PROCEDURE.");
/*  87 */     if (this.loggers != null)
/*  88 */       for (int i = 0; i < this.loggers.size(); i++) {
/*  89 */         Logger l = (Logger)this.loggers.elementAt(i);
/*  90 */         LogLog.debug("FB: Searching for [" + this.primary.getName() + "] in logger [" + l.getName() + "].");
/*     */ 
/*  92 */         LogLog.debug("FB: Replacing [" + this.primary.getName() + "] by [" + this.backup.getName() + "] in logger [" + l.getName() + "].");
/*     */ 
/*  94 */         l.removeAppender(this.primary);
/*  95 */         LogLog.debug("FB: Adding appender [" + this.backup.getName() + "] to logger " + l.getName());
/*     */ 
/*  97 */         l.addAppender(this.backup);
/*     */       }
/*     */   }
/*     */ 
/*     */   public void error(String message)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setAppender(Appender primary)
/*     */   {
/* 120 */     LogLog.debug("FB: Setting primary appender to [" + primary.getName() + "].");
/* 121 */     this.primary = primary;
/*     */   }
/*     */ 
/*     */   public void setBackupAppender(Appender backup)
/*     */   {
/* 129 */     LogLog.debug("FB: Setting backup appender to [" + backup.getName() + "].");
/* 130 */     this.backup = backup;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.varia.FallbackErrorHandler
 * JD-Core Version:    0.6.0
 */
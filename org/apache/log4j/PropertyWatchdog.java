/*     */ package org.apache.log4j;
/*     */ 
/*     */ import org.apache.log4j.helpers.FileWatchdog;
/*     */ 
/*     */ class PropertyWatchdog extends FileWatchdog
/*     */ {
/*     */   PropertyWatchdog(String filename)
/*     */   {
/* 674 */     super(filename);
/*     */   }
/*     */ 
/*     */   public void doOnChange()
/*     */   {
/* 682 */     new PropertyConfigurator().doConfigure(this.filename, LogManager.getLoggerRepository());
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.PropertyWatchdog
 * JD-Core Version:    0.6.0
 */
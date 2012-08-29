/*     */ package org.apache.log4j.xml;
/*     */ 
/*     */ import org.apache.log4j.LogManager;
/*     */ import org.apache.log4j.helpers.FileWatchdog;
/*     */ 
/*     */ class XMLWatchdog extends FileWatchdog
/*     */ {
/*     */   XMLWatchdog(String filename)
/*     */   {
/* 853 */     super(filename);
/*     */   }
/*     */ 
/*     */   public void doOnChange()
/*     */   {
/* 861 */     new DOMConfigurator().doConfigure(this.filename, LogManager.getLoggerRepository());
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.xml.XMLWatchdog
 * JD-Core Version:    0.6.0
 */
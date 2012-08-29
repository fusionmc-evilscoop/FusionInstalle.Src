/*     */ package org.apache.log4j.helpers;
/*     */ 
/*     */ import java.io.File;
/*     */ 
/*     */ public abstract class FileWatchdog extends Thread
/*     */ {
/*     */   public static final long DEFAULT_DELAY = 60000L;
/*     */   protected String filename;
/*  45 */   protected long delay = 60000L;
/*     */   File file;
/*  48 */   long lastModif = 0L;
/*  49 */   boolean warnedAlready = false;
/*  50 */   boolean interrupted = false;
/*     */ 
/*     */   protected FileWatchdog(String filename)
/*     */   {
/*  54 */     this.filename = filename;
/*  55 */     this.file = new File(filename);
/*  56 */     setDaemon(true);
/*  57 */     checkAndConfigure();
/*     */   }
/*     */ 
/*     */   public void setDelay(long delay)
/*     */   {
/*  65 */     this.delay = delay;
/*     */   }
/*     */ 
/*     */   protected abstract void doOnChange();
/*     */ 
/*     */   protected void checkAndConfigure()
/*     */   {
/*     */     boolean fileExists;
/*     */     try {
/*  76 */       fileExists = this.file.exists();
/*     */     } catch (SecurityException e) {
/*  78 */       LogLog.warn("Was not allowed to read check file existance, file:[" + this.filename + "].");
/*     */ 
/*  80 */       this.interrupted = true;
/*  81 */       return;
/*     */     }
/*     */ 
/*  84 */     if (fileExists) {
/*  85 */       long l = this.file.lastModified();
/*  86 */       if (l > this.lastModif) {
/*  87 */         this.lastModif = l;
/*  88 */         doOnChange();
/*  89 */         this.warnedAlready = false;
/*     */       }
/*     */     }
/*  92 */     else if (!this.warnedAlready) {
/*  93 */       LogLog.debug("[" + this.filename + "] does not exist.");
/*  94 */       this.warnedAlready = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/* 101 */     while (!this.interrupted) {
/*     */       try {
/* 103 */         Thread.currentThread(); Thread.sleep(this.delay);
/*     */       }
/*     */       catch (InterruptedException e) {
/*     */       }
/* 107 */       checkAndConfigure();
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.helpers.FileWatchdog
 * JD-Core Version:    0.6.0
 */
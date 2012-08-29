/*     */ package org.apache.log4j;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import org.apache.log4j.helpers.CountingQuietWriter;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ import org.apache.log4j.helpers.OptionConverter;
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ public class RollingFileAppender extends FileAppender
/*     */ {
/*  42 */   protected long maxFileSize = 10485760L;
/*     */ 
/*  47 */   protected int maxBackupIndex = 1;
/*     */ 
/*     */   public RollingFileAppender()
/*     */   {
/*     */   }
/*     */ 
/*     */   public RollingFileAppender(Layout layout, String filename, boolean append)
/*     */     throws IOException
/*     */   {
/*  69 */     super(layout, filename, append);
/*     */   }
/*     */ 
/*     */   public RollingFileAppender(Layout layout, String filename)
/*     */     throws IOException
/*     */   {
/*  80 */     super(layout, filename);
/*     */   }
/*     */ 
/*     */   public int getMaxBackupIndex()
/*     */   {
/*  88 */     return this.maxBackupIndex;
/*     */   }
/*     */ 
/*     */   public long getMaximumFileSize()
/*     */   {
/*  99 */     return this.maxFileSize;
/*     */   }
/*     */ 
/*     */   public void rollOver()
/*     */   {
/* 121 */     if (this.qw != null) {
/* 122 */       LogLog.debug("rolling over count=" + ((CountingQuietWriter)this.qw).getCount());
/*     */     }
/* 124 */     LogLog.debug("maxBackupIndex=" + this.maxBackupIndex);
/*     */ 
/* 127 */     if (this.maxBackupIndex > 0)
/*     */     {
/* 129 */       File file = new File(this.fileName + '.' + this.maxBackupIndex);
/* 130 */       if (file.exists()) {
/* 131 */         file.delete();
/*     */       }
/*     */ 
/* 134 */       for (int i = this.maxBackupIndex - 1; i >= 1; i--) {
/* 135 */         file = new File(this.fileName + "." + i);
/* 136 */         if (file.exists()) {
/* 137 */           target = new File(this.fileName + '.' + (i + 1));
/* 138 */           LogLog.debug("Renaming file " + file + " to " + target);
/* 139 */           file.renameTo(target);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 144 */       File target = new File(this.fileName + "." + 1);
/*     */ 
/* 146 */       closeFile();
/*     */ 
/* 148 */       file = new File(this.fileName);
/* 149 */       LogLog.debug("Renaming file " + file + " to " + target);
/* 150 */       file.renameTo(target);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 156 */       setFile(this.fileName, false, this.bufferedIO, this.bufferSize);
/*     */     }
/*     */     catch (IOException e) {
/* 159 */       LogLog.error("setFile(" + this.fileName + ", false) call failed.", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize)
/*     */     throws IOException
/*     */   {
/* 167 */     super.setFile(fileName, append, this.bufferedIO, this.bufferSize);
/* 168 */     if (append) {
/* 169 */       File f = new File(fileName);
/* 170 */       ((CountingQuietWriter)this.qw).setCount(f.length());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setMaxBackupIndex(int maxBackups)
/*     */   {
/* 186 */     this.maxBackupIndex = maxBackups;
/*     */   }
/*     */ 
/*     */   public void setMaximumFileSize(long maxFileSize)
/*     */   {
/* 203 */     this.maxFileSize = maxFileSize;
/*     */   }
/*     */ 
/*     */   public void setMaxFileSize(String value)
/*     */   {
/* 220 */     this.maxFileSize = OptionConverter.toFileSize(value, this.maxFileSize + 1L);
/*     */   }
/*     */ 
/*     */   protected void setQWForFiles(Writer writer)
/*     */   {
/* 225 */     this.qw = new CountingQuietWriter(writer, this.errorHandler);
/*     */   }
/*     */ 
/*     */   protected void subAppend(LoggingEvent event)
/*     */   {
/* 236 */     super.subAppend(event);
/* 237 */     if ((this.fileName != null) && (((CountingQuietWriter)this.qw).getCount() >= this.maxFileSize))
/*     */     {
/* 239 */       rollOver();
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.RollingFileAppender
 * JD-Core Version:    0.6.0
 */
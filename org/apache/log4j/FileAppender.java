/*     */ package org.apache.log4j;
/*     */ 
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FilterWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ import org.apache.log4j.helpers.QuietWriter;
/*     */ import org.apache.log4j.spi.ErrorHandler;
/*     */ 
/*     */ public class FileAppender extends WriterAppender
/*     */ {
/*  52 */   protected boolean fileAppend = true;
/*     */ 
/*  56 */   protected String fileName = null;
/*     */ 
/*  60 */   protected boolean bufferedIO = false;
/*     */ 
/*  65 */   protected int bufferSize = 8192;
/*     */ 
/*     */   public FileAppender()
/*     */   {
/*     */   }
/*     */ 
/*     */   public FileAppender(Layout layout, String filename, boolean append, boolean bufferedIO, int bufferSize)
/*     */     throws IOException
/*     */   {
/*  91 */     this.layout = layout;
/*  92 */     setFile(filename, append, bufferedIO, bufferSize);
/*     */   }
/*     */ 
/*     */   public FileAppender(Layout layout, String filename, boolean append)
/*     */     throws IOException
/*     */   {
/* 107 */     this.layout = layout;
/* 108 */     setFile(filename, append, false, this.bufferSize);
/*     */   }
/*     */ 
/*     */   public FileAppender(Layout layout, String filename)
/*     */     throws IOException
/*     */   {
/* 119 */     this(layout, filename, true);
/*     */   }
/*     */ 
/*     */   public void setFile(String file)
/*     */   {
/* 134 */     String val = file.trim();
/* 135 */     this.fileName = val;
/*     */   }
/*     */ 
/*     */   public boolean getAppend()
/*     */   {
/* 143 */     return this.fileAppend;
/*     */   }
/*     */ 
/*     */   public String getFile()
/*     */   {
/* 150 */     return this.fileName;
/*     */   }
/*     */ 
/*     */   public void activateOptions()
/*     */   {
/* 161 */     if (this.fileName != null) {
/*     */       try {
/* 163 */         setFile(this.fileName, this.fileAppend, this.bufferedIO, this.bufferSize);
/*     */       }
/*     */       catch (IOException e) {
/* 166 */         this.errorHandler.error("setFile(" + this.fileName + "," + this.fileAppend + ") call failed.", e, 4);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 171 */       LogLog.warn("File option not set for appender [" + this.name + "].");
/* 172 */       LogLog.warn("Are you using FileAppender instead of ConsoleAppender?");
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void closeFile()
/*     */   {
/* 181 */     if (this.qw != null)
/*     */       try {
/* 183 */         this.qw.close();
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 188 */         LogLog.error("Could not close " + this.qw, e);
/*     */       }
/*     */   }
/*     */ 
/*     */   public boolean getBufferedIO()
/*     */   {
/* 202 */     return this.bufferedIO;
/*     */   }
/*     */ 
/*     */   public int getBufferSize()
/*     */   {
/* 211 */     return this.bufferSize;
/*     */   }
/*     */ 
/*     */   public void setAppend(boolean flag)
/*     */   {
/* 228 */     this.fileAppend = flag;
/*     */   }
/*     */ 
/*     */   public void setBufferedIO(boolean bufferedIO)
/*     */   {
/* 243 */     this.bufferedIO = bufferedIO;
/* 244 */     if (bufferedIO)
/* 245 */       this.immediateFlush = false;
/*     */   }
/*     */ 
/*     */   public void setBufferSize(int bufferSize)
/*     */   {
/* 255 */     this.bufferSize = bufferSize;
/*     */   }
/*     */ 
/*     */   public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize)
/*     */     throws IOException
/*     */   {
/* 276 */     LogLog.debug("setFile called: " + fileName + ", " + append);
/*     */ 
/* 279 */     if (bufferedIO) {
/* 280 */       setImmediateFlush(false);
/*     */     }
/*     */ 
/* 283 */     reset();
/* 284 */     FileOutputStream ostream = null;
/*     */     try
/*     */     {
/* 289 */       ostream = new FileOutputStream(fileName, append);
/*     */     }
/*     */     catch (FileNotFoundException ex)
/*     */     {
/* 296 */       String parentName = new File(fileName).getParent();
/* 297 */       if (parentName != null) {
/* 298 */         File parentDir = new File(parentName);
/* 299 */         if ((!parentDir.exists()) && (parentDir.mkdirs()))
/* 300 */           ostream = new FileOutputStream(fileName, append);
/*     */         else
/* 302 */           throw ex;
/*     */       }
/*     */       else {
/* 305 */         throw ex;
/*     */       }
/*     */     }
/* 308 */     Writer fw = createWriter(ostream);
/* 309 */     if (bufferedIO) {
/* 310 */       fw = new BufferedWriter(fw, bufferSize);
/*     */     }
/* 312 */     setQWForFiles(fw);
/* 313 */     this.fileName = fileName;
/* 314 */     this.fileAppend = append;
/* 315 */     this.bufferedIO = bufferedIO;
/* 316 */     this.bufferSize = bufferSize;
/* 317 */     writeHeader();
/* 318 */     LogLog.debug("setFile ended");
/*     */   }
/*     */ 
/*     */   protected void setQWForFiles(Writer writer)
/*     */   {
/* 329 */     this.qw = new QuietWriter(writer, this.errorHandler);
/*     */   }
/*     */ 
/*     */   protected void reset()
/*     */   {
/* 338 */     closeFile();
/* 339 */     this.fileName = null;
/* 340 */     super.reset();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.FileAppender
 * JD-Core Version:    0.6.0
 */
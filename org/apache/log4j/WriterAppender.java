/*     */ package org.apache.log4j;
/*     */ 
/*     */ import java.io.FilterWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ import org.apache.log4j.helpers.QuietWriter;
/*     */ import org.apache.log4j.spi.ErrorHandler;
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ public class WriterAppender extends AppenderSkeleton
/*     */ {
/*  54 */   protected boolean immediateFlush = true;
/*     */   protected String encoding;
/*     */   protected QuietWriter qw;
/*     */ 
/*     */   public WriterAppender()
/*     */   {
/*     */   }
/*     */ 
/*     */   public WriterAppender(Layout layout, OutputStream os)
/*     */   {
/*  82 */     this(layout, new OutputStreamWriter(os));
/*     */   }
/*     */ 
/*     */   public WriterAppender(Layout layout, Writer writer)
/*     */   {
/*  93 */     this.layout = layout;
/*  94 */     setWriter(writer);
/*     */   }
/*     */ 
/*     */   public void setImmediateFlush(boolean value)
/*     */   {
/* 113 */     this.immediateFlush = value;
/*     */   }
/*     */ 
/*     */   public boolean getImmediateFlush()
/*     */   {
/* 121 */     return this.immediateFlush;
/*     */   }
/*     */ 
/*     */   public void activateOptions()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void append(LoggingEvent event)
/*     */   {
/* 156 */     if (!checkEntryConditions()) {
/* 157 */       return;
/*     */     }
/* 159 */     subAppend(event);
/*     */   }
/*     */ 
/*     */   protected boolean checkEntryConditions()
/*     */   {
/* 170 */     if (this.closed) {
/* 171 */       LogLog.warn("Not allowed to write to a closed appender.");
/* 172 */       return false;
/*     */     }
/*     */ 
/* 175 */     if (this.qw == null) {
/* 176 */       this.errorHandler.error("No output stream or file set for the appender named [" + this.name + "].");
/*     */ 
/* 178 */       return false;
/*     */     }
/*     */ 
/* 181 */     if (this.layout == null) {
/* 182 */       this.errorHandler.error("No layout set for the appender named [" + this.name + "].");
/* 183 */       return false;
/*     */     }
/* 185 */     return true;
/*     */   }
/*     */ 
/*     */   public synchronized void close()
/*     */   {
/* 200 */     if (this.closed)
/* 201 */       return;
/* 202 */     this.closed = true;
/* 203 */     writeFooter();
/* 204 */     reset();
/*     */   }
/*     */ 
/*     */   protected void closeWriter()
/*     */   {
/* 211 */     if (this.qw != null)
/*     */       try {
/* 213 */         this.qw.close();
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 217 */         LogLog.error("Could not close " + this.qw, e);
/*     */       }
/*     */   }
/*     */ 
/*     */   protected OutputStreamWriter createWriter(OutputStream os)
/*     */   {
/* 230 */     OutputStreamWriter retval = null;
/*     */ 
/* 232 */     String enc = getEncoding();
/* 233 */     if (enc != null) {
/*     */       try {
/* 235 */         retval = new OutputStreamWriter(os, enc);
/*     */       } catch (IOException e) {
/* 237 */         LogLog.warn("Error initializing output writer.");
/* 238 */         LogLog.warn("Unsupported encoding?");
/*     */       }
/*     */     }
/* 241 */     if (retval == null) {
/* 242 */       retval = new OutputStreamWriter(os);
/*     */     }
/* 244 */     return retval;
/*     */   }
/*     */ 
/*     */   public String getEncoding() {
/* 248 */     return this.encoding;
/*     */   }
/*     */ 
/*     */   public void setEncoding(String value) {
/* 252 */     this.encoding = value;
/*     */   }
/*     */ 
/*     */   public synchronized void setErrorHandler(ErrorHandler eh)
/*     */   {
/* 262 */     if (eh == null) {
/* 263 */       LogLog.warn("You have tried to set a null error-handler.");
/*     */     } else {
/* 265 */       this.errorHandler = eh;
/* 266 */       if (this.qw != null)
/* 267 */         this.qw.setErrorHandler(eh);
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void setWriter(Writer writer)
/*     */   {
/* 285 */     reset();
/* 286 */     this.qw = new QuietWriter(writer, this.errorHandler);
/*     */ 
/* 288 */     writeHeader();
/*     */   }
/*     */ 
/*     */   protected void subAppend(LoggingEvent event)
/*     */   {
/* 301 */     this.qw.write(this.layout.format(event));
/*     */ 
/* 303 */     if (this.layout.ignoresThrowable()) {
/* 304 */       String[] s = event.getThrowableStrRep();
/* 305 */       if (s != null) {
/* 306 */         int len = s.length;
/* 307 */         for (int i = 0; i < len; i++) {
/* 308 */           this.qw.write(s[i]);
/* 309 */           this.qw.write(Layout.LINE_SEP);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 314 */     if (this.immediateFlush)
/* 315 */       this.qw.flush();
/*     */   }
/*     */ 
/*     */   public boolean requiresLayout()
/*     */   {
/* 327 */     return true;
/*     */   }
/*     */ 
/*     */   protected void reset()
/*     */   {
/* 337 */     closeWriter();
/* 338 */     this.qw = null;
/*     */   }
/*     */ 
/*     */   protected void writeFooter()
/*     */   {
/* 348 */     if (this.layout != null) {
/* 349 */       String f = this.layout.getFooter();
/* 350 */       if ((f != null) && (this.qw != null)) {
/* 351 */         this.qw.write(f);
/* 352 */         this.qw.flush();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void writeHeader()
/*     */   {
/* 362 */     if (this.layout != null) {
/* 363 */       String h = this.layout.getHeader();
/* 364 */       if ((h != null) && (this.qw != null))
/* 365 */         this.qw.write(h);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.WriterAppender
 * JD-Core Version:    0.6.0
 */
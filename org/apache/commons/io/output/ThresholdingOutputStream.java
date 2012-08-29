/*     */ package org.apache.commons.io.output;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public abstract class ThresholdingOutputStream extends OutputStream
/*     */ {
/*     */   private int threshold;
/*     */   private long written;
/*     */   private boolean thresholdExceeded;
/*     */ 
/*     */   public ThresholdingOutputStream(int threshold)
/*     */   {
/*  77 */     this.threshold = threshold;
/*     */   }
/*     */ 
/*     */   public void write(int b)
/*     */     throws IOException
/*     */   {
/*  93 */     checkThreshold(1);
/*  94 */     getStream().write(b);
/*  95 */     this.written += 1L;
/*     */   }
/*     */ 
/*     */   public void write(byte[] b)
/*     */     throws IOException
/*     */   {
/* 109 */     checkThreshold(b.length);
/* 110 */     getStream().write(b);
/* 111 */     this.written += b.length;
/*     */   }
/*     */ 
/*     */   public void write(byte[] b, int off, int len)
/*     */     throws IOException
/*     */   {
/* 127 */     checkThreshold(len);
/* 128 */     getStream().write(b, off, len);
/* 129 */     this.written += len;
/*     */   }
/*     */ 
/*     */   public void flush()
/*     */     throws IOException
/*     */   {
/* 141 */     getStream().flush();
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 155 */       flush();
/*     */     }
/*     */     catch (IOException ignored)
/*     */     {
/*     */     }
/*     */ 
/* 161 */     getStream().close();
/*     */   }
/*     */ 
/*     */   public int getThreshold()
/*     */   {
/* 175 */     return this.threshold;
/*     */   }
/*     */ 
/*     */   public long getByteCount()
/*     */   {
/* 186 */     return this.written;
/*     */   }
/*     */ 
/*     */   public boolean isThresholdExceeded()
/*     */   {
/* 199 */     return this.written > this.threshold;
/*     */   }
/*     */ 
/*     */   protected void checkThreshold(int count)
/*     */     throws IOException
/*     */   {
/* 218 */     if ((!this.thresholdExceeded) && (this.written + count > this.threshold))
/*     */     {
/* 220 */       thresholdReached();
/* 221 */       this.thresholdExceeded = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected abstract OutputStream getStream()
/*     */     throws IOException;
/*     */ 
/*     */   protected abstract void thresholdReached()
/*     */     throws IOException;
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.output.ThresholdingOutputStream
 * JD-Core Version:    0.6.0
 */
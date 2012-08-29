/*     */ package org.apache.commons.io.output;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ public class DeferredFileOutputStream extends ThresholdingOutputStream
/*     */ {
/*     */   private ByteArrayOutputStream memoryOutputStream;
/*     */   private OutputStream currentOutputStream;
/*     */   private File outputFile;
/*  74 */   private boolean closed = false;
/*     */ 
/*     */   public DeferredFileOutputStream(int threshold, File outputFile)
/*     */   {
/*  88 */     super(threshold);
/*  89 */     this.outputFile = outputFile;
/*     */ 
/*  91 */     this.memoryOutputStream = new ByteArrayOutputStream();
/*  92 */     this.currentOutputStream = this.memoryOutputStream;
/*     */   }
/*     */ 
/*     */   protected OutputStream getStream()
/*     */     throws IOException
/*     */   {
/* 109 */     return this.currentOutputStream;
/*     */   }
/*     */ 
/*     */   protected void thresholdReached()
/*     */     throws IOException
/*     */   {
/* 123 */     FileOutputStream fos = new FileOutputStream(this.outputFile);
/* 124 */     this.memoryOutputStream.writeTo(fos);
/* 125 */     this.currentOutputStream = fos;
/* 126 */     this.memoryOutputStream = null;
/*     */   }
/*     */ 
/*     */   public boolean isInMemory()
/*     */   {
/* 142 */     return !isThresholdExceeded();
/*     */   }
/*     */ 
/*     */   public byte[] getData()
/*     */   {
/* 156 */     if (this.memoryOutputStream != null)
/*     */     {
/* 158 */       return this.memoryOutputStream.toByteArray();
/*     */     }
/* 160 */     return null;
/*     */   }
/*     */ 
/*     */   public File getFile()
/*     */   {
/* 173 */     return this.outputFile;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 184 */     super.close();
/* 185 */     this.closed = true;
/*     */   }
/*     */ 
/*     */   public void writeTo(OutputStream out)
/*     */     throws IOException
/*     */   {
/* 201 */     if (!this.closed)
/*     */     {
/* 203 */       throw new IOException("Stream not closed");
/*     */     }
/*     */ 
/* 206 */     if (isInMemory())
/*     */     {
/* 208 */       this.memoryOutputStream.writeTo(out);
/*     */     }
/*     */     else
/*     */     {
/* 212 */       FileInputStream fis = new FileInputStream(this.outputFile);
/*     */       try {
/* 214 */         IOUtils.copy(fis, out);
/*     */       } finally {
/* 216 */         IOUtils.closeQuietly(fis);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.output.DeferredFileOutputStream
 * JD-Core Version:    0.6.0
 */
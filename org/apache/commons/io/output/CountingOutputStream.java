/*     */ package org.apache.commons.io.output;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public class CountingOutputStream extends ProxyOutputStream
/*     */ {
/*     */   private long count;
/*     */ 
/*     */   public CountingOutputStream(OutputStream out)
/*     */   {
/*  42 */     super(out);
/*     */   }
/*     */ 
/*     */   public void write(byte[] b)
/*     */     throws IOException
/*     */   {
/*  55 */     this.count += b.length;
/*  56 */     super.write(b);
/*     */   }
/*     */ 
/*     */   public void write(byte[] b, int off, int len)
/*     */     throws IOException
/*     */   {
/*  70 */     this.count += len;
/*  71 */     super.write(b, off, len);
/*     */   }
/*     */ 
/*     */   public void write(int b)
/*     */     throws IOException
/*     */   {
/*  83 */     this.count += 1L;
/*  84 */     super.write(b);
/*     */   }
/*     */ 
/*     */   public synchronized int getCount()
/*     */   {
/*  99 */     long result = getByteCount();
/* 100 */     if (result > 2147483647L) {
/* 101 */       throw new ArithmeticException("The byte count " + result + " is too large to be converted to an int");
/*     */     }
/* 103 */     return (int)result;
/*     */   }
/*     */ 
/*     */   public synchronized int resetCount()
/*     */   {
/* 117 */     long result = resetByteCount();
/* 118 */     if (result > 2147483647L) {
/* 119 */       throw new ArithmeticException("The byte count " + result + " is too large to be converted to an int");
/*     */     }
/* 121 */     return (int)result;
/*     */   }
/*     */ 
/*     */   public synchronized long getByteCount()
/*     */   {
/* 135 */     return this.count;
/*     */   }
/*     */ 
/*     */   public synchronized long resetByteCount()
/*     */   {
/* 149 */     long tmp = this.count;
/* 150 */     this.count = 0L;
/* 151 */     return tmp;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.output.CountingOutputStream
 * JD-Core Version:    0.6.0
 */
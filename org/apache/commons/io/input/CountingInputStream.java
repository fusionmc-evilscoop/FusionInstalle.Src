/*     */ package org.apache.commons.io.input;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public class CountingInputStream extends ProxyInputStream
/*     */ {
/*     */   private long count;
/*     */ 
/*     */   public CountingInputStream(InputStream in)
/*     */   {
/*  43 */     super(in);
/*     */   }
/*     */ 
/*     */   public int read(byte[] b)
/*     */     throws IOException
/*     */   {
/*  57 */     int found = super.read(b);
/*  58 */     this.count += (found >= 0 ? found : 0L);
/*  59 */     return found;
/*     */   }
/*     */ 
/*     */   public int read(byte[] b, int off, int len)
/*     */     throws IOException
/*     */   {
/*  74 */     int found = super.read(b, off, len);
/*  75 */     this.count += (found >= 0 ? found : 0L);
/*  76 */     return found;
/*     */   }
/*     */ 
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/*  88 */     int found = super.read();
/*  89 */     this.count += (found >= 0 ? 1L : 0L);
/*  90 */     return found;
/*     */   }
/*     */ 
/*     */   public long skip(long length)
/*     */     throws IOException
/*     */   {
/* 103 */     long skip = super.skip(length);
/* 104 */     this.count += skip;
/* 105 */     return skip;
/*     */   }
/*     */ 
/*     */   public synchronized int getCount()
/*     */   {
/* 120 */     long result = getByteCount();
/* 121 */     if (result > 2147483647L) {
/* 122 */       throw new ArithmeticException("The byte count " + result + " is too large to be converted to an int");
/*     */     }
/* 124 */     return (int)result;
/*     */   }
/*     */ 
/*     */   public synchronized int resetCount()
/*     */   {
/* 138 */     long result = resetByteCount();
/* 139 */     if (result > 2147483647L) {
/* 140 */       throw new ArithmeticException("The byte count " + result + " is too large to be converted to an int");
/*     */     }
/* 142 */     return (int)result;
/*     */   }
/*     */ 
/*     */   public synchronized long getByteCount()
/*     */   {
/* 156 */     return this.count;
/*     */   }
/*     */ 
/*     */   public synchronized long resetByteCount()
/*     */   {
/* 170 */     long tmp = this.count;
/* 171 */     this.count = 0L;
/* 172 */     return tmp;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.input.CountingInputStream
 * JD-Core Version:    0.6.0
 */
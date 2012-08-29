/*     */ package org.apache.commons.io.input;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public class NullInputStream extends InputStream
/*     */ {
/*     */   private long size;
/*     */   private long position;
/*  67 */   private long mark = -1L;
/*     */   private long readlimit;
/*     */   private boolean eof;
/*     */   private boolean throwEofException;
/*     */   private boolean markSupported;
/*     */ 
/*     */   public NullInputStream(long size)
/*     */   {
/*  80 */     this(size, true, false);
/*     */   }
/*     */ 
/*     */   public NullInputStream(long size, boolean markSupported, boolean throwEofException)
/*     */   {
/*  95 */     this.size = size;
/*  96 */     this.markSupported = markSupported;
/*  97 */     this.throwEofException = throwEofException;
/*     */   }
/*     */ 
/*     */   public long getPosition()
/*     */   {
/* 106 */     return this.position;
/*     */   }
/*     */ 
/*     */   public long getSize()
/*     */   {
/* 115 */     return this.size;
/*     */   }
/*     */ 
/*     */   public int available()
/*     */   {
/* 124 */     long avail = this.size - this.position;
/* 125 */     if (avail <= 0L)
/* 126 */       return 0;
/* 127 */     if (avail > 2147483647L) {
/* 128 */       return 2147483647;
/*     */     }
/* 130 */     return (int)avail;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 141 */     this.eof = false;
/* 142 */     this.position = 0L;
/* 143 */     this.mark = -1L;
/*     */   }
/*     */ 
/*     */   public synchronized void mark(int readlimit)
/*     */   {
/* 154 */     if (!this.markSupported) {
/* 155 */       throw new UnsupportedOperationException("Mark not supported");
/*     */     }
/* 157 */     this.mark = this.position;
/* 158 */     this.readlimit = readlimit;
/*     */   }
/*     */ 
/*     */   public boolean markSupported()
/*     */   {
/* 167 */     return this.markSupported;
/*     */   }
/*     */ 
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/* 181 */     if (this.eof) {
/* 182 */       throw new IOException("Read after end of file");
/*     */     }
/* 184 */     if (this.position == this.size) {
/* 185 */       return doEndOfFile();
/*     */     }
/* 187 */     this.position += 1L;
/* 188 */     return processByte();
/*     */   }
/*     */ 
/*     */   public int read(byte[] bytes)
/*     */     throws IOException
/*     */   {
/* 203 */     return read(bytes, 0, bytes.length);
/*     */   }
/*     */ 
/*     */   public int read(byte[] bytes, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 220 */     if (this.eof) {
/* 221 */       throw new IOException("Read after end of file");
/*     */     }
/* 223 */     if (this.position == this.size) {
/* 224 */       return doEndOfFile();
/*     */     }
/* 226 */     this.position += length;
/* 227 */     int returnLength = length;
/* 228 */     if (this.position > this.size) {
/* 229 */       returnLength = length - (int)(this.position - this.size);
/* 230 */       this.position = this.size;
/*     */     }
/* 232 */     processBytes(bytes, offset, returnLength);
/* 233 */     return returnLength;
/*     */   }
/*     */ 
/*     */   public synchronized void reset()
/*     */     throws IOException
/*     */   {
/* 245 */     if (!this.markSupported) {
/* 246 */       throw new UnsupportedOperationException("Mark not supported");
/*     */     }
/* 248 */     if (this.mark < 0L) {
/* 249 */       throw new IOException("No position has been marked");
/*     */     }
/* 251 */     if (this.position > this.mark + this.readlimit) {
/* 252 */       throw new IOException("Marked position [" + this.mark + "] is no longer valid - passed the read limit [" + this.readlimit + "]");
/*     */     }
/*     */ 
/* 256 */     this.position = this.mark;
/* 257 */     this.eof = false;
/*     */   }
/*     */ 
/*     */   public long skip(long numberOfBytes)
/*     */     throws IOException
/*     */   {
/* 272 */     if (this.eof) {
/* 273 */       throw new IOException("Skip after end of file");
/*     */     }
/* 275 */     if (this.position == this.size) {
/* 276 */       return doEndOfFile();
/*     */     }
/* 278 */     this.position += numberOfBytes;
/* 279 */     long returnLength = numberOfBytes;
/* 280 */     if (this.position > this.size) {
/* 281 */       returnLength = numberOfBytes - (this.position - this.size);
/* 282 */       this.position = this.size;
/*     */     }
/* 284 */     return returnLength;
/*     */   }
/*     */ 
/*     */   protected int processByte()
/*     */   {
/* 296 */     return 0;
/*     */   }
/*     */ 
/*     */   protected void processBytes(byte[] bytes, int offset, int length)
/*     */   {
/*     */   }
/*     */ 
/*     */   private int doEndOfFile()
/*     */     throws EOFException
/*     */   {
/* 322 */     this.eof = true;
/* 323 */     if (this.throwEofException) {
/* 324 */       throw new EOFException();
/*     */     }
/* 326 */     return -1;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.input.NullInputStream
 * JD-Core Version:    0.6.0
 */
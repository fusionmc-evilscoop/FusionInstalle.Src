/*     */ package org.apache.commons.io.input;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ 
/*     */ public class NullReader extends Reader
/*     */ {
/*     */   private long size;
/*     */   private long position;
/*  67 */   private long mark = -1L;
/*     */   private long readlimit;
/*     */   private boolean eof;
/*     */   private boolean throwEofException;
/*     */   private boolean markSupported;
/*     */ 
/*     */   public NullReader(long size)
/*     */   {
/*  80 */     this(size, true, false);
/*     */   }
/*     */ 
/*     */   public NullReader(long size, boolean markSupported, boolean throwEofException)
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
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 125 */     this.eof = false;
/* 126 */     this.position = 0L;
/* 127 */     this.mark = -1L;
/*     */   }
/*     */ 
/*     */   public synchronized void mark(int readlimit)
/*     */   {
/* 138 */     if (!this.markSupported) {
/* 139 */       throw new UnsupportedOperationException("Mark not supported");
/*     */     }
/* 141 */     this.mark = this.position;
/* 142 */     this.readlimit = readlimit;
/*     */   }
/*     */ 
/*     */   public boolean markSupported()
/*     */   {
/* 151 */     return this.markSupported;
/*     */   }
/*     */ 
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/* 165 */     if (this.eof) {
/* 166 */       throw new IOException("Read after end of file");
/*     */     }
/* 168 */     if (this.position == this.size) {
/* 169 */       return doEndOfFile();
/*     */     }
/* 171 */     this.position += 1L;
/* 172 */     return processChar();
/*     */   }
/*     */ 
/*     */   public int read(char[] chars)
/*     */     throws IOException
/*     */   {
/* 187 */     return read(chars, 0, chars.length);
/*     */   }
/*     */ 
/*     */   public int read(char[] chars, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 204 */     if (this.eof) {
/* 205 */       throw new IOException("Read after end of file");
/*     */     }
/* 207 */     if (this.position == this.size) {
/* 208 */       return doEndOfFile();
/*     */     }
/* 210 */     this.position += length;
/* 211 */     int returnLength = length;
/* 212 */     if (this.position > this.size) {
/* 213 */       returnLength = length - (int)(this.position - this.size);
/* 214 */       this.position = this.size;
/*     */     }
/* 216 */     processChars(chars, offset, returnLength);
/* 217 */     return returnLength;
/*     */   }
/*     */ 
/*     */   public synchronized void reset()
/*     */     throws IOException
/*     */   {
/* 229 */     if (!this.markSupported) {
/* 230 */       throw new UnsupportedOperationException("Mark not supported");
/*     */     }
/* 232 */     if (this.mark < 0L) {
/* 233 */       throw new IOException("No position has been marked");
/*     */     }
/* 235 */     if (this.position > this.mark + this.readlimit) {
/* 236 */       throw new IOException("Marked position [" + this.mark + "] is no longer valid - passed the read limit [" + this.readlimit + "]");
/*     */     }
/*     */ 
/* 240 */     this.position = this.mark;
/* 241 */     this.eof = false;
/*     */   }
/*     */ 
/*     */   public long skip(long numberOfChars)
/*     */     throws IOException
/*     */   {
/* 256 */     if (this.eof) {
/* 257 */       throw new IOException("Skip after end of file");
/*     */     }
/* 259 */     if (this.position == this.size) {
/* 260 */       return doEndOfFile();
/*     */     }
/* 262 */     this.position += numberOfChars;
/* 263 */     long returnLength = numberOfChars;
/* 264 */     if (this.position > this.size) {
/* 265 */       returnLength = numberOfChars - (this.position - this.size);
/* 266 */       this.position = this.size;
/*     */     }
/* 268 */     return returnLength;
/*     */   }
/*     */ 
/*     */   protected int processChar()
/*     */   {
/* 280 */     return 0;
/*     */   }
/*     */ 
/*     */   protected void processChars(char[] chars, int offset, int length)
/*     */   {
/*     */   }
/*     */ 
/*     */   private int doEndOfFile()
/*     */     throws EOFException
/*     */   {
/* 306 */     this.eof = true;
/* 307 */     if (this.throwEofException) {
/* 308 */       throw new EOFException();
/*     */     }
/* 310 */     return -1;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.input.NullReader
 * JD-Core Version:    0.6.0
 */
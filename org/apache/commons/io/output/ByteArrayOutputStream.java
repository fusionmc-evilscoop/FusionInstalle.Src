/*     */ package org.apache.commons.io.output;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ByteArrayOutputStream extends OutputStream
/*     */ {
/*  53 */   private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
/*     */ 
/*  56 */   private List buffers = new ArrayList();
/*     */   private int currentBufferIndex;
/*     */   private int filledBufferSum;
/*     */   private byte[] currentBuffer;
/*     */   private int count;
/*     */ 
/*     */   public ByteArrayOutputStream()
/*     */   {
/*  71 */     this(1024);
/*     */   }
/*     */ 
/*     */   public ByteArrayOutputStream(int size)
/*     */   {
/*  82 */     if (size < 0) {
/*  83 */       throw new IllegalArgumentException("Negative initial size: " + size);
/*     */     }
/*     */ 
/*  86 */     needNewBuffer(size);
/*     */   }
/*     */ 
/*     */   private byte[] getBuffer(int index)
/*     */   {
/*  97 */     return (byte[])(byte[])this.buffers.get(index);
/*     */   }
/*     */ 
/*     */   private void needNewBuffer(int newcount)
/*     */   {
/* 107 */     if (this.currentBufferIndex < this.buffers.size() - 1)
/*     */     {
/* 109 */       this.filledBufferSum += this.currentBuffer.length;
/*     */ 
/* 111 */       this.currentBufferIndex += 1;
/* 112 */       this.currentBuffer = getBuffer(this.currentBufferIndex);
/*     */     }
/*     */     else
/*     */     {
/*     */       int newBufferSize;
/* 116 */       if (this.currentBuffer == null) {
/* 117 */         int newBufferSize = newcount;
/* 118 */         this.filledBufferSum = 0;
/*     */       } else {
/* 120 */         newBufferSize = Math.max(this.currentBuffer.length << 1, newcount - this.filledBufferSum);
/*     */ 
/* 123 */         this.filledBufferSum += this.currentBuffer.length;
/*     */       }
/*     */ 
/* 126 */       this.currentBufferIndex += 1;
/* 127 */       this.currentBuffer = new byte[newBufferSize];
/* 128 */       this.buffers.add(this.currentBuffer);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(byte[] b, int off, int len)
/*     */   {
/* 136 */     if ((off < 0) || (off > b.length) || (len < 0) || (off + len > b.length) || (off + len < 0))
/*     */     {
/* 141 */       throw new IndexOutOfBoundsException();
/* 142 */     }if (len == 0) {
/* 143 */       return;
/*     */     }
/* 145 */     synchronized (this) {
/* 146 */       int newcount = this.count + len;
/* 147 */       int remaining = len;
/* 148 */       int inBufferPos = this.count - this.filledBufferSum;
/* 149 */       while (remaining > 0) {
/* 150 */         int part = Math.min(remaining, this.currentBuffer.length - inBufferPos);
/* 151 */         System.arraycopy(b, off + len - remaining, this.currentBuffer, inBufferPos, part);
/* 152 */         remaining -= part;
/* 153 */         if (remaining > 0) {
/* 154 */           needNewBuffer(newcount);
/* 155 */           inBufferPos = 0;
/*     */         }
/*     */       }
/* 158 */       this.count = newcount;
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void write(int b)
/*     */   {
/* 166 */     int inBufferPos = this.count - this.filledBufferSum;
/* 167 */     if (inBufferPos == this.currentBuffer.length) {
/* 168 */       needNewBuffer(this.count + 1);
/* 169 */       inBufferPos = 0;
/*     */     }
/* 171 */     this.currentBuffer[inBufferPos] = (byte)b;
/* 172 */     this.count += 1;
/*     */   }
/*     */ 
/*     */   public synchronized int size()
/*     */   {
/* 179 */     return this.count;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*     */   }
/*     */ 
/*     */   public synchronized void reset()
/*     */   {
/* 198 */     this.count = 0;
/* 199 */     this.filledBufferSum = 0;
/* 200 */     this.currentBufferIndex = 0;
/* 201 */     this.currentBuffer = getBuffer(this.currentBufferIndex);
/*     */   }
/*     */ 
/*     */   public synchronized void writeTo(OutputStream out)
/*     */     throws IOException
/*     */   {
/* 213 */     int remaining = this.count;
/* 214 */     for (int i = 0; i < this.buffers.size(); i++) {
/* 215 */       byte[] buf = getBuffer(i);
/* 216 */       int c = Math.min(buf.length, remaining);
/* 217 */       out.write(buf, 0, c);
/* 218 */       remaining -= c;
/* 219 */       if (remaining == 0)
/*     */         break;
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized byte[] toByteArray()
/*     */   {
/* 233 */     int remaining = this.count;
/* 234 */     if (remaining == 0) {
/* 235 */       return EMPTY_BYTE_ARRAY;
/*     */     }
/* 237 */     byte[] newbuf = new byte[remaining];
/* 238 */     int pos = 0;
/* 239 */     for (int i = 0; i < this.buffers.size(); i++) {
/* 240 */       byte[] buf = getBuffer(i);
/* 241 */       int c = Math.min(buf.length, remaining);
/* 242 */       System.arraycopy(buf, 0, newbuf, pos, c);
/* 243 */       pos += c;
/* 244 */       remaining -= c;
/* 245 */       if (remaining == 0) {
/*     */         break;
/*     */       }
/*     */     }
/* 249 */     return newbuf;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 258 */     return new String(toByteArray());
/*     */   }
/*     */ 
/*     */   public String toString(String enc)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 271 */     return new String(toByteArray(), enc);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.output.ByteArrayOutputStream
 * JD-Core Version:    0.6.0
 */
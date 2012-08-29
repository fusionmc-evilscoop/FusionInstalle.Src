/*     */ package org.apache.log4j.helpers;
/*     */ 
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ public class BoundedFIFO
/*     */ {
/*     */   LoggingEvent[] buf;
/*  33 */   int numElements = 0;
/*  34 */   int first = 0;
/*  35 */   int next = 0;
/*     */   int maxSize;
/*     */ 
/*     */   public BoundedFIFO(int maxSize)
/*     */   {
/*  43 */     if (maxSize < 1) {
/*  44 */       throw new IllegalArgumentException("The maxSize argument (" + maxSize + ") is not a positive integer.");
/*     */     }
/*     */ 
/*  47 */     this.maxSize = maxSize;
/*  48 */     this.buf = new LoggingEvent[maxSize];
/*     */   }
/*     */ 
/*     */   public LoggingEvent get()
/*     */   {
/*  56 */     if (this.numElements == 0) {
/*  57 */       return null;
/*     */     }
/*  59 */     LoggingEvent r = this.buf[this.first];
/*  60 */     this.buf[this.first] = null;
/*     */ 
/*  62 */     if (++this.first == this.maxSize) {
/*  63 */       this.first = 0;
/*     */     }
/*  65 */     this.numElements -= 1;
/*  66 */     return r;
/*     */   }
/*     */ 
/*     */   public void put(LoggingEvent o)
/*     */   {
/*  75 */     if (this.numElements != this.maxSize) {
/*  76 */       this.buf[this.next] = o;
/*  77 */       if (++this.next == this.maxSize) {
/*  78 */         this.next = 0;
/*     */       }
/*  80 */       this.numElements += 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getMaxSize()
/*     */   {
/*  89 */     return this.maxSize;
/*     */   }
/*     */ 
/*     */   public boolean isFull()
/*     */   {
/*  97 */     return this.numElements == this.maxSize;
/*     */   }
/*     */ 
/*     */   public int length()
/*     */   {
/* 107 */     return this.numElements;
/*     */   }
/*     */ 
/*     */   int min(int a, int b)
/*     */   {
/* 112 */     return a < b ? a : b;
/*     */   }
/*     */ 
/*     */   public synchronized void resize(int newSize)
/*     */   {
/* 125 */     if (newSize == this.maxSize) {
/* 126 */       return;
/*     */     }
/*     */ 
/* 129 */     LoggingEvent[] tmp = new LoggingEvent[newSize];
/*     */ 
/* 132 */     int len1 = this.maxSize - this.first;
/*     */ 
/* 135 */     len1 = min(len1, newSize);
/*     */ 
/* 139 */     len1 = min(len1, this.numElements);
/*     */ 
/* 142 */     System.arraycopy(this.buf, this.first, tmp, 0, len1);
/*     */ 
/* 145 */     int len2 = 0;
/* 146 */     if ((len1 < this.numElements) && (len1 < newSize)) {
/* 147 */       len2 = this.numElements - len1;
/* 148 */       len2 = min(len2, newSize - len1);
/* 149 */       System.arraycopy(this.buf, 0, tmp, len1, len2);
/*     */     }
/*     */ 
/* 152 */     this.buf = tmp;
/* 153 */     this.maxSize = newSize;
/* 154 */     this.first = 0;
/* 155 */     this.numElements = (len1 + len2);
/* 156 */     this.next = this.numElements;
/* 157 */     if (this.next == this.maxSize)
/* 158 */       this.next = 0;
/*     */   }
/*     */ 
/*     */   public boolean wasEmpty()
/*     */   {
/* 168 */     return this.numElements == 1;
/*     */   }
/*     */ 
/*     */   public boolean wasFull()
/*     */   {
/* 177 */     return this.numElements + 1 == this.maxSize;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.helpers.BoundedFIFO
 * JD-Core Version:    0.6.0
 */
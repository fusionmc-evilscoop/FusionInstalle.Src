/*     */ package org.apache.log4j.helpers;
/*     */ 
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ public class CyclicBuffer
/*     */ {
/*     */   LoggingEvent[] ea;
/*     */   int first;
/*     */   int last;
/*     */   int numElems;
/*     */   int maxSize;
/*     */ 
/*     */   public CyclicBuffer(int maxSize)
/*     */     throws IllegalArgumentException
/*     */   {
/*  49 */     if (maxSize < 1) {
/*  50 */       throw new IllegalArgumentException("The maxSize argument (" + maxSize + ") is not a positive integer.");
/*     */     }
/*     */ 
/*  53 */     this.maxSize = maxSize;
/*  54 */     this.ea = new LoggingEvent[maxSize];
/*  55 */     this.first = 0;
/*  56 */     this.last = 0;
/*  57 */     this.numElems = 0;
/*     */   }
/*     */ 
/*     */   public void add(LoggingEvent event)
/*     */   {
/*  66 */     this.ea[this.last] = event;
/*  67 */     if (++this.last == this.maxSize) {
/*  68 */       this.last = 0;
/*     */     }
/*  70 */     if (this.numElems < this.maxSize)
/*  71 */       this.numElems += 1;
/*  72 */     else if (++this.first == this.maxSize)
/*  73 */       this.first = 0;
/*     */   }
/*     */ 
/*     */   public LoggingEvent get(int i)
/*     */   {
/*  86 */     if ((i < 0) || (i >= this.numElems)) {
/*  87 */       return null;
/*     */     }
/*  89 */     return this.ea[((this.first + i) % this.maxSize)];
/*     */   }
/*     */ 
/*     */   public int getMaxSize()
/*     */   {
/*  94 */     return this.maxSize;
/*     */   }
/*     */ 
/*     */   public LoggingEvent get()
/*     */   {
/* 103 */     LoggingEvent r = null;
/* 104 */     if (this.numElems > 0) {
/* 105 */       this.numElems -= 1;
/* 106 */       r = this.ea[this.first];
/* 107 */       this.ea[this.first] = null;
/* 108 */       if (++this.first == this.maxSize)
/* 109 */         this.first = 0;
/*     */     }
/* 111 */     return r;
/*     */   }
/*     */ 
/*     */   public int length()
/*     */   {
/* 121 */     return this.numElems;
/*     */   }
/*     */ 
/*     */   public void resize(int newSize)
/*     */   {
/* 131 */     if (newSize < 0) {
/* 132 */       throw new IllegalArgumentException("Negative array size [" + newSize + "] not allowed.");
/*     */     }
/*     */ 
/* 135 */     if (newSize == this.numElems) {
/* 136 */       return;
/*     */     }
/* 138 */     LoggingEvent[] temp = new LoggingEvent[newSize];
/*     */ 
/* 140 */     int loopLen = newSize < this.numElems ? newSize : this.numElems;
/*     */ 
/* 142 */     for (int i = 0; i < loopLen; i++) {
/* 143 */       temp[i] = this.ea[this.first];
/* 144 */       this.ea[this.first] = null;
/* 145 */       if (++this.first == this.numElems)
/* 146 */         this.first = 0;
/*     */     }
/* 148 */     this.ea = temp;
/* 149 */     this.first = 0;
/* 150 */     this.numElems = loopLen;
/* 151 */     this.maxSize = newSize;
/* 152 */     if (loopLen == newSize)
/* 153 */       this.last = 0;
/*     */     else
/* 155 */       this.last = loopLen;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.helpers.CyclicBuffer
 * JD-Core Version:    0.6.0
 */
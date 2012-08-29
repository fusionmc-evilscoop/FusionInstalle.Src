/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ 
/*     */ class GradientCache
/*     */ {
/*     */   private GradientCacheEntry[] gradients;
/*     */   private int size;
/*     */   private int threshold;
/*     */   private final float loadFactor;
/* 149 */   private final ReferenceQueue queue = new ReferenceQueue();
/*     */ 
/*     */   GradientCache() {
/* 152 */     this.loadFactor = 0.75F;
/* 153 */     this.threshold = 16;
/* 154 */     this.gradients = new GradientCacheEntry[16];
/*     */   }
/*     */ 
/*     */   BufferedImage retrieve(GradientInfo info) {
/* 158 */     int ln = info.length;
/* 159 */     GradientCacheEntry[] grads = getGradients();
/* 160 */     int index = bucket(ln, grads.length);
/* 161 */     GradientCacheEntry e = grads[index];
/*     */ 
/* 163 */     while (e != null) {
/* 164 */       GradientInfo egi = e.getInfo();
/*     */       try {
/* 166 */         if ((egi != null) && 
/* 167 */           (e.length == ln) && (egi.isEquivalent(info))) {
/* 168 */           return e.gradient;
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (NullPointerException npe)
/*     */       {
/*     */       }
/*     */ 
/* 180 */       e = e.next;
/*     */     }
/* 182 */     return null;
/*     */   }
/*     */ 
/*     */   Object store(GradientInfo info, BufferedImage gradient) {
/* 186 */     GradientCacheEntry[] grads = getGradients();
/* 187 */     int i = bucket(info.length, grads.length);
/*     */ 
/* 189 */     GradientCacheEntry e = grads[i];
/*     */ 
/* 191 */     if (!entryNotInCache(e, info)) {
/* 192 */       System.err.println("Duplicate entry found!");
/*     */     }
/*     */ 
/* 195 */     grads[i] = new GradientCacheEntry(info, gradient, this.queue, e);
/* 196 */     if (++this.size >= this.threshold)
/* 197 */       resize(grads.length << 1);
/* 198 */     return null;
/*     */   }
/*     */ 
/*     */   void clear() {
/* 202 */     GradientCacheEntry[] a = getGradients();
/* 203 */     for (int i = 0; i < a.length; i++) {
/* 204 */       a[i] = null;
/*     */     }
/* 206 */     this.size = 0;
/* 207 */     this.threshold = 16;
/* 208 */     this.gradients = new GradientCacheEntry[16];
/*     */   }
/*     */ 
/*     */   private boolean entryNotInCache(GradientCacheEntry e, GradientInfo info) {
/* 212 */     while ((e != null) && (e.getInfo() != null)) {
/* 213 */       if ((e.length == info.length) && (e.getInfo().isEquivalent(info))) {
/* 214 */         return false;
/*     */       }
/* 216 */       e = e.next;
/*     */     }
/* 218 */     return true;
/*     */   }
/*     */ 
/*     */   private void resize(int newCapacity) {
/* 222 */     GradientCacheEntry[] oldArray = getGradients();
/* 223 */     int oldCapacity = oldArray.length;
/* 224 */     if (oldCapacity == 1073741824) {
/* 225 */       this.threshold = 2147483647;
/* 226 */       return;
/*     */     }
/*     */ 
/* 229 */     GradientCacheEntry[] newArray = new GradientCacheEntry[newCapacity];
/* 230 */     moveEntries(oldArray, newArray);
/* 231 */     this.gradients = newArray;
/*     */ 
/* 233 */     if (this.size >= this.threshold >> 1) {
/* 234 */       this.threshold = (int)(newCapacity * this.loadFactor);
/*     */     }
/*     */     else {
/* 237 */       cleanOldCacheEntries();
/* 238 */       moveEntries(newArray, oldArray);
/* 239 */       this.gradients = oldArray;
/*     */     }
/*     */   }
/*     */ 
/*     */   private GradientCacheEntry[] getGradients() {
/* 244 */     cleanOldCacheEntries();
/* 245 */     return this.gradients;
/*     */   }
/*     */ 
/*     */   private static int bucket(int h, int length) {
/* 249 */     return h & length - 1;
/*     */   }
/*     */ 
/*     */   private void moveEntries(GradientCacheEntry[] src, GradientCacheEntry[] dest) {
/* 253 */     for (int j = 0; j < src.length; j++) {
/* 254 */       GradientCacheEntry e = src[j];
/* 255 */       src[j] = null;
/* 256 */       while (e != null) {
/* 257 */         GradientCacheEntry next = e.next;
/* 258 */         Object o = e.get();
/* 259 */         if (o == null) {
/* 260 */           e.next = null;
/* 261 */           e.gradient = null;
/* 262 */           this.size -= 1;
/*     */         }
/*     */         else {
/* 265 */           int i = bucket(e.length, dest.length);
/* 266 */           e.next = dest[i];
/* 267 */           dest[i] = e;
/*     */         }
/* 269 */         e = next;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void cleanOldCacheEntries()
/*     */   {
/*     */     GradientCacheEntry e;
/* 276 */     while ((e = (GradientCacheEntry)this.queue.poll()) != null) {
/* 277 */       int i = bucket(e.length, this.gradients.length);
/*     */ 
/* 279 */       GradientCacheEntry prev = this.gradients[i];
/* 280 */       GradientCacheEntry p = prev;
/* 281 */       while (p != null) {
/* 282 */         GradientCacheEntry next = p.next;
/* 283 */         if (p == e) {
/* 284 */           if (prev == e)
/* 285 */             this.gradients[i] = next;
/*     */           else
/* 287 */             prev.next = next;
/* 288 */           e.next = null;
/* 289 */           e.gradient = null;
/* 290 */           this.size -= 1;
/* 291 */           break;
/*     */         }
/* 293 */         prev = p;
/* 294 */         p = next;
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.GradientCache
 * JD-Core Version:    0.6.0
 */
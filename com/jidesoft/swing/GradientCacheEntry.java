/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.SoftReference;
/*     */ 
/*     */ class GradientCacheEntry extends SoftReference
/*     */ {
/*     */   GradientCacheEntry next;
/*     */   BufferedImage gradient;
/*     */   int length;
/*     */ 
/*     */   GradientCacheEntry(GradientInfo info, BufferedImage gradient, ReferenceQueue queue, GradientCacheEntry next)
/*     */   {
/* 306 */     super(info, queue);
/* 307 */     this.next = next;
/* 308 */     this.gradient = gradient;
/* 309 */     this.length = info.length;
/*     */   }
/*     */ 
/*     */   GradientInfo getInfo() {
/* 313 */     return (GradientInfo)get();
/*     */   }
/*     */ 
/*     */   BufferedImage getGradient() {
/* 317 */     return this.gradient;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.GradientCacheEntry
 * JD-Core Version:    0.6.0
 */
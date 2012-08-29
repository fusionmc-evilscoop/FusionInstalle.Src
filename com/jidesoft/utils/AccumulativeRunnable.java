/*     */ package com.jidesoft.utils;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.swing.SwingUtilities;
/*     */ 
/*     */ abstract class AccumulativeRunnable<T>
/*     */   implements Runnable
/*     */ {
/*  69 */   private List<T> arguments = null;
/*     */ 
/*     */   protected abstract void run(List<T> paramList);
/*     */ 
/*     */   public final void run()
/*     */   {
/*  85 */     run(flush());
/*     */   }
/*     */ 
/*     */   public final synchronized void add(T[] args)
/*     */   {
/*  96 */     boolean isSubmitted = true;
/*  97 */     if (this.arguments == null) {
/*  98 */       isSubmitted = false;
/*  99 */       this.arguments = new ArrayList();
/*     */     }
/* 101 */     Collections.addAll(this.arguments, args);
/* 102 */     if (!isSubmitted)
/* 103 */       submit();
/*     */   }
/*     */ 
/*     */   protected void submit()
/*     */   {
/* 117 */     SwingUtilities.invokeLater(this);
/*     */   }
/*     */ 
/*     */   private final synchronized List<T> flush()
/*     */   {
/* 126 */     List list = this.arguments;
/* 127 */     this.arguments = null;
/* 128 */     return list;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.AccumulativeRunnable
 * JD-Core Version:    0.6.0
 */
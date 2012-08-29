/*     */ package org.apache.commons.io.filefilter;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ 
/*     */ public class PrefixFileFilter extends AbstractFileFilter
/*     */ {
/*     */   private String[] prefixes;
/*     */ 
/*     */   public PrefixFileFilter(String prefix)
/*     */   {
/*  56 */     if (prefix == null) {
/*  57 */       throw new IllegalArgumentException("The prefix must not be null");
/*     */     }
/*  59 */     this.prefixes = new String[] { prefix };
/*     */   }
/*     */ 
/*     */   public PrefixFileFilter(String[] prefixes)
/*     */   {
/*  72 */     if (prefixes == null) {
/*  73 */       throw new IllegalArgumentException("The array of prefixes must not be null");
/*     */     }
/*  75 */     this.prefixes = prefixes;
/*     */   }
/*     */ 
/*     */   public PrefixFileFilter(List prefixes)
/*     */   {
/*  86 */     if (prefixes == null) {
/*  87 */       throw new IllegalArgumentException("The list of prefixes must not be null");
/*     */     }
/*  89 */     this.prefixes = ((String[])(String[])prefixes.toArray(new String[prefixes.size()]));
/*     */   }
/*     */ 
/*     */   public boolean accept(File file)
/*     */   {
/*  99 */     String name = file.getName();
/* 100 */     for (int i = 0; i < this.prefixes.length; i++) {
/* 101 */       if (name.startsWith(this.prefixes[i])) {
/* 102 */         return true;
/*     */       }
/*     */     }
/* 105 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean accept(File file, String name)
/*     */   {
/* 116 */     for (int i = 0; i < this.prefixes.length; i++) {
/* 117 */       if (name.startsWith(this.prefixes[i])) {
/* 118 */         return true;
/*     */       }
/*     */     }
/* 121 */     return false;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.filefilter.PrefixFileFilter
 * JD-Core Version:    0.6.0
 */
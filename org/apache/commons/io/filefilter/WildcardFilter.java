/*     */ package org.apache.commons.io.filefilter;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ 
/*     */ /** @deprecated */
/*     */ public class WildcardFilter extends AbstractFileFilter
/*     */ {
/*     */   private String[] wildcards;
/*     */ 
/*     */   public WildcardFilter(String wildcard)
/*     */   {
/*  64 */     if (wildcard == null) {
/*  65 */       throw new IllegalArgumentException("The wildcard must not be null");
/*     */     }
/*  67 */     this.wildcards = new String[] { wildcard };
/*     */   }
/*     */ 
/*     */   public WildcardFilter(String[] wildcards)
/*     */   {
/*  77 */     if (wildcards == null) {
/*  78 */       throw new IllegalArgumentException("The wildcard array must not be null");
/*     */     }
/*  80 */     this.wildcards = wildcards;
/*     */   }
/*     */ 
/*     */   public WildcardFilter(List wildcards)
/*     */   {
/*  91 */     if (wildcards == null) {
/*  92 */       throw new IllegalArgumentException("The wildcard list must not be null");
/*     */     }
/*  94 */     this.wildcards = ((String[])(String[])wildcards.toArray(new String[wildcards.size()]));
/*     */   }
/*     */ 
/*     */   public boolean accept(File dir, String name)
/*     */   {
/* 106 */     if ((dir != null) && (new File(dir, name).isDirectory())) {
/* 107 */       return false;
/*     */     }
/*     */ 
/* 110 */     for (int i = 0; i < this.wildcards.length; i++) {
/* 111 */       if (FilenameUtils.wildcardMatch(name, this.wildcards[i])) {
/* 112 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean accept(File file)
/*     */   {
/* 126 */     if (file.isDirectory()) {
/* 127 */       return false;
/*     */     }
/*     */ 
/* 130 */     for (int i = 0; i < this.wildcards.length; i++) {
/* 131 */       if (FilenameUtils.wildcardMatch(file.getName(), this.wildcards[i])) {
/* 132 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 136 */     return false;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.filefilter.WildcardFilter
 * JD-Core Version:    0.6.0
 */
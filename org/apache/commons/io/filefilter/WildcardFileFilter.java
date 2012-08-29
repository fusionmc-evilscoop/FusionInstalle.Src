/*     */ package org.apache.commons.io.filefilter;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.apache.commons.io.IOCase;
/*     */ 
/*     */ public class WildcardFileFilter extends AbstractFileFilter
/*     */ {
/*     */   private String[] wildcards;
/*     */   private IOCase caseSensitivity;
/*     */ 
/*     */   public WildcardFileFilter(String wildcard)
/*     */   {
/*  65 */     this(wildcard, null);
/*     */   }
/*     */ 
/*     */   public WildcardFileFilter(String wildcard, IOCase caseSensitivity)
/*     */   {
/*  76 */     if (wildcard == null) {
/*  77 */       throw new IllegalArgumentException("The wildcard must not be null");
/*     */     }
/*  79 */     this.wildcards = new String[] { wildcard };
/*  80 */     this.caseSensitivity = (caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity);
/*     */   }
/*     */ 
/*     */   public WildcardFileFilter(String[] wildcards)
/*     */   {
/*  93 */     this(wildcards, null);
/*     */   }
/*     */ 
/*     */   public WildcardFileFilter(String[] wildcards, IOCase caseSensitivity)
/*     */   {
/* 107 */     if (wildcards == null) {
/* 108 */       throw new IllegalArgumentException("The wildcard array must not be null");
/*     */     }
/* 110 */     this.wildcards = wildcards;
/* 111 */     this.caseSensitivity = (caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity);
/*     */   }
/*     */ 
/*     */   public WildcardFileFilter(List wildcards)
/*     */   {
/* 122 */     this(wildcards, null);
/*     */   }
/*     */ 
/*     */   public WildcardFileFilter(List wildcards, IOCase caseSensitivity)
/*     */   {
/* 134 */     if (wildcards == null) {
/* 135 */       throw new IllegalArgumentException("The wildcard list must not be null");
/*     */     }
/* 137 */     this.wildcards = ((String[])(String[])wildcards.toArray(new String[wildcards.size()]));
/* 138 */     this.caseSensitivity = (caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity);
/*     */   }
/*     */ 
/*     */   public boolean accept(File dir, String name)
/*     */   {
/* 150 */     for (int i = 0; i < this.wildcards.length; i++) {
/* 151 */       if (FilenameUtils.wildcardMatch(name, this.wildcards[i], this.caseSensitivity)) {
/* 152 */         return true;
/*     */       }
/*     */     }
/* 155 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean accept(File file)
/*     */   {
/* 165 */     String name = file.getName();
/* 166 */     for (int i = 0; i < this.wildcards.length; i++) {
/* 167 */       if (FilenameUtils.wildcardMatch(name, this.wildcards[i], this.caseSensitivity)) {
/* 168 */         return true;
/*     */       }
/*     */     }
/* 171 */     return false;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.filefilter.WildcardFileFilter
 * JD-Core Version:    0.6.0
 */
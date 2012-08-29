/*     */ package org.apache.commons.io.filefilter;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import org.apache.commons.io.IOCase;
/*     */ 
/*     */ public class NameFileFilter extends AbstractFileFilter
/*     */ {
/*     */   private String[] names;
/*     */   private IOCase caseSensitivity;
/*     */ 
/*     */   public NameFileFilter(String name)
/*     */   {
/*  60 */     this(name, null);
/*     */   }
/*     */ 
/*     */   public NameFileFilter(String name, IOCase caseSensitivity)
/*     */   {
/*  71 */     if (name == null) {
/*  72 */       throw new IllegalArgumentException("The wildcard must not be null");
/*     */     }
/*  74 */     this.names = new String[] { name };
/*  75 */     this.caseSensitivity = (caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity);
/*     */   }
/*     */ 
/*     */   public NameFileFilter(String[] names)
/*     */   {
/*  88 */     this(names, null);
/*     */   }
/*     */ 
/*     */   public NameFileFilter(String[] names, IOCase caseSensitivity)
/*     */   {
/* 102 */     if (names == null) {
/* 103 */       throw new IllegalArgumentException("The array of names must not be null");
/*     */     }
/* 105 */     this.names = names;
/* 106 */     this.caseSensitivity = (caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity);
/*     */   }
/*     */ 
/*     */   public NameFileFilter(List names)
/*     */   {
/* 117 */     this(names, null);
/*     */   }
/*     */ 
/*     */   public NameFileFilter(List names, IOCase caseSensitivity)
/*     */   {
/* 129 */     if (names == null) {
/* 130 */       throw new IllegalArgumentException("The list of names must not be null");
/*     */     }
/* 132 */     this.names = ((String[])(String[])names.toArray(new String[names.size()]));
/* 133 */     this.caseSensitivity = (caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity);
/*     */   }
/*     */ 
/*     */   public boolean accept(File file)
/*     */   {
/* 144 */     String name = file.getName();
/* 145 */     for (int i = 0; i < this.names.length; i++) {
/* 146 */       if (this.caseSensitivity.checkEquals(name, this.names[i])) {
/* 147 */         return true;
/*     */       }
/*     */     }
/* 150 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean accept(File file, String name)
/*     */   {
/* 161 */     for (int i = 0; i < this.names.length; i++) {
/* 162 */       if (this.caseSensitivity.checkEquals(name, this.names[i])) {
/* 163 */         return true;
/*     */       }
/*     */     }
/* 166 */     return false;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.filefilter.NameFileFilter
 * JD-Core Version:    0.6.0
 */
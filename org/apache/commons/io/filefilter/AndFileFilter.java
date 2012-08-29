/*     */ package org.apache.commons.io.filefilter;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class AndFileFilter extends AbstractFileFilter
/*     */   implements ConditionalFileFilter
/*     */ {
/*     */   private List fileFilters;
/*     */ 
/*     */   public AndFileFilter()
/*     */   {
/*  50 */     this.fileFilters = new ArrayList();
/*     */   }
/*     */ 
/*     */   public AndFileFilter(List fileFilters)
/*     */   {
/*  61 */     if (fileFilters == null)
/*  62 */       this.fileFilters = new ArrayList();
/*     */     else
/*  64 */       this.fileFilters = new ArrayList(fileFilters);
/*     */   }
/*     */ 
/*     */   public AndFileFilter(IOFileFilter filter1, IOFileFilter filter2)
/*     */   {
/*  76 */     if ((filter1 == null) || (filter2 == null)) {
/*  77 */       throw new IllegalArgumentException("The filters must not be null");
/*     */     }
/*  79 */     this.fileFilters = new ArrayList();
/*  80 */     addFileFilter(filter1);
/*  81 */     addFileFilter(filter2);
/*     */   }
/*     */ 
/*     */   public void addFileFilter(IOFileFilter ioFileFilter)
/*     */   {
/*  88 */     this.fileFilters.add(ioFileFilter);
/*     */   }
/*     */ 
/*     */   public List getFileFilters()
/*     */   {
/*  95 */     return Collections.unmodifiableList(this.fileFilters);
/*     */   }
/*     */ 
/*     */   public boolean removeFileFilter(IOFileFilter ioFileFilter)
/*     */   {
/* 102 */     return this.fileFilters.remove(ioFileFilter);
/*     */   }
/*     */ 
/*     */   public void setFileFilters(List fileFilters)
/*     */   {
/* 109 */     this.fileFilters = new ArrayList(fileFilters);
/*     */   }
/*     */ 
/*     */   public boolean accept(File file)
/*     */   {
/* 116 */     if (this.fileFilters.size() == 0) {
/* 117 */       return false;
/*     */     }
/* 119 */     for (Iterator iter = this.fileFilters.iterator(); iter.hasNext(); ) {
/* 120 */       IOFileFilter fileFilter = (IOFileFilter)iter.next();
/* 121 */       if (!fileFilter.accept(file)) {
/* 122 */         return false;
/*     */       }
/*     */     }
/* 125 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean accept(File file, String name)
/*     */   {
/* 132 */     if (this.fileFilters.size() == 0) {
/* 133 */       return false;
/*     */     }
/* 135 */     for (Iterator iter = this.fileFilters.iterator(); iter.hasNext(); ) {
/* 136 */       IOFileFilter fileFilter = (IOFileFilter)iter.next();
/* 137 */       if (!fileFilter.accept(file, name)) {
/* 138 */         return false;
/*     */       }
/*     */     }
/* 141 */     return true;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.filefilter.AndFileFilter
 * JD-Core Version:    0.6.0
 */
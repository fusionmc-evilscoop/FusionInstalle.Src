/*     */ package org.apache.commons.io.filefilter;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class OrFileFilter extends AbstractFileFilter
/*     */   implements ConditionalFileFilter
/*     */ {
/*     */   private List fileFilters;
/*     */ 
/*     */   public OrFileFilter()
/*     */   {
/*  50 */     this.fileFilters = new ArrayList();
/*     */   }
/*     */ 
/*     */   public OrFileFilter(List fileFilters)
/*     */   {
/*  61 */     if (fileFilters == null)
/*  62 */       this.fileFilters = new ArrayList();
/*     */     else
/*  64 */       this.fileFilters = new ArrayList(fileFilters);
/*     */   }
/*     */ 
/*     */   public OrFileFilter(IOFileFilter filter1, IOFileFilter filter2)
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
/* 109 */     this.fileFilters = fileFilters;
/*     */   }
/*     */ 
/*     */   public boolean accept(File file)
/*     */   {
/* 116 */     for (Iterator iter = this.fileFilters.iterator(); iter.hasNext(); ) {
/* 117 */       IOFileFilter fileFilter = (IOFileFilter)iter.next();
/* 118 */       if (fileFilter.accept(file)) {
/* 119 */         return true;
/*     */       }
/*     */     }
/* 122 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean accept(File file, String name)
/*     */   {
/* 129 */     for (Iterator iter = this.fileFilters.iterator(); iter.hasNext(); ) {
/* 130 */       IOFileFilter fileFilter = (IOFileFilter)iter.next();
/* 131 */       if (fileFilter.accept(file, name)) {
/* 132 */         return true;
/*     */       }
/*     */     }
/* 135 */     return false;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.filefilter.OrFileFilter
 * JD-Core Version:    0.6.0
 */
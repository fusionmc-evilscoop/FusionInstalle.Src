/*     */ package org.apache.commons.io.filefilter;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ 
/*     */ public class AgeFileFilter extends AbstractFileFilter
/*     */ {
/*     */   private long cutoff;
/*     */   private boolean acceptOlder;
/*     */ 
/*     */   public AgeFileFilter(long cutoff)
/*     */   {
/*  59 */     this(cutoff, true);
/*     */   }
/*     */ 
/*     */   public AgeFileFilter(long cutoff, boolean acceptOlder)
/*     */   {
/*  71 */     this.acceptOlder = acceptOlder;
/*  72 */     this.cutoff = cutoff;
/*     */   }
/*     */ 
/*     */   public AgeFileFilter(Date cutoffDate)
/*     */   {
/*  82 */     this(cutoffDate, true);
/*     */   }
/*     */ 
/*     */   public AgeFileFilter(Date cutoffDate, boolean acceptOlder)
/*     */   {
/*  94 */     this(cutoffDate.getTime(), acceptOlder);
/*     */   }
/*     */ 
/*     */   public AgeFileFilter(File cutoffReference)
/*     */   {
/* 105 */     this(cutoffReference, true);
/*     */   }
/*     */ 
/*     */   public AgeFileFilter(File cutoffReference, boolean acceptOlder)
/*     */   {
/* 119 */     this(cutoffReference.lastModified(), acceptOlder);
/*     */   }
/*     */ 
/*     */   public boolean accept(File file)
/*     */   {
/* 136 */     boolean newer = FileUtils.isFileNewer(file, this.cutoff);
/* 137 */     return this.acceptOlder ? false : !newer ? true : newer;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.filefilter.AgeFileFilter
 * JD-Core Version:    0.6.0
 */
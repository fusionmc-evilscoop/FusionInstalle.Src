/*     */ package org.apache.commons.io.filefilter;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.FilenameFilter;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class FileFilterUtils
/*     */ {
/*     */   private static IOFileFilter cvsFilter;
/*     */   private static IOFileFilter svnFilter;
/*     */ 
/*     */   public static IOFileFilter prefixFileFilter(String prefix)
/*     */   {
/*  53 */     return new PrefixFileFilter(prefix);
/*     */   }
/*     */ 
/*     */   public static IOFileFilter suffixFileFilter(String suffix)
/*     */   {
/*  63 */     return new SuffixFileFilter(suffix);
/*     */   }
/*     */ 
/*     */   public static IOFileFilter nameFileFilter(String name)
/*     */   {
/*  73 */     return new NameFileFilter(name);
/*     */   }
/*     */ 
/*     */   public static IOFileFilter directoryFileFilter()
/*     */   {
/*  82 */     return DirectoryFileFilter.DIRECTORY;
/*     */   }
/*     */ 
/*     */   public static IOFileFilter fileFileFilter()
/*     */   {
/*  91 */     return FileFileFilter.FILE;
/*     */   }
/*     */ 
/*     */   public static IOFileFilter andFileFilter(IOFileFilter filter1, IOFileFilter filter2)
/*     */   {
/* 103 */     return new AndFileFilter(filter1, filter2);
/*     */   }
/*     */ 
/*     */   public static IOFileFilter orFileFilter(IOFileFilter filter1, IOFileFilter filter2)
/*     */   {
/* 114 */     return new OrFileFilter(filter1, filter2);
/*     */   }
/*     */ 
/*     */   public static IOFileFilter notFileFilter(IOFileFilter filter)
/*     */   {
/* 124 */     return new NotFileFilter(filter);
/*     */   }
/*     */ 
/*     */   public static IOFileFilter trueFileFilter()
/*     */   {
/* 134 */     return TrueFileFilter.TRUE;
/*     */   }
/*     */ 
/*     */   public static IOFileFilter falseFileFilter()
/*     */   {
/* 143 */     return FalseFileFilter.FALSE;
/*     */   }
/*     */ 
/*     */   public static IOFileFilter asFileFilter(FileFilter filter)
/*     */   {
/* 155 */     return new DelegateFileFilter(filter);
/*     */   }
/*     */ 
/*     */   public static IOFileFilter asFileFilter(FilenameFilter filter)
/*     */   {
/* 166 */     return new DelegateFileFilter(filter);
/*     */   }
/*     */ 
/*     */   public static IOFileFilter ageFileFilter(long cutoff)
/*     */   {
/* 179 */     return new AgeFileFilter(cutoff);
/*     */   }
/*     */ 
/*     */   public static IOFileFilter ageFileFilter(long cutoff, boolean acceptOlder)
/*     */   {
/* 191 */     return new AgeFileFilter(cutoff, acceptOlder);
/*     */   }
/*     */ 
/*     */   public static IOFileFilter ageFileFilter(Date cutoffDate)
/*     */   {
/* 203 */     return new AgeFileFilter(cutoffDate);
/*     */   }
/*     */ 
/*     */   public static IOFileFilter ageFileFilter(Date cutoffDate, boolean acceptOlder)
/*     */   {
/* 215 */     return new AgeFileFilter(cutoffDate, acceptOlder);
/*     */   }
/*     */ 
/*     */   public static IOFileFilter ageFileFilter(File cutoffReference)
/*     */   {
/* 228 */     return new AgeFileFilter(cutoffReference);
/*     */   }
/*     */ 
/*     */   public static IOFileFilter ageFileFilter(File cutoffReference, boolean acceptOlder)
/*     */   {
/* 241 */     return new AgeFileFilter(cutoffReference, acceptOlder);
/*     */   }
/*     */ 
/*     */   public static IOFileFilter sizeFileFilter(long threshold)
/*     */   {
/* 253 */     return new SizeFileFilter(threshold);
/*     */   }
/*     */ 
/*     */   public static IOFileFilter sizeFileFilter(long threshold, boolean acceptLarger)
/*     */   {
/* 265 */     return new SizeFileFilter(threshold, acceptLarger);
/*     */   }
/*     */ 
/*     */   public static IOFileFilter sizeRangeFileFilter(long minSizeInclusive, long maxSizeInclusive)
/*     */   {
/* 278 */     IOFileFilter minimumFilter = new SizeFileFilter(minSizeInclusive, true);
/* 279 */     IOFileFilter maximumFilter = new SizeFileFilter(maxSizeInclusive + 1L, false);
/* 280 */     return new AndFileFilter(minimumFilter, maximumFilter);
/*     */   }
/*     */ 
/*     */   public static IOFileFilter makeCVSAware(IOFileFilter filter)
/*     */   {
/* 300 */     if (cvsFilter == null) {
/* 301 */       cvsFilter = notFileFilter(andFileFilter(directoryFileFilter(), nameFileFilter("CVS")));
/*     */     }
/*     */ 
/* 304 */     if (filter == null) {
/* 305 */       return cvsFilter;
/*     */     }
/* 307 */     return andFileFilter(filter, cvsFilter);
/*     */   }
/*     */ 
/*     */   public static IOFileFilter makeSVNAware(IOFileFilter filter)
/*     */   {
/* 321 */     if (svnFilter == null) {
/* 322 */       svnFilter = notFileFilter(andFileFilter(directoryFileFilter(), nameFileFilter(".svn")));
/*     */     }
/*     */ 
/* 325 */     if (filter == null) {
/* 326 */       return svnFilter;
/*     */     }
/* 328 */     return andFileFilter(filter, svnFilter);
/*     */   }
/*     */ 
/*     */   public static IOFileFilter makeDirectoryOnly(IOFileFilter filter)
/*     */   {
/* 341 */     if (filter == null) {
/* 342 */       return DirectoryFileFilter.DIRECTORY;
/*     */     }
/* 344 */     return new AndFileFilter(DirectoryFileFilter.DIRECTORY, filter);
/*     */   }
/*     */ 
/*     */   public static IOFileFilter makeFileOnly(IOFileFilter filter)
/*     */   {
/* 355 */     if (filter == null) {
/* 356 */       return FileFileFilter.FILE;
/*     */     }
/* 358 */     return new AndFileFilter(FileFileFilter.FILE, filter);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.filefilter.FileFilterUtils
 * JD-Core Version:    0.6.0
 */
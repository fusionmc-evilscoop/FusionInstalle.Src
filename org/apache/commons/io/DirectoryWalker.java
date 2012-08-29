/*     */ package org.apache.commons.io;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import org.apache.commons.io.filefilter.FileFilterUtils;
/*     */ import org.apache.commons.io.filefilter.IOFileFilter;
/*     */ import org.apache.commons.io.filefilter.TrueFileFilter;
/*     */ 
/*     */ public abstract class DirectoryWalker
/*     */ {
/*     */   private final FileFilter filter;
/*     */   private final int depthLimit;
/*     */ 
/*     */   protected DirectoryWalker()
/*     */   {
/* 266 */     this(null, -1);
/*     */   }
/*     */ 
/*     */   protected DirectoryWalker(FileFilter filter, int depthLimit)
/*     */   {
/* 282 */     this.filter = filter;
/* 283 */     this.depthLimit = depthLimit;
/*     */   }
/*     */ 
/*     */   protected DirectoryWalker(IOFileFilter directoryFilter, IOFileFilter fileFilter, int depthLimit)
/*     */   {
/* 301 */     if ((directoryFilter == null) && (fileFilter == null)) {
/* 302 */       this.filter = null;
/*     */     } else {
/* 304 */       directoryFilter = directoryFilter != null ? directoryFilter : TrueFileFilter.TRUE;
/* 305 */       fileFilter = fileFilter != null ? fileFilter : TrueFileFilter.TRUE;
/* 306 */       directoryFilter = FileFilterUtils.makeDirectoryOnly(directoryFilter);
/* 307 */       fileFilter = FileFilterUtils.makeFileOnly(fileFilter);
/* 308 */       this.filter = FileFilterUtils.orFileFilter(directoryFilter, fileFilter);
/*     */     }
/* 310 */     this.depthLimit = depthLimit;
/*     */   }
/*     */ 
/*     */   protected final void walk(File startDirectory, Collection results)
/*     */     throws IOException
/*     */   {
/* 330 */     if (startDirectory == null)
/* 331 */       throw new NullPointerException("Start Directory is null");
/*     */     try
/*     */     {
/* 334 */       handleStart(startDirectory, results);
/* 335 */       walk(startDirectory, 0, results);
/* 336 */       handleEnd(results);
/*     */     } catch (CancelException cancel) {
/* 338 */       handleCancelled(startDirectory, results, cancel);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void walk(File directory, int depth, Collection results)
/*     */     throws IOException
/*     */   {
/* 351 */     checkIfCancelled(directory, depth, results);
/* 352 */     if (handleDirectory(directory, depth, results)) {
/* 353 */       handleDirectoryStart(directory, depth, results);
/* 354 */       int childDepth = depth + 1;
/* 355 */       if ((this.depthLimit < 0) || (childDepth <= this.depthLimit)) {
/* 356 */         checkIfCancelled(directory, depth, results);
/* 357 */         File[] childFiles = this.filter == null ? directory.listFiles() : directory.listFiles(this.filter);
/* 358 */         if (childFiles == null)
/* 359 */           handleRestricted(directory, childDepth, results);
/*     */         else {
/* 361 */           for (int i = 0; i < childFiles.length; i++) {
/* 362 */             File childFile = childFiles[i];
/* 363 */             if (childFile.isDirectory()) {
/* 364 */               walk(childFile, childDepth, results);
/*     */             } else {
/* 366 */               checkIfCancelled(childFile, childDepth, results);
/* 367 */               handleFile(childFile, childDepth, results);
/* 368 */               checkIfCancelled(childFile, childDepth, results);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 373 */       handleDirectoryEnd(directory, depth, results);
/*     */     }
/* 375 */     checkIfCancelled(directory, depth, results);
/*     */   }
/*     */ 
/*     */   protected final void checkIfCancelled(File file, int depth, Collection results)
/*     */     throws IOException
/*     */   {
/* 394 */     if (handleIsCancelled(file, depth, results))
/* 395 */       throw new CancelException(file, depth);
/*     */   }
/*     */ 
/*     */   protected boolean handleIsCancelled(File file, int depth, Collection results)
/*     */     throws IOException
/*     */   {
/* 437 */     return false;
/*     */   }
/*     */ 
/*     */   protected void handleCancelled(File startDirectory, Collection results, CancelException cancel)
/*     */     throws IOException
/*     */   {
/* 456 */     throw cancel;
/*     */   }
/*     */ 
/*     */   protected void handleStart(File startDirectory, Collection results)
/*     */     throws IOException
/*     */   {
/*     */   }
/*     */ 
/*     */   protected boolean handleDirectory(File directory, int depth, Collection results)
/*     */     throws IOException
/*     */   {
/* 490 */     return true;
/*     */   }
/*     */ 
/*     */   protected void handleDirectoryStart(File directory, int depth, Collection results)
/*     */     throws IOException
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void handleFile(File file, int depth, Collection results)
/*     */     throws IOException
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void handleRestricted(File directory, int depth, Collection results)
/*     */     throws IOException
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void handleDirectoryEnd(File directory, int depth, Collection results)
/*     */     throws IOException
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void handleEnd(Collection results)
/*     */     throws IOException
/*     */   {
/*     */   }
/*     */ 
/*     */   public static class CancelException extends IOException
/*     */   {
/*     */     private static final long serialVersionUID = 1347339620135041008L;
/*     */     private File file;
/* 574 */     private int depth = -1;
/*     */ 
/*     */     public CancelException(File file, int depth)
/*     */     {
/* 584 */       this("Operation Cancelled", file, depth);
/*     */     }
/*     */ 
/*     */     public CancelException(String message, File file, int depth)
/*     */     {
/* 597 */       super();
/* 598 */       this.file = file;
/* 599 */       this.depth = depth;
/*     */     }
/*     */ 
/*     */     public File getFile()
/*     */     {
/* 608 */       return this.file;
/*     */     }
/*     */ 
/*     */     public int getDepth()
/*     */     {
/* 617 */       return this.depth;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.DirectoryWalker
 * JD-Core Version:    0.6.0
 */
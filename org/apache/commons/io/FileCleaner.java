/*     */ package org.apache.commons.io;
/*     */ 
/*     */ import java.io.File;
/*     */ 
/*     */ public class FileCleaner
/*     */ {
/*  43 */   static final FileCleaningTracker theInstance = new FileCleaningTracker();
/*     */ 
/*     */   public static void track(File file, Object marker)
/*     */   {
/*  56 */     theInstance.track(file, marker);
/*     */   }
/*     */ 
/*     */   public static void track(File file, Object marker, FileDeleteStrategy deleteStrategy)
/*     */   {
/*  70 */     theInstance.track(file, marker, deleteStrategy);
/*     */   }
/*     */ 
/*     */   public static void track(String path, Object marker)
/*     */   {
/*  83 */     theInstance.track(path, marker);
/*     */   }
/*     */ 
/*     */   public static void track(String path, Object marker, FileDeleteStrategy deleteStrategy)
/*     */   {
/*  97 */     theInstance.track(path, marker, deleteStrategy);
/*     */   }
/*     */ 
/*     */   public static int getTrackCount()
/*     */   {
/* 108 */     return theInstance.getTrackCount();
/*     */   }
/*     */ 
/*     */   public static synchronized void exitWhenFinished()
/*     */   {
/* 133 */     theInstance.exitWhenFinished();
/*     */   }
/*     */ 
/*     */   public static FileCleaningTracker getInstance()
/*     */   {
/* 143 */     return theInstance;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.FileCleaner
 * JD-Core Version:    0.6.0
 */
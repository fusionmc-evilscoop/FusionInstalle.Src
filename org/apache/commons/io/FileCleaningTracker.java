/*     */ package org.apache.commons.io;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.lang.ref.PhantomReference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.util.Collection;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class FileCleaningTracker
/*     */ {
/*     */   ReferenceQueue q;
/*     */   final Collection trackers;
/*     */   volatile boolean exitWhenFinished;
/*     */   Thread reaper;
/*     */ 
/*     */   public FileCleaningTracker()
/*     */   {
/*  47 */     this.q = new ReferenceQueue();
/*     */ 
/*  51 */     this.trackers = new Vector();
/*     */ 
/*  55 */     this.exitWhenFinished = false;
/*     */   }
/*     */ 
/*     */   public void track(File file, Object marker)
/*     */   {
/*  72 */     track(file, marker, (FileDeleteStrategy)null);
/*     */   }
/*     */ 
/*     */   public void track(File file, Object marker, FileDeleteStrategy deleteStrategy)
/*     */   {
/*  86 */     if (file == null) {
/*  87 */       throw new NullPointerException("The file must not be null");
/*     */     }
/*  89 */     addTracker(file.getPath(), marker, deleteStrategy);
/*     */   }
/*     */ 
/*     */   public void track(String path, Object marker)
/*     */   {
/* 102 */     track(path, marker, (FileDeleteStrategy)null);
/*     */   }
/*     */ 
/*     */   public void track(String path, Object marker, FileDeleteStrategy deleteStrategy)
/*     */   {
/* 116 */     if (path == null) {
/* 117 */       throw new NullPointerException("The path must not be null");
/*     */     }
/* 119 */     addTracker(path, marker, deleteStrategy);
/*     */   }
/*     */ 
/*     */   private synchronized void addTracker(String path, Object marker, FileDeleteStrategy deleteStrategy)
/*     */   {
/* 131 */     if (this.exitWhenFinished) {
/* 132 */       throw new IllegalStateException("No new trackers can be added once exitWhenFinished() is called");
/*     */     }
/* 134 */     if (this.reaper == null) {
/* 135 */       this.reaper = new Reaper();
/* 136 */       this.reaper.start();
/*     */     }
/* 138 */     this.trackers.add(new Tracker(path, deleteStrategy, marker, this.q));
/*     */   }
/*     */ 
/*     */   public int getTrackCount()
/*     */   {
/* 149 */     return this.trackers.size();
/*     */   }
/*     */ 
/*     */   public synchronized void exitWhenFinished()
/*     */   {
/* 175 */     this.exitWhenFinished = true;
/* 176 */     if (this.reaper != null)
/* 177 */       synchronized (this.reaper) {
/* 178 */         this.reaper.interrupt();
/*     */       }
/*     */   }
/*     */ 
/*     */   private static final class Tracker extends PhantomReference
/*     */   {
/*     */     private final String path;
/*     */     private final FileDeleteStrategy deleteStrategy;
/*     */ 
/*     */     Tracker(String path, FileDeleteStrategy deleteStrategy, Object marker, ReferenceQueue queue)
/*     */     {
/* 242 */       super(queue);
/* 243 */       this.path = path;
/* 244 */       this.deleteStrategy = (deleteStrategy == null ? FileDeleteStrategy.NORMAL : deleteStrategy);
/*     */     }
/*     */ 
/*     */     public boolean delete()
/*     */     {
/* 254 */       return this.deleteStrategy.deleteQuietly(new File(this.path));
/*     */     }
/*     */   }
/*     */ 
/*     */   private final class Reaper extends Thread
/*     */   {
/*     */     Reaper()
/*     */     {
/* 190 */       super();
/* 191 */       setPriority(10);
/* 192 */       setDaemon(true);
/*     */     }
/*     */ 
/*     */     public void run()
/*     */     {
/* 201 */       while ((!FileCleaningTracker.this.exitWhenFinished) || (FileCleaningTracker.this.trackers.size() > 0)) {
/* 202 */         FileCleaningTracker.Tracker tracker = null;
/*     */         try
/*     */         {
/* 205 */           tracker = (FileCleaningTracker.Tracker)FileCleaningTracker.this.q.remove(); } catch (Exception e) {
/*     */         }
/* 207 */         continue;
/*     */ 
/* 209 */         if (tracker != null) {
/* 210 */           tracker.delete();
/* 211 */           tracker.clear();
/* 212 */           FileCleaningTracker.this.trackers.remove(tracker);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.FileCleaningTracker
 * JD-Core Version:    0.6.0
 */
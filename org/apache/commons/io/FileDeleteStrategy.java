/*     */ package org.apache.commons.io;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class FileDeleteStrategy
/*     */ {
/*  41 */   public static final FileDeleteStrategy NORMAL = new FileDeleteStrategy("Normal");
/*     */ 
/*  46 */   public static final FileDeleteStrategy FORCE = new ForceFileDeleteStrategy();
/*     */   private final String name;
/*     */ 
/*     */   protected FileDeleteStrategy(String name)
/*     */   {
/*  58 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public boolean deleteQuietly(File fileToDelete)
/*     */   {
/*  73 */     if ((fileToDelete == null) || (!fileToDelete.exists()))
/*  74 */       return true;
/*     */     try
/*     */     {
/*  77 */       return doDelete(fileToDelete); } catch (IOException ex) {
/*     */     }
/*  79 */     return false;
/*     */   }
/*     */ 
/*     */   public void delete(File fileToDelete)
/*     */     throws IOException
/*     */   {
/*  94 */     if ((fileToDelete.exists()) && (!doDelete(fileToDelete)))
/*  95 */       throw new IOException("Deletion failed: " + fileToDelete);
/*     */   }
/*     */ 
/*     */   protected boolean doDelete(File fileToDelete)
/*     */     throws IOException
/*     */   {
/* 116 */     return fileToDelete.delete();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 126 */     return "FileDeleteStrategy[" + this.name + "]";
/*     */   }
/*     */ 
/*     */   static class ForceFileDeleteStrategy extends FileDeleteStrategy
/*     */   {
/*     */     ForceFileDeleteStrategy()
/*     */     {
/* 136 */       super();
/*     */     }
/*     */ 
/*     */     protected boolean doDelete(File fileToDelete)
/*     */       throws IOException
/*     */     {
/* 151 */       FileUtils.forceDelete(fileToDelete);
/* 152 */       return true;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.FileDeleteStrategy
 * JD-Core Version:    0.6.0
 */
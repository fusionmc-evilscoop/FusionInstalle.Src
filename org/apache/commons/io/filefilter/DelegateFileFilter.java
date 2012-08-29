/*    */ package org.apache.commons.io.filefilter;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileFilter;
/*    */ import java.io.FilenameFilter;
/*    */ 
/*    */ public class DelegateFileFilter extends AbstractFileFilter
/*    */ {
/*    */   private FilenameFilter filenameFilter;
/*    */   private FileFilter fileFilter;
/*    */ 
/*    */   public DelegateFileFilter(FilenameFilter filter)
/*    */   {
/* 44 */     if (filter == null) {
/* 45 */       throw new IllegalArgumentException("The FilenameFilter must not be null");
/*    */     }
/* 47 */     this.filenameFilter = filter;
/*    */   }
/*    */ 
/*    */   public DelegateFileFilter(FileFilter filter)
/*    */   {
/* 56 */     if (filter == null) {
/* 57 */       throw new IllegalArgumentException("The FileFilter must not be null");
/*    */     }
/* 59 */     this.fileFilter = filter;
/*    */   }
/*    */ 
/*    */   public boolean accept(File file)
/*    */   {
/* 69 */     if (this.fileFilter != null) {
/* 70 */       return this.fileFilter.accept(file);
/*    */     }
/* 72 */     return super.accept(file);
/*    */   }
/*    */ 
/*    */   public boolean accept(File dir, String name)
/*    */   {
/* 84 */     if (this.filenameFilter != null) {
/* 85 */       return this.filenameFilter.accept(dir, name);
/*    */     }
/* 87 */     return super.accept(dir, name);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.filefilter.DelegateFileFilter
 * JD-Core Version:    0.6.0
 */
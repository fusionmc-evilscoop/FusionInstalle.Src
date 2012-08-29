/*    */ package org.apache.commons.io.filefilter;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ public class DirectoryFileFilter extends AbstractFileFilter
/*    */ {
/* 47 */   public static final IOFileFilter DIRECTORY = new DirectoryFileFilter();
/*    */ 
/* 54 */   public static final IOFileFilter INSTANCE = DIRECTORY;
/*    */ 
/*    */   public boolean accept(File file)
/*    */   {
/* 69 */     return file.isDirectory();
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.filefilter.DirectoryFileFilter
 * JD-Core Version:    0.6.0
 */
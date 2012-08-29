/*    */ package org.apache.commons.io.filefilter;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ public class EmptyFileFilter extends AbstractFileFilter
/*    */ {
/* 56 */   public static final IOFileFilter EMPTY = new EmptyFileFilter();
/*    */ 
/* 59 */   public static final IOFileFilter NOT_EMPTY = new NotFileFilter(EMPTY);
/*    */ 
/*    */   public boolean accept(File file)
/*    */   {
/* 75 */     if (file.isDirectory()) {
/* 76 */       File[] files = file.listFiles();
/* 77 */       return (files == null) || (files.length == 0);
/*    */     }
/* 79 */     return file.length() == 0L;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.filefilter.EmptyFileFilter
 * JD-Core Version:    0.6.0
 */
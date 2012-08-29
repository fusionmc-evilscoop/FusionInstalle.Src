/*    */ package org.apache.commons.io.filefilter;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ public class FileFileFilter extends AbstractFileFilter
/*    */ {
/* 41 */   public static final IOFileFilter FILE = new FileFileFilter();
/*    */ 
/*    */   public boolean accept(File file)
/*    */   {
/* 56 */     return file.isFile();
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.filefilter.FileFileFilter
 * JD-Core Version:    0.6.0
 */
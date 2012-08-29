/*    */ package org.apache.commons.io.filefilter;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ public class CanReadFileFilter extends AbstractFileFilter
/*    */ {
/* 65 */   public static final IOFileFilter CAN_READ = new CanReadFileFilter();
/*    */ 
/* 68 */   public static final IOFileFilter CANNOT_READ = new NotFileFilter(CAN_READ);
/*    */ 
/* 71 */   public static final IOFileFilter READ_ONLY = new AndFileFilter(CAN_READ, CanWriteFileFilter.CANNOT_WRITE);
/*    */ 
/*    */   public boolean accept(File file)
/*    */   {
/* 88 */     return file.canRead();
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.filefilter.CanReadFileFilter
 * JD-Core Version:    0.6.0
 */
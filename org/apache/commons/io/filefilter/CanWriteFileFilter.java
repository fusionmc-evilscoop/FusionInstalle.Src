/*    */ package org.apache.commons.io.filefilter;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ public class CanWriteFileFilter extends AbstractFileFilter
/*    */ {
/* 57 */   public static final IOFileFilter CAN_WRITE = new CanWriteFileFilter();
/*    */ 
/* 60 */   public static final IOFileFilter CANNOT_WRITE = new NotFileFilter(CAN_WRITE);
/*    */ 
/*    */   public boolean accept(File file)
/*    */   {
/* 76 */     return file.canWrite();
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.filefilter.CanWriteFileFilter
 * JD-Core Version:    0.6.0
 */
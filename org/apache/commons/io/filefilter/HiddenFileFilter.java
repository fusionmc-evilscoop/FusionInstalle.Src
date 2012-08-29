/*    */ package org.apache.commons.io.filefilter;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ public class HiddenFileFilter extends AbstractFileFilter
/*    */ {
/* 53 */   public static final IOFileFilter HIDDEN = new HiddenFileFilter();
/*    */ 
/* 56 */   public static final IOFileFilter VISIBLE = new NotFileFilter(HIDDEN);
/*    */ 
/*    */   public boolean accept(File file)
/*    */   {
/* 72 */     return file.isHidden();
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.filefilter.HiddenFileFilter
 * JD-Core Version:    0.6.0
 */
/*    */ package org.apache.commons.io.filefilter;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ public class FalseFileFilter
/*    */   implements IOFileFilter
/*    */ {
/* 35 */   public static final IOFileFilter FALSE = new FalseFileFilter();
/*    */ 
/* 42 */   public static final IOFileFilter INSTANCE = FALSE;
/*    */ 
/*    */   public boolean accept(File file)
/*    */   {
/* 57 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean accept(File dir, String name)
/*    */   {
/* 68 */     return false;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.filefilter.FalseFileFilter
 * JD-Core Version:    0.6.0
 */
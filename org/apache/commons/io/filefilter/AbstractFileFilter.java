/*    */ package org.apache.commons.io.filefilter;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ public abstract class AbstractFileFilter
/*    */   implements IOFileFilter
/*    */ {
/*    */   public boolean accept(File file)
/*    */   {
/* 42 */     return accept(file.getParentFile(), file.getName());
/*    */   }
/*    */ 
/*    */   public boolean accept(File dir, String name)
/*    */   {
/* 53 */     return accept(new File(dir, name));
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.filefilter.AbstractFileFilter
 * JD-Core Version:    0.6.0
 */
/*    */ package org.apache.commons.io.filefilter;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ public class NotFileFilter extends AbstractFileFilter
/*    */ {
/*    */   private IOFileFilter filter;
/*    */ 
/*    */   public NotFileFilter(IOFileFilter filter)
/*    */   {
/* 41 */     if (filter == null) {
/* 42 */       throw new IllegalArgumentException("The filter must not be null");
/*    */     }
/* 44 */     this.filter = filter;
/*    */   }
/*    */ 
/*    */   public boolean accept(File file)
/*    */   {
/* 54 */     return !this.filter.accept(file);
/*    */   }
/*    */ 
/*    */   public boolean accept(File file, String name)
/*    */   {
/* 65 */     return !this.filter.accept(file, name);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.filefilter.NotFileFilter
 * JD-Core Version:    0.6.0
 */
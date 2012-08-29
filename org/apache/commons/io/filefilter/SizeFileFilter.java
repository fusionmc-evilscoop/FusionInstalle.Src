/*    */ package org.apache.commons.io.filefilter;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ public class SizeFileFilter extends AbstractFileFilter
/*    */ {
/*    */   private long size;
/*    */   private boolean acceptLarger;
/*    */ 
/*    */   public SizeFileFilter(long size)
/*    */   {
/* 55 */     this(size, true);
/*    */   }
/*    */ 
/*    */   public SizeFileFilter(long size, boolean acceptLarger)
/*    */   {
/* 68 */     if (size < 0L) {
/* 69 */       throw new IllegalArgumentException("The size must be non-negative");
/*    */     }
/* 71 */     this.size = size;
/* 72 */     this.acceptLarger = acceptLarger;
/*    */   }
/*    */ 
/*    */   public boolean accept(File file)
/*    */   {
/* 88 */     boolean smaller = file.length() < this.size;
/* 89 */     return this.acceptLarger ? false : !smaller ? true : smaller;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.filefilter.SizeFileFilter
 * JD-Core Version:    0.6.0
 */
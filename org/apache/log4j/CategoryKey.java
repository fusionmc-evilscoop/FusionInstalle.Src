/*    */ package org.apache.log4j;
/*    */ 
/*    */ class CategoryKey
/*    */ {
/*    */   String name;
/*    */   int hashCache;
/*    */ 
/*    */   CategoryKey(String name)
/*    */   {
/* 30 */     this.name = name;
/* 31 */     this.hashCache = name.hashCode();
/*    */   }
/*    */ 
/*    */   public final int hashCode()
/*    */   {
/* 37 */     return this.hashCache;
/*    */   }
/*    */ 
/*    */   public final boolean equals(Object rArg)
/*    */   {
/* 43 */     if (this == rArg) {
/* 44 */       return true;
/*    */     }
/* 46 */     if ((rArg != null) && (CategoryKey.class == rArg.getClass())) {
/* 47 */       return this.name.equals(((CategoryKey)rArg).name);
/*    */     }
/* 49 */     return false;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.CategoryKey
 * JD-Core Version:    0.6.0
 */
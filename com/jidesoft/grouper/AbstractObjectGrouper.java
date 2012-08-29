/*    */ package com.jidesoft.grouper;
/*    */ 
/*    */ import com.jidesoft.comparator.ComparatorContext;
/*    */ import com.jidesoft.converter.ConverterContext;
/*    */ 
/*    */ public abstract class AbstractObjectGrouper
/*    */   implements ObjectGrouper
/*    */ {
/*    */   public ConverterContext getConverterContext()
/*    */   {
/* 12 */     return null;
/*    */   }
/*    */ 
/*    */   public ComparatorContext getComparatorContext() {
/* 16 */     return null;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.grouper.AbstractObjectGrouper
 * JD-Core Version:    0.6.0
 */
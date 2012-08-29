/*    */ package com.jidesoft.comparator;
/*    */ 
/*    */ import com.jidesoft.converter.AbstractContext;
/*    */ 
/*    */ public class ComparatorContext extends AbstractContext
/*    */ {
/* 18 */   public static final ComparatorContext DEFAULT_CONTEXT = new ComparatorContext("");
/*    */ 
/*    */   public ComparatorContext(String name)
/*    */   {
/* 26 */     super(name);
/*    */   }
/*    */ 
/*    */   public ComparatorContext(String name, Object object)
/*    */   {
/* 36 */     super(name, object);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.comparator.ComparatorContext
 * JD-Core Version:    0.6.0
 */
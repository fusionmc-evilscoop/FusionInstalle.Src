/*    */ package com.jidesoft.grouper;
/*    */ 
/*    */ import com.jidesoft.converter.AbstractContext;
/*    */ 
/*    */ public class GrouperContext extends AbstractContext
/*    */ {
/* 18 */   public static GrouperContext DEFAULT_CONTEXT = new GrouperContext("");
/*    */ 
/*    */   public GrouperContext(String name)
/*    */   {
/* 26 */     super(name);
/*    */   }
/*    */ 
/*    */   public GrouperContext(String name, Object object)
/*    */   {
/* 36 */     super(name, object);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.grouper.GrouperContext
 * JD-Core Version:    0.6.0
 */
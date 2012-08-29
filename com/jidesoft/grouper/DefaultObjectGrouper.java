/*    */ package com.jidesoft.grouper;
/*    */ 
/*    */ public class DefaultObjectGrouper extends AbstractObjectGrouper
/*    */ {
/*    */   private String _name;
/*    */ 
/*    */   public DefaultObjectGrouper()
/*    */   {
/* 10 */     this("");
/*    */   }
/*    */ 
/*    */   public DefaultObjectGrouper(String name) {
/* 14 */     this._name = name;
/*    */   }
/*    */ 
/*    */   public Object getValue(Object value) {
/* 18 */     return value;
/*    */   }
/*    */ 
/*    */   public Class<?> getType() {
/* 22 */     return Object.class;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 26 */     return this._name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 30 */     this._name = name;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.grouper.DefaultObjectGrouper
 * JD-Core Version:    0.6.0
 */
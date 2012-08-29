/*    */ package com.jidesoft.validation;
/*    */ 
/*    */ import java.util.EventObject;
/*    */ 
/*    */ public class ValidationObject extends EventObject
/*    */ {
/*    */   private Object _newValue;
/*    */   private Object _oldValue;
/*    */ 
/*    */   public ValidationObject(Object source, Object oldValue, Object newValue)
/*    */   {
/* 52 */     super(source);
/* 53 */     this._newValue = newValue;
/* 54 */     this._oldValue = oldValue;
/*    */   }
/*    */ 
/*    */   public Object getNewValue()
/*    */   {
/* 64 */     return this._newValue;
/*    */   }
/*    */ 
/*    */   public Object getOldValue()
/*    */   {
/* 73 */     return this._oldValue;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 78 */     String properties = " source=" + getSource() + " oldValue=" + getOldValue() + " newValue=" + getNewValue() + " ";
/*    */ 
/* 83 */     return getClass().getName() + "[" + properties + "]";
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.validation.ValidationObject
 * JD-Core Version:    0.6.0
 */
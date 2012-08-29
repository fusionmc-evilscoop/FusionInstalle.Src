/*    */ package com.jidesoft.validation;
/*    */ 
/*    */ import java.util.EventObject;
/*    */ 
/*    */ public class RowValidationObject extends EventObject
/*    */ {
/*    */   private int _rowIndex;
/*    */ 
/*    */   public RowValidationObject(Object source, int rowIndex)
/*    */   {
/* 21 */     super(source);
/* 22 */     this._rowIndex = rowIndex;
/*    */   }
/*    */ 
/*    */   public int getRowIndex()
/*    */   {
/* 32 */     return this._rowIndex;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 37 */     String properties = " source=" + getSource() + " rowIndex=" + getRowIndex() + " ";
/*    */ 
/* 41 */     return getClass().getName() + "[" + properties + "]";
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.validation.RowValidationObject
 * JD-Core Version:    0.6.0
 */
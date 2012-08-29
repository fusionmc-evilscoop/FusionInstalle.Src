/*    */ package com.jidesoft.validation;
/*    */ 
/*    */ public class TableValidationObject extends ValidationObject
/*    */ {
/*    */   private int _row;
/*    */   private int _column;
/*    */ 
/*    */   public TableValidationObject(Object source, Object oldValue, Object newValue)
/*    */   {
/* 17 */     super(source, oldValue, newValue);
/*    */   }
/*    */ 
/*    */   public TableValidationObject(Object source, Object oldValue, Object newValue, int row, int column) {
/* 21 */     super(source, oldValue, newValue);
/* 22 */     this._row = row;
/* 23 */     this._column = column;
/*    */   }
/*    */ 
/*    */   public int getRow()
/*    */   {
/* 32 */     return this._row;
/*    */   }
/*    */ 
/*    */   public void setRow(int row)
/*    */   {
/* 41 */     this._row = row;
/*    */   }
/*    */ 
/*    */   public int getColumn()
/*    */   {
/* 50 */     return this._column;
/*    */   }
/*    */ 
/*    */   public void setColumn(int column)
/*    */   {
/* 59 */     this._column = column;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 64 */     String properties = " source=" + getSource() + " oldValue=" + getOldValue() + " newValue=" + getNewValue() + " row=" + getRow() + " column=" + getColumn() + " ";
/*    */ 
/* 71 */     return getClass().getName() + "[" + properties + "]";
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.validation.TableValidationObject
 * JD-Core Version:    0.6.0
 */
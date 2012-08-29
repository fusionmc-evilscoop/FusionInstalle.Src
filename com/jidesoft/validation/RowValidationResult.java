/*    */ package com.jidesoft.validation;
/*    */ 
/*    */ public class RowValidationResult extends ValidationResult
/*    */ {
/*    */   protected int[] _invalidColumns;
/*    */ 
/*    */   public RowValidationResult()
/*    */   {
/*    */   }
/*    */ 
/*    */   public RowValidationResult(int id)
/*    */   {
/* 15 */     super(id);
/*    */   }
/*    */ 
/*    */   public RowValidationResult(boolean valid) {
/* 19 */     super(valid);
/*    */   }
/*    */ 
/*    */   public RowValidationResult(int id, String message) {
/* 23 */     super(id, message);
/*    */   }
/*    */ 
/*    */   public RowValidationResult(int id, boolean valid, String message) {
/* 27 */     super(id, valid, message);
/*    */   }
/*    */ 
/*    */   public RowValidationResult(int id, boolean valid, int failBehavoir) {
/* 31 */     super(id, valid, failBehavoir);
/*    */   }
/*    */ 
/*    */   public RowValidationResult(int id, boolean valid, int failBehavoir, String message) {
/* 35 */     super(id, valid, failBehavoir, message);
/*    */   }
/*    */ 
/*    */   public RowValidationResult(int[] invalidColumns) {
/* 39 */     this._invalidColumns = invalidColumns;
/*    */   }
/*    */ 
/*    */   public RowValidationResult(int id, int[] invalidColumns) {
/* 43 */     super(id);
/* 44 */     this._invalidColumns = invalidColumns;
/*    */   }
/*    */ 
/*    */   public RowValidationResult(boolean valid, int[] invalidColumns) {
/* 48 */     super(valid);
/* 49 */     this._invalidColumns = invalidColumns;
/*    */   }
/*    */ 
/*    */   public RowValidationResult(int id, String message, int[] invalidColumns) {
/* 53 */     super(id, message);
/* 54 */     this._invalidColumns = invalidColumns;
/*    */   }
/*    */ 
/*    */   public RowValidationResult(int id, boolean valid, String message, int[] invalidColumns) {
/* 58 */     super(id, valid, message);
/* 59 */     this._invalidColumns = invalidColumns;
/*    */   }
/*    */ 
/*    */   public RowValidationResult(int id, boolean valid, int failBehavoir, int[] invalidColumns) {
/* 63 */     super(id, valid, failBehavoir);
/* 64 */     this._invalidColumns = invalidColumns;
/*    */   }
/*    */ 
/*    */   public RowValidationResult(int id, boolean valid, int failBehavoir, String message, int[] invalidColumns) {
/* 68 */     super(id, valid, failBehavoir, message);
/* 69 */     this._invalidColumns = invalidColumns;
/*    */   }
/*    */ 
/*    */   public int[] getInvalidColumns()
/*    */   {
/* 78 */     return this._invalidColumns;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.validation.RowValidationResult
 * JD-Core Version:    0.6.0
 */
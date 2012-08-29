/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import javax.swing.DefaultListSelectionModel;
/*    */ import javax.swing.ListModel;
/*    */ 
/*    */ public class CheckBoxListSelectionModel extends DefaultListSelectionModel
/*    */ {
/*    */   private ListModel _model;
/*    */ 
/*    */   public CheckBoxListSelectionModel()
/*    */   {
/*  9 */     setSelectionMode(2);
/*    */   }
/*    */ 
/*    */   public CheckBoxListSelectionModel(ListModel model) {
/* 13 */     this._model = model;
/* 14 */     setSelectionMode(2);
/*    */   }
/*    */ 
/*    */   public ListModel getModel() {
/* 18 */     return this._model;
/*    */   }
/*    */ 
/*    */   public void setModel(ListModel model) {
/* 22 */     int oldLength = 0;
/* 23 */     int newLength = 0;
/* 24 */     if (this._model != null) {
/* 25 */       oldLength = this._model.getSize();
/*    */     }
/* 27 */     this._model = model;
/* 28 */     if (this._model != null) {
/* 29 */       newLength = this._model.getSize();
/*    */     }
/* 31 */     if (oldLength > newLength)
/* 32 */       removeIndexInterval(newLength, oldLength);
/*    */   }
/*    */ 
/*    */   public void insertIndexInterval(int index, int length, boolean before)
/*    */   {
/* 45 */     if (before) {
/* 46 */       boolean old = isSelectedIndex(index);
/* 47 */       super.setValueIsAdjusting(true);
/*    */       try {
/* 49 */         if (old) {
/* 50 */           removeSelectionInterval(index, index);
/*    */         }
/* 52 */         super.insertIndexInterval(index, length, before);
/* 53 */         if (old)
/* 54 */           addSelectionInterval(index + length, index + length);
/*    */       }
/*    */       finally
/*    */       {
/* 58 */         super.setValueIsAdjusting(false);
/*    */       }
/*    */     }
/*    */     else {
/* 62 */       super.insertIndexInterval(index, length, before);
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.CheckBoxListSelectionModel
 * JD-Core Version:    0.6.0
 */
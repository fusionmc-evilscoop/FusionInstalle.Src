/*     */ package org.apache.log4j.lf5.viewer.categoryexplorer;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.EventObject;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.event.CellEditorListener;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.EventListenerList;
/*     */ import javax.swing.table.TableCellEditor;
/*     */ import javax.swing.tree.TreeCellEditor;
/*     */ 
/*     */ public class CategoryAbstractCellEditor
/*     */   implements TableCellEditor, TreeCellEditor
/*     */ {
/*  46 */   protected EventListenerList _listenerList = new EventListenerList();
/*     */   protected Object _value;
/*  48 */   protected ChangeEvent _changeEvent = null;
/*  49 */   protected int _clickCountToStart = 1;
/*     */ 
/*     */   public Object getCellEditorValue()
/*     */   {
/*  64 */     return this._value;
/*     */   }
/*     */ 
/*     */   public void setCellEditorValue(Object value) {
/*  68 */     this._value = value;
/*     */   }
/*     */ 
/*     */   public void setClickCountToStart(int count) {
/*  72 */     this._clickCountToStart = count;
/*     */   }
/*     */ 
/*     */   public int getClickCountToStart() {
/*  76 */     return this._clickCountToStart;
/*     */   }
/*     */ 
/*     */   public boolean isCellEditable(EventObject anEvent)
/*     */   {
/*  82 */     return (!(anEvent instanceof MouseEvent)) || 
/*  81 */       (((MouseEvent)anEvent).getClickCount() >= this._clickCountToStart);
/*     */   }
/*     */ 
/*     */   public boolean shouldSelectCell(EventObject anEvent)
/*     */   {
/*  92 */     return (isCellEditable(anEvent)) && (
/*  90 */       (anEvent == null) || (((MouseEvent)anEvent).getClickCount() >= this._clickCountToStart));
/*     */   }
/*     */ 
/*     */   public boolean stopCellEditing()
/*     */   {
/*  99 */     fireEditingStopped();
/* 100 */     return true;
/*     */   }
/*     */ 
/*     */   public void cancelCellEditing() {
/* 104 */     fireEditingCanceled();
/*     */   }
/*     */ 
/*     */   public void addCellEditorListener(CellEditorListener l) {
/* 108 */     this._listenerList.add(CellEditorListener.class, l);
/*     */   }
/*     */ 
/*     */   public void removeCellEditorListener(CellEditorListener l) {
/* 112 */     this._listenerList.remove(CellEditorListener.class, l);
/*     */   }
/*     */ 
/*     */   public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row)
/*     */   {
/* 120 */     return null;
/*     */   }
/*     */ 
/*     */   public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
/*     */   {
/* 127 */     return null;
/*     */   }
/*     */ 
/*     */   protected void fireEditingStopped()
/*     */   {
/* 134 */     Object[] listeners = this._listenerList.getListenerList();
/*     */ 
/* 136 */     for (int i = listeners.length - 2; i >= 0; i -= 2)
/* 137 */       if (listeners[i] == CellEditorListener.class) {
/* 138 */         if (this._changeEvent == null) {
/* 139 */           this._changeEvent = new ChangeEvent(this);
/*     */         }
/*     */ 
/* 142 */         ((CellEditorListener)listeners[(i + 1)]).editingStopped(this._changeEvent);
/*     */       }
/*     */   }
/*     */ 
/*     */   protected void fireEditingCanceled()
/*     */   {
/* 148 */     Object[] listeners = this._listenerList.getListenerList();
/*     */ 
/* 150 */     for (int i = listeners.length - 2; i >= 0; i -= 2)
/* 151 */       if (listeners[i] == CellEditorListener.class) {
/* 152 */         if (this._changeEvent == null) {
/* 153 */           this._changeEvent = new ChangeEvent(this);
/*     */         }
/*     */ 
/* 156 */         ((CellEditorListener)listeners[(i + 1)]).editingCanceled(this._changeEvent);
/*     */       }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.categoryexplorer.CategoryAbstractCellEditor
 * JD-Core Version:    0.6.0
 */
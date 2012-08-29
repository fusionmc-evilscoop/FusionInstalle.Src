/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.swing.event.SearchableEvent;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.ListSelectionModel;
/*     */ import javax.swing.event.TableModelEvent;
/*     */ import javax.swing.event.TableModelListener;
/*     */ import javax.swing.table.TableColumnModel;
/*     */ import javax.swing.table.TableModel;
/*     */ 
/*     */ public class TableSearchable extends Searchable
/*     */   implements TableModelListener, PropertyChangeListener
/*     */ {
/*  56 */   private int[] _searchColumnIndices = { 0 };
/*     */ 
/*     */   public TableSearchable(JTable table) {
/*  59 */     super(table);
/*     */   }
/*     */ 
/*     */   public void installListeners()
/*     */   {
/*  64 */     super.installListeners();
/*  65 */     if ((this._component instanceof JTable)) {
/*  66 */       ((JTable)this._component).getModel().addTableModelListener(this);
/*  67 */       this._component.addPropertyChangeListener("model", this);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void uninstallListeners()
/*     */   {
/*  73 */     super.uninstallListeners();
/*  74 */     if ((this._component instanceof JTable)) {
/*  75 */       ((JTable)this._component).getModel().removeTableModelListener(this);
/*  76 */       this._component.removePropertyChangeListener("model", this);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void setSelectedIndex(int index, boolean incremental)
/*     */   {
/*  83 */     JTable table = (JTable)this._component;
/*  84 */     if (isColumnSelectionAllowed(table)) {
/*  85 */       int minorIndex = index;
/*  86 */       int majorIndex = getMainIndex();
/*  87 */       addTableSelection(table, majorIndex, minorIndex, incremental);
/*     */     }
/*  89 */     else if (isRowSelectionAllowed(table)) {
/*  90 */       int majorIndex = index;
/*  91 */       int minorIndex = table.convertColumnIndexToView(getMainIndex());
/*  92 */       addTableSelection(table, majorIndex, minorIndex, incremental);
/*     */     }
/*     */     else {
/*  95 */       int columnCount = table.getColumnCount();
/*  96 */       if (columnCount == 0) {
/*  97 */         return;
/*     */       }
/*  99 */       int majorIndex = index / columnCount;
/* 100 */       int minorIndex = index % columnCount;
/* 101 */       addTableSelection(table, majorIndex, minorIndex, incremental);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void addTableSelection(JTable table, int rowIndex, int columnIndex, boolean incremental)
/*     */   {
/* 117 */     if (!incremental)
/* 118 */       table.clearSelection();
/* 119 */     if ((rowIndex >= 0) && (columnIndex >= 0) && (rowIndex < table.getRowCount()) && (columnIndex < table.getColumnCount()) && (!table.isCellSelected(rowIndex, columnIndex)))
/*     */     {
/* 121 */       table.changeSelection(rowIndex, columnIndex, true, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean isColumnSelectionAllowed(JTable table)
/*     */   {
/* 133 */     return (getSearchColumnIndices().length == 1) && (table.getColumnSelectionAllowed()) && (!table.getRowSelectionAllowed());
/*     */   }
/*     */ 
/*     */   protected boolean isRowSelectionAllowed(JTable table)
/*     */   {
/* 144 */     return (getSearchColumnIndices().length == 1) && (!table.getColumnSelectionAllowed()) && (table.getRowSelectionAllowed());
/*     */   }
/*     */ 
/*     */   protected boolean isSearchSelectedRows()
/*     */   {
/* 153 */     return getSearchColumnIndices().length > 1;
/*     */   }
/*     */ 
/*     */   protected int getSelectedIndex()
/*     */   {
/* 163 */     JTable table = (JTable)this._component;
/* 164 */     if (isColumnSelectionAllowed(table)) {
/* 165 */       return table.getColumnModel().getSelectionModel().getAnchorSelectionIndex();
/*     */     }
/* 167 */     if (isRowSelectionAllowed(table)) {
/* 168 */       return table.getSelectionModel().getAnchorSelectionIndex();
/*     */     }
/*     */ 
/* 171 */     return table.getSelectionModel().getAnchorSelectionIndex() * table.getColumnCount() + table.getColumnModel().getSelectionModel().getAnchorSelectionIndex();
/*     */   }
/*     */ 
/*     */   protected Object getElementAt(int index)
/*     */   {
/* 177 */     JTable table = (JTable)this._component;
/* 178 */     if (isColumnSelectionAllowed(table)) {
/* 179 */       return getValueAt(table, getMainIndex(), index);
/*     */     }
/* 181 */     if (isRowSelectionAllowed(table)) {
/* 182 */       return getValueAt(table, index, table.convertColumnIndexToView(getMainIndex()));
/*     */     }
/* 184 */     if (isSearchSelectedRows()) {
/* 185 */       int columnIndex = index % table.getColumnCount();
/* 186 */       int modelIndex = table.convertColumnIndexToModel(columnIndex);
/* 187 */       boolean doNotSearch = true;
/* 188 */       for (int i : getSearchColumnIndices()) {
/* 189 */         if (i == modelIndex) {
/* 190 */           doNotSearch = false;
/*     */         }
/*     */       }
/*     */ 
/* 194 */       if (doNotSearch) {
/* 195 */         return null;
/*     */       }
/*     */ 
/* 198 */       int rowIndex = index / table.getColumnCount();
/* 199 */       return getValueAt(table, rowIndex, columnIndex);
/*     */     }
/*     */ 
/* 202 */     int columnIndex = index % table.getColumnCount();
/* 203 */     int rowIndex = index / table.getColumnCount();
/* 204 */     return getValueAt(table, rowIndex, columnIndex);
/*     */   }
/*     */ 
/*     */   protected Object getValueAt(JTable table, int rowIndex, int columnIndex)
/*     */   {
/* 217 */     if ((rowIndex >= 0) && (rowIndex < table.getRowCount()) && (columnIndex >= 0) && (columnIndex < table.getColumnCount())) {
/* 218 */       return table.getValueAt(rowIndex, columnIndex);
/*     */     }
/*     */ 
/* 221 */     return null;
/*     */   }
/*     */ 
/*     */   protected int getElementCount()
/*     */   {
/* 227 */     JTable table = (JTable)this._component;
/* 228 */     if (isColumnSelectionAllowed(table)) {
/* 229 */       return table.getColumnCount();
/*     */     }
/* 231 */     if (isRowSelectionAllowed(table)) {
/* 232 */       return table.getRowCount();
/*     */     }
/*     */ 
/* 235 */     return table.getColumnCount() * table.getRowCount();
/*     */   }
/*     */ 
/*     */   protected String convertElementToString(Object item)
/*     */   {
/* 241 */     if (item != null) {
/* 242 */       return item.toString();
/*     */     }
/*     */ 
/* 245 */     return "";
/*     */   }
/*     */ 
/*     */   public int[] getSearchColumnIndices()
/*     */   {
/* 255 */     return this._searchColumnIndices;
/*     */   }
/*     */ 
/*     */   public int getMainIndex()
/*     */   {
/* 264 */     if (this._searchColumnIndices.length == 0) {
/* 265 */       return -1;
/*     */     }
/*     */ 
/* 268 */     return this._searchColumnIndices[0];
/*     */   }
/*     */ 
/*     */   public void setSearchColumnIndices(int[] columnIndices)
/*     */   {
/* 277 */     if (columnIndices == null) {
/* 278 */       columnIndices = new int[0];
/*     */     }
/*     */ 
/* 281 */     int[] old = this._searchColumnIndices;
/* 282 */     if (!JideSwingUtilities.equals(old, columnIndices, true)) {
/* 283 */       this._searchColumnIndices = columnIndices;
/* 284 */       hidePopup();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setMainIndex(int mainIndex)
/*     */   {
/* 294 */     int[] temp = { mainIndex };
/* 295 */     if (mainIndex < 0) {
/* 296 */       temp = new int[0];
/*     */     }
/* 298 */     int[] old = this._searchColumnIndices;
/* 299 */     if (old != temp) {
/* 300 */       this._searchColumnIndices = temp;
/* 301 */       hidePopup();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean isFindNextKey(KeyEvent e)
/*     */   {
/* 307 */     int keyCode = e.getKeyCode();
/* 308 */     JTable table = (JTable)this._component;
/* 309 */     if (isColumnSelectionAllowed(table)) {
/* 310 */       return keyCode == 39;
/*     */     }
/* 312 */     if (isRowSelectionAllowed(table)) {
/* 313 */       return keyCode == 40;
/*     */     }
/*     */ 
/* 316 */     return (keyCode == 40) || (keyCode == 39);
/*     */   }
/*     */ 
/*     */   protected boolean isFindPreviousKey(KeyEvent e)
/*     */   {
/* 322 */     int keyCode = e.getKeyCode();
/* 323 */     JTable table = (JTable)this._component;
/* 324 */     if (isColumnSelectionAllowed(table)) {
/* 325 */       return keyCode == 37;
/*     */     }
/* 327 */     if (isRowSelectionAllowed(table)) {
/* 328 */       return keyCode == 38;
/*     */     }
/*     */ 
/* 331 */     return (keyCode == 38) || (keyCode == 37);
/*     */   }
/*     */ 
/*     */   public void tableChanged(TableModelEvent e)
/*     */   {
/* 336 */     if (isProcessModelChangeEvent()) {
/* 337 */       hidePopup();
/* 338 */       fireSearchableEvent(new SearchableEvent(this, 3005));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void propertyChange(PropertyChangeEvent evt) {
/* 343 */     if ("model".equals(evt.getPropertyName())) {
/* 344 */       hidePopup();
/*     */ 
/* 346 */       if ((evt.getOldValue() instanceof TableModel)) {
/* 347 */         ((TableModel)evt.getOldValue()).removeTableModelListener(this);
/*     */       }
/*     */ 
/* 350 */       if ((evt.getNewValue() instanceof TableModel)) {
/* 351 */         ((TableModel)evt.getNewValue()).addTableModelListener(this);
/*     */       }
/* 353 */       fireSearchableEvent(new SearchableEvent(this, 3005));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean isActivateKey(KeyEvent e)
/*     */   {
/* 359 */     boolean editable = isSelectedCellEditable();
/* 360 */     return (!editable) && (super.isActivateKey(e));
/*     */   }
/*     */ 
/*     */   protected boolean isSelectedCellEditable()
/*     */   {
/* 369 */     int selectedRow = ((JTable)this._component).getSelectedRow();
/* 370 */     int selectedColumn = ((JTable)this._component).getSelectedColumn();
/* 371 */     return (selectedRow != -1) && (selectedColumn != -1) && (((JTable)this._component).isCellEditable(selectedRow, selectedColumn));
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.TableSearchable
 * JD-Core Version:    0.6.0
 */
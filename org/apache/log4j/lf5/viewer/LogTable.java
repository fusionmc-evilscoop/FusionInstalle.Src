/*     */ package org.apache.log4j.lf5.viewer;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.util.Enumeration;
/*     */ import java.util.EventObject;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.ListSelectionModel;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ import javax.swing.table.TableColumn;
/*     */ import javax.swing.table.TableColumnModel;
/*     */ import javax.swing.table.TableModel;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import org.apache.log4j.lf5.util.DateFormatManager;
/*     */ 
/*     */ public class LogTable extends JTable
/*     */ {
/*  50 */   protected int _rowHeight = 30;
/*     */   protected JTextArea _detailTextArea;
/*  54 */   protected int _numCols = 9;
/*  55 */   protected TableColumn[] _tableColumns = new TableColumn[this._numCols];
/*  56 */   protected int[] _colWidths = { 40, 40, 40, 70, 70, 360, 440, 200, 60 };
/*  57 */   protected LogTableColumn[] _colNames = LogTableColumn.getLogTableColumnArray();
/*  58 */   protected int _colDate = 0;
/*  59 */   protected int _colThread = 1;
/*  60 */   protected int _colMessageNum = 2;
/*  61 */   protected int _colLevel = 3;
/*  62 */   protected int _colNDC = 4;
/*  63 */   protected int _colCategory = 5;
/*  64 */   protected int _colMessage = 6;
/*  65 */   protected int _colLocation = 7;
/*  66 */   protected int _colThrown = 8;
/*     */ 
/*  68 */   protected DateFormatManager _dateFormatManager = null;
/*     */ 
/*     */   public LogTable(JTextArea detailTextArea)
/*     */   {
/*  81 */     init();
/*     */ 
/*  83 */     this._detailTextArea = detailTextArea;
/*     */ 
/*  85 */     setModel(new FilteredLogTableModel());
/*     */ 
/*  87 */     Enumeration columns = getColumnModel().getColumns();
/*  88 */     int i = 0;
/*  89 */     while (columns.hasMoreElements()) {
/*  90 */       TableColumn col = (TableColumn)columns.nextElement();
/*  91 */       col.setCellRenderer(new LogTableRowRenderer());
/*  92 */       col.setPreferredWidth(this._colWidths[i]);
/*     */ 
/*  94 */       this._tableColumns[i] = col;
/*  95 */       i++;
/*     */     }
/*     */ 
/*  98 */     ListSelectionModel rowSM = getSelectionModel();
/*  99 */     rowSM.addListSelectionListener(new LogTableListSelectionListener(this));
/*     */   }
/*     */ 
/*     */   public DateFormatManager getDateFormatManager()
/*     */   {
/* 112 */     return this._dateFormatManager;
/*     */   }
/*     */ 
/*     */   public void setDateFormatManager(DateFormatManager dfm)
/*     */   {
/* 119 */     this._dateFormatManager = dfm;
/*     */   }
/*     */ 
/*     */   public synchronized void clearLogRecords()
/*     */   {
/* 127 */     getFilteredLogTableModel().clear();
/*     */   }
/*     */ 
/*     */   public FilteredLogTableModel getFilteredLogTableModel() {
/* 131 */     return (FilteredLogTableModel)getModel();
/*     */   }
/*     */ 
/*     */   public void setDetailedView()
/*     */   {
/* 137 */     TableColumnModel model = getColumnModel();
/*     */ 
/* 139 */     for (int f = 0; f < this._numCols; f++) {
/* 140 */       model.removeColumn(this._tableColumns[f]);
/*     */     }
/*     */ 
/* 143 */     for (int i = 0; i < this._numCols; i++) {
/* 144 */       model.addColumn(this._tableColumns[i]);
/*     */     }
/*     */ 
/* 147 */     sizeColumnsToFit(-1);
/*     */   }
/*     */ 
/*     */   public void setView(List columns) {
/* 151 */     TableColumnModel model = getColumnModel();
/*     */ 
/* 154 */     for (int f = 0; f < this._numCols; f++) {
/* 155 */       model.removeColumn(this._tableColumns[f]);
/*     */     }
/* 157 */     Iterator selectedColumns = columns.iterator();
/* 158 */     Vector columnNameAndNumber = getColumnNameAndNumber();
/* 159 */     while (selectedColumns.hasNext())
/*     */     {
/* 161 */       model.addColumn(this._tableColumns[columnNameAndNumber.indexOf(selectedColumns.next())]);
/*     */     }
/*     */ 
/* 165 */     sizeColumnsToFit(-1);
/*     */   }
/*     */ 
/*     */   public void setFont(Font font) {
/* 169 */     super.setFont(font);
/* 170 */     Graphics g = getGraphics();
/* 171 */     if (g != null) {
/* 172 */       FontMetrics fm = g.getFontMetrics(font);
/* 173 */       int height = fm.getHeight();
/* 174 */       this._rowHeight = (height + height / 3);
/* 175 */       setRowHeight(this._rowHeight);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void init()
/*     */   {
/* 187 */     setRowHeight(this._rowHeight);
/* 188 */     setSelectionMode(0);
/*     */   }
/*     */ 
/*     */   protected Vector getColumnNameAndNumber()
/*     */   {
/* 193 */     Vector columnNameAndNumber = new Vector();
/* 194 */     for (int i = 0; i < this._colNames.length; i++) {
/* 195 */       columnNameAndNumber.add(i, this._colNames[i]);
/*     */     }
/* 197 */     return columnNameAndNumber;
/*     */   }
/*     */ 
/*     */   class LogTableListSelectionListener
/*     */     implements ListSelectionListener
/*     */   {
/*     */     protected JTable _table;
/*     */ 
/*     */     public LogTableListSelectionListener(JTable table)
/*     */     {
/* 212 */       this._table = table;
/*     */     }
/*     */ 
/*     */     public void valueChanged(ListSelectionEvent e)
/*     */     {
/* 217 */       if (e.getValueIsAdjusting()) {
/* 218 */         return;
/*     */       }
/*     */ 
/* 221 */       ListSelectionModel lsm = (ListSelectionModel)e.getSource();
/* 222 */       if (!lsm.isSelectionEmpty())
/*     */       {
/* 225 */         StringBuffer buf = new StringBuffer();
/* 226 */         int selectedRow = lsm.getMinSelectionIndex();
/*     */ 
/* 228 */         for (int i = 0; i < LogTable.this._numCols - 1; i++) {
/* 229 */           String value = "";
/* 230 */           Object obj = this._table.getModel().getValueAt(selectedRow, i);
/* 231 */           if (obj != null) {
/* 232 */             value = obj.toString();
/*     */           }
/*     */ 
/* 235 */           buf.append(LogTable.this._colNames[i] + ":");
/* 236 */           buf.append("\t");
/*     */ 
/* 238 */           if ((i == LogTable.this._colThread) || (i == LogTable.this._colMessage) || (i == LogTable.this._colLevel)) {
/* 239 */             buf.append("\t");
/*     */           }
/*     */ 
/* 242 */           if ((i == LogTable.this._colDate) || (i == LogTable.this._colNDC)) {
/* 243 */             buf.append("\t\t");
/*     */           }
/*     */ 
/* 251 */           buf.append(value);
/* 252 */           buf.append("\n");
/*     */         }
/* 254 */         buf.append(LogTable.this._colNames[(LogTable.this._numCols - 1)] + ":\n");
/* 255 */         Object obj = this._table.getModel().getValueAt(selectedRow, LogTable.this._numCols - 1);
/* 256 */         if (obj != null) {
/* 257 */           buf.append(obj.toString());
/*     */         }
/*     */ 
/* 260 */         LogTable.this._detailTextArea.setText(buf.toString());
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.LogTable
 * JD-Core Version:    0.6.0
 */
/*     */ package org.apache.log4j.lf5.viewer;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ import org.apache.log4j.lf5.LogRecord;
/*     */ import org.apache.log4j.lf5.LogRecordFilter;
/*     */ import org.apache.log4j.lf5.PassingLogRecordFilter;
/*     */ 
/*     */ public class FilteredLogTableModel extends AbstractTableModel
/*     */ {
/*  48 */   protected LogRecordFilter _filter = new PassingLogRecordFilter();
/*  49 */   protected List _allRecords = new ArrayList();
/*     */   protected List _filteredRecords;
/*  51 */   protected int _maxNumberOfLogRecords = 5000;
/*  52 */   protected String[] _colNames = { "Date", "Thread", "Message #", "Level", "NDC", "Category", "Message", "Location", "Thrown" };
/*     */ 
/*     */   public void setLogRecordFilter(LogRecordFilter filter)
/*     */   {
/*  79 */     this._filter = filter;
/*     */   }
/*     */ 
/*     */   public LogRecordFilter getLogRecordFilter() {
/*  83 */     return this._filter;
/*     */   }
/*     */ 
/*     */   public String getColumnName(int i) {
/*  87 */     return this._colNames[i];
/*     */   }
/*     */ 
/*     */   public int getColumnCount() {
/*  91 */     return this._colNames.length;
/*     */   }
/*     */ 
/*     */   public int getRowCount() {
/*  95 */     return getFilteredRecords().size();
/*     */   }
/*     */ 
/*     */   public int getTotalRowCount() {
/*  99 */     return this._allRecords.size();
/*     */   }
/*     */ 
/*     */   public Object getValueAt(int row, int col) {
/* 103 */     LogRecord record = getFilteredRecord(row);
/* 104 */     return getColumn(col, record);
/*     */   }
/*     */ 
/*     */   public void setMaxNumberOfLogRecords(int maxNumRecords) {
/* 108 */     if (maxNumRecords > 0)
/* 109 */       this._maxNumberOfLogRecords = maxNumRecords;
/*     */   }
/*     */ 
/*     */   public synchronized boolean addLogRecord(LogRecord record)
/*     */   {
/* 116 */     this._allRecords.add(record);
/*     */ 
/* 118 */     if (!this._filter.passes(record)) {
/* 119 */       return false;
/*     */     }
/* 121 */     getFilteredRecords().add(record);
/* 122 */     fireTableRowsInserted(getRowCount(), getRowCount());
/* 123 */     trimRecords();
/* 124 */     return true;
/*     */   }
/*     */ 
/*     */   public synchronized void refresh()
/*     */   {
/* 132 */     this._filteredRecords = createFilteredRecordsList();
/* 133 */     fireTableDataChanged();
/*     */   }
/*     */ 
/*     */   public synchronized void fastRefresh() {
/* 137 */     this._filteredRecords.remove(0);
/* 138 */     fireTableRowsDeleted(0, 0);
/*     */   }
/*     */ 
/*     */   public synchronized void clear()
/*     */   {
/* 146 */     this._allRecords.clear();
/* 147 */     this._filteredRecords.clear();
/* 148 */     fireTableDataChanged();
/*     */   }
/*     */ 
/*     */   protected List getFilteredRecords()
/*     */   {
/* 156 */     if (this._filteredRecords == null) {
/* 157 */       refresh();
/*     */     }
/* 159 */     return this._filteredRecords;
/*     */   }
/*     */ 
/*     */   protected List createFilteredRecordsList() {
/* 163 */     List result = new ArrayList();
/* 164 */     Iterator records = this._allRecords.iterator();
/*     */ 
/* 166 */     while (records.hasNext()) {
/* 167 */       LogRecord current = (LogRecord)records.next();
/* 168 */       if (this._filter.passes(current)) {
/* 169 */         result.add(current);
/*     */       }
/*     */     }
/* 172 */     return result;
/*     */   }
/*     */ 
/*     */   protected LogRecord getFilteredRecord(int row) {
/* 176 */     List records = getFilteredRecords();
/* 177 */     int size = records.size();
/* 178 */     if (row < size) {
/* 179 */       return (LogRecord)records.get(row);
/*     */     }
/*     */ 
/* 185 */     return (LogRecord)records.get(size - 1);
/*     */   }
/*     */ 
/*     */   protected Object getColumn(int col, LogRecord lr)
/*     */   {
/* 190 */     if (lr == null) {
/* 191 */       return "NULL Column";
/*     */     }
/* 193 */     String date = new Date(lr.getMillis()).toString();
/* 194 */     switch (col) {
/*     */     case 0:
/* 196 */       return date + " (" + lr.getMillis() + ")";
/*     */     case 1:
/* 198 */       return lr.getThreadDescription();
/*     */     case 2:
/* 200 */       return new Long(lr.getSequenceNumber());
/*     */     case 3:
/* 202 */       return lr.getLevel();
/*     */     case 4:
/* 204 */       return lr.getNDC();
/*     */     case 5:
/* 206 */       return lr.getCategory();
/*     */     case 6:
/* 208 */       return lr.getMessage();
/*     */     case 7:
/* 210 */       return lr.getLocation();
/*     */     case 8:
/* 212 */       return lr.getThrownStackTrace();
/*     */     }
/* 214 */     String message = "The column number " + col + "must be between 0 and 8";
/* 215 */     throw new IllegalArgumentException(message);
/*     */   }
/*     */ 
/*     */   protected void trimRecords()
/*     */   {
/* 226 */     if (needsTrimming())
/* 227 */       trimOldestRecords();
/*     */   }
/*     */ 
/*     */   protected boolean needsTrimming()
/*     */   {
/* 232 */     return this._allRecords.size() > this._maxNumberOfLogRecords;
/*     */   }
/*     */ 
/*     */   protected void trimOldestRecords() {
/* 236 */     synchronized (this._allRecords) {
/* 237 */       int trim = numberOfRecordsToTrim();
/* 238 */       if (trim > 1) {
/* 239 */         List oldRecords = this._allRecords.subList(0, trim);
/*     */ 
/* 241 */         oldRecords.clear();
/* 242 */         refresh();
/*     */       } else {
/* 244 */         this._allRecords.remove(0);
/* 245 */         fastRefresh();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private int numberOfRecordsToTrim()
/*     */   {
/* 255 */     return this._allRecords.size() - this._maxNumberOfLogRecords;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.FilteredLogTableModel
 * JD-Core Version:    0.6.0
 */
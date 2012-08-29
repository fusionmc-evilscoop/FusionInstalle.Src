/*     */ package org.apache.log4j.lf5.viewer;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class LogTableColumn
/*     */   implements Serializable
/*     */ {
/*  35 */   public static final LogTableColumn DATE = new LogTableColumn("Date");
/*  36 */   public static final LogTableColumn THREAD = new LogTableColumn("Thread");
/*  37 */   public static final LogTableColumn MESSAGE_NUM = new LogTableColumn("Message #");
/*  38 */   public static final LogTableColumn LEVEL = new LogTableColumn("Level");
/*  39 */   public static final LogTableColumn NDC = new LogTableColumn("NDC");
/*  40 */   public static final LogTableColumn CATEGORY = new LogTableColumn("Category");
/*  41 */   public static final LogTableColumn MESSAGE = new LogTableColumn("Message");
/*  42 */   public static final LogTableColumn LOCATION = new LogTableColumn("Location");
/*  43 */   public static final LogTableColumn THROWN = new LogTableColumn("Thrown");
/*     */   protected String _label;
/*  61 */   private static LogTableColumn[] _log4JColumns = { DATE, THREAD, MESSAGE_NUM, LEVEL, NDC, CATEGORY, MESSAGE, LOCATION, THROWN };
/*     */ 
/*  64 */   private static Map _logTableColumnMap = new HashMap();
/*     */ 
/*     */   public LogTableColumn(String label)
/*     */   {
/*  73 */     this._label = label;
/*     */   }
/*     */ 
/*     */   public String getLabel()
/*     */   {
/*  84 */     return this._label;
/*     */   }
/*     */ 
/*     */   public static LogTableColumn valueOf(String column)
/*     */     throws LogTableColumnFormatException
/*     */   {
/*  97 */     LogTableColumn tableColumn = null;
/*  98 */     if (column != null) {
/*  99 */       column = column.trim();
/* 100 */       tableColumn = (LogTableColumn)_logTableColumnMap.get(column);
/*     */     }
/*     */ 
/* 103 */     if (tableColumn == null) {
/* 104 */       StringBuffer buf = new StringBuffer();
/* 105 */       buf.append("Error while trying to parse (" + column + ") into");
/* 106 */       buf.append(" a LogTableColumn.");
/* 107 */       throw new LogTableColumnFormatException(buf.toString());
/*     */     }
/* 109 */     return tableColumn;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/* 114 */     boolean equals = false;
/*     */ 
/* 116 */     if (((o instanceof LogTableColumn)) && 
/* 117 */       (getLabel() == ((LogTableColumn)o).getLabel()))
/*     */     {
/* 119 */       equals = true;
/*     */     }
/*     */ 
/* 123 */     return equals;
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/* 127 */     return this._label.hashCode();
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 131 */     return this._label;
/*     */   }
/*     */ 
/*     */   public static List getLogTableColumns()
/*     */   {
/* 139 */     return Arrays.asList(_log4JColumns);
/*     */   }
/*     */ 
/*     */   public static LogTableColumn[] getLogTableColumnArray() {
/* 143 */     return _log4JColumns;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  66 */     for (int i = 0; i < _log4JColumns.length; i++)
/*  67 */       _logTableColumnMap.put(_log4JColumns[i].getLabel(), _log4JColumns[i]);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.LogTableColumn
 * JD-Core Version:    0.6.0
 */
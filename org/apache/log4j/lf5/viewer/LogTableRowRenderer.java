/*    */ package org.apache.log4j.lf5.viewer;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.util.Map;
/*    */ import javax.swing.JTable;
/*    */ import javax.swing.table.DefaultTableCellRenderer;
/*    */ import org.apache.log4j.lf5.LogLevel;
/*    */ import org.apache.log4j.lf5.LogRecord;
/*    */ 
/*    */ public class LogTableRowRenderer extends DefaultTableCellRenderer
/*    */ {
/* 43 */   protected boolean _highlightFatal = true;
/* 44 */   protected Color _color = new Color(230, 230, 230);
/*    */ 
/*    */   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col)
/*    */   {
/* 65 */     if (row % 2 == 0)
/* 66 */       setBackground(this._color);
/*    */     else {
/* 68 */       setBackground(Color.white);
/*    */     }
/*    */ 
/* 71 */     FilteredLogTableModel model = (FilteredLogTableModel)table.getModel();
/* 72 */     LogRecord record = model.getFilteredRecord(row);
/*    */ 
/* 74 */     setForeground(getLogLevelColor(record.getLevel()));
/*    */ 
/* 76 */     return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
/*    */   }
/*    */ 
/*    */   protected Color getLogLevelColor(LogLevel level)
/*    */   {
/* 88 */     return (Color)LogLevel.getLogLevelColorMap().get(level);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.LogTableRowRenderer
 * JD-Core Version:    0.6.0
 */
/*    */ package org.apache.log4j.lf5.viewer;
/*    */ 
/*    */ import javax.swing.table.DefaultTableModel;
/*    */ 
/*    */ public class LogTableModel extends DefaultTableModel
/*    */ {
/*    */   public LogTableModel(Object[] colNames, int numRows)
/*    */   {
/* 47 */     super(colNames, numRows);
/*    */   }
/*    */ 
/*    */   public boolean isCellEditable(int row, int column)
/*    */   {
/* 55 */     return false;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.LogTableModel
 * JD-Core Version:    0.6.0
 */
/*    */ package org.apache.log4j.lf5.viewer.categoryexplorer;
/*    */ 
/*    */ import java.util.Enumeration;
/*    */ import javax.swing.tree.DefaultMutableTreeNode;
/*    */ import javax.swing.tree.DefaultTreeModel;
/*    */ import org.apache.log4j.lf5.LogRecord;
/*    */ import org.apache.log4j.lf5.LogRecordFilter;
/*    */ 
/*    */ public class CategoryExplorerLogRecordFilter
/*    */   implements LogRecordFilter
/*    */ {
/*    */   protected CategoryExplorerModel _model;
/*    */ 
/*    */   public CategoryExplorerLogRecordFilter(CategoryExplorerModel model)
/*    */   {
/* 51 */     this._model = model;
/*    */   }
/*    */ 
/*    */   public boolean passes(LogRecord record)
/*    */   {
/* 65 */     CategoryPath path = new CategoryPath(record.getCategory());
/* 66 */     return this._model.isCategoryPathActive(path);
/*    */   }
/*    */ 
/*    */   public void reset()
/*    */   {
/* 73 */     resetAllNodes();
/*    */   }
/*    */ 
/*    */   protected void resetAllNodes()
/*    */   {
/* 81 */     Enumeration nodes = this._model.getRootCategoryNode().depthFirstEnumeration();
/*    */ 
/* 83 */     while (nodes.hasMoreElements()) {
/* 84 */       CategoryNode current = (CategoryNode)nodes.nextElement();
/* 85 */       current.resetNumberOfContainedRecords();
/* 86 */       this._model.nodeChanged(current);
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.categoryexplorer.CategoryExplorerLogRecordFilter
 * JD-Core Version:    0.6.0
 */
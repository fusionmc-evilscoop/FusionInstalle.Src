/*    */ package org.apache.log4j.lf5.viewer.categoryexplorer;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import javax.swing.JCheckBox;
/*    */ import javax.swing.JTree;
/*    */ 
/*    */ public class CategoryNodeEditorRenderer extends CategoryNodeRenderer
/*    */ {
/*    */   public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
/*    */   {
/* 56 */     Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
/*    */ 
/* 60 */     return c;
/*    */   }
/*    */ 
/*    */   public JCheckBox getCheckBox() {
/* 64 */     return this._checkBox;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.categoryexplorer.CategoryNodeEditorRenderer
 * JD-Core Version:    0.6.0
 */
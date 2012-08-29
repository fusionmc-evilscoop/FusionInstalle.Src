/*    */ package com.jidesoft.plaf.basic;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.io.File;
/*    */ import java.io.PrintStream;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JTree;
/*    */ import javax.swing.tree.DefaultTreeCellRenderer;
/*    */ 
/*    */ class BasicFileSystemTreeCellRenderer extends DefaultTreeCellRenderer
/*    */ {
/*    */   public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
/*    */   {
/* 15 */     if ((value instanceof BasicFileSystemTreeNode)) {
/* 16 */       BasicFileSystemTreeNode fileTreeNode = (BasicFileSystemTreeNode)value;
/* 17 */       JLabel label = (JLabel)super.getTreeCellRendererComponent(tree, fileTreeNode.getName(), sel, expanded, leaf, row, hasFocus);
/*    */       try {
/* 19 */         label.setIcon(fileTreeNode.getIcon());
/*    */       }
/*    */       catch (Exception e) {
/* 22 */         System.out.println(fileTreeNode.getFile().getAbsolutePath());
/*    */       }
/* 24 */       return label;
/*    */     }
/* 26 */     return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.BasicFileSystemTreeCellRenderer
 * JD-Core Version:    0.6.0
 */
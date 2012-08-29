/*    */ package com.jidesoft.plaf.basic;
/*    */ 
/*    */ import com.jidesoft.swing.FolderChooser;
/*    */ import javax.swing.filechooser.FileSystemView;
/*    */ import javax.swing.tree.DefaultMutableTreeNode;
/*    */ import javax.swing.tree.DefaultTreeModel;
/*    */ import javax.swing.tree.MutableTreeNode;
/*    */ import javax.swing.tree.TreePath;
/*    */ 
/*    */ class BasicFileSystemTreeModel extends DefaultTreeModel
/*    */ {
/*    */   private FileSystemView _fileSystemView;
/*    */ 
/*    */   public BasicFileSystemTreeModel(FolderChooser folderChooser)
/*    */   {
/* 20 */     super(new MyComputerTreeNode(folderChooser));
/*    */   }
/*    */ 
/*    */   public FileSystemView getFileSystemView() {
/* 24 */     if (this._fileSystemView == null) {
/* 25 */       this._fileSystemView = FileSystemView.getFileSystemView();
/*    */     }
/* 27 */     return this._fileSystemView;
/*    */   }
/*    */ 
/*    */   public Object getChild(Object parent, int index)
/*    */   {
/* 32 */     if ((parent instanceof DefaultMutableTreeNode)) {
/* 33 */       return ((DefaultMutableTreeNode)parent).getChildAt(index);
/*    */     }
/*    */ 
/* 36 */     return null;
/*    */   }
/*    */ 
/*    */   public void removePath(TreePath path, int index, Object deletedObject)
/*    */   {
/* 41 */     TreePath parentPath = path.getParentPath();
/* 42 */     Object source = parentPath.getLastPathComponent();
/* 43 */     Object[] paths = parentPath.getPath();
/* 44 */     if (((LazyMutableTreeNode)source).isLoaded()) {
/* 45 */       ((DefaultMutableTreeNode)source).remove((MutableTreeNode)deletedObject);
/*    */     }
/* 47 */     fireTreeNodesRemoved(source, paths, new int[] { index }, new Object[] { deletedObject });
/*    */   }
/*    */ 
/*    */   public void addPath(TreePath parent, int insertionIndex, Object insertedObject)
/*    */   {
/* 60 */     fireTreeNodesInserted(parent.getLastPathComponent(), parent.getPath(), new int[] { insertionIndex }, new Object[] { insertedObject });
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.BasicFileSystemTreeModel
 * JD-Core Version:    0.6.0
 */
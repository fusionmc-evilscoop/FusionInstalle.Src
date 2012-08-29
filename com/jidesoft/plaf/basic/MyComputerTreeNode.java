/*    */ package com.jidesoft.plaf.basic;
/*    */ 
/*    */ import com.jidesoft.swing.FolderChooser;
/*    */ import java.io.File;
/*    */ import java.util.Arrays;
/*    */ import javax.swing.filechooser.FileSystemView;
/*    */ 
/*    */ class MyComputerTreeNode extends LazyMutableTreeNode
/*    */ {
/*    */   private FolderChooser _folderChooser;
/*    */ 
/*    */   public MyComputerTreeNode(FolderChooser folderChooser)
/*    */   {
/* 19 */     super(folderChooser.getFileSystemView());
/* 20 */     this._folderChooser = folderChooser;
/*    */   }
/*    */ 
/*    */   protected void initChildren()
/*    */   {
/* 25 */     FileSystemView fsv = (FileSystemView)getUserObject();
/* 26 */     File[] roots = fsv.getRoots();
/* 27 */     if (roots != null) {
/* 28 */       Arrays.sort(roots);
/* 29 */       int i = 0; for (int c = roots.length; i < c; i++) {
/* 30 */         BasicFileSystemTreeNode newChild = BasicFileSystemTreeNode.createFileSystemTreeNode(roots[i], this._folderChooser);
/* 31 */         add(newChild);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 38 */     return "/";
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.MyComputerTreeNode
 * JD-Core Version:    0.6.0
 */
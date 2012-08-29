/*     */ package com.jidesoft.plaf.basic;
/*     */ 
/*     */ import com.jidesoft.swing.FolderChooser;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import com.jidesoft.swing.TreeSearchable;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.event.TreeExpansionEvent;
/*     */ import javax.swing.event.TreeExpansionListener;
/*     */ import javax.swing.event.TreeWillExpandListener;
/*     */ import javax.swing.tree.ExpandVetoException;
/*     */ import javax.swing.tree.TreePath;
/*     */ 
/*     */ class BasicFileSystemTree extends JTree
/*     */ {
/*     */   public BasicFileSystemTree(FolderChooser folderChooser)
/*     */   {
/*  23 */     super(new BasicFileSystemTreeModel(folderChooser));
/*  24 */     initComponents();
/*     */   }
/*     */ 
/*     */   protected void initComponents() {
/*  28 */     setCellRenderer(new BasicFileSystemTreeCellRenderer());
/*  29 */     setShowsRootHandles(false);
/*  30 */     setRootVisible(false);
/*  31 */     setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));
/*  32 */     setRowHeight(JideSwingUtilities.getLineHeight(this, 17));
/*  33 */     expandRow(0);
/*  34 */     FolderTreeListener treeListener = new FolderTreeListener(null);
/*  35 */     addTreeWillExpandListener(treeListener);
/*  36 */     addTreeExpansionListener(treeListener);
/*  37 */     new TreeSearchable(this)
/*     */     {
/*     */       protected String convertElementToString(Object object) {
/*  40 */         if ((object instanceof TreePath)) {
/*  41 */           Object treeNode = ((TreePath)object).getLastPathComponent();
/*  42 */           if ((treeNode instanceof BasicFileSystemTreeNode)) {
/*  43 */             return ((BasicFileSystemTreeNode)treeNode).getName();
/*     */           }
/*     */         }
/*  46 */         return super.convertElementToString(object);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public String getToolTipText(MouseEvent event)
/*     */   {
/*  88 */     TreePath path = getPathForLocation(event.getX(), event.getY());
/*  89 */     if ((path != null) && ((path.getLastPathComponent() instanceof BasicFileSystemTreeNode))) {
/*  90 */       BasicFileSystemTreeNode node = (BasicFileSystemTreeNode)path.getLastPathComponent();
/*  91 */       String typeDescription = node.getTypeDescription();
/*  92 */       if ((typeDescription == null) || (typeDescription.length() == 0)) {
/*  93 */         return node.toString();
/*     */       }
/*     */ 
/*  96 */       return node.toString() + " - " + typeDescription;
/*     */     }
/*     */ 
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */   private class FolderTreeListener
/*     */     implements TreeWillExpandListener, TreeExpansionListener
/*     */   {
/*     */     private Cursor oldCursor;
/*     */ 
/*     */     private FolderTreeListener()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void treeWillExpand(TreeExpansionEvent event)
/*     */       throws ExpandVetoException
/*     */     {
/*  59 */       Window window = SwingUtilities.getWindowAncestor(BasicFileSystemTree.this);
/*  60 */       if (window != null) {
/*  61 */         this.oldCursor = window.getCursor();
/*  62 */         window.setCursor(Cursor.getPredefinedCursor(3));
/*     */       }
/*     */     }
/*     */ 
/*     */     public void treeWillCollapse(TreeExpansionEvent event)
/*     */       throws ExpandVetoException
/*     */     {
/*     */     }
/*     */ 
/*     */     public void treeExpanded(TreeExpansionEvent event)
/*     */     {
/*  74 */       Window window = SwingUtilities.getWindowAncestor(BasicFileSystemTree.this);
/*  75 */       if (window != null) {
/*  76 */         window.setCursor(this.oldCursor != null ? this.oldCursor : Cursor.getDefaultCursor());
/*     */       }
/*  78 */       this.oldCursor = null;
/*     */     }
/*     */ 
/*     */     public void treeCollapsed(TreeExpansionEvent event)
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.BasicFileSystemTree
 * JD-Core Version:    0.6.0
 */
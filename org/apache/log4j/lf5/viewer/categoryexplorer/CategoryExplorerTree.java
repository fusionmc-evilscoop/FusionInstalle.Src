/*     */ package org.apache.log4j.lf5.viewer.categoryexplorer;
/*     */ 
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.event.TreeModelEvent;
/*     */ import javax.swing.tree.DefaultMutableTreeNode;
/*     */ import javax.swing.tree.DefaultTreeModel;
/*     */ import javax.swing.tree.TreePath;
/*     */ 
/*     */ public class CategoryExplorerTree extends JTree
/*     */ {
/*     */   protected CategoryExplorerModel _model;
/*  43 */   protected boolean _rootAlreadyExpanded = false;
/*     */ 
/*     */   public CategoryExplorerTree(CategoryExplorerModel model)
/*     */   {
/*  57 */     super(model);
/*     */ 
/*  59 */     this._model = model;
/*  60 */     init();
/*     */   }
/*     */ 
/*     */   public CategoryExplorerTree()
/*     */   {
/*  69 */     CategoryNode rootNode = new CategoryNode("Categories");
/*     */ 
/*  71 */     this._model = new CategoryExplorerModel(rootNode);
/*     */ 
/*  73 */     setModel(this._model);
/*     */ 
/*  75 */     init();
/*     */   }
/*     */ 
/*     */   public CategoryExplorerModel getExplorerModel()
/*     */   {
/*  83 */     return this._model;
/*     */   }
/*     */ 
/*     */   public String getToolTipText(MouseEvent e)
/*     */   {
/*     */     try {
/*  89 */       return super.getToolTipText(e); } catch (Exception ex) {
/*     */     }
/*  91 */     return "";
/*     */   }
/*     */ 
/*     */   protected void init()
/*     */   {
/* 102 */     putClientProperty("JTree.lineStyle", "Angled");
/*     */ 
/* 106 */     CategoryNodeRenderer renderer = new CategoryNodeRenderer();
/* 107 */     setEditable(true);
/* 108 */     setCellRenderer(renderer);
/*     */ 
/* 110 */     CategoryNodeEditor editor = new CategoryNodeEditor(this._model);
/*     */ 
/* 112 */     setCellEditor(new CategoryImmediateEditor(this, new CategoryNodeRenderer(), editor));
/*     */ 
/* 115 */     setShowsRootHandles(true);
/*     */ 
/* 117 */     setToolTipText("");
/*     */ 
/* 119 */     ensureRootExpansion();
/*     */   }
/*     */ 
/*     */   protected void expandRootNode()
/*     */   {
/* 124 */     if (this._rootAlreadyExpanded) {
/* 125 */       return;
/*     */     }
/* 127 */     this._rootAlreadyExpanded = true;
/* 128 */     TreePath path = new TreePath(this._model.getRootCategoryNode().getPath());
/* 129 */     expandPath(path);
/*     */   }
/*     */ 
/*     */   protected void ensureRootExpansion() {
/* 133 */     this._model.addTreeModelListener(new TreeModelAdapter() {
/*     */       public void treeNodesInserted(TreeModelEvent e) {
/* 135 */         CategoryExplorerTree.this.expandRootNode();
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.categoryexplorer.CategoryExplorerTree
 * JD-Core Version:    0.6.0
 */
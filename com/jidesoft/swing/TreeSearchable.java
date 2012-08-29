/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.event.TreeModelEvent;
/*     */ import javax.swing.event.TreeModelListener;
/*     */ import javax.swing.tree.TreeModel;
/*     */ import javax.swing.tree.TreePath;
/*     */ 
/*     */ public class TreeSearchable extends Searchable
/*     */   implements TreeModelListener, PropertyChangeListener
/*     */ {
/*  54 */   private boolean _recursive = false;
/*     */   private transient List<TreePath> _treePathes;
/*     */ 
/*     */   public TreeSearchable(JTree tree)
/*     */   {
/*  58 */     super(tree);
/*  59 */     if (tree.getModel() != null) {
/*  60 */       tree.getModel().addTreeModelListener(this);
/*     */     }
/*     */ 
/*  63 */     tree.addPropertyChangeListener("model", this);
/*     */   }
/*     */ 
/*     */   public boolean isRecursive()
/*     */   {
/*  72 */     return this._recursive;
/*     */   }
/*     */ 
/*     */   public void setRecursive(boolean recursive)
/*     */   {
/*  86 */     this._recursive = recursive;
/*  87 */     resetTreePathes();
/*     */   }
/*     */ 
/*     */   public void uninstallListeners()
/*     */   {
/*  92 */     super.uninstallListeners();
/*  93 */     if (((this._component instanceof JTree)) && 
/*  94 */       (((JTree)this._component).getModel() != null)) {
/*  95 */       ((JTree)this._component).getModel().removeTreeModelListener(this);
/*     */     }
/*     */ 
/*  98 */     this._component.removePropertyChangeListener("model", this);
/*     */   }
/*     */ 
/*     */   protected void setSelectedIndex(int index, boolean incremental)
/*     */   {
/* 103 */     if (!isRecursive()) {
/* 104 */       if (incremental) {
/* 105 */         ((JTree)this._component).addSelectionInterval(index, index);
/*     */       }
/*     */       else {
/* 108 */         ((JTree)this._component).setSelectionRow(index);
/*     */       }
/* 110 */       ((JTree)this._component).scrollRowToVisible(index);
/*     */     }
/*     */     else {
/* 113 */       Object elementAt = getElementAt(index);
/* 114 */       if ((elementAt instanceof TreePath)) {
/* 115 */         TreePath path = (TreePath)elementAt;
/* 116 */         if (incremental) {
/* 117 */           ((JTree)this._component).addSelectionPath(path);
/*     */         }
/*     */         else {
/* 120 */           ((JTree)this._component).setSelectionPath(path);
/*     */         }
/* 122 */         ((JTree)this._component).scrollPathToVisible(path);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected int getSelectedIndex()
/*     */   {
/* 129 */     if (!isRecursive()) {
/* 130 */       int[] ai = ((JTree)this._component).getSelectionRows();
/* 131 */       return (ai != null) && (ai.length != 0) ? ai[0] : -1;
/*     */     }
/*     */ 
/* 134 */     TreePath[] treePaths = ((JTree)this._component).getSelectionPaths();
/* 135 */     if ((treePaths != null) && (treePaths.length > 0)) {
/* 136 */       return getTreePathes().indexOf(treePaths[0]);
/*     */     }
/*     */ 
/* 139 */     return -1;
/*     */   }
/*     */ 
/*     */   protected Object getElementAt(int index)
/*     */   {
/* 145 */     if (index == -1) {
/* 146 */       return null;
/*     */     }
/* 148 */     if (!isRecursive()) {
/* 149 */       return ((JTree)this._component).getPathForRow(index);
/*     */     }
/*     */ 
/* 152 */     return getTreePathes().get(index);
/*     */   }
/*     */ 
/*     */   protected int getElementCount()
/*     */   {
/* 158 */     if (!isRecursive()) {
/* 159 */       return ((JTree)this._component).getRowCount();
/*     */     }
/*     */ 
/* 162 */     return getTreePathes().size();
/*     */   }
/*     */ 
/*     */   protected void populateTreePaths()
/*     */   {
/* 172 */     this._treePathes = new ArrayList();
/* 173 */     Object root = ((JTree)this._component).getModel().getRoot();
/* 174 */     populateTreePaths0(root, new TreePath(root), ((JTree)this._component).getModel());
/*     */   }
/*     */ 
/*     */   private void populateTreePaths0(Object node, TreePath path, TreeModel model) {
/* 178 */     if ((((JTree)this._component).isRootVisible()) || (path.getLastPathComponent() != ((JTree)this._component).getModel().getRoot()))
/*     */     {
/* 180 */       this._treePathes.add(path);
/*     */     }
/* 182 */     for (int i = 0; i < model.getChildCount(node); i++) {
/* 183 */       Object childNode = model.getChild(node, i);
/* 184 */       populateTreePaths0(childNode, path.pathByAddingChild(childNode), model);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void resetTreePathes()
/*     */   {
/* 194 */     this._treePathes = null;
/*     */   }
/*     */ 
/*     */   protected List<TreePath> getTreePathes()
/*     */   {
/* 205 */     if (this._treePathes == null) {
/* 206 */       populateTreePaths();
/*     */     }
/* 208 */     return this._treePathes;
/*     */   }
/*     */ 
/*     */   protected String convertElementToString(Object object)
/*     */   {
/* 220 */     if ((object instanceof TreePath)) {
/* 221 */       Object treeNode = ((TreePath)object).getLastPathComponent();
/* 222 */       return treeNode.toString();
/*     */     }
/* 224 */     if (object != null) {
/* 225 */       return object.toString();
/*     */     }
/*     */ 
/* 228 */     return "";
/*     */   }
/*     */ 
/*     */   public void treeNodesChanged(TreeModelEvent e)
/*     */   {
/* 233 */     if (!isProcessModelChangeEvent()) {
/* 234 */       return;
/*     */     }
/* 236 */     hidePopup();
/* 237 */     resetTreePathes();
/*     */   }
/*     */ 
/*     */   public void treeNodesInserted(TreeModelEvent e) {
/* 241 */     if (!isProcessModelChangeEvent()) {
/* 242 */       return;
/*     */     }
/* 244 */     hidePopup();
/* 245 */     resetTreePathes();
/*     */   }
/*     */ 
/*     */   public void treeNodesRemoved(TreeModelEvent e) {
/* 249 */     if (!isProcessModelChangeEvent()) {
/* 250 */       return;
/*     */     }
/* 252 */     hidePopup();
/* 253 */     resetTreePathes();
/*     */   }
/*     */ 
/*     */   public void treeStructureChanged(TreeModelEvent e) {
/* 257 */     if (!isProcessModelChangeEvent()) {
/* 258 */       return;
/*     */     }
/* 260 */     hidePopup();
/* 261 */     resetTreePathes();
/*     */   }
/*     */ 
/*     */   public void propertyChange(PropertyChangeEvent evt) {
/* 265 */     if ("model".equals(evt.getPropertyName())) {
/* 266 */       hidePopup();
/*     */ 
/* 268 */       if ((evt.getOldValue() instanceof TreeModel)) {
/* 269 */         ((TreeModel)evt.getOldValue()).removeTreeModelListener(this);
/*     */       }
/*     */ 
/* 272 */       if ((evt.getNewValue() instanceof TreeModel)) {
/* 273 */         ((TreeModel)evt.getNewValue()).addTreeModelListener(this);
/*     */       }
/*     */ 
/* 276 */       resetTreePathes();
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.TreeSearchable
 * JD-Core Version:    0.6.0
 */
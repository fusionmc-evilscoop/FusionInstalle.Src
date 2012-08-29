/*     */ package org.apache.log4j.lf5.viewer.categoryexplorer;
/*     */ 
/*     */ import java.awt.AWTEventMulticaster;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.Enumeration;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.tree.DefaultMutableTreeNode;
/*     */ import javax.swing.tree.DefaultTreeModel;
/*     */ import javax.swing.tree.TreeNode;
/*     */ import javax.swing.tree.TreePath;
/*     */ import org.apache.log4j.lf5.LogRecord;
/*     */ 
/*     */ public class CategoryExplorerModel extends DefaultTreeModel
/*     */ {
/*  49 */   protected boolean _renderFatal = true;
/*  50 */   protected ActionListener _listener = null;
/*  51 */   protected ActionEvent _event = new ActionEvent(this, 1001, "Nodes Selection changed");
/*     */ 
/*     */   public CategoryExplorerModel(CategoryNode node)
/*     */   {
/*  64 */     super(node);
/*     */   }
/*     */ 
/*     */   public void addLogRecord(LogRecord lr)
/*     */   {
/*  71 */     CategoryPath path = new CategoryPath(lr.getCategory());
/*  72 */     addCategory(path);
/*  73 */     CategoryNode node = getCategoryNode(path);
/*  74 */     node.addRecord();
/*  75 */     if ((this._renderFatal) && (lr.isFatal())) {
/*  76 */       TreeNode[] nodes = getPathToRoot(node);
/*  77 */       int len = nodes.length;
/*     */ 
/*  82 */       for (int i = 1; i < len - 1; i++) {
/*  83 */         CategoryNode parent = (CategoryNode)nodes[i];
/*  84 */         parent.setHasFatalChildren(true);
/*  85 */         nodeChanged(parent);
/*     */       }
/*  87 */       node.setHasFatalRecords(true);
/*  88 */       nodeChanged(node);
/*     */     }
/*     */   }
/*     */ 
/*     */   public CategoryNode getRootCategoryNode() {
/*  93 */     return (CategoryNode)getRoot();
/*     */   }
/*     */ 
/*     */   public CategoryNode getCategoryNode(String category) {
/*  97 */     CategoryPath path = new CategoryPath(category);
/*  98 */     return getCategoryNode(path);
/*     */   }
/*     */ 
/*     */   public CategoryNode getCategoryNode(CategoryPath path)
/*     */   {
/* 105 */     CategoryNode root = (CategoryNode)getRoot();
/* 106 */     CategoryNode parent = root;
/*     */ 
/* 108 */     for (int i = 0; i < path.size(); i++) {
/* 109 */       CategoryElement element = path.categoryElementAt(i);
/*     */ 
/* 112 */       Enumeration children = parent.children();
/*     */ 
/* 114 */       boolean categoryAlreadyExists = false;
/* 115 */       while (children.hasMoreElements()) {
/* 116 */         CategoryNode node = (CategoryNode)children.nextElement();
/* 117 */         String title = node.getTitle().toLowerCase();
/*     */ 
/* 119 */         String pathLC = element.getTitle().toLowerCase();
/* 120 */         if (title.equals(pathLC)) {
/* 121 */           categoryAlreadyExists = true;
/*     */ 
/* 123 */           parent = node;
/* 124 */           break;
/*     */         }
/*     */       }
/*     */ 
/* 128 */       if (!categoryAlreadyExists) {
/* 129 */         return null;
/*     */       }
/*     */     }
/*     */ 
/* 133 */     return parent;
/*     */   }
/*     */ 
/*     */   public boolean isCategoryPathActive(CategoryPath path)
/*     */   {
/* 141 */     CategoryNode root = (CategoryNode)getRoot();
/* 142 */     CategoryNode parent = root;
/* 143 */     boolean active = false;
/*     */ 
/* 145 */     for (int i = 0; i < path.size(); i++) {
/* 146 */       CategoryElement element = path.categoryElementAt(i);
/*     */ 
/* 149 */       Enumeration children = parent.children();
/*     */ 
/* 151 */       boolean categoryAlreadyExists = false;
/* 152 */       active = false;
/*     */ 
/* 154 */       while (children.hasMoreElements()) {
/* 155 */         CategoryNode node = (CategoryNode)children.nextElement();
/* 156 */         String title = node.getTitle().toLowerCase();
/*     */ 
/* 158 */         String pathLC = element.getTitle().toLowerCase();
/* 159 */         if (title.equals(pathLC)) {
/* 160 */           categoryAlreadyExists = true;
/*     */ 
/* 162 */           parent = node;
/*     */ 
/* 164 */           if (!parent.isSelected()) break;
/* 165 */           active = true; break;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 172 */       if ((!active) || (!categoryAlreadyExists)) {
/* 173 */         return false;
/*     */       }
/*     */     }
/*     */ 
/* 177 */     return active;
/*     */   }
/*     */ 
/*     */   public CategoryNode addCategory(CategoryPath path)
/*     */   {
/* 189 */     CategoryNode root = (CategoryNode)getRoot();
/* 190 */     CategoryNode parent = root;
/*     */ 
/* 192 */     for (int i = 0; i < path.size(); i++) {
/* 193 */       CategoryElement element = path.categoryElementAt(i);
/*     */ 
/* 196 */       Enumeration children = parent.children();
/*     */ 
/* 198 */       boolean categoryAlreadyExists = false;
/* 199 */       while (children.hasMoreElements()) {
/* 200 */         CategoryNode node = (CategoryNode)children.nextElement();
/* 201 */         String title = node.getTitle().toLowerCase();
/*     */ 
/* 203 */         String pathLC = element.getTitle().toLowerCase();
/* 204 */         if (title.equals(pathLC)) {
/* 205 */           categoryAlreadyExists = true;
/*     */ 
/* 207 */           parent = node;
/* 208 */           break;
/*     */         }
/*     */       }
/*     */ 
/* 212 */       if (categoryAlreadyExists)
/*     */         continue;
/* 214 */       CategoryNode newNode = new CategoryNode(element.getTitle());
/*     */ 
/* 222 */       insertNodeInto(newNode, parent, parent.getChildCount());
/* 223 */       refresh(newNode);
/*     */ 
/* 226 */       parent = newNode;
/*     */     }
/*     */ 
/* 231 */     return parent;
/*     */   }
/*     */ 
/*     */   public void update(CategoryNode node, boolean selected) {
/* 235 */     if (node.isSelected() == selected) {
/* 236 */       return;
/*     */     }
/*     */ 
/* 239 */     if (selected)
/* 240 */       setParentSelection(node, true);
/*     */     else
/* 242 */       setDescendantSelection(node, false);
/*     */   }
/*     */ 
/*     */   public void setDescendantSelection(CategoryNode node, boolean selected)
/*     */   {
/* 247 */     Enumeration descendants = node.depthFirstEnumeration();
/*     */ 
/* 249 */     while (descendants.hasMoreElements()) {
/* 250 */       CategoryNode current = (CategoryNode)descendants.nextElement();
/*     */ 
/* 252 */       if (current.isSelected() != selected) {
/* 253 */         current.setSelected(selected);
/* 254 */         nodeChanged(current);
/*     */       }
/*     */     }
/* 257 */     notifyActionListeners();
/*     */   }
/*     */ 
/*     */   public void setParentSelection(CategoryNode node, boolean selected) {
/* 261 */     TreeNode[] nodes = getPathToRoot(node);
/* 262 */     int len = nodes.length;
/*     */ 
/* 267 */     for (int i = 1; i < len; i++) {
/* 268 */       CategoryNode parent = (CategoryNode)nodes[i];
/* 269 */       if (parent.isSelected() != selected) {
/* 270 */         parent.setSelected(selected);
/* 271 */         nodeChanged(parent);
/*     */       }
/*     */     }
/* 274 */     notifyActionListeners();
/*     */   }
/*     */ 
/*     */   public synchronized void addActionListener(ActionListener l)
/*     */   {
/* 279 */     this._listener = AWTEventMulticaster.add(this._listener, l);
/*     */   }
/*     */ 
/*     */   public synchronized void removeActionListener(ActionListener l) {
/* 283 */     this._listener = AWTEventMulticaster.remove(this._listener, l);
/*     */   }
/*     */ 
/*     */   public void resetAllNodeCounts() {
/* 287 */     Enumeration nodes = getRootCategoryNode().depthFirstEnumeration();
/*     */ 
/* 289 */     while (nodes.hasMoreElements()) {
/* 290 */       CategoryNode current = (CategoryNode)nodes.nextElement();
/* 291 */       current.resetNumberOfContainedRecords();
/* 292 */       nodeChanged(current);
/*     */     }
/*     */   }
/*     */ 
/*     */   public TreePath getTreePathToRoot(CategoryNode node)
/*     */   {
/* 303 */     if (node == null) {
/* 304 */       return null;
/*     */     }
/* 306 */     return new TreePath(getPathToRoot(node));
/*     */   }
/*     */ 
/*     */   protected void notifyActionListeners()
/*     */   {
/* 313 */     if (this._listener != null)
/* 314 */       this._listener.actionPerformed(this._event);
/*     */   }
/*     */ 
/*     */   protected void refresh(CategoryNode node)
/*     */   {
/* 322 */     SwingUtilities.invokeLater(new Runnable(node) { private final CategoryNode val$node;
/*     */ 
/* 324 */       public void run() { CategoryExplorerModel.this.nodeChanged(this.val$node);
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.categoryexplorer.CategoryExplorerModel
 * JD-Core Version:    0.6.0
 */
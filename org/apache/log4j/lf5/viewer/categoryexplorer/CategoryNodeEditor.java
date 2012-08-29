/*     */ package org.apache.log4j.lf5.viewer.categoryexplorer;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.InputEvent;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.tree.DefaultMutableTreeNode;
/*     */ import javax.swing.tree.DefaultTreeModel;
/*     */ import javax.swing.tree.TreePath;
/*     */ 
/*     */ public class CategoryNodeEditor extends CategoryAbstractCellEditor
/*     */ {
/*     */   protected CategoryNodeEditorRenderer _renderer;
/*     */   protected CategoryNode _lastEditedNode;
/*     */   protected JCheckBox _checkBox;
/*     */   protected CategoryExplorerModel _categoryModel;
/*     */   protected JTree _tree;
/*     */ 
/*     */   public CategoryNodeEditor(CategoryExplorerModel model)
/*     */   {
/*  60 */     this._renderer = new CategoryNodeEditorRenderer();
/*  61 */     this._checkBox = this._renderer.getCheckBox();
/*  62 */     this._categoryModel = model;
/*     */ 
/*  64 */     this._checkBox.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  66 */         CategoryNodeEditor.this._categoryModel.update(CategoryNodeEditor.this._lastEditedNode, CategoryNodeEditor.this._checkBox.isSelected());
/*  67 */         CategoryNodeEditor.this.stopCellEditing();
/*     */       }
/*     */     });
/*  71 */     this._renderer.addMouseListener(new MouseAdapter() {
/*     */       public void mousePressed(MouseEvent e) {
/*  73 */         if ((e.getModifiers() & 0x4) != 0) {
/*  74 */           CategoryNodeEditor.this.showPopup(CategoryNodeEditor.this._lastEditedNode, e.getX(), e.getY());
/*     */         }
/*  76 */         CategoryNodeEditor.this.stopCellEditing();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public Component getTreeCellEditorComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row)
/*     */   {
/*  88 */     this._lastEditedNode = ((CategoryNode)value);
/*  89 */     this._tree = tree;
/*     */ 
/*  91 */     return this._renderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, true);
/*     */   }
/*     */ 
/*     */   public Object getCellEditorValue()
/*     */   {
/*  98 */     return this._lastEditedNode.getUserObject();
/*     */   }
/*     */ 
/*     */   protected JMenuItem createPropertiesMenuItem(CategoryNode node)
/*     */   {
/* 105 */     JMenuItem result = new JMenuItem("Properties");
/* 106 */     result.addActionListener(new ActionListener(node) { private final CategoryNode val$node;
/*     */ 
/* 108 */       public void actionPerformed(ActionEvent e) { CategoryNodeEditor.this.showPropertiesDialog(this.val$node);
/*     */       }
/*     */     });
/* 111 */     return result;
/*     */   }
/*     */ 
/*     */   protected void showPropertiesDialog(CategoryNode node) {
/* 115 */     JOptionPane.showMessageDialog(this._tree, getDisplayedProperties(node), "Category Properties: " + node.getTitle(), -1);
/*     */   }
/*     */ 
/*     */   protected Object getDisplayedProperties(CategoryNode node)
/*     */   {
/* 124 */     ArrayList result = new ArrayList();
/* 125 */     result.add("Category: " + node.getTitle());
/* 126 */     if (node.hasFatalRecords()) {
/* 127 */       result.add("Contains at least one fatal LogRecord.");
/*     */     }
/* 129 */     if (node.hasFatalChildren()) {
/* 130 */       result.add("Contains descendants with a fatal LogRecord.");
/*     */     }
/* 132 */     result.add("LogRecords in this category alone: " + node.getNumberOfContainedRecords());
/*     */ 
/* 134 */     result.add("LogRecords in descendant categories: " + node.getNumberOfRecordsFromChildren());
/*     */ 
/* 136 */     result.add("LogRecords in this category including descendants: " + node.getTotalNumberOfRecords());
/*     */ 
/* 138 */     return result.toArray();
/*     */   }
/*     */ 
/*     */   protected void showPopup(CategoryNode node, int x, int y) {
/* 142 */     JPopupMenu popup = new JPopupMenu();
/* 143 */     popup.setSize(150, 400);
/*     */ 
/* 147 */     if (node.getParent() == null) {
/* 148 */       popup.add(createRemoveMenuItem());
/* 149 */       popup.addSeparator();
/*     */     }
/* 151 */     popup.add(createSelectDescendantsMenuItem(node));
/* 152 */     popup.add(createUnselectDescendantsMenuItem(node));
/* 153 */     popup.addSeparator();
/* 154 */     popup.add(createExpandMenuItem(node));
/* 155 */     popup.add(createCollapseMenuItem(node));
/* 156 */     popup.addSeparator();
/* 157 */     popup.add(createPropertiesMenuItem(node));
/* 158 */     popup.show(this._renderer, x, y);
/*     */   }
/*     */ 
/*     */   protected JMenuItem createSelectDescendantsMenuItem(CategoryNode node) {
/* 162 */     JMenuItem selectDescendants = new JMenuItem("Select All Descendant Categories");
/*     */ 
/* 164 */     selectDescendants.addActionListener(new ActionListener(node) { private final CategoryNode val$node;
/*     */ 
/* 167 */       public void actionPerformed(ActionEvent e) { CategoryNodeEditor.this._categoryModel.setDescendantSelection(this.val$node, true);
/*     */       }
/*     */     });
/* 171 */     return selectDescendants;
/*     */   }
/*     */ 
/*     */   protected JMenuItem createUnselectDescendantsMenuItem(CategoryNode node) {
/* 175 */     JMenuItem unselectDescendants = new JMenuItem("Deselect All Descendant Categories");
/*     */ 
/* 177 */     unselectDescendants.addActionListener(new ActionListener(node) {
/*     */       private final CategoryNode val$node;
/*     */ 
/* 181 */       public void actionPerformed(ActionEvent e) { CategoryNodeEditor.this._categoryModel.setDescendantSelection(this.val$node, false);
/*     */       }
/*     */     });
/* 186 */     return unselectDescendants;
/*     */   }
/*     */ 
/*     */   protected JMenuItem createExpandMenuItem(CategoryNode node) {
/* 190 */     JMenuItem result = new JMenuItem("Expand All Descendant Categories");
/* 191 */     result.addActionListener(new ActionListener(node) { private final CategoryNode val$node;
/*     */ 
/* 193 */       public void actionPerformed(ActionEvent e) { CategoryNodeEditor.this.expandDescendants(this.val$node);
/*     */       }
/*     */     });
/* 196 */     return result;
/*     */   }
/*     */ 
/*     */   protected JMenuItem createCollapseMenuItem(CategoryNode node) {
/* 200 */     JMenuItem result = new JMenuItem("Collapse All Descendant Categories");
/* 201 */     result.addActionListener(new ActionListener(node) { private final CategoryNode val$node;
/*     */ 
/* 203 */       public void actionPerformed(ActionEvent e) { CategoryNodeEditor.this.collapseDescendants(this.val$node);
/*     */       }
/*     */     });
/* 206 */     return result;
/*     */   }
/*     */ 
/*     */   protected JMenuItem createRemoveMenuItem()
/*     */   {
/* 219 */     JMenuItem result = new JMenuItem("Remove All Empty Categories");
/* 220 */     result.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 222 */         while (CategoryNodeEditor.this.removeUnusedNodes() > 0);
/*     */       }
/*     */     });
/* 225 */     return result;
/*     */   }
/*     */ 
/*     */   protected void expandDescendants(CategoryNode node) {
/* 229 */     Enumeration descendants = node.depthFirstEnumeration();
/*     */ 
/* 231 */     while (descendants.hasMoreElements()) {
/* 232 */       CategoryNode current = (CategoryNode)descendants.nextElement();
/* 233 */       expand(current);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void collapseDescendants(CategoryNode node) {
/* 238 */     Enumeration descendants = node.depthFirstEnumeration();
/*     */ 
/* 240 */     while (descendants.hasMoreElements()) {
/* 241 */       CategoryNode current = (CategoryNode)descendants.nextElement();
/* 242 */       collapse(current);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected int removeUnusedNodes()
/*     */   {
/* 250 */     int count = 0;
/* 251 */     CategoryNode root = this._categoryModel.getRootCategoryNode();
/* 252 */     Enumeration enumeration = root.depthFirstEnumeration();
/* 253 */     while (enumeration.hasMoreElements()) {
/* 254 */       CategoryNode node = (CategoryNode)enumeration.nextElement();
/* 255 */       if ((!node.isLeaf()) || (node.getNumberOfContainedRecords() != 0) || (node.getParent() == null))
/*     */         continue;
/* 257 */       this._categoryModel.removeNodeFromParent(node);
/* 258 */       count++;
/*     */     }
/*     */ 
/* 262 */     return count;
/*     */   }
/*     */ 
/*     */   protected void expand(CategoryNode node) {
/* 266 */     this._tree.expandPath(getTreePath(node));
/*     */   }
/*     */ 
/*     */   protected TreePath getTreePath(CategoryNode node) {
/* 270 */     return new TreePath(node.getPath());
/*     */   }
/*     */ 
/*     */   protected void collapse(CategoryNode node) {
/* 274 */     this._tree.collapsePath(getTreePath(node));
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.categoryexplorer.CategoryNodeEditor
 * JD-Core Version:    0.6.0
 */
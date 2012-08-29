/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.KeyListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.event.TreeSelectionEvent;
/*     */ import javax.swing.event.TreeSelectionListener;
/*     */ import javax.swing.text.Position.Bias;
/*     */ import javax.swing.tree.DefaultTreeCellRenderer;
/*     */ import javax.swing.tree.TreeCellRenderer;
/*     */ import javax.swing.tree.TreeModel;
/*     */ import javax.swing.tree.TreeNode;
/*     */ import javax.swing.tree.TreePath;
/*     */ import javax.swing.tree.TreeSelectionModel;
/*     */ 
/*     */ public class CheckBoxTree extends JTree
/*     */ {
/*     */   public static final String PROPERTY_CHECKBOX_ENABLED = "checkBoxEnabled";
/*     */   public static final String PROPERTY_CLICK_IN_CHECKBOX_ONLY = "clickInCheckBoxOnly";
/*     */   public static final String PROPERTY_DIG_IN = "digIn";
/*     */   protected CheckBoxTreeCellRenderer _treeCellRenderer;
/*     */   private CheckBoxTreeSelectionModel _checkBoxTreeSelectionModel;
/*  45 */   private boolean _checkBoxEnabled = true;
/*  46 */   private boolean _clickInCheckBoxOnly = true;
/*     */   private PropertyChangeListener _modelChangeListener;
/*     */   private TristateCheckBox _checkBox;
/*     */   private TreeCellRenderer _defaultRenderer;
/*     */ 
/*     */   public CheckBoxTree()
/*     */   {
/*  51 */     init();
/*     */   }
/*     */ 
/*     */   public CheckBoxTree(Object[] value) {
/*  55 */     super(value);
/*  56 */     init();
/*     */   }
/*     */ 
/*     */   public CheckBoxTree(Vector<?> value) {
/*  60 */     super(value);
/*  61 */     init();
/*     */   }
/*     */ 
/*     */   public CheckBoxTree(Hashtable<?, ?> value) {
/*  65 */     super(value);
/*  66 */     init();
/*     */   }
/*     */ 
/*     */   public CheckBoxTree(TreeNode root) {
/*  70 */     super(root);
/*  71 */     init();
/*     */   }
/*     */ 
/*     */   public CheckBoxTree(TreeNode root, boolean asksAllowsChildren) {
/*  75 */     super(root, asksAllowsChildren);
/*  76 */     init();
/*     */   }
/*     */ 
/*     */   public CheckBoxTree(TreeModel newModel) {
/*  80 */     super(newModel);
/*  81 */     init();
/*     */   }
/*     */ 
/*     */   protected void init()
/*     */   {
/*  88 */     this._checkBoxTreeSelectionModel = createCheckBoxTreeSelectionModel(getModel());
/*  89 */     this._checkBoxTreeSelectionModel.setTree(this);
/*  90 */     Handler handler = createHandler();
/*  91 */     JideSwingUtilities.insertMouseListener(this, handler, 0);
/*  92 */     addKeyListener(handler);
/*  93 */     this._checkBoxTreeSelectionModel.addTreeSelectionListener(handler);
/*     */ 
/*  95 */     if (this._modelChangeListener == null) {
/*  96 */       this._modelChangeListener = new PropertyChangeListener() {
/*     */         public void propertyChange(PropertyChangeEvent evt) {
/*  98 */           if ("selectionModel".equals(evt.getPropertyName())) {
/*  99 */             CheckBoxTree.this.updateRowMapper();
/*     */           }
/* 101 */           if (("model".equals(evt.getPropertyName())) && ((evt.getNewValue() instanceof TreeModel)))
/* 102 */             CheckBoxTree.this._checkBoxTreeSelectionModel.setModel((TreeModel)evt.getNewValue());
/*     */         }
/*     */       };
/*     */     }
/* 107 */     addPropertyChangeListener("selectionModel", this._modelChangeListener);
/* 108 */     addPropertyChangeListener("model", this._modelChangeListener);
/* 109 */     updateRowMapper();
/*     */   }
/*     */ 
/*     */   protected CheckBoxTreeSelectionModel createCheckBoxTreeSelectionModel(TreeModel model)
/*     */   {
/* 119 */     return new CheckBoxTreeSelectionModel(model);
/*     */   }
/*     */ 
/*     */   private void updateRowMapper()
/*     */   {
/* 126 */     this._checkBoxTreeSelectionModel.setRowMapper(getSelectionModel().getRowMapper());
/*     */   }
/*     */ 
/*     */   public TreeCellRenderer getCellRenderer()
/*     */   {
/* 139 */     TreeCellRenderer cellRenderer = getActualCellRenderer();
/* 140 */     if (cellRenderer == null) {
/* 141 */       cellRenderer = getDefaultRenderer();
/*     */     }
/*     */ 
/* 144 */     if (this._treeCellRenderer == null) {
/* 145 */       this._treeCellRenderer = createCellRenderer(cellRenderer);
/*     */     }
/*     */     else {
/* 148 */       this._treeCellRenderer.setActualTreeRenderer(cellRenderer);
/*     */     }
/* 150 */     return this._treeCellRenderer;
/*     */   }
/*     */ 
/*     */   private TreeCellRenderer getDefaultRenderer() {
/* 154 */     if (this._defaultRenderer == null)
/* 155 */       this._defaultRenderer = new DefaultTreeCellRenderer();
/* 156 */     return this._defaultRenderer;
/*     */   }
/*     */ 
/*     */   public TreeCellRenderer getActualCellRenderer()
/*     */   {
/* 167 */     if (this._treeCellRenderer != null) {
/* 168 */       return this._treeCellRenderer.getActualTreeRenderer();
/*     */     }
/*     */ 
/* 171 */     return super.getCellRenderer();
/*     */   }
/*     */ 
/*     */   public void setCellRenderer(TreeCellRenderer x)
/*     */   {
/* 177 */     if (x == null) {
/* 178 */       x = getDefaultRenderer();
/*     */     }
/* 180 */     super.setCellRenderer(x);
/* 181 */     if (this._treeCellRenderer != null)
/* 182 */       this._treeCellRenderer.setActualTreeRenderer(x);
/*     */   }
/*     */ 
/*     */   protected CheckBoxTreeCellRenderer createCellRenderer(TreeCellRenderer renderer)
/*     */   {
/* 195 */     CheckBoxTreeCellRenderer checkBoxTreeCellRenderer = new CheckBoxTreeCellRenderer(renderer, getCheckBox());
/* 196 */     addPropertyChangeListener("cellRenderer", new PropertyChangeListener(checkBoxTreeCellRenderer) {
/*     */       public void propertyChange(PropertyChangeEvent evt) {
/* 198 */         TreeCellRenderer treeCellRenderer = (TreeCellRenderer)evt.getNewValue();
/* 199 */         if (treeCellRenderer != this.val$checkBoxTreeCellRenderer) {
/* 200 */           this.val$checkBoxTreeCellRenderer.setActualTreeRenderer(treeCellRenderer);
/*     */         }
/*     */         else
/* 203 */           this.val$checkBoxTreeCellRenderer.setActualTreeRenderer(null);
/*     */       }
/*     */     });
/* 207 */     return checkBoxTreeCellRenderer;
/*     */   }
/*     */ 
/*     */   protected Handler createHandler()
/*     */   {
/* 216 */     return new Handler(this);
/*     */   }
/*     */ 
/*     */   public TristateCheckBox getCheckBox()
/*     */   {
/* 226 */     return this._checkBox;
/*     */   }
/*     */ 
/*     */   public void setCheckBox(TristateCheckBox checkBox)
/*     */   {
/* 237 */     if (this._checkBox != checkBox) {
/* 238 */       this._checkBox = checkBox;
/* 239 */       this._treeCellRenderer = null;
/* 240 */       revalidate();
/* 241 */       repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   public TreePath getNextMatch(String prefix, int startingRow, Position.Bias bias)
/*     */   {
/* 407 */     return null;
/*     */   }
/*     */ 
/*     */   public CheckBoxTreeSelectionModel getCheckBoxTreeSelectionModel()
/*     */   {
/* 417 */     return this._checkBoxTreeSelectionModel;
/*     */   }
/*     */ 
/*     */   public boolean isCheckBoxEnabled()
/*     */   {
/* 428 */     return this._checkBoxEnabled;
/*     */   }
/*     */ 
/*     */   public void setCheckBoxEnabled(boolean checkBoxEnabled)
/*     */   {
/* 438 */     if (checkBoxEnabled != this._checkBoxEnabled) {
/* 439 */       Boolean oldValue = this._checkBoxEnabled ? Boolean.TRUE : Boolean.FALSE;
/* 440 */       Boolean newValue = checkBoxEnabled ? Boolean.TRUE : Boolean.FALSE;
/* 441 */       this._checkBoxEnabled = checkBoxEnabled;
/* 442 */       firePropertyChange("checkBoxEnabled", oldValue, newValue);
/* 443 */       repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isCheckBoxEnabled(TreePath path)
/*     */   {
/* 458 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isCheckBoxVisible(TreePath path)
/*     */   {
/* 469 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isDigIn()
/*     */   {
/* 480 */     return getCheckBoxTreeSelectionModel().isDigIn();
/*     */   }
/*     */ 
/*     */   public void setDigIn(boolean digIn)
/*     */   {
/* 491 */     boolean old = isDigIn();
/* 492 */     if (old != digIn) {
/* 493 */       getCheckBoxTreeSelectionModel().setDigIn(digIn);
/* 494 */       firePropertyChange("digIn", old, digIn);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isClickInCheckBoxOnly()
/*     */   {
/* 506 */     return this._clickInCheckBoxOnly;
/*     */   }
/*     */ 
/*     */   public void setClickInCheckBoxOnly(boolean clickInCheckBoxOnly)
/*     */   {
/* 516 */     if (clickInCheckBoxOnly != this._clickInCheckBoxOnly) {
/* 517 */       boolean old = this._clickInCheckBoxOnly;
/* 518 */       this._clickInCheckBoxOnly = clickInCheckBoxOnly;
/* 519 */       firePropertyChange("clickInCheckBoxOnly", old, this._clickInCheckBoxOnly);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected static class Handler
/*     */     implements MouseListener, KeyListener, TreeSelectionListener
/*     */   {
/*     */     protected CheckBoxTree _tree;
/* 247 */     int _hotspot = new JCheckBox().getPreferredSize().width;
/* 248 */     private int _toggleCount = -1;
/*     */ 
/*     */     public Handler(CheckBoxTree tree) {
/* 251 */       this._tree = tree;
/*     */     }
/*     */ 
/*     */     protected TreePath getTreePathForMouseEvent(MouseEvent e) {
/* 255 */       if (!SwingUtilities.isLeftMouseButton(e)) {
/* 256 */         return null;
/*     */       }
/*     */ 
/* 259 */       if (!this._tree.isCheckBoxEnabled()) {
/* 260 */         return null;
/*     */       }
/*     */ 
/* 263 */       TreePath path = this._tree.getPathForLocation(e.getX(), e.getY());
/* 264 */       if (path == null) {
/* 265 */         return null;
/*     */       }
/* 267 */       if ((clicksInCheckBox(e, path)) || (!this._tree.isClickInCheckBoxOnly())) {
/* 268 */         return path;
/*     */       }
/*     */ 
/* 271 */       return null;
/*     */     }
/*     */ 
/*     */     protected boolean clicksInCheckBox(MouseEvent e, TreePath path)
/*     */     {
/* 276 */       if (!this._tree.isCheckBoxVisible(path)) {
/* 277 */         return false;
/*     */       }
/*     */ 
/* 280 */       Rectangle bounds = this._tree.getPathBounds(path);
/* 281 */       if (this._tree.getComponentOrientation().isLeftToRight()) {
/* 282 */         return e.getX() < bounds.x + this._hotspot;
/*     */       }
/*     */ 
/* 285 */       return e.getX() > bounds.x + bounds.width - this._hotspot;
/*     */     }
/*     */ 
/*     */     private TreePath preventToggleEvent(MouseEvent e)
/*     */     {
/* 291 */       TreePath pathForMouseEvent = getTreePathForMouseEvent(e);
/* 292 */       if (pathForMouseEvent != null) {
/* 293 */         int toggleCount = this._tree.getToggleClickCount();
/* 294 */         if (toggleCount != -1) {
/* 295 */           this._toggleCount = toggleCount;
/* 296 */           this._tree.setToggleClickCount(-1);
/*     */         }
/*     */       }
/* 299 */       return pathForMouseEvent;
/*     */     }
/*     */ 
/*     */     public void mouseClicked(MouseEvent e) {
/* 303 */       if (e.isConsumed()) {
/* 304 */         return;
/*     */       }
/*     */ 
/* 307 */       preventToggleEvent(e);
/*     */     }
/*     */ 
/*     */     public void mousePressed(MouseEvent e) {
/* 311 */       if (e.isConsumed()) {
/* 312 */         return;
/*     */       }
/*     */ 
/* 315 */       TreePath path = preventToggleEvent(e);
/* 316 */       if (path != null) {
/* 317 */         toggleSelections(new TreePath[] { path });
/* 318 */         e.consume();
/*     */       }
/*     */     }
/*     */ 
/*     */     public void mouseReleased(MouseEvent e) {
/* 323 */       if (e.isConsumed()) {
/* 324 */         return;
/*     */       }
/*     */ 
/* 327 */       TreePath path = preventToggleEvent(e);
/* 328 */       if (path != null) {
/* 329 */         e.consume();
/*     */       }
/* 331 */       if (this._toggleCount != -1)
/* 332 */         this._tree.setToggleClickCount(this._toggleCount);
/*     */     }
/*     */ 
/*     */     public void mouseEntered(MouseEvent e)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void mouseExited(MouseEvent e) {
/*     */     }
/*     */ 
/*     */     public void keyPressed(KeyEvent e) {
/* 343 */       if (e.isConsumed()) {
/* 344 */         return;
/*     */       }
/*     */ 
/* 347 */       if (!this._tree.isCheckBoxEnabled()) {
/* 348 */         return;
/*     */       }
/*     */ 
/* 351 */       if ((e.getModifiers() == 0) && (e.getKeyChar() == ' '))
/* 352 */         toggleSelections();
/*     */     }
/*     */ 
/*     */     public void keyTyped(KeyEvent e) {
/*     */     }
/*     */ 
/*     */     public void keyReleased(KeyEvent e) {
/*     */     }
/*     */ 
/*     */     public void valueChanged(TreeSelectionEvent e) {
/* 362 */       this._tree.treeDidChange();
/*     */     }
/*     */ 
/*     */     protected void toggleSelections() {
/* 366 */       TreePath[] treePaths = this._tree.getSelectionPaths();
/* 367 */       toggleSelections(treePaths);
/*     */     }
/*     */ 
/*     */     private void toggleSelections(TreePath[] treePaths) {
/* 371 */       if ((treePaths == null) || (treePaths.length == 0) || (!this._tree.isEnabled())) {
/* 372 */         return;
/*     */       }
/* 374 */       if ((treePaths.length == 1) && (!this._tree.isCheckBoxEnabled(treePaths[0]))) {
/* 375 */         return;
/*     */       }
/* 377 */       CheckBoxTreeSelectionModel selectionModel = this._tree.getCheckBoxTreeSelectionModel();
/* 378 */       List pathToAdded = new ArrayList();
/* 379 */       List pathToRemoved = new ArrayList();
/* 380 */       for (TreePath treePath : treePaths) {
/* 381 */         boolean selected = selectionModel.isPathSelected(treePath, selectionModel.isDigIn());
/* 382 */         if (selected) {
/* 383 */           pathToRemoved.add(treePath);
/*     */         }
/*     */         else {
/* 386 */           pathToAdded.add(treePath);
/*     */         }
/*     */       }
/* 389 */       selectionModel.removeTreeSelectionListener(this);
/*     */       try {
/* 391 */         if (pathToAdded.size() > 0) {
/* 392 */           selectionModel.addSelectionPaths((TreePath[])pathToAdded.toArray(new TreePath[pathToAdded.size()]));
/*     */         }
/* 394 */         if (pathToRemoved.size() > 0)
/* 395 */           selectionModel.removeSelectionPaths((TreePath[])pathToRemoved.toArray(new TreePath[pathToRemoved.size()]));
/*     */       }
/*     */       finally
/*     */       {
/* 399 */         selectionModel.addTreeSelectionListener(this);
/* 400 */         this._tree.treeDidChange();
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.CheckBoxTree
 * JD-Core Version:    0.6.0
 */
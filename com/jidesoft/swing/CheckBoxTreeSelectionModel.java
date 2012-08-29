/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.Stack;
/*     */ import java.util.Vector;
/*     */ import javax.swing.event.TreeModelEvent;
/*     */ import javax.swing.event.TreeModelListener;
/*     */ import javax.swing.event.TreeSelectionEvent;
/*     */ import javax.swing.tree.DefaultTreeSelectionModel;
/*     */ import javax.swing.tree.TreeModel;
/*     */ import javax.swing.tree.TreePath;
/*     */ 
/*     */ public class CheckBoxTreeSelectionModel extends DefaultTreeSelectionModel
/*     */   implements TreeModelListener
/*     */ {
/*     */   private TreeModel _model;
/*  20 */   private boolean _digIn = true;
/*     */   private CheckBoxTree _tree;
/*     */   protected Set<TreePath> _pathHasAdded;
/*  28 */   private boolean _singleEventMode = false;
/*     */   private static final long serialVersionUID = 1368502059666946634L;
/* 205 */   private boolean _fireEvent = true;
/*     */ 
/* 564 */   private boolean _batchMode = false;
/*     */ 
/* 582 */   private Set<TreePath> _toBeAdded = new HashSet();
/* 583 */   private Set<TreePath> _toBeRemoved = new HashSet();
/*     */ 
/*     */   public CheckBoxTreeSelectionModel(TreeModel model)
/*     */   {
/*  32 */     setModel(model);
/*  33 */     setSelectionMode(4);
/*     */   }
/*     */ 
/*     */   void setTree(CheckBoxTree tree) {
/*  37 */     this._tree = tree;
/*     */   }
/*     */ 
/*     */   public CheckBoxTreeSelectionModel(TreeModel model, boolean digIn) {
/*  41 */     setModel(model);
/*  42 */     this._digIn = digIn;
/*     */   }
/*     */ 
/*     */   public TreeModel getModel() {
/*  46 */     return this._model;
/*     */   }
/*     */ 
/*     */   public void setModel(TreeModel model) {
/*  50 */     if (this._model != model) {
/*  51 */       if (this._model != null) {
/*  52 */         this._model.removeTreeModelListener(this);
/*     */       }
/*  54 */       this._model = model;
/*  55 */       if (this._model != null)
/*  56 */         this._model.addTreeModelListener(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isDigIn()
/*     */   {
/*  69 */     return this._digIn;
/*     */   }
/*     */ 
/*     */   public void setDigIn(boolean digIn)
/*     */   {
/*  80 */     this._digIn = digIn;
/*     */   }
/*     */ 
/*     */   public boolean isPartiallySelected(TreePath path)
/*     */   {
/*  93 */     if (!isDigIn()) {
/*  94 */       return isPathSelected(path, false);
/*     */     }
/*  96 */     if (isPathSelected(path, true))
/*  97 */       return false;
/*  98 */     TreePath[] selectionPaths = getSelectionPaths();
/*  99 */     if (selectionPaths == null)
/* 100 */       return false;
/* 101 */     for (TreePath selectionPath : selectionPaths) {
/* 102 */       if (isDescendant(selectionPath, path))
/* 103 */         return true;
/*     */     }
/* 105 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isRowSelected(int row)
/*     */   {
/* 110 */     return isPathSelected(this._tree.getPathForRow(row), this._tree.isDigIn());
/*     */   }
/*     */ 
/*     */   protected boolean isParentActuallySelected(TreePath path, TreePath parent)
/*     */   {
/* 127 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isPathSelected(TreePath path, boolean digIn)
/*     */   {
/* 142 */     if (path == null) {
/* 143 */       return false;
/*     */     }
/*     */ 
/* 146 */     if (!digIn) {
/* 147 */       return super.isPathSelected(path);
/*     */     }
/* 149 */     TreePath parent = path;
/* 150 */     while ((parent != null) && (!super.isPathSelected(parent))) {
/* 151 */       parent = parent.getParentPath();
/*     */     }
/*     */ 
/* 154 */     if (parent != null) {
/* 155 */       return isParentActuallySelected(path, parent);
/*     */     }
/*     */ 
/* 158 */     if (this._model == null) {
/* 159 */       return true;
/*     */     }
/*     */ 
/* 162 */     Object node = path.getLastPathComponent();
/* 163 */     if (getChildrenCount(node) == 0) {
/* 164 */       return false;
/*     */     }
/*     */ 
/* 168 */     boolean allChildrenSelected = true;
/* 169 */     for (int i = 0; i < getChildrenCount(node); i++) {
/* 170 */       Object childNode = getChild(node, i);
/* 171 */       if (!isPathSelected(path.pathByAddingChild(childNode), true)) {
/* 172 */         allChildrenSelected = false;
/* 173 */         break;
/*     */       }
/*     */     }
/*     */ 
/* 177 */     if ((this._tree.isCheckBoxVisible(path)) && (allChildrenSelected)) {
/* 178 */       addSelectionPaths(new TreePath[] { path });
/*     */     }
/* 180 */     return allChildrenSelected;
/*     */   }
/*     */ 
/*     */   private boolean isDescendant(TreePath path1, TreePath path2)
/*     */   {
/* 194 */     Object[] obj1 = path1.getPath();
/* 195 */     Object[] obj2 = path2.getPath();
/* 196 */     if (obj1.length < obj2.length)
/* 197 */       return false;
/* 198 */     for (int i = 0; i < obj2.length; i++) {
/* 199 */       if (obj1[i] != obj2[i])
/* 200 */         return false;
/*     */     }
/* 202 */     return true;
/*     */   }
/*     */ 
/*     */   protected void notifyPathChange(Vector changedPaths, TreePath oldLeadSelection)
/*     */   {
/* 210 */     if (this._fireEvent)
/* 211 */       super.notifyPathChange(changedPaths, oldLeadSelection);
/*     */   }
/*     */ 
/*     */   public void setSelectionPaths(TreePath[] pPaths)
/*     */   {
/* 225 */     if ((!isDigIn()) || (this.selectionMode == 1)) {
/* 226 */       super.setSelectionPaths(pPaths);
/*     */     }
/*     */     else {
/* 229 */       clearSelection();
/* 230 */       addSelectionPaths(pPaths);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addSelectionPaths(TreePath[] paths)
/*     */   {
/* 244 */     if (!isDigIn()) {
/* 245 */       super.addSelectionPaths(paths);
/* 246 */       return;
/*     */     }
/*     */ 
/* 249 */     setBatchMode(true);
/* 250 */     boolean fireEventAtTheEnd = false;
/* 251 */     if ((isSingleEventMode()) && (this._fireEvent)) {
/* 252 */       this._fireEvent = false;
/* 253 */       fireEventAtTheEnd = true;
/*     */     }
/*     */     try
/*     */     {
/* 257 */       this._pathHasAdded = new HashSet();
/* 258 */       for (TreePath path : paths) {
/* 259 */         if (isPathSelected(path, isDigIn()))
/*     */         {
/*     */           continue;
/*     */         }
/* 263 */         if (this._toBeAdded.contains(path)) {
/* 264 */           addToExistingSet(this._pathHasAdded, path);
/*     */         }
/*     */         else
/*     */         {
/* 268 */           boolean findAncestor = false;
/* 269 */           for (TreePath addPath : this._pathHasAdded) {
/* 270 */             if (addPath.isDescendant(path)) {
/* 271 */               findAncestor = true;
/* 272 */               break;
/*     */             }
/*     */           }
/* 275 */           if (findAncestor) {
/*     */             continue;
/*     */           }
/* 278 */           TreePath temp = null;
/*     */ 
/* 281 */           while (areSiblingsSelected(path)) {
/* 282 */             temp = path;
/* 283 */             if (path.getParentPath() == null)
/*     */               break;
/* 285 */             path = path.getParentPath();
/*     */           }
/* 287 */           if (temp != null) {
/* 288 */             if (temp.getParentPath() != null) {
/* 289 */               delegateAddSelectionPaths(new TreePath[] { temp.getParentPath() });
/*     */             }
/*     */             else {
/* 292 */               delegateAddSelectionPaths(new TreePath[] { temp });
/*     */             }
/*     */           }
/*     */           else {
/* 296 */             delegateAddSelectionPaths(new TreePath[] { path });
/*     */           }
/* 298 */           addToExistingSet(this._pathHasAdded, path);
/*     */         }
/*     */       }
/* 301 */       List toBeRemoved = new ArrayList();
/* 302 */       for (TreePath path : this._toBeAdded) {
/* 303 */         TreePath[] selectionPaths = getSelectionPaths();
/* 304 */         if (selectionPaths == null)
/*     */           break;
/* 306 */         for (TreePath selectionPath : selectionPaths) {
/* 307 */           if (isDescendant(selectionPath, path))
/* 308 */             toBeRemoved.add(selectionPath);
/*     */         }
/*     */       }
/* 311 */       if (toBeRemoved.size() > 0)
/* 312 */         delegateRemoveSelectionPaths((TreePath[])toBeRemoved.toArray(new TreePath[toBeRemoved.size()]));
/*     */     }
/*     */     finally
/*     */     {
/* 316 */       this._fireEvent = true;
/* 317 */       setBatchMode(false);
/* 318 */       if ((isSingleEventMode()) && (fireEventAtTheEnd))
/* 319 */         notifyPathChange(paths, true, paths[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean areSiblingsSelected(TreePath path)
/*     */   {
/* 334 */     TreePath parent = path.getParentPath();
/* 335 */     if (parent == null)
/* 336 */       return true;
/* 337 */     Object node = path.getLastPathComponent();
/* 338 */     Object parentNode = parent.getLastPathComponent();
/*     */ 
/* 340 */     int childCount = getChildrenCount(parentNode);
/* 341 */     for (int i = 0; i < childCount; i++) {
/* 342 */       Object childNode = getChild(parentNode, i);
/* 343 */       if (childNode == node)
/*     */         continue;
/* 345 */       TreePath childPath = parent.pathByAddingChild(childNode);
/* 346 */       if ((this._tree != null) && (!this._tree.isCheckBoxVisible(childPath)))
/*     */       {
/* 348 */         if ((!isPathSelected(childPath, true)) && ((this._pathHasAdded == null) || (!this._pathHasAdded.contains(childPath)))) {
/* 349 */           return false;
/*     */         }
/*     */       }
/* 352 */       if ((!isPathSelected(childPath)) && ((this._pathHasAdded == null) || (!this._pathHasAdded.contains(childPath)))) {
/* 353 */         return false;
/*     */       }
/*     */     }
/* 356 */     return true;
/*     */   }
/*     */ 
/*     */   public void removeSelectionPaths(TreePath[] paths)
/*     */   {
/* 361 */     removeSelectionPaths(paths, true);
/*     */   }
/*     */ 
/*     */   public void removeSelectionPaths(TreePath[] paths, boolean doFireEvent) {
/* 365 */     if (!isDigIn()) {
/* 366 */       super.removeSelectionPaths(paths);
/* 367 */       return;
/*     */     }
/*     */ 
/* 370 */     boolean fireEventAtTheEnd = false;
/* 371 */     if ((doFireEvent) && 
/* 372 */       (isSingleEventMode()) && (this._fireEvent)) {
/* 373 */       this._fireEvent = false;
/* 374 */       fireEventAtTheEnd = true;
/*     */     }
/*     */ 
/* 377 */     setBatchMode(true);
/*     */     try {
/* 379 */       Set pathHasRemoved = new HashSet();
/* 380 */       for (TreePath path : paths) {
/* 381 */         if (!isPathSelected(path, isDigIn())) {
/*     */           continue;
/*     */         }
/* 384 */         TreePath upperMostSelectedAncestor = null;
/* 385 */         if (this._toBeAdded.contains(path)) {
/* 386 */           this._toBeAdded.remove(path);
/* 387 */           addToExistingSet(pathHasRemoved, path);
/*     */         }
/*     */         else
/*     */         {
/* 391 */           boolean findAncestor = false;
/* 392 */           for (TreePath removedPath : pathHasRemoved) {
/* 393 */             if (removedPath.isDescendant(path)) {
/* 394 */               findAncestor = true;
/* 395 */               break;
/*     */             }
/*     */           }
/* 398 */           if (findAncestor)
/*     */           {
/*     */             continue;
/*     */           }
/* 402 */           Set pathToRemoved = new HashSet();
/* 403 */           for (TreePath pathToAdded : this._toBeAdded) {
/* 404 */             if (path.isDescendant(pathToAdded)) {
/* 405 */               pathToRemoved.add(pathToAdded);
/*     */             }
/*     */           }
/* 408 */           this._toBeAdded.removeAll(pathToRemoved);
/*     */ 
/* 410 */           for (TreePath pathToAdded : this._toBeAdded) {
/* 411 */             if (pathToAdded.isDescendant(path)) {
/* 412 */               upperMostSelectedAncestor = pathToAdded;
/* 413 */               break;
/*     */             }
/*     */           }
/* 416 */           TreePath parent = path.getParentPath();
/* 417 */           Stack stack = new Stack();
/* 418 */           while ((parent != null) && (upperMostSelectedAncestor == null ? !isPathSelected(parent) : parent != upperMostSelectedAncestor)) {
/* 419 */             stack.push(parent);
/* 420 */             parent = parent.getParentPath();
/*     */           }
/* 422 */           if (parent != null) {
/* 423 */             stack.push(parent);
/*     */           } else {
/* 425 */             delegateRemoveSelectionPaths(new TreePath[] { path });
/* 426 */             addToExistingSet(pathHasRemoved, path);
/* 427 */             continue;
/*     */           }
/*     */ 
/* 430 */           List toBeAdded = new ArrayList();
/* 431 */           while (!stack.isEmpty()) {
/* 432 */             TreePath temp = (TreePath)stack.pop();
/* 433 */             TreePath peekPath = stack.isEmpty() ? path : (TreePath)stack.peek();
/* 434 */             Object node = temp.getLastPathComponent();
/* 435 */             Object peekNode = peekPath.getLastPathComponent();
/* 436 */             int childCount = getChildrenCount(node);
/* 437 */             for (int i = 0; i < childCount; i++) {
/* 438 */               Object childNode = getChild(node, i);
/* 439 */               if (childNode != peekNode) {
/* 440 */                 TreePath treePath = temp.pathByAddingChild(childNode);
/* 441 */                 toBeAdded.add(treePath);
/*     */               }
/*     */             }
/*     */           }
/* 445 */           if (toBeAdded.size() > 0) {
/* 446 */             delegateAddSelectionPaths((TreePath[])toBeAdded.toArray(new TreePath[toBeAdded.size()]));
/*     */           }
/* 448 */           delegateRemoveSelectionPaths(new TreePath[] { parent });
/* 449 */           addToExistingSet(pathHasRemoved, path);
/*     */         }
/*     */       }
/*     */     } finally {
/* 453 */       this._fireEvent = true;
/* 454 */       setBatchMode(false);
/* 455 */       if ((isSingleEventMode()) && (fireEventAtTheEnd))
/* 456 */         notifyPathChange(paths, false, paths[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Object getChild(Object node, int i)
/*     */   {
/* 469 */     return this._model.getChild(node, i);
/*     */   }
/*     */ 
/*     */   protected int getChildrenCount(Object node)
/*     */   {
/* 479 */     return this._model.getChildCount(node);
/*     */   }
/*     */ 
/*     */   private void addToExistingSet(Set<TreePath> pathHasOperated, TreePath pathToOperate) {
/* 483 */     if (pathHasOperated.contains(pathToOperate)) {
/* 484 */       return;
/*     */     }
/* 486 */     for (TreePath path : pathHasOperated) {
/* 487 */       if (path.isDescendant(pathToOperate)) {
/* 488 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 492 */     Set duplicatePathToErase = new HashSet();
/* 493 */     for (TreePath path : pathHasOperated) {
/* 494 */       if (pathToOperate.isDescendant(path)) {
/* 495 */         duplicatePathToErase.add(path);
/*     */       }
/*     */     }
/* 498 */     pathHasOperated.removeAll(duplicatePathToErase);
/* 499 */     pathHasOperated.add(pathToOperate);
/*     */   }
/*     */ 
/*     */   public boolean isSingleEventMode() {
/* 503 */     return this._singleEventMode;
/*     */   }
/*     */ 
/*     */   public void setSingleEventMode(boolean singleEventMode)
/*     */   {
/* 537 */     this._singleEventMode = singleEventMode;
/*     */   }
/*     */ 
/*     */   protected void notifyPathChange(TreePath[] changedPaths, boolean isNew, TreePath oldLeadSelection)
/*     */   {
/* 548 */     if (this._fireEvent) {
/* 549 */       int cPathCount = changedPaths.length;
/* 550 */       boolean[] newness = new boolean[cPathCount];
/*     */ 
/* 552 */       for (int counter = 0; counter < cPathCount; counter++) {
/* 553 */         newness[counter] = isNew;
/*     */       }
/*     */ 
/* 556 */       TreeSelectionEvent event = new TreeSelectionEvent(this, changedPaths, newness, oldLeadSelection, this.leadPath);
/*     */ 
/* 559 */       fireValueChanged(event);
/*     */     }
/*     */   }
/*     */ 
/*     */   boolean isBatchMode()
/*     */   {
/* 567 */     return this._batchMode;
/*     */   }
/*     */ 
/*     */   public void setBatchMode(boolean batchMode) {
/* 571 */     this._batchMode = batchMode;
/* 572 */     if (!this._batchMode) {
/* 573 */       TreePath[] treePaths = (TreePath[])this._toBeAdded.toArray(new TreePath[this._toBeAdded.size()]);
/* 574 */       this._toBeAdded.clear();
/* 575 */       super.addSelectionPaths(treePaths);
/* 576 */       treePaths = (TreePath[])this._toBeRemoved.toArray(new TreePath[this._toBeRemoved.size()]);
/* 577 */       this._toBeRemoved.clear();
/* 578 */       super.removeSelectionPaths(treePaths);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void delegateRemoveSelectionPaths(TreePath[] paths)
/*     */   {
/* 586 */     if (!isBatchMode()) {
/* 587 */       super.removeSelectionPaths(paths);
/*     */     }
/*     */     else
/* 590 */       for (TreePath path : paths) {
/* 591 */         this._toBeRemoved.add(path);
/* 592 */         this._toBeAdded.remove(path);
/*     */       }
/*     */   }
/*     */ 
/*     */   private void delegateAddSelectionPaths(TreePath[] paths)
/*     */   {
/* 609 */     if (!isBatchMode()) {
/* 610 */       super.addSelectionPaths(paths);
/*     */     }
/*     */     else
/* 613 */       for (TreePath path : paths) {
/* 614 */         addToExistingSet(this._toBeAdded, path);
/* 615 */         this._toBeRemoved.remove(path);
/*     */       }
/*     */   }
/*     */ 
/*     */   public void treeNodesChanged(TreeModelEvent e)
/*     */   {
/* 631 */     revalidateSelectedTreePaths();
/*     */   }
/*     */ 
/*     */   public void treeNodesInserted(TreeModelEvent e)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void treeNodesRemoved(TreeModelEvent e) {
/* 639 */     revalidateSelectedTreePaths();
/*     */   }
/*     */ 
/*     */   private boolean isTreePathValid(TreePath path) {
/* 643 */     Object parent = this._model.getRoot();
/* 644 */     for (int i = 0; i < path.getPathCount(); i++) {
/* 645 */       Object pathComponent = path.getPathComponent(i);
/* 646 */       if (i == 0) {
/* 647 */         if (pathComponent != parent)
/* 648 */           return false;
/*     */       }
/*     */       else
/*     */       {
/* 652 */         boolean found = false;
/* 653 */         for (int j = 0; j < getChildrenCount(parent); j++) {
/* 654 */           Object child = getChild(parent, j);
/* 655 */           if (child == pathComponent) {
/* 656 */             found = true;
/* 657 */             break;
/*     */           }
/*     */         }
/* 660 */         if (!found) {
/* 661 */           return false;
/*     */         }
/* 663 */         parent = pathComponent;
/*     */       }
/*     */     }
/* 666 */     return true;
/*     */   }
/*     */ 
/*     */   public void treeStructureChanged(TreeModelEvent e) {
/* 670 */     revalidateSelectedTreePaths();
/*     */   }
/*     */ 
/*     */   private void revalidateSelectedTreePaths() {
/* 674 */     TreePath[] treePaths = getSelectionPaths();
/* 675 */     if (treePaths != null)
/* 676 */       for (TreePath treePath : treePaths)
/* 677 */         if ((treePath != null) && (!isTreePathValid(treePath)))
/* 678 */           super.removeSelectionPath(treePath);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.CheckBoxTreeSelectionModel
 * JD-Core Version:    0.6.0
 */
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
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.ListCellRenderer;
/*     */ import javax.swing.ListModel;
/*     */ import javax.swing.ListSelectionModel;
/*     */ import javax.swing.event.ListDataEvent;
/*     */ import javax.swing.event.ListDataListener;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ import javax.swing.text.Position.Bias;
/*     */ 
/*     */ public class CheckBoxList extends JList
/*     */ {
/*     */   protected CheckBoxListCellRenderer _listCellRenderer;
/*     */   public static final String PROPERTY_CHECKBOX_ENABLED = "checkBoxEnabled";
/*     */   public static final String PROPERTY_CLICK_IN_CHECKBOX_ONLY = "clickInCheckBoxOnly";
/*  54 */   private boolean _checkBoxEnabled = true;
/*  55 */   private boolean _clickInCheckBoxOnly = true;
/*     */   private CheckBoxListSelectionModel _checkBoxListSelectionModel;
/*     */   protected Handler _handler;
/*     */ 
/*     */   public CheckBoxList()
/*     */   {
/*  64 */     init();
/*     */   }
/*     */ 
/*     */   public CheckBoxList(Vector<?> listData)
/*     */   {
/*  73 */     super(listData);
/*  74 */     init();
/*     */   }
/*     */ 
/*     */   public CheckBoxList(Object[] listData)
/*     */   {
/*  83 */     super(listData);
/*  84 */     init();
/*     */   }
/*     */ 
/*     */   public CheckBoxList(ListModel dataModel)
/*     */   {
/*  96 */     super(dataModel);
/*  97 */     init();
/*     */   }
/*     */ 
/*     */   public void setModel(ListModel model)
/*     */   {
/* 102 */     super.setModel(model);
/* 103 */     if (getCheckBoxListSelectionModel() != null)
/* 104 */       getCheckBoxListSelectionModel().clearSelection();
/*     */   }
/*     */ 
/*     */   protected void init()
/*     */   {
/* 112 */     this._checkBoxListSelectionModel = createCheckBoxListSelectionModel(getModel());
/* 113 */     setSelectionMode(2);
/* 114 */     this._listCellRenderer = createCellRenderer();
/* 115 */     this._handler = createHandler();
/* 116 */     this._checkBoxListSelectionModel.addListSelectionListener(this._handler);
/* 117 */     JideSwingUtilities.insertMouseListener(this, this._handler, 0);
/* 118 */     addKeyListener(this._handler);
/* 119 */     addPropertyChangeListener("model", this._handler);
/* 120 */     ListModel model = getModel();
/* 121 */     if (model != null)
/* 122 */       model.addListDataListener(this._handler);
/*     */   }
/*     */ 
/*     */   public int getLastVisibleIndex()
/*     */   {
/* 128 */     int visibleIndex = super.getLastVisibleIndex();
/* 129 */     if (visibleIndex < 0) {
/* 130 */       return getModel().getSize() - 1;
/*     */     }
/* 132 */     return visibleIndex;
/*     */   }
/*     */ 
/*     */   protected CheckBoxListSelectionModel createCheckBoxListSelectionModel(ListModel model) {
/* 136 */     return new CheckBoxListSelectionModel(model);
/*     */   }
/*     */ 
/*     */   protected CheckBoxListCellRenderer createCellRenderer()
/*     */   {
/* 145 */     return new CheckBoxListCellRenderer();
/*     */   }
/*     */ 
/*     */   protected Handler createHandler()
/*     */   {
/* 154 */     return new Handler(this);
/*     */   }
/*     */ 
/*     */   public ListCellRenderer getCellRenderer()
/*     */   {
/* 159 */     if (this._listCellRenderer != null) {
/* 160 */       this._listCellRenderer.setActualListRenderer(super.getCellRenderer());
/* 161 */       return this._listCellRenderer;
/*     */     }
/*     */ 
/* 164 */     return super.getCellRenderer();
/*     */   }
/*     */ 
/*     */   public ListCellRenderer getActualCellRenderer()
/*     */   {
/* 169 */     return super.getCellRenderer();
/*     */   }
/*     */ 
/*     */   public int getNextMatch(String prefix, int startIndex, Position.Bias bias)
/*     */   {
/* 357 */     return -1;
/*     */   }
/*     */ 
/*     */   public boolean isCheckBoxEnabled(int index)
/*     */   {
/* 368 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isCheckBoxVisible(int index)
/*     */   {
/* 381 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isCheckBoxEnabled()
/*     */   {
/* 392 */     return this._checkBoxEnabled;
/*     */   }
/*     */ 
/*     */   public void setCheckBoxEnabled(boolean checkBoxEnabled)
/*     */   {
/* 402 */     if (checkBoxEnabled != this._checkBoxEnabled) {
/* 403 */       boolean old = this._checkBoxEnabled;
/* 404 */       this._checkBoxEnabled = checkBoxEnabled;
/* 405 */       firePropertyChange("checkBoxEnabled", old, this._checkBoxEnabled);
/* 406 */       repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isClickInCheckBoxOnly()
/*     */   {
/* 418 */     return this._clickInCheckBoxOnly;
/*     */   }
/*     */ 
/*     */   public void setClickInCheckBoxOnly(boolean clickInCheckBoxOnly)
/*     */   {
/* 428 */     if (clickInCheckBoxOnly != this._clickInCheckBoxOnly) {
/* 429 */       boolean old = this._clickInCheckBoxOnly;
/* 430 */       this._clickInCheckBoxOnly = clickInCheckBoxOnly;
/* 431 */       firePropertyChange("clickInCheckBoxOnly", old, this._clickInCheckBoxOnly);
/*     */     }
/*     */   }
/*     */ 
/*     */   public CheckBoxListSelectionModel getCheckBoxListSelectionModel()
/*     */   {
/* 441 */     return this._checkBoxListSelectionModel;
/*     */   }
/*     */ 
/*     */   public void setCheckBoxListSelectionModel(CheckBoxListSelectionModel checkBoxListSelectionModel) {
/* 445 */     this._checkBoxListSelectionModel = checkBoxListSelectionModel;
/* 446 */     this._checkBoxListSelectionModel.setModel(getModel());
/*     */   }
/*     */ 
/*     */   public int[] getCheckBoxListSelectedIndices()
/*     */   {
/* 458 */     ListSelectionModel listSelectionModel = getCheckBoxListSelectionModel();
/* 459 */     int iMin = listSelectionModel.getMinSelectionIndex();
/* 460 */     int iMax = listSelectionModel.getMaxSelectionIndex();
/*     */ 
/* 462 */     if ((iMin < 0) || (iMax < 0)) {
/* 463 */       return new int[0];
/*     */     }
/*     */ 
/* 466 */     int[] temp = new int[1 + (iMax - iMin)];
/* 467 */     int n = 0;
/* 468 */     for (int i = iMin; i <= iMax; i++) {
/* 469 */       if (listSelectionModel.isSelectedIndex(i)) {
/* 470 */         temp[n] = i;
/* 471 */         n++;
/*     */       }
/*     */     }
/* 474 */     int[] indices = new int[n];
/* 475 */     System.arraycopy(temp, 0, indices, 0, n);
/* 476 */     return indices;
/*     */   }
/*     */ 
/*     */   public void setCheckBoxListSelectedIndex(int index)
/*     */   {
/* 489 */     if ((index >= 0) && (index < getModel().getSize()))
/* 490 */       getCheckBoxListSelectionModel().setSelectionInterval(index, index);
/*     */   }
/*     */ 
/*     */   public void addCheckBoxListSelectedIndex(int index)
/*     */   {
/* 503 */     if ((index >= 0) && (index < getModel().getSize()))
/* 504 */       getCheckBoxListSelectionModel().addSelectionInterval(index, index);
/*     */   }
/*     */ 
/*     */   public void removeCheckBoxListSelectedIndex(int index)
/*     */   {
/* 517 */     if ((index >= 0) && (index < getModel().getSize()))
/* 518 */       getCheckBoxListSelectionModel().removeSelectionInterval(index, index);
/*     */   }
/*     */ 
/*     */   public void setCheckBoxListSelectedIndices(int[] indices)
/*     */   {
/* 531 */     ListSelectionModel listSelectionModel = getCheckBoxListSelectionModel();
/*     */     try {
/* 533 */       listSelectionModel.setValueIsAdjusting(true);
/* 534 */       listSelectionModel.clearSelection();
/* 535 */       int size = getModel().getSize();
/* 536 */       for (int indice : indices) {
/* 537 */         if ((indice >= 0) && (indice < size))
/* 538 */           listSelectionModel.addSelectionInterval(indice, indice);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 543 */       listSelectionModel.setValueIsAdjusting(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setSelectedObjects(Object[] elements)
/*     */   {
/* 553 */     Map selected = new HashMap();
/* 554 */     for (Object element : elements) {
/* 555 */       selected.put(element, "");
/*     */     }
/* 557 */     setSelectedObjects(selected);
/*     */   }
/*     */ 
/*     */   public void setSelectedObjects(Vector<?> elements)
/*     */   {
/* 566 */     Map selected = new HashMap();
/* 567 */     for (Iterator i$ = elements.iterator(); i$.hasNext(); ) { Object element = i$.next();
/* 568 */       selected.put(element, "");
/*     */     }
/* 570 */     setSelectedObjects(selected);
/*     */   }
/*     */ 
/*     */   private void setSelectedObjects(Map<Object, String> selected) {
/* 574 */     List indices = new ArrayList();
/* 575 */     for (int i = 0; i < getModel().getSize(); i++) {
/* 576 */       Object elementAt = getModel().getElementAt(i);
/* 577 */       if (selected.get(elementAt) != null) {
/* 578 */         indices.add(Integer.valueOf(i));
/*     */       }
/*     */     }
/* 581 */     int[] selectedIndices = new int[indices.size()];
/* 582 */     for (int i = 0; i < indices.size(); i++) {
/* 583 */       Integer row = (Integer)indices.get(i);
/* 584 */       selectedIndices[i] = row.intValue();
/*     */     }
/* 586 */     setCheckBoxListSelectedIndices(selectedIndices);
/*     */   }
/*     */ 
/*     */   public Object[] getCheckBoxListSelectedValues()
/*     */   {
/* 599 */     ListSelectionModel listSelectionModel = getCheckBoxListSelectionModel();
/* 600 */     ListModel model = getModel();
/*     */ 
/* 602 */     int iMin = listSelectionModel.getMinSelectionIndex();
/* 603 */     int iMax = listSelectionModel.getMaxSelectionIndex();
/*     */ 
/* 605 */     if ((iMin < 0) || (iMax < 0)) {
/* 606 */       return new Object[0];
/*     */     }
/*     */ 
/* 609 */     Object[] temp = new Object[1 + (iMax - iMin)];
/* 610 */     int n = 0;
/* 611 */     for (int i = iMin; i <= iMax; i++) {
/* 612 */       if (listSelectionModel.isSelectedIndex(i)) {
/* 613 */         temp[n] = model.getElementAt(i);
/* 614 */         n++;
/*     */       }
/*     */     }
/* 617 */     Object[] indices = new Object[n];
/* 618 */     System.arraycopy(temp, 0, indices, 0, n);
/* 619 */     return indices;
/*     */   }
/*     */ 
/*     */   public int getCheckBoxListSelectedIndex()
/*     */   {
/* 632 */     return getCheckBoxListSelectionModel().getMinSelectionIndex();
/*     */   }
/*     */ 
/*     */   public Object getCheckBoxListSelectedValue()
/*     */   {
/* 646 */     int i = getCheckBoxListSelectionModel().getMinSelectionIndex();
/* 647 */     return i == -1 ? null : getModel().getElementAt(i);
/*     */   }
/*     */ 
/*     */   public void setCheckBoxListSelectedValue(Object anObject, boolean shouldScroll)
/*     */   {
/* 658 */     if (anObject == null) {
/* 659 */       setSelectedIndex(-1);
/*     */     }
/*     */     else {
/* 662 */       ListModel model = getModel();
/* 663 */       int i = 0; for (int c = model.getSize(); i < c; i++)
/* 664 */         if (anObject.equals(model.getElementAt(i))) {
/* 665 */           setCheckBoxListSelectedIndex(i);
/* 666 */           if (shouldScroll)
/* 667 */             ensureIndexIsVisible(i);
/* 668 */           repaint();
/* 669 */           return;
/*     */         }
/* 671 */       setCheckBoxListSelectedIndex(-1);
/*     */     }
/* 673 */     repaint();
/*     */   }
/*     */ 
/*     */   public void addCheckBoxListSelectedValue(Object anObject, boolean shouldScroll)
/*     */   {
/* 684 */     ListModel model = getModel();
/*     */ 
/* 686 */     if (anObject != null) {
/* 687 */       int i = 0; for (int c = model.getSize(); i < c; i++) {
/* 688 */         if (anObject.equals(model.getElementAt(i))) {
/* 689 */           addCheckBoxListSelectedIndex(i);
/* 690 */           if (shouldScroll)
/* 691 */             ensureIndexIsVisible(i);
/* 692 */           repaint();
/* 693 */           return;
/*     */         }
/*     */       }
/*     */     }
/* 697 */     int i = 0; for (int c = model.getSize(); i < c; i++)
/* 698 */       if (model.getElementAt(i) == null) {
/* 699 */         addCheckBoxListSelectedIndex(i);
/* 700 */         if (shouldScroll)
/* 701 */           ensureIndexIsVisible(i);
/* 702 */         repaint();
/* 703 */         return;
/*     */       }
/*     */   }
/*     */ 
/*     */   public void addCheckBoxListSelectedValues(Object[] objects)
/*     */   {
/* 715 */     if (objects != null) {
/* 716 */       Map map = new HashMap();
/* 717 */       for (Object o : objects) {
/* 718 */         map.put(o, "");
/*     */       }
/*     */ 
/* 721 */       ListModel model = getModel();
/* 722 */       boolean changed = false;
/* 723 */       int i = 0; for (int c = model.getSize(); i < c; i++)
/* 724 */         if (map.get(model.getElementAt(i)) != null) {
/* 725 */           addCheckBoxListSelectedIndex(i);
/* 726 */           changed = true;
/*     */         }
/* 728 */       if (changed) {
/* 729 */         repaint();
/*     */       }
/* 731 */       map.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removeCheckBoxListSelectedValues(Object[] objects)
/*     */   {
/* 741 */     if (objects != null) {
/* 742 */       Map map = new HashMap();
/* 743 */       for (Object o : objects) {
/* 744 */         map.put(o, "");
/*     */       }
/*     */ 
/* 747 */       ListModel model = getModel();
/* 748 */       boolean changed = false;
/* 749 */       int i = 0; for (int c = model.getSize(); i < c; i++)
/* 750 */         if (map.get(model.getElementAt(i)) != null) {
/* 751 */           removeCheckBoxListSelectedIndex(i);
/* 752 */           changed = true;
/*     */         }
/* 754 */       if (changed) {
/* 755 */         repaint();
/*     */       }
/* 757 */       map.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removeCheckBoxListSelectedValue(Object anObject, boolean shouldScroll)
/*     */   {
/* 769 */     if (anObject != null)
/*     */     {
/* 771 */       ListModel model = getModel();
/* 772 */       int i = 0; for (int c = model.getSize(); i < c; i++)
/* 773 */         if (anObject.equals(model.getElementAt(i))) {
/* 774 */           removeCheckBoxListSelectedIndex(i);
/* 775 */           if (shouldScroll)
/* 776 */             ensureIndexIsVisible(i);
/* 777 */           repaint();
/* 778 */           return;
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void clearCheckBoxListSelection() {
/* 784 */     getCheckBoxListSelectionModel().clearSelection();
/*     */   }
/*     */ 
/*     */   public void selectAll()
/*     */   {
/* 791 */     if (getModel().getSize() > 0)
/* 792 */       getCheckBoxListSelectionModel().setSelectionInterval(0, getModel().getSize() - 1);
/*     */   }
/*     */ 
/*     */   public void selectNone()
/*     */   {
/* 800 */     if (getModel().getSize() > 0)
/* 801 */       getCheckBoxListSelectionModel().removeIndexInterval(0, getModel().getSize() - 1);
/*     */   }
/*     */ 
/*     */   public Dimension getPreferredScrollableViewportSize()
/*     */   {
/* 807 */     return JideSwingUtilities.adjustPreferredScrollableViewportSize(this, super.getPreferredScrollableViewportSize());
/*     */   }
/*     */ 
/*     */   protected static class Handler
/*     */     implements MouseListener, KeyListener, ListSelectionListener, PropertyChangeListener, ListDataListener
/*     */   {
/*     */     protected CheckBoxList _list;
/* 174 */     int hotspot = new JCheckBox().getPreferredSize().width;
/*     */ 
/*     */     public Handler(CheckBoxList list)
/*     */     {
/* 178 */       this._list = list;
/*     */     }
/*     */ 
/*     */     public void propertyChange(PropertyChangeEvent evt) {
/* 182 */       if ((evt.getOldValue() instanceof ListModel)) {
/* 183 */         ((ListModel)evt.getOldValue()).removeListDataListener(this);
/*     */       }
/* 185 */       if ((evt.getNewValue() instanceof ListModel)) {
/* 186 */         this._list.getCheckBoxListSelectionModel().setModel((ListModel)evt.getNewValue());
/* 187 */         ((ListModel)evt.getNewValue()).addListDataListener(this);
/*     */       }
/*     */     }
/*     */ 
/*     */     protected boolean clicksInCheckBox(MouseEvent e) {
/* 192 */       int index = this._list.locationToIndex(e.getPoint());
/* 193 */       Rectangle bounds = this._list.getCellBounds(index, index);
/*     */ 
/* 195 */       if (bounds != null) {
/* 196 */         if (this._list.getComponentOrientation().isLeftToRight()) {
/* 197 */           return e.getX() < bounds.x + this.hotspot;
/*     */         }
/*     */ 
/* 200 */         return e.getX() > bounds.x + bounds.width - this.hotspot;
/*     */       }
/*     */ 
/* 204 */       return false;
/*     */     }
/*     */ 
/*     */     public void mouseClicked(MouseEvent e)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void mousePressed(MouseEvent e) {
/* 212 */       if (e.isConsumed()) {
/* 213 */         return;
/*     */       }
/*     */ 
/* 216 */       if (!this._list.isCheckBoxEnabled()) {
/* 217 */         return;
/*     */       }
/*     */ 
/* 220 */       boolean clickInBox = clicksInCheckBox(e);
/* 221 */       if ((!this._list.isClickInCheckBoxOnly()) || (clickInBox)) {
/* 222 */         int index = this._list.locationToIndex(e.getPoint());
/* 223 */         toggleSelection(index);
/* 224 */         if (clickInBox)
/* 225 */           e.consume();
/*     */       }
/*     */     }
/*     */ 
/*     */     public void mouseReleased(MouseEvent e)
/*     */     {
/* 231 */       if (e.isConsumed()) {
/* 232 */         return;
/*     */       }
/*     */ 
/* 235 */       if (!this._list.isCheckBoxEnabled()) {
/* 236 */         return;
/*     */       }
/*     */ 
/* 239 */       if (clicksInCheckBox(e))
/* 240 */         e.consume();
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
/* 251 */       if (e.isConsumed()) {
/* 252 */         return;
/*     */       }
/*     */ 
/* 255 */       if (!this._list.isCheckBoxEnabled()) {
/* 256 */         return;
/*     */       }
/*     */ 
/* 259 */       if ((e.getModifiers() == 0) && (e.getKeyChar() == ' '))
/* 260 */         toggleSelections();
/*     */     }
/*     */ 
/*     */     public void keyTyped(KeyEvent e) {
/*     */     }
/*     */ 
/*     */     public void keyReleased(KeyEvent e) {
/*     */     }
/*     */ 
/*     */     protected void toggleSelections() {
/* 270 */       int[] indices = this._list.getSelectedIndices();
/* 271 */       CheckBoxListSelectionModel selectionModel = this._list.getCheckBoxListSelectionModel();
/* 272 */       selectionModel.removeListSelectionListener(this);
/* 273 */       selectionModel.setValueIsAdjusting(true);
/*     */       try {
/* 275 */         if (indices.length > 0) {
/* 276 */           boolean selected = selectionModel.isSelectedIndex(indices[0]);
/* 277 */           for (int index : indices) {
/* 278 */             if (!this._list.isCheckBoxEnabled(index)) {
/*     */               continue;
/*     */             }
/* 281 */             if ((selected) && (selectionModel.isSelectedIndex(index))) {
/* 282 */               selectionModel.removeSelectionInterval(index, index);
/*     */             }
/* 284 */             else if ((!selected) && (!selectionModel.isSelectedIndex(index)))
/* 285 */               selectionModel.addSelectionInterval(index, index);
/*     */           }
/*     */         }
/*     */       }
/*     */       finally
/*     */       {
/* 291 */         selectionModel.setValueIsAdjusting(false);
/* 292 */         selectionModel.addListSelectionListener(this);
/* 293 */         this._list.repaint();
/*     */       }
/*     */     }
/*     */ 
/*     */     public void valueChanged(ListSelectionEvent e)
/*     */     {
/* 299 */       this._list.repaint();
/*     */     }
/*     */ 
/*     */     protected void toggleSelection(int index) {
/* 303 */       if ((!this._list.isEnabled()) || (!this._list.isCheckBoxEnabled(index))) {
/* 304 */         return;
/*     */       }
/*     */ 
/* 307 */       CheckBoxListSelectionModel selectionModel = this._list.getCheckBoxListSelectionModel();
/* 308 */       boolean selected = selectionModel.isSelectedIndex(index);
/* 309 */       selectionModel.removeListSelectionListener(this);
/*     */       try {
/* 311 */         if (selected)
/* 312 */           selectionModel.removeSelectionInterval(index, index);
/*     */         else
/* 314 */           selectionModel.addSelectionInterval(index, index);
/*     */       }
/*     */       finally {
/* 317 */         selectionModel.addListSelectionListener(this);
/* 318 */         this._list.repaint();
/*     */       }
/*     */     }
/*     */ 
/*     */     protected void toggleSelection() {
/* 323 */       int index = this._list.getSelectedIndex();
/* 324 */       toggleSelection(index);
/*     */     }
/*     */ 
/*     */     public void intervalAdded(ListDataEvent e) {
/* 328 */       int minIndex = Math.min(e.getIndex0(), e.getIndex1());
/* 329 */       int maxIndex = Math.max(e.getIndex0(), e.getIndex1());
/*     */ 
/* 334 */       ListSelectionModel listSelectionModel = this._list.getCheckBoxListSelectionModel();
/* 335 */       if (listSelectionModel != null)
/* 336 */         listSelectionModel.insertIndexInterval(minIndex, maxIndex - minIndex + 1, true);
/*     */     }
/*     */ 
/*     */     public void intervalRemoved(ListDataEvent e)
/*     */     {
/* 344 */       ListSelectionModel listSelectionModel = this._list.getCheckBoxListSelectionModel();
/* 345 */       if (listSelectionModel != null)
/* 346 */         listSelectionModel.removeIndexInterval(e.getIndex0(), e.getIndex1());
/*     */     }
/*     */ 
/*     */     public void contentsChanged(ListDataEvent e)
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.CheckBoxList
 * JD-Core Version:    0.6.0
 */
/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.ItemSelectable;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.KeyListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.ListCellRenderer;
/*     */ import javax.swing.ListModel;
/*     */ import javax.swing.event.EventListenerList;
/*     */ import javax.swing.text.Position.Bias;
/*     */ 
/*     */ public class CheckBoxListWithSelectable extends JList
/*     */   implements ItemSelectable
/*     */ {
/*     */   protected CheckBoxListCellRenderer _listCellRenderer;
/*     */   public static final String PROPERTY_CHECKBOX_ENABLED = "checkBoxEnabled";
/*     */   public static final String PROPERTY_CLICK_IN_CHECKBOX_ONLY = "clickInCheckBoxOnly";
/*  44 */   private boolean _checkBoxEnabled = true;
/*  45 */   private boolean _clickInCheckBoxOnly = true;
/*     */ 
/*     */   public CheckBoxListWithSelectable()
/*     */   {
/*  51 */     init();
/*     */   }
/*     */ 
/*     */   public CheckBoxListWithSelectable(Vector<?> listData)
/*     */   {
/*  62 */     super(wrap(listData));
/*  63 */     init();
/*     */   }
/*     */ 
/*     */   public CheckBoxListWithSelectable(Object[] listData)
/*     */   {
/*  74 */     super(wrap(listData));
/*  75 */     init();
/*     */   }
/*     */ 
/*     */   public CheckBoxListWithSelectable(ListModel dataModel)
/*     */   {
/*  89 */     super(wrap(dataModel));
/*  90 */     init();
/*     */   }
/*     */ 
/*     */   protected void init()
/*     */   {
/*  97 */     setSelectionMode(2);
/*  98 */     this._listCellRenderer = createCellRenderer();
/*  99 */     Handler handle = createHandler();
/* 100 */     addMouseListener(handle);
/* 101 */     addKeyListener(handle);
/*     */   }
/*     */ 
/*     */   protected CheckBoxListCellRenderer createCellRenderer()
/*     */   {
/* 110 */     return new CheckBoxListCellRenderer();
/*     */   }
/*     */ 
/*     */   protected Handler createHandler()
/*     */   {
/* 119 */     return new Handler(this);
/*     */   }
/*     */ 
/*     */   public void setSelectedObjects(Object[] elements)
/*     */   {
/* 128 */     Map selected = new HashMap();
/* 129 */     for (Object element : elements) {
/* 130 */       selected.put(element, "");
/*     */     }
/* 132 */     setSelectedObjects(selected);
/*     */   }
/*     */ 
/*     */   public void setSelectedObjects(Vector<?> objects)
/*     */   {
/* 141 */     Map selected = new HashMap();
/* 142 */     for (Iterator i$ = objects.iterator(); i$.hasNext(); ) { Object element = i$.next();
/* 143 */       selected.put(element, "");
/*     */     }
/* 145 */     setSelectedObjects(selected);
/*     */   }
/*     */ 
/*     */   public ListCellRenderer getCellRenderer()
/*     */   {
/* 150 */     if (this._listCellRenderer != null) {
/* 151 */       this._listCellRenderer.setActualListRenderer(super.getCellRenderer());
/* 152 */       return this._listCellRenderer;
/*     */     }
/*     */ 
/* 155 */     return super.getCellRenderer();
/*     */   }
/*     */ 
/*     */   public ListCellRenderer getActualCellRenderer()
/*     */   {
/* 160 */     if (this._listCellRenderer != null) {
/* 161 */       return this._listCellRenderer.getActualListRenderer();
/*     */     }
/*     */ 
/* 164 */     return super.getCellRenderer();
/*     */   }
/*     */ 
/*     */   private void setSelectedObjects(Map<Object, String> selected)
/*     */   {
/* 169 */     for (int i = 0; i < getModel().getSize(); i++) {
/* 170 */       Object elementAt = getModel().getElementAt(i);
/* 171 */       if ((elementAt instanceof Selectable)) {
/* 172 */         Selectable selectable = (Selectable)elementAt;
/* 173 */         if ((selectable instanceof DefaultSelectable)) {
/* 174 */           elementAt = ((DefaultSelectable)selectable).getObject();
/*     */         }
/* 176 */         if (selected.get(elementAt) != null) {
/* 177 */           selectable.setSelected(true);
/* 178 */           fireItemStateChanged(new ItemEvent(this, 701, selectable, 1));
/* 179 */           selected.remove(elementAt);
/* 180 */           if (selected.size() == 0) {
/* 181 */             break;
/*     */           }
/*     */ 
/*     */         }
/* 185 */         else if (selectable.isSelected()) {
/* 186 */           selectable.setSelected(false);
/* 187 */           fireItemStateChanged(new ItemEvent(this, 701, selectable, 2));
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 192 */     repaint();
/*     */   }
/*     */ 
/*     */   private static ListModel wrap(ListModel dataModel) {
/* 196 */     for (int i = 0; i < dataModel.getSize(); i++) {
/* 197 */       if (!(dataModel.getElementAt(i) instanceof Selectable)) {
/* 198 */         throw new IllegalArgumentException("The ListModel contains an element which is not an instance of Selectable at index " + i + ".");
/*     */       }
/*     */     }
/* 201 */     return dataModel;
/*     */   }
/*     */ 
/*     */   private static Selectable[] wrap(Object[] objects) {
/* 205 */     if ((objects instanceof Selectable[])) {
/* 206 */       return (Selectable[])(Selectable[])objects;
/*     */     }
/*     */ 
/* 209 */     Selectable[] elements = new Selectable[objects.length];
/* 210 */     for (int i = 0; i < elements.length; i++) {
/* 211 */       elements[i] = new DefaultSelectable(objects[i]);
/*     */     }
/* 213 */     return elements;
/*     */   }
/*     */ 
/*     */   private static Vector<?> wrap(Vector<?> objects)
/*     */   {
/* 218 */     Vector elements = new Vector();
/* 219 */     for (Iterator i$ = objects.iterator(); i$.hasNext(); ) { Object o = i$.next();
/* 220 */       if ((o instanceof Selectable)) {
/* 221 */         elements.add((Selectable)o);
/*     */       }
/*     */       else {
/* 224 */         elements.add(new DefaultSelectable(o));
/*     */       }
/*     */     }
/* 227 */     return elements;
/*     */   }
/*     */ 
/*     */   public boolean isClickInCheckBoxOnly()
/*     */   {
/* 238 */     return this._clickInCheckBoxOnly;
/*     */   }
/*     */ 
/*     */   public void setClickInCheckBoxOnly(boolean clickInCheckBoxOnly)
/*     */   {
/* 248 */     if (clickInCheckBoxOnly != this._clickInCheckBoxOnly) {
/* 249 */       boolean old = this._clickInCheckBoxOnly;
/* 250 */       this._clickInCheckBoxOnly = clickInCheckBoxOnly;
/* 251 */       firePropertyChange("clickInCheckBoxOnly", old, this._clickInCheckBoxOnly);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addItemListener(ItemListener listener)
/*     */   {
/* 359 */     this.listenerList.add(ItemListener.class, listener);
/*     */   }
/*     */ 
/*     */   public void removeItemListener(ItemListener listener)
/*     */   {
/* 369 */     this.listenerList.remove(ItemListener.class, listener);
/*     */   }
/*     */ 
/*     */   public ItemListener[] getItemListeners()
/*     */   {
/* 381 */     return (ItemListener[])this.listenerList.getListeners(ItemListener.class);
/*     */   }
/*     */ 
/*     */   protected void fireItemStateChanged(ItemEvent event)
/*     */   {
/* 393 */     Object[] listeners = this.listenerList.getListenerList();
/* 394 */     ItemEvent e = null;
/*     */ 
/* 397 */     for (int i = listeners.length - 2; i >= 0; i -= 2) {
/* 398 */       if (listeners[i] != ItemListener.class)
/*     */         continue;
/* 400 */       if (e == null) {
/* 401 */         e = new ItemEvent(this, 701, event.getItem(), event.getStateChange());
/*     */       }
/*     */ 
/* 406 */       ((ItemListener)listeners[(i + 1)]).itemStateChanged(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object[] getSelectedObjects()
/*     */   {
/* 418 */     Vector elements = new Vector();
/* 419 */     for (int i = 0; i < getModel().getSize(); i++) {
/* 420 */       Object elementAt = getModel().getElementAt(i);
/* 421 */       if ((elementAt instanceof Selectable)) {
/* 422 */         Selectable selectable = (Selectable)elementAt;
/* 423 */         if (selectable.isSelected()) {
/* 424 */           if ((selectable instanceof DefaultSelectable)) {
/* 425 */             elements.add(((DefaultSelectable)selectable).getObject());
/*     */           }
/*     */           else {
/* 428 */             elements.add(selectable);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 433 */     return elements.toArray();
/*     */   }
/*     */ 
/*     */   public void selectAll()
/*     */   {
/* 440 */     for (int i = 0; i < getModel().getSize(); i++) {
/* 441 */       Object elementAt = getModel().getElementAt(i);
/* 442 */       if ((elementAt instanceof Selectable)) {
/* 443 */         Selectable selectable = (Selectable)elementAt;
/* 444 */         if ((selectable.isEnabled()) && (!selectable.isSelected())) {
/* 445 */           selectable.setSelected(true);
/* 446 */           fireItemStateChanged(new ItemEvent(this, 701, selectable, 1));
/*     */         }
/*     */       }
/*     */     }
/* 450 */     repaint();
/*     */   }
/*     */ 
/*     */   public void selectNone()
/*     */   {
/* 457 */     for (int i = 0; i < getModel().getSize(); i++) {
/* 458 */       Object elementAt = getModel().getElementAt(i);
/* 459 */       if ((elementAt instanceof Selectable)) {
/* 460 */         Selectable selectable = (Selectable)elementAt;
/* 461 */         if ((selectable.isEnabled()) && (selectable.isSelected())) {
/* 462 */           selectable.setSelected(false);
/* 463 */           fireItemStateChanged(new ItemEvent(this, 701, selectable, 2));
/*     */         }
/*     */       }
/*     */     }
/* 467 */     repaint();
/*     */   }
/*     */ 
/*     */   public void setListData(Vector listData)
/*     */   {
/* 472 */     super.setListData(wrap(listData));
/*     */   }
/*     */ 
/*     */   public void setListData(Object[] listData)
/*     */   {
/* 477 */     super.setListData(wrap(listData));
/*     */   }
/*     */ 
/*     */   public int getNextMatch(String prefix, int startIndex, Position.Bias bias)
/*     */   {
/* 482 */     return -1;
/*     */   }
/*     */ 
/*     */   public boolean isCheckBoxEnabled()
/*     */   {
/* 493 */     return this._checkBoxEnabled;
/*     */   }
/*     */ 
/*     */   public boolean isCheckBoxVisible(int index)
/*     */   {
/* 505 */     return true;
/*     */   }
/*     */ 
/*     */   public void setCheckBoxEnabled(boolean checkBoxEnabled)
/*     */   {
/* 514 */     if (checkBoxEnabled != this._checkBoxEnabled) {
/* 515 */       Boolean oldValue = this._checkBoxEnabled ? Boolean.TRUE : Boolean.FALSE;
/* 516 */       Boolean newValue = checkBoxEnabled ? Boolean.TRUE : Boolean.FALSE;
/* 517 */       this._checkBoxEnabled = checkBoxEnabled;
/* 518 */       firePropertyChange("checkBoxEnabled", oldValue, newValue);
/* 519 */       repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected static class Handler
/*     */     implements MouseListener, KeyListener
/*     */   {
/*     */     protected CheckBoxListWithSelectable _list;
/* 257 */     int _hotspot = new JCheckBox().getPreferredSize().width;
/*     */ 
/*     */     public Handler(CheckBoxListWithSelectable list) {
/* 260 */       this._list = list;
/*     */     }
/*     */ 
/*     */     protected boolean clicksInCheckBox(MouseEvent e) {
/* 264 */       int index = this._list.locationToIndex(e.getPoint());
/* 265 */       Rectangle bounds = this._list.getCellBounds(index, index);
/* 266 */       if (bounds != null) {
/* 267 */         if (this._list.getComponentOrientation().isLeftToRight()) {
/* 268 */           return e.getX() < bounds.x + this._hotspot;
/*     */         }
/*     */ 
/* 271 */         return e.getX() > bounds.x + bounds.width - this._hotspot;
/*     */       }
/*     */ 
/* 275 */       return false;
/*     */     }
/*     */ 
/*     */     public void mouseClicked(MouseEvent e)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void mousePressed(MouseEvent e)
/*     */     {
/* 284 */       if ((!this._list.isCheckBoxEnabled()) || (!this._list.isEnabled())) {
/* 285 */         return;
/*     */       }
/*     */ 
/* 288 */       if ((!this._list.isClickInCheckBoxOnly()) || (clicksInCheckBox(e))) {
/* 289 */         int index = this._list.locationToIndex(e.getPoint());
/* 290 */         toggleSelection(index);
/*     */       }
/*     */     }
/*     */ 
/*     */     public void mouseReleased(MouseEvent e) {
/*     */     }
/*     */ 
/*     */     public void mouseEntered(MouseEvent e) {
/*     */     }
/*     */ 
/*     */     public void mouseExited(MouseEvent e) {
/*     */     }
/*     */ 
/*     */     public void keyPressed(KeyEvent e) {
/* 304 */       if ((!this._list.isCheckBoxEnabled()) || (!this._list.isEnabled())) {
/* 305 */         return;
/*     */       }
/*     */ 
/* 308 */       if ((e.getModifiers() == 0) && (e.getKeyChar() == ' '))
/* 309 */         toggleSelections();
/*     */     }
/*     */ 
/*     */     public void keyTyped(KeyEvent e) {
/*     */     }
/*     */ 
/*     */     public void keyReleased(KeyEvent e) {
/*     */     }
/*     */ 
/*     */     protected void toggleSelections() {
/* 319 */       int[] indices = this._list.getSelectedIndices();
/* 320 */       ListModel model = this._list.getModel();
/* 321 */       for (int index : indices) {
/* 322 */         Object element = model.getElementAt(index);
/* 323 */         if (((element instanceof Selectable)) && (((Selectable)element).isEnabled())) {
/* 324 */           ((Selectable)element).invertSelected();
/* 325 */           boolean selected = ((Selectable)element).isSelected();
/* 326 */           this._list.fireItemStateChanged(new ItemEvent(this._list, 701, element, selected ? 1 : 2));
/*     */         }
/*     */       }
/* 329 */       this._list.repaint();
/*     */     }
/*     */ 
/*     */     protected void toggleSelection(int index) {
/* 333 */       ListModel model = this._list.getModel();
/* 334 */       if (index >= 0) {
/* 335 */         Object element = model.getElementAt(index);
/* 336 */         if (((element instanceof Selectable)) && (((Selectable)element).isEnabled())) {
/* 337 */           ((Selectable)element).invertSelected();
/* 338 */           boolean selected = ((Selectable)element).isSelected();
/* 339 */           this._list.fireItemStateChanged(new ItemEvent(this._list, 701, element, selected ? 1 : 2));
/*     */         }
/* 341 */         this._list.repaint();
/*     */       }
/*     */     }
/*     */ 
/*     */     protected void toggleSelection() {
/* 346 */       int index = this._list.getSelectedIndex();
/* 347 */       toggleSelection(index);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.CheckBoxListWithSelectable
 * JD-Core Version:    0.6.0
 */
/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.swing.event.SearchableEvent;
/*     */ import java.awt.IllegalComponentStateException;
/*     */ import java.awt.KeyboardFocusManager;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.ComboBoxModel;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComboBox.KeySelectionManager;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.event.ListDataEvent;
/*     */ import javax.swing.event.ListDataListener;
/*     */ import javax.swing.event.PopupMenuEvent;
/*     */ import javax.swing.event.PopupMenuListener;
/*     */ 
/*     */ public class ComboBoxSearchable extends Searchable
/*     */   implements ListDataListener, PropertyChangeListener, PopupMenuListener
/*     */ {
/*  46 */   private boolean _showPopupDuringSearching = true;
/*     */ 
/*     */   public ComboBoxSearchable(JComboBox comboBox) {
/*  49 */     super(comboBox);
/*     */ 
/*  52 */     comboBox.setKeySelectionManager(new JComboBox.KeySelectionManager() {
/*     */       public int selectionForKey(char aKey, ComboBoxModel aModel) {
/*  54 */         return -1;
/*     */       }
/*     */     });
/*  57 */     comboBox.getModel().addListDataListener(this);
/*  58 */     comboBox.addPropertyChangeListener("model", this);
/*  59 */     comboBox.addPopupMenuListener(this);
/*     */   }
/*     */ 
/*     */   public void uninstallListeners()
/*     */   {
/*  64 */     super.uninstallListeners();
/*  65 */     if ((this._component instanceof JComboBox)) {
/*  66 */       ((JComboBox)this._component).getModel().removeListDataListener(this);
/*  67 */       ((JComboBox)this._component).removePopupMenuListener(this);
/*     */     }
/*  69 */     this._component.removePropertyChangeListener("model", this);
/*     */   }
/*     */ 
/*     */   public boolean isShowPopupDuringSearching()
/*     */   {
/*  78 */     return this._showPopupDuringSearching;
/*     */   }
/*     */ 
/*     */   public void setShowPopupDuringSearching(boolean showPopupDuringSearching)
/*     */   {
/*  87 */     this._showPopupDuringSearching = showPopupDuringSearching;
/*     */   }
/*     */ 
/*     */   protected void setSelectedIndex(int index, boolean incremental)
/*     */   {
/*  92 */     if (((JComboBox)this._component).getSelectedIndex() != index) {
/*  93 */       ((JComboBox)this._component).setSelectedIndex(index);
/*     */     }
/*  95 */     if (isShowPopupDuringSearching()) {
/*  96 */       boolean old = isHideSearchPopupOnEvent();
/*  97 */       setHideSearchPopupOnEvent(false);
/*  98 */       ((JComboBox)this._component).hidePopup();
/*  99 */       setHideSearchPopupOnEvent(old);
/*     */       try {
/* 101 */         if ((!((JComboBox)this._component).isPopupVisible()) && (KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() != null) && (SwingUtilities.isDescendingFrom(KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner(), this._component)))
/*     */         {
/* 104 */           ((JComboBox)this._component).showPopup();
/*     */         }
/*     */       }
/*     */       catch (IllegalComponentStateException e)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected int getSelectedIndex()
/*     */   {
/* 115 */     return ((JComboBox)this._component).getSelectedIndex();
/*     */   }
/*     */ 
/*     */   protected Object getElementAt(int index)
/*     */   {
/* 120 */     ComboBoxModel comboBoxModel = ((JComboBox)this._component).getModel();
/* 121 */     return comboBoxModel.getElementAt(index);
/*     */   }
/*     */ 
/*     */   protected int getElementCount()
/*     */   {
/* 126 */     ComboBoxModel comboBoxModel = ((JComboBox)this._component).getModel();
/* 127 */     return comboBoxModel.getSize();
/*     */   }
/*     */ 
/*     */   protected String convertElementToString(Object object)
/*     */   {
/* 139 */     if (object != null) {
/* 140 */       return object.toString();
/*     */     }
/*     */ 
/* 143 */     return "";
/*     */   }
/*     */ 
/*     */   public void contentsChanged(ListDataEvent e)
/*     */   {
/* 148 */     if (!isProcessModelChangeEvent()) {
/* 149 */       return;
/*     */     }
/* 151 */     if ((e.getIndex0() != -1) || (e.getIndex1() != -1))
/*     */     {
/* 154 */       hidePopup();
/* 155 */       fireSearchableEvent(new SearchableEvent(this, 3005));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void intervalAdded(ListDataEvent e) {
/* 160 */     if (!isProcessModelChangeEvent()) {
/* 161 */       return;
/*     */     }
/* 163 */     hidePopup();
/* 164 */     fireSearchableEvent(new SearchableEvent(this, 3005));
/*     */   }
/*     */ 
/*     */   public void intervalRemoved(ListDataEvent e) {
/* 168 */     if (!isProcessModelChangeEvent()) {
/* 169 */       return;
/*     */     }
/* 171 */     hidePopup();
/* 172 */     fireSearchableEvent(new SearchableEvent(this, 3005));
/*     */   }
/*     */ 
/*     */   public void propertyChange(PropertyChangeEvent evt) {
/* 176 */     if ("model".equals(evt.getPropertyName())) {
/* 177 */       hidePopup();
/*     */ 
/* 179 */       if ((evt.getOldValue() instanceof ComboBoxModel)) {
/* 180 */         ((ComboBoxModel)evt.getOldValue()).removeListDataListener(this);
/*     */       }
/*     */ 
/* 183 */       if ((evt.getNewValue() instanceof ComboBoxModel)) {
/* 184 */         ((ComboBoxModel)evt.getNewValue()).addListDataListener(this);
/*     */       }
/*     */     }
/* 187 */     fireSearchableEvent(new SearchableEvent(this, 3005));
/*     */   }
/*     */ 
/*     */   public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
/*     */   }
/*     */ 
/*     */   public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
/* 194 */     if (isHideSearchPopupOnEvent())
/* 195 */       hidePopup();
/*     */   }
/*     */ 
/*     */   public void popupMenuCanceled(PopupMenuEvent e)
/*     */   {
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.ComboBoxSearchable
 * JD-Core Version:    0.6.0
 */
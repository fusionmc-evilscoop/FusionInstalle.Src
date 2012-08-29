/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.swing.event.SearchableEvent;
/*     */ import java.awt.Component;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.ListCellRenderer;
/*     */ import javax.swing.ListModel;
/*     */ import javax.swing.event.ListDataEvent;
/*     */ import javax.swing.event.ListDataListener;
/*     */ 
/*     */ public class ListSearchable extends Searchable
/*     */   implements ListDataListener, PropertyChangeListener
/*     */ {
/*  53 */   private boolean _useRendererAsConverter = false;
/*     */ 
/*     */   public ListSearchable(JList list) {
/*  56 */     super(list);
/*  57 */     list.getModel().addListDataListener(this);
/*  58 */     list.addPropertyChangeListener("model", this);
/*     */   }
/*     */ 
/*     */   public void uninstallListeners()
/*     */   {
/*  63 */     super.uninstallListeners();
/*  64 */     if ((this._component instanceof JList)) {
/*  65 */       ((JList)this._component).getModel().removeListDataListener(this);
/*     */     }
/*  67 */     this._component.removePropertyChangeListener("model", this);
/*     */   }
/*     */ 
/*     */   public void setSelectedIndex(int index, boolean incremental)
/*     */   {
/*  73 */     if (incremental) {
/*  74 */       ((JList)this._component).addSelectionInterval(index, index);
/*     */     }
/*  77 */     else if (((JList)this._component).getSelectedIndex() != index) {
/*  78 */       ((JList)this._component).setSelectedIndex(index);
/*     */     }
/*     */ 
/*  81 */     ((JList)this._component).ensureIndexIsVisible(index);
/*     */   }
/*     */ 
/*     */   protected int getSelectedIndex()
/*     */   {
/*  86 */     return ((JList)this._component).getSelectedIndex();
/*     */   }
/*     */ 
/*     */   protected Object getElementAt(int index)
/*     */   {
/*  91 */     ListModel listModel = ((JList)this._component).getModel();
/*  92 */     return listModel.getElementAt(index);
/*     */   }
/*     */ 
/*     */   protected int getElementCount()
/*     */   {
/*  97 */     ListModel listModel = ((JList)this._component).getModel();
/*  98 */     return listModel.getSize();
/*     */   }
/*     */ 
/*     */   protected String convertElementToString(Object object)
/*     */   {
/* 110 */     if (isUseRendererAsConverter()) {
/* 111 */       ListCellRenderer renderer = ((JList)this._component).getCellRenderer();
/*     */ 
/* 114 */       if (renderer != null) {
/* 115 */         Component component = renderer.getListCellRendererComponent((JList)this._component, object, 0, false, false);
/* 116 */         if (component != null) {
/* 117 */           if ((component instanceof JLabel)) {
/* 118 */             return ((JLabel)component).getText();
/*     */           }
/* 120 */           if ((component instanceof CheckBoxListCellRenderer)) {
/* 121 */             ListCellRenderer actualRenderer = ((CheckBoxListCellRenderer)component).getActualListRenderer();
/* 122 */             if ((actualRenderer != null) && ((actualRenderer instanceof JLabel))) {
/* 123 */               return ((JLabel)actualRenderer).getText();
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 129 */     if (object != null) {
/* 130 */       return object.toString();
/*     */     }
/*     */ 
/* 133 */     return "";
/*     */   }
/*     */ 
/*     */   public void contentsChanged(ListDataEvent e)
/*     */   {
/* 138 */     if (!isProcessModelChangeEvent()) {
/* 139 */       return;
/*     */     }
/* 141 */     if ((e.getIndex0() == -1) && (e.getIndex1() == -1)) {
/* 142 */       return;
/*     */     }
/* 144 */     hidePopup();
/* 145 */     fireSearchableEvent(new SearchableEvent(this, 3005));
/*     */   }
/*     */ 
/*     */   public void intervalAdded(ListDataEvent e) {
/* 149 */     if (!isProcessModelChangeEvent()) {
/* 150 */       return;
/*     */     }
/* 152 */     hidePopup();
/* 153 */     fireSearchableEvent(new SearchableEvent(this, 3005));
/*     */   }
/*     */ 
/*     */   public void intervalRemoved(ListDataEvent e) {
/* 157 */     if (!isProcessModelChangeEvent()) {
/* 158 */       return;
/*     */     }
/* 160 */     hidePopup();
/* 161 */     fireSearchableEvent(new SearchableEvent(this, 3005));
/*     */   }
/*     */ 
/*     */   public void propertyChange(PropertyChangeEvent evt) {
/* 165 */     if ("model".equals(evt.getPropertyName())) {
/* 166 */       hidePopup();
/*     */ 
/* 168 */       ListModel oldModel = (ListModel)evt.getOldValue();
/* 169 */       if (oldModel != null) {
/* 170 */         oldModel.removeListDataListener(this);
/*     */       }
/*     */ 
/* 173 */       ListModel newModel = (ListModel)evt.getNewValue();
/* 174 */       if (newModel != null) {
/* 175 */         newModel.addListDataListener(this);
/*     */       }
/* 177 */       fireSearchableEvent(new SearchableEvent(this, 3005));
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isUseRendererAsConverter()
/*     */   {
/* 190 */     return this._useRendererAsConverter;
/*     */   }
/*     */ 
/*     */   public void setUseRendererAsConverter(boolean useRendererAsConverter)
/*     */   {
/* 201 */     this._useRendererAsConverter = useRendererAsConverter;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.ListSearchable
 * JD-Core Version:    0.6.0
 */
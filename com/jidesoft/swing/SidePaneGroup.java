/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.swing.event.SidePaneEvent;
/*     */ import com.jidesoft.swing.event.SidePaneListener;
/*     */ import java.awt.Component;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.event.EventListenerList;
/*     */ 
/*     */ public class SidePaneGroup extends ArrayList<SidePaneItem>
/*     */ {
/*  25 */   private SidePaneItem _selectedItem = null;
/*     */   protected EventListenerList listenerList;
/*     */ 
/*     */   public SidePaneItem getSelectedItem()
/*     */   {
/*  45 */     if (this._selectedItem != null)
/*  46 */       return this._selectedItem;
/*  47 */     if (size() > 0) {
/*  48 */       return (SidePaneItem)get(0);
/*     */     }
/*  50 */     return null;
/*     */   }
/*     */ 
/*     */   public void setSelectedItem(SidePaneItem selectedItem)
/*     */   {
/*  59 */     boolean changedSelection = selectedItem != this._selectedItem;
/*  60 */     if ((changedSelection) && (this._selectedItem != null)) {
/*  61 */       fireSidePaneEvent(this._selectedItem, 4100);
/*     */     }
/*     */ 
/*  64 */     if (this._selectedItem != null) {
/*  65 */       this._selectedItem.setSelected(false);
/*     */     }
/*  67 */     this._selectedItem = selectedItem;
/*  68 */     if (this._selectedItem != null) {
/*  69 */       this._selectedItem.setSelected(true);
/*     */     }
/*     */ 
/*  72 */     if ((changedSelection) && (selectedItem != null))
/*  73 */       fireSidePaneEvent(selectedItem, 4099);
/*     */   }
/*     */ 
/*     */   public int getSelectedIndex()
/*     */   {
/*  83 */     return indexOf(this._selectedItem);
/*     */   }
/*     */ 
/*     */   public void setSelectedIndex(int index)
/*     */   {
/*  92 */     setSelectedItem((SidePaneItem)get(index));
/*     */   }
/*     */ 
/*     */   public String getLongestTitle()
/*     */   {
/* 102 */     int length = 0;
/* 103 */     String title = "";
/* 104 */     for (int i = 0; i < size(); i++) {
/* 105 */       SidePaneItem item = (SidePaneItem)get(i);
/* 106 */       if (item.getTitle().length() > length) {
/* 107 */         length = item.getTitle().length();
/* 108 */         title = item.getTitle();
/*     */       }
/*     */     }
/* 111 */     return title;
/*     */   }
/*     */ 
/*     */   public boolean removeComponent(Component comp)
/*     */   {
/* 122 */     if (comp == null)
/* 123 */       return false;
/* 124 */     for (int i = 0; i < size(); i++) {
/* 125 */       SidePaneItem item = (SidePaneItem)get(i);
/* 126 */       if (item.getComponent().equals(comp)) {
/* 127 */         remove(item);
/* 128 */         if ((item.equals(this._selectedItem)) && (size() > 0)) {
/* 129 */           this._selectedItem = ((SidePaneItem)get(0));
/*     */         }
/* 131 */         return true;
/*     */       }
/*     */     }
/* 134 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean exists(Component comp)
/*     */   {
/* 145 */     if (comp == null)
/* 146 */       return false;
/* 147 */     for (int i = 0; i < size(); i++) {
/* 148 */       SidePaneItem item = (SidePaneItem)get(i);
/* 149 */       if (item.getComponent().equals(comp))
/* 150 */         return true;
/*     */     }
/* 152 */     return false;
/*     */   }
/*     */ 
/*     */   public SidePaneItem getSidePaneItem(Component comp)
/*     */   {
/* 163 */     if (comp == null)
/* 164 */       return null;
/* 165 */     for (int i = 0; i < size(); i++) {
/* 166 */       SidePaneItem item = (SidePaneItem)get(i);
/* 167 */       if (item.getComponent().equals(comp))
/* 168 */         return item;
/*     */     }
/* 170 */     return null;
/*     */   }
/*     */ 
/*     */   private boolean initListenerList(boolean create) {
/* 174 */     if ((this.listenerList == null) && (create)) {
/* 175 */       this.listenerList = new EventListenerList();
/* 176 */       return true;
/*     */     }
/*     */ 
/* 179 */     return this.listenerList != null;
/*     */   }
/*     */ 
/*     */   public void addSidePaneListener(SidePaneListener l)
/*     */   {
/* 189 */     if ((initListenerList(true)) && 
/* 190 */       (!JideSwingUtilities.isListenerRegistered(this.listenerList, SidePaneListener.class, l)))
/* 191 */       this.listenerList.add(SidePaneListener.class, l);
/*     */   }
/*     */ 
/*     */   public void removeSidePaneListener(SidePaneListener l)
/*     */   {
/* 203 */     if (initListenerList(false))
/* 204 */       this.listenerList.remove(SidePaneListener.class, l);
/*     */   }
/*     */ 
/*     */   public SidePaneListener[] getSidePaneListeners()
/*     */   {
/* 218 */     if (initListenerList(false)) {
/* 219 */       return (SidePaneListener[])this.listenerList.getListeners(SidePaneListener.class);
/*     */     }
/*     */ 
/* 222 */     return new SidePaneListener[0];
/*     */   }
/*     */ 
/*     */   protected void fireSidePaneEvent(SidePaneItem sidePaneItem, int id)
/*     */   {
/* 234 */     if (initListenerList(false)) {
/* 235 */       Object[] listeners = this.listenerList.getListenerList();
/* 236 */       SidePaneEvent e = null;
/* 237 */       for (int i = listeners.length - 2; i >= 0; i -= 2)
/* 238 */         if (listeners[i] == SidePaneListener.class) {
/* 239 */           if (e == null) {
/* 240 */             e = new SidePaneEvent(sidePaneItem, id);
/*     */           }
/* 242 */           switch (e.getID()) {
/*     */           case 4099:
/* 244 */             ((SidePaneListener)listeners[(i + 1)]).sidePaneTabSelected(e);
/* 245 */             break;
/*     */           case 4100:
/* 247 */             ((SidePaneListener)listeners[(i + 1)]).sidePaneTabDeselected(e);
/*     */           }
/*     */         }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.SidePaneGroup
 * JD-Core Version:    0.6.0
 */
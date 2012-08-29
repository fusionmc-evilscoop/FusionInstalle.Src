/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ public class DefaultSelectable
/*     */   implements Selectable
/*     */ {
/*     */   protected Object _object;
/*  14 */   protected boolean _selected = false;
/*  15 */   protected boolean _enabled = true;
/*     */ 
/*     */   public DefaultSelectable(Object object)
/*     */   {
/*  24 */     this._object = object;
/*     */   }
/*     */ 
/*     */   public void setObject(Object object)
/*     */   {
/*  33 */     this._object = object;
/*     */   }
/*     */ 
/*     */   public Object getObject()
/*     */   {
/*  42 */     return this._object;
/*     */   }
/*     */ 
/*     */   public void setSelected(boolean selected)
/*     */   {
/*  51 */     this._selected = selected;
/*     */   }
/*     */ 
/*     */   public void invertSelected()
/*     */   {
/*  58 */     setSelected(!this._selected);
/*     */   }
/*     */ 
/*     */   public boolean isSelected()
/*     */   {
/*  67 */     return this._selected;
/*     */   }
/*     */ 
/*     */   public void setEnabled(boolean enabled)
/*     */   {
/*  78 */     this._enabled = enabled;
/*     */   }
/*     */ 
/*     */   public boolean isEnabled()
/*     */   {
/*  87 */     return this._enabled;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  98 */     return this._object != null ? this._object.hashCode() : 0;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 109 */     return this._object != null ? this._object.toString() : "";
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 114 */     if ((obj instanceof DefaultSelectable)) {
/* 115 */       if ((getObject() == null) && (((DefaultSelectable)obj).getObject() == null)) {
/* 116 */         return true;
/*     */       }
/* 118 */       if ((getObject() == null) && (((DefaultSelectable)obj).getObject() != null)) {
/* 119 */         return false;
/*     */       }
/* 121 */       return getObject().equals(((DefaultSelectable)obj).getObject());
/*     */     }
/*     */ 
/* 124 */     return (obj == null) && (getObject() == null);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.DefaultSelectable
 * JD-Core Version:    0.6.0
 */
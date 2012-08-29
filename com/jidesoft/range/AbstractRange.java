/*    */ package com.jidesoft.range;
/*    */ 
/*    */ import java.beans.PropertyChangeEvent;
/*    */ import java.beans.PropertyChangeListener;
/*    */ import java.beans.PropertyChangeSupport;
/*    */ 
/*    */ public abstract class AbstractRange<T>
/*    */   implements Range<T>
/*    */ {
/*    */   private PropertyChangeSupport changeSupport;
/*    */ 
/*    */   public Range<T> copy()
/*    */   {
/* 23 */     throw new UnsupportedOperationException("Copy method not implemented");
/*    */   }
/*    */ 
/*    */   public void addPropertyChangeListener(PropertyChangeListener listener) {
/* 27 */     if (listener == null) {
/* 28 */       return;
/*    */     }
/* 30 */     if (this.changeSupport == null) {
/* 31 */       this.changeSupport = new PropertyChangeSupport(this);
/*    */     }
/* 33 */     this.changeSupport.addPropertyChangeListener(listener);
/*    */   }
/*    */ 
/*    */   public void removePropertyChangeListener(PropertyChangeListener listener) {
/* 37 */     if ((listener == null) || (this.changeSupport == null)) {
/* 38 */       return;
/*    */     }
/* 40 */     this.changeSupport.removePropertyChangeListener(listener);
/*    */   }
/*    */ 
/*    */   protected void firePropertyChange(PropertyChangeEvent evt) {
/* 44 */     if (this.changeSupport == null) {
/* 45 */       return;
/*    */     }
/* 47 */     this.changeSupport.firePropertyChange(evt);
/*    */   }
/*    */ 
/*    */   protected void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
/* 51 */     if (this.changeSupport == null) {
/* 52 */       return;
/*    */     }
/* 54 */     this.changeSupport.firePropertyChange(propertyName, oldValue, newValue);
/*    */   }
/*    */ 
/*    */   protected void firePropertyChange(String propertyName, int oldValue, int newValue) {
/* 58 */     if (this.changeSupport == null) {
/* 59 */       return;
/*    */     }
/* 61 */     this.changeSupport.firePropertyChange(propertyName, oldValue, newValue);
/*    */   }
/*    */ 
/*    */   protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
/* 65 */     if (this.changeSupport == null) {
/* 66 */       return;
/*    */     }
/* 68 */     this.changeSupport.firePropertyChange(propertyName, oldValue, newValue);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.range.AbstractRange
 * JD-Core Version:    0.6.0
 */
/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import java.awt.event.ItemEvent;
/*    */ import java.beans.PropertyChangeEvent;
/*    */ import java.beans.PropertyChangeListener;
/*    */ import java.lang.ref.WeakReference;
/*    */ import java.lang.reflect.Method;
/*    */ import javax.swing.event.ChangeEvent;
/*    */ 
/*    */ public class WeakPropertyChangeListener
/*    */   implements PropertyChangeListener
/*    */ {
/*    */   private WeakReference<PropertyChangeListener> _listenerRef;
/*    */   private Object _src;
/*    */ 
/*    */   public WeakPropertyChangeListener(PropertyChangeListener listener, Object src)
/*    */   {
/* 49 */     this._listenerRef = new WeakReference(listener);
/* 50 */     this._src = src;
/*    */   }
/*    */ 
/*    */   public void propertyChange(PropertyChangeEvent evt) {
/* 54 */     PropertyChangeListener listener = (PropertyChangeListener)this._listenerRef.get();
/* 55 */     if (listener == null) {
/* 56 */       removeListener();
/*    */     }
/*    */     else
/* 59 */       listener.propertyChange(evt);
/*    */   }
/*    */ 
/*    */   public void itemStateChanged(ItemEvent e)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void stateChanged(ChangeEvent e)
/*    */   {
/*    */   }
/*    */ 
/*    */   private void removeListener() {
/*    */     try {
/* 72 */       Method method = this._src.getClass().getMethod("removePropertyChangeListener", new Class[] { PropertyChangeListener.class });
/*    */ 
/* 74 */       method.invoke(this._src, new Object[] { this });
/*    */     }
/*    */     catch (Exception e) {
/* 77 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.WeakPropertyChangeListener
 * JD-Core Version:    0.6.0
 */
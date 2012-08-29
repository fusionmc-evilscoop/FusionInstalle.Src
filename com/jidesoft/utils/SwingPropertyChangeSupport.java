/*    */ package com.jidesoft.utils;
/*    */ 
/*    */ import java.beans.PropertyChangeEvent;
/*    */ import java.beans.PropertyChangeSupport;
/*    */ import javax.swing.SwingUtilities;
/*    */ 
/*    */ public final class SwingPropertyChangeSupport extends PropertyChangeSupport
/*    */ {
/*    */   static final long serialVersionUID = 7162625831330845068L;
/*    */   private final boolean notifyOnEDT;
/*    */ 
/*    */   public SwingPropertyChangeSupport(Object sourceBean)
/*    */   {
/* 32 */     this(sourceBean, false);
/*    */   }
/*    */ 
/*    */   public SwingPropertyChangeSupport(Object sourceBean, boolean notifyOnEDT)
/*    */   {
/* 44 */     super(sourceBean);
/* 45 */     this.notifyOnEDT = notifyOnEDT;
/*    */   }
/*    */ 
/*    */   public void firePropertyChange(PropertyChangeEvent evt)
/*    */   {
/* 61 */     if (evt == null) {
/* 62 */       throw new NullPointerException();
/*    */     }
/* 64 */     if ((!isNotifyOnEDT()) || (SwingUtilities.isEventDispatchThread()))
/*    */     {
/* 66 */       super.firePropertyChange(evt);
/*    */     }
/*    */     else
/* 69 */       SwingUtilities.invokeLater(new Runnable(evt)
/*    */       {
/*    */         public void run() {
/* 72 */           SwingPropertyChangeSupport.this.firePropertyChange(this.val$evt);
/*    */         }
/*    */       });
/*    */   }
/*    */ 
/*    */   public final boolean isNotifyOnEDT()
/*    */   {
/* 85 */     return this.notifyOnEDT;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.SwingPropertyChangeSupport
 * JD-Core Version:    0.6.0
 */
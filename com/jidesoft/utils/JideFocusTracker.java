/*     */ package com.jidesoft.utils;
/*     */ 
/*     */ import java.awt.AWTEventMulticaster;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.event.ContainerEvent;
/*     */ import java.awt.event.ContainerListener;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ 
/*     */ public class JideFocusTracker
/*     */ {
/*     */   protected Component compHeighest;
/*  24 */   protected FocusListener listenerFocus = null;
/*  25 */   protected ContainerListener listenerContainer = null;
/*     */   protected transient FocusListener listenerMultiCast;
/*     */   protected boolean repeat;
/*     */   protected transient Component lastFocus;
/*     */ 
/*     */   public JideFocusTracker()
/*     */   {
/*  32 */     this.lastFocus = null;
/*  33 */     setRepeating(true);
/*  34 */     this.listenerFocus = new MainFocusListener();
/*  35 */     this.listenerContainer = new MainContainerListener();
/*     */   }
/*     */ 
/*     */   public JideFocusTracker(Component compHeighest) {
/*  39 */     this();
/*     */ 
/*  41 */     setHeighestComponent(compHeighest);
/*     */   }
/*     */ 
/*     */   public void setHeighestComponent(Component compHeighest)
/*     */   {
/*  49 */     Component OldValue = this.compHeighest;
/*     */ 
/*  51 */     if (OldValue != null) {
/*  52 */       synchronized (OldValue.getTreeLock()) {
/*  53 */         removeInternalListeners(OldValue);
/*     */       }
/*     */     }
/*     */ 
/*  57 */     if (compHeighest != null) {
/*  58 */       synchronized (compHeighest.getTreeLock()) {
/*  59 */         addInternalListeners(compHeighest);
/*     */       }
/*     */     }
/*     */ 
/*  63 */     this.compHeighest = compHeighest;
/*     */   }
/*     */ 
/*     */   public Component getHeighestComponent()
/*     */   {
/*  68 */     return this.compHeighest;
/*     */   }
/*     */ 
/*     */   public boolean isRepeating()
/*     */   {
/*  77 */     return this.repeat;
/*     */   }
/*     */ 
/*     */   public void setRepeating(boolean repeat)
/*     */   {
/*  84 */     this.repeat = repeat;
/*     */   }
/*     */ 
/*     */   public synchronized void addFocusListener(FocusListener l) {
/*  88 */     this.listenerMultiCast = AWTEventMulticaster.add(this.listenerMultiCast, l);
/*     */   }
/*     */ 
/*     */   public synchronized void removeFocusListener(FocusListener l) {
/*  92 */     this.listenerMultiCast = AWTEventMulticaster.remove(this.listenerMultiCast, l);
/*     */   }
/*     */ 
/*     */   protected void addInternalListeners(Component component)
/*     */   {
/* 100 */     component.addFocusListener(this.listenerFocus);
/* 101 */     if ((component instanceof Container)) {
/* 102 */       Container container = (Container)component;
/* 103 */       container.addContainerListener(this.listenerContainer);
/* 104 */       for (int i = 0; i < container.getComponentCount(); i++)
/* 105 */         addInternalListeners(container.getComponent(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void removeInternalListeners(Component component)
/*     */   {
/* 111 */     component.removeFocusListener(this.listenerFocus);
/* 112 */     if ((component instanceof Container)) {
/* 113 */       Container container = (Container)component;
/* 114 */       container.removeContainerListener(this.listenerContainer);
/* 115 */       for (int i = 0; i < container.getComponentCount(); i++)
/* 116 */         removeInternalListeners(container.getComponent(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   class MainFocusListener
/*     */     implements FocusListener
/*     */   {
/*     */     MainFocusListener()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void focusGained(FocusEvent e)
/*     */     {
/* 140 */       if ((JideFocusTracker.this.listenerMultiCast != null) && (
/* 141 */         (e.getSource() != JideFocusTracker.this.lastFocus) || (JideFocusTracker.this.isRepeating())))
/* 142 */         JideFocusTracker.this.listenerMultiCast.focusGained(e);
/*     */     }
/*     */ 
/*     */     public void focusLost(FocusEvent e)
/*     */     {
/* 147 */       if ((JideFocusTracker.this.listenerMultiCast != null) && (
/* 148 */         (e.getSource() != JideFocusTracker.this.lastFocus) || (JideFocusTracker.this.isRepeating())))
/* 149 */         JideFocusTracker.this.listenerMultiCast.focusLost(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   class MainContainerListener
/*     */     implements ContainerListener
/*     */   {
/*     */     MainContainerListener()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void componentAdded(ContainerEvent e)
/*     */     {
/* 124 */       synchronized (e.getChild().getTreeLock()) {
/* 125 */         JideFocusTracker.this.addInternalListeners(e.getChild());
/*     */       }
/*     */     }
/*     */ 
/*     */     public void componentRemoved(ContainerEvent e)
/*     */     {
/* 131 */       synchronized (e.getChild().getTreeLock()) {
/* 132 */         JideFocusTracker.this.removeInternalListeners(e.getChild());
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.JideFocusTracker
 * JD-Core Version:    0.6.0
 */
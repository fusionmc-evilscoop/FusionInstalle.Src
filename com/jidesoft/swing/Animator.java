/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.Timer;
/*     */ import javax.swing.event.EventListenerList;
/*     */ 
/*     */ public class Animator
/*     */   implements ActionListener
/*     */ {
/*     */   private final Component _source;
/*     */   private Timer _timer;
/*     */   private final int _totalSteps;
/*     */   private int _currentStep;
/*  32 */   private EventListenerList _listenerList = new EventListenerList();
/*     */ 
/*     */   public Animator(Component source)
/*     */   {
/*  40 */     this(source, 50, 10, 10);
/*     */   }
/*     */ 
/*     */   public Animator(Component source, int initDelay, int delay, int totalSteps)
/*     */   {
/*  53 */     this._source = source;
/*  54 */     this._totalSteps = totalSteps;
/*     */ 
/*  56 */     this._timer = createTimer(delay, this);
/*  57 */     this._timer.setInitialDelay(initDelay);
/*     */   }
/*     */ 
/*     */   protected Timer createTimer(int delay, ActionListener listener)
/*     */   {
/*  68 */     return new Timer(delay, listener);
/*     */   }
/*     */ 
/*     */   public void addAnimatorListener(AnimatorListener l)
/*     */   {
/*  77 */     this._listenerList.add(AnimatorListener.class, l);
/*     */   }
/*     */ 
/*     */   public void removeAnimatorListener(AnimatorListener l)
/*     */   {
/*  86 */     this._listenerList.remove(AnimatorListener.class, l);
/*     */   }
/*     */ 
/*     */   public AnimatorListener[] getAnimatorListeners()
/*     */   {
/*  95 */     return (AnimatorListener[])this._listenerList.getListeners(AnimatorListener.class);
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e) {
/*  99 */     if (this._source != null) {
/* 100 */       if (this._listenerList != null) {
/* 101 */         AnimatorListener[] listeners = getAnimatorListeners();
/* 102 */         for (AnimatorListener listener : listeners) {
/* 103 */           listener.animationFrame(this._source, this._totalSteps, this._currentStep);
/*     */         }
/*     */       }
/*     */ 
/* 107 */       this._currentStep += 1;
/* 108 */       if ((this._totalSteps != -1) && (this._currentStep > this._totalSteps)) {
/* 109 */         stop();
/* 110 */         if (this._listenerList != null) {
/* 111 */           AnimatorListener[] listeners = getAnimatorListeners();
/* 112 */           for (AnimatorListener listener : listeners)
/* 113 */             listener.animationEnds(this._source);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/* 124 */     if (this._listenerList != null) {
/* 125 */       AnimatorListener[] listeners = getAnimatorListeners();
/* 126 */       for (AnimatorListener listener : listeners) {
/* 127 */         listener.animationStarts(this._source);
/*     */       }
/*     */     }
/* 130 */     if (this._timer != null)
/* 131 */       this._timer.start();
/* 132 */     this._currentStep = 0;
/*     */   }
/*     */ 
/*     */   public void stop()
/*     */   {
/* 139 */     if (this._timer != null) {
/* 140 */       this._timer.stop();
/*     */     }
/* 142 */     this._currentStep = 0;
/*     */   }
/*     */ 
/*     */   public void interrupt()
/*     */   {
/* 149 */     if (this._timer != null)
/* 150 */       this._timer.stop();
/*     */   }
/*     */ 
/*     */   public boolean isRunning()
/*     */   {
/* 160 */     return (this._timer != null) && (this._timer.isRunning());
/*     */   }
/*     */ 
/*     */   public void setDelay(int delay) {
/* 164 */     this._timer.setDelay(delay);
/*     */   }
/*     */ 
/*     */   public void dispose() {
/* 168 */     stop();
/* 169 */     this._timer.removeActionListener(this);
/* 170 */     this._timer = null;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.Animator
 * JD-Core Version:    0.6.0
 */
/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.Timer;
/*     */ 
/*     */ public abstract class Flashable
/*     */ {
/*     */   public static final String CLIENT_PROPERTY_FLASHABLE = "jide.flashable";
/*  23 */   private int _interval = 500;
/*     */   protected JComponent _component;
/*     */   protected Animator _animator;
/*  28 */   protected Timer _timer = null;
/*     */   private static boolean _synchronizedFlashFlag;
/*     */   private static Timer _synchronizedFlashTimer;
/*     */ 
/*     */   public Flashable(JComponent component)
/*     */   {
/*  34 */     this._component = component;
/*  35 */     install(this._component);
/*     */   }
/*     */ 
/*     */   public static boolean getSynchronizedFlashFlag()
/*     */   {
/*  56 */     return _synchronizedFlashFlag;
/*     */   }
/*     */ 
/*     */   private void install(JComponent component) {
/*  60 */     this._animator = new Animator(component, 0, getInterval(), -1)
/*     */     {
/*     */       protected Timer createTimer(int delay, ActionListener listener) {
/*  63 */         if (Flashable._synchronizedFlashTimer == null) {
/*  64 */           Flashable.access$102(new Flashable.FlashTimer(delay, listener));
/*     */         }
/*  66 */         Flashable._synchronizedFlashTimer.addActionListener(listener);
/*  67 */         return Flashable._synchronizedFlashTimer;
/*     */       }
/*     */     };
/*  70 */     this._animator.addAnimatorListener(new AnimatorListener()
/*     */     {
/*     */       public void animationStarts(Component component) {
/*     */       }
/*     */ 
/*     */       public void animationFrame(Component component, int totalStep, int step) {
/*  76 */         Flashable.this.flash();
/*     */       }
/*     */ 
/*     */       public void animationEnds(Component component)
/*     */       {
/*     */       }
/*     */     });
/*  83 */     component.putClientProperty("jide.flashable", this);
/*     */   }
/*     */ 
/*     */   public JComponent getComponent()
/*     */   {
/*  92 */     return this._component;
/*     */   }
/*     */ 
/*     */   public void setComponent(JComponent component)
/*     */   {
/* 101 */     JComponent old = this._component;
/* 102 */     if (old != component) {
/* 103 */       this._component.putClientProperty("jide.flashable", null);
/* 104 */       this._component = component;
/* 105 */       this._component.putClientProperty("jide.flashable", this);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getInterval()
/*     */   {
/* 115 */     return this._interval;
/*     */   }
/*     */ 
/*     */   public void setInterval(int interval)
/*     */   {
/* 125 */     int old = this._interval;
/* 126 */     if (old != interval) {
/* 127 */       this._interval = interval;
/* 128 */       getAnimator().setDelay(interval);
/*     */     }
/*     */   }
/*     */ 
/*     */   public abstract void flash();
/*     */ 
/*     */   public abstract void clearFlashing();
/*     */ 
/*     */   protected Animator getAnimator()
/*     */   {
/* 144 */     return this._animator;
/*     */   }
/*     */ 
/*     */   public void startFlashing()
/*     */   {
/* 151 */     clearFlashing();
/* 152 */     getAnimator().start();
/*     */   }
/*     */ 
/*     */   public void stopFlashing()
/*     */   {
/* 159 */     clearFlashing();
/* 160 */     getAnimator().stop();
/*     */   }
/*     */ 
/*     */   public void uninstall()
/*     */   {
/* 169 */     stopFlashing();
/* 170 */     this._animator.dispose();
/* 171 */     this._animator = null;
/* 172 */     getComponent().putClientProperty("jide.flashable", null);
/*     */   }
/*     */ 
/*     */   public boolean isFlashing()
/*     */   {
/* 181 */     return getAnimator().isRunning();
/*     */   }
/*     */ 
/*     */   public static boolean isFlashableInstalled(JComponent component)
/*     */   {
/* 191 */     Object flashable = component.getClientProperty("jide.flashable");
/* 192 */     return flashable instanceof Flashable;
/*     */   }
/*     */ 
/*     */   public static Flashable getFlashable(JComponent component)
/*     */   {
/* 202 */     Object flashable = component.getClientProperty("jide.flashable");
/* 203 */     if ((flashable instanceof Flashable)) {
/* 204 */       return (Flashable)flashable;
/*     */     }
/*     */ 
/* 207 */     return null;
/*     */   }
/*     */ 
/*     */   static class FlashTimer extends Timer
/*     */     implements ActionListener
/*     */   {
/*     */     public FlashTimer(int delay, ActionListener listener)
/*     */     {
/*  40 */       super(listener);
/*  41 */       addActionListener(this);
/*     */     }
/*     */ 
/*     */     public void actionPerformed(ActionEvent e) {
/*  45 */       Flashable.access$002(!Flashable._synchronizedFlashFlag);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.Flashable
 * JD-Core Version:    0.6.0
 */
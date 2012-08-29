/*     */ package com.jidesoft.dialog;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.event.EventListenerList;
/*     */ 
/*     */ public abstract class AbstractPage extends JPanel
/*     */   implements Laziness
/*     */ {
/*  39 */   public static int INVOKE_ON_ALL = -1;
/*     */ 
/*  44 */   public static int INVOKE_ON_NONE = 0;
/*     */ 
/*  49 */   public static int INVOKE_ON_PAINT = 1;
/*     */ 
/*  54 */   public static int INVOKE_ON_REPAINT = 2;
/*     */ 
/*  59 */   public static int INVOKE_ON_UPDATE = 4;
/*     */ 
/*  65 */   public static int INVOKE_ON_VALIDATE = 8;
/*     */ 
/*  67 */   private boolean _allowClosing = true;
/*     */ 
/*  69 */   private int _invokeCondition = INVOKE_ON_PAINT | INVOKE_ON_REPAINT | INVOKE_ON_UPDATE;
/*     */ 
/*  74 */   protected transient PageEvent _pageEvent = null;
/*     */ 
/*  77 */   private boolean _lazyConstructorCalled = false;
/*     */ 
/*  82 */   private static final Logger LOGGER_EVENT = Logger.getLogger(PageEvent.class.getName());
/*     */ 
/*     */   public int getInvokeCondition()
/*     */   {
/* 100 */     return this._invokeCondition;
/*     */   }
/*     */ 
/*     */   public void setInvokeCondition(int invokeCondition)
/*     */   {
/* 109 */     this._invokeCondition = invokeCondition;
/*     */   }
/*     */ 
/*     */   public void invalidate()
/*     */   {
/* 114 */     if ((getInvokeCondition() & INVOKE_ON_VALIDATE) != 0) {
/* 115 */       initialize();
/*     */     }
/* 117 */     super.invalidate();
/*     */   }
/*     */ 
/*     */   public void revalidate()
/*     */   {
/* 122 */     if ((getInvokeCondition() & INVOKE_ON_VALIDATE) != 0) {
/* 123 */       initialize();
/*     */     }
/* 125 */     super.revalidate();
/*     */   }
/*     */ 
/*     */   public void paint(Graphics g)
/*     */   {
/* 130 */     if ((getInvokeCondition() & INVOKE_ON_PAINT) != 0) {
/* 131 */       initialize();
/*     */     }
/*     */ 
/* 134 */     super.paint(g);
/*     */   }
/*     */ 
/*     */   public void paintAll(Graphics g)
/*     */   {
/* 139 */     if ((getInvokeCondition() & INVOKE_ON_PAINT) != 0) {
/* 140 */       initialize();
/*     */     }
/*     */ 
/* 143 */     super.paintAll(g);
/*     */   }
/*     */ 
/*     */   public void paintComponents(Graphics g)
/*     */   {
/* 148 */     if ((getInvokeCondition() & INVOKE_ON_PAINT) != 0) {
/* 149 */       initialize();
/*     */     }
/*     */ 
/* 152 */     super.paintComponents(g);
/*     */   }
/*     */ 
/*     */   public void repaint()
/*     */   {
/* 157 */     if ((getInvokeCondition() & INVOKE_ON_REPAINT) != 0) {
/* 158 */       initialize();
/*     */     }
/* 160 */     super.repaint();
/*     */   }
/*     */ 
/*     */   public void repaint(long l)
/*     */   {
/* 165 */     if ((getInvokeCondition() & INVOKE_ON_REPAINT) != 0) {
/* 166 */       initialize();
/*     */     }
/* 168 */     super.repaint(l);
/*     */   }
/*     */ 
/*     */   public void repaint(int i1, int i2, int i3, int i4)
/*     */   {
/* 173 */     if ((getInvokeCondition() & INVOKE_ON_REPAINT) != 0) {
/* 174 */       initialize();
/*     */     }
/* 176 */     super.repaint(i1, i2, i3, i4);
/*     */   }
/*     */ 
/*     */   public void repaint(long l, int i1, int i2, int i3, int i4)
/*     */   {
/* 181 */     if ((getInvokeCondition() & INVOKE_ON_REPAINT) != 0) {
/* 182 */       initialize();
/*     */     }
/*     */ 
/* 185 */     super.repaint(l, i1, i2, i3, i4);
/*     */   }
/*     */ 
/*     */   public void update(Graphics g)
/*     */   {
/* 190 */     if ((getInvokeCondition() & INVOKE_ON_UPDATE) != 0) {
/* 191 */       initialize();
/*     */     }
/* 193 */     super.update(g);
/*     */   }
/*     */ 
/*     */   public final synchronized void initialize()
/*     */   {
/* 201 */     if (!this._lazyConstructorCalled) {
/* 202 */       this._lazyConstructorCalled = true;
/* 203 */       lazyInitialize();
/* 204 */       validate();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addPageListener(PageListener l)
/*     */   {
/* 214 */     this.listenerList.add(PageListener.class, l);
/*     */   }
/*     */ 
/*     */   public void removePageListener(PageListener l)
/*     */   {
/* 223 */     this.listenerList.remove(PageListener.class, l);
/*     */   }
/*     */ 
/*     */   public PageListener[] getPageListeners()
/*     */   {
/* 233 */     return (PageListener[])this.listenerList.getListeners(PageListener.class);
/*     */   }
/*     */ 
/*     */   public void firePageEvent(int id)
/*     */   {
/* 243 */     firePageEvent(this, id);
/*     */   }
/*     */ 
/*     */   public void firePageEvent(Object source, int id)
/*     */   {
/* 255 */     initialize();
/*     */ 
/* 257 */     if (source == null) {
/* 258 */       source = this;
/*     */     }
/*     */ 
/* 261 */     if (LOGGER_EVENT.isLoggable(Level.FINE)) {
/* 262 */       switch (id) {
/*     */       case 3199:
/* 264 */         LOGGER_EVENT.fine("Page \"" + this + " is opened, source is " + source.getClass().getName());
/* 265 */         break;
/*     */       case 3200:
/* 267 */         LOGGER_EVENT.fine("Page \"" + this + " is closing, source is " + source.getClass().getName());
/* 268 */         break;
/*     */       case 3201:
/* 270 */         LOGGER_EVENT.fine("Page \"" + this + " is closed, source is " + source.getClass().getName());
/* 271 */         break;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 277 */     Object[] listeners = this.listenerList.getListenerList();
/* 278 */     for (int i = listeners.length - 2; i >= 0; i -= 2)
/* 279 */       if (listeners[i] == PageListener.class) {
/* 280 */         this._pageEvent = new PageEvent(source, id);
/* 281 */         ((PageListener)listeners[(i + 1)]).pageEventFired(this._pageEvent);
/*     */       }
/*     */   }
/*     */ 
/*     */   public void setAllowClosing(boolean allowClosing)
/*     */   {
/* 293 */     this._allowClosing = allowClosing;
/*     */   }
/*     */ 
/*     */   public boolean allowClosing()
/*     */   {
/* 304 */     return this._allowClosing;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.dialog.AbstractPage
 * JD-Core Version:    0.6.0
 */
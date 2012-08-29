/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.swing.Timer;
/*     */ import javax.swing.undo.CannotRedoException;
/*     */ import javax.swing.undo.CannotUndoException;
/*     */ import javax.swing.undo.CompoundEdit;
/*     */ import javax.swing.undo.UndoManager;
/*     */ import javax.swing.undo.UndoableEdit;
/*     */ 
/*     */ public class DelayUndoManager extends UndoManager
/*     */ {
/*     */   private static final long serialVersionUID = -2910365359251677780L;
/*  23 */   private int _delay = 500;
/*     */   private CompoundEdit _cache;
/*  26 */   private static final Logger LOGGER = Logger.getLogger(DelayUndoManager.class.getName());
/*     */   protected Timer _timer;
/*     */ 
/*     */   public DelayUndoManager()
/*     */   {
/*     */   }
/*     */ 
/*     */   public DelayUndoManager(int delay)
/*     */   {
/*  33 */     this._delay = delay;
/*     */   }
/*     */ 
/*     */   public boolean isCacheEmpty()
/*     */   {
/*  43 */     return this._cache == null;
/*     */   }
/*     */ 
/*     */   public synchronized void commitCache()
/*     */   {
/*  50 */     if (this._cache != null) {
/*  51 */       this._cache.end();
/*  52 */       addEditWithoutCaching();
/*  53 */       if (LOGGER.isLoggable(Level.FINE)) {
/*  54 */         LOGGER.fine("Commit cache: " + this._cache);
/*     */       }
/*  56 */       this._cache = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addEditWithoutCaching()
/*     */   {
/*  64 */     super.addEdit(this._cache);
/*     */   }
/*     */ 
/*     */   public synchronized void discardCache() {
/*  68 */     this._cache = null;
/*  69 */     if (this._timer != null) {
/*  70 */       this._timer.stop();
/*  71 */       this._timer = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized boolean addEdit(UndoableEdit anEdit)
/*     */   {
/*  77 */     if (this._cache == null) {
/*  78 */       if (LOGGER.isLoggable(Level.FINE)) {
/*  79 */         LOGGER.fine("Create cache: " + anEdit);
/*     */       }
/*  81 */       this._cache = new CompoundEdit();
/*  82 */       boolean ret = this._cache.addEdit(anEdit);
/*  83 */       if (ret) {
/*  84 */         this._timer = new Timer(this._delay, new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/*  86 */             DelayUndoManager.this.commitCache();
/*     */           }
/*     */         });
/*  89 */         this._timer.setRepeats(false);
/*  90 */         this._timer.start();
/*     */       }
/*  92 */       return ret;
/*     */     }
/*     */ 
/*  95 */     if (LOGGER.isLoggable(Level.FINE)) {
/*  96 */       LOGGER.fine("Add to cache: " + anEdit);
/*     */     }
/*  98 */     if (this._timer != null) {
/*  99 */       this._timer.restart();
/*     */     }
/* 101 */     return this._cache.addEdit(anEdit);
/*     */   }
/*     */ 
/*     */   public synchronized boolean canUndo()
/*     */   {
/* 112 */     commitCache();
/* 113 */     return super.canUndo();
/*     */   }
/*     */ 
/*     */   public synchronized boolean canRedo()
/*     */   {
/* 123 */     commitCache();
/* 124 */     return super.canRedo();
/*     */   }
/*     */ 
/*     */   public synchronized void undo()
/*     */     throws CannotUndoException
/*     */   {
/* 134 */     commitCache();
/* 135 */     super.undo();
/*     */   }
/*     */ 
/*     */   public synchronized void redo()
/*     */     throws CannotRedoException
/*     */   {
/* 145 */     commitCache();
/* 146 */     super.redo();
/*     */   }
/*     */ 
/*     */   public synchronized void discardAllEdits()
/*     */   {
/* 151 */     super.discardAllEdits();
/* 152 */     discardCache();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.DelayUndoManager
 * JD-Core Version:    0.6.0
 */
/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public abstract class SelectionModelGroup<T, V>
/*     */   implements Serializable
/*     */ {
/*  19 */   protected List<T> _models = new Vector();
/*     */   protected V _selectionListener;
/*     */ 
/*     */   public SelectionModelGroup()
/*     */   {
/*  27 */     this._selectionListener = createSelectionListener();
/*     */   }
/*     */ 
/*     */   protected abstract V createSelectionListener();
/*     */ 
/*     */   protected abstract void addSelectionListener(T paramT, V paramV);
/*     */ 
/*     */   protected abstract void removeSelectionListener(T paramT, V paramV);
/*     */ 
/*     */   public void add(T model)
/*     */   {
/*  42 */     if (model == null) {
/*  43 */       return;
/*     */     }
/*  45 */     if (!this._models.contains(model)) {
/*  46 */       this._models.add(model);
/*  47 */       addSelectionListener(model, this._selectionListener);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void add(int index, T model)
/*     */   {
/*  58 */     if (model == null) {
/*  59 */       return;
/*     */     }
/*  61 */     if (!this._models.contains(model)) {
/*  62 */       if ((index < 0) || (index > this._models.size())) {
/*  63 */         this._models.add(model);
/*     */       }
/*     */       else {
/*  66 */         this._models.add(index, model);
/*     */       }
/*  68 */       addSelectionListener(model, this._selectionListener);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void remove(T model)
/*     */   {
/*  78 */     if (model == null) {
/*  79 */       return;
/*     */     }
/*  81 */     if (this._models.remove(model))
/*  82 */       removeSelectionListener(model, this._selectionListener);
/*     */   }
/*     */ 
/*     */   public List<T> getElements()
/*     */   {
/*  92 */     return Collections.unmodifiableList(this._models);
/*     */   }
/*     */ 
/*     */   public List<T> getModels()
/*     */   {
/* 101 */     return this._models;
/*     */   }
/*     */ 
/*     */   public int getModelCount()
/*     */   {
/* 110 */     if (this._models == null) {
/* 111 */       return 0;
/*     */     }
/*     */ 
/* 114 */     return this._models.size();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.SelectionModelGroup
 * JD-Core Version:    0.6.0
 */
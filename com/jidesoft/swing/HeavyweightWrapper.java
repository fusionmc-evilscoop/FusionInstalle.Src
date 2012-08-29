/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Panel;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.ComponentListener;
/*     */ import javax.swing.JComponent;
/*     */ 
/*     */ public class HeavyweightWrapper extends Panel
/*     */ {
/*     */   private Component _component;
/*     */   private boolean _heavyweight;
/*  21 */   private final Dimension MIN_DIM = new Dimension(0, 0);
/*     */ 
/*     */   public HeavyweightWrapper(Component component) {
/*  24 */     this(component, false);
/*     */   }
/*     */ 
/*     */   public Dimension getMinimumSize()
/*     */   {
/*  29 */     return this.MIN_DIM;
/*     */   }
/*     */ 
/*     */   public HeavyweightWrapper(Component component, boolean heavyweight) {
/*  33 */     this._component = component;
/*  34 */     if (this._component != null) {
/*  35 */       ((JComponent)this._component).putClientProperty("HeavyweightWrapper", this);
/*  36 */       this._component.addComponentListener(new ComponentListener() {
/*     */         public void componentResized(ComponentEvent e) {
/*     */         }
/*     */ 
/*     */         public void componentMoved(ComponentEvent e) {
/*     */         }
/*     */ 
/*     */         public void componentShown(ComponentEvent e) {
/*  44 */           HeavyweightWrapper.this.setVisible(true);
/*     */         }
/*     */ 
/*     */         public void componentHidden(ComponentEvent e) {
/*  48 */           HeavyweightWrapper.this.setVisible(false);
/*     */         } } );
/*     */     }
/*  52 */     setLayout(new BorderLayout());
/*  53 */     setVisible(false);
/*  54 */     this._heavyweight = heavyweight;
/*     */   }
/*     */ 
/*     */   public boolean isHeavyweight() {
/*  58 */     return this._heavyweight;
/*     */   }
/*     */ 
/*     */   public void setHeavyweight(boolean heavyweight) {
/*  62 */     this._heavyweight = heavyweight;
/*     */   }
/*     */ 
/*     */   public void delegateAdd(Container parent, Object constraints) {
/*  66 */     JideSwingUtilities.removeFromParentWithFocusTransfer(this._component);
/*     */ 
/*  68 */     if (isHeavyweight()) {
/*  69 */       if (this._component.getParent() != this) {
/*  70 */         add(this._component);
/*     */       }
/*     */ 
/*  73 */       if (getParent() != parent) {
/*  74 */         parent.add(this, constraints);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*  79 */       parent.add(this._component, constraints);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delegateRemove(Container parent) {
/*  84 */     JideSwingUtilities.removeFromParentWithFocusTransfer(this._component);
/*     */ 
/*  86 */     if (isHeavyweight()) {
/*  87 */       remove(this._component);
/*  88 */       parent.remove(this);
/*     */     }
/*     */     else
/*     */     {
/*  92 */       parent.remove(this._component);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delegateSetVisible(boolean visible) {
/*  97 */     if (isHeavyweight()) {
/*  98 */       setVisible(visible);
/*  99 */       this._component.setVisible(visible);
/*     */     }
/*     */     else {
/* 102 */       this._component.setVisible(visible);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delegateSetBounds(Rectangle bounds) {
/* 107 */     if (isHeavyweight()) {
/* 108 */       setBounds(bounds);
/* 109 */       this._component.setBounds(0, 0, bounds.width, bounds.height);
/*     */     }
/*     */     else {
/* 112 */       this._component.setBounds(bounds);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delegateSetBounds(int x, int y, int width, int height) {
/* 117 */     if (isHeavyweight()) {
/* 118 */       setBounds(x, y, width, height);
/* 119 */       this._component.setBounds(0, 0, width, height);
/*     */     }
/*     */     else {
/* 122 */       this._component.setBounds(x, y, width, height);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delegateSetLocation(int x, int y) {
/* 127 */     if (isHeavyweight()) {
/* 128 */       setLocation(x, y);
/* 129 */       this._component.setLocation(0, 0);
/*     */     }
/*     */     else {
/* 132 */       this._component.setLocation(x, y);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delegateSetLocation(Point p) {
/* 137 */     if (isHeavyweight()) {
/* 138 */       setLocation(p);
/* 139 */       this._component.setLocation(0, 0);
/*     */     }
/*     */     else {
/* 142 */       this._component.setLocation(p);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delegateSetCursor(Cursor cursor) {
/* 147 */     this._component.setCursor(cursor);
/*     */   }
/*     */ 
/*     */   public void delegateSetNull() {
/* 151 */     ((JComponent)this._component).putClientProperty("HeavyweightWrapper", null);
/* 152 */     this._component = null;
/*     */   }
/*     */ 
/*     */   public Container delegateGetParent() {
/* 156 */     if (isHeavyweight()) {
/* 157 */       return getParent();
/*     */     }
/*     */ 
/* 160 */     return this._component.getParent();
/*     */   }
/*     */ 
/*     */   public boolean delegateIsVisible()
/*     */   {
/* 165 */     if (isHeavyweight()) {
/* 166 */       return isVisible();
/*     */     }
/*     */ 
/* 169 */     return this._component.isVisible();
/*     */   }
/*     */ 
/*     */   public Rectangle delegateGetBounds()
/*     */   {
/* 174 */     if (isHeavyweight()) {
/* 175 */       return getBounds();
/*     */     }
/*     */ 
/* 178 */     return this._component.getBounds();
/*     */   }
/*     */ 
/*     */   public void delegateRepaint()
/*     */   {
/* 183 */     if (isHeavyweight()) {
/* 184 */       repaint();
/* 185 */       this._component.repaint();
/*     */     }
/*     */     else {
/* 188 */       this._component.repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Component getComponent() {
/* 193 */     return this._component;
/*     */   }
/*     */ 
/*     */   public void setComponent(Component component) {
/* 197 */     this._component = component;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.HeavyweightWrapper
 * JD-Core Version:    0.6.0
 */
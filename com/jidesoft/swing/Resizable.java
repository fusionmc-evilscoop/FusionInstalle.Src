/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import java.awt.Color;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.event.MouseInputListener;
/*     */ 
/*     */ public class Resizable
/*     */ {
/*     */   public static final int NONE = 0;
/*     */   public static final int UPPER_LEFT = 1;
/*     */   public static final int UPPER = 2;
/*     */   public static final int UPPER_RIGHT = 4;
/*     */   public static final int RIGHT = 8;
/*     */   public static final int LOWER_RIGHT = 16;
/*     */   public static final int LOWER = 32;
/*     */   public static final int LOWER_LEFT = 64;
/*     */   public static final int LEFT = 128;
/*     */   public static final int ALL = 255;
/*  33 */   private int _resizableCorners = 255;
/*     */ 
/*  35 */   private int _resizeCornerSize = 16;
/*     */   public static final String PROPERTY_RESIZABLE_CORNERS = "resizableCorner";
/*     */   public static final String PROPERTY_RESIZE_CORNER_SIZE = "resizeCornerSize";
/*     */   protected final JComponent _component;
/*     */   private Insets _resizeInsets;
/*     */   private MouseInputListener _mouseInputAdapter;
/*     */   private boolean _topLevel;
/*     */ 
/*     */   public Resizable(JComponent component)
/*     */   {
/*  51 */     this._component = component;
/*  52 */     installListeners();
/*     */   }
/*     */ 
/*     */   public int getResizableCorners()
/*     */   {
/*  61 */     return this._resizableCorners;
/*     */   }
/*     */ 
/*     */   public void setResizableCorners(int resizableCorners)
/*     */   {
/*  71 */     if (this._resizableCorners != resizableCorners) {
/*  72 */       int old = this._resizableCorners;
/*  73 */       this._resizableCorners = resizableCorners;
/*  74 */       this._component.firePropertyChange("resizableCorner", old, this._resizableCorners);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getResizeCornerSize()
/*     */   {
/*  85 */     return this._resizeCornerSize;
/*     */   }
/*     */ 
/*     */   public void setResizeCornerSize(int resizeCornerSize)
/*     */   {
/*  94 */     if (this._resizeCornerSize != resizeCornerSize) {
/*  95 */       int old = this._resizeCornerSize;
/*  96 */       this._resizeCornerSize = resizeCornerSize;
/*  97 */       this._component.firePropertyChange("resizeCornerSize", old, this._resizeCornerSize);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void installListeners()
/*     */   {
/* 106 */     this._mouseInputAdapter = createMouseInputListener();
/* 107 */     this._component.addMouseListener(this._mouseInputAdapter);
/* 108 */     this._component.addMouseMotionListener(this._mouseInputAdapter);
/*     */   }
/*     */ 
/*     */   public void uninstallListeners()
/*     */   {
/* 116 */     this._component.removeMouseListener(this._mouseInputAdapter);
/* 117 */     this._component.removeMouseMotionListener(this._mouseInputAdapter);
/* 118 */     this._mouseInputAdapter = null;
/*     */   }
/*     */ 
/*     */   protected MouseInputListener createMouseInputListener()
/*     */   {
/* 128 */     return new ResizableMouseInputAdapter(this);
/*     */   }
/*     */ 
/*     */   public MouseInputListener getMouseInputAdapter()
/*     */   {
/* 137 */     return this._mouseInputAdapter;
/*     */   }
/*     */ 
/*     */   public void beginResizing(int resizeCorner)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void resizing(int resizeCorner, int newX, int newY, int newW, int newH)
/*     */   {
/* 165 */     Dimension minimumSize = this._component.getMinimumSize();
/* 166 */     Dimension maximumSize = this._component.getMaximumSize();
/* 167 */     if (newW < minimumSize.width) {
/* 168 */       newW = minimumSize.width;
/*     */     }
/* 170 */     if (newH < minimumSize.height) {
/* 171 */       newW = minimumSize.height;
/*     */     }
/* 173 */     if (newW > maximumSize.width) {
/* 174 */       newW = maximumSize.width;
/*     */     }
/* 176 */     if (newH > maximumSize.height) {
/* 177 */       newH = maximumSize.height;
/*     */     }
/* 179 */     this._component.setPreferredSize(new Dimension(newW, newH));
/* 180 */     this._component.getParent().doLayout();
/*     */   }
/*     */ 
/*     */   public void endResizing(int resizeCorner)
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean isTopLevel()
/*     */   {
/* 201 */     return this._topLevel;
/*     */   }
/*     */ 
/*     */   public void setTopLevel(boolean topLevel)
/*     */   {
/* 212 */     this._topLevel = topLevel;
/*     */   }
/*     */ 
/*     */   public JComponent getComponent()
/*     */   {
/* 221 */     return this._component;
/*     */   }
/*     */ 
/*     */   public Insets getResizeInsets()
/*     */   {
/* 231 */     if (this._resizeInsets != null) {
/* 232 */       return this._resizeInsets;
/*     */     }
/* 234 */     return getComponent().getInsets();
/*     */   }
/*     */ 
/*     */   public void setResizeInsets(Insets resizeInsets)
/*     */   {
/* 243 */     this._resizeInsets = resizeInsets;
/*     */   }
/*     */ 
/*     */   public static class ResizeCorner extends JComponent
/*     */   {
/*     */     static final int SIZE = 16;
/* 248 */     private int _corner = 16;
/*     */ 
/*     */     public ResizeCorner() {
/*     */     }
/*     */ 
/*     */     public ResizeCorner(int corner) {
/* 254 */       this._corner = corner;
/*     */     }
/*     */ 
/*     */     public int getCorner() {
/* 258 */       return this._corner;
/*     */     }
/*     */ 
/*     */     public void setCorner(int corner) {
/* 262 */       this._corner = corner;
/*     */     }
/*     */ 
/*     */     public Dimension getPreferredSize()
/*     */     {
/* 267 */       return new Dimension(16, 16);
/*     */     }
/*     */ 
/*     */     protected void paintComponent(Graphics g)
/*     */     {
/* 272 */       super.paintComponent(g);
/* 273 */       int size = Math.min(getWidth(), getHeight());
/* 274 */       int count = Math.min(size / 4, 4);
/* 275 */       Color old = g.getColor();
/* 276 */       int corner = getCorner();
/* 277 */       boolean ltr = getComponentOrientation().isLeftToRight();
/* 278 */       switch (corner) {
/*     */       case 16:
/* 280 */         g.setColor(UIDefaultsLookup.getColor("controlLtHighlight"));
/* 281 */         int delta = 0;
/* 282 */         for (int i = 0; i < count; i++) {
/* 283 */           delta += 4;
/* 284 */           if (ltr) {
/* 285 */             g.drawLine(size, size - delta, size - delta, size);
/*     */           }
/*     */           else {
/* 288 */             g.drawLine(0, delta, size - delta, size);
/*     */           }
/*     */         }
/* 291 */         g.setColor(UIDefaultsLookup.getColor("controlShadow"));
/* 292 */         delta = 0;
/* 293 */         for (int i = 0; i < count; i++) {
/* 294 */           delta += 4;
/* 295 */           if (ltr) {
/* 296 */             g.drawLine(size, size - delta + 1, size - delta + 1, size);
/* 297 */             g.drawLine(size, size - delta + 2, size - delta + 2, size);
/*     */           }
/*     */           else {
/* 300 */             g.drawLine(0, delta + 1, size - delta - 1, size);
/* 301 */             g.drawLine(0, delta + 2, size - delta - 2, size);
/*     */           }
/*     */         }
/*     */ 
/* 305 */         break;
/*     */       case 4:
/* 307 */         g.setColor(UIDefaultsLookup.getColor("controlLtHighlight"));
/* 308 */         int delta = 0;
/* 309 */         for (int i = 0; i < count; i++) {
/* 310 */           delta += 4;
/* 311 */           if (ltr) {
/* 312 */             g.drawLine(size - delta, 0, size, delta);
/*     */           }
/*     */           else {
/* 315 */             g.drawLine(delta, 0, size, size - delta);
/*     */           }
/*     */         }
/* 318 */         g.setColor(UIDefaultsLookup.getColor("controlShadow"));
/* 319 */         delta = 0;
/* 320 */         for (int i = 0; i < count; i++) {
/* 321 */           delta += 4;
/* 322 */           if (ltr) {
/* 323 */             g.drawLine(size - delta + 1, 0, size, delta - 1);
/* 324 */             g.drawLine(size - delta + 2, 0, size, delta - 2);
/*     */           }
/*     */           else {
/* 327 */             g.drawLine(delta + 1, 0, size, size - delta - 1);
/* 328 */             g.drawLine(delta + 2, 0, size, size - delta - 2);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 335 */       g.setColor(old);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.Resizable
 * JD-Core Version:    0.6.0
 */
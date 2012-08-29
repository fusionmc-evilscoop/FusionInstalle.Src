/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.Timer;
/*     */ 
/*     */ public abstract class AutoScroll
/*     */ {
/*     */   protected Timer _timer;
/*  22 */   protected boolean _autoScrolling = false;
/*  23 */   protected int _scrollDirection = 0;
/*     */   protected boolean _hasEntered;
/*     */   public static final int SCROLL_UP = 0;
/*     */   public static final int SCROLL_DOWN = 1;
/*     */   public static final int SCROLL_LEFT = 2;
/*     */   public static final int SCROLL_RIGHT = 4;
/*     */   protected Component _component;
/*  32 */   protected boolean _vertical = true;
/*     */ 
/*  34 */   protected int _autoScrollInterval = 100;
/*  35 */   private boolean _componentSelfScrollable = true;
/*     */ 
/*     */   protected AutoScroll(Component component) {
/*  38 */     this._component = component;
/*     */   }
/*     */ 
/*     */   protected AutoScroll(Component component, boolean vertical) {
/*  42 */     this._component = component;
/*  43 */     this._vertical = vertical;
/*     */   }
/*     */ 
/*     */   public int getAutoScrollInterval() {
/*  47 */     return this._autoScrollInterval;
/*     */   }
/*     */ 
/*     */   public void setAutoScrollInterval(int autoScrollInterval) {
/*  51 */     this._autoScrollInterval = autoScrollInterval;
/*     */   }
/*     */ 
/*     */   public void startAutoScrolling(int direction)
/*     */   {
/*  67 */     if (this._autoScrolling) {
/*  68 */       this._timer.stop();
/*     */     }
/*     */ 
/*  71 */     this._autoScrolling = true;
/*  72 */     this._scrollDirection = direction;
/*  73 */     autoScrollingStarted(this._scrollDirection);
/*  74 */     this._timer = new Timer(this._autoScrollInterval, new AutoScrollActionHandler(this._scrollDirection));
/*  75 */     this._timer.start();
/*     */   }
/*     */ 
/*     */   public void stopAutoScrolling()
/*     */   {
/*  82 */     this._autoScrolling = false;
/*     */ 
/*  84 */     if (this._timer != null) {
/*  85 */       this._timer.stop();
/*  86 */       this._timer = null;
/*     */     }
/*     */ 
/*  89 */     autoScrollingEnded(this._scrollDirection);
/*     */   }
/*     */ 
/*     */   public boolean isAutoScrolling() {
/*  93 */     return this._autoScrolling;
/*     */   }
/*     */ 
/*     */   public int getScrollDirection() {
/*  97 */     return this._scrollDirection;
/*     */   }
/*     */ 
/*     */   public boolean isComponentSelfScrollable() {
/* 101 */     return this._componentSelfScrollable;
/*     */   }
/*     */ 
/*     */   public void setComponentSelfScrollable(boolean scrollable) {
/* 105 */     this._componentSelfScrollable = scrollable;
/*     */   }
/*     */ 
/*     */   protected MouseEvent convertMouseEvent(MouseEvent e) {
/* 109 */     if (e.getSource() == this._component) {
/* 110 */       return e;
/*     */     }
/*     */ 
/* 113 */     Point convertedPoint = SwingUtilities.convertPoint((Component)e.getSource(), e.getPoint(), this._component);
/*     */ 
/* 115 */     return new MouseEvent((Component)e.getSource(), e.getID(), e.getWhen(), e.getModifiers(), convertedPoint.x, convertedPoint.y, e.getClickCount(), e.isPopupTrigger());
/*     */   }
/*     */ 
/*     */   public void mouseReleased(MouseEvent e)
/*     */   {
/* 127 */     this._hasEntered = false;
/* 128 */     stopAutoScrolling();
/*     */   }
/*     */ 
/*     */   public void mousePressed(MouseEvent e) {
/* 132 */     stopAutoScrolling();
/*     */   }
/*     */ 
/*     */   public void mouseDragged(MouseEvent e) {
/* 136 */     if ((this._componentSelfScrollable) && (e.getSource() == this._component)) {
/* 137 */       return;
/*     */     }
/* 139 */     if (this._component.isVisible()) {
/* 140 */       MouseEvent newEvent = convertMouseEvent(e);
/* 141 */       Rectangle r = new Rectangle();
/* 142 */       if ((this._component instanceof JComponent)) {
/* 143 */         ((JComponent)this._component).computeVisibleRect(r);
/*     */       }
/*     */       else {
/* 146 */         r = this._component.getBounds();
/*     */       }
/* 148 */       if ((newEvent.getPoint().y >= r.y) && (newEvent.getPoint().y <= r.y + r.height - 1) && (newEvent.getPoint().x >= r.x) && (newEvent.getPoint().x <= r.x + r.width - 1))
/*     */       {
/* 150 */         this._hasEntered = true;
/* 151 */         if (this._autoScrolling) {
/* 152 */           stopAutoScrolling();
/*     */         }
/* 154 */         Point location = newEvent.getPoint();
/* 155 */         if (r.contains(location)) {
/* 156 */           updateSelectionForEvent(newEvent, false);
/*     */         }
/*     */ 
/*     */       }
/* 160 */       else if (this._hasEntered)
/*     */       {
/*     */         int directionToScroll;
/*     */         int directionToScroll;
/* 162 */         if (newEvent.getPoint().y < r.y) {
/* 163 */           directionToScroll = 0;
/*     */         }
/*     */         else
/*     */         {
/*     */           int directionToScroll;
/* 165 */           if (newEvent.getPoint().x < r.x) {
/* 166 */             directionToScroll = 2;
/*     */           }
/*     */           else
/*     */           {
/*     */             int directionToScroll;
/* 168 */             if (newEvent.getPoint().y > r.y + r.height) {
/* 169 */               directionToScroll = 1;
/*     */             }
/*     */             else
/* 172 */               directionToScroll = 4;
/*     */           }
/*     */         }
/* 175 */         if ((this._autoScrolling) && (this._scrollDirection != directionToScroll)) {
/* 176 */           stopAutoScrolling();
/* 177 */           startAutoScrolling(directionToScroll);
/*     */         }
/* 179 */         else if (!this._autoScrolling) {
/* 180 */           startAutoScrolling(directionToScroll);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void mouseMoved(MouseEvent e)
/*     */   {
/* 188 */     if (e.getSource() == this._component) {
/* 189 */       Point location = e.getPoint();
/* 190 */       Rectangle r = new Rectangle();
/* 191 */       if ((this._component instanceof JComponent)) {
/* 192 */         ((JComponent)this._component).computeVisibleRect(r);
/* 193 */         if (r.contains(location))
/* 194 */           updateSelectionForEvent(e, false);
/*     */       }
/*     */       else
/*     */       {
/* 198 */         updateSelectionForEvent(e, false);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void autoScrollingStarted(int direction)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void autoScrollingEnded(int direction)
/*     */   {
/*     */   }
/*     */ 
/*     */   public abstract void autoScrolling(int paramInt);
/*     */ 
/*     */   public abstract void updateSelectionForEvent(MouseEvent paramMouseEvent, boolean paramBoolean);
/*     */ 
/*     */   private class AutoScrollActionHandler
/*     */     implements ActionListener
/*     */   {
/*     */     private int _direction;
/*     */ 
/*     */     AutoScrollActionHandler(int direction)
/*     */     {
/*  58 */       this._direction = direction;
/*     */     }
/*     */ 
/*     */     public void actionPerformed(ActionEvent e) {
/*  62 */       AutoScroll.this.autoScrolling(this._direction);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.AutoScroll
 * JD-Core Version:    0.6.0
 */
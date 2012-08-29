/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.BoundedRangeModel;
/*     */ import javax.swing.JScrollBar;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.Timer;
/*     */ 
/*     */ public class MarqueePane extends JScrollPane
/*     */ {
/*  21 */   private int _scrollDelay = 100;
/*  22 */   private int _stayDelay = 2000;
/*  23 */   private int _scrollAmount = 2;
/*  24 */   private int _scrollDirection = 0;
/*  25 */   private int _stayPosition = -1;
/*     */ 
/*  27 */   private Timer _scrollTimer = null;
/*     */   private int _scrollTimes;
/*  29 */   private boolean _reachStayPosition = false;
/*     */   public static final int SCROLL_DIRECTION_LEFT = 0;
/*     */   public static final int SCROLL_DIRECTION_RIGHT = 1;
/*     */   public static final int SCROLL_DIRECTION_UP = 2;
/*     */   public static final int SCROLL_DIRECTION_DOWN = 3;
/*     */ 
/*     */   public MarqueePane(Component view)
/*     */   {
/*  37 */     super(view);
/*  38 */     startAutoScrolling();
/*     */   }
/*     */ 
/*     */   public MarqueePane()
/*     */   {
/*  43 */     startAutoScrolling();
/*     */   }
/*     */ 
/*     */   public void updateUI()
/*     */   {
/*  48 */     super.updateUI();
/*  49 */     setViewportBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/*  50 */     setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/*  51 */     setVerticalScrollBarPolicy(21);
/*  52 */     setHorizontalScrollBarPolicy(31);
/*  53 */     setWheelScrollingEnabled(false);
/*     */   }
/*     */ 
/*     */   public int getScrollDelay()
/*     */   {
/*  64 */     return this._scrollDelay;
/*     */   }
/*     */ 
/*     */   public void setScrollDelay(int scrollDelay)
/*     */   {
/*  73 */     this._scrollDelay = scrollDelay;
/*     */   }
/*     */ 
/*     */   public int getScrollAmount()
/*     */   {
/*  84 */     return this._scrollAmount;
/*     */   }
/*     */ 
/*     */   public void setScrollAmount(int scrollAmount)
/*     */   {
/*  93 */     this._scrollAmount = scrollAmount;
/*     */   }
/*     */ 
/*     */   public int getScrollDirection()
/*     */   {
/* 107 */     return this._scrollDirection;
/*     */   }
/*     */ 
/*     */   public void setScrollDirection(int scrollDirection)
/*     */   {
/* 116 */     this._scrollDirection = scrollDirection;
/*     */   }
/*     */ 
/*     */   public int getStayDelay()
/*     */   {
/* 127 */     return this._stayDelay;
/*     */   }
/*     */ 
/*     */   public void setStayDelay(int stayDelay)
/*     */   {
/* 136 */     this._stayDelay = stayDelay;
/*     */   }
/*     */ 
/*     */   public void stopAutoScrolling()
/*     */   {
/* 143 */     if (this._scrollTimer != null) {
/* 144 */       if (this._scrollTimer.isRunning()) {
/* 145 */         this._scrollTimer.stop();
/*     */       }
/* 147 */       this._scrollTimer = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void startAutoScrolling()
/*     */   {
/* 155 */     stopAutoScrolling();
/* 156 */     this._scrollTimer = new Timer(getScrollDelay(), new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/*     */         BoundedRangeModel rangeModel;
/*     */         BoundedRangeModel rangeModel;
/* 159 */         if ((MarqueePane.this.getScrollDirection() == 0) || (MarqueePane.this.getScrollDirection() == 1)) {
/* 160 */           rangeModel = MarqueePane.this.getHorizontalScrollBar().getModel();
/*     */         }
/*     */         else {
/* 163 */           rangeModel = MarqueePane.this.getVerticalScrollBar().getModel();
/*     */         }
/* 165 */         int value = rangeModel.getValue();
/*     */ 
/* 167 */         if ((MarqueePane.this.getScrollDirection() == 0) || (MarqueePane.this.getScrollDirection() == 2)) {
/* 168 */           if (value + rangeModel.getExtent() == rangeModel.getMaximum()) {
/* 169 */             rangeModel.setValue(rangeModel.getMinimum());
/* 170 */             MarqueePane.access$002(MarqueePane.this, 0);
/*     */           }
/* 172 */           else if (value + MarqueePane.this.getScrollAmount() + rangeModel.getExtent() > rangeModel.getMaximum()) {
/* 173 */             rangeModel.setValue(rangeModel.getMaximum() - rangeModel.getExtent());
/*     */           }
/*     */           else {
/* 176 */             rangeModel.setValue(value + MarqueePane.this.getScrollAmount());
/* 177 */             MarqueePane.access$008(MarqueePane.this);
/*     */           }
/* 179 */           MarqueePane.access$102(MarqueePane.this, rangeModel.getValue() == rangeModel.getMinimum());
/*     */         }
/*     */         else {
/* 182 */           if (value == rangeModel.getMinimum()) {
/* 183 */             int maximum = rangeModel.getMaximum();
/* 184 */             int extent = rangeModel.getExtent();
/* 185 */             rangeModel.setValue(rangeModel.getMaximum() - rangeModel.getExtent());
/*     */ 
/* 187 */             if ((maximum != rangeModel.getMaximum()) || (extent != rangeModel.getExtent())) {
/* 188 */               rangeModel.setValue(rangeModel.getMaximum() - rangeModel.getExtent());
/*     */             }
/* 190 */             MarqueePane.access$002(MarqueePane.this, 0);
/*     */           }
/* 192 */           else if (value - MarqueePane.this.getScrollAmount() < rangeModel.getMinimum()) {
/* 193 */             rangeModel.setValue(rangeModel.getMinimum());
/*     */           }
/*     */           else {
/* 196 */             rangeModel.setValue(value - MarqueePane.this.getScrollAmount());
/* 197 */             MarqueePane.access$008(MarqueePane.this);
/*     */           }
/* 199 */           MarqueePane.access$102(MarqueePane.this, (rangeModel.getValue() == rangeModel.getMinimum()) || (rangeModel.getValue() == rangeModel.getMaximum()));
/*     */         }
/* 201 */         if ((!MarqueePane.this._reachStayPosition) && (MarqueePane.this.getStayPosition() >= 0) && 
/* 202 */           (MarqueePane.this._scrollTimes % MarqueePane.this.getStayPosition() == 0)) {
/* 203 */           MarqueePane.access$102(MarqueePane.this, true);
/*     */         }
/*     */ 
/* 206 */         if (MarqueePane.this._scrollTimer != null)
/* 207 */           MarqueePane.this._scrollTimer.setDelay(MarqueePane.this._reachStayPosition ? MarqueePane.this.getStayDelay() : MarqueePane.this.getScrollDelay());
/*     */       }
/*     */     });
/* 211 */     this._scrollTimer.start();
/*     */   }
/*     */ 
/*     */   public int getStayPosition()
/*     */   {
/* 224 */     return this._stayPosition;
/*     */   }
/*     */ 
/*     */   public void setStayPosition(int stayPosition)
/*     */   {
/* 233 */     this._stayPosition = stayPosition;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.MarqueePane
 * JD-Core Version:    0.6.0
 */
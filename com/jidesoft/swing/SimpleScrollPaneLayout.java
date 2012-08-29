/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JViewport;
/*     */ import javax.swing.ScrollPaneLayout;
/*     */ import javax.swing.Scrollable;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.UIResource;
/*     */ 
/*     */ class SimpleScrollPaneLayout extends ScrollPaneLayout
/*     */ {
/*     */   protected AbstractButton _scrollUp;
/*     */   protected AbstractButton _scrollDown;
/*     */   protected AbstractButton _scrollLeft;
/*     */   protected AbstractButton _scrollRight;
/*     */ 
/*     */   public void syncWithScrollPane(JScrollPane sp)
/*     */   {
/*  24 */     super.syncWithScrollPane(sp);
/*  25 */     if ((sp instanceof SimpleScrollPane)) {
/*  26 */       this._scrollUp = ((SimpleScrollPane)sp).getScrollUpButton();
/*  27 */       this._scrollDown = ((SimpleScrollPane)sp).getScrollDownButton();
/*  28 */       this._scrollLeft = ((SimpleScrollPane)sp).getScrollLeftButton();
/*  29 */       this._scrollRight = ((SimpleScrollPane)sp).getScrollRightButton();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addLayoutComponent(String s, Component c)
/*     */   {
/*  35 */     if ("SCROLL_UP_BUTTON".equals(s)) {
/*  36 */       this._scrollUp = ((AbstractButton)addSingletonComponent(this._scrollUp, c));
/*     */     }
/*  38 */     else if ("SCROLL_DOWN_BUTTON".equals(s)) {
/*  39 */       this._scrollDown = ((AbstractButton)addSingletonComponent(this._scrollDown, c));
/*     */     }
/*  41 */     else if ("SCROLL_LEFT_BUTTON".equals(s)) {
/*  42 */       this._scrollLeft = ((AbstractButton)addSingletonComponent(this._scrollLeft, c));
/*     */     }
/*  44 */     else if ("SCROLL_RIGHT_BUTTON".equals(s)) {
/*  45 */       this._scrollRight = ((AbstractButton)addSingletonComponent(this._scrollRight, c));
/*     */     }
/*     */     else
/*  48 */       super.addLayoutComponent(s, c);
/*     */   }
/*     */ 
/*     */   public void removeLayoutComponent(Component c)
/*     */   {
/*  54 */     if (c == this._scrollUp) {
/*  55 */       this._scrollUp = null;
/*     */     }
/*  57 */     else if (c == this._scrollDown) {
/*  58 */       this._scrollDown = null;
/*     */     }
/*  60 */     else if (c == this._scrollLeft) {
/*  61 */       this._scrollLeft = null;
/*     */     }
/*  63 */     else if (c == this._scrollRight) {
/*  64 */       this._scrollRight = null;
/*     */     }
/*     */     else
/*  67 */       super.removeLayoutComponent(c);
/*     */   }
/*     */ 
/*     */   public Dimension preferredLayoutSize(Container parent)
/*     */   {
/*  91 */     JScrollPane scrollPane = (JScrollPane)parent;
/*  92 */     this.vsbPolicy = scrollPane.getVerticalScrollBarPolicy();
/*  93 */     this.hsbPolicy = scrollPane.getHorizontalScrollBarPolicy();
/*     */ 
/*  95 */     Insets insets = parent.getInsets();
/*  96 */     int prefWidth = insets.left + insets.right;
/*  97 */     int prefHeight = insets.top + insets.bottom;
/*     */ 
/* 104 */     Dimension extentSize = null;
/* 105 */     Dimension viewSize = null;
/* 106 */     Component view = null;
/*     */ 
/* 108 */     if (this.viewport != null) {
/* 109 */       extentSize = this.viewport.getPreferredSize();
/* 110 */       viewSize = this.viewport.getViewSize();
/* 111 */       view = this.viewport.getView();
/*     */     }
/*     */ 
/* 117 */     if (extentSize != null) {
/* 118 */       prefWidth += extentSize.width;
/* 119 */       prefHeight += extentSize.height;
/*     */     }
/*     */ 
/* 125 */     Border viewportBorder = scrollPane.getViewportBorder();
/* 126 */     if (viewportBorder != null) {
/* 127 */       Insets vpbInsets = viewportBorder.getBorderInsets(parent);
/* 128 */       prefWidth += vpbInsets.left + vpbInsets.right;
/* 129 */       prefHeight += vpbInsets.top + vpbInsets.bottom;
/*     */     }
/*     */ 
/* 149 */     if ((this._scrollUp != null) && (this._scrollDown != null) && (this.vsbPolicy != 21)) {
/* 150 */       if (this.vsbPolicy == 22) {
/* 151 */         prefHeight += (this._scrollUp.isVisible() ? this._scrollUp.getPreferredSize().height : 0);
/* 152 */         prefHeight += (this._scrollDown.isVisible() ? this._scrollDown.getPreferredSize().height : 0);
/*     */       }
/* 154 */       else if ((viewSize != null) && (extentSize != null)) {
/* 155 */         boolean canScroll = true;
/* 156 */         if ((view instanceof Scrollable)) {
/* 157 */           canScroll = !((Scrollable)view).getScrollableTracksViewportHeight();
/*     */         }
/* 159 */         if ((canScroll) && (viewSize.height > extentSize.height)) {
/* 160 */           prefHeight += (this._scrollUp.isVisible() ? this._scrollUp.getPreferredSize().height : 0);
/* 161 */           prefHeight += (this._scrollDown.isVisible() ? this._scrollDown.getPreferredSize().height : 0);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 166 */     if ((this._scrollLeft != null) && (this._scrollRight != null) && (this.hsbPolicy != 31)) {
/* 167 */       if (this.hsbPolicy == 32) {
/* 168 */         prefWidth += (this._scrollLeft.isVisible() ? this._scrollLeft.getPreferredSize().width : 0);
/* 169 */         prefWidth += (this._scrollRight.isVisible() ? this._scrollRight.getPreferredSize().width : 0);
/*     */       }
/* 171 */       else if ((viewSize != null) && (extentSize != null)) {
/* 172 */         boolean canScroll = true;
/* 173 */         if ((view instanceof Scrollable)) {
/* 174 */           canScroll = !((Scrollable)view).getScrollableTracksViewportWidth();
/*     */         }
/* 176 */         if ((canScroll) && (viewSize.width > extentSize.width)) {
/* 177 */           prefWidth += (this._scrollLeft.isVisible() ? this._scrollLeft.getPreferredSize().width : 0);
/* 178 */           prefWidth += (this._scrollRight.isVisible() ? this._scrollRight.getPreferredSize().width : 0);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 183 */     return new Dimension(prefWidth, prefHeight);
/*     */   }
/*     */ 
/*     */   public Dimension minimumLayoutSize(Container parent)
/*     */   {
/* 201 */     JScrollPane scrollPane = (JScrollPane)parent;
/* 202 */     this.vsbPolicy = scrollPane.getVerticalScrollBarPolicy();
/* 203 */     this.hsbPolicy = scrollPane.getHorizontalScrollBarPolicy();
/*     */ 
/* 205 */     Insets insets = parent.getInsets();
/* 206 */     int minWidth = insets.left + insets.right;
/* 207 */     int minHeight = insets.top + insets.bottom;
/*     */ 
/* 212 */     if (this.viewport != null) {
/* 213 */       Dimension size = this.viewport.getMinimumSize();
/* 214 */       minWidth += size.width;
/* 215 */       minHeight += size.height;
/*     */     }
/*     */ 
/* 221 */     Border viewportBorder = scrollPane.getViewportBorder();
/* 222 */     if (viewportBorder != null) {
/* 223 */       Insets vpbInsets = viewportBorder.getBorderInsets(parent);
/* 224 */       minWidth += vpbInsets.left + vpbInsets.right;
/* 225 */       minHeight += vpbInsets.top + vpbInsets.bottom;
/*     */     }
/*     */ 
/* 232 */     if ((this._scrollUp != null) && (this._scrollDown != null) && (this.vsbPolicy != 21)) {
/* 233 */       Dimension size = new Dimension(Math.max(this._scrollUp.getMinimumSize().width, this._scrollDown.getMinimumSize().width), 0);
/* 234 */       size.height += (this._scrollUp.isVisible() ? this._scrollUp.getMinimumSize().height : 0);
/* 235 */       size.height += (this._scrollDown.isVisible() ? this._scrollDown.getMinimumSize().height : 0);
/* 236 */       minHeight += size.height;
/* 237 */       minWidth = Math.max(minWidth, size.width);
/*     */     }
/*     */ 
/* 240 */     if ((this._scrollLeft != null) && (this._scrollLeft != null) && (this.hsbPolicy != 31)) {
/* 241 */       Dimension size = new Dimension(0, Math.max(this._scrollLeft.getMinimumSize().height, this._scrollRight.getMinimumSize().height));
/* 242 */       size.width += (this._scrollLeft.isVisible() ? this._scrollLeft.getMinimumSize().width : 0);
/* 243 */       size.width += (this._scrollRight.isVisible() ? this._scrollRight.getMinimumSize().width : 0);
/* 244 */       minWidth += size.width;
/* 245 */       minHeight = Math.max(minHeight, size.height);
/*     */     }
/*     */ 
/* 248 */     return new Dimension(minWidth, minHeight);
/*     */   }
/*     */ 
/*     */   public void layoutContainer(Container parent)
/*     */   {
/* 290 */     JScrollPane scrollPane = (JScrollPane)parent;
/* 291 */     this.vsbPolicy = scrollPane.getVerticalScrollBarPolicy();
/* 292 */     this.hsbPolicy = scrollPane.getHorizontalScrollBarPolicy();
/*     */ 
/* 294 */     Rectangle availR = scrollPane.getBounds();
/* 295 */     availR.x = (availR.y = 0);
/*     */ 
/* 297 */     Insets insets = parent.getInsets();
/* 298 */     availR.x = insets.left;
/* 299 */     availR.y = insets.top;
/* 300 */     availR.width -= insets.left + insets.right;
/* 301 */     availR.height -= insets.top + insets.bottom;
/*     */ 
/* 307 */     Border viewportBorder = scrollPane.getViewportBorder();
/*     */     Insets vpbInsets;
/* 309 */     if (viewportBorder != null) {
/* 310 */       Insets vpbInsets = viewportBorder.getBorderInsets(parent);
/* 311 */       availR.x += vpbInsets.left;
/* 312 */       availR.y += vpbInsets.top;
/* 313 */       availR.width -= vpbInsets.left + vpbInsets.right;
/* 314 */       availR.height -= vpbInsets.top + vpbInsets.bottom;
/*     */     }
/*     */     else {
/* 317 */       vpbInsets = new Insets(0, 0, 0, 0);
/*     */     }
/*     */ 
/* 341 */     Component view = this.viewport != null ? this.viewport.getView() : null;
/* 342 */     Dimension viewPrefSize = view != null ? view.getPreferredSize() : new Dimension(0, 0);
/*     */ 
/* 346 */     Dimension extentSize = this.viewport != null ? this.viewport.toViewCoordinates(availR.getSize()) : new Dimension(0, 0);
/*     */ 
/* 350 */     boolean viewTracksViewportWidth = false;
/* 351 */     boolean viewTracksViewportHeight = false;
/* 352 */     boolean isEmpty = (availR.width < 0) || (availR.height < 0);
/*     */     Scrollable sv;
/* 357 */     if ((!isEmpty) && ((view instanceof Scrollable))) {
/* 358 */       Scrollable sv = (Scrollable)view;
/* 359 */       viewTracksViewportWidth = sv.getScrollableTracksViewportWidth();
/* 360 */       viewTracksViewportHeight = sv.getScrollableTracksViewportHeight();
/*     */     }
/*     */     else {
/* 363 */       sv = null;
/*     */     }
/*     */ 
/* 371 */     Rectangle scrollUpR = new Rectangle(0, 0, 0, this._scrollUp.getPreferredSize().height);
/* 372 */     Rectangle scrollDownR = new Rectangle(0, 0, 0, this._scrollDown.getPreferredSize().height);
/*     */     boolean vsbNeeded;
/*     */     boolean vsbNeeded;
/* 375 */     if (isEmpty) {
/* 376 */       vsbNeeded = false;
/*     */     }
/*     */     else
/*     */     {
/*     */       boolean vsbNeeded;
/* 378 */       if (this.vsbPolicy == 22) {
/* 379 */         vsbNeeded = true;
/*     */       }
/*     */       else
/*     */       {
/*     */         boolean vsbNeeded;
/* 381 */         if (this.vsbPolicy == 21) {
/* 382 */           vsbNeeded = false;
/*     */         }
/*     */         else {
/* 385 */           if (!this._scrollUp.isEnabled()) {
/* 386 */             scrollUpR.height = 0;
/*     */           }
/* 388 */           if (!this._scrollDown.isEnabled()) {
/* 389 */             scrollDownR.height = 0;
/*     */           }
/* 391 */           vsbNeeded = (!viewTracksViewportHeight) && (viewPrefSize.height > extentSize.height);
/*     */         }
/*     */       }
/*     */     }
/* 395 */     if ((this._scrollUp != null) && (this._scrollDown != null) && (vsbNeeded)) {
/* 396 */       adjustForScrollUpAndDown(true, availR, scrollUpR, scrollDownR, vpbInsets);
/* 397 */       extentSize = this.viewport.toViewCoordinates(availR.getSize());
/*     */     }
/*     */ 
/* 405 */     Rectangle scrollLeftR = new Rectangle(0, 0, this._scrollLeft.getPreferredSize().width, 0);
/* 406 */     Rectangle scrollRightR = new Rectangle(0, 0, this._scrollRight.getPreferredSize().width, 0);
/*     */     boolean hsbNeeded;
/*     */     boolean hsbNeeded;
/* 408 */     if (isEmpty) {
/* 409 */       hsbNeeded = false;
/*     */     }
/*     */     else
/*     */     {
/*     */       boolean hsbNeeded;
/* 411 */       if (this.hsbPolicy == 32) {
/* 412 */         hsbNeeded = true;
/*     */       }
/*     */       else
/*     */       {
/*     */         boolean hsbNeeded;
/* 414 */         if (this.hsbPolicy == 31) {
/* 415 */           hsbNeeded = false;
/*     */         }
/*     */         else {
/* 418 */           if (!this._scrollLeft.isEnabled()) {
/* 419 */             scrollLeftR.width = 0;
/*     */           }
/* 421 */           if (!this._scrollRight.isEnabled()) {
/* 422 */             scrollRightR.width = 0;
/*     */           }
/* 424 */           hsbNeeded = (!viewTracksViewportWidth) && (viewPrefSize.width > extentSize.width);
/*     */         }
/*     */       }
/*     */     }
/* 427 */     if ((this.hsb != null) && (hsbNeeded)) {
/* 428 */       adjustForScrollLeftAndRight(true, availR, scrollLeftR, scrollRightR, vpbInsets);
/*     */ 
/* 436 */       if ((this._scrollUp != null) && (this._scrollDown != null) && (!vsbNeeded) && (this.vsbPolicy != 21))
/*     */       {
/* 439 */         extentSize = this.viewport.toViewCoordinates(availR.getSize());
/* 440 */         vsbNeeded = viewPrefSize.height > extentSize.height;
/*     */ 
/* 442 */         if (vsbNeeded) {
/* 443 */           adjustForScrollUpAndDown(true, availR, scrollUpR, scrollDownR, vpbInsets);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 455 */     if (this.viewport != null) {
/* 456 */       this.viewport.setBounds(availR);
/*     */ 
/* 458 */       if (sv != null) {
/* 459 */         extentSize = this.viewport.toViewCoordinates(availR.getSize());
/*     */ 
/* 461 */         boolean oldHSBNeeded = hsbNeeded;
/* 462 */         boolean oldVSBNeeded = vsbNeeded;
/* 463 */         viewTracksViewportWidth = sv.getScrollableTracksViewportWidth();
/*     */ 
/* 465 */         viewTracksViewportHeight = sv.getScrollableTracksViewportHeight();
/*     */ 
/* 467 */         if ((this.vsb != null) && (this.vsbPolicy == 20)) {
/* 468 */           boolean newVSBNeeded = (!viewTracksViewportHeight) && (viewPrefSize.height > extentSize.height);
/*     */ 
/* 470 */           if (newVSBNeeded != vsbNeeded) {
/* 471 */             vsbNeeded = newVSBNeeded;
/* 472 */             adjustForScrollUpAndDown(vsbNeeded, availR, scrollUpR, scrollDownR, vpbInsets);
/* 473 */             extentSize = this.viewport.toViewCoordinates(availR.getSize());
/*     */           }
/*     */         }
/* 476 */         if ((this.hsb != null) && (this.hsbPolicy == 30)) {
/* 477 */           boolean newHSBbNeeded = (!viewTracksViewportWidth) && (viewPrefSize.width > extentSize.width);
/*     */ 
/* 479 */           if (newHSBbNeeded != hsbNeeded) {
/* 480 */             hsbNeeded = newHSBbNeeded;
/* 481 */             adjustForScrollLeftAndRight(hsbNeeded, availR, scrollLeftR, scrollRightR, vpbInsets);
/* 482 */             if ((this.vsb != null) && (!vsbNeeded) && (this.vsbPolicy != 21))
/*     */             {
/* 485 */               extentSize = this.viewport.toViewCoordinates(availR.getSize());
/*     */ 
/* 487 */               vsbNeeded = viewPrefSize.height > extentSize.height;
/*     */ 
/* 490 */               if (vsbNeeded) {
/* 491 */                 adjustForScrollUpAndDown(true, availR, scrollUpR, scrollDownR, vpbInsets);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 496 */         if ((oldHSBNeeded != hsbNeeded) || (oldVSBNeeded != vsbNeeded))
/*     */         {
/* 498 */           this.viewport.setBounds(availR);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 512 */     if ((this._scrollUp != null) && (this._scrollDown != null)) {
/* 513 */       if (vsbNeeded) {
/* 514 */         this._scrollUp.setVisible(true);
/* 515 */         this._scrollDown.setVisible(true);
/* 516 */         this._scrollUp.setBounds(scrollUpR);
/* 517 */         this._scrollDown.setBounds(scrollDownR);
/*     */       }
/*     */       else {
/* 520 */         this._scrollUp.setVisible(false);
/* 521 */         this._scrollDown.setVisible(false);
/* 522 */         this._scrollUp.setBounds(scrollUpR.x, scrollUpR.y, 0, 0);
/* 523 */         this._scrollDown.setBounds(scrollDownR.x, scrollDownR.y, 0, 0);
/*     */       }
/*     */     }
/*     */ 
/* 527 */     if ((this._scrollLeft != null) && (this._scrollRight != null))
/* 528 */       if (hsbNeeded) {
/* 529 */         this._scrollLeft.setVisible(true);
/* 530 */         this._scrollRight.setVisible(true);
/* 531 */         this._scrollLeft.setBounds(scrollLeftR);
/* 532 */         this._scrollRight.setBounds(scrollRightR);
/*     */       }
/*     */       else {
/* 535 */         this._scrollLeft.setVisible(false);
/* 536 */         this._scrollRight.setVisible(false);
/* 537 */         this._scrollLeft.setBounds(scrollLeftR.x, scrollLeftR.y, 0, 0);
/* 538 */         this._scrollRight.setBounds(scrollRightR.x, scrollRightR.y, 0, 0);
/*     */       }
/*     */   }
/*     */ 
/*     */   private void adjustForScrollUpAndDown(boolean wantsVSB, Rectangle available, Rectangle upR, Rectangle downR, Insets vpbInsets)
/*     */   {
/* 553 */     if (wantsVSB) {
/* 554 */       int buttonWidth = Math.max(0, Math.max(available.width + vpbInsets.left + vpbInsets.right, Math.max(this._scrollUp.getPreferredSize().width, this._scrollDown.getPreferredSize().width)));
/*     */ 
/* 556 */       available.height -= upR.height;
/* 557 */       available.height -= downR.height;
/*     */ 
/* 559 */       upR.width = buttonWidth;
/* 560 */       downR.width = buttonWidth;
/*     */ 
/* 562 */       available.x -= vpbInsets.left;
/* 563 */       available.x -= vpbInsets.left;
/*     */ 
/* 565 */       available.y -= vpbInsets.top;
/* 566 */       available.y += upR.height;
/* 567 */       downR.y = (available.y + available.height + vpbInsets.bottom);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void adjustForScrollLeftAndRight(boolean wantsHSB, Rectangle available, Rectangle leftR, Rectangle rightR, Insets vpbInsets)
/*     */   {
/* 582 */     if (wantsHSB) {
/* 583 */       int buttonHeight = Math.max(0, Math.max(available.height + vpbInsets.top + vpbInsets.bottom, Math.max(this._scrollLeft.getPreferredSize().height, this._scrollRight.getPreferredSize().height)));
/*     */ 
/* 585 */       available.width -= leftR.width;
/* 586 */       available.width -= rightR.width;
/*     */ 
/* 588 */       leftR.height = buttonHeight;
/* 589 */       rightR.height = buttonHeight;
/*     */ 
/* 591 */       available.y -= vpbInsets.top;
/* 592 */       available.y -= vpbInsets.top;
/*     */ 
/* 594 */       available.x -= vpbInsets.left;
/* 595 */       available.x += leftR.width;
/* 596 */       rightR.x = (available.x + available.width + vpbInsets.right);
/*     */     }
/*     */   }
/*     */ 
/*     */   static class UIResource extends SimpleScrollPaneLayout
/*     */     implements UIResource
/*     */   {
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.SimpleScrollPaneLayout
 * JD-Core Version:    0.6.0
 */
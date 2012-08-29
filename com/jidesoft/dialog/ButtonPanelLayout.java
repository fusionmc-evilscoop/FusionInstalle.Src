/*     */ package com.jidesoft.dialog;
/*     */ 
/*     */ import java.awt.AWTError;
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager2;
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.SizeRequirements;
/*     */ 
/*     */ class ButtonPanelLayout
/*     */   implements LayoutManager2, Serializable
/*     */ {
/*     */   public static final int X_AXIS = 0;
/*     */   public static final int Y_AXIS = 1;
/*     */   public static final int LINE_AXIS = 2;
/*     */   public static final int PAGE_AXIS = 3;
/*     */   private Container _target;
/*     */   private transient SizeRequirements[] _xChildren;
/*     */   private transient SizeRequirements[] _yChildren;
/*     */   private transient SizeRequirements _xTotal;
/*     */   private transient SizeRequirements _yTotal;
/*  45 */   private List<Component> _affirmativeButtons = new Vector(13);
/*  46 */   private List<Component> _cancelButtons = new Vector(13);
/*  47 */   private List<Component> _helpButtons = new Vector(13);
/*  48 */   private List<Component> _otherButtons = new Vector(13);
/*     */   int _minWidth;
/*     */   int _maxWidth;
/*  54 */   int _groupGap = 10;
/*     */ 
/*  56 */   int _buttonGap = 6;
/*     */ 
/*  58 */   private int _sizeConstraint = 1;
/*     */ 
/*  60 */   private String _buttonOrder = "ACO";
/*     */ 
/*  62 */   private String _oppositeButtonOrder = "H";
/*     */ 
/*  64 */   private int _minButtonWidth = 75;
/*     */ 
/*  66 */   private int _axis = 0;
/*     */ 
/*  68 */   private int _alignment = 11;
/*     */   private static final long serialVersionUID = -738156624351781041L;
/*     */ 
/*     */   public ButtonPanelLayout(Container target, int axis, int alignment, int sizeConstraint, String buttonOrder, String oppositeButtonOrder, int buttonGap, int groupGap)
/*     */   {
/*  89 */     if ((axis != 0) && (axis != 1) && (axis != 2) && (axis != 3))
/*     */     {
/*  93 */       throw new AWTError("Invalid axis");
/*     */     }
/*  95 */     this._axis = axis;
/*  96 */     this._target = target;
/*  97 */     this._alignment = alignment;
/*  98 */     this._sizeConstraint = sizeConstraint;
/*  99 */     this._buttonOrder = buttonOrder;
/* 100 */     this._oppositeButtonOrder = oppositeButtonOrder;
/* 101 */     this._buttonGap = buttonGap;
/* 102 */     this._groupGap = groupGap;
/*     */   }
/*     */ 
/*     */   public synchronized void invalidateLayout(Container target)
/*     */   {
/* 116 */     checkContainer(target);
/* 117 */     this._xChildren = null;
/* 118 */     this._yChildren = null;
/* 119 */     this._xTotal = null;
/* 120 */     this._yTotal = null;
/*     */   }
/*     */ 
/*     */   public void addLayoutComponent(String name, Component comp)
/*     */   {
/* 130 */     addLayoutComponent(comp, name);
/*     */   }
/*     */ 
/*     */   public void removeLayoutComponent(Component comp)
/*     */   {
/* 139 */     if (this._affirmativeButtons.contains(comp)) {
/* 140 */       this._affirmativeButtons.remove(comp);
/*     */     }
/* 142 */     if (this._cancelButtons.contains(comp)) {
/* 143 */       this._cancelButtons.remove(comp);
/*     */     }
/* 145 */     if (this._helpButtons.contains(comp)) {
/* 146 */       this._helpButtons.remove(comp);
/*     */     }
/* 148 */     if (this._otherButtons.contains(comp))
/* 149 */       this._otherButtons.remove(comp);
/*     */   }
/*     */ 
/*     */   public void addLayoutComponent(Component comp, Object constraints)
/*     */   {
/* 160 */     if (("AFFIRMATIVE".equals(constraints)) && 
/* 161 */       (!this._affirmativeButtons.contains(comp))) {
/* 162 */       this._affirmativeButtons.add(comp);
/*     */     }
/*     */ 
/* 166 */     if (("CANCEL".equals(constraints)) && 
/* 167 */       (!this._cancelButtons.contains(comp))) {
/* 168 */       this._cancelButtons.add(comp);
/*     */     }
/*     */ 
/* 172 */     if (("HELP".equals(constraints)) && 
/* 173 */       (!this._helpButtons.contains(comp))) {
/* 174 */       this._helpButtons.add(comp);
/*     */     }
/*     */ 
/* 178 */     if (("ALTERNATIVE".equals(constraints)) && 
/* 179 */       (!this._otherButtons.contains(comp)))
/* 180 */       this._otherButtons.add(comp);
/*     */   }
/*     */ 
/*     */   public Dimension preferredLayoutSize(Container target)
/*     */   {
/*     */     Dimension size;
/* 198 */     synchronized (this) {
/* 199 */       checkContainer(target);
/* 200 */       checkRequests();
/* 201 */       size = new Dimension(this._xTotal.preferred, this._yTotal.preferred);
/*     */     }
/*     */ 
/* 204 */     Insets insets = target.getInsets();
/* 205 */     size.width = (int)Math.min(size.width + insets.left + insets.right, 2147483647L);
/*     */ 
/* 209 */     size.height = (int)Math.min(size.height + insets.top + insets.bottom, 2147483647L);
/*     */ 
/* 213 */     return size;
/*     */   }
/*     */ 
/*     */   public Dimension minimumLayoutSize(Container target)
/*     */   {
/*     */     Dimension size;
/* 228 */     synchronized (this) {
/* 229 */       checkContainer(target);
/* 230 */       checkRequests();
/* 231 */       size = new Dimension(this._xTotal.minimum, this._yTotal.minimum);
/*     */     }
/*     */ 
/* 234 */     Insets insets = target.getInsets();
/* 235 */     size.width = (int)Math.min(size.width + insets.left + insets.right, 2147483647L);
/*     */ 
/* 239 */     size.height = (int)Math.min(size.height + insets.top + insets.bottom, 2147483647L);
/*     */ 
/* 243 */     Dimension preferredSize = preferredLayoutSize(target);
/* 244 */     if (size.width > preferredSize.width) {
/* 245 */       size.width = preferredSize.width;
/*     */     }
/* 247 */     if (size.height > preferredSize.height) {
/* 248 */       size.height = preferredSize.height;
/*     */     }
/* 250 */     return size;
/*     */   }
/*     */ 
/*     */   public Dimension maximumLayoutSize(Container target)
/*     */   {
/*     */     Dimension size;
/* 265 */     synchronized (this) {
/* 266 */       checkContainer(target);
/* 267 */       checkRequests();
/* 268 */       size = new Dimension(this._xTotal.maximum, this._yTotal.maximum);
/*     */     }
/*     */ 
/* 271 */     Insets insets = target.getInsets();
/* 272 */     size.width = (int)Math.min(size.width + insets.left + insets.right, 2147483647L);
/*     */ 
/* 276 */     size.height = (int)Math.min(size.height + insets.top + insets.bottom, 2147483647L);
/*     */ 
/* 280 */     Dimension preferredSize = preferredLayoutSize(target);
/* 281 */     if (size.width < preferredSize.width) {
/* 282 */       size.width = preferredSize.width;
/*     */     }
/* 284 */     if (size.height < preferredSize.height) {
/* 285 */       size.height = preferredSize.height;
/*     */     }
/* 287 */     return size;
/*     */   }
/*     */ 
/*     */   public synchronized float getLayoutAlignmentX(Container target)
/*     */   {
/* 300 */     checkContainer(target);
/* 301 */     checkRequests();
/* 302 */     return this._xTotal.alignment;
/*     */   }
/*     */ 
/*     */   public synchronized float getLayoutAlignmentY(Container target)
/*     */   {
/* 315 */     checkContainer(target);
/* 316 */     checkRequests();
/* 317 */     return this._yTotal.alignment;
/*     */   }
/*     */ 
/*     */   public void layoutContainer(Container target)
/*     */   {
/* 327 */     checkContainer(target);
/*     */ 
/* 329 */     Dimension alloc = target.getSize();
/* 330 */     Insets in = target.getInsets();
/* 331 */     alloc.width -= in.left + in.right;
/* 332 */     alloc.height -= in.top + in.bottom;
/*     */ 
/* 335 */     ComponentOrientation o = target.getComponentOrientation();
/* 336 */     int absoluteAxis = resolveAxis(this._axis, o);
/* 337 */     boolean ltr = o.isLeftToRight();
/* 338 */     int alignment = this._alignment;
/* 339 */     if (this._alignment == 10) {
/* 340 */       alignment = ltr ? 2 : 4;
/*     */     }
/* 342 */     else if (this._alignment == 11) {
/* 343 */       alignment = ltr ? 4 : 2;
/*     */     }
/*     */ 
/* 347 */     synchronized (this) {
/* 348 */       checkRequests();
/* 349 */       resetBounds();
/* 350 */       if (absoluteAxis == 0) {
/* 351 */         int y = in.top;
/* 352 */         if (alignment == 0) {
/* 353 */           Dimension size = preferredLayoutSize(target);
/*     */ 
/* 355 */           int x = (alloc.width + size.width) / 2;
/* 356 */           for (int i = 0; i < getButtonOrder().length(); i++) {
/* 357 */             char c = getButtonOrder().charAt(getButtonOrder().length() - i - 1);
/* 358 */             if ((c == 'A') || (c == 'a')) {
/* 359 */               x = layoutButtonsRightAlign(this._affirmativeButtons, x, y, alloc, ltr);
/*     */             }
/* 361 */             else if ((c == 'C') || (c == 'c')) {
/* 362 */               x = layoutButtonsRightAlign(this._cancelButtons, x, y, alloc, ltr);
/*     */             }
/* 364 */             else if ((c == 'H') || (c == 'h')) {
/* 365 */               x = layoutButtonsRightAlign(this._helpButtons, x, y, alloc, ltr);
/*     */             }
/* 367 */             else if ((c == 'O') || (c == 'o')) {
/* 368 */               x = layoutButtonsRightAlign(this._otherButtons, x, y, alloc, ltr);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 374 */           x = (alloc.width - size.width) / 2;
/* 375 */           for (int i = 0; i < getOppositeButtonOrder().length(); i++) {
/* 376 */             char c = getOppositeButtonOrder().charAt(i);
/* 377 */             if ((c == 'A') || (c == 'a')) {
/* 378 */               x = layoutButtonsLeftAlign(this._affirmativeButtons, x, y, alloc, ltr);
/*     */             }
/* 380 */             else if ((c == 'C') || (c == 'c')) {
/* 381 */               x = layoutButtonsLeftAlign(this._cancelButtons, x, y, alloc, ltr);
/*     */             }
/* 383 */             else if ((c == 'H') || (c == 'h')) {
/* 384 */               x = layoutButtonsLeftAlign(this._helpButtons, x, y, alloc, ltr);
/*     */             }
/* 386 */             else if ((c == 'O') || (c == 'o')) {
/* 387 */               x = layoutButtonsLeftAlign(this._otherButtons, x, y, alloc, ltr);
/*     */             }
/*     */           }
/*     */         }
/* 391 */         else if (alignment == 4)
/*     */         {
/* 393 */           int x = in.left + alloc.width;
/* 394 */           for (int i = 0; i < getButtonOrder().length(); i++) {
/* 395 */             char c = getButtonOrder().charAt(ltr ? getButtonOrder().length() - i - 1 : i);
/* 396 */             if ((c == 'A') || (c == 'a')) {
/* 397 */               x = layoutButtonsRightAlign(this._affirmativeButtons, x, y, alloc, ltr);
/*     */             }
/* 399 */             else if ((c == 'C') || (c == 'c')) {
/* 400 */               x = layoutButtonsRightAlign(this._cancelButtons, x, y, alloc, ltr);
/*     */             }
/* 402 */             else if ((c == 'H') || (c == 'h')) {
/* 403 */               x = layoutButtonsRightAlign(this._helpButtons, x, y, alloc, ltr);
/*     */             }
/* 405 */             else if ((c == 'O') || (c == 'o')) {
/* 406 */               x = layoutButtonsRightAlign(this._otherButtons, x, y, alloc, ltr);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 412 */           x = in.left;
/* 413 */           for (int i = 0; i < getOppositeButtonOrder().length(); i++) {
/* 414 */             char c = getOppositeButtonOrder().charAt(ltr ? i : getOppositeButtonOrder().length() - i - 1);
/* 415 */             if ((c == 'A') || (c == 'a')) {
/* 416 */               x = layoutButtonsLeftAlign(this._affirmativeButtons, x, y, alloc, ltr);
/*     */             }
/* 418 */             else if ((c == 'C') || (c == 'c')) {
/* 419 */               x = layoutButtonsLeftAlign(this._cancelButtons, x, y, alloc, ltr);
/*     */             }
/* 421 */             else if ((c == 'H') || (c == 'h')) {
/* 422 */               x = layoutButtonsLeftAlign(this._helpButtons, x, y, alloc, ltr);
/*     */             }
/* 424 */             else if ((c == 'O') || (c == 'o')) {
/* 425 */               x = layoutButtonsLeftAlign(this._otherButtons, x, y, alloc, ltr);
/*     */             }
/*     */           }
/*     */         }
/* 429 */         else if (alignment == 2)
/*     */         {
/* 431 */           int x = in.left;
/* 432 */           for (int i = 0; i < getButtonOrder().length(); i++) {
/* 433 */             char c = getButtonOrder().charAt(ltr ? i : getButtonOrder().length() - i - 1);
/* 434 */             if ((c == 'A') || (c == 'a')) {
/* 435 */               x = layoutButtonsLeftAlign(this._affirmativeButtons, x, y, alloc, ltr);
/*     */             }
/* 437 */             else if ((c == 'C') || (c == 'c')) {
/* 438 */               x = layoutButtonsLeftAlign(this._cancelButtons, x, y, alloc, ltr);
/*     */             }
/* 440 */             else if ((c == 'H') || (c == 'h')) {
/* 441 */               x = layoutButtonsLeftAlign(this._helpButtons, x, y, alloc, ltr);
/*     */             }
/* 443 */             else if ((c == 'O') || (c == 'o')) {
/* 444 */               x = layoutButtonsLeftAlign(this._otherButtons, x, y, alloc, ltr);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 449 */           x = in.left + alloc.width;
/* 450 */           for (int i = 0; i < getOppositeButtonOrder().length(); i++) {
/* 451 */             char c = getOppositeButtonOrder().charAt(ltr ? getOppositeButtonOrder().length() - i - 1 : i);
/* 452 */             if ((c == 'A') || (c == 'a')) {
/* 453 */               x = layoutButtonsRightAlign(this._affirmativeButtons, x, y, alloc, ltr);
/*     */             }
/* 455 */             else if ((c == 'C') || (c == 'c')) {
/* 456 */               x = layoutButtonsRightAlign(this._cancelButtons, x, y, alloc, ltr);
/*     */             }
/* 458 */             else if ((c == 'H') || (c == 'h')) {
/* 459 */               x = layoutButtonsRightAlign(this._helpButtons, x, y, alloc, ltr);
/*     */             }
/* 461 */             else if ((c == 'O') || (c == 'o'))
/* 462 */               x = layoutButtonsRightAlign(this._otherButtons, x, y, alloc, ltr);
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 468 */         int x = in.left;
/* 469 */         if (alignment == 1)
/*     */         {
/* 471 */           int y = in.top;
/* 472 */           for (int i = 0; i < getButtonOrder().length(); i++) {
/* 473 */             char c = getButtonOrder().charAt(i);
/* 474 */             if ((c == 'A') || (c == 'a')) {
/* 475 */               y = layoutButtonsTopAlign(this._affirmativeButtons, x, y, alloc);
/*     */             }
/* 477 */             else if ((c == 'C') || (c == 'c')) {
/* 478 */               y = layoutButtonsTopAlign(this._cancelButtons, x, y, alloc);
/*     */             }
/* 480 */             else if ((c == 'H') || (c == 'h')) {
/* 481 */               y = layoutButtonsTopAlign(this._helpButtons, x, y, alloc);
/*     */             }
/* 483 */             else if ((c == 'O') || (c == 'o')) {
/* 484 */               y = layoutButtonsTopAlign(this._otherButtons, x, y, alloc);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 489 */           y = in.top + alloc.height;
/* 490 */           for (int i = 0; i < getOppositeButtonOrder().length(); i++) {
/* 491 */             char c = getOppositeButtonOrder().charAt(getOppositeButtonOrder().length() - i - 1);
/* 492 */             if ((c == 'A') || (c == 'a')) {
/* 493 */               y = layoutButtonsBottomAlign(this._affirmativeButtons, x, y, alloc);
/*     */             }
/* 495 */             else if ((c == 'C') || (c == 'c')) {
/* 496 */               y = layoutButtonsBottomAlign(this._cancelButtons, x, y, alloc);
/*     */             }
/* 498 */             else if ((c == 'H') || (c == 'h')) {
/* 499 */               y = layoutButtonsBottomAlign(this._helpButtons, x, y, alloc);
/*     */             }
/* 501 */             else if ((c == 'O') || (c == 'o')) {
/* 502 */               y = layoutButtonsBottomAlign(this._otherButtons, x, y, alloc);
/*     */             }
/*     */           }
/*     */         }
/* 506 */         else if (alignment == 3)
/*     */         {
/* 508 */           int y = in.top + alloc.height;
/* 509 */           for (int i = 0; i < getButtonOrder().length(); i++) {
/* 510 */             char c = getButtonOrder().charAt(getButtonOrder().length() - i - 1);
/* 511 */             if ((c == 'A') || (c == 'a')) {
/* 512 */               y = layoutButtonsBottomAlign(this._affirmativeButtons, x, y, alloc);
/*     */             }
/* 514 */             else if ((c == 'C') || (c == 'c')) {
/* 515 */               y = layoutButtonsBottomAlign(this._cancelButtons, x, y, alloc);
/*     */             }
/* 517 */             else if ((c == 'H') || (c == 'h')) {
/* 518 */               y = layoutButtonsBottomAlign(this._helpButtons, x, y, alloc);
/*     */             }
/* 520 */             else if ((c == 'O') || (c == 'o')) {
/* 521 */               y = layoutButtonsBottomAlign(this._otherButtons, x, y, alloc);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 526 */           y = in.top;
/* 527 */           for (int i = 0; i < getOppositeButtonOrder().length(); i++) {
/* 528 */             char c = getOppositeButtonOrder().charAt(i);
/* 529 */             if ((c == 'A') || (c == 'a')) {
/* 530 */               y = layoutButtonsTopAlign(this._affirmativeButtons, x, y, alloc);
/*     */             }
/* 532 */             else if ((c == 'C') || (c == 'c')) {
/* 533 */               y = layoutButtonsTopAlign(this._cancelButtons, x, y, alloc);
/*     */             }
/* 535 */             else if ((c == 'H') || (c == 'h')) {
/* 536 */               y = layoutButtonsTopAlign(this._helpButtons, x, y, alloc);
/*     */             }
/* 538 */             else if ((c == 'O') || (c == 'o'))
/* 539 */               y = layoutButtonsTopAlign(this._otherButtons, x, y, alloc);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private int layoutButtonsRightAlign(List buttons, int x, int y, Dimension alloc, boolean ltr)
/*     */   {
/* 548 */     boolean containsVisibleButton = false;
/* 549 */     for (int i = this._target.getComponentCount() - 1; i >= 0; i--)
/*     */     {
/*     */       Component component;
/*     */       Component component;
/* 551 */       if (ltr) {
/* 552 */         component = this._target.getComponent(i);
/*     */       }
/*     */       else {
/* 555 */         component = this._target.getComponent(this._target.getComponentCount() - 1 - i);
/*     */       }
/* 557 */       if ((!component.isVisible()) || (!buttons.contains(component)))
/*     */       {
/*     */         continue;
/*     */       }
/*     */ 
/* 562 */       if (containsVisibleButton) {
/* 563 */         x -= this._buttonGap;
/*     */       }
/*     */ 
/* 566 */       containsVisibleButton = true;
/* 567 */       int prefWidth = component.getPreferredSize().width;
/* 568 */       int width = (prefWidth > this._minWidth) || (shouldKeepPreferredWidth(component)) ? prefWidth : this._minWidth;
/* 569 */       component.setBounds(x - width, y, width, alloc.height);
/* 570 */       x -= width;
/*     */     }
/*     */ 
/* 576 */     if ((buttons.size() != 0) && (containsVisibleButton)) {
/* 577 */       x -= this._groupGap;
/*     */     }
/* 579 */     return x;
/*     */   }
/*     */ 
/*     */   private int layoutButtonsLeftAlign(List<Component> buttons, int x, int y, Dimension alloc, boolean ltr) {
/* 583 */     boolean containsVisibleButton = false;
/* 584 */     for (int i = 0; i < this._target.getComponentCount(); i++)
/*     */     {
/*     */       Component component;
/*     */       Component component;
/* 586 */       if (ltr) {
/* 587 */         component = this._target.getComponent(i);
/*     */       }
/*     */       else {
/* 590 */         component = this._target.getComponent(this._target.getComponentCount() - 1 - i);
/*     */       }
/* 592 */       if ((!component.isVisible()) || (!buttons.contains(component))) {
/*     */         continue;
/*     */       }
/* 595 */       containsVisibleButton = true;
/* 596 */       int prefWidth = component.getPreferredSize().width;
/* 597 */       int width = (prefWidth > this._minWidth) || (shouldKeepPreferredWidth(component)) ? prefWidth : this._minWidth;
/* 598 */       component.setBounds(x, y, width, alloc.height);
/* 599 */       x += width + this._buttonGap;
/*     */     }
/* 601 */     if (containsVisibleButton) {
/* 602 */       x -= this._buttonGap;
/* 603 */       x += this._groupGap;
/*     */     }
/* 605 */     return x;
/*     */   }
/*     */ 
/*     */   private int layoutButtonsBottomAlign(List<Component> buttons, int x, int y, Dimension alloc) {
/* 609 */     boolean containsVisibleButton = false;
/* 610 */     for (int i = this._target.getComponentCount() - 1; i >= 0; i--) {
/* 611 */       Component component = this._target.getComponent(i);
/* 612 */       if ((!component.isVisible()) || (!buttons.contains(component))) {
/*     */         continue;
/*     */       }
/* 615 */       if (containsVisibleButton) {
/* 616 */         y -= this._buttonGap;
/*     */       }
/* 618 */       containsVisibleButton = true;
/* 619 */       Dimension preferredSize = component.getPreferredSize();
/* 620 */       int height = preferredSize.height;
/* 621 */       int prefWidth = preferredSize.width;
/* 622 */       component.setBounds(shouldKeepPreferredWidth(component) ? alloc.width - prefWidth + x : x, y - height, shouldKeepPreferredWidth(component) ? prefWidth : alloc.width, height);
/* 623 */       y -= height;
/*     */     }
/* 625 */     if ((buttons.size() != 0) && (containsVisibleButton)) {
/* 626 */       y -= this._groupGap;
/*     */     }
/* 628 */     return y;
/*     */   }
/*     */ 
/*     */   private int layoutButtonsTopAlign(List<Component> buttons, int x, int y, Dimension alloc) {
/* 632 */     boolean containsVisibleButton = false;
/* 633 */     for (int i = 0; i < this._target.getComponentCount(); i++) {
/* 634 */       Component component = this._target.getComponent(i);
/* 635 */       if ((!component.isVisible()) || (!buttons.contains(component))) {
/*     */         continue;
/*     */       }
/* 638 */       containsVisibleButton = true;
/* 639 */       Dimension preferredSize = component.getPreferredSize();
/* 640 */       int height = preferredSize.height;
/* 641 */       int prefWidth = preferredSize.width;
/* 642 */       component.setBounds(shouldKeepPreferredWidth(component) ? alloc.width - prefWidth + x : x, y, shouldKeepPreferredWidth(component) ? prefWidth : alloc.width, height);
/* 643 */       y += height + this._buttonGap;
/*     */     }
/* 645 */     if (containsVisibleButton) {
/* 646 */       y -= this._buttonGap;
/* 647 */       y += this._groupGap;
/*     */     }
/* 649 */     return y;
/*     */   }
/*     */ 
/*     */   private boolean shouldKeepPreferredWidth(Component component) {
/* 653 */     return ((component instanceof JComponent)) && (Boolean.TRUE.equals(((JComponent)component).getClientProperty("keepPreferredWidth")));
/*     */   }
/*     */ 
/*     */   void checkContainer(Container target) {
/* 657 */     if (this._target != target) {
/* 658 */       throw new AWTError("BorderPaneLayout can't be shared");
/*     */     }
/* 660 */     if (!(target instanceof ButtonPanel))
/* 661 */       throw new AWTError("Target is not a ButtonPanel");
/*     */   }
/*     */ 
/*     */   int getButtonCountof(List<Component> buttons)
/*     */   {
/* 666 */     int count = 0;
/* 667 */     for (Component button : buttons) {
/* 668 */       if (button.isVisible()) {
/* 669 */         count++;
/*     */       }
/*     */     }
/* 672 */     return count;
/*     */   }
/*     */ 
/*     */   void checkRequests() {
/* 676 */     int totalGroup = (getButtonCountof(this._affirmativeButtons) == 0 ? 0 : 1) + (getButtonCountof(this._otherButtons) == 0 ? 0 : 1) + (getButtonCountof(this._cancelButtons) == 0 ? 0 : 1) + (getButtonCountof(this._helpButtons) == 0 ? 0 : 1);
/*     */ 
/* 681 */     int totalButtonCount = getButtonCountof(this._affirmativeButtons) + getButtonCountof(this._otherButtons) + getButtonCountof(this._cancelButtons) + getButtonCountof(this._helpButtons);
/*     */ 
/* 687 */     if ((this._xChildren == null) || (this._yChildren == null))
/*     */     {
/* 690 */       int componentCount = this._target.getComponentCount();
/* 691 */       int visibleComponentCount = componentCount;
/* 692 */       for (int i = 0; i < componentCount; i++) {
/* 693 */         if (!this._target.getComponent(i).isVisible()) {
/* 694 */           visibleComponentCount--;
/*     */         }
/*     */       }
/*     */ 
/* 698 */       this._xChildren = new SizeRequirements[visibleComponentCount];
/* 699 */       this._yChildren = new SizeRequirements[visibleComponentCount];
/* 700 */       int index = 0;
/* 701 */       for (int i = 0; i < componentCount; i++) {
/* 702 */         Component c = this._target.getComponent(i);
/* 703 */         if (!c.isVisible()) {
/*     */           continue;
/*     */         }
/* 706 */         Dimension min = c.getMinimumSize();
/* 707 */         Dimension typ = c.getPreferredSize();
/* 708 */         Dimension max = c.getMaximumSize();
/* 709 */         this._xChildren[index] = new SizeRequirements(min.width, typ.width, max.width, c.getAlignmentX());
/*     */ 
/* 715 */         this._yChildren[index] = new SizeRequirements(min.height, typ.height, max.height, c.getAlignmentY());
/*     */ 
/* 721 */         if (shouldKeepPreferredWidth(this._target.getComponent(i))) {
/* 722 */           this._xChildren[index].maximum = 0;
/*     */         }
/* 724 */         index++;
/*     */       }
/*     */ 
/* 728 */       int absoluteAxis = resolveAxis(this._axis, this._target.getComponentOrientation());
/*     */ 
/* 731 */       if (absoluteAxis == 0) {
/* 732 */         this._xTotal = SizeRequirements.getTiledSizeRequirements(this._xChildren);
/* 733 */         this._yTotal = SizeRequirements.getAlignedSizeRequirements(this._yChildren);
/*     */ 
/* 735 */         this._maxWidth = SizeRequirements.getAlignedSizeRequirements(this._xChildren).maximum;
/*     */ 
/* 737 */         if (this._sizeConstraint == 0) {
/* 738 */           int width = getMinButtonWidth();
/* 739 */           if (this._maxWidth < width) {
/* 740 */             this._maxWidth = width;
/*     */           }
/* 742 */           this._minWidth = this._maxWidth;
/*     */         }
/*     */         else {
/* 745 */           int width = getMinButtonWidth();
/* 746 */           if (width == 0) {
/* 747 */             this._minWidth = 75;
/*     */           }
/*     */           else {
/* 750 */             this._minWidth = width;
/*     */           }
/*     */         }
/*     */ 
/* 754 */         for (SizeRequirements sizeRequirements : this._xChildren) {
/* 755 */           if (sizeRequirements.preferred < this._minWidth) {
/* 756 */             sizeRequirements.preferred = this._minWidth;
/*     */           }
/*     */         }
/* 759 */         this._xTotal = SizeRequirements.getTiledSizeRequirements(this._xChildren);
/*     */ 
/* 762 */         this._xTotal.preferred += (totalGroup - 1) * this._groupGap + (totalButtonCount - totalGroup) * this._buttonGap;
/*     */ 
/* 764 */         this._xTotal.minimum += (totalGroup - 1) * this._groupGap + (totalButtonCount - totalGroup) * this._buttonGap;
/*     */       }
/*     */       else
/*     */       {
/* 769 */         this._xTotal = SizeRequirements.getAlignedSizeRequirements(this._xChildren);
/* 770 */         this._yTotal = SizeRequirements.getTiledSizeRequirements(this._yChildren);
/*     */ 
/* 772 */         int width = getMinButtonWidth();
/* 773 */         if (width == 0) {
/* 774 */           this._maxWidth = 75;
/*     */         }
/*     */         else {
/* 777 */           this._maxWidth = width;
/*     */         }
/* 779 */         this._minWidth = this._maxWidth;
/* 780 */         this._xTotal.preferred = (this._maxWidth > this._xTotal.maximum ? this._maxWidth : this._xTotal.preferred);
/*     */ 
/* 783 */         this._yTotal.preferred += (totalGroup - 1) * this._groupGap + (totalButtonCount - totalGroup) * this._buttonGap;
/*     */ 
/* 785 */         this._yTotal.minimum += (totalGroup - 1) * this._groupGap + (totalButtonCount - totalGroup) * this._buttonGap;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private int resolveAxis(int axis, ComponentOrientation o)
/*     */   {
/*     */     int absoluteAxis;
/*     */     int absoluteAxis;
/* 802 */     if (axis == 2) {
/* 803 */       absoluteAxis = o.isHorizontal() ? 0 : 1;
/*     */     }
/*     */     else
/*     */     {
/*     */       int absoluteAxis;
/* 805 */       if (axis == 3) {
/* 806 */         absoluteAxis = o.isHorizontal() ? 1 : 0;
/*     */       }
/*     */       else
/* 809 */         absoluteAxis = axis;
/*     */     }
/* 811 */     return absoluteAxis;
/*     */   }
/*     */ 
/*     */   public int getGroupGap() {
/* 815 */     return this._groupGap;
/*     */   }
/*     */ 
/*     */   public void setGroupGap(int groupGap) {
/* 819 */     this._groupGap = groupGap;
/* 820 */     invalidateLayout(this._target);
/*     */   }
/*     */ 
/*     */   public int getButtonGap() {
/* 824 */     return this._buttonGap;
/*     */   }
/*     */ 
/*     */   public void setButtonGap(int buttonGap) {
/* 828 */     this._buttonGap = buttonGap;
/* 829 */     invalidateLayout(this._target);
/*     */   }
/*     */ 
/*     */   public int getSizeConstraint() {
/* 833 */     return this._sizeConstraint;
/*     */   }
/*     */ 
/*     */   public void setSizeConstraint(int sizeConstraint) {
/* 837 */     this._sizeConstraint = sizeConstraint;
/* 838 */     invalidateLayout(this._target);
/*     */   }
/*     */ 
/*     */   public int getMinButtonWidth() {
/* 842 */     return this._minButtonWidth;
/*     */   }
/*     */ 
/*     */   public void setMinButtonWidth(int minButtonWidth) {
/* 846 */     this._minButtonWidth = minButtonWidth;
/* 847 */     invalidateLayout(this._target);
/*     */   }
/*     */ 
/*     */   public String getButtonOrder() {
/* 851 */     if (this._buttonOrder == null) {
/* 852 */       return "";
/*     */     }
/*     */ 
/* 855 */     return this._buttonOrder;
/*     */   }
/*     */ 
/*     */   public void setButtonOrder(String buttonOrder)
/*     */   {
/* 860 */     this._buttonOrder = buttonOrder;
/* 861 */     invalidateLayout(this._target);
/*     */   }
/*     */ 
/*     */   public String getOppositeButtonOrder() {
/* 865 */     if (this._oppositeButtonOrder == null) {
/* 866 */       return "";
/*     */     }
/*     */ 
/* 869 */     return this._oppositeButtonOrder;
/*     */   }
/*     */ 
/*     */   public void setOppositeButtonOrder(String oppositeButtonOrder)
/*     */   {
/* 874 */     this._oppositeButtonOrder = oppositeButtonOrder;
/* 875 */     invalidateLayout(this._target);
/*     */   }
/*     */ 
/*     */   public int getAxis() {
/* 879 */     return this._axis;
/*     */   }
/*     */ 
/*     */   public void setAxis(int axis) {
/* 883 */     this._axis = axis;
/* 884 */     invalidateLayout(this._target);
/*     */   }
/*     */ 
/*     */   public int getAlignment() {
/* 888 */     return this._alignment;
/*     */   }
/*     */ 
/*     */   public void setAlignment(int alignment) {
/* 892 */     this._alignment = alignment;
/* 893 */     invalidateLayout(this._target);
/*     */   }
/*     */ 
/*     */   void resetBounds() {
/* 897 */     for (Component component : this._affirmativeButtons) {
/* 898 */       component.setBounds(0, 0, 0, 0);
/*     */     }
/* 900 */     for (Component component : this._cancelButtons) {
/* 901 */       component.setBounds(0, 0, 0, 0);
/*     */     }
/* 903 */     for (Component component : this._otherButtons) {
/* 904 */       component.setBounds(0, 0, 0, 0);
/*     */     }
/* 906 */     for (Component component : this._helpButtons)
/* 907 */       component.setBounds(0, 0, 0, 0);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.dialog.ButtonPanelLayout
 * JD-Core Version:    0.6.0
 */
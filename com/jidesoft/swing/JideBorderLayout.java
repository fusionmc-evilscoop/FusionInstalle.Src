/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager2;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class JideBorderLayout
/*     */   implements LayoutManager2, Serializable
/*     */ {
/*     */   int hgap;
/*     */   int vgap;
/*     */   Component north;
/*     */   Component west;
/*     */   Component east;
/*     */   Component south;
/*     */   Component center;
/*     */   Component firstLine;
/*     */   Component lastLine;
/*     */   Component firstItem;
/*     */   Component lastItem;
/*     */   public static final String NORTH = "North";
/*     */   public static final String SOUTH = "South";
/*     */   public static final String EAST = "East";
/*     */   public static final String WEST = "West";
/*     */   public static final String CENTER = "Center";
/*     */   public static final String BEFORE_FIRST_LINE = "First";
/*     */   public static final String AFTER_LAST_LINE = "Last";
/*     */   public static final String BEFORE_LINE_BEGINS = "Before";
/*     */   public static final String AFTER_LINE_ENDS = "After";
/*     */   public static final String PAGE_START = "First";
/*     */   public static final String PAGE_END = "Last";
/*     */   public static final String LINE_START = "Before";
/*     */   public static final String LINE_END = "After";
/*     */   private static final long serialVersionUID = -8658291919501921765L;
/*     */ 
/*     */   public JideBorderLayout()
/*     */   {
/* 211 */     this(0, 0);
/*     */   }
/*     */ 
/*     */   public JideBorderLayout(int hgap, int vgap)
/*     */   {
/* 222 */     this.hgap = hgap;
/* 223 */     this.vgap = vgap;
/*     */   }
/*     */ 
/*     */   public int getHgap()
/*     */   {
/* 232 */     return this.hgap;
/*     */   }
/*     */ 
/*     */   public void setHgap(int hgap)
/*     */   {
/* 242 */     this.hgap = hgap;
/*     */   }
/*     */ 
/*     */   public int getVgap()
/*     */   {
/* 251 */     return this.vgap;
/*     */   }
/*     */ 
/*     */   public void setVgap(int vgap)
/*     */   {
/* 261 */     this.vgap = vgap;
/*     */   }
/*     */ 
/*     */   public void addLayoutComponent(Component comp, Object constraints)
/*     */   {
/* 280 */     synchronized (comp.getTreeLock()) {
/* 281 */       if ((constraints == null) || ((constraints instanceof String))) {
/* 282 */         addLayoutComponent((String)constraints, comp);
/*     */       }
/*     */       else
/* 285 */         throw new IllegalArgumentException("cannot add to layout: constraint must be a string (or null)");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addLayoutComponent(String name, Component comp)
/*     */   {
/* 291 */     synchronized (comp.getTreeLock())
/*     */     {
/* 293 */       if (name == null) {
/* 294 */         name = "Center";
/*     */       }
/*     */ 
/* 299 */       if ("Center".equals(name)) {
/* 300 */         this.center = comp;
/*     */       }
/* 302 */       else if ("North".equals(name)) {
/* 303 */         this.north = comp;
/*     */       }
/* 305 */       else if ("South".equals(name)) {
/* 306 */         this.south = comp;
/*     */       }
/* 308 */       else if ("East".equals(name)) {
/* 309 */         this.east = comp;
/*     */       }
/* 311 */       else if ("West".equals(name)) {
/* 312 */         this.west = comp;
/*     */       }
/* 314 */       else if ("First".equals(name)) {
/* 315 */         this.firstLine = comp;
/*     */       }
/* 317 */       else if ("Last".equals(name)) {
/* 318 */         this.lastLine = comp;
/*     */       }
/* 320 */       else if ("Before".equals(name)) {
/* 321 */         this.firstItem = comp;
/*     */       }
/* 323 */       else if ("After".equals(name)) {
/* 324 */         this.lastItem = comp;
/*     */       }
/*     */       else
/* 327 */         throw new IllegalArgumentException("cannot add to layout: unknown constraint: " + name);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removeLayoutComponent(Component comp)
/*     */   {
/* 341 */     synchronized (comp.getTreeLock()) {
/* 342 */       if (comp == this.center) {
/* 343 */         this.center = null;
/*     */       }
/* 345 */       else if (comp == this.north) {
/* 346 */         this.north = null;
/*     */       }
/* 348 */       else if (comp == this.south) {
/* 349 */         this.south = null;
/*     */       }
/* 351 */       else if (comp == this.east) {
/* 352 */         this.east = null;
/*     */       }
/* 354 */       else if (comp == this.west) {
/* 355 */         this.west = null;
/*     */       }
/* 357 */       if (comp == this.firstLine) {
/* 358 */         this.firstLine = null;
/*     */       }
/* 360 */       else if (comp == this.lastLine) {
/* 361 */         this.lastLine = null;
/*     */       }
/* 363 */       else if (comp == this.firstItem) {
/* 364 */         this.firstItem = null;
/*     */       }
/* 366 */       else if (comp == this.lastItem)
/* 367 */         this.lastItem = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Dimension minimumLayoutSize(Container target)
/*     */   {
/* 385 */     synchronized (target.getTreeLock()) {
/* 386 */       Dimension dim = new Dimension(0, 0);
/*     */ 
/* 388 */       boolean ltr = target.getComponentOrientation().isLeftToRight();
/*     */       Component c;
/* 391 */       if ((c = getChild("Center", ltr)) != null) {
/* 392 */         Dimension d = c.getMinimumSize();
/* 393 */         dim.width += d.width;
/* 394 */         dim.height = Math.max(d.height, dim.height);
/*     */       }
/* 396 */       if ((c = getChild("North", ltr)) != null) {
/* 397 */         Dimension d = c.getMinimumSize();
/* 398 */         dim.width = Math.max(d.width, dim.width);
/* 399 */         dim.height += d.height + this.vgap;
/*     */       }
/* 401 */       if ((c = getChild("South", ltr)) != null) {
/* 402 */         Dimension d = c.getMinimumSize();
/* 403 */         dim.width = Math.max(d.width, dim.width);
/* 404 */         dim.height += d.height + this.vgap;
/*     */       }
/* 406 */       if ((c = getChild("East", ltr)) != null) {
/* 407 */         Dimension d = c.getMinimumSize();
/* 408 */         dim.width += d.width + this.hgap;
/* 409 */         dim.height = Math.max(d.height, dim.height);
/*     */       }
/* 411 */       if ((c = getChild("West", ltr)) != null) {
/* 412 */         Dimension d = c.getMinimumSize();
/* 413 */         dim.width += d.width + this.hgap;
/* 414 */         dim.height = Math.max(d.height, dim.height);
/*     */       }
/*     */ 
/* 417 */       Insets insets = target.getInsets();
/* 418 */       dim.width += insets.left + insets.right;
/* 419 */       dim.height += insets.top + insets.bottom;
/*     */ 
/* 421 */       return dim;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Dimension preferredLayoutSize(Container target)
/*     */   {
/* 439 */     synchronized (target.getTreeLock()) {
/* 440 */       Dimension dim = new Dimension(0, 0);
/*     */ 
/* 442 */       boolean ltr = target.getComponentOrientation().isLeftToRight();
/*     */       Component c;
/* 445 */       if ((c = getChild("Center", ltr)) != null) {
/* 446 */         Dimension d = c.getPreferredSize();
/* 447 */         dim.width += d.width;
/* 448 */         dim.height = Math.max(d.height, dim.height);
/*     */       }
/* 450 */       if ((c = getChild("North", ltr)) != null) {
/* 451 */         Dimension d = c.getPreferredSize();
/* 452 */         dim.width = Math.max(d.width, dim.width);
/* 453 */         dim.height += d.height + this.vgap;
/*     */       }
/* 455 */       if ((c = getChild("South", ltr)) != null) {
/* 456 */         Dimension d = c.getPreferredSize();
/* 457 */         dim.width = Math.max(d.width, dim.width);
/* 458 */         dim.height += d.height + this.vgap;
/*     */       }
/* 460 */       if ((c = getChild("East", ltr)) != null) {
/* 461 */         Dimension d = c.getPreferredSize();
/* 462 */         dim.width += d.width + this.hgap;
/* 463 */         dim.height = Math.max(d.height, dim.height);
/*     */       }
/* 465 */       if ((c = getChild("West", ltr)) != null) {
/* 466 */         Dimension d = c.getPreferredSize();
/* 467 */         dim.width += d.width + this.hgap;
/* 468 */         dim.height = Math.max(d.height, dim.height);
/*     */       }
/*     */ 
/* 471 */       Insets insets = target.getInsets();
/* 472 */       dim.width += insets.left + insets.right;
/* 473 */       dim.height += insets.top + insets.bottom;
/*     */ 
/* 475 */       return dim;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Dimension maximumLayoutSize(Container target)
/*     */   {
/* 488 */     return new Dimension(2147483647, 2147483647);
/*     */   }
/*     */ 
/*     */   public float getLayoutAlignmentX(Container parent)
/*     */   {
/* 497 */     return 0.5F;
/*     */   }
/*     */ 
/*     */   public float getLayoutAlignmentY(Container parent)
/*     */   {
/* 506 */     return 0.5F;
/*     */   }
/*     */ 
/*     */   public void invalidateLayout(Container target)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void layoutContainer(Container target)
/*     */   {
/* 532 */     synchronized (target.getTreeLock()) {
/* 533 */       Insets insets = target.getInsets();
/* 534 */       int top = insets.top;
/* 535 */       int bottom = target.getHeight() - insets.bottom;
/* 536 */       int left = insets.left;
/* 537 */       int right = target.getWidth() - insets.right;
/*     */ 
/* 539 */       boolean ltr = target.getComponentOrientation().isLeftToRight();
/*     */ 
/* 541 */       Component north = getChild("North", ltr);
/* 542 */       Component south = getChild("South", ltr);
/* 543 */       Component east = getChild("East", ltr);
/* 544 */       Component west = getChild("West", ltr);
/* 545 */       Component center = getChild("Center", ltr);
/*     */ 
/* 551 */       int westGap = west != null ? west.getPreferredSize().width + this.hgap : 0;
/* 552 */       int eastGap = east != null ? east.getPreferredSize().width + this.hgap : 0;
/*     */ 
/* 554 */       if (north != null)
/*     */       {
/* 556 */         Dimension d = north.getPreferredSize();
/* 557 */         north.setBounds(left + westGap, top, right - left - westGap - eastGap, d.height);
/*     */ 
/* 561 */         top += d.height + this.vgap;
/*     */       }
/* 563 */       if (south != null)
/*     */       {
/* 565 */         Dimension d = south.getPreferredSize();
/* 566 */         south.setBounds(left + westGap, bottom - d.height, right - left - westGap - eastGap, d.height);
/*     */ 
/* 570 */         bottom -= d.height + this.vgap;
/*     */       }
/* 572 */       if (east != null) {
/* 573 */         east.setSize(east.getWidth(), bottom - top);
/* 574 */         Dimension d = east.getPreferredSize();
/* 575 */         east.setBounds(right - d.width, top, d.width, bottom - top);
/* 576 */         right -= d.width + this.hgap;
/*     */       }
/* 578 */       if (west != null) {
/* 579 */         west.setSize(west.getWidth(), bottom - top);
/* 580 */         Dimension d = west.getPreferredSize();
/* 581 */         west.setBounds(left, top, d.width, bottom - top);
/* 582 */         left += d.width + this.hgap;
/*     */       }
/* 584 */       if (center != null)
/* 585 */         center.setBounds(left, top, right - left, bottom - top);
/*     */     }
/*     */   }
/*     */ 
/*     */   private Component getChild(String key, boolean ltr)
/*     */   {
/* 598 */     Component result = null;
/*     */ 
/* 600 */     if (key.equals("North")) {
/* 601 */       result = this.firstLine != null ? this.firstLine : this.north;
/*     */     }
/* 603 */     else if (key.equals("South")) {
/* 604 */       result = this.lastLine != null ? this.lastLine : this.south;
/*     */     }
/* 606 */     else if (key.equals("West")) {
/* 607 */       result = ltr ? this.firstItem : this.lastItem;
/* 608 */       if (result == null) {
/* 609 */         result = this.west;
/*     */       }
/*     */     }
/* 612 */     else if (key.equals("East")) {
/* 613 */       result = ltr ? this.lastItem : this.firstItem;
/* 614 */       if (result == null) {
/* 615 */         result = this.east;
/*     */       }
/*     */     }
/* 618 */     else if (key.equals("Center")) {
/* 619 */       result = this.center;
/*     */     }
/* 621 */     if ((result != null) && (!result.isVisible())) {
/* 622 */       result = null;
/*     */     }
/* 624 */     return result;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 634 */     return getClass().getName() + "[hgap=" + this.hgap + ",vgap=" + this.vgap + "]";
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.JideBorderLayout
 * JD-Core Version:    0.6.0
 */
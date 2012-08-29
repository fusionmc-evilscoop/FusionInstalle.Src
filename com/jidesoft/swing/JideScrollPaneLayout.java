/*      */ package com.jidesoft.swing;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.awt.ComponentOrientation;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Rectangle;
/*      */ import javax.swing.JScrollBar;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JViewport;
/*      */ import javax.swing.ScrollPaneLayout;
/*      */ import javax.swing.Scrollable;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.plaf.UIResource;
/*      */ 
/*      */ public class JideScrollPaneLayout extends ScrollPaneLayout
/*      */   implements JideScrollPaneConstants
/*      */ {
/*      */   protected JViewport _rowFoot;
/*      */   protected JViewport _subColHead;
/*      */   protected JViewport _colFoot;
/*      */   protected Component _hLeft;
/*      */   protected Component _hRight;
/*      */   protected Component _vTop;
/*      */   protected Component _vBottom;
/*      */   private static final long serialVersionUID = 7897026041296359186L;
/*      */ 
/*      */   public void syncWithScrollPane(JScrollPane sp)
/*      */   {
/*   61 */     super.syncWithScrollPane(sp);
/*   62 */     if ((sp instanceof JideScrollPane)) {
/*   63 */       this._rowFoot = ((JideScrollPane)sp).getRowFooter();
/*   64 */       this._colFoot = ((JideScrollPane)sp).getColumnFooter();
/*   65 */       this._subColHead = ((JideScrollPane)sp).getSubColumnHeader();
/*   66 */       this._hLeft = ((JideScrollPane)sp).getScrollBarCorner("HORIZONTAL_LEFT");
/*   67 */       this._hRight = ((JideScrollPane)sp).getScrollBarCorner("HORIZONTAL_RIGHT");
/*   68 */       this._vTop = ((JideScrollPane)sp).getScrollBarCorner("VERTICAL_TOP");
/*   69 */       this._vBottom = ((JideScrollPane)sp).getScrollBarCorner("VERTICAL_BOTTOM");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected boolean isHsbCoversWholeWidth(JScrollPane sp) {
/*   74 */     return ((sp instanceof JideScrollPane)) && (((JideScrollPane)sp).isHorizontalScrollBarCoversWholeWidth());
/*      */   }
/*      */ 
/*      */   protected boolean isVsbCoversWholeHeight(JScrollPane sp) {
/*   78 */     return ((sp instanceof JideScrollPane)) && (((JideScrollPane)sp).isVerticalScrollBarCoversWholeHeight());
/*      */   }
/*      */ 
/*      */   protected boolean isColumnHeadersHeightUnified(JScrollPane sp) {
/*   82 */     return ((sp instanceof JideScrollPane)) && (((JideScrollPane)sp).isColumnHeadersHeightUnified());
/*      */   }
/*      */ 
/*      */   protected boolean isColumnFootersHeightUnified(JScrollPane sp) {
/*   86 */     return ((sp instanceof JideScrollPane)) && (((JideScrollPane)sp).isColumnFootersHeightUnified());
/*      */   }
/*      */ 
/*      */   public void addLayoutComponent(String s, Component c)
/*      */   {
/*   91 */     if (s.equals("ROW_FOOTER")) {
/*   92 */       this._rowFoot = ((JViewport)addSingletonComponent(this._rowFoot, c));
/*      */     }
/*   94 */     else if (s.equals("SUB_COLUMN_HEADER")) {
/*   95 */       this._subColHead = ((JViewport)addSingletonComponent(this._subColHead, c));
/*      */     }
/*   97 */     else if (s.equals("COLUMN_FOOTER")) {
/*   98 */       this._colFoot = ((JViewport)addSingletonComponent(this._colFoot, c));
/*      */     }
/*  100 */     else if (s.equals("HORIZONTAL_LEFT")) {
/*  101 */       this._hLeft = addSingletonComponent(this._hLeft, c);
/*      */     }
/*  103 */     else if (s.equals("HORIZONTAL_RIGHT")) {
/*  104 */       this._hRight = addSingletonComponent(this._hRight, c);
/*      */     }
/*  106 */     else if (s.equals("VERTICAL_TOP")) {
/*  107 */       this._vTop = addSingletonComponent(this._vTop, c);
/*      */     }
/*  109 */     else if (s.equals("VERTICAL_BOTTOM")) {
/*  110 */       this._vBottom = addSingletonComponent(this._vBottom, c);
/*      */     }
/*      */     else
/*  113 */       super.addLayoutComponent(s, c);
/*      */   }
/*      */ 
/*      */   public void removeLayoutComponent(Component c)
/*      */   {
/*  119 */     if (c == this._rowFoot) {
/*  120 */       this._rowFoot = null;
/*      */     }
/*  122 */     else if (c == this._subColHead) {
/*  123 */       this._subColHead = null;
/*      */     }
/*  125 */     else if (c == this._colFoot) {
/*  126 */       this._colFoot = null;
/*      */     }
/*  128 */     else if (c == this._hLeft) {
/*  129 */       this._hLeft = null;
/*      */     }
/*  131 */     else if (c == this._hRight) {
/*  132 */       this._hRight = null;
/*      */     }
/*  134 */     else if (c == this._vTop) {
/*  135 */       this._vTop = null;
/*      */     }
/*  137 */     else if (c == this._vBottom) {
/*  138 */       this._vBottom = null;
/*      */     }
/*      */     else
/*  141 */       super.removeLayoutComponent(c);
/*      */   }
/*      */ 
/*      */   public JViewport getRowFooter()
/*      */   {
/*  153 */     return this._rowFoot;
/*      */   }
/*      */ 
/*      */   public JViewport getRowSubColumnHeader()
/*      */   {
/*  164 */     return this._subColHead;
/*      */   }
/*      */ 
/*      */   public JViewport getColumnFooter()
/*      */   {
/*  175 */     return this._colFoot;
/*      */   }
/*      */ 
/*      */   public Component getScrollBarCorner(String key)
/*      */   {
/*  188 */     if (key.equals("HORIZONTAL_LEFT")) {
/*  189 */       return this._hLeft;
/*      */     }
/*  191 */     if (key.equals("HORIZONTAL_RIGHT")) {
/*  192 */       return this._hRight;
/*      */     }
/*  194 */     if (key.equals("VERTICAL_BOTTOM")) {
/*  195 */       return this._vBottom;
/*      */     }
/*  197 */     if (key.equals("VERTICAL_TOP")) {
/*  198 */       return this._vTop;
/*      */     }
/*      */ 
/*  201 */     return super.getCorner(key);
/*      */   }
/*      */ 
/*      */   public Dimension preferredLayoutSize(Container parent)
/*      */   {
/*  222 */     JScrollPane scrollPane = (JScrollPane)parent;
/*  223 */     this.vsbPolicy = scrollPane.getVerticalScrollBarPolicy();
/*  224 */     this.hsbPolicy = scrollPane.getHorizontalScrollBarPolicy();
/*      */ 
/*  226 */     Insets insets = parent.getInsets();
/*  227 */     int prefWidth = insets.left + insets.right;
/*  228 */     int prefHeight = insets.top + insets.bottom;
/*      */ 
/*  235 */     Dimension extentSize = null;
/*  236 */     Dimension viewSize = null;
/*  237 */     Component view = null;
/*      */ 
/*  239 */     if (this.viewport != null) {
/*  240 */       extentSize = this.viewport.getPreferredSize();
/*  241 */       viewSize = this.viewport.getViewSize();
/*  242 */       view = this.viewport.getView();
/*      */     }
/*      */ 
/*  248 */     if (extentSize != null) {
/*  249 */       prefWidth += extentSize.width;
/*  250 */       prefHeight += extentSize.height;
/*      */     }
/*      */ 
/*  256 */     Border viewportBorder = scrollPane.getViewportBorder();
/*  257 */     if (viewportBorder != null) {
/*  258 */       Insets vpbInsets = viewportBorder.getBorderInsets(parent);
/*  259 */       prefWidth += vpbInsets.left + vpbInsets.right;
/*  260 */       prefHeight += vpbInsets.top + vpbInsets.bottom;
/*      */     }
/*      */ 
/*  267 */     int rowHeaderWidth = 0;
/*  268 */     if ((this.rowHead != null) && (this.rowHead.isVisible())) {
/*  269 */       rowHeaderWidth = this.rowHead.getPreferredSize().width;
/*      */     }
/*  271 */     if ((this.upperLeft != null) && (this.upperLeft.isVisible())) {
/*  272 */       rowHeaderWidth = Math.max(rowHeaderWidth, this.upperLeft.getPreferredSize().width);
/*      */     }
/*  274 */     if ((this.lowerLeft != null) && (this.lowerLeft.isVisible())) {
/*  275 */       rowHeaderWidth = Math.max(rowHeaderWidth, this.lowerLeft.getPreferredSize().width);
/*      */     }
/*  277 */     prefWidth += rowHeaderWidth;
/*      */ 
/*  279 */     int upperHeight = getUpperHeight();
/*      */ 
/*  281 */     prefHeight += upperHeight;
/*      */ 
/*  283 */     if ((this._rowFoot != null) && (this._rowFoot.isVisible())) {
/*  284 */       prefWidth += this._rowFoot.getPreferredSize().width;
/*      */     }
/*      */ 
/*  287 */     int lowerHeight = getLowerHeight();
/*  288 */     prefHeight += lowerHeight;
/*      */ 
/*  307 */     if ((this.vsb != null) && (this.vsbPolicy != 21)) {
/*  308 */       if (this.vsbPolicy == 22) {
/*  309 */         prefWidth += this.vsb.getPreferredSize().width;
/*      */       }
/*  311 */       else if ((viewSize != null) && (extentSize != null)) {
/*  312 */         boolean canScroll = true;
/*  313 */         if ((view instanceof Scrollable)) {
/*  314 */           canScroll = !((Scrollable)view).getScrollableTracksViewportHeight();
/*      */         }
/*  316 */         if ((canScroll) && (viewSize.height > extentSize.height)) {
/*  317 */           prefWidth += this.vsb.getPreferredSize().width;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  322 */     if ((this.hsb != null) && (this.hsbPolicy != 31)) {
/*  323 */       if (this.hsbPolicy == 32) {
/*  324 */         prefHeight += this.hsb.getPreferredSize().height;
/*      */       }
/*  326 */       else if ((viewSize != null) && (extentSize != null)) {
/*  327 */         boolean canScroll = true;
/*  328 */         if ((view instanceof Scrollable)) {
/*  329 */           canScroll = !((Scrollable)view).getScrollableTracksViewportWidth();
/*      */         }
/*  331 */         if ((canScroll) && (viewSize.width > extentSize.width)) {
/*  332 */           prefHeight += this.hsb.getPreferredSize().height;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  337 */     return new Dimension(prefWidth, prefHeight);
/*      */   }
/*      */ 
/*      */   private int getUpperHeight() {
/*  341 */     int upperHeight = 0;
/*      */ 
/*  343 */     if ((this.upperLeft != null) && (this.upperLeft.isVisible())) {
/*  344 */       upperHeight = this.upperLeft.getPreferredSize().height;
/*      */     }
/*  346 */     if ((this.upperRight != null) && (this.upperRight.isVisible())) {
/*  347 */       upperHeight = Math.max(this.upperRight.getPreferredSize().height, upperHeight);
/*      */     }
/*      */ 
/*  350 */     if ((this.colHead != null) && (this.colHead.isVisible())) {
/*  351 */       upperHeight = Math.max(this.colHead.getPreferredSize().height, upperHeight);
/*      */     }
/*  353 */     return upperHeight;
/*      */   }
/*      */ 
/*      */   private int getLowerHeight() {
/*  357 */     int lowerHeight = 0;
/*      */ 
/*  359 */     if ((this.lowerLeft != null) && (this.lowerLeft.isVisible())) {
/*  360 */       lowerHeight = this.lowerLeft.getPreferredSize().height;
/*      */     }
/*  362 */     if ((this.lowerRight != null) && (this.lowerRight.isVisible())) {
/*  363 */       lowerHeight = Math.max(this.lowerRight.getPreferredSize().height, lowerHeight);
/*      */     }
/*  365 */     if ((this._colFoot != null) && (this._colFoot.isVisible())) {
/*  366 */       lowerHeight = Math.max(this._colFoot.getPreferredSize().height, lowerHeight);
/*      */     }
/*  368 */     return lowerHeight;
/*      */   }
/*      */ 
/*      */   public Dimension minimumLayoutSize(Container parent)
/*      */   {
/*  384 */     JScrollPane scrollPane = (JScrollPane)parent;
/*  385 */     this.vsbPolicy = scrollPane.getVerticalScrollBarPolicy();
/*  386 */     this.hsbPolicy = scrollPane.getHorizontalScrollBarPolicy();
/*      */ 
/*  388 */     Insets insets = parent.getInsets();
/*  389 */     int minWidth = insets.left + insets.right;
/*  390 */     int minHeight = insets.top + insets.bottom;
/*      */ 
/*  395 */     if (this.viewport != null) {
/*  396 */       Dimension size = this.viewport.getMinimumSize();
/*  397 */       minWidth += size.width;
/*  398 */       minHeight += size.height;
/*      */     }
/*      */ 
/*  404 */     Border viewportBorder = scrollPane.getViewportBorder();
/*  405 */     if (viewportBorder != null) {
/*  406 */       Insets vpbInsets = viewportBorder.getBorderInsets(parent);
/*  407 */       minWidth += vpbInsets.left + vpbInsets.right;
/*  408 */       minHeight += vpbInsets.top + vpbInsets.bottom;
/*      */     }
/*      */ 
/*  415 */     int rowHeaderWidth = 0;
/*  416 */     if ((this.rowHead != null) && (this.rowHead.isVisible())) {
/*  417 */       Dimension size = this.rowHead.getMinimumSize();
/*  418 */       rowHeaderWidth = size.width;
/*  419 */       minHeight = Math.max(minHeight, size.height);
/*      */     }
/*  421 */     if ((this.upperLeft != null) && (this.upperLeft.isVisible())) {
/*  422 */       rowHeaderWidth = Math.max(rowHeaderWidth, this.upperLeft.getMinimumSize().width);
/*      */     }
/*  424 */     if ((this.lowerLeft != null) && (this.lowerLeft.isVisible())) {
/*  425 */       rowHeaderWidth = Math.max(rowHeaderWidth, this.lowerLeft.getMinimumSize().width);
/*      */     }
/*  427 */     minWidth += rowHeaderWidth;
/*      */ 
/*  429 */     int upperHeight = 0;
/*      */ 
/*  431 */     if ((this.upperLeft != null) && (this.upperLeft.isVisible())) {
/*  432 */       upperHeight = this.upperLeft.getMinimumSize().height;
/*      */     }
/*  434 */     if ((this.upperRight != null) && (this.upperRight.isVisible())) {
/*  435 */       upperHeight = Math.max(this.upperRight.getMinimumSize().height, upperHeight);
/*      */     }
/*      */ 
/*  438 */     if ((this.colHead != null) && (this.colHead.isVisible())) {
/*  439 */       Dimension size = this.colHead.getMinimumSize();
/*  440 */       minWidth = Math.max(minWidth, size.width);
/*  441 */       upperHeight = Math.max(size.height, upperHeight);
/*      */     }
/*      */ 
/*  444 */     minHeight += upperHeight;
/*  445 */     if ((this._subColHead != null) && (this._subColHead.isVisible())) {
/*  446 */       Dimension size = this._subColHead.getMinimumSize();
/*  447 */       minWidth = Math.max(minWidth, size.width);
/*  448 */       minHeight += size.height;
/*      */     }
/*      */ 
/*  452 */     int lowerHeight = 0;
/*      */ 
/*  454 */     if ((this.lowerLeft != null) && (this.lowerLeft.isVisible())) {
/*  455 */       lowerHeight = this.lowerLeft.getMinimumSize().height;
/*      */     }
/*  457 */     if ((this.lowerRight != null) && (this.lowerRight.isVisible())) {
/*  458 */       lowerHeight = Math.max(this.lowerRight.getMinimumSize().height, lowerHeight);
/*      */     }
/*      */ 
/*  461 */     if ((this._colFoot != null) && (this._colFoot.isVisible())) {
/*  462 */       Dimension size = this._colFoot.getMinimumSize();
/*  463 */       minWidth = Math.max(minWidth, size.width);
/*  464 */       lowerHeight = Math.max(size.height, lowerHeight);
/*      */     }
/*      */ 
/*  467 */     minHeight += lowerHeight;
/*      */ 
/*  469 */     if ((this._rowFoot != null) && (this._rowFoot.isVisible())) {
/*  470 */       Dimension size = this._rowFoot.getMinimumSize();
/*  471 */       minWidth = Math.max(minWidth, size.width);
/*  472 */       minHeight += size.height;
/*      */     }
/*      */ 
/*  480 */     if ((this.vsb != null) && (this.vsbPolicy != 21)) {
/*  481 */       Dimension size = this.vsb.getMinimumSize();
/*  482 */       minWidth += size.width;
/*  483 */       minHeight = Math.max(minHeight, size.height);
/*      */     }
/*      */ 
/*  486 */     if ((this.hsb != null) && (this.hsbPolicy != 31)) {
/*  487 */       Dimension size = this.hsb.getMinimumSize();
/*  488 */       minWidth = Math.max(minWidth, size.width);
/*  489 */       minHeight += size.height;
/*      */     }
/*      */ 
/*  492 */     return new Dimension(minWidth, minHeight);
/*      */   }
/*      */ 
/*      */   public void layoutContainer(Container parent)
/*      */   {
/*  525 */     JScrollPane scrollPane = (JScrollPane)parent;
/*  526 */     this.vsbPolicy = scrollPane.getVerticalScrollBarPolicy();
/*  527 */     this.hsbPolicy = scrollPane.getHorizontalScrollBarPolicy();
/*      */ 
/*  529 */     Rectangle availR = scrollPane.getBounds();
/*  530 */     availR.x = (availR.y = 0);
/*      */ 
/*  532 */     Insets insets = parent.getInsets();
/*  533 */     availR.x = insets.left;
/*  534 */     availR.y = insets.top;
/*  535 */     availR.width -= insets.left + insets.right;
/*  536 */     availR.height -= insets.top + insets.bottom;
/*      */ 
/*  543 */     Rectangle colHeadR = new Rectangle(0, availR.y, 0, 0);
/*      */ 
/*  545 */     int upperHeight = getUpperHeight();
/*      */ 
/*  547 */     if ((this.colHead != null) && (this.colHead.isVisible())) {
/*  548 */       int colHeadHeight = Math.min(availR.height, upperHeight);
/*  549 */       colHeadR.height = colHeadHeight;
/*  550 */       availR.y += colHeadHeight;
/*  551 */       availR.height -= colHeadHeight;
/*      */     }
/*      */ 
/*  554 */     Rectangle subColHeadR = new Rectangle(0, availR.y, 0, 0);
/*  555 */     if ((this._subColHead != null) && (this._subColHead.isVisible())) {
/*  556 */       int subColHeadHeight = Math.min(availR.height, this._subColHead.getPreferredSize().height);
/*  557 */       subColHeadR.height = subColHeadHeight;
/*  558 */       availR.y += subColHeadHeight;
/*  559 */       availR.height -= subColHeadHeight;
/*      */     }
/*      */ 
/*  567 */     Rectangle rowHeadR = new Rectangle(0, 0, 0, 0);
/*      */ 
/*  569 */     if ((this.rowHead != null) && (this.rowHead.isVisible())) {
/*  570 */       int rowHeadWidth = this.rowHead.getPreferredSize().width;
/*  571 */       if ((this.upperLeft != null) && (this.upperLeft.isVisible())) {
/*  572 */         rowHeadWidth = Math.max(rowHeadWidth, this.upperLeft.getPreferredSize().width);
/*      */       }
/*  574 */       if ((this.lowerLeft != null) && (this.lowerLeft.isVisible())) {
/*  575 */         rowHeadWidth = Math.max(rowHeadWidth, this.lowerLeft.getPreferredSize().width);
/*      */       }
/*      */ 
/*  578 */       rowHeadR.width = rowHeadWidth;
/*  579 */       availR.width -= rowHeadWidth;
/*  580 */       rowHeadR.x = availR.x;
/*  581 */       availR.x += rowHeadWidth;
/*      */     }
/*      */ 
/*  588 */     Border viewportBorder = scrollPane.getViewportBorder();
/*      */     Insets vpbInsets;
/*  590 */     if (viewportBorder != null) {
/*  591 */       Insets vpbInsets = viewportBorder.getBorderInsets(parent);
/*  592 */       availR.x += vpbInsets.left;
/*  593 */       availR.y += vpbInsets.top;
/*  594 */       availR.width -= vpbInsets.left + vpbInsets.right;
/*  595 */       availR.height -= vpbInsets.top + vpbInsets.bottom;
/*      */     }
/*      */     else {
/*  598 */       vpbInsets = new Insets(0, 0, 0, 0);
/*      */     }
/*      */ 
/*  606 */     Rectangle rowFootR = new Rectangle(0, 0, 0, 0);
/*      */ 
/*  608 */     if ((this._rowFoot != null) && (this._rowFoot.isVisible())) {
/*  609 */       int rowFootWidth = this._rowFoot.getPreferredSize().width;
/*  610 */       if ((this.upperRight != null) && (this.upperRight.isVisible())) {
/*  611 */         rowFootWidth = Math.max(rowFootWidth, this.upperRight.getPreferredSize().width);
/*      */       }
/*  613 */       if ((this.lowerRight != null) && (this.lowerRight.isVisible())) {
/*  614 */         rowFootWidth = Math.max(rowFootWidth, this.lowerRight.getPreferredSize().width);
/*      */       }
/*  616 */       rowFootR.width = rowFootWidth;
/*  617 */       availR.width -= rowFootWidth;
/*  618 */       availR.x += availR.width;
/*      */     }
/*      */ 
/*  626 */     Rectangle colFootR = new Rectangle(0, availR.y, 0, 0);
/*      */ 
/*  628 */     int lowerHeight = getLowerHeight();
/*      */ 
/*  630 */     if ((this._colFoot != null) && (this._colFoot.isVisible())) {
/*  631 */       int colFootHeight = Math.min(availR.height, lowerHeight);
/*  632 */       colFootR.height = colFootHeight;
/*  633 */       availR.height -= colFootHeight;
/*  634 */       availR.y += availR.height;
/*      */     }
/*      */ 
/*  658 */     Component view = this.viewport != null ? this.viewport.getView() : null;
/*  659 */     Dimension viewPrefSize = view != null ? view.getPreferredSize() : new Dimension(0, 0);
/*      */ 
/*  661 */     Dimension extentSize = this.viewport != null ? this.viewport.toViewCoordinates(availR.getSize()) : new Dimension(0, 0);
/*      */ 
/*  665 */     boolean viewTracksViewportWidth = false;
/*  666 */     boolean viewTracksViewportHeight = false;
/*  667 */     boolean isEmpty = (availR.width < 0) || (availR.height < 0);
/*      */     Scrollable sv;
/*  672 */     if ((!isEmpty) && ((view instanceof Scrollable))) {
/*  673 */       Scrollable sv = (Scrollable)view;
/*  674 */       viewTracksViewportWidth = sv.getScrollableTracksViewportWidth();
/*  675 */       viewTracksViewportHeight = sv.getScrollableTracksViewportHeight();
/*      */     }
/*      */     else {
/*  678 */       sv = null;
/*      */     }
/*      */ 
/*  686 */     Rectangle vsbR = new Rectangle(0, isVsbCoversWholeHeight(scrollPane) ? insets.top : availR.y - vpbInsets.top, 0, 0);
/*      */     boolean vsbNeeded;
/*      */     boolean vsbNeeded;
/*  689 */     if (this.vsbPolicy == 22) {
/*  690 */       vsbNeeded = true;
/*      */     }
/*      */     else
/*      */     {
/*      */       boolean vsbNeeded;
/*  692 */       if (this.vsbPolicy == 21) {
/*  693 */         vsbNeeded = false;
/*      */       }
/*      */       else
/*      */       {
/*      */         boolean vsbNeeded;
/*  695 */         if (isEmpty) {
/*  696 */           vsbNeeded = false;
/*      */         }
/*      */         else {
/*  699 */           vsbNeeded = (!viewTracksViewportHeight) && ((viewPrefSize.height > extentSize.height) || ((this.rowHead != null) && (this.rowHead.getView() != null) && (this.rowHead.getView().getPreferredSize().height > extentSize.height)));
/*  700 */           if ((!vsbNeeded) && ((scrollPane instanceof JideScrollPane)) && (((JideScrollPane)scrollPane).isKeepCornerVisible()) && ((this._vBottom != null) || (this._vTop != null))) {
/*  701 */             vsbNeeded = true;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  706 */     if ((this.vsb != null) && (vsbNeeded)) {
/*  707 */       adjustForVSB(true, availR, vsbR, vpbInsets, true);
/*  708 */       extentSize = this.viewport.toViewCoordinates(availR.getSize());
/*      */     }
/*      */ 
/*  716 */     Rectangle hsbR = new Rectangle(isHsbCoversWholeWidth(scrollPane) ? insets.left : availR.x - vpbInsets.left, 0, 0, 0);
/*      */     boolean hsbNeeded;
/*      */     boolean hsbNeeded;
/*  718 */     if (this.hsbPolicy == 32) {
/*  719 */       hsbNeeded = true;
/*      */     }
/*      */     else
/*      */     {
/*      */       boolean hsbNeeded;
/*  721 */       if (this.hsbPolicy == 31) {
/*  722 */         hsbNeeded = false;
/*      */       }
/*      */       else
/*      */       {
/*      */         boolean hsbNeeded;
/*  724 */         if (isEmpty) {
/*  725 */           hsbNeeded = false;
/*      */         }
/*      */         else {
/*  728 */           hsbNeeded = (!viewTracksViewportWidth) && ((viewPrefSize.width > extentSize.width) || ((this.colHead != null) && (this.colHead.getView() != null) && (this.colHead.getView().getPreferredSize().width > extentSize.width)));
/*  729 */           if ((!hsbNeeded) && ((scrollPane instanceof JideScrollPane)) && (((JideScrollPane)scrollPane).isKeepCornerVisible()) && ((this._hLeft != null) || (this._hRight != null)))
/*  730 */             hsbNeeded = true;
/*      */         }
/*      */       }
/*      */     }
/*  734 */     if ((this.hsb != null) && (hsbNeeded)) {
/*  735 */       adjustForHSB(true, availR, hsbR, vpbInsets);
/*      */ 
/*  743 */       if ((this.vsb != null) && (!vsbNeeded) && (this.vsbPolicy != 21))
/*      */       {
/*  746 */         extentSize = this.viewport.toViewCoordinates(availR.getSize());
/*  747 */         vsbNeeded = viewPrefSize.height > extentSize.height;
/*  748 */         if ((!vsbNeeded) && ((scrollPane instanceof JideScrollPane)) && (((JideScrollPane)scrollPane).isKeepCornerVisible()) && ((this._vBottom != null) || (this._vTop != null))) {
/*  749 */           vsbNeeded = true;
/*      */         }
/*      */ 
/*  752 */         if (vsbNeeded) {
/*  753 */           adjustForVSB(true, availR, vsbR, vpbInsets, true);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  766 */     boolean ltr = scrollPane.getComponentOrientation().isLeftToRight();
/*      */ 
/*  769 */     if (this.viewport != null) {
/*  770 */       this.viewport.setBounds(adjustBounds(parent, availR, ltr));
/*      */ 
/*  773 */       if (sv != null) {
/*  774 */         extentSize = this.viewport.toViewCoordinates(availR.getSize());
/*      */ 
/*  776 */         boolean oldHSBNeeded = hsbNeeded;
/*  777 */         boolean oldVSBNeeded = vsbNeeded;
/*  778 */         viewTracksViewportWidth = sv.getScrollableTracksViewportWidth();
/*  779 */         viewTracksViewportHeight = sv.getScrollableTracksViewportHeight();
/*  780 */         if ((this.vsb != null) && (this.vsbPolicy == 20)) {
/*  781 */           boolean newVSBNeeded = (!viewTracksViewportHeight) && ((viewPrefSize.height > extentSize.height) || ((this.rowHead != null) && (this.rowHead.getView() != null) && (this.rowHead.getView().getPreferredSize().height > extentSize.height)));
/*  782 */           if ((!newVSBNeeded) && ((scrollPane instanceof JideScrollPane)) && (((JideScrollPane)scrollPane).isKeepCornerVisible()) && ((this._vBottom != null) || (this._vTop != null))) {
/*  783 */             newVSBNeeded = true;
/*      */           }
/*  785 */           if (newVSBNeeded != vsbNeeded) {
/*  786 */             vsbNeeded = newVSBNeeded;
/*  787 */             adjustForVSB(vsbNeeded, availR, vsbR, vpbInsets, true);
/*  788 */             extentSize = this.viewport.toViewCoordinates(availR.getSize());
/*      */           }
/*      */         }
/*      */ 
/*  792 */         if ((this.hsb != null) && (this.hsbPolicy == 30)) {
/*  793 */           boolean newHSBbNeeded = (!viewTracksViewportWidth) && ((viewPrefSize.width > extentSize.width) || ((this.colHead != null) && (this.colHead.getView() != null) && (this.colHead.getView().getPreferredSize().width > extentSize.width)));
/*  794 */           if ((!newHSBbNeeded) && ((scrollPane instanceof JideScrollPane)) && (((JideScrollPane)scrollPane).isKeepCornerVisible()) && ((this._hLeft != null) || (this._hRight != null))) {
/*  795 */             newHSBbNeeded = true;
/*      */           }
/*  797 */           if (newHSBbNeeded != hsbNeeded) {
/*  798 */             hsbNeeded = newHSBbNeeded;
/*  799 */             adjustForHSB(hsbNeeded, availR, hsbR, vpbInsets);
/*  800 */             if ((this.vsb != null) && (!vsbNeeded) && (this.vsbPolicy != 21))
/*      */             {
/*  803 */               extentSize = this.viewport.toViewCoordinates(availR.getSize());
/*      */ 
/*  805 */               vsbNeeded = viewPrefSize.height > extentSize.height;
/*      */ 
/*  807 */               if ((!vsbNeeded) && ((scrollPane instanceof JideScrollPane)) && (((JideScrollPane)scrollPane).isKeepCornerVisible()) && ((this._vBottom != null) || (this._vTop != null))) {
/*  808 */                 vsbNeeded = true;
/*      */               }
/*      */ 
/*  811 */               if (vsbNeeded) {
/*  812 */                 adjustForVSB(true, availR, vsbR, vpbInsets, true);
/*      */               }
/*      */             }
/*  815 */             if ((this._rowFoot != null) && (this._rowFoot.isVisible())) {
/*  816 */               vsbR.x += rowFootR.width;
/*      */             }
/*      */           }
/*      */         }
/*  820 */         if ((oldHSBNeeded != hsbNeeded) || (oldVSBNeeded != vsbNeeded))
/*      */         {
/*  822 */           this.viewport.setBounds(adjustBounds(parent, availR, ltr));
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  835 */     vsbR.height = (isVsbCoversWholeHeight(scrollPane) ? scrollPane.getHeight() - insets.bottom - insets.top : availR.height + vpbInsets.top + vpbInsets.bottom);
/*  836 */     hsbR.width = (isHsbCoversWholeWidth(scrollPane) ? scrollPane.getWidth() - vsbR.width - insets.left - insets.right : availR.width + vpbInsets.left + vpbInsets.right);
/*  837 */     rowHeadR.height = (availR.height + vpbInsets.top + vpbInsets.bottom);
/*  838 */     availR.y -= vpbInsets.top;
/*  839 */     colHeadR.width = (availR.width + vpbInsets.left + vpbInsets.right);
/*  840 */     availR.x -= vpbInsets.left;
/*  841 */     subColHeadR.width = colHeadR.width;
/*  842 */     subColHeadR.x = colHeadR.x;
/*      */ 
/*  844 */     colFootR.x = availR.x;
/*  845 */     rowHeadR.y += rowHeadR.height;
/*  846 */     colFootR.width = availR.width;
/*  847 */     availR.x += availR.width;
/*  848 */     rowFootR.y = availR.y;
/*  849 */     rowFootR.height = availR.height;
/*      */ 
/*  851 */     vsbR.x += rowFootR.width;
/*  852 */     hsbR.y += colFootR.height;
/*      */ 
/*  858 */     if (this.rowHead != null) {
/*  859 */       this.rowHead.setBounds(adjustBounds(parent, rowHeadR, ltr));
/*      */     }
/*      */ 
/*  862 */     if (this._rowFoot != null) {
/*  863 */       this._rowFoot.setBounds(adjustBounds(parent, rowFootR, ltr));
/*      */     }
/*      */ 
/*  866 */     int columnHeaderHeight = isColumnHeadersHeightUnified(scrollPane) ? Math.max(colHeadR.height, Math.max(this.upperLeft == null ? 0 : this.upperLeft.getPreferredSize().height, this.upperRight == null ? 0 : this.upperRight.getPreferredSize().height)) : 0;
/*      */ 
/*  868 */     int columnFooterHeight = isColumnFootersHeightUnified(scrollPane) ? Math.max(colFootR.height, Math.max(this.lowerLeft == null ? 0 : this.lowerLeft.getPreferredSize().height, this.lowerRight == null ? 0 : this.lowerRight.getPreferredSize().height)) : 0;
/*      */ 
/*  871 */     if (this.colHead != null) {
/*  872 */       int height = isColumnHeadersHeightUnified(scrollPane) ? columnHeaderHeight : Math.min(colHeadR.height, this.colHead.getPreferredSize().height);
/*  873 */       this.colHead.setBounds(adjustBounds(parent, new Rectangle(colHeadR.x, colHeadR.y + colHeadR.height - height, colHeadR.width, height), ltr));
/*      */     }
/*      */ 
/*  876 */     if (this._subColHead != null) {
/*  877 */       this._subColHead.setBounds(adjustBounds(parent, subColHeadR, ltr));
/*      */     }
/*      */ 
/*  880 */     if (this._colFoot != null) {
/*  881 */       int height = isColumnFootersHeightUnified(scrollPane) ? columnFooterHeight : Math.min(colFootR.height, this._colFoot.getPreferredSize().height);
/*  882 */       this._colFoot.setBounds(adjustBounds(parent, new Rectangle(colFootR.x, colFootR.y, colFootR.width, height), ltr));
/*      */     }
/*  885 */     else if (isColumnFootersHeightUnified(scrollPane)) {
/*  886 */       columnFooterHeight = hsbR.height;
/*      */     }
/*      */ 
/*  890 */     if (this.vsb != null) {
/*  891 */       if (vsbNeeded) {
/*  892 */         this.vsb.setVisible(true);
/*  893 */         if ((this.vsbPolicy == 20) && (!isEmpty) && ((viewTracksViewportHeight) || ((viewPrefSize.height <= extentSize.height) && ((this.rowHead == null) || (this.rowHead.getView() == null) || (this.rowHead.getView().getPreferredSize().height <= extentSize.height))))) {
/*  894 */           this.vsb.setVisible(false);
/*      */         }
/*  896 */         if ((this._vTop == null) && (this._vBottom == null)) {
/*  897 */           this.vsb.setBounds(adjustBounds(parent, vsbR, ltr));
/*      */         } else {
/*  899 */           Rectangle rect = new Rectangle(vsbR);
/*  900 */           if (this._vTop != null) {
/*  901 */             Dimension dim = this._vTop.getPreferredSize();
/*  902 */             rect.y += dim.height;
/*  903 */             rect.height -= dim.height;
/*  904 */             this._vTop.setVisible(true);
/*  905 */             this._vTop.setBounds(adjustBounds(parent, new Rectangle(vsbR.x, vsbR.y, vsbR.width, dim.height), ltr));
/*      */           }
/*  907 */           if (this._vBottom != null) {
/*  908 */             Dimension dim = this._vBottom.getPreferredSize();
/*  909 */             rect.height -= dim.height;
/*  910 */             this._vBottom.setVisible(true);
/*  911 */             this._vBottom.setBounds(adjustBounds(parent, new Rectangle(vsbR.x, vsbR.y + vsbR.height - dim.height, vsbR.width, dim.height), ltr));
/*      */           }
/*  913 */           this.vsb.setBounds(adjustBounds(parent, rect, ltr));
/*      */         }
/*      */       }
/*      */       else {
/*  917 */         if (viewPrefSize.height > extentSize.height) {
/*  918 */           this.vsb.setVisible(true);
/*  919 */           this.vsb.setBounds(adjustBounds(parent, new Rectangle(vsbR.x, vsbR.y, 0, vsbR.height), ltr));
/*      */         }
/*      */         else {
/*  922 */           this.vsb.setVisible(false);
/*      */         }
/*  924 */         if (this._vTop != null)
/*  925 */           this._vTop.setVisible(false);
/*  926 */         if (this._vBottom != null) {
/*  927 */           this._vBottom.setVisible(false);
/*      */         }
/*      */       }
/*      */     }
/*  931 */     if (this.hsb != null) {
/*  932 */       if (hsbNeeded) {
/*  933 */         this.hsb.setVisible(true);
/*  934 */         if ((this.hsbPolicy == 30) && (!isEmpty) && ((viewTracksViewportWidth) || ((viewPrefSize.width <= extentSize.width) && ((this.colHead == null) || (this.colHead.getView() == null) || (this.colHead.getView().getPreferredSize().width <= extentSize.width))))) {
/*  935 */           this.hsb.setVisible(false);
/*      */         }
/*  937 */         if ((this._hLeft == null) && (this._hRight == null)) {
/*  938 */           this.hsb.setBounds(adjustBounds(parent, hsbR, ltr));
/*      */         } else {
/*  940 */           Rectangle rect = new Rectangle(hsbR);
/*  941 */           if (this._hLeft != null) {
/*  942 */             Dimension dim = this._hLeft.getPreferredSize();
/*  943 */             rect.x += dim.width;
/*  944 */             rect.width -= dim.width;
/*  945 */             this._hLeft.setVisible(true);
/*  946 */             this._hLeft.setBounds(adjustBounds(parent, new Rectangle(hsbR.x, hsbR.y, dim.width, hsbR.height), ltr));
/*  947 */             this._hLeft.doLayout();
/*      */           }
/*  949 */           if (this._hRight != null) {
/*  950 */             Dimension dim = this._hRight.getPreferredSize();
/*  951 */             rect.width -= dim.width;
/*  952 */             this._hRight.setVisible(true);
/*  953 */             this._hRight.setBounds(adjustBounds(parent, new Rectangle(hsbR.x + hsbR.width - dim.width, hsbR.y, dim.width, hsbR.height), ltr));
/*      */           }
/*  955 */           this.hsb.setBounds(adjustBounds(parent, rect, ltr));
/*      */         }
/*      */       }
/*      */       else {
/*  959 */         if (viewPrefSize.width > extentSize.width) {
/*  960 */           this.hsb.setVisible(true);
/*  961 */           this.hsb.setBounds(adjustBounds(parent, new Rectangle(hsbR.x, hsbR.y, hsbR.width, 0), ltr));
/*      */         }
/*      */         else {
/*  964 */           this.hsb.setVisible(false);
/*      */         }
/*  966 */         if (this._hLeft != null)
/*  967 */           this._hLeft.setVisible(false);
/*  968 */         if (this._hRight != null) {
/*  969 */           this._hRight.setVisible(false);
/*      */         }
/*      */       }
/*      */     }
/*  973 */     if ((this.lowerLeft != null) && (this.lowerLeft.isVisible())) {
/*  974 */       int height = isColumnFootersHeightUnified(scrollPane) ? columnFooterHeight : Math.min(this.lowerLeft.getPreferredSize().height, colFootR.height);
/*  975 */       this.lowerLeft.setBounds(adjustBounds(parent, new Rectangle(rowHeadR.x, colFootR.y != 0 ? colFootR.y : hsbR.y, rowHeadR.width, height), ltr));
/*      */     }
/*      */ 
/*  978 */     if ((this.lowerRight != null) && (this.lowerRight.isVisible())) {
/*  979 */       int height = isColumnFootersHeightUnified(scrollPane) ? columnFooterHeight : Math.min(this.lowerRight.getPreferredSize().height, colFootR.height);
/*  980 */       this.lowerRight.setBounds(adjustBounds(parent, new Rectangle(rowFootR.x, colFootR.y != 0 ? colFootR.y : hsbR.y, rowFootR.width, height), ltr));
/*      */     }
/*      */ 
/*  983 */     if ((this.upperLeft != null) && (this.upperLeft.isVisible())) {
/*  984 */       int height = isColumnHeadersHeightUnified(scrollPane) ? columnHeaderHeight : Math.min(this.upperLeft.getPreferredSize().height, colHeadR.height);
/*  985 */       this.upperLeft.setBounds(adjustBounds(parent, new Rectangle(rowHeadR.x, colHeadR.y + colHeadR.height - height, rowHeadR.width, height), ltr));
/*      */     }
/*      */ 
/*  988 */     if ((this.upperRight != null) && (this.upperRight.isVisible())) {
/*  989 */       int height = isColumnHeadersHeightUnified(scrollPane) ? columnHeaderHeight : Math.min(this.upperRight.getPreferredSize().height, colHeadR.height);
/*  990 */       this.upperRight.setBounds(adjustBounds(parent, new Rectangle(rowFootR.x, colHeadR.y + colHeadR.height - height, rowFootR.width, height), ltr));
/*      */     }
/*      */   }
/*      */ 
/*      */   private Rectangle adjustBounds(Container container, Rectangle rect, boolean ltr) {
/*  995 */     if (ltr) {
/*  996 */       return rect;
/*      */     }
/*      */ 
/*  999 */     Rectangle r = new Rectangle(rect);
/* 1000 */     int w = container.getWidth();
/* 1001 */     r.x = (w - (rect.x + rect.width));
/* 1002 */     return r;
/*      */   }
/*      */ 
/*      */   private void adjustForVSB(boolean wantsVSB, Rectangle available, Rectangle vsbR, Insets vpbInsets, boolean leftToRight)
/*      */   {
/* 1015 */     int oldWidth = vsbR.width;
/* 1016 */     if (wantsVSB) {
/* 1017 */       int vsbWidth = Math.max(0, this.vsb.getPreferredSize().width);
/*      */ 
/* 1019 */       available.width -= vsbWidth;
/* 1020 */       vsbR.width = vsbWidth;
/*      */ 
/* 1022 */       if (leftToRight) {
/* 1023 */         vsbR.x = (available.x + available.width + vpbInsets.right);
/*      */       }
/*      */       else {
/* 1026 */         available.x -= vpbInsets.left;
/* 1027 */         available.x += vsbWidth;
/*      */       }
/*      */     }
/*      */     else {
/* 1031 */       available.width += oldWidth;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void adjustForHSB(boolean wantsHSB, Rectangle available, Rectangle hsbR, Insets vpbInsets)
/*      */   {
/* 1043 */     int oldHeight = hsbR.height;
/* 1044 */     if (wantsHSB) {
/* 1045 */       int hsbHeight = Math.max(0, this.hsb.getPreferredSize().height);
/*      */ 
/* 1047 */       available.height -= hsbHeight;
/* 1048 */       hsbR.y = (available.y + available.height + vpbInsets.bottom);
/* 1049 */       hsbR.height = hsbHeight;
/*      */     }
/*      */     else {
/* 1052 */       available.height += oldHeight;
/*      */     }
/*      */   }
/*      */ 
/*      */   static class UIResource extends JideScrollPaneLayout
/*      */     implements UIResource
/*      */   {
/*      */     private static final long serialVersionUID = 1057343395078846689L;
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.JideScrollPaneLayout
 * JD-Core Version:    0.6.0
 */
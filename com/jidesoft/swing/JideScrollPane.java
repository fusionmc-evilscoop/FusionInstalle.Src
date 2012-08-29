/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Point;
/*     */ import javax.swing.JScrollBar;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JViewport;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.plaf.UIResource;
/*     */ 
/*     */ public class JideScrollPane extends JScrollPane
/*     */   implements JideScrollPaneConstants
/*     */ {
/*     */   protected JViewport _rowFooter;
/*     */   private JViewport _subColumnHeader;
/*     */   protected JViewport _columnFooter;
/*     */   protected Component _hLeft;
/*     */   protected Component _hRight;
/*     */   protected Component _vTop;
/*     */   protected Component _vBottom;
/*  73 */   private boolean _keepCornerVisible = false;
/*     */   private boolean _horizontalScrollBarCoversWholeWidth;
/*     */   private boolean _verticalScrollBarCoversWholeHeight;
/*     */   public static final String PROPERTY_HORIZONTAL_SCROLL_BAR_COVERS_WHOLE_WIDTH = "horizontalScrollBarCoversWholeWidth";
/*     */   public static final String PROPERTY_VERTICAL_SCROLL_BAR_COVERS_WHOLE_HEIGHT = "verticalScrollBarCoversWholeHeight";
/*     */   private boolean _columnHeadersHeightUnified;
/*     */   private boolean _columnFootersHeightUnified;
/*     */   public static final String PROPERTY_COLUMN_HEADERS_HEIGHT_UNIFIED = "columnHeadersHeightUnified";
/*     */   public static final String PROPERTY_COLUMN_FOOTERS_HEIGHT_UNIFIED = "columnFootersHeightUnified";
/*     */   public static final String CLIENT_PROPERTY_SLAVE_VIEWPORT = "synchronizeViewSlaveViewport";
/*     */   public static final String CLIENT_PROPERTY_MASTER_VIEWPORT = "synchronizeViewMasterViewport";
/*     */ 
/*     */   public JideScrollPane(Component view, int vsbPolicy, int hsbPolicy)
/*     */   {
/* 102 */     setLayout(new JideScrollPaneLayout.UIResource());
/* 103 */     setVerticalScrollBarPolicy(vsbPolicy);
/* 104 */     setHorizontalScrollBarPolicy(hsbPolicy);
/* 105 */     setViewport(createViewport());
/* 106 */     setVerticalScrollBar(createVerticalScrollBar());
/* 107 */     setHorizontalScrollBar(createHorizontalScrollBar());
/* 108 */     if (null != view) {
/* 109 */       setViewportView(view);
/*     */     }
/* 111 */     setOpaque(true);
/* 112 */     updateUI();
/*     */ 
/* 114 */     if (!getComponentOrientation().isLeftToRight())
/* 115 */       this.viewport.setViewPosition(new Point(2147483647, 0));
/*     */   }
/*     */ 
/*     */   public JideScrollPane(Component view)
/*     */   {
/* 128 */     this(view, 20, 30);
/*     */   }
/*     */ 
/*     */   public JideScrollPane(int vsbPolicy, int hsbPolicy)
/*     */   {
/* 141 */     this(null, vsbPolicy, hsbPolicy);
/*     */   }
/*     */ 
/*     */   public JideScrollPane()
/*     */   {
/* 150 */     this(null, 20, 30);
/*     */   }
/*     */ 
/*     */   public void setViewport(JViewport viewport)
/*     */   {
/* 155 */     JViewport old = getViewport();
/* 156 */     super.setViewport(viewport);
/* 157 */     if (old != null) {
/* 158 */       if (this.rowHeader != null) {
/* 159 */         JideSwingUtilities.unsynchronizeView(this.rowHeader, old);
/*     */       }
/* 161 */       if (this._rowFooter != null) {
/* 162 */         JideSwingUtilities.unsynchronizeView(this._rowFooter, old);
/* 163 */         JideSwingUtilities.unsynchronizeView(old, this._rowFooter);
/*     */       }
/* 165 */       if (this._columnFooter != null) {
/* 166 */         JideSwingUtilities.unsynchronizeView(this._columnFooter, old);
/* 167 */         JideSwingUtilities.unsynchronizeView(old, this._columnFooter);
/*     */       }
/* 169 */       if (this.columnHeader != null) {
/* 170 */         JideSwingUtilities.unsynchronizeView(this.columnHeader, old);
/*     */       }
/* 172 */       if (this._subColumnHeader != null) {
/* 173 */         JideSwingUtilities.unsynchronizeView(this._subColumnHeader, old);
/* 174 */         JideSwingUtilities.unsynchronizeView(old, this._subColumnHeader);
/*     */       }
/*     */     }
/* 177 */     if (viewport != null) {
/* 178 */       if (this.rowHeader != null) {
/* 179 */         JideSwingUtilities.synchronizeView(this.rowHeader, getViewport(), 1);
/*     */       }
/* 181 */       if (this._rowFooter != null) {
/* 182 */         JideSwingUtilities.synchronizeView(this._rowFooter, getViewport(), 1);
/* 183 */         JideSwingUtilities.synchronizeView(getViewport(), this._rowFooter, 1);
/*     */       }
/* 185 */       if (this._columnFooter != null) {
/* 186 */         JideSwingUtilities.synchronizeView(this._columnFooter, getViewport(), 0);
/* 187 */         JideSwingUtilities.synchronizeView(getViewport(), this._columnFooter, 0);
/*     */       }
/* 189 */       if (this.columnHeader != null) {
/* 190 */         JideSwingUtilities.synchronizeView(this.columnHeader, getViewport(), 0);
/*     */       }
/* 192 */       if (this._subColumnHeader != null) {
/* 193 */         JideSwingUtilities.synchronizeView(this._subColumnHeader, getViewport(), 0);
/* 194 */         JideSwingUtilities.synchronizeView(getViewport(), this._subColumnHeader, 0);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public JViewport getRowFooter()
/*     */   {
/* 207 */     return this._rowFooter;
/*     */   }
/*     */ 
/*     */   public void setRowFooter(JViewport rowFooter)
/*     */   {
/* 221 */     JViewport old = getRowFooter();
/* 222 */     this._rowFooter = rowFooter;
/* 223 */     if (null != rowFooter) {
/* 224 */       add(rowFooter, "ROW_FOOTER");
/*     */     }
/* 226 */     else if (null != old) {
/* 227 */       remove(old);
/*     */     }
/* 229 */     firePropertyChange("rowFooter", old, rowFooter);
/* 230 */     revalidate();
/* 231 */     repaint();
/* 232 */     if (old != null) {
/* 233 */       JideSwingUtilities.unsynchronizeView(old, getViewport());
/* 234 */       JideSwingUtilities.unsynchronizeView(getViewport(), old);
/*     */     }
/* 236 */     if (rowFooter != null) {
/* 237 */       JideSwingUtilities.synchronizeView(rowFooter, getViewport(), 1);
/* 238 */       JideSwingUtilities.synchronizeView(getViewport(), rowFooter, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setRowHeader(JViewport rowHeader)
/*     */   {
/* 250 */     JViewport old = getRowHeader();
/* 251 */     super.setRowHeader(rowHeader);
/* 252 */     if (old != null) {
/* 253 */       JideSwingUtilities.unsynchronizeView(old, getViewport());
/*     */     }
/* 255 */     if (getRowHeader() != null)
/* 256 */       JideSwingUtilities.synchronizeView(getRowHeader(), getViewport(), 1);
/*     */   }
/*     */ 
/*     */   public void setRowFooterView(Component view)
/*     */   {
/* 274 */     if (null == getRowFooter()) {
/* 275 */       setRowFooter(createViewport());
/*     */     }
/* 277 */     getRowFooter().setView(view);
/*     */   }
/*     */ 
/*     */   public JViewport getColumnFooter()
/*     */   {
/* 289 */     return this._columnFooter;
/*     */   }
/*     */ 
/*     */   public void setColumnFooter(JViewport columnFooter)
/*     */   {
/* 303 */     JViewport old = getColumnFooter();
/* 304 */     this._columnFooter = columnFooter;
/* 305 */     if (null != columnFooter) {
/* 306 */       add(columnFooter, "COLUMN_FOOTER");
/*     */     }
/* 308 */     else if (null != old) {
/* 309 */       remove(old);
/*     */     }
/* 311 */     firePropertyChange("columnFooter", old, columnFooter);
/*     */ 
/* 313 */     revalidate();
/* 314 */     repaint();
/*     */ 
/* 316 */     if (old != null) {
/* 317 */       JideSwingUtilities.unsynchronizeView(old, getViewport());
/* 318 */       JideSwingUtilities.unsynchronizeView(getViewport(), old);
/*     */     }
/* 320 */     if (this._columnFooter != null) {
/* 321 */       JideSwingUtilities.synchronizeView(this._columnFooter, getViewport(), 0);
/* 322 */       JideSwingUtilities.synchronizeView(getViewport(), this._columnFooter, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setColumnHeader(JViewport columnHeader)
/*     */   {
/* 333 */     JViewport old = getColumnHeader();
/* 334 */     super.setColumnHeader(columnHeader);
/* 335 */     if (old != null) {
/* 336 */       JideSwingUtilities.unsynchronizeView(old, getViewport());
/*     */     }
/* 338 */     if (getColumnHeader() != null)
/* 339 */       JideSwingUtilities.synchronizeView(getColumnHeader(), getViewport(), 0);
/*     */   }
/*     */ 
/*     */   public JViewport getSubColumnHeader()
/*     */   {
/* 351 */     return this._subColumnHeader;
/*     */   }
/*     */ 
/*     */   public void setSubColumnHeader(JViewport subColumnHeader)
/*     */   {
/* 363 */     JViewport old = getSubColumnHeader();
/* 364 */     this._subColumnHeader = subColumnHeader;
/* 365 */     if (null != subColumnHeader) {
/* 366 */       add(subColumnHeader, "SUB_COLUMN_HEADER");
/*     */     }
/* 368 */     else if (null != old) {
/* 369 */       remove(old);
/*     */     }
/* 371 */     firePropertyChange("subColumnHeader", old, subColumnHeader);
/*     */ 
/* 373 */     revalidate();
/* 374 */     repaint();
/*     */ 
/* 376 */     if (old != null) {
/* 377 */       JideSwingUtilities.unsynchronizeView(old, getViewport());
/* 378 */       JideSwingUtilities.unsynchronizeView(getViewport(), old);
/*     */     }
/* 380 */     if (this._subColumnHeader != null) {
/* 381 */       JideSwingUtilities.synchronizeView(this._subColumnHeader, getViewport(), 0);
/* 382 */       JideSwingUtilities.synchronizeView(getViewport(), this._subColumnHeader, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setColumnFooterView(Component view)
/*     */   {
/* 400 */     if (null == getColumnFooter()) {
/* 401 */       setColumnFooter(createViewport());
/*     */     }
/* 403 */     getColumnFooter().setView(view);
/*     */   }
/*     */ 
/*     */   public void setSubColumnHeaderView(Component view)
/*     */   {
/* 415 */     if (null == getSubColumnHeader()) {
/* 416 */       setSubColumnHeader(createViewport());
/*     */     }
/* 418 */     getSubColumnHeader().setView(view);
/*     */   }
/*     */ 
/*     */   public Component getScrollBarCorner(String key)
/*     */   {
/* 434 */     boolean isLeftToRight = getComponentOrientation().isLeftToRight();
/* 435 */     if (key.equals("HORIZONTAL_LEADING")) {
/* 436 */       key = isLeftToRight ? "HORIZONTAL_LEFT" : "HORIZONTAL_RIGHT";
/*     */     }
/* 438 */     else if (key.equals("HORIZONTAL_TRAILING")) {
/* 439 */       key = isLeftToRight ? "HORIZONTAL_RIGHT" : "HORIZONTAL_LEFT";
/*     */     }
/*     */ 
/* 442 */     if (key.equals("HORIZONTAL_LEFT")) {
/* 443 */       return this._hLeft;
/*     */     }
/* 445 */     if (key.equals("HORIZONTAL_RIGHT")) {
/* 446 */       return this._hRight;
/*     */     }
/* 448 */     if (key.equals("VERTICAL_BOTTOM")) {
/* 449 */       return this._vBottom;
/*     */     }
/* 451 */     if (key.equals("VERTICAL_TOP")) {
/* 452 */       return this._vTop;
/*     */     }
/*     */ 
/* 455 */     return null;
/*     */   }
/*     */ 
/*     */   public void setScrollBarCorner(String key, Component corner)
/*     */   {
/* 477 */     boolean isLeftToRight = getComponentOrientation().isLeftToRight();
/* 478 */     if (key.equals("HORIZONTAL_LEADING")) {
/* 479 */       key = isLeftToRight ? "HORIZONTAL_LEFT" : "HORIZONTAL_RIGHT";
/*     */     }
/* 481 */     else if (key.equals("HORIZONTAL_TRAILING")) {
/* 482 */       key = isLeftToRight ? "HORIZONTAL_RIGHT" : "HORIZONTAL_LEFT";
/*     */     }
/*     */ 
/* 485 */     if (key.equals("HORIZONTAL_LEFT")) {
/* 486 */       Component old = this._hLeft;
/* 487 */       this._hLeft = corner;
/*     */     }
/* 489 */     else if (key.equals("HORIZONTAL_RIGHT")) {
/* 490 */       Component old = this._hRight;
/* 491 */       this._hRight = corner;
/*     */     }
/* 493 */     else if (key.equals("VERTICAL_TOP")) {
/* 494 */       Component old = this._vTop;
/* 495 */       this._vTop = corner;
/*     */     }
/* 497 */     else if (key.equals("VERTICAL_BOTTOM")) {
/* 498 */       Component old = this._vBottom;
/* 499 */       this._vBottom = corner;
/*     */     }
/*     */     else {
/* 502 */       throw new IllegalArgumentException("invalid scroll bar corner key");
/*     */     }
/*     */     Component old;
/* 505 */     if (null != old) {
/* 506 */       remove(old);
/*     */     }
/* 508 */     if (null != corner) {
/* 509 */       add(corner, key);
/*     */     }
/* 511 */     if (corner != null) corner.setComponentOrientation(getComponentOrientation());
/* 512 */     firePropertyChange(key, old, corner);
/* 513 */     revalidate();
/* 514 */     repaint();
/*     */   }
/*     */ 
/*     */   public void updateUI()
/*     */   {
/* 519 */     super.updateUI();
/* 520 */     setLayout(new JideScrollPaneLayout.UIResource());
/* 521 */     if ((getBorder() instanceof UIResource))
/* 522 */       LookAndFeel.installBorder(this, "JideScrollPane.border");
/*     */   }
/*     */ 
/*     */   public void setLayout(LayoutManager layout)
/*     */   {
/* 528 */     if (!(layout instanceof JideScrollPaneLayout)) {
/* 529 */       super.setLayout(new JideScrollPaneLayout.UIResource());
/*     */     }
/*     */     else
/* 532 */       super.setLayout(layout);
/*     */   }
/*     */ 
/*     */   public boolean isVerticalScrollBarCoversWholeHeight()
/*     */   {
/* 537 */     return this._verticalScrollBarCoversWholeHeight;
/*     */   }
/*     */ 
/*     */   public void setHorizontalScrollBarCoversWholeWidth(boolean horizontalScrollBarCoversWholeWidth) {
/* 541 */     boolean old = this._horizontalScrollBarCoversWholeWidth;
/* 542 */     if (old != horizontalScrollBarCoversWholeWidth) {
/* 543 */       this._horizontalScrollBarCoversWholeWidth = horizontalScrollBarCoversWholeWidth;
/* 544 */       firePropertyChange("horizontalScrollBarCoversWholeWidth", old, this._horizontalScrollBarCoversWholeWidth);
/* 545 */       invalidate();
/* 546 */       doLayout();
/* 547 */       if (getHorizontalScrollBar() != null)
/* 548 */         getHorizontalScrollBar().doLayout();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isHorizontalScrollBarCoversWholeWidth()
/*     */   {
/* 554 */     return this._horizontalScrollBarCoversWholeWidth;
/*     */   }
/*     */ 
/*     */   public void setVerticalScrollBarCoversWholeHeight(boolean verticalScrollBarCoversWholeHeight) {
/* 558 */     boolean old = this._verticalScrollBarCoversWholeHeight;
/* 559 */     if (old != verticalScrollBarCoversWholeHeight) {
/* 560 */       this._verticalScrollBarCoversWholeHeight = verticalScrollBarCoversWholeHeight;
/* 561 */       firePropertyChange("verticalScrollBarCoversWholeHeight", old, this._verticalScrollBarCoversWholeHeight);
/* 562 */       invalidate();
/* 563 */       doLayout();
/* 564 */       if (getVerticalScrollBar() != null)
/* 565 */         getVerticalScrollBar().doLayout();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isColumnHeadersHeightUnified()
/*     */   {
/* 577 */     return this._columnHeadersHeightUnified;
/*     */   }
/*     */ 
/*     */   public void setColumnHeadersHeightUnified(boolean columnHeadersHeightUnified)
/*     */   {
/* 587 */     boolean old = this._columnHeadersHeightUnified;
/* 588 */     if (old != columnHeadersHeightUnified) {
/* 589 */       this._columnHeadersHeightUnified = columnHeadersHeightUnified;
/* 590 */       firePropertyChange("columnHeadersHeightUnified", old, this._horizontalScrollBarCoversWholeWidth);
/* 591 */       invalidate();
/* 592 */       doLayout();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isColumnFootersHeightUnified()
/*     */   {
/* 603 */     return this._columnFootersHeightUnified;
/*     */   }
/*     */ 
/*     */   public void setColumnFootersHeightUnified(boolean columnFootersHeightUnified)
/*     */   {
/* 613 */     boolean old = this._columnFootersHeightUnified;
/* 614 */     if (old != columnFootersHeightUnified) {
/* 615 */       this._columnFootersHeightUnified = columnFootersHeightUnified;
/* 616 */       firePropertyChange("columnFootersHeightUnified", old, this._horizontalScrollBarCoversWholeWidth);
/* 617 */       invalidate();
/* 618 */       doLayout();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isKeepCornerVisible()
/*     */   {
/* 634 */     return this._keepCornerVisible;
/*     */   }
/*     */ 
/*     */   public void setKeepCornerVisible(boolean keepCornerVisible)
/*     */   {
/* 644 */     this._keepCornerVisible = keepCornerVisible;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.JideScrollPane
 * JD-Core Version:    0.6.0
 */
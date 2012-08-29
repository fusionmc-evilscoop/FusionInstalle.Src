/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JWindow;
/*     */ import javax.swing.SwingUtilities;
/*     */ 
/*     */ public class Contour extends JComponent
/*     */   implements IContour
/*     */ {
/*     */   public static final int PARTIAL_OUTLINE_MODE = 0;
/*     */   public static final int MIX_OUTLINE_MODE = 1;
/*     */   public static final int FULL_OUTLINE_MODE = 2;
/*  32 */   private int _thickness = 4;
/*     */   private static final int TAB_WIDTH = 42;
/*     */   private static final int TAB_LEADING = 8;
/*  47 */   private Color _lineColor = new Color(136, 136, 136);
/*     */ 
/*  59 */   private int _tabHeight = 22;
/*     */ 
/*  65 */   private boolean _allowDocking = true;
/*     */   private boolean _tabDocking;
/*     */   private int _tabSide;
/*     */   private boolean _floating;
/*     */   private Component _attachedComponent;
/*     */   private int _attachedSide;
/*     */   private boolean _single;
/*     */   private JComponent _saveDraggedComponent;
/*     */   private int _saveX;
/*     */   private int _saveY;
/*     */   private int _saveMouseModifier;
/*     */   private Container _relativeContainer;
/* 111 */   private int _outlineMode = 0;
/*     */   private Outline _topOutline;
/*     */   private Outline _bottomOutline;
/*     */   private Outline _leftOutline;
/*     */   private Outline _rightOutline;
/* 121 */   private boolean _ghost = false;
/*     */   private Component _glassPane;
/* 125 */   private boolean _changeCursor = false;
/*     */ 
/*     */   public Contour()
/*     */   {
/* 131 */     this(22);
/* 132 */     setOpaque(false);
/* 133 */     setDoubleBuffered(true);
/*     */   }
/*     */ 
/*     */   public Contour(int tabHeight)
/*     */   {
/* 142 */     this._thickness = (UIDefaultsLookup.getInt("Contour.thickness") == 0 ? 4 : UIDefaultsLookup.getInt("Contour.thickness"));
/* 143 */     this._lineColor = (UIDefaultsLookup.getColor("Contour.color") == null ? new Color(136, 136, 136) : UIDefaultsLookup.getColor("Contour.color"));
/* 144 */     setTabHeight(tabHeight);
/* 145 */     if (getOutlineMode() != 0)
/* 146 */       initOutline();
/*     */   }
/*     */ 
/*     */   private void initOutline()
/*     */   {
/* 152 */     this._topOutline = new Outline();
/* 153 */     this._bottomOutline = new Outline();
/* 154 */     this._leftOutline = new Outline();
/* 155 */     this._rightOutline = new Outline();
/*     */   }
/*     */ 
/*     */   public boolean isDoubleBuffered()
/*     */   {
/* 165 */     return true;
/*     */   }
/*     */ 
/*     */   public void paint(Graphics g)
/*     */   {
/* 175 */     if (!this._ghost)
/* 176 */       paintOutline(g, false);
/*     */   }
/*     */ 
/*     */   private void paintOutline(Graphics g, boolean xorMode)
/*     */   {
/* 181 */     Rectangle bounds = getBounds();
/* 182 */     g.translate(-bounds.x, -bounds.y);
/*     */ 
/* 184 */     if (xorMode) {
/* 185 */       bounds = SwingUtilities.convertRectangle(this, bounds, getRelativeContainer() != null ? getRelativeContainer() : getParent());
/*     */     }
/*     */     else
/*     */     {
/* 189 */       g.setColor(this._lineColor);
/*     */     }
/*     */ 
/* 194 */     if ((getOutlineMode() != 2) && (isTabDocking())) {
/* 195 */       drawTab(g, bounds.x, bounds.y, bounds.width, bounds.height - 1, this._tabHeight - 1, 42, 8, this._thickness, getTabSide());
/*     */     }
/*     */     else
/*     */     {
/* 200 */       drawRect(g, bounds.x, bounds.y, bounds.width - 1, bounds.height - 1, this._thickness);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void drawLine(Graphics g, int x1, int y1, int x2, int y2, int thick)
/*     */   {
/* 216 */     if (x1 == x2) {
/* 217 */       if (y2 > y1) {
/* 218 */         g.fillRect(x1, y1, thick, y2 - y1);
/*     */       }
/*     */       else {
/* 221 */         g.fillRect(x1, y2, thick, y1 - y2);
/*     */       }
/*     */     }
/* 224 */     else if (y1 == y2)
/* 225 */       if (x2 > x1) {
/* 226 */         g.fillRect(x1, y1, x2 - x1, thick);
/*     */       }
/*     */       else
/* 229 */         g.fillRect(x2, y1, x1 - x2, thick);
/*     */   }
/*     */ 
/*     */   private static void drawRect(Graphics g, int x, int y, int width, int height, int thick)
/*     */   {
/* 236 */     if (width <= thick) {
/* 237 */       drawLine(g, x, y, x, y + height, thick);
/*     */     }
/* 239 */     else if (height <= thick) {
/* 240 */       drawLine(g, x, y, x + width, y, thick);
/*     */     }
/*     */     else {
/* 243 */       drawLine(g, x, y, x + width - thick, y, thick);
/* 244 */       drawLine(g, x + width - thick, y, x + width - thick, y + height - thick, thick);
/* 245 */       drawLine(g, x + width, y + height - thick, x + thick, y + height - thick, thick);
/* 246 */       drawLine(g, x, y + height, x, y + thick, thick);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void drawTab(Graphics g, int x, int y, int width, int height, int tabHeight, int tabWidth, int tabLeading, int thick, int side)
/*     */   {
/* 256 */     switch (side) {
/*     */     case 1:
/* 258 */       drawTopTab(g, x, y, width, height, tabHeight, tabWidth, tabLeading, thick);
/* 259 */       break;
/*     */     case 3:
/* 261 */       drawBottomTab(g, x, y, width, height, tabHeight, tabWidth, tabLeading, thick);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void drawTopTab(Graphics g, int x, int y, int width, int height, int tabHeight, int tabWidth, int tabLeading, int thick)
/*     */   {
/* 278 */     drawLine(g, x + width - thick, y + tabHeight, x + width - thick, y + height - 1, thick);
/*     */ 
/* 280 */     drawLine(g, x + tabLeading + thick, y + tabHeight, x, y + tabHeight, thick);
/* 281 */     drawLine(g, x + tabWidth + tabLeading, y + tabHeight, x + tabWidth + tabLeading, y, thick);
/* 282 */     drawLine(g, x + tabWidth + tabLeading, y, x + tabLeading + 1, y, thick);
/* 283 */     drawLine(g, x + tabLeading, y, x + tabLeading, y + tabHeight, thick);
/* 284 */     drawLine(g, x + width, y + tabHeight, x + tabWidth + tabLeading, y + tabHeight, thick);
/*     */ 
/* 286 */     drawLine(g, x, y + tabHeight, x, y + height, thick);
/*     */ 
/* 288 */     drawLine(g, x, y + height - thick, x + width, y + height - thick, thick);
/*     */   }
/*     */ 
/*     */   private static void drawBottomTab(Graphics g, int x, int y, int width, int height, int tabHeight, int tabWidth, int tabLeading, int thick)
/*     */   {
/* 315 */     drawLine(g, x, y, x + width - 1, y, thick);
/* 316 */     drawLine(g, x + width - thick, y, x + width - thick, y + height - tabHeight - 1, thick);
/*     */ 
/* 318 */     drawLine(g, x + width, y + height - tabHeight - thick, x + tabWidth + tabLeading, y + height - tabHeight - thick, thick);
/* 319 */     drawLine(g, x + tabWidth + tabLeading, y + height - tabHeight, x + tabWidth + tabLeading, y + height, thick);
/* 320 */     drawLine(g, x + tabWidth + tabLeading, y + height - thick, x + tabLeading + 1, y + height - thick, thick);
/* 321 */     drawLine(g, x + tabLeading, y + height, x + tabLeading, y + height - tabHeight, thick);
/* 322 */     drawLine(g, x + tabLeading + thick, y + height - tabHeight - thick, x + 1, y + height - tabHeight - thick, thick);
/*     */ 
/* 324 */     drawLine(g, x, y + height - tabHeight, x, y + 1, thick);
/*     */   }
/*     */ 
/*     */   public void setBounds(Rectangle r)
/*     */   {
/* 346 */     setBounds(r.x, r.y, r.width, r.height);
/*     */   }
/*     */ 
/*     */   public void setBounds(int x, int y, int width, int height)
/*     */   {
/* 361 */     if (isLightweight()) {
/* 362 */       if (getOutlineMode() == 0) {
/* 363 */         if ((this._ghost) && ((getRelativeContainer() != null) || (getParent() != null))) {
/* 364 */           Graphics g = (getRelativeContainer() != null ? getRelativeContainer() : getParent()).getGraphics();
/* 365 */           g.setXORMode(this._lineColor);
/* 366 */           paintOutline(g, true);
/* 367 */           super.setBounds(x, y, width % 2 == 0 ? width + 1 : width, height % 2 == 0 ? height + 1 : height);
/*     */         }
/*     */         else
/*     */         {
/* 373 */           super.setBounds(x, y, width % 2 == 0 ? width + 1 : width, height % 2 == 0 ? height + 1 : height);
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 379 */         Rectangle rectangle = new Rectangle(x, y, width % 2 == 0 ? width + 1 : width, height % 2 == 0 ? height + 1 : height);
/*     */ 
/* 382 */         if (getParent() != null) {
/* 383 */           Rectangle parentRectangle = getParent().getBounds();
/* 384 */           super.setBounds(x, y, width % 2 == 0 ? width + 1 : width, height % 2 == 0 ? height + 1 : height);
/*     */ 
/* 387 */           if ((getOutlineMode() == 1) && (parentRectangle.contains(rectangle))) {
/* 388 */             this._leftOutline.setVisible(false);
/* 389 */             this._rightOutline.setVisible(false);
/* 390 */             this._topOutline.setVisible(false);
/* 391 */             this._bottomOutline.setVisible(false);
/*     */           }
/*     */           else {
/* 394 */             super.setVisible(false);
/* 395 */             Point point = rectangle.getLocation();
/* 396 */             SwingUtilities.convertPointToScreen(point, getParent());
/* 397 */             arrangeOutline(rectangle, point);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/* 403 */     else if (getRelativeContainer() != null) {
/* 404 */       Point point = new Point(x, y);
/* 405 */       SwingUtilities.convertPointToScreen(point, getRelativeContainer());
/* 406 */       super.setBounds(point.x, point.y, width % 2 == 0 ? width + 1 : width, height % 2 == 0 ? height + 1 : height);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void arrangeOutline(Rectangle rectangle, Point point)
/*     */   {
/* 418 */     this._leftOutline.setBounds(point.x, point.y, this._thickness, rectangle.height - this._thickness);
/* 419 */     this._topOutline.setBounds(point.x + this._thickness, point.y, rectangle.width - this._thickness - 1, this._thickness);
/* 420 */     this._rightOutline.setBounds(point.x + rectangle.width - this._thickness - 1, point.y + this._thickness, this._thickness, rectangle.height - this._thickness);
/* 421 */     this._bottomOutline.setBounds(point.x, point.y + rectangle.height - this._thickness, rectangle.width - 1, this._thickness);
/* 422 */     if (!this._topOutline.isVisible()) {
/* 423 */       this._topOutline.setVisible(true);
/*     */     }
/* 425 */     if (!this._leftOutline.isVisible()) {
/* 426 */       this._leftOutline.setVisible(true);
/*     */     }
/* 428 */     if (!this._rightOutline.isVisible()) {
/* 429 */       this._rightOutline.setVisible(true);
/*     */     }
/* 431 */     if (!this._bottomOutline.isVisible())
/* 432 */       this._bottomOutline.setVisible(true);
/*     */   }
/*     */ 
/*     */   public int getTabHeight()
/*     */   {
/* 442 */     return this._tabHeight;
/*     */   }
/*     */ 
/*     */   public void setTabHeight(int tabHeight)
/*     */   {
/* 451 */     this._tabHeight = tabHeight;
/*     */   }
/*     */ 
/*     */   public boolean isTabDocking()
/*     */   {
/* 460 */     return this._tabDocking;
/*     */   }
/*     */ 
/*     */   public void setTabDocking(boolean tabDocking)
/*     */   {
/* 469 */     this._tabDocking = tabDocking;
/* 470 */     updateCursor();
/*     */   }
/*     */ 
/*     */   public int getTabSide()
/*     */   {
/* 479 */     return this._tabSide;
/*     */   }
/*     */ 
/*     */   public void setTabSide(int tabSide)
/*     */   {
/* 488 */     this._tabSide = tabSide;
/*     */   }
/*     */ 
/*     */   public boolean isFloating()
/*     */   {
/* 497 */     return this._floating;
/*     */   }
/*     */ 
/*     */   public void setFloating(boolean floating)
/*     */   {
/* 506 */     this._floating = floating;
/* 507 */     updateCursor();
/*     */   }
/*     */ 
/*     */   public Component getAttachedComponent()
/*     */   {
/* 516 */     return this._attachedComponent;
/*     */   }
/*     */ 
/*     */   public void setAttachedComponent(Component attachedComponent)
/*     */   {
/* 525 */     this._attachedComponent = attachedComponent;
/*     */   }
/*     */ 
/*     */   public int getAttachedSide()
/*     */   {
/* 534 */     return this._attachedSide;
/*     */   }
/*     */ 
/*     */   public void setAttachedSide(int attachedSide)
/*     */   {
/* 543 */     this._attachedSide = attachedSide;
/* 544 */     updateCursor();
/*     */   }
/*     */ 
/*     */   private void updateCursor() {
/* 548 */     if (getGlassPane() == null) {
/* 549 */       return;
/*     */     }
/*     */ 
/* 553 */     if ((!isVisible()) && (this._floating)) {
/* 554 */       getGlassPane().setCursor(JideCursors.getPredefinedCursor(23));
/* 555 */       return;
/*     */     }
/*     */ 
/* 558 */     getGlassPane().setCursor(Cursor.getDefaultCursor());
/*     */ 
/* 561 */     if (!this._changeCursor) {
/* 562 */       return;
/*     */     }
/*     */ 
/* 565 */     if (!isVisible()) {
/* 566 */       getGlassPane().setCursor(Cursor.getDefaultCursor());
/* 567 */       return;
/*     */     }
/*     */ 
/* 570 */     if ((isVisible()) && ((!this._allowDocking) || (this._floating))) {
/* 571 */       getGlassPane().setCursor(JideCursors.getPredefinedCursor(29));
/*     */     }
/* 573 */     else if ((isVisible()) && (this._tabDocking)) {
/* 574 */       getGlassPane().setCursor(JideCursors.getPredefinedCursor(28));
/*     */     }
/* 576 */     else if ((getAttachedComponent() instanceof JideSplitPaneDivider)) {
/* 577 */       if (((JideSplitPaneDivider)getAttachedComponent()).getJideSplitPane().getOrientation() == 1) {
/* 578 */         getGlassPane().setCursor(JideCursors.getPredefinedCursor(31));
/*     */       }
/*     */       else {
/* 581 */         getGlassPane().setCursor(JideCursors.getPredefinedCursor(30));
/*     */       }
/*     */     }
/*     */     else
/* 585 */       switch (this._attachedSide) {
/*     */       case 1:
/* 587 */         getGlassPane().setCursor(JideCursors.getPredefinedCursor(24));
/* 588 */         break;
/*     */       case 2:
/* 590 */         getGlassPane().setCursor(JideCursors.getPredefinedCursor(25));
/* 591 */         break;
/*     */       case 4:
/* 593 */         getGlassPane().setCursor(JideCursors.getPredefinedCursor(26));
/* 594 */         break;
/*     */       case 8:
/* 596 */         getGlassPane().setCursor(JideCursors.getPredefinedCursor(27));
/* 597 */         break;
/*     */       case 3:
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/*     */       default:
/* 599 */         getGlassPane().setCursor(Cursor.getDefaultCursor());
/*     */       }
/*     */   }
/*     */ 
/*     */   public boolean isSingle()
/*     */   {
/* 613 */     return this._single;
/*     */   }
/*     */ 
/*     */   public void setSingle(boolean single)
/*     */   {
/* 622 */     this._single = single;
/*     */   }
/*     */ 
/*     */   public boolean isAllowDocking()
/*     */   {
/* 631 */     return this._allowDocking;
/*     */   }
/*     */ 
/*     */   public void setAllowDocking(boolean allowDocking)
/*     */   {
/* 640 */     this._allowDocking = allowDocking;
/* 641 */     updateCursor();
/*     */   }
/*     */ 
/*     */   public Container getRelativeContainer() {
/* 645 */     return this._relativeContainer;
/*     */   }
/*     */ 
/*     */   public void setRelativeContainer(Container relativeContainer) {
/* 649 */     this._relativeContainer = relativeContainer;
/*     */   }
/*     */ 
/*     */   public int getSaveX()
/*     */   {
/* 658 */     return this._saveX;
/*     */   }
/*     */ 
/*     */   public int getSaveY()
/*     */   {
/* 667 */     return this._saveY;
/*     */   }
/*     */ 
/*     */   public int getSaveMouseModifier()
/*     */   {
/* 676 */     return this._saveMouseModifier;
/*     */   }
/*     */ 
/*     */   public JComponent getSaveDraggedComponent()
/*     */   {
/* 685 */     return this._saveDraggedComponent;
/*     */   }
/*     */ 
/*     */   public void setDraggingInformation(JComponent comp, int saveX, int saveY, int saveMouseModifier)
/*     */   {
/* 698 */     this._saveDraggedComponent = comp;
/* 699 */     this._saveX = saveX;
/* 700 */     this._saveY = saveY;
/* 701 */     this._saveMouseModifier = saveMouseModifier;
/*     */   }
/*     */ 
/*     */   public void cleanup() {
/* 705 */     if (getOutlineMode() != 0)
/*     */     {
/* 707 */       this._leftOutline.dispose();
/* 708 */       this._rightOutline.dispose();
/* 709 */       this._topOutline.dispose();
/* 710 */       this._bottomOutline.dispose();
/* 711 */       this._leftOutline = null;
/* 712 */       this._rightOutline = null;
/* 713 */       this._topOutline = null;
/* 714 */       this._bottomOutline = null;
/*     */     }
/* 716 */     if (getGlassPane() != null)
/* 717 */       getGlassPane().setCursor(Cursor.getDefaultCursor());
/*     */   }
/*     */ 
/*     */   public void setVisible(boolean aFlag)
/*     */   {
/* 731 */     super.setVisible(aFlag);
/* 732 */     updateCursor();
/* 733 */     if ((!aFlag) && (getOutlineMode() != 0)) {
/* 734 */       this._leftOutline.setVisible(false);
/* 735 */       this._rightOutline.setVisible(false);
/* 736 */       this._topOutline.setVisible(false);
/* 737 */       this._bottomOutline.setVisible(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isVisible()
/*     */   {
/* 752 */     if (super.isVisible()) {
/* 753 */       return true;
/*     */     }
/*     */ 
/* 757 */     return (getOutlineMode() != 0) && ((this._topOutline.isVisible()) || (this._bottomOutline.isVisible()) || (this._leftOutline.isVisible()) || (this._rightOutline.isVisible()));
/*     */   }
/*     */ 
/*     */   public int getOutlineMode()
/*     */   {
/* 802 */     return this._outlineMode;
/*     */   }
/*     */ 
/*     */   public void setOutlineMode(int outlineMode) {
/* 806 */     if ((outlineMode != 0) && (this._outlineMode == 0)) {
/* 807 */       initOutline();
/*     */     }
/* 809 */     this._outlineMode = outlineMode;
/*     */   }
/*     */ 
/*     */   public Component getGlassPane() {
/* 813 */     return this._glassPane;
/*     */   }
/*     */ 
/*     */   public void setGlassPane(Component glassPane) {
/* 817 */     this._glassPane = glassPane;
/*     */   }
/*     */ 
/*     */   public boolean isChangeCursor() {
/* 821 */     return this._changeCursor;
/*     */   }
/*     */ 
/*     */   public void setChangeCursor(boolean changeCursor) {
/* 825 */     this._changeCursor = changeCursor;
/*     */   }
/*     */ 
/*     */   class Outline extends JWindow
/*     */   {
/*     */     public Outline()
/*     */     {
/* 765 */       setVisible(false);
/* 766 */       setBackground(Contour.this._lineColor);
/*     */     }
/*     */ 
/*     */     public void paint(Graphics g)
/*     */     {
/* 771 */       g.setColor(Contour.this._lineColor);
/* 772 */       g.fillRect(0, 0, getWidth(), getHeight());
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.Contour
 * JD-Core Version:    0.6.0
 */
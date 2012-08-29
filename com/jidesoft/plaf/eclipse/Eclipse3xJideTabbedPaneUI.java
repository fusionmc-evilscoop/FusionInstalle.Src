/*      */ package com.jidesoft.plaf.eclipse;
/*      */ 
/*      */ import com.jidesoft.plaf.UIDefaultsLookup;
/*      */ import com.jidesoft.plaf.basic.BasicJideTabbedPaneUI.ScrollableTabPanel;
/*      */ import com.jidesoft.plaf.basic.BasicJideTabbedPaneUI.ScrollableTabSupport;
/*      */ import com.jidesoft.plaf.basic.BasicJideTabbedPaneUI.ScrollableTabViewport;
/*      */ import com.jidesoft.plaf.basic.BasicJideTabbedPaneUI.TabCloseButton;
/*      */ import com.jidesoft.plaf.basic.BasicJideTabbedPaneUI.TabbedPaneLayout;
/*      */ import com.jidesoft.plaf.basic.ThemePainter;
/*      */ import com.jidesoft.plaf.vsnet.VsnetJideTabbedPaneUI;
/*      */ import com.jidesoft.swing.JideSwingUtilities;
/*      */ import com.jidesoft.swing.JideTabbedPane;
/*      */ import com.jidesoft.swing.JideTabbedPane.NoFocusButton;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.ComponentOrientation;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Point;
/*      */ import java.awt.Polygon;
/*      */ import java.awt.Rectangle;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.LookAndFeel;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.basic.BasicGraphicsUtils;
/*      */ import javax.swing.text.View;
/*      */ 
/*      */ public class Eclipse3xJideTabbedPaneUI extends VsnetJideTabbedPaneUI
/*      */ {
/*      */   protected int _closeButtonMargin;
/*      */   protected int _closeButtonMarginSize;
/*      */   protected int _iconMarginHorizon;
/*      */   protected int _iconMarginVertical;
/*      */ 
/*      */   public static ComponentUI createUI(JComponent c)
/*      */   {
/*   35 */     return new Eclipse3xJideTabbedPaneUI();
/*      */   }
/*      */ 
/*      */   public void installUI(JComponent c)
/*      */   {
/*   40 */     super.installUI(c);
/*   41 */     this._rectSizeExtend = 12;
/*      */   }
/*      */ 
/*      */   protected void installDefaults()
/*      */   {
/*   46 */     super.installDefaults();
/*      */ 
/*   48 */     LookAndFeel.installBorder(this._tabPane, "JideTabbedPane.border");
/*   49 */     this._closeButtonMargin = UIDefaultsLookup.getInt("JideTabbedPane.closeButtonMargin");
/*   50 */     this._closeButtonMarginSize = UIDefaultsLookup.getInt("JideTabbedPane.closeButtonMarginSize");
/*   51 */     this._iconMarginHorizon = UIDefaultsLookup.getInt("JideTabbedPane.iconMarginHorizon");
/*   52 */     this._iconMarginVertical = UIDefaultsLookup.getInt("JideTabbedPane.iconMarginVertical");
/*      */   }
/*      */ 
/*      */   public void paintBackground(Graphics g, Component c)
/*      */   {
/*   57 */     if (getTabShape() == 7) {
/*   58 */       if (this._tabPane.isOpaque()) {
/*   59 */         int width = c.getWidth();
/*   60 */         int height = c.getHeight();
/*      */ 
/*   62 */         int temp1 = -1;
/*   63 */         int temp2 = -1;
/*   64 */         if (isTabLeadingComponentVisible()) {
/*   65 */           if (height < this._tabLeadingComponent.getSize().height) {
/*   66 */             height = this._tabLeadingComponent.getSize().height;
/*   67 */             temp1 = this._tabLeadingComponent.getSize().height;
/*      */           }
/*   69 */           if (width < this._tabLeadingComponent.getSize().width) {
/*   70 */             width = this._tabLeadingComponent.getSize().width;
/*   71 */             temp2 = this._tabLeadingComponent.getSize().width;
/*      */           }
/*      */         }
/*      */ 
/*   75 */         if (isTabTrailingComponentVisible()) {
/*   76 */           if ((height < this._tabTrailingComponent.getSize().height) && (temp1 < this._tabTrailingComponent.getSize().height)) {
/*   77 */             height = this._tabTrailingComponent.getSize().height;
/*      */           }
/*   79 */           if ((width < this._tabTrailingComponent.getSize().width) && (temp2 < this._tabTrailingComponent.getSize().width)) {
/*   80 */             width = this._tabTrailingComponent.getSize().width;
/*      */           }
/*      */         }
/*      */ 
/*   84 */         g.setColor(this._background);
/*   85 */         g.fillRect(0, 0, width, height);
/*      */       }
/*      */     }
/*      */     else
/*   89 */       super.paintBackground(g, c);
/*      */   }
/*      */ 
/*      */   protected void ensureCurrentLayout()
/*      */   {
/*  100 */     if (!this._tabPane.isValid()) {
/*  101 */       BasicJideTabbedPaneUI.TabbedPaneLayout layout = (BasicJideTabbedPaneUI.TabbedPaneLayout)this._tabPane.getLayout();
/*  102 */       layout.calculateLayoutInfo();
/*      */     }
/*      */ 
/*  107 */     if ((scrollableTabLayoutEnabled()) && (isShowCloseButton()) && (isShowCloseButtonOnTab()))
/*      */     {
/*  109 */       for (int i = 0; i < this._closeButtons.length; i++) {
/*  110 */         if (this._tabPane.isShowCloseButtonOnSelectedTab()) {
/*  111 */           if (i != this._tabPane.getSelectedIndex()) {
/*  112 */             this._closeButtons[i].setBounds(0, 0, 0, 0);
/*  113 */             continue;
/*      */           }
/*      */ 
/*      */         }
/*  117 */         else if (i >= this._rects.length) {
/*  118 */           this._closeButtons[i].setBounds(0, 0, 0, 0);
/*  119 */           continue;
/*      */         }
/*      */ 
/*  123 */         if (!this._tabPane.isTabClosableAt(i)) {
/*  124 */           this._closeButtons[i].setBounds(0, 0, 0, 0);
/*      */         }
/*      */         else {
/*  127 */           Dimension size = this._closeButtons[i].getPreferredSize();
/*      */           Rectangle bounds;
/*      */           Rectangle bounds;
/*  130 */           if (this._closeButtonAlignment == 11)
/*      */           {
/*      */             Rectangle bounds;
/*  131 */             if ((this._tabPane.getTabPlacement() == 1) || (this._tabPane.getTabPlacement() == 3))
/*      */             {
/*      */               Rectangle bounds;
/*  132 */               if (this._tabPane.getComponentOrientation().isLeftToRight()) {
/*  133 */                 bounds = new Rectangle(this._rects[i].x + this._rects[i].width - size.width - 16, this._rects[i].y + (this._rects[i].height - size.height >> 1), size.width, size.height);
/*      */               }
/*      */               else
/*  136 */                 bounds = new Rectangle(this._rects[i].x + 4, this._rects[i].height - size.height >> 1, size.width, size.height);
/*      */             }
/*      */             else
/*      */             {
/*      */               Rectangle bounds;
/*  139 */               if (this._tabPane.getTabPlacement() == 2) {
/*  140 */                 bounds = new Rectangle(this._rects[i].x + (this._rects[i].width - size.width >> 1), this._rects[i].y + this._rects[i].height - size.height - 16, size.width, size.height);
/*      */               }
/*      */               else
/*  143 */                 bounds = new Rectangle(this._rects[i].x + (this._rects[i].width - size.width >> 1), this._rects[i].y + this._rects[i].height - size.height - 16, size.width, size.height);
/*      */             }
/*      */           }
/*      */           else {
/*  147 */             bounds = new Rectangle(this._rects[i].x + 4, this._rects[i].height - size.height >> 1, size.width, size.height);
/*  148 */             if ((!this._tabPane.getComponentOrientation().isLeftToRight()) && ((this._tabPane.getTabPlacement() == 1) || (this._tabPane.getTabPlacement() == 3))) {
/*  149 */               bounds = new Rectangle(this._rects[i].x + this._rects[i].width - size.width - 16, this._rects[i].y + (this._rects[i].height - size.height >> 1), size.width, size.height);
/*      */             }
/*      */           }
/*  152 */           if ((this._closeButtons[i] instanceof JideTabbedPane.NoFocusButton)) {
/*  153 */             ((JideTabbedPane.NoFocusButton)this._closeButtons[i]).setIndex(i);
/*      */           }
/*  155 */           if (!bounds.equals(this._closeButtons[i].getBounds())) {
/*  156 */             this._closeButtons[i].setBounds(bounds);
/*      */           }
/*  158 */           if (this._tabPane.getSelectedIndex() == i) {
/*  159 */             this._closeButtons[i].setBackground(this._selectedColor == null ? this._tabPane.getBackgroundAt(i) : this._selectedColor);
/*      */           }
/*      */           else
/*  162 */             this._closeButtons[i].setBackground(this._tabPane.getBackgroundAt(i));
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex, Component c)
/*      */   {
/*  187 */     if (getTabShape() == 7) {
/*  188 */       int tabCount = this._tabPane.getTabCount();
/*      */ 
/*  190 */       Rectangle iconRect = new Rectangle(); Rectangle textRect = new Rectangle();
/*  191 */       Rectangle clipRect = g.getClipBounds();
/*  192 */       Rectangle viewRect = this._tabScroller.viewport.getViewRect();
/*      */ 
/*  194 */       if (this._tabPane.isOpaque()) {
/*  195 */         g.setColor(this._tabBackground);
/*  196 */         g.fillRect(clipRect.x, clipRect.y, clipRect.width, clipRect.height);
/*      */       }
/*      */ 
/*  200 */       for (int i = this._runCount - 1; i >= 0; i--) {
/*  201 */         int start = this._tabRuns[i];
/*  202 */         int next = this._tabRuns[(i + 1)];
/*  203 */         int end = next != 0 ? next - 1 : tabCount - 1;
/*  204 */         for (int j = start; j <= end; j++) {
/*  205 */           if (this._rects[j].intersects(clipRect)) {
/*  206 */             paintTab(g, tabPlacement, this._rects, j, iconRect, textRect);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  213 */       if ((selectedIndex >= 0) && (getRunForTab(tabCount, selectedIndex) == 0) && 
/*  214 */         (this._rects[selectedIndex].intersects(clipRect))) {
/*  215 */         paintTab(g, tabPlacement, this._rects, selectedIndex, iconRect, textRect);
/*      */       }
/*      */ 
/*  220 */       if (this._tabPane.isOpaque()) {
/*  221 */         g.setColor(this._shadow);
/*  222 */         switch (tabPlacement) {
/*      */         case 2:
/*  224 */           if (!isTabLeadingComponentVisible()) {
/*  225 */             g.fillRect(viewRect.x, viewRect.y + 3, 1, 2);
/*  226 */             g.fillRect(viewRect.x + 1, viewRect.y + 2, 1, 1);
/*  227 */             g.fillRect(viewRect.x + 2, viewRect.y + 1, 1, 1);
/*  228 */             g.fillRect(viewRect.x + 3, viewRect.y, 2, 1);
/*      */           }
/*      */ 
/*  231 */           if (isNoneTabTrailingComponentVisible()) {
/*  232 */             g.fillRect(viewRect.x + 3, viewRect.y + viewRect.height - 1, 2, 1);
/*  233 */             g.fillRect(viewRect.x + 2, viewRect.y + viewRect.height - 2, 1, 1);
/*  234 */             g.fillRect(viewRect.x + 1, viewRect.y + viewRect.height - 3, 1, 1);
/*  235 */             g.fillRect(viewRect.x, viewRect.y + viewRect.height - 5, 1, 2);
/*      */           }
/*      */ 
/*  238 */           g.setColor(this._tabBackground);
/*  239 */           g.fillRect(viewRect.x, viewRect.y, 3, 1);
/*  240 */           g.fillRect(viewRect.x, viewRect.y + 1, 2, 1);
/*  241 */           g.fillRect(viewRect.x, viewRect.y + 2, 1, 1);
/*  242 */           break;
/*      */         case 4:
/*  244 */           if (!isTabLeadingComponentVisible()) {
/*  245 */             g.fillRect(viewRect.x + viewRect.width - 5, viewRect.y, 2, 1);
/*  246 */             g.fillRect(viewRect.x + viewRect.width - 3, viewRect.y + 1, 1, 1);
/*  247 */             g.fillRect(viewRect.x + viewRect.width - 2, viewRect.y + 2, 1, 1);
/*  248 */             g.fillRect(viewRect.x + viewRect.width - 1, viewRect.y + 3, 1, 2);
/*      */           }
/*      */ 
/*  251 */           if (isNoneTabTrailingComponentVisible()) {
/*  252 */             g.fillRect(viewRect.x + viewRect.width - 5, viewRect.y + viewRect.height - 1, 2, 1);
/*  253 */             g.fillRect(viewRect.x + viewRect.width - 3, viewRect.y + viewRect.height - 2, 1, 1);
/*  254 */             g.fillRect(viewRect.x + viewRect.width - 2, viewRect.y + viewRect.height - 3, 1, 1);
/*  255 */             g.fillRect(viewRect.x + viewRect.width - 1, viewRect.y + viewRect.height - 5, 1, 2);
/*      */           }
/*      */ 
/*  258 */           g.setColor(this._tabBackground);
/*  259 */           g.fillRect(viewRect.x + viewRect.width - 3, viewRect.y, 3, 1);
/*  260 */           g.fillRect(viewRect.x + viewRect.width - 2, viewRect.y + 1, 2, 1);
/*  261 */           g.fillRect(viewRect.x + viewRect.width - 1, viewRect.y + 2, 1, 1);
/*  262 */           break;
/*      */         case 3:
/*  264 */           if (!isTabLeadingComponentVisible()) {
/*  265 */             g.fillRect(viewRect.x + 3, viewRect.y + viewRect.height - 1, 2, 1);
/*  266 */             g.fillRect(viewRect.x + 2, viewRect.y + viewRect.height - 2, 1, 1);
/*  267 */             g.fillRect(viewRect.x + 1, viewRect.y + viewRect.height - 3, 1, 1);
/*  268 */             g.fillRect(viewRect.x, viewRect.y + viewRect.height - 5, 1, 2);
/*      */           }
/*      */ 
/*  271 */           if (isNoneTabTrailingComponentVisible()) {
/*  272 */             g.fillRect(viewRect.x + viewRect.width - 5, viewRect.y + viewRect.height - 1, 2, 1);
/*  273 */             g.fillRect(viewRect.x + viewRect.width - 3, viewRect.y + viewRect.height - 2, 1, 1);
/*  274 */             g.fillRect(viewRect.x + viewRect.width - 2, viewRect.y + viewRect.height - 3, 1, 1);
/*  275 */             g.fillRect(viewRect.x + viewRect.width - 1, viewRect.y + viewRect.height - 5, 1, 2);
/*      */           }
/*      */ 
/*  278 */           g.setColor(this._tabBackground);
/*  279 */           g.fillRect(viewRect.x, viewRect.y + viewRect.height - 1, 3, 1);
/*  280 */           g.fillRect(viewRect.x, viewRect.y + viewRect.height - 2, 2, 1);
/*  281 */           g.fillRect(viewRect.x, viewRect.y + viewRect.height - 3, 1, 1);
/*  282 */           break;
/*      */         case 1:
/*      */         default:
/*  285 */           if (!isTabLeadingComponentVisible()) {
/*  286 */             g.fillRect(viewRect.x + 3, viewRect.y, 2, 1);
/*  287 */             g.fillRect(viewRect.x + 2, viewRect.y + 1, 1, 1);
/*  288 */             g.fillRect(viewRect.x + 1, viewRect.y + 2, 1, 1);
/*  289 */             g.fillRect(viewRect.x, viewRect.y + 3, 1, 2);
/*      */           }
/*      */ 
/*  292 */           if (isNoneTabTrailingComponentVisible()) {
/*  293 */             g.fillRect(viewRect.x + viewRect.width - 5, viewRect.y, 2, 1);
/*  294 */             g.fillRect(viewRect.x + viewRect.width - 3, viewRect.y + 1, 1, 1);
/*  295 */             g.fillRect(viewRect.x + viewRect.width - 2, viewRect.y + 2, 1, 1);
/*  296 */             g.fillRect(viewRect.x + viewRect.width - 1, viewRect.y + 3, 1, 2);
/*      */           }
/*      */ 
/*  299 */           g.setColor(this._tabBackground);
/*  300 */           g.fillRect(viewRect.x, viewRect.y, 3, 1);
/*  301 */           g.fillRect(viewRect.x, viewRect.y + 1, 2, 1);
/*  302 */           g.fillRect(viewRect.x, viewRect.y + 2, 1, 1);
/*      */         }
/*      */       }
/*      */     }
/*      */     else {
/*  307 */       super.paintTabArea(g, tabPlacement, selectedIndex, c);
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean isNoneTabTrailingComponentVisible() {
/*  312 */     return (!this._tabScroller.scrollForwardButton.isVisible()) && (!this._tabScroller.scrollBackwardButton.isVisible()) && (!this._tabScroller.closeButton.isVisible()) && (!isTabTrailingComponentVisible());
/*      */   }
/*      */ 
/*      */   protected void layoutLabel(int tabPlacement, FontMetrics metrics, int tabIndex, String title, Icon icon, Rectangle tabRect, Rectangle iconRect, Rectangle textRect, boolean isSelected)
/*      */   {
/*  320 */     textRect.x = (textRect.y = iconRect.x = iconRect.y = 0);
/*      */ 
/*  322 */     View v = getTextViewForTab(tabIndex);
/*  323 */     if (v != null) {
/*  324 */       this._tabPane.putClientProperty("html", v);
/*      */     }
/*      */ 
/*  327 */     SwingUtilities.layoutCompoundLabel(this._tabPane, metrics, title, icon, 0, 0, 0, 11, tabRect, iconRect, textRect, this._textIconGap);
/*      */ 
/*  332 */     this._tabPane.putClientProperty("html", null);
/*      */ 
/*  334 */     if ((tabPlacement == 1) || (tabPlacement == 3)) {
/*  335 */       tabRect.x += this._iconMarginHorizon;
/*  336 */       textRect.x = (icon != null ? iconRect.x + iconRect.width + this._textIconGap : tabRect.x + this._textPadding);
/*  337 */       iconRect.width = Math.min(iconRect.width, tabRect.width - this._tabRectPadding);
/*  338 */       textRect.width = (tabRect.width - this._tabRectPadding - iconRect.width - (icon != null ? this._textIconGap : this._noIconMargin));
/*      */ 
/*  340 */       if ((getTabResizeMode() == 2) || (this._tabPane.getTabResizeMode() == 3)) {
/*  341 */         textRect.width -= 10;
/*  342 */         if ((isShowCloseButton()) && (isShowCloseButtonOnTab())) {
/*  343 */           if (this._tabPane.isShowCloseButtonOnSelectedTab()) {
/*  344 */             if (isSelected) {
/*  345 */               textRect.width -= this._closeButtons[tabIndex].getPreferredSize().width;
/*      */             }
/*      */           }
/*      */           else {
/*  349 */             textRect.width -= this._closeButtons[tabIndex].getPreferredSize().width;
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*  354 */       else if ((getTabResizeMode() == 4) && (isShowCloseButton()) && (isShowCloseButtonOnTab()) && 
/*  355 */         (!this._tabPane.isShowCloseButtonOnSelectedTab()) && 
/*  356 */         (!isSelected)) {
/*  357 */         iconRect.width = (iconRect.width + this._closeButtons[tabIndex].getPreferredSize().width + this._closeButtonMarginSize);
/*      */ 
/*  360 */         textRect.width = 0;
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  366 */       tabRect.y += this._iconMarginVertical;
/*  367 */       textRect.y = (icon != null ? iconRect.y + iconRect.height + this._textIconGap : tabRect.y + this._textPadding);
/*      */ 
/*  369 */       tabRect.x += 3;
/*  370 */       tabRect.x += 3;
/*  371 */       tabRect.width -= this._textMarginVertical;
/*  372 */       textRect.height = (tabRect.height - this._tabRectPadding - iconRect.height - (icon != null ? this._textIconGap : this._noIconMargin));
/*      */ 
/*  374 */       if ((getTabResizeMode() == 2) || (this._tabPane.getTabResizeMode() == 3))
/*      */       {
/*  376 */         textRect.height -= 10;
/*  377 */         if ((isShowCloseButton()) && (isShowCloseButtonOnTab())) {
/*  378 */           if (this._tabPane.isShowCloseButtonOnSelectedTab()) {
/*  379 */             if (isSelected) {
/*  380 */               textRect.height -= this._closeButtons[tabIndex].getPreferredSize().height;
/*      */             }
/*      */           }
/*      */           else {
/*  384 */             textRect.height -= this._closeButtons[tabIndex].getPreferredSize().height;
/*      */           }
/*      */         }
/*      */       }
/*  388 */       else if ((getTabResizeMode() == 4) && (isShowCloseButton()) && (isShowCloseButtonOnTab()))
/*      */       {
/*  390 */         if ((!this._tabPane.isShowCloseButtonOnSelectedTab()) && 
/*  391 */           (!isSelected)) {
/*  392 */           iconRect.height = (iconRect.height + this._closeButtons[tabIndex].getPreferredSize().height + this._closeButtonMarginSize);
/*  393 */           textRect.height = 0;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */   {
/*  414 */     if (getTabShape() == 7) {
/*  415 */       g.setColor(this._lightHighlight);
/*  416 */       boolean leftToRight = this._tabPane.getComponentOrientation().isLeftToRight();
/*  417 */       switch (tabPlacement) {
/*      */       case 2:
/*  419 */         if (!isTabLeadingComponentVisible()) {
/*  420 */           y--;
/*      */         }
/*  422 */         if (isSelected) {
/*  423 */           g.setColor(this._shadow);
/*  424 */           g.drawLine(x + 5, y, x + w - 1, y);
/*      */ 
/*  427 */           g.drawLine(x + 4, y + 1, x + 3, y + 1);
/*  428 */           g.drawLine(x + 2, y + 2, x + 2, y + 2);
/*  429 */           g.drawLine(x + 1, y + 3, x + 1, y + 3);
/*  430 */           g.drawLine(x, y + 4, x, y + 5);
/*      */ 
/*  432 */           if (isTabTopVisible(tabPlacement)) {
/*  433 */             g.drawLine(x, y + 5, x, y + h - 21);
/*      */           }
/*      */ 
/*  437 */           g.drawLine(x, y + h - 21, x, y + h - 19);
/*  438 */           g.drawLine(x + 1, y + h - 18, x + 1, y + h - 16);
/*  439 */           g.drawLine(x + 2, y + h - 15, x + 2, y + h - 14);
/*  440 */           g.drawLine(x + 3, y + h - 13, x + 3, y + h - 13);
/*  441 */           g.drawLine(x + 4, y + h - 12, x + 4, y + h - 11);
/*      */ 
/*  444 */           for (int i = 0; i < w - 10; i++) {
/*  445 */             g.drawLine(x + 5 + i, y + h - 10 + i, x + 5 + i, y + h - 10 + i);
/*      */           }
/*      */ 
/*  449 */           g.drawLine(x + w - 5, y + h + w - 20, x + w - 5, y + h + w - 19);
/*  450 */           g.drawLine(x + w - 4, y + h + w - 18, x + w - 4, y + h + w - 18);
/*  451 */           g.drawLine(x + w - 3, y + h + w - 17, x + w - 3, y + h + w - 16);
/*  452 */           g.drawLine(x + w - 2, y + h + w - 15, x + w - 2, y + h + w - 13);
/*  453 */           g.drawLine(x + w - 1, y + h + w - 12, x + w - 1, y + h + w - 10);
/*      */ 
/*  455 */           if (isTabLeadingComponentVisible()) break;
/*  456 */           y++;
/*      */         }
/*  461 */         else if (tabIndex > this._tabPane.getSelectedIndex()) {
/*  462 */           g.setColor(this._shadow);
/*  463 */           g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */         }
/*      */         else
/*      */         {
/*  468 */           if ((tabIndex >= this._tabPane.getSelectedIndex()) || (tabIndex == 0)) {
/*      */             break;
/*      */           }
/*  471 */           g.setColor(this._shadow);
/*  472 */           g.drawLine(x, y - 1, x + w - 1, y - 1);
/*      */         }
/*  474 */         break;
/*      */       case 4:
/*  476 */         if (!isTabLeadingComponentVisible()) {
/*  477 */           y--;
/*      */         }
/*  479 */         if (isSelected) {
/*  480 */           g.setColor(this._shadow);
/*      */ 
/*  482 */           g.drawLine(x, y, x + w - 6, y);
/*      */ 
/*  485 */           g.drawLine(x + w - 5, y + 1, x + w - 4, y + 1);
/*  486 */           g.drawLine(x + w - 3, y + 2, x + w - 3, y + 2);
/*  487 */           g.drawLine(x + w - 2, y + 3, x + w - 2, y + 3);
/*  488 */           g.drawLine(x + w - 1, y + 4, x + w - 1, y + 5);
/*      */ 
/*  490 */           if (isTabTopVisible(tabPlacement)) {
/*  491 */             g.drawLine(x + w - 1, y + 5, x + w - 1, y + h - 21);
/*      */           }
/*      */ 
/*  495 */           g.drawLine(x + w - 1, y + h - 21, x + w - 1, y + h - 19);
/*  496 */           g.drawLine(x + w - 2, y + h - 18, x + w - 2, y + h - 16);
/*  497 */           g.drawLine(x + w - 3, y + h - 15, x + w - 3, y + h - 14);
/*  498 */           g.drawLine(x + w - 4, y + h - 13, x + w - 4, y + h - 13);
/*  499 */           g.drawLine(x + w - 5, y + h - 12, x + w - 5, y + h - 11);
/*      */ 
/*  502 */           for (int i = 0; i < w - 10; i++) {
/*  503 */             g.drawLine(x + w - 6 - i, y + h - 10 + i, x + w - 6 - i, y + h - 10 + i);
/*      */           }
/*      */ 
/*  507 */           g.drawLine(x + 4, y + h + w - 20, x + 4, y + h + w - 19);
/*  508 */           g.drawLine(x + 3, y + h + w - 18, x + 3, y + h + w - 18);
/*  509 */           g.drawLine(x + 2, y + h + w - 17, x + 2, y + h + w - 16);
/*  510 */           g.drawLine(x + 1, y + h + w - 15, x + 1, y + h + w - 13);
/*  511 */           g.drawLine(x, y + h + w - 12, x, y + h + w - 10);
/*      */         }
/*  517 */         else if (tabIndex > this._tabPane.getSelectedIndex()) {
/*  518 */           g.setColor(this._shadow);
/*  519 */           g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */         }
/*      */         else
/*      */         {
/*  524 */           if ((tabIndex >= this._tabPane.getSelectedIndex()) || (tabIndex == 0)) {
/*      */             break;
/*      */           }
/*  527 */           g.setColor(this._shadow);
/*  528 */           g.drawLine(x, y - 1, x + w - 1, y - 1);
/*      */         }
/*  530 */         break;
/*      */       case 3:
/*  532 */         if (!isTabLeadingComponentVisible()) {
/*  533 */           x--;
/*      */         }
/*  535 */         if (isSelected)
/*      */         {
/*  537 */           g.setColor(this._shadow);
/*      */ 
/*  539 */           g.drawLine(x, y + h - 6, x, y);
/*      */ 
/*  542 */           g.drawLine(x + 1, y + h - 5, x + 1, y + h - 4);
/*  543 */           g.drawLine(x + 2, y + h - 3, x + 2, y + h - 3);
/*  544 */           g.drawLine(x + 3, y + h - 2, x + 3, y + h - 2);
/*  545 */           g.drawLine(x + 4, y + h - 1, x + 5, y + h - 1);
/*      */ 
/*  547 */           if (isTabTopVisible(tabPlacement)) {
/*  548 */             g.drawLine(x + 5, y + h - 1, x + w - 20, y + h - 1);
/*      */           }
/*      */ 
/*  552 */           g.drawLine(x + w - 20, y + h - 1, x + w - 18, y + h - 1);
/*  553 */           g.drawLine(x + w - 17, y + h - 2, x + w - 15, y + h - 2);
/*  554 */           g.drawLine(x + w - 14, y + h - 3, x + w - 13, y + h - 3);
/*  555 */           g.drawLine(x + w - 12, y + h - 4, x + w - 12, y + h - 4);
/*  556 */           g.drawLine(x + w - 11, y + h - 5, x + w - 10, y + h - 5);
/*      */ 
/*  559 */           for (int i = 0; i < h - 10; i++) {
/*  560 */             g.drawLine(x + w - 9 + i, y + h - 6 - i, x + w - 9 + i, y + h - 6 - i);
/*      */           }
/*      */ 
/*  564 */           g.drawLine(x + w + h - 19, y + 4, x + w + h - 18, y + 4);
/*  565 */           g.drawLine(x + w + h - 17, y + 3, x + w + h - 17, y + 3);
/*  566 */           g.drawLine(x + w + h - 16, y + 2, x + w + h - 15, y + 2);
/*  567 */           g.drawLine(x + w + h - 14, y + 1, x + w + h - 12, y + 1);
/*  568 */           g.drawLine(x + w + h - 11, y, x + w + h - 9, y);
/*      */         }
/*  573 */         else if (tabIndex > this._tabPane.getSelectedIndex()) {
/*  574 */           g.setColor(this._shadow);
/*  575 */           if (leftToRight) {
/*  576 */             g.drawLine(x + w - 2, y - 1, x + w - 2, y + h);
/*      */           }
/*      */           else {
/*  579 */             g.drawLine(x, y - 1, x, y + h);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  584 */           if ((tabIndex >= this._tabPane.getSelectedIndex()) || (tabIndex == 0)) {
/*      */             break;
/*      */           }
/*  587 */           g.setColor(this._shadow);
/*  588 */           if (leftToRight) {
/*  589 */             g.drawLine(x, y - 1, x, y + h);
/*      */           }
/*      */           else
/*  592 */             g.drawLine(x + w - 2, y - 1, x + w - 2, y + h);
/*      */         }
/*  594 */         break;
/*      */       case 1:
/*      */       default:
/*  597 */         if (!isTabLeadingComponentVisible()) {
/*  598 */           x--;
/*      */         }
/*  600 */         if (isSelected) {
/*  601 */           g.setColor(this._shadow);
/*      */ 
/*  603 */           g.drawLine(x, y + 5, x, y + h);
/*      */ 
/*  606 */           g.drawLine(x + 4, y, x + 5, y);
/*  607 */           g.drawLine(x + 3, y + 1, x + 3, y + 1);
/*  608 */           g.drawLine(x + 2, y + 2, x + 2, y + 2);
/*  609 */           g.drawLine(x + 1, y + 3, x + 1, y + 4);
/*      */ 
/*  611 */           if (isTabTopVisible(tabPlacement)) {
/*  612 */             g.drawLine(x + 5, y, x + w - 20, y);
/*      */           }
/*      */ 
/*  616 */           g.drawLine(x + w - 20, y, x + w - 18, y);
/*  617 */           g.drawLine(x + w - 17, y + 1, x + w - 15, y + 1);
/*  618 */           g.drawLine(x + w - 14, y + 2, x + w - 13, y + 2);
/*  619 */           g.drawLine(x + w - 12, y + 3, x + w - 12, y + 3);
/*  620 */           g.drawLine(x + w - 11, y + 4, x + w - 10, y + 4);
/*      */ 
/*  623 */           for (int i = 0; i < h - 10; i++) {
/*  624 */             g.drawLine(x + w - 9 + i, y + 5 + i, x + w - 9 + i, y + 5 + i);
/*      */           }
/*      */ 
/*  628 */           g.drawLine(x + w + h - 19, y + h - 5, x + w + h - 18, y + h - 5);
/*  629 */           g.drawLine(x + w + h - 17, y + h - 4, x + w + h - 17, y + h - 4);
/*  630 */           g.drawLine(x + w + h - 16, y + h - 3, x + w + h - 15, y + h - 3);
/*  631 */           g.drawLine(x + w + h - 14, y + h - 2, x + w + h - 12, y + h - 2);
/*  632 */           g.drawLine(x + w + h - 11, y + h - 1, x + w + h - 9, y + h - 1);
/*      */         }
/*  636 */         else if (tabIndex > this._tabPane.getSelectedIndex()) {
/*  637 */           g.setColor(this._shadow);
/*  638 */           if (leftToRight) {
/*  639 */             g.drawLine(x + w - 2, y, x + w - 2, y + (h - 1));
/*      */           }
/*      */           else {
/*  642 */             g.drawLine(x, y, x, y + (h - 1));
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  647 */           if ((tabIndex >= this._tabPane.getSelectedIndex()) || (tabIndex == 0))
/*      */             break;
/*  649 */           g.setColor(this._shadow);
/*      */ 
/*  651 */           if (leftToRight) {
/*  652 */             g.drawLine(x, y, x, y + (h - 1));
/*      */           }
/*      */           else
/*  655 */             g.drawLine(x + w - 2, y, x + w - 2, y + (h - 1));
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  661 */       super.paintTabBorder(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */   {
/*  672 */     if (!isSelected) {
/*  673 */       return;
/*      */     }
/*      */ 
/*  676 */     if (getTabShape() == 7) {
/*  677 */       Graphics2D g2d = (Graphics2D)g;
/*      */       Color background2;
/*      */       Color background1;
/*      */       Color background2;
/*  680 */       if (showFocusIndicator()) {
/*  681 */         Color background1 = UIDefaultsLookup.getColor("DockableFrame.activeTitleBackground");
/*  682 */         background2 = UIDefaultsLookup.getColor("DockableFrame.activeTitleBackground2");
/*      */       }
/*      */       else {
/*  685 */         background1 = UIDefaultsLookup.getColor("DockableFrame.activeTitleBackground");
/*  686 */         background2 = this._background;
/*      */       }
/*      */ 
/*  689 */       switch (tabPlacement) {
/*      */       case 2:
/*  691 */         if (!isTabLeadingComponentVisible())
/*  692 */           y--;
/*  693 */         int[] xp = { x + w, x + 5, x, x, x + 6, x + w - 6, x + w };
/*  694 */         int[] yp = { y, y, y + 4, y + h - 19, y + h - 8, y + h + w - 21, y + h + w - 10 };
/*      */ 
/*  696 */         int np = yp.length;
/*  697 */         Polygon p = new Polygon(xp, yp, np);
/*  698 */         JideSwingUtilities.fillGradient(g2d, p, background1, background2, false);
/*      */ 
/*  700 */         break;
/*      */       case 4:
/*  702 */         if (!isTabLeadingComponentVisible())
/*  703 */           y--;
/*  704 */         int[] xp = { x, x + w - 6, x + w, x + w, x + w - 5, x + 5, x };
/*  705 */         int[] yp = { y, y, y + 4, y + h - 21, y + h - 10, y + h + w - 21, y + h + w - 10 };
/*      */ 
/*  707 */         int np = yp.length;
/*  708 */         Polygon p = new Polygon(xp, yp, np);
/*  709 */         JideSwingUtilities.fillGradient(g2d, p, background2, background1, false);
/*      */ 
/*  711 */         break;
/*      */       case 3:
/*  713 */         if (!isTabLeadingComponentVisible()) {
/*  714 */           x--;
/*      */         }
/*  716 */         int[] xp = { x, x, x + 6, x + w - 20, x + w - 16, x + w - 14, x + w - 12, x + w - 9, x + w + h - 19, x + w + h - 10, x + w + h - 12 };
/*      */ 
/*  719 */         int[] yp = { y, y + h - 6, y + h, y + h, y + h - 2, y + h - 3, y + h - 4, y + h - 6, y + 4, y + 1, y };
/*      */ 
/*  721 */         int np = yp.length;
/*  722 */         Polygon p = new Polygon(xp, yp, np);
/*  723 */         JideSwingUtilities.fillGradient(g2d, p, background2, background1, true);
/*      */ 
/*  725 */         break;
/*      */       case 1:
/*      */       default:
/*  728 */         if (!isTabLeadingComponentVisible())
/*  729 */           x--;
/*  730 */         int[] xp = { x, x, x + 2, x + 3, x + 6, x + w - 20, x + w - 14, x + w - 12, x + w - 9, x + w + h - 20, x + w + h - 9 };
/*      */ 
/*  732 */         int[] yp = { y + h, y + 5, y + 2, y + 1, y, y, y + 2, y + 3, y + 5, y + h - 6, y + h };
/*      */ 
/*  734 */         int np = xp.length;
/*  735 */         Polygon p = new Polygon(xp, yp, np);
/*  736 */         JideSwingUtilities.fillGradient(g2d, p, background1, background2, true);
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  742 */       super.paintTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex)
/*      */   {
/*  749 */     if (selectedIndex < 0) {
/*  750 */       return;
/*      */     }
/*      */ 
/*  753 */     if (getTabShape() == 7) {
/*  754 */       int width = this._tabPane.getWidth();
/*  755 */       int height = this._tabPane.getHeight();
/*  756 */       Insets insets = this._tabPane.getInsets();
/*      */ 
/*  758 */       int x = insets.left;
/*  759 */       int y = insets.top;
/*  760 */       int w = width - insets.right - insets.left;
/*  761 */       int h = height - insets.top - insets.bottom;
/*      */ 
/*  763 */       int temp = -1;
/*  764 */       switch (tabPlacement) {
/*      */       case 2:
/*  766 */         x += calculateTabAreaWidth(tabPlacement, this._runCount, this._maxTabWidth);
/*  767 */         if ((isTabLeadingComponentVisible()) && 
/*  768 */           (this._tabLeadingComponent.getSize().width > calculateTabAreaWidth(tabPlacement, this._runCount, this._maxTabWidth))) {
/*  769 */           x = insets.left + this._tabLeadingComponent.getSize().width;
/*  770 */           temp = this._tabLeadingComponent.getSize().width;
/*      */         }
/*      */ 
/*  773 */         if ((isTabTrailingComponentVisible()) && 
/*  774 */           (this._maxTabWidth < this._tabTrailingComponent.getSize().width) && (temp < this._tabTrailingComponent.getSize().width))
/*      */         {
/*  776 */           x = insets.left + this._tabTrailingComponent.getSize().width;
/*      */         }
/*      */ 
/*  779 */         w -= x - insets.left;
/*  780 */         break;
/*      */       case 4:
/*  782 */         w -= calculateTabAreaWidth(tabPlacement, this._runCount, this._maxTabWidth);
/*  783 */         break;
/*      */       case 3:
/*  785 */         h -= calculateTabAreaHeight(tabPlacement, this._runCount, this._maxTabHeight);
/*  786 */         break;
/*      */       case 1:
/*      */       default:
/*  789 */         y += calculateTabAreaHeight(tabPlacement, this._runCount, this._maxTabHeight);
/*  790 */         if ((isTabLeadingComponentVisible()) && 
/*  791 */           (this._tabLeadingComponent.getSize().height > calculateTabAreaHeight(tabPlacement, this._runCount, this._maxTabHeight))) {
/*  792 */           y = insets.top + this._tabLeadingComponent.getSize().height;
/*  793 */           temp = this._tabLeadingComponent.getSize().height;
/*      */         }
/*      */ 
/*  796 */         if ((isTabTrailingComponentVisible()) && 
/*  797 */           (this._maxTabHeight < this._tabTrailingComponent.getSize().height) && (temp < this._tabTrailingComponent.getSize().height))
/*      */         {
/*  799 */           y = insets.top + this._tabTrailingComponent.getSize().height;
/*      */         }
/*      */ 
/*  802 */         h -= y - insets.top;
/*      */       }
/*      */ 
/*  806 */       paintContentBorder(g, x, y, w, h);
/*      */ 
/*  808 */       Rectangle viewRect = this._tabScroller.viewport.getViewRect();
/*  809 */       Rectangle r = this._rects[selectedIndex];
/*  810 */       Rectangle button = this._tabScroller.scrollForwardButton.getBounds();
/*  811 */       Rectangle panel = this._tabScroller.tabPanel.getBounds();
/*  812 */       int lsize = 0;
/*      */ 
/*  814 */       if ((this._tabPane.getTabPlacement() == 1) || (this._tabPane.getTabPlacement() == 3)) {
/*  815 */         if (isTabLeadingComponentVisible()) {
/*  816 */           lsize = this._tabLeadingComponent.getSize().width;
/*      */         }
/*      */ 
/*      */       }
/*  820 */       else if (isTabLeadingComponentVisible()) {
/*  821 */         lsize = this._tabLeadingComponent.getSize().height;
/*      */       }
/*      */ 
/*  826 */       switch (tabPlacement) {
/*      */       case 2:
/*  828 */         if ((r.y < viewRect.y + viewRect.height) && (r.y + r.height + this._tabPane.getBoundsAt(selectedIndex).width - 9 > viewRect.y + viewRect.height))
/*      */         {
/*  833 */           if (selectedIndex != this._tabPane.getTabCount() - 1) {
/*  834 */             viewRect.y += r.y + r.height + this._tabPane.getBoundsAt(selectedIndex).width - 9 - (viewRect.y + viewRect.height);
/*  835 */             this._tabScroller.viewport.setViewPosition(new Point(viewRect.x, viewRect.y));
/*      */           }
/*  839 */           else if (panel.y + panel.height + lsize > button.y) {
/*  840 */             viewRect.y += r.y + r.height + this._tabPane.getBoundsAt(selectedIndex).width - 9 - (viewRect.y + viewRect.height);
/*  841 */             this._tabScroller.viewport.setViewPosition(new Point(viewRect.x, viewRect.y));
/*      */           }
/*      */           else
/*      */           {
/*  845 */             this._tabScroller.viewport.setSize(viewRect.width, viewRect.height + getLayoutSize());
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  851 */         paintContentBorderLeftEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/*  852 */         break;
/*      */       case 4:
/*  855 */         if ((r.y < viewRect.y + viewRect.height) && (r.y + r.height + this._tabPane.getBoundsAt(selectedIndex).width - 9 > viewRect.y + viewRect.height))
/*      */         {
/*  860 */           if (selectedIndex != this._tabPane.getTabCount() - 1) {
/*  861 */             viewRect.y += r.y + r.height + this._tabPane.getBoundsAt(selectedIndex).width - 9 - (viewRect.y + viewRect.height);
/*  862 */             this._tabScroller.viewport.setViewPosition(new Point(viewRect.x, viewRect.y));
/*      */           }
/*  865 */           else if (panel.y + panel.height + lsize > button.y) {
/*  866 */             viewRect.y += r.y + r.height + this._tabPane.getBoundsAt(selectedIndex).width - 9 - (viewRect.y + viewRect.height);
/*  867 */             this._tabScroller.viewport.setViewPosition(new Point(viewRect.x, viewRect.y));
/*      */           }
/*      */           else {
/*  870 */             this._tabScroller.viewport.setSize(viewRect.width, viewRect.height + getLayoutSize());
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  876 */         paintContentBorderRightEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/*  877 */         break;
/*      */       case 3:
/*  880 */         if ((r.x < viewRect.x + viewRect.width) && (r.x + r.width + this._tabPane.getBoundsAt(selectedIndex).height - 9 > viewRect.x + viewRect.width))
/*      */         {
/*  884 */           if (selectedIndex != this._tabPane.getTabCount() - 1) {
/*  885 */             viewRect.x += r.x + r.width + this._tabPane.getBoundsAt(selectedIndex).height - 9 - (viewRect.x + viewRect.width);
/*  886 */             this._tabScroller.viewport.setViewPosition(new Point(viewRect.x, viewRect.y));
/*      */           }
/*  890 */           else if (panel.x + panel.width + lsize > button.x) {
/*  891 */             viewRect.x += r.x + r.width + this._tabPane.getBoundsAt(selectedIndex).height - 9 - (viewRect.x + viewRect.width);
/*  892 */             this._tabScroller.viewport.setViewPosition(new Point(viewRect.x, viewRect.y));
/*      */           }
/*      */           else {
/*  895 */             this._tabScroller.viewport.setSize(viewRect.width + getLayoutSize(), viewRect.height);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  901 */         paintContentBorderBottomEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/*  902 */         break;
/*      */       case 1:
/*      */       default:
/*  907 */         if ((r.x < viewRect.x + viewRect.width) && (r.x + r.width + this._tabPane.getBoundsAt(selectedIndex).height - 9 > viewRect.x + viewRect.width)) {
/*  908 */           if (selectedIndex != this._tabPane.getTabCount() - 1) {
/*  909 */             viewRect.x += r.x + r.width + this._tabPane.getBoundsAt(selectedIndex).height - 9 - (viewRect.x + viewRect.width);
/*  910 */             this._tabScroller.viewport.setViewPosition(new Point(viewRect.x, viewRect.y));
/*      */           }
/*  913 */           else if (panel.x + panel.width + lsize > button.x) {
/*  914 */             viewRect.x += r.x + r.width + this._tabPane.getBoundsAt(selectedIndex).height - 9 - (viewRect.x + viewRect.width);
/*  915 */             this._tabScroller.viewport.setViewPosition(new Point(viewRect.x, viewRect.y));
/*      */           }
/*      */           else
/*      */           {
/*  919 */             this._tabScroller.viewport.setSize(viewRect.width + getLayoutSize(), viewRect.height);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  924 */         paintContentBorderTopEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/*      */       }
/*      */ 
/*  929 */       g.setColor(this._shadow);
/*  930 */       if (this._tabPane.isTabShown()) {
/*  931 */         switch (tabPlacement) {
/*      */         case 2:
/*  933 */           g.drawLine(width - 1, 0, width - 1, height - 1);
/*  934 */           g.drawLine(6, 0, width - 1, 0);
/*  935 */           g.drawLine(6, height - 1, width - 1, height - 1);
/*  936 */           g.drawLine(0, 6, 0, height - 7);
/*      */ 
/*  938 */           g.drawLine(1, height - 6, 1, height - 5);
/*  939 */           g.drawLine(2, height - 4, 2, height - 4);
/*  940 */           g.drawLine(3, height - 3, 3, height - 3);
/*  941 */           g.drawLine(4, height - 2, 5, height - 2);
/*      */ 
/*  943 */           g.drawLine(4, 1, 5, 1);
/*  944 */           g.drawLine(3, 2, 3, 2);
/*  945 */           g.drawLine(2, 3, 2, 3);
/*  946 */           g.drawLine(1, 4, 1, 5);
/*  947 */           break;
/*      */         case 4:
/*  949 */           g.drawLine(0, 0, 0, height - 1);
/*  950 */           g.drawLine(0, 0, width - 7, 0);
/*  951 */           g.drawLine(0, height - 1, width - 7, height - 1);
/*  952 */           g.drawLine(width - 1, 6, width - 1, height - 7);
/*      */ 
/*  954 */           g.drawLine(width - 2, height - 6, width - 2, height - 5);
/*  955 */           g.drawLine(width - 3, height - 4, width - 3, height - 4);
/*  956 */           g.drawLine(width - 4, height - 3, width - 4, height - 3);
/*  957 */           g.drawLine(width - 5, height - 2, width - 6, height - 2);
/*      */ 
/*  959 */           g.drawLine(width - 6, 1, width - 5, 1);
/*  960 */           g.drawLine(width - 4, 2, width - 4, 2);
/*  961 */           g.drawLine(width - 3, 3, width - 3, 3);
/*  962 */           g.drawLine(width - 2, 4, width - 2, 5);
/*  963 */           break;
/*      */         case 3:
/*  965 */           g.drawLine(0, 0, width - 1, 0);
/*  966 */           g.drawLine(0, 0, 0, height - 7);
/*  967 */           g.drawLine(width - 1, 0, width - 1, height - 7);
/*  968 */           g.drawLine(6, height - 1, width - 7, height - 1);
/*      */ 
/*  970 */           g.drawLine(width - 6, height - 2, width - 5, height - 2);
/*  971 */           g.drawLine(width - 4, height - 3, width - 4, height - 3);
/*  972 */           g.drawLine(width - 3, height - 4, width - 3, height - 4);
/*  973 */           g.drawLine(width - 2, height - 5, width - 2, height - 6);
/*      */ 
/*  975 */           g.drawLine(1, height - 6, 1, height - 5);
/*  976 */           g.drawLine(2, height - 4, 2, height - 4);
/*  977 */           g.drawLine(3, height - 3, 3, height - 3);
/*  978 */           g.drawLine(4, height - 2, 5, height - 2);
/*  979 */           break;
/*      */         case 1:
/*      */         default:
/*  982 */           g.drawLine(6, 0, width - 7, 0);
/*  983 */           g.drawLine(0, height - 1, width - 1, height - 1);
/*  984 */           g.drawLine(width - 1, 6, width - 1, height - 1);
/*  985 */           g.drawLine(0, 6, 0, height - 1);
/*      */ 
/*  987 */           g.drawLine(width - 6, 1, width - 5, 1);
/*  988 */           g.drawLine(width - 4, 2, width - 4, 2);
/*  989 */           g.drawLine(width - 3, 3, width - 3, 3);
/*  990 */           g.drawLine(width - 2, 4, width - 2, 5);
/*      */ 
/*  992 */           g.drawLine(4, 1, 5, 1);
/*  993 */           g.drawLine(3, 2, 3, 2);
/*  994 */           g.drawLine(2, 3, 2, 3);
/*  995 */           g.drawLine(1, 4, 1, 5);
/*  996 */           break;
/*      */         }
/*      */       }
/*      */       else
/* 1000 */         g.drawRect(0, 0, width - 1, height - 1);
/*      */     }
/*      */     else
/*      */     {
/* 1004 */       super.paintContentBorder(g, tabPlacement, selectedIndex);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintContentBorder(Graphics g, int x, int y, int w, int h)
/*      */   {
/* 1016 */     if (getTabShape() == 7) {
/* 1017 */       if (showFocusIndicator()) {
/* 1018 */         Insets insets = getContentBorderInsets(this._tabPane.getTabPlacement());
/* 1019 */         Color selectedTitleColor2 = UIDefaultsLookup.getColor("DockableFrame.activeTitleBackground2");
/* 1020 */         g.setColor(selectedTitleColor2);
/* 1021 */         g.fillRect(x, y, w, insets.top);
/* 1022 */         g.fillRect(x, y, insets.left, h);
/* 1023 */         g.fillRect(x, y + h - insets.bottom, w, insets.bottom);
/* 1024 */         g.fillRect(x + w - insets.right, y, insets.right, h);
/*      */       }
/*      */     }
/*      */     else
/* 1028 */       super.paintContentBorder(g, x, y, w, h);
/*      */   }
/*      */ 
/*      */   protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
/*      */   {
/* 1040 */     if (selectedIndex < 0) {
/* 1041 */       return;
/*      */     }
/*      */ 
/* 1044 */     if (getTabShape() == 7) {
/* 1045 */       Rectangle selRect = getTabBounds(selectedIndex, this._calcRect);
/*      */ 
/* 1047 */       Rectangle viewRect = this._tabScroller.viewport.getViewRect();
/* 1048 */       Rectangle r = this._rects[selectedIndex];
/*      */ 
/* 1050 */       g.setColor(getPainter().getControlShadow());
/*      */ 
/* 1053 */       if ((isTabLeadingComponentVisible()) && (selRect.x > 0)) {
/* 1054 */         g.drawLine(x, y, selRect.x, y);
/*      */       }
/*      */ 
/* 1057 */       if (r.x > viewRect.x) {
/* 1058 */         g.drawLine(x, y, selRect.x - 1, y);
/*      */       }
/*      */ 
/* 1061 */       if (this._tabPane.isTabShown()) {
/* 1062 */         if (r.x >= viewRect.x + viewRect.width) {
/* 1063 */           g.drawLine(x, y, x + w - 1, y);
/*      */         }
/*      */         else
/* 1066 */           g.drawLine(selRect.x + selRect.width + this._tabPane.getBoundsAt(selectedIndex).height - 9, y, x + w - 1, y);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1071 */       super.paintContentBorderTopEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
/*      */   {
/* 1083 */     if (selectedIndex < 0) {
/* 1084 */       return;
/*      */     }
/*      */ 
/* 1087 */     if (getTabShape() == 7) {
/* 1088 */       Rectangle selRect = getTabBounds(selectedIndex, this._calcRect);
/*      */ 
/* 1090 */       g.setColor(getPainter().getControlShadow());
/*      */ 
/* 1092 */       Rectangle viewRect = this._tabScroller.viewport.getViewRect();
/* 1093 */       Rectangle r = this._rects[selectedIndex];
/*      */ 
/* 1095 */       if ((isTabLeadingComponentVisible()) && (selRect.x > 0)) {
/* 1096 */         g.drawLine(x, y + h - 1, selRect.x, y + h - 1);
/*      */       }
/*      */ 
/* 1100 */       if (r.x > viewRect.x) {
/* 1101 */         g.drawLine(x, y + h - 1, selRect.x - 1, y + h - 1);
/*      */       }
/*      */ 
/* 1104 */       if (this._tabPane.isTabShown()) {
/* 1105 */         if (r.x >= viewRect.x + viewRect.width) {
/* 1106 */           g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */         }
/*      */         else
/* 1109 */           g.drawLine(selRect.x + selRect.width + this._tabPane.getBoundsAt(selectedIndex).height - 9, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1114 */       super.paintContentBorderBottomEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
/*      */   {
/* 1128 */     if (selectedIndex < 0) {
/* 1129 */       return;
/*      */     }
/*      */ 
/* 1132 */     if (getTabShape() == 7) {
/* 1133 */       Rectangle selRect = getTabBounds(selectedIndex, this._calcRect);
/*      */ 
/* 1135 */       Rectangle viewRect = this._tabScroller.viewport.getViewRect();
/* 1136 */       Rectangle r = this._rects[selectedIndex];
/*      */ 
/* 1138 */       g.setColor(getPainter().getControlShadow());
/*      */ 
/* 1140 */       if ((isTabLeadingComponentVisible()) && (selRect.y > 0)) {
/* 1141 */         g.drawLine(x, y, x, selRect.y);
/*      */       }
/*      */ 
/* 1145 */       if (r.y - 2 > viewRect.y) {
/* 1146 */         g.drawLine(x, y, x, selRect.y - 3);
/*      */       }
/*      */ 
/* 1149 */       if (this._tabPane.isTabShown()) {
/* 1150 */         if (r.y >= viewRect.y + viewRect.height) {
/* 1151 */           g.drawLine(x, y, x, y + h - 1);
/*      */         }
/*      */         else
/* 1154 */           g.drawLine(x, selRect.y + selRect.height + this._tabPane.getBoundsAt(selectedIndex).width - 9, x, y + h - 1);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1159 */       super.paintContentBorderLeftEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintContentBorderRightEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
/*      */   {
/* 1172 */     if (selectedIndex < 0) {
/* 1173 */       return;
/*      */     }
/*      */ 
/* 1176 */     if (getTabShape() == 7) {
/* 1177 */       Rectangle selRect = getTabBounds(selectedIndex, this._calcRect);
/*      */ 
/* 1179 */       Rectangle viewRect = this._tabScroller.viewport.getViewRect();
/* 1180 */       Rectangle r = this._rects[selectedIndex];
/*      */ 
/* 1182 */       g.setColor(getPainter().getControlShadow());
/*      */ 
/* 1184 */       if ((isTabLeadingComponentVisible()) && (selRect.y > 0)) {
/* 1185 */         g.drawLine(x + w - 1, y, x + w - 1, selRect.y);
/*      */       }
/*      */ 
/* 1189 */       if (r.y - 2 > viewRect.y) {
/* 1190 */         g.drawLine(x + w - 1, y, x + w - 1, selRect.y - 3);
/*      */       }
/*      */ 
/* 1193 */       if (this._tabPane.isTabShown()) {
/* 1194 */         if (r.y >= viewRect.y + viewRect.height) {
/* 1195 */           g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*      */         }
/*      */         else
/* 1198 */           g.drawLine(x + w - 1, selRect.y + selRect.height + this._tabPane.getBoundsAt(selectedIndex).width - 9, x + w - 1, y + h - 1);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1203 */       super.paintContentBorderRightEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected Rectangle getTabsTextBoundsAt(int tabIndex)
/*      */   {
/* 1210 */     Rectangle tabRect = this._tabPane.getBoundsAt(tabIndex);
/* 1211 */     Rectangle iconRect = new Rectangle(); Rectangle textRect = new Rectangle();
/*      */ 
/* 1213 */     String title = this._tabPane.getDisplayTitleAt(tabIndex);
/* 1214 */     Icon icon = this._tabPane.getIconForTab(tabIndex);
/*      */ 
/* 1216 */     SwingUtilities.layoutCompoundLabel(this._tabPane, this._tabPane.getGraphics().getFontMetrics(this._tabPane.getFont()), title, icon, 0, 0, 0, 11, tabRect, iconRect, textRect, icon == null ? 0 : this._textIconGap);
/*      */ 
/* 1222 */     if ((this._tabPane.getTabPlacement() == 1) || (this._tabPane.getTabPlacement() == 3)) {
/* 1223 */       tabRect.x += this._iconMarginHorizon;
/* 1224 */       textRect.x = (icon != null ? iconRect.x + iconRect.width + this._textIconGap : tabRect.x + this._textPadding);
/*      */     }
/*      */     else {
/* 1227 */       tabRect.y += this._iconMarginVertical;
/* 1228 */       textRect.y = (icon != null ? iconRect.y + iconRect.height + this._textIconGap : tabRect.y + this._textPadding);
/* 1229 */       tabRect.x += 2;
/* 1230 */       tabRect.x += 2;
/*      */     }
/*      */ 
/* 1233 */     return textRect;
/*      */   }
/*      */ 
/*      */   protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected)
/*      */   {
/* 1241 */     Rectangle tabRect = rects[tabIndex];
/* 1242 */     if ((this._tabPane.hasFocus()) && (isSelected))
/*      */     {
/* 1244 */       g.setColor(this._focus);
/*      */       int x;
/*      */       int y;
/*      */       int w;
/*      */       int h;
/* 1245 */       switch (tabPlacement) {
/*      */       case 2:
/* 1247 */         x = tabRect.x + 2;
/* 1248 */         y = tabRect.y + 3;
/* 1249 */         w = tabRect.width - 4;
/* 1250 */         h = tabRect.height - 19;
/* 1251 */         break;
/*      */       case 4:
/* 1253 */         x = tabRect.x + 2;
/* 1254 */         y = tabRect.y + 3;
/* 1255 */         w = tabRect.width - 4;
/* 1256 */         h = tabRect.height - 19;
/* 1257 */         break;
/*      */       case 3:
/* 1259 */         x = tabRect.x + 3;
/* 1260 */         y = tabRect.y + 2;
/* 1261 */         w = tabRect.width - 19;
/* 1262 */         h = tabRect.height - 3;
/* 1263 */         break;
/*      */       case 1:
/*      */       default:
/* 1266 */         x = tabRect.x + 3;
/* 1267 */         y = tabRect.y + 2;
/* 1268 */         w = tabRect.width - 19;
/* 1269 */         h = tabRect.height - 3;
/*      */       }
/* 1271 */       BasicGraphicsUtils.drawDashedRect(g, x, y, w, h);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected BasicJideTabbedPaneUI.TabCloseButton createNoFocusButton(int type)
/*      */   {
/* 1277 */     return new Eclipse3xTabCloseButton(type);
/*      */   }
/*      */ 
/*      */   public class Eclipse3xTabCloseButton extends BasicJideTabbedPaneUI.TabCloseButton {
/*      */     public Eclipse3xTabCloseButton(int type) {
/* 1282 */       super(type);
/*      */     }
/*      */ 
/*      */     public Dimension getPreferredSize()
/*      */     {
/* 1287 */       return new Dimension(15, 15);
/*      */     }
/*      */ 
/*      */     protected void paintComponent(Graphics g)
/*      */     {
/* 1292 */       if (!isEnabled()) {
/* 1293 */         setMouseOver(false);
/* 1294 */         setMousePressed(false);
/*      */       }
/* 1296 */       g.setColor(UIDefaultsLookup.getColor("controlShadow").darker());
/* 1297 */       int centerX = getWidth() >> 1;
/* 1298 */       int centerY = getHeight() >> 1;
/* 1299 */       switch (getType()) {
/*      */       case 0:
/* 1301 */         g.drawLine(centerX - 4, centerY - 4, centerX - 2, centerY - 4);
/* 1302 */         g.drawLine(centerX - 4, centerY - 4, centerX - 4, centerY - 2);
/*      */ 
/* 1304 */         g.drawLine(centerX - 1, centerY - 3, centerX - 0, centerY - 2);
/* 1305 */         g.drawLine(centerX - 3, centerY - 1, centerX - 2, centerY - 0);
/*      */ 
/* 1307 */         g.drawLine(centerX + 3, centerY - 4, centerX + 5, centerY - 4);
/* 1308 */         g.drawLine(centerX + 5, centerY - 4, centerX + 5, centerY - 2);
/*      */ 
/* 1310 */         g.drawLine(centerX + 2, centerY - 3, centerX + 1, centerY - 2);
/* 1311 */         g.drawLine(centerX + 4, centerY - 1, centerX + 3, centerY - 0);
/*      */ 
/* 1313 */         g.drawLine(centerX - 4, centerY + 5, centerX - 2, centerY + 5);
/* 1314 */         g.drawLine(centerX - 4, centerY + 5, centerX - 4, centerY + 3);
/*      */ 
/* 1316 */         g.drawLine(centerX - 1, centerY + 4, centerX - 0, centerY + 3);
/* 1317 */         g.drawLine(centerX - 3, centerY + 2, centerX - 2, centerY + 1);
/*      */ 
/* 1319 */         g.drawLine(centerX + 3, centerY + 5, centerX + 5, centerY + 5);
/* 1320 */         g.drawLine(centerX + 5, centerY + 5, centerX + 5, centerY + 3);
/*      */ 
/* 1322 */         g.drawLine(centerX + 2, centerY + 4, centerX + 1, centerY + 3);
/* 1323 */         g.drawLine(centerX + 4, centerY + 2, centerX + 3, centerY + 1);
/*      */ 
/* 1325 */         if (isMouseOver()) {
/* 1326 */           g.setColor(new Color(252, 160, 160));
/*      */         }
/*      */         else {
/* 1329 */           g.setColor(Color.WHITE);
/*      */         }
/* 1331 */         g.drawLine(centerX - 2, centerY - 3, centerX + 4, centerY + 3);
/* 1332 */         g.drawLine(centerX - 3, centerY - 3, centerX + 4, centerY + 4);
/* 1333 */         g.drawLine(centerX - 3, centerY - 2, centerX + 3, centerY + 4);
/*      */ 
/* 1335 */         g.drawLine(centerX - 3, centerY + 3, centerX + 3, centerY - 3);
/* 1336 */         g.drawLine(centerX - 3, centerY + 4, centerX + 4, centerY - 3);
/* 1337 */         g.drawLine(centerX - 2, centerY + 4, centerX + 4, centerY - 2);
/* 1338 */         break;
/*      */       default:
/* 1340 */         super.paintComponent(g);
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.eclipse.Eclipse3xJideTabbedPaneUI
 * JD-Core Version:    0.6.0
 */
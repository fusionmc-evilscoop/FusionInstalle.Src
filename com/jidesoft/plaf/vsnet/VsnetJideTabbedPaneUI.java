/*      */ package com.jidesoft.plaf.vsnet;
/*      */ 
/*      */ import com.jidesoft.plaf.UIDefaultsLookup;
/*      */ import com.jidesoft.plaf.basic.BasicJideTabbedPaneUI;
/*      */ import com.jidesoft.plaf.basic.BasicJideTabbedPaneUI.ScrollableTabSupport;
/*      */ import com.jidesoft.plaf.basic.BasicJideTabbedPaneUI.ScrollableTabViewport;
/*      */ import com.jidesoft.plaf.basic.ThemePainter;
/*      */ import com.jidesoft.swing.JideTabbedPane;
/*      */ import com.jidesoft.swing.JideTabbedPane.ColorProvider;
/*      */ import com.jidesoft.swing.JideTabbedPane.GradientColorProvider;
/*      */ import com.jidesoft.utils.ColorUtils;
/*      */ import java.awt.Color;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Rectangle;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.UIResource;
/*      */ 
/*      */ public class VsnetJideTabbedPaneUI extends BasicJideTabbedPaneUI
/*      */ {
/*      */   protected Color _backgroundSelectedColorStart;
/*      */   protected Color _backgroundSelectedColorEnd;
/*      */   protected Color _backgroundUnselectedColorStart;
/*      */   protected Color _backgroundUnselectedColorEnd;
/*      */ 
/*      */   public static ComponentUI createUI(JComponent c)
/*      */   {
/*   34 */     return new VsnetJideTabbedPaneUI();
/*      */   }
/*      */ 
/*      */   public void installColorTheme()
/*      */   {
/*   39 */     super.installColorTheme();
/*      */ 
/*   41 */     int tabStyle = getTabShape();
/*   42 */     int colorTheme = getColorTheme();
/*   43 */     switch (tabStyle) {
/*      */     case 3:
/*   45 */       if (colorTheme == 3) {
/*   46 */         this._selectColor1 = this._shadow;
/*   47 */         this._selectColor2 = this._selectColor1;
/*      */       }
/*   49 */       else if (colorTheme == 4) {
/*   50 */         this._selectColor1 = UIDefaultsLookup.getColor("TextArea.selectionBackground");
/*   51 */         this._selectColor2 = this._selectColor1;
/*      */       }
/*   53 */       this._unselectColor1 = getPainter().getControlShadow();
/*      */ 
/*   55 */       this._unselectColor2 = this._lightHighlight;
/*   56 */       break;
/*      */     case 8:
/*   59 */       if (this._tabPane.getColorTheme() != 3) break;
/*   60 */       this._selectColor2 = null;
/*   61 */       this._selectColor3 = null;
/*   62 */       this._unselectColor2 = this._lightHighlight;
/*   63 */       this._unselectColor3 = this._shadow; break;
/*      */     case 1:
/*      */     case 11:
/*   68 */       if (this._tabPane.getColorTheme() == 3) {
/*   69 */         this._selectColor1 = getPainter().getTabbedPaneSelectDk();
/*   70 */         this._selectColor2 = getPainter().getTabbedPaneSelectLt();
/*   71 */         this._selectColor3 = getPainter().getControlDk();
/*   72 */         this._unselectColor1 = getPainter().getControlDk();
/*   73 */         this._unselectColor2 = null;
/*   74 */         this._unselectColor3 = null;
/*      */       }
/*   76 */       if (this._tabPane.getColorTheme() != 4) break;
/*   77 */       this._selectColor1 = getPainter().getTabbedPaneSelectDk();
/*   78 */       this._selectColor2 = getPainter().getTabbedPaneSelectLt();
/*   79 */       this._selectColor3 = getPainter().getControlDk();
/*   80 */       this._unselectColor1 = getPainter().getControlDk();
/*   81 */       this._unselectColor2 = null;
/*   82 */       this._unselectColor3 = null;
/*      */     }
/*      */ 
/*   87 */     installBackgroundColor();
/*      */   }
/*      */ 
/*      */   protected void installBackgroundColor() {
/*   91 */     int colorTheme = getColorTheme();
/*      */ 
/*   93 */     switch (getTabShape()) {
/*      */     case 2:
/*   95 */       this._backgroundSelectedColorStart = this._highlight;
/*   96 */       this._backgroundSelectedColorEnd = this._highlight;
/*   97 */       this._backgroundUnselectedColorStart = null;
/*   98 */       this._backgroundUnselectedColorEnd = null;
/*   99 */       break;
/*      */     case 9:
/*  101 */       if (colorTheme == 1) {
/*  102 */         this._backgroundSelectedColorStart = this._highlight;
/*  103 */         this._backgroundSelectedColorEnd = this._highlight;
/*  104 */         this._backgroundUnselectedColorStart = null;
/*  105 */         this._backgroundUnselectedColorEnd = null;
/*      */       }
/*      */       else {
/*  108 */         this._backgroundSelectedColorStart = this._background;
/*  109 */         this._backgroundSelectedColorEnd = this._highlight;
/*  110 */         this._backgroundUnselectedColorStart = null;
/*  111 */         this._backgroundUnselectedColorEnd = null;
/*      */       }
/*  113 */       break;
/*      */     case 5:
/*      */     case 10:
/*  116 */       this._backgroundSelectedColorStart = this._highlight;
/*  117 */       this._backgroundSelectedColorEnd = this._highlight;
/*  118 */       this._backgroundUnselectedColorStart = this._highlight;
/*  119 */       this._backgroundUnselectedColorEnd = this._highlight;
/*  120 */       break;
/*      */     case 3:
/*  122 */       if (colorTheme == 1) {
/*  123 */         this._backgroundSelectedColorStart = this._highlight;
/*  124 */         this._backgroundSelectedColorEnd = this._highlight;
/*  125 */         this._backgroundUnselectedColorStart = null;
/*  126 */         this._backgroundUnselectedColorEnd = null;
/*      */       }
/*  128 */       else if (this._tabPane.getColorTheme() == 3) {
/*  129 */         this._backgroundSelectedColorStart = this._lightHighlight;
/*  130 */         this._backgroundSelectedColorEnd = this._lightHighlight;
/*  131 */         this._backgroundUnselectedColorStart = null;
/*  132 */         this._backgroundUnselectedColorEnd = null;
/*      */       } else {
/*  134 */         if (this._tabPane.getColorTheme() != 4) break;
/*  135 */         this._backgroundSelectedColorStart = this._lightHighlight;
/*  136 */         this._backgroundSelectedColorEnd = this._highlight;
/*  137 */         this._backgroundUnselectedColorStart = null;
/*  138 */         this._backgroundUnselectedColorEnd = null; } break;
/*      */     case 8:
/*  142 */       if (this._tabPane.getColorTheme() == 1) {
/*  143 */         this._backgroundSelectedColorStart = this._highlight;
/*  144 */         this._backgroundSelectedColorEnd = this._highlight;
/*  145 */         this._backgroundUnselectedColorStart = this._lightHighlight;
/*  146 */         this._backgroundUnselectedColorEnd = this._lightHighlight;
/*      */       }
/*  148 */       else if (this._tabPane.getColorTheme() == 3) {
/*  149 */         this._backgroundSelectedColorStart = this._lightHighlight;
/*  150 */         this._backgroundSelectedColorEnd = this._lightHighlight;
/*  151 */         this._backgroundUnselectedColorStart = this._highlight;
/*  152 */         this._backgroundUnselectedColorEnd = this._highlight;
/*      */       }
/*      */       else {
/*  155 */         this._backgroundSelectedColorStart = this._lightHighlight;
/*  156 */         this._backgroundSelectedColorEnd = this._lightHighlight;
/*  157 */         this._backgroundUnselectedColorStart = this._lightHighlight;
/*  158 */         this._backgroundUnselectedColorEnd = this._highlight;
/*      */       }
/*  160 */       break;
/*      */     case 1:
/*      */     case 11:
/*  163 */       if ((colorTheme == 3) || (colorTheme == 4)) {
/*  164 */         this._backgroundSelectedColorStart = this._lightHighlight;
/*  165 */         this._backgroundSelectedColorEnd = this._lightHighlight;
/*  166 */         this._backgroundUnselectedColorStart = this._lightHighlight;
/*  167 */         this._backgroundUnselectedColorEnd = this._highlight;
/*      */       }
/*      */       else {
/*  170 */         this._backgroundSelectedColorStart = this._highlight;
/*  171 */         this._backgroundSelectedColorEnd = this._highlight;
/*  172 */         this._backgroundUnselectedColorStart = this._highlight;
/*  173 */         this._backgroundUnselectedColorEnd = this._highlight;
/*      */       }
/*  175 */       break;
/*      */     case 4:
/*  177 */       if (colorTheme == 1) {
/*  178 */         this._backgroundSelectedColorStart = this._highlight;
/*  179 */         this._backgroundSelectedColorEnd = this._highlight;
/*  180 */         this._backgroundUnselectedColorStart = this._highlight;
/*  181 */         this._backgroundUnselectedColorEnd = this._highlight;
/*      */       }
/*  183 */       else if (colorTheme == 4) {
/*  184 */         this._backgroundSelectedColorStart = this._lightHighlight;
/*  185 */         this._backgroundSelectedColorEnd = this._lightHighlight;
/*  186 */         this._backgroundUnselectedColorStart = this._lightHighlight;
/*  187 */         this._backgroundUnselectedColorEnd = this._highlight;
/*      */       }
/*      */       else {
/*  190 */         this._backgroundSelectedColorStart = this._lightHighlight;
/*  191 */         this._backgroundSelectedColorEnd = this._lightHighlight;
/*  192 */         this._backgroundUnselectedColorStart = this._lightHighlight;
/*  193 */         this._backgroundUnselectedColorEnd = this._highlight;
/*      */       }
/*      */     case 6:
/*      */     case 7:
/*      */     }
/*      */   }
/*      */ 
/*      */   public void uninstallColorTheme() {
/*  201 */     super.uninstallColorTheme();
/*      */ 
/*  203 */     this._backgroundSelectedColorStart = null;
/*  204 */     this._backgroundSelectedColorEnd = null;
/*  205 */     this._backgroundUnselectedColorStart = null;
/*  206 */     this._backgroundUnselectedColorEnd = null;
/*      */   }
/*      */ 
/*      */   protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */   {
/*  212 */     super.paintTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
/*      */ 
/*  214 */     if (this.tabRegion != null) {
/*  215 */       Color[] colors = getGradientColors(tabIndex, isSelected);
/*  216 */       if (colors != null) {
/*  217 */         getPainter().paintTabBackground(this._tabPane, g, this.tabRegion, colors, 0, 0);
/*      */       }
/*      */     }
/*      */ 
/*  221 */     if ((getTabShape() == 11) && 
/*  222 */       (this._mouseEnter) && (tabIndex == this._indexMouseOver) && (!isSelected) && (this._tabPane.isEnabledAt(this._indexMouseOver)))
/*  223 */       paintTabBackgroundMouseOver(g, tabPlacement, tabIndex, x, y, w, h, isSelected, this._backgroundUnselectedColorStart, this._backgroundUnselectedColorEnd);
/*      */   }
/*      */ 
/*      */   protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
/*      */   {
/*  236 */     if (selectedIndex < 0) {
/*  237 */       return;
/*      */     }
/*      */ 
/*  240 */     if (!this._tabPane.isTabShown()) {
/*  241 */       return;
/*      */     }
/*      */ 
/*  244 */     Rectangle selRect = getTabBounds(selectedIndex, this._calcRect);
/*      */ 
/*  246 */     Rectangle viewRect = this._tabScroller.viewport.getViewRect();
/*  247 */     Rectangle r = this._rects[selectedIndex];
/*      */ 
/*  249 */     int tabShape = getTabShape();
/*      */ 
/*  251 */     Insets contentInsets = getContentBorderInsets(tabPlacement);
/*      */ 
/*  253 */     if (tabShape == 4) {
/*  254 */       if (this._tabPane.getColorTheme() == 1) {
/*  255 */         g.setColor(this._shadow);
/*      */       }
/*      */       else {
/*  258 */         g.setColor(this._selectColor1);
/*      */       }
/*      */ 
/*  261 */       if (contentInsets.left > 0) {
/*  262 */         g.drawLine(x, y, x, y + h - 1);
/*      */       }
/*  264 */       if (contentInsets.right > 0) {
/*  265 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*      */       }
/*  267 */       if (contentInsets.bottom > 0) {
/*  268 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*      */ 
/*  271 */       if ((!this._tabPane.isTabShown()) || (r.x >= viewRect.x + viewRect.width)) {
/*  272 */         g.drawLine(x, y, x + w - 1, y);
/*      */       }
/*      */       else {
/*  275 */         g.drawLine(x, y, selRect.x - selRect.height + 2, y);
/*  276 */         g.drawLine(selRect.x + selRect.width, y, x + w - 1, y);
/*      */       }
/*      */     }
/*  279 */     else if (tabShape == 8) {
/*  280 */       if (this._tabPane.getColorTheme() == 1) {
/*  281 */         g.setColor(Color.BLACK);
/*      */       }
/*      */       else {
/*  284 */         g.setColor(getPainter().getControlShadow());
/*      */       }
/*      */ 
/*  287 */       g.drawLine(x, y, selRect.x - selRect.height / 2 + 4, y);
/*      */ 
/*  289 */       if (contentInsets.left > 0) {
/*  290 */         g.drawLine(x, y, x, y + h - 1);
/*      */       }
/*  292 */       if (contentInsets.right > 0) {
/*  293 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*      */       }
/*  295 */       if (contentInsets.bottom > 0) {
/*  296 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*      */ 
/*  300 */       if ((!this._tabPane.isTabShown()) || (r.x >= viewRect.x + viewRect.width)) {
/*  301 */         g.drawLine(x, y, x + w - 1, y);
/*      */       }
/*  304 */       else if ((!this._tabPane.isShowIconsOnTab()) && (!this._tabPane.isUseDefaultShowIconsOnTab()))
/*      */       {
/*  306 */         g.drawLine(selRect.x + selRect.width + selRect.height / 2 - 4, y, x + w - 1, y);
/*      */       }
/*      */       else
/*      */       {
/*  310 */         g.drawLine(selRect.x + selRect.width + selRect.height / 2 - 6, y, x + w - 1, y);
/*      */       }
/*      */ 
/*      */     }
/*  315 */     else if ((tabShape == 1) || (tabShape == 11))
/*      */     {
/*  317 */       if ((this._tabPane.getColorTheme() == 3) || (this._tabPane.getColorTheme() == 4))
/*      */       {
/*  319 */         g.setColor(getPainter().getControlDk());
/*      */       }
/*      */       else {
/*  322 */         g.setColor(getBorderEdgeColor());
/*      */       }
/*      */ 
/*  325 */       if ((this._tabPane.getColorTheme() == 3) || (this._tabPane.getColorTheme() == 4))
/*      */       {
/*  327 */         g.drawLine(x, y, selRect.x - 1, y);
/*      */       }
/*      */       else {
/*  330 */         g.drawLine(x, y, selRect.x - 1, y);
/*      */       }
/*      */ 
/*  333 */       if (contentInsets.left > 0) {
/*  334 */         g.drawLine(x, y, x, y + h - 1);
/*      */       }
/*      */ 
/*  337 */       if ((!this._tabPane.isTabShown()) || (r.x >= viewRect.x + viewRect.width)) {
/*  338 */         if ((this._tabPane.getColorTheme() == 3) || (this._tabPane.getColorTheme() == 4))
/*      */         {
/*  340 */           g.setColor(getPainter().getControlDk());
/*      */         }
/*      */         else {
/*  343 */           g.setColor(this._lightHighlight);
/*      */         }
/*      */ 
/*  346 */         g.drawLine(x, y, x + w - 1, y);
/*      */       }
/*  349 */       else if ((this._tabPane.getColorTheme() == 3) || (this._tabPane.getColorTheme() == 4))
/*      */       {
/*  351 */         g.setColor(getPainter().getControlDk());
/*  352 */         g.drawLine(selRect.x + selRect.width + 2, y, x + w - 1, y);
/*      */       }
/*      */       else {
/*  355 */         g.setColor(this._lightHighlight);
/*  356 */         g.drawLine(selRect.x + selRect.width + 2, y, x + w - 1, y);
/*      */       }
/*      */ 
/*  360 */       if (this._tabPane.getColorTheme() == 1) {
/*  361 */         g.setColor(new Color(115, 109, 99));
/*  362 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*  363 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*      */       else {
/*  366 */         g.setColor(getPainter().getControlDk());
/*      */ 
/*  368 */         if (contentInsets.left > 0) {
/*  369 */           g.drawLine(x, y, x, y + h - 1);
/*      */         }
/*  371 */         if (contentInsets.right > 0) {
/*  372 */           g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*      */         }
/*  374 */         if (contentInsets.bottom > 0) {
/*  375 */           g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */         }
/*      */       }
/*      */     }
/*  379 */     else if (tabShape == 2) {
/*  380 */       g.setColor(this._selectColor1);
/*      */ 
/*  383 */       g.drawLine(x, y, selRect.x, y);
/*      */ 
/*  385 */       if (contentInsets.left > 0) {
/*  386 */         g.drawLine(x, y, x, y + h - 1);
/*      */       }
/*      */ 
/*  389 */       if ((!this._tabPane.isTabShown()) || (r.x >= viewRect.x + viewRect.width)) {
/*  390 */         g.setColor(this._selectColor1);
/*  391 */         g.drawLine(x, y, x + w - 1, y);
/*      */       }
/*      */       else {
/*  394 */         g.setColor(this._selectColor2);
/*  395 */         g.drawLine(selRect.x + selRect.width - 1, y, selRect.x + selRect.width - 1, y);
/*      */ 
/*  397 */         g.setColor(this._selectColor1);
/*  398 */         g.drawLine(selRect.x, y, selRect.x, y);
/*      */ 
/*  400 */         g.setColor(this._selectColor1);
/*  401 */         g.drawLine(selRect.x + selRect.width, y, x + w - 1, y);
/*      */       }
/*      */ 
/*  404 */       g.setColor(this._selectColor2);
/*  405 */       if (contentInsets.right > 0) {
/*  406 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*      */       }
/*  408 */       if (contentInsets.bottom > 0) {
/*  409 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*      */ 
/*      */     }
/*  413 */     else if (tabShape == 9) {
/*  414 */       g.setColor(this._selectColor1);
/*      */ 
/*  417 */       g.drawLine(x, y, selRect.x, y);
/*      */ 
/*  419 */       if (contentInsets.left > 0) {
/*  420 */         g.drawLine(x, y, x, y + h - 1);
/*      */       }
/*      */ 
/*  423 */       if ((!this._tabPane.isTabShown()) || (r.x >= viewRect.x + viewRect.width)) {
/*  424 */         g.drawLine(x, y, x + w - 1, y);
/*      */       }
/*      */       else {
/*  427 */         g.drawLine(selRect.x + selRect.width - 1, y, selRect.x + selRect.width - 1, y);
/*      */ 
/*  429 */         g.drawLine(selRect.x, y, selRect.x, y);
/*      */ 
/*  431 */         g.drawLine(selRect.x + selRect.width, y, x + w - 1, y);
/*      */       }
/*      */ 
/*  434 */       g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 1);
/*  435 */       g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */     }
/*  437 */     else if ((tabShape == 5) || (tabShape == 10)) {
/*  438 */       g.setColor(this._shadow);
/*      */ 
/*  440 */       if (contentInsets.left > 0) {
/*  441 */         g.drawLine(x, y, x, y + h - 1);
/*      */       }
/*  443 */       if (contentInsets.right > 0) {
/*  444 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*      */       }
/*  446 */       if (contentInsets.bottom > 0) {
/*  447 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*      */ 
/*  450 */       g.drawLine(x, y, selRect.x, y);
/*      */ 
/*  452 */       if ((!this._tabPane.isTabShown()) || (r.x >= viewRect.x + viewRect.width)) {
/*  453 */         g.drawLine(x, y, x + w - 1, y);
/*      */       }
/*      */       else
/*  456 */         g.drawLine(selRect.x + selRect.width, y, x + w - 1, y);
/*      */     }
/*      */     else
/*      */     {
/*  460 */       super.paintContentBorderTopEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
/*      */   {
/*  472 */     if (selectedIndex < 0) {
/*  473 */       return;
/*      */     }
/*      */ 
/*  476 */     if (!this._tabPane.isTabShown()) {
/*  477 */       return;
/*      */     }
/*      */ 
/*  480 */     Rectangle selRect = getTabBounds(selectedIndex, this._calcRect);
/*      */ 
/*  482 */     Rectangle viewRect = this._tabScroller.viewport.getViewRect();
/*  483 */     Rectangle r = this._rects[selectedIndex];
/*      */ 
/*  485 */     int tabShape = getTabShape();
/*      */ 
/*  487 */     Insets contentInsets = getContentBorderInsets(tabPlacement);
/*      */ 
/*  489 */     if (tabShape == 4) {
/*  490 */       if (this._tabPane.getColorTheme() == 1) {
/*  491 */         g.setColor(this._shadow);
/*      */       }
/*      */       else {
/*  494 */         g.setColor(this._selectColor1);
/*      */       }
/*  496 */       if (contentInsets.left > 0) {
/*  497 */         g.drawLine(x, y, x, y + h - 1);
/*      */       }
/*  499 */       if (contentInsets.right > 0) {
/*  500 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*      */       }
/*  502 */       if (contentInsets.top > 0) {
/*  503 */         g.drawLine(x, y, x + w - 1, y);
/*      */       }
/*      */ 
/*  506 */       if ((!this._tabPane.isTabShown()) || (r.x >= viewRect.x + viewRect.width)) {
/*  507 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*  510 */       else if (!this._tabPane.isTabShown()) {
/*  511 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*      */       else {
/*  514 */         g.drawLine(x, y + h - 1, selRect.x - selRect.height + 2, y + h - 1);
/*  515 */         g.drawLine(selRect.x + selRect.width, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*      */ 
/*      */     }
/*  519 */     else if (tabShape == 8) {
/*  520 */       if (this._tabPane.getColorTheme() == 1) {
/*  521 */         g.setColor(Color.BLACK);
/*      */       }
/*      */       else {
/*  524 */         g.setColor(getPainter().getControlShadow());
/*      */       }
/*      */ 
/*  527 */       g.drawLine(x, y + h - 1, selRect.x - selRect.height / 2 + 4, y + h - 1);
/*      */ 
/*  529 */       if (contentInsets.left > 0) {
/*  530 */         g.drawLine(x, y, x, y + h - 1);
/*      */       }
/*  532 */       if (contentInsets.right > 0) {
/*  533 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*      */       }
/*  535 */       if (contentInsets.top > 0) {
/*  536 */         g.drawLine(x, y, x + w - 1, y);
/*      */       }
/*      */ 
/*  539 */       if ((!this._tabPane.isTabShown()) || (r.x >= viewRect.x + viewRect.width)) {
/*  540 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*  543 */       else if ((!this._tabPane.isShowIconsOnTab()) && (!this._tabPane.isUseDefaultShowIconsOnTab())) {
/*  544 */         g.drawLine(selRect.x + selRect.width + selRect.height / 2 - 4, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*      */       else {
/*  547 */         g.drawLine(selRect.x + selRect.width + selRect.height / 2 - 6, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*      */ 
/*      */     }
/*  551 */     else if ((tabShape == 1) || (tabShape == 11))
/*      */     {
/*  553 */       if (this._tabPane.getColorTheme() == 1) {
/*  554 */         g.setColor(new Color(113, 111, 100));
/*  555 */         g.drawLine(x, y + h - 1, selRect.x - 2, y + h - 1);
/*  556 */         if (contentInsets.right > 0) {
/*  557 */           g.drawLine(x + w - 1, y + h - 1, x + w - 1, y);
/*      */         }
/*      */ 
/*  560 */         if ((!this._tabPane.isTabShown()) || (r.x >= viewRect.x + viewRect.width)) {
/*  561 */           g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */         }
/*      */         else {
/*  564 */           g.drawLine(selRect.x + selRect.width, y + h - 1, x + w - 1, y + h - 1);
/*  565 */           g.setColor(UIDefaultsLookup.getColor("control"));
/*  566 */           g.drawLine(selRect.x, y + h - 1, selRect.x + selRect.width - 2, y + h - 1);
/*  567 */           g.drawLine(selRect.x, y + h - 2, selRect.x + selRect.width, y + h - 2);
/*      */         }
/*      */ 
/*  570 */         g.setColor(new Color(255, 255, 255));
/*      */ 
/*  572 */         if (contentInsets.left > 0) {
/*  573 */           g.drawLine(x, y, x, y + h - 2);
/*      */         }
/*  575 */         if (contentInsets.top > 0) {
/*  576 */           g.drawLine(x, y, x + w - 2, y);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  581 */         g.setColor(getPainter().getControlDk());
/*      */ 
/*  583 */         g.drawLine(x, y + h - 1, selRect.x - 1, y + h - 1);
/*      */ 
/*  585 */         if ((!this._tabPane.isTabShown()) || (r.x >= viewRect.x + viewRect.width)) {
/*  586 */           g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */         }
/*      */         else {
/*  589 */           g.drawLine(selRect.x + selRect.width + 2, y + h - 1, x + w - 1, y + h - 1);
/*      */         }
/*      */ 
/*  592 */         g.drawLine(selRect.x - 1, y + h - 1, selRect.x - 1, y + h - 1);
/*  593 */         g.drawLine(selRect.x + selRect.width + 2, y + h - 1, selRect.x + selRect.width + 2, y + h - 1);
/*      */ 
/*  595 */         if (contentInsets.left > 0) {
/*  596 */           g.drawLine(x, y, x, y + h - 2);
/*      */         }
/*  598 */         if (contentInsets.top > 0) {
/*  599 */           g.drawLine(x, y, x + w - 2, y);
/*      */         }
/*  601 */         if (contentInsets.left > 0) {
/*  602 */           g.drawLine(x + w - 1, y + h - 1, x + w - 1, y);
/*      */         }
/*      */       }
/*      */     }
/*  606 */     else if (tabShape == 2)
/*      */     {
/*  608 */       g.setColor(this._selectColor2);
/*  609 */       g.drawLine(x, y + h - 1, selRect.x - 1, y + h - 1);
/*      */ 
/*  611 */       g.setColor(this._selectColor1);
/*  612 */       g.drawLine(selRect.x, y + h - 1, selRect.x, y + h - 1);
/*      */ 
/*  614 */       if ((!this._tabPane.isTabShown()) || (r.x >= viewRect.x + viewRect.width)) {
/*  615 */         g.setColor(this._selectColor2);
/*  616 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*      */       else {
/*  619 */         g.setColor(this._selectColor2);
/*  620 */         g.drawLine(selRect.x + selRect.width - 1, y + h - 1, x + w - 2, y + h - 1);
/*      */       }
/*      */ 
/*  624 */       g.setColor(this._selectColor2);
/*  625 */       if (contentInsets.right > 0) {
/*  626 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*      */       }
/*      */ 
/*  629 */       g.setColor(this._selectColor1);
/*  630 */       if (contentInsets.left > 0) {
/*  631 */         g.drawLine(x, y, x, y + h - 2);
/*      */       }
/*  633 */       if (contentInsets.top > 0) {
/*  634 */         g.drawLine(x, y, x + w - 2, y);
/*      */       }
/*      */     }
/*  637 */     else if (tabShape == 9)
/*      */     {
/*  639 */       g.setColor(this._selectColor1);
/*  640 */       g.drawLine(x, y + h - 1, selRect.x - 1, y + h - 1);
/*      */ 
/*  642 */       g.drawLine(selRect.x, y + h - 1, selRect.x, y + h - 1);
/*      */ 
/*  644 */       if ((!this._tabPane.isTabShown()) || (r.x >= viewRect.x + viewRect.width)) {
/*  645 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*      */       else {
/*  648 */         g.drawLine(selRect.x + selRect.width - 1, y + h - 1, x + w - 2, y + h - 1);
/*      */       }
/*      */ 
/*  652 */       if (contentInsets.left > 0) {
/*  653 */         g.drawLine(x, y, x, y + h - 2);
/*      */       }
/*  655 */       if (contentInsets.right > 0) {
/*  656 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*      */       }
/*  658 */       if (contentInsets.top > 0) {
/*  659 */         g.drawLine(x, y, x + w - 2, y);
/*      */       }
/*      */     }
/*  662 */     else if ((tabShape == 5) || (tabShape == 10))
/*      */     {
/*  664 */       g.setColor(this._shadow);
/*  665 */       g.drawLine(x, y + h - 1, selRect.x - 1, y + h - 1);
/*  666 */       if (contentInsets.left > 0) {
/*  667 */         g.drawLine(x, y, x, y + h - 1);
/*      */       }
/*  669 */       if (contentInsets.right > 0) {
/*  670 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*      */       }
/*  672 */       if (contentInsets.top > 0) {
/*  673 */         g.drawLine(x, y, x + w - 1, y);
/*      */       }
/*  675 */       g.drawLine(selRect.x, y + h - 1, selRect.x, y + h - 1);
/*      */ 
/*  677 */       if ((!this._tabPane.isTabShown()) || (r.x >= viewRect.x + viewRect.width)) {
/*  678 */         g.drawLine(x, y + h - 1, x + w - 2, y + h - 1);
/*      */       }
/*      */       else
/*  681 */         g.drawLine(selRect.x + selRect.width, y + h - 1, x + w - 2, y + h - 1);
/*      */     }
/*      */     else
/*      */     {
/*  685 */       super.paintContentBorderBottomEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
/*      */   {
/*  697 */     if (selectedIndex < 0) {
/*  698 */       return;
/*      */     }
/*      */ 
/*  701 */     if (!this._tabPane.isTabShown()) {
/*  702 */       return;
/*      */     }
/*      */ 
/*  705 */     Rectangle selRect = getTabBounds(selectedIndex, this._calcRect);
/*      */ 
/*  707 */     Rectangle viewRect = this._tabScroller.viewport.getViewRect();
/*  708 */     Rectangle r = this._rects[selectedIndex];
/*      */ 
/*  710 */     int tabShape = getTabShape();
/*      */ 
/*  712 */     Insets contentInsets = getContentBorderInsets(tabPlacement);
/*      */ 
/*  714 */     if (tabShape == 4) {
/*  715 */       if (this._tabPane.getColorTheme() == 1) {
/*  716 */         g.setColor(this._shadow);
/*      */       }
/*      */       else {
/*  719 */         g.setColor(this._selectColor1);
/*      */       }
/*      */ 
/*  722 */       if (contentInsets.top > 0) {
/*  723 */         g.drawLine(x, y, x + w - 1, y);
/*      */       }
/*  725 */       if (contentInsets.right > 0) {
/*  726 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*      */       }
/*  728 */       if (contentInsets.bottom > 0) {
/*  729 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*      */ 
/*  732 */       if ((!this._tabPane.isTabShown()) || (r.y >= viewRect.y + viewRect.height)) {
/*  733 */         g.drawLine(x, y, x, y + h - 1);
/*      */       }
/*      */       else {
/*  736 */         g.drawLine(x, y, x, selRect.y - selRect.width + 2);
/*  737 */         g.drawLine(x, selRect.y + selRect.height, x, y + h - 1);
/*      */       }
/*      */     }
/*  740 */     else if (tabShape == 8) {
/*  741 */       if (this._tabPane.getColorTheme() == 1) {
/*  742 */         g.setColor(Color.BLACK);
/*      */       }
/*      */       else {
/*  745 */         g.setColor(getPainter().getControlShadow());
/*      */       }
/*      */ 
/*  748 */       g.drawLine(x, y, x, selRect.y - selRect.width / 2 + 4);
/*      */ 
/*  750 */       if (contentInsets.top > 0) {
/*  751 */         g.drawLine(x, y, x + w - 1, y);
/*      */       }
/*  753 */       if (contentInsets.right > 0) {
/*  754 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*      */       }
/*  756 */       if (contentInsets.bottom > 0) {
/*  757 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*      */ 
/*  760 */       if ((!this._tabPane.isTabShown()) || (r.y >= viewRect.y + viewRect.height)) {
/*  761 */         g.drawLine(x, y, x, y + h - 1);
/*      */       }
/*      */       else {
/*  764 */         g.drawLine(x, selRect.y + selRect.height + selRect.width / 2 - 4, x, y + h - 1);
/*      */       }
/*      */     }
/*  767 */     else if ((tabShape == 1) || (tabShape == 11))
/*      */     {
/*  769 */       if (this._tabPane.getColorTheme() == 1) {
/*  770 */         g.setColor(new Color(115, 109, 99));
/*  771 */         if (contentInsets.right > 0) {
/*  772 */           g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*      */         }
/*  774 */         if (contentInsets.bottom > 0) {
/*  775 */           g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */         }
/*      */ 
/*  778 */         g.setColor(new Color(255, 255, 255));
/*  779 */         if (contentInsets.top > 0) {
/*  780 */           g.drawLine(x, y, x + w - 2, y);
/*      */         }
/*  782 */         g.drawLine(x, y, x, selRect.y - 1);
/*      */ 
/*  784 */         if ((!this._tabPane.isTabShown()) || (r.y >= viewRect.y + viewRect.height)) {
/*  785 */           g.drawLine(x, y, x, y + h - 2);
/*      */         }
/*      */         else {
/*  788 */           g.drawLine(x, selRect.y + selRect.height + 1, x, y + h - 2);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  793 */         g.setColor(getPainter().getControlDk());
/*      */ 
/*  795 */         if (contentInsets.top > 0) {
/*  796 */           g.drawLine(x, y, x + w - 1, y);
/*      */         }
/*  798 */         if (contentInsets.right > 0) {
/*  799 */           g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*      */         }
/*  801 */         if (contentInsets.bottom > 0) {
/*  802 */           g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */         }
/*  804 */         g.drawLine(x, y, x, selRect.y - 2);
/*      */ 
/*  806 */         if ((!this._tabPane.isTabShown()) || (r.y >= viewRect.y + viewRect.height)) {
/*  807 */           g.drawLine(x, y, x, y + h - 2);
/*      */         }
/*      */         else {
/*  810 */           g.drawLine(x, selRect.y + selRect.height + 2, x, y + h - 2);
/*      */         }
/*      */       }
/*      */     }
/*  814 */     else if (tabShape == 2) {
/*  815 */       g.setColor(this._selectColor1);
/*      */ 
/*  818 */       g.drawLine(x, y, x, selRect.y);
/*      */ 
/*  820 */       if (contentInsets.top > 0) {
/*  821 */         g.drawLine(x, y, x + w - 1, y);
/*      */       }
/*      */ 
/*  824 */       if ((!this._tabPane.isTabShown()) || (r.x >= viewRect.x + viewRect.width)) {
/*  825 */         g.setColor(this._selectColor1);
/*  826 */         g.drawLine(x, y, x, y + h - 1);
/*      */       }
/*      */       else {
/*  829 */         g.setColor(this._selectColor1);
/*  830 */         g.drawLine(x, selRect.y, x, selRect.y);
/*      */ 
/*  832 */         g.setColor(this._selectColor2);
/*  833 */         g.drawLine(x, selRect.y + selRect.height - 1, x, selRect.y + selRect.height - 1);
/*      */ 
/*  835 */         g.setColor(this._selectColor1);
/*  836 */         g.drawLine(x, selRect.y + selRect.height, x, y + h - 1);
/*      */       }
/*      */ 
/*  840 */       g.setColor(this._selectColor2);
/*  841 */       if (contentInsets.right > 0) {
/*  842 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*      */       }
/*  844 */       if (contentInsets.bottom > 0) {
/*  845 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*      */     }
/*  848 */     else if (tabShape == 9) {
/*  849 */       g.setColor(this._selectColor1);
/*      */ 
/*  852 */       g.drawLine(x, y, x, selRect.y);
/*      */ 
/*  854 */       if ((!this._tabPane.isTabShown()) || (r.x >= viewRect.x + viewRect.width)) {
/*  855 */         g.drawLine(x, y, x, y + h - 1);
/*      */       }
/*      */       else {
/*  858 */         g.drawLine(x, selRect.y, x, selRect.y);
/*  859 */         g.drawLine(x, selRect.y + selRect.height - 1, x, selRect.y + selRect.height - 1);
/*  860 */         g.drawLine(x, selRect.y + selRect.height, x, y + h - 1);
/*      */       }
/*      */ 
/*  864 */       if (contentInsets.top > 0) {
/*  865 */         g.drawLine(x, y, x + w - 1, y);
/*      */       }
/*  867 */       if (contentInsets.right > 0) {
/*  868 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*      */       }
/*  870 */       if (contentInsets.bottom > 0) {
/*  871 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*      */     }
/*  874 */     else if ((tabShape == 5) || (tabShape == 10)) {
/*  875 */       g.setColor(this._shadow);
/*  876 */       if (contentInsets.top > 0) {
/*  877 */         g.drawLine(x, y, x + w - 1, y);
/*      */       }
/*  879 */       if (contentInsets.right > 0) {
/*  880 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*      */       }
/*  882 */       if (contentInsets.bottom > 0) {
/*  883 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*  885 */       g.drawLine(x, y, x, selRect.y);
/*      */ 
/*  887 */       if ((!this._tabPane.isTabShown()) || (r.y >= viewRect.y + viewRect.height)) {
/*  888 */         g.drawLine(x, y, x, y + h - 1);
/*      */       }
/*      */       else
/*  891 */         g.drawLine(x, selRect.y + selRect.height, x, y + h - 1);
/*      */     }
/*      */     else
/*      */     {
/*  895 */       super.paintContentBorderLeftEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintContentBorderRightEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
/*      */   {
/*  907 */     if (selectedIndex < 0) {
/*  908 */       return;
/*      */     }
/*      */ 
/*  911 */     if (!this._tabPane.isTabShown()) {
/*  912 */       return;
/*      */     }
/*      */ 
/*  915 */     Rectangle selRect = getTabBounds(selectedIndex, this._calcRect);
/*      */ 
/*  917 */     Rectangle viewRect = this._tabScroller.viewport.getViewRect();
/*  918 */     Rectangle r = this._rects[selectedIndex];
/*      */ 
/*  920 */     int tabShape = getTabShape();
/*      */ 
/*  922 */     Insets contentInsets = getContentBorderInsets(tabPlacement);
/*      */ 
/*  924 */     if (tabShape == 4) {
/*  925 */       if (this._tabPane.getColorTheme() == 1) {
/*  926 */         g.setColor(this._shadow);
/*      */       }
/*      */       else {
/*  929 */         g.setColor(this._selectColor1);
/*      */       }
/*      */ 
/*  932 */       if (contentInsets.top > 0) {
/*  933 */         g.drawLine(x, y, x + w - 1, y);
/*      */       }
/*  935 */       if (contentInsets.left > 0) {
/*  936 */         g.drawLine(x, y, x, y + h - 1);
/*      */       }
/*  938 */       if (contentInsets.bottom > 0) {
/*  939 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*      */ 
/*  942 */       if ((!this._tabPane.isTabShown()) || (r.y >= viewRect.y + viewRect.height)) {
/*  943 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*      */       }
/*      */       else {
/*  946 */         g.drawLine(x + w - 1, y, x + w - 1, selRect.y - selRect.width + 2);
/*  947 */         g.drawLine(x + w - 1, selRect.y + selRect.height, x + w - 1, y + h - 1);
/*      */       }
/*      */     }
/*  950 */     else if (tabShape == 8) {
/*  951 */       if (this._tabPane.getColorTheme() == 1) {
/*  952 */         g.setColor(Color.BLACK);
/*      */       }
/*      */       else {
/*  955 */         g.setColor(getPainter().getControlShadow());
/*      */       }
/*      */ 
/*  958 */       g.drawLine(x + w - 1, y, x + w - 1, selRect.y - selRect.width / 2 + 4);
/*      */ 
/*  960 */       if (contentInsets.top > 0) {
/*  961 */         g.drawLine(x, y, x + w - 1, y);
/*      */       }
/*  963 */       if (contentInsets.left > 0) {
/*  964 */         g.drawLine(x, y, x, y + h - 1);
/*      */       }
/*  966 */       if (contentInsets.bottom > 0) {
/*  967 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*      */ 
/*  970 */       if ((!this._tabPane.isTabShown()) || (r.y >= viewRect.y + viewRect.height)) {
/*  971 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*      */       }
/*      */       else {
/*  974 */         g.drawLine(x + w - 1, selRect.y + selRect.height + selRect.width / 2 - 4, x + w - 1, y + h - 1);
/*      */       }
/*      */     }
/*  977 */     else if ((tabShape == 1) || (tabShape == 11))
/*      */     {
/*  979 */       if (this._tabPane.getColorTheme() == 1)
/*      */       {
/*  981 */         g.setColor(new Color(115, 109, 99));
/*  982 */         g.drawLine(x + w - 1, y, x + w - 1, selRect.y - 2);
/*  983 */         if (contentInsets.bottom > 0) {
/*  984 */           g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */         }
/*      */ 
/*  987 */         if ((!this._tabPane.isTabShown()) || (r.y >= viewRect.y + viewRect.height)) {
/*  988 */           g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*  989 */           g.setColor(new Color(173, 170, 156));
/*  990 */           g.drawLine(x + w - 2, y + 1, x + w - 2, y + h - 2);
/*      */         }
/*      */         else {
/*  993 */           g.drawLine(x + w - 1, selRect.y + selRect.height + 1, x + w - 1, y + h - 1);
/*  994 */           g.setColor(UIDefaultsLookup.getColor("control"));
/*  995 */           g.drawLine(x + w - 1, selRect.y, x + w - 1, selRect.y + selRect.height - 1);
/*  996 */           g.drawLine(x + w - 2, selRect.y, x + w - 2, selRect.y + selRect.height + 1);
/*      */         }
/*      */ 
/*  999 */         g.setColor(new Color(255, 255, 255));
/* 1000 */         if (contentInsets.top > 0) {
/* 1001 */           g.drawLine(x, y, x + w - 2, y);
/*      */         }
/* 1003 */         if (contentInsets.left > 0)
/* 1004 */           g.drawLine(x, y, x, y + h - 2);
/*      */       }
/*      */       else
/*      */       {
/* 1008 */         g.setColor(getPainter().getControlDk());
/* 1009 */         if (contentInsets.top > 0) {
/* 1010 */           g.drawLine(x, y, x + w - 1, y);
/*      */         }
/* 1012 */         if (contentInsets.left > 0) {
/* 1013 */           g.drawLine(x, y, x, y + h - 1);
/*      */         }
/* 1015 */         if (contentInsets.bottom > 0) {
/* 1016 */           g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */         }
/* 1018 */         g.drawLine(x + w - 1, y, x + w - 1, selRect.y - 2);
/*      */ 
/* 1020 */         if ((!this._tabPane.isTabShown()) || (r.y >= viewRect.y + viewRect.height)) {
/* 1021 */           g.drawLine(x + w - 1, y, x + w - 1, y + h - 2);
/*      */         }
/*      */         else {
/* 1024 */           g.drawLine(x + w - 1, selRect.y + selRect.height + 2, x + w - 1, y + h - 2);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/* 1029 */     else if (tabShape == 2) {
/* 1030 */       g.setColor(getBorderEdgeColor());
/*      */ 
/* 1033 */       g.setColor(this._selectColor2);
/* 1034 */       g.drawLine(x + w - 1, y, x + w - 1, selRect.y);
/* 1035 */       if (contentInsets.bottom > 0) {
/* 1036 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*      */ 
/* 1039 */       if ((!this._tabPane.isTabShown()) || (r.x >= viewRect.x + viewRect.width)) {
/* 1040 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*      */       }
/*      */       else {
/* 1043 */         g.setColor(this._selectColor2);
/* 1044 */         g.drawLine(x + w - 1, selRect.y + selRect.height - 1, x + w - 1, selRect.y + selRect.height - 1);
/* 1045 */         g.drawLine(x + w - 1, selRect.y + selRect.height, x + w - 1, y + h - 1);
/*      */       }
/*      */ 
/* 1048 */       g.setColor(this._selectColor1);
/* 1049 */       if (contentInsets.top > 0) {
/* 1050 */         g.drawLine(x, y, x + w - 2, y);
/*      */       }
/* 1052 */       if (contentInsets.left > 0) {
/* 1053 */         g.drawLine(x, y, x, y + h - 2);
/*      */       }
/*      */     }
/* 1056 */     else if (tabShape == 9) {
/* 1057 */       g.setColor(this._selectColor1);
/*      */ 
/* 1060 */       g.drawLine(x + w - 1, y, x + w - 1, selRect.y - 1);
/*      */ 
/* 1062 */       if ((!this._tabPane.isTabShown()) || (r.x >= viewRect.x + viewRect.width)) {
/* 1063 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*      */       }
/*      */       else {
/* 1066 */         g.drawLine(x + w - 1, selRect.y, x + w - 1, selRect.y);
/* 1067 */         g.drawLine(x + w - 1, selRect.y + selRect.height - 1, x + w - 1, selRect.y + selRect.height - 1);
/* 1068 */         g.drawLine(x + w - 1, selRect.y + selRect.height, x + w - 1, y + h - 1);
/*      */       }
/*      */ 
/* 1071 */       if (contentInsets.top > 0) {
/* 1072 */         g.drawLine(x, y, x + w - 1, y);
/*      */       }
/* 1074 */       if (contentInsets.left > 0) {
/* 1075 */         g.drawLine(x, y, x, y + h - 2);
/*      */       }
/* 1077 */       if (contentInsets.bottom > 0) {
/* 1078 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*      */     }
/* 1081 */     else if ((tabShape == 5) || (tabShape == 10))
/*      */     {
/* 1083 */       g.setColor(this._shadow);
/*      */ 
/* 1085 */       if (contentInsets.top > 0) {
/* 1086 */         g.drawLine(x, y, x + w - 1, y);
/*      */       }
/* 1088 */       if (contentInsets.left > 0) {
/* 1089 */         g.drawLine(x, y, x, y + h - 1);
/*      */       }
/* 1091 */       if (contentInsets.bottom > 0) {
/* 1092 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/* 1094 */       g.drawLine(x + w - 1, y, x + w - 1, selRect.y);
/*      */ 
/* 1096 */       if ((!this._tabPane.isTabShown()) || (r.y >= viewRect.y + viewRect.height)) {
/* 1097 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*      */       }
/*      */       else
/* 1100 */         g.drawLine(x + w - 1, selRect.y + selRect.height, x + w - 1, y + h - 1);
/*      */     }
/*      */     else
/*      */     {
/* 1104 */       super.paintContentBorderRightEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintContentBorder(Graphics g, int x, int y, int w, int h)
/*      */   {
/* 1114 */     Insets insets = getContentBorderInsets(this._tabPane.getTabPlacement());
/*      */ 
/* 1116 */     JideTabbedPane.ColorProvider colorProvider = this._tabPane.getTabColorProvider();
/* 1117 */     boolean useDefault = true;
/* 1118 */     if (colorProvider != null) {
/* 1119 */       Color backgroundAt = this._tabPane.getBackground();
/* 1120 */       if (this._tabPane.getSelectedIndex() != -1)
/* 1121 */         backgroundAt = colorProvider.getBackgroundAt(this._tabPane.getSelectedIndex());
/* 1122 */       if (backgroundAt != null) {
/* 1123 */         g.setColor(backgroundAt);
/* 1124 */         g.fillRect(x, y, w, h);
/* 1125 */         useDefault = false;
/*      */       }
/*      */     }
/*      */ 
/* 1129 */     if (useDefault) {
/* 1130 */       Color[] colors = getGradientColors(this._tabPane.getSelectedIndex(), true);
/* 1131 */       if (colors != null) {
/* 1132 */         g.setColor(colors[1]);
/* 1133 */         g.fillRect(x, y, w, insets.top);
/* 1134 */         g.fillRect(x, y, insets.left, h);
/* 1135 */         g.fillRect(x, y + h - insets.bottom, w, insets.bottom);
/* 1136 */         g.fillRect(x + w - insets.right, y, insets.right, h);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected Color[] getGradientColors(int tabIndex, boolean isSelected) {
/* 1142 */     Color backgroundEnd = null;
/* 1143 */     Color backgroundStart = null;
/*      */ 
/* 1145 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/* 1146 */     if (highContrast.booleanValue()) {
/* 1147 */       backgroundStart = isSelected ? UIDefaultsLookup.getColor("JideButton.selectedBackground") : UIDefaultsLookup.getColor("JideButton.background");
/* 1148 */       backgroundEnd = backgroundStart;
/*      */     }
/*      */     else {
/* 1151 */       JideTabbedPane.ColorProvider colorProvider = this._tabPane.getTabColorProvider();
/* 1152 */       if (colorProvider != null) {
/* 1153 */         backgroundEnd = colorProvider.getBackgroundAt(tabIndex);
/* 1154 */         if ((colorProvider instanceof JideTabbedPane.GradientColorProvider)) {
/* 1155 */           backgroundStart = ((JideTabbedPane.GradientColorProvider)colorProvider).getTopBackgroundAt(tabIndex);
/*      */         }
/*      */         else
/* 1158 */           backgroundStart = backgroundEnd != null ? ColorUtils.getDerivedColor(backgroundEnd, colorProvider.getGradientRatio(tabIndex)) : null;
/*      */       }
/*      */       else
/*      */       {
/* 1162 */         Color color = this._tabPane.getBackground();
/* 1163 */         if (tabIndex != -1)
/* 1164 */           color = this._tabPane.getBackgroundAt(tabIndex);
/* 1165 */         if ((!(color instanceof UIResource)) && (color != this._tabPane.getBackground())) {
/* 1166 */           backgroundEnd = color;
/* 1167 */           if (getColorTheme() == 2) {
/* 1168 */             backgroundStart = ColorUtils.getDerivedColor(color, 0.8F);
/*      */           }
/*      */           else {
/* 1171 */             backgroundStart = color;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 1176 */       if (isSelected) {
/* 1177 */         if (showFocusIndicator()) {
/* 1178 */           if (backgroundEnd == null) {
/* 1179 */             backgroundEnd = this._backgroundSelectedColorEnd;
/*      */           }
/* 1181 */           if (backgroundStart == null) {
/* 1182 */             backgroundStart = this._backgroundSelectedColorStart;
/*      */           }
/*      */ 
/*      */         }
/* 1186 */         else if (getColorTheme() == 3) {
/* 1187 */           if (backgroundEnd == null) {
/* 1188 */             backgroundEnd = this._backgroundSelectedColorEnd;
/*      */           }
/* 1190 */           if (backgroundStart == null)
/* 1191 */             backgroundStart = this._backgroundSelectedColorStart;
/*      */         }
/*      */         else
/*      */         {
/* 1195 */           if (backgroundEnd == null) {
/* 1196 */             backgroundEnd = ColorUtils.getDerivedColor(this._backgroundUnselectedColorEnd, 0.7F);
/*      */           }
/* 1198 */           if (backgroundStart == null) {
/* 1199 */             backgroundStart = ColorUtils.getDerivedColor(this._backgroundUnselectedColorStart, 0.8F);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/* 1205 */       else if (getTabShape() != 11) {
/* 1206 */         if (backgroundEnd == null) {
/* 1207 */           backgroundEnd = this._backgroundUnselectedColorEnd;
/*      */         }
/* 1209 */         if (backgroundStart == null) {
/* 1210 */           backgroundStart = this._backgroundUnselectedColorStart;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1215 */     return new Color[] { backgroundStart, backgroundEnd };
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.vsnet.VsnetJideTabbedPaneUI
 * JD-Core Version:    0.6.0
 */
/*     */ package com.jidesoft.plaf.office2003;
/*     */ 
/*     */ import com.jidesoft.plaf.basic.BasicJideTabbedPaneUI.ScrollableTabSupport;
/*     */ import com.jidesoft.plaf.basic.BasicJideTabbedPaneUI.ScrollableTabViewport;
/*     */ import com.jidesoft.plaf.basic.ThemePainter;
/*     */ import com.jidesoft.plaf.vsnet.VsnetJideTabbedPaneUI;
/*     */ import com.jidesoft.swing.JideTabbedPane;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ 
/*     */ public class Office2003JideTabbedPaneUI extends VsnetJideTabbedPaneUI
/*     */ {
/*     */   public void installColorTheme()
/*     */   {
/*  20 */     super.installColorTheme();
/*     */ 
/*  22 */     if (this._tabPane.getColorTheme() != 2) {
/*  23 */       return;
/*     */     }
/*  25 */     switch (getTabShape()) {
/*     */     case 10:
/*  27 */       this._selectColor1 = getPainter().getControlShadow();
/*  28 */       this._selectColor2 = getPainter().getControlShadow();
/*  29 */       this._unselectColor1 = this._selectColor1;
/*  30 */       this._unselectColor2 = this._selectColor2;
/*  31 */       break;
/*     */     case 3:
/*  33 */       this._selectColor1 = getPainter().getControlShadow();
/*  34 */       this._selectColor2 = getPainter().getControlShadow();
/*  35 */       this._unselectColor1 = getPainter().getControlShadow();
/*  36 */       this._unselectColor2 = this._lightHighlight;
/*  37 */       break;
/*     */     case 8:
/*  39 */       this._selectColor1 = getPainter().getControlShadow();
/*  40 */       this._selectColor2 = null;
/*  41 */       this._selectColor3 = null;
/*  42 */       this._unselectColor1 = getPainter().getControlShadow();
/*  43 */       this._unselectColor2 = null;
/*  44 */       this._unselectColor3 = null;
/*  45 */       break;
/*     */     case 1:
/*     */     case 11:
/*  48 */       this._selectColor1 = this._lightHighlight;
/*  49 */       this._selectColor2 = getPainter().getControlDk();
/*  50 */       this._selectColor3 = getPainter().getControlShadow();
/*  51 */       this._unselectColor1 = this._selectColor1;
/*  52 */       this._unselectColor2 = this._selectColor2;
/*  53 */       this._unselectColor3 = this._selectColor3;
/*  54 */       break;
/*     */     case 2:
/*  56 */       this._selectColor1 = getPainter().getControlShadow();
/*  57 */       this._selectColor2 = getPainter().getControlShadow();
/*  58 */       this._unselectColor1 = getPainter().getControlShadow();
/*  59 */       break;
/*     */     case 4:
/*     */     case 5:
/*     */     case 6:
/*     */     case 7:
/*     */     case 9:
/*     */     default:
/*  62 */       this._selectColor1 = getPainter().getControlShadow();
/*  63 */       this._unselectColor1 = getPainter().getControlShadow();
/*  64 */       this._unselectColor2 = this._lightHighlight;
/*  65 */       this._unselectColor3 = getPainter().getControlDk();
/*     */     }
/*     */ 
/*  69 */     installBackgroundColor();
/*     */   }
/*     */ 
/*     */   protected void installBackgroundColor()
/*     */   {
/*  74 */     if (this._tabPane.getColorTheme() == 2) {
/*  75 */       if (this._showFocusIndicator) {
/*  76 */         this._backgroundSelectedColorStart = getPainter().getSelectionSelectedLt();
/*  77 */         this._backgroundSelectedColorEnd = getPainter().getSelectionSelectedDk();
/*     */       }
/*     */       else {
/*  80 */         this._backgroundSelectedColorStart = getPainter().getBackgroundLt();
/*  81 */         this._backgroundSelectedColorEnd = getPainter().getBackgroundDk();
/*     */       }
/*  83 */       if (getTabShape() == 3) {
/*  84 */         this._backgroundUnselectedColorStart = null;
/*  85 */         this._backgroundUnselectedColorEnd = null;
/*     */       }
/*     */       else {
/*  88 */         this._backgroundUnselectedColorStart = getPainter().getBackgroundLt();
/*  89 */         this._backgroundUnselectedColorEnd = getPainter().getBackgroundDk();
/*     */       }
/*     */     }
/*     */     else {
/*  93 */       super.installBackgroundColor();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintBackground(Graphics g, Component c)
/*     */   {
/* 100 */     if (this._tabPane.isOpaque()) {
/* 101 */       int width = c.getWidth();
/* 102 */       int height = c.getHeight();
/* 103 */       int h = 0;
/* 104 */       int w = 0;
/* 105 */       Graphics2D g2d = (Graphics2D)g;
/*     */ 
/* 107 */       if (this._tabPane.getTabCount() > 0) {
/* 108 */         h = this._tabPane.getBoundsAt(0).height;
/* 109 */         w = this._tabPane.getBoundsAt(0).width;
/*     */       }
/*     */       else {
/* 112 */         return;
/*     */       }
/*     */ 
/* 115 */       int temp1 = -1;
/* 116 */       int temp2 = -1;
/* 117 */       if (isTabLeadingComponentVisible()) {
/* 118 */         if (h < this._tabLeadingComponent.getSize().height) {
/* 119 */           h = this._tabLeadingComponent.getSize().height;
/* 120 */           temp1 = this._tabLeadingComponent.getSize().height;
/*     */         }
/* 122 */         if (w < this._tabLeadingComponent.getSize().width) {
/* 123 */           w = this._tabLeadingComponent.getSize().width;
/* 124 */           temp2 = this._tabLeadingComponent.getSize().width;
/*     */         }
/*     */       }
/*     */ 
/* 128 */       if (isTabTrailingComponentVisible()) {
/* 129 */         if ((h < this._tabTrailingComponent.getSize().height) && (temp1 < this._tabTrailingComponent.getSize().height)) {
/* 130 */           h = this._tabTrailingComponent.getSize().height;
/*     */         }
/* 132 */         if ((w < this._tabTrailingComponent.getSize().width) && (temp2 < this._tabTrailingComponent.getSize().width)) {
/* 133 */           w = this._tabTrailingComponent.getSize().width;
/*     */         }
/*     */       }
/*     */ 
/* 137 */       super.paintBackground(g, c);
/*     */ 
/* 139 */       Rectangle rect = null;
/* 140 */       if (this._tabPane.getTabPlacement() == 1) {
/* 141 */         rect = new Rectangle(0, 0, width, h + 2);
/*     */       }
/* 144 */       else if (this._tabPane.getTabPlacement() == 3) {
/* 145 */         rect = new Rectangle(0, height - h - 2, width, h + 2);
/*     */       }
/* 148 */       else if (this._tabPane.getTabPlacement() == 2) {
/* 149 */         rect = new Rectangle(0, 0, w + 2, height);
/*     */       }
/* 152 */       else if (this._tabPane.getTabPlacement() == 4) {
/* 153 */         rect = new Rectangle(width - w - 2, 0, w + 2, height);
/*     */       }
/* 155 */       if (rect != null)
/* 156 */         paintTabAreaBackground(g, rect, this._tabPane.getTabPlacement());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static ComponentUI createUI(JComponent c)
/*     */   {
/* 162 */     return new Office2003JideTabbedPaneUI();
/*     */   }
/*     */ 
/*     */   protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
/*     */   {
/* 172 */     if (getColorTheme() != 2) {
/* 173 */       super.paintContentBorderTopEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/* 174 */       return;
/*     */     }
/*     */ 
/* 177 */     if (selectedIndex < 0) {
/* 178 */       return;
/*     */     }
/*     */ 
/* 181 */     Rectangle selRect = getTabBounds(selectedIndex, this._calcRect);
/*     */ 
/* 183 */     Rectangle viewRect = this._tabScroller.viewport.getViewRect();
/* 184 */     Rectangle r = this._rects[selectedIndex];
/*     */ 
/* 186 */     int tabShape = getTabShape();
/*     */ 
/* 188 */     Insets contentInsets = getContentBorderInsets(tabPlacement);
/*     */ 
/* 190 */     if (this._tabPane.getColorTheme() == 2) {
/* 191 */       if (tabShape == 4) {
/* 192 */         g.setColor(getPainter().getControlShadow());
/*     */ 
/* 194 */         if ((this._alwaysShowLineBorder) || (this._tabPane.hasFocusComponent())) {
/* 195 */           if (contentInsets.left > 0) {
/* 196 */             g.drawLine(x, y, x, y + h - 1);
/*     */           }
/* 198 */           if (contentInsets.right > 0) {
/* 199 */             g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*     */           }
/* 201 */           if (contentInsets.bottom > 0) {
/* 202 */             g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*     */           }
/*     */ 
/* 205 */           if ((contentInsets.top > 0) && (this._tabPane.isTabShown())) {
/* 206 */             if (r.x >= viewRect.x + viewRect.width) {
/* 207 */               g.drawLine(x, y, x + w - 1, y);
/*     */             }
/* 210 */             else if (this._tabPane.getComponentOrientation().isLeftToRight()) {
/* 211 */               g.drawLine(x, y, selRect.x - selRect.height + 2, y);
/* 212 */               g.drawLine(selRect.x + selRect.width, y, x + w - 1, y);
/*     */             }
/*     */             else {
/* 215 */               g.drawLine(x, y, selRect.x, y);
/* 216 */               g.drawLine(selRect.x + selRect.width + selRect.height - 3, y, x + w - 1, y);
/*     */             }
/*     */ 
/* 220 */             Rectangle bounds = this._tabScroller.viewport.getBounds();
/* 221 */             g.drawLine(0, y, bounds.x, y);
/* 222 */             g.drawLine(bounds.x + bounds.width, y, x + w - 1, y);
/*     */           }
/* 224 */           else if ((contentInsets.top > 0) && (!this._tabPane.isTabShown())) {
/* 225 */             g.drawLine(x, y, x + w - 1, y);
/*     */           }
/*     */         }
/* 228 */         else if (this._tabPane.isTabShown()) {
/* 229 */           if (r.x >= viewRect.x + viewRect.width) {
/* 230 */             g.drawLine(x, y, x + w - 1, y);
/*     */           }
/* 233 */           else if (this._tabPane.getComponentOrientation().isLeftToRight()) {
/* 234 */             g.drawLine(x, y, selRect.x - selRect.height + 2, y);
/* 235 */             g.drawLine(selRect.x + selRect.width, y, x + w - 1, y);
/*     */           }
/*     */           else {
/* 238 */             g.drawLine(x, y, selRect.x, y);
/* 239 */             g.drawLine(selRect.x + selRect.width + selRect.height - 3, y, x + w - 1, y);
/*     */           }
/*     */ 
/* 243 */           Rectangle bounds = this._tabScroller.viewport.getBounds();
/* 244 */           g.drawLine(x, y, bounds.x, y);
/* 245 */           g.drawLine(bounds.x + bounds.width, y, x + w - 1, y);
/*     */         }
/*     */ 
/*     */       }
/* 249 */       else if (tabShape == 8) {
/* 250 */         g.setColor(getPainter().getControlShadow());
/*     */ 
/* 252 */         g.drawLine(x, y, selRect.x - selRect.height / 2 + 4, y);
/*     */ 
/* 254 */         if (contentInsets.left > 0) {
/* 255 */           g.drawLine(x, y, x, y + h - 1);
/*     */         }
/* 257 */         if (contentInsets.right > 0) {
/* 258 */           g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*     */         }
/* 260 */         if (contentInsets.bottom > 0) {
/* 261 */           g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*     */         }
/*     */ 
/* 265 */         if ((!this._tabPane.isTabShown()) || (r.x >= viewRect.x + viewRect.width)) {
/* 266 */           g.drawLine(x, y, x + w - 1, y);
/*     */         }
/* 269 */         else if ((!this._tabPane.isShowIconsOnTab()) && (!this._tabPane.isUseDefaultShowIconsOnTab()))
/*     */         {
/* 271 */           g.drawLine(selRect.x + selRect.width + selRect.height / 2 - 4, y, x + w - 1, y);
/*     */         }
/*     */         else
/*     */         {
/* 275 */           g.drawLine(selRect.x + selRect.width + selRect.height / 2 - 6, y, x + w - 1, y);
/*     */         }
/*     */ 
/*     */       }
/* 280 */       else if ((tabShape == 1) || (tabShape == 11))
/*     */       {
/* 282 */         g.setColor(getBorderEdgeColor());
/*     */ 
/* 284 */         if ((this._tabPane.getColorTheme() == 3) || (this._tabPane.getColorTheme() == 4))
/*     */         {
/* 286 */           g.drawLine(x, y, selRect.x - 2, y);
/*     */         }
/*     */         else {
/* 289 */           g.drawLine(x, y, selRect.x - 1, y);
/*     */         }
/*     */ 
/* 292 */         if (contentInsets.left > 0) {
/* 293 */           g.drawLine(x, y, x, y + h - 1);
/*     */         }
/*     */ 
/* 296 */         if ((!this._tabPane.isTabShown()) || (r.x >= viewRect.x + viewRect.width)) {
/* 297 */           g.setColor(this._lightHighlight);
/* 298 */           g.drawLine(x, y, x + w - 1, y);
/*     */         }
/*     */         else {
/* 301 */           g.setColor(this._lightHighlight);
/* 302 */           g.drawLine(selRect.x + selRect.width + 3, y, x + w - 1, y);
/*     */         }
/*     */ 
/* 306 */         g.setColor(getPainter().getControlDk());
/* 307 */         if (contentInsets.right > 0) {
/* 308 */           g.drawLine(x + w - 2, y + 1, x + w - 2, y + h - 2);
/*     */         }
/* 310 */         if (contentInsets.bottom > 0) {
/* 311 */           g.drawLine(x + 1, y + h - 2, x + w - 2, y + h - 2);
/*     */         }
/*     */ 
/* 314 */         g.setColor(getPainter().getControlShadow());
/* 315 */         if (contentInsets.right > 0) {
/* 316 */           g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 1);
/*     */         }
/* 318 */         if (contentInsets.bottom > 0)
/* 319 */           g.drawLine(x + 1, y + h - 1, x + w - 1, y + h - 1);
/*     */       }
/*     */       else
/*     */       {
/* 323 */         super.paintContentBorderTopEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/*     */       }
/*     */     }
/*     */     else
/* 327 */       super.paintContentBorderTopEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/*     */   }
/*     */ 
/*     */   protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
/*     */   {
/* 338 */     if (getColorTheme() != 2) {
/* 339 */       super.paintContentBorderBottomEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/* 340 */       return;
/*     */     }
/*     */ 
/* 343 */     if (selectedIndex < 0) {
/* 344 */       return;
/*     */     }
/*     */ 
/* 347 */     Rectangle selRect = getTabBounds(selectedIndex, this._calcRect);
/*     */ 
/* 349 */     Rectangle viewRect = this._tabScroller.viewport.getViewRect();
/* 350 */     Rectangle r = this._rects[selectedIndex];
/*     */ 
/* 352 */     int tabShape = getTabShape();
/*     */ 
/* 354 */     Insets contentInsets = getContentBorderInsets(tabPlacement);
/*     */ 
/* 356 */     if (tabShape == 4) {
/* 357 */       g.setColor(getPainter().getControlShadow());
/*     */ 
/* 359 */       if ((this._alwaysShowLineBorder) || (this._tabPane.hasFocusComponent())) {
/* 360 */         if (contentInsets.left > 0) {
/* 361 */           g.drawLine(x, y, x, y + h - 1);
/*     */         }
/* 363 */         if (contentInsets.right > 0) {
/* 364 */           g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*     */         }
/* 366 */         if (contentInsets.top > 0) {
/* 367 */           g.drawLine(x, y, x + w - 1, y);
/*     */         }
/*     */ 
/* 370 */         if ((contentInsets.bottom > 0) && (this._tabPane.isTabShown())) {
/* 371 */           if (r.x >= viewRect.x + viewRect.width) {
/* 372 */             g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*     */           }
/* 375 */           else if (this._tabPane.getComponentOrientation().isLeftToRight()) {
/* 376 */             g.drawLine(x, y + h - 1, selRect.x - selRect.height + 2, y + h - 1);
/* 377 */             g.drawLine(selRect.x + selRect.width, y + h - 1, x + w - 1, y + h - 1);
/*     */           }
/*     */           else {
/* 380 */             g.drawLine(x, y + h - 1, selRect.x, y + h - 1);
/* 381 */             g.drawLine(selRect.x + selRect.width + selRect.height - 3, y + h - 1, x + w - 1, y + h - 1);
/*     */           }
/*     */ 
/* 385 */           Rectangle bounds = this._tabScroller.viewport.getBounds();
/* 386 */           g.drawLine(x, y + h - 1, bounds.x, y + h - 1);
/* 387 */           g.drawLine(bounds.x + bounds.width, y + h - 1, x + w - 1, y + h - 1);
/*     */         }
/* 389 */         else if ((contentInsets.bottom > 0) && (!this._tabPane.isTabShown())) {
/* 390 */           g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*     */         }
/*     */       }
/* 393 */       else if (this._tabPane.isTabShown()) {
/* 394 */         if (r.x >= viewRect.x + viewRect.width) {
/* 395 */           g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*     */         }
/* 398 */         else if (this._tabPane.getComponentOrientation().isLeftToRight()) {
/* 399 */           g.drawLine(x, y + h - 1, selRect.x - selRect.height + 2, y + h - 1);
/* 400 */           g.drawLine(selRect.x + selRect.width, y + h - 1, x + w - 1, y + h - 1);
/*     */         }
/*     */         else {
/* 403 */           g.drawLine(x, y + h - 1, selRect.x, y + h - 1);
/* 404 */           g.drawLine(selRect.x + selRect.width + selRect.height - 3, y + h - 1, x + w - 1, y + h - 1);
/*     */         }
/*     */ 
/* 408 */         Rectangle bounds = this._tabScroller.viewport.getBounds();
/* 409 */         g.drawLine(x, y + h - 1, bounds.x, y + h - 1);
/* 410 */         g.drawLine(bounds.x + bounds.width, y + h - 1, x + w - 1, y + h - 1);
/*     */       }
/*     */     }
/* 413 */     else if (tabShape == 8) {
/* 414 */       g.setColor(getPainter().getControlShadow());
/*     */ 
/* 416 */       g.drawLine(x, y + h - 1, selRect.x - selRect.height / 2 + 4, y + h - 1);
/*     */ 
/* 418 */       if (contentInsets.left > 0) {
/* 419 */         g.drawLine(x, y, x, y + h - 1);
/*     */       }
/* 421 */       if (contentInsets.right > 0) {
/* 422 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*     */       }
/* 424 */       if (contentInsets.top > 0) {
/* 425 */         g.drawLine(x, y, x + w - 1, y);
/*     */       }
/*     */ 
/* 428 */       if ((!this._tabPane.isTabShown()) || (r.x >= viewRect.x + viewRect.width)) {
/* 429 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*     */       }
/* 432 */       else if ((!this._tabPane.isShowIconsOnTab()) && (!this._tabPane.isUseDefaultShowIconsOnTab()))
/*     */       {
/* 434 */         g.drawLine(selRect.x + selRect.width + selRect.height / 2 - 4, y + h - 1, x + w - 1, y + h - 1);
/*     */       }
/*     */       else
/*     */       {
/* 438 */         g.drawLine(selRect.x + selRect.width + selRect.height / 2 - 6, y + h - 1, x + w - 1, y + h - 1);
/*     */       }
/*     */ 
/*     */     }
/* 443 */     else if ((tabShape == 1) || (tabShape == 11))
/*     */     {
/* 445 */       g.setColor(getPainter().getControlShadow());
/* 446 */       g.drawLine(x + 1, y + h - 1, selRect.x - 2, y + h - 1);
/* 447 */       if (contentInsets.right > 0) {
/* 448 */         g.drawLine(x + w - 1, y + h - 1, x + w - 1, y + 1);
/*     */       }
/*     */ 
/* 451 */       if ((!this._tabPane.isTabShown()) || (r.x >= viewRect.x + viewRect.width)) {
/* 452 */         g.setColor(getPainter().getControlShadow());
/* 453 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*     */       }
/*     */       else {
/* 456 */         g.setColor(getPainter().getControlShadow());
/* 457 */         g.drawLine(selRect.x + selRect.width, y + h - 1, x + w - 1, y + h - 1);
/* 458 */         g.setColor(getPainter().getControlDk());
/* 459 */         g.drawLine(selRect.x + selRect.width, y + h - 2, x + w - 2, y + h - 2);
/*     */ 
/* 461 */         g.setColor(getPainter().getControlDk());
/* 462 */         g.drawLine(x + 1, y + h - 2, selRect.x - 2, y + h - 2);
/* 463 */         if (contentInsets.right > 0) {
/* 464 */           g.drawLine(x + w - 2, y + 1, x + w - 2, y + h - 2);
/*     */         }
/* 466 */         g.drawLine(selRect.x + selRect.width - 1, y + h - 1, selRect.x + selRect.width - 1, y + h - 1);
/*     */ 
/* 472 */         g.setColor(new Color(255, 255, 255));
/* 473 */         g.drawLine(selRect.x - 1, y + h - 1, selRect.x - 1, y + h - 1);
/* 474 */         g.drawLine(selRect.x - 1, y + h - 2, selRect.x - 1, y + h - 2);
/*     */ 
/* 476 */         if (contentInsets.left > 0) {
/* 477 */           g.drawLine(x, y, x, y + h - 2);
/*     */         }
/* 479 */         if (contentInsets.top > 0) {
/* 480 */           g.drawLine(x, y, x + w - 2, y);
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 486 */       super.paintContentBorderBottomEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
/*     */   {
/* 498 */     if (getColorTheme() != 2) {
/* 499 */       super.paintContentBorderLeftEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/* 500 */       return;
/*     */     }
/*     */ 
/* 503 */     if (selectedIndex < 0) {
/* 504 */       return;
/*     */     }
/*     */ 
/* 507 */     Rectangle selRect = getTabBounds(selectedIndex, this._calcRect);
/*     */ 
/* 509 */     Rectangle viewRect = this._tabScroller.viewport.getViewRect();
/* 510 */     Rectangle r = this._rects[selectedIndex];
/*     */ 
/* 512 */     int tabShape = getTabShape();
/*     */ 
/* 514 */     Insets contentInsets = getContentBorderInsets(tabPlacement);
/*     */ 
/* 516 */     if (tabShape == 4) {
/* 517 */       g.setColor(getPainter().getControlShadow());
/*     */ 
/* 519 */       if ((this._alwaysShowLineBorder) || (this._tabPane.hasFocusComponent())) {
/* 520 */         if (contentInsets.top > 0) {
/* 521 */           g.drawLine(x, y, x + w - 1, y);
/*     */         }
/* 523 */         if (contentInsets.right > 0) {
/* 524 */           g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*     */         }
/* 526 */         if (contentInsets.bottom > 0) {
/* 527 */           g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*     */         }
/*     */ 
/* 530 */         if ((contentInsets.left > 0) && (this._tabPane.isTabShown())) {
/* 531 */           if (r.y >= viewRect.y + viewRect.height) {
/* 532 */             g.drawLine(x, y, x, y + h - 1);
/*     */           }
/*     */           else {
/* 535 */             g.drawLine(x, y, x, selRect.y - selRect.width + 2);
/* 536 */             g.drawLine(x, selRect.y + selRect.height, x, y + h - 1);
/*     */           }
/*     */ 
/* 539 */           Rectangle bounds = this._tabScroller.viewport.getBounds();
/* 540 */           g.drawLine(x, 0, x, bounds.y);
/* 541 */           g.drawLine(x, bounds.y + bounds.height, x, y + h - 1);
/*     */         }
/* 543 */         else if ((contentInsets.left > 0) && (!this._tabPane.isTabShown())) {
/* 544 */           g.drawLine(x, y, x, y + h - 1);
/*     */         }
/*     */       }
/* 547 */       else if (this._tabPane.isTabShown()) {
/* 548 */         if (r.y >= viewRect.y + viewRect.height) {
/* 549 */           g.drawLine(x, y, x, y + h - 1);
/*     */         }
/*     */         else {
/* 552 */           g.drawLine(x, y, x, selRect.y - selRect.width + 2);
/* 553 */           g.drawLine(x, selRect.y + selRect.height, x, y + h - 1);
/*     */         }
/*     */ 
/* 556 */         Rectangle bounds = this._tabScroller.viewport.getBounds();
/* 557 */         g.drawLine(x, y, x, bounds.y);
/* 558 */         g.drawLine(x, bounds.y + bounds.height, x, y + h - 1);
/*     */       }
/*     */     }
/* 561 */     else if (tabShape == 8) {
/* 562 */       g.setColor(getPainter().getControlShadow());
/*     */ 
/* 564 */       g.drawLine(x, y, x, selRect.y - selRect.width / 2 + 4);
/*     */ 
/* 566 */       if (contentInsets.top > 0) {
/* 567 */         g.drawLine(x, y, x + w - 1, y);
/*     */       }
/* 569 */       if (contentInsets.right > 0) {
/* 570 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*     */       }
/* 572 */       if (contentInsets.bottom > 0) {
/* 573 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*     */       }
/*     */ 
/* 576 */       if ((!this._tabPane.isTabShown()) || (r.y >= viewRect.y + viewRect.height)) {
/* 577 */         g.drawLine(x, y, x, y + h - 1);
/*     */       }
/*     */       else {
/* 580 */         g.drawLine(x, selRect.y + selRect.height + selRect.width / 2 - 4, x, y + h - 1);
/*     */       }
/*     */ 
/*     */     }
/* 584 */     else if ((tabShape == 1) || (tabShape == 11))
/*     */     {
/* 586 */       g.setColor(getPainter().getControlShadow());
/* 587 */       if (contentInsets.right > 0) {
/* 588 */         g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 1);
/*     */       }
/* 590 */       if (contentInsets.bottom > 0) {
/* 591 */         g.drawLine(x + 1, y + h - 1, x + w - 1, y + h - 1);
/*     */       }
/*     */ 
/* 594 */       g.setColor(getPainter().getControlDk());
/* 595 */       g.drawLine(x + w - 2, y + 1, x + w - 2, y + h - 2);
/* 596 */       g.drawLine(x + 1, y + h - 2, x + w - 2, y + h - 2);
/*     */ 
/* 598 */       g.setColor(new Color(255, 255, 255));
/* 599 */       if (contentInsets.top > 0) {
/* 600 */         g.drawLine(x, y, x + w - 2, y);
/*     */       }
/* 602 */       g.drawLine(x, y, x, selRect.y - 1);
/*     */ 
/* 604 */       if ((!this._tabPane.isTabShown()) || (r.y >= viewRect.y + viewRect.height)) {
/* 605 */         g.drawLine(x, y, x, y + h - 2);
/*     */       }
/*     */       else
/* 608 */         g.drawLine(x, selRect.y + selRect.height + 1, x, y + h - 2);
/*     */     }
/*     */     else
/*     */     {
/* 612 */       super.paintContentBorderLeftEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void paintContentBorderRightEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
/*     */   {
/* 623 */     if (getColorTheme() != 2) {
/* 624 */       super.paintContentBorderRightEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/* 625 */       return;
/*     */     }
/*     */ 
/* 628 */     if (selectedIndex < 0) {
/* 629 */       return;
/*     */     }
/*     */ 
/* 632 */     Rectangle selRect = getTabBounds(selectedIndex, this._calcRect);
/*     */ 
/* 634 */     Rectangle viewRect = this._tabScroller.viewport.getViewRect();
/* 635 */     Rectangle r = this._rects[selectedIndex];
/*     */ 
/* 637 */     int tabShape = getTabShape();
/*     */ 
/* 639 */     Insets contentInsets = getContentBorderInsets(tabPlacement);
/*     */ 
/* 641 */     if (tabShape == 4) {
/* 642 */       g.setColor(getPainter().getControlShadow());
/*     */ 
/* 644 */       if ((this._alwaysShowLineBorder) || (this._tabPane.hasFocusComponent())) {
/* 645 */         if (contentInsets.top > 0) {
/* 646 */           g.drawLine(x, y, x + w - 1, y);
/*     */         }
/* 648 */         if (contentInsets.left > 0) {
/* 649 */           g.drawLine(x, y, x, y + h - 1);
/*     */         }
/* 651 */         if (contentInsets.bottom > 0) {
/* 652 */           g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*     */         }
/*     */ 
/* 655 */         if ((contentInsets.right > 0) && (this._tabPane.isTabShown())) {
/* 656 */           if (r.y >= viewRect.y + viewRect.height) {
/* 657 */             g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*     */           }
/*     */           else {
/* 660 */             g.drawLine(x + w - 1, y, x + w - 1, selRect.y - selRect.width + 2);
/* 661 */             g.drawLine(x + w - 1, selRect.y + selRect.height, x + w - 1, y + h - 1);
/*     */           }
/*     */ 
/* 664 */           Rectangle bounds = this._tabScroller.viewport.getBounds();
/* 665 */           g.drawLine(x + w - 1, 0, x + w - 1, bounds.y);
/* 666 */           g.drawLine(x + w - 1, bounds.y + bounds.height, x + w - 1, y + h - 1);
/*     */         }
/* 668 */         else if ((contentInsets.right > 0) && (!this._tabPane.isTabShown())) {
/* 669 */           g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*     */         }
/*     */       }
/* 672 */       else if (this._tabPane.isTabShown()) {
/* 673 */         if (r.y >= viewRect.y + viewRect.height) {
/* 674 */           g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*     */         }
/*     */         else {
/* 677 */           g.drawLine(x + w - 1, y, x + w - 1, selRect.y - selRect.width + 2);
/* 678 */           g.drawLine(x + w - 1, selRect.y + selRect.height, x + w - 1, y + h - 1);
/*     */         }
/*     */ 
/* 681 */         Rectangle bounds = this._tabScroller.viewport.getBounds();
/* 682 */         g.drawLine(x + w - 1, y, x + w - 1, bounds.y);
/* 683 */         g.drawLine(x + w - 1, bounds.y + bounds.height, x + w - 1, y + h - 1);
/*     */       }
/*     */     }
/* 686 */     else if (tabShape == 8) {
/* 687 */       g.setColor(getPainter().getControlShadow());
/*     */ 
/* 689 */       g.drawLine(x + w - 1, y, x + w - 1, selRect.y - selRect.width / 2 + 4);
/*     */ 
/* 691 */       if (contentInsets.top > 0) {
/* 692 */         g.drawLine(x, y, x + w - 1, y);
/*     */       }
/* 694 */       if (contentInsets.left > 0) {
/* 695 */         g.drawLine(x, y, x, y + h - 1);
/*     */       }
/* 697 */       if (contentInsets.bottom > 0) {
/* 698 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*     */       }
/*     */ 
/* 701 */       if ((!this._tabPane.isTabShown()) || (r.y >= viewRect.y + viewRect.height)) {
/* 702 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*     */       }
/*     */       else {
/* 705 */         g.drawLine(x + w - 1, selRect.y + selRect.height + selRect.width / 2 - 4, x + w - 1, y + h - 1);
/*     */       }
/*     */     }
/* 708 */     else if ((tabShape == 1) || (tabShape == 11))
/*     */     {
/* 710 */       g.setColor(getPainter().getControlDk());
/* 711 */       g.drawLine(x + w - 2, y + 1, x + w - 2, selRect.y - 2);
/* 712 */       g.drawLine(x + 1, y + h - 2, x + w - 2, y + h - 2);
/*     */ 
/* 714 */       g.setColor(getPainter().getControlShadow());
/* 715 */       g.drawLine(x + w - 1, y + 1, x + w - 1, selRect.y - 2);
/* 716 */       if (contentInsets.bottom > 0) {
/* 717 */         g.drawLine(x + 1, y + h - 1, x + w - 1, y + h - 1);
/*     */       }
/*     */ 
/* 720 */       if ((!this._tabPane.isTabShown()) || (r.y >= viewRect.y + viewRect.height)) {
/* 721 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/* 722 */         g.setColor(getPainter().getControlDk());
/* 723 */         g.drawLine(x + w - 2, y + 1, x + w - 2, y + h - 2);
/*     */       }
/*     */       else {
/* 726 */         g.drawLine(x + w - 1, selRect.y + selRect.height + 1, x + w - 1, y + h - 1);
/* 727 */         g.setColor(getPainter().getControlDk());
/* 728 */         g.drawLine(x + w - 2, selRect.y + selRect.height + 2, x + w - 2, y + h - 2);
/* 729 */         g.drawLine(x + w - 1, selRect.y + selRect.height, x + w - 1, selRect.y + selRect.height);
/*     */       }
/*     */ 
/* 732 */       g.setColor(new Color(255, 255, 255));
/* 733 */       if (contentInsets.top > 0) {
/* 734 */         g.drawLine(x, y, x + w - 1, y);
/*     */       }
/* 736 */       if (contentInsets.left > 0)
/* 737 */         g.drawLine(x, y, x, y + h - 1);
/*     */     }
/*     */     else
/*     */     {
/* 741 */       super.paintContentBorderRightEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.office2003.Office2003JideTabbedPaneUI
 * JD-Core Version:    0.6.0
 */
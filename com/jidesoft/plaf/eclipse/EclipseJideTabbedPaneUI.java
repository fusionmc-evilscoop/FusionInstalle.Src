/*     */ package com.jidesoft.plaf.eclipse;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.plaf.vsnet.VsnetJideTabbedPaneUI;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import com.jidesoft.swing.JideTabbedPane;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ 
/*     */ public class EclipseJideTabbedPaneUI extends VsnetJideTabbedPaneUI
/*     */ {
/*     */   public static ComponentUI createUI(JComponent c)
/*     */   {
/*  25 */     return new EclipseJideTabbedPaneUI();
/*     */   }
/*     */ 
/*     */   protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*     */   {
/*  35 */     if (getTabShape() == 6) {
/*  36 */       Color old = g.getColor();
/*     */ 
/*  38 */       boolean leftToRight = this._tabPane.getComponentOrientation().isLeftToRight();
/*     */ 
/*  40 */       switch (tabPlacement) {
/*     */       case 2:
/*  42 */         if (isSelected) {
/*  43 */           g.setColor(this._lightHighlight);
/*  44 */           g.drawRect(x + 1, y + 1, w - 1, h - 3);
/*     */ 
/*  46 */           g.setColor(this._shadow);
/*  47 */           g.drawLine(x + 1, y, x + w - 1, y);
/*  48 */           g.drawLine(x + 1, y + h - 1, x + w - 1, y + h - 1);
/*  49 */           g.drawLine(x, y + 1, x, y + 1);
/*  50 */           g.drawLine(x, y + h - 2, x, y + h - 2);
/*     */         }
/*     */         else {
/*  53 */           g.setColor(this._shadow);
/*  54 */           if (tabIndex > this._tabPane.getSelectedIndex()) {
/*  55 */             if (tabIndex == this._tabPane.getTabCount() - 1) {
/*  56 */               g.drawLine(x, y + h - 1, x + w, y + h - 1);
/*     */             }
/*     */             else {
/*  59 */               g.drawLine(x, y + h - 1, x + w / 2, y + h - 1);
/*     */             }
/*     */           }
/*  62 */           else if (tabIndex < this._tabPane.getSelectedIndex()) {
/*  63 */             if (tabIndex != 0) {
/*  64 */               g.drawLine(x, y, x + w / 2, y);
/*     */             }
/*  67 */             else if (isTabLeadingComponentVisible()) {
/*  68 */               g.drawLine(x, y, x + w, y);
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*  74 */         if (!isTabTopVisible(tabPlacement)) break;
/*  75 */         g.setColor(this._shadow);
/*  76 */         g.drawLine(x, y, x, y + h - 1); break;
/*     */       case 4:
/*  80 */         if (isSelected) {
/*  81 */           g.setColor(this._lightHighlight);
/*  82 */           g.drawRect(x, y + 1, w - 1, h - 3);
/*     */ 
/*  84 */           g.setColor(this._shadow);
/*  85 */           g.drawLine(x, y, x + w - 1, y);
/*  86 */           g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*  87 */           g.drawLine(x + w, y + 1, x + w, y + 1);
/*  88 */           g.drawLine(x + w, y + h - 2, x + w, y + h - 2);
/*     */         }
/*     */         else {
/*  91 */           g.setColor(this._shadow);
/*  92 */           if (tabIndex > this._tabPane.getSelectedIndex()) {
/*  93 */             if (tabIndex == this._tabPane.getTabCount() - 1) {
/*  94 */               g.drawLine(x, y + h - 1, x + w, y + h - 1);
/*     */             }
/*     */             else {
/*  97 */               g.drawLine(x + w / 2, y + h - 1, x + w, y + h - 1);
/*     */             }
/*     */           }
/* 100 */           else if (tabIndex < this._tabPane.getSelectedIndex()) {
/* 101 */             if (tabIndex != 0) {
/* 102 */               g.drawLine(x + w / 2, y, x + w, y);
/*     */             }
/* 105 */             else if (isTabLeadingComponentVisible()) {
/* 106 */               g.drawLine(x, y, x + w, y);
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 112 */         if (!isTabTopVisible(tabPlacement)) break;
/* 113 */         g.setColor(this._shadow);
/* 114 */         g.drawLine(x + w, y, x + w, y + h - 1); break;
/*     */       case 3:
/* 118 */         if (isSelected) {
/* 119 */           g.setColor(this._lightHighlight);
/* 120 */           g.drawRect(x + 1, y, w - 3, h - 1);
/*     */ 
/* 122 */           g.setColor(this._shadow);
/* 123 */           g.drawLine(x, y, x, y + h - 1);
/* 124 */           g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/* 125 */           g.drawLine(x + 1, y + h, x + 1, y + h);
/* 126 */           g.drawLine(x + w - 2, y + h, x + w - 2, y + h);
/*     */         }
/*     */         else {
/* 129 */           g.setColor(this._shadow);
/* 130 */           if (leftToRight) {
/* 131 */             if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 132 */               if (tabIndex == this._tabPane.getTabCount() - 1) {
/* 133 */                 g.drawLine(x + w - 1, y, x + w - 1, y + h);
/*     */               }
/*     */               else {
/* 136 */                 g.drawLine(x + w - 1, y + h / 2, x + w - 1, y + h);
/*     */               }
/*     */             }
/* 139 */             else if (tabIndex < this._tabPane.getSelectedIndex()) {
/* 140 */               if (tabIndex != 0) {
/* 141 */                 g.drawLine(x, y + h / 2, x, y + h);
/*     */               }
/* 144 */               else if (isTabLeadingComponentVisible()) {
/* 145 */                 g.drawLine(x, y, x, y + h);
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/*     */           }
/* 151 */           else if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 152 */             if (tabIndex == this._tabPane.getTabCount() - 1) {
/* 153 */               g.drawLine(x, y, x, y + h);
/*     */             }
/*     */             else {
/* 156 */               g.drawLine(x, y + h / 2, x, y + h);
/*     */             }
/*     */           }
/* 159 */           else if (tabIndex < this._tabPane.getSelectedIndex()) {
/* 160 */             if (tabIndex != 0) {
/* 161 */               g.drawLine(x + w - 1, y + h / 2, x + w - 1, y + h);
/*     */             }
/* 164 */             else if (isTabLeadingComponentVisible()) {
/* 165 */               g.drawLine(x + w - 1, y, x + w - 1, y + h);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 173 */         if (!isTabTopVisible(tabPlacement)) break;
/* 174 */         g.setColor(this._shadow);
/* 175 */         g.drawLine(x, y + h, x + w - 1, y + h); break;
/*     */       case 1:
/*     */       default:
/* 180 */         if (isSelected) {
/* 181 */           g.setColor(this._lightHighlight);
/* 182 */           g.drawRect(x + 1, y + 1, w - 3, h);
/*     */ 
/* 184 */           g.setColor(this._shadow);
/* 185 */           g.drawLine(x, y + 1, x, y + h - 1);
/* 186 */           g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 1);
/* 187 */           g.drawLine(x + 1, y, x + 1, y);
/* 188 */           g.drawLine(x + w - 2, y, x + w - 2, y);
/*     */         }
/*     */         else {
/* 191 */           g.setColor(this._shadow);
/* 192 */           if (leftToRight) {
/* 193 */             if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 194 */               if (tabIndex == this._tabPane.getTabCount() - 1) {
/* 195 */                 g.drawLine(x + w - 1, y, x + w - 1, y + h);
/*     */               }
/*     */               else {
/* 198 */                 g.drawLine(x + w - 1, y, x + w - 1, y + h / 2);
/*     */               }
/*     */             }
/* 201 */             else if (tabIndex < this._tabPane.getSelectedIndex()) {
/* 202 */               if (tabIndex != 0) {
/* 203 */                 g.drawLine(x, y, x, y + h / 2);
/*     */               }
/* 206 */               else if (isTabLeadingComponentVisible()) {
/* 207 */                 g.drawLine(x, y, x, y + h);
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/*     */           }
/* 213 */           else if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 214 */             if (tabIndex == this._tabPane.getTabCount() - 1) {
/* 215 */               g.drawLine(x, y, x, y + h);
/*     */             }
/*     */             else {
/* 218 */               g.drawLine(x, y, x, y + h / 2);
/*     */             }
/*     */           }
/* 221 */           else if (tabIndex < this._tabPane.getSelectedIndex()) {
/* 222 */             if (tabIndex != 0) {
/* 223 */               g.drawLine(x + w - 1, y, x + w - 1, y + h / 2);
/*     */             }
/* 226 */             else if (isTabLeadingComponentVisible()) {
/* 227 */               g.drawLine(x + w - 1, y, x + w - 1, y + h);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 234 */         if (!isTabTopVisible(tabPlacement)) break;
/* 235 */         g.setColor(this._shadow);
/* 236 */         g.drawLine(x, y, x + w - 1, y);
/*     */       }
/*     */ 
/* 240 */       g.setColor(old);
/*     */     }
/*     */     else
/*     */     {
/* 244 */       super.paintTabBorder(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex, Component c)
/*     */   {
/* 250 */     if (getTabShape() == 6) {
/* 251 */       int tabCount = this._tabPane.getTabCount();
/*     */ 
/* 253 */       Rectangle iconRect = new Rectangle();
/* 254 */       Rectangle textRect = new Rectangle();
/* 255 */       Rectangle clipRect = g.getClipBounds();
/*     */ 
/* 257 */       g.setColor(this._tabBackground);
/* 258 */       g.fillRect(clipRect.x, clipRect.y, clipRect.width, clipRect.height);
/*     */ 
/* 261 */       for (int i = this._runCount - 1; i >= 0; i--) {
/* 262 */         int start = this._tabRuns[i];
/* 263 */         int next = this._tabRuns[(i + 1)];
/* 264 */         int end = next != 0 ? next - 1 : tabCount - 1;
/* 265 */         for (int j = start; j <= end; j++) {
/* 266 */           if (this._rects[j].intersects(clipRect)) {
/* 267 */             paintTab(g, tabPlacement, this._rects, j, iconRect, textRect);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 274 */       if ((selectedIndex >= 0) && (getRunForTab(tabCount, selectedIndex) == 0) && 
/* 275 */         (this._rects[selectedIndex].intersects(clipRect))) {
/* 276 */         paintTab(g, tabPlacement, this._rects, selectedIndex, iconRect, textRect);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 281 */       super.paintTabArea(g, tabPlacement, selectedIndex, c);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
/*     */   {
/* 287 */     if (getTabShape() == 6) {
/* 288 */       if (this._tabPane.isTabShown()) {
/* 289 */         g.setColor(this._shadow);
/* 290 */         g.drawLine(x, y, x + w - 1, y);
/*     */       }
/*     */     }
/*     */     else
/* 294 */       super.paintContentBorderTopEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/*     */   }
/*     */ 
/*     */   protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
/*     */   {
/* 300 */     if (getTabShape() == 6) {
/* 301 */       if (this._tabPane.isTabShown()) {
/* 302 */         g.setColor(this._shadow);
/* 303 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*     */       }
/*     */     }
/*     */     else
/* 307 */       super.paintContentBorderBottomEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/*     */   }
/*     */ 
/*     */   protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
/*     */   {
/* 313 */     if (getTabShape() == 6) {
/* 314 */       if (this._tabPane.isTabShown()) {
/* 315 */         g.setColor(this._shadow);
/* 316 */         g.drawLine(x, y, x, y + h - 1);
/*     */       }
/*     */     }
/*     */     else
/* 320 */       super.paintContentBorderLeftEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/*     */   }
/*     */ 
/*     */   protected void paintContentBorderRightEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
/*     */   {
/* 326 */     if (getTabShape() == 6) {
/* 327 */       if (this._tabPane.isTabShown()) {
/* 328 */         g.setColor(this._shadow);
/* 329 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*     */       }
/*     */     }
/*     */     else
/* 333 */       super.paintContentBorderRightEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/*     */   }
/*     */ 
/*     */   protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*     */   {
/* 339 */     if (getTabShape() == 6) {
/* 340 */       this._tabPane.setBackgroundAt(tabIndex, this._tabBackground);
/*     */ 
/* 342 */       if (isSelected)
/*     */       {
/*     */         Color background2;
/*     */         Color background1;
/*     */         Color background2;
/* 345 */         if (showFocusIndicator()) {
/* 346 */           Color background1 = UIDefaultsLookup.getColor("DockableFrame.activeTitleBackground");
/* 347 */           background2 = UIDefaultsLookup.getColor("DockableFrame.activeTitleBackground2");
/*     */         }
/*     */         else {
/* 350 */           background1 = Color.WHITE;
/* 351 */           background2 = this._background;
/*     */         }
/*     */ 
/* 354 */         Graphics2D g2d = (Graphics2D)g;
/* 355 */         int buttonSize = 16;
/* 356 */         int restWidth = w - (isShowCloseButtonOnTab() ? buttonSize : 0) - 3;
/* 357 */         int restHeight = h - (isShowCloseButtonOnTab() ? buttonSize : 0) - 3;
/* 358 */         switch (tabPlacement) {
/*     */         case 2:
/* 360 */           JideSwingUtilities.fillGradient(g2d, new Rectangle(x, y + 1, w, restHeight >> 1), background1, background2, true);
/* 361 */           break;
/*     */         case 4:
/* 363 */           JideSwingUtilities.fillGradient(g2d, new Rectangle(x, y + 1, w - 1, restHeight >> 1), background1, background2, true);
/* 364 */           break;
/*     */         case 3:
/* 366 */           JideSwingUtilities.fillGradient(g2d, new Rectangle(x + 1, y, restWidth >> 1, h - 1), background1, background2, false);
/* 367 */           break;
/*     */         case 1:
/*     */         default:
/* 370 */           JideSwingUtilities.fillGradient(g2d, new Rectangle(x + 1, y + 1, restWidth >> 1, h), background1, background2, false);
/*     */         }
/*     */ 
/* 373 */         switch (tabPlacement) {
/*     */         case 2:
/* 375 */           JideSwingUtilities.fillGradient(g2d, new Rectangle(x, y + 1 + (restHeight >> 1), w, restHeight >> 1), background2, this._tabBackground, true);
/* 376 */           break;
/*     */         case 4:
/* 378 */           JideSwingUtilities.fillGradient(g2d, new Rectangle(x, y + 1 + (restHeight >> 1), w - 1, restHeight >> 1), background2, this._tabBackground, true);
/* 379 */           break;
/*     */         case 3:
/* 381 */           JideSwingUtilities.fillGradient(g2d, new Rectangle(x + 1 + (restWidth >> 1), y, restWidth >> 1, h - 1), background2, this._tabBackground, false);
/* 382 */           break;
/*     */         case 1:
/*     */         default:
/* 385 */           JideSwingUtilities.fillGradient(g2d, new Rectangle(x + 1 + (restWidth >> 1), y + 1, restWidth >> 1, h), background2, this._tabBackground, false);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 390 */         super.paintTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
/*     */       }
/*     */     }
/*     */     else {
/* 394 */       super.paintTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.eclipse.EclipseJideTabbedPaneUI
 * JD-Core Version:    0.6.0
 */
/*     */ package com.jidesoft.plaf.basic;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.swing.ComponentStateSupport;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import com.jidesoft.swing.JideTabbedPane;
/*     */ import com.jidesoft.utils.ColorUtils;
/*     */ import com.jidesoft.utils.SecurityUtils;
/*     */ import com.jidesoft.utils.SystemInfo;
/*     */ import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
/*     */ import java.awt.Color;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.SwingConstants;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.synth.Region;
/*     */ import javax.swing.plaf.synth.SynthContext;
/*     */ import javax.swing.plaf.synth.SynthLookAndFeel;
/*     */ import sun.swing.plaf.synth.SynthIcon;
/*     */ 
/*     */ public class BasicPainter
/*     */   implements SwingConstants, ThemePainter
/*     */ {
/*     */   private static BasicPainter _instance;
/*     */   protected Color _bk0;
/*     */   protected Color _bk1;
/*     */   protected Color _bk2;
/*     */   protected Color _bk3;
/*     */   protected Color _borderColor;
/* 820 */   public static int V_GAP = 0;
/*     */ 
/* 825 */   public static int H_GAP = 5;
/*     */ 
/* 830 */   public static int ARROW_TEXT_GAP = 0;
/*     */ 
/*     */   public static ThemePainter getInstance()
/*     */   {
/*  29 */     if (_instance == null) {
/*  30 */       _instance = new BasicPainter();
/*     */     }
/*  32 */     return _instance;
/*     */   }
/*     */ 
/*     */   public void installDefaults()
/*     */   {
/*  45 */     if (this._bk0 == null) {
/*  46 */       this._bk0 = UIDefaultsLookup.getColor("JideButton.background");
/*     */     }
/*  48 */     if (this._bk1 == null) {
/*  49 */       this._bk1 = UIDefaultsLookup.getColor("JideButton.focusedBackground");
/*     */     }
/*  51 */     if (this._bk2 == null) {
/*  52 */       this._bk2 = UIDefaultsLookup.getColor("JideButton.selectedBackground");
/*     */     }
/*  54 */     if (this._bk3 == null) {
/*  55 */       this._bk3 = UIDefaultsLookup.getColor("JideButton.selectedAndFocusedBackground");
/*     */     }
/*  57 */     if (this._borderColor == null)
/*  58 */       this._borderColor = UIDefaultsLookup.getColor("JideButton.borderColor");
/*     */   }
/*     */ 
/*     */   public void uninstallDefaults()
/*     */   {
/*  63 */     this._borderColor = null;
/*  64 */     this._bk0 = null;
/*  65 */     this._bk1 = null;
/*  66 */     this._bk2 = null;
/*  67 */     this._bk3 = null;
/*     */   }
/*     */ 
/*     */   public Color getGripperForeground() {
/*  71 */     return UIDefaultsLookup.getColor("Gripper.foreground");
/*     */   }
/*     */ 
/*     */   public Color getGripperForegroundLt() {
/*  75 */     return UIDefaultsLookup.getColor("JideButton.highlight");
/*     */   }
/*     */ 
/*     */   public Color getSeparatorForeground() {
/*  79 */     return UIDefaultsLookup.getColor("JideButton.shadow");
/*     */   }
/*     */ 
/*     */   public Color getSeparatorForegroundLt() {
/*  83 */     return UIDefaultsLookup.getColor("JideButton.highlight");
/*     */   }
/*     */ 
/*     */   public Color getCollapsiblePaneContentBackground() {
/*  87 */     return UIDefaultsLookup.getColor("CollapsiblePane.contentBackground");
/*     */   }
/*     */ 
/*     */   public Color getCollapsiblePaneTitleForeground() {
/*  91 */     return UIDefaultsLookup.getColor("CollapsiblePane.foreground");
/*     */   }
/*     */ 
/*     */   public Color getCollapsiblePaneTitleForegroundEmphasized() {
/*  95 */     return UIDefaultsLookup.getColor("CollapsiblePane.emphasizedForeground");
/*     */   }
/*     */ 
/*     */   public Color getCollapsiblePaneFocusTitleForegroundEmphasized() {
/*  99 */     return UIDefaultsLookup.getColor("CollapsiblePane.emphasizedForeground");
/*     */   }
/*     */ 
/*     */   public Color getCollapsiblePaneFocusTitleForeground() {
/* 103 */     return UIDefaultsLookup.getColor("CollapsiblePane.foreground");
/*     */   }
/*     */ 
/*     */   public ImageIcon getCollapsiblePaneUpIcon() {
/* 107 */     return (ImageIcon)UIDefaultsLookup.getIcon("CollapsiblePane.upIcon");
/*     */   }
/*     */ 
/*     */   public ImageIcon getCollapsiblePaneDownIcon() {
/* 111 */     return (ImageIcon)UIDefaultsLookup.getIcon("CollapsiblePane.downIcon");
/*     */   }
/*     */ 
/*     */   public ImageIcon getCollapsiblePaneUpIconEmphasized() {
/* 115 */     return getCollapsiblePaneUpIcon();
/*     */   }
/*     */ 
/*     */   public ImageIcon getCollapsiblePaneDownIconEmphasized() {
/* 119 */     return getCollapsiblePaneDownIcon();
/*     */   }
/*     */ 
/*     */   public ImageIcon getCollapsiblePaneTitleButtonBackground() {
/* 123 */     return (ImageIcon)UIDefaultsLookup.getIcon("CollapsiblePane.titleButtonBackground");
/*     */   }
/*     */ 
/*     */   public ImageIcon getCollapsiblePaneTitleButtonBackgroundEmphasized() {
/* 127 */     return (ImageIcon)UIDefaultsLookup.getIcon("CollapsiblePane.titleButtonBackground.emphasized");
/*     */   }
/*     */ 
/*     */   public ImageIcon getCollapsiblePaneUpMask() {
/* 131 */     return getCollapsiblePaneUpIcon();
/*     */   }
/*     */ 
/*     */   public ImageIcon getCollapsiblePaneDownMask() {
/* 135 */     return getCollapsiblePaneDownIcon();
/*     */   }
/*     */ 
/*     */   public Color getBackgroundDk() {
/* 139 */     return UIDefaultsLookup.getColor("JideButton.background");
/*     */   }
/*     */ 
/*     */   public Color getBackgroundLt() {
/* 143 */     return UIDefaultsLookup.getColor("JideButton.background");
/*     */   }
/*     */ 
/*     */   public Color getSelectionSelectedDk() {
/* 147 */     return this._bk2;
/*     */   }
/*     */ 
/*     */   public Color getSelectionSelectedLt() {
/* 151 */     return this._bk2;
/*     */   }
/*     */ 
/*     */   public Color getMenuItemBorderColor() {
/* 155 */     return UIDefaultsLookup.getColor("MenuItem.selectionBorderColor");
/*     */   }
/*     */ 
/*     */   public Color getMenuItemBackground() {
/* 159 */     return UIDefaultsLookup.getColor("MenuItem.background");
/*     */   }
/*     */ 
/*     */   public Color getCommandBarTitleBarBackground() {
/* 163 */     return UIDefaultsLookup.getColor("CommandBar.titleBarBackground");
/*     */   }
/*     */ 
/*     */   public Color getControl() {
/* 167 */     return UIDefaultsLookup.getColor("JideButton.background");
/*     */   }
/*     */ 
/*     */   public Color getControlLt() {
/* 171 */     return getControlShadow();
/*     */   }
/*     */ 
/*     */   public Color getControlDk() {
/* 175 */     return getControlShadow();
/*     */   }
/*     */ 
/*     */   public Color getControlShadow() {
/* 179 */     return UIDefaultsLookup.getColor("JideButton.shadow");
/*     */   }
/*     */ 
/*     */   public Color getTitleBarBackground() {
/* 183 */     return UIDefaultsLookup.getColor("DockableFrame.activeTitleBackground");
/*     */   }
/*     */ 
/*     */   public Color getDockableFrameTitleBarActiveForeground() {
/* 187 */     return UIDefaultsLookup.getColor("DockableFrame.activeTitleForeground");
/*     */   }
/*     */ 
/*     */   public Color getDockableFrameTitleBarInactiveForeground() {
/* 191 */     return UIDefaultsLookup.getColor("DockableFrame.inactiveTitleForeground");
/*     */   }
/*     */ 
/*     */   public Color getTabbedPaneSelectDk() {
/* 195 */     return UIDefaultsLookup.getColor("JideTabbedPane.selectedTabBackgroundDk");
/*     */   }
/*     */ 
/*     */   public Color getTabbedPaneSelectLt() {
/* 199 */     return UIDefaultsLookup.getColor("JideTabbedPane.selectedTabBackgroundlt");
/*     */   }
/*     */ 
/*     */   public Color getTabAreaBackgroundDk() {
/* 203 */     return UIDefaultsLookup.getColor("JideTabbedPane.tabAreaBackgroundDk");
/*     */   }
/*     */ 
/*     */   public Color getTabAreaBackgroundLt() {
/* 207 */     return UIDefaultsLookup.getColor("JideTabbedPane.tabAreaBackgroundLt");
/*     */   }
/*     */ 
/*     */   public Color getOptionPaneBannerForeground() {
/* 211 */     return new ColorUIResource(255, 255, 255);
/*     */   }
/*     */ 
/*     */   public Color getOptionPaneBannerDk() {
/* 215 */     return new ColorUIResource(45, 96, 249);
/*     */   }
/*     */ 
/*     */   public Color getOptionPaneBannerLt() {
/* 219 */     return new ColorUIResource(0, 52, 206);
/*     */   }
/*     */ 
/*     */   public void paintSelectedMenu(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 223 */     Color oldColor = g.getColor();
/* 224 */     g.setColor(UIDefaultsLookup.getColor("JideButton.darkShadow"));
/* 225 */     g.drawLine(rect.x, rect.y + rect.height, rect.x, rect.y + 1);
/* 226 */     g.drawLine(rect.x + rect.width - 2, rect.y, rect.x + rect.width - 2, rect.y + rect.height);
/* 227 */     if (orientation == 0) {
/* 228 */       g.drawLine(rect.x, rect.y, rect.x + rect.width - 3, rect.y);
/*     */     }
/*     */     else {
/* 231 */       g.drawLine(rect.x, rect.y + rect.height - 1, rect.x + rect.width - 3, rect.y + rect.height - 1);
/*     */     }
/* 233 */     g.setColor(oldColor);
/*     */   }
/*     */ 
/*     */   public void paintMenuItemBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 237 */     paintMenuItemBackground(c, g, rect, orientation, state, true);
/*     */   }
/*     */ 
/*     */   public void paintMenuItemBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state, boolean showBorder) {
/* 241 */     paintButtonBackground(c, g, rect, orientation, state, showBorder);
/*     */   }
/*     */ 
/*     */   public void paintButtonBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 245 */     paintButtonBackground(c, g, rect, orientation, state, true);
/*     */   }
/*     */ 
/*     */   public void paintButtonBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state, boolean showBorder) {
/* 249 */     installDefaults();
/* 250 */     Color background = null;
/*     */ 
/* 252 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/* 253 */     if (highContrast.booleanValue()) {
/* 254 */       background = c.getBackground();
/* 255 */       paintBackground(c, g, rect, (state == 0) || (state == 4) ? null : this._borderColor, (state == 1) || (state == 3) || (state == 2) ? UIDefaultsLookup.getColor("JideButton.selectedBackground") : background, orientation);
/*     */ 
/* 257 */       return;
/*     */     }
/*     */ 
/* 260 */     switch (state) {
/*     */     case 0:
/* 262 */       background = c.getBackground();
/* 263 */       if ((background == null) || ((background instanceof UIResource))) {
/* 264 */         background = this._bk0;
/*     */       }
/* 266 */       paintBackground(c, g, rect, showBorder ? this._borderColor : null, background, orientation);
/* 267 */       break;
/*     */     case 2:
/* 269 */       if ((c instanceof ComponentStateSupport)) {
/* 270 */         background = ((ComponentStateSupport)c).getBackgroundOfState(2);
/*     */       }
/* 272 */       if ((background == null) || ((background instanceof UIResource))) {
/* 273 */         background = this._bk1;
/*     */       }
/* 275 */       paintBackground(c, g, rect, showBorder ? this._borderColor : null, background, orientation);
/* 276 */       break;
/*     */     case 3:
/* 278 */       if ((c instanceof ComponentStateSupport)) {
/* 279 */         background = ((ComponentStateSupport)c).getBackgroundOfState(3);
/*     */       }
/* 281 */       if ((background == null) || ((background instanceof UIResource))) {
/* 282 */         background = this._bk2;
/*     */       }
/* 284 */       paintBackground(c, g, rect, showBorder ? this._borderColor : null, background, orientation);
/* 285 */       break;
/*     */     case 1:
/* 287 */       if ((c instanceof ComponentStateSupport)) {
/* 288 */         background = ((ComponentStateSupport)c).getBackgroundOfState(1);
/*     */       }
/* 290 */       if ((background == null) || ((background instanceof UIResource))) {
/* 291 */         background = this._bk3;
/*     */       }
/* 293 */       paintBackground(c, g, rect, showBorder ? this._borderColor : null, background, orientation);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void paintBackground(JComponent c, Graphics g, Rectangle rect, Color borderColor, Color background, int orientation)
/*     */   {
/* 299 */     Color oldColor = g.getColor();
/* 300 */     if (borderColor != null) {
/* 301 */       boolean paintDefaultBorder = true;
/* 302 */       Object o = c.getClientProperty("JideButton.paintDefaultBorder");
/* 303 */       if ((o instanceof Boolean)) {
/* 304 */         paintDefaultBorder = ((Boolean)o).booleanValue();
/*     */       }
/* 306 */       if (paintDefaultBorder) {
/* 307 */         g.setColor(borderColor);
/* 308 */         Object position = c.getClientProperty("JButton.segmentPosition");
/* 309 */         if ((position == null) || ("only".equals(position))) {
/* 310 */           g.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
/*     */         }
/* 312 */         else if ("first".equals(position)) {
/* 313 */           if (orientation == 0) {
/* 314 */             g.drawRect(rect.x, rect.y, rect.width, rect.height - 1);
/*     */           }
/*     */           else {
/* 317 */             g.drawRect(rect.x, rect.y, rect.width - 1, rect.height);
/*     */           }
/*     */         }
/* 320 */         else if ("middle".equals(position)) {
/* 321 */           if (orientation == 0) {
/* 322 */             g.drawRect(rect.x, rect.y, rect.width, rect.height - 1);
/*     */           }
/*     */           else {
/* 325 */             g.drawRect(rect.x, rect.y, rect.width - 1, rect.height);
/*     */           }
/*     */         }
/* 328 */         else if ("last".equals(position)) {
/* 329 */           if (orientation == 0) {
/* 330 */             g.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
/*     */           }
/*     */           else {
/* 333 */             g.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
/*     */           }
/*     */         }
/*     */       }
/* 337 */       g.setColor(background);
/* 338 */       g.fillRect(rect.x + 1, rect.y + 1, rect.width - 2, rect.height - 2);
/*     */     }
/*     */     else {
/* 341 */       g.setColor(background);
/* 342 */       g.fillRect(rect.x, rect.y, rect.width, rect.height);
/*     */     }
/* 344 */     g.setColor(oldColor);
/*     */   }
/*     */ 
/*     */   public void paintChevronBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 348 */     if (state != 0)
/* 349 */       paintButtonBackground(c, g, rect, orientation, state);
/*     */   }
/*     */ 
/*     */   public void paintDividerBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 354 */     Color oldColor = g.getColor();
/* 355 */     g.setColor(UIDefaultsLookup.getColor("SplitPane.background"));
/* 356 */     g.fillRect(0, 0, rect.width, rect.height);
/* 357 */     g.setColor(oldColor);
/*     */   }
/*     */ 
/*     */   public void paintCommandBarBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 361 */     g.setColor(UIDefaultsLookup.getColor("CommandBar.background"));
/* 362 */     g.fillRoundRect(rect.x, rect.y, rect.width, rect.height, 2, 2);
/*     */   }
/*     */ 
/*     */   public void paintFloatingCommandBarBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 366 */     g.setColor(UIDefaultsLookup.getColor("CommandBar.background"));
/* 367 */     g.fillRect(rect.x, rect.y, rect.width, rect.height);
/*     */   }
/*     */ 
/*     */   public void paintMenuShadow(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 371 */     Color oldColor = g.getColor();
/* 372 */     g.setColor(UIDefaultsLookup.getColor("MenuItem.shadowColor"));
/* 373 */     g.fillRect(rect.x, rect.y, rect.width, rect.height);
/* 374 */     g.setColor(oldColor);
/*     */   }
/*     */ 
/*     */   public void paintContentBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 378 */     g.setColor(UIDefaultsLookup.getColor("control"));
/* 379 */     g.fillRect(rect.x, rect.y, rect.width, rect.height);
/*     */   }
/*     */ 
/*     */   public void paintStatusBarBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 383 */     if (c.isOpaque())
/* 384 */       paintContentBackground(c, g, rect, orientation, state);
/*     */   }
/*     */ 
/*     */   public void paintCommandBarTitlePane(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 389 */     g.setColor(getCommandBarTitleBarBackground());
/* 390 */     g.fillRect(rect.x, rect.y, rect.width, rect.height);
/*     */   }
/*     */ 
/*     */   public void paintGripper(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 399 */     g.setColor(getGripperForeground());
/*     */ 
/* 402 */     if (rect.width > rect.height) {
/* 403 */       rect.x = (rect.x + rect.width / 2 - 10);
/* 404 */       rect.width = 22;
/*     */     }
/*     */     else {
/* 407 */       rect.y = (rect.y + rect.height / 2 - 10);
/* 408 */       rect.height = 22;
/*     */     }
/*     */ 
/* 411 */     if (orientation == 0) {
/* 412 */       if (rect.width <= 30) {
/* 413 */         int MARGIN = 3;
/* 414 */         for (int i = 0; i < (rect.height - 6) / 2; i++)
/* 415 */           g.drawLine(rect.x + 3, rect.y + 3 + i * 2, rect.x + rect.width - 3, rect.y + 3 + i * 2);
/*     */       }
/*     */       else
/*     */       {
/* 419 */         int MARGIN = 2;
/* 420 */         for (int i = 0; i < (rect.height - 4) / 2; i++)
/* 421 */           g.drawLine((rect.width - rect.width) / 2, rect.y + 2 + i * 2, (rect.width + rect.width) / 2, rect.y + 2 + i * 2);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 426 */       int MARGIN = 3;
/* 427 */       int count = (rect.width - 6) / 2;
/* 428 */       for (int i = 0; i < count; i++) {
/* 429 */         int x = rect.x + rect.width / 2 - count + i * 2;
/* 430 */         g.drawLine(x, rect.y + 3, x, rect.y + rect.height - 3);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintChevronMore(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 436 */     g.setColor(UIDefaultsLookup.getColor("CommandBar.darkShadow"));
/* 437 */     if (orientation == 0) {
/* 438 */       if (!c.getComponentOrientation().isLeftToRight()) {
/* 439 */         int y = rect.y + 4;
/* 440 */         for (int i = -2; i <= 2; i++) {
/* 441 */           int offset = Math.abs(i);
/* 442 */           g.drawLine(rect.x + 2 + offset, y, rect.x + 3 + offset, y);
/* 443 */           g.drawLine(rect.x + 6 + offset, y, rect.x + 7 + offset, y);
/* 444 */           y++;
/*     */         }
/*     */       }
/*     */       else {
/* 448 */         int y = rect.y + 4;
/* 449 */         for (int i = -2; i <= 2; i++) {
/* 450 */           int offset = -Math.abs(i);
/* 451 */           g.drawLine(rect.x + 4 + offset, y, rect.x + 5 + offset, y);
/* 452 */           g.drawLine(rect.x + 8 + offset, y, rect.x + 9 + offset, y);
/* 453 */           y++;
/*     */         }
/*     */       }
/*     */     }
/* 457 */     else if (orientation == 1) {
/* 458 */       int x = rect.x + 4;
/* 459 */       for (int i = -2; i <= 2; i++) {
/* 460 */         int offset = -Math.abs(i);
/* 461 */         g.drawLine(x, rect.y + 4 + offset, x, rect.y + 5 + offset);
/* 462 */         g.drawLine(x, rect.y + 8 + offset, x, rect.y + 9 + offset);
/* 463 */         x++;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintChevronOption(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/*     */     int startY;
/*     */     int startX;
/*     */     int startY;
/* 471 */     if ((orientation == 0) || (!c.getComponentOrientation().isLeftToRight())) {
/* 472 */       int startX = rect.x + 3;
/* 473 */       startY = rect.y + rect.height - 7;
/*     */     }
/*     */     else {
/* 476 */       startX = rect.x + rect.width - 7;
/* 477 */       startY = rect.y + 3;
/*     */     }
/* 479 */     if ((orientation == 0) || (!c.getComponentOrientation().isLeftToRight())) {
/* 480 */       JideSwingUtilities.paintArrow(g, UIDefaultsLookup.getColor("CommandBar.darkShadow"), startX, startY, 5, 0);
/*     */     }
/*     */     else
/* 483 */       JideSwingUtilities.paintArrow(g, UIDefaultsLookup.getColor("CommandBar.darkShadow"), startX, startY, 5, orientation);
/*     */   }
/*     */ 
/*     */   public void paintFloatingChevronOption(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 488 */     int startX = rect.width / 2 - 4;
/* 489 */     int startY = rect.height / 2 - 2;
/* 490 */     if (state == 2) {
/* 491 */       JideSwingUtilities.paintArrow(g, Color.BLACK, startX, startY, 9, orientation);
/*     */     }
/*     */     else
/* 494 */       JideSwingUtilities.paintArrow(g, Color.WHITE, startX, startY, 9, orientation);
/*     */   }
/*     */ 
/*     */   public void paintDockableFrameBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 499 */     if (!c.isOpaque()) {
/* 500 */       return;
/*     */     }
/* 502 */     g.setColor(UIDefaultsLookup.getColor("DockableFrame.background"));
/* 503 */     g.fillRect(rect.x, rect.y, rect.width, rect.height);
/*     */   }
/*     */ 
/*     */   public void paintDockableFrameTitlePane(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 507 */     int x = rect.x;
/* 508 */     int y = rect.y;
/* 509 */     int w = rect.width - 1;
/* 510 */     int h = rect.height;
/* 511 */     if (c.getBorder() != null) {
/* 512 */       Insets insets = c.getBorder().getBorderInsets(c);
/* 513 */       x += insets.left;
/* 514 */       y += insets.top;
/* 515 */       w -= insets.right + insets.left;
/* 516 */       h -= insets.top + insets.bottom;
/*     */     }
/* 518 */     rect = new Rectangle(x + 1, y + 1, w - 1, h - 1);
/*     */ 
/* 520 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/* 521 */     if (state == 3) {
/* 522 */       g.setColor(UIDefaultsLookup.getColor("DockableFrame.activeTitleBorderColor"));
/* 523 */       if ("true".equals(SecurityUtils.getProperty("shadingtheme", "false"))) {
/* 524 */         g.drawRoundRect(x, y, w, h, 2, 2);
/*     */       }
/*     */       else {
/* 527 */         g.drawRect(x, y, w, h);
/*     */       }
/* 529 */       g.setColor(highContrast.booleanValue() ? UIDefaultsLookup.getColor("JideButton.selectedBackground") : UIDefaultsLookup.getColor("DockableFrame.activeTitleBackground"));
/* 530 */       g.fillRect(rect.x, rect.y, rect.width, rect.height);
/*     */     }
/*     */     else {
/* 533 */       g.setColor(UIDefaultsLookup.getColor("DockableFrame.inactiveTitleBorderColor"));
/* 534 */       g.drawRoundRect(x, y, w, h, 2, 2);
/* 535 */       g.setColor(UIDefaultsLookup.getColor("DockableFrame.inactiveTitleBackground"));
/* 536 */       g.fillRect(rect.x, rect.y, rect.width, rect.height);
/*     */     }
/* 538 */     if ("true".equals(SecurityUtils.getProperty("shadingtheme", "false")))
/* 539 */       JideSwingUtilities.fillGradient(g, rect, 0);
/*     */   }
/*     */ 
/*     */   public void paintCollapsiblePaneTitlePaneBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 544 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/* 545 */     if (!(c.getBackground() instanceof UIResource)) {
/* 546 */       g.setColor(c.getBackground());
/*     */     }
/*     */     else {
/* 549 */       g.setColor(UIDefaultsLookup.getColor(highContrast.booleanValue() ? "JideButton.background" : "CollapsiblePane.background"));
/*     */     }
/* 551 */     g.fillRect(rect.x, rect.y, rect.width, rect.height);
/* 552 */     if (highContrast.booleanValue()) {
/* 553 */       g.setColor(UIDefaultsLookup.getColor("CollapsiblePane.background"));
/* 554 */       g.drawRect(rect.x, rect.y, rect.width - 1, rect.height);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintCollapsiblePaneTitlePaneBackgroundEmphasized(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 559 */     if (!(c.getBackground() instanceof UIResource)) {
/* 560 */       g.setColor(c.getBackground());
/*     */     }
/*     */     else {
/* 563 */       g.setColor(UIDefaultsLookup.getColor("CollapsiblePane.emphasizedBackground"));
/*     */     }
/* 565 */     g.fillRect(rect.x, rect.y, rect.width, rect.height);
/*     */   }
/*     */ 
/*     */   public void paintCollapsiblePanesBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 569 */     if (!c.isOpaque()) {
/* 570 */       return;
/*     */     }
/*     */ 
/* 573 */     if (!(c.getBackground() instanceof UIResource)) {
/* 574 */       g.setColor(c.getBackground());
/* 575 */       g.fillRect(rect.x, rect.y, rect.width, rect.height);
/*     */     }
/*     */     else {
/* 578 */       g.setColor(UIDefaultsLookup.getColor("TextField.background"));
/* 579 */       g.fillRect(rect.x, rect.y, rect.width, rect.height);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintCollapsiblePaneTitlePaneBackgroundPlainEmphasized(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 584 */     g.setColor(UIDefaultsLookup.getColor("CollapsiblePane.emphasizedBackground"));
/* 585 */     g.drawLine(rect.x, rect.y + rect.height - 1, rect.x + rect.width, rect.y + rect.height - 1);
/*     */   }
/*     */ 
/*     */   public void paintCollapsiblePaneTitlePaneBackgroundPlain(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 589 */     if (!(c.getBackground() instanceof UIResource)) {
/* 590 */       g.setColor(c.getBackground());
/*     */     }
/*     */     else {
/* 593 */       g.setColor(UIDefaultsLookup.getColor("CollapsiblePane.background"));
/*     */     }
/* 595 */     switch (orientation) {
/*     */     case 3:
/* 597 */       g.drawLine(rect.x + rect.width - 1, rect.y, rect.x + rect.width - 1, rect.y + rect.height - 1);
/* 598 */       break;
/*     */     case 7:
/* 600 */       g.drawLine(rect.x, rect.y, rect.x, rect.y + rect.height - 1);
/* 601 */       break;
/*     */     case 1:
/* 603 */       g.drawLine(rect.x, rect.y, rect.x + rect.width, rect.y);
/*     */     case 2:
/*     */     case 4:
/*     */     case 5:
/*     */     case 6:
/*     */     default:
/* 606 */       g.drawLine(rect.x, rect.y + rect.height - 1, rect.x + rect.width, rect.y + rect.height - 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintCollapsiblePaneTitlePaneBackgroundSeparatorEmphasized(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 612 */     g.setColor(UIManager.getColor("CollapsiblePane.emphasizedBackground"));
/* 613 */     g.fillRect(rect.x, rect.y, rect.x + rect.width, rect.height);
/*     */   }
/*     */ 
/*     */   public void paintCollapsiblePaneTitlePaneBackgroundSeparator(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 617 */     g.setColor(UIManager.getColor("CollapsiblePane.background"));
/* 618 */     g.fillRect(rect.x, rect.y, rect.x + rect.width, rect.height);
/*     */   }
/*     */ 
/*     */   public Color getColor(Object key) {
/* 622 */     return UIDefaultsLookup.getColor(key);
/*     */   }
/*     */ 
/*     */   public void paintTabAreaBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 626 */     if ((c.isOpaque()) && ((c instanceof JideTabbedPane))) {
/* 627 */       JideTabbedPane tabbedPane = (JideTabbedPane)c;
/* 628 */       int tabShape = tabbedPane.getTabShape();
/* 629 */       int colorTheme = tabbedPane.getColorTheme();
/* 630 */       if (tabShape == 3) {
/* 631 */         g.setColor(UIDefaultsLookup.getColor("control"));
/*     */       }
/* 634 */       else if (colorTheme == 1) {
/* 635 */         g.setColor(UIDefaultsLookup.getColor("control"));
/*     */       }
/* 638 */       else if (colorTheme == 3) {
/* 639 */         g.setColor(UIDefaultsLookup.getColor("JideTabbedPane.tabAreaBackground"));
/*     */       }
/*     */       else {
/* 642 */         g.setColor(UIDefaultsLookup.getColor("control"));
/*     */       }
/*     */ 
/* 645 */       g.fillRect(rect.x, rect.y, rect.width, rect.height);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintTabBackground(JComponent c, Graphics g, Shape region, Color[] colors, int orientation, int state) {
/* 650 */     Graphics2D g2d = (Graphics2D)g.create();
/* 651 */     Color backgroundStart = colors[0];
/* 652 */     Color backgroundEnd = colors[1];
/* 653 */     if ((backgroundEnd != null) && (backgroundStart != null)) {
/* 654 */       int tabPlacement = 1;
/* 655 */       if ((c instanceof JideTabbedPane)) {
/* 656 */         tabPlacement = ((JideTabbedPane)c).getTabPlacement();
/*     */       }
/* 658 */       switch (tabPlacement) {
/*     */       case 2:
/* 660 */         JideSwingUtilities.fillGradient(g2d, region, backgroundStart, backgroundEnd, false);
/* 661 */         break;
/*     */       case 4:
/* 663 */         JideSwingUtilities.fillGradient(g2d, region, backgroundEnd, backgroundStart, false);
/* 664 */         break;
/*     */       case 3:
/* 666 */         JideSwingUtilities.fillGradient(g2d, region, backgroundEnd, backgroundStart, true);
/* 667 */         break;
/*     */       case 1:
/*     */       default:
/* 670 */         JideSwingUtilities.fillGradient(g2d, region, backgroundStart, backgroundEnd, true);
/*     */       }
/*     */     }
/*     */ 
/* 674 */     g2d.dispose();
/*     */   }
/*     */ 
/*     */   public void paintTabContentBorder(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void paintSidePaneItemBackground(JComponent c, Graphics g, Rectangle rect, Color[] colors, int orientation, int state) {
/* 682 */     Color startColor = (colors != null) && (colors.length > 0) ? colors[0] : c.getBackground();
/* 683 */     Color endColor = (colors != null) && (colors.length > 1) ? colors[1] : startColor;
/* 684 */     switch (orientation) {
/*     */     case 7:
/* 686 */       JideSwingUtilities.fillGradient((Graphics2D)g, rect, startColor, endColor, false);
/*     */ 
/* 688 */       break;
/*     */     case 3:
/* 690 */       JideSwingUtilities.fillGradient((Graphics2D)g, rect, endColor, startColor, false);
/*     */ 
/* 692 */       break;
/*     */     case 1:
/* 694 */       JideSwingUtilities.fillGradient((Graphics2D)g, rect, startColor, endColor, true);
/*     */ 
/* 696 */       break;
/*     */     case 5:
/* 698 */       JideSwingUtilities.fillGradient((Graphics2D)g, rect, endColor, startColor, true);
/*     */     case 2:
/*     */     case 4:
/*     */     case 6:
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintHeaderBoxBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 705 */     boolean isCellEditor = Boolean.TRUE.equals(c.getClientProperty("HeaderBox.isTableCellEditor"));
/*     */ 
/* 707 */     Color baseColor = c.getBackground();
/* 708 */     if ((baseColor instanceof UIResource)) {
/* 709 */       baseColor = UIDefaultsLookup.getColor("HeaderBox.background");
/* 710 */       if (baseColor == null) {
/* 711 */         baseColor = UIDefaultsLookup.getColor("control");
/*     */       }
/*     */     }
/* 714 */     if ((state == 1) || (state == 3) || (state == 2)) {
/* 715 */       Color color = ColorUtils.getDerivedColor(baseColor, 0.48F);
/* 716 */       if (isCellEditor) {
/* 717 */         g.setColor(color);
/* 718 */         g.fillRect(rect.x, rect.y, rect.width, rect.height);
/*     */       }
/*     */       else {
/* 721 */         g.setColor(color);
/* 722 */         g.fillRoundRect(rect.x, rect.y, rect.width - 1, rect.height - 1, 4, 4);
/*     */ 
/* 724 */         g.setColor(ColorUtils.getDerivedColor(baseColor, 0.4F));
/* 725 */         g.drawRoundRect(rect.x, rect.y, rect.width - 1, rect.height - 1, 6, 6);
/*     */       }
/*     */ 
/* 728 */       g.setColor(ColorUtils.getDerivedColor(baseColor, 0.45F));
/* 729 */       g.drawLine(rect.x + 1, rect.y + rect.height - 3, rect.x + rect.width - 2, rect.y + rect.height - 3);
/*     */ 
/* 731 */       g.setColor(ColorUtils.getDerivedColor(baseColor, 0.43F));
/* 732 */       g.drawLine(rect.x + 2, rect.y + rect.height - 2, rect.x + rect.width - 3, rect.y + rect.height - 2);
/*     */ 
/* 734 */       g.setColor(ColorUtils.getDerivedColor(baseColor, 0.4F));
/* 735 */       g.drawLine(rect.x + 3, rect.y + rect.height - 1, rect.x + rect.width - 4, rect.y + rect.height - 1);
/*     */     }
/*     */     else {
/* 738 */       if (isCellEditor) {
/* 739 */         g.setColor(baseColor);
/* 740 */         g.fillRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
/*     */       }
/*     */       else {
/* 743 */         g.setColor(baseColor);
/* 744 */         g.fillRoundRect(rect.x, rect.y, rect.width - 1, rect.height - 1, 2, 2);
/*     */ 
/* 746 */         g.setColor(ColorUtils.getDerivedColor(baseColor, 0.42F));
/* 747 */         g.drawRoundRect(rect.x, rect.y, rect.width - 1, rect.height - 1, 2, 4);
/*     */       }
/*     */ 
/* 750 */       g.setColor(ColorUtils.getDerivedColor(baseColor, 0.48F));
/* 751 */       g.drawLine(rect.x + 1, rect.y + rect.height - 3, rect.x + rect.width - 2, rect.y + rect.height - 3);
/* 752 */       g.setColor(ColorUtils.getDerivedColor(baseColor, 0.47F));
/* 753 */       g.drawLine(rect.x + 1, rect.y + rect.height - 2, rect.x + rect.width - 2, rect.y + rect.height - 2);
/*     */ 
/* 755 */       if (isCellEditor) {
/* 756 */         g.setColor(new Color(198, 197, 178));
/* 757 */         g.drawLine(rect.x + rect.width - 3, rect.y + 4, rect.x + rect.width - 3, rect.y + rect.height - 7);
/* 758 */         g.setColor(Color.WHITE);
/* 759 */         g.drawLine(rect.x + rect.width - 2, rect.y + 4, rect.x + rect.width - 2, rect.y + rect.height - 7);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintToolBarSeparator(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 765 */     if (c.isOpaque()) {
/* 766 */       g.setColor(c.getBackground());
/* 767 */       g.fillRect(rect.x, rect.y, rect.width, rect.height);
/*     */     }
/* 769 */     if (orientation == 0) {
/* 770 */       g.setColor(c.getForeground());
/* 771 */       g.drawLine(rect.x + rect.width / 2, rect.y + 1, rect.x + rect.width / 2, rect.y + rect.height - 2);
/*     */     }
/*     */     else {
/* 774 */       g.setColor(c.getForeground());
/* 775 */       g.drawLine(rect.x + 1, rect.y + rect.height / 2, rect.x + rect.width - 2, rect.y + rect.height / 2);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintPopupMenuSeparator(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 780 */     int defaultShadowWidth = UIDefaultsLookup.getInt("MenuItem.shadowWidth");
/* 781 */     int defaultTextIconGap = UIDefaultsLookup.getInt("MenuItem.textIconGap");
/* 782 */     Color shadowColor = UIDefaultsLookup.getColor("MenuItem.shadowColor");
/* 783 */     Color foreground = UIDefaultsLookup.getColor("PopupMenuSeparator.foreground");
/* 784 */     Color background = UIDefaultsLookup.getColor("PopupMenuSeparator.background");
/*     */ 
/* 786 */     g.setColor(shadowColor);
/* 787 */     if (c.getComponentOrientation().isLeftToRight()) {
/* 788 */       g.fillRect(0, 0, defaultShadowWidth, rect.height);
/* 789 */       if ("true".equals(SecurityUtils.getProperty("shadingtheme", "false"))) {
/* 790 */         JideSwingUtilities.fillSingleGradient(g, new Rectangle(rect.x, rect.y, defaultShadowWidth, rect.height), 3, 255);
/*     */       }
/*     */ 
/* 793 */       g.setColor(background);
/* 794 */       g.fillRect(rect.x + defaultShadowWidth, rect.y, rect.width - defaultShadowWidth, rect.height);
/*     */ 
/* 796 */       g.setColor(foreground);
/* 797 */       g.drawLine(rect.x + defaultShadowWidth + defaultTextIconGap, rect.y + 1, rect.x + rect.width, rect.y + 1);
/*     */     }
/*     */     else {
/* 800 */       g.fillRect(rect.x + rect.width, rect.y, defaultShadowWidth, rect.height);
/* 801 */       if ("true".equals(SecurityUtils.getProperty("shadingtheme", "false"))) {
/* 802 */         JideSwingUtilities.fillSingleGradient(g, new Rectangle(rect.x + rect.width - defaultTextIconGap, rect.y, defaultShadowWidth, 2), 7, 255);
/*     */       }
/*     */ 
/* 805 */       g.setColor(background);
/* 806 */       g.fillRect(rect.x, rect.y, rect.width - defaultShadowWidth, rect.height);
/*     */ 
/* 808 */       g.setColor(foreground);
/* 809 */       g.drawLine(rect.x, rect.y + 1, rect.x + rect.width - defaultShadowWidth - defaultTextIconGap, rect.y + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintStatusBarSeparator(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected boolean shouldDisplayOnTop()
/*     */   {
/* 838 */     return (SystemInfo.isWindowsVistaAbove()) && ((UIManager.getLookAndFeel() instanceof WindowsLookAndFeel));
/*     */   }
/*     */ 
/*     */   public void fillBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state, Color color) {
/* 842 */     Color oldColor = g.getColor();
/* 843 */     g.setColor(color);
/* 844 */     g.fillRect(rect.x, rect.y, rect.width, rect.height);
/* 845 */     g.setColor(oldColor);
/*     */   }
/*     */ 
/*     */   public void paintSortableTableHeaderColumn(JComponent c, Graphics g, Rectangle rect, int orientation, int state, int sortOrder, Icon sortIcon, int orderIndex, Color indexColor, boolean paintIndex) {
/* 849 */     int iconHeight = sortIcon == null ? 0 : sortIcon.getIconHeight();
/* 850 */     int iconWidth = sortIcon == null ? 0 : sortIcon.getIconWidth();
/* 851 */     int yOffset = sortIcon == null ? 0 : (rect.height - iconHeight) / 2 - V_GAP;
/*     */ 
/* 853 */     boolean leftToRight = c.getComponentOrientation().isLeftToRight();
/* 854 */     if ((paintIndex) && (orderIndex != -1)) {
/* 855 */       Font oldFont = g.getFont();
/* 856 */       Font font = g.getFont().deriveFont(0, oldFont.getSize() - 3);
/* 857 */       String str = "" + (orderIndex + 1);
/* 858 */       int textWidth = SwingUtilities.computeStringWidth(c.getFontMetrics(font), str);
/* 859 */       g.setFont(font);
/* 860 */       Color oldColor = g.getColor();
/* 861 */       g.setColor(indexColor);
/* 862 */       int x = rect.x + rect.width / 2 - (iconWidth + ARROW_TEXT_GAP + textWidth) / 2;
/* 863 */       if (shouldDisplayOnTop()) {
/* 864 */         JideSwingUtilities.drawString(c, g, str, x + ARROW_TEXT_GAP + iconWidth, rect.y + V_GAP + c.getFontMetrics(font).getAscent() - 2);
/*     */       }
/* 866 */       else if (leftToRight) {
/* 867 */         JideSwingUtilities.drawString(c, g, str, rect.x + rect.width - textWidth - H_GAP, rect.y + yOffset + iconHeight / 2 + 1);
/*     */       }
/*     */       else {
/* 870 */         JideSwingUtilities.drawString(c, g, str, rect.x + H_GAP, rect.y + yOffset + iconHeight / 2 + 1);
/*     */       }
/* 872 */       g.setColor(oldColor);
/* 873 */       g.setFont(oldFont);
/* 874 */       if (sortIcon != null) {
/* 875 */         if (shouldDisplayOnTop()) {
/* 876 */           if ((sortIcon instanceof SynthIcon)) {
/* 877 */             SynthContext context = new SynthContext(c, Region.TABLE_HEADER, SynthLookAndFeel.getStyle(c, Region.TABLE_HEADER), 0);
/* 878 */             ((SynthIcon)sortIcon).paintIcon(context, g, x, rect.y + V_GAP, ((SynthIcon)sortIcon).getIconWidth(context), ((SynthIcon)sortIcon).getIconHeight(context));
/*     */           }
/*     */           else {
/* 881 */             sortIcon.paintIcon(c, g, x, rect.y + V_GAP);
/*     */           }
/*     */         }
/* 884 */         else if (leftToRight) {
/* 885 */           if ((sortIcon instanceof SynthIcon)) {
/* 886 */             SynthContext context = new SynthContext(c, Region.TABLE_HEADER, SynthLookAndFeel.getStyle(c, Region.TABLE_HEADER), 0);
/* 887 */             ((SynthIcon)sortIcon).paintIcon(context, g, rect.x + rect.width - iconWidth - textWidth - H_GAP - ARROW_TEXT_GAP, rect.y + yOffset, ((SynthIcon)sortIcon).getIconWidth(context), ((SynthIcon)sortIcon).getIconHeight(context));
/*     */           }
/*     */           else {
/* 890 */             sortIcon.paintIcon(c, g, rect.x + rect.width - iconWidth - textWidth - H_GAP - ARROW_TEXT_GAP, rect.y + yOffset);
/*     */           }
/*     */ 
/*     */         }
/* 894 */         else if ((sortIcon instanceof SynthIcon)) {
/* 895 */           SynthContext context = new SynthContext(c, Region.TABLE_HEADER, SynthLookAndFeel.getStyle(c, Region.TABLE_HEADER), 0);
/* 896 */           ((SynthIcon)sortIcon).paintIcon(context, g, rect.x + textWidth + H_GAP + ARROW_TEXT_GAP, rect.y + yOffset, ((SynthIcon)sortIcon).getIconWidth(context), ((SynthIcon)sortIcon).getIconHeight(context));
/*     */         }
/*     */         else {
/* 899 */           sortIcon.paintIcon(c, g, rect.x + textWidth + H_GAP + ARROW_TEXT_GAP, rect.y + yOffset);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/* 905 */     else if (sortIcon != null) {
/* 906 */       if (shouldDisplayOnTop()) {
/* 907 */         if ((sortIcon instanceof SynthIcon)) {
/* 908 */           SynthContext context = new SynthContext(c, Region.TABLE_HEADER, SynthLookAndFeel.getStyle(c, Region.TABLE_HEADER), 0);
/* 909 */           ((SynthIcon)sortIcon).paintIcon(context, g, rect.x + rect.width / 2 - iconWidth / 2, rect.y + V_GAP, ((SynthIcon)sortIcon).getIconWidth(context), ((SynthIcon)sortIcon).getIconHeight(context));
/*     */         }
/*     */         else {
/* 912 */           sortIcon.paintIcon(c, g, rect.x + rect.width / 2 - iconWidth / 2, rect.y + V_GAP);
/*     */         }
/*     */       }
/* 915 */       else if (leftToRight) {
/* 916 */         if ((sortIcon instanceof SynthIcon)) {
/* 917 */           SynthContext context = new SynthContext(c, Region.TABLE_HEADER, SynthLookAndFeel.getStyle(c, Region.TABLE_HEADER), 0);
/* 918 */           ((SynthIcon)sortIcon).paintIcon(context, g, rect.x + rect.width - iconWidth - H_GAP, rect.y + yOffset, ((SynthIcon)sortIcon).getIconWidth(context), ((SynthIcon)sortIcon).getIconHeight(context));
/*     */         }
/*     */         else {
/* 921 */           sortIcon.paintIcon(c, g, rect.x + rect.width - iconWidth - H_GAP, rect.y + yOffset);
/*     */         }
/*     */ 
/*     */       }
/* 925 */       else if ((sortIcon instanceof SynthIcon)) {
/* 926 */         SynthContext context = new SynthContext(c, Region.TABLE_HEADER, SynthLookAndFeel.getStyle(c, Region.TABLE_HEADER), 0);
/* 927 */         ((SynthIcon)sortIcon).paintIcon(context, g, rect.x + H_GAP, rect.y + yOffset, ((SynthIcon)sortIcon).getIconWidth(context), ((SynthIcon)sortIcon).getIconHeight(context));
/*     */       }
/*     */       else {
/* 930 */         sortIcon.paintIcon(c, g, rect.x + H_GAP, rect.y + yOffset);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.BasicPainter
 * JD-Core Version:    0.6.0
 */
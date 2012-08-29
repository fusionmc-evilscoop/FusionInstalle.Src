/*     */ package com.jidesoft.plaf.office2007;
/*     */ 
/*     */ import com.jidesoft.icons.IconsFactory;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.plaf.basic.BasicJideButtonUI;
/*     */ import com.jidesoft.plaf.basic.BasicPainter;
/*     */ import com.jidesoft.plaf.basic.ThemePainter;
/*     */ import com.jidesoft.plaf.office2003.Office2003Painter;
/*     */ import com.jidesoft.swing.ComponentStateSupport;
/*     */ import com.jidesoft.swing.JideSplitButton;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import com.jidesoft.utils.ColorUtils;
/*     */ import com.jidesoft.utils.SystemInfo;
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.GradientPaint;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.geom.Area;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.UIResource;
/*     */ 
/*     */ public class Office2007Painter extends BasicPainter
/*     */ {
/*     */   public static final String IS_MENU_PART_BUTTON = "isMenuPartButton";
/*     */   private static Office2007Painter _instance;
/*     */   private ThemePainter _defaultPainter;
/*     */   private static final Color[] STATUS_BAR_BG;
/*     */   private static final Color[] SPECIAL_STATUS_BAR_BG;
/*     */   private static final Color[] DOCKABLE_FRAME_TITLE_BAR_BG;
/*     */   private static final Color[] ACTIVE_DOCKABLE_FRAME_TITLE_BAR_BG;
/*     */   private static final Color[] COLLAPSIBLE_PANE_TITLE_BAR_BG;
/*     */   private static final Color[] EMPHASIZED_COLLAPSIBLE_PANE_TITLE_BAR_BG;
/*     */   private static final Color[] COLLAPSIBLE_PANE_TITLE_BAR_SEPARATOR_BG;
/*     */   private static final Color[] EMPHASIZED_COLLAPSIBLE_PANE_TITLE_BAR_SEPARATOR_BG;
/*     */ 
/*     */   public static ThemePainter getInstance()
/*     */   {
/*  40 */     if (_instance == null) {
/*  41 */       _instance = new Office2007Painter();
/*     */     }
/*  43 */     return _instance;
/*     */   }
/*     */ 
/*     */   protected ThemePainter createDefaultPainter()
/*     */   {
/*  50 */     return Office2003Painter.getInstance();
/*     */   }
/*     */ 
/*     */   public ThemePainter getDefaultPainter() {
/*  54 */     if (this._defaultPainter == null) {
/*  55 */       this._defaultPainter = createDefaultPainter();
/*     */     }
/*  57 */     return this._defaultPainter;
/*     */   }
/*     */ 
/*     */   public void installDefaults() {
/*  61 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/*  62 */     if (highContrast.booleanValue())
/*  63 */       super.installDefaults();
/*     */   }
/*     */ 
/*     */   public void uninstallDefaults()
/*     */   {
/*  68 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/*  69 */     if (highContrast.booleanValue())
/*  70 */       super.uninstallDefaults();
/*     */   }
/*     */ 
/*     */   public void paintContentBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/*  83 */     Graphics2D g2d = (Graphics2D)g.create();
/*  84 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/*  85 */     g2d.setColor(highContrast.booleanValue() ? UIDefaultsLookup.getColor("Content.background") : new Color(12573695));
/*  86 */     g2d.fillRect(rect.x, rect.y, rect.width, rect.height);
/*     */ 
/* 109 */     g2d.dispose();
/*     */   }
/*     */ 
/*     */   private void paintButtonBorder(Component c, Graphics g, Rectangle rect, int state)
/*     */   {
/* 114 */     int x = rect.x;
/* 115 */     int y = rect.y;
/* 116 */     int w = rect.width;
/* 117 */     int h = rect.height;
/*     */ 
/* 119 */     Graphics2D gfx = (Graphics2D)g;
/* 120 */     if ((state == 3) || (state == 1)) {
/* 121 */       gfx.setColor(new Color(9139796));
/* 122 */       gfx.drawLine(x + 1, y, x + w - 2, y);
/* 123 */       gfx.setPaint(new GradientPaint(x, y + 1, new Color(9139796), x, y + h - 3, new Color(12892584)));
/* 124 */       gfx.drawLine(x, y + 1, x, y + h - 3);
/* 125 */       gfx.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 3);
/* 126 */       gfx.setColor(new Color(15977082));
/* 127 */       gfx.drawLine(x, y + h - 3, x, y + h - 3);
/* 128 */       gfx.drawLine(x + w - 1, y + h - 3, x + w - 1, y + h - 3);
/*     */ 
/* 130 */       if (((c instanceof JideSplitButton)) && (BasicJideButtonUI.shouldWrapText(c))) {
/* 131 */         gfx.setColor(new Color(13680517));
/* 132 */         gfx.drawLine(x, y + h - 30, x + w, y + h - 30);
/*     */       }
/*     */     }
/* 135 */     else if (state == 2) {
/* 136 */       if (h != 0) {
/* 137 */         Paint lgp = JideSwingUtilities.getLinearGradientPaint(x, y, x, y + h, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { new Color(14207638), new Color(12165236), new Color(12102030) });
/*     */ 
/* 140 */         gfx.setPaint(lgp);
/*     */       }
/* 142 */       gfx.drawLine(x, y + 1, x, h - 2);
/* 143 */       gfx.setColor(new Color(14405273));
/* 144 */       gfx.drawLine(x + 1, y, x + w - 2, y);
/* 145 */       gfx.setPaint(new GradientPaint(x, y + h, new Color(12299927), x + (w >> 1), y + h, new Color(13353898), true));
/* 146 */       gfx.drawLine(x + 1, y + h - 1, x + w - 2, y + h - 1);
/* 147 */       if (h - 1 != 0) {
/* 148 */         gfx.setPaint(JideSwingUtilities.getLinearGradientPaint(x + w - 1, y, x + w - 1, y + h - 1, new float[] { 0.0F, 0.5F, 1.0F }, new Color[] { new Color(14470810), new Color(12823418), new Color(13815481) }));
/*     */       }
/*     */ 
/* 152 */       gfx.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 2);
/* 153 */       gfx.setColor(new Color(15393448));
/* 154 */       gfx.drawLine(x + 1, y + 1, x + 1, y + 1);
/* 155 */       gfx.drawLine(x + w - 2, y + 1, x + w - 2, y + 1);
/* 156 */       gfx.setColor(new Color(15786940));
/* 157 */       gfx.drawLine(x + 1, y + h - 2, x + 1, y + h - 2);
/* 158 */       gfx.drawLine(x + w - 2, y + h - 2, x + w - 2, y + h - 2);
/*     */     }
/* 171 */     else if (state == 0)
/*     */     {
/* 173 */       gfx.setColor(new Color(7836601));
/* 174 */       gfx.drawLine(x + 1, y, x + w - 2, y);
/* 175 */       gfx.drawLine(x, y + 1, x, y + h - 2);
/* 176 */       gfx.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 2);
/* 177 */       gfx.drawLine(x + 1, y + h - 1, x + w - 2, y + h - 1);
/*     */ 
/* 180 */       gfx.setColor(new Color(10269908));
/* 181 */       gfx.drawLine(x + 1, y + 1, x + 1, y + 1);
/* 182 */       gfx.drawLine(x + 1, y + h - 2, x + 1, y + h - 2);
/* 183 */       gfx.drawLine(x + w - 2, y + 1, x + w - 2, y + 1);
/* 184 */       gfx.drawLine(x + w - 2, y + h - 2, x + w - 2, y + h - 2);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintButtonBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state, boolean showBorder) {
/* 189 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/* 190 */     if (highContrast.booleanValue()) {
/* 191 */       super.paintButtonBackground(c, g, rect, orientation, state, showBorder);
/* 192 */       return;
/*     */     }
/*     */ 
/* 195 */     if (!SystemInfo.isJdk6Above()) {
/* 196 */       getDefaultPainter().paintButtonBackground(c, g, rect, orientation, state, showBorder);
/* 197 */       return;
/*     */     }
/* 199 */     Color background = null;
/* 200 */     switch (state) {
/*     */     case 0:
/* 202 */       background = c.getBackground();
/* 203 */       break;
/*     */     case 2:
/* 205 */       if (!(c instanceof ComponentStateSupport)) break;
/* 206 */       background = ((ComponentStateSupport)c).getBackgroundOfState(state); break;
/*     */     case 3:
/* 210 */       if (!(c instanceof ComponentStateSupport)) break;
/* 211 */       background = ((ComponentStateSupport)c).getBackgroundOfState(state); break;
/*     */     case 1:
/* 215 */       if (!(c instanceof ComponentStateSupport)) break;
/* 216 */       background = ((ComponentStateSupport)c).getBackgroundOfState(state);
/*     */     }
/*     */ 
/* 220 */     if ((background != null) && (!(background instanceof UIResource))) {
/* 221 */       getDefaultPainter().paintButtonBackground(c, g, rect, orientation, state, showBorder);
/* 222 */       return;
/*     */     }
/* 224 */     int x = rect.x;
/* 225 */     int y = rect.y;
/* 226 */     int width = rect.width;
/* 227 */     int height = rect.height;
/*     */ 
/* 229 */     if (!showBorder) {
/* 230 */       x--;
/* 231 */       y--;
/* 232 */       width += 2;
/* 233 */       height += 2;
/*     */     }
/*     */ 
/* 236 */     Graphics2D g2d = (Graphics2D)g.create();
/*     */ 
/* 239 */     if (state == 1) {
/* 240 */       paintShadowedButtonBackground(g2d, rect, new Color[] { new Color(16624913), new Color(16161816), new Color(16229415), new Color(15771740) }, new Color[] { new Color(16623720), new Color(16551741), new Color(16560957) });
/*     */     }
/* 244 */     else if (state == 2) {
/* 245 */       if (c.getClientProperty("isMenuPartButton") == Boolean.TRUE) {
/* 246 */         if (1 != height - 2) {
/* 247 */           g2d.setPaint(JideSwingUtilities.getLinearGradientPaint(x + 1, y + 1, x + 1, y + height - 2, new float[] { 0.0F, 0.5F, 0.51F, 1.0F }, new Color[] { new Color(16776930), new Color(16768115), new Color(16766560), new Color(16771752) }));
/*     */         }
/*     */ 
/* 251 */         g2d.fillRect(x + 1, y + 1, width - 2, height - 2);
/*     */       }
/*     */       else
/*     */       {
/* 255 */         g2d.setPaint(new GradientPaint(x, y, new Color(16777215), x, y + height, new Color(16773982)));
/* 256 */         g2d.fillRect(x + 1, y + 1, width - 2, height - 2);
/* 257 */         g2d.setPaint(new GradientPaint(x, y, new Color(16775058), x + width >> 1, y, new Color(16777215), true));
/* 258 */         g2d.drawLine(x, y + height - 2, x + width, y + height - 2);
/* 259 */         if (2 != height - 4) {
/* 260 */           g2d.setPaint(JideSwingUtilities.getLinearGradientPaint(x + 2, y + 2, x + 2, y + height - 4, new float[] { 0.0F, 0.36F, 0.37F, 0.38F, 1.0F }, new Color[] { new Color(16776671), new Color(16770964), new Color(16699744), new Color(16698712), new Color(16770964) }));
/*     */         }
/*     */ 
/* 264 */         g2d.fillRect(x + 2, y + 2, width - 4, height - 4);
/*     */       }
/*     */ 
/*     */     }
/* 275 */     else if (state == 3) {
/* 276 */       paintShadowedButtonBackground(g2d, rect, new Color[] { new Color(15978405), new Color(15774041), new Color(15839569), new Color(16500832) }, new Color[] { new Color(16633240), new Color(16298843), new Color(16504194) });
/*     */     }
/* 280 */     else if (state == 0) {
/* 281 */       if (1 != height - 2) {
/* 282 */         g2d.setPaint(JideSwingUtilities.getLinearGradientPaint(x + 1, y + 1, x + 1, y + height - 2, new float[] { 0.0F, 0.5F, 0.51F, 1.0F }, new Color[] { new Color(15266300), new Color(15266300), new Color(13820404), new Color(15463421) }));
/*     */       }
/*     */ 
/* 286 */       g2d.fillRect(x + 1, y + 1, width - 2, height - 2);
/*     */     }
/*     */ 
/* 289 */     if (showBorder) {
/* 290 */       g2d.setComposite(AlphaComposite.getInstance(10));
/* 291 */       paintButtonBorder(c, g2d, rect, state);
/*     */     }
/*     */ 
/* 294 */     g2d.dispose();
/*     */   }
/*     */ 
/*     */   static void paintShadowedButtonBackground(Graphics2D gfx, Rectangle rect, Color[] baseColors, Color[] innerBackgroundColors) {
/* 298 */     assert ((baseColors.length == 4) && (innerBackgroundColors.length == 3));
/*     */ 
/* 300 */     int x = rect.x;
/* 301 */     int y = rect.y;
/* 302 */     int width = rect.width;
/* 303 */     int height = rect.height;
/*     */ 
/* 306 */     if (1 != height - 2) {
/* 307 */       gfx.setPaint(JideSwingUtilities.getLinearGradientPaint(x + 1, y + 1, x + width - 2, y + height - 2, new float[] { 0.0F, 0.6F, 0.61F, 1.0F }, baseColors));
/*     */     }
/*     */ 
/* 312 */     gfx.fillRect(x + 1, y + 1, width - 2, height - 2);
/*     */ 
/* 314 */     Area base = new Area(new Rectangle(x + 2, y + 1, width - 4, height - 3));
/* 315 */     base.subtract(new Area(new Rectangle(x + 2, y + height - 3, 1, 1)));
/* 316 */     base.subtract(new Area(new Rectangle(x + width - 3, y + height - 3, 1, 1)));
/* 317 */     if (2 != height - 4) {
/* 318 */       gfx.setPaint(JideSwingUtilities.getLinearGradientPaint(x + 2, y + 2, x + 2, y + height - 4, new float[] { 0.39F, 0.4F, 1.0F }, innerBackgroundColors));
/*     */     }
/*     */ 
/* 323 */     gfx.fill(base);
/*     */   }
/*     */ 
/*     */   public void paintStatusBarBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 366 */     if (c.isOpaque()) {
/* 367 */       Color[] bg = state == 0 ? STATUS_BAR_BG : SPECIAL_STATUS_BAR_BG;
/* 368 */       Graphics2D g2d = (Graphics2D)g.create();
/* 369 */       int y = rect.y;
/* 370 */       g2d.setColor(bg[0]);
/* 371 */       g2d.drawLine(rect.x, y, rect.x + rect.width, y);
/* 372 */       y++;
/* 373 */       g2d.setColor(bg[1]);
/* 374 */       g2d.drawLine(rect.x, y, rect.x + rect.width, y);
/* 375 */       y++;
/*     */ 
/* 377 */       Rectangle r = new Rectangle(rect.x, y, rect.width, (rect.height - 4) / 3);
/* 378 */       JideSwingUtilities.fillGradient(g2d, r, bg[2], bg[3], true);
/* 379 */       r.y += r.height;
/* 380 */       r.height = (rect.height - r.y - 2);
/* 381 */       JideSwingUtilities.fillGradient(g2d, r, bg[4], bg[5], true);
/* 382 */       y = r.y + r.height;
/*     */ 
/* 384 */       g2d.setColor(bg[6]);
/* 385 */       g2d.drawLine(rect.x, y, rect.x + rect.width, y);
/* 386 */       y++;
/* 387 */       g2d.setColor(bg[7]);
/* 388 */       g2d.drawLine(rect.x, y, rect.x + rect.width, y);
/*     */ 
/* 390 */       g2d.dispose();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintStatusBarSeparator(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 401 */     g.setColor(new Color(9284821));
/* 402 */     g.drawLine(rect.x, rect.y, rect.x, rect.y + rect.height);
/* 403 */     g.setColor(new Color(16777215));
/* 404 */     g.drawLine(rect.x + 1, rect.y, rect.x + 1, rect.y + rect.height);
/*     */   }
/*     */ 
/*     */   public void paintMenuShadow(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 408 */     if (c.getClientProperty("vsnet.paintShadow") != Boolean.FALSE) {
/* 409 */       super.paintMenuShadow(c, g, rect, orientation, state);
/* 410 */       g.setColor(new Color(12961221));
/* 411 */       g.drawLine(rect.x + rect.width, rect.y, rect.x + rect.width, rect.y + rect.height);
/*     */     }
/*     */     else {
/* 414 */       g.setColor(getMenuItemBackground());
/* 415 */       g.fillRect(rect.x, rect.y, rect.width, rect.height);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintMenuItemBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state, boolean showBorder) {
/* 420 */     if (c.getClientProperty("isMenuPartButton") == Boolean.TRUE) {
/* 421 */       paintButtonBackground(c, g, rect, orientation, state, showBorder);
/*     */     }
/*     */     else
/* 424 */       JideSwingUtilities.drawImageBorder(g, IconsFactory.getImageIcon(Office2007Painter.class, "icons/menu_item_bg.png"), rect, new Insets(2, 2, 2, 2), true);
/*     */   }
/*     */ 
/*     */   public void paintPopupMenuSeparator(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 430 */     super.paintPopupMenuSeparator(c, g, rect, orientation, state);
/* 431 */     g.setColor(new Color(12961221));
/* 432 */     g.drawLine(rect.x + 24, rect.y, rect.x + 24, rect.y + rect.height);
/*     */   }
/*     */ 
/*     */   public void paintDropDownIcon(Graphics g, int x, int y) {
/* 436 */     g.setColor(new Color(5668273));
/* 437 */     g.drawLine(x, y, x + 5, y);
/* 438 */     g.drawLine(x + 1, y + 1, x + 4, y + 1);
/* 439 */     g.drawLine(x + 2, y + 2, x + 3, y + 2);
/*     */   }
/*     */ 
/*     */   public void paintCommandBarBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 444 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/* 445 */     if (highContrast.booleanValue()) {
/* 446 */       super.paintCommandBarBackground(c, g, rect, orientation, state);
/* 447 */       return;
/*     */     }
/*     */ 
/* 450 */     if (!SystemInfo.isJdk6Above()) {
/* 451 */       getDefaultPainter().paintCommandBarBackground(c, g, rect, orientation, state);
/* 452 */       return;
/*     */     }
/* 454 */     int x = rect.x;
/* 455 */     int y = rect.y;
/* 456 */     int width = rect.width;
/* 457 */     int height = rect.height;
/*     */ 
/* 459 */     Graphics2D g2d = (Graphics2D)g.create();
/* 460 */     Color[] colors = { new Color(15266300), new Color(15266300), new Color(13820404), new Color(15463421) };
/* 461 */     for (int i = 0; i < colors.length; i++) {
/* 462 */       Color color = colors[i];
/* 463 */       colors[i] = ColorUtils.getDerivedColor(color, 0.47F);
/*     */     }
/* 465 */     if (1 != height - 2) {
/* 466 */       g2d.setPaint(JideSwingUtilities.getLinearGradientPaint(x + 1, y + 1, x + 1, y + height - 2, new float[] { 0.0F, 0.5F, 0.51F, 1.0F }, colors));
/*     */     }
/*     */ 
/* 470 */     g2d.fillRect(x + 1, y + 1, width - 2, height - 2);
/* 471 */     AlphaComposite alphaComposite = AlphaComposite.getInstance(10);
/* 472 */     g2d.setComposite(SystemInfo.isJdk6Above() ? alphaComposite.derive(0.1F) : AlphaComposite.getInstance(alphaComposite.getRule(), 0.1F));
/* 473 */     paintButtonBorder(c, g2d, rect, state);
/* 474 */     g2d.dispose();
/*     */   }
/*     */ 
/*     */   public void paintFloatingCommandBarBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 479 */     if (!SystemInfo.isJdk6Above()) {
/* 480 */       getDefaultPainter().paintFloatingCommandBarBackground(c, g, rect, orientation, state);
/* 481 */       return;
/*     */     }
/* 483 */     int x = rect.x;
/* 484 */     int y = rect.y;
/* 485 */     int width = rect.width;
/* 486 */     int height = rect.height;
/*     */ 
/* 488 */     Graphics2D g2d = (Graphics2D)g.create();
/* 489 */     Color[] colors = { new Color(15266300), new Color(15266300), new Color(13820404), new Color(15463421) };
/* 490 */     for (int i = 0; i < colors.length; i++) {
/* 491 */       Color color = colors[i];
/* 492 */       colors[i] = ColorUtils.getDerivedColor(color, 0.48F);
/*     */     }
/* 494 */     if (height != 0) {
/* 495 */       g2d.setPaint(JideSwingUtilities.getLinearGradientPaint(x, y, x, y + height, new float[] { 0.0F, 0.5F, 0.51F, 1.0F }, colors));
/*     */     }
/*     */ 
/* 499 */     g2d.fillRect(x, y, width, height);
/* 500 */     g2d.dispose();
/*     */   }
/*     */ 
/*     */   public void paintDockableFrameTitlePane(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 545 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/* 546 */     if (highContrast.booleanValue()) {
/* 547 */       super.paintDockableFrameTitlePane(c, g, rect, orientation, state);
/* 548 */       return;
/*     */     }
/*     */ 
/* 551 */     if (!SystemInfo.isJdk6Above()) {
/* 552 */       getDefaultPainter().paintDockableFrameTitlePane(c, g, rect, orientation, state);
/* 553 */       return;
/*     */     }
/* 555 */     int x = rect.x;
/* 556 */     int y = rect.y;
/* 557 */     int w = rect.width;
/* 558 */     int h = rect.height;
/* 559 */     if (c.getBorder() != null) {
/* 560 */       Insets insets = c.getBorder().getBorderInsets(c);
/* 561 */       x += insets.left;
/* 562 */       y += insets.top;
/* 563 */       w -= insets.right + insets.left;
/* 564 */       h -= insets.top + insets.bottom;
/*     */     }
/* 566 */     boolean active = state == 3;
/* 567 */     Color[] colors = active ? ACTIVE_DOCKABLE_FRAME_TITLE_BAR_BG : DOCKABLE_FRAME_TITLE_BAR_BG;
/* 568 */     Graphics2D g2d = (Graphics2D)g.create();
/* 569 */     Color old = g2d.getColor();
/* 570 */     g2d.setColor(Color.WHITE);
/* 571 */     g2d.drawLine(x, y, x + w, y);
/* 572 */     g2d.drawLine(x, y, x, y + h);
/* 573 */     g2d.setColor(old);
/* 574 */     if (h != 0) {
/* 575 */       g2d.setPaint(JideSwingUtilities.getLinearGradientPaint(x + 1, y + 1, x + 1, y + h - 1, new float[] { 0.0F, 0.333F, 0.334F, 1.0F }, colors));
/*     */     }
/*     */ 
/* 579 */     g2d.fillRect(x + 1, y + 1, w - 1, h - 1);
/* 580 */     g2d.dispose();
/*     */   }
/*     */ 
/*     */   public void paintCollapsiblePaneTitlePaneBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 585 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/* 586 */     if (highContrast.booleanValue()) {
/* 587 */       super.paintCollapsiblePaneTitlePaneBackground(c, g, rect, orientation, state);
/* 588 */       return;
/*     */     }
/* 590 */     if (!SystemInfo.isJdk6Above()) {
/* 591 */       getDefaultPainter().paintCollapsiblePaneTitlePaneBackground(c, g, rect, orientation, state);
/* 592 */       return;
/*     */     }
/* 594 */     paintCollapsiblePaneTitlePane(c, g, rect, COLLAPSIBLE_PANE_TITLE_BAR_BG, orientation, state);
/*     */   }
/*     */ 
/*     */   public void paintCollapsiblePaneTitlePaneBackgroundEmphasized(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 599 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/* 600 */     if (highContrast.booleanValue()) {
/* 601 */       super.paintCollapsiblePaneTitlePaneBackgroundEmphasized(c, g, rect, orientation, state);
/* 602 */       return;
/*     */     }
/* 604 */     if (!SystemInfo.isJdk6Above()) {
/* 605 */       getDefaultPainter().paintCollapsiblePaneTitlePaneBackgroundEmphasized(c, g, rect, orientation, state);
/* 606 */       return;
/*     */     }
/* 608 */     paintCollapsiblePaneTitlePane(c, g, rect, EMPHASIZED_COLLAPSIBLE_PANE_TITLE_BAR_BG, orientation, state);
/*     */   }
/*     */ 
/*     */   public void paintCollapsiblePaneTitlePaneBackgroundSeparatorEmphasized(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 613 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/* 614 */     if (highContrast.booleanValue()) {
/* 615 */       super.paintCollapsiblePaneTitlePaneBackgroundSeparatorEmphasized(c, g, rect, orientation, state);
/* 616 */       return;
/*     */     }
/* 618 */     if (!SystemInfo.isJdk6Above()) {
/* 619 */       getDefaultPainter().paintCollapsiblePaneTitlePaneBackgroundSeparatorEmphasized(c, g, rect, orientation, state);
/* 620 */       return;
/*     */     }
/* 622 */     paintCollapsiblePaneTitlePaneSeparator(c, g, rect, EMPHASIZED_COLLAPSIBLE_PANE_TITLE_BAR_SEPARATOR_BG, state);
/*     */   }
/*     */ 
/*     */   public void paintCollapsiblePaneTitlePaneBackgroundSeparator(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 627 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/* 628 */     if (highContrast.booleanValue()) {
/* 629 */       super.paintCollapsiblePaneTitlePaneBackgroundSeparator(c, g, rect, orientation, state);
/* 630 */       return;
/*     */     }
/* 632 */     if (!SystemInfo.isJdk6Above()) {
/* 633 */       getDefaultPainter().paintCollapsiblePaneTitlePaneBackgroundSeparator(c, g, rect, orientation, state);
/* 634 */       return;
/*     */     }
/* 636 */     paintCollapsiblePaneTitlePaneSeparator(c, g, rect, COLLAPSIBLE_PANE_TITLE_BAR_SEPARATOR_BG, state);
/*     */   }
/*     */ 
/*     */   private void paintCollapsiblePaneTitlePane(JComponent c, Graphics g, Rectangle rect, Color[] colors, int orientation, int state) {
/* 640 */     int x = rect.x;
/* 641 */     int y = rect.y;
/* 642 */     int w = rect.width;
/* 643 */     int h = rect.height;
/* 644 */     if (c.getBorder() != null) {
/* 645 */       Insets insets = c.getBorder().getBorderInsets(c);
/* 646 */       x += insets.left;
/* 647 */       y += insets.top;
/* 648 */       w -= insets.right + insets.left;
/* 649 */       h -= insets.top + insets.bottom;
/*     */     }
/* 651 */     if (h != 0) {
/* 652 */       Graphics2D g2d = (Graphics2D)g.create();
/* 653 */       Paint paint = null;
/* 654 */       switch (orientation) {
/*     */       case 3:
/* 656 */         if (state == 2) {
/* 657 */           Color[] newColors = new Color[colors.length];
/* 658 */           for (int i = 0; i < colors.length; i++) {
/* 659 */             Color color = colors[i];
/* 660 */             newColors[i] = ColorUtils.getDerivedColor(color, 0.6F);
/*     */           }
/* 662 */           paint = JideSwingUtilities.getLinearGradientPaint(x, y, x + w, y, new float[] { 0.0F, 0.333F, 0.334F, 1.0F }, newColors);
/*     */         }
/*     */         else {
/* 665 */           paint = JideSwingUtilities.getLinearGradientPaint(x, y, x + w, y, new float[] { 0.0F, 0.333F, 0.334F, 1.0F }, colors);
/*     */         }
/* 667 */         break;
/*     */       case 7:
/* 669 */         if (state == 2) {
/* 670 */           Color[] newColors = new Color[colors.length];
/* 671 */           for (int i = 0; i < colors.length; i++) {
/* 672 */             Color color = colors[i];
/* 673 */             newColors[i] = ColorUtils.getDerivedColor(color, 0.6F);
/*     */           }
/* 675 */           paint = JideSwingUtilities.getLinearGradientPaint(x + w, y, x, y, new float[] { 0.0F, 0.333F, 0.334F, 1.0F }, newColors);
/*     */         }
/*     */         else {
/* 678 */           paint = JideSwingUtilities.getLinearGradientPaint(x + w, y, x, y, new float[] { 0.0F, 0.333F, 0.334F, 1.0F }, colors);
/*     */         }
/* 680 */         break;
/*     */       case 1:
/* 682 */         if (state == 2) {
/* 683 */           Color[] newColors = new Color[colors.length];
/* 684 */           for (int i = 0; i < colors.length; i++) {
/* 685 */             Color color = colors[i];
/* 686 */             newColors[i] = ColorUtils.getDerivedColor(color, 0.6F);
/*     */           }
/* 688 */           paint = JideSwingUtilities.getLinearGradientPaint(x, y + h, x, y, new float[] { 0.0F, 0.333F, 0.334F, 1.0F }, newColors);
/*     */         }
/*     */         else {
/* 691 */           paint = JideSwingUtilities.getLinearGradientPaint(x, y + h, x, y, new float[] { 0.0F, 0.333F, 0.334F, 1.0F }, colors);
/*     */         }
/* 693 */         break;
/*     */       case 5:
/* 695 */         if (state == 2) {
/* 696 */           Color[] newColors = new Color[colors.length];
/* 697 */           for (int i = 0; i < colors.length; i++) {
/* 698 */             Color color = colors[i];
/* 699 */             newColors[i] = ColorUtils.getDerivedColor(color, 0.6F);
/*     */           }
/* 701 */           paint = JideSwingUtilities.getLinearGradientPaint(x, y, x, y + h, new float[] { 0.0F, 0.333F, 0.334F, 1.0F }, newColors);
/*     */         }
/*     */         else {
/* 704 */           paint = JideSwingUtilities.getLinearGradientPaint(x, y, x, y + h, new float[] { 0.0F, 0.333F, 0.334F, 1.0F }, colors);
/*     */         }case 2:
/*     */       case 4:
/*     */       case 6:
/* 708 */       }g2d.setPaint(paint);
/* 709 */       g2d.fillRect(x, y, w, h);
/* 710 */       g2d.dispose();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void paintCollapsiblePaneTitlePaneSeparator(JComponent c, Graphics g, Rectangle rect, Color[] colors, int state) {
/* 715 */     int x = rect.x;
/* 716 */     int y = rect.y;
/* 717 */     int w = rect.width;
/* 718 */     int h = rect.height;
/* 719 */     if (c.getBorder() != null) {
/* 720 */       Insets insets = c.getBorder().getBorderInsets(c);
/* 721 */       x += insets.left;
/* 722 */       y += insets.top;
/* 723 */       w -= insets.right + insets.left;
/* 724 */       h -= insets.top + insets.bottom;
/*     */     }
/* 726 */     if (h != 0) {
/* 727 */       Graphics2D g2d = (Graphics2D)g.create();
/* 728 */       if (state == 2) {
/* 729 */         Color[] newColors = new Color[colors.length];
/* 730 */         for (int i = 0; i < colors.length; i++) {
/* 731 */           Color color = colors[i];
/* 732 */           newColors[i] = ColorUtils.getDerivedColor(color, 0.6F);
/*     */         }
/* 734 */         g2d.setPaint(JideSwingUtilities.getLinearGradientPaint(x, y, x + w, y, new float[] { 0.0F, 0.5F, 1.0F }, newColors));
/*     */       }
/*     */       else {
/* 737 */         g2d.setPaint(JideSwingUtilities.getLinearGradientPaint(x, y, x + w, y, new float[] { 0.0F, 0.5F, 1.0F }, colors));
/*     */       }
/* 739 */       g2d.fillRect(x, y, w, h);
/* 740 */       g2d.dispose();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintCollapsiblePanesBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 746 */     if (!c.isOpaque()) {
/* 747 */       return;
/*     */     }
/* 749 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/* 750 */     if (highContrast.booleanValue()) {
/* 751 */       super.paintCollapsiblePanesBackground(c, g, rect, orientation, state);
/* 752 */       return;
/*     */     }
/* 754 */     Graphics2D g2d = (Graphics2D)g;
/* 755 */     if (!(c.getBackground() instanceof UIResource)) {
/* 756 */       JideSwingUtilities.fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), c.getBackground(), ColorUtils.getDerivedColor(c.getBackground(), 0.6F), orientation == 0);
/*     */     }
/*     */     else
/*     */     {
/* 763 */       JideSwingUtilities.fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), UIDefaultsLookup.getColor("CollapsiblePanes.backgroundLt"), UIDefaultsLookup.getColor("CollapsiblePanes.backgroundDk"), orientation == 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintSidePaneItemBackground(JComponent c, Graphics g, Rectangle rect, Color[] colors, int orientation, int state)
/*     */   {
/* 773 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/* 774 */     if (highContrast.booleanValue()) {
/* 775 */       super.paintSidePaneItemBackground(c, g, rect, colors, orientation, state);
/* 776 */       return;
/*     */     }
/* 778 */     Graphics2D g2d = (Graphics2D)g.create();
/* 779 */     switch (orientation) {
/*     */     case 3:
/*     */     case 7:
/* 782 */       g2d.rotate(-Math.toRadians(90.0D), rect.x, rect.y);
/* 783 */       g2d.translate(-rect.height, rect.y);
/*     */ 
/* 785 */       paintButtonBackground(c, g2d, new Rectangle(0, 0, rect.height, rect.width), 0, state, false);
/* 786 */       break;
/*     */     case 1:
/*     */     case 5:
/* 789 */       paintButtonBackground(c, g2d, rect, 0, state, false);
/*     */     case 2:
/*     */     case 4:
/* 792 */     case 6: } g2d.dispose();
/*     */   }
/*     */ 
/*     */   public void paintHeaderBoxBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 797 */     boolean paintBorder = (!(c instanceof AbstractButton)) || (((AbstractButton)c).isBorderPainted());
/* 798 */     paintButtonBackground(c, g, rect, orientation, state, paintBorder);
/* 799 */     if (!paintBorder) {
/* 800 */       Color old = g.getColor();
/* 801 */       g.setColor(UIDefaultsLookup.getColor("Table.gridColor"));
/* 802 */       g.drawLine(rect.x + rect.width - 1, rect.y, rect.x + rect.width - 1, rect.y + rect.height - 1);
/* 803 */       g.drawLine(rect.x, rect.y + rect.height - 1, rect.x + rect.width - 1, rect.y + rect.height - 1);
/* 804 */       g.setColor(old);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintGripper(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 810 */     if (rect.width > 30) {
/* 811 */       orientation = 1;
/*     */     }
/* 813 */     else if (rect.height > 30) {
/* 814 */       orientation = 0;
/*     */     }
/*     */ 
/* 817 */     int h = orientation == 0 ? rect.height : rect.width;
/* 818 */     int count = Math.min(9, (h - 6) / 4);
/* 819 */     int y = rect.y;
/* 820 */     int x = rect.x;
/*     */ 
/* 822 */     if (orientation == 0) {
/* 823 */       y += rect.height / 2 - count * 2;
/* 824 */       x += rect.width / 2 - 1;
/*     */     }
/*     */     else {
/* 827 */       x += rect.width / 2 - count * 2;
/* 828 */       y += rect.height / 2 - 1;
/*     */     }
/*     */ 
/* 831 */     for (int i = 0; i < count; i++) {
/* 832 */       g.setColor(getGripperForegroundLt());
/* 833 */       g.fillRect(x + 1, y + 1, 2, 2);
/* 834 */       g.setColor(getGripperForeground());
/* 835 */       g.fillRect(x, y, 2, 2);
/* 836 */       g.setColor(ColorUtils.getDerivedColor(getGripperForeground(), 0.55F));
/* 837 */       g.fillRect(x + 1, y + 1, 1, 1);
/* 838 */       if (orientation == 0) {
/* 839 */         y += 4;
/*     */       }
/*     */       else
/* 842 */         x += 4;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Color getGripperForegroundLt()
/*     */   {
/* 848 */     return UIDefaultsLookup.getColor("Gripper.light");
/*     */   }
/*     */ 
/*     */   public Color getSelectionSelectedDk()
/*     */   {
/* 853 */     return new ColorUIResource(16305011);
/*     */   }
/*     */ 
/*     */   public Color getSelectionSelectedLt()
/*     */   {
/* 858 */     return new ColorUIResource(16710613);
/*     */   }
/*     */ 
/*     */   public Color getBackgroundDk() {
/* 862 */     return UIDefaultsLookup.getColor("JideTabbedPane.background");
/*     */   }
/*     */ 
/*     */   public Color getBackgroundLt() {
/* 866 */     return UIDefaultsLookup.getColor("JideTabbedPane.background");
/*     */   }
/*     */ 
/*     */   public void fillBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state, Color color) {
/* 870 */     Graphics2D g2d = (Graphics2D)g.create();
/* 871 */     if (orientation == 0) {
/* 872 */       int topHeight = rect.height / 3;
/* 873 */       JideSwingUtilities.fillGradient((Graphics2D)g, new Rectangle(rect.x, rect.y, rect.width, topHeight), ColorUtils.getDerivedColor(color, 0.74F), ColorUtils.getDerivedColor(color, 0.64F), true);
/*     */ 
/* 875 */       JideSwingUtilities.fillGradient((Graphics2D)g, new Rectangle(rect.x, rect.y + topHeight, rect.width, rect.height - topHeight), color, ColorUtils.getDerivedColor(color, 0.64F), true);
/*     */     }
/*     */     else
/*     */     {
/* 879 */       int leftWidth = rect.width / 3;
/* 880 */       JideSwingUtilities.fillGradient((Graphics2D)g, new Rectangle(rect.x, rect.y, leftWidth, rect.height), ColorUtils.getDerivedColor(color, 0.74F), ColorUtils.getDerivedColor(color, 0.64F), false);
/*     */ 
/* 882 */       JideSwingUtilities.fillGradient((Graphics2D)g, new Rectangle(rect.x + leftWidth, rect.y, rect.width - leftWidth, rect.height), color, ColorUtils.getDerivedColor(color, 0.64F), false);
/*     */     }
/*     */ 
/* 885 */     g2d.dispose();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 343 */     STATUS_BAR_BG = new Color[] { new Color(5668272), new Color(16777215), new Color(14149369), new Color(13098232), new Color(11784437), new Color(14149111), new Color(13492471), new Color(12244215) };
/*     */ 
/* 354 */     SPECIAL_STATUS_BAR_BG = new Color[] { new Color(5668272), new Color(12967160), new Color(12769783), new Color(11127543), new Color(9484010), new Color(7772357), new Color(7640514), new Color(9809358) };
/*     */ 
/* 503 */     DOCKABLE_FRAME_TITLE_BAR_BG = new Color[] { new Color(15070461), new Color(14217212), new Color(12510973), new Color(10934772) };
/*     */ 
/* 510 */     ACTIVE_DOCKABLE_FRAME_TITLE_BAR_BG = new Color[] { new Color(16769959), new Color(16764785), new Color(16761679), new Color(16762964) };
/*     */ 
/* 517 */     COLLAPSIBLE_PANE_TITLE_BAR_BG = new Color[] { new Color(14149369), new Color(13098232), new Color(11784437), new Color(14149111) };
/*     */ 
/* 524 */     EMPHASIZED_COLLAPSIBLE_PANE_TITLE_BAR_BG = new Color[] { new Color(15200510), new Color(12178423), new Color(10863853), new Color(9415117) };
/*     */ 
/* 531 */     COLLAPSIBLE_PANE_TITLE_BAR_SEPARATOR_BG = new Color[] { new Color(13098232), new Color(14149369), new Color(13098232) };
/*     */ 
/* 537 */     EMPHASIZED_COLLAPSIBLE_PANE_TITLE_BAR_SEPARATOR_BG = new Color[] { new Color(12178423), new Color(15200510), new Color(12178423) };
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.office2007.Office2007Painter
 * JD-Core Version:    0.6.0
 */
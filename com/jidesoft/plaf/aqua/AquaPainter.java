/*     */ package com.jidesoft.plaf.aqua;
/*     */ 
/*     */ import com.jidesoft.icons.IconsFactory;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.plaf.basic.BasicPainter;
/*     */ import com.jidesoft.plaf.basic.ThemePainter;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.util.logging.Logger;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JComponent;
/*     */ 
/*     */ public class AquaPainter extends BasicPainter
/*     */ {
/*  25 */   private static final Logger LOGGER = Logger.getLogger(AquaPainter.class.getName());
/*     */   private static AquaPainter _instance;
/*  28 */   private static final ImageIcon SELECTED = IconsFactory.getImageIcon(AquaPainter.class, "icons/selected.gif");
/*  29 */   private static final ImageIcon ROLLOVER = IconsFactory.getImageIcon(AquaPainter.class, "icons/rollover.gif");
/*  30 */   private static final ImageIcon PRESSED = IconsFactory.getImageIcon(AquaPainter.class, "icons/pressed.gif");
/*  31 */   private static final Color ROLLOVER_BACKGROUND = new Color(238, 238, 238);
/*  32 */   private static final Color SELECTED_BACKGROUND = new Color(153, 153, 153);
/*  33 */   private static final Color PRESSED_BACKGROUND = new Color(195, 195, 195);
/*     */   private static boolean _errorOccurred;
/* 137 */   private static Color ACTIVE_TOP_GRADIENT_COLOR = new Color(12369084);
/* 138 */   private static Color ACTIVE_BOTTOM_GRADIENT_COLOR = new Color(10132122);
/* 139 */   private static Color INACTIVE_TOP_GRADIENT_COLOR = new Color(15000804);
/* 140 */   private static Color INACTIVE_BOTTOM_GRADIENT_COLOR = new Color(13750737);
/*     */ 
/*     */   public static ThemePainter getInstance()
/*     */   {
/*  37 */     if (_instance == null) {
/*  38 */       _instance = new AquaPainter();
/*     */     }
/*  40 */     return _instance;
/*     */   }
/*     */ 
/*     */   public Color getCommandBarTitleBarBackground()
/*     */   {
/*  48 */     return UIDefaultsLookup.getColor("JideButton.background");
/*     */   }
/*     */ 
/*     */   public void paintButtonBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/*  53 */     if (state == 0) {
/*  54 */       super.paintButtonBackground(c, g, rect, orientation, state);
/*     */     }
/*  56 */     else if (state == 2) {
/*  57 */       paintImageBorder(g, rect, ROLLOVER, ROLLOVER_BACKGROUND);
/*     */     }
/*  59 */     else if (state == 3) {
/*  60 */       paintImageBorder(g, rect, SELECTED, SELECTED_BACKGROUND);
/*     */     }
/*  62 */     else if (state == 1)
/*  63 */       paintImageBorder(g, rect, PRESSED, PRESSED_BACKGROUND);
/*     */   }
/*     */ 
/*     */   private void paintImageBorder(Graphics g, Rectangle rect, ImageIcon icon, Color background)
/*     */   {
/*  68 */     JideSwingUtilities.drawImageBorder(g, icon, rect, new Insets(3, 3, 3, 3), false);
/*     */ 
/*  70 */     if (background != null) {
/*  71 */       Color oldColor = g.getColor();
/*  72 */       g.setColor(background);
/*  73 */       g.fillRect(rect.x + 3, rect.y + 3, rect.width - 6, rect.height - 6);
/*  74 */       g.setColor(oldColor);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintCollapsiblePaneTitlePaneBackgroundEmphasized(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/*  80 */     if (!_errorOccurred) {
/*     */       try {
/*  82 */         drawFrameTitleBackground((Graphics2D)g, rect.x, rect.y, rect.width, rect.height, true, false, false);
/*  83 */         return;
/*     */       }
/*     */       catch (Exception e) {
/*  86 */         _errorOccurred = true;
/*  87 */         LOGGER.warning(e.getLocalizedMessage());
/*     */       }
/*     */     }
/*  90 */     super.paintCollapsiblePaneTitlePaneBackgroundEmphasized(c, g, rect, orientation, state);
/*     */   }
/*     */ 
/*     */   public void paintCollapsiblePaneTitlePaneBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/*  94 */     if (!_errorOccurred) {
/*     */       try {
/*  96 */         drawFrameTitleBackground((Graphics2D)g, rect.x, rect.y, rect.width, rect.height, false, false, false);
/*  97 */         return;
/*     */       }
/*     */       catch (Exception e) {
/* 100 */         _errorOccurred = true;
/* 101 */         LOGGER.warning(e.getLocalizedMessage());
/*     */       }
/*     */     }
/* 104 */     super.paintCollapsiblePaneTitlePaneBackground(c, g, rect, orientation, state);
/*     */   }
/*     */ 
/*     */   public void paintDockableFrameTitlePane(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 108 */     if (!_errorOccurred) {
/*     */       try {
/* 110 */         drawFrameTitleBackground((Graphics2D)g, rect.x, rect.y, rect.width, rect.height, state == 3, false, false);
/* 111 */         return;
/*     */       }
/*     */       catch (Exception e) {
/* 114 */         _errorOccurred = true;
/* 115 */         LOGGER.warning(e.getLocalizedMessage());
/*     */       }
/*     */     }
/* 118 */     super.paintDockableFrameTitlePane(c, g, rect, orientation, state);
/*     */   }
/*     */ 
/*     */   public void paintCommandBarTitlePane(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 123 */     if (!_errorOccurred) {
/*     */       try {
/* 125 */         drawFrameTitleBackground((Graphics2D)g, rect.x, rect.y, rect.width, rect.height, true, false, false);
/* 126 */         return;
/*     */       }
/*     */       catch (Exception e) {
/* 129 */         _errorOccurred = true;
/* 130 */         LOGGER.warning(e.getLocalizedMessage());
/*     */       }
/*     */     }
/* 133 */     super.paintCommandBarTitlePane(c, g, rect, orientation, state);
/*     */   }
/*     */ 
/*     */   private void drawFrameTitleBackground(Graphics2D g, int x, int y, int w, int h, boolean active, boolean c, boolean d)
/*     */   {
/* 144 */     Color topColor = active ? ACTIVE_TOP_GRADIENT_COLOR : INACTIVE_TOP_GRADIENT_COLOR;
/* 145 */     Color bottomColor = active ? ACTIVE_BOTTOM_GRADIENT_COLOR : INACTIVE_BOTTOM_GRADIENT_COLOR;
/* 146 */     JideSwingUtilities.fillGradient(g, new Rectangle(x, y, w, h), topColor, bottomColor, true);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.aqua.AquaPainter
 * JD-Core Version:    0.6.0
 */
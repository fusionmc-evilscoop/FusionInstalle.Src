/*     */ package com.jidesoft.plaf.xerto;
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
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JComponent;
/*     */ 
/*     */ public class XertoPainter extends BasicPainter
/*     */ {
/*     */   private static XertoPainter _instance;
/*  25 */   private static final ImageIcon SELECTED = IconsFactory.getImageIcon(XertoPainter.class, "icons/selected.gif");
/*  26 */   private static final ImageIcon SELECTED_C = IconsFactory.getImageIcon(XertoPainter.class, "icons/selected_c.gif");
/*  27 */   private static final ImageIcon ROLLOVER = IconsFactory.getImageIcon(XertoPainter.class, "icons/rollover.gif");
/*  28 */   private static final ImageIcon ROLLOVER_C = IconsFactory.getImageIcon(XertoPainter.class, "icons/rollover_c.gif");
/*  29 */   private static final ImageIcon PRESSED = IconsFactory.getImageIcon(XertoPainter.class, "icons/pressed.gif");
/*  30 */   private static final ImageIcon PRESSED_C = IconsFactory.getImageIcon(XertoPainter.class, "icons/pressed_c.gif");
/*     */ 
/*     */   public static ThemePainter getInstance() {
/*  33 */     if (_instance == null) {
/*  34 */       _instance = new XertoPainter();
/*     */     }
/*  36 */     return _instance;
/*     */   }
/*     */ 
/*     */   public void paintCollapsiblePaneTitlePaneBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/*  50 */     Graphics2D g2d = (Graphics2D)g;
/*  51 */     Color gradientBot = XertoUtils.getHighlightColor(c.getBackground());
/*  52 */     Color gradientTop = XertoUtils.getLighterColor(c.getBackground());
/*  53 */     JideSwingUtilities.fillGradient(g2d, rect, gradientTop, gradientBot, true);
/*     */   }
/*     */ 
/*     */   public void paintCollapsiblePaneTitlePaneBackgroundEmphasized(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/*  58 */     Graphics2D g2d = (Graphics2D)g;
/*  59 */     Color gradientBot = XertoUtils.getEmBaseColor(c.getBackground());
/*  60 */     Color gradientTop = c.getBackground();
/*  61 */     JideSwingUtilities.fillGradient(g2d, rect, gradientTop, gradientBot, true);
/*     */   }
/*     */ 
/*     */   public void paintMenuItemBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state, boolean showBorder)
/*     */   {
/*  66 */     super.paintMenuItemBackground(c, g, rect, orientation, state, showBorder);
/*     */   }
/*     */ 
/*     */   public void paintButtonBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/*  71 */     if (state == 0) {
/*  72 */       super.paintButtonBackground(c, g, rect, orientation, state);
/*     */     }
/*  74 */     else if (state == 2) {
/*  75 */       paintImageBorder(g, rect, ROLLOVER, ROLLOVER_C, null);
/*     */     }
/*  77 */     else if (state == 3) {
/*  78 */       paintImageBorder(g, rect, SELECTED, SELECTED_C, Color.WHITE);
/*     */     }
/*  80 */     else if (state == 1)
/*  81 */       paintImageBorder(g, rect, PRESSED, PRESSED_C, null);
/*     */   }
/*     */ 
/*     */   private void paintImageBorder(Graphics g, Rectangle rect, ImageIcon icon, ImageIcon center, Color background)
/*     */   {
/*  86 */     JideSwingUtilities.drawImageBorder(g, icon, rect, new Insets(4, 4, 4, 4), false);
/*     */ 
/*  88 */     if (center == null) {
/*  89 */       Color oldColor = g.getColor();
/*  90 */       g.setColor(background);
/*  91 */       g.fillRect(rect.x + 4, rect.y + 4, rect.width - 8, rect.height - 8);
/*  92 */       g.setColor(oldColor);
/*     */     }
/*     */     else {
/*  95 */       g.drawImage(center.getImage(), rect.x + 4, rect.y + 4, rect.x + rect.width - 4, rect.y + rect.height - 4, 0, 0, center.getIconWidth(), center.getIconHeight(), background, null);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintGripper(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 102 */     if (rect.width > 30) {
/* 103 */       orientation = 1;
/*     */     }
/*     */ 
/* 106 */     int h = orientation == 0 ? rect.height : rect.width;
/* 107 */     int count = Math.min(9, (h - 6) / 4);
/* 108 */     int y = rect.y;
/* 109 */     int x = rect.x;
/*     */ 
/* 111 */     if (orientation == 0) {
/* 112 */       y += rect.height / 2 - count * 2;
/* 113 */       x += rect.width / 2 - 1;
/*     */     }
/*     */     else {
/* 116 */       x += rect.width / 2 - count * 2;
/* 117 */       y += rect.height / 2 - 1;
/*     */     }
/*     */ 
/* 120 */     for (int i = 0; i < count; i++) {
/* 121 */       g.setColor(getGripperForegroundLt());
/* 122 */       g.fillRect(x + 1, y + 1, 2, 2);
/* 123 */       g.setColor(XertoUtils.getControlMidShadowColor());
/* 124 */       g.fillRect(x, y, 2, 2);
/* 125 */       g.setColor(XertoUtils.getControlLightShadowColor());
/* 126 */       g.fillRect(x, y, 1, 1);
/* 127 */       g.setColor(XertoUtils.getControlDarkShadowColor());
/* 128 */       g.fillRect(x + 1, y + 1, 1, 1);
/*     */ 
/* 130 */       if (orientation == 0) {
/* 131 */         y += 4;
/*     */       }
/*     */       else
/* 134 */         x += 4;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintDockableFrameTitlePane(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 142 */     Graphics2D g2d = (Graphics2D)g;
/* 143 */     if (3 == state) {
/* 144 */       JideSwingUtilities.fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), XertoUtils.getFrameActiveTitleTopColor(), XertoUtils.getFrameActiveTitleBottomColor(), orientation == 0);
/*     */     }
/*     */     else
/*     */     {
/* 148 */       JideSwingUtilities.fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), XertoUtils.getFrameInactiveTitleTopColor(), XertoUtils.getFrameInactiveTitleBottomColor(), orientation == 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintStatusBarSeparator(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 156 */     int h = orientation == 0 ? c.getHeight() : c.getWidth();
/* 157 */     h -= 3;
/*     */ 
/* 161 */     if (orientation == 0) {
/* 162 */       int x = rect.x;
/* 163 */       int y = rect.y + 1;
/* 164 */       g.setColor(UIDefaultsLookup.getColor("controlShadow"));
/* 165 */       g.drawLine(x, y, x, y + h);
/* 166 */       g.setColor(UIDefaultsLookup.getColor("controlLtHighlight"));
/* 167 */       g.drawLine(x + 1, y, x + 1, y + h);
/*     */     }
/*     */     else {
/* 170 */       int x = rect.x + 1;
/* 171 */       int y = rect.y;
/* 172 */       g.setColor(UIDefaultsLookup.getColor("controlShadow"));
/* 173 */       g.drawLine(x, y, x + h, y);
/* 174 */       g.setColor(UIDefaultsLookup.getColor("controlLtHighlight"));
/* 175 */       g.drawLine(x, y + 1, x + h, y + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Color getGripperForeground()
/*     */   {
/* 182 */     return XertoUtils.getControlLightShadowColor();
/*     */   }
/*     */ 
/*     */   public Color getGripperForegroundLt()
/*     */   {
/* 187 */     return Color.WHITE;
/*     */   }
/*     */ 
/*     */   public Color getSelectionSelectedDk()
/*     */   {
/* 192 */     return XertoUtils.getControlMidShadowColor();
/*     */   }
/*     */ 
/*     */   public Color getSelectionSelectedLt()
/*     */   {
/* 197 */     return XertoUtils.getControlLightShadowColor();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.xerto.XertoPainter
 * JD-Core Version:    0.6.0
 */
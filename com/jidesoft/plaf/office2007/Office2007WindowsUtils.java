/*     */ package com.jidesoft.plaf.office2007;
/*     */ 
/*     */ import com.jidesoft.icons.IconsFactory;
/*     */ import com.jidesoft.plaf.LookAndFeelFactory;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.plaf.WindowsDesktopProperty;
/*     */ import com.jidesoft.plaf.basic.BasicPainter;
/*     */ import com.jidesoft.plaf.basic.Painter;
/*     */ import com.jidesoft.plaf.basic.ThemePainter;
/*     */ import com.jidesoft.plaf.office2003.Office2003WindowsUtils;
/*     */ import com.jidesoft.plaf.vsnet.VsnetWindowsUtils;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import com.jidesoft.utils.ColorUtils;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.InsetsUIResource;
/*     */ 
/*     */ public class Office2007WindowsUtils extends VsnetWindowsUtils
/*     */ {
/*     */   public static void initClassDefaults(UIDefaults table, boolean withMenu)
/*     */   {
/*  39 */     Office2003WindowsUtils.initClassDefaults(table);
/*     */ 
/*  41 */     int products = LookAndFeelFactory.getProductsUsed();
/*     */ 
/*  48 */     table.put("JideTabbedPaneUI", "com.jidesoft.plaf.office2007.Office2007JideTabbedPaneUI");
/*     */ 
/*  52 */     if ((products & 0x1) != 0) {
/*  53 */       table.put("SidePaneUI", "com.jidesoft.plaf.office2007.Office2007SidePaneUI");
/*     */     }
/*     */ 
/*  56 */     if (((products & 0x2) == 0) || 
/*  61 */       ((products & 0x10) != 0));
/*     */   }
/*     */ 
/*     */   public static void initClassDefaults(UIDefaults table)
/*     */   {
/*  74 */     initClassDefaults(table, true);
/*     */   }
/*     */ 
/*     */   public static void initComponentDefaults(UIDefaults table)
/*     */   {
/*  83 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*     */ 
/*  85 */     WindowsDesktopProperty defaultTextColor = new WindowsDesktopProperty("win.button.textColor", table.get("controlText"), toolkit);
/*     */ 
/*  87 */     WindowsDesktopProperty defaultBackgroundColor = new WindowsDesktopProperty("win.3d.backgroundColor", table.get("control"), toolkit);
/*     */ 
/*  89 */     WindowsDesktopProperty defaultLightColor = new WindowsDesktopProperty("win.3d.lightColor", table.get("controlHighlight"), toolkit);
/*  90 */     WindowsDesktopProperty defaultHighlightColor = new WindowsDesktopProperty("win.3d.highlightColor", table.get("controlLtHighlight"), toolkit);
/*  91 */     WindowsDesktopProperty defaultShadowColor = new WindowsDesktopProperty("win.3d.shadowColor", table.get("controlShadow"), toolkit);
/*  92 */     WindowsDesktopProperty defaultDarkShadowColor = new WindowsDesktopProperty("win.3d.darkShadowColor", table.get("controlDkShadow"), toolkit);
/*     */ 
/*  94 */     Color defaultFormBackground = new ColorUIResource(12573695);
/*     */ 
/*  96 */     Object toolbarFont = JideSwingUtilities.getMenuFont(toolkit, table);
/*  97 */     Object boldFont = JideSwingUtilities.getBoldFont(toolkit, table);
/*     */ 
/*  99 */     Painter gripperPainter = new Painter() {
/*     */       public void paint(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 101 */         Object p = UIDefaultsLookup.get("Theme.painter");
/* 102 */         if ((p instanceof ThemePainter)) {
/* 103 */           ((ThemePainter)p).paintGripper(c, g, rect, orientation, state);
/*     */         }
/*     */         else
/* 106 */           BasicPainter.getInstance().paintGripper(c, g, rect, orientation, state);
/*     */       }
/*     */     };
/* 111 */     Object[] uiDefaults = { "MenuItem.checkIcon", IconsFactory.getImageIcon(Office2007Painter.class, "icons/menu_checkbox.png"), "MenuItem.shadowColor", new ColorUIResource(15331054), "PopupMenuSeparator.foreground", new ColorUIResource(12961221), "JideTabbedPane.gripperPainter", gripperPainter, "JideSplitPaneDivider.gripperPainter", gripperPainter, "JideTabbedPane.defaultTabShape", Integer.valueOf(4), "JideTabbedPane.defaultTabColorTheme", Integer.valueOf(2), "JideTabbedPane.contentBorderInsets", new InsetsUIResource(3, 3, 3, 3), "JideTabbedPane.background", defaultFormBackground, "JideButton.margin.vertical", new InsetsUIResource(2, 5, 1, 5), "JideButton.margin", new InsetsUIResource(3, 3, 3, 4), "JideSplitButton.margin.vertical", new InsetsUIResource(2, 5, 1, 5), "JideSplitButton.margin", new InsetsUIResource(3, 3, 3, 4), "JideSplitButton.nonActiveRolloverAlpha", Integer.valueOf(40), "JideSplitButton.textIconGap", Integer.valueOf(4), "JideLabel.background", table.get("Label.background"), "JideLabel.font", table.get("Label.font"), "Gripper.painter", gripperPainter, "Gripper.foreground", new ColorUIResource(6656975), "Gripper.light", new ColorUIResource(16777215) };
/*     */ 
/* 143 */     table.putDefaults(uiDefaults);
/*     */ 
/* 145 */     int products = LookAndFeelFactory.getProductsUsed();
/*     */ 
/* 147 */     if ((products & 0x2) != 0) {
/* 148 */       ImageIcon collapsiblePaneImage = IconsFactory.getImageIcon(Office2007WindowsUtils.class, "icons/collapsible_pane_vista.gif");
/* 149 */       int collapsiblePaneSize = 12;
/*     */ 
/* 151 */       uiDefaults = new Object[] { "StatusBar.childrenOpaque", Boolean.FALSE, "StatusBar.border", BorderFactory.createEmptyBorder(2, 0, 2, 0), "MemoryStatusBarItem.fillColor", new ColorUIResource(16693839), "CollapsiblePane.downIcon", IconsFactory.getIcon(null, collapsiblePaneImage, 0, 0, 12, 12), "CollapsiblePane.upIcon", IconsFactory.getIcon(null, collapsiblePaneImage, 0, 12, 12, 12), "CollapsiblePanes.backgroundLt", new ColorUIResource(12573695), "CollapsiblePanes.backgroundDk", new ColorUIResource(12573695) };
/*     */ 
/* 161 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 164 */     if ((products & 0x1) != 0) {
/* 165 */       ImageIcon titleButtonImage = IconsFactory.getImageIcon(Office2007WindowsUtils.class, "icons/title_buttons_office2007.gif");
/* 166 */       int titleButtonSize = 10;
/*     */ 
/* 168 */       uiDefaults = new Object[] { "ContentContainer.background", defaultFormBackground, "SidePane.background", defaultFormBackground, "DockableFrame.activeTitleBackground", new ColorUIResource(12638704), "DockableFrameTitlePane.gripperPainter", gripperPainter, "DockableFrameTitlePane.hideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 0, 10, 10), "DockableFrameTitlePane.unfloatIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 10, 10, 10), "DockableFrameTitlePane.floatIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 20, 10, 10), "DockableFrameTitlePane.autohideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 30, 10, 10), "DockableFrameTitlePane.stopAutohideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 40, 10, 10), "DockableFrameTitlePane.hideAutohideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 50, 10, 10), "DockableFrameTitlePane.maximizeIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 60, 10, 10), "DockableFrameTitlePane.restoreIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 70, 10, 10), "DockableFrameTitlePane.backgroundLt", new ColorUIResource(ColorUtils.getDerivedColor(new Color(12573695), 0.55F)), "DockableFrameTitlePane.backgroundDk", new ColorUIResource(ColorUtils.getDerivedColor(new Color(12573695), 0.45F)), "DockableFrameTitlePane.activeBackgroundLt", new ColorUIResource(ColorUtils.getDerivedColor(new Color(16439456), 0.55F)), "DockableFrameTitlePane.activeBackgroundDk", new ColorUIResource(ColorUtils.getDerivedColor(new Color(16439456), 0.45F)), "DockableFrameTitlePane.margin", new InsetsUIResource(1, 6, 0, 6), "DockableFrameTitlePane.buttonGap", Integer.valueOf(2) };
/*     */ 
/* 194 */       table.putDefaults(uiDefaults);
/*     */     }
/* 196 */     if ((products & 0x10) != 0) {
/* 197 */       uiDefaults = new Object[] { "Chevron.alwaysVisible", Boolean.TRUE };
/*     */ 
/* 200 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 203 */     if ((products & 0x4) != 0) {
/* 204 */       uiDefaults = new Object[0];
/*     */ 
/* 206 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 209 */     UIDefaultsLookup.put(table, "Theme.painter", Office2007Painter.getInstance());
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.office2007.Office2007WindowsUtils
 * JD-Core Version:    0.6.0
 */
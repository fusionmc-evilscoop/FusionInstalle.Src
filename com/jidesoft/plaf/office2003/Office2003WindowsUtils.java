/*     */ package com.jidesoft.plaf.office2003;
/*     */ 
/*     */ import com.jidesoft.plaf.ExtWindowsDesktopProperty;
/*     */ import com.jidesoft.plaf.LookAndFeelFactory;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.plaf.WindowsDesktopProperty;
/*     */ import com.jidesoft.plaf.basic.Painter;
/*     */ import com.jidesoft.plaf.basic.ThemePainter;
/*     */ import com.jidesoft.plaf.vsnet.ConvertListener;
/*     */ import com.jidesoft.plaf.vsnet.HeaderCellBorder;
/*     */ import com.jidesoft.plaf.vsnet.ResizeFrameBorder;
/*     */ import com.jidesoft.plaf.vsnet.VsnetWindowsUtils;
/*     */ import com.jidesoft.plaf.xerto.SlidingFrameBorder;
/*     */ import com.jidesoft.plaf.xerto.StatusBarBorder;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import com.jidesoft.utils.SecurityUtils;
/*     */ import com.jidesoft.utils.SystemInfo;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIDefaults.ActiveValue;
/*     */ import javax.swing.UIDefaults.LazyInputMap;
/*     */ import javax.swing.plaf.BorderUIResource;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.InsetsUIResource;
/*     */ 
/*     */ public class Office2003WindowsUtils extends VsnetWindowsUtils
/*     */ {
/*     */   public static void initClassDefaults(UIDefaults table, boolean withMenu)
/*     */   {
/*  43 */     if (withMenu) {
/*  44 */       VsnetWindowsUtils.initClassDefaultsWithMenu(table);
/*     */     }
/*     */     else {
/*  47 */       VsnetWindowsUtils.initClassDefaults(table);
/*     */     }
/*     */ 
/*  50 */     int products = LookAndFeelFactory.getProductsUsed();
/*     */ 
/*  52 */     table.put("JideTabbedPaneUI", "com.jidesoft.plaf.office2003.Office2003JideTabbedPaneUI");
/*     */ 
/*  54 */     if ((products & 0x1) != 0) {
/*  55 */       table.put("SidePaneUI", "com.jidesoft.plaf.office2003.Office2003SidePaneUI");
/*     */     }
/*     */ 
/*  58 */     if ((products & 0x2) != 0)
/*  59 */       table.put("CollapsiblePaneUI", "com.jidesoft.plaf.office2003.Office2003CollapsiblePaneUI");
/*     */   }
/*     */ 
/*     */   public static void initClassDefaults(UIDefaults table)
/*     */   {
/*  69 */     initClassDefaults(table, true);
/*     */   }
/*     */ 
/*     */   public static void initComponentDefaults(UIDefaults table)
/*     */   {
/*  78 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*     */ 
/*  80 */     WindowsDesktopProperty defaultTextColor = new WindowsDesktopProperty("win.button.textColor", UIDefaultsLookup.get("controlText"), toolkit);
/*  81 */     WindowsDesktopProperty defaultBackgroundColor = new WindowsDesktopProperty("win.3d.backgroundColor", UIDefaultsLookup.get("control"), toolkit);
/*  82 */     WindowsDesktopProperty defaultLightColor = new WindowsDesktopProperty("win.3d.lightColor", UIDefaultsLookup.get("controlHighlight"), toolkit);
/*  83 */     WindowsDesktopProperty defaultHighlightColor = new WindowsDesktopProperty("win.3d.highlightColor", UIDefaultsLookup.get("controlLtHighlight"), toolkit);
/*  84 */     WindowsDesktopProperty defaultShadowColor = new WindowsDesktopProperty("win.3d.shadowColor", UIDefaultsLookup.get("controlShadow"), toolkit);
/*  85 */     WindowsDesktopProperty defaultDarkShadowColor = new WindowsDesktopProperty("win.3d.darkShadowColor", UIDefaultsLookup.get("controlDkShadow"), toolkit);
/*     */ 
/*  87 */     Object toolbarFont = JideSwingUtilities.getMenuFont(toolkit, table);
/*  88 */     Object boldFont = JideSwingUtilities.getBoldFont(toolkit, table);
/*     */ 
/*  90 */     Painter gripperPainter = new Painter() {
/*     */       public void paint(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/*  92 */         Object p = UIDefaultsLookup.get("Theme.painter");
/*  93 */         if ((p instanceof ThemePainter)) {
/*  94 */           ((ThemePainter)p).paintGripper(c, g, rect, orientation, state);
/*     */         }
/*     */         else
/*  97 */           Office2003Painter.getInstance().paintGripper(c, g, rect, orientation, state);
/*     */       }
/*     */     };
/* 102 */     Object[] uiDefaults = { "JideTabbedPane.defaultTabShape", Integer.valueOf(4), "JideTabbedPane.defaultTabColorTheme", Integer.valueOf(2), "JideTabbedPane.contentBorderInsets", new InsetsUIResource(3, 3, 3, 3), "JideTabbedPane.gripperPainter", gripperPainter, "JideTabbedPane.alwaysShowLineBorder", Boolean.FALSE, "JideTabbedPane.showFocusIndicator", Boolean.TRUE, "JideSplitPaneDivider.gripperPainter", gripperPainter, "Gripper.size", Integer.valueOf(8), "Gripper.painter", gripperPainter, "Icon.floating", Boolean.FALSE, "JideScrollPane.border", UIDefaultsLookup.getBorder("ScrollPane.border"), "Menu.margin", new InsetsUIResource(2, 7, 3, 7), "Menu.submenuPopupOffsetX", Integer.valueOf(1), "Menu.submenuPopupOffsetY", Integer.valueOf(0), "MenuBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(1, 2, 1, 2)), "PopupMenu.background", new UIDefaults.ActiveValue()
/*     */     {
/*     */       public Object createValue(UIDefaults table)
/*     */       {
/* 127 */         return Office2003Painter.getInstance().getMenuItemBackground();
/*     */       }
/*     */     }
/*     */      };
/* 131 */     table.putDefaults(uiDefaults);
/*     */ 
/* 133 */     int products = LookAndFeelFactory.getProductsUsed();
/*     */ 
/* 135 */     if ((products & 0x1) != 0) {
/* 136 */       boolean useShadowBorder = "true".equals(SecurityUtils.getProperty("jide.shadeSlidingBorder", "false"));
/*     */ 
/* 138 */       Object slidingEastFrameBorder = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 141 */           return new SlidingFrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(1, 15, 1, 1));
/*     */         }
/*     */       });
/* 146 */       Object slidingWestFrameBorder = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 149 */           return new SlidingFrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(1, 1, 1, 15));
/*     */         }
/*     */       });
/* 154 */       Object slidingNorthFrameBorder = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 157 */           return new SlidingFrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(1, 1, 15, 1));
/*     */         }
/*     */       });
/* 162 */       Object slidingSouthFrameBorder = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 165 */           return new SlidingFrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(15, 1, 1, 1));
/*     */         }
/*     */       });
/* 170 */       Object slidingEastFrameBorder2 = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 173 */           return new ResizeFrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(0, 4, 0, 0));
/*     */         }
/*     */       });
/* 178 */       Object slidingWestFrameBorder2 = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 181 */           return new ResizeFrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(0, 0, 0, 4));
/*     */         }
/*     */       });
/* 186 */       Object slidingNorthFrameBorder2 = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 189 */           return new ResizeFrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(0, 0, 4, 0));
/*     */         }
/*     */       });
/* 194 */       Object slidingSouthFrameBorder2 = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 197 */           return new ResizeFrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(4, 0, 0, 0));
/*     */         }
/*     */       });
/* 202 */       uiDefaults = new Object[] { "SidePane.foreground", defaultTextColor, "SidePane.lineColor", new UIDefaults.ActiveValue()
/*     */       {
/*     */         public Object createValue(UIDefaults table)
/*     */         {
/* 207 */           return Office2003Painter.getInstance().getControlShadow();
/*     */         }
/*     */       }
/*     */       , "StatusBarItem.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 1, 0, 1)), "StatusBar.border", new StatusBarBorder(), "DockableFrame.titleBorder", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "DockableFrameTitlePane.use3dButtons", Boolean.FALSE, "DockableFrameTitlePane.gripperPainter", gripperPainter, "DockableFrameTitlePane.margin", new InsetsUIResource(1, 6, 1, 6), "DockableFrameTitlePane.contentFilledButtons", Boolean.valueOf(true), "DockableFrame.activeTitleForeground", UIDefaultsLookup.getColor("DockableFrame.inactiveTitleForeground"), "DockableFrame.slidingEastBorder", useShadowBorder ? slidingEastFrameBorder : slidingEastFrameBorder2, "DockableFrame.slidingWestBorder", useShadowBorder ? slidingWestFrameBorder : slidingWestFrameBorder2, "DockableFrame.slidingNorthBorder", useShadowBorder ? slidingNorthFrameBorder : slidingNorthFrameBorder2, "DockableFrame.slidingSouthBorder", useShadowBorder ? slidingSouthFrameBorder : slidingSouthFrameBorder2, "FrameContainer.contentBorderInsets", new InsetsUIResource(3, 3, 3, 3) };
/*     */ 
/* 228 */       table.putDefaults(uiDefaults);
/*     */     }
/* 230 */     if ((products & 0x10) != 0) {
/* 231 */       Object floatingBorder = new ExtWindowsDesktopProperty(new String[] { "win.3d.titleBarColor" }, new Object[] { UIDefaultsLookup.get("controlShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 234 */           return new BorderUIResource(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder((Color)obj[0], 2), BorderFactory.createEmptyBorder(1, 1, 1, 1)));
/*     */         }
/*     */       });
/* 239 */       WindowsDesktopProperty activeTitleTextColor = new WindowsDesktopProperty("win.frame.captionTextColor", UIDefaultsLookup.get("activeCaptionText"), toolkit);
/* 240 */       WindowsDesktopProperty activeTitleBackgroundColor = new WindowsDesktopProperty("win.frame.activeCaptionColor", UIDefaultsLookup.get("activeCaption"), toolkit);
/*     */ 
/* 242 */       uiDefaults = new Object[] { "CommandBar.font", toolbarFont, "CommandBar.background", defaultBackgroundColor, "CommandBar.foreground", defaultTextColor, "CommandBar.shadow", defaultShadowColor, "CommandBar.darkShadow", defaultDarkShadowColor, "CommandBar.light", defaultLightColor, "CommandBar.highlight", defaultHighlightColor, "CommandBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(1, 2, 2, 0)), "CommandBar.borderVert", new BorderUIResource(BorderFactory.createEmptyBorder(2, 1, 0, 2)), "CommandBar.borderFloating", new BorderUIResource(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIDefaultsLookup.getColor("activeCaption"), 2), BorderFactory.createEmptyBorder(1, 1, 1, 1))), "CommandBar.floatingBorder", floatingBorder, "CommandBar.separatorSize", Integer.valueOf(5), "CommandBar.titleBarSize", Integer.valueOf(17), "CommandBar.titleBarButtonGap", Integer.valueOf(1), "CommandBar.titleBarBackground", activeTitleBackgroundColor, "CommandBar.titleBarForeground", SystemInfo.isWindowsVistaAbove() ? new ColorUIResource(Color.WHITE) : activeTitleTextColor, "CommandBar.titleBarFont", boldFont, "Chevron.size", Integer.valueOf(13), "Chevron.alwaysVisible", Boolean.TRUE };
/*     */ 
/* 266 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 269 */     if ((products & 0x4) != 0) {
/* 270 */       uiDefaults = new Object[] { "AbstractComboBox.useJButton", Boolean.FALSE, "NestedTableHeader.cellBorder", new HeaderCellBorder(), "GroupList.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "TAB", "selectNextGroup", "shift TAB", "selectPreviousGroup" }) };
/*     */ 
/* 279 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 282 */     if ((products & 0x2) != 0) {
/* 283 */       uiDefaults = new Object[] { "StatusBarItem.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 1, 0, 1)), "StatusBar.border", new StatusBarBorder() };
/*     */ 
/* 287 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 290 */     UIDefaultsLookup.put(table, "Theme.painter", Office2003Painter.getInstance());
/*     */ 
/* 293 */     Object popupMenuBorder = new ExtWindowsDesktopProperty(new String[] { "null" }, new Object[] { ((ThemePainter)UIDefaultsLookup.get("Theme.painter")).getMenuItemBorderColor() }, toolkit, new ConvertListener() {
/*     */       public Object convert(Object[] obj) {
/* 295 */         return new BorderUIResource(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder((Color)obj[0]), BorderFactory.createEmptyBorder(1, 1, 1, 1)));
/*     */       }
/*     */     });
/* 298 */     table.put("PopupMenu.border", popupMenuBorder);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.office2003.Office2003WindowsUtils
 * JD-Core Version:    0.6.0
 */
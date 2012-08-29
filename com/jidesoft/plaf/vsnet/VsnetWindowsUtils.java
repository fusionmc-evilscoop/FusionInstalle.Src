/*     */ package com.jidesoft.plaf.vsnet;
/*     */ 
/*     */ import com.jidesoft.icons.IconsFactory;
/*     */ import com.jidesoft.icons.JideIconsFactory;
/*     */ import com.jidesoft.icons.MenuCheckIcon;
/*     */ import com.jidesoft.plaf.ExtWindowsDesktopProperty;
/*     */ import com.jidesoft.plaf.LookAndFeelFactory;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.plaf.WindowsDesktopProperty;
/*     */ import com.jidesoft.plaf.basic.BasicPainter;
/*     */ import com.jidesoft.plaf.basic.Painter;
/*     */ import com.jidesoft.plaf.basic.ThemePainter;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.beans.Beans;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIDefaults.LazyInputMap;
/*     */ import javax.swing.plaf.BorderUIResource;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.DimensionUIResource;
/*     */ import javax.swing.plaf.InsetsUIResource;
/*     */ import javax.swing.plaf.basic.BasicBorders.MarginBorder;
/*     */ 
/*     */ public class VsnetWindowsUtils extends VsnetLookAndFeelExtension
/*     */ {
/*     */   public static void initClassDefaultsWithMenu(UIDefaults table)
/*     */   {
/*  42 */     VsnetLookAndFeelExtension.initClassDefaultsWithMenu(table);
/*  43 */     initClassDefaults(table);
/*     */   }
/*     */ 
/*     */   public static void initClassDefaults(UIDefaults table)
/*     */   {
/*  52 */     VsnetLookAndFeelExtension.initClassDefaults(table);
/*     */ 
/*  54 */     String windowsPackageName = "com.jidesoft.plaf.windows.";
/*     */ 
/*  57 */     table.put("JidePopupMenuUI", "com.jidesoft.plaf.windows.WindowsJidePopupMenuUI");
/*  58 */     table.put("RangeSliderUI", "com.jidesoft.plaf.windows.WindowsRangeSliderUI");
/*     */ 
/*  60 */     int products = LookAndFeelFactory.getProductsUsed();
/*     */ 
/*  62 */     if ((products & 0x4) != 0)
/*     */     {
/*  64 */       table.put("NestedTableHeaderUI", "com.jidesoft.plaf.windows.WindowsNestedTableHeaderUI");
/*  65 */       table.put("EditableTableHeaderUI", "com.jidesoft.plaf.windows.WindowsEditableTableHeaderUI");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void initComponentDefaults(UIDefaults table)
/*     */   {
/*  75 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*     */ 
/*  77 */     WindowsDesktopProperty defaultTextColor = new WindowsDesktopProperty("win.button.textColor", UIDefaultsLookup.get("controlText"), toolkit);
/*  78 */     WindowsDesktopProperty defaultBackgroundColor = new WindowsDesktopProperty("win.3d.backgroundColor", UIDefaultsLookup.get("control"), toolkit);
/*  79 */     WindowsDesktopProperty defaultHighlightColor = new WindowsDesktopProperty("win.3d.lightColor", UIDefaultsLookup.get("controlHighlight"), toolkit);
/*  80 */     WindowsDesktopProperty defaultLtHighlightColor = new WindowsDesktopProperty("win.3d.highlightColor", UIDefaultsLookup.get("controlLtHighlight"), toolkit);
/*  81 */     WindowsDesktopProperty defaultShadowColor = new WindowsDesktopProperty("win.3d.shadowColor", UIDefaultsLookup.get("controlShadow"), toolkit);
/*  82 */     WindowsDesktopProperty defaultDarkShadowColor = new WindowsDesktopProperty("win.3d.darkShadowColor", UIDefaultsLookup.get("controlDkShadow"), toolkit);
/*  83 */     WindowsDesktopProperty activeTitleTextColor = new WindowsDesktopProperty("win.frame.captionTextColor", UIDefaultsLookup.get("activeCaptionText"), toolkit);
/*  84 */     WindowsDesktopProperty activeTitleBackgroundColor = new WindowsDesktopProperty("win.frame.activeCaptionColor", UIDefaultsLookup.get("activeCaption"), toolkit);
/*  85 */     WindowsDesktopProperty mdiBackgroundColor = new WindowsDesktopProperty("win.mdi.backgroundColor", UIDefaultsLookup.get("controlShadow"), toolkit);
/*     */ 
/*  87 */     WindowsDesktopProperty menuTextColor = new WindowsDesktopProperty("win.menu.textColor", UIDefaultsLookup.get("control"), toolkit);
/*     */ 
/*  89 */     WindowsDesktopProperty highContrast = new WindowsDesktopProperty("win.highContrast.on", UIDefaultsLookup.get("highContrast"), toolkit);
/*     */ 
/*  91 */     Object controlFont = JideSwingUtilities.getControlFont(toolkit, table);
/*  92 */     Object toolbarFont = JideSwingUtilities.getMenuFont(toolkit, table);
/*  93 */     Object boldFont = JideSwingUtilities.getBoldFont(toolkit, table);
/*     */ 
/*  95 */     Object resizeBorder = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */     {
/*     */       public Object convert(Object[] obj) {
/*  98 */         return new ResizeFrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(4, 4, 4, 4));
/*     */       }
/*     */     });
/* 103 */     Object defaultFormBackground = new ExtWindowsDesktopProperty(new String[] { "win.3d.backgroundColor" }, new Object[] { UIDefaultsLookup.get("control") }, toolkit, new ConvertListener() {
/*     */       public Object convert(Object[] obj) {
/* 105 */         return VsnetUtils.getDefaultBackgroundColor((Color)obj[0]);
/*     */       }
/*     */     });
/* 109 */     Object focusedButtonColor = new ExtWindowsDesktopProperty(new String[] { "win.item.highlightColor" }, new Object[] { UIDefaultsLookup.get("textHighlight") }, toolkit, new ConvertListener() {
/*     */       public Object convert(Object[] obj) {
/* 111 */         return new ColorUIResource(VsnetUtils.getRolloverButtonColor((Color)obj[0]));
/*     */       }
/*     */     });
/* 115 */     Object selectedAndFocusedButtonColor = new ExtWindowsDesktopProperty(new String[] { "win.item.highlightColor" }, new Object[] { UIDefaultsLookup.get("textHighlight") }, toolkit, new ConvertListener() {
/*     */       public Object convert(Object[] obj) {
/* 117 */         return new ColorUIResource(VsnetUtils.getSelectedAndRolloverButtonColor((Color)obj[0]));
/*     */       }
/*     */     });
/* 121 */     Object selectedButtonColor = new ExtWindowsDesktopProperty(new String[] { "win.item.highlightColor" }, new Object[] { UIDefaultsLookup.get("textHighlight") }, toolkit, new ConvertListener() {
/*     */       public Object convert(Object[] obj) {
/* 123 */         return new ColorUIResource(VsnetUtils.getSelectedButtonColor((Color)obj[0]));
/*     */       }
/*     */     });
/* 127 */     Object singleLineBorder = new ExtWindowsDesktopProperty(new String[] { "win.3d.shadowColor" }, new Object[] { UIDefaultsLookup.get("controlShadow") }, toolkit, new ConvertListener() {
/*     */       public Object convert(Object[] obj) {
/* 129 */         return new BorderUIResource(BorderFactory.createLineBorder((Color)obj[0]));
/*     */       }
/*     */     });
/* 133 */     Object borderColor = new ExtWindowsDesktopProperty(new String[] { "win.item.highlightColor" }, new Object[] { UIDefaultsLookup.get("controlShadow") }, toolkit, new ConvertListener() {
/*     */       public Object convert(Object[] obj) {
/* 135 */         return new ColorUIResource(VsnetUtils.getButtonBorderColor((Color)obj[0]));
/*     */       }
/*     */     });
/* 139 */     Object gripperForeground = new ExtWindowsDesktopProperty(new String[] { "win.3d.backgroundColor" }, new Object[] { UIDefaultsLookup.get("control") }, toolkit, new ConvertListener() {
/*     */       public Object convert(Object[] obj) {
/* 141 */         return new ColorUIResource(VsnetUtils.getGripperForegroundColor((Color)obj[0]));
/*     */       }
/*     */     });
/* 145 */     Painter gripperPainter = new Painter() {
/*     */       public void paint(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 147 */         Object p = UIDefaultsLookup.get("Theme.painter");
/* 148 */         if ((p instanceof ThemePainter)) {
/* 149 */           ((ThemePainter)p).paintGripper(c, g, rect, orientation, state);
/*     */         }
/*     */         else
/* 152 */           BasicPainter.getInstance().paintGripper(c, g, rect, orientation, state);
/*     */       }
/*     */     };
/* 157 */     Object buttonBorder = new BasicBorders.MarginBorder();
/*     */ 
/* 159 */     Object[] uiDefaults = { "Theme.highContrast", highContrast, "Content.background", defaultBackgroundColor, "JideLabel.font", controlFont, "JideLabel.background", defaultBackgroundColor, "JideLabel.foreground", defaultTextColor, "JideButton.selectedAndFocusedBackground", selectedAndFocusedButtonColor, "JideButton.focusedBackground", focusedButtonColor, "JideButton.selectedBackground", selectedButtonColor, "JideButton.borderColor", borderColor, "JideButton.font", controlFont, "JideButton.background", defaultBackgroundColor, "JideButton.foreground", defaultTextColor, "JideButton.shadow", defaultShadowColor, "JideButton.darkShadow", defaultDarkShadowColor, "JideButton.light", defaultHighlightColor, "JideButton.highlight", defaultLtHighlightColor, "JideButton.border", buttonBorder, "JideButton.margin", new InsetsUIResource(3, 3, 3, 3), "JideButton.textIconGap", Integer.valueOf(2), "JideButton.textShiftOffset", Integer.valueOf(0), "JideButton.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released", "ENTER", "pressed", "released ENTER", "released" }), "JideSplitPane.dividerSize", Integer.valueOf(4), "JideSplitPaneDivider.border", new BorderUIResource(BorderFactory.createEmptyBorder()), "JideSplitPaneDivider.background", defaultBackgroundColor, "JideSplitPaneDivider.gripperPainter", gripperPainter, "JideTabbedPane.defaultTabShape", Integer.valueOf(2), "JideTabbedPane.defaultResizeMode", Integer.valueOf(1), "JideTabbedPane.defaultTabColorTheme", Integer.valueOf(3), "JideTabbedPane.tabRectPadding", Integer.valueOf(2), "JideTabbedPane.closeButtonMarginHorizonal", Integer.valueOf(3), "JideTabbedPane.closeButtonMarginVertical", Integer.valueOf(3), "JideTabbedPane.textMarginVertical", Integer.valueOf(4), "JideTabbedPane.noIconMargin", Integer.valueOf(2), "JideTabbedPane.iconMargin", Integer.valueOf(5), "JideTabbedPane.textPadding", Integer.valueOf(6), "JideTabbedPane.buttonSize", Integer.valueOf(18), "JideTabbedPane.buttonMargin", Integer.valueOf(5), "JideTabbedPane.fitStyleBoundSize", Integer.valueOf(8), "JideTabbedPane.fitStyleFirstTabMargin", Integer.valueOf(4), "JideTabbedPane.fitStyleIconMinWidth", Integer.valueOf(24), "JideTabbedPane.fitStyleTextMinWidth", Integer.valueOf(16), "JideTabbedPane.compressedStyleNoIconRectSize", Integer.valueOf(24), "JideTabbedPane.compressedStyleIconMargin", Integer.valueOf(12), "JideTabbedPane.compressedStyleCloseButtonMarginHorizontal", Integer.valueOf(0), "JideTabbedPane.compressedStyleCloseButtonMarginVertical", Integer.valueOf(0), "JideTabbedPane.fixedStyleRectSize", Integer.valueOf(60), "JideTabbedPane.closeButtonMargin", Integer.valueOf(2), "JideTabbedPane.gripLeftMargin", Integer.valueOf(4), "JideTabbedPane.closeButtonMarginSize", Integer.valueOf(6), "JideTabbedPane.closeButtonLeftMargin", Integer.valueOf(1), "JideTabbedPane.closeButtonRightMargin", Integer.valueOf(1), "JideTabbedPane.defaultTabBorderShadowColor", new ColorUIResource(115, 109, 99), "JideTabbedPane.gripperPainter", gripperPainter, "JideTabbedPane.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "JideTabbedPane.background", defaultFormBackground, "JideTabbedPane.foreground", defaultTextColor, "JideTabbedPane.light", Color.BLACK.equals(defaultBackgroundColor.createValue(table)) ? defaultBackgroundColor : defaultHighlightColor, "JideTabbedPane.highlight", defaultLtHighlightColor, "JideTabbedPane.shadow", defaultShadowColor, "JideTabbedPane.darkShadow", defaultTextColor, "JideTabbedPane.tabInsets", new InsetsUIResource(1, 4, 1, 4), "JideTabbedPane.contentBorderInsets", new InsetsUIResource(2, 2, 2, 2), "JideTabbedPane.ignoreContentBorderInsetsIfNoTabs", Boolean.FALSE, "JideTabbedPane.tabAreaInsets", new InsetsUIResource(2, 4, 0, 4), "JideTabbedPane.tabAreaBackground", defaultFormBackground, "JideTabbedPane.tabAreaBackgroundLt", defaultLtHighlightColor, "JideTabbedPane.tabAreaBackgroundDk", defaultBackgroundColor, "JideTabbedPane.tabRunOverlay", Integer.valueOf(2), "JideTabbedPane.font", controlFont, "JideTabbedPane.selectedTabFont", controlFont, "JideTabbedPane.selectedTabTextForeground", defaultTextColor, "JideTabbedPane.unselectedTabTextForeground", defaultTextColor, "JideTabbedPane.selectedTabBackground", defaultBackgroundColor, "JideTabbedPane.selectedTabBackgroundLt", new ColorUIResource(230, 139, 44), "JideTabbedPane.selectedTabBackgroundDk", new ColorUIResource(255, 199, 60), "JideTabbedPane.tabListBackground", UIDefaultsLookup.getColor("List.background"), "JideTabbedPane.textIconGap", Integer.valueOf(4), "JideTabbedPane.showIconOnTab", Boolean.TRUE, "JideTabbedPane.showCloseButtonOnTab", Boolean.FALSE, "JideTabbedPane.closeButtonAlignment", Integer.valueOf(11), "JideTabbedPane.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "RIGHT", "navigateRight", "KP_RIGHT", "navigateRight", "LEFT", "navigateLeft", "KP_LEFT", "navigateLeft", "UP", "navigateUp", "KP_UP", "navigateUp", "DOWN", "navigateDown", "KP_DOWN", "navigateDown", "ctrl DOWN", "requestFocusForVisibleComponent", "ctrl KP_DOWN", "requestFocusForVisibleComponent" }), "JideTabbedPane.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "ctrl PAGE_DOWN", "navigatePageDown", "ctrl PAGE_UP", "navigatePageUp", "ctrl UP", "requestFocus", "ctrl KP_UP", "requestFocus" }), "Gripper.size", Integer.valueOf(8), "Gripper.foreground", gripperForeground, "Gripper.painter", gripperPainter, "Resizable.resizeBorder", resizeBorder, "ButtonPanel.order", "ACO", "ButtonPanel.oppositeOrder", "H", "ButtonPanel.buttonGap", Integer.valueOf(6), "ButtonPanel.groupGap", Integer.valueOf(6), "ButtonPanel.minButtonWidth", Integer.valueOf(75), "JideSplitButton.font", controlFont, "JideSplitButton.margin", new InsetsUIResource(3, 3, 3, 7), "JideSplitButton.border", buttonBorder, "JideSplitButton.borderPainted", Boolean.FALSE, "JideSplitButton.textIconGap", Integer.valueOf(3), "JideSplitButton.selectionForeground", menuTextColor, "JideSplitButton.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released", "ENTER", "pressed", "released ENTER", "released", "DOWN", "downPressed", "released DOWN", "downReleased" }), "MeterProgressBar.border", new BorderUIResource(BorderFactory.createLineBorder(Color.BLACK)), "MeterProgressBar.background", new ColorUIResource(Color.BLACK), "MeterProgressBar.foreground", new ColorUIResource(Color.GREEN), "MeterProgressBar.cellForeground", new ColorUIResource(Color.GREEN), "MeterProgressBar.cellBackground", new ColorUIResource(32768), "MeterProgressBar.cellLength", Integer.valueOf(2), "MeterProgressBar.cellSpacing", Integer.valueOf(2), "Cursor.hsplit", JideIconsFactory.getImageIcon("jide/cursor_h_split.gif"), "Cursor.vsplit", JideIconsFactory.getImageIcon("jide/cursor_v_split.gif"), "Cursor.north", JideIconsFactory.getImageIcon("jide/cursor_north.gif"), "Cursor.south", JideIconsFactory.getImageIcon("jide/cursor_south.gif"), "Cursor.east", JideIconsFactory.getImageIcon("jide/cursor_east.gif"), "Cursor.west", JideIconsFactory.getImageIcon("jide/cursor_west.gif"), "Cursor.tab", JideIconsFactory.getImageIcon("jide/cursor_tab.gif"), "Cursor.float", JideIconsFactory.getImageIcon("jide/cursor_float.gif"), "Cursor.vertical", JideIconsFactory.getImageIcon("jide/cursor_vertical.gif"), "Cursor.horizontal", JideIconsFactory.getImageIcon("jide/cursor_horizontal.gif"), "Cursor.delete", JideIconsFactory.getImageIcon("jide/cursor_delete.gif"), "Cursor.drag", JideIconsFactory.getImageIcon("jide/cursor_drag.gif"), "Cursor.dragStop", JideIconsFactory.getImageIcon("jide/cursor_drag_stop.gif"), "Cursor.dragText", JideIconsFactory.getImageIcon("jide/cursor_drag_text.gif"), "Cursor.dragTextStop", JideIconsFactory.getImageIcon("jide/cursor_drag_text_stop.gif"), "Cursor.percentage", JideIconsFactory.getImageIcon("jide/cursor_percentage.gif"), "Cursor.moveEast", JideIconsFactory.getImageIcon("jide/cursor_move_east.gif"), "Cursor.moveWest", JideIconsFactory.getImageIcon("jide/cursor_move_west.gif"), "HeaderBox.background", defaultBackgroundColor, "Icon.floating", Boolean.TRUE, "JideScrollPane.border", singleLineBorder, "TextArea.font", controlFont };
/*     */ 
/* 338 */     table.putDefaults(uiDefaults);
/*     */ 
/* 340 */     int products = LookAndFeelFactory.getProductsUsed();
/*     */ 
/* 342 */     if ((products & 0x1) != 0) {
/* 343 */       Object slidingEastFrameBorder = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 346 */           return new ResizeFrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(0, 4, 0, 0));
/*     */         }
/*     */       });
/* 351 */       Object slidingWestFrameBorder = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 354 */           return new ResizeFrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(0, 0, 0, 4));
/*     */         }
/*     */       });
/* 359 */       Object slidingNorthFrameBorder = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 362 */           return new ResizeFrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(0, 0, 4, 0));
/*     */         }
/*     */       });
/* 367 */       Object slidingSouthFrameBorder = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 370 */           return new ResizeFrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(4, 0, 0, 0));
/*     */         }
/*     */       });
/* 375 */       ImageIcon titleButtonImage = IconsFactory.getImageIcon(VsnetWindowsUtils.class, "icons/title_buttons_windows.gif");
/* 376 */       int titleButtonSize = 10;
/*     */ 
/* 378 */       uiDefaults = new Object[] { "Workspace.background", mdiBackgroundColor, "SidePane.margin", new InsetsUIResource(1, 1, 1, 1), "SidePane.iconTextGap", Integer.valueOf(2), "SidePane.textBorderGap", Integer.valueOf(13), "SidePane.itemGap", Integer.valueOf(5), "SidePane.groupGap", Integer.valueOf(13), "SidePane.foreground", defaultDarkShadowColor, "SidePane.background", defaultFormBackground, "SidePane.lineColor", defaultShadowColor, "SidePane.buttonBackground", defaultBackgroundColor, "SidePane.selectedButtonBackground", selectedButtonColor, "SidePane.selectedButtonForeground", defaultTextColor, "SidePane.font", controlFont, "SidePane.orientation", Integer.valueOf(1), "SidePane.showSelectedTabText", Boolean.TRUE, "SidePane.alwaysShowTabText", Boolean.FALSE, "SidePane.highlighSelectedTab", Boolean.FALSE, "ContentContainer.background", defaultFormBackground, "ContentContainer.vgap", Integer.valueOf(1), "ContentContainer.hgap", Integer.valueOf(1), "MainContainer.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "DockableFrame.defaultIcon", JideIconsFactory.getImageIcon("jide/dockableframe_blank.gif"), "DockableFrame.background", defaultBackgroundColor, "DockableFrame.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "DockableFrame.floatingBorder", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "DockableFrame.slidingEastBorder", slidingEastFrameBorder, "DockableFrame.slidingWestBorder", slidingWestFrameBorder, "DockableFrame.slidingNorthBorder", slidingNorthFrameBorder, "DockableFrame.slidingSouthBorder", slidingSouthFrameBorder, "DockableFrame.activeTitleBackground", activeTitleBackgroundColor, "DockableFrame.activeTitleForeground", activeTitleTextColor, "DockableFrame.inactiveTitleBackground", defaultBackgroundColor, "DockableFrame.inactiveTitleForeground", defaultTextColor, "DockableFrame.titleBorder", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 3, 0)), "DockableFrame.activeTitleBorderColor", activeTitleBackgroundColor, "DockableFrame.inactiveTitleBorderColor", defaultShadowColor, "DockableFrame.font", controlFont, "DockableFrameTitlePane.gripperPainter", gripperPainter, "DockableFrameTitlePane.font", controlFont, "DockableFrameTitlePane.hideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 0, 10, 10), "DockableFrameTitlePane.unfloatIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 10, 10, 10), "DockableFrameTitlePane.floatIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 20, 10, 10), "DockableFrameTitlePane.autohideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 30, 10, 10), "DockableFrameTitlePane.stopAutohideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 40, 10, 10), "DockableFrameTitlePane.hideAutohideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 50, 10, 10), "DockableFrameTitlePane.maximizeIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 60, 10, 10), "DockableFrameTitlePane.restoreIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 70, 10, 10), "DockableFrameTitlePane.titleBarComponent", Boolean.FALSE, "DockableFrameTitlePane.alwaysShowAllButtons", Boolean.FALSE, "DockableFrameTitlePane.use3dButtons", Boolean.TRUE, "DockableFrameTitlePane.buttonsAlignment", Integer.valueOf(11), "DockableFrameTitlePane.titleAlignment", Integer.valueOf(10), "DockableFrameTitlePane.buttonGap", Integer.valueOf(4), "DockableFrameTitlePane.showIcon", Boolean.FALSE, "DockableFrameTitlePane.margin", new InsetsUIResource(0, 6, 0, 6), "Contour.color", new ColorUIResource(136, 136, 136), "Contour.thickness", Integer.valueOf(4), "DockingFramework.changeCursor", Boolean.FALSE, "FrameContainer.contentBorderInsets", new InsetsUIResource(2, 0, 0, 0) };
/*     */ 
/* 449 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 452 */     if ((products & 0x2) != 0) {
/* 453 */       ImageIcon collapsiblePaneImage = IconsFactory.getImageIcon(VsnetWindowsUtils.class, "icons/collapsible_pane_windows.gif");
/* 454 */       int collapsiblePaneSize = 12;
/*     */ 
/* 456 */       uiDefaults = new Object[] { "CollapsiblePanes.border", new BorderUIResource(BorderFactory.createEmptyBorder(12, 12, 12, 12)), "CollapsiblePanes.gap", Integer.valueOf(15), "CollapsiblePane.background", Color.BLACK.equals(defaultBackgroundColor.createValue(table)) ? defaultLtHighlightColor : defaultBackgroundColor, "CollapsiblePane.contentBackground", Color.BLACK.equals(defaultBackgroundColor.createValue(table)) ? defaultBackgroundColor : defaultLtHighlightColor, "CollapsiblePane.foreground", defaultTextColor, "CollapsiblePane.emphasizedBackground", activeTitleBackgroundColor, "CollapsiblePane.emphasizedForeground", activeTitleTextColor, "CollapsiblePane.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "CollapsiblePane.font", controlFont, "CollapsiblePane.contentBorder", new BorderUIResource(BorderFactory.createEmptyBorder(8, 10, 8, 10)), "CollapsiblePane.titleBorder", new BorderUIResource(BorderFactory.createEmptyBorder(3, 3, 3, 3)), "CollapsiblePane.titleFont", boldFont, "CollapsiblePane.downIcon", IconsFactory.getIcon(null, collapsiblePaneImage, 0, 0, 12, 12), "CollapsiblePane.upIcon", IconsFactory.getIcon(null, collapsiblePaneImage, 0, 12, 12, 12), "StatusBarItem.border", singleLineBorder, "StatusBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(2, 0, 0, 0)), "StatusBar.gap", Integer.valueOf(2), "StatusBar.background", defaultBackgroundColor, "StatusBar.font", controlFont, "MemoryStatusBarItem.fillColor", new ColorUIResource(236, 233, 176), "DocumentPane.groupBorder", new BorderUIResource(BorderFactory.createLineBorder(Color.gray)), "DocumentPane.newHorizontalGroupIcon", JideIconsFactory.getImageIcon("jide/windows_new_horizontal_tab_group.png"), "DocumentPane.newVerticalGroupIcon", JideIconsFactory.getImageIcon("jide/windows_new_vertical_tab_group.png"), "DocumentPane.boldActiveTab", Boolean.TRUE, "OutlookTabbedPane.buttonStyle", Integer.valueOf(1), "FloorTabbedPane.buttonStyle", Integer.valueOf(1) };
/*     */ 
/* 492 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 495 */     if ((products & 0x10) != 0) {
/* 496 */       Object commandBarBackground = new ExtWindowsDesktopProperty(new String[] { "win.3d.backgroundColor" }, new Object[] { UIDefaultsLookup.get("control") }, toolkit, new ConvertListener() {
/*     */         public Object convert(Object[] obj) {
/* 498 */           return new ColorUIResource(VsnetUtils.getToolBarBackgroundColor((Color)obj[0]));
/*     */         }
/*     */       });
/* 502 */       uiDefaults = new Object[] { "CommandBar.font", toolbarFont, "CommandBar.background", commandBarBackground, "CommandBar.foreground", defaultTextColor, "CommandBar.shadow", defaultShadowColor, "CommandBar.darkShadow", defaultDarkShadowColor, "CommandBar.light", defaultHighlightColor, "CommandBar.highlight", defaultLtHighlightColor, "CommandBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(1, 1, 1, 1)), "CommandBar.borderVert", new BorderUIResource(BorderFactory.createEmptyBorder(1, 1, 1, 1)), "CommandBar.borderFloating", new BorderUIResource(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder((Color)activeTitleBackgroundColor.createValue(table), 2), BorderFactory.createEmptyBorder(1, 1, 1, 1))), "CommandBar.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "UP", "navigateUp", "KP_UP", "navigateUp", "DOWN", "navigateDown", "KP_DOWN", "navigateDown", "LEFT", "navigateLeft", "KP_LEFT", "navigateLeft", "RIGHT", "navigateRight", "KP_RIGHT", "navigateRight" }), "CommandBar.titleBarSize", Integer.valueOf(17), "CommandBar.titleBarButtonGap", Integer.valueOf(1), "CommandBar.titleBarBackground", activeTitleBackgroundColor, "CommandBar.titleBarForeground", activeTitleTextColor, "CommandBar.titleBarFont", boldFont, "CommandBar.minimumSize", new DimensionUIResource(20, 20), "CommandBar.separatorSize", Integer.valueOf(5), "CommandBarSeparator.background", new Color(219, 216, 209), "CommandBarSeparator.foreground", new Color(166, 166, 166), "Chevron.size", Integer.valueOf(11), "Chevron.alwaysVisible", Boolean.FALSE };
/*     */ 
/* 542 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 545 */     if ((products & 0x4) != 0) {
/* 546 */       uiDefaults = new Object[] { "AbstractComboBox.useJButton", Boolean.FALSE, "NestedTableHeader.cellBorder", new HeaderCellBorder(), "GroupList.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "TAB", "selectNextGroup", "shift TAB", "selectPreviousGroup" }) };
/*     */ 
/* 555 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 558 */     if ((products & 0x4000) != 0) {
/* 559 */       uiDefaults = new Object[] { "DiffMerge.changed", new ColorUIResource(196, 196, 255), "DiffMerge.deleted", new ColorUIResource(200, 200, 200), "DiffMerge.inserted", new ColorUIResource(196, 255, 196), "DiffMerge.conflicted", new ColorUIResource(255, 153, 153) };
/*     */ 
/* 565 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 568 */     if (!JideSwingUtilities.shouldUseSystemFont()) {
/* 569 */       Object[] uiDefaultsFont = { "TabbedPane.font", controlFont, "TitledBorder.font", boldFont, "TableHeader.font", controlFont, "Table.font", controlFont, "List.font", controlFont, "Tree.font", controlFont, "ToolTip.font", controlFont, "CheckBox.font", controlFont, "RadioButton.font", controlFont, "Label.font", controlFont, "Panel.font", controlFont, "TextField.font", controlFont, "ComboBox.font", controlFont, "Button.font", controlFont };
/*     */ 
/* 585 */       table.putDefaults(uiDefaultsFont);
/*     */     }
/*     */ 
/* 589 */     table.put("Spinner.font", UIDefaultsLookup.get("TextField.font"));
/* 590 */     table.put("Spinner.margin", UIDefaultsLookup.get("TextField.margin"));
/* 591 */     table.put("Spinner.border", UIDefaultsLookup.get("TextField.border"));
/* 592 */     table.put("FormattedTextField.font", UIDefaultsLookup.get("TextField.font"));
/*     */ 
/* 594 */     UIDefaultsLookup.put(table, "Theme.painter", BasicPainter.getInstance());
/*     */   }
/*     */ 
/*     */   public static void initComponentDefaultsWithMenu(UIDefaults table)
/*     */   {
/* 603 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*     */ 
/* 605 */     initComponentDefaults(table);
/*     */ 
/* 607 */     if (!Beans.isDesignTime()) {
/* 608 */       Object defaultLightColor = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor" }, new Object[] { UIDefaultsLookup.get("controlHighlight") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 611 */           return new ColorUIResource(VsnetUtils.getLighterColor((Color)obj[0]));
/*     */         }
/*     */       });
/* 614 */       Object borderColor = new ExtWindowsDesktopProperty(new String[] { "win.item.highlightColor" }, new Object[] { UIDefaultsLookup.get("controlShadow") }, toolkit, new ConvertListener() {
/*     */         public Object convert(Object[] obj) {
/* 616 */           return new ColorUIResource(VsnetUtils.getButtonBorderColor((Color)obj[0]));
/*     */         }
/*     */       });
/* 619 */       WindowsDesktopProperty menuTextColor = new WindowsDesktopProperty("win.menu.textColor", UIDefaultsLookup.get("control"), toolkit);
/*     */ 
/* 621 */       Object menuFont = JideSwingUtilities.getMenuFont(toolkit, table);
/*     */ 
/* 623 */       Object menuSelectionBackground = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor" }, new Object[] { UIDefaultsLookup.get("controlShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 626 */           return new ColorUIResource(VsnetUtils.getMenuSelectionColor((Color)obj[0]));
/*     */         }
/*     */       });
/* 630 */       Object menuBackground = new ExtWindowsDesktopProperty(new String[] { "win.3d.backgroundColor" }, new Object[] { UIDefaultsLookup.get("control") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 633 */           return new ColorUIResource(VsnetUtils.getMenuBackgroundColor((Color)obj[0]));
/*     */         }
/*     */       });
/* 637 */       Object separatorColor = new ExtWindowsDesktopProperty(new String[] { "win.3d.shadowColor" }, new Object[] { UIDefaultsLookup.get("controlShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 640 */           return new ColorUIResource(((Color)obj[0]).brighter());
/*     */         }
/*     */       });
/* 644 */       Object[] uiDefaults = { "PopupMenuSeparator.foreground", separatorColor, "PopupMenuSeparator.background", menuBackground, "CheckBoxMenuItem.checkIcon", new MenuCheckIcon(JideIconsFactory.getImageIcon("jide/menu_checkbox_vsnet.gif")), "CheckBoxMenuItem.selectionBackground", menuSelectionBackground, "CheckBoxMenuItem.selectionForeground", menuTextColor, "CheckBoxMenuItem.acceleratorSelectionForeground", menuTextColor, "CheckBoxMenuItem.mouseHoverBackground", menuSelectionBackground, "CheckBoxMenuItem.mouseHoverBorder", new BorderUIResource(BorderFactory.createLineBorder(new Color(10, 36, 106))), "CheckBoxMenuItem.margin", new InsetsUIResource(3, 0, 3, 0), "CheckBoxMenuItem.font", menuFont, "CheckBoxMenuItem.acceleratorFont", menuFont, "CheckBoxMenuItem.textIconGap", Integer.valueOf(8), "RadioButtonMenuItem.checkIcon", new MenuCheckIcon(JideIconsFactory.getImageIcon("jide/menu_checkbox_vsnet.gif")), "RadioButtonMenuItem.selectionBackground", menuSelectionBackground, "RadioButtonMenuItem.selectionForeground", menuTextColor, "RadioButtonMenuItem.acceleratorSelectionForeground", menuTextColor, "RadioButtonMenuItem.mouseHoverBackground", menuSelectionBackground, "RadioButtonMenuItem.mouseHoverBorder", new BorderUIResource(BorderFactory.createLineBorder(new Color(10, 36, 106))), "RadioButtonMenuItem.margin", new InsetsUIResource(3, 0, 3, 0), "RadioButtonMenuItem.font", menuFont, "RadioButtonMenuItem.acceleratorFont", menuFont, "RadioButtonMenuItem.textIconGap", Integer.valueOf(8), "MenuBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(2, 2, 2, 2)), "Menu.selectionBackground", menuSelectionBackground, "Menu.selectionForeground", menuTextColor, "Menu.mouseHoverBackground", menuSelectionBackground, "Menu.mouseHoverBorder", new BorderUIResource(BorderFactory.createLineBorder(new Color(10, 36, 106))), "Menu.margin", new InsetsUIResource(3, 7, 2, 7), "Menu.checkIcon", new MenuCheckIcon(JideIconsFactory.getImageIcon("jide/menu_checkbox_vsnet.gif")), "Menu.textIconGap", Integer.valueOf(2), "Menu.font", menuFont, "Menu.acceleratorFont", menuFont, "Menu.submenuPopupOffsetX", Integer.valueOf(0), "Menu.submenuPopupOffsetY", Integer.valueOf(0), "MenuItem.checkIcon", new MenuCheckIcon(JideIconsFactory.getImageIcon("jide/menu_checkbox_vsnet.gif")), "MenuItem.selectionBackground", menuSelectionBackground, "MenuItem.selectionForeground", menuTextColor, "MenuItem.acceleratorSelectionForeground", menuTextColor, "MenuItem.background", menuBackground, "MenuItem.selectionBorderColor", borderColor, "MenuItem.shadowWidth", Integer.valueOf(24), "MenuItem.shadowColor", defaultLightColor, "MenuItem.textIconGap", Integer.valueOf(8), "MenuItem.accelEndGap", Integer.valueOf(18), "MenuItem.margin", new InsetsUIResource(3, 0, 3, 0), "MenuItem.font", menuFont, "MenuItem.acceleratorFont", menuFont };
/*     */ 
/* 699 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 703 */     Object popupMenuBorder = new ExtWindowsDesktopProperty(new String[] { "null" }, new Object[] { ((ThemePainter)UIDefaultsLookup.get("Theme.painter")).getMenuItemBorderColor() }, toolkit, new ConvertListener() {
/*     */       public Object convert(Object[] obj) {
/* 705 */         return new BorderUIResource(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder((Color)obj[0]), BorderFactory.createEmptyBorder(1, 1, 1, 1)));
/*     */       }
/*     */     });
/* 708 */     table.put("PopupMenu.border", popupMenuBorder);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.vsnet.VsnetWindowsUtils
 * JD-Core Version:    0.6.0
 */
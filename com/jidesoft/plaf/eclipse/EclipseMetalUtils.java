/*     */ package com.jidesoft.plaf.eclipse;
/*     */ 
/*     */ import com.jidesoft.icons.IconsFactory;
/*     */ import com.jidesoft.icons.JideIconsFactory;
/*     */ import com.jidesoft.plaf.LookAndFeelFactory;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.plaf.basic.Painter;
/*     */ import com.jidesoft.plaf.basic.ThemePainter;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import com.jidesoft.utils.ColorUtils;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIDefaults.LazyInputMap;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.BorderUIResource;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.DimensionUIResource;
/*     */ import javax.swing.plaf.InsetsUIResource;
/*     */ import javax.swing.plaf.basic.BasicBorders.MarginBorder;
/*     */ 
/*     */ public class EclipseMetalUtils extends EclipseLookAndFeelExtension
/*     */ {
/*     */   public static void initClassDefaults(UIDefaults table)
/*     */   {
/*  39 */     EclipseLookAndFeelExtension.initClassDefaults(table);
/*     */ 
/*  41 */     table.put("RangeSliderUI", "com.jidesoft.plaf.metal.MetalRangeSliderUI");
/*     */   }
/*     */ 
/*     */   public static void initComponentDefaults(UIDefaults table)
/*     */   {
/*  50 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*     */ 
/*  52 */     Object defaultTextColor = UIDefaultsLookup.get("controlText");
/*  53 */     Object defaultBackgroundColor = UIDefaultsLookup.get("control");
/*  54 */     Object defaultHighlightColor = UIDefaultsLookup.get("controlHighlight");
/*  55 */     Object defaultLtHighlightColor = UIDefaultsLookup.get("controlLtHighlight");
/*  56 */     Object defaultShadowColor = UIDefaultsLookup.get("controlShadow");
/*  57 */     Object defaultDarkShadowColor = UIDefaultsLookup.get("controlDkShadow");
/*  58 */     Object activeTitleTextColor = UIDefaultsLookup.get("activeCaptionText");
/*  59 */     Object activeTitleBackgroundColor = UIDefaultsLookup.get("activeCaption");
/*  60 */     Object activeTitleBarGradientColor = UIDefaultsLookup.getColor("activeCaption").darker();
/*  61 */     Object activeTitleBorderColor = UIDefaultsLookup.get("controlDkShadow");
/*  62 */     Object inactiveTitleTextColor = UIDefaultsLookup.get("controlText");
/*  63 */     Object inactiveTitleBackgroundColor = UIDefaultsLookup.get("control");
/*  64 */     Object mdiBackgroundColor = UIDefaultsLookup.get("controlShadow");
/*  65 */     Object selectionBackgroundColor = UIDefaultsLookup.getColor("controlShadow");
/*     */ 
/*  67 */     Object controlFont = JideSwingUtilities.getControlFont(toolkit, table);
/*  68 */     Object toolbarFont = JideSwingUtilities.getMenuFont(toolkit, table);
/*  69 */     Object boldFont = JideSwingUtilities.getBoldFont(toolkit, table);
/*     */ 
/*  71 */     Border shadowBorder = BorderFactory.createCompoundBorder(new ShadowBorder(null, null, new Color(171, 168, 165), new Color(143, 141, 138), new Insets(0, 0, 2, 2)), BorderFactory.createLineBorder(Color.gray));
/*     */ 
/*  74 */     Border documentBorder = shadowBorder;
/*     */ 
/*  76 */     Object slidingEastFrameBorder = new FrameBorder(UIDefaultsLookup.getColor("control"), UIDefaultsLookup.getColor("controlLtHighlight"), UIDefaultsLookup.getColor("controlShadow"), UIDefaultsLookup.getColor("controlDkShadow"), new Insets(0, 4, 0, 0));
/*     */ 
/*  78 */     Object slidingWestFrameBorder = new FrameBorder(UIDefaultsLookup.getColor("control"), UIDefaultsLookup.getColor("controlLtHighlight"), UIDefaultsLookup.getColor("controlShadow"), UIDefaultsLookup.getColor("controlDkShadow"), new Insets(0, 0, 0, 4));
/*     */ 
/*  80 */     Object slidingNorthFrameBorder = new FrameBorder(UIDefaultsLookup.getColor("control"), UIDefaultsLookup.getColor("controlLtHighlight"), UIDefaultsLookup.getColor("controlShadow"), UIDefaultsLookup.getColor("controlDkShadow"), new Insets(0, 0, 4, 0));
/*     */ 
/*  82 */     Object slidingSouthFrameBorder = new FrameBorder(UIDefaultsLookup.getColor("control"), UIDefaultsLookup.getColor("controlLtHighlight"), UIDefaultsLookup.getColor("controlShadow"), UIDefaultsLookup.getColor("controlDkShadow"), new Insets(4, 0, 0, 0));
/*     */ 
/*  84 */     Object focusedButtonColor = new ColorUIResource(EclipseUtils.getFocusedButtonColor(UIDefaultsLookup.getColor("textHighlight")));
/*     */ 
/*  87 */     Object selectedAndFocusedButtonColor = new ColorUIResource(EclipseUtils.getSelectedAndFocusedButtonColor(UIDefaultsLookup.getColor("textHighlight")));
/*     */ 
/*  90 */     Object selectedButtonColor = new ColorUIResource(EclipseUtils.getSelectedButtonColor(UIDefaultsLookup.getColor("textHighlight")));
/*     */ 
/*  93 */     Painter gripperPainter = new Painter() {
/*     */       public void paint(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/*  95 */         Object p = UIDefaultsLookup.get("Theme.painter");
/*  96 */         if ((p instanceof ThemePainter)) {
/*  97 */           ((ThemePainter)p).paintGripper(c, g, rect, orientation, state);
/*     */         }
/*     */         else
/* 100 */           EclipsePainter.getInstance().paintGripper(c, g, rect, orientation, state);
/*     */       }
/*     */     };
/* 105 */     Object buttonBorder = new BasicBorders.MarginBorder();
/*     */ 
/* 107 */     Object[] uiDefaults = { "JideLabel.font", controlFont, "JideLabel.background", defaultBackgroundColor, "JideLabel.foreground", defaultTextColor, "JideButton.selectedAndFocusedBackground", selectedAndFocusedButtonColor, "JideButton.focusedBackground", focusedButtonColor, "JideButton.selectedBackground", selectedButtonColor, "JideButton.borderColor", selectionBackgroundColor, "JideButton.font", controlFont, "JideButton.background", defaultBackgroundColor, "JideButton.foreground", defaultTextColor, "JideButton.shadow", defaultShadowColor, "JideButton.darkShadow", defaultDarkShadowColor, "JideButton.light", defaultHighlightColor, "JideButton.highlight", defaultLtHighlightColor, "JideButton.border", buttonBorder, "JideButton.margin", new InsetsUIResource(3, 3, 3, 3), "JideButton.textIconGap", Integer.valueOf(4), "JideButton.textShiftOffset", Integer.valueOf(0), "JideButton.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released" }), "JideSplitPane.dividerSize", Integer.valueOf(3), "JideSplitPaneDivider.border", new BorderUIResource(BorderFactory.createEmptyBorder()), "JideSplitPaneDivider.background", defaultBackgroundColor, "JideSplitPaneDivider.gripperPainter", gripperPainter, "JideTabbedPane.defaultTabShape", Integer.valueOf(6), "JideTabbedPane.defaultTabColorTheme", Integer.valueOf(1), "JideTabbedPane.tabRectPadding", Integer.valueOf(2), "JideTabbedPane.closeButtonMarginHorizonal", Integer.valueOf(3), "JideTabbedPane.closeButtonMarginVertical", Integer.valueOf(3), "JideTabbedPane.textMarginVertical", Integer.valueOf(4), "JideTabbedPane.noIconMargin", Integer.valueOf(2), "JideTabbedPane.iconMargin", Integer.valueOf(5), "JideTabbedPane.textPadding", Integer.valueOf(6), "JideTabbedPane.buttonSize", Integer.valueOf(18), "JideTabbedPane.buttonMargin", Integer.valueOf(5), "JideTabbedPane.fitStyleBoundSize", Integer.valueOf(8), "JideTabbedPane.fitStyleFirstTabMargin", Integer.valueOf(0), "JideTabbedPane.fitStyleIconMinWidth", Integer.valueOf(24), "JideTabbedPane.fitStyleTextMinWidth", Integer.valueOf(16), "JideTabbedPane.compressedStyleNoIconRectSize", Integer.valueOf(24), "JideTabbedPane.compressedStyleIconMargin", Integer.valueOf(12), "JideTabbedPane.compressedStyleCloseButtonMarginHorizontal", Integer.valueOf(0), "JideTabbedPane.compressedStyleCloseButtonMarginVertical", Integer.valueOf(0), "JideTabbedPane.fixedStyleRectSize", Integer.valueOf(60), "JideTabbedPane.closeButtonMargin", Integer.valueOf(2), "JideTabbedPane.gripLeftMargin", Integer.valueOf(4), "JideTabbedPane.closeButtonMarginSize", Integer.valueOf(6), "JideTabbedPane.closeButtonLeftMargin", Integer.valueOf(1), "JideTabbedPane.closeButtonRightMargin", Integer.valueOf(1), "JideTabbedPane.defaultTabBorderShadowColor", new ColorUIResource(115, 109, 99), "JideTabbedPane.gripperPainter", gripperPainter, "JideTabbedPane.border", new BorderUIResource(shadowBorder), "JideTabbedPane.background", defaultBackgroundColor, "JideTabbedPane.foreground", defaultTextColor, "JideTabbedPane.light", defaultHighlightColor, "JideTabbedPane.highlight", defaultLtHighlightColor, "JideTabbedPane.shadow", defaultShadowColor, "JideTabbedPane.darkShadow", defaultDarkShadowColor, "JideTabbedPane.tabInsets", new InsetsUIResource(1, 4, 1, 4), "JideTabbedPane.contentBorderInsets", new InsetsUIResource(1, 0, 0, 0), "JideTabbedPane.ignoreContentBorderInsetsIfNoTabs", Boolean.FALSE, "JideTabbedPane.tabAreaInsets", new InsetsUIResource(0, 0, 0, 0), "JideTabbedPane.tabAreaBackground", defaultBackgroundColor, "JideTabbedPane.tabAreaBackgroundLt", defaultLtHighlightColor, "JideTabbedPane.tabAreaBackgroundDk", defaultBackgroundColor, "JideTabbedPane.tabRunOverlay", Integer.valueOf(2), "JideTabbedPane.font", controlFont, "JideTabbedPane.selectedTabFont", controlFont, "JideTabbedPane.selectedTabTextForeground", activeTitleTextColor, "JideTabbedPane.unselectedTabTextForeground", inactiveTitleTextColor, "JideTabbedPane.selectedTabBackground", defaultBackgroundColor, "JideTabbedPane.selectedTabBackgroundLt", new ColorUIResource(230, 139, 44), "JideTabbedPane.selectedTabBackgroundDk", new ColorUIResource(255, 199, 60), "JideTabbedPane.tabListBackground", UIDefaultsLookup.getColor("List.background"), "JideTabbedPane.textIconGap", Integer.valueOf(4), "JideTabbedPane.showIconOnTab", Boolean.FALSE, "JideTabbedPane.showCloseButtonOnTab", Boolean.TRUE, "JideTabbedPane.closeButtonAlignment", Integer.valueOf(11), "JideTabbedPane.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "RIGHT", "navigateRight", "KP_RIGHT", "navigateRight", "LEFT", "navigateLeft", "KP_LEFT", "navigateLeft", "UP", "navigateUp", "KP_UP", "navigateUp", "DOWN", "navigateDown", "KP_DOWN", "navigateDown", "ctrl DOWN", "requestFocusForVisibleComponent", "ctrl KP_DOWN", "requestFocusForVisibleComponent" }), "JideTabbedPane.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "ctrl PAGE_DOWN", "navigatePageDown", "ctrl PAGE_UP", "navigatePageUp", "ctrl UP", "requestFocus", "ctrl KP_UP", "requestFocus" }), "Resizable.resizeBorder", new BorderUIResource(shadowBorder), "Gripper.size", Integer.valueOf(8), "Gripper.size", Integer.valueOf(8), "Gripper.painter", gripperPainter, "MenuBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(2, 2, 2, 2)), "Icon.floating", Boolean.FALSE, "JideSplitButton.font", controlFont, "JideSplitButton.margin", new InsetsUIResource(3, 3, 3, 7), "JideSplitButton.border", buttonBorder, "JideSplitButton.borderPainted", Boolean.FALSE, "JideSplitButton.textIconGap", Integer.valueOf(3), "JideSplitButton.selectionBackground", selectionBackgroundColor, "JideSplitButton.selectionForeground", defaultTextColor, "JideSplitButton.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released", "DOWN", "downPressed", "released DOWN", "downReleased" }), "ButtonPanel.order", "ACO", "ButtonPanel.oppositeOrder", "H", "ButtonPanel.buttonGap", Integer.valueOf(5), "ButtonPanel.groupGap", Integer.valueOf(5), "ButtonPanel.minButtonWidth", Integer.valueOf(57), "HeaderBox.background", defaultBackgroundColor, "Cursor.hsplit", JideIconsFactory.getImageIcon("jide/cursor_h_split.gif"), "Cursor.vsplit", JideIconsFactory.getImageIcon("jide/cursor_v_split.gif"), "Cursor.north", JideIconsFactory.getImageIcon("jide/cursor_north.gif"), "Cursor.south", JideIconsFactory.getImageIcon("jide/cursor_south.gif"), "Cursor.east", JideIconsFactory.getImageIcon("jide/cursor_east.gif"), "Cursor.west", JideIconsFactory.getImageIcon("jide/cursor_west.gif"), "Cursor.tab", JideIconsFactory.getImageIcon("jide/cursor_tab.gif"), "Cursor.float", JideIconsFactory.getImageIcon("jide/cursor_float.gif"), "Cursor.vertical", JideIconsFactory.getImageIcon("jide/cursor_vertical.gif"), "Cursor.horizontal", JideIconsFactory.getImageIcon("jide/cursor_horizontal.gif"), "Cursor.delete", JideIconsFactory.getImageIcon("jide/cursor_delete.gif"), "Cursor.drag", JideIconsFactory.getImageIcon("jide/cursor_drag.gif"), "Cursor.dragStop", JideIconsFactory.getImageIcon("jide/cursor_drag_stop.gif"), "Cursor.dragText", JideIconsFactory.getImageIcon("jide/cursor_drag_text.gif"), "Cursor.dragTextStop", JideIconsFactory.getImageIcon("jide/cursor_drag_text_stop.gif"), "Cursor.percentage", JideIconsFactory.getImageIcon("jide/cursor_percentage.gif"), "Cursor.moveEast", JideIconsFactory.getImageIcon("jide/cursor_move_east.gif"), "Cursor.moveWest", JideIconsFactory.getImageIcon("jide/cursor_move_west.gif") };
/*     */ 
/* 268 */     table.putDefaults(uiDefaults);
/*     */ 
/* 270 */     int products = LookAndFeelFactory.getProductsUsed();
/*     */ 
/* 272 */     if ((products & 0x1) != 0) {
/* 273 */       ImageIcon titleButtonImage = IconsFactory.getImageIcon(EclipseMetalUtils.class, "icons/title_buttons_eclipse.gif");
/* 274 */       int titleButtonSize = 16;
/*     */ 
/* 276 */       uiDefaults = new Object[] { "Workspace.background", mdiBackgroundColor, "SidePane.margin", new InsetsUIResource(1, 1, 1, 1), "SidePane.iconTextGap", Integer.valueOf(2), "SidePane.textBorderGap", Integer.valueOf(13), "SidePane.itemGap", Integer.valueOf(4), "SidePane.groupGap", Integer.valueOf(3), "SidePane.foreground", defaultDarkShadowColor, "SidePane.background", defaultBackgroundColor, "SidePane.lineColor", defaultShadowColor, "SidePane.buttonBackground", defaultBackgroundColor, "SidePane.font", controlFont, "SidePane.orientation", Integer.valueOf(1), "SidePane.showSelectedTabText", Boolean.FALSE, "SidePane.alwaysShowTabText", Boolean.FALSE, "DockableFrame.defaultIcon", JideIconsFactory.getImageIcon("jide/dockableframe_blank.gif"), "DockableFrame.background", defaultBackgroundColor, "DockableFrame.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "DockableFrame.floatingBorder", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "DockableFrame.slidingEastBorder", slidingEastFrameBorder, "DockableFrame.slidingWestBorder", slidingWestFrameBorder, "DockableFrame.slidingNorthBorder", slidingNorthFrameBorder, "DockableFrame.slidingSouthBorder", slidingSouthFrameBorder, "DockableFrame.activeTitleBackground", activeTitleBackgroundColor, "DockableFrame.activeTitleBackground2", activeTitleBarGradientColor, "DockableFrame.activeTitleForeground", activeTitleTextColor, "DockableFrame.inactiveTitleBackground", inactiveTitleBackgroundColor, "DockableFrame.inactiveTitleForeground", inactiveTitleTextColor, "DockableFrame.activeTitleBorderColor", activeTitleBorderColor, "DockableFrame.inactiveTitleBorderColor", activeTitleBorderColor, "DockableFrame.titleBorder", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "DockableFrame.font", controlFont, "DockableFrameTitlePane.gripperPainter", gripperPainter, "DockableFrameTitlePane.font", controlFont, "DockableFrameTitlePane.hideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 0, 16, 16), "DockableFrameTitlePane.unfloatIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 16, 16, 16), "DockableFrameTitlePane.floatIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 32, 16, 16), "DockableFrameTitlePane.autohideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 48, 16, 16), "DockableFrameTitlePane.stopAutohideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 64, 16, 16), "DockableFrameTitlePane.hideAutohideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 80, 16, 16), "DockableFrameTitlePane.maximizeIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 96, 16, 16), "DockableFrameTitlePane.restoreIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 112, 16, 16), "DockableFrameTitlePane.titleBarComponent", Boolean.TRUE, "DockableFrameTitlePane.alwaysShowAllButtons", Boolean.FALSE, "DockableFrameTitlePane.buttonsAlignment", Integer.valueOf(11), "DockableFrameTitlePane.titleAlignment", Integer.valueOf(10), "DockableFrameTitlePane.buttonGap", Integer.valueOf(3), "DockableFrameTitlePane.showIcon", Boolean.TRUE, "DockableFrameTitlePane.margin", new InsetsUIResource(0, 6, 0, 6), "DockableFrameTitlePane.use3dButtons", Boolean.TRUE, "ContentContainer.background", defaultBackgroundColor, "ContentContainer.vgap", Integer.valueOf(1), "ContentContainer.hgap", Integer.valueOf(1), "MainContainer.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "DockingFramework.changeCursor", Boolean.TRUE, "Contour.color", new ColorUIResource(136, 136, 136), "Contour.thickness", Integer.valueOf(2) };
/*     */ 
/* 342 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 345 */     if ((products & 0x2) != 0) {
/* 346 */       ImageIcon collapsiblePaneImage = IconsFactory.getImageIcon(EclipseMetalUtils.class, "icons/collapsible_pane_eclipse.gif");
/* 347 */       int collapsiblePaneSize = 11;
/*     */ 
/* 349 */       uiDefaults = new Object[] { "CollapsiblePanes.border", new BorderUIResource(BorderFactory.createEmptyBorder(12, 12, 12, 12)), "CollapsiblePanes.gap", Integer.valueOf(15), "CollapsiblePane.background", (defaultBackgroundColor instanceof Color) ? ColorUtils.getDerivedColor((Color)defaultBackgroundColor, 0.45F) : defaultBackgroundColor, "CollapsiblePane.contentBackground", defaultHighlightColor, "CollapsiblePane.foreground", defaultTextColor, "CollapsiblePane.emphasizedBackground", activeTitleBackgroundColor, "CollapsiblePane.emphasizedForeground", activeTitleTextColor, "CollapsiblePane.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "CollapsiblePane.font", controlFont, "CollapsiblePane.contentBorder", new BorderUIResource(BorderFactory.createEmptyBorder(8, 10, 8, 10)), "CollapsiblePane.titleBorder", new BorderUIResource(BorderFactory.createEmptyBorder(3, 3, 3, 3)), "CollapsiblePane.titleFont", boldFont, "CollapsiblePane.downIcon", IconsFactory.getIcon(null, collapsiblePaneImage, 0, 0, 11, 11), "CollapsiblePane.upIcon", IconsFactory.getIcon(null, collapsiblePaneImage, 0, 11, 11, 11), "StatusBarItem.border", new BorderUIResource(BorderFactory.createEtchedBorder()), "StatusBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(2, 0, 0, 0)), "StatusBar.margin", new Insets(2, 0, 0, 0), "StatusBar.gap", Integer.valueOf(3), "StatusBar.background", defaultBackgroundColor, "StatusBar.font", controlFont, "MemoryStatusBarItem.fillColor", new ColorUIResource(236, 233, 176), "DocumentPane.groupBorder", new BorderUIResource(documentBorder), "DocumentPane.newHorizontalGroupIcon", JideIconsFactory.getImageIcon("jide/windows_new_horizontal_tab_group.png"), "DocumentPane.newVerticalGroupIcon", JideIconsFactory.getImageIcon("jide/windows_new_vertical_tab_group.png"), "DocumentPane.boldActiveTab", Boolean.FALSE, "OutlookTabbedPane.buttonStyle", Integer.valueOf(1), "FloorTabbedPane.buttonStyle", Integer.valueOf(1) };
/*     */ 
/* 385 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 388 */     if ((products & 0x10) != 0) {
/* 389 */       uiDefaults = new Object[] { "CommandBar.font", toolbarFont, "CommandBar.background", defaultBackgroundColor, "CommandBar.foreground", defaultTextColor, "CommandBar.shadow", defaultShadowColor, "CommandBar.darkShadow", defaultDarkShadowColor, "CommandBar.light", defaultHighlightColor, "CommandBar.highlight", defaultLtHighlightColor, "CommandBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(2, 1, 2, 1)), "CommandBar.borderVert", new BorderUIResource(BorderFactory.createEmptyBorder(2, 1, 2, 1)), "CommandBar.borderFloating", new BorderUIResource(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder((Color)activeTitleBackgroundColor, 2), BorderFactory.createEmptyBorder(1, 1, 1, 1))), "CommandBar.separatorSize", Integer.valueOf(3), "CommandBar.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "UP", "navigateUp", "KP_UP", "navigateUp", "DOWN", "navigateDown", "KP_DOWN", "navigateDown", "LEFT", "navigateLeft", "KP_LEFT", "navigateLeft", "RIGHT", "navigateRight", "KP_RIGHT", "navigateRight" }), "CommandBar.titleBarSize", Integer.valueOf(17), "CommandBar.titleBarButtonGap", Integer.valueOf(1), "CommandBar.titleBarBackground", activeTitleBackgroundColor, "CommandBar.titleBarForeground", activeTitleTextColor, "CommandBar.titleBarFont", boldFont, "CommandBar.minimumSize", new DimensionUIResource(20, 20), "CommandBar.separatorSize", new DimensionUIResource(5, 20), "CommandBarSeparator.background", new Color(219, 216, 209), "CommandBarSeparator.foreground", new Color(166, 166, 166), "Chevron.size", Integer.valueOf(11), "Chevron.alwaysVisible", Boolean.FALSE };
/*     */ 
/* 430 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 433 */     if ((products & 0x4) != 0) {
/* 434 */       uiDefaults = new Object[] { "AbstractComboBox.useJButton", Boolean.TRUE, "NestedTableHeader.cellBorder", UIDefaultsLookup.getBorder("TableHeader.cellBorder") };
/*     */ 
/* 438 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 441 */     if ((products & 0x4000) != 0) {
/* 442 */       uiDefaults = new Object[] { "DiffMerge.changed", new ColorUIResource(196, 196, 255), "DiffMerge.deleted", new ColorUIResource(200, 200, 200), "DiffMerge.inserted", new ColorUIResource(196, 255, 196), "DiffMerge.conflicted", new ColorUIResource(255, 153, 153) };
/*     */ 
/* 448 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 451 */     UIDefaultsLookup.put(table, "Theme.painter", EclipsePainter.getInstance());
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.eclipse.EclipseMetalUtils
 * JD-Core Version:    0.6.0
 */
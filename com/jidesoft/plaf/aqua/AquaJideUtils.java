/*     */ package com.jidesoft.plaf.aqua;
/*     */ 
/*     */ import com.jidesoft.icons.IconsFactory;
/*     */ import com.jidesoft.icons.JideIconsFactory;
/*     */ import com.jidesoft.plaf.ExtWindowsDesktopProperty;
/*     */ import com.jidesoft.plaf.LookAndFeelFactory;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.plaf.vsnet.ConvertListener;
/*     */ import com.jidesoft.plaf.vsnet.VsnetLookAndFeelExtension;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Toolkit;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIDefaults.LazyInputMap;
/*     */ import javax.swing.plaf.BorderUIResource;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.DimensionUIResource;
/*     */ import javax.swing.plaf.InsetsUIResource;
/*     */ import javax.swing.plaf.basic.BasicBorders.MarginBorder;
/*     */ 
/*     */ public class AquaJideUtils extends VsnetLookAndFeelExtension
/*     */ {
/* 482 */   public static final Color[] HALF_LIGHT = { new Color(251, 251, 251), new Color(237, 237, 237) };
/*     */ 
/* 487 */   public static final Color[] HALF_DARK = { new Color(133, 133, 133), new Color(125, 125, 125) };
/*     */ 
/* 497 */   public static final Color[] AQUA_WHITE = { new Color(252, 252, 252), new Color(236, 236, 236), new Color(225, 225, 225), new Color(255, 255, 255) };
/*     */ 
/* 504 */   public static final Color[] AQUA_BLUE = { new Color(221, 225, 244), new Color(139, 187, 238), new Color(100, 168, 242), new Color(187, 255, 255) };
/*     */ 
/* 511 */   public static final Color[] AQUA_GRAPHITE = { new Color(231, 233, 235), new Color(182, 188, 198), new Color(158, 158, 180), new Color(231, 241, 255) };
/*     */ 
/* 519 */   public static final Color[] AQUA_BANNER_WHITE = { new Color(255, 255, 255), new Color(248, 248, 248), new Color(228, 228, 228), new Color(239, 239, 239) };
/*     */ 
/* 526 */   public static final Color[] AQUA_BANNER_BLUE = { new Color(103, 159, 254), new Color(73, 132, 253), new Color(51, 132, 253), new Color(84, 170, 254) };
/*     */ 
/*     */   public static void initClassDefaults(UIDefaults table)
/*     */   {
/*  37 */     VsnetLookAndFeelExtension.initClassDefaults(table);
/*     */ 
/*  39 */     String aquaPackageName = "com.jidesoft.plaf.aqua.";
/*     */ 
/*  41 */     int products = LookAndFeelFactory.getProductsUsed();
/*     */ 
/*  43 */     table.put("JideSplitButtonUI", "com.jidesoft.plaf.aqua.AquaJideSplitButtonUI");
/*  44 */     table.put("JidePopupMenuUI", "com.jidesoft.plaf.aqua.AquaJidePopupMenuUI");
/*  45 */     table.put("JideTabbedPaneUI", "com.jidesoft.plaf.aqua.AquaJideTabbedPaneUI");
/*  46 */     table.put("GripperUI", "com.jidesoft.plaf.aqua.AquaGripperUI");
/*  47 */     table.put("RangeSliderUI", "com.jidesoft.plaf.aqua.AquaRangeSliderUI");
/*     */ 
/*  49 */     if ((products & 0x4) != 0) {
/*  50 */       table.put("JideTableUI", "com.jidesoft.plaf.aqua.AquaJideTableUI");
/*  51 */       table.put("NavigableTableUI", "com.jidesoft.plaf.aqua.AquaNavigableTableUI");
/*  52 */       table.put("CellSpanTableUI", "com.jidesoft.plaf.aqua.AquaCellSpanTableUI");
/*  53 */       table.put("TreeTableUI", "com.jidesoft.plaf.aqua.AquaTreeTableUI");
/*  54 */       table.put("HierarchicalTableUI", "com.jidesoft.plaf.aqua.AquaHierarchicalTableUI");
/*  55 */       table.put("NestedTableHeaderUI", "com.jidesoft.plaf.aqua.AquaNestedTableHeaderUI");
/*  56 */       table.put("EditableTableHeaderUI", "com.jidesoft.plaf.aqua.AquaEditableTableHeaderUI");
/*     */     }
/*     */ 
/*  59 */     if ((products & 0x1) != 0) {
/*  60 */       table.put("SidePaneUI", "com.jidesoft.plaf.aqua.AquaSidePaneUI");
/*  61 */       table.put("DockableFrameUI", "com.jidesoft.plaf.aqua.AquaDockableFrameUI");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void initComponentDefaults(UIDefaults table)
/*     */   {
/*  71 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*     */ 
/*  73 */     Object defaultTextColor = UIDefaultsLookup.get("controlText");
/*     */ 
/*  75 */     Object defaultBackgroundColor = UIDefaultsLookup.get("Panel.background");
/*     */ 
/*  77 */     Object defaultLightColor = UIDefaultsLookup.get("controlHighlight");
/*  78 */     Object defaultHighlightColor = UIDefaultsLookup.get("controlLtHighlight");
/*  79 */     Object defaultShadowColor = UIDefaultsLookup.get("controlShadow");
/*  80 */     Object defaultDarkShadowColor = UIDefaultsLookup.get("controlDkShadow");
/*     */ 
/*  82 */     Object mdiBackgroundColor = UIDefaultsLookup.get("Panel.background");
/*     */ 
/*  84 */     Object controlFont = UIDefaultsLookup.get("Button.font");
/*     */ 
/*  86 */     Object controlSmallFont = UIDefaultsLookup.get("TabbedPane.smallFont");
/*     */ 
/*  88 */     Object boldFont = UIDefaultsLookup.get("Button.font");
/*     */ 
/*  90 */     Object resizeBorder = BorderFactory.createLineBorder(new Color(230, 230, 230), 2);
/*     */ 
/*  92 */     Object defaultFormBackground = new ExtWindowsDesktopProperty(new String[] { "win.3d.shadowColor" }, new Object[] { UIDefaultsLookup.get("control") }, toolkit, new ConvertListener()
/*     */     {
/*     */       public Object convert(Object[] obj) {
/*  95 */         return obj[0];
/*     */       }
/*     */     });
/*  99 */     Object inactiveTabForeground = new ExtWindowsDesktopProperty(new String[] { "win.3d.shadowColor" }, new Object[] { UIDefaultsLookup.get("controlShadow") }, toolkit, new ConvertListener()
/*     */     {
/*     */       public Object convert(Object[] obj) {
/* 102 */         return ((Color)obj[0]).darker();
/*     */       }
/*     */     });
/* 106 */     Object focusedButtonColor = UIDefaultsLookup.get("Menu.selectionBackground");
/*     */ 
/* 108 */     Object selectedAndFocusedButtonColor = UIDefaultsLookup.get("Menu.selectionBackground");
/*     */ 
/* 110 */     Object selectedButtonColor = UIDefaultsLookup.get("Menu.selectionBackground");
/*     */ 
/* 112 */     Object selectionBackgroundColor = UIDefaultsLookup.get("TextField.selectionBackground");
/*     */ 
/* 114 */     Object buttonBorder = new BasicBorders.MarginBorder();
/*     */ 
/* 116 */     Object[] uiDefaults = { "JideLabel.font", controlFont, "JideLabel.background", defaultBackgroundColor, "JideLabel.foreground", defaultTextColor, "JideButton.selectedAndFocusedBackground", selectedAndFocusedButtonColor, "JideButton.focusedBackground", focusedButtonColor, "JideButton.selectedBackground", selectedButtonColor, "JideButton.borderColor", selectionBackgroundColor, "JideButton.font", controlFont, "JideButton.background", defaultBackgroundColor, "JideButton.foreground", defaultTextColor, "JideButton.shadow", defaultShadowColor, "JideButton.darkShadow", defaultDarkShadowColor, "JideButton.light", defaultLightColor, "JideButton.highlight", defaultHighlightColor, "JideButton.border", buttonBorder, "JideButton.margin", new InsetsUIResource(3, 3, 3, 3), "JideButton.textIconGap", Integer.valueOf(4), "JideButton.textShiftOffset", Integer.valueOf(0), "JideButton.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released", "ENTER", "pressed", "released ENTER", "released" }), "JideSplitPane.dividerSize", Integer.valueOf(3), "JideSplitPaneDivider.border", new BorderUIResource(BorderFactory.createEmptyBorder()), "JideSplitPaneDivider.background", defaultBackgroundColor, "JideTabbedPane.defaultTabShape", Integer.valueOf(2), "JideTabbedPane.defaultResizeMode", Integer.valueOf(1), "JideTabbedPane.defaultTabColorTheme", Integer.valueOf(3), "JideTabbedPane.tabRectPadding", Integer.valueOf(2), "JideTabbedPane.closeButtonMarginHorizonal", Integer.valueOf(3), "JideTabbedPane.closeButtonMarginVertical", Integer.valueOf(3), "JideTabbedPane.textMarginVertical", Integer.valueOf(4), "JideTabbedPane.noIconMargin", Integer.valueOf(2), "JideTabbedPane.iconMargin", Integer.valueOf(5), "JideTabbedPane.textPadding", Integer.valueOf(6), "JideTabbedPane.buttonSize", Integer.valueOf(18), "JideTabbedPane.buttonMargin", Integer.valueOf(5), "JideTabbedPane.fitStyleBoundSize", Integer.valueOf(8), "JideTabbedPane.fitStyleFirstTabMargin", Integer.valueOf(4), "JideTabbedPane.fitStyleIconMinWidth", Integer.valueOf(24), "JideTabbedPane.fitStyleTextMinWidth", Integer.valueOf(16), "JideTabbedPane.compressedStyleNoIconRectSize", Integer.valueOf(24), "JideTabbedPane.compressedStyleIconMargin", Integer.valueOf(12), "JideTabbedPane.compressedStyleCloseButtonMarginHorizontal", Integer.valueOf(0), "JideTabbedPane.compressedStyleCloseButtonMarginVertical", Integer.valueOf(0), "JideTabbedPane.fixedStyleRectSize", Integer.valueOf(60), "JideTabbedPane.closeButtonMargin", Integer.valueOf(2), "JideTabbedPane.gripLeftMargin", Integer.valueOf(4), "JideTabbedPane.closeButtonMarginSize", Integer.valueOf(6), "JideTabbedPane.closeButtonLeftMargin", Integer.valueOf(1), "JideTabbedPane.closeButtonRightMargin", Integer.valueOf(1), "JideTabbedPane.defaultTabBorderShadowColor", new ColorUIResource(115, 109, 99), "JideTabbedPane.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "JideTabbedPane.background", defaultFormBackground, "JideTabbedPane.foreground", defaultTextColor, "JideTabbedPane.light", defaultLightColor, "JideTabbedPane.highlight", defaultHighlightColor, "JideTabbedPane.shadow", defaultShadowColor, "JideTabbedPane.tabInsets", new InsetsUIResource(1, 4, 1, 4), "JideTabbedPane.contentBorderInsets", new InsetsUIResource(2, 2, 2, 2), "JideTabbedPane.ignoreContentBorderInsetsIfNoTabs", Boolean.FALSE, "JideTabbedPane.tabAreaInsets", new InsetsUIResource(2, 4, 0, 4), "JideTabbedPane.tabAreaBackground", defaultFormBackground, "JideTabbedPane.tabAreaBackgroundLt", defaultHighlightColor, "JideTabbedPane.tabAreaBackgroundDk", defaultBackgroundColor, "JideTabbedPane.tabRunOverlay", Integer.valueOf(2), "JideTabbedPane.font", controlSmallFont, "JideTabbedPane.selectedTabFont", controlSmallFont, "JideTabbedPane.darkShadow", defaultTextColor, "JideTabbedPane.selectedTabTextForeground", defaultTextColor, "JideTabbedPane.unselectedTabTextForeground", inactiveTabForeground, "JideTabbedPane.selectedTabBackground", defaultBackgroundColor, "JideTabbedPane.selectedTabBackgroundLt", new ColorUIResource(230, 139, 44), "JideTabbedPane.selectedTabBackgroundDk", new ColorUIResource(255, 199, 60), "JideTabbedPane.tabListBackground", UIDefaultsLookup.getColor("List.background"), "JideTabbedPane.textIconGap", Integer.valueOf(4), "JideTabbedPane.showIconOnTab", Boolean.TRUE, "JideTabbedPane.showCloseButtonOnTab", Boolean.TRUE, "JideTabbedPane.closeButtonAlignment", Integer.valueOf(11), "JideTabbedPane.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "RIGHT", "navigateRight", "KP_RIGHT", "navigateRight", "LEFT", "navigateLeft", "KP_LEFT", "navigateLeft", "UP", "navigateUp", "KP_UP", "navigateUp", "DOWN", "navigateDown", "KP_DOWN", "navigateDown", "ctrl DOWN", "requestFocusForVisibleComponent", "ctrl KP_DOWN", "requestFocusForVisibleComponent" }), "JideTabbedPane.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "ctrl PAGE_DOWN", "navigatePageDown", "ctrl PAGE_UP", "navigatePageUp", "ctrl UP", "requestFocus", "ctrl KP_UP", "requestFocus" }), "Resizable.resizeBorder", resizeBorder, "MeterProgressBar.border", new BorderUIResource(BorderFactory.createLineBorder(Color.BLACK)), "MeterProgressBar.background", new ColorUIResource(Color.BLACK), "MeterProgressBar.foreground", new ColorUIResource(Color.GREEN), "MeterProgressBar.cellForeground", new ColorUIResource(Color.GREEN), "MeterProgressBar.cellBackground", new ColorUIResource(32768), "MeterProgressBar.cellLength", Integer.valueOf(2), "MeterProgressBar.cellSpacing", Integer.valueOf(2), "ButtonPanel.order", "CA", "ButtonPanel.oppositeOrder", "HO", "ButtonPanel.buttonGap", Integer.valueOf(6), "ButtonPanel.groupGap", Integer.valueOf(12), "ButtonPanel.minButtonWidth", Integer.valueOf(69), "Contour.color", new ColorUIResource(136, 136, 136), "Contour.thickness", Integer.valueOf(4), "Gripper.size", Integer.valueOf(8), "Gripper.foreground", defaultShadowColor, "Icon.floating", Boolean.FALSE, "JideScrollPane.border", table.getBorder("ScrollPane.border"), "JideSplitButton.font", controlFont, "JideSplitButton.background", defaultBackgroundColor, "JideSplitButton.foreground", defaultTextColor, "JideSplitButton.margin", new InsetsUIResource(3, 3, 3, 7), "JideSplitButton.border", buttonBorder, "JideSplitButton.borderPainted", Boolean.FALSE, "JideSplitButton.textIconGap", Integer.valueOf(3), "JideSplitButton.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released", "ENTER", "pressed", "released ENTER", "released", "DOWN", "downPressed", "released DOWN", "downReleased" }), "Cursor.hsplit", JideIconsFactory.getImageIcon("jide/cursor_h_split.gif"), "Cursor.vsplit", JideIconsFactory.getImageIcon("jide/cursor_v_split.gif"), "Cursor.north", JideIconsFactory.getImageIcon("jide/cursor_north.gif"), "Cursor.south", JideIconsFactory.getImageIcon("jide/cursor_south.gif"), "Cursor.east", JideIconsFactory.getImageIcon("jide/cursor_east.gif"), "Cursor.west", JideIconsFactory.getImageIcon("jide/cursor_west.gif"), "Cursor.tab", JideIconsFactory.getImageIcon("jide/cursor_tab.gif"), "Cursor.float", JideIconsFactory.getImageIcon("jide/cursor_float.gif"), "Cursor.vertical", JideIconsFactory.getImageIcon("jide/cursor_vertical.gif"), "Cursor.horizontal", JideIconsFactory.getImageIcon("jide/cursor_horizontal.gif"), "Cursor.delete", JideIconsFactory.getImageIcon("jide/cursor_delete.gif"), "Cursor.drag", JideIconsFactory.getImageIcon("jide/cursor_drag.gif"), "Cursor.dragStop", JideIconsFactory.getImageIcon("jide/cursor_drag_stop.gif"), "Cursor.dragText", JideIconsFactory.getImageIcon("jide/cursor_drag_text.gif"), "Cursor.dragTextStop", JideIconsFactory.getImageIcon("jide/cursor_drag_text_stop.gif"), "Cursor.percentage", JideIconsFactory.getImageIcon("jide/cursor_percentage.gif"), "Cursor.moveEast", JideIconsFactory.getImageIcon("jide/cursor_move_east.gif"), "Cursor.moveWest", JideIconsFactory.getImageIcon("jide/cursor_move_west.gif") };
/*     */ 
/* 291 */     table.putDefaults(uiDefaults);
/*     */ 
/* 293 */     int products = LookAndFeelFactory.getProductsUsed();
/*     */ 
/* 295 */     if ((products & 0x1) != 0) {
/* 296 */       Color slidingBorderColor = new Color(190, 190, 190);
/* 297 */       Object slidingEastFrameBorder = BorderFactory.createLineBorder(slidingBorderColor);
/* 298 */       Object slidingWestFrameBorder = BorderFactory.createLineBorder(slidingBorderColor);
/* 299 */       Object slidingNorthFrameBorder = BorderFactory.createLineBorder(slidingBorderColor);
/* 300 */       Object slidingSouthFrameBorder = BorderFactory.createLineBorder(slidingBorderColor);
/*     */ 
/* 302 */       uiDefaults = new Object[] { "DockableFrame.usingMacStandardIcons", Boolean.TRUE, "DockableFrame.defaultIcon", JideIconsFactory.getImageIcon("jide/dockableframe_blank.gif"), "DockableFrame.background", defaultBackgroundColor, "DockableFrame.border", new BorderUIResource(BorderFactory.createLineBorder(Color.lightGray, 1)), "DockableFrame.floatingBorder", new BorderUIResource(BorderFactory.createLineBorder(Color.lightGray, 1)), "DockableFrame.slidingEastBorder", slidingEastFrameBorder, "DockableFrame.slidingWestBorder", slidingWestFrameBorder, "DockableFrame.slidingNorthBorder", slidingNorthFrameBorder, "DockableFrame.slidingSouthBorder", slidingSouthFrameBorder, "DockableFrame.activeTitleBackground", UIDefaultsLookup.getColor("InternalFrame.activeTitleBackground"), "DockableFrame.activeTitleForeground", UIDefaultsLookup.getColor("InternalFrame.activeTitleForeground"), "DockableFrame.inactiveTitleBackground", UIDefaultsLookup.getColor("InternalFrame.inactiveTitleBackground"), "DockableFrame.inactiveTitleForeground", UIDefaultsLookup.getColor("InternalFrame.inactiveTitleForeground"), "DockableFrame.titleBorder", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "DockableFrame.activeTitleBorderColor", UIDefaultsLookup.getColor("InternalFrame.activeTitleBackground"), "DockableFrame.inactiveTitleBorderColor", defaultShadowColor, "DockableFrame.font", controlFont, "DockableFrameTitlePane.font", controlSmallFont, "DockableFrameTitlePane.titleBarComponent", Boolean.FALSE, "DockableFrameTitlePane.alwaysShowAllButtons", Boolean.TRUE, "DockableFrameTitlePane.buttonsAlignment", Integer.valueOf(10), "DockableFrameTitlePane.titleAlignment", Integer.valueOf(0), "DockableFrameTitlePane.buttonGap", Integer.valueOf(3), "DockableFrameTitlePane.showIcon", Boolean.FALSE, "DockableFrameTitlePane.margin", new InsetsUIResource(0, 10, 0, 3), "SidePane.margin", new InsetsUIResource(1, 1, 1, 1), "SidePane.iconTextGap", Integer.valueOf(2), "SidePane.textBorderGap", Integer.valueOf(13), "SidePane.itemGap", Integer.valueOf(5), "SidePane.groupGap", Integer.valueOf(8), "SidePane.foreground", defaultDarkShadowColor, "SidePane.background", defaultFormBackground, "SidePane.lineColor", new Color(151, 151, 151), "SidePane.buttonBackground", new Color(133, 133, 133), "SidePane.selectedButtonBackground", new Color(133, 133, 133), "SidePane.selectedButtonForeground", defaultTextColor, "SidePane.font", controlSmallFont, "SidePane.orientation", Integer.valueOf(1), "SidePane.showSelectedTabText", Boolean.TRUE, "SidePane.alwaysShowTabText", Boolean.TRUE, "Workspace.background", mdiBackgroundColor, "DockingFramework.changeCursor", Boolean.FALSE, "FrameContainer.contentBorderInsets", new InsetsUIResource(0, 0, 0, 0), "ContentContainer.background", defaultFormBackground, "ContentContainer.vgap", Integer.valueOf(1), "ContentContainer.hgap", Integer.valueOf(1), "MainContainer.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)) };
/*     */ 
/* 360 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 363 */     if ((products & 0x2) != 0) {
/* 364 */       ImageIcon collapsiblePaneImage = IconsFactory.getImageIcon(AquaJideUtils.class, "icons/collapsible_pane_aqua.gif");
/* 365 */       int collapsiblePaneSize = 12;
/*     */ 
/* 367 */       uiDefaults = new Object[] { "CollapsiblePanes.border", new BorderUIResource(BorderFactory.createEmptyBorder(12, 12, 12, 12)), "CollapsiblePanes.gap", Integer.valueOf(15), "CollapsiblePane.background", UIDefaultsLookup.getColor("InternalFrame.inactiveTitleBackground"), "CollapsiblePane.contentBackground", defaultHighlightColor, "CollapsiblePane.foreground", UIDefaultsLookup.getColor("InternalFrame.activeTitleForeground"), "CollapsiblePane.emphasizedBackground", UIDefaultsLookup.getColor("InternalFrame.activeTitleBackground"), "CollapsiblePane.emphasizedForeground", UIDefaultsLookup.getColor("InternalFrame.activeTitleForeground"), "CollapsiblePane.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "CollapsiblePane.font", controlFont, "CollapsiblePane.contentBorder", new BorderUIResource(BorderFactory.createEmptyBorder(8, 10, 8, 10)), "CollapsiblePane.titleBorder", new BorderUIResource(BorderFactory.createEmptyBorder(3, 3, 3, 3)), "CollapsiblePane.titleFont", boldFont, "CollapsiblePane.downIcon", IconsFactory.getIcon(null, collapsiblePaneImage, 0, 0, 12, 12), "CollapsiblePane.upIcon", IconsFactory.getIcon(null, collapsiblePaneImage, 0, 12, 12, 12), "StatusBarItem.border", new BorderUIResource(BorderFactory.createLineBorder(UIDefaultsLookup.getColor("controlShadow"), 1)), "StatusBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(2, 0, 0, 0)), "StatusBar.gap", Integer.valueOf(2), "StatusBar.background", defaultBackgroundColor, "StatusBar.font", controlFont, "MemoryStatusBarItem.fillColor", new ColorUIResource(236, 233, 176), "DocumentPane.groupBorder", new BorderUIResource(BorderFactory.createLineBorder(Color.gray)), "DocumentPane.newHorizontalGroupIcon", JideIconsFactory.getImageIcon("jide/windows_new_horizontal_tab_group.png"), "DocumentPane.newVerticalGroupIcon", JideIconsFactory.getImageIcon("jide/windows_new_vertical_tab_group.png"), "DocumentPane.boldActiveTab", Boolean.TRUE, "OutlookTabbedPane.buttonStyle", Integer.valueOf(1), "FloorTabbedPane.buttonStyle", Integer.valueOf(1) };
/*     */ 
/* 402 */       table.putDefaults(uiDefaults);
/*     */     }
/* 404 */     if ((products & 0x10) != 0) {
/* 405 */       uiDefaults = new Object[] { "CommandBar.font", controlFont, "CommandBar.background", defaultBackgroundColor, "CommandBar.foreground", defaultTextColor, "CommandBar.shadow", defaultShadowColor, "CommandBar.darkShadow", defaultDarkShadowColor, "CommandBar.light", defaultLightColor, "CommandBar.highlight", defaultHighlightColor, "CommandBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(1, 1, 1, 1)), "CommandBar.borderVert", new BorderUIResource(BorderFactory.createEmptyBorder(1, 1, 1, 1)), "CommandBar.borderFloating", new BorderUIResource(BorderFactory.createEmptyBorder(2, 2, 2, 2)), "CommandBar.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "UP", "navigateUp", "KP_UP", "navigateUp", "DOWN", "navigateDown", "KP_DOWN", "navigateDown", "LEFT", "navigateLeft", "KP_LEFT", "navigateLeft", "RIGHT", "navigateRight", "KP_RIGHT", "navigateRight" }), "CommandBar.titleBarSize", Integer.valueOf(17), "CommandBar.titleBarButtonGap", Integer.valueOf(1), "CommandBar.titleBarBackground", UIDefaultsLookup.getColor("InternalFrame.activeTitleBackground"), "CommandBar.titleBarForeground", defaultTextColor, "CommandBar.titleBarFont", boldFont, "CommandBar.minimumSize", new DimensionUIResource(20, 20), "CommandBar.separatorSize", Integer.valueOf(5), "CommandBarSeparator.background", new Color(219, 216, 209), "CommandBarSeparator.foreground", new Color(166, 166, 166), "Chevron.size", Integer.valueOf(11), "Chevron.alwaysVisible", Boolean.FALSE };
/*     */ 
/* 446 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 449 */     if ((products & 0x4) != 0) {
/* 450 */       uiDefaults = new Object[] { "AbstractComboBox.useJButton", Boolean.FALSE, "GroupList.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "TAB", "selectNextGroup", "shift TAB", "selectPreviousGroup" }) };
/*     */ 
/* 458 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 461 */     if ((products & 0x4000) != 0) {
/* 462 */       uiDefaults = new Object[] { "DiffMerge.changed", new ColorUIResource(196, 196, 255), "DiffMerge.deleted", new ColorUIResource(200, 200, 200), "DiffMerge.inserted", new ColorUIResource(196, 255, 196), "DiffMerge.conflicted", new ColorUIResource(255, 153, 153) };
/*     */ 
/* 468 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 471 */     UIDefaultsLookup.put(table, "Theme.painter", AquaPainter.getInstance());
/*     */   }
/*     */ 
/*     */   public static boolean isGraphite() {
/* 475 */     String appleAquaColorVariant = AquaPreferences.getString("AppleAquaColorVariant");
/* 476 */     return "6".equals(appleAquaColorVariant);
/*     */   }
/*     */ 
/*     */   public static Color[] reverse(Color[] colors)
/*     */   {
/* 534 */     Color[] reverse = new Color[colors.length];
/* 535 */     for (int i = 0; i < colors.length; i++) {
/* 536 */       reverse[i] = colors[(colors.length - i - 1)];
/*     */     }
/* 538 */     return reverse;
/*     */   }
/*     */ 
/*     */   public static void fillAquaGradientHorizontal(Graphics g, Shape shape, Color[] colors) {
/* 542 */     Color[] c = colors;
/* 543 */     if ((c == null) || (c.length != 4)) {
/* 544 */       c = AQUA_WHITE;
/*     */     }
/*     */ 
/* 547 */     Graphics2D g2d = (Graphics2D)g;
/*     */ 
/* 551 */     Rectangle rect = shape.getBounds();
/* 552 */     Rectangle r2 = new Rectangle(rect.x, rect.y + rect.height / 2, rect.width, rect.height / 2);
/* 553 */     Rectangle r1 = new Rectangle(rect.x, rect.y, rect.width, rect.height / 2);
/* 554 */     JideSwingUtilities.fillGradient(g2d, r1, c[0], c[1], true);
/* 555 */     JideSwingUtilities.fillGradient(g2d, r2, c[2], c[3], true);
/*     */   }
/*     */ 
/*     */   public static void fillAquaGradientVertical(Graphics g, Shape shape, Color[] colors)
/*     */   {
/* 560 */     Color[] c = colors;
/* 561 */     if ((c == null) || (c.length != 4)) {
/* 562 */       c = AQUA_WHITE;
/*     */     }
/*     */ 
/* 565 */     Graphics2D g2d = (Graphics2D)g;
/*     */ 
/* 568 */     Rectangle rect = shape.getBounds();
/* 569 */     Rectangle r2 = new Rectangle(rect.x + rect.width / 2, rect.y, rect.width / 2, rect.height);
/* 570 */     Rectangle r1 = new Rectangle(rect.x, rect.y, rect.width / 2, rect.height);
/* 571 */     JideSwingUtilities.fillGradient(g2d, r1, c[0], c[1], false);
/* 572 */     JideSwingUtilities.fillGradient(g2d, r2, c[2], c[3], false);
/*     */   }
/*     */ 
/*     */   public static void fillSquareButtonHorizontal(Graphics g, Shape shape, Color[] colors)
/*     */   {
/* 577 */     Color[] c = colors;
/* 578 */     if ((c == null) || (c.length != 2)) {
/* 579 */       c = HALF_LIGHT;
/*     */     }
/*     */ 
/* 582 */     Graphics2D g2d = (Graphics2D)g;
/*     */ 
/* 585 */     Rectangle rect = shape.getBounds();
/* 586 */     g2d.setColor(c[0]);
/* 587 */     g2d.fillRect(rect.x, rect.y, rect.width, rect.height / 2);
/* 588 */     g2d.setColor(c[1]);
/* 589 */     g2d.fillRect(rect.x, rect.y + rect.height / 2, rect.width, rect.height / 2);
/*     */   }
/*     */ 
/*     */   public static void fillSquareButtonVertical(Graphics g, Shape shape, Color[] colors)
/*     */   {
/* 595 */     Color[] c = colors;
/* 596 */     if ((c == null) || (c.length != 2)) {
/* 597 */       c = HALF_LIGHT;
/*     */     }
/*     */ 
/* 600 */     Graphics2D g2d = (Graphics2D)g;
/*     */ 
/* 603 */     Rectangle rect = shape.getBounds();
/* 604 */     g.setColor(c[0]);
/* 605 */     g.fillRect(rect.x, rect.y, rect.width / 2, rect.height);
/* 606 */     g.setColor(c[1]);
/* 607 */     g.fillRect(rect.x + rect.width / 2, rect.y, rect.width / 2, rect.height);
/*     */   }
/*     */ 
/*     */   public static void antialiasShape(Graphics g, boolean onoff)
/*     */   {
/* 613 */     Graphics2D g2d = (Graphics2D)g;
/* 614 */     g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, onoff ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
/*     */   }
/*     */ 
/*     */   public static void antialiasText(Graphics g, boolean onoff)
/*     */   {
/* 620 */     Graphics2D g2d = (Graphics2D)g;
/* 621 */     g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, onoff ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.aqua.AquaJideUtils
 * JD-Core Version:    0.6.0
 */
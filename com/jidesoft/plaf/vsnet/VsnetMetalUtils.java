/*     */ package com.jidesoft.plaf.vsnet;
/*     */ 
/*     */ import com.jidesoft.icons.IconsFactory;
/*     */ import com.jidesoft.icons.JideIconsFactory;
/*     */ import com.jidesoft.plaf.LookAndFeelFactory;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.plaf.basic.Painter;
/*     */ import com.jidesoft.plaf.basic.ThemePainter;
/*     */ import com.jidesoft.plaf.metal.MetalPainter;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import com.jidesoft.utils.ColorUtils;
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
/*     */ public class VsnetMetalUtils extends VsnetLookAndFeelExtension
/*     */ {
/*     */   public static void initClassDefaultsWithMenu(UIDefaults table)
/*     */   {
/*  40 */     if (!Beans.isDesignTime()) {
/*  41 */       table.put("MenuUI", "com.jidesoft.plaf.metal.MetalMenuUI");
/*     */     }
/*  43 */     initClassDefaults(table);
/*     */   }
/*     */ 
/*     */   public static void initClassDefaults(UIDefaults table) {
/*  47 */     VsnetLookAndFeelExtension.initClassDefaults(table);
/*     */ 
/*  49 */     String metalPackageName = "com.jidesoft.plaf.metal.";
/*     */ 
/*  52 */     table.put("JideSplitButtonUI", "com.jidesoft.plaf.metal.MetalJideSplitButtonUI");
/*  53 */     table.put("RangeSliderUI", "com.jidesoft.plaf.metal.MetalRangeSliderUI");
/*     */   }
/*     */ 
/*     */   public static void initComponentDefaults(UIDefaults table)
/*     */   {
/*  62 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*     */ 
/*  64 */     Object defaultTextColor = UIDefaultsLookup.get("controlText");
/*  65 */     Object defaultBackgroundColor = UIDefaultsLookup.get("control");
/*  66 */     Object defaultHighlightColor = UIDefaultsLookup.get("controlHighlight");
/*  67 */     Object defaultLtHighlightColor = UIDefaultsLookup.get("controlLtHighlight");
/*  68 */     Object defaultShadowColor = UIDefaultsLookup.get("controlShadow");
/*  69 */     Object defaultDarkShadowColor = UIDefaultsLookup.get("controlDkShadow");
/*  70 */     Object activeTitleTextColor = UIDefaultsLookup.get("activeCaptionText");
/*  71 */     Object activeTitleBackgroundColor = UIDefaultsLookup.get("activeCaption");
/*  72 */     Object activeTitleBorderColor = UIDefaultsLookup.get("controlDkShadow");
/*  73 */     Object inactiveTitleTextColor = UIDefaultsLookup.get("controlText");
/*  74 */     Object inactiveTitleBackgroundColor = UIDefaultsLookup.get("control");
/*  75 */     Object mdiBackgroundColor = UIDefaultsLookup.get("controlShadow");
/*     */ 
/*  77 */     Object controlFont = JideSwingUtilities.getControlFont(toolkit, table);
/*  78 */     Object toolbarFont = JideSwingUtilities.getMenuFont(toolkit, table);
/*     */ 
/*  80 */     Object singleLineBorder = new BorderUIResource(BorderFactory.createLineBorder(UIDefaultsLookup.getColor("controlShadow")));
/*     */ 
/*  82 */     Object slidingEastFrameBorder = new ResizeFrameBorder(UIDefaultsLookup.getColor("control"), UIDefaultsLookup.getColor("controlLtHighlight"), UIDefaultsLookup.getColor("controlShadow"), UIDefaultsLookup.getColor("controlDkShadow"), new Insets(0, 4, 0, 0));
/*     */ 
/*  85 */     Object slidingWestFrameBorder = new ResizeFrameBorder(UIDefaultsLookup.getColor("control"), UIDefaultsLookup.getColor("controlLtHighlight"), UIDefaultsLookup.getColor("controlShadow"), UIDefaultsLookup.getColor("controlDkShadow"), new Insets(0, 0, 0, 4));
/*     */ 
/*  88 */     Object slidingNorthFrameBorder = new ResizeFrameBorder(UIDefaultsLookup.getColor("control"), UIDefaultsLookup.getColor("controlLtHighlight"), UIDefaultsLookup.getColor("controlShadow"), UIDefaultsLookup.getColor("controlDkShadow"), new Insets(0, 0, 4, 0));
/*     */ 
/*  91 */     Object slidingSouthFrameBorder = new ResizeFrameBorder(UIDefaultsLookup.getColor("control"), UIDefaultsLookup.getColor("controlLtHighlight"), UIDefaultsLookup.getColor("controlShadow"), UIDefaultsLookup.getColor("controlDkShadow"), new Insets(4, 0, 0, 0));
/*     */ 
/*  94 */     Object resizeBorder = new ResizeFrameBorder(UIDefaultsLookup.getColor("control"), UIDefaultsLookup.getColor("controlLtHighlight"), UIDefaultsLookup.getColor("controlShadow"), UIDefaultsLookup.getColor("controlDkShadow"), new Insets(4, 4, 4, 4));
/*     */ 
/*  97 */     Object defaultFormBackgroundColor = VsnetUtils.getLighterColor((Color)activeTitleBackgroundColor);
/*     */ 
/*  99 */     Color highlightColor = UIDefaultsLookup.getColor("textHighlight");
/*     */ 
/* 101 */     Object focusedButtonColor = new ColorUIResource(VsnetUtils.getRolloverButtonColor(highlightColor));
/*     */ 
/* 104 */     Object selectedAndFocusedButtonColor = new ColorUIResource(VsnetUtils.getSelectedAndRolloverButtonColor(highlightColor));
/*     */ 
/* 107 */     Object selectedButtonColor = new ColorUIResource(VsnetUtils.getSelectedButtonColor(highlightColor));
/*     */ 
/* 110 */     Painter gripperPainter = new Painter() {
/*     */       public void paint(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 112 */         Object p = UIDefaultsLookup.get("Theme.painter");
/* 113 */         if ((p instanceof ThemePainter)) {
/* 114 */           ((ThemePainter)p).paintGripper(c, g, rect, orientation, state);
/*     */         }
/*     */         else
/* 117 */           MetalPainter.getInstance().paintGripper(c, g, rect, orientation, state);
/*     */       }
/*     */     };
/* 122 */     Object buttonBorder = new BasicBorders.MarginBorder();
/*     */ 
/* 124 */     Object[] uiDefaults = { "JideLabel.font", controlFont, "JideLabel.background", defaultBackgroundColor, "JideLabel.foreground", defaultTextColor, "JideButton.selectedAndFocusedBackground", selectedAndFocusedButtonColor, "JideButton.focusedBackground", focusedButtonColor, "JideButton.selectedBackground", selectedButtonColor, "JideButton.borderColor", Color.black, "JideButton.font", controlFont, "JideButton.background", defaultBackgroundColor, "JideButton.foreground", defaultTextColor, "JideButton.shadow", defaultShadowColor, "JideButton.darkShadow", defaultDarkShadowColor, "JideButton.light", defaultHighlightColor, "JideButton.highlight", defaultLtHighlightColor, "JideButton.border", buttonBorder, "JideButton.margin", new InsetsUIResource(3, 3, 3, 3), "JideButton.textIconGap", Integer.valueOf(2), "JideButton.textShiftOffset", Integer.valueOf(0), "JideButton.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released" }), "JideScrollPane.border", singleLineBorder, "JideSplitPane.dividerSize", Integer.valueOf(3), "JideSplitPaneDivider.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "JideSplitPaneDivider.background", defaultBackgroundColor, "JideSplitPaneDivider.gripperPainter", gripperPainter, "JideTabbedPane.defaultTabShape", Integer.valueOf(2), "JideTabbedPane.defaultResizeMode", Integer.valueOf(1), "JideTabbedPane.defaultTabColorTheme", Integer.valueOf(3), "JideTabbedPane.tabRectPadding", Integer.valueOf(2), "JideTabbedPane.closeButtonMarginHorizonal", Integer.valueOf(3), "JideTabbedPane.closeButtonMarginVertical", Integer.valueOf(3), "JideTabbedPane.textMarginVertical", Integer.valueOf(4), "JideTabbedPane.noIconMargin", Integer.valueOf(2), "JideTabbedPane.iconMargin", Integer.valueOf(5), "JideTabbedPane.textPadding", Integer.valueOf(6), "JideTabbedPane.buttonSize", Integer.valueOf(18), "JideTabbedPane.buttonMargin", Integer.valueOf(5), "JideTabbedPane.fitStyleBoundSize", Integer.valueOf(8), "JideTabbedPane.fitStyleFirstTabMargin", Integer.valueOf(4), "JideTabbedPane.fitStyleIconMinWidth", Integer.valueOf(24), "JideTabbedPane.fitStyleTextMinWidth", Integer.valueOf(16), "JideTabbedPane.compressedStyleNoIconRectSize", Integer.valueOf(24), "JideTabbedPane.compressedStyleIconMargin", Integer.valueOf(12), "JideTabbedPane.compressedStyleCloseButtonMarginHorizontal", Integer.valueOf(0), "JideTabbedPane.compressedStyleCloseButtonMarginVertical", Integer.valueOf(0), "JideTabbedPane.fixedStyleRectSize", Integer.valueOf(60), "JideTabbedPane.closeButtonMargin", Integer.valueOf(2), "JideTabbedPane.gripLeftMargin", Integer.valueOf(4), "JideTabbedPane.closeButtonMarginSize", Integer.valueOf(6), "JideTabbedPane.closeButtonLeftMargin", Integer.valueOf(1), "JideTabbedPane.closeButtonRightMargin", Integer.valueOf(1), "JideTabbedPane.defaultTabBorderShadowColor", new ColorUIResource(115, 109, 99), "JideTabbedPane.gripperPainter", gripperPainter, "JideTabbedPane.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "JideTabbedPane.background", defaultFormBackgroundColor, "JideTabbedPane.foreground", defaultTextColor, "JideTabbedPane.light", defaultHighlightColor, "JideTabbedPane.highlight", defaultLtHighlightColor, "JideTabbedPane.shadow", defaultShadowColor, "JideTabbedPane.darkShadow", defaultTextColor, "JideTabbedPane.tabInsets", new InsetsUIResource(1, 4, 1, 4), "JideTabbedPane.contentBorderInsets", new InsetsUIResource(2, 2, 2, 2), "JideTabbedPane.ignoreContentBorderInsetsIfNoTabs", Boolean.FALSE, "JideTabbedPane.tabAreaInsets", new InsetsUIResource(2, 4, 0, 4), "JideTabbedPane.tabAreaBackground", defaultFormBackgroundColor, "JideTabbedPane.tabAreaBackgroundLt", defaultLtHighlightColor, "JideTabbedPane.tabAreaBackgroundDk", defaultBackgroundColor, "JideTabbedPane.tabRunOverlay", Integer.valueOf(2), "JideTabbedPane.font", controlFont, "JideTabbedPane.selectedTabFont", controlFont, "JideTabbedPane.selectedTabTextForeground", defaultDarkShadowColor, "JideTabbedPane.unselectedTabTextForeground", defaultDarkShadowColor, "JideTabbedPane.selectedTabBackground", defaultBackgroundColor, "JideTabbedPane.selectedTabBackgroundLt", new ColorUIResource(230, 139, 44), "JideTabbedPane.selectedTabBackgroundDk", new ColorUIResource(255, 199, 60), "JideTabbedPane.tabListBackground", UIDefaultsLookup.getColor("List.background"), "JideTabbedPane.textIconGap", Integer.valueOf(4), "JideTabbedPane.showIconOnTab", Boolean.TRUE, "JideTabbedPane.showCloseButtonOnTab", Boolean.FALSE, "JideTabbedPane.closeButtonAlignment", Integer.valueOf(11), "JideTabbedPane.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "RIGHT", "navigateRight", "KP_RIGHT", "navigateRight", "LEFT", "navigateLeft", "KP_LEFT", "navigateLeft", "UP", "navigateUp", "KP_UP", "navigateUp", "DOWN", "navigateDown", "KP_DOWN", "navigateDown", "ctrl DOWN", "requestFocusForVisibleComponent", "ctrl KP_DOWN", "requestFocusForVisibleComponent" }), "JideTabbedPane.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "ctrl PAGE_DOWN", "navigatePageDown", "ctrl PAGE_UP", "navigatePageUp", "ctrl UP", "requestFocus", "ctrl KP_UP", "requestFocus" }), "Resizable.resizeBorder", resizeBorder, "Gripper.size", Integer.valueOf(8), "Gripper.foreground", defaultBackgroundColor, "Gripper.painter", gripperPainter, "MenuBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(1, 0, 1, 0)), "Icon.floating", Boolean.FALSE, "JideSplitButton.font", controlFont, "JideSplitButton.margin", new InsetsUIResource(3, 3, 3, 7), "JideSplitButton.border", buttonBorder, "JideSplitButton.borderPainted", Boolean.FALSE, "JideSplitButton.textIconGap", Integer.valueOf(3), "JideSplitButton.selectionBackground", UIDefaultsLookup.getColor("MenuItem.selectionBackground"), "JideSplitButton.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released", "DOWN", "downPressed", "released DOWN", "downReleased" }), "ButtonPanel.order", "ACO", "ButtonPanel.oppositeOrder", "H", "ButtonPanel.buttonGap", Integer.valueOf(5), "ButtonPanel.groupGap", Integer.valueOf(5), "ButtonPanel.minButtonWidth", Integer.valueOf(57), "MeterProgressBar.border", new BorderUIResource(BorderFactory.createLineBorder(Color.BLACK)), "MeterProgressBar.background", new ColorUIResource(Color.BLACK), "MeterProgressBar.foreground", new ColorUIResource(Color.GREEN), "MeterProgressBar.cellForeground", new ColorUIResource(Color.GREEN), "MeterProgressBar.cellBackground", new ColorUIResource(32768), "MeterProgressBar.cellLength", Integer.valueOf(2), "MeterProgressBar.cellSpacing", Integer.valueOf(2), "HeaderBox.background", defaultBackgroundColor, "Cursor.hsplit", JideIconsFactory.getImageIcon("jide/cursor_h_split.gif"), "Cursor.vsplit", JideIconsFactory.getImageIcon("jide/cursor_v_split.gif"), "Cursor.north", JideIconsFactory.getImageIcon("jide/cursor_north.gif"), "Cursor.south", JideIconsFactory.getImageIcon("jide/cursor_south.gif"), "Cursor.east", JideIconsFactory.getImageIcon("jide/cursor_east.gif"), "Cursor.west", JideIconsFactory.getImageIcon("jide/cursor_west.gif"), "Cursor.tab", JideIconsFactory.getImageIcon("jide/cursor_tab.gif"), "Cursor.float", JideIconsFactory.getImageIcon("jide/cursor_float.gif"), "Cursor.vertical", JideIconsFactory.getImageIcon("jide/cursor_vertical.gif"), "Cursor.horizontal", JideIconsFactory.getImageIcon("jide/cursor_horizontal.gif"), "Cursor.delete", JideIconsFactory.getImageIcon("jide/cursor_delete.gif"), "Cursor.drag", JideIconsFactory.getImageIcon("jide/cursor_drag.gif"), "Cursor.dragStop", JideIconsFactory.getImageIcon("jide/cursor_drag_stop.gif"), "Cursor.dragText", JideIconsFactory.getImageIcon("jide/cursor_drag_text.gif"), "Cursor.dragTextStop", JideIconsFactory.getImageIcon("jide/cursor_drag_text_stop.gif"), "Cursor.percentage", JideIconsFactory.getImageIcon("jide/cursor_percentage.gif"), "Cursor.moveEast", JideIconsFactory.getImageIcon("jide/cursor_move_east.gif"), "Cursor.moveWest", JideIconsFactory.getImageIcon("jide/cursor_move_west.gif") };
/*     */ 
/* 295 */     table.putDefaults(uiDefaults);
/*     */ 
/* 297 */     int products = LookAndFeelFactory.getProductsUsed();
/*     */ 
/* 299 */     if ((products & 0x1) != 0)
/*     */     {
/* 301 */       ImageIcon titleButtonImage = IconsFactory.getImageIcon(VsnetMetalUtils.class, "icons/title_buttons_metal.gif");
/* 302 */       int titleButtonSize = 16;
/*     */ 
/* 304 */       uiDefaults = new Object[] { "Workspace.background", mdiBackgroundColor, "SidePane.margin", new InsetsUIResource(1, 1, 1, 1), "SidePane.iconTextGap", Integer.valueOf(2), "SidePane.textBorderGap", Integer.valueOf(13), "SidePane.itemGap", Integer.valueOf(5), "SidePane.groupGap", Integer.valueOf(13), "SidePane.foreground", defaultTextColor, "SidePane.background", defaultFormBackgroundColor, "SidePane.lineColor", defaultDarkShadowColor, "SidePane.buttonBackground", defaultBackgroundColor, "SidePane.font", controlFont, "SidePane.orientation", Integer.valueOf(1), "SidePane.showSelectedTabText", Boolean.TRUE, "SidePane.alwaysShowTabText", Boolean.FALSE, "DockableFrame.defaultIcon", JideIconsFactory.getImageIcon("jide/dockableframe_blank.gif"), "DockableFrame.background", defaultBackgroundColor, "DockableFrame.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "DockableFrame.floatingBorder", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "DockableFrame.slidingEastBorder", slidingEastFrameBorder, "DockableFrame.slidingWestBorder", slidingWestFrameBorder, "DockableFrame.slidingNorthBorder", slidingNorthFrameBorder, "DockableFrame.slidingSouthBorder", slidingSouthFrameBorder, "DockableFrame.activeTitleBackground", activeTitleBackgroundColor, "DockableFrame.activeTitleForeground", activeTitleTextColor, "DockableFrame.inactiveTitleBackground", inactiveTitleBackgroundColor, "DockableFrame.inactiveTitleForeground", inactiveTitleTextColor, "DockableFrame.titleBorder", new BorderUIResource(BorderFactory.createEmptyBorder(1, 0, 3, 0)), "DockableFrame.inactiveTitleBorderColor", activeTitleBorderColor, "DockableFrame.activeTitleBorderColor", activeTitleBorderColor, "DockableFrame.font", controlFont, "DockableFrameTitlePane.gripperPainter", gripperPainter, "DockableFrameTitlePane.font", controlFont, "DockableFrameTitlePane.hideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 0, 16, 16), "DockableFrameTitlePane.unfloatIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 16, 16, 16), "DockableFrameTitlePane.floatIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 32, 16, 16), "DockableFrameTitlePane.autohideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 48, 16, 16), "DockableFrameTitlePane.stopAutohideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 64, 16, 16), "DockableFrameTitlePane.hideAutohideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 80, 16, 16), "DockableFrameTitlePane.maximizeIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 96, 16, 16), "DockableFrameTitlePane.restoreIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 112, 16, 16), "DockableFrameTitlePane.titleBarComponent", Boolean.FALSE, "DockableFrameTitlePane.alwaysShowAllButtons", Boolean.FALSE, "DockableFrameTitlePane.buttonsAlignment", Integer.valueOf(11), "DockableFrameTitlePane.titleAlignment", Integer.valueOf(10), "DockableFrameTitlePane.buttonGap", Integer.valueOf(2), "DockableFrameTitlePane.showIcon", Boolean.FALSE, "DockableFrameTitlePane.margin", new InsetsUIResource(0, 6, 0, 6), "DockableFrameTitlePane.use3dButtons", Boolean.TRUE, "ContentContainer.background", defaultFormBackgroundColor, "ContentContainer.vgap", Integer.valueOf(1), "ContentContainer.hgap", Integer.valueOf(1), "MainContainer.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "Contour.color", new ColorUIResource(136, 136, 136), "Contour.thickness", Integer.valueOf(4), "DockingFramework.changeCursor", Boolean.FALSE, "FrameContainer.contentBorderInsets", new InsetsUIResource(2, 0, 0, 0) };
/*     */ 
/* 373 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 376 */     if ((products & 0x2) != 0) {
/* 377 */       ImageIcon collapsiblePaneImage = IconsFactory.getImageIcon(VsnetMetalUtils.class, "icons/collapsible_pane_metal.gif");
/* 378 */       int collapsiblePaneSize = 12;
/*     */ 
/* 380 */       uiDefaults = new Object[] { "CollapsiblePanes.border", new BorderUIResource(BorderFactory.createEmptyBorder(12, 12, 12, 12)), "CollapsiblePanes.gap", Integer.valueOf(15), "CollapsiblePane.background", (defaultBackgroundColor instanceof Color) ? ColorUtils.getDerivedColor((Color)defaultBackgroundColor, 0.45F) : defaultBackgroundColor, "CollapsiblePane.contentBackground", defaultHighlightColor, "CollapsiblePane.foreground", defaultTextColor, "CollapsiblePane.emphasizedBackground", activeTitleBackgroundColor, "CollapsiblePane.emphasizedForeground", activeTitleTextColor, "CollapsiblePane.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "CollapsiblePane.font", controlFont, "CollapsiblePane.contentBorder", new BorderUIResource(BorderFactory.createEmptyBorder(8, 10, 8, 10)), "CollapsiblePane.titleBorder", new BorderUIResource(BorderFactory.createEmptyBorder(3, 3, 3, 3)), "CollapsiblePane.titleFont", controlFont, "CollapsiblePane.downIcon", IconsFactory.getIcon(null, collapsiblePaneImage, 0, 0, 12, 12), "CollapsiblePane.upIcon", IconsFactory.getIcon(null, collapsiblePaneImage, 0, 12, 12, 12), "StatusBarItem.border", BorderFactory.createEtchedBorder(), "StatusBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(2, 0, 0, 0)), "StatusBar.gap", Integer.valueOf(5), "StatusBar.background", defaultBackgroundColor, "StatusBar.font", controlFont, "MemoryStatusBarItem.fillColor", new ColorUIResource(236, 233, 176), "DocumentPane.groupBorder", new BorderUIResource(BorderFactory.createLineBorder(Color.gray)), "DocumentPane.newHorizontalGroupIcon", JideIconsFactory.getImageIcon("jide/windows_new_horizontal_tab_group.png"), "DocumentPane.newVerticalGroupIcon", JideIconsFactory.getImageIcon("jide/windows_new_vertical_tab_group.png"), "DocumentPane.boldActiveTab", Boolean.TRUE, "OutlookTabbedPane.buttonStyle", Integer.valueOf(1), "FloorTabbedPane.buttonStyle", Integer.valueOf(1) };
/*     */ 
/* 416 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 419 */     if ((products & 0x10) != 0) {
/* 420 */       uiDefaults = new Object[] { "CommandBar.font", toolbarFont, "CommandBar.background", defaultBackgroundColor, "CommandBar.foreground", defaultTextColor, "CommandBar.shadow", defaultShadowColor, "CommandBar.darkShadow", defaultDarkShadowColor, "CommandBar.light", defaultHighlightColor, "CommandBar.highlight", defaultLtHighlightColor, "CommandBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(1, 1, 1, 1)), "CommandBar.borderVert", new BorderUIResource(BorderFactory.createEmptyBorder(1, 1, 1, 1)), "CommandBar.borderFloating", new BorderUIResource(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder((Color)activeTitleBackgroundColor, 2), BorderFactory.createEmptyBorder(1, 1, 1, 1))), "CommandBar.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "UP", "navigateUp", "KP_UP", "navigateUp", "DOWN", "navigateDown", "KP_DOWN", "navigateDown", "LEFT", "navigateLeft", "KP_LEFT", "navigateLeft", "RIGHT", "navigateRight", "KP_RIGHT", "navigateRight" }), "CommandBar.titleBarSize", Integer.valueOf(17), "CommandBar.titleBarButtonGap", Integer.valueOf(1), "CommandBar.titleBarBackground", activeTitleBackgroundColor, "CommandBar.titleBarForeground", activeTitleTextColor, "CommandBar.titleBarFont", controlFont, "CommandBar.minimumSize", new DimensionUIResource(16, 16), "CommandBar.separatorSize", Integer.valueOf(5), "CommandBarSeparator.background", new Color(219, 216, 209), "CommandBarSeparator.foreground", new Color(166, 166, 166), "Chevron.size", Integer.valueOf(11), "Chevron.alwaysVisible", Boolean.FALSE };
/*     */ 
/* 461 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 464 */     if ((products & 0x4) != 0) {
/* 465 */       uiDefaults = new Object[] { "AbstractComboBox.useJButton", Boolean.TRUE, "NestedTableHeader.cellBorder", UIDefaultsLookup.getBorder("TableHeader.cellBorder"), "GroupList.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "TAB", "selectNextGroup", "shift TAB", "selectPreviousGroup" }) };
/*     */ 
/* 474 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 477 */     if ((products & 0x4000) != 0) {
/* 478 */       uiDefaults = new Object[] { "DiffMerge.changed", new ColorUIResource(196, 196, 255), "DiffMerge.deleted", new ColorUIResource(200, 200, 200), "DiffMerge.inserted", new ColorUIResource(196, 255, 196), "DiffMerge.conflicted", new ColorUIResource(255, 153, 153) };
/*     */ 
/* 484 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 488 */     table.put("Spinner.font", UIDefaultsLookup.get("TextField.font"));
/* 489 */     table.put("Spinner.margin", UIDefaultsLookup.get("TextField.margin"));
/* 490 */     table.put("FormattedTextField.font", UIDefaultsLookup.get("TextField.font"));
/*     */ 
/* 492 */     UIDefaultsLookup.put(table, "Theme.painter", MetalPainter.getInstance());
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.vsnet.VsnetMetalUtils
 * JD-Core Version:    0.6.0
 */
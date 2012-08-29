/*     */ package com.jidesoft.plaf.eclipse;
/*     */ 
/*     */ import com.jidesoft.icons.IconsFactory;
/*     */ import com.jidesoft.icons.JideIconsFactory;
/*     */ import com.jidesoft.icons.MenuCheckIcon;
/*     */ import com.jidesoft.plaf.ExtWindowsDesktopProperty;
/*     */ import com.jidesoft.plaf.LookAndFeelFactory;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.plaf.WindowsDesktopProperty;
/*     */ import com.jidesoft.plaf.basic.Painter;
/*     */ import com.jidesoft.plaf.basic.ThemePainter;
/*     */ import com.jidesoft.plaf.vsnet.ConvertListener;
/*     */ import com.jidesoft.plaf.vsnet.HeaderCellBorder;
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
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.BorderUIResource;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.DimensionUIResource;
/*     */ import javax.swing.plaf.InsetsUIResource;
/*     */ import javax.swing.plaf.basic.BasicBorders.MarginBorder;
/*     */ 
/*     */ public class EclipseWindowsUtils extends EclipseLookAndFeelExtension
/*     */ {
/*     */   public static void initClassDefaultsWithMenu(UIDefaults table)
/*     */   {
/*  44 */     EclipseLookAndFeelExtension.initClassDefaultsWithMenu(table);
/*  45 */     initClassDefaults(table);
/*     */   }
/*     */ 
/*     */   public static void initClassDefaults(UIDefaults table)
/*     */   {
/*  54 */     EclipseLookAndFeelExtension.initClassDefaults(table);
/*     */ 
/*  56 */     String windowsPackageName = "com.jidesoft.plaf.windows.";
/*     */ 
/*  58 */     int products = LookAndFeelFactory.getProductsUsed();
/*     */ 
/*  60 */     table.put("JidePopupMenuUI", "com.jidesoft.plaf.windows.WindowsJidePopupMenuUI");
/*  61 */     table.put("RangeSliderUI", "com.jidesoft.plaf.windows.WindowsRangeSliderUI");
/*     */ 
/*  63 */     if ((products & 0x4) != 0) {
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
/*  85 */     WindowsDesktopProperty activeTitleBarGradientColor = new WindowsDesktopProperty("win.frame.activeCaptionGradientColor", UIDefaultsLookup.get("activeCaption"), toolkit);
/*  86 */     WindowsDesktopProperty inactiveTitleTextColor = new WindowsDesktopProperty("win.frame.inactiveCaptionTextColor", UIDefaultsLookup.get("controlText"), toolkit);
/*  87 */     WindowsDesktopProperty inactiveTitleBackgroundColor = new WindowsDesktopProperty("win.3d.shadowColor", UIDefaultsLookup.get("controlShadow"), toolkit);
/*  88 */     WindowsDesktopProperty mdiBackgroundColor = new WindowsDesktopProperty("win.mdi.backgroundColor", UIDefaultsLookup.get("controlShadow"), toolkit);
/*     */ 
/*  90 */     WindowsDesktopProperty highContrast = new WindowsDesktopProperty("win.highContrast.on", UIDefaultsLookup.get("highContrast"), toolkit);
/*     */ 
/*  92 */     Object controlFont = JideSwingUtilities.getControlFont(toolkit, table);
/*  93 */     Object toolbarFont = JideSwingUtilities.getMenuFont(toolkit, table);
/*  94 */     Object boldFont = JideSwingUtilities.getBoldFont(toolkit, table);
/*     */ 
/*  96 */     Border shadowBorder = BorderFactory.createCompoundBorder(new ShadowBorder(null, null, new Color(171, 168, 165), new Color(143, 141, 138), new Insets(0, 0, 2, 2)), BorderFactory.createLineBorder(Color.gray));
/*     */ 
/*  99 */     Border documentBorder = shadowBorder;
/*     */ 
/* 103 */     Object sunkenBorder = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */     {
/*     */       public Object convert(Object[] obj) {
/* 106 */         return new SunkenBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(1, 1, 2, 1));
/*     */       }
/*     */     });
/* 111 */     Object focusedButtonColor = new ExtWindowsDesktopProperty(new String[] { "win.item.highlightColor" }, new Object[] { UIDefaultsLookup.get("textHighlight") }, toolkit, new ConvertListener() {
/*     */       public Object convert(Object[] obj) {
/* 113 */         return new ColorUIResource(EclipseUtils.getFocusedButtonColor((Color)obj[0]));
/*     */       }
/*     */     });
/* 117 */     Object selectedAndFocusedButtonColor = new ExtWindowsDesktopProperty(new String[] { "win.item.highlightColor" }, new Object[] { UIDefaultsLookup.get("textHighlight") }, toolkit, new ConvertListener() {
/*     */       public Object convert(Object[] obj) {
/* 119 */         return new ColorUIResource(EclipseUtils.getSelectedAndFocusedButtonColor((Color)obj[0]));
/*     */       }
/*     */     });
/* 123 */     Object selectedButtonColor = new ExtWindowsDesktopProperty(new String[] { "win.item.highlightColor" }, new Object[] { UIDefaultsLookup.get("textHighlight") }, toolkit, new ConvertListener() {
/*     */       public Object convert(Object[] obj) {
/* 125 */         return new ColorUIResource(EclipseUtils.getSelectedButtonColor((Color)obj[0]));
/*     */       }
/*     */     });
/* 129 */     WindowsDesktopProperty selectionBackgroundColor = new WindowsDesktopProperty("win.item.highlightColor", UIDefaultsLookup.get("controlShadow"), toolkit);
/*     */ 
/* 131 */     Painter gripperPainter = new Painter() {
/*     */       public void paint(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 133 */         Object p = UIDefaultsLookup.get("Theme.painter");
/* 134 */         if ((p instanceof ThemePainter)) {
/* 135 */           ((ThemePainter)p).paintGripper(c, g, rect, orientation, state);
/*     */         }
/*     */         else
/* 138 */           EclipsePainter.getInstance().paintGripper(c, g, rect, orientation, state);
/*     */       }
/*     */     };
/* 143 */     Object buttonBorder = new BasicBorders.MarginBorder();
/*     */ 
/* 145 */     Object[] uiDefaults = { "Theme.highContrast", highContrast, "Content.background", defaultBackgroundColor, "JideLabel.font", controlFont, "JideLabel.background", defaultBackgroundColor, "JideLabel.foreground", defaultTextColor, "JideButton.selectedAndFocusedBackground", selectedAndFocusedButtonColor, "JideButton.focusedBackground", focusedButtonColor, "JideButton.selectedBackground", selectedButtonColor, "JideButton.borderColor", selectionBackgroundColor, "JideButton.font", controlFont, "JideButton.background", defaultBackgroundColor, "JideButton.foreground", defaultTextColor, "JideButton.shadow", defaultShadowColor, "JideButton.darkShadow", defaultDarkShadowColor, "JideButton.light", defaultHighlightColor, "JideButton.highlight", defaultLtHighlightColor, "JideButton.border", buttonBorder, "JideButton.margin", new InsetsUIResource(3, 3, 3, 3), "JideButton.textIconGap", Integer.valueOf(4), "JideButton.textShiftOffset", Integer.valueOf(0), "JideButton.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released", "ENTER", "pressed", "released ENTER", "released" }), "JideSplitPane.dividerSize", Integer.valueOf(3), "JideSplitPaneDivider.border", new BorderUIResource(BorderFactory.createEmptyBorder()), "JideSplitPaneDivider.background", defaultBackgroundColor, "JideSplitPaneDivider.gripperPainter", gripperPainter, "JideTabbedPane.defaultTabShape", Integer.valueOf(6), "JideTabbedPane.defaultTabColorTheme", Integer.valueOf(1), "JideTabbedPane.tabRectPadding", Integer.valueOf(2), "JideTabbedPane.closeButtonMarginHorizonal", Integer.valueOf(3), "JideTabbedPane.closeButtonMarginVertical", Integer.valueOf(3), "JideTabbedPane.textMarginVertical", Integer.valueOf(4), "JideTabbedPane.noIconMargin", Integer.valueOf(2), "JideTabbedPane.iconMargin", Integer.valueOf(5), "JideTabbedPane.textPadding", Integer.valueOf(6), "JideTabbedPane.buttonSize", Integer.valueOf(18), "JideTabbedPane.buttonMargin", Integer.valueOf(5), "JideTabbedPane.fitStyleBoundSize", Integer.valueOf(8), "JideTabbedPane.fitStyleFirstTabMargin", Integer.valueOf(0), "JideTabbedPane.fitStyleIconMinWidth", Integer.valueOf(24), "JideTabbedPane.fitStyleTextMinWidth", Integer.valueOf(16), "JideTabbedPane.compressedStyleNoIconRectSize", Integer.valueOf(24), "JideTabbedPane.compressedStyleIconMargin", Integer.valueOf(12), "JideTabbedPane.compressedStyleCloseButtonMarginHorizontal", Integer.valueOf(0), "JideTabbedPane.compressedStyleCloseButtonMarginVertical", Integer.valueOf(0), "JideTabbedPane.fixedStyleRectSize", Integer.valueOf(60), "JideTabbedPane.closeButtonMargin", Integer.valueOf(2), "JideTabbedPane.gripLeftMargin", Integer.valueOf(4), "JideTabbedPane.closeButtonMarginSize", Integer.valueOf(6), "JideTabbedPane.closeButtonLeftMargin", Integer.valueOf(1), "JideTabbedPane.closeButtonRightMargin", Integer.valueOf(1), "JideTabbedPane.defaultTabBorderShadowColor", new ColorUIResource(115, 109, 99), "JideTabbedPane.showFocusIndicator", Boolean.TRUE, "JideTabbedPane.gripperPainter", gripperPainter, "JideTabbedPane.border", new BorderUIResource(shadowBorder), "JideTabbedPane.background", defaultBackgroundColor, "JideTabbedPane.foreground", defaultTextColor, "JideTabbedPane.light", defaultHighlightColor, "JideTabbedPane.highlight", defaultLtHighlightColor, "JideTabbedPane.shadow", defaultShadowColor, "JideTabbedPane.tabInsets", new InsetsUIResource(1, 4, 1, 4), "JideTabbedPane.contentBorderInsets", new InsetsUIResource(1, 0, 0, 0), "JideTabbedPane.ignoreContentBorderInsetsIfNoTabs", Boolean.FALSE, "JideTabbedPane.tabAreaInsets", new InsetsUIResource(0, 0, 0, 0), "JideTabbedPane.tabAreaBackground", defaultBackgroundColor, "JideTabbedPane.tabAreaBackgroundLt", defaultLtHighlightColor, "JideTabbedPane.tabAreaBackgroundDk", defaultBackgroundColor, "JideTabbedPane.tabRunOverlay", Integer.valueOf(2), "JideTabbedPane.font", controlFont, "JideTabbedPane.selectedTabFont", controlFont, "JideTabbedPane.darkShadow", defaultTextColor, "JideTabbedPane.selectedTabTextForeground", activeTitleTextColor, "JideTabbedPane.unselectedTabTextForeground", defaultTextColor, "JideTabbedPane.selectedTabBackground", defaultBackgroundColor, "JideTabbedPane.selectedTabBackgroundLt", new ColorUIResource(230, 139, 44), "JideTabbedPane.selectedTabBackgroundDk", new ColorUIResource(255, 199, 60), "JideTabbedPane.tabListBackground", UIDefaultsLookup.getColor("List.background"), "JideTabbedPane.textIconGap", Integer.valueOf(4), "JideTabbedPane.showIconOnTab", Boolean.FALSE, "JideTabbedPane.showCloseButtonOnTab", Boolean.TRUE, "JideTabbedPane.closeButtonAlignment", Integer.valueOf(11), "JideTabbedPane.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "RIGHT", "navigateRight", "KP_RIGHT", "navigateRight", "LEFT", "navigateLeft", "KP_LEFT", "navigateLeft", "UP", "navigateUp", "KP_UP", "navigateUp", "DOWN", "navigateDown", "KP_DOWN", "navigateDown", "ctrl DOWN", "requestFocusForVisibleComponent", "ctrl KP_DOWN", "requestFocusForVisibleComponent" }), "JideTabbedPane.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "ctrl PAGE_DOWN", "navigatePageDown", "ctrl PAGE_UP", "navigatePageUp", "ctrl UP", "requestFocus", "ctrl KP_UP", "requestFocus" }), "DockableFrame.activeTitleBackground2", activeTitleBarGradientColor, "DockableFrame.activeTitleBackground", activeTitleBackgroundColor, "Gripper.size", Integer.valueOf(8), "Gripper.painter", gripperPainter, "Resizable.resizeBorder", new BorderUIResource(shadowBorder), "ButtonPanel.order", "ACO", "ButtonPanel.oppositeOrder", "H", "ButtonPanel.buttonGap", Integer.valueOf(6), "ButtonPanel.groupGap", Integer.valueOf(6), "ButtonPanel.minButtonWidth", Integer.valueOf(75), "JideSplitButton.font", controlFont, "JideSplitButton.margin", new InsetsUIResource(3, 3, 3, 7), "JideSplitButton.border", buttonBorder, "JideSplitButton.borderPainted", Boolean.FALSE, "JideSplitButton.textIconGap", Integer.valueOf(3), "JideSplitButton.selectionBackground", selectionBackgroundColor, "JideSplitButton.selectionForeground", defaultTextColor, "JideSplitButton.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released", "ENTER", "pressed", "released ENTER", "released", "DOWN", "downPressed", "released DOWN", "downReleased" }), "Cursor.hsplit", JideIconsFactory.getImageIcon("jide/cursor_h_split.gif"), "Cursor.vsplit", JideIconsFactory.getImageIcon("jide/cursor_v_split.gif"), "Cursor.north", JideIconsFactory.getImageIcon("jide/cursor_north.gif"), "Cursor.south", JideIconsFactory.getImageIcon("jide/cursor_south.gif"), "Cursor.east", JideIconsFactory.getImageIcon("jide/cursor_east.gif"), "Cursor.west", JideIconsFactory.getImageIcon("jide/cursor_west.gif"), "Cursor.tab", JideIconsFactory.getImageIcon("jide/cursor_tab.gif"), "Cursor.float", JideIconsFactory.getImageIcon("jide/cursor_float.gif"), "Cursor.vertical", JideIconsFactory.getImageIcon("jide/cursor_vertical.gif"), "Cursor.horizontal", JideIconsFactory.getImageIcon("jide/cursor_horizontal.gif"), "Cursor.delete", JideIconsFactory.getImageIcon("jide/cursor_delete.gif"), "Cursor.drag", JideIconsFactory.getImageIcon("jide/cursor_drag.gif"), "Cursor.dragStop", JideIconsFactory.getImageIcon("jide/cursor_drag_stop.gif"), "Cursor.dragText", JideIconsFactory.getImageIcon("jide/cursor_drag_text.gif"), "Cursor.dragTextStop", JideIconsFactory.getImageIcon("jide/cursor_drag_text_stop.gif"), "Cursor.percentage", JideIconsFactory.getImageIcon("jide/cursor_percentage.gif"), "Cursor.moveEast", JideIconsFactory.getImageIcon("jide/cursor_move_east.gif"), "Cursor.moveWest", JideIconsFactory.getImageIcon("jide/cursor_move_west.gif"), "HeaderBox.background", defaultBackgroundColor, "Icon.floating", Boolean.FALSE, "JideScrollPane.border", new BorderUIResource(BorderFactory.createEmptyBorder()), "MenuBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(2, 2, 2, 2)), "TextArea.font", controlFont };
/*     */ 
/* 321 */     table.putDefaults(uiDefaults);
/*     */ 
/* 323 */     int products = LookAndFeelFactory.getProductsUsed();
/*     */ 
/* 325 */     if ((products & 0x1) != 0) {
/* 326 */       Object slidingEastFrameBorder = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 329 */           return new FrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(0, 4, 0, 0));
/*     */         }
/*     */       });
/* 334 */       Object slidingWestFrameBorder = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 337 */           return new FrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(0, 0, 0, 4));
/*     */         }
/*     */       });
/* 342 */       Object slidingNorthFrameBorder = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 345 */           return new FrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(0, 0, 4, 0));
/*     */         }
/*     */       });
/* 350 */       Object slidingSouthFrameBorder = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 353 */           return new FrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(4, 0, 0, 0));
/*     */         }
/*     */       });
/* 358 */       ImageIcon titleButtonImage = IconsFactory.getImageIcon(Eclipse3xWindowsUtils.class, "icons/title_buttons_eclipse.gif");
/* 359 */       int titleButtonSize = 16;
/*     */ 
/* 361 */       uiDefaults = new Object[] { "Workspace.background", mdiBackgroundColor, "SidePane.margin", new InsetsUIResource(1, 1, 1, 1), "SidePane.iconTextGap", Integer.valueOf(2), "SidePane.textBorderGap", Integer.valueOf(13), "SidePane.itemGap", Integer.valueOf(4), "SidePane.groupGap", Integer.valueOf(3), "SidePane.foreground", defaultDarkShadowColor, "SidePane.background", defaultBackgroundColor, "SidePane.lineColor", defaultShadowColor, "SidePane.buttonBackground", defaultBackgroundColor, "SidePane.font", controlFont, "SidePane.orientation", Integer.valueOf(1), "SidePane.showSelectedTabText", Boolean.FALSE, "SidePane.alwaysShowTabText", Boolean.FALSE, "ContentContainer.background", defaultBackgroundColor, "ContentContainer.vgap", Integer.valueOf(1), "ContentContainer.hgap", Integer.valueOf(1), "MainContainer.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "DockableFrame.defaultIcon", JideIconsFactory.getImageIcon("jide/dockableframe_blank.gif"), "DockableFrame.background", defaultBackgroundColor, "DockableFrame.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "DockableFrame.floatingBorder", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "DockableFrame.slidingEastBorder", slidingEastFrameBorder, "DockableFrame.slidingWestBorder", slidingWestFrameBorder, "DockableFrame.slidingNorthBorder", slidingNorthFrameBorder, "DockableFrame.slidingSouthBorder", slidingSouthFrameBorder, "DockableFrame.activeTitleForeground", activeTitleTextColor, "DockableFrame.inactiveTitleBackground", inactiveTitleBackgroundColor, "DockableFrame.inactiveTitleForeground", defaultTextColor, "DockableFrame.activeTitleBorderColor", activeTitleBackgroundColor, "DockableFrame.inactiveTitleBorderColor", inactiveTitleTextColor, "DockableFrame.titleBorder", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "DockableFrame.font", controlFont, "DockableFrameTitlePane.gripperPainter", gripperPainter, "DockableFrameTitlePane.font", controlFont, "DockableFrameTitlePane.hideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 0, 16, 16), "DockableFrameTitlePane.unfloatIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 16, 16, 16), "DockableFrameTitlePane.floatIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 32, 16, 16), "DockableFrameTitlePane.autohideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 48, 16, 16), "DockableFrameTitlePane.stopAutohideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 64, 16, 16), "DockableFrameTitlePane.hideAutohideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 80, 16, 16), "DockableFrameTitlePane.maximizeIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 96, 16, 16), "DockableFrameTitlePane.restoreIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 112, 16, 16), "DockableFrameTitlePane.titleBarComponent", Boolean.TRUE, "DockableFrameTitlePane.alwaysShowAllButtons", Boolean.FALSE, "DockableFrameTitlePane.buttonsAlignment", Integer.valueOf(11), "DockableFrameTitlePane.titleAlignment", Integer.valueOf(10), "DockableFrameTitlePane.buttonGap", Integer.valueOf(3), "DockableFrameTitlePane.showIcon", Boolean.TRUE, "DockableFrameTitlePane.margin", new InsetsUIResource(0, 6, 0, 0), "Contour.color", new ColorUIResource(136, 136, 136), "Contour.thickness", Integer.valueOf(2), "DockingFramework.changeCursor", Boolean.TRUE };
/*     */ 
/* 424 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 427 */     if ((products & 0x2) != 0) {
/* 428 */       ImageIcon collapsiblePaneImage = IconsFactory.getImageIcon(Eclipse3xWindowsUtils.class, "icons/collapsible_pane_eclipse.gif");
/* 429 */       int collapsiblePaneSize = 11;
/*     */ 
/* 431 */       uiDefaults = new Object[] { "CollapsiblePanes.border", new BorderUIResource(BorderFactory.createEmptyBorder(12, 12, 12, 12)), "CollapsiblePanes.gap", Integer.valueOf(15), "CollapsiblePane.background", defaultBackgroundColor, "CollapsiblePane.contentBackground", defaultLtHighlightColor, "CollapsiblePane.foreground", defaultTextColor, "CollapsiblePane.emphasizedBackground", activeTitleBackgroundColor, "CollapsiblePane.emphasizedForeground", activeTitleTextColor, "CollapsiblePane.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "CollapsiblePane.font", controlFont, "CollapsiblePane.contentBorder", new BorderUIResource(BorderFactory.createEmptyBorder(8, 10, 8, 10)), "CollapsiblePane.titleBorder", new BorderUIResource(BorderFactory.createEmptyBorder(3, 3, 3, 3)), "CollapsiblePane.titleFont", boldFont, "CollapsiblePane.downIcon", IconsFactory.getIcon(null, collapsiblePaneImage, 0, 0, 11, 11), "CollapsiblePane.upIcon", IconsFactory.getIcon(null, collapsiblePaneImage, 0, 11, 11, 11), "StatusBarItem.border", sunkenBorder, "StatusBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(2, 0, 0, 0)), "StatusBar.margin", new Insets(2, 0, 0, 0), "StatusBar.gap", Integer.valueOf(3), "StatusBar.background", defaultBackgroundColor, "StatusBar.font", controlFont, "MemoryStatusBarItem.fillColor", new ColorUIResource(236, 233, 176), "DocumentPane.groupBorder", new BorderUIResource(documentBorder), "DocumentPane.newHorizontalGroupIcon", JideIconsFactory.getImageIcon("jide/windows_new_horizontal_tab_group.png"), "DocumentPane.newVerticalGroupIcon", JideIconsFactory.getImageIcon("jide/windows_new_vertical_tab_group.png"), "DocumentPane.boldActiveTab", Boolean.FALSE, "OutlookTabbedPane.buttonStyle", Integer.valueOf(1), "FloorTabbedPane.buttonStyle", Integer.valueOf(1) };
/*     */ 
/* 467 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 470 */     if ((products & 0x10) != 0) {
/* 471 */       uiDefaults = new Object[] { "CommandBar.font", toolbarFont, "CommandBar.background", defaultBackgroundColor, "CommandBar.foreground", defaultTextColor, "CommandBar.shadow", defaultShadowColor, "CommandBar.darkShadow", defaultDarkShadowColor, "CommandBar.light", defaultHighlightColor, "CommandBar.highlight", defaultLtHighlightColor, "CommandBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(2, 1, 2, 1)), "CommandBar.borderVert", new BorderUIResource(BorderFactory.createEmptyBorder(2, 1, 2, 1)), "CommandBar.borderFloating", new BorderUIResource(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder((Color)activeTitleBackgroundColor.createValue(table), 2), BorderFactory.createEmptyBorder(1, 1, 1, 1))), "CommandBar.separatorSize", Integer.valueOf(3), "CommandBar.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "UP", "navigateUp", "KP_UP", "navigateUp", "DOWN", "navigateDown", "KP_DOWN", "navigateDown", "LEFT", "navigateLeft", "KP_LEFT", "navigateLeft", "RIGHT", "navigateRight", "KP_RIGHT", "navigateRight" }), "CommandBar.titleBarSize", Integer.valueOf(17), "CommandBar.titleBarButtonGap", Integer.valueOf(1), "CommandBar.titleBarBackground", activeTitleBackgroundColor, "CommandBar.titleBarForeground", activeTitleTextColor, "CommandBar.titleBarFont", boldFont, "CommandBar.minimumSize", new DimensionUIResource(20, 20), "CommandBar.separatorSize", new DimensionUIResource(5, 20), "CommandBarSeparator.background", new Color(219, 216, 209), "CommandBarSeparator.foreground", new Color(166, 166, 166), "Chevron.size", Integer.valueOf(11), "Chevron.alwaysVisible", Boolean.FALSE };
/*     */ 
/* 511 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 514 */     if ((products & 0x4) != 0) {
/* 515 */       uiDefaults = new Object[] { "AbstractComboBox.useJButton", Boolean.FALSE, "NestedTableHeader.cellBorder", new HeaderCellBorder(), "GroupList.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "TAB", "selectNextGroup", "shift TAB", "selectPreviousGroup" }) };
/*     */ 
/* 524 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 527 */     if ((products & 0x4000) != 0) {
/* 528 */       uiDefaults = new Object[] { "DiffMerge.changed", new ColorUIResource(196, 196, 255), "DiffMerge.deleted", new ColorUIResource(200, 200, 200), "DiffMerge.inserted", new ColorUIResource(196, 255, 196), "DiffMerge.conflicted", new ColorUIResource(255, 153, 153) };
/*     */ 
/* 534 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 537 */     if (!JideSwingUtilities.shouldUseSystemFont()) {
/* 538 */       Object[] uiDefaultsFont = { "TabbedPane.font", controlFont, "TitledBorder.font", controlFont, "TableHeader.font", controlFont, "Table.font", controlFont, "List.font", controlFont, "Tree.font", controlFont, "ToolTip.font", controlFont, "CheckBox.font", controlFont, "RadioButton.font", controlFont, "Label.font", controlFont, "Panel.font", controlFont, "TextField.font", controlFont, "ComboBox.font", controlFont, "Button.font", controlFont };
/*     */ 
/* 554 */       table.putDefaults(uiDefaultsFont);
/*     */     }
/* 556 */     UIDefaultsLookup.put(table, "Theme.painter", EclipsePainter.getInstance());
/*     */   }
/*     */ 
/*     */   public static void initComponentDefaultsWithMenu(UIDefaults table)
/*     */   {
/* 565 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*     */ 
/* 567 */     initComponentDefaults(table);
/*     */ 
/* 569 */     if (!Beans.isDesignTime()) {
/* 570 */       WindowsDesktopProperty defaultLightColor = new WindowsDesktopProperty("win.3d.lightColor", UIDefaultsLookup.get("controlHighlight"), toolkit);
/* 571 */       WindowsDesktopProperty defaultHighlightColor = new WindowsDesktopProperty("win.3d.highlightColor", UIDefaultsLookup.get("controlLtHighlight"), toolkit);
/* 572 */       WindowsDesktopProperty selectionTextColor = new WindowsDesktopProperty("win.item.highlightTextColor", UIDefaultsLookup.get("textHighlightText"), toolkit);
/* 573 */       WindowsDesktopProperty selectionBackgroundColor = new WindowsDesktopProperty("win.item.highlightColor", UIDefaultsLookup.get("controlShadow"), toolkit);
/*     */ 
/* 575 */       WindowsDesktopProperty defaultShadowColor = new WindowsDesktopProperty("win.3d.shadowColor", UIDefaultsLookup.get("controlShadow"), toolkit);
/*     */ 
/* 577 */       Object menuBorder = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 580 */           return new RaisedBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(2, 2, 2, 2));
/*     */         }
/*     */       });
/* 585 */       Object menuFont = JideSwingUtilities.getMenuFont(toolkit, table);
/*     */ 
/* 587 */       Object[] uiDefaults = { "PopupMenuSeparator.foreground", defaultHighlightColor, "PopupMenuSeparator.background", defaultShadowColor, "CheckBoxMenuItem.checkIcon", new MenuCheckIcon(JideIconsFactory.getImageIcon("jide/menu_checkbox_eclipse.gif")), "CheckBoxMenuItem.selectionBackground", selectionBackgroundColor, "CheckBoxMenuItem.selectionForeground", selectionTextColor, "CheckBoxMenuItem.acceleratorSelectionForeground", selectionTextColor, "CheckBoxMenuItem.mouseHoverBackground", defaultHighlightColor, "CheckBoxMenuItem.mouseHoverBorder", new BorderUIResource(BorderFactory.createLineBorder(new Color(10, 36, 106))), "CheckBoxMenuItem.margin", new InsetsUIResource(2, 2, 2, 2), "CheckBoxMenuItem.font", menuFont, "CheckBoxMenuItem.acceleratorFont", menuFont, "RadioButtonMenuItem.checkIcon", new MenuCheckIcon(JideIconsFactory.getImageIcon("jide/menu_radiobutton_eclipse.gif")), "RadioButtonMenuItem.selectionBackground", selectionBackgroundColor, "RadioButtonMenuItem.selectionForeground", selectionTextColor, "RadioButtonMenuItem.acceleratorSelectionForeground", selectionTextColor, "RadioButtonMenuItem.mouseHoverBackground", defaultHighlightColor, "RadioButtonMenuItem.mouseHoverBorder", new BorderUIResource(BorderFactory.createLineBorder(new Color(10, 36, 106))), "RadioButtonMenuItem.margin", new InsetsUIResource(2, 2, 2, 2), "RadioButtonMenuItem.font", menuFont, "RadioButtonMenuItem.acceleratorFont", menuFont, "MenuBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(2, 2, 4, 2)), "Menu.selectionBackground", selectionBackgroundColor, "Menu.selectionForeground", selectionTextColor, "Menu.mouseHoverBackground", selectionBackgroundColor, "Menu.mouseHoverBorder", new BorderUIResource(BorderFactory.createEmptyBorder()), "Menu.mouseSelectedBorder", new BorderUIResource(BorderFactory.createEmptyBorder()), "Menu.margin", new InsetsUIResource(4, 6, 2, 6), "Menu.textIconGap", Integer.valueOf(4), "Menu.checkIcon", new MenuCheckIcon(JideIconsFactory.getImageIcon("jide/menu_checkbox_vsnet.gif")), "Menu.font", menuFont, "Menu.acceleratorFont", menuFont, "PopupMenu.border", menuBorder, "MenuItem.checkIcon", new MenuCheckIcon(JideIconsFactory.getImageIcon("jide/menu_checkbox_vsnet.gif")), "MenuItem.selectionBackground", selectionBackgroundColor, "MenuItem.selectionForeground", selectionTextColor, "MenuItem.acceleratorSelectionForeground", selectionTextColor, "MenuItem.selectionBorderColor", selectionBackgroundColor, "MenuItem.shadowWidth", Integer.valueOf(24), "MenuItem.shadowColor", defaultLightColor, "MenuItem.textIconGap", Integer.valueOf(4), "MenuItem.accelEndGap", Integer.valueOf(18), "MenuItem.margin", new InsetsUIResource(2, 2, 2, 2), "MenuItem.font", menuFont, "MenuItem.acceleratorFont", menuFont };
/*     */ 
/* 640 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.eclipse.EclipseWindowsUtils
 * JD-Core Version:    0.6.0
 */
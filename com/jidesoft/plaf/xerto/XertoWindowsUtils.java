/*     */ package com.jidesoft.plaf.xerto;
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
/*     */ import com.jidesoft.plaf.office2003.Office2003WindowsUtils;
/*     */ import com.jidesoft.plaf.vsnet.ConvertListener;
/*     */ import com.jidesoft.plaf.vsnet.HeaderCellBorder;
/*     */ import com.jidesoft.plaf.vsnet.ResizeFrameBorder;
/*     */ import com.jidesoft.plaf.vsnet.VsnetLookAndFeelExtension;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import com.jidesoft.utils.SecurityUtils;
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
/*     */ import javax.swing.plaf.BorderUIResource;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.InsetsUIResource;
/*     */ import javax.swing.plaf.basic.BasicBorders.MarginBorder;
/*     */ 
/*     */ public class XertoWindowsUtils extends Office2003WindowsUtils
/*     */ {
/*     */   public static void initClassDefaultsWithMenu(UIDefaults table)
/*     */   {
/*  45 */     VsnetLookAndFeelExtension.initClassDefaultsWithMenu(table);
/*  46 */     initClassDefaults(table);
/*     */   }
/*     */ 
/*     */   public static void initClassDefaults(UIDefaults table)
/*     */   {
/*  55 */     Office2003WindowsUtils.initClassDefaults(table, false);
/*  56 */     initClassDefaultsForXerto(table);
/*     */   }
/*     */ 
/*     */   private static void initClassDefaultsForXerto(UIDefaults table) {
/*  60 */     int products = LookAndFeelFactory.getProductsUsed();
/*     */ 
/*  62 */     String xertoPackageName = "com.jidesoft.plaf.xerto.";
/*     */ 
/*  64 */     if ((products & 0x2) != 0) {
/*  65 */       table.put("CollapsiblePaneUI", "com.jidesoft.plaf.xerto.XertoCollapsiblePaneUI");
/*     */     }
/*     */ 
/*  68 */     if ((products & 0x1) != 0) {
/*  69 */       table.put("SidePaneUI", "com.jidesoft.plaf.xerto.XertoSidePaneUI");
/*  70 */       table.put("DockableFrameUI", "com.jidesoft.plaf.xerto.XertoDockableFrameUI");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void initComponentDefaultsWithMenu(UIDefaults table)
/*     */   {
/*  81 */     System.setProperty("shadingtheme", "true");
/*     */ 
/*  83 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*     */ 
/*  85 */     WindowsDesktopProperty defaultHighlightColor = new WindowsDesktopProperty("win.3d.lightColor", UIDefaultsLookup.get("controlHighlight"), toolkit);
/*  86 */     WindowsDesktopProperty selectionBackgroundColor = new WindowsDesktopProperty("win.item.highlightColor", UIDefaultsLookup.get("controlShadow"), toolkit);
/*  87 */     WindowsDesktopProperty menuTextColor = new WindowsDesktopProperty("win.menu.textColor", UIDefaultsLookup.get("control"), toolkit);
/*     */ 
/*  89 */     Object menuFont = JideSwingUtilities.getMenuFont(toolkit, table);
/*     */ 
/*  91 */     Object menuSelectionBackground = new ExtWindowsDesktopProperty(new String[] { "win.item.highlightColor" }, new Object[] { UIDefaultsLookup.get("controlShadow") }, toolkit, new ConvertListener()
/*     */     {
/*     */       public Object convert(Object[] obj) {
/*  94 */         return new ColorUIResource(XertoUtils.getMenuSelectionColor((Color)obj[0]));
/*     */       }
/*     */     });
/*  98 */     Object menuBackground = new ExtWindowsDesktopProperty(new String[] { "win.3d.backgroundColor" }, new Object[] { UIDefaultsLookup.get("control") }, toolkit, new ConvertListener()
/*     */     {
/*     */       public Object convert(Object[] obj) {
/* 101 */         return new ColorUIResource(XertoUtils.getMenuBackgroundColor((Color)obj[0]));
/*     */       }
/*     */     });
/* 105 */     Object separatorColor = new ExtWindowsDesktopProperty(new String[] { "win.3d.shadowColor" }, new Object[] { UIDefaultsLookup.get("controlShadow") }, toolkit, new ConvertListener()
/*     */     {
/*     */       public Object convert(Object[] obj) {
/* 108 */         return new ColorUIResource(((Color)obj[0]).brighter());
/*     */       }
/*     */     });
/* 112 */     Object[] uiDefaults = { "PopupMenuSeparator.foreground", separatorColor, "PopupMenuSeparator.background", menuBackground, "CheckBoxMenuItem.checkIcon", new MenuCheckIcon(JideIconsFactory.getImageIcon("jide/menu_checkbox_vsnet.gif")), "CheckBoxMenuItem.selectionBackground", menuSelectionBackground, "CheckBoxMenuItem.selectionForeground", menuTextColor, "CheckBoxMenuItem.acceleratorSelectionForeground", menuTextColor, "CheckBoxMenuItem.mouseHoverBackground", menuSelectionBackground, "CheckBoxMenuItem.mouseHoverBorder", new BorderUIResource(BorderFactory.createLineBorder(new Color(10, 36, 106))), "CheckBoxMenuItem.margin", new InsetsUIResource(3, 0, 3, 0), "CheckBoxMenuItem.font", menuFont, "CheckBoxMenuItem.acceleratorFont", menuFont, "CheckBoxMenuItem.textIconGap", Integer.valueOf(8), "RadioButtonMenuItem.checkIcon", new MenuCheckIcon(JideIconsFactory.getImageIcon("jide/menu_checkbox_vsnet.gif")), "RadioButtonMenuItem.selectionBackground", menuSelectionBackground, "RadioButtonMenuItem.selectionForeground", menuTextColor, "RadioButtonMenuItem.acceleratorSelectionForeground", menuTextColor, "RadioButtonMenuItem.mouseHoverBackground", menuSelectionBackground, "RadioButtonMenuItem.mouseHoverBorder", new BorderUIResource(BorderFactory.createLineBorder(new Color(10, 36, 106))), "RadioButtonMenuItem.margin", new InsetsUIResource(3, 0, 3, 0), "RadioButtonMenuItem.font", menuFont, "RadioButtonMenuItem.acceleratorFont", menuFont, "RadioButtonMenuItem.textIconGap", Integer.valueOf(8), "MenuBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(2, 2, 2, 2)), "Menu.selectionBackground", menuSelectionBackground, "Menu.selectionForeground", menuTextColor, "Menu.mouseHoverBackground", menuSelectionBackground, "Menu.mouseHoverBorder", new BorderUIResource(BorderFactory.createLineBorder(new Color(10, 36, 106))), "Menu.margin", new InsetsUIResource(2, 7, 1, 7), "Menu.checkIcon", new MenuCheckIcon(JideIconsFactory.getImageIcon("jide/menu_checkbox_vsnet.gif")), "Menu.textIconGap", Integer.valueOf(2), "Menu.font", menuFont, "Menu.acceleratorFont", menuFont, "Menu.submenuPopupOffsetX", Integer.valueOf(0), "Menu.submenuPopupOffsetY", Integer.valueOf(0), "PopupMenu.border", new BorderUIResource(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(102, 102, 102)), BorderFactory.createEmptyBorder(1, 1, 1, 1))), "MenuItem.checkIcon", new MenuCheckIcon(JideIconsFactory.getImageIcon("jide/menu_checkbox_vsnet.gif")), "MenuItem.selectionBackground", menuSelectionBackground, "MenuItem.selectionForeground", menuTextColor, "MenuItem.acceleratorSelectionForeground", menuTextColor, "MenuItem.background", menuBackground, "MenuItem.selectionBorderColor", selectionBackgroundColor, "MenuItem.shadowWidth", Integer.valueOf(24), "MenuItem.shadowColor", defaultHighlightColor, "MenuItem.textIconGap", Integer.valueOf(8), "MenuItem.accelEndGap", Integer.valueOf(18), "MenuItem.margin", new InsetsUIResource(4, 0, 3, 0), "MenuItem.font", menuFont, "MenuItem.acceleratorFont", menuFont };
/*     */ 
/* 171 */     table.putDefaults(uiDefaults);
/* 172 */     initComponentDefaults(table);
/*     */ 
/* 174 */     UIDefaultsLookup.put(table, "Theme.painter", XertoPainter.getInstance());
/*     */ 
/* 177 */     Object popupMenuBorder = new ExtWindowsDesktopProperty(new String[] { "null" }, new Object[] { ((ThemePainter)UIDefaultsLookup.get("Theme.painter")).getMenuItemBorderColor() }, toolkit, new ConvertListener() {
/*     */       public Object convert(Object[] obj) {
/* 179 */         return new BorderUIResource(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder((Color)obj[0]), BorderFactory.createEmptyBorder(1, 1, 1, 1)));
/*     */       }
/*     */     });
/* 182 */     table.put("PopupMenu.border", popupMenuBorder);
/*     */   }
/*     */ 
/*     */   public static void initComponentDefaults(UIDefaults table)
/*     */   {
/* 192 */     System.setProperty("shadingtheme", "true");
/*     */ 
/* 194 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*     */ 
/* 196 */     WindowsDesktopProperty defaultHighlightColor = new WindowsDesktopProperty("win.3d.lightColor", UIDefaultsLookup.get("controlHighlight"), toolkit);
/* 197 */     WindowsDesktopProperty defaultLtHighlightColor = new WindowsDesktopProperty("win.3d.highlightColor", UIDefaultsLookup.get("controlLtHighlight"), toolkit);
/* 198 */     WindowsDesktopProperty selectionBackgroundColor = new WindowsDesktopProperty("win.item.highlightColor", UIDefaultsLookup.get("controlShadow"), toolkit);
/* 199 */     WindowsDesktopProperty mdiBackgroundColor = new WindowsDesktopProperty("win.mdi.backgroundColor", UIDefaultsLookup.get("controlShadow"), toolkit);
/* 200 */     WindowsDesktopProperty menuTextColor = new WindowsDesktopProperty("win.menu.textColor", UIDefaultsLookup.get("controlText"), toolkit);
/* 201 */     WindowsDesktopProperty defaultTextColor = new WindowsDesktopProperty("win.button.textColor", UIDefaultsLookup.get("controlText"), toolkit);
/* 202 */     WindowsDesktopProperty defaultBackgroundColor = new WindowsDesktopProperty("win.3d.backgroundColor", UIDefaultsLookup.get("control"), toolkit);
/* 203 */     WindowsDesktopProperty defaultShadowColor = new WindowsDesktopProperty("win.3d.shadowColor", UIDefaultsLookup.get("controlShadow"), toolkit);
/* 204 */     WindowsDesktopProperty defaultDarkShadowColor = new WindowsDesktopProperty("win.3d.darkShadowColor", UIDefaultsLookup.get("controlDkShadow"), toolkit);
/* 205 */     WindowsDesktopProperty activeTitleBackgroundColor = new WindowsDesktopProperty("win.frame.activeCaptionColor", UIDefaultsLookup.get("activeCaption"), toolkit);
/* 206 */     WindowsDesktopProperty activeTitleTextColor = new WindowsDesktopProperty("win.frame.captionTextColor", UIDefaultsLookup.get("activeCaptionText"), toolkit);
/*     */ 
/* 208 */     WindowsDesktopProperty highContrast = new WindowsDesktopProperty("win.highContrast.on", UIDefaultsLookup.get("highContrast"), toolkit);
/*     */ 
/* 210 */     Object singleLineBorder = new ExtWindowsDesktopProperty(new String[] { "win.3d.shadowColor" }, new Object[] { UIDefaultsLookup.get("controlShadow") }, toolkit, new ConvertListener() {
/*     */       public Object convert(Object[] obj) {
/* 212 */         return new BorderUIResource(BorderFactory.createLineBorder((Color)obj[0]));
/*     */       }
/*     */     });
/* 216 */     Object controlFont = JideSwingUtilities.getControlFont(toolkit, table);
/* 217 */     Object toolbarFont = JideSwingUtilities.getMenuFont(toolkit, table);
/* 218 */     Object boldFont = JideSwingUtilities.getBoldFont(toolkit, table);
/*     */ 
/* 220 */     Object resizeBorder = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */     {
/*     */       public Object convert(Object[] obj) {
/* 223 */         return new XertoFrameBorder(new Insets(4, 4, 4, 4));
/*     */       }
/*     */     });
/* 228 */     Object defaultFormBackground = new ExtWindowsDesktopProperty(new String[] { "win.3d.backgroundColor" }, new Object[] { UIDefaultsLookup.get("control") }, toolkit, new ConvertListener() {
/*     */       public Object convert(Object[] obj) {
/* 230 */         return new ColorUIResource(XertoUtils.getDefaultBackgroundColor((Color)obj[0]));
/*     */       }
/*     */     });
/* 234 */     Object inactiveTabForground = new ExtWindowsDesktopProperty(new String[] { "win.3d.shadowColor" }, new Object[] { UIDefaultsLookup.get("controlShadow") }, toolkit, new ConvertListener()
/*     */     {
/*     */       public Object convert(Object[] obj) {
/* 237 */         return new ColorUIResource(((Color)obj[0]).darker());
/*     */       }
/*     */     });
/* 241 */     Object focusedButtonColor = new ExtWindowsDesktopProperty(new String[] { "win.item.highlightColor" }, new Object[] { UIDefaultsLookup.get("textHighlight") }, toolkit, new ConvertListener() {
/*     */       public Object convert(Object[] obj) {
/* 243 */         return new ColorUIResource(XertoUtils.getFocusedButtonColor((Color)obj[0]));
/*     */       }
/*     */     });
/* 247 */     Object selectedAndFocusedButtonColor = new ExtWindowsDesktopProperty(new String[] { "win.item.highlightColor" }, new Object[] { UIDefaultsLookup.get("textHighlight") }, toolkit, new ConvertListener() {
/*     */       public Object convert(Object[] obj) {
/* 249 */         return new ColorUIResource(XertoUtils.getSelectedAndFocusedButtonColor((Color)obj[0]));
/*     */       }
/*     */     });
/* 253 */     Object selectedButtonColor = new ExtWindowsDesktopProperty(new String[] { "win.item.highlightColor" }, new Object[] { UIDefaultsLookup.get("textHighlight") }, toolkit, new ConvertListener() {
/*     */       public Object convert(Object[] obj) {
/* 255 */         return new ColorUIResource(XertoUtils.getSelectedButtonColor((Color)obj[0]));
/*     */       }
/*     */     });
/* 260 */     Object gripperForeground = new ExtWindowsDesktopProperty(new String[] { "win.3d.backgroundColor" }, new Object[] { UIDefaultsLookup.get("control") }, toolkit, new ConvertListener() {
/*     */       public Object convert(Object[] obj) {
/* 262 */         return new ColorUIResource(XertoUtils.getGripperForegroundColor((Color)obj[0]));
/*     */       }
/*     */     });
/* 266 */     Object commandBarBackground = new ExtWindowsDesktopProperty(new String[] { "win.3d.backgroundColor" }, new Object[] { UIDefaultsLookup.get("control") }, toolkit, new ConvertListener() {
/*     */       public Object convert(Object[] obj) {
/* 268 */         return new ColorUIResource(XertoUtils.getToolBarBackgroundColor((Color)obj[0]));
/*     */       }
/*     */     });
/* 272 */     Painter gripperPainter = new Painter() {
/*     */       public void paint(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 274 */         Object p = UIDefaultsLookup.get("Theme.painter");
/* 275 */         if ((p instanceof ThemePainter)) {
/* 276 */           ((ThemePainter)p).paintGripper(c, g, rect, orientation, state);
/*     */         }
/*     */         else
/* 279 */           XertoPainter.getInstance().paintGripper(c, g, rect, orientation, state);
/*     */       }
/*     */     };
/* 284 */     Object buttonBorder = new BasicBorders.MarginBorder();
/*     */ 
/* 286 */     Object[] uiDefaults = { "Theme.highContrast", highContrast, "Content.background", defaultBackgroundColor, "JideScrollPane.border", singleLineBorder, "JideButton.selectedAndFocusedBackground", selectedAndFocusedButtonColor, "JideButton.focusedBackground", focusedButtonColor, "JideButton.selectedBackground", selectedButtonColor, "JideButton.borderColor", selectionBackgroundColor, "JideButton.font", controlFont, "JideButton.background", defaultBackgroundColor, "JideButton.foreground", defaultTextColor, "JideButton.shadow", defaultShadowColor, "JideButton.darkShadow", defaultDarkShadowColor, "JideButton.light", defaultHighlightColor, "JideButton.highlight", defaultLtHighlightColor, "JideButton.border", buttonBorder, "JideButton.margin", new InsetsUIResource(3, 3, 3, 3), "JideButton.textIconGap", Integer.valueOf(2), "JideButton.textShiftOffset", Integer.valueOf(0), "JideButton.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released", "ENTER", "pressed", "released ENTER", "released" }), "JideSplitPane.dividerSize", Integer.valueOf(3), "JideSplitPaneDivider.border", new BorderUIResource(BorderFactory.createEmptyBorder()), "JideSplitPaneDivider.background", defaultBackgroundColor, "JideSplitPaneDivider.gripperPainter", gripperPainter, "JideTabbedPane.defaultTabShape", Integer.valueOf(9), "JideTabbedPane.defaultResizeMode", Integer.valueOf(1), "JideTabbedPane.defaultTabColorTheme", Integer.valueOf(2), "JideTabbedPane.tabRectPadding", Integer.valueOf(2), "JideTabbedPane.closeButtonMarginHorizonal", Integer.valueOf(3), "JideTabbedPane.closeButtonMarginVertical", Integer.valueOf(3), "JideTabbedPane.textMarginVertical", Integer.valueOf(4), "JideTabbedPane.noIconMargin", Integer.valueOf(2), "JideTabbedPane.iconMargin", Integer.valueOf(5), "JideTabbedPane.textPadding", Integer.valueOf(6), "JideTabbedPane.buttonSize", Integer.valueOf(18), "JideTabbedPane.buttonMargin", Integer.valueOf(5), "JideTabbedPane.fitStyleBoundSize", Integer.valueOf(8), "JideTabbedPane.fitStyleFirstTabMargin", Integer.valueOf(4), "JideTabbedPane.fitStyleIconMinWidth", Integer.valueOf(24), "JideTabbedPane.fitStyleTextMinWidth", Integer.valueOf(16), "JideTabbedPane.compressedStyleNoIconRectSize", Integer.valueOf(24), "JideTabbedPane.compressedStyleIconMargin", Integer.valueOf(12), "JideTabbedPane.compressedStyleCloseButtonMarginHorizontal", Integer.valueOf(0), "JideTabbedPane.compressedStyleCloseButtonMarginVertical", Integer.valueOf(0), "JideTabbedPane.fixedStyleRectSize", Integer.valueOf(60), "JideTabbedPane.closeButtonMargin", Integer.valueOf(2), "JideTabbedPane.gripLeftMargin", Integer.valueOf(4), "JideTabbedPane.closeButtonMarginSize", Integer.valueOf(6), "JideTabbedPane.closeButtonLeftMargin", Integer.valueOf(1), "JideTabbedPane.closeButtonRightMargin", Integer.valueOf(1), "JideTabbedPane.defaultTabBorderShadowColor", new ColorUIResource(115, 109, 99), "JideTabbedPane.gripperPainter", gripperPainter, "JideTabbedPane.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "JideTabbedPane.background", new ColorUIResource(XertoUtils.getControlColor()), "JideTabbedPane.foreground", new ColorUIResource(XertoUtils.getTabForgroundColor()), "JideTabbedPane.light", defaultHighlightColor, "JideTabbedPane.highlight", defaultLtHighlightColor, "JideTabbedPane.shadow", defaultShadowColor, "JideTabbedPane.darkShadow", new ColorUIResource(Color.GRAY), "JideTabbedPane.tabInsets", new InsetsUIResource(1, 4, 1, 4), "JideTabbedPane.contentBorderInsets", new InsetsUIResource(1, 1, 1, 1), "JideTabbedPane.ignoreContentBorderInsetsIfNoTabs", Boolean.FALSE, "JideTabbedPane.tabAreaInsets", new InsetsUIResource(2, 4, 0, 4), "JideTabbedPane.tabAreaBackground", new ColorUIResource(XertoUtils.getApplicationFrameBackgroundColor()), "JideTabbedPane.tabAreaBackgroundLt", defaultLtHighlightColor, "JideTabbedPane.tabAreaBackgroundDk", defaultBackgroundColor, "JideTabbedPane.tabRunOverlay", Integer.valueOf(2), "JideTabbedPane.font", controlFont, "JideTabbedPane.selectedTabFont", controlFont, "JideTabbedPane.selectedTabTextForeground", new ColorUIResource(XertoUtils.getTabForgroundColor()), "JideTabbedPane.unselectedTabTextForeground", inactiveTabForground, "JideTabbedPane.selectedTabBackground", new ColorUIResource(XertoUtils.getSelectedTabBackgroundColor()), "JideTabbedPane.selectedTabBackgroundLt", new ColorUIResource(230, 139, 44), "JideTabbedPane.selectedTabBackgroundDk", new ColorUIResource(255, 199, 60), "JideTabbedPane.tabListBackground", UIDefaultsLookup.getColor("List.background"), "JideTabbedPane.textIconGap", Integer.valueOf(4), "JideTabbedPane.showIconOnTab", Boolean.TRUE, "JideTabbedPane.showCloseButtonOnTab", Boolean.FALSE, "JideTabbedPane.closeButtonAlignment", Integer.valueOf(11), "JideTabbedPane.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "RIGHT", "navigateRight", "KP_RIGHT", "navigateRight", "LEFT", "navigateLeft", "KP_LEFT", "navigateLeft", "UP", "navigateUp", "KP_UP", "navigateUp", "DOWN", "navigateDown", "KP_DOWN", "navigateDown", "ctrl DOWN", "requestFocusForVisibleComponent", "ctrl KP_DOWN", "requestFocusForVisibleComponent" }), "JideTabbedPane.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "ctrl PAGE_DOWN", "navigatePageDown", "ctrl PAGE_UP", "navigatePageUp", "ctrl UP", "requestFocus", "ctrl KP_UP", "requestFocus" }), "ButtonPanel.order", "ACO", "ButtonPanel.oppositeOrder", "H", "ButtonPanel.buttonGap", Integer.valueOf(6), "ButtonPanel.groupGap", Integer.valueOf(6), "ButtonPanel.minButtonWidth", Integer.valueOf(75), "JideSplitButton.font", controlFont, "JideSplitButton.margin", new InsetsUIResource(3, 3, 3, 7), "JideSplitButton.border", buttonBorder, "JideSplitButton.borderPainted", Boolean.FALSE, "JideSplitButton.textIconGap", Integer.valueOf(3), "JideSplitButton.selectionForeground", menuTextColor, "JideSplitButton.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released", "ENTER", "pressed", "released ENTER", "released", "DOWN", "downPressed", "released DOWN", "downReleased" }), "Cursor.hsplit", JideIconsFactory.getImageIcon("jide/cursor_h_split.gif"), "Cursor.vsplit", JideIconsFactory.getImageIcon("jide/cursor_v_split.gif"), "Cursor.north", JideIconsFactory.getImageIcon("jide/cursor_north.gif"), "Cursor.south", JideIconsFactory.getImageIcon("jide/cursor_south.gif"), "Cursor.east", JideIconsFactory.getImageIcon("jide/cursor_east.gif"), "Cursor.west", JideIconsFactory.getImageIcon("jide/cursor_west.gif"), "Cursor.tab", JideIconsFactory.getImageIcon("jide/cursor_tab.gif"), "Cursor.float", JideIconsFactory.getImageIcon("jide/cursor_float.gif"), "Cursor.vertical", JideIconsFactory.getImageIcon("jide/cursor_vertical.gif"), "Cursor.horizontal", JideIconsFactory.getImageIcon("jide/cursor_horizontal.gif"), "Cursor.delete", JideIconsFactory.getImageIcon("jide/cursor_delete.gif"), "Cursor.drag", JideIconsFactory.getImageIcon("jide/cursor_drag.gif"), "Cursor.dragStop", JideIconsFactory.getImageIcon("jide/cursor_drag_stop.gif"), "Cursor.dragText", JideIconsFactory.getImageIcon("jide/cursor_drag_text.gif"), "Cursor.dragTextStop", JideIconsFactory.getImageIcon("jide/cursor_drag_text_stop.gif"), "Cursor.percentage", JideIconsFactory.getImageIcon("jide/cursor_percentage.gif"), "Cursor.moveEast", JideIconsFactory.getImageIcon("jide/cursor_move_east.gif"), "Cursor.moveWest", JideIconsFactory.getImageIcon("jide/cursor_move_west.gif"), "Gripper.size", Integer.valueOf(8), "Gripper.foreground", gripperForeground, "Gripper.painter", gripperPainter, "HeaderBox.background", defaultBackgroundColor, "Icon.floating", Boolean.FALSE, "Resizable.resizeBorder", resizeBorder, "TextArea.font", controlFont };
/*     */ 
/* 452 */     table.putDefaults(uiDefaults);
/*     */ 
/* 454 */     int products = LookAndFeelFactory.getProductsUsed();
/*     */ 
/* 456 */     if ((products & 0x1) != 0) {
/* 457 */       ImageIcon titleButtonImage = IconsFactory.getImageIcon(XertoWindowsUtils.class, "icons/title_buttons_xerto.gif");
/* 458 */       int titleButtonSize = 10;
/*     */ 
/* 460 */       FrameBorder frameBorder = new FrameBorder();
/*     */ 
/* 462 */       boolean useShadowBorder = "true".equals(SecurityUtils.getProperty("jide.shadeSlidingBorder", "false"));
/*     */ 
/* 464 */       Object slidingEastFrameBorder = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 467 */           return new SlidingFrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(1, 15, 1, 0));
/*     */         }
/*     */       });
/* 472 */       Object slidingWestFrameBorder = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 475 */           return new SlidingFrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(1, 0, 1, 15));
/*     */         }
/*     */       });
/* 480 */       Object slidingNorthFrameBorder = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 483 */           return new SlidingFrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(0, 1, 15, 1));
/*     */         }
/*     */       });
/* 488 */       Object slidingSouthFrameBorder = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 491 */           return new SlidingFrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(15, 1, 0, 1));
/*     */         }
/*     */       });
/* 496 */       Object slidingEastFrameBorder2 = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 499 */           return new ResizeFrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(0, 4, 0, 0));
/*     */         }
/*     */       });
/* 504 */       Object slidingWestFrameBorder2 = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 507 */           return new ResizeFrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(0, 0, 0, 4));
/*     */         }
/*     */       });
/* 512 */       Object slidingNorthFrameBorder2 = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 515 */           return new ResizeFrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(0, 0, 4, 0));
/*     */         }
/*     */       });
/* 520 */       Object slidingSouthFrameBorder2 = new ExtWindowsDesktopProperty(new String[] { "win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor" }, new Object[] { UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow") }, toolkit, new ConvertListener()
/*     */       {
/*     */         public Object convert(Object[] obj) {
/* 523 */           return new ResizeFrameBorder((Color)obj[0], (Color)obj[1], (Color)obj[2], (Color)obj[3], new Insets(4, 0, 0, 0));
/*     */         }
/*     */       });
/* 528 */       uiDefaults = new Object[] { "Workspace.background", mdiBackgroundColor, "SidePane.margin", new InsetsUIResource(1, 1, 1, 1), "SidePane.iconTextGap", Integer.valueOf(2), "SidePane.textBorderGap", Integer.valueOf(13), "SidePane.itemGap", Integer.valueOf(5), "SidePane.groupGap", Integer.valueOf(13), "SidePane.foreground", defaultDarkShadowColor, "SidePane.background", new ColorUIResource(XertoUtils.getApplicationFrameBackgroundColor()), "SidePane.lineColor", defaultShadowColor, "SidePane.buttonBackground", new ColorUIResource(XertoUtils.getLightControlColor()), "SidePane.selectedButtonBackground", selectedButtonColor, "SidePane.selectedButtonForeground", defaultTextColor, "SidePane.font", controlFont, "SidePane.orientation", Integer.valueOf(1), "SidePane.showSelectedTabText", Boolean.TRUE, "SidePane.alwaysShowTabText", Boolean.FALSE, "DockableFrame.defaultIcon", JideIconsFactory.getImageIcon("jide/dockableframe_blank.gif"), "DockableFrame.background", defaultBackgroundColor, "DockableFrame.border", frameBorder, "DockableFrame.floatingBorder", new BorderUIResource(BorderFactory.createLineBorder(XertoUtils.getFrameBorderColor())), "DockableFrame.slidingEastBorder", useShadowBorder ? slidingEastFrameBorder : slidingEastFrameBorder2, "DockableFrame.slidingWestBorder", useShadowBorder ? slidingWestFrameBorder : slidingWestFrameBorder2, "DockableFrame.slidingNorthBorder", useShadowBorder ? slidingNorthFrameBorder : slidingNorthFrameBorder2, "DockableFrame.slidingSouthBorder", useShadowBorder ? slidingSouthFrameBorder : slidingSouthFrameBorder2, "DockableFrame.activeTitleBackground", activeTitleBackgroundColor, "DockableFrame.activeTitleForeground", new ColorUIResource(Color.WHITE), "DockableFrame.inactiveTitleBackground", defaultBackgroundColor, "DockableFrame.inactiveTitleForeground", new ColorUIResource(Color.WHITE), "DockableFrame.titleBorder", new BorderUIResource(BorderFactory.createEmptyBorder(1, 0, 1, 0)), "DockableFrame.activeTitleBorderColor", activeTitleBackgroundColor, "DockableFrame.inactiveTitleBorderColor", defaultShadowColor, "DockableFrame.font", controlFont, "DockableFrameTitlePane.gripperPainter", gripperPainter, "DockableFrameTitlePane.font", controlFont, "DockableFrameTitlePane.hideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 0, 10, 10), "DockableFrameTitlePane.unfloatIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 10, 10, 10), "DockableFrameTitlePane.floatIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 20, 10, 10), "DockableFrameTitlePane.autohideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 30, 10, 10), "DockableFrameTitlePane.stopAutohideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 40, 10, 10), "DockableFrameTitlePane.hideAutohideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 50, 10, 10), "DockableFrameTitlePane.maximizeIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 60, 10, 10), "DockableFrameTitlePane.restoreIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 70, 10, 10), "DockableFrameTitlePane.titleBarComponent", Boolean.FALSE, "DockableFrameTitlePane.alwaysShowAllButtons", Boolean.FALSE, "DockableFrameTitlePane.buttonsAlignment", Integer.valueOf(11), "DockableFrameTitlePane.titleAlignment", Integer.valueOf(10), "DockableFrameTitlePane.buttonGap", Integer.valueOf(0), "DockableFrameTitlePane.showIcon", Boolean.TRUE, "DockableFrameTitlePane.margin", new InsetsUIResource(0, 3, 0, 3), "Contour.color", new ColorUIResource(136, 136, 136), "Contour.thickness", Integer.valueOf(4), "ContentContainer.background", defaultFormBackground, "ContentContainer.vgap", Integer.valueOf(3), "ContentContainer.hgap", Integer.valueOf(3), "DockingFramework.changeCursor", Boolean.FALSE, "FrameContainer.contentBorderInsets", new InsetsUIResource(0, 0, 0, 0) };
/*     */ 
/* 596 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 599 */     if ((products & 0x2) != 0) {
/* 600 */       int SIZE = 12;
/* 601 */       int MASK_SIZE = 12;
/* 602 */       ImageIcon collapsiblePaneImage = IconsFactory.getImageIcon(XertoWindowsUtils.class, "icons/collapsible_pane_xerto.png");
/* 603 */       ImageIcon collapsiblePaneMask = IconsFactory.getImageIcon(XertoWindowsUtils.class, "icons/collapsible_pane_mask.png");
/* 604 */       ImageIcon normalIcon = IconsFactory.getIcon(null, collapsiblePaneImage, 0, 0, 12, 12);
/* 605 */       ImageIcon emphasizedIcon = IconsFactory.getIcon(null, collapsiblePaneImage, 12, 0, 12, 12);
/* 606 */       ImageIcon downMark = IconsFactory.getIcon(null, collapsiblePaneMask, 0, 0, 12, 12);
/* 607 */       ImageIcon upMark = IconsFactory.getIcon(null, collapsiblePaneMask, 0, 12, 12, 12);
/*     */ 
/* 609 */       ColorUIResource collapsiblePaneBackground = new ColorUIResource(172, 168, 153);
/*     */ 
/* 611 */       uiDefaults = new Object[] { "CollapsiblePanes.border", new BorderUIResource(BorderFactory.createEmptyBorder(12, 12, 12, 12)), "CollapsiblePanes.gap", Integer.valueOf(5), "CollapsiblePane.background", collapsiblePaneBackground, "CollapsiblePane.contentBackground", defaultLtHighlightColor, "CollapsiblePane.foreground", new ColorUIResource(XertoUtils.getTextColor(collapsiblePaneBackground)), "CollapsiblePane.emphasizedBackground", collapsiblePaneBackground, "CollapsiblePane.emphasizedForeground", new ColorUIResource(XertoUtils.getTextColor(XertoUtils.getEmBaseColor(collapsiblePaneBackground))), "CollapsiblePane.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)), "CollapsiblePane.font", controlFont, "CollapsiblePane.contentBorder", new BorderUIResource(BorderFactory.createEmptyBorder(8, 10, 8, 10)), "CollapsiblePane.titleBorder", new BorderUIResource(BorderFactory.createEmptyBorder()), "CollapsiblePane.titleFont", boldFont, "CollapsiblePane.downIcon", IconsFactory.getOverlayIcon(null, normalIcon, downMark, 0), "CollapsiblePane.upIcon", IconsFactory.getOverlayIcon(null, normalIcon, upMark, 0), "CollapsiblePane.downIcon.emphasized", IconsFactory.getOverlayIcon(null, emphasizedIcon, downMark, 0), "CollapsiblePane.upIcon.emphasized", IconsFactory.getOverlayIcon(null, emphasizedIcon, upMark, 0), "CollapsiblePane.titleButtonBackground", normalIcon, "CollapsiblePane.titleButtonBackground.emphasized", emphasizedIcon, "StatusBarItem.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 1, 0, 1)), "StatusBar.border", new StatusBarBorder(), "StatusBar.gap", Integer.valueOf(2), "StatusBar.background", defaultBackgroundColor, "StatusBar.font", controlFont, "MemoryStatusBarItem.fillColor", new ColorUIResource(236, 233, 176), "DocumentPane.groupBorder", new BorderUIResource(BorderFactory.createLineBorder(Color.gray)), "DocumentPane.newHorizontalGroupIcon", JideIconsFactory.getImageIcon("jide/windows_new_horizontal_tab_group.png"), "DocumentPane.newVerticalGroupIcon", JideIconsFactory.getImageIcon("jide/windows_new_vertical_tab_group.png"), "DocumentPane.boldActiveTab", Boolean.FALSE };
/*     */ 
/* 650 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 653 */     if ((products & 0x10) != 0) {
/* 654 */       uiDefaults = new Object[] { "CommandBar.font", toolbarFont, "CommandBar.background", commandBarBackground, "CommandBar.foreground", defaultTextColor, "CommandBar.shadow", defaultShadowColor, "CommandBar.darkShadow", defaultDarkShadowColor, "CommandBar.light", defaultHighlightColor, "CommandBar.highlight", defaultLtHighlightColor, "CommandBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(1, 1, 1, 1)), "CommandBar.borderVert", new BorderUIResource(BorderFactory.createEmptyBorder(1, 1, 1, 1)), "CommandBar.borderFloating", new BorderUIResource(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIDefaultsLookup.getColor("activeCaption"), 2), BorderFactory.createEmptyBorder(1, 1, 1, 1))), "CommandBar.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "UP", "navigateUp", "KP_UP", "navigateUp", "DOWN", "navigateDown", "KP_DOWN", "navigateDown", "LEFT", "navigateLeft", "KP_LEFT", "navigateLeft", "RIGHT", "navigateRight", "KP_RIGHT", "navigateRight" }), "CommandBar.titleBarSize", Integer.valueOf(17), "CommandBar.titleBarButtonGap", Integer.valueOf(1), "CommandBar.titleBarBackground", activeTitleBackgroundColor, "CommandBar.titleBarForeground", activeTitleTextColor, "CommandBar.titleBarFont", boldFont, "CommandBar.separatorSize", Integer.valueOf(5), "CommandBarSeparator.background", XertoUtils.getControlColor(), "CommandBarSeparator.foreground", XertoUtils.getControlMidShadowColor(), "Chevron.size", Integer.valueOf(11) };
/*     */ 
/* 692 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 695 */     if ((products & 0x4) != 0) {
/* 696 */       uiDefaults = new Object[] { "NestedTableHeader.cellBorder", new HeaderCellBorder(), "GroupList.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] { "TAB", "selectNextGroup", "shift TAB", "selectPreviousGroup" }) };
/*     */ 
/* 704 */       table.putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 707 */     if (!JideSwingUtilities.shouldUseSystemFont()) {
/* 708 */       Object[] uiDefaultsFont = { "TabbedPane.font", controlFont, "TitledBorder.font", boldFont, "TableHeader.font", controlFont, "Table.font", controlFont, "List.font", controlFont, "Tree.font", controlFont, "ToolTip.font", controlFont, "CheckBox.font", controlFont, "RadioButton.font", controlFont, "Label.font", controlFont, "Panel.font", controlFont, "TextField.font", controlFont, "ComboBox.font", controlFont, "Button.font", controlFont };
/*     */ 
/* 724 */       table.putDefaults(uiDefaultsFont);
/*     */     }
/*     */ 
/* 727 */     UIDefaultsLookup.put(table, "Theme.painter", XertoPainter.getInstance());
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.xerto.XertoWindowsUtils
 * JD-Core Version:    0.6.0
 */
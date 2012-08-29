/*     */ package com.jidesoft.plaf.office2003;
/*     */ 
/*     */ import com.jidesoft.icons.IconsFactory;
/*     */ import com.jidesoft.plaf.ExtWindowsDesktopProperty;
/*     */ import com.jidesoft.plaf.LookAndFeelFactory;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.plaf.WindowsDesktopProperty;
/*     */ import com.jidesoft.plaf.XPUtils;
/*     */ import com.jidesoft.plaf.vsnet.ConvertListener;
/*     */ import com.jidesoft.utils.ColorUtils;
/*     */ import java.awt.Color;
/*     */ import java.awt.Toolkit;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIDefaults.ActiveValue;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ 
/*     */ public class DefaultOffice2003Theme extends Office2003Theme
/*     */ {
/*     */   private static final double DAKRER_FACTOR = 0.76D;
/*     */   private static final double FACTOR = 0.85D;
/*     */   private static final double LIGHTER_FACTOR = 0.95D;
/*     */   private static final double EVEN_LIGHTER_FACTOR = 0.97D;
/*     */   private static boolean _useStandardSelectionColor;
/*     */ 
/*     */   static Color getLighterColor(Color color)
/*     */   {
/*  41 */     if (Color.BLACK.equals(color)) {
/*  42 */       return color;
/*     */     }
/*  44 */     if (Color.WHITE.equals(color)) {
/*  45 */       return color;
/*     */     }
/*  47 */     return ColorUtils.getDerivedColor(color, 0.93F);
/*     */   }
/*     */ 
/*     */   public DefaultOffice2003Theme() {
/*  51 */     super("Default");
/*     */ 
/*  53 */     if (!isUseStandardSelectionColor()) {
/*  54 */       putDerivedSelectionColor();
/*     */     }
/*     */ 
/*  57 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*  58 */     WindowsDesktopProperty control = new WindowsDesktopProperty("win.3d.backgroundColor", UIDefaultsLookup.get("control"), toolkit);
/*  59 */     Object controlDk2 = new ExtWindowsDesktopProperty(new String[] { "win.3d.backgroundColor" }, new Object[] { UIDefaultsLookup.get("control") }, toolkit, new ConvertListener()
/*     */     {
/*     */       public Object convert(Object[] obj) {
/*  62 */         return DefaultOffice2003Theme.this.darker((Color)obj[0], 0.95D);
/*     */       }
/*     */     });
/*  65 */     Object controlDk = new ExtWindowsDesktopProperty(new String[] { "win.3d.backgroundColor" }, new Object[] { UIDefaultsLookup.get("control") }, toolkit, new ConvertListener()
/*     */     {
/*     */       public Object convert(Object[] obj) {
/*  68 */         return DefaultOffice2003Theme.this.darker((Color)obj[0], 0.76D);
/*     */       }
/*     */     });
/*  71 */     Object controlLt = new ExtWindowsDesktopProperty(new String[] { "win.3d.highlightColor" }, new Object[] { UIDefaultsLookup.get("controlLtHighlight") }, toolkit, new ConvertListener()
/*     */     {
/*     */       public Object convert(Object[] obj) {
/*  74 */         return DefaultOffice2003Theme.this.brighter((Color)obj[0], 0.85D);
/*     */       }
/*     */     });
/*  77 */     WindowsDesktopProperty controlShadow = new WindowsDesktopProperty("win.3d.shadowColor", UIDefaultsLookup.get("controlShadow"), toolkit);
/*  78 */     Object controlDkShadow = new ExtWindowsDesktopProperty(new String[] { "win.3d.shadowColor" }, new Object[] { UIDefaultsLookup.get("controlShadow") }, toolkit, new ConvertListener()
/*     */     {
/*     */       public Object convert(Object[] obj) {
/*  81 */         return DefaultOffice2003Theme.this.darker((Color)obj[0], 0.85D);
/*     */       }
/*     */     });
/*  84 */     WindowsDesktopProperty controlText = new WindowsDesktopProperty("win.button.textColor", UIDefaultsLookup.get("controlText"), toolkit);
/*  85 */     WindowsDesktopProperty controlLtHighlight = new WindowsDesktopProperty("win.3d.highlightColor", UIDefaultsLookup.get("controlLtHighlight"), toolkit);
/*  86 */     WindowsDesktopProperty commandBarCaption = new WindowsDesktopProperty("", UIDefaultsLookup.get("inactiveCaption"), toolkit);
/*  87 */     Object menuItemBackground = new ExtWindowsDesktopProperty(new String[] { "win.3d.backgroundColor" }, new Object[] { UIDefaultsLookup.get("control") }, toolkit, new ConvertListener()
/*     */     {
/*     */       public Object convert(Object[] obj) {
/*  90 */         return new ColorUIResource(DefaultOffice2003Theme.getLighterColor((Color)obj[0]));
/*     */       }
/*     */     });
/*  94 */     Object[] uiDefaults = { "control", control, "controlLt", controlLt, "controlDk", control, "controlShadow", controlShadow, "OptionPane.bannerLt", new ColorUIResource(0, 52, 206), "OptionPane.bannerDk", new ColorUIResource(45, 96, 249), "OptionPane.bannerForeground", new ColorUIResource(255, 255, 255), "Separator.foreground", controlShadow, "Separator.foregroundLt", controlLt, "Gripper.foreground", controlShadow, "Gripper.foregroundLt", controlLt, "Chevron.backgroundLt", controlDk, "Chevron.backgroundDk", controlShadow, "Divider.backgroundLt", controlShadow, "Divider.backgroundDk", controlDkShadow, "backgroundLt", controlLt, "backgroundDk", control, "selection.border", controlShadow, "MenuItem.background", menuItemBackground, "DockableFrameTitlePane.backgroundLt", controlLt, "DockableFrameTitlePane.backgroundDk", controlDk2, "DockableFrameTitlePane.activeForeground", controlText, "DockableFrameTitlePane.inactiveForeground", controlText, "DockableFrame.backgroundLt", controlLt, "DockableFrame.backgroundDk", controlLt, "CommandBar.titleBarBackground", commandBarCaption };
/*     */ 
/* 132 */     putDefaults(uiDefaults);
/*     */ 
/* 134 */     int products = LookAndFeelFactory.getProductsUsed();
/*     */ 
/* 136 */     if ((products & 0x2) != 0) {
/* 137 */       int SIZE = 20;
/* 138 */       int MASK_SIZE = 11;
/* 139 */       ImageIcon collapsiblePaneImage = IconsFactory.getImageIcon(Office2003WindowsUtils.class, "icons/collapsible_pane_default.png");
/* 140 */       ImageIcon collapsiblePaneMask = IconsFactory.getImageIcon(Office2003WindowsUtils.class, "icons/collapsible_pane_mask.png");
/* 141 */       ImageIcon normalIcon = IconsFactory.getIcon(null, collapsiblePaneImage, 0, 0, 20, 20);
/* 142 */       ImageIcon emphasizedIcon = IconsFactory.getIcon(null, collapsiblePaneImage, 20, 0, 20, 20);
/* 143 */       ImageIcon downMark = IconsFactory.getIcon(null, collapsiblePaneMask, 0, 0, 11, 11);
/* 144 */       ImageIcon upMark = IconsFactory.getIcon(null, collapsiblePaneMask, 0, 11, 11, 11);
/* 145 */       uiDefaults = new Object[] { "CollapsiblePane.contentBackground", controlLtHighlight, "CollapsiblePanes.backgroundLt", control, "CollapsiblePanes.backgroundDk", controlShadow, "CollapsiblePaneTitlePane.backgroundLt", controlLt, "CollapsiblePaneTitlePane.backgroundDk", control, "CollapsiblePaneTitlePane.foreground", controlText, "CollapsiblePaneTitlePane.foreground.focus", controlText, "CollapsiblePaneTitlePane.backgroundLt.emphasized", controlShadow, "CollapsiblePaneTitlePane.backgroundDk.emphasized", controlDkShadow, "CollapsiblePaneTitlePane.foreground.emphasized", controlLtHighlight, "CollapsiblePaneTitlePane.foreground.focus.emphasized", controlLtHighlight, "CollapsiblePane.downIcon", IconsFactory.getOverlayIcon(null, normalIcon, downMark, 0), "CollapsiblePane.upIcon", IconsFactory.getOverlayIcon(null, normalIcon, upMark, 0), "CollapsiblePane.downIcon.emphasized", IconsFactory.getOverlayIcon(null, emphasizedIcon, downMark, 0), "CollapsiblePane.upIcon.emphasized", IconsFactory.getOverlayIcon(null, emphasizedIcon, upMark, 0), "CollapsiblePane.upMask", upMark, "CollapsiblePane.downMask", downMark, "CollapsiblePane.titleButtonBackground", normalIcon, "CollapsiblePane.titleButtonBackground.emphasized", emphasizedIcon };
/*     */ 
/* 166 */       putDefaults(uiDefaults);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void putDerivedSelectionColor() {
/* 171 */     Object selectionRollover = new UIDefaults.ActiveValue() {
/*     */       public Object createValue(UIDefaults table) {
/* 173 */         return UIDefaultsLookup.getColor("JideButton.focusedBackground");
/*     */       }
/*     */     };
/* 176 */     Object selectionRolloverLt = new UIDefaults.ActiveValue() {
/*     */       public Object createValue(UIDefaults table) {
/* 178 */         return DefaultOffice2003Theme.this.brighter(UIDefaultsLookup.getColor("JideButton.focusedBackground"), 0.95D);
/*     */       }
/*     */     };
/* 181 */     Object selectionRolloverDk = new UIDefaults.ActiveValue() {
/*     */       public Object createValue(UIDefaults table) {
/* 183 */         return DefaultOffice2003Theme.this.darker(UIDefaultsLookup.getColor("JideButton.focusedBackground"), 0.95D);
/*     */       }
/*     */     };
/* 187 */     Object selectionSelected = new UIDefaults.ActiveValue() {
/*     */       public Object createValue(UIDefaults table) {
/* 189 */         return UIDefaultsLookup.getColor("JideButton.selectedBackground");
/*     */       }
/*     */     };
/* 192 */     Object selectionSelectedLt = new UIDefaults.ActiveValue() {
/*     */       public Object createValue(UIDefaults table) {
/* 194 */         return DefaultOffice2003Theme.this.brighter(UIDefaultsLookup.getColor("JideButton.selectedBackground"), 0.95D);
/*     */       }
/*     */     };
/* 197 */     Object selectionSelectedDk = new UIDefaults.ActiveValue() {
/*     */       public Object createValue(UIDefaults table) {
/* 199 */         return DefaultOffice2003Theme.this.darker(UIDefaultsLookup.getColor("JideButton.selectedBackground"), 0.95D);
/*     */       }
/*     */     };
/* 202 */     Object selectionSelectedAndFocused = new UIDefaults.ActiveValue() {
/*     */       public Object createValue(UIDefaults table) {
/* 204 */         return UIDefaultsLookup.getColor("JideButton.selectedAndFocusedBackground");
/*     */       }
/*     */     };
/* 207 */     Object selectionSelectedAndFocusedLt = new UIDefaults.ActiveValue() {
/*     */       public Object createValue(UIDefaults table) {
/* 209 */         return DefaultOffice2003Theme.this.brighter(UIDefaultsLookup.getColor("JideButton.selectedAndFocusedBackground"), 0.95D);
/*     */       }
/*     */     };
/* 212 */     Object selectionSelectedAndFocusedDk = new UIDefaults.ActiveValue() {
/*     */       public Object createValue(UIDefaults table) {
/* 214 */         return DefaultOffice2003Theme.this.darker(UIDefaultsLookup.getColor("JideButton.selectedAndFocusedBackground"), 0.95D);
/*     */       }
/*     */     };
/* 218 */     Object[] uiDefaultsSelection = { "selection.Rollover", selectionRollover, "selection.RolloverLt", selectionRolloverLt, "selection.RolloverDk", selectionRolloverDk, "selection.Selected", selectionSelected, "selection.SelectedLt", selectionSelectedLt, "selection.SelectedDk", selectionSelectedDk, "selection.Pressed", selectionSelectedAndFocused, "selection.PressedLt", selectionSelectedAndFocusedLt, "selection.PressedDk", selectionSelectedAndFocusedDk };
/*     */ 
/* 231 */     putDefaults(uiDefaultsSelection);
/*     */   }
/*     */ 
/*     */   private void removeDerivedSelectionColor() {
/* 235 */     Object[] uiDefaultsSelection = { "selection.Rollover", "selection.RolloverLt", "selection.RolloverDk", "selection.Selected", "selection.SelectedLt", "selection.SelectedDk", "selection.Pressed", "selection.PressedLt", "selection.PressedDk" };
/*     */ 
/* 248 */     for (Object o : uiDefaultsSelection)
/* 249 */       remove(o);
/*     */   }
/*     */ 
/*     */   private Color brighter(Color c, double factor)
/*     */   {
/* 270 */     int r = c.getRed();
/* 271 */     int g = c.getGreen();
/* 272 */     int b = c.getBlue();
/*     */ 
/* 279 */     int i = (int)(1.0D / (1.0D - factor));
/* 280 */     if ((r == 0) && (g == 0) && (b == 0)) {
/* 281 */       return new Color(i, i, i);
/*     */     }
/* 283 */     if ((r > 0) && (r < i)) r = i;
/* 284 */     if ((g > 0) && (g < i)) g = i;
/* 285 */     if ((b > 0) && (b < i)) b = i;
/*     */ 
/* 287 */     return new Color(Math.min((int)(r / factor), 255), Math.min((int)(g / factor), 255), Math.min((int)(b / factor), 255));
/*     */   }
/*     */ 
/*     */   private Color darker(Color c, double factor)
/*     */   {
/* 309 */     return new Color(Math.max((int)(c.getRed() * factor), 0), Math.max((int)(c.getGreen() * factor), 0), Math.max((int)(c.getBlue() * factor), 0));
/*     */   }
/*     */ 
/*     */   public static boolean isUseStandardSelectionColor()
/*     */   {
/* 320 */     return _useStandardSelectionColor;
/*     */   }
/*     */ 
/*     */   public static void setUseStandardSelectionColor(boolean useStandardSelectionColor)
/*     */   {
/* 333 */     _useStandardSelectionColor = useStandardSelectionColor;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/*  33 */       _useStandardSelectionColor = XPUtils.isXPStyleOn();
/*     */     }
/*     */     catch (UnsupportedOperationException e) {
/*  36 */       _useStandardSelectionColor = false;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.office2003.DefaultOffice2003Theme
 * JD-Core Version:    0.6.0
 */
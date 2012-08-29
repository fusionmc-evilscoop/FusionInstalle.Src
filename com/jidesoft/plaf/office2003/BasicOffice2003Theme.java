/*     */ package com.jidesoft.plaf.office2003;
/*     */ 
/*     */ import com.jidesoft.icons.IconsFactory;
/*     */ import com.jidesoft.plaf.LookAndFeelFactory;
/*     */ import com.jidesoft.utils.ColorUtils;
/*     */ import java.awt.Color;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ 
/*     */ public class BasicOffice2003Theme extends Office2003Theme
/*     */ {
/*     */   private Color _baseColor;
/*     */ 
/*     */   public BasicOffice2003Theme(String themeName)
/*     */   {
/*  25 */     super(themeName);
/*     */   }
/*     */ 
/*     */   public void setBaseColor(Color color, boolean derivedSelectionColor, String prefix)
/*     */   {
/*  41 */     this._baseColor = color;
/*  42 */     float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
/*  43 */     Color selectionColor = Color.getHSBColor(hsb[0], hsb[1] > 0.01D ? 0.45F : hsb[0], 0.9F);
/*     */ 
/*  45 */     Object[] uiDefaults = { "control", ColorUtils.getDerivedColor(color, 0.9F), "controlLt", ColorUtils.getDerivedColor(color, 0.95F), "controlDk", ColorUtils.getDerivedColor(color, 0.8F), "controlShadow", ColorUtils.getDerivedColor(color, 0.4F), "TabbedPane.selectDk", ColorUtils.getDerivedColor(selectionColor, 0.4F), "TabbedPane.selectLt", ColorUtils.getDerivedColor(selectionColor, 0.55F), "OptionPane.bannerLt", ColorUtils.getDerivedColor(color, 0.5F), "OptionPane.bannerDk", ColorUtils.getDerivedColor(color, 0.3F), "OptionPane.bannerForeground", new ColorUIResource(255, 255, 255), "Separator.foreground", ColorUtils.getDerivedColor(color, 0.4F), "Separator.foregroundLt", ColorUtils.getDerivedColor(color, 1.0F), "Gripper.foreground", ColorUtils.getDerivedColor(color, 0.6F), "Gripper.foregroundLt", ColorUtils.getDerivedColor(color, 0.92F), "Chevron.backgroundLt", ColorUtils.getDerivedColor(color, 0.85F), "Chevron.backgroundDk", ColorUtils.getDerivedColor(color, 0.75F), "Divider.backgroundLt", ColorUtils.getDerivedColor(color, 0.9F), "Divider.backgroundDk", ColorUtils.getDerivedColor(color, 0.97F), "backgroundLt", ColorUtils.getDerivedColor(color, 0.95F), "backgroundDk", ColorUtils.getDerivedColor(color, 0.9F), "CommandBar.titleBarBackground", ColorUtils.getDerivedColor(color, 0.6F), "MenuItem.background", ColorUtils.getDerivedColor(color, 0.95F), "DockableFrameTitlePane.backgroundLt", ColorUtils.getDerivedColor(color, 0.92F), "DockableFrameTitlePane.backgroundDk", ColorUtils.getDerivedColor(color, 0.85F), "DockableFrameTitlePane.activeForeground", new ColorUIResource(0, 0, 0), "DockableFrameTitlePane.inactiveForeground", new ColorUIResource(0, 0, 0), "DockableFrame.backgroundLt", ColorUtils.getDerivedColor(color, 0.92F), "DockableFrame.backgroundDk", ColorUtils.getDerivedColor(color, 0.89F), "selection.border", ColorUtils.getDerivedColor(color, 0.5F) };
/*     */ 
/*  86 */     putDefaults(uiDefaults);
/*     */ 
/*  88 */     int products = LookAndFeelFactory.getProductsUsed();
/*  89 */     if ((products & 0x2) != 0) {
/*  90 */       int SIZE = 20;
/*  91 */       int MASK_SIZE = 11;
/*  92 */       ImageIcon collapsiblePaneImage = IconsFactory.getImageIcon(Office2003WindowsUtils.class, "icons/collapsible_pane_" + prefix + ".png");
/*  93 */       ImageIcon collapsiblePaneMask = IconsFactory.getImageIcon(Office2003WindowsUtils.class, "icons/collapsible_pane_mask.png");
/*  94 */       ImageIcon normalIcon = IconsFactory.getIcon(null, collapsiblePaneImage, 0, 0, 20, 20);
/*  95 */       ImageIcon emphasizedIcon = IconsFactory.getIcon(null, collapsiblePaneImage, 20, 0, 20, 20);
/*  96 */       ImageIcon downMark = IconsFactory.getIcon(null, collapsiblePaneMask, 0, 0, 11, 11);
/*  97 */       ImageIcon upMark = IconsFactory.getIcon(null, collapsiblePaneMask, 0, 11, 11, 11);
/*  98 */       uiDefaults = new Object[] { "CollapsiblePane.contentBackground", ColorUtils.getDerivedColor(color, 0.98F), "CollapsiblePanes.backgroundLt", ColorUtils.getDerivedColor(color, 0.82F), "CollapsiblePanes.backgroundDk", ColorUtils.getDerivedColor(color, 0.78F), "CollapsiblePaneTitlePane.backgroundLt", ColorUtils.getDerivedColor(color, 0.98F), "CollapsiblePaneTitlePane.backgroundDk", ColorUtils.getDerivedColor(color, 0.93F), "CollapsiblePaneTitlePane.foreground", new ColorUIResource(63, 61, 61), "CollapsiblePaneTitlePane.foreground.focus", new ColorUIResource(126, 124, 124), "CollapsiblePaneTitlePane.backgroundLt.emphasized", ColorUtils.getDerivedColor(color, 0.7F), "CollapsiblePaneTitlePane.backgroundDk.emphasized", ColorUtils.getDerivedColor(color, 0.72F), "CollapsiblePaneTitlePane.foreground.emphasized", new ColorUIResource(255, 255, 255), "CollapsiblePaneTitlePane.foreground.focus.emphasized", new ColorUIResource(230, 230, 230), "CollapsiblePane.downIcon", IconsFactory.getOverlayIcon(null, normalIcon, downMark, 0), "CollapsiblePane.upIcon", IconsFactory.getOverlayIcon(null, normalIcon, upMark, 0), "CollapsiblePane.downIcon.emphasized", IconsFactory.getOverlayIcon(null, emphasizedIcon, downMark, 0), "CollapsiblePane.upIcon.emphasized", IconsFactory.getOverlayIcon(null, emphasizedIcon, upMark, 0), "CollapsiblePane.upMask", upMark, "CollapsiblePane.downMask", downMark, "CollapsiblePane.titleButtonBackground", normalIcon, "CollapsiblePane.titleButtonBackground.emphasized", emphasizedIcon };
/*     */ 
/* 120 */       putDefaults(uiDefaults);
/*     */     }
/*     */ 
/* 123 */     if (derivedSelectionColor) {
/* 124 */       Object[] uiDefaultsSelection = { "selection.Rollover", selectionColor, "selection.RolloverLt", ColorUtils.getDerivedColor(selectionColor, 0.55F), "selection.RolloverDk", ColorUtils.getDerivedColor(selectionColor, 0.45F), "selection.Selected", ColorUtils.getDerivedColor(selectionColor, 0.45F), "selection.SelectedLt", ColorUtils.getDerivedColor(selectionColor, 0.55F), "selection.SelectedDk", ColorUtils.getDerivedColor(selectionColor, 0.5F), "selection.Pressed", ColorUtils.getDerivedColor(selectionColor, 0.4F), "selection.PressedLt", ColorUtils.getDerivedColor(selectionColor, 0.45F), "selection.PressedDk", ColorUtils.getDerivedColor(selectionColor, 0.35F) };
/*     */ 
/* 137 */       putDefaults(uiDefaultsSelection);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Color getBaseColor()
/*     */   {
/* 149 */     return this._baseColor;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.office2003.BasicOffice2003Theme
 * JD-Core Version:    0.6.0
 */
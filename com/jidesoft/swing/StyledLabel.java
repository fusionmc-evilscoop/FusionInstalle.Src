/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.plaf.LookAndFeelFactory;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ public class StyledLabel extends JLabel
/*     */ {
/*     */   private static final String uiClassID = "StyledLabelUI";
/*     */   private List<StyleRange> _styleRanges;
/*     */   private boolean _ignoreColorSettings;
/*     */   public static final String PROPERTY_STYLE_RANGE = "styleRange";
/*     */   public static final String PROPERTY_IGNORE_COLOR_SETTINGS = "ignoreColorSettings";
/*     */ 
/*     */   public StyledLabel()
/*     */   {
/*     */   }
/*     */ 
/*     */   public StyledLabel(Icon image)
/*     */   {
/*  62 */     super(image);
/*     */   }
/*     */ 
/*     */   public StyledLabel(Icon image, int horizontalAlignment) {
/*  66 */     super(image, horizontalAlignment);
/*     */   }
/*     */ 
/*     */   public StyledLabel(String text) {
/*  70 */     super(text);
/*     */   }
/*     */ 
/*     */   public StyledLabel(String text, int horizontalAlignment) {
/*  74 */     super(text, horizontalAlignment);
/*     */   }
/*     */ 
/*     */   public StyledLabel(String text, Icon icon, int horizontalAlignment) {
/*  78 */     super(text, icon, horizontalAlignment);
/*     */   }
/*     */ 
/*     */   public void updateUI()
/*     */   {
/*  88 */     if (UIDefaultsLookup.get("StyledLabelUI") == null) {
/*  89 */       LookAndFeelFactory.installJideExtension();
/*     */     }
/*  91 */     setUI(UIManager.getUI(this));
/*     */   }
/*     */ 
/*     */   public String getUIClassID()
/*     */   {
/* 104 */     return "StyledLabelUI";
/*     */   }
/*     */ 
/*     */   public synchronized void addStyleRange(StyleRange styleRange)
/*     */   {
/* 113 */     if (styleRange == null) {
/* 114 */       throw new IllegalArgumentException("StyleRange cannot be null.");
/*     */     }
/* 116 */     List ranges = internalGetStyleRanges();
/* 117 */     for (int i = ranges.size() - 1; i >= 0; i--) {
/* 118 */       StyleRange range = (StyleRange)ranges.get(i);
/* 119 */       if ((range.getStart() == styleRange.getStart()) && (range.getStart() == styleRange.getStart())) {
/* 120 */         ranges.remove(i);
/*     */       }
/*     */     }
/* 123 */     internalGetStyleRanges().add(styleRange);
/* 124 */     firePropertyChange("styleRange", null, styleRange);
/*     */   }
/*     */ 
/*     */   public synchronized void setStyleRanges(StyleRange[] styleRanges)
/*     */   {
/* 133 */     internalGetStyleRanges().clear();
/* 134 */     addStyleRanges(styleRanges);
/*     */   }
/*     */ 
/*     */   public synchronized void addStyleRanges(StyleRange[] styleRanges)
/*     */   {
/* 143 */     if (styleRanges != null) {
/* 144 */       for (StyleRange styleRange : styleRanges) {
/* 145 */         internalGetStyleRanges().add(styleRange);
/*     */       }
/* 147 */       firePropertyChange("styleRange", null, styleRanges);
/*     */     }
/*     */     else {
/* 150 */       firePropertyChange("styleRange", null, null);
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized StyleRange[] getStyleRanges()
/*     */   {
/* 160 */     List list = internalGetStyleRanges();
/* 161 */     return (StyleRange[])list.toArray(new StyleRange[list.size()]);
/*     */   }
/*     */ 
/*     */   private List<StyleRange> internalGetStyleRanges() {
/* 165 */     if (this._styleRanges == null) {
/* 166 */       this._styleRanges = new Vector();
/*     */     }
/* 168 */     return this._styleRanges;
/*     */   }
/*     */ 
/*     */   public synchronized void clearStyleRange(StyleRange styleRange)
/*     */   {
/* 177 */     if (internalGetStyleRanges().remove(styleRange))
/* 178 */       firePropertyChange("styleRange", styleRange, null);
/*     */   }
/*     */ 
/*     */   public synchronized void clearStyleRanges()
/*     */   {
/* 186 */     internalGetStyleRanges().clear();
/* 187 */     firePropertyChange("styleRange", null, null);
/*     */   }
/*     */ 
/*     */   public boolean isIgnoreColorSettings()
/*     */   {
/* 198 */     return this._ignoreColorSettings;
/*     */   }
/*     */ 
/*     */   public void setIgnoreColorSettings(boolean ignoreColorSettings)
/*     */   {
/* 210 */     boolean old = this._ignoreColorSettings;
/* 211 */     if (old != ignoreColorSettings) {
/* 212 */       this._ignoreColorSettings = ignoreColorSettings;
/* 213 */       firePropertyChange("ignoreColorSettings", old, ignoreColorSettings);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.StyledLabel
 * JD-Core Version:    0.6.0
 */
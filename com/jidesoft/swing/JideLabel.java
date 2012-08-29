/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.plaf.LookAndFeelFactory;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ public class JideLabel extends JLabel
/*     */   implements Alignable, AlignmentSupport
/*     */ {
/*     */   private static final String uiClassID = "JideLabelUI";
/*     */   public static final String PROPERTY_CLOCKWISE = "clockwise";
/*  23 */   private boolean _clockwise = true;
/*     */   private int _orientation;
/*     */ 
/*     */   public JideLabel()
/*     */   {
/*     */   }
/*     */ 
/*     */   public JideLabel(String text)
/*     */   {
/*  31 */     super(text);
/*     */   }
/*     */ 
/*     */   public JideLabel(Icon image, int horizontalAlignment) {
/*  35 */     super(image, horizontalAlignment);
/*     */   }
/*     */ 
/*     */   public JideLabel(Icon image) {
/*  39 */     super(image);
/*     */   }
/*     */ 
/*     */   public JideLabel(String text, int horizontalAlignment) {
/*  43 */     super(text, horizontalAlignment);
/*     */   }
/*     */ 
/*     */   public JideLabel(String text, Icon icon, int horizontalAlignment) {
/*  47 */     super(text, icon, horizontalAlignment);
/*     */   }
/*     */ 
/*     */   public void updateUI()
/*     */   {
/*  57 */     if (UIDefaultsLookup.get("JideLabelUI") == null) {
/*  58 */       LookAndFeelFactory.installJideExtension();
/*     */     }
/*  60 */     setUI(UIManager.getUI(this));
/*     */   }
/*     */ 
/*     */   public String getUIClassID()
/*     */   {
/*  74 */     return "JideLabelUI";
/*     */   }
/*     */ 
/*     */   public int getOrientation()
/*     */   {
/*  83 */     return this._orientation;
/*     */   }
/*     */ 
/*     */   public void setOrientation(int orientation) {
/*  87 */     int old = this._orientation;
/*  88 */     if (old != orientation) {
/*  89 */       this._orientation = orientation;
/*  90 */       firePropertyChange("orientation", old, orientation);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean supportVerticalOrientation()
/*     */   {
/* 100 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean supportHorizontalOrientation()
/*     */   {
/* 109 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isClockwise()
/*     */   {
/* 118 */     return this._clockwise;
/*     */   }
/*     */ 
/*     */   public void setClockwise(boolean clockwise)
/*     */   {
/* 127 */     boolean old = this._clockwise;
/* 128 */     if (clockwise != this._clockwise) {
/* 129 */       this._clockwise = clockwise;
/* 130 */       firePropertyChange("clockwise", old, this._clockwise);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.JideLabel
 * JD-Core Version:    0.6.0
 */
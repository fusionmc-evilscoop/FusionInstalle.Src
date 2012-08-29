/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import com.jidesoft.plaf.LookAndFeelFactory;
/*    */ import com.jidesoft.plaf.UIDefaultsLookup;
/*    */ import javax.swing.BoundedRangeModel;
/*    */ import javax.swing.JProgressBar;
/*    */ import javax.swing.UIManager;
/*    */ 
/*    */ public class MeterProgressBar extends JProgressBar
/*    */ {
/*    */   private static final String uiClassID = "MeterProgressBarUI";
/*    */   public static final String PROPERTY_STYLE = "style";
/*    */   public static final int STYLE_PLAIN = 0;
/*    */   public static final int STYLE_GRADIENT = 1;
/* 32 */   private int _style = 1;
/*    */ 
/*    */   public MeterProgressBar()
/*    */   {
/*    */   }
/*    */ 
/*    */   public MeterProgressBar(int orient) {
/* 39 */     super(orient);
/*    */   }
/*    */ 
/*    */   public MeterProgressBar(int min, int max) {
/* 43 */     super(min, max);
/*    */   }
/*    */ 
/*    */   public MeterProgressBar(int orient, int min, int max) {
/* 47 */     super(orient, min, max);
/*    */   }
/*    */ 
/*    */   public MeterProgressBar(BoundedRangeModel newModel) {
/* 51 */     super(newModel);
/*    */   }
/*    */ 
/*    */   public String getUIClassID()
/*    */   {
/* 61 */     return "MeterProgressBarUI";
/*    */   }
/*    */ 
/*    */   public void updateUI()
/*    */   {
/* 71 */     if (UIDefaultsLookup.get("MeterProgressBarUI") == null) {
/* 72 */       LookAndFeelFactory.installJideExtension();
/*    */     }
/* 74 */     setUI(UIManager.getUI(this));
/*    */   }
/*    */ 
/*    */   public int getStyle()
/*    */   {
/* 83 */     return this._style;
/*    */   }
/*    */ 
/*    */   public void setStyle(int style)
/*    */   {
/* 92 */     if ((style == 0) || (style == 1)) {
/* 93 */       if (this._style != style) {
/* 94 */         int oldValue = this._style;
/* 95 */         this._style = style;
/* 96 */         firePropertyChange("style", oldValue, style);
/*    */       }
/*    */     }
/*    */     else
/* 100 */       throw new IllegalArgumentException("style can be only PLAIN_STYLE or GRADIENT_STYLE");
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.MeterProgressBar
 * JD-Core Version:    0.6.0
 */
/*    */ package com.jidesoft.plaf.office2003;
/*    */ 
/*    */ import javax.swing.UIDefaults;
/*    */ import javax.swing.plaf.ColorUIResource;
/*    */ 
/*    */ public class Office2003Theme extends UIDefaults
/*    */ {
/*    */   private String _themeName;
/* 16 */   public final Object[] _uiDefaultsSelection = { "selection.Rollover", new ColorUIResource(255, 238, 194), "selection.RolloverLt", new ColorUIResource(255, 244, 204), "selection.RolloverDk", new ColorUIResource(255, 208, 145), "selection.Selected", new ColorUIResource(255, 192, 111), "selection.SelectedLt", new ColorUIResource(255, 213, 140), "selection.SelectedDk", new ColorUIResource(255, 173, 85), "selection.Pressed", new ColorUIResource(254, 128, 62), "selection.PressedLt", new ColorUIResource(255, 211, 142), "selection.PressedDk", new ColorUIResource(254, 145, 78) };
/*    */ 
/*    */   public Office2003Theme(String themeName)
/*    */   {
/* 31 */     this._themeName = themeName;
/* 32 */     putDefaults(this._uiDefaultsSelection);
/*    */   }
/*    */ 
/*    */   public String getThemeName() {
/* 36 */     return this._themeName;
/*    */   }
/*    */ 
/*    */   public void setThemeName(String themeName) {
/* 40 */     this._themeName = themeName;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.office2003.Office2003Theme
 * JD-Core Version:    0.6.0
 */
/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import com.jidesoft.plaf.LookAndFeelFactory;
/*    */ import com.jidesoft.plaf.UIDefaultsLookup;
/*    */ import javax.swing.UIManager;
/*    */ 
/*    */ public class HeaderBox extends JideButton
/*    */ {
/*    */   private static final String uiClassID = "HeaderBoxUI";
/*    */   public static final String CLIENT_PROPERTY_TABLE_CELL_EDITOR = "HeaderBox.isTableCellEditor";
/*    */ 
/*    */   public HeaderBox()
/*    */   {
/* 27 */     setOpaque(false);
/* 28 */     setRolloverEnabled(true);
/*    */   }
/*    */ 
/*    */   public void updateUI()
/*    */   {
/* 38 */     if (UIDefaultsLookup.get("HeaderBoxUI") == null) {
/* 39 */       LookAndFeelFactory.installJideExtension();
/*    */     }
/* 41 */     setUI(UIManager.getUI(this));
/*    */   }
/*    */ 
/*    */   public String getUIClassID()
/*    */   {
/* 52 */     return "HeaderBoxUI";
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.HeaderBox
 * JD-Core Version:    0.6.0
 */
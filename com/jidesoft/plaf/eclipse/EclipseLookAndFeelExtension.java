/*    */ package com.jidesoft.plaf.eclipse;
/*    */ 
/*    */ import com.jidesoft.plaf.LookAndFeelFactory;
/*    */ import com.jidesoft.plaf.basic.BasicLookAndFeelExtension;
/*    */ import java.beans.Beans;
/*    */ import javax.swing.UIDefaults;
/*    */ 
/*    */ public class EclipseLookAndFeelExtension extends BasicLookAndFeelExtension
/*    */ {
/*    */   public static void initClassDefaultsWithMenu(UIDefaults table)
/*    */   {
/* 26 */     if (!Beans.isDesignTime()) {
/* 27 */       table.put("PopupMenuSeparatorUI", "com.jidesoft.plaf.eclipse.EclipsePopupMenuSeparatorUI");
/* 28 */       table.put("SeparatorUI", "com.jidesoft.plaf.eclipse.EclipsePopupMenuSeparatorUI");
/* 29 */       table.put("MenuUI", "com.jidesoft.plaf.eclipse.EclipseMenuUI");
/* 30 */       table.put("MenuItemUI", "com.jidesoft.plaf.eclipse.EclipseMenuItemUI");
/* 31 */       table.put("CheckBoxMenuItemUI", "com.jidesoft.plaf.eclipse.EclipseCheckBoxMenuItemUI");
/* 32 */       table.put("RadioButtonMenuItemUI", "com.jidesoft.plaf.eclipse.EclipseRadioButtonMenuItemUI");
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void initClassDefaults(UIDefaults table)
/*    */   {
/* 42 */     BasicLookAndFeelExtension.initClassDefaults(table);
/*    */ 
/* 44 */     String eclipsePackageName = "com.jidesoft.plaf.eclipse.";
/*    */ 
/* 46 */     int products = LookAndFeelFactory.getProductsUsed();
/*    */ 
/* 48 */     table.put("JideTabbedPaneUI", "com.jidesoft.plaf.eclipse.EclipseJideTabbedPaneUI");
/* 49 */     table.put("JideSplitButtonUI", "com.jidesoft.plaf.eclipse.EclipseJideSplitButtonUI");
/* 50 */     table.put("GripperUI", "com.jidesoft.plaf.eclipse.EclipseGripperUI");
/*    */ 
/* 52 */     if ((products & 0x1) != 0) {
/* 53 */       table.put("SidePaneUI", "com.jidesoft.plaf.eclipse.EclipseSidePaneUI");
/* 54 */       table.put("DockableFrameUI", "com.jidesoft.plaf.eclipse.EclipseDockableFrameUI");
/*    */     }
/*    */ 
/* 57 */     if ((products & 0x2) != 0) {
/* 58 */       table.put("CollapsiblePaneUI", "com.jidesoft.plaf.eclipse.EclipseCollapsiblePaneUI");
/*    */     }
/*    */ 
/* 61 */     if ((products & 0x10) != 0)
/* 62 */       table.put("CommandBarUI", "com.jidesoft.plaf.eclipse.EclipseCommandBarUI");
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.eclipse.EclipseLookAndFeelExtension
 * JD-Core Version:    0.6.0
 */
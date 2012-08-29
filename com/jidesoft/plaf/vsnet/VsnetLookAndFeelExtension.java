/*    */ package com.jidesoft.plaf.vsnet;
/*    */ 
/*    */ import com.jidesoft.plaf.LookAndFeelFactory;
/*    */ import com.jidesoft.plaf.basic.BasicLookAndFeelExtension;
/*    */ import java.beans.Beans;
/*    */ import javax.swing.UIDefaults;
/*    */ 
/*    */ public class VsnetLookAndFeelExtension extends BasicLookAndFeelExtension
/*    */ {
/*    */   public static void initClassDefaultsWithMenu(UIDefaults table)
/*    */   {
/* 26 */     if (!Beans.isDesignTime()) {
/* 27 */       table.put("PopupMenuSeparatorUI", "com.jidesoft.plaf.vsnet.VsnetPopupMenuSeparatorUI");
/* 28 */       table.put("MenuUI", "com.jidesoft.plaf.vsnet.VsnetMenuUI");
/* 29 */       table.put("MenuItemUI", "com.jidesoft.plaf.vsnet.VsnetMenuItemUI");
/* 30 */       table.put("CheckBoxMenuItemUI", "com.jidesoft.plaf.vsnet.VsnetCheckBoxMenuItemUI");
/* 31 */       table.put("RadioButtonMenuItemUI", "com.jidesoft.plaf.vsnet.VsnetRadioButtonMenuItemUI");
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void initClassDefaults(UIDefaults table)
/*    */   {
/* 41 */     BasicLookAndFeelExtension.initClassDefaults(table);
/*    */ 
/* 43 */     String vsnetPackageName = "com.jidesoft.plaf.vsnet.";
/*    */ 
/* 46 */     table.put("JideTabbedPaneUI", "com.jidesoft.plaf.vsnet.VsnetJideTabbedPaneUI");
/* 47 */     table.put("GripperUI", "com.jidesoft.plaf.vsnet.VsnetGripperUI");
/*    */ 
/* 49 */     int products = LookAndFeelFactory.getProductsUsed();
/*    */ 
/* 51 */     if ((products & 0x1) != 0)
/*    */     {
/* 53 */       table.put("SidePaneUI", "com.jidesoft.plaf.vsnet.VsnetSidePaneUI");
/* 54 */       table.put("DockableFrameUI", "com.jidesoft.plaf.vsnet.VsnetDockableFrameUI");
/*    */     }
/*    */ 
/* 57 */     if ((products & 0x2) != 0)
/*    */     {
/* 59 */       table.put("CollapsiblePaneUI", "com.jidesoft.plaf.vsnet.VsnetCollapsiblePaneUI");
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.vsnet.VsnetLookAndFeelExtension
 * JD-Core Version:    0.6.0
 */
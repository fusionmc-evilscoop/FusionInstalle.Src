/*    */ package com.jidesoft.plaf.basic;
/*    */ 
/*    */ import com.jidesoft.plaf.LookAndFeelExtension;
/*    */ import com.jidesoft.plaf.LookAndFeelFactory;
/*    */ import javax.swing.UIDefaults;
/*    */ 
/*    */ public class BasicLookAndFeelExtension
/*    */   implements LookAndFeelExtension
/*    */ {
/*    */   public static void initClassDefaults(UIDefaults table)
/*    */   {
/* 25 */     int products = LookAndFeelFactory.getProductsUsed();
/*    */ 
/* 27 */     String basicPackageName = "com.jidesoft.plaf.basic.";
/*    */ 
/* 30 */     table.put("JidePopupMenuUI", "com.jidesoft.plaf.basic.BasicJidePopupMenuUI");
/* 31 */     table.put("HeaderBoxUI", "com.jidesoft.plaf.basic.BasicHeaderBoxUI");
/* 32 */     table.put("RangeSliderUI", "com.jidesoft.plaf.basic.BasicRangeSliderUI");
/* 33 */     table.put("FolderChooserUI", "com.jidesoft.plaf.basic.BasicFolderChooserUI");
/* 34 */     table.put("StyledLabelUI", "com.jidesoft.plaf.basic.BasicStyledLabelUI");
/* 35 */     table.put("GripperUI", "com.jidesoft.plaf.basic.BasicGripperUI");
/* 36 */     table.put("JidePopupUI", "com.jidesoft.plaf.basic.BasicJidePopupUI");
/* 37 */     table.put("JideTabbedPaneUI", "com.jidesoft.plaf.basic.BasicJideTabbedPaneUI");
/* 38 */     table.put("JideLabelUI", "com.jidesoft.plaf.basic.BasicJideLabelUI");
/* 39 */     table.put("JideButtonUI", "com.jidesoft.plaf.basic.BasicJideButtonUI");
/* 40 */     table.put("JideSplitButtonUI", "com.jidesoft.plaf.basic.BasicJideSplitButtonUI");
/* 41 */     table.put("JideComboBoxUI", "com.jidesoft.plaf.basic.BasicJideComboBoxUI");
/* 42 */     table.put("MeterProgressBarUI", "com.jidesoft.plaf.basic.MeterProgressBarUI");
/*    */ 
/* 44 */     if ((products & 0x4) != 0)
/*    */     {
/* 46 */       table.put("JideTableUI", "com.jidesoft.plaf.basic.BasicJideTableUI");
/* 47 */       table.put("NavigableTableUI", "com.jidesoft.plaf.basic.BasicNavigableTableUI");
/* 48 */       table.put("CellSpanTableUI", "com.jidesoft.plaf.basic.BasicCellSpanTableUI");
/* 49 */       table.put("TreeTableUI", "com.jidesoft.plaf.basic.BasicTreeTableUI");
/* 50 */       table.put("HierarchicalTableUI", "com.jidesoft.plaf.basic.BasicHierarchicalTableUI");
/* 51 */       table.put("NestedTableHeaderUI", "com.jidesoft.plaf.basic.BasicNestedTableHeaderUI");
/* 52 */       table.put("EditableTableHeaderUI", "com.jidesoft.plaf.basic.BasicEditableTableHeaderUI");
/* 53 */       table.put("GroupListUI", "com.jidesoft.plaf.basic.BasicGroupListUI");
/*    */     }
/*    */ 
/* 56 */     if ((products & 0x1) != 0)
/*    */     {
/* 58 */       table.put("SidePaneUI", "com.jidesoft.plaf.basic.BasicSidePaneUI");
/* 59 */       table.put("DockableFrameUI", "com.jidesoft.plaf.basic.BasicDockableFrameUI");
/*    */     }
/*    */ 
/* 62 */     if ((products & 0x2) != 0)
/*    */     {
/* 64 */       table.put("CollapsiblePaneUI", "com.jidesoft.plaf.basic.BasicCollapsiblePaneUI");
/* 65 */       table.put("StatusBarSeparatorUI", "com.jidesoft.plaf.basic.BasicStatusBarSeparatorUI");
/*    */     }
/*    */ 
/* 68 */     if ((products & 0x10) != 0)
/*    */     {
/* 70 */       table.put("CommandBarUI", "com.jidesoft.plaf.basic.BasicCommandBarUI");
/* 71 */       table.put("CommandBarSeparatorUI", "com.jidesoft.plaf.basic.BasicCommandBarSeparatorUI");
/* 72 */       table.put("ChevronUI", "com.jidesoft.plaf.basic.BasicChevronUI");
/* 73 */       table.put("CommandBarTitleBarUI", "com.jidesoft.plaf.basic.BasicCommandBarTitleBarUI");
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.BasicLookAndFeelExtension
 * JD-Core Version:    0.6.0
 */
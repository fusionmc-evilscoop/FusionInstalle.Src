/*    */ package com.jidesoft.plaf.eclipse;
/*    */ 
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.UIDefaults;
/*    */ import javax.swing.plaf.BorderUIResource;
/*    */ import javax.swing.plaf.InsetsUIResource;
/*    */ 
/*    */ public class Eclipse3xWindowsUtils extends EclipseWindowsUtils
/*    */ {
/*    */   public static void initClassDefaultsWithMenu(UIDefaults table)
/*    */   {
/* 25 */     EclipseWindowsUtils.initClassDefaultsWithMenu(table);
/* 26 */     initClassDefaults(table);
/*    */   }
/*    */ 
/*    */   public static void initClassDefaults(UIDefaults table)
/*    */   {
/* 35 */     EclipseWindowsUtils.initClassDefaults(table);
/* 36 */     table.put("JideTabbedPaneUI", "com.jidesoft.plaf.eclipse.Eclipse3xJideTabbedPaneUI");
/*    */   }
/*    */ 
/*    */   public static void initComponentDefaults(UIDefaults table)
/*    */   {
/* 45 */     EclipseWindowsUtils.initComponentDefaults(table);
/* 46 */     initComponentDefaultsForEclipse3x(table);
/*    */   }
/*    */ 
/*    */   public static void initComponentDefaultsWithMenu(UIDefaults table)
/*    */   {
/* 55 */     EclipseWindowsUtils.initComponentDefaultsWithMenu(table);
/* 56 */     initComponentDefaultsForEclipse3x(table);
/*    */   }
/*    */ 
/*    */   private static void initComponentDefaultsForEclipse3x(UIDefaults table) {
/* 60 */     Object[] uiDefaults = { "JideTabbedPane.defaultTabShape", Integer.valueOf(7), "JideTabbedPane.defaultTabColorTheme", Integer.valueOf(1), "JideTabbedPane.defaultResizeMode", Integer.valueOf(1), "JideTabbedPane.closeButtonMarginSize", Integer.valueOf(10), "JideTabbedPane.iconMarginHorizon", Integer.valueOf(8), "JideTabbedPane.iconMarginVertical", Integer.valueOf(6), "JideTabbedPane.border", new BorderUIResource(BorderFactory.createEmptyBorder(1, 1, 1, 1)), "JideTabbedPane.contentBorderInsets", new InsetsUIResource(2, 2, 2, 2) };
/*    */ 
/* 72 */     table.putDefaults(uiDefaults);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.eclipse.Eclipse3xWindowsUtils
 * JD-Core Version:    0.6.0
 */
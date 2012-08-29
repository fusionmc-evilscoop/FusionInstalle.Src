/*    */ package com.jidesoft.plaf.eclipse;
/*    */ 
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.UIDefaults;
/*    */ import javax.swing.plaf.BorderUIResource;
/*    */ import javax.swing.plaf.InsetsUIResource;
/*    */ 
/*    */ public class Eclipse3xMetalUtils extends EclipseMetalUtils
/*    */ {
/*    */   public static void initClassDefaults(UIDefaults table)
/*    */   {
/* 26 */     EclipseLookAndFeelExtension.initClassDefaults(table);
/* 27 */     table.put("JideTabbedPaneUI", "com.jidesoft.plaf.eclipse.Eclipse3xJideTabbedPaneUI");
/*    */   }
/*    */ 
/*    */   public static void initComponentDefaults(UIDefaults table)
/*    */   {
/* 36 */     EclipseMetalUtils.initComponentDefaults(table);
/* 37 */     initComponentDefaultsForEclipse3x(table);
/*    */   }
/*    */ 
/*    */   private static void initComponentDefaultsForEclipse3x(UIDefaults table) {
/* 41 */     Object[] uiDefaults = { "JideTabbedPane.defaultTabShape", Integer.valueOf(7), "JideTabbedPane.defaultTabColorTheme", Integer.valueOf(1), "JideTabbedPane.defaultResizeMode", Integer.valueOf(1), "JideTabbedPane.closeButtonMarginSize", Integer.valueOf(10), "JideTabbedPane.iconMarginHorizon", Integer.valueOf(8), "JideTabbedPane.iconMarginVertical", Integer.valueOf(6), "JideTabbedPane.border", new BorderUIResource(BorderFactory.createEmptyBorder(1, 1, 1, 1)), "JideTabbedPane.contentBorderInsets", new InsetsUIResource(2, 2, 2, 2) };
/*    */ 
/* 52 */     table.putDefaults(uiDefaults);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.eclipse.Eclipse3xMetalUtils
 * JD-Core Version:    0.6.0
 */
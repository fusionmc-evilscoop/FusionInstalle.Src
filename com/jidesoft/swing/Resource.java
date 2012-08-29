/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ class Resource
/*    */ {
/*    */   static final String BASENAME = "com.jidesoft.swing.swing";
/* 14 */   static final ResourceBundle RB = ResourceBundle.getBundle("com.jidesoft.swing.swing");
/*    */ 
/*    */   public static ResourceBundle getResourceBundle(Locale locale) {
/* 17 */     return ResourceBundle.getBundle("com.jidesoft.swing.swing", locale);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.Resource
 * JD-Core Version:    0.6.0
 */
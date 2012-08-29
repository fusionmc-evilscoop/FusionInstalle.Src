/*    */ package com.jidesoft.utils;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ class FontFilesResource
/*    */ {
/*    */   static final String BASENAME = "fonts.fontfiles";
/* 14 */   static final ResourceBundle RB = ResourceBundle.getBundle("fonts.fontfiles");
/*    */ 
/*    */   public static ResourceBundle getResourceBundle(Locale locale) {
/* 17 */     return ResourceBundle.getBundle("fonts.fontfiles", locale);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.FontFilesResource
 * JD-Core Version:    0.6.0
 */
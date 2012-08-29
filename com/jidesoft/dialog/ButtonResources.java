/*    */ package com.jidesoft.dialog;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public class ButtonResources
/*    */ {
/*    */   static final String BASENAME = "com.jidesoft.dialog.buttons";
/* 12 */   static final ResourceBundle RB = ResourceBundle.getBundle("com.jidesoft.dialog.buttons");
/*    */ 
/*    */   public static ResourceBundle getResourceBundle(Locale locale) {
/* 15 */     return ResourceBundle.getBundle("com.jidesoft.dialog.buttons", locale);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.dialog.ButtonResources
 * JD-Core Version:    0.6.0
 */
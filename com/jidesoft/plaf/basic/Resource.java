/*    */ package com.jidesoft.plaf.basic;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public class Resource
/*    */ {
/*    */   static final String BASENAME = "com.jidesoft.plaf.basic.basic";
/* 11 */   static final ResourceBundle RB = ResourceBundle.getBundle("com.jidesoft.plaf.basic.basic");
/*    */ 
/*    */   public static ResourceBundle getResourceBundle(Locale locale) {
/* 14 */     return ResourceBundle.getBundle("com.jidesoft.plaf.basic.basic", locale);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.Resource
 * JD-Core Version:    0.6.0
 */
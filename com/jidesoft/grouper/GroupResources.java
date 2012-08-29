/*    */ package com.jidesoft.grouper;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public class GroupResources
/*    */ {
/*    */   static final String BASENAME = "com.jidesoft.grouper.group";
/* 15 */   static final ResourceBundle RB = ResourceBundle.getBundle("com.jidesoft.grouper.group");
/*    */ 
/*    */   public static ResourceBundle getResourceBundle(Locale locale) {
/* 18 */     return ResourceBundle.getBundle("com.jidesoft.grouper.group", locale);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.grouper.GroupResources
 * JD-Core Version:    0.6.0
 */
/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ class Resource
/*    */ {
/*    */   static final String BASENAME = "com.jidesoft.converter.converter";
/*  9 */   static final ResourceBundle RB = ResourceBundle.getBundle("com.jidesoft.converter.converter");
/*    */ 
/*    */   public static ResourceBundle getResourceBundle(Locale locale) {
/* 12 */     return ResourceBundle.getBundle("com.jidesoft.converter.converter", locale);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.Resource
 * JD-Core Version:    0.6.0
 */
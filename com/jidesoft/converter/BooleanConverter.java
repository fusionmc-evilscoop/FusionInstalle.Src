/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public class BooleanConverter
/*    */   implements ObjectConverter
/*    */ {
/*    */   public String toString(Object object, ConverterContext context)
/*    */   {
/* 19 */     if (Boolean.FALSE.equals(object)) {
/* 20 */       return getFalse();
/*    */     }
/* 22 */     if (Boolean.TRUE.equals(object)) {
/* 23 */       return getTrue();
/*    */     }
/*    */ 
/* 26 */     return getNull();
/*    */   }
/*    */ 
/*    */   public boolean supportToString(Object object, ConverterContext context)
/*    */   {
/* 31 */     return true;
/*    */   }
/*    */ 
/*    */   public Object fromString(String string, ConverterContext context) {
/* 35 */     if (string.equalsIgnoreCase(getTrue())) {
/* 36 */       return Boolean.TRUE;
/*    */     }
/*    */ 
/* 39 */     if (string.equalsIgnoreCase("true")) {
/* 40 */       return Boolean.TRUE;
/*    */     }
/* 42 */     if (string.equalsIgnoreCase(getFalse())) {
/* 43 */       return Boolean.FALSE;
/*    */     }
/*    */ 
/* 46 */     if (string.equalsIgnoreCase("false")) {
/* 47 */       return Boolean.FALSE;
/*    */     }
/*    */ 
/* 50 */     return null;
/*    */   }
/*    */ 
/*    */   public boolean supportFromString(String string, ConverterContext context)
/*    */   {
/* 55 */     return true;
/*    */   }
/*    */ 
/*    */   protected String getTrue()
/*    */   {
/* 65 */     String s = Resource.getResourceBundle(Locale.getDefault()).getString("Boolean.true");
/* 66 */     return s != null ? s.trim() : s;
/*    */   }
/*    */ 
/*    */   protected String getFalse()
/*    */   {
/* 76 */     String s = Resource.getResourceBundle(Locale.getDefault()).getString("Boolean.false");
/* 77 */     return s != null ? s.trim() : s;
/*    */   }
/*    */ 
/*    */   protected String getNull()
/*    */   {
/* 87 */     return "";
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.BooleanConverter
 * JD-Core Version:    0.6.0
 */
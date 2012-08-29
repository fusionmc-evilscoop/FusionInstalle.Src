/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ import java.awt.GraphicsEnvironment;
/*    */ 
/*    */ public class FontNameConverter
/*    */   implements ObjectConverter
/*    */ {
/* 19 */   public static ConverterContext CONTEXT = new ConverterContext("FontName");
/*    */ 
/*    */   public String toString(Object object, ConverterContext context) {
/* 22 */     if (object == null) {
/* 23 */       return "";
/*    */     }
/*    */ 
/* 26 */     String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
/* 27 */     for (String fontName : fontNames) {
/* 28 */       if (fontName.equals(object)) {
/* 29 */         return fontName;
/*    */       }
/*    */     }
/* 32 */     return "";
/*    */   }
/*    */ 
/*    */   public boolean supportToString(Object object, ConverterContext context)
/*    */   {
/* 37 */     return true;
/*    */   }
/*    */ 
/*    */   public Object fromString(String string, ConverterContext context) {
/* 41 */     if (string.length() == 0) {
/* 42 */       return null;
/*    */     }
/*    */ 
/* 45 */     String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
/* 46 */     for (String fontName : fontNames) {
/* 47 */       if (fontName.equals(string)) {
/* 48 */         return string;
/*    */       }
/*    */     }
/* 51 */     return null;
/*    */   }
/*    */ 
/*    */   public boolean supportFromString(String string, ConverterContext context)
/*    */   {
/* 56 */     return true;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.FontNameConverter
 * JD-Core Version:    0.6.0
 */
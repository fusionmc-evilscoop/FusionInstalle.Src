/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ import java.awt.Font;
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public class FontConverter
/*    */   implements ObjectConverter
/*    */ {
/*    */   public String toString(Object object, ConverterContext context)
/*    */   {
/* 17 */     if ((object instanceof Font)) {
/* 18 */       Font font = (Font)object;
/* 19 */       return font.getName() + ", " + getResourceString(font.getStyle()) + ", " + font.getSize();
/*    */     }
/*    */ 
/* 22 */     return null;
/*    */   }
/*    */ 
/*    */   protected String getResourceString(int style)
/*    */   {
/* 27 */     ResourceBundle resourceBundle = Resource.getResourceBundle(Locale.getDefault());
/* 28 */     switch (style) {
/*    */     case 0:
/* 30 */       return resourceBundle.getString("Font.plain");
/*    */     case 1:
/* 32 */       return resourceBundle.getString("Font.bold");
/*    */     case 2:
/* 34 */       return resourceBundle.getString("Font.italic");
/*    */     case 3:
/* 36 */       return resourceBundle.getString("Font.boldItalic");
/*    */     }
/* 38 */     return "";
/*    */   }
/*    */ 
/*    */   protected int getStyleValue(String style)
/*    */   {
/* 43 */     ResourceBundle resourceBundle = Resource.getResourceBundle(Locale.getDefault());
/* 44 */     if (resourceBundle.getString("Font.italic").equalsIgnoreCase(style)) {
/* 45 */       return 2;
/*    */     }
/* 47 */     if (resourceBundle.getString("Font.bold").equalsIgnoreCase(style)) {
/* 48 */       return 1;
/*    */     }
/* 50 */     if (resourceBundle.getString("Font.boldItalic").equalsIgnoreCase(style)) {
/* 51 */       return 3;
/*    */     }
/*    */ 
/* 54 */     return 0;
/*    */   }
/*    */ 
/*    */   public boolean supportToString(Object object, ConverterContext context)
/*    */   {
/* 59 */     return true;
/*    */   }
/*    */ 
/*    */   public Object fromString(String string, ConverterContext context) {
/* 63 */     if ((string == null) || (string.length() == 0)) {
/* 64 */       return null;
/*    */     }
/*    */ 
/* 67 */     String fontFace = null;
/* 68 */     int style = 0;
/* 69 */     int size = 10;
/*    */ 
/* 71 */     String[] strings = string.split(",");
/* 72 */     if (strings.length > 0) {
/* 73 */       fontFace = strings[0].trim();
/*    */     }
/* 75 */     if (strings.length > 1) {
/* 76 */       style = getStyleValue(strings[1].trim());
/*    */     }
/* 78 */     if (strings.length > 2) {
/*    */       try {
/* 80 */         double s = Double.parseDouble(strings[2].trim());
/* 81 */         size = (int)s;
/*    */       }
/*    */       catch (NumberFormatException e)
/*    */       {
/*    */       }
/*    */     }
/*    */ 
/* 88 */     if (fontFace != null) {
/* 89 */       return new Font(fontFace, style, size);
/*    */     }
/*    */ 
/* 92 */     return null;
/*    */   }
/*    */ 
/*    */   public boolean supportFromString(String string, ConverterContext context)
/*    */   {
/* 98 */     return true;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.FontConverter
 * JD-Core Version:    0.6.0
 */
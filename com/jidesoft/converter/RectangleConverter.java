/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ import java.awt.Rectangle;
/*    */ 
/*    */ public class RectangleConverter extends ArrayConverter
/*    */ {
/*    */   RectangleConverter()
/*    */   {
/* 15 */     super("; ", 4, Integer.class);
/*    */   }
/*    */ 
/*    */   public String toString(Object object, ConverterContext context) {
/* 19 */     if ((object instanceof Rectangle)) {
/* 20 */       Rectangle rectangle = (Rectangle)object;
/* 21 */       return arrayToString(new Object[] { Integer.valueOf(rectangle.x), Integer.valueOf(rectangle.y), Integer.valueOf(rectangle.width), Integer.valueOf(rectangle.height) }, context);
/*    */     }
/*    */ 
/* 26 */     return "";
/*    */   }
/*    */ 
/*    */   public boolean supportToString(Object object, ConverterContext context)
/*    */   {
/* 31 */     return true;
/*    */   }
/*    */ 
/*    */   public Object fromString(String string, ConverterContext context) {
/* 35 */     if ((string == null) || (string.trim().length() == 0)) {
/* 36 */       return null;
/*    */     }
/* 38 */     Object[] objects = arrayFromString(string, context);
/* 39 */     int x = 0; int y = 0; int w = 0; int h = 0;
/* 40 */     if ((objects.length >= 1) && ((objects[0] instanceof Integer))) {
/* 41 */       x = ((Integer)objects[0]).intValue();
/*    */     }
/* 43 */     if ((objects.length >= 2) && ((objects[1] instanceof Integer))) {
/* 44 */       y = ((Integer)objects[1]).intValue();
/*    */     }
/* 46 */     if ((objects.length >= 3) && ((objects[2] instanceof Integer))) {
/* 47 */       w = ((Integer)objects[2]).intValue();
/*    */     }
/* 49 */     if ((objects.length >= 4) && ((objects[3] instanceof Integer))) {
/* 50 */       h = ((Integer)objects[3]).intValue();
/*    */     }
/* 52 */     return new Rectangle(x, y, w, h);
/*    */   }
/*    */ 
/*    */   public boolean supportFromString(String string, ConverterContext context) {
/* 56 */     return true;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.RectangleConverter
 * JD-Core Version:    0.6.0
 */
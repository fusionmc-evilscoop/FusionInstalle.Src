/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ import java.awt.Dimension;
/*    */ 
/*    */ public class DimensionConverter extends ArrayConverter
/*    */ {
/*    */   DimensionConverter()
/*    */   {
/* 15 */     super("; ", 2, Integer.class);
/*    */   }
/*    */ 
/*    */   public String toString(Object object, ConverterContext context) {
/* 19 */     if ((object instanceof Dimension)) {
/* 20 */       Dimension dim = (Dimension)object;
/* 21 */       return arrayToString(new Object[] { Integer.valueOf(dim.width), Integer.valueOf(dim.height) }, context);
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
/* 39 */     int x = 0; int y = 0;
/* 40 */     if ((objects.length >= 1) && ((objects[0] instanceof Integer))) {
/* 41 */       x = ((Integer)objects[0]).intValue();
/*    */     }
/* 43 */     if ((objects.length >= 2) && ((objects[1] instanceof Integer))) {
/* 44 */       y = ((Integer)objects[1]).intValue();
/*    */     }
/* 46 */     return new Dimension(x, y);
/*    */   }
/*    */ 
/*    */   public boolean supportFromString(String string, ConverterContext context) {
/* 50 */     return true;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.DimensionConverter
 * JD-Core Version:    0.6.0
 */
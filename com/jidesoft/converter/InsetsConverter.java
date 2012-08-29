/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ import java.awt.Insets;
/*    */ 
/*    */ public class InsetsConverter extends ArrayConverter
/*    */ {
/*    */   public InsetsConverter()
/*    */   {
/* 15 */     super("; ", 4, Integer.class);
/*    */   }
/*    */ 
/*    */   public InsetsConverter(String separator) {
/* 19 */     super(separator, 4, Integer.class);
/*    */   }
/*    */ 
/*    */   public String toString(Object object, ConverterContext context) {
/* 23 */     if ((object instanceof Insets)) {
/* 24 */       Insets Insets = (Insets)object;
/* 25 */       return arrayToString(new Object[] { Integer.valueOf(Insets.top), Integer.valueOf(Insets.left), Integer.valueOf(Insets.bottom), Integer.valueOf(Insets.right) }, context);
/*    */     }
/*    */ 
/* 30 */     return "";
/*    */   }
/*    */ 
/*    */   public boolean supportToString(Object object, ConverterContext context)
/*    */   {
/* 35 */     return true;
/*    */   }
/*    */ 
/*    */   public Object fromString(String string, ConverterContext context) {
/* 39 */     if ((string == null) || (string.trim().length() == 0)) {
/* 40 */       return null;
/*    */     }
/* 42 */     Object[] objects = arrayFromString(string, context);
/* 43 */     int top = 0; int left = 0; int bottom = 0; int right = 0;
/* 44 */     if ((objects.length >= 1) && ((objects[0] instanceof Integer))) {
/* 45 */       top = ((Integer)objects[0]).intValue();
/*    */     }
/* 47 */     if ((objects.length >= 2) && ((objects[1] instanceof Integer))) {
/* 48 */       left = ((Integer)objects[1]).intValue();
/*    */     }
/* 50 */     if ((objects.length >= 3) && ((objects[2] instanceof Integer))) {
/* 51 */       bottom = ((Integer)objects[2]).intValue();
/*    */     }
/* 53 */     if ((objects.length >= 4) && ((objects[3] instanceof Integer))) {
/* 54 */       right = ((Integer)objects[3]).intValue();
/*    */     }
/* 56 */     return new Insets(top, left, bottom, right);
/*    */   }
/*    */ 
/*    */   public boolean supportFromString(String string, ConverterContext context) {
/* 60 */     return true;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.InsetsConverter
 * JD-Core Version:    0.6.0
 */
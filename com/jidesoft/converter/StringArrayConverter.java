/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ public class StringArrayConverter
/*    */   implements ObjectConverter
/*    */ {
/*  4 */   public static final ConverterContext CONTEXT = new ConverterContext("StringArrayConverter");
/*  5 */   private String _separator = ";";
/*    */ 
/*    */   public StringArrayConverter()
/*    */   {
/*    */   }
/*    */ 
/*    */   public StringArrayConverter(String separator)
/*    */   {
/* 22 */     this._separator = separator;
/*    */   }
/*    */ 
/*    */   public String toString(Object object, ConverterContext context) {
/* 26 */     if (object == null) {
/* 27 */       return null;
/*    */     }
/* 29 */     if (object.getClass().isArray()) {
/* 30 */       String[] array = (String[])(String[])object;
/* 31 */       StringBuffer b = new StringBuffer();
/* 32 */       for (int i = 0; i < array.length; i++) {
/* 33 */         if (i > 0) b.append(this._separator);
/* 34 */         b.append(array[i]);
/*    */       }
/* 36 */       return b.toString();
/*    */     }
/* 38 */     return null;
/*    */   }
/*    */ 
/*    */   public boolean supportToString(Object object, ConverterContext context) {
/* 42 */     return true;
/*    */   }
/*    */ 
/*    */   public Object fromString(String string, ConverterContext context) {
/* 46 */     if (string.length() == 0) {
/* 47 */       return new String[0];
/*    */     }
/* 49 */     return string.split(this._separator);
/*    */   }
/*    */ 
/*    */   public boolean supportFromString(String string, ConverterContext context) {
/* 53 */     return true;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.StringArrayConverter
 * JD-Core Version:    0.6.0
 */
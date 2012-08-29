/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ public class FileConverter
/*    */   implements ObjectConverter
/*    */ {
/*    */   public String toString(Object object, ConverterContext context)
/*    */   {
/* 15 */     if ((object == null) || (!(object instanceof File))) {
/* 16 */       return null;
/*    */     }
/*    */ 
/* 19 */     return ((File)object).getAbsolutePath();
/*    */   }
/*    */ 
/*    */   public boolean supportToString(Object object, ConverterContext context)
/*    */   {
/* 24 */     return true;
/*    */   }
/*    */ 
/*    */   public Object fromString(String string, ConverterContext context) {
/* 28 */     if ((string == null) || (string.trim().length() == 0)) {
/* 29 */       return null;
/*    */     }
/* 31 */     return new File(string);
/*    */   }
/*    */ 
/*    */   public boolean supportFromString(String string, ConverterContext context) {
/* 35 */     return true;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.FileConverter
 * JD-Core Version:    0.6.0
 */
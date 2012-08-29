/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ public class PasswordConverter extends DefaultObjectConverter
/*    */ {
/* 18 */   public static ConverterContext CONTEXT = new ConverterContext("Password");
/* 19 */   private char _echoChar = '*';
/*    */ 
/*    */   public PasswordConverter()
/*    */   {
/*    */   }
/*    */ 
/*    */   public PasswordConverter(char echoChar)
/*    */   {
/* 30 */     this._echoChar = echoChar;
/*    */   }
/*    */ 
/*    */   public String toString(Object object, ConverterContext context)
/*    */   {
/* 35 */     if ((object instanceof char[])) {
/* 36 */       int length = ((char[])(char[])object).length;
/* 37 */       char[] chars = new char[length];
/* 38 */       Arrays.fill(chars, getEchoChar());
/* 39 */       return new String(chars);
/*    */     }
/* 41 */     if (object != null) {
/* 42 */       int length = object.toString().length();
/* 43 */       char[] chars = new char[length];
/* 44 */       Arrays.fill(chars, getEchoChar());
/* 45 */       return new String(chars);
/*    */     }
/*    */ 
/* 48 */     return "";
/*    */   }
/*    */ 
/*    */   public char getEchoChar()
/*    */   {
/* 53 */     return this._echoChar;
/*    */   }
/*    */ 
/*    */   public void setEchoChar(char echoChar) {
/* 57 */     this._echoChar = echoChar;
/*    */   }
/*    */ 
/*    */   public boolean supportFromString(String string, ConverterContext context)
/*    */   {
/* 62 */     return false;
/*    */   }
/*    */ 
/*    */   public Object fromString(String string, ConverterContext context)
/*    */   {
/* 67 */     return null;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.PasswordConverter
 * JD-Core Version:    0.6.0
 */
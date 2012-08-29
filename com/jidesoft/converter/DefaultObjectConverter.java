/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ import java.text.ParseException;
/*    */ import javax.swing.JFormattedTextField.AbstractFormatter;
/*    */ 
/*    */ public class DefaultObjectConverter
/*    */   implements ObjectConverter
/*    */ {
/*    */   public String toString(Object object, ConverterContext context)
/*    */   {
/* 34 */     if ((context != null) && ((context.getUserObject() instanceof JFormattedTextField.AbstractFormatter))) {
/*    */       try {
/* 36 */         return ((JFormattedTextField.AbstractFormatter)context.getUserObject()).valueToString(object);
/*    */       }
/*    */       catch (ParseException e)
/*    */       {
/*    */       }
/*    */     }
/* 42 */     return object == null ? "" : object.toString();
/*    */   }
/*    */ 
/*    */   public boolean supportToString(Object object, ConverterContext context) {
/* 46 */     return true;
/*    */   }
/*    */ 
/*    */   public Object fromString(String string, ConverterContext context) {
/* 50 */     if ((context != null) && ((context.getUserObject() instanceof JFormattedTextField.AbstractFormatter))) {
/*    */       try {
/* 52 */         return ((JFormattedTextField.AbstractFormatter)context.getUserObject()).stringToValue(string);
/*    */       }
/*    */       catch (ParseException e)
/*    */       {
/*    */       }
/*    */     }
/* 58 */     return string;
/*    */   }
/*    */ 
/*    */   public boolean supportFromString(String string, ConverterContext context) {
/* 62 */     return true;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.DefaultObjectConverter
 * JD-Core Version:    0.6.0
 */
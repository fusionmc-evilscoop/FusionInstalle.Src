/*    */ package com.jidesoft.spinner;
/*    */ 
/*    */ import java.awt.Point;
/*    */ import java.io.PrintStream;
/*    */ import java.text.ParseException;
/*    */ import javax.swing.JFormattedTextField.AbstractFormatter;
/*    */ import javax.swing.text.DefaultFormatter;
/*    */ 
/*    */ public class PointFormatter extends DefaultFormatter
/*    */ {
/*    */   private static JFormattedTextField.AbstractFormatter formatter;
/*    */ 
/*    */   public static synchronized JFormattedTextField.AbstractFormatter getInstance()
/*    */   {
/* 22 */     if (formatter == null) {
/* 23 */       formatter = new PointFormatter();
/*    */     }
/* 25 */     return formatter;
/*    */   }
/*    */ 
/*    */   public Object stringToValue(String text)
/*    */     throws ParseException
/*    */   {
/* 34 */     text = text.trim();
/* 35 */     if ((text.startsWith("(")) && (text.endsWith(")")))
/* 36 */       text = text.substring(1, text.length() - 1);
/*    */     try
/*    */     {
/* 39 */       String[] splition = text.split(",");
/* 40 */       return new Point(Integer.parseInt(splition[0].trim()), Integer.parseInt(splition[1].trim()));
/*    */     } catch (Exception e) {
/*    */     }
/* 43 */     throw new ParseException(text, 0);
/*    */   }
/*    */ 
/*    */   public String valueToString(Object value)
/*    */     throws ParseException
/*    */   {
/* 49 */     if ((value instanceof Point)) {
/* 50 */       Point point = (Point)value;
/* 51 */       return "(" + point.x + ", " + point.y + ")";
/*    */     }
/*    */ 
/* 54 */     return super.valueToString(value);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 59 */     Point point = new Point(5, -5);
/* 60 */     JFormattedTextField.AbstractFormatter formatter = getInstance();
/*    */     try
/*    */     {
/* 63 */       value = formatter.valueToString(point);
/*    */     }
/*    */     catch (ParseException e) {
/* 66 */       value = null;
/*    */     }
/* 68 */     System.out.println(value);
/* 69 */     String value = "(3, -3)";
/*    */     try {
/* 71 */       point = (Point)formatter.stringToValue(value);
/*    */     }
/*    */     catch (ParseException e) {
/* 74 */       point = null;
/*    */     }
/* 76 */     System.out.println(point);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.spinner.PointFormatter
 * JD-Core Version:    0.6.0
 */
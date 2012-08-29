/*    */ package com.jidesoft.utils;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class StringUtils
/*    */ {
/*    */   public static String toCamelCase(String str)
/*    */   {
/* 20 */     String firstLetter = str.substring(0, 1);
/* 21 */     String rest = str.substring(1);
/* 22 */     return firstLetter.toUpperCase() + rest.toLowerCase();
/*    */   }
/*    */ 
/*    */   public static String stringList(String prefix, String suffix, String separator, Object[] objects)
/*    */   {
/* 37 */     StringBuilder builder = new StringBuilder(prefix);
/* 38 */     for (int i = 0; i < objects.length; i++) {
/* 39 */       builder.append(objects[i].toString());
/* 40 */       if (i < objects.length - 1) {
/* 41 */         builder.append(separator);
/*    */       }
/*    */     }
/* 44 */     builder.append(suffix);
/* 45 */     return builder.toString();
/*    */   }
/*    */ 
/*    */   public static String stringList(Object[] objects) {
/* 49 */     return stringList("[", "]", ",", objects);
/*    */   }
/*    */ 
/*    */   public static String stringList(List<?> objects) {
/* 53 */     if (objects == null) {
/* 54 */       return "";
/*    */     }
/* 56 */     return stringList(objects.toArray());
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.StringUtils
 * JD-Core Version:    0.6.0
 */
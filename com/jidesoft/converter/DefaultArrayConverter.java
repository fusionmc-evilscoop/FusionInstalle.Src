/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.io.PrintStream;
/*    */ import java.lang.reflect.Array;
/*    */ 
/*    */ public class DefaultArrayConverter extends ArrayConverter
/*    */ {
/*    */   public DefaultArrayConverter(String separator, Class<?> elementClass)
/*    */   {
/* 11 */     super(separator, -1, elementClass);
/*    */   }
/*    */ 
/*    */   public String toString(Object object, ConverterContext context) {
/* 15 */     if (object == null) {
/* 16 */       return "";
/*    */     }
/*    */ 
/* 19 */     if (object.getClass().isArray())
/*    */     {
/*    */       Object[] objects;
/*    */       Object[] objects;
/* 21 */       if (getElementClass() == Object.class) {
/* 22 */         objects = (Object[])(Object[])object;
/*    */       }
/*    */       else {
/* 25 */         objects = new Object[Array.getLength(object)];
/*    */       }
/* 27 */       for (int i = 0; i < objects.length; i++) {
/* 28 */         objects[i] = Array.get(object, i);
/*    */       }
/* 30 */       return arrayToString(objects, context);
/*    */     }
/*    */ 
/* 33 */     return ObjectConverterManager.toString(object, getElementClass(), context);
/*    */   }
/*    */ 
/*    */   public boolean supportToString(Object object, ConverterContext context)
/*    */   {
/* 39 */     return true;
/*    */   }
/*    */ 
/*    */   public Object fromString(String string, ConverterContext context) {
/* 43 */     if ((string == null) || ("".equals(string))) {
/* 44 */       return new Object[0];
/*    */     }
/*    */ 
/* 47 */     Object[] objects = arrayFromString(string, context);
/* 48 */     if (objects == null) {
/* 49 */       return new Object[0];
/*    */     }
/* 51 */     Class elementClass = getElementClass();
/* 52 */     if (elementClass == Object.class) {
/* 53 */       return objects;
/*    */     }
/* 55 */     for (Object object : objects) {
/* 56 */       if ((object != null) && (!elementClass.isAssignableFrom(object.getClass()))) {
/* 57 */         return new Object[0];
/*    */       }
/*    */     }
/* 60 */     Object array = Array.newInstance(elementClass, objects.length);
/* 61 */     for (int i = 0; i < objects.length; i++) {
/* 62 */       Object object = objects[i];
/* 63 */       Array.set(array, i, object);
/*    */     }
/* 65 */     return array;
/*    */   }
/*    */ 
/*    */   public boolean supportFromString(String string, ConverterContext context)
/*    */   {
/* 70 */     return true;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) {
/* 74 */     System.out.println(new DefaultArrayConverter(";", Integer.TYPE).toString(new int[] { 2, 3, 2, 4 }, null));
/* 75 */     System.out.println(new DefaultArrayConverter(";", Integer.TYPE).fromString("2;3;2;4", null));
/*    */ 
/* 77 */     System.out.println(new DefaultArrayConverter(";", Color.class).toString(new Color[] { Color.RED, Color.YELLOW, Color.GREEN }, HexColorConverter.CONTEXT_HEX));
/* 78 */     System.out.println(new DefaultArrayConverter(";", Color.class).fromString("#FF0000;#FFFF00;#00FF00", HexColorConverter.CONTEXT_HEX));
/* 79 */     System.out.println(new DefaultArrayConverter(";", Object.class).fromString("#FF0000;#FFFF00;#00FF00", null));
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.DefaultArrayConverter
 * JD-Core Version:    0.6.0
 */
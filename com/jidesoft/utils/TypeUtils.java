/*    */ package com.jidesoft.utils;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Image;
/*    */ import java.util.Calendar;
/*    */ import java.util.Date;
/*    */ import javax.swing.Icon;
/*    */ 
/*    */ public class TypeUtils
/*    */ {
/*    */   private static final int WRAPPER_TYPE_INDEX = 0;
/*    */   private static final int PRIMITIVE_TYPE_INDEX = 1;
/*    */   private static final int PRIMITIVE_TYPE_KEY_INDEX = 2;
/* 15 */   private static final Object[][] PRIMITIVE_ARRAY_TYPES = { { Boolean.class, Boolean.TYPE, "Z" }, { Character.class, Character.TYPE, "C" }, { Byte.class, Byte.TYPE, "B" }, { Short.class, Short.TYPE, "S" }, { Integer.class, Integer.TYPE, "I" }, { Long.class, Long.TYPE, "J" }, { Float.class, Float.TYPE, "F" }, { Double.class, Double.TYPE, "D" } };
/*    */ 
/*    */   public static boolean isPrimitive(Class<?> primitive)
/*    */   {
/* 27 */     for (Object[] primitiveArrayType : PRIMITIVE_ARRAY_TYPES) {
/* 28 */       if (primitiveArrayType[1] == primitive) {
/* 29 */         return true;
/*    */       }
/*    */     }
/* 32 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean isPrimitiveWrapper(Class<?> wrapperType) {
/* 36 */     for (Object[] primitiveArrayType : PRIMITIVE_ARRAY_TYPES) {
/* 37 */       if (primitiveArrayType[0] == wrapperType) {
/* 38 */         return true;
/*    */       }
/*    */     }
/* 41 */     return false;
/*    */   }
/*    */ 
/*    */   public static Class<?> convertPrimitiveToWrapperType(Class<?> primitive) {
/* 45 */     for (Object[] primitiveArrayType : PRIMITIVE_ARRAY_TYPES) {
/* 46 */       if (primitiveArrayType[1] == primitive) {
/* 47 */         return (Class)primitiveArrayType[0];
/*    */       }
/*    */     }
/* 50 */     return primitive;
/*    */   }
/*    */ 
/*    */   public static Class<?> convertWrapperToPrimitiveType(Class<?> wrapperType) {
/* 54 */     for (Object[] primitiveArrayType : PRIMITIVE_ARRAY_TYPES) {
/* 55 */       if (primitiveArrayType[0] == wrapperType) {
/* 56 */         return (Class)primitiveArrayType[1];
/*    */       }
/*    */     }
/* 59 */     return wrapperType;
/*    */   }
/*    */ 
/*    */   public static boolean isNumericType(Class<?> type)
/*    */   {
/* 69 */     return (type != null) && ((Number.class.isAssignableFrom(type)) || (type == Double.TYPE) || (type == Integer.TYPE) || (type == Float.TYPE) || (type == Short.TYPE) || (type == Long.TYPE));
/*    */   }
/*    */ 
/*    */   public static boolean isTemporalType(Class<?> type)
/*    */   {
/* 84 */     return (type != null) && ((Date.class.isAssignableFrom(type)) || (Calendar.class.isAssignableFrom(type)) || (type == Double.TYPE) || (type == Long.TYPE));
/*    */   }
/*    */ 
/*    */   public static boolean isVisualType(Class<?> type)
/*    */   {
/* 96 */     return (type != null) && ((Color.class.isAssignableFrom(type)) || (Icon.class.isAssignableFrom(type)) || (Image.class.isAssignableFrom(type)));
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.TypeUtils
 * JD-Core Version:    0.6.0
 */
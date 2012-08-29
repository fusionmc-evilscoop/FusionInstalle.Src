/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ public class ConverterContext extends AbstractContext
/*    */ {
/* 16 */   public static ConverterContext DEFAULT_CONTEXT = new ConverterContext("");
/*    */ 
/* 20 */   public static ConverterContext DEFAULT_CONTEXT_DYNAMIC_VALUE = new ConverterContext("Dynamic Value");
/*    */   private static final long serialVersionUID = 8015351559541303641L;
/*    */ 
/*    */   public ConverterContext(String name)
/*    */   {
/* 29 */     super(name);
/*    */   }
/*    */ 
/*    */   public ConverterContext(String name, Object object)
/*    */   {
/* 39 */     super(name, object);
/*    */   }
/*    */ 
/*    */   public static boolean isArrayConverterContext(ConverterContext context)
/*    */   {
/* 51 */     return (context != null) && (context.getName() != null) && (context.getName().endsWith("[]"));
/*    */   }
/*    */ 
/*    */   public static ConverterContext getElementConverterContext(ConverterContext context)
/*    */   {
/* 61 */     if (isArrayConverterContext(context)) {
/* 62 */       return new ConverterContext(context.getName().substring(0, context.getName().length() - 2));
/*    */     }
/*    */ 
/* 65 */     return context;
/*    */   }
/*    */ 
/*    */   public static ConverterContext getArrayConverterContext(ConverterContext context)
/*    */   {
/* 76 */     if (!isArrayConverterContext(context)) {
/* 77 */       return new ConverterContext(context.getName() + "[]");
/*    */     }
/*    */ 
/* 80 */     return context;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.ConverterContext
 * JD-Core Version:    0.6.0
 */
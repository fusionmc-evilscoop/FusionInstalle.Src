/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ import java.lang.reflect.Array;
/*    */ 
/*    */ public class MultipleEnumConverter extends ArrayConverter
/*    */ {
/*    */   private EnumConverter _enumConverter;
/*    */   private transient ConverterContext _conext;
/*    */ 
/*    */   public MultipleEnumConverter(String separator, EnumConverter converter)
/*    */   {
/* 20 */     super(separator, -1, converter.getType());
/* 21 */     this._enumConverter = converter;
/*    */   }
/*    */ 
/*    */   public Class<?> getType() {
/* 25 */     return Array.newInstance(this._enumConverter.getType(), 0).getClass();
/*    */   }
/*    */ 
/*    */   public EnumConverter getEnumConverter() {
/* 29 */     return this._enumConverter;
/*    */   }
/*    */ 
/*    */   public void setEnumConverter(EnumConverter enumConverter) {
/* 33 */     this._enumConverter = enumConverter;
/*    */   }
/*    */ 
/*    */   public String toString(Object object, ConverterContext context) {
/* 37 */     if (object == null) {
/* 38 */       return "";
/*    */     }
/* 40 */     if (object.getClass().isArray()) {
/* 41 */       int length = Array.getLength(object);
/* 42 */       Object[] values = new Object[length];
/* 43 */       for (int i = 0; i < length; i++) {
/* 44 */         Object o = Array.get(object, i);
/* 45 */         values[i] = o;
/*    */       }
/* 47 */       return arrayToString(values, context);
/*    */     }
/* 49 */     return "";
/*    */   }
/*    */ 
/*    */   public boolean supportToString(Object object, ConverterContext context) {
/* 53 */     return true;
/*    */   }
/*    */ 
/*    */   public Object fromString(String string, ConverterContext context) {
/* 57 */     return arrayFromString(string, context);
/*    */   }
/*    */ 
/*    */   public boolean supportFromString(String string, ConverterContext context) {
/* 61 */     return true;
/*    */   }
/*    */ 
/*    */   protected String toString(int i, Object o, ConverterContext context)
/*    */   {
/* 66 */     return "" + o;
/*    */   }
/*    */ 
/*    */   protected Object fromString(int i, String s, ConverterContext context)
/*    */   {
/* 71 */     return this._enumConverter != null ? this._enumConverter.fromString(s, context) : s;
/*    */   }
/*    */ 
/*    */   public ConverterContext getContext()
/*    */   {
/* 83 */     if (this._conext == null) {
/* 84 */       this._conext = ConverterContext.getArrayConverterContext(this._enumConverter.getContext());
/*    */     }
/* 86 */     return this._conext;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.MultipleEnumConverter
 * JD-Core Version:    0.6.0
 */
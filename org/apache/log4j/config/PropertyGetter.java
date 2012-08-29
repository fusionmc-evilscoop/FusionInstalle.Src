/*    */ package org.apache.log4j.config;
/*    */ 
/*    */ import java.beans.BeanInfo;
/*    */ import java.beans.FeatureDescriptor;
/*    */ import java.beans.IntrospectionException;
/*    */ import java.beans.Introspector;
/*    */ import java.beans.PropertyDescriptor;
/*    */ import java.lang.reflect.Method;
/*    */ import org.apache.log4j.Priority;
/*    */ import org.apache.log4j.helpers.LogLog;
/*    */ 
/*    */ public class PropertyGetter
/*    */ {
/* 31 */   protected static final Object[] NULL_ARG = new Object[0];
/*    */   protected Object obj;
/*    */   protected PropertyDescriptor[] props;
/*    */ 
/*    */   public PropertyGetter(Object obj)
/*    */     throws IntrospectionException
/*    */   {
/* 48 */     BeanInfo bi = Introspector.getBeanInfo(obj.getClass());
/* 49 */     this.props = bi.getPropertyDescriptors();
/* 50 */     this.obj = obj;
/*    */   }
/*    */ 
/*    */   public static void getProperties(Object obj, PropertyCallback callback, String prefix)
/*    */   {
/*    */     try
/*    */     {
/* 57 */       new PropertyGetter(obj).getProperties(callback, prefix);
/*    */     } catch (IntrospectionException ex) {
/* 59 */       LogLog.error("Failed to introspect object " + obj, ex);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void getProperties(PropertyCallback callback, String prefix)
/*    */   {
/* 65 */     for (int i = 0; i < this.props.length; i++) {
/* 66 */       Method getter = this.props[i].getReadMethod();
/* 67 */       if ((getter == null) || 
/* 68 */         (!isHandledType(getter.getReturnType())))
/*    */       {
/*    */         continue;
/*    */       }
/* 72 */       String name = this.props[i].getName();
/*    */       try {
/* 74 */         Object result = getter.invoke(this.obj, NULL_ARG);
/*    */ 
/* 76 */         if (result != null)
/* 77 */           callback.foundProperty(this.obj, prefix, name, result);
/*    */       }
/*    */       catch (Exception ex) {
/* 80 */         LogLog.warn("Failed to get value of property " + name);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   protected boolean isHandledType(Class type)
/*    */   {
/* 87 */     return (String.class.isAssignableFrom(type)) || (Integer.TYPE.isAssignableFrom(type)) || (Long.TYPE.isAssignableFrom(type)) || (Boolean.TYPE.isAssignableFrom(type)) || (Priority.class.isAssignableFrom(type));
/*    */   }
/*    */ 
/*    */   public static abstract interface PropertyCallback
/*    */   {
/*    */     public abstract void foundProperty(Object paramObject1, String paramString1, String paramString2, Object paramObject2);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.config.PropertyGetter
 * JD-Core Version:    0.6.0
 */
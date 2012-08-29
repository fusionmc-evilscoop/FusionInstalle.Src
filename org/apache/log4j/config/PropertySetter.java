/*     */ package org.apache.log4j.config;
/*     */ 
/*     */ import java.beans.BeanInfo;
/*     */ import java.beans.FeatureDescriptor;
/*     */ import java.beans.IntrospectionException;
/*     */ import java.beans.Introspector;
/*     */ import java.beans.PropertyDescriptor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Properties;
/*     */ import org.apache.log4j.Appender;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ import org.apache.log4j.helpers.OptionConverter;
/*     */ import org.apache.log4j.spi.OptionHandler;
/*     */ 
/*     */ public class PropertySetter
/*     */ {
/*     */   protected Object obj;
/*     */   protected PropertyDescriptor[] props;
/*     */ 
/*     */   public PropertySetter(Object obj)
/*     */   {
/*  65 */     this.obj = obj;
/*     */   }
/*     */ 
/*     */   protected void introspect()
/*     */   {
/*     */     try
/*     */     {
/*  75 */       BeanInfo bi = Introspector.getBeanInfo(this.obj.getClass());
/*  76 */       this.props = bi.getPropertyDescriptors();
/*     */     } catch (IntrospectionException ex) {
/*  78 */       LogLog.error("Failed to introspect " + this.obj + ": " + ex.getMessage());
/*  79 */       this.props = new PropertyDescriptor[0];
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void setProperties(Object obj, Properties properties, String prefix)
/*     */   {
/*  96 */     new PropertySetter(obj).setProperties(properties, prefix);
/*     */   }
/*     */ 
/*     */   public void setProperties(Properties properties, String prefix)
/*     */   {
/* 108 */     int len = prefix.length();
/*     */ 
/* 110 */     for (Enumeration e = properties.propertyNames(); e.hasMoreElements(); ) {
/* 111 */       String key = (String)e.nextElement();
/*     */ 
/* 114 */       if (!key.startsWith(prefix))
/*     */       {
/*     */         continue;
/*     */       }
/* 118 */       if (key.indexOf('.', len + 1) > 0)
/*     */       {
/*     */         continue;
/*     */       }
/*     */ 
/* 124 */       String value = OptionConverter.findAndSubst(key, properties);
/* 125 */       key = key.substring(len);
/* 126 */       if (("layout".equals(key)) && ((this.obj instanceof Appender))) {
/*     */         continue;
/*     */       }
/* 129 */       setProperty(key, value);
/*     */     }
/*     */ 
/* 132 */     activate();
/*     */   }
/*     */ 
/*     */   public void setProperty(String name, String value)
/*     */   {
/* 152 */     if (value == null) return;
/*     */ 
/* 154 */     name = Introspector.decapitalize(name);
/* 155 */     PropertyDescriptor prop = getPropertyDescriptor(name);
/*     */ 
/* 159 */     if (prop == null)
/* 160 */       LogLog.warn("No such property [" + name + "] in " + this.obj.getClass().getName() + ".");
/*     */     else
/*     */       try
/*     */       {
/* 164 */         setProperty(prop, name, value);
/*     */       } catch (PropertySetterException ex) {
/* 166 */         LogLog.warn("Failed to set property [" + name + "] to value \"" + value + "\". ", ex.rootCause);
/*     */       }
/*     */   }
/*     */ 
/*     */   public void setProperty(PropertyDescriptor prop, String name, String value)
/*     */     throws PropertySetterException
/*     */   {
/* 183 */     Method setter = prop.getWriteMethod();
/* 184 */     if (setter == null) {
/* 185 */       throw new PropertySetterException("No setter for property [" + name + "].");
/*     */     }
/* 187 */     Class[] paramTypes = setter.getParameterTypes();
/* 188 */     if (paramTypes.length != 1)
/* 189 */       throw new PropertySetterException("#params for setter != 1");
/*     */     Object arg;
/*     */     try
/*     */     {
/* 194 */       arg = convertArg(value, paramTypes[0]);
/*     */     } catch (Throwable t) {
/* 196 */       throw new PropertySetterException("Conversion to type [" + paramTypes[0] + "] failed. Reason: " + t);
/*     */     }
/*     */ 
/* 199 */     if (arg == null) {
/* 200 */       throw new PropertySetterException("Conversion to type [" + paramTypes[0] + "] failed.");
/*     */     }
/*     */ 
/* 203 */     LogLog.debug("Setting property [" + name + "] to [" + arg + "].");
/*     */     try {
/* 205 */       setter.invoke(this.obj, new Object[] { arg });
/*     */     } catch (Exception ex) {
/* 207 */       throw new PropertySetterException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Object convertArg(String val, Class type)
/*     */   {
/* 218 */     if (val == null) {
/* 219 */       return null;
/*     */     }
/* 221 */     String v = val.trim();
/* 222 */     if (String.class.isAssignableFrom(type))
/* 223 */       return val;
/* 224 */     if (Integer.TYPE.isAssignableFrom(type))
/* 225 */       return new Integer(v);
/* 226 */     if (Long.TYPE.isAssignableFrom(type))
/* 227 */       return new Long(v);
/* 228 */     if (Boolean.TYPE.isAssignableFrom(type)) {
/* 229 */       if ("true".equalsIgnoreCase(v))
/* 230 */         return Boolean.TRUE;
/* 231 */       if ("false".equalsIgnoreCase(v))
/* 232 */         return Boolean.FALSE;
/*     */     }
/* 234 */     else if (Priority.class.isAssignableFrom(type)) {
/* 235 */       return OptionConverter.toLevel(v, Level.DEBUG);
/*     */     }
/* 237 */     return null;
/*     */   }
/*     */ 
/*     */   protected PropertyDescriptor getPropertyDescriptor(String name)
/*     */   {
/* 243 */     if (this.props == null) introspect();
/*     */ 
/* 245 */     for (int i = 0; i < this.props.length; i++) {
/* 246 */       if (name.equals(this.props[i].getName())) {
/* 247 */         return this.props[i];
/*     */       }
/*     */     }
/* 250 */     return null;
/*     */   }
/*     */ 
/*     */   public void activate()
/*     */   {
/* 255 */     if ((this.obj instanceof OptionHandler))
/* 256 */       ((OptionHandler)this.obj).activateOptions();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.config.PropertySetter
 * JD-Core Version:    0.6.0
 */
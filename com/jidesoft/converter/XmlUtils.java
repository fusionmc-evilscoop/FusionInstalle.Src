/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ import com.jidesoft.swing.JideSwingUtilities;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.Modifier;
/*    */ import java.util.HashMap;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import org.w3c.dom.Element;
/*    */ import org.w3c.dom.NamedNodeMap;
/*    */ import org.w3c.dom.Node;
/*    */ 
/*    */ public class XmlUtils
/*    */ {
/* 24 */   private static final Pattern mutatorPattern = Pattern.compile("^set([A-Z0-9_][A-Za-z0-9_]*)$");
/*    */   private static final int MUTATOR = 2;
/*    */   private static final int ANYOTHER = 0;
/*    */ 
/*    */   public static void readElement(Object object, Element element)
/*    */   {
/* 31 */     if (object == null) {
/* 32 */       return;
/*    */     }
/*    */ 
/* 35 */     NamedNodeMap map = element.getAttributes();
/* 36 */     HashMap properties = new HashMap();
/* 37 */     for (int i = 0; i < map.getLength(); i++) {
/* 38 */       Node node = map.item(i);
/* 39 */       String name = node.getNodeName();
/* 40 */       properties.put(name, node.getNodeValue());
/*    */     }
/*    */ 
/* 43 */     Method[] methods = object.getClass().getMethods();
/* 44 */     for (Method method : methods)
/*    */     {
/* 46 */       int methodType = 0;
/* 47 */       Class type = null;
/*    */ 
/* 49 */       if ((!Modifier.isPublic(method.getModifiers())) || (Modifier.isStatic(method.getModifiers())))
/*    */         continue;
/*    */       Matcher matcher;
/* 53 */       if (((matcher = mutatorPattern.matcher(method.getName())).matches()) && 
/* 54 */         (method.getReturnType() == Void.TYPE) && (method.getParameterTypes().length == 1)) {
/* 55 */         methodType = 2;
/* 56 */         type = method.getParameterTypes()[0];
/*    */       }
/*    */ 
/* 60 */       if (methodType == 2) {
/* 61 */         String name = matcher.group(1);
/* 62 */         if (name.equals("Class"))
/*    */           continue;
/* 64 */         name = name.substring(0, 1).toLowerCase() + name.substring(1);
/* 65 */         Object value = properties.get(name);
/* 66 */         if (value == null) {
/*    */           continue;
/*    */         }
/*    */         try
/*    */         {
/* 71 */           method.invoke(object, new Object[] { ObjectConverterManager.fromString((String)value, type) });
/*    */         }
/*    */         catch (IllegalAccessException e) {
/* 74 */           throw new RuntimeException(e);
/*    */         }
/*    */         catch (InvocationTargetException e) {
/* 77 */           JideSwingUtilities.ignoreException(e);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.XmlUtils
 * JD-Core Version:    0.6.0
 */
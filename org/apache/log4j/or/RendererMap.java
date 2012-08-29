/*     */ package org.apache.log4j.or;
/*     */ 
/*     */ import java.util.Hashtable;
/*     */ import org.apache.log4j.helpers.Loader;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ import org.apache.log4j.helpers.OptionConverter;
/*     */ import org.apache.log4j.spi.RendererSupport;
/*     */ 
/*     */ public class RendererMap
/*     */ {
/*     */   Hashtable map;
/*  34 */   static ObjectRenderer defaultRenderer = new DefaultRenderer();
/*     */ 
/*     */   public RendererMap()
/*     */   {
/*  38 */     this.map = new Hashtable();
/*     */   }
/*     */ 
/*     */   public static void addRenderer(RendererSupport repository, String renderedClassName, String renderingClassName)
/*     */   {
/*  48 */     LogLog.debug("Rendering class: [" + renderingClassName + "], Rendered class: [" + renderedClassName + "].");
/*     */ 
/*  50 */     ObjectRenderer renderer = (ObjectRenderer)OptionConverter.instantiateByClassName(renderingClassName, ObjectRenderer.class, null);
/*     */ 
/*  54 */     if (renderer == null) {
/*  55 */       LogLog.error("Could not instantiate renderer [" + renderingClassName + "].");
/*  56 */       return;
/*     */     }
/*     */     try {
/*  59 */       Class renderedClass = Loader.loadClass(renderedClassName);
/*  60 */       repository.setRenderer(renderedClass, renderer);
/*     */     } catch (ClassNotFoundException e) {
/*  62 */       LogLog.error("Could not find class [" + renderedClassName + "].", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String findAndRender(Object o)
/*     */   {
/*  76 */     if (o == null) {
/*  77 */       return null;
/*     */     }
/*  79 */     return get(o.getClass()).doRender(o);
/*     */   }
/*     */ 
/*     */   public ObjectRenderer get(Object o)
/*     */   {
/*  88 */     if (o == null) {
/*  89 */       return null;
/*     */     }
/*  91 */     return get(o.getClass());
/*     */   }
/*     */ 
/*     */   public ObjectRenderer get(Class clazz)
/*     */   {
/* 147 */     ObjectRenderer r = null;
/* 148 */     for (Class c = clazz; c != null; c = c.getSuperclass())
/*     */     {
/* 150 */       r = (ObjectRenderer)this.map.get(c);
/* 151 */       if (r != null) {
/* 152 */         return r;
/*     */       }
/* 154 */       r = searchInterfaces(c);
/* 155 */       if (r != null)
/* 156 */         return r;
/*     */     }
/* 158 */     return defaultRenderer;
/*     */   }
/*     */ 
/*     */   ObjectRenderer searchInterfaces(Class c)
/*     */   {
/* 164 */     ObjectRenderer r = (ObjectRenderer)this.map.get(c);
/* 165 */     if (r != null) {
/* 166 */       return r;
/*     */     }
/* 168 */     Class[] ia = c.getInterfaces();
/* 169 */     for (int i = 0; i < ia.length; i++) {
/* 170 */       r = searchInterfaces(ia[i]);
/* 171 */       if (r != null) {
/* 172 */         return r;
/*     */       }
/*     */     }
/* 175 */     return null;
/*     */   }
/*     */ 
/*     */   public ObjectRenderer getDefaultRenderer()
/*     */   {
/* 181 */     return defaultRenderer;
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 187 */     this.map.clear();
/*     */   }
/*     */ 
/*     */   public void put(Class clazz, ObjectRenderer or)
/*     */   {
/* 195 */     this.map.put(clazz, or);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.or.RendererMap
 * JD-Core Version:    0.6.0
 */
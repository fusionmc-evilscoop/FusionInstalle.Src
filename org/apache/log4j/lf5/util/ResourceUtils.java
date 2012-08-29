/*     */ package org.apache.log4j.lf5.util;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ 
/*     */ public class ResourceUtils
/*     */ {
/*     */   public static InputStream getResourceAsStream(Object object, Resource resource)
/*     */   {
/*  72 */     ClassLoader loader = object.getClass().getClassLoader();
/*     */ 
/*  74 */     InputStream in = null;
/*     */ 
/*  76 */     if (loader != null)
/*  77 */       in = loader.getResourceAsStream(resource.getName());
/*     */     else {
/*  79 */       in = ClassLoader.getSystemResourceAsStream(resource.getName());
/*     */     }
/*     */ 
/*  82 */     return in;
/*     */   }
/*     */ 
/*     */   public static URL getResourceAsURL(Object object, Resource resource)
/*     */   {
/* 101 */     ClassLoader loader = object.getClass().getClassLoader();
/*     */ 
/* 103 */     URL url = null;
/*     */ 
/* 105 */     if (loader != null)
/* 106 */       url = loader.getResource(resource.getName());
/*     */     else {
/* 108 */       url = ClassLoader.getSystemResource(resource.getName());
/*     */     }
/*     */ 
/* 111 */     return url;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.util.ResourceUtils
 * JD-Core Version:    0.6.0
 */
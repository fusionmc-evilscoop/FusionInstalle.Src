/*     */ package org.apache.log4j.lf5.util;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ 
/*     */ public class Resource
/*     */ {
/*     */   protected String _name;
/*     */ 
/*     */   public Resource()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Resource(String name)
/*     */   {
/*  62 */     this._name = name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/*  83 */     this._name = name;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  93 */     return this._name;
/*     */   }
/*     */ 
/*     */   public InputStream getInputStream()
/*     */   {
/* 104 */     InputStream in = ResourceUtils.getResourceAsStream(this, this);
/*     */ 
/* 106 */     return in;
/*     */   }
/*     */ 
/*     */   public InputStreamReader getInputStreamReader()
/*     */   {
/* 117 */     InputStream in = ResourceUtils.getResourceAsStream(this, this);
/*     */ 
/* 119 */     if (in == null) {
/* 120 */       return null;
/*     */     }
/*     */ 
/* 123 */     InputStreamReader reader = new InputStreamReader(in);
/*     */ 
/* 125 */     return reader;
/*     */   }
/*     */ 
/*     */   public URL getURL()
/*     */   {
/* 134 */     return ResourceUtils.getResourceAsURL(this, this);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.util.Resource
 * JD-Core Version:    0.6.0
 */
/*     */ package com.jidesoft.converter;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class AbstractContext
/*     */   implements Serializable
/*     */ {
/*     */   private String _name;
/*     */   private Object _userObject;
/*     */ 
/*     */   public AbstractContext(String name)
/*     */   {
/*  28 */     this._name = name;
/*     */   }
/*     */ 
/*     */   public AbstractContext(String name, Object object)
/*     */   {
/*  38 */     this._name = name;
/*  39 */     this._userObject = object;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  48 */     return this._name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/*  57 */     this._name = name;
/*     */   }
/*     */ 
/*     */   public Object getUserObject()
/*     */   {
/*  66 */     return this._userObject;
/*     */   }
/*     */ 
/*     */   public void setUserObject(Object userObject)
/*     */   {
/*  75 */     this._userObject = userObject;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/*  86 */     if (this == o) return true;
/*  87 */     if (!(o instanceof AbstractContext)) return false;
/*     */ 
/*  89 */     AbstractContext abstractContext = (AbstractContext)o;
/*     */ 
/*  91 */     return this._name != null ? this._name.equals(abstractContext._name) : abstractContext._name == null;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  96 */     return this._name != null ? this._name.hashCode() : 0;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 101 */     return getName();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.AbstractContext
 * JD-Core Version:    0.6.0
 */
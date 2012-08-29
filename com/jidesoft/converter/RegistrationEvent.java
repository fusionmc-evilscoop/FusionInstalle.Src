/*     */ package com.jidesoft.converter;
/*     */ 
/*     */ import java.util.EventObject;
/*     */ 
/*     */ public class RegistrationEvent extends EventObject
/*     */ {
/*     */   public static final int REGISTRATION_EVENT_FIRST = 3399;
/*     */   public static final int REGISTRATION_EVENT_LAST = 3402;
/*     */   public static final int REGISTRATION_ADDED = 3399;
/*     */   public static final int REGISTRATION_REMOVED = 3400;
/*     */   public static final int REGISTRATION_CLEARED = 3401;
/*     */   private int _id;
/*     */   private Object _object;
/*     */   private Object _context;
/*     */   private Object _key;
/*     */ 
/*     */   public RegistrationEvent(Object source, int id)
/*     */   {
/*  53 */     super(source);
/*  54 */     if (id != 3401) {
/*  55 */       throw new IllegalArgumentException("This constructor is only for REGISTRATION_CLEARED event.");
/*     */     }
/*  57 */     this._id = id;
/*     */   }
/*     */ 
/*     */   public RegistrationEvent(Object source, int id, Object object, Object key, Object context)
/*     */   {
/*  67 */     super(source);
/*  68 */     this._id = id;
/*  69 */     this._object = object;
/*  70 */     this._context = context;
/*  71 */     this._key = key;
/*     */   }
/*     */ 
/*     */   public Object getKey() {
/*  75 */     return this._key;
/*     */   }
/*     */ 
/*     */   public Object getContext() {
/*  79 */     return this._context;
/*     */   }
/*     */ 
/*     */   public Object getObject() {
/*  83 */     return this._object;
/*     */   }
/*     */ 
/*     */   public int getId() {
/*  87 */     return this._id;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*     */     String action;
/*  93 */     switch (getId()) {
/*     */     case 3399:
/*  95 */       action = "ADDED ";
/*  96 */       break;
/*     */     case 3400:
/*  98 */       action = "REMOVED ";
/*  99 */       break;
/*     */     case 3401:
/* 101 */       action = "CLEARED ";
/* 102 */       break;
/*     */     default:
/* 104 */       action = "UNKNOWN " + getId() + " ";
/*     */     }
/*     */ 
/* 107 */     return action + "{key = " + getKey() + "; context = " + getContext() + "; object = " + getObject();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.RegistrationEvent
 * JD-Core Version:    0.6.0
 */
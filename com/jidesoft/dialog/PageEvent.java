/*     */ package com.jidesoft.dialog;
/*     */ 
/*     */ import java.util.EventObject;
/*     */ 
/*     */ public class PageEvent extends EventObject
/*     */ {
/*     */   public static final int PAGE_EVENT_FIRST = 3199;
/*     */   public static final int PAGE_EVENT_LAST = 3203;
/*     */   public static final int PAGE_OPENED = 3199;
/*     */   public static final int PAGE_CLOSING = 3200;
/*     */   public static final int PAGE_CLOSED = 3201;
/*     */   private int _id;
/*     */ 
/*     */   public PageEvent(Object source, int id)
/*     */   {
/*  56 */     super(source);
/*  57 */     this._id = id;
/*     */   }
/*     */ 
/*     */   public int getID()
/*     */   {
/*  67 */     return this._id;
/*     */   }
/*     */ 
/*     */   public void setID(int id)
/*     */   {
/*  76 */     this._id = id;
/*     */   }
/*     */ 
/*     */   public String paramString()
/*     */   {
/*     */     String typeStr;
/*  86 */     switch (getID()) {
/*     */     case 3199:
/*  88 */       typeStr = "PAGE_OPENED";
/*  89 */       break;
/*     */     case 3200:
/*  91 */       typeStr = "PAGE_CLOSING";
/*  92 */       break;
/*     */     case 3201:
/*  94 */       typeStr = "PAGE_CLOSED";
/*  95 */       break;
/*     */     default:
/*  97 */       typeStr = "PAGE_EVENT_UNKNOWN";
/*     */     }
/*  99 */     return typeStr;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 104 */     return "PageEvent{id=" + paramString() + "}";
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.dialog.PageEvent
 * JD-Core Version:    0.6.0
 */
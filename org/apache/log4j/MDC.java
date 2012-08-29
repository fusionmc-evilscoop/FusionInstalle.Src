/*     */ package org.apache.log4j;
/*     */ 
/*     */ import java.util.Hashtable;
/*     */ import org.apache.log4j.helpers.Loader;
/*     */ import org.apache.log4j.helpers.ThreadLocalMap;
/*     */ 
/*     */ public class MDC
/*     */ {
/*  44 */   static final MDC mdc = new MDC();
/*     */   static final int HT_SIZE = 7;
/*     */   boolean java1;
/*     */   Object tlm;
/*     */ 
/*     */   private MDC()
/*     */   {
/*  54 */     this.java1 = Loader.isJava1();
/*  55 */     if (!this.java1)
/*  56 */       this.tlm = new ThreadLocalMap();
/*     */   }
/*     */ 
/*     */   public static void put(String key, Object o)
/*     */   {
/*  72 */     mdc.put0(key, o);
/*     */   }
/*     */ 
/*     */   public static Object get(String key)
/*     */   {
/*  83 */     return mdc.get0(key);
/*     */   }
/*     */ 
/*     */   public static void remove(String key)
/*     */   {
/*  94 */     mdc.remove0(key);
/*     */   }
/*     */ 
/*     */   public static Hashtable getContext()
/*     */   {
/* 103 */     return mdc.getContext0();
/*     */   }
/*     */ 
/*     */   private void put0(String key, Object o)
/*     */   {
/* 109 */     if (this.java1) {
/* 110 */       return;
/*     */     }
/* 112 */     Hashtable ht = (Hashtable)((ThreadLocalMap)this.tlm).get();
/* 113 */     if (ht == null) {
/* 114 */       ht = new Hashtable(7);
/* 115 */       ((ThreadLocalMap)this.tlm).set(ht);
/*     */     }
/* 117 */     ht.put(key, o);
/*     */   }
/*     */ 
/*     */   private Object get0(String key)
/*     */   {
/* 123 */     if (this.java1) {
/* 124 */       return null;
/*     */     }
/* 126 */     Hashtable ht = (Hashtable)((ThreadLocalMap)this.tlm).get();
/* 127 */     if ((ht != null) && (key != null)) {
/* 128 */       return ht.get(key);
/*     */     }
/* 130 */     return null;
/*     */   }
/*     */ 
/*     */   private void remove0(String key)
/*     */   {
/* 137 */     if (!this.java1) {
/* 138 */       Hashtable ht = (Hashtable)((ThreadLocalMap)this.tlm).get();
/* 139 */       if (ht != null)
/* 140 */         ht.remove(key);
/*     */     }
/*     */   }
/*     */ 
/*     */   private Hashtable getContext0()
/*     */   {
/* 148 */     if (this.java1) {
/* 149 */       return null;
/*     */     }
/* 151 */     return (Hashtable)((ThreadLocalMap)this.tlm).get();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.MDC
 * JD-Core Version:    0.6.0
 */
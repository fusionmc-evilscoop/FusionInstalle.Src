/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ class SoftHashMap<K, V> extends AbstractMap<K, V>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2456984612468446907L;
/*  27 */   private final Map<K, SoftReference<V>> hash = new HashMap();
/*     */ 
/*  29 */   private final Map<SoftReference<V>, K> reverseLookup = new HashMap();
/*     */ 
/*  34 */   private final ReferenceQueue<V> queue = new ReferenceQueue();
/*     */ 
/*     */   public V get(Object key)
/*     */   {
/*  38 */     expungeStaleEntries();
/*  39 */     Object result = null;
/*     */ 
/*  41 */     SoftReference soft_ref = (SoftReference)this.hash.get(key);
/*  42 */     if (soft_ref != null)
/*     */     {
/*  45 */       result = soft_ref.get();
/*  46 */       if (result == null)
/*     */       {
/*  49 */         this.hash.remove(key);
/*  50 */         this.reverseLookup.remove(soft_ref);
/*     */       }
/*     */     }
/*  53 */     return result;
/*     */   }
/*     */ 
/*     */   private void expungeStaleEntries()
/*     */   {
/*     */     Reference sv;
/*  58 */     while ((sv = this.queue.poll()) != null)
/*  59 */       this.hash.remove(this.reverseLookup.remove(sv));
/*     */   }
/*     */ 
/*     */   public V put(K key, V value)
/*     */   {
/*  65 */     expungeStaleEntries();
/*  66 */     SoftReference soft_ref = new SoftReference(value, this.queue);
/*  67 */     this.reverseLookup.put(soft_ref, key);
/*  68 */     SoftReference result = (SoftReference)this.hash.put(key, soft_ref);
/*  69 */     if (result == null)
/*  70 */       return null;
/*  71 */     return result.get();
/*     */   }
/*     */ 
/*     */   public V remove(Object key)
/*     */   {
/*  76 */     expungeStaleEntries();
/*  77 */     SoftReference result = (SoftReference)this.hash.remove(key);
/*  78 */     if (result == null)
/*  79 */       return null;
/*  80 */     return result.get();
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/*  85 */     this.hash.clear();
/*  86 */     this.reverseLookup.clear();
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/*  91 */     expungeStaleEntries();
/*  92 */     return this.hash.size();
/*     */   }
/*     */ 
/*     */   public Set<Map.Entry<K, V>> entrySet()
/*     */   {
/* 101 */     expungeStaleEntries();
/* 102 */     Set result = new LinkedHashSet();
/* 103 */     for (Map.Entry entry : this.hash.entrySet()) {
/* 104 */       Object value = ((SoftReference)entry.getValue()).get();
/* 105 */       if (value != null)
/* 106 */         result.add(new Map.Entry(entry, value) {
/*     */           public K getKey() {
/* 108 */             return this.val$entry.getKey();
/*     */           }
/*     */ 
/*     */           public V getValue() {
/* 112 */             return this.val$value;
/*     */           }
/*     */ 
/*     */           public V setValue(V v) {
/* 116 */             this.val$entry.setValue(new SoftReference(v, SoftHashMap.this.queue));
/* 117 */             return this.val$value;
/*     */           }
/*     */         });
/*     */     }
/* 122 */     return result;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.SoftHashMap
 * JD-Core Version:    0.6.0
 */
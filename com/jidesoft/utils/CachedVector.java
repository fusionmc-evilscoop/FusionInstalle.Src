/*     */ package com.jidesoft.utils;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class CachedVector<E> extends Vector<E>
/*     */ {
/*     */   private Map<Object, Integer> _indexCache;
/*  12 */   private boolean _lazyCaching = false;
/*     */ 
/*     */   public CachedVector() {
/*     */   }
/*     */ 
/*     */   public CachedVector(Collection<? extends E> c) {
/*  18 */     super(c);
/*  19 */     if (!isLazyCaching())
/*  20 */       cacheAll();
/*     */   }
/*     */ 
/*     */   public CachedVector(int initialCapacity)
/*     */   {
/*  25 */     super(initialCapacity);
/*     */   }
/*     */ 
/*     */   public int indexOf(Object elem)
/*     */   {
/*  30 */     initializeCache();
/*  31 */     Integer o = (Integer)this._indexCache.get(elem);
/*  32 */     if (o != null) {
/*  33 */       return o.intValue();
/*     */     }
/*  35 */     if (isLazyCaching()) {
/*  36 */       int i = super.indexOf(elem);
/*  37 */       if (i == -1) {
/*  38 */         uncacheIt(elem);
/*     */       }
/*     */       else {
/*  41 */         cacheIt(elem, i);
/*     */       }
/*  43 */       return i;
/*     */     }
/*     */ 
/*  46 */     return -1;
/*     */   }
/*     */ 
/*     */   protected synchronized void adjustCache(int index, int increase)
/*     */   {
/*  58 */     if (this._indexCache != null) {
/*  59 */       Map newCache = createCache();
/*  60 */       Set keys = this._indexCache.keySet();
/*  61 */       for (Iterator i$ = keys.iterator(); i$.hasNext(); ) { Object key = i$.next();
/*  62 */         int value = ((Integer)this._indexCache.get(key)).intValue();
/*  63 */         if (value >= index) {
/*  64 */           newCache.put(key, Integer.valueOf(value + increase));
/*     */         }
/*     */         else {
/*  67 */           newCache.put(key, Integer.valueOf(value));
/*     */         }
/*     */       }
/*  70 */       this._indexCache = newCache;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Map<Object, Integer> createCache() {
/*  75 */     return new IdentityHashMap();
/*     */   }
/*     */ 
/*     */   public synchronized void cacheIt(Object o, int index)
/*     */   {
/*  85 */     if ((this._indexCache != null) && ((this._indexCache.get(o) == null) || (index < ((Integer)this._indexCache.get(o)).intValue())))
/*  86 */       this._indexCache.put(o, Integer.valueOf(index));
/*     */   }
/*     */ 
/*     */   public synchronized void uncacheIt(Object o)
/*     */   {
/*  96 */     if (this._indexCache != null)
/*  97 */       this._indexCache.remove(o);
/*     */   }
/*     */ 
/*     */   public boolean add(E element)
/*     */   {
/* 103 */     boolean added = super.add(element);
/* 104 */     if ((!isLazyCaching()) && (added)) {
/* 105 */       initializeCache();
/* 106 */       cacheIt(element, size() - 1);
/*     */     }
/* 108 */     return added;
/*     */   }
/*     */ 
/*     */   public void add(int index, E element)
/*     */   {
/* 113 */     super.add(index, element);
/* 114 */     if (!isLazyCaching()) {
/* 115 */       initializeCache();
/* 116 */       adjustCache(index, 1);
/* 117 */       cacheIt(element, index);
/*     */     }
/* 119 */     else if (this._indexCache != null) {
/* 120 */       adjustCache(index, 1);
/* 121 */       cacheIt(element, index);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initializeCache() {
/* 126 */     if (this._indexCache == null)
/* 127 */       this._indexCache = createCache();
/*     */   }
/*     */ 
/*     */   public E remove(int index)
/*     */   {
/* 133 */     Object element = super.remove(index);
/* 134 */     if (element != null) {
/* 135 */       uncacheIt(element);
/* 136 */       adjustCache(index, -1);
/*     */     }
/* 138 */     return element;
/*     */   }
/*     */ 
/*     */   public boolean remove(Object o)
/*     */   {
/* 143 */     int oldIndex = indexOf(o);
/* 144 */     boolean removed = super.remove(o);
/* 145 */     if (removed) {
/* 146 */       uncacheIt(o);
/* 147 */       adjustCache(oldIndex, -1);
/*     */     }
/* 149 */     return removed;
/*     */   }
/*     */ 
/*     */   public boolean removeAll(Collection<?> c)
/*     */   {
/* 154 */     uncacheAll();
/* 155 */     return super.removeAll(c);
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 161 */     uncacheAll();
/* 162 */     super.clear();
/*     */   }
/*     */ 
/*     */   public boolean addAll(Collection<? extends E> c)
/*     */   {
/* 168 */     boolean added = super.addAll(c);
/* 169 */     if (added) {
/* 170 */       cacheAll();
/*     */     }
/* 172 */     return added;
/*     */   }
/*     */ 
/*     */   public boolean addAll(int index, Collection<? extends E> c)
/*     */   {
/* 177 */     boolean added = super.addAll(index, c);
/* 178 */     initializeCache();
/* 179 */     adjustCache(index, c.size());
/* 180 */     for (Iterator i$ = c.iterator(); i$.hasNext(); ) { Object e = i$.next();
/* 181 */       cacheIt(e, index++);
/*     */     }
/* 183 */     return added;
/*     */   }
/*     */ 
/*     */   public E set(int index, E element)
/*     */   {
/* 188 */     if (!isLazyCaching()) {
/* 189 */       initializeCache();
/* 190 */       Object e = super.set(index, element);
/* 191 */       uncacheIt(e);
/* 192 */       cacheIt(element, index);
/* 193 */       return e;
/*     */     }
/*     */ 
/* 196 */     return super.set(index, element);
/*     */   }
/*     */ 
/*     */   public void invalidateCache()
/*     */   {
/* 204 */     uncacheAll();
/*     */   }
/*     */ 
/*     */   public synchronized void uncacheAll()
/*     */   {
/* 211 */     if (this._indexCache != null) {
/* 212 */       this._indexCache.clear();
/* 213 */       this._indexCache = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void cacheAll()
/*     */   {
/* 221 */     this._indexCache = createCache();
/* 222 */     Integer i = Integer.valueOf(0);
/* 223 */     for (Iterator i$ = iterator(); i$.hasNext(); ) { Object elem = i$.next();
/* 224 */       if (this._indexCache.get(elem) == null) {
/* 225 */         this._indexCache.put(elem, i);
/*     */       }
/* 227 */       localInteger1 = i; Integer localInteger2 = i = Integer.valueOf(i.intValue() + 1);
/*     */     }
/*     */     Integer localInteger1;
/*     */   }
/*     */ 
/*     */   public boolean isLazyCaching()
/*     */   {
/* 238 */     return this._lazyCaching;
/*     */   }
/*     */ 
/*     */   public void setLazyCaching(boolean lazyCaching) {
/* 242 */     this._lazyCaching = lazyCaching;
/*     */   }
/*     */ 
/*     */   protected void removeRange(int fromIndex, int toIndex)
/*     */   {
/* 247 */     if (fromIndex == toIndex) {
/* 248 */       remove(fromIndex);
/*     */     }
/*     */     else {
/* 251 */       super.removeRange(fromIndex, toIndex);
/* 252 */       uncacheAll();
/* 253 */       if (!isLazyCaching())
/* 254 */         cacheAll();
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.CachedVector
 * JD-Core Version:    0.6.0
 */
/*     */ package com.jidesoft.utils;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class CachedArrayList<E> extends ArrayList<E>
/*     */ {
/*     */   private Map<Object, Integer> _indexCache;
/*  18 */   private boolean _lazyCaching = false;
/*     */ 
/*     */   public CachedArrayList() {
/*     */   }
/*     */ 
/*     */   public CachedArrayList(Collection<? extends E> c) {
/*  24 */     super(c);
/*  25 */     if (!isLazyCaching())
/*  26 */       cacheAll();
/*     */   }
/*     */ 
/*     */   public CachedArrayList(int initialCapacity)
/*     */   {
/*  31 */     super(initialCapacity);
/*     */   }
/*     */ 
/*     */   public int indexOf(Object elem)
/*     */   {
/*  36 */     initializeCache();
/*  37 */     Integer o = (Integer)this._indexCache.get(elem);
/*  38 */     if (o != null) {
/*  39 */       return o.intValue();
/*     */     }
/*  41 */     if (isLazyCaching()) {
/*  42 */       int i = super.indexOf(elem);
/*  43 */       if (i == -1) {
/*  44 */         uncacheIt(elem);
/*     */       }
/*     */       else {
/*  47 */         cacheIt(elem, i);
/*     */       }
/*  49 */       return i;
/*     */     }
/*     */ 
/*  52 */     return -1;
/*     */   }
/*     */ 
/*     */   protected synchronized void adjustCache(int index, int increase)
/*     */   {
/*  64 */     if (this._indexCache != null) {
/*  65 */       Map newCache = createCache();
/*  66 */       Set keys = this._indexCache.keySet();
/*  67 */       for (Iterator i$ = keys.iterator(); i$.hasNext(); ) { Object key = i$.next();
/*  68 */         int value = ((Integer)this._indexCache.get(key)).intValue();
/*  69 */         if (value >= index) {
/*  70 */           newCache.put(key, Integer.valueOf(value + increase));
/*     */         }
/*     */         else {
/*  73 */           newCache.put(key, Integer.valueOf(value));
/*     */         }
/*     */       }
/*  76 */       this._indexCache = newCache;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Map<Object, Integer> createCache() {
/*  81 */     return new IdentityHashMap();
/*     */   }
/*     */ 
/*     */   public void cacheIt(Object o, int index)
/*     */   {
/*  92 */     if ((this._indexCache != null) && ((this._indexCache.get(o) == null) || (index < ((Integer)this._indexCache.get(o)).intValue())))
/*  93 */       this._indexCache.put(o, Integer.valueOf(index));
/*     */   }
/*     */ 
/*     */   public void uncacheIt(Object o)
/*     */   {
/* 103 */     if (this._indexCache != null)
/* 104 */       this._indexCache.remove(o);
/*     */   }
/*     */ 
/*     */   public boolean add(E o)
/*     */   {
/* 110 */     boolean added = super.add(o);
/* 111 */     if ((!isLazyCaching()) && (added)) {
/* 112 */       initializeCache();
/* 113 */       cacheIt(o, size() - 1);
/*     */     }
/* 115 */     return added;
/*     */   }
/*     */ 
/*     */   public void add(int index, E element)
/*     */   {
/* 120 */     super.add(index, element);
/* 121 */     if (!isLazyCaching()) {
/* 122 */       initializeCache();
/* 123 */       adjustCache(index, 1);
/* 124 */       cacheIt(element, index);
/*     */     }
/* 126 */     else if (this._indexCache != null) {
/* 127 */       adjustCache(index, 1);
/* 128 */       cacheIt(element, index);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initializeCache() {
/* 133 */     if (this._indexCache == null)
/* 134 */       this._indexCache = createCache();
/*     */   }
/*     */ 
/*     */   public E remove(int index)
/*     */   {
/* 140 */     Object element = super.remove(index);
/* 141 */     if (element != null) {
/* 142 */       uncacheIt(element);
/* 143 */       adjustCache(index, -1);
/*     */     }
/* 145 */     return element;
/*     */   }
/*     */ 
/*     */   public boolean remove(Object o)
/*     */   {
/* 150 */     int oldIndex = indexOf(o);
/* 151 */     boolean removed = super.remove(o);
/* 152 */     if (removed) {
/* 153 */       uncacheIt(o);
/* 154 */       adjustCache(oldIndex, -1);
/*     */     }
/* 156 */     return removed;
/*     */   }
/*     */ 
/*     */   public boolean removeAll(Collection<?> c)
/*     */   {
/* 161 */     uncacheAll();
/* 162 */     return super.removeAll(c);
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 168 */     uncacheAll();
/* 169 */     super.clear();
/*     */   }
/*     */ 
/*     */   public boolean addAll(Collection<? extends E> c)
/*     */   {
/* 175 */     boolean added = super.addAll(c);
/* 176 */     if (added) {
/* 177 */       cacheAll();
/*     */     }
/* 179 */     return added;
/*     */   }
/*     */ 
/*     */   public boolean addAll(int index, Collection<? extends E> c)
/*     */   {
/* 184 */     boolean added = super.addAll(index, c);
/* 185 */     initializeCache();
/* 186 */     adjustCache(index, c.size());
/* 187 */     for (Iterator i$ = c.iterator(); i$.hasNext(); ) { Object e = i$.next();
/* 188 */       cacheIt(e, index++);
/*     */     }
/* 190 */     return added;
/*     */   }
/*     */ 
/*     */   public E set(int index, E element)
/*     */   {
/* 195 */     if (!isLazyCaching()) {
/* 196 */       initializeCache();
/* 197 */       Object e = super.set(index, element);
/* 198 */       uncacheIt(e);
/* 199 */       cacheIt(element, index);
/* 200 */       return e;
/*     */     }
/*     */ 
/* 203 */     return super.set(index, element);
/*     */   }
/*     */ 
/*     */   public void invalidateCache()
/*     */   {
/* 211 */     uncacheAll();
/*     */   }
/*     */ 
/*     */   public void uncacheAll()
/*     */   {
/* 218 */     if (this._indexCache != null) {
/* 219 */       this._indexCache.clear();
/* 220 */       this._indexCache = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void cacheAll()
/*     */   {
/* 228 */     this._indexCache = createCache();
/* 229 */     Integer i = Integer.valueOf(0);
/* 230 */     for (Iterator i$ = iterator(); i$.hasNext(); ) { Object elem = i$.next();
/* 231 */       if (this._indexCache.get(elem) == null) {
/* 232 */         this._indexCache.put(elem, i);
/*     */       }
/* 234 */       localInteger1 = i; Integer localInteger2 = i = Integer.valueOf(i.intValue() + 1);
/*     */     }
/*     */     Integer localInteger1;
/*     */   }
/*     */ 
/*     */   public boolean isLazyCaching()
/*     */   {
/* 245 */     return this._lazyCaching;
/*     */   }
/*     */ 
/*     */   public void setLazyCaching(boolean lazyCaching) {
/* 249 */     this._lazyCaching = lazyCaching;
/*     */   }
/*     */ 
/*     */   protected void removeRange(int fromIndex, int toIndex)
/*     */   {
/* 254 */     if (fromIndex == toIndex) {
/* 255 */       remove(fromIndex);
/*     */     }
/*     */     else {
/* 258 */       super.removeRange(fromIndex, toIndex);
/* 259 */       uncacheAll();
/* 260 */       if (!isLazyCaching())
/* 261 */         cacheAll();
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.CachedArrayList
 * JD-Core Version:    0.6.0
 */
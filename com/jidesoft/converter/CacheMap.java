/*     */ package com.jidesoft.converter;
/*     */ 
/*     */ import com.jidesoft.utils.TypeUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.swing.event.EventListenerList;
/*     */ 
/*     */ public class CacheMap<T, K>
/*     */ {
/*  21 */   private HashMap<Class<?>, Cache<K, T>> _cache = new HashMap();
/*     */   private K _defaultContext;
/* 280 */   protected EventListenerList listenerList = new EventListenerList();
/*     */ 
/*     */   public CacheMap(K defaultContext)
/*     */   {
/*  31 */     this._defaultContext = defaultContext;
/*     */   }
/*     */ 
/*     */   protected Cache<K, T> getCache(Class<?> clazz)
/*     */   {
/*  50 */     if (clazz == null) {
/*  51 */       throw new IllegalArgumentException("Clazz cannot be null");
/*     */     }
/*  53 */     return (Cache)this._cache.get(clazz);
/*     */   }
/*     */ 
/*     */   public K[] getKeys(Class<?> clazz, K[] a)
/*     */   {
/*  64 */     Cache cache = getCache(clazz);
/*  65 */     if (cache != null) {
/*  66 */       Set set = cache.keySet();
/*  67 */       return set.toArray(a);
/*     */     }
/*     */ 
/*  70 */     return a;
/*     */   }
/*     */ 
/*     */   protected Cache<K, T> initCache(Class<?> clazz)
/*     */   {
/*  75 */     Cache cache = getCache(clazz);
/*  76 */     if (cache != null) {
/*  77 */       return cache;
/*     */     }
/*     */ 
/*  80 */     cache = new Cache();
/*  81 */     this._cache.put(clazz, cache);
/*  82 */     return cache;
/*     */   }
/*     */ 
/*     */   public void register(Class<?> clazz, T object, K context)
/*     */   {
/*  95 */     if (clazz == null) {
/*  96 */       throw new IllegalArgumentException("Parameter clazz cannot be null");
/*     */     }
/*     */ 
/*  99 */     Cache cache = initCache(clazz);
/* 100 */     cache.setObject(context, object);
/* 101 */     fireRegistrationChanged(new RegistrationEvent(this, 3399, object, clazz, context));
/*     */   }
/*     */ 
/*     */   public void unregister(Class<?> clazz, K context)
/*     */   {
/* 111 */     Cache cache = getCache(clazz);
/* 112 */     if (cache != null) {
/* 113 */       Object object = cache.getObject(context);
/* 114 */       cache.setObject(context, null);
/* 115 */       fireRegistrationChanged(new RegistrationEvent(this, 3400, object, clazz, context));
/* 116 */       if (cache.size() == 0)
/* 117 */         this._cache.remove(clazz);
/*     */     }
/*     */   }
/*     */ 
/*     */   public T getRegisteredObject(Class<?> clazz, K context)
/*     */   {
/* 135 */     if (clazz == null) {
/* 136 */       return null;
/*     */     }
/*     */ 
/* 139 */     Cache cache = getCache(clazz);
/*     */ 
/* 141 */     if ((cache == null) || (!cache.containsKey(context))) {
/* 142 */       List classesToSearch = new ArrayList();
/*     */ 
/* 144 */       classesToSearch.add(clazz);
/* 145 */       if (TypeUtils.isPrimitive(clazz)) {
/* 146 */         classesToSearch.add(TypeUtils.convertPrimitiveToWrapperType(clazz));
/*     */       }
/* 148 */       else if (TypeUtils.isPrimitiveWrapper(clazz)) {
/* 149 */         classesToSearch.add(TypeUtils.convertWrapperToPrimitiveType(clazz));
/*     */       }
/*     */ 
/* 153 */       Class[] interfaces = clazz.getInterfaces();
/* 154 */       classesToSearch.addAll(Arrays.asList(interfaces));
/*     */ 
/* 156 */       Class superClass = clazz;
/*     */ 
/* 158 */       while (!superClass.isInterface()) {
/* 159 */         superClass = superClass.getSuperclass();
/* 160 */         if (superClass == null) break;
/* 161 */         classesToSearch.add(superClass);
/* 162 */         interfaces = superClass.getInterfaces();
/* 163 */         classesToSearch.addAll(Arrays.asList(interfaces));
/*     */       }
/*     */ 
/* 170 */       if (!classesToSearch.contains(Object.class)) {
/* 171 */         classesToSearch.add(Object.class);
/*     */       }
/*     */ 
/* 175 */       for (Class c : classesToSearch) {
/* 176 */         Cache cacheForClass = getCache(c);
/* 177 */         if (cacheForClass != null) {
/* 178 */           Object object = cacheForClass.getObject(context);
/* 179 */           if (object != null) {
/* 180 */             return object;
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 186 */       if (!this._defaultContext.equals(context)) {
/* 187 */         for (Class c : classesToSearch) {
/* 188 */           Cache cacheForClass = getCache(c);
/* 189 */           if (cacheForClass != null) {
/* 190 */             Object object = cacheForClass.getObject(this._defaultContext);
/* 191 */             if (object != null) {
/* 192 */               return object;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 199 */     if (cache != null) {
/* 200 */       Object object = cache.getObject(context);
/* 201 */       if ((object == null) && (!this._defaultContext.equals(context))) {
/* 202 */         return getRegisteredObject(clazz, this._defaultContext);
/*     */       }
/* 204 */       if (object != null) {
/* 205 */         return object;
/*     */       }
/*     */     }
/*     */ 
/* 209 */     return null;
/*     */   }
/*     */ 
/*     */   public T getMatchRegisteredObject(Class<?> clazz, K context)
/*     */   {
/* 222 */     if (clazz == null) {
/* 223 */       return null;
/*     */     }
/*     */ 
/* 226 */     if (context == null) {
/* 227 */       context = this._defaultContext;
/*     */     }
/*     */ 
/* 230 */     Cache cache = getCache(clazz);
/* 231 */     if (cache != null) {
/* 232 */       Object object = cache.getObject(context);
/* 233 */       if (object != null) {
/* 234 */         return object;
/*     */       }
/*     */     }
/* 237 */     return null;
/*     */   }
/*     */ 
/*     */   public List<T> getValues() {
/* 241 */     List list = new ArrayList();
/* 242 */     Collection col = this._cache.values();
/* 243 */     for (Cache o : col) {
/* 244 */       Collection col2 = o.values();
/* 245 */       for (i$ = col2.iterator(); i$.hasNext(); ) { Object o2 = i$.next();
/* 246 */         if (!list.contains(o2))
/* 247 */           list.add(o2);
/*     */       }
/*     */     }
/*     */     Iterator i$;
/* 251 */     return list;
/*     */   }
/*     */ 
/*     */   public void remove(Class<?> clazz)
/*     */   {
/* 260 */     Cache cache = getCache(clazz);
/* 261 */     if (cache != null) {
/* 262 */       Object[] keys = cache.keySet().toArray();
/* 263 */       for (Object context : keys) {
/* 264 */         Object object = cache.getObject(context);
/* 265 */         cache.setObject(context, null);
/* 266 */         fireRegistrationChanged(new RegistrationEvent(this, 3400, object, clazz, context));
/*     */       }
/*     */     }
/* 269 */     this._cache.remove(clazz);
/*     */   }
/*     */ 
/*     */   public void clear() {
/* 273 */     this._cache.clear();
/* 274 */     fireRegistrationChanged(new RegistrationEvent(this, 3401));
/*     */   }
/*     */ 
/*     */   public void addRegistrationListener(RegistrationListener l)
/*     */   {
/* 288 */     this.listenerList.add(RegistrationListener.class, l);
/*     */   }
/*     */ 
/*     */   public void removeRegistrationListener(RegistrationListener l)
/*     */   {
/* 297 */     this.listenerList.remove(RegistrationListener.class, l);
/*     */   }
/*     */ 
/*     */   public RegistrationListener[] getRegistrationListeners()
/*     */   {
/* 310 */     return (RegistrationListener[])this.listenerList.getListeners(RegistrationListener.class);
/*     */   }
/*     */ 
/*     */   public void fireRegistrationChanged(RegistrationEvent e)
/*     */   {
/* 324 */     Object[] listeners = this.listenerList.getListenerList();
/*     */ 
/* 327 */     for (int i = listeners.length - 2; i >= 0; i -= 2)
/* 328 */       if (listeners[i] == RegistrationListener.class)
/* 329 */         ((RegistrationListener)listeners[(i + 1)]).registrationChanged(e);
/*     */   }
/*     */ 
/*     */   static class Cache<K, T> extends HashMap<K, T>
/*     */   {
/*     */     public T getObject(K context)
/*     */     {
/*  36 */       return get(context);
/*     */     }
/*     */ 
/*     */     public void setObject(K context, T object) {
/*  40 */       if (object == null) {
/*  41 */         remove(context);
/*     */       }
/*     */       else
/*  44 */         put(context, object);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.CacheMap
 * JD-Core Version:    0.6.0
 */
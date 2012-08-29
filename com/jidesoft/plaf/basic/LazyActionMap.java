/*     */ package com.jidesoft.plaf.basic;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.ActionMap;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ActionMapUIResource;
/*     */ 
/*     */ public class LazyActionMap extends ActionMapUIResource
/*     */ {
/*     */   private transient Object _loader;
/*     */ 
/*     */   public static void installLazyActionMap(JComponent c, Class loaderClass, String defaultsKey)
/*     */   {
/*  45 */     ActionMap map = (ActionMap)UIDefaultsLookup.get(defaultsKey);
/*  46 */     if (map == null) {
/*  47 */       map = new LazyActionMap(loaderClass);
/*  48 */       UIManager.getLookAndFeelDefaults().put(defaultsKey, map);
/*     */     }
/*  50 */     SwingUtilities.replaceUIActionMap(c, map);
/*     */   }
/*     */ 
/*     */   static ActionMap getActionMap(Class loaderClass, String defaultsKey)
/*     */   {
/*  67 */     ActionMap map = (ActionMap)UIDefaultsLookup.get(defaultsKey);
/*  68 */     if (map == null) {
/*  69 */       map = new LazyActionMap(loaderClass);
/*  70 */       UIManager.getLookAndFeelDefaults().put(defaultsKey, map);
/*     */     }
/*  72 */     return map;
/*     */   }
/*     */ 
/*     */   private LazyActionMap(Class loader)
/*     */   {
/*  77 */     this._loader = loader;
/*     */   }
/*     */ 
/*     */   public void put(Action action) {
/*  81 */     put(action.getValue("Name"), action);
/*     */   }
/*     */ 
/*     */   public void put(Object key, Action action)
/*     */   {
/*  86 */     loadIfNecessary();
/*  87 */     super.put(key, action);
/*     */   }
/*     */ 
/*     */   public Action get(Object key)
/*     */   {
/*  92 */     loadIfNecessary();
/*  93 */     return super.get(key);
/*     */   }
/*     */ 
/*     */   public void remove(Object key)
/*     */   {
/*  98 */     loadIfNecessary();
/*  99 */     super.remove(key);
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 104 */     loadIfNecessary();
/* 105 */     super.clear();
/*     */   }
/*     */ 
/*     */   public Object[] keys()
/*     */   {
/* 110 */     loadIfNecessary();
/* 111 */     return super.keys();
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 116 */     loadIfNecessary();
/* 117 */     return super.size();
/*     */   }
/*     */ 
/*     */   public Object[] allKeys()
/*     */   {
/* 122 */     loadIfNecessary();
/* 123 */     return super.allKeys();
/*     */   }
/*     */ 
/*     */   public void setParent(ActionMap map)
/*     */   {
/* 128 */     loadIfNecessary();
/* 129 */     super.setParent(map);
/*     */   }
/*     */ 
/*     */   private void loadIfNecessary() {
/* 133 */     if (this._loader != null) {
/* 134 */       Object loader = this._loader;
/*     */ 
/* 136 */       this._loader = null;
/* 137 */       Class klass = (Class)loader;
/*     */       try {
/* 139 */         Method method = klass.getDeclaredMethod("loadActionMap", new Class[] { LazyActionMap.class });
/*     */ 
/* 141 */         method.invoke(klass, new Object[] { this });
/*     */       }
/*     */       catch (Exception nsme) {
/* 144 */         System.out.println("LazyActionMap unable to load actions " + klass);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.LazyActionMap
 * JD-Core Version:    0.6.0
 */
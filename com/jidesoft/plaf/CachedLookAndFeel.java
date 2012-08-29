/*     */ package com.jidesoft.plaf;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ 
/*     */ class CachedLookAndFeel extends LookAndFeel
/*     */ {
/*     */   static ClassLoader currentLoader;
/*  24 */   UIDefaults customDefaults = new CustomUIDefaults();
/*     */ 
/*     */   public static void install()
/*     */   {
/*     */     try
/*     */     {
/*  32 */       Method method = UIManager.class.getDeclaredMethod("getLAFState", new Class[] { null });
/*  33 */       method.setAccessible(true);
/*  34 */       Object lafState = method.invoke(null, new Object[] { null });
/*     */ 
/*  37 */       Field field = lafState.getClass().getDeclaredField("multiLookAndFeel");
/*  38 */       field.setAccessible(true);
/*  39 */       CachedLookAndFeel laf = new CachedLookAndFeel();
/*  40 */       field.set(lafState, laf);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  44 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public CachedLookAndFeel() {
/*  49 */     this.customDefaults = new CustomUIDefaults();
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  54 */     return "CachedLookAndFeel";
/*     */   }
/*     */ 
/*     */   public String getID()
/*     */   {
/*  59 */     return "CachedLookAndFeel";
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  64 */     return "Provide customized behaviour for getUI() method";
/*     */   }
/*     */ 
/*     */   public boolean isNativeLookAndFeel()
/*     */   {
/*  69 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isSupportedLookAndFeel()
/*     */   {
/*  74 */     return true;
/*     */   }
/*     */ 
/*     */   public UIDefaults getDefaults()
/*     */   {
/*  79 */     return this.customDefaults;
/*     */   }
/*     */ 
/*     */   public static void installJideExtension(ClassLoader newLoader)
/*     */   {
/*  89 */     installJideExtension(newLoader, false);
/*     */   }
/*     */ 
/*     */   public static void installJideExtension(ClassLoader newLoader, boolean force)
/*     */   {
/* 101 */     if ((currentLoader == newLoader) && (!force)) {
/* 102 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 106 */       Class lafFactory = newLoader.loadClass("com.jidesoft.plaf.LookAndFeelFactory");
/* 107 */       Method installJideExtension = lafFactory.getDeclaredMethod("installJideExtension", new Class[] { Integer.TYPE });
/* 108 */       int style = LookAndFeelFactory.getDefaultStyle();
/* 109 */       UIManager.put("jidesoft.extendsionInstalled", null);
/* 110 */       installJideExtension.invoke(null, new Object[] { Integer.valueOf(style) });
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */ 
/* 117 */     currentLoader = newLoader;
/* 118 */     UIManager.put("ClassLoader", newLoader);
/*     */   }
/*     */ 
/*     */   public static void reinstallJideExtension() {
/* 122 */     installJideExtension(currentLoader, true);
/*     */   }
/*     */ 
/*     */   private static void removeCachedClass(UIDefaults defaults, Class componentUIClass) {
/* 126 */     if (componentUIClass != null)
/*     */     {
/* 128 */       defaults.remove(componentUIClass.getName());
/*     */ 
/* 130 */       defaults.remove(componentUIClass);
/*     */     }
/*     */   }
/*     */ 
/*     */   static class CustomUIDefaults extends UIDefaults {
/*     */     private static final long serialVersionUID = -6034471887061473005L;
/*     */ 
/*     */     public ComponentUI getUI(JComponent target) {
/* 139 */       UIDefaults defaults = UIManager.getDefaults();
/*     */ 
/* 147 */       String className = (String)defaults.get(target.getUIClassID());
/* 148 */       Class componentUIClass = className != null ? (Class)defaults.get(className) : null;
/* 149 */       ClassLoader componentUIClassLoader = componentUIClass != null ? componentUIClass.getClassLoader() : null;
/*     */ 
/* 151 */       ClassLoader targetClassLoader = target.getClass().getClassLoader();
/* 152 */       ClassLoader uiClassLoader = (ClassLoader)UIManager.get("ClassLoader");
/*     */ 
/* 154 */       if (targetClassLoader == null) {
/* 155 */         if ((componentUIClassLoader != null) && (componentUIClassLoader != uiClassLoader)) {
/* 156 */           CachedLookAndFeel.access$000(defaults, componentUIClass);
/*     */         }
/* 158 */         return null;
/*     */       }
/*     */ 
/* 161 */       if ((targetClassLoader != componentUIClassLoader) || (targetClassLoader != uiClassLoader))
/*     */       {
/* 163 */         if (componentUIClassLoader != null) {
/* 164 */           CachedLookAndFeel.access$000(defaults, componentUIClass);
/*     */         }
/* 166 */         CachedLookAndFeel.installJideExtension(targetClassLoader);
/*     */       }
/* 168 */       return null;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.CachedLookAndFeel
 * JD-Core Version:    0.6.0
 */
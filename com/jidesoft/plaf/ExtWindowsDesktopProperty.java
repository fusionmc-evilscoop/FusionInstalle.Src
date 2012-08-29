/*     */ package com.jidesoft.plaf;
/*     */ 
/*     */ import com.jidesoft.plaf.vsnet.ConvertListener;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.Window;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.lang.ref.WeakReference;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIDefaults.ActiveValue;
/*     */ import javax.swing.UIDefaults.ProxyLazyValue;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.FontUIResource;
/*     */ 
/*     */ public class ExtWindowsDesktopProperty
/*     */   implements UIDefaults.ActiveValue
/*     */ {
/*     */   private static boolean updatePending;
/*     */   private WeakPCL pcl;
/*     */   private String[] keys;
/*     */   private Object[] value;
/*     */   private Object[] fallback;
/*     */   private ConvertListener listener;
/*     */   private Toolkit toolkit;
/*     */ 
/*     */   private static synchronized void setUpdatePending(boolean update)
/*     */   {
/*  67 */     updatePending = update;
/*     */   }
/*     */ 
/*     */   private static synchronized boolean isUpdatePending()
/*     */   {
/*  74 */     return updatePending;
/*     */   }
/*     */ 
/*     */   private static void updateAllUIs()
/*     */   {
/*  81 */     Frame[] appFrames = Frame.getFrames();
/*  82 */     for (Frame frame : appFrames)
/*  83 */       updateWindowUI(frame);
/*     */   }
/*     */ 
/*     */   private static void updateWindowUI(Window window)
/*     */   {
/*  91 */     SwingUtilities.updateComponentTreeUI(window);
/*  92 */     Window[] ownedWins = window.getOwnedWindows();
/*  93 */     for (Window win : ownedWins)
/*  94 */       updateWindowUI(win);
/*     */   }
/*     */ 
/*     */   public ExtWindowsDesktopProperty(String[] keys, Object[] fallback, Toolkit toolkit, ConvertListener listener)
/*     */   {
/* 108 */     this.keys = keys;
/* 109 */     this.fallback = fallback;
/* 110 */     this.toolkit = toolkit;
/* 111 */     this.listener = listener;
/*     */   }
/*     */ 
/*     */   public Object createValue(UIDefaults table)
/*     */   {
/* 119 */     if (this.value == null) {
/* 120 */       this.value = configureValue(getValueFromDesktop());
/* 121 */       if (this.value[0] == null) {
/* 122 */         this.value = configureValue(getDefaultValue());
/*     */       }
/*     */     }
/* 125 */     return this.listener.convert(this.value);
/*     */   }
/*     */ 
/*     */   protected Object[] getValueFromDesktop()
/*     */   {
/* 132 */     if (this.toolkit == null) {
/* 133 */       this.toolkit = Toolkit.getDefaultToolkit();
/*     */     }
/* 135 */     this.pcl = new WeakPCL(this, this.toolkit);
/* 136 */     Object[] values = new Object[getKeys().length];
/* 137 */     for (int i = 0; i < getKeys().length; i++) {
/* 138 */       values[i] = this.toolkit.getDesktopProperty(getKeys()[i]);
/* 139 */       this.toolkit.addPropertyChangeListener(getKeys()[i], this.pcl);
/*     */     }
/* 141 */     return values;
/*     */   }
/*     */ 
/*     */   protected Object[] getDefaultValue()
/*     */   {
/* 148 */     return this.fallback;
/*     */   }
/*     */ 
/*     */   public void invalidate()
/*     */   {
/* 156 */     if (this.pcl != null) {
/* 157 */       for (int i = 0; i < getKeys().length; i++) {
/* 158 */         this.toolkit.removePropertyChangeListener(getKeys()[i], this.pcl);
/*     */       }
/* 160 */       this.toolkit = null;
/* 161 */       this.pcl = null;
/* 162 */       this.value = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void updateUI()
/*     */   {
/* 174 */     if (!isUpdatePending()) {
/* 175 */       setUpdatePending(true);
/* 176 */       Runnable uiUpdater = new Runnable() {
/*     */         public void run() {
/* 178 */           ExtWindowsDesktopProperty.access$000();
/* 179 */           ExtWindowsDesktopProperty.access$100(false);
/*     */         }
/*     */       };
/* 182 */       SwingUtilities.invokeLater(uiUpdater);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Object[] configureValue(Object[] value)
/*     */   {
/* 191 */     if (value != null) {
/* 192 */       for (int i = 0; i < value.length; i++) {
/* 193 */         value[i] = configureValue(value[i]);
/*     */       }
/*     */     }
/* 196 */     return value;
/*     */   }
/*     */ 
/*     */   protected Object configureValue(Object value)
/*     */   {
/* 204 */     if (value != null) {
/* 205 */       if ((value instanceof Color)) {
/* 206 */         return new ColorUIResource((Color)value);
/*     */       }
/* 208 */       if ((value instanceof Font)) {
/* 209 */         return new FontUIResource((Font)value);
/*     */       }
/* 211 */       if ((value instanceof UIDefaults.ProxyLazyValue)) {
/* 212 */         value = ((UIDefaults.ProxyLazyValue)value).createValue(null);
/*     */       }
/* 214 */       else if ((value instanceof UIDefaults.ActiveValue)) {
/* 215 */         value = ((UIDefaults.ActiveValue)value).createValue(null);
/*     */       }
/*     */     }
/* 218 */     return value;
/*     */   }
/*     */ 
/*     */   protected String[] getKeys()
/*     */   {
/* 225 */     return this.keys;
/*     */   }
/*     */ 
/*     */   private static class WeakPCL extends WeakReference
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     private Toolkit kit;
/*     */ 
/*     */     WeakPCL(Object target, Toolkit kit)
/*     */     {
/* 239 */       super();
/* 240 */       this.kit = kit;
/*     */     }
/*     */ 
/*     */     public void propertyChange(PropertyChangeEvent pce) {
/* 244 */       ExtWindowsDesktopProperty property = (ExtWindowsDesktopProperty)get();
/*     */ 
/* 246 */       if (property == null)
/*     */       {
/* 249 */         this.kit.removePropertyChangeListener(pce.getPropertyName(), this);
/*     */       }
/*     */       else {
/* 252 */         property.invalidate();
/* 253 */         property.updateUI();
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.ExtWindowsDesktopProperty
 * JD-Core Version:    0.6.0
 */
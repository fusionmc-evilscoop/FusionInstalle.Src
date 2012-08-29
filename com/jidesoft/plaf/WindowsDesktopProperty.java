/*     */ package com.jidesoft.plaf;
/*     */ 
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
/*     */ public class WindowsDesktopProperty
/*     */   implements UIDefaults.ActiveValue
/*     */ {
/*     */   private static boolean updatePending;
/*     */   private WeakPCL pcl;
/*     */   private String key;
/*     */   private Object value;
/*     */   private Object fallback;
/*     */   private Toolkit toolkit;
/*  57 */   private float fontSize = -1.0F;
/*     */ 
/*  59 */   private int fontStyle = -1;
/*     */ 
/*     */   private static synchronized void setUpdatePending(boolean update)
/*     */   {
/*  66 */     updatePending = update;
/*     */   }
/*     */ 
/*     */   private static synchronized boolean isUpdatePending()
/*     */   {
/*  73 */     return updatePending;
/*     */   }
/*     */ 
/*     */   private static void updateAllUIs()
/*     */   {
/*  80 */     Frame[] appFrames = Frame.getFrames();
/*  81 */     for (Frame frame : appFrames)
/*  82 */       updateWindowUI(frame);
/*     */   }
/*     */ 
/*     */   private static void updateWindowUI(Window window)
/*     */   {
/*  90 */     SwingUtilities.updateComponentTreeUI(window);
/*  91 */     Window[] ownedWins = window.getOwnedWindows();
/*  92 */     for (Window win : ownedWins)
/*  93 */       updateWindowUI(win);
/*     */   }
/*     */ 
/*     */   public WindowsDesktopProperty(String key, Object fallback, Toolkit toolkit)
/*     */   {
/* 107 */     this.key = key;
/* 108 */     this.fallback = fallback;
/* 109 */     this.toolkit = toolkit;
/*     */   }
/*     */ 
/*     */   public WindowsDesktopProperty(String key, Object fallback, Toolkit toolkit, float fontSize) {
/* 113 */     this.key = key;
/* 114 */     this.fallback = fallback;
/* 115 */     this.toolkit = toolkit;
/* 116 */     this.fontSize = fontSize;
/*     */   }
/*     */ 
/*     */   public WindowsDesktopProperty(String key, Object fallback, Toolkit toolkit, float fontSize, int fontStyle) {
/* 120 */     this.key = key;
/* 121 */     this.fallback = fallback;
/* 122 */     this.toolkit = toolkit;
/* 123 */     this.fontSize = fontSize;
/* 124 */     this.fontStyle = fontStyle;
/*     */   }
/*     */ 
/*     */   public Object createValue(UIDefaults table)
/*     */   {
/* 132 */     if (this.value == null) {
/* 133 */       this.value = configureValue(getValueFromDesktop());
/* 134 */       if (this.value == null) {
/* 135 */         this.value = configureValue(getDefaultValue());
/*     */       }
/*     */     }
/* 138 */     return this.value;
/*     */   }
/*     */ 
/*     */   protected Object getValueFromDesktop()
/*     */   {
/* 145 */     if (this.toolkit == null) {
/* 146 */       this.toolkit = Toolkit.getDefaultToolkit();
/*     */     }
/* 148 */     Object value = this.toolkit.getDesktopProperty(getKey());
/* 149 */     this.pcl = new WeakPCL(this, this.toolkit);
/* 150 */     this.toolkit.addPropertyChangeListener(getKey(), this.pcl);
/* 151 */     return value;
/*     */   }
/*     */ 
/*     */   protected Object getDefaultValue()
/*     */   {
/* 158 */     if ((this.fallback instanceof String)) {
/* 159 */       return UIDefaultsLookup.get(this.fallback);
/*     */     }
/*     */ 
/* 162 */     return this.fallback;
/*     */   }
/*     */ 
/*     */   public void invalidate()
/*     */   {
/* 171 */     if (this.pcl != null) {
/* 172 */       this.toolkit.removePropertyChangeListener(getKey(), this.pcl);
/* 173 */       this.toolkit = null;
/* 174 */       this.pcl = null;
/* 175 */       this.value = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void updateUI()
/*     */   {
/* 187 */     if (!isUpdatePending()) {
/* 188 */       setUpdatePending(true);
/* 189 */       Runnable uiUpdater = new Runnable() {
/*     */         public void run() {
/* 191 */           WindowsDesktopProperty.access$000();
/* 192 */           WindowsDesktopProperty.access$100(false);
/*     */         }
/*     */       };
/* 195 */       SwingUtilities.invokeLater(uiUpdater);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Object configureValue(Object value)
/*     */   {
/* 204 */     if (value != null) {
/* 205 */       if ((value instanceof Color)) {
/* 206 */         return new ColorUIResource((Color)value);
/*     */       }
/* 208 */       if ((value instanceof Font)) {
/* 209 */         if ((this.fontSize != -1.0F) && (this.fontStyle != -1.0F)) {
/* 210 */           return new FontUIResource(((Font)value).deriveFont(this.fontStyle, this.fontSize));
/*     */         }
/* 212 */         if (this.fontSize != -1.0F) {
/* 213 */           return new FontUIResource(((Font)value).deriveFont(this.fontSize));
/*     */         }
/* 215 */         if (this.fontStyle != -1.0F) {
/* 216 */           return new FontUIResource(((Font)value).deriveFont(this.fontStyle));
/*     */         }
/*     */ 
/* 219 */         return new FontUIResource((Font)value);
/*     */       }
/*     */ 
/* 222 */       if ((value instanceof UIDefaults.ProxyLazyValue)) {
/* 223 */         value = ((UIDefaults.ProxyLazyValue)value).createValue(null);
/*     */       }
/* 225 */       else if ((value instanceof UIDefaults.ActiveValue)) {
/* 226 */         value = ((UIDefaults.ActiveValue)value).createValue(null);
/*     */       }
/*     */     }
/* 229 */     return value;
/*     */   }
/*     */ 
/*     */   protected String getKey()
/*     */   {
/* 236 */     return this.key;
/*     */   }
/*     */ 
/*     */   private static class WeakPCL extends WeakReference
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     private Toolkit kit;
/*     */ 
/*     */     WeakPCL(Object target, Toolkit kit)
/*     */     {
/* 250 */       super();
/* 251 */       this.kit = kit;
/*     */     }
/*     */ 
/*     */     public void propertyChange(PropertyChangeEvent pce) {
/* 255 */       WindowsDesktopProperty property = (WindowsDesktopProperty)get();
/*     */ 
/* 257 */       if (property == null)
/*     */       {
/* 260 */         this.kit.removePropertyChangeListener(pce.getPropertyName(), this);
/*     */       }
/*     */       else {
/* 263 */         property.invalidate();
/* 264 */         property.updateUI();
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.WindowsDesktopProperty
 * JD-Core Version:    0.6.0
 */
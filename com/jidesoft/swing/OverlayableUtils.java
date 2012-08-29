/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ 
/*     */ public class OverlayableUtils
/*     */ {
/*     */   public static Overlayable getOverlayable(JComponent component)
/*     */   {
/*  20 */     Container parent = component;
/*     */     while (true) {
/*  22 */       Object o = ((JComponent)parent).getClientProperty("Overlayable.overlayable");
/*  23 */       if ((o instanceof Overlayable)) {
/*  24 */         return (Overlayable)o;
/*     */       }
/*  26 */       parent = parent.getParent();
/*  27 */       if (!(parent instanceof JComponent)) {
/*     */         break;
/*     */       }
/*     */     }
/*  31 */     return null;
/*     */   }
/*     */ 
/*     */   public static Overlayable[] getAllOverlayables(JComponent component)
/*     */   {
/*  43 */     List list = new ArrayList();
/*  44 */     Container parent = component;
/*     */     while (true) {
/*  46 */       Object o = ((JComponent)parent).getClientProperty("Overlayable.overlayable");
/*  47 */       if (((o instanceof Overlayable)) && 
/*  48 */         (!list.contains(o))) {
/*  49 */         list.add((Overlayable)o);
/*     */       }
/*     */ 
/*  52 */       parent = parent.getParent();
/*  53 */       if (parent == null) {
/*     */         break;
/*     */       }
/*     */     }
/*  57 */     return (Overlayable[])list.toArray(new Overlayable[list.size()]);
/*     */   }
/*     */ 
/*     */   public static void repaintOverlayable(JComponent component)
/*     */   {
/*  80 */     Overlayable overlayable = getOverlayable(component);
/*  81 */     if ((overlayable != null) && ((overlayable instanceof Component)))
/*  82 */       ((Component)overlayable).repaint();
/*     */   }
/*     */ 
/*     */   public static void repaintAllOverlayables(JComponent component)
/*     */   {
/*  92 */     Overlayable[] overlayables = getAllOverlayables(component);
/*  93 */     for (Overlayable overlayable : overlayables)
/*  94 */       if ((overlayable != null) && ((overlayable instanceof Component)))
/*  95 */         ((Component)overlayable).repaint();
/*     */   }
/*     */ 
/*     */   public static Icon getPredefinedOverlayIcon(String name)
/*     */   {
/* 110 */     return OverlayableIconsFactory.getImageIcon(name);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.OverlayableUtils
 * JD-Core Version:    0.6.0
 */
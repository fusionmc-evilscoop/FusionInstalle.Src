/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.event.FocusAdapter;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ import javax.swing.text.JTextComponent;
/*     */ 
/*     */ public class SelectAllUtils
/*     */ {
/*     */   public static final String CLIENT_PROPERTY_ONLYONCE = "SelectAll.onlyOnce";
/*  42 */   private static FocusListener SELECT_ALL = new FocusAdapter()
/*     */   {
/*     */     public void focusGained(FocusEvent e) {
/*  45 */       Object object = e.getSource();
/*  46 */       if ((object instanceof JTextComponent)) {
/*  47 */         ((JTextComponent)object).selectAll();
/*  48 */         Object clientProperty = ((JTextComponent)object).getClientProperty("SelectAll.onlyOnce");
/*  49 */         if (Boolean.TRUE.equals(clientProperty)) {
/*  50 */           ((JTextComponent)object).removeFocusListener(SelectAllUtils.SELECT_ALL);
/*     */         }
/*     */       }
/*  53 */       else if ((object instanceof Component)) {
/*  54 */         JideSwingUtilities.setRecursively((Component)object, new JideSwingUtilities.Handler() {
/*     */           public boolean condition(Component c) {
/*  56 */             return c instanceof JTextComponent;
/*     */           }
/*     */ 
/*     */           public void action(Component c) {
/*  60 */             ((JTextComponent)c).selectAll();
/*  61 */             Object clientProperty = ((JTextComponent)c).getClientProperty("SelectAll.onlyOnce");
/*  62 */             if (Boolean.TRUE.equals(clientProperty))
/*  63 */               c.removeFocusListener(SelectAllUtils.SELECT_ALL);
/*     */           }
/*     */ 
/*     */           public void postAction(Component c)
/*     */           {
/*     */           }
/*     */         });
/*     */       }
/*     */     }
/*  42 */   };
/*     */ 
/*     */   public static void install(Component component)
/*     */   {
/*  83 */     install(component, true);
/*     */   }
/*     */ 
/*     */   public static void install(Component component, boolean onlyOnce)
/*     */   {
/*  97 */     if ((component instanceof JTextComponent)) {
/*  98 */       if (onlyOnce) {
/*  99 */         ((JTextComponent)component).putClientProperty("SelectAll.onlyOnce", Boolean.TRUE);
/*     */       }
/* 101 */       component.addFocusListener(SELECT_ALL);
/*     */     }
/*     */     else {
/* 104 */       JideSwingUtilities.setRecursively(component, new JideSwingUtilities.Handler(onlyOnce) {
/*     */         public boolean condition(Component c) {
/* 106 */           return c instanceof JTextComponent;
/*     */         }
/*     */ 
/*     */         public void action(Component c) {
/* 110 */           if (this.val$onlyOnce) {
/* 111 */             ((JTextComponent)c).putClientProperty("SelectAll.onlyOnce", Boolean.TRUE);
/*     */           }
/* 113 */           c.addFocusListener(SelectAllUtils.SELECT_ALL);
/*     */         }
/*     */ 
/*     */         public void postAction(Component c)
/*     */         {
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void uninstall(Component component)
/*     */   {
/* 128 */     if ((component instanceof JTextComponent)) {
/* 129 */       component.removeFocusListener(SELECT_ALL);
/*     */     }
/*     */     else
/* 132 */       JideSwingUtilities.setRecursively(component, new JideSwingUtilities.Handler() {
/*     */         public boolean condition(Component c) {
/* 134 */           return c instanceof JTextComponent;
/*     */         }
/*     */ 
/*     */         public void action(Component c) {
/* 138 */           c.removeFocusListener(SelectAllUtils.SELECT_ALL);
/*     */         }
/*     */ 
/*     */         public void postAction(Component c)
/*     */         {
/*     */         }
/*     */       });
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.SelectAllUtils
 * JD-Core Version:    0.6.0
 */
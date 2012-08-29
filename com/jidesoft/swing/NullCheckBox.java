/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.FontUIResource;
/*     */ 
/*     */ public class NullCheckBox extends JCheckBox
/*     */ {
/*     */   public NullCheckBox()
/*     */   {
/*  72 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullCheckBox(Icon icon) {
/*  76 */     super(icon);
/*  77 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullCheckBox(Icon icon, boolean selected) {
/*  81 */     super(icon, selected);
/*  82 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullCheckBox(String text) {
/*  86 */     super(text);
/*  87 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullCheckBox(Action a) {
/*  91 */     super(a);
/*  92 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullCheckBox(String text, boolean selected) {
/*  96 */     super(text, selected);
/*  97 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullCheckBox(String text, Icon icon) {
/* 101 */     super(text, icon);
/* 102 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullCheckBox(String text, Icon icon, boolean selected) {
/* 106 */     super(text, icon, selected);
/* 107 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   private void clearAttribute() {
/* 111 */     super.setFont(null);
/* 112 */     super.setBackground(null);
/* 113 */     super.setForeground(null);
/*     */   }
/*     */ 
/*     */   public void setFont(Font font)
/*     */   {
/* 118 */     if ((font instanceof FontUIResource)) {
/* 119 */       return;
/*     */     }
/* 121 */     super.setFont(font);
/*     */   }
/*     */ 
/*     */   public void setBackground(Color bg)
/*     */   {
/* 126 */     if ((bg instanceof ColorUIResource)) {
/* 127 */       return;
/*     */     }
/* 129 */     super.setBackground(bg);
/*     */   }
/*     */ 
/*     */   public void setForeground(Color fg)
/*     */   {
/* 134 */     if ((fg instanceof ColorUIResource)) {
/* 135 */       return;
/*     */     }
/* 137 */     super.setForeground(fg);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.NullCheckBox
 * JD-Core Version:    0.6.0
 */
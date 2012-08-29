/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JRadioButton;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.FontUIResource;
/*     */ 
/*     */ public class NullRadioButton extends JRadioButton
/*     */ {
/*     */   public NullRadioButton()
/*     */   {
/*  72 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullRadioButton(Icon icon) {
/*  76 */     super(icon);
/*  77 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullRadioButton(Action a) {
/*  81 */     super(a);
/*  82 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullRadioButton(Icon icon, boolean selected) {
/*  86 */     super(icon, selected);
/*  87 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullRadioButton(String text) {
/*  91 */     super(text);
/*  92 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullRadioButton(String text, boolean selected) {
/*  96 */     super(text, selected);
/*  97 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullRadioButton(String text, Icon icon) {
/* 101 */     super(text, icon);
/* 102 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullRadioButton(String text, Icon icon, boolean selected) {
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
 * Qualified Name:     com.jidesoft.swing.NullRadioButton
 * JD-Core Version:    0.6.0
 */
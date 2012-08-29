/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.FontUIResource;
/*     */ 
/*     */ public class NullButton extends JButton
/*     */ {
/*     */   public NullButton()
/*     */   {
/*  72 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullButton(Icon icon) {
/*  76 */     super(icon);
/*  77 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullButton(String text) {
/*  81 */     super(text);
/*  82 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullButton(Action a) {
/*  86 */     super(a);
/*  87 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullButton(String text, Icon icon) {
/*  91 */     super(text, icon);
/*  92 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   private void clearAttribute() {
/*  96 */     super.setFont(null);
/*  97 */     super.setBackground(null);
/*  98 */     super.setForeground(null);
/*     */   }
/*     */ 
/*     */   public void setFont(Font font)
/*     */   {
/* 103 */     if ((font instanceof FontUIResource)) {
/* 104 */       return;
/*     */     }
/* 106 */     super.setFont(font);
/*     */   }
/*     */ 
/*     */   public void setBackground(Color bg)
/*     */   {
/* 111 */     if ((bg instanceof ColorUIResource)) {
/* 112 */       return;
/*     */     }
/* 114 */     super.setBackground(bg);
/*     */   }
/*     */ 
/*     */   public void setForeground(Color fg)
/*     */   {
/* 119 */     if ((fg instanceof ColorUIResource)) {
/* 120 */       return;
/*     */     }
/* 122 */     super.setForeground(fg);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.NullButton
 * JD-Core Version:    0.6.0
 */
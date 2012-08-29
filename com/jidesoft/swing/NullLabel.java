/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.FontUIResource;
/*     */ 
/*     */ public class NullLabel extends JLabel
/*     */ {
/*     */   public NullLabel()
/*     */   {
/*  72 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullLabel(String text, Icon icon, int horizontalAlignment) {
/*  76 */     super(text, icon, horizontalAlignment);
/*  77 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullLabel(String text, int horizontalAlignment) {
/*  81 */     super(text, horizontalAlignment);
/*  82 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullLabel(String text) {
/*  86 */     super(text);
/*  87 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullLabel(Icon image, int horizontalAlignment) {
/*  91 */     super(image, horizontalAlignment);
/*  92 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullLabel(Icon image) {
/*  96 */     super(image);
/*  97 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   private void clearAttribute() {
/* 101 */     super.setFont(null);
/* 102 */     super.setBackground(null);
/* 103 */     super.setForeground(null);
/*     */   }
/*     */ 
/*     */   public void setFont(Font font)
/*     */   {
/* 108 */     if ((font instanceof FontUIResource)) {
/* 109 */       return;
/*     */     }
/* 111 */     super.setFont(font);
/*     */   }
/*     */ 
/*     */   public void setBackground(Color bg)
/*     */   {
/* 116 */     if ((bg instanceof ColorUIResource)) {
/* 117 */       return;
/*     */     }
/* 119 */     super.setBackground(bg);
/*     */   }
/*     */ 
/*     */   public void setForeground(Color fg)
/*     */   {
/* 124 */     if ((fg instanceof ColorUIResource)) {
/* 125 */       return;
/*     */     }
/* 127 */     super.setForeground(fg);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.NullLabel
 * JD-Core Version:    0.6.0
 */
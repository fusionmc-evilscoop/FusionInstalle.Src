/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.FontUIResource;
/*     */ 
/*     */ public class NullTristateCheckBox extends TristateCheckBox
/*     */ {
/*     */   public NullTristateCheckBox()
/*     */   {
/*  72 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullTristateCheckBox(String text) {
/*  76 */     super(text);
/*  77 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullTristateCheckBox(String text, TristateCheckBox.State initial) {
/*  81 */     super(text, initial);
/*  82 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullTristateCheckBox(String text, Icon icon, TristateCheckBox.State initial) {
/*  86 */     super(text, icon, initial);
/*  87 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   private void clearAttribute() {
/*  91 */     super.setFont(null);
/*  92 */     super.setBackground(null);
/*  93 */     super.setForeground(null);
/*     */   }
/*     */ 
/*     */   public void setFont(Font font)
/*     */   {
/*  98 */     if ((font instanceof FontUIResource)) {
/*  99 */       return;
/*     */     }
/* 101 */     super.setFont(font);
/*     */   }
/*     */ 
/*     */   public void setBackground(Color bg)
/*     */   {
/* 106 */     if ((bg instanceof ColorUIResource)) {
/* 107 */       return;
/*     */     }
/* 109 */     super.setBackground(bg);
/*     */   }
/*     */ 
/*     */   public void setForeground(Color fg)
/*     */   {
/* 114 */     if ((fg instanceof ColorUIResource)) {
/* 115 */       return;
/*     */     }
/* 117 */     super.setForeground(fg);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.NullTristateCheckBox
 * JD-Core Version:    0.6.0
 */
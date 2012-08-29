/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.LayoutManager;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.FontUIResource;
/*     */ 
/*     */ public class NullPanel extends JPanel
/*     */ {
/*     */   public NullPanel()
/*     */   {
/*  72 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullPanel(boolean isDoubleBuffered) {
/*  76 */     super(isDoubleBuffered);
/*  77 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullPanel(LayoutManager layout) {
/*  81 */     super(layout);
/*  82 */     clearAttribute();
/*     */   }
/*     */ 
/*     */   public NullPanel(LayoutManager layout, boolean isDoubleBuffered) {
/*  86 */     super(layout, isDoubleBuffered);
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
 * Qualified Name:     com.jidesoft.swing.NullPanel
 * JD-Core Version:    0.6.0
 */
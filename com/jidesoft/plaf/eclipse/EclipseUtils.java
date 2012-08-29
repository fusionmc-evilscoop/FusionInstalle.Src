/*     */ package com.jidesoft.plaf.eclipse;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Stroke;
/*     */ 
/*     */ public class EclipseUtils
/*     */ {
/*  84 */   private static final BasicStroke DOTTED_STROKE = new BasicStroke(1.0F, 2, 1, 1.0F, new float[] { 0.0F, 2.0F, 0.0F, 2.0F }, 0.0F);
/*     */   static final double RATIO1 = 0.67D;
/*     */   static final double RATIO2 = 0.78D;
/*     */   static final double RATIO3 = 0.86D;
/*     */ 
/*     */   static Color getLighterColor(Color backColor)
/*     */   {
/*  15 */     int r = getLighterColor(backColor.getRed());
/*  16 */     int g = getLighterColor(backColor.getGreen());
/*  17 */     int b = getLighterColor(backColor.getBlue());
/*  18 */     if (r >= 255) r = 255;
/*  19 */     if (g >= 255) g = 255;
/*  20 */     if (b >= 255) b = 255;
/*  21 */     return new Color(r, g, b);
/*     */   }
/*     */ 
/*     */   static int getLighterColor(int x) {
/*  25 */     return (int)(x * 36.0D / 255.0D + 219.5D);
/*     */   }
/*     */ 
/*     */   static Color getMenuSelectionColor(Color backColor)
/*     */   {
/*  30 */     int r = getMenuSelectionValue(backColor.getRed());
/*  31 */     int g = getMenuSelectionValue(backColor.getGreen());
/*  32 */     int b = getMenuSelectionValue(backColor.getBlue());
/*  33 */     if (r >= 255) r = 255;
/*  34 */     if (g >= 255) g = 255;
/*  35 */     if (b >= 255) b = 255;
/*  36 */     return new Color(r, g, b);
/*     */   }
/*     */ 
/*     */   static int getMenuSelectionValue(int x) {
/*  40 */     return (int)(x * 76.0D / 255.0D + 179.5D);
/*     */   }
/*     */ 
/*     */   static Color getMenuBackgroundColor(Color backColor) {
/*  44 */     int r = getMenuValue(backColor.getRed());
/*  45 */     int g = getMenuValue(backColor.getGreen());
/*  46 */     int b = getMenuValue(backColor.getBlue());
/*  47 */     if (r >= 255) r = 255;
/*  48 */     if (g >= 255) g = 255;
/*  49 */     if (b >= 255) b = 255;
/*  50 */     return new Color(r, g, b);
/*     */   }
/*     */ 
/*     */   static int getMenuValue(int x) {
/*  54 */     return (int)(x * 36.0D / 255.0D + 219.5D);
/*     */   }
/*     */ 
/*     */   static Color getDefaultBackgroundColor(Color backColor)
/*     */   {
/*     */     Color backIDE;
/*     */     Color backIDE;
/*  61 */     if ((backColor.getRed() == 212) && (backColor.getGreen() == 208) && (backColor.getBlue() == 200))
/*     */     {
/*  63 */       backIDE = new Color(247, 243, 233);
/*     */     }
/*     */     else
/*     */     {
/*     */       Color backIDE;
/*  65 */       if ((backColor.getRed() == 236) && (backColor.getGreen() == 233) && (backColor.getBlue() == 216))
/*     */       {
/*  68 */         backIDE = new Color(255, 251, 233);
/*     */       }
/*     */       else
/*     */       {
/*  72 */         int r = backColor.getRed() + 35;
/*  73 */         int g = backColor.getGreen() + 35;
/*  74 */         int b = backColor.getBlue() + 35;
/*  75 */         if (r >= 255) r = 255;
/*  76 */         if (g >= 255) g = 255;
/*  77 */         if (b >= 255) b = 255;
/*  78 */         backIDE = new Color(r, g, b);
/*     */       }
/*     */     }
/*  81 */     return backIDE;
/*     */   }
/*     */ 
/*     */   public static void fillRectWithHatch(Graphics2D g, Rectangle rect, Color color)
/*     */   {
/*  88 */     Stroke oldStroke = g.getStroke();
/*     */ 
/*  90 */     g.setColor(Color.white);
/*  91 */     g.fillRect(rect.x, rect.y, rect.width, rect.height);
/*     */ 
/*  93 */     g.setColor(color);
/*  94 */     g.setStroke(DOTTED_STROKE);
/*     */ 
/*  96 */     for (int i = 0; i < rect.width; i++) {
/*  97 */       if (i % 2 == 0) {
/*  98 */         g.drawLine(rect.x + i, rect.y, rect.x + i, rect.y + rect.height - 1);
/*     */       }
/*     */       else {
/* 101 */         g.drawLine(rect.x + i, rect.y + 1, rect.x + i, rect.y + rect.height - 1);
/*     */       }
/*     */     }
/* 104 */     g.setStroke(oldStroke);
/*     */   }
/*     */ 
/*     */   static int getLightColor(int x, double ratio)
/*     */   {
/* 112 */     return (int)((255 - x) * ratio + x);
/*     */   }
/*     */ 
/*     */   static Color getLighterColor(Color backColor, double ratio) {
/* 116 */     int r = getLightColor(backColor.getRed(), ratio);
/* 117 */     int g = getLightColor(backColor.getGreen(), ratio) + 1;
/* 118 */     int b = getLightColor(backColor.getBlue(), ratio);
/* 119 */     if (r >= 255) r = 255;
/* 120 */     if (g >= 255) g = 255;
/* 121 */     if (b >= 255) b = 255;
/* 122 */     return new Color(r, g, b);
/*     */   }
/*     */ 
/*     */   static Color getSelectedAndFocusedButtonColor(Color backColor) {
/* 126 */     return getLighterColor(backColor, 0.67D);
/*     */   }
/*     */ 
/*     */   static Color getFocusedButtonColor(Color backColor) {
/* 130 */     return getLighterColor(backColor, 0.78D);
/*     */   }
/*     */ 
/*     */   static Color getSelectedButtonColor(Color backColor) {
/* 134 */     return getLighterColor(backColor, 0.86D);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.eclipse.EclipseUtils
 * JD-Core Version:    0.6.0
 */
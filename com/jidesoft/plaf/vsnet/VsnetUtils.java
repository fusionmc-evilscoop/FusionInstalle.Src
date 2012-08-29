/*     */ package com.jidesoft.plaf.vsnet;
/*     */ 
/*     */ import com.jidesoft.utils.ColorUtils;
/*     */ import java.awt.Color;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ 
/*     */ class VsnetUtils
/*     */ {
/*  27 */   static final Color DARK_GREEN = new Color(0, 128, 0);
/*  28 */   static final Color DARK_MAGENTA = new Color(128, 0, 128);
/*     */ 
/* 100 */   private static double RATIO1 = 0.8D;
/* 101 */   private static double RATIO2 = 0.9200000166893005D;
/* 102 */   private static double RATIO3 = 0.8600000143051148D;
/*     */ 
/*     */   static Color getLighterColor(Color color)
/*     */   {
/*  18 */     if (Color.BLACK.equals(color)) {
/*  19 */       return color;
/*     */     }
/*  21 */     if (Color.WHITE.equals(color)) {
/*  22 */       return color;
/*     */     }
/*  24 */     return ColorUtils.getDerivedColor(color, 0.93F);
/*     */   }
/*     */ 
/*     */   static Color getMenuSelectionColor(Color color)
/*     */   {
/*  31 */     if ((DARK_GREEN.equals(color)) || (DARK_MAGENTA.equals(color))) {
/*  32 */       return color;
/*     */     }
/*  34 */     return ColorUtils.getDerivedColor(color, 0.8555F);
/*     */   }
/*     */ 
/*     */   static Color getMenuBackgroundColor(Color color) {
/*  38 */     return getLighterColor(color);
/*     */   }
/*     */ 
/*     */   static Color getToolBarBackgroundColor(Color color) {
/*  42 */     if (Color.BLACK.equals(color)) {
/*  43 */       return color;
/*     */     }
/*  45 */     if (Color.WHITE.equals(color)) {
/*  46 */       return color;
/*     */     }
/*  48 */     return ColorUtils.getDerivedColor(color, 0.645F);
/*     */   }
/*     */ 
/*     */   static Color getGripperForegroundColor(Color color) {
/*  52 */     int r = getGripperValue(color.getRed());
/*  53 */     int g = getGripperValue(color.getGreen());
/*  54 */     int b = getGripperValue(color.getBlue());
/*  55 */     if (r >= 255) r = 255;
/*  56 */     if (g >= 255) g = 255;
/*  57 */     if (b >= 255) b = 255;
/*  58 */     return new ColorUIResource(r, g, b);
/*     */   }
/*     */ 
/*     */   static int getGripperValue(int x) {
/*  62 */     if (x == 255) {
/*  63 */       return 0;
/*     */     }
/*  65 */     if ((x >= 0) && (x <= 64)) {
/*  66 */       return x * 33 / 64 + 123;
/*     */     }
/*     */ 
/*  69 */     return (x - 65) * 157 / 189 + 33;
/*     */   }
/*     */ 
/*     */   static Color getDefaultBackgroundColor(Color color)
/*     */   {
/*     */     Color backIDE;
/*     */     Color backIDE;
/*  77 */     if ((color.getRed() == 212) && (color.getGreen() == 208) && (color.getBlue() == 200))
/*     */     {
/*  79 */       backIDE = new ColorUIResource(247, 243, 233);
/*     */     }
/*     */     else
/*     */     {
/*     */       Color backIDE;
/*  81 */       if ((color.getRed() == 236) && (color.getGreen() == 233) && (color.getBlue() == 216))
/*     */       {
/*  84 */         backIDE = new ColorUIResource(255, 251, 233);
/*     */       }
/*     */       else
/*     */       {
/*  88 */         int r = color.getRed() + 35;
/*  89 */         int g = color.getGreen() + 35;
/*  90 */         int b = color.getBlue() + 35;
/*  91 */         if (r >= 255) r = 255;
/*  92 */         if (g >= 255) g = 255;
/*  93 */         if (b >= 255) b = 255;
/*  94 */         backIDE = new ColorUIResource(r, g, b);
/*     */       }
/*     */     }
/*  97 */     return backIDE;
/*     */   }
/*     */ 
/*     */   public static void setColorRatios(double selectedAndFocused, double rollover, double selected)
/*     */   {
/* 112 */     RATIO1 = selectedAndFocused;
/* 113 */     RATIO2 = rollover;
/* 114 */     RATIO3 = selected;
/*     */   }
/*     */ 
/*     */   static int getLightColor(int x, double ratio) {
/* 118 */     return (int)((255 - x) * ratio + x);
/*     */   }
/*     */ 
/*     */   static Color getLighterColor(Color color, float ratio) {
/* 122 */     if ((DARK_GREEN.equals(color)) || (DARK_MAGENTA.equals(color))) {
/* 123 */       return color;
/*     */     }
/* 125 */     return ColorUtils.getDerivedColor(color, ratio);
/*     */   }
/*     */ 
/*     */   static Color getSelectedAndRolloverButtonColor(Color color) {
/* 129 */     return getLighterColor(color, (float)RATIO1);
/*     */   }
/*     */ 
/*     */   static Color getRolloverButtonColor(Color color) {
/* 133 */     return getLighterColor(color, (float)RATIO2);
/*     */   }
/*     */ 
/*     */   static Color getSelectedButtonColor(Color color) {
/* 137 */     return getLighterColor(color, (float)RATIO3);
/*     */   }
/*     */ 
/*     */   static Color getButtonBorderColor(Color color) {
/* 141 */     if ((DARK_GREEN.equals(color)) || (DARK_MAGENTA.equals(color))) {
/* 142 */       return new ColorUIResource(Color.WHITE);
/*     */     }
/* 144 */     return color;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.vsnet.VsnetUtils
 * JD-Core Version:    0.6.0
 */
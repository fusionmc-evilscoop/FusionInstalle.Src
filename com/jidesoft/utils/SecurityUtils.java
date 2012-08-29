/*     */ package com.jidesoft.utils;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.FontFormatException;
/*     */ import java.io.IOException;
/*     */ import java.security.AccessControlException;
/*     */ import java.util.Locale;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.plaf.FontUIResource;
/*     */ 
/*     */ public class SecurityUtils
/*     */ {
/*     */   public static final String BOLD = "Bold";
/*     */   public static final String ITALIC = "Italic";
/*     */   public static final String BOLD_ITALIC = "Bold Italic";
/* 130 */   private static boolean _AWTEventListenerDisabled = false;
/*     */ 
/* 153 */   private static boolean _translucentWindowFeatureDisabled = (!SystemInfo.isJdk6u10Above()) || (!SystemInfo.isWindows());
/*     */ 
/*     */   public static FontUIResource createFontUIResource(String name, int style, int size)
/*     */   {
/*  21 */     Font font = createFont(name, style, size);
/*  22 */     if (font != null) {
/*  23 */       return new FontUIResource(font);
/*     */     }
/*     */ 
/*  26 */     return null;
/*     */   }
/*     */ 
/*     */   private static String createFontStrings(String font, int style)
/*     */   {
/*     */     String fontString;
/*  41 */     switch (style) {
/*     */     case 1:
/*  43 */       fontString = font + " " + "Bold";
/*  44 */       break;
/*     */     case 2:
/*  46 */       fontString = font + " " + "Italic";
/*  47 */       break;
/*     */     case 3:
/*  49 */       fontString = font + " " + "Bold Italic";
/*  50 */       break;
/*     */     case 0:
/*     */     default:
/*  53 */       fontString = font;
/*     */     }
/*     */ 
/*  56 */     return fontString.replace(' ', '_');
/*     */   }
/*     */ 
/*     */   public static Font createFont(String name, int style, int size)
/*     */   {
/*     */     try
/*     */     {
/*  71 */       return new Font(name, style, size);
/*     */     }
/*     */     catch (AccessControlException e)
/*     */     {
/*  75 */       ClassLoader cl = SecurityUtils.class.getClassLoader();
/*     */       try {
/*  77 */         String value = null;
/*     */         try {
/*  79 */           value = FontFilesResource.getResourceBundle(Locale.getDefault()).getString(createFontStrings(name, style));
/*     */         }
/*     */         catch (MissingResourceException me1) {
/*     */           try {
/*  83 */             value = FontFilesResource.getResourceBundle(Locale.getDefault()).getString(name);
/*     */           }
/*     */           catch (MissingResourceException me2)
/*     */           {
/*     */           }
/*     */         }
/*  89 */         if (value == null) {
/*  90 */           return null;
/*     */         }
/*     */ 
/*  94 */         Font font = Font.createFont(0, cl.getResourceAsStream(value));
/*     */ 
/*  96 */         if (font != null) {
/*  97 */           return font.deriveFont(style, size);
/*     */         }
/*     */       }
/*     */       catch (FontFormatException e1)
/*     */       {
/* 102 */         e1.printStackTrace();
/* 103 */         throw e;
/*     */       }
/*     */       catch (IOException e1) {
/* 106 */         e1.printStackTrace();
/* 107 */         throw e;
/*     */       }
/*     */     }
/* 110 */     return null;
/*     */   }
/*     */ 
/*     */   public static String getProperty(String key, String defaultValue)
/*     */   {
/*     */     try
/*     */     {
/* 123 */       return System.getProperty(key, defaultValue);
/*     */     } catch (AccessControlException e) {
/*     */     }
/* 126 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   public static boolean isAWTEventListenerDisabled()
/*     */   {
/* 140 */     return _AWTEventListenerDisabled;
/*     */   }
/*     */ 
/*     */   public static void setAWTEventListenerDisabled(boolean AWTEventListenerDisabled)
/*     */   {
/* 150 */     _AWTEventListenerDisabled = AWTEventListenerDisabled;
/*     */   }
/*     */ 
/*     */   public static boolean isTranslucentWindowFeatureDisabled()
/*     */   {
/* 162 */     return _translucentWindowFeatureDisabled;
/*     */   }
/*     */ 
/*     */   public static void setTranslucentWindowFeatureDisabled(boolean translucentWindowFeatureDisabled)
/*     */   {
/* 173 */     _translucentWindowFeatureDisabled = translucentWindowFeatureDisabled;
/*     */   }
/*     */ 
/*     */   static class FontStruct
/*     */   {
/*     */     String font;
/*     */     int style;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.SecurityUtils
 * JD-Core Version:    0.6.0
 */
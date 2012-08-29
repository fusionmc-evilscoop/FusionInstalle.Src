/*     */ package com.jidesoft.plaf.aqua;
/*     */ 
/*     */ import com.jidesoft.utils.SecurityUtils;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ class AquaPreferences
/*     */ {
/*  33 */   private static final Logger LOGGER = Logger.getLogger(AquaPreferences.class.getName());
/*     */   private static HashMap prefs;
/*     */ 
/*     */   public static String getString(String key)
/*     */   {
/*  44 */     return (String)get(key);
/*     */   }
/*     */ 
/*     */   public static Object get(String key) {
/*  48 */     if (prefs == null) {
/*  49 */       prefs = new HashMap();
/*  50 */       loadGlobalPreferences();
/*     */     }
/*     */ 
/*  53 */     return prefs.get(key);
/*     */   }
/*     */ 
/*     */   private static void loadGlobalPreferences()
/*     */   {
/*  64 */     prefs.put("AppleAquaColorVariant", "1");
/*     */ 
/*  66 */     prefs.put("AppleHighlightColor", "0.709800 0.835300 1.000000");
/*     */ 
/*  68 */     prefs.put("AppleCollationOrder", "en");
/*     */ 
/*  70 */     prefs.put("AppleScrollBarVariant", "DoubleMax");
/*     */ 
/*  72 */     prefs.put("AppleScrollerPagingBehavior", "false");
/*     */ 
/*  74 */     File globalPrefsFile = new File(SecurityUtils.getProperty("user.home", "") + "/Library/Preferences/.GlobalPreferences.plist");
/*     */     try
/*     */     {
/*  79 */       XMLElement xml = readPList(globalPrefsFile);
/*  80 */       for (i0 = xml.iterateChildren(); i0.hasNext(); ) {
/*  81 */         XMLElement xml1 = (XMLElement)i0.next();
/*     */ 
/*  83 */         key = null;
/*  84 */         for (i1 = xml1.iterateChildren(); i1.hasNext(); ) {
/*  85 */           XMLElement xml2 = (XMLElement)i1.next();
/*  86 */           if (xml2.getName().equals("key")) {
/*  87 */             key = xml2.getContent();
/*     */           }
/*     */           else {
/*  90 */             if (key != null)
/*     */             {
/*  92 */               prefs.put(key, xml2.getContent());
/*     */             }
/*  94 */             key = null;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*     */       Iterator i0;
/*     */       String key;
/*     */       Iterator i1;
/* 100 */       LOGGER.warning("AquaPreferences failed to load Mac OS X global system preferences - " + e.getLocalizedMessage());
/*     */     }
/*     */     catch (Exception e) {
/* 103 */       LOGGER.warning("AquaPreferences failed to load Mac OS X global system preferences - " + e.getLocalizedMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   private static XMLElement readPList(File plistFile)
/*     */     throws IOException
/*     */   {
/* 113 */     FileReader reader = null;
/* 114 */     XMLElement xml = null;
/*     */     try {
/* 116 */       reader = new FileReader(plistFile);
/* 117 */       xml = new XMLElement(new HashMap(), false, false);
/*     */       try {
/* 119 */         xml.parseFromReader(reader);
/*     */       }
/*     */       catch (XMLParseException e) {
/* 122 */         xml = new BinaryPListParser().parse(plistFile);
/*     */       }
/*     */     }
/*     */     finally {
/* 126 */       if (reader != null) {
/* 127 */         reader.close();
/*     */       }
/*     */     }
/* 130 */     return xml;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.aqua.AquaPreferences
 * JD-Core Version:    0.6.0
 */
/*     */ package org.apache.commons.io;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public final class IOCase
/*     */   implements Serializable
/*     */ {
/*  43 */   public static final IOCase SENSITIVE = new IOCase("Sensitive", true);
/*     */ 
/*  47 */   public static final IOCase INSENSITIVE = new IOCase("Insensitive", false);
/*     */ 
/*  55 */   public static final IOCase SYSTEM = new IOCase("System", !FilenameUtils.isSystemWindows());
/*     */   private static final long serialVersionUID = -6343169151696340687L;
/*     */   private final String name;
/*     */   private final transient boolean sensitive;
/*     */ 
/*     */   public static IOCase forName(String name)
/*     */   {
/*  74 */     if (SENSITIVE.name.equals(name)) {
/*  75 */       return SENSITIVE;
/*     */     }
/*  77 */     if (INSENSITIVE.name.equals(name)) {
/*  78 */       return INSENSITIVE;
/*     */     }
/*  80 */     if (SYSTEM.name.equals(name)) {
/*  81 */       return SYSTEM;
/*     */     }
/*  83 */     throw new IllegalArgumentException("Invalid IOCase name: " + name);
/*     */   }
/*     */ 
/*     */   private IOCase(String name, boolean sensitive)
/*     */   {
/*  94 */     this.name = name;
/*  95 */     this.sensitive = sensitive;
/*     */   }
/*     */ 
/*     */   private Object readResolve()
/*     */   {
/* 105 */     return forName(this.name);
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 115 */     return this.name;
/*     */   }
/*     */ 
/*     */   public boolean isCaseSensitive()
/*     */   {
/* 124 */     return this.sensitive;
/*     */   }
/*     */ 
/*     */   public boolean checkEquals(String str1, String str2)
/*     */   {
/* 140 */     if ((str1 == null) || (str2 == null)) {
/* 141 */       throw new NullPointerException("The strings must not be null");
/*     */     }
/* 143 */     return this.sensitive ? str1.equals(str2) : str1.equalsIgnoreCase(str2);
/*     */   }
/*     */ 
/*     */   public boolean checkStartsWith(String str, String start)
/*     */   {
/* 158 */     return str.regionMatches(!this.sensitive, 0, start, 0, start.length());
/*     */   }
/*     */ 
/*     */   public boolean checkEndsWith(String str, String end)
/*     */   {
/* 173 */     int endLen = end.length();
/* 174 */     return str.regionMatches(!this.sensitive, str.length() - endLen, end, 0, endLen);
/*     */   }
/*     */ 
/*     */   public boolean checkRegionMatches(String str, int strStartIndex, String search)
/*     */   {
/* 190 */     return str.regionMatches(!this.sensitive, strStartIndex, search, 0, search.length());
/*     */   }
/*     */ 
/*     */   String convertCase(String str)
/*     */   {
/* 201 */     if (str == null) {
/* 202 */       return null;
/*     */     }
/* 204 */     return this.sensitive ? str : str.toLowerCase();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 214 */     return this.name;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.IOCase
 * JD-Core Version:    0.6.0
 */
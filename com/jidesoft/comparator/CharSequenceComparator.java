/*     */ package com.jidesoft.comparator;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ 
/*     */ public class CharSequenceComparator
/*     */   implements Comparator, Serializable
/*     */ {
/*     */   private boolean _caseSensitive;
/*  21 */   public static final ComparatorContext CONTEXT = new ComparatorContext("IgnoreLocale");
/*  22 */   public static final ComparatorContext CONTEXT_IGNORE_CASE = new ComparatorContext("IgnoreLocale_Ignorecase");
/*     */ 
/*     */   public CharSequenceComparator()
/*     */   {
/*  28 */     this(true);
/*     */   }
/*     */ 
/*     */   public CharSequenceComparator(boolean caseSensitive) {
/*  32 */     this._caseSensitive = caseSensitive;
/*     */   }
/*     */ 
/*     */   public boolean isCaseSensitive()
/*     */   {
/*  41 */     return this._caseSensitive;
/*     */   }
/*     */ 
/*     */   public void setCaseSensitive(boolean caseSensitive)
/*     */   {
/*  50 */     this._caseSensitive = caseSensitive;
/*     */   }
/*     */ 
/*     */   public int compare(Object o1, Object o2) {
/*  54 */     if ((o1 == null) && (o2 == null)) {
/*  55 */       return 0;
/*     */     }
/*  57 */     if (o1 == null) {
/*  58 */       return -1;
/*     */     }
/*  60 */     if (o2 == null) {
/*  61 */       return 1;
/*     */     }
/*     */ 
/*  64 */     if ((o1 instanceof CharSequence)) {
/*  65 */       if ((o2 instanceof CharSequence)) {
/*  66 */         CharSequence s1 = (CharSequence)o1;
/*  67 */         CharSequence s2 = (CharSequence)o2;
/*  68 */         return isCaseSensitive() ? compareCase(s1, s2) : compareIgnoreCase(s1, s2);
/*     */       }
/*     */ 
/*  72 */       throw new ClassCastException("The second argument of this method was not a CharSequence: " + o2.getClass().getName());
/*     */     }
/*     */ 
/*  75 */     if ((o2 instanceof Comparable))
/*     */     {
/*  77 */       throw new ClassCastException("The first argument of this method was not a CharSequence: " + o1.getClass().getName());
/*     */     }
/*     */ 
/*  81 */     throw new ClassCastException("Both arguments of this method were not CharSequences: " + o1.getClass().getName() + " and " + o2.getClass().getName());
/*     */   }
/*     */ 
/*     */   private int compareCase(CharSequence s1, CharSequence s2)
/*     */   {
/*  86 */     int len1 = s1.length();
/*  87 */     int len2 = s2.length();
/*  88 */     int n = Math.min(len1, len2);
/*     */ 
/*  90 */     int k = 0;
/*  91 */     while (k < n) {
/*  92 */       char c1 = s1.charAt(k);
/*  93 */       char c2 = s2.charAt(k);
/*  94 */       if (c1 != c2) {
/*  95 */         return c1 - c2;
/*     */       }
/*  97 */       k++;
/*     */     }
/*  99 */     return len1 - len2;
/*     */   }
/*     */ 
/*     */   private int compareIgnoreCase(CharSequence s1, CharSequence s2) {
/* 103 */     int n1 = s1.length(); int n2 = s2.length();
/* 104 */     int i1 = 0; for (int i2 = 0; (i1 < n1) && (i2 < n2); i2++) {
/* 105 */       char c1 = s1.charAt(i1);
/* 106 */       char c2 = s2.charAt(i2);
/* 107 */       if (c1 != c2) {
/* 108 */         c1 = Character.toUpperCase(c1);
/* 109 */         c2 = Character.toUpperCase(c2);
/* 110 */         if (c1 != c2) {
/* 111 */           c1 = Character.toLowerCase(c1);
/* 112 */           c2 = Character.toLowerCase(c2);
/* 113 */           if (c1 != c2)
/* 114 */             return c1 - c2;
/*     */         }
/*     */       }
/* 104 */       i1++;
/*     */     }
/*     */ 
/* 119 */     return n1 - n2;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.comparator.CharSequenceComparator
 * JD-Core Version:    0.6.0
 */
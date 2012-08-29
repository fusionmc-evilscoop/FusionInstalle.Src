/*     */ package com.jidesoft.comparator;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ 
/*     */ public class NumberComparator
/*     */   implements Comparator
/*     */ {
/*  12 */   public static final ComparatorContext CONTEXT_ABSOLUTE = new ComparatorContext("AbsoluteValue");
/*     */ 
/*  14 */   private static NumberComparator singleton = null;
/*     */ 
/*  16 */   private boolean _absolute = false;
/*     */ 
/*     */   public static NumberComparator getInstance()
/*     */   {
/*  33 */     if (singleton == null)
/*  34 */       singleton = new NumberComparator();
/*  35 */     return singleton;
/*     */   }
/*     */ 
/*     */   public int compare(Object o1, Object o2)
/*     */   {
/*  46 */     if ((o1 == null) && (o2 == null)) {
/*  47 */       return 0;
/*     */     }
/*  49 */     if (o1 == null) {
/*  50 */       return -1;
/*     */     }
/*  52 */     if (o2 == null) {
/*  53 */       return 1;
/*     */     }
/*     */ 
/*  56 */     if ((o1 instanceof Number)) {
/*  57 */       if ((o2 instanceof Number)) {
/*  58 */         long l1 = 0L;
/*  59 */         long l2 = 0L;
/*  60 */         double d1 = 0.0D;
/*  61 */         double d2 = 0.0D;
/*  62 */         if ((o1 instanceof Long)) {
/*  63 */           l1 = ((Number)o1).longValue();
/*     */         }
/*     */         else {
/*  66 */           d1 = ((Number)o1).doubleValue();
/*     */         }
/*  68 */         if ((o2 instanceof Long)) {
/*  69 */           l2 = ((Number)o2).longValue();
/*     */         }
/*     */         else {
/*  72 */           d2 = ((Number)o2).doubleValue();
/*     */         }
/*     */ 
/*  75 */         if (isAbsolute()) {
/*  76 */           if (d1 < 0.0D) {
/*  77 */             d1 = -d1;
/*     */           }
/*  79 */           if (d2 < 0.0D) {
/*  80 */             d2 = -d2;
/*     */           }
/*  82 */           if (l1 < 0L) {
/*  83 */             l1 = -l1;
/*     */           }
/*  85 */           if (l2 < 0L) {
/*  86 */             l2 = -l2;
/*     */           }
/*     */         }
/*     */ 
/*  90 */         if (((o1 instanceof Long)) && ((o2 instanceof Long))) {
/*  91 */           if (l1 < l2)
/*  92 */             return -1;
/*  93 */           if (l1 > l2) {
/*  94 */             return 1;
/*     */           }
/*  96 */           return 0;
/*     */         }
/*  98 */         if ((o1 instanceof Long)) {
/*  99 */           if (l1 < d2)
/* 100 */             return -1;
/* 101 */           if (l1 > d2) {
/* 102 */             return 1;
/*     */           }
/* 104 */           return 0;
/*     */         }
/* 106 */         if ((o2 instanceof Long)) {
/* 107 */           if (d1 < l2)
/* 108 */             return -1;
/* 109 */           if (d1 > l2) {
/* 110 */             return 1;
/*     */           }
/* 112 */           return 0;
/*     */         }
/*     */ 
/* 115 */         if (d1 < d2)
/* 116 */           return -1;
/* 117 */         if (d1 > d2) {
/* 118 */           return 1;
/*     */         }
/* 120 */         return 0;
/*     */       }
/*     */ 
/* 125 */       throw new ClassCastException("The first argument of this method was not a Number but " + o2.getClass().getName());
/*     */     }
/*     */ 
/* 131 */     if ((o2 instanceof Number))
/*     */     {
/* 133 */       throw new ClassCastException("The second argument of this method was not a Number but " + o1.getClass().getName());
/*     */     }
/*     */ 
/* 140 */     throw new ClassCastException("Both arguments of this method were not Numbers. They are " + o1.getClass().getName() + " and " + o2.getClass().getName());
/*     */   }
/*     */ 
/*     */   public boolean isAbsolute()
/*     */   {
/* 153 */     return this._absolute;
/*     */   }
/*     */ 
/*     */   public void setAbsolute(boolean absolute)
/*     */   {
/* 162 */     this._absolute = absolute;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.comparator.NumberComparator
 * JD-Core Version:    0.6.0
 */
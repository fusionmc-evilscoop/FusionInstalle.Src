/*     */ package com.jidesoft.comparator;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ 
/*     */ public class ComparableComparator
/*     */   implements Comparator, Serializable
/*     */ {
/*  72 */   private static final ComparableComparator instance = new ComparableComparator();
/*     */   private static final long serialVersionUID = -291439688585137865L;
/*     */ 
/*     */   public static ComparableComparator getInstance()
/*     */   {
/*  83 */     return instance;
/*     */   }
/*     */ 
/*     */   public int compare(Object o1, Object o2)
/*     */   {
/*  95 */     if ((o1 == null) && (o2 == null)) {
/*  96 */       return 0;
/*     */     }
/*  98 */     if (o1 == null) {
/*  99 */       return -1;
/*     */     }
/* 101 */     if (o2 == null) {
/* 102 */       return 1;
/*     */     }
/*     */ 
/* 105 */     if ((o1 instanceof Comparable)) {
/* 106 */       if ((o2 instanceof Comparable)) { int result1;
/*     */         int result2;
/*     */         try { result1 = ((Comparable)o1).compareTo(o2);
/* 111 */           result2 = ((Comparable)o2).compareTo(o1);
/*     */         } catch (ClassCastException e)
/*     */         {
/* 114 */           return o1.getClass().getName().compareTo(o2.getClass().getName());
/*     */         }
/*     */ 
/* 118 */         if ((result1 == 0) && (result2 == 0)) {
/* 119 */           return 0;
/*     */         }
/*     */ 
/* 122 */         if ((result1 < 0) && (result2 > 0)) {
/* 123 */           return result1;
/*     */         }
/*     */ 
/* 126 */         if ((result1 > 0) && (result2 < 0)) {
/* 127 */           return result1;
/*     */         }
/*     */ 
/* 131 */         throw new ClassCastException("The two compareTo methods of o1 and o2 returned two inconsistent results. Please make sure sgn(x.compareTo(y)) == -sgn(y.compareTo(x)) for all x and y.");
/*     */       }
/*     */ 
/* 136 */       throw new ClassCastException("The second argument of this method was not a Comparable: " + o2.getClass().getName());
/*     */     }
/*     */ 
/* 139 */     if ((o2 instanceof Comparable))
/*     */     {
/* 141 */       throw new ClassCastException("The first argument of this method was not a Comparable: " + o1.getClass().getName());
/*     */     }
/*     */ 
/* 145 */     throw new ClassCastException("Both arguments of this method were not Comparables: " + o1.getClass().getName() + " and " + o2.getClass().getName());
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.comparator.ComparableComparator
 * JD-Core Version:    0.6.0
 */
/*     */ package com.jidesoft.utils;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public final class MathUtils
/*     */ {
/*     */   public static double sum(List<Number> numbers)
/*     */   {
/*  21 */     double sum = 0.0D;
/*  22 */     for (Number value : numbers) {
/*  23 */       sum += value.doubleValue();
/*     */     }
/*  25 */     return sum;
/*     */   }
/*     */ 
/*     */   public static double mean(List<Number> numbers)
/*     */   {
/*  35 */     double sum = sum(numbers);
/*  36 */     return sum / numbers.size();
/*     */   }
/*     */ 
/*     */   public static double min(List<Number> numbers)
/*     */   {
/*  46 */     double min = 2147483647.0D;
/*  47 */     for (Number value : numbers) {
/*  48 */       double v = value.doubleValue();
/*  49 */       if (v < min) {
/*  50 */         min = v;
/*     */       }
/*     */     }
/*  53 */     return min;
/*     */   }
/*     */ 
/*     */   public static double max(List<Number> numbers)
/*     */   {
/*  63 */     double max = -2147483648.0D;
/*  64 */     for (Number value : numbers) {
/*  65 */       double v = value.doubleValue();
/*  66 */       if (v > max) {
/*  67 */         max = v;
/*     */       }
/*     */     }
/*  70 */     return max;
/*     */   }
/*     */ 
/*     */   public static double stddev(List<Number> numbers, boolean biasCorrected)
/*     */   {
/*  84 */     double stddev = (0.0D / 0.0D);
/*  85 */     int n = numbers.size();
/*  86 */     if (n > 0) {
/*  87 */       if (n > 1) {
/*  88 */         stddev = Math.sqrt(var(numbers, biasCorrected));
/*     */       }
/*     */       else {
/*  91 */         stddev = 0.0D;
/*     */       }
/*     */     }
/*  94 */     return stddev;
/*     */   }
/*     */ 
/*     */   public static double var(List<Number> numbers, boolean biasCorrected)
/*     */   {
/* 111 */     int n = numbers.size();
/* 112 */     if (n == 0) {
/* 113 */       return (0.0D / 0.0D);
/*     */     }
/* 115 */     if (n == 1) {
/* 116 */       return 0.0D;
/*     */     }
/* 118 */     double mean = mean(numbers);
/* 119 */     List squares = new ArrayList();
/* 120 */     for (Number number : numbers) {
/* 121 */       double XminMean = number.doubleValue() - mean;
/* 122 */       squares.add(Double.valueOf(Math.pow(XminMean, 2.0D)));
/*     */     }
/* 124 */     double sum = sum(squares);
/* 125 */     return sum / (biasCorrected ? n - 1 : n);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.MathUtils
 * JD-Core Version:    0.6.0
 */
/*     */ package com.jidesoft.utils;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.math.MathContext;
/*     */ import java.math.RoundingMode;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.TreeSet;
/*     */ 
/*     */ public final class BigDecimalMathUtils
/*     */ {
/*  16 */   public static final BigDecimal TWO = BigDecimal.valueOf(2L);
/*     */ 
/*     */   public static BigDecimal sum(List<BigDecimal> numbers)
/*     */   {
/*  28 */     BigDecimal sum = new BigDecimal(0);
/*  29 */     for (BigDecimal bigDecimal : numbers) {
/*  30 */       sum = sum.add(bigDecimal);
/*     */     }
/*  32 */     return sum;
/*     */   }
/*     */ 
/*     */   public static BigDecimal mean(List<BigDecimal> numbers, MathContext context)
/*     */   {
/*  43 */     BigDecimal sum = sum(numbers);
/*  44 */     return sum.divide(new BigDecimal(numbers.size()), context);
/*     */   }
/*     */ 
/*     */   public static BigDecimal min(List<BigDecimal> numbers)
/*     */   {
/*  54 */     return (BigDecimal)new TreeSet(numbers).first();
/*     */   }
/*     */ 
/*     */   public static BigDecimal max(List<BigDecimal> numbers)
/*     */   {
/*  64 */     return (BigDecimal)new TreeSet(numbers).last();
/*     */   }
/*     */ 
/*     */   public static BigDecimal stddev(List<BigDecimal> numbers, boolean biasCorrected, MathContext context)
/*     */   {
/*  80 */     int n = numbers.size();
/*     */     BigDecimal stddev;
/*     */     BigDecimal stddev;
/*  81 */     if (n > 0)
/*     */     {
/*     */       BigDecimal stddev;
/*  82 */       if (n > 1) {
/*  83 */         stddev = sqrt(var(numbers, biasCorrected, context));
/*     */       }
/*     */       else
/*  86 */         stddev = BigDecimal.ZERO;
/*     */     }
/*     */     else
/*     */     {
/*  90 */       stddev = BigDecimal.valueOf((0.0D / 0.0D));
/*     */     }
/*  92 */     return stddev;
/*     */   }
/*     */ 
/*     */   public static BigDecimal var(List<BigDecimal> numbers, boolean biasCorrected, MathContext context)
/*     */   {
/* 111 */     int n = numbers.size();
/* 112 */     if (n == 0) {
/* 113 */       return BigDecimal.valueOf((0.0D / 0.0D));
/*     */     }
/* 115 */     if (n == 1) {
/* 116 */       return BigDecimal.ZERO;
/*     */     }
/* 118 */     BigDecimal mean = mean(numbers, context);
/* 119 */     List squares = new ArrayList();
/* 120 */     for (BigDecimal number : numbers) {
/* 121 */       BigDecimal XminMean = number.subtract(mean);
/* 122 */       squares.add(XminMean.pow(2, context));
/*     */     }
/* 124 */     BigDecimal sum = sum(squares);
/* 125 */     return sum.divide(new BigDecimal(biasCorrected ? numbers.size() - 1 : numbers.size()), context);
/*     */   }
/*     */ 
/*     */   public static BigDecimal sqrt(BigDecimal number)
/*     */   {
/* 140 */     BigDecimal temp1 = null;
/* 141 */     BigDecimal temp2 = null;
/*     */ 
/* 143 */     int extraPrecision = number.precision();
/* 144 */     MathContext mc = new MathContext(extraPrecision, RoundingMode.HALF_UP);
/* 145 */     BigDecimal numberToBeSquareRooted = number;
/* 146 */     double num = numberToBeSquareRooted.doubleValue();
/*     */ 
/* 148 */     if (mc.getPrecision() == 0)
/* 149 */       throw new IllegalArgumentException("\nRoots need a MathContext precision > 0");
/* 150 */     if (num < 0.0D)
/* 151 */       throw new ArithmeticException("\nCannot calculate the square root of a negative number");
/* 152 */     if (num == 0.0D) {
/* 153 */       return number.round(mc);
/*     */     }
/* 155 */     if (mc.getPrecision() < 50)
/* 156 */       extraPrecision += 10;
/* 157 */     int startPrecision = 1;
/*     */     BigDecimal iteration2;
/*     */     BigDecimal iteration1;
/*     */     BigDecimal iteration2;
/* 163 */     if (num == (1.0D / 0.0D))
/*     */     {
/* 165 */       BigInteger bi = numberToBeSquareRooted.unscaledValue();
/* 166 */       int biLen = bi.bitLength();
/* 167 */       int biSqrtLen = biLen / 2;
/*     */ 
/* 169 */       bi = bi.shiftRight(biSqrtLen);
/* 170 */       BigDecimal iteration1 = new BigDecimal(bi);
/*     */ 
/* 172 */       MathContext mm = new MathContext(5, RoundingMode.HALF_DOWN);
/* 173 */       extraPrecision += 10;
/*     */ 
/* 175 */       iteration2 = BigDecimal.ONE.divide(TWO.multiply(iteration1, mm), mm);
/*     */     }
/*     */     else
/*     */     {
/* 179 */       double s = Math.sqrt(num);
/* 180 */       iteration1 = new BigDecimal(s);
/* 181 */       iteration2 = new BigDecimal(0.5D / s);
/*     */ 
/* 184 */       startPrecision = 64;
/*     */     }
/*     */ 
/* 187 */     int digits = mc.getPrecision() + extraPrecision;
/*     */ 
/* 190 */     MathContext n = new MathContext(startPrecision, mc.getRoundingMode());
/*     */ 
/* 192 */     return sqrtProcedure(n, digits, numberToBeSquareRooted, iteration1, iteration2, temp1, temp2);
/*     */   }
/*     */ 
/*     */   private static BigDecimal sqrtProcedure(MathContext mc, int digits, BigDecimal numberToBeSquareRooted, BigDecimal iteration1, BigDecimal iteration2, BigDecimal temp1, BigDecimal temp2)
/*     */   {
/* 213 */     temp1 = BigDecimal.ONE.subtract(TWO.multiply(iteration1, mc).multiply(iteration2, mc), mc);
/* 214 */     iteration2 = iteration2.add(temp1.multiply(iteration2, mc), mc);
/*     */ 
/* 217 */     temp2 = numberToBeSquareRooted.subtract(iteration1.multiply(iteration1, mc), mc);
/* 218 */     iteration1 = iteration1.add(temp2.multiply(iteration2, mc), mc);
/*     */ 
/* 221 */     int m = mc.getPrecision();
/* 222 */     if (m < 2)
/* 223 */       m++;
/*     */     else {
/* 225 */       m = m * 2 - 1;
/*     */     }
/* 227 */     if (m < 2 * digits)
/*     */     {
/* 229 */       mc = new MathContext(m, mc.getRoundingMode());
/* 230 */       sqrtProcedure(mc, digits, numberToBeSquareRooted, iteration1, iteration2, temp1, temp2);
/*     */     }
/*     */ 
/* 233 */     return iteration1;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 237 */     System.out.println(sqrt(new BigDecimal("25029.33333")));
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.BigDecimalMathUtils
 * JD-Core Version:    0.6.0
 */
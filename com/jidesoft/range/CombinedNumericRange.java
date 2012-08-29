/*     */ package com.jidesoft.range;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class CombinedNumericRange extends AbstractNumericRange<Double>
/*     */ {
/*  19 */   private final Object monitor = new Object();
/*  20 */   private List<Range<Double>> _ranges = new ArrayList();
/*  21 */   private Double _max = null;
/*  22 */   private Double _min = null;
/*     */ 
/*     */   public CombinedNumericRange add(Range<Double> range)
/*     */   {
/*  40 */     if (range == null) {
/*  41 */       return this;
/*     */     }
/*  43 */     synchronized (this.monitor) {
/*  44 */       this._ranges.add(range);
/*  45 */       this._min = null;
/*  46 */       this._max = null;
/*     */     }
/*  48 */     return this;
/*     */   }
/*     */ 
/*     */   public Double lower()
/*     */   {
/*  55 */     return Double.valueOf(minimum());
/*     */   }
/*     */ 
/*     */   public Double upper()
/*     */   {
/*  62 */     return Double.valueOf(maximum());
/*     */   }
/*     */ 
/*     */   public void adjust(Double lower, Double upper)
/*     */   {
/*  74 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public double maximum()
/*     */   {
/*  81 */     synchronized (this.monitor) {
/*  82 */       if (this._max != null) {
/*  83 */         return this._max.doubleValue();
/*     */       }
/*  85 */       if ((this._ranges == null) || (this._ranges.size() == 0)) {
/*  86 */         return 1.7976931348623157E+308D;
/*     */       }
/*  88 */       this._max = Double.valueOf(4.9E-324D);
/*  89 */       for (Range range : this._ranges) {
/*  90 */         if ((range != null) && (range.maximum() > this._max.doubleValue())) {
/*  91 */           this._max = Double.valueOf(range.maximum());
/*     */         }
/*     */       }
/*  94 */       return this._max.doubleValue();
/*     */     }
/*     */   }
/*     */ 
/*     */   public double minimum()
/*     */   {
/* 102 */     synchronized (this.monitor) {
/* 103 */       if (this._min != null) {
/* 104 */         return this._min.doubleValue();
/*     */       }
/* 106 */       if ((this._ranges == null) || (this._ranges.size() == 0)) {
/* 107 */         return 4.9E-324D;
/*     */       }
/* 109 */       this._min = Double.valueOf(1.7976931348623157E+308D);
/* 110 */       for (Range range : this._ranges) {
/* 111 */         if ((range != null) && (range.minimum() < this._min.doubleValue())) {
/* 112 */           this._min = Double.valueOf(range.minimum());
/*     */         }
/*     */       }
/* 115 */       return this._min.doubleValue();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean contains(Double x)
/*     */   {
/* 124 */     synchronized (this.monitor) {
/* 125 */       if ((x == null) || (this._ranges.size() == 0)) {
/* 126 */         return false;
/*     */       }
/*     */ 
/* 129 */       for (Range range : this._ranges) {
/* 130 */         if (range.contains(x)) {
/* 131 */           return true;
/*     */         }
/*     */       }
/* 134 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public double size()
/*     */   {
/* 143 */     synchronized (this.monitor) {
/* 144 */       return maximum() - minimum();
/*     */     }
/*     */   }
/*     */ 
/*     */   public NumericRange getRange(double leadingMarginProportion, double trailingMarginProportion)
/*     */   {
/* 157 */     double maximum = maximum();
/* 158 */     double minimum = minimum();
/* 159 */     double difference = Math.abs(maximum - minimum);
/* 160 */     double leadingMargin = leadingMarginProportion * difference;
/* 161 */     double trailingMargin = trailingMarginProportion * difference;
/* 162 */     return new NumericRange(minimum - leadingMargin, maximum + trailingMargin);
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 166 */     return String.format("#<CombinedNumericRange min=%s max=%s>", new Object[] { Double.valueOf(minimum()), Double.valueOf(maximum()) });
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.range.CombinedNumericRange
 * JD-Core Version:    0.6.0
 */
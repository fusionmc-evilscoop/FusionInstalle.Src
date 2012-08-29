/*     */ package com.jidesoft.range;
/*     */ 
/*     */ public class NumericRange extends AbstractNumericRange<Double>
/*     */ {
/*     */   protected double _min;
/*     */   protected double _max;
/*     */ 
/*     */   public NumericRange()
/*     */   {
/*  23 */     this(0.0D, 1.0D);
/*     */   }
/*     */ 
/*     */   public NumericRange(double min, double max)
/*     */   {
/*  34 */     this._min = Math.min(min, max);
/*  35 */     this._max = Math.max(min, max);
/*     */   }
/*     */ 
/*     */   public NumericRange(NumericRange numericRange)
/*     */   {
/*  43 */     this(numericRange.minimum(), numericRange.maximum());
/*     */   }
/*     */ 
/*     */   public Range<Double> copy()
/*     */   {
/*  48 */     return new NumericRange(this);
/*     */   }
/*     */ 
/*     */   public double minimum()
/*     */   {
/*  55 */     return this._min;
/*     */   }
/*     */ 
/*     */   public double maximum()
/*     */   {
/*  62 */     return this._max;
/*     */   }
/*     */ 
/*     */   public double getMin()
/*     */   {
/*  69 */     return this._min;
/*     */   }
/*     */ 
/*     */   public void setMin(double min)
/*     */   {
/*  78 */     double old = this._min;
/*  79 */     if (old == min) {
/*  80 */       return;
/*     */     }
/*  82 */     assert (min <= this._max);
/*  83 */     this._min = min;
/*  84 */     firePropertyChange("min", Double.valueOf(old), Double.valueOf(min));
/*     */   }
/*     */ 
/*     */   public double getMax()
/*     */   {
/*  91 */     return this._max;
/*     */   }
/*     */ 
/*     */   public void setMax(double max)
/*     */   {
/* 100 */     double old = this._max;
/* 101 */     if (old == max) {
/* 102 */       return;
/*     */     }
/* 104 */     assert (max >= this._min);
/* 105 */     this._max = max;
/* 106 */     firePropertyChange("max", Double.valueOf(old), Double.valueOf(max));
/*     */   }
/*     */ 
/*     */   public double size()
/*     */   {
/* 113 */     return this._max - this._min;
/*     */   }
/*     */ 
/*     */   public Double lower()
/*     */   {
/* 120 */     return Double.valueOf(minimum());
/*     */   }
/*     */ 
/*     */   public void adjust(Double lower, Double upper)
/*     */   {
/* 127 */     setMin(lower.doubleValue());
/* 128 */     setMax(upper.doubleValue());
/*     */   }
/*     */ 
/*     */   public Double upper()
/*     */   {
/* 135 */     return Double.valueOf(maximum());
/*     */   }
/*     */ 
/*     */   public boolean contains(Double x)
/*     */   {
/* 142 */     return (x != null) && (x.doubleValue() >= this._min) && (x.doubleValue() <= this._max);
/*     */   }
/*     */ 
/*     */   public NumericRange stretch(double stretchFactor)
/*     */   {
/* 153 */     double mid = (this._max + this._min) / 2.0D;
/* 154 */     double halfSize = size() / 2.0D;
/* 155 */     return new NumericRange(mid - halfSize * stretchFactor, mid + halfSize * stretchFactor);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object other)
/*     */   {
/* 163 */     if ((other instanceof NumericRange)) {
/* 164 */       NumericRange otherRange = (NumericRange)other;
/* 165 */       return (this._min == otherRange._min) && (this._max == otherRange._max);
/*     */     }
/*     */ 
/* 168 */     return false;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 174 */     return String.format("#<NumericRange min=%f max=%f>", new Object[] { Double.valueOf(this._min), Double.valueOf(this._max) });
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.range.NumericRange
 * JD-Core Version:    0.6.0
 */
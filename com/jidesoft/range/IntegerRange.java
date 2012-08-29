/*     */ package com.jidesoft.range;
/*     */ 
/*     */ public class IntegerRange extends AbstractNumericRange<Integer>
/*     */ {
/*     */   protected int _min;
/*     */   protected int _max;
/*     */ 
/*     */   public IntegerRange()
/*     */   {
/*  22 */     this(0, 1);
/*     */   }
/*     */ 
/*     */   public IntegerRange(int min, int max)
/*     */   {
/*  33 */     this._min = Math.min(min, max);
/*  34 */     this._max = Math.max(min, max);
/*     */   }
/*     */ 
/*     */   public IntegerRange(IntegerRange integerRange)
/*     */   {
/*  42 */     this((int)integerRange.minimum(), (int)integerRange.maximum());
/*     */   }
/*     */ 
/*     */   public Range<Integer> copy()
/*     */   {
/*  47 */     return new IntegerRange(this);
/*     */   }
/*     */ 
/*     */   public double minimum()
/*     */   {
/*  54 */     return this._min;
/*     */   }
/*     */ 
/*     */   public double maximum()
/*     */   {
/*  61 */     return this._max;
/*     */   }
/*     */ 
/*     */   public int getMin()
/*     */   {
/*  68 */     return this._min;
/*     */   }
/*     */ 
/*     */   public void setMin(int min)
/*     */   {
/*  77 */     int old = this._min;
/*  78 */     if (old == min) {
/*  79 */       return;
/*     */     }
/*  81 */     assert (min <= this._max) : ("minimum " + min + " not <= " + this._max);
/*  82 */     this._min = min;
/*  83 */     firePropertyChange("min", old, min);
/*     */   }
/*     */ 
/*     */   public int getMax()
/*     */   {
/*  90 */     return this._max;
/*     */   }
/*     */ 
/*     */   public void setMax(int max)
/*     */   {
/*  99 */     int old = this._max;
/* 100 */     if (old == max) {
/* 101 */       return;
/*     */     }
/* 103 */     assert (max >= this._min) : ("maximum " + max + " not >= " + this._min);
/* 104 */     this._max = max;
/* 105 */     firePropertyChange("max", old, max);
/*     */   }
/*     */ 
/*     */   public void adjust(Integer lower, Integer upper) {
/* 109 */     setMin(lower.intValue());
/* 110 */     setMax(upper.intValue());
/*     */   }
/*     */ 
/*     */   public double size()
/*     */   {
/* 117 */     return this._max - this._min;
/*     */   }
/*     */ 
/*     */   public Integer lower()
/*     */   {
/* 124 */     return Integer.valueOf((int)minimum());
/*     */   }
/*     */ 
/*     */   public Integer upper()
/*     */   {
/* 131 */     return Integer.valueOf((int)maximum());
/*     */   }
/*     */ 
/*     */   public boolean contains(Integer x)
/*     */   {
/* 138 */     return (x.intValue() >= this._min) && (x.intValue() <= this._max);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object other)
/*     */   {
/* 146 */     if ((other instanceof IntegerRange)) {
/* 147 */       IntegerRange otherRange = (IntegerRange)other;
/* 148 */       return (this._min == otherRange._min) && (this._max == otherRange._max);
/*     */     }
/*     */ 
/* 151 */     return false;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 157 */     return String.format("#<IntegerRange min=%d max=%d>", new Object[] { Integer.valueOf(this._min), Integer.valueOf(this._max) });
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.range.IntegerRange
 * JD-Core Version:    0.6.0
 */
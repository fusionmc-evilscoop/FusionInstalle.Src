/*     */ package com.jidesoft.range;
/*     */ 
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ public class TimeRange extends AbstractRange<Date>
/*     */ {
/*     */   protected Date _min;
/*     */   protected Date _max;
/*  31 */   private TimeZone timeZone = TimeZone.getDefault();
/*     */ 
/*     */   public TimeRange()
/*     */   {
/*  37 */     Calendar today = Calendar.getInstance();
/*  38 */     today.set(11, 0);
/*  39 */     today.set(12, 0);
/*  40 */     today.set(13, 0);
/*  41 */     today.set(14, 0);
/*  42 */     long todayLong = today.getTimeInMillis();
/*  43 */     today.add(10, 24);
/*  44 */     long tomorrowLong = today.getTimeInMillis();
/*  45 */     this._min = new Date(todayLong);
/*  46 */     this._max = new Date(tomorrowLong);
/*     */   }
/*     */ 
/*     */   public TimeRange(Calendar from, Calendar to) {
/*  50 */     this._min = from.getTime();
/*  51 */     this._max = to.getTime();
/*  52 */     assert (from.before(to));
/*     */   }
/*     */ 
/*     */   public TimeRange(Date from, Date to) {
/*  56 */     this._min = from;
/*  57 */     this._max = to;
/*     */   }
/*     */ 
/*     */   public TimeRange(long from, long to) {
/*  61 */     this._min = new Date(from);
/*  62 */     this._max = new Date(to);
/*     */   }
/*     */ 
/*     */   public TimeRange(TimeRange timeRange)
/*     */   {
/*  70 */     this(()timeRange.minimum(), ()timeRange.maximum());
/*     */   }
/*     */ 
/*     */   public Range<Date> copy()
/*     */   {
/*  75 */     return new TimeRange(this);
/*     */   }
/*     */ 
/*     */   public void setMin(Date from) {
/*  79 */     Date oldValue = this._min;
/*  80 */     this._min = from;
/*  81 */     firePropertyChange("min", oldValue, this._min);
/*     */   }
/*     */ 
/*     */   public void setMin(long from) {
/*  85 */     Date oldValue = this._min;
/*  86 */     this._min = new Date(from);
/*  87 */     firePropertyChange("min", oldValue, this._min);
/*     */   }
/*     */ 
/*     */   public void adjust(Date lower, Date upper) {
/*  91 */     setMin(lower);
/*  92 */     setMax(upper);
/*     */   }
/*     */ 
/*     */   public void setMax(Date to) {
/*  96 */     Date oldValue = this._max;
/*  97 */     this._max = to;
/*  98 */     firePropertyChange("max", oldValue, this._max);
/*     */   }
/*     */ 
/*     */   public void setMax(long to) {
/* 102 */     Date oldValue = this._max;
/* 103 */     this._max = new Date(to);
/* 104 */     firePropertyChange("max", oldValue, this._max);
/*     */   }
/*     */ 
/*     */   public double minimum() {
/* 108 */     return this._min.getTime();
/*     */   }
/*     */ 
/*     */   public double maximum() {
/* 112 */     return this._max.getTime();
/*     */   }
/*     */ 
/*     */   public double size() {
/* 116 */     return maximum() - minimum();
/*     */   }
/*     */ 
/*     */   public Date lower() {
/* 120 */     return this._min;
/*     */   }
/*     */ 
/*     */   public Date upper() {
/* 124 */     return this._max;
/*     */   }
/*     */ 
/*     */   public boolean contains(Date x) {
/* 128 */     if (x == null) {
/* 129 */       return false;
/*     */     }
/*     */ 
/* 132 */     long value = x.getTime();
/* 133 */     return (value >= minimum()) && (value <= maximum());
/*     */   }
/*     */ 
/*     */   public TimeZone getTimeZone()
/*     */   {
/* 138 */     return this.timeZone;
/*     */   }
/*     */ 
/*     */   public void setTimeZone(TimeZone timeZone) {
/* 142 */     this.timeZone = timeZone;
/*     */   }
/*     */ 
/*     */   public static TimeRange union(TimeRange r1, TimeRange r2)
/*     */   {
/* 154 */     if (r1 == null) {
/* 155 */       return r2;
/*     */     }
/* 157 */     if (r2 == null) {
/* 158 */       return r1;
/*     */     }
/* 160 */     long r1Min = r1._min == null ? 9223372036854775807L : r1._min.getTime();
/* 161 */     long r2Min = r2._min == null ? 9223372036854775807L : r2._min.getTime();
/* 162 */     long r1Max = r1._max == null ? -9223372036854775808L : r1._max.getTime();
/* 163 */     long r2Max = r2._max == null ? -9223372036854775808L : r2._max.getTime();
/*     */ 
/* 165 */     long min = Math.min(r1Min, r2Min);
/* 166 */     long max = Math.max(r1Max, r2Max);
/* 167 */     return new TimeRange(min, max);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object other)
/*     */   {
/* 173 */     if ((other instanceof TimeRange)) {
/* 174 */       TimeRange otherRange = (TimeRange)other;
/* 175 */       return (JideSwingUtilities.equals(this._min, otherRange._min)) && (JideSwingUtilities.equals(this._max, otherRange._max));
/*     */     }
/*     */ 
/* 178 */     return false;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 184 */     DateFormat f = DateFormat.getDateTimeInstance(2, 2);
/* 185 */     return String.format("[%s, %s] rounded to [%s, %s]", new Object[] { this._min == null ? "null" : f.format(this._min), this._max == null ? "null" : f.format(this._max), this._min == null ? "null" : f.format(Double.valueOf(minimum())), this._max == null ? "null" : f.format(Double.valueOf(maximum())) });
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.range.TimeRange
 * JD-Core Version:    0.6.0
 */
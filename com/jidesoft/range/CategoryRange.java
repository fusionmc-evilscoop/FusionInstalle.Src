/*     */ package com.jidesoft.range;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class CategoryRange<T> extends AbstractRange<T>
/*     */   implements Iterable<Category<T>>
/*     */ {
/*     */   private static final String PROPERTY_VALUES = "values";
/*  20 */   private List<T> _possibleValues = null;
/*  21 */   private List<Category<T>> _categoryValues = null;
/*     */   private Double minimum;
/*     */   private Double maximum;
/*     */ 
/*     */   public CategoryRange()
/*     */   {
/*  26 */     this._possibleValues = new ArrayList();
/*  27 */     this._categoryValues = new ArrayList();
/*     */   }
/*     */ 
/*     */   public CategoryRange(T[] values)
/*     */   {
/*  36 */     this._possibleValues = new ArrayList();
/*  37 */     this._possibleValues.addAll(Arrays.asList(values));
/*     */   }
/*     */ 
/*     */   public CategoryRange(Set<T> values)
/*     */   {
/*  47 */     this._possibleValues = new ArrayList(values);
/*     */   }
/*     */ 
/*     */   public CategoryRange(CategoryRange<T> categoryRange)
/*     */   {
/*  56 */     this._categoryValues = new ArrayList(categoryRange.getCategoryValues());
/*  57 */     this._possibleValues = new ArrayList(categoryRange.getPossibleValues());
/*  58 */     CategoryRange range = new CategoryRange();
/*  59 */     setMinimum(categoryRange.minimum());
/*  60 */     setMaximum(categoryRange.maximum());
/*     */   }
/*     */ 
/*     */   public List<T> getPossibleValues() {
/*  64 */     return this._possibleValues;
/*     */   }
/*     */ 
/*     */   public CategoryRange<T> add(Category<T> c)
/*     */   {
/*  77 */     if (!contains(c)) {
/*  78 */       this._possibleValues.add(c.getValue());
/*  79 */       this._categoryValues.add(c);
/*  80 */       c.setRange(this);
/*  81 */       firePropertyChange("values", null, this._possibleValues);
/*     */     }
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */   public Range<T> copy()
/*     */   {
/*  89 */     return new CategoryRange(this);
/*     */   }
/*     */ 
/*     */   public T lower()
/*     */   {
/*  94 */     if ((this._possibleValues == null) || (this._possibleValues.size() == 0)) {
/*  95 */       return null;
/*     */     }
/*  97 */     return this._possibleValues.get(0);
/*     */   }
/*     */ 
/*     */   public T upper()
/*     */   {
/* 102 */     if ((this._possibleValues == null) || (this._possibleValues.size() == 0)) {
/* 103 */       return null;
/*     */     }
/* 105 */     int numElements = this._possibleValues.size();
/* 106 */     return this._possibleValues.get(numElements - 1);
/*     */   }
/*     */ 
/*     */   public void adjust(T lower, T upper)
/*     */   {
/* 113 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public double maximum()
/*     */   {
/* 122 */     if (this.maximum == null) {
/* 123 */       Object upper = upper();
/* 124 */       if (upper == null) {
/* 125 */         return 1.0D;
/*     */       }
/* 127 */       this.maximum = Double.valueOf(position(upper) + 1.0D);
/*     */     }
/*     */ 
/* 130 */     return this.maximum.doubleValue();
/*     */   }
/*     */ 
/*     */   public double minimum()
/*     */   {
/* 139 */     if (this.minimum == null) {
/* 140 */       Object lower = lower();
/* 141 */       if (lower == null) {
/* 142 */         return 0.0D;
/*     */       }
/* 144 */       this.minimum = Double.valueOf(position(lower) - 1.0D);
/*     */     }
/*     */ 
/* 147 */     return this.minimum.doubleValue();
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/* 154 */     this.maximum = null;
/* 155 */     this.minimum = null;
/*     */   }
/*     */ 
/*     */   public void setMinimum(double value) {
/* 159 */     Double oldValue = this.minimum;
/* 160 */     this.minimum = Double.valueOf(value);
/* 161 */     firePropertyChange("min", oldValue, Double.valueOf(value));
/*     */   }
/*     */ 
/*     */   public void setMaximum(double value) {
/* 165 */     Double oldValue = this.maximum;
/* 166 */     this.maximum = Double.valueOf(value);
/* 167 */     firePropertyChange("max", oldValue, Double.valueOf(value));
/*     */   }
/*     */ 
/*     */   public double size()
/*     */   {
/* 178 */     if (this._possibleValues == null) {
/* 179 */       return 0.0D;
/*     */     }
/*     */ 
/* 182 */     int numElements = this._possibleValues.size();
/* 183 */     if (numElements == 0) {
/* 184 */       return 0.0D;
/*     */     }
/*     */ 
/* 188 */     return maximum() - minimum();
/*     */   }
/*     */ 
/*     */   public int position(T value)
/*     */   {
/* 193 */     int index = this._possibleValues.indexOf(value);
/* 194 */     if (index < 0) {
/* 195 */       throw new IllegalArgumentException("Value " + value + " not known");
/*     */     }
/* 197 */     return 1 + index;
/*     */   }
/*     */ 
/*     */   public boolean contains(T x)
/*     */   {
/* 204 */     if (x == null) {
/* 205 */       return false;
/*     */     }
/*     */ 
/* 208 */     for (Iterator i$ = this._possibleValues.iterator(); i$.hasNext(); ) { Object category = i$.next();
/* 209 */       if (x.equals(category)) {
/* 210 */         return true;
/*     */       }
/*     */     }
/* 213 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean contains(Category<T> value)
/*     */   {
/* 224 */     if (value == null) {
/* 225 */       return false;
/*     */     }
/*     */ 
/* 228 */     for (Category category : getCategoryValues()) {
/* 229 */       if (value.equals(category)) {
/* 230 */         return true;
/*     */       }
/*     */     }
/* 233 */     return false;
/*     */   }
/*     */ 
/*     */   public Iterator<Category<T>> iterator()
/*     */   {
/* 238 */     return getCategoryValues().iterator();
/*     */   }
/*     */ 
/*     */   public List<Category<T>> getCategoryValues()
/*     */   {
/*     */     Iterator i$;
/* 242 */     if (this._categoryValues == null) {
/* 243 */       this._categoryValues = new ArrayList();
/* 244 */       for (i$ = this._possibleValues.iterator(); i$.hasNext(); ) { Object value = i$.next();
/* 245 */         this._categoryValues.add(new Category(value, this));
/*     */       }
/*     */     }
/* 248 */     return this._categoryValues;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 253 */     int prime = 31;
/* 254 */     int result = 1;
/* 255 */     result = 31 * result + (this._categoryValues == null ? 0 : this._categoryValues.hashCode());
/* 256 */     result = 31 * result + (this._possibleValues == null ? 0 : this._possibleValues.hashCode());
/* 257 */     result = 31 * result + (this.maximum == null ? 0 : this.maximum.hashCode());
/* 258 */     result = 31 * result + (this.minimum == null ? 0 : this.minimum.hashCode());
/* 259 */     return result;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 264 */     if (this == obj)
/* 265 */       return true;
/* 266 */     if (obj == null)
/* 267 */       return false;
/* 268 */     if (getClass() != obj.getClass())
/* 269 */       return false;
/* 270 */     CategoryRange other = (CategoryRange)obj;
/* 271 */     if (this._categoryValues == null) {
/* 272 */       if (other._categoryValues != null)
/* 273 */         return false;
/* 274 */     } else if (!this._categoryValues.equals(other._categoryValues))
/* 275 */       return false;
/* 276 */     if (this._possibleValues == null) {
/* 277 */       if (other._possibleValues != null)
/* 278 */         return false;
/* 279 */     } else if (!this._possibleValues.equals(other._possibleValues))
/* 280 */       return false;
/* 281 */     if (this.maximum == null) {
/* 282 */       if (other.maximum != null)
/* 283 */         return false;
/* 284 */     } else if (!this.maximum.equals(other.maximum))
/* 285 */       return false;
/* 286 */     if (this.minimum == null) {
/* 287 */       if (other.minimum != null)
/* 288 */         return false;
/* 289 */     } else if (!this.minimum.equals(other.minimum))
/* 290 */       return false;
/* 291 */     return true;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 295 */     StringBuilder builder = new StringBuilder("#<CategoryRange ");
/* 296 */     builder.append("minimum=");
/* 297 */     builder.append(this.minimum);
/* 298 */     builder.append(" maximum=");
/* 299 */     builder.append(this.maximum);
/* 300 */     builder.append(">");
/* 301 */     return builder.toString();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.range.CategoryRange
 * JD-Core Version:    0.6.0
 */
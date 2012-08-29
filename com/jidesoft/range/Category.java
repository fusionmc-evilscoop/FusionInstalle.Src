/*     */ package com.jidesoft.range;
/*     */ 
/*     */ public class Category<T>
/*     */   implements Positionable
/*     */ {
/*     */   private String _name;
/*     */   private T _value;
/*     */   private CategoryRange<T> _range;
/*     */ 
/*     */   public Category(String name, T value)
/*     */   {
/*  21 */     setName(name);
/*  22 */     this._value = value;
/*     */   }
/*     */ 
/*     */   public Category(String name, T value, CategoryRange<T> range) {
/*  26 */     setName(name);
/*  27 */     this._value = value;
/*  28 */     this._range = range;
/*     */   }
/*     */ 
/*     */   public Category(T value) {
/*  32 */     this._value = value;
/*     */   }
/*     */ 
/*     */   public Category(T value, CategoryRange<T> range) {
/*  36 */     this._value = value;
/*  37 */     this._range = range;
/*     */   }
/*     */ 
/*     */   public CategoryRange<T> getRange() {
/*  41 */     return this._range;
/*     */   }
/*     */ 
/*     */   public void setRange(CategoryRange<T> range) {
/*  45 */     this._range = range;
/*     */   }
/*     */ 
/*     */   public double position() {
/*  49 */     if (this._range == null) {
/*  50 */       throw new IllegalStateException("Cannot compute position for a category that does not belong to a range");
/*     */     }
/*  52 */     return this._range.position(this._value);
/*     */   }
/*     */ 
/*     */   public T getValue() {
/*  56 */     return this._value;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  60 */     if (this._name == null) {
/*  61 */       return this._value.toString();
/*     */     }
/*  63 */     return this._name;
/*     */   }
/*     */ 
/*     */   private void setName(String name) {
/*  67 */     this._name = name;
/*     */   }
/*     */ 
/*     */   public int compareTo(Positionable o) {
/*  71 */     double otherPosition = o.position();
/*  72 */     double position = position();
/*  73 */     if (position < otherPosition) {
/*  74 */       return -1;
/*     */     }
/*  76 */     if (position > otherPosition) {
/*  77 */       return 1;
/*     */     }
/*     */ 
/*  80 */     return 0;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  88 */     int prime = 31;
/*  89 */     int result = 1;
/*  90 */     result = 31 * result + (this._name == null ? 0 : this._name.hashCode());
/*  91 */     result = 31 * result + (this._value == null ? 0 : this._value.hashCode());
/*  92 */     return result;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  97 */     if (this == obj)
/*  98 */       return true;
/*  99 */     if (obj == null)
/* 100 */       return false;
/* 101 */     if (getClass() != obj.getClass())
/* 102 */       return false;
/* 103 */     Category other = (Category)obj;
/* 104 */     if (this._name == null) {
/* 105 */       if (other._name != null)
/* 106 */         return false;
/* 107 */     } else if (!this._name.equals(other._name))
/* 108 */       return false;
/* 109 */     if (this._value == null) {
/* 110 */       if (other._value != null)
/* 111 */         return false;
/* 112 */     } else if (!this._value.equals(other._value))
/* 113 */       return false;
/* 114 */     return true;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 119 */     return String.format("#<Category name='%s' value='%s'>", new Object[] { this._name, this._value.toString() });
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.range.Category
 * JD-Core Version:    0.6.0
 */
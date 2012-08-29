/*     */ package com.jidesoft.utils;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ 
/*     */ public class SortedList<E>
/*     */   implements List<E>
/*     */ {
/*     */   private Comparator<E> comparator;
/*     */   private List<E> delegate;
/*     */ 
/*     */   public SortedList(List<E> delegate, Comparator comparator)
/*     */   {
/*  19 */     this.delegate = delegate;
/*  20 */     this.comparator = comparator;
/*     */   }
/*     */ 
/*     */   public void add(int index, E element)
/*     */   {
/*  25 */     add(element);
/*     */   }
/*     */ 
/*     */   public boolean add(E o) {
/*  29 */     int size = this.delegate.size();
/*  30 */     for (int i = 0; i < size; i++) {
/*  31 */       if (this.comparator.compare(this.delegate.get(i), o) > 0) {
/*  32 */         this.delegate.add(i, o);
/*  33 */         return true;
/*     */       }
/*     */     }
/*  36 */     this.delegate.add(o);
/*  37 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean addAll(Collection<? extends E> c) {
/*  41 */     return this.delegate.addAll(c);
/*     */   }
/*     */ 
/*     */   public boolean addAll(int index, Collection<? extends E> c) {
/*  45 */     return this.delegate.addAll(index, c);
/*     */   }
/*     */ 
/*     */   public void clear() {
/*  49 */     this.delegate.clear();
/*     */   }
/*     */ 
/*     */   public boolean contains(Object o) {
/*  53 */     return this.delegate.contains(o);
/*     */   }
/*     */ 
/*     */   public boolean containsAll(Collection<?> c) {
/*  57 */     return this.delegate.containsAll(c);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/*  62 */     return this.delegate.equals(o);
/*     */   }
/*     */ 
/*     */   public E get(int index) {
/*  66 */     return this.delegate.get(index);
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  71 */     return this.delegate.hashCode();
/*     */   }
/*     */ 
/*     */   public int indexOf(Object o) {
/*  75 */     return this.delegate.indexOf(o);
/*     */   }
/*     */ 
/*     */   public boolean isEmpty() {
/*  79 */     return this.delegate.isEmpty();
/*     */   }
/*     */ 
/*     */   public Iterator<E> iterator() {
/*  83 */     return this.delegate.iterator();
/*     */   }
/*     */ 
/*     */   public int lastIndexOf(Object o) {
/*  87 */     return this.delegate.lastIndexOf(o);
/*     */   }
/*     */ 
/*     */   public ListIterator<E> listIterator() {
/*  91 */     return this.delegate.listIterator();
/*     */   }
/*     */ 
/*     */   public ListIterator<E> listIterator(int index) {
/*  95 */     return this.delegate.listIterator(index);
/*     */   }
/*     */ 
/*     */   public E remove(int index) {
/*  99 */     return this.delegate.remove(index);
/*     */   }
/*     */ 
/*     */   public boolean remove(Object o) {
/* 103 */     return this.delegate.remove(o);
/*     */   }
/*     */ 
/*     */   public boolean removeAll(Collection<?> c) {
/* 107 */     return this.delegate.removeAll(c);
/*     */   }
/*     */ 
/*     */   public boolean retainAll(Collection<?> c) {
/* 111 */     return this.delegate.retainAll(c);
/*     */   }
/*     */ 
/*     */   public E set(int index, E element) {
/* 115 */     return this.delegate.set(index, element);
/*     */   }
/*     */ 
/*     */   public int size() {
/* 119 */     return this.delegate.size();
/*     */   }
/*     */ 
/*     */   public List<E> subList(int fromIndex, int toIndex) {
/* 123 */     return this.delegate.subList(fromIndex, toIndex);
/*     */   }
/*     */ 
/*     */   public Object[] toArray() {
/* 127 */     return this.delegate.toArray();
/*     */   }
/*     */ 
/*     */   public Object[] toArray(Object[] a) {
/* 131 */     return this.delegate.toArray(a);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 135 */     List sortedList = new SortedList(new ArrayList(), new Comparator() {
/*     */       public int compare(String o1, String o2) {
/* 137 */         return o1.compareTo(o2);
/*     */       }
/*     */     });
/* 140 */     sortedList.add("test");
/* 141 */     sortedList.add("aaa");
/* 142 */     sortedList.add("ddd");
/* 143 */     sortedList.add("ccc");
/* 144 */     for (String s : sortedList)
/* 145 */       System.out.println(s);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.SortedList
 * JD-Core Version:    0.6.0
 */
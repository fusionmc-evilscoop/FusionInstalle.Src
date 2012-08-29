/*     */ package com.jidesoft.hints;
/*     */ 
/*     */ import java.util.AbstractList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.swing.text.JTextComponent;
/*     */ 
/*     */ public class ListDataIntelliHints<T> extends AbstractListIntelliHints
/*     */ {
/*  20 */   private boolean _caseSensitive = false;
/*     */   private List<T> _completionList;
/*     */ 
/*     */   public ListDataIntelliHints(JTextComponent comp, List<T> completionList)
/*     */   {
/*  24 */     super(comp);
/*  25 */     setCompletionList(completionList);
/*     */   }
/*     */ 
/*     */   public ListDataIntelliHints(JTextComponent comp, T[] completionList) {
/*  29 */     super(comp);
/*  30 */     setCompletionList(completionList);
/*     */   }
/*     */ 
/*     */   public List<T> getCompletionList()
/*     */   {
/*  39 */     return this._completionList;
/*     */   }
/*     */ 
/*     */   public void setCompletionList(List<T> completionList)
/*     */   {
/*  48 */     this._completionList = completionList;
/*     */   }
/*     */ 
/*     */   public void setCompletionList(T[] completionList)
/*     */   {
/*  57 */     Object[] list = completionList;
/*  58 */     this._completionList = new AbstractList(list)
/*     */     {
/*     */       public T get(int index) {
/*  61 */         return this.val$list[index];
/*     */       }
/*     */ 
/*     */       public int size()
/*     */       {
/*  66 */         return this.val$list.length;
/*     */       } } ;
/*     */   }
/*     */ 
/*     */   public boolean updateHints(Object context) {
/*  72 */     if (context == null) {
/*  73 */       return false;
/*     */     }
/*  75 */     List possibleHints = new ArrayList();
/*  76 */     for (Iterator i$ = getCompletionList().iterator(); i$.hasNext(); ) { Object o = i$.next();
/*  77 */       if (compare(context, o)) possibleHints.add(o);
/*     */     }
/*     */ 
/*  80 */     Object[] objects = possibleHints.toArray();
/*  81 */     setListData(objects);
/*  82 */     return objects.length > 0;
/*     */   }
/*     */ 
/*     */   protected boolean compare(Object context, T o)
/*     */   {
/*  93 */     boolean match = false;
/*  94 */     String listEntry = o == null ? "" : o.toString();
/*  95 */     String s = context.toString();
/*  96 */     int substringLen = s.length();
/*  97 */     if (substringLen <= listEntry.length()) {
/*  98 */       if (!isCaseSensitive()) {
/*  99 */         if (s.equalsIgnoreCase(listEntry.substring(0, substringLen)))
/* 100 */           match = true;
/*     */       }
/* 102 */       else if (listEntry.startsWith(s))
/* 103 */         match = true;
/*     */     }
/* 105 */     return match;
/*     */   }
/*     */ 
/*     */   public boolean isCaseSensitive()
/*     */   {
/* 114 */     return this._caseSensitive;
/*     */   }
/*     */ 
/*     */   public void setCaseSensitive(boolean caseSensitive)
/*     */   {
/* 123 */     this._caseSensitive = caseSensitive;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.hints.ListDataIntelliHints
 * JD-Core Version:    0.6.0
 */
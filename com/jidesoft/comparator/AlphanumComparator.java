/*     */ package com.jidesoft.comparator;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ 
/*     */ public class AlphanumComparator
/*     */   implements Comparator<CharSequence>
/*     */ {
/*     */   private boolean _caseSensitive;
/*  37 */   public static final ComparatorContext CONTEXT = new ComparatorContext("Alphanum");
/*  38 */   public static final ComparatorContext CONTEXT_IGNORE_CASE = new ComparatorContext("Alphanum_Ignorecase");
/*     */ 
/*     */   public AlphanumComparator()
/*     */   {
/*  45 */     this(true);
/*     */   }
/*     */ 
/*     */   public AlphanumComparator(boolean caseSensitive)
/*     */   {
/*  54 */     this._caseSensitive = caseSensitive;
/*     */   }
/*     */ 
/*     */   private boolean isDigit(char ch) {
/*  58 */     return (ch >= '0') && (ch <= '9');
/*     */   }
/*     */ 
/*     */   private String getChunk(CharSequence s, int slength, int marker)
/*     */   {
/*  66 */     StringBuilder chunk = new StringBuilder();
/*  67 */     char c = s.charAt(marker);
/*  68 */     chunk.append(c);
/*  69 */     marker++;
/*  70 */     if (isDigit(c)) {
/*  71 */       while (marker < slength) {
/*  72 */         c = s.charAt(marker);
/*  73 */         if (!isDigit(c))
/*     */           break;
/*  75 */         chunk.append(c);
/*  76 */         marker++;
/*     */       }
/*     */     }
/*     */ 
/*  80 */     while (marker < slength) {
/*  81 */       c = s.charAt(marker);
/*  82 */       if (isDigit(c))
/*     */         break;
/*  84 */       chunk.append(c);
/*  85 */       marker++;
/*     */     }
/*     */ 
/*  88 */     return chunk.toString();
/*     */   }
/*     */ 
/*     */   public int compare(CharSequence s1, CharSequence s2) {
/*  92 */     int thisMarker = 0;
/*  93 */     int thatMarker = 0;
/*  94 */     int s1Length = s1.length();
/*  95 */     int s2Length = s2.length();
/*     */ 
/*  97 */     while ((thisMarker < s1Length) && (thatMarker < s2Length)) {
/*  98 */       String thisChunk = getChunk(s1, s1Length, thisMarker);
/*  99 */       thisMarker += thisChunk.length();
/*     */ 
/* 101 */       String thatChunk = getChunk(s2, s2Length, thatMarker);
/* 102 */       thatMarker += thatChunk.length();
/*     */       int result;
/* 106 */       if ((isDigit(thisChunk.charAt(0))) && (isDigit(thatChunk.charAt(0))))
/*     */       {
/* 108 */         int thisChunkLength = thisChunk.length();
/* 109 */         int result = thisChunkLength - thatChunk.length();
/*     */ 
/* 111 */         if (result == 0) {
/* 112 */           for (int i = 0; i < thisChunkLength; i++) {
/* 113 */             result = thisChunk.charAt(i) - thatChunk.charAt(i);
/* 114 */             if (result != 0)
/* 115 */               return result;
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 121 */         result = isCaseSensitive() ? thisChunk.compareTo(thatChunk) : thisChunk.compareToIgnoreCase(thatChunk);
/*     */       }
/*     */ 
/* 124 */       if (result != 0) {
/* 125 */         return result;
/*     */       }
/*     */     }
/* 128 */     return s1Length - s2Length;
/*     */   }
/*     */ 
/*     */   public boolean isCaseSensitive()
/*     */   {
/* 137 */     return this._caseSensitive;
/*     */   }
/*     */ 
/*     */   public void setCaseSensitive(boolean caseSensitive)
/*     */   {
/* 146 */     this._caseSensitive = caseSensitive;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.comparator.AlphanumComparator
 * JD-Core Version:    0.6.0
 */
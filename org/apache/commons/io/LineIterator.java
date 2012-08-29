/*     */ package org.apache.commons.io;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ public class LineIterator
/*     */   implements Iterator
/*     */ {
/*     */   private final BufferedReader bufferedReader;
/*     */   private String cachedLine;
/*  60 */   private boolean finished = false;
/*     */ 
/*     */   public LineIterator(Reader reader)
/*     */     throws IllegalArgumentException
/*     */   {
/*  69 */     if (reader == null) {
/*  70 */       throw new IllegalArgumentException("Reader must not be null");
/*     */     }
/*  72 */     if ((reader instanceof BufferedReader))
/*  73 */       this.bufferedReader = ((BufferedReader)reader);
/*     */     else
/*  75 */       this.bufferedReader = new BufferedReader(reader);
/*     */   }
/*     */ 
/*     */   public boolean hasNext()
/*     */   {
/*  89 */     if (this.cachedLine != null)
/*  90 */       return true;
/*  91 */     if (this.finished)
/*  92 */       return false;
/*     */     try
/*     */     {
/*     */       while (true) {
/*  96 */         String line = this.bufferedReader.readLine();
/*  97 */         if (line == null) {
/*  98 */           this.finished = true;
/*  99 */           return false;
/* 100 */         }if (isValidLine(line)) {
/* 101 */           this.cachedLine = line;
/* 102 */           return true;
/*     */         }
/*     */       }
/*     */     } catch (IOException ioe) {
/* 106 */       close();
/* 107 */     }throw new IllegalStateException(ioe.toString());
/*     */   }
/*     */ 
/*     */   protected boolean isValidLine(String line)
/*     */   {
/* 119 */     return true;
/*     */   }
/*     */ 
/*     */   public Object next()
/*     */   {
/* 129 */     return nextLine();
/*     */   }
/*     */ 
/*     */   public String nextLine()
/*     */   {
/* 139 */     if (!hasNext()) {
/* 140 */       throw new NoSuchElementException("No more lines");
/*     */     }
/* 142 */     String currentLine = this.cachedLine;
/* 143 */     this.cachedLine = null;
/* 144 */     return currentLine;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/* 155 */     this.finished = true;
/* 156 */     IOUtils.closeQuietly(this.bufferedReader);
/* 157 */     this.cachedLine = null;
/*     */   }
/*     */ 
/*     */   public void remove()
/*     */   {
/* 166 */     throw new UnsupportedOperationException("Remove unsupported on LineIterator");
/*     */   }
/*     */ 
/*     */   public static void closeQuietly(LineIterator iterator)
/*     */   {
/* 176 */     if (iterator != null)
/* 177 */       iterator.close();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.LineIterator
 * JD-Core Version:    0.6.0
 */
/*     */ package org.apache.log4j.spi;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Vector;
/*     */ 
/*     */ class VectorWriter extends PrintWriter
/*     */ {
/*     */   private Vector v;
/*     */ 
/*     */   VectorWriter()
/*     */   {
/*  80 */     super(new NullWriter());
/*  81 */     this.v = new Vector();
/*     */   }
/*     */ 
/*     */   public void print(Object o) {
/*  85 */     this.v.addElement(o.toString());
/*     */   }
/*     */ 
/*     */   public void print(char[] chars) {
/*  89 */     this.v.addElement(new String(chars));
/*     */   }
/*     */ 
/*     */   public void print(String s) {
/*  93 */     this.v.addElement(s);
/*     */   }
/*     */ 
/*     */   public void println(Object o) {
/*  97 */     this.v.addElement(o.toString());
/*     */   }
/*     */ 
/*     */   public void println(char[] chars)
/*     */   {
/* 104 */     this.v.addElement(new String(chars));
/*     */   }
/*     */ 
/*     */   public void println(String s)
/*     */   {
/* 109 */     this.v.addElement(s);
/*     */   }
/*     */ 
/*     */   public void write(char[] chars) {
/* 113 */     this.v.addElement(new String(chars));
/*     */   }
/*     */ 
/*     */   public void write(char[] chars, int off, int len) {
/* 117 */     this.v.addElement(new String(chars, off, len));
/*     */   }
/*     */ 
/*     */   public void write(String s, int off, int len) {
/* 121 */     this.v.addElement(s.substring(off, off + len));
/*     */   }
/*     */ 
/*     */   public void write(String s) {
/* 125 */     this.v.addElement(s);
/*     */   }
/*     */ 
/*     */   public String[] toStringArray() {
/* 129 */     int len = this.v.size();
/* 130 */     String[] sa = new String[len];
/* 131 */     for (int i = 0; i < len; i++) {
/* 132 */       sa[i] = ((String)this.v.elementAt(i));
/*     */     }
/* 134 */     return sa;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.spi.VectorWriter
 * JD-Core Version:    0.6.0
 */
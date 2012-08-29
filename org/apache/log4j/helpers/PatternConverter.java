/*     */ package org.apache.log4j.helpers;
/*     */ 
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ public abstract class PatternConverter
/*     */ {
/*     */   public PatternConverter next;
/*  37 */   int min = -1;
/*  38 */   int max = 2147483647;
/*  39 */   boolean leftAlign = false;
/*     */ 
/*  90 */   static String[] SPACES = { " ", "  ", "    ", "        ", "                ", "                                " };
/*     */ 
/*     */   protected PatternConverter()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected PatternConverter(FormattingInfo fi)
/*     */   {
/*  46 */     this.min = fi.min;
/*  47 */     this.max = fi.max;
/*  48 */     this.leftAlign = fi.leftAlign;
/*     */   }
/*     */ 
/*     */   protected abstract String convert(LoggingEvent paramLoggingEvent);
/*     */ 
/*     */   public void format(StringBuffer sbuf, LoggingEvent e)
/*     */   {
/*  64 */     String s = convert(e);
/*     */ 
/*  66 */     if (s == null) {
/*  67 */       if (0 < this.min)
/*  68 */         spacePad(sbuf, this.min);
/*  69 */       return;
/*     */     }
/*     */ 
/*  72 */     int len = s.length();
/*     */ 
/*  74 */     if (len > this.max)
/*  75 */       sbuf.append(s.substring(len - this.max));
/*  76 */     else if (len < this.min) {
/*  77 */       if (this.leftAlign) {
/*  78 */         sbuf.append(s);
/*  79 */         spacePad(sbuf, this.min - len);
/*     */       }
/*     */       else {
/*  82 */         spacePad(sbuf, this.min - len);
/*  83 */         sbuf.append(s);
/*     */       }
/*     */     }
/*     */     else
/*  87 */       sbuf.append(s);
/*     */   }
/*     */ 
/*     */   public void spacePad(StringBuffer sbuf, int length)
/*     */   {
/*  99 */     while (length >= 32) {
/* 100 */       sbuf.append(SPACES[5]);
/* 101 */       length -= 32;
/*     */     }
/*     */ 
/* 104 */     for (int i = 4; i >= 0; i--)
/* 105 */       if ((length & 1 << i) != 0)
/* 106 */         sbuf.append(SPACES[i]);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.helpers.PatternConverter
 * JD-Core Version:    0.6.0
 */
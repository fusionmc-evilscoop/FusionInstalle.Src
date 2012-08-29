/*     */ package org.apache.log4j;
/*     */ 
/*     */ import org.apache.log4j.helpers.PatternConverter;
/*     */ import org.apache.log4j.helpers.PatternParser;
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ public class PatternLayout extends Layout
/*     */ {
/*     */   public static final String DEFAULT_CONVERSION_PATTERN = "%m%n";
/*     */   public static final String TTCC_CONVERSION_PATTERN = "%r [%t] %p %c %x - %m%n";
/* 408 */   protected final int BUF_SIZE = 256;
/* 409 */   protected final int MAX_CAPACITY = 1024;
/*     */ 
/* 413 */   private StringBuffer sbuf = new StringBuffer(256);
/*     */   private String pattern;
/*     */   private PatternConverter head;
/*     */   private String timezone;
/*     */ 
/*     */   public PatternLayout()
/*     */   {
/* 427 */     this("%m%n");
/*     */   }
/*     */ 
/*     */   public PatternLayout(String pattern)
/*     */   {
/* 434 */     this.pattern = pattern;
/* 435 */     this.head = createPatternParser(pattern == null ? "%m%n" : pattern).parse();
/*     */   }
/*     */ 
/*     */   public void setConversionPattern(String conversionPattern)
/*     */   {
/* 446 */     this.pattern = conversionPattern;
/* 447 */     this.head = createPatternParser(conversionPattern).parse();
/*     */   }
/*     */ 
/*     */   public String getConversionPattern()
/*     */   {
/* 455 */     return this.pattern;
/*     */   }
/*     */ 
/*     */   public void activateOptions()
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean ignoresThrowable()
/*     */   {
/* 474 */     return true;
/*     */   }
/*     */ 
/*     */   protected PatternParser createPatternParser(String pattern)
/*     */   {
/* 485 */     return new PatternParser(pattern);
/*     */   }
/*     */ 
/*     */   public String format(LoggingEvent event)
/*     */   {
/* 494 */     if (this.sbuf.capacity() > 1024)
/* 495 */       this.sbuf = new StringBuffer(256);
/*     */     else {
/* 497 */       this.sbuf.setLength(0);
/*     */     }
/*     */ 
/* 500 */     PatternConverter c = this.head;
/*     */ 
/* 502 */     while (c != null) {
/* 503 */       c.format(this.sbuf, event);
/* 504 */       c = c.next;
/*     */     }
/* 506 */     return this.sbuf.toString();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.PatternLayout
 * JD-Core Version:    0.6.0
 */
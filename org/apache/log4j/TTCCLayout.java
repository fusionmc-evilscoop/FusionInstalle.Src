/*     */ package org.apache.log4j;
/*     */ 
/*     */ import org.apache.log4j.helpers.DateLayout;
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ public class TTCCLayout extends DateLayout
/*     */ {
/*  76 */   private boolean threadPrinting = true;
/*  77 */   private boolean categoryPrefixing = true;
/*  78 */   private boolean contextPrinting = true;
/*     */ 
/*  81 */   protected final StringBuffer buf = new StringBuffer(256);
/*     */ 
/*     */   public TTCCLayout()
/*     */   {
/*  91 */     setDateFormat("RELATIVE", null);
/*     */   }
/*     */ 
/*     */   public TTCCLayout(String dateFormatType)
/*     */   {
/* 104 */     setDateFormat(dateFormatType);
/*     */   }
/*     */ 
/*     */   public void setThreadPrinting(boolean threadPrinting)
/*     */   {
/* 114 */     this.threadPrinting = threadPrinting;
/*     */   }
/*     */ 
/*     */   public boolean getThreadPrinting()
/*     */   {
/* 122 */     return this.threadPrinting;
/*     */   }
/*     */ 
/*     */   public void setCategoryPrefixing(boolean categoryPrefixing)
/*     */   {
/* 131 */     this.categoryPrefixing = categoryPrefixing;
/*     */   }
/*     */ 
/*     */   public boolean getCategoryPrefixing()
/*     */   {
/* 139 */     return this.categoryPrefixing;
/*     */   }
/*     */ 
/*     */   public void setContextPrinting(boolean contextPrinting)
/*     */   {
/* 149 */     this.contextPrinting = contextPrinting;
/*     */   }
/*     */ 
/*     */   public boolean getContextPrinting()
/*     */   {
/* 157 */     return this.contextPrinting;
/*     */   }
/*     */ 
/*     */   public String format(LoggingEvent event)
/*     */   {
/* 175 */     this.buf.setLength(0);
/*     */ 
/* 177 */     dateFormat(this.buf, event);
/*     */ 
/* 179 */     if (this.threadPrinting) {
/* 180 */       this.buf.append('[');
/* 181 */       this.buf.append(event.getThreadName());
/* 182 */       this.buf.append("] ");
/*     */     }
/* 184 */     this.buf.append(event.getLevel().toString());
/* 185 */     this.buf.append(' ');
/*     */ 
/* 187 */     if (this.categoryPrefixing) {
/* 188 */       this.buf.append(event.getLoggerName());
/* 189 */       this.buf.append(' ');
/*     */     }
/*     */ 
/* 192 */     if (this.contextPrinting) {
/* 193 */       String ndc = event.getNDC();
/*     */ 
/* 195 */       if (ndc != null) {
/* 196 */         this.buf.append(ndc);
/* 197 */         this.buf.append(' ');
/*     */       }
/*     */     }
/* 200 */     this.buf.append("- ");
/* 201 */     this.buf.append(event.getRenderedMessage());
/* 202 */     this.buf.append(Layout.LINE_SEP);
/* 203 */     return this.buf.toString();
/*     */   }
/*     */ 
/*     */   public boolean ignoresThrowable()
/*     */   {
/* 214 */     return true;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.TTCCLayout
 * JD-Core Version:    0.6.0
 */
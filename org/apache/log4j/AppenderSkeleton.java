/*     */ package org.apache.log4j;
/*     */ 
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ import org.apache.log4j.helpers.OnlyOnceErrorHandler;
/*     */ import org.apache.log4j.spi.ErrorHandler;
/*     */ import org.apache.log4j.spi.Filter;
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ import org.apache.log4j.spi.OptionHandler;
/*     */ 
/*     */ public abstract class AppenderSkeleton
/*     */   implements Appender, OptionHandler
/*     */ {
/*     */   protected Layout layout;
/*     */   protected String name;
/*     */   protected Priority threshold;
/*  53 */   protected ErrorHandler errorHandler = new OnlyOnceErrorHandler();
/*     */   protected Filter headFilter;
/*     */   protected Filter tailFilter;
/*  64 */   protected boolean closed = false;
/*     */ 
/*     */   public void activateOptions()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void addFilter(Filter newFilter)
/*     */   {
/*  82 */     if (this.headFilter == null) {
/*  83 */       this.headFilter = (this.tailFilter = newFilter);
/*     */     } else {
/*  85 */       this.tailFilter.next = newFilter;
/*  86 */       this.tailFilter = newFilter;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected abstract void append(LoggingEvent paramLoggingEvent);
/*     */ 
/*     */   public void clearFilters()
/*     */   {
/* 108 */     this.headFilter = (this.tailFilter = null);
/*     */   }
/*     */ 
/*     */   public void finalize()
/*     */   {
/* 120 */     if (this.closed) {
/* 121 */       return;
/*     */     }
/* 123 */     LogLog.debug("Finalizing appender named [" + this.name + "].");
/* 124 */     close();
/*     */   }
/*     */ 
/*     */   public ErrorHandler getErrorHandler()
/*     */   {
/* 135 */     return this.errorHandler;
/*     */   }
/*     */ 
/*     */   public Filter getFilter()
/*     */   {
/* 146 */     return this.headFilter;
/*     */   }
/*     */ 
/*     */   public final Filter getFirstFilter()
/*     */   {
/* 158 */     return this.headFilter;
/*     */   }
/*     */ 
/*     */   public Layout getLayout()
/*     */   {
/* 166 */     return this.layout;
/*     */   }
/*     */ 
/*     */   public final String getName()
/*     */   {
/* 176 */     return this.name;
/*     */   }
/*     */ 
/*     */   public Priority getThreshold()
/*     */   {
/* 186 */     return this.threshold;
/*     */   }
/*     */ 
/*     */   public boolean isAsSevereAsThreshold(Priority priority)
/*     */   {
/* 198 */     return (this.threshold == null) || (priority.isGreaterOrEqual(this.threshold));
/*     */   }
/*     */ 
/*     */   public synchronized void doAppend(LoggingEvent event)
/*     */   {
/* 210 */     if (this.closed) {
/* 211 */       LogLog.error("Attempted to append to closed appender named [" + this.name + "].");
/* 212 */       return;
/*     */     }
/*     */ 
/* 215 */     if (!isAsSevereAsThreshold(event.getLevel())) {
/* 216 */       return;
/*     */     }
/*     */ 
/* 219 */     Filter f = this.headFilter;
/*     */ 
/* 222 */     while (f != null) {
/* 223 */       switch (f.decide(event)) { case -1:
/* 224 */         return;
/*     */       case 1:
/* 225 */         break;
/*     */       case 0:
/* 226 */         f = f.next;
/*     */       }
/*     */     }
/*     */ 
/* 230 */     append(event);
/*     */   }
/*     */ 
/*     */   public synchronized void setErrorHandler(ErrorHandler eh)
/*     */   {
/* 240 */     if (eh == null)
/*     */     {
/* 243 */       LogLog.warn("You have tried to set a null error-handler.");
/*     */     }
/* 245 */     else this.errorHandler = eh;
/*     */   }
/*     */ 
/*     */   public void setLayout(Layout layout)
/*     */   {
/* 257 */     this.layout = layout;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 266 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public void setThreshold(Priority threshold)
/*     */   {
/* 281 */     this.threshold = threshold;
/*     */   }
/*     */ 
/*     */   public abstract boolean requiresLayout();
/*     */ 
/*     */   public abstract void close();
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.AppenderSkeleton
 * JD-Core Version:    0.6.0
 */
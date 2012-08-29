/*     */ package org.apache.log4j;
/*     */ 
/*     */ import java.text.MessageFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.helpers.AppenderAttachableImpl;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ import org.apache.log4j.spi.AppenderAttachable;
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ public class AsyncAppender extends AppenderSkeleton
/*     */   implements AppenderAttachable
/*     */ {
/*     */   public static final int DEFAULT_BUFFER_SIZE = 128;
/*  67 */   private final List buffer = new ArrayList();
/*     */ 
/*  72 */   private final Map discardMap = new HashMap();
/*     */ 
/*  77 */   private int bufferSize = 128;
/*     */   AppenderAttachableImpl aai;
/*     */   private final AppenderAttachableImpl appenders;
/*     */   private final Thread dispatcher;
/*  95 */   private boolean locationInfo = false;
/*     */ 
/* 100 */   private boolean blocking = true;
/*     */ 
/*     */   public AsyncAppender()
/*     */   {
/* 106 */     this.appenders = new AppenderAttachableImpl();
/*     */ 
/* 110 */     this.aai = this.appenders;
/*     */ 
/* 112 */     this.dispatcher = new Thread(new Dispatcher(this, this.buffer, this.discardMap, this.appenders));
/*     */ 
/* 117 */     this.dispatcher.setDaemon(true);
/*     */ 
/* 121 */     this.dispatcher.setName("Dispatcher-" + this.dispatcher.getName());
/* 122 */     this.dispatcher.start();
/*     */   }
/*     */ 
/*     */   public void addAppender(Appender newAppender)
/*     */   {
/* 131 */     synchronized (this.appenders) {
/* 132 */       this.appenders.addAppender(newAppender);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void append(LoggingEvent event)
/*     */   {
/* 144 */     if ((this.dispatcher == null) || (!this.dispatcher.isAlive()) || (this.bufferSize <= 0)) {
/* 145 */       synchronized (this.appenders) {
/* 146 */         this.appenders.appendLoopOnAppenders(event);
/*     */       }
/*     */ 
/* 149 */       return;
/*     */     }
/*     */ 
/* 154 */     event.getNDC();
/* 155 */     event.getThreadName();
/*     */ 
/* 157 */     event.getMDCCopy();
/* 158 */     if (this.locationInfo) {
/* 159 */       event.getLocationInformation();
/*     */     }
/*     */ 
/* 162 */     synchronized (this.buffer) {
/*     */       while (true) {
/* 164 */         int previousSize = this.buffer.size();
/*     */ 
/* 166 */         if (previousSize < this.bufferSize) {
/* 167 */           this.buffer.add(event);
/*     */ 
/* 174 */           if (previousSize != 0) break;
/* 175 */           this.buffer.notifyAll(); break;
/*     */         }
/*     */ 
/* 188 */         boolean discard = true;
/* 189 */         if ((this.blocking) && (!Thread.interrupted()) && (Thread.currentThread() != this.dispatcher))
/*     */         {
/*     */           try
/*     */           {
/* 193 */             this.buffer.wait();
/* 194 */             discard = false;
/*     */           }
/*     */           catch (InterruptedException e)
/*     */           {
/* 200 */             Thread.currentThread().interrupt();
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 208 */         if (discard) {
/* 209 */           String loggerName = event.getLoggerName();
/* 210 */           DiscardSummary summary = (DiscardSummary)this.discardMap.get(loggerName);
/*     */ 
/* 212 */           if (summary == null) {
/* 213 */             summary = new DiscardSummary(event);
/* 214 */             this.discardMap.put(loggerName, summary); break;
/*     */           }
/* 216 */           summary.add(event);
/*     */ 
/* 219 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/* 234 */     synchronized (this.buffer) {
/* 235 */       this.closed = true;
/* 236 */       this.buffer.notifyAll();
/*     */     }
/*     */     try
/*     */     {
/* 240 */       this.dispatcher.join();
/*     */     } catch (InterruptedException localObject1) {
/* 242 */       Thread.currentThread().interrupt();
/* 243 */       LogLog.error("Got an InterruptedException while waiting for the dispatcher to finish.", (Throwable)???);
/*     */     }
/*     */ 
/* 251 */     synchronized (this.appenders) {
/* 252 */       Enumeration iter = this.appenders.getAllAppenders();
/*     */ 
/* 254 */       if (iter != null)
/* 255 */         while (iter.hasMoreElements()) {
/* 256 */           Object next = iter.nextElement();
/*     */ 
/* 258 */           if ((next instanceof Appender))
/* 259 */             ((Appender)next).close();
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Enumeration getAllAppenders()
/*     */   {
/* 271 */     synchronized (this.appenders) {
/* 272 */       return this.appenders.getAllAppenders();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Appender getAppender(String name)
/*     */   {
/* 283 */     synchronized (this.appenders) {
/* 284 */       return this.appenders.getAppender(name);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean getLocationInfo()
/*     */   {
/* 295 */     return this.locationInfo;
/*     */   }
/*     */ 
/*     */   public boolean isAttached(Appender appender)
/*     */   {
/* 304 */     synchronized (this.appenders) {
/* 305 */       return this.appenders.isAttached(appender);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean requiresLayout()
/*     */   {
/* 313 */     return false;
/*     */   }
/*     */ 
/*     */   public void removeAllAppenders()
/*     */   {
/* 320 */     synchronized (this.appenders) {
/* 321 */       this.appenders.removeAllAppenders();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removeAppender(Appender appender)
/*     */   {
/* 330 */     synchronized (this.appenders) {
/* 331 */       this.appenders.removeAppender(appender);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removeAppender(String name)
/*     */   {
/* 340 */     synchronized (this.appenders) {
/* 341 */       this.appenders.removeAppender(name);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setLocationInfo(boolean flag)
/*     */   {
/* 359 */     this.locationInfo = flag;
/*     */   }
/*     */ 
/*     */   public void setBufferSize(int size)
/*     */   {
/* 375 */     if (size < 0) {
/* 376 */       throw new NegativeArraySizeException("size");
/*     */     }
/*     */ 
/* 379 */     synchronized (this.buffer)
/*     */     {
/* 383 */       this.bufferSize = (size < 1 ? 1 : size);
/* 384 */       this.buffer.notifyAll();
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getBufferSize()
/*     */   {
/* 393 */     return this.bufferSize;
/*     */   }
/*     */ 
/*     */   public void setBlocking(boolean value)
/*     */   {
/* 403 */     synchronized (this.buffer) {
/* 404 */       this.blocking = value;
/* 405 */       this.buffer.notifyAll();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean getBlocking()
/*     */   {
/* 417 */     return this.blocking;
/*     */   }
/*     */ 
/*     */   private static class Dispatcher
/*     */     implements Runnable
/*     */   {
/*     */     private final AsyncAppender parent;
/*     */     private final List buffer;
/*     */     private final Map discardMap;
/*     */     private final AppenderAttachableImpl appenders;
/*     */ 
/*     */     public Dispatcher(AsyncAppender parent, List buffer, Map discardMap, AppenderAttachableImpl appenders)
/*     */     {
/* 509 */       this.parent = parent;
/* 510 */       this.buffer = buffer;
/* 511 */       this.appenders = appenders;
/* 512 */       this.discardMap = discardMap;
/*     */     }
/*     */ 
/*     */     public void run()
/*     */     {
/* 519 */       boolean isActive = true;
/*     */       try
/*     */       {
/* 528 */         while (isActive) {
/* 529 */           LoggingEvent[] events = null;
/*     */ 
/* 535 */           synchronized (this.buffer) {
/* 536 */             int bufferSize = this.buffer.size();
/* 537 */             isActive = !this.parent.closed;
/*     */ 
/* 539 */             while ((bufferSize == 0) && (isActive)) {
/* 540 */               this.buffer.wait();
/* 541 */               bufferSize = this.buffer.size();
/* 542 */               isActive = !this.parent.closed;
/*     */             }
/*     */ 
/* 545 */             if (bufferSize > 0) {
/* 546 */               events = new LoggingEvent[bufferSize + this.discardMap.size()];
/* 547 */               this.buffer.toArray(events);
/*     */ 
/* 552 */               ??? = bufferSize;
/*     */ 
/* 555 */               Iterator iter = this.discardMap.values().iterator();
/* 556 */               while (iter.hasNext()) {
/* 557 */                 events[(???++)] = ((AsyncAppender.DiscardSummary)iter.next()).createEvent();
/*     */               }
/*     */ 
/* 563 */               this.buffer.clear();
/* 564 */               this.discardMap.clear();
/*     */ 
/* 568 */               this.buffer.notifyAll();
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 575 */           if (events != null)
/* 576 */             for (int i = 0; i < events.length; i++)
/* 577 */               synchronized (this.appenders) {
/* 578 */                 this.appenders.appendLoopOnAppenders(events[i]);
/*     */               }
/*     */         }
/*     */       }
/*     */       catch (InterruptedException ex)
/*     */       {
/* 584 */         Thread.currentThread().interrupt();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static final class DiscardSummary
/*     */   {
/*     */     private LoggingEvent maxEvent;
/*     */     private int count;
/*     */ 
/*     */     public DiscardSummary(LoggingEvent event)
/*     */     {
/* 440 */       this.maxEvent = event;
/* 441 */       this.count = 1;
/*     */     }
/*     */ 
/*     */     public void add(LoggingEvent event)
/*     */     {
/* 450 */       if (event.getLevel().toInt() > this.maxEvent.getLevel().toInt()) {
/* 451 */         this.maxEvent = event;
/*     */       }
/*     */ 
/* 454 */       this.count += 1;
/*     */     }
/*     */ 
/*     */     public LoggingEvent createEvent()
/*     */     {
/* 463 */       String msg = MessageFormat.format("Discarded {0} messages due to full event buffer including: {1}", new Object[] { new Integer(this.count), this.maxEvent.getMessage() });
/*     */ 
/* 468 */       return new LoggingEvent(null, Logger.getLogger(this.maxEvent.getLoggerName()), this.maxEvent.getLevel(), msg, null);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.AsyncAppender
 * JD-Core Version:    0.6.0
 */
/*     */ package org.apache.log4j.spi;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Hashtable;
/*     */ import org.apache.log4j.Category;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.MDC;
/*     */ import org.apache.log4j.NDC;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.apache.log4j.helpers.Loader;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ import org.apache.log4j.or.RendererMap;
/*     */ 
/*     */ public class LoggingEvent
/*     */   implements Serializable
/*     */ {
/*  46 */   private static long startTime = System.currentTimeMillis();
/*     */   public final transient String fqnOfCategoryClass;
/*     */ 
/*     */   /** @deprecated */
/*     */   private transient Category logger;
/*     */ 
/*     */   /** @deprecated */
/*     */   public final String categoryName;
/*     */ 
/*     */   /** @deprecated */
/*     */   public transient Priority level;
/*     */   private String ndc;
/*     */   private Hashtable mdcCopy;
/*  98 */   private boolean ndcLookupRequired = true;
/*     */ 
/* 104 */   private boolean mdcCopyLookupRequired = true;
/*     */   private transient Object message;
/*     */   private String renderedMessage;
/*     */   private String threadName;
/*     */   private ThrowableInformation throwableInfo;
/*     */   public final long timeStamp;
/*     */   private LocationInfo locationInfo;
/*     */   static final long serialVersionUID = -868428216207166145L;
/* 131 */   static final Integer[] PARAM_ARRAY = new Integer[1];
/*     */   static final String TO_LEVEL = "toLevel";
/* 133 */   static final Class[] TO_LEVEL_PARAMS = { Integer.TYPE };
/* 134 */   static final Hashtable methodCache = new Hashtable(3);
/*     */ 
/*     */   public LoggingEvent(String fqnOfCategoryClass, Category logger, Priority level, Object message, Throwable throwable)
/*     */   {
/* 148 */     this.fqnOfCategoryClass = fqnOfCategoryClass;
/* 149 */     this.logger = logger;
/* 150 */     this.categoryName = logger.getName();
/* 151 */     this.level = level;
/* 152 */     this.message = message;
/* 153 */     if (throwable != null) {
/* 154 */       this.throwableInfo = new ThrowableInformation(throwable);
/*     */     }
/* 156 */     this.timeStamp = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */   public LoggingEvent(String fqnOfCategoryClass, Category logger, long timeStamp, Priority level, Object message, Throwable throwable)
/*     */   {
/* 173 */     this.fqnOfCategoryClass = fqnOfCategoryClass;
/* 174 */     this.logger = logger;
/* 175 */     this.categoryName = logger.getName();
/* 176 */     this.level = level;
/* 177 */     this.message = message;
/* 178 */     if (throwable != null) {
/* 179 */       this.throwableInfo = new ThrowableInformation(throwable);
/*     */     }
/*     */ 
/* 182 */     this.timeStamp = timeStamp;
/*     */   }
/*     */ 
/*     */   public LocationInfo getLocationInformation()
/*     */   {
/* 190 */     if (this.locationInfo == null) {
/* 191 */       this.locationInfo = new LocationInfo(new Throwable(), this.fqnOfCategoryClass);
/*     */     }
/* 193 */     return this.locationInfo;
/*     */   }
/*     */ 
/*     */   public Level getLevel()
/*     */   {
/* 200 */     return (Level)this.level;
/*     */   }
/*     */ 
/*     */   public String getLoggerName()
/*     */   {
/* 208 */     return this.categoryName;
/*     */   }
/*     */ 
/*     */   public Object getMessage()
/*     */   {
/* 222 */     if (this.message != null) {
/* 223 */       return this.message;
/*     */     }
/* 225 */     return getRenderedMessage();
/*     */   }
/*     */ 
/*     */   public String getNDC()
/*     */   {
/* 236 */     if (this.ndcLookupRequired) {
/* 237 */       this.ndcLookupRequired = false;
/* 238 */       this.ndc = NDC.get();
/*     */     }
/* 240 */     return this.ndc;
/*     */   }
/*     */ 
/*     */   public Object getMDC(String key)
/*     */   {
/* 261 */     if (this.mdcCopy != null) {
/* 262 */       Object r = this.mdcCopy.get(key);
/* 263 */       if (r != null) {
/* 264 */         return r;
/*     */       }
/*     */     }
/* 267 */     return MDC.get(key);
/*     */   }
/*     */ 
/*     */   public void getMDCCopy()
/*     */   {
/* 276 */     if (this.mdcCopyLookupRequired) {
/* 277 */       this.mdcCopyLookupRequired = false;
/*     */ 
/* 280 */       Hashtable t = MDC.getContext();
/* 281 */       if (t != null)
/* 282 */         this.mdcCopy = ((Hashtable)t.clone());
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getRenderedMessage()
/*     */   {
/* 289 */     if ((this.renderedMessage == null) && (this.message != null)) {
/* 290 */       if ((this.message instanceof String)) {
/* 291 */         this.renderedMessage = ((String)this.message);
/*     */       } else {
/* 293 */         LoggerRepository repository = this.logger.getLoggerRepository();
/*     */ 
/* 295 */         if ((repository instanceof RendererSupport)) {
/* 296 */           RendererSupport rs = (RendererSupport)repository;
/* 297 */           this.renderedMessage = rs.getRendererMap().findAndRender(this.message);
/*     */         } else {
/* 299 */           this.renderedMessage = this.message.toString();
/*     */         }
/*     */       }
/*     */     }
/* 303 */     return this.renderedMessage;
/*     */   }
/*     */ 
/*     */   public static long getStartTime()
/*     */   {
/* 310 */     return startTime;
/*     */   }
/*     */ 
/*     */   public String getThreadName()
/*     */   {
/* 315 */     if (this.threadName == null)
/* 316 */       this.threadName = Thread.currentThread().getName();
/* 317 */     return this.threadName;
/*     */   }
/*     */ 
/*     */   public ThrowableInformation getThrowableInformation()
/*     */   {
/* 330 */     return this.throwableInfo;
/*     */   }
/*     */ 
/*     */   public String[] getThrowableStrRep()
/*     */   {
/* 339 */     if (this.throwableInfo == null) {
/* 340 */       return null;
/*     */     }
/* 342 */     return this.throwableInfo.getThrowableStrRep();
/*     */   }
/*     */ 
/*     */   private void readLevel(ObjectInputStream ois)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 350 */     int p = ois.readInt();
/*     */     try {
/* 352 */       String className = (String)ois.readObject();
/* 353 */       if (className == null) {
/* 354 */         this.level = Level.toLevel(p);
/*     */       } else {
/* 356 */         Method m = (Method)methodCache.get(className);
/* 357 */         if (m == null) {
/* 358 */           Class clazz = Loader.loadClass(className);
/*     */ 
/* 365 */           m = clazz.getDeclaredMethod("toLevel", TO_LEVEL_PARAMS);
/* 366 */           methodCache.put(className, m);
/*     */         }
/* 368 */         PARAM_ARRAY[0] = new Integer(p);
/* 369 */         this.level = ((Level)m.invoke(null, PARAM_ARRAY));
/*     */       }
/*     */     } catch (Exception e) {
/* 372 */       LogLog.warn("Level deserialization failed, reverting to default.", e);
/* 373 */       this.level = Level.toLevel(p);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException
/*     */   {
/* 379 */     ois.defaultReadObject();
/* 380 */     readLevel(ois);
/*     */ 
/* 383 */     if (this.locationInfo == null)
/* 384 */       this.locationInfo = new LocationInfo(null, null);
/*     */   }
/*     */ 
/*     */   private void writeObject(ObjectOutputStream oos)
/*     */     throws IOException
/*     */   {
/* 391 */     getThreadName();
/*     */ 
/* 394 */     getRenderedMessage();
/*     */ 
/* 398 */     getNDC();
/*     */ 
/* 402 */     getMDCCopy();
/*     */ 
/* 405 */     getThrowableStrRep();
/*     */ 
/* 407 */     oos.defaultWriteObject();
/*     */ 
/* 410 */     writeLevel(oos);
/*     */   }
/*     */ 
/*     */   private void writeLevel(ObjectOutputStream oos)
/*     */     throws IOException
/*     */   {
/* 416 */     oos.writeInt(this.level.toInt());
/*     */ 
/* 418 */     Class clazz = this.level.getClass();
/* 419 */     if (clazz == Level.class) {
/* 420 */       oos.writeObject(null);
/*     */     }
/*     */     else
/*     */     {
/* 425 */       oos.writeObject(clazz.getName());
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.spi.LoggingEvent
 * JD-Core Version:    0.6.0
 */
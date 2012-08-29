/*     */ package org.apache.log4j;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ import org.apache.log4j.or.ObjectRenderer;
/*     */ import org.apache.log4j.or.RendererMap;
/*     */ import org.apache.log4j.spi.HierarchyEventListener;
/*     */ import org.apache.log4j.spi.LoggerFactory;
/*     */ import org.apache.log4j.spi.LoggerRepository;
/*     */ import org.apache.log4j.spi.RendererSupport;
/*     */ 
/*     */ public class Hierarchy
/*     */   implements LoggerRepository, RendererSupport
/*     */ {
/*     */   private LoggerFactory defaultFactory;
/*     */   private Vector listeners;
/*     */   Hashtable ht;
/*     */   Logger root;
/*     */   RendererMap rendererMap;
/*     */   int thresholdInt;
/*     */   Level threshold;
/*  76 */   boolean emittedNoAppenderWarning = false;
/*  77 */   boolean emittedNoResourceBundleWarning = false;
/*     */ 
/*     */   public Hierarchy(Logger root)
/*     */   {
/*  87 */     this.ht = new Hashtable();
/*  88 */     this.listeners = new Vector(1);
/*  89 */     this.root = root;
/*     */ 
/*  91 */     setThreshold(Level.ALL);
/*  92 */     this.root.setHierarchy(this);
/*  93 */     this.rendererMap = new RendererMap();
/*  94 */     this.defaultFactory = new DefaultCategoryFactory();
/*     */   }
/*     */ 
/*     */   public void addRenderer(Class classToRender, ObjectRenderer or)
/*     */   {
/* 102 */     this.rendererMap.put(classToRender, or);
/*     */   }
/*     */ 
/*     */   public void addHierarchyEventListener(HierarchyEventListener listener)
/*     */   {
/* 107 */     if (this.listeners.contains(listener))
/* 108 */       LogLog.warn("Ignoring attempt to add an existent listener.");
/*     */     else
/* 110 */       this.listeners.addElement(listener);
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 126 */     this.ht.clear();
/*     */   }
/*     */ 
/*     */   public void emitNoAppenderWarning(Category cat)
/*     */   {
/* 132 */     if (!this.emittedNoAppenderWarning) {
/* 133 */       LogLog.warn("No appenders could be found for logger (" + cat.getName() + ").");
/*     */ 
/* 135 */       LogLog.warn("Please initialize the log4j system properly.");
/* 136 */       this.emittedNoAppenderWarning = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Logger exists(String name)
/*     */   {
/* 149 */     Object o = this.ht.get(new CategoryKey(name));
/* 150 */     if ((o instanceof Logger)) {
/* 151 */       return (Logger)o;
/*     */     }
/* 153 */     return null;
/*     */   }
/*     */ 
/*     */   public void setThreshold(String levelStr)
/*     */   {
/* 162 */     Level l = Level.toLevel(levelStr, null);
/* 163 */     if (l != null)
/* 164 */       setThreshold(l);
/*     */     else
/* 166 */       LogLog.warn("Could not convert [" + levelStr + "] to Level.");
/*     */   }
/*     */ 
/*     */   public void setThreshold(Level l)
/*     */   {
/* 179 */     if (l != null) {
/* 180 */       this.thresholdInt = l.level;
/* 181 */       this.threshold = l;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void fireAddAppenderEvent(Category logger, Appender appender)
/*     */   {
/* 187 */     if (this.listeners != null) {
/* 188 */       int size = this.listeners.size();
/*     */ 
/* 190 */       for (int i = 0; i < size; i++) {
/* 191 */         HierarchyEventListener listener = (HierarchyEventListener)this.listeners.elementAt(i);
/* 192 */         listener.addAppenderEvent(logger, appender);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   void fireRemoveAppenderEvent(Category logger, Appender appender) {
/* 198 */     if (this.listeners != null) {
/* 199 */       int size = this.listeners.size();
/*     */ 
/* 201 */       for (int i = 0; i < size; i++) {
/* 202 */         HierarchyEventListener listener = (HierarchyEventListener)this.listeners.elementAt(i);
/* 203 */         listener.removeAppenderEvent(logger, appender);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Level getThreshold()
/*     */   {
/* 215 */     return this.threshold;
/*     */   }
/*     */ 
/*     */   public Logger getLogger(String name)
/*     */   {
/* 242 */     return getLogger(name, this.defaultFactory);
/*     */   }
/*     */ 
/*     */   public Logger getLogger(String name, LoggerFactory factory)
/*     */   {
/* 261 */     CategoryKey key = new CategoryKey(name);
/*     */ 
/* 267 */     synchronized (this.ht) {
/* 268 */       Object o = this.ht.get(key);
/*     */       Logger logger;
/*     */       Logger localLogger1;
/* 269 */       if (o == null) {
/* 270 */         logger = factory.makeNewLoggerInstance(name);
/* 271 */         logger.setHierarchy(this);
/* 272 */         this.ht.put(key, logger);
/* 273 */         updateParents(logger);
/* 274 */         return logger;
/* 275 */       }if ((o instanceof Logger))
/* 276 */         return (Logger)o;
/* 277 */       if ((o instanceof ProvisionNode))
/*     */       {
/* 279 */         logger = factory.makeNewLoggerInstance(name);
/* 280 */         logger.setHierarchy(this);
/* 281 */         this.ht.put(key, logger);
/* 282 */         updateChildren((ProvisionNode)o, logger);
/* 283 */         updateParents(logger);
/* 284 */         return logger;
/*     */       }
/*     */ 
/* 288 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Enumeration getCurrentLoggers()
/*     */   {
/* 304 */     Vector v = new Vector(this.ht.size());
/*     */ 
/* 306 */     Enumeration elems = this.ht.elements();
/* 307 */     while (elems.hasMoreElements()) {
/* 308 */       Object o = elems.nextElement();
/* 309 */       if ((o instanceof Logger)) {
/* 310 */         v.addElement(o);
/*     */       }
/*     */     }
/* 313 */     return v.elements();
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Enumeration getCurrentCategories()
/*     */   {
/* 321 */     return getCurrentLoggers();
/*     */   }
/*     */ 
/*     */   public RendererMap getRendererMap()
/*     */   {
/* 330 */     return this.rendererMap;
/*     */   }
/*     */ 
/*     */   public Logger getRootLogger()
/*     */   {
/* 341 */     return this.root;
/*     */   }
/*     */ 
/*     */   public boolean isDisabled(int level)
/*     */   {
/* 351 */     return this.thresholdInt > level;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void overrideAsNeeded(String override)
/*     */   {
/* 359 */     LogLog.warn("The Hiearchy.overrideAsNeeded method has been deprecated.");
/*     */   }
/*     */ 
/*     */   public void resetConfiguration()
/*     */   {
/* 379 */     getRootLogger().setLevel(Level.DEBUG);
/* 380 */     this.root.setResourceBundle(null);
/* 381 */     setThreshold(Level.ALL);
/*     */ 
/* 385 */     synchronized (this.ht) {
/* 386 */       shutdown();
/*     */ 
/* 388 */       Enumeration cats = getCurrentLoggers();
/* 389 */       while (cats.hasMoreElements()) {
/* 390 */         Logger c = (Logger)cats.nextElement();
/* 391 */         c.setLevel(null);
/* 392 */         c.setAdditivity(true);
/* 393 */         c.setResourceBundle(null);
/*     */       }
/*     */     }
/* 396 */     this.rendererMap.clear();
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void setDisableOverride(String override)
/*     */   {
/* 406 */     LogLog.warn("The Hiearchy.setDisableOverride method has been deprecated.");
/*     */   }
/*     */ 
/*     */   public void setRenderer(Class renderedClass, ObjectRenderer renderer)
/*     */   {
/* 416 */     this.rendererMap.put(renderedClass, renderer);
/*     */   }
/*     */ 
/*     */   public void shutdown()
/*     */   {
/* 438 */     Logger root = getRootLogger();
/*     */ 
/* 441 */     root.closeNestedAppenders();
/*     */ 
/* 443 */     synchronized (this.ht) {
/* 444 */       Enumeration cats = getCurrentLoggers();
/* 445 */       while (cats.hasMoreElements()) {
/* 446 */         Logger c = (Logger)cats.nextElement();
/* 447 */         c.closeNestedAppenders();
/*     */       }
/*     */ 
/* 451 */       root.removeAllAppenders();
/* 452 */       cats = getCurrentLoggers();
/* 453 */       while (cats.hasMoreElements()) {
/* 454 */         Logger c = (Logger)cats.nextElement();
/* 455 */         c.removeAllAppenders();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private final void updateParents(Logger cat)
/*     */   {
/* 484 */     String name = cat.name;
/* 485 */     int length = name.length();
/* 486 */     boolean parentFound = false;
/*     */ 
/* 491 */     for (int i = name.lastIndexOf('.', length - 1); i >= 0; )
/*     */     {
/* 493 */       String substr = name.substring(0, i);
/*     */ 
/* 496 */       CategoryKey key = new CategoryKey(substr);
/* 497 */       Object o = this.ht.get(key);
/*     */ 
/* 499 */       if (o == null)
/*     */       {
/* 501 */         ProvisionNode pn = new ProvisionNode(cat);
/* 502 */         this.ht.put(key, pn); } else {
/* 503 */         if ((o instanceof Category)) {
/* 504 */           parentFound = true;
/* 505 */           cat.parent = ((Category)o);
/*     */ 
/* 507 */           break;
/* 508 */         }if ((o instanceof ProvisionNode)) {
/* 509 */           ((ProvisionNode)o).addElement(cat);
/*     */         } else {
/* 511 */           Exception e = new IllegalStateException("unexpected object type " + o.getClass() + " in ht.");
/*     */ 
/* 513 */           e.printStackTrace();
/*     */         }
/*     */       }
/* 492 */       i = name.lastIndexOf('.', i - 1);
/*     */     }
/*     */ 
/* 517 */     if (!parentFound)
/* 518 */       cat.parent = this.root;
/*     */   }
/*     */ 
/*     */   private final void updateChildren(ProvisionNode pn, Logger logger)
/*     */   {
/* 540 */     int last = pn.size();
/*     */ 
/* 542 */     for (int i = 0; i < last; i++) {
/* 543 */       Logger l = (Logger)pn.elementAt(i);
/*     */ 
/* 548 */       if (!l.parent.name.startsWith(logger.name)) {
/* 549 */         logger.parent = l.parent;
/* 550 */         l.parent = logger;
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.Hierarchy
 * JD-Core Version:    0.6.0
 */
/*      */ package org.apache.log4j;
/*      */ 
/*      */ import java.text.MessageFormat;
/*      */ import java.util.Enumeration;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.ResourceBundle;
/*      */ import org.apache.log4j.helpers.AppenderAttachableImpl;
/*      */ import org.apache.log4j.helpers.NullEnumeration;
/*      */ import org.apache.log4j.spi.AppenderAttachable;
/*      */ import org.apache.log4j.spi.LoggerRepository;
/*      */ import org.apache.log4j.spi.LoggingEvent;
/*      */ 
/*      */ public class Category
/*      */   implements AppenderAttachable
/*      */ {
/*      */   protected String name;
/*      */   protected volatile Level level;
/*      */   protected volatile Category parent;
/*  115 */   private static final String FQCN = Category.class.getName();
/*      */   protected ResourceBundle resourceBundle;
/*      */   protected LoggerRepository repository;
/*      */   AppenderAttachableImpl aai;
/*  132 */   protected boolean additive = true;
/*      */ 
/*      */   protected Category(String name)
/*      */   {
/*  145 */     this.name = name;
/*      */   }
/*      */ 
/*      */   public synchronized void addAppender(Appender newAppender)
/*      */   {
/*  158 */     if (this.aai == null) {
/*  159 */       this.aai = new AppenderAttachableImpl();
/*      */     }
/*  161 */     this.aai.addAppender(newAppender);
/*  162 */     this.repository.fireAddAppenderEvent(this, newAppender);
/*      */   }
/*      */ 
/*      */   public void assertLog(boolean assertion, String msg)
/*      */   {
/*  180 */     if (!assertion)
/*  181 */       error(msg);
/*      */   }
/*      */ 
/*      */   public void callAppenders(LoggingEvent event)
/*      */   {
/*  197 */     int writes = 0;
/*      */ 
/*  199 */     for (Category c = this; c != null; c = c.parent)
/*      */     {
/*  201 */       synchronized (c) {
/*  202 */         if (c.aai != null) {
/*  203 */           writes += c.aai.appendLoopOnAppenders(event);
/*      */         }
/*  205 */         if (!c.additive) {
/*  206 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  211 */     if (writes == 0)
/*  212 */       this.repository.emitNoAppenderWarning(this);
/*      */   }
/*      */ 
/*      */   synchronized void closeNestedAppenders()
/*      */   {
/*  223 */     Enumeration enumeration = getAllAppenders();
/*  224 */     if (enumeration != null)
/*  225 */       while (enumeration.hasMoreElements()) {
/*  226 */         Appender a = (Appender)enumeration.nextElement();
/*  227 */         if ((a instanceof AppenderAttachable))
/*  228 */           a.close();
/*      */       }
/*      */   }
/*      */ 
/*      */   public void debug(Object message)
/*      */   {
/*  254 */     if (this.repository.isDisabled(10000))
/*  255 */       return;
/*  256 */     if (Level.DEBUG.isGreaterOrEqual(getEffectiveLevel()))
/*  257 */       forcedLog(FQCN, Level.DEBUG, message, null);
/*      */   }
/*      */ 
/*      */   public void debug(Object message, Throwable t)
/*      */   {
/*  273 */     if (this.repository.isDisabled(10000))
/*  274 */       return;
/*  275 */     if (Level.DEBUG.isGreaterOrEqual(getEffectiveLevel()))
/*  276 */       forcedLog(FQCN, Level.DEBUG, message, t);
/*      */   }
/*      */ 
/*      */   public void error(Object message)
/*      */   {
/*  299 */     if (this.repository.isDisabled(40000))
/*  300 */       return;
/*  301 */     if (Level.ERROR.isGreaterOrEqual(getEffectiveLevel()))
/*  302 */       forcedLog(FQCN, Level.ERROR, message, null);
/*      */   }
/*      */ 
/*      */   public void error(Object message, Throwable t)
/*      */   {
/*  316 */     if (this.repository.isDisabled(40000))
/*  317 */       return;
/*  318 */     if (Level.ERROR.isGreaterOrEqual(getEffectiveLevel()))
/*  319 */       forcedLog(FQCN, Level.ERROR, message, t);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public static Logger exists(String name)
/*      */   {
/*  335 */     return LogManager.exists(name);
/*      */   }
/*      */ 
/*      */   public void fatal(Object message)
/*      */   {
/*  359 */     if (this.repository.isDisabled(50000))
/*  360 */       return;
/*  361 */     if (Level.FATAL.isGreaterOrEqual(getEffectiveLevel()))
/*  362 */       forcedLog(FQCN, Level.FATAL, message, null);
/*      */   }
/*      */ 
/*      */   public void fatal(Object message, Throwable t)
/*      */   {
/*  376 */     if (this.repository.isDisabled(50000))
/*  377 */       return;
/*  378 */     if (Level.FATAL.isGreaterOrEqual(getEffectiveLevel()))
/*  379 */       forcedLog(FQCN, Level.FATAL, message, t);
/*      */   }
/*      */ 
/*      */   protected void forcedLog(String fqcn, Priority level, Object message, Throwable t)
/*      */   {
/*  388 */     callAppenders(new LoggingEvent(fqcn, this, level, message, t));
/*      */   }
/*      */ 
/*      */   public boolean getAdditivity()
/*      */   {
/*  397 */     return this.additive;
/*      */   }
/*      */ 
/*      */   public synchronized Enumeration getAllAppenders()
/*      */   {
/*  409 */     if (this.aai == null) {
/*  410 */       return NullEnumeration.getInstance();
/*      */     }
/*  412 */     return this.aai.getAllAppenders();
/*      */   }
/*      */ 
/*      */   public synchronized Appender getAppender(String name)
/*      */   {
/*  423 */     if ((this.aai == null) || (name == null)) {
/*  424 */       return null;
/*      */     }
/*  426 */     return this.aai.getAppender(name);
/*      */   }
/*      */ 
/*      */   public Level getEffectiveLevel()
/*      */   {
/*  439 */     for (Category c = this; c != null; c = c.parent) {
/*  440 */       if (c.level != null)
/*  441 */         return c.level;
/*      */     }
/*  443 */     return null;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Priority getChainedPriority()
/*      */   {
/*  453 */     for (Category c = this; c != null; c = c.parent) {
/*  454 */       if (c.level != null)
/*  455 */         return c.level;
/*      */     }
/*  457 */     return null;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public static Enumeration getCurrentCategories()
/*      */   {
/*  473 */     return LogManager.getCurrentLoggers();
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public static LoggerRepository getDefaultHierarchy()
/*      */   {
/*  487 */     return LogManager.getLoggerRepository();
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public LoggerRepository getHierarchy()
/*      */   {
/*  499 */     return this.repository;
/*      */   }
/*      */ 
/*      */   public LoggerRepository getLoggerRepository()
/*      */   {
/*  509 */     return this.repository;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public static Category getInstance(String name)
/*      */   {
/*  519 */     return LogManager.getLogger(name);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public static Category getInstance(Class clazz)
/*      */   {
/*  528 */     return LogManager.getLogger(clazz);
/*      */   }
/*      */ 
/*      */   public final String getName()
/*      */   {
/*  537 */     return this.name;
/*      */   }
/*      */ 
/*      */   public final Category getParent()
/*      */   {
/*  552 */     return this.parent;
/*      */   }
/*      */ 
/*      */   public final Level getLevel()
/*      */   {
/*  564 */     return this.level;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public final Level getPriority()
/*      */   {
/*  573 */     return this.level;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public static final Category getRoot()
/*      */   {
/*  584 */     return LogManager.getRootLogger();
/*      */   }
/*      */ 
/*      */   public ResourceBundle getResourceBundle()
/*      */   {
/*  600 */     for (Category c = this; c != null; c = c.parent) {
/*  601 */       if (c.resourceBundle != null) {
/*  602 */         return c.resourceBundle;
/*      */       }
/*      */     }
/*  605 */     return null;
/*      */   }
/*      */ 
/*      */   protected String getResourceBundleString(String key)
/*      */   {
/*  618 */     ResourceBundle rb = getResourceBundle();
/*      */ 
/*  621 */     if (rb == null)
/*      */     {
/*  626 */       return null;
/*      */     }
/*      */     try
/*      */     {
/*  630 */       return rb.getString(key);
/*      */     }
/*      */     catch (MissingResourceException mre) {
/*  633 */       error("No resource is associated with key \"" + key + "\".");
/*  634 */     }return null;
/*      */   }
/*      */ 
/*      */   public void info(Object message)
/*      */   {
/*  660 */     if (this.repository.isDisabled(20000))
/*  661 */       return;
/*  662 */     if (Level.INFO.isGreaterOrEqual(getEffectiveLevel()))
/*  663 */       forcedLog(FQCN, Level.INFO, message, null);
/*      */   }
/*      */ 
/*      */   public void info(Object message, Throwable t)
/*      */   {
/*  677 */     if (this.repository.isDisabled(20000))
/*  678 */       return;
/*  679 */     if (Level.INFO.isGreaterOrEqual(getEffectiveLevel()))
/*  680 */       forcedLog(FQCN, Level.INFO, message, t);
/*      */   }
/*      */ 
/*      */   public boolean isAttached(Appender appender)
/*      */   {
/*  688 */     if ((appender == null) || (this.aai == null)) {
/*  689 */       return false;
/*      */     }
/*  691 */     return this.aai.isAttached(appender);
/*      */   }
/*      */ 
/*      */   public boolean isDebugEnabled()
/*      */   {
/*  731 */     if (this.repository.isDisabled(10000))
/*  732 */       return false;
/*  733 */     return Level.DEBUG.isGreaterOrEqual(getEffectiveLevel());
/*      */   }
/*      */ 
/*      */   public boolean isEnabledFor(Priority level)
/*      */   {
/*  746 */     if (this.repository.isDisabled(level.level))
/*  747 */       return false;
/*  748 */     return level.isGreaterOrEqual(getEffectiveLevel());
/*      */   }
/*      */ 
/*      */   public boolean isInfoEnabled()
/*      */   {
/*  760 */     if (this.repository.isDisabled(20000))
/*  761 */       return false;
/*  762 */     return Level.INFO.isGreaterOrEqual(getEffectiveLevel());
/*      */   }
/*      */ 
/*      */   public void l7dlog(Priority priority, String key, Throwable t)
/*      */   {
/*  776 */     if (this.repository.isDisabled(priority.level)) {
/*  777 */       return;
/*      */     }
/*  779 */     if (priority.isGreaterOrEqual(getEffectiveLevel())) {
/*  780 */       String msg = getResourceBundleString(key);
/*      */ 
/*  783 */       if (msg == null) {
/*  784 */         msg = key;
/*      */       }
/*  786 */       forcedLog(FQCN, priority, msg, t);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void l7dlog(Priority priority, String key, Object[] params, Throwable t)
/*      */   {
/*  800 */     if (this.repository.isDisabled(priority.level)) {
/*  801 */       return;
/*      */     }
/*  803 */     if (priority.isGreaterOrEqual(getEffectiveLevel())) {
/*  804 */       String pattern = getResourceBundleString(key);
/*      */       String msg;
/*  806 */       if (pattern == null)
/*  807 */         msg = key;
/*      */       else
/*  809 */         msg = MessageFormat.format(pattern, params);
/*  810 */       forcedLog(FQCN, priority, msg, t);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void log(Priority priority, Object message, Throwable t)
/*      */   {
/*  819 */     if (this.repository.isDisabled(priority.level)) {
/*  820 */       return;
/*      */     }
/*  822 */     if (priority.isGreaterOrEqual(getEffectiveLevel()))
/*  823 */       forcedLog(FQCN, priority, message, t);
/*      */   }
/*      */ 
/*      */   public void log(Priority priority, Object message)
/*      */   {
/*  831 */     if (this.repository.isDisabled(priority.level)) {
/*  832 */       return;
/*      */     }
/*  834 */     if (priority.isGreaterOrEqual(getEffectiveLevel()))
/*  835 */       forcedLog(FQCN, priority, message, null);
/*      */   }
/*      */ 
/*      */   public void log(String callerFQCN, Priority level, Object message, Throwable t)
/*      */   {
/*  849 */     if (this.repository.isDisabled(level.level)) {
/*  850 */       return;
/*      */     }
/*  852 */     if (level.isGreaterOrEqual(getEffectiveLevel()))
/*  853 */       forcedLog(callerFQCN, level, message, t);
/*      */   }
/*      */ 
/*      */   public synchronized void removeAllAppenders()
/*      */   {
/*  867 */     if (this.aai != null) {
/*  868 */       this.aai.removeAllAppenders();
/*  869 */       this.aai = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public synchronized void removeAppender(Appender appender)
/*      */   {
/*  881 */     if ((appender == null) || (this.aai == null))
/*  882 */       return;
/*  883 */     this.aai.removeAppender(appender);
/*      */   }
/*      */ 
/*      */   public synchronized void removeAppender(String name)
/*      */   {
/*  894 */     if ((name == null) || (this.aai == null)) return;
/*  895 */     this.aai.removeAppender(name);
/*      */   }
/*      */ 
/*      */   public void setAdditivity(boolean additive)
/*      */   {
/*  904 */     this.additive = additive;
/*      */   }
/*      */ 
/*      */   final void setHierarchy(LoggerRepository repository)
/*      */   {
/*  912 */     this.repository = repository;
/*      */   }
/*      */ 
/*      */   public void setLevel(Level level)
/*      */   {
/*  928 */     this.level = level;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void setPriority(Priority priority)
/*      */   {
/*  941 */     this.level = ((Level)priority);
/*      */   }
/*      */ 
/*      */   public void setResourceBundle(ResourceBundle bundle)
/*      */   {
/*  954 */     this.resourceBundle = bundle;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public static void shutdown()
/*      */   {
/*  979 */     LogManager.shutdown();
/*      */   }
/*      */ 
/*      */   public void warn(Object message)
/*      */   {
/* 1004 */     if (this.repository.isDisabled(30000)) {
/* 1005 */       return;
/*      */     }
/* 1007 */     if (Level.WARN.isGreaterOrEqual(getEffectiveLevel()))
/* 1008 */       forcedLog(FQCN, Level.WARN, message, null);
/*      */   }
/*      */ 
/*      */   public void warn(Object message, Throwable t)
/*      */   {
/* 1022 */     if (this.repository.isDisabled(30000))
/* 1023 */       return;
/* 1024 */     if (Level.WARN.isGreaterOrEqual(getEffectiveLevel()))
/* 1025 */       forcedLog(FQCN, Level.WARN, message, t);
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.Category
 * JD-Core Version:    0.6.0
 */
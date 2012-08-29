/*     */ package org.apache.log4j;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.config.PropertySetter;
/*     */ import org.apache.log4j.helpers.FileWatchdog;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ import org.apache.log4j.helpers.OptionConverter;
/*     */ import org.apache.log4j.or.RendererMap;
/*     */ import org.apache.log4j.spi.Configurator;
/*     */ import org.apache.log4j.spi.LoggerFactory;
/*     */ import org.apache.log4j.spi.LoggerRepository;
/*     */ import org.apache.log4j.spi.OptionHandler;
/*     */ import org.apache.log4j.spi.RendererSupport;
/*     */ 
/*     */ public class PropertyConfigurator
/*     */   implements Configurator
/*     */ {
/*  90 */   protected Hashtable registry = new Hashtable(11);
/*  91 */   protected LoggerFactory loggerFactory = new DefaultCategoryFactory();
/*     */   static final String CATEGORY_PREFIX = "log4j.category.";
/*     */   static final String LOGGER_PREFIX = "log4j.logger.";
/*     */   static final String FACTORY_PREFIX = "log4j.factory";
/*     */   static final String ADDITIVITY_PREFIX = "log4j.additivity.";
/*     */   static final String ROOT_CATEGORY_PREFIX = "log4j.rootCategory";
/*     */   static final String ROOT_LOGGER_PREFIX = "log4j.rootLogger";
/*     */   static final String APPENDER_PREFIX = "log4j.appender.";
/*     */   static final String RENDERER_PREFIX = "log4j.renderer.";
/*     */   static final String THRESHOLD_PREFIX = "log4j.threshold";
/*     */   public static final String LOGGER_FACTORY_KEY = "log4j.loggerFactory";
/*     */   private static final String INTERNAL_ROOT_NAME = "root";
/*     */ 
/*     */   public void doConfigure(String configFileName, LoggerRepository hierarchy)
/*     */   {
/* 304 */     Properties props = new Properties();
/*     */     try {
/* 306 */       FileInputStream istream = new FileInputStream(configFileName);
/* 307 */       props.load(istream);
/* 308 */       istream.close();
/*     */     }
/*     */     catch (IOException e) {
/* 311 */       LogLog.error("Could not read configuration file [" + configFileName + "].", e);
/* 312 */       LogLog.error("Ignoring configuration file [" + configFileName + "].");
/* 313 */       return;
/*     */     }
/*     */ 
/* 316 */     doConfigure(props, hierarchy);
/*     */   }
/*     */ 
/*     */   public static void configure(String configFilename)
/*     */   {
/* 324 */     new PropertyConfigurator().doConfigure(configFilename, LogManager.getLoggerRepository());
/*     */   }
/*     */ 
/*     */   public static void configure(URL configURL)
/*     */   {
/* 336 */     new PropertyConfigurator().doConfigure(configURL, LogManager.getLoggerRepository());
/*     */   }
/*     */ 
/*     */   public static void configure(Properties properties)
/*     */   {
/* 349 */     new PropertyConfigurator().doConfigure(properties, LogManager.getLoggerRepository());
/*     */   }
/*     */ 
/*     */   public static void configureAndWatch(String configFilename)
/*     */   {
/* 364 */     configureAndWatch(configFilename, 60000L);
/*     */   }
/*     */ 
/*     */   public static void configureAndWatch(String configFilename, long delay)
/*     */   {
/* 382 */     PropertyWatchdog pdog = new PropertyWatchdog(configFilename);
/* 383 */     pdog.setDelay(delay);
/* 384 */     pdog.start();
/*     */   }
/*     */ 
/*     */   public void doConfigure(Properties properties, LoggerRepository hierarchy)
/*     */   {
/* 396 */     String value = properties.getProperty("log4j.debug");
/* 397 */     if (value == null) {
/* 398 */       value = properties.getProperty("log4j.configDebug");
/* 399 */       if (value != null) {
/* 400 */         LogLog.warn("[log4j.configDebug] is deprecated. Use [log4j.debug] instead.");
/*     */       }
/*     */     }
/* 403 */     if (value != null) {
/* 404 */       LogLog.setInternalDebugging(OptionConverter.toBoolean(value, true));
/*     */     }
/*     */ 
/* 407 */     String thresholdStr = OptionConverter.findAndSubst("log4j.threshold", properties);
/*     */ 
/* 409 */     if (thresholdStr != null) {
/* 410 */       hierarchy.setThreshold(OptionConverter.toLevel(thresholdStr, Level.ALL));
/*     */ 
/* 412 */       LogLog.debug("Hierarchy threshold set to [" + hierarchy.getThreshold() + "].");
/*     */     }
/*     */ 
/* 415 */     configureRootCategory(properties, hierarchy);
/* 416 */     configureLoggerFactory(properties);
/* 417 */     parseCatsAndRenderers(properties, hierarchy);
/*     */ 
/* 419 */     LogLog.debug("Finished configuring.");
/*     */ 
/* 422 */     this.registry.clear();
/*     */   }
/*     */ 
/*     */   public void doConfigure(URL configURL, LoggerRepository hierarchy)
/*     */   {
/* 430 */     Properties props = new Properties();
/* 431 */     LogLog.debug("Reading configuration from URL " + configURL);
/*     */     try {
/* 433 */       props.load(configURL.openStream());
/*     */     }
/*     */     catch (IOException e) {
/* 436 */       LogLog.error("Could not read configuration file from URL [" + configURL + "].", e);
/*     */ 
/* 438 */       LogLog.error("Ignoring configuration file [" + configURL + "].");
/* 439 */       return;
/*     */     }
/* 441 */     doConfigure(props, hierarchy);
/*     */   }
/*     */ 
/*     */   protected void configureLoggerFactory(Properties props)
/*     */   {
/* 460 */     String factoryClassName = OptionConverter.findAndSubst("log4j.loggerFactory", props);
/*     */ 
/* 462 */     if (factoryClassName != null) {
/* 463 */       LogLog.debug("Setting category factory to [" + factoryClassName + "].");
/* 464 */       this.loggerFactory = ((LoggerFactory)OptionConverter.instantiateByClassName(factoryClassName, LoggerFactory.class, this.loggerFactory));
/*     */ 
/* 468 */       PropertySetter.setProperties(this.loggerFactory, props, "log4j.factory.");
/*     */     }
/*     */   }
/*     */ 
/*     */   void configureRootCategory(Properties props, LoggerRepository hierarchy)
/*     */   {
/* 496 */     String effectiveFrefix = "log4j.rootLogger";
/* 497 */     String value = OptionConverter.findAndSubst("log4j.rootLogger", props);
/*     */ 
/* 499 */     if (value == null) {
/* 500 */       value = OptionConverter.findAndSubst("log4j.rootCategory", props);
/* 501 */       effectiveFrefix = "log4j.rootCategory";
/*     */     }
/*     */ 
/* 504 */     if (value == null) {
/* 505 */       LogLog.debug("Could not find root logger information. Is this OK?");
/*     */     } else {
/* 507 */       Logger root = hierarchy.getRootLogger();
/* 508 */       synchronized (root) {
/* 509 */         parseCategory(props, root, effectiveFrefix, "root", value);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void parseCatsAndRenderers(Properties props, LoggerRepository hierarchy)
/*     */   {
/* 520 */     Enumeration enumeration = props.propertyNames();
/* 521 */     while (enumeration.hasMoreElements()) {
/* 522 */       String key = (String)enumeration.nextElement();
/* 523 */       if ((key.startsWith("log4j.category.")) || (key.startsWith("log4j.logger."))) {
/* 524 */         String loggerName = null;
/* 525 */         if (key.startsWith("log4j.category."))
/* 526 */           loggerName = key.substring("log4j.category.".length());
/* 527 */         else if (key.startsWith("log4j.logger.")) {
/* 528 */           loggerName = key.substring("log4j.logger.".length());
/*     */         }
/* 530 */         String value = OptionConverter.findAndSubst(key, props);
/* 531 */         Logger logger = hierarchy.getLogger(loggerName, this.loggerFactory);
/* 532 */         synchronized (logger) {
/* 533 */           parseCategory(props, logger, key, loggerName, value);
/* 534 */           parseAdditivityForLogger(props, logger, loggerName);
/*     */         }
/* 536 */       } else if (key.startsWith("log4j.renderer.")) {
/* 537 */         String renderedClass = key.substring("log4j.renderer.".length());
/* 538 */         String renderingClass = OptionConverter.findAndSubst(key, props);
/* 539 */         if ((hierarchy instanceof RendererSupport))
/* 540 */           RendererMap.addRenderer((RendererSupport)hierarchy, renderedClass, renderingClass);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   void parseAdditivityForLogger(Properties props, Logger cat, String loggerName)
/*     */   {
/* 552 */     String value = OptionConverter.findAndSubst("log4j.additivity." + loggerName, props);
/*     */ 
/* 554 */     LogLog.debug("Handling log4j.additivity." + loggerName + "=[" + value + "]");
/*     */ 
/* 556 */     if ((value != null) && (!value.equals(""))) {
/* 557 */       boolean additivity = OptionConverter.toBoolean(value, true);
/* 558 */       LogLog.debug("Setting additivity for \"" + loggerName + "\" to " + additivity);
/*     */ 
/* 560 */       cat.setAdditivity(additivity);
/*     */     }
/*     */   }
/*     */ 
/*     */   void parseCategory(Properties props, Logger logger, String optionKey, String loggerName, String value)
/*     */   {
/* 570 */     LogLog.debug("Parsing for [" + loggerName + "] with value=[" + value + "].");
/*     */ 
/* 572 */     StringTokenizer st = new StringTokenizer(value, ",");
/*     */ 
/* 577 */     if ((!value.startsWith(",")) && (!value.equals("")))
/*     */     {
/* 580 */       if (!st.hasMoreTokens()) {
/* 581 */         return;
/*     */       }
/* 583 */       String levelStr = st.nextToken();
/* 584 */       LogLog.debug("Level token is [" + levelStr + "].");
/*     */ 
/* 589 */       if (("inherited".equalsIgnoreCase(levelStr)) || ("null".equalsIgnoreCase(levelStr)))
/*     */       {
/* 591 */         if (loggerName.equals("root"))
/* 592 */           LogLog.warn("The root logger cannot be set to null.");
/*     */         else
/* 594 */           logger.setLevel(null);
/*     */       }
/*     */       else {
/* 597 */         logger.setLevel(OptionConverter.toLevel(levelStr, Level.DEBUG));
/*     */       }
/* 599 */       LogLog.debug("Category " + loggerName + " set to " + logger.getLevel());
/*     */     }
/*     */ 
/* 603 */     logger.removeAllAppenders();
/*     */ 
/* 607 */     while (st.hasMoreTokens()) {
/* 608 */       String appenderName = st.nextToken().trim();
/* 609 */       if ((appenderName == null) || (appenderName.equals(",")))
/*     */         continue;
/* 611 */       LogLog.debug("Parsing appender named \"" + appenderName + "\".");
/* 612 */       Appender appender = parseAppender(props, appenderName);
/* 613 */       if (appender != null)
/* 614 */         logger.addAppender(appender);
/*     */     }
/*     */   }
/*     */ 
/*     */   Appender parseAppender(Properties props, String appenderName)
/*     */   {
/* 620 */     Appender appender = registryGet(appenderName);
/* 621 */     if (appender != null) {
/* 622 */       LogLog.debug("Appender \"" + appenderName + "\" was already parsed.");
/* 623 */       return appender;
/*     */     }
/*     */ 
/* 626 */     String prefix = "log4j.appender." + appenderName;
/* 627 */     String layoutPrefix = prefix + ".layout";
/*     */ 
/* 629 */     appender = (Appender)OptionConverter.instantiateByKey(props, prefix, Appender.class, null);
/*     */ 
/* 632 */     if (appender == null) {
/* 633 */       LogLog.error("Could not instantiate appender named \"" + appenderName + "\".");
/*     */ 
/* 635 */       return null;
/*     */     }
/* 637 */     appender.setName(appenderName);
/*     */ 
/* 639 */     if ((appender instanceof OptionHandler)) {
/* 640 */       if (appender.requiresLayout()) {
/* 641 */         Layout layout = (Layout)OptionConverter.instantiateByKey(props, layoutPrefix, Layout.class, null);
/*     */ 
/* 645 */         if (layout != null) {
/* 646 */           appender.setLayout(layout);
/* 647 */           LogLog.debug("Parsing layout options for \"" + appenderName + "\".");
/*     */ 
/* 649 */           PropertySetter.setProperties(layout, props, layoutPrefix + ".");
/* 650 */           LogLog.debug("End of parsing for \"" + appenderName + "\".");
/*     */         }
/*     */       }
/*     */ 
/* 654 */       PropertySetter.setProperties(appender, props, prefix + ".");
/* 655 */       LogLog.debug("Parsed \"" + appenderName + "\" options.");
/*     */     }
/* 657 */     registryPut(appender);
/* 658 */     return appender;
/*     */   }
/*     */ 
/*     */   void registryPut(Appender appender)
/*     */   {
/* 663 */     this.registry.put(appender.getName(), appender);
/*     */   }
/*     */ 
/*     */   Appender registryGet(String name) {
/* 667 */     return (Appender)this.registry.get(name);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.PropertyConfigurator
 * JD-Core Version:    0.6.0
 */
/*     */ package org.apache.log4j;
/*     */ 
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.Enumeration;
/*     */ import org.apache.log4j.helpers.Loader;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ import org.apache.log4j.helpers.OptionConverter;
/*     */ import org.apache.log4j.spi.DefaultRepositorySelector;
/*     */ import org.apache.log4j.spi.LoggerFactory;
/*     */ import org.apache.log4j.spi.LoggerRepository;
/*     */ import org.apache.log4j.spi.RepositorySelector;
/*     */ import org.apache.log4j.spi.RootLogger;
/*     */ 
/*     */ public class LogManager
/*     */ {
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final String DEFAULT_CONFIGURATION_FILE = "log4j.properties";
/*     */   static final String DEFAULT_XML_CONFIGURATION_FILE = "log4j.xml";
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final String DEFAULT_CONFIGURATION_KEY = "log4j.configuration";
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final String CONFIGURATOR_CLASS_KEY = "log4j.configuratorClass";
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final String DEFAULT_INIT_OVERRIDE_KEY = "log4j.defaultInitOverride";
/*  73 */   private static Object guard = null;
/*     */   private static RepositorySelector repositorySelector;
/*     */ 
/*     */   public static void setRepositorySelector(RepositorySelector selector, Object guard)
/*     */     throws IllegalArgumentException
/*     */   {
/* 152 */     if ((guard != null) && (guard != guard)) {
/* 153 */       throw new IllegalArgumentException("Attempted to reset the LoggerFactory without possessing the guard.");
/*     */     }
/*     */ 
/* 157 */     if (selector == null) {
/* 158 */       throw new IllegalArgumentException("RepositorySelector must be non-null.");
/*     */     }
/*     */ 
/* 161 */     guard = guard;
/* 162 */     repositorySelector = selector;
/*     */   }
/*     */ 
/*     */   public static LoggerRepository getLoggerRepository()
/*     */   {
/* 168 */     return repositorySelector.getLoggerRepository();
/*     */   }
/*     */ 
/*     */   public static Logger getRootLogger()
/*     */   {
/* 178 */     return repositorySelector.getLoggerRepository().getRootLogger();
/*     */   }
/*     */ 
/*     */   public static Logger getLogger(String name)
/*     */   {
/* 188 */     return repositorySelector.getLoggerRepository().getLogger(name);
/*     */   }
/*     */ 
/*     */   public static Logger getLogger(Class clazz)
/*     */   {
/* 198 */     return repositorySelector.getLoggerRepository().getLogger(clazz.getName());
/*     */   }
/*     */ 
/*     */   public static Logger getLogger(String name, LoggerFactory factory)
/*     */   {
/* 209 */     return repositorySelector.getLoggerRepository().getLogger(name, factory);
/*     */   }
/*     */ 
/*     */   public static Logger exists(String name)
/*     */   {
/* 215 */     return repositorySelector.getLoggerRepository().exists(name);
/*     */   }
/*     */ 
/*     */   public static Enumeration getCurrentLoggers()
/*     */   {
/* 221 */     return repositorySelector.getLoggerRepository().getCurrentLoggers();
/*     */   }
/*     */ 
/*     */   public static void shutdown()
/*     */   {
/* 227 */     repositorySelector.getLoggerRepository().shutdown();
/*     */   }
/*     */ 
/*     */   public static void resetConfiguration()
/*     */   {
/* 233 */     repositorySelector.getLoggerRepository().resetConfiguration();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  78 */     Hierarchy h = new Hierarchy(new RootLogger(Level.DEBUG));
/*  79 */     repositorySelector = new DefaultRepositorySelector(h);
/*     */ 
/*  82 */     String override = OptionConverter.getSystemProperty("log4j.defaultInitOverride", null);
/*     */ 
/*  87 */     if ((override == null) || ("false".equalsIgnoreCase(override)))
/*     */     {
/*  89 */       String configurationOptionStr = OptionConverter.getSystemProperty("log4j.configuration", null);
/*     */ 
/*  93 */       String configuratorClassName = OptionConverter.getSystemProperty("log4j.configuratorClass", null);
/*     */ 
/*  97 */       URL url = null;
/*     */ 
/* 102 */       if (configurationOptionStr == null) {
/* 103 */         url = Loader.getResource("log4j.xml");
/* 104 */         if (url == null)
/* 105 */           url = Loader.getResource("log4j.properties");
/*     */       }
/*     */       else {
/*     */         try {
/* 109 */           url = new URL(configurationOptionStr);
/*     */         }
/*     */         catch (MalformedURLException ex)
/*     */         {
/* 113 */           url = Loader.getResource(configurationOptionStr);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 120 */       if (url != null) {
/* 121 */         LogLog.debug("Using URL [" + url + "] for automatic log4j configuration.");
/* 122 */         OptionConverter.selectAndConfigure(url, configuratorClassName, getLoggerRepository());
/*     */       }
/*     */       else {
/* 125 */         LogLog.debug("Could not find resource: [" + configurationOptionStr + "].");
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.LogManager
 * JD-Core Version:    0.6.0
 */
/*     */ package org.apache.log4j.xml;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.FactoryConfigurationError;
/*     */ import org.apache.log4j.Appender;
/*     */ import org.apache.log4j.Category;
/*     */ import org.apache.log4j.Layout;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.LogManager;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.config.PropertySetter;
/*     */ import org.apache.log4j.helpers.FileWatchdog;
/*     */ import org.apache.log4j.helpers.Loader;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ import org.apache.log4j.helpers.OptionConverter;
/*     */ import org.apache.log4j.or.RendererMap;
/*     */ import org.apache.log4j.spi.AppenderAttachable;
/*     */ import org.apache.log4j.spi.Configurator;
/*     */ import org.apache.log4j.spi.ErrorHandler;
/*     */ import org.apache.log4j.spi.Filter;
/*     */ import org.apache.log4j.spi.LoggerFactory;
/*     */ import org.apache.log4j.spi.LoggerRepository;
/*     */ import org.apache.log4j.spi.RendererSupport;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class DOMConfigurator
/*     */   implements Configurator
/*     */ {
/*     */   static final String CONFIGURATION_TAG = "log4j:configuration";
/*     */   static final String OLD_CONFIGURATION_TAG = "configuration";
/*     */   static final String RENDERER_TAG = "renderer";
/*     */   static final String APPENDER_TAG = "appender";
/*     */   static final String APPENDER_REF_TAG = "appender-ref";
/*     */   static final String PARAM_TAG = "param";
/*     */   static final String LAYOUT_TAG = "layout";
/*     */   static final String CATEGORY = "category";
/*     */   static final String LOGGER = "logger";
/*     */   static final String LOGGER_REF = "logger-ref";
/*     */   static final String CATEGORY_FACTORY_TAG = "categoryFactory";
/*     */   static final String NAME_ATTR = "name";
/*     */   static final String CLASS_ATTR = "class";
/*     */   static final String VALUE_ATTR = "value";
/*     */   static final String ROOT_TAG = "root";
/*     */   static final String ROOT_REF = "root-ref";
/*     */   static final String LEVEL_TAG = "level";
/*     */   static final String PRIORITY_TAG = "priority";
/*     */   static final String FILTER_TAG = "filter";
/*     */   static final String ERROR_HANDLER_TAG = "errorHandler";
/*     */   static final String REF_ATTR = "ref";
/*     */   static final String ADDITIVITY_ATTR = "additivity";
/*     */   static final String THRESHOLD_ATTR = "threshold";
/*     */   static final String CONFIG_DEBUG_ATTR = "configDebug";
/*     */   static final String INTERNAL_DEBUG_ATTR = "debug";
/*     */   static final String RENDERING_CLASS_ATTR = "renderingClass";
/*     */   static final String RENDERED_CLASS_ATTR = "renderedClass";
/*     */   static final String EMPTY_STR = "";
/* 100 */   static final Class[] ONE_STRING_PARAM = { String.class };
/*     */   static final String dbfKey = "javax.xml.parsers.DocumentBuilderFactory";
/*     */   Hashtable appenderBag;
/*     */   Properties props;
/*     */   LoggerRepository repository;
/*     */ 
/*     */   public DOMConfigurator()
/*     */   {
/* 116 */     this.appenderBag = new Hashtable();
/*     */   }
/*     */ 
/*     */   protected Appender findAppenderByName(Document doc, String appenderName)
/*     */   {
/* 124 */     Appender appender = (Appender)this.appenderBag.get(appenderName);
/*     */ 
/* 126 */     if (appender != null) {
/* 127 */       return appender;
/*     */     }
/*     */ 
/* 133 */     Element element = null;
/* 134 */     NodeList list = doc.getElementsByTagName("appender");
/* 135 */     for (int t = 0; t < list.getLength(); t++) {
/* 136 */       Node node = list.item(t);
/* 137 */       NamedNodeMap map = node.getAttributes();
/* 138 */       Node attrNode = map.getNamedItem("name");
/* 139 */       if (appenderName.equals(attrNode.getNodeValue())) {
/* 140 */         element = (Element)node;
/* 141 */         break;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 146 */     if (element == null) {
/* 147 */       LogLog.error("No appender named [" + appenderName + "] could be found.");
/* 148 */       return null;
/*     */     }
/* 150 */     appender = parseAppender(element);
/* 151 */     this.appenderBag.put(appenderName, appender);
/* 152 */     return appender;
/*     */   }
/*     */ 
/*     */   protected Appender findAppenderByReference(Element appenderRef)
/*     */   {
/* 161 */     String appenderName = subst(appenderRef.getAttribute("ref"));
/* 162 */     Document doc = appenderRef.getOwnerDocument();
/* 163 */     return findAppenderByName(doc, appenderName);
/*     */   }
/*     */ 
/*     */   protected Appender parseAppender(Element appenderElement)
/*     */   {
/* 171 */     String className = subst(appenderElement.getAttribute("class"));
/* 172 */     LogLog.debug("Class name: [" + className + ']');
/*     */     try {
/* 174 */       Object instance = Loader.loadClass(className).newInstance();
/* 175 */       Appender appender = (Appender)instance;
/* 176 */       PropertySetter propSetter = new PropertySetter(appender);
/*     */ 
/* 178 */       appender.setName(subst(appenderElement.getAttribute("name")));
/*     */ 
/* 180 */       NodeList children = appenderElement.getChildNodes();
/* 181 */       int length = children.getLength();
/*     */ 
/* 183 */       for (int loop = 0; loop < length; loop++) {
/* 184 */         Node currentNode = children.item(loop);
/*     */ 
/* 187 */         if (currentNode.getNodeType() == 1) {
/* 188 */           Element currentElement = (Element)currentNode;
/*     */ 
/* 191 */           if (currentElement.getTagName().equals("param")) {
/* 192 */             setParameter(currentElement, propSetter);
/*     */           }
/* 195 */           else if (currentElement.getTagName().equals("layout")) {
/* 196 */             appender.setLayout(parseLayout(currentElement));
/*     */           }
/* 199 */           else if (currentElement.getTagName().equals("filter")) {
/* 200 */             parseFilters(currentElement, appender);
/*     */           }
/* 202 */           else if (currentElement.getTagName().equals("errorHandler")) {
/* 203 */             parseErrorHandler(currentElement, appender);
/*     */           }
/* 205 */           else if (currentElement.getTagName().equals("appender-ref")) {
/* 206 */             String refName = subst(currentElement.getAttribute("ref"));
/* 207 */             if ((appender instanceof AppenderAttachable)) {
/* 208 */               AppenderAttachable aa = (AppenderAttachable)appender;
/* 209 */               LogLog.debug("Attaching appender named [" + refName + "] to appender named [" + appender.getName() + "].");
/*     */ 
/* 211 */               aa.addAppender(findAppenderByReference(currentElement));
/*     */             } else {
/* 213 */               LogLog.error("Requesting attachment of appender named [" + refName + "] to appender named [" + appender.getName() + "] which does not implement org.apache.log4j.spi.AppenderAttachable.");
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 220 */       propSetter.activate();
/* 221 */       return appender;
/*     */     }
/*     */     catch (Exception oops)
/*     */     {
/* 226 */       LogLog.error("Could not create an Appender. Reported error follows.", oops);
/*     */     }
/* 228 */     return null;
/*     */   }
/*     */ 
/*     */   protected void parseErrorHandler(Element element, Appender appender)
/*     */   {
/* 237 */     ErrorHandler eh = (ErrorHandler)OptionConverter.instantiateByClassName(subst(element.getAttribute("class")), ErrorHandler.class, null);
/*     */ 
/* 242 */     if (eh != null) {
/* 243 */       eh.setAppender(appender);
/*     */ 
/* 245 */       PropertySetter propSetter = new PropertySetter(eh);
/* 246 */       NodeList children = element.getChildNodes();
/* 247 */       int length = children.getLength();
/*     */ 
/* 249 */       for (int loop = 0; loop < length; loop++) {
/* 250 */         Node currentNode = children.item(loop);
/* 251 */         if (currentNode.getNodeType() == 1) {
/* 252 */           Element currentElement = (Element)currentNode;
/* 253 */           String tagName = currentElement.getTagName();
/* 254 */           if (tagName.equals("param")) {
/* 255 */             setParameter(currentElement, propSetter);
/* 256 */           } else if (tagName.equals("appender-ref")) {
/* 257 */             eh.setBackupAppender(findAppenderByReference(currentElement));
/* 258 */           } else if (tagName.equals("logger-ref")) {
/* 259 */             String loggerName = currentElement.getAttribute("ref");
/* 260 */             Logger logger = this.repository.getLogger(loggerName);
/* 261 */             eh.setLogger(logger);
/* 262 */           } else if (tagName.equals("root-ref")) {
/* 263 */             Logger root = this.repository.getRootLogger();
/* 264 */             eh.setLogger(root);
/*     */           }
/*     */         }
/*     */       }
/* 268 */       propSetter.activate();
/* 269 */       appender.setErrorHandler(eh);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void parseFilters(Element element, Appender appender)
/*     */   {
/* 278 */     String clazz = subst(element.getAttribute("class"));
/* 279 */     Filter filter = (Filter)OptionConverter.instantiateByClassName(clazz, Filter.class, null);
/*     */ 
/* 282 */     if (filter != null) {
/* 283 */       PropertySetter propSetter = new PropertySetter(filter);
/* 284 */       NodeList children = element.getChildNodes();
/* 285 */       int length = children.getLength();
/*     */ 
/* 287 */       for (int loop = 0; loop < length; loop++) {
/* 288 */         Node currentNode = children.item(loop);
/* 289 */         if (currentNode.getNodeType() == 1) {
/* 290 */           Element currentElement = (Element)currentNode;
/* 291 */           String tagName = currentElement.getTagName();
/* 292 */           if (tagName.equals("param")) {
/* 293 */             setParameter(currentElement, propSetter);
/*     */           }
/*     */         }
/*     */       }
/* 297 */       propSetter.activate();
/* 298 */       LogLog.debug("Adding filter of type [" + filter.getClass() + "] to appender named [" + appender.getName() + "].");
/*     */ 
/* 300 */       appender.addFilter(filter);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void parseCategory(Element loggerElement)
/*     */   {
/* 310 */     String catName = subst(loggerElement.getAttribute("name"));
/*     */ 
/* 314 */     String className = subst(loggerElement.getAttribute("class"));
/*     */     Logger cat;
/* 317 */     if ("".equals(className)) {
/* 318 */       LogLog.debug("Retreiving an instance of org.apache.log4j.Logger.");
/* 319 */       cat = this.repository.getLogger(catName);
/*     */     }
/*     */     else {
/* 322 */       LogLog.debug("Desired logger sub-class: [" + className + ']');
/*     */       try {
/* 324 */         Class clazz = Loader.loadClass(className);
/* 325 */         Method getInstanceMethod = clazz.getMethod("getLogger", ONE_STRING_PARAM);
/*     */ 
/* 327 */         cat = (Logger)getInstanceMethod.invoke(null, new Object[] { catName });
/*     */       } catch (Exception clazz) {
/* 329 */         LogLog.error("Could not retrieve category [" + catName + "]. Reported error follows.", ???);
/*     */ 
/* 331 */         return;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 338 */     synchronized (cat) {
/* 339 */       boolean additivity = OptionConverter.toBoolean(subst(loggerElement.getAttribute("additivity")), true);
/*     */ 
/* 343 */       LogLog.debug("Setting [" + cat.getName() + "] additivity to [" + additivity + "].");
/* 344 */       cat.setAdditivity(additivity);
/* 345 */       parseChildrenOfLoggerElement(loggerElement, cat, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void parseCategoryFactory(Element factoryElement)
/*     */   {
/* 355 */     String className = subst(factoryElement.getAttribute("class"));
/*     */ 
/* 357 */     if ("".equals(className)) {
/* 358 */       LogLog.error("Category Factory tag class attribute not found.");
/* 359 */       LogLog.debug("No Category Factory configured.");
/*     */     }
/*     */     else {
/* 362 */       LogLog.debug("Desired category factory: [" + className + ']');
/* 363 */       Object catFactory = OptionConverter.instantiateByClassName(className, LoggerFactory.class, null);
/*     */ 
/* 366 */       PropertySetter propSetter = new PropertySetter(catFactory);
/*     */ 
/* 368 */       Element currentElement = null;
/* 369 */       Node currentNode = null;
/* 370 */       NodeList children = factoryElement.getChildNodes();
/* 371 */       int length = children.getLength();
/*     */ 
/* 373 */       for (int loop = 0; loop < length; loop++) {
/* 374 */         currentNode = children.item(loop);
/* 375 */         if (currentNode.getNodeType() == 1) {
/* 376 */           currentElement = (Element)currentNode;
/* 377 */           if (currentElement.getTagName().equals("param"))
/* 378 */             setParameter(currentElement, propSetter);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void parseRoot(Element rootElement)
/*     */   {
/* 391 */     Logger root = this.repository.getRootLogger();
/*     */ 
/* 393 */     synchronized (root) {
/* 394 */       parseChildrenOfLoggerElement(rootElement, root, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void parseChildrenOfLoggerElement(Element catElement, Logger cat, boolean isRoot)
/*     */   {
/* 406 */     PropertySetter propSetter = new PropertySetter(cat);
/*     */ 
/* 410 */     cat.removeAllAppenders();
/*     */ 
/* 413 */     NodeList children = catElement.getChildNodes();
/* 414 */     int length = children.getLength();
/*     */ 
/* 416 */     for (int loop = 0; loop < length; loop++) {
/* 417 */       Node currentNode = children.item(loop);
/*     */ 
/* 419 */       if (currentNode.getNodeType() == 1) {
/* 420 */         Element currentElement = (Element)currentNode;
/* 421 */         String tagName = currentElement.getTagName();
/*     */ 
/* 423 */         if (tagName.equals("appender-ref")) {
/* 424 */           Element appenderRef = (Element)currentNode;
/* 425 */           Appender appender = findAppenderByReference(appenderRef);
/* 426 */           String refName = subst(appenderRef.getAttribute("ref"));
/* 427 */           if (appender != null) {
/* 428 */             LogLog.debug("Adding appender named [" + refName + "] to category [" + cat.getName() + "].");
/*     */           }
/*     */           else {
/* 431 */             LogLog.debug("Appender named [" + refName + "] not found.");
/*     */           }
/* 433 */           cat.addAppender(appender);
/*     */         }
/* 435 */         else if (tagName.equals("level")) {
/* 436 */           parseLevel(currentElement, cat, isRoot);
/* 437 */         } else if (tagName.equals("priority")) {
/* 438 */           parseLevel(currentElement, cat, isRoot);
/* 439 */         } else if (tagName.equals("param")) {
/* 440 */           setParameter(currentElement, propSetter);
/*     */         }
/*     */       }
/*     */     }
/* 444 */     propSetter.activate();
/*     */   }
/*     */ 
/*     */   protected Layout parseLayout(Element layout_element)
/*     */   {
/* 452 */     String className = subst(layout_element.getAttribute("class"));
/* 453 */     LogLog.debug("Parsing layout of class: \"" + className + "\"");
/*     */     try {
/* 455 */       Object instance = Loader.loadClass(className).newInstance();
/* 456 */       Layout layout = (Layout)instance;
/* 457 */       PropertySetter propSetter = new PropertySetter(layout);
/*     */ 
/* 459 */       NodeList params = layout_element.getChildNodes();
/* 460 */       int length = params.getLength();
/*     */ 
/* 462 */       for (int loop = 0; loop < length; loop++) {
/* 463 */         Node currentNode = params.item(loop);
/* 464 */         if (currentNode.getNodeType() == 1) {
/* 465 */           Element currentElement = (Element)currentNode;
/* 466 */           String tagName = currentElement.getTagName();
/* 467 */           if (tagName.equals("param")) {
/* 468 */             setParameter(currentElement, propSetter);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 473 */       propSetter.activate();
/* 474 */       return layout;
/*     */     }
/*     */     catch (Exception oops) {
/* 477 */       LogLog.error("Could not create the Layout. Reported error follows.", oops);
/*     */     }
/* 479 */     return null;
/*     */   }
/*     */ 
/*     */   protected void parseRenderer(Element element)
/*     */   {
/* 485 */     String renderingClass = subst(element.getAttribute("renderingClass"));
/* 486 */     String renderedClass = subst(element.getAttribute("renderedClass"));
/* 487 */     if ((this.repository instanceof RendererSupport))
/* 488 */       RendererMap.addRenderer((RendererSupport)this.repository, renderedClass, renderingClass);
/*     */   }
/*     */ 
/*     */   protected void parseLevel(Element element, Logger logger, boolean isRoot)
/*     */   {
/* 498 */     String catName = logger.getName();
/* 499 */     if (isRoot) {
/* 500 */       catName = "root";
/*     */     }
/*     */ 
/* 503 */     String priStr = subst(element.getAttribute("value"));
/* 504 */     LogLog.debug("Level value for " + catName + " is  [" + priStr + "].");
/*     */ 
/* 506 */     if (("inherited".equalsIgnoreCase(priStr)) || ("null".equalsIgnoreCase(priStr))) {
/* 507 */       if (isRoot)
/* 508 */         LogLog.error("Root level cannot be inherited. Ignoring directive.");
/*     */       else
/* 510 */         logger.setLevel(null);
/*     */     }
/*     */     else {
/* 513 */       String className = subst(element.getAttribute("class"));
/* 514 */       if ("".equals(className)) {
/* 515 */         logger.setLevel(OptionConverter.toLevel(priStr, Level.DEBUG));
/*     */       } else {
/* 517 */         LogLog.debug("Desired Level sub-class: [" + className + ']');
/*     */         try {
/* 519 */           Class clazz = Loader.loadClass(className);
/* 520 */           Method toLevelMethod = clazz.getMethod("toLevel", ONE_STRING_PARAM);
/*     */ 
/* 522 */           Level pri = (Level)toLevelMethod.invoke(null, new Object[] { priStr });
/*     */ 
/* 524 */           logger.setLevel(pri);
/*     */         } catch (Exception oops) {
/* 526 */           LogLog.error("Could not create level [" + priStr + "]. Reported error follows.", oops);
/*     */ 
/* 528 */           return;
/*     */         }
/*     */       }
/*     */     }
/* 532 */     LogLog.debug(catName + " level set to " + logger.getLevel());
/*     */   }
/*     */ 
/*     */   protected void setParameter(Element elem, PropertySetter propSetter)
/*     */   {
/* 537 */     String name = subst(elem.getAttribute("name"));
/* 538 */     String value = elem.getAttribute("value");
/* 539 */     value = subst(OptionConverter.convertSpecialChars(value));
/* 540 */     propSetter.setProperty(name, value);
/*     */   }
/*     */ 
/*     */   public static void configure(Element element)
/*     */   {
/* 552 */     DOMConfigurator configurator = new DOMConfigurator();
/* 553 */     configurator.doConfigure(element, LogManager.getLoggerRepository());
/*     */   }
/*     */ 
/*     */   public static void configureAndWatch(String configFilename)
/*     */   {
/* 567 */     configureAndWatch(configFilename, 60000L);
/*     */   }
/*     */ 
/*     */   public static void configureAndWatch(String configFilename, long delay)
/*     */   {
/* 584 */     XMLWatchdog xdog = new XMLWatchdog(configFilename);
/* 585 */     xdog.setDelay(delay);
/* 586 */     xdog.start();
/*     */   }
/*     */ 
/*     */   public void doConfigure(String filename, LoggerRepository repository)
/*     */   {
/* 596 */     ParseAction action = new ParseAction(filename) { private final String val$filename;
/*     */ 
/* 598 */       public Document parse(DocumentBuilder parser) throws SAXException, IOException { return parser.parse(new File(this.val$filename)); }
/*     */ 
/*     */       public String toString() {
/* 601 */         return "file [" + this.val$filename + "]";
/*     */       }
/*     */     };
/* 604 */     doConfigure(action, repository);
/*     */   }
/*     */ 
/*     */   public void doConfigure(URL url, LoggerRepository repository)
/*     */   {
/* 610 */     ParseAction action = new ParseAction(url) { private final URL val$url;
/*     */ 
/* 612 */       public Document parse(DocumentBuilder parser) throws SAXException, IOException { return parser.parse(this.val$url.toString()); }
/*     */ 
/*     */       public String toString() {
/* 615 */         return "url [" + this.val$url.toString() + "]";
/*     */       }
/*     */     };
/* 618 */     doConfigure(action, repository);
/*     */   }
/*     */ 
/*     */   public void doConfigure(InputStream inputStream, LoggerRepository repository)
/*     */     throws FactoryConfigurationError
/*     */   {
/* 629 */     ParseAction action = new ParseAction(inputStream) { private final InputStream val$inputStream;
/*     */ 
/* 631 */       public Document parse(DocumentBuilder parser) throws SAXException, IOException { InputSource inputSource = new InputSource(this.val$inputStream);
/* 632 */         inputSource.setSystemId("dummy://log4j.dtd");
/* 633 */         return parser.parse(inputSource); }
/*     */ 
/*     */       public String toString() {
/* 636 */         return "input stream [" + this.val$inputStream.toString() + "]";
/*     */       }
/*     */     };
/* 639 */     doConfigure(action, repository);
/*     */   }
/*     */ 
/*     */   public void doConfigure(Reader reader, LoggerRepository repository)
/*     */     throws FactoryConfigurationError
/*     */   {
/* 650 */     ParseAction action = new ParseAction(reader) { private final Reader val$reader;
/*     */ 
/* 652 */       public Document parse(DocumentBuilder parser) throws SAXException, IOException { InputSource inputSource = new InputSource(this.val$reader);
/* 653 */         inputSource.setSystemId("dummy://log4j.dtd");
/* 654 */         return parser.parse(inputSource); }
/*     */ 
/*     */       public String toString() {
/* 657 */         return "reader [" + this.val$reader.toString() + "]";
/*     */       }
/*     */     };
/* 660 */     doConfigure(action, repository);
/*     */   }
/*     */ 
/*     */   protected void doConfigure(InputSource inputSource, LoggerRepository repository)
/*     */     throws FactoryConfigurationError
/*     */   {
/* 671 */     if (inputSource.getSystemId() == null) {
/* 672 */       inputSource.setSystemId("dummy://log4j.dtd");
/*     */     }
/* 674 */     ParseAction action = new ParseAction(inputSource) { private final InputSource val$inputSource;
/*     */ 
/* 676 */       public Document parse(DocumentBuilder parser) throws SAXException, IOException { return parser.parse(this.val$inputSource); }
/*     */ 
/*     */       public String toString() {
/* 679 */         return "input source [" + this.val$inputSource.toString() + "]";
/*     */       }
/*     */     };
/* 682 */     doConfigure(action, repository);
/*     */   }
/*     */ 
/*     */   private final void doConfigure(ParseAction action, LoggerRepository repository)
/*     */     throws FactoryConfigurationError
/*     */   {
/* 688 */     DocumentBuilderFactory dbf = null;
/* 689 */     this.repository = repository;
/*     */     try {
/* 691 */       LogLog.debug("System property is :" + OptionConverter.getSystemProperty("javax.xml.parsers.DocumentBuilderFactory", null));
/*     */ 
/* 694 */       dbf = DocumentBuilderFactory.newInstance();
/* 695 */       LogLog.debug("Standard DocumentBuilderFactory search succeded.");
/* 696 */       LogLog.debug("DocumentBuilderFactory is: " + dbf.getClass().getName());
/*     */     } catch (FactoryConfigurationError fce) {
/* 698 */       Exception e = fce.getException();
/* 699 */       LogLog.debug("Could not instantiate a DocumentBuilderFactory.", e);
/* 700 */       throw fce;
/*     */     }
/*     */     try
/*     */     {
/* 704 */       dbf.setValidating(true);
/*     */ 
/* 706 */       DocumentBuilder docBuilder = dbf.newDocumentBuilder();
/*     */ 
/* 708 */       docBuilder.setErrorHandler(new SAXErrorHandler());
/* 709 */       docBuilder.setEntityResolver(new Log4jEntityResolver());
/*     */ 
/* 711 */       Document doc = action.parse(docBuilder);
/* 712 */       parse(doc.getDocumentElement());
/*     */     }
/*     */     catch (Exception e) {
/* 715 */       LogLog.error("Could not parse " + action.toString() + ".", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doConfigure(Element element, LoggerRepository repository)
/*     */   {
/* 723 */     this.repository = repository;
/* 724 */     parse(element);
/*     */   }
/*     */ 
/*     */   public static void configure(String filename)
/*     */     throws FactoryConfigurationError
/*     */   {
/* 733 */     new DOMConfigurator().doConfigure(filename, LogManager.getLoggerRepository());
/*     */   }
/*     */ 
/*     */   public static void configure(URL url)
/*     */     throws FactoryConfigurationError
/*     */   {
/* 743 */     new DOMConfigurator().doConfigure(url, LogManager.getLoggerRepository());
/*     */   }
/*     */ 
/*     */   protected void parse(Element element)
/*     */   {
/* 755 */     String rootElementName = element.getTagName();
/*     */ 
/* 757 */     if (!rootElementName.equals("log4j:configuration")) {
/* 758 */       if (rootElementName.equals("configuration")) {
/* 759 */         LogLog.warn("The <configuration> element has been deprecated.");
/*     */ 
/* 761 */         LogLog.warn("Use the <log4j:configuration> element instead.");
/*     */       } else {
/* 763 */         LogLog.error("DOM element is - not a <log4j:configuration> element.");
/* 764 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 768 */     String debugAttrib = subst(element.getAttribute("debug"));
/*     */ 
/* 770 */     LogLog.debug("debug attribute= \"" + debugAttrib + "\".");
/*     */ 
/* 773 */     if ((!debugAttrib.equals("")) && (!debugAttrib.equals("null")))
/* 774 */       LogLog.setInternalDebugging(OptionConverter.toBoolean(debugAttrib, true));
/*     */     else {
/* 776 */       LogLog.debug("Ignoring debug attribute.");
/*     */     }
/*     */ 
/* 780 */     String confDebug = subst(element.getAttribute("configDebug"));
/* 781 */     if ((!confDebug.equals("")) && (!confDebug.equals("null"))) {
/* 782 */       LogLog.warn("The \"configDebug\" attribute is deprecated.");
/* 783 */       LogLog.warn("Use the \"debug\" attribute instead.");
/* 784 */       LogLog.setInternalDebugging(OptionConverter.toBoolean(confDebug, true));
/*     */     }
/*     */ 
/* 787 */     String thresholdStr = subst(element.getAttribute("threshold"));
/* 788 */     LogLog.debug("Threshold =\"" + thresholdStr + "\".");
/* 789 */     if ((!"".equals(thresholdStr)) && (!"null".equals(thresholdStr))) {
/* 790 */       this.repository.setThreshold(thresholdStr);
/*     */     }
/*     */ 
/* 802 */     String tagName = null;
/* 803 */     Element currentElement = null;
/* 804 */     Node currentNode = null;
/* 805 */     NodeList children = element.getChildNodes();
/* 806 */     int length = children.getLength();
/*     */ 
/* 808 */     for (int loop = 0; loop < length; loop++) {
/* 809 */       currentNode = children.item(loop);
/* 810 */       if (currentNode.getNodeType() == 1) {
/* 811 */         currentElement = (Element)currentNode;
/* 812 */         tagName = currentElement.getTagName();
/*     */ 
/* 814 */         if (tagName.equals("categoryFactory")) {
/* 815 */           parseCategoryFactory(currentElement);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 820 */     for (int loop = 0; loop < length; loop++) {
/* 821 */       currentNode = children.item(loop);
/* 822 */       if (currentNode.getNodeType() == 1) {
/* 823 */         currentElement = (Element)currentNode;
/* 824 */         tagName = currentElement.getTagName();
/*     */ 
/* 826 */         if ((tagName.equals("category")) || (tagName.equals("logger")))
/* 827 */           parseCategory(currentElement);
/* 828 */         else if (tagName.equals("root"))
/* 829 */           parseRoot(currentElement);
/* 830 */         else if (tagName.equals("renderer"))
/* 831 */           parseRenderer(currentElement);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected String subst(String value)
/*     */   {
/*     */     try
/*     */     {
/* 841 */       return OptionConverter.substVars(value, this.props);
/*     */     } catch (IllegalArgumentException e) {
/* 843 */       LogLog.warn("Could not perform variable substitution.", e);
/* 844 */     }return value;
/*     */   }
/*     */ 
/*     */   private static abstract interface ParseAction
/*     */   {
/*     */     public abstract Document parse(DocumentBuilder paramDocumentBuilder)
/*     */       throws SAXException, IOException;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.xml.DOMConfigurator
 * JD-Core Version:    0.6.0
 */
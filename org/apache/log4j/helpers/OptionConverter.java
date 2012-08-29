/*     */ package org.apache.log4j.helpers;
/*     */ 
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.util.Properties;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.PropertyConfigurator;
/*     */ import org.apache.log4j.spi.Configurator;
/*     */ import org.apache.log4j.spi.LoggerRepository;
/*     */ 
/*     */ public class OptionConverter
/*     */ {
/*  39 */   static String DELIM_START = "${";
/*  40 */   static char DELIM_STOP = '}';
/*  41 */   static int DELIM_START_LEN = 2;
/*  42 */   static int DELIM_STOP_LEN = 1;
/*     */ 
/*     */   public static String[] concatanateArrays(String[] l, String[] r)
/*     */   {
/*  50 */     int len = l.length + r.length;
/*  51 */     String[] a = new String[len];
/*     */ 
/*  53 */     System.arraycopy(l, 0, a, 0, l.length);
/*  54 */     System.arraycopy(r, 0, a, l.length, r.length);
/*     */ 
/*  56 */     return a;
/*     */   }
/*     */ 
/*     */   public static String convertSpecialChars(String s)
/*     */   {
/*  63 */     int len = s.length();
/*  64 */     StringBuffer sbuf = new StringBuffer(len);
/*     */ 
/*  66 */     int i = 0;
/*  67 */     while (i < len) {
/*  68 */       char c = s.charAt(i++);
/*  69 */       if (c == '\\') {
/*  70 */         c = s.charAt(i++);
/*  71 */         if (c == 'n') c = '\n';
/*  72 */         else if (c == 'r') c = '\r';
/*  73 */         else if (c == 't') c = '\t';
/*  74 */         else if (c == 'f') c = '\f';
/*  75 */         else if (c == '\b') c = '\b';
/*  76 */         else if (c == '"') c = '"';
/*  77 */         else if (c == '\'') c = '\'';
/*  78 */         else if (c == '\\') c = '\\';
/*     */       }
/*  80 */       sbuf.append(c);
/*     */     }
/*  82 */     return sbuf.toString();
/*     */   }
/*     */ 
/*     */   public static String getSystemProperty(String key, String def)
/*     */   {
/*     */     try
/*     */     {
/* 100 */       return System.getProperty(key, def);
/*     */     } catch (Throwable e) {
/* 102 */       LogLog.debug("Was not allowed to read system property \"" + key + "\".");
/* 103 */     }return def;
/*     */   }
/*     */ 
/*     */   public static Object instantiateByKey(Properties props, String key, Class superClass, Object defaultValue)
/*     */   {
/* 114 */     String className = findAndSubst(key, props);
/* 115 */     if (className == null) {
/* 116 */       LogLog.error("Could not find value for key " + key);
/* 117 */       return defaultValue;
/*     */     }
/*     */ 
/* 120 */     return instantiateByClassName(className.trim(), superClass, defaultValue);
/*     */   }
/*     */ 
/*     */   public static boolean toBoolean(String value, boolean dEfault)
/*     */   {
/* 134 */     if (value == null)
/* 135 */       return dEfault;
/* 136 */     String trimmedVal = value.trim();
/* 137 */     if ("true".equalsIgnoreCase(trimmedVal))
/* 138 */       return true;
/* 139 */     if ("false".equalsIgnoreCase(trimmedVal))
/* 140 */       return false;
/* 141 */     return dEfault;
/*     */   }
/*     */ 
/*     */   public static int toInt(String value, int dEfault)
/*     */   {
/* 147 */     if (value != null) {
/* 148 */       String s = value.trim();
/*     */       try {
/* 150 */         return Integer.valueOf(s).intValue();
/*     */       }
/*     */       catch (NumberFormatException e) {
/* 153 */         LogLog.error("[" + s + "] is not in proper int form.");
/* 154 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 157 */     return dEfault;
/*     */   }
/*     */ 
/*     */   public static Level toLevel(String value, Level defaultValue)
/*     */   {
/* 183 */     if (value == null) {
/* 184 */       return defaultValue;
/*     */     }
/* 186 */     value = value.trim();
/*     */ 
/* 188 */     int hashIndex = value.indexOf('#');
/* 189 */     if (hashIndex == -1) {
/* 190 */       if ("NULL".equalsIgnoreCase(value)) {
/* 191 */         return null;
/*     */       }
/*     */ 
/* 194 */       return Level.toLevel(value, defaultValue);
/*     */     }
/*     */ 
/* 198 */     Level result = defaultValue;
/*     */ 
/* 200 */     String clazz = value.substring(hashIndex + 1);
/* 201 */     String levelName = value.substring(0, hashIndex);
/*     */ 
/* 204 */     if ("NULL".equalsIgnoreCase(levelName)) {
/* 205 */       return null;
/*     */     }
/*     */ 
/* 208 */     LogLog.debug("toLevel:class=[" + clazz + "]" + ":pri=[" + levelName + "]");
/*     */     try
/*     */     {
/* 212 */       Class customLevel = Loader.loadClass(clazz);
/*     */ 
/* 216 */       Class[] paramTypes = { String.class, Level.class };
/*     */ 
/* 219 */       Method toLevelMethod = customLevel.getMethod("toLevel", paramTypes);
/*     */ 
/* 223 */       Object[] params = { levelName, defaultValue };
/* 224 */       Object o = toLevelMethod.invoke(null, params);
/*     */ 
/* 226 */       result = (Level)o;
/*     */     } catch (ClassNotFoundException e) {
/* 228 */       LogLog.warn("custom level class [" + clazz + "] not found.");
/*     */     } catch (NoSuchMethodException e) {
/* 230 */       LogLog.warn("custom level class [" + clazz + "]" + " does not have a constructor which takes one string parameter", e);
/*     */     }
/*     */     catch (InvocationTargetException e) {
/* 233 */       LogLog.warn("custom level class [" + clazz + "]" + " could not be instantiated", e);
/*     */     }
/*     */     catch (ClassCastException e) {
/* 236 */       LogLog.warn("class [" + clazz + "] is not a subclass of org.apache.log4j.Level", e);
/*     */     }
/*     */     catch (IllegalAccessException e) {
/* 239 */       LogLog.warn("class [" + clazz + "] cannot be instantiated due to access restrictions", e);
/*     */     }
/*     */     catch (Exception e) {
/* 242 */       LogLog.warn("class [" + clazz + "], level [" + levelName + "] conversion failed.", e);
/*     */     }
/*     */ 
/* 245 */     return result;
/*     */   }
/*     */ 
/*     */   public static long toFileSize(String value, long dEfault)
/*     */   {
/* 251 */     if (value == null) {
/* 252 */       return dEfault;
/*     */     }
/* 254 */     String s = value.trim().toUpperCase();
/* 255 */     long multiplier = 1L;
/*     */     int index;
/* 258 */     if ((index = s.indexOf("KB")) != -1) {
/* 259 */       multiplier = 1024L;
/* 260 */       s = s.substring(0, index);
/*     */     }
/* 262 */     else if ((index = s.indexOf("MB")) != -1) {
/* 263 */       multiplier = 1048576L;
/* 264 */       s = s.substring(0, index);
/*     */     }
/* 266 */     else if ((index = s.indexOf("GB")) != -1) {
/* 267 */       multiplier = 1073741824L;
/* 268 */       s = s.substring(0, index);
/*     */     }
/* 270 */     if (s != null) {
/*     */       try {
/* 272 */         return Long.valueOf(s).longValue() * multiplier;
/*     */       }
/*     */       catch (NumberFormatException e) {
/* 275 */         LogLog.error("[" + s + "] is not in proper int form.");
/* 276 */         LogLog.error("[" + value + "] not in expected format.", e);
/*     */       }
/*     */     }
/* 279 */     return dEfault;
/*     */   }
/*     */ 
/*     */   public static String findAndSubst(String key, Properties props)
/*     */   {
/* 291 */     String value = props.getProperty(key);
/* 292 */     if (value == null)
/* 293 */       return null;
/*     */     try
/*     */     {
/* 296 */       return substVars(value, props);
/*     */     } catch (IllegalArgumentException e) {
/* 298 */       LogLog.error("Bad option value [" + value + "].", e);
/* 299 */     }return value;
/*     */   }
/*     */ 
/*     */   public static Object instantiateByClassName(String className, Class superClass, Object defaultValue)
/*     */   {
/* 317 */     if (className != null) {
/*     */       try {
/* 319 */         Class classObj = Loader.loadClass(className);
/* 320 */         if (!superClass.isAssignableFrom(classObj)) {
/* 321 */           LogLog.error("A \"" + className + "\" object is not assignable to a \"" + superClass.getName() + "\" variable.");
/*     */ 
/* 323 */           LogLog.error("The class \"" + superClass.getName() + "\" was loaded by ");
/* 324 */           LogLog.error("[" + superClass.getClassLoader() + "] whereas object of type ");
/* 325 */           LogLog.error("\"" + classObj.getName() + "\" was loaded by [" + classObj.getClassLoader() + "].");
/*     */ 
/* 327 */           return defaultValue;
/*     */         }
/* 329 */         return classObj.newInstance();
/*     */       } catch (Exception e) {
/* 331 */         LogLog.error("Could not instantiate class [" + className + "].", e);
/*     */       }
/*     */     }
/* 334 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   public static String substVars(String val, Properties props)
/*     */     throws IllegalArgumentException
/*     */   {
/* 378 */     StringBuffer sbuf = new StringBuffer();
/*     */ 
/* 380 */     int i = 0;
/*     */     while (true)
/*     */     {
/* 384 */       int j = val.indexOf(DELIM_START, i);
/* 385 */       if (j == -1)
/*     */       {
/* 387 */         if (i == 0) {
/* 388 */           return val;
/*     */         }
/* 390 */         sbuf.append(val.substring(i, val.length()));
/* 391 */         return sbuf.toString();
/*     */       }
/*     */ 
/* 394 */       sbuf.append(val.substring(i, j));
/* 395 */       int k = val.indexOf(DELIM_STOP, j);
/* 396 */       if (k == -1) {
/* 397 */         throw new IllegalArgumentException('"' + val + "\" has no closing brace. Opening brace at position " + j + '.');
/*     */       }
/*     */ 
/* 401 */       j += DELIM_START_LEN;
/* 402 */       String key = val.substring(j, k);
/*     */ 
/* 404 */       String replacement = getSystemProperty(key, null);
/*     */ 
/* 406 */       if ((replacement == null) && (props != null)) {
/* 407 */         replacement = props.getProperty(key);
/*     */       }
/*     */ 
/* 410 */       if (replacement != null)
/*     */       {
/* 416 */         String recursiveReplacement = substVars(replacement, props);
/* 417 */         sbuf.append(recursiveReplacement);
/*     */       }
/* 419 */       i = k + DELIM_STOP_LEN;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void selectAndConfigure(URL url, String clazz, LoggerRepository hierarchy)
/*     */   {
/* 450 */     Configurator configurator = null;
/* 451 */     String filename = url.getFile();
/*     */ 
/* 453 */     if ((clazz == null) && (filename != null) && (filename.endsWith(".xml"))) {
/* 454 */       clazz = "org.apache.log4j.xml.DOMConfigurator";
/*     */     }
/*     */ 
/* 457 */     if (clazz != null) {
/* 458 */       LogLog.debug("Preferred configurator class: " + clazz);
/* 459 */       configurator = (Configurator)instantiateByClassName(clazz, Configurator.class, null);
/*     */ 
/* 462 */       if (configurator == null) {
/* 463 */         LogLog.error("Could not instantiate configurator [" + clazz + "].");
/* 464 */         return;
/*     */       }
/*     */     } else {
/* 467 */       configurator = new PropertyConfigurator();
/*     */     }
/*     */ 
/* 470 */     configurator.doConfigure(url, hierarchy);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.helpers.OptionConverter
 * JD-Core Version:    0.6.0
 */
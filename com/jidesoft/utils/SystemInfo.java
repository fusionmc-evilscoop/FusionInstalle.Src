/*     */ package com.jidesoft.utils;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.Locale;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public final class SystemInfo
/*     */ {
/*  21 */   private static boolean _isWindows = false;
/*     */ 
/*  26 */   private static boolean _isWindowsNTor2000 = false;
/*     */ 
/*  31 */   private static boolean _isWindowsXP = false;
/*     */ 
/*  36 */   private static boolean _isWindowsVista = false;
/*     */ 
/*  41 */   private static boolean _isWindows7 = false;
/*     */ 
/*  46 */   private static boolean _isWindows2003 = false;
/*     */ 
/*  51 */   private static boolean _isClassicWindows = false;
/*     */ 
/*  56 */   private static boolean _isWindows95 = false;
/*     */ 
/*  61 */   private static boolean _isWindows98 = false;
/*     */ 
/*  66 */   private static boolean _supportsTray = false;
/*     */ 
/*  71 */   private static boolean _isMacClassic = false;
/*     */ 
/*  76 */   private static boolean _isMacOSX = false;
/*     */ 
/*  81 */   private static boolean _isLinux = false;
/*     */ 
/*  86 */   private static boolean _isSolaris = false;
/*     */   private static JavaVersion _currentVersion;
/*     */ 
/*     */   public static String getJavaVersion()
/*     */   {
/* 154 */     return SecurityUtils.getProperty("java.version", "1.4.2");
/*     */   }
/*     */ 
/*     */   public static String getJavaVendor()
/*     */   {
/* 163 */     return SecurityUtils.getProperty("java.vendor", "");
/*     */   }
/*     */ 
/*     */   public static String getJavaClassVerion()
/*     */   {
/* 172 */     return SecurityUtils.getProperty("java.class.version", "");
/*     */   }
/*     */ 
/*     */   public static String getOS()
/*     */   {
/* 181 */     return SecurityUtils.getProperty("os.name", "Windows XP");
/*     */   }
/*     */ 
/*     */   public static String getOSVersion()
/*     */   {
/* 190 */     return SecurityUtils.getProperty("os.version", "");
/*     */   }
/*     */ 
/*     */   public static String getOSArchitecture()
/*     */   {
/* 199 */     return SecurityUtils.getProperty("os.arch", "");
/*     */   }
/*     */ 
/*     */   public static String getCurrentDirectory()
/*     */   {
/* 208 */     return SecurityUtils.getProperty("user.dir", "");
/*     */   }
/*     */ 
/*     */   public static boolean supportsTray()
/*     */   {
/* 217 */     return _supportsTray;
/*     */   }
/*     */ 
/*     */   public static void setSupportsTray(boolean support)
/*     */   {
/* 226 */     _supportsTray = support;
/*     */   }
/*     */ 
/*     */   public static boolean isWindows()
/*     */   {
/* 235 */     return _isWindows;
/*     */   }
/*     */ 
/*     */   public static boolean isClassicWindows()
/*     */   {
/* 245 */     return _isClassicWindows;
/*     */   }
/*     */ 
/*     */   public static boolean isWindowsNTor2000()
/*     */   {
/* 254 */     return _isWindowsNTor2000;
/*     */   }
/*     */ 
/*     */   public static boolean isWindowsXP()
/*     */   {
/* 263 */     return _isWindowsXP;
/*     */   }
/*     */ 
/*     */   public static boolean isWindowsVista()
/*     */   {
/* 272 */     return _isWindowsVista;
/*     */   }
/*     */ 
/*     */   public static boolean isWindows7()
/*     */   {
/* 281 */     return _isWindows7;
/*     */   }
/*     */ 
/*     */   public static boolean isWindowsVistaAbove()
/*     */   {
/* 290 */     return (_isWindowsVista) || (_isWindows7);
/*     */   }
/*     */ 
/*     */   public static boolean isWindows95()
/*     */   {
/* 299 */     return _isWindows95;
/*     */   }
/*     */ 
/*     */   public static boolean isWindows98()
/*     */   {
/* 308 */     return _isWindows98;
/*     */   }
/*     */ 
/*     */   public static boolean isWindows2003()
/*     */   {
/* 317 */     return _isWindows2003;
/*     */   }
/*     */ 
/*     */   public static boolean isMacClassic()
/*     */   {
/* 327 */     return _isMacClassic;
/*     */   }
/*     */ 
/*     */   public static boolean isMacOSX()
/*     */   {
/* 336 */     return _isMacOSX;
/*     */   }
/*     */ 
/*     */   public static boolean isAnyMac()
/*     */   {
/* 346 */     return (_isMacClassic) || (_isMacOSX);
/*     */   }
/*     */ 
/*     */   public static boolean isSolaris()
/*     */   {
/* 355 */     return _isSolaris;
/*     */   }
/*     */ 
/*     */   public static boolean isLinux()
/*     */   {
/* 364 */     return _isLinux;
/*     */   }
/*     */ 
/*     */   public static boolean isUnix()
/*     */   {
/* 374 */     return (_isLinux) || (_isSolaris);
/*     */   }
/*     */ 
/*     */   private static void checkJdkVersion() {
/* 378 */     if (_currentVersion == null)
/* 379 */       _currentVersion = new JavaVersion(getJavaVersion());
/*     */   }
/*     */ 
/*     */   public static boolean isJdk13Above()
/*     */   {
/* 389 */     checkJdkVersion();
/* 390 */     return _currentVersion.compareVersion(1.3D, 0, 0) >= 0;
/*     */   }
/*     */ 
/*     */   public static boolean isJdk142Above()
/*     */   {
/* 399 */     checkJdkVersion();
/* 400 */     return _currentVersion.compareVersion(1.4D, 2, 0) >= 0;
/*     */   }
/*     */ 
/*     */   public static boolean isJdk14Above()
/*     */   {
/* 409 */     checkJdkVersion();
/* 410 */     return _currentVersion.compareVersion(1.4D, 0, 0) >= 0;
/*     */   }
/*     */ 
/*     */   public static boolean isJdk15Above()
/*     */   {
/* 419 */     checkJdkVersion();
/* 420 */     return _currentVersion.compareVersion(1.5D, 0, 0) >= 0;
/*     */   }
/*     */ 
/*     */   public static boolean isJdk6Above()
/*     */   {
/* 429 */     checkJdkVersion();
/* 430 */     return _currentVersion.compareVersion(1.6D, 0, 0) >= 0;
/*     */   }
/*     */ 
/*     */   public static boolean isJdk6u10Above()
/*     */   {
/* 439 */     checkJdkVersion();
/* 440 */     return _currentVersion.compareVersion(1.6D, 0, 10) >= 0;
/*     */   }
/*     */ 
/*     */   public static boolean isJdk6u14Above()
/*     */   {
/* 450 */     checkJdkVersion();
/* 451 */     return _currentVersion.compareVersion(1.6D, 0, 14) >= 0;
/*     */   }
/*     */ 
/*     */   public static boolean isJdk7Above()
/*     */   {
/* 460 */     checkJdkVersion();
/* 461 */     return _currentVersion.compareVersion(1.7D, 0, 0) >= 0;
/*     */   }
/*     */ 
/*     */   public static boolean isJdkVersion(double majorVersion, int minorVersion, int build)
/*     */   {
/* 473 */     checkJdkVersion();
/* 474 */     return _currentVersion.compareVersion(majorVersion, minorVersion, build) == 0;
/*     */   }
/*     */ 
/*     */   public static boolean isJdkVersionAbove(double majorVersion, int minorVersion, int build)
/*     */   {
/* 486 */     checkJdkVersion();
/* 487 */     return _currentVersion.compareVersion(majorVersion, minorVersion, build) >= 0;
/*     */   }
/*     */ 
/*     */   public static boolean isJdkVersionBelow(double majorVersion, int minorVersion, int build)
/*     */   {
/* 499 */     checkJdkVersion();
/* 500 */     return _currentVersion.compareVersion(majorVersion, minorVersion, build) <= 0;
/*     */   }
/*     */ 
/*     */   public static boolean isCJKLocale()
/*     */   {
/* 510 */     return isCJKLocale(Locale.getDefault());
/*     */   }
/*     */ 
/*     */   public static boolean isCJKLocale(Locale locale)
/*     */   {
/* 520 */     return (locale.equals(Locale.CHINA)) || (locale.equals(Locale.CHINESE)) || (locale.equals(new Locale("zh", "HK"))) || (locale.equals(Locale.TAIWAN)) || (locale.equals(Locale.JAPAN)) || (locale.equals(Locale.JAPANESE)) || (locale.equals(Locale.KOREA)) || (locale.equals(Locale.KOREAN));
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 101 */     String os = SecurityUtils.getProperty("os.name", "Windows XP");
/*     */ 
/* 104 */     _isWindows = os.indexOf("Windows") != -1;
/*     */     try {
/* 106 */       String osVersion = SecurityUtils.getProperty("os.version", "5.0");
/* 107 */       Float version = Float.valueOf(osVersion);
/* 108 */       _isClassicWindows = version.floatValue() <= 4.0D;
/*     */     }
/*     */     catch (NumberFormatException ex) {
/* 111 */       _isClassicWindows = false;
/*     */     }
/* 113 */     if ((os.indexOf("Windows XP") != -1) || (os.indexOf("Windows NT") != -1) || (os.indexOf("Windows 2000") != -1)) {
/* 114 */       _isWindowsNTor2000 = true;
/*     */     }
/* 116 */     if (os.indexOf("Windows XP") != -1) {
/* 117 */       _isWindowsXP = true;
/*     */     }
/* 119 */     if (os.indexOf("Windows Vista") != -1) {
/* 120 */       _isWindowsVista = true;
/*     */     }
/* 122 */     if (os.indexOf("Windows 7") != -1) {
/* 123 */       _isWindows7 = true;
/*     */     }
/* 125 */     if (os.indexOf("Windows 2003") != -1) {
/* 126 */       _isWindows2003 = true;
/* 127 */       _isWindowsXP = true;
/*     */     }
/* 129 */     if (os.indexOf("Windows 95") != -1) {
/* 130 */       _isWindows95 = true;
/*     */     }
/* 132 */     if (os.indexOf("Windows 98") != -1) {
/* 133 */       _isWindows98 = true;
/*     */     }
/* 135 */     if (_isWindows) _supportsTray = true;
/* 136 */     _isSolaris = (os.indexOf("Solaris") != -1) || (os.indexOf("SunOS") != -1);
/* 137 */     _isLinux = os.indexOf("Linux") != -1;
/* 138 */     if (os.startsWith("Mac OS"))
/* 139 */       if (os.endsWith("X")) {
/* 140 */         _isMacOSX = true;
/*     */       }
/*     */       else
/* 143 */         _isMacClassic = true;  
/*     */   }
/*     */   public static class JavaVersion {
/* 534 */     private static Pattern SUN_JAVA_VERSION = Pattern.compile("(\\d+\\.\\d+)(\\.(\\d+))?(_([^-]+))?(.*)");
/* 535 */     private static Pattern SUN_JAVA_VERSION_SIMPLE = Pattern.compile("(\\d+\\.\\d+)(\\.(\\d+))?(.*)");
/*     */     private double _majorVersion;
/*     */     private int _minorVersion;
/*     */     private int _buildNumber;
/*     */     private String _patch;
/*     */ 
/* 543 */     public JavaVersion(String version) { this._majorVersion = 1.4D;
/* 544 */       this._minorVersion = 0;
/* 545 */       this._buildNumber = 0;
/*     */       try {
/* 547 */         Matcher matcher = SUN_JAVA_VERSION.matcher(version);
/* 548 */         if (matcher.matches()) {
/* 549 */           int groups = matcher.groupCount();
/* 550 */           this._majorVersion = Double.parseDouble(matcher.group(1));
/* 551 */           if ((groups >= 3) && (matcher.group(3) != null)) {
/* 552 */             this._minorVersion = Integer.parseInt(matcher.group(3));
/*     */           }
/* 554 */           if ((groups >= 5) && (matcher.group(5) != null)) {
/*     */             try {
/* 556 */               this._buildNumber = Integer.parseInt(matcher.group(5));
/*     */             }
/*     */             catch (NumberFormatException e) {
/* 559 */               this._patch = matcher.group(5);
/*     */             }
/*     */           }
/* 562 */           if ((groups >= 6) && (matcher.group(6) != null)) {
/* 563 */             String s = matcher.group(6);
/* 564 */             if ((s != null) && (s.trim().length() > 0)) this._patch = s; 
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (NumberFormatException e)
/*     */       {
/*     */         try {
/* 570 */           Matcher matcher = SUN_JAVA_VERSION_SIMPLE.matcher(version);
/* 571 */           if (matcher.matches()) {
/* 572 */             int groups = matcher.groupCount();
/* 573 */             this._majorVersion = Double.parseDouble(matcher.group(1));
/* 574 */             if ((groups >= 3) && (matcher.group(3) != null))
/* 575 */               this._minorVersion = Integer.parseInt(matcher.group(3));
/*     */           }
/*     */         }
/*     */         catch (NumberFormatException e1)
/*     */         {
/* 580 */           System.err.println("Please check the installation of your JDK. The version number " + version + " is not right.");
/*     */         }
/*     */       } }
/*     */ 
/*     */     public JavaVersion(double major, int minor, int build)
/*     */     {
/* 586 */       this._majorVersion = major;
/* 587 */       this._minorVersion = minor;
/* 588 */       this._buildNumber = build;
/*     */     }
/*     */ 
/*     */     public int compareVersion(double major, int minor, int build) {
/* 592 */       double majorResult = this._majorVersion - major;
/* 593 */       if (majorResult != 0.0D) {
/* 594 */         return majorResult < 0.0D ? -1 : 1;
/*     */       }
/* 596 */       int result = this._minorVersion - minor;
/* 597 */       if (result != 0) {
/* 598 */         return result;
/*     */       }
/* 600 */       return this._buildNumber - build;
/*     */     }
/*     */ 
/*     */     public double getMajorVersion() {
/* 604 */       return this._majorVersion;
/*     */     }
/*     */ 
/*     */     public int getMinorVersion() {
/* 608 */       return this._minorVersion;
/*     */     }
/*     */ 
/*     */     public int getBuildNumber() {
/* 612 */       return this._buildNumber;
/*     */     }
/*     */ 
/*     */     public String getPatch() {
/* 616 */       return this._patch;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.SystemInfo
 * JD-Core Version:    0.6.0
 */
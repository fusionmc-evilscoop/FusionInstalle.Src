/*     */ package org.apache.log4j.net;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import org.apache.log4j.AppenderSkeleton;
/*     */ import org.apache.log4j.Layout;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.apache.log4j.helpers.SyslogQuietWriter;
/*     */ import org.apache.log4j.helpers.SyslogWriter;
/*     */ import org.apache.log4j.spi.ErrorHandler;
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ public class SyslogAppender extends AppenderSkeleton
/*     */ {
/*     */   public static final int LOG_KERN = 0;
/*     */   public static final int LOG_USER = 8;
/*     */   public static final int LOG_MAIL = 16;
/*     */   public static final int LOG_DAEMON = 24;
/*     */   public static final int LOG_AUTH = 32;
/*     */   public static final int LOG_SYSLOG = 40;
/*     */   public static final int LOG_LPR = 48;
/*     */   public static final int LOG_NEWS = 56;
/*     */   public static final int LOG_UUCP = 64;
/*     */   public static final int LOG_CRON = 72;
/*     */   public static final int LOG_AUTHPRIV = 80;
/*     */   public static final int LOG_FTP = 88;
/*     */   public static final int LOG_LOCAL0 = 128;
/*     */   public static final int LOG_LOCAL1 = 136;
/*     */   public static final int LOG_LOCAL2 = 144;
/*     */   public static final int LOG_LOCAL3 = 152;
/*     */   public static final int LOG_LOCAL4 = 160;
/*     */   public static final int LOG_LOCAL5 = 168;
/*     */   public static final int LOG_LOCAL6 = 176;
/*     */   public static final int LOG_LOCAL7 = 184;
/*     */   protected static final int SYSLOG_HOST_OI = 0;
/*     */   protected static final int FACILITY_OI = 1;
/*     */   static final String TAB = "    ";
/*  89 */   int syslogFacility = 8;
/*     */   String facilityStr;
/*  91 */   boolean facilityPrinting = false;
/*     */   SyslogQuietWriter sqw;
/*     */   String syslogHost;
/*     */ 
/*     */   public SyslogAppender()
/*     */   {
/*  99 */     initSyslogFacilityStr();
/*     */   }
/*     */ 
/*     */   public SyslogAppender(Layout layout, int syslogFacility)
/*     */   {
/* 104 */     this.layout = layout;
/* 105 */     this.syslogFacility = syslogFacility;
/* 106 */     initSyslogFacilityStr();
/*     */   }
/*     */ 
/*     */   public SyslogAppender(Layout layout, String syslogHost, int syslogFacility)
/*     */   {
/* 111 */     this(layout, syslogFacility);
/* 112 */     setSyslogHost(syslogHost);
/*     */   }
/*     */ 
/*     */   public synchronized void close()
/*     */   {
/* 123 */     this.closed = true;
/*     */ 
/* 126 */     this.sqw = null;
/*     */   }
/*     */ 
/*     */   private void initSyslogFacilityStr()
/*     */   {
/* 131 */     this.facilityStr = getFacilityString(this.syslogFacility);
/*     */ 
/* 133 */     if (this.facilityStr == null) {
/* 134 */       System.err.println("\"" + this.syslogFacility + "\" is an unknown syslog facility. Defaulting to \"USER\".");
/*     */ 
/* 136 */       this.syslogFacility = 8;
/* 137 */       this.facilityStr = "user:";
/*     */     } else {
/* 139 */       this.facilityStr += ":";
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String getFacilityString(int syslogFacility)
/*     */   {
/* 150 */     switch (syslogFacility) { case 0:
/* 151 */       return "kern";
/*     */     case 8:
/* 152 */       return "user";
/*     */     case 16:
/* 153 */       return "mail";
/*     */     case 24:
/* 154 */       return "daemon";
/*     */     case 32:
/* 155 */       return "auth";
/*     */     case 40:
/* 156 */       return "syslog";
/*     */     case 48:
/* 157 */       return "lpr";
/*     */     case 56:
/* 158 */       return "news";
/*     */     case 64:
/* 159 */       return "uucp";
/*     */     case 72:
/* 160 */       return "cron";
/*     */     case 80:
/* 161 */       return "authpriv";
/*     */     case 88:
/* 162 */       return "ftp";
/*     */     case 128:
/* 163 */       return "local0";
/*     */     case 136:
/* 164 */       return "local1";
/*     */     case 144:
/* 165 */       return "local2";
/*     */     case 152:
/* 166 */       return "local3";
/*     */     case 160:
/* 167 */       return "local4";
/*     */     case 168:
/* 168 */       return "local5";
/*     */     case 176:
/* 169 */       return "local6";
/*     */     case 184:
/* 170 */       return "local7"; }
/* 171 */     return null;
/*     */   }
/*     */ 
/*     */   public static int getFacility(String facilityName)
/*     */   {
/* 189 */     if (facilityName != null) {
/* 190 */       facilityName = facilityName.trim();
/*     */     }
/* 192 */     if ("KERN".equalsIgnoreCase(facilityName))
/* 193 */       return 0;
/* 194 */     if ("USER".equalsIgnoreCase(facilityName))
/* 195 */       return 8;
/* 196 */     if ("MAIL".equalsIgnoreCase(facilityName))
/* 197 */       return 16;
/* 198 */     if ("DAEMON".equalsIgnoreCase(facilityName))
/* 199 */       return 24;
/* 200 */     if ("AUTH".equalsIgnoreCase(facilityName))
/* 201 */       return 32;
/* 202 */     if ("SYSLOG".equalsIgnoreCase(facilityName))
/* 203 */       return 40;
/* 204 */     if ("LPR".equalsIgnoreCase(facilityName))
/* 205 */       return 48;
/* 206 */     if ("NEWS".equalsIgnoreCase(facilityName))
/* 207 */       return 56;
/* 208 */     if ("UUCP".equalsIgnoreCase(facilityName))
/* 209 */       return 64;
/* 210 */     if ("CRON".equalsIgnoreCase(facilityName))
/* 211 */       return 72;
/* 212 */     if ("AUTHPRIV".equalsIgnoreCase(facilityName))
/* 213 */       return 80;
/* 214 */     if ("FTP".equalsIgnoreCase(facilityName))
/* 215 */       return 88;
/* 216 */     if ("LOCAL0".equalsIgnoreCase(facilityName))
/* 217 */       return 128;
/* 218 */     if ("LOCAL1".equalsIgnoreCase(facilityName))
/* 219 */       return 136;
/* 220 */     if ("LOCAL2".equalsIgnoreCase(facilityName))
/* 221 */       return 144;
/* 222 */     if ("LOCAL3".equalsIgnoreCase(facilityName))
/* 223 */       return 152;
/* 224 */     if ("LOCAL4".equalsIgnoreCase(facilityName))
/* 225 */       return 160;
/* 226 */     if ("LOCAL5".equalsIgnoreCase(facilityName))
/* 227 */       return 168;
/* 228 */     if ("LOCAL6".equalsIgnoreCase(facilityName))
/* 229 */       return 176;
/* 230 */     if ("LOCAL7".equalsIgnoreCase(facilityName)) {
/* 231 */       return 184;
/*     */     }
/* 233 */     return -1;
/*     */   }
/*     */ 
/*     */   public void append(LoggingEvent event)
/*     */   {
/* 240 */     if (!isAsSevereAsThreshold(event.getLevel())) {
/* 241 */       return;
/*     */     }
/*     */ 
/* 244 */     if (this.sqw == null) {
/* 245 */       this.errorHandler.error("No syslog host is set for SyslogAppedender named \"" + this.name + "\".");
/*     */ 
/* 247 */       return;
/*     */     }
/*     */ 
/* 250 */     String buffer = (this.facilityPrinting ? this.facilityStr : "") + this.layout.format(event);
/*     */ 
/* 253 */     this.sqw.setLevel(event.getLevel().getSyslogEquivalent());
/* 254 */     this.sqw.write(buffer);
/*     */ 
/* 256 */     if (this.layout.ignoresThrowable()) {
/* 257 */       String[] s = event.getThrowableStrRep();
/* 258 */       if (s != null) {
/* 259 */         int len = s.length;
/* 260 */         if (len > 0) {
/* 261 */           this.sqw.write(s[0]);
/* 262 */           for (int i = 1; i < len; i++)
/* 263 */             this.sqw.write("    " + s[i].substring(1));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void activateOptions()
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean requiresLayout()
/*     */   {
/* 285 */     return true;
/*     */   }
/*     */ 
/*     */   public void setSyslogHost(String syslogHost)
/*     */   {
/* 299 */     this.sqw = new SyslogQuietWriter(new SyslogWriter(syslogHost), this.syslogFacility, this.errorHandler);
/*     */ 
/* 302 */     this.syslogHost = syslogHost;
/*     */   }
/*     */ 
/*     */   public String getSyslogHost()
/*     */   {
/* 310 */     return this.syslogHost;
/*     */   }
/*     */ 
/*     */   public void setFacility(String facilityName)
/*     */   {
/* 324 */     if (facilityName == null) {
/* 325 */       return;
/*     */     }
/* 327 */     this.syslogFacility = getFacility(facilityName);
/* 328 */     if (this.syslogFacility == -1) {
/* 329 */       System.err.println("[" + facilityName + "] is an unknown syslog facility. Defaulting to [USER].");
/*     */ 
/* 331 */       this.syslogFacility = 8;
/*     */     }
/*     */ 
/* 334 */     initSyslogFacilityStr();
/*     */ 
/* 337 */     if (this.sqw != null)
/* 338 */       this.sqw.setSyslogFacility(this.syslogFacility);
/*     */   }
/*     */ 
/*     */   public String getFacility()
/*     */   {
/* 347 */     return getFacilityString(this.syslogFacility);
/*     */   }
/*     */ 
/*     */   public void setFacilityPrinting(boolean on)
/*     */   {
/* 357 */     this.facilityPrinting = on;
/*     */   }
/*     */ 
/*     */   public boolean getFacilityPrinting()
/*     */   {
/* 365 */     return this.facilityPrinting;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.net.SyslogAppender
 * JD-Core Version:    0.6.0
 */
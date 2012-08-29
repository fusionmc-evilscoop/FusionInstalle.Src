/*     */ package org.apache.log4j;
/*     */ 
/*     */ import java.util.Date;
/*     */ import org.apache.log4j.helpers.Transform;
/*     */ import org.apache.log4j.spi.LocationInfo;
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ public class HTMLLayout extends Layout
/*     */ {
/*  30 */   protected final int BUF_SIZE = 256;
/*  31 */   protected final int MAX_CAPACITY = 1024;
/*     */ 
/*  33 */   static String TRACE_PREFIX = "<br>&nbsp;&nbsp;&nbsp;&nbsp;";
/*     */ 
/*  36 */   private StringBuffer sbuf = new StringBuffer(256);
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final String LOCATION_INFO_OPTION = "LocationInfo";
/*     */   public static final String TITLE_OPTION = "Title";
/*  60 */   boolean locationInfo = false;
/*     */ 
/*  62 */   String title = "Log4J Log Messages";
/*     */ 
/*     */   public void setLocationInfo(boolean flag)
/*     */   {
/*  77 */     this.locationInfo = flag;
/*     */   }
/*     */ 
/*     */   public boolean getLocationInfo()
/*     */   {
/*  85 */     return this.locationInfo;
/*     */   }
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/*  96 */     this.title = title;
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 104 */     return this.title;
/*     */   }
/*     */ 
/*     */   public String getContentType()
/*     */   {
/* 112 */     return "text/html";
/*     */   }
/*     */ 
/*     */   public void activateOptions()
/*     */   {
/*     */   }
/*     */ 
/*     */   public String format(LoggingEvent event)
/*     */   {
/* 125 */     if (this.sbuf.capacity() > 1024)
/* 126 */       this.sbuf = new StringBuffer(256);
/*     */     else {
/* 128 */       this.sbuf.setLength(0);
/*     */     }
/*     */ 
/* 131 */     this.sbuf.append(Layout.LINE_SEP + "<tr>" + Layout.LINE_SEP);
/*     */ 
/* 133 */     this.sbuf.append("<td>");
/* 134 */     this.sbuf.append(event.timeStamp - LoggingEvent.getStartTime());
/* 135 */     this.sbuf.append("</td>" + Layout.LINE_SEP);
/*     */ 
/* 137 */     this.sbuf.append("<td title=\"" + event.getThreadName() + " thread\">");
/* 138 */     this.sbuf.append(Transform.escapeTags(event.getThreadName()));
/* 139 */     this.sbuf.append("</td>" + Layout.LINE_SEP);
/*     */ 
/* 141 */     this.sbuf.append("<td title=\"Level\">");
/* 142 */     if (event.getLevel().equals(Level.DEBUG)) {
/* 143 */       this.sbuf.append("<font color=\"#339933\">");
/* 144 */       this.sbuf.append(event.getLevel());
/* 145 */       this.sbuf.append("</font>");
/*     */     }
/* 147 */     else if (event.getLevel().isGreaterOrEqual(Level.WARN)) {
/* 148 */       this.sbuf.append("<font color=\"#993300\"><strong>");
/* 149 */       this.sbuf.append(event.getLevel());
/* 150 */       this.sbuf.append("</strong></font>");
/*     */     } else {
/* 152 */       this.sbuf.append(event.getLevel());
/*     */     }
/* 154 */     this.sbuf.append("</td>" + Layout.LINE_SEP);
/*     */ 
/* 156 */     this.sbuf.append("<td title=\"" + event.getLoggerName() + " category\">");
/* 157 */     this.sbuf.append(Transform.escapeTags(event.getLoggerName()));
/* 158 */     this.sbuf.append("</td>" + Layout.LINE_SEP);
/*     */ 
/* 160 */     if (this.locationInfo) {
/* 161 */       LocationInfo locInfo = event.getLocationInformation();
/* 162 */       this.sbuf.append("<td>");
/* 163 */       this.sbuf.append(Transform.escapeTags(locInfo.getFileName()));
/* 164 */       this.sbuf.append(':');
/* 165 */       this.sbuf.append(locInfo.getLineNumber());
/* 166 */       this.sbuf.append("</td>" + Layout.LINE_SEP);
/*     */     }
/*     */ 
/* 169 */     this.sbuf.append("<td title=\"Message\">");
/* 170 */     this.sbuf.append(Transform.escapeTags(event.getRenderedMessage()));
/* 171 */     this.sbuf.append("</td>" + Layout.LINE_SEP);
/* 172 */     this.sbuf.append("</tr>" + Layout.LINE_SEP);
/*     */ 
/* 174 */     if (event.getNDC() != null) {
/* 175 */       this.sbuf.append("<tr><td bgcolor=\"#EEEEEE\" style=\"font-size : xx-small;\" colspan=\"6\" title=\"Nested Diagnostic Context\">");
/* 176 */       this.sbuf.append("NDC: " + Transform.escapeTags(event.getNDC()));
/* 177 */       this.sbuf.append("</td></tr>" + Layout.LINE_SEP);
/*     */     }
/*     */ 
/* 180 */     String[] s = event.getThrowableStrRep();
/* 181 */     if (s != null) {
/* 182 */       this.sbuf.append("<tr><td bgcolor=\"#993300\" style=\"color:White; font-size : xx-small;\" colspan=\"6\">");
/* 183 */       appendThrowableAsHTML(s, this.sbuf);
/* 184 */       this.sbuf.append("</td></tr>" + Layout.LINE_SEP);
/*     */     }
/*     */ 
/* 187 */     return this.sbuf.toString();
/*     */   }
/*     */ 
/*     */   void appendThrowableAsHTML(String[] s, StringBuffer sbuf) {
/* 191 */     if (s != null) {
/* 192 */       int len = s.length;
/* 193 */       if (len == 0)
/* 194 */         return;
/* 195 */       sbuf.append(Transform.escapeTags(s[0]));
/* 196 */       sbuf.append(Layout.LINE_SEP);
/* 197 */       for (int i = 1; i < len; i++) {
/* 198 */         sbuf.append(TRACE_PREFIX);
/* 199 */         sbuf.append(Transform.escapeTags(s[i]));
/* 200 */         sbuf.append(Layout.LINE_SEP);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getHeader()
/*     */   {
/* 210 */     StringBuffer sbuf = new StringBuffer();
/* 211 */     sbuf.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">" + Layout.LINE_SEP);
/* 212 */     sbuf.append("<html>" + Layout.LINE_SEP);
/* 213 */     sbuf.append("<head>" + Layout.LINE_SEP);
/* 214 */     sbuf.append("<title>" + this.title + "</title>" + Layout.LINE_SEP);
/* 215 */     sbuf.append("<style type=\"text/css\">" + Layout.LINE_SEP);
/* 216 */     sbuf.append("<!--" + Layout.LINE_SEP);
/* 217 */     sbuf.append("body, table {font-family: arial,sans-serif; font-size: x-small;}" + Layout.LINE_SEP);
/* 218 */     sbuf.append("th {background: #336699; color: #FFFFFF; text-align: left;}" + Layout.LINE_SEP);
/* 219 */     sbuf.append("-->" + Layout.LINE_SEP);
/* 220 */     sbuf.append("</style>" + Layout.LINE_SEP);
/* 221 */     sbuf.append("</head>" + Layout.LINE_SEP);
/* 222 */     sbuf.append("<body bgcolor=\"#FFFFFF\" topmargin=\"6\" leftmargin=\"6\">" + Layout.LINE_SEP);
/* 223 */     sbuf.append("<hr size=\"1\" noshade>" + Layout.LINE_SEP);
/* 224 */     sbuf.append("Log session start time " + new Date() + "<br>" + Layout.LINE_SEP);
/* 225 */     sbuf.append("<br>" + Layout.LINE_SEP);
/* 226 */     sbuf.append("<table cellspacing=\"0\" cellpadding=\"4\" border=\"1\" bordercolor=\"#224466\" width=\"100%\">" + Layout.LINE_SEP);
/* 227 */     sbuf.append("<tr>" + Layout.LINE_SEP);
/* 228 */     sbuf.append("<th>Time</th>" + Layout.LINE_SEP);
/* 229 */     sbuf.append("<th>Thread</th>" + Layout.LINE_SEP);
/* 230 */     sbuf.append("<th>Level</th>" + Layout.LINE_SEP);
/* 231 */     sbuf.append("<th>Category</th>" + Layout.LINE_SEP);
/* 232 */     if (this.locationInfo) {
/* 233 */       sbuf.append("<th>File:Line</th>" + Layout.LINE_SEP);
/*     */     }
/* 235 */     sbuf.append("<th>Message</th>" + Layout.LINE_SEP);
/* 236 */     sbuf.append("</tr>" + Layout.LINE_SEP);
/* 237 */     return sbuf.toString();
/*     */   }
/*     */ 
/*     */   public String getFooter()
/*     */   {
/* 245 */     StringBuffer sbuf = new StringBuffer();
/* 246 */     sbuf.append("</table>" + Layout.LINE_SEP);
/* 247 */     sbuf.append("<br>" + Layout.LINE_SEP);
/* 248 */     sbuf.append("</body></html>");
/* 249 */     return sbuf.toString();
/*     */   }
/*     */ 
/*     */   public boolean ignoresThrowable()
/*     */   {
/* 257 */     return false;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.HTMLLayout
 * JD-Core Version:    0.6.0
 */
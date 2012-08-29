/*     */ package org.apache.log4j.lf5.util;
/*     */ 
/*     */ import java.awt.Dialog;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import javax.swing.SwingUtilities;
/*     */ import org.apache.log4j.lf5.Log4JLogRecord;
/*     */ import org.apache.log4j.lf5.LogLevel;
/*     */ import org.apache.log4j.lf5.LogLevelFormatException;
/*     */ import org.apache.log4j.lf5.LogRecord;
/*     */ import org.apache.log4j.lf5.viewer.LogBrokerMonitor;
/*     */ import org.apache.log4j.lf5.viewer.LogFactor5ErrorDialog;
/*     */ import org.apache.log4j.lf5.viewer.LogFactor5LoadingDialog;
/*     */ 
/*     */ public class LogFileParser
/*     */   implements Runnable
/*     */ {
/*     */   public static final String RECORD_DELIMITER = "[slf5s.start]";
/*     */   public static final String ATTRIBUTE_DELIMITER = "[slf5s.";
/*     */   public static final String DATE_DELIMITER = "[slf5s.DATE]";
/*     */   public static final String THREAD_DELIMITER = "[slf5s.THREAD]";
/*     */   public static final String CATEGORY_DELIMITER = "[slf5s.CATEGORY]";
/*     */   public static final String LOCATION_DELIMITER = "[slf5s.LOCATION]";
/*     */   public static final String MESSAGE_DELIMITER = "[slf5s.MESSAGE]";
/*     */   public static final String PRIORITY_DELIMITER = "[slf5s.PRIORITY]";
/*     */   public static final String NDC_DELIMITER = "[slf5s.NDC]";
/*  62 */   private static SimpleDateFormat _sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss,S");
/*     */   private LogBrokerMonitor _monitor;
/*     */   LogFactor5LoadingDialog _loadDialog;
/*  65 */   private InputStream _in = null;
/*     */ 
/*     */   public LogFileParser(File file)
/*     */     throws IOException, FileNotFoundException
/*     */   {
/*  72 */     this(new FileInputStream(file));
/*     */   }
/*     */ 
/*     */   public LogFileParser(InputStream stream) throws IOException {
/*  76 */     this._in = stream;
/*     */   }
/*     */ 
/*     */   public void parse(LogBrokerMonitor monitor)
/*     */     throws RuntimeException
/*     */   {
/*  88 */     this._monitor = monitor;
/*  89 */     Thread t = new Thread(this);
/*  90 */     t.start();
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/*  99 */     int index = 0;
/* 100 */     int counter = 0;
/*     */ 
/* 102 */     boolean isLogFile = false;
/*     */ 
/* 104 */     this._loadDialog = new LogFactor5LoadingDialog(this._monitor.getBaseFrame(), "Loading file...");
/*     */     try
/*     */     {
/* 109 */       String logRecords = loadLogFile(this._in);
/*     */       LogRecord temp;
/* 111 */       while ((counter = logRecords.indexOf("[slf5s.start]", index)) != -1) {
/* 112 */         temp = createLogRecord(logRecords.substring(index, counter));
/* 113 */         isLogFile = true;
/*     */ 
/* 115 */         if (temp != null) {
/* 116 */           this._monitor.addMessage(temp);
/*     */         }
/*     */ 
/* 119 */         index = counter + "[slf5s.start]".length();
/*     */       }
/*     */ 
/* 122 */       if ((index < logRecords.length()) && (isLogFile)) {
/* 123 */         temp = createLogRecord(logRecords.substring(index));
/*     */ 
/* 125 */         if (temp != null) {
/* 126 */           this._monitor.addMessage(temp);
/*     */         }
/*     */       }
/*     */ 
/* 130 */       if (!isLogFile) {
/* 131 */         throw new RuntimeException("Invalid log file format");
/*     */       }
/* 133 */       SwingUtilities.invokeLater(new Runnable() {
/*     */         public void run() {
/* 135 */           LogFileParser.this.destroyDialog();
/*     */         } } );
/*     */     }
/*     */     catch (RuntimeException e) {
/* 140 */       destroyDialog();
/* 141 */       displayError("Error - Invalid log file format.\nPlease see documentation on how to load log files.");
/*     */     }
/*     */     catch (IOException e) {
/* 144 */       destroyDialog();
/* 145 */       displayError("Error - Unable to load log file!");
/*     */     }
/*     */ 
/* 148 */     this._in = null;
/*     */   }
/*     */ 
/*     */   protected void displayError(String message)
/*     */   {
/* 155 */     LogFactor5ErrorDialog error = new LogFactor5ErrorDialog(this._monitor.getBaseFrame(), message);
/*     */   }
/*     */ 
/*     */   private void destroyDialog()
/*     */   {
/* 164 */     this._loadDialog.hide();
/* 165 */     this._loadDialog.dispose();
/*     */   }
/*     */ 
/*     */   private String loadLogFile(InputStream stream)
/*     */     throws IOException
/*     */   {
/* 172 */     BufferedInputStream br = new BufferedInputStream(stream);
/*     */ 
/* 174 */     int count = 0;
/* 175 */     int size = br.available();
/*     */ 
/* 177 */     StringBuffer sb = null;
/* 178 */     if (size > 0)
/* 179 */       sb = new StringBuffer(size);
/*     */     else {
/* 181 */       sb = new StringBuffer(1024);
/*     */     }
/*     */ 
/* 184 */     while ((count = br.read()) != -1) {
/* 185 */       sb.append((char)count);
/*     */     }
/*     */ 
/* 188 */     br.close();
/* 189 */     br = null;
/* 190 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   private String parseAttribute(String name, String record)
/*     */   {
/* 196 */     int index = record.indexOf(name);
/*     */ 
/* 198 */     if (index == -1) {
/* 199 */       return null;
/*     */     }
/*     */ 
/* 202 */     return getAttribute(index, record);
/*     */   }
/*     */ 
/*     */   private long parseDate(String record) {
/*     */     try {
/* 207 */       String s = parseAttribute("[slf5s.DATE]", record);
/*     */ 
/* 209 */       if (s == null) {
/* 210 */         return 0L;
/*     */       }
/*     */ 
/* 213 */       Date d = _sdf.parse(s);
/*     */ 
/* 215 */       return d.getTime(); } catch (ParseException e) {
/*     */     }
/* 217 */     return 0L;
/*     */   }
/*     */ 
/*     */   private LogLevel parsePriority(String record)
/*     */   {
/* 222 */     String temp = parseAttribute("[slf5s.PRIORITY]", record);
/*     */ 
/* 224 */     if (temp != null) {
/*     */       try {
/* 226 */         return LogLevel.valueOf(temp);
/*     */       } catch (LogLevelFormatException e) {
/* 228 */         return LogLevel.DEBUG;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 233 */     return LogLevel.DEBUG;
/*     */   }
/*     */ 
/*     */   private String parseThread(String record) {
/* 237 */     return parseAttribute("[slf5s.THREAD]", record);
/*     */   }
/*     */ 
/*     */   private String parseCategory(String record) {
/* 241 */     return parseAttribute("[slf5s.CATEGORY]", record);
/*     */   }
/*     */ 
/*     */   private String parseLocation(String record) {
/* 245 */     return parseAttribute("[slf5s.LOCATION]", record);
/*     */   }
/*     */ 
/*     */   private String parseMessage(String record) {
/* 249 */     return parseAttribute("[slf5s.MESSAGE]", record);
/*     */   }
/*     */ 
/*     */   private String parseNDC(String record) {
/* 253 */     return parseAttribute("[slf5s.NDC]", record);
/*     */   }
/*     */ 
/*     */   private String parseThrowable(String record) {
/* 257 */     return getAttribute(record.length(), record);
/*     */   }
/*     */ 
/*     */   private LogRecord createLogRecord(String record) {
/* 261 */     if ((record == null) || (record.trim().length() == 0)) {
/* 262 */       return null;
/*     */     }
/*     */ 
/* 265 */     LogRecord lr = new Log4JLogRecord();
/* 266 */     lr.setMillis(parseDate(record));
/* 267 */     lr.setLevel(parsePriority(record));
/* 268 */     lr.setCategory(parseCategory(record));
/* 269 */     lr.setLocation(parseLocation(record));
/* 270 */     lr.setThreadDescription(parseThread(record));
/* 271 */     lr.setNDC(parseNDC(record));
/* 272 */     lr.setMessage(parseMessage(record));
/* 273 */     lr.setThrownStackTrace(parseThrowable(record));
/*     */ 
/* 275 */     return lr;
/*     */   }
/*     */ 
/*     */   private String getAttribute(int index, String record)
/*     */   {
/* 280 */     int start = record.lastIndexOf("[slf5s.", index - 1);
/*     */ 
/* 282 */     if (start == -1) {
/* 283 */       return record.substring(0, index);
/*     */     }
/*     */ 
/* 286 */     start = record.indexOf("]", start);
/*     */ 
/* 288 */     return record.substring(start + 1, index).trim();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.util.LogFileParser
 * JD-Core Version:    0.6.0
 */
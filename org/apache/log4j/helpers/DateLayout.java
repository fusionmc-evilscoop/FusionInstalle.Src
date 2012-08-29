/*     */ package org.apache.log4j.helpers;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.text.FieldPosition;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.TimeZone;
/*     */ import org.apache.log4j.Layout;
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ public abstract class DateLayout extends Layout
/*     */ {
/*     */   public static final String NULL_DATE_FORMAT = "NULL";
/*     */   public static final String RELATIVE_TIME_DATE_FORMAT = "RELATIVE";
/*  54 */   protected FieldPosition pos = new FieldPosition(0);
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final String DATE_FORMAT_OPTION = "DateFormat";
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final String TIMEZONE_OPTION = "TimeZone";
/*     */   private String timeZoneID;
/*     */   private String dateFormatOption;
/*     */   protected DateFormat dateFormat;
/*  74 */   protected Date date = new Date();
/*     */ 
/*     */   /** @deprecated */
/*     */   public String[] getOptionStrings()
/*     */   {
/*  82 */     return new String[] { "DateFormat", "TimeZone" };
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void setOption(String option, String value)
/*     */   {
/*  91 */     if (option.equalsIgnoreCase("DateFormat"))
/*  92 */       this.dateFormatOption = value.toUpperCase();
/*  93 */     else if (option.equalsIgnoreCase("TimeZone"))
/*  94 */       this.timeZoneID = value;
/*     */   }
/*     */ 
/*     */   public void setDateFormat(String dateFormat)
/*     */   {
/* 106 */     if (dateFormat != null) {
/* 107 */       this.dateFormatOption = dateFormat;
/*     */     }
/* 109 */     setDateFormat(this.dateFormatOption, TimeZone.getDefault());
/*     */   }
/*     */ 
/*     */   public String getDateFormat()
/*     */   {
/* 117 */     return this.dateFormatOption;
/*     */   }
/*     */ 
/*     */   public void setTimeZone(String timeZone)
/*     */   {
/* 126 */     this.timeZoneID = timeZone;
/*     */   }
/*     */ 
/*     */   public String getTimeZone()
/*     */   {
/* 134 */     return this.timeZoneID;
/*     */   }
/*     */ 
/*     */   public void activateOptions()
/*     */   {
/* 139 */     setDateFormat(this.dateFormatOption);
/* 140 */     if ((this.timeZoneID != null) && (this.dateFormat != null))
/* 141 */       this.dateFormat.setTimeZone(TimeZone.getTimeZone(this.timeZoneID));
/*     */   }
/*     */ 
/*     */   public void dateFormat(StringBuffer buf, LoggingEvent event)
/*     */   {
/* 147 */     if (this.dateFormat != null) {
/* 148 */       this.date.setTime(event.timeStamp);
/* 149 */       this.dateFormat.format(this.date, buf, this.pos);
/* 150 */       buf.append(' ');
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setDateFormat(DateFormat dateFormat, TimeZone timeZone)
/*     */   {
/* 160 */     this.dateFormat = dateFormat;
/* 161 */     this.dateFormat.setTimeZone(timeZone);
/*     */   }
/*     */ 
/*     */   public void setDateFormat(String dateFormatType, TimeZone timeZone)
/*     */   {
/* 180 */     if (dateFormatType == null) {
/* 181 */       this.dateFormat = null;
/* 182 */       return;
/*     */     }
/*     */ 
/* 185 */     if (dateFormatType.equalsIgnoreCase("NULL")) {
/* 186 */       this.dateFormat = null;
/* 187 */     } else if (dateFormatType.equalsIgnoreCase("RELATIVE")) {
/* 188 */       this.dateFormat = new RelativeTimeDateFormat();
/* 189 */     } else if (dateFormatType.equalsIgnoreCase("ABSOLUTE"))
/*     */     {
/* 191 */       this.dateFormat = new AbsoluteTimeDateFormat(timeZone);
/* 192 */     } else if (dateFormatType.equalsIgnoreCase("DATE"))
/*     */     {
/* 194 */       this.dateFormat = new DateTimeDateFormat(timeZone);
/* 195 */     } else if (dateFormatType.equalsIgnoreCase("ISO8601"))
/*     */     {
/* 197 */       this.dateFormat = new ISO8601DateFormat(timeZone);
/*     */     } else {
/* 199 */       this.dateFormat = new SimpleDateFormat(dateFormatType);
/* 200 */       this.dateFormat.setTimeZone(timeZone);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.helpers.DateLayout
 * JD-Core Version:    0.6.0
 */
/*    */ package org.apache.log4j.helpers;
/*    */ 
/*    */ import java.text.DateFormat;
/*    */ import java.text.DateFormatSymbols;
/*    */ import java.text.FieldPosition;
/*    */ import java.text.ParsePosition;
/*    */ import java.util.Calendar;
/*    */ import java.util.Date;
/*    */ import java.util.TimeZone;
/*    */ 
/*    */ public class DateTimeDateFormat extends AbsoluteTimeDateFormat
/*    */ {
/*    */   String[] shortMonths;
/*    */ 
/*    */   public DateTimeDateFormat()
/*    */   {
/* 40 */     this.shortMonths = new DateFormatSymbols().getShortMonths();
/*    */   }
/*    */ 
/*    */   public DateTimeDateFormat(TimeZone timeZone)
/*    */   {
/* 45 */     this();
/* 46 */     setCalendar(Calendar.getInstance(timeZone));
/*    */   }
/*    */ 
/*    */   public StringBuffer format(Date date, StringBuffer sbuf, FieldPosition fieldPosition)
/*    */   {
/* 59 */     this.calendar.setTime(date);
/*    */ 
/* 61 */     int day = this.calendar.get(5);
/* 62 */     if (day < 10)
/* 63 */       sbuf.append('0');
/* 64 */     sbuf.append(day);
/* 65 */     sbuf.append(' ');
/* 66 */     sbuf.append(this.shortMonths[this.calendar.get(2)]);
/* 67 */     sbuf.append(' ');
/*    */ 
/* 69 */     int year = this.calendar.get(1);
/* 70 */     sbuf.append(year);
/* 71 */     sbuf.append(' ');
/*    */ 
/* 73 */     return super.format(date, sbuf, fieldPosition);
/*    */   }
/*    */ 
/*    */   public Date parse(String s, ParsePosition pos)
/*    */   {
/* 81 */     return null;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.helpers.DateTimeDateFormat
 * JD-Core Version:    0.6.0
 */
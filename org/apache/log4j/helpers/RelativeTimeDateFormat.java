/*    */ package org.apache.log4j.helpers;
/*    */ 
/*    */ import java.text.DateFormat;
/*    */ import java.text.FieldPosition;
/*    */ import java.text.ParsePosition;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class RelativeTimeDateFormat extends DateFormat
/*    */ {
/*    */   protected final long startTime;
/*    */ 
/*    */   public RelativeTimeDateFormat()
/*    */   {
/* 39 */     this.startTime = System.currentTimeMillis();
/*    */   }
/*    */ 
/*    */   public StringBuffer format(Date date, StringBuffer sbuf, FieldPosition fieldPosition)
/*    */   {
/* 52 */     return sbuf.append(date.getTime() - this.startTime);
/*    */   }
/*    */ 
/*    */   public Date parse(String s, ParsePosition pos)
/*    */   {
/* 60 */     return null;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.helpers.RelativeTimeDateFormat
 * JD-Core Version:    0.6.0
 */
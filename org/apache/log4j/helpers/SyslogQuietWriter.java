/*    */ package org.apache.log4j.helpers;
/*    */ 
/*    */ import java.io.Writer;
/*    */ import org.apache.log4j.spi.ErrorHandler;
/*    */ 
/*    */ public class SyslogQuietWriter extends QuietWriter
/*    */ {
/*    */   int syslogFacility;
/*    */   int level;
/*    */ 
/*    */   public SyslogQuietWriter(Writer writer, int syslogFacility, ErrorHandler eh)
/*    */   {
/* 37 */     super(writer, eh);
/* 38 */     this.syslogFacility = syslogFacility;
/*    */   }
/*    */ 
/*    */   public void setLevel(int level)
/*    */   {
/* 43 */     this.level = level;
/*    */   }
/*    */ 
/*    */   public void setSyslogFacility(int syslogFacility)
/*    */   {
/* 48 */     this.syslogFacility = syslogFacility;
/*    */   }
/*    */ 
/*    */   public void write(String string)
/*    */   {
/* 53 */     super.write("<" + (this.syslogFacility | this.level) + ">" + string);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.helpers.SyslogQuietWriter
 * JD-Core Version:    0.6.0
 */
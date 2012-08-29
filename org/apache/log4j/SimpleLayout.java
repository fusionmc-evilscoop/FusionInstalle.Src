/*    */ package org.apache.log4j;
/*    */ 
/*    */ import org.apache.log4j.spi.LoggingEvent;
/*    */ 
/*    */ public class SimpleLayout extends Layout
/*    */ {
/* 37 */   StringBuffer sbuf = new StringBuffer(128);
/*    */ 
/*    */   public void activateOptions()
/*    */   {
/*    */   }
/*    */ 
/*    */   public String format(LoggingEvent event)
/*    */   {
/* 59 */     this.sbuf.setLength(0);
/* 60 */     this.sbuf.append(event.getLevel().toString());
/* 61 */     this.sbuf.append(" - ");
/* 62 */     this.sbuf.append(event.getRenderedMessage());
/* 63 */     this.sbuf.append(Layout.LINE_SEP);
/* 64 */     return this.sbuf.toString();
/*    */   }
/*    */ 
/*    */   public boolean ignoresThrowable()
/*    */   {
/* 75 */     return true;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.SimpleLayout
 * JD-Core Version:    0.6.0
 */
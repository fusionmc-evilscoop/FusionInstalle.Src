/*    */ package org.apache.log4j.varia;
/*    */ 
/*    */ import org.apache.log4j.FileAppender;
/*    */ import org.apache.log4j.RollingFileAppender;
/*    */ 
/*    */ public class ExternallyRolledFileAppender extends RollingFileAppender
/*    */ {
/*    */   public static final String ROLL_OVER = "RollOver";
/*    */   public static final String OK = "OK";
/* 60 */   int port = 0;
/*    */   HUP hup;
/*    */ 
/*    */   public void setPort(int port)
/*    */   {
/* 76 */     this.port = port;
/*    */   }
/*    */ 
/*    */   public int getPort()
/*    */   {
/* 84 */     return this.port;
/*    */   }
/*    */ 
/*    */   public void activateOptions()
/*    */   {
/* 92 */     super.activateOptions();
/* 93 */     if (this.port != 0) {
/* 94 */       if (this.hup != null) {
/* 95 */         this.hup.interrupt();
/*    */       }
/* 97 */       this.hup = new HUP(this, this.port);
/* 98 */       this.hup.setDaemon(true);
/* 99 */       this.hup.start();
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.varia.ExternallyRolledFileAppender
 * JD-Core Version:    0.6.0
 */
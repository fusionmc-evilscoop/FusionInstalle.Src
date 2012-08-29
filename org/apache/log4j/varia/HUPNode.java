/*     */ package org.apache.log4j.varia;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.FilterOutputStream;
/*     */ import java.net.Socket;
/*     */ import org.apache.log4j.RollingFileAppender;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ 
/*     */ class HUPNode
/*     */   implements Runnable
/*     */ {
/*     */   Socket socket;
/*     */   DataInputStream dis;
/*     */   DataOutputStream dos;
/*     */   ExternallyRolledFileAppender er;
/*     */ 
/*     */   public HUPNode(Socket socket, ExternallyRolledFileAppender er)
/*     */   {
/* 142 */     this.socket = socket;
/* 143 */     this.er = er;
/*     */     try {
/* 145 */       this.dis = new DataInputStream(socket.getInputStream());
/* 146 */       this.dos = new DataOutputStream(socket.getOutputStream());
/*     */     }
/*     */     catch (Exception e) {
/* 149 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void run() {
/*     */     try {
/* 155 */       String line = this.dis.readUTF();
/* 156 */       LogLog.debug("Got external roll over signal.");
/* 157 */       if ("RollOver".equals(line)) {
/* 158 */         synchronized (this.er) {
/* 159 */           this.er.rollOver();
/*     */         }
/* 161 */         this.dos.writeUTF("OK");
/*     */       }
/*     */       else {
/* 164 */         this.dos.writeUTF("Expecting [RollOver] string.");
/*     */       }
/* 166 */       this.dos.close();
/*     */     }
/*     */     catch (Exception e) {
/* 169 */       LogLog.error("Unexpected exception. Exiting HUPNode.", e);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.varia.HUPNode
 * JD-Core Version:    0.6.0
 */
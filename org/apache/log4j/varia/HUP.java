/*     */ package org.apache.log4j.varia;
/*     */ 
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ 
/*     */ class HUP extends Thread
/*     */ {
/*     */   int port;
/*     */   ExternallyRolledFileAppender er;
/*     */ 
/*     */   HUP(ExternallyRolledFileAppender er, int port)
/*     */   {
/* 111 */     this.er = er;
/* 112 */     this.port = port;
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/* 117 */     while (!isInterrupted())
/*     */       try {
/* 119 */         ServerSocket serverSocket = new ServerSocket(this.port);
/*     */         while (true) {
/* 121 */           Socket socket = serverSocket.accept();
/* 122 */           LogLog.debug("Connected to client at " + socket.getInetAddress());
/* 123 */           new Thread(new HUPNode(socket, this.er)).start();
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/* 127 */         e.printStackTrace();
/*     */       }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.varia.HUP
 * JD-Core Version:    0.6.0
 */
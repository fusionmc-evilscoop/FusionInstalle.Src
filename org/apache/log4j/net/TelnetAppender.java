/*     */ package org.apache.log4j.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ import org.apache.log4j.AppenderSkeleton;
/*     */ import org.apache.log4j.Layout;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ public class TelnetAppender extends AppenderSkeleton
/*     */ {
/*     */   private SocketHandler sh;
/*  58 */   private int port = 23;
/*     */ 
/*     */   public boolean requiresLayout()
/*     */   {
/*  64 */     return true;
/*     */   }
/*     */ 
/*     */   public void activateOptions()
/*     */   {
/*     */     try
/*     */     {
/*  71 */       this.sh = new SocketHandler(this.port);
/*  72 */       this.sh.start();
/*     */     }
/*     */     catch (Exception e) {
/*  75 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getPort()
/*     */   {
/*  81 */     return this.port;
/*     */   }
/*     */ 
/*     */   public void setPort(int port)
/*     */   {
/*  86 */     this.port = port;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/*  92 */     this.sh.finalize();
/*     */   }
/*     */ 
/*     */   protected void append(LoggingEvent event)
/*     */   {
/*  98 */     this.sh.send(this.layout.format(event));
/*  99 */     if (this.layout.ignoresThrowable()) {
/* 100 */       String[] s = event.getThrowableStrRep();
/* 101 */       if (s != null) {
/* 102 */         int len = s.length;
/* 103 */         for (int i = 0; i < len; i++) {
/* 104 */           this.sh.send(s[i]);
/* 105 */           this.sh.send(Layout.LINE_SEP);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected class SocketHandler extends Thread
/*     */   {
/* 118 */     private boolean done = false;
/* 119 */     private Vector writers = new Vector();
/* 120 */     private Vector connections = new Vector();
/*     */     private ServerSocket serverSocket;
/* 122 */     private int MAX_CONNECTIONS = 20;
/*     */ 
/*     */     public void finalize()
/*     */     {
/* 126 */       for (Enumeration e = this.connections.elements(); e.hasMoreElements(); )
/*     */         try {
/* 128 */           ((Socket)e.nextElement()).close();
/*     */         }
/*     */         catch (Exception ex) {
/*     */         }
/*     */       try {
/* 133 */         this.serverSocket.close();
/*     */       } catch (Exception ex) {
/*     */       }
/* 136 */       this.done = true;
/*     */     }
/*     */ 
/*     */     public void send(String message)
/*     */     {
/* 141 */       Enumeration ce = this.connections.elements();
/* 142 */       for (Enumeration e = this.writers.elements(); e.hasMoreElements(); ) {
/* 143 */         Socket sock = (Socket)ce.nextElement();
/* 144 */         PrintWriter writer = (PrintWriter)e.nextElement();
/* 145 */         writer.print(message);
/* 146 */         if (!writer.checkError())
/*     */           continue;
/* 148 */         this.connections.remove(sock);
/* 149 */         this.writers.remove(writer);
/*     */       }
/*     */     }
/*     */ 
/*     */     public void run()
/*     */     {
/* 159 */       while (!this.done)
/*     */         try {
/* 161 */           Socket newClient = this.serverSocket.accept();
/* 162 */           PrintWriter pw = new PrintWriter(newClient.getOutputStream());
/* 163 */           if (this.connections.size() < this.MAX_CONNECTIONS) {
/* 164 */             this.connections.addElement(newClient);
/* 165 */             this.writers.addElement(pw);
/* 166 */             pw.print("TelnetAppender v1.0 (" + this.connections.size() + " active connections)\r\n\r\n");
/*     */ 
/* 168 */             pw.flush();
/*     */           } else {
/* 170 */             pw.print("Too many connections.\r\n");
/* 171 */             pw.flush();
/* 172 */             newClient.close();
/*     */           }
/*     */         } catch (Exception e) {
/* 175 */           LogLog.error("Encountered error while in SocketHandler loop.", e);
/*     */         }
/*     */     }
/*     */ 
/*     */     public SocketHandler(int port) throws IOException
/*     */     {
/* 181 */       this.serverSocket = new ServerSocket(port);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.net.TelnetAppender
 * JD-Core Version:    0.6.0
 */
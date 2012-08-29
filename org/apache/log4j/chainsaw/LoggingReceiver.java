/*     */ package org.apache.log4j.chainsaw;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.net.InetAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketException;
/*     */ import org.apache.log4j.Category;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ class LoggingReceiver extends Thread
/*     */ {
/*  35 */   private static final Logger LOG = Logger.getLogger(LoggingReceiver.class);
/*     */   private MyTableModel mModel;
/*     */   private ServerSocket mSvrSock;
/*     */ 
/*     */   LoggingReceiver(MyTableModel aModel, int aPort)
/*     */     throws IOException
/*     */   {
/*  98 */     setDaemon(true);
/*  99 */     this.mModel = aModel;
/* 100 */     this.mSvrSock = new ServerSocket(aPort);
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/* 105 */     LOG.info("Thread started");
/*     */     try {
/*     */       while (true) {
/* 108 */         LOG.debug("Waiting for a connection");
/* 109 */         Socket client = this.mSvrSock.accept();
/* 110 */         LOG.debug("Got a connection from " + client.getInetAddress().getHostName());
/*     */ 
/* 112 */         Thread t = new Thread(new Slurper(client));
/* 113 */         t.setDaemon(true);
/* 114 */         t.start();
/*     */       }
/*     */     } catch (IOException e) {
/* 117 */       LOG.error("Error in accepting connections, stopping.", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private class Slurper
/*     */     implements Runnable
/*     */   {
/*     */     private final Socket mClient;
/*     */ 
/*     */     Slurper(Socket aClient)
/*     */     {
/*  53 */       this.mClient = aClient;
/*     */     }
/*     */ 
/*     */     public void run()
/*     */     {
/*  58 */       LoggingReceiver.LOG.debug("Starting to get data");
/*     */       try {
/*  60 */         ObjectInputStream ois = new ObjectInputStream(this.mClient.getInputStream());
/*     */         while (true)
/*     */         {
/*  63 */           LoggingEvent event = (LoggingEvent)ois.readObject();
/*  64 */           LoggingReceiver.this.mModel.addEvent(new EventDetails(event));
/*     */         }
/*     */       } catch (EOFException e) {
/*  67 */         LoggingReceiver.LOG.info("Reached EOF, closing connection");
/*     */       } catch (SocketException e) {
/*  69 */         LoggingReceiver.LOG.info("Caught SocketException, closing connection");
/*     */       } catch (IOException e) {
/*  71 */         LoggingReceiver.LOG.warn("Got IOException, closing connection", e);
/*     */       } catch (ClassNotFoundException e) {
/*  73 */         LoggingReceiver.LOG.warn("Got ClassNotFoundException, closing connection", e);
/*     */       }
/*     */       try
/*     */       {
/*  77 */         this.mClient.close();
/*     */       } catch (IOException e) {
/*  79 */         LoggingReceiver.LOG.warn("Error closing connection", e);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.chainsaw.LoggingReceiver
 * JD-Core Version:    0.6.0
 */
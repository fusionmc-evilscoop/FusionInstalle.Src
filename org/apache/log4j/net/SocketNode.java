/*    */ package org.apache.log4j.net;
/*    */ 
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.EOFException;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.net.Socket;
/*    */ import java.net.SocketException;
/*    */ import org.apache.log4j.Category;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.Priority;
/*    */ import org.apache.log4j.spi.LoggerRepository;
/*    */ import org.apache.log4j.spi.LoggingEvent;
/*    */ 
/*    */ public class SocketNode
/*    */   implements Runnable
/*    */ {
/*    */   Socket socket;
/*    */   LoggerRepository hierarchy;
/*    */   ObjectInputStream ois;
/* 48 */   static Logger logger = Logger.getLogger(SocketNode.class);
/*    */ 
/*    */   public SocketNode(Socket socket, LoggerRepository hierarchy) {
/* 51 */     this.socket = socket;
/* 52 */     this.hierarchy = hierarchy;
/*    */     try {
/* 54 */       this.ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 58 */       logger.error("Could not open ObjectInputStream to " + socket, e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void run()
/*    */   {
/*    */     try
/*    */     {
/*    */       while (true)
/*    */       {
/* 75 */         LoggingEvent event = (LoggingEvent)this.ois.readObject();
/*    */ 
/* 77 */         Logger remoteLogger = this.hierarchy.getLogger(event.getLoggerName());
/*    */ 
/* 80 */         if (!event.getLevel().isGreaterOrEqual(remoteLogger.getEffectiveLevel()))
/*    */           continue;
/* 82 */         remoteLogger.callAppenders(event);
/*    */       }
/*    */     }
/*    */     catch (EOFException e) {
/* 86 */       logger.info("Caught java.io.EOFException closing conneciton.");
/*    */     } catch (SocketException e) {
/* 88 */       logger.info("Caught java.net.SocketException closing conneciton.");
/*    */     } catch (IOException e) {
/* 90 */       logger.info("Caught java.io.IOException: " + e);
/* 91 */       logger.info("Closing connection.");
/*    */     } catch (Exception e) {
/* 93 */       logger.error("Unexpected exception. Closing conneciton.", e);
/*    */     }
/*    */     try
/*    */     {
/* 97 */       this.ois.close();
/*    */     } catch (Exception e) {
/* 99 */       logger.info("Could not close connection.", e);
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.net.SocketNode
 * JD-Core Version:    0.6.0
 */
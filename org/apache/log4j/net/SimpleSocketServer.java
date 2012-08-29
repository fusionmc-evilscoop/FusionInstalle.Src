/*    */ package org.apache.log4j.net;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.net.ServerSocket;
/*    */ import java.net.Socket;
/*    */ import org.apache.log4j.Category;
/*    */ import org.apache.log4j.LogManager;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.PropertyConfigurator;
/*    */ import org.apache.log4j.xml.DOMConfigurator;
/*    */ 
/*    */ public class SimpleSocketServer
/*    */ {
/* 45 */   static Logger cat = Logger.getLogger(SimpleSocketServer.class);
/*    */   static int port;
/*    */ 
/*    */   public static void main(String[] argv)
/*    */   {
/* 52 */     if (argv.length == 2)
/* 53 */       init(argv[0], argv[1]);
/*    */     else {
/* 55 */       usage("Wrong number of arguments.");
/*    */     }
/*    */     try
/*    */     {
/* 59 */       cat.info("Listening on port " + port);
/* 60 */       ServerSocket serverSocket = new ServerSocket(port);
/*    */       while (true) {
/* 62 */         cat.info("Waiting to accept a new client.");
/* 63 */         Socket socket = serverSocket.accept();
/* 64 */         cat.info("Connected to client at " + socket.getInetAddress());
/* 65 */         cat.info("Starting new socket node.");
/* 66 */         new Thread(new SocketNode(socket, LogManager.getLoggerRepository())).start();
/*    */       }
/*    */     }
/*    */     catch (Exception e) {
/* 70 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   static void usage(String msg)
/*    */   {
/* 76 */     System.err.println(msg);
/* 77 */     System.err.println("Usage: java " + SimpleSocketServer.class.getName() + " port configFile");
/*    */ 
/* 79 */     System.exit(1);
/*    */   }
/*    */ 
/*    */   static void init(String portStr, String configFile) {
/*    */     try {
/* 84 */       port = Integer.parseInt(portStr);
/*    */     } catch (NumberFormatException e) {
/* 86 */       e.printStackTrace();
/* 87 */       usage("Could not interpret port number [" + portStr + "].");
/*    */     }
/*    */ 
/* 90 */     if (configFile.endsWith(".xml")) {
/* 91 */       new DOMConfigurator(); DOMConfigurator.configure(configFile);
/*    */     } else {
/* 93 */       new PropertyConfigurator(); PropertyConfigurator.configure(configFile);
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.net.SimpleSocketServer
 * JD-Core Version:    0.6.0
 */
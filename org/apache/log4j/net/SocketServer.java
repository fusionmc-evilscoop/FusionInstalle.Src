/*     */ package org.apache.log4j.net;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.net.InetAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.util.Hashtable;
/*     */ import org.apache.log4j.Category;
/*     */ import org.apache.log4j.Hierarchy;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.LogManager;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.apache.log4j.PropertyConfigurator;
/*     */ import org.apache.log4j.spi.LoggerRepository;
/*     */ import org.apache.log4j.spi.RootLogger;
/*     */ 
/*     */ public class SocketServer
/*     */ {
/*  84 */   static String GENERIC = "generic";
/*  85 */   static String CONFIG_FILE_EXT = ".lcf";
/*     */ 
/*  87 */   static Logger cat = Logger.getLogger(SocketServer.class);
/*     */   static SocketServer server;
/*     */   static int port;
/*     */   Hashtable hierarchyMap;
/*     */   LoggerRepository genericHierarchy;
/*     */   File dir;
/*     */ 
/*     */   public static void main(String[] argv)
/*     */   {
/*  99 */     if (argv.length == 3)
/* 100 */       init(argv[0], argv[1], argv[2]);
/*     */     else
/* 102 */       usage("Wrong number of arguments.");
/*     */     try
/*     */     {
/* 105 */       cat.info("Listening on port " + port);
/* 106 */       ServerSocket serverSocket = new ServerSocket(port);
/*     */       while (true) {
/* 108 */         cat.info("Waiting to accept a new client.");
/* 109 */         Socket socket = serverSocket.accept();
/* 110 */         InetAddress inetAddress = socket.getInetAddress();
/* 111 */         cat.info("Connected to client at " + inetAddress);
/*     */ 
/* 113 */         LoggerRepository h = (LoggerRepository)server.hierarchyMap.get(inetAddress);
/* 114 */         if (h == null) {
/* 115 */           h = server.configureHierarchy(inetAddress);
/*     */         }
/*     */ 
/* 118 */         cat.info("Starting new socket node.");
/* 119 */         new Thread(new SocketNode(socket, h)).start();
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 123 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   static void usage(String msg)
/*     */   {
/* 130 */     System.err.println(msg);
/* 131 */     System.err.println("Usage: java " + SocketServer.class.getName() + " port configFile directory");
/*     */ 
/* 133 */     System.exit(1);
/*     */   }
/*     */ 
/*     */   static void init(String portStr, String configFile, String dirStr)
/*     */   {
/*     */     try {
/* 139 */       port = Integer.parseInt(portStr);
/*     */     }
/*     */     catch (NumberFormatException e) {
/* 142 */       e.printStackTrace();
/* 143 */       usage("Could not interpret port number [" + portStr + "].");
/*     */     }
/*     */ 
/* 146 */     PropertyConfigurator.configure(configFile);
/*     */ 
/* 148 */     File dir = new File(dirStr);
/* 149 */     if (!dir.isDirectory()) {
/* 150 */       usage("[" + dirStr + "] is not a directory.");
/*     */     }
/* 152 */     server = new SocketServer(dir);
/*     */   }
/*     */ 
/*     */   public SocketServer(File directory)
/*     */   {
/* 158 */     this.dir = directory;
/* 159 */     this.hierarchyMap = new Hashtable(11);
/*     */   }
/*     */ 
/*     */   LoggerRepository configureHierarchy(InetAddress inetAddress)
/*     */   {
/* 165 */     cat.info("Locating configuration file for " + inetAddress);
/*     */ 
/* 168 */     String s = inetAddress.toString();
/* 169 */     int i = s.indexOf("/");
/* 170 */     if (i == -1) {
/* 171 */       cat.warn("Could not parse the inetAddress [" + inetAddress + "]. Using default hierarchy.");
/*     */ 
/* 173 */       return genericHierarchy();
/*     */     }
/* 175 */     String key = s.substring(0, i);
/*     */ 
/* 177 */     File configFile = new File(this.dir, key + CONFIG_FILE_EXT);
/* 178 */     if (configFile.exists()) {
/* 179 */       Hierarchy h = new Hierarchy(new RootLogger((Level)Priority.DEBUG));
/* 180 */       this.hierarchyMap.put(inetAddress, h);
/*     */ 
/* 182 */       new PropertyConfigurator().doConfigure(configFile.getAbsolutePath(), h);
/*     */ 
/* 184 */       return h;
/*     */     }
/* 186 */     cat.warn("Could not find config file [" + configFile + "].");
/* 187 */     return genericHierarchy();
/*     */   }
/*     */ 
/*     */   LoggerRepository genericHierarchy()
/*     */   {
/* 193 */     if (this.genericHierarchy == null) {
/* 194 */       File f = new File(this.dir, GENERIC + CONFIG_FILE_EXT);
/* 195 */       if (f.exists()) {
/* 196 */         this.genericHierarchy = new Hierarchy(new RootLogger((Level)Priority.DEBUG));
/* 197 */         new PropertyConfigurator().doConfigure(f.getAbsolutePath(), this.genericHierarchy);
/*     */       } else {
/* 199 */         cat.warn("Could not find config file [" + f + "]. Will use the default hierarchy.");
/*     */ 
/* 201 */         this.genericHierarchy = LogManager.getLoggerRepository();
/*     */       }
/*     */     }
/* 204 */     return this.genericHierarchy;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.net.SocketServer
 * JD-Core Version:    0.6.0
 */
/*     */ package org.apache.log4j.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.net.ConnectException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.Socket;
/*     */ import org.apache.log4j.AppenderSkeleton;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ import org.apache.log4j.spi.ErrorHandler;
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ public class SocketAppender extends AppenderSkeleton
/*     */ {
/*     */   static final int DEFAULT_PORT = 4560;
/*     */   static final int DEFAULT_RECONNECTION_DELAY = 30000;
/*     */   String remoteHost;
/*     */   InetAddress address;
/* 119 */   int port = 4560;
/*     */   ObjectOutputStream oos;
/* 121 */   int reconnectionDelay = 30000;
/* 122 */   boolean locationInfo = false;
/*     */   private Connector connector;
/* 126 */   int counter = 0;
/*     */   private static final int RESET_FREQUENCY = 1;
/*     */ 
/*     */   public SocketAppender()
/*     */   {
/*     */   }
/*     */ 
/*     */   public SocketAppender(InetAddress address, int port)
/*     */   {
/* 140 */     this.address = address;
/* 141 */     this.remoteHost = address.getHostName();
/* 142 */     this.port = port;
/* 143 */     connect(address, port);
/*     */   }
/*     */ 
/*     */   public SocketAppender(String host, int port)
/*     */   {
/* 150 */     this.port = port;
/* 151 */     this.address = getAddressByName(host);
/* 152 */     this.remoteHost = host;
/* 153 */     connect(this.address, port);
/*     */   }
/*     */ 
/*     */   public void activateOptions()
/*     */   {
/* 160 */     connect(this.address, this.port);
/*     */   }
/*     */ 
/*     */   public synchronized void close()
/*     */   {
/* 170 */     if (this.closed) {
/* 171 */       return;
/*     */     }
/* 173 */     this.closed = true;
/* 174 */     cleanUp();
/*     */   }
/*     */ 
/*     */   public void cleanUp()
/*     */   {
/* 182 */     if (this.oos != null) {
/*     */       try {
/* 184 */         this.oos.close();
/*     */       } catch (IOException e) {
/* 186 */         LogLog.error("Could not close oos.", e);
/*     */       }
/* 188 */       this.oos = null;
/*     */     }
/* 190 */     if (this.connector != null)
/*     */     {
/* 192 */       this.connector.interrupted = true;
/* 193 */       this.connector = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   void connect(InetAddress address, int port) {
/* 198 */     if (this.address == null)
/* 199 */       return;
/*     */     try
/*     */     {
/* 202 */       cleanUp();
/* 203 */       this.oos = new ObjectOutputStream(new Socket(address, port).getOutputStream());
/*     */     }
/*     */     catch (IOException e) {
/* 206 */       String msg = "Could not connect to remote log4j server at [" + address.getHostName() + "].";
/*     */ 
/* 208 */       if (this.reconnectionDelay > 0) {
/* 209 */         msg = msg + " We will try again later.";
/* 210 */         fireConnector();
/*     */       }
/* 212 */       LogLog.error(msg, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void append(LoggingEvent event)
/*     */   {
/* 218 */     if (event == null) {
/* 219 */       return;
/*     */     }
/* 221 */     if (this.address == null) {
/* 222 */       this.errorHandler.error("No remote host is set for SocketAppender named \"" + this.name + "\".");
/*     */ 
/* 224 */       return;
/*     */     }
/*     */ 
/* 227 */     if (this.oos != null)
/*     */       try {
/* 229 */         if (this.locationInfo) {
/* 230 */           event.getLocationInformation();
/*     */         }
/* 232 */         this.oos.writeObject(event);
/*     */ 
/* 234 */         this.oos.flush();
/* 235 */         if (++this.counter >= 1) {
/* 236 */           this.counter = 0;
/*     */ 
/* 240 */           this.oos.reset();
/*     */         }
/*     */       } catch (IOException e) {
/* 243 */         this.oos = null;
/* 244 */         LogLog.warn("Detected problem with connection: " + e);
/* 245 */         if (this.reconnectionDelay > 0)
/* 246 */           fireConnector();
/*     */       }
/*     */   }
/*     */ 
/*     */   void fireConnector()
/*     */   {
/* 253 */     if (this.connector == null) {
/* 254 */       LogLog.debug("Starting a new connector thread.");
/* 255 */       this.connector = new Connector();
/* 256 */       this.connector.setDaemon(true);
/* 257 */       this.connector.setPriority(1);
/* 258 */       this.connector.start();
/*     */     }
/*     */   }
/*     */ 
/*     */   static InetAddress getAddressByName(String host)
/*     */   {
/*     */     try {
/* 265 */       return InetAddress.getByName(host);
/*     */     } catch (Exception e) {
/* 267 */       LogLog.error("Could not find address of [" + host + "].", e);
/* 268 */     }return null;
/*     */   }
/*     */ 
/*     */   public boolean requiresLayout()
/*     */   {
/* 277 */     return false;
/*     */   }
/*     */ 
/*     */   public void setRemoteHost(String host)
/*     */   {
/* 286 */     this.address = getAddressByName(host);
/* 287 */     this.remoteHost = host;
/*     */   }
/*     */ 
/*     */   public String getRemoteHost()
/*     */   {
/* 294 */     return this.remoteHost;
/*     */   }
/*     */ 
/*     */   public void setPort(int port)
/*     */   {
/* 302 */     this.port = port;
/*     */   }
/*     */ 
/*     */   public int getPort()
/*     */   {
/* 309 */     return this.port;
/*     */   }
/*     */ 
/*     */   public void setLocationInfo(boolean locationInfo)
/*     */   {
/* 318 */     this.locationInfo = locationInfo;
/*     */   }
/*     */ 
/*     */   public boolean getLocationInfo()
/*     */   {
/* 325 */     return this.locationInfo;
/*     */   }
/*     */ 
/*     */   public void setReconnectionDelay(int delay)
/*     */   {
/* 338 */     this.reconnectionDelay = delay;
/*     */   }
/*     */ 
/*     */   public int getReconnectionDelay()
/*     */   {
/* 345 */     return this.reconnectionDelay;
/*     */   }
/*     */ 
/*     */   class Connector extends Thread
/*     */   {
/* 362 */     boolean interrupted = false;
/*     */ 
/*     */     Connector() {
/*     */     }
/*     */     public void run() {
/* 367 */       while (!this.interrupted)
/*     */         try {
/* 369 */           Thread.sleep(SocketAppender.this.reconnectionDelay);
/* 370 */           LogLog.debug("Attempting connection to " + SocketAppender.this.address.getHostName());
/* 371 */           Socket socket = new Socket(SocketAppender.this.address, SocketAppender.this.port);
/* 372 */           synchronized (this) {
/* 373 */             SocketAppender.this.oos = new ObjectOutputStream(socket.getOutputStream());
/* 374 */             SocketAppender.access$002(SocketAppender.this, null);
/* 375 */             LogLog.debug("Connection established. Exiting connector thread.");
/*     */           }
/*     */         }
/*     */         catch (InterruptedException e) {
/* 379 */           LogLog.debug("Connector interrupted. Leaving loop.");
/* 380 */           return;
/*     */         } catch (ConnectException e) {
/* 382 */           LogLog.debug("Remote host " + SocketAppender.this.address.getHostName() + " refused connection.");
/*     */         }
/*     */         catch (IOException e) {
/* 385 */           LogLog.debug("Could not connect to " + SocketAppender.this.address.getHostName() + ". Exception is " + e);
/*     */         }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.net.SocketAppender
 * JD-Core Version:    0.6.0
 */
/*     */ package org.apache.log4j.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.net.InetAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketException;
/*     */ import java.util.Vector;
/*     */ import org.apache.log4j.AppenderSkeleton;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ public class SocketHubAppender extends AppenderSkeleton
/*     */ {
/*     */   static final int DEFAULT_PORT = 4560;
/* 112 */   private int port = 4560;
/* 113 */   private Vector oosList = new Vector();
/* 114 */   private ServerMonitor serverMonitor = null;
/* 115 */   private boolean locationInfo = false;
/*     */ 
/*     */   public SocketHubAppender()
/*     */   {
/*     */   }
/*     */ 
/*     */   public SocketHubAppender(int _port)
/*     */   {
/* 123 */     this.port = _port;
/* 124 */     startServer();
/*     */   }
/*     */ 
/*     */   public void activateOptions()
/*     */   {
/* 131 */     startServer();
/*     */   }
/*     */ 
/*     */   public synchronized void close()
/*     */   {
/* 141 */     if (this.closed) {
/* 142 */       return;
/*     */     }
/* 144 */     LogLog.debug("closing SocketHubAppender " + getName());
/* 145 */     this.closed = true;
/* 146 */     cleanUp();
/* 147 */     LogLog.debug("SocketHubAppender " + getName() + " closed");
/*     */   }
/*     */ 
/*     */   public void cleanUp()
/*     */   {
/* 156 */     LogLog.debug("stopping ServerSocket");
/* 157 */     this.serverMonitor.stopMonitor();
/* 158 */     this.serverMonitor = null;
/*     */ 
/* 161 */     LogLog.debug("closing client connections");
/* 162 */     while (this.oosList.size() != 0) {
/* 163 */       ObjectOutputStream oos = (ObjectOutputStream)this.oosList.elementAt(0);
/* 164 */       if (oos == null) continue;
/*     */       try {
/* 166 */         oos.close();
/*     */       }
/*     */       catch (IOException e) {
/* 169 */         LogLog.error("could not close oos.", e);
/*     */       }
/*     */ 
/* 172 */       this.oosList.removeElementAt(0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void append(LoggingEvent event)
/*     */   {
/* 182 */     if ((event == null) || (this.oosList.size() == 0)) {
/* 183 */       return;
/*     */     }
/*     */ 
/* 186 */     if (this.locationInfo) {
/* 187 */       event.getLocationInformation();
/*     */     }
/*     */ 
/* 191 */     for (int streamCount = 0; streamCount < this.oosList.size(); streamCount++)
/*     */     {
/* 193 */       ObjectOutputStream oos = null;
/*     */       try {
/* 195 */         oos = (ObjectOutputStream)this.oosList.elementAt(streamCount);
/*     */       }
/*     */       catch (ArrayIndexOutOfBoundsException e)
/*     */       {
/*     */       }
/*     */ 
/* 204 */       if (oos == null)
/*     */         break;
/*     */       try
/*     */       {
/* 208 */         oos.writeObject(event);
/* 209 */         oos.flush();
/*     */ 
/* 213 */         oos.reset();
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 217 */         this.oosList.removeElementAt(streamCount);
/* 218 */         LogLog.debug("dropped connection");
/*     */ 
/* 221 */         streamCount--;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean requiresLayout()
/*     */   {
/* 231 */     return false;
/*     */   }
/*     */ 
/*     */   public void setPort(int _port)
/*     */   {
/* 239 */     this.port = _port;
/*     */   }
/*     */ 
/*     */   public int getPort()
/*     */   {
/* 246 */     return this.port;
/*     */   }
/*     */ 
/*     */   public void setLocationInfo(boolean _locationInfo)
/*     */   {
/* 255 */     this.locationInfo = _locationInfo;
/*     */   }
/*     */ 
/*     */   public boolean getLocationInfo()
/*     */   {
/* 262 */     return this.locationInfo;
/*     */   }
/*     */ 
/*     */   private void startServer()
/*     */   {
/* 269 */     this.serverMonitor = new ServerMonitor(this.port, this.oosList);
/*     */   }
/*     */ 
/*     */   private class ServerMonitor
/*     */     implements Runnable
/*     */   {
/*     */     private int port;
/*     */     private Vector oosList;
/*     */     private boolean keepRunning;
/*     */     private Thread monitorThread;
/*     */ 
/*     */     public ServerMonitor(int _port, Vector _oosList)
/*     */     {
/* 287 */       this.port = _port;
/* 288 */       this.oosList = _oosList;
/* 289 */       this.keepRunning = true;
/* 290 */       this.monitorThread = new Thread(this);
/* 291 */       this.monitorThread.setDaemon(true);
/* 292 */       this.monitorThread.start();
/*     */     }
/*     */ 
/*     */     public synchronized void stopMonitor()
/*     */     {
/* 301 */       if (this.keepRunning) {
/* 302 */         LogLog.debug("server monitor thread shutting down");
/* 303 */         this.keepRunning = false;
/*     */         try {
/* 305 */           this.monitorThread.join();
/*     */         }
/*     */         catch (InterruptedException e)
/*     */         {
/*     */         }
/*     */ 
/* 312 */         this.monitorThread = null;
/* 313 */         LogLog.debug("server monitor thread shut down");
/*     */       }
/*     */     }
/*     */ 
/*     */     public void run()
/*     */     {
/* 322 */       ServerSocket serverSocket = null;
/*     */       try {
/* 324 */         serverSocket = new ServerSocket(this.port);
/* 325 */         serverSocket.setSoTimeout(1000);
/*     */       }
/*     */       catch (Exception e) {
/* 328 */         LogLog.error("exception setting timeout, shutting down server socket.", e);
/* 329 */         this.keepRunning = false;
/* 330 */         return;
/*     */       }
/*     */       try
/*     */       {
/*     */         try {
/* 335 */           serverSocket.setSoTimeout(1000);
/*     */         }
/*     */         catch (SocketException e) {
/* 338 */           LogLog.error("exception setting timeout, shutting down server socket.", e);
/* 339 */           jsr 151;
/*     */         }
/*     */ 
/* 342 */         while (this.keepRunning) {
/* 343 */           Socket socket = null;
/*     */           try {
/* 345 */             socket = serverSocket.accept();
/*     */           }
/*     */           catch (InterruptedIOException e)
/*     */           {
/*     */           }
/*     */           catch (SocketException e) {
/* 351 */             LogLog.error("exception accepting socket, shutting down server socket.", e);
/* 352 */             this.keepRunning = false;
/*     */           }
/*     */           catch (IOException e) {
/* 355 */             LogLog.error("exception accepting socket.", e);
/*     */           }
/*     */ 
/* 359 */           if (socket == null) continue;
/*     */           try {
/* 361 */             InetAddress remoteAddress = socket.getInetAddress();
/* 362 */             LogLog.debug("accepting connection from " + remoteAddress.getHostName() + " (" + remoteAddress.getHostAddress() + ")");
/*     */ 
/* 366 */             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
/*     */ 
/* 369 */             this.oosList.addElement(oos);
/*     */           }
/*     */           catch (IOException e) {
/* 372 */             LogLog.error("exception creating output stream on socket.", e);
/*     */           }
/*     */         }
/*     */       }
/*     */       finally
/*     */       {
/*     */         try
/*     */         {
/* 380 */           serverSocket.close();
/*     */         }
/*     */         catch (IOException e)
/*     */         {
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.net.SocketHubAppender
 * JD-Core Version:    0.6.0
 */
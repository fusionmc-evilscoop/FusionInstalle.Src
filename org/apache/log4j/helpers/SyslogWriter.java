/*     */ package org.apache.log4j.helpers;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.SocketException;
/*     */ import java.net.URL;
/*     */ import java.net.UnknownHostException;
/*     */ 
/*     */ public class SyslogWriter extends Writer
/*     */ {
/*  38 */   final int SYSLOG_PORT = 514;
/*     */ 
/*     */   /** @deprecated */
/*     */   static String syslogHost;
/*     */   private InetAddress address;
/*     */   private final int port;
/*     */   private DatagramSocket ds;
/*     */ 
/*     */   public SyslogWriter(String syslogHost)
/*     */   {
/*  59 */     syslogHost = syslogHost;
/*  60 */     if (syslogHost == null) {
/*  61 */       throw new NullPointerException("syslogHost");
/*     */     }
/*     */ 
/*  64 */     String host = syslogHost;
/*  65 */     int urlPort = -1;
/*     */ 
/*  71 */     if ((host.indexOf("[") != -1) || (host.indexOf(':') == host.lastIndexOf(':'))) {
/*     */       try {
/*  73 */         URL url = new URL("http://" + host);
/*  74 */         if (url.getHost() != null) {
/*  75 */           host = url.getHost();
/*     */ 
/*  77 */           if ((host.startsWith("[")) && (host.charAt(host.length() - 1) == ']')) {
/*  78 */             host = host.substring(1, host.length() - 1);
/*     */           }
/*  80 */           urlPort = url.getPort();
/*     */         }
/*     */       } catch (MalformedURLException e) {
/*  83 */         LogLog.error("Malformed URL: will attempt to interpret as InetAddress.", e);
/*     */       }
/*     */     }
/*     */ 
/*  87 */     if (urlPort == -1) {
/*  88 */       urlPort = 514;
/*     */     }
/*  90 */     this.port = urlPort;
/*     */     try
/*     */     {
/*  93 */       this.address = InetAddress.getByName(host);
/*     */     }
/*     */     catch (UnknownHostException e) {
/*  96 */       LogLog.error("Could not find " + host + ". All logging will FAIL.", e);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 101 */       this.ds = new DatagramSocket();
/*     */     }
/*     */     catch (SocketException e) {
/* 104 */       e.printStackTrace();
/* 105 */       LogLog.error("Could not instantiate DatagramSocket to " + host + ". All logging will FAIL.", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(char[] buf, int off, int len)
/*     */     throws IOException
/*     */   {
/* 114 */     write(new String(buf, off, len));
/*     */   }
/*     */ 
/*     */   public void write(String string) throws IOException
/*     */   {
/* 119 */     byte[] bytes = string.getBytes();
/* 120 */     DatagramPacket packet = new DatagramPacket(bytes, bytes.length, this.address, this.port);
/*     */ 
/* 123 */     if ((this.ds != null) && (this.address != null))
/* 124 */       this.ds.send(packet);
/*     */   }
/*     */ 
/*     */   public void flush()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.helpers.SyslogWriter
 * JD-Core Version:    0.6.0
 */
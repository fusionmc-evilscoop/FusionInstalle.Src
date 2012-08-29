/*     */ package org.apache.log4j.varia;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.Socket;
/*     */ import org.apache.log4j.BasicConfigurator;
/*     */ import org.apache.log4j.Category;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class Roller
/*     */ {
/*  40 */   static Logger cat = Logger.getLogger(Roller.class);
/*     */   static String host;
/*     */   static int port;
/*     */ 
/*     */   public static void main(String[] argv)
/*     */   {
/*  60 */     BasicConfigurator.configure();
/*     */ 
/*  62 */     if (argv.length == 2)
/*  63 */       init(argv[0], argv[1]);
/*     */     else {
/*  65 */       usage("Wrong number of arguments.");
/*     */     }
/*  67 */     roll();
/*     */   }
/*     */ 
/*     */   static void usage(String msg)
/*     */   {
/*  72 */     System.err.println(msg);
/*  73 */     System.err.println("Usage: java " + Roller.class.getName() + "host_name port_number");
/*     */ 
/*  75 */     System.exit(1);
/*     */   }
/*     */ 
/*     */   static void init(String hostArg, String portArg)
/*     */   {
/*  80 */     host = hostArg;
/*     */     try {
/*  82 */       port = Integer.parseInt(portArg);
/*     */     }
/*     */     catch (NumberFormatException e) {
/*  85 */       usage("Second argument " + portArg + " is not a valid integer.");
/*     */     }
/*     */   }
/*     */ 
/*     */   static void roll()
/*     */   {
/*     */     try {
/*  92 */       Socket socket = new Socket(host, port);
/*  93 */       DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
/*  94 */       DataInputStream dis = new DataInputStream(socket.getInputStream());
/*  95 */       dos.writeUTF("RollOver");
/*  96 */       String rc = dis.readUTF();
/*  97 */       if ("OK".equals(rc)) {
/*  98 */         cat.info("Roll over signal acknowledged by remote appender.");
/*     */       } else {
/* 100 */         cat.warn("Unexpected return code " + rc + " from remote entity.");
/* 101 */         System.exit(2);
/*     */       }
/*     */     } catch (IOException e) {
/* 104 */       cat.error("Could not send roll signal on host " + host + " port " + port + " .", e);
/*     */ 
/* 106 */       System.exit(2);
/*     */     }
/* 108 */     System.exit(0);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.varia.Roller
 * JD-Core Version:    0.6.0
 */
/*     */ package org.apache.log4j.lf5.util;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public abstract class StreamUtils
/*     */ {
/*     */   public static final int DEFAULT_BUFFER_SIZE = 2048;
/*     */ 
/*     */   public static void copy(InputStream input, OutputStream output)
/*     */     throws IOException
/*     */   {
/*  65 */     copy(input, output, 2048);
/*     */   }
/*     */ 
/*     */   public static void copy(InputStream input, OutputStream output, int bufferSize)
/*     */     throws IOException
/*     */   {
/*  77 */     byte[] buf = new byte[bufferSize];
/*  78 */     int bytesRead = input.read(buf);
/*  79 */     while (bytesRead != -1) {
/*  80 */       output.write(buf, 0, bytesRead);
/*  81 */       bytesRead = input.read(buf);
/*     */     }
/*  83 */     output.flush();
/*     */   }
/*     */ 
/*     */   public static void copyThenClose(InputStream input, OutputStream output)
/*     */     throws IOException
/*     */   {
/*  93 */     copy(input, output);
/*  94 */     input.close();
/*  95 */     output.close();
/*     */   }
/*     */ 
/*     */   public static byte[] getBytes(InputStream input)
/*     */     throws IOException
/*     */   {
/* 105 */     ByteArrayOutputStream result = new ByteArrayOutputStream();
/* 106 */     copy(input, result);
/* 107 */     result.close();
/* 108 */     return result.toByteArray();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.util.StreamUtils
 * JD-Core Version:    0.6.0
 */
/*     */ package org.apache.commons.io;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.io.Writer;
/*     */ 
/*     */ /** @deprecated */
/*     */ public class CopyUtils
/*     */ {
/*     */   private static final int DEFAULT_BUFFER_SIZE = 4096;
/*     */ 
/*     */   public static void copy(byte[] input, OutputStream output)
/*     */     throws IOException
/*     */   {
/* 138 */     output.write(input);
/*     */   }
/*     */ 
/*     */   public static void copy(byte[] input, Writer output)
/*     */     throws IOException
/*     */   {
/* 155 */     ByteArrayInputStream in = new ByteArrayInputStream(input);
/* 156 */     copy(in, output);
/*     */   }
/*     */ 
/*     */   public static void copy(byte[] input, Writer output, String encoding)
/*     */     throws IOException
/*     */   {
/* 175 */     ByteArrayInputStream in = new ByteArrayInputStream(input);
/* 176 */     copy(in, output, encoding);
/*     */   }
/*     */ 
/*     */   public static int copy(InputStream input, OutputStream output)
/*     */     throws IOException
/*     */   {
/* 196 */     byte[] buffer = new byte[4096];
/* 197 */     int count = 0;
/* 198 */     int n = 0;
/* 199 */     while (-1 != (n = input.read(buffer))) {
/* 200 */       output.write(buffer, 0, n);
/* 201 */       count += n;
/*     */     }
/* 203 */     return count;
/*     */   }
/*     */ 
/*     */   public static int copy(Reader input, Writer output)
/*     */     throws IOException
/*     */   {
/* 221 */     char[] buffer = new char[4096];
/* 222 */     int count = 0;
/* 223 */     int n = 0;
/* 224 */     while (-1 != (n = input.read(buffer))) {
/* 225 */       output.write(buffer, 0, n);
/* 226 */       count += n;
/*     */     }
/* 228 */     return count;
/*     */   }
/*     */ 
/*     */   public static void copy(InputStream input, Writer output)
/*     */     throws IOException
/*     */   {
/* 247 */     InputStreamReader in = new InputStreamReader(input);
/* 248 */     copy(in, output);
/*     */   }
/*     */ 
/*     */   public static void copy(InputStream input, Writer output, String encoding)
/*     */     throws IOException
/*     */   {
/* 266 */     InputStreamReader in = new InputStreamReader(input, encoding);
/* 267 */     copy(in, output);
/*     */   }
/*     */ 
/*     */   public static void copy(Reader input, OutputStream output)
/*     */     throws IOException
/*     */   {
/* 286 */     OutputStreamWriter out = new OutputStreamWriter(output);
/* 287 */     copy(input, out);
/*     */ 
/* 290 */     out.flush();
/*     */   }
/*     */ 
/*     */   public static void copy(String input, OutputStream output)
/*     */     throws IOException
/*     */   {
/* 309 */     StringReader in = new StringReader(input);
/* 310 */     OutputStreamWriter out = new OutputStreamWriter(output);
/* 311 */     copy(in, out);
/*     */ 
/* 314 */     out.flush();
/*     */   }
/*     */ 
/*     */   public static void copy(String input, Writer output)
/*     */     throws IOException
/*     */   {
/* 329 */     output.write(input);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.CopyUtils
 * JD-Core Version:    0.6.0
 */
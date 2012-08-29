/*     */ package org.apache.commons.io;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class FileSystemUtils
/*     */ {
/*  52 */   private static final FileSystemUtils INSTANCE = new FileSystemUtils();
/*     */   private static final int INIT_PROBLEM = -1;
/*     */   private static final int OTHER = 0;
/*     */   private static final int WINDOWS = 1;
/*     */   private static final int UNIX = 2;
/*     */   private static final int POSIX_UNIX = 3;
/*     */   private static final int OS;
/*     */ 
/*     */   /** @deprecated */
/*     */   public static long freeSpace(String path)
/*     */     throws IOException
/*     */   {
/* 137 */     return INSTANCE.freeSpaceOS(path, OS, false);
/*     */   }
/*     */ 
/*     */   public static long freeSpaceKb(String path)
/*     */     throws IOException
/*     */   {
/* 166 */     return INSTANCE.freeSpaceOS(path, OS, true);
/*     */   }
/*     */ 
/*     */   long freeSpaceOS(String path, int os, boolean kb)
/*     */     throws IOException
/*     */   {
/* 189 */     if (path == null) {
/* 190 */       throw new IllegalArgumentException("Path must not be empty");
/*     */     }
/* 192 */     switch (os) {
/*     */     case 1:
/* 194 */       return kb ? freeSpaceWindows(path) / 1024L : freeSpaceWindows(path);
/*     */     case 2:
/* 196 */       return freeSpaceUnix(path, kb, false);
/*     */     case 3:
/* 198 */       return freeSpaceUnix(path, kb, true);
/*     */     case 0:
/* 200 */       throw new IllegalStateException("Unsupported operating system");
/*     */     }
/* 202 */     throw new IllegalStateException("Exception caught when determining operating system");
/*     */   }
/*     */ 
/*     */   long freeSpaceWindows(String path)
/*     */     throws IOException
/*     */   {
/* 216 */     path = FilenameUtils.normalize(path);
/* 217 */     if ((path.length() > 2) && (path.charAt(1) == ':')) {
/* 218 */       path = path.substring(0, 2);
/*     */     }
/*     */ 
/* 222 */     String[] cmdAttribs = { "cmd.exe", "/C", "dir /-c " + path };
/*     */ 
/* 225 */     List lines = performCommand(cmdAttribs, 2147483647);
/*     */ 
/* 231 */     for (int i = lines.size() - 1; i >= 0; i--) {
/* 232 */       String line = (String)lines.get(i);
/* 233 */       if (line.length() > 0) {
/* 234 */         return parseDir(line, path);
/*     */       }
/*     */     }
/*     */ 
/* 238 */     throw new IOException("Command line 'dir /-c' did not return any info for path '" + path + "'");
/*     */   }
/*     */ 
/*     */   long parseDir(String line, String path)
/*     */     throws IOException
/*     */   {
/* 256 */     int bytesStart = 0;
/* 257 */     int bytesEnd = 0;
/* 258 */     int j = line.length() - 1;
/* 259 */     while (j >= 0) {
/* 260 */       char c = line.charAt(j);
/* 261 */       if (Character.isDigit(c))
/*     */       {
/* 264 */         bytesEnd = j + 1;
/* 265 */         break;
/*     */       }
/* 267 */       j--;
/*     */     }
/* 269 */     while (j >= 0) {
/* 270 */       char c = line.charAt(j);
/* 271 */       if ((!Character.isDigit(c)) && (c != ',') && (c != '.'))
/*     */       {
/* 274 */         bytesStart = j + 1;
/* 275 */         break;
/*     */       }
/* 277 */       j--;
/*     */     }
/* 279 */     if (j < 0) {
/* 280 */       throw new IOException("Command line 'dir /-c' did not return valid info for path '" + path + "'");
/*     */     }
/*     */ 
/* 286 */     StringBuffer buf = new StringBuffer(line.substring(bytesStart, bytesEnd));
/* 287 */     for (int k = 0; k < buf.length(); k++) {
/* 288 */       if ((buf.charAt(k) == ',') || (buf.charAt(k) == '.')) {
/* 289 */         buf.deleteCharAt(k--);
/*     */       }
/*     */     }
/* 292 */     return parseBytes(buf.toString(), path);
/*     */   }
/*     */ 
/*     */   long freeSpaceUnix(String path, boolean kb, boolean posix)
/*     */     throws IOException
/*     */   {
/* 306 */     if (path.length() == 0) {
/* 307 */       throw new IllegalArgumentException("Path must not be empty");
/*     */     }
/* 309 */     path = FilenameUtils.normalize(path);
/*     */ 
/* 312 */     String flags = "-";
/* 313 */     if (kb) {
/* 314 */       flags = flags + "k";
/*     */     }
/* 316 */     if (posix) {
/* 317 */       flags = flags + "P";
/*     */     }
/* 319 */     String[] cmdAttribs = { "df", flags.length() > 1 ? new String[] { "df", flags, path } : path };
/*     */ 
/* 323 */     List lines = performCommand(cmdAttribs, 3);
/* 324 */     if (lines.size() < 2)
/*     */     {
/* 326 */       throw new IOException("Command line 'df' did not return info as expected for path '" + path + "'- response was " + lines);
/*     */     }
/*     */ 
/* 330 */     String line2 = (String)lines.get(1);
/*     */ 
/* 333 */     StringTokenizer tok = new StringTokenizer(line2, " ");
/* 334 */     if (tok.countTokens() < 4)
/*     */     {
/* 336 */       if ((tok.countTokens() == 1) && (lines.size() >= 3)) {
/* 337 */         String line3 = (String)lines.get(2);
/* 338 */         tok = new StringTokenizer(line3, " ");
/*     */       } else {
/* 340 */         throw new IOException("Command line 'df' did not return data as expected for path '" + path + "'- check path is valid");
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 345 */       tok.nextToken();
/*     */     }
/* 347 */     tok.nextToken();
/* 348 */     tok.nextToken();
/* 349 */     String freeSpace = tok.nextToken();
/* 350 */     return parseBytes(freeSpace, path);
/*     */   }
/*     */ 
/*     */   long parseBytes(String freeSpace, String path)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 364 */       long bytes = Long.parseLong(freeSpace);
/* 365 */       if (bytes < 0L) {
/* 366 */         throw new IOException("Command line 'df' did not find free space in response for path '" + path + "'- check path is valid");
/*     */       }
/*     */ 
/* 370 */       return bytes;
/*     */     } catch (NumberFormatException ex) {
/*     */     }
/* 373 */     throw new IOException("Command line 'df' did not return numeric data as expected for path '" + path + "'- check path is valid");
/*     */   }
/*     */ 
/*     */   List performCommand(String[] cmdAttribs, int max)
/*     */     throws IOException
/*     */   {
/* 397 */     List lines = new ArrayList(20);
/* 398 */     Process proc = null;
/* 399 */     InputStream in = null;
/* 400 */     OutputStream out = null;
/* 401 */     InputStream err = null;
/* 402 */     BufferedReader inr = null;
/*     */     try {
/* 404 */       proc = openProcess(cmdAttribs);
/* 405 */       in = proc.getInputStream();
/* 406 */       out = proc.getOutputStream();
/* 407 */       err = proc.getErrorStream();
/* 408 */       inr = new BufferedReader(new InputStreamReader(in));
/* 409 */       String line = inr.readLine();
/* 410 */       while ((line != null) && (lines.size() < max)) {
/* 411 */         line = line.toLowerCase().trim();
/* 412 */         lines.add(line);
/* 413 */         line = inr.readLine();
/*     */       }
/*     */ 
/* 416 */       proc.waitFor();
/* 417 */       if (proc.exitValue() != 0)
/*     */       {
/* 419 */         throw new IOException("Command line returned OS error code '" + proc.exitValue() + "' for command " + Arrays.asList(cmdAttribs));
/*     */       }
/*     */ 
/* 423 */       if (lines.size() == 0)
/*     */       {
/* 425 */         throw new IOException("Command line did not return any info for command " + Arrays.asList(cmdAttribs));
/*     */       }
/*     */ 
/* 429 */       List localList1 = lines;
/*     */       return localList1;
/*     */     }
/*     */     catch (InterruptedException ex)
/*     */     {
/* 432 */       throw new IOException("Command line threw an InterruptedException '" + ex.getMessage() + "' for command " + Arrays.asList(cmdAttribs));
/*     */     }
/*     */     finally
/*     */     {
/* 436 */       IOUtils.closeQuietly(in);
/* 437 */       IOUtils.closeQuietly(out);
/* 438 */       IOUtils.closeQuietly(err);
/* 439 */       IOUtils.closeQuietly(inr);
/* 440 */       if (proc != null)
/* 441 */         proc.destroy(); 
/* 441 */     }throw localObject;
/*     */   }
/*     */ 
/*     */   Process openProcess(String[] cmdAttribs)
/*     */     throws IOException
/*     */   {
/* 454 */     return Runtime.getRuntime().exec(cmdAttribs);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  68 */     int os = 0;
/*     */     try {
/*  70 */       String osName = System.getProperty("os.name");
/*  71 */       if (osName == null) {
/*  72 */         throw new IOException("os.name not found");
/*     */       }
/*  74 */       osName = osName.toLowerCase();
/*     */ 
/*  76 */       if (osName.indexOf("windows") != -1)
/*  77 */         os = 1;
/*  78 */       else if ((osName.indexOf("linux") != -1) || (osName.indexOf("sun os") != -1) || (osName.indexOf("sunos") != -1) || (osName.indexOf("solaris") != -1) || (osName.indexOf("mpe/ix") != -1) || (osName.indexOf("freebsd") != -1) || (osName.indexOf("irix") != -1) || (osName.indexOf("digital unix") != -1) || (osName.indexOf("unix") != -1) || (osName.indexOf("mac os x") != -1))
/*     */       {
/*  88 */         os = 2;
/*  89 */       } else if ((osName.indexOf("hp-ux") != -1) || (osName.indexOf("aix") != -1))
/*     */       {
/*  91 */         os = 3;
/*     */       }
/*  93 */       else os = 0;
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*  97 */       os = -1;
/*     */     }
/*  99 */     OS = os;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.FileSystemUtils
 * JD-Core Version:    0.6.0
 */
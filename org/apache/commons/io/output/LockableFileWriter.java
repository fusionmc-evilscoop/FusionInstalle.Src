/*     */ package org.apache.commons.io.output;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ public class LockableFileWriter extends Writer
/*     */ {
/*     */   private static final String LCK = ".lck";
/*     */   private final Writer out;
/*     */   private final File lockFile;
/*     */ 
/*     */   public LockableFileWriter(String fileName)
/*     */     throws IOException
/*     */   {
/*  71 */     this(fileName, false, null);
/*     */   }
/*     */ 
/*     */   public LockableFileWriter(String fileName, boolean append)
/*     */     throws IOException
/*     */   {
/*  83 */     this(fileName, append, null);
/*     */   }
/*     */ 
/*     */   public LockableFileWriter(String fileName, boolean append, String lockDir)
/*     */     throws IOException
/*     */   {
/*  96 */     this(new File(fileName), append, lockDir);
/*     */   }
/*     */ 
/*     */   public LockableFileWriter(File file)
/*     */     throws IOException
/*     */   {
/* 108 */     this(file, false, null);
/*     */   }
/*     */ 
/*     */   public LockableFileWriter(File file, boolean append)
/*     */     throws IOException
/*     */   {
/* 120 */     this(file, append, null);
/*     */   }
/*     */ 
/*     */   public LockableFileWriter(File file, boolean append, String lockDir)
/*     */     throws IOException
/*     */   {
/* 133 */     this(file, null, append, lockDir);
/*     */   }
/*     */ 
/*     */   public LockableFileWriter(File file, String encoding)
/*     */     throws IOException
/*     */   {
/* 145 */     this(file, encoding, false, null);
/*     */   }
/*     */ 
/*     */   public LockableFileWriter(File file, String encoding, boolean append, String lockDir)
/*     */     throws IOException
/*     */   {
/* 162 */     file = file.getAbsoluteFile();
/* 163 */     if (file.getParentFile() != null) {
/* 164 */       FileUtils.forceMkdir(file.getParentFile());
/*     */     }
/* 166 */     if (file.isDirectory()) {
/* 167 */       throw new IOException("File specified is a directory");
/*     */     }
/*     */ 
/* 171 */     if (lockDir == null) {
/* 172 */       lockDir = System.getProperty("java.io.tmpdir");
/*     */     }
/* 174 */     File lockDirFile = new File(lockDir);
/* 175 */     FileUtils.forceMkdir(lockDirFile);
/* 176 */     testLockDir(lockDirFile);
/* 177 */     this.lockFile = new File(lockDirFile, file.getName() + ".lck");
/*     */ 
/* 180 */     createLock();
/*     */ 
/* 183 */     this.out = initWriter(file, encoding, append);
/*     */   }
/*     */ 
/*     */   private void testLockDir(File lockDir)
/*     */     throws IOException
/*     */   {
/* 195 */     if (!lockDir.exists()) {
/* 196 */       throw new IOException("Could not find lockDir: " + lockDir.getAbsolutePath());
/*     */     }
/*     */ 
/* 199 */     if (!lockDir.canWrite())
/* 200 */       throw new IOException("Could not write to lockDir: " + lockDir.getAbsolutePath());
/*     */   }
/*     */ 
/*     */   private void createLock()
/*     */     throws IOException
/*     */   {
/* 211 */     synchronized (LockableFileWriter.class) {
/* 212 */       if (!this.lockFile.createNewFile()) {
/* 213 */         throw new IOException("Can't write file, lock " + this.lockFile.getAbsolutePath() + " exists");
/*     */       }
/*     */ 
/* 216 */       this.lockFile.deleteOnExit();
/*     */     }
/*     */   }
/*     */ 
/*     */   private Writer initWriter(File file, String encoding, boolean append)
/*     */     throws IOException
/*     */   {
/* 231 */     boolean fileExistedAlready = file.exists();
/* 232 */     OutputStream stream = null;
/* 233 */     Writer writer = null;
/*     */     try {
/* 235 */       if (encoding == null) {
/* 236 */         writer = new FileWriter(file.getAbsolutePath(), append);
/*     */       } else {
/* 238 */         stream = new FileOutputStream(file.getAbsolutePath(), append);
/* 239 */         writer = new OutputStreamWriter(stream, encoding);
/*     */       }
/*     */     } catch (IOException ex) {
/* 242 */       IOUtils.closeQuietly(writer);
/* 243 */       IOUtils.closeQuietly(stream);
/* 244 */       this.lockFile.delete();
/* 245 */       if (!fileExistedAlready) {
/* 246 */         file.delete();
/*     */       }
/* 248 */       throw ex;
/*     */     } catch (RuntimeException ex) {
/* 250 */       IOUtils.closeQuietly(writer);
/* 251 */       IOUtils.closeQuietly(stream);
/* 252 */       this.lockFile.delete();
/* 253 */       if (!fileExistedAlready) {
/* 254 */         file.delete();
/*     */       }
/* 256 */       throw ex;
/*     */     }
/* 258 */     return writer;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 269 */       this.out.close();
/*     */     } finally {
/* 271 */       this.lockFile.delete();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(int idx)
/*     */     throws IOException
/*     */   {
/* 278 */     this.out.write(idx);
/*     */   }
/*     */ 
/*     */   public void write(char[] chr) throws IOException
/*     */   {
/* 283 */     this.out.write(chr);
/*     */   }
/*     */ 
/*     */   public void write(char[] chr, int st, int end) throws IOException
/*     */   {
/* 288 */     this.out.write(chr, st, end);
/*     */   }
/*     */ 
/*     */   public void write(String str) throws IOException
/*     */   {
/* 293 */     this.out.write(str);
/*     */   }
/*     */ 
/*     */   public void write(String str, int st, int end) throws IOException
/*     */   {
/* 298 */     this.out.write(str, st, end);
/*     */   }
/*     */ 
/*     */   public void flush() throws IOException
/*     */   {
/* 303 */     this.out.flush();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.output.LockableFileWriter
 * JD-Core Version:    0.6.0
 */
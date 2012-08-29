/*     */ package org.apache.log4j;
/*     */ 
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ 
/*     */ public class ConsoleAppender extends WriterAppender
/*     */ {
/*     */   public static final String SYSTEM_OUT = "System.out";
/*     */   public static final String SYSTEM_ERR = "System.err";
/*  36 */   protected String target = "System.out";
/*     */ 
/*  42 */   private boolean follow = false;
/*     */ 
/*     */   public ConsoleAppender()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ConsoleAppender(Layout layout)
/*     */   {
/*  56 */     this(layout, "System.out");
/*     */   }
/*     */ 
/*     */   public ConsoleAppender(Layout layout, String target)
/*     */   {
/*  65 */     setLayout(layout);
/*  66 */     setTarget(target);
/*  67 */     activateOptions();
/*     */   }
/*     */ 
/*     */   public void setTarget(String value)
/*     */   {
/*  77 */     String v = value.trim();
/*     */ 
/*  79 */     if ("System.out".equalsIgnoreCase(v))
/*  80 */       this.target = "System.out";
/*  81 */     else if ("System.err".equalsIgnoreCase(v))
/*  82 */       this.target = "System.err";
/*     */     else
/*  84 */       targetWarn(value);
/*     */   }
/*     */ 
/*     */   public String getTarget()
/*     */   {
/*  96 */     return this.target;
/*     */   }
/*     */ 
/*     */   public final void setFollow(boolean newValue)
/*     */   {
/* 107 */     this.follow = newValue;
/*     */   }
/*     */ 
/*     */   public final boolean getFollow()
/*     */   {
/* 118 */     return this.follow;
/*     */   }
/*     */ 
/*     */   void targetWarn(String val) {
/* 122 */     LogLog.warn("[" + val + "] should be System.out or System.err.");
/* 123 */     LogLog.warn("Using previously set target, System.out by default.");
/*     */   }
/*     */ 
/*     */   public void activateOptions()
/*     */   {
/* 130 */     if (this.follow) {
/* 131 */       if (this.target.equals("System.err"))
/* 132 */         setWriter(createWriter(new SystemErrStream()));
/*     */       else {
/* 134 */         setWriter(createWriter(new SystemOutStream()));
/*     */       }
/*     */     }
/* 137 */     else if (this.target.equals("System.err"))
/* 138 */       setWriter(createWriter(System.err));
/*     */     else {
/* 140 */       setWriter(createWriter(System.out));
/*     */     }
/*     */ 
/* 144 */     super.activateOptions();
/*     */   }
/*     */ 
/*     */   protected final void closeWriter()
/*     */   {
/* 153 */     if (this.follow)
/* 154 */       super.closeWriter();
/*     */   }
/*     */ 
/*     */   private static class SystemOutStream extends OutputStream
/*     */   {
/*     */     public void close()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void flush()
/*     */     {
/* 202 */       System.out.flush();
/*     */     }
/*     */ 
/*     */     public void write(byte[] b) throws IOException {
/* 206 */       System.out.write(b);
/*     */     }
/*     */ 
/*     */     public void write(byte[] b, int off, int len) throws IOException
/*     */     {
/* 211 */       System.out.write(b, off, len);
/*     */     }
/*     */ 
/*     */     public void write(int b) throws IOException {
/* 215 */       System.out.write(b);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SystemErrStream extends OutputStream
/*     */   {
/*     */     public void close()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void flush()
/*     */     {
/* 172 */       System.err.flush();
/*     */     }
/*     */ 
/*     */     public void write(byte[] b) throws IOException {
/* 176 */       System.err.write(b);
/*     */     }
/*     */ 
/*     */     public void write(byte[] b, int off, int len) throws IOException
/*     */     {
/* 181 */       System.err.write(b, off, len);
/*     */     }
/*     */ 
/*     */     public void write(int b) throws IOException {
/* 185 */       System.err.write(b);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.ConsoleAppender
 * JD-Core Version:    0.6.0
 */
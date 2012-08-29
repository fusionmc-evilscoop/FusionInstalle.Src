/*     */ package org.apache.log4j.nt;
/*     */ 
/*     */ import org.apache.log4j.AppenderSkeleton;
/*     */ import org.apache.log4j.Layout;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.apache.log4j.TTCCLayout;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ public class NTEventLogAppender extends AppenderSkeleton
/*     */ {
/*  40 */   private int _handle = 0;
/*     */ 
/*  42 */   private String source = null;
/*  43 */   private String server = null;
/*     */ 
/*  45 */   private static final int FATAL = Level.FATAL.toInt();
/*  46 */   private static final int ERROR = Level.ERROR.toInt();
/*  47 */   private static final int WARN = Level.WARN.toInt();
/*  48 */   private static final int INFO = Level.INFO.toInt();
/*  49 */   private static final int DEBUG = Level.DEBUG.toInt();
/*     */ 
/*     */   public NTEventLogAppender() {
/*  52 */     this(null, null, null);
/*     */   }
/*     */ 
/*     */   public NTEventLogAppender(String source) {
/*  56 */     this(null, source, null);
/*     */   }
/*     */ 
/*     */   public NTEventLogAppender(String server, String source) {
/*  60 */     this(server, source, null);
/*     */   }
/*     */ 
/*     */   public NTEventLogAppender(Layout layout) {
/*  64 */     this(null, null, layout);
/*     */   }
/*     */ 
/*     */   public NTEventLogAppender(String source, Layout layout) {
/*  68 */     this(null, source, layout);
/*     */   }
/*     */ 
/*     */   public NTEventLogAppender(String server, String source, Layout layout) {
/*  72 */     if (source == null) {
/*  73 */       source = "Log4j";
/*     */     }
/*  75 */     if (layout == null)
/*  76 */       this.layout = new TTCCLayout();
/*     */     else {
/*  78 */       this.layout = layout;
/*     */     }
/*     */     try
/*     */     {
/*  82 */       this._handle = registerEventSource(server, source);
/*     */     } catch (Exception e) {
/*  84 */       e.printStackTrace();
/*  85 */       this._handle = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void activateOptions()
/*     */   {
/*  96 */     if (this.source != null)
/*     */       try {
/*  98 */         this._handle = registerEventSource(this.server, this.source);
/*     */       } catch (Exception e) {
/* 100 */         LogLog.error("Could not register event source.", e);
/* 101 */         this._handle = 0;
/*     */       }
/*     */   }
/*     */ 
/*     */   public void append(LoggingEvent event)
/*     */   {
/* 109 */     StringBuffer sbuf = new StringBuffer();
/*     */ 
/* 111 */     sbuf.append(this.layout.format(event));
/* 112 */     if (this.layout.ignoresThrowable()) {
/* 113 */       String[] s = event.getThrowableStrRep();
/* 114 */       if (s != null) {
/* 115 */         int len = s.length;
/* 116 */         for (int i = 0; i < len; i++) {
/* 117 */           sbuf.append(s[i]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 122 */     int nt_category = event.getLevel().toInt();
/*     */ 
/* 128 */     reportEvent(this._handle, sbuf.toString(), nt_category);
/*     */   }
/*     */ 
/*     */   public void finalize()
/*     */   {
/* 134 */     deregisterEventSource(this._handle);
/* 135 */     this._handle = 0;
/*     */   }
/*     */ 
/*     */   public void setSource(String source)
/*     */   {
/* 144 */     this.source = source.trim();
/*     */   }
/*     */ 
/*     */   public String getSource()
/*     */   {
/* 149 */     return this.source;
/*     */   }
/*     */ 
/*     */   public boolean requiresLayout()
/*     */   {
/* 157 */     return true; } 
/*     */   private native int registerEventSource(String paramString1, String paramString2);
/*     */ 
/*     */   private native void reportEvent(int paramInt1, String paramString, int paramInt2);
/*     */ 
/*     */   private native void deregisterEventSource(int paramInt);
/*     */ 
/* 165 */   static { System.loadLibrary("NTEventLogAppender");
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.nt.NTEventLogAppender
 * JD-Core Version:    0.6.0
 */
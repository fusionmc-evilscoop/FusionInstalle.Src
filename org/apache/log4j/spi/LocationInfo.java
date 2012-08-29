/*     */ package org.apache.log4j.spi;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Serializable;
/*     */ import java.io.StringWriter;
/*     */ import org.apache.log4j.Layout;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ 
/*     */ public class LocationInfo
/*     */   implements Serializable
/*     */ {
/*     */   transient String lineNumber;
/*     */   transient String fileName;
/*     */   transient String className;
/*     */   transient String methodName;
/*     */   public String fullInfo;
/*  55 */   private static StringWriter sw = new StringWriter();
/*  56 */   private static PrintWriter pw = new PrintWriter(sw);
/*     */   public static final String NA = "?";
/*     */   static final long serialVersionUID = -1325822038990805636L;
/*  68 */   static boolean inVisualAge = false;
/*     */ 
/*     */   public LocationInfo(Throwable t, String fqnOfCallingClass)
/*     */   {
/*  99 */     if (t == null)
/* 100 */       return;
/*     */     String s;
/* 104 */     synchronized (sw) {
/* 105 */       t.printStackTrace(pw);
/* 106 */       s = sw.toString();
/* 107 */       sw.getBuffer().setLength(0);
/*     */     }
/*     */ 
/* 119 */     int ibegin = s.lastIndexOf(fqnOfCallingClass);
/* 120 */     if (ibegin == -1) {
/* 121 */       return;
/*     */     }
/*     */ 
/* 124 */     ibegin = s.indexOf(Layout.LINE_SEP, ibegin);
/* 125 */     if (ibegin == -1)
/* 126 */       return;
/* 127 */     ibegin += Layout.LINE_SEP_LEN;
/*     */ 
/* 130 */     int iend = s.indexOf(Layout.LINE_SEP, ibegin);
/* 131 */     if (iend == -1) {
/* 132 */       return;
/*     */     }
/*     */ 
/* 136 */     if (!inVisualAge)
/*     */     {
/* 138 */       ibegin = s.lastIndexOf("at ", iend);
/* 139 */       if (ibegin == -1) {
/* 140 */         return;
/*     */       }
/* 142 */       ibegin += 3;
/*     */     }
/*     */ 
/* 145 */     this.fullInfo = s.substring(ibegin, iend);
/*     */   }
/*     */ 
/*     */   public String getClassName()
/*     */   {
/* 154 */     if (this.fullInfo == null) return "?";
/* 155 */     if (this.className == null)
/*     */     {
/* 158 */       int iend = this.fullInfo.lastIndexOf('(');
/* 159 */       if (iend == -1) {
/* 160 */         this.className = "?";
/*     */       } else {
/* 162 */         iend = this.fullInfo.lastIndexOf('.', iend);
/*     */ 
/* 173 */         int ibegin = 0;
/* 174 */         if (inVisualAge) {
/* 175 */           ibegin = this.fullInfo.lastIndexOf(' ', iend) + 1;
/*     */         }
/*     */ 
/* 178 */         if (iend == -1)
/* 179 */           this.className = "?";
/*     */         else
/* 181 */           this.className = this.fullInfo.substring(ibegin, iend);
/*     */       }
/*     */     }
/* 184 */     return this.className;
/*     */   }
/*     */ 
/*     */   public String getFileName()
/*     */   {
/* 194 */     if (this.fullInfo == null) return "?";
/*     */ 
/* 196 */     if (this.fileName == null) {
/* 197 */       int iend = this.fullInfo.lastIndexOf(':');
/* 198 */       if (iend == -1) {
/* 199 */         this.fileName = "?";
/*     */       } else {
/* 201 */         int ibegin = this.fullInfo.lastIndexOf('(', iend - 1);
/* 202 */         this.fileName = this.fullInfo.substring(ibegin + 1, iend);
/*     */       }
/*     */     }
/* 205 */     return this.fileName;
/*     */   }
/*     */ 
/*     */   public String getLineNumber()
/*     */   {
/* 215 */     if (this.fullInfo == null) return "?";
/*     */ 
/* 217 */     if (this.lineNumber == null) {
/* 218 */       int iend = this.fullInfo.lastIndexOf(')');
/* 219 */       int ibegin = this.fullInfo.lastIndexOf(':', iend - 1);
/* 220 */       if (ibegin == -1)
/* 221 */         this.lineNumber = "?";
/*     */       else
/* 223 */         this.lineNumber = this.fullInfo.substring(ibegin + 1, iend);
/*     */     }
/* 225 */     return this.lineNumber;
/*     */   }
/*     */ 
/*     */   public String getMethodName()
/*     */   {
/* 233 */     if (this.fullInfo == null) return "?";
/* 234 */     if (this.methodName == null) {
/* 235 */       int iend = this.fullInfo.lastIndexOf('(');
/* 236 */       int ibegin = this.fullInfo.lastIndexOf('.', iend);
/* 237 */       if (ibegin == -1)
/* 238 */         this.methodName = "?";
/*     */       else
/* 240 */         this.methodName = this.fullInfo.substring(ibegin + 1, iend);
/*     */     }
/* 242 */     return this.methodName;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/*  71 */       Class dummy = Class.forName("com.ibm.uvm.tools.DebugSupport");
/*  72 */       inVisualAge = true;
/*  73 */       LogLog.debug("Detected IBM VisualAge environment.");
/*     */     }
/*     */     catch (Throwable e)
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.spi.LocationInfo
 * JD-Core Version:    0.6.0
 */
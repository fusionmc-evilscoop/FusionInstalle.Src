/*     */ package org.apache.log4j.helpers;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ import org.apache.log4j.Appender;
/*     */ import org.apache.log4j.spi.AppenderAttachable;
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ public class AppenderAttachableImpl
/*     */   implements AppenderAttachable
/*     */ {
/*     */   protected Vector appenderList;
/*     */ 
/*     */   public void addAppender(Appender newAppender)
/*     */   {
/*  44 */     if (newAppender == null) {
/*  45 */       return;
/*     */     }
/*  47 */     if (this.appenderList == null) {
/*  48 */       this.appenderList = new Vector(1);
/*     */     }
/*  50 */     if (!this.appenderList.contains(newAppender))
/*  51 */       this.appenderList.addElement(newAppender);
/*     */   }
/*     */ 
/*     */   public int appendLoopOnAppenders(LoggingEvent event)
/*     */   {
/*  58 */     int size = 0;
/*     */ 
/*  61 */     if (this.appenderList != null) {
/*  62 */       size = this.appenderList.size();
/*  63 */       for (int i = 0; i < size; i++) {
/*  64 */         Appender appender = (Appender)this.appenderList.elementAt(i);
/*  65 */         appender.doAppend(event);
/*     */       }
/*     */     }
/*  68 */     return size;
/*     */   }
/*     */ 
/*     */   public Enumeration getAllAppenders()
/*     */   {
/*  80 */     if (this.appenderList == null) {
/*  81 */       return null;
/*     */     }
/*  83 */     return this.appenderList.elements();
/*     */   }
/*     */ 
/*     */   public Appender getAppender(String name)
/*     */   {
/*  95 */     if ((this.appenderList == null) || (name == null)) {
/*  96 */       return null;
/*     */     }
/*  98 */     int size = this.appenderList.size();
/*     */ 
/* 100 */     for (int i = 0; i < size; i++) {
/* 101 */       Appender appender = (Appender)this.appenderList.elementAt(i);
/* 102 */       if (name.equals(appender.getName()))
/* 103 */         return appender;
/*     */     }
/* 105 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean isAttached(Appender appender)
/*     */   {
/* 116 */     if ((this.appenderList == null) || (appender == null)) {
/* 117 */       return false;
/*     */     }
/* 119 */     int size = this.appenderList.size();
/*     */ 
/* 121 */     for (int i = 0; i < size; i++) {
/* 122 */       Appender a = (Appender)this.appenderList.elementAt(i);
/* 123 */       if (a == appender)
/* 124 */         return true;
/*     */     }
/* 126 */     return false;
/*     */   }
/*     */ 
/*     */   public void removeAllAppenders()
/*     */   {
/* 136 */     if (this.appenderList != null) {
/* 137 */       int len = this.appenderList.size();
/* 138 */       for (int i = 0; i < len; i++) {
/* 139 */         Appender a = (Appender)this.appenderList.elementAt(i);
/* 140 */         a.close();
/*     */       }
/* 142 */       this.appenderList.removeAllElements();
/* 143 */       this.appenderList = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removeAppender(Appender appender)
/*     */   {
/* 153 */     if ((appender == null) || (this.appenderList == null))
/* 154 */       return;
/* 155 */     this.appenderList.removeElement(appender);
/*     */   }
/*     */ 
/*     */   public void removeAppender(String name)
/*     */   {
/* 165 */     if ((name == null) || (this.appenderList == null)) return;
/* 166 */     int size = this.appenderList.size();
/* 167 */     for (int i = 0; i < size; i++)
/* 168 */       if (name.equals(((Appender)this.appenderList.elementAt(i)).getName())) {
/* 169 */         this.appenderList.removeElementAt(i);
/* 170 */         break;
/*     */       }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.helpers.AppenderAttachableImpl
 * JD-Core Version:    0.6.0
 */
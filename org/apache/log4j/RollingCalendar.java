/*     */ package org.apache.log4j;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ class RollingCalendar extends GregorianCalendar
/*     */ {
/* 369 */   int type = -1;
/*     */ 
/*     */   RollingCalendar()
/*     */   {
/*     */   }
/*     */ 
/*     */   RollingCalendar(TimeZone tz, Locale locale) {
/* 376 */     super(tz, locale);
/*     */   }
/*     */ 
/*     */   void setType(int type) {
/* 380 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public long getNextCheckMillis(Date now) {
/* 384 */     return getNextCheckDate(now).getTime();
/*     */   }
/*     */ 
/*     */   public Date getNextCheckDate(Date now) {
/* 388 */     setTime(now);
/*     */ 
/* 390 */     switch (this.type) {
/*     */     case 0:
/* 392 */       set(13, 0);
/* 393 */       set(14, 0);
/* 394 */       add(12, 1);
/* 395 */       break;
/*     */     case 1:
/* 397 */       set(12, 0);
/* 398 */       set(13, 0);
/* 399 */       set(14, 0);
/* 400 */       add(11, 1);
/* 401 */       break;
/*     */     case 2:
/* 403 */       set(12, 0);
/* 404 */       set(13, 0);
/* 405 */       set(14, 0);
/* 406 */       int hour = get(11);
/* 407 */       if (hour < 12) {
/* 408 */         set(11, 12);
/*     */       } else {
/* 410 */         set(11, 0);
/* 411 */         add(5, 1);
/*     */       }
/* 413 */       break;
/*     */     case 3:
/* 415 */       set(11, 0);
/* 416 */       set(12, 0);
/* 417 */       set(13, 0);
/* 418 */       set(14, 0);
/* 419 */       add(5, 1);
/* 420 */       break;
/*     */     case 4:
/* 422 */       set(7, getFirstDayOfWeek());
/* 423 */       set(11, 0);
/* 424 */       set(12, 0);
/* 425 */       set(13, 0);
/* 426 */       set(14, 0);
/* 427 */       add(3, 1);
/* 428 */       break;
/*     */     case 5:
/* 430 */       set(5, 1);
/* 431 */       set(11, 0);
/* 432 */       set(12, 0);
/* 433 */       set(13, 0);
/* 434 */       set(14, 0);
/* 435 */       add(2, 1);
/* 436 */       break;
/*     */     default:
/* 438 */       throw new IllegalStateException("Unknown periodicity type.");
/*     */     }
/* 440 */     return getTime();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.RollingCalendar
 * JD-Core Version:    0.6.0
 */
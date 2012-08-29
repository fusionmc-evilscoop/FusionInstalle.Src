/*     */ package org.apache.log4j.chainsaw;
/*     */ 
/*     */ import org.apache.log4j.Priority;
/*     */ import org.apache.log4j.spi.LocationInfo;
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ class EventDetails
/*     */ {
/*     */   private final long mTimeStamp;
/*     */   private final Priority mPriority;
/*     */   private final String mCategoryName;
/*     */   private final String mNDC;
/*     */   private final String mThreadName;
/*     */   private final String mMessage;
/*     */   private final String[] mThrowableStrRep;
/*     */   private final String mLocationDetails;
/*     */ 
/*     */   EventDetails(long aTimeStamp, Priority aPriority, String aCategoryName, String aNDC, String aThreadName, String aMessage, String[] aThrowableStrRep, String aLocationDetails)
/*     */   {
/*  67 */     this.mTimeStamp = aTimeStamp;
/*  68 */     this.mPriority = aPriority;
/*  69 */     this.mCategoryName = aCategoryName;
/*  70 */     this.mNDC = aNDC;
/*  71 */     this.mThreadName = aThreadName;
/*  72 */     this.mMessage = aMessage;
/*  73 */     this.mThrowableStrRep = aThrowableStrRep;
/*  74 */     this.mLocationDetails = aLocationDetails;
/*     */   }
/*     */ 
/*     */   EventDetails(LoggingEvent aEvent)
/*     */   {
/*  84 */     this(aEvent.timeStamp, aEvent.getLevel(), aEvent.getLoggerName(), aEvent.getNDC(), aEvent.getThreadName(), aEvent.getRenderedMessage(), aEvent.getThrowableStrRep(), aEvent.getLocationInformation() == null ? null : aEvent.getLocationInformation().fullInfo);
/*     */   }
/*     */ 
/*     */   long getTimeStamp()
/*     */   {
/*  97 */     return this.mTimeStamp;
/*     */   }
/*     */ 
/*     */   Priority getPriority()
/*     */   {
/* 102 */     return this.mPriority;
/*     */   }
/*     */ 
/*     */   String getCategoryName()
/*     */   {
/* 107 */     return this.mCategoryName;
/*     */   }
/*     */ 
/*     */   String getNDC()
/*     */   {
/* 112 */     return this.mNDC;
/*     */   }
/*     */ 
/*     */   String getThreadName()
/*     */   {
/* 117 */     return this.mThreadName;
/*     */   }
/*     */ 
/*     */   String getMessage()
/*     */   {
/* 122 */     return this.mMessage;
/*     */   }
/*     */ 
/*     */   String getLocationDetails()
/*     */   {
/* 127 */     return this.mLocationDetails;
/*     */   }
/*     */ 
/*     */   String[] getThrowableStrRep()
/*     */   {
/* 132 */     return this.mThrowableStrRep;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.chainsaw.EventDetails
 * JD-Core Version:    0.6.0
 */
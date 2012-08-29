/*     */ package org.apache.log4j.chainsaw;
/*     */ 
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Level;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ class XMLFileHandler extends DefaultHandler
/*     */ {
/*     */   private static final String TAG_EVENT = "log4j:event";
/*     */   private static final String TAG_MESSAGE = "log4j:message";
/*     */   private static final String TAG_NDC = "log4j:NDC";
/*     */   private static final String TAG_THROWABLE = "log4j:throwable";
/*     */   private static final String TAG_LOCATION_INFO = "log4j:locationInfo";
/*     */   private final MyTableModel mModel;
/*     */   private int mNumEvents;
/*     */   private long mTimeStamp;
/*     */   private Level mLevel;
/*     */   private String mCategoryName;
/*     */   private String mNDC;
/*     */   private String mThreadName;
/*     */   private String mMessage;
/*     */   private String[] mThrowableStrRep;
/*     */   private String mLocationDetails;
/*  67 */   private final StringBuffer mBuf = new StringBuffer();
/*     */ 
/*     */   XMLFileHandler(MyTableModel aModel)
/*     */   {
/*  75 */     this.mModel = aModel;
/*     */   }
/*     */ 
/*     */   public void startDocument()
/*     */     throws SAXException
/*     */   {
/*  82 */     this.mNumEvents = 0;
/*     */   }
/*     */ 
/*     */   public void characters(char[] aChars, int aStart, int aLength)
/*     */   {
/*  87 */     this.mBuf.append(String.valueOf(aChars, aStart, aLength));
/*     */   }
/*     */ 
/*     */   public void endElement(String aNamespaceURI, String aLocalName, String aQName)
/*     */   {
/*  95 */     if ("log4j:event".equals(aQName)) {
/*  96 */       addEvent();
/*  97 */       resetData();
/*  98 */     } else if ("log4j:NDC".equals(aQName)) {
/*  99 */       this.mNDC = this.mBuf.toString();
/* 100 */     } else if ("log4j:message".equals(aQName)) {
/* 101 */       this.mMessage = this.mBuf.toString();
/* 102 */     } else if ("log4j:throwable".equals(aQName)) {
/* 103 */       StringTokenizer st = new StringTokenizer(this.mBuf.toString(), "\n\t");
/*     */ 
/* 105 */       this.mThrowableStrRep = new String[st.countTokens()];
/* 106 */       if (this.mThrowableStrRep.length > 0) {
/* 107 */         this.mThrowableStrRep[0] = st.nextToken();
/* 108 */         for (int i = 1; i < this.mThrowableStrRep.length; i++)
/* 109 */           this.mThrowableStrRep[i] = ("\t" + st.nextToken());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void startElement(String aNamespaceURI, String aLocalName, String aQName, Attributes aAtts)
/*     */   {
/* 121 */     this.mBuf.setLength(0);
/*     */ 
/* 123 */     if ("log4j:event".equals(aQName)) {
/* 124 */       this.mThreadName = aAtts.getValue("thread");
/* 125 */       this.mTimeStamp = Long.parseLong(aAtts.getValue("timestamp"));
/* 126 */       this.mCategoryName = aAtts.getValue("logger");
/* 127 */       this.mLevel = Level.toLevel(aAtts.getValue("level"));
/* 128 */     } else if ("log4j:locationInfo".equals(aQName)) {
/* 129 */       this.mLocationDetails = (aAtts.getValue("class") + "." + aAtts.getValue("method") + "(" + aAtts.getValue("file") + ":" + aAtts.getValue("line") + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   int getNumEvents()
/*     */   {
/* 138 */     return this.mNumEvents;
/*     */   }
/*     */ 
/*     */   private void addEvent()
/*     */   {
/* 147 */     this.mModel.addEvent(new EventDetails(this.mTimeStamp, this.mLevel, this.mCategoryName, this.mNDC, this.mThreadName, this.mMessage, this.mThrowableStrRep, this.mLocationDetails));
/*     */ 
/* 155 */     this.mNumEvents += 1;
/*     */   }
/*     */ 
/*     */   private void resetData()
/*     */   {
/* 160 */     this.mTimeStamp = 0L;
/* 161 */     this.mLevel = null;
/* 162 */     this.mCategoryName = null;
/* 163 */     this.mNDC = null;
/* 164 */     this.mThreadName = null;
/* 165 */     this.mMessage = null;
/* 166 */     this.mThrowableStrRep = null;
/* 167 */     this.mLocationDetails = null;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.chainsaw.XMLFileHandler
 * JD-Core Version:    0.6.0
 */
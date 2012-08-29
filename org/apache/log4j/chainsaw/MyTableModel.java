/*     */ package org.apache.log4j.chainsaw;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeSet;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ import org.apache.log4j.Category;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ class MyTableModel extends AbstractTableModel
/*     */ {
/*  41 */   private static final Logger LOG = Logger.getLogger(MyTableModel.class);
/*     */ 
/*  44 */   private static final Comparator MY_COMP = new Comparator()
/*     */   {
/*     */     public int compare(Object aObj1, Object aObj2)
/*     */     {
/*  48 */       if ((aObj1 == null) && (aObj2 == null))
/*  49 */         return 0;
/*  50 */       if (aObj1 == null)
/*  51 */         return -1;
/*  52 */       if (aObj2 == null) {
/*  53 */         return 1;
/*     */       }
/*     */ 
/*  57 */       EventDetails le1 = (EventDetails)aObj1;
/*  58 */       EventDetails le2 = (EventDetails)aObj2;
/*     */ 
/*  60 */       if (le1.getTimeStamp() < le2.getTimeStamp()) {
/*  61 */         return 1;
/*     */       }
/*     */ 
/*  64 */       return -1;
/*     */     }
/*  44 */   };
/*     */ 
/* 111 */   private static final String[] COL_NAMES = { "Time", "Priority", "Trace", "Category", "NDC", "Message" };
/*     */ 
/* 115 */   private static final EventDetails[] EMPTY_LIST = new EventDetails[0];
/*     */ 
/* 118 */   private static final DateFormat DATE_FORMATTER = DateFormat.getDateTimeInstance(3, 2);
/*     */ 
/* 122 */   private final Object mLock = new Object();
/*     */ 
/* 124 */   private final SortedSet mAllEvents = new TreeSet(MY_COMP);
/*     */ 
/* 126 */   private EventDetails[] mFilteredEvents = EMPTY_LIST;
/*     */ 
/* 128 */   private final List mPendingEvents = new ArrayList();
/*     */ 
/* 130 */   private boolean mPaused = false;
/*     */ 
/* 133 */   private String mThreadFilter = "";
/*     */ 
/* 135 */   private String mMessageFilter = "";
/*     */ 
/* 137 */   private String mNDCFilter = "";
/*     */ 
/* 139 */   private String mCategoryFilter = "";
/*     */ 
/* 141 */   private Priority mPriorityFilter = Priority.DEBUG;
/*     */ 
/*     */   MyTableModel()
/*     */   {
/* 149 */     Thread t = new Thread(new Processor(null));
/* 150 */     t.setDaemon(true);
/* 151 */     t.start();
/*     */   }
/*     */ 
/*     */   public int getRowCount()
/*     */   {
/* 161 */     synchronized (this.mLock) {
/* 162 */       return this.mFilteredEvents.length;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getColumnCount()
/*     */   {
/* 169 */     return COL_NAMES.length;
/*     */   }
/*     */ 
/*     */   public String getColumnName(int aCol)
/*     */   {
/* 175 */     return COL_NAMES[aCol];
/*     */   }
/*     */ 
/*     */   public Class getColumnClass(int aCol)
/*     */   {
/* 181 */     return Object.class;
/*     */   }
/*     */ 
/*     */   public Object getValueAt(int aRow, int aCol)
/*     */   {
/* 186 */     synchronized (this.mLock) {
/* 187 */       EventDetails event = this.mFilteredEvents[aRow];
/*     */       Object localObject2;
/* 189 */       if (aCol == 0)
/* 190 */         return DATE_FORMATTER.format(new Date(event.getTimeStamp()));
/* 191 */       if (aCol == 1)
/* 192 */         return event.getPriority();
/* 193 */       if (aCol == 2) {
/* 194 */         return event.getThrowableStrRep() == null ? Boolean.FALSE : Boolean.TRUE;
/*     */       }
/* 196 */       if (aCol == 3)
/* 197 */         return event.getCategoryName();
/* 198 */       if (aCol == 4) {
/* 199 */         return event.getNDC();
/*     */       }
/* 201 */       return event.getMessage();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setPriorityFilter(Priority aPriority)
/*     */   {
/* 216 */     synchronized (this.mLock) {
/* 217 */       this.mPriorityFilter = aPriority;
/* 218 */       updateFilteredEvents(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setThreadFilter(String aStr)
/*     */   {
/* 228 */     synchronized (this.mLock) {
/* 229 */       this.mThreadFilter = aStr.trim();
/* 230 */       updateFilteredEvents(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setMessageFilter(String aStr)
/*     */   {
/* 240 */     synchronized (this.mLock) {
/* 241 */       this.mMessageFilter = aStr.trim();
/* 242 */       updateFilteredEvents(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setNDCFilter(String aStr)
/*     */   {
/* 252 */     synchronized (this.mLock) {
/* 253 */       this.mNDCFilter = aStr.trim();
/* 254 */       updateFilteredEvents(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setCategoryFilter(String aStr)
/*     */   {
/* 264 */     synchronized (this.mLock) {
/* 265 */       this.mCategoryFilter = aStr.trim();
/* 266 */       updateFilteredEvents(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addEvent(EventDetails aEvent)
/*     */   {
/* 276 */     synchronized (this.mLock) {
/* 277 */       this.mPendingEvents.add(aEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 285 */     synchronized (this.mLock) {
/* 286 */       this.mAllEvents.clear();
/* 287 */       this.mFilteredEvents = new EventDetails[0];
/* 288 */       this.mPendingEvents.clear();
/* 289 */       fireTableDataChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void toggle()
/*     */   {
/* 295 */     synchronized (this.mLock) {
/* 296 */       this.mPaused = (!this.mPaused);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isPaused()
/*     */   {
/* 302 */     synchronized (this.mLock) {
/* 303 */       return this.mPaused;
/*     */     }
/*     */   }
/*     */ 
/*     */   public EventDetails getEventDetails(int aRow)
/*     */   {
/* 314 */     synchronized (this.mLock) {
/* 315 */       return this.mFilteredEvents[aRow];
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateFilteredEvents(boolean aInsertedToFront)
/*     */   {
/* 330 */     long start = System.currentTimeMillis();
/* 331 */     List filtered = new ArrayList();
/* 332 */     int size = this.mAllEvents.size();
/* 333 */     Iterator it = this.mAllEvents.iterator();
/*     */ 
/* 335 */     while (it.hasNext()) {
/* 336 */       EventDetails event = (EventDetails)it.next();
/* 337 */       if (matchFilter(event)) {
/* 338 */         filtered.add(event);
/*     */       }
/*     */     }
/*     */ 
/* 342 */     EventDetails lastFirst = this.mFilteredEvents.length == 0 ? null : this.mFilteredEvents[0];
/*     */ 
/* 345 */     this.mFilteredEvents = ((EventDetails[])filtered.toArray(EMPTY_LIST));
/*     */ 
/* 347 */     if ((aInsertedToFront) && (lastFirst != null)) {
/* 348 */       int index = filtered.indexOf(lastFirst);
/* 349 */       if (index < 1) {
/* 350 */         LOG.warn("In strange state");
/* 351 */         fireTableDataChanged();
/*     */       } else {
/* 353 */         fireTableRowsInserted(0, index - 1);
/*     */       }
/*     */     } else {
/* 356 */       fireTableDataChanged();
/*     */     }
/*     */ 
/* 359 */     long end = System.currentTimeMillis();
/* 360 */     LOG.debug("Total time [ms]: " + (end - start) + " in update, size: " + size);
/*     */   }
/*     */ 
/*     */   private boolean matchFilter(EventDetails aEvent)
/*     */   {
/* 371 */     if ((aEvent.getPriority().isGreaterOrEqual(this.mPriorityFilter)) && (aEvent.getThreadName().indexOf(this.mThreadFilter) >= 0) && (aEvent.getCategoryName().indexOf(this.mCategoryFilter) >= 0) && ((this.mNDCFilter.length() == 0) || ((aEvent.getNDC() != null) && (aEvent.getNDC().indexOf(this.mNDCFilter) >= 0))))
/*     */     {
/* 378 */       String rm = aEvent.getMessage();
/* 379 */       if (rm == null)
/*     */       {
/* 381 */         return this.mMessageFilter.length() == 0;
/*     */       }
/* 383 */       return rm.indexOf(this.mMessageFilter) >= 0;
/*     */     }
/*     */ 
/* 387 */     return false;
/*     */   }
/*     */ 
/*     */   private class Processor
/*     */     implements Runnable
/*     */   {
/*     */     private final MyTableModel this$0;
/*     */ 
/*     */     private Processor()
/*     */     {
/*  72 */       this.this$0 = this$0;
/*     */     }
/*     */ 
/*     */     public void run()
/*     */     {
/*     */       while (true) {
/*     */         try {
/*  79 */           Thread.sleep(1000L);
/*     */         }
/*     */         catch (java.lang.InterruptedException )
/*     */         {
/*     */         }
/*  84 */         synchronized (this.this$0.mLock) {
/*  85 */           if (this.this$0.mPaused)
/*     */           {
/*     */             continue;
/*     */           }
/*  89 */           boolean toHead = true;
/*  90 */           boolean needUpdate = false;
/*  91 */           Iterator it = this.this$0.mPendingEvents.iterator();
/*  92 */           while (it.hasNext()) {
/*  93 */             EventDetails event = (EventDetails)it.next();
/*  94 */             this.this$0.mAllEvents.add(event);
/*  95 */             toHead = (toHead) && (event == this.this$0.mAllEvents.first());
/*  96 */             needUpdate = (needUpdate) || (this.this$0.matchFilter(event));
/*     */           }
/*  98 */           this.this$0.mPendingEvents.clear();
/*     */ 
/* 100 */           if (needUpdate)
/* 101 */             this.this$0.updateFilteredEvents(toHead);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     Processor(MyTableModel.1 x1)
/*     */     {
/*  72 */       this();
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.chainsaw.MyTableModel
 * JD-Core Version:    0.6.0
 */
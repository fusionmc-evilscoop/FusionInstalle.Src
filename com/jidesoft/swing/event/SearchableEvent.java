/*     */ package com.jidesoft.swing.event;
/*     */ 
/*     */ import com.jidesoft.swing.Searchable;
/*     */ import java.awt.AWTEvent;
/*     */ 
/*     */ public class SearchableEvent extends AWTEvent
/*     */ {
/*     */   private String _searchingText;
/*     */   private String _oldSearchingText;
/*     */   private String _matchingText;
/*     */   private Object _matchingObject;
/*     */   public static final int SEARCHABLE_FIRST = 2999;
/*     */   public static final int SEARCHABLE_LAST = 3005;
/*     */   public static final int SEARCHABLE_START = 2999;
/*     */   public static final int SEARCHABLE_END = 3000;
/*     */   public static final int SEARCHABLE_MATCH = 3002;
/*     */   public static final int SEARCHABLE_NOMATCH = 3003;
/*     */   public static final int SEARCHABLE_CHANGE = 3004;
/*     */   public static final int SEARCHABLE_MODEL_CHANGE = 3005;
/*     */ 
/*     */   public SearchableEvent(Searchable source, int id)
/*     */   {
/*  33 */     super(source, id);
/*     */   }
/*     */ 
/*     */   public SearchableEvent(Object source, int id, String searchingText)
/*     */   {
/*  44 */     super(source, id);
/*  45 */     this._searchingText = searchingText;
/*     */   }
/*     */ 
/*     */   public SearchableEvent(Object source, int id, String searchingText, String oldSearchingText) {
/*  49 */     super(source, id);
/*  50 */     this._searchingText = searchingText;
/*  51 */     this._oldSearchingText = oldSearchingText;
/*     */   }
/*     */ 
/*     */   public SearchableEvent(Object source, int id, String searchingText, Object matchingObject, String matchingText) {
/*  55 */     super(source, id);
/*  56 */     this._searchingText = searchingText;
/*  57 */     this._matchingObject = matchingObject;
/*  58 */     this._matchingText = matchingText;
/*     */   }
/*     */ 
/*     */   public String paramString()
/*     */   {
/*     */     String typeStr;
/* 115 */     switch (this.id) {
/*     */     case 2999:
/* 117 */       typeStr = "SEARCHABLE_START: searchingText = \"" + this._searchingText + "\"";
/* 118 */       break;
/*     */     case 3000:
/* 120 */       typeStr = "SEARCHABLE_END";
/* 121 */       break;
/*     */     case 3002:
/* 123 */       typeStr = "SEARCHABLE_MATCH: searchingText = \"" + this._searchingText + "\" matchingText = \"" + this._matchingText + "\"";
/* 124 */       break;
/*     */     case 3003:
/* 126 */       typeStr = "SEARCHABLE_NOMATCH: searchingText = \"" + this._searchingText + "\"";
/* 127 */       break;
/*     */     case 3004:
/* 129 */       typeStr = "SEARCHABLE_CHANGE: searchingText = \"" + this._searchingText + "\" oldSearchingText = \"" + this._oldSearchingText + "\"";
/* 130 */       break;
/*     */     case 3005:
/* 132 */       typeStr = "SEARCHABLE_MODEL";
/* 133 */       break;
/*     */     case 3001:
/*     */     default:
/* 135 */       typeStr = "SEARCHABLE_UNKNOWN";
/*     */     }
/* 137 */     return typeStr;
/*     */   }
/*     */ 
/*     */   public Searchable getSearchable()
/*     */   {
/* 148 */     return (this.source instanceof Searchable) ? (Searchable)this.source : null;
/*     */   }
/*     */ 
/*     */   public String getSearchingText()
/*     */   {
/* 158 */     return this._searchingText;
/*     */   }
/*     */ 
/*     */   public String getOldSearchingText()
/*     */   {
/* 168 */     return this._oldSearchingText;
/*     */   }
/*     */ 
/*     */   public String getMatchingText()
/*     */   {
/* 178 */     return this._matchingText;
/*     */   }
/*     */ 
/*     */   public Object getMatchingObject()
/*     */   {
/* 188 */     return this._matchingObject;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.event.SearchableEvent
 * JD-Core Version:    0.6.0
 */
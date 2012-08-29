/*     */ package com.jidesoft.dialog;
/*     */ 
/*     */ import java.util.EventObject;
/*     */ 
/*     */ public class ButtonEvent extends EventObject
/*     */ {
/*     */   public static final int BUTTON_EVENT_FIRST = 3299;
/*     */   public static final int BUTTON_EVENT_LAST = 3308;
/*     */   public static final int SHOW_BUTTON = 3299;
/*     */   public static final int HIDE_BUTTON = 3300;
/*     */   public static final int ENABLE_BUTTON = 3301;
/*     */   public static final int DISABLE_BUTTON = 3302;
/*     */   public static final int CHANGE_BUTTON_TEXT = 3303;
/*     */   public static final int CHANGE_BUTTON_MNEMONIC = 3304;
/*     */   public static final int CHANGE_BUTTON_TOOLTIP = 3305;
/*     */   public static final int CHANGE_BUTTON_FOCUS = 3306;
/*     */   public static final int SET_DEFAULT_BUTTON = 3307;
/*     */   public static final int CLEAR_DEFAULT_BUTTON = 3308;
/*     */   private int _id;
/*     */   private String _buttonName;
/*     */   private String _userObject;
/*     */ 
/*     */   public ButtonEvent(Object source, int id, String buttonName)
/*     */   {
/*  90 */     super(source);
/*  91 */     this._id = id;
/*  92 */     this._buttonName = buttonName;
/*  93 */     checkParam();
/*     */   }
/*     */ 
/*     */   public ButtonEvent(Object source, int id, String buttonName, String userObject)
/*     */   {
/* 106 */     super(source);
/* 107 */     this._id = id;
/* 108 */     this._buttonName = buttonName;
/* 109 */     this._userObject = userObject;
/* 110 */     checkParam();
/*     */   }
/*     */ 
/*     */   private void checkParam() {
/* 114 */     if ((getID() < 3299) && (getID() > 3308)) {
/* 115 */       throw new IllegalArgumentException(getID() + " is an invalid event id for ButtonEvent");
/*     */     }
/* 117 */     if ((this._buttonName == null) || (this._buttonName.trim().length() == 0)) {
/* 118 */       throw new IllegalArgumentException("buttonName cannot be null or empty");
/*     */     }
/* 120 */     if (((this._userObject == null) || (this._userObject.trim().length() == 0)) && ((getID() == 3303) || (getID() == 3304) || (getID() == 3305)))
/*     */     {
/* 124 */       throw new IllegalArgumentException("userObject cannot be null or empty for " + paramString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getID()
/*     */   {
/* 134 */     return this._id;
/*     */   }
/*     */ 
/*     */   public void setID(int id)
/*     */   {
/* 143 */     this._id = id;
/*     */   }
/*     */ 
/*     */   public String getButtonName()
/*     */   {
/* 152 */     return this._buttonName;
/*     */   }
/*     */ 
/*     */   public void setButtonName(String buttonName)
/*     */   {
/* 161 */     this._buttonName = buttonName;
/*     */   }
/*     */ 
/*     */   public String getUserObject()
/*     */   {
/* 170 */     return this._userObject;
/*     */   }
/*     */ 
/*     */   public void setUserObject(String userObject)
/*     */   {
/* 179 */     this._userObject = userObject;
/*     */   }
/*     */ 
/*     */   public String paramString()
/*     */   {
/*     */     String typeStr;
/* 189 */     switch (getID()) {
/*     */     case 3299:
/* 191 */       typeStr = "SHOW_BUTTON";
/* 192 */       break;
/*     */     case 3300:
/* 194 */       typeStr = "HIDE_BUTTON";
/* 195 */       break;
/*     */     case 3301:
/* 197 */       typeStr = "ENABLE_BUTTON";
/* 198 */       break;
/*     */     case 3302:
/* 200 */       typeStr = "DISABLE_BUTTON";
/* 201 */       break;
/*     */     case 3303:
/* 203 */       typeStr = "CHANGE_BUTTON_TEXT";
/* 204 */       break;
/*     */     case 3304:
/* 206 */       typeStr = "CHANGE_BUTTON_MNEMONIC";
/* 207 */       break;
/*     */     case 3305:
/* 209 */       typeStr = "CHANGE_BUTTON_TOOLTIP";
/* 210 */       break;
/*     */     case 3306:
/* 212 */       typeStr = "CHANGE_BUTTON_FOCUS";
/* 213 */       break;
/*     */     case 3307:
/* 215 */       typeStr = "SET_DEFAULT_BUTTON";
/* 216 */       break;
/*     */     default:
/* 218 */       typeStr = "BUTTON_EVENT_UNKNOWN";
/*     */     }
/* 220 */     return typeStr;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.dialog.ButtonEvent
 * JD-Core Version:    0.6.0
 */
/*     */ package com.jidesoft.validation;
/*     */ 
/*     */ public class ValidationResult
/*     */ {
/*     */   private int _id;
/*     */   private boolean _valid;
/*  23 */   private int _failBehavior = 0;
/*     */   private String _message;
/*     */   public static final int FAIL_BEHAVIOR_REVERT = 0;
/*     */   public static final int FAIL_BEHAVIOR_PERSIST = 1;
/*     */   public static final int FAIL_BEHAVIOR_RESET = 2;
/*  45 */   public static final ValidationResult OK = new ValidationResult(true);
/*     */ 
/*     */   public ValidationResult()
/*     */   {
/*  51 */     this(false);
/*     */   }
/*     */ 
/*     */   public ValidationResult(int id)
/*     */   {
/*  60 */     this(id, false, null);
/*     */   }
/*     */ 
/*     */   public ValidationResult(boolean valid)
/*     */   {
/*  69 */     this(0, valid, null);
/*     */   }
/*     */ 
/*     */   public ValidationResult(int id, String message)
/*     */   {
/*  79 */     this(id, false, message);
/*     */   }
/*     */ 
/*     */   public ValidationResult(int id, boolean valid, String message)
/*     */   {
/*  90 */     this._id = id;
/*  91 */     this._valid = valid;
/*  92 */     this._message = message;
/*     */   }
/*     */ 
/*     */   public ValidationResult(int id, boolean valid, int failBehavoir)
/*     */   {
/* 103 */     this._id = id;
/* 104 */     this._valid = valid;
/* 105 */     this._failBehavior = failBehavoir;
/*     */   }
/*     */ 
/*     */   public ValidationResult(int id, boolean valid, int failBehavoir, String message)
/*     */   {
/* 117 */     this._id = id;
/* 118 */     this._valid = valid;
/* 119 */     this._failBehavior = failBehavoir;
/* 120 */     this._message = message;
/*     */   }
/*     */ 
/*     */   public int getId()
/*     */   {
/* 129 */     return this._id;
/*     */   }
/*     */ 
/*     */   public void setId(int id)
/*     */   {
/* 138 */     this._id = id;
/*     */   }
/*     */ 
/*     */   public boolean isValid()
/*     */   {
/* 147 */     return this._valid;
/*     */   }
/*     */ 
/*     */   public void setValid(boolean valid)
/*     */   {
/* 156 */     this._valid = valid;
/*     */   }
/*     */ 
/*     */   public String getMessage()
/*     */   {
/* 165 */     return this._message;
/*     */   }
/*     */ 
/*     */   public void setMessage(String message)
/*     */   {
/* 174 */     this._message = message;
/*     */   }
/*     */ 
/*     */   public int getFailBehavior()
/*     */   {
/* 183 */     return this._failBehavior;
/*     */   }
/*     */ 
/*     */   public void setFailBehavior(int failBehavior)
/*     */   {
/* 197 */     this._failBehavior = failBehavior;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 202 */     String properties = " id=" + getId() + " message=" + getMessage() + " ";
/*     */ 
/* 204 */     return getClass().getName() + "[" + properties + "]";
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.validation.ValidationResult
 * JD-Core Version:    0.6.0
 */
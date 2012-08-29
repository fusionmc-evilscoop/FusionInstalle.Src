/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.event.ItemEvent;
/*     */ import javax.swing.DefaultButtonModel;
/*     */ 
/*     */ public class DefaultSplitButtonModel extends DefaultButtonModel
/*     */   implements SplitButtonModel
/*     */ {
/*     */   public static final int BUTTON_SELECTED = 64;
/*     */   public static final int BUTTON_ENABLED = 128;
/*     */   public static final int BUTTON_ROLLOVER = 256;
/*     */ 
/*     */   public DefaultSplitButtonModel()
/*     */   {
/*  31 */     setButtonEnabled(true);
/*     */   }
/*     */ 
/*     */   public void setButtonSelected(boolean b)
/*     */   {
/*  40 */     if (isButtonSelected() == b) {
/*  41 */       return;
/*     */     }
/*     */ 
/*  44 */     if (b) {
/*  45 */       this.stateMask |= 64;
/*     */     }
/*     */     else {
/*  48 */       this.stateMask &= -65;
/*     */     }
/*     */ 
/*  51 */     fireItemStateChanged(new ItemEvent(this, 701, this, b ? 1 : 2));
/*     */ 
/*  56 */     fireStateChanged();
/*     */   }
/*     */ 
/*     */   public boolean isButtonSelected()
/*     */   {
/*  66 */     return (this.stateMask & 0x40) != 0;
/*     */   }
/*     */ 
/*     */   public void setButtonEnabled(boolean b)
/*     */   {
/*  75 */     if (isButtonEnabled() == b) {
/*  76 */       return;
/*     */     }
/*     */ 
/*  79 */     if (b) {
/*  80 */       this.stateMask |= 128;
/*     */     }
/*     */     else {
/*  83 */       this.stateMask &= -129;
/*     */     }
/*     */ 
/*  86 */     fireStateChanged();
/*     */   }
/*     */ 
/*     */   public boolean isButtonEnabled()
/*     */   {
/*  96 */     return (this.stateMask & 0x80) != 0;
/*     */   }
/*     */ 
/*     */   public void setButtonRollover(boolean b)
/*     */   {
/* 105 */     if (isButtonRollover() == b) {
/* 106 */       return;
/*     */     }
/*     */ 
/* 109 */     if (b) {
/* 110 */       this.stateMask |= 256;
/*     */     }
/*     */     else {
/* 113 */       this.stateMask &= -257;
/*     */     }
/*     */ 
/* 116 */     fireStateChanged();
/*     */   }
/*     */ 
/*     */   public boolean isButtonRollover()
/*     */   {
/* 125 */     return (this.stateMask & 0x100) != 0;
/*     */   }
/*     */ 
/*     */   public void setRollover(boolean b)
/*     */   {
/* 130 */     super.setRollover(b);
/* 131 */     if (!b)
/* 132 */       setButtonRollover(false);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.DefaultSplitButtonModel
 * JD-Core Version:    0.6.0
 */
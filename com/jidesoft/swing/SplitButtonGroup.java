/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.ButtonModel;
/*     */ 
/*     */ public class SplitButtonGroup extends ButtonGroup
/*     */ {
/*  20 */   ButtonModel selection = null;
/*     */ 
/*     */   public void add(AbstractButton b)
/*     */   {
/*  35 */     if (b == null) {
/*  36 */       return;
/*     */     }
/*  38 */     this.buttons.addElement(b);
/*     */ 
/*  40 */     if ((b instanceof JideSplitButton)) {
/*  41 */       if (((JideSplitButton)b).isButtonSelected()) {
/*  42 */         if (this.selection == null) {
/*  43 */           this.selection = b.getModel();
/*     */         }
/*     */         else {
/*  46 */           ((JideSplitButton)b).setButtonSelected(false);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*  51 */     else if (b.isSelected()) {
/*  52 */       if (this.selection == null) {
/*  53 */         this.selection = b.getModel();
/*     */       }
/*     */       else {
/*  56 */         b.setSelected(false);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  61 */     b.getModel().setGroup(this);
/*     */   }
/*     */ 
/*     */   public void remove(AbstractButton b)
/*     */   {
/*  71 */     if (b == null) {
/*  72 */       return;
/*     */     }
/*  74 */     this.buttons.removeElement(b);
/*  75 */     if (b.getModel() == this.selection) {
/*  76 */       this.selection = null;
/*     */     }
/*  78 */     b.getModel().setGroup(null);
/*     */   }
/*     */ 
/*     */   public void setSelected(ButtonModel m, boolean b)
/*     */   {
/*  91 */     if ((b) && (m != null) && (m != this.selection)) {
/*  92 */       ButtonModel oldSelection = this.selection;
/*  93 */       this.selection = m;
/*  94 */       if (oldSelection != null) {
/*  95 */         if ((oldSelection instanceof SplitButtonModel))
/*  96 */           ((SplitButtonModel)oldSelection).setButtonSelected(false);
/*     */         else
/*  98 */           oldSelection.setSelected(false);
/*     */       }
/* 100 */       if ((m instanceof SplitButtonModel))
/* 101 */         ((SplitButtonModel)m).setButtonSelected(true);
/*     */       else
/* 103 */         m.setSelected(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isSelected(ButtonModel m)
/*     */   {
/* 115 */     return m == this.selection;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.SplitButtonGroup
 * JD-Core Version:    0.6.0
 */
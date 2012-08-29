/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import javax.swing.ComboBoxModel;
/*     */ import javax.swing.JComboBox;
/*     */ 
/*     */ public class AutoCompletionComboBox extends JComboBox
/*     */ {
/*     */   protected AutoCompletion _autoCompletion;
/*     */ 
/*     */   public AutoCompletionComboBox()
/*     */   {
/*  23 */     initComponents();
/*     */   }
/*     */ 
/*     */   public AutoCompletionComboBox(Vector<?> items) {
/*  27 */     super(items);
/*  28 */     initComponents();
/*     */   }
/*     */ 
/*     */   public AutoCompletionComboBox(Object[] items) {
/*  32 */     super(items);
/*  33 */     initComponents();
/*     */   }
/*     */ 
/*     */   public AutoCompletionComboBox(ComboBoxModel aModel) {
/*  37 */     super(aModel);
/*  38 */     initComponents();
/*     */   }
/*     */ 
/*     */   protected void initComponents() {
/*  42 */     setEditable(true);
/*  43 */     this._autoCompletion = createAutoCompletion();
/*     */   }
/*     */ 
/*     */   protected AutoCompletion createAutoCompletion()
/*     */   {
/*  52 */     return new AutoCompletion(this);
/*     */   }
/*     */ 
/*     */   public boolean isStrict()
/*     */   {
/*  61 */     return getAutoCompletion().isStrict();
/*     */   }
/*     */ 
/*     */   public void setStrict(boolean strict)
/*     */   {
/*  72 */     getAutoCompletion().setStrict(strict);
/*     */   }
/*     */ 
/*     */   public boolean isStrictCompletion()
/*     */   {
/*  83 */     return getAutoCompletion().isStrictCompletion();
/*     */   }
/*     */ 
/*     */   public void setStrictCompletion(boolean strictCompletion)
/*     */   {
/*  95 */     getAutoCompletion().setStrictCompletion(strictCompletion);
/*     */   }
/*     */ 
/*     */   public AutoCompletion getAutoCompletion()
/*     */   {
/* 104 */     return this._autoCompletion;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.AutoCompletionComboBox
 * JD-Core Version:    0.6.0
 */
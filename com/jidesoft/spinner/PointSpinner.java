/*     */ package com.jidesoft.spinner;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JFormattedTextField;
/*     */ import javax.swing.JFormattedTextField.AbstractFormatter;
/*     */ import javax.swing.JSpinner;
/*     */ import javax.swing.JSpinner.DefaultEditor;
/*     */ import javax.swing.SpinnerModel;
/*     */ import javax.swing.text.DefaultFormatterFactory;
/*     */ 
/*     */ public class PointSpinner extends JSpinner
/*     */ {
/*     */   public PointSpinner(SpinnerPointModel model)
/*     */   {
/*  30 */     super(model);
/*  31 */     customizeSpinner();
/*     */   }
/*     */ 
/*     */   public PointSpinner()
/*     */   {
/*  40 */     this(new SpinnerPointModel());
/*     */   }
/*     */ 
/*     */   protected JComponent createEditor(SpinnerModel model)
/*     */   {
/*  45 */     return new PointEditor(this);
/*     */   }
/*     */ 
/*     */   private void updateField()
/*     */   {
/*  99 */     JComponent editor = getEditor();
/* 100 */     if (((editor instanceof PointEditor)) && ((getModel() instanceof SpinnerPointModel))) {
/* 101 */       JFormattedTextField ftf = ((PointEditor)editor).getTextField();
/* 102 */       SpinnerPointModel model = (SpinnerPointModel)getModel();
/* 103 */       int comma = ftf.getText().indexOf(',');
/* 104 */       int caret = ftf.getCaretPosition();
/* 105 */       model.setField(caret <= comma ? 0 : 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Point getNextValue()
/*     */   {
/* 111 */     updateField();
/* 112 */     return (Point)super.getNextValue();
/*     */   }
/*     */ 
/*     */   public Point getPreviousValue()
/*     */   {
/* 117 */     updateField();
/* 118 */     return (Point)super.getPreviousValue();
/*     */   }
/*     */ 
/*     */   public Point getValue()
/*     */   {
/* 123 */     return (Point)super.getValue();
/*     */   }
/*     */ 
/*     */   protected void customizeSpinner() {
/* 127 */     SpinnerWheelSupport.installMouseWheelSupport(this);
/*     */   }
/*     */ 
/*     */   public static class PointEditor extends JSpinner.DefaultEditor
/*     */   {
/*     */     public PointEditor(JSpinner spinner)
/*     */     {
/*  57 */       super();
/*  58 */       if (!(spinner.getModel() instanceof SpinnerPointModel)) {
/*  59 */         throw new IllegalArgumentException("model not a SpinnerPointModel");
/*     */       }
/*     */ 
/*  62 */       SpinnerPointModel model = (SpinnerPointModel)spinner.getModel();
/*     */ 
/*  64 */       JFormattedTextField.AbstractFormatter formatter = PointFormatter.getInstance();
/*  65 */       DefaultFormatterFactory factory = new DefaultFormatterFactory(formatter);
/*  66 */       JFormattedTextField ftf = getTextField();
/*  67 */       ftf.setEditable(true);
/*  68 */       ftf.setFormatterFactory(factory);
/*  69 */       ftf.setHorizontalAlignment(4);
/*     */ 
/*  75 */       String min = Integer.toString(-2147483648);
/*  76 */       ftf.setColumns(4 + 2 * min.length());
/*     */ 
/*  78 */       ftf.addPropertyChangeListener("value", new PropertyChangeListener(ftf, model) {
/*     */         public void propertyChange(PropertyChangeEvent evt) {
/*  80 */           String text = this.val$ftf.getText();
/*  81 */           int comma = text.indexOf(',');
/*     */           int number;
/*     */           String digit;
/*     */           int number;
/*  84 */           if (this.val$model.getField() == 0) {
/*  85 */             String digit = text.substring(text.indexOf('(') + 1, comma).trim();
/*  86 */             number = text.indexOf(digit);
/*     */           }
/*     */           else {
/*  89 */             digit = text.substring(comma + 1, text.indexOf(')')).trim();
/*  90 */             number = text.lastIndexOf(digit);
/*     */           }
/*  92 */           this.val$ftf.select(number, number + digit.length());
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.spinner.PointSpinner
 * JD-Core Version:    0.6.0
 */
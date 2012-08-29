/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import javax.swing.Box;
/*    */ import javax.swing.BoxLayout;
/*    */ import javax.swing.JCheckBox;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JRadioButton;
/*    */ import javax.swing.JToggleButton;
/*    */ 
/*    */ public class MultilineToggleButton extends JPanel
/*    */ {
/*    */   private JToggleButton _button;
/*    */   private MultilineLabel _label;
/* 17 */   public static int CHECKBOX_TYPE = 0;
/* 18 */   public static int RADIOBUTTON_TYPE = 1;
/*    */ 
/*    */   public MultilineToggleButton(int type, String labelTxt)
/*    */   {
/* 27 */     if (type == CHECKBOX_TYPE) {
/* 28 */       this._button = new JCheckBox();
/*    */     }
/* 30 */     else if (type == RADIOBUTTON_TYPE) {
/* 31 */       this._button = new JRadioButton();
/*    */     }
/*    */     else {
/* 34 */       this._button = new JToggleButton();
/*    */     }
/* 36 */     this._label = new MultilineLabel(labelTxt);
/* 37 */     build();
/*    */   }
/*    */ 
/*    */   private void build()
/*    */   {
/* 44 */     setLayout(new BoxLayout(this, 2));
/* 45 */     add(this._button);
/* 46 */     add(Box.createHorizontalGlue());
/* 47 */     add(this._label);
/*    */   }
/*    */ 
/*    */   public void setTopAlignment() {
/* 51 */     this._button.setAlignmentY(0.0F);
/* 52 */     this._label.setAlignmentY(0.0F);
/*    */   }
/*    */ 
/*    */   public void setCenterAlignment() {
/* 56 */     this._button.setAlignmentY(0.5F);
/* 57 */     this._label.setAlignmentY(0.5F);
/*    */   }
/*    */ 
/*    */   public JToggleButton getToggleButton()
/*    */   {
/* 66 */     return this._button;
/*    */   }
/*    */ 
/*    */   public void setVisible(boolean b)
/*    */   {
/* 71 */     super.setVisible(b);
/* 72 */     this._label.setVisible(b);
/* 73 */     this._button.setVisible(b);
/*    */   }
/*    */ 
/*    */   public void setEnabled(boolean b)
/*    */   {
/* 78 */     super.setEnabled(b);
/* 79 */     this._button.setEnabled(b);
/* 80 */     this._label.setEnabled(b);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) {
/* 84 */     JFrame frame = new JFrame();
/* 85 */     MultilineToggleButton chkCertA = new MultilineToggleButton(CHECKBOX_TYPE, "Very Long Text Goes Here");
/* 86 */     MultilineToggleButton radioButton = new MultilineToggleButton(RADIOBUTTON_TYPE, "Very Long Text");
/*    */ 
/* 88 */     frame.setLayout(new BoxLayout(frame.getContentPane(), 1));
/* 89 */     frame.add(chkCertA);
/* 90 */     frame.add(radioButton);
/*    */ 
/* 92 */     frame.pack();
/* 93 */     frame.setVisible(true);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.MultilineToggleButton
 * JD-Core Version:    0.6.0
 */
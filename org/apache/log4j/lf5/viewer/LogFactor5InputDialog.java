/*     */ package org.apache.log4j.lf5.viewer;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.KeyAdapter;
/*     */ import java.awt.event.KeyEvent;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.text.JTextComponent;
/*     */ 
/*     */ public class LogFactor5InputDialog extends LogFactor5Dialog
/*     */ {
/*     */   public static final int SIZE = 30;
/*     */   private JTextField _textField;
/*     */ 
/*     */   public LogFactor5InputDialog(JFrame jframe, String title, String label)
/*     */   {
/*  61 */     this(jframe, title, label, 30);
/*     */   }
/*     */ 
/*     */   public LogFactor5InputDialog(JFrame jframe, String title, String label, int size)
/*     */   {
/*  73 */     super(jframe, title, true);
/*     */ 
/*  75 */     JPanel bottom = new JPanel();
/*  76 */     bottom.setLayout(new FlowLayout());
/*     */ 
/*  78 */     JPanel main = new JPanel();
/*  79 */     main.setLayout(new FlowLayout());
/*  80 */     main.add(new JLabel(label));
/*  81 */     this._textField = new JTextField(size);
/*  82 */     main.add(this._textField);
/*     */ 
/*  84 */     addKeyListener(new KeyAdapter() {
/*     */       public void keyPressed(KeyEvent e) {
/*  86 */         if (e.getKeyCode() == 10)
/*  87 */           LogFactor5InputDialog.this.hide();
/*     */       }
/*     */     });
/*  92 */     JButton ok = new JButton("Ok");
/*  93 */     ok.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  95 */         LogFactor5InputDialog.this.hide();
/*     */       }
/*     */     });
/*  99 */     JButton cancel = new JButton("Cancel");
/* 100 */     cancel.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 102 */         LogFactor5InputDialog.this.hide();
/*     */ 
/* 106 */         LogFactor5InputDialog.this._textField.setText("");
/*     */       }
/*     */     });
/* 110 */     bottom.add(ok);
/* 111 */     bottom.add(cancel);
/* 112 */     getContentPane().add(main, "Center");
/* 113 */     getContentPane().add(bottom, "South");
/* 114 */     pack();
/* 115 */     centerWindow(this);
/* 116 */     show();
/*     */   }
/*     */ 
/*     */   public String getText()
/*     */   {
/* 123 */     String s = this._textField.getText();
/*     */ 
/* 125 */     if ((s != null) && (s.trim().length() == 0)) {
/* 126 */       return null;
/*     */     }
/*     */ 
/* 129 */     return s;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.LogFactor5InputDialog
 * JD-Core Version:    0.6.0
 */
/*    */ package org.apache.log4j.lf5.viewer;
/*    */ 
/*    */ import java.awt.Container;
/*    */ import java.awt.Dialog;
/*    */ import java.awt.FlowLayout;
/*    */ import java.awt.GridBagLayout;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.AbstractButton;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JDialog;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ public class LogFactor5ErrorDialog extends LogFactor5Dialog
/*    */ {
/*    */   public LogFactor5ErrorDialog(JFrame jframe, String message)
/*    */   {
/* 49 */     super(jframe, "Error", true);
/*    */ 
/* 51 */     JButton ok = new JButton("Ok");
/* 52 */     ok.addActionListener(new ActionListener() {
/*    */       public void actionPerformed(ActionEvent e) {
/* 54 */         LogFactor5ErrorDialog.this.hide();
/*    */       }
/*    */     });
/* 58 */     JPanel bottom = new JPanel();
/* 59 */     bottom.setLayout(new FlowLayout());
/* 60 */     bottom.add(ok);
/*    */ 
/* 62 */     JPanel main = new JPanel();
/* 63 */     main.setLayout(new GridBagLayout());
/* 64 */     wrapStringOnPanel(message, main);
/*    */ 
/* 66 */     getContentPane().add(main, "Center");
/* 67 */     getContentPane().add(bottom, "South");
/* 68 */     show();
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.LogFactor5ErrorDialog
 * JD-Core Version:    0.6.0
 */
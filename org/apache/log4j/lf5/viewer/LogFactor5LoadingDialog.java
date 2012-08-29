/*    */ package org.apache.log4j.lf5.viewer;
/*    */ 
/*    */ import java.awt.Container;
/*    */ import java.awt.FlowLayout;
/*    */ import java.awt.GridBagLayout;
/*    */ import javax.swing.JDialog;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ public class LogFactor5LoadingDialog extends LogFactor5Dialog
/*    */ {
/*    */   public LogFactor5LoadingDialog(JFrame jframe, String message)
/*    */   {
/* 48 */     super(jframe, "LogFactor5", false);
/*    */ 
/* 50 */     JPanel bottom = new JPanel();
/* 51 */     bottom.setLayout(new FlowLayout());
/*    */ 
/* 53 */     JPanel main = new JPanel();
/* 54 */     main.setLayout(new GridBagLayout());
/* 55 */     wrapStringOnPanel(message, main);
/*    */ 
/* 57 */     getContentPane().add(main, "Center");
/* 58 */     getContentPane().add(bottom, "South");
/* 59 */     show();
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.LogFactor5LoadingDialog
 * JD-Core Version:    0.6.0
 */
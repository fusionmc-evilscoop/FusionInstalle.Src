/*    */ package com.jidesoft.plaf.xerto;
/*    */ 
/*    */ import com.jidesoft.plaf.basic.BasicJideButtonUI;
/*    */ import com.sun.java.swing.plaf.windows.WindowsButtonUI;
/*    */ import java.awt.Graphics;
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ 
/*    */ public class XertoJideButtonUI extends BasicJideButtonUI
/*    */ {
/* 18 */   private static WindowsButtonUI _buttonUI = new WindowsButtonUI();
/*    */ 
/*    */   public static ComponentUI createUI(JComponent c) {
/* 21 */     return new XertoJideButtonUI();
/*    */   }
/*    */ 
/*    */   public void installUI(JComponent c)
/*    */   {
/* 29 */     _buttonUI.installUI(c);
/* 30 */     if ((c instanceof JButton)) {
/* 31 */       c.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
/* 32 */       ((JButton)c).setRolloverEnabled(true);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void uninstallUI(JComponent c)
/*    */   {
/* 38 */     _buttonUI.uninstallUI(c);
/*    */   }
/*    */ 
/*    */   public void paint(Graphics g, JComponent c)
/*    */   {
/* 43 */     _buttonUI.paint(g, c);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.xerto.XertoJideButtonUI
 * JD-Core Version:    0.6.0
 */
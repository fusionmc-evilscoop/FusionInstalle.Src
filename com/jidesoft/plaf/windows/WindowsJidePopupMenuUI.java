/*    */ package com.jidesoft.plaf.windows;
/*    */ 
/*    */ import com.jidesoft.plaf.basic.BasicJidePopupMenuUI;
/*    */ import com.sun.java.swing.plaf.windows.WindowsPopupMenuUI;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JPopupMenu;
/*    */ import javax.swing.Popup;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ 
/*    */ public class WindowsJidePopupMenuUI extends WindowsPopupMenuUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent c)
/*    */   {
/* 20 */     return new WindowsJidePopupMenuUI();
/*    */   }
/*    */ 
/*    */   public Popup getPopup(JPopupMenu popupMenu, int x, int y)
/*    */   {
/* 25 */     Popup popup = BasicJidePopupMenuUI.addScrollPaneIfNecessary(popupMenu, x, y);
/* 26 */     return popup == null ? super.getPopup(popupMenu, x, y) : popup;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.windows.WindowsJidePopupMenuUI
 * JD-Core Version:    0.6.0
 */
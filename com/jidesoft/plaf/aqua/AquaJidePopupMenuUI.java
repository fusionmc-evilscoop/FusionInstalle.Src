/*    */ package com.jidesoft.plaf.aqua;
/*    */ 
/*    */ import apple.laf.AquaPopupMenuUI;
/*    */ import com.jidesoft.plaf.basic.BasicJidePopupMenuUI;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JPopupMenu;
/*    */ import javax.swing.Popup;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ 
/*    */ public class AquaJidePopupMenuUI extends AquaPopupMenuUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent c)
/*    */   {
/* 20 */     return new AquaJidePopupMenuUI();
/*    */   }
/*    */ 
/*    */   public Popup getPopup(JPopupMenu popupMenu, int x, int y)
/*    */   {
/* 25 */     Popup popup = BasicJidePopupMenuUI.addScrollPaneIfNecessary(popupMenu, x, y);
/* 26 */     return popup == null ? super.getPopup(popupMenu, x, y) : popup;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.aqua.AquaJidePopupMenuUI
 * JD-Core Version:    0.6.0
 */
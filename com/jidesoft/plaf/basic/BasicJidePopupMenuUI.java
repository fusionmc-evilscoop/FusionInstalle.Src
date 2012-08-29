/*    */ package com.jidesoft.plaf.basic;
/*    */ 
/*    */ import com.jidesoft.swing.JidePopupMenu;
/*    */ import com.jidesoft.swing.SimpleScrollPane;
/*    */ import com.jidesoft.utils.SystemInfo;
/*    */ import java.awt.Component;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Point;
/*    */ import java.awt.Rectangle;
/*    */ import javax.swing.AbstractButton;
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.BoxLayout;
/*    */ import javax.swing.ButtonModel;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JMenuItem;
/*    */ import javax.swing.JPopupMenu;
/*    */ import javax.swing.JViewport;
/*    */ import javax.swing.Popup;
/*    */ import javax.swing.PopupFactory;
/*    */ import javax.swing.event.ChangeEvent;
/*    */ import javax.swing.event.ChangeListener;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ import javax.swing.plaf.basic.BasicPopupMenuUI;
/*    */ import javax.swing.plaf.basic.DefaultMenuLayout;
/*    */ 
/*    */ public class BasicJidePopupMenuUI extends BasicPopupMenuUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent c)
/*    */   {
/* 27 */     return new BasicJidePopupMenuUI();
/*    */   }
/*    */ 
/*    */   public Popup getPopup(JPopupMenu popupMenu, int x, int y)
/*    */   {
/* 32 */     Popup popup = addScrollPaneIfNecessary(popupMenu, x, y);
/* 33 */     return popup == null ? super.getPopup(popupMenu, x, y) : popup;
/*    */   }
/*    */ 
/*    */   public static Popup addScrollPaneIfNecessary(JPopupMenu popupMenu, int x, int y)
/*    */   {
/* 45 */     SimpleScrollPane contents = new SimpleScrollPane(popupMenu, 20, 31);
/* 46 */     if (((popupMenu instanceof JidePopupMenu)) && (popupMenu.getPreferredSize().height != ((JidePopupMenu)popupMenu).getPreferredScrollableViewportSize().height)) {
/* 47 */       if (((popupMenu.getLayout() instanceof DefaultMenuLayout)) && (SystemInfo.isJdk6Above())) {
/* 48 */         popupMenu.setLayout(new BoxLayout(popupMenu, ((DefaultMenuLayout)popupMenu.getLayout()).getAxis()));
/*    */       }
/* 50 */       PopupFactory popupFactory = PopupFactory.getSharedInstance();
/* 51 */       contents.getScrollUpButton().setOpaque(true);
/* 52 */       contents.getScrollDownButton().setOpaque(true);
/* 53 */       contents.setBorder(BorderFactory.createEmptyBorder());
/* 54 */       Component[] components = popupMenu.getComponents();
/* 55 */       for (Component component : components) {
/* 56 */         if ((component instanceof JMenuItem)) {
/* 57 */           ((JMenuItem)component).addChangeListener(new ChangeListener(popupMenu, contents) {
/*    */             public void stateChanged(ChangeEvent e) {
/* 59 */               if (((e.getSource() instanceof JMenuItem)) && 
/* 60 */                 (((JMenuItem)e.getSource()).getModel().isArmed())) {
/* 61 */                 this.val$popupMenu.scrollRectToVisible(((JMenuItem)e.getSource()).getBounds());
/* 62 */                 Point position = this.val$contents.getViewport().getViewPosition();
/* 63 */                 this.val$contents.getScrollUpButton().setEnabled(position.y > 2);
/* 64 */                 this.val$contents.getScrollDownButton().setEnabled(position.y < this.val$contents.getViewport().getViewSize().height - this.val$contents.getViewport().getViewRect().height - 2);
/*    */               }
/*    */             }
/*    */           });
/*    */         }
/*    */       }
/* 71 */       return popupFactory.getPopup(popupMenu.getInvoker(), contents, x, y);
/*    */     }
/*    */ 
/* 74 */     return null;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.BasicJidePopupMenuUI
 * JD-Core Version:    0.6.0
 */
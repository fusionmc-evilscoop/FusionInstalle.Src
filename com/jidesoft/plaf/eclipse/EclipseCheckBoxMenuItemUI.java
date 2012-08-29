/*    */ package com.jidesoft.plaf.eclipse;
/*    */ 
/*    */ import java.awt.Point;
/*    */ import java.awt.event.MouseEvent;
/*    */ import javax.swing.ButtonModel;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JMenuItem;
/*    */ import javax.swing.MenuElement;
/*    */ import javax.swing.MenuSelectionManager;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ 
/*    */ public class EclipseCheckBoxMenuItemUI extends EclipseMenuItemUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent c)
/*    */   {
/* 20 */     return new EclipseCheckBoxMenuItemUI();
/*    */   }
/*    */ 
/*    */   protected String getPropertyPrefix()
/*    */   {
/* 25 */     return "CheckBoxMenuItem";
/*    */   }
/*    */ 
/*    */   public void processMouseEvent(JMenuItem item, MouseEvent e, MenuElement[] path, MenuSelectionManager manager) {
/* 29 */     Point p = e.getPoint();
/* 30 */     if ((p.x >= 0) && (p.x < item.getWidth()) && (p.y >= 0) && (p.y < item.getHeight()))
/*    */     {
/* 32 */       if (e.getID() == 502) {
/* 33 */         manager.clearSelectedPath();
/* 34 */         item.doClick(0);
/*    */       }
/*    */       else {
/* 37 */         manager.setSelectedPath(path);
/*    */       }
/* 39 */     } else if (item.getModel().isArmed()) {
/* 40 */       MenuElement[] newPath = new MenuElement[path.length - 1];
/*    */ 
/* 42 */       int i = 0; for (int c = path.length - 1; i < c; i++)
/* 43 */         newPath[i] = path[i];
/* 44 */       manager.setSelectedPath(newPath);
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.eclipse.EclipseCheckBoxMenuItemUI
 * JD-Core Version:    0.6.0
 */
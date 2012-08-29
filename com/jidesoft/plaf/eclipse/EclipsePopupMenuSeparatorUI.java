/*    */ package com.jidesoft.plaf.eclipse;
/*    */ 
/*    */ import com.jidesoft.plaf.UIDefaultsLookup;
/*    */ import java.awt.Color;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JPopupMenu;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ import javax.swing.plaf.basic.BasicSeparatorUI;
/*    */ 
/*    */ public class EclipsePopupMenuSeparatorUI extends BasicSeparatorUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent c)
/*    */   {
/* 21 */     return new EclipsePopupMenuSeparatorUI();
/*    */   }
/*    */ 
/*    */   public void paint(Graphics g, JComponent c)
/*    */   {
/* 26 */     if (!(c.getParent() instanceof JPopupMenu)) {
/* 27 */       super.paint(g, c);
/*    */     }
/*    */ 
/* 30 */     Dimension s = c.getSize();
/*    */ 
/* 32 */     Color foreground = UIDefaultsLookup.getColor("PopupMenuSeparator.foreground");
/* 33 */     Color background = UIDefaultsLookup.getColor("PopupMenuSeparator.background");
/*    */ 
/* 35 */     g.setColor(background);
/* 36 */     g.drawLine(1, 0, s.width - 2, 0);
/*    */ 
/* 38 */     g.setColor(foreground);
/* 39 */     g.drawLine(1, 1, s.width - 2, 1);
/*    */   }
/*    */ 
/*    */   public Dimension getPreferredSize(JComponent c)
/*    */   {
/* 44 */     return new Dimension(0, 2);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.eclipse.EclipsePopupMenuSeparatorUI
 * JD-Core Version:    0.6.0
 */
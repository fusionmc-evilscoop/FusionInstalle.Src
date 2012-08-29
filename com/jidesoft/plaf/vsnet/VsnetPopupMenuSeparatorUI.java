/*    */ package com.jidesoft.plaf.vsnet;
/*    */ 
/*    */ import com.jidesoft.plaf.UIDefaultsLookup;
/*    */ import com.jidesoft.plaf.basic.ThemePainter;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Rectangle;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JPopupMenu;
/*    */ import javax.swing.JSeparator;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ import javax.swing.plaf.basic.BasicPopupMenuSeparatorUI;
/*    */ 
/*    */ public class VsnetPopupMenuSeparatorUI extends BasicPopupMenuSeparatorUI
/*    */ {
/*    */   public static final int HEIGHT = 3;
/*    */   private ThemePainter _painter;
/*    */ 
/*    */   public static ComponentUI createUI(JComponent c)
/*    */   {
/* 25 */     return new VsnetPopupMenuSeparatorUI();
/*    */   }
/*    */ 
/*    */   protected void installDefaults(JSeparator s)
/*    */   {
/* 30 */     this._painter = ((ThemePainter)UIDefaultsLookup.get("Theme.painter"));
/* 31 */     super.installDefaults(s);
/*    */   }
/*    */ 
/*    */   protected void uninstallDefaults(JSeparator s)
/*    */   {
/* 36 */     super.uninstallDefaults(s);
/* 37 */     this._painter = null;
/*    */   }
/*    */ 
/*    */   public ThemePainter getPainter() {
/* 41 */     return this._painter;
/*    */   }
/*    */ 
/*    */   public void paint(Graphics g, JComponent c)
/*    */   {
/* 46 */     if (!(c.getParent() instanceof JPopupMenu)) {
/* 47 */       super.paint(g, c);
/* 48 */       return;
/*    */     }
/*    */ 
/* 51 */     getPainter().paintPopupMenuSeparator(c, g, new Rectangle(0, 0, c.getWidth(), c.getHeight()), 0, 0);
/*    */   }
/*    */ 
/*    */   public Dimension getPreferredSize(JComponent c)
/*    */   {
/* 56 */     return new Dimension(0, 3);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.vsnet.VsnetPopupMenuSeparatorUI
 * JD-Core Version:    0.6.0
 */
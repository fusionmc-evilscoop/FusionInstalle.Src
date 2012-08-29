/*    */ package com.jidesoft.plaf.eclipse;
/*    */ 
/*    */ import com.jidesoft.plaf.UIDefaultsLookup;
/*    */ import com.jidesoft.plaf.basic.BasicGripperUI;
/*    */ import com.jidesoft.plaf.basic.Painter;
/*    */ import com.jidesoft.plaf.basic.ThemePainter;
/*    */ import com.jidesoft.swing.Gripper;
/*    */ import com.jidesoft.swing.JideSwingUtilities;
/*    */ import java.awt.Color;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Rectangle;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ 
/*    */ public class EclipseGripperUI extends BasicGripperUI
/*    */ {
/*    */   protected Color _shadowColor;
/*    */   protected Color _darkShadowColor;
/*    */   protected Color _highlight;
/*    */   protected Color _lightHighlightColor;
/*    */ 
/*    */   public static ComponentUI createUI(JComponent c)
/*    */   {
/* 27 */     return new EclipseGripperUI();
/*    */   }
/*    */ 
/*    */   protected void installDefaults(Gripper s)
/*    */   {
/* 32 */     this._shadowColor = UIDefaultsLookup.getColor("controlShadow");
/* 33 */     this._darkShadowColor = UIDefaultsLookup.getColor("controlDkShadow");
/* 34 */     this._highlight = UIDefaultsLookup.getColor("controlHighlight");
/* 35 */     this._lightHighlightColor = UIDefaultsLookup.getColor("controlLtHighlight");
/* 36 */     super.installDefaults(s);
/*    */   }
/*    */ 
/*    */   protected void uninstallDefaults(Gripper s)
/*    */   {
/* 41 */     this._shadowColor = null;
/* 42 */     this._highlight = null;
/* 43 */     this._lightHighlightColor = null;
/* 44 */     this._darkShadowColor = null;
/* 45 */     super.uninstallDefaults(s);
/*    */   }
/*    */ 
/*    */   public void paint(Graphics g, JComponent c)
/*    */   {
/* 50 */     if (this._gripperPainter == null) {
/* 51 */       getPainter().paintGripper(c, g, new Rectangle(0, 0, c.getWidth(), c.getHeight()), JideSwingUtilities.getOrientationOf(c), 0);
/*    */     }
/*    */     else
/* 54 */       this._gripperPainter.paint(c, g, new Rectangle(0, 0, c.getWidth(), c.getHeight()), JideSwingUtilities.getOrientationOf(c), 0);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.eclipse.EclipseGripperUI
 * JD-Core Version:    0.6.0
 */
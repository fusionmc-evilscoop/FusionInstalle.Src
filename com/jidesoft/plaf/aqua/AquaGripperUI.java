/*    */ package com.jidesoft.plaf.aqua;
/*    */ 
/*    */ import com.jidesoft.plaf.basic.BasicGripperUI;
/*    */ import com.jidesoft.plaf.basic.ThemePainter;
/*    */ import com.jidesoft.swing.Gripper;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Rectangle;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ 
/*    */ public class AquaGripperUI extends BasicGripperUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent c)
/*    */   {
/* 15 */     return new AquaGripperUI();
/*    */   }
/*    */ 
/*    */   public void paint(Graphics g, JComponent c)
/*    */   {
/* 20 */     Gripper gripper = (Gripper)c;
/* 21 */     paintBackground(g, gripper);
/* 22 */     getPainter().paintGripper(c, g, new Rectangle(0, 0, c.getWidth(), c.getHeight()), gripper.getOrientation(), 0);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.aqua.AquaGripperUI
 * JD-Core Version:    0.6.0
 */
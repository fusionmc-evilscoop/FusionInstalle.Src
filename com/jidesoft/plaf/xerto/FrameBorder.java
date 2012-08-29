/*    */ package com.jidesoft.plaf.xerto;
/*    */ 
/*    */ import com.jidesoft.plaf.UIDefaultsLookup;
/*    */ import java.awt.Component;
/*    */ import java.awt.Container;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Insets;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.border.Border;
/*    */ import javax.swing.plaf.UIResource;
/*    */ 
/*    */ public class FrameBorder
/*    */   implements Border, UIResource
/*    */ {
/* 18 */   private static final Insets INSETS = new Insets(1, 1, 3, 3);
/*    */ 
/*    */   public Insets getBorderInsets(Component c)
/*    */   {
/* 26 */     return INSETS;
/*    */   }
/*    */ 
/*    */   public boolean isBorderOpaque()
/*    */   {
/* 34 */     return false;
/*    */   }
/*    */ 
/*    */   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
/*    */   {
/* 48 */     g.setColor(XertoUtils.getFrameBorderColor());
/* 49 */     g.drawLine(x, y, x + width - 3, y);
/* 50 */     g.drawLine(x, y, x, y + height - 3);
/* 51 */     g.drawLine(x + width - 3, y, x + width - 3, y + height - 3);
/* 52 */     g.drawLine(x, y + height - 3, x + width - 3, y + height - 3);
/* 53 */     g.setColor(XertoUtils.getControlColor());
/* 54 */     g.fillRect(x + width - 2, y, 2, 2);
/* 55 */     g.fillRect(x, y + height - 2, 2, 2);
/* 56 */     g.setColor(XertoUtils.getControlMidShadowColor());
/* 57 */     g.drawLine(x + width - 2, y + 1, x + width - 2, y + height - 2);
/* 58 */     g.drawLine(x + 1, y + height - 2, x + width - 2, y + height - 2);
/* 59 */     g.setColor(XertoUtils.getControlLightShadowColor());
/* 60 */     g.drawLine(x + width - 1, y + 2, x + width - 1, y + height - 1);
/*    */ 
/* 63 */     if (("DockableFrameUI".equals(((JComponent)c).getUIClassID())) && (c.getParent().getComponentCount() > 1)) {
/* 64 */       g.setColor(UIDefaultsLookup.getColor("JideTabbedPane.selectedTabBackground"));
/* 65 */       g.drawLine(x + 2, y + height - 1, x + width - 2, y + height - 1);
/*    */     }
/*    */     else {
/* 68 */       g.setColor(XertoUtils.getControlLightShadowColor());
/* 69 */       g.drawLine(x + 2, y + height - 1, x + width - 1, y + height - 1);
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.xerto.FrameBorder
 * JD-Core Version:    0.6.0
 */
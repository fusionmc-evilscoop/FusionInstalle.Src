/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import java.util.Vector;
/*    */ import javax.swing.ComboBoxModel;
/*    */ import javax.swing.JComboBox;
/*    */ 
/*    */ public class OverlayComboBox extends JComboBox
/*    */ {
/*    */   public OverlayComboBox()
/*    */   {
/*    */   }
/*    */ 
/*    */   public OverlayComboBox(Vector<?> items)
/*    */   {
/* 17 */     super(items);
/*    */   }
/*    */ 
/*    */   public OverlayComboBox(Object[] items) {
/* 21 */     super(items);
/*    */   }
/*    */ 
/*    */   public OverlayComboBox(ComboBoxModel aModel) {
/* 25 */     super(aModel);
/*    */   }
/*    */ 
/*    */   public void repaint(long tm, int x, int y, int width, int height)
/*    */   {
/* 30 */     super.repaint(tm, x, y, width, height);
/* 31 */     OverlayableUtils.repaintOverlayable(this);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.OverlayComboBox
 * JD-Core Version:    0.6.0
 */
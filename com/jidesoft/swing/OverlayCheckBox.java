/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import javax.swing.Action;
/*    */ import javax.swing.Icon;
/*    */ import javax.swing.JCheckBox;
/*    */ 
/*    */ public class OverlayCheckBox extends JCheckBox
/*    */ {
/*    */   public OverlayCheckBox()
/*    */   {
/*    */   }
/*    */ 
/*    */   public OverlayCheckBox(Icon icon)
/*    */   {
/* 16 */     super(icon);
/*    */   }
/*    */ 
/*    */   public OverlayCheckBox(Icon icon, boolean selected) {
/* 20 */     super(icon, selected);
/*    */   }
/*    */ 
/*    */   public OverlayCheckBox(String text) {
/* 24 */     super(text);
/*    */   }
/*    */ 
/*    */   public OverlayCheckBox(Action a) {
/* 28 */     super(a);
/*    */   }
/*    */ 
/*    */   public OverlayCheckBox(String text, boolean selected) {
/* 32 */     super(text, selected);
/*    */   }
/*    */ 
/*    */   public OverlayCheckBox(String text, Icon icon) {
/* 36 */     super(text, icon);
/*    */   }
/*    */ 
/*    */   public OverlayCheckBox(String text, Icon icon, boolean selected) {
/* 40 */     super(text, icon, selected);
/*    */   }
/*    */ 
/*    */   public void repaint(long tm, int x, int y, int width, int height)
/*    */   {
/* 45 */     super.repaint(tm, x, y, width, height);
/* 46 */     OverlayableUtils.repaintOverlayable(this);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.OverlayCheckBox
 * JD-Core Version:    0.6.0
 */
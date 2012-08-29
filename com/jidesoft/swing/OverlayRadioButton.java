/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import javax.swing.Action;
/*    */ import javax.swing.Icon;
/*    */ import javax.swing.JRadioButton;
/*    */ 
/*    */ public class OverlayRadioButton extends JRadioButton
/*    */ {
/*    */   public OverlayRadioButton()
/*    */   {
/*    */   }
/*    */ 
/*    */   public OverlayRadioButton(Icon icon)
/*    */   {
/* 17 */     super(icon);
/*    */   }
/*    */ 
/*    */   public OverlayRadioButton(Action a) {
/* 21 */     super(a);
/*    */   }
/*    */ 
/*    */   public OverlayRadioButton(Icon icon, boolean selected) {
/* 25 */     super(icon, selected);
/*    */   }
/*    */ 
/*    */   public OverlayRadioButton(String text) {
/* 29 */     super(text);
/*    */   }
/*    */ 
/*    */   public OverlayRadioButton(String text, boolean selected) {
/* 33 */     super(text, selected);
/*    */   }
/*    */ 
/*    */   public OverlayRadioButton(String text, Icon icon) {
/* 37 */     super(text, icon);
/*    */   }
/*    */ 
/*    */   public OverlayRadioButton(String text, Icon icon, boolean selected) {
/* 41 */     super(text, icon, selected);
/*    */   }
/*    */ 
/*    */   public void repaint(long tm, int x, int y, int width, int height)
/*    */   {
/* 46 */     super.repaint(tm, x, y, width, height);
/* 47 */     OverlayableUtils.repaintOverlayable(this);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.OverlayRadioButton
 * JD-Core Version:    0.6.0
 */
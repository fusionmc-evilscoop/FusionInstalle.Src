/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import javax.swing.JTextField;
/*    */ import javax.swing.text.Document;
/*    */ 
/*    */ public class OverlayTextField extends JTextField
/*    */ {
/*    */   public OverlayTextField()
/*    */   {
/*    */   }
/*    */ 
/*    */   public OverlayTextField(String text)
/*    */   {
/* 17 */     super(text);
/*    */   }
/*    */ 
/*    */   public OverlayTextField(int columns) {
/* 21 */     super(columns);
/*    */   }
/*    */ 
/*    */   public OverlayTextField(String text, int columns) {
/* 25 */     super(text, columns);
/*    */   }
/*    */ 
/*    */   public OverlayTextField(Document doc, String text, int columns) {
/* 29 */     super(doc, text, columns);
/*    */   }
/*    */ 
/*    */   public void repaint(long tm, int x, int y, int width, int height)
/*    */   {
/* 34 */     super.repaint(tm, x, y, width, height);
/* 35 */     OverlayableUtils.repaintOverlayable(this);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.OverlayTextField
 * JD-Core Version:    0.6.0
 */
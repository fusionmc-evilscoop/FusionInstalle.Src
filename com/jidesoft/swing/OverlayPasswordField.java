/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import javax.swing.JPasswordField;
/*    */ import javax.swing.text.Document;
/*    */ 
/*    */ public class OverlayPasswordField extends JPasswordField
/*    */ {
/*    */   public OverlayPasswordField()
/*    */   {
/*    */   }
/*    */ 
/*    */   public OverlayPasswordField(Document doc, String txt, int columns)
/*    */   {
/* 20 */     super(doc, txt, columns);
/*    */   }
/*    */ 
/*    */   public OverlayPasswordField(int columns) {
/* 24 */     super(columns);
/*    */   }
/*    */ 
/*    */   public OverlayPasswordField(String text, int columns) {
/* 28 */     super(text, columns);
/*    */   }
/*    */ 
/*    */   public OverlayPasswordField(String text) {
/* 32 */     super(text);
/*    */   }
/*    */ 
/*    */   public void repaint(long tm, int x, int y, int width, int height)
/*    */   {
/* 37 */     super.repaint(tm, x, y, width, height);
/* 38 */     OverlayableUtils.repaintOverlayable(this);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.OverlayPasswordField
 * JD-Core Version:    0.6.0
 */
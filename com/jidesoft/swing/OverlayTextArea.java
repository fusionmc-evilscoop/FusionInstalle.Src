/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import javax.swing.JTextArea;
/*    */ import javax.swing.text.Document;
/*    */ 
/*    */ public class OverlayTextArea extends JTextArea
/*    */ {
/*    */   public OverlayTextArea()
/*    */   {
/*    */   }
/*    */ 
/*    */   public OverlayTextArea(String text)
/*    */   {
/* 17 */     super(text);
/*    */   }
/*    */ 
/*    */   public OverlayTextArea(int rows, int columns) {
/* 21 */     super(rows, columns);
/*    */   }
/*    */ 
/*    */   public OverlayTextArea(String text, int rows, int columns) {
/* 25 */     super(text, rows, columns);
/*    */   }
/*    */ 
/*    */   public OverlayTextArea(Document doc) {
/* 29 */     super(doc);
/*    */   }
/*    */ 
/*    */   public OverlayTextArea(Document doc, String text, int rows, int columns) {
/* 33 */     super(doc, text, rows, columns);
/*    */   }
/*    */ 
/*    */   public void repaint(long tm, int x, int y, int width, int height)
/*    */   {
/* 38 */     super.repaint(tm, x, y, width, height);
/* 39 */     OverlayableUtils.repaintOverlayable(this);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.OverlayTextArea
 * JD-Core Version:    0.6.0
 */
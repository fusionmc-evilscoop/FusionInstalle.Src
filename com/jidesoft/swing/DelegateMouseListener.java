/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import java.awt.event.MouseEvent;
/*    */ import java.awt.event.MouseListener;
/*    */ 
/*    */ public class DelegateMouseListener
/*    */   implements MouseListener
/*    */ {
/*    */   private MouseListener _listener;
/*    */ 
/*    */   public DelegateMouseListener(MouseListener listener)
/*    */   {
/* 21 */     this._listener = listener;
/*    */   }
/*    */ 
/*    */   public void mouseClicked(MouseEvent e) {
/* 25 */     if (this._listener != null)
/* 26 */       this._listener.mouseClicked(e);
/*    */   }
/*    */ 
/*    */   public void mousePressed(MouseEvent e)
/*    */   {
/* 31 */     if (this._listener != null)
/* 32 */       this._listener.mousePressed(e);
/*    */   }
/*    */ 
/*    */   public void mouseReleased(MouseEvent e)
/*    */   {
/* 37 */     if (this._listener != null)
/* 38 */       this._listener.mouseReleased(e);
/*    */   }
/*    */ 
/*    */   public void mouseEntered(MouseEvent e)
/*    */   {
/* 43 */     if (this._listener != null)
/* 44 */       this._listener.mouseEntered(e);
/*    */   }
/*    */ 
/*    */   public void mouseExited(MouseEvent e)
/*    */   {
/* 49 */     if (this._listener != null)
/* 50 */       this._listener.mouseExited(e);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.DelegateMouseListener
 * JD-Core Version:    0.6.0
 */
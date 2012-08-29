/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import java.awt.event.MouseEvent;
/*    */ import javax.swing.event.MouseInputListener;
/*    */ 
/*    */ public class DelegateMouseInputListener
/*    */   implements MouseInputListener
/*    */ {
/*    */   private MouseInputListener _listener;
/*    */ 
/*    */   public DelegateMouseInputListener(MouseInputListener listener)
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
/*    */ 
/*    */   public void mouseDragged(MouseEvent e)
/*    */   {
/* 55 */     if (this._listener != null)
/* 56 */       this._listener.mouseDragged(e);
/*    */   }
/*    */ 
/*    */   public void mouseMoved(MouseEvent e)
/*    */   {
/* 61 */     if (this._listener != null)
/* 62 */       this._listener.mouseMoved(e);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.DelegateMouseInputListener
 * JD-Core Version:    0.6.0
 */
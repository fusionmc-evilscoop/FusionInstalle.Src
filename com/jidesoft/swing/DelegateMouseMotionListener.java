/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import java.awt.event.MouseEvent;
/*    */ import java.awt.event.MouseMotionListener;
/*    */ 
/*    */ public class DelegateMouseMotionListener
/*    */   implements MouseMotionListener
/*    */ {
/*    */   private MouseMotionListener _listener;
/*    */ 
/*    */   public DelegateMouseMotionListener(MouseMotionListener listener)
/*    */   {
/* 21 */     this._listener = listener;
/*    */   }
/*    */ 
/*    */   public void mouseDragged(MouseEvent e) {
/* 25 */     if (this._listener != null)
/* 26 */       this._listener.mouseDragged(e);
/*    */   }
/*    */ 
/*    */   public void mouseMoved(MouseEvent e)
/*    */   {
/* 31 */     if (this._listener != null)
/* 32 */       this._listener.mouseMoved(e);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.DelegateMouseMotionListener
 * JD-Core Version:    0.6.0
 */
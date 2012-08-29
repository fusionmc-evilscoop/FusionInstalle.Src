/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import java.awt.event.MouseEvent;
/*    */ import javax.swing.event.MouseInputListener;
/*    */ 
/*    */ class MouseInputListeners
/*    */   implements MouseInputListener
/*    */ {
/*    */   private MouseInputListener[] _mouseInputListeners;
/*    */ 
/*    */   public MouseInputListeners(MouseInputListener[] mouseInputListeners)
/*    */   {
/* 17 */     this._mouseInputListeners = mouseInputListeners;
/*    */   }
/*    */ 
/*    */   public void mouseClicked(MouseEvent e) {
/* 21 */     for (MouseInputListener mouseInputListener : this._mouseInputListeners) {
/* 22 */       if (e.isConsumed()) {
/*    */         break;
/*    */       }
/* 25 */       mouseInputListener.mouseClicked(e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void mousePressed(MouseEvent e) {
/* 30 */     for (MouseInputListener mouseInputListener : this._mouseInputListeners) {
/* 31 */       if (e.isConsumed()) {
/*    */         break;
/*    */       }
/* 34 */       mouseInputListener.mousePressed(e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void mouseReleased(MouseEvent e) {
/* 39 */     for (MouseInputListener mouseInputListener : this._mouseInputListeners) {
/* 40 */       if (e.isConsumed()) {
/*    */         break;
/*    */       }
/* 43 */       mouseInputListener.mouseReleased(e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void mouseEntered(MouseEvent e) {
/* 48 */     for (MouseInputListener mouseInputListener : this._mouseInputListeners) {
/* 49 */       if (e.isConsumed()) {
/*    */         break;
/*    */       }
/* 52 */       mouseInputListener.mouseEntered(e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void mouseExited(MouseEvent e) {
/* 57 */     for (MouseInputListener mouseInputListener : this._mouseInputListeners) {
/* 58 */       if (e.isConsumed()) {
/*    */         break;
/*    */       }
/* 61 */       mouseInputListener.mouseExited(e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void mouseDragged(MouseEvent e) {
/* 66 */     for (MouseInputListener mouseInputListener : this._mouseInputListeners) {
/* 67 */       if (e.isConsumed()) {
/*    */         break;
/*    */       }
/* 70 */       mouseInputListener.mouseDragged(e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void mouseMoved(MouseEvent e) {
/* 75 */     for (MouseInputListener mouseInputListener : this._mouseInputListeners) {
/* 76 */       if (e.isConsumed()) {
/*    */         break;
/*    */       }
/* 79 */       mouseInputListener.mouseMoved(e);
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.MouseInputListeners
 * JD-Core Version:    0.6.0
 */
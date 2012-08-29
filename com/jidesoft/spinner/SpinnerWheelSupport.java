/*    */ package com.jidesoft.spinner;
/*    */ 
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.MouseWheelEvent;
/*    */ import java.awt.event.MouseWheelListener;
/*    */ import javax.swing.Action;
/*    */ import javax.swing.ActionMap;
/*    */ import javax.swing.JSpinner;
/*    */ 
/*    */ public class SpinnerWheelSupport
/*    */ {
/*    */   public static final String CLIENT_PROPERTY_MOUSE_WHEEL_LISTENER = "mouseWheelListener";
/*    */   protected static final String ACTION_NAME_INCREMENT = "increment";
/*    */   protected static final String ACTION_NAME_DECREMENT = "decrement";
/*    */ 
/*    */   public static void installMouseWheelSupport(JSpinner spinner)
/*    */   {
/* 25 */     MouseWheelListener l = new MouseWheelListener(spinner) {
/*    */       public void mouseWheelMoved(MouseWheelEvent e) {
/* 27 */         int rotation = e.getWheelRotation();
/* 28 */         if (rotation < 0) {
/* 29 */           Action action = this.val$spinner.getActionMap().get("increment");
/* 30 */           if (action != null) {
/* 31 */             action.actionPerformed(new ActionEvent(e.getSource(), 0, "increment"));
/*    */           }
/*    */         }
/* 34 */         else if (rotation > 0) {
/* 35 */           Action action = this.val$spinner.getActionMap().get("decrement");
/* 36 */           if (action != null)
/* 37 */             action.actionPerformed(new ActionEvent(e.getSource(), 0, "decrement"));
/*    */         }
/*    */       }
/*    */     };
/* 42 */     spinner.addMouseWheelListener(l);
/* 43 */     spinner.putClientProperty("mouseWheelListener", l);
/*    */   }
/*    */ 
/*    */   public static void uninstallMouseWheelSupport(JSpinner spinner) {
/* 47 */     MouseWheelListener l = (MouseWheelListener)spinner.getClientProperty("mouseWheelListener");
/* 48 */     if (l != null)
/* 49 */       spinner.removeMouseWheelListener(l);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.spinner.SpinnerWheelSupport
 * JD-Core Version:    0.6.0
 */
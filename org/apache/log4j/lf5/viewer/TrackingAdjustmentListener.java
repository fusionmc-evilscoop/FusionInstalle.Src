/*    */ package org.apache.log4j.lf5.viewer;
/*    */ 
/*    */ import java.awt.Adjustable;
/*    */ import java.awt.event.AdjustmentEvent;
/*    */ import java.awt.event.AdjustmentListener;
/*    */ 
/*    */ public class TrackingAdjustmentListener
/*    */   implements AdjustmentListener
/*    */ {
/* 45 */   protected int _lastMaximum = -1;
/*    */ 
/*    */   public void adjustmentValueChanged(AdjustmentEvent e)
/*    */   {
/* 60 */     Adjustable bar = e.getAdjustable();
/* 61 */     int currentMaximum = bar.getMaximum();
/* 62 */     if (bar.getMaximum() == this._lastMaximum) {
/* 63 */       return;
/*    */     }
/* 65 */     int bottom = bar.getValue() + bar.getVisibleAmount();
/*    */ 
/* 67 */     if (bottom + bar.getUnitIncrement() >= this._lastMaximum) {
/* 68 */       bar.setValue(bar.getMaximum());
/*    */     }
/* 70 */     this._lastMaximum = currentMaximum;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.TrackingAdjustmentListener
 * JD-Core Version:    0.6.0
 */
/*    */ package com.jidesoft.dialog;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Container;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Rectangle;
/*    */ import javax.swing.Scrollable;
/*    */ 
/*    */ public class ScrollableButtonPanel extends ButtonPanel
/*    */   implements Scrollable
/*    */ {
/*    */   public ScrollableButtonPanel()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ScrollableButtonPanel(int alignment)
/*    */   {
/* 16 */     super(alignment);
/*    */   }
/*    */ 
/*    */   public ScrollableButtonPanel(int alignment, int sizeContraint) {
/* 20 */     super(alignment, sizeContraint);
/*    */   }
/*    */ 
/*    */   public Dimension getPreferredScrollableViewportSize() {
/* 24 */     return getPreferredSize();
/*    */   }
/*    */ 
/*    */   public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
/* 28 */     if (getComponentCount() > 0) {
/* 29 */       Component c = getComponent(0);
/* 30 */       if (orientation == 0) {
/* 31 */         return c.getWidth();
/*    */       }
/* 33 */       return c.getHeight();
/*    */     }
/* 35 */     return 50;
/*    */   }
/*    */ 
/*    */   public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
/* 39 */     if (orientation == 0) {
/* 40 */       return visibleRect.width;
/*    */     }
/* 42 */     return visibleRect.width;
/*    */   }
/*    */ 
/*    */   public boolean getScrollableTracksViewportWidth()
/*    */   {
/* 51 */     if (getParent() == null) {
/* 52 */       return true;
/*    */     }
/*    */ 
/* 55 */     return getParent().getSize().width > getPreferredSize().width;
/*    */   }
/*    */ 
/*    */   public boolean getScrollableTracksViewportHeight() {
/* 59 */     return false;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.dialog.ScrollableButtonPanel
 * JD-Core Version:    0.6.0
 */
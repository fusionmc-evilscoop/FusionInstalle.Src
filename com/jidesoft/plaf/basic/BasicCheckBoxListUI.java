/*    */ package com.jidesoft.plaf.basic;
/*    */ 
/*    */ import java.awt.Insets;
/*    */ import java.awt.Point;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JList;
/*    */ import javax.swing.ListModel;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ import javax.swing.plaf.basic.BasicListUI;
/*    */ 
/*    */ public class BasicCheckBoxListUI extends BasicListUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent c)
/*    */   {
/* 21 */     return new BasicCheckBoxListUI();
/*    */   }
/*    */ 
/*    */   public int locationToIndex(JList aList, Point location)
/*    */   {
/* 26 */     int index = super.locationToIndex(aList, location);
/* 27 */     int size = this.list.getModel().getSize();
/* 28 */     if (index < size - 1) {
/* 29 */       return index;
/*    */     }
/*    */ 
/* 33 */     int y = location.y;
/* 34 */     Insets insets = this.list.getInsets();
/* 35 */     int maxRow = size - 1;
/*    */     int row;
/*    */     int row;
/* 37 */     if (this.cellHeights == null) {
/* 38 */       row = this.cellHeight == 0 ? 0 : (y - insets.top) / this.cellHeight;
/*    */     }
/*    */     else {
/* 41 */       if (size > this.cellHeights.length) {
/* 42 */         return index;
/*    */       }
/*    */ 
/* 45 */       int rowYOffset = insets.top;
/* 46 */       for (row = 0; (row < size) && (
/* 47 */         (y < rowYOffset) || (y >= rowYOffset + this.cellHeights[row])); row++)
/*    */       {
/* 50 */         rowYOffset += this.cellHeights[row];
/*    */       }
/*    */     }
/* 53 */     if (row > maxRow) {
/* 54 */       return -1;
/*    */     }
/*    */ 
/* 57 */     return index;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.BasicCheckBoxListUI
 * JD-Core Version:    0.6.0
 */
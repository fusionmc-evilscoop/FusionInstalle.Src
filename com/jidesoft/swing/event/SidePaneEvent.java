/*    */ package com.jidesoft.swing.event;
/*    */ 
/*    */ import com.jidesoft.swing.SidePaneItem;
/*    */ import java.awt.AWTEvent;
/*    */ 
/*    */ public class SidePaneEvent extends AWTEvent
/*    */ {
/*    */   public static final int SIDE_PANE_FIRST = 4099;
/*    */   public static final int SIDE_PANE_LAST = 4100;
/*    */   public static final int SIDE_PANE_TAB_SELECTED = 4099;
/*    */   public static final int SIDE_PANE_TAB_DESELECTED = 4100;
/*    */ 
/*    */   public SidePaneEvent(SidePaneItem source, int id)
/*    */   {
/* 21 */     super(source, id);
/*    */   }
/*    */ 
/*    */   public String paramString()
/*    */   {
/*    */     String typeStr;
/* 52 */     switch (this.id) {
/*    */     case 4099:
/* 54 */       typeStr = "SIDE_PANE_TAB_SELECTED";
/* 55 */       break;
/*    */     case 4100:
/* 57 */       typeStr = "SIDE_PANE_TAB_DESELECTED";
/* 58 */       break;
/*    */     default:
/* 60 */       typeStr = "SIDE_PANE_UNKNOWN";
/*    */     }
/* 62 */     return typeStr;
/*    */   }
/*    */ 
/*    */   public SidePaneItem getSidePaneItem()
/*    */   {
/* 73 */     return (this.source instanceof SidePaneItem) ? (SidePaneItem)this.source : null;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.event.SidePaneEvent
 * JD-Core Version:    0.6.0
 */
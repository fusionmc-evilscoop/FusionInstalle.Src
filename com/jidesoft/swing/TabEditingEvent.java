/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import java.awt.AWTEvent;
/*    */ 
/*    */ public class TabEditingEvent extends AWTEvent
/*    */ {
/*    */   public static final int TAB_EDITING_STARTED = 3099;
/*    */   public static final int TAB_EDITING_STOPPED = 3100;
/*    */   public static final int TAB_EDITING_CANCELLED = 3101;
/*    */   private int _tabIndex;
/*    */   private String _oldTitle;
/*    */   private String _newTitle;
/*    */ 
/*    */   public TabEditingEvent(Object source, int id, int tabIndex)
/*    */   {
/* 18 */     super(source, id);
/* 19 */     this._tabIndex = tabIndex;
/*    */   }
/*    */ 
/*    */   public TabEditingEvent(Object source, int id, int tabIndex, String oldTitle, String newTitle) {
/* 23 */     super(source, id);
/* 24 */     this._tabIndex = tabIndex;
/* 25 */     this._oldTitle = oldTitle;
/* 26 */     this._newTitle = newTitle;
/*    */   }
/*    */ 
/*    */   public int getTabIndex()
/*    */   {
/* 35 */     return this._tabIndex;
/*    */   }
/*    */ 
/*    */   public String getOldTitle()
/*    */   {
/* 45 */     return this._oldTitle;
/*    */   }
/*    */ 
/*    */   public String getNewTitle()
/*    */   {
/* 55 */     return this._newTitle;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.TabEditingEvent
 * JD-Core Version:    0.6.0
 */
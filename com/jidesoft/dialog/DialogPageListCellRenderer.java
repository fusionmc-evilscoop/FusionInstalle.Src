/*    */ package com.jidesoft.dialog;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import javax.swing.DefaultListCellRenderer;
/*    */ import javax.swing.JList;
/*    */ 
/*    */ class DialogPageListCellRenderer extends DefaultListCellRenderer
/*    */ {
/*    */   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
/*    */   {
/* 22 */     if ((value instanceof AbstractDialogPage)) {
/* 23 */       AbstractDialogPage page = (AbstractDialogPage)value;
/* 24 */       return super.getListCellRendererComponent(list, page.getTitle(), index, isSelected, cellHasFocus);
/*    */     }
/*    */ 
/* 27 */     return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.dialog.DialogPageListCellRenderer
 * JD-Core Version:    0.6.0
 */
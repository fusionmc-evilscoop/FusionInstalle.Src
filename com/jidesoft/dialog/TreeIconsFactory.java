/*    */ package com.jidesoft.dialog;
/*    */ 
/*    */ import com.jidesoft.icons.IconsFactory;
/*    */ import javax.swing.ImageIcon;
/*    */ 
/*    */ class TreeIconsFactory
/*    */ {
/*    */   public static ImageIcon getImageIcon(String name)
/*    */   {
/* 25 */     if (name != null) {
/* 26 */       return IconsFactory.getImageIcon(TreeIconsFactory.class, name);
/*    */     }
/* 28 */     return null;
/*    */   }
/*    */ 
/*    */   public static void main(String[] argv) {
/* 32 */     IconsFactory.generateHTML(TreeIconsFactory.class);
/*    */   }
/*    */ 
/*    */   static class CellRenderer
/*    */   {
/*    */     public static final String SELECTED_C16 = "icons/selected-c16.gif";
/*    */     public static final String SELECTED_B16 = "icons/selected-b16.gif";
/*    */     public static final String BLANK_16 = "icons/blank-16.gif";
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.dialog.TreeIconsFactory
 * JD-Core Version:    0.6.0
 */
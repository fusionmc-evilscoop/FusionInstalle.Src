/*    */ package com.jidesoft.plaf.basic;
/*    */ 
/*    */ import com.jidesoft.icons.IconsFactory;
/*    */ import javax.swing.ImageIcon;
/*    */ 
/*    */ public class BasicFolderChooserIconsFactory
/*    */ {
/*    */   public static ImageIcon getImageIcon(String name)
/*    */   {
/* 31 */     if (name != null) {
/* 32 */       return IconsFactory.getImageIcon(BasicFolderChooserIconsFactory.class, name);
/*    */     }
/* 34 */     return null;
/*    */   }
/*    */ 
/*    */   public static void main(String[] argv) {
/* 38 */     IconsFactory.generateHTML(BasicFolderChooserIconsFactory.class);
/*    */   }
/*    */ 
/*    */   public static class ToolBar
/*    */   {
/*    */     public static final String NEW = "icons/new.png";
/*    */     public static final String DELETE = "icons/delete.png";
/*    */     public static final String HOME = "icons/home.png";
/*    */     public static final String MY_DOCUMENT = "icons/myDocument.png";
/*    */     public static final String DESKTOP = "icons/desktop.png";
/*    */     public static final String REFRESH = "icons/refresh.png";
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.BasicFolderChooserIconsFactory
 * JD-Core Version:    0.6.0
 */
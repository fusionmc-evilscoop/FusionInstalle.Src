/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import com.jidesoft.icons.IconsFactory;
/*    */ import javax.swing.ImageIcon;
/*    */ 
/*    */ public class OverlayableIconsFactory
/*    */ {
/*    */   public static final String ATTENTION = "icons/overlay_attention.png";
/*    */   public static final String CORRECT = "icons/overlay_correct.png";
/*    */   public static final String ERROR = "icons/overlay_error.png";
/*    */   public static final String INFO = "icons/overlay_info.png";
/*    */   public static final String QUESTION = "icons/overlay_question.png";
/*    */ 
/*    */   public static ImageIcon getImageIcon(String name)
/*    */   {
/* 25 */     if (name != null) {
/* 26 */       return IconsFactory.getImageIcon(OverlayableIconsFactory.class, name);
/*    */     }
/* 28 */     return null;
/*    */   }
/*    */ 
/*    */   public static void main(String[] argv) {
/* 32 */     IconsFactory.generateHTML(OverlayableIconsFactory.class);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.OverlayableIconsFactory
 * JD-Core Version:    0.6.0
 */
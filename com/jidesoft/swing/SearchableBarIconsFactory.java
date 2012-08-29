/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import com.jidesoft.icons.IconsFactory;
/*    */ import javax.swing.ImageIcon;
/*    */ 
/*    */ public class SearchableBarIconsFactory
/*    */ {
/*    */   public static ImageIcon getImageIcon(String name)
/*    */   {
/* 36 */     if (name != null) {
/* 37 */       return IconsFactory.getImageIcon(SearchableBarIconsFactory.class, name);
/*    */     }
/* 39 */     return null;
/*    */   }
/*    */ 
/*    */   public static void main(String[] argv) {
/* 43 */     IconsFactory.generateHTML(SearchableBarIconsFactory.class);
/*    */   }
/*    */ 
/*    */   public static class Buttons
/*    */   {
/*    */     public static final String CLOSE = "icons/close.png";
/*    */     public static final String CLOSE_ROLLOVER = "icons/closeR.png";
/*    */     public static final String HIGHLIGHTS = "icons/highlights.png";
/*    */     public static final String HIGHLIGHTS_SELECTED = "icons/highlightsS.png";
/*    */     public static final String HIGHLIGHTS_DISABLED = "icons/highlightsD.png";
/*    */     public static final String HIGHLIGHTS_ROLLOVER = "icons/highlightsR.png";
/*    */     public static final String HIGHLIGHTS_ROLLOVER_SELECTED = "icons/highlightsRS.png";
/*    */     public static final String NEXT = "icons/next.png";
/*    */     public static final String NEXT_ROLLOVER = "icons/nextR.png";
/*    */     public static final String NEXT_DISABLED = "icons/nextD.png";
/*    */     public static final String PREVIOUS = "icons/previous.png";
/*    */     public static final String PREVIOUS_ROLLOVER = "icons/previousR.png";
/*    */     public static final String PREVIOUS_DISABLED = "icons/previousD.png";
/*    */     public static final String ERROR = "icons/error.png";
/*    */     public static final String REPEAT = "icons/repeat.png";
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.SearchableBarIconsFactory
 * JD-Core Version:    0.6.0
 */
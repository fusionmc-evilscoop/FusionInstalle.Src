/*    */ package com.jidesoft.plaf.aqua;
/*    */ 
/*    */ import com.jidesoft.plaf.basic.BasicJideSplitButtonUI;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ 
/*    */ public class AquaJideSplitButtonUI extends BasicJideSplitButtonUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent x)
/*    */   {
/* 21 */     return new AquaJideSplitButtonUI();
/*    */   }
/*    */ 
/*    */   protected int getOffset()
/*    */   {
/* 31 */     return 2;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.aqua.AquaJideSplitButtonUI
 * JD-Core Version:    0.6.0
 */
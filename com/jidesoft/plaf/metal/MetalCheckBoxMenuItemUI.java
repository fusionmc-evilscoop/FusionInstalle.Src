/*    */ package com.jidesoft.plaf.metal;
/*    */ 
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ 
/*    */ public class MetalCheckBoxMenuItemUI extends MetalMenuItemUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent c)
/*    */   {
/* 18 */     return new MetalCheckBoxMenuItemUI();
/*    */   }
/*    */ 
/*    */   protected String getPropertyPrefix()
/*    */   {
/* 23 */     return "CheckBoxMenuItem";
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.metal.MetalCheckBoxMenuItemUI
 * JD-Core Version:    0.6.0
 */
/*    */ package com.jidesoft.plaf.metal;
/*    */ 
/*    */ import com.jidesoft.plaf.vsnet.VsnetMenuItemUI;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ 
/*    */ public class MetalRadioButtonMenuItemUI extends VsnetMenuItemUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent b)
/*    */   {
/* 19 */     return new MetalRadioButtonMenuItemUI();
/*    */   }
/*    */ 
/*    */   protected String getPropertyPrefix()
/*    */   {
/* 24 */     return "RadioButtonMenuItem";
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.metal.MetalRadioButtonMenuItemUI
 * JD-Core Version:    0.6.0
 */
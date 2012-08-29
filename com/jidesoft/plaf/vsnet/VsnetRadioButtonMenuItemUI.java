/*    */ package com.jidesoft.plaf.vsnet;
/*    */ 
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ 
/*    */ public class VsnetRadioButtonMenuItemUI extends VsnetMenuItemUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent b)
/*    */   {
/* 17 */     return new VsnetRadioButtonMenuItemUI();
/*    */   }
/*    */ 
/*    */   protected String getPropertyPrefix()
/*    */   {
/* 22 */     return "RadioButtonMenuItem";
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.vsnet.VsnetRadioButtonMenuItemUI
 * JD-Core Version:    0.6.0
 */
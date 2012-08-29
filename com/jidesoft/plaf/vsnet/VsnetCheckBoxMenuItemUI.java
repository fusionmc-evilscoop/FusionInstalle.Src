/*    */ package com.jidesoft.plaf.vsnet;
/*    */ 
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ 
/*    */ public class VsnetCheckBoxMenuItemUI extends VsnetMenuItemUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent c)
/*    */   {
/* 18 */     return new VsnetCheckBoxMenuItemUI();
/*    */   }
/*    */ 
/*    */   protected String getPropertyPrefix()
/*    */   {
/* 23 */     return "CheckBoxMenuItem";
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.vsnet.VsnetCheckBoxMenuItemUI
 * JD-Core Version:    0.6.0
 */
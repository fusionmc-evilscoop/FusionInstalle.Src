/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import com.jidesoft.plaf.LookAndFeelFactory;
/*    */ import com.jidesoft.plaf.UIDefaultsLookup;
/*    */ import java.util.Vector;
/*    */ import javax.swing.ComboBoxModel;
/*    */ import javax.swing.JComboBox;
/*    */ import javax.swing.UIManager;
/*    */ 
/*    */ public class JideComboBox extends JComboBox
/*    */ {
/*    */   private static final String uiClassID = "JideComboBoxUI";
/*    */ 
/*    */   public JideComboBox(ComboBoxModel aModel)
/*    */   {
/* 17 */     super(aModel);
/*    */   }
/*    */ 
/*    */   public JideComboBox(Object[] items) {
/* 21 */     super(items);
/*    */   }
/*    */ 
/*    */   public JideComboBox(Vector<?> items) {
/* 25 */     super(items);
/*    */   }
/*    */ 
/*    */   public JideComboBox()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void updateUI()
/*    */   {
/* 39 */     if (UIDefaultsLookup.get("JideComboBoxUI") == null) {
/* 40 */       LookAndFeelFactory.installJideExtension();
/*    */     }
/* 42 */     setUI(UIManager.getUI(this));
/*    */   }
/*    */ 
/*    */   public String getUIClassID()
/*    */   {
/* 54 */     return "JideComboBoxUI";
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.JideComboBox
 * JD-Core Version:    0.6.0
 */
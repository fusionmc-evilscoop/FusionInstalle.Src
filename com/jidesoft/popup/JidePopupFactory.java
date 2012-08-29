/*    */ package com.jidesoft.popup;
/*    */ 
/*    */ public class JidePopupFactory
/*    */ {
/* 14 */   private static int _popupType = -1;
/*    */   private static JidePopupFactory _popupFactory;
/*    */ 
/*    */   public JidePopup createPopup()
/*    */   {
/* 18 */     JidePopup p = new JidePopup();
/*    */ 
/* 20 */     if (_popupType != -1) {
/* 21 */       p.setPopupType(_popupType);
/*    */     }
/*    */ 
/* 24 */     return p;
/*    */   }
/*    */ 
/*    */   public static void setPopupType(int poupType)
/*    */   {
/* 34 */     if ((poupType != 0) && (poupType != 2)) {
/* 35 */       throw new IllegalArgumentException("invalid popup type. It must be JidePopup.HEAVY_WEIGHT_POPUP or JidePopup.LIGHT_WEIGHT_POPUP.");
/*    */     }
/*    */ 
/* 39 */     _popupType = poupType;
/*    */   }
/*    */ 
/*    */   public static void setSharedInstance(JidePopupFactory factory)
/*    */   {
/* 51 */     if (factory == null) {
/* 52 */       throw new IllegalArgumentException("JidePopupFactory can not be null");
/*    */     }
/*    */ 
/* 55 */     _popupFactory = factory;
/*    */   }
/*    */ 
/*    */   public static int getPopupType()
/*    */   {
/* 64 */     return _popupType;
/*    */   }
/*    */ 
/*    */   public static JidePopupFactory getSharedInstance()
/*    */   {
/* 73 */     if (_popupFactory == null) {
/* 74 */       _popupFactory = new JidePopupFactory();
/*    */     }
/*    */ 
/* 77 */     return _popupFactory;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.popup.JidePopupFactory
 * JD-Core Version:    0.6.0
 */
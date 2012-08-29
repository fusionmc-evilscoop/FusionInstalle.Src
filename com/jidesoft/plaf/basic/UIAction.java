/*    */ package com.jidesoft.plaf.basic;
/*    */ 
/*    */ import java.beans.PropertyChangeListener;
/*    */ import javax.swing.Action;
/*    */ 
/*    */ public abstract class UIAction
/*    */   implements Action
/*    */ {
/*    */   private String name;
/*    */ 
/*    */   public UIAction(String name)
/*    */   {
/* 46 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public final String getName() {
/* 50 */     return this.name;
/*    */   }
/*    */ 
/*    */   public Object getValue(String key) {
/* 54 */     if ("Name".equals(key)) {
/* 55 */       return this.name;
/*    */     }
/* 57 */     return null;
/*    */   }
/*    */ 
/*    */   public void putValue(String key, Object value)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void setEnabled(boolean b)
/*    */   {
/*    */   }
/*    */ 
/*    */   public final boolean isEnabled()
/*    */   {
/* 72 */     return isEnabled(null);
/*    */   }
/*    */ 
/*    */   public boolean isEnabled(Object sender)
/*    */   {
/* 84 */     return true;
/*    */   }
/*    */ 
/*    */   public void addPropertyChangeListener(PropertyChangeListener listener)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void removePropertyChangeListener(PropertyChangeListener listener)
/*    */   {
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.UIAction
 * JD-Core Version:    0.6.0
 */
/*    */ package com.jidesoft.dialog;
/*    */ 
/*    */ import javax.swing.tree.DefaultMutableTreeNode;
/*    */ 
/*    */ class MutableTreeNodeEx extends DefaultMutableTreeNode
/*    */ {
/*    */   private static final long serialVersionUID = 8410814900789441894L;
/* 16 */   protected boolean _enabled = true;
/*    */ 
/*    */   public MutableTreeNodeEx() {
/* 19 */     this(null, true, true);
/*    */   }
/*    */ 
/*    */   public MutableTreeNodeEx(Object userObject) {
/* 23 */     this(userObject, true, true);
/*    */   }
/*    */ 
/*    */   public MutableTreeNodeEx(Object userObject, boolean allowsChildren) {
/* 27 */     this(userObject, allowsChildren, true);
/*    */   }
/*    */ 
/*    */   public MutableTreeNodeEx(Object userObject, boolean allowsChildren, boolean enabled) {
/* 31 */     super(userObject, allowsChildren);
/* 32 */     setEnabled(enabled);
/*    */   }
/*    */ 
/*    */   public int getChildCount()
/*    */   {
/* 40 */     if (isEnabled()) {
/* 41 */       return super.getChildCount();
/*    */     }
/*    */ 
/* 44 */     return 0;
/*    */   }
/*    */ 
/*    */   public void setEnabled(boolean enabled)
/*    */   {
/* 56 */     this._enabled = enabled;
/*    */   }
/*    */ 
/*    */   public boolean isEnabled()
/*    */   {
/* 66 */     return this._enabled;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.dialog.MutableTreeNodeEx
 * JD-Core Version:    0.6.0
 */
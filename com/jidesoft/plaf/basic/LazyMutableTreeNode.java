/*    */ package com.jidesoft.plaf.basic;
/*    */ 
/*    */ import javax.swing.tree.DefaultMutableTreeNode;
/*    */ 
/*    */ public abstract class LazyMutableTreeNode extends DefaultMutableTreeNode
/*    */ {
/* 15 */   protected boolean _loaded = false;
/*    */ 
/*    */   public LazyMutableTreeNode()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LazyMutableTreeNode(Object userObject) {
/* 22 */     super(userObject);
/*    */   }
/*    */ 
/*    */   public LazyMutableTreeNode(Object userObject, boolean allowsChildren) {
/* 26 */     super(userObject, allowsChildren);
/*    */   }
/*    */ 
/*    */   public int getChildCount()
/*    */   {
/* 31 */     synchronized (this) {
/* 32 */       if (!this._loaded) {
/* 33 */         this._loaded = true;
/* 34 */         initChildren();
/*    */       }
/*    */     }
/* 37 */     return super.getChildCount();
/*    */   }
/*    */ 
/*    */   public void clear() {
/* 41 */     removeAllChildren();
/* 42 */     this._loaded = false;
/*    */   }
/*    */ 
/*    */   public boolean isLoaded() {
/* 46 */     return this._loaded;
/*    */   }
/*    */ 
/*    */   protected abstract void initChildren();
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.LazyMutableTreeNode
 * JD-Core Version:    0.6.0
 */
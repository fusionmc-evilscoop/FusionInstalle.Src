/*    */ package com.jidesoft.utils;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.PrintStream;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.RepaintManager;
/*    */ import javax.swing.SwingUtilities;
/*    */ 
/*    */ public class ThreadCheckingRepaintManager extends RepaintManager
/*    */ {
/* 18 */   private int tabCount = 0;
/* 19 */   private boolean checkIsShowing = false;
/*    */ 
/*    */   public ThreadCheckingRepaintManager()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ThreadCheckingRepaintManager(boolean checkIsShowing)
/*    */   {
/* 27 */     this.checkIsShowing = checkIsShowing;
/*    */   }
/*    */ 
/*    */   public synchronized void addInvalidComponent(JComponent jComponent)
/*    */   {
/* 32 */     checkThread(jComponent);
/* 33 */     super.addInvalidComponent(jComponent);
/*    */   }
/*    */ 
/*    */   private void checkThread(JComponent c) {
/* 37 */     if ((!SwingUtilities.isEventDispatchThread()) && (checkIsShowing(c))) {
/* 38 */       System.out.println("----------Wrong Thread START");
/* 39 */       System.out.println(getStracktraceAsString(new Exception()));
/* 40 */       dumpComponentTree(c);
/* 41 */       System.out.println("----------Wrong Thread END");
/*    */     }
/*    */   }
/*    */ 
/*    */   private String getStracktraceAsString(Exception e) {
/* 46 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 47 */     PrintStream printStream = new PrintStream(byteArrayOutputStream);
/* 48 */     e.printStackTrace(printStream);
/* 49 */     printStream.flush();
/* 50 */     return byteArrayOutputStream.toString();
/*    */   }
/*    */ 
/*    */   private boolean checkIsShowing(JComponent c) {
/* 54 */     if (!this.checkIsShowing) {
/* 55 */       return true;
/*    */     }
/*    */ 
/* 58 */     return c.isShowing();
/*    */   }
/*    */ 
/*    */   public synchronized void addDirtyRegion(JComponent jComponent, int i, int i1, int i2, int i3)
/*    */   {
/* 64 */     checkThread(jComponent);
/* 65 */     super.addDirtyRegion(jComponent, i, i1, i2, i3);
/*    */   }
/*    */ 
/*    */   private void dumpComponentTree(Component c) {
/* 69 */     System.out.println("----------Component Tree");
/* 70 */     resetTabCount();
/* 71 */     for (; c != null; c = c.getParent()) {
/* 72 */       printTabIndent();
/* 73 */       System.out.println(c);
/* 74 */       printTabIndent();
/* 75 */       System.out.println("Showing:" + c.isShowing() + " Visible: " + c.isVisible());
/* 76 */       incrementTabCount();
/*    */     }
/*    */   }
/*    */ 
/*    */   private void resetTabCount() {
/* 81 */     this.tabCount = 0;
/*    */   }
/*    */ 
/*    */   private void incrementTabCount() {
/* 85 */     this.tabCount += 1;
/*    */   }
/*    */ 
/*    */   private void printTabIndent() {
/* 89 */     for (int i = 0; i < this.tabCount; i++)
/* 90 */       System.out.print("\t");
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.ThreadCheckingRepaintManager
 * JD-Core Version:    0.6.0
 */
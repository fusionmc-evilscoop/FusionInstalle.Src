/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.event.MouseEvent;
/*    */ import javax.swing.Icon;
/*    */ import javax.swing.event.MouseInputListener;
/*    */ 
/*    */ public class ClickThroughStyledLabel extends StyledLabel
/*    */   implements MouseInputListener
/*    */ {
/*    */   private Component _target;
/*    */ 
/*    */   public ClickThroughStyledLabel()
/*    */   {
/* 28 */     installListeners();
/*    */   }
/*    */ 
/*    */   public ClickThroughStyledLabel(Icon image) {
/* 32 */     super(image);
/* 33 */     installListeners();
/*    */   }
/*    */ 
/*    */   public ClickThroughStyledLabel(Icon image, int horizontalAlignment) {
/* 37 */     super(image, horizontalAlignment);
/* 38 */     installListeners();
/*    */   }
/*    */ 
/*    */   public ClickThroughStyledLabel(String text) {
/* 42 */     super(text);
/* 43 */     installListeners();
/*    */   }
/*    */ 
/*    */   public ClickThroughStyledLabel(String text, int horizontalAlignment) {
/* 47 */     super(text, horizontalAlignment);
/* 48 */     installListeners();
/*    */   }
/*    */ 
/*    */   public ClickThroughStyledLabel(String text, Icon icon, int horizontalAlignment) {
/* 52 */     super(text, icon, horizontalAlignment);
/* 53 */     installListeners();
/*    */   }
/*    */ 
/*    */   public Component getTarget() {
/* 57 */     return this._target;
/*    */   }
/*    */ 
/*    */   public void setTarget(Component target) {
/* 61 */     this._target = target;
/*    */   }
/*    */ 
/*    */   protected void installListeners() {
/* 65 */     addMouseListener(this);
/* 66 */     addMouseMotionListener(this);
/*    */   }
/*    */ 
/*    */   protected void uninstallListeners() {
/* 70 */     removeMouseListener(this);
/* 71 */     removeMouseMotionListener(this);
/*    */   }
/*    */ 
/*    */   public void mouseClicked(MouseEvent e) {
/* 75 */     JideSwingUtilities.retargetMouseEvent(e.getID(), e, getTarget());
/*    */   }
/*    */ 
/*    */   public void mousePressed(MouseEvent e) {
/* 79 */     JideSwingUtilities.retargetMouseEvent(e.getID(), e, getTarget());
/*    */   }
/*    */ 
/*    */   public void mouseReleased(MouseEvent e) {
/* 83 */     JideSwingUtilities.retargetMouseEvent(e.getID(), e, getTarget());
/*    */   }
/*    */ 
/*    */   public void mouseEntered(MouseEvent e) {
/* 87 */     JideSwingUtilities.retargetMouseEvent(e.getID(), e, getTarget());
/*    */   }
/*    */ 
/*    */   public void mouseExited(MouseEvent e) {
/* 91 */     JideSwingUtilities.retargetMouseEvent(e.getID(), e, getTarget());
/*    */   }
/*    */ 
/*    */   public void mouseDragged(MouseEvent e) {
/* 95 */     JideSwingUtilities.retargetMouseEvent(e.getID(), e, getTarget());
/*    */   }
/*    */ 
/*    */   public void mouseMoved(MouseEvent e) {
/* 99 */     JideSwingUtilities.retargetMouseEvent(e.getID(), e, getTarget());
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.ClickThroughStyledLabel
 * JD-Core Version:    0.6.0
 */
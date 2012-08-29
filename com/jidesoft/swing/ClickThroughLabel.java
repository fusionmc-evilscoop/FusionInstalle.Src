/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.event.MouseInputListener;
/*     */ 
/*     */ public class ClickThroughLabel extends JLabel
/*     */   implements MouseInputListener
/*     */ {
/*     */   private Component _target;
/*     */ 
/*     */   public ClickThroughLabel()
/*     */   {
/*  33 */     installListeners();
/*     */   }
/*     */ 
/*     */   public ClickThroughLabel(Icon image) {
/*  37 */     super(image);
/*  38 */     installListeners();
/*     */   }
/*     */ 
/*     */   public ClickThroughLabel(Icon image, int horizontalAlignment) {
/*  42 */     super(image, horizontalAlignment);
/*  43 */     installListeners();
/*     */   }
/*     */ 
/*     */   public ClickThroughLabel(String text) {
/*  47 */     super(text);
/*  48 */     installListeners();
/*     */   }
/*     */ 
/*     */   public ClickThroughLabel(String text, int horizontalAlignment) {
/*  52 */     super(text, horizontalAlignment);
/*  53 */     installListeners();
/*     */   }
/*     */ 
/*     */   public ClickThroughLabel(String text, Icon icon, int horizontalAlignment) {
/*  57 */     super(text, icon, horizontalAlignment);
/*  58 */     installListeners();
/*     */   }
/*     */ 
/*     */   public Component getTarget() {
/*  62 */     return this._target;
/*     */   }
/*     */ 
/*     */   public void setTarget(Component target) {
/*  66 */     this._target = target;
/*     */   }
/*     */ 
/*     */   protected void installListeners() {
/*  70 */     addMouseListener(this);
/*  71 */     addMouseMotionListener(this);
/*     */   }
/*     */ 
/*     */   protected void uninstallListeners() {
/*  75 */     removeMouseListener(this);
/*  76 */     removeMouseMotionListener(this);
/*     */   }
/*     */ 
/*     */   public void mouseClicked(MouseEvent e) {
/*  80 */     JideSwingUtilities.retargetMouseEvent(e.getID(), e, getTarget());
/*     */   }
/*     */ 
/*     */   public void mousePressed(MouseEvent e) {
/*  84 */     JideSwingUtilities.retargetMouseEvent(e.getID(), e, getTarget());
/*     */   }
/*     */ 
/*     */   public void mouseReleased(MouseEvent e) {
/*  88 */     JideSwingUtilities.retargetMouseEvent(e.getID(), e, getTarget());
/*     */   }
/*     */ 
/*     */   public void mouseEntered(MouseEvent e) {
/*  92 */     JideSwingUtilities.retargetMouseEvent(e.getID(), e, getTarget());
/*     */   }
/*     */ 
/*     */   public void mouseExited(MouseEvent e) {
/*  96 */     JideSwingUtilities.retargetMouseEvent(e.getID(), e, getTarget());
/*     */   }
/*     */ 
/*     */   public void mouseDragged(MouseEvent e) {
/* 100 */     JideSwingUtilities.retargetMouseEvent(e.getID(), e, getTarget());
/*     */   }
/*     */ 
/*     */   public void mouseMoved(MouseEvent e) {
/* 104 */     JideSwingUtilities.retargetMouseEvent(e.getID(), e, getTarget());
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.ClickThroughLabel
 * JD-Core Version:    0.6.0
 */
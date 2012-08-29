/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.Timer;
/*     */ 
/*     */ public class AutoRepeatButtonUtils
/*     */   implements ActionListener, MouseListener
/*     */ {
/*  16 */   public static String AUTO_REPEAT = "AutoRepeat";
/*  17 */   public static String CLIENT_PROPERTY_AUTO_REPEAT = "AutoRepeat.AutoRepeatButtonUtils";
/*  18 */   public static int DEFAULT_DELAY = 200;
/*  19 */   public static int DEFAULT_INITIAL_DELAY = 500;
/*     */ 
/*  21 */   private Timer _timer = null;
/*     */   private AbstractButton _button;
/*     */ 
/*     */   public static void install(AbstractButton button)
/*     */   {
/*  30 */     uninstall(button);
/*  31 */     new AutoRepeatButtonUtils().installListeners(button, DEFAULT_DELAY, DEFAULT_INITIAL_DELAY);
/*     */   }
/*     */ 
/*     */   public static void install(AbstractButton button, int delay, int initialDelay)
/*     */   {
/*  43 */     uninstall(button);
/*  44 */     new AutoRepeatButtonUtils().installListeners(button, delay, initialDelay);
/*     */   }
/*     */ 
/*     */   public static void uninstall(AbstractButton button)
/*     */   {
/*  53 */     Object clientProperty = button.getClientProperty(CLIENT_PROPERTY_AUTO_REPEAT);
/*  54 */     if ((clientProperty instanceof AutoRepeatButtonUtils))
/*  55 */       ((AutoRepeatButtonUtils)clientProperty).uninstallListeners();
/*     */   }
/*     */ 
/*     */   protected void installListeners(AbstractButton button, int delay, int initialDelay)
/*     */   {
/*  60 */     this._button = button;
/*  61 */     button.putClientProperty(CLIENT_PROPERTY_AUTO_REPEAT, this);
/*  62 */     button.addMouseListener(this);
/*     */ 
/*  64 */     this._timer = new Timer(delay, this);
/*  65 */     this._timer.setInitialDelay(initialDelay);
/*  66 */     this._timer.setRepeats(true);
/*     */   }
/*     */ 
/*     */   protected void uninstallListeners() {
/*  70 */     if (this._button != null) {
/*  71 */       this._button.putClientProperty(CLIENT_PROPERTY_AUTO_REPEAT, null);
/*  72 */       this._button.removeMouseListener(this);
/*  73 */       this._button = null;
/*     */     }
/*  75 */     if (this._timer != null) {
/*  76 */       this._timer.stop();
/*  77 */       this._timer = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void mousePressed(MouseEvent e) {
/*  82 */     this._timer.start();
/*     */   }
/*     */ 
/*     */   public void mouseReleased(MouseEvent e) {
/*  86 */     this._timer.stop();
/*     */   }
/*     */ 
/*     */   public void mouseExited(MouseEvent e) {
/*  90 */     this._timer.stop();
/*     */   }
/*     */ 
/*     */   public void mouseClicked(MouseEvent e)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void mouseEntered(MouseEvent e)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent event) {
/* 102 */     if (!this._button.getModel().isPressed()) {
/* 103 */       return;
/*     */     }
/* 105 */     ActionListener[] listeners = this._button.getActionListeners();
/* 106 */     ActionEvent e = null;
/*     */ 
/* 109 */     for (int i = listeners.length - 1; i >= 0; i--) {
/* 110 */       ActionListener listener = listeners[i];
/* 111 */       if (e == null) {
/* 112 */         String actionCommand = event.getActionCommand();
/* 113 */         if (actionCommand == null) {
/* 114 */           actionCommand = this._button.getActionCommand();
/*     */         }
/* 116 */         e = new ActionEvent(this._button, 1001, actionCommand, event.getWhen(), event.getModifiers());
/*     */       }
/*     */ 
/* 122 */       listener.actionPerformed(e);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.AutoRepeatButtonUtils
 * JD-Core Version:    0.6.0
 */
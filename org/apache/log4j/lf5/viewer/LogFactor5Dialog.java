/*     */ package org.apache.log4j.lf5.viewer;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Label;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.Window;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ 
/*     */ public abstract class LogFactor5Dialog extends JDialog
/*     */ {
/*  34 */   protected static final Font DISPLAY_FONT = new Font("Arial", 1, 12);
/*     */ 
/*     */   protected LogFactor5Dialog(JFrame jframe, String message, boolean modal)
/*     */   {
/*  47 */     super(jframe, message, modal);
/*     */   }
/*     */ 
/*     */   public void show()
/*     */   {
/*  54 */     pack();
/*  55 */     minimumSizeDialog(this, 200, 100);
/*  56 */     centerWindow(this);
/*  57 */     super.show();
/*     */   }
/*     */ 
/*     */   protected void centerWindow(Window win)
/*     */   {
/*  68 */     Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
/*     */ 
/*  71 */     if (screenDim.width < win.getSize().width) {
/*  72 */       win.setSize(screenDim.width, win.getSize().height);
/*     */     }
/*     */ 
/*  75 */     if (screenDim.height < win.getSize().height) {
/*  76 */       win.setSize(win.getSize().width, screenDim.height);
/*     */     }
/*     */ 
/*  80 */     int x = (screenDim.width - win.getSize().width) / 2;
/*  81 */     int y = (screenDim.height - win.getSize().height) / 2;
/*  82 */     win.setLocation(x, y);
/*     */   }
/*     */ 
/*     */   protected void wrapStringOnPanel(String message, Container container)
/*     */   {
/*  87 */     GridBagConstraints c = getDefaultConstraints();
/*  88 */     c.gridwidth = 0;
/*     */ 
/*  90 */     c.insets = new Insets(0, 0, 0, 0);
/*  91 */     GridBagLayout gbLayout = (GridBagLayout)container.getLayout();
/*     */ 
/*  94 */     while (message.length() > 0) {
/*  95 */       int newLineIndex = message.indexOf('\n');
/*     */       String line;
/*  97 */       if (newLineIndex >= 0) {
/*  98 */         line = message.substring(0, newLineIndex);
/*  99 */         message = message.substring(newLineIndex + 1);
/*     */       } else {
/* 101 */         line = message;
/* 102 */         message = "";
/*     */       }
/* 104 */       Label label = new Label(line);
/* 105 */       label.setFont(DISPLAY_FONT);
/* 106 */       gbLayout.setConstraints(label, c);
/* 107 */       container.add(label);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected GridBagConstraints getDefaultConstraints() {
/* 112 */     GridBagConstraints constraints = new GridBagConstraints();
/* 113 */     constraints.weightx = 1.0D;
/* 114 */     constraints.weighty = 1.0D;
/* 115 */     constraints.gridheight = 1;
/*     */ 
/* 117 */     constraints.insets = new Insets(4, 4, 4, 4);
/*     */ 
/* 119 */     constraints.fill = 0;
/*     */ 
/* 121 */     constraints.anchor = 17;
/*     */ 
/* 123 */     return constraints;
/*     */   }
/*     */ 
/*     */   protected void minimumSizeDialog(Component component, int minWidth, int minHeight)
/*     */   {
/* 130 */     if (component.getSize().width < minWidth) {
/* 131 */       component.setSize(minWidth, component.getSize().height);
/*     */     }
/* 133 */     if (component.getSize().height < minHeight)
/* 134 */       component.setSize(component.getSize().width, minHeight);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.LogFactor5Dialog
 * JD-Core Version:    0.6.0
 */
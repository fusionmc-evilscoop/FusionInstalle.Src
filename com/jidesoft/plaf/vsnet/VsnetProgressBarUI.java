/*     */ package com.jidesoft.plaf.vsnet;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JProgressBar;
/*     */ import javax.swing.Timer;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicProgressBarUI;
/*     */ 
/*     */ public class VsnetProgressBarUI extends BasicProgressBarUI
/*     */   implements ActionListener
/*     */ {
/*     */   private int repaintInterval;
/*  35 */   private int x = 0; private int y = 0; private int delta = 1;
/*  36 */   private Timer timer = null;
/*     */   private Rectangle boxRect;
/*     */ 
/*     */   public static ComponentUI createUI(JComponent x)
/*     */   {
/*  40 */     return new VsnetProgressBarUI();
/*     */   }
/*     */ 
/*     */   protected void installDefaults()
/*     */   {
/*  45 */     super.installDefaults();
/*  46 */     initRepaintInterval();
/*     */   }
/*     */ 
/*     */   protected void startAnimationTimer()
/*     */   {
/*  51 */     if (this.timer == null) {
/*  52 */       this.timer = new Timer(getRepaintInterval() / 20, this);
/*     */     }
/*  54 */     this.x = (this.y = 0);
/*  55 */     this.delta = 1;
/*  56 */     this.timer.start();
/*     */   }
/*     */ 
/*     */   protected void stopAnimationTimer()
/*     */   {
/*  61 */     if (this.timer != null)
/*  62 */       this.timer.stop();
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent ae)
/*     */   {
/*  68 */     if (this.x == 0)
/*  69 */       this.delta = 1;
/*  70 */     else if (this.x == this.progressBar.getWidth())
/*  71 */       this.delta = -1;
/*  72 */     this.x += this.delta;
/*     */ 
/*  74 */     this.progressBar.repaint();
/*     */   }
/*     */ 
/*     */   protected int getRepaintInterval()
/*     */   {
/*  92 */     return this.repaintInterval;
/*     */   }
/*     */ 
/*     */   private int initRepaintInterval() {
/*  96 */     this.repaintInterval = UIDefaultsLookup.getInt("ProgressBar.repaintInterval");
/*  97 */     return this.repaintInterval;
/*     */   }
/*     */ 
/*     */   public void paintIndeterminate(Graphics g, JComponent c)
/*     */   {
/* 104 */     if (!(g instanceof Graphics2D)) {
/* 105 */       return;
/*     */     }
/*     */ 
/* 108 */     Color startColor = this.progressBar.getForeground();
/* 109 */     Color endColor = VsnetUtils.getLighterColor(startColor, 0.9F);
/*     */ 
/* 111 */     Insets b = this.progressBar.getInsets();
/* 112 */     int barRectWidth = this.progressBar.getWidth() - (b.right + b.left);
/* 113 */     int barRectHeight = this.progressBar.getHeight() - (b.top + b.bottom);
/*     */ 
/* 115 */     Graphics2D g2d = (Graphics2D)g;
/*     */ 
/* 118 */     boolean isVertical = c.getHeight() > c.getWidth();
/* 119 */     if (this.delta > 0) {
/* 120 */       this.boxRect = new Rectangle(0, 0, this.x, this.progressBar.getHeight() - 1);
/* 121 */       JideSwingUtilities.fillNormalGradient(g2d, this.boxRect, endColor, startColor, isVertical);
/*     */     }
/*     */     else {
/* 124 */       this.boxRect = new Rectangle(this.x, 0, this.progressBar.getWidth() - this.x, this.progressBar.getHeight() - 1);
/* 125 */       JideSwingUtilities.fillNormalGradient(g2d, this.boxRect, startColor, endColor, isVertical);
/*     */     }
/*     */ 
/* 129 */     if (this.progressBar.isStringPainted())
/* 130 */       if (this.progressBar.getOrientation() == 0) {
/* 131 */         paintString(g2d, b.left, b.top, barRectWidth, barRectHeight, this.boxRect.x, this.boxRect.width, b);
/*     */       }
/*     */       else
/*     */       {
/* 136 */         paintString(g2d, b.left, b.top, barRectWidth, barRectHeight, this.boxRect.y, this.boxRect.height, b);
/*     */       }
/*     */   }
/*     */ 
/*     */   private void paintString(Graphics g, int x, int y, int width, int height, int fillStart, int amountFull, Insets b)
/*     */   {
/* 159 */     if (!(g instanceof Graphics2D)) {
/* 160 */       return;
/*     */     }
/*     */ 
/* 163 */     Graphics2D g2 = (Graphics2D)g;
/* 164 */     String progressString = this.progressBar.getString();
/* 165 */     g2.setFont(this.progressBar.getFont());
/* 166 */     Point renderLocation = getStringPlacement(g2, progressString, x, y, width, height);
/*     */ 
/* 168 */     Rectangle oldClip = g2.getClipBounds();
/*     */ 
/* 170 */     if (this.progressBar.getOrientation() == 0) {
/* 171 */       g2.setColor(getSelectionBackground());
/* 172 */       JideSwingUtilities.drawString(this.progressBar, g2, progressString, renderLocation.x, renderLocation.y);
/*     */ 
/* 174 */       g2.setColor(getSelectionForeground());
/* 175 */       g2.clipRect(fillStart, y, amountFull, height);
/* 176 */       JideSwingUtilities.drawString(this.progressBar, g2, progressString, renderLocation.x, renderLocation.y);
/*     */     }
/*     */     else
/*     */     {
/* 180 */       g2.setColor(getSelectionBackground());
/* 181 */       AffineTransform rotate = AffineTransform.getRotateInstance(1.570796326794897D);
/*     */ 
/* 183 */       g2.setFont(this.progressBar.getFont().deriveFont(rotate));
/* 184 */       renderLocation = getStringPlacement(g2, progressString, x, y, width, height);
/*     */ 
/* 186 */       JideSwingUtilities.drawString(this.progressBar, g2, progressString, renderLocation.x, renderLocation.y);
/*     */ 
/* 188 */       g2.setColor(getSelectionForeground());
/* 189 */       g2.clipRect(x, fillStart, width, amountFull);
/* 190 */       JideSwingUtilities.drawString(this.progressBar, g2, progressString, renderLocation.x, renderLocation.y);
/*     */     }
/*     */ 
/* 193 */     g2.setClip(oldClip);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.vsnet.VsnetProgressBarUI
 * JD-Core Version:    0.6.0
 */
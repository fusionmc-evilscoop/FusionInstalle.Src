/*     */ package com.jidesoft.plaf.vsnet;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import java.awt.BorderLayout;
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
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JProgressBar;
/*     */ import javax.swing.Timer;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.metal.MetalProgressBarUI;
/*     */ 
/*     */ public class VsnetMetalProgressBarUI extends MetalProgressBarUI
/*     */   implements ActionListener
/*     */ {
/*     */   private int repaintInterval;
/*  35 */   private int x = 0; private int y = 0; private int delta = 1;
/*  36 */   private Timer timer = null;
/*     */   private Rectangle boxRect;
/*     */ 
/*     */   public static ComponentUI createUI(JComponent x)
/*     */   {
/*  40 */     return new VsnetMetalProgressBarUI();
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
/* 104 */     super.paintDeterminate(g, c);
/*     */ 
/* 106 */     if (!(g instanceof Graphics2D)) {
/* 107 */       return;
/*     */     }
/*     */ 
/* 110 */     Color startColor = this.progressBar.getForeground();
/* 111 */     Color endColor = VsnetUtils.getLighterColor(startColor, 0.9F);
/*     */ 
/* 113 */     Insets b = this.progressBar.getInsets();
/* 114 */     int barRectWidth = this.progressBar.getWidth() - (b.right + b.left);
/* 115 */     int barRectHeight = this.progressBar.getHeight() - (b.top + b.bottom);
/*     */ 
/* 117 */     Graphics2D g2d = (Graphics2D)g;
/*     */ 
/* 120 */     boolean isVertical = c.getHeight() > c.getWidth();
/* 121 */     if (this.delta > 0) {
/* 122 */       this.boxRect = new Rectangle(0, 0, this.x, this.progressBar.getHeight() - 1);
/* 123 */       JideSwingUtilities.fillNormalGradient(g2d, this.boxRect, endColor, startColor, isVertical);
/*     */     }
/*     */     else {
/* 126 */       this.boxRect = new Rectangle(this.x, 0, this.progressBar.getWidth() - this.x, this.progressBar.getHeight() - 1);
/* 127 */       JideSwingUtilities.fillNormalGradient(g2d, this.boxRect, startColor, endColor, isVertical);
/*     */     }
/*     */ 
/* 131 */     if (this.progressBar.isStringPainted())
/* 132 */       if (this.progressBar.getOrientation() == 0) {
/* 133 */         paintString(g2d, b.left, b.top, barRectWidth, barRectHeight, this.boxRect.x, this.boxRect.width, b);
/*     */       }
/*     */       else
/*     */       {
/* 138 */         paintString(g2d, b.left, b.top, barRectWidth, barRectHeight, this.boxRect.y, this.boxRect.height, b);
/*     */       }
/*     */   }
/*     */ 
/*     */   private void paintString(Graphics g, int x, int y, int width, int height, int fillStart, int amountFull, Insets b)
/*     */   {
/* 161 */     if (!(g instanceof Graphics2D)) {
/* 162 */       return;
/*     */     }
/*     */ 
/* 165 */     Graphics2D g2 = (Graphics2D)g;
/* 166 */     String progressString = this.progressBar.getString();
/* 167 */     g2.setFont(this.progressBar.getFont());
/* 168 */     Point renderLocation = getStringPlacement(g2, progressString, x, y, width, height);
/*     */ 
/* 170 */     Rectangle oldClip = g2.getClipBounds();
/*     */ 
/* 172 */     if (this.progressBar.getOrientation() == 0) {
/* 173 */       g2.setColor(getSelectionBackground());
/* 174 */       JideSwingUtilities.drawString(this.progressBar, g2, progressString, renderLocation.x, renderLocation.y);
/*     */ 
/* 176 */       g2.setColor(getSelectionForeground());
/* 177 */       g2.clipRect(fillStart, y, amountFull, height);
/* 178 */       JideSwingUtilities.drawString(this.progressBar, g2, progressString, renderLocation.x, renderLocation.y);
/*     */     }
/*     */     else
/*     */     {
/* 182 */       g2.setColor(getSelectionBackground());
/* 183 */       AffineTransform rotate = AffineTransform.getRotateInstance(1.570796326794897D);
/*     */ 
/* 185 */       g2.setFont(this.progressBar.getFont().deriveFont(rotate));
/* 186 */       renderLocation = getStringPlacement(g2, progressString, x, y, width, height);
/*     */ 
/* 188 */       JideSwingUtilities.drawString(this.progressBar, g2, progressString, renderLocation.x, renderLocation.y);
/*     */ 
/* 190 */       g2.setColor(getSelectionForeground());
/* 191 */       g2.clipRect(x, fillStart, width, amountFull);
/* 192 */       JideSwingUtilities.drawString(this.progressBar, g2, progressString, renderLocation.x, renderLocation.y);
/*     */     }
/*     */ 
/* 195 */     g2.setClip(oldClip);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */     try {
/* 201 */       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/*     */     }
/*     */     catch (Exception e) {
/* 204 */       e.printStackTrace();
/*     */     }
/* 206 */     JProgressBar progressBar = new JProgressBar();
/* 207 */     JProgressBar myProgressBar = new JProgressBar();
/* 208 */     myProgressBar.setUI(new VsnetMetalProgressBarUI());
/* 209 */     progressBar.setIndeterminate(true);
/* 210 */     progressBar.setString("Percent");
/* 211 */     progressBar.setStringPainted(true);
/* 212 */     myProgressBar.setIndeterminate(true);
/* 213 */     myProgressBar.setString("Percent");
/* 214 */     myProgressBar.setStringPainted(true);
/* 215 */     JPanel panel = new JPanel(new BorderLayout(5, 5));
/* 216 */     panel.add(progressBar, "North");
/* 217 */     panel.add(myProgressBar, "South");
/* 218 */     JOptionPane.showMessageDialog(null, panel, "ProgressBars made intutive - santhosh@in.fiorano.com", 1);
/* 219 */     System.exit(0);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.vsnet.VsnetMetalProgressBarUI
 * JD-Core Version:    0.6.0
 */
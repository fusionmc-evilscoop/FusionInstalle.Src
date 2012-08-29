/*     */ package com.jidesoft.plaf.vsnet;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import com.sun.java.swing.plaf.windows.WindowsProgressBarUI;
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
/*     */ 
/*     */ public class VsnetWindowsProgressBarUI extends WindowsProgressBarUI
/*     */   implements ActionListener
/*     */ {
/*     */   private int repaintInterval;
/*  35 */   private int x = 0; private int y = 0; private int delta = 1;
/*  36 */   private Timer timer = null;
/*     */   private Rectangle boxRect;
/*     */ 
/*     */   public static ComponentUI createUI(JComponent x)
/*     */   {
/*  40 */     return new VsnetWindowsProgressBarUI();
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
/* 104 */     super.paintIndeterminate(g, c);
/* 105 */     Color startColor = this.progressBar.getForeground();
/* 106 */     Color endColor = VsnetUtils.getLighterColor(startColor, 0.9F);
/*     */ 
/* 108 */     if (!(g instanceof Graphics2D)) {
/* 109 */       return;
/*     */     }
/*     */ 
/* 112 */     boolean vertical = this.progressBar.getOrientation() == 1;
/*     */ 
/* 114 */     Insets b = this.progressBar.getInsets();
/* 115 */     b.top = 2;
/* 116 */     b.left = 2;
/* 117 */     b.right = 2;
/* 118 */     b.bottom = 2;
/* 119 */     int barRectWidth = this.progressBar.getWidth() - (b.right + b.left);
/* 120 */     int barRectHeight = this.progressBar.getHeight() - (b.top + b.bottom);
/* 121 */     g.setColor(this.progressBar.getBackground());
/* 122 */     g.fillRect(b.left, b.top, barRectWidth, barRectHeight);
/*     */ 
/* 124 */     Graphics2D g2d = (Graphics2D)g;
/*     */ 
/* 127 */     if (this.delta > 0) {
/* 128 */       this.boxRect = new Rectangle(b.left, b.top, this.x, barRectHeight);
/* 129 */       JideSwingUtilities.fillNormalGradient(g2d, this.boxRect, endColor, startColor, vertical);
/*     */     }
/*     */     else {
/* 132 */       this.boxRect = new Rectangle(this.x, b.top, barRectWidth - this.x, barRectHeight);
/* 133 */       JideSwingUtilities.fillNormalGradient(g2d, this.boxRect, startColor, endColor, vertical);
/*     */     }
/*     */ 
/* 137 */     if (this.progressBar.isStringPainted())
/* 138 */       if (this.progressBar.getOrientation() == 0) {
/* 139 */         paintString(g2d, b.left, b.top, barRectWidth, barRectHeight, this.boxRect.x, this.boxRect.width, b);
/*     */       }
/*     */       else
/*     */       {
/* 144 */         paintString(g2d, b.left, b.top, barRectWidth, barRectHeight, this.boxRect.y, this.boxRect.height, b);
/*     */       }
/*     */   }
/*     */ 
/*     */   private void paintString(Graphics g, int x, int y, int width, int height, int fillStart, int amountFull, Insets b)
/*     */   {
/* 167 */     if (!(g instanceof Graphics2D)) {
/* 168 */       return;
/*     */     }
/*     */ 
/* 171 */     Graphics2D g2 = (Graphics2D)g;
/* 172 */     String progressString = this.progressBar.getString();
/* 173 */     g2.setFont(this.progressBar.getFont());
/* 174 */     Point renderLocation = getStringPlacement(g2, progressString, x, y, width, height);
/*     */ 
/* 176 */     Rectangle oldClip = g2.getClipBounds();
/*     */ 
/* 178 */     if (this.progressBar.getOrientation() == 0) {
/* 179 */       g2.setColor(getSelectionBackground());
/* 180 */       JideSwingUtilities.drawString(this.progressBar, g2, progressString, renderLocation.x, renderLocation.y);
/*     */ 
/* 182 */       g2.setColor(getSelectionForeground());
/* 183 */       g2.clipRect(fillStart, y, amountFull, height);
/* 184 */       JideSwingUtilities.drawString(this.progressBar, g2, progressString, renderLocation.x, renderLocation.y);
/*     */     }
/*     */     else
/*     */     {
/* 188 */       g2.setColor(getSelectionBackground());
/* 189 */       AffineTransform rotate = AffineTransform.getRotateInstance(1.570796326794897D);
/*     */ 
/* 191 */       g2.setFont(this.progressBar.getFont().deriveFont(rotate));
/* 192 */       renderLocation = getStringPlacement(g2, progressString, x, y, width, height);
/*     */ 
/* 194 */       JideSwingUtilities.drawString(this.progressBar, g2, progressString, renderLocation.x, renderLocation.y);
/*     */ 
/* 196 */       g2.setColor(getSelectionForeground());
/* 197 */       g2.clipRect(x, fillStart, width, amountFull);
/* 198 */       JideSwingUtilities.drawString(this.progressBar, g2, progressString, renderLocation.x, renderLocation.y);
/*     */     }
/*     */ 
/* 201 */     g2.setClip(oldClip);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */     try {
/* 207 */       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/*     */     }
/*     */     catch (Exception e) {
/* 210 */       e.printStackTrace();
/*     */     }
/* 212 */     JProgressBar progressBar = new JProgressBar();
/* 213 */     JProgressBar myProgressBar = new JProgressBar();
/* 214 */     myProgressBar.setUI(new VsnetWindowsProgressBarUI());
/* 215 */     progressBar.setIndeterminate(true);
/* 216 */     progressBar.setString("Percent");
/* 217 */     progressBar.setStringPainted(true);
/* 218 */     myProgressBar.setIndeterminate(true);
/* 219 */     myProgressBar.setString("Percent");
/* 220 */     myProgressBar.setStringPainted(true);
/* 221 */     JPanel panel = new JPanel(new BorderLayout(5, 5));
/* 222 */     panel.add(progressBar, "North");
/* 223 */     panel.add(myProgressBar, "South");
/* 224 */     JOptionPane.showMessageDialog(null, panel, "ProgressBars made intutive - santhosh@in.fiorano.com", 1);
/* 225 */     System.exit(0);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.vsnet.VsnetWindowsProgressBarUI
 * JD-Core Version:    0.6.0
 */
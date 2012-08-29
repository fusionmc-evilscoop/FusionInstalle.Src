/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Area;
/*     */ import java.awt.geom.Point2D.Double;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.Timer;
/*     */ 
/*     */ public class InfiniteProgressPanel extends JComponent
/*     */   implements ActionListener
/*     */ {
/*     */   private static final int DEFAULT_NUMBER_OF_BARS = 12;
/*     */   private int numBars;
/*  37 */   private double dScale = 1.2D;
/*     */   private Area[] bars;
/*  40 */   private Rectangle barsBounds = null;
/*  41 */   private Rectangle barsScreenBounds = null;
/*  42 */   private AffineTransform centerAndScaleTransform = null;
/*  43 */   private Timer timer = new Timer(62, this);
/*  44 */   private Color[] colors = null;
/*  45 */   private int colorOffset = 0;
/*  46 */   private boolean tempHide = false;
/*     */ 
/*     */   public InfiniteProgressPanel() {
/*  49 */     this(1.0D);
/*     */   }
/*     */ 
/*     */   public InfiniteProgressPanel(double ratio) {
/*  53 */     this.numBars = 12;
/*     */ 
/*  55 */     this.colors = new Color[this.numBars * 2];
/*     */ 
/*  58 */     this.bars = buildTicker(this.numBars, ratio);
/*     */ 
/*  61 */     this.barsBounds = new Rectangle();
/*  62 */     for (Area bar : this.bars) {
/*  63 */       this.barsBounds = this.barsBounds.union(bar.getBounds());
/*     */     }
/*     */ 
/*  67 */     for (int i = 0; i < this.bars.length; i++) {
/*  68 */       int channel = 224 - 128 / (i + 1);
/*  69 */       this.colors[i] = new Color(channel, channel, channel);
/*  70 */       this.colors[(this.numBars + i)] = this.colors[i];
/*     */     }
/*     */ 
/*  77 */     setOpaque(true);
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/*  85 */     if (this.colorOffset == this.numBars - 1) {
/*  86 */       this.colorOffset = 0;
/*     */     }
/*     */     else {
/*  89 */       this.colorOffset += 1;
/*     */     }
/*     */ 
/*  92 */     if (this.barsScreenBounds != null) {
/*  93 */       repaint(this.barsScreenBounds);
/*     */     }
/*     */     else
/*  96 */       repaint();
/*     */   }
/*     */ 
/*     */   public void setVisible(boolean i_bIsVisible)
/*     */   {
/* 105 */     setOpaque(false);
/*     */ 
/* 107 */     if (i_bIsVisible)
/*     */     {
/* 109 */       this.timer.start();
/*     */     }
/*     */     else
/*     */     {
/* 113 */       this.timer.stop();
/*     */     }
/* 115 */     super.setVisible(i_bIsVisible);
/*     */   }
/*     */ 
/*     */   public void setBounds(int x, int y, int width, int height)
/*     */   {
/* 123 */     super.setBounds(x, y, width, height);
/*     */ 
/* 125 */     this.centerAndScaleTransform = new AffineTransform();
/* 126 */     this.centerAndScaleTransform.translate(getWidth() / 2.0D, getHeight() / 2.0D);
/* 127 */     this.centerAndScaleTransform.scale(this.dScale, this.dScale);
/*     */ 
/* 129 */     if (this.barsBounds != null) {
/* 130 */       Area oBounds = new Area(this.barsBounds);
/* 131 */       oBounds.transform(this.centerAndScaleTransform);
/* 132 */       this.barsScreenBounds = oBounds.getBounds();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void paintComponent(Graphics g)
/*     */   {
/* 141 */     if (!this.tempHide) {
/* 142 */       Rectangle oClip = g.getClipBounds();
/*     */ 
/* 144 */       if (isOpaque()) {
/* 145 */         g.setColor(getBackground());
/* 146 */         g.fillRect(oClip.x, oClip.y, oClip.width, oClip.height);
/*     */       }
/*     */ 
/* 150 */       Graphics2D g2 = (Graphics2D)g.create();
/* 151 */       g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/* 152 */       g2.transform(this.centerAndScaleTransform);
/*     */ 
/* 154 */       for (int i = 0; i < this.bars.length; i++) {
/* 155 */         g2.setColor(this.colors[(i + this.colorOffset)]);
/* 156 */         g2.fill(this.bars[i]);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static Area[] buildTicker(int i_iBarCount, double ratio)
/*     */   {
/* 165 */     Area[] ticker = new Area[i_iBarCount];
/* 166 */     Point2D.Double center = new Point2D.Double(0.0D, 0.0D);
/* 167 */     double fixedAngle = 6.283185307179586D / i_iBarCount;
/*     */ 
/* 169 */     for (double i = 0.0D; i < i_iBarCount; i += 1.0D) {
/* 170 */       Area primitive = buildPrimitive(ratio);
/*     */ 
/* 172 */       AffineTransform toCenter = AffineTransform.getTranslateInstance(center.getX(), center.getY());
/* 173 */       AffineTransform toBorder = AffineTransform.getTranslateInstance(2.0D, -0.4D);
/* 174 */       AffineTransform toCircle = AffineTransform.getRotateInstance(-i * fixedAngle, center.getX(), center.getY());
/*     */ 
/* 176 */       AffineTransform toWheel = new AffineTransform();
/* 177 */       toWheel.concatenate(toCenter);
/* 178 */       toWheel.concatenate(toBorder);
/*     */ 
/* 180 */       primitive.transform(toWheel);
/* 181 */       primitive.transform(toCircle);
/*     */ 
/* 183 */       ticker[(int)i] = primitive;
/*     */     }
/*     */ 
/* 186 */     return ticker;
/*     */   }
/*     */ 
/*     */   private static Area buildPrimitive(double ratio)
/*     */   {
/* 202 */     Rectangle2D.Double body = new Rectangle2D.Double(2.0D * ratio, 0.0D, 4.0D * ratio, ratio);
/*     */ 
/* 210 */     return new Area(body);
/*     */   }
/*     */ 
/*     */   public void start() {
/* 214 */     setVisible(true);
/*     */   }
/*     */ 
/*     */   public void stop() {
/* 218 */     setVisible(false);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.InfiniteProgressPanel
 * JD-Core Version:    0.6.0
 */
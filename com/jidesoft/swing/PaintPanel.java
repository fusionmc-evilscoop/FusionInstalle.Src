/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Image;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.TexturePaint;
/*     */ import java.awt.image.BufferedImage;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class PaintPanel extends JPanel
/*     */ {
/*     */   private Paint _backgroundPaint;
/*     */   protected Color _startColor;
/*     */   protected Color _endColor;
/*     */   protected boolean _isVertical;
/*     */ 
/*     */   public PaintPanel()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PaintPanel(boolean isDoubleBuffered)
/*     */   {
/*  22 */     super(isDoubleBuffered);
/*     */   }
/*     */ 
/*     */   public PaintPanel(LayoutManager layout) {
/*  26 */     super(layout);
/*     */   }
/*     */ 
/*     */   public PaintPanel(LayoutManager layout, boolean isDoubleBuffered) {
/*  30 */     super(layout, isDoubleBuffered);
/*     */   }
/*     */ 
/*     */   public Paint getBackgroundPaint()
/*     */   {
/*  39 */     return this._backgroundPaint;
/*     */   }
/*     */ 
/*     */   public void setBackgroundPaint(Paint backgroundPaint)
/*     */   {
/*  48 */     this._backgroundPaint = backgroundPaint;
/*     */   }
/*     */ 
/*     */   public static TexturePaint createTexturePaint(JPanel panel, Image img, int x, int y, int w, int h) {
/*  52 */     BufferedImage bi = new BufferedImage(w, h, 2);
/*  53 */     Graphics2D tG2 = bi.createGraphics();
/*  54 */     tG2.drawImage(img, x, y, Color.white, panel);
/*  55 */     Rectangle r = new Rectangle(0, 0, w, h);
/*  56 */     return new TexturePaint(bi, r);
/*     */   }
/*     */ 
/*     */   public void setGradientPaint(Color startColor, Color endColor, boolean isVertical)
/*     */   {
/*  73 */     setStartColor(startColor);
/*  74 */     setEndColor(endColor);
/*  75 */     setVertical(isVertical);
/*     */   }
/*     */ 
/*     */   public Color getStartColor() {
/*  79 */     return this._startColor;
/*     */   }
/*     */ 
/*     */   public void setStartColor(Color startColor) {
/*  83 */     this._startColor = startColor;
/*     */   }
/*     */ 
/*     */   public Color getEndColor() {
/*  87 */     return this._endColor;
/*     */   }
/*     */ 
/*     */   public void setEndColor(Color endColor) {
/*  91 */     this._endColor = endColor;
/*     */   }
/*     */ 
/*     */   public boolean isVertical() {
/*  95 */     return this._isVertical;
/*     */   }
/*     */ 
/*     */   public void setVertical(boolean vertical) {
/*  99 */     this._isVertical = vertical;
/*     */   }
/*     */ 
/*     */   protected void paintComponent(Graphics g)
/*     */   {
/* 109 */     super.paintComponent(g);
/* 110 */     if ((getStartColor() != null) && (getEndColor() != null)) {
/* 111 */       JideSwingUtilities.fillGradient((Graphics2D)g, new Rectangle(0, 0, getWidth(), getHeight()), getStartColor(), getEndColor(), isVertical());
/*     */     }
/* 113 */     else if ((isOpaque()) && (getBackgroundPaint() != null)) {
/* 114 */       Graphics2D g2d = (Graphics2D)g;
/* 115 */       g2d.setPaint(getBackgroundPaint());
/* 116 */       g2d.fillRect(0, 0, getWidth(), getHeight());
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.PaintPanel
 * JD-Core Version:    0.6.0
 */
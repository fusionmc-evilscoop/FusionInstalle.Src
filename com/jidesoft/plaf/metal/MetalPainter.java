/*     */ package com.jidesoft.plaf.metal;
/*     */ 
/*     */ import com.jidesoft.plaf.basic.BasicPainter;
/*     */ import com.jidesoft.plaf.basic.ThemePainter;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.GradientPaint;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.metal.MetalLookAndFeel;
/*     */ 
/*     */ public class MetalPainter extends BasicPainter
/*     */ {
/*     */   private static MetalPainter _instance;
/*     */ 
/*     */   public static ThemePainter getInstance()
/*     */   {
/*  26 */     if (_instance == null) {
/*  27 */       _instance = new MetalPainter();
/*     */     }
/*  29 */     return _instance;
/*     */   }
/*     */ 
/*     */   public void paintGripper(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/*  37 */     if (orientation == 0) {
/*  38 */       MetalBumps bumps = new MetalBumps(rect.width, rect.height - 6, state == 3 ? MetalLookAndFeel.getPrimaryControlHighlight() : MetalLookAndFeel.getControlHighlight(), state == 3 ? MetalLookAndFeel.getPrimaryControlDarkShadow() : MetalLookAndFeel.getControlDarkShadow(), null);
/*     */ 
/*  42 */       bumps.paintIcon(null, g, rect.x, rect.y + 3);
/*     */     }
/*     */     else {
/*  45 */       MetalBumps bumps = new MetalBumps(rect.width - 6, rect.height, state == 3 ? MetalLookAndFeel.getPrimaryControlHighlight() : MetalLookAndFeel.getControlHighlight(), state == 3 ? MetalLookAndFeel.getPrimaryControlDarkShadow() : MetalLookAndFeel.getControlDarkShadow(), null);
/*     */ 
/*  49 */       bumps.paintIcon(null, g, rect.x + 3, rect.y);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintDockableFrameTitlePane(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/*  55 */     boolean isSelected = state == 3;
/*     */ 
/*  57 */     int width = rect.width;
/*  58 */     int height = rect.height;
/*     */ 
/*  60 */     Color background = isSelected ? MetalLookAndFeel.getWindowTitleBackground() : MetalLookAndFeel.getWindowTitleInactiveBackground();
/*  61 */     String gradientKey = isSelected ? "InternalFrame.activeTitleGradient" : "InternalFrame.inactiveTitleGradient";
/*  62 */     if (!drawGradient(c, g, gradientKey, 0, 0, width, height, true)) {
/*  63 */       g.setColor(background);
/*  64 */       g.fillRect(0, 0, width, height);
/*     */     }
/*     */   }
/*     */ 
/*     */   static boolean drawGradient(Component c, Graphics g, String key, int x, int y, int w, int h, boolean vertical)
/*     */   {
/*  90 */     Object colors = UIManager.get(key);
/*  91 */     if (!(colors instanceof java.awt.List)) {
/*  92 */       return false;
/*     */     }
/*     */ 
/*  95 */     java.util.List gradient = (java.util.List)colors;
/*  96 */     if ((gradient == null) || (!(g instanceof Graphics2D))) {
/*  97 */       return false;
/*     */     }
/*     */ 
/* 100 */     if ((w <= 0) || (h <= 0)) {
/* 101 */       return true;
/*     */     }
/*     */ 
/* 104 */     GradientPainter.INSTANCE.paint(c, (Graphics2D)g, gradient, x, y, w, h, vertical);
/*     */ 
/* 106 */     return true;
/*     */   }
/*     */ 
/*     */   private static class GradientPainter
/*     */   {
/* 114 */     public static final GradientPainter INSTANCE = new GradientPainter();
/*     */ 
/*     */     public void paint(Component c, Graphics2D g, java.util.List gradient, int x, int y, int w, int h, boolean isVertical)
/*     */     {
/*     */       int imageHeight;
/*     */       int imageWidth;
/*     */       int imageHeight;
/* 125 */       if (isVertical) {
/* 126 */         int imageWidth = w;
/* 127 */         imageHeight = h;
/*     */       }
/*     */       else {
/* 130 */         imageWidth = w;
/* 131 */         imageHeight = h;
/*     */       }
/* 133 */       synchronized (c.getTreeLock()) {
/* 134 */         paint(c, g, imageWidth, imageHeight, gradient, isVertical);
/*     */       }
/*     */     }
/*     */ 
/*     */     protected void paint(Component c, Graphics g, int w, int h, java.util.List gradient, boolean isVertical) {
/* 139 */       Graphics2D g2 = (Graphics2D)g;
/*     */ 
/* 141 */       if (isVertical) {
/* 142 */         drawVerticalGradient(g2, ((Number)gradient.get(0)).floatValue(), ((Number)gradient.get(1)).floatValue(), (Color)gradient.get(2), (Color)gradient.get(3), (Color)gradient.get(4), w, h);
/*     */       }
/*     */       else
/*     */       {
/* 150 */         drawHorizontalGradient(g2, ((Number)gradient.get(0)).floatValue(), ((Number)gradient.get(1)).floatValue(), (Color)gradient.get(2), (Color)gradient.get(3), (Color)gradient.get(4), w, h);
/*     */       }
/*     */     }
/*     */ 
/*     */     private void drawVerticalGradient(Graphics2D g, float ratio1, float ratio2, Color c1, Color c2, Color c3, int w, int h)
/*     */     {
/* 162 */       int mid = (int)(ratio1 * h);
/* 163 */       int mid2 = (int)(ratio2 * h);
/* 164 */       if (mid > 0) {
/* 165 */         g.setPaint(getGradient(0.0F, 0.0F, c1, 0.0F, mid, c2));
/*     */ 
/* 167 */         g.fillRect(0, 0, w, mid);
/*     */       }
/* 169 */       if (mid2 > 0) {
/* 170 */         g.setColor(c2);
/* 171 */         g.fillRect(0, mid, w, mid2);
/*     */       }
/* 173 */       if (mid > 0) {
/* 174 */         g.setPaint(getGradient(0.0F, mid + mid2, c2, 0.0F, mid * 2.0F + mid2, c1));
/*     */ 
/* 176 */         g.fillRect(0, mid + mid2, w, mid);
/*     */       }
/* 178 */       if (h - mid * 2 - mid2 > 0) {
/* 179 */         g.setPaint(getGradient(0.0F, mid * 2.0F + mid2, c1, 0.0F, h, c3));
/*     */ 
/* 181 */         g.fillRect(0, mid * 2 + mid2, w, h - mid * 2 - mid2);
/*     */       }
/*     */     }
/*     */ 
/*     */     private void drawHorizontalGradient(Graphics2D g, float ratio1, float ratio2, Color c1, Color c2, Color c3, int w, int h)
/*     */     {
/* 188 */       int mid = (int)(ratio1 * w);
/* 189 */       int mid2 = (int)(ratio2 * w);
/* 190 */       if (mid > 0) {
/* 191 */         g.setPaint(getGradient(0.0F, 0.0F, c1, mid, 0.0F, c2));
/*     */ 
/* 193 */         g.fillRect(0, 0, mid, h);
/*     */       }
/* 195 */       if (mid2 > 0) {
/* 196 */         g.setColor(c2);
/* 197 */         g.fillRect(mid, 0, mid2, h);
/*     */       }
/* 199 */       if (mid > 0) {
/* 200 */         g.setPaint(getGradient(mid + mid2, 0.0F, c2, mid * 2.0F + mid2, 0.0F, c1));
/*     */ 
/* 202 */         g.fillRect(mid + mid2, 0, mid, h);
/*     */       }
/* 204 */       if (w - mid * 2 - mid2 > 0) {
/* 205 */         g.setPaint(getGradient(mid * 2.0F + mid2, 0.0F, c1, w, 0.0F, c3));
/*     */ 
/* 207 */         g.fillRect(mid * 2 + mid2, 0, w - mid * 2 - mid2, h);
/*     */       }
/*     */     }
/*     */ 
/*     */     private GradientPaint getGradient(float x1, float y1, Color c1, float x2, float y2, Color c2)
/*     */     {
/* 214 */       return new GradientPaint(x1, y1, c1, x2, y2, c2, true);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.metal.MetalPainter
 * JD-Core Version:    0.6.0
 */
/*     */ package com.jidesoft.plaf.metal;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.plaf.metal.MetalLookAndFeel;
/*     */ 
/*     */ class MetalBumps
/*     */   implements Icon
/*     */ {
/*  28 */   static final Color ALPHA = new Color(0, 0, 0, 0);
/*     */   protected int xBumps;
/*     */   protected int yBumps;
/*     */   protected Color topColor;
/*     */   protected Color shadowColor;
/*     */   protected Color backColor;
/*  36 */   protected static Vector buffers = new Vector();
/*     */   protected BumpBuffer buffer;
/*     */ 
/*     */   public MetalBumps(Dimension bumpArea)
/*     */   {
/*  40 */     this(bumpArea.width, bumpArea.height);
/*     */   }
/*     */ 
/*     */   public MetalBumps(int width, int height) {
/*  44 */     this(width, height, MetalLookAndFeel.getPrimaryControlHighlight(), MetalLookAndFeel.getPrimaryControlDarkShadow(), MetalLookAndFeel.getPrimaryControlShadow());
/*     */   }
/*     */ 
/*     */   public MetalBumps(int width, int height, Color newTopColor, Color newShadowColor, Color newBackColor)
/*     */   {
/*  56 */     setBumpArea(width, height);
/*  57 */     setBumpColors(newTopColor, newShadowColor, newBackColor);
/*     */   }
/*     */ 
/*     */   private BumpBuffer getBuffer(GraphicsConfiguration gc, Color aTopColor, Color aShadowColor, Color aBackColor)
/*     */   {
/*  62 */     if ((this.buffer != null) && (this.buffer.hasSameConfiguration(gc, aTopColor, aShadowColor, aBackColor)))
/*     */     {
/*  64 */       return this.buffer;
/*     */     }
/*  66 */     BumpBuffer result = null;
/*     */ 
/*  68 */     Enumeration elements = buffers.elements();
/*     */ 
/*  70 */     while (elements.hasMoreElements()) {
/*  71 */       BumpBuffer aBuffer = (BumpBuffer)elements.nextElement();
/*  72 */       if (aBuffer.hasSameConfiguration(gc, aTopColor, aShadowColor, aBackColor))
/*     */       {
/*  74 */         result = aBuffer;
/*  75 */         break;
/*     */       }
/*     */     }
/*  78 */     if (result == null) {
/*  79 */       result = new BumpBuffer(gc, this.topColor, this.shadowColor, this.backColor);
/*  80 */       buffers.addElement(result);
/*     */     }
/*  82 */     return result;
/*     */   }
/*     */ 
/*     */   public void setBumpArea(Dimension bumpArea) {
/*  86 */     setBumpArea(bumpArea.width, bumpArea.height);
/*     */   }
/*     */ 
/*     */   public void setBumpArea(int width, int height) {
/*  90 */     this.xBumps = (width / 2);
/*  91 */     this.yBumps = (height / 2);
/*     */   }
/*     */ 
/*     */   public void setBumpColors(Color newTopColor, Color newShadowColor, Color newBackColor) {
/*  95 */     this.topColor = newTopColor;
/*  96 */     this.shadowColor = newShadowColor;
/*  97 */     if (newBackColor == null) {
/*  98 */       this.backColor = ALPHA;
/*     */     }
/*     */     else
/* 101 */       this.backColor = newBackColor;
/*     */   }
/*     */ 
/*     */   public void paintIcon(Component c, Graphics g, int x, int y)
/*     */   {
/* 106 */     GraphicsConfiguration gc = (g instanceof Graphics2D) ? ((Graphics2D)g).getDeviceConfiguration() : null;
/*     */ 
/* 110 */     this.buffer = getBuffer(gc, this.topColor, this.shadowColor, this.backColor);
/*     */ 
/* 112 */     int bufferWidth = this.buffer.getImageSize().width;
/* 113 */     int bufferHeight = this.buffer.getImageSize().height;
/* 114 */     int iconWidth = getIconWidth();
/* 115 */     int iconHeight = getIconHeight();
/* 116 */     int x2 = x + iconWidth;
/* 117 */     int y2 = y + iconHeight;
/* 118 */     int savex = x;
/*     */ 
/* 120 */     while (y < y2) {
/* 121 */       int h = Math.min(y2 - y, bufferHeight);
/* 122 */       for (x = savex; x < x2; x += bufferWidth) {
/* 123 */         int w = Math.min(x2 - x, bufferWidth);
/* 124 */         g.drawImage(this.buffer.getImage(), x, y, x + w, y + h, 0, 0, w, h, null);
/*     */       }
/*     */ 
/* 129 */       y += bufferHeight;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getIconWidth() {
/* 134 */     return this.xBumps * 2;
/*     */   }
/*     */ 
/*     */   public int getIconHeight() {
/* 138 */     return this.yBumps * 2;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.metal.MetalBumps
 * JD-Core Version:    0.6.0
 */
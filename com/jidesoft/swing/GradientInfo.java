/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ 
/*     */ class GradientInfo
/*     */ {
/*     */   GraphicsConfiguration gfxConfig;
/*     */   int length;
/*     */   Color startColor;
/*     */   Color endColor;
/*     */   boolean isVertical;
/*     */ 
/*     */   public GradientInfo(GraphicsConfiguration gc, int ln, Color sc, Color ec, boolean v)
/*     */   {
/* 118 */     this.gfxConfig = gc;
/* 119 */     this.length = ln;
/* 120 */     this.startColor = sc;
/* 121 */     this.endColor = ec;
/* 122 */     this.isVertical = v;
/*     */   }
/*     */ 
/*     */   boolean isEquivalent(GradientInfo gi) {
/* 126 */     return (gi.gfxConfig.equals(this.gfxConfig)) && (gi.length == this.length) && (gi.startColor.equals(this.startColor)) && (gi.endColor.equals(this.endColor)) && (gi.isVertical == this.isVertical);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/* 131 */     if (!(o instanceof GradientInfo)) return false;
/* 132 */     return isEquivalent((GradientInfo)o);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 137 */     return "Direction:" + (this.isVertical ? "ver" : "hor") + ", Length: " + Integer.toString(this.length) + ", Color1: " + Integer.toString(this.startColor.getRGB(), 16) + ", Color2: " + Integer.toString(this.endColor.getRGB(), 16);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.GradientInfo
 * JD-Core Version:    0.6.0
 */
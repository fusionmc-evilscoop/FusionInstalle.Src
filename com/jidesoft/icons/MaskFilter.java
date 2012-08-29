/*    */ package com.jidesoft.icons;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Image;
/*    */ import java.awt.Toolkit;
/*    */ import java.awt.image.FilteredImageSource;
/*    */ import java.awt.image.ImageProducer;
/*    */ import java.awt.image.RGBImageFilter;
/*    */ 
/*    */ public class MaskFilter extends RGBImageFilter
/*    */ {
/*    */   private Color _newColor;
/*    */   private Color _oldColor;
/* 20 */   private static MaskFilter _maskFilter = null;
/*    */ 
/*    */   public static MaskFilter getInstance(Color oldColor, Color newColor) {
/* 23 */     if (_maskFilter == null) {
/* 24 */       _maskFilter = new MaskFilter(oldColor, newColor);
/*    */     }
/*    */     else {
/* 27 */       _maskFilter.setOldColor(oldColor);
/* 28 */       _maskFilter.setNewColor(newColor);
/*    */     }
/* 30 */     return _maskFilter;
/*    */   }
/*    */ 
/*    */   private void setNewColor(Color newColor) {
/* 34 */     this._newColor = newColor;
/*    */   }
/*    */ 
/*    */   private void setOldColor(Color oldColor) {
/* 38 */     this._oldColor = oldColor;
/*    */   }
/*    */ 
/*    */   public static Image createImage(Image i, Color oldColor, Color newColor)
/*    */   {
/* 45 */     MaskFilter filter = getInstance(oldColor, newColor);
/* 46 */     ImageProducer prod = new FilteredImageSource(i.getSource(), filter);
/* 47 */     Image image = Toolkit.getDefaultToolkit().createImage(prod);
/* 48 */     return image;
/*    */   }
/*    */ 
/*    */   public static Image createNegativeImage(Image i)
/*    */   {
/* 56 */     return createImage(i, Color.black, Color.white);
/*    */   }
/*    */ 
/*    */   public MaskFilter(Color oldColor, Color newColor)
/*    */   {
/* 68 */     this._newColor = newColor;
/* 69 */     this._oldColor = oldColor;
/* 70 */     this.canFilterIndexColorModel = true;
/*    */   }
/*    */ 
/*    */   public int filterRGB(int x, int y, int rgb)
/*    */   {
/* 78 */     if ((this._newColor != null) && (this._oldColor != null) && 
/* 79 */       (rgb == this._oldColor.getRGB())) {
/* 80 */       return this._newColor.getRGB();
/*    */     }
/*    */ 
/* 83 */     return rgb;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.icons.MaskFilter
 * JD-Core Version:    0.6.0
 */
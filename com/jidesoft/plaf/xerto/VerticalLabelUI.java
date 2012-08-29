/*    */ package com.jidesoft.plaf.xerto;
/*    */ 
/*    */ import java.awt.Dimension;
/*    */ import java.awt.FontMetrics;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.Insets;
/*    */ import java.awt.Rectangle;
/*    */ import java.awt.geom.AffineTransform;
/*    */ import javax.swing.Icon;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.plaf.basic.BasicLabelUI;
/*    */ 
/*    */ public class VerticalLabelUI extends BasicLabelUI
/*    */ {
/*    */   protected boolean clockwise;
/*    */   private static Rectangle s_oPaintIconRectangle;
/*    */   private static Rectangle s_oPaintTextRectangle;
/*    */   private static Rectangle s_oPaintViewRectangle;
/*    */   private static Insets s_oPaintViewInsets;
/*    */ 
/*    */   public VerticalLabelUI(boolean clockwise)
/*    */   {
/* 23 */     this.clockwise = clockwise;
/*    */   }
/*    */ 
/*    */   public Dimension getPreferredSize(JComponent c)
/*    */   {
/* 29 */     Dimension dim = super.getPreferredSize(c);
/* 30 */     return new Dimension(dim.height, dim.width);
/*    */   }
/*    */ 
/*    */   public void paint(Graphics i_oGraphics, JComponent i_oComponent)
/*    */   {
/* 40 */     JLabel oLabel = (JLabel)i_oComponent;
/* 41 */     String oText = oLabel.getText();
/* 42 */     Icon oIcon = oLabel.isEnabled() ? oLabel.getIcon() : oLabel.getDisabledIcon();
/*    */ 
/* 44 */     if ((oIcon == null) && (oText == null)) {
/* 45 */       return;
/*    */     }
/*    */ 
/* 48 */     FontMetrics oFontMetrics = i_oGraphics.getFontMetrics();
/* 49 */     s_oPaintViewInsets = i_oComponent.getInsets(s_oPaintViewInsets);
/*    */ 
/* 51 */     s_oPaintViewRectangle.x = s_oPaintViewInsets.left;
/* 52 */     s_oPaintViewRectangle.y = s_oPaintViewInsets.top;
/*    */ 
/* 55 */     s_oPaintViewRectangle.height = (i_oComponent.getWidth() - (s_oPaintViewInsets.left + s_oPaintViewInsets.right));
/* 56 */     s_oPaintViewRectangle.width = (i_oComponent.getHeight() - (s_oPaintViewInsets.top + s_oPaintViewInsets.bottom));
/*    */ 
/* 58 */     s_oPaintIconRectangle.x = (s_oPaintIconRectangle.y = s_oPaintIconRectangle.width = s_oPaintIconRectangle.height = 0);
/* 59 */     s_oPaintTextRectangle.x = (s_oPaintTextRectangle.y = s_oPaintTextRectangle.width = s_oPaintTextRectangle.height = 0);
/*    */ 
/* 61 */     String sClippedText = layoutCL(oLabel, oFontMetrics, oText, oIcon, s_oPaintViewRectangle, s_oPaintIconRectangle, s_oPaintTextRectangle);
/*    */ 
/* 64 */     Graphics2D g2 = (Graphics2D)i_oGraphics;
/* 65 */     AffineTransform oTransform = g2.getTransform();
/* 66 */     if (this.clockwise) {
/* 67 */       g2.rotate(1.570796326794897D);
/* 68 */       g2.translate(0, -i_oComponent.getWidth());
/*    */     }
/*    */     else {
/* 71 */       g2.rotate(-1.570796326794897D);
/* 72 */       g2.translate(-i_oComponent.getHeight(), 0);
/*    */     }
/*    */ 
/* 75 */     if (oIcon != null) {
/* 76 */       oIcon.paintIcon(i_oComponent, i_oGraphics, s_oPaintIconRectangle.x, s_oPaintIconRectangle.y);
/*    */     }
/*    */ 
/* 79 */     if (oText != null) {
/* 80 */       int iTextX = s_oPaintTextRectangle.x;
/* 81 */       int iTextY = s_oPaintTextRectangle.y + oFontMetrics.getAscent();
/*    */ 
/* 83 */       if (oLabel.isEnabled()) {
/* 84 */         paintEnabledText(oLabel, i_oGraphics, sClippedText, iTextX, iTextY);
/*    */       }
/*    */       else {
/* 87 */         paintDisabledText(oLabel, i_oGraphics, sClippedText, iTextX, iTextY);
/*    */       }
/*    */     }
/*    */ 
/* 91 */     g2.setTransform(oTransform);
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 16 */     labelUI = new VerticalLabelUI(false);
/*    */ 
/* 33 */     s_oPaintIconRectangle = new Rectangle();
/* 34 */     s_oPaintTextRectangle = new Rectangle();
/* 35 */     s_oPaintViewRectangle = new Rectangle();
/* 36 */     s_oPaintViewInsets = new Insets(0, 0, 0, 0);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.xerto.VerticalLabelUI
 * JD-Core Version:    0.6.0
 */
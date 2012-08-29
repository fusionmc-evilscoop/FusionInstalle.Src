/*     */ package com.jidesoft.plaf.basic;
/*     */ 
/*     */ import com.jidesoft.swing.JideLabel;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.LabelUI;
/*     */ import javax.swing.plaf.basic.BasicLabelUI;
/*     */ 
/*     */ public class BasicJideLabelUI extends BasicLabelUI
/*     */ {
/*  15 */   private static final LabelUI INSTANCE = new BasicJideLabelUI();
/*     */ 
/*  63 */   private static Rectangle paintIconR = new Rectangle();
/*  64 */   private static Rectangle paintTextR = new Rectangle();
/*  65 */   private static Rectangle paintViewR = new Rectangle();
/*  66 */   private static Insets paintViewInsets = new Insets(0, 0, 0, 0);
/*     */ 
/*     */   public static ComponentUI createUI(JComponent c)
/*     */   {
/*  18 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   protected void installDefaults(JLabel c)
/*     */   {
/*  23 */     super.installDefaults(c);
/*  24 */     LookAndFeel.installColorsAndFont(c, "JideLabel.background", "JideLabel.foreground", "JideLabel.font");
/*     */   }
/*     */ 
/*     */   public Dimension getMinimumSize(JComponent c)
/*     */   {
/*  29 */     Dimension d = super.getMinimumSize(c);
/*  30 */     if (JideSwingUtilities.getOrientationOf(c) == 0) {
/*  31 */       return d;
/*     */     }
/*     */ 
/*  35 */     return new Dimension(d.height, d.width);
/*     */   }
/*     */ 
/*     */   public Dimension getMaximumSize(JComponent c)
/*     */   {
/*  41 */     Dimension d = super.getMaximumSize(c);
/*  42 */     if (JideSwingUtilities.getOrientationOf(c) == 0) {
/*  43 */       return d;
/*     */     }
/*     */ 
/*  47 */     return new Dimension(d.height, d.width);
/*     */   }
/*     */ 
/*     */   public Dimension getPreferredSize(JComponent c)
/*     */   {
/*  53 */     Dimension d = super.getPreferredSize(c);
/*  54 */     if (JideSwingUtilities.getOrientationOf(c) == 0) {
/*  55 */       return d;
/*     */     }
/*     */ 
/*  59 */     return new Dimension(d.height, d.width);
/*     */   }
/*     */ 
/*     */   public void paint(Graphics g, JComponent c)
/*     */   {
/*  70 */     if (JideSwingUtilities.getOrientationOf(c) == 1) {
/*  71 */       boolean clockwise = true;
/*  72 */       if ((c instanceof JideLabel)) {
/*  73 */         clockwise = ((JideLabel)c).isClockwise();
/*     */       }
/*  75 */       paintVertically(g, c, clockwise);
/*     */     }
/*     */     else {
/*  78 */       super.paint(g, c);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintVertically(Graphics g, JComponent c, boolean clockwise)
/*     */   {
/*  84 */     JLabel label = (JLabel)c;
/*  85 */     String text = label.getText();
/*  86 */     Icon icon = label.isEnabled() ? label.getIcon() : label.getDisabledIcon();
/*     */ 
/*  88 */     if ((icon == null) && (text == null)) {
/*  89 */       return;
/*     */     }
/*     */ 
/*  92 */     FontMetrics fm = g.getFontMetrics();
/*  93 */     paintViewInsets = c.getInsets(paintViewInsets);
/*     */ 
/*  95 */     paintViewR.x = paintViewInsets.left;
/*  96 */     paintViewR.y = paintViewInsets.top;
/*     */ 
/*  99 */     paintViewR.height = (c.getWidth() - (paintViewInsets.left + paintViewInsets.right));
/* 100 */     paintViewR.width = (c.getHeight() - (paintViewInsets.top + paintViewInsets.bottom));
/*     */ 
/* 102 */     paintIconR.x = (paintIconR.y = paintIconR.width = paintIconR.height = 0);
/* 103 */     paintTextR.x = (paintTextR.y = paintTextR.width = paintTextR.height = 0);
/*     */ 
/* 105 */     String clippedText = layoutCL(label, fm, text, icon, paintViewR, paintIconR, paintTextR);
/*     */ 
/* 107 */     Graphics2D g2 = (Graphics2D)g;
/* 108 */     AffineTransform tr = g2.getTransform();
/* 109 */     if (clockwise) {
/* 110 */       g2.rotate(1.570796326794897D);
/* 111 */       g2.translate(0, -c.getWidth());
/*     */     }
/*     */     else {
/* 114 */       g2.rotate(-1.570796326794897D);
/* 115 */       g2.translate(-c.getHeight(), 0);
/*     */     }
/*     */ 
/* 118 */     if (icon != null) {
/* 119 */       icon.paintIcon(c, g, paintIconR.x, paintIconR.y);
/*     */     }
/*     */ 
/* 122 */     if (text != null) {
/* 123 */       int textX = paintTextR.x;
/* 124 */       int textY = paintTextR.y + fm.getAscent();
/*     */ 
/* 126 */       if (label.isEnabled()) {
/* 127 */         paintEnabledText(label, g, clippedText, textX, textY);
/*     */       }
/*     */       else {
/* 130 */         paintDisabledText(label, g, clippedText, textX, textY);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 135 */     g2.setTransform(tr);
/*     */   }
/*     */ 
/*     */   public void propertyChange(PropertyChangeEvent e) {
/* 139 */     super.propertyChange(e);
/* 140 */     if ("orientation" == e.getPropertyName()) {
/* 141 */       if ((e.getSource() instanceof JLabel)) {
/* 142 */         JLabel label = (JLabel)e.getSource();
/* 143 */         label.revalidate();
/*     */       }
/*     */     }
/* 146 */     else if (("clockwise".equals(e.getPropertyName())) && 
/* 147 */       ((e.getSource() instanceof JLabel))) {
/* 148 */       JLabel label = (JLabel)e.getSource();
/* 149 */       label.repaint();
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.BasicJideLabelUI
 * JD-Core Version:    0.6.0
 */
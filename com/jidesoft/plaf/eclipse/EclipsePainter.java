/*     */ package com.jidesoft.plaf.eclipse;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.plaf.basic.BasicPainter;
/*     */ import com.jidesoft.plaf.basic.ThemePainter;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.border.Border;
/*     */ 
/*     */ public class EclipsePainter extends BasicPainter
/*     */ {
/*     */   private static EclipsePainter _instance;
/*     */   protected Color _shadowColor;
/*     */   protected Color _darkShadowColor;
/*     */   protected Color _highlight;
/*     */   protected Color _lightHighlightColor;
/*     */ 
/*     */   public static ThemePainter getInstance()
/*     */   {
/*  26 */     if (_instance == null) {
/*  27 */       _instance = new EclipsePainter();
/*     */     }
/*  29 */     return _instance;
/*     */   }
/*     */ 
/*     */   protected EclipsePainter()
/*     */   {
/*  38 */     this._shadowColor = UIDefaultsLookup.getColor("controlShadow");
/*  39 */     this._darkShadowColor = UIDefaultsLookup.getColor("controlDkShadow");
/*  40 */     this._highlight = UIDefaultsLookup.getColor("controlHighlight");
/*  41 */     this._lightHighlightColor = UIDefaultsLookup.getColor("controlLtHighlight");
/*     */   }
/*     */ 
/*     */   public void paintButtonBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/*  46 */     Color oldColor = g.getColor();
/*  47 */     if (state != 0)
/*     */     {
/*  49 */       if (state == 2) {
/*  50 */         if (orientation == 0) {
/*  51 */           g.setColor(this._lightHighlightColor);
/*  52 */           g.drawLine(rect.x, rect.y, rect.x + rect.width - 1, rect.y);
/*  53 */           g.drawLine(rect.x, rect.y, rect.x, rect.y + rect.height - 1);
/*     */ 
/*  55 */           g.setColor(this._shadowColor);
/*  56 */           g.drawLine(rect.x, rect.y + rect.height - 1, rect.x + rect.width - 1, rect.y + rect.height - 1);
/*  57 */           g.drawLine(rect.x + rect.width - 1, rect.y, rect.x + rect.width - 1, rect.x + rect.height - 1);
/*     */         }
/*     */         else {
/*  60 */           g.setColor(this._lightHighlightColor);
/*  61 */           g.drawLine(rect.x, rect.y, rect.x, rect.y + rect.height - 1);
/*  62 */           g.drawLine(rect.x, rect.y + rect.height - 1, rect.x + rect.width - 1, rect.y + rect.height - 1);
/*     */ 
/*  64 */           g.setColor(this._shadowColor);
/*  65 */           g.drawLine(rect.x, rect.y, rect.x + rect.width - 1, rect.y);
/*  66 */           g.drawLine(rect.x + rect.width - 1, rect.y, rect.x + rect.width - 1, rect.x + rect.height - 1);
/*     */         }
/*     */       }
/*  69 */       else if (state == 3) {
/*  70 */         EclipseUtils.fillRectWithHatch((Graphics2D)g, new Rectangle(2, 2, rect.width - 4, rect.height - 4), UIDefaultsLookup.getColor("JideButton.background"));
/*     */ 
/*  73 */         if (orientation == 0) {
/*  74 */           g.setColor(this._shadowColor);
/*  75 */           g.drawLine(rect.x, rect.y, rect.x + rect.width - 1, rect.y);
/*  76 */           g.drawLine(rect.x, rect.y, rect.x, rect.y + rect.height - 1);
/*     */ 
/*  78 */           g.setColor(this._lightHighlightColor);
/*  79 */           g.drawLine(rect.x, rect.y + rect.height - 1, rect.x + rect.width - 1, rect.y + rect.height - 1);
/*  80 */           g.drawLine(rect.x + rect.width - 1, rect.y, rect.x + rect.width - 1, rect.y + rect.height - 1);
/*     */         }
/*     */         else {
/*  83 */           g.setColor(this._shadowColor);
/*  84 */           g.drawLine(rect.x, rect.y, rect.x, rect.y + rect.height - 1);
/*  85 */           g.drawLine(rect.x, rect.y + rect.height - 1, rect.x + rect.width - 1, rect.y + rect.height - 1);
/*     */ 
/*  87 */           g.setColor(this._lightHighlightColor);
/*  88 */           g.drawLine(rect.x, rect.y, rect.x + rect.width - 1, rect.y);
/*  89 */           g.drawLine(rect.x + rect.width - 1, rect.y, rect.x + rect.width - 1, rect.y + rect.height - 1);
/*     */         }
/*     */ 
/*     */       }
/*  93 */       else if (state == 1) {
/*  94 */         g.setColor(this._shadowColor);
/*  95 */         g.drawLine(rect.x, rect.y, rect.x + rect.width - 1, rect.y);
/*  96 */         g.drawLine(rect.x, rect.y, rect.x, rect.y + rect.height - 1);
/*     */ 
/*  98 */         g.setColor(this._lightHighlightColor);
/*  99 */         g.drawLine(rect.x, rect.y + rect.height - 1, rect.x + rect.width - 1, rect.y + rect.height - 1);
/* 100 */         g.drawLine(rect.x + rect.width - 1, rect.y, rect.x + rect.width - 1, rect.y + rect.height - 1);
/*     */       }
/*     */     }
/* 102 */     g.setColor(oldColor);
/*     */   }
/*     */ 
/*     */   public void paintSelectedMenu(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void paintGripper(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 111 */     int h = orientation == 0 ? rect.height : rect.width;
/* 112 */     h -= 4;
/*     */ 
/* 114 */     int y = rect.y + 2;
/* 115 */     int x = rect.x + 2;
/*     */ 
/* 117 */     Color oldColor = g.getColor();
/* 118 */     if (orientation == 0) {
/* 119 */       g.setColor(this._lightHighlightColor);
/* 120 */       g.drawLine(x, y, x, y + h);
/* 121 */       g.drawLine(x, y, x + 2, y);
/* 122 */       g.setColor(this._shadowColor);
/* 123 */       g.drawLine(x + 2, y, x + 2, y + h);
/* 124 */       g.drawLine(x, y + h, x + 2, y + h);
/* 125 */       g.setColor(UIDefaultsLookup.getColor("JideButton.background"));
/* 126 */       g.drawLine(x + 1, y + 1, x + 1, y + h - 1);
/*     */     }
/*     */     else {
/* 129 */       g.setColor(this._lightHighlightColor);
/* 130 */       g.drawLine(x, y, x + h, y);
/* 131 */       g.drawLine(x, y, x, y + 2);
/* 132 */       g.setColor(this._shadowColor);
/* 133 */       g.drawLine(x, y + 2, x + h, y + 2);
/* 134 */       g.drawLine(x + h, y, x + h, y + 2);
/* 135 */       g.setColor(UIDefaultsLookup.getColor("JideButton.background"));
/* 136 */       g.drawLine(x + 1, y + 1, x + h - 1, y + 1);
/*     */     }
/* 138 */     g.setColor(oldColor);
/*     */   }
/*     */ 
/*     */   public void paintDockableFrameTitlePane(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 143 */     int x = rect.x;
/* 144 */     int y = rect.y;
/* 145 */     int w = c.getWidth();
/* 146 */     int h = rect.height;
/* 147 */     if (c.getBorder() != null) {
/* 148 */       Insets insets = c.getBorder().getBorderInsets(c);
/* 149 */       x += insets.left;
/* 150 */       y += insets.top;
/* 151 */       w -= insets.right + insets.left;
/* 152 */       h -= insets.top + insets.bottom;
/*     */     }
/*     */ 
/* 155 */     g.setColor(Color.white);
/* 156 */     g.drawLine(x, y, x + w, y);
/* 157 */     g.drawLine(x, y, x, y + h - 1);
/*     */ 
/* 159 */     g.setColor(Color.gray);
/* 160 */     g.drawLine(x, y + h - 1, x + w, y + h - 1);
/*     */ 
/* 162 */     if (state == 3) {
/* 163 */       int width = rect.width;
/* 164 */       Graphics2D g2d = (Graphics2D)g;
/* 165 */       JideSwingUtilities.fillGradient(g2d, new Rectangle(x + 1, y + 1, width / 2, h - 2), UIDefaultsLookup.getColor("DockableFrame.activeTitleBackground"), UIDefaultsLookup.getColor("DockableFrame.activeTitleBackground2"), false);
/* 166 */       JideSwingUtilities.fillGradient(g2d, new Rectangle(x + 1 + width / 2, y + 1, width / 2, h - 2), UIDefaultsLookup.getColor("DockableFrame.activeTitleBackground2"), UIDefaultsLookup.getColor("DockableFrame.background"), false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintToolBarSeparator(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*     */   {
/* 177 */     int h = orientation == 0 ? c.getHeight() : c.getWidth();
/* 178 */     h -= 5;
/*     */ 
/* 182 */     if (JideSwingUtilities.getOrientationOf(c) == 0) {
/* 183 */       int y = rect.y + 3;
/* 184 */       int x = rect.x + 1;
/* 185 */       g.setColor(this._shadowColor);
/* 186 */       g.drawLine(x, y, x, y + h);
/* 187 */       g.setColor(this._lightHighlightColor);
/* 188 */       g.drawLine(x + 1, y + 1, x + 1, y + h + 1);
/*     */     }
/*     */     else {
/* 191 */       int y = rect.y + 1;
/* 192 */       int x = rect.x + 3;
/* 193 */       g.setColor(this._shadowColor);
/* 194 */       g.drawLine(x, y, x + h, y);
/* 195 */       g.setColor(this._lightHighlightColor);
/* 196 */       g.drawLine(x + 1, y + 1, x + 1 + h, y + 1);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.eclipse.EclipsePainter
 * JD-Core Version:    0.6.0
 */
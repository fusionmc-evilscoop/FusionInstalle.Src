/*     */ package com.jidesoft.plaf.aqua;
/*     */ 
/*     */ import com.jidesoft.plaf.basic.BasicJideTabbedPaneUI.TabCloseButton;
/*     */ import com.jidesoft.plaf.basic.BasicJideTabbedPaneUI.TabEditor;
/*     */ import com.jidesoft.plaf.vsnet.VsnetJideTabbedPaneUI;
/*     */ import com.jidesoft.swing.JideTabbedPane;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ 
/*     */ public class AquaJideTabbedPaneUI extends VsnetJideTabbedPaneUI
/*     */ {
/*  93 */   private static final Color COLOR1 = new Color(130, 130, 130);
/*  94 */   private static final Color COLOR2 = new Color(86, 86, 86);
/*  95 */   private static final Color COLOR3 = new Color(252, 252, 252);
/*     */ 
/*     */   public static ComponentUI createUI(JComponent c)
/*     */   {
/*  21 */     return new AquaJideTabbedPaneUI();
/*     */   }
/*     */ 
/*     */   protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*     */   {
/*  30 */     super.paintTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
/*     */ 
/*  32 */     if (this._tabPane.getTabColorProvider() != null) {
/*  33 */       return;
/*     */     }
/*     */ 
/*  40 */     if (!isSelected) {
/*  41 */       return;
/*     */     }
/*     */ 
/*  44 */     Color[] color = AquaJideUtils.isGraphite() ? AquaJideUtils.AQUA_GRAPHITE : AquaJideUtils.AQUA_BLUE;
/*     */ 
/*  46 */     if (this.tabRegion != null) {
/*  47 */       Graphics2D g2d = (Graphics2D)g;
/*  48 */       switch (tabPlacement) {
/*     */       case 2:
/*  50 */         AquaJideUtils.fillAquaGradientVertical(g2d, this.tabRegion, color);
/*  51 */         break;
/*     */       case 4:
/*  53 */         AquaJideUtils.fillAquaGradientVertical(g2d, this.tabRegion, color);
/*  54 */         break;
/*     */       case 3:
/*  56 */         AquaJideUtils.fillAquaGradientHorizontal(g2d, this.tabRegion, color);
/*  57 */         break;
/*     */       case 1:
/*     */       default:
/*  60 */         AquaJideUtils.fillAquaGradientHorizontal(g2d, this.tabRegion, color);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected boolean isRoundedCorner()
/*     */   {
/*  75 */     return true;
/*     */   }
/*     */ 
/*     */   protected boolean isShading() {
/*  79 */     return true;
/*     */   }
/*     */ 
/*     */   protected Color getBorderEdgeColor()
/*     */   {
/*  84 */     return this._shadow;
/*     */   }
/*     */ 
/*     */   protected BasicJideTabbedPaneUI.TabCloseButton createNoFocusButton(int type)
/*     */   {
/*  89 */     return new AquaTabCloseButton(type);
/*     */   }
/*     */ 
/*     */   protected void prepareEditor(BasicJideTabbedPaneUI.TabEditor e, int tabIndex)
/*     */   {
/* 286 */     e.setOpaque(true);
/* 287 */     super.prepareEditor(e, tabIndex);
/*     */   }
/*     */ 
/*     */   public class AquaTabCloseButton extends BasicJideTabbedPaneUI.TabCloseButton
/*     */   {
/*     */     public void updateUI()
/*     */     {
/* 105 */       super.updateUI();
/* 106 */       setMargin(new Insets(0, 0, 0, 0));
/* 107 */       setBorder(BorderFactory.createEmptyBorder());
/* 108 */       setFocusPainted(false);
/*     */     }
/*     */ 
/*     */     public AquaTabCloseButton() {
/* 112 */       this(0);
/*     */     }
/*     */     public AquaTabCloseButton(int type) {
/* 115 */       super();
/* 116 */       addMouseMotionListener(this);
/* 117 */       addMouseListener(this);
/* 118 */       setContentAreaFilled(false);
/* 119 */       setType(type);
/*     */     }
/*     */ 
/*     */     public Dimension getPreferredSize()
/*     */     {
/* 124 */       return new Dimension(16, 16);
/*     */     }
/*     */ 
/*     */     public Dimension getMinimumSize()
/*     */     {
/* 129 */       return new Dimension(5, 5);
/*     */     }
/*     */ 
/*     */     public Dimension getMaximumSize()
/*     */     {
/* 134 */       return new Dimension(2147483647, 2147483647);
/*     */     }
/*     */ 
/*     */     protected void paintComponent(Graphics g)
/*     */     {
/* 139 */       if (!isEnabled()) {
/* 140 */         setMouseOver(false);
/* 141 */         setMousePressed(false);
/*     */       }
/*     */ 
/* 145 */       AquaJideUtils.antialiasShape(g, true);
/*     */ 
/* 147 */       Color color = g.getColor();
/*     */ 
/* 149 */       if ((isMouseOver()) && (isMousePressed())) {
/* 150 */         g.setColor(AquaJideTabbedPaneUI.COLOR1);
/* 151 */         g.fillOval(2, 2, getWidth() - 4, getHeight() - 4);
/* 152 */         g.setColor(AquaJideTabbedPaneUI.COLOR2);
/*     */       }
/* 154 */       else if (isMouseOver()) {
/* 155 */         g.setColor(AquaJideTabbedPaneUI.COLOR1);
/* 156 */         g.fillOval(2, 2, getWidth() - 4, getHeight() - 4);
/* 157 */         g.setColor(AquaJideTabbedPaneUI.COLOR3);
/*     */       }
/*     */       else {
/* 160 */         g.setColor(AquaJideTabbedPaneUI.COLOR1);
/*     */       }
/* 162 */       int centerX = getWidth() >> 1;
/* 163 */       int centerY = getHeight() >> 1;
/* 164 */       switch (getType()) {
/*     */       case 0:
/* 166 */         if (isEnabled()) {
/* 167 */           g.drawLine(centerX - 2, centerY - 2, centerX + 2, centerY + 2);
/* 168 */           g.drawLine(centerX - 3, centerY - 2, centerX + 1, centerY + 2);
/* 169 */           g.drawLine(centerX + 2, centerY - 2, centerX - 2, centerY + 2);
/* 170 */           g.drawLine(centerX + 1, centerY - 2, centerX - 3, centerY + 2);
/*     */         }
/*     */         else {
/* 173 */           g.drawLine(centerX - 3, centerY - 3, centerX + 3, centerY + 3);
/* 174 */           g.drawLine(centerX + 3, centerY - 3, centerX - 3, centerY + 3);
/*     */         }
/* 176 */         break;
/*     */       case 1:
/* 189 */         if ((AquaJideTabbedPaneUI.this._tabPane.getTabPlacement() == 1) || (AquaJideTabbedPaneUI.this._tabPane.getTabPlacement() == 3)) {
/* 190 */           int x = centerX + 2; int y = centerY;
/* 191 */           if (isEnabled()) {
/* 192 */             g.drawLine(x - 3, y - 3, x - 3, y + 3);
/* 193 */             g.drawLine(x - 2, y - 2, x - 2, y + 2);
/* 194 */             g.drawLine(x - 1, y - 1, x - 1, y + 1);
/* 195 */             g.drawLine(x, y, x, y);
/*     */           }
/*     */           else {
/* 198 */             g.drawLine(x - 3, y - 3, x, y);
/* 199 */             g.drawLine(x - 3, y - 3, x - 3, y + 3);
/* 200 */             g.drawLine(x - 3, y + 3, x, y);
/*     */           }
/*     */         }
/*     */         else {
/* 204 */           int x = centerX; int y = centerY + 2;
/* 205 */           if (isEnabled()) {
/* 206 */             g.drawLine(x - 3, y - 3, x + 3, y - 3);
/* 207 */             g.drawLine(x - 2, y - 2, x + 2, y - 2);
/* 208 */             g.drawLine(x - 1, y - 1, x + 1, y - 1);
/* 209 */             g.drawLine(x, y, x, y);
/*     */           }
/*     */           else {
/* 212 */             g.drawLine(x - 3, y - 3, x, y);
/* 213 */             g.drawLine(x - 3, y - 3, x + 3, y - 3);
/* 214 */             g.drawLine(x + 3, y - 3, x, y);
/*     */           }
/*     */         }
/*     */ 
/* 218 */         break;
/*     */       case 2:
/* 232 */         if ((AquaJideTabbedPaneUI.this._tabPane.getTabPlacement() == 1) || (AquaJideTabbedPaneUI.this._tabPane.getTabPlacement() == 3)) {
/* 233 */           int x = centerX - 3; int y = centerY;
/* 234 */           if (isEnabled()) {
/* 235 */             g.drawLine(x, y, x, y);
/* 236 */             g.drawLine(x + 1, y - 1, x + 1, y + 1);
/* 237 */             g.drawLine(x + 2, y - 2, x + 2, y + 2);
/* 238 */             g.drawLine(x + 3, y - 3, x + 3, y + 3);
/*     */           }
/*     */           else {
/* 241 */             g.drawLine(x, y, x + 3, y - 3);
/* 242 */             g.drawLine(x, y, x + 3, y + 3);
/* 243 */             g.drawLine(x + 3, y - 3, x + 3, y + 3);
/*     */           }
/*     */         }
/*     */         else {
/* 247 */           int x = centerX; int y = centerY - 2;
/* 248 */           if (isEnabled()) {
/* 249 */             g.drawLine(x, y, x, y);
/* 250 */             g.drawLine(x - 1, y + 1, x + 1, y + 1);
/* 251 */             g.drawLine(x - 2, y + 2, x + 2, y + 2);
/* 252 */             g.drawLine(x - 3, y + 3, x + 3, y + 3);
/*     */           }
/*     */           else {
/* 255 */             g.drawLine(x, y, x - 3, y + 3);
/* 256 */             g.drawLine(x, y, x + 3, y + 3);
/* 257 */             g.drawLine(x - 3, y + 3, x + 3, y + 3);
/*     */           }
/*     */         }
/*     */ 
/* 261 */         break;
/*     */       case 5:
/* 264 */         int x = centerX; int y = centerY + 2;
/* 265 */         g.drawLine(x - 3, y - 3, x + 3, y - 3);
/* 266 */         g.drawLine(x - 2, y - 2, x + 2, y - 2);
/* 267 */         g.drawLine(x - 1, y - 1, x + 1, y - 1);
/* 268 */         g.drawLine(x, y, x, y);
/* 269 */         break;
/*     */       case 3:
/*     */       case 4:
/*     */       }
/* 273 */       g.setColor(color);
/*     */ 
/* 275 */       AquaJideUtils.antialiasShape(g, false);
/*     */     }
/*     */ 
/*     */     public boolean isOpaque()
/*     */     {
/* 280 */       return false;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.aqua.AquaJideTabbedPaneUI
 * JD-Core Version:    0.6.0
 */
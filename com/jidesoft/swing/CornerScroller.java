/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.plaf.UIResource;
/*     */ 
/*     */ public class CornerScroller extends JideButton
/*     */ {
/*     */   protected ScrollPaneOverview _scrollPaneBidule;
/*     */ 
/*     */   public CornerScroller(JScrollPane scrollPane)
/*     */   {
/*  50 */     this._scrollPaneBidule = createScrollPaneOverview(scrollPane);
/*  51 */     setFocusPainted(false);
/*  52 */     addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  54 */         CornerScroller.this._scrollPaneBidule.display();
/*     */       }
/*     */     });
/*  57 */     setBorderPainted(false);
/*     */ 
/*  59 */     setIcon(new ScrollerIcon(null));
/*  60 */     setPreferredSize(new Dimension(16, 16));
/*     */   }
/*     */ 
/*     */   protected ScrollPaneOverview createScrollPaneOverview(JScrollPane scrollPane)
/*     */   {
/*  70 */     return new ScrollPaneOverview(scrollPane, this);
/*     */   }
/*     */ 
/*     */   public void setSelectionBorderColor(Color selectionBorder) {
/*  74 */     if (this._scrollPaneBidule != null)
/*  75 */       this._scrollPaneBidule.setSelectionBorderColor(selectionBorder);
/*     */   }
/*     */ 
/*     */   private static class ScrollerIcon implements Icon, UIResource, Serializable
/*     */   {
/*     */     public int getIconHeight() {
/*  81 */       return 16;
/*     */     }
/*     */ 
/*     */     public int getIconWidth() {
/*  85 */       return 16;
/*     */     }
/*     */ 
/*     */     public void paintIcon(Component c, Graphics g, int x, int y) {
/*  89 */       Graphics2D g2d = (Graphics2D)g;
/*     */ 
/*  91 */       Object oldrenderinghint = g2d.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
/*  92 */       g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*  93 */       GeneralPath path = new GeneralPath();
/*     */ 
/*  95 */       int mx = x + getIconWidth() / 2;
/*  96 */       int my = y + getIconHeight() / 2;
/*     */ 
/*  98 */       int gap = 4;
/*     */ 
/* 100 */       path.moveTo(mx - gap, my - 2);
/* 101 */       path.lineTo(mx - gap - 2, my);
/* 102 */       path.lineTo(mx - gap, my + 2);
/*     */ 
/* 104 */       path.moveTo(mx - 2, my - gap);
/* 105 */       path.lineTo(mx, my - gap - 2);
/* 106 */       path.lineTo(mx + 2, my - gap);
/*     */ 
/* 108 */       path.moveTo(mx + gap, my - 2);
/* 109 */       path.lineTo(mx + gap + 2, my);
/* 110 */       path.lineTo(mx + gap, my + 2);
/*     */ 
/* 112 */       path.moveTo(mx - 2, my + gap);
/* 113 */       path.lineTo(mx, my + gap + 2);
/* 114 */       path.lineTo(mx + 2, my + gap);
/*     */ 
/* 116 */       g.setColor(Color.GRAY);
/* 117 */       g2d.draw(path);
/*     */ 
/* 119 */       g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldrenderinghint);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.CornerScroller
 * JD-Core Version:    0.6.0
 */
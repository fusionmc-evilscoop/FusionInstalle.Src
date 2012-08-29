/*     */ package com.jidesoft.plaf.basic;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import com.jidesoft.swing.MeterProgressBar;
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JProgressBar;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicProgressBarUI;
/*     */ 
/*     */ public class MeterProgressBarUI extends BasicProgressBarUI
/*     */ {
/*     */   protected Color _cellBackground;
/*     */   protected Color _cellForeground;
/*     */   protected int _cellLength;
/*     */   protected int _cellSpacing;
/*     */   private PropertyChangeListener _propertyChangeListener;
/*     */ 
/*     */   public static ComponentUI createUI(JComponent c)
/*     */   {
/*  32 */     return new MeterProgressBarUI();
/*     */   }
/*     */ 
/*     */   protected void installDefaults()
/*     */   {
/*  37 */     super.installDefaults();
/*  38 */     LookAndFeel.installBorder(this.progressBar, "MeterProgressBar.border");
/*  39 */     LookAndFeel.installColors(this.progressBar, "MeterProgressBar.background", "MeterProgressBar.foreground");
/*  40 */     this._cellForeground = UIDefaultsLookup.getColor("MeterProgressBar.cellForeground");
/*  41 */     this._cellBackground = UIDefaultsLookup.getColor("MeterProgressBar.cellBackground");
/*  42 */     this._cellLength = UIDefaultsLookup.getInt("MeterProgressBar.cellLength");
/*  43 */     this._cellSpacing = UIDefaultsLookup.getInt("MeterProgressBar.cellSpacing");
/*     */   }
/*     */ 
/*     */   protected void uninstallDefaults()
/*     */   {
/*  49 */     super.uninstallDefaults();
/*  50 */     this._cellBackground = null;
/*  51 */     this._cellForeground = null;
/*     */   }
/*     */ 
/*     */   protected void installListeners()
/*     */   {
/*  56 */     super.installListeners();
/*  57 */     this.progressBar.addPropertyChangeListener(this._propertyChangeListener = new PropertyChangeHandler(null));
/*     */   }
/*     */ 
/*     */   protected void uninstallListeners()
/*     */   {
/*  63 */     this.progressBar.removePropertyChangeListener(this._propertyChangeListener);
/*  64 */     super.uninstallListeners();
/*     */   }
/*     */ 
/*     */   public void paint(Graphics g, JComponent c)
/*     */   {
/*  69 */     Insets b = this.progressBar.getInsets();
/*  70 */     int barRectWidth = this.progressBar.getWidth() - (b.right + b.left);
/*  71 */     int barRectHeight = this.progressBar.getHeight() - (b.top + b.bottom);
/*     */ 
/*  74 */     int amountFull = getAmountFull(b, barRectWidth, barRectHeight);
/*  75 */     int orientation = this.progressBar.getOrientation();
/*  76 */     float width = barRectWidth;
/*     */ 
/*  78 */     Graphics2D g2 = (Graphics2D)g.create();
/*     */ 
/*  81 */     g2.setColor(this._cellBackground);
/*  82 */     g2.setStroke(new BasicStroke(width, 0, 2));
/*  83 */     if (orientation == 0) {
/*  84 */       g2.drawLine(b.left, barRectHeight / 2 + b.top, b.left + barRectWidth, barRectHeight / 2 + b.top);
/*     */     }
/*     */     else
/*     */     {
/*  88 */       g2.drawLine(barRectWidth / 2 + b.left, b.top + barRectHeight, barRectWidth / 2 + b.left, b.top);
/*     */     }
/*     */ 
/*  93 */     if (((MeterProgressBar)c).getStyle() == 0) {
/*  94 */       g2.setColor(this._cellForeground);
/*  95 */       if (orientation == 0) {
/*  96 */         if (c.getComponentOrientation().isLeftToRight()) {
/*  97 */           g2.drawLine(b.left, barRectHeight / 2 + b.top, amountFull + b.left, barRectHeight / 2 + b.top);
/*     */         }
/*     */         else
/*     */         {
/* 101 */           g2.drawLine(barRectWidth + b.left, barRectHeight / 2 + b.top, barRectWidth + b.left - amountFull, barRectHeight / 2 + b.top);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 106 */         g2.drawLine(barRectWidth / 2 + b.left, b.top + barRectHeight, barRectWidth / 2 + b.left, b.top + barRectHeight - amountFull);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 111 */       g2.setStroke(new BasicStroke(1.0F, 0, 2));
/* 112 */       if (orientation == 0) {
/* 113 */         Rectangle rect = new Rectangle(b.left, b.top, amountFull, barRectHeight / 2);
/* 114 */         if (!c.getComponentOrientation().isLeftToRight()) {
/* 115 */           rect.x += barRectWidth - amountFull;
/*     */         }
/* 117 */         JideSwingUtilities.fillGradient(g2, rect, this._cellForeground, this._cellBackground, true);
/* 118 */         rect.y += barRectHeight / 2;
/* 119 */         JideSwingUtilities.fillGradient(g2, rect, this._cellBackground, this._cellForeground, true);
/*     */       }
/*     */       else {
/* 122 */         Rectangle rect = new Rectangle(b.left, b.top + barRectHeight - amountFull, barRectWidth / 2, amountFull);
/*     */ 
/* 124 */         JideSwingUtilities.fillGradient(g2, rect, this._cellForeground, this._cellBackground, false);
/* 125 */         rect.x += barRectWidth / 2;
/* 126 */         JideSwingUtilities.fillGradient(g2, rect, this._cellBackground, this._cellForeground, false);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 131 */     g2.setColor(this.progressBar.getBackground());
/* 132 */     g2.setStroke(new BasicStroke(width, 0, 2, 0.0F, new float[] { this._cellLength, this._cellSpacing }, 0.0F));
/*     */ 
/* 135 */     if (orientation == 0) {
/* 136 */       g2.drawLine(b.left - this._cellSpacing, barRectHeight / 2 + b.top, b.left + barRectWidth, barRectHeight / 2 + b.top);
/*     */     }
/*     */     else
/*     */     {
/* 140 */       g2.drawLine(barRectWidth / 2 + b.left, b.top + barRectHeight + this._cellSpacing, barRectWidth / 2 + b.left, b.top);
/*     */     }
/*     */ 
/* 144 */     g2.dispose();
/*     */   }
/*     */   private class PropertyChangeHandler implements PropertyChangeListener {
/*     */     private PropertyChangeHandler() {
/*     */     }
/*     */     public void propertyChange(PropertyChangeEvent evt) {
/* 150 */       if ("style".equals(evt.getPropertyName()))
/* 151 */         MeterProgressBarUI.this.progressBar.repaint();
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.MeterProgressBarUI
 * JD-Core Version:    0.6.0
 */
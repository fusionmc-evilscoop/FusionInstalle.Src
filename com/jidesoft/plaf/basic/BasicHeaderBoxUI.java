/*     */ package com.jidesoft.plaf.basic;
/*     */ 
/*     */ import com.jidesoft.plaf.HeaderBoxUI;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.swing.HeaderBox;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.event.MouseInputAdapter;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ 
/*     */ public class BasicHeaderBoxUI extends HeaderBoxUI
/*     */ {
/*     */   private static HeaderBoxUI _headerBoxUI;
/*     */   protected ThemePainter _painter;
/*     */ 
/*     */   public static ComponentUI createUI(JComponent c)
/*     */   {
/*  28 */     if (_headerBoxUI == null) {
/*  29 */       _headerBoxUI = new BasicHeaderBoxUI();
/*     */     }
/*  31 */     return _headerBoxUI;
/*     */   }
/*     */ 
/*     */   public void installUI(JComponent c)
/*     */   {
/*  36 */     HeaderBox p = (HeaderBox)c;
/*  37 */     super.installUI(p);
/*  38 */     installDefaults(p);
/*  39 */     installListeners(p);
/*     */   }
/*     */ 
/*     */   public void uninstallUI(JComponent c)
/*     */   {
/*  44 */     HeaderBox p = (HeaderBox)c;
/*  45 */     uninstallDefaults(p);
/*  46 */     uninstallListeners(p);
/*  47 */     super.uninstallUI(c);
/*     */   }
/*     */ 
/*     */   protected void installListeners(JComponent c)
/*     */   {
/* 123 */     MouseInputAdapter l = createHeaderBoxMouseListener();
/* 124 */     c.addMouseListener(l);
/*     */   }
/*     */ 
/*     */   private RolloverMouseInputAdapter getMouseListener(HeaderBox b)
/*     */   {
/* 131 */     MouseMotionListener[] listeners = b.getMouseMotionListeners();
/*     */ 
/* 133 */     if (listeners != null) {
/* 134 */       for (MouseMotionListener listener : listeners) {
/* 135 */         if ((listener instanceof RolloverMouseInputAdapter)) {
/* 136 */           return (RolloverMouseInputAdapter)listener;
/*     */         }
/*     */       }
/*     */     }
/* 140 */     return null;
/*     */   }
/*     */ 
/*     */   private RolloverMouseInputAdapter createHeaderBoxMouseListener() {
/* 144 */     return new RolloverMouseInputAdapter();
/*     */   }
/*     */ 
/*     */   protected void uninstallListeners(JComponent c) {
/* 148 */     HeaderBox b = (HeaderBox)c;
/* 149 */     RolloverMouseInputAdapter listener = getMouseListener(b);
/* 150 */     if (listener != null)
/* 151 */       b.removeMouseListener(listener);
/*     */   }
/*     */ 
/*     */   protected void installDefaults(HeaderBox p)
/*     */   {
/* 156 */     this._painter = ((ThemePainter)UIDefaultsLookup.get("Theme.painter"));
/* 157 */     LookAndFeel.installColorsAndFont(p, "Panel.background", "Panel.foreground", "Panel.font");
/*     */ 
/* 161 */     LookAndFeel.installBorder(p, "Panel.border");
/*     */   }
/*     */ 
/*     */   public void paint(Graphics g, JComponent c)
/*     */   {
/* 166 */     super.paint(g, c);
/* 167 */     paintBackground(g, c);
/* 168 */     paintBorder(g, c);
/*     */   }
/*     */ 
/*     */   protected void paintBorder(Graphics g, JComponent c) {
/*     */   }
/*     */ 
/*     */   protected void paintBackground(Graphics g, JComponent c) {
/* 175 */     HeaderBox headerBox = (HeaderBox)c;
/*     */ 
/* 177 */     Rectangle rect = new Rectangle(0, 0, c.getWidth(), c.getHeight());
/* 178 */     if (headerBox.getModel().isPressed()) {
/* 179 */       this._painter.paintHeaderBoxBackground(c, g, rect, 0, 1);
/*     */     }
/* 181 */     else if (headerBox.getModel().isSelected()) {
/* 182 */       this._painter.paintHeaderBoxBackground(c, g, rect, 0, 3);
/*     */     }
/* 184 */     else if (headerBox.getModel().isRollover()) {
/* 185 */       this._painter.paintHeaderBoxBackground(c, g, rect, 0, 2);
/*     */     }
/*     */     else
/* 188 */       this._painter.paintHeaderBoxBackground(c, g, rect, 0, 0);
/*     */   }
/*     */ 
/*     */   protected void uninstallDefaults(HeaderBox p)
/*     */   {
/* 193 */     LookAndFeel.uninstallBorder(p);
/* 194 */     this._painter = null;
/*     */   }
/*     */ 
/*     */   protected class RolloverMouseInputAdapter extends MouseInputAdapter
/*     */   {
/*  51 */     private long lastPressedTimestamp = -1L;
/*  52 */     private boolean shouldDiscardRelease = false;
/*     */ 
/*     */     protected RolloverMouseInputAdapter() {
/*     */     }
/*  56 */     public void mousePressed(MouseEvent e) { if (SwingUtilities.isLeftMouseButton(e)) {
/*  57 */         AbstractButton b = (AbstractButton)e.getSource();
/*     */ 
/*  59 */         if (b.contains(e.getX(), e.getY())) {
/*  60 */           long multiClickThreshold = b.getMultiClickThreshhold();
/*  61 */           long lastTime = this.lastPressedTimestamp;
/*  62 */           long currentTime = this.lastPressedTimestamp = e.getWhen();
/*  63 */           if ((lastTime != -1L) && (currentTime - lastTime < multiClickThreshold)) {
/*  64 */             this.shouldDiscardRelease = true;
/*  65 */             return;
/*     */           }
/*     */ 
/*  68 */           ButtonModel model = b.getModel();
/*  69 */           if (!model.isEnabled())
/*     */           {
/*  71 */             return;
/*     */           }
/*  73 */           if (!model.isArmed())
/*     */           {
/*  75 */             model.setArmed(true);
/*     */           }
/*  77 */           model.setPressed(true);
/*  78 */           if ((!b.hasFocus()) && (b.isRequestFocusEnabled()))
/*  79 */             b.requestFocus();
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     public void mouseReleased(MouseEvent e)
/*     */     {
/*  87 */       if (SwingUtilities.isLeftMouseButton(e))
/*     */       {
/*  89 */         if (this.shouldDiscardRelease) {
/*  90 */           this.shouldDiscardRelease = false;
/*  91 */           return;
/*     */         }
/*  93 */         AbstractButton b = (AbstractButton)e.getSource();
/*  94 */         ButtonModel model = b.getModel();
/*  95 */         model.setPressed(false);
/*  96 */         model.setArmed(false);
/*     */       }
/*     */     }
/*     */ 
/*     */     public void mouseEntered(MouseEvent e)
/*     */     {
/* 102 */       AbstractButton b = (AbstractButton)e.getSource();
/* 103 */       ButtonModel model = b.getModel();
/* 104 */       if ((b.isRolloverEnabled()) && (!SwingUtilities.isLeftMouseButton(e))) {
/* 105 */         model.setRollover(true);
/*     */       }
/* 107 */       if (model.isPressed())
/* 108 */         model.setArmed(true);
/*     */     }
/*     */ 
/*     */     public void mouseExited(MouseEvent e)
/*     */     {
/* 113 */       AbstractButton b = (AbstractButton)e.getSource();
/* 114 */       ButtonModel model = b.getModel();
/* 115 */       if (b.isRolloverEnabled()) {
/* 116 */         model.setRollover(false);
/*     */       }
/* 118 */       model.setArmed(false);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.BasicHeaderBoxUI
 * JD-Core Version:    0.6.0
 */
/*     */ package com.jidesoft.plaf.basic;
/*     */ 
/*     */ import com.jidesoft.plaf.GripperUI;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.swing.Gripper;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import com.jidesoft.utils.SecurityUtils;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ 
/*     */ public class BasicGripperUI extends GripperUI
/*     */ {
/*     */   private int _size;
/*     */   protected ThemePainter _painter;
/*     */   protected Painter _gripperPainter;
/*     */ 
/*     */   public static ComponentUI createUI(JComponent c)
/*     */   {
/*  32 */     return new BasicGripperUI();
/*     */   }
/*     */ 
/*     */   public void installUI(JComponent c)
/*     */   {
/*  37 */     this._painter = ((ThemePainter)UIDefaultsLookup.get("Theme.painter"));
/*  38 */     this._gripperPainter = ((Painter)UIDefaultsLookup.get("Gripper.painter"));
/*  39 */     installDefaults((Gripper)c);
/*  40 */     installListeners((Gripper)c);
/*     */   }
/*     */ 
/*     */   public void uninstallUI(JComponent c)
/*     */   {
/*  45 */     this._painter = null;
/*  46 */     this._gripperPainter = null;
/*  47 */     uninstallDefaults((Gripper)c);
/*  48 */     uninstallListeners((Gripper)c);
/*     */   }
/*     */ 
/*     */   protected void installDefaults(Gripper s) {
/*  52 */     this._size = UIDefaultsLookup.getInt("Gripper.size");
/*     */   }
/*     */ 
/*     */   protected void uninstallDefaults(Gripper s) {
/*     */   }
/*     */ 
/*     */   protected MouseListener createMouseListener() {
/*  59 */     return new GripperMouseListener();
/*     */   }
/*     */ 
/*     */   protected void installListeners(Gripper g) {
/*  63 */     MouseListener listener = createMouseListener();
/*  64 */     if (listener != null)
/*     */     {
/*  67 */       g.putClientProperty(this, listener);
/*  68 */       g.addMouseListener(listener);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void uninstallListeners(Gripper g) {
/*  73 */     MouseListener listener = (MouseListener)g.getClientProperty(this);
/*  74 */     g.putClientProperty(this, null);
/*  75 */     if (listener != null)
/*  76 */       g.removeMouseListener(listener);
/*     */   }
/*     */ 
/*     */   protected void paintBackground(Graphics g, Gripper b)
/*     */   {
/*  81 */     Rectangle rect = new Rectangle(0, 0, b.getWidth(), b.getHeight());
/*  82 */     if (b.isRollover()) {
/*  83 */       getPainter().paintButtonBackground(b, g, rect, 0, 2);
/*     */     }
/*  86 */     else if (b.isOpaque()) {
/*  87 */       getPainter().paintButtonBackground(b, g, rect, 0, b.isSelected() ? 3 : 0, false);
/*  88 */       if ("true".equals(SecurityUtils.getProperty("shadingtheme", "false")))
/*  89 */         JideSwingUtilities.fillGradient(g, rect, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paint(Graphics g, JComponent c)
/*     */   {
/*  97 */     Gripper gripper = (Gripper)c;
/*  98 */     paintBackground(g, gripper);
/*  99 */     int state = gripper.isSelected() ? 3 : 0;
/* 100 */     if (this._gripperPainter == null) {
/* 101 */       getPainter().paintGripper(c, g, new Rectangle(0, 0, c.getWidth(), c.getHeight()), gripper.getOrientation(), state);
/*     */     }
/*     */     else
/* 104 */       this._gripperPainter.paint(c, g, new Rectangle(0, 0, c.getWidth(), c.getHeight()), gripper.getOrientation(), state);
/*     */   }
/*     */ 
/*     */   public Dimension getPreferredSize(JComponent c)
/*     */   {
/* 110 */     return new Dimension(this._size, this._size);
/*     */   }
/*     */ 
/*     */   public Dimension getMinimumSize(JComponent c)
/*     */   {
/* 115 */     return getPreferredSize(c);
/*     */   }
/*     */ 
/*     */   public Dimension getMaximumSize(JComponent c)
/*     */   {
/* 120 */     Gripper gripper = (Gripper)c;
/* 121 */     if (gripper.getOrientation() == 0) {
/* 122 */       return new Dimension(this._size, c.getParent().getHeight());
/*     */     }
/* 124 */     return new Dimension(c.getParent().getWidth(), this._size);
/*     */   }
/*     */ 
/*     */   public ThemePainter getPainter() {
/* 128 */     return this._painter;
/*     */   }
/*     */   class GripperMouseListener extends MouseAdapter {
/*     */     GripperMouseListener() {
/*     */     }
/*     */     public void mouseEntered(MouseEvent e) {
/* 134 */       super.mouseEntered(e);
/* 135 */       if (((e.getSource() instanceof Gripper)) && 
/* 136 */         (((Gripper)e.getSource()).isRolloverEnabled()))
/* 137 */         ((Gripper)e.getSource()).setRollover(true);
/*     */     }
/*     */ 
/*     */     public void mouseExited(MouseEvent e)
/*     */     {
/* 144 */       super.mouseExited(e);
/* 145 */       if (((e.getSource() instanceof Gripper)) && 
/* 146 */         (((Gripper)e.getSource()).isRolloverEnabled()))
/* 147 */         ((Gripper)e.getSource()).setRollover(false);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.BasicGripperUI
 * JD-Core Version:    0.6.0
 */
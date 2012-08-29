/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Container.AccessibleAWTContainer;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.swing.JComponent;
/*     */ 
/*     */ @Deprecated
/*     */ public class JCellRendererPane extends JComponent
/*     */   implements Accessible
/*     */ {
/* 144 */   protected AccessibleContext accessibleContext = null;
/*     */ 
/*     */   public JCellRendererPane()
/*     */   {
/*  28 */     setLayout(null);
/*  29 */     setVisible(false);
/*     */   }
/*     */ 
/*     */   public void invalidate()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void paint(Graphics g)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void update(Graphics g)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void addImpl(Component x, Object constraints, int index)
/*     */   {
/*  61 */     if (x.getParent() == this) {
/*  62 */       return;
/*     */     }
/*     */ 
/*  65 */     super.addImpl(x, constraints, index);
/*     */   }
/*     */ 
/*     */   public void paintComponent(Graphics g, Component c, Container p, int x, int y, int w, int h, boolean shouldValidate)
/*     */   {
/*  77 */     if (c == null) {
/*  78 */       if (p != null) {
/*  79 */         Color oldColor = g.getColor();
/*  80 */         g.setColor(p.getBackground());
/*  81 */         g.fillRect(x, y, w, h);
/*  82 */         g.setColor(oldColor);
/*     */       }
/*  84 */       return;
/*     */     }
/*     */ 
/*  87 */     if (c.getParent() != this) {
/*  88 */       add(c);
/*     */     }
/*     */ 
/*  91 */     c.setBounds(x, y, w, h);
/*     */ 
/*  93 */     if (shouldValidate) {
/*  94 */       c.validate();
/*     */     }
/*     */ 
/*  97 */     boolean wasDoubleBuffered = false;
/*  98 */     if (((c instanceof JComponent)) && (c.isDoubleBuffered())) {
/*  99 */       wasDoubleBuffered = true;
/* 100 */       ((JComponent)c).setDoubleBuffered(false);
/*     */     }
/*     */ 
/* 103 */     Graphics cg = g.create(x, y, w, h);
/*     */     try {
/* 105 */       c.paint(cg);
/*     */     }
/*     */     finally {
/* 108 */       cg.dispose();
/*     */     }
/*     */ 
/* 111 */     if ((wasDoubleBuffered) && ((c instanceof JComponent))) {
/* 112 */       ((JComponent)c).setDoubleBuffered(true);
/*     */     }
/*     */ 
/* 115 */     c.setBounds(-w, -h, 0, 0);
/*     */   }
/*     */ 
/*     */   public void paintComponent(Graphics g, Component c, Container p, int x, int y, int w, int h)
/*     */   {
/* 123 */     paintComponent(g, c, p, x, y, w, h, false);
/*     */   }
/*     */ 
/*     */   public void paintComponent(Graphics g, Component c, Container p, Rectangle r)
/*     */   {
/* 131 */     paintComponent(g, c, p, r.x, r.y, r.width, r.height);
/*     */   }
/*     */ 
/*     */   private void writeObject(ObjectOutputStream s) throws IOException
/*     */   {
/* 136 */     removeAll();
/* 137 */     s.defaultWriteObject();
/*     */   }
/*     */ 
/*     */   public AccessibleContext getAccessibleContext()
/*     */   {
/* 154 */     if (this.accessibleContext == null) {
/* 155 */       this.accessibleContext = new AccessibleCellRendererPane();
/*     */     }
/* 157 */     return this.accessibleContext;
/*     */   }
/*     */ 
/*     */   protected class AccessibleCellRendererPane extends Container.AccessibleAWTContainer
/*     */   {
/*     */     protected AccessibleCellRendererPane() {
/* 163 */       super();
/*     */     }
/*     */ 
/*     */     public AccessibleRole getAccessibleRole()
/*     */     {
/* 176 */       return AccessibleRole.PANEL;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.JCellRendererPane
 * JD-Core Version:    0.6.0
 */
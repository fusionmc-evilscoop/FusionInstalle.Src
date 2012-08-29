/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.event.MouseInputListener;
/*     */ 
/*     */ public class SidePaneItem
/*     */ {
/*     */   private Icon _icon;
/*     */   private String _title;
/*     */   private Component _component;
/*     */   private Color _foreground;
/*     */   private Color _background;
/*     */   private MouseInputListener _mouseListener;
/*  27 */   private boolean _selected = false;
/*     */ 
/*     */   public SidePaneItem(String title)
/*     */   {
/*  35 */     this(title, null, null, null);
/*     */   }
/*     */ 
/*     */   public SidePaneItem(String title, Icon icon)
/*     */   {
/*  45 */     this(title, icon, null, null);
/*     */   }
/*     */ 
/*     */   public SidePaneItem(String title, Icon icon, Component component)
/*     */   {
/*  56 */     this(title, icon, component, null);
/*     */   }
/*     */ 
/*     */   public SidePaneItem(String title, Icon icon, Component component, MouseInputListener listener)
/*     */   {
/*  68 */     setTitle(title);
/*  69 */     setIcon(icon);
/*  70 */     setComponent(component);
/*  71 */     setMouseInputListener(listener);
/*     */   }
/*     */ 
/*     */   public Icon getIcon()
/*     */   {
/*  80 */     return this._icon;
/*     */   }
/*     */ 
/*     */   public void setIcon(Icon icon)
/*     */   {
/*  89 */     this._icon = icon;
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/*  98 */     return this._title;
/*     */   }
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/* 107 */     this._title = title;
/*     */   }
/*     */ 
/*     */   public Component getComponent()
/*     */   {
/* 116 */     return this._component;
/*     */   }
/*     */ 
/*     */   public void setComponent(Component component)
/*     */   {
/* 125 */     this._component = component;
/*     */   }
/*     */ 
/*     */   public MouseInputListener getMouseListener()
/*     */   {
/* 134 */     return this._mouseListener;
/*     */   }
/*     */ 
/*     */   public void setMouseInputListener(MouseInputListener mouseListener)
/*     */   {
/* 143 */     this._mouseListener = mouseListener;
/*     */   }
/*     */ 
/*     */   public boolean isSelected()
/*     */   {
/* 152 */     return this._selected;
/*     */   }
/*     */ 
/*     */   public void setSelected(boolean selected)
/*     */   {
/* 161 */     this._selected = selected;
/*     */   }
/*     */ 
/*     */   public Color getForeground() {
/* 165 */     return this._foreground;
/*     */   }
/*     */ 
/*     */   public void setForeground(Color foreground) {
/* 169 */     this._foreground = foreground;
/*     */   }
/*     */ 
/*     */   public Color getBackground() {
/* 173 */     return this._background;
/*     */   }
/*     */ 
/*     */   public void setBackground(Color background) {
/* 177 */     this._background = background;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.SidePaneItem
 * JD-Core Version:    0.6.0
 */
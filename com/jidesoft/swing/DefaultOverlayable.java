/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.ComponentListener;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.SwingUtilities;
/*     */ 
/*     */ public class DefaultOverlayable extends JPanel
/*     */   implements Overlayable, ComponentListener
/*     */ {
/*     */   private JComponent _actualComponent;
/*  24 */   private Insets _overlayLocationInsets = new Insets(0, 0, 0, 0);
/*     */   private List<JComponent> _overlayComponents;
/*     */   private Map<JComponent, Integer> _overlayLocations;
/*     */ 
/*     */   public DefaultOverlayable()
/*     */   {
/*  29 */     initComponents();
/*     */   }
/*     */ 
/*     */   public DefaultOverlayable(JComponent component) {
/*  33 */     initComponents();
/*  34 */     setActualComponent(component);
/*     */   }
/*     */ 
/*     */   public DefaultOverlayable(JComponent actualComponent, JComponent overlayComponent, int overlayLocation) {
/*  38 */     initComponents();
/*  39 */     setActualComponent(actualComponent);
/*  40 */     addOverlayComponent(overlayComponent, overlayLocation);
/*     */   }
/*     */ 
/*     */   public DefaultOverlayable(JComponent actualComponent, JComponent overlayComponent) {
/*  44 */     initComponents();
/*  45 */     setActualComponent(actualComponent);
/*  46 */     addOverlayComponent(overlayComponent, 0);
/*     */   }
/*     */ 
/*     */   private void initComponents() {
/*  50 */     setLayout(null);
/*  51 */     this._overlayComponents = new Vector();
/*  52 */     this._overlayLocations = new Hashtable();
/*     */   }
/*     */ 
/*     */   public Dimension getPreferredSize()
/*     */   {
/*  64 */     Dimension size = getActualComponent() == null ? new Dimension(0, 0) : getActualComponent().getPreferredSize();
/*  65 */     Insets insets = getOverlayLocationInsets();
/*  66 */     if (insets != null) {
/*  67 */       size.width += Math.max(0, insets.left) + Math.max(0, insets.right);
/*  68 */       size.height += Math.max(0, insets.top) + Math.max(0, insets.bottom);
/*     */     }
/*  70 */     return size;
/*     */   }
/*     */ 
/*     */   public void setPreferredSize(Dimension preferredSize)
/*     */   {
/*  75 */     super.setPreferredSize(preferredSize);
/*  76 */     if (getActualComponent() != null) {
/*  77 */       if (preferredSize != null) {
/*  78 */         Insets insets = getOverlayLocationInsets();
/*  79 */         if (insets != null) {
/*  80 */           preferredSize.width -= Math.max(0, insets.left) + Math.max(0, insets.right);
/*  81 */           preferredSize.width = Math.max(0, preferredSize.width);
/*  82 */           preferredSize.height -= Math.max(0, insets.top) + Math.max(0, insets.bottom);
/*  83 */           preferredSize.height = Math.max(0, preferredSize.height);
/*     */         }
/*     */       }
/*  86 */       getActualComponent().setPreferredSize(preferredSize);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Dimension getMinimumSize()
/*     */   {
/*  92 */     Dimension size = getActualComponent() == null ? new Dimension(0, 0) : getActualComponent().getMinimumSize();
/*  93 */     Insets insets = getOverlayLocationInsets();
/*  94 */     if (insets != null) {
/*  95 */       size.width += Math.max(0, insets.left) + Math.max(0, insets.right);
/*  96 */       size.height += Math.max(0, insets.top) + Math.max(0, insets.bottom);
/*     */     }
/*  98 */     return size;
/*     */   }
/*     */ 
/*     */   public void setMinimumSize(Dimension minimumSize)
/*     */   {
/* 103 */     super.setMinimumSize(minimumSize);
/* 104 */     if (getActualComponent() != null) {
/* 105 */       if (minimumSize != null) {
/* 106 */         Insets insets = getOverlayLocationInsets();
/* 107 */         if (insets != null) {
/* 108 */           minimumSize.width -= Math.max(0, insets.left) + Math.max(0, insets.right);
/* 109 */           minimumSize.width = Math.max(0, minimumSize.width);
/* 110 */           minimumSize.height -= Math.max(0, insets.top) + Math.max(0, insets.bottom);
/* 111 */           minimumSize.height = Math.max(0, minimumSize.height);
/*     */         }
/*     */       }
/* 114 */       getActualComponent().setMinimumSize(minimumSize);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setBounds(int x, int y, int width, int height)
/*     */   {
/* 120 */     super.setBounds(x, y, width, height);
/*     */ 
/* 122 */     Insets insets = getOverlayLocationInsets();
/* 123 */     x = Math.max(0, insets.left);
/* 124 */     y = Math.max(0, insets.top);
/* 125 */     width -= Math.max(0, insets.left) + Math.max(0, insets.right);
/* 126 */     height -= Math.max(0, insets.top) + Math.max(0, insets.bottom);
/* 127 */     getActualComponent().setBounds(x, y, width, height);
/*     */ 
/* 129 */     updateLocation();
/*     */   }
/*     */ 
/*     */   private void updateLocation() {
/* 133 */     JComponent[] components = getOverlayComponents();
/* 134 */     for (JComponent c : components) {
/* 135 */       if (c == null) {
/* 136 */         return;
/*     */       }
/*     */ 
/* 139 */       Rectangle r = getOverlayComponentBounds(c);
/* 140 */       c.setBounds(r);
/*     */     }
/*     */   }
/*     */ 
/*     */   private Rectangle getOverlayComponentBounds(JComponent component) {
/* 145 */     Component relativeComponent = getActualComponent();
/*     */ 
/* 147 */     Rectangle bounds = relativeComponent.getBounds();
/* 148 */     if (relativeComponent != getActualComponent()) {
/* 149 */       bounds = SwingUtilities.convertRectangle(relativeComponent.getParent(), bounds, getActualComponent());
/*     */     }
/* 151 */     Rectangle overlayBounds = new Rectangle(bounds);
/* 152 */     Insets insets = getOverlayLocationInsets();
/* 153 */     overlayBounds.x -= insets.left;
/* 154 */     overlayBounds.y -= insets.top;
/* 155 */     overlayBounds.width += insets.left + insets.right;
/* 156 */     overlayBounds.height += insets.top + insets.bottom;
/*     */ 
/* 158 */     int cx = 0;
/* 159 */     int cy = 0;
/*     */ 
/* 161 */     Dimension size = component.getPreferredSize();
/* 162 */     int cw = size.width;
/* 163 */     int ch = size.height;
/*     */ 
/* 165 */     switch (getOverlayLocation(component)) {
/*     */     case 0:
/* 167 */       cx = bounds.x + (bounds.width - cw) / 2;
/* 168 */       cy = bounds.y + (bounds.height - ch) / 2;
/* 169 */       break;
/*     */     case 1:
/* 171 */       cx = bounds.x + (bounds.width - cw) / 2;
/* 172 */       cy = overlayBounds.y;
/* 173 */       break;
/*     */     case 5:
/* 175 */       cx = bounds.x + (bounds.width - cw) / 2;
/* 176 */       cy = overlayBounds.y + overlayBounds.height - ch;
/* 177 */       break;
/*     */     case 7:
/* 179 */       cx = overlayBounds.x;
/* 180 */       cy = bounds.y + (bounds.height - ch) / 2;
/* 181 */       break;
/*     */     case 3:
/* 183 */       cx = overlayBounds.x + overlayBounds.width - cw;
/* 184 */       cy = bounds.y + (bounds.height - ch) / 2;
/* 185 */       break;
/*     */     case 8:
/* 187 */       cx = overlayBounds.x;
/* 188 */       cy = overlayBounds.y;
/* 189 */       break;
/*     */     case 2:
/* 191 */       cx = overlayBounds.x + overlayBounds.width - cw;
/* 192 */       cy = overlayBounds.y;
/* 193 */       break;
/*     */     case 6:
/* 195 */       cx = overlayBounds.x;
/* 196 */       cy = overlayBounds.y + overlayBounds.height - ch;
/* 197 */       break;
/*     */     case 4:
/* 199 */       cx = overlayBounds.x + overlayBounds.width - cw;
/* 200 */       cy = overlayBounds.y + overlayBounds.height - ch;
/*     */     }
/*     */ 
/* 204 */     return new Rectangle(cx, cy, cw, ch);
/*     */   }
/*     */ 
/*     */   public int getOverlayLocation(JComponent component) {
/* 208 */     Integer location = (Integer)this._overlayLocations.get(component);
/* 209 */     if (location != null) {
/* 210 */       return location.intValue();
/*     */     }
/*     */ 
/* 213 */     return -1;
/*     */   }
/*     */ 
/*     */   public void setOverlayLocation(JComponent component, int location)
/*     */   {
/* 218 */     setOverlayLocation(component, null, location);
/*     */   }
/*     */ 
/*     */   private void setOverlayLocation(JComponent component, Component relativeComponent, int location)
/*     */   {
/* 223 */     boolean updated = false;
/* 224 */     int old = getOverlayLocation(component);
/* 225 */     if (old != location) {
/* 226 */       this._overlayLocations.put(component, Integer.valueOf(location));
/* 227 */       updated = true;
/*     */     }
/* 229 */     if (updated)
/* 230 */       updateLocation();
/*     */   }
/*     */ 
/*     */   public void addOverlayComponent(JComponent component)
/*     */   {
/* 235 */     addOverlayComponent(component, 0, -1);
/*     */   }
/*     */ 
/*     */   public void addOverlayComponent(JComponent component, int location) {
/* 239 */     addOverlayComponent(component, location, -1);
/*     */   }
/*     */ 
/*     */   public void addOverlayComponent(JComponent component, int location, int index) {
/* 243 */     addOverlayComponent(component, null, location, index);
/*     */   }
/*     */ 
/*     */   private void addOverlayComponent(JComponent component, Component relativeComponent, int location, int index) {
/* 247 */     if (this._overlayComponents.contains(component)) {
/* 248 */       this._overlayComponents.remove(component);
/*     */     }
/* 250 */     if (index == -1) {
/* 251 */       this._overlayComponents.add(component);
/* 252 */       add(component, getComponentCount() - 1);
/*     */     }
/*     */     else {
/* 255 */       this._overlayComponents.add(index, component);
/* 256 */       add(component, index);
/*     */     }
/* 258 */     setOverlayLocation(component, relativeComponent, location);
/*     */   }
/*     */ 
/*     */   public void removeOverlayComponent(JComponent component) {
/* 262 */     if (this._overlayComponents.contains(component)) {
/* 263 */       this._overlayComponents.remove(component);
/* 264 */       this._overlayLocations.remove(component);
/* 265 */       remove(component);
/*     */     }
/*     */   }
/*     */ 
/*     */   public JComponent[] getOverlayComponents() {
/* 270 */     return (JComponent[])this._overlayComponents.toArray(new JComponent[this._overlayComponents.size()]);
/*     */   }
/*     */ 
/*     */   public JComponent getActualComponent() {
/* 274 */     return this._actualComponent;
/*     */   }
/*     */ 
/*     */   public void setActualComponent(JComponent actualComponent) {
/* 278 */     if (this._actualComponent != null) {
/* 279 */       remove(this._actualComponent);
/* 280 */       this._actualComponent.putClientProperty("Overlayable.overlayable", null);
/*     */     }
/* 282 */     this._actualComponent = actualComponent;
/* 283 */     this._actualComponent.putClientProperty("Overlayable.overlayable", this);
/* 284 */     add(this._actualComponent);
/* 285 */     Container container = getParent();
/* 286 */     if (container != null) {
/* 287 */       invalidate();
/* 288 */       container.validate();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Insets getOverlayLocationInsets()
/*     */   {
/* 294 */     return this._overlayLocationInsets;
/*     */   }
/*     */ 
/*     */   public void setOverlayLocationInsets(Insets overlayLocationInsets) {
/* 298 */     this._overlayLocationInsets = overlayLocationInsets;
/* 299 */     Container container = getParent();
/* 300 */     if (container != null) {
/* 301 */       invalidate();
/* 302 */       container.validate();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setOverlayVisible(boolean visible) {
/* 307 */     JComponent[] components = getOverlayComponents();
/* 308 */     for (JComponent component : components)
/* 309 */       component.setVisible(visible);
/*     */   }
/*     */ 
/*     */   public void componentResized(ComponentEvent e)
/*     */   {
/* 314 */     updateLocation();
/*     */   }
/*     */ 
/*     */   public void componentMoved(ComponentEvent e) {
/* 318 */     updateLocation();
/*     */   }
/*     */ 
/*     */   public void componentShown(ComponentEvent e) {
/* 322 */     updateLocation();
/*     */   }
/*     */ 
/*     */   public void componentHidden(ComponentEvent e) {
/* 326 */     updateLocation();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.DefaultOverlayable
 * JD-Core Version:    0.6.0
 */
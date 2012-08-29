/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.utils.PortingUtils;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.KeyEventDispatcher;
/*     */ import java.awt.KeyboardFocusManager;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.ComponentListener;
/*     */ import java.awt.event.ContainerEvent;
/*     */ import java.awt.event.ContainerListener;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.awt.event.WindowListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.swing.DefaultFocusManager;
/*     */ import javax.swing.JLayeredPane;
/*     */ import javax.swing.JWindow;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.event.EventListenerList;
/*     */ import javax.swing.event.PopupMenuEvent;
/*     */ import javax.swing.event.PopupMenuListener;
/*     */ 
/*     */ public class PopupWindow
/*     */ {
/*  33 */   protected EventListenerList listenerList = new EventListenerList();
/*     */   private JWindow _delegate;
/*     */   private Container _container;
/*  37 */   private List _grabbed = new Vector();
/*  38 */   private List _excluded = new Vector();
/*     */   private WindowListener _windowListener;
/*     */   private ComponentListener _componentListener;
/*     */   private ContainerListener _containerListener;
/*     */   private MouseListener _mouseListener;
/*     */   private Component _component;
/*     */   private KeyEventDispatcher _keyEventDispatcher;
/*     */   private Component _parent;
/*     */ 
/*     */   public PopupWindow(Container container)
/*     */   {
/*  53 */     this._container = container;
/*  54 */     createDelegate();
/*  55 */     createListeners();
/*     */   }
/*     */ 
/*     */   private void createDelegate() {
/*  59 */     Window window = getWindow();
/*  60 */     if (window != null)
/*  61 */       this._delegate = new JWindow(window);
/*     */   }
/*     */ 
/*     */   public void add(Component component)
/*     */   {
/*  66 */     this._component = component;
/*  67 */     this._component.addPropertyChangeListener("preferredSize", new PropertyChangeListener() {
/*     */       public void propertyChange(PropertyChangeEvent evt) {
/*  69 */         if (PopupWindow.this._delegate != null)
/*  70 */           PopupWindow.this._delegate.pack();
/*     */       }
/*     */     });
/*  74 */     if (this._delegate != null) {
/*  75 */       this._delegate.getContentPane().add(component);
/*  76 */       this._delegate.pack();
/*     */ 
/*  80 */       this._delegate.pack();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void show(Component relative, int x, int y)
/*     */   {
/*  86 */     this._parent = relative;
/*  87 */     if (this._delegate == null) {
/*  88 */       createDelegate();
/*  89 */       if (this._delegate == null) return;
/*  90 */       add(this._component);
/*     */     }
/*     */ 
/*  93 */     Point p = new Point(x, y);
/*     */ 
/*  95 */     SwingUtilities.convertPointToScreen(p, relative);
/*     */ 
/*  97 */     Rectangle screenSize = PortingUtils.getScreenBounds(relative);
/*     */ 
/*  99 */     Dimension size = this._component.getPreferredSize();
/*     */ 
/* 101 */     int left = p.x + size.width;
/* 102 */     int bottom = p.y + size.height;
/*     */ 
/* 104 */     if (p.x < screenSize.x) {
/* 105 */       p.x = screenSize.x;
/*     */     }
/* 107 */     if (left > screenSize.width) {
/* 108 */       p.x = (screenSize.width - size.width);
/*     */     }
/*     */ 
/* 111 */     if (p.y < screenSize.y) {
/* 112 */       p.y = screenSize.y;
/*     */     }
/* 114 */     if (bottom > screenSize.height) {
/* 115 */       p.y = (screenSize.height - size.height);
/*     */     }
/*     */ 
/* 119 */     this._delegate.setLocation(p.x, p.y);
/* 120 */     this._delegate.setSize(this._component.getPreferredSize());
/* 121 */     firePopupMenuWillBecomeVisible();
/* 122 */     this._delegate.setVisible(true);
/* 123 */     grabContainers();
/*     */ 
/* 144 */     this._keyEventDispatcher = new KeyEventDispatcher() {
/*     */       public boolean dispatchKeyEvent(KeyEvent e) {
/* 146 */         if (e.getKeyCode() == 27) {
/* 147 */           PopupWindow.this.hide();
/* 148 */           return true;
/*     */         }
/* 150 */         return false;
/*     */       }
/*     */     };
/* 153 */     DefaultFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this._keyEventDispatcher);
/*     */   }
/*     */ 
/*     */   public void hide() {
/* 157 */     if (this._parent != null) {
/* 158 */       this._parent.requestFocus();
/*     */     }
/* 160 */     firePopupMenuWillBecomeInvisible();
/*     */ 
/* 162 */     if (this._delegate != null) {
/* 163 */       this._delegate.setVisible(false);
/*     */     }
/*     */ 
/* 166 */     if (this._keyEventDispatcher != null)
/*     */     {
/* 170 */       DefaultFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(this._keyEventDispatcher);
/* 171 */       this._keyEventDispatcher = null;
/*     */     }
/* 173 */     releaseContainers();
/* 174 */     disposeDelegate();
/*     */   }
/*     */ 
/*     */   private void createListeners() {
/* 178 */     this._windowListener = new WindowAdapter()
/*     */     {
/*     */       public void windowClosing(WindowEvent e) {
/* 181 */         PopupWindow.this.hide();
/*     */       }
/*     */ 
/*     */       public void windowClosed(WindowEvent e)
/*     */       {
/* 186 */         PopupWindow.this.hide();
/*     */       }
/*     */ 
/*     */       public void windowIconified(WindowEvent e)
/*     */       {
/* 191 */         PopupWindow.this.hide();
/*     */       }
/*     */     };
/* 194 */     this._componentListener = new ComponentListener() {
/*     */       public void componentResized(ComponentEvent e) {
/* 196 */         PopupWindow.this.hide();
/*     */       }
/*     */ 
/*     */       public void componentMoved(ComponentEvent e) {
/* 200 */         PopupWindow.this.hide();
/*     */       }
/*     */ 
/*     */       public void componentShown(ComponentEvent e) {
/* 204 */         PopupWindow.this.hide();
/*     */       }
/*     */ 
/*     */       public void componentHidden(ComponentEvent e) {
/* 208 */         PopupWindow.this.hide();
/*     */       }
/*     */     };
/* 211 */     this._containerListener = new ContainerListener() {
/*     */       public void componentAdded(ContainerEvent e) {
/* 213 */         PopupWindow.this.hide();
/*     */       }
/*     */ 
/*     */       public void componentRemoved(ContainerEvent e) {
/* 217 */         PopupWindow.this.hide();
/*     */       }
/*     */     };
/* 220 */     this._mouseListener = new MouseAdapter()
/*     */     {
/*     */       public void mousePressed(MouseEvent e) {
/* 223 */         PopupWindow.this.hide();
/*     */       } } ;
/*     */   }
/*     */ 
/*     */   private void disposeDelegate() {
/* 229 */     if (this._delegate != null) {
/* 230 */       this._delegate.dispose();
/* 231 */       this._delegate = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   private Window getWindow() {
/* 236 */     Container c = this._container;
/* 237 */     if (c == null) {
/* 238 */       return null;
/*     */     }
/* 240 */     while ((!(c instanceof Window)) && (c.getParent() != null)) c = c.getParent();
/* 241 */     if ((c instanceof Window)) return (Window)c;
/* 242 */     return null;
/*     */   }
/*     */ 
/*     */   private void grabContainers() {
/* 246 */     Container c = this._container;
/* 247 */     while ((!(c instanceof Window)) && (c.getParent() != null))
/* 248 */       c = c.getParent();
/* 249 */     grabContainer(c);
/*     */   }
/*     */ 
/*     */   private void grabContainer(Container c) {
/* 253 */     if ((c instanceof Window)) {
/* 254 */       ((Window)c).addWindowListener(this._windowListener);
/* 255 */       c.addComponentListener(this._componentListener);
/* 256 */       this._grabbed.add(c);
/*     */     }
/*     */ 
/* 259 */     synchronized (c.getTreeLock()) {
/* 260 */       int ncomponents = c.getComponentCount();
/* 261 */       Component[] component = c.getComponents();
/* 262 */       for (int i = 0; i < ncomponents; i++) {
/* 263 */         Component comp = component[i];
/* 264 */         if ((!comp.isVisible()) || 
/* 265 */           (isExcludedComponent(comp)))
/*     */         {
/*     */           continue;
/*     */         }
/*     */ 
/* 275 */         comp.addMouseListener(this._mouseListener);
/* 276 */         this._grabbed.add(comp);
/* 277 */         if ((comp instanceof Container)) {
/* 278 */           Container cont = (Container)comp;
/* 279 */           if ((cont instanceof JLayeredPane)) {
/* 280 */             cont.addContainerListener(this._containerListener);
/*     */           }
/* 282 */           grabContainer(cont);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   void releaseContainers() {
/* 289 */     for (Iterator i$ = this._grabbed.iterator(); i$.hasNext(); ) { Object o = i$.next();
/* 290 */       Component c = (Component)o;
/* 291 */       if ((c instanceof Window)) {
/* 292 */         ((Window)c).removeWindowListener(this._windowListener);
/* 293 */         c.removeComponentListener(this._componentListener);
/*     */       }
/*     */       else {
/* 296 */         c.removeMouseListener(this._mouseListener);
/*     */       }
/*     */ 
/* 299 */       if (((c instanceof Container)) && 
/* 300 */         ((c instanceof JLayeredPane))) {
/* 301 */         ((Container)c).removeContainerListener(this._containerListener);
/*     */       }
/*     */     }
/*     */ 
/* 305 */     this._grabbed.clear();
/*     */   }
/*     */ 
/*     */   public boolean isVisible()
/*     */   {
/* 314 */     return this._delegate != null ? this._delegate.isVisible() : false;
/*     */   }
/*     */ 
/*     */   public void addPopupMenuListener(PopupMenuListener l)
/*     */   {
/* 329 */     this.listenerList.add(PopupMenuListener.class, l);
/*     */   }
/*     */ 
/*     */   public void removePopupMenuListener(PopupMenuListener l)
/*     */   {
/* 340 */     this.listenerList.remove(PopupMenuListener.class, l);
/*     */   }
/*     */ 
/*     */   public PopupMenuListener[] getPopupMenuListeners()
/*     */   {
/* 351 */     return (PopupMenuListener[])this.listenerList.getListeners(PopupMenuListener.class);
/*     */   }
/*     */ 
/*     */   public void firePopupMenuWillBecomeVisible()
/*     */   {
/* 363 */     Object[] listeners = this.listenerList.getListenerList();
/* 364 */     PopupMenuEvent e = null;
/* 365 */     for (int i = listeners.length - 2; i >= 0; i -= 2)
/* 366 */       if (listeners[i] == PopupMenuListener.class) {
/* 367 */         if (e == null)
/* 368 */           e = new PopupMenuEvent(this);
/* 369 */         ((PopupMenuListener)listeners[(i + 1)]).popupMenuWillBecomeVisible(e);
/*     */       }
/*     */   }
/*     */ 
/*     */   public void firePopupMenuWillBecomeInvisible()
/*     */   {
/* 383 */     Object[] listeners = this.listenerList.getListenerList();
/* 384 */     PopupMenuEvent e = null;
/* 385 */     for (int i = listeners.length - 2; i >= 0; i -= 2)
/* 386 */       if (listeners[i] == PopupMenuListener.class) {
/* 387 */         if (e == null)
/* 388 */           e = new PopupMenuEvent(this);
/* 389 */         ((PopupMenuListener)listeners[(i + 1)]).popupMenuWillBecomeInvisible(e);
/*     */       }
/*     */   }
/*     */ 
/*     */   public void firePopupMenuCanceled()
/*     */   {
/* 403 */     Object[] listeners = this.listenerList.getListenerList();
/* 404 */     PopupMenuEvent e = null;
/* 405 */     for (int i = listeners.length - 2; i >= 0; i -= 2)
/* 406 */       if (listeners[i] == PopupMenuListener.class) {
/* 407 */         if (e == null)
/* 408 */           e = new PopupMenuEvent(this);
/* 409 */         ((PopupMenuListener)listeners[(i + 1)]).popupMenuCanceled(e);
/*     */       }
/*     */   }
/*     */ 
/*     */   public void addAsExcludedComponents(Component comp)
/*     */   {
/* 421 */     if (this._excluded.contains(comp)) {
/* 422 */       return;
/*     */     }
/* 424 */     this._excluded.add(comp);
/*     */   }
/*     */ 
/*     */   public void removeFromExcludedComponents(Component comp) {
/* 428 */     if (!this._excluded.contains(comp)) {
/* 429 */       return;
/*     */     }
/* 431 */     this._excluded.remove(comp);
/*     */   }
/*     */ 
/*     */   public boolean isExcludedComponent(Component comp) {
/* 435 */     return this._excluded.contains(comp);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.PopupWindow
 * JD-Core Version:    0.6.0
 */
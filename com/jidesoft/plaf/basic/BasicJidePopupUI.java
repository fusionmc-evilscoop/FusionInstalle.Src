/*     */ package com.jidesoft.plaf.basic;
/*     */ 
/*     */ import com.jidesoft.plaf.PopupUI;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.popup.JidePopup;
/*     */ import com.jidesoft.swing.Gripper;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.IllegalComponentStateException;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.ActionMap;
/*     */ import javax.swing.InputMap;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ActionMapUIResource;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ 
/*     */ public class BasicJidePopupUI extends PopupUI
/*     */ {
/*     */   protected JidePopup _popup;
/*     */   protected PropertyChangeListener _propertyChangeListener;
/*     */   protected LayoutManager _dockableFrameLayout;
/*     */   protected JComponent _northPane;
/*     */   protected JComponent _southPane;
/*     */   protected JComponent _westPane;
/*     */   protected JComponent _eastPane;
/*     */   protected Gripper _titlePane;
/*  41 */   private boolean keyBindingRegistered = false;
/*  42 */   private boolean keyBindingActive = false;
/*     */ 
/*     */   public static ComponentUI createUI(JComponent b)
/*     */   {
/*  49 */     return new BasicJidePopupUI((JidePopup)b);
/*     */   }
/*     */ 
/*     */   public BasicJidePopupUI() {
/*     */   }
/*     */ 
/*     */   public BasicJidePopupUI(JidePopup f) {
/*  56 */     this._popup = f;
/*     */   }
/*     */ 
/*     */   public void installUI(JComponent c)
/*     */   {
/*  62 */     this._popup = ((JidePopup)c);
/*     */ 
/*  64 */     installDefaults();
/*  65 */     installListeners();
/*  66 */     installComponents();
/*  67 */     installKeyboardActions();
/*  68 */     this._popup.setOpaque(true);
/*     */   }
/*     */ 
/*     */   public void uninstallUI(JComponent c)
/*     */   {
/*  73 */     if (c != this._popup) {
/*  74 */       throw new IllegalComponentStateException(this + " was asked to deinstall() " + c + " when it only knows about " + this._popup + ".");
/*     */     }
/*     */ 
/*  78 */     uninstallKeyboardActions();
/*  79 */     uninstallComponents();
/*  80 */     uninstallListeners();
/*  81 */     uninstallDefaults();
/*  82 */     this._popup = null;
/*     */   }
/*     */ 
/*     */   protected void installDefaults() {
/*  86 */     JComponent contentPane = (JComponent)this._popup.getContentPane();
/*  87 */     if (contentPane != null) {
/*  88 */       Color bg = contentPane.getBackground();
/*  89 */       if ((bg instanceof UIResource))
/*  90 */         contentPane.setBackground(null);
/*     */     }
/*  92 */     this._popup.setLayout(this._dockableFrameLayout = createLayoutManager());
/*  93 */     this._popup.setBackground(UIDefaultsLookup.getColor("JideButton.background"));
/*     */ 
/*  95 */     LookAndFeel.installBorder(this._popup, "Popup.border");
/*     */   }
/*     */ 
/*     */   protected void installKeyboardActions()
/*     */   {
/* 100 */     ActionMap actionMap = getActionMap();
/* 101 */     SwingUtilities.replaceUIActionMap(this._popup, actionMap);
/*     */   }
/*     */ 
/*     */   ActionMap getActionMap() {
/* 105 */     ActionMap map = (ActionMap)UIDefaultsLookup.get("Popup.actionMap");
/* 106 */     if (map == null) {
/* 107 */       map = createActionMap();
/* 108 */       if (map != null) {
/* 109 */         UIManager.getLookAndFeelDefaults().put("Popup.actionMap", map);
/*     */       }
/*     */     }
/*     */ 
/* 113 */     return map;
/*     */   }
/*     */ 
/*     */   ActionMap createActionMap() {
/* 117 */     ActionMap map = new ActionMapUIResource();
/*     */ 
/* 119 */     return map;
/*     */   }
/*     */ 
/*     */   protected void installComponents() {
/* 123 */     setNorthPane(createNorthPane(this._popup));
/* 124 */     setSouthPane(createSouthPane(this._popup));
/* 125 */     setEastPane(createEastPane(this._popup));
/* 126 */     setWestPane(createWestPane(this._popup));
/*     */   }
/*     */ 
/*     */   protected void installListeners()
/*     */   {
/* 133 */     this._propertyChangeListener = createPropertyChangeListener();
/* 134 */     this._popup.addPropertyChangeListener(this._propertyChangeListener);
/*     */   }
/*     */ 
/*     */   InputMap getInputMap(int condition) {
/* 138 */     if (condition == 2) {
/* 139 */       return createInputMap(condition);
/*     */     }
/* 141 */     return null;
/*     */   }
/*     */ 
/*     */   InputMap createInputMap(int condition) {
/* 145 */     if (condition == 2) {
/* 146 */       Object[] bindings = (Object[])(Object[])UIDefaultsLookup.get("Popup.windowBindings");
/*     */ 
/* 148 */       if (bindings != null) {
/* 149 */         return LookAndFeel.makeComponentInputMap(this._popup, bindings);
/*     */       }
/*     */     }
/* 152 */     return null;
/*     */   }
/*     */ 
/*     */   protected void uninstallDefaults() {
/* 156 */     this._dockableFrameLayout = null;
/* 157 */     this._popup.setLayout(null);
/* 158 */     LookAndFeel.uninstallBorder(this._popup);
/*     */   }
/*     */ 
/*     */   protected void uninstallComponents() {
/* 162 */     setNorthPane(null);
/* 163 */     setSouthPane(null);
/* 164 */     setEastPane(null);
/* 165 */     setWestPane(null);
/* 166 */     this._titlePane = null;
/*     */   }
/*     */ 
/*     */   protected void uninstallListeners()
/*     */   {
/* 173 */     this._popup.removePropertyChangeListener(this._propertyChangeListener);
/* 174 */     this._propertyChangeListener = null;
/*     */   }
/*     */ 
/*     */   protected void uninstallKeyboardActions() {
/* 178 */     SwingUtilities.replaceUIInputMap(this._popup, 2, null);
/*     */ 
/* 180 */     SwingUtilities.replaceUIActionMap(this._popup, null);
/*     */   }
/*     */ 
/*     */   public Component getGripper()
/*     */   {
/* 186 */     return this._titlePane;
/*     */   }
/*     */ 
/*     */   protected LayoutManager createLayoutManager() {
/* 190 */     return new PopupLayout();
/*     */   }
/*     */ 
/*     */   protected PropertyChangeListener createPropertyChangeListener() {
/* 194 */     return new PopupPropertyChangeListener();
/*     */   }
/*     */ 
/*     */   public Dimension getPreferredSize(JComponent x)
/*     */   {
/* 200 */     if ((this._popup == x) && (this._popup.getLayout() != null))
/* 201 */       return this._popup.getLayout().preferredLayoutSize(x);
/* 202 */     return new Dimension(100, 100);
/*     */   }
/*     */ 
/*     */   public Dimension getMinimumSize(JComponent x)
/*     */   {
/* 207 */     if (this._popup == x) {
/* 208 */       return this._popup.getLayout().minimumLayoutSize(x);
/*     */     }
/* 210 */     return new Dimension(0, 0);
/*     */   }
/*     */ 
/*     */   public Dimension getMaximumSize(JComponent x)
/*     */   {
/* 215 */     return new Dimension(2147483647, 2147483647);
/*     */   }
/*     */ 
/*     */   protected void replacePane(JComponent currentPane, JComponent newPane)
/*     */   {
/* 224 */     if (currentPane != null) {
/* 225 */       deinstallMouseHandlers(currentPane);
/* 226 */       this._popup.remove(currentPane);
/*     */     }
/* 228 */     if (newPane != null) {
/* 229 */       this._popup.add(newPane);
/* 230 */       installMouseHandlers(newPane);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void deinstallMouseHandlers(JComponent c) {
/*     */   }
/*     */ 
/*     */   protected void installMouseHandlers(JComponent c) {
/*     */   }
/*     */ 
/*     */   protected JComponent createNorthPane(JidePopup w) {
/* 241 */     if ((w.getGripperLocation() == 1) && (w.isMovable())) {
/* 242 */       this._titlePane = new Gripper();
/* 243 */       this._titlePane.setOrientation(1);
/* 244 */       this._titlePane.setRolloverEnabled(true);
/* 245 */       this._titlePane.setOpaque(true);
/* 246 */       return this._titlePane;
/*     */     }
/*     */ 
/* 249 */     return null;
/*     */   }
/*     */ 
/*     */   protected JComponent createSouthPane(JidePopup w)
/*     */   {
/* 255 */     if ((w.getGripperLocation() == 5) && (w.isMovable())) {
/* 256 */       this._titlePane = new Gripper();
/* 257 */       this._titlePane.setOrientation(1);
/* 258 */       this._titlePane.setRolloverEnabled(true);
/* 259 */       this._titlePane.setOpaque(true);
/* 260 */       return this._titlePane;
/*     */     }
/*     */ 
/* 263 */     return null;
/*     */   }
/*     */ 
/*     */   protected JComponent createWestPane(JidePopup w)
/*     */   {
/* 268 */     if ((w.getGripperLocation() == 7) && (w.isMovable())) {
/* 269 */       this._titlePane = new Gripper();
/* 270 */       this._titlePane.setOrientation(0);
/* 271 */       this._titlePane.setRolloverEnabled(true);
/* 272 */       this._titlePane.setOpaque(true);
/* 273 */       return this._titlePane;
/*     */     }
/*     */ 
/* 276 */     return null;
/*     */   }
/*     */ 
/*     */   protected JComponent createEastPane(JidePopup w)
/*     */   {
/* 281 */     if ((w.getGripperLocation() == 3) && (w.isMovable())) {
/* 282 */       this._titlePane = new Gripper();
/* 283 */       this._titlePane.setOrientation(0);
/* 284 */       this._titlePane.setRolloverEnabled(true);
/* 285 */       this._titlePane.setOpaque(true);
/* 286 */       return this._titlePane;
/*     */     }
/*     */ 
/* 289 */     return null;
/*     */   }
/*     */ 
/*     */   protected final boolean isKeyBindingRegistered()
/*     */   {
/* 295 */     return this.keyBindingRegistered;
/*     */   }
/*     */ 
/*     */   protected final void setKeyBindingRegistered(boolean b) {
/* 299 */     this.keyBindingRegistered = b;
/*     */   }
/*     */ 
/*     */   public final boolean isKeyBindingActive() {
/* 303 */     return this.keyBindingActive;
/*     */   }
/*     */ 
/*     */   protected final void setKeyBindingActive(boolean b) {
/* 307 */     this.keyBindingActive = b;
/*     */   }
/*     */ 
/*     */   protected void setupMenuOpenKey()
/*     */   {
/* 316 */     InputMap map = getInputMap(2);
/* 317 */     SwingUtilities.replaceUIInputMap(this._popup, 2, map);
/*     */   }
/*     */ 
/*     */   protected void setupMenuCloseKey()
/*     */   {
/*     */   }
/*     */ 
/*     */   public JComponent getNorthPane()
/*     */   {
/* 327 */     return this._northPane;
/*     */   }
/*     */ 
/*     */   protected void setNorthPane(JComponent c) {
/* 331 */     replacePane(this._northPane, c);
/* 332 */     this._northPane = c;
/*     */   }
/*     */ 
/*     */   public JComponent getSouthPane() {
/* 336 */     return this._southPane;
/*     */   }
/*     */ 
/*     */   protected void setSouthPane(JComponent c) {
/* 340 */     replacePane(this._southPane, c);
/* 341 */     this._southPane = c;
/*     */   }
/*     */ 
/*     */   public JComponent getWestPane() {
/* 345 */     return this._westPane;
/*     */   }
/*     */ 
/*     */   protected void setWestPane(JComponent c) {
/* 349 */     replacePane(this._westPane, c);
/* 350 */     this._westPane = c;
/*     */   }
/*     */ 
/*     */   public JComponent getEastPane() {
/* 354 */     return this._eastPane;
/*     */   }
/*     */ 
/*     */   protected void setEastPane(JComponent c) {
/* 358 */     replacePane(this._eastPane, c);
/* 359 */     this._eastPane = c;
/*     */   }
/*     */ 
/*     */   public class PopupLayout
/*     */     implements LayoutManager
/*     */   {
/*     */     public PopupLayout()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void addLayoutComponent(String name, Component c)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void removeLayoutComponent(Component c)
/*     */     {
/*     */     }
/*     */ 
/*     */     public Dimension preferredLayoutSize(Container c)
/*     */     {
/* 389 */       Insets i = BasicJidePopupUI.this._popup.getInsets();
/*     */ 
/* 391 */       Dimension result = new Dimension(BasicJidePopupUI.this._popup.getRootPane().getPreferredSize());
/* 392 */       result.width += i.left + i.right;
/* 393 */       result.height += i.top + i.bottom;
/*     */ 
/* 395 */       if (BasicJidePopupUI.this.getNorthPane() != null) {
/* 396 */         Dimension d = BasicJidePopupUI.this.getNorthPane().getPreferredSize();
/* 397 */         result.width = Math.max(d.width, result.width);
/* 398 */         result.height += d.height;
/*     */       }
/*     */ 
/* 401 */       if (BasicJidePopupUI.this.getSouthPane() != null) {
/* 402 */         Dimension d = BasicJidePopupUI.this.getSouthPane().getPreferredSize();
/* 403 */         result.width = Math.max(d.width, result.width);
/* 404 */         result.height += d.height;
/*     */       }
/*     */ 
/* 407 */       if (BasicJidePopupUI.this.getEastPane() != null) {
/* 408 */         Dimension d = BasicJidePopupUI.this.getEastPane().getPreferredSize();
/* 409 */         result.width += d.width;
/* 410 */         result.height = Math.max(d.height, result.height);
/*     */       }
/*     */ 
/* 413 */       if (BasicJidePopupUI.this.getWestPane() != null) {
/* 414 */         Dimension d = BasicJidePopupUI.this.getWestPane().getPreferredSize();
/* 415 */         result.width += d.width;
/* 416 */         result.height = Math.max(d.height, result.height);
/*     */       }
/*     */ 
/* 419 */       return result;
/*     */     }
/*     */ 
/*     */     public Dimension minimumLayoutSize(Container c)
/*     */     {
/* 427 */       Dimension result = new Dimension();
/* 428 */       if (BasicJidePopupUI.this.getNorthPane() != null) {
/* 429 */         result = new Dimension(BasicJidePopupUI.this.getNorthPane().getMinimumSize());
/*     */       }
/* 431 */       if (BasicJidePopupUI.this.getSouthPane() != null) {
/* 432 */         Dimension minimumSize = BasicJidePopupUI.this.getSouthPane().getMinimumSize();
/* 433 */         result.width = Math.max(result.width, minimumSize.width);
/* 434 */         result.height += minimumSize.height;
/*     */       }
/* 436 */       if (BasicJidePopupUI.this.getEastPane() != null) {
/* 437 */         Dimension minimumSize = BasicJidePopupUI.this.getEastPane().getMinimumSize();
/* 438 */         result.width += minimumSize.width;
/* 439 */         result.height = Math.max(result.height, minimumSize.height);
/*     */       }
/* 441 */       if (BasicJidePopupUI.this.getWestPane() != null) {
/* 442 */         Dimension minimumSize = BasicJidePopupUI.this.getWestPane().getMinimumSize();
/* 443 */         result.width = Math.max(result.width, minimumSize.width);
/* 444 */         result.height += minimumSize.height;
/*     */       }
/* 446 */       Dimension alter = BasicJidePopupUI.this._popup.getContentPane().getMinimumSize();
/*     */ 
/* 448 */       if (alter.width > result.width) {
/* 449 */         result.width = alter.width;
/*     */       }
/* 451 */       result.height += alter.height;
/*     */ 
/* 453 */       Insets i = BasicJidePopupUI.this._popup.getInsets();
/* 454 */       result.width += i.left + i.right;
/* 455 */       result.height += i.top + i.bottom;
/*     */ 
/* 457 */       return result;
/*     */     }
/*     */ 
/*     */     public void layoutContainer(Container c) {
/* 461 */       Insets i = BasicJidePopupUI.this._popup.getInsets();
/*     */ 
/* 464 */       int cx = i.left;
/* 465 */       int cy = i.top;
/* 466 */       int cw = BasicJidePopupUI.this._popup.getWidth() - i.left - i.right;
/* 467 */       int ch = BasicJidePopupUI.this._popup.getHeight() - i.top - i.bottom;
/*     */ 
/* 469 */       if (BasicJidePopupUI.this.getNorthPane() != null) {
/* 470 */         BasicJidePopupUI.this.getNorthPane().setVisible(true);
/* 471 */         Dimension size = BasicJidePopupUI.this.getNorthPane().getPreferredSize();
/* 472 */         BasicJidePopupUI.this.getNorthPane().setBounds(cx, cy, cw, size.height);
/* 473 */         cy += size.height;
/* 474 */         ch -= size.height;
/*     */       }
/* 476 */       if (BasicJidePopupUI.this.getSouthPane() != null) {
/* 477 */         Dimension size = BasicJidePopupUI.this.getSouthPane().getPreferredSize();
/* 478 */         BasicJidePopupUI.this.getSouthPane().setBounds(cx, BasicJidePopupUI.this._popup.getHeight() - i.bottom - size.height, cw, size.height);
/*     */ 
/* 481 */         ch -= size.height;
/*     */       }
/* 483 */       if (BasicJidePopupUI.this.getWestPane() != null) {
/* 484 */         Dimension size = BasicJidePopupUI.this.getWestPane().getPreferredSize();
/* 485 */         BasicJidePopupUI.this.getWestPane().setBounds(cx, cy, size.width, ch);
/* 486 */         cw -= size.width;
/* 487 */         cx += size.width;
/*     */       }
/* 489 */       if (BasicJidePopupUI.this.getEastPane() != null) {
/* 490 */         Dimension size = BasicJidePopupUI.this.getEastPane().getPreferredSize();
/* 491 */         BasicJidePopupUI.this.getEastPane().setBounds(cw - size.width, cy, size.width, ch);
/* 492 */         cw -= size.width;
/*     */       }
/*     */ 
/* 495 */       if (BasicJidePopupUI.this._popup.getRootPane() != null)
/* 496 */         BasicJidePopupUI.this._popup.getRootPane().setBounds(cx, cy, cw, ch);
/*     */     }
/*     */   }
/*     */ 
/*     */   public class PopupPropertyChangeListener
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     public PopupPropertyChangeListener()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void propertyChange(PropertyChangeEvent evt)
/*     */     {
/* 367 */       String prop = evt.getPropertyName();
/* 368 */       JidePopup f = (JidePopup)evt.getSource();
/* 369 */       Object newValue = evt.getNewValue();
/* 370 */       Object oldValue = evt.getOldValue();
/* 371 */       if ("movable".equals(prop)) {
/* 372 */         f.updateUI();
/*     */       }
/* 374 */       if ("gripperLocation".equals(prop))
/* 375 */         f.updateUI();
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.BasicJidePopupUI
 * JD-Core Version:    0.6.0
 */
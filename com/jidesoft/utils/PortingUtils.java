/*     */ package com.jidesoft.utils;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Insets;
/*     */ import java.awt.KeyboardFocusManager;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.geom.Area;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JApplet;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ public class PortingUtils
/*     */ {
/*  20 */   private static Rectangle SCREEN_BOUNDS = null;
/*     */   private static Area SCREEN_AREA;
/*     */   private static Rectangle[] SCREENS;
/*     */   private static Insets[] INSETS;
/* 246 */   private static Thread _initializationThread = null;
/*     */ 
/* 302 */   public static boolean INITIALIZE_SCREEN_AREA_USING_THREAD = true;
/*     */ 
/*     */   public static Component getCurrentFocusComponent(AWTEvent event)
/*     */   {
/*  29 */     return KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
/*     */   }
/*     */ 
/*     */   public static int getFrameState(Frame frame)
/*     */   {
/*  39 */     return frame.getExtendedState();
/*     */   }
/*     */ 
/*     */   public static void setFrameState(Frame frame, int state)
/*     */   {
/*  49 */     frame.setExtendedState(state);
/*     */   }
/*     */ 
/*     */   public static int getMouseModifiers(MouseEvent e)
/*     */   {
/*  59 */     return e.getModifiersEx();
/*     */   }
/*     */ 
/*     */   public static void removeFocus(JComponent component)
/*     */   {
/*  68 */     component.setRequestFocusEnabled(false);
/*  69 */     component.setFocusable(false);
/*     */   }
/*     */ 
/*     */   public static void removeButtonBorder(AbstractButton button)
/*     */   {
/*  78 */     button.setContentAreaFilled(false);
/*  79 */     button.setMargin(new Insets(0, 0, 0, 0));
/*  80 */     button.setBorder(BorderFactory.createEmptyBorder());
/*     */   }
/*     */ 
/*     */   public static Rectangle containsInScreenBounds(Component invoker, Rectangle rect)
/*     */   {
/*  91 */     Rectangle screenBounds = getScreenBounds(invoker);
/*  92 */     Point p = rect.getLocation();
/*  93 */     if (p.x + rect.width > screenBounds.x + screenBounds.width) {
/*  94 */       p.x = (screenBounds.x + screenBounds.width - rect.width);
/*     */     }
/*  96 */     if (p.y + rect.height > screenBounds.y + screenBounds.height) {
/*  97 */       p.y = (screenBounds.y + screenBounds.height - rect.height);
/*     */     }
/*  99 */     if (p.x < screenBounds.x) {
/* 100 */       p.x = screenBounds.x;
/*     */     }
/* 102 */     if (p.y < screenBounds.y) {
/* 103 */       p.y = screenBounds.y;
/*     */     }
/* 105 */     return new Rectangle(p, rect.getSize());
/*     */   }
/*     */ 
/*     */   public static Rectangle overlapWithScreenBounds(Component invoker, Rectangle rect)
/*     */   {
/* 116 */     Rectangle screenBounds = getScreenBounds(invoker);
/* 117 */     Point p = rect.getLocation();
/* 118 */     if (p.x > screenBounds.x + screenBounds.width) {
/* 119 */       p.x = (screenBounds.x + screenBounds.width - rect.width);
/*     */     }
/* 121 */     if (p.y > screenBounds.y + screenBounds.height) {
/* 122 */       p.y = (screenBounds.y + screenBounds.height - rect.height);
/*     */     }
/* 124 */     if (p.x + rect.width < screenBounds.x) {
/* 125 */       p.x = screenBounds.x;
/*     */     }
/* 127 */     if (p.y + rect.height < screenBounds.y) {
/* 128 */       p.y = screenBounds.y;
/*     */     }
/* 130 */     return new Rectangle(p, rect.getSize());
/*     */   }
/*     */ 
/*     */   public static Dimension getScreenSize(Component invoker)
/*     */   {
/* 140 */     ensureScreenBounds();
/*     */ 
/* 143 */     Dimension screenSize = SCREEN_BOUNDS.getSize();
/*     */ 
/* 146 */     if ((invoker != null) && (!(invoker instanceof JApplet)) && (invoker.getGraphicsConfiguration() != null)) {
/* 147 */       Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(invoker.getGraphicsConfiguration());
/* 148 */       screenSize.width -= insets.left + insets.right;
/* 149 */       screenSize.height -= insets.top + insets.bottom;
/*     */     }
/*     */ 
/* 152 */     return screenSize;
/*     */   }
/*     */ 
/*     */   public static Dimension getLocalScreenSize(Component invoker)
/*     */   {
/* 162 */     ensureScreenBounds();
/*     */ 
/* 165 */     if ((invoker != null) && (!(invoker instanceof JApplet)) && (invoker.getGraphicsConfiguration() != null))
/*     */     {
/* 167 */       GraphicsConfiguration gc = invoker.getGraphicsConfiguration();
/* 168 */       Rectangle bounds = gc.getBounds();
/* 169 */       Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gc);
/* 170 */       bounds.width -= insets.left + insets.right;
/* 171 */       bounds.height -= insets.top + insets.bottom;
/* 172 */       return bounds.getSize();
/*     */     }
/*     */ 
/* 175 */     return getScreenSize(invoker);
/*     */   }
/*     */ 
/*     */   public static Rectangle getScreenBounds(Component invoker, boolean useInvokerDevice)
/*     */   {
/* 188 */     ensureScreenBounds();
/*     */ 
/* 191 */     Rectangle bounds = (!useInvokerDevice) || (invoker == null) || (invoker.getGraphicsConfiguration() == null) ? (Rectangle)SCREEN_BOUNDS.clone() : invoker.getGraphicsConfiguration().getBounds();
/*     */ 
/* 195 */     if ((invoker != null) && (!(invoker instanceof JApplet)) && (invoker.getGraphicsConfiguration() != null)) {
/* 196 */       Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(invoker.getGraphicsConfiguration());
/* 197 */       bounds.x += insets.left;
/* 198 */       bounds.y += insets.top;
/* 199 */       bounds.width -= insets.left + insets.right;
/* 200 */       bounds.height -= insets.top + insets.bottom;
/*     */     }
/*     */ 
/* 203 */     return bounds;
/*     */   }
/*     */ 
/*     */   public static Rectangle getScreenBounds(Component invoker)
/*     */   {
/* 217 */     return getScreenBounds(invoker, false);
/*     */   }
/*     */ 
/*     */   public static Rectangle getLocalScreenBounds()
/*     */   {
/* 226 */     GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
/* 227 */     return e.getMaximumWindowBounds();
/*     */   }
/*     */ 
/*     */   private static void ensureScreenBounds() {
/* 231 */     if (SCREEN_BOUNDS == null) {
/* 232 */       SCREEN_BOUNDS = new Rectangle();
/* 233 */       GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
/* 234 */       GraphicsDevice[] gs = ge.getScreenDevices();
/* 235 */       for (GraphicsDevice gd : gs) {
/* 236 */         GraphicsConfiguration gc = gd.getDefaultConfiguration();
/* 237 */         SCREEN_BOUNDS = SCREEN_BOUNDS.union(gc.getBounds());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static synchronized void initializeScreenArea()
/*     */   {
/* 254 */     initializeScreenArea(5);
/*     */   }
/*     */ 
/*     */   public static synchronized void initializeScreenArea(int priority)
/*     */   {
/* 269 */     if (_initializationThread == null) {
/* 270 */       _initializationThread = new Thread()
/*     */       {
/*     */         public void run() {
/* 273 */           PortingUtils.access$002(new Area());
/* 274 */           PortingUtils.access$102(new Rectangle());
/* 275 */           GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/* 276 */           List screensList = new ArrayList();
/* 277 */           List insetsList = new ArrayList();
/* 278 */           GraphicsDevice[] screenDevices = environment.getScreenDevices();
/* 279 */           for (GraphicsDevice device : screenDevices) {
/* 280 */             GraphicsConfiguration configuration = device.getDefaultConfiguration();
/* 281 */             Rectangle screenBounds = configuration.getBounds();
/* 282 */             Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(configuration);
/* 283 */             screensList.add(screenBounds);
/* 284 */             insetsList.add(insets);
/* 285 */             PortingUtils.SCREEN_AREA.add(new Area(screenBounds));
/* 286 */             PortingUtils.access$102(PortingUtils.SCREEN_BOUNDS.union(screenBounds));
/*     */           }
/* 288 */           PortingUtils.access$202((Rectangle[])screensList.toArray(new Rectangle[screensList.size()]));
/* 289 */           PortingUtils.access$302((Insets[])insetsList.toArray(new Insets[screensList.size()]));
/*     */         }
/*     */       };
/* 292 */       _initializationThread.setPriority(priority);
/* 293 */       if (INITIALIZE_SCREEN_AREA_USING_THREAD) {
/* 294 */         _initializationThread.start();
/*     */       }
/*     */       else
/* 297 */         _initializationThread.run();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean isInitializationThreadAlive()
/*     */   {
/* 305 */     return (_initializationThread != null) && (_initializationThread.isAlive());
/*     */   }
/*     */ 
/*     */   public static boolean isInitalizationThreadStarted() {
/* 309 */     return _initializationThread != null;
/*     */   }
/*     */ 
/*     */   private static void waitForInitialization() {
/* 313 */     initializeScreenArea();
/*     */ 
/* 315 */     while (_initializationThread.isAlive())
/*     */       try {
/* 317 */         Thread.sleep(100L);
/*     */       }
/*     */       catch (InterruptedException e)
/*     */       {
/*     */       }
/*     */   }
/*     */ 
/*     */   public static Rectangle ensureVisible(Component invoker, Rectangle bounds)
/*     */   {
/* 333 */     Rectangle mainScreenBounds = getLocalScreenBounds();
/* 334 */     if (!mainScreenBounds.contains(bounds.getLocation())) {
/* 335 */       Rectangle screenBounds = getScreenBounds(invoker, false);
/* 336 */       if ((bounds.x > screenBounds.x + screenBounds.width) || (bounds.x < screenBounds.x)) {
/* 337 */         bounds.x = mainScreenBounds.x;
/*     */       }
/* 339 */       if ((bounds.y > screenBounds.y + screenBounds.height) || (bounds.y < screenBounds.y)) {
/* 340 */         bounds.y = mainScreenBounds.y;
/*     */       }
/*     */     }
/* 343 */     return bounds;
/*     */   }
/*     */ 
/*     */   public static Rectangle ensureOnScreen(Rectangle rect)
/*     */   {
/* 354 */     Rectangle localScreenBounds = getLocalScreenBounds();
/* 355 */     if (localScreenBounds.contains(rect)) {
/* 356 */       return rect;
/*     */     }
/*     */ 
/* 359 */     waitForInitialization();
/*     */ 
/* 362 */     if (SCREEN_AREA.contains(rect)) return rect;
/*     */ 
/* 364 */     Rectangle containgScreen = null;
/* 365 */     Point rectPos = rect.getLocation();
/* 366 */     for (Rectangle screenBounds : SCREENS) {
/* 367 */       if (screenBounds.contains(rectPos)) {
/* 368 */         containgScreen = screenBounds;
/* 369 */         break;
/*     */       }
/*     */     }
/*     */ 
/* 373 */     for (Rectangle screenBounds : SCREENS) {
/* 374 */       if (screenBounds.intersects(rect)) {
/* 375 */         containgScreen = screenBounds;
/* 376 */         break;
/*     */       }
/*     */     }
/*     */ 
/* 380 */     if (containgScreen == null)
/*     */     {
/* 382 */       rect.x = ((SCREENS[0].width - rect.width) / 2);
/* 383 */       rect.y = ((SCREENS[0].width - rect.width) / 2);
/* 384 */       return rect;
/*     */     }
/*     */ 
/* 389 */     int rectRight = rect.x + rect.width;
/* 390 */     int screenRight = containgScreen.x + containgScreen.width;
/* 391 */     if (rectRight > screenRight) {
/* 392 */       rect.x = (screenRight - rect.width);
/*     */     }
/* 394 */     if (rect.x < containgScreen.x) rect.x = containgScreen.x;
/*     */ 
/* 396 */     int rectBottom = rect.y + rect.height;
/* 397 */     int screenBottom = containgScreen.y + containgScreen.height;
/* 398 */     if (rectBottom > screenBottom) {
/* 399 */       rect.y = (screenBottom - rect.height);
/*     */     }
/* 401 */     if (rect.y < containgScreen.y) rect.y = containgScreen.y;
/*     */ 
/* 403 */     return rect;
/*     */   }
/*     */ 
/*     */   public static Rectangle getContainingScreenBounds(Rectangle rect, boolean considerInsets)
/*     */   {
/* 415 */     waitForInitialization();
/*     */ 
/* 420 */     Rectangle containgScreen = null;
/* 421 */     Insets insets = null;
/* 422 */     Point rectPos = rect.getLocation();
/* 423 */     for (int i = 0; i < SCREENS.length; i++) {
/* 424 */       Rectangle screenBounds = SCREENS[i];
/* 425 */       if (screenBounds.contains(rectPos)) {
/* 426 */         containgScreen = screenBounds;
/* 427 */         insets = INSETS[i];
/* 428 */         break;
/*     */       }
/*     */     }
/*     */ 
/* 432 */     for (int i = 0; i < SCREENS.length; i++) {
/* 433 */       Rectangle screenBounds = SCREENS[i];
/* 434 */       if (screenBounds.intersects(rect)) {
/* 435 */         containgScreen = screenBounds;
/* 436 */         insets = INSETS[i];
/* 437 */         break;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 442 */     if (containgScreen == null) {
/* 443 */       containgScreen = SCREENS[0];
/* 444 */       insets = INSETS[0];
/*     */     }
/*     */ 
/* 447 */     Rectangle bounds = new Rectangle(containgScreen);
/* 448 */     if ((considerInsets) && (insets != null)) {
/* 449 */       bounds.x += insets.left;
/* 450 */       bounds.y += insets.top;
/* 451 */       bounds.width -= insets.left + insets.right;
/* 452 */       bounds.height -= insets.top + insets.bottom;
/*     */     }
/* 454 */     return bounds;
/*     */   }
/*     */ 
/*     */   public static Area getScreenArea()
/*     */   {
/* 464 */     waitForInitialization();
/* 465 */     return SCREEN_AREA;
/*     */   }
/*     */ 
/*     */   public static void notifyUser()
/*     */   {
/* 472 */     notifyUser(null);
/*     */   }
/*     */ 
/*     */   public static void notifyUser(Component component)
/*     */   {
/* 481 */     String beep = SecurityUtils.getProperty("jide.beepNotifyUser", "true");
/* 482 */     if ("true".equals(beep))
/* 483 */       UIManager.getLookAndFeel().provideErrorFeedback(component);
/*     */   }
/*     */ 
/*     */   public static void prerequisiteChecking()
/*     */   {
/* 491 */     if (!SystemInfo.isJdk14Above()) {
/* 492 */       notifyUser();
/* 493 */       JOptionPane.showMessageDialog(null, "J2SE 1.4 or above is required for this demo.", "JIDE Software, Inc.", 2);
/* 494 */       System.exit(0);
/*     */     }
/*     */ 
/* 497 */     if (!SystemInfo.isJdk142Above()) {
/* 498 */       notifyUser();
/* 499 */       JOptionPane.showMessageDialog(null, "J2SE 1.4.2 or above is recommended for this demo for the best experience of seamless integration with Windows XP.", "JIDE Software, Inc.", 2);
/*     */     }
/*     */ 
/* 502 */     if (SystemInfo.isMacOSX()) {
/* 503 */       System.setProperty("apple.laf.useScreenMenuBar", "true");
/* 504 */       System.setProperty("apple.awt.brushMetalLook", "true");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void setPreferredSize(Component component, Dimension size)
/*     */   {
/* 516 */     if (SystemInfo.isJdk15Above()) {
/* 517 */       component.setPreferredSize(size);
/*     */     }
/* 519 */     else if ((component instanceof JComponent))
/*     */     {
/* 521 */       ((JComponent)component).setPreferredSize(size);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void setMinimumSize(Component component, Dimension size)
/*     */   {
/* 533 */     if (SystemInfo.isJdk15Above()) {
/* 534 */       component.setMinimumSize(size);
/*     */     }
/* 536 */     else if ((component instanceof JComponent))
/*     */     {
/* 538 */       ((JComponent)component).setMinimumSize(size);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.PortingUtils
 * JD-Core Version:    0.6.0
 */
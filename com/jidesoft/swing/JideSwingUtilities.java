/*      */ package com.jidesoft.swing;
/*      */ 
/*      */ import com.jidesoft.dialog.ButtonPanel;
/*      */ import com.jidesoft.plaf.UIDefaultsLookup;
/*      */ import com.jidesoft.plaf.WindowsDesktopProperty;
/*      */ import com.jidesoft.utils.SecurityUtils;
/*      */ import com.jidesoft.utils.SystemInfo;
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.ComponentOrientation;
/*      */ import java.awt.Container;
/*      */ import java.awt.DefaultKeyboardFocusManager;
/*      */ import java.awt.Dialog;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FocusTraversalPolicy;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Frame;
/*      */ import java.awt.GradientPaint;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.GridBagConstraints;
/*      */ import java.awt.GridBagLayout;
/*      */ import java.awt.HeadlessException;
/*      */ import java.awt.Insets;
/*      */ import java.awt.KeyboardFocusManager;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.RenderingHints.Key;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.InputEvent;
/*      */ import java.awt.event.KeyListener;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseListener;
/*      */ import java.awt.event.MouseMotionListener;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.PrintStream;
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.security.AccessControlException;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.text.DecimalFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.EventListener;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import java.util.logging.Logger;
/*      */ import javax.swing.AbstractAction;
/*      */ import javax.swing.AbstractButton;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.ButtonModel;
/*      */ import javax.swing.CellEditor;
/*      */ import javax.swing.DefaultComboBoxModel;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JApplet;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.JMenu;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JPopupMenu;
/*      */ import javax.swing.JRootPane;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JSeparator;
/*      */ import javax.swing.JTabbedPane;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.JViewport;
/*      */ import javax.swing.JWindow;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.ListCellRenderer;
/*      */ import javax.swing.RootPaneContainer;
/*      */ import javax.swing.SwingConstants;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.UIDefaults;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.border.CompoundBorder;
/*      */ import javax.swing.border.EmptyBorder;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.event.EventListenerList;
/*      */ import javax.swing.event.TableModelListener;
/*      */ import javax.swing.plaf.FontUIResource;
/*      */ import javax.swing.plaf.UIResource;
/*      */ import javax.swing.table.AbstractTableModel;
/*      */ import javax.swing.table.DefaultTableModel;
/*      */ import javax.swing.table.TableCellRenderer;
/*      */ import javax.swing.table.TableModel;
/*      */ import javax.swing.text.JTextComponent;
/*      */ import javax.swing.text.View;
/*      */ import javax.swing.tree.TreeCellRenderer;
/*      */ 
/*      */ public class JideSwingUtilities
/*      */   implements SwingConstants
/*      */ {
/*   51 */   private static final Logger LOGGER_FOCUS = Logger.getLogger(JideSwingUtilities.class.getName() + ".focus");
/*      */   private static final boolean AA_TEXT;
/*      */   private static final boolean AA_TEXT_DEFINED;
/*   66 */   public static final Object AA_TEXT_PROPERTY_KEY = new StringBuffer("AATextPropertyKey");
/*      */   private static PropertyChangeListener _setOpaqueTrueListener;
/*      */   private static PropertyChangeListener _setOpaqueFalseListener;
/*      */   private static final String OPAQUE_LISTENER = "setOpaqueRecursively.opaqueListener";
/*      */   public static final String SET_OPAQUE_RECURSIVELY_EXCLUDED = "setOpaqueRecursively.excluded";
/*      */   static RenderingHints renderingHints;
/*      */   private static Class<?> _radialGradientPaintClass;
/*      */   private static Constructor<?> _radialGradientPaintConstructor1;
/*      */   private static Constructor<?> _radialGradientPaintConstructor2;
/*      */   private static Class<?> _linearGradientPaintClass;
/*      */   private static Constructor<?> _linearGradientPaintConstructor1;
/*      */   private static Constructor<?> _linearGradientPaintConstructor2;
/*      */   protected static boolean tracingFocus;
/*      */   private static ChangeListener _viewportSyncListener;
/*      */ 
/*      */   public static JPanel createLeftPanel(Component object)
/*      */   {
/*   82 */     JPanel ret = new NullPanel(new BorderLayout());
/*   83 */     ret.setOpaque(false);
/*   84 */     ret.add(object, "Before");
/*   85 */     return ret;
/*      */   }
/*      */ 
/*      */   public static JPanel createRightPanel(Component object)
/*      */   {
/*   95 */     JPanel ret = new NullPanel(new BorderLayout());
/*   96 */     ret.setOpaque(false);
/*   97 */     ret.add(object, "After");
/*   98 */     return ret;
/*      */   }
/*      */ 
/*      */   public static JPanel createTopPanel(Component object)
/*      */   {
/*  108 */     JPanel ret = new NullPanel(new BorderLayout());
/*  109 */     ret.setOpaque(false);
/*  110 */     ret.add(object, "First");
/*  111 */     return ret;
/*      */   }
/*      */ 
/*      */   public static JPanel createBottomPanel(Component object)
/*      */   {
/*  121 */     JPanel ret = new NullPanel(new BorderLayout());
/*  122 */     ret.setOpaque(false);
/*  123 */     ret.add(object, "Last");
/*  124 */     return ret;
/*      */   }
/*      */ 
/*      */   public static JPanel createCenterPanel(Component object)
/*      */   {
/*  134 */     JPanel ret = new NullPanel(new GridBagLayout());
/*  135 */     ret.setOpaque(false);
/*  136 */     ret.add(object, new GridBagConstraints());
/*  137 */     return ret;
/*      */   }
/*      */ 
/*      */   public static JPanel createLabeledComponent(JLabel title, Component component, Object constraint)
/*      */   {
/*  150 */     JPanel ret = new NullPanel(new JideBorderLayout(3, 3));
/*  151 */     ret.setOpaque(false);
/*  152 */     ret.add(title, constraint);
/*  153 */     title.setLabelFor(component);
/*  154 */     ret.add(component);
/*  155 */     return ret;
/*      */   }
/*      */ 
/*      */   public static void centerWindow(Window childToCenter)
/*      */   {
/*  164 */     childToCenter.setLocationRelativeTo(childToCenter.getParent());
/*      */   }
/*      */ 
/*      */   public static void globalCenterWindow(Window childToCenter)
/*      */   {
/*  183 */     childToCenter.setLocationRelativeTo(null);
/*      */   }
/*      */ 
/*      */   public static void paintArrow(Graphics g, Color color, int startX, int startY, int width, int orientation)
/*      */   {
/*  204 */     Color oldColor = g.getColor();
/*  205 */     g.setColor(color);
/*  206 */     width = width / 2 * 2 + 1;
/*  207 */     if (orientation == 0) {
/*  208 */       for (int i = 0; i < (width + 1) / 2; i++) {
/*  209 */         g.drawLine(startX + i, startY + i, startX + width - i - 1, startY + i);
/*      */       }
/*      */     }
/*  212 */     else if (orientation == 1) {
/*  213 */       for (int i = 0; i < (width + 1) / 2; i++) {
/*  214 */         g.drawLine(startX + i, startY + i, startX + i, startY + width - i - 1);
/*      */       }
/*      */     }
/*  217 */     g.setColor(oldColor);
/*      */   }
/*      */ 
/*      */   public static void paintArrow(JComponent c, Graphics g, Color color, int startX, int startY, int width, int orientation)
/*      */   {
/*  232 */     if (!c.getComponentOrientation().isLeftToRight()) {
/*  233 */       Color oldColor = g.getColor();
/*  234 */       g.setColor(color);
/*  235 */       width = width / 2 * 2 + 1;
/*  236 */       for (int i = 0; i < (width + 1) / 2; i++) {
/*  237 */         g.drawLine(startX + width - i, startY + i, startX + width - i, startY + width - i - 1);
/*      */       }
/*  239 */       g.setColor(oldColor);
/*  240 */       return;
/*      */     }
/*      */ 
/*  243 */     paintArrow(g, color, startX, startY, width, orientation);
/*      */   }
/*      */ 
/*      */   public static void paintCross(Graphics g, Color color, int centerX, int centerY, int size, int width)
/*      */   {
/*  257 */     g.setColor(color);
/*  258 */     size /= 2;
/*  259 */     for (int i = 0; i < width; i++) {
/*  260 */       g.drawLine(centerX - size, centerY - size, centerX + size, centerY + size);
/*  261 */       g.drawLine(centerX + size, centerY - size, centerX - size, centerY + size);
/*  262 */       centerX++;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static Frame getFrame(Component component)
/*      */   {
/*  273 */     if (component == null) return null;
/*      */ 
/*  275 */     if ((component instanceof Frame)) return (Frame)component;
/*      */ 
/*  278 */     Container p = component.getParent();
/*  279 */     while (p != null) {
/*  280 */       if ((p instanceof Frame)) {
/*  281 */         return (Frame)p;
/*      */       }
/*  283 */       p = p.getParent();
/*      */     }
/*  285 */     return null;
/*      */   }
/*      */ 
/*      */   public static void toggleRTLnLTR(Component topContainer)
/*      */   {
/*  295 */     ComponentOrientation co = topContainer.getComponentOrientation();
/*  296 */     if (co == ComponentOrientation.RIGHT_TO_LEFT)
/*  297 */       co = ComponentOrientation.LEFT_TO_RIGHT;
/*      */     else
/*  299 */       co = ComponentOrientation.RIGHT_TO_LEFT;
/*  300 */     topContainer.applyComponentOrientation(co);
/*      */   }
/*      */ 
/*      */   public static void synchronizeView(JViewport masterViewport, JViewport slaveViewport, int orientation)
/*      */   {
/*  313 */     ChangeListener[] changeListeners = masterViewport.getChangeListeners();
/*  314 */     int i = 0;
/*  315 */     while ((i < changeListeners.length) && 
/*  316 */       (changeListeners[i] != getViewportSynchronizationChangeListener())) {
/*  315 */       i++;
/*      */     }
/*      */ 
/*  320 */     if (i >= changeListeners.length) {
/*  321 */       masterViewport.addChangeListener(getViewportSynchronizationChangeListener());
/*      */     }
/*      */ 
/*  324 */     Object property = masterViewport.getClientProperty("synchronizeViewSlaveViewport");
/*  325 */     if (!(property instanceof Map)) {
/*  326 */       property = new HashMap();
/*      */     }
/*  328 */     Map slaveViewportMap = (Map)property;
/*  329 */     slaveViewportMap.put(slaveViewport, Integer.valueOf(orientation));
/*  330 */     masterViewport.putClientProperty("synchronizeViewSlaveViewport", slaveViewportMap);
/*      */ 
/*  332 */     property = slaveViewport.getClientProperty("synchronizeViewMasterViewport");
/*  333 */     if (!(property instanceof Map)) {
/*  334 */       property = new HashMap();
/*      */     }
/*  336 */     Map masterViewportMap = (Map)property;
/*  337 */     masterViewportMap.put(masterViewport, Integer.valueOf(orientation));
/*  338 */     slaveViewport.putClientProperty("synchronizeViewMasterViewport", masterViewportMap);
/*      */   }
/*      */ 
/*      */   public static void unsynchronizeView(JViewport masterViewport, JViewport slaveViewport)
/*      */   {
/*  348 */     Object property = masterViewport.getClientProperty("synchronizeViewSlaveViewport");
/*  349 */     if ((property instanceof Map)) {
/*  350 */       Map slaveViewportMap = (Map)property;
/*  351 */       slaveViewportMap.remove(slaveViewport);
/*  352 */       if (slaveViewportMap.isEmpty()) {
/*  353 */         slaveViewportMap = null;
/*  354 */         masterViewport.removeChangeListener(getViewportSynchronizationChangeListener());
/*      */       }
/*  356 */       masterViewport.putClientProperty("synchronizeViewSlaveViewport", slaveViewportMap);
/*      */     }
/*      */ 
/*  359 */     property = slaveViewport.getClientProperty("synchronizeViewMasterViewport");
/*  360 */     if ((property instanceof Map)) {
/*  361 */       Map masterViewportMap = (Map)property;
/*  362 */       masterViewportMap.remove(masterViewport);
/*  363 */       if (masterViewportMap.isEmpty()) {
/*  364 */         masterViewportMap = null;
/*      */       }
/*  366 */       slaveViewport.putClientProperty("synchronizeViewMasterViewport", masterViewportMap);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static int getButtonState(AbstractButton b) {
/*  371 */     ButtonModel model = b.getModel();
/*  372 */     if (!model.isEnabled()) {
/*  373 */       if (model.isSelected()) {
/*  374 */         return 5;
/*      */       }
/*      */ 
/*  377 */       return 4;
/*      */     }
/*      */ 
/*  380 */     if ((model.isPressed()) && (model.isArmed())) {
/*  381 */       if (model.isRollover()) {
/*  382 */         return 1;
/*      */       }
/*  384 */       if (model.isSelected())
/*  385 */         return 3;
/*      */     }
/*      */     else {
/*  388 */       if ((b.isRolloverEnabled()) && (model.isRollover())) {
/*  389 */         if (model.isSelected()) {
/*  390 */           return 1;
/*      */         }
/*      */ 
/*  393 */         return 2;
/*      */       }
/*      */ 
/*  396 */       if (model.isSelected()) {
/*  397 */         return 3;
/*      */       }
/*  399 */       if ((b.hasFocus()) && (b.isFocusPainted())) {
/*  400 */         if (model.isSelected()) {
/*  401 */           return 1;
/*      */         }
/*      */ 
/*  404 */         return 2;
/*      */       }
/*      */     }
/*  407 */     return 0;
/*      */   }
/*      */ 
/*      */   public static int[] getButtonState(JideSplitButton b) {
/*  411 */     int[] states = new int[2];
/*  412 */     SplitButtonModel model = (SplitButtonModel)b.getModel();
/*  413 */     if (!model.isEnabled()) {
/*  414 */       if (model.isButtonSelected()) {
/*  415 */         states[0] = 5;
/*      */       }
/*      */       else {
/*  418 */         states[0] = 4;
/*      */       }
/*      */     }
/*  421 */     else if ((b.hasFocus()) && (b.isFocusPainted())) {
/*  422 */       if (model.isButtonSelected()) {
/*  423 */         states[0] = 3;
/*  424 */         states[1] = 7;
/*      */       }
/*  426 */       else if (model.isSelected()) {
/*  427 */         states[0] = 7;
/*  428 */         states[1] = 3;
/*      */       }
/*      */       else {
/*  431 */         states[0] = 2;
/*  432 */         states[1] = 7;
/*      */       }
/*      */     }
/*  435 */     else if ((model.isPressed()) && (model.isArmed())) {
/*  436 */       if (model.isButtonRollover()) {
/*  437 */         states[0] = 1;
/*  438 */         states[1] = 7;
/*      */       }
/*  440 */       else if (model.isRollover()) {
/*  441 */         states[0] = 7;
/*  442 */         states[1] = 2;
/*      */       }
/*      */     }
/*  445 */     else if ((b.isRolloverEnabled()) && (model.isButtonRollover())) {
/*  446 */       if (model.isButtonSelected()) {
/*  447 */         states[0] = 1;
/*  448 */         states[1] = 7;
/*      */       }
/*  450 */       else if (model.isSelected()) {
/*  451 */         states[0] = 2;
/*  452 */         states[1] = 1;
/*      */       }
/*      */       else {
/*  455 */         states[0] = 2;
/*  456 */         states[1] = 7;
/*      */       }
/*      */     }
/*  459 */     else if ((b.isRolloverEnabled()) && (model.isRollover())) {
/*  460 */       if (model.isButtonSelected()) {
/*  461 */         states[0] = 1;
/*  462 */         states[1] = 2;
/*      */       }
/*  464 */       else if (model.isSelected()) {
/*  465 */         states[0] = 7;
/*  466 */         states[1] = 1;
/*      */       }
/*      */       else {
/*  469 */         states[0] = 7;
/*  470 */         states[1] = 2;
/*      */       }
/*      */     }
/*  473 */     else if (model.isButtonSelected()) {
/*  474 */       states[0] = 3;
/*  475 */       states[1] = 7;
/*      */     }
/*  477 */     else if (model.isSelected()) {
/*  478 */       states[0] = 7;
/*  479 */       states[1] = 3;
/*      */     }
/*      */     else {
/*  482 */       states[0] = 0;
/*  483 */       states[1] = 0;
/*      */     }
/*  485 */     return states;
/*      */   }
/*      */ 
/*      */   public static boolean equals(Object o1, Object o2)
/*      */   {
/*  498 */     return equals(o1, o2, false);
/*      */   }
/*      */ 
/*      */   public static boolean equals(Object o1, Object o2, boolean considerArray)
/*      */   {
/*  514 */     if (o1 == o2) {
/*  515 */       return true;
/*      */     }
/*  517 */     if ((o1 != null) && (o2 == null)) {
/*  518 */       return false;
/*      */     }
/*  520 */     if (o1 == null) {
/*  521 */       return false;
/*      */     }
/*  523 */     if (((o1 instanceof Comparable)) && ((o2 instanceof Comparable)) && (o1.getClass().isAssignableFrom(o2.getClass()))) {
/*  524 */       return ((Comparable)o1).compareTo(o2) == 0;
/*      */     }
/*  526 */     if (((o1 instanceof Comparable)) && ((o2 instanceof Comparable)) && (o2.getClass().isAssignableFrom(o1.getClass()))) {
/*  527 */       return ((Comparable)o2).compareTo(o1) == 0;
/*      */     }
/*      */ 
/*  530 */     if ((considerArray) && (o1.getClass().isArray()) && (o2.getClass().isArray())) {
/*  531 */       int length1 = Array.getLength(o1);
/*  532 */       int length2 = Array.getLength(o2);
/*  533 */       if (length1 != length2) {
/*  534 */         return false;
/*      */       }
/*  536 */       for (int i = 0; i < length1; i++) {
/*  537 */         boolean equals = equals(Array.get(o1, i), Array.get(o2, i));
/*  538 */         if (!equals) {
/*  539 */           return false;
/*      */         }
/*      */       }
/*  542 */       return true;
/*      */     }
/*      */ 
/*  545 */     return o1.equals(o2);
/*      */   }
/*      */ 
/*      */   public static BufferedImage getFasterScaledInstance(BufferedImage img, int targetWidth, int targetHeight, Object hint, boolean progressiveBilinear)
/*      */   {
/*  567 */     int type = img.getTransparency() == 1 ? 1 : 2;
/*      */ 
/*  569 */     BufferedImage ret = img;
/*  570 */     BufferedImage scratchImage = null;
/*  571 */     Graphics2D g2 = null;
/*      */ 
/*  573 */     int prevW = ret.getWidth();
/*  574 */     int prevH = ret.getHeight();
/*  575 */     boolean isTranslucent = img.getTransparency() != 1;
/*      */     int h;
/*      */     int w;
/*      */     int h;
/*  577 */     if (progressiveBilinear)
/*      */     {
/*  581 */       int w = img.getWidth();
/*  582 */       h = img.getHeight();
/*      */     }
/*      */     else
/*      */     {
/*  587 */       w = targetWidth;
/*  588 */       h = targetHeight;
/*      */     }
/*      */     do
/*      */     {
/*  592 */       if ((progressiveBilinear) && (w > targetWidth)) {
/*  593 */         w /= 2;
/*  594 */         if (w < targetWidth) {
/*  595 */           w = targetWidth;
/*      */         }
/*      */       }
/*      */ 
/*  599 */       if ((progressiveBilinear) && (h > targetHeight)) {
/*  600 */         h /= 2;
/*  601 */         if (h < targetHeight) {
/*  602 */           h = targetHeight;
/*      */         }
/*      */       }
/*      */ 
/*  606 */       if ((scratchImage == null) || (isTranslucent))
/*      */       {
/*  610 */         scratchImage = new BufferedImage(w, h, type);
/*  611 */         g2 = scratchImage.createGraphics();
/*      */       }
/*      */ 
/*  614 */       if (g2 != null) {
/*  615 */         g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
/*  616 */         g2.drawImage(ret, 0, 0, w, h, 0, 0, prevW, prevH, null);
/*      */       }
/*      */ 
/*  619 */       prevW = w;
/*  620 */       prevH = h;
/*      */ 
/*  622 */       ret = scratchImage;
/*      */     }
/*  624 */     while ((w != targetWidth) || (h != targetHeight));
/*      */ 
/*  626 */     if (g2 != null) {
/*  627 */       g2.dispose();
/*      */     }
/*      */ 
/*  632 */     if ((targetWidth != ret.getWidth()) || (targetHeight != ret.getHeight())) {
/*  633 */       scratchImage = new BufferedImage(targetWidth, targetHeight, type);
/*  634 */       g2 = scratchImage.createGraphics();
/*  635 */       g2.drawImage(ret, 0, 0, null);
/*  636 */       g2.dispose();
/*  637 */       ret = scratchImage;
/*      */     }
/*      */ 
/*  640 */     return ret;
/*      */   }
/*      */ 
/*      */   public static boolean shouldUseSystemFont()
/*      */   {
/*  704 */     String property = SecurityUtils.getProperty("jide.useSystemfont", "");
/*  705 */     if ("false".equals(property)) {
/*  706 */       return false;
/*      */     }
/*  708 */     if ("true".equals(property)) {
/*  709 */       return true;
/*      */     }
/*      */ 
/*  712 */     if ((SystemInfo.isJdk15Above()) || (SystemInfo.isCJKLocale())) {
/*  713 */       return true;
/*      */     }
/*      */ 
/*  716 */     String systemFonts = null;
/*      */     try {
/*  718 */       systemFonts = (String)AccessController.doPrivileged(new GetPropertyAction("swing.useSystemFontSettings"));
/*      */     }
/*      */     catch (AccessControlException e)
/*      */     {
/*      */     }
/*      */ 
/*  724 */     boolean useSystemFontSettings = (systemFonts != null) && (Boolean.valueOf(systemFonts).booleanValue());
/*      */ 
/*  727 */     if (useSystemFontSettings) {
/*  728 */       Object value = UIDefaultsLookup.get("Application.useSystemFontSettings");
/*      */ 
/*  730 */       useSystemFontSettings = (value != null) || (Boolean.TRUE.equals(value));
/*      */     }
/*      */ 
/*  734 */     return ("true".equals(SecurityUtils.getProperty("defaultFont", "false"))) || (useSystemFontSettings);
/*      */   }
/*      */ 
/*      */   public static void printUIDefaults() {
/*  738 */     Enumeration e = UIManager.getDefaults().keys();
/*  739 */     List list = new ArrayList();
/*      */ 
/*  741 */     System.out.println("Non-string keys ---");
/*  742 */     while (e.hasMoreElements()) {
/*  743 */       Object key = e.nextElement();
/*  744 */       if ((key instanceof String)) {
/*  745 */         list.add((String)key);
/*      */       }
/*      */       else {
/*  748 */         System.out.println(key + " => " + UIDefaultsLookup.get(key));
/*      */       }
/*      */     }
/*      */ 
/*  752 */     System.out.println();
/*      */ 
/*  754 */     Collections.sort(list);
/*  755 */     System.out.println("String keys ---");
/*  756 */     for (Object key : list)
/*  757 */       System.out.println(key + " => " + UIDefaultsLookup.get(key));
/*      */   }
/*      */ 
/*      */   public static void setRecursively(Component c, Handler handler)
/*      */   {
/*  863 */     setRecursively0(c, handler);
/*  864 */     handler.postAction(c);
/*      */   }
/*      */ 
/*      */   private static void setRecursively0(Component c, Handler handler) {
/*  868 */     if (handler.condition(c)) {
/*  869 */       handler.action(c);
/*      */     }
/*      */ 
/*  872 */     if (((handler instanceof ConditionHandler)) && (((ConditionHandler)handler).stopCondition(c))) {
/*  873 */       return;
/*      */     }
/*      */ 
/*  876 */     Component[] children = null;
/*      */ 
/*  878 */     if ((c instanceof JMenu)) {
/*  879 */       children = ((JMenu)c).getMenuComponents();
/*      */     }
/*  881 */     else if ((c instanceof JTabbedPane)) {
/*  882 */       JTabbedPane tabbedPane = (JTabbedPane)c;
/*  883 */       children = new Component[tabbedPane.getTabCount()];
/*  884 */       for (int i = 0; i < children.length; i++) {
/*  885 */         children[i] = tabbedPane.getComponentAt(i);
/*      */       }
/*      */     }
/*  888 */     else if ((c instanceof Container)) {
/*  889 */       children = ((Container)c).getComponents();
/*      */     }
/*  891 */     if (children != null)
/*  892 */       for (Component child : children)
/*  893 */         setRecursively0(child, handler);
/*      */   }
/*      */ 
/*      */   public static Component getRecursively(Component c, GetHandler handler)
/*      */   {
/*  906 */     return getRecursively0(c, handler);
/*      */   }
/*      */ 
/*      */   private static Component getRecursively0(Component c, GetHandler handler) {
/*  910 */     if (handler.condition(c)) {
/*  911 */       return handler.action(c);
/*      */     }
/*      */ 
/*  914 */     Component[] children = null;
/*      */ 
/*  916 */     if ((c instanceof JMenu)) {
/*  917 */       children = ((JMenu)c).getMenuComponents();
/*      */     }
/*  919 */     else if ((c instanceof Container)) {
/*  920 */       children = ((Container)c).getComponents();
/*      */     }
/*      */ 
/*  923 */     if (children != null) {
/*  924 */       for (Component child : children) {
/*  925 */         Component result = getRecursively0(child, handler);
/*  926 */         if (result != null) {
/*  927 */           return result;
/*      */         }
/*      */       }
/*      */     }
/*  931 */     return null;
/*      */   }
/*      */ 
/*      */   public static void setEnabledRecursively(Component c, boolean enabled)
/*      */   {
/*  941 */     setRecursively(c, new Handler(enabled) {
/*      */       public boolean condition(Component c) {
/*  943 */         return true;
/*      */       }
/*      */ 
/*      */       public void action(Component c) {
/*  947 */         c.setEnabled(this.val$enabled);
/*      */       }
/*      */ 
/*      */       public void postAction(Component c)
/*      */       {
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   public static void putClientPropertyRecursively(Component c, String clientProperty, Object value)
/*      */   {
/*  963 */     setRecursively(c, new Handler(clientProperty, value) {
/*      */       public boolean condition(Component c) {
/*  965 */         return c instanceof JComponent;
/*      */       }
/*      */ 
/*      */       public void action(Component c) {
/*  969 */         ((JComponent)c).putClientProperty(this.val$clientProperty, this.val$value);
/*      */       }
/*      */ 
/*      */       public void postAction(Component c)
/*      */       {
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   public static void setRequestFocusEnabledRecursively(Component c, boolean enabled)
/*      */   {
/*  985 */     setRecursively(c, new Handler(enabled) {
/*      */       public boolean condition(Component c) {
/*  987 */         return true;
/*      */       }
/*      */ 
/*      */       public void action(Component c) {
/*  991 */         if ((c instanceof JComponent))
/*  992 */           ((JComponent)c).setRequestFocusEnabled(this.val$enabled);
/*      */       }
/*      */ 
/*      */       public void postAction(Component c)
/*      */       {
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   public static void setOpaqueRecursively(Component c, boolean opaque)
/*      */   {
/* 1022 */     setRecursively(c, new Handler(opaque) {
/*      */       public boolean condition(Component c) {
/* 1024 */         return (!(c instanceof JComboBox)) && (!(c instanceof JButton)) && (!(c instanceof JTextComponent)) && (!(c instanceof ListCellRenderer)) && (!(c instanceof TreeCellRenderer)) && (!(c instanceof TableCellRenderer)) && (!(c instanceof CellEditor));
/*      */       }
/*      */ 
/*      */       public void action(Component c)
/*      */       {
/* 1029 */         if ((c instanceof JComponent)) {
/* 1030 */           JComponent jc = (JComponent)c;
/* 1031 */           if (Boolean.TRUE.equals(jc.getClientProperty("setOpaqueRecursively.excluded"))) {
/* 1032 */             return;
/*      */           }
/*      */ 
/* 1035 */           Object clientProperty = jc.getClientProperty("setOpaqueRecursively.opaqueListener");
/* 1036 */           if (clientProperty != null) {
/* 1037 */             jc.removePropertyChangeListener("opaque", (PropertyChangeListener)clientProperty);
/* 1038 */             jc.putClientProperty("setOpaqueRecursively.opaqueListener", null);
/*      */           }
/* 1040 */           jc.setOpaque(this.val$opaque);
/* 1041 */           if (jc.getClientProperty("setOpaqueRecursively.opaqueListener") == null)
/* 1042 */             if (this.val$opaque) {
/* 1043 */               if (JideSwingUtilities._setOpaqueTrueListener == null)
/* 1044 */                 JideSwingUtilities.access$002(new PropertyChangeListener() {
/*      */                   public void propertyChange(PropertyChangeEvent evt) {
/* 1046 */                     if ((evt.getSource() instanceof JComponent)) {
/* 1047 */                       Component component = (Component)evt.getSource();
/* 1048 */                       component.removePropertyChangeListener("opaque", this);
/* 1049 */                       if ((component instanceof JComponent))
/* 1050 */                         ((JComponent)component).setOpaque(true);
/* 1051 */                       component.addPropertyChangeListener("opaque", this);
/*      */                     }
/*      */                   }
/*      */                 });
/* 1056 */               jc.addPropertyChangeListener("opaque", JideSwingUtilities._setOpaqueTrueListener);
/* 1057 */               jc.putClientProperty("setOpaqueRecursively.opaqueListener", JideSwingUtilities._setOpaqueTrueListener);
/*      */             }
/*      */             else {
/* 1060 */               if (JideSwingUtilities._setOpaqueFalseListener == null) {
/* 1061 */                 JideSwingUtilities.access$102(new PropertyChangeListener() {
/*      */                   public void propertyChange(PropertyChangeEvent evt) {
/* 1063 */                     if (((evt.getSource() instanceof JComponent)) && 
/* 1064 */                       ((evt.getSource() instanceof JComponent))) {
/* 1065 */                       Component component = (Component)evt.getSource();
/* 1066 */                       component.removePropertyChangeListener("opaque", this);
/* 1067 */                       if ((component instanceof JComponent))
/* 1068 */                         ((JComponent)component).setOpaque(false);
/* 1069 */                       component.addPropertyChangeListener("opaque", this);
/*      */                     }
/*      */                   }
/*      */                 });
/*      */               }
/* 1075 */               jc.addPropertyChangeListener("opaque", JideSwingUtilities._setOpaqueFalseListener);
/* 1076 */               jc.putClientProperty("setOpaqueRecursively.opaqueListener", JideSwingUtilities._setOpaqueFalseListener);
/*      */             }
/*      */         }
/*      */       }
/*      */ 
/*      */       public void postAction(Component c) {
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   public static Dimension getPreferredButtonSize(AbstractButton b, int textIconGap, boolean isHorizontal) {
/* 1088 */     if (b.getComponentCount() > 0) {
/* 1089 */       return null;
/*      */     }
/*      */ 
/* 1092 */     Icon icon = b.getIcon();
/* 1093 */     String text = b.getText();
/*      */ 
/* 1095 */     Font font = b.getFont();
/* 1096 */     FontMetrics fm = b.getFontMetrics(font);
/*      */ 
/* 1098 */     Rectangle iconR = new Rectangle();
/* 1099 */     Rectangle textR = new Rectangle();
/* 1100 */     Rectangle viewR = new Rectangle(32767, 32767);
/*      */ 
/* 1102 */     layoutCompoundLabel(b, fm, text, icon, isHorizontal, b.getVerticalAlignment(), b.getHorizontalAlignment(), b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewR, iconR, textR, text == null ? 0 : textIconGap);
/*      */ 
/* 1111 */     Rectangle r = iconR.union(textR);
/*      */ 
/* 1113 */     Insets insets = b.getInsets();
/* 1114 */     r.width += insets.left + insets.right;
/* 1115 */     r.height += insets.top + insets.bottom;
/*      */ 
/* 1117 */     return r.getSize();
/*      */   }
/*      */ 
/*      */   public static String layoutCompoundLabel(JComponent c, FontMetrics fm, String text, Icon icon, boolean isHorizontal, int verticalAlignment, int horizontalAlignment, int verticalTextPosition, int horizontalTextPosition, Rectangle viewR, Rectangle iconR, Rectangle textR, int textIconGap)
/*      */   {
/* 1154 */     boolean orientationIsLeftToRight = true;
/* 1155 */     int hAlign = horizontalAlignment;
/* 1156 */     int hTextPos = horizontalTextPosition;
/*      */ 
/* 1158 */     if ((c != null) && 
/* 1159 */       (!c.getComponentOrientation().isLeftToRight())) {
/* 1160 */       orientationIsLeftToRight = false;
/*      */     }
/*      */ 
/* 1166 */     switch (horizontalAlignment) {
/*      */     case 10:
/* 1168 */       hAlign = orientationIsLeftToRight ? 2 : 4;
/* 1169 */       break;
/*      */     case 11:
/* 1171 */       hAlign = orientationIsLeftToRight ? 4 : 2;
/*      */     }
/*      */ 
/* 1177 */     switch (horizontalTextPosition) {
/*      */     case 10:
/* 1179 */       hTextPos = orientationIsLeftToRight ? 2 : 4;
/* 1180 */       break;
/*      */     case 11:
/* 1182 */       hTextPos = orientationIsLeftToRight ? 4 : 2;
/*      */     }
/*      */ 
/* 1186 */     return layoutCompoundLabelImpl(c, fm, text, icon, isHorizontal, verticalAlignment, hAlign, verticalTextPosition, hTextPos, viewR, iconR, textR, textIconGap);
/*      */   }
/*      */ 
/*      */   public static String layoutCompoundLabel(FontMetrics fm, String text, Icon icon, boolean isHorizontal, int verticalAlignment, int horizontalAlignment, int verticalTextPosition, int horizontalTextPosition, Rectangle viewR, Rectangle iconR, Rectangle textR, int textIconGap)
/*      */   {
/* 1234 */     return layoutCompoundLabelImpl(null, fm, text, icon, isHorizontal, verticalAlignment, horizontalAlignment, verticalTextPosition, horizontalTextPosition, viewR, iconR, textR, textIconGap);
/*      */   }
/*      */ 
/*      */   private static String layoutCompoundLabelImpl(JComponent c, FontMetrics fm, String text, Icon icon, boolean isHorizontal, int verticalAlignment, int horizontalAlignment, int verticalTextPosition, int horizontalTextPosition, Rectangle viewR, Rectangle iconR, Rectangle textR, int textIconGap)
/*      */   {
/* 1266 */     if (isHorizontal) {
/* 1267 */       return layoutCompoundLabelImplHorizontal(c, fm, text, icon, verticalAlignment, horizontalAlignment, verticalTextPosition, horizontalTextPosition, viewR, iconR, textR, textIconGap);
/*      */     }
/*      */ 
/* 1280 */     return layoutCompoundLabelImplVertical(c, fm, text, icon, verticalAlignment, horizontalAlignment, verticalTextPosition, horizontalTextPosition, viewR, iconR, textR, textIconGap);
/*      */   }
/*      */ 
/*      */   private static String getMaxLengthWord(String text)
/*      */   {
/* 1297 */     if (text.indexOf(' ') == -1) {
/* 1298 */       return text;
/*      */     }
/*      */ 
/* 1301 */     int minDiff = text.length();
/* 1302 */     int minPos = -1;
/* 1303 */     int mid = text.length() / 2;
/*      */ 
/* 1305 */     int pos = -1;
/*      */     while (true) {
/* 1307 */       pos = text.indexOf(' ', pos + 1);
/* 1308 */       if (pos == -1) {
/*      */         break;
/*      */       }
/* 1311 */       int diff = Math.abs(pos - mid);
/* 1312 */       if (diff < minDiff) {
/* 1313 */         minDiff = diff;
/* 1314 */         minPos = pos;
/*      */       }
/*      */     }
/* 1317 */     return minPos >= mid ? text.substring(0, minPos) : text.substring(minPos + 1);
/*      */   }
/*      */ 
/*      */   private static String layoutCompoundLabelImplHorizontal(JComponent c, FontMetrics fm, String text, Icon icon, int verticalAlignment, int horizontalAlignment, int verticalTextPosition, int horizontalTextPosition, Rectangle viewR, Rectangle iconR, Rectangle textR, int textIconGap)
/*      */   {
/* 1336 */     if (icon != null) {
/* 1337 */       iconR.width = icon.getIconWidth();
/* 1338 */       iconR.height = icon.getIconHeight();
/*      */     }
/*      */     else {
/* 1341 */       iconR.width = (iconR.height = 0);
/*      */     }
/*      */ 
/* 1349 */     boolean textIsEmpty = (text == null) || (text.equals(""));
/*      */ 
/* 1351 */     View v = null;
/* 1352 */     if (textIsEmpty) {
/* 1353 */       textR.width = (textR.height = 0);
/* 1354 */       text = "";
/*      */     }
/*      */     else {
/* 1357 */       v = c != null ? (View)c.getClientProperty("html") : null;
/* 1358 */       if (v != null) {
/* 1359 */         textR.width = (int)v.getPreferredSpan(0);
/* 1360 */         textR.height = (int)v.getPreferredSpan(1);
/*      */       }
/*      */       else
/*      */       {
/* 1379 */         textR.width = SwingUtilities.computeStringWidth(fm, text);
/* 1380 */         textR.height = fm.getHeight();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1390 */     int gap = (textIsEmpty) || (icon == null) ? 0 : textIconGap;
/*      */ 
/* 1392 */     if (!textIsEmpty)
/*      */     {
/*      */       int availTextWidth;
/*      */       int availTextWidth;
/* 1401 */       if (horizontalTextPosition == 0) {
/* 1402 */         availTextWidth = viewR.width;
/*      */       }
/*      */       else {
/* 1405 */         availTextWidth = viewR.width - (iconR.width + gap);
/*      */       }
/*      */ 
/* 1409 */       if (textR.width > availTextWidth) {
/* 1410 */         if (v != null) {
/* 1411 */           textR.width = availTextWidth;
/*      */         }
/*      */         else {
/* 1414 */           String clipString = "...";
/* 1415 */           int totalWidth = SwingUtilities.computeStringWidth(fm, clipString);
/*      */ 
/* 1417 */           for (int nChars = 0; nChars < text.length(); nChars++) {
/* 1418 */             totalWidth += fm.charWidth(text.charAt(nChars));
/* 1419 */             if (totalWidth > availTextWidth) {
/*      */               break;
/*      */             }
/*      */           }
/* 1423 */           text = text.substring(0, nChars) + clipString;
/* 1424 */           textR.width = SwingUtilities.computeStringWidth(fm, text);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1433 */     if (verticalTextPosition == 1) {
/* 1434 */       if (horizontalTextPosition != 0) {
/* 1435 */         textR.y = 0;
/*      */       }
/*      */       else {
/* 1438 */         textR.y = (-(textR.height + gap));
/*      */       }
/*      */     }
/* 1441 */     else if (verticalTextPosition == 0) {
/* 1442 */       textR.y = ((iconR.height >> 1) - (textR.height >> 1));
/*      */     }
/* 1445 */     else if (horizontalTextPosition != 0) {
/* 1446 */       textR.y = (iconR.height - textR.height);
/*      */     }
/*      */     else {
/* 1449 */       textR.y = (iconR.height + gap);
/*      */     }
/*      */ 
/* 1453 */     if (horizontalTextPosition == 2) {
/* 1454 */       textR.x = (-(textR.width + gap));
/*      */     }
/* 1456 */     else if (horizontalTextPosition == 0) {
/* 1457 */       textR.x = ((iconR.width >> 1) - (textR.width >> 1));
/*      */     }
/*      */     else {
/* 1460 */       textR.x = (iconR.width + gap);
/*      */     }
/*      */ 
/* 1470 */     int labelR_x = Math.min(iconR.x, textR.x);
/* 1471 */     int labelR_width = Math.max(iconR.x + iconR.width, textR.x + textR.width) - labelR_x;
/*      */ 
/* 1473 */     int labelR_y = Math.min(iconR.y, textR.y);
/* 1474 */     int labelR_height = Math.max(iconR.y + iconR.height, textR.y + textR.height) - labelR_y;
/*      */     int dy;
/*      */     int dy;
/* 1479 */     if (verticalAlignment == 1) {
/* 1480 */       dy = viewR.y - labelR_y;
/*      */     }
/*      */     else
/*      */     {
/*      */       int dy;
/* 1482 */       if (verticalAlignment == 0) {
/* 1483 */         dy = viewR.y + (viewR.height >> 1) - (labelR_y + (labelR_height >> 1));
/*      */       }
/*      */       else
/* 1486 */         dy = viewR.y + viewR.height - (labelR_y + labelR_height);
/*      */     }
/*      */     int dx;
/*      */     int dx;
/* 1489 */     if (horizontalAlignment == 2) {
/* 1490 */       dx = viewR.x - labelR_x;
/*      */     }
/*      */     else
/*      */     {
/*      */       int dx;
/* 1492 */       if (horizontalAlignment == 4) {
/* 1493 */         dx = viewR.x + viewR.width - (labelR_x + labelR_width);
/*      */       }
/*      */       else {
/* 1496 */         dx = viewR.x + (viewR.width >> 1) - (labelR_x + (labelR_width >> 1));
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1503 */     textR.x += dx;
/* 1504 */     textR.y += dy;
/*      */ 
/* 1506 */     iconR.x += dx;
/* 1507 */     iconR.y += dy;
/*      */ 
/* 1509 */     return text;
/*      */   }
/*      */ 
/*      */   private static String layoutCompoundLabelImplVertical(JComponent c, FontMetrics fm, String text, Icon icon, int verticalAlignment, int horizontalAlignment, int verticalTextPosition, int horizontalTextPosition, Rectangle viewR, Rectangle iconR, Rectangle textR, int textIconGap)
/*      */   {
/* 1527 */     if (icon != null) {
/* 1528 */       iconR.width = icon.getIconWidth();
/* 1529 */       iconR.height = icon.getIconHeight();
/*      */     }
/*      */     else {
/* 1532 */       iconR.width = (iconR.height = 0);
/*      */     }
/*      */ 
/* 1540 */     boolean textIsEmpty = (text == null) || (text.equals(""));
/*      */ 
/* 1542 */     View v = null;
/* 1543 */     if (textIsEmpty) {
/* 1544 */       textR.width = (textR.height = 0);
/* 1545 */       text = "";
/*      */     }
/*      */     else {
/* 1548 */       v = c != null ? (View)c.getClientProperty("html") : null;
/* 1549 */       if (v != null) {
/* 1550 */         textR.height = (int)v.getPreferredSpan(0);
/* 1551 */         textR.width = (int)v.getPreferredSpan(1);
/*      */       }
/*      */       else {
/* 1554 */         textR.height = SwingUtilities.computeStringWidth(fm, text);
/* 1555 */         textR.width = fm.getHeight();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1564 */     int gap = (textIsEmpty) || (icon == null) ? 0 : textIconGap;
/*      */ 
/* 1566 */     if (!textIsEmpty)
/*      */     {
/*      */       int availTextHeight;
/*      */       int availTextHeight;
/* 1575 */       if (horizontalTextPosition == 0) {
/* 1576 */         availTextHeight = viewR.height;
/*      */       }
/*      */       else {
/* 1579 */         availTextHeight = viewR.height - (iconR.height + gap);
/*      */       }
/*      */ 
/* 1583 */       if (textR.height > availTextHeight) {
/* 1584 */         if (v != null) {
/* 1585 */           textR.height = availTextHeight;
/*      */         }
/*      */         else {
/* 1588 */           String clipString = "...";
/* 1589 */           int totalHeight = SwingUtilities.computeStringWidth(fm, clipString);
/*      */ 
/* 1591 */           for (int nChars = 0; nChars < text.length(); nChars++) {
/* 1592 */             totalHeight += fm.charWidth(text.charAt(nChars));
/* 1593 */             if (totalHeight > availTextHeight) {
/*      */               break;
/*      */             }
/*      */           }
/* 1597 */           text = text.substring(0, nChars) + clipString;
/* 1598 */           textR.height = SwingUtilities.computeStringWidth(fm, text);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1607 */     if (verticalTextPosition == 1) {
/* 1608 */       if (horizontalTextPosition != 0) {
/* 1609 */         textR.x = 0;
/*      */       }
/*      */       else {
/* 1612 */         textR.x = (-(textR.width + gap));
/*      */       }
/*      */     }
/* 1615 */     else if (verticalTextPosition == 0) {
/* 1616 */       textR.y = ((iconR.width >> 1) - (textR.width >> 1));
/*      */     }
/* 1619 */     else if (horizontalTextPosition != 0) {
/* 1620 */       textR.x = (iconR.width - textR.width);
/*      */     }
/*      */     else {
/* 1623 */       textR.x = (iconR.width + gap);
/*      */     }
/*      */ 
/* 1627 */     if (horizontalTextPosition == 2) {
/* 1628 */       textR.y = (-(textR.height + gap));
/*      */     }
/* 1630 */     else if (horizontalTextPosition == 0) {
/* 1631 */       textR.y = ((iconR.height >> 1) - (textR.height >> 1));
/*      */     }
/*      */     else {
/* 1634 */       textR.y = (iconR.height + gap);
/*      */     }
/*      */ 
/* 1644 */     int labelR_x = Math.min(iconR.y, textR.y);
/* 1645 */     int labelR_width = Math.max(iconR.y + iconR.height, textR.y + textR.height) - labelR_x;
/*      */ 
/* 1647 */     int labelR_y = Math.min(iconR.x, textR.x);
/* 1648 */     int labelR_height = Math.max(iconR.x + iconR.width, textR.x + textR.width) - labelR_y;
/*      */     int dIcony;
/*      */     int dy;
/*      */     int dIcony;
/* 1655 */     if (verticalAlignment == 1) {
/* 1656 */       int dy = viewR.x - labelR_y;
/* 1657 */       dIcony = viewR.x + viewR.width - (labelR_y + labelR_height);
/*      */     }
/*      */     else
/*      */     {
/*      */       int dIcony;
/* 1659 */       if (verticalAlignment == 0) {
/* 1660 */         int dy = viewR.x + (viewR.width >> 1) - (labelR_y + (labelR_height >> 1));
/* 1661 */         dIcony = dy;
/*      */       }
/*      */       else {
/* 1664 */         dy = viewR.x + viewR.width - (labelR_y + labelR_height);
/* 1665 */         dIcony = viewR.x - labelR_y;
/*      */       }
/*      */     }
/*      */     int dx;
/*      */     int dx;
/* 1668 */     if (horizontalAlignment == 2) {
/* 1669 */       dx = viewR.y - labelR_x;
/*      */     }
/*      */     else
/*      */     {
/*      */       int dx;
/* 1671 */       if (horizontalAlignment == 4) {
/* 1672 */         dx = viewR.y + viewR.height - (labelR_x + labelR_width);
/*      */       }
/*      */       else {
/* 1675 */         dx = viewR.y + (viewR.height >> 1) - (labelR_x + (labelR_width >> 1));
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1682 */     textR.y += dx;
/* 1683 */     textR.x += dy;
/*      */ 
/* 1685 */     iconR.y += dx;
/* 1686 */     iconR.x += dIcony;
/*      */ 
/* 1688 */     return text;
/*      */   }
/*      */ 
/*      */   public static int getOrientationOf(Component component) {
/* 1692 */     if ((component instanceof Alignable)) {
/* 1693 */       return ((Alignable)component).getOrientation();
/*      */     }
/* 1695 */     if ((component instanceof JComponent)) {
/* 1696 */       Integer value = (Integer)((JComponent)component).getClientProperty("orientation");
/* 1697 */       if (value != null)
/* 1698 */         return value.intValue();
/*      */     }
/* 1700 */     return 0;
/*      */   }
/*      */ 
/*      */   public static void setOrientationOf(Component component, int orientation) {
/* 1704 */     int old = getOrientationOf(component);
/* 1705 */     if (orientation != old)
/* 1706 */       if ((component instanceof Alignable)) {
/* 1707 */         ((Alignable)component).setOrientation(orientation);
/*      */       }
/* 1709 */       else if ((component instanceof JComponent))
/* 1710 */         ((JComponent)component).putClientProperty("orientation", Integer.valueOf(orientation));
/*      */   }
/*      */ 
/*      */   public static void setChildrenOrientationOf(Container c, int orientation)
/*      */   {
/* 1716 */     Component[] components = c.getComponents();
/* 1717 */     for (Component component : components)
/* 1718 */       setOrientationOf(component, orientation);
/*      */   }
/*      */ 
/*      */   public static Map<Component, Boolean> disableDoubleBuffered(Component c)
/*      */   {
/* 1731 */     Map map = new HashMap();
/* 1732 */     if ((c instanceof JComponent))
/* 1733 */       setRecursively(c, new Handler(map) {
/*      */         public boolean condition(Component c) {
/* 1735 */           return ((c instanceof JComponent)) && (c.isDoubleBuffered());
/*      */         }
/*      */ 
/*      */         public void action(Component c) {
/* 1739 */           this.val$map.put(c, Boolean.TRUE);
/* 1740 */           ((JComponent)c).setDoubleBuffered(false);
/*      */         }
/*      */ 
/*      */         public void postAction(Component c)
/*      */         {
/*      */         }
/*      */       });
/* 1748 */     return map;
/*      */   }
/*      */ 
/*      */   public static Map<Component, Boolean> enableDoubleBuffered(Component c)
/*      */   {
/* 1760 */     Map map = new HashMap();
/* 1761 */     if ((c instanceof JComponent))
/* 1762 */       setRecursively(c, new Handler(map) {
/*      */         public boolean condition(Component c) {
/* 1764 */           return ((c instanceof JComponent)) && (!c.isDoubleBuffered());
/*      */         }
/*      */ 
/*      */         public void action(Component c) {
/* 1768 */           this.val$map.put(c, Boolean.FALSE);
/* 1769 */           ((JComponent)c).setDoubleBuffered(true);
/*      */         }
/*      */ 
/*      */         public void postAction(Component c)
/*      */         {
/*      */         }
/*      */       });
/* 1777 */     return map;
/*      */   }
/*      */ 
/*      */   public static void restoreDoubleBuffered(Component c, Map<Component, Boolean> map)
/*      */   {
/* 1789 */     setRecursively(c, new Handler(map) {
/*      */       public boolean condition(Component c) {
/* 1791 */         return c instanceof JComponent;
/*      */       }
/*      */ 
/*      */       public void action(Component c) {
/* 1795 */         Boolean value = (Boolean)this.val$map.get(c);
/* 1796 */         if (value != null)
/* 1797 */           ((JComponent)c).setDoubleBuffered(Boolean.TRUE.equals(value));
/*      */       }
/*      */ 
/*      */       public void postAction(Component c) {
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   public static void paintBackground(Graphics g, Rectangle rect, Color border, Color bk) {
/* 1807 */     Color old = g.getColor();
/* 1808 */     g.setColor(bk);
/* 1809 */     g.fillRect(rect.x + 1, rect.y + 1, rect.width - 2, rect.height - 2);
/* 1810 */     g.setColor(border);
/* 1811 */     g.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
/* 1812 */     g.setColor(old);
/*      */   }
/*      */ 
/*      */   public static void paintBackground(Graphics2D g2d, Rectangle rect, Color border, Paint paint) {
/* 1816 */     Color old = g2d.getColor();
/* 1817 */     g2d.setPaint(paint);
/* 1818 */     g2d.fillRect(rect.x + 1, rect.y + 1, rect.width - 2, rect.height - 2);
/* 1819 */     g2d.setColor(border);
/* 1820 */     g2d.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
/* 1821 */     g2d.setColor(old);
/*      */   }
/*      */ 
/*      */   private static boolean drawTextAntialiased(Component c)
/*      */   {
/* 1831 */     if (!AA_TEXT_DEFINED) {
/* 1832 */       if (c != null)
/*      */       {
/* 1834 */         if ((c instanceof JComponent)) {
/* 1835 */           Boolean aaProperty = (Boolean)((JComponent)c).getClientProperty(AA_TEXT_PROPERTY_KEY);
/* 1836 */           return aaProperty != null ? aaProperty.booleanValue() : false;
/*      */         }
/*      */ 
/* 1839 */         return false;
/*      */       }
/*      */ 
/* 1843 */       return false;
/*      */     }
/*      */ 
/* 1846 */     return AA_TEXT;
/*      */   }
/*      */ 
/*      */   public static boolean drawTextAntialiased(boolean aaText)
/*      */   {
/* 1856 */     if (!AA_TEXT_DEFINED)
/*      */     {
/* 1858 */       return aaText;
/*      */     }
/*      */ 
/* 1861 */     return AA_TEXT;
/*      */   }
/*      */ 
/*      */   public static void drawStringUnderlineCharAt(JComponent c, Graphics g, String text, int underlinedIndex, int x, int y)
/*      */   {
/* 1866 */     drawString(c, g, text, x, y);
/*      */ 
/* 1868 */     if ((underlinedIndex >= 0) && (underlinedIndex < text.length())) {
/* 1869 */       FontMetrics fm = g.getFontMetrics();
/* 1870 */       int underlineRectX = x + fm.stringWidth(text.substring(0, underlinedIndex));
/* 1871 */       int underlineRectY = y;
/* 1872 */       int underlineRectWidth = fm.charWidth(text.charAt(underlinedIndex));
/* 1873 */       int underlineRectHeight = 1;
/* 1874 */       g.fillRect(underlineRectX, underlineRectY + fm.getDescent() - 1, underlineRectWidth, underlineRectHeight);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static RenderingHints getRenderingHints(Graphics2D g2d, RenderingHints hintsToSave, RenderingHints savedHints)
/*      */   {
/* 1903 */     if (savedHints == null) {
/* 1904 */       savedHints = new RenderingHints(null);
/*      */     }
/*      */     else {
/* 1907 */       savedHints.clear();
/*      */     }
/* 1909 */     if ((hintsToSave == null) || (hintsToSave.size() == 0)) {
/* 1910 */       return savedHints;
/*      */     }
/*      */ 
/* 1913 */     Set objects = hintsToSave.keySet();
/* 1914 */     for (Iterator i$ = objects.iterator(); i$.hasNext(); ) { Object o = i$.next();
/* 1915 */       RenderingHints.Key key = (RenderingHints.Key)o;
/* 1916 */       Object value = g2d.getRenderingHint(key);
/* 1917 */       savedHints.put(key, value);
/*      */     }
/*      */ 
/* 1920 */     return savedHints;
/*      */   }
/*      */ 
/*      */   public static void drawString(JComponent c, Graphics g, String text, int x, int y) {
/* 1924 */     if (SystemInfo.isJdk6Above()) {
/* 1925 */       Graphics2D g2d = (Graphics2D)g;
/* 1926 */       RenderingHints oldHints = null;
/* 1927 */       if (renderingHints != null) {
/* 1928 */         oldHints = getRenderingHints(g2d, renderingHints, null);
/* 1929 */         g2d.addRenderingHints(renderingHints);
/*      */       }
/* 1931 */       g2d.drawString(text, x, y);
/* 1932 */       if (oldHints != null) {
/* 1933 */         g2d.addRenderingHints(oldHints);
/*      */       }
/*      */ 
/*      */     }
/* 1938 */     else if ((drawTextAntialiased(c)) && ((g instanceof Graphics2D))) {
/* 1939 */       Graphics2D g2 = (Graphics2D)g;
/* 1940 */       Object oldAAValue = g2.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
/* 1941 */       g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 1942 */       g2.drawString(text, x, y);
/* 1943 */       g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, oldAAValue);
/*      */     }
/*      */     else {
/* 1946 */       g.drawString(text, x, y);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static Object setupAntialiasing(Component c, Graphics g)
/*      */   {
/* 1964 */     Graphics2D g2d = (Graphics2D)g;
/*      */     Object oldHints;
/* 1966 */     if (SystemInfo.isJdk6Above()) {
/* 1967 */       Object oldHints = getRenderingHints(g2d, renderingHints, null);
/* 1968 */       if (renderingHints != null)
/* 1969 */         g2d.addRenderingHints(renderingHints);
/*      */     }
/*      */     else
/*      */     {
/* 1973 */       oldHints = g2d.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
/* 1974 */       if (drawTextAntialiased(c)) {
/* 1975 */         g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*      */       }
/*      */     }
/* 1978 */     return oldHints;
/*      */   }
/*      */ 
/*      */   public static void restoreAntialiasing(Component c, Graphics g, Object oldHints)
/*      */   {
/* 1989 */     Graphics2D g2d = (Graphics2D)g;
/* 1990 */     if (SystemInfo.isJdk6Above()) {
/* 1991 */       if (oldHints != null) {
/* 1992 */         g2d.addRenderingHints((RenderingHints)oldHints);
/*      */       }
/*      */     }
/*      */     else
/* 1996 */       g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, oldHints);
/*      */   }
/*      */ 
/*      */   public static Object setupShapeAntialiasing(Graphics g)
/*      */   {
/* 2008 */     Graphics2D g2d = (Graphics2D)g;
/* 2009 */     Object oldHints = g2d.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
/* 2010 */     g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/* 2011 */     return oldHints;
/*      */   }
/*      */ 
/*      */   public static void restoreShapeAntialiasing(Graphics g, Object oldHints)
/*      */   {
/* 2021 */     Graphics2D g2d = (Graphics2D)g;
/* 2022 */     g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldHints);
/*      */   }
/*      */ 
/*      */   public static void drawGrip(Graphics g, Rectangle rectangle, int maxLength, int maxThickness) {
/* 2026 */     drawGrip(g, rectangle, maxLength, maxThickness, true);
/*      */   }
/*      */ 
/*      */   public static void drawGrip(Graphics g, Rectangle rectangle, int maxLength, int maxThickness, boolean isSelected) {
/* 2030 */     if (rectangle.width > rectangle.height) {
/* 2031 */       int count = maxLength;
/* 2032 */       if (maxLength * 3 > rectangle.width) {
/* 2033 */         count = rectangle.width / 3;
/*      */       }
/* 2035 */       int startX = rectangle.x + (rectangle.width - count * 3 >> 1);
/* 2036 */       int startY = rectangle.y + (rectangle.height - maxThickness * 3 >> 1);
/* 2037 */       for (int i = 0; i < maxThickness; i++)
/* 2038 */         for (int j = 0; j < count; j++) {
/* 2039 */           if (isSelected) {
/* 2040 */             g.setColor(UIDefaultsLookup.getColor("controlLtHighlight"));
/* 2041 */             g.drawLine(startX + j * 3, startY + i * 3, startX + j * 3, startY + i * 3);
/*      */           }
/* 2043 */           g.setColor(UIDefaultsLookup.getColor("controlShadow"));
/* 2044 */           g.drawLine(startX + j * 3 + 1, startY + i * 3 + 1, startX + j * 3 + 1, startY + i * 3 + 1);
/*      */         }
/*      */     }
/*      */     else
/*      */     {
/* 2049 */       int count = maxLength;
/* 2050 */       if (maxLength * 3 > rectangle.height) {
/* 2051 */         count = rectangle.height / 3;
/*      */       }
/* 2053 */       int startX = rectangle.x + (rectangle.width - maxThickness * 3 >> 1);
/* 2054 */       int startY = rectangle.y + (rectangle.height - count * 3 >> 1);
/* 2055 */       for (int i = 0; i < maxThickness; i++)
/* 2056 */         for (int j = 0; j < count; j++) {
/* 2057 */           if (isSelected) {
/* 2058 */             g.setColor(UIDefaultsLookup.getColor("controlLtHighlight"));
/* 2059 */             g.drawLine(startX + i * 3, startY + j * 3, startX + i * 3, startY + j * 3);
/*      */           }
/* 2061 */           g.setColor(UIDefaultsLookup.getColor("controlShadow"));
/* 2062 */           g.drawLine(startX + i * 3 + 1, startY + j * 3 + 1, startX + i * 3 + 1, startY + j * 3 + 1);
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void registerTabKey(Container container)
/*      */   {
/* 2074 */     if ((container instanceof JComponent)) {
/* 2075 */       ((JComponent)container).registerKeyboardAction(new AbstractAction()
/*      */       {
/*      */         public void actionPerformed(ActionEvent e)
/*      */         {
/* 2079 */           DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
/*      */         }
/*      */       }
/*      */       , KeyStroke.getKeyStroke(9, 0), 0);
/*      */     }
/*      */     else
/*      */     {
/* 2084 */       for (int i = 0; i < container.getComponentCount(); i++) {
/* 2085 */         Component c = container.getComponent(i);
/*      */ 
/* 2088 */         if (((c instanceof JComponent)) && (c.isFocusable()))
/* 2089 */           ((JComponent)container).registerKeyboardAction(new AbstractAction()
/*      */           {
/*      */             public void actionPerformed(ActionEvent e)
/*      */             {
/* 2093 */               DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
/*      */             }
/*      */           }
/*      */           , KeyStroke.getKeyStroke(9, 0), 0);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void fillGradient(Graphics g, Rectangle rect, int orientation)
/*      */   {
/* 2102 */     Graphics2D g2d = (Graphics2D)g;
/*      */ 
/* 2104 */     Color col1 = new Color(255, 255, 255, 0);
/* 2105 */     Color col2 = new Color(255, 255, 255, 48);
/* 2106 */     Color col3 = new Color(0, 0, 0, 0);
/* 2107 */     Color col4 = new Color(0, 0, 0, 32);
/*      */ 
/* 2109 */     if (orientation == 0)
/*      */     {
/* 2111 */       fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height >> 1), col2, col1, true);
/*      */ 
/* 2114 */       fillGradient(g2d, new Rectangle(rect.x, rect.y + (rect.height >> 1), rect.width, rect.height >> 1), col3, col4, true);
/*      */     }
/*      */     else
/*      */     {
/* 2118 */       fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width >> 1, rect.height), col2, col1, false);
/*      */ 
/* 2121 */       fillGradient(g2d, new Rectangle(rect.x + (rect.width >> 1), rect.y, rect.width >> 1, rect.height), col3, col4, false);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void fillSingleGradient(Graphics g, Rectangle rect, int orientation) {
/* 2126 */     fillSingleGradient(g, rect, orientation, 127);
/*      */   }
/*      */ 
/*      */   public static void fillSingleGradient(Graphics g, Rectangle rect, int orientation, int level) {
/* 2130 */     Graphics2D g2d = (Graphics2D)g;
/* 2131 */     Color col1 = new Color(255, 255, 255, 0);
/* 2132 */     Color col2 = new Color(255, 255, 255, level);
/*      */ 
/* 2134 */     if (orientation == 5) {
/* 2135 */       fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), col2, col1, true);
/*      */     }
/* 2137 */     else if (orientation == 1) {
/* 2138 */       fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), col1, col2, true);
/*      */     }
/* 2140 */     else if (orientation == 3) {
/* 2141 */       fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), col2, col1, false);
/*      */     }
/* 2143 */     else if (orientation == 7)
/* 2144 */       fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), col1, col2, false);
/*      */   }
/*      */ 
/*      */   public static Paint getRadialGradientPaint(Point2D point, float radius, float[] fractions, Color[] colors)
/*      */   {
/* 2158 */     Class radialGradientPaintClass = null;
/*      */     try {
/* 2160 */       if (SystemInfo.isJdk6Above()) {
/* 2161 */         radialGradientPaintClass = Class.forName("java.awt.RadialGradientPaint");
/*      */       }
/*      */       else {
/* 2164 */         radialGradientPaintClass = Class.forName("org.apache.batik.ext.awt.RadialGradientPaint");
/*      */       }
/*      */     }
/*      */     catch (ClassNotFoundException e1)
/*      */     {
/*      */     }
/* 2170 */     if (radialGradientPaintClass != null) {
/*      */       try {
/* 2172 */         if (_radialGradientPaintConstructor2 == null) {
/* 2173 */           _radialGradientPaintConstructor2 = radialGradientPaintClass.getConstructor(new Class[] { Point2D.class, Float.TYPE, [F.class, [Ljava.awt.Color.class });
/*      */         }
/* 2175 */         Object radialGradientPaint = _radialGradientPaintConstructor2.newInstance(new Object[] { point, Float.valueOf(radius), fractions, colors });
/* 2176 */         return (Paint)radialGradientPaint;
/*      */       }
/*      */       catch (NoSuchMethodException e)
/*      */       {
/*      */       }
/*      */       catch (InstantiationException e)
/*      */       {
/*      */       }
/*      */       catch (IllegalAccessException e)
/*      */       {
/*      */       }
/*      */       catch (InvocationTargetException e)
/*      */       {
/*      */       }
/*      */     }
/*      */ 
/* 2192 */     System.err.println("Warning - radial gradients are only supported in Java 6 and higher or use batik-aw-util.jar, using a plain color instead");
/* 2193 */     return colors[0];
/*      */   }
/*      */ 
/*      */   public static Paint getRadialGradientPaint(float cx, float cy, float radius, float[] fractions, Color[] colors)
/*      */   {
/* 2202 */     if (_radialGradientPaintClass == null) {
/*      */       try {
/* 2204 */         if (SystemInfo.isJdk6Above()) {
/* 2205 */           _radialGradientPaintClass = Class.forName("java.awt.RadialGradientPaint");
/*      */         }
/*      */         else {
/* 2208 */           _radialGradientPaintClass = Class.forName("org.apache.batik.ext.awt.RadialGradientPaint");
/*      */         }
/*      */       }
/*      */       catch (ClassNotFoundException e1)
/*      */       {
/*      */       }
/*      */     }
/* 2215 */     if (_radialGradientPaintClass != null) {
/*      */       try {
/* 2217 */         if (_radialGradientPaintConstructor1 == null) {
/* 2218 */           _radialGradientPaintConstructor1 = _radialGradientPaintClass.getConstructor(new Class[] { Float.TYPE, Float.TYPE, Float.TYPE, [F.class, [Ljava.awt.Color.class });
/*      */         }
/* 2220 */         Object radialGradientPaint = _radialGradientPaintConstructor1.newInstance(new Object[] { Float.valueOf(cx), Float.valueOf(cy), Float.valueOf(radius), fractions, colors });
/* 2221 */         return (Paint)radialGradientPaint;
/*      */       }
/*      */       catch (NoSuchMethodException e)
/*      */       {
/*      */       }
/*      */       catch (InstantiationException e)
/*      */       {
/*      */       }
/*      */       catch (IllegalAccessException e)
/*      */       {
/*      */       }
/*      */       catch (InvocationTargetException e)
/*      */       {
/*      */       }
/*      */     }
/*      */ 
/* 2237 */     System.err.println("Warning - radial gradients are only supported in Java 6 and higher or use batik-aw-util.jar, using a plain color instead");
/* 2238 */     return colors[0];
/*      */   }
/*      */ 
/*      */   public static Paint getLinearGradientPaint(float startX, float startY, float endX, float endY, float[] fractions, Color[] colors)
/*      */   {
/* 2251 */     if (_linearGradientPaintClass == null) {
/*      */       try {
/* 2253 */         if (SystemInfo.isJdk6Above()) {
/* 2254 */           _linearGradientPaintClass = Class.forName("java.awt.LinearGradientPaint");
/*      */         }
/*      */         else {
/* 2257 */           _linearGradientPaintClass = Class.forName("org.apache.batik.ext.awt.LinearGradientPaint");
/*      */         }
/*      */       }
/*      */       catch (ClassNotFoundException e1)
/*      */       {
/*      */       }
/*      */     }
/* 2264 */     if (_linearGradientPaintClass != null) {
/*      */       try {
/* 2266 */         if (_linearGradientPaintConstructor1 == null) {
/* 2267 */           _linearGradientPaintConstructor1 = _linearGradientPaintClass.getConstructor(new Class[] { Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, [F.class, [Ljava.awt.Color.class });
/*      */         }
/* 2269 */         Object linearGradientPaint = _linearGradientPaintConstructor1.newInstance(new Object[] { Float.valueOf(startX), Float.valueOf(startY), Float.valueOf(endX), Float.valueOf(endY), fractions, colors });
/* 2270 */         return (Paint)linearGradientPaint;
/*      */       }
/*      */       catch (NoSuchMethodException e)
/*      */       {
/*      */       }
/*      */       catch (InstantiationException e)
/*      */       {
/*      */       }
/*      */       catch (IllegalAccessException e)
/*      */       {
/*      */       }
/*      */       catch (InvocationTargetException e)
/*      */       {
/*      */       }
/*      */     }
/*      */ 
/* 2286 */     System.err.println("Warning - linear gradients are only supported in Java 6 and higher or use batik-aw-util.jar, using a plain color instead");
/* 2287 */     return colors[0];
/*      */   }
/*      */ 
/*      */   public static boolean containerContainsFocus(Container cont)
/*      */   {
/* 2298 */     Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
/*      */ 
/* 2300 */     Component permFocusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getPermanentFocusOwner();
/*      */ 
/* 2303 */     boolean focusOwned = (focusOwner != null) && (SwingUtilities.isDescendingFrom(focusOwner, cont));
/* 2304 */     if (!focusOwned) {
/* 2305 */       focusOwned = (permFocusOwner != null) && (SwingUtilities.isDescendingFrom(permFocusOwner, cont));
/*      */     }
/*      */ 
/* 2308 */     return focusOwned;
/*      */   }
/*      */ 
/*      */   public static boolean componentIsPermanentFocusOwner(Component comp)
/*      */   {
/* 2314 */     return (comp != null) && (KeyboardFocusManager.getCurrentKeyboardFocusManager().getPermanentFocusOwner() == comp);
/*      */   }
/*      */ 
/*      */   public static void installColorsAndFont(Component c, Color background, Color foreground, Font font)
/*      */   {
/* 2324 */     installFont(c, font);
/* 2325 */     installColors(c, background, foreground);
/*      */   }
/*      */ 
/*      */   public static void installFont(Component c, Font font) {
/* 2329 */     Font f = c.getFont();
/* 2330 */     if ((f == null) || ((f instanceof UIResource)))
/* 2331 */       c.setFont(font);
/*      */   }
/*      */ 
/*      */   public static void installColors(Component c, Color background, Color foreground)
/*      */   {
/* 2337 */     Color bg = c.getBackground();
/* 2338 */     if ((background != null) && ((bg == null) || ((bg instanceof UIResource)))) {
/* 2339 */       c.setBackground(background);
/*      */     }
/*      */ 
/* 2342 */     Color fg = c.getForeground();
/* 2343 */     if ((foreground != null) && ((fg == null) || ((fg instanceof UIResource))))
/* 2344 */       c.setForeground(foreground);
/*      */   }
/*      */ 
/*      */   public static void installBorder(JComponent c, Border defaultBorder)
/*      */   {
/* 2349 */     Border border = c.getBorder();
/* 2350 */     if ((border == null) || ((border instanceof UIResource)))
/* 2351 */       c.setBorder(defaultBorder);
/*      */   }
/*      */ 
/*      */   public static void fillNormalGradient(Graphics2D g2d, Shape s, Color startColor, Color endColor, boolean isVertical)
/*      */   {
/* 2356 */     Rectangle rect = s.getBounds();
/*      */     GradientPaint paint;
/*      */     GradientPaint paint;
/* 2358 */     if (isVertical) {
/* 2359 */       paint = new GradientPaint(rect.x, rect.y, startColor, rect.x, rect.height + rect.y, endColor, true);
/*      */     }
/*      */     else {
/* 2362 */       paint = new GradientPaint(rect.x, rect.y, startColor, rect.width + rect.x, rect.y, endColor, true);
/*      */     }
/* 2364 */     Paint old = g2d.getPaint();
/* 2365 */     g2d.setPaint(paint);
/* 2366 */     g2d.fill(s);
/* 2367 */     g2d.setPaint(old);
/*      */   }
/*      */ 
/*      */   public static void fillGradient(Graphics2D g2d, Shape s, Color startColor, Color endColor, boolean isVertical)
/*      */   {
/* 2384 */     if ("true".equals(SecurityUtils.getProperty("normalGradientPaint", "false"))) {
/* 2385 */       fillNormalGradient(g2d, s, startColor, endColor, isVertical);
/*      */     }
/*      */     else
/* 2388 */       FastGradientPainter.drawGradient(g2d, s, startColor, endColor, isVertical);
/*      */   }
/*      */ 
/*      */   public static void clearGradientCache()
/*      */   {
/* 2396 */     FastGradientPainter.clearGradientCache();
/*      */   }
/*      */ 
/*      */   public static Window getTopModalDialog(Window w)
/*      */   {
/* 2406 */     Window[] ws = w.getOwnedWindows();
/* 2407 */     for (Window w1 : ws) {
/* 2408 */       if ((w1.isVisible()) && ((w1 instanceof Dialog)) && (((Dialog)w1).isModal())) {
/* 2409 */         return getTopModalDialog(w1);
/*      */       }
/*      */     }
/* 2412 */     return w;
/*      */   }
/*      */ 
/*      */   public static void traceFocus()
/*      */   {
/* 2421 */     traceFocus(false);
/*      */   }
/*      */ 
/*      */   public static void traceFocus(boolean useBorders)
/*      */   {
/* 2428 */     if (tracingFocus)
/* 2429 */       return;
/* 2430 */     PropertyChangeListener listener = new PropertyChangeListener(useBorders) {
/*      */       public void propertyChange(PropertyChangeEvent evt) {
/* 2432 */         if (this.val$useBorders) {
/* 2433 */           Component oldValue = (Component)evt.getOldValue();
/* 2434 */           if ((oldValue instanceof JComponent)) {
/* 2435 */             Border oldBorder = ((JComponent)oldValue).getBorder();
/* 2436 */             if ((oldBorder instanceof JideSwingUtilities.TraceDebugBorder)) {
/* 2437 */               ((JComponent)oldValue).setBorder(((JideSwingUtilities.TraceDebugBorder)oldBorder).getInsideBorder());
/*      */             }
/*      */           }
/* 2440 */           Component newValue = (Component)evt.getNewValue();
/* 2441 */           if ((newValue instanceof JComponent)) {
/* 2442 */             Border oldBorder = ((JComponent)newValue).getBorder();
/* 2443 */             if (oldBorder == null)
/* 2444 */               oldBorder = new EmptyBorder(0, 0, 0, 0);
/* 2445 */             if (!(oldBorder instanceof JideSwingUtilities.TraceDebugBorder)) {
/* 2446 */               ((JComponent)newValue).setBorder(new JideSwingUtilities.TraceDebugBorder(oldBorder));
/*      */             }
/*      */           }
/*      */         }
/* 2450 */         String oldName = evt.getOldValue() == null ? "null" : evt.getOldValue().getClass().getName();
/* 2451 */         if (((evt.getOldValue() instanceof Component)) && (((Component)evt.getOldValue()).getName() != null))
/* 2452 */           oldName = oldName + "'" + ((Component)evt.getOldValue()).getName() + "'";
/* 2453 */         String newName = evt.getNewValue() == null ? "null" : evt.getNewValue().getClass().getName();
/* 2454 */         if (((evt.getNewValue() instanceof Component)) && (((Component)evt.getNewValue()).getName() != null)) {
/* 2455 */           newName = newName + "'" + ((Component)evt.getNewValue()).getName() + "'";
/*      */         }
/* 2457 */         System.out.println(evt.getPropertyName() + ": " + oldName + " ==> " + newName);
/*      */       }
/*      */     };
/* 2460 */     DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener("focusOwner", listener);
/* 2461 */     DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener("permanentFocusOwner", listener);
/* 2462 */     DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener("activeWindow", listener);
/* 2463 */     tracingFocus = true;
/*      */   }
/*      */ 
/*      */   public static void runGCAndPrintFreeMemory()
/*      */   {
/* 2484 */     DecimalFormat memFormatter = new DecimalFormat("###,###,##0.####");
/* 2485 */     String memFree = memFormatter.format(Runtime.getRuntime().freeMemory() / 1024L);
/*      */ 
/* 2487 */     String memTotal = memFormatter.format(Runtime.getRuntime().totalMemory() / 1024L);
/*      */ 
/* 2489 */     String memUsed = memFormatter.format((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024L);
/*      */ 
/* 2492 */     System.out.println("before gc: (Total [" + memTotal + "k] - Free [" + memFree + "k]) = Used [" + memUsed + "k]");
/*      */ 
/* 2494 */     System.runFinalization();
/* 2495 */     System.gc();
/*      */     try
/*      */     {
/* 2498 */       Thread.sleep(100L);
/*      */     }
/*      */     catch (InterruptedException ie) {
/*      */     }
/* 2502 */     memFree = memFormatter.format(Runtime.getRuntime().freeMemory() / 1024L);
/* 2503 */     memTotal = memFormatter.format(Runtime.getRuntime().totalMemory() / 1024L);
/* 2504 */     memUsed = memFormatter.format((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024L);
/*      */ 
/* 2506 */     System.out.println("after gc: (Total [" + memTotal + "k] - Free [" + memFree + "k]) = Used [" + memUsed + "k]");
/*      */   }
/*      */ 
/*      */   public static JPanel createTableModelModifier(DefaultTableModel tableModel)
/*      */   {
/* 2514 */     JPanel tableModelPanel = new JPanel(new BorderLayout(6, 6));
/* 2515 */     JTable table = new JTable(tableModel);
/* 2516 */     tableModelPanel.add(new JScrollPane(table));
/* 2517 */     ButtonPanel buttonPanel = new ButtonPanel();
/*      */ 
/* 2519 */     JButton insert = new JButton("Insert");
/* 2520 */     insert.addActionListener(new AbstractAction(tableModel, table) {
/*      */       public void actionPerformed(ActionEvent e) {
/* 2522 */         Vector rowData = this.val$tableModel.getDataVector();
/* 2523 */         int index = this.val$table.getSelectedRow();
/* 2524 */         if (index != -1) {
/* 2525 */           Vector v = (Vector)rowData.get(index);
/* 2526 */           Vector clone = new Vector();
/* 2527 */           for (int i = 0; i < v.size(); i++) {
/* 2528 */             if (i == 0) {
/* 2529 */               clone.add(Integer.valueOf((int)(Math.random() * 10.0D)));
/*      */             }
/*      */             else {
/* 2532 */               clone.add("" + v.get(i));
/*      */             }
/*      */           }
/* 2535 */           this.val$tableModel.insertRow(index, clone);
/*      */         }
/*      */       }
/*      */     });
/* 2540 */     JButton delete = new JButton("Delete");
/* 2541 */     delete.addActionListener(new AbstractAction(table, tableModel) {
/*      */       public void actionPerformed(ActionEvent e) {
/* 2543 */         int[] rows = this.val$table.getSelectedRows();
/* 2544 */         for (int i = rows.length - 1; i >= 0; i--) {
/* 2545 */           int row = rows[i];
/* 2546 */           this.val$tableModel.removeRow(row);
/*      */         }
/*      */       }
/*      */     });
/* 2551 */     JButton clear = new JButton("Clear");
/* 2552 */     clear.addActionListener(new AbstractAction(tableModel) {
/*      */       public void actionPerformed(ActionEvent e) {
/* 2554 */         for (int i = 0; i < this.val$tableModel.getRowCount(); i++)
/* 2555 */           this.val$tableModel.removeRow(0);
/*      */       }
/*      */     });
/* 2560 */     buttonPanel.add(insert);
/* 2561 */     buttonPanel.add(delete);
/* 2562 */     buttonPanel.add(clear);
/* 2563 */     tableModelPanel.add(buttonPanel, "Last");
/* 2564 */     return tableModelPanel;
/*      */   }
/*      */ 
/*      */   public static Component findSomethingFocusable(Container container)
/*      */   {
/* 2578 */     if (passesFocusabilityTest(container)) {
/* 2579 */       container.requestFocusInWindow();
/* 2580 */       return container;
/*      */     }
/*      */ 
/* 2584 */     Component[] comps = container.getComponents();
/* 2585 */     for (Component comp1 : comps) {
/* 2586 */       if (passesFocusabilityTest(comp1)) {
/* 2587 */         container.requestFocusInWindow();
/* 2588 */         return container;
/*      */       }
/* 2590 */       if ((comp1 instanceof Container)) {
/* 2591 */         Component comp = findSomethingFocusable((Container)(Container)comp1);
/* 2592 */         if (comp != null) {
/* 2593 */           return comp;
/*      */         }
/*      */       }
/*      */     }
/* 2597 */     return null;
/*      */   }
/*      */ 
/*      */   public static boolean passesFocusabilityTest(Component comp)
/*      */   {
/* 2607 */     return (comp != null) && (comp.isEnabled()) && (comp.isDisplayable()) && (comp.isVisible()) && (comp.isFocusable()) && (comp.isShowing());
/*      */   }
/*      */ 
/*      */   public static void ignoreException(Exception e)
/*      */   {
/*      */   }
/*      */ 
/*      */   public static void printException(Exception e)
/*      */   {
/* 2628 */     System.err.println(e.getLocalizedMessage());
/*      */   }
/*      */ 
/*      */   public static void throwException(Exception e)
/*      */   {
/* 2638 */     if ((e instanceof RuntimeException)) {
/* 2639 */       throw ((RuntimeException)e);
/*      */     }
/*      */ 
/* 2642 */     throw new RuntimeException(e);
/*      */   }
/*      */ 
/*      */   public static void throwInvocationTargetException(InvocationTargetException e)
/*      */   {
/* 2656 */     if ((e.getTargetException() instanceof RuntimeException)) {
/* 2657 */       throw ((RuntimeException)e.getTargetException());
/*      */     }
/* 2659 */     if ((e.getTargetException() instanceof Error)) {
/* 2660 */       throw ((Error)e.getTargetException());
/*      */     }
/*      */ 
/* 2663 */     throw new RuntimeException(e.getTargetException());
/*      */   }
/*      */ 
/*      */   public static int findDisplayedMnemonicIndex(String text, int mnemonic)
/*      */   {
/* 2668 */     if ((text == null) || (mnemonic == 0)) {
/* 2669 */       return -1;
/*      */     }
/*      */ 
/* 2672 */     char uc = Character.toUpperCase((char)mnemonic);
/* 2673 */     char lc = Character.toLowerCase((char)mnemonic);
/*      */ 
/* 2675 */     int uci = text.indexOf(uc);
/* 2676 */     int lci = text.indexOf(lc);
/*      */ 
/* 2678 */     if (uci == -1) {
/* 2679 */       return lci;
/*      */     }
/* 2681 */     if (lci == -1) {
/* 2682 */       return uci;
/*      */     }
/*      */ 
/* 2685 */     return lci < uci ? lci : uci;
/*      */   }
/*      */ 
/*      */   public static Component getDescendantOfClass(Class c, Container container)
/*      */   {
/* 2698 */     if ((container == null) || (c == null)) {
/* 2699 */       return null;
/*      */     }
/* 2701 */     Component[] components = container.getComponents();
/*      */ 
/* 2703 */     for (Component component : components) {
/* 2704 */       if (c.isInstance(component)) {
/* 2705 */         return component;
/*      */       }
/* 2707 */       if ((component instanceof Container)) {
/* 2708 */         Component found = getDescendantOfClass(c, (Container)component);
/* 2709 */         if (found != null) {
/* 2710 */           return found;
/*      */         }
/*      */       }
/*      */     }
/* 2714 */     return null;
/*      */   }
/*      */ 
/*      */   public static float getDefaultFontSize()
/*      */   {
/* 2719 */     String fontSize = SecurityUtils.getProperty("jide.fontSize", null);
/* 2720 */     float defaultFontSize = -1.0F;
/*      */     try {
/* 2722 */       if (fontSize != null) {
/* 2723 */         defaultFontSize = Float.parseFloat(fontSize);
/*      */       }
/*      */     }
/*      */     catch (NumberFormatException e)
/*      */     {
/*      */     }
/*      */ 
/* 2730 */     return defaultFontSize;
/*      */   }
/*      */ 
/*      */   public static Object getMenuFont(Toolkit toolkit, UIDefaults table) {
/* 2734 */     Object menuFont = null;
/*      */ 
/* 2736 */     float defaultFontSize = getDefaultFontSize();
/*      */ 
/* 2738 */     if (shouldUseSystemFont()) {
/* 2739 */       if (defaultFontSize == -1.0F) {
/* 2740 */         menuFont = table.getFont("ToolBar.font");
/*      */       }
/*      */       else
/* 2743 */         menuFont = new WindowsDesktopProperty("win.menu.font", table.getFont("ToolBar.font"), toolkit, defaultFontSize);
/*      */     }
/*      */     else
/*      */     {
/* 2747 */       Font font = table.getFont("ToolBar.font");
/* 2748 */       if (font == null) {
/* 2749 */         menuFont = SecurityUtils.createFontUIResource("Tahoma", 0, defaultFontSize != -1.0F ? (int)defaultFontSize : 11);
/*      */       }
/*      */       else {
/* 2752 */         menuFont = SecurityUtils.createFontUIResource(font.getFontName(), 0, defaultFontSize != -1.0F ? (int)defaultFontSize : font.getSize());
/*      */       }
/*      */     }
/*      */ 
/* 2756 */     if (menuFont == null) {
/* 2757 */       return getControlFont(toolkit, table);
/*      */     }
/*      */ 
/* 2760 */     return menuFont;
/*      */   }
/*      */ 
/*      */   public static Object getControlFont(Toolkit toolkit, UIDefaults table)
/*      */   {
/* 2767 */     float defaultFontSize = getDefaultFontSize();
/*      */     Object controlFont;
/*      */     Object controlFont;
/* 2769 */     if (shouldUseSystemFont()) {
/* 2770 */       Font font = table.getFont("Label.font");
/* 2771 */       if (font == null)
/* 2772 */         font = new Font("Tahoma", 0, 12);
/*      */       Object controlFont;
/* 2774 */       if (defaultFontSize == -1.0F) {
/* 2775 */         controlFont = font;
/*      */       }
/*      */       else
/* 2778 */         controlFont = new WindowsDesktopProperty("win.defaultGUI.font", font, toolkit, defaultFontSize);
/*      */     }
/*      */     else
/*      */     {
/* 2782 */       Font font = table.getFont("Label.font");
/*      */       Object controlFont;
/* 2783 */       if (font == null) {
/* 2784 */         controlFont = SecurityUtils.createFontUIResource("Tahoma", 0, defaultFontSize != -1.0F ? (int)defaultFontSize : 11);
/*      */       }
/*      */       else {
/* 2787 */         controlFont = defaultFontSize == -1.0F ? font : new WindowsDesktopProperty("win.defaultGUI.font", font, toolkit, defaultFontSize);
/*      */       }
/*      */     }
/*      */ 
/* 2791 */     return controlFont;
/*      */   }
/*      */ 
/*      */   public static Object getBoldFont(Toolkit toolkit, UIDefaults table) {
/* 2795 */     if (SystemInfo.isCJKLocale()) {
/* 2796 */       return getControlFont(toolkit, table);
/*      */     }
/*      */ 
/* 2801 */     float defaultFontSize = getDefaultFontSize();
/*      */     Object boldFont;
/*      */     Object boldFont;
/* 2803 */     if (shouldUseSystemFont()) {
/* 2804 */       Font font = table.getFont("Label.font");
/* 2805 */       if (font == null)
/* 2806 */         font = new Font("Tahoma", 0, 12);
/*      */       Object boldFont;
/* 2808 */       if (defaultFontSize == -1.0F) {
/* 2809 */         boldFont = new FontUIResource(font.deriveFont(1));
/*      */       }
/*      */       else
/* 2812 */         boldFont = new WindowsDesktopProperty("win.defaultGUI.font", font, toolkit, defaultFontSize, 1);
/*      */     }
/*      */     else
/*      */     {
/* 2816 */       Font font = table.getFont("Label.font");
/*      */       Object boldFont;
/* 2817 */       if (font == null) {
/* 2818 */         boldFont = SecurityUtils.createFontUIResource("Tahoma", 1, defaultFontSize != -1.0F ? (int)defaultFontSize : 11);
/*      */       }
/*      */       else {
/* 2821 */         boldFont = SecurityUtils.createFontUIResource(font.getFontName(), 1, defaultFontSize != -1.0F ? (int)defaultFontSize : font.getSize());
/*      */       }
/*      */     }
/* 2824 */     return boldFont;
/*      */   }
/*      */ 
/*      */   public static void drawShadow(Graphics g, Component c, int x, int y, int w, int h)
/*      */   {
/* 2829 */     if ((w <= 0) || (h <= 0)) {
/* 2830 */       return;
/*      */     }
/* 2832 */     ShadowFactory factory = new ShadowFactory(6, 0.7F, Color.GRAY);
/* 2833 */     BufferedImage temp = new BufferedImage(w, h, 2);
/* 2834 */     Graphics2D g2 = temp.createGraphics();
/* 2835 */     g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/*      */ 
/* 2837 */     g2.setColor(Color.BLACK);
/* 2838 */     g2.fillRect(0, 0, temp.getWidth(), temp.getHeight());
/* 2839 */     g2.dispose();
/* 2840 */     BufferedImage shadow = factory.createShadow(temp);
/* 2841 */     g.drawImage(shadow, x, y, c);
/*      */   }
/*      */ 
/*      */   public static void drawImageBorder(Graphics g, ImageIcon img, Rectangle rect, Insets ins, boolean drawCenter)
/*      */   {
/* 2894 */     int left = ins.left;
/* 2895 */     int right = ins.right;
/* 2896 */     int top = ins.top;
/* 2897 */     int bottom = ins.bottom;
/* 2898 */     int x = rect.x;
/* 2899 */     int y = rect.y;
/* 2900 */     int w = rect.width;
/* 2901 */     int h = rect.height;
/*      */ 
/* 2904 */     g.drawImage(img.getImage(), x, y, x + left, y + top, 0, 0, left, top, null);
/*      */ 
/* 2906 */     g.drawImage(img.getImage(), x + left, y, x + w - right, y + top, left, 0, img.getIconWidth() - right, top, null);
/*      */ 
/* 2908 */     g.drawImage(img.getImage(), x + w - right, y, x + w, y + top, img.getIconWidth() - right, 0, img.getIconWidth(), top, null);
/*      */ 
/* 2912 */     g.drawImage(img.getImage(), x, y + top, x + left, y + h - bottom, 0, top, left, img.getIconHeight() - bottom, null);
/*      */ 
/* 2914 */     g.drawImage(img.getImage(), x + left, y + top, x + w - right, y + h - bottom, left, top, img.getIconWidth() - right, img.getIconHeight() - bottom, null);
/*      */ 
/* 2916 */     g.drawImage(img.getImage(), x + w - right, y + top, x + w, y + h - bottom, img.getIconWidth() - right, top, img.getIconWidth(), img.getIconHeight() - bottom, null);
/*      */ 
/* 2920 */     g.drawImage(img.getImage(), x, y + h - bottom, x + left, y + h, 0, img.getIconHeight() - bottom, left, img.getIconHeight(), null);
/*      */ 
/* 2922 */     g.drawImage(img.getImage(), x + left, y + h - bottom, x + w - right, y + h, left, img.getIconHeight() - bottom, img.getIconWidth() - right, img.getIconHeight(), null);
/*      */ 
/* 2924 */     g.drawImage(img.getImage(), x + w - right, y + h - bottom, x + w, y + h, img.getIconWidth() - right, img.getIconHeight() - bottom, img.getIconWidth(), img.getIconHeight(), null);
/*      */ 
/* 2927 */     if (drawCenter)
/* 2928 */       g.drawImage(img.getImage(), x + left, y + top, x + w - right, y + h - bottom, left, top, img.getIconWidth() - right, img.getIconHeight() - bottom, null);
/*      */   }
/*      */ 
/*      */   public static boolean compositeRequestFocus(Component component)
/*      */   {
/* 2940 */     LOGGER_FOCUS.fine("compositeRequestFocus " + component);
/* 2941 */     if ((component instanceof Container)) {
/* 2942 */       LOGGER_FOCUS.fine("compositeRequestFocus is container.");
/* 2943 */       Container container = (Container)component;
/* 2944 */       if (container.isFocusCycleRoot()) {
/* 2945 */         LOGGER_FOCUS.fine("compositeRequestFocus is focuscycleroot.");
/* 2946 */         FocusTraversalPolicy policy = container.getFocusTraversalPolicy();
/* 2947 */         Component comp = policy.getDefaultComponent(container);
/* 2948 */         LOGGER_FOCUS.fine("compositeRequestFocus default component = " + comp);
/*      */ 
/* 2950 */         if ((comp != null) && (comp.isShowing()) && (container.getComponentCount() > 0)) {
/* 2951 */           LOGGER_FOCUS.fine("compositeRequestFocus default component passesFocusabilityTest =" + passesFocusabilityTest(comp));
/* 2952 */           LOGGER_FOCUS.fine("compositeRequestFocus requestFocus for " + comp);
/* 2953 */           return comp.requestFocusInWindow();
/*      */         }
/*      */       }
/* 2956 */       Container rootAncestor = container.getFocusCycleRootAncestor();
/* 2957 */       if (rootAncestor != null) {
/* 2958 */         LOGGER_FOCUS.fine("compositeRequestFocus using rootAncestor =" + rootAncestor);
/* 2959 */         FocusTraversalPolicy policy = rootAncestor.getFocusTraversalPolicy();
/* 2960 */         Component comp = null;
/*      */         try {
/* 2962 */           comp = policy.getComponentAfter(rootAncestor, container);
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*      */         }
/*      */ 
/* 2969 */         LOGGER_FOCUS.fine("compositeRequestFocus getComponentAfter =" + comp);
/* 2970 */         if ((comp != null) && (SwingUtilities.isDescendingFrom(comp, container))) {
/* 2971 */           LOGGER_FOCUS.fine("compositeRequestFocus getComponentAfter passesFocusabilityTest =" + passesFocusabilityTest(comp));
/* 2972 */           LOGGER_FOCUS.fine("compositeRequestFocus requestFocus for " + comp);
/* 2973 */           return comp.requestFocusInWindow();
/*      */         }
/*      */       }
/*      */     }
/* 2977 */     if (!passesFocusabilityTest(component)) {
/* 2978 */       LOGGER_FOCUS.fine("compositeRequestFocus returingfalse because !passesFocusabilityTest" + component);
/* 2979 */       return false;
/*      */     }
/*      */ 
/* 2982 */     LOGGER_FOCUS.fine("compositeRequestFocus component=" + component);
/* 2983 */     return component.requestFocusInWindow();
/*      */   }
/*      */ 
/*      */   public static boolean isAncestorOfFocusOwner(Component component) {
/* 2987 */     boolean hasFocus = false;
/* 2988 */     Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
/* 2989 */     if ((component == focusOwner) || (((component instanceof Container)) && (((Container)component).isAncestorOf(focusOwner)))) {
/* 2990 */       hasFocus = true;
/*      */     }
/* 2992 */     return hasFocus;
/*      */   }
/*      */ 
/*      */   public static Window getWindowForComponent(Component parentComponent)
/*      */     throws HeadlessException
/*      */   {
/* 3003 */     if (parentComponent == null)
/* 3004 */       return JOptionPane.getRootFrame();
/* 3005 */     if (((parentComponent instanceof Frame)) || ((parentComponent instanceof Dialog)))
/* 3006 */       return (Window)parentComponent;
/* 3007 */     return getWindowForComponent(parentComponent.getParent());
/*      */   }
/*      */ 
/*      */   public static boolean isKeyListenerRegistered(Component component, KeyListener l)
/*      */   {
/* 3018 */     KeyListener[] listeners = component.getKeyListeners();
/* 3019 */     for (KeyListener listener : listeners) {
/* 3020 */       if (listener == l) {
/* 3021 */         return true;
/*      */       }
/*      */     }
/* 3024 */     return false;
/*      */   }
/*      */ 
/*      */   public static void insertKeyListener(Component component, KeyListener l, int index)
/*      */   {
/* 3035 */     KeyListener[] listeners = component.getKeyListeners();
/* 3036 */     for (KeyListener listener : listeners) {
/* 3037 */       component.removeKeyListener(listener);
/*      */     }
/* 3039 */     for (int i = 0; i < listeners.length; i++) {
/* 3040 */       KeyListener listener = listeners[i];
/* 3041 */       if (index == i) {
/* 3042 */         component.addKeyListener(l);
/*      */       }
/* 3044 */       component.addKeyListener(listener);
/*      */     }
/*      */ 
/* 3047 */     if (index > listeners.length - 1)
/* 3048 */       component.addKeyListener(l);
/*      */   }
/*      */ 
/*      */   public static void insertTableModelListener(TableModel model, TableModelListener l, int index)
/*      */   {
/* 3061 */     if (!(model instanceof AbstractTableModel)) {
/* 3062 */       model.addTableModelListener(l);
/* 3063 */       return;
/*      */     }
/* 3065 */     TableModelListener[] listeners = ((AbstractTableModel)model).getTableModelListeners();
/* 3066 */     for (TableModelListener listener : listeners) {
/* 3067 */       model.removeTableModelListener(listener);
/*      */     }
/* 3069 */     for (int i = 0; i < listeners.length; i++) {
/* 3070 */       TableModelListener listener = listeners[i];
/* 3071 */       if (index == i) {
/* 3072 */         model.addTableModelListener(l);
/*      */       }
/* 3074 */       model.addTableModelListener(listener);
/*      */     }
/*      */ 
/* 3077 */     if ((index < 0) || (index > listeners.length - 1))
/* 3078 */       model.addTableModelListener(l);
/*      */   }
/*      */ 
/*      */   public static boolean isPropertyChangeListenerRegistered(Component component, PropertyChangeListener l)
/*      */   {
/* 3090 */     PropertyChangeListener[] listeners = component.getPropertyChangeListeners();
/* 3091 */     for (PropertyChangeListener listener : listeners) {
/* 3092 */       if (listener == l) {
/* 3093 */         return true;
/*      */       }
/*      */     }
/* 3096 */     return false;
/*      */   }
/*      */ 
/*      */   public static boolean isMouseListenerRegistered(Component component, MouseListener l)
/*      */   {
/* 3107 */     MouseListener[] listeners = component.getMouseListeners();
/* 3108 */     for (MouseListener listener : listeners) {
/* 3109 */       if (listener == l) {
/* 3110 */         return true;
/*      */       }
/*      */     }
/* 3113 */     return false;
/*      */   }
/*      */ 
/*      */   public static void insertMouseListener(Component component, MouseListener l, int index)
/*      */   {
/* 3124 */     MouseListener[] listeners = component.getMouseListeners();
/* 3125 */     for (MouseListener listener : listeners) {
/* 3126 */       component.removeMouseListener(listener);
/*      */     }
/* 3128 */     for (int i = 0; i < listeners.length; i++) {
/* 3129 */       MouseListener listener = listeners[i];
/* 3130 */       if (index == i) {
/* 3131 */         component.addMouseListener(l);
/*      */       }
/* 3133 */       component.addMouseListener(listener);
/*      */     }
/*      */ 
/* 3136 */     if ((index < 0) || (index > listeners.length - 1))
/* 3137 */       component.addMouseListener(l);
/*      */   }
/*      */ 
/*      */   public static boolean isMouseMotionListenerRegistered(Component component, MouseMotionListener l)
/*      */   {
/* 3149 */     MouseMotionListener[] listeners = component.getMouseMotionListeners();
/* 3150 */     for (MouseMotionListener listener : listeners) {
/* 3151 */       if (listener == l) {
/* 3152 */         return true;
/*      */       }
/*      */     }
/* 3155 */     return false;
/*      */   }
/*      */ 
/*      */   public static void insertMouseMotionListener(Component component, MouseMotionListener l, int index)
/*      */   {
/* 3166 */     MouseMotionListener[] listeners = component.getMouseMotionListeners();
/* 3167 */     for (MouseMotionListener listener : listeners) {
/* 3168 */       component.removeMouseMotionListener(listener);
/*      */     }
/* 3170 */     for (int i = 0; i < listeners.length; i++) {
/* 3171 */       MouseMotionListener listener = listeners[i];
/* 3172 */       if (index == i) {
/* 3173 */         component.addMouseMotionListener(l);
/*      */       }
/* 3175 */       component.addMouseMotionListener(listener);
/*      */     }
/*      */ 
/* 3178 */     if ((index < 0) || (index > listeners.length - 1))
/* 3179 */       component.addMouseMotionListener(l);
/*      */   }
/*      */ 
/*      */   public static Component getScrollPane(Component innerComponent)
/*      */   {
/* 3190 */     Component component = innerComponent;
/* 3191 */     if ((innerComponent instanceof JScrollPane)) {
/* 3192 */       return innerComponent;
/*      */     }
/* 3194 */     if ((component.getParent() != null) && (component.getParent().getParent() != null) && ((component.getParent().getParent() instanceof JScrollPane))) {
/* 3195 */       component = component.getParent().getParent();
/* 3196 */       return component;
/*      */     }
/*      */ 
/* 3199 */     return null;
/*      */   }
/*      */ 
/*      */   public static boolean isListenerRegistered(EventListenerList list, Class t, EventListener l)
/*      */   {
/* 3213 */     Object[] objects = list.getListenerList();
/* 3214 */     return isListenerRegistered(objects, t, l);
/*      */   }
/*      */ 
/*      */   public static boolean isListenerRegistered(Component component, Class t, EventListener l)
/*      */   {
/* 3227 */     Object[] objects = component.getListeners(t);
/* 3228 */     return isListenerRegistered(objects, t, l);
/*      */   }
/*      */ 
/*      */   private static boolean isListenerRegistered(Object[] objects, Class t, EventListener l) {
/* 3232 */     for (int i = objects.length - 2; i >= 0; i -= 2) {
/* 3233 */       if ((objects[i] == t) && (objects[(i + 1)].equals(l))) {
/* 3234 */         return true;
/*      */       }
/*      */     }
/* 3237 */     return false;
/*      */   }
/*      */ 
/*      */   public static Component getFirstChildOf(Class<?> clazz, Component c)
/*      */   {
/* 3248 */     return getRecursively(c, new GetHandler(clazz) {
/*      */       public boolean condition(Component c) {
/* 3250 */         return this.val$clazz.isAssignableFrom(c.getClass());
/*      */       }
/*      */ 
/*      */       public Component action(Component c) {
/* 3254 */         return c;
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   public static int getComponentIndex(Container container, Component c)
/*      */   {
/* 3267 */     if (c.getParent() != container) {
/* 3268 */       return -1;
/*      */     }
/* 3270 */     Component[] children = container.getComponents();
/* 3271 */     for (int i = 0; i < children.length; i++) {
/* 3272 */       if (children[i] == c) {
/* 3273 */         return i;
/*      */       }
/*      */     }
/* 3276 */     return -1;
/*      */   }
/*      */ 
/*      */   public static Vector convertDefaultComboBoxModelToVector(DefaultComboBoxModel model)
/*      */   {
/* 3281 */     Vector v = new Vector();
/* 3282 */     for (int i = 0; i < model.getSize(); i++) {
/* 3283 */       v.add(model.getElementAt(i));
/*      */     }
/* 3285 */     return v;
/*      */   }
/*      */ 
/*      */   public static void ensureRowVisible(JTable table, int row)
/*      */   {
/* 3297 */     Rectangle r = table.getVisibleRect();
/*      */ 
/* 3300 */     Rectangle rMid = table.getCellRect(row, 0, true);
/* 3301 */     Rectangle rBefore = null; Rectangle rAfter = null;
/* 3302 */     if (row < table.getModel().getRowCount() - 1)
/* 3303 */       rAfter = table.getCellRect(row + 1, 0, true);
/* 3304 */     if (row > 0) {
/* 3305 */       rBefore = table.getCellRect(row - 1, 0, true);
/*      */     }
/* 3307 */     int yLow = (int)rMid.getMinY();
/* 3308 */     int yHi = (int)rMid.getMaxY();
/* 3309 */     int xLow = r.x;
/* 3310 */     int xHi = r.x + r.width;
/*      */ 
/* 3312 */     if (rBefore != null) {
/* 3313 */       yLow = (int)rBefore.getMinY();
/*      */     }
/* 3315 */     if (rAfter != null) {
/* 3316 */       yHi = (int)rAfter.getMaxY();
/*      */     }
/*      */ 
/* 3319 */     Rectangle rScrollTo = new Rectangle(xLow, yLow, xHi - xLow, yHi - yLow);
/* 3320 */     if ((!r.contains(rScrollTo)) && (rScrollTo.height != 0))
/* 3321 */       table.scrollRectToVisible(rScrollTo);
/*      */   }
/*      */ 
/*      */   public static void retargetMouseEvent(int id, MouseEvent e, Component target)
/*      */   {
/* 3326 */     if ((target == null) || ((target == e.getSource()) && (id == e.getID()))) {
/* 3327 */       return;
/*      */     }
/* 3329 */     if (e.isConsumed()) {
/* 3330 */       return;
/*      */     }
/*      */ 
/* 3337 */     Point p = SwingUtilities.convertPoint((Component)e.getSource(), e.getX(), e.getY(), target);
/*      */ 
/* 3340 */     MouseEvent retargeted = new MouseEvent(target, id, e.getWhen(), e.getModifiersEx() | e.getModifiers(), p.x, p.y, e.getClickCount(), e.isPopupTrigger());
/*      */ 
/* 3348 */     target.dispatchEvent(retargeted);
/*      */   }
/*      */ 
/*      */   public static JRootPane getOutermostRootPane(Component c)
/*      */   {
/* 3359 */     if (((c instanceof RootPaneContainer)) && (c.getParent() == null)) {
/* 3360 */       return ((RootPaneContainer)c).getRootPane();
/*      */     }
/*      */ 
/* 3363 */     for (; c != null; c = SwingUtilities.getRootPane(c)) {
/* 3364 */       if ((c instanceof JRootPane)) {
/* 3365 */         JRootPane lastRootPane = (JRootPane)c;
/* 3366 */         if (c.getParent().getParent() == null) {
/* 3367 */           return lastRootPane;
/*      */         }
/* 3369 */         if (((c.getParent() instanceof JDialog)) || ((c.getParent() instanceof JWindow)) || ((c.getParent() instanceof JFrame)) || ((c.getParent() instanceof JApplet)))
/*      */         {
/* 3371 */           return lastRootPane;
/*      */         }
/* 3373 */         c = c.getParent().getParent();
/*      */       }
/*      */     }
/* 3376 */     return null;
/*      */   }
/*      */ 
/*      */   public static boolean isFixedWidthFont(String fontName, Component component)
/*      */   {
/* 3388 */     if ((fontName.endsWith(" Bold")) || (fontName.endsWith(" ITC")) || (fontName.endsWith(" MT")) || (fontName.endsWith(" LET")) || (fontName.endsWith(".bold")) || (fontName.endsWith(".italic")))
/*      */     {
/* 3390 */       return false;
/*      */     }try {
/* 3392 */       Font font = new Font(fontName, 0, 12);
/* 3393 */       if (!font.canDisplay('W'))
/* 3394 */         return false;
/* 3395 */       Font boldFont = font.deriveFont(1);
/* 3396 */       FontMetrics fm = component.getFontMetrics(font);
/* 3397 */       FontMetrics fmBold = component.getFontMetrics(boldFont);
/* 3398 */       int l1 = fm.charWidth('l');
/* 3399 */       int l2 = fmBold.charWidth('l');
/* 3400 */       if (l1 == l2) {
/* 3401 */         int w1 = fm.charWidth('W');
/* 3402 */         int w2 = fmBold.charWidth('W');
/* 3403 */         if ((w1 == w2) && (l1 == w1)) {
/* 3404 */           int s1 = fm.charWidth(' ');
/* 3405 */           int s2 = fmBold.charWidth(' ');
/* 3406 */           if (s1 == s2) {
/* 3407 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Throwable throwable)
/*      */     {
/*      */     }
/* 3415 */     return false;
/*      */   }
/*      */ 
/*      */   public static void setLocaleRecursively(Component c, Locale locale)
/*      */   {
/* 3425 */     setRecursively(c, new Handler(locale) {
/*      */       public boolean condition(Component c) {
/* 3427 */         return true;
/*      */       }
/*      */ 
/*      */       public void action(Component c) {
/* 3431 */         c.setLocale(this.val$locale);
/*      */       }
/*      */ 
/*      */       public void postAction(Component c)
/*      */       {
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   public static void setBounds(Container container, Component component, Rectangle bounds)
/*      */   {
/* 3449 */     if (container.getComponentOrientation().isLeftToRight()) {
/* 3450 */       component.setBounds(bounds);
/*      */     }
/*      */     else {
/* 3453 */       Rectangle r = new Rectangle(bounds);
/* 3454 */       int w = container.getWidth();
/* 3455 */       r.x = (w - (bounds.x + bounds.width));
/* 3456 */       component.setBounds(r);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void setBounds(Container container, Component component, int x, int y, int width, int height)
/*      */   {
/* 3472 */     if (container.getComponentOrientation().isLeftToRight()) {
/* 3473 */       component.setBounds(x, y, width, height);
/*      */     }
/*      */     else {
/* 3476 */       int w = container.getWidth();
/* 3477 */       component.setBounds(w - x - width, y, width, height);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void invalidateRecursively(Component c)
/*      */   {
/* 3487 */     if ((c instanceof JComponent))
/* 3488 */       setRecursively(c, new Handler() {
/*      */         public boolean condition(Component c) {
/* 3490 */           return true;
/*      */         }
/*      */ 
/*      */         public void action(Component c) {
/* 3494 */           if ((c instanceof JComponent)) ((JComponent)c).revalidate();
/* 3495 */           c.invalidate();
/*      */         }
/*      */ 
/*      */         public void postAction(Component c) {
/*      */         }
/*      */       });
/* 3502 */     c.doLayout();
/* 3503 */     c.repaint();
/*      */   }
/*      */ 
/*      */   public static void synchronizeKeyboardActions(JComponent sourceComponent, JComponent targetComponent, KeyStroke[] keyStrokes, int condition)
/*      */   {
/* 3517 */     for (KeyStroke keyStroke : keyStrokes) {
/* 3518 */       ActionListener actionListener = sourceComponent.getActionForKeyStroke(keyStroke);
/* 3519 */       if (actionListener != null)
/* 3520 */         targetComponent.registerKeyboardAction(actionListener, keyStroke, condition);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static JComponent getFirstJComponent(RootPaneContainer rootPaneContainer)
/*      */   {
/* 3532 */     return (JComponent)getRecursively(rootPaneContainer.getContentPane(), new GetHandler() {
/*      */       public boolean condition(Component c) {
/* 3534 */         return c instanceof JComponent;
/*      */       }
/*      */ 
/*      */       public Component action(Component c) {
/* 3538 */         return c;
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   public static Dimension adjustPreferredScrollableViewportSize(JList list, Dimension defaultViewportSize)
/*      */   {
/* 3562 */     Rectangle cellBonds = list.getCellBounds(0, 0);
/* 3563 */     if ((cellBonds != null) && (cellBonds.height < 3)) {
/* 3564 */       ListCellRenderer renderer = list.getCellRenderer();
/* 3565 */       if (renderer != null) {
/* 3566 */         Component c = renderer.getListCellRendererComponent(list, "DUMMY STRING", 0, false, false);
/* 3567 */         if (c != null) {
/* 3568 */           Dimension preferredSize = c.getPreferredSize();
/* 3569 */           if (preferredSize != null) {
/* 3570 */             int height = preferredSize.height;
/* 3571 */             if (height < 3) {
/*      */               try {
/* 3573 */                 height = list.getCellBounds(1, 1).height;
/*      */               }
/*      */               catch (Exception e) {
/* 3576 */                 height = 16;
/*      */               }
/*      */             }
/* 3579 */             list.setFixedCellHeight(height);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 3584 */     if (SystemInfo.isJdk15Above()) {
/* 3585 */       return defaultViewportSize;
/*      */     }
/*      */ 
/* 3589 */     defaultViewportSize.height += 1;
/* 3590 */     return defaultViewportSize;
/*      */   }
/*      */ 
/*      */   public static void removeFromParentWithFocusTransfer(Component component)
/*      */   {
/* 3602 */     boolean wasVisible = component.isVisible();
/* 3603 */     component.setVisible(false);
/* 3604 */     if (component.getParent() != null) {
/* 3605 */       component.getParent().remove(component);
/*      */     }
/* 3607 */     component.setVisible(wasVisible);
/*      */   }
/*      */ 
/*      */   public static int getLineHeight(Component c, int defaultHeight)
/*      */   {
/* 3619 */     Font f = c == null ? null : c.getFont();
/* 3620 */     if (f == null) {
/* 3621 */       return defaultHeight;
/*      */     }
/* 3623 */     FontMetrics fm = c.getFontMetrics(f);
/* 3624 */     float h = fm.getHeight();
/*      */ 
/* 3626 */     h += fm.getDescent();
/*      */ 
/* 3628 */     return (int)h;
/*      */   }
/*      */ 
/*      */   public static void addSeparatorIfNecessary(JPopupMenu popup)
/*      */   {
/* 3637 */     int count = popup.getComponentCount();
/* 3638 */     if ((count > 0) && (!(popup.getComponent(count - 1) instanceof JSeparator)))
/* 3639 */       popup.addSeparator();
/*      */   }
/*      */ 
/*      */   public static void setTextComponentTransparent(JTextComponent textComponent)
/*      */   {
/* 3650 */     textComponent.setOpaque(false);
/*      */ 
/* 3653 */     textComponent.putClientProperty("Synthetica.opaque", Boolean.valueOf(false));
/*      */ 
/* 3655 */     textComponent.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.valueOf(false));
/* 3656 */     textComponent.putClientProperty("Nimbus.Overrides", new UIDefaults());
/*      */   }
/*      */ 
/*      */   public static int binarySearch(Object[] a, Object key)
/*      */   {
/* 3669 */     int x1 = 0;
/* 3670 */     int x2 = a.length;
/* 3671 */     int i = x2 / 2;
/* 3672 */     while (x1 < x2) {
/* 3673 */       int c = ((Comparable)a[i]).compareTo(key);
/* 3674 */       if (c == 0) {
/* 3675 */         return i;
/*      */       }
/* 3677 */       if (c < 0) {
/* 3678 */         x1 = i + 1;
/*      */       }
/*      */       else {
/* 3681 */         x2 = i;
/*      */       }
/* 3683 */       i = x1 + (x2 - x1) / 2;
/*      */     }
/* 3685 */     return -1 * i;
/*      */   }
/*      */ 
/*      */   public static int binarySearch(int[] a, int key)
/*      */   {
/* 3697 */     return binarySearch(a, key, 0, a.length);
/*      */   }
/*      */ 
/*      */   public static int binarySearch(int[] a, int key, int start, int end)
/*      */   {
/* 3711 */     int x1 = start;
/* 3712 */     int x2 = end;
/* 3713 */     int i = x2 / 2;
/* 3714 */     while (x1 < x2) {
/* 3715 */       if (a[i] == key) {
/* 3716 */         return i;
/*      */       }
/* 3718 */       if (a[i] < key) {
/* 3719 */         x1 = i + 1;
/*      */       }
/*      */       else {
/* 3722 */         x2 = i;
/*      */       }
/* 3724 */       i = x1 + (x2 - x1) / 2;
/*      */     }
/* 3726 */     return -1 * i;
/*      */   }
/*      */ 
/*      */   public static boolean isMenuShortcutKeyDown(InputEvent event)
/*      */   {
/* 3736 */     return (event.getModifiers() & Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0;
/*      */   }
/*      */ 
/*      */   public static boolean isMenuShortcutKeyDown(ActionEvent event)
/*      */   {
/* 3746 */     return (event.getModifiers() & Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0;
/*      */   }
/*      */ 
/*      */   public static ChangeListener getViewportSynchronizationChangeListener()
/*      */   {
/* 3752 */     if (_viewportSyncListener == null) {
/* 3753 */       _viewportSyncListener = new viewportSynchronizationChangeListener(null);
/*      */     }
/* 3755 */     return _viewportSyncListener;
/*      */   }
/*      */ 
/*      */   public static void setWindowOpaque(Window window, boolean opaque)
/*      */   {
/*      */     try
/*      */     {
/* 3804 */       Class c = Class.forName("com.sun.awt.AWTUtilities");
/* 3805 */       Method m = c.getMethod("setWindowOpaque", new Class[] { Window.class, Boolean.TYPE });
/* 3806 */       m.invoke(null, new Object[] { window, Boolean.valueOf(opaque) });
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void setWindowOpacity(Window window, float opacity)
/*      */   {
/*      */     try
/*      */     {
/* 3821 */       Class awtUtilitiesClass = Class.forName("com.sun.awt.AWTUtilities");
/* 3822 */       Method mSetWindowOpacity = awtUtilitiesClass.getMethod("setWindowOpacity", new Class[] { Window.class, Float.TYPE });
/* 3823 */       mSetWindowOpacity.invoke(null, new Object[] { window, Float.valueOf(opacity) });
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void setWindowShape(Window window, Shape shape)
/*      */   {
/*      */     try
/*      */     {
/* 3838 */       Class c = Class.forName("com.sun.awt.AWTUtilities");
/* 3839 */       Method m = c.getMethod("setWindowShape", new Class[] { Window.class, Shape.class });
/* 3840 */       m.invoke(null, new Object[] { window, shape });
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*   70 */     Object aa = SecurityUtils.getProperty("swing.aatext", "false");
/*   71 */     AA_TEXT_DEFINED = aa != null;
/*   72 */     AA_TEXT = "true".equals(aa);
/*      */ 
/* 1879 */     renderingHints = null;
/*      */ 
/* 1882 */     if (SystemInfo.isJdk6Above()) {
/* 1883 */       Toolkit tk = Toolkit.getDefaultToolkit();
/* 1884 */       renderingHints = (RenderingHints)(RenderingHints)tk.getDesktopProperty("awt.font.desktophints");
/* 1885 */       tk.addPropertyChangeListener("awt.font.desktophints", new PropertyChangeListener() {
/*      */         public void propertyChange(PropertyChangeEvent evt) {
/* 1887 */           if ((evt.getNewValue() instanceof RenderingHints)) {
/* 1888 */             JideSwingUtilities.renderingHints = (RenderingHints)evt.getNewValue();
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       });
/*      */     }
/*      */ 
/* 2415 */     tracingFocus = false;
/*      */ 
/* 2845 */     Font.getFont("defaultFont");
/* 2846 */     Font.getFont("emphasizedFont");
/*      */   }
/*      */ 
/*      */   private static class viewportSynchronizationChangeListener
/*      */     implements ChangeListener
/*      */   {
/*      */     public void stateChanged(ChangeEvent e)
/*      */     {
/* 3760 */       if (!(e.getSource() instanceof JViewport)) {
/* 3761 */         return;
/*      */       }
/* 3763 */       JViewport masterViewport = (JViewport)e.getSource();
/* 3764 */       Object property = masterViewport.getClientProperty("synchronizeViewSlaveViewport");
/* 3765 */       if (!(property instanceof Map)) {
/* 3766 */         return;
/*      */       }
/*      */ 
/* 3769 */       Dimension size = masterViewport.getSize();
/* 3770 */       if ((size.width == 0) || (size.height == 0)) {
/* 3771 */         return;
/*      */       }
/* 3773 */       Map slaveViewportMap = (Map)property;
/* 3774 */       for (JViewport slaveViewport : slaveViewportMap.keySet()) {
/* 3775 */         slaveViewport.removeChangeListener(JideSwingUtilities.getViewportSynchronizationChangeListener());
/* 3776 */         int orientation = ((Integer)slaveViewportMap.get(slaveViewport)).intValue();
/* 3777 */         if (orientation == 0) {
/* 3778 */           Point v1 = masterViewport.getViewPosition();
/* 3779 */           Point v2 = slaveViewport.getViewPosition();
/* 3780 */           if (v1.x != v2.x) {
/* 3781 */             slaveViewport.setViewPosition(new Point(v1.x, v2.y));
/*      */           }
/*      */         }
/* 3784 */         else if (orientation == 1) {
/* 3785 */           Point v1 = masterViewport.getViewPosition();
/* 3786 */           Point v2 = slaveViewport.getViewPosition();
/* 3787 */           if (v1.y != v2.y) {
/* 3788 */             slaveViewport.setViewPosition(new Point(v2.x, v1.y));
/*      */           }
/*      */         }
/* 3791 */         slaveViewport.addChangeListener(JideSwingUtilities.getViewportSynchronizationChangeListener());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class TraceDebugBorder extends CompoundBorder
/*      */   {
/*      */     private static final long serialVersionUID = -1396250213346461982L;
/*      */ 
/*      */     public TraceDebugBorder(Border insideBorder)
/*      */     {
/* 2470 */       super(insideBorder);
/*      */     }
/*      */ 
/*      */     public Insets getBorderInsets(Component c) {
/* 2474 */       return getInsideBorder().getBorderInsets(c);
/*      */     }
/*      */ 
/*      */     public Insets getBorderInsets(Component c, Insets insets) {
/* 2478 */       return getInsideBorder().getBorderInsets(c);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static abstract interface GetHandler
/*      */   {
/*      */     public abstract boolean condition(Component paramComponent);
/*      */ 
/*      */     public abstract Component action(Component paramComponent);
/*      */   }
/*      */ 
/*      */   public static abstract interface ConditionHandler extends JideSwingUtilities.Handler
/*      */   {
/*      */     public abstract boolean stopCondition(Component paramComponent);
/*      */   }
/*      */ 
/*      */   public static abstract interface Handler
/*      */   {
/*      */     public abstract boolean condition(Component paramComponent);
/*      */ 
/*      */     public abstract void action(Component paramComponent);
/*      */ 
/*      */     public abstract void postAction(Component paramComponent);
/*      */   }
/*      */ 
/*      */   private static class GetPropertyAction
/*      */     implements PrivilegedAction
/*      */   {
/*      */     private String theProp;
/*      */     private String defaultVal;
/*      */ 
/*      */     public GetPropertyAction(String theProp)
/*      */     {
/*  654 */       this.theProp = theProp;
/*      */     }
/*      */ 
/*      */     public GetPropertyAction(String theProp, String defaultVal)
/*      */     {
/*  664 */       this.theProp = theProp;
/*  665 */       this.defaultVal = defaultVal;
/*      */     }
/*      */ 
/*      */     public Object run()
/*      */     {
/*  674 */       String value = System.getProperty(this.theProp);
/*  675 */       return value == null ? this.defaultVal : value;
/*      */     }
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.JideSwingUtilities
 * JD-Core Version:    0.6.0
 */
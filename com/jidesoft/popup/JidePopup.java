/*      */ package com.jidesoft.popup;
/*      */ 
/*      */ import com.jidesoft.plaf.LookAndFeelFactory;
/*      */ import com.jidesoft.plaf.PopupUI;
/*      */ import com.jidesoft.plaf.UIDefaultsLookup;
/*      */ import com.jidesoft.swing.JideScrollPane;
/*      */ import com.jidesoft.swing.JideSwingUtilities;
/*      */ import com.jidesoft.swing.JideSwingUtilities.Handler;
/*      */ import com.jidesoft.swing.Resizable;
/*      */ import com.jidesoft.swing.Resizable.ResizeCorner;
/*      */ import com.jidesoft.swing.ResizablePanel;
/*      */ import com.jidesoft.swing.ResizableSupport;
/*      */ import com.jidesoft.swing.ResizableWindow;
/*      */ import com.jidesoft.utils.PortingUtils;
/*      */ import com.jidesoft.utils.SecurityUtils;
/*      */ import com.jidesoft.utils.SystemInfo;
/*      */ import java.applet.Applet;
/*      */ import java.awt.AWTEvent;
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Frame;
/*      */ import java.awt.IllegalComponentStateException;
/*      */ import java.awt.Insets;
/*      */ import java.awt.KeyboardFocusManager;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.AWTEventListener;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.ComponentAdapter;
/*      */ import java.awt.event.ComponentEvent;
/*      */ import java.awt.event.HierarchyEvent;
/*      */ import java.awt.event.HierarchyListener;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.WindowAdapter;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleValue;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JComponent.AccessibleJComponent;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JLayeredPane;
/*      */ import javax.swing.JMenuBar;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JRootPane;
/*      */ import javax.swing.JWindow;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.RootPaneContainer;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.Timer;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.WindowConstants;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.event.EventListenerList;
/*      */ import javax.swing.event.PopupMenuEvent;
/*      */ import javax.swing.event.PopupMenuListener;
/*      */ import sun.awt.EmbeddedFrame;
/*      */ 
/*      */ public class JidePopup extends JComponent
/*      */   implements Accessible, WindowConstants
/*      */ {
/*      */   public static final String CLIENT_PROPERTY_WINDOW_OPACITY = "windowOpacity";
/*      */   public static final String CLIENT_PROPERTY_WINDOW_OPAQUE = "windowOpaque";
/*      */   public static final String CLIENT_PROPERTY_WINDOW_SHAPE = "windowShape";
/*      */   private static final String uiClassID = "JidePopupUI";
/*      */   private JRootPane rootPane;
/*   78 */   private boolean rootPaneCheckingEnabled = false;
/*      */   public static final String CONTENT_PANE_PROPERTY = "contentPane";
/*      */   public static final String MENU_BAR_PROPERTY = "JMenuBar";
/*      */   public static final String LAYERED_PANE_PROPERTY = "layeredPane";
/*      */   public static final String ROOT_PANE_PROPERTY = "rootPane";
/*      */   public static final String GLASS_PANE_PROPERTY = "glassPane";
/*      */   public static final String VISIBLE_PROPERTY = "visible";
/*      */   public static final String TRANSIENT_PROPERTY = "transient";
/*      */   public static final String ATTACHABLE_PROPERTY = "attachable";
/*  122 */   private boolean _attachable = true;
/*      */   public static final String MOVABLE_PROPERTY = "movable";
/*  132 */   private boolean _movable = false;
/*      */   public static final String DETACHED_PROPERTY = "detached";
/*      */   public static final String CLIENT_PROPERTY_POPUP_TYPE = "popupType";
/*      */   public static final String CLIENT_PROPERTY_VALUE_POPUP_TYPE_COMBOBOX = "comboBox";
/*      */   protected boolean _detached;
/*      */   protected ResizableWindow _window;
/*      */   protected ResizablePanel _panel;
/*      */   protected ResizableSupport _resizableSupport;
/*      */   private ComponentAdapter _componentListener;
/*      */   private WindowAdapter _windowListener;
/*      */   private ComponentAdapter _ownerComponentListener;
/*      */   private HierarchyListener _hierarchyListener;
/*      */   private Point _displayStartLocation;
/*      */   public static final String CLIENT_PROPERTY_POPUP_ACTUAL_OWNER = "JidePopup.actualOwner";
/*      */   public static final String RESIZABLE_PROPERTY = "resizable";
/*  171 */   private boolean _resizable = true;
/*      */ 
/*  173 */   private boolean _keepPreviousSize = true;
/*      */   public static final String OWNER_PROPERTY = "owner";
/*      */   private Component _owner;
/*      */   private Border _popupBorder;
/*  191 */   private boolean _transient = true;
/*      */ 
/*  193 */   private int _timeout = 0;
/*      */   private Timer _timer;
/*      */   private Component _defaultFocusComponent;
/*      */   public static final int DO_NOTHING_ON_MOVED = -1;
/*      */   public static final int HIDE_ON_MOVED = 0;
/*      */   public static final int MOVE_ON_MOVED = 1;
/*  213 */   private int _defaultMoveOperation = 0;
/*      */ 
/*  217 */   public int DISTANCE_TO_SCREEN_BORDER = 10;
/*      */   private List<Component> _excludedComponents;
/*  221 */   private int _gripperLocation = 1;
/*      */   public static final String PROPERTY_GRIPPER_LOCATION = "gripperLocation";
/*      */   private ComponentAdapter _popupResizeListener;
/*      */   protected Dimension _previousSize;
/*      */   protected Component _actualOwner;
/*      */   protected Point _actualOwnerLocation;
/*      */   public static final int LIGHT_WEIGHT_POPUP = 0;
/*      */   public static final int HEAVY_WEIGHT_POPUP = 2;
/*  240 */   private int _popupType = 2;
/*      */   private ActionListener _escapeActionListener;
/*  765 */   protected Insets _insets = null;
/*      */ 
/* 1452 */   private boolean _isDragging = false;
/*      */   private double _relativeX;
/*      */   private double _relativeY;
/*      */   private Point _startPoint;
/*      */   private Window _currentWindow;
/*      */   private JPanel _currentPanel;
/* 1617 */   private Insets _backToOriginalInsets = new Insets(10, 10, 10, 10);
/*      */   private AWTEventListener _awtEventListener;
/*      */   private static boolean checkedUnpostPopup;
/*      */   private static boolean unpostPopup;
/*      */ 
/*      */   public JidePopup()
/*      */   {
/*  247 */     this._excludedComponents = new ArrayList();
/*  248 */     setRootPane(createRootPane());
/*  249 */     setLayout(new BorderLayout());
/*  250 */     setRootPaneCheckingEnabled(true);
/*  251 */     setFocusable(false);
/*  252 */     updateUI();
/*      */   }
/*      */ 
/*      */   protected JRootPane createRootPane()
/*      */   {
/*  263 */     JRootPane pane = new JRootPane();
/*      */ 
/*  265 */     pane.getContentPane().setLayout(new BorderLayout());
/*  266 */     pane.setOpaque(false);
/*  267 */     return pane;
/*      */   }
/*      */ 
/*      */   public PopupUI getUI()
/*      */   {
/*  276 */     return (PopupUI)this.ui;
/*      */   }
/*      */ 
/*      */   public void setUI(PopupUI ui)
/*      */   {
/*  285 */     boolean checkingEnabled = isRootPaneCheckingEnabled();
/*      */     try {
/*  287 */       setRootPaneCheckingEnabled(false);
/*  288 */       super.setUI(ui);
/*      */     }
/*      */     finally {
/*  291 */       setRootPaneCheckingEnabled(checkingEnabled);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateUI()
/*      */   {
/*  303 */     if (UIDefaultsLookup.get("JidePopupUI") == null) {
/*  304 */       LookAndFeelFactory.installJideExtension();
/*      */     }
/*  306 */     setUI((PopupUI)UIManager.getUI(this));
/*  307 */     invalidate();
/*      */   }
/*      */ 
/*      */   public String getUIClassID()
/*      */   {
/*  320 */     return "JidePopupUI";
/*      */   }
/*      */ 
/*      */   protected boolean isRootPaneCheckingEnabled()
/*      */   {
/*  333 */     return this.rootPaneCheckingEnabled;
/*      */   }
/*      */ 
/*      */   protected void setRootPaneCheckingEnabled(boolean enabled)
/*      */   {
/*  346 */     this.rootPaneCheckingEnabled = enabled;
/*      */   }
/*      */ 
/*      */   protected void addImpl(Component comp, Object constraints, int index)
/*      */   {
/*  366 */     if (isRootPaneCheckingEnabled()) {
/*  367 */       getContentPane().add(comp, constraints, index);
/*      */     }
/*      */     else
/*  370 */       super.addImpl(comp, constraints, index);
/*      */   }
/*      */ 
/*      */   public void remove(Component comp)
/*      */   {
/*  382 */     int oldCount = getComponentCount();
/*  383 */     super.remove(comp);
/*  384 */     if (oldCount == getComponentCount())
/*      */     {
/*  387 */       getContentPane().remove(comp);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setLayout(LayoutManager manager)
/*      */   {
/*  407 */     if (isRootPaneCheckingEnabled()) {
/*  408 */       getContentPane().setLayout(manager);
/*      */     }
/*      */     else
/*  411 */       super.setLayout(manager);
/*      */   }
/*      */ 
/*      */   public JMenuBar getJMenuBar()
/*      */   {
/*  428 */     return getRootPane().getJMenuBar();
/*      */   }
/*      */ 
/*      */   public void setJMenuBar(JMenuBar m)
/*      */   {
/*  438 */     JMenuBar oldValue = getJMenuBar();
/*  439 */     getRootPane().setJMenuBar(m);
/*  440 */     firePropertyChange("JMenuBar", oldValue, m);
/*      */   }
/*      */ 
/*      */   public Container getContentPane()
/*      */   {
/*  451 */     return getRootPane().getContentPane();
/*      */   }
/*      */ 
/*      */   public void setContentPane(Container c)
/*      */   {
/*  464 */     Container oldValue = getContentPane();
/*  465 */     getRootPane().setContentPane(c);
/*  466 */     firePropertyChange("contentPane", oldValue, c);
/*      */   }
/*      */ 
/*      */   public JLayeredPane getLayeredPane()
/*      */   {
/*  478 */     return getRootPane().getLayeredPane();
/*      */   }
/*      */ 
/*      */   public void setLayeredPane(JLayeredPane layered)
/*      */   {
/*  490 */     JLayeredPane oldValue = getLayeredPane();
/*  491 */     getRootPane().setLayeredPane(layered);
/*  492 */     firePropertyChange("layeredPane", oldValue, layered);
/*      */   }
/*      */ 
/*      */   public Component getGlassPane()
/*      */   {
/*  503 */     return getRootPane().getGlassPane();
/*      */   }
/*      */ 
/*      */   public void setGlassPane(Component glass)
/*      */   {
/*  513 */     Component oldValue = getGlassPane();
/*  514 */     getRootPane().setGlassPane(glass);
/*  515 */     firePropertyChange("glassPane", oldValue, glass);
/*      */   }
/*      */ 
/*      */   public JRootPane getRootPane()
/*      */   {
/*  527 */     return this.rootPane;
/*      */   }
/*      */ 
/*      */   protected void setRootPane(JRootPane root)
/*      */   {
/*  537 */     if (this.rootPane != null) {
/*  538 */       this.rootPane.removeAll();
/*  539 */       remove(this.rootPane);
/*      */     }
/*  541 */     JRootPane oldValue = getRootPane();
/*  542 */     this.rootPane = root;
/*  543 */     if (this.rootPane != null) {
/*  544 */       boolean checkingEnabled = isRootPaneCheckingEnabled();
/*      */       try {
/*  546 */         setRootPaneCheckingEnabled(false);
/*  547 */         add(this.rootPane, "Center");
/*      */       }
/*      */       finally {
/*  550 */         setRootPaneCheckingEnabled(checkingEnabled);
/*      */       }
/*      */     }
/*  553 */     firePropertyChange("rootPane", oldValue, root);
/*      */   }
/*      */ 
/*      */   public void setVisible(boolean visible)
/*      */   {
/*  563 */     boolean old = isVisible();
/*  564 */     if (visible != old) {
/*  565 */       super.setVisible(visible);
/*  566 */       firePropertyChange("visible", old, visible);
/*      */     }
/*      */   }
/*      */ 
/*      */   public AccessibleContext getAccessibleContext()
/*      */   {
/*  582 */     if (this.accessibleContext == null) {
/*  583 */       this.accessibleContext = new AccessiblePopup();
/*      */     }
/*  585 */     return this.accessibleContext;
/*      */   }
/*      */ 
/*      */   public boolean isKeepPreviousSize()
/*      */   {
/*  597 */     return this._keepPreviousSize;
/*      */   }
/*      */ 
/*      */   public void setKeepPreviousSize(boolean keepPreviousSize)
/*      */   {
/*  606 */     this._keepPreviousSize = keepPreviousSize;
/*      */   }
/*      */ 
/*      */   public Insets getBackToOriginalInsets()
/*      */   {
/*  619 */     return this._backToOriginalInsets;
/*      */   }
/*      */ 
/*      */   public void setBackToOriginalInsets(Insets backToOriginalInsets)
/*      */   {
/*  629 */     this._backToOriginalInsets = backToOriginalInsets;
/*      */   }
/*      */ 
/*      */   public void showPopup()
/*      */   {
/*  743 */     showPopup(new Insets(0, 0, 0, 0), null);
/*      */   }
/*      */ 
/*      */   public void showPopup(Component owner)
/*      */   {
/*  753 */     showPopup(new Insets(0, 0, 0, 0), owner);
/*      */   }
/*      */ 
/*      */   public void showPopup(Insets insets)
/*      */   {
/*  762 */     showPopup(insets, null);
/*      */   }
/*      */ 
/*      */   public void showPopup(Insets insets, Component owner)
/*      */   {
/*  776 */     this._insets = insets;
/*  777 */     Component actualOwner = owner != null ? owner : getOwner();
/*  778 */     if ((actualOwner != null) && (actualOwner.isShowing())) {
/*  779 */       Point point = actualOwner.getLocationOnScreen();
/*  780 */       internalShowPopup(point.x, point.y, actualOwner);
/*      */     }
/*      */     else {
/*  783 */       showPopup(0);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected Point getPopupLocation(Point point, Dimension size, Component owner)
/*      */   {
/*  796 */     Component actualOwner = owner != null ? owner : getOwner();
/*  797 */     Dimension ownerSize = actualOwner != null ? actualOwner.getSize() : new Dimension(0, 0);
/*  798 */     Dimension screenSize = PortingUtils.getScreenSize(owner);
/*      */ 
/*  800 */     if (size.width == 0) {
/*  801 */       size = getPreferredSize();
/*      */     }
/*      */ 
/*  804 */     Point p = new Point(point.x + this._insets.left, point.y + ownerSize.height - this._insets.bottom);
/*  805 */     int left = p.x + size.width;
/*  806 */     int bottom = p.y + size.height;
/*      */ 
/*  808 */     if (left > screenSize.width) {
/*  809 */       p.x -= left - screenSize.width;
/*      */     }
/*      */ 
/*  812 */     if (bottom > screenSize.height) {
/*  813 */       p.y = (point.y + this._insets.top - size.height);
/*  814 */       if (isResizable()) {
/*  815 */         setupResizeCorner(4);
/*      */       }
/*      */ 
/*      */     }
/*  819 */     else if (isResizable()) {
/*  820 */       setupResizeCorner(16);
/*      */     }
/*      */ 
/*  823 */     return p;
/*      */   }
/*      */ 
/*      */   public void setupResizeCorner(int corner)
/*      */   {
/*  832 */     switch (corner) {
/*      */     case 4:
/*  834 */       if (this._resizableSupport == null) break;
/*  835 */       this._resizableSupport.getResizable().setResizableCorners(4);
/*  836 */       JideSwingUtilities.setRecursively(this, new JideSwingUtilities.Handler() {
/*      */         public boolean condition(Component c) {
/*  838 */           return c instanceof JideScrollPane;
/*      */         }
/*      */ 
/*      */         public void action(Component c) {
/*  842 */           Resizable.ResizeCorner corner = new Resizable.ResizeCorner(4);
/*  843 */           corner.addMouseListener(JidePopup.this._resizableSupport.getResizable().getMouseInputAdapter());
/*  844 */           corner.addMouseMotionListener(JidePopup.this._resizableSupport.getResizable().getMouseInputAdapter());
/*  845 */           ((JideScrollPane)c).setScrollBarCorner("VERTICAL_TOP", corner);
/*  846 */           ((JideScrollPane)c).setScrollBarCorner("VERTICAL_BOTTOM", null);
/*      */         }
/*      */ 
/*      */         public void postAction(Component c)
/*      */         {
/*      */         }
/*      */       });
/*  836 */       break;
/*      */     case 16:
/*  856 */       if (this._resizableSupport == null) break;
/*  857 */       this._resizableSupport.getResizable().setResizableCorners(16);
/*  858 */       JideSwingUtilities.setRecursively(this, new JideSwingUtilities.Handler() {
/*      */         public boolean condition(Component c) {
/*  860 */           return c instanceof JideScrollPane;
/*      */         }
/*      */ 
/*      */         public void action(Component c) {
/*  864 */           Resizable.ResizeCorner corner = new Resizable.ResizeCorner(16);
/*  865 */           corner.addMouseListener(JidePopup.this._resizableSupport.getResizable().getMouseInputAdapter());
/*  866 */           corner.addMouseMotionListener(JidePopup.this._resizableSupport.getResizable().getMouseInputAdapter());
/*  867 */           ((JideScrollPane)c).setScrollBarCorner("VERTICAL_BOTTOM", corner);
/*  868 */           ((JideScrollPane)c).setScrollBarCorner("VERTICAL_TOP", null);
/*      */         }
/*      */ 
/*      */         public void postAction(Component c)
/*      */         {
/*      */         }
/*      */       });
/*  858 */       break;
/*      */     default:
/*  877 */       if (this._resizableSupport == null) break;
/*  878 */       this._resizableSupport.getResizable().setResizableCorners(corner);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static Component getTopLevelAncestor(Component component)
/*      */   {
/*  885 */     if (component == null) {
/*  886 */       return null;
/*      */     }
/*      */ 
/*  889 */     for (Component p = component; p != null; p = p.getParent()) {
/*  890 */       if (((p instanceof Window)) || ((p instanceof Applet))) {
/*  891 */         return p;
/*      */       }
/*      */     }
/*  894 */     return null;
/*      */   }
/*      */ 
/*      */   public void showPopup(int location)
/*      */   {
/*  909 */     showPopup(location, null);
/*      */   }
/*      */ 
/*      */   public void showPopup(int location, Component owner)
/*      */   {
/*  926 */     setDetached(true);
/*  927 */     Rectangle screenDim = getDisplayScreenBounds(owner);
/*      */ 
/*  929 */     Dimension actualSize = getSize();
/*  930 */     Dimension size = actualSize.width == 0 ? getPreferredSize() : actualSize;
/*  931 */     Point displayLocation = getDisplayStartLocation(screenDim, size, location);
/*  932 */     internalShowPopup(displayLocation.x, displayLocation.y, owner);
/*      */   }
/*      */ 
/*      */   public void setDisplayStartLocation(Point startLocation)
/*      */   {
/*  942 */     this._displayStartLocation = startLocation;
/*      */   }
/*      */ 
/*      */   protected Point getDisplayStartLocation(Rectangle screenDim, Dimension size, int location)
/*      */   {
/*  956 */     if (this._displayStartLocation != null) {
/*  957 */       return this._displayStartLocation;
/*      */     }
/*  959 */     switch (location) {
/*      */     case 0:
/*  961 */       return new Point(screenDim.x + (screenDim.width - size.width) / 2, screenDim.y + (screenDim.height - size.height) / 2);
/*      */     case 5:
/*  964 */       return new Point(screenDim.x + (screenDim.width - size.width) / 2, screenDim.y + screenDim.height - size.height - this.DISTANCE_TO_SCREEN_BORDER);
/*      */     case 1:
/*  967 */       return new Point(screenDim.x + (screenDim.width - size.width) / 2, screenDim.y + this.DISTANCE_TO_SCREEN_BORDER);
/*      */     case 3:
/*  970 */       return new Point(screenDim.x + screenDim.width - size.width - this.DISTANCE_TO_SCREEN_BORDER, screenDim.y + (screenDim.height - size.height) / 2);
/*      */     case 7:
/*  973 */       return new Point(screenDim.x + this.DISTANCE_TO_SCREEN_BORDER, screenDim.y + (screenDim.height - size.height) / 2);
/*      */     case 6:
/*  976 */       return new Point(screenDim.x + this.DISTANCE_TO_SCREEN_BORDER, screenDim.y + screenDim.height - size.height - this.DISTANCE_TO_SCREEN_BORDER);
/*      */     case 2:
/*  979 */       return new Point(screenDim.x + screenDim.width - size.width - this.DISTANCE_TO_SCREEN_BORDER, screenDim.y + this.DISTANCE_TO_SCREEN_BORDER);
/*      */     case 8:
/*  982 */       return new Point(screenDim.x + this.DISTANCE_TO_SCREEN_BORDER, screenDim.y + this.DISTANCE_TO_SCREEN_BORDER);
/*      */     case 4:
/*      */     }
/*      */ 
/*  986 */     return new Point(screenDim.x + screenDim.width - size.width - this.DISTANCE_TO_SCREEN_BORDER, screenDim.y + screenDim.height - size.height - this.DISTANCE_TO_SCREEN_BORDER);
/*      */   }
/*      */ 
/*      */   protected Rectangle getDisplayScreenBounds(Component owner)
/*      */   {
/*      */     Rectangle screenDim;
/*  993 */     if ((owner != null) && (owner.isShowing())) {
/*  994 */       Rectangle screenDim = owner.getBounds();
/*  995 */       Point p = owner.getLocationOnScreen();
/*  996 */       screenDim.x = p.x;
/*  997 */       screenDim.y = p.y;
/*      */     }
/*      */     else {
/* 1000 */       screenDim = getOwner() == null ? PortingUtils.getLocalScreenBounds() : PortingUtils.getScreenBounds(getOwner(), true);
/*      */     }
/* 1002 */     return screenDim;
/*      */   }
/*      */ 
/*      */   public void packPopup() {
/* 1006 */     if (this._popupType == 0) {
/* 1007 */       if (this._panel == null) {
/* 1008 */         return;
/*      */       }
/* 1010 */       this._panel.setSize(this._panel.getPreferredSize());
/*      */     }
/* 1012 */     else if (this._popupType == 2) {
/* 1013 */       if (this._window == null) {
/* 1014 */         return;
/*      */       }
/* 1016 */       this._window.pack();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void internalShowPopup(int x, int y)
/*      */   {
/* 1024 */     internalShowPopup(x, y, null);
/*      */   }
/*      */ 
/*      */   protected void internalShowPopup(int x, int y, Component owner) {
/* 1028 */     this._actualOwner = (owner != null ? owner : getOwner());
/* 1029 */     if (this._actualOwner != null) {
/*      */       try {
/* 1031 */         this._actualOwnerLocation = this._actualOwner.getLocationOnScreen();
/*      */       }
/*      */       catch (IllegalComponentStateException e) {
/* 1034 */         return;
/*      */       }
/*      */     }
/* 1037 */     createWindow(this._actualOwner, x, y);
/* 1038 */     showPopupImmediately();
/*      */   }
/*      */ 
/*      */   protected void createWindow(Component owner, int x, int y) {
/* 1042 */     if (this._popupType == 0) {
/* 1043 */       if (this._panel == null) {
/* 1044 */         this._panel = createLightweightPopupContainer(owner);
/* 1045 */         this._resizableSupport = this._panel;
/* 1046 */         installListeners();
/* 1047 */         installBorder();
/*      */       }
/*      */ 
/* 1050 */       if ((this._previousSize != null) && (isKeepPreviousSize())) {
/* 1051 */         setPreferredSize(this._previousSize);
/*      */       }
/* 1053 */       this._previousSize = null;
/* 1054 */       packPopup();
/*      */ 
/* 1056 */       if (this._insets != null) {
/* 1057 */         Point p = getPopupLocation(new Point(x, y), this._panel.getSize(), owner);
/* 1058 */         x = p.x;
/* 1059 */         y = p.y;
/*      */       }
/*      */ 
/* 1062 */       JRootPane rootPane = JideSwingUtilities.getOutermostRootPane(owner);
/*      */       JLayeredPane layeredPane;
/* 1064 */       if (rootPane != null)
/* 1065 */         layeredPane = rootPane.getLayeredPane();
/*      */       else
/* 1067 */         return;
/*      */       JLayeredPane layeredPane;
/* 1070 */       Point p = new Point(x, y);
/* 1071 */       SwingUtilities.convertPointFromScreen(p, layeredPane);
/* 1072 */       layeredPane.add(this._panel, JLayeredPane.PALETTE_LAYER);
/* 1073 */       if (SystemInfo.isJdk15Above()) {
/* 1074 */         layeredPane.setComponentZOrder(this._panel, 0);
/*      */       }
/*      */ 
/* 1077 */       this._panel.setLocation(p.x, p.y);
/*      */     }
/* 1079 */     else if (this._popupType == 2) {
/* 1080 */       if (this._window == null) {
/* 1081 */         this._window = createHeavyweightPopupContainer(owner);
/* 1082 */         this._resizableSupport = this._window;
/* 1083 */         installListeners();
/* 1084 */         installBorder();
/*      */       }
/*      */ 
/* 1087 */       if ((this._previousSize != null) && (isKeepPreviousSize())) {
/* 1088 */         setPreferredSize(this._previousSize);
/*      */       }
/* 1090 */       this._previousSize = null;
/* 1091 */       packPopup();
/*      */ 
/* 1093 */       if (this._insets != null) {
/* 1094 */         Point p = getPopupLocation(new Point(x, y), this._window.getSize(), owner);
/* 1095 */         x = p.x;
/* 1096 */         y = p.y;
/*      */       }
/*      */ 
/* 1099 */       this._window.setLocation(x, y);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void showPopup(int x, int y)
/*      */   {
/* 1110 */     showPopup(x, y, null);
/*      */   }
/*      */ 
/*      */   public void showPopup(int x, int y, Component owner)
/*      */   {
/* 1122 */     internalShowPopup(x, y, owner);
/*      */   }
/*      */ 
/*      */   protected static Frame getFrame(Component c) {
/* 1126 */     Component w = c;
/*      */ 
/* 1128 */     while ((!(w instanceof Frame)) && (w != null)) {
/* 1129 */       w = w.getParent();
/*      */     }
/* 1131 */     return (Frame)w;
/*      */   }
/*      */ 
/*      */   protected ResizableWindow createHeavyweightPopupContainer(Component owner)
/*      */   {
/* 1141 */     Component topLevelAncestor = getTopLevelAncestor(owner);
/*      */     ResizableWindow container;
/*      */     ResizableWindow container;
/* 1142 */     if ((topLevelAncestor instanceof Frame)) {
/* 1143 */       container = new ResizableWindow((Frame)topLevelAncestor);
/*      */     }
/*      */     else
/*      */     {
/*      */       ResizableWindow container;
/* 1145 */       if ((topLevelAncestor instanceof Window)) {
/* 1146 */         container = new ResizableWindow((Window)topLevelAncestor);
/*      */       }
/*      */       else {
/* 1149 */         Frame frame = getFrame(owner);
/* 1150 */         container = new ResizableWindow(frame);
/*      */       }
/*      */     }
/* 1152 */     container.setName("JidePopup");
/* 1153 */     container.getContentPane().add(this);
/*      */ 
/* 1155 */     Object opaque = getClientProperty("windowOpaque");
/* 1156 */     if ((opaque instanceof Boolean)) {
/* 1157 */       JideSwingUtilities.setWindowOpaque(container, ((Boolean)opaque).booleanValue());
/*      */     }
/* 1159 */     Object opacity = getClientProperty("windowOpacity");
/* 1160 */     if ((opacity instanceof Float)) {
/* 1161 */       JideSwingUtilities.setWindowOpacity(container, ((Float)opacity).floatValue());
/*      */     }
/* 1163 */     Object shape = getClientProperty("windowShape");
/* 1164 */     if ((shape instanceof Shape)) {
/* 1165 */       JideSwingUtilities.setWindowShape(container, (Shape)shape);
/*      */     }
/*      */ 
/* 1168 */     return container;
/*      */   }
/*      */ 
/*      */   protected ResizablePanel createLightweightPopupContainer(Component owner)
/*      */   {
/* 1181 */     ResizablePanel panel = new ResizablePanel()
/*      */     {
/*      */       protected Resizable createResizable() {
/* 1184 */         return new Resizable(this)
/*      */         {
/*      */           public void resizing(int resizeCorner, int newX, int newY, int newW, int newH) {
/* 1187 */             JidePopup.3.this.setBounds(newX, newY, newW, newH);
/*      */           }
/*      */         };
/*      */       }
/*      */     };
/* 1192 */     panel.setVisible(false);
/* 1193 */     panel.setOpaque(false);
/* 1194 */     panel.setLayout(new BorderLayout());
/* 1195 */     panel.add(this);
/* 1196 */     return panel;
/*      */   }
/*      */ 
/*      */   protected void installListeners()
/*      */   {
/* 1235 */     SwingUtilities.invokeLater(new Runnable() {
/*      */       public void run() {
/* 1237 */         JidePopup.this.addMouseEventHandler();
/*      */       }
/*      */     });
/* 1240 */     this._componentListener = new ComponentAdapter()
/*      */     {
/*      */       public void componentHidden(ComponentEvent e) {
/* 1243 */         JidePopup.this.hidePopup();
/*      */       }
/*      */     };
/* 1246 */     this._escapeActionListener = new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/* 1248 */         Component owner = JidePopup.this.getActualOwner();
/* 1249 */         JidePopup.this.hidePopupImmediately(true);
/* 1250 */         if (owner != null)
/* 1251 */           owner.requestFocus();
/*      */       }
/*      */     };
/* 1255 */     registerKeyboardAction(this._escapeActionListener, KeyStroke.getKeyStroke(27, 0), 1);
/* 1256 */     if (this._popupType == 2) {
/* 1257 */       this._window.addComponentListener(this._componentListener);
/* 1258 */       this._windowListener = new WindowAdapter()
/*      */       {
/*      */         public void windowClosing(WindowEvent e) {
/* 1261 */           Component owner = JidePopup.this.getActualOwner();
/* 1262 */           JidePopup.this.hidePopup();
/* 1263 */           if (owner != null)
/* 1264 */             owner.requestFocus();
/*      */         }
/*      */       };
/* 1268 */       this._window.addWindowListener(this._windowListener);
/*      */     }
/*      */ 
/* 1271 */     Component owner = getActualOwner();
/* 1272 */     if (owner != null) {
/* 1273 */       this._ownerComponentListener = new ComponentAdapter()
/*      */       {
/*      */         public void componentHidden(ComponentEvent e) {
/* 1276 */           JidePopup.this.ancestorHidden();
/*      */         }
/*      */ 
/*      */         public void componentMoved(ComponentEvent e)
/*      */         {
/*      */           try {
/* 1282 */             if ((JidePopup.this._actualOwnerLocation == null) || (JidePopup.this._actualOwner == null) || (!JidePopup.this._actualOwner.getLocationOnScreen().equals(JidePopup.this._actualOwnerLocation)))
/* 1283 */               JidePopup.this.ancestorMoved();
/*      */           }
/*      */           catch (Exception ex)
/*      */           {
/*      */           }
/*      */         }
/*      */       };
/* 1291 */       owner.addComponentListener(this._ownerComponentListener);
/* 1292 */       this._hierarchyListener = new HierarchyListener() {
/*      */         public void hierarchyChanged(HierarchyEvent e) {
/* 1294 */           JidePopup.this.ancestorHidden();
/*      */         }
/*      */       };
/* 1297 */       owner.addHierarchyListener(this._hierarchyListener);
/*      */     }
/*      */ 
/* 1300 */     this._popupResizeListener = new ComponentAdapter()
/*      */     {
/*      */       public void componentResized(ComponentEvent e) {
/* 1303 */         JidePopup.this.removeComponentListener(JidePopup.this._popupResizeListener);
/* 1304 */         JidePopup.this.contentResized();
/* 1305 */         JidePopup.this.addComponentListener(JidePopup.this._popupResizeListener);
/*      */       }
/*      */     };
/* 1308 */     addComponentListener(this._popupResizeListener);
/*      */   }
/*      */ 
/*      */   protected void contentResized()
/*      */   {
/* 1313 */     packPopup();
/*      */   }
/*      */ 
/*      */   protected void installBorder() {
/* 1317 */     if (getPopupBorder() != null) {
/* 1318 */       if (isResizable()) {
/* 1319 */         this._resizableSupport.getResizable().setResizableCorners(255);
/*      */       }
/*      */       else {
/* 1322 */         this._resizableSupport.getResizable().setResizableCorners(0);
/*      */       }
/* 1324 */       this._resizableSupport.setBorder(getPopupBorder());
/*      */     }
/* 1327 */     else if (isDetached()) {
/* 1328 */       if (isResizable()) {
/* 1329 */         this._resizableSupport.getResizable().setResizableCorners(255);
/*      */       }
/*      */       else {
/* 1332 */         this._resizableSupport.getResizable().setResizableCorners(0);
/*      */       }
/* 1334 */       this._resizableSupport.setBorder(UIDefaultsLookup.getBorder("Resizable.resizeBorder"));
/*      */     }
/*      */     else {
/* 1337 */       if (isResizable()) {
/* 1338 */         this._resizableSupport.getResizable().setResizableCorners(56);
/*      */       }
/*      */       else {
/* 1341 */         this._resizableSupport.getResizable().setResizableCorners(0);
/*      */       }
/* 1343 */       if (!"comboBox".equals(getClientProperty("popupType")))
/* 1344 */         this._resizableSupport.setBorder(UIDefaultsLookup.getBorder("PopupMenu.border"));
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void showPopupImmediately()
/*      */   {
/* 1351 */     if (this._popupType == 0) {
/* 1352 */       if (this._panel == null) {
/* 1353 */         return;
/*      */       }
/*      */ 
/* 1356 */       this._panel.applyComponentOrientation(getComponentOrientation());
/*      */ 
/* 1358 */       firePopupMenuWillBecomeVisible();
/*      */ 
/* 1360 */       if (!this._panel.isVisible()) {
/* 1361 */         packPopup();
/* 1362 */         this._panel.setVisible(true);
/*      */       }
/*      */ 
/* 1365 */       firePropertyChange("visible", Boolean.FALSE, Boolean.TRUE);
/*      */ 
/* 1367 */       if ((isFocusable()) || (getDefaultFocusComponent() != null))
/*      */       {
/* 1369 */         if (getDefaultFocusComponent() != null) {
/* 1370 */           Runnable runnable = new Runnable() {
/*      */             public void run() {
/* 1372 */               Component c = JidePopup.this.getDefaultFocusComponent();
/* 1373 */               if ((c instanceof JComponent))
/* 1374 */                 ((JComponent)c).requestFocus(true);
/*      */             }
/*      */           };
/* 1378 */           SwingUtilities.invokeLater(runnable);
/*      */         }
/*      */       }
/*      */     }
/* 1382 */     else if (this._popupType == 2) {
/* 1383 */       if (this._window == null) {
/* 1384 */         return;
/*      */       }
/* 1386 */       this._window.applyComponentOrientation(getComponentOrientation());
/*      */ 
/* 1388 */       firePopupMenuWillBecomeVisible();
/*      */ 
/* 1391 */       if ((!isFocusable()) && (getDefaultFocusComponent() == null)) {
/* 1392 */         this._window.setFocusableWindowState(false);
/*      */       }
/*      */       else {
/* 1395 */         setFocusCycleRoot(true);
/*      */       }
/*      */ 
/* 1398 */       if (!this._window.isVisible()) {
/* 1399 */         this._window.pack();
/* 1400 */         this._window.setVisible(true);
/*      */       }
/*      */ 
/* 1403 */       firePropertyChange("visible", Boolean.FALSE, Boolean.TRUE);
/*      */ 
/* 1405 */       if ((isFocusable()) || (getDefaultFocusComponent() != null))
/*      */       {
/* 1407 */         this._window.setFocusable(true);
/* 1408 */         if (getDefaultFocusComponent() != null) {
/* 1409 */           Runnable runnable = new Runnable() {
/*      */             public void run() {
/* 1411 */               Component c = JidePopup.this.getDefaultFocusComponent();
/* 1412 */               if ((c instanceof JComponent))
/* 1413 */                 ((JComponent)c).requestFocus(true);
/*      */             }
/*      */           };
/* 1417 */           SwingUtilities.invokeLater(runnable);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1422 */     if (getTimeout() != 0)
/* 1423 */       startTimeoutTimer();
/*      */   }
/*      */ 
/*      */   protected void movePopup()
/*      */   {
/* 1428 */     if ((isPopupVisible()) && 
/* 1429 */       (!isDetached()) && (this._actualOwner != null))
/* 1430 */       if (this._insets != null) {
/* 1431 */         showPopup(this._insets, this._actualOwner);
/*      */       }
/* 1433 */       else if (this._actualOwnerLocation != null) {
/* 1434 */         Point newLocation = this._actualOwner.getLocationOnScreen();
/* 1435 */         Point p = null;
/* 1436 */         if (this._popupType == 0) {
/* 1437 */           p = this._panel.getLocationOnScreen();
/*      */         }
/* 1439 */         else if (this._popupType == 2) {
/* 1440 */           p = this._window.getLocationOnScreen();
/*      */         }
/* 1442 */         if (p != null) {
/* 1443 */           p.x += newLocation.x - this._actualOwnerLocation.x;
/* 1444 */           p.y += newLocation.y - this._actualOwnerLocation.y;
/* 1445 */           showPopup(p.x, p.y, this._actualOwner);
/*      */         }
/*      */       }
/*      */   }
/*      */ 
/*      */   protected void endDragging()
/*      */   {
/* 1465 */     this._isDragging = false;
/* 1466 */     if (this._popupType == 0) {
/* 1467 */       this._currentPanel = null;
/*      */     }
/* 1469 */     else if (this._popupType == 2) {
/* 1470 */       if (((this._currentWindow instanceof JWindow)) && (((JWindow)this._currentWindow).getGlassPane() != null)) {
/* 1471 */         ((JWindow)this._currentWindow).getGlassPane().setVisible(false);
/* 1472 */         ((JWindow)this._currentWindow).getGlassPane().setCursor(Cursor.getDefaultCursor());
/*      */       }
/* 1474 */       else if (((this._currentWindow instanceof JDialog)) && (((JDialog)this._currentWindow).getGlassPane() != null)) {
/* 1475 */         ((JDialog)this._currentWindow).getGlassPane().setVisible(false);
/* 1476 */         ((JDialog)this._currentWindow).getGlassPane().setCursor(Cursor.getDefaultCursor());
/*      */       }
/* 1478 */       this._currentWindow = null;
/*      */     }
/* 1480 */     this._relativeX = 0.0D;
/* 1481 */     this._relativeY = 0.0D;
/*      */   }
/*      */ 
/*      */   protected void beginDragging(JComponent f, int mouseX, int mouseY, double relativeX, double relativeY)
/*      */   {
/* 1486 */     this._relativeX = relativeX;
/* 1487 */     this._relativeY = relativeY;
/*      */ 
/* 1489 */     Component owner = getActualOwner();
/* 1490 */     if (this._popupType == 0) {
/* 1491 */       this._currentPanel = this._panel;
/* 1492 */       this._isDragging = true;
/* 1493 */       if ((isDetached()) && (owner != null)) {
/* 1494 */         this._startPoint = owner.getLocationOnScreen();
/* 1495 */         this._startPoint.y += owner.getHeight();
/*      */       }
/*      */       else {
/* 1498 */         this._startPoint = this._currentPanel.getLocationOnScreen();
/*      */       }
/*      */     }
/* 1501 */     else if (this._popupType == 2) {
/* 1502 */       if ((f.getTopLevelAncestor() instanceof JWindow))
/* 1503 */         this._currentWindow = ((JWindow)f.getTopLevelAncestor());
/* 1504 */       if ((f.getTopLevelAncestor() instanceof JDialog)) {
/* 1505 */         this._currentWindow = ((JDialog)f.getTopLevelAncestor());
/*      */       }
/* 1507 */       if (((this._currentWindow instanceof JWindow)) && (((JWindow)this._currentWindow).getGlassPane() != null)) {
/* 1508 */         ((JWindow)this._currentWindow).getGlassPane().setVisible(true);
/* 1509 */         ((JWindow)this._currentWindow).getGlassPane().setCursor(Cursor.getPredefinedCursor(13));
/*      */       }
/* 1511 */       else if (((this._currentWindow instanceof JDialog)) && (((JDialog)this._currentWindow).getGlassPane() != null)) {
/* 1512 */         ((JDialog)this._currentWindow).getGlassPane().setVisible(true);
/* 1513 */         ((JDialog)this._currentWindow).getGlassPane().setCursor(Cursor.getPredefinedCursor(13));
/*      */       }
/*      */ 
/* 1516 */       this._isDragging = true;
/* 1517 */       if ((isDetached()) && (owner != null)) {
/* 1518 */         this._startPoint = owner.getLocationOnScreen();
/* 1519 */         this._startPoint.y += owner.getHeight();
/*      */       }
/*      */       else {
/* 1522 */         this._startPoint = this._currentWindow.getLocationOnScreen();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected boolean isDragging() {
/* 1528 */     return this._isDragging;
/*      */   }
/*      */ 
/*      */   static void convertPointToScreen(Point p, Component c, boolean startInFloat)
/*      */   {
/*      */     do
/*      */     {
/*      */       int y;
/*      */       int x;
/*      */       int y;
/* 1535 */       if ((c instanceof JComponent)) {
/* 1536 */         int x = c.getX();
/* 1537 */         y = c.getY();
/*      */       }
/* 1539 */       else if (((c instanceof Applet)) || (startInFloat ? (c instanceof Window) : (c instanceof JFrame))) {
/*      */         try {
/* 1541 */           Point pp = c.getLocationOnScreen();
/* 1542 */           x = pp.x;
/* 1543 */           y = pp.y;
/*      */         }
/*      */         catch (IllegalComponentStateException icse) {
/* 1546 */           int x = c.getX();
/* 1547 */           int y = c.getY();
/*      */         }
/*      */       }
/*      */       else {
/* 1551 */         x = c.getX();
/* 1552 */         y = c.getY();
/*      */       }
/*      */ 
/* 1555 */       p.x += x;
/* 1556 */       p.y += y;
/*      */ 
/* 1558 */       if (startInFloat ? (c instanceof Window) : (c instanceof JFrame)) break; if ((c instanceof Applet))
/*      */         break;
/* 1560 */       c = c.getParent();
/*      */     }
/* 1562 */     while (c != null);
/*      */   }
/*      */ 
/*      */   protected void drag(JComponent f, int newX, int newY, int mouseModifiers)
/*      */   {
/* 1567 */     if (this._popupType == 0) {
/* 1568 */       int x = newX - (int)(this._currentPanel.getWidth() * this._relativeX);
/* 1569 */       int y = newY - (int)(this._currentPanel.getHeight() * this._relativeY);
/* 1570 */       Rectangle bounds = new Rectangle(x, y, this._currentPanel.getWidth(), this._currentPanel.getHeight());
/*      */ 
/* 1572 */       Rectangle screenBounds = PortingUtils.getScreenBounds(this._currentPanel, true);
/* 1573 */       if (bounds.y + bounds.height > screenBounds.y + screenBounds.height) {
/* 1574 */         bounds.y = (screenBounds.y + screenBounds.height - bounds.height);
/*      */       }
/* 1576 */       if (bounds.y < screenBounds.y) {
/* 1577 */         bounds.y = screenBounds.y;
/*      */       }
/*      */ 
/* 1580 */       if ((isAttachable()) && (isWithinAroundArea(new Point(x, y), this._startPoint))) {
/* 1581 */         Point p = new Point(this._startPoint);
/* 1582 */         SwingUtilities.convertPointFromScreen(p, this._currentPanel.getParent());
/* 1583 */         this._currentPanel.setLocation(p);
/* 1584 */         setDetached(false);
/*      */       }
/*      */       else {
/* 1587 */         Point p = new Point(x, y);
/* 1588 */         SwingUtilities.convertPointFromScreen(p, this._currentPanel.getParent());
/* 1589 */         this._currentPanel.setLocation(p);
/* 1590 */         setDetached(true);
/*      */       }
/*      */     }
/* 1593 */     else if (this._popupType == 2) {
/* 1594 */       int x = newX - (int)(this._currentWindow.getWidth() * this._relativeX);
/* 1595 */       int y = newY - (int)(this._currentWindow.getHeight() * this._relativeY);
/* 1596 */       Rectangle bounds = new Rectangle(x, y, this._currentWindow.getWidth(), this._currentWindow.getHeight());
/*      */ 
/* 1598 */       Rectangle screenBounds = PortingUtils.getScreenBounds(this._currentWindow, true);
/* 1599 */       if (bounds.y + bounds.height > screenBounds.y + screenBounds.height) {
/* 1600 */         bounds.y = (screenBounds.y + screenBounds.height - bounds.height);
/*      */       }
/* 1602 */       if (bounds.y < screenBounds.y) {
/* 1603 */         bounds.y = screenBounds.y;
/*      */       }
/*      */ 
/* 1606 */       if ((isAttachable()) && (isWithinAroundArea(new Point(x, y), this._startPoint))) {
/* 1607 */         this._currentWindow.setLocation(this._startPoint);
/* 1608 */         setDetached(false);
/*      */       }
/*      */       else {
/* 1611 */         this._currentWindow.setLocation(x, y);
/* 1612 */         setDetached(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   boolean isWithinAroundArea(Point p, Point newPoint)
/*      */   {
/* 1620 */     if ((getBackToOriginalInsets().left == 0) && (getBackToOriginalInsets().top == 0) && (getBackToOriginalInsets().right == 0) && (getBackToOriginalInsets().bottom == 0)) {
/* 1621 */       return false;
/*      */     }
/* 1623 */     Rectangle rect = new Rectangle(p.x - getBackToOriginalInsets().left, p.y - getBackToOriginalInsets().top, p.x + getBackToOriginalInsets().right, p.y + getBackToOriginalInsets().bottom);
/* 1624 */     return rect.contains(newPoint);
/*      */   }
/*      */ 
/*      */   protected void handleMousePressed(MouseEvent e)
/*      */   {
/* 1703 */     Component c = e.getComponent();
/* 1704 */     if (c == null) {
/* 1705 */       return;
/*      */     }
/* 1707 */     Component component = SwingUtilities.getDeepestComponentAt(c, e.getX(), e.getY());
/* 1708 */     if (!isClickOnPopup(e)) {
/* 1709 */       if (isExcludedComponent(component)) {
/* 1710 */         return;
/*      */       }
/* 1712 */       ancestorHidden();
/*      */     }
/* 1714 */     else if (isPopupVisible()) {
/* 1715 */       Point point = SwingUtilities.convertPoint(component, e.getPoint(), this);
/*      */ 
/* 1717 */       Rectangle startingBounds = null;
/*      */ 
/* 1719 */       if (this._popupType == 0) {
/* 1720 */         startingBounds = this._panel.getBounds();
/* 1721 */         Container parent = this._panel.getParent();
/* 1722 */         if ((SystemInfo.isJdk15Above()) && 
/* 1723 */           (isClickOnPopup(e)) && (parent.getComponentZOrder(this._panel) != 0)) {
/* 1724 */           parent.setComponentZOrder(this._panel, 0);
/* 1725 */           parent.repaint();
/*      */         }
/*      */ 
/*      */       }
/* 1729 */       else if (this._popupType == 2) {
/* 1730 */         Window sourceWindow = SwingUtilities.getWindowAncestor(component);
/* 1731 */         if (sourceWindow == this._window) {
/* 1732 */           startingBounds = this._window.getBounds();
/* 1733 */           if (KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow() != this._window) {
/* 1734 */             this._window.toFront();
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 1739 */       if (startingBounds != null) {
/* 1740 */         this._relativeX = (point.x / startingBounds.width);
/* 1741 */         this._relativeY = (point.y / startingBounds.height);
/*      */ 
/* 1743 */         Point screenPoint = new Point(e.getX(), e.getY());
/* 1744 */         convertPointToScreen(screenPoint, component, true);
/*      */ 
/* 1747 */         Component gripper = getUI().getGripper();
/* 1748 */         if (((gripper instanceof Container)) && ((gripper == component) || (((Container)gripper).isAncestorOf(component)))) {
/* 1749 */           beginDragging(this, screenPoint.x, screenPoint.y, this._relativeX, this._relativeY);
/* 1750 */           e.consume();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void handleMouseReleased(MouseEvent e) {
/* 1757 */     if (isDragging()) {
/* 1758 */       endDragging();
/* 1759 */       e.consume();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void handleMouseDragged(MouseEvent e) {
/* 1764 */     if (isDragging()) {
/* 1765 */       Point screenPoint = e.getPoint();
/* 1766 */       if ((e.getSource() instanceof Component)) {
/* 1767 */         convertPointToScreen(screenPoint, (Component)e.getSource(), true);
/* 1768 */         drag(null, screenPoint.x, screenPoint.y, e.getModifiersEx());
/* 1769 */         e.consume();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void handleMouseEntered(MouseEvent e) {
/* 1775 */     if (this._popupType == 0) {
/* 1776 */       if ((this._panel != null) && ((e.getSource() instanceof Component)) && (this._panel.isAncestorOf((Component)e.getSource())) && (getTimeout() != 0))
/*      */       {
/* 1778 */         stopTimeoutTimer();
/*      */       }
/*      */     }
/* 1781 */     else if ((this._popupType == 2) && 
/* 1782 */       (this._window != null) && ((e.getSource() instanceof Component)) && (this._window.isAncestorOf((Component)e.getSource())) && (getTimeout() != 0))
/*      */     {
/* 1784 */       stopTimeoutTimer();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void handleMouseExited(MouseEvent e)
/*      */   {
/* 1790 */     if (this._popupType == 0) {
/* 1791 */       if ((this._panel != null) && ((e.getSource() instanceof Component)) && (this._panel.isAncestorOf((Component)e.getSource())) && (getTimeout() != 0))
/*      */       {
/* 1793 */         startTimeoutTimer();
/*      */       }
/*      */     }
/* 1796 */     else if ((this._popupType == 2) && 
/* 1797 */       (this._window != null) && ((e.getSource() instanceof Component)) && (this._window.isAncestorOf((Component)e.getSource())) && (getTimeout() != 0))
/*      */     {
/* 1799 */       startTimeoutTimer();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static boolean doUnpostPopupOnDeactivation()
/*      */   {
/* 1808 */     if (!checkedUnpostPopup) {
/* 1809 */       unpostPopup = ((Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*      */       {
/*      */         public Object run() {
/* 1812 */           String pKey = "sun.swing.unpostPopupsOnWindowDeactivation";
/* 1813 */           String value = System.getProperty(pKey, "true");
/* 1814 */           return Boolean.valueOf(value);
/*      */         }
/*      */       })).booleanValue();
/*      */ 
/* 1818 */       checkedUnpostPopup = true;
/*      */     }
/* 1820 */     return unpostPopup;
/*      */   }
/*      */ 
/*      */   protected void handleWindowEvent(WindowEvent e) {
/* 1824 */     Component owner = getActualOwner();
/* 1825 */     if ((e.getSource() != getTopLevelAncestor()) && (e.getWindow() != null) && ((e.getWindow() == owner) || (e.getWindow().isAncestorOf(owner))))
/* 1826 */       if ((e.getID() == 201) || (e.getID() == 203)) {
/* 1827 */         hidePopup(true);
/*      */       }
/* 1836 */       else if ((isTransient()) && (e.getID() == 206) && (!(e.getWindow() instanceof EmbeddedFrame)))
/*      */       {
/* 1840 */         if (doUnpostPopupOnDeactivation()) {
/* 1841 */           Window oppositeWindow = e.getOppositeWindow();
/* 1842 */           if (oppositeWindow == getTopLevelAncestor()) {
/* 1843 */             return;
/*      */           }
/* 1845 */           if ((oppositeWindow instanceof RootPaneContainer)) {
/* 1846 */             JComponent realParent = getRealParent((RootPaneContainer)oppositeWindow);
/* 1847 */             if ((realParent != null) && (realParent.getTopLevelAncestor() == getTopLevelAncestor())) {
/* 1848 */               return;
/*      */             }
/*      */           }
/* 1851 */           hidePopup(true);
/*      */         }
/*      */       }
/*      */   }
/*      */ 
/*      */   protected JComponent getRealParent(RootPaneContainer rootPaneContainer)
/*      */   {
/* 1858 */     JComponent c = JideSwingUtilities.getFirstJComponent(rootPaneContainer);
/* 1859 */     if (c != null) {
/* 1860 */       Object clientProperty = c.getClientProperty("JidePopup.actualOwner");
/* 1861 */       if ((clientProperty instanceof JComponent)) {
/* 1862 */         return (JComponent)clientProperty;
/*      */       }
/*      */     }
/* 1865 */     return null;
/*      */   }
/*      */ 
/*      */   protected void handleComponentEvent(ComponentEvent e)
/*      */   {
/* 1876 */     Component owner = getActualOwner();
/* 1877 */     if (!(e.getSource() instanceof Container)) {
/* 1878 */       return;
/*      */     }
/* 1880 */     Container container = (Container)e.getSource();
/* 1881 */     if ((e.getID() == 103) && ((container == owner) || (container.isAncestorOf(owner)))) {
/* 1882 */       ancestorHidden();
/*      */     }
/* 1884 */     else if ((e.getID() == 100) && ((container == owner) || (container.isAncestorOf(owner))))
/*      */     {
/*      */       try
/*      */       {
/* 1888 */         if ((this._actualOwnerLocation == null) || (this._actualOwner == null) || (!this._actualOwner.getLocationOnScreen().equals(this._actualOwnerLocation)))
/* 1889 */           ancestorMoved();
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void ancestorHidden()
/*      */   {
/* 1903 */     if (isTransient())
/* 1904 */       hidePopupImmediately(true);
/*      */   }
/*      */ 
/*      */   protected void ancestorMoved()
/*      */   {
/* 1914 */     if (getDefaultMoveOperation() == 1) {
/* 1915 */       movePopup();
/*      */     }
/* 1917 */     else if ((getDefaultMoveOperation() == 0) && 
/* 1918 */       (isTransient()))
/* 1919 */       hidePopupImmediately(true);
/*      */   }
/*      */ 
/*      */   public void hidePopup()
/*      */   {
/* 1925 */     hidePopup(false);
/*      */   }
/*      */ 
/*      */   public void hidePopup(boolean cancelled) {
/* 1929 */     if (!isPopupVisible()) {
/* 1930 */       return;
/*      */     }
/* 1932 */     hidePopupImmediately(cancelled);
/*      */   }
/*      */ 
/*      */   public boolean isPopupVisible() {
/* 1936 */     if (this._popupType == 0) {
/* 1937 */       return (this._panel != null) && (this._panel.isVisible());
/*      */     }
/* 1939 */     if (this._popupType == 2) {
/* 1940 */       return (this._window != null) && (this._window.isShowing());
/*      */     }
/* 1942 */     return false;
/*      */   }
/*      */ 
/*      */   public Rectangle getPopupBounds() {
/* 1946 */     if (this._popupType == 0) {
/* 1947 */       return isPopupVisible() ? this._panel.getBounds() : null;
/*      */     }
/* 1949 */     if (this._popupType == 2) {
/* 1950 */       return isPopupVisible() ? this._window.getBounds() : null;
/*      */     }
/* 1952 */     return null;
/*      */   }
/*      */ 
/*      */   public void hidePopupImmediately(boolean cancelled) {
/* 1956 */     Component owner = getActualOwner();
/* 1957 */     if (owner != null) {
/* 1958 */       owner.removeHierarchyListener(this._hierarchyListener);
/* 1959 */       this._hierarchyListener = null;
/* 1960 */       owner.removeComponentListener(this._ownerComponentListener);
/* 1961 */       this._ownerComponentListener = null;
/*      */     }
/* 1963 */     if (this._escapeActionListener != null) {
/* 1964 */       unregisterKeyboardAction(KeyStroke.getKeyStroke(27, 0));
/* 1965 */       this._escapeActionListener = null;
/*      */     }
/*      */ 
/* 1968 */     int insetWidth = 0;
/* 1969 */     int insetHeight = 0;
/* 1970 */     Container container = getParent();
/* 1971 */     while (container != null) {
/* 1972 */       Insets insets = container.getInsets();
/* 1973 */       if (insets != null) {
/* 1974 */         insetWidth += insets.left + insets.right;
/* 1975 */         insetHeight += insets.top + insets.bottom;
/*      */       }
/* 1977 */       if ((container == this._window) || (container == this._panel)) {
/*      */         break;
/*      */       }
/* 1980 */       container = container.getParent();
/*      */     }
/* 1982 */     if (this._window != null) {
/* 1983 */       this._window.removeWindowListener(this._windowListener);
/* 1984 */       this._windowListener = null;
/* 1985 */       this._window.removeComponentListener(this._componentListener);
/* 1986 */       this._componentListener = null;
/* 1987 */       this._window.getContentPane().remove(this);
/* 1988 */       if (cancelled) {
/* 1989 */         firePopupMenuCanceled();
/*      */       }
/* 1991 */       firePopupMenuWillBecomeInvisible();
/*      */     }
/* 1993 */     if (this._panel != null) {
/* 1994 */       this._panel.remove(this);
/* 1995 */       if (cancelled) {
/* 1996 */         firePopupMenuCanceled();
/*      */       }
/* 1998 */       firePopupMenuWillBecomeInvisible();
/*      */     }
/* 2000 */     if (this._popupResizeListener != null) {
/* 2001 */       removeComponentListener(this._popupResizeListener);
/* 2002 */       this._popupResizeListener = null;
/*      */     }
/*      */ 
/* 2005 */     if (owner != null) {
/* 2006 */       owner.requestFocus();
/*      */     }
/*      */ 
/* 2009 */     if (this._window != null) {
/* 2010 */       this._previousSize = this._window.getSize();
/* 2011 */       this._window.setVisible(false);
/* 2012 */       this._window.removeAll();
/* 2013 */       this._window.dispose();
/* 2014 */       this._window = null;
/* 2015 */       firePropertyChange("visible", Boolean.TRUE, Boolean.FALSE);
/*      */     }
/*      */ 
/* 2018 */     if (this._panel != null) {
/* 2019 */       this._previousSize = this._panel.getSize();
/* 2020 */       this._panel.setVisible(false);
/* 2021 */       Container parent = this._panel.getParent();
/* 2022 */       if (parent != null) {
/* 2023 */         parent.remove(this._panel);
/*      */       }
/* 2025 */       this._panel = null;
/* 2026 */       firePropertyChange("visible", Boolean.TRUE, Boolean.FALSE);
/*      */     }
/* 2028 */     if (this._previousSize != null) {
/* 2029 */       this._previousSize.width -= insetWidth;
/* 2030 */       this._previousSize.height -= insetHeight;
/*      */     }
/*      */ 
/* 2033 */     SwingUtilities.invokeLater(new Runnable() {
/*      */       public void run() {
/* 2035 */         JidePopup.this.removeMouseEventHandler();
/*      */       }
/*      */     });
/* 2039 */     this._resizableSupport = null;
/* 2040 */     this._owner = null;
/* 2041 */     this._actualOwner = null;
/* 2042 */     this._actualOwnerLocation = null;
/*      */   }
/*      */ 
/*      */   public void hidePopupImmediately()
/*      */   {
/* 2049 */     hidePopupImmediately(false);
/*      */   }
/*      */ 
/*      */   private void addMouseEventHandler()
/*      */   {
/* 2056 */     if (shouldAWTEventListenerBeUsed()) {
/* 2057 */       return;
/*      */     }
/*      */ 
/* 2060 */     if (this._awtEventListener == null)
/* 2061 */       this._awtEventListener = new AWTEventListener() {
/*      */         public void eventDispatched(AWTEvent event) {
/* 2063 */           if ("sun.awt.UngrabEvent".equals(event.getClass().getName()))
/*      */           {
/* 2066 */             JidePopup.this.hidePopupImmediately(true);
/* 2067 */             return;
/*      */           }
/*      */ 
/* 2070 */           if ((event instanceof MouseEvent)) {
/* 2071 */             if (event.getID() == 501) {
/* 2072 */               JidePopup.this.handleMousePressed((MouseEvent)event);
/*      */             }
/* 2074 */             else if (event.getID() == 506) {
/* 2075 */               JidePopup.this.handleMouseDragged((MouseEvent)event);
/*      */             }
/* 2077 */             else if (event.getID() == 502) {
/* 2078 */               JidePopup.this.handleMouseReleased((MouseEvent)event);
/*      */             }
/* 2080 */             else if (event.getID() == 504) {
/* 2081 */               JidePopup.this.handleMouseEntered((MouseEvent)event);
/*      */             }
/* 2083 */             else if (event.getID() == 505) {
/* 2084 */               JidePopup.this.handleMouseExited((MouseEvent)event);
/*      */             }
/*      */           }
/* 2087 */           else if ((event instanceof WindowEvent)) {
/* 2088 */             JidePopup.this.handleWindowEvent((WindowEvent)event);
/*      */           }
/* 2090 */           else if ((event instanceof ComponentEvent))
/* 2091 */             JidePopup.this.handleComponentEvent((ComponentEvent)event);
/*      */         }
/*      */       };
/*      */     try
/*      */     {
/* 2097 */       AccessController.doPrivileged(new PrivilegedAction()
/*      */       {
/*      */         public Object run() {
/* 2100 */           Toolkit.getDefaultToolkit().addAWTEventListener(JidePopup.this._awtEventListener, 113L);
/*      */ 
/* 2102 */           return null;
/*      */         }
/*      */       });
/*      */     }
/*      */     catch (SecurityException e) {
/* 2108 */       throw new RuntimeException(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected boolean shouldAWTEventListenerBeUsed()
/*      */   {
/* 2118 */     return (SecurityUtils.isAWTEventListenerDisabled()) || ("true".equals(SecurityUtils.getProperty("jide.disableAWTEventListener", "false")));
/*      */   }
/*      */ 
/*      */   private void removeMouseEventHandler()
/*      */   {
/* 2125 */     if (shouldAWTEventListenerBeUsed()) {
/* 2126 */       return;
/*      */     }
/*      */     try
/*      */     {
/* 2130 */       AccessController.doPrivileged(new PrivilegedAction()
/*      */       {
/*      */         public Object run() {
/* 2133 */           Toolkit.getDefaultToolkit().removeAWTEventListener(JidePopup.this._awtEventListener);
/* 2134 */           JidePopup.access$302(JidePopup.this, null);
/* 2135 */           return null;
/*      */         }
/*      */       });
/*      */     }
/*      */     catch (SecurityException e) {
/* 2141 */       throw new RuntimeException(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Component getOwner() {
/* 2146 */     return this._owner;
/*      */   }
/*      */ 
/*      */   public void setOwner(Component owner) {
/* 2150 */     if (this._owner != owner) {
/* 2151 */       Component old = this._owner;
/* 2152 */       this._owner = owner;
/* 2153 */       firePropertyChange("owner", old, this._owner);
/* 2154 */       removeExcludedComponent(old);
/* 2155 */       addExcludedComponent(this._owner);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isMovable()
/*      */   {
/* 2166 */     return this._movable;
/*      */   }
/*      */ 
/*      */   public void setMovable(boolean movable)
/*      */   {
/* 2175 */     boolean old = this._movable;
/* 2176 */     if (old != movable) {
/* 2177 */       this._movable = movable;
/* 2178 */       firePropertyChange("movable", old, this._movable);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isResizable()
/*      */   {
/* 2195 */     return this._resizable;
/*      */   }
/*      */ 
/*      */   public void setResizable(boolean resizable)
/*      */   {
/* 2204 */     if (this._resizable != resizable) {
/* 2205 */       boolean old = this._resizable;
/* 2206 */       this._resizable = resizable;
/* 2207 */       firePropertyChange("resizable", old, this._resizable);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isAttachable()
/*      */   {
/* 2217 */     return this._attachable;
/*      */   }
/*      */ 
/*      */   public void setAttachable(boolean attachable)
/*      */   {
/* 2226 */     if (this._attachable != attachable) {
/* 2227 */       boolean old = this._attachable;
/* 2228 */       this._attachable = attachable;
/* 2229 */       firePropertyChange("attachable", old, this._attachable);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isDetached()
/*      */   {
/* 2246 */     return this._detached;
/*      */   }
/*      */ 
/*      */   public void setDetached(boolean detached)
/*      */   {
/* 2255 */     if (this._detached != detached) {
/* 2256 */       boolean old = this._detached;
/* 2257 */       this._detached = detached;
/* 2258 */       firePropertyChange("detacted", old, this._detached);
/* 2259 */       if (this._resizableSupport != null)
/* 2260 */         if (this._detached) {
/* 2261 */           if (getPopupBorder() == null) {
/* 2262 */             this._resizableSupport.setBorder(UIDefaultsLookup.getBorder("Resizable.resizeBorder"));
/*      */           }
/*      */           else {
/* 2265 */             this._resizableSupport.setBorder(getPopupBorder());
/*      */           }
/* 2267 */           if (isResizable()) {
/* 2268 */             this._resizableSupport.getResizable().setResizableCorners(255);
/*      */           }
/*      */           else
/* 2271 */             this._resizableSupport.getResizable().setResizableCorners(0);
/*      */         }
/*      */         else
/*      */         {
/* 2275 */           if (getPopupBorder() == null) {
/* 2276 */             if (!"comboBox".equals(getClientProperty("popupType"))) {
/* 2277 */               this._resizableSupport.setBorder(UIDefaultsLookup.getBorder("PopupMenu.border"));
/*      */             }
/*      */           }
/*      */           else {
/* 2281 */             this._resizableSupport.setBorder(getPopupBorder());
/*      */           }
/* 2283 */           if (isResizable()) {
/* 2284 */             this._resizableSupport.getResizable().setResizableCorners(56);
/*      */           }
/*      */           else
/* 2287 */             this._resizableSupport.getResizable().setResizableCorners(0);
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Border getPopupBorder()
/*      */   {
/* 2300 */     return this._popupBorder;
/*      */   }
/*      */ 
/*      */   public void setPopupBorder(Border popupBorder)
/*      */   {
/* 2309 */     this._popupBorder = popupBorder;
/*      */   }
/*      */ 
/*      */   public boolean isTransient()
/*      */   {
/* 2320 */     return this._transient;
/*      */   }
/*      */ 
/*      */   public void setTransient(boolean isTransient)
/*      */   {
/* 2330 */     boolean old = this._transient;
/* 2331 */     if (old != isTransient) {
/* 2332 */       this._transient = isTransient;
/* 2333 */       firePropertyChange("transient", old, isTransient);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getTimeout()
/*      */   {
/* 2343 */     return this._timeout;
/*      */   }
/*      */ 
/*      */   public void setTimeout(int timeout)
/*      */   {
/* 2358 */     this._timeout = timeout;
/* 2359 */     if ((this._timer != null) && (this._timer.isRunning()))
/* 2360 */       startTimeoutTimer();
/*      */   }
/*      */ 
/*      */   private void stopTimeoutTimer()
/*      */   {
/* 2365 */     if (this._timer != null) {
/* 2366 */       this._timer.stop();
/* 2367 */       this._timer = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void startTimeoutTimer()
/*      */   {
/* 2373 */     stopTimeoutTimer();
/* 2374 */     this._timer = new Timer(getTimeout(), new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/* 2376 */         JidePopup.this.hidePopup();
/*      */       }
/*      */     });
/* 2379 */     this._timer.setRepeats(false);
/* 2380 */     this._timer.start();
/*      */   }
/*      */ 
/*      */   public Component getDefaultFocusComponent()
/*      */   {
/* 2390 */     return this._defaultFocusComponent;
/*      */   }
/*      */ 
/*      */   public void setDefaultFocusComponent(Component defaultFocusComponent)
/*      */   {
/* 2401 */     this._defaultFocusComponent = defaultFocusComponent;
/*      */   }
/*      */ 
/*      */   public void addPopupMenuListener(PopupMenuListener l)
/*      */   {
/* 2415 */     this.listenerList.add(PopupMenuListener.class, l);
/*      */   }
/*      */ 
/*      */   public void removePopupMenuListener(PopupMenuListener l)
/*      */   {
/* 2426 */     this.listenerList.remove(PopupMenuListener.class, l);
/*      */   }
/*      */ 
/*      */   public PopupMenuListener[] getPopupMenuListeners()
/*      */   {
/* 2435 */     return (PopupMenuListener[])this.listenerList.getListeners(PopupMenuListener.class);
/*      */   }
/*      */ 
/*      */   public void firePopupMenuWillBecomeVisible()
/*      */   {
/* 2446 */     Object[] listeners = this.listenerList.getListenerList();
/* 2447 */     PopupMenuEvent e = null;
/* 2448 */     for (int i = listeners.length - 2; i >= 0; i -= 2)
/* 2449 */       if (listeners[i] == PopupMenuListener.class) {
/* 2450 */         if (e == null)
/* 2451 */           e = new PopupMenuEvent(this);
/* 2452 */         ((PopupMenuListener)listeners[(i + 1)]).popupMenuWillBecomeVisible(e);
/*      */       }
/*      */   }
/*      */ 
/*      */   public void firePopupMenuWillBecomeInvisible()
/*      */   {
/* 2465 */     Object[] listeners = this.listenerList.getListenerList();
/* 2466 */     PopupMenuEvent e = null;
/* 2467 */     for (int i = listeners.length - 2; i >= 0; i -= 2)
/* 2468 */       if (listeners[i] == PopupMenuListener.class) {
/* 2469 */         if (e == null)
/* 2470 */           e = new PopupMenuEvent(this);
/* 2471 */         ((PopupMenuListener)listeners[(i + 1)]).popupMenuWillBecomeInvisible(e);
/*      */       }
/*      */   }
/*      */ 
/*      */   public void firePopupMenuCanceled()
/*      */   {
/* 2484 */     Object[] listeners = this.listenerList.getListenerList();
/* 2485 */     PopupMenuEvent e = null;
/* 2486 */     for (int i = listeners.length - 2; i >= 0; i -= 2)
/* 2487 */       if (listeners[i] == PopupMenuListener.class) {
/* 2488 */         if (e == null)
/* 2489 */           e = new PopupMenuEvent(this);
/* 2490 */         ((PopupMenuListener)listeners[(i + 1)]).popupMenuCanceled(e);
/*      */       }
/*      */   }
/*      */ 
/*      */   public int getDefaultMoveOperation()
/*      */   {
/* 2502 */     return this._defaultMoveOperation;
/*      */   }
/*      */ 
/*      */   public void setDefaultMoveOperation(int defaultMoveOperation)
/*      */   {
/* 2512 */     this._defaultMoveOperation = defaultMoveOperation;
/*      */   }
/*      */ 
/*      */   public void addExcludedComponent(Component component)
/*      */   {
/* 2539 */     if ((component != null) && (!this._excludedComponents.contains(component)))
/* 2540 */       this._excludedComponents.add(component);
/*      */   }
/*      */ 
/*      */   public void removeExcludedComponent(Component component)
/*      */   {
/* 2551 */     this._excludedComponents.remove(component);
/*      */   }
/*      */ 
/*      */   public void removeAllExcludedComponents()
/*      */   {
/* 2558 */     this._excludedComponents.clear();
/*      */   }
/*      */ 
/*      */   public boolean isExcludedComponent(Component component)
/*      */   {
/* 2569 */     boolean contain = this._excludedComponents.contains(component);
/* 2570 */     if (!contain) {
/* 2571 */       for (Component c : this._excludedComponents) {
/* 2572 */         if (((c instanceof Container)) && 
/* 2573 */           (((Container)c).isAncestorOf(component))) {
/* 2574 */           return true;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2579 */       if ((component instanceof JComponent)) {
/* 2580 */         Container c = ((JComponent)component).getTopLevelAncestor();
/* 2581 */         if ((c instanceof RootPaneContainer)) {
/* 2582 */           JComponent realParent = getRealParent((RootPaneContainer)c);
/* 2583 */           if ((realParent != null) && (realParent.getTopLevelAncestor() == getTopLevelAncestor())) {
/* 2584 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 2589 */     return contain;
/*      */   }
/*      */ 
/*      */   public int getGripperLocation() {
/* 2593 */     return this._gripperLocation;
/*      */   }
/*      */ 
/*      */   public void setGripperLocation(int gripperLocation)
/*      */   {
/* 2603 */     int old = this._gripperLocation;
/* 2604 */     if (old != gripperLocation) {
/* 2605 */       this._gripperLocation = gripperLocation;
/* 2606 */       firePropertyChange("gripperLocation", old, gripperLocation);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getPopupType() {
/* 2611 */     return this._popupType;
/*      */   }
/*      */ 
/*      */   public void setPopupType(int popupType) {
/* 2615 */     if ((popupType != 0) && (popupType != 2)) {
/* 2616 */       throw new IllegalArgumentException("invalid popup type. It must be JidePopup.HEAVY_WEIGHT_POPUP or JidePopup.LIGHT_WEIGHT_POPUP.");
/*      */     }
/* 2618 */     this._popupType = popupType;
/*      */   }
/*      */ 
/*      */   public boolean isClickOnPopup(MouseEvent e)
/*      */   {
/* 2630 */     Component c = e.getComponent();
/* 2631 */     if (c == null) {
/* 2632 */       return false;
/*      */     }
/* 2634 */     Component component = SwingUtilities.getDeepestComponentAt(c, e.getX(), e.getY());
/* 2635 */     return (this._window != null) && ((this._window == component) || (this._window.isAncestorOf(component)));
/*      */   }
/*      */ 
/*      */   protected Component getActualOwner()
/*      */   {
/* 2645 */     if (this._actualOwner != null) {
/* 2646 */       return this._actualOwner;
/*      */     }
/*      */ 
/* 2649 */     return getOwner();
/*      */   }
/*      */ 
/*      */   public void setPreferredPopupSize(Dimension size)
/*      */   {
/* 2660 */     this._previousSize = size;
/*      */   }
/*      */ 
/*      */   public static boolean isPopupAncestorOf(JidePopup popup, Component c)
/*      */   {
/*      */     Container p;
/* 2665 */     if ((c == null) || ((p = c.getParent()) == null))
/* 2666 */       return false;
/*      */     Container p;
/* 2668 */     while (p != null) {
/* 2669 */       if (p == popup) {
/* 2670 */         return true;
/*      */       }
/* 2672 */       if ((p instanceof JidePopup)) {
/* 2673 */         return false;
/*      */       }
/* 2675 */       p = p.getParent();
/*      */     }
/* 2677 */     return false;
/*      */   }
/*      */ 
/*      */   protected class AccessiblePopup extends JComponent.AccessibleJComponent
/*      */     implements AccessibleValue
/*      */   {
/*      */     private static final long serialVersionUID = -1095213042773793649L;
/*      */ 
/*      */     protected AccessiblePopup()
/*      */     {
/*  636 */       super();
/*      */     }
/*      */ 
/*      */     public String getAccessibleName()
/*      */     {
/*  649 */       if (this.accessibleName != null) {
/*  650 */         return this.accessibleName;
/*      */       }
/*      */ 
/*  653 */       return JidePopup.this.getName();
/*      */     }
/*      */ 
/*      */     public AccessibleRole getAccessibleRole()
/*      */     {
/*  666 */       return AccessibleRole.SWING_COMPONENT;
/*      */     }
/*      */ 
/*      */     public AccessibleValue getAccessibleValue()
/*      */     {
/*  678 */       return this;
/*      */     }
/*      */ 
/*      */     public Number getCurrentAccessibleValue()
/*      */     {
/*  691 */       if (isVisible()) {
/*  692 */         return Integer.valueOf(1);
/*      */       }
/*      */ 
/*  695 */       return Integer.valueOf(0);
/*      */     }
/*      */ 
/*      */     public boolean setCurrentAccessibleValue(Number n)
/*      */     {
/*  705 */       if ((n instanceof Integer)) {
/*  706 */         if (n.intValue() == 0)
/*  707 */           setVisible(true);
/*      */         else
/*  709 */           setVisible(false);
/*  710 */         return true;
/*      */       }
/*      */ 
/*  713 */       return false;
/*      */     }
/*      */ 
/*      */     public Number getMinimumAccessibleValue()
/*      */     {
/*  723 */       return Integer.valueOf(-2147483648);
/*      */     }
/*      */ 
/*      */     public Number getMaximumAccessibleValue()
/*      */     {
/*  732 */       return Integer.valueOf(2147483647);
/*      */     }
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.popup.JidePopup
 * JD-Core Version:    0.6.0
 */
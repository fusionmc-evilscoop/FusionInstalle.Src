/*      */ package com.jidesoft.plaf.basic;
/*      */ 
/*      */ import com.jidesoft.plaf.JideTabbedPaneUI;
/*      */ import com.jidesoft.plaf.UIDefaultsLookup;
/*      */ import com.jidesoft.popup.JidePopup;
/*      */ import com.jidesoft.popup.JidePopupFactory;
/*      */ import com.jidesoft.swing.JideSwingUtilities;
/*      */ import com.jidesoft.swing.JideTabbedPane;
/*      */ import com.jidesoft.swing.JideTabbedPane.ColorProvider;
/*      */ import com.jidesoft.swing.JideTabbedPane.NoFocusButton;
/*      */ import com.jidesoft.swing.PartialLineBorder;
/*      */ import com.jidesoft.swing.Sticky;
/*      */ import com.jidesoft.swing.TabEditingValidator;
/*      */ import com.jidesoft.utils.PortingUtils;
/*      */ import com.jidesoft.utils.SecurityUtils;
/*      */ import com.jidesoft.utils.SystemInfo;
/*      */ import java.awt.AlphaComposite;
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.ComponentOrientation;
/*      */ import java.awt.Composite;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FocusTraversalPolicy;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Insets;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.Point;
/*      */ import java.awt.Polygon;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.dnd.DropTarget;
/*      */ import java.awt.dnd.DropTargetDragEvent;
/*      */ import java.awt.dnd.DropTargetDropEvent;
/*      */ import java.awt.dnd.DropTargetEvent;
/*      */ import java.awt.dnd.DropTargetListener;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.ComponentAdapter;
/*      */ import java.awt.event.ComponentEvent;
/*      */ import java.awt.event.ComponentListener;
/*      */ import java.awt.event.ContainerEvent;
/*      */ import java.awt.event.ContainerListener;
/*      */ import java.awt.event.FocusAdapter;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.KeyAdapter;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.KeyListener;
/*      */ import java.awt.event.MouseAdapter;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseListener;
/*      */ import java.awt.event.MouseMotionAdapter;
/*      */ import java.awt.event.MouseMotionListener;
/*      */ import java.awt.event.MouseWheelEvent;
/*      */ import java.awt.event.MouseWheelListener;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Vector;
/*      */ import javax.swing.AbstractAction;
/*      */ import javax.swing.Action;
/*      */ import javax.swing.ActionMap;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.DefaultListModel;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.InputMap;
/*      */ import javax.swing.InputVerifier;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JCheckBoxMenuItem;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JPopupMenu;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTabbedPane;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.JToolTip;
/*      */ import javax.swing.JViewport;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.ListCellRenderer;
/*      */ import javax.swing.LookAndFeel;
/*      */ import javax.swing.SingleSelectionModel;
/*      */ import javax.swing.SwingConstants;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.Timer;
/*      */ import javax.swing.UIDefaults;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.ViewportLayout;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.event.DocumentEvent;
/*      */ import javax.swing.event.DocumentListener;
/*      */ import javax.swing.plaf.ActionMapUIResource;
/*      */ import javax.swing.plaf.ColorUIResource;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.InputMapUIResource;
/*      */ import javax.swing.plaf.InsetsUIResource;
/*      */ import javax.swing.plaf.UIResource;
/*      */ import javax.swing.plaf.basic.BasicGraphicsUtils;
/*      */ import javax.swing.plaf.basic.BasicHTML;
/*      */ import javax.swing.text.Document;
/*      */ import javax.swing.text.View;
/*      */ 
/*      */ public class BasicJideTabbedPaneUI extends JideTabbedPaneUI
/*      */   implements SwingConstants, DocumentListener
/*      */ {
/*      */   protected int _tabRectPadding;
/*      */   protected int _closeButtonMarginHorizon;
/*      */   protected int _closeButtonMarginVertical;
/*      */   protected int _textMarginVertical;
/*      */   protected int _noIconMargin;
/*      */   protected int _iconMargin;
/*      */   protected int _textPadding;
/*      */   protected int _buttonSize;
/*      */   protected int _buttonMargin;
/*      */   protected int _fitStyleBoundSize;
/*      */   protected int _fitStyleFirstTabMargin;
/*      */   protected int _fitStyleIconMinWidth;
/*      */   protected int _fitStyleTextMinWidth;
/*      */   protected int _compressedStyleNoIconRectSize;
/*      */   protected int _compressedStyleIconMargin;
/*      */   protected int _compressedStyleCloseButtonMarginHorizon;
/*      */   protected int _compressedStyleCloseButtonMarginVertical;
/*      */   protected int _fixedStyleRectSize;
/*      */   protected int _closeButtonMargin;
/*      */   protected int _gripLeftMargin;
/*      */   protected int _closeButtonMarginSize;
/*      */   protected int _closeButtonLeftMargin;
/*      */   protected int _closeButtonRightMargin;
/*      */   protected Component _tabLeadingComponent;
/*      */   protected Component _tabTrailingComponent;
/*      */   protected JideTabbedPane _tabPane;
/*      */   protected Color _tabBackground;
/*      */   protected Color _background;
/*      */   protected Color _highlight;
/*      */   protected Color _lightHighlight;
/*      */   protected Color _shadow;
/*      */   protected Color _darkShadow;
/*      */   protected Color _focus;
/*      */   protected Color _inactiveTabForeground;
/*      */   protected Color _activeTabForeground;
/*      */   protected Color _tabListBackground;
/*      */   protected Color _selectedColor;
/*      */   protected int _textIconGap;
/*      */   protected int _tabRunOverlay;
/*      */   protected boolean _showIconOnTab;
/*      */   protected boolean _showCloseButtonOnTab;
/*      */   protected int _closeButtonAlignment;
/*      */   protected Insets _tabInsets;
/*      */   protected Insets _selectedTabPadInsets;
/*      */   protected Insets _tabAreaInsets;
/*      */   protected boolean _ignoreContentBorderInsetsIfNoTabs;
/*      */   protected int[] _tabRuns;
/*      */   protected int _runCount;
/*      */   protected int _selectedRun;
/*      */   protected Rectangle[] _rects;
/*      */   protected int _maxTabHeight;
/*      */   protected int _maxTabWidth;
/*      */   protected int _gripperWidth;
/*      */   protected int _gripperHeight;
/*      */   protected ChangeListener _tabChangeListener;
/*      */   protected FocusListener _tabFocusListener;
/*      */   protected PropertyChangeListener _propertyChangeListener;
/*      */   protected MouseListener _mouseListener;
/*      */   protected MouseMotionListener _mousemotionListener;
/*      */   protected MouseWheelListener _mouseWheelListener;
/*      */   private ContainerListener _containerListener;
/*      */   private ComponentListener _componentListener;
/*      */   private Insets _currentTabInsets;
/*      */   private Insets _currentPadInsets;
/*      */   private Insets _currentTabAreaInsets;
/*      */   private Insets _currentContentBorderInsets;
/*      */   private Component visibleComponent;
/*      */   private Vector htmlViews;
/*      */   private Hashtable _mnemonicToIndexMap;
/*      */   private InputMap _mnemonicInputMap;
/*      */   public ScrollableTabSupport _tabScroller;
/*      */   protected transient Rectangle _calcRect;
/*      */   protected int _tabCount;
/*      */   protected JButton[] _closeButtons;
/*      */   private ThemePainter _painter;
/*      */   private Painter _gripperPainter;
/*      */   private DropTargetListener _dropListener;
/*      */   public DropTarget _dt;
/*      */   public static final int DEFAULT_LEFT_MARGIN = 0;
/*      */   public static final int OFFICE2003_LEFT_MARGIN = 18;
/*      */   public static final int EXCEL_LEFT_MARGIN = 6;
/*      */   protected int _rectSizeExtend;
/*      */   protected Polygon tabRegion;
/*      */   protected Color _selectColor1;
/*      */   protected Color _selectColor2;
/*      */   protected Color _selectColor3;
/*      */   protected Color _unselectColor1;
/*      */   protected Color _unselectColor2;
/*      */   protected Color _unselectColor3;
/*      */   protected Color _officeTabBorderColor;
/*      */   protected Color _defaultTabBorderShadowColor;
/*      */   protected boolean _mouseEnter;
/*      */   protected int _indexMouseOver;
/*      */   protected boolean _alwaysShowLineBorder;
/*      */   protected boolean _showFocusIndicator;
/*      */   public static final String BUTTON_NAME_CLOSE = "JideTabbedPane.close";
/*      */   public static final String BUTTON_NAME_TAB_LIST = "JideTabbedPane.showList";
/*      */   public static final String BUTTON_NAME_SCROLL_BACKWARD = "JideTabbedPane.scrollBackward";
/*      */   public static final String BUTTON_NAME_SCROLL_FORWARD = "JideTabbedPane.scrollForward";
/*      */   private int[] xCropLen;
/*      */   private int[] yCropLen;
/*      */   private static final int CROP_SEGMENT = 12;
/*      */   protected TabSpaceAllocator tryTabSpacer;
/*      */   protected Color _closeButtonSelectedColor;
/*      */   protected Color _closeButtonColor;
/*      */   protected Color _popupColor;
/*      */   protected TabEditor _tabEditor;
/*      */   protected boolean _isEditing;
/*      */   protected int _editingTab;
/*      */   protected String _oldValue;
/*      */   protected String _oldPrefix;
/*      */   protected String _oldPostfix;
/*      */   protected Component _originalFocusComponent;
/*      */   protected final boolean PAINT_TAB = true;
/*      */   protected final boolean PAINT_TAB_BORDER = true;
/*      */   protected final boolean PAINT_TAB_BACKGROUND = true;
/*      */   protected final boolean PAINT_TABAREA = true;
/*      */   protected final boolean PAINT_CONTENT_BORDER = true;
/*      */   protected final boolean PAINT_CONTENT_BORDER_EDGE = true;
/*      */ 
/*      */   public BasicJideTabbedPaneUI()
/*      */   {
/*   93 */     this._tabLeadingComponent = null;
/*      */ 
/*   95 */     this._tabTrailingComponent = null;
/*      */ 
/*  127 */     this._closeButtonAlignment = 11;
/*      */ 
/*  139 */     this._tabRuns = new int[10];
/*      */ 
/*  141 */     this._runCount = 0;
/*      */ 
/*  143 */     this._selectedRun = -1;
/*      */ 
/*  145 */     this._rects = new Rectangle[0];
/*      */ 
/*  151 */     this._gripperWidth = 6;
/*      */ 
/*  153 */     this._gripperHeight = 6;
/*      */ 
/*  175 */     this._currentTabInsets = new Insets(0, 0, 0, 0);
/*      */ 
/*  177 */     this._currentPadInsets = new Insets(0, 0, 0, 0);
/*      */ 
/*  179 */     this._currentTabAreaInsets = new Insets(2, 4, 0, 4);
/*      */ 
/*  181 */     this._currentContentBorderInsets = new Insets(3, 0, 0, 0);
/*      */ 
/*  202 */     this._calcRect = new Rectangle(0, 0, 0, 0);
/*      */ 
/*  229 */     this._rectSizeExtend = 0;
/*      */ 
/*  232 */     this.tabRegion = null;
/*      */ 
/*  234 */     this._selectColor1 = null;
/*  235 */     this._selectColor2 = null;
/*  236 */     this._selectColor3 = null;
/*  237 */     this._unselectColor1 = null;
/*  238 */     this._unselectColor2 = null;
/*  239 */     this._unselectColor3 = null;
/*      */ 
/*  244 */     this._mouseEnter = false;
/*      */ 
/*  248 */     this._alwaysShowLineBorder = false;
/*  249 */     this._showFocusIndicator = false;
/*      */ 
/* 1063 */     this.xCropLen = new int[] { 1, 1, 0, 0, 1, 1, 2, 2 };
/*      */ 
/* 1065 */     this.yCropLen = new int[] { 0, 3, 3, 6, 6, 9, 9, 12 };
/*      */ 
/* 6217 */     this.tryTabSpacer = new TabSpaceAllocator();
/*      */ 
/* 8087 */     this._closeButtonSelectedColor = new Color(255, 162, 165);
/* 8088 */     this._closeButtonColor = Color.BLACK;
/* 8089 */     this._popupColor = Color.BLACK;
/*      */ 
/* 9086 */     this._editingTab = -1;
/*      */ 
/* 9561 */     this.PAINT_TAB = true;
/*      */ 
/* 9563 */     this.PAINT_TAB_BORDER = true;
/*      */ 
/* 9565 */     this.PAINT_TAB_BACKGROUND = true;
/*      */ 
/* 9567 */     this.PAINT_TABAREA = true;
/*      */ 
/* 9569 */     this.PAINT_CONTENT_BORDER = true;
/*      */ 
/* 9571 */     this.PAINT_CONTENT_BORDER_EDGE = true;
/*      */   }
/*      */ 
/*      */   public static ComponentUI createUI(JComponent c)
/*      */   {
/*  258 */     return new BasicJideTabbedPaneUI();
/*      */   }
/*      */ 
/*      */   public void installUI(JComponent c)
/*      */   {
/*  265 */     if (c == null) {
/*  266 */       return;
/*      */     }
/*      */ 
/*  269 */     this._tabPane = ((JideTabbedPane)c);
/*      */ 
/*  271 */     if ((this._tabPane.isTabShown()) && (this._tabPane.getTabLeadingComponent() != null)) {
/*  272 */       this._tabLeadingComponent = this._tabPane.getTabLeadingComponent();
/*      */     }
/*  274 */     if ((this._tabPane.isTabShown()) && (this._tabPane.getTabTrailingComponent() != null)) {
/*  275 */       this._tabTrailingComponent = this._tabPane.getTabTrailingComponent();
/*      */     }
/*      */ 
/*  278 */     setMouseOverTabIndex(-1);
/*      */ 
/*  280 */     c.setLayout(createLayoutManager());
/*  281 */     installComponents();
/*  282 */     installDefaults();
/*  283 */     installColorTheme();
/*  284 */     installListeners();
/*  285 */     installKeyboardActions();
/*      */   }
/*      */ 
/*      */   public void installColorTheme() {
/*  289 */     switch (getTabShape()) {
/*      */     case 8:
/*  291 */       this._selectColor1 = this._darkShadow;
/*  292 */       this._selectColor2 = this._lightHighlight;
/*  293 */       this._selectColor3 = this._shadow;
/*  294 */       this._unselectColor1 = this._darkShadow;
/*  295 */       this._unselectColor2 = this._lightHighlight;
/*  296 */       this._unselectColor3 = this._shadow;
/*  297 */       break;
/*      */     case 1:
/*      */     case 11:
/*  300 */       this._selectColor1 = this._lightHighlight;
/*  301 */       this._selectColor2 = this._shadow;
/*  302 */       this._selectColor3 = this._defaultTabBorderShadowColor;
/*  303 */       this._unselectColor1 = this._selectColor1;
/*  304 */       this._unselectColor2 = this._selectColor2;
/*  305 */       this._unselectColor3 = this._selectColor3;
/*  306 */       break;
/*      */     case 2:
/*  308 */       this._selectColor1 = this._shadow;
/*  309 */       this._selectColor2 = this._shadow;
/*  310 */       this._unselectColor1 = this._selectColor1;
/*  311 */       break;
/*      */     case 9:
/*  313 */       this._selectColor1 = this._shadow;
/*  314 */       this._selectColor2 = this._selectColor1;
/*  315 */       this._unselectColor1 = this._selectColor1;
/*  316 */       break;
/*      */     case 5:
/*  318 */       this._selectColor1 = this._shadow;
/*  319 */       this._unselectColor1 = this._selectColor1;
/*  320 */       break;
/*      */     case 10:
/*  322 */       this._selectColor1 = this._shadow;
/*  323 */       this._selectColor2 = this._shadow;
/*  324 */       this._unselectColor1 = this._selectColor1;
/*  325 */       this._unselectColor2 = this._selectColor2;
/*  326 */       break;
/*      */     case 3:
/*  328 */       this._selectColor1 = this._shadow;
/*  329 */       this._selectColor2 = this._lightHighlight;
/*  330 */       this._unselectColor1 = getPainter().getControlShadow();
/*  331 */       this._unselectColor2 = this._lightHighlight;
/*  332 */       break;
/*      */     case 4:
/*      */     case 6:
/*      */     case 7:
/*      */     default:
/*  335 */       this._selectColor1 = this._shadow;
/*  336 */       this._selectColor2 = this._lightHighlight;
/*  337 */       this._unselectColor1 = this._shadow;
/*  338 */       this._unselectColor2 = null;
/*  339 */       this._unselectColor3 = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void uninstallUI(JComponent c)
/*      */   {
/*  346 */     uninstallKeyboardActions();
/*  347 */     uninstallListeners();
/*  348 */     uninstallColorTheme();
/*  349 */     uninstallDefaults();
/*  350 */     uninstallComponents();
/*  351 */     c.setLayout(null);
/*  352 */     this._tabTrailingComponent = null;
/*  353 */     this._tabLeadingComponent = null;
/*  354 */     this._tabPane = null;
/*      */   }
/*      */ 
/*      */   public void uninstallColorTheme() {
/*  358 */     this._selectColor1 = null;
/*  359 */     this._selectColor2 = null;
/*  360 */     this._selectColor3 = null;
/*  361 */     this._unselectColor1 = null;
/*  362 */     this._unselectColor2 = null;
/*  363 */     this._unselectColor3 = null;
/*      */   }
/*      */ 
/*      */   protected LayoutManager createLayoutManager()
/*      */   {
/*  374 */     if (this._tabPane.getTabLayoutPolicy() == 1) {
/*  375 */       return new TabbedPaneScrollLayout();
/*      */     }
/*      */ 
/*  378 */     return new TabbedPaneLayout();
/*      */   }
/*      */ 
/*      */   protected boolean scrollableTabLayoutEnabled()
/*      */   {
/*  389 */     return this._tabPane.getLayout() instanceof TabbedPaneScrollLayout;
/*      */   }
/*      */ 
/*      */   protected void installComponents()
/*      */   {
/*  396 */     if ((scrollableTabLayoutEnabled()) && 
/*  397 */       (this._tabScroller == null)) {
/*  398 */       this._tabScroller = new ScrollableTabSupport(this._tabPane.getTabPlacement());
/*  399 */       this._tabPane.add(this._tabScroller.viewport);
/*  400 */       this._tabPane.add(this._tabScroller.scrollForwardButton);
/*  401 */       this._tabPane.add(this._tabScroller.scrollBackwardButton);
/*  402 */       this._tabPane.add(this._tabScroller.listButton);
/*  403 */       this._tabPane.add(this._tabScroller.closeButton);
/*  404 */       if (this._tabLeadingComponent != null) {
/*  405 */         this._tabPane.add(this._tabLeadingComponent);
/*      */       }
/*  407 */       if (this._tabTrailingComponent != null)
/*  408 */         this._tabPane.add(this._tabTrailingComponent);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void uninstallComponents()
/*      */   {
/*  418 */     if (scrollableTabLayoutEnabled()) {
/*  419 */       this._tabPane.remove(this._tabScroller.viewport);
/*  420 */       this._tabPane.remove(this._tabScroller.scrollForwardButton);
/*  421 */       this._tabPane.remove(this._tabScroller.scrollBackwardButton);
/*  422 */       this._tabPane.remove(this._tabScroller.listButton);
/*  423 */       this._tabPane.remove(this._tabScroller.closeButton);
/*  424 */       if (this._tabLeadingComponent != null) {
/*  425 */         this._tabPane.remove(this._tabLeadingComponent);
/*      */       }
/*  427 */       if (this._tabTrailingComponent != null) {
/*  428 */         this._tabPane.remove(this._tabTrailingComponent);
/*      */       }
/*  430 */       this._tabScroller = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void installDefaults() {
/*  435 */     this._painter = ((ThemePainter)UIDefaultsLookup.get("Theme.painter"));
/*  436 */     this._gripperPainter = ((Painter)UIDefaultsLookup.get("JideTabbedPane.gripperPainter"));
/*      */ 
/*  438 */     LookAndFeel.installColorsAndFont(this._tabPane, "JideTabbedPane.background", "JideTabbedPane.foreground", "JideTabbedPane.font");
/*      */ 
/*  440 */     LookAndFeel.installBorder(this._tabPane, "JideTabbedPane.border");
/*  441 */     Font f = this._tabPane.getSelectedTabFont();
/*  442 */     if ((f == null) || ((f instanceof UIResource))) {
/*  443 */       this._tabPane.setSelectedTabFont(UIDefaultsLookup.getFont("JideTabbedPane.selectedTabFont"));
/*      */     }
/*      */ 
/*  446 */     this._highlight = UIDefaultsLookup.getColor("JideTabbedPane.light");
/*  447 */     this._lightHighlight = UIDefaultsLookup.getColor("JideTabbedPane.highlight");
/*  448 */     this._shadow = UIDefaultsLookup.getColor("JideTabbedPane.shadow");
/*  449 */     this._darkShadow = UIDefaultsLookup.getColor("JideTabbedPane.darkShadow");
/*  450 */     this._focus = UIDefaultsLookup.getColor("TabbedPane.focus");
/*      */ 
/*  452 */     if (getTabShape() == 3) {
/*  453 */       this._background = UIDefaultsLookup.getColor("JideTabbedPane.selectedTabBackground");
/*  454 */       this._tabBackground = UIDefaultsLookup.getColor("JideTabbedPane.selectedTabBackground");
/*  455 */       this._inactiveTabForeground = UIDefaultsLookup.getColor("JideTabbedPane.foreground");
/*  456 */       this._activeTabForeground = UIDefaultsLookup.getColor("JideTabbedPane.foreground");
/*  457 */       this._selectedColor = this._lightHighlight;
/*      */     }
/*      */     else {
/*  460 */       this._background = UIDefaultsLookup.getColor("JideTabbedPane.background");
/*  461 */       this._tabBackground = UIDefaultsLookup.getColor("JideTabbedPane.tabAreaBackground");
/*  462 */       this._inactiveTabForeground = UIDefaultsLookup.getColor("JideTabbedPane.unselectedTabTextForeground");
/*  463 */       this._activeTabForeground = UIDefaultsLookup.getColor("JideTabbedPane.selectedTabTextForeground");
/*  464 */       this._selectedColor = UIDefaultsLookup.getColor("JideTabbedPane.selectedTabBackground");
/*      */     }
/*      */ 
/*  467 */     this._tabListBackground = UIDefaultsLookup.getColor("JideTabbedPane.tabListBackground");
/*      */ 
/*  469 */     this._textIconGap = UIDefaultsLookup.getInt("JideTabbedPane.textIconGap");
/*      */ 
/*  471 */     Insets tabInsets = this._tabPane.getTabInsets();
/*  472 */     if ((tabInsets == null) || ((tabInsets instanceof UIResource))) {
/*  473 */       this._tabPane.setTabInsets(UIDefaultsLookup.getInsets("JideTabbedPane.tabInsets"));
/*      */     }
/*      */ 
/*  476 */     this._selectedTabPadInsets = UIDefaultsLookup.getInsets("TabbedPane.selectedTabPadInsets");
/*  477 */     if (this._selectedTabPadInsets == null) this._selectedTabPadInsets = new InsetsUIResource(0, 0, 0, 0);
/*      */ 
/*  479 */     Insets tabAreaInsets = this._tabPane.getTabAreaInsets();
/*  480 */     if ((tabAreaInsets == null) || ((tabAreaInsets instanceof UIResource))) {
/*  481 */       this._tabPane.setTabAreaInsets(UIDefaultsLookup.getInsets("JideTabbedPane.tabAreaInsets"));
/*      */     }
/*      */ 
/*  484 */     Insets insets = this._tabPane.getContentBorderInsets();
/*  485 */     if ((insets == null) || ((insets instanceof UIResource))) {
/*  486 */       this._tabPane.setContentBorderInsets(UIDefaultsLookup.getInsets("JideTabbedPane.contentBorderInsets"));
/*      */     }
/*      */ 
/*  489 */     this._ignoreContentBorderInsetsIfNoTabs = UIDefaultsLookup.getBoolean("JideTabbedPane.ignoreContentBorderInsetsIfNoTabs");
/*  490 */     this._tabRunOverlay = UIDefaultsLookup.getInt("JideTabbedPane.tabRunOverlay");
/*  491 */     this._showIconOnTab = UIDefaultsLookup.getBoolean("JideTabbedPane.showIconOnTab");
/*  492 */     this._showCloseButtonOnTab = UIDefaultsLookup.getBoolean("JideTabbedPane.showCloseButtonOnTab");
/*  493 */     this._closeButtonAlignment = UIDefaultsLookup.getInt("JideTabbedPane.closeButtonAlignment");
/*  494 */     this._gripperWidth = UIDefaultsLookup.getInt("Gripper.size");
/*      */ 
/*  496 */     this._tabRectPadding = UIDefaultsLookup.getInt("JideTabbedPane.tabRectPadding");
/*  497 */     this._closeButtonMarginHorizon = UIDefaultsLookup.getInt("JideTabbedPane.closeButtonMarginHorizonal");
/*  498 */     this._closeButtonMarginVertical = UIDefaultsLookup.getInt("JideTabbedPane.closeButtonMarginVertical");
/*  499 */     this._textMarginVertical = UIDefaultsLookup.getInt("JideTabbedPane.textMarginVertical");
/*  500 */     this._noIconMargin = UIDefaultsLookup.getInt("JideTabbedPane.noIconMargin");
/*  501 */     this._iconMargin = UIDefaultsLookup.getInt("JideTabbedPane.iconMargin");
/*  502 */     this._textPadding = UIDefaultsLookup.getInt("JideTabbedPane.textPadding");
/*  503 */     this._buttonSize = UIDefaultsLookup.getInt("JideTabbedPane.buttonSize");
/*  504 */     this._buttonMargin = UIDefaultsLookup.getInt("JideTabbedPane.buttonMargin");
/*  505 */     this._fitStyleBoundSize = UIDefaultsLookup.getInt("JideTabbedPane.fitStyleBoundSize");
/*  506 */     this._fitStyleFirstTabMargin = UIDefaultsLookup.getInt("JideTabbedPane.fitStyleFirstTabMargin");
/*  507 */     this._fitStyleIconMinWidth = UIDefaultsLookup.getInt("JideTabbedPane.fitStyleIconMinWidth");
/*  508 */     this._fitStyleTextMinWidth = UIDefaultsLookup.getInt("JideTabbedPane.fitStyleTextMinWidth");
/*  509 */     this._compressedStyleNoIconRectSize = UIDefaultsLookup.getInt("JideTabbedPane.compressedStyleNoIconRectSize");
/*  510 */     this._compressedStyleIconMargin = UIDefaultsLookup.getInt("JideTabbedPane.compressedStyleIconMargin");
/*  511 */     this._compressedStyleCloseButtonMarginHorizon = UIDefaultsLookup.getInt("JideTabbedPane.compressedStyleCloseButtonMarginHorizontal");
/*  512 */     this._compressedStyleCloseButtonMarginVertical = UIDefaultsLookup.getInt("JideTabbedPane.compressedStyleCloseButtonMarginVertical");
/*  513 */     this._fixedStyleRectSize = UIDefaultsLookup.getInt("JideTabbedPane.fixedStyleRectSize");
/*  514 */     this._closeButtonMargin = UIDefaultsLookup.getInt("JideTabbedPane.closeButtonMargin");
/*  515 */     this._gripLeftMargin = UIDefaultsLookup.getInt("JideTabbedPane.gripLeftMargin");
/*  516 */     this._closeButtonMarginSize = UIDefaultsLookup.getInt("JideTabbedPane.closeButtonMarginSize");
/*  517 */     this._closeButtonLeftMargin = UIDefaultsLookup.getInt("JideTabbedPane.closeButtonLeftMargin");
/*  518 */     this._closeButtonRightMargin = UIDefaultsLookup.getInt("JideTabbedPane.closeButtonRightMargin");
/*      */ 
/*  520 */     this._defaultTabBorderShadowColor = UIDefaultsLookup.getColor("JideTabbedPane.defaultTabBorderShadowColor");
/*  521 */     this._alwaysShowLineBorder = UIDefaultsLookup.getBoolean("JideTabbedPane.alwaysShowLineBorder");
/*  522 */     this._showFocusIndicator = UIDefaultsLookup.getBoolean("JideTabbedPane.showFocusIndicator");
/*      */   }
/*      */ 
/*      */   protected void uninstallDefaults() {
/*  526 */     this._painter = null;
/*  527 */     this._gripperPainter = null;
/*      */ 
/*  529 */     this._highlight = null;
/*  530 */     this._lightHighlight = null;
/*  531 */     this._shadow = null;
/*  532 */     this._darkShadow = null;
/*  533 */     this._focus = null;
/*  534 */     this._inactiveTabForeground = null;
/*  535 */     this._selectedColor = null;
/*  536 */     this._tabInsets = null;
/*  537 */     this._selectedTabPadInsets = null;
/*  538 */     this._tabAreaInsets = null;
/*      */ 
/*  540 */     this._defaultTabBorderShadowColor = null;
/*      */   }
/*      */ 
/*      */   protected void installListeners() {
/*  544 */     this._tabPane.getModel().addChangeListener(new ChangeListener() {
/*      */       public void stateChanged(ChangeEvent e) {
/*  546 */         if (BasicJideTabbedPaneUI.this._tabPane != null) {
/*  547 */           int selectedIndex = BasicJideTabbedPaneUI.this._tabPane.getSelectedIndex();
/*  548 */           if ((selectedIndex >= 0) && (selectedIndex < BasicJideTabbedPaneUI.this._tabPane.getTabCount()) && (BasicJideTabbedPaneUI.this._tabScroller != null) && (BasicJideTabbedPaneUI.this._tabScroller.closeButton != null))
/*  549 */             BasicJideTabbedPaneUI.this._tabScroller.closeButton.setEnabled(BasicJideTabbedPaneUI.this._tabPane.isTabClosableAt(selectedIndex));
/*      */         }
/*      */       }
/*      */     });
/*  554 */     if (this._propertyChangeListener == null) {
/*  555 */       this._propertyChangeListener = createPropertyChangeListener();
/*  556 */       this._tabPane.addPropertyChangeListener(this._propertyChangeListener);
/*      */     }
/*  558 */     if (this._tabChangeListener == null) {
/*  559 */       this._tabChangeListener = createChangeListener();
/*  560 */       this._tabPane.addChangeListener(this._tabChangeListener);
/*      */     }
/*  562 */     if (this._tabFocusListener == null) {
/*  563 */       this._tabFocusListener = createFocusListener();
/*  564 */       this._tabPane.addFocusListener(this._tabFocusListener);
/*      */     }
/*      */ 
/*  567 */     if (this._mouseListener == null) {
/*  568 */       this._mouseListener = createMouseListener();
/*  569 */       this._tabPane.addMouseListener(this._mouseListener);
/*      */     }
/*      */ 
/*  572 */     if (this._mousemotionListener == null) {
/*  573 */       this._mousemotionListener = createMouseMotionListener();
/*  574 */       this._tabPane.addMouseMotionListener(this._mousemotionListener);
/*      */     }
/*      */ 
/*  577 */     if (this._mouseWheelListener == null) {
/*  578 */       this._mouseWheelListener = createMouseWheelListener();
/*  579 */       this._tabPane.addMouseWheelListener(this._mouseWheelListener);
/*      */     }
/*      */ 
/*  583 */     if (this._containerListener == null) {
/*  584 */       this._containerListener = new ContainerHandler(null);
/*  585 */       this._tabPane.addContainerListener(this._containerListener);
/*  586 */       if (this._tabPane.getTabCount() > 0) {
/*  587 */         this.htmlViews = createHTMLVector();
/*      */       }
/*      */     }
/*  590 */     if (this._componentListener == null) {
/*  591 */       this._componentListener = new ComponentHandler(null);
/*  592 */       this._tabPane.addComponentListener(this._componentListener);
/*      */     }
/*      */ 
/*  595 */     if ((!this._tabPane.isDragOverDisabled()) && 
/*  596 */       (this._dropListener == null)) {
/*  597 */       this._dropListener = createDropListener();
/*  598 */       this._dt = new DropTarget(getTabPanel(), this._dropListener);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected DropListener createDropListener()
/*      */   {
/*  604 */     return new DropListener();
/*      */   }
/*      */ 
/*      */   protected void uninstallListeners()
/*      */   {
/*  609 */     if (this._containerListener != null) {
/*  610 */       this._tabPane.removeContainerListener(this._containerListener);
/*  611 */       this._containerListener = null;
/*  612 */       if (this.htmlViews != null) {
/*  613 */         this.htmlViews.removeAllElements();
/*  614 */         this.htmlViews = null;
/*      */       }
/*      */     }
/*      */ 
/*  618 */     if (this._componentListener != null) {
/*  619 */       this._tabPane.removeComponentListener(this._componentListener);
/*  620 */       this._componentListener = null;
/*      */     }
/*      */ 
/*  623 */     if (this._tabChangeListener != null) {
/*  624 */       this._tabPane.removeChangeListener(this._tabChangeListener);
/*  625 */       this._tabChangeListener = null;
/*      */     }
/*      */ 
/*  628 */     if (this._tabFocusListener != null) {
/*  629 */       this._tabPane.removeFocusListener(this._tabFocusListener);
/*  630 */       this._tabFocusListener = null;
/*      */     }
/*      */ 
/*  633 */     if (this._mouseListener != null) {
/*  634 */       this._tabPane.removeMouseListener(this._mouseListener);
/*  635 */       this._mouseListener = null;
/*      */     }
/*      */ 
/*  638 */     if (this._mousemotionListener != null) {
/*  639 */       this._tabPane.removeMouseMotionListener(this._mousemotionListener);
/*  640 */       this._mousemotionListener = null;
/*      */     }
/*      */ 
/*  643 */     if (this._mouseWheelListener != null) {
/*  644 */       this._tabPane.removeMouseWheelListener(this._mouseWheelListener);
/*  645 */       this._mouseWheelListener = null;
/*      */     }
/*      */ 
/*  648 */     if (this._propertyChangeListener != null) {
/*  649 */       this._tabPane.removePropertyChangeListener(this._propertyChangeListener);
/*  650 */       this._propertyChangeListener = null;
/*      */     }
/*      */ 
/*  653 */     if ((this._dt != null) && (this._dropListener != null)) {
/*  654 */       this._dt.removeDropTargetListener(this._dropListener);
/*  655 */       this._dropListener = null;
/*  656 */       this._dt = null;
/*  657 */       getTabPanel().setDropTarget(null);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected ChangeListener createChangeListener() {
/*  662 */     return new TabSelectionHandler();
/*      */   }
/*      */ 
/*      */   protected FocusListener createFocusListener() {
/*  666 */     return new TabFocusListener();
/*      */   }
/*      */ 
/*      */   protected PropertyChangeListener createPropertyChangeListener() {
/*  670 */     return new PropertyChangeHandler();
/*      */   }
/*      */ 
/*      */   protected void installKeyboardActions() {
/*  674 */     InputMap km = getInputMap(1);
/*  675 */     SwingUtilities.replaceUIInputMap(this._tabPane, 1, km);
/*  676 */     km = getInputMap(0);
/*  677 */     SwingUtilities.replaceUIInputMap(this._tabPane, 0, km);
/*  678 */     ActionMap am = getActionMap();
/*      */ 
/*  680 */     SwingUtilities.replaceUIActionMap(this._tabPane, am);
/*      */ 
/*  682 */     ensureCloseButtonCreated();
/*      */ 
/*  684 */     if (scrollableTabLayoutEnabled()) {
/*  685 */       Icon forwardIcon = this._tabScroller.scrollForwardButton.getIcon();
/*  686 */       this._tabScroller.scrollForwardButton.setAction(am.get("scrollTabsForwardAction"));
/*  687 */       if (forwardIcon != null) {
/*  688 */         this._tabScroller.scrollForwardButton.setIcon(forwardIcon);
/*      */       }
/*  690 */       Icon backwardIcon = this._tabScroller.scrollBackwardButton.getIcon();
/*  691 */       this._tabScroller.scrollBackwardButton.setAction(am.get("scrollTabsBackwardAction"));
/*  692 */       if (backwardIcon != null) {
/*  693 */         this._tabScroller.scrollBackwardButton.setIcon(backwardIcon);
/*      */       }
/*  695 */       Icon listIcon = this._tabScroller.listButton.getIcon();
/*  696 */       this._tabScroller.listButton.setAction(am.get("scrollTabsListAction"));
/*  697 */       if (listIcon != null) {
/*  698 */         this._tabScroller.listButton.setIcon(listIcon);
/*      */       }
/*  700 */       Action action = this._tabPane.getCloseAction();
/*  701 */       updateButtonFromAction(this._tabScroller.closeButton, action);
/*  702 */       Icon closeIcon = this._tabScroller.closeButton.getIcon();
/*  703 */       this._tabScroller.closeButton.setAction(am.get("closeTabAction"));
/*  704 */       if (closeIcon != null) {
/*  705 */         this._tabScroller.closeButton.setIcon(closeIcon);
/*      */       }
/*  707 */       this._tabScroller.scrollForwardButton.setToolTipText(getResourceString("JideTabbedPane.scrollForward"));
/*  708 */       this._tabScroller.scrollBackwardButton.setToolTipText(getResourceString("JideTabbedPane.scrollBackward"));
/*  709 */       this._tabScroller.listButton.setToolTipText(getResourceString("JideTabbedPane.showList"));
/*  710 */       this._tabScroller.closeButton.setToolTipText(getResourceString("JideTabbedPane.close"));
/*      */     }
/*      */   }
/*      */ 
/*      */   InputMap getInputMap(int condition)
/*      */   {
/*  716 */     if (condition == 1) {
/*  717 */       return (InputMap)UIDefaultsLookup.get("JideTabbedPane.ancestorInputMap");
/*      */     }
/*  719 */     if (condition == 0) {
/*  720 */       return (InputMap)UIDefaultsLookup.get("JideTabbedPane.focusInputMap");
/*      */     }
/*  722 */     return null;
/*      */   }
/*      */ 
/*      */   ActionMap getActionMap() {
/*  726 */     ActionMap map = (ActionMap)UIDefaultsLookup.get("JideTabbedPane.actionMap");
/*      */ 
/*  728 */     if (map == null) {
/*  729 */       map = createActionMap();
/*  730 */       if (map != null) {
/*  731 */         UIManager.getLookAndFeelDefaults().put("JideTabbedPane.actionMap", map);
/*      */       }
/*      */     }
/*  734 */     return map;
/*      */   }
/*      */ 
/*      */   ActionMap createActionMap() {
/*  738 */     ActionMap map = new ActionMapUIResource();
/*  739 */     map.put("navigateNext", new NextAction(null));
/*  740 */     map.put("navigatePrevious", new PreviousAction(null));
/*  741 */     map.put("navigateRight", new RightAction(null));
/*  742 */     map.put("navigateLeft", new LeftAction(null));
/*  743 */     map.put("navigateUp", new UpAction(null));
/*  744 */     map.put("navigateDown", new DownAction(null));
/*  745 */     map.put("navigatePageUp", new PageUpAction(null));
/*  746 */     map.put("navigatePageDown", new PageDownAction(null));
/*  747 */     map.put("requestFocus", new RequestFocusAction(null));
/*  748 */     map.put("requestFocusForVisibleComponent", new RequestFocusForVisibleAction(null));
/*  749 */     map.put("setSelectedIndex", new SetSelectedIndexAction(null));
/*  750 */     map.put("scrollTabsForwardAction", new ScrollTabsForwardAction());
/*  751 */     map.put("scrollTabsBackwardAction", new ScrollTabsBackwardAction());
/*  752 */     map.put("scrollTabsListAction", new ScrollTabsListAction());
/*  753 */     map.put("closeTabAction", new CloseTabAction());
/*  754 */     return map;
/*      */   }
/*      */ 
/*      */   protected void uninstallKeyboardActions() {
/*  758 */     SwingUtilities.replaceUIActionMap(this._tabPane, null);
/*  759 */     SwingUtilities.replaceUIInputMap(this._tabPane, 1, null);
/*  760 */     SwingUtilities.replaceUIInputMap(this._tabPane, 0, null);
/*      */ 
/*  762 */     if (this._closeButtons != null) {
/*  763 */       for (int i = 0; i < this._closeButtons.length; i++) {
/*  764 */         this._closeButtons[i] = null;
/*      */       }
/*  766 */       this._closeButtons = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void updateMnemonics()
/*      */   {
/*  775 */     resetMnemonics();
/*  776 */     for (int counter = this._tabPane.getTabCount() - 1; counter >= 0; counter--) {
/*  777 */       int mnemonic = this._tabPane.getMnemonicAt(counter);
/*      */ 
/*  779 */       if (mnemonic > 0)
/*  780 */         addMnemonic(counter, mnemonic);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void resetMnemonics()
/*      */   {
/*  789 */     if (this._mnemonicToIndexMap != null) {
/*  790 */       this._mnemonicToIndexMap.clear();
/*  791 */       this._mnemonicInputMap.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addMnemonic(int index, int mnemonic)
/*      */   {
/*  802 */     if (this._mnemonicToIndexMap == null) {
/*  803 */       initMnemonics();
/*      */     }
/*  805 */     this._mnemonicInputMap.put(KeyStroke.getKeyStroke(mnemonic, 8), "setSelectedIndex");
/*  806 */     this._mnemonicToIndexMap.put(Integer.valueOf(mnemonic), Integer.valueOf(index));
/*      */   }
/*      */ 
/*      */   private void initMnemonics()
/*      */   {
/*  813 */     this._mnemonicToIndexMap = new Hashtable();
/*  814 */     this._mnemonicInputMap = new InputMapUIResource();
/*  815 */     this._mnemonicInputMap.setParent(SwingUtilities.getUIInputMap(this._tabPane, 1));
/*  816 */     SwingUtilities.replaceUIInputMap(this._tabPane, 1, this._mnemonicInputMap);
/*      */   }
/*      */ 
/*      */   public Dimension getPreferredSize(JComponent c)
/*      */   {
/*  824 */     return null;
/*      */   }
/*      */ 
/*      */   public Dimension getMinimumSize(JComponent c)
/*      */   {
/*  830 */     return null;
/*      */   }
/*      */ 
/*      */   public Dimension getMaximumSize(JComponent c)
/*      */   {
/*  836 */     return null;
/*      */   }
/*      */ 
/*      */   public void paint(Graphics g, JComponent c)
/*      */   {
/*  843 */     int tc = this._tabPane.getTabCount();
/*      */ 
/*  845 */     paintBackground(g, c);
/*      */ 
/*  847 */     if (tc == 0) {
/*  848 */       return;
/*      */     }
/*      */ 
/*  851 */     if (this._tabCount != tc) {
/*  852 */       this._tabCount = tc;
/*  853 */       updateMnemonics();
/*      */     }
/*      */ 
/*  856 */     int selectedIndex = this._tabPane.getSelectedIndex();
/*  857 */     int tabPlacement = this._tabPane.getTabPlacement();
/*      */ 
/*  859 */     ensureCurrentLayout();
/*      */ 
/*  865 */     if (!scrollableTabLayoutEnabled()) {
/*  866 */       paintTabArea(g, tabPlacement, selectedIndex, c);
/*      */     }
/*      */ 
/*  871 */     paintContentBorder(g, tabPlacement, selectedIndex);
/*      */   }
/*      */ 
/*      */   public void paintBackground(Graphics g, Component c) {
/*  875 */     if (this._tabPane.isOpaque()) {
/*  876 */       int width = c.getWidth();
/*  877 */       int height = c.getHeight();
/*  878 */       g.setColor(this._background);
/*  879 */       g.fillRect(0, 0, width, height);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex, Component c)
/*      */   {
/*  901 */     int tabCount = this._tabPane.getTabCount();
/*      */ 
/*  903 */     Rectangle iconRect = new Rectangle();
/*  904 */     Rectangle textRect = new Rectangle();
/*      */ 
/*  906 */     Rectangle rect = new Rectangle(0, 0, c.getWidth(), c.getHeight());
/*      */ 
/*  908 */     paintTabAreaBackground(g, rect, tabPlacement);
/*  909 */     boolean leftToRight = (tabPlacement == 2) || (tabPlacement == 4) || (this._tabPane.getComponentOrientation().isLeftToRight());
/*      */ 
/*  912 */     for (int i = this._runCount - 1; i >= 0; i--) {
/*  913 */       int start = this._tabRuns[i];
/*  914 */       int next = this._tabRuns[(i + 1)];
/*  915 */       int end = next != 0 ? next - 1 : tabCount - 1;
/*  916 */       for (int j = start; j <= end; j++) {
/*  917 */         if (((this._rects[j].intersects(rect)) || (!leftToRight)) && (j != selectedIndex)) {
/*  918 */           paintTab(g, tabPlacement, this._rects, j, iconRect, textRect);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  925 */     if ((selectedIndex >= 0) && (getRunForTab(tabCount, selectedIndex) == 0) && (
/*  926 */       (this._rects[selectedIndex].intersects(rect)) || (!leftToRight)))
/*  927 */       paintTab(g, tabPlacement, this._rects, selectedIndex, iconRect, textRect);
/*      */   }
/*      */ 
/*      */   protected void paintTabAreaBackground(Graphics g, Rectangle rect, int tabPlacement)
/*      */   {
/*  933 */     getPainter().paintTabAreaBackground(this._tabPane, g, rect, (tabPlacement == 1) || (tabPlacement == 3) ? 0 : 1, 0);
/*      */   }
/*      */ 
/*      */   protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect)
/*      */   {
/*  946 */     Rectangle tabRect = new Rectangle(rects[tabIndex]);
/*  947 */     if (((tabPlacement == 1) || (tabPlacement == 3)) && (!this._tabPane.getComponentOrientation().isLeftToRight())) {
/*  948 */       tabRect.x += this._tabScroller.viewport.getExpectedViewX();
/*      */     }
/*      */ 
/*  951 */     int selectedIndex = this._tabPane.getSelectedIndex();
/*  952 */     boolean isSelected = selectedIndex == tabIndex;
/*  953 */     boolean leftToRight = this._tabPane.getComponentOrientation().isLeftToRight();
/*      */ 
/*  955 */     paintTabBackground(g, tabPlacement, tabIndex, tabRect.x, tabRect.y, tabRect.width, tabRect.height, isSelected);
/*      */ 
/*  958 */     Object savedHints = JideSwingUtilities.setupShapeAntialiasing(g);
/*  959 */     paintTabBorder(g, tabPlacement, tabIndex, tabRect.x, tabRect.y, tabRect.width, tabRect.height, isSelected);
/*      */ 
/*  961 */     JideSwingUtilities.restoreShapeAntialiasing(g, savedHints);
/*      */ 
/*  963 */     Icon icon = this._tabPane.getIconForTab(tabIndex);
/*      */ 
/*  965 */     Rectangle tempTabRect = new Rectangle(tabRect);
/*      */ 
/*  967 */     if (this._tabPane.isShowGripper()) {
/*  968 */       if (leftToRight) {
/*  969 */         tempTabRect.x += this._gripperWidth;
/*      */       }
/*  971 */       tempTabRect.width -= this._gripperWidth;
/*  972 */       Rectangle gripperRect = new Rectangle(tabRect);
/*  973 */       if (leftToRight) {
/*  974 */         gripperRect.x += this._gripLeftMargin;
/*      */       }
/*      */       else {
/*  977 */         gripperRect.x = (tabRect.x + tabRect.width - this._gripLeftMargin - this._gripperWidth);
/*      */       }
/*  979 */       gripperRect.width = this._gripperWidth;
/*  980 */       if (this._gripperPainter != null) {
/*  981 */         this._gripperPainter.paint(this._tabPane, g, gripperRect, 0, isSelected ? 3 : 0);
/*      */       }
/*      */       else {
/*  984 */         getPainter().paintGripper(this._tabPane, g, gripperRect, 0, isSelected ? 3 : 0);
/*      */       }
/*      */     }
/*      */ 
/*  988 */     if ((isShowCloseButton()) && (isShowCloseButtonOnTab()) && (this._tabPane.isTabClosableAt(tabIndex)) && ((!this._tabPane.isShowCloseButtonOnSelectedTab()) || (isSelected)))
/*      */     {
/*  990 */       if ((tabPlacement == 1) || (tabPlacement == 3)) {
/*  991 */         int buttonWidth = this._closeButtons[tabIndex].getPreferredSize().width + this._closeButtonLeftMargin + this._closeButtonRightMargin;
/*  992 */         if (!(this._closeButtonAlignment == 10 ^ leftToRight)) {
/*  993 */           tempTabRect.x += buttonWidth;
/*      */         }
/*  995 */         tempTabRect.width -= buttonWidth;
/*      */       }
/*      */       else {
/*  998 */         int buttonHeight = this._closeButtons[tabIndex].getPreferredSize().height + this._closeButtonLeftMargin + this._closeButtonRightMargin;
/*  999 */         if (this._closeButtonAlignment == 10) {
/* 1000 */           tempTabRect.y += buttonHeight;
/* 1001 */           tempTabRect.height -= buttonHeight;
/*      */         }
/*      */         else {
/* 1004 */           tempTabRect.height -= buttonHeight;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1009 */     String title = getCurrentDisplayTitleAt(this._tabPane, tabIndex);
/*      */     Font font;
/*      */     Font font;
/* 1012 */     if ((isSelected) && (this._tabPane.getSelectedTabFont() != null)) {
/* 1013 */       font = this._tabPane.getSelectedTabFont();
/*      */     }
/*      */     else {
/* 1016 */       font = this._tabPane.getFont();
/*      */     }
/*      */ 
/* 1019 */     if ((isSelected) && (this._tabPane.isBoldActiveTab()) && (font.getStyle() != 1)) {
/* 1020 */       font = font.deriveFont(1);
/*      */     }
/*      */ 
/* 1023 */     FontMetrics metrics = g.getFontMetrics(font);
/*      */ 
/* 1028 */     layoutLabel(tabPlacement, metrics, tabIndex, title, icon, tempTabRect, iconRect, textRect, isSelected);
/*      */ 
/* 1031 */     if ((!this._isEditing) || (!isSelected)) {
/* 1032 */       paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
/*      */     }
/* 1034 */     paintIcon(g, tabPlacement, tabIndex, icon, iconRect, isSelected);
/*      */ 
/* 1036 */     paintFocusIndicator(g, tabPlacement, rects, tabIndex, iconRect, textRect, isSelected);
/*      */   }
/*      */ 
/*      */   private void paintCroppedTabEdge(Graphics g, int tabPlacement, int tabIndex, boolean isSelected, int x, int y)
/*      */   {
/*      */     int xx;
/* 1135 */     switch (tabPlacement) {
/*      */     case 2:
/*      */     case 4:
/* 1138 */       xx = x;
/* 1139 */       g.setColor(this._shadow);
/*      */     case 1:
/* 1140 */     case 3: } while (xx <= x + this._rects[tabIndex].width) {
/* 1141 */       for (int i = 0; i < this.xCropLen.length; i += 2) {
/* 1142 */         g.drawLine(xx + this.yCropLen[i], y - this.xCropLen[i], xx + this.yCropLen[(i + 1)] - 1, y - this.xCropLen[(i + 1)]);
/*      */       }
/*      */ 
/* 1145 */       xx += 12; continue;
/*      */ 
/* 1151 */       int yy = y;
/* 1152 */       g.setColor(this._shadow);
/* 1153 */       while (yy <= y + this._rects[tabIndex].height) {
/* 1154 */         for (int i = 0; i < this.xCropLen.length; i += 2) {
/* 1155 */           g.drawLine(x - this.xCropLen[i], yy + this.yCropLen[i], x - this.xCropLen[(i + 1)], yy + this.yCropLen[(i + 1)] - 1);
/*      */         }
/*      */ 
/* 1158 */         yy += 12;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void layoutLabel(int tabPlacement, FontMetrics metrics, int tabIndex, String title, Icon icon, Rectangle tabRect, Rectangle iconRect, Rectangle textRect, boolean isSelected)
/*      */   {
/* 1168 */     textRect.x = (textRect.y = iconRect.x = iconRect.y = 0);
/*      */ 
/* 1170 */     View v = getTextViewForTab(tabIndex);
/* 1171 */     if (v != null) {
/* 1172 */       this._tabPane.putClientProperty("html", v);
/*      */     }
/*      */ 
/* 1175 */     SwingUtilities.layoutCompoundLabel(this._tabPane, metrics, title, icon, 0, 0, 0, 11, tabRect, iconRect, textRect, this._textIconGap);
/*      */ 
/* 1186 */     this._tabPane.putClientProperty("html", null);
/*      */ 
/* 1188 */     if ((tabPlacement == 1) || (tabPlacement == 3)) {
/* 1189 */       if (iconRect.x - tabRect.x < this._iconMargin) {
/* 1190 */         tabRect.x += this._iconMargin;
/* 1191 */         textRect.x = (icon != null ? iconRect.x + iconRect.width + this._textIconGap : tabRect.x + this._textPadding);
/* 1192 */         iconRect.width = Math.min(iconRect.width, tabRect.width - this._tabRectPadding);
/* 1193 */         textRect.width = (tabRect.width - this._tabRectPadding - iconRect.width - (icon != null ? this._textIconGap + this._iconMargin : this._noIconMargin));
/*      */       }
/*      */ 
/* 1196 */       if ((getTabResizeMode() == 4) && (isShowCloseButton()) && (isShowCloseButtonOnTab()))
/*      */       {
/* 1198 */         if ((!this._tabPane.isShowCloseButtonOnSelectedTab()) && 
/* 1199 */           (!isSelected)) {
/* 1200 */           iconRect.width = (iconRect.width + this._closeButtons[tabIndex].getPreferredSize().width + this._closeButtonMarginHorizon);
/* 1201 */           textRect.width = 0;
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1207 */       tabRect.y += this._iconMargin;
/* 1208 */       textRect.y = (icon != null ? iconRect.y + iconRect.height + this._textIconGap : tabRect.y + this._textPadding);
/* 1209 */       tabRect.x += 2;
/* 1210 */       tabRect.x += 2;
/* 1211 */       tabRect.width -= this._textMarginVertical;
/* 1212 */       textRect.height = (tabRect.height - this._tabRectPadding - iconRect.height - (icon != null ? this._textIconGap + this._iconMargin : this._noIconMargin));
/*      */ 
/* 1214 */       if ((getTabResizeMode() == 4) && (isShowCloseButton()) && (isShowCloseButtonOnTab()))
/*      */       {
/* 1216 */         if ((!this._tabPane.isShowCloseButtonOnSelectedTab()) && 
/* 1217 */           (!isSelected)) {
/* 1218 */           iconRect.height = (iconRect.height + this._closeButtons[tabIndex].getPreferredSize().height + this._closeButtonMarginVertical);
/* 1219 */           textRect.height = 0;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintIcon(Graphics g, int tabPlacement, int tabIndex, Icon icon, Rectangle iconRect, boolean isSelected)
/*      */   {
/* 1230 */     if ((icon != null) && (iconRect.width >= icon.getIconWidth()))
/* 1231 */       if ((tabPlacement == 1) || (tabPlacement == 3)) {
/* 1232 */         icon.paintIcon(this._tabPane, g, iconRect.x, iconRect.y);
/*      */       }
/* 1235 */       else if (iconRect.height < this._rects[tabIndex].height - this._gripperHeight)
/* 1236 */         icon.paintIcon(this._tabPane, g, iconRect.x, iconRect.y);
/*      */   }
/*      */ 
/*      */   protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected)
/*      */   {
/* 1246 */     Graphics2D g2d = (Graphics2D)g.create();
/* 1247 */     if ((isSelected) && (this._tabPane.isBoldActiveTab())) {
/* 1248 */       g2d.setFont(font.deriveFont(1));
/*      */     }
/*      */     else {
/* 1251 */       g2d.setFont(font);
/*      */     }
/*      */ 
/* 1254 */     String actualText = title;
/*      */ 
/* 1256 */     if ((tabPlacement == 1) || (tabPlacement == 3)) {
/* 1257 */       if (textRect.width <= 0) {
/* 1258 */         return;
/*      */       }
/* 1260 */       while (SwingUtilities.computeStringWidth(metrics, actualText) > textRect.width) {
/* 1261 */         actualText = actualText.substring(0, actualText.length() - 1);
/*      */       }
/* 1263 */       if (!actualText.equals(title))
/* 1264 */         if (actualText.length() >= 2)
/* 1265 */           actualText = actualText.substring(0, actualText.length() - 2) + "..";
/*      */         else
/* 1267 */           actualText = "";
/*      */     }
/*      */     else
/*      */     {
/* 1271 */       if (textRect.height <= 0) {
/* 1272 */         return;
/*      */       }
/* 1274 */       while (SwingUtilities.computeStringWidth(metrics, actualText) > textRect.height) {
/* 1275 */         actualText = actualText.substring(0, actualText.length() - 1);
/*      */       }
/* 1277 */       if (!actualText.equals(title)) {
/* 1278 */         if (actualText.length() >= 2)
/* 1279 */           actualText = actualText.substring(0, actualText.length() - 2) + "..";
/*      */         else {
/* 1281 */           actualText = "";
/*      */         }
/*      */       }
/*      */     }
/* 1285 */     View v = getTextViewForTab(tabIndex);
/* 1286 */     if (v != null)
/*      */     {
/* 1288 */       v.paint(g2d, textRect);
/*      */     }
/*      */     else
/*      */     {
/* 1292 */       int mnemIndex = this._tabPane.getDisplayedMnemonicIndexAt(tabIndex);
/* 1293 */       JideTabbedPane.ColorProvider colorProvider = this._tabPane.getTabColorProvider();
/* 1294 */       if ((this._tabPane.isEnabled()) && (this._tabPane.isEnabledAt(tabIndex))) {
/* 1295 */         if (colorProvider != null) {
/* 1296 */           g2d.setColor(colorProvider.getForegroudAt(tabIndex));
/*      */         }
/*      */         else {
/* 1299 */           Color color = this._tabPane.getForegroundAt(tabIndex);
/* 1300 */           if ((isSelected) && (showFocusIndicator())) {
/* 1301 */             if (!(color instanceof ColorUIResource)) {
/* 1302 */               g2d.setColor(color);
/*      */             }
/*      */             else {
/* 1305 */               g2d.setColor(this._activeTabForeground);
/*      */             }
/*      */ 
/*      */           }
/* 1309 */           else if (!(color instanceof ColorUIResource)) {
/* 1310 */             g2d.setColor(color);
/*      */           }
/*      */           else {
/* 1313 */             g2d.setColor(this._inactiveTabForeground);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1318 */         if ((tabPlacement == 1) || (tabPlacement == 3)) {
/* 1319 */           JideSwingUtilities.drawStringUnderlineCharAt(this._tabPane, g2d, actualText, mnemIndex, textRect.x, textRect.y + metrics.getAscent());
/*      */         }
/*      */         else {
/* 1322 */           AffineTransform old = g2d.getTransform();
/* 1323 */           g2d.translate(textRect.x, textRect.y);
/* 1324 */           if (tabPlacement == 4) {
/* 1325 */             g2d.rotate(1.570796326794897D);
/* 1326 */             g2d.translate(0, -textRect.width);
/*      */           }
/*      */           else {
/* 1329 */             g2d.rotate(-1.570796326794897D);
/* 1330 */             g2d.translate(-textRect.height + metrics.getHeight() / 2 + this._rectSizeExtend, 0);
/*      */           }
/* 1332 */           JideSwingUtilities.drawStringUnderlineCharAt(this._tabPane, g2d, actualText, mnemIndex, 0, (textRect.width - metrics.getHeight()) / 2 + metrics.getAscent());
/*      */ 
/* 1334 */           g2d.setTransform(old);
/*      */         }
/*      */ 
/*      */       }
/* 1338 */       else if ((tabPlacement == 1) || (tabPlacement == 3)) {
/* 1339 */         g2d.setColor(this._tabPane.getBackgroundAt(tabIndex).brighter());
/* 1340 */         JideSwingUtilities.drawStringUnderlineCharAt(this._tabPane, g2d, actualText, mnemIndex, textRect.x, textRect.y + metrics.getAscent());
/* 1341 */         g2d.setColor(this._tabPane.getBackgroundAt(tabIndex).darker());
/* 1342 */         JideSwingUtilities.drawStringUnderlineCharAt(this._tabPane, g2d, actualText, mnemIndex, textRect.x - 1, textRect.y + metrics.getAscent() - 1);
/*      */       }
/*      */       else {
/* 1345 */         AffineTransform old = g2d.getTransform();
/* 1346 */         g2d.translate(textRect.x, textRect.y);
/* 1347 */         if (tabPlacement == 4) {
/* 1348 */           g2d.rotate(1.570796326794897D);
/* 1349 */           g2d.translate(0, -textRect.width);
/*      */         }
/*      */         else {
/* 1352 */           g2d.rotate(-1.570796326794897D);
/* 1353 */           g2d.translate(-textRect.height + metrics.getHeight() / 2 + this._rectSizeExtend, 0);
/*      */         }
/* 1355 */         g2d.setColor(this._tabPane.getBackgroundAt(tabIndex).brighter());
/* 1356 */         JideSwingUtilities.drawStringUnderlineCharAt(this._tabPane, g2d, actualText, mnemIndex, 0, (textRect.width - metrics.getHeight()) / 2 + metrics.getAscent());
/*      */ 
/* 1358 */         g2d.setColor(this._tabPane.getBackgroundAt(tabIndex).darker());
/* 1359 */         JideSwingUtilities.drawStringUnderlineCharAt(this._tabPane, g2d, actualText, mnemIndex, tabPlacement == 4 ? -1 : 1, (textRect.width - metrics.getHeight()) / 2 + metrics.getAscent() - 1);
/*      */ 
/* 1361 */         g2d.setTransform(old);
/*      */       }
/*      */     }
/*      */ 
/* 1365 */     g2d.dispose();
/*      */   }
/*      */ 
/*      */   protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */   {
/* 1386 */     switch (getTabShape()) {
/*      */     case 3:
/* 1388 */       paintBoxTabBorder(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
/* 1389 */       break;
/*      */     case 8:
/* 1391 */       paintExcelTabBorder(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
/* 1392 */       break;
/*      */     case 1:
/* 1394 */       paintWindowsTabBorder(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
/* 1395 */       break;
/*      */     case 11:
/* 1397 */       if (!isSelected) break;
/* 1398 */       paintWindowsTabBorder(g, tabPlacement, tabIndex, x, y, w, h, isSelected); break;
/*      */     case 2:
/* 1402 */       paintVsnetTabBorder(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
/* 1403 */       break;
/*      */     case 9:
/* 1405 */       paintRoundedVsnetTabBorder(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
/* 1406 */       break;
/*      */     case 5:
/* 1408 */       paintFlatTabBorder(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
/* 1409 */       break;
/*      */     case 10:
/* 1411 */       paintRoundedFlatTabBorder(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
/* 1412 */       break;
/*      */     case 4:
/*      */     case 6:
/*      */     case 7:
/*      */     default:
/* 1415 */       paintOffice2003TabBorder(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
/*      */     }
/*      */ 
/* 1418 */     int tabShape = getTabShape();
/* 1419 */     if (tabShape == 1) {
/* 1420 */       if ((this._mouseEnter) && (this._tabPane.getColorTheme() == 4) && (tabIndex == this._indexMouseOver) && (!isSelected) && (this._tabPane.isEnabledAt(this._indexMouseOver)))
/*      */       {
/* 1422 */         paintTabBorderMouseOver(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
/*      */       }
/*      */     }
/* 1425 */     else if ((tabShape == 11) && 
/* 1426 */       (this._mouseEnter) && (tabIndex == this._indexMouseOver) && (!isSelected) && (this._tabPane.isEnabledAt(this._indexMouseOver)))
/* 1427 */       paintTabBorderMouseOver(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
/*      */   }
/*      */ 
/*      */   protected void paintOffice2003TabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */   {
/* 1434 */     boolean leftToRight = this._tabPane.getComponentOrientation().isLeftToRight();
/*      */ 
/* 1436 */     switch (tabPlacement) {
/*      */     case 2:
/* 1438 */       y += 2;
/* 1439 */       if (isSelected) {
/* 1440 */         g.setColor(this._selectColor1);
/*      */ 
/* 1442 */         g.drawLine(x, y + 3, x, y + h - 5);
/* 1443 */         g.drawLine(x + 1, y + h - 4, x + 1, y + h - 4);
/*      */ 
/* 1445 */         g.drawLine(x + 2, y + h - 3, x + w - 1, y + h - 3);
/*      */ 
/* 1447 */         g.drawLine(x + 1, y + 2, x + 1, y + 1);
/* 1448 */         g.drawLine(x + 2, y, x + 2, y - 1);
/*      */ 
/* 1450 */         for (int i = 0; i < w - 4; i++) {
/* 1451 */           g.drawLine(x + 3 + i, y - 2 - i, x + 3 + i, y - 2 - i);
/*      */         }
/*      */ 
/* 1454 */         g.drawLine(x + w - 1, y - w + 1, x + w - 1, y - w + 2);
/*      */ 
/* 1456 */         g.setColor(this._selectColor2);
/*      */ 
/* 1458 */         g.drawLine(x + 1, y + 3, x + 1, y + h - 5);
/* 1459 */         g.drawLine(x + 2, y + h - 4, x + w - 1, y + h - 4);
/*      */ 
/* 1461 */         g.drawLine(x + 2, y + 2, x + 2, y + 1);
/* 1462 */         g.drawLine(x + 3, y, x + 3, y - 1);
/*      */ 
/* 1464 */         for (int i = 0; i < w - 4; i++) {
/* 1465 */           g.drawLine(x + 4 + i, y - 2 - i, x + 4 + i, y - 2 - i);
/*      */         }
/*      */ 
/*      */       }
/* 1470 */       else if (tabIndex == 0) {
/* 1471 */         g.setColor(this._unselectColor1);
/*      */ 
/* 1473 */         g.drawLine(x, y + 3, x, y + h - 5);
/* 1474 */         g.drawLine(x + 1, y + h - 4, x + 1, y + h - 4);
/*      */ 
/* 1476 */         g.drawLine(x + 2, y + h - 3, x + w - 1, y + h - 3);
/*      */ 
/* 1478 */         g.drawLine(x + 1, y + 2, x + 1, y + 1);
/* 1479 */         g.drawLine(x + 2, y, x + 2, y - 1);
/*      */ 
/* 1481 */         for (int i = 0; i < w - 4; i++) {
/* 1482 */           g.drawLine(x + 3 + i, y - 2 - i, x + 3 + i, y - 2 - i);
/*      */         }
/*      */ 
/* 1485 */         g.drawLine(x + w - 1, y - w + 1, x + w - 1, y - w + 2);
/*      */ 
/* 1487 */         if (this._unselectColor2 != null) {
/* 1488 */           g.setColor(this._unselectColor2);
/*      */ 
/* 1490 */           g.drawLine(x + 1, y + 3, x + 1, y + h - 6);
/*      */ 
/* 1492 */           g.drawLine(x + 2, y + 2, x + 2, y + 1);
/* 1493 */           g.drawLine(x + 3, y, x + 3, y - 1);
/*      */ 
/* 1495 */           for (int i = 0; i < w - 4; i++) {
/* 1496 */             g.drawLine(x + 4 + i, y - 2 - i, x + 4 + i, y - 2 - i);
/*      */           }
/*      */ 
/* 1499 */           g.setColor(getPainter().getControlDk());
/*      */         }
/*      */ 
/* 1502 */         if (this._unselectColor3 == null) break;
/* 1503 */         g.setColor(this._unselectColor3);
/*      */ 
/* 1505 */         g.drawLine(x + 2, y + h - 4, x + w - 1, y + h - 4);
/* 1506 */         g.drawLine(x + 1, y + h - 5, x + 1, y + h - 5);
/*      */       }
/*      */       else
/*      */       {
/* 1511 */         g.setColor(this._unselectColor1);
/*      */ 
/* 1513 */         g.drawLine(x, y + 3, x, y + h - 5);
/* 1514 */         g.drawLine(x + 1, y + h - 4, x + 1, y + h - 4);
/*      */ 
/* 1516 */         g.drawLine(x + 2, y + h - 3, x + w - 1, y + h - 3);
/*      */ 
/* 1518 */         g.drawLine(x + 1, y + 2, x + 1, y + 1);
/* 1519 */         g.drawLine(x + 2, y, x + 2, y - 1);
/* 1520 */         g.drawLine(x + 3, y - 2, x + 3, y - 2);
/*      */ 
/* 1522 */         if (this._unselectColor2 != null) {
/* 1523 */           g.setColor(this._unselectColor2);
/*      */ 
/* 1525 */           g.drawLine(x + 1, y + 3, x + 1, y + h - 6);
/*      */ 
/* 1527 */           g.drawLine(x + 2, y + 2, x + 2, y + 1);
/* 1528 */           g.drawLine(x + 3, y, x + 3, y - 1);
/* 1529 */           g.drawLine(x + 4, y - 2, x + 4, y - 2);
/*      */         }
/*      */ 
/* 1533 */         if (this._unselectColor3 == null) break;
/* 1534 */         g.setColor(this._unselectColor3);
/*      */ 
/* 1536 */         g.drawLine(x + 2, y + h - 4, x + w - 1, y + h - 4);
/* 1537 */         g.drawLine(x + 1, y + h - 5, x + 1, y + h - 5); } break;
/*      */     case 4:
/* 1543 */       if (isSelected) {
/* 1544 */         g.setColor(this._selectColor1);
/*      */ 
/* 1546 */         g.drawLine(x + w - 1, y + 5, x + w - 1, y + h - 3);
/* 1547 */         g.drawLine(x + w - 2, y + h - 2, x + w - 2, y + h - 2);
/*      */ 
/* 1549 */         g.drawLine(x + w - 3, y + h - 1, x, y + h - 1);
/*      */ 
/* 1551 */         g.drawLine(x + w - 2, y + 4, x + w - 2, y + 3);
/* 1552 */         g.drawLine(x + w - 3, y + 2, x + w - 3, y + 1);
/*      */ 
/* 1554 */         for (int i = 0; i < w - 4; i++) {
/* 1555 */           g.drawLine(x + w - 4 - i, y - i, x + w - 4 - i, y - i);
/*      */         }
/*      */ 
/* 1558 */         g.drawLine(x, y - w + 3, x, y - w + 4);
/*      */ 
/* 1560 */         g.setColor(this._selectColor2);
/*      */ 
/* 1562 */         g.drawLine(x + w - 2, y + 5, x + w - 2, y + h - 3);
/* 1563 */         g.drawLine(x + w - 3, y + h - 2, x, y + h - 2);
/*      */ 
/* 1565 */         g.drawLine(x + w - 3, y + 4, x + w - 3, y + 3);
/* 1566 */         g.drawLine(x + w - 4, y + 2, x + w - 4, y + 1);
/*      */ 
/* 1568 */         for (int i = 0; i < w - 4; i++) {
/* 1569 */           g.drawLine(x + w - 5 - i, y - i, x + w - 5 - i, y - i);
/*      */         }
/*      */ 
/*      */       }
/* 1573 */       else if (tabIndex == 0) {
/* 1574 */         g.setColor(this._unselectColor1);
/*      */ 
/* 1576 */         g.drawLine(x + w - 1, y + 5, x + w - 1, y + h - 3);
/* 1577 */         g.drawLine(x + w - 2, y + h - 2, x + w - 2, y + h - 2);
/*      */ 
/* 1579 */         g.drawLine(x + w - 3, y + h - 1, x, y + h - 1);
/*      */ 
/* 1581 */         g.drawLine(x + w - 2, y + 4, x + w - 2, y + 3);
/* 1582 */         g.drawLine(x + w - 3, y + 2, x + w - 3, y + 1);
/*      */ 
/* 1584 */         for (int i = 0; i < w - 4; i++) {
/* 1585 */           g.drawLine(x + w - 4 - i, y - i, x + w - 4 - i, y - i);
/*      */         }
/*      */ 
/* 1588 */         g.drawLine(x, y - w + 3, x, y - w + 4);
/*      */ 
/* 1590 */         if (this._unselectColor2 != null) {
/* 1591 */           g.setColor(this._unselectColor2);
/*      */ 
/* 1593 */           g.drawLine(x + w - 2, y + 5, x + w - 2, y + h - 4);
/*      */ 
/* 1595 */           g.drawLine(x + w - 3, y + 4, x + w - 3, y + 3);
/*      */ 
/* 1597 */           g.drawLine(x + w - 4, y + 2, x + w - 4, y + 1);
/*      */ 
/* 1599 */           for (int i = 0; i < w - 4; i++) {
/* 1600 */             g.drawLine(x + w - 5 - i, y - i, x + w - 5 - i, y - i);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1605 */         if (this._unselectColor3 == null) break;
/* 1606 */         g.setColor(this._unselectColor3);
/*      */ 
/* 1608 */         g.drawLine(x + w - 2, y + h - 3, x + w - 2, y + h - 3);
/*      */ 
/* 1610 */         g.drawLine(x + w - 3, y + h - 2, x, y + h - 2);
/*      */       }
/*      */       else
/*      */       {
/* 1615 */         g.setColor(this._unselectColor1);
/*      */ 
/* 1617 */         g.drawLine(x + w - 1, y + 5, x + w - 1, y + h - 3);
/* 1618 */         g.drawLine(x + w - 2, y + h - 2, x + w - 2, y + h - 2);
/*      */ 
/* 1620 */         g.drawLine(x + w - 3, y + h - 1, x, y + h - 1);
/*      */ 
/* 1622 */         g.drawLine(x + w - 2, y + 4, x + w - 2, y + 3);
/* 1623 */         g.drawLine(x + w - 3, y + 2, x + w - 3, y + 1);
/* 1624 */         g.drawLine(x + w - 4, y, x + w - 4, y);
/*      */ 
/* 1626 */         if (this._unselectColor2 != null) {
/* 1627 */           g.setColor(this._unselectColor2);
/*      */ 
/* 1629 */           g.drawLine(x + w - 2, y + 5, x + w - 2, y + h - 4);
/*      */ 
/* 1631 */           g.drawLine(x + w - 3, y + 4, x + w - 3, y + 3);
/*      */ 
/* 1633 */           g.drawLine(x + w - 4, y + 2, x + w - 4, y + 1);
/* 1634 */           g.drawLine(x + w - 5, y, x + w - 5, y);
/*      */         }
/*      */ 
/* 1637 */         if (this._unselectColor3 == null) break;
/* 1638 */         g.setColor(this._unselectColor3);
/*      */ 
/* 1640 */         g.drawLine(x + w - 2, y + h - 3, x + w - 2, y + h - 3);
/*      */ 
/* 1642 */         g.drawLine(x + w - 3, y + h - 2, x, y + h - 2); } break;
/*      */     case 3:
/* 1648 */       if (leftToRight) {
/* 1649 */         if (isSelected) {
/* 1650 */           g.setColor(this._selectColor1);
/*      */ 
/* 1652 */           g.drawLine(x + w - 1, y + h - 3, x + w - 1, y);
/*      */ 
/* 1654 */           g.drawLine(x + w - 2, y + h - 2, x + w - 2, y + h - 2);
/*      */ 
/* 1657 */           g.drawLine(x + 5, y + h - 1, x + w - 3, y + h - 1);
/*      */ 
/* 1659 */           g.drawLine(x + 3, y + h - 2, x + 4, y + h - 2);
/* 1660 */           g.drawLine(x + 1, y + h - 3, x + 2, y + h - 3);
/* 1661 */           g.drawLine(x, y + h - 4, x, y + h - 4);
/*      */ 
/* 1664 */           for (int i = 3; i < h - 2; i++) {
/* 1665 */             g.drawLine(x + 2 - i, y + h - 2 - i, x + 2 - i, y + h - 2 - i);
/*      */           }
/*      */ 
/* 1669 */           g.drawLine(x - h + 3, y, x - h + 4, y);
/*      */ 
/* 1671 */           g.setColor(this._selectColor2);
/*      */ 
/* 1673 */           g.drawLine(x + 5, y + h - 2, x + w - 3, y + h - 2);
/*      */ 
/* 1675 */           g.drawLine(x + w - 2, y, x + w - 2, y + h - 3);
/*      */ 
/* 1677 */           g.drawLine(x + 3, y + h - 3, x + 4, y + h - 3);
/* 1678 */           g.drawLine(x + 1, y + h - 4, x + 2, y + h - 4);
/* 1679 */           g.drawLine(x, y + h - 5, x, y + h - 5);
/*      */ 
/* 1681 */           for (int i = 3; i < h - 2; i++) {
/* 1682 */             g.drawLine(x + 2 - i, y + h - 3 - i, x + 2 - i, y + h - 3 - i);
/*      */           }
/*      */ 
/*      */         }
/* 1688 */         else if (tabIndex == 0) {
/* 1689 */           g.setColor(this._unselectColor1);
/*      */ 
/* 1691 */           g.drawLine(x + w - 1, y + h - 3, x + w - 1, y);
/*      */ 
/* 1693 */           g.drawLine(x + w - 2, y + h - 2, x + w - 2, y + h - 2);
/*      */ 
/* 1696 */           g.drawLine(x + 5, y + h - 1, x + w - 3, y + h - 1);
/*      */ 
/* 1698 */           g.drawLine(x + 3, y + h - 2, x + 4, y + h - 2);
/* 1699 */           g.drawLine(x + 1, y + h - 3, x + 2, y + h - 3);
/* 1700 */           g.drawLine(x, y + h - 4, x, y + h - 4);
/*      */ 
/* 1703 */           for (int i = 3; i < h - 2; i++) {
/* 1704 */             g.drawLine(x + 2 - i, y + h - 2 - i, x + 2 - i, y + h - 2 - i);
/*      */           }
/*      */ 
/* 1708 */           g.drawLine(x - h + 3, y, x - h + 4, y);
/*      */ 
/* 1710 */           if (this._unselectColor2 != null) {
/* 1711 */             g.setColor(this._unselectColor2);
/*      */ 
/* 1713 */             g.drawLine(x + 3, y + h - 3, x + 4, y + h - 3);
/*      */ 
/* 1715 */             g.drawLine(x + 1, y + h - 4, x + 2, y + h - 4);
/*      */ 
/* 1717 */             g.drawLine(x, y + h - 5, x, y + h - 5);
/*      */ 
/* 1720 */             for (int i = 3; i < h - 2; i++) {
/* 1721 */               g.drawLine(x + 2 - i, y + h - 3 - i, x + 2 - i, y + h - 3 - i);
/*      */             }
/*      */ 
/* 1725 */             g.drawLine(x + 5, y + h - 2, x + w - 4, y + h - 2);
/*      */           }
/*      */ 
/* 1729 */           if (this._unselectColor3 == null) break;
/* 1730 */           g.setColor(this._unselectColor3);
/*      */ 
/* 1732 */           g.drawLine(x + w - 3, y + h - 2, x + w - 3, y + h - 2);
/* 1733 */           g.drawLine(x + w - 2, y + h - 3, x + w - 2, y);
/*      */         }
/*      */         else
/*      */         {
/* 1737 */           g.setColor(this._unselectColor1);
/*      */ 
/* 1739 */           g.drawLine(x + 5, y + h - 1, x + w - 3, y + h - 1);
/*      */ 
/* 1741 */           g.drawLine(x + w - 1, y + h - 3, x + w - 1, y);
/*      */ 
/* 1743 */           g.drawLine(x + w - 2, y + h - 2, x + w - 2, y + h - 2);
/*      */ 
/* 1746 */           g.drawLine(x + 3, y + h - 2, x + 4, y + h - 2);
/* 1747 */           g.drawLine(x + 1, y + h - 3, x + 2, y + h - 3);
/* 1748 */           g.drawLine(x, y + h - 4, x, y + h - 4);
/*      */ 
/* 1750 */           if (this._unselectColor2 != null) {
/* 1751 */             g.setColor(this._unselectColor2);
/*      */ 
/* 1753 */             g.drawLine(x + 3, y + h - 3, x + 4, y + h - 3);
/*      */ 
/* 1755 */             g.drawLine(x + 1, y + h - 4, x + 2, y + h - 4);
/*      */ 
/* 1757 */             g.drawLine(x, y + h - 5, x, y + h - 5);
/*      */ 
/* 1759 */             g.drawLine(x + 5, y + h - 2, x + w - 4, y + h - 2);
/*      */           }
/*      */ 
/* 1762 */           if (this._unselectColor3 == null) break;
/* 1763 */           g.setColor(this._unselectColor3);
/*      */ 
/* 1765 */           g.drawLine(x + w - 3, y + h - 2, x + w - 3, y + h - 2);
/* 1766 */           g.drawLine(x + w - 2, y + h - 3, x + w - 2, y);
/*      */         }
/*      */ 
/*      */       }
/* 1772 */       else if (isSelected) {
/* 1773 */         g.setColor(this._selectColor1);
/*      */ 
/* 1775 */         g.drawLine(x, y + h - 3, x, y);
/*      */ 
/* 1777 */         g.drawLine(x + 1, y + h - 2, x + 1, y + h - 2);
/*      */ 
/* 1780 */         g.drawLine(x + w - 6, y + h - 1, x + 2, y + h - 1);
/*      */ 
/* 1782 */         g.drawLine(x + w - 4, y + h - 2, x + w - 5, y + h - 2);
/* 1783 */         g.drawLine(x + w - 2, y + h - 3, x + w - 3, y + h - 3);
/* 1784 */         g.drawLine(x + w - 1, y + h - 4, x + w - 1, y + h - 4);
/*      */ 
/* 1787 */         for (int i = 3; i < h - 2; i++) {
/* 1788 */           g.drawLine(x + w - 3 + i, y + h - 2 - i, x + w - 3 + i, y + h - 2 - i);
/*      */         }
/*      */ 
/* 1791 */         g.drawLine(x + w - 4 + h, y, x + w - 5 + h, y);
/*      */ 
/* 1793 */         g.setColor(this._selectColor2);
/*      */ 
/* 1795 */         g.drawLine(x + w - 6, y + h - 2, x + 2, y + h - 2);
/*      */ 
/* 1797 */         g.drawLine(x + 1, y, x + 1, y + h - 3);
/*      */ 
/* 1799 */         g.drawLine(x + w - 4, y + h - 3, x + w - 5, y + h - 3);
/* 1800 */         g.drawLine(x + w - 2, y + h - 4, x + w - 3, y + h - 4);
/* 1801 */         g.drawLine(x + w - 1, y + h - 5, x + w - 1, y + h - 5);
/*      */ 
/* 1803 */         for (int i = 3; i < h - 2; i++) {
/* 1804 */           g.drawLine(x + w - 3 + i, y + h - 3 - i, x + w - 3 + i, y + h - 3 - i);
/*      */         }
/*      */ 
/*      */       }
/* 1808 */       else if (tabIndex == 0) {
/* 1809 */         g.setColor(this._unselectColor1);
/*      */ 
/* 1811 */         g.drawLine(x, y + h - 3, x, y);
/*      */ 
/* 1813 */         g.drawLine(x + 1, y + h - 2, x + 1, y + h - 2);
/*      */ 
/* 1816 */         g.drawLine(x + w - 6, y + h - 1, x + 2, y + h - 1);
/*      */ 
/* 1818 */         g.drawLine(x + w - 4, y + h - 2, x + w - 5, y + h - 2);
/* 1819 */         g.drawLine(x + w - 2, y + h - 3, x + w - 3, y + h - 3);
/* 1820 */         g.drawLine(x + w - 1, y + h - 4, x + w - 1, y + h - 4);
/*      */ 
/* 1823 */         for (int i = 3; i < h - 2; i++) {
/* 1824 */           g.drawLine(x + w - 3 + i, y + h - 2 - i, x + w - 3 + i, y + h - 2 - i);
/*      */         }
/*      */ 
/* 1827 */         g.drawLine(x + w - 4 + h, y, x + w - 5 + h, y);
/*      */ 
/* 1829 */         if (this._unselectColor2 != null) {
/* 1830 */           g.setColor(this._unselectColor2);
/*      */ 
/* 1832 */           g.drawLine(x + w - 4, y + h - 3, x + w - 5, y + h - 3);
/*      */ 
/* 1834 */           g.drawLine(x + w - 2, y + h - 4, x + w - 3, y + h - 4);
/*      */ 
/* 1836 */           g.drawLine(x + w - 1, y + h - 5, x + w - 1, y + h - 5);
/*      */ 
/* 1839 */           for (int i = 3; i < h - 2; i++) {
/* 1840 */             g.drawLine(x + w - 3 + i, y + h - 3 - i, x + w - 3 + i, y + h - 3 - i);
/*      */           }
/*      */ 
/* 1843 */           g.drawLine(x + w - 6, y + h - 2, x + 3, y + h - 2);
/*      */         }
/*      */ 
/* 1846 */         if (this._unselectColor3 == null) break;
/* 1847 */         g.setColor(this._unselectColor3);
/*      */ 
/* 1849 */         g.drawLine(x + 2, y + h - 2, x + 2, y + h - 2);
/* 1850 */         g.drawLine(x + 1, y + h - 3, x + 1, y);
/*      */       }
/*      */       else
/*      */       {
/* 1854 */         g.setColor(this._unselectColor1);
/*      */ 
/* 1856 */         g.drawLine(x + w - 6, y + h - 1, x + 2, y + h - 1);
/*      */ 
/* 1858 */         g.drawLine(x, y + h - 3, x, y);
/*      */ 
/* 1860 */         g.drawLine(x + 1, y + h - 2, x + 1, y + h - 2);
/*      */ 
/* 1863 */         g.drawLine(x + w - 4, y + h - 2, x + w - 5, y + h - 2);
/* 1864 */         g.drawLine(x + w - 2, y + h - 3, x + w - 3, y + h - 3);
/* 1865 */         g.drawLine(x + w - 1, y + h - 4, x + w - 1, y + h - 4);
/*      */ 
/* 1867 */         if (this._unselectColor2 != null) {
/* 1868 */           g.setColor(this._unselectColor2);
/*      */ 
/* 1870 */           g.drawLine(x + w - 4, y + h - 3, x + w - 5, y + h - 3);
/*      */ 
/* 1872 */           g.drawLine(x + w - 2, y + h - 4, x + w - 3, y + h - 4);
/*      */ 
/* 1874 */           g.drawLine(x + w - 1, y + h - 5, x + w - 1, y + h - 5);
/*      */ 
/* 1876 */           g.drawLine(x + w - 6, y + h - 2, x + 3, y + h - 2);
/*      */         }
/*      */ 
/* 1879 */         if (this._unselectColor3 == null) break;
/* 1880 */         g.setColor(this._unselectColor3);
/*      */ 
/* 1882 */         g.drawLine(x + 2, y + h - 2, x + 2, y + h - 2);
/* 1883 */         g.drawLine(x + 1, y + h - 3, x + 1, y); } break;
/*      */     case 1:
/*      */     default:
/* 1891 */       if (leftToRight) {
/* 1892 */         if (isSelected) {
/* 1893 */           g.setColor(this._selectColor1);
/*      */ 
/* 1895 */           g.drawLine(x + 3, y + 1, x + 4, y + 1);
/* 1896 */           g.drawLine(x + 1, y + 2, x + 2, y + 2);
/* 1897 */           g.drawLine(x, y + 3, x, y + 3);
/*      */ 
/* 1899 */           g.drawLine(x + 5, y, x + w - 3, y);
/*      */ 
/* 1901 */           g.drawLine(x + w - 2, y + 1, x + w - 2, y + 1);
/*      */ 
/* 1903 */           g.drawLine(x + w - 1, y + 2, x + w - 1, y + h - 1);
/*      */ 
/* 1906 */           for (int i = 3; i < h - 2; i++) {
/* 1907 */             g.drawLine(x + 2 - i, y + 1 + i, x + 2 - i, y + 1 + i);
/*      */           }
/* 1909 */           g.drawLine(x - h + 3, y + h - 1, x - h + 4, y + h - 1);
/*      */ 
/* 1911 */           g.setColor(this._selectColor2);
/*      */ 
/* 1913 */           g.drawLine(x + 3, y + 2, x + 4, y + 2);
/* 1914 */           g.drawLine(x + 1, y + 3, x + 2, y + 3);
/* 1915 */           g.drawLine(x, y + 4, x, y + 4);
/*      */ 
/* 1917 */           g.drawLine(x + 5, y + 1, x + w - 3, y + 1);
/*      */ 
/* 1919 */           g.drawLine(x + w - 2, y + 2, x + w - 2, y + h - 1);
/*      */ 
/* 1922 */           for (int i = 3; i < h - 2; i++) {
/* 1923 */             g.drawLine(x + 2 - i, y + 2 + i, x + 2 - i, y + 2 + i);
/*      */           }
/*      */ 
/*      */         }
/* 1927 */         else if (tabIndex == 0) {
/* 1928 */           g.setColor(this._unselectColor1);
/*      */ 
/* 1930 */           g.drawLine(x + 3, y + 1, x + 4, y + 1);
/* 1931 */           g.drawLine(x + 1, y + 2, x + 2, y + 2);
/* 1932 */           g.drawLine(x, y + 3, x, y + 3);
/*      */ 
/* 1934 */           g.drawLine(x + 5, y, x + w - 3, y);
/*      */ 
/* 1936 */           g.drawLine(x + w - 2, y + 1, x + w - 2, y + 1);
/*      */ 
/* 1938 */           g.drawLine(x + w - 1, y + 2, x + w - 1, y + h - 1);
/*      */ 
/* 1941 */           for (int i = 3; i < h - 2; i++) {
/* 1942 */             g.drawLine(x + 2 - i, y + 1 + i, x + 2 - i, y + 1 + i);
/*      */           }
/* 1944 */           g.drawLine(x - h + 3, y + h - 1, x - h + 4, y + h - 1);
/*      */ 
/* 1946 */           if (this._unselectColor2 != null) {
/* 1947 */             g.setColor(this._unselectColor2);
/*      */ 
/* 1949 */             g.drawLine(x + 3, y + 2, x + 4, y + 2);
/* 1950 */             g.drawLine(x + 1, y + 3, x + 2, y + 3);
/* 1951 */             g.drawLine(x, y + 4, x, y + 4);
/*      */ 
/* 1954 */             for (int i = 3; i < h - 2; i++) {
/* 1955 */               g.drawLine(x + 2 - i, y + 2 + i, x + 2 - i, y + 2 + i);
/*      */             }
/*      */ 
/* 1959 */             g.drawLine(x + 5, y + 1, x + w - 4, y + 1);
/*      */           }
/*      */ 
/* 1962 */           if (this._unselectColor3 == null) break;
/* 1963 */           g.setColor(this._unselectColor3);
/*      */ 
/* 1965 */           g.drawLine(x + w - 3, y + 1, x + w - 3, y + 1);
/* 1966 */           g.drawLine(x + w - 2, y + 2, x + w - 2, y + h - 1);
/*      */         }
/*      */         else
/*      */         {
/* 1970 */           g.setColor(this._unselectColor1);
/*      */ 
/* 1972 */           g.drawLine(x + 3, y + 1, x + 4, y + 1);
/* 1973 */           g.drawLine(x + 1, y + 2, x + 2, y + 2);
/* 1974 */           g.drawLine(x, y + 3, x, y + 3);
/*      */ 
/* 1976 */           g.drawLine(x + 5, y, x + w - 3, y);
/*      */ 
/* 1978 */           g.drawLine(x + w - 2, y + 1, x + w - 2, y + 1);
/*      */ 
/* 1980 */           g.drawLine(x + w - 1, y + 2, x + w - 1, y + h - 1);
/*      */ 
/* 1982 */           if (this._unselectColor2 != null) {
/* 1983 */             g.setColor(this._unselectColor2);
/*      */ 
/* 1985 */             g.drawLine(x + 3, y + 2, x + 4, y + 2);
/* 1986 */             g.drawLine(x + 1, y + 3, x + 2, y + 3);
/* 1987 */             g.drawLine(x, y + 4, x, y + 4);
/*      */ 
/* 1989 */             g.drawLine(x + 5, y + 1, x + w - 4, y + 1);
/*      */           }
/*      */ 
/* 1992 */           if (this._unselectColor3 == null) break;
/* 1993 */           g.setColor(this._unselectColor3);
/*      */ 
/* 1995 */           g.drawLine(x + w - 3, y + 1, x + w - 3, y + 1);
/* 1996 */           g.drawLine(x + w - 2, y + 2, x + w - 2, y + h - 1);
/*      */         }
/*      */ 
/*      */       }
/* 2002 */       else if (isSelected) {
/* 2003 */         g.setColor(this._selectColor1);
/*      */ 
/* 2005 */         g.drawLine(x + w - 4, y + 1, x + w - 5, y + 1);
/* 2006 */         g.drawLine(x + w - 2, y + 2, x + w - 3, y + 2);
/* 2007 */         g.drawLine(x + w - 1, y + 3, x + w - 1, y + 3);
/*      */ 
/* 2009 */         g.drawLine(x + w - 6, y, x + 2, y);
/*      */ 
/* 2011 */         g.drawLine(x + 1, y + 1, x + 1, y + 1);
/*      */ 
/* 2013 */         g.drawLine(x, y + 2, x, y + h - 1);
/*      */ 
/* 2016 */         for (int i = 3; i < h - 2; i++) {
/* 2017 */           g.drawLine(x + w - 3 + i, y + 1 + i, x + w - 3 + i, y + 1 + i);
/*      */         }
/* 2019 */         g.drawLine(x + w - 4 + h, y + h - 1, x + w - 5 + h, y + h - 1);
/*      */ 
/* 2021 */         g.setColor(this._selectColor2);
/*      */ 
/* 2023 */         g.drawLine(x + w - 4, y + 2, x + w - 5, y + 2);
/* 2024 */         g.drawLine(x + w - 2, y + 3, x + w - 3, y + 3);
/* 2025 */         g.drawLine(x + w - 1, y + 4, x + w - 1, y + 4);
/*      */ 
/* 2027 */         g.drawLine(x + w - 6, y + 1, x + 2, y + 1);
/*      */ 
/* 2029 */         g.drawLine(x + 1, y + 2, x + 1, y + h - 1);
/*      */ 
/* 2032 */         for (int i = 3; i < h - 2; i++) {
/* 2033 */           g.drawLine(x + w - 3 + i, y + 2 + i, x + w - 3 + i, y + 2 + i);
/*      */         }
/*      */ 
/*      */       }
/* 2037 */       else if (tabIndex == 0) {
/* 2038 */         g.setColor(this._unselectColor1);
/*      */ 
/* 2040 */         g.drawLine(x + w - 4, y + 1, x + w - 5, y + 1);
/* 2041 */         g.drawLine(x + w - 2, y + 2, x + w - 3, y + 2);
/* 2042 */         g.drawLine(x + w - 1, y + 3, x + w - 1, y + 3);
/*      */ 
/* 2044 */         g.drawLine(x + w - 6, y, x + 2, y);
/*      */ 
/* 2046 */         g.drawLine(x + 1, y + 1, x + 1, y + 1);
/*      */ 
/* 2048 */         g.drawLine(x, y + 2, x, y + h - 1);
/*      */ 
/* 2051 */         for (int i = 3; i < h - 2; i++) {
/* 2052 */           g.drawLine(x + w - 3 + i, y + 1 + i, x + w - 3 + i, y + 1 + i);
/*      */         }
/* 2054 */         g.drawLine(x + w - 4 + h, y + h - 1, x + w - 5 + h, y + h - 1);
/*      */ 
/* 2056 */         if (this._unselectColor2 != null) {
/* 2057 */           g.setColor(this._unselectColor2);
/*      */ 
/* 2059 */           g.drawLine(x + w - 4, y + 2, x + w - 5, y + 2);
/* 2060 */           g.drawLine(x + w - 2, y + 3, x + w - 3, y + 3);
/* 2061 */           g.drawLine(x + w - 1, y + 4, x + w - 1, y + 4);
/*      */ 
/* 2064 */           for (int i = 3; i < h - 2; i++) {
/* 2065 */             g.drawLine(x + w - 3 + i, y + 2 + i, x + w - 3 + i, y + 2 + i);
/*      */           }
/*      */ 
/* 2068 */           g.drawLine(x + w - 6, y + 1, x + 3, y + 1);
/*      */         }
/*      */ 
/* 2071 */         if (this._unselectColor3 == null) break;
/* 2072 */         g.setColor(this._unselectColor3);
/*      */ 
/* 2074 */         g.drawLine(x + 2, y + 1, x + 2, y + 1);
/* 2075 */         g.drawLine(x + 1, y + 2, x + 1, y + h - 1);
/*      */       }
/*      */       else
/*      */       {
/* 2079 */         g.setColor(this._unselectColor1);
/*      */ 
/* 2081 */         g.drawLine(x + w - 4, y + 1, x + w - 5, y + 1);
/* 2082 */         g.drawLine(x + w - 2, y + 2, x + w - 3, y + 2);
/* 2083 */         g.drawLine(x + w - 1, y + 3, x + w - 1, y + 3);
/*      */ 
/* 2085 */         g.drawLine(x + w - 6, y, x + 2, y);
/*      */ 
/* 2087 */         g.drawLine(x + 1, y + 1, x + 1, y + 1);
/*      */ 
/* 2089 */         g.drawLine(x, y + 2, x, y + h - 1);
/*      */ 
/* 2091 */         if (this._unselectColor2 != null) {
/* 2092 */           g.setColor(this._unselectColor2);
/*      */ 
/* 2094 */           g.drawLine(x + w - 4, y + 2, x + w - 5, y + 2);
/* 2095 */           g.drawLine(x + w - 2, y + 3, x + w - 3, y + 3);
/* 2096 */           g.drawLine(x + w - 1, y + 4, x + w - 1, y + 4);
/*      */ 
/* 2098 */           g.drawLine(x + w - 6, y + 1, x + 3, y + 1);
/*      */         }
/*      */ 
/* 2101 */         if (this._unselectColor3 == null) break;
/* 2102 */         g.setColor(this._unselectColor3);
/*      */ 
/* 2104 */         g.drawLine(x + 2, y + 1, x + 2, y + 1);
/* 2105 */         g.drawLine(x + 1, y + 2, x + 1, y + h - 1);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintExcelTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */   {
/* 2117 */     boolean leftToRight = this._tabPane.getComponentOrientation().isLeftToRight();
/* 2118 */     switch (tabPlacement) {
/*      */     case 2:
/* 2120 */       if (isSelected) {
/* 2121 */         g.setColor(this._selectColor1);
/* 2122 */         g.drawLine(x, y + 5, x, y + h - 5);
/* 2123 */         int i = 0; for (int j = 0; i < w / 2 + 1; j += 2) {
/* 2124 */           g.drawLine(x + 1 + j, y + 4 - i, x + 2 + j, y + 4 - i);
/*      */ 
/* 2123 */           i++;
/*      */         }
/*      */ 
/* 2126 */         int i = 0; for (int j = 0; i < w / 2 + 1; j += 2) {
/* 2127 */           g.drawLine(x + j, y + h - 4 + i, x + 1 + j, y + h - 4 + i);
/*      */ 
/* 2126 */           i++;
/*      */         }
/*      */ 
/* 2130 */         if (this._selectColor2 != null) {
/* 2131 */           g.setColor(this._selectColor2);
/* 2132 */           g.drawLine(x + 1, y + 6, x + 1, y + h - 6);
/* 2133 */           int i = 0; for (int j = 0; i < w / 2 + 1; j += 2) {
/* 2134 */             g.drawLine(x + 1 + j, y + 5 - i, x + 2 + j, y + 5 - i);
/*      */ 
/* 2133 */             i++;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2138 */         if (this._selectColor3 == null) break;
/* 2139 */         g.setColor(this._selectColor3);
/* 2140 */         g.drawLine(x + 1, y + h - 5, x + 1, y + h - 5);
/* 2141 */         int i = 0; for (int j = 0; i < w / 2 + 1; j += 2) {
/* 2142 */           g.drawLine(x + 2 + j, y + h - 4 + i, x + 3 + j, y + h - 4 + i);
/*      */ 
/* 2141 */           i++;
/*      */         }
/*      */ 
/*      */       }
/* 2147 */       else if (tabIndex == 0) {
/* 2148 */         g.setColor(this._unselectColor1);
/* 2149 */         g.drawLine(x, y + 5, x, y + h - 5);
/* 2150 */         int i = 0; for (int j = 0; i < w / 2 + 1; j += 2) {
/* 2151 */           g.drawLine(x + 1 + j, y + 4 - i, x + 2 + j, y + 4 - i);
/*      */ 
/* 2150 */           i++;
/*      */         }
/*      */ 
/* 2154 */         int i = 0; for (int j = 0; i < w / 2 + 1; j += 2) {
/* 2155 */           g.drawLine(x + j, y + h - 4 + i, x + 1 + j, y + h - 4 + i);
/*      */ 
/* 2154 */           i++;
/*      */         }
/*      */ 
/* 2158 */         if (this._unselectColor2 != null) {
/* 2159 */           g.setColor(this._unselectColor2);
/* 2160 */           g.drawLine(x + 1, y + 6, x + 1, y + h - 6);
/* 2161 */           int i = 0; for (int j = 0; i < w / 2; j += 2) {
/* 2162 */             g.drawLine(x + 1 + j, y + 5 - i, x + 2 + j, y + 5 - i);
/*      */ 
/* 2161 */             i++;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2166 */         if (this._unselectColor3 == null) break;
/* 2167 */         g.setColor(this._unselectColor3);
/* 2168 */         g.drawLine(x + 1, y + h - 5, x + 1, y + h - 5);
/* 2169 */         int i = 0; for (int j = 0; i < w / 2 + 1; j += 2) {
/* 2170 */           g.drawLine(x + 2 + j, y + h - 4 + i, x + 3 + j, y + h - 4 + i);
/*      */ 
/* 2169 */           i++;
/*      */         }
/*      */ 
/*      */       }
/* 2174 */       else if (tabIndex == this._tabPane.getSelectedIndex() - 1) {
/* 2175 */         g.setColor(this._unselectColor1);
/* 2176 */         g.drawLine(x, y + 5, x, y + h - 5);
/* 2177 */         int i = 0; for (int j = 0; i < 4; j += 2) {
/* 2178 */           g.drawLine(x + 1 + j, y + 4 - i, x + 2 + j, y + 4 - i);
/*      */ 
/* 2177 */           i++;
/*      */         }
/*      */ 
/* 2181 */         int i = 0; for (int j = 0; i < 5; j += 2) {
/* 2182 */           g.drawLine(x + j, y + h - 4 + i, x + 1 + j, y + h - 4 + i);
/*      */ 
/* 2181 */           i++;
/*      */         }
/*      */ 
/* 2185 */         if (this._unselectColor2 != null) {
/* 2186 */           g.setColor(this._unselectColor2);
/* 2187 */           g.drawLine(x + 1, y + 6, x + 1, y + h - 6);
/* 2188 */           int i = 0; for (int j = 0; i < 4; j += 2) {
/* 2189 */             g.drawLine(x + 1 + j, y + 5 - i, x + 2 + j, y + 5 - i);
/*      */ 
/* 2188 */             i++;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2193 */         if (this._unselectColor3 == null) break;
/* 2194 */         g.setColor(this._unselectColor3);
/* 2195 */         g.drawLine(x + 1, y + h - 5, x + 1, y + h - 5);
/* 2196 */         int i = 0; for (int j = 0; i < 5; j += 2) {
/* 2197 */           g.drawLine(x + 2 + j, y + h - 4 + i, x + 3 + j, y + h - 4 + i);
/*      */ 
/* 2196 */           i++;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 2201 */         if (tabIndex == this._tabPane.getSelectedIndex() - 1) break;
/* 2202 */         g.setColor(this._unselectColor1);
/* 2203 */         g.drawLine(x, y + 5, x, y + h - 5);
/* 2204 */         int i = 0; for (int j = 0; i < 4; j += 2) {
/* 2205 */           g.drawLine(x + 1 + j, y + 4 - i, x + 2 + j, y + 4 - i);
/*      */ 
/* 2204 */           i++;
/*      */         }
/*      */ 
/* 2207 */         int i = 0; for (int j = 0; i < w / 2 + 1; j += 2) {
/* 2208 */           g.drawLine(x + j, y + h - 4 + i, x + 1 + j, y + h - 4 + i);
/*      */ 
/* 2207 */           i++;
/*      */         }
/*      */ 
/* 2211 */         if (this._unselectColor2 != null) {
/* 2212 */           g.setColor(this._unselectColor2);
/* 2213 */           g.drawLine(x + 1, y + 6, x + 1, y + h - 6);
/* 2214 */           int i = 0; for (int j = 0; i < 4; j += 2) {
/* 2215 */             g.drawLine(x + 1 + j, y + 5 - i, x + 2 + j, y + 5 - i);
/*      */ 
/* 2214 */             i++;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2219 */         if (this._unselectColor3 == null) break;
/* 2220 */         g.setColor(this._unselectColor3);
/* 2221 */         g.drawLine(x + 1, y + h - 5, x + 1, y + h - 5);
/* 2222 */         int i = 0; for (int j = 0; i < w / 2 + 1; j += 2) {
/* 2223 */           g.drawLine(x + 2 + j, y + h - 4 + i, x + 3 + j, y + h - 4 + i);
/*      */ 
/* 2222 */           i++; } 
/* 2222 */       }break;
/*      */     case 4:
/* 2230 */       if (isSelected) {
/* 2231 */         g.setColor(this._selectColor1);
/* 2232 */         g.drawLine(x + w - 1, y + 5, x + w - 1, y + h - 5);
/* 2233 */         int i = 0; for (int j = 0; i < w / 2 + 1; j += 2) {
/* 2234 */           g.drawLine(x + w - 2 - j, y + 4 - i, x + w - 3 - j, y + 4 - i);
/*      */ 
/* 2233 */           i++;
/*      */         }
/*      */ 
/* 2236 */         int i = 0; for (int j = 0; i < w / 2 + 1; j += 2) {
/* 2237 */           g.drawLine(x + w - 1 - j, y + h - 4 + i, x + w - 2 - j, y + h - 4 + i);
/*      */ 
/* 2236 */           i++;
/*      */         }
/*      */ 
/* 2240 */         if (this._selectColor2 != null) {
/* 2241 */           g.setColor(this._selectColor2);
/* 2242 */           g.drawLine(x + w - 2, y + 6, x + w - 2, y + h - 6);
/* 2243 */           int i = 0; for (int j = 0; i < w / 2 + 1; j += 2) {
/* 2244 */             g.drawLine(x + w - 2 - j, y + 5 - i, x + w - 3 - j, y + 5 - i);
/*      */ 
/* 2243 */             i++;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2248 */         if (this._selectColor3 == null) break;
/* 2249 */         g.setColor(this._selectColor3);
/* 2250 */         g.drawLine(x + w - 2, y + h - 5, x + w - 2, y + h - 5);
/* 2251 */         int i = 0; for (int j = 0; i < w / 2 + 1; j += 2) {
/* 2252 */           g.drawLine(x + w - 3 - j, y + h - 4 + i, x + w - 4 - j, y + h - 4 + i);
/*      */ 
/* 2251 */           i++;
/*      */         }
/*      */ 
/*      */       }
/* 2257 */       else if (tabIndex == 0) {
/* 2258 */         g.setColor(this._unselectColor1);
/* 2259 */         g.drawLine(x + w - 1, y + 5, x + w - 1, y + h - 5);
/* 2260 */         int i = 0; for (int j = 0; i < w / 2 + 1; j += 2) {
/* 2261 */           g.drawLine(x + w - 2 - j, y + 4 - i, x + w - 3 - j, y + 4 - i);
/*      */ 
/* 2260 */           i++;
/*      */         }
/*      */ 
/* 2264 */         int i = 0; for (int j = 0; i < w / 2 + 1; j += 2) {
/* 2265 */           g.drawLine(x + w - 1 - j, y + h - 4 + i, x + w - 2 - j, y + h - 4 + i);
/*      */ 
/* 2264 */           i++;
/*      */         }
/*      */ 
/* 2268 */         if (this._unselectColor2 != null) {
/* 2269 */           g.setColor(this._unselectColor2);
/* 2270 */           g.drawLine(x + w - 2, y + 6, x + w - 2, y + h - 6);
/*      */ 
/* 2272 */           int i = 0; for (int j = 0; i < w / 2 + 1; j += 2) {
/* 2273 */             g.drawLine(x + w - 2 - j, y + 5 - i, x + w - 3 - j, y + 5 - i);
/*      */ 
/* 2272 */             i++;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2277 */         if (this._unselectColor3 == null) break;
/* 2278 */         g.setColor(this._unselectColor3);
/*      */ 
/* 2280 */         g.drawLine(x + w - 2, y + h - 5, x + w - 2, y + h - 5);
/*      */ 
/* 2283 */         int i = 0; for (int j = 0; i < w / 2 + 1; j += 2) {
/* 2284 */           g.drawLine(x + w - 3 - j, y + h - 4 + i, x + w - 4 - j, y + h - 4 + i);
/*      */ 
/* 2283 */           i++;
/*      */         }
/*      */ 
/*      */       }
/* 2288 */       else if (tabIndex == this._tabPane.getSelectedIndex() - 1) {
/* 2289 */         g.setColor(this._unselectColor1);
/* 2290 */         g.drawLine(x + w - 1, y + 5, x + w - 1, y + h - 5);
/* 2291 */         int i = 0; for (int j = 0; i < 4; j += 2) {
/* 2292 */           g.drawLine(x + w - 2 - j, y + 4 - i, x + w - 3 - j, y + 4 - i);
/*      */ 
/* 2291 */           i++;
/*      */         }
/*      */ 
/* 2295 */         int i = 0; for (int j = 0; i < 5; j += 2) {
/* 2296 */           g.drawLine(x + w - 1 - j, y + h - 4 + i, x + w - 2 - j, y + h - 4 + i);
/*      */ 
/* 2295 */           i++;
/*      */         }
/*      */ 
/* 2299 */         if (this._unselectColor2 != null) {
/* 2300 */           g.setColor(this._unselectColor2);
/* 2301 */           g.drawLine(x + w - 2, y + 6, x + w - 2, y + h - 6);
/* 2302 */           int i = 0; for (int j = 0; i < 4; j += 2) {
/* 2303 */             g.drawLine(x + w - 2 - j, y + 5 - i, x + w - 3 - j, y + 5 - i);
/*      */ 
/* 2302 */             i++;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2308 */         if (this._unselectColor3 == null) break;
/* 2309 */         g.setColor(this._unselectColor3);
/*      */ 
/* 2311 */         g.drawLine(x + w - 2, y + h - 5, x + w - 2, y + h - 5);
/*      */ 
/* 2313 */         int i = 0; for (int j = 0; i < 5; j += 2) {
/* 2314 */           g.drawLine(x + w - 3 - j, y + h - 4 + i, x + w - 4 - j, y + h - 4 + i);
/*      */ 
/* 2313 */           i++;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 2318 */         if (tabIndex == this._tabPane.getSelectedIndex() - 1) break;
/* 2319 */         g.setColor(this._unselectColor1);
/* 2320 */         g.drawLine(x + w - 1, y + 5, x + w - 1, y + h - 5);
/* 2321 */         int i = 0; for (int j = 0; i < 4; j += 2) {
/* 2322 */           g.drawLine(x + w - 2 - j, y + 4 - i, x + w - 3 - j, y + 4 - i);
/*      */ 
/* 2321 */           i++;
/*      */         }
/*      */ 
/* 2325 */         int i = 0; for (int j = 0; i < w / 2 + 1; j += 2) {
/* 2326 */           g.drawLine(x + w - 1 - j, y + h - 4 + i, x + w - 2 - j, y + h - 4 + i);
/*      */ 
/* 2325 */           i++;
/*      */         }
/*      */ 
/* 2329 */         if (this._unselectColor2 != null) {
/* 2330 */           g.setColor(this._unselectColor2);
/* 2331 */           g.drawLine(x + w - 2, y + 6, x + w - 2, y + h - 6);
/* 2332 */           int i = 0; for (int j = 0; i < 4; j += 2) {
/* 2333 */             g.drawLine(x + w - 2 - j, y + 5 - i, x + w - 3 - j, y + 5 - i);
/*      */ 
/* 2332 */             i++;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2337 */         if (this._unselectColor3 == null) break;
/* 2338 */         g.setColor(this._unselectColor3);
/* 2339 */         g.drawLine(x + w - 2, y + h - 5, x + w - 2, y + h - 5);
/* 2340 */         int i = 0; for (int j = 0; i < w / 2 + 1; j += 2) {
/* 2341 */           g.drawLine(x + w - 3 - j, y + h - 4 + i, x + w - 4 - j, y + h - 4 + i);
/*      */ 
/* 2340 */           i++; } 
/* 2340 */       }break;
/*      */     case 3:
/* 2348 */       if (isSelected) {
/* 2349 */         g.setColor(this._selectColor1);
/* 2350 */         g.drawLine(x + 5, y + h - 1, x + w - 5, y + h - 1);
/* 2351 */         int i = 0; for (int j = 0; i < h / 2 + 1; j += 2) {
/* 2352 */           g.drawLine(x + 4 - i, y + h - 2 - j, x + 4 - i, y + h - 3 - j);
/*      */ 
/* 2351 */           i++;
/*      */         }
/*      */ 
/* 2355 */         int i = 0; for (int j = 0; i < h / 2 + 1; j += 2) {
/* 2356 */           g.drawLine(x + w - 4 - 1 + i, y + h - 1 - j, x + w - 4 - 1 + i, y + h - 2 - j);
/*      */ 
/* 2355 */           i++;
/*      */         }
/*      */ 
/* 2359 */         if (this._selectColor2 != null) {
/* 2360 */           g.setColor(this._selectColor2);
/*      */ 
/* 2362 */           g.drawLine(x + 5, y + h - 3, x + 5, y + h - 3);
/*      */ 
/* 2364 */           int i = 0; for (int j = 0; i < h / 2 + 1; j += 2) {
/* 2365 */             g.drawLine(x + 4 - i, y + h - 4 - j, x + 4 - i, y + h - 5 - j);
/*      */ 
/* 2364 */             i++;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2370 */         if (this._selectColor3 == null) break;
/* 2371 */         g.setColor(this._selectColor3);
/* 2372 */         g.drawLine(x + 5, y + h - 2, x + w - 6, y + h - 2);
/* 2373 */         int i = 0; for (int j = 0; i < h / 2 + 1; j += 2) {
/* 2374 */           g.drawLine(x + w - 5 + i, y + h - 3 - j, x + w - 5 + i, y + h - 4 - j);
/*      */ 
/* 2373 */           i++;
/*      */         }
/*      */ 
/*      */       }
/* 2379 */       else if (((leftToRight) && (tabIndex == 0)) || ((!leftToRight) && (tabIndex == this._tabPane.getTabCount() - 1))) {
/* 2380 */         g.setColor(this._unselectColor1);
/* 2381 */         g.drawLine(x + 5, y + h - 1, x + w - 5, y + h - 1);
/* 2382 */         int i = 0; for (int j = 0; i < h / 2 + 1; j += 2) {
/* 2383 */           g.drawLine(x + 4 - i, y + h - 2 - j, x + 4 - i, y + h - 3 - j);
/*      */ 
/* 2382 */           i++;
/*      */         }
/*      */ 
/* 2386 */         int i = 0; for (int j = 0; i < h / 2 + 1; j += 2) {
/* 2387 */           g.drawLine(x + w - 4 - 1 + i, y + h - 1 - j, x + w - 4 - 1 + i, y + h - 2 - j);
/*      */ 
/* 2386 */           i++;
/*      */         }
/*      */ 
/* 2390 */         if (this._unselectColor2 != null) {
/* 2391 */           g.setColor(this._unselectColor2);
/* 2392 */           int i = 0; for (int j = 0; i < h / 2 + 1; j += 2) {
/* 2393 */             g.drawLine(x + 5 - i, y + h - 2 - j, x + 5 - i, y + h - 3 - j);
/*      */ 
/* 2392 */             i++;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2397 */         if (this._unselectColor3 == null) break;
/* 2398 */         g.setColor(this._unselectColor3);
/* 2399 */         g.drawLine(x + w - 6, y + h - 2, x + w - 6, y + h - 2);
/* 2400 */         int i = 0; for (int j = 0; i < h / 2 + 1; j += 2) {
/* 2401 */           g.drawLine(x + w - 5 + i, y + h - 3 - j, x + w - 5 + i, y + h - 4 - j);
/*      */ 
/* 2400 */           i++;
/*      */         }
/*      */ 
/*      */       }
/* 2405 */       else if (tabIndex == this._tabPane.getSelectedIndex() + (leftToRight ? -1 : 1)) {
/* 2406 */         g.setColor(this._unselectColor1);
/* 2407 */         g.drawLine(x + 5, y + h - 1, x + w - 6, y + h - 1);
/* 2408 */         int i = 0; for (int j = 0; i < 5; j += 2) {
/* 2409 */           g.drawLine(x + 4 - i, y + h - 2 - j, x + 4 - i, y + h - 3 - j);
/*      */ 
/* 2408 */           i++;
/*      */         }
/*      */ 
/* 2411 */         int i = 0; for (int j = 0; i < 5; j += 2) {
/* 2412 */           g.drawLine(x + w - 5 + i, y + h - 1 - j, x + w - 5 + i, y + h - 2 - j);
/*      */ 
/* 2411 */           i++;
/*      */         }
/*      */ 
/* 2415 */         if (this._unselectColor2 != null) {
/* 2416 */           g.setColor(this._unselectColor2);
/* 2417 */           int i = 0; for (int j = 0; i < 5; j += 2) {
/* 2418 */             g.drawLine(x + 5 - i, y + h - 2 - j, x + 5 - i, y + h - 3 - j);
/*      */ 
/* 2417 */             i++;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2422 */         if (this._unselectColor3 == null) break;
/* 2423 */         g.setColor(this._unselectColor3);
/* 2424 */         g.drawLine(x + w - 6, y + h - 2, x + w - 6, y + h - 2);
/* 2425 */         int i = 0; for (int j = 0; i < 5; j += 2) {
/* 2426 */           g.drawLine(x + w - 5 + i, y + h - 3 - j, x + w - 5 + i, y + h - 4 - j);
/*      */ 
/* 2425 */           i++;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 2430 */         if (tabIndex == this._tabPane.getSelectedIndex() + (leftToRight ? -1 : 1)) break;
/* 2431 */         g.setColor(this._unselectColor1);
/* 2432 */         g.drawLine(x + 5, y + h - 1, x + w - 6, y + h - 1);
/* 2433 */         int i = 0; for (int j = 0; i < 5; j += 2) {
/* 2434 */           g.drawLine(x + 4 - i, y + h - 2 - j, x + 4 - i, y + h - 3 - j);
/*      */ 
/* 2433 */           i++;
/*      */         }
/*      */ 
/* 2437 */         int i = 0; for (int j = 0; i < h / 2 + 1; j += 2) {
/* 2438 */           g.drawLine(x + w - 5 + i, y + h - 1 - j, x + w - 5 + i, y + h - 2 - j);
/*      */ 
/* 2437 */           i++;
/*      */         }
/*      */ 
/* 2441 */         if (this._unselectColor2 != null) {
/* 2442 */           g.setColor(this._unselectColor2);
/* 2443 */           int i = 0; for (int j = 0; i < 5; j += 2) {
/* 2444 */             g.drawLine(x + 5 - i, y + h - 2 - j, x + 5 - i, y + h - 3 - j);
/*      */ 
/* 2443 */             i++;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2448 */         if (this._unselectColor3 == null) break;
/* 2449 */         g.setColor(this._unselectColor3);
/* 2450 */         g.drawLine(x + w - 6, y + h - 2, x + w - 6, y + h - 2);
/* 2451 */         int i = 0; for (int j = 0; i < h / 2 + 1; j += 2) {
/* 2452 */           g.drawLine(x + w - 5 + i, y + h - 3 - j, x + w - 5 + i, y + h - 4 - j);
/*      */ 
/* 2451 */           i++; } 
/* 2451 */       }break;
/*      */     case 1:
/*      */     default:
/* 2460 */       if (isSelected) {
/* 2461 */         g.setColor(this._selectColor1);
/* 2462 */         g.drawLine(x + 5, y, x + w - 5, y);
/* 2463 */         int i = 0; for (int j = 0; i < h / 2 + 1; j += 2) {
/* 2464 */           g.drawLine(x + 4 - i, y + 1 + j, x + 4 - i, y + 2 + j);
/*      */ 
/* 2463 */           i++;
/*      */         }
/*      */ 
/* 2466 */         int i = 0; for (int j = 0; i < h / 2 + 1; j += 2) {
/* 2467 */           g.drawLine(x + w - 4 - 1 + i, y + j, x + w - 4 - 1 + i, y + 1 + j);
/*      */ 
/* 2466 */           i++;
/*      */         }
/*      */ 
/* 2470 */         if (this._selectColor2 != null) {
/* 2471 */           g.setColor(this._selectColor2);
/* 2472 */           g.drawLine(x + 6, y + 1, x + w - 7, y + 1);
/* 2473 */           int i = 0; for (int j = 0; i < h / 2 + 1; j += 2) {
/* 2474 */             g.drawLine(x + 5 - i, y + 1 + j, x + 5 - i, y + 2 + j);
/*      */ 
/* 2473 */             i++;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2478 */         if (this._selectColor3 == null) break;
/* 2479 */         g.setColor(this._selectColor3);
/* 2480 */         g.drawLine(x + w - 6, y + 1, x + w - 6, y + 1);
/* 2481 */         int i = 0; for (int j = 0; i < h / 2 + 1; j += 2) {
/* 2482 */           g.drawLine(x + w - 5 + i, y + 2 + j, x + w - 5 + i, y + 3 + j);
/*      */ 
/* 2481 */           i++;
/*      */         }
/*      */ 
/*      */       }
/* 2487 */       else if (((leftToRight) && (tabIndex == 0)) || ((!leftToRight) && (tabIndex == this._tabPane.getTabCount() - 1))) {
/* 2488 */         g.setColor(this._unselectColor1);
/* 2489 */         g.drawLine(x + 5, y, x + w - 5, y);
/* 2490 */         int i = 0; for (int j = 0; i < h / 2 + 1; j += 2) {
/* 2491 */           g.drawLine(x + 4 - i, y + 1 + j, x + 4 - i, y + 2 + j);
/*      */ 
/* 2490 */           i++;
/*      */         }
/*      */ 
/* 2493 */         int i = 0; for (int j = 0; i < h / 2 + 1; j += 2) {
/* 2494 */           g.drawLine(x + w - 4 - 1 + i, y + j, x + w - 4 - 1 + i, y + 1 + j);
/*      */ 
/* 2493 */           i++;
/*      */         }
/*      */ 
/* 2496 */         if (this._unselectColor2 != null) {
/* 2497 */           g.setColor(this._unselectColor2);
/* 2498 */           g.drawLine(x + 6, y + 1, x + w - 7, y + 1);
/* 2499 */           int i = 0; for (int j = 0; i < h / 2 + 1; j += 2) {
/* 2500 */             g.drawLine(x + 5 - i, y + 1 + j, x + 5 - i, y + 2 + j);
/*      */ 
/* 2499 */             i++;
/*      */           }
/*      */         }
/*      */ 
/* 2503 */         if (this._unselectColor3 == null) break;
/* 2504 */         g.setColor(this._unselectColor3);
/* 2505 */         g.drawLine(x + w - 6, y + 1, x + w - 6, y + 1);
/* 2506 */         int i = 0; for (int j = 0; i < h / 2 + 1; j += 2) {
/* 2507 */           g.drawLine(x + w - 5 + i, y + 2 + j, x + w - 5 + i, y + 3 + j);
/*      */ 
/* 2506 */           i++;
/*      */         }
/*      */ 
/*      */       }
/* 2511 */       else if (tabIndex == this._tabPane.getSelectedIndex() + (leftToRight ? -1 : 1)) {
/* 2512 */         g.setColor(this._unselectColor1);
/* 2513 */         g.drawLine(x + 5, y, x + w - 5, y);
/* 2514 */         int i = 0; for (int j = 0; i < 5; j += 2) {
/* 2515 */           g.drawLine(x + 4 - i, y + 1 + j, x + 4 - i, y + 2 + j);
/*      */ 
/* 2514 */           i++;
/*      */         }
/*      */ 
/* 2517 */         int i = 0; for (int j = 0; i < 5; j += 2) {
/* 2518 */           g.drawLine(x + w - 4 - 1 + i, y + j, x + w - 4 - 1 + i, y + 1 + j);
/*      */ 
/* 2517 */           i++;
/*      */         }
/*      */ 
/* 2521 */         if (this._unselectColor2 != null) {
/* 2522 */           g.setColor(this._unselectColor2);
/* 2523 */           g.drawLine(x + 6, y + 1, x + w - 7, y + 1);
/* 2524 */           int i = 0; for (int j = 0; i < 5; j += 2) {
/* 2525 */             g.drawLine(x + 5 - i, y + 1 + j, x + 5 - i, y + 2 + j);
/*      */ 
/* 2524 */             i++;
/*      */           }
/*      */         }
/*      */ 
/* 2528 */         if (this._unselectColor3 == null) break;
/* 2529 */         g.setColor(this._unselectColor3);
/* 2530 */         g.drawLine(x + w - 6, y + 1, x + w - 6, y + 1);
/* 2531 */         int i = 0; for (int j = 0; i < 5; j += 2) {
/* 2532 */           g.drawLine(x + w - 5 + i, y + 2 + j, x + w - 5 + i, y + 3 + j);
/*      */ 
/* 2531 */           i++;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 2536 */         if (tabIndex == this._tabPane.getSelectedIndex() + (leftToRight ? -1 : 1)) break;
/* 2537 */         g.setColor(this._unselectColor1);
/* 2538 */         g.drawLine(x + 5, y, x + w - 5, y);
/* 2539 */         int i = 0; for (int j = 0; i < 5; j += 2) {
/* 2540 */           g.drawLine(x + 4 - i, y + 1 + j, x + 4 - i, y + 2 + j);
/*      */ 
/* 2539 */           i++;
/*      */         }
/*      */ 
/* 2542 */         int i = 0; for (int j = 0; i < h / 2 + 1; j += 2) {
/* 2543 */           g.drawLine(x + w - 4 - 1 + i, y + j, x + w - 4 - 1 + i, y + 1 + j);
/*      */ 
/* 2542 */           i++;
/*      */         }
/*      */ 
/* 2545 */         if (this._unselectColor2 != null) {
/* 2546 */           g.setColor(this._unselectColor2);
/* 2547 */           g.drawLine(x + 6, y + 1, x + w - 7, y + 1);
/* 2548 */           int i = 0; for (int j = 0; i < 5; j += 2) {
/* 2549 */             g.drawLine(x + 5 - i, y + 1 + j, x + 5 - i, y + 2 + j);
/*      */ 
/* 2548 */             i++;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2553 */         if (this._unselectColor3 == null) break;
/* 2554 */         g.setColor(this._unselectColor3);
/* 2555 */         g.drawLine(x + w - 6, y + 1, x + w - 6, y + 1);
/* 2556 */         int i = 0; for (int j = 0; i < h / 2 + 1; j += 2) {
/* 2557 */           g.drawLine(x + w - 5 + i, y + 2 + j, x + w - 5 + i, y + 3 + j);
/*      */ 
/* 2556 */           i++;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintWindowsTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */   {
/* 2568 */     int colorTheme = getColorTheme();
/* 2569 */     switch (tabPlacement) {
/*      */     case 2:
/* 2571 */       if ((colorTheme == 2) || (colorTheme == 1)) {
/* 2572 */         if (isSelected) {
/* 2573 */           g.setColor(this._selectColor1);
/* 2574 */           g.drawLine(x - 2, y + 1, x - 2, y + h - 1);
/* 2575 */           g.drawLine(x - 1, y, x - 1, y);
/* 2576 */           g.drawLine(x, y - 1, x + w - 1, y - 1);
/*      */ 
/* 2578 */           g.setColor(this._selectColor2);
/* 2579 */           g.drawLine(x - 1, y + h, x - 1, y + h);
/* 2580 */           g.drawLine(x, y + h + 1, x, y + h + 1);
/* 2581 */           g.drawLine(x + 1, y + h, x + w - 1, y + h);
/*      */ 
/* 2583 */           g.setColor(this._selectColor3);
/* 2584 */           g.drawLine(x, y + h, x, y + h);
/* 2585 */           g.drawLine(x + 1, y + h + 1, x + w - 1, y + h + 1);
/*      */         }
/* 2588 */         else if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 2589 */           g.setColor(this._unselectColor1);
/* 2590 */           g.drawLine(x, y + 2, x, y + h - 3);
/* 2591 */           g.drawLine(x + 1, y + 1, x + 1, y + 1);
/* 2592 */           g.drawLine(x + 2, y, x + w - 1, y);
/*      */ 
/* 2594 */           g.setColor(this._unselectColor2);
/* 2595 */           g.drawLine(x + 1, y + h - 2, x + 1, y + h - 2);
/* 2596 */           g.drawLine(x + 2, y + h - 1, x + 2, y + h - 1);
/* 2597 */           g.drawLine(x + 3, y + h - 2, x + w - 1, y + h - 2);
/*      */ 
/* 2599 */           g.setColor(this._unselectColor3);
/* 2600 */           g.drawLine(x + 2, y + h - 2, x + 2, y + h - 2);
/* 2601 */           g.drawLine(x + 3, y + h - 1, x + w - 1, y + h - 1);
/*      */         } else {
/* 2603 */           if (tabIndex >= this._tabPane.getSelectedIndex()) break;
/* 2604 */           g.setColor(this._unselectColor1);
/* 2605 */           g.drawLine(x, y + 3, x, y + h - 2);
/* 2606 */           g.drawLine(x + 1, y + 2, x + 1, y + 2);
/* 2607 */           g.drawLine(x + 2, y + 1, x + w - 1, y + 1);
/*      */ 
/* 2609 */           g.setColor(this._unselectColor2);
/* 2610 */           g.drawLine(x + 1, y + h - 1, x + 1, y + h - 1);
/* 2611 */           g.drawLine(x + 2, y + h, x + 2, y + h);
/* 2612 */           g.drawLine(x + 3, y + h - 1, x + w - 1, y + h - 1);
/*      */ 
/* 2614 */           g.setColor(this._unselectColor3);
/* 2615 */           g.drawLine(x + 2, y + h - 1, x + 2, y + h - 1);
/* 2616 */           g.drawLine(x + 3, y + h, x + w - 1, y + h);
/*      */         }
/*      */ 
/*      */       }
/* 2621 */       else if (isSelected) {
/* 2622 */         g.setColor(this._selectColor1);
/* 2623 */         g.drawLine(x - 2, y + 1, x - 2, y + h - 1);
/* 2624 */         g.drawLine(x - 1, y, x - 1, y);
/* 2625 */         g.drawLine(x, y - 1, x, y - 1);
/* 2626 */         g.drawLine(x - 1, y + h, x - 1, y + h);
/* 2627 */         g.drawLine(x, y + h + 1, x, y + h + 1);
/*      */ 
/* 2629 */         g.setColor(this._selectColor2);
/* 2630 */         g.drawLine(x - 1, y + 1, x - 1, y + h - 1);
/* 2631 */         g.drawLine(x, y, x, y + h);
/*      */ 
/* 2633 */         g.setColor(this._selectColor3);
/* 2634 */         g.drawLine(x + 1, y - 2, x + w - 1, y - 2);
/* 2635 */         g.drawLine(x + 1, y + h + 2, x + w - 1, y + h + 2);
/*      */       }
/* 2638 */       else if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 2639 */         g.setColor(this._unselectColor1);
/* 2640 */         g.drawLine(x, y + 2, x, y + h - 4);
/* 2641 */         g.drawLine(x + 1, y + 1, x + 1, y + 1);
/* 2642 */         g.drawLine(x + 2, y, x + w - 1, y);
/* 2643 */         g.drawLine(x + 1, y + h - 3, x + 1, y + h - 3);
/* 2644 */         g.drawLine(x + 2, y + h - 2, x + w - 1, y + h - 2);
/*      */       } else {
/* 2646 */         if (tabIndex >= this._tabPane.getSelectedIndex()) break;
/* 2647 */         g.setColor(this._unselectColor1);
/* 2648 */         g.drawLine(x, y + 4, x, y + h - 2);
/* 2649 */         g.drawLine(x + 1, y + 3, x + 1, y + 3);
/* 2650 */         g.drawLine(x + 2, y + 2, x + w - 1, y + 2);
/* 2651 */         g.drawLine(x + 1, y + h - 1, x + 1, y + h - 1);
/* 2652 */         g.drawLine(x + 2, y + h, x + w - 1, y + h); } break;
/*      */     case 4:
/* 2658 */       if ((colorTheme == 2) || (colorTheme == 1)) {
/* 2659 */         if (isSelected) {
/* 2660 */           g.setColor(this._selectColor1);
/* 2661 */           g.drawLine(x + w - 1, y - 1, x, y - 1);
/*      */ 
/* 2663 */           g.setColor(this._selectColor2);
/* 2664 */           g.drawLine(x + w, y + 1, x + w, y + h - 1);
/* 2665 */           g.drawLine(x + w - 1, y + h, x, y + h);
/*      */ 
/* 2667 */           g.setColor(this._selectColor3);
/* 2668 */           g.drawLine(x + w, y, x + w, y);
/* 2669 */           g.drawLine(x + w + 1, y + 1, x + w + 1, y + h - 1);
/* 2670 */           g.drawLine(x + w, y + h, x + w, y + h);
/* 2671 */           g.drawLine(x + w - 1, y + h + 1, x, y + h + 1);
/*      */         }
/* 2674 */         else if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 2675 */           g.setColor(this._unselectColor1);
/* 2676 */           g.drawLine(x + w - 3, y, x, y);
/*      */ 
/* 2678 */           g.setColor(this._unselectColor2);
/* 2679 */           g.drawLine(x + w - 2, y + 2, x + w - 2, y + h - 3);
/* 2680 */           g.drawLine(x + w - 3, y + h - 2, x, y + h - 2);
/*      */ 
/* 2682 */           g.setColor(this._unselectColor3);
/* 2683 */           g.drawLine(x + w - 2, y + 1, x + w - 2, y + 1);
/* 2684 */           g.drawLine(x + w - 1, y + 2, x + w - 1, y + h - 3);
/* 2685 */           g.drawLine(x + w - 2, y + h - 2, x + w - 2, y + h - 2);
/* 2686 */           g.drawLine(x + w - 3, y + h - 1, x, y + h - 1);
/*      */         } else {
/* 2688 */           if (tabIndex >= this._tabPane.getSelectedIndex()) break;
/* 2689 */           g.setColor(this._unselectColor1);
/* 2690 */           g.drawLine(x + w - 3, y + 1, x, y + 1);
/*      */ 
/* 2692 */           g.setColor(this._unselectColor2);
/* 2693 */           g.drawLine(x + w - 2, y + 3, x + w - 2, y + h - 2);
/* 2694 */           g.drawLine(x + w - 3, y + h - 1, x, y + h - 1);
/*      */ 
/* 2696 */           g.setColor(this._unselectColor3);
/* 2697 */           g.drawLine(x + w - 2, y + 2, x + w - 2, y + 2);
/* 2698 */           g.drawLine(x + w - 1, y + 3, x + w - 1, y + h - 2);
/* 2699 */           g.drawLine(x + w - 2, y + h - 1, x + w - 2, y + h - 1);
/* 2700 */           g.drawLine(x + w - 3, y + h, x, y + h);
/*      */         }
/*      */ 
/*      */       }
/* 2705 */       else if (isSelected) {
/* 2706 */         g.setColor(this._selectColor1);
/* 2707 */         g.drawLine(x + w + 1, y + 1, x + w + 1, y + h - 1);
/* 2708 */         g.drawLine(x + w, y, x + w, y);
/* 2709 */         g.drawLine(x + w - 1, y - 1, x + w - 1, y - 1);
/* 2710 */         g.drawLine(x + w, y + h, x + w, y + h);
/* 2711 */         g.drawLine(x + w - 1, y + h + 1, x + w - 1, y + h + 1);
/*      */ 
/* 2713 */         g.setColor(this._selectColor2);
/* 2714 */         g.drawLine(x + w, y + 1, x + w, y + h - 1);
/* 2715 */         g.drawLine(x + w - 1, y, x + w - 1, y + h);
/*      */ 
/* 2717 */         g.setColor(this._selectColor3);
/* 2718 */         g.drawLine(x + w - 2, y - 2, x, y - 2);
/* 2719 */         g.drawLine(x + w - 2, y + h + 2, x, y + h + 2);
/*      */       }
/* 2722 */       else if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 2723 */         g.setColor(this._unselectColor1);
/* 2724 */         g.drawLine(x + w - 1, y + 2, x + w - 1, y + h - 4);
/* 2725 */         g.drawLine(x + w - 2, y + 1, x + w - 2, y + 1);
/* 2726 */         g.drawLine(x + w - 2, y + h - 3, x + w - 2, y + h - 3);
/* 2727 */         g.drawLine(x + w - 3, y, x, y);
/* 2728 */         g.drawLine(x + w - 3, y + h - 2, x, y + h - 2);
/*      */       } else {
/* 2730 */         if (tabIndex >= this._tabPane.getSelectedIndex()) break;
/* 2731 */         g.setColor(this._unselectColor1);
/* 2732 */         g.drawLine(x + w - 1, y + 4, x + w - 1, y + h - 2);
/* 2733 */         g.drawLine(x + w - 2, y + 3, x + w - 2, y + 3);
/* 2734 */         g.drawLine(x + w - 3, y + 2, x, y + 2);
/* 2735 */         g.drawLine(x + w - 2, y + h - 1, x + w - 2, y + h - 1);
/* 2736 */         g.drawLine(x + w - 3, y + h, x, y + h); } break;
/*      */     case 3:
/* 2742 */       if ((colorTheme == 2) || (colorTheme == 1)) {
/* 2743 */         if (isSelected) {
/* 2744 */           g.setColor(this._selectColor1);
/* 2745 */           g.drawLine(x, y + h, x, y + h);
/* 2746 */           g.drawLine(x - 1, y + h - 1, x - 1, y);
/*      */ 
/* 2748 */           g.setColor(this._selectColor2);
/* 2749 */           g.drawLine(x + 1, y + h, x + w - 2, y + h);
/* 2750 */           g.drawLine(x + w - 1, y + h - 1, x + w - 1, y - 1);
/*      */ 
/* 2752 */           g.setColor(this._selectColor3);
/* 2753 */           g.drawLine(x + 1, y + h + 1, x + w - 2, y + h + 1);
/* 2754 */           g.drawLine(x + w - 1, y + h, x + w - 1, y + h);
/* 2755 */           g.drawLine(x + w, y + h - 1, x + w, y - 1);
/*      */         }
/* 2758 */         else if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 2759 */           g.setColor(this._unselectColor1);
/* 2760 */           g.drawLine(x, y + h - 2, x, y + h - 2);
/* 2761 */           g.drawLine(x - 1, y + h - 3, x - 1, y);
/*      */ 
/* 2763 */           g.setColor(this._unselectColor2);
/* 2764 */           g.drawLine(x + 1, y + h - 2, x + w - 4, y + h - 2);
/* 2765 */           g.drawLine(x + w - 3, y + h - 3, x + w - 3, y - 1);
/*      */ 
/* 2767 */           g.setColor(this._unselectColor3);
/* 2768 */           g.drawLine(x + 1, y + h - 1, x + w - 4, y + h - 1);
/* 2769 */           g.drawLine(x + w - 3, y + h - 2, x + w - 3, y + h - 2);
/* 2770 */           g.drawLine(x + w - 2, y + h - 3, x + w - 2, y - 1);
/*      */         } else {
/* 2772 */           if (tabIndex >= this._tabPane.getSelectedIndex()) break;
/* 2773 */           g.setColor(this._unselectColor1);
/* 2774 */           g.drawLine(x + 2, y + h - 2, x + 2, y + h - 2);
/* 2775 */           g.drawLine(x + 1, y + h - 3, x + 1, y);
/*      */ 
/* 2777 */           g.setColor(this._unselectColor2);
/* 2778 */           g.drawLine(x + 3, y + h - 2, x + w - 2, y + h - 2);
/* 2779 */           g.drawLine(x + w - 1, y + h - 3, x + w - 1, y);
/*      */ 
/* 2781 */           g.setColor(this._unselectColor3);
/* 2782 */           g.drawLine(x + 3, y + h - 1, x + w - 2, y + h - 1);
/* 2783 */           g.drawLine(x + w - 1, y + h - 2, x + w - 1, y + h - 2);
/* 2784 */           g.drawLine(x + w, y + h - 3, x + w, y);
/*      */         }
/*      */ 
/*      */       }
/* 2789 */       else if (isSelected) {
/* 2790 */         g.setColor(this._selectColor1);
/* 2791 */         g.drawLine(x + 1, y + h + 1, x + w, y + h + 1);
/* 2792 */         g.drawLine(x, y + h, x, y + h);
/* 2793 */         g.drawLine(x - 1, y + h - 1, x - 1, y + h - 1);
/* 2794 */         g.drawLine(x + w + 1, y + h, x + w + 1, y + h);
/* 2795 */         g.drawLine(x + w + 2, y + h - 1, x + w + 2, y + h - 1);
/*      */ 
/* 2797 */         g.setColor(this._selectColor2);
/* 2798 */         g.drawLine(x + 1, y + h, x + w, y + h);
/* 2799 */         g.drawLine(x, y + h - 1, x + w + 1, y + h - 1);
/*      */ 
/* 2801 */         g.setColor(this._selectColor3);
/* 2802 */         g.drawLine(x - 1, y + h - 2, x - 1, y);
/* 2803 */         g.drawLine(x + w + 2, y + h - 2, x + w + 2, y);
/*      */       }
/* 2806 */       else if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 2807 */         g.setColor(this._unselectColor1);
/* 2808 */         g.drawLine(x + 3, y + h - 1, x + w - 3, y + h - 1);
/* 2809 */         g.drawLine(x + w - 2, y + h - 2, x + w - 2, y + h - 2);
/* 2810 */         g.drawLine(x + w - 1, y + h - 3, x + w - 1, y - 1);
/* 2811 */         g.drawLine(x + 2, y + h - 2, x + 2, y + h - 2);
/* 2812 */         g.drawLine(x + 1, y + h - 3, x + 1, y);
/*      */       } else {
/* 2814 */         if (tabIndex >= this._tabPane.getSelectedIndex()) break;
/* 2815 */         g.setColor(this._unselectColor1);
/* 2816 */         g.drawLine(x + 3, y + h - 1, x + w - 3, y + h - 1);
/* 2817 */         g.drawLine(x + w - 2, y + h - 2, x + w - 2, y + h - 2);
/* 2818 */         g.drawLine(x + w - 1, y + h - 3, x + w - 1, y - 1);
/* 2819 */         g.drawLine(x + 2, y + h - 2, x + 2, y + h - 2);
/* 2820 */         g.drawLine(x + 1, y + h - 3, x + 1, y); } break;
/*      */     case 1:
/*      */     default:
/* 2827 */       if ((colorTheme == 2) || (colorTheme == 1)) {
/* 2828 */         if (isSelected) {
/* 2829 */           g.setColor(this._selectColor1);
/* 2830 */           g.drawLine(x, y - 1, x, y - 1);
/* 2831 */           g.drawLine(x - 1, y, x - 1, y + h - 1);
/* 2832 */           g.drawLine(x + 1, y - 2, x + w + 1, y - 2);
/*      */ 
/* 2834 */           g.setColor(this._selectColor2);
/* 2835 */           g.drawLine(x + w + 2, y - 1, x + w + 2, y + h - 1);
/*      */ 
/* 2837 */           g.setColor(this._selectColor3);
/* 2838 */           g.drawLine(x + w + 2, y - 1, x + w + 2, y - 1);
/* 2839 */           g.drawLine(x + w + 3, y, x + w + 3, y + h - 1);
/*      */         }
/* 2842 */         else if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 2843 */           g.setColor(this._unselectColor1);
/* 2844 */           g.drawLine(x + 2, y + 1, x + 2, y + 1);
/* 2845 */           g.drawLine(x + 1, y + 2, x + 1, y + h - 1);
/* 2846 */           g.drawLine(x + 3, y, x + w - 2, y);
/*      */ 
/* 2848 */           g.setColor(this._unselectColor2);
/* 2849 */           g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 1);
/*      */ 
/* 2851 */           g.setColor(this._unselectColor3);
/* 2852 */           g.drawLine(x + w - 1, y + 1, x + w - 1, y + 1);
/* 2853 */           g.drawLine(x + w, y + 2, x + w, y + h - 1);
/*      */         } else {
/* 2855 */           if (tabIndex >= this._tabPane.getSelectedIndex()) break;
/* 2856 */           g.setColor(this._unselectColor1);
/* 2857 */           g.drawLine(x + 2, y + 1, x + 2, y + 1);
/* 2858 */           g.drawLine(x + 1, y + 2, x + 1, y + h - 1);
/* 2859 */           g.drawLine(x + 3, y, x + w - 2, y);
/*      */ 
/* 2861 */           g.setColor(this._unselectColor2);
/* 2862 */           g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 1);
/*      */ 
/* 2864 */           g.setColor(this._unselectColor3);
/* 2865 */           g.drawLine(x + w - 1, y + 1, x + w - 1, y + 1);
/* 2866 */           g.drawLine(x + w, y + 2, x + w, y + h - 1);
/*      */         }
/*      */ 
/*      */       }
/* 2871 */       else if (isSelected) {
/* 2872 */         g.setColor(this._selectColor1);
/* 2873 */         g.drawLine(x + 1, y - 2, x + w, y - 2);
/* 2874 */         g.drawLine(x, y - 1, x, y - 1);
/* 2875 */         g.drawLine(x - 1, y, x - 1, y);
/* 2876 */         g.drawLine(x + w + 1, y - 1, x + w + 1, y - 1);
/* 2877 */         g.drawLine(x + w + 2, y, x + w + 2, y);
/*      */ 
/* 2879 */         g.setColor(this._selectColor2);
/* 2880 */         g.drawLine(x + 1, y - 1, x + w, y - 1);
/* 2881 */         g.drawLine(x, y, x + w + 1, y);
/*      */ 
/* 2883 */         g.setColor(this._selectColor3);
/* 2884 */         g.drawLine(x - 1, y + 1, x - 1, y + h - 1);
/* 2885 */         g.drawLine(x + w + 2, y + 1, x + w + 2, y + h - 1);
/*      */       }
/* 2888 */       else if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 2889 */         g.setColor(this._unselectColor1);
/* 2890 */         g.drawLine(x + 1, y + 2, x + 1, y + h - 1);
/* 2891 */         g.drawLine(x + 2, y + 1, x + 2, y + 1);
/* 2892 */         g.drawLine(x + 3, y, x + w - 3, y);
/* 2893 */         g.drawLine(x + w - 2, y + 1, x + w - 2, y + 1);
/* 2894 */         g.drawLine(x + w - 1, y + 2, x + w - 1, y + h - 1);
/*      */       } else {
/* 2896 */         if (tabIndex >= this._tabPane.getSelectedIndex()) break;
/* 2897 */         g.setColor(this._unselectColor1);
/* 2898 */         g.drawLine(x + 1, y + 2, x + 1, y + h - 1);
/* 2899 */         g.drawLine(x + 2, y + 1, x + 2, y + 1);
/* 2900 */         g.drawLine(x + 3, y, x + w - 3, y);
/* 2901 */         g.drawLine(x + w - 2, y + 1, x + w - 2, y + 1);
/* 2902 */         g.drawLine(x + w - 1, y + 2, x + w - 1, y + h - 1);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintTabBorderMouseOver(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */   {
/* 2912 */     if (getTabShape() == 1) {
/* 2913 */       switch (tabPlacement) {
/*      */       case 2:
/* 2915 */         if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 2916 */           y -= 2;
/*      */         }
/*      */ 
/* 2919 */         g.setColor(this._selectColor1);
/* 2920 */         g.drawLine(x, y + 4, x, y + h - 2);
/* 2921 */         g.drawLine(x + 1, y + 3, x + 1, y + 3);
/* 2922 */         g.drawLine(x + 2, y + 2, x + 2, y + 2);
/* 2923 */         g.drawLine(x + 1, y + h - 1, x + 1, y + h - 1);
/* 2924 */         g.drawLine(x + 2, y + h, x + 2, y + h);
/*      */ 
/* 2926 */         g.setColor(this._selectColor2);
/* 2927 */         g.drawLine(x + 1, y + 4, x + 1, y + h - 2);
/* 2928 */         g.drawLine(x + 2, y + 3, x + 2, y + h - 1);
/* 2929 */         break;
/*      */       case 4:
/* 2931 */         if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 2932 */           y -= 2;
/*      */         }
/*      */ 
/* 2935 */         g.setColor(this._selectColor1);
/* 2936 */         g.drawLine(x + w - 1, y + 4, x + w - 1, y + h - 2);
/* 2937 */         g.drawLine(x + w - 2, y + 3, x + w - 2, y + 3);
/* 2938 */         g.drawLine(x + w - 3, y + 2, x + w - 3, y + 2);
/* 2939 */         g.drawLine(x + w - 2, y + h - 1, x + w - 2, y + h - 1);
/* 2940 */         g.drawLine(x + w - 3, y + h, x + w - 3, y + h);
/*      */ 
/* 2942 */         g.setColor(this._selectColor2);
/* 2943 */         g.drawLine(x + w - 2, y + 4, x + w - 2, y + h - 2);
/* 2944 */         g.drawLine(x + w - 3, y + 3, x + w - 3, y + h - 1);
/* 2945 */         break;
/*      */       case 3:
/* 2947 */         g.setColor(this._selectColor1);
/* 2948 */         g.drawLine(x + 3, y + h - 1, x + w - 3, y + h - 1);
/* 2949 */         g.drawLine(x + 2, y + h - 2, x + 2, y + h - 2);
/* 2950 */         g.drawLine(x + 1, y + h - 3, x + 1, y + h - 3);
/* 2951 */         g.drawLine(x + w - 2, y + h - 2, x + w - 2, y + h - 2);
/* 2952 */         g.drawLine(x + w - 1, y + h - 3, x + w - 1, y + h - 3);
/*      */ 
/* 2954 */         g.setColor(this._selectColor2);
/* 2955 */         g.drawLine(x + 3, y + h - 2, x + w - 3, y + h - 2);
/* 2956 */         g.drawLine(x + 2, y + h - 3, x + w - 2, y + h - 3);
/* 2957 */         break;
/*      */       case 1:
/*      */       default:
/* 2960 */         g.setColor(this._selectColor1);
/* 2961 */         g.drawLine(x + 3, y, x + w - 3, y);
/* 2962 */         g.drawLine(x + 2, y + 1, x + 2, y + 1);
/* 2963 */         g.drawLine(x + 1, y + 2, x + 1, y + 2);
/* 2964 */         g.drawLine(x + w - 2, y + 1, x + w - 2, y + 1);
/* 2965 */         g.drawLine(x + w - 1, y + 2, x + w - 1, y + 2);
/*      */ 
/* 2967 */         g.setColor(this._selectColor2);
/* 2968 */         g.drawLine(x + 3, y + 1, x + w - 3, y + 1);
/* 2969 */         g.drawLine(x + 2, y + 2, x + w - 2, y + 2); break;
/*      */       }
/*      */     }
/* 2972 */     else if (getTabShape() == 11)
/* 2973 */       switch (tabPlacement) {
/*      */       case 2:
/* 2975 */         if (getColorTheme() == 4) {
/* 2976 */           if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 2977 */             y -= 2;
/*      */           }
/*      */ 
/* 2980 */           g.setColor(this._selectColor1);
/* 2981 */           g.drawLine(x, y + 4, x, y + h - 2);
/* 2982 */           g.drawLine(x + 1, y + 3, x + 1, y + 3);
/* 2983 */           g.drawLine(x + 2, y + 2, x + 2, y + 2);
/* 2984 */           g.drawLine(x + 1, y + h - 1, x + 1, y + h - 1);
/* 2985 */           g.drawLine(x + 2, y + h, x + 2, y + h);
/*      */ 
/* 2987 */           g.setColor(this._selectColor2);
/* 2988 */           g.drawLine(x + 1, y + 4, x + 1, y + h - 2);
/* 2989 */           g.drawLine(x + 2, y + 3, x + 2, y + h - 1);
/*      */ 
/* 2991 */           g.setColor(this._selectColor3);
/* 2992 */           g.drawLine(x + 3, y + 2, x + w - 1, y + 2);
/* 2993 */           g.drawLine(x + 3, y + h, x + w - 1, y + h);
/*      */         }
/*      */         else {
/* 2996 */           if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 2997 */             y -= 1;
/*      */           }
/* 2999 */           g.setColor(this._selectColor1);
/* 3000 */           g.drawLine(x, y + 3, x, y + h - 2);
/* 3001 */           g.drawLine(x + 1, y + 2, x + 1, y + 2);
/* 3002 */           g.drawLine(x + 2, y + 1, x + w - 1, y + 1);
/*      */ 
/* 3004 */           g.setColor(this._selectColor2);
/* 3005 */           g.drawLine(x + 1, y + h - 1, x + 1, y + h - 1);
/* 3006 */           g.drawLine(x + 2, y + h, x + 2, y + h);
/* 3007 */           g.drawLine(x + 3, y + h - 1, x + w - 1, y + h - 1);
/*      */ 
/* 3009 */           g.setColor(this._selectColor3);
/* 3010 */           g.drawLine(x + 2, y + h - 1, x + 2, y + h - 1);
/* 3011 */           g.drawLine(x + 3, y + h, x + w - 1, y + h);
/*      */         }
/* 3013 */         break;
/*      */       case 4:
/* 3015 */         if (getColorTheme() == 4) {
/* 3016 */           if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 3017 */             y -= 2;
/*      */           }
/*      */ 
/* 3020 */           g.setColor(this._selectColor1);
/* 3021 */           g.drawLine(x + w - 1, y + 4, x + w - 1, y + h - 2);
/* 3022 */           g.drawLine(x + w - 2, y + 3, x + w - 2, y + 3);
/* 3023 */           g.drawLine(x + w - 3, y + 2, x + w - 3, y + 2);
/* 3024 */           g.drawLine(x + w - 2, y + h - 1, x + w - 2, y + h - 1);
/* 3025 */           g.drawLine(x + w - 3, y + h, x + w - 3, y + h);
/*      */ 
/* 3027 */           g.setColor(this._selectColor2);
/* 3028 */           g.drawLine(x + w - 2, y + 4, x + w - 2, y + h - 2);
/* 3029 */           g.drawLine(x + w - 3, y + 3, x + w - 3, y + h - 1);
/*      */ 
/* 3031 */           g.setColor(this._selectColor3);
/* 3032 */           g.drawLine(x + w - 4, y + 2, x, y + 2);
/* 3033 */           g.drawLine(x + w - 4, y + h, x, y + h);
/*      */         }
/*      */         else {
/* 3036 */           if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 3037 */             y -= 1;
/*      */           }
/*      */ 
/* 3040 */           g.setColor(this._selectColor3);
/* 3041 */           g.drawLine(x + w - 1, y + 3, x + w - 1, y + h - 2);
/* 3042 */           g.drawLine(x + w - 2, y + h - 1, x + w - 2, y + h - 1);
/* 3043 */           g.drawLine(x + w - 3, y + h, x, y + h);
/*      */ 
/* 3045 */           g.setColor(this._selectColor2);
/* 3046 */           g.drawLine(x + w - 2, y + 3, x + w - 2, y + h - 2);
/* 3047 */           g.drawLine(x + w - 3, y + h - 1, x, y + h - 1);
/*      */ 
/* 3049 */           g.setColor(this._selectColor1);
/* 3050 */           g.drawLine(x + w - 2, y + 2, x + w - 2, y + 2);
/* 3051 */           g.drawLine(x + w - 3, y + 1, x, y + 1);
/*      */         }
/* 3053 */         break;
/*      */       case 3:
/* 3055 */         if (getColorTheme() == 4) {
/* 3056 */           g.setColor(this._selectColor1);
/* 3057 */           g.drawLine(x + 3, y + h - 1, x + w - 3, y + h - 1);
/* 3058 */           g.drawLine(x + 2, y + h - 2, x + 2, y + h - 2);
/* 3059 */           g.drawLine(x + 1, y + h - 3, x + 1, y + h - 3);
/* 3060 */           g.drawLine(x + w - 2, y + h - 2, x + w - 2, y + h - 2);
/* 3061 */           g.drawLine(x + w - 1, y + h - 3, x + w - 1, y + h - 3);
/*      */ 
/* 3063 */           g.setColor(this._selectColor2);
/* 3064 */           g.drawLine(x + 3, y + h - 2, x + w - 3, y + h - 2);
/* 3065 */           g.drawLine(x + 2, y + h - 3, x + w - 2, y + h - 3);
/*      */ 
/* 3067 */           g.setColor(this._selectColor3);
/* 3068 */           g.drawLine(x + 1, y, x + 1, y + h - 4);
/* 3069 */           g.drawLine(x + w - 1, y, x + w - 1, y + h - 4);
/*      */         }
/*      */         else {
/* 3072 */           if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 3073 */             x -= 2;
/*      */           }
/*      */ 
/* 3076 */           g.setColor(this._selectColor3);
/* 3077 */           g.drawLine(x + 3, y + h - 1, x + w - 2, y + h - 1);
/* 3078 */           g.drawLine(x + w - 1, y + h - 2, x + w - 1, y + h - 2);
/* 3079 */           g.drawLine(x + w, y + h - 3, x + w, y);
/*      */ 
/* 3081 */           g.setColor(this._selectColor1);
/* 3082 */           g.drawLine(x + 2, y + h - 2, x + 2, y + h - 2);
/* 3083 */           g.drawLine(x + 1, y + h - 3, x + 1, y);
/*      */ 
/* 3085 */           g.setColor(this._selectColor2);
/* 3086 */           g.drawLine(x + 3, y + h - 2, x + w - 2, y + h - 2);
/* 3087 */           g.drawLine(x + w - 1, y + h - 3, x + w - 1, y);
/*      */         }
/* 3089 */         break;
/*      */       case 1:
/*      */       default:
/* 3092 */         if (getColorTheme() == 4) {
/* 3093 */           g.setColor(this._selectColor1);
/* 3094 */           g.drawLine(x + 3, y, x + w - 3, y);
/* 3095 */           g.drawLine(x + 2, y + 1, x + 2, y + 1);
/* 3096 */           g.drawLine(x + 1, y + 2, x + 1, y + 2);
/* 3097 */           g.drawLine(x + w - 2, y + 1, x + w - 2, y + 1);
/* 3098 */           g.drawLine(x + w - 1, y + 2, x + w - 1, y + 2);
/*      */ 
/* 3100 */           g.setColor(this._selectColor2);
/* 3101 */           g.drawLine(x + 3, y + 1, x + w - 3, y + 1);
/* 3102 */           g.drawLine(x + 2, y + 2, x + w - 2, y + 2);
/*      */ 
/* 3104 */           g.setColor(this._selectColor3);
/* 3105 */           g.drawLine(x + 1, y + 3, x + 1, y + h - 1);
/* 3106 */           g.drawLine(x + w - 1, y + 3, x + w - 1, y + h - 1);
/*      */         }
/*      */         else {
/* 3109 */           if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 3110 */             x -= 1;
/*      */           }
/* 3112 */           g.setColor(this._selectColor1);
/* 3113 */           g.drawLine(x + 2, y + 1, x + 2, y + 1);
/* 3114 */           g.drawLine(x + 1, y + 2, x + 1, y + h - 1);
/* 3115 */           g.drawLine(x + 3, y, x + w - 2, y);
/*      */ 
/* 3117 */           g.setColor(this._selectColor2);
/* 3118 */           g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 1);
/*      */ 
/* 3120 */           g.setColor(this._selectColor3);
/* 3121 */           g.drawLine(x + w - 1, y + 1, x + w - 1, y + 1);
/* 3122 */           g.drawLine(x + w, y + 2, x + w, y + h - 1);
/*      */         }
/*      */       }
/*      */   }
/*      */ 
/*      */   protected void paintVsnetTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */   {
/* 3131 */     boolean leftToRight = this._tabPane.getComponentOrientation().isLeftToRight();
/* 3132 */     switch (tabPlacement) {
/*      */     case 2:
/* 3134 */       if (isSelected) {
/* 3135 */         g.setColor(this._selectColor1);
/* 3136 */         g.drawLine(x, y, x + w - 1, y);
/* 3137 */         g.drawLine(x, y, x, y + h - 2);
/*      */ 
/* 3139 */         g.setColor(this._selectColor2);
/* 3140 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*      */       else {
/* 3143 */         g.setColor(this._unselectColor1);
/* 3144 */         if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 3145 */           g.drawLine(x + 2, y + h - 2, x + w - 2, y + h - 2);
/*      */         } else {
/* 3147 */           if ((tabIndex >= this._tabPane.getSelectedIndex()) || (tabIndex == 0)) break;
/* 3148 */           g.drawLine(x + 2, y, x + w - 2, y); } 
/* 3148 */       }break;
/*      */     case 4:
/* 3153 */       if (isSelected) {
/* 3154 */         g.setColor(this._selectColor1);
/* 3155 */         g.drawLine(x, y, x + w - 1, y);
/*      */ 
/* 3157 */         g.setColor(this._selectColor2);
/* 3158 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 2);
/* 3159 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*      */       else {
/* 3162 */         g.setColor(this._unselectColor1);
/* 3163 */         if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 3164 */           g.drawLine(x + 1, y + h - 2, x + w - 3, y + h - 2);
/*      */         } else {
/* 3166 */           if ((tabIndex >= this._tabPane.getSelectedIndex()) || (tabIndex == 0)) break;
/* 3167 */           g.drawLine(x + 1, y, x + w - 3, y); } 
/* 3167 */       }break;
/*      */     case 3:
/* 3172 */       if (isSelected) {
/* 3173 */         g.setColor(this._selectColor1);
/* 3174 */         g.drawLine(x, y, x, y + h - 1);
/*      */ 
/* 3176 */         g.setColor(this._selectColor2);
/* 3177 */         g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/* 3178 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 2);
/*      */       }
/*      */       else {
/* 3181 */         g.setColor(this._unselectColor1);
/* 3182 */         if (leftToRight) {
/* 3183 */           if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 3184 */             g.drawLine(x + w - 2, y + 2, x + w - 2, y + h - 2);
/*      */           } else {
/* 3186 */             if ((tabIndex >= this._tabPane.getSelectedIndex()) || (tabIndex == 0)) break;
/* 3187 */             g.drawLine(x, y + 2, x, y + h - 2);
/*      */           }
/*      */ 
/*      */         }
/* 3191 */         else if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 3192 */           g.drawLine(x, y + 2, x, y + h - 2);
/*      */         } else {
/* 3194 */           if ((tabIndex >= this._tabPane.getSelectedIndex()) || (tabIndex == this._tabPane.getTabCount() - 1)) break;
/* 3195 */           g.drawLine(x + w - 2, y + 2, x + w - 2, y + h - 2); } 
/* 3195 */       }break;
/*      */     case 1:
/*      */     default:
/* 3202 */       if (isSelected) {
/* 3203 */         g.setColor(this._selectColor1);
/* 3204 */         g.drawLine(x, y + 1, x, y + h - 1);
/* 3205 */         g.drawLine(x, y, x + w - 1, y);
/*      */ 
/* 3207 */         g.setColor(this._selectColor2);
/* 3208 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/*      */       }
/*      */       else {
/* 3211 */         g.setColor(this._unselectColor1);
/* 3212 */         if (leftToRight) {
/* 3213 */           if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 3214 */             g.drawLine(x + w - 2, y + 2, x + w - 2, y + h - 2);
/*      */           } else {
/* 3216 */             if ((tabIndex >= this._tabPane.getSelectedIndex()) || (tabIndex == 0)) break;
/* 3217 */             g.drawLine(x, y + 2, x, y + h - 2);
/*      */           }
/*      */ 
/*      */         }
/* 3221 */         else if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 3222 */           g.drawLine(x, y + 2, x, y + h - 2);
/*      */         } else {
/* 3224 */           if ((tabIndex >= this._tabPane.getSelectedIndex()) || (tabIndex == this._tabPane.getTabCount() - 1)) break;
/* 3225 */           g.drawLine(x + w - 2, y + 2, x + w - 2, y + h - 2);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintRoundedVsnetTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */   {
/* 3235 */     boolean leftToRight = this._tabPane.getComponentOrientation().isLeftToRight();
/* 3236 */     switch (tabPlacement) {
/*      */     case 2:
/* 3238 */       if (isSelected) {
/* 3239 */         g.setColor(this._selectColor1);
/* 3240 */         g.drawLine(x + 2, y, x + w - 1, y);
/* 3241 */         g.drawLine(x + 1, y + 1, x + 1, y + 1);
/* 3242 */         g.drawLine(x, y + 2, x, y + h - 3);
/* 3243 */         g.drawLine(x + 1, y + h - 2, x + 1, y + h - 2);
/* 3244 */         g.drawLine(x + 2, y + h - 1, x + w - 1, y + h - 1);
/*      */       }
/*      */       else {
/* 3247 */         g.setColor(this._unselectColor1);
/* 3248 */         if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 3249 */           g.drawLine(x + 2, y + h - 2, x + w - 2, y + h - 2);
/*      */         } else {
/* 3251 */           if ((tabIndex >= this._tabPane.getSelectedIndex()) || (tabIndex == 0)) break;
/* 3252 */           g.drawLine(x + 2, y + 1, x + w - 2, y + 1); } 
/* 3252 */       }break;
/*      */     case 4:
/* 3257 */       if (isSelected) {
/* 3258 */         g.setColor(this._selectColor1);
/* 3259 */         g.drawLine(x, y, x + w - 3, y);
/* 3260 */         g.drawLine(x + w - 2, y + 1, x + w - 2, y + 1);
/* 3261 */         g.drawLine(x + w - 1, y + 2, x + w - 1, y + h - 3);
/* 3262 */         g.drawLine(x + w - 2, y + h - 2, x + w - 2, y + h - 2);
/* 3263 */         g.drawLine(x, y + h - 1, x + w - 3, y + h - 1);
/*      */       }
/*      */       else {
/* 3266 */         g.setColor(this._unselectColor1);
/* 3267 */         if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 3268 */           g.drawLine(x + 1, y + h - 2, x + w - 3, y + h - 2);
/*      */         } else {
/* 3270 */           if ((tabIndex >= this._tabPane.getSelectedIndex()) || (tabIndex == 0)) break;
/* 3271 */           g.drawLine(x + 1, y + 1, x + w - 3, y + 1); } 
/* 3271 */       }break;
/*      */     case 3:
/* 3276 */       if (isSelected) {
/* 3277 */         g.setColor(this._selectColor1);
/* 3278 */         g.drawLine(x, y, x, y + h - 3);
/* 3279 */         g.drawLine(x + 1, y + h - 2, x + 1, y + h - 2);
/* 3280 */         g.drawLine(x + 2, y + h - 1, x + w - 3, y + h - 1);
/* 3281 */         g.drawLine(x + w - 2, y + h - 2, x + w - 2, y + h - 2);
/* 3282 */         g.drawLine(x + w - 1, y, x + w - 1, y + h - 3);
/*      */       }
/*      */       else {
/* 3285 */         g.setColor(this._unselectColor1);
/* 3286 */         if (leftToRight) {
/* 3287 */           if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 3288 */             g.drawLine(x + w - 2, y + 2, x + w - 2, y + h - 2);
/*      */           } else {
/* 3290 */             if ((tabIndex >= this._tabPane.getSelectedIndex()) || (tabIndex == 0)) break;
/* 3291 */             g.drawLine(x, y + 2, x, y + h - 2);
/*      */           }
/*      */ 
/*      */         }
/* 3295 */         else if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 3296 */           g.drawLine(x, y + 2, x, y + h - 2);
/*      */         } else {
/* 3298 */           if ((tabIndex >= this._tabPane.getSelectedIndex()) || (tabIndex == this._tabPane.getTabCount() - 1)) break;
/* 3299 */           g.drawLine(x + w - 2, y + 2, x + w - 2, y + h - 2); } 
/* 3299 */       }break;
/*      */     case 1:
/*      */     default:
/* 3306 */       if (isSelected) {
/* 3307 */         g.setColor(this._selectColor1);
/* 3308 */         g.drawLine(x, y + 2, x, y + h - 1);
/* 3309 */         g.drawLine(x, y + 2, x + 2, y);
/* 3310 */         g.drawLine(x + 2, y, x + w - 3, y);
/* 3311 */         g.drawLine(x + w - 3, y, x + w - 1, y + 2);
/* 3312 */         g.drawLine(x + w - 1, y + 2, x + w - 1, y + h - 1);
/*      */       }
/*      */       else {
/* 3315 */         g.setColor(this._unselectColor1);
/* 3316 */         if (leftToRight) {
/* 3317 */           if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 3318 */             g.drawLine(x + w - 2, y + 2, x + w - 2, y + h - 2);
/*      */           } else {
/* 3320 */             if ((tabIndex >= this._tabPane.getSelectedIndex()) || (tabIndex == 0)) break;
/* 3321 */             g.drawLine(x, y + 2, x, y + h - 2);
/*      */           }
/*      */ 
/*      */         }
/* 3325 */         else if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 3326 */           g.drawLine(x, y + 2, x, y + h - 2);
/*      */         } else {
/* 3328 */           if ((tabIndex >= this._tabPane.getSelectedIndex()) || (tabIndex == this._tabPane.getTabCount() - 1)) break;
/* 3329 */           g.drawLine(x + w - 2, y + 2, x + w - 2, y + h - 2);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintFlatTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */   {
/* 3339 */     switch (tabPlacement) {
/*      */     case 2:
/* 3341 */       if (isSelected) {
/* 3342 */         g.setColor(this._selectColor1);
/* 3343 */         g.drawRect(x, y, w, h);
/*      */       }
/*      */       else {
/* 3346 */         g.setColor(this._unselectColor1);
/* 3347 */         if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 3348 */           if (tabIndex == this._tabPane.getTabCount() - 1) {
/* 3349 */             g.drawRect(x, y, w, h - 1);
/*      */           }
/*      */           else
/* 3352 */             g.drawRect(x, y, w, h);
/*      */         }
/*      */         else {
/* 3355 */           if (tabIndex >= this._tabPane.getSelectedIndex()) break;
/* 3356 */           g.drawRect(x, y, w, h); } 
/* 3356 */       }break;
/*      */     case 4:
/* 3361 */       if (isSelected) {
/* 3362 */         g.setColor(this._selectColor1);
/* 3363 */         g.drawRect(x - 1, y, w, h);
/*      */       }
/*      */       else {
/* 3366 */         g.setColor(this._unselectColor1);
/* 3367 */         if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 3368 */           if (tabIndex == this._tabPane.getTabCount() - 1) {
/* 3369 */             g.drawRect(x - 1, y, w, h - 1);
/*      */           }
/*      */           else
/* 3372 */             g.drawRect(x - 1, y, w, h);
/*      */         }
/*      */         else {
/* 3375 */           if (tabIndex >= this._tabPane.getSelectedIndex()) break;
/* 3376 */           g.drawRect(x - 1, y, w, h); } 
/* 3376 */       }break;
/*      */     case 3:
/* 3381 */       if (isSelected) {
/* 3382 */         g.setColor(this._selectColor1);
/* 3383 */         g.drawRect(x, y - 1, w, h);
/*      */       }
/*      */       else {
/* 3386 */         g.setColor(this._unselectColor1);
/* 3387 */         g.drawRect(x, y - 1, w, h);
/*      */       }
/* 3389 */       break;
/*      */     case 1:
/*      */     default:
/* 3392 */       if (isSelected) {
/* 3393 */         g.setColor(this._selectColor1);
/* 3394 */         g.drawRect(x, y, w, h);
/*      */       }
/*      */       else {
/* 3397 */         g.setColor(this._unselectColor1);
/* 3398 */         g.drawRect(x, y, w, h);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintRoundedFlatTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */   {
/* 3405 */     switch (tabPlacement) {
/*      */     case 2:
/* 3407 */       if (isSelected) {
/* 3408 */         g.setColor(this._selectColor1);
/* 3409 */         g.drawLine(x + 2, y, x + w - 1, y);
/* 3410 */         g.drawLine(x + 2, y + h, x + w - 1, y + h);
/* 3411 */         g.drawLine(x, y + 2, x, y + h - 2);
/*      */ 
/* 3413 */         g.setColor(this._selectColor2);
/* 3414 */         g.drawLine(x + 1, y + 1, x + 1, y + 1);
/*      */ 
/* 3416 */         g.drawLine(x + 1, y + h - 1, x + 1, y + h - 1);
/*      */       }
/* 3420 */       else if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 3421 */         g.setColor(this._unselectColor1);
/* 3422 */         g.drawLine(x + 2, y, x + w - 1, y);
/*      */ 
/* 3424 */         if (tabIndex == this._tabPane.getTabCount() - 1) {
/* 3425 */           g.drawLine(x + 2, y + h - 1, x + w - 1, y + h - 1);
/* 3426 */           g.drawLine(x, y + 2, x, y + h - 3);
/*      */         }
/*      */         else {
/* 3429 */           g.drawLine(x + 2, y + h, x + w - 1, y + h);
/* 3430 */           g.drawLine(x, y + 2, x, y + h - 2);
/*      */         }
/*      */ 
/* 3433 */         g.setColor(this._unselectColor2);
/* 3434 */         g.drawLine(x + 1, y + 1, x + 1, y + 1);
/*      */ 
/* 3437 */         if (tabIndex == this._tabPane.getTabCount() - 1) {
/* 3438 */           g.drawLine(x, y + h - 2, x, y + h - 2);
/* 3439 */           g.drawLine(x + 1, y + h - 1, x + 1, y + h - 1);
/*      */         }
/*      */         else {
/* 3442 */           g.drawLine(x, y + h - 1, x, y + h - 1);
/* 3443 */           g.drawLine(x + 1, y + h, x + 1, y + h);
/*      */         }
/*      */       } else {
/* 3446 */         if (tabIndex >= this._tabPane.getSelectedIndex()) break;
/* 3447 */         g.setColor(this._unselectColor1);
/* 3448 */         g.drawLine(x + 2, y, x + w - 1, y);
/* 3449 */         g.drawLine(x + 2, y + h, x + w - 1, y + h);
/* 3450 */         g.drawLine(x, y + 2, x, y + h - 2);
/*      */ 
/* 3452 */         g.setColor(this._unselectColor2);
/* 3453 */         g.drawLine(x + 1, y + 1, x + 1, y + 1);
/*      */ 
/* 3455 */         g.drawLine(x + 1, y + h - 1, x + 1, y + h - 1); } break;
/*      */     case 4:
/* 3461 */       if (isSelected) {
/* 3462 */         g.setColor(this._selectColor1);
/* 3463 */         g.drawLine(x, y, x + w - 3, y);
/* 3464 */         g.drawLine(x, y + h, x + w - 3, y + h);
/* 3465 */         g.drawLine(x + w - 1, y + 2, x + w - 1, y + h - 2);
/*      */ 
/* 3467 */         g.setColor(this._selectColor2);
/* 3468 */         g.drawLine(x + w - 2, y + 1, x + w - 2, y + 1);
/*      */ 
/* 3471 */         g.drawLine(x + w - 2, y + h - 1, x + w - 2, y + h - 1);
/*      */       }
/* 3474 */       else if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 3475 */         g.setColor(this._unselectColor1);
/* 3476 */         g.drawLine(x, y, x + w - 3, y);
/*      */ 
/* 3478 */         if (tabIndex == this._tabPane.getTabCount() - 1) {
/* 3479 */           g.drawLine(x, y + h - 1, x + w - 3, y + h - 1);
/* 3480 */           g.drawLine(x + w - 1, y + 2, x + w - 1, y + h - 3);
/*      */         }
/*      */         else {
/* 3483 */           g.drawLine(x, y + h, x + w - 3, y + h);
/* 3484 */           g.drawLine(x + w - 1, y + 2, x + w - 1, y + h - 2);
/*      */         }
/*      */ 
/* 3487 */         g.setColor(this._unselectColor2);
/* 3488 */         g.drawLine(x + w - 2, y + 1, x + w - 2, y + 1);
/*      */ 
/* 3491 */         if (tabIndex == this._tabPane.getTabCount() - 1) {
/* 3492 */           g.drawLine(x + w - 2, y + h - 2, x + w - 2, y + h - 2);
/*      */         }
/*      */         else
/*      */         {
/* 3497 */           g.drawLine(x + w - 2, y + h - 1, x + w - 2, y + h - 1);
/*      */         }
/*      */       } else {
/* 3500 */         if (tabIndex >= this._tabPane.getSelectedIndex()) break;
/* 3501 */         g.setColor(this._unselectColor1);
/* 3502 */         g.drawLine(x, y, x + w - 3, y);
/* 3503 */         g.drawLine(x, y + h, x + w - 3, y + h);
/* 3504 */         g.drawLine(x + w - 1, y + 2, x + w - 1, y + h - 2);
/*      */ 
/* 3506 */         g.setColor(this._unselectColor2);
/* 3507 */         g.drawLine(x + w - 2, y + 1, x + w - 2, y + 1);
/*      */ 
/* 3509 */         g.drawLine(x + w - 2, y + h - 1, x + w - 2, y + h - 1); } break;
/*      */     case 3:
/* 3515 */       if (isSelected) {
/* 3516 */         g.setColor(this._selectColor1);
/* 3517 */         g.drawLine(x, y, x, y + h - 3);
/* 3518 */         g.drawLine(x + 2, y + h - 1, x + w - 2, y + h - 1);
/* 3519 */         g.drawLine(x + w, y, x + w, y + h - 3);
/*      */ 
/* 3521 */         g.setColor(this._selectColor2);
/* 3522 */         g.drawLine(x + 1, y + h - 2, x + 1, y + h - 2);
/*      */ 
/* 3525 */         g.drawLine(x + w - 1, y + h - 2, x + w - 1, y + h - 2);
/*      */       }
/* 3528 */       else if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 3529 */         g.setColor(this._unselectColor1);
/* 3530 */         g.drawLine(x, y, x, y + h - 3);
/*      */ 
/* 3532 */         if (tabIndex == this._tabPane.getTabCount() - 1) {
/* 3533 */           g.drawLine(x + 2, y + h - 1, x + w - 3, y + h - 1);
/* 3534 */           g.drawLine(x + w - 1, y, x + w - 1, y + h - 3);
/*      */         }
/*      */         else {
/* 3537 */           g.drawLine(x + 2, y + h - 1, x + w - 2, y + h - 1);
/* 3538 */           g.drawLine(x + w, y, x + w, y + h - 3);
/*      */         }
/*      */ 
/* 3541 */         g.setColor(this._unselectColor2);
/* 3542 */         g.drawLine(x + 1, y + h - 2, x + 1, y + h - 2);
/*      */ 
/* 3545 */         if (tabIndex == this._tabPane.getTabCount() - 1)
/*      */         {
/* 3547 */           g.drawLine(x + w - 2, y + h - 2, x + w - 2, y + h - 2);
/*      */         }
/*      */         else
/*      */         {
/* 3551 */           g.drawLine(x + w - 1, y + h - 2, x + w - 1, y + h - 2);
/*      */         }
/*      */       } else {
/* 3554 */         if (tabIndex >= this._tabPane.getSelectedIndex()) break;
/* 3555 */         g.setColor(this._unselectColor1);
/* 3556 */         g.drawLine(x, y, x, y + h - 3);
/* 3557 */         g.drawLine(x + 2, y + h - 1, x + w - 2, y + h - 1);
/* 3558 */         g.drawLine(x + w, y, x + w, y + h - 3);
/*      */ 
/* 3560 */         g.setColor(this._unselectColor2);
/* 3561 */         g.drawLine(x + 1, y + h - 2, x + 1, y + h - 2);
/*      */ 
/* 3564 */         g.drawLine(x + w - 1, y + h - 2, x + w - 1, y + h - 2); } break;
/*      */     case 1:
/*      */     default:
/* 3570 */       if (isSelected) {
/* 3571 */         g.setColor(this._selectColor1);
/* 3572 */         g.drawLine(x, y + h - 1, x, y + 2);
/* 3573 */         g.drawLine(x + 2, y, x + w - 2, y);
/* 3574 */         g.drawLine(x + w, y + 2, x + w, y + h - 1);
/*      */ 
/* 3576 */         g.setColor(this._selectColor2);
/* 3577 */         g.drawLine(x, y + 2, x + 2, y);
/* 3578 */         g.drawLine(x + w - 2, y, x + w, y + 2);
/*      */       }
/* 3582 */       else if (tabIndex > this._tabPane.getSelectedIndex()) {
/* 3583 */         g.setColor(this._unselectColor1);
/* 3584 */         g.drawLine(x, y + h - 1, x, y + 2);
/*      */ 
/* 3586 */         if (tabIndex == this._tabPane.getTabCount() - 1) {
/* 3587 */           g.drawLine(x + 2, y, x + w - 3, y);
/* 3588 */           g.drawLine(x + w - 1, y + 2, x + w - 1, y + h - 1);
/*      */         }
/*      */         else {
/* 3591 */           g.drawLine(x + 2, y, x + w - 2, y);
/* 3592 */           g.drawLine(x + w, y + 2, x + w, y + h - 1);
/*      */         }
/*      */ 
/* 3595 */         g.setColor(this._unselectColor2);
/* 3596 */         g.drawLine(x, y + 2, x + 2, y);
/*      */ 
/* 3598 */         if (tabIndex == this._tabPane.getTabCount() - 1) {
/* 3599 */           g.drawLine(x + w - 3, y, x + w - 1, y + 2);
/*      */         }
/*      */         else
/* 3602 */           g.drawLine(x + w - 2, y, x + w, y + 2);
/*      */       }
/*      */       else {
/* 3605 */         if (tabIndex >= this._tabPane.getSelectedIndex()) break;
/* 3606 */         g.setColor(this._unselectColor1);
/* 3607 */         g.drawLine(x, y + h - 1, x, y + 2);
/* 3608 */         g.drawLine(x + 2, y, x + w - 2, y);
/* 3609 */         g.drawLine(x + w, y + 2, x + w, y + h - 1);
/*      */ 
/* 3611 */         g.setColor(this._unselectColor2);
/* 3612 */         g.drawLine(x, y + 2, x + 2, y);
/* 3613 */         g.drawLine(x + w - 2, y, x + w, y + 2);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintBoxTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */   {
/* 3621 */     boolean leftToRight = this._tabPane.getComponentOrientation().isLeftToRight();
/*      */ 
/* 3623 */     if (isSelected) {
/* 3624 */       g.setColor(this._selectColor1);
/* 3625 */       g.drawLine(x, y, x + w - 2, y);
/* 3626 */       g.drawLine(x, y, x, y + h - 2);
/*      */ 
/* 3628 */       g.setColor(this._selectColor2);
/* 3629 */       g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
/* 3630 */       g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
/*      */     }
/* 3633 */     else if (tabIndex != this._tabPane.getSelectedIndex() - 1) {
/* 3634 */       switch (tabPlacement) {
/*      */       case 2:
/*      */       case 4:
/* 3637 */         g.setColor(this._unselectColor1);
/* 3638 */         g.drawLine(x + 2, y + h, x + w - 2, y + h);
/*      */ 
/* 3640 */         g.setColor(this._unselectColor2);
/* 3641 */         g.drawLine(x + 2, y + h + 1, x + w - 2, y + h + 1);
/* 3642 */         break;
/*      */       case 1:
/*      */       case 3:
/*      */       default:
/* 3646 */         if (leftToRight) {
/* 3647 */           g.setColor(this._unselectColor1);
/* 3648 */           g.drawLine(x + w, y + 2, x + w, y + h - 2);
/*      */ 
/* 3650 */           g.setColor(this._unselectColor2);
/* 3651 */           g.drawLine(x + w + 1, y + 2, x + w + 1, y + h - 2);
/*      */         }
/*      */         else {
/* 3654 */           g.setColor(this._unselectColor1);
/* 3655 */           g.drawLine(x, y + 2, x, y + h - 2);
/*      */ 
/* 3657 */           g.setColor(this._unselectColor2);
/* 3658 */           g.drawLine(x + 1, y + 2, x + 1, y + h - 2);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */   {
/* 3670 */     switch (getTabShape()) {
/*      */     case 3:
/* 3672 */       paintButtonTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
/* 3673 */       break;
/*      */     case 8:
/* 3675 */       paintExcelTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
/* 3676 */       break;
/*      */     case 1:
/* 3678 */       paintDefaultTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
/* 3679 */       break;
/*      */     case 11:
/* 3681 */       if (!isSelected) break;
/* 3682 */       paintDefaultTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected); break;
/*      */     case 2:
/*      */     case 9:
/* 3687 */       paintVsnetTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
/* 3688 */       break;
/*      */     case 5:
/*      */     case 10:
/* 3691 */       paintFlatTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
/* 3692 */       break;
/*      */     case 4:
/*      */     case 6:
/*      */     case 7:
/*      */     default:
/* 3695 */       paintOffice2003TabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintOffice2003TabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */   {
/* 3703 */     boolean leftToRight = this._tabPane.getComponentOrientation().isLeftToRight();
/* 3704 */     switch (tabPlacement) {
/*      */     case 2:
/* 3706 */       if ((tabIndex != 0) && (!isSelected)) {
/* 3707 */         int[] xp = { x + w, x + 4, x + 2, x, x, x + 3, x + w };
/* 3708 */         int[] yp = { y, y, y + 2, y + 5, y + h - 5, y + h - 2, y + h - 2 };
/* 3709 */         int np = yp.length;
/* 3710 */         this.tabRegion = new Polygon(xp, yp, np);
/*      */       }
/*      */       else {
/* 3713 */         int[] xp = { x + w, x + 2, x, x, x + 3, x + w };
/* 3714 */         int[] yp = { y - w + 2 + 2, y + 2, y + 5, y + h - 5, y + h - 2, y + h - 2 };
/* 3715 */         int np = yp.length;
/* 3716 */         this.tabRegion = new Polygon(xp, yp, np);
/*      */       }
/* 3718 */       break;
/*      */     case 4:
/* 3720 */       if ((tabIndex != 0) && (!isSelected)) {
/* 3721 */         int[] xp = { x, x + w - 4, x + w - 3, x + w - 1, x + w - 1, x + w - 3, x };
/* 3722 */         int[] yp = { y, y, y + 2, y + 5, y + h - 5, y + h - 2, y + h - 2 };
/* 3723 */         int np = yp.length;
/* 3724 */         this.tabRegion = new Polygon(xp, yp, np);
/*      */       }
/*      */       else {
/* 3727 */         int[] xp = { x, x + w - 3, x + w - 1, x + w - 1, x + w - 3, x };
/* 3728 */         int[] yp = { y - w + 2 + 2, y + 2, y + 5, y + h - 5, y + h - 2, y + h - 2 };
/* 3729 */         int np = yp.length;
/* 3730 */         this.tabRegion = new Polygon(xp, yp, np);
/*      */       }
/* 3732 */       break;
/*      */     case 3:
/* 3734 */       if (leftToRight) {
/* 3735 */         int[] xp = { x - ((tabIndex == 0) || (isSelected) ? h - 5 : 0), x, x + 4, x + w - 3, x + w - 1, x + w - 1 };
/* 3736 */         int[] yp = { y, y + h - 5, y + h - 1, y + h - 1, y + h - 5, y };
/* 3737 */         int np = yp.length;
/* 3738 */         this.tabRegion = new Polygon(xp, yp, np);
/*      */       }
/*      */       else {
/* 3741 */         int[] xp = { x, x, x + 2, x + w - 5, x + w - 1, x + w - 1 + ((tabIndex == 0) || (isSelected) ? h - 5 : 0) };
/* 3742 */         int[] yp = { y, y + h - 5, y + h - 1, y + h - 1, y + h - 5, y };
/* 3743 */         int np = yp.length;
/* 3744 */         this.tabRegion = new Polygon(xp, yp, np);
/*      */       }
/* 3746 */       break;
/*      */     case 1:
/*      */     default:
/* 3749 */       if (leftToRight) {
/* 3750 */         int[] xp = { x - ((tabIndex == 0) || (isSelected) ? h - 5 : 0), x, x + 4, x + w - 3, x + w - 1, x + w - 1 };
/* 3751 */         int[] yp = { y + h, y + 3, y + 1, y + 1, y + 3, y + h };
/* 3752 */         int np = yp.length;
/* 3753 */         this.tabRegion = new Polygon(xp, yp, np);
/*      */       }
/*      */       else {
/* 3756 */         int[] xp = { x, x, x + 2, x + w - 5, x + w - 1, x + w - 1 + ((tabIndex == 0) || (isSelected) ? h - 5 : 0) };
/* 3757 */         int[] yp = { y + h, y + 3, y + 1, y + 1, y + 3, y + h };
/* 3758 */         int np = yp.length;
/* 3759 */         this.tabRegion = new Polygon(xp, yp, np);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintExcelTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */   {
/* 3767 */     boolean leftToRight = this._tabPane.getComponentOrientation().isLeftToRight();
/* 3768 */     switch (tabPlacement) {
/*      */     case 2:
/* 3770 */       if (!isSelected) {
/* 3771 */         if (((leftToRight) && (tabIndex == 0)) || ((!leftToRight) && (tabIndex == this._tabPane.getTabCount() - 1))) {
/* 3772 */           int[] xp = { x + w, x, x, x + w };
/* 3773 */           int[] yp = { y - 5, y + 5, y + h - 5, y + h + 6 };
/* 3774 */           int np = yp.length;
/* 3775 */           this.tabRegion = new Polygon(xp, yp, np);
/*      */         }
/*      */         else {
/* 3778 */           int[] xp = { x + w, x + 9, x, x, x + w };
/* 3779 */           int[] yp = { y + 8, y + 2, y + 6, y + h - 5, y + h + 6 };
/* 3780 */           int np = yp.length;
/* 3781 */           this.tabRegion = new Polygon(xp, yp, np);
/*      */         }
/*      */       }
/*      */       else {
/* 3785 */         int[] xp = { x + w, x, x, x + w };
/* 3786 */         int[] yp = { y - 5, y + 5, y + h - 5, y + h + 6 };
/* 3787 */         int np = yp.length;
/* 3788 */         this.tabRegion = new Polygon(xp, yp, np);
/*      */       }
/* 3790 */       break;
/*      */     case 4:
/* 3792 */       if (!isSelected) {
/* 3793 */         if (((leftToRight) && (tabIndex == 0)) || ((!leftToRight) && (tabIndex == this._tabPane.getTabCount() - 1))) {
/* 3794 */           int[] xp = { x, x + w - 1, x + w - 1, x };
/* 3795 */           int[] yp = { y - 5, y + 5, y + h - 5, y + h + 6 };
/* 3796 */           int np = yp.length;
/* 3797 */           this.tabRegion = new Polygon(xp, yp, np);
/*      */         }
/*      */         else {
/* 3800 */           int[] xp = { x, x + w - 10, x + w - 1, x + w - 1, x };
/* 3801 */           int[] yp = { y + 8, y + 2, y + 6, y + h - 5, y + h + 6 };
/*      */ 
/* 3803 */           int np = yp.length;
/* 3804 */           this.tabRegion = new Polygon(xp, yp, np);
/*      */         }
/*      */       }
/*      */       else {
/* 3808 */         int[] xp = { x, x + w - 1, x + w - 1, x };
/* 3809 */         int[] yp = { y - 5, y + 5, y + h - 4, y + h + 6 };
/* 3810 */         int np = yp.length;
/* 3811 */         this.tabRegion = new Polygon(xp, yp, np);
/*      */       }
/* 3813 */       break;
/*      */     case 3:
/* 3815 */       if (!isSelected) {
/* 3816 */         if (((leftToRight) && (tabIndex == 0)) || ((!leftToRight) && (tabIndex == this._tabPane.getTabCount() - 1))) {
/* 3817 */           int[] xp = { x - 5, x + 5, x + w - 5, x + w + 5 };
/* 3818 */           int[] yp = { y, y + h - 1, y + h - 1, y };
/* 3819 */           int np = yp.length;
/* 3820 */           this.tabRegion = new Polygon(xp, yp, np);
/*      */         }
/*      */         else {
/* 3823 */           int[] xp = { x + 7, x + 1, x + 5, x + w - 5, x + w + 5 };
/* 3824 */           int[] yp = { y, y + h - 10, y + h - 1, y + h - 1, y };
/* 3825 */           int np = yp.length;
/* 3826 */           this.tabRegion = new Polygon(xp, yp, np);
/*      */         }
/*      */       }
/*      */       else {
/* 3830 */         int[] xp = { x - 5, x + 5, x + w - 5, x + w + 5 };
/* 3831 */         int[] yp = { y, y + h - 1, y + h - 1, y };
/* 3832 */         int np = yp.length;
/* 3833 */         this.tabRegion = new Polygon(xp, yp, np);
/*      */       }
/* 3835 */       break;
/*      */     case 1:
/*      */     default:
/* 3838 */       if (!isSelected) {
/* 3839 */         if (((leftToRight) && (tabIndex == 0)) || ((!leftToRight) && (tabIndex == this._tabPane.getTabCount() - 1))) {
/* 3840 */           int[] xp = { x - 6, x + 5, x + w - 5, x + w + 5 };
/* 3841 */           int[] yp = { y + h, y, y, y + h };
/* 3842 */           int np = yp.length;
/* 3843 */           this.tabRegion = new Polygon(xp, yp, np);
/*      */         }
/*      */         else {
/* 3846 */           int[] xp = { x + 7, x + 1, x + 6, x + w - 5, x + w + 5 };
/* 3847 */           int[] yp = { y + h, y + 9, y, y, y + h };
/* 3848 */           int np = yp.length;
/* 3849 */           this.tabRegion = new Polygon(xp, yp, np);
/*      */         }
/*      */       }
/*      */       else {
/* 3853 */         int[] xp = { x - 6, x + 5, x + w - 5, x + w + 5 };
/* 3854 */         int[] yp = { y + h, y, y, y + h };
/* 3855 */         int np = yp.length;
/* 3856 */         this.tabRegion = new Polygon(xp, yp, np);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintDefaultTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */   {
/* 3864 */     switch (tabPlacement) {
/*      */     case 2:
/* 3866 */       if (isSelected) {
/* 3867 */         x += 1;
/* 3868 */         int[] xp = { x + w, x, x - 2, x - 2, x + w };
/* 3869 */         int[] yp = { y - 1, y - 1, y + 1, y + h + 2, y + h + 2 };
/* 3870 */         int np = yp.length;
/* 3871 */         this.tabRegion = new Polygon(xp, yp, np);
/*      */       }
/* 3874 */       else if (tabIndex < this._tabPane.getSelectedIndex()) {
/* 3875 */         y += 1;
/* 3876 */         int[] xp = { x + w, x + 2, x, x, x + w };
/* 3877 */         int[] yp = { y + 1, y + 1, y + 3, y + h - 1, y + h - 1 };
/*      */ 
/* 3879 */         int np = yp.length;
/* 3880 */         this.tabRegion = new Polygon(xp, yp, np);
/*      */       }
/*      */       else {
/* 3883 */         int[] xp = { x + w, x + 2, x, x, x + w };
/* 3884 */         int[] yp = { y + 1, y + 1, y + 3, y + h - 2, y + h - 2 };
/*      */ 
/* 3886 */         int np = yp.length;
/* 3887 */         this.tabRegion = new Polygon(xp, yp, np);
/*      */       }
/*      */ 
/* 3890 */       break;
/*      */     case 4:
/* 3892 */       if (isSelected)
/*      */       {
/* 3894 */         int[] xp = { x, x + w - 1, x + w, x + w, x };
/* 3895 */         int[] yp = { y - 1, y - 1, y + 1, y + h + 2, y + h + 2 };
/* 3896 */         int np = yp.length;
/* 3897 */         this.tabRegion = new Polygon(xp, yp, np);
/*      */       }
/* 3900 */       else if (tabIndex < this._tabPane.getSelectedIndex()) {
/* 3901 */         y += 1;
/* 3902 */         int[] xp = { x, x + w - 3, x + w - 1, x + w - 1, x };
/* 3903 */         int[] yp = { y + 1, y + 1, y + 3, y + h - 1, y + h - 1 };
/*      */ 
/* 3905 */         int np = yp.length;
/* 3906 */         this.tabRegion = new Polygon(xp, yp, np);
/*      */       }
/*      */       else {
/* 3909 */         int[] xp = { x, x + w - 2, x + w - 1, x + w - 1, x };
/* 3910 */         int[] yp = { y + 1, y + 1, y + 3, y + h - 2, y + h - 2 };
/*      */ 
/* 3912 */         int np = yp.length;
/* 3913 */         this.tabRegion = new Polygon(xp, yp, np);
/*      */       }
/*      */ 
/* 3916 */       break;
/*      */     case 3:
/* 3918 */       if (isSelected) {
/* 3919 */         int[] xp = { x, x, x + 2, x + w + 2, x + w + 2 };
/* 3920 */         int[] yp = { y + h, y, y - 2, y - 2, y + h };
/* 3921 */         int np = yp.length;
/* 3922 */         this.tabRegion = new Polygon(xp, yp, np);
/*      */       }
/*      */       else {
/* 3925 */         int[] xp = { x + 1, x + 1, x + 1, x + w - 1, x + w - 1 };
/* 3926 */         int[] yp = { y + h - 1, y + 2, y, y, y + h - 1 };
/* 3927 */         int np = yp.length;
/* 3928 */         this.tabRegion = new Polygon(xp, yp, np);
/*      */       }
/* 3930 */       break;
/*      */     case 1:
/*      */     default:
/* 3933 */       if (isSelected) {
/* 3934 */         int[] xp = { x, x, x + 2, x + w + 2, x + w + 2 };
/* 3935 */         int[] yp = { y + h + 1, y, y - 2, y - 2, y + h + 1 };
/* 3936 */         int np = yp.length;
/* 3937 */         this.tabRegion = new Polygon(xp, yp, np);
/*      */       }
/*      */       else {
/* 3940 */         int[] xp = { x + 1, x + 1, x + 3, x + w - 1, x + w - 1 };
/* 3941 */         int[] yp = { y + h, y + 2, y, y, y + h };
/* 3942 */         int np = yp.length;
/* 3943 */         this.tabRegion = new Polygon(xp, yp, np);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintTabBackgroundMouseOver(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected, Color backgroundUnselectedColorStart, Color backgroundUnselectedColorEnd)
/*      */   {
/* 3951 */     Graphics2D g2d = (Graphics2D)g;
/*      */     Polygon polygon;
/*      */     Polygon polygon;
/* 3955 */     switch (tabPlacement)
/*      */     {
/*      */     case 2:
/*      */       Polygon polygon;
/* 3957 */       if (tabIndex < this._tabPane.getSelectedIndex())
/*      */       {
/* 3959 */         int[] xp = { x + w, x + 2, x, x, x + 2, x + w };
/* 3960 */         int[] yp = { y + 2, y + 2, y + 4, y + h - 1, y + h, y + h };
/*      */ 
/* 3962 */         int np = yp.length;
/* 3963 */         polygon = new Polygon(xp, yp, np);
/*      */       }
/*      */       else
/*      */       {
/* 3968 */         int[] xp = { x + w, x + 2, x, x, x + 2, x + w };
/* 3969 */         int[] yp = { y + 1, y + 1, y + 3, y + h - 3, y + h - 2, y + h - 2 };
/*      */ 
/* 3971 */         int np = yp.length;
/* 3972 */         polygon = new Polygon(xp, yp, np);
/*      */       }
/*      */ 
/* 3975 */       JideSwingUtilities.fillGradient(g2d, polygon, backgroundUnselectedColorStart, backgroundUnselectedColorEnd, false);
/* 3976 */       break;
/*      */     case 4:
/* 3978 */       if (tabIndex < this._tabPane.getSelectedIndex()) {
/* 3979 */         int[] xp = { x, x + w - 3, x + w - 1, x + w - 1, x + w - 3, x };
/*      */ 
/* 3981 */         int[] yp = { y + 2, y + 2, y + 4, y + h - 1, y + h, y + h };
/*      */ 
/* 3983 */         int np = yp.length;
/* 3984 */         polygon = new Polygon(xp, yp, np);
/*      */       }
/*      */       else {
/* 3987 */         int[] xp = { x, x + w - 2, x + w - 1, x + w - 1, x + w - 3, x };
/*      */ 
/* 3989 */         int[] yp = { y + 1, y + 1, y + 3, y + h - 3, y + h - 2, y + h - 2 };
/*      */ 
/* 3991 */         int np = yp.length;
/* 3992 */         polygon = new Polygon(xp, yp, np);
/*      */       }
/* 3994 */       JideSwingUtilities.fillGradient(g2d, polygon, backgroundUnselectedColorEnd, backgroundUnselectedColorStart, false);
/* 3995 */       break;
/*      */     case 3:
/* 3997 */       int[] xp = { x + 1, x + 1, x + 1, x + w - 1, x + w - 1 };
/* 3998 */       int[] yp = { y + h - 2, y + 2, y, y, y + h - 2 };
/* 3999 */       int np = yp.length;
/* 4000 */       polygon = new Polygon(xp, yp, np);
/* 4001 */       JideSwingUtilities.fillGradient(g2d, polygon, backgroundUnselectedColorEnd, backgroundUnselectedColorStart, true);
/* 4002 */       break;
/*      */     case 1:
/*      */     default:
/* 4005 */       int[] xp1 = { x + 1, x + 1, x + 3, x + w - 1, x + w - 1 };
/* 4006 */       int[] yp1 = { y + h, y + 2, y, y, y + h };
/* 4007 */       int np1 = yp1.length;
/* 4008 */       polygon = new Polygon(xp1, yp1, np1);
/* 4009 */       JideSwingUtilities.fillGradient(g2d, polygon, backgroundUnselectedColorStart, backgroundUnselectedColorEnd, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintVsnetTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */   {
/*      */     int[] xp;
/*      */     int[] yp;
/* 4018 */     switch (tabPlacement) {
/*      */     case 2:
/* 4020 */       xp = new int[] { x + 1, x + 1, x + w, x + w };
/* 4021 */       yp = new int[] { y + h - 1, y + 1, y + 1, y + h - 1 };
/* 4022 */       break;
/*      */     case 4:
/* 4024 */       xp = new int[] { x, x, x + w - 1, x + w - 1 };
/* 4025 */       yp = new int[] { y + h - 1, y + 1, y + 1, y + h - 1 };
/* 4026 */       break;
/*      */     case 3:
/* 4028 */       xp = new int[] { x + 1, x + 1, x + w - 1, x + w - 1 };
/* 4029 */       yp = new int[] { y + h - 1, y, y, y + h - 1 };
/* 4030 */       break;
/*      */     case 1:
/*      */     default:
/* 4033 */       xp = new int[] { x + 1, x + 1, x + w - 1, x + w - 1 };
/* 4034 */       yp = new int[] { y + h, y + 1, y + 1, y + h };
/*      */     }
/*      */ 
/* 4037 */     int np = yp.length;
/* 4038 */     this.tabRegion = new Polygon(xp, yp, np);
/*      */   }
/*      */ 
/*      */   protected void paintFlatTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */   {
/* 4043 */     switch (tabPlacement) {
/*      */     case 2:
/* 4045 */       int[] xp1 = { x + 1, x + 1, x + w, x + w };
/* 4046 */       int[] yp1 = { y + h, y + 1, y + 1, y + h };
/* 4047 */       int np1 = yp1.length;
/* 4048 */       this.tabRegion = new Polygon(xp1, yp1, np1);
/* 4049 */       break;
/*      */     case 4:
/* 4051 */       int[] xp2 = { x, x, x + w - 1, x + w - 1 };
/* 4052 */       int[] yp2 = { y + h, y + 1, y + 1, y + h };
/* 4053 */       int np2 = yp2.length;
/* 4054 */       this.tabRegion = new Polygon(xp2, yp2, np2);
/* 4055 */       break;
/*      */     case 3:
/* 4057 */       int[] xp3 = { x + 1, x + 1, x + w, x + w };
/* 4058 */       int[] yp3 = { y + h - 1, y, y, y + h - 1 };
/* 4059 */       int np3 = yp3.length;
/* 4060 */       this.tabRegion = new Polygon(xp3, yp3, np3);
/* 4061 */       break;
/*      */     case 1:
/*      */     default:
/* 4064 */       int[] xp4 = { x, x + 1, x + w, x + w };
/* 4065 */       int[] yp4 = { y + h, y + 1, y + 1, y + h };
/* 4066 */       int np4 = yp4.length;
/* 4067 */       this.tabRegion = new Polygon(xp4, yp4, np4);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintButtonTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */   {
/* 4074 */     int[] xp = { x, x, x + w, x + w };
/* 4075 */     int[] yp = { y + h, y, y, y + h };
/* 4076 */     int np = yp.length;
/* 4077 */     this.tabRegion = new Polygon(xp, yp, np);
/*      */   }
/*      */ 
/*      */   protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
/* 4081 */     int width = this._tabPane.getWidth();
/* 4082 */     int height = this._tabPane.getHeight();
/* 4083 */     Insets insets = this._tabPane.getInsets();
/*      */ 
/* 4085 */     int x = insets.left;
/* 4086 */     int y = insets.top;
/* 4087 */     int w = width - insets.right - insets.left;
/* 4088 */     int h = height - insets.top - insets.bottom;
/*      */ 
/* 4090 */     Dimension lsize = new Dimension(0, 0);
/* 4091 */     Dimension tsize = new Dimension(0, 0);
/*      */ 
/* 4093 */     if (isTabLeadingComponentVisible()) {
/* 4094 */       lsize = this._tabLeadingComponent.getPreferredSize();
/*      */     }
/* 4096 */     if (isTabTrailingComponentVisible()) {
/* 4097 */       tsize = this._tabTrailingComponent.getPreferredSize();
/*      */     }
/*      */ 
/* 4100 */     switch (tabPlacement) {
/*      */     case 2:
/* 4102 */       int tabAreaWidth = calculateTabAreaWidth(tabPlacement, this._runCount, this._maxTabWidth);
/* 4103 */       if ((isTabLeadingComponentVisible()) && 
/* 4104 */         (lsize.width > tabAreaWidth)) {
/* 4105 */         tabAreaWidth = lsize.width;
/*      */       }
/*      */ 
/* 4108 */       if ((isTabTrailingComponentVisible()) && 
/* 4109 */         (tsize.width > tabAreaWidth)) {
/* 4110 */         tabAreaWidth = tsize.width;
/*      */       }
/*      */ 
/* 4113 */       if (scrollableTabLayoutEnabled())
/* 4114 */         paintTabAreaBackground(g, new Rectangle(x, y, tabAreaWidth, h), tabPlacement);
/* 4115 */       x += tabAreaWidth;
/* 4116 */       w -= x - insets.left;
/* 4117 */       break;
/*      */     case 4:
/* 4119 */       int areaWidth = calculateTabAreaWidth(tabPlacement, this._runCount, this._maxTabWidth);
/* 4120 */       if (scrollableTabLayoutEnabled())
/* 4121 */         paintTabAreaBackground(g, new Rectangle(x, y, areaWidth, h), tabPlacement);
/* 4122 */       w -= areaWidth;
/* 4123 */       break;
/*      */     case 3:
/* 4125 */       int areaHeight = calculateTabAreaHeight(tabPlacement, this._runCount, this._maxTabHeight);
/* 4126 */       if (scrollableTabLayoutEnabled())
/* 4127 */         paintTabAreaBackground(g, new Rectangle(x, y, w, areaHeight), tabPlacement);
/* 4128 */       h -= areaHeight;
/* 4129 */       break;
/*      */     case 1:
/*      */     default:
/* 4132 */       int tabAreaHeight = calculateTabAreaHeight(tabPlacement, this._runCount, this._maxTabHeight);
/* 4133 */       if ((isTabLeadingComponentVisible()) && 
/* 4134 */         (lsize.height > tabAreaHeight)) {
/* 4135 */         tabAreaHeight = lsize.height;
/*      */       }
/*      */ 
/* 4138 */       if ((isTabTrailingComponentVisible()) && 
/* 4139 */         (tabAreaHeight < tsize.height)) {
/* 4140 */         tabAreaHeight = tsize.height;
/*      */       }
/*      */ 
/* 4143 */       if (scrollableTabLayoutEnabled())
/* 4144 */         paintTabAreaBackground(g, new Rectangle(x, y, w, tabAreaHeight), tabPlacement);
/* 4145 */       y += tabAreaHeight;
/* 4146 */       h -= y - insets.top;
/*      */     }
/*      */ 
/* 4149 */     if (getTabShape() != 3)
/*      */     {
/* 4152 */       paintContentBorder(g, x, y, w, h);
/*      */ 
/* 4154 */       switch (tabPlacement) {
/*      */       case 2:
/* 4156 */         paintContentBorderLeftEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/* 4157 */         break;
/*      */       case 4:
/* 4159 */         paintContentBorderRightEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/* 4160 */         break;
/*      */       case 3:
/* 4162 */         paintContentBorderBottomEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/* 4163 */         break;
/*      */       case 1:
/*      */       default:
/* 4166 */         paintContentBorderTopEdge(g, tabPlacement, selectedIndex, x, y, w, h);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void paintContentBorderRightEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void paintContentBorder(Graphics g, int x, int y, int w, int h)
/*      */   {
/* 4189 */     if (this._tabPane.isOpaque()) {
/* 4190 */       g.setColor(this._tabBackground);
/* 4191 */       g.fillRect(x, y, w, h);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected Color getBorderEdgeColor() {
/* 4196 */     if ("true".equals(SecurityUtils.getProperty("shadingtheme", "false"))) {
/* 4197 */       return this._shadow;
/*      */     }
/*      */ 
/* 4200 */     return this._lightHighlight;
/*      */   }
/*      */ 
/*      */   protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
/*      */   {
/* 4212 */     if (selectedIndex < 0) {
/* 4213 */       return;
/*      */     }
/*      */ 
/* 4216 */     if (!this._tabPane.isTabShown()) {
/* 4217 */       return;
/*      */     }
/*      */ 
/* 4220 */     Rectangle selRect = getTabBounds(selectedIndex, this._calcRect);
/*      */ 
/* 4222 */     g.setColor(getBorderEdgeColor());
/*      */ 
/* 4228 */     if ((tabPlacement != 1) || (selectedIndex < 0) || (selRect.x < x) || (selRect.x > x + w))
/*      */     {
/* 4231 */       g.drawLine(x, y, x + w - 1, y);
/*      */     }
/*      */     else
/*      */     {
/* 4235 */       g.drawLine(x, y, selRect.x, y);
/* 4236 */       if (!getBorderEdgeColor().equals(this._lightHighlight)) {
/* 4237 */         if (selRect.x + selRect.width < x + w - 2) {
/* 4238 */           g.drawLine(selRect.x + selRect.width - 1, y, selRect.x + selRect.width - 1, y);
/*      */ 
/* 4240 */           g.drawLine(selRect.x + selRect.width, y, x + w - 1, y);
/*      */         }
/*      */         else {
/* 4243 */           g.drawLine(x + w - 2, y, x + w - 1, y);
/*      */         }
/*      */ 
/*      */       }
/* 4247 */       else if (selRect.x + selRect.width < x + w - 2) {
/* 4248 */         g.setColor(this._darkShadow);
/* 4249 */         g.drawLine(selRect.x + selRect.width - 1, y, selRect.x + selRect.width - 1, y);
/*      */ 
/* 4251 */         g.setColor(this._lightHighlight);
/* 4252 */         g.drawLine(selRect.x + selRect.width, y, x + w - 1, y);
/*      */       }
/*      */       else {
/* 4255 */         g.setColor(this._selectedColor == null ? this._tabPane.getBackground() : this._selectedColor);
/*      */ 
/* 4257 */         g.drawLine(x + w - 2, y, x + w - 1, y);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
/*      */   {
/* 4270 */     if (selectedIndex < 0) {
/* 4271 */       return;
/*      */     }
/*      */ 
/* 4274 */     if (!this._tabPane.isTabShown()) {
/* 4275 */       return;
/*      */     }
/*      */ 
/* 4278 */     Rectangle selRect = getTabBounds(selectedIndex, this._calcRect);
/*      */ 
/* 4284 */     if ((tabPlacement != 3) || (selectedIndex < 0) || (selRect.x < x) || (selRect.x > x + w))
/*      */     {
/* 4287 */       g.setColor(getBorderEdgeColor());
/* 4288 */       g.drawLine(x, y + h - 1, x + w - 2, y + h - 1);
/*      */     }
/* 4291 */     else if (!getBorderEdgeColor().equals(this._lightHighlight)) {
/* 4292 */       g.setColor(getBorderEdgeColor());
/* 4293 */       g.drawLine(x, y + h - 1, selRect.x - 1, y + h - 1);
/* 4294 */       g.drawLine(selRect.x, y + h - 1, selRect.x, y + h - 1);
/* 4295 */       if (selRect.x + selRect.width < x + w - 2) {
/* 4296 */         g.drawLine(selRect.x + selRect.width - 1, y + h - 1, x + w - 2, y + h - 1);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 4301 */       g.setColor(this._darkShadow);
/* 4302 */       g.drawLine(x, y + h - 1, selRect.x - 1, y + h - 1);
/* 4303 */       g.setColor(this._lightHighlight);
/*      */ 
/* 4305 */       g.drawLine(selRect.x, y + h - 1, selRect.x, y + h - 1);
/* 4306 */       if (selRect.x + selRect.width < x + w - 2) {
/* 4307 */         g.setColor(this._darkShadow);
/* 4308 */         g.drawLine(selRect.x + selRect.width - 1, y + h - 1, x + w - 2, y + h - 1);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void ensureCurrentLayout()
/*      */   {
/* 4322 */     if (!this._tabPane.isValid()) {
/* 4323 */       TabbedPaneLayout layout = (TabbedPaneLayout)this._tabPane.getLayout();
/* 4324 */       layout.calculateLayoutInfo();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void updateCloseButtons() {
/* 4329 */     boolean leftToRight = this._tabPane.getComponentOrientation().isLeftToRight();
/* 4330 */     if ((scrollableTabLayoutEnabled()) && (isShowCloseButton()) && (isShowCloseButtonOnTab()))
/* 4331 */       for (int i = 0; i < this._closeButtons.length; i++) {
/* 4332 */         if (this._tabPane.isShowCloseButtonOnSelectedTab()) {
/* 4333 */           if (i != this._tabPane.getSelectedIndex()) {
/* 4334 */             this._closeButtons[i].setBounds(0, 0, 0, 0);
/* 4335 */             continue;
/*      */           }
/*      */ 
/*      */         }
/* 4339 */         else if (i >= this._rects.length) {
/* 4340 */           this._closeButtons[i].setBounds(0, 0, 0, 0);
/* 4341 */           continue;
/*      */         }
/*      */ 
/* 4345 */         if (!this._tabPane.isTabClosableAt(i)) {
/* 4346 */           this._closeButtons[i].setBounds(0, 0, 0, 0);
/*      */         }
/*      */         else {
/* 4349 */           Dimension size = this._closeButtons[i].getPreferredSize();
/*      */           Rectangle bounds;
/* 4352 */           if (this._closeButtonAlignment == 11)
/*      */           {
/*      */             Rectangle bounds;
/* 4353 */             if ((this._tabPane.getTabPlacement() == 1) || (this._tabPane.getTabPlacement() == 3)) {
/* 4354 */               if (leftToRight) {
/* 4355 */                 Rectangle bounds = new Rectangle(this._rects[i].x + this._rects[i].width - size.width - this._closeButtonRightMargin, this._rects[i].y + (this._rects[i].height - size.height) / 2, size.width, size.height);
/*      */ 
/* 4357 */                 bounds.x -= getTabGap();
/*      */               }
/*      */               else {
/* 4360 */                 bounds = new Rectangle(this._rects[i].x + this._closeButtonLeftMargin + getTabGap(), this._rects[i].y + (this._rects[i].height - size.height) / 2, size.width, size.height);
/*      */               }
/*      */             }
/*      */             else {
/* 4364 */               Rectangle bounds = new Rectangle(this._rects[i].x + (this._rects[i].width - size.width) / 2, this._rects[i].y + this._rects[i].height - size.height - this._closeButtonRightMargin, size.width, size.height);
/* 4365 */               bounds.y -= getTabGap();
/*      */             }
/*      */ 
/*      */           }
/* 4369 */           else if ((this._tabPane.getTabPlacement() == 1) || (this._tabPane.getTabPlacement() == 3))
/*      */           {
/*      */             Rectangle bounds;
/* 4370 */             if (leftToRight) {
/* 4371 */               bounds = new Rectangle(this._rects[i].x + this._closeButtonLeftMargin + getTabGap(), this._rects[i].y + (this._rects[i].height - size.height) / 2, size.width, size.height);
/*      */             }
/*      */             else {
/* 4374 */               Rectangle bounds = new Rectangle(this._rects[i].x + this._rects[i].width - size.width - this._closeButtonRightMargin, this._rects[i].y + (this._rects[i].height - size.height) / 2, size.width, size.height);
/*      */ 
/* 4376 */               bounds.x -= getTabGap();
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*      */             Rectangle bounds;
/* 4379 */             if (this._tabPane.getTabPlacement() == 2) {
/* 4380 */               bounds = new Rectangle(this._rects[i].x + (this._rects[i].width - size.width) / 2, this._rects[i].y + this._closeButtonLeftMargin, size.width, size.height);
/*      */             }
/*      */             else {
/* 4383 */               bounds = new Rectangle(this._rects[i].x + (this._rects[i].width - size.width) / 2 - 2, this._rects[i].y + this._closeButtonLeftMargin, size.width, size.height);
/*      */             }
/*      */           }
/* 4386 */           if ((this._closeButtons[i] instanceof JideTabbedPane.NoFocusButton)) {
/* 4387 */             ((JideTabbedPane.NoFocusButton)this._closeButtons[i]).setIndex(i);
/*      */           }
/* 4389 */           if (!bounds.equals(this._closeButtons[i].getBounds())) {
/* 4390 */             this._closeButtons[i].setBounds(bounds);
/*      */           }
/* 4392 */           if (this._tabPane.getSelectedIndex() == i) {
/* 4393 */             this._closeButtons[i].setBackground(this._selectedColor == null ? this._tabPane.getBackgroundAt(i) : this._selectedColor);
/*      */           }
/*      */           else
/* 4396 */             this._closeButtons[i].setBackground(this._tabPane.getBackgroundAt(i));
/*      */         }
/*      */       }
/*      */   }
/*      */ 
/*      */   public Rectangle getTabBounds(JTabbedPane pane, int i)
/*      */   {
/* 4410 */     ensureCurrentLayout();
/* 4411 */     Rectangle tabRect = new Rectangle();
/* 4412 */     return getTabBounds(i, tabRect);
/*      */   }
/*      */ 
/*      */   public int getTabRunCount(JTabbedPane pane)
/*      */   {
/* 4417 */     ensureCurrentLayout();
/* 4418 */     return this._runCount;
/*      */   }
/*      */ 
/*      */   public int tabForCoordinate(JTabbedPane pane, int x, int y)
/*      */   {
/* 4426 */     ensureCurrentLayout();
/* 4427 */     Point p = new Point(x, y);
/*      */ 
/* 4429 */     if (scrollableTabLayoutEnabled()) {
/* 4430 */       translatePointToTabPanel(x, y, p);
/*      */     }
/* 4432 */     int tabCount = this._tabPane.getTabCount();
/* 4433 */     boolean horizontalTab = (this._tabPane.getTabPlacement() == 1) || (this._tabPane.getTabPlacement() == 3);
/* 4434 */     boolean isRTL = (horizontalTab) && (!this._tabPane.getComponentOrientation().isLeftToRight());
/* 4435 */     if (isRTL) {
/* 4436 */       p.x -= this._tabScroller.viewport.getExpectedViewX();
/*      */     }
/*      */ 
/* 4439 */     int firstButtonPos = isRTL ? 0 : 2147483647;
/* 4440 */     Component[] components = this._tabPane.getComponents();
/* 4441 */     for (Component component : components) {
/* 4442 */       if (((component instanceof JideTabbedPane.NoFocusButton)) && (component.isVisible())) {
/* 4443 */         Rectangle bounds = component.getBounds();
/* 4444 */         if ((horizontalTab) && (bounds.x != 0)) {
/* 4445 */           if (isRTL) {
/* 4446 */             firstButtonPos = Math.max(firstButtonPos, bounds.x);
/*      */           }
/*      */           else {
/* 4449 */             firstButtonPos = Math.min(firstButtonPos, bounds.x);
/*      */           }
/*      */         }
/* 4452 */         else if ((!horizontalTab) && (bounds.y != 0)) {
/* 4453 */           firstButtonPos = Math.min(firstButtonPos, bounds.y);
/*      */         }
/*      */       }
/*      */     }
/* 4457 */     if ((this._tabPane.getTabTrailingComponent() != null) && (this._tabPane.getTabTrailingComponent().isVisible())) {
/* 4458 */       Rectangle bounds = this._tabPane.getTabTrailingComponent().getBounds();
/* 4459 */       if ((horizontalTab) && (bounds.x != 0)) {
/* 4460 */         if (isRTL) {
/* 4461 */           firstButtonPos = Math.max(firstButtonPos, bounds.x);
/*      */         }
/*      */         else {
/* 4464 */           firstButtonPos = Math.min(firstButtonPos, bounds.x);
/*      */         }
/*      */       }
/* 4467 */       else if ((!horizontalTab) && (bounds.y != 0)) {
/* 4468 */         firstButtonPos = Math.min(firstButtonPos, bounds.y);
/*      */       }
/*      */     }
/* 4471 */     if (horizontalTab) {
/* 4472 */       if (((isRTL) && (x <= firstButtonPos)) || ((!isRTL) && (x >= firstButtonPos))) {
/* 4473 */         return -1;
/*      */       }
/*      */ 
/*      */     }
/* 4477 */     else if (y >= firstButtonPos) {
/* 4478 */       return -1;
/*      */     }
/*      */ 
/* 4482 */     for (int i = 0; i < tabCount; i++) {
/* 4483 */       if (this._rects[i].contains(p.x, p.y)) {
/* 4484 */         return i;
/*      */       }
/*      */     }
/* 4487 */     return -1;
/*      */   }
/*      */ 
/*      */   protected Rectangle getTabBounds(int tabIndex, Rectangle dest)
/*      */   {
/* 4505 */     if (this._rects.length == 0) {
/* 4506 */       return null;
/*      */     }
/*      */ 
/* 4509 */     if (tabIndex > this._rects.length - 1) {
/* 4510 */       tabIndex = this._rects.length - 1;
/*      */     }
/* 4512 */     if (tabIndex < 0) {
/* 4513 */       tabIndex = 0;
/*      */     }
/*      */ 
/* 4516 */     dest.width = this._rects[tabIndex].width;
/* 4517 */     dest.height = this._rects[tabIndex].height;
/*      */ 
/* 4519 */     if (scrollableTabLayoutEnabled())
/*      */     {
/* 4522 */       int tabPlacement = this._tabPane.getTabPlacement();
/* 4523 */       Point vpp = this._tabScroller.viewport.getLocation();
/* 4524 */       Point viewp = this._tabScroller.viewport.getViewPosition();
/* 4525 */       dest.x = (this._rects[tabIndex].x + vpp.x - (((tabPlacement == 1) || (tabPlacement == 3)) && (!this._tabPane.getComponentOrientation().isLeftToRight()) ? -this._tabScroller.viewport.getExpectedViewX() : viewp.x));
/* 4526 */       dest.y = (this._rects[tabIndex].y + vpp.y - viewp.y);
/*      */     }
/*      */     else
/*      */     {
/* 4530 */       dest.x = this._rects[tabIndex].x;
/* 4531 */       dest.y = this._rects[tabIndex].y;
/*      */     }
/* 4533 */     return dest;
/*      */   }
/*      */ 
/*      */   public int getTabAtLocation(int x, int y)
/*      */   {
/* 4546 */     ensureCurrentLayout();
/*      */ 
/* 4548 */     int tabCount = this._tabPane.getTabCount();
/* 4549 */     for (int i = 0; i < tabCount; i++) {
/* 4550 */       if (this._rects[i].contains(x, y)) {
/* 4551 */         return i;
/*      */       }
/*      */     }
/* 4554 */     return -1;
/*      */   }
/*      */ 
/*      */   public boolean isEmptyTabArea(int x, int y)
/*      */   {
/* 4566 */     int tabCount = this._tabPane.getTabCount();
/* 4567 */     if ((getTabAtLocation(x, y) >= 0) || (tabCount <= 0)) {
/* 4568 */       return false;
/*      */     }
/* 4570 */     int tabPlacement = this._tabPane.getTabPlacement();
/* 4571 */     if ((tabPlacement == 1) || (tabPlacement == 3)) {
/* 4572 */       if (this._rects[0].contains(this._rects[0].x + 1, y)) {
/* 4573 */         return true;
/*      */       }
/*      */ 
/*      */     }
/* 4577 */     else if (this._rects[0].contains(x, this._rects[0].y + 1)) {
/* 4578 */       return true;
/*      */     }
/*      */ 
/* 4581 */     return false;
/*      */   }
/*      */ 
/*      */   private int getClosestTab(int x, int y)
/*      */   {
/* 4590 */     int min = 0;
/* 4591 */     int tabCount = Math.min(this._rects.length, this._tabPane.getTabCount());
/* 4592 */     int max = tabCount;
/* 4593 */     int tabPlacement = this._tabPane.getTabPlacement();
/* 4594 */     boolean useX = (tabPlacement == 1) || (tabPlacement == 3);
/* 4595 */     int want = useX ? x : y;
/* 4596 */     if (!this._tabPane.getComponentOrientation().isLeftToRight()) {
/* 4597 */       want = x - this._tabScroller.viewport.getExpectedViewX() - 1;
/*      */     }
/* 4599 */     Rectangle[] rects = new Rectangle[this._rects.length];
/* 4600 */     boolean needConvert = false;
/* 4601 */     if ((!useX) || (this._tabPane.getComponentOrientation().isLeftToRight())) {
/* 4602 */       System.arraycopy(this._rects, 0, rects, 0, this._rects.length);
/*      */     }
/*      */     else {
/* 4605 */       if (x == this._tabScroller.viewport.getViewRect().width) {
/* 4606 */         return this._tabScroller.leadingTabIndex;
/*      */       }
/* 4608 */       needConvert = true;
/* 4609 */       for (int i = 0; i < this._rects.length; i++) {
/* 4610 */         rects[i] = this._rects[(this._rects.length - 1 - i)];
/*      */       }
/*      */     }
/*      */ 
/* 4614 */     while (min != max) {
/* 4615 */       int current = max + min >> 1;
/*      */       int maxLoc;
/*      */       int minLoc;
/*      */       int maxLoc;
/* 4619 */       if (useX) {
/* 4620 */         int minLoc = rects[current].x;
/* 4621 */         maxLoc = minLoc + rects[current].width;
/*      */       }
/*      */       else {
/* 4624 */         minLoc = rects[current].y;
/* 4625 */         maxLoc = minLoc + rects[current].height;
/*      */       }
/* 4627 */       if (want < minLoc) {
/* 4628 */         max = current;
/* 4629 */         if (min == max) {
/* 4630 */           int tabIndex = Math.max(0, current - 1);
/* 4631 */           return needConvert ? rects.length - 1 - tabIndex : tabIndex;
/*      */         }
/*      */       }
/* 4634 */       else if (want >= maxLoc) {
/* 4635 */         min = current;
/* 4636 */         if (max - min <= 1) {
/* 4637 */           int tabIndex = Math.max(current + 1, tabCount - 1);
/* 4638 */           return needConvert ? rects.length - 1 - tabIndex : tabIndex;
/*      */         }
/*      */       }
/*      */       else {
/* 4642 */         return needConvert ? rects.length - 1 - current : current;
/*      */       }
/*      */     }
/* 4645 */     return needConvert ? rects.length - 1 - min : min;
/*      */   }
/*      */ 
/*      */   private Point translatePointToTabPanel(int srcx, int srcy, Point dest)
/*      */   {
/* 4654 */     Point vpp = this._tabScroller.viewport.getLocation();
/* 4655 */     Point viewp = this._tabScroller.viewport.getViewPosition();
/* 4656 */     dest.x = (srcx - vpp.x + viewp.x);
/* 4657 */     dest.y = (srcy - vpp.y + viewp.y);
/* 4658 */     return dest;
/*      */   }
/*      */ 
/*      */   protected Component getVisibleComponent()
/*      */   {
/* 4664 */     return this.visibleComponent;
/*      */   }
/*      */ 
/*      */   protected void setVisibleComponent(Component component) {
/* 4668 */     if ((this.visibleComponent != null) && (this.visibleComponent != component) && (this.visibleComponent.getParent() == this._tabPane))
/*      */     {
/* 4670 */       this.visibleComponent.setVisible(false);
/*      */     }
/* 4672 */     if ((component != null) && (!component.isVisible())) {
/* 4673 */       component.setVisible(true);
/*      */     }
/* 4675 */     this.visibleComponent = component;
/*      */   }
/*      */ 
/*      */   protected void assureRectsCreated(int tabCount) {
/* 4679 */     int rectArrayLen = this._rects.length;
/* 4680 */     if (tabCount != rectArrayLen) {
/* 4681 */       Rectangle[] tempRectArray = new Rectangle[tabCount];
/* 4682 */       System.arraycopy(this._rects, 0, tempRectArray, 0, Math.min(rectArrayLen, tabCount));
/*      */ 
/* 4684 */       this._rects = tempRectArray;
/* 4685 */       for (int rectIndex = rectArrayLen; rectIndex < tabCount; rectIndex++)
/* 4686 */         this._rects[rectIndex] = new Rectangle();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void expandTabRunsArray()
/*      */   {
/* 4693 */     int rectLen = this._tabRuns.length;
/* 4694 */     int[] newArray = new int[rectLen + 10];
/* 4695 */     System.arraycopy(this._tabRuns, 0, newArray, 0, this._runCount);
/* 4696 */     this._tabRuns = newArray;
/*      */   }
/*      */ 
/*      */   protected int getRunForTab(int tabCount, int tabIndex) {
/* 4700 */     for (int i = 0; i < this._runCount; i++) {
/* 4701 */       int first = this._tabRuns[i];
/* 4702 */       int last = lastTabInRun(tabCount, i);
/* 4703 */       if ((tabIndex >= first) && (tabIndex <= last)) {
/* 4704 */         return i;
/*      */       }
/*      */     }
/* 4707 */     return 0;
/*      */   }
/*      */ 
/*      */   protected int lastTabInRun(int tabCount, int run) {
/* 4711 */     if (this._runCount == 1) {
/* 4712 */       return tabCount - 1;
/*      */     }
/* 4714 */     int nextRun = run == this._runCount - 1 ? 0 : run + 1;
/* 4715 */     if (this._tabRuns[nextRun] == 0) {
/* 4716 */       return tabCount - 1;
/*      */     }
/* 4718 */     return this._tabRuns[nextRun] - 1;
/*      */   }
/*      */ 
/*      */   protected int getTabRunOverlay(int tabPlacement)
/*      */   {
/* 4723 */     return this._tabRunOverlay;
/*      */   }
/*      */ 
/*      */   protected int getTabRunIndent(int tabPlacement, int run)
/*      */   {
/* 4728 */     return 0;
/*      */   }
/*      */ 
/*      */   protected boolean shouldPadTabRun(int tabPlacement, int run)
/*      */   {
/* 4733 */     return this._runCount > 1;
/*      */   }
/*      */ 
/*      */   protected boolean shouldRotateTabRuns(int tabPlacement)
/*      */   {
/* 4738 */     return true;
/*      */   }
/*      */ 
/*      */   protected View getTextViewForTab(int tabIndex)
/*      */   {
/* 4749 */     if ((this.htmlViews != null) && (tabIndex < this.htmlViews.size())) {
/* 4750 */       return (View)this.htmlViews.elementAt(tabIndex);
/*      */     }
/* 4752 */     return null;
/*      */   }
/*      */ 
/*      */   protected int calculateTabHeight(int tabPlacement, int tabIndex, FontMetrics metrics) {
/* 4756 */     int height = 0;
/* 4757 */     if ((tabPlacement == 1) || (tabPlacement == 3)) {
/* 4758 */       View v = getTextViewForTab(tabIndex);
/* 4759 */       if (v != null)
/*      */       {
/* 4761 */         height += (int)v.getPreferredSpan(1);
/*      */       }
/*      */       else
/*      */       {
/* 4765 */         height += metrics.getHeight();
/*      */       }
/* 4767 */       Icon icon = this._tabPane.getIconForTab(tabIndex);
/* 4768 */       Insets tabInsets = getTabInsets(tabPlacement, tabIndex);
/*      */ 
/* 4770 */       if (icon != null) {
/* 4771 */         height = Math.max(height, icon.getIconHeight());
/*      */       }
/* 4773 */       height += tabInsets.top + tabInsets.bottom + 2;
/*      */     }
/*      */     else {
/* 4776 */       Icon icon = this._tabPane.getIconForTab(tabIndex);
/* 4777 */       Insets tabInsets = getTabInsets(tabPlacement, tabIndex);
/* 4778 */       height = tabInsets.top + tabInsets.bottom + 3;
/*      */ 
/* 4780 */       if (icon != null) {
/* 4781 */         height += icon.getIconHeight() + this._textIconGap;
/*      */       }
/* 4783 */       View v = getTextViewForTab(tabIndex);
/* 4784 */       if (v != null)
/*      */       {
/* 4786 */         height += (int)v.getPreferredSpan(0);
/*      */       }
/*      */       else
/*      */       {
/* 4790 */         String title = getCurrentDisplayTitleAt(this._tabPane, tabIndex);
/* 4791 */         height += SwingUtilities.computeStringWidth(metrics, title);
/*      */       }
/*      */ 
/* 4795 */       if (this._tabPane.isShowGripper()) {
/* 4796 */         height += this._gripperHeight;
/*      */       }
/*      */ 
/* 4799 */       if ((scrollableTabLayoutEnabled()) && (isShowCloseButton()) && (isShowCloseButtonOnTab()) && (this._tabPane.isTabClosableAt(tabIndex))) {
/* 4800 */         if (this._tabPane.isShowCloseButtonOnSelectedTab()) {
/* 4801 */           if (this._tabPane.getSelectedIndex() == tabIndex) {
/* 4802 */             height += this._closeButtons[tabIndex].getPreferredSize().height + this._closeButtonRightMargin + this._closeButtonLeftMargin;
/*      */           }
/*      */         }
/*      */         else {
/* 4806 */           height += this._closeButtons[tabIndex].getPreferredSize().height + this._closeButtonRightMargin + this._closeButtonLeftMargin;
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 4812 */     return height;
/*      */   }
/*      */ 
/*      */   protected int calculateMaxTabHeight(int tabPlacement) {
/* 4816 */     int tabCount = this._tabPane.getTabCount();
/* 4817 */     int result = 0;
/* 4818 */     for (int i = 0; i < tabCount; i++) {
/* 4819 */       FontMetrics metrics = getFontMetrics(i);
/* 4820 */       result = Math.max(calculateTabHeight(tabPlacement, i, metrics), result);
/*      */     }
/* 4822 */     return result;
/*      */   }
/*      */ 
/*      */   protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
/* 4826 */     int width = 0;
/* 4827 */     if ((tabPlacement == 1) || (tabPlacement == 3)) {
/* 4828 */       Icon icon = this._tabPane.getIconForTab(tabIndex);
/* 4829 */       Insets tabInsets = getTabInsets(tabPlacement, tabIndex);
/* 4830 */       width = tabInsets.left + tabInsets.right + 3 + getTabGap();
/*      */ 
/* 4832 */       if (icon != null) {
/* 4833 */         width += icon.getIconWidth() + this._textIconGap;
/*      */       }
/* 4835 */       View v = getTextViewForTab(tabIndex);
/* 4836 */       if (v != null)
/*      */       {
/* 4838 */         width += (int)v.getPreferredSpan(0);
/*      */       }
/*      */       else
/*      */       {
/* 4842 */         String title = getCurrentDisplayTitleAt(this._tabPane, tabIndex);
/*      */ 
/* 4845 */         width += SwingUtilities.computeStringWidth(metrics, title);
/*      */       }
/*      */ 
/* 4849 */       if (this._tabPane.isShowGripper()) {
/* 4850 */         width += this._gripperWidth;
/*      */       }
/*      */ 
/* 4853 */       if ((scrollableTabLayoutEnabled()) && (isShowCloseButton()) && (isShowCloseButtonOnTab()) && (this._tabPane.isTabClosableAt(tabIndex))) {
/* 4854 */         if (this._tabPane.isShowCloseButtonOnSelectedTab()) {
/* 4855 */           if (this._tabPane.getSelectedIndex() == tabIndex) {
/* 4856 */             width += this._closeButtons[tabIndex].getPreferredSize().width + this._closeButtonRightMargin + this._closeButtonLeftMargin;
/*      */           }
/*      */         }
/*      */         else {
/* 4860 */           width += this._closeButtons[tabIndex].getPreferredSize().width + this._closeButtonRightMargin + this._closeButtonLeftMargin;
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 4867 */       View v = getTextViewForTab(tabIndex);
/* 4868 */       if (v != null)
/*      */       {
/* 4870 */         width += (int)v.getPreferredSpan(1);
/*      */       }
/*      */       else
/*      */       {
/* 4874 */         width += metrics.getHeight();
/*      */       }
/* 4876 */       Icon icon = this._tabPane.getIconForTab(tabIndex);
/* 4877 */       Insets tabInsets = getTabInsets(tabPlacement, tabIndex);
/*      */ 
/* 4879 */       if (icon != null) {
/* 4880 */         width = Math.max(width, icon.getIconWidth());
/*      */       }
/* 4882 */       width += tabInsets.left + tabInsets.right + 2;
/*      */     }
/* 4884 */     return width;
/*      */   }
/*      */ 
/*      */   protected int calculateMaxTabWidth(int tabPlacement) {
/* 4888 */     int tabCount = this._tabPane.getTabCount();
/* 4889 */     int result = 0;
/* 4890 */     for (int i = 0; i < tabCount; i++) {
/* 4891 */       FontMetrics metrics = getFontMetrics(i);
/* 4892 */       result = Math.max(calculateTabWidth(tabPlacement, i, metrics), result);
/*      */     }
/* 4894 */     return result;
/*      */   }
/*      */ 
/*      */   protected int calculateTabAreaHeight(int tabPlacement, int horizRunCount, int maxTabHeight) {
/* 4898 */     if (!this._tabPane.isTabShown()) {
/* 4899 */       return 0;
/*      */     }
/* 4901 */     Insets tabAreaInsets = getTabAreaInsets(tabPlacement);
/* 4902 */     int tabRunOverlay = getTabRunOverlay(tabPlacement);
/* 4903 */     return horizRunCount > 0 ? horizRunCount * (maxTabHeight - tabRunOverlay) + tabRunOverlay + tabAreaInsets.top + tabAreaInsets.bottom : 0;
/*      */   }
/*      */ 
/*      */   protected int calculateTabAreaWidth(int tabPlacement, int vertRunCount, int maxTabWidth) {
/* 4907 */     if (!this._tabPane.isTabShown()) {
/* 4908 */       return 0;
/*      */     }
/* 4910 */     Insets tabAreaInsets = getTabAreaInsets(tabPlacement);
/* 4911 */     int tabRunOverlay = getTabRunOverlay(tabPlacement);
/* 4912 */     return vertRunCount > 0 ? vertRunCount * (maxTabWidth - tabRunOverlay) + tabRunOverlay + tabAreaInsets.left + tabAreaInsets.right : 0;
/*      */   }
/*      */ 
/*      */   protected Insets getTabInsets(int tabPlacement, int tabIndex)
/*      */   {
/* 4917 */     rotateInsets(this._tabPane.getTabInsets(), this._currentTabInsets, tabPlacement);
/* 4918 */     return this._currentTabInsets;
/*      */   }
/*      */ 
/*      */   protected Insets getSelectedTabPadInsets(int tabPlacement) {
/* 4922 */     rotateInsets(this._selectedTabPadInsets, this._currentPadInsets, tabPlacement);
/* 4923 */     return this._currentPadInsets;
/*      */   }
/*      */ 
/*      */   protected Insets getTabAreaInsets(int tabPlacement) {
/* 4927 */     rotateInsets(this._tabPane.getTabAreaInsets(), this._currentTabAreaInsets, tabPlacement);
/* 4928 */     return this._currentTabAreaInsets;
/*      */   }
/*      */ 
/*      */   protected Insets getContentBorderInsets(int tabPlacement) {
/* 4932 */     rotateInsets(this._tabPane.getContentBorderInsets(), this._currentContentBorderInsets, tabPlacement);
/* 4933 */     if ((this._ignoreContentBorderInsetsIfNoTabs) && (!this._tabPane.isTabShown())) {
/* 4934 */       return new Insets(0, 0, 0, 0);
/*      */     }
/* 4936 */     return this._currentContentBorderInsets;
/*      */   }
/*      */ 
/*      */   protected FontMetrics getFontMetrics(int tab)
/*      */   {
/* 4942 */     int selectedIndex = this._tabPane.getSelectedIndex();
/*      */     Font font;
/*      */     Font font;
/* 4943 */     if ((selectedIndex == tab) && (this._tabPane.getSelectedTabFont() != null)) {
/* 4944 */       font = this._tabPane.getSelectedTabFont();
/*      */     }
/*      */     else {
/* 4947 */       font = this._tabPane.getFont();
/*      */     }
/*      */ 
/* 4950 */     if ((selectedIndex == tab) && (this._tabPane.isBoldActiveTab()) && (font.getStyle() != 1)) {
/* 4951 */       font = font.deriveFont(1);
/*      */     }
/* 4953 */     return this._tabPane.getFontMetrics(font);
/*      */   }
/*      */ 
/*      */   protected void navigateSelectedTab(int direction)
/*      */   {
/* 4959 */     int tabPlacement = this._tabPane.getTabPlacement();
/* 4960 */     int current = this._tabPane.getSelectedIndex();
/* 4961 */     int tabCount = this._tabPane.getTabCount();
/*      */ 
/* 4963 */     boolean leftToRight = this._tabPane.getComponentOrientation().isLeftToRight();
/*      */ 
/* 4966 */     if (tabCount <= 0)
/* 4967 */       return;
/*      */     int offset;
/* 4971 */     switch (tabPlacement) {
/*      */     case 12:
/* 4973 */       selectNextTab(current);
/* 4974 */       break;
/*      */     case 13:
/* 4976 */       selectPreviousTab(current);
/* 4977 */       break;
/*      */     case 2:
/*      */     case 4:
/* 4980 */       switch (direction) {
/*      */       case 1:
/* 4982 */         selectPreviousTabInRun(current);
/* 4983 */         break;
/*      */       case 5:
/* 4985 */         selectNextTabInRun(current);
/* 4986 */         break;
/*      */       case 7:
/* 4988 */         offset = getTabRunOffset(tabPlacement, tabCount, current, false);
/* 4989 */         selectAdjacentRunTab(tabPlacement, current, offset);
/* 4990 */         break;
/*      */       case 3:
/* 4992 */         offset = getTabRunOffset(tabPlacement, tabCount, current, true);
/* 4993 */         selectAdjacentRunTab(tabPlacement, current, offset);
/*      */       case 2:
/*      */       case 4:
/*      */       case 6:
/* 4997 */       }break;
/*      */     case 1:
/*      */     case 3:
/*      */     case 5:
/*      */     case 6:
/*      */     case 7:
/*      */     case 8:
/*      */     case 9:
/*      */     case 10:
/*      */     case 11:
/*      */     default:
/* 5001 */       switch (direction) {
/*      */       case 1:
/* 5003 */         offset = getTabRunOffset(tabPlacement, tabCount, current, false);
/* 5004 */         selectAdjacentRunTab(tabPlacement, current, offset);
/* 5005 */         break;
/*      */       case 5:
/* 5007 */         offset = getTabRunOffset(tabPlacement, tabCount, current, true);
/* 5008 */         selectAdjacentRunTab(tabPlacement, current, offset);
/* 5009 */         break;
/*      */       case 3:
/* 5011 */         if (leftToRight) {
/* 5012 */           selectNextTabInRun(current);
/*      */         }
/*      */         else {
/* 5015 */           selectPreviousTabInRun(current);
/*      */         }
/* 5017 */         break;
/*      */       case 7:
/* 5019 */         if (leftToRight) {
/* 5020 */           selectPreviousTabInRun(current);
/*      */         }
/*      */         else
/* 5023 */           selectNextTabInRun(current); case 2:
/*      */       case 4:
/* 5025 */       case 6: } break;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void selectNextTabInRun(int current)
/*      */   {
/* 5032 */     int tabCount = this._tabPane.getTabCount();
/* 5033 */     int tabIndex = getNextTabIndexInRun(tabCount, current);
/*      */ 
/* 5035 */     while ((tabIndex != current) && (!this._tabPane.isEnabledAt(tabIndex))) {
/* 5036 */       tabIndex = getNextTabIndexInRun(tabCount, tabIndex);
/*      */     }
/* 5038 */     this._tabPane.setSelectedIndex(tabIndex);
/*      */   }
/*      */ 
/*      */   protected void selectPreviousTabInRun(int current) {
/* 5042 */     int tabCount = this._tabPane.getTabCount();
/* 5043 */     int tabIndex = getPreviousTabIndexInRun(tabCount, current);
/*      */ 
/* 5045 */     while ((tabIndex != current) && (!this._tabPane.isEnabledAt(tabIndex))) {
/* 5046 */       tabIndex = getPreviousTabIndexInRun(tabCount, tabIndex);
/*      */     }
/* 5048 */     this._tabPane.setSelectedIndex(tabIndex);
/*      */   }
/*      */ 
/*      */   protected void selectNextTab(int current) {
/* 5052 */     int tabIndex = getNextTabIndex(current);
/*      */ 
/* 5054 */     while ((tabIndex != current) && (!this._tabPane.isEnabledAt(tabIndex))) {
/* 5055 */       tabIndex = getNextTabIndex(tabIndex);
/*      */     }
/* 5057 */     this._tabPane.setSelectedIndex(tabIndex);
/*      */   }
/*      */ 
/*      */   protected void selectPreviousTab(int current) {
/* 5061 */     int tabIndex = getPreviousTabIndex(current);
/*      */ 
/* 5063 */     while ((tabIndex != current) && (!this._tabPane.isEnabledAt(tabIndex))) {
/* 5064 */       tabIndex = getPreviousTabIndex(tabIndex);
/*      */     }
/* 5066 */     this._tabPane.setSelectedIndex(tabIndex);
/*      */   }
/*      */ 
/*      */   protected void selectAdjacentRunTab(int tabPlacement, int tabIndex, int offset)
/*      */   {
/* 5071 */     if (this._runCount < 2) {
/* 5072 */       return;
/*      */     }
/*      */ 
/* 5075 */     Rectangle r = this._rects[tabIndex];
/*      */     int newIndex;
/* 5076 */     switch (tabPlacement) {
/*      */     case 2:
/*      */     case 4:
/* 5079 */       newIndex = getTabAtLocation(r.x + (r.width >> 1) + offset, r.y + (r.height >> 1));
/*      */ 
/* 5081 */       break;
/*      */     case 1:
/*      */     case 3:
/*      */     default:
/* 5085 */       newIndex = getTabAtLocation(r.x + (r.width >> 1), r.y + (r.height >> 1) + offset);
/*      */     }
/*      */ 
/* 5088 */     if (newIndex != -1) {
/* 5089 */       while ((!this._tabPane.isEnabledAt(newIndex)) && (newIndex != tabIndex)) {
/* 5090 */         newIndex = getNextTabIndex(newIndex);
/*      */       }
/* 5092 */       this._tabPane.setSelectedIndex(newIndex);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected int getTabRunOffset(int tabPlacement, int tabCount, int tabIndex, boolean forward)
/*      */   {
/* 5098 */     int run = getRunForTab(tabCount, tabIndex);
/*      */     int offset;
/*      */     int offset;
/*      */     int offset;
/*      */     int offset;
/* 5100 */     switch (tabPlacement)
/*      */     {
/*      */     case 2:
/*      */       int offset;
/* 5102 */       if (run == 0) {
/* 5103 */         offset = forward ? -(calculateTabAreaWidth(tabPlacement, this._runCount, this._maxTabWidth) - this._maxTabWidth) : -this._maxTabWidth;
/*      */       }
/*      */       else
/*      */       {
/*      */         int offset;
/* 5108 */         if (run == this._runCount - 1) {
/* 5109 */           offset = forward ? this._maxTabWidth : calculateTabAreaWidth(tabPlacement, this._runCount, this._maxTabWidth) - this._maxTabWidth;
/*      */         }
/*      */         else
/*      */         {
/* 5114 */           offset = forward ? this._maxTabWidth : -this._maxTabWidth;
/*      */         }
/*      */       }
/* 5116 */       break;
/*      */     case 4:
/* 5119 */       if (run == 0) {
/* 5120 */         offset = forward ? this._maxTabWidth : calculateTabAreaWidth(tabPlacement, this._runCount, this._maxTabWidth) - this._maxTabWidth;
/*      */       }
/*      */       else
/*      */       {
/*      */         int offset;
/* 5124 */         if (run == this._runCount - 1) {
/* 5125 */           offset = forward ? -(calculateTabAreaWidth(tabPlacement, this._runCount, this._maxTabWidth) - this._maxTabWidth) : -this._maxTabWidth;
/*      */         }
/*      */         else
/*      */         {
/* 5130 */           offset = forward ? this._maxTabWidth : -this._maxTabWidth;
/*      */         }
/*      */       }
/* 5132 */       break;
/*      */     case 3:
/* 5135 */       if (run == 0) {
/* 5136 */         offset = forward ? this._maxTabHeight : calculateTabAreaHeight(tabPlacement, this._runCount, this._maxTabHeight) - this._maxTabHeight;
/*      */       }
/*      */       else
/*      */       {
/*      */         int offset;
/* 5140 */         if (run == this._runCount - 1) {
/* 5141 */           offset = forward ? -(calculateTabAreaHeight(tabPlacement, this._runCount, this._maxTabHeight) - this._maxTabHeight) : -this._maxTabHeight;
/*      */         }
/*      */         else
/*      */         {
/* 5146 */           offset = forward ? this._maxTabHeight : -this._maxTabHeight;
/*      */         }
/*      */       }
/* 5148 */       break;
/*      */     case 1:
/*      */     default:
/* 5152 */       if (run == 0) {
/* 5153 */         offset = forward ? -(calculateTabAreaHeight(tabPlacement, this._runCount, this._maxTabHeight) - this._maxTabHeight) : -this._maxTabHeight;
/*      */       }
/*      */       else
/*      */       {
/*      */         int offset;
/* 5157 */         if (run == this._runCount - 1) {
/* 5158 */           offset = forward ? this._maxTabHeight : calculateTabAreaHeight(tabPlacement, this._runCount, this._maxTabHeight) - this._maxTabHeight;
/*      */         }
/*      */         else
/*      */         {
/* 5163 */           offset = forward ? this._maxTabHeight : -this._maxTabHeight;
/*      */         }
/*      */       }
/*      */     }
/* 5167 */     return offset;
/*      */   }
/*      */ 
/*      */   protected int getPreviousTabIndex(int base) {
/* 5171 */     int tabIndex = base - 1 >= 0 ? base - 1 : this._tabPane.getTabCount() - 1;
/* 5172 */     return tabIndex >= 0 ? tabIndex : 0;
/*      */   }
/*      */ 
/*      */   protected int getNextTabIndex(int base) {
/* 5176 */     return (base + 1) % this._tabPane.getTabCount();
/*      */   }
/*      */ 
/*      */   protected int getNextTabIndexInRun(int tabCount, int base) {
/* 5180 */     if (this._runCount < 2) {
/* 5181 */       return getNextTabIndex(base);
/*      */     }
/* 5183 */     int currentRun = getRunForTab(tabCount, base);
/* 5184 */     int next = getNextTabIndex(base);
/* 5185 */     if (next == this._tabRuns[getNextTabRun(currentRun)]) {
/* 5186 */       return this._tabRuns[currentRun];
/*      */     }
/* 5188 */     return next;
/*      */   }
/*      */ 
/*      */   protected int getPreviousTabIndexInRun(int tabCount, int base) {
/* 5192 */     if (this._runCount < 2) {
/* 5193 */       return getPreviousTabIndex(base);
/*      */     }
/* 5195 */     int currentRun = getRunForTab(tabCount, base);
/* 5196 */     if (base == this._tabRuns[currentRun]) {
/* 5197 */       int previous = this._tabRuns[getNextTabRun(currentRun)] - 1;
/* 5198 */       return previous != -1 ? previous : tabCount - 1;
/*      */     }
/* 5200 */     return getPreviousTabIndex(base);
/*      */   }
/*      */ 
/*      */   protected int getPreviousTabRun(int baseRun) {
/* 5204 */     int runIndex = baseRun - 1 >= 0 ? baseRun - 1 : this._runCount - 1;
/* 5205 */     return runIndex >= 0 ? runIndex : 0;
/*      */   }
/*      */ 
/*      */   protected int getNextTabRun(int baseRun) {
/* 5209 */     return (baseRun + 1) % this._runCount;
/*      */   }
/*      */ 
/*      */   public static void rotateInsets(Insets topInsets, Insets targetInsets, int targetPlacement) {
/* 5213 */     if (topInsets == null) {
/* 5214 */       targetInsets.top = 0;
/* 5215 */       targetInsets.left = 0;
/* 5216 */       targetInsets.bottom = 0;
/* 5217 */       targetInsets.right = 0;
/* 5218 */       return;
/*      */     }
/*      */ 
/* 5221 */     switch (targetPlacement) {
/*      */     case 2:
/* 5223 */       targetInsets.top = topInsets.left;
/* 5224 */       targetInsets.left = topInsets.top;
/* 5225 */       targetInsets.bottom = topInsets.right;
/* 5226 */       targetInsets.right = topInsets.bottom;
/* 5227 */       break;
/*      */     case 3:
/* 5229 */       targetInsets.top = topInsets.bottom;
/* 5230 */       targetInsets.left = topInsets.left;
/* 5231 */       targetInsets.bottom = topInsets.top;
/* 5232 */       targetInsets.right = topInsets.right;
/* 5233 */       break;
/*      */     case 4:
/* 5235 */       targetInsets.top = topInsets.left;
/* 5236 */       targetInsets.left = topInsets.bottom;
/* 5237 */       targetInsets.bottom = topInsets.right;
/* 5238 */       targetInsets.right = topInsets.top;
/* 5239 */       break;
/*      */     case 1:
/*      */     default:
/* 5242 */       targetInsets.top = topInsets.top;
/* 5243 */       targetInsets.left = topInsets.left;
/* 5244 */       targetInsets.bottom = topInsets.bottom;
/* 5245 */       targetInsets.right = topInsets.right;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected boolean requestFocusForVisibleComponent() {
/* 5250 */     Component visibleComponent = getVisibleComponent();
/* 5251 */     Component lastFocused = this._tabPane.getLastFocusedComponent(visibleComponent);
/* 5252 */     if ((lastFocused != null) && (lastFocused.requestFocusInWindow())) {
/* 5253 */       return true;
/*      */     }
/* 5255 */     if ((visibleComponent != null) && (JideSwingUtilities.passesFocusabilityTest(visibleComponent))) {
/* 5256 */       JideSwingUtilities.compositeRequestFocus(visibleComponent);
/* 5257 */       return true;
/*      */     }
/*      */ 
/* 5261 */     return ((visibleComponent instanceof JComponent)) && 
/* 5260 */       (((JComponent)visibleComponent).requestDefaultFocus());
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   protected TabCloseButton createNoFocusButton(int type)
/*      */   {
/* 5418 */     return new TabCloseButton(type);
/*      */   }
/*      */ 
/*      */   private JButton createButton(int type) {
/* 5422 */     return this._tabPane.createNoFocusButton(type);
/*      */   }
/*      */ 
/*      */   protected void stopOrCancelEditing()
/*      */   {
/* 5517 */     boolean isEditValid = true;
/* 5518 */     if ((this._tabPane != null) && (this._tabPane.isTabEditing()) && (this._tabPane.getTabEditingValidator() != null)) {
/* 5519 */       isEditValid = this._tabPane.getTabEditingValidator().isValid(this._editingTab, this._oldPrefix + this._tabEditor.getText() + this._oldPostfix);
/*      */     }
/* 5521 */     if (isEditValid)
/* 5522 */       this._tabPane.stopTabEditing();
/*      */     else
/* 5524 */       this._tabPane.cancelTabEditing();
/*      */   }
/*      */ 
/*      */   protected void ensureCurrentRects(int leftMargin, int tabCount)
/*      */   {
/* 7091 */     Dimension size = this._tabPane.getSize();
/* 7092 */     Insets insets = this._tabPane.getInsets();
/* 7093 */     int totalWidth = 0;
/* 7094 */     int totalHeight = 0;
/* 7095 */     boolean verticalTabRuns = (this._tabPane.getTabPlacement() == 2) || (this._tabPane.getTabPlacement() == 4);
/* 7096 */     boolean ltr = this._tabPane.getComponentOrientation().isLeftToRight();
/*      */ 
/* 7098 */     if (tabCount == 0) {
/* 7099 */       return;
/*      */     }
/*      */ 
/* 7102 */     Rectangle r = this._rects[(tabCount - 1)];
/*      */ 
/* 7104 */     Dimension lsize = new Dimension(0, 0);
/* 7105 */     Dimension tsize = new Dimension(0, 0);
/*      */ 
/* 7107 */     if (isTabLeadingComponentVisible()) {
/* 7108 */       lsize = this._tabLeadingComponent.getPreferredSize();
/*      */     }
/* 7110 */     if (isTabTrailingComponentVisible()) {
/* 7111 */       tsize = this._tabTrailingComponent.getPreferredSize();
/*      */     }
/*      */ 
/* 7115 */     if (verticalTabRuns) {
/* 7116 */       totalHeight = r.y + r.height;
/*      */ 
/* 7118 */       if (this._tabLeadingComponent != null) {
/* 7119 */         totalHeight -= lsize.height;
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 7125 */       for (Rectangle rect : this._rects) {
/* 7126 */         totalWidth += rect.width;
/*      */       }
/* 7128 */       if (ltr) {
/* 7129 */         totalWidth += this._rects[0].x;
/*      */       }
/*      */       else {
/* 7132 */         totalWidth += size.width - this._rects[0].x - this._rects[0].width - this._tabScroller.viewport.getLocation().x;
/*      */       }
/*      */ 
/* 7135 */       if (this._tabLeadingComponent != null) {
/* 7136 */         totalWidth -= lsize.width;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 7142 */     if (getTabResizeMode() == 2) {
/* 7143 */       if (verticalTabRuns)
/*      */       {
/*      */         int availHeight;
/*      */         int availHeight;
/* 7145 */         if (getTabShape() != 3) {
/* 7146 */           availHeight = (int)size.getHeight() - this._fitStyleBoundSize - insets.top - insets.bottom - leftMargin - getTabRightPadding();
/*      */         }
/*      */         else
/*      */         {
/* 7150 */           availHeight = (int)size.getHeight() - this._fitStyleBoundSize - insets.top - insets.bottom;
/*      */         }
/*      */ 
/* 7154 */         if (this._tabPane.isShowCloseButton()) {
/* 7155 */           availHeight -= this._buttonSize;
/*      */         }
/*      */ 
/* 7158 */         if (isTabLeadingComponentVisible()) {
/* 7159 */           availHeight -= lsize.height;
/*      */         }
/* 7161 */         if (isTabTrailingComponentVisible()) {
/* 7162 */           availHeight -= tsize.height;
/*      */         }
/*      */ 
/* 7165 */         int numberOfButtons = getNumberOfTabButtons();
/* 7166 */         availHeight -= this._buttonSize * numberOfButtons;
/* 7167 */         if (totalHeight > availHeight)
/*      */         {
/* 7169 */           int tabHeight = availHeight / tabCount;
/*      */ 
/* 7171 */           totalHeight = this._fitStyleFirstTabMargin;
/*      */ 
/* 7173 */           for (int k = 0; k < tabCount; k++) {
/* 7174 */             this._rects[k].height = tabHeight;
/* 7175 */             Rectangle tabRect = this._rects[k];
/* 7176 */             if (getTabShape() != 3) {
/* 7177 */               tabRect.y = (totalHeight + leftMargin);
/*      */             }
/*      */             else {
/* 7180 */               tabRect.y = totalHeight;
/*      */             }
/* 7182 */             totalHeight += tabRect.height;
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*      */         int availWidth;
/*      */         int availWidth;
/* 7189 */         if (getTabShape() != 3) {
/* 7190 */           availWidth = (int)size.getWidth() - this._fitStyleBoundSize - insets.left - insets.right - leftMargin - getTabRightPadding();
/*      */         }
/*      */         else
/*      */         {
/* 7194 */           availWidth = (int)size.getWidth() - this._fitStyleBoundSize - insets.left - insets.right;
/*      */         }
/*      */ 
/* 7198 */         if (this._tabPane.isShowCloseButton()) {
/* 7199 */           availWidth -= this._buttonSize;
/*      */         }
/*      */ 
/* 7202 */         if (isTabLeadingComponentVisible()) {
/* 7203 */           availWidth -= lsize.width;
/*      */         }
/* 7205 */         if (isTabTrailingComponentVisible()) {
/* 7206 */           availWidth -= tsize.width;
/*      */         }
/*      */ 
/* 7209 */         int numberOfButtons = getNumberOfTabButtons();
/* 7210 */         availWidth -= this._buttonSize * numberOfButtons;
/* 7211 */         if (totalWidth > availWidth)
/*      */         {
/* 7213 */           int tabWidth = availWidth / tabCount;
/* 7214 */           int gripperWidth = this._tabPane.isShowGripper() ? this._gripperWidth : 0;
/*      */ 
/* 7216 */           if ((tabWidth < this._textIconGap + this._fitStyleTextMinWidth + this._fitStyleIconMinWidth + gripperWidth) && (tabWidth > this._fitStyleIconMinWidth + gripperWidth))
/*      */           {
/* 7220 */             tabWidth = this._fitStyleIconMinWidth + gripperWidth;
/*      */           }
/* 7222 */           if ((tabWidth < this._fitStyleIconMinWidth + gripperWidth) && (tabWidth > this._fitStyleFirstTabMargin + gripperWidth))
/*      */           {
/* 7225 */             tabWidth = this._fitStyleFirstTabMargin + gripperWidth;
/*      */           }
/* 7227 */           this.tryTabSpacer.reArrange(this._rects, insets, availWidth);
/*      */         }
/* 7229 */         totalWidth = this._fitStyleFirstTabMargin;
/*      */ 
/* 7231 */         for (int k = 0; k < tabCount; k++) {
/* 7232 */           Rectangle tabRect = this._rects[k];
/* 7233 */           if (getTabShape() != 3) {
/* 7234 */             if (ltr) {
/* 7235 */               tabRect.x = (totalWidth + leftMargin);
/*      */             }
/*      */             else {
/* 7238 */               tabRect.x = (availWidth - totalWidth - tabRect.width + leftMargin);
/*      */             }
/*      */ 
/*      */           }
/* 7242 */           else if (ltr) {
/* 7243 */             tabRect.x = totalWidth;
/*      */           }
/*      */           else {
/* 7246 */             tabRect.x = (availWidth - totalWidth - tabRect.width);
/*      */           }
/*      */ 
/* 7249 */           totalWidth += tabRect.width;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 7254 */     if (getTabResizeMode() == 3) {
/* 7255 */       if (verticalTabRuns) {
/* 7256 */         for (int k = 0; k < tabCount; k++) {
/* 7257 */           this._rects[k].height = this._fixedStyleRectSize;
/*      */ 
/* 7259 */           if ((isShowCloseButton()) && (this._tabPane.isShowCloseButtonOnTab())) {
/* 7260 */             this._rects[k].height += this._closeButtons[k].getPreferredSize().height;
/*      */           }
/*      */ 
/* 7263 */           if (k != 0) {
/* 7264 */             this._rects[k].y = (this._rects[(k - 1)].y + this._rects[(k - 1)].height);
/*      */           }
/*      */ 
/* 7267 */           totalHeight = this._rects[k].y + this._rects[k].height;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 7272 */         for (int k = 0; k < tabCount; k++) {
/* 7273 */           int oldWidth = this._rects[k].width;
/* 7274 */           this._rects[k].width = this._fixedStyleRectSize;
/*      */ 
/* 7276 */           if ((isShowCloseButton()) && (this._tabPane.isShowCloseButtonOnTab())) {
/* 7277 */             this._rects[k].width += this._closeButtons[k].getPreferredSize().width;
/*      */           }
/*      */ 
/* 7280 */           if ((k == 0) && (!ltr)) {
/* 7281 */             this._rects[k].x += oldWidth - this._rects[k].width;
/*      */           }
/*      */ 
/* 7284 */           if (k != 0) {
/* 7285 */             if (ltr) {
/* 7286 */               this._rects[k].x = (this._rects[(k - 1)].x + this._rects[(k - 1)].width);
/*      */             }
/*      */             else {
/* 7289 */               this._rects[k].x = (this._rects[(k - 1)].x - this._rects[(k - 1)].width);
/*      */             }
/*      */           }
/*      */ 
/* 7293 */           totalWidth = this._rects[k].x + this._rects[k].width;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 7298 */     if (getTabResizeMode() == 4) {
/* 7299 */       if (verticalTabRuns) {
/* 7300 */         for (int k = 0; k < tabCount; k++) {
/* 7301 */           if (k != this._tabPane.getSelectedIndex()) {
/* 7302 */             if ((!this._tabPane.isShowIconsOnTab()) && (!this._tabPane.isUseDefaultShowIconsOnTab())) {
/* 7303 */               this._rects[k].height = this._compressedStyleNoIconRectSize;
/*      */             }
/*      */             else {
/* 7306 */               Icon icon = this._tabPane.getIconForTab(k);
/* 7307 */               this._rects[k].height = (icon.getIconHeight() + this._compressedStyleIconMargin);
/*      */             }
/*      */ 
/* 7310 */             if ((isShowCloseButton()) && (isShowCloseButtonOnTab()) && (!this._tabPane.isShowCloseButtonOnSelectedTab())) {
/* 7311 */               this._rects[k].height = (this._rects[k].height + this._closeButtons[k].getPreferredSize().height + this._compressedStyleCloseButtonMarginVertical);
/*      */             }
/*      */           }
/*      */ 
/* 7315 */           if (k != 0) {
/* 7316 */             this._rects[k].y = (this._rects[(k - 1)].y + this._rects[(k - 1)].height);
/*      */           }
/*      */ 
/* 7319 */           totalHeight = this._rects[k].y + this._rects[k].height;
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 7325 */         for (int k = 0; k < tabCount; k++) {
/* 7326 */           int oldWidth = this._rects[k].width;
/* 7327 */           if (k != this._tabPane.getSelectedIndex()) {
/* 7328 */             if ((!this._tabPane.isShowIconsOnTab()) && (!this._tabPane.isUseDefaultShowIconsOnTab()))
/*      */             {
/* 7330 */               this._rects[k].width = this._compressedStyleNoIconRectSize;
/*      */             }
/*      */             else {
/* 7333 */               Icon icon = this._tabPane.getIconForTab(k);
/* 7334 */               this._rects[k].width = (icon.getIconWidth() + this._compressedStyleIconMargin);
/*      */             }
/*      */ 
/* 7337 */             if ((isShowCloseButton()) && (isShowCloseButtonOnTab()) && (!this._tabPane.isShowCloseButtonOnSelectedTab())) {
/* 7338 */               this._rects[k].width = (this._rects[k].width + this._closeButtons[k].getPreferredSize().width + this._compressedStyleCloseButtonMarginHorizon);
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 7343 */           if ((k == 0) && (!ltr)) {
/* 7344 */             this._rects[k].x += oldWidth - this._rects[k].width;
/*      */           }
/*      */ 
/* 7347 */           if (k != 0) {
/* 7348 */             if (ltr) {
/* 7349 */               this._rects[k].x = (this._rects[(k - 1)].x + this._rects[(k - 1)].width);
/*      */             }
/*      */             else {
/* 7352 */               this._rects[k].x = (this._rects[(k - 1)].x - this._rects[(k - 1)].width);
/*      */             }
/*      */           }
/*      */ 
/* 7356 */           totalWidth = this._rects[k].x + this._rects[k].width;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 7363 */     if ((this._tabPane.getTabPlacement() == 1) || (this._tabPane.getTabPlacement() == 3)) {
/* 7364 */       totalWidth += getLayoutSize();
/*      */ 
/* 7366 */       if (isTabLeadingComponentVisible())
/* 7367 */         totalWidth += lsize.width;
/*      */     }
/*      */     else
/*      */     {
/* 7371 */       totalHeight += getLayoutSize();
/*      */ 
/* 7373 */       if (isTabLeadingComponentVisible()) {
/* 7374 */         totalHeight += tsize.height;
/*      */       }
/*      */     }
/*      */ 
/* 7378 */     if ((this._tabPane.getTabAlignment() == 0) && (
/* 7379 */       (this._tabPane.getTabPlacement() == 1) || (this._tabPane.getTabPlacement() == 3))) {
/* 7380 */       int startX = this._rects[0].x;
/* 7381 */       int endX = this._rects[(this._rects.length - 1)].x + this._rects[(this._rects.length - 1)].width;
/* 7382 */       int width = this._tabPane.getWidth();
/* 7383 */       int offset = width / 2 - (endX - startX) / 2 - startX;
/* 7384 */       for (Rectangle rect : this._rects) {
/* 7385 */         rect.x += offset;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 7390 */     this._tabScroller.tabPanel.setPreferredSize(new Dimension(totalWidth, totalHeight));
/*      */   }
/*      */ 
/*      */   protected ListCellRenderer getTabListCellRenderer()
/*      */   {
/* 7409 */     return this._tabPane.getTabListCellRenderer();
/*      */   }
/*      */ 
/*      */   protected void updateCloseAction()
/*      */   {
/* 8542 */     ensureCloseButtonCreated();
/*      */   }
/*      */ 
/*      */   private Vector createHTMLVector()
/*      */   {
/* 8814 */     Vector htmlViews = new Vector();
/* 8815 */     int count = this._tabPane.getTabCount();
/* 8816 */     if (count > 0) {
/* 8817 */       for (int i = 0; i < count; i++) {
/* 8818 */         String title = getCurrentDisplayTitleAt(this._tabPane, i);
/* 8819 */         if (BasicHTML.isHTMLString(title)) {
/* 8820 */           htmlViews.addElement(BasicHTML.createHTMLView(this._tabPane, title));
/*      */         }
/*      */         else {
/* 8823 */           htmlViews.addElement(null);
/*      */         }
/*      */       }
/*      */     }
/* 8827 */     return htmlViews;
/*      */   }
/*      */ 
/*      */   public Component getTabPanel()
/*      */   {
/* 8832 */     if (scrollableTabLayoutEnabled()) {
/* 8833 */       return this._tabScroller.tabPanel;
/*      */     }
/* 8835 */     return this._tabPane;
/*      */   }
/*      */ 
/*      */   public void ensureActiveTabIsVisible(boolean scrollLeft)
/*      */   {
/* 8959 */     if ((this._tabPane == null) || (this._tabPane.getWidth() == 0)) {
/* 8960 */       return;
/*      */     }
/*      */ 
/* 8963 */     if (scrollableTabLayoutEnabled()) {
/* 8964 */       ensureCurrentLayout();
/* 8965 */       if ((scrollLeft) && (this._rects.length > 0)) {
/* 8966 */         if ((this._tabPane.getTabPlacement() == 2) || (this._tabPane.getTabPlacement() == 4) || (this._tabPane.getComponentOrientation().isLeftToRight())) {
/* 8967 */           this._tabScroller.viewport.setViewPosition(new Point(0, 0));
/* 8968 */           this._tabScroller.tabPanel.scrollRectToVisible(this._rects[0]);
/*      */         }
/*      */         else {
/* 8971 */           this._tabScroller.viewport.setViewPosition(new Point(0, 0));
/*      */         }
/*      */       }
/* 8974 */       int index = this._tabPane.getSelectedIndex();
/* 8975 */       if (((!scrollLeft) || (index != 0)) && (index < this._rects.length) && (index != -1)) {
/* 8976 */         if (index == 0) {
/* 8977 */           this._tabScroller.viewport.setViewPosition(new Point(0, 0));
/*      */         }
/* 8980 */         else if (index == this._rects.length - 1) {
/* 8981 */           Rectangle lastRect = this._rects[index];
/* 8982 */           if (((this._tabPane.getTabPlacement() == 1) || (this._tabPane.getTabPlacement() == 3)) && (this._tabPane.getComponentOrientation().isLeftToRight())) {
/* 8983 */             lastRect.width = (this._tabScroller.tabPanel.getWidth() - lastRect.x);
/*      */           }
/* 8985 */           this._tabScroller.tabPanel.scrollRectToVisible(lastRect);
/*      */         }
/*      */         else {
/* 8988 */           this._tabScroller.tabPanel.scrollRectToVisible(this._rects[index]);
/*      */         }
/*      */ 
/* 8991 */         if ((this._tabPane.getTabPlacement() == 2) || (this._tabPane.getTabPlacement() == 4) || (this._tabPane.getComponentOrientation().isLeftToRight())) {
/* 8992 */           this._tabScroller.tabPanel.getParent().doLayout();
/*      */         }
/*      */       }
/* 8995 */       if ((this._tabPane.getTabPlacement() == 2) || (this._tabPane.getTabPlacement() == 4) || (this._tabPane.getComponentOrientation().isLeftToRight())) {
/* 8996 */         this._tabPane.revalidate();
/* 8997 */         this._tabPane.repaintTabAreaAndContentBorder();
/*      */       }
/*      */       else {
/* 9000 */         this._tabPane.repaint();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected boolean isShowCloseButtonOnTab() {
/* 9006 */     if (this._tabPane.isUseDefaultShowCloseButtonOnTab()) {
/* 9007 */       return this._showCloseButtonOnTab;
/*      */     }
/* 9009 */     return this._tabPane.isShowCloseButtonOnTab();
/*      */   }
/*      */ 
/*      */   protected boolean isShowCloseButton() {
/* 9013 */     return this._tabPane.isShowCloseButton();
/*      */   }
/*      */ 
/*      */   public void ensureCloseButtonCreated() {
/* 9017 */     if ((isShowCloseButton()) && (isShowCloseButtonOnTab()) && (scrollableTabLayoutEnabled())) {
/* 9018 */       if (this._closeButtons == null) {
/* 9019 */         this._closeButtons = new JButton[this._tabPane.getTabCount()];
/*      */       }
/* 9021 */       else if (this._closeButtons.length > this._tabPane.getTabCount()) {
/* 9022 */         JButton[] temp = new JButton[this._tabPane.getTabCount()];
/* 9023 */         System.arraycopy(this._closeButtons, 0, temp, 0, temp.length);
/* 9024 */         for (int i = temp.length; i < this._closeButtons.length; i++) {
/* 9025 */           JButton tabCloseButton = this._closeButtons[i];
/* 9026 */           this._tabScroller.tabPanel.remove(tabCloseButton);
/*      */         }
/* 9028 */         this._closeButtons = temp;
/*      */       }
/* 9030 */       else if (this._closeButtons.length < this._tabPane.getTabCount()) {
/* 9031 */         JButton[] temp = new JButton[this._tabPane.getTabCount()];
/* 9032 */         System.arraycopy(this._closeButtons, 0, temp, 0, this._closeButtons.length);
/* 9033 */         this._closeButtons = temp;
/*      */       }
/* 9035 */       ActionMap am = getActionMap();
/* 9036 */       for (int i = 0; i < this._closeButtons.length; i++) {
/* 9037 */         JButton closeButton = this._closeButtons[i];
/* 9038 */         if (closeButton == null) {
/* 9039 */           closeButton = createButton(0);
/* 9040 */           closeButton.setName("JideTabbedPane.close");
/* 9041 */           this._closeButtons[i] = closeButton;
/* 9042 */           closeButton.setBounds(0, 0, 0, 0);
/* 9043 */           Action action = this._tabPane.getCloseAction();
/* 9044 */           Icon closeIcon = closeButton.getIcon();
/* 9045 */           closeButton.setAction(am.get("closeTabAction"));
/* 9046 */           updateButtonFromAction(closeButton, action);
/* 9047 */           if (closeIcon != null) {
/* 9048 */             this._tabScroller.closeButton.setIcon(closeIcon);
/*      */           }
/* 9050 */           this._tabScroller.tabPanel.add(closeButton);
/*      */         }
/* 9052 */         if ((closeButton instanceof JideTabbedPane.NoFocusButton))
/* 9053 */           ((JideTabbedPane.NoFocusButton)closeButton).setIndex(i);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void updateButtonFromAction(JButton closeButton, Action action)
/*      */   {
/* 9060 */     if (action == null) {
/* 9061 */       return;
/*      */     }
/* 9063 */     closeButton.setEnabled(action.isEnabled());
/* 9064 */     Object desc = action.getValue("ShortDescription");
/* 9065 */     if ((desc instanceof String)) {
/* 9066 */       closeButton.setToolTipText((String)desc);
/*      */     }
/* 9068 */     Object icon = action.getValue("SmallIcon");
/* 9069 */     if ((icon instanceof Icon))
/* 9070 */       closeButton.setIcon((Icon)icon);
/*      */   }
/*      */ 
/*      */   protected boolean isShowTabButtons()
/*      */   {
/* 9075 */     return (this._tabPane.getTabCount() != 0) && (this._tabPane.isShowTabArea()) && (this._tabPane.isShowTabButtons());
/*      */   }
/*      */ 
/*      */   protected boolean isShrinkTabs() {
/* 9079 */     return (this._tabPane.getTabCount() != 0) && (this._tabPane.getTabResizeMode() == 2);
/*      */   }
/*      */ 
/*      */   public boolean isTabEditing()
/*      */   {
/* 9098 */     return this._isEditing;
/*      */   }
/*      */ 
/*      */   protected TabEditor createDefaultTabEditor() {
/* 9102 */     TabEditor editor = new TabEditor();
/* 9103 */     editor.getDocument().addDocumentListener(this);
/* 9104 */     editor.setInputVerifier(new InputVerifier()
/*      */     {
/*      */       public boolean verify(JComponent input) {
/* 9107 */         return true;
/*      */       }
/*      */ 
/*      */       public boolean shouldYieldFocus(JComponent input) {
/* 9111 */         boolean shouldStopEditing = true;
/* 9112 */         if ((BasicJideTabbedPaneUI.this._tabPane != null) && (BasicJideTabbedPaneUI.this._tabPane.isTabEditing()) && (BasicJideTabbedPaneUI.this._tabPane.getTabEditingValidator() != null)) {
/* 9113 */           shouldStopEditing = BasicJideTabbedPaneUI.this._tabPane.getTabEditingValidator().alertIfInvalid(BasicJideTabbedPaneUI.this._editingTab, BasicJideTabbedPaneUI.this._oldPrefix + BasicJideTabbedPaneUI.this._tabEditor.getText() + BasicJideTabbedPaneUI.this._oldPostfix);
/*      */         }
/*      */ 
/* 9116 */         if ((shouldStopEditing) && (BasicJideTabbedPaneUI.this._tabPane != null) && (BasicJideTabbedPaneUI.this._tabPane.isTabEditing())) {
/* 9117 */           BasicJideTabbedPaneUI.this._tabPane.stopTabEditing();
/*      */         }
/*      */ 
/* 9120 */         return shouldStopEditing;
/*      */       }
/*      */     });
/* 9123 */     editor.addFocusListener(new FocusAdapter()
/*      */     {
/*      */       public void focusGained(FocusEvent e) {
/* 9126 */         BasicJideTabbedPaneUI.this._originalFocusComponent = e.getOppositeComponent();
/*      */       }
/*      */ 
/*      */       public void focusLost(FocusEvent e)
/*      */       {
/*      */       }
/*      */     });
/* 9133 */     editor.addActionListener(new ActionListener(editor) {
/*      */       public void actionPerformed(ActionEvent e) {
/* 9135 */         this.val$editor.transferFocus();
/*      */       }
/*      */     });
/* 9138 */     editor.addKeyListener(new KeyAdapter()
/*      */     {
/*      */       public void keyPressed(KeyEvent e) {
/* 9141 */         if ((BasicJideTabbedPaneUI.this._isEditing) && (e.getKeyCode() == 27)) {
/* 9142 */           if ((BasicJideTabbedPaneUI.this._editingTab >= 0) && (BasicJideTabbedPaneUI.this._editingTab < BasicJideTabbedPaneUI.this._tabPane.getTabCount())) {
/* 9143 */             BasicJideTabbedPaneUI.this._tabPane.setTitleAt(BasicJideTabbedPaneUI.this._editingTab, BasicJideTabbedPaneUI.this._oldValue);
/*      */           }
/* 9145 */           BasicJideTabbedPaneUI.this._tabPane.cancelTabEditing();
/*      */         }
/*      */       }
/*      */     });
/* 9149 */     editor.setFont(this._tabPane.getFont());
/*      */ 
/* 9151 */     return editor;
/*      */   }
/*      */ 
/*      */   public void stopTabEditing()
/*      */   {
/* 9156 */     if ((this._editingTab >= 0) && (this._editingTab < this._tabPane.getTabCount())) {
/* 9157 */       this._tabPane.setTitleAt(this._editingTab, this._oldPrefix + this._tabEditor.getText() + this._oldPostfix);
/*      */     }
/* 9159 */     cancelTabEditing();
/*      */   }
/*      */ 
/*      */   public void cancelTabEditing()
/*      */   {
/* 9164 */     if (this._tabEditor != null) {
/* 9165 */       this._isEditing = false;
/*      */ 
/* 9167 */       ((Container)getTabPanel()).remove(this._tabEditor);
/*      */ 
/* 9169 */       if ((this._editingTab >= 0) && (this._editingTab < this._tabPane.getTabCount())) {
/* 9170 */         Rectangle tabRect = this._tabPane.getBoundsAt(this._editingTab);
/* 9171 */         getTabPanel().repaint(tabRect.x, tabRect.y, tabRect.width, tabRect.height);
/*      */       }
/*      */       else
/*      */       {
/* 9175 */         getTabPanel().repaint();
/*      */       }
/*      */ 
/* 9178 */       if (this._originalFocusComponent != null)
/* 9179 */         this._originalFocusComponent.requestFocus();
/*      */       else {
/* 9181 */         this._tabPane.requestFocusForVisibleComponent();
/*      */       }
/* 9183 */       this._editingTab = -1;
/* 9184 */       this._oldValue = null;
/* 9185 */       this._tabPane.doLayout();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean editTabAt(int tabIndex)
/*      */   {
/* 9191 */     if (this._isEditing) {
/* 9192 */       return false;
/*      */     }
/*      */ 
/* 9196 */     if (this._tabEditor == null) {
/* 9197 */       this._tabEditor = createDefaultTabEditor();
/*      */     }
/* 9199 */     if (this._tabEditor != null) {
/* 9200 */       prepareEditor(this._tabEditor, tabIndex);
/*      */ 
/* 9202 */       ((Container)getTabPanel()).add(this._tabEditor);
/* 9203 */       resizeEditor(tabIndex);
/*      */ 
/* 9205 */       this._editingTab = tabIndex;
/* 9206 */       this._isEditing = true;
/*      */ 
/* 9208 */       this._tabEditor.requestFocusInWindow();
/* 9209 */       this._tabEditor.selectAll();
/* 9210 */       return true;
/*      */     }
/* 9212 */     return false;
/*      */   }
/*      */ 
/*      */   public int getEditingTabIndex()
/*      */   {
/* 9217 */     return this._editingTab;
/*      */   }
/*      */ 
/*      */   protected void prepareEditor(TabEditor e, int tabIndex)
/*      */   {
/*      */     Font font;
/*      */     Font font;
/* 9222 */     if (this._tabPane.getSelectedTabFont() != null) {
/* 9223 */       font = this._tabPane.getSelectedTabFont();
/*      */     }
/*      */     else {
/* 9226 */       font = this._tabPane.getFont();
/*      */     }
/* 9228 */     if ((this._tabPane.isBoldActiveTab()) && (font.getStyle() != 1)) {
/* 9229 */       font = font.deriveFont(1);
/*      */     }
/* 9231 */     e.setFont(font);
/*      */ 
/* 9233 */     this._oldValue = this._tabPane.getTitleAt(tabIndex);
/* 9234 */     if ((this._oldValue.startsWith("<HTML>")) && (this._oldValue.endsWith("/HTML>"))) {
/* 9235 */       this._oldPrefix = "<HTML>";
/* 9236 */       this._oldPostfix = "</HTML>";
/* 9237 */       String title = this._oldValue.substring("<HTML>".length(), this._oldValue.length() - "</HTML>".length());
/* 9238 */       if ((title.startsWith("<B>")) && (title.endsWith("/B>"))) {
/* 9239 */         title = title.substring("<B>".length(), title.length() - "</B>".length());
/* 9240 */         this._oldPrefix += "<B>";
/* 9241 */         this._oldPostfix = ("</B>" + this._oldPostfix);
/*      */       }
/* 9243 */       e.setText(title);
/*      */     }
/*      */     else {
/* 9246 */       this._oldPrefix = "";
/* 9247 */       this._oldPostfix = "";
/* 9248 */       e.setText(this._oldValue);
/*      */     }
/* 9250 */     e.selectAll();
/* 9251 */     e.setForeground(this._tabPane.getForegroundAt(tabIndex));
/*      */   }
/*      */ 
/*      */   protected Rectangle getTabsTextBoundsAt(int tabIndex) {
/* 9255 */     Rectangle tabRect = this._tabPane.getBoundsAt(tabIndex);
/* 9256 */     Rectangle iconRect = new Rectangle();
/* 9257 */     Rectangle textRect = new Rectangle();
/*      */ 
/* 9259 */     if (tabRect.width < 200) {
/* 9260 */       tabRect.width = 200;
/*      */     }
/* 9262 */     String title = getCurrentDisplayTitleAt(this._tabPane, tabIndex);
/*      */ 
/* 9266 */     Icon icon = this._tabPane.getIconForTab(tabIndex);
/*      */ 
/* 9268 */     Font font = this._tabPane.getFont();
/* 9269 */     if ((tabIndex == this._tabPane.getSelectedIndex()) && (this._tabPane.isBoldActiveTab())) {
/* 9270 */       font = font.deriveFont(1);
/*      */     }
/* 9272 */     SwingUtilities.layoutCompoundLabel(this._tabPane, this._tabPane.getGraphics().getFontMetrics(font), title, icon, 0, 0, 0, 11, tabRect, iconRect, textRect, icon == null ? 0 : this._textIconGap);
/*      */ 
/* 9277 */     if ((this._tabPane.getTabPlacement() == 1) || (this._tabPane.getTabPlacement() == 3)) {
/* 9278 */       tabRect.x += this._iconMargin;
/* 9279 */       textRect.x = (icon != null ? iconRect.x + iconRect.width + this._textIconGap : tabRect.x + this._textPadding);
/* 9280 */       textRect.width += 2;
/*      */     }
/*      */     else {
/* 9283 */       tabRect.y += this._iconMargin;
/* 9284 */       textRect.y = (icon != null ? iconRect.y + iconRect.height + this._textIconGap : tabRect.y + this._textPadding);
/* 9285 */       tabRect.x += 2;
/* 9286 */       tabRect.x += 2;
/* 9287 */       textRect.height += 2;
/*      */     }
/*      */ 
/* 9290 */     return textRect;
/*      */   }
/*      */ 
/*      */   private void updateTab() {
/* 9294 */     if (this._isEditing)
/* 9295 */       resizeEditor(getEditingTabIndex());
/*      */   }
/*      */ 
/*      */   public void insertUpdate(DocumentEvent e)
/*      */   {
/* 9300 */     updateTab();
/*      */   }
/*      */ 
/*      */   public void removeUpdate(DocumentEvent e) {
/* 9304 */     updateTab();
/*      */   }
/*      */ 
/*      */   public void changedUpdate(DocumentEvent e) {
/* 9308 */     updateTab();
/*      */   }
/*      */ 
/*      */   protected void resizeEditor(int tabIndex)
/*      */   {
/* 9313 */     Rectangle tabsTextBoundsAt = getTabsTextBoundsAt(tabIndex);
/* 9314 */     if (tabsTextBoundsAt.isEmpty()) {
/* 9315 */       tabsTextBoundsAt = new Rectangle(14, 3);
/*      */     }
/*      */ 
/* 9318 */     tabsTextBoundsAt.x -= this._tabEditor.getBorder().getBorderInsets(this._tabEditor).left;
/* 9319 */     tabsTextBoundsAt.width = (tabsTextBoundsAt.width + this._tabEditor.getBorder().getBorderInsets(this._tabEditor).left + this._tabEditor.getBorder().getBorderInsets(this._tabEditor).right);
/*      */ 
/* 9322 */     tabsTextBoundsAt.y -= this._tabEditor.getBorder().getBorderInsets(this._tabEditor).top;
/* 9323 */     tabsTextBoundsAt.height = (tabsTextBoundsAt.height + this._tabEditor.getBorder().getBorderInsets(this._tabEditor).top + this._tabEditor.getBorder().getBorderInsets(this._tabEditor).bottom);
/*      */ 
/* 9326 */     this._tabEditor.setBounds(SwingUtilities.convertRectangle(this._tabPane, tabsTextBoundsAt, getTabPanel()));
/* 9327 */     this._tabEditor.invalidate();
/* 9328 */     this._tabEditor.validate();
/*      */ 
/* 9335 */     this._tabPane.doLayout();
/*      */ 
/* 9338 */     getTabPanel().getParent().getParent().repaint();
/*      */   }
/*      */ 
/*      */   protected String getCurrentDisplayTitleAt(JideTabbedPane tp, int index) {
/* 9342 */     String returnTitle = tp.getDisplayTitleAt(index);
/* 9343 */     if ((this._isEditing) && (index == this._editingTab)) {
/* 9344 */       returnTitle = this._tabEditor.getText();
/*      */     }
/* 9346 */     return returnTitle;
/*      */   }
/*      */ 
/*      */   public void startEditing(MouseEvent e)
/*      */   {
/* 9381 */     int tabIndex = tabForCoordinate(this._tabPane, e.getX(), e.getY());
/*      */ 
/* 9383 */     if ((!e.isPopupTrigger()) && (tabIndex >= 0) && (this._tabPane.isEnabledAt(tabIndex)) && (this._tabPane.isTabEditingAllowed()) && (e.getClickCount() == 2))
/*      */     {
/* 9386 */       boolean shouldEdit = true;
/* 9387 */       if (this._tabPane.getTabEditingValidator() != null) {
/* 9388 */         shouldEdit = this._tabPane.getTabEditingValidator().shouldStartEdit(tabIndex, e);
/*      */       }
/* 9390 */       if (shouldEdit) {
/* 9391 */         e.consume();
/* 9392 */         this._tabPane.editTabAt(tabIndex);
/*      */       }
/*      */     }
/* 9395 */     if ((e.getClickCount() == 1) && 
/* 9396 */       (this._tabPane.isTabEditing())) {
/* 9397 */       boolean shouldStopEdit = true;
/* 9398 */       if (this._tabPane.getTabEditingValidator() != null) {
/* 9399 */         shouldStopEdit = this._tabPane.getTabEditingValidator().alertIfInvalid(tabIndex, this._oldPrefix + this._tabEditor.getText() + this._oldPostfix);
/*      */       }
/* 9401 */       if (shouldStopEdit)
/* 9402 */         this._tabPane.stopTabEditing();
/*      */     }
/*      */   }
/*      */ 
/*      */   public ThemePainter getPainter()
/*      */   {
/* 9408 */     return this._painter;
/*      */   }
/*      */ 
/*      */   protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected)
/*      */   {
/* 9507 */     Rectangle tabRect = new Rectangle(rects[tabIndex]);
/* 9508 */     if (((tabPlacement == 1) || (tabPlacement == 3)) && (!this._tabPane.getComponentOrientation().isLeftToRight())) {
/* 9509 */       tabRect.x += this._tabScroller.viewport.getExpectedViewX();
/*      */     }
/* 9511 */     if ((this._tabPane.hasFocus()) && (isSelected))
/*      */     {
/* 9513 */       g.setColor(this._focus);
/*      */       int x;
/*      */       int y;
/*      */       int w;
/*      */       int h;
/* 9514 */       switch (tabPlacement) {
/*      */       case 2:
/* 9516 */         x = tabRect.x + 3;
/* 9517 */         y = tabRect.y + 3;
/* 9518 */         w = tabRect.width - 5;
/* 9519 */         h = tabRect.height - 6 - getTabGap();
/* 9520 */         break;
/*      */       case 4:
/* 9522 */         x = tabRect.x + 2;
/* 9523 */         y = tabRect.y + 3;
/* 9524 */         w = tabRect.width - 5;
/* 9525 */         h = tabRect.height - 6 - getTabGap();
/* 9526 */         break;
/*      */       case 3:
/* 9528 */         x = tabRect.x + 3;
/* 9529 */         y = tabRect.y + 2;
/* 9530 */         w = tabRect.width - 6 - getTabGap();
/* 9531 */         h = tabRect.height - 5;
/* 9532 */         break;
/*      */       case 1:
/*      */       default:
/* 9535 */         x = tabRect.x + 3;
/* 9536 */         y = tabRect.y + 3;
/* 9537 */         w = tabRect.width - 6 - getTabGap();
/* 9538 */         h = tabRect.height - 5;
/*      */       }
/* 9540 */       BasicGraphicsUtils.drawDashedRect(g, x, y, w, h);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected boolean isRoundedCorner() {
/* 9545 */     return "true".equals(SecurityUtils.getProperty("shadingtheme", "false"));
/*      */   }
/*      */ 
/*      */   protected int getTabShape() {
/* 9549 */     return this._tabPane.getTabShape();
/*      */   }
/*      */ 
/*      */   protected int getTabResizeMode() {
/* 9553 */     return this._tabPane.getTabResizeMode();
/*      */   }
/*      */ 
/*      */   protected int getColorTheme() {
/* 9557 */     return this._tabPane.getColorTheme();
/*      */   }
/*      */ 
/*      */   protected int getLeftMargin()
/*      */   {
/* 9574 */     if (getTabShape() == 4) {
/* 9575 */       return 18;
/*      */     }
/* 9577 */     if (getTabShape() == 8) {
/* 9578 */       return 6;
/*      */     }
/*      */ 
/* 9581 */     return 0;
/*      */   }
/*      */ 
/*      */   protected int getTabGap()
/*      */   {
/* 9586 */     if (getTabShape() == 4) {
/* 9587 */       return 4;
/*      */     }
/*      */ 
/* 9590 */     return 0;
/*      */   }
/*      */ 
/*      */   protected int getLayoutSize()
/*      */   {
/* 9595 */     int tabShape = getTabShape();
/* 9596 */     if (tabShape == 8) {
/* 9597 */       return 6;
/*      */     }
/* 9599 */     if (tabShape == 7) {
/* 9600 */       return 15;
/*      */     }
/* 9602 */     if ((this._tabPane.getTabShape() == 5) || (this._tabPane.getTabShape() == 10)) {
/* 9603 */       return 2;
/*      */     }
/* 9605 */     if ((tabShape == 1) || (tabShape == 11))
/*      */     {
/* 9607 */       return 6;
/*      */     }
/*      */ 
/* 9610 */     return 0;
/*      */   }
/*      */ 
/*      */   protected int getTabRightPadding()
/*      */   {
/* 9615 */     if (getTabShape() == 8) {
/* 9616 */       return 4;
/*      */     }
/*      */ 
/* 9619 */     return 0;
/*      */   }
/*      */ 
/*      */   protected MouseListener createMouseListener()
/*      */   {
/* 9624 */     if ((getTabShape() == 1) || (getTabShape() == 11)) {
/* 9625 */       return new RolloverMouseHandler();
/*      */     }
/*      */ 
/* 9628 */     return new MouseHandler();
/*      */   }
/*      */ 
/*      */   protected MouseWheelListener createMouseWheelListener()
/*      */   {
/* 9633 */     return new MouseWheelHandler();
/*      */   }
/*      */ 
/*      */   protected MouseMotionListener createMouseMotionListener() {
/* 9637 */     if ((getTabShape() == 1) || (getTabShape() == 11)) {
/* 9638 */       return new RolloverMouseMotionHandler();
/*      */     }
/*      */ 
/* 9641 */     return new MouseMotionHandler();
/*      */   }
/*      */ 
/*      */   protected boolean isTabLeadingComponentVisible()
/*      */   {
/* 9717 */     return (this._tabPane.isTabShown()) && (this._tabLeadingComponent != null) && (this._tabLeadingComponent.isVisible());
/*      */   }
/*      */ 
/*      */   protected boolean isTabTrailingComponentVisible() {
/* 9721 */     return (this._tabPane.isTabShown()) && (this._tabTrailingComponent != null) && (this._tabTrailingComponent.isVisible());
/*      */   }
/*      */ 
/*      */   protected boolean isTabTopVisible(int tabPlacement) {
/* 9725 */     switch (tabPlacement) {
/*      */     case 2:
/*      */     case 4:
/* 9728 */       return ((isTabLeadingComponentVisible()) && (this._tabLeadingComponent.getPreferredSize().width > calculateMaxTabWidth(tabPlacement))) || ((isTabTrailingComponentVisible()) && (this._tabTrailingComponent.getPreferredSize().width > calculateMaxTabWidth(tabPlacement)));
/*      */     case 1:
/*      */     case 3:
/*      */     }
/*      */ 
/* 9733 */     return ((isTabLeadingComponentVisible()) && (this._tabLeadingComponent.getPreferredSize().height > calculateMaxTabHeight(tabPlacement))) || ((isTabTrailingComponentVisible()) && (this._tabTrailingComponent.getPreferredSize().height > calculateMaxTabHeight(tabPlacement)));
/*      */   }
/*      */ 
/*      */   protected boolean showFocusIndicator()
/*      */   {
/* 9739 */     return (this._tabPane.hasFocusComponent()) && (this._showFocusIndicator);
/*      */   }
/*      */ 
/*      */   private int getNumberOfTabButtons() {
/* 9743 */     int numberOfButtons = (!isShowTabButtons()) || (isShrinkTabs()) ? 1 : 4;
/* 9744 */     if ((!isShowCloseButton()) || (isShowCloseButtonOnTab())) {
/* 9745 */       numberOfButtons--;
/*      */     }
/* 9747 */     return numberOfButtons;
/*      */   }
/*      */ 
/*      */   protected String getResourceString(String key)
/*      */   {
/* 9757 */     if (this._tabPane != null) {
/* 9758 */       return this._tabPane.getResourceString(key);
/*      */     }
/* 9760 */     return Resource.getResourceBundle(Locale.getDefault()).getString(key);
/*      */   }
/*      */ 
/*      */   private void setMouseOverTabIndex(int index) {
/* 9764 */     this._indexMouseOver = index;
/* 9765 */     this._tabPane.putClientProperty("JideTabbedPane.mouseOverTabIndex", Integer.valueOf(this._indexMouseOver));
/*      */   }
/*      */ 
/*      */   public class RolloverMouseHandler extends BasicJideTabbedPaneUI.MouseHandler
/*      */   {
/*      */     public RolloverMouseHandler()
/*      */     {
/* 9697 */       super();
/*      */     }
/*      */     public void mouseEntered(MouseEvent e) {
/* 9700 */       super.mouseEntered(e);
/* 9701 */       int tabIndex = BasicJideTabbedPaneUI.this.tabForCoordinate(BasicJideTabbedPaneUI.this._tabPane, e.getX(), e.getY());
/* 9702 */       BasicJideTabbedPaneUI.this._mouseEnter = true;
/* 9703 */       BasicJideTabbedPaneUI.this.setMouseOverTabIndex(tabIndex);
/* 9704 */       BasicJideTabbedPaneUI.this._tabPane.repaint();
/*      */     }
/*      */ 
/*      */     public void mouseExited(MouseEvent e)
/*      */     {
/* 9709 */       super.mouseExited(e);
/* 9710 */       BasicJideTabbedPaneUI.this.setMouseOverTabIndex(-1);
/* 9711 */       BasicJideTabbedPaneUI.this._mouseEnter = false;
/* 9712 */       BasicJideTabbedPaneUI.this._tabPane.repaint();
/*      */     }
/*      */   }
/*      */ 
/*      */   public class RolloverMouseMotionHandler extends MouseMotionAdapter
/*      */   {
/*      */     public RolloverMouseMotionHandler()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void mouseMoved(MouseEvent e)
/*      */     {
/* 9686 */       super.mouseMoved(e);
/* 9687 */       int tabIndex = BasicJideTabbedPaneUI.this.tabForCoordinate(BasicJideTabbedPaneUI.this._tabPane, e.getX(), e.getY());
/* 9688 */       if (tabIndex != BasicJideTabbedPaneUI.this._indexMouseOver) {
/* 9689 */         BasicJideTabbedPaneUI.this.setMouseOverTabIndex(tabIndex);
/* 9690 */         BasicJideTabbedPaneUI.this._tabPane.repaint();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public class DefaultMouseHandler extends BasicJideTabbedPaneUI.MouseHandler
/*      */   {
/*      */     public DefaultMouseHandler()
/*      */     {
/* 9659 */       super();
/*      */     }
/*      */     public void mousePressed(MouseEvent e) {
/* 9662 */       super.mousePressed(e);
/*      */     }
/*      */ 
/*      */     public void mouseEntered(MouseEvent e)
/*      */     {
/* 9667 */       super.mouseEntered(e);
/* 9668 */       int tabIndex = BasicJideTabbedPaneUI.this.getTabAtLocation(e.getX(), e.getY());
/* 9669 */       BasicJideTabbedPaneUI.this._mouseEnter = true;
/* 9670 */       BasicJideTabbedPaneUI.this.setMouseOverTabIndex(tabIndex);
/* 9671 */       BasicJideTabbedPaneUI.this._tabPane.repaint();
/*      */     }
/*      */ 
/*      */     public void mouseExited(MouseEvent e)
/*      */     {
/* 9676 */       super.mouseExited(e);
/* 9677 */       BasicJideTabbedPaneUI.this.setMouseOverTabIndex(-1);
/* 9678 */       BasicJideTabbedPaneUI.this._mouseEnter = false;
/* 9679 */       BasicJideTabbedPaneUI.this._tabPane.repaint();
/*      */     }
/*      */   }
/*      */ 
/*      */   public class DefaultMouseMotionHandler extends MouseMotionAdapter
/*      */   {
/*      */     public DefaultMouseMotionHandler()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void mouseMoved(MouseEvent e)
/*      */     {
/* 9648 */       super.mouseMoved(e);
/* 9649 */       int tabIndex = BasicJideTabbedPaneUI.this.getTabAtLocation(e.getX(), e.getY());
/* 9650 */       if (tabIndex != BasicJideTabbedPaneUI.this._indexMouseOver) {
/* 9651 */         BasicJideTabbedPaneUI.this.setMouseOverTabIndex(tabIndex);
/* 9652 */         BasicJideTabbedPaneUI.this._tabPane.repaint();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private class DropListener
/*      */     implements DropTargetListener
/*      */   {
/*      */     private BasicJideTabbedPaneUI.DragOverTimer _timer;
/* 9445 */     int _index = -1;
/*      */ 
/*      */     public DropListener() {
/*      */     }
/*      */ 
/*      */     public void dragEnter(DropTargetDragEvent dtde) {
/*      */     }
/*      */ 
/*      */     public void dragOver(DropTargetDragEvent dtde) {
/* 9454 */       if (!BasicJideTabbedPaneUI.this._tabPane.isEnabled()) {
/* 9455 */         return;
/*      */       }
/*      */ 
/* 9458 */       int tabIndex = BasicJideTabbedPaneUI.this.getTabAtLocation(dtde.getLocation().x, dtde.getLocation().y);
/* 9459 */       if ((tabIndex >= 0) && (BasicJideTabbedPaneUI.this._tabPane.isEnabledAt(tabIndex))) {
/* 9460 */         if (tabIndex != BasicJideTabbedPaneUI.this._tabPane.getSelectedIndex())
/*      */         {
/* 9463 */           if (tabIndex != this._index)
/*      */           {
/* 9467 */             stopTimer();
/* 9468 */             startTimer(tabIndex);
/* 9469 */             this._index = tabIndex;
/*      */           }
/*      */         }
/*      */       }
/* 9473 */       else stopTimer();
/*      */ 
/* 9475 */       dtde.rejectDrag();
/*      */     }
/*      */ 
/*      */     private void startTimer(int tabIndex) {
/* 9479 */       this._timer = new BasicJideTabbedPaneUI.DragOverTimer(BasicJideTabbedPaneUI.this, tabIndex);
/* 9480 */       this._timer.start();
/*      */     }
/*      */ 
/*      */     private void stopTimer() {
/* 9484 */       if (this._timer != null) {
/* 9485 */         this._timer.stop();
/* 9486 */         this._timer = null;
/* 9487 */         this._index = -1;
/*      */       }
/*      */     }
/*      */ 
/*      */     public void dropActionChanged(DropTargetDragEvent dtde) {
/*      */     }
/*      */ 
/*      */     public void dragExit(DropTargetEvent dte) {
/* 9495 */       stopTimer();
/*      */     }
/*      */ 
/*      */     public void drop(DropTargetDropEvent dtde) {
/* 9499 */       stopTimer();
/*      */     }
/*      */   }
/*      */ 
/*      */   private class DragOverTimer extends Timer
/*      */     implements ActionListener
/*      */   {
/*      */     private int _index;
/*      */     private static final long serialVersionUID = -2529347876574638854L;
/*      */ 
/*      */     public DragOverTimer(int index)
/*      */     {
/* 9416 */       super(null);
/* 9417 */       this._index = index;
/* 9418 */       addActionListener(this);
/* 9419 */       setRepeats(false);
/*      */     }
/*      */ 
/*      */     public void actionPerformed(ActionEvent e) {
/* 9423 */       if (BasicJideTabbedPaneUI.this._tabPane.getTabCount() == 0) {
/* 9424 */         return;
/*      */       }
/* 9426 */       if (this._index == BasicJideTabbedPaneUI.this._tabPane.getSelectedIndex()) {
/* 9427 */         if (BasicJideTabbedPaneUI.this._tabPane.isRequestFocusEnabled()) {
/* 9428 */           BasicJideTabbedPaneUI.this._tabPane.requestFocusInWindow();
/* 9429 */           BasicJideTabbedPaneUI.this._tabPane.repaint(BasicJideTabbedPaneUI.this.getTabBounds(BasicJideTabbedPaneUI.this._tabPane, this._index));
/*      */         }
/*      */       }
/*      */       else {
/* 9433 */         if (BasicJideTabbedPaneUI.this._tabPane.isRequestFocusEnabled()) {
/* 9434 */           BasicJideTabbedPaneUI.this._tabPane.requestFocusInWindow();
/*      */         }
/* 9436 */         BasicJideTabbedPaneUI.this._tabPane.setSelectedIndex(this._index);
/*      */       }
/* 9438 */       stop();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected class TabEditor extends JTextField
/*      */     implements UIResource
/*      */   {
/*      */     TabEditor()
/*      */     {
/* 9351 */       setOpaque(false);
/*      */ 
/* 9353 */       setBorder(BorderFactory.createCompoundBorder(new PartialLineBorder(Color.BLACK, 1, true), BorderFactory.createEmptyBorder(0, 2, 0, 2)));
/*      */     }
/*      */ 
/*      */     public boolean stopEditing()
/*      */     {
/* 9359 */       return true;
/*      */     }
/*      */ 
/*      */     protected void paintComponent(Graphics g) {
/* 9363 */       Graphics2D g2 = (Graphics2D)g;
/* 9364 */       Composite orgComposite = g2.getComposite();
/* 9365 */       Color orgColor = g2.getColor();
/*      */ 
/* 9367 */       g2.setComposite(AlphaComposite.getInstance(3, 0.7F));
/* 9368 */       Object o = JideSwingUtilities.setupShapeAntialiasing(g);
/*      */ 
/* 9370 */       g2.setColor(getBackground());
/* 9371 */       g.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 1, 1);
/*      */ 
/* 9373 */       JideSwingUtilities.restoreShapeAntialiasing(g, o);
/* 9374 */       g2.setColor(orgColor);
/* 9375 */       g2.setComposite(orgComposite);
/* 9376 */       super.paintComponent(g);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class TabSpaceAllocator
/*      */   {
/*      */     static final int startOffset = 4;
/* 8852 */     private Insets insets = null;
/*      */     static final int tabWidth = 24;
/*      */     static final int textIconGap = 8;
/*      */     private BasicJideTabbedPaneUI.AbstractTab[] tabs;
/*      */ 
/*      */     private void setInsets(Insets insets)
/*      */     {
/* 8861 */       this.insets = ((Insets)insets.clone());
/*      */     }
/*      */ 
/*      */     private void init(Rectangle[] rects, Insets insets) {
/* 8865 */       setInsets(insets);
/* 8866 */       this.tabs = new BasicJideTabbedPaneUI.AbstractTab[rects.length];
/*      */ 
/* 8868 */       for (int i = 0; i < rects.length; i++) {
/* 8869 */         this.tabs[i] = new BasicJideTabbedPaneUI.AbstractTab();
/* 8870 */         this.tabs[i].id = i;
/* 8871 */         this.tabs[i].width = rects[i].width;
/*      */       }
/* 8873 */       tabSort();
/*      */     }
/*      */ 
/*      */     private void bestfit(BasicJideTabbedPaneUI.AbstractTab[] tabs, int freeWidth, int startTab) {
/* 8877 */       int tabCount = tabs.length;
/*      */ 
/* 8882 */       int currentTabWidth = tabs[startTab].width;
/* 8883 */       int initialPos = startTab;
/*      */ 
/* 8885 */       if (startTab == tabCount - 1)
/*      */       {
/* 8887 */         tabs[startTab].width = freeWidth;
/* 8888 */         return;
/*      */       }
/* 8890 */       int worstWidth = freeWidth / (tabCount - startTab);
/*      */ 
/* 8892 */       while (currentTabWidth < worstWidth) {
/* 8893 */         freeWidth -= currentTabWidth;
/* 8894 */         startTab++; if (startTab < tabCount - 1) {
/* 8895 */           currentTabWidth = tabs[startTab].width; continue;
/*      */         }
/*      */ 
/* 8898 */         tabs[startTab].width = worstWidth;
/* 8899 */         return;
/*      */       }
/*      */ 
/* 8903 */       if (startTab == initialPos)
/*      */       {
/* 8905 */         for (int i = startTab; i < tabCount; i++) {
/* 8906 */           tabs[i].width = worstWidth;
/*      */         }
/*      */       }
/* 8909 */       else if (startTab < tabCount - 1)
/* 8910 */         bestfit(tabs, freeWidth, startTab);
/*      */     }
/*      */ 
/*      */     private void tabSort()
/*      */     {
/* 8917 */       int tabCount = this.tabs.length;
/* 8918 */       BasicJideTabbedPaneUI.AbstractTab tempTab = new BasicJideTabbedPaneUI.AbstractTab();
/* 8919 */       for (int i = 0; i < tabCount - 1; i++)
/* 8920 */         for (int j = i + 1; j < tabCount; j++)
/* 8921 */           if (this.tabs[i].width > this.tabs[j].width) {
/* 8922 */             tempTab.copy(this.tabs[j]);
/* 8923 */             this.tabs[j].copy(this.tabs[i]);
/* 8924 */             this.tabs[i].copy(tempTab);
/*      */           }
/*      */     }
/*      */ 
/*      */     private void outpush(Rectangle[] rects)
/*      */     {
/* 8933 */       for (BasicJideTabbedPaneUI.AbstractTab tab : this.tabs) {
/* 8934 */         rects[tab.id].width = tab.width;
/*      */       }
/* 8936 */       rects[0].x = 4;
/* 8937 */       for (int i = 1; i < rects.length; i++)
/* 8938 */         rects[i].x = (rects[(i - 1)].x + rects[(i - 1)].width);
/*      */     }
/*      */ 
/*      */     public void reArrange(Rectangle[] rects, Insets insets, int totalAvailableSpace)
/*      */     {
/* 8943 */       init(rects, insets);
/* 8944 */       bestfit(this.tabs, totalAvailableSpace, 0);
/* 8945 */       outpush(rects);
/* 8946 */       clearup();
/*      */     }
/*      */ 
/*      */     private void clearup() {
/* 8950 */       for (int i = 0; i < this.tabs.length; i++) {
/* 8951 */         this.tabs[i] = null;
/*      */       }
/* 8953 */       this.tabs = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   static class AbstractTab
/*      */   {
/*      */     int width;
/*      */     int id;
/*      */ 
/*      */     public void copy(AbstractTab tab)
/*      */     {
/* 8844 */       this.width = tab.width;
/* 8845 */       this.id = tab.id;
/*      */     }
/*      */   }
/*      */ 
/*      */   private class ContainerHandler
/*      */     implements ContainerListener
/*      */   {
/*      */     private ContainerHandler()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void componentAdded(ContainerEvent e)
/*      */     {
/* 8746 */       JideTabbedPane tp = (JideTabbedPane)e.getContainer();
/*      */ 
/* 8748 */       Component child = e.getChild();
/* 8749 */       if (((child instanceof UIResource)) || (child == tp.getTabLeadingComponent()) || (child == tp.getTabTrailingComponent())) {
/* 8750 */         return;
/*      */       }
/*      */ 
/* 8753 */       int index = tp.indexOfComponent(child);
/* 8754 */       String title = BasicJideTabbedPaneUI.this.getCurrentDisplayTitleAt(tp, index);
/* 8755 */       boolean isHTML = BasicHTML.isHTMLString(title);
/* 8756 */       if (isHTML) {
/* 8757 */         if (BasicJideTabbedPaneUI.this.htmlViews == null) {
/* 8758 */           BasicJideTabbedPaneUI.access$1902(BasicJideTabbedPaneUI.this, BasicJideTabbedPaneUI.this.createHTMLVector());
/*      */         }
/*      */         else {
/* 8761 */           View v = BasicHTML.createHTMLView(tp, title);
/* 8762 */           BasicJideTabbedPaneUI.this.htmlViews.insertElementAt(v, index);
/*      */         }
/*      */ 
/*      */       }
/* 8766 */       else if (BasicJideTabbedPaneUI.this.htmlViews != null) {
/* 8767 */         BasicJideTabbedPaneUI.this.htmlViews.insertElementAt(null, index);
/*      */       }
/*      */ 
/* 8771 */       if (BasicJideTabbedPaneUI.this._tabPane.isTabEditing()) {
/* 8772 */         if (index <= BasicJideTabbedPaneUI.this._tabPane.getEditingTabIndex()) {
/* 8773 */           ((BasicJideTabbedPaneUI)BasicJideTabbedPaneUI.this._tabPane.getUI())._editingTab += 1;
/*      */         }
/* 8775 */         BasicJideTabbedPaneUI.this.ensureCloseButtonCreated();
/* 8776 */         ((BasicJideTabbedPaneUI)BasicJideTabbedPaneUI.this._tabPane.getUI()).stopOrCancelEditing();
/*      */       }
/*      */ 
/* 8779 */       BasicJideTabbedPaneUI.this.ensureCloseButtonCreated();
/*      */     }
/*      */ 
/*      */     public void componentRemoved(ContainerEvent e) {
/* 8783 */       JideTabbedPane tp = (JideTabbedPane)e.getContainer();
/*      */ 
/* 8785 */       Component child = e.getChild();
/* 8786 */       if (((child instanceof UIResource)) || (child == tp.getTabLeadingComponent()) || (child == tp.getTabTrailingComponent())) {
/* 8787 */         return;
/*      */       }
/*      */ 
/* 8795 */       Integer index = (Integer)tp.getClientProperty("__index_to_remove__");
/*      */ 
/* 8797 */       if (index != null) {
/* 8798 */         if ((BasicJideTabbedPaneUI.this.htmlViews != null) && (BasicJideTabbedPaneUI.this.htmlViews.size() > index.intValue())) {
/* 8799 */           BasicJideTabbedPaneUI.this.htmlViews.removeElementAt(index.intValue());
/*      */         }
/* 8801 */         tp.putClientProperty("__index_to_remove__", null);
/*      */       }
/*      */ 
/* 8804 */       if (BasicJideTabbedPaneUI.this._tabPane.isTabEditing()) {
/* 8805 */         ((BasicJideTabbedPaneUI)BasicJideTabbedPaneUI.this._tabPane.getUI()).stopOrCancelEditing();
/*      */       }
/*      */ 
/* 8808 */       BasicJideTabbedPaneUI.this.ensureCloseButtonCreated();
/*      */     }
/*      */   }
/*      */ 
/*      */   private class ComponentHandler
/*      */     implements ComponentListener
/*      */   {
/*      */     private ComponentHandler()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void componentResized(ComponentEvent e)
/*      */     {
/* 8698 */       if ((BasicJideTabbedPaneUI.this.scrollableTabLayoutEnabled()) && ((BasicJideTabbedPaneUI.this._tabPane.getTabPlacement() == 3) || (BasicJideTabbedPaneUI.this._tabPane.getTabPlacement() == 7) || (BasicJideTabbedPaneUI.this._tabPane.getComponentOrientation().isLeftToRight()))) {
/* 8699 */         BasicJideTabbedPaneUI.this._tabScroller.viewport.setViewSize(new Dimension(BasicJideTabbedPaneUI.this._tabPane.getWidth(), BasicJideTabbedPaneUI.this._tabScroller.viewport.getViewSize().height));
/* 8700 */         BasicJideTabbedPaneUI.this.ensureActiveTabIsVisible(true);
/*      */       }
/*      */     }
/*      */ 
/*      */     public void componentMoved(ComponentEvent e)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void componentShown(ComponentEvent e)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void componentHidden(ComponentEvent e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public class MouseWheelHandler
/*      */     implements MouseWheelListener
/*      */   {
/*      */     public MouseWheelHandler()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void mouseWheelMoved(MouseWheelEvent e)
/*      */     {
/* 8675 */       if (BasicJideTabbedPaneUI.this._tabPane.isScrollSelectedTabOnWheel())
/*      */       {
/* 8678 */         BasicJideTabbedPaneUI.this._tabPane.setSelectedIndex(Math.min(BasicJideTabbedPaneUI.this._tabPane.getTabCount() - 1, Math.max(0, BasicJideTabbedPaneUI.this._tabPane.getSelectedIndex() + e.getWheelRotation())));
/*      */       }
/* 8681 */       else if ((BasicJideTabbedPaneUI.this.scrollableTabLayoutEnabled()) && (e.getWheelRotation() != 0))
/* 8682 */         if (e.getWheelRotation() > 0) {
/* 8683 */           for (int i = 0; i < e.getScrollAmount(); i++) {
/* 8684 */             BasicJideTabbedPaneUI.this._tabScroller.scrollForward(BasicJideTabbedPaneUI.this._tabPane.getTabPlacement());
/*      */           }
/*      */         }
/* 8687 */         else if (e.getWheelRotation() < 0)
/* 8688 */           for (int i = 0; i < e.getScrollAmount(); i++)
/* 8689 */             BasicJideTabbedPaneUI.this._tabScroller.scrollBackward(BasicJideTabbedPaneUI.this._tabPane.getTabPlacement());
/*      */     }
/*      */   }
/*      */ 
/*      */   public class MouseHandler extends MouseAdapter
/*      */   {
/*      */     public MouseHandler()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void mouseClicked(MouseEvent e)
/*      */     {
/* 8592 */       if ((BasicJideTabbedPaneUI.this._tabPane == null) || (!BasicJideTabbedPaneUI.this._tabPane.isEnabled())) {
/* 8593 */         return;
/*      */       }
/*      */ 
/* 8596 */       if (SwingUtilities.isMiddleMouseButton(e)) {
/* 8597 */         int tabIndex = BasicJideTabbedPaneUI.this.tabForCoordinate(BasicJideTabbedPaneUI.this._tabPane, e.getX(), e.getY());
/* 8598 */         Action action = BasicJideTabbedPaneUI.this.getActionMap().get("closeTabAction");
/* 8599 */         if ((action != null) && (tabIndex >= 0) && (BasicJideTabbedPaneUI.this._tabPane.isEnabledAt(tabIndex)) && (BasicJideTabbedPaneUI.this._tabPane.isCloseTabOnMouseMiddleButton()) && (BasicJideTabbedPaneUI.this._tabPane.isTabClosableAt(tabIndex))) {
/* 8600 */           ActionEvent event = new ActionEvent(BasicJideTabbedPaneUI.this._tabPane, tabIndex, "middleMouseButtonClicked");
/* 8601 */           action.actionPerformed(event);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     public void mousePressed(MouseEvent e)
/*      */     {
/* 8608 */       if ((BasicJideTabbedPaneUI.this._tabPane == null) || (!BasicJideTabbedPaneUI.this._tabPane.isEnabled())) {
/* 8609 */         return;
/*      */       }
/*      */ 
/* 8612 */       if ((SwingUtilities.isLeftMouseButton(e)) || (BasicJideTabbedPaneUI.this._tabPane.isRightClickSelect())) {
/* 8613 */         int tabIndex = BasicJideTabbedPaneUI.this.tabForCoordinate(BasicJideTabbedPaneUI.this._tabPane, e.getX(), e.getY());
/* 8614 */         if ((tabIndex >= 0) && (BasicJideTabbedPaneUI.this._tabPane.isEnabledAt(tabIndex))) {
/* 8615 */           if ((tabIndex == BasicJideTabbedPaneUI.this._tabPane.getSelectedIndex()) && (JideSwingUtilities.isAncestorOfFocusOwner(BasicJideTabbedPaneUI.this._tabPane))) {
/* 8616 */             if ((BasicJideTabbedPaneUI.this._tabPane.isAutoFocusOnTabHideClose()) && (BasicJideTabbedPaneUI.this._tabPane.isRequestFocusEnabled()))
/*      */             {
/* 8618 */               BasicJideTabbedPaneUI.this._tabPane.requestFocus();
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 8623 */             BasicJideTabbedPaneUI.this._tabPane.setSelectedIndex(tabIndex);
/* 8624 */             BasicJideTabbedPaneUI.this._tabPane.processMouseSelection(tabIndex, e);
/* 8625 */             Component comp = BasicJideTabbedPaneUI.this._tabPane.getComponentAt(tabIndex);
/* 8626 */             if ((BasicJideTabbedPaneUI.this._tabPane.isAutoFocusOnTabHideClose()) && (comp != null) && (!comp.isVisible()) && (SystemInfo.isJdk15Above()) && (!SystemInfo.isJdk6Above())) {
/* 8627 */               comp.addComponentListener(new ComponentAdapter(comp)
/*      */               {
/*      */                 public void componentShown(ComponentEvent e)
/*      */                 {
/* 8631 */                   this.val$comp.removeComponentListener(this);
/*      */ 
/* 8633 */                   Component lastFocused = BasicJideTabbedPaneUI.this._tabPane.getLastFocusedComponent(this.val$comp);
/* 8634 */                   if (lastFocused != null)
/*      */                   {
/* 8637 */                     lastFocused.requestFocus();
/*      */                   }
/* 8640 */                   else if (BasicJideTabbedPaneUI.this._tabPane.isRequestFocusEnabled())
/*      */                   {
/* 8642 */                     BasicJideTabbedPaneUI.this._tabPane.requestFocus();
/*      */                   }
/*      */                 }
/*      */               });
/*      */             }
/*      */             else {
/* 8649 */               Component lastFocused = BasicJideTabbedPaneUI.this._tabPane.getLastFocusedComponent(comp);
/* 8650 */               if (lastFocused != null)
/*      */               {
/* 8653 */                 lastFocused.requestFocus();
/*      */               }
/*      */               else
/*      */               {
/* 8658 */                 boolean foundInTab = JideSwingUtilities.compositeRequestFocus(comp);
/* 8659 */                 if (!foundInTab) {
/* 8660 */                   BasicJideTabbedPaneUI.this._tabPane.requestFocus();
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 8667 */       if (!BasicJideTabbedPaneUI.this.isTabEditing())
/* 8668 */         BasicJideTabbedPaneUI.this.startEditing(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public class MouseMotionHandler extends MouseMotionAdapter
/*      */   {
/*      */     public MouseMotionHandler()
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public class TabFocusListener
/*      */     implements FocusListener
/*      */   {
/*      */     public TabFocusListener()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void focusGained(FocusEvent e)
/*      */     {
/* 8564 */       repaintSelectedTab();
/*      */     }
/*      */ 
/*      */     public void focusLost(FocusEvent e) {
/* 8568 */       repaintSelectedTab();
/*      */     }
/*      */ 
/*      */     private void repaintSelectedTab() {
/* 8572 */       if (BasicJideTabbedPaneUI.this._tabPane.getTabCount() > 0) {
/* 8573 */         Rectangle rect = BasicJideTabbedPaneUI.this.getTabBounds(BasicJideTabbedPaneUI.this._tabPane, BasicJideTabbedPaneUI.this._tabPane.getSelectedIndex());
/* 8574 */         if (rect != null)
/* 8575 */           BasicJideTabbedPaneUI.this._tabPane.repaint(rect);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public class TabSelectionHandler
/*      */     implements ChangeListener
/*      */   {
/*      */     public TabSelectionHandler()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void stateChanged(ChangeEvent e)
/*      */     {
/* 8551 */       ((BasicJideTabbedPaneUI)BasicJideTabbedPaneUI.this._tabPane.getUI()).stopOrCancelEditing();
/* 8552 */       BasicJideTabbedPaneUI.this.ensureCloseButtonCreated();
/* 8553 */       Runnable runnable = new Runnable() {
/*      */         public void run() {
/* 8555 */           BasicJideTabbedPaneUI.this.ensureActiveTabIsVisible(false);
/*      */         }
/*      */       };
/* 8558 */       SwingUtilities.invokeLater(runnable);
/*      */     }
/*      */   }
/*      */ 
/*      */   public class PropertyChangeHandler
/*      */     implements PropertyChangeListener
/*      */   {
/*      */     public PropertyChangeHandler()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void propertyChange(PropertyChangeEvent e)
/*      */     {
/* 8431 */       JTabbedPane pane = (JTabbedPane)e.getSource();
/* 8432 */       String name = e.getPropertyName();
/* 8433 */       if ("mnemonicAt".equals(name)) {
/* 8434 */         BasicJideTabbedPaneUI.this.updateMnemonics();
/* 8435 */         pane.repaint();
/*      */       }
/* 8437 */       else if ("displayedMnemonicIndexAt".equals(name)) {
/* 8438 */         pane.repaint();
/*      */       }
/* 8440 */       else if (name.equals("indexForTitle")) {
/* 8441 */         int index = ((Integer)e.getNewValue()).intValue();
/* 8442 */         String title = BasicJideTabbedPaneUI.this.getCurrentDisplayTitleAt(BasicJideTabbedPaneUI.this._tabPane, index);
/* 8443 */         if (BasicHTML.isHTMLString(title)) {
/* 8444 */           if (BasicJideTabbedPaneUI.this.htmlViews == null) {
/* 8445 */             BasicJideTabbedPaneUI.access$1902(BasicJideTabbedPaneUI.this, BasicJideTabbedPaneUI.this.createHTMLVector());
/*      */           }
/*      */           else {
/* 8448 */             View v = BasicHTML.createHTMLView(BasicJideTabbedPaneUI.this._tabPane, title);
/* 8449 */             BasicJideTabbedPaneUI.this.htmlViews.setElementAt(v, index);
/*      */           }
/*      */ 
/*      */         }
/* 8453 */         else if ((BasicJideTabbedPaneUI.this.htmlViews != null) && (BasicJideTabbedPaneUI.this.htmlViews.elementAt(index) != null)) {
/* 8454 */           BasicJideTabbedPaneUI.this.htmlViews.setElementAt(null, index);
/*      */         }
/*      */ 
/* 8457 */         BasicJideTabbedPaneUI.this.updateMnemonics();
/*      */ 
/* 8459 */         if ((BasicJideTabbedPaneUI.this.scrollableTabLayoutEnabled()) && ((BasicJideTabbedPaneUI.this._tabPane.getTabPlacement() == 3) || (BasicJideTabbedPaneUI.this._tabPane.getTabPlacement() == 7) || (BasicJideTabbedPaneUI.this._tabPane.getComponentOrientation().isLeftToRight()))) {
/* 8460 */           BasicJideTabbedPaneUI.this._tabScroller.viewport.setViewSize(new Dimension(BasicJideTabbedPaneUI.this._tabPane.getWidth(), BasicJideTabbedPaneUI.this._tabScroller.viewport.getViewSize().height));
/*      */ 
/* 8462 */           BasicJideTabbedPaneUI.this.ensureActiveTabIsVisible(false);
/*      */         }
/*      */       }
/* 8465 */       else if (name.equals("tabLayoutPolicy")) {
/* 8466 */         BasicJideTabbedPaneUI.this._tabPane.updateUI();
/*      */       }
/* 8468 */       else if (name.equals("closeTabAction")) {
/* 8469 */         BasicJideTabbedPaneUI.this.updateCloseAction();
/*      */       }
/* 8471 */       else if (name.equals("dragOverDisabled")) {
/* 8472 */         BasicJideTabbedPaneUI.this._tabPane.updateUI();
/*      */       }
/* 8474 */       else if (name.equals("tabColorProvider")) {
/* 8475 */         BasicJideTabbedPaneUI.this._tabPane.repaint();
/*      */       }
/* 8477 */       else if (name.equals("locale")) {
/* 8478 */         BasicJideTabbedPaneUI.this._tabPane.updateUI();
/*      */       }
/* 8480 */       else if (name.equals("boldActiveTab")) {
/* 8481 */         BasicJideTabbedPaneUI.this.getTabPanel().invalidate();
/* 8482 */         BasicJideTabbedPaneUI.this._tabPane.invalidate();
/* 8483 */         if ((BasicJideTabbedPaneUI.this.scrollableTabLayoutEnabled()) && ((BasicJideTabbedPaneUI.this._tabPane.getTabPlacement() == 3) || (BasicJideTabbedPaneUI.this._tabPane.getTabPlacement() == 7) || (BasicJideTabbedPaneUI.this._tabPane.getComponentOrientation().isLeftToRight()))) {
/* 8484 */           BasicJideTabbedPaneUI.this._tabScroller.viewport.setViewSize(new Dimension(BasicJideTabbedPaneUI.this._tabPane.getWidth(), BasicJideTabbedPaneUI.this._tabScroller.viewport.getViewSize().height));
/* 8485 */           BasicJideTabbedPaneUI.this.ensureActiveTabIsVisible(true);
/*      */         }
/*      */       }
/* 8488 */       else if (name.equals("tabLeadingComponent")) {
/* 8489 */         BasicJideTabbedPaneUI.this.ensureCurrentLayout();
/* 8490 */         if (BasicJideTabbedPaneUI.this._tabLeadingComponent != null) {
/* 8491 */           BasicJideTabbedPaneUI.this._tabLeadingComponent.setVisible(false);
/* 8492 */           BasicJideTabbedPaneUI.this._tabPane.remove(BasicJideTabbedPaneUI.this._tabLeadingComponent);
/*      */         }
/* 8494 */         BasicJideTabbedPaneUI.this._tabLeadingComponent = ((Component)e.getNewValue());
/* 8495 */         if (BasicJideTabbedPaneUI.this._tabLeadingComponent != null) {
/* 8496 */           BasicJideTabbedPaneUI.this._tabLeadingComponent.setVisible(true);
/* 8497 */           BasicJideTabbedPaneUI.this._tabPane.add(BasicJideTabbedPaneUI.this._tabLeadingComponent);
/*      */         }
/* 8499 */         BasicJideTabbedPaneUI.this._tabScroller.tabPanel.updateUI();
/*      */       }
/* 8501 */       else if (name.equals("tabTrailingComponent")) {
/* 8502 */         BasicJideTabbedPaneUI.this.ensureCurrentLayout();
/* 8503 */         if (BasicJideTabbedPaneUI.this._tabTrailingComponent != null) {
/* 8504 */           BasicJideTabbedPaneUI.this._tabTrailingComponent.setVisible(false);
/* 8505 */           BasicJideTabbedPaneUI.this._tabPane.remove(BasicJideTabbedPaneUI.this._tabTrailingComponent);
/*      */         }
/* 8507 */         BasicJideTabbedPaneUI.this._tabTrailingComponent = ((Component)e.getNewValue());
/* 8508 */         if (BasicJideTabbedPaneUI.this._tabTrailingComponent != null) {
/* 8509 */           BasicJideTabbedPaneUI.this._tabPane.add(BasicJideTabbedPaneUI.this._tabTrailingComponent);
/* 8510 */           BasicJideTabbedPaneUI.this._tabTrailingComponent.setVisible(true);
/*      */         }
/* 8512 */         BasicJideTabbedPaneUI.this._tabScroller.tabPanel.updateUI();
/*      */       }
/* 8514 */       else if ((name.equals("shrinkTab")) || (name.equals("hideIfOneTab")) || (name.equals("showTabArea")) || (name.equals("showTabContent")) || (name.equals("boxStyle")) || (name.equals("showIconsOnTab")) || (name.equals("showCloseButton")) || (name.equals("useDefaultShowIconsOnTab")) || (name.equals("showCloseButtonOnTab")) || (name.equals("useDefaultShowCloseButtonOnTab")) || (name.equals("tabClosable")) || (name.equals("tabShape")) || (name.equals("colorTheme")) || (name.equals("tabResizeMode")) || (name.equals("showTabButtons")))
/*      */       {
/* 8529 */         if (((name.equals("useDefaultShowCloseButtonOnTab")) || (name.equals("showCloseButtonOnTab"))) && (BasicJideTabbedPaneUI.this.isShowCloseButton()) && (BasicJideTabbedPaneUI.this.isShowCloseButtonOnTab()))
/*      */         {
/* 8531 */           BasicJideTabbedPaneUI.this.ensureCloseButtonCreated();
/*      */         }
/* 8533 */         BasicJideTabbedPaneUI.this._tabPane.updateUI();
/*      */       }
/* 8535 */       else if (name.equals("__index_to_remove__")) {
/* 8536 */         BasicJideTabbedPaneUI.this.setVisibleComponent(null);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public class TabCloseButton extends JButton
/*      */     implements MouseMotionListener, MouseListener, UIResource
/*      */   {
/*      */ 
/*      */     @Deprecated
/*      */     public static final int CLOSE_BUTTON = 0;
/*      */ 
/*      */     @Deprecated
/*      */     public static final int EAST_BUTTON = 1;
/*      */ 
/*      */     @Deprecated
/*      */     public static final int WEST_BUTTON = 2;
/*      */ 
/*      */     @Deprecated
/*      */     public static final int NORTH_BUTTON = 3;
/*      */ 
/*      */     @Deprecated
/*      */     public static final int SOUTH_BUTTON = 4;
/*      */ 
/*      */     @Deprecated
/*      */     public static final int LIST_BUTTON = 5;
/*      */     private int _type;
/* 8128 */     private int _index = -1;
/* 8129 */     private boolean _mouseOver = false;
/* 8130 */     private boolean _mousePressed = false;
/*      */ 
/*      */     public void updateUI()
/*      */     {
/* 8139 */       super.updateUI();
/* 8140 */       setMargin(new Insets(0, 0, 0, 0));
/* 8141 */       setBorder(BorderFactory.createEmptyBorder());
/* 8142 */       setFocusPainted(false);
/* 8143 */       setFocusable(false);
/* 8144 */       setRequestFocusEnabled(false);
/* 8145 */       String name = getName();
/* 8146 */       if (name != null) setToolTipText(BasicJideTabbedPaneUI.this.getResourceString(name)); 
/*      */     }
/*      */ 
/*      */     public TabCloseButton()
/*      */     {
/* 8150 */       this(0);
/*      */     }
/*      */ 
/*      */     public TabCloseButton(int type) {
/* 8154 */       addMouseMotionListener(this);
/* 8155 */       addMouseListener(this);
/* 8156 */       setFocusPainted(false);
/* 8157 */       setFocusable(false);
/* 8158 */       setType(type);
/*      */     }
/*      */ 
/*      */     public Dimension getPreferredSize()
/*      */     {
/* 8163 */       return new Dimension(16, 16);
/*      */     }
/*      */ 
/*      */     public Dimension getMinimumSize()
/*      */     {
/* 8168 */       return new Dimension(5, 5);
/*      */     }
/*      */ 
/*      */     public int getIndex() {
/* 8172 */       return this._index;
/*      */     }
/*      */ 
/*      */     public void setIndex(int index) {
/* 8176 */       this._index = index;
/*      */     }
/*      */ 
/*      */     public Dimension getMaximumSize()
/*      */     {
/* 8181 */       return new Dimension(2147483647, 2147483647);
/*      */     }
/*      */ 
/*      */     protected void paintComponent(Graphics g)
/*      */     {
/* 8186 */       if (!isEnabled()) {
/* 8187 */         setMouseOver(false);
/* 8188 */         setMousePressed(false);
/*      */       }
/* 8190 */       if ((isMouseOver()) && (isMousePressed())) {
/* 8191 */         g.setColor(UIDefaultsLookup.getColor("controlDkShadow"));
/* 8192 */         g.drawLine(0, 0, getWidth() - 1, 0);
/* 8193 */         g.drawLine(0, getHeight() - 2, 0, 1);
/* 8194 */         g.setColor(UIDefaultsLookup.getColor("control"));
/* 8195 */         g.drawLine(getWidth() - 1, 1, getWidth() - 1, getHeight() - 2);
/* 8196 */         g.drawLine(getWidth() - 1, getHeight() - 1, 0, getHeight() - 1);
/*      */       }
/* 8198 */       else if (isMouseOver()) {
/* 8199 */         g.setColor(UIDefaultsLookup.getColor("control"));
/* 8200 */         g.drawLine(0, 0, getWidth() - 1, 0);
/* 8201 */         g.drawLine(0, getHeight() - 2, 0, 1);
/* 8202 */         g.setColor(UIDefaultsLookup.getColor("controlDkShadow"));
/* 8203 */         g.drawLine(getWidth() - 1, 1, getWidth() - 1, getHeight() - 2);
/* 8204 */         g.drawLine(getWidth() - 1, getHeight() - 1, 0, getHeight() - 1);
/*      */       }
/* 8206 */       g.setColor(UIDefaultsLookup.getColor("controlShadow").darker());
/* 8207 */       int centerX = getWidth() >> 1;
/* 8208 */       int centerY = getHeight() >> 1;
/* 8209 */       int type = getType();
/* 8210 */       if (((BasicJideTabbedPaneUI.this._tabPane.getTabPlacement() == 1) || (BasicJideTabbedPaneUI.this._tabPane.getTabPlacement() == 3)) && (!BasicJideTabbedPaneUI.this._tabPane.getComponentOrientation().isLeftToRight())) {
/* 8211 */         if (type == 1) {
/* 8212 */           type = 2;
/*      */         }
/* 8214 */         else if (type == 2) {
/* 8215 */           type = 1;
/*      */         }
/*      */       }
/* 8218 */       switch (type) {
/*      */       case 0:
/* 8220 */         if (isEnabled()) {
/* 8221 */           g.drawLine(centerX - 3, centerY - 3, centerX + 3, centerY + 3);
/* 8222 */           g.drawLine(centerX - 4, centerY - 3, centerX + 2, centerY + 3);
/* 8223 */           g.drawLine(centerX + 3, centerY - 3, centerX - 3, centerY + 3);
/* 8224 */           g.drawLine(centerX + 2, centerY - 3, centerX - 4, centerY + 3);
/*      */         }
/*      */         else {
/* 8227 */           g.drawLine(centerX - 3, centerY - 3, centerX + 3, centerY + 3);
/* 8228 */           g.drawLine(centerX + 3, centerY - 3, centerX - 3, centerY + 3);
/*      */         }
/* 8230 */         break;
/*      */       case 1:
/* 8244 */         if ((BasicJideTabbedPaneUI.this._tabPane.getTabPlacement() == 1) || (BasicJideTabbedPaneUI.this._tabPane.getTabPlacement() == 3)) {
/* 8245 */           int x = centerX + 2; int y = centerY;
/* 8246 */           if (isEnabled()) {
/* 8247 */             g.drawLine(x - 4, y - 4, x - 4, y + 4);
/* 8248 */             g.drawLine(x - 3, y - 3, x - 3, y + 3);
/* 8249 */             g.drawLine(x - 2, y - 2, x - 2, y + 2);
/* 8250 */             g.drawLine(x - 1, y - 1, x - 1, y + 1);
/* 8251 */             g.drawLine(x, y, x, y);
/*      */           }
/*      */           else {
/* 8254 */             g.drawLine(x - 4, y - 4, x, y);
/* 8255 */             g.drawLine(x - 4, y - 4, x - 4, y + 4);
/* 8256 */             g.drawLine(x - 4, y + 4, x, y);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 8261 */           int x = centerX + 3; int y = centerY - 2;
/* 8262 */           if (isEnabled()) {
/* 8263 */             g.drawLine(x - 8, y, x, y);
/* 8264 */             g.drawLine(x - 7, y + 1, x - 1, y + 1);
/* 8265 */             g.drawLine(x - 6, y + 2, x - 2, y + 2);
/* 8266 */             g.drawLine(x - 5, y + 3, x - 3, y + 3);
/* 8267 */             g.drawLine(x - 4, y + 4, x - 4, y + 4);
/*      */           }
/*      */           else {
/* 8270 */             g.drawLine(x - 8, y, x, y);
/* 8271 */             g.drawLine(x - 8, y, x - 4, y + 4);
/* 8272 */             g.drawLine(x - 4, y + 4, x, y);
/*      */           }
/*      */         }
/*      */ 
/* 8276 */         break;
/*      */       case 2:
/* 8290 */         if ((BasicJideTabbedPaneUI.this._tabPane.getTabPlacement() == 1) || (BasicJideTabbedPaneUI.this._tabPane.getTabPlacement() == 3)) {
/* 8291 */           int x = centerX - 3; int y = centerY;
/* 8292 */           if (isEnabled()) {
/* 8293 */             g.drawLine(x, y, x, y);
/* 8294 */             g.drawLine(x + 1, y - 1, x + 1, y + 1);
/* 8295 */             g.drawLine(x + 2, y - 2, x + 2, y + 2);
/* 8296 */             g.drawLine(x + 3, y - 3, x + 3, y + 3);
/* 8297 */             g.drawLine(x + 4, y - 4, x + 4, y + 4);
/*      */           }
/*      */           else {
/* 8300 */             g.drawLine(x, y, x + 4, y - 4);
/* 8301 */             g.drawLine(x, y, x + 4, y + 4);
/* 8302 */             g.drawLine(x + 4, y - 4, x + 4, y + 4);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 8307 */           int x = centerX - 5; int y = centerY + 3;
/* 8308 */           if (isEnabled()) {
/* 8309 */             g.drawLine(x, y, x + 8, y);
/* 8310 */             g.drawLine(x + 1, y - 1, x + 7, y - 1);
/* 8311 */             g.drawLine(x + 2, y - 2, x + 6, y - 2);
/* 8312 */             g.drawLine(x + 3, y - 3, x + 5, y - 3);
/* 8313 */             g.drawLine(x + 4, y - 4, x + 4, y - 4);
/*      */           }
/*      */           else {
/* 8316 */             g.drawLine(x, y, x + 8, y);
/* 8317 */             g.drawLine(x, y, x + 4, y - 4);
/* 8318 */             g.drawLine(x + 8, y, x + 4, y - 4);
/*      */           }
/*      */         }
/*      */ 
/* 8322 */         break;
/*      */       case 5:
/* 8325 */         int x = centerX + 2; int y = centerY;
/*      */ 
/* 8327 */         g.drawLine(x - 6, y - 4, x - 6, y + 4);
/* 8328 */         g.drawLine(x + 1, y - 4, x + 1, y + 4);
/* 8329 */         g.drawLine(x - 6, y - 4, x + 1, y - 4);
/* 8330 */         g.drawLine(x - 4, y - 2, x - 1, y - 2);
/* 8331 */         g.drawLine(x - 4, y, x - 1, y);
/* 8332 */         g.drawLine(x - 4, y + 2, x - 1, y + 2);
/* 8333 */         g.drawLine(x - 6, y + 4, x + 1, y + 4);
/* 8334 */         break;
/*      */       case 3:
/*      */       case 4:
/*      */       }
/*      */     }
/*      */ 
/*      */     public boolean isFocusable() {
/* 8341 */       return false;
/*      */     }
/*      */ 
/*      */     public void requestFocus()
/*      */     {
/*      */     }
/*      */ 
/*      */     public boolean isOpaque()
/*      */     {
/* 8350 */       return false;
/*      */     }
/*      */ 
/*      */     public boolean scrollsForward() {
/* 8354 */       return (getType() == 1) || (getType() == 4);
/*      */     }
/*      */ 
/*      */     public void mouseDragged(MouseEvent e) {
/*      */     }
/*      */ 
/*      */     public void mouseMoved(MouseEvent e) {
/* 8361 */       if (!isEnabled()) return;
/* 8362 */       setMouseOver(true);
/* 8363 */       repaint();
/*      */     }
/*      */ 
/*      */     public void mouseClicked(MouseEvent e) {
/* 8367 */       if (!isEnabled()) return;
/* 8368 */       setMouseOver(true);
/* 8369 */       setMousePressed(false);
/*      */     }
/*      */ 
/*      */     public void mousePressed(MouseEvent e) {
/* 8373 */       if (!isEnabled()) return;
/* 8374 */       setMousePressed(true);
/* 8375 */       repaint();
/*      */     }
/*      */ 
/*      */     public void mouseReleased(MouseEvent e) {
/* 8379 */       if (!isEnabled()) return;
/* 8380 */       setMousePressed(false);
/* 8381 */       setMouseOver(false);
/*      */     }
/*      */ 
/*      */     public void mouseEntered(MouseEvent e) {
/* 8385 */       if (!isEnabled()) return;
/* 8386 */       setMouseOver(true);
/* 8387 */       repaint();
/*      */     }
/*      */ 
/*      */     public void mouseExited(MouseEvent e) {
/* 8391 */       if (!isEnabled()) return;
/* 8392 */       setMouseOver(false);
/* 8393 */       setMousePressed(false);
/* 8394 */       repaint();
/* 8395 */       BasicJideTabbedPaneUI.this._tabScroller.tabPanel.repaint();
/*      */     }
/*      */ 
/*      */     public int getType() {
/* 8399 */       return this._type;
/*      */     }
/*      */ 
/*      */     public void setType(int type) {
/* 8403 */       this._type = type;
/*      */     }
/*      */ 
/*      */     public boolean isMouseOver() {
/* 8407 */       return this._mouseOver;
/*      */     }
/*      */ 
/*      */     public void setMouseOver(boolean mouseOver) {
/* 8411 */       this._mouseOver = mouseOver;
/*      */     }
/*      */ 
/*      */     public boolean isMousePressed() {
/* 8415 */       return this._mousePressed;
/*      */     }
/*      */ 
/*      */     public void setMousePressed(boolean mousePressed) {
/* 8419 */       this._mousePressed = mousePressed;
/*      */     }
/*      */   }
/*      */ 
/*      */   public class ScrollableTabPanel extends JPanel
/*      */     implements UIResource
/*      */   {
/*      */     public ScrollableTabPanel()
/*      */     {
/* 7991 */       setLayout(null);
/*      */     }
/*      */ 
/*      */     public boolean isOpaque()
/*      */     {
/* 7996 */       return false;
/*      */     }
/*      */ 
/*      */     public void paintComponent(Graphics g)
/*      */     {
/* 8001 */       super.paintComponent(g);
/*      */ 
/* 8003 */       if (BasicJideTabbedPaneUI.this._tabPane.isOpaque()) {
/* 8004 */         if (BasicJideTabbedPaneUI.this.getTabShape() == 3) {
/* 8005 */           g.setColor(UIDefaultsLookup.getColor("JideTabbedPane.selectedTabBackground"));
/*      */         }
/*      */         else {
/* 8008 */           g.setColor(UIDefaultsLookup.getColor("JideTabbedPane.tabAreaBackground"));
/*      */         }
/* 8010 */         g.fillRect(0, 0, getWidth(), getHeight());
/*      */       }
/*      */ 
/* 8013 */       BasicJideTabbedPaneUI.this.paintTabArea(g, BasicJideTabbedPaneUI.this._tabPane.getTabPlacement(), BasicJideTabbedPaneUI.this._tabPane.getSelectedIndex(), this);
/*      */     }
/*      */ 
/*      */     public void scrollRectToVisible(Rectangle aRect)
/*      */     {
/* 8020 */       if (((BasicJideTabbedPaneUI.this._tabPane.getTabPlacement() == 1) || (BasicJideTabbedPaneUI.this._tabPane.getTabPlacement() == 3)) && (!BasicJideTabbedPaneUI.this._tabPane.getComponentOrientation().isLeftToRight())) {
/* 8021 */         int startX = aRect.x + BasicJideTabbedPaneUI.this._tabScroller.viewport.getExpectedViewX();
/* 8022 */         if (startX < 0)
/*      */         {
/* 8024 */           for (int i = BasicJideTabbedPaneUI.this._tabScroller.leadingTabIndex; i < BasicJideTabbedPaneUI.this._rects.length; i++) {
/* 8025 */             startX += BasicJideTabbedPaneUI.this._rects[i].width;
/* 8026 */             if (startX >= 0) {
/*      */               break;
/*      */             }
/*      */           }
/* 8030 */           BasicJideTabbedPaneUI.this._tabScroller.setLeadingTabIndex(BasicJideTabbedPaneUI.this._tabPane.getTabPlacement(), Math.min(i + 1, BasicJideTabbedPaneUI.this._rects.length - 1));
/*      */         }
/* 8032 */         else if (startX > aRect.x + aRect.width)
/*      */         {
/* 8034 */           for (int i = BasicJideTabbedPaneUI.this._tabScroller.leadingTabIndex - 1; i >= 0; i--) {
/* 8035 */             startX -= BasicJideTabbedPaneUI.this._rects[i].width;
/* 8036 */             if (startX <= aRect.x + aRect.width) {
/*      */               break;
/*      */             }
/*      */           }
/* 8040 */           BasicJideTabbedPaneUI.this._tabScroller.setLeadingTabIndex(BasicJideTabbedPaneUI.this._tabPane.getTabPlacement(), Math.max(i, 0));
/*      */         }
/* 8042 */         return;
/*      */       }
/* 8044 */       super.scrollRectToVisible(aRect);
/*      */     }
/*      */ 
/*      */     public void setBounds(int x, int y, int width, int height)
/*      */     {
/* 8051 */       if (((BasicJideTabbedPaneUI.this._tabPane.getTabPlacement() == 1) || (BasicJideTabbedPaneUI.this._tabPane.getTabPlacement() == 3)) && (!BasicJideTabbedPaneUI.this._tabPane.getComponentOrientation().isLeftToRight())) {
/* 8052 */         super.setBounds(0, y, width, height);
/* 8053 */         return;
/*      */       }
/* 8055 */       super.setBounds(x, y, width, height);
/*      */     }
/*      */ 
/*      */     public void setToolTipText(String text)
/*      */     {
/* 8063 */       BasicJideTabbedPaneUI.this._tabPane.setToolTipText(text);
/*      */     }
/*      */ 
/*      */     public String getToolTipText()
/*      */     {
/* 8068 */       return BasicJideTabbedPaneUI.this._tabPane.getToolTipText();
/*      */     }
/*      */ 
/*      */     public String getToolTipText(MouseEvent event)
/*      */     {
/* 8073 */       return BasicJideTabbedPaneUI.this._tabPane.getToolTipText(SwingUtilities.convertMouseEvent(this, event, BasicJideTabbedPaneUI.this._tabPane));
/*      */     }
/*      */ 
/*      */     public Point getToolTipLocation(MouseEvent event)
/*      */     {
/* 8078 */       return BasicJideTabbedPaneUI.this._tabPane.getToolTipLocation(SwingUtilities.convertMouseEvent(this, event, BasicJideTabbedPaneUI.this._tabPane));
/*      */     }
/*      */ 
/*      */     public JToolTip createToolTip()
/*      */     {
/* 8083 */       return BasicJideTabbedPaneUI.this._tabPane.createToolTip();
/*      */     }
/*      */   }
/*      */ 
/*      */   public class ScrollableTabViewport extends JViewport
/*      */     implements UIResource
/*      */   {
/* 7934 */     int _expectViewX = 0;
/* 7935 */     boolean _protectView = false;
/*      */ 
/*      */     public ScrollableTabViewport()
/*      */     {
/* 7939 */       setScrollMode(0);
/* 7940 */       setOpaque(false);
/* 7941 */       setLayout(new ViewportLayout(BasicJideTabbedPaneUI.this) {
/*      */         private static final long serialVersionUID = -1069760662716244442L;
/*      */ 
/*      */         public void layoutContainer(Container parent) {
/* 7946 */           if (((BasicJideTabbedPaneUI.this._tabPane.getTabPlacement() == 1) || (BasicJideTabbedPaneUI.this._tabPane.getTabPlacement() == 3)) && (!BasicJideTabbedPaneUI.this._tabPane.getComponentOrientation().isLeftToRight()))
/* 7947 */             BasicJideTabbedPaneUI.ScrollableTabViewport.this._protectView = true;
/*      */           try
/*      */           {
/* 7950 */             super.layoutContainer(parent);
/*      */           }
/*      */           finally {
/* 7953 */             BasicJideTabbedPaneUI.ScrollableTabViewport.this._protectView = false;
/*      */           }
/*      */         }
/*      */       });
/*      */     }
/*      */ 
/*      */     public Color getBackground()
/*      */     {
/* 7967 */       return UIDefaultsLookup.getColor("JideTabbedPane.background");
/*      */     }
/*      */ 
/*      */     public void setViewPosition(Point p)
/*      */     {
/* 7974 */       int oldX = this._expectViewX;
/* 7975 */       this._expectViewX = p.x;
/* 7976 */       super.setViewPosition(p);
/* 7977 */       if (this._protectView) {
/* 7978 */         this._expectViewX = oldX;
/* 7979 */         Point savedPosition = new Point(oldX, p.y);
/* 7980 */         super.setViewPosition(savedPosition);
/*      */       }
/*      */     }
/*      */ 
/*      */     public int getExpectedViewX() {
/* 7985 */       return this._expectViewX;
/*      */     }
/*      */   }
/*      */ 
/*      */   public class ScrollableTabSupport
/*      */     implements ChangeListener
/*      */   {
/*      */     public BasicJideTabbedPaneUI.ScrollableTabViewport viewport;
/*      */     public BasicJideTabbedPaneUI.ScrollableTabPanel tabPanel;
/*      */     public JButton scrollForwardButton;
/*      */     public JButton scrollBackwardButton;
/*      */     public JButton listButton;
/*      */     public JButton closeButton;
/*      */     public int leadingTabIndex;
/* 7421 */     private Point tabViewPosition = new Point(0, 0);
/*      */     public JidePopup _popup;
/*      */ 
/*      */     ScrollableTabSupport(int tabPlacement)
/*      */     {
/* 7426 */       this.viewport = new BasicJideTabbedPaneUI.ScrollableTabViewport(BasicJideTabbedPaneUI.this);
/* 7427 */       this.tabPanel = new BasicJideTabbedPaneUI.ScrollableTabPanel(BasicJideTabbedPaneUI.this);
/* 7428 */       this.viewport.setView(this.tabPanel);
/* 7429 */       this.viewport.addChangeListener(this);
/*      */ 
/* 7431 */       this.scrollForwardButton = BasicJideTabbedPaneUI.this.createButton(1);
/* 7432 */       this.scrollForwardButton.setName("JideTabbedPane.scrollForward");
/* 7433 */       this.scrollBackwardButton = BasicJideTabbedPaneUI.this.createButton(2);
/* 7434 */       this.scrollBackwardButton.setName("JideTabbedPane.scrollBackward");
/*      */ 
/* 7436 */       this.scrollForwardButton.setBackground(this.viewport.getBackground());
/* 7437 */       this.scrollBackwardButton.setBackground(this.viewport.getBackground());
/*      */ 
/* 7439 */       this.listButton = BasicJideTabbedPaneUI.this.createButton(5);
/* 7440 */       this.listButton.setName("JideTabbedPane.showList");
/* 7441 */       this.listButton.setBackground(this.viewport.getBackground());
/*      */ 
/* 7443 */       this.closeButton = BasicJideTabbedPaneUI.this.createButton(0);
/* 7444 */       this.closeButton.setName("JideTabbedPane.close");
/* 7445 */       this.closeButton.setBackground(this.viewport.getBackground());
/*      */     }
/*      */ 
/*      */     public void createPopupMenu(int tabPlacement) {
/* 7449 */       JPopupMenu popup = new JPopupMenu();
/* 7450 */       int totalCount = BasicJideTabbedPaneUI.this._tabPane.getTabCount();
/*      */ 
/* 7453 */       int selectedIndex = BasicJideTabbedPaneUI.this._tabPane.getSelectedIndex();
/* 7454 */       for (int i = 0; i < totalCount; i++) {
/* 7455 */         if (!BasicJideTabbedPaneUI.this._tabPane.isEnabledAt(i))
/*      */           continue;
/*      */         JMenuItem item;
/* 7457 */         popup.add(item = new JCheckBoxMenuItem(new BasicJideTabbedPaneUI.ActivateTabAction(BasicJideTabbedPaneUI.this, BasicJideTabbedPaneUI.this._tabPane.getTitleAt(i), BasicJideTabbedPaneUI.this._tabPane.getIconForTab(i), i)));
/* 7458 */         item.setToolTipText(BasicJideTabbedPaneUI.this._tabPane.getToolTipTextAt(i));
/* 7459 */         item.setSelected(selectedIndex == i);
/* 7460 */         item.setHorizontalTextPosition(4);
/*      */       }
/*      */ 
/* 7464 */       Dimension preferredSize = popup.getPreferredSize();
/* 7465 */       Rectangle bounds = this.listButton.getBounds();
/* 7466 */       switch (tabPlacement) {
/*      */       case 1:
/* 7468 */         popup.show(BasicJideTabbedPaneUI.this._tabPane, bounds.x + bounds.width - preferredSize.width, bounds.y + bounds.height);
/* 7469 */         break;
/*      */       case 3:
/* 7471 */         popup.show(BasicJideTabbedPaneUI.this._tabPane, bounds.x + bounds.width - preferredSize.width, bounds.y - preferredSize.height);
/* 7472 */         break;
/*      */       case 2:
/* 7474 */         popup.show(BasicJideTabbedPaneUI.this._tabPane, bounds.x + bounds.width, bounds.y + bounds.height - preferredSize.height);
/* 7475 */         break;
/*      */       case 4:
/* 7477 */         popup.show(BasicJideTabbedPaneUI.this._tabPane, bounds.x - preferredSize.width, bounds.y + bounds.height - preferredSize.height);
/*      */       }
/*      */     }
/*      */ 
/*      */     public void createPopup(int tabPlacement)
/*      */     {
/* 7483 */       JList list = new JList()
/*      */       {
/*      */         public void removeSelectionInterval(int index0, int index1)
/*      */         {
/* 7487 */           super.removeSelectionInterval(index0, index1);
/* 7488 */           if (getSelectedIndex() == -1)
/* 7489 */             setSelectedIndex(index0);
/*      */         }
/*      */ 
/*      */         public Dimension getPreferredScrollableViewportSize()
/*      */         {
/* 7495 */           Dimension preferredScrollableViewportSize = super.getPreferredScrollableViewportSize();
/* 7496 */           if (preferredScrollableViewportSize.width < 150) {
/* 7497 */             preferredScrollableViewportSize.width = 150;
/*      */           }
/* 7499 */           int screenWidth = PortingUtils.getScreenSize(this).width;
/* 7500 */           if (preferredScrollableViewportSize.width >= screenWidth) {
/* 7501 */             preferredScrollableViewportSize.width = screenWidth;
/*      */           }
/* 7503 */           return preferredScrollableViewportSize;
/*      */         }
/*      */ 
/*      */         public Dimension getPreferredSize()
/*      */         {
/* 7508 */           Dimension preferredSize = super.getPreferredSize();
/* 7509 */           int screenWidth = PortingUtils.getScreenSize(this).width;
/* 7510 */           if (preferredSize.width >= screenWidth) {
/* 7511 */             preferredSize.width = screenWidth;
/*      */           }
/* 7513 */           return preferredSize;
/*      */         }
/*      */       };
/* 7516 */       new Sticky(list);
/* 7517 */       list.setBackground(BasicJideTabbedPaneUI.this._tabListBackground);
/* 7518 */       JScrollPane scroller = new JScrollPane(list);
/* 7519 */       scroller.setBorder(BorderFactory.createEmptyBorder());
/* 7520 */       scroller.getViewport().setOpaque(false);
/* 7521 */       scroller.setOpaque(false);
/*      */ 
/* 7523 */       JPanel panel = new JPanel(new BorderLayout());
/* 7524 */       panel.setBackground(BasicJideTabbedPaneUI.this._tabListBackground);
/* 7525 */       panel.setOpaque(true);
/* 7526 */       panel.add(scroller);
/* 7527 */       panel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
/*      */ 
/* 7529 */       if (this._popup != null) {
/* 7530 */         if (this._popup.isPopupVisible()) {
/* 7531 */           this._popup.hidePopupImmediately();
/*      */         }
/* 7533 */         this._popup = null;
/*      */       }
/* 7535 */       this._popup = JidePopupFactory.getSharedInstance().createPopup();
/* 7536 */       this._popup.setPopupBorder(BorderFactory.createLineBorder(BasicJideTabbedPaneUI.this._darkShadow));
/* 7537 */       this._popup.add(panel);
/* 7538 */       this._popup.addExcludedComponent(this.listButton);
/* 7539 */       this._popup.setDefaultFocusComponent(list);
/*      */ 
/* 7541 */       DefaultListModel listModel = new DefaultListModel();
/*      */ 
/* 7544 */       int selectedIndex = BasicJideTabbedPaneUI.this._tabPane.getSelectedIndex();
/* 7545 */       int totalCount = BasicJideTabbedPaneUI.this._tabPane.getTabCount();
/* 7546 */       for (int i = 0; i < totalCount; i++) {
/* 7547 */         listModel.addElement(BasicJideTabbedPaneUI.this._tabPane);
/*      */       }
/* 7549 */       list.setCellRenderer(BasicJideTabbedPaneUI.this.getTabListCellRenderer());
/* 7550 */       list.setModel(listModel);
/* 7551 */       list.setSelectedIndex(selectedIndex);
/* 7552 */       list.addKeyListener(new KeyListener(list) {
/*      */         public void keyTyped(KeyEvent e) {
/*      */         }
/*      */ 
/*      */         public void keyPressed(KeyEvent e) {
/* 7557 */           if (e.getKeyCode() == 10)
/* 7558 */             BasicJideTabbedPaneUI.ScrollableTabSupport.this.componentSelected(this.val$list);
/*      */         }
/*      */ 
/*      */         public void keyReleased(KeyEvent e)
/*      */         {
/*      */         }
/*      */       });
/* 7565 */       list.addMouseListener(new MouseAdapter(list)
/*      */       {
/*      */         public void mouseReleased(MouseEvent e) {
/* 7568 */           BasicJideTabbedPaneUI.ScrollableTabSupport.this.componentSelected(this.val$list);
/*      */         }
/*      */       });
/* 7572 */       list.setSelectionMode(0);
/* 7573 */       Insets insets = panel.getInsets();
/* 7574 */       int max = (PortingUtils.getLocalScreenSize(BasicJideTabbedPaneUI.this._tabPane).height - insets.top - insets.bottom) / list.getCellBounds(0, 0).height;
/* 7575 */       if (listModel.getSize() > max) {
/* 7576 */         list.setVisibleRowCount(max);
/*      */       }
/*      */       else {
/* 7579 */         list.setVisibleRowCount(listModel.getSize());
/*      */       }
/*      */ 
/* 7582 */       this._popup.setOwner(BasicJideTabbedPaneUI.this._tabPane);
/* 7583 */       this._popup.removeExcludedComponent(BasicJideTabbedPaneUI.this._tabPane);
/*      */ 
/* 7585 */       Dimension size = this._popup.getPreferredSize();
/* 7586 */       Rectangle bounds = this.listButton.getBounds();
/* 7587 */       Point p = this.listButton.getLocationOnScreen();
/* 7588 */       bounds.x = p.x;
/* 7589 */       bounds.y = p.y;
/*      */       int x;
/*      */       int y;
/*      */       int x;
/* 7592 */       switch (tabPlacement)
/*      */       {
/*      */       case 1:
/*      */       default:
/*      */         int x;
/* 7595 */         if (BasicJideTabbedPaneUI.this._tabPane.getComponentOrientation().isLeftToRight()) {
/* 7596 */           x = bounds.x + bounds.width - size.width;
/*      */         }
/*      */         else {
/* 7599 */           x = bounds.x;
/*      */         }
/* 7601 */         y = bounds.y + bounds.height + 2;
/* 7602 */         break;
/*      */       case 3:
/* 7604 */         if (BasicJideTabbedPaneUI.this._tabPane.getComponentOrientation().isLeftToRight()) {
/* 7605 */           x = bounds.x + bounds.width - size.width;
/*      */         }
/*      */         else {
/* 7608 */           x = bounds.x;
/*      */         }
/* 7610 */         y = bounds.y - size.height - 2;
/* 7611 */         break;
/*      */       case 2:
/* 7613 */         x = bounds.x + bounds.width + 2;
/* 7614 */         y = bounds.y + bounds.height - size.height;
/* 7615 */         break;
/*      */       case 4:
/* 7617 */         x = bounds.x - size.width - 2;
/* 7618 */         y = bounds.y + bounds.height - size.height;
/*      */       }
/*      */ 
/* 7622 */       Rectangle screenBounds = PortingUtils.getScreenBounds(BasicJideTabbedPaneUI.this._tabPane);
/* 7623 */       int right = x + size.width + 3;
/* 7624 */       int bottom = y + size.height + 3;
/*      */ 
/* 7626 */       if (right > screenBounds.x + screenBounds.width) {
/* 7627 */         x -= right - screenBounds.x - screenBounds.width;
/*      */       }
/*      */ 
/* 7630 */       if (x < screenBounds.x) {
/* 7631 */         x = screenBounds.x;
/*      */       }
/*      */ 
/* 7634 */       if (bottom > screenBounds.height) {
/* 7635 */         y -= bottom - screenBounds.height;
/*      */       }
/*      */ 
/* 7638 */       if (y < screenBounds.y) {
/* 7639 */         y = screenBounds.y;
/*      */       }
/*      */ 
/* 7642 */       this._popup.showPopup(x, y);
/*      */     }
/*      */ 
/*      */     private void componentSelected(JList list) {
/* 7646 */       int tabIndex = list.getSelectedIndex();
/* 7647 */       if ((tabIndex != -1) && (BasicJideTabbedPaneUI.this._tabPane.isEnabledAt(tabIndex))) {
/* 7648 */         if ((tabIndex == BasicJideTabbedPaneUI.this._tabPane.getSelectedIndex()) && (JideSwingUtilities.isAncestorOfFocusOwner(BasicJideTabbedPaneUI.this._tabPane))) {
/* 7649 */           if ((BasicJideTabbedPaneUI.this._tabPane.isAutoFocusOnTabHideClose()) && (BasicJideTabbedPaneUI.this._tabPane.isRequestFocusEnabled())) {
/* 7650 */             Runnable runnable = new Runnable() {
/*      */               public void run() {
/* 7652 */                 BasicJideTabbedPaneUI.this._tabPane.requestFocus();
/*      */               }
/*      */             };
/* 7655 */             SwingUtilities.invokeLater(runnable);
/*      */           }
/*      */         }
/*      */         else {
/* 7659 */           BasicJideTabbedPaneUI.this._tabPane.setSelectedIndex(tabIndex);
/* 7660 */           Component comp = BasicJideTabbedPaneUI.this._tabPane.getComponentAt(tabIndex);
/* 7661 */           if ((BasicJideTabbedPaneUI.this._tabPane.isAutoFocusOnTabHideClose()) && (!comp.isVisible()) && (SystemInfo.isJdk15Above()) && (!SystemInfo.isJdk6Above())) {
/* 7662 */             comp.addComponentListener(new ComponentAdapter(comp)
/*      */             {
/*      */               public void componentShown(ComponentEvent e)
/*      */               {
/* 7666 */                 this.val$comp.removeComponentListener(this);
/*      */ 
/* 7668 */                 Component lastFocused = BasicJideTabbedPaneUI.this._tabPane.getLastFocusedComponent(this.val$comp);
/* 7669 */                 Runnable runnable = new Runnable(lastFocused) {
/*      */                   public void run() {
/* 7671 */                     if (this.val$lastFocused != null) {
/* 7672 */                       this.val$lastFocused.requestFocus();
/*      */                     }
/* 7674 */                     else if (BasicJideTabbedPaneUI.this._tabPane.isRequestFocusEnabled())
/* 7675 */                       BasicJideTabbedPaneUI.this._tabPane.requestFocus();
/*      */                   }
/*      */                 };
/* 7679 */                 SwingUtilities.invokeLater(runnable);
/*      */               } } );
/*      */           }
/*      */           else {
/* 7684 */             Component lastFocused = BasicJideTabbedPaneUI.this._tabPane.getLastFocusedComponent(comp);
/* 7685 */             if (lastFocused != null) {
/* 7686 */               Runnable runnable = new Runnable(lastFocused) {
/*      */                 public void run() {
/* 7688 */                   this.val$lastFocused.requestFocus();
/*      */                 }
/*      */               };
/* 7691 */               SwingUtilities.invokeLater(runnable);
/*      */             }
/*      */             else
/*      */             {
/*      */               Container container;
/*      */               Container container;
/* 7695 */               if ((comp instanceof Container)) {
/* 7696 */                 container = (Container)comp;
/*      */               }
/*      */               else {
/* 7699 */                 container = comp.getFocusCycleRootAncestor();
/*      */               }
/* 7701 */               FocusTraversalPolicy traversalPolicy = container.getFocusTraversalPolicy();
/*      */               Component focusComponent;
/* 7703 */               if (traversalPolicy != null) {
/* 7704 */                 Component focusComponent = traversalPolicy.getDefaultComponent(container);
/* 7705 */                 if (focusComponent == null)
/* 7706 */                   focusComponent = traversalPolicy.getFirstComponent(container);
/*      */               }
/*      */               else
/*      */               {
/*      */                 Component focusComponent;
/* 7709 */                 if ((comp instanceof Container))
/*      */                 {
/* 7711 */                   focusComponent = findFocusableComponent((Container)comp);
/*      */                 }
/*      */                 else
/* 7714 */                   focusComponent = comp;
/*      */               }
/* 7716 */               if (focusComponent != null) {
/* 7717 */                 Component theComponent = focusComponent;
/* 7718 */                 Runnable runnable = new Runnable(theComponent) {
/*      */                   public void run() {
/* 7720 */                     this.val$theComponent.requestFocus();
/*      */                   }
/*      */                 };
/* 7723 */                 SwingUtilities.invokeLater(runnable);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/* 7728 */         BasicJideTabbedPaneUI.this.ensureActiveTabIsVisible(false);
/* 7729 */         this._popup.hidePopupImmediately();
/* 7730 */         this._popup = null;
/*      */       }
/*      */     }
/*      */ 
/*      */     private Component findFocusableComponent(Container parent) {
/* 7735 */       FocusTraversalPolicy traversalPolicy = parent.getFocusTraversalPolicy();
/* 7736 */       Component focusComponent = null;
/* 7737 */       if (traversalPolicy != null) {
/* 7738 */         focusComponent = traversalPolicy.getDefaultComponent(parent);
/* 7739 */         if (focusComponent == null) {
/* 7740 */           focusComponent = traversalPolicy.getFirstComponent(parent);
/*      */         }
/*      */       }
/* 7743 */       if (focusComponent != null) {
/* 7744 */         return focusComponent;
/*      */       }
/* 7746 */       int i = 0;
/* 7747 */       while (i < parent.getComponentCount()) {
/* 7748 */         Component comp = parent.getComponent(i);
/* 7749 */         if ((comp instanceof Container)) {
/* 7750 */           focusComponent = findFocusableComponent((Container)comp);
/* 7751 */           if (focusComponent != null) {
/* 7752 */             return focusComponent;
/*      */           }
/*      */         }
/* 7755 */         else if (comp.isFocusable()) {
/* 7756 */           return comp;
/*      */         }
/* 7758 */         i++;
/*      */       }
/* 7760 */       if (parent.isFocusable()) {
/* 7761 */         return parent;
/*      */       }
/* 7763 */       return null;
/*      */     }
/*      */ 
/*      */     public void scrollForward(int tabPlacement) {
/* 7767 */       Dimension viewSize = this.viewport.getViewSize();
/* 7768 */       Rectangle viewRect = this.viewport.getViewRect();
/*      */ 
/* 7770 */       if ((tabPlacement == 1) || (tabPlacement == 3)) {
/* 7771 */         if (viewRect.width >= viewSize.width - viewRect.x) {
/* 7772 */           return;
/*      */         }
/*      */ 
/*      */       }
/* 7776 */       else if (viewRect.height >= viewSize.height - viewRect.y) {
/* 7777 */         return;
/*      */       }
/*      */ 
/* 7780 */       setLeadingTabIndex(tabPlacement, this.leadingTabIndex + 1);
/*      */     }
/*      */ 
/*      */     public void scrollBackward(int tabPlacement) {
/* 7784 */       setLeadingTabIndex(tabPlacement, this.leadingTabIndex > 0 ? this.leadingTabIndex - 1 : 0);
/*      */     }
/*      */ 
/*      */     public void setLeadingTabIndex(int tabPlacement, int index)
/*      */     {
/* 7789 */       if ((index < 0) || (index >= BasicJideTabbedPaneUI.this._tabPane.getTabCount())) {
/* 7790 */         return;
/*      */       }
/* 7792 */       this.leadingTabIndex = index;
/* 7793 */       Dimension viewSize = this.viewport.getViewSize();
/* 7794 */       Rectangle viewRect = this.viewport.getViewRect();
/*      */ 
/* 7796 */       switch (tabPlacement) {
/*      */       case 1:
/*      */       case 3:
/* 7799 */         this.tabViewPosition.y = 0;
/* 7800 */         if (BasicJideTabbedPaneUI.this._tabPane.getComponentOrientation().isLeftToRight()) {
/* 7801 */           this.tabViewPosition.x = (this.leadingTabIndex == 0 ? 0 : BasicJideTabbedPaneUI.this._rects[this.leadingTabIndex].x);
/*      */         }
/*      */         else {
/* 7804 */           this.tabViewPosition.x = ((BasicJideTabbedPaneUI.this._rects.length <= 0) || (this.leadingTabIndex == 0) ? 0 : BasicJideTabbedPaneUI.this._rects[0].x - BasicJideTabbedPaneUI.this._rects[this.leadingTabIndex].x + (BasicJideTabbedPaneUI.this._rects[0].width - BasicJideTabbedPaneUI.this._rects[this.leadingTabIndex].width) + 25);
/*      */         }
/*      */ 
/* 7807 */         if (viewSize.width - this.tabViewPosition.x >= viewRect.width) break;
/* 7808 */         this.tabViewPosition.x = (viewSize.width - viewRect.width); break;
/*      */       case 2:
/*      */       case 4:
/* 7820 */         this.tabViewPosition.x = 0;
/* 7821 */         this.tabViewPosition.y = (this.leadingTabIndex == 0 ? 0 : BasicJideTabbedPaneUI.this._rects[this.leadingTabIndex].y);
/*      */ 
/* 7823 */         if (viewSize.height - this.tabViewPosition.y >= viewRect.height) break;
/* 7824 */         this.tabViewPosition.y = (viewSize.height - viewRect.height);
/*      */       }
/*      */ 
/* 7833 */       this.viewport.setViewPosition(this.tabViewPosition);
/* 7834 */       BasicJideTabbedPaneUI.this._tabPane.repaint();
/* 7835 */       if (((tabPlacement == 1) || (tabPlacement == 3)) && (!BasicJideTabbedPaneUI.this._tabPane.getComponentOrientation().isLeftToRight()) && (this.tabViewPosition.x == 0))
/*      */       {
/* 7837 */         stateChanged(new ChangeEvent(this.viewport));
/*      */       }
/*      */     }
/*      */ 
/*      */     public void stateChanged(ChangeEvent e) {
/* 7842 */       if (BasicJideTabbedPaneUI.this._tabPane == null) return;
/*      */ 
/* 7844 */       BasicJideTabbedPaneUI.this.ensureCurrentLayout();
/*      */ 
/* 7846 */       JViewport viewport = (JViewport)e.getSource();
/* 7847 */       int tabPlacement = BasicJideTabbedPaneUI.this._tabPane.getTabPlacement();
/* 7848 */       int tabCount = BasicJideTabbedPaneUI.this._tabPane.getTabCount();
/* 7849 */       Rectangle vpRect = viewport.getBounds();
/* 7850 */       Dimension viewSize = viewport.getViewSize();
/* 7851 */       Rectangle viewRect = viewport.getViewRect();
/*      */ 
/* 7853 */       if (((tabPlacement == 1) || (tabPlacement == 3)) && (!BasicJideTabbedPaneUI.this._tabPane.getComponentOrientation().isLeftToRight())) {
/* 7854 */         this.leadingTabIndex = BasicJideTabbedPaneUI.this.getClosestTab(viewRect.x + viewRect.width, viewRect.y + viewRect.height);
/* 7855 */         if (this.leadingTabIndex < 0)
/* 7856 */           this.leadingTabIndex = 0;
/*      */       }
/*      */       else
/*      */       {
/* 7860 */         this.leadingTabIndex = BasicJideTabbedPaneUI.this.getClosestTab(viewRect.x, viewRect.y);
/*      */       }
/*      */ 
/* 7864 */       if ((this.leadingTabIndex < BasicJideTabbedPaneUI.this._rects.length) && (this.leadingTabIndex >= BasicJideTabbedPaneUI.this._rects.length)) {
/* 7865 */         switch (tabPlacement) {
/*      */         case 1:
/*      */         case 3:
/* 7868 */           if (BasicJideTabbedPaneUI.this._rects[this.leadingTabIndex].x >= viewRect.x) break;
/* 7869 */           this.leadingTabIndex += 1; break;
/*      */         case 2:
/*      */         case 4:
/* 7874 */           if (BasicJideTabbedPaneUI.this._rects[this.leadingTabIndex].y >= viewRect.y) break;
/* 7875 */           this.leadingTabIndex += 1;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 7882 */       Insets contentInsets = BasicJideTabbedPaneUI.this.getContentBorderInsets(tabPlacement);
/*      */       int checkX;
/* 7884 */       switch (tabPlacement) {
/*      */       case 2:
/* 7886 */         BasicJideTabbedPaneUI.this._tabPane.repaint(vpRect.x + vpRect.width, vpRect.y, contentInsets.left, vpRect.height);
/* 7887 */         this.scrollBackwardButton.setEnabled((viewRect.y > 0) || (this.leadingTabIndex > 0));
/* 7888 */         this.scrollForwardButton.setEnabled((this.leadingTabIndex < tabCount - 1) && (viewSize.height - viewRect.y > viewRect.height));
/* 7889 */         break;
/*      */       case 4:
/* 7891 */         BasicJideTabbedPaneUI.this._tabPane.repaint(vpRect.x - contentInsets.right, vpRect.y, contentInsets.right, vpRect.height);
/* 7892 */         this.scrollBackwardButton.setEnabled((viewRect.y > 0) || (this.leadingTabIndex > 0));
/* 7893 */         this.scrollForwardButton.setEnabled((this.leadingTabIndex < tabCount - 1) && (viewSize.height - viewRect.y > viewRect.height));
/* 7894 */         break;
/*      */       case 3:
/* 7896 */         BasicJideTabbedPaneUI.this._tabPane.repaint(vpRect.x, vpRect.y - contentInsets.bottom, vpRect.width, contentInsets.bottom);
/* 7897 */         this.scrollBackwardButton.setEnabled((viewRect.x > 0) || (this.leadingTabIndex > 0));
/* 7898 */         checkX = BasicJideTabbedPaneUI.this._tabPane.getComponentOrientation().isLeftToRight() ? viewRect.x : BasicJideTabbedPaneUI.this._tabScroller.viewport.getExpectedViewX();
/* 7899 */         this.scrollForwardButton.setEnabled((this.leadingTabIndex < tabCount - 1) && (viewSize.width - checkX > viewRect.width));
/* 7900 */         break;
/*      */       case 1:
/*      */       default:
/* 7903 */         BasicJideTabbedPaneUI.this._tabPane.repaint(vpRect.x, vpRect.y + vpRect.height, vpRect.width, contentInsets.top);
/* 7904 */         this.scrollBackwardButton.setEnabled((viewRect.x > 0) || (this.leadingTabIndex > 0));
/* 7905 */         checkX = BasicJideTabbedPaneUI.this._tabPane.getComponentOrientation().isLeftToRight() ? viewRect.x : BasicJideTabbedPaneUI.this._tabScroller.viewport.getExpectedViewX();
/* 7906 */         this.scrollForwardButton.setEnabled((this.leadingTabIndex < tabCount - 1) && (viewSize.width - checkX > viewRect.width));
/*      */       }
/*      */ 
/* 7909 */       if (SystemInfo.isJdk15Above()) {
/* 7910 */         BasicJideTabbedPaneUI.this._tabPane.setComponentZOrder(BasicJideTabbedPaneUI.this._tabScroller.scrollForwardButton, 0);
/* 7911 */         BasicJideTabbedPaneUI.this._tabPane.setComponentZOrder(BasicJideTabbedPaneUI.this._tabScroller.scrollBackwardButton, 0);
/*      */       }
/* 7913 */       BasicJideTabbedPaneUI.this._tabScroller.scrollForwardButton.repaint();
/* 7914 */       BasicJideTabbedPaneUI.this._tabScroller.scrollBackwardButton.repaint();
/*      */ 
/* 7917 */       int selectedIndex = BasicJideTabbedPaneUI.this._tabPane.getSelectedIndex();
/* 7918 */       if ((selectedIndex >= 0) && (selectedIndex < BasicJideTabbedPaneUI.this._tabPane.getTabCount()))
/* 7919 */         this.closeButton.setEnabled(BasicJideTabbedPaneUI.this._tabPane.isTabClosableAt(selectedIndex));
/*      */     }
/*      */ 
/*      */     public String toString()
/*      */     {
/* 7925 */       return "viewport.viewSize=" + this.viewport.getViewSize() + "\n" + "viewport.viewRectangle=" + this.viewport.getViewRect() + "\n" + "leadingTabIndex=" + this.leadingTabIndex + "\n" + "tabViewPosition=" + this.tabViewPosition;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected class ActivateTabAction extends AbstractAction
/*      */   {
/*      */     int _tabIndex;
/*      */     private static final long serialVersionUID = 3270152106579039554L;
/*      */ 
/*      */     public ActivateTabAction(String name, Icon icon, int tabIndex)
/*      */     {
/* 7399 */       super(icon);
/* 7400 */       this._tabIndex = tabIndex;
/*      */     }
/*      */ 
/*      */     public void actionPerformed(ActionEvent e) {
/* 7404 */       BasicJideTabbedPaneUI.this._tabPane.setSelectedIndex(this._tabIndex);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected class TabbedPaneScrollLayout extends BasicJideTabbedPaneUI.TabbedPaneLayout
/*      */   {
/*      */     protected TabbedPaneScrollLayout()
/*      */     {
/* 6219 */       super();
/*      */     }
/*      */ 
/*      */     protected int preferredTabAreaHeight(int tabPlacement, int width) {
/* 6223 */       return BasicJideTabbedPaneUI.this.calculateMaxTabHeight(tabPlacement);
/*      */     }
/*      */ 
/*      */     protected int preferredTabAreaWidth(int tabPlacement, int height)
/*      */     {
/* 6228 */       return BasicJideTabbedPaneUI.this.calculateMaxTabWidth(tabPlacement);
/*      */     }
/*      */ 
/*      */     public void layoutContainer(Container parent)
/*      */     {
/* 6233 */       int tabPlacement = BasicJideTabbedPaneUI.this._tabPane.getTabPlacement();
/* 6234 */       int tabCount = BasicJideTabbedPaneUI.this._tabPane.getTabCount();
/* 6235 */       Insets insets = BasicJideTabbedPaneUI.this._tabPane.getInsets();
/* 6236 */       int selectedIndex = BasicJideTabbedPaneUI.this._tabPane.getSelectedIndex();
/* 6237 */       Component visibleComponent = BasicJideTabbedPaneUI.this.getVisibleComponent();
/* 6238 */       boolean leftToRight = BasicJideTabbedPaneUI.this._tabPane.getComponentOrientation().isLeftToRight();
/* 6239 */       JViewport viewport = null;
/*      */ 
/* 6241 */       calculateLayoutInfo();
/*      */ 
/* 6243 */       if (selectedIndex < 0) {
/* 6244 */         if (visibleComponent != null)
/*      */         {
/* 6246 */           BasicJideTabbedPaneUI.this.setVisibleComponent(null);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 6251 */         Component selectedComponent = selectedIndex >= BasicJideTabbedPaneUI.this._tabPane.getTabCount() ? null : BasicJideTabbedPaneUI.this._tabPane.getComponentAt(selectedIndex);
/* 6252 */         boolean shouldChangeFocus = false;
/*      */ 
/* 6261 */         if (selectedComponent != null) {
/* 6262 */           if ((selectedComponent != visibleComponent) && (visibleComponent != null) && 
/* 6263 */             (JideSwingUtilities.isAncestorOfFocusOwner(visibleComponent)) && (BasicJideTabbedPaneUI.this._tabPane.isAutoRequestFocus())) {
/* 6264 */             shouldChangeFocus = true;
/*      */           }
/*      */ 
/* 6267 */           BasicJideTabbedPaneUI.this.setVisibleComponent(selectedComponent);
/*      */         }
/*      */ 
/* 6271 */         Insets contentInsets = BasicJideTabbedPaneUI.this.getContentBorderInsets(tabPlacement);
/* 6272 */         Rectangle bounds = BasicJideTabbedPaneUI.this._tabPane.getBounds();
/* 6273 */         int numChildren = BasicJideTabbedPaneUI.this._tabPane.getComponentCount();
/*      */ 
/* 6275 */         Dimension lsize = new Dimension(0, 0);
/* 6276 */         Dimension tsize = new Dimension(0, 0);
/*      */ 
/* 6278 */         if (BasicJideTabbedPaneUI.this.isTabLeadingComponentVisible()) {
/* 6279 */           lsize = BasicJideTabbedPaneUI.this._tabLeadingComponent.getPreferredSize();
/*      */         }
/* 6281 */         if (BasicJideTabbedPaneUI.this.isTabTrailingComponentVisible()) {
/* 6282 */           tsize = BasicJideTabbedPaneUI.this._tabTrailingComponent.getPreferredSize();
/*      */         }
/*      */ 
/* 6285 */         if (numChildren > 0)
/*      */         {
/*      */           int tw;
/*      */           int th;
/*      */           int tx;
/*      */           int ty;
/*      */           int cx;
/*      */           int cy;
/*      */           int cw;
/*      */           int ch;
/* 6286 */           switch (tabPlacement)
/*      */           {
/*      */           case 2:
/* 6289 */             tw = BasicJideTabbedPaneUI.this.calculateTabAreaHeight(1, BasicJideTabbedPaneUI.this._runCount, BasicJideTabbedPaneUI.this._maxTabWidth);
/* 6290 */             th = bounds.height - insets.top - insets.bottom;
/* 6291 */             tx = insets.left;
/* 6292 */             ty = insets.top;
/*      */ 
/* 6294 */             if (BasicJideTabbedPaneUI.this.isTabLeadingComponentVisible()) {
/* 6295 */               ty += lsize.height;
/* 6296 */               th -= lsize.height;
/*      */ 
/* 6298 */               if (lsize.width > tw) {
/* 6299 */                 tw = lsize.width;
/*      */               }
/*      */             }
/* 6302 */             if (BasicJideTabbedPaneUI.this.isTabTrailingComponentVisible()) {
/* 6303 */               th -= tsize.height;
/*      */ 
/* 6305 */               if (tsize.width > tw) {
/* 6306 */                 tw = tsize.width;
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/* 6311 */             cx = tx + tw + contentInsets.left;
/* 6312 */             cy = insets.top + contentInsets.top;
/* 6313 */             cw = bounds.width - insets.left - insets.right - tw - contentInsets.left - contentInsets.right;
/* 6314 */             ch = bounds.height - insets.top - insets.bottom - contentInsets.top - contentInsets.bottom;
/* 6315 */             break;
/*      */           case 4:
/* 6318 */             tw = BasicJideTabbedPaneUI.this.calculateTabAreaHeight(1, BasicJideTabbedPaneUI.this._runCount, BasicJideTabbedPaneUI.this._maxTabWidth);
/*      */ 
/* 6320 */             th = bounds.height - insets.top - insets.bottom;
/* 6321 */             tx = bounds.width - insets.right - tw;
/* 6322 */             ty = insets.top;
/*      */ 
/* 6324 */             if (BasicJideTabbedPaneUI.this.isTabLeadingComponentVisible()) {
/* 6325 */               ty += lsize.height;
/* 6326 */               th -= lsize.height;
/*      */ 
/* 6328 */               if (lsize.width > tw) {
/* 6329 */                 tw = lsize.width;
/* 6330 */                 tx = bounds.width - insets.right - tw;
/*      */               }
/*      */             }
/* 6333 */             if (BasicJideTabbedPaneUI.this.isTabTrailingComponentVisible()) {
/* 6334 */               th -= tsize.height;
/*      */ 
/* 6336 */               if (tsize.width > tw) {
/* 6337 */                 tw = tsize.width;
/* 6338 */                 tx = bounds.width - insets.right - tw;
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/* 6343 */             cx = insets.left + contentInsets.left;
/* 6344 */             cy = insets.top + contentInsets.top;
/* 6345 */             cw = bounds.width - insets.left - insets.right - tw - contentInsets.left - contentInsets.right;
/* 6346 */             ch = bounds.height - insets.top - insets.bottom - contentInsets.top - contentInsets.bottom;
/* 6347 */             break;
/*      */           case 3:
/* 6350 */             tw = bounds.width - insets.left - insets.right;
/* 6351 */             th = BasicJideTabbedPaneUI.this.calculateTabAreaHeight(tabPlacement, BasicJideTabbedPaneUI.this._runCount, BasicJideTabbedPaneUI.this._maxTabHeight);
/*      */ 
/* 6353 */             tx = insets.left;
/* 6354 */             ty = bounds.height - insets.bottom - th;
/*      */ 
/* 6356 */             if (leftToRight) {
/* 6357 */               if (BasicJideTabbedPaneUI.this.isTabLeadingComponentVisible()) {
/* 6358 */                 tx += lsize.width;
/* 6359 */                 tw -= lsize.width;
/*      */ 
/* 6361 */                 if (lsize.height > th) {
/* 6362 */                   th = lsize.height;
/* 6363 */                   ty = bounds.height - insets.bottom - th;
/*      */                 }
/*      */               }
/* 6366 */               if (BasicJideTabbedPaneUI.this.isTabTrailingComponentVisible()) {
/* 6367 */                 tw -= tsize.width;
/*      */ 
/* 6369 */                 if (tsize.height > th) {
/* 6370 */                   th = tsize.height;
/* 6371 */                   ty = bounds.height - insets.bottom - th;
/*      */                 }
/*      */               }
/*      */             }
/*      */             else {
/* 6376 */               if (BasicJideTabbedPaneUI.this.isTabTrailingComponentVisible()) {
/* 6377 */                 tx += tsize.width;
/* 6378 */                 tw -= tsize.width;
/*      */ 
/* 6380 */                 if (tsize.height > th) {
/* 6381 */                   th = tsize.height;
/* 6382 */                   ty = bounds.height - insets.bottom - th;
/*      */                 }
/*      */               }
/* 6385 */               if (BasicJideTabbedPaneUI.this.isTabLeadingComponentVisible()) {
/* 6386 */                 tw -= lsize.width;
/*      */ 
/* 6388 */                 if (lsize.height > th) {
/* 6389 */                   th = lsize.height;
/* 6390 */                   ty = bounds.height - insets.bottom - th;
/*      */                 }
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/* 6396 */             cx = insets.left + contentInsets.left;
/* 6397 */             cy = insets.top + contentInsets.top;
/* 6398 */             cw = bounds.width - insets.left - insets.right - contentInsets.left - contentInsets.right;
/*      */ 
/* 6400 */             ch = bounds.height - insets.top - insets.bottom - th - contentInsets.top - contentInsets.bottom;
/* 6401 */             break;
/*      */           case 1:
/*      */           default:
/* 6405 */             tw = bounds.width - insets.left - insets.right;
/* 6406 */             th = BasicJideTabbedPaneUI.this.calculateTabAreaHeight(tabPlacement, BasicJideTabbedPaneUI.this._runCount, BasicJideTabbedPaneUI.this._maxTabHeight);
/*      */ 
/* 6408 */             tx = insets.left;
/* 6409 */             ty = insets.top;
/*      */ 
/* 6411 */             if (leftToRight) {
/* 6412 */               if (BasicJideTabbedPaneUI.this.isTabLeadingComponentVisible()) {
/* 6413 */                 tx += lsize.width;
/* 6414 */                 tw -= lsize.width;
/*      */ 
/* 6416 */                 if (lsize.height > th) {
/* 6417 */                   th = lsize.height;
/*      */                 }
/*      */               }
/* 6420 */               if (BasicJideTabbedPaneUI.this.isTabTrailingComponentVisible()) {
/* 6421 */                 tw -= tsize.width;
/*      */ 
/* 6423 */                 if (tsize.height > th)
/* 6424 */                   th = tsize.height;
/*      */               }
/*      */             }
/*      */             else
/*      */             {
/* 6429 */               if (BasicJideTabbedPaneUI.this.isTabTrailingComponentVisible()) {
/* 6430 */                 tx += tsize.width;
/* 6431 */                 tw -= tsize.width;
/*      */ 
/* 6433 */                 if (tsize.height > th) {
/* 6434 */                   th = tsize.height;
/*      */                 }
/*      */               }
/* 6437 */               if (BasicJideTabbedPaneUI.this.isTabLeadingComponentVisible()) {
/* 6438 */                 tw -= lsize.width;
/*      */ 
/* 6440 */                 if (lsize.height > th) {
/* 6441 */                   th = lsize.height;
/*      */                 }
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/* 6447 */             cx = insets.left + contentInsets.left;
/* 6448 */             cy = insets.top + th + contentInsets.top;
/* 6449 */             cw = bounds.width - insets.left - insets.right - contentInsets.left - contentInsets.right;
/*      */ 
/* 6451 */             ch = bounds.height - insets.top - insets.bottom - th - contentInsets.top - contentInsets.bottom;
/*      */           }
/*      */ 
/* 6471 */           for (int i = 0; i < numChildren; i++) {
/* 6472 */             Component child = BasicJideTabbedPaneUI.this._tabPane.getComponent(i);
/* 6473 */             boolean tabButtonsVisible = true;
/*      */ 
/* 6475 */             if ((child instanceof BasicJideTabbedPaneUI.ScrollableTabViewport)) {
/* 6476 */               viewport = (JViewport)child;
/*      */ 
/* 6478 */               int vw = tw;
/* 6479 */               int vh = th;
/* 6480 */               int numberOfButtons = BasicJideTabbedPaneUI.this.getNumberOfTabButtons();
/* 6481 */               switch (tabPlacement) {
/*      */               case 2:
/*      */               case 4:
/* 6484 */                 int totalTabHeight = BasicJideTabbedPaneUI.this._rects[(tabCount - 1)].y + BasicJideTabbedPaneUI.this._rects[(tabCount - 1)].height;
/* 6485 */                 if (((totalTabHeight > th) && (BasicJideTabbedPaneUI.this._tabPane.getTabCount() > 1)) || (BasicJideTabbedPaneUI.this.isShowTabButtons())) {
/* 6486 */                   if (!BasicJideTabbedPaneUI.this.isShowTabButtons()) numberOfButtons += 3;
/*      */ 
/* 6488 */                   vh = Math.max(th - BasicJideTabbedPaneUI.this._buttonSize * numberOfButtons, 0);
/*      */                 }
/*      */                 else
/*      */                 {
/* 6499 */                   vh = Math.max(th - BasicJideTabbedPaneUI.this._buttonSize * numberOfButtons, 0);
/* 6500 */                   tabButtonsVisible = false;
/*      */                 }
/*      */ 
/* 6503 */                 if (vh + BasicJideTabbedPaneUI.this.getLayoutSize() >= th - BasicJideTabbedPaneUI.this._buttonSize * numberOfButtons) break;
/* 6504 */                 vh += BasicJideTabbedPaneUI.this.getLayoutSize(); break;
/*      */               case 1:
/*      */               case 3:
/*      */               default:
/* 6510 */                 int totalTabWidth = BasicJideTabbedPaneUI.this._rects[(tabCount - 1)].x + BasicJideTabbedPaneUI.this._rects[(tabCount - 1)].width;
/* 6511 */                 boolean widthEnough = totalTabWidth <= tw;
/* 6512 */                 if ((BasicJideTabbedPaneUI.this.isShowTabButtons()) || ((!widthEnough) && (BasicJideTabbedPaneUI.this._tabPane.getTabCount() > 1))) {
/* 6513 */                   if (!BasicJideTabbedPaneUI.this.isShowTabButtons()) numberOfButtons += 3;
/*      */ 
/* 6515 */                   vw = Math.max(tw - BasicJideTabbedPaneUI.this._buttonSize * numberOfButtons, 0);
/* 6516 */                   if (!leftToRight) {
/* 6517 */                     tx = BasicJideTabbedPaneUI.this._buttonSize * numberOfButtons;
/*      */                   }
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/* 6529 */                   tabButtonsVisible = false;
/*      */ 
/* 6531 */                   vw = Math.max(tw - BasicJideTabbedPaneUI.this._buttonSize * numberOfButtons, 0);
/* 6532 */                   if (!leftToRight) {
/* 6533 */                     tx = BasicJideTabbedPaneUI.this._buttonSize * numberOfButtons;
/*      */                   }
/*      */                 }
/* 6536 */                 if (vw + BasicJideTabbedPaneUI.this.getLayoutSize() >= tw - BasicJideTabbedPaneUI.this._buttonSize * numberOfButtons) break;
/* 6537 */                 vw += BasicJideTabbedPaneUI.this.getLayoutSize();
/* 6538 */                 if (leftToRight) break;
/* 6539 */                 tx -= BasicJideTabbedPaneUI.this.getLayoutSize();
/*      */               }
/*      */ 
/* 6544 */               if ((BasicJideTabbedPaneUI.this._tabTrailingComponent != null) && (BasicJideTabbedPaneUI.this._tabPane.isHideTrailingWhileNoButtons())) {
/* 6545 */                 BasicJideTabbedPaneUI.this._tabTrailingComponent.setVisible(tabButtonsVisible);
/*      */               }
/* 6547 */               child.setBounds(tx, ty, vw, vh);
/*      */             }
/* 6550 */             else if ((child instanceof JideTabbedPane.NoFocusButton)) {
/* 6551 */               JideTabbedPane.NoFocusButton scrollbutton = (JideTabbedPane.NoFocusButton)child;
/* 6552 */               if ((BasicJideTabbedPaneUI.this._tabPane.isTabShown()) && ((scrollbutton.getType() != 0) || (!BasicJideTabbedPaneUI.this.isShowCloseButtonOnTab()))) {
/* 6553 */                 Dimension bsize = scrollbutton.getPreferredSize();
/* 6554 */                 int bx = 0;
/* 6555 */                 int by = 0;
/* 6556 */                 int bw = bsize.width;
/* 6557 */                 int bh = bsize.height;
/* 6558 */                 boolean visible = false;
/*      */                 int temp;
/* 6560 */                 switch (tabPlacement) {
/*      */                 case 2:
/*      */                 case 4:
/* 6563 */                   int totalTabHeight = BasicJideTabbedPaneUI.this._rects[(tabCount - 1)].y + BasicJideTabbedPaneUI.this._rects[(tabCount - 1)].height;
/* 6564 */                   if ((BasicJideTabbedPaneUI.this._tabPane.isTabShown()) && ((BasicJideTabbedPaneUI.this.isShowTabButtons()) || ((totalTabHeight > th) && (BasicJideTabbedPaneUI.this._tabPane.getTabCount() > 1)))) {
/* 6565 */                     int dir = scrollbutton.getType();
/* 6566 */                     scrollbutton.setType(dir);
/* 6567 */                     switch (dir) {
/*      */                     case 0:
/* 6569 */                       if (BasicJideTabbedPaneUI.this.isShowCloseButton()) {
/* 6570 */                         visible = true;
/* 6571 */                         by = bounds.height - insets.top - bsize.height - 5;
/*      */                       }
/*      */                       else {
/* 6574 */                         visible = false;
/* 6575 */                         by = 0;
/*      */                       }
/* 6577 */                       break;
/*      */                     case 5:
/* 6579 */                       visible = true;
/* 6580 */                       by = bounds.height - insets.top - (2 - ((!BasicJideTabbedPaneUI.this.isShowCloseButton()) || (BasicJideTabbedPaneUI.this.isShowCloseButtonOnTab()) ? 1 : 0)) * bsize.height - 5;
/* 6581 */                       break;
/*      */                     case 1:
/* 6583 */                       visible = !BasicJideTabbedPaneUI.this.isShrinkTabs();
/* 6584 */                       by = bounds.height - insets.top - (3 - ((!BasicJideTabbedPaneUI.this.isShowCloseButton()) || (BasicJideTabbedPaneUI.this.isShowCloseButtonOnTab()) ? 1 : 0)) * bsize.height - 5;
/* 6585 */                       break;
/*      */                     case 2:
/* 6587 */                       visible = !BasicJideTabbedPaneUI.this.isShrinkTabs();
/* 6588 */                       by = bounds.height - insets.top - (4 - ((!BasicJideTabbedPaneUI.this.isShowCloseButton()) || (BasicJideTabbedPaneUI.this.isShowCloseButtonOnTab()) ? 1 : 0)) * bsize.height - 5;
/*      */                     case 3:
/*      */                     case 4:
/* 6591 */                     }bx = tx + 2;
/*      */                   }
/*      */                   else
/*      */                   {
/* 6595 */                     tabButtonsVisible = false;
/* 6596 */                     int dir = scrollbutton.getType();
/* 6597 */                     scrollbutton.setType(dir);
/* 6598 */                     if (dir == 0) {
/* 6599 */                       if (BasicJideTabbedPaneUI.this.isShowCloseButton()) {
/* 6600 */                         visible = true;
/* 6601 */                         by = bounds.height - insets.top - bsize.height - 5;
/*      */                       }
/*      */                       else {
/* 6604 */                         visible = false;
/* 6605 */                         by = 0;
/*      */                       }
/* 6607 */                       bx = tx + 2;
/*      */                     }
/*      */                   }
/* 6610 */                   if ((BasicJideTabbedPaneUI.this._tabTrailingComponent != null) && (BasicJideTabbedPaneUI.this._tabPane.isHideTrailingWhileNoButtons())) {
/* 6611 */                     BasicJideTabbedPaneUI.this._tabTrailingComponent.setVisible(tabButtonsVisible);
/*      */                   }
/* 6613 */                   if (BasicJideTabbedPaneUI.this.isTabTrailingComponentVisible()) {
/* 6614 */                     by -= tsize.height;
/*      */                   }
/* 6616 */                   temp = -1;
/* 6617 */                   if ((BasicJideTabbedPaneUI.this.isTabLeadingComponentVisible()) && 
/* 6618 */                     (lsize.width >= BasicJideTabbedPaneUI.this._rects[0].width) && 
/* 6619 */                     (tabPlacement == 2)) {
/* 6620 */                     bx += lsize.width - BasicJideTabbedPaneUI.this._rects[0].width;
/* 6621 */                     temp = lsize.width;
/*      */                   }
/*      */ 
/* 6625 */                   if ((!BasicJideTabbedPaneUI.this.isTabTrailingComponentVisible()) || 
/* 6626 */                     (tsize.width < BasicJideTabbedPaneUI.this._rects[0].width) || (temp >= tsize.width))
/*      */                     break;
/* 6628 */                   if (tabPlacement != 2) break;
/* 6629 */                   bx += tsize.width - BasicJideTabbedPaneUI.this._rects[0].width; break;
/*      */                 case 1:
/*      */                 case 3:
/*      */                 default:
/* 6637 */                   int totalTabWidth = BasicJideTabbedPaneUI.this._rects[(tabCount - 1)].x + BasicJideTabbedPaneUI.this._rects[(tabCount - 1)].width;
/* 6638 */                   boolean widthEnough = totalTabWidth <= tw;
/* 6639 */                   if ((BasicJideTabbedPaneUI.this._tabPane.isTabShown()) && ((BasicJideTabbedPaneUI.this.isShowTabButtons()) || ((!widthEnough) && (BasicJideTabbedPaneUI.this._tabPane.getTabCount() > 1)))) {
/* 6640 */                     int dir = scrollbutton.getType();
/*      */ 
/* 6642 */                     scrollbutton.setType(dir);
/* 6643 */                     switch (dir) {
/*      */                     case 0:
/* 6645 */                       if (BasicJideTabbedPaneUI.this.isShowCloseButton()) {
/* 6646 */                         visible = true;
/* 6647 */                         if (leftToRight) {
/* 6648 */                           bx = bounds.width - insets.left - bsize.width - 5;
/*      */                         }
/*      */                         else
/* 6651 */                           bx = insets.left - 5;
/*      */                       }
/*      */                       else
/*      */                       {
/* 6655 */                         visible = false;
/* 6656 */                         bx = 0;
/*      */                       }
/* 6658 */                       break;
/*      */                     case 5:
/* 6660 */                       visible = true;
/* 6661 */                       if (leftToRight) {
/* 6662 */                         bx = bounds.width - insets.left - (2 - ((!BasicJideTabbedPaneUI.this.isShowCloseButton()) || (BasicJideTabbedPaneUI.this.isShowCloseButtonOnTab()) ? 1 : 0)) * bsize.width - 5;
/*      */                       }
/*      */                       else {
/* 6665 */                         bx = insets.left + (1 - ((!BasicJideTabbedPaneUI.this.isShowCloseButton()) || (BasicJideTabbedPaneUI.this.isShowCloseButtonOnTab()) ? 1 : 0)) * bsize.width + 5;
/*      */                       }
/* 6667 */                       break;
/*      */                     case 1:
/* 6669 */                       visible = !BasicJideTabbedPaneUI.this.isShrinkTabs();
/* 6670 */                       if (leftToRight) {
/* 6671 */                         bx = bounds.width - insets.left - (3 - ((!BasicJideTabbedPaneUI.this.isShowCloseButton()) || (BasicJideTabbedPaneUI.this.isShowCloseButtonOnTab()) ? 1 : 0)) * bsize.width - 5;
/*      */                       }
/*      */                       else {
/* 6674 */                         bx = insets.left + (2 - ((!BasicJideTabbedPaneUI.this.isShowCloseButton()) || (BasicJideTabbedPaneUI.this.isShowCloseButtonOnTab()) ? 1 : 0)) * bsize.width + 5;
/*      */                       }
/* 6676 */                       break;
/*      */                     case 2:
/* 6678 */                       visible = !BasicJideTabbedPaneUI.this.isShrinkTabs();
/* 6679 */                       if (leftToRight) {
/* 6680 */                         bx = bounds.width - insets.left - (4 - ((!BasicJideTabbedPaneUI.this.isShowCloseButton()) || (BasicJideTabbedPaneUI.this.isShowCloseButtonOnTab()) ? 1 : 0)) * bsize.width - 5;
/*      */                       }
/*      */                       else
/* 6683 */                         bx = insets.left + (3 - ((!BasicJideTabbedPaneUI.this.isShowCloseButton()) || (BasicJideTabbedPaneUI.this.isShowCloseButtonOnTab()) ? 1 : 0)) * bsize.width + 5;
/*      */                     case 3:
/*      */                     case 4:
/*      */                     }
/* 6687 */                     by = (th - bsize.height >> 1) + ty;
/*      */                   }
/*      */                   else
/*      */                   {
/* 6691 */                     tabButtonsVisible = false;
/* 6692 */                     int dir = scrollbutton.getType();
/* 6693 */                     scrollbutton.setType(dir);
/* 6694 */                     if (dir == 0) {
/* 6695 */                       if (BasicJideTabbedPaneUI.this.isShowCloseButton()) {
/* 6696 */                         visible = true;
/* 6697 */                         bx = bounds.width - insets.left - bsize.width - 5;
/*      */                       }
/*      */                       else {
/* 6700 */                         visible = false;
/* 6701 */                         bx = 0;
/*      */                       }
/* 6703 */                       by = (th - bsize.height >> 1) + ty;
/*      */                     }
/*      */                   }
/* 6706 */                   if ((BasicJideTabbedPaneUI.this._tabTrailingComponent != null) && (BasicJideTabbedPaneUI.this._tabPane.isHideTrailingWhileNoButtons())) {
/* 6707 */                     BasicJideTabbedPaneUI.this._tabTrailingComponent.setVisible(tabButtonsVisible);
/*      */                   }
/* 6709 */                   if (BasicJideTabbedPaneUI.this.isTabTrailingComponentVisible()) {
/* 6710 */                     bx -= tsize.width;
/*      */                   }
/* 6712 */                   temp = -1;
/* 6713 */                   if ((BasicJideTabbedPaneUI.this.isTabLeadingComponentVisible()) && 
/* 6714 */                     (lsize.height >= BasicJideTabbedPaneUI.this._rects[0].height)) {
/* 6715 */                     if (tabPlacement == 1) {
/* 6716 */                       by = ty + 2 + lsize.height - BasicJideTabbedPaneUI.this._rects[0].height;
/* 6717 */                       temp = lsize.height;
/*      */                     }
/*      */                     else {
/* 6720 */                       by = ty + 2;
/*      */                     }
/*      */                   }
/*      */ 
/* 6724 */                   if ((!BasicJideTabbedPaneUI.this.isTabTrailingComponentVisible()) || 
/* 6725 */                     (tsize.height < BasicJideTabbedPaneUI.this._rects[0].height) || (temp >= tsize.height))
/*      */                     break;
/* 6727 */                   if (tabPlacement == 1) {
/* 6728 */                     by = ty + 2 + tsize.height - BasicJideTabbedPaneUI.this._rects[0].height;
/*      */                   }
/*      */                   else {
/* 6731 */                     by = ty + 2;
/*      */                   }
/*      */ 
/*      */                 }
/*      */ 
/* 6738 */                 child.setVisible(visible);
/* 6739 */                 if (visible)
/* 6740 */                   child.setBounds(bx, by, bw, bh);
/*      */               }
/*      */               else
/*      */               {
/* 6744 */                 scrollbutton.setBounds(0, 0, 0, 0);
/*      */               }
/*      */             }
/* 6747 */             else if ((child != BasicJideTabbedPaneUI.this._tabPane.getTabLeadingComponent()) && (child != BasicJideTabbedPaneUI.this._tabPane.getTabTrailingComponent())) {
/* 6748 */               if (BasicJideTabbedPaneUI.this._tabPane.isShowTabContent())
/*      */               {
/* 6750 */                 child.setBounds(cx, cy, cw, ch);
/*      */               }
/*      */               else {
/* 6753 */                 child.setBounds(0, 0, 0, 0);
/*      */               }
/*      */             }
/*      */           }
/*      */ 
/* 6758 */           if (leftToRight) {
/* 6759 */             if (BasicJideTabbedPaneUI.this.isTabLeadingComponentVisible()) {
/* 6760 */               switch (BasicJideTabbedPaneUI.this._tabPane.getTabPlacement()) {
/*      */               case 2:
/* 6762 */                 BasicJideTabbedPaneUI.this._tabLeadingComponent.setBounds(tx + tw - lsize.width, ty - lsize.height, lsize.width, lsize.height);
/* 6763 */                 break;
/*      */               case 4:
/* 6765 */                 BasicJideTabbedPaneUI.this._tabLeadingComponent.setBounds(tx, ty - lsize.height, lsize.width, lsize.height);
/* 6766 */                 break;
/*      */               case 3:
/* 6768 */                 BasicJideTabbedPaneUI.this._tabLeadingComponent.setBounds(tx - lsize.width, ty, lsize.width, lsize.height);
/* 6769 */                 break;
/*      */               case 1:
/*      */               default:
/* 6772 */                 BasicJideTabbedPaneUI.this._tabLeadingComponent.setBounds(tx - lsize.width, ty + th - lsize.height, lsize.width, lsize.height);
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/* 6778 */             if (BasicJideTabbedPaneUI.this.isTabTrailingComponentVisible())
/* 6779 */               switch (BasicJideTabbedPaneUI.this._tabPane.getTabPlacement()) {
/*      */               case 2:
/* 6781 */                 BasicJideTabbedPaneUI.this._tabTrailingComponent.setBounds(tx + tw - tsize.width, ty + th, tsize.width, tsize.height);
/* 6782 */                 break;
/*      */               case 4:
/* 6784 */                 BasicJideTabbedPaneUI.this._tabTrailingComponent.setBounds(tx, ty + th, tsize.width, tsize.height);
/* 6785 */                 break;
/*      */               case 3:
/* 6787 */                 BasicJideTabbedPaneUI.this._tabTrailingComponent.setBounds(tx + tw, ty, tsize.width, tsize.height);
/* 6788 */                 break;
/*      */               case 1:
/*      */               default:
/* 6791 */                 BasicJideTabbedPaneUI.this._tabTrailingComponent.setBounds(tx + tw, ty + th - tsize.height, tsize.width, tsize.height);
/* 6792 */                 break;
/*      */               }
/*      */           }
/*      */           else
/*      */           {
/* 6797 */             if (BasicJideTabbedPaneUI.this.isTabTrailingComponentVisible()) {
/* 6798 */               switch (BasicJideTabbedPaneUI.this._tabPane.getTabPlacement()) {
/*      */               case 2:
/* 6800 */                 BasicJideTabbedPaneUI.this._tabTrailingComponent.setBounds(tx + tw - tsize.width, ty - tsize.height, tsize.width, tsize.height);
/* 6801 */                 break;
/*      */               case 4:
/* 6803 */                 BasicJideTabbedPaneUI.this._tabTrailingComponent.setBounds(tx, ty - tsize.height, tsize.width, tsize.height);
/* 6804 */                 break;
/*      */               case 3:
/* 6806 */                 BasicJideTabbedPaneUI.this._tabTrailingComponent.setBounds(tx - tsize.width, ty, tsize.width, tsize.height);
/* 6807 */                 break;
/*      */               case 1:
/*      */               default:
/* 6810 */                 BasicJideTabbedPaneUI.this._tabTrailingComponent.setBounds(tx - tsize.width, ty + th - tsize.height, tsize.width, tsize.height);
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/* 6816 */             if (BasicJideTabbedPaneUI.this.isTabLeadingComponentVisible()) {
/* 6817 */               switch (BasicJideTabbedPaneUI.this._tabPane.getTabPlacement()) {
/*      */               case 2:
/* 6819 */                 BasicJideTabbedPaneUI.this._tabLeadingComponent.setBounds(tx + tw - lsize.width, ty + th, lsize.width, lsize.height);
/* 6820 */                 break;
/*      */               case 4:
/* 6822 */                 BasicJideTabbedPaneUI.this._tabLeadingComponent.setBounds(tx, ty + th, lsize.width, lsize.height);
/* 6823 */                 break;
/*      */               case 3:
/* 6825 */                 BasicJideTabbedPaneUI.this._tabLeadingComponent.setBounds(tx + tw, ty, lsize.width, lsize.height);
/* 6826 */                 break;
/*      */               case 1:
/*      */               default:
/* 6829 */                 BasicJideTabbedPaneUI.this._tabLeadingComponent.setBounds(tx + tw, ty + th - lsize.height, lsize.width, lsize.height);
/*      */               }
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 6835 */           boolean verticalTabRuns = (tabPlacement == 2) || (tabPlacement == 4);
/*      */           Rectangle firstButtonRect;
/*      */           Rectangle trailingCompRect;
/*      */           int offset;
/* 6836 */           if ((BasicJideTabbedPaneUI.this.isTabTrailingComponentVisible()) && (BasicJideTabbedPaneUI.this._tabPane.isLayoutTrailingComponentBeforeButtons())) {
/* 6837 */             List buttons = new ArrayList();
/* 6838 */             for (int i = 0; i < numChildren; i++) {
/* 6839 */               Component child = BasicJideTabbedPaneUI.this._tabPane.getComponent(i);
/* 6840 */               if (((child instanceof JButton)) && ((child instanceof UIResource)) && (child != BasicJideTabbedPaneUI.this._tabLeadingComponent) && (child != BasicJideTabbedPaneUI.this._tabTrailingComponent) && (child.isVisible()) && (child.getBounds().width != 0)) {
/* 6841 */                 buttons.add((JButton)child);
/*      */               }
/*      */             }
/* 6844 */             if (buttons.size() > 0) {
/* 6845 */               Rectangle lastButtonRect = ((JButton)buttons.get(buttons.size() - 1)).getBounds();
/* 6846 */               firstButtonRect = ((JButton)buttons.get(0)).getBounds();
/* 6847 */               trailingCompRect = BasicJideTabbedPaneUI.this._tabTrailingComponent.getBounds();
/*      */               int offset;
/* 6848 */               if (!verticalTabRuns) {
/* 6849 */                 BasicJideTabbedPaneUI.this._tabTrailingComponent.setBounds(new Rectangle(firstButtonRect.x, trailingCompRect.y, trailingCompRect.width, trailingCompRect.height));
/* 6850 */                 offset = BasicJideTabbedPaneUI.this._tabTrailingComponent.getWidth() - (lastButtonRect.x + lastButtonRect.width - firstButtonRect.x);
/* 6851 */                 for (JButton button : buttons) {
/* 6852 */                   Rectangle buttonRect = button.getBounds();
/* 6853 */                   button.setBounds(trailingCompRect.x + offset + buttonRect.x - firstButtonRect.x, buttonRect.y, buttonRect.width, buttonRect.height);
/*      */                 }
/*      */               }
/*      */               else {
/* 6857 */                 BasicJideTabbedPaneUI.this._tabTrailingComponent.setBounds(new Rectangle(trailingCompRect.x, firstButtonRect.y, trailingCompRect.width, trailingCompRect.height));
/* 6858 */                 offset = BasicJideTabbedPaneUI.this._tabTrailingComponent.getHeight() - (lastButtonRect.y + lastButtonRect.height - firstButtonRect.y);
/* 6859 */                 for (JButton button : buttons) {
/* 6860 */                   Rectangle buttonRect = button.getBounds();
/* 6861 */                   button.setBounds(buttonRect.x, trailingCompRect.y + offset + buttonRect.y - firstButtonRect.y, buttonRect.width, buttonRect.height);
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/* 6866 */           if ((!leftToRight) && (!verticalTabRuns) && (viewport != null) && (!viewport.getSize().equals(BasicJideTabbedPaneUI.this._tabPane.getSize()))) {
/* 6867 */             int offset = BasicJideTabbedPaneUI.this._tabPane.getWidth() - viewport.getWidth();
/* 6868 */             for (Rectangle rect : BasicJideTabbedPaneUI.this._rects) {
/* 6869 */               rect.x -= offset;
/*      */             }
/*      */           }
/* 6872 */           BasicJideTabbedPaneUI.this.updateCloseButtons();
/*      */ 
/* 6874 */           if ((shouldChangeFocus) && 
/* 6875 */             (!BasicJideTabbedPaneUI.this.requestFocusForVisibleComponent()) && 
/* 6876 */             (!BasicJideTabbedPaneUI.this._tabPane.requestFocusInWindow()))
/* 6877 */             BasicJideTabbedPaneUI.this._tabPane.requestFocus();
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     protected void calculateTabRects(int tabPlacement, int tabCount)
/*      */     {
/* 6887 */       Dimension size = BasicJideTabbedPaneUI.this._tabPane.getSize();
/* 6888 */       Insets insets = BasicJideTabbedPaneUI.this._tabPane.getInsets();
/* 6889 */       Insets tabAreaInsets = BasicJideTabbedPaneUI.this.getTabAreaInsets(tabPlacement);
/* 6890 */       boolean verticalTabRuns = (tabPlacement == 2) || (tabPlacement == 4);
/* 6891 */       boolean leftToRight = BasicJideTabbedPaneUI.this._tabPane.getComponentOrientation().isLeftToRight();
/* 6892 */       int x = tabAreaInsets.left;
/* 6893 */       int y = tabAreaInsets.top;
/*      */ 
/* 6898 */       Dimension lsize = new Dimension(0, 0);
/* 6899 */       Dimension tsize = new Dimension(0, 0);
/*      */ 
/* 6901 */       if (BasicJideTabbedPaneUI.this.isTabLeadingComponentVisible()) {
/* 6902 */         lsize = BasicJideTabbedPaneUI.this._tabLeadingComponent.getPreferredSize();
/*      */       }
/* 6904 */       if (BasicJideTabbedPaneUI.this.isTabTrailingComponentVisible()) {
/* 6905 */         tsize = BasicJideTabbedPaneUI.this._tabTrailingComponent.getPreferredSize();
/*      */       }
/*      */ 
/* 6908 */       switch (tabPlacement) {
/*      */       case 2:
/*      */       case 4:
/* 6911 */         BasicJideTabbedPaneUI.this._maxTabWidth = BasicJideTabbedPaneUI.this.calculateMaxTabWidth(tabPlacement);
/* 6912 */         if ((BasicJideTabbedPaneUI.this.isTabLeadingComponentVisible()) && 
/* 6913 */           (tabPlacement == 4) && 
/* 6914 */           (BasicJideTabbedPaneUI.this._maxTabWidth < lsize.width)) {
/* 6915 */           BasicJideTabbedPaneUI.this._maxTabWidth = lsize.width;
/*      */         }
/*      */ 
/* 6919 */         if ((!BasicJideTabbedPaneUI.this.isTabTrailingComponentVisible()) || 
/* 6920 */           (tabPlacement != 4) || 
/* 6921 */           (BasicJideTabbedPaneUI.this._maxTabWidth >= tsize.width)) break;
/* 6922 */         BasicJideTabbedPaneUI.this._maxTabWidth = tsize.width; break;
/*      */       case 1:
/*      */       case 3:
/*      */       default:
/* 6930 */         BasicJideTabbedPaneUI.this._maxTabHeight = BasicJideTabbedPaneUI.this.calculateMaxTabHeight(tabPlacement);
/* 6931 */         if ((BasicJideTabbedPaneUI.this.isTabLeadingComponentVisible()) && 
/* 6932 */           (tabPlacement == 3) && 
/* 6933 */           (BasicJideTabbedPaneUI.this._maxTabHeight < lsize.height)) {
/* 6934 */           BasicJideTabbedPaneUI.this._maxTabHeight = lsize.height;
/*      */         }
/*      */ 
/* 6938 */         if ((!BasicJideTabbedPaneUI.this.isTabTrailingComponentVisible()) || 
/* 6939 */           (tabPlacement != 3) || 
/* 6940 */           (BasicJideTabbedPaneUI.this._maxTabHeight >= tsize.height)) break;
/* 6941 */         BasicJideTabbedPaneUI.this._maxTabHeight = tsize.height;
/*      */       }
/*      */ 
/* 6947 */       BasicJideTabbedPaneUI.this._runCount = 0;
/* 6948 */       BasicJideTabbedPaneUI.this._selectedRun = -1;
/*      */ 
/* 6950 */       if (tabCount == 0) {
/* 6951 */         return;
/*      */       }
/*      */ 
/* 6954 */       BasicJideTabbedPaneUI.this._selectedRun = 0;
/* 6955 */       BasicJideTabbedPaneUI.this._runCount = 1;
/*      */ 
/* 6959 */       for (int i = 0; i < tabCount; i++) {
/* 6960 */         FontMetrics metrics = BasicJideTabbedPaneUI.this.getFontMetrics(i);
/* 6961 */         Rectangle rect = BasicJideTabbedPaneUI.this._rects[i];
/*      */ 
/* 6963 */         if (!verticalTabRuns)
/*      */         {
/* 6965 */           if (i > 0) {
/* 6966 */             rect.x = (BasicJideTabbedPaneUI.this._rects[(i - 1)].x + BasicJideTabbedPaneUI.this._rects[(i - 1)].width);
/*      */           }
/*      */           else {
/* 6969 */             BasicJideTabbedPaneUI.this._tabRuns[0] = 0;
/* 6970 */             BasicJideTabbedPaneUI.this._maxTabWidth = 0;
/* 6971 */             if (BasicJideTabbedPaneUI.this.getTabShape() != 3) {
/* 6972 */               rect.x = (x + BasicJideTabbedPaneUI.this.getLeftMargin());
/*      */             }
/*      */             else {
/* 6975 */               rect.x = x;
/*      */             }
/*      */           }
/* 6978 */           rect.width = (BasicJideTabbedPaneUI.this.calculateTabWidth(tabPlacement, i, metrics) + BasicJideTabbedPaneUI.this._rectSizeExtend);
/* 6979 */           BasicJideTabbedPaneUI.this._maxTabWidth = Math.max(BasicJideTabbedPaneUI.this._maxTabWidth, rect.width);
/*      */ 
/* 6981 */           rect.y = y;
/*      */ 
/* 6983 */           int temp = -1;
/* 6984 */           if ((BasicJideTabbedPaneUI.this.isTabLeadingComponentVisible()) && 
/* 6985 */             (tabPlacement == 1) && 
/* 6986 */             (BasicJideTabbedPaneUI.this._maxTabHeight < lsize.height)) {
/* 6987 */             rect.y = (y + lsize.height - BasicJideTabbedPaneUI.this._maxTabHeight - 2);
/* 6988 */             temp = lsize.height;
/*      */ 
/* 6990 */             if (BasicJideTabbedPaneUI.this._rectSizeExtend > 0) {
/* 6991 */               rect.y += 2;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 6997 */           if ((BasicJideTabbedPaneUI.this.isTabTrailingComponentVisible()) && 
/* 6998 */             (tabPlacement == 1) && 
/* 6999 */             (BasicJideTabbedPaneUI.this._maxTabHeight < tsize.height) && (temp < tsize.height))
/*      */           {
/* 7001 */             rect.y = (y + tsize.height - BasicJideTabbedPaneUI.this._maxTabHeight - 2);
/*      */ 
/* 7003 */             if (BasicJideTabbedPaneUI.this._rectSizeExtend > 0) {
/* 7004 */               rect.y += 2;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 7010 */           rect.height = BasicJideTabbedPaneUI.this.calculateMaxTabHeight(tabPlacement);
/*      */         }
/*      */         else
/*      */         {
/* 7014 */           if (i > 0) {
/* 7015 */             rect.y = (BasicJideTabbedPaneUI.this._rects[(i - 1)].y + BasicJideTabbedPaneUI.this._rects[(i - 1)].height);
/*      */           }
/*      */           else {
/* 7018 */             BasicJideTabbedPaneUI.this._tabRuns[0] = 0;
/* 7019 */             BasicJideTabbedPaneUI.this._maxTabHeight = 0;
/* 7020 */             if (BasicJideTabbedPaneUI.this.getTabShape() != 3) {
/* 7021 */               rect.y = (y + BasicJideTabbedPaneUI.this.getLeftMargin());
/*      */             }
/*      */             else {
/* 7024 */               rect.y = y;
/*      */             }
/*      */           }
/* 7027 */           rect.height = (BasicJideTabbedPaneUI.this.calculateTabHeight(tabPlacement, i, metrics) + BasicJideTabbedPaneUI.this._rectSizeExtend);
/* 7028 */           BasicJideTabbedPaneUI.this._maxTabHeight = Math.max(BasicJideTabbedPaneUI.this._maxTabHeight, rect.height);
/*      */ 
/* 7030 */           rect.x = x;
/* 7031 */           int temp = -1;
/* 7032 */           if ((BasicJideTabbedPaneUI.this.isTabLeadingComponentVisible()) && 
/* 7033 */             (tabPlacement == 2) && 
/* 7034 */             (BasicJideTabbedPaneUI.this._maxTabWidth < lsize.width)) {
/* 7035 */             rect.x = (x + lsize.width - BasicJideTabbedPaneUI.this._maxTabWidth - 2);
/* 7036 */             temp = lsize.width;
/*      */ 
/* 7038 */             if (BasicJideTabbedPaneUI.this._rectSizeExtend > 0) {
/* 7039 */               rect.x += 2;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 7045 */           if ((BasicJideTabbedPaneUI.this.isTabTrailingComponentVisible()) && 
/* 7046 */             (tabPlacement == 2) && 
/* 7047 */             (BasicJideTabbedPaneUI.this._maxTabWidth < tsize.width) && (temp < tsize.width))
/*      */           {
/* 7049 */             rect.x = (x + tsize.width - BasicJideTabbedPaneUI.this._maxTabWidth - 2);
/*      */ 
/* 7051 */             if (BasicJideTabbedPaneUI.this._rectSizeExtend > 0) {
/* 7052 */               rect.x += 2;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 7058 */           rect.width = BasicJideTabbedPaneUI.this.calculateMaxTabWidth(tabPlacement);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 7065 */       if ((!leftToRight) && (!verticalTabRuns)) {
/* 7066 */         int rightMargin = size.width - (insets.right + tabAreaInsets.right) - BasicJideTabbedPaneUI.this._tabScroller.viewport.getLocation().x;
/*      */ 
/* 7068 */         if (BasicJideTabbedPaneUI.this.isTabLeadingComponentVisible()) {
/* 7069 */           rightMargin -= lsize.width;
/*      */         }
/* 7071 */         int offset = 0;
/* 7072 */         if (BasicJideTabbedPaneUI.this.isTabTrailingComponentVisible()) {
/* 7073 */           offset += tsize.width;
/*      */         }
/* 7075 */         for (int i = 0; i < tabCount; i++) {
/* 7076 */           BasicJideTabbedPaneUI.this._rects[i].x = (rightMargin - BasicJideTabbedPaneUI.this._rects[i].x - BasicJideTabbedPaneUI.this._rects[i].width - offset + tabAreaInsets.left);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 7084 */       BasicJideTabbedPaneUI.this.ensureCurrentRects(BasicJideTabbedPaneUI.this.getLeftMargin(), tabCount);
/*      */     }
/*      */   }
/*      */ 
/*      */   public class TabbedPaneLayout
/*      */     implements LayoutManager
/*      */   {
/*      */     public TabbedPaneLayout()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void addLayoutComponent(String name, Component comp)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void removeLayoutComponent(Component comp)
/*      */     {
/*      */     }
/*      */ 
/*      */     public Dimension preferredLayoutSize(Container parent)
/*      */     {
/* 5643 */       return calculateSize(false);
/*      */     }
/*      */ 
/*      */     public Dimension minimumLayoutSize(Container parent) {
/* 5647 */       return calculateSize(true);
/*      */     }
/*      */ 
/*      */     protected Dimension calculateSize(boolean minimum) {
/* 5651 */       int tabPlacement = BasicJideTabbedPaneUI.this._tabPane.getTabPlacement();
/* 5652 */       Insets insets = BasicJideTabbedPaneUI.this._tabPane.getInsets();
/* 5653 */       Insets contentInsets = BasicJideTabbedPaneUI.this.getContentBorderInsets(tabPlacement);
/* 5654 */       Insets tabAreaInsets = BasicJideTabbedPaneUI.this.getTabAreaInsets(tabPlacement);
/*      */ 
/* 5656 */       Dimension zeroSize = new Dimension(0, 0);
/* 5657 */       int height = contentInsets.top + contentInsets.bottom;
/* 5658 */       int width = contentInsets.left + contentInsets.right;
/* 5659 */       int cWidth = 0;
/* 5660 */       int cHeight = 0;
/*      */ 
/* 5662 */       synchronized (this) {
/* 5663 */         BasicJideTabbedPaneUI.this.ensureCloseButtonCreated();
/* 5664 */         calculateLayoutInfo();
/*      */ 
/* 5670 */         if (BasicJideTabbedPaneUI.this._tabPane.isShowTabContent()) {
/* 5671 */           for (int i = 0; i < BasicJideTabbedPaneUI.this._tabPane.getTabCount(); i++) {
/* 5672 */             Component component = BasicJideTabbedPaneUI.this._tabPane.getComponentAt(i);
/* 5673 */             if (component != null) {
/* 5674 */               Dimension size = zeroSize;
/* 5675 */               size = minimum ? component.getMinimumSize() : component.getPreferredSize();
/*      */ 
/* 5678 */               if (size != null) {
/* 5679 */                 cHeight = Math.max(size.height, cHeight);
/* 5680 */                 cWidth = Math.max(size.width, cWidth);
/*      */               }
/*      */             }
/*      */           }
/*      */ 
/* 5685 */           width += cWidth;
/* 5686 */           height += cHeight;
/*      */         }
/*      */ 
/* 5694 */         Dimension lsize = new Dimension(0, 0);
/* 5695 */         Dimension tsize = new Dimension(0, 0);
/*      */ 
/* 5697 */         if (BasicJideTabbedPaneUI.this.isTabLeadingComponentVisible()) {
/* 5698 */           lsize = BasicJideTabbedPaneUI.this._tabLeadingComponent.getPreferredSize();
/*      */         }
/* 5700 */         if (BasicJideTabbedPaneUI.this.isTabTrailingComponentVisible())
/* 5701 */           tsize = BasicJideTabbedPaneUI.this._tabTrailingComponent.getPreferredSize();
/*      */         int tabExtent;
/* 5704 */         switch (tabPlacement) {
/*      */         case 2:
/*      */         case 4:
/* 5707 */           height = Math.max(height, (minimum ? 0 : BasicJideTabbedPaneUI.this.calculateMaxTabHeight(tabPlacement)) + tabAreaInsets.top + tabAreaInsets.bottom);
/* 5708 */           tabExtent = BasicJideTabbedPaneUI.this.calculateTabAreaWidth(tabPlacement, BasicJideTabbedPaneUI.this._runCount, BasicJideTabbedPaneUI.this._maxTabWidth);
/*      */ 
/* 5710 */           if (BasicJideTabbedPaneUI.this.isTabLeadingComponentVisible()) {
/* 5711 */             tabExtent = Math.max(lsize.width, tabExtent);
/*      */           }
/* 5713 */           if (BasicJideTabbedPaneUI.this.isTabTrailingComponentVisible()) {
/* 5714 */             tabExtent = Math.max(tsize.width, tabExtent);
/*      */           }
/*      */ 
/* 5717 */           width += tabExtent;
/* 5718 */           break;
/*      */         case 1:
/*      */         case 3:
/*      */         default:
/* 5722 */           if (BasicJideTabbedPaneUI.this._tabPane.getTabResizeMode() == 2) {
/* 5723 */             width = Math.max(width, (BasicJideTabbedPaneUI.this._tabPane.getTabCount() << 2) + tabAreaInsets.left + tabAreaInsets.right);
/*      */           }
/*      */           else
/*      */           {
/* 5727 */             width = Math.max(width, (minimum ? 0 : BasicJideTabbedPaneUI.this.calculateMaxTabWidth(tabPlacement)) + tabAreaInsets.left + tabAreaInsets.right);
/*      */           }
/*      */ 
/* 5731 */           if (!BasicJideTabbedPaneUI.this._tabPane.isTabShown()) break;
/* 5732 */           tabExtent = BasicJideTabbedPaneUI.this.calculateTabAreaHeight(tabPlacement, BasicJideTabbedPaneUI.this._runCount, BasicJideTabbedPaneUI.this._maxTabHeight);
/*      */ 
/* 5734 */           if (BasicJideTabbedPaneUI.this.isTabLeadingComponentVisible()) {
/* 5735 */             tabExtent = Math.max(lsize.height, tabExtent);
/*      */           }
/* 5737 */           if (BasicJideTabbedPaneUI.this.isTabTrailingComponentVisible()) {
/* 5738 */             tabExtent = Math.max(tsize.height, tabExtent);
/*      */           }
/*      */ 
/* 5741 */           height += tabExtent;
/*      */         }
/*      */       }
/*      */ 
/* 5745 */       return new Dimension(width + insets.left + insets.right, height + insets.bottom + insets.top);
/*      */     }
/*      */ 
/*      */     protected int preferredTabAreaHeight(int tabPlacement, int width)
/*      */     {
/* 5751 */       int tabCount = BasicJideTabbedPaneUI.this._tabPane.getTabCount();
/* 5752 */       int total = 0;
/* 5753 */       if (tabCount > 0) {
/* 5754 */         int rows = 1;
/* 5755 */         int x = 0;
/*      */ 
/* 5757 */         int maxTabHeight = BasicJideTabbedPaneUI.this.calculateMaxTabHeight(tabPlacement);
/*      */ 
/* 5759 */         for (int i = 0; i < tabCount; i++) {
/* 5760 */           FontMetrics metrics = BasicJideTabbedPaneUI.this.getFontMetrics(i);
/* 5761 */           int tabWidth = BasicJideTabbedPaneUI.this.calculateTabWidth(tabPlacement, i, metrics);
/*      */ 
/* 5763 */           if ((x != 0) && (x + tabWidth > width)) {
/* 5764 */             rows++;
/* 5765 */             x = 0;
/*      */           }
/* 5767 */           x += tabWidth;
/*      */         }
/* 5769 */         total = BasicJideTabbedPaneUI.this.calculateTabAreaHeight(tabPlacement, rows, maxTabHeight);
/*      */       }
/* 5771 */       return total;
/*      */     }
/*      */ 
/*      */     protected int preferredTabAreaWidth(int tabPlacement, int height) {
/* 5775 */       int tabCount = BasicJideTabbedPaneUI.this._tabPane.getTabCount();
/* 5776 */       int total = 0;
/* 5777 */       if (tabCount > 0) {
/* 5778 */         int columns = 1;
/* 5779 */         int y = 0;
/*      */ 
/* 5781 */         BasicJideTabbedPaneUI.this._maxTabWidth = BasicJideTabbedPaneUI.this.calculateMaxTabWidth(tabPlacement);
/*      */ 
/* 5783 */         for (int i = 0; i < tabCount; i++) {
/* 5784 */           FontMetrics metrics = BasicJideTabbedPaneUI.this.getFontMetrics(i);
/* 5785 */           int tabHeight = BasicJideTabbedPaneUI.this.calculateTabHeight(tabPlacement, i, metrics);
/* 5786 */           if ((y != 0) && (y + tabHeight > height)) {
/* 5787 */             columns++;
/* 5788 */             y = 0;
/*      */           }
/* 5790 */           y += tabHeight;
/*      */         }
/* 5792 */         total = BasicJideTabbedPaneUI.this.calculateTabAreaWidth(tabPlacement, columns, BasicJideTabbedPaneUI.this._maxTabWidth);
/*      */       }
/* 5794 */       return total;
/*      */     }
/*      */ 
/*      */     public void layoutContainer(Container parent) {
/* 5798 */       int tabPlacement = BasicJideTabbedPaneUI.this._tabPane.getTabPlacement();
/* 5799 */       Insets insets = BasicJideTabbedPaneUI.this._tabPane.getInsets();
/* 5800 */       int selectedIndex = BasicJideTabbedPaneUI.this._tabPane.getSelectedIndex();
/* 5801 */       Component visibleComponent = BasicJideTabbedPaneUI.this.getVisibleComponent();
/*      */ 
/* 5803 */       synchronized (this) {
/* 5804 */         BasicJideTabbedPaneUI.this.ensureCloseButtonCreated();
/* 5805 */         calculateLayoutInfo();
/*      */ 
/* 5807 */         if (selectedIndex < 0) {
/* 5808 */           if (visibleComponent != null)
/*      */           {
/* 5810 */             BasicJideTabbedPaneUI.this.setVisibleComponent(null);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 5815 */           int totalTabWidth = 0;
/* 5816 */           int totalTabHeight = 0;
/* 5817 */           Insets contentInsets = BasicJideTabbedPaneUI.this.getContentBorderInsets(tabPlacement);
/*      */ 
/* 5819 */           Component selectedComponent = BasicJideTabbedPaneUI.this._tabPane.getComponentAt(selectedIndex);
/* 5820 */           boolean shouldChangeFocus = false;
/*      */ 
/* 5829 */           if (selectedComponent != null) {
/* 5830 */             if ((selectedComponent != visibleComponent) && (visibleComponent != null) && 
/* 5831 */               (JideSwingUtilities.isAncestorOfFocusOwner(visibleComponent)) && (BasicJideTabbedPaneUI.this._tabPane.isAutoRequestFocus())) {
/* 5832 */               shouldChangeFocus = true;
/*      */             }
/*      */ 
/* 5835 */             BasicJideTabbedPaneUI.this.setVisibleComponent(selectedComponent);
/*      */           }
/*      */ 
/* 5838 */           Rectangle bounds = BasicJideTabbedPaneUI.this._tabPane.getBounds();
/* 5839 */           int numChildren = BasicJideTabbedPaneUI.this._tabPane.getComponentCount();
/*      */ 
/* 5841 */           if (numChildren > 0)
/*      */           {
/*      */             int cx;
/*      */             int cy;
/* 5842 */             switch (tabPlacement) {
/*      */             case 2:
/* 5844 */               totalTabWidth = BasicJideTabbedPaneUI.this.calculateTabAreaWidth(tabPlacement, BasicJideTabbedPaneUI.this._runCount, BasicJideTabbedPaneUI.this._maxTabWidth);
/* 5845 */               cx = insets.left + totalTabWidth + contentInsets.left;
/* 5846 */               cy = insets.top + contentInsets.top;
/* 5847 */               break;
/*      */             case 4:
/* 5849 */               totalTabWidth = BasicJideTabbedPaneUI.this.calculateTabAreaWidth(tabPlacement, BasicJideTabbedPaneUI.this._runCount, BasicJideTabbedPaneUI.this._maxTabWidth);
/* 5850 */               cx = insets.left + contentInsets.left;
/* 5851 */               cy = insets.top + contentInsets.top;
/* 5852 */               break;
/*      */             case 3:
/* 5854 */               totalTabHeight = BasicJideTabbedPaneUI.this.calculateTabAreaHeight(tabPlacement, BasicJideTabbedPaneUI.this._runCount, BasicJideTabbedPaneUI.this._maxTabHeight);
/* 5855 */               cx = insets.left + contentInsets.left;
/* 5856 */               cy = insets.top + contentInsets.top;
/* 5857 */               break;
/*      */             case 1:
/*      */             default:
/* 5860 */               totalTabHeight = BasicJideTabbedPaneUI.this.calculateTabAreaHeight(tabPlacement, BasicJideTabbedPaneUI.this._runCount, BasicJideTabbedPaneUI.this._maxTabHeight);
/* 5861 */               cx = insets.left + contentInsets.left;
/* 5862 */               cy = insets.top + totalTabHeight + contentInsets.top;
/*      */             }
/*      */ 
/* 5865 */             int cw = bounds.width - totalTabWidth - insets.left - insets.right - contentInsets.left - contentInsets.right;
/*      */ 
/* 5868 */             int ch = bounds.height - totalTabHeight - insets.top - insets.bottom - contentInsets.top - contentInsets.bottom;
/*      */ 
/* 5872 */             for (int i = 0; i < numChildren; i++) {
/* 5873 */               Component child = BasicJideTabbedPaneUI.this._tabPane.getComponent(i);
/* 5874 */               child.setBounds(cx, cy, cw, ch);
/*      */             }
/*      */           }
/*      */ 
/* 5878 */           if ((shouldChangeFocus) && 
/* 5879 */             (!BasicJideTabbedPaneUI.this.requestFocusForVisibleComponent()) && 
/* 5880 */             (!BasicJideTabbedPaneUI.this._tabPane.requestFocusInWindow()))
/* 5881 */             BasicJideTabbedPaneUI.this._tabPane.requestFocus();
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     public void calculateLayoutInfo()
/*      */     {
/* 5890 */       int tabCount = BasicJideTabbedPaneUI.this._tabPane.getTabCount();
/* 5891 */       BasicJideTabbedPaneUI.this.assureRectsCreated(tabCount);
/* 5892 */       calculateTabRects(BasicJideTabbedPaneUI.this._tabPane.getTabPlacement(), tabCount);
/*      */     }
/*      */ 
/*      */     protected void calculateTabRects(int tabPlacement, int tabCount) {
/* 5896 */       Dimension size = BasicJideTabbedPaneUI.this._tabPane.getSize();
/* 5897 */       Insets insets = BasicJideTabbedPaneUI.this._tabPane.getInsets();
/* 5898 */       Insets tabAreaInsets = BasicJideTabbedPaneUI.this.getTabAreaInsets(tabPlacement);
/* 5899 */       int selectedIndex = BasicJideTabbedPaneUI.this._tabPane.getSelectedIndex();
/*      */ 
/* 5904 */       boolean verticalTabRuns = (tabPlacement == 2) || (tabPlacement == 4);
/* 5905 */       boolean leftToRight = BasicJideTabbedPaneUI.this._tabPane.getComponentOrientation().isLeftToRight();
/*      */       int x;
/*      */       int y;
/*      */       int returnAt;
/* 5910 */       switch (tabPlacement) {
/*      */       case 2:
/* 5912 */         BasicJideTabbedPaneUI.this._maxTabWidth = BasicJideTabbedPaneUI.this.calculateMaxTabWidth(tabPlacement);
/* 5913 */         x = insets.left + tabAreaInsets.left;
/* 5914 */         y = insets.top + tabAreaInsets.top;
/* 5915 */         returnAt = size.height - (insets.bottom + tabAreaInsets.bottom);
/* 5916 */         break;
/*      */       case 4:
/* 5918 */         BasicJideTabbedPaneUI.this._maxTabWidth = BasicJideTabbedPaneUI.this.calculateMaxTabWidth(tabPlacement);
/* 5919 */         x = size.width - insets.right - tabAreaInsets.right - BasicJideTabbedPaneUI.this._maxTabWidth;
/* 5920 */         y = insets.top + tabAreaInsets.top;
/* 5921 */         returnAt = size.height - (insets.bottom + tabAreaInsets.bottom);
/* 5922 */         break;
/*      */       case 3:
/* 5924 */         BasicJideTabbedPaneUI.this._maxTabHeight = BasicJideTabbedPaneUI.this.calculateMaxTabHeight(tabPlacement);
/* 5925 */         x = insets.left + tabAreaInsets.left;
/* 5926 */         y = size.height - insets.bottom - tabAreaInsets.bottom - BasicJideTabbedPaneUI.this._maxTabHeight;
/* 5927 */         returnAt = size.width - (insets.right + tabAreaInsets.right);
/* 5928 */         break;
/*      */       case 1:
/*      */       default:
/* 5931 */         BasicJideTabbedPaneUI.this._maxTabHeight = BasicJideTabbedPaneUI.this.calculateMaxTabHeight(tabPlacement);
/* 5932 */         x = insets.left + tabAreaInsets.left;
/* 5933 */         y = insets.top + tabAreaInsets.top;
/* 5934 */         returnAt = size.width - (insets.right + tabAreaInsets.right);
/*      */       }
/*      */ 
/* 5938 */       int tabRunOverlay = BasicJideTabbedPaneUI.this.getTabRunOverlay(tabPlacement);
/*      */ 
/* 5940 */       BasicJideTabbedPaneUI.this._runCount = 0;
/* 5941 */       BasicJideTabbedPaneUI.this._selectedRun = -1;
/*      */ 
/* 5943 */       if (tabCount == 0) {
/* 5944 */         return;
/*      */       }
/*      */ 
/* 5949 */       for (int i = 0; i < tabCount; i++) {
/* 5950 */         FontMetrics metrics = BasicJideTabbedPaneUI.this.getFontMetrics(i);
/* 5951 */         Rectangle rect = BasicJideTabbedPaneUI.this._rects[i];
/*      */ 
/* 5953 */         if (!verticalTabRuns)
/*      */         {
/* 5955 */           if (i > 0) {
/* 5956 */             rect.x = (BasicJideTabbedPaneUI.this._rects[(i - 1)].x + BasicJideTabbedPaneUI.this._rects[(i - 1)].width);
/*      */           }
/*      */           else {
/* 5959 */             BasicJideTabbedPaneUI.this._tabRuns[0] = 0;
/* 5960 */             BasicJideTabbedPaneUI.this._runCount = 1;
/* 5961 */             BasicJideTabbedPaneUI.this._maxTabWidth = 0;
/* 5962 */             rect.x = x;
/*      */           }
/* 5964 */           rect.width = BasicJideTabbedPaneUI.this.calculateTabWidth(tabPlacement, i, metrics);
/* 5965 */           BasicJideTabbedPaneUI.this._maxTabWidth = Math.max(BasicJideTabbedPaneUI.this._maxTabWidth, rect.width);
/*      */ 
/* 5970 */           if ((rect.x != 2 + insets.left) && (rect.x + rect.width > returnAt)) {
/* 5971 */             if (BasicJideTabbedPaneUI.this._runCount > BasicJideTabbedPaneUI.this._tabRuns.length - 1) {
/* 5972 */               BasicJideTabbedPaneUI.this.expandTabRunsArray();
/*      */             }
/* 5974 */             BasicJideTabbedPaneUI.this._tabRuns[BasicJideTabbedPaneUI.this._runCount] = i;
/* 5975 */             BasicJideTabbedPaneUI.this._runCount += 1;
/* 5976 */             rect.x = x;
/*      */           }
/*      */ 
/* 5979 */           rect.y = y;
/* 5980 */           rect.height = BasicJideTabbedPaneUI.this._maxTabHeight;
/*      */         }
/*      */         else
/*      */         {
/* 5985 */           if (i > 0) {
/* 5986 */             rect.y = (BasicJideTabbedPaneUI.this._rects[(i - 1)].y + BasicJideTabbedPaneUI.this._rects[(i - 1)].height);
/*      */           }
/*      */           else {
/* 5989 */             BasicJideTabbedPaneUI.this._tabRuns[0] = 0;
/* 5990 */             BasicJideTabbedPaneUI.this._runCount = 1;
/* 5991 */             BasicJideTabbedPaneUI.this._maxTabHeight = 0;
/* 5992 */             rect.y = y;
/*      */           }
/* 5994 */           rect.height = BasicJideTabbedPaneUI.this.calculateTabHeight(tabPlacement, i, metrics);
/* 5995 */           BasicJideTabbedPaneUI.this._maxTabHeight = Math.max(BasicJideTabbedPaneUI.this._maxTabHeight, rect.height);
/*      */ 
/* 6000 */           if ((rect.y != 2 + insets.top) && (rect.y + rect.height > returnAt)) {
/* 6001 */             if (BasicJideTabbedPaneUI.this._runCount > BasicJideTabbedPaneUI.this._tabRuns.length - 1) {
/* 6002 */               BasicJideTabbedPaneUI.this.expandTabRunsArray();
/*      */             }
/* 6004 */             BasicJideTabbedPaneUI.this._tabRuns[BasicJideTabbedPaneUI.this._runCount] = i;
/* 6005 */             BasicJideTabbedPaneUI.this._runCount += 1;
/* 6006 */             rect.y = y;
/*      */           }
/*      */ 
/* 6009 */           rect.x = x;
/* 6010 */           rect.width = BasicJideTabbedPaneUI.this._maxTabWidth;
/*      */         }
/*      */ 
/* 6013 */         if (i == selectedIndex) {
/* 6014 */           BasicJideTabbedPaneUI.this._selectedRun = (BasicJideTabbedPaneUI.this._runCount - 1);
/*      */         }
/*      */       }
/*      */ 
/* 6018 */       if (BasicJideTabbedPaneUI.this._runCount > 1)
/*      */       {
/* 6020 */         normalizeTabRuns(tabPlacement, tabCount, verticalTabRuns ? y : x, returnAt);
/*      */ 
/* 6022 */         BasicJideTabbedPaneUI.this._selectedRun = BasicJideTabbedPaneUI.this.getRunForTab(tabCount, selectedIndex);
/*      */ 
/* 6025 */         if (BasicJideTabbedPaneUI.this.shouldRotateTabRuns(tabPlacement)) {
/* 6026 */           rotateTabRuns(tabPlacement, BasicJideTabbedPaneUI.this._selectedRun);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 6032 */       for (i = BasicJideTabbedPaneUI.this._runCount - 1; i >= 0; i--) {
/* 6033 */         int start = BasicJideTabbedPaneUI.this._tabRuns[i];
/* 6034 */         int next = BasicJideTabbedPaneUI.this._tabRuns[(i + 1)];
/* 6035 */         int end = next != 0 ? next - 1 : tabCount - 1;
/* 6036 */         if (!verticalTabRuns) {
/* 6037 */           for (int j = start; j <= end; j++) {
/* 6038 */             Rectangle rect = BasicJideTabbedPaneUI.this._rects[j];
/* 6039 */             rect.y = y;
/* 6040 */             rect.x += BasicJideTabbedPaneUI.this.getTabRunIndent(tabPlacement, i);
/*      */           }
/* 6042 */           if (BasicJideTabbedPaneUI.this.shouldPadTabRun(tabPlacement, i)) {
/* 6043 */             padTabRun(tabPlacement, start, end, returnAt);
/*      */           }
/* 6045 */           if (tabPlacement == 3) {
/* 6046 */             y -= BasicJideTabbedPaneUI.this._maxTabHeight - tabRunOverlay;
/*      */           }
/*      */           else
/* 6049 */             y += BasicJideTabbedPaneUI.this._maxTabHeight - tabRunOverlay;
/*      */         }
/*      */         else
/*      */         {
/* 6053 */           for (int j = start; j <= end; j++) {
/* 6054 */             Rectangle rect = BasicJideTabbedPaneUI.this._rects[j];
/* 6055 */             rect.x = x;
/* 6056 */             rect.y += BasicJideTabbedPaneUI.this.getTabRunIndent(tabPlacement, i);
/*      */           }
/* 6058 */           if (BasicJideTabbedPaneUI.this.shouldPadTabRun(tabPlacement, i)) {
/* 6059 */             padTabRun(tabPlacement, start, end, returnAt);
/*      */           }
/* 6061 */           if (tabPlacement == 4) {
/* 6062 */             x -= BasicJideTabbedPaneUI.this._maxTabWidth - tabRunOverlay;
/*      */           }
/*      */           else {
/* 6065 */             x += BasicJideTabbedPaneUI.this._maxTabWidth - tabRunOverlay;
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 6071 */       padSelectedTab(tabPlacement, selectedIndex);
/*      */ 
/* 6075 */       if ((!leftToRight) && (!verticalTabRuns)) {
/* 6076 */         int rightMargin = size.width - (insets.right + tabAreaInsets.right);
/*      */ 
/* 6078 */         for (i = 0; i < tabCount; i++)
/* 6079 */           BasicJideTabbedPaneUI.this._rects[i].x = (rightMargin - BasicJideTabbedPaneUI.this._rects[i].x - BasicJideTabbedPaneUI.this._rects[i].width);
/*      */       }
/*      */     }
/*      */ 
/*      */     protected void rotateTabRuns(int tabPlacement, int selectedRun)
/*      */     {
/* 6090 */       for (int i = 0; i < selectedRun; i++) {
/* 6091 */         int save = BasicJideTabbedPaneUI.this._tabRuns[0];
/* 6092 */         for (int j = 1; j < BasicJideTabbedPaneUI.this._runCount; j++) {
/* 6093 */           BasicJideTabbedPaneUI.this._tabRuns[(j - 1)] = BasicJideTabbedPaneUI.this._tabRuns[j];
/*      */         }
/* 6095 */         BasicJideTabbedPaneUI.this._tabRuns[(BasicJideTabbedPaneUI.this._runCount - 1)] = save;
/*      */       }
/*      */     }
/*      */ 
/*      */     protected void normalizeTabRuns(int tabPlacement, int tabCount, int start, int max)
/*      */     {
/* 6101 */       boolean verticalTabRuns = (tabPlacement == 2) || (tabPlacement == 4);
/* 6102 */       int run = BasicJideTabbedPaneUI.this._runCount - 1;
/* 6103 */       boolean keepAdjusting = true;
/* 6104 */       double weight = 1.25D;
/*      */ 
/* 6117 */       while (keepAdjusting) {
/* 6118 */         int last = BasicJideTabbedPaneUI.this.lastTabInRun(tabCount, run);
/* 6119 */         int prevLast = BasicJideTabbedPaneUI.this.lastTabInRun(tabCount, run - 1);
/*      */         int prevLastLen;
/*      */         int end;
/*      */         int prevLastLen;
/* 6123 */         if (!verticalTabRuns) {
/* 6124 */           int end = BasicJideTabbedPaneUI.this._rects[last].x + BasicJideTabbedPaneUI.this._rects[last].width;
/* 6125 */           prevLastLen = (int)(BasicJideTabbedPaneUI.this._maxTabWidth * weight);
/*      */         }
/*      */         else {
/* 6128 */           end = BasicJideTabbedPaneUI.this._rects[last].y + BasicJideTabbedPaneUI.this._rects[last].height;
/* 6129 */           prevLastLen = (int)(BasicJideTabbedPaneUI.this._maxTabHeight * weight * 2.0D);
/*      */         }
/*      */ 
/* 6134 */         if (max - end > prevLastLen)
/*      */         {
/* 6137 */           BasicJideTabbedPaneUI.this._tabRuns[run] = prevLast;
/* 6138 */           if (!verticalTabRuns) {
/* 6139 */             BasicJideTabbedPaneUI.this._rects[prevLast].x = start;
/*      */           }
/*      */           else {
/* 6142 */             BasicJideTabbedPaneUI.this._rects[prevLast].y = start;
/*      */           }
/* 6144 */           for (int i = prevLast + 1; i <= last; i++) {
/* 6145 */             if (!verticalTabRuns) {
/* 6146 */               BasicJideTabbedPaneUI.this._rects[i].x = (BasicJideTabbedPaneUI.this._rects[(i - 1)].x + BasicJideTabbedPaneUI.this._rects[(i - 1)].width);
/*      */             }
/*      */             else {
/* 6149 */               BasicJideTabbedPaneUI.this._rects[i].y = (BasicJideTabbedPaneUI.this._rects[(i - 1)].y + BasicJideTabbedPaneUI.this._rects[(i - 1)].height);
/*      */             }
/*      */           }
/*      */ 
/*      */         }
/* 6154 */         else if (run == BasicJideTabbedPaneUI.this._runCount - 1)
/*      */         {
/* 6156 */           keepAdjusting = false;
/*      */         }
/* 6158 */         if (run - 1 > 0)
/*      */         {
/* 6160 */           run--;
/*      */         }
/*      */         else
/*      */         {
/* 6166 */           run = BasicJideTabbedPaneUI.this._runCount - 1;
/* 6167 */           weight += 0.25D;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     protected void padTabRun(int tabPlacement, int start, int end, int max) {
/* 6173 */       Rectangle lastRect = BasicJideTabbedPaneUI.this._rects[end];
/* 6174 */       if ((tabPlacement == 1) || (tabPlacement == 3)) {
/* 6175 */         int runWidth = lastRect.x + lastRect.width - BasicJideTabbedPaneUI.this._rects[start].x;
/* 6176 */         int deltaWidth = max - (lastRect.x + lastRect.width);
/* 6177 */         float factor = deltaWidth / runWidth;
/*      */ 
/* 6179 */         for (int j = start; j <= end; j++) {
/* 6180 */           Rectangle pastRect = BasicJideTabbedPaneUI.this._rects[j];
/* 6181 */           if (j > start) {
/* 6182 */             pastRect.x = (BasicJideTabbedPaneUI.this._rects[(j - 1)].x + BasicJideTabbedPaneUI.this._rects[(j - 1)].width);
/*      */           }
/* 6184 */           pastRect.width += Math.round(pastRect.width * factor);
/*      */         }
/* 6186 */         lastRect.width = (max - lastRect.x);
/*      */       }
/*      */       else {
/* 6189 */         int runHeight = lastRect.y + lastRect.height - BasicJideTabbedPaneUI.this._rects[start].y;
/* 6190 */         int deltaHeight = max - (lastRect.y + lastRect.height);
/* 6191 */         float factor = deltaHeight / runHeight;
/*      */ 
/* 6193 */         for (int j = start; j <= end; j++) {
/* 6194 */           Rectangle pastRect = BasicJideTabbedPaneUI.this._rects[j];
/* 6195 */           if (j > start) {
/* 6196 */             pastRect.y = (BasicJideTabbedPaneUI.this._rects[(j - 1)].y + BasicJideTabbedPaneUI.this._rects[(j - 1)].height);
/*      */           }
/* 6198 */           pastRect.height += Math.round(pastRect.height * factor);
/*      */         }
/* 6200 */         lastRect.height = (max - lastRect.y);
/*      */       }
/*      */     }
/*      */ 
/*      */     protected void padSelectedTab(int tabPlacement, int selectedIndex)
/*      */     {
/* 6206 */       if (selectedIndex >= 0) {
/* 6207 */         Rectangle selRect = BasicJideTabbedPaneUI.this._rects[selectedIndex];
/* 6208 */         Insets padInsets = BasicJideTabbedPaneUI.this.getSelectedTabPadInsets(tabPlacement);
/* 6209 */         selRect.x -= padInsets.left;
/* 6210 */         selRect.width += padInsets.left + padInsets.right;
/* 6211 */         selRect.y -= padInsets.top;
/* 6212 */         selRect.height += padInsets.top + padInsets.bottom;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class CloseTabAction extends AbstractAction
/*      */   {
/*      */     private static final long serialVersionUID = 7779678389793199733L;
/*      */ 
/*      */     public CloseTabAction()
/*      */     {
/* 5532 */       putValue("ShortDescription", Resource.getResourceBundle(Locale.getDefault()).getString("JideTabbedPane.close"));
/*      */     }
/*      */ 
/*      */     public void actionPerformed(ActionEvent e)
/*      */     {
/* 5537 */       Object src = e.getSource();
/*      */ 
/* 5539 */       boolean closeSelected = false;
/*      */       JideTabbedPane pane;
/* 5540 */       if ((src instanceof JideTabbedPane)) {
/* 5541 */         pane = (JideTabbedPane)src;
/*      */       }
/* 5543 */       else if (((src instanceof JideTabbedPane.NoFocusButton)) && ((((JideTabbedPane.NoFocusButton)src).getParent() instanceof JideTabbedPane))) {
/* 5544 */         JideTabbedPane pane = (JideTabbedPane)((JideTabbedPane.NoFocusButton)src).getParent();
/* 5545 */         closeSelected = true;
/*      */       }
/* 5547 */       else if (((src instanceof JideTabbedPane.NoFocusButton)) && ((((JideTabbedPane.NoFocusButton)src).getParent() instanceof BasicJideTabbedPaneUI.ScrollableTabPanel))) {
/* 5548 */         JideTabbedPane pane = (JideTabbedPane)SwingUtilities.getAncestorOfClass(JideTabbedPane.class, (JideTabbedPane.NoFocusButton)src);
/* 5549 */         closeSelected = false;
/*      */       }
/*      */       else {
/* 5552 */         return;
/*      */       }
/*      */       JideTabbedPane pane;
/* 5555 */       if (pane.isTabEditing()) {
/* 5556 */         ((BasicJideTabbedPaneUI)pane.getUI()).stopOrCancelEditing();
/*      */       }
/*      */ 
/* 5559 */       ActionEvent e2 = e;
/* 5560 */       if ((src instanceof JideTabbedPane.NoFocusButton)) {
/* 5561 */         int index = ((JideTabbedPane.NoFocusButton)src).getIndex();
/* 5562 */         Component compSrc = index != -1 ? pane.getComponentAt(index) : pane.getSelectedComponent();
/*      */ 
/* 5566 */         if (compSrc != null)
/* 5567 */           e2 = new ActionEvent(compSrc, e.getID(), e.getActionCommand(), e.getWhen(), e.getModifiers());
/*      */       }
/* 5569 */       else if ("middleMouseButtonClicked".equals(e.getActionCommand())) {
/* 5570 */         int index = e.getID();
/* 5571 */         Component compSrc = index != -1 ? pane.getComponentAt(index) : pane.getSelectedComponent();
/*      */ 
/* 5575 */         if (compSrc != null) {
/* 5576 */           e2 = new ActionEvent(compSrc, e.getID(), e.getActionCommand(), e.getWhen(), e.getModifiers());
/*      */         }
/*      */       }
/* 5579 */       if (pane.getCloseAction() != null) {
/* 5580 */         pane.getCloseAction().actionPerformed(e2);
/*      */       }
/* 5583 */       else if ("middleMouseButtonClicked".equals(e.getActionCommand())) {
/* 5584 */         int index = e.getID();
/* 5585 */         if (index >= 0)
/* 5586 */           pane.removeTabAt(index);
/* 5587 */         if (pane.getTabCount() == 0) {
/* 5588 */           pane.updateUI();
/*      */         }
/* 5590 */         pane.doLayout();
/* 5591 */         if (pane.getSelectedIndex() >= 0) {
/* 5592 */           ((BasicJideTabbedPaneUI)pane.getUI())._tabScroller.tabPanel.scrollRectToVisible(((BasicJideTabbedPaneUI)pane.getUI())._rects[pane.getSelectedIndex()]);
/*      */         }
/*      */       }
/* 5595 */       else if (closeSelected) {
/* 5596 */         if (pane.getSelectedIndex() >= 0)
/* 5597 */           pane.removeTabAt(pane.getSelectedIndex());
/* 5598 */         if (pane.getTabCount() == 0) {
/* 5599 */           pane.updateUI();
/*      */         }
/* 5601 */         pane.doLayout();
/* 5602 */         if (pane.getSelectedIndex() >= 0) {
/* 5603 */           ((BasicJideTabbedPaneUI)pane.getUI())._tabScroller.tabPanel.scrollRectToVisible(((BasicJideTabbedPaneUI)pane.getUI())._rects[pane.getSelectedIndex()]);
/*      */         }
/*      */       }
/* 5606 */       else if ((src instanceof JideTabbedPane.NoFocusButton)) {
/* 5607 */         int i = ((JideTabbedPane.NoFocusButton)src).getIndex();
/* 5608 */         if (i != -1)
/*      */         {
/* 5610 */           int tabIndex = pane.getSelectedIndex();
/*      */ 
/* 5612 */           pane.removeTabAt(i);
/*      */ 
/* 5614 */           if (i < tabIndex) {
/* 5615 */             pane.setSelectedIndex(tabIndex - 1);
/*      */           }
/*      */ 
/* 5618 */           if (pane.getTabCount() == 0) {
/* 5619 */             pane.updateUI();
/*      */           }
/* 5621 */           pane.doLayout();
/* 5622 */           if (pane.getSelectedIndex() >= 0)
/* 5623 */             ((BasicJideTabbedPaneUI)pane.getUI())._tabScroller.tabPanel.scrollRectToVisible(((BasicJideTabbedPaneUI)pane.getUI())._rects[pane.getSelectedIndex()]);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class ScrollTabsListAction extends AbstractAction
/*      */   {
/*      */     private static final long serialVersionUID = 246103712600916771L;
/*      */ 
/*      */     public ScrollTabsListAction()
/*      */     {
/* 5487 */       putValue("ShortDescription", Resource.getResourceBundle(Locale.getDefault()).getString("JideTabbedPane.showList"));
/*      */     }
/*      */ 
/*      */     public void actionPerformed(ActionEvent e) {
/* 5491 */       JTabbedPane pane = null;
/* 5492 */       Object src = e.getSource();
/* 5493 */       if ((src instanceof JTabbedPane)) {
/* 5494 */         pane = (JTabbedPane)src;
/*      */       }
/* 5496 */       else if ((src instanceof JideTabbedPane.NoFocusButton)) {
/* 5497 */         pane = (JTabbedPane)SwingUtilities.getAncestorOfClass(JTabbedPane.class, (JideTabbedPane.NoFocusButton)src);
/*      */       }
/*      */ 
/* 5500 */       if (pane != null) {
/* 5501 */         BasicJideTabbedPaneUI ui = (BasicJideTabbedPaneUI)pane.getUI();
/*      */ 
/* 5503 */         if (ui.scrollableTabLayoutEnabled())
/* 5504 */           if ((ui._tabScroller._popup != null) && (ui._tabScroller._popup.isPopupVisible())) {
/* 5505 */             ui._tabScroller._popup.hidePopupImmediately();
/* 5506 */             ui._tabScroller._popup = null;
/*      */           }
/*      */           else {
/* 5509 */             ui._tabScroller.createPopup(pane.getTabPlacement());
/*      */           }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class ScrollTabsBackwardAction extends AbstractAction
/*      */   {
/*      */     private static final long serialVersionUID = -426408621939940046L;
/*      */ 
/*      */     public ScrollTabsBackwardAction()
/*      */     {
/* 5458 */       putValue("ShortDescription", Resource.getResourceBundle(Locale.getDefault()).getString("JideTabbedPane.scrollBackward"));
/*      */     }
/*      */ 
/*      */     public void actionPerformed(ActionEvent e) {
/* 5462 */       JTabbedPane pane = null;
/* 5463 */       Object src = e.getSource();
/* 5464 */       if ((src instanceof JTabbedPane)) {
/* 5465 */         pane = (JTabbedPane)src;
/*      */       }
/* 5467 */       else if ((src instanceof JideTabbedPane.NoFocusButton)) {
/* 5468 */         pane = (JTabbedPane)SwingUtilities.getAncestorOfClass(JTabbedPane.class, (JideTabbedPane.NoFocusButton)src);
/*      */       }
/*      */ 
/* 5471 */       if (pane != null) {
/* 5472 */         BasicJideTabbedPaneUI ui = (BasicJideTabbedPaneUI)pane.getUI();
/*      */ 
/* 5474 */         if (ui.scrollableTabLayoutEnabled())
/* 5475 */           ui._tabScroller.scrollBackward(pane.getTabPlacement());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class ScrollTabsForwardAction extends AbstractAction
/*      */   {
/*      */     private static final long serialVersionUID = 8772616556895545931L;
/*      */ 
/*      */     public ScrollTabsForwardAction()
/*      */     {
/* 5430 */       putValue("ShortDescription", Resource.getResourceBundle(Locale.getDefault()).getString("JideTabbedPane.scrollForward"));
/*      */     }
/*      */ 
/*      */     public void actionPerformed(ActionEvent e) {
/* 5434 */       JTabbedPane pane = null;
/* 5435 */       Object src = e.getSource();
/* 5436 */       if ((src instanceof JTabbedPane)) {
/* 5437 */         pane = (JTabbedPane)src;
/*      */       }
/* 5439 */       else if ((src instanceof JideTabbedPane.NoFocusButton)) {
/* 5440 */         pane = (JTabbedPane)SwingUtilities.getAncestorOfClass(JTabbedPane.class, (JideTabbedPane.NoFocusButton)src);
/*      */       }
/*      */ 
/* 5443 */       if (pane != null) {
/* 5444 */         BasicJideTabbedPaneUI ui = (BasicJideTabbedPaneUI)pane.getUI();
/*      */ 
/* 5446 */         if (ui.scrollableTabLayoutEnabled())
/* 5447 */           ui._tabScroller.scrollForward(pane.getTabPlacement());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class SetSelectedIndexAction extends AbstractAction
/*      */   {
/*      */     private static final long serialVersionUID = 6216635910156115469L;
/*      */ 
/*      */     public void actionPerformed(ActionEvent e)
/*      */     {
/* 5388 */       JTabbedPane pane = (JTabbedPane)e.getSource();
/*      */ 
/* 5390 */       if ((pane != null) && ((pane.getUI() instanceof BasicJideTabbedPaneUI))) {
/* 5391 */         BasicJideTabbedPaneUI ui = (BasicJideTabbedPaneUI)pane.getUI();
/* 5392 */         String command = e.getActionCommand();
/*      */ 
/* 5394 */         if ((command != null) && (command.length() > 0)) {
/* 5395 */           int mnemonic = e.getActionCommand().charAt(0);
/* 5396 */           if ((mnemonic >= 97) && (mnemonic <= 122)) {
/* 5397 */             mnemonic -= 32;
/*      */           }
/* 5399 */           Integer index = (Integer)ui._mnemonicToIndexMap.get(new Integer(mnemonic));
/*      */ 
/* 5401 */           if ((index != null) && (pane.isEnabledAt(index.intValue())))
/* 5402 */             pane.setSelectedIndex(index.intValue());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class RequestFocusForVisibleAction extends AbstractAction
/*      */   {
/*      */     private static final long serialVersionUID = 6677797853998039155L;
/*      */ 
/*      */     public void actionPerformed(ActionEvent e)
/*      */     {
/* 5374 */       JTabbedPane pane = (JTabbedPane)e.getSource();
/* 5375 */       BasicJideTabbedPaneUI ui = (BasicJideTabbedPaneUI)pane.getUI();
/* 5376 */       ui.requestFocusForVisibleComponent();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class RequestFocusAction extends AbstractAction
/*      */   {
/*      */     private static final long serialVersionUID = 3791111435639724577L;
/*      */ 
/*      */     public void actionPerformed(ActionEvent e)
/*      */     {
/* 5363 */       JTabbedPane pane = (JTabbedPane)e.getSource();
/* 5364 */       if (!pane.requestFocusInWindow())
/* 5365 */         pane.requestFocus();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class PageDownAction extends AbstractAction
/*      */   {
/*      */     private static final long serialVersionUID = 4895454480954468453L;
/*      */ 
/*      */     public void actionPerformed(ActionEvent e)
/*      */     {
/* 5347 */       JTabbedPane pane = (JTabbedPane)e.getSource();
/* 5348 */       BasicJideTabbedPaneUI ui = (BasicJideTabbedPaneUI)pane.getUI();
/* 5349 */       int tabPlacement = pane.getTabPlacement();
/* 5350 */       if ((tabPlacement == 1) || (tabPlacement == 3)) {
/* 5351 */         ui.navigateSelectedTab(3);
/*      */       }
/*      */       else
/* 5354 */         ui.navigateSelectedTab(5);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class PageUpAction extends AbstractAction
/*      */   {
/*      */     private static final long serialVersionUID = 1154273528778779166L;
/*      */ 
/*      */     public void actionPerformed(ActionEvent e)
/*      */     {
/* 5331 */       JTabbedPane pane = (JTabbedPane)e.getSource();
/* 5332 */       BasicJideTabbedPaneUI ui = (BasicJideTabbedPaneUI)pane.getUI();
/* 5333 */       int tabPlacement = pane.getTabPlacement();
/* 5334 */       if ((tabPlacement == 1) || (tabPlacement == 3)) {
/* 5335 */         ui.navigateSelectedTab(7);
/*      */       }
/*      */       else
/* 5338 */         ui.navigateSelectedTab(1);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class PreviousAction extends AbstractAction
/*      */   {
/*      */     private static final long serialVersionUID = 2095403667386334865L;
/*      */ 
/*      */     public void actionPerformed(ActionEvent e)
/*      */     {
/* 5321 */       JTabbedPane pane = (JTabbedPane)e.getSource();
/* 5322 */       BasicJideTabbedPaneUI ui = (BasicJideTabbedPaneUI)pane.getUI();
/* 5323 */       ui.navigateSelectedTab(13);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class NextAction extends AbstractAction
/*      */   {
/*      */     private static final long serialVersionUID = -154035573464933924L;
/*      */ 
/*      */     public void actionPerformed(ActionEvent e)
/*      */     {
/* 5311 */       JTabbedPane pane = (JTabbedPane)e.getSource();
/* 5312 */       BasicJideTabbedPaneUI ui = (BasicJideTabbedPaneUI)pane.getUI();
/* 5313 */       ui.navigateSelectedTab(12);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class DownAction extends AbstractAction
/*      */   {
/*      */     private static final long serialVersionUID = -453174268282628886L;
/*      */ 
/*      */     public void actionPerformed(ActionEvent e)
/*      */     {
/* 5301 */       JTabbedPane pane = (JTabbedPane)e.getSource();
/* 5302 */       BasicJideTabbedPaneUI ui = (BasicJideTabbedPaneUI)pane.getUI();
/* 5303 */       ui.navigateSelectedTab(5);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class UpAction extends AbstractAction
/*      */   {
/*      */     private static final long serialVersionUID = -6961702501242792445L;
/*      */ 
/*      */     public void actionPerformed(ActionEvent e)
/*      */     {
/* 5291 */       JTabbedPane pane = (JTabbedPane)e.getSource();
/* 5292 */       BasicJideTabbedPaneUI ui = (BasicJideTabbedPaneUI)pane.getUI();
/* 5293 */       ui.navigateSelectedTab(1);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class LeftAction extends AbstractAction
/*      */   {
/*      */     private static final long serialVersionUID = 8670680299012169408L;
/*      */ 
/*      */     public void actionPerformed(ActionEvent e)
/*      */     {
/* 5281 */       JTabbedPane pane = (JTabbedPane)e.getSource();
/* 5282 */       BasicJideTabbedPaneUI ui = (BasicJideTabbedPaneUI)pane.getUI();
/* 5283 */       ui.navigateSelectedTab(7);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class RightAction extends AbstractAction
/*      */   {
/*      */     private static final long serialVersionUID = -1759791760116532857L;
/*      */ 
/*      */     public void actionPerformed(ActionEvent e)
/*      */     {
/* 5271 */       JTabbedPane pane = (JTabbedPane)e.getSource();
/* 5272 */       BasicJideTabbedPaneUI ui = (BasicJideTabbedPaneUI)pane.getUI();
/* 5273 */       ui.navigateSelectedTab(3);
/*      */     }
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.BasicJideTabbedPaneUI
 * JD-Core Version:    0.6.0
 */
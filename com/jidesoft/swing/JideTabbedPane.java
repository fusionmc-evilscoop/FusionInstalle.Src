/*      */ package com.jidesoft.swing;
/*      */ 
/*      */ import com.jidesoft.plaf.JideTabbedPaneUI;
/*      */ import com.jidesoft.plaf.LookAndFeelFactory;
/*      */ import com.jidesoft.plaf.UIDefaultsLookup;
/*      */ import com.jidesoft.plaf.basic.BasicJideTabbedPaneUI;
/*      */ import com.jidesoft.plaf.basic.Resource;
/*      */ import com.jidesoft.utils.JideFocusTracker;
/*      */ import com.jidesoft.utils.SystemInfo;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.ComponentOrientation;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Insets;
/*      */ import java.awt.KeyboardFocusManager;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.FocusAdapter;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseListener;
/*      */ import java.awt.event.MouseMotionListener;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.lang.reflect.Field;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Map;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.swing.Action;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.DefaultListCellRenderer;
/*      */ import javax.swing.DefaultSingleSelectionModel;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.JTabbedPane;
/*      */ import javax.swing.ListCellRenderer;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.EventListenerList;
/*      */ import javax.swing.plaf.TabbedPaneUI;
/*      */ import javax.swing.plaf.UIResource;
/*      */ 
/*      */ public class JideTabbedPane extends JTabbedPane
/*      */ {
/*   36 */   private boolean _hideOneTab = false;
/*      */ 
/*   38 */   private boolean _showTabButtons = false;
/*      */ 
/*   40 */   private boolean _showCloseButton = false;
/*   41 */   private boolean _showCloseButtonOnTab = false;
/*   42 */   private boolean _useDefaultShowCloseButtonOnTab = false;
/*   43 */   private boolean _showTabArea = true;
/*   44 */   private boolean _showTabContent = true;
/*      */ 
/*   46 */   private boolean _showIconsOnTab = true;
/*   47 */   private boolean _useDefaultShowIconsOnTab = true;
/*      */   private boolean _rightClickSelect;
/*      */   private boolean _dragOverDisabled;
/*   52 */   private boolean _scrollSelectedTabOnWheel = false;
/*      */ 
/*   54 */   private int _tabAlignment = 10;
/*      */   public static final String SHRINK_TAB_PROPERTY = "shrinkTab";
/*      */   public static final String HIDE_IF_ONE_TAB_PROPERTY = "hideIfOneTab";
/*      */   public static final String SHOW_TAB_BUTTONS_PROPERTY = "showTabButtons";
/*      */   public static final String BOX_STYLE_PROPERTY = "boxStyle";
/*      */   public static final String SHOW_ICONS_PROPERTY = "showIconsOnTab";
/*      */   public static final String USE_DEFAULT_SHOW_ICONS_PROPERTY = "useDefaultShowIconsOnTab";
/*      */   public static final String SHOW_CLOSE_BUTTON_ON_TAB_PROPERTY = "showCloseButtonOnTab";
/*      */   public static final String SHOW_CLOSE_BUTTON_PROPERTY = "showCloseButton";
/*      */   public static final String SHOW_TAB_AREA_PROPERTY = "showTabArea";
/*      */   public static final String SHOW_TAB_CONTENT_PROPERTY = "showTabContent";
/*      */   public static final String TAB_CLOSABLE_PROPERTY = "tabClosable";
/*      */   public static final String USE_DEFAULT_SHOW_CLOSE_BUTTON_ON_TAB_PROPERTY = "useDefaultShowCloseButtonOnTab";
/*      */   public static final String BOLDACTIVETAB_PROPERTY = "boldActiveTab";
/*      */   public static final String GRIPPER_PROPERTY = "gripper";
/*      */   public static final String PROPERTY_TAB_SHAPE = "tabShape";
/*      */   public static final String PROPERTY_COLOR_THEME = "colorTheme";
/*      */   public static final String PROPERTY_TAB_RESIZE_MODE = "tabResizeMode";
/*      */   public static final String PROPERTY_TAB_LEADING_COMPONENT = "tabLeadingComponent";
/*      */   public static final String PROPERTY_TAB_TRAILING_COMPONENT = "tabTrailingComponent";
/*      */   public static final String PROPERTY_TAB_COLOR_PROVIDER = "tabColorProvider";
/*      */   public static final String PROPERTY_CONTENT_BORDER_INSETS = "contentBorderInsets";
/*      */   public static final String PROPERTY_TAB_AREA_INSETS = "tabAreaInsets";
/*      */   public static final String PROPERTY_TAB_INSETS = "tabInsets";
/*      */   public static final String PROPERTY_DRAG_OVER_DISABLED = "dragOverDisabled";
/*      */   public static final String SCROLL_TAB_ON_WHEEL_PROPERTY = "scrollTabOnWheel";
/*      */   public static final String PROPERTY_SELECTED_INDEX = "selectedIndex";
/*      */   public static final int BUTTON_CLOSE = 0;
/*      */   public static final int BUTTON_EAST = 1;
/*      */   public static final int BUTTON_WEST = 2;
/*      */   public static final int BUTTON_NORTH = 3;
/*      */   public static final int BUTTON_SOUTH = 4;
/*      */   public static final int BUTTON_LIST = 5;
/*      */   private static final String uiClassID = "JideTabbedPaneUI";
/*  155 */   private boolean _showGripper = false;
/*      */   private StringConverter _stringConverter;
/*  162 */   private boolean _boldActiveTab = false;
/*      */ 
/*  164 */   private Map<Object, Object> _closableMap = new Hashtable();
/*      */ 
/*  166 */   private Hashtable<Component, Object> _pageLastFocusTrackers = new Hashtable();
/*      */   private Font _selectedTabFont;
/*      */   public static final int RESIZE_MODE_DEFAULT = 0;
/*      */   public static final int RESIZE_MODE_NONE = 1;
/*      */   public static final int RESIZE_MODE_FIT = 2;
/*      */   public static final int RESIZE_MODE_FIXED = 3;
/*      */   public static final int RESIZE_MODE_COMPRESSED = 4;
/*  201 */   private int _tabResizeMode = 0;
/*      */   public static final int COLOR_THEME_DEFAULT = 0;
/*      */   public static final int COLOR_THEME_WIN2K = 1;
/*      */   public static final int COLOR_THEME_OFFICE2003 = 2;
/*      */   public static final int COLOR_THEME_VSNET = 3;
/*      */   public static final int COLOR_THEME_WINXP = 4;
/*  213 */   private int _colorTheme = 0;
/*      */   public static final int SHAPE_DEFAULT = 0;
/*      */   public static final int SHAPE_WINDOWS = 1;
/*      */   public static final int SHAPE_VSNET = 2;
/*      */   public static final int SHAPE_BOX = 3;
/*      */   public static final int SHAPE_OFFICE2003 = 4;
/*      */   public static final int SHAPE_FLAT = 5;
/*      */   public static final int SHAPE_ECLIPSE = 6;
/*      */   public static final int SHAPE_ECLIPSE3X = 7;
/*      */   public static final int SHAPE_EXCEL = 8;
/*      */   public static final int SHAPE_ROUNDED_VSNET = 9;
/*      */   public static final int SHAPE_ROUNDED_FLAT = 10;
/*      */   public static final int SHAPE_WINDOWS_SELECTED = 11;
/*  229 */   private int _tabShape = 0;
/*      */ 
/*  231 */   private Component _tabLeadingComponent = null;
/*  232 */   private Component _tabTrailingComponent = null;
/*  233 */   private boolean _hideTrailingWhileNoButtons = false;
/*      */ 
/*  236 */   private boolean _showCloseButtonOnSelectedTab = false;
/*      */   private ListCellRenderer _tabListCellRenderer;
/*      */   private Insets _contentBorderInsets;
/*      */   private Insets _tabAreaInsets;
/*      */   private Insets _tabInsets;
/*  244 */   private static final Logger LOGGER_EVENT = Logger.getLogger(TabEditingEvent.class.getName());
/*      */ 
/*  246 */   private boolean _closeTabOnMouseMiddleButton = false;
/*  247 */   private boolean _layoutTrailingComponentBeforeButtons = false;
/*      */   private Action _closeAction;
/*  431 */   boolean _autoFocusonTabHideClose = true;
/*      */ 
/*  440 */   private boolean _suppressStateChangedEvents = false;
/*      */ 
/*  463 */   private boolean _suppressSetSelectedIndex = false;
/*      */ 
/*  515 */   private boolean _autoRequestFocus = true;
/*      */ 
/*  873 */   protected transient boolean _tabEditingAllowed = false;
/*      */   protected transient TabEditingValidator _tabEditValidator;
/*      */   private ColorProvider _tabColorProvider;
/* 1440 */   private static Color[] ONENOTE_COLORS = { new Color(138, 168, 228), new Color(238, 149, 151), new Color(180, 158, 222), new Color(145, 186, 174), new Color(246, 176, 120), new Color(255, 216, 105), new Color(183, 201, 151) };
/*      */ 
/* 1450 */   public static ColorProvider ONENOTE_COLOR_PROVIDER = new OneNoteColorProvider(null);
/*      */   protected PropertyChangeListener _focusChangeListener;
/*      */ 
/*      */   public JideTabbedPane()
/*      */   {
/*  255 */     this(1, 1);
/*      */   }
/*      */ 
/*      */   public JideTabbedPane(int tabPlacement)
/*      */   {
/*  267 */     this(tabPlacement, 1);
/*      */   }
/*      */ 
/*      */   public JideTabbedPane(int tabPlacement, int tabLayoutPolicy)
/*      */   {
/*  283 */     super(tabPlacement, tabLayoutPolicy);
/*      */ 
/*  287 */     setModel(new IgnoreableSingleSelectionModel());
/*      */   }
/*      */ 
/*      */   public TabbedPaneUI getUI()
/*      */   {
/*  298 */     return (TabbedPaneUI)this.ui;
/*      */   }
/*      */ 
/*      */   public void setUI(TabbedPaneUI ui)
/*      */   {
/*  309 */     super.setUI(ui);
/*      */   }
/*      */ 
/*      */   public void updateUI()
/*      */   {
/*  319 */     if (UIDefaultsLookup.get("JideTabbedPaneUI") == null) {
/*  320 */       LookAndFeelFactory.installJideExtension();
/*      */     }
/*  322 */     setUI((TabbedPaneUI)UIManager.getUI(this));
/*      */   }
/*      */ 
/*      */   public String getUIClassID()
/*      */   {
/*  335 */     return "JideTabbedPaneUI";
/*      */   }
/*      */ 
/*      */   public boolean isHideOneTab()
/*      */   {
/*  345 */     return (!isShowTabButtons()) && (this._hideOneTab);
/*      */   }
/*      */ 
/*      */   public void setHideOneTab(boolean hideOne)
/*      */   {
/*  356 */     boolean oldValue = this._hideOneTab;
/*      */ 
/*  358 */     if (oldValue != hideOne) {
/*  359 */       this._hideOneTab = hideOne;
/*  360 */       firePropertyChange("hideIfOneTab", oldValue, this._hideOneTab);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isTabShown()
/*      */   {
/*  370 */     return (isShowTabArea()) && ((!isHideOneTab()) || (getTabCount() > 1));
/*      */   }
/*      */ 
/*      */   public boolean isShowTabButtons()
/*      */   {
/*  381 */     return this._showTabButtons;
/*      */   }
/*      */ 
/*      */   public void setShowTabButtons(boolean showButtons)
/*      */   {
/*  391 */     boolean oldValue = this._showTabButtons;
/*      */ 
/*  393 */     if (oldValue != showButtons) {
/*  394 */       this._showTabButtons = showButtons;
/*  395 */       firePropertyChange("showTabButtons", oldValue, this._showTabButtons);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setCloseAction(Action action)
/*      */   {
/*  407 */     Action old = this._closeAction;
/*  408 */     if (old != action) {
/*  409 */       this._closeAction = action;
/*  410 */       firePropertyChange("closeTabAction", old, this._closeAction);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Action getCloseAction()
/*      */   {
/*  420 */     return this._closeAction;
/*      */   }
/*      */ 
/*      */   public void setAutoFocusOnTabHideClose(boolean autoFocusonTabHideClose) {
/*  424 */     this._autoFocusonTabHideClose = autoFocusonTabHideClose;
/*      */   }
/*      */ 
/*      */   public boolean isAutoFocusOnTabHideClose() {
/*  428 */     return this._autoFocusonTabHideClose;
/*      */   }
/*      */ 
/*      */   public void resetDefaultCloseAction()
/*      */   {
/*  437 */     setCloseAction(null);
/*      */   }
/*      */ 
/*      */   public void setSuppressStateChangedEvents(boolean suppress)
/*      */   {
/*  443 */     this._suppressStateChangedEvents = suppress;
/*      */   }
/*      */ 
/*      */   public boolean isSuppressStateChangedEvents() {
/*  447 */     return this._suppressStateChangedEvents;
/*      */   }
/*      */ 
/*      */   protected void fireStateChanged()
/*      */   {
/*  452 */     if (this._suppressStateChangedEvents) {
/*  453 */       return;
/*      */     }
/*  455 */     if (!isAutoFocusOnTabHideClose())
/*  456 */       clearVisComp();
/*  457 */     super.fireStateChanged();
/*      */   }
/*      */ 
/*      */   public boolean isSuppressSetSelectedIndex()
/*      */   {
/*  466 */     return this._suppressSetSelectedIndex;
/*      */   }
/*      */ 
/*      */   public void setSuppressSetSelectedIndex(boolean suppressSetSelectedIndex) {
/*  470 */     this._suppressSetSelectedIndex = suppressSetSelectedIndex;
/*      */   }
/*      */ 
/*      */   public void setSelectedIndex(int index)
/*      */   {
/*  475 */     if (this._suppressSetSelectedIndex) {
/*  476 */       return;
/*      */     }
/*  478 */     boolean old = isFocusCycleRoot();
/*  479 */     setFocusCycleRoot(true);
/*      */     try {
/*  481 */       int oldIndex = getSelectedIndex();
/*  482 */       if (oldIndex != index) {
/*  483 */         super.setSelectedIndex(index);
/*  484 */         firePropertyChange("selectedIndex", oldIndex, index);
/*      */       }
/*      */     }
/*      */     finally {
/*  488 */       setFocusCycleRoot(old);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void popupSelectedIndex(int index)
/*      */   {
/*  497 */     setSelectedIndex(index);
/*      */   }
/*      */ 
/*      */   public void setComponentAt(int index, Component component) {
/*  501 */     Component oldComponent = getComponentAt(index);
/*  502 */     if (oldComponent != null)
/*      */     {
/*  504 */       PageLastFocusTracker tracker = (PageLastFocusTracker)this._pageLastFocusTrackers.get(oldComponent);
/*  505 */       this._pageLastFocusTrackers.remove(oldComponent);
/*  506 */       if (tracker != null) {
/*  507 */         tracker.setHeighestComponent(null);
/*      */       }
/*      */     }
/*  510 */     super.setComponentAt(index, component);
/*  511 */     if (!isAutoFocusOnTabHideClose())
/*  512 */       clearVisComp();
/*      */   }
/*      */ 
/*      */   public boolean isAutoRequestFocus()
/*      */   {
/*  524 */     return this._autoRequestFocus;
/*      */   }
/*      */ 
/*      */   public void setAutoRequestFocus(boolean autoRequestFocus) {
/*  528 */     this._autoRequestFocus = autoRequestFocus;
/*      */   }
/*      */ 
/*      */   public void moveSelectedTabTo(int tabIndex)
/*      */   {
/*  537 */     int selectedIndex = getSelectedIndex();
/*  538 */     if (selectedIndex == tabIndex) {
/*  539 */       return;
/*      */     }
/*  541 */     if ((tabIndex == -1) || (selectedIndex == -1)) {
/*  542 */       return;
/*      */     }
/*  544 */     if (isTabEditing()) {
/*  545 */       stopTabEditing();
/*      */     }
/*  547 */     Component selectedComponent = getComponentAt(selectedIndex);
/*      */ 
/*  549 */     boolean old = isAutoRequestFocus();
/*      */ 
/*  551 */     boolean shouldChangeFocus = false;
/*      */ 
/*  554 */     if ((selectedComponent != null) && 
/*  555 */       (JideSwingUtilities.isAncestorOfFocusOwner(selectedComponent)) && (isAutoFocusOnTabHideClose())) {
/*  556 */       shouldChangeFocus = true;
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  561 */       this._suppressStateChangedEvents = true;
/*  562 */       setAutoRequestFocus(false);
/*      */ 
/*  564 */       if ((selectedIndex - tabIndex == 1) || (tabIndex - selectedIndex == 1)) {
/*  565 */         Component frame = getComponentAt(tabIndex);
/*  566 */         String title = getTitleAt(tabIndex);
/*  567 */         String tooltip = getToolTipTextAt(tabIndex);
/*  568 */         Icon icon = getIconAt(tabIndex);
/*  569 */         this._suppressSetSelectedIndex = true;
/*      */         try {
/*  571 */           if (tabIndex > selectedIndex)
/*  572 */             insertTab(title, icon, frame, tooltip, selectedIndex);
/*      */           else
/*  574 */             insertTab(title, icon, frame, tooltip, selectedIndex + 1);
/*      */         }
/*      */         finally
/*      */         {
/*  578 */           this._suppressSetSelectedIndex = false;
/*      */         }
/*      */       }
/*      */       else {
/*  582 */         Component frame = getComponentAt(selectedIndex);
/*  583 */         String title = getTitleAt(selectedIndex);
/*  584 */         String tooltip = getToolTipTextAt(selectedIndex);
/*  585 */         Icon icon = getIconAt(selectedIndex);
/*  586 */         this._suppressSetSelectedIndex = true;
/*      */         try {
/*  588 */           if (tabIndex > selectedIndex)
/*  589 */             insertTab(title, icon, frame, tooltip, tabIndex + 1);
/*      */           else
/*  591 */             insertTab(title, icon, frame, tooltip, tabIndex);
/*      */         }
/*      */         finally
/*      */         {
/*  595 */           this._suppressSetSelectedIndex = false;
/*      */         }
/*      */       }
/*      */ 
/*  599 */       if (!SystemInfo.isJdk15Above())
/*      */       {
/*  601 */         if (tabIndex == getTabCount() - 2) {
/*  602 */           setSelectedIndex(getTabCount() - 1);
/*      */         }
/*      */       }
/*      */ 
/*  606 */       setAutoRequestFocus(old);
/*  607 */       setSelectedIndex(tabIndex);
/*      */     }
/*      */     finally {
/*  610 */       this._suppressStateChangedEvents = false;
/*      */ 
/*  612 */       if ((shouldChangeFocus) && 
/*  613 */         (!requestFocusForVisibleComponent()))
/*      */       {
/*  615 */         requestFocusInWindow();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean requestFocusForVisibleComponent()
/*      */   {
/*  624 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean isHideTrailingWhileNoButtons()
/*      */   {
/*  658 */     return this._hideTrailingWhileNoButtons;
/*      */   }
/*      */ 
/*      */   public void setHideTrailingWhileNoButtons(boolean hideTrailingWhileNoButtons)
/*      */   {
/*  668 */     this._hideTrailingWhileNoButtons = hideTrailingWhileNoButtons;
/*      */   }
/*      */ 
/*      */   public boolean isLayoutTrailingComponentBeforeButtons()
/*      */   {
/*  678 */     return this._layoutTrailingComponentBeforeButtons;
/*      */   }
/*      */ 
/*      */   public void setLayoutTrailingComponentBeforeButtons(boolean layoutTrailingComponentBeforeButtons)
/*      */   {
/*  689 */     this._layoutTrailingComponentBeforeButtons = layoutTrailingComponentBeforeButtons;
/*      */   }
/*      */ 
/*      */   public void processMouseSelection(int tabIndex, MouseEvent e)
/*      */   {
/*      */   }
/*      */ 
/*      */   public int getTabHeight()
/*      */   {
/*  718 */     if ((getTabPlacement() == 1) || (getTabPlacement() == 3)) {
/*  719 */       return ((JideTabbedPaneUI)getUI()).getTabPanel().getHeight();
/*      */     }
/*      */ 
/*  722 */     return ((JideTabbedPaneUI)getUI()).getTabPanel().getWidth();
/*      */   }
/*      */ 
/*      */   public boolean isRightClickSelect()
/*      */   {
/*  732 */     return this._rightClickSelect;
/*      */   }
/*      */ 
/*      */   public void setRightClickSelect(boolean rightClickSelect)
/*      */   {
/*  741 */     this._rightClickSelect = rightClickSelect;
/*      */   }
/*      */ 
/*      */   public int getTabAtLocation(int x, int y) {
/*  745 */     int tabCount = getTabCount();
/*  746 */     for (int i = 0; i < tabCount; i++) {
/*  747 */       if (getUI().getTabBounds(this, i).contains(x, y)) {
/*  748 */         return i;
/*      */       }
/*      */     }
/*  751 */     return -1;
/*      */   }
/*      */ 
/*      */   public boolean isShowGripper()
/*      */   {
/*  761 */     return this._showGripper;
/*      */   }
/*      */ 
/*      */   public void setShowGripper(boolean showGripper)
/*      */   {
/*  770 */     boolean oldShowGripper = this._showGripper;
/*  771 */     if (oldShowGripper != showGripper) {
/*  772 */       this._showGripper = showGripper;
/*  773 */       firePropertyChange("gripper", oldShowGripper, this._showGripper);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isShowIconsOnTab()
/*      */   {
/*  783 */     return this._showIconsOnTab;
/*      */   }
/*      */ 
/*      */   public void setShowIconsOnTab(boolean showIconsOnTab)
/*      */   {
/*  793 */     boolean oldShowIconsOnTab = this._showIconsOnTab;
/*  794 */     if (oldShowIconsOnTab != showIconsOnTab) {
/*  795 */       this._showIconsOnTab = showIconsOnTab;
/*  796 */       firePropertyChange("showIconsOnTab", oldShowIconsOnTab, this._showIconsOnTab);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isUseDefaultShowIconsOnTab()
/*      */   {
/*  806 */     return this._useDefaultShowIconsOnTab;
/*      */   }
/*      */ 
/*      */   public void setUseDefaultShowIconsOnTab(boolean useDefaultShowIconsOnTab)
/*      */   {
/*  815 */     boolean oldUseDefaultShowIconsOnTab = this._useDefaultShowIconsOnTab;
/*  816 */     if (oldUseDefaultShowIconsOnTab != useDefaultShowIconsOnTab) {
/*  817 */       this._useDefaultShowIconsOnTab = useDefaultShowIconsOnTab;
/*  818 */       firePropertyChange("useDefaultShowIconsOnTab", oldUseDefaultShowIconsOnTab, this._useDefaultShowIconsOnTab);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isShowCloseButtonOnTab()
/*      */   {
/*  828 */     return this._showCloseButtonOnTab;
/*      */   }
/*      */ 
/*      */   public void setShowCloseButtonOnTab(boolean showCloseButtonOnTab)
/*      */   {
/*  839 */     boolean oldShowCloseButtonOnTab = this._showCloseButtonOnTab;
/*  840 */     if (oldShowCloseButtonOnTab != showCloseButtonOnTab) {
/*  841 */       this._showCloseButtonOnTab = showCloseButtonOnTab;
/*  842 */       firePropertyChange("showCloseButtonOnTab", oldShowCloseButtonOnTab, this._showCloseButtonOnTab);
/*  843 */       if (this._showCloseButtonOnTab) {
/*  844 */         setShowCloseButton(true);
/*      */       }
/*      */     }
/*  847 */     setUseDefaultShowCloseButtonOnTab(false);
/*      */   }
/*      */ 
/*      */   public boolean isUseDefaultShowCloseButtonOnTab()
/*      */   {
/*  856 */     return this._useDefaultShowCloseButtonOnTab;
/*      */   }
/*      */ 
/*      */   public void setUseDefaultShowCloseButtonOnTab(boolean useDefaultShowCloseButtonOnTab)
/*      */   {
/*  865 */     boolean oldUseDefaultShowCloseButtonOnTab = this._useDefaultShowCloseButtonOnTab;
/*  866 */     if (oldUseDefaultShowCloseButtonOnTab != useDefaultShowCloseButtonOnTab) {
/*  867 */       this._useDefaultShowCloseButtonOnTab = useDefaultShowCloseButtonOnTab;
/*  868 */       firePropertyChange("useDefaultShowCloseButtonOnTab", oldUseDefaultShowCloseButtonOnTab, this._useDefaultShowCloseButtonOnTab);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setTabEditingAllowed(boolean allowed)
/*      */   {
/*  882 */     this._tabEditingAllowed = allowed;
/*      */   }
/*      */ 
/*      */   public boolean isTabEditingAllowed()
/*      */   {
/*  892 */     return (this._tabEditingAllowed) && (getTabLayoutPolicy() == 1);
/*      */   }
/*      */ 
/*      */   public void setTabEditingValidator(TabEditingValidator tabEditValidator)
/*      */   {
/*  898 */     this._tabEditValidator = tabEditValidator;
/*      */   }
/*      */ 
/*      */   public TabEditingValidator getTabEditingValidator() {
/*  902 */     return this._tabEditValidator;
/*      */   }
/*      */ 
/*      */   public boolean isShowCloseButton()
/*      */   {
/*  911 */     return this._showCloseButton;
/*      */   }
/*      */ 
/*      */   public void setShowCloseButton(boolean showCloseButton)
/*      */   {
/*  921 */     boolean oldShowCloseButton = this._showCloseButton;
/*  922 */     if (oldShowCloseButton != showCloseButton) {
/*  923 */       this._showCloseButton = showCloseButton;
/*  924 */       firePropertyChange("showCloseButton", oldShowCloseButton, this._showCloseButton);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isShowTabArea()
/*      */   {
/*  934 */     return this._showTabArea;
/*      */   }
/*      */ 
/*      */   public void setShowTabArea(boolean showTabArea)
/*      */   {
/*  944 */     boolean oldShowTabArea = this._showTabArea;
/*  945 */     if (oldShowTabArea != showTabArea) {
/*  946 */       this._showTabArea = showTabArea;
/*  947 */       firePropertyChange("showTabArea", oldShowTabArea, this._showTabArea);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isShowTabContent()
/*      */   {
/*  957 */     return this._showTabContent;
/*      */   }
/*      */ 
/*      */   public void setShowTabContent(boolean showTabContent)
/*      */   {
/*  966 */     boolean oldShowTabContent = this._showTabContent;
/*  967 */     if (oldShowTabContent != showTabContent) {
/*  968 */       this._showTabContent = showTabContent;
/*  969 */       firePropertyChange("showTabContent", oldShowTabContent, this._showTabContent);
/*      */     }
/*      */   }
/*      */ 
/*      */   public StringConverter getStringConverter()
/*      */   {
/*  979 */     return this._stringConverter;
/*      */   }
/*      */ 
/*      */   public void setStringConverter(StringConverter stringConverter)
/*      */   {
/*  989 */     this._stringConverter = stringConverter;
/*      */   }
/*      */ 
/*      */   public String getDisplayTitleAt(int index)
/*      */   {
/* 1001 */     if (this._stringConverter != null) {
/* 1002 */       return this._stringConverter.convert(super.getTitleAt(index));
/*      */     }
/*      */ 
/* 1005 */     return super.getTitleAt(index);
/*      */   }
/*      */ 
/*      */   public boolean isBoldActiveTab()
/*      */   {
/* 1015 */     return this._boldActiveTab;
/*      */   }
/*      */ 
/*      */   public void setBoldActiveTab(boolean boldActiveTab)
/*      */   {
/* 1024 */     boolean old = this._boldActiveTab;
/* 1025 */     if (old != boldActiveTab) {
/* 1026 */       this._boldActiveTab = boldActiveTab;
/* 1027 */       firePropertyChange("boldActiveTab", old, this._boldActiveTab);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeTabAt(int index)
/*      */   {
/* 1033 */     int tabCount = getTabCount();
/* 1034 */     int selected = getSelectedIndex();
/* 1035 */     boolean enforce = false;
/* 1036 */     if ((selected == index) && (selected < tabCount - 1))
/*      */     {
/* 1039 */       enforce = !SystemInfo.isJdk15Above();
/*      */     }
/*      */ 
/* 1042 */     boolean contains = false;
/* 1043 */     String titleAt = getTitleAt(index);
/* 1044 */     if (this._closableMap.containsKey(titleAt)) {
/* 1045 */       contains = true;
/*      */     }
/*      */ 
/* 1048 */     Component component = getComponentAt(index);
/* 1049 */     if (!isAutoFocusOnTabHideClose()) {
/* 1050 */       clearVisComp();
/*      */     }
/* 1052 */     super.removeTabAt(index);
/*      */ 
/* 1055 */     if (contains) {
/* 1056 */       this._closableMap.remove(titleAt);
/*      */     }
/* 1058 */     if (component != null)
/*      */     {
/* 1060 */       PageLastFocusTracker tracker = (PageLastFocusTracker)this._pageLastFocusTrackers.get(component);
/* 1061 */       this._pageLastFocusTrackers.remove(component);
/* 1062 */       if (tracker != null) {
/* 1063 */         tracker.setHeighestComponent(null);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1068 */     if (enforce) {
/*      */       try {
/* 1070 */         fireStateChanged();
/*      */       }
/*      */       catch (Throwable th) {
/* 1073 */         th.printStackTrace();
/*      */       }
/*      */     }
/*      */ 
/* 1077 */     updateUI();
/*      */   }
/*      */ 
/*      */   public void setTitleAt(int index, String title)
/*      */   {
/* 1082 */     boolean contains = false;
/* 1083 */     if (this._closableMap.containsKey(getTitleAt(index))) {
/* 1084 */       contains = true;
/*      */     }
/* 1086 */     super.setTitleAt(index, title);
/* 1087 */     if (contains)
/* 1088 */       this._closableMap.put(title, "");
/*      */   }
/*      */ 
/*      */   public boolean isTabClosableAt(int tabIndex)
/*      */   {
/* 1103 */     return !this._closableMap.containsKey(Integer.valueOf(tabIndex));
/*      */   }
/*      */ 
/*      */   public void setTabClosableAt(int tabIndex, boolean closable)
/*      */   {
/* 1119 */     if (closable) {
/* 1120 */       this._closableMap.remove(Integer.valueOf(tabIndex));
/*      */     }
/*      */     else {
/* 1123 */       this._closableMap.put(Integer.valueOf(tabIndex), Boolean.FALSE);
/*      */     }
/* 1125 */     firePropertyChange("tabClosable", !closable, closable);
/*      */   }
/*      */ 
/*      */   protected Hashtable getPageLastFocusTrackers() {
/* 1129 */     return this._pageLastFocusTrackers;
/*      */   }
/*      */ 
/*      */   public Component getLastFocusedComponent(Component pageComponent)
/*      */   {
/* 1140 */     if (pageComponent == null) {
/* 1141 */       return null;
/*      */     }
/*      */ 
/* 1145 */     PageLastFocusTracker tracker = (PageLastFocusTracker)(PageLastFocusTracker)getPageLastFocusTrackers().get(pageComponent);
/*      */ 
/* 1170 */     return tracker != null ? tracker.getLastFocusedComponent() : null;
/*      */   }
/*      */ 
/*      */   protected void clearVisComp()
/*      */   {
/*      */     try
/*      */     {
/* 1177 */       Field field = JTabbedPane.class.getDeclaredField("visComp");
/*      */ 
/* 1179 */       field.setAccessible(true);
/* 1180 */       field.set(this, null);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void insertTab(String title, Icon icon, Component component, String tip, int index)
/*      */   {
/* 1196 */     if ((component == getTabLeadingComponent()) || (component == getTabTrailingComponent())) {
/* 1197 */       return;
/*      */     }
/* 1199 */     if ((component != null) && (!component.isVisible())) {
/* 1200 */       component.setVisible(false);
/*      */     }
/* 1202 */     super.insertTab(title, icon, component, tip, index);
/*      */ 
/* 1204 */     if (component != null)
/*      */     {
/* 1206 */       this._pageLastFocusTrackers.put(component, new PageLastFocusTracker(component));
/*      */     }
/*      */   }
/*      */ 
/*      */   public Font getSelectedTabFont()
/*      */   {
/* 1255 */     return this._selectedTabFont;
/*      */   }
/*      */ 
/*      */   public void setSelectedTabFont(Font selectedTabFont)
/*      */   {
/* 1264 */     this._selectedTabFont = selectedTabFont;
/*      */   }
/*      */ 
/*      */   public int getColorTheme() {
/* 1268 */     if (this._colorTheme == 0) {
/* 1269 */       return getDefaultColorTheme();
/*      */     }
/*      */ 
/* 1272 */     return this._colorTheme;
/*      */   }
/*      */ 
/*      */   public int getDefaultColorTheme()
/*      */   {
/* 1277 */     return UIDefaultsLookup.getInt("JideTabbedPane.defaultTabColorTheme");
/*      */   }
/*      */ 
/*      */   public void setColorTheme(int colorTheme) {
/* 1281 */     int old = this._colorTheme;
/* 1282 */     if (old != colorTheme) {
/* 1283 */       this._colorTheme = colorTheme;
/* 1284 */       firePropertyChange("colorTheme", old, colorTheme);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getTabResizeMode() {
/* 1289 */     if (this._tabResizeMode == 0) {
/* 1290 */       return getDefaultTabResizeMode();
/*      */     }
/*      */ 
/* 1293 */     return this._tabResizeMode;
/*      */   }
/*      */ 
/*      */   public void setTabResizeMode(int resizeMode)
/*      */   {
/* 1304 */     int old = this._tabResizeMode;
/* 1305 */     if (old != resizeMode) {
/* 1306 */       this._tabResizeMode = resizeMode;
/* 1307 */       firePropertyChange("tabResizeMode", old, resizeMode);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getDefaultTabResizeMode() {
/* 1312 */     return UIDefaultsLookup.getInt("JideTabbedPane.defaultResizeMode");
/*      */   }
/*      */ 
/*      */   public int getTabShape()
/*      */   {
/* 1317 */     if (this._tabShape == 0) {
/* 1318 */       return getDefaultTabStyle();
/*      */     }
/*      */ 
/* 1321 */     return this._tabShape;
/*      */   }
/*      */ 
/*      */   public int getDefaultTabStyle()
/*      */   {
/* 1326 */     return UIDefaultsLookup.getInt("JideTabbedPane.defaultTabShape");
/*      */   }
/*      */ 
/*      */   public void setTabShape(int tabShape) {
/* 1330 */     int old = this._tabShape;
/* 1331 */     if (old != tabShape) {
/* 1332 */       this._tabShape = tabShape;
/* 1333 */       firePropertyChange("tabShape", old, this._tabShape);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setTabLeadingComponent(Component component)
/*      */   {
/* 1348 */     Component old = this._tabLeadingComponent;
/* 1349 */     this._tabLeadingComponent = component;
/* 1350 */     firePropertyChange("tabLeadingComponent", old, component);
/*      */   }
/*      */ 
/*      */   public Component getTabLeadingComponent() {
/* 1354 */     return this._tabLeadingComponent;
/*      */   }
/*      */ 
/*      */   public void setTabTrailingComponent(Component component)
/*      */   {
/* 1368 */     Component old = this._tabTrailingComponent;
/* 1369 */     this._tabTrailingComponent = component;
/* 1370 */     firePropertyChange("tabTrailingComponent", old, component);
/*      */   }
/*      */ 
/*      */   public Component getTabTrailingComponent() {
/* 1374 */     return this._tabTrailingComponent;
/*      */   }
/*      */ 
/*      */   public boolean isShowCloseButtonOnSelectedTab() {
/* 1378 */     return this._showCloseButtonOnSelectedTab;
/*      */   }
/*      */ 
/*      */   public void setShowCloseButtonOnSelectedTab(boolean i)
/*      */   {
/* 1388 */     this._showCloseButtonOnSelectedTab = i;
/*      */   }
/*      */ 
/*      */   public ColorProvider getTabColorProvider()
/*      */   {
/* 1473 */     return this._tabColorProvider;
/*      */   }
/*      */ 
/*      */   public void setTabColorProvider(ColorProvider tabColorProvider)
/*      */   {
/* 1486 */     ColorProvider old = this._tabColorProvider;
/* 1487 */     if (old != tabColorProvider) {
/* 1488 */       this._tabColorProvider = tabColorProvider;
/* 1489 */       firePropertyChange("tabColorProvider", old, tabColorProvider);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void editTabAt(int tabIndex)
/*      */   {
/* 1499 */     boolean started = ((JideTabbedPaneUI)getUI()).editTabAt(tabIndex);
/* 1500 */     if (started)
/* 1501 */       fireTabEditing(3099, tabIndex, getTitleAt(tabIndex), null);
/*      */   }
/*      */ 
/*      */   public boolean isTabEditing()
/*      */   {
/* 1511 */     return ((JideTabbedPaneUI)getUI()).isTabEditing();
/*      */   }
/*      */ 
/*      */   public void stopTabEditing() {
/* 1515 */     int tabIndex = getEditingTabIndex();
/* 1516 */     if ((tabIndex != -1) && (tabIndex < getTabCount())) {
/* 1517 */       String oldTitle = getTitleAt(tabIndex);
/* 1518 */       ((JideTabbedPaneUI)getUI()).stopTabEditing();
/* 1519 */       String newTitle = getTitleAt(tabIndex);
/* 1520 */       fireTabEditing(3100, tabIndex, oldTitle, newTitle);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void cancelTabEditing() {
/* 1525 */     int tabIndex = getEditingTabIndex();
/* 1526 */     if (tabIndex != -1) {
/* 1527 */       ((JideTabbedPaneUI)getUI()).cancelTabEditing();
/* 1528 */       fireTabEditing(3101, tabIndex, getTitleAt(tabIndex), getTitleAt(tabIndex));
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getEditingTabIndex() {
/* 1533 */     return ((JideTabbedPaneUI)getUI()).getEditingTabIndex();
/*      */   }
/*      */ 
/*      */   protected PropertyChangeListener createFocusChangeListener()
/*      */   {
/* 1539 */     return new PropertyChangeListener() {
/*      */       public void propertyChange(PropertyChangeEvent evt) {
/* 1541 */         boolean hadFocus = (JideTabbedPane.this.isAncestorOf((Component)evt.getOldValue())) || (JideTabbedPane.this == evt.getOldValue());
/* 1542 */         boolean hasFocus = (JideTabbedPane.this == evt.getNewValue()) || (JideTabbedPane.this.hasFocusComponent());
/* 1543 */         if (hasFocus != hadFocus)
/* 1544 */           JideTabbedPane.this.repaintTabAreaAndContentBorder();
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public void repaintTabAreaAndContentBorder()
/*      */   {
/* 1555 */     int delay = 200;
/* 1556 */     ((JideTabbedPaneUI)getUI()).getTabPanel().repaint(delay);
/*      */ 
/* 1558 */     Insets contentinsets = getContentBorderInsets();
/* 1559 */     if (contentinsets == null) {
/* 1560 */       LookAndFeelFactory.installJideExtension();
/* 1561 */       contentinsets = getContentBorderInsets();
/*      */     }
/*      */ 
/* 1564 */     if ((contentinsets != null) && ((contentinsets.top != 0) || (contentinsets.bottom != 0) || (contentinsets.left != 0) || (contentinsets.right != 0))) {
/* 1565 */       Insets insets = new Insets(0, 0, 0, 0);
/* 1566 */       BasicJideTabbedPaneUI.rotateInsets(contentinsets, insets, this.tabPlacement);
/* 1567 */       switch (getTabPlacement()) {
/*      */       case 1:
/* 1569 */         insets.top += getTabHeight();
/* 1570 */         break;
/*      */       case 3:
/* 1572 */         insets.bottom += getTabHeight();
/* 1573 */         break;
/*      */       case 2:
/* 1575 */         insets.left += getTabHeight();
/* 1576 */         break;
/*      */       case 4:
/* 1578 */         insets.right += getTabHeight();
/*      */       }
/*      */ 
/* 1581 */       if (insets.top != 0) {
/* 1582 */         repaintContentBorder(0, 0, getWidth(), insets.top);
/*      */       }
/* 1584 */       if (insets.left != 0) {
/* 1585 */         repaintContentBorder(0, 0, insets.left, getHeight());
/*      */       }
/* 1587 */       if (insets.right != 0) {
/* 1588 */         repaintContentBorder(getWidth() - insets.right, 0, insets.right, getHeight());
/*      */       }
/* 1590 */       if (insets.bottom != 0)
/* 1591 */         repaintContentBorder(0, getHeight() - insets.bottom, getWidth(), insets.bottom);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void repaintContentBorder(int x, int y, int width, int height)
/*      */   {
/* 1605 */     repaint(x, y, width, height);
/*      */   }
/*      */ 
/*      */   public void addNotify()
/*      */   {
/* 1610 */     super.addNotify();
/* 1611 */     if (this._focusChangeListener == null) {
/* 1612 */       this._focusChangeListener = createFocusChangeListener();
/* 1613 */       KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener("focusOwner", this._focusChangeListener);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeNotify()
/*      */   {
/* 1620 */     super.removeNotify();
/* 1621 */     if (this._focusChangeListener != null) {
/* 1622 */       KeyboardFocusManager.getCurrentKeyboardFocusManager().removePropertyChangeListener("focusOwner", this._focusChangeListener);
/* 1623 */       this._focusChangeListener = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public ListCellRenderer getTabListCellRenderer()
/*      */   {
/* 1635 */     if (this._tabListCellRenderer != null) {
/* 1636 */       return this._tabListCellRenderer;
/*      */     }
/*      */ 
/* 1639 */     return new TabListCellRenderer();
/*      */   }
/*      */ 
/*      */   public void setTabListCellRenderer(ListCellRenderer tabListCellRenderer)
/*      */   {
/* 1707 */     this._tabListCellRenderer = tabListCellRenderer;
/*      */   }
/*      */ 
/*      */   public boolean hasFocusComponent()
/*      */   {
/* 1717 */     return JideSwingUtilities.isAncestorOfFocusOwner(this);
/*      */   }
/*      */ 
/*      */   public Insets getContentBorderInsets() {
/* 1721 */     return this._contentBorderInsets;
/*      */   }
/*      */ 
/*      */   public void setContentBorderInsets(Insets contentBorderInsets)
/*      */   {
/* 1731 */     Insets old = this._contentBorderInsets;
/* 1732 */     this._contentBorderInsets = contentBorderInsets;
/* 1733 */     firePropertyChange("contentBorderInsets", old, this._contentBorderInsets);
/*      */   }
/*      */ 
/*      */   public Insets getTabAreaInsets() {
/* 1737 */     return this._tabAreaInsets;
/*      */   }
/*      */ 
/*      */   public void setTabAreaInsets(Insets tabAreaInsets)
/*      */   {
/* 1747 */     Insets old = this._tabAreaInsets;
/* 1748 */     this._tabAreaInsets = tabAreaInsets;
/* 1749 */     firePropertyChange("tabAreaInsets", old, this._tabAreaInsets);
/*      */   }
/*      */ 
/*      */   public Insets getTabInsets() {
/* 1753 */     return this._tabInsets;
/*      */   }
/*      */ 
/*      */   public void setTabInsets(Insets tabInsets)
/*      */   {
/* 1763 */     Insets old = this._tabInsets;
/* 1764 */     this._tabInsets = tabInsets;
/* 1765 */     firePropertyChange("tabInsets", old, this._tabInsets);
/*      */   }
/*      */ 
/*      */   public boolean isDragOverDisabled()
/*      */   {
/* 1775 */     return this._dragOverDisabled;
/*      */   }
/*      */ 
/*      */   public void setDragOverDisabled(boolean dragOverDisabled)
/*      */   {
/* 1786 */     boolean old = this._dragOverDisabled;
/* 1787 */     if (old != dragOverDisabled) {
/* 1788 */       this._dragOverDisabled = dragOverDisabled;
/* 1789 */       firePropertyChange("dragOverDisabled", old, dragOverDisabled);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void scrollSelectedTabToVisible(boolean scrollLeft)
/*      */   {
/* 1802 */     ((JideTabbedPaneUI)getUI()).ensureActiveTabIsVisible(scrollLeft);
/*      */   }
/*      */ 
/*      */   public void addTabEditingListener(TabEditingListener l)
/*      */   {
/* 1814 */     this.listenerList.add(TabEditingListener.class, l);
/*      */   }
/*      */ 
/*      */   public void removeTabEditingListener(TabEditingListener l)
/*      */   {
/* 1825 */     this.listenerList.remove(TabEditingListener.class, l);
/*      */   }
/*      */ 
/*      */   public TabEditingListener[] getTabEditingListeners()
/*      */   {
/* 1835 */     return (TabEditingListener[])this.listenerList.getListeners(TabEditingListener.class);
/*      */   }
/*      */ 
/*      */   protected void fireTabEditing(int id, int index, String oldTitle, String newTitle) {
/* 1839 */     if (LOGGER_EVENT.isLoggable(Level.FINE)) {
/* 1840 */       switch (id) {
/*      */       case 3099:
/* 1842 */         LOGGER_EVENT.fine("TabEditing Started at tab \"" + index + "\"; the current title is " + oldTitle);
/* 1843 */         break;
/*      */       case 3100:
/* 1845 */         LOGGER_EVENT.fine("TabEditing Stopped at tab \"" + index + "\"; the old title is " + oldTitle + "; the new title is " + newTitle);
/* 1846 */         break;
/*      */       case 3101:
/* 1848 */         LOGGER_EVENT.fine("TabEditing Cancelled at tab \"" + index + "\"; the current title remains " + oldTitle);
/*      */       }
/*      */     }
/*      */ 
/* 1852 */     Object[] listeners = this.listenerList.getListenerList();
/* 1853 */     for (int i = listeners.length - 2; i >= 0; i -= 2)
/* 1854 */       if (listeners[i] == TabEditingListener.class) {
/* 1855 */         TabEditingEvent tabEditingEvent = new TabEditingEvent(this, id, index, oldTitle, newTitle);
/* 1856 */         if (id == 3099) {
/* 1857 */           ((TabEditingListener)listeners[(i + 1)]).editingStarted(tabEditingEvent);
/*      */         }
/* 1859 */         else if (id == 3101) {
/* 1860 */           ((TabEditingListener)listeners[(i + 1)]).editingCanceled(tabEditingEvent);
/*      */         }
/* 1862 */         else if (id == 3100)
/* 1863 */           ((TabEditingListener)listeners[(i + 1)]).editingStopped(tabEditingEvent);
/*      */       }
/*      */   }
/*      */ 
/*      */   public Icon getIconForTab(int tabIndex)
/*      */   {
/* 1878 */     boolean _showIconOnTab = UIDefaultsLookup.getBoolean("JideTabbedPane.showIconOnTab");
/* 1879 */     if (isUseDefaultShowIconsOnTab()) {
/* 1880 */       if (_showIconOnTab) {
/* 1881 */         return (!isEnabled()) || (!isEnabledAt(tabIndex)) ? getDisabledIconAt(tabIndex) : getIconAt(tabIndex);
/*      */       }
/*      */ 
/* 1884 */       return null;
/*      */     }
/*      */ 
/* 1887 */     if (isShowIconsOnTab()) {
/* 1888 */       return (!isEnabled()) || (!isEnabledAt(tabIndex)) ? getDisabledIconAt(tabIndex) : getIconAt(tabIndex);
/*      */     }
/*      */ 
/* 1891 */     return null;
/*      */   }
/*      */ 
/*      */   public boolean isScrollSelectedTabOnWheel()
/*      */   {
/* 1901 */     return this._scrollSelectedTabOnWheel;
/*      */   }
/*      */ 
/*      */   public void setScrollSelectedTabOnWheel(boolean scrollSelectedTabOnWheel)
/*      */   {
/* 1910 */     boolean oldValue = isScrollSelectedTabOnWheel();
/* 1911 */     if (oldValue != scrollSelectedTabOnWheel) {
/* 1912 */       this._scrollSelectedTabOnWheel = scrollSelectedTabOnWheel;
/* 1913 */       firePropertyChange("scrollTabOnWheel", oldValue, this._scrollSelectedTabOnWheel);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isCloseTabOnMouseMiddleButton()
/*      */   {
/* 1923 */     return this._closeTabOnMouseMiddleButton;
/*      */   }
/*      */ 
/*      */   public void setCloseTabOnMouseMiddleButton(boolean closeTabOnMouseMiddleButton)
/*      */   {
/* 1932 */     this._closeTabOnMouseMiddleButton = closeTabOnMouseMiddleButton;
/*      */   }
/*      */ 
/*      */   public int getTabAlignment()
/*      */   {
/* 1941 */     return this._tabAlignment;
/*      */   }
/*      */ 
/*      */   public void setTabAlignment(int tabAlignment)
/*      */   {
/* 1958 */     if ((tabAlignment != 10) && (tabAlignment != 0)) {
/* 1959 */       throw new IllegalArgumentException("illegal tab alignment: must be LEADING or CENTER");
/*      */     }
/* 1961 */     if (this._tabAlignment != tabAlignment) {
/* 1962 */       int oldValue = this._tabAlignment;
/* 1963 */       this._tabAlignment = tabAlignment;
/* 1964 */       firePropertyChange("tabAlignment", oldValue, tabAlignment);
/* 1965 */       revalidate();
/* 1966 */       repaint();
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getResourceString(String key)
/*      */   {
/* 1977 */     return Resource.getResourceBundle(getLocale()).getString(key);
/*      */   }
/*      */ 
/*      */   public NoFocusButton createNoFocusButton(int type)
/*      */   {
/* 1987 */     return new NoFocusButton(type);
/*      */   }
/*      */ 
/*      */   public class NoFocusButton extends JButton
/*      */     implements MouseMotionListener, MouseListener, UIResource
/*      */   {
/*      */     private int _type;
/* 1992 */     private int _index = -1;
/* 1993 */     private boolean _mouseOver = false;
/* 1994 */     private boolean _mousePressed = false;
/*      */ 
/*      */     public void updateUI()
/*      */     {
/* 2003 */       super.updateUI();
/* 2004 */       setMargin(new Insets(0, 0, 0, 0));
/* 2005 */       setBorder(BorderFactory.createEmptyBorder());
/* 2006 */       setFocusPainted(false);
/* 2007 */       setFocusable(false);
/* 2008 */       setRequestFocusEnabled(false);
/* 2009 */       String name = getName();
/* 2010 */       if (name != null) setToolTipText(JideTabbedPane.this.getResourceString(name)); 
/*      */     }
/*      */ 
/*      */     public NoFocusButton()
/*      */     {
/* 2014 */       this(0);
/*      */     }
/*      */ 
/*      */     public NoFocusButton(int type) {
/* 2018 */       addMouseMotionListener(this);
/* 2019 */       addMouseListener(this);
/* 2020 */       setFocusPainted(false);
/* 2021 */       setFocusable(false);
/* 2022 */       setType(type);
/*      */     }
/*      */ 
/*      */     public Dimension getPreferredSize()
/*      */     {
/* 2027 */       return new Dimension(16, 16);
/*      */     }
/*      */ 
/*      */     public Dimension getMinimumSize()
/*      */     {
/* 2032 */       return new Dimension(5, 5);
/*      */     }
/*      */ 
/*      */     public int getIndex() {
/* 2036 */       return this._index;
/*      */     }
/*      */ 
/*      */     public void setIndex(int index) {
/* 2040 */       this._index = index;
/*      */     }
/*      */ 
/*      */     public Dimension getMaximumSize()
/*      */     {
/* 2045 */       return new Dimension(2147483647, 2147483647);
/*      */     }
/*      */ 
/*      */     protected void paintComponent(Graphics g)
/*      */     {
/* 2050 */       if (getIcon() != null) {
/* 2051 */         super.paintComponent(g);
/* 2052 */         return;
/*      */       }
/* 2054 */       if (!isEnabled()) {
/* 2055 */         setMouseOver(false);
/* 2056 */         setMousePressed(false);
/*      */       }
/* 2058 */       if ((isMouseOver()) && (isMousePressed())) {
/* 2059 */         g.setColor(getPressedShadowColor());
/* 2060 */         g.drawLine(0, 0, getWidth() - 1, 0);
/* 2061 */         g.drawLine(0, getHeight() - 2, 0, 1);
/* 2062 */         g.setColor(getShadowColor());
/* 2063 */         g.drawLine(getWidth() - 1, 1, getWidth() - 1, getHeight() - 2);
/* 2064 */         g.drawLine(getWidth() - 1, getHeight() - 1, 0, getHeight() - 1);
/*      */       }
/* 2066 */       else if (isMouseOver()) {
/* 2067 */         g.setColor(getShadowColor());
/* 2068 */         g.drawLine(0, 0, getWidth() - 1, 0);
/* 2069 */         g.drawLine(0, getHeight() - 2, 0, 1);
/* 2070 */         g.setColor(getPressedShadowColor());
/* 2071 */         g.drawLine(getWidth() - 1, 1, getWidth() - 1, getHeight() - 2);
/* 2072 */         g.drawLine(getWidth() - 1, getHeight() - 1, 0, getHeight() - 1);
/*      */       }
/* 2074 */       g.setColor(getForegroundColor());
/* 2075 */       int centerX = getWidth() >> 1;
/* 2076 */       int centerY = getHeight() >> 1;
/* 2077 */       int type = getType();
/* 2078 */       if (((JideTabbedPane.this.getTabPlacement() == 1) || (JideTabbedPane.this.getTabPlacement() == 3)) && (!getComponentOrientation().isLeftToRight())) {
/* 2079 */         if (type == 1) {
/* 2080 */           type = 2;
/*      */         }
/* 2082 */         else if (type == 2) {
/* 2083 */           type = 1;
/*      */         }
/*      */       }
/* 2086 */       switch (type) {
/*      */       case 0:
/* 2088 */         if (isEnabled()) {
/* 2089 */           g.drawLine(centerX - 3, centerY - 3, centerX + 3, centerY + 3);
/* 2090 */           g.drawLine(centerX - 4, centerY - 3, centerX + 2, centerY + 3);
/* 2091 */           g.drawLine(centerX + 3, centerY - 3, centerX - 3, centerY + 3);
/* 2092 */           g.drawLine(centerX + 2, centerY - 3, centerX - 4, centerY + 3);
/*      */         }
/*      */         else {
/* 2095 */           g.drawLine(centerX - 3, centerY - 3, centerX + 3, centerY + 3);
/* 2096 */           g.drawLine(centerX + 3, centerY - 3, centerX - 3, centerY + 3);
/*      */         }
/* 2098 */         break;
/*      */       case 1:
/* 2112 */         if ((JideTabbedPane.this.getTabPlacement() == 1) || (JideTabbedPane.this.getTabPlacement() == 3)) {
/* 2113 */           int x = centerX + 2; int y = centerY;
/* 2114 */           if (isEnabled()) {
/* 2115 */             g.drawLine(x - 4, y - 4, x - 4, y + 4);
/* 2116 */             g.drawLine(x - 3, y - 3, x - 3, y + 3);
/* 2117 */             g.drawLine(x - 2, y - 2, x - 2, y + 2);
/* 2118 */             g.drawLine(x - 1, y - 1, x - 1, y + 1);
/* 2119 */             g.drawLine(x, y, x, y);
/*      */           }
/*      */           else {
/* 2122 */             g.drawLine(x - 4, y - 4, x, y);
/* 2123 */             g.drawLine(x - 4, y - 4, x - 4, y + 4);
/* 2124 */             g.drawLine(x - 4, y + 4, x, y);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 2129 */           int x = centerX + 3; int y = centerY - 2;
/* 2130 */           if (isEnabled()) {
/* 2131 */             g.drawLine(x - 8, y, x, y);
/* 2132 */             g.drawLine(x - 7, y + 1, x - 1, y + 1);
/* 2133 */             g.drawLine(x - 6, y + 2, x - 2, y + 2);
/* 2134 */             g.drawLine(x - 5, y + 3, x - 3, y + 3);
/* 2135 */             g.drawLine(x - 4, y + 4, x - 4, y + 4);
/*      */           }
/*      */           else {
/* 2138 */             g.drawLine(x - 8, y, x, y);
/* 2139 */             g.drawLine(x - 8, y, x - 4, y + 4);
/* 2140 */             g.drawLine(x - 4, y + 4, x, y);
/*      */           }
/*      */         }
/*      */ 
/* 2144 */         break;
/*      */       case 2:
/* 2158 */         if ((JideTabbedPane.this.getTabPlacement() == 1) || (JideTabbedPane.this.getTabPlacement() == 3)) {
/* 2159 */           int x = centerX - 3; int y = centerY;
/* 2160 */           if (isEnabled()) {
/* 2161 */             g.drawLine(x, y, x, y);
/* 2162 */             g.drawLine(x + 1, y - 1, x + 1, y + 1);
/* 2163 */             g.drawLine(x + 2, y - 2, x + 2, y + 2);
/* 2164 */             g.drawLine(x + 3, y - 3, x + 3, y + 3);
/* 2165 */             g.drawLine(x + 4, y - 4, x + 4, y + 4);
/*      */           }
/*      */           else {
/* 2168 */             g.drawLine(x, y, x + 4, y - 4);
/* 2169 */             g.drawLine(x, y, x + 4, y + 4);
/* 2170 */             g.drawLine(x + 4, y - 4, x + 4, y + 4);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 2175 */           int x = centerX - 5; int y = centerY + 3;
/* 2176 */           if (isEnabled()) {
/* 2177 */             g.drawLine(x, y, x + 8, y);
/* 2178 */             g.drawLine(x + 1, y - 1, x + 7, y - 1);
/* 2179 */             g.drawLine(x + 2, y - 2, x + 6, y - 2);
/* 2180 */             g.drawLine(x + 3, y - 3, x + 5, y - 3);
/* 2181 */             g.drawLine(x + 4, y - 4, x + 4, y - 4);
/*      */           }
/*      */           else {
/* 2184 */             g.drawLine(x, y, x + 8, y);
/* 2185 */             g.drawLine(x, y, x + 4, y - 4);
/* 2186 */             g.drawLine(x + 8, y, x + 4, y - 4);
/*      */           }
/*      */         }
/*      */ 
/* 2190 */         break;
/*      */       case 5:
/* 2193 */         int x = centerX + 2; int y = centerY;
/*      */ 
/* 2195 */         g.drawLine(x - 6, y - 4, x - 6, y + 4);
/* 2196 */         g.drawLine(x + 1, y - 4, x + 1, y + 4);
/* 2197 */         g.drawLine(x - 6, y - 4, x + 1, y - 4);
/* 2198 */         g.drawLine(x - 4, y - 2, x - 1, y - 2);
/* 2199 */         g.drawLine(x - 4, y, x - 1, y);
/* 2200 */         g.drawLine(x - 4, y + 2, x - 1, y + 2);
/* 2201 */         g.drawLine(x - 6, y + 4, x + 1, y + 4);
/* 2202 */         break;
/*      */       case 3:
/*      */       case 4:
/*      */       }
/*      */     }
/*      */ 
/*      */     protected Color getForegroundColor() {
/* 2208 */       return UIDefaultsLookup.getColor("controlShadow").darker();
/*      */     }
/*      */ 
/*      */     protected Color getShadowColor() {
/* 2212 */       return UIDefaultsLookup.getColor("control");
/*      */     }
/*      */ 
/*      */     protected Color getPressedShadowColor() {
/* 2216 */       return UIDefaultsLookup.getColor("controlDkShadow");
/*      */     }
/*      */ 
/*      */     public boolean isFocusable()
/*      */     {
/* 2221 */       return false;
/*      */     }
/*      */ 
/*      */     public void requestFocus()
/*      */     {
/*      */     }
/*      */ 
/*      */     public boolean isOpaque()
/*      */     {
/* 2230 */       return false;
/*      */     }
/*      */ 
/*      */     public void mouseDragged(MouseEvent e) {
/*      */     }
/*      */ 
/*      */     public void mouseMoved(MouseEvent e) {
/* 2237 */       if (!isEnabled()) return;
/* 2238 */       setMouseOver(true);
/* 2239 */       repaint();
/*      */     }
/*      */ 
/*      */     public void mouseClicked(MouseEvent e) {
/* 2243 */       if (!isEnabled()) return;
/* 2244 */       setMouseOver(true);
/* 2245 */       setMousePressed(false);
/*      */     }
/*      */ 
/*      */     public void mousePressed(MouseEvent e) {
/* 2249 */       if (!isEnabled()) return;
/* 2250 */       setMousePressed(true);
/* 2251 */       repaint();
/*      */     }
/*      */ 
/*      */     public void mouseReleased(MouseEvent e) {
/* 2255 */       if (!isEnabled()) return;
/* 2256 */       setMousePressed(false);
/* 2257 */       setMouseOver(false);
/*      */     }
/*      */ 
/*      */     public void mouseEntered(MouseEvent e) {
/* 2261 */       if (!isEnabled()) return;
/* 2262 */       setMouseOver(true);
/* 2263 */       repaint();
/*      */     }
/*      */ 
/*      */     public void mouseExited(MouseEvent e) {
/* 2267 */       if (!isEnabled()) return;
/* 2268 */       setMouseOver(false);
/* 2269 */       setMousePressed(false);
/* 2270 */       repaint();
/* 2271 */       JideTabbedPane.this.repaint();
/*      */     }
/*      */ 
/*      */     public int getType() {
/* 2275 */       return this._type;
/*      */     }
/*      */ 
/*      */     public void setType(int type) {
/* 2279 */       this._type = type;
/*      */     }
/*      */ 
/*      */     public boolean isMouseOver() {
/* 2283 */       return this._mouseOver;
/*      */     }
/*      */ 
/*      */     public void setMouseOver(boolean mouseOver) {
/* 2287 */       this._mouseOver = mouseOver;
/*      */     }
/*      */ 
/*      */     public boolean isMousePressed() {
/* 2291 */       return this._mousePressed;
/*      */     }
/*      */ 
/*      */     public void setMousePressed(boolean mousePressed) {
/* 2295 */       this._mousePressed = mousePressed;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class TabListCellRenderer extends DefaultListCellRenderer
/*      */   {
/*      */     public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
/*      */     {
/* 1649 */       if ((value instanceof JideTabbedPane)) {
/* 1650 */         JideTabbedPane tabbedPane = (JideTabbedPane)value;
/* 1651 */         String title = tabbedPane.getTitleAt(index);
/* 1652 */         String tooltip = tabbedPane.getToolTipTextAt(index);
/* 1653 */         Icon icon = tabbedPane.getIconForTab(index);
/* 1654 */         JLabel label = (JLabel)super.getListCellRendererComponent(list, title, index, isSelected, cellHasFocus);
/* 1655 */         label.setToolTipText(tooltip);
/*      */         Font fnt;
/*      */         Font fnt;
/* 1657 */         if (tabbedPane.getSelectedIndex() == index) {
/* 1658 */           fnt = tabbedPane.getSelectedTabFont();
/*      */         }
/*      */         else {
/* 1661 */           fnt = tabbedPane.getFont();
/*      */         }
/*      */ 
/* 1664 */         if ((tabbedPane.getSelectedIndex() == index) && (tabbedPane.isBoldActiveTab()) && (fnt.getStyle() != 1)) {
/* 1665 */           fnt = fnt.deriveFont(1);
/*      */         }
/* 1667 */         label.setFont(fnt);
/* 1668 */         label.setIcon(icon);
/* 1669 */         label.setEnabled(tabbedPane.isEnabledAt(index));
/* 1670 */         return label;
/*      */       }
/*      */ 
/* 1673 */       return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class OneNoteColorProvider
/*      */     implements JideTabbedPane.ColorProvider
/*      */   {
/*      */     public Color getBackgroundAt(int index)
/*      */     {
/* 1454 */       return JideTabbedPane.ONENOTE_COLORS[(index % JideTabbedPane.ONENOTE_COLORS.length)];
/*      */     }
/*      */ 
/*      */     public Color getForegroudAt(int index) {
/* 1458 */       return Color.BLACK;
/*      */     }
/*      */ 
/*      */     public float getGradientRatio(int tabIndex) {
/* 1462 */       return 0.86F;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static abstract interface GradientColorProvider extends JideTabbedPane.ColorProvider
/*      */   {
/*      */     public abstract Color getTopBackgroundAt(int paramInt);
/*      */   }
/*      */ 
/*      */   public static abstract interface ColorProvider
/*      */   {
/*      */     public abstract Color getBackgroundAt(int paramInt);
/*      */ 
/*      */     public abstract Color getForegroudAt(int paramInt);
/*      */ 
/*      */     public abstract float getGradientRatio(int paramInt);
/*      */   }
/*      */ 
/*      */   protected class PageLastFocusTracker extends JideFocusTracker
/*      */   {
/*      */     private Component _lastFocusedComponent;
/*      */     private FocusListener _lastFocusedListener;
/*      */ 
/*      */     protected PageLastFocusTracker(Component pageComp)
/*      */     {
/* 1219 */       setHeighestComponent(pageComp);
/*      */     }
/*      */ 
/*      */     protected Component getLastFocusedComponent() {
/* 1223 */       return this._lastFocusedComponent;
/*      */     }
/*      */ 
/*      */     public void setHeighestComponent(Component compHeighest)
/*      */     {
/* 1228 */       if (compHeighest == null) {
/* 1229 */         if (this._lastFocusedListener != null) {
/* 1230 */           removeFocusListener(this._lastFocusedListener);
/* 1231 */           this._lastFocusedListener = null;
/*      */         }
/*      */ 
/*      */       }
/* 1235 */       else if (this._lastFocusedListener == null) {
/* 1236 */         this._lastFocusedListener = new FocusAdapter()
/*      */         {
/*      */           public void focusGained(FocusEvent e) {
/* 1239 */             JideTabbedPane.PageLastFocusTracker.access$102(JideTabbedPane.PageLastFocusTracker.this, e.getComponent());
/*      */           }
/*      */         };
/* 1242 */         addFocusListener(this._lastFocusedListener);
/*      */       }
/*      */ 
/* 1245 */       super.setHeighestComponent(compHeighest);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected class IgnoreableSingleSelectionModel extends DefaultSingleSelectionModel
/*      */   {
/*      */     private static final long serialVersionUID = -4321082126384337792L;
/*      */ 
/*      */     protected IgnoreableSingleSelectionModel()
/*      */     {
/*      */     }
/*      */ 
/*      */     protected void fireStateChanged()
/*      */     {
/*  701 */       if (!JideTabbedPane.this._suppressStateChangedEvents)
/*  702 */         super.fireStateChanged();
/*      */     }
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.JideTabbedPane
 * JD-Core Version:    0.6.0
 */
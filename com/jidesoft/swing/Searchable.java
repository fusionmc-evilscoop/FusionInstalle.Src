/*      */ package com.jidesoft.swing;
/*      */ 
/*      */ import com.jidesoft.plaf.UIDefaultsLookup;
/*      */ import com.jidesoft.popup.JidePopup;
/*      */ import com.jidesoft.swing.event.SearchableEvent;
/*      */ import com.jidesoft.swing.event.SearchableListener;
/*      */ import com.jidesoft.utils.DefaultWildcardSupport;
/*      */ import com.jidesoft.utils.WildcardSupport;
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.IllegalComponentStateException;
/*      */ import java.awt.Point;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.ComponentAdapter;
/*      */ import java.awt.event.ComponentEvent;
/*      */ import java.awt.event.ComponentListener;
/*      */ import java.awt.event.FocusAdapter;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.KeyAdapter;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.KeyListener;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.beans.PropertyChangeSupport;
/*      */ import java.io.PrintStream;
/*      */ import java.util.HashSet;
/*      */ import java.util.Locale;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import java.util.regex.PatternSyntaxException;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JLayeredPane;
/*      */ import javax.swing.JRootPane;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.Timer;
/*      */ import javax.swing.event.DocumentEvent;
/*      */ import javax.swing.event.DocumentListener;
/*      */ import javax.swing.event.EventListenerList;
/*      */ import javax.swing.text.Document;
/*      */ 
/*      */ public abstract class Searchable
/*      */ {
/*   84 */   private final PropertyChangeSupport _propertyChangeSupport = new PropertyChangeSupport(this);
/*      */   protected final JComponent _component;
/*      */   private SearchPopup _popup;
/*      */   private JLayeredPane _layeredPane;
/*      */   private boolean _heavyweightComponentEnabled;
/*      */   private SearchableProvider _searchableProvider;
/*      */   private Pattern _pattern;
/*      */   private String _searchText;
/*      */   private String _previousSearchText;
/*  101 */   private boolean _fromStart = true;
/*  102 */   private boolean _caseSensitive = false;
/*  103 */   private boolean _repeats = false;
/*  104 */   private boolean _wildcardEnabled = true;
/*  105 */   private WildcardSupport _wildcardSupport = null;
/*      */   private Color _mismatchForeground;
/*  107 */   private Color _foreground = null;
/*  108 */   private Color _background = null;
/*      */   protected ComponentListener _componentListener;
/*      */   protected KeyListener _keyListener;
/*      */   protected FocusListener _focusListener;
/*      */   public static final String PROPERTY_SEARCH_TEXT = "searchText";
/*  115 */   private int _cursor = -1;
/*      */ 
/*  117 */   private String _searchLabel = null;
/*      */ 
/*  122 */   private int _popupLocation = 1;
/*      */ 
/*  124 */   private int _searchingDelay = 0;
/*      */ 
/*  126 */   private boolean _reverseOrder = false;
/*      */ 
/*  131 */   protected EventListenerList listenerList = new EventListenerList();
/*      */   private Component _popupLocationRelativeTo;
/*      */   public static final String CLIENT_PROPERTY_SEARCHABLE = "Searchable";
/*      */   private Set<Integer> _selection;
/*  143 */   private boolean _processModelChangeEvent = true;
/*  144 */   private boolean _hideSearchPopupOnEvent = true;
/*      */ 
/*      */   public Searchable(JComponent component)
/*      */   {
/*  152 */     this._previousSearchText = null;
/*  153 */     this._component = component;
/*  154 */     this._selection = new HashSet();
/*  155 */     installListeners();
/*  156 */     updateClientProperty(this._component, this);
/*      */   }
/*      */ 
/*      */   public Searchable(JComponent component, SearchableProvider searchableProvider)
/*      */   {
/*  166 */     this._searchableProvider = searchableProvider;
/*  167 */     this._previousSearchText = null;
/*  168 */     this._component = component;
/*  169 */     this._selection = new HashSet();
/*  170 */     installListeners();
/*  171 */     updateClientProperty(this._component, this);
/*      */   }
/*      */ 
/*      */   protected abstract int getSelectedIndex();
/*      */ 
/*      */   protected abstract void setSelectedIndex(int paramInt, boolean paramBoolean);
/*      */ 
/*      */   public void adjustSelectedIndex(int index, boolean incremental)
/*      */   {
/*  211 */     setSelectedIndex(index, incremental);
/*      */   }
/*      */ 
/*      */   protected abstract int getElementCount();
/*      */ 
/*      */   protected abstract Object getElementAt(int paramInt);
/*      */ 
/*      */   protected abstract String convertElementToString(Object paramObject);
/*      */ 
/*      */   public boolean isHideSearchPopupOnEvent()
/*      */   {
/*  252 */     return this._hideSearchPopupOnEvent;
/*      */   }
/*      */ 
/*      */   public void setHideSearchPopupOnEvent(boolean hideSearchPopupOnEvent)
/*      */   {
/*  262 */     this._hideSearchPopupOnEvent = hideSearchPopupOnEvent;
/*      */   }
/*      */ 
/*      */   public void hidePopup()
/*      */   {
/*  455 */     if (this._popup != null) {
/*  456 */       if (isHeavyweightComponentEnabled()) {
/*  457 */         this._popup.hidePopupImmediately();
/*      */       }
/*  460 */       else if (this._layeredPane != null) {
/*  461 */         this._layeredPane.remove(this._popup);
/*  462 */         this._layeredPane.validate();
/*  463 */         this._layeredPane.repaint();
/*  464 */         this._layeredPane = null;
/*      */       }
/*      */ 
/*  467 */       this._popup = null;
/*  468 */       this._searchableProvider = null;
/*  469 */       fireSearchableEvent(new SearchableEvent(this, 3000, "", Integer.valueOf(getCurrentIndex()), this._previousSearchText));
/*      */     }
/*  471 */     setCursor(-1);
/*      */   }
/*      */ 
/*      */   public SearchableProvider getSearchableProvider() {
/*  475 */     return this._searchableProvider;
/*      */   }
/*      */ 
/*      */   public void setSearchableProvider(SearchableProvider searchableProvider) {
/*  479 */     this._searchableProvider = searchableProvider;
/*      */   }
/*      */ 
/*      */   public void installListeners()
/*      */   {
/*  487 */     if (this._componentListener == null) {
/*  488 */       this._componentListener = createComponentListener();
/*      */     }
/*  490 */     this._component.addComponentListener(this._componentListener);
/*  491 */     Component scrollPane = JideSwingUtilities.getScrollPane(this._component);
/*  492 */     if (scrollPane != null) {
/*  493 */       scrollPane.addComponentListener(this._componentListener);
/*      */     }
/*      */ 
/*  496 */     if (this._keyListener == null) {
/*  497 */       this._keyListener = createKeyListener();
/*      */     }
/*  499 */     JideSwingUtilities.insertKeyListener(getComponent(), this._keyListener, 0);
/*      */ 
/*  501 */     if (this._focusListener == null) {
/*  502 */       this._focusListener = createFocusListener();
/*      */     }
/*  504 */     getComponent().addFocusListener(this._focusListener);
/*      */   }
/*      */ 
/*      */   protected ComponentListener createComponentListener()
/*      */   {
/*  513 */     return new ComponentAdapter()
/*      */     {
/*      */       public void componentHidden(ComponentEvent e) {
/*  516 */         super.componentHidden(e);
/*  517 */         boolean passive = (Searchable.this._searchableProvider == null) || (Searchable.this._searchableProvider.isPassive());
/*  518 */         if (passive)
/*  519 */           Searchable.this.hidePopup();
/*      */       }
/*      */ 
/*      */       public void componentResized(ComponentEvent e)
/*      */       {
/*  525 */         super.componentResized(e);
/*  526 */         boolean passive = (Searchable.this._searchableProvider == null) || (Searchable.this._searchableProvider.isPassive());
/*  527 */         if (passive)
/*  528 */           Searchable.this.updateSizeAndLocation();
/*      */       }
/*      */ 
/*      */       public void componentMoved(ComponentEvent e)
/*      */       {
/*  534 */         super.componentMoved(e);
/*  535 */         boolean passive = (Searchable.this._searchableProvider == null) || (Searchable.this._searchableProvider.isPassive());
/*  536 */         if (passive)
/*  537 */           Searchable.this.updateSizeAndLocation();
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   protected KeyListener createKeyListener()
/*      */   {
/*  549 */     return new KeyAdapter()
/*      */     {
/*      */       public void keyTyped(KeyEvent e) {
/*  552 */         boolean passive = (Searchable.this._searchableProvider == null) || (Searchable.this._searchableProvider.isPassive());
/*  553 */         if (passive)
/*  554 */           Searchable.this.keyTypedOrPressed(e);
/*      */       }
/*      */ 
/*      */       public void keyPressed(KeyEvent e)
/*      */       {
/*  560 */         boolean passive = (Searchable.this._searchableProvider == null) || (Searchable.this._searchableProvider.isPassive());
/*  561 */         if (passive)
/*  562 */           Searchable.this.keyTypedOrPressed(e);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   protected FocusListener createFocusListener()
/*      */   {
/*  574 */     return new FocusAdapter()
/*      */     {
/*      */       public void focusLost(FocusEvent focusevent) {
/*  577 */         boolean passive = (Searchable.this._searchableProvider == null) || (Searchable.this._searchableProvider.isPassive());
/*  578 */         if (passive)
/*  579 */           Searchable.this.hidePopup();
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public void uninstallListeners()
/*      */   {
/*  591 */     if (this._componentListener != null) {
/*  592 */       getComponent().removeComponentListener(this._componentListener);
/*  593 */       Component scrollPane = JideSwingUtilities.getScrollPane(getComponent());
/*  594 */       if (scrollPane != null) {
/*  595 */         scrollPane.removeComponentListener(this._componentListener);
/*      */       }
/*  597 */       this._componentListener = null;
/*      */     }
/*      */ 
/*  600 */     if (this._keyListener != null) {
/*  601 */       getComponent().removeKeyListener(this._keyListener);
/*  602 */       this._keyListener = null;
/*      */     }
/*      */ 
/*  605 */     if (this._focusListener != null) {
/*  606 */       getComponent().removeFocusListener(this._focusListener);
/*  607 */       this._focusListener = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addPropertyChangeListener(PropertyChangeListener propertychangelistener)
/*      */   {
/*  618 */     this._propertyChangeSupport.addPropertyChangeListener(propertychangelistener);
/*      */   }
/*      */ 
/*      */   public void removePropertyChangeListener(PropertyChangeListener propertychangelistener)
/*      */   {
/*  627 */     this._propertyChangeSupport.removePropertyChangeListener(propertychangelistener);
/*      */   }
/*      */ 
/*      */   public void firePropertyChangeEvent(String searchingText) {
/*  631 */     if (!searchingText.equals(this._previousSearchText)) {
/*  632 */       this._propertyChangeSupport.firePropertyChange("searchText", this._previousSearchText, searchingText);
/*  633 */       fireSearchableEvent(new SearchableEvent(this, 3004, searchingText, Integer.valueOf(getCurrentIndex()), this._previousSearchText));
/*  634 */       this._previousSearchText = searchingText;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected boolean compare(Object element, String searchingText)
/*      */   {
/*  646 */     String text = convertElementToString(element);
/*  647 */     if (text != null);
/*  647 */     return compare(isCaseSensitive() ? text : text.toLowerCase(), searchingText);
/*      */   }
/*      */ 
/*      */   protected boolean compare(String text, String searchingText)
/*      */   {
/*  659 */     if ((searchingText == null) || (searchingText.trim().length() == 0)) {
/*  660 */       return true;
/*      */     }
/*      */ 
/*  663 */     if (!isWildcardEnabled()) {
/*  664 */       return (searchingText != null) && ((searchingText.equals(text)) || ((searchingText.length() > 0) && (isFromStart() ? text.startsWith(searchingText) : text.indexOf(searchingText) != -1)));
/*      */     }
/*      */ 
/*  669 */     if ((this._searchText != null) && (this._searchText.equals(searchingText)) && (this._pattern != null)) {
/*  670 */       return this._pattern.matcher(text).find();
/*      */     }
/*      */ 
/*  673 */     WildcardSupport wildcardSupport = getWildcardSupport();
/*  674 */     String s = wildcardSupport.convert(searchingText);
/*  675 */     if (searchingText.equals(s)) {
/*  676 */       return text.indexOf(searchingText) != -1 ? true : isFromStart() ? text.startsWith(searchingText) : false;
/*      */     }
/*  678 */     this._searchText = searchingText;
/*      */     try
/*      */     {
/*  681 */       this._pattern = Pattern.compile(isFromStart() ? "^" + s : s, isCaseSensitive() ? 0 : 2);
/*  682 */       return this._pattern.matcher(text).find();
/*      */     } catch (PatternSyntaxException e) {
/*      */     }
/*  685 */     return false;
/*      */   }
/*      */ 
/*      */   public int getCursor()
/*      */   {
/*  698 */     return this._cursor;
/*      */   }
/*      */ 
/*      */   public void setCursor(int cursor)
/*      */   {
/*  708 */     setCursor(cursor, false);
/*      */   }
/*      */ 
/*      */   public void setCursor(int cursor, boolean incremental)
/*      */   {
/*  722 */     if ((!incremental) || (this._cursor < 0)) this._selection.clear();
/*  723 */     if (this._cursor >= 0) this._selection.add(Integer.valueOf(cursor));
/*  724 */     this._cursor = cursor;
/*      */   }
/*      */ 
/*      */   protected void highlightAll()
/*      */   {
/*  734 */     int firstIndex = -1;
/*  735 */     int index = getSelectedIndex();
/*  736 */     String text = getSearchingText();
/*      */ 
/*  738 */     while (index != -1) {
/*  739 */       int newIndex = findNext(text);
/*  740 */       if (index == newIndex) {
/*  741 */         index = -1;
/*      */       }
/*      */       else {
/*  744 */         index = newIndex;
/*      */       }
/*  746 */       if (index != -1) {
/*  747 */         if (firstIndex == -1) {
/*  748 */           firstIndex = index;
/*      */         }
/*  750 */         select(index, text);
/*      */       }
/*      */     }
/*      */ 
/*  754 */     if (firstIndex != -1)
/*  755 */       select(firstIndex, text);
/*      */   }
/*      */ 
/*      */   protected void cancelHighlightAll()
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void select(int index, String searchingText)
/*      */   {
/*  776 */     if (index != -1) {
/*  777 */       setSelectedIndex(index, true);
/*  778 */       setCursor(index, true);
/*  779 */       Object element = getElementAt(index);
/*  780 */       fireSearchableEvent(new SearchableEvent(this, 3002, searchingText, element, convertElementToString(element)));
/*      */     }
/*      */     else {
/*  783 */       setSelectedIndex(-1, false);
/*  784 */       fireSearchableEvent(new SearchableEvent(this, 3003, searchingText));
/*      */     }
/*      */   }
/*      */ 
/*      */   public int findNext(String s)
/*      */   {
/*  795 */     String str = isCaseSensitive() ? s : s.toLowerCase();
/*  796 */     int count = getElementCount();
/*  797 */     if (count == 0)
/*  798 */       return s.length() > 0 ? -1 : 0;
/*  799 */     int selectedIndex = getCurrentIndex();
/*  800 */     for (int i = selectedIndex + 1; i < count; i++) {
/*  801 */       Object element = getElementAt(i);
/*  802 */       if (compare(element, str)) {
/*  803 */         return i;
/*      */       }
/*      */     }
/*  806 */     if (isRepeats()) {
/*  807 */       for (int i = 0; i < selectedIndex; i++) {
/*  808 */         Object element = getElementAt(i);
/*  809 */         if (compare(element, str)) {
/*  810 */           return i;
/*      */         }
/*      */       }
/*      */     }
/*  814 */     return compare(getElementAt(selectedIndex), str) ? selectedIndex : selectedIndex == -1 ? -1 : -1;
/*      */   }
/*      */ 
/*      */   protected int getCurrentIndex() {
/*  818 */     if (this._selection.contains(Integer.valueOf(getSelectedIndex()))) {
/*  819 */       return this._cursor != -1 ? this._cursor : getSelectedIndex();
/*      */     }
/*      */ 
/*  822 */     this._selection.clear();
/*  823 */     return getSelectedIndex();
/*      */   }
/*      */ 
/*      */   public int findPrevious(String s)
/*      */   {
/*  834 */     String str = isCaseSensitive() ? s : s.toLowerCase();
/*  835 */     int count = getElementCount();
/*  836 */     if (count == 0)
/*  837 */       return s.length() > 0 ? -1 : 0;
/*  838 */     int selectedIndex = getCurrentIndex();
/*  839 */     for (int i = selectedIndex - 1; i >= 0; i--) {
/*  840 */       Object element = getElementAt(i);
/*  841 */       if (compare(element, str)) {
/*  842 */         return i;
/*      */       }
/*      */     }
/*  845 */     if (isRepeats()) {
/*  846 */       for (int i = count - 1; i >= selectedIndex; i--) {
/*  847 */         Object element = getElementAt(i);
/*  848 */         if (compare(element, str))
/*  849 */           return i;
/*      */       }
/*      */     }
/*  852 */     return compare(getElementAt(selectedIndex), str) ? selectedIndex : selectedIndex == -1 ? -1 : -1;
/*      */   }
/*      */ 
/*      */   public int findFromCursor(String s)
/*      */   {
/*  864 */     if (isReverseOrder()) {
/*  865 */       return reverseFindFromCursor(s);
/*      */     }
/*      */ 
/*  868 */     String str = isCaseSensitive() ? s : s.toLowerCase();
/*  869 */     int selectedIndex = getCurrentIndex();
/*  870 */     if (selectedIndex < 0)
/*  871 */       selectedIndex = 0;
/*  872 */     int count = getElementCount();
/*  873 */     if (count == 0) {
/*  874 */       return -1;
/*      */     }
/*      */ 
/*  877 */     for (int i = selectedIndex; i < count; i++) {
/*  878 */       Object element = getElementAt(i);
/*  879 */       if (compare(element, str)) {
/*  880 */         return i;
/*      */       }
/*      */     }
/*      */ 
/*  884 */     for (int i = 0; i < selectedIndex; i++) {
/*  885 */       Object element = getElementAt(i);
/*  886 */       if (compare(element, str)) {
/*  887 */         return i;
/*      */       }
/*      */     }
/*  890 */     return -1;
/*      */   }
/*      */ 
/*      */   public int reverseFindFromCursor(String s)
/*      */   {
/*  900 */     if (!isReverseOrder()) {
/*  901 */       return findFromCursor(s);
/*      */     }
/*      */ 
/*  904 */     String str = isCaseSensitive() ? s : s.toLowerCase();
/*  905 */     int selectedIndex = getCurrentIndex();
/*  906 */     if (selectedIndex < 0)
/*  907 */       selectedIndex = 0;
/*  908 */     int count = getElementCount();
/*  909 */     if (count == 0) {
/*  910 */       return -1;
/*      */     }
/*      */ 
/*  913 */     for (int i = selectedIndex; i >= 0; i--) {
/*  914 */       Object element = getElementAt(i);
/*  915 */       if (compare(element, str)) {
/*  916 */         return i;
/*      */       }
/*      */     }
/*      */ 
/*  920 */     for (int i = count - 1; i >= selectedIndex; i--) {
/*  921 */       Object element = getElementAt(i);
/*  922 */       if (compare(element, str)) {
/*  923 */         return i;
/*      */       }
/*      */     }
/*  926 */     return -1;
/*      */   }
/*      */ 
/*      */   public int findFirst(String s)
/*      */   {
/*  936 */     String str = isCaseSensitive() ? s : s.toLowerCase();
/*  937 */     int count = getElementCount();
/*  938 */     if (count == 0) {
/*  939 */       return s.length() > 0 ? -1 : 0;
/*      */     }
/*  941 */     for (int i = 0; i < count; i++) {
/*  942 */       int index = getIndex(count, i);
/*  943 */       Object element = getElementAt(index);
/*  944 */       if (compare(element, str)) {
/*  945 */         return index;
/*      */       }
/*      */     }
/*  948 */     return -1;
/*      */   }
/*      */ 
/*      */   public int findLast(String s)
/*      */   {
/*  958 */     String str = isCaseSensitive() ? s : s.toLowerCase();
/*  959 */     int count = getElementCount();
/*  960 */     if (count == 0)
/*  961 */       return s.length() > 0 ? -1 : 0;
/*  962 */     for (int i = count - 1; i >= 0; i--) {
/*  963 */       Object element = getElementAt(i);
/*  964 */       if (compare(element, str))
/*  965 */         return i;
/*      */     }
/*  967 */     return -1;
/*      */   }
/*      */ 
/*      */   protected void keyTypedOrPressed(KeyEvent e)
/*      */   {
/*  976 */     if ((this._searchableProvider != null) && (this._searchableProvider.isPassive())) {
/*  977 */       this._searchableProvider.processKeyEvent(e);
/*  978 */       return;
/*      */     }
/*      */ 
/*  981 */     if (isActivateKey(e)) {
/*  982 */       String searchingText = "";
/*  983 */       if (e.getID() == 400) {
/*  984 */         if (JideSwingUtilities.isMenuShortcutKeyDown(e)) {
/*  985 */           return;
/*      */         }
/*  987 */         if (e.isAltDown()) {
/*  988 */           return;
/*      */         }
/*      */ 
/*  991 */         searchingText = String.valueOf(e.getKeyChar());
/*      */       }
/*  993 */       showPopup(searchingText);
/*  994 */       if (e.getKeyCode() != 10)
/*  995 */         e.consume();
/*      */     }
/*      */   }
/*      */ 
/*      */   private int getIndex(int count, int index)
/*      */   {
/* 1001 */     return isReverseOrder() ? count - index - 1 : index;
/*      */   }
/*      */ 
/*      */   public void showPopup(String searchingText)
/*      */   {
/* 1012 */     if (this._searchableProvider == null) {
/* 1013 */       fireSearchableEvent(new SearchableEvent(this, 2999, searchingText));
/* 1014 */       showPopup(createSearchPopup(searchingText));
/* 1015 */       this._searchableProvider = new SearchableProvider() {
/*      */         public String getSearchingText() {
/* 1017 */           return Searchable.this._popup != null ? Searchable.this._popup.getSearchingText() : "";
/*      */         }
/*      */ 
/*      */         public boolean isPassive() {
/* 1021 */           return true;
/*      */         }
/*      */ 
/*      */         public void processKeyEvent(KeyEvent e) {
/* 1025 */           if (Searchable.this._popup != null)
/* 1026 */             Searchable.this._popup.processKeyEvent(e);
/*      */         }
/*      */       };
/*      */     }
/*      */   }
/*      */ 
/*      */   protected SearchPopup createSearchPopup(String searchingText)
/*      */   {
/* 1040 */     return new DefaultSearchPopup(searchingText);
/*      */   }
/*      */ 
/*      */   public String getSearchingText()
/*      */   {
/* 1049 */     return this._searchableProvider != null ? this._searchableProvider.getSearchingText() : "";
/*      */   }
/*      */ 
/*      */   private void showPopup(SearchPopup searchpopup) {
/* 1053 */     JRootPane rootPane = this._component.getRootPane();
/* 1054 */     if (rootPane != null)
/* 1055 */       this._layeredPane = rootPane.getLayeredPane();
/*      */     else {
/* 1057 */       this._layeredPane = null;
/*      */     }
/*      */ 
/* 1060 */     if ((this._layeredPane == null) || (isHeavyweightComponentEnabled())) {
/* 1061 */       this._popup = searchpopup;
/* 1062 */       Point location = updateSizeAndLocation();
/* 1063 */       if (location != null) {
/* 1064 */         searchpopup.showPopup(location.x, location.y);
/* 1065 */         this._popup.setVisible(true);
/*      */       }
/*      */       else {
/* 1068 */         this._popup = null;
/*      */       }
/*      */     }
/*      */     else {
/* 1072 */       if ((this._popup != null) && (this._layeredPane != null)) {
/* 1073 */         this._layeredPane.remove(this._popup);
/* 1074 */         this._layeredPane.validate();
/* 1075 */         this._layeredPane.repaint();
/* 1076 */         this._layeredPane = null;
/*      */       }
/* 1078 */       else if (!this._component.isShowing()) {
/* 1079 */         this._popup = null;
/*      */       } else {
/* 1081 */         this._popup = searchpopup;
/*      */       }
/* 1083 */       if ((this._popup == null) || (!this._component.isDisplayable())) {
/* 1084 */         return;
/*      */       }
/* 1086 */       if (this._layeredPane == null) {
/* 1087 */         System.err.println("Failed to find layeredPane.");
/* 1088 */         return;
/*      */       }
/*      */ 
/* 1091 */       this._layeredPane.add(this._popup, JLayeredPane.POPUP_LAYER);
/*      */ 
/* 1093 */       updateSizeAndLocation();
/* 1094 */       this._popup.setVisible(true);
/* 1095 */       this._popup.validate();
/*      */     }
/*      */   }
/*      */ 
/*      */   private Point updateSizeAndLocation() {
/* 1100 */     Component component = getPopupLocationRelativeTo();
/* 1101 */     if (component == null) {
/* 1102 */       component = JideSwingUtilities.getScrollPane(this._component);
/*      */     }
/* 1104 */     if (component == null) {
/* 1105 */       component = this._component;
/*      */     }
/*      */ 
/* 1109 */     if (this._popup != null) {
/* 1110 */       Dimension size = this._popup.getPreferredSize();
/*      */       Point componentLocation;
/* 1111 */       switch (getPopupLocation()) {
/*      */       case 3:
/*      */         try {
/* 1114 */           componentLocation = component.getLocationOnScreen();
/* 1115 */           componentLocation.y += component.getHeight();
/* 1116 */           if (!isHeavyweightComponentEnabled()) {
/* 1117 */             SwingUtilities.convertPointFromScreen(componentLocation, this._layeredPane);
/* 1118 */             if (componentLocation.y + size.height > this._layeredPane.getHeight())
/* 1119 */               componentLocation.y = (this._layeredPane.getHeight() - size.height);
/*      */           }
/*      */         }
/*      */         catch (IllegalComponentStateException e)
/*      */         {
/* 1124 */           return null;
/*      */         }
/*      */       case 1:
/*      */       default:
/*      */         try
/*      */         {
/* 1130 */           componentLocation = component.getLocationOnScreen();
/* 1131 */           if (!isHeavyweightComponentEnabled()) {
/* 1132 */             SwingUtilities.convertPointFromScreen(componentLocation, this._layeredPane);
/*      */           }
/* 1134 */           componentLocation.y -= size.height;
/* 1135 */           if (componentLocation.y < 0)
/* 1136 */             componentLocation.y = 0;
/*      */         }
/*      */         catch (IllegalComponentStateException e)
/*      */         {
/* 1140 */           return null;
/*      */         }
/*      */       }
/*      */ 
/* 1144 */       if (!isHeavyweightComponentEnabled()) {
/* 1145 */         this._popup.setLocation(componentLocation);
/* 1146 */         this._popup.setSize(size);
/*      */       }
/*      */       else {
/* 1149 */         this._popup.packPopup();
/*      */       }
/* 1151 */       return componentLocation;
/*      */     }
/*      */ 
/* 1154 */     return null;
/*      */   }
/*      */ 
/*      */   protected boolean isFindFirstKey(KeyEvent e)
/*      */   {
/* 1165 */     return e.getKeyCode() == 36;
/*      */   }
/*      */ 
/*      */   protected boolean isFindLastKey(KeyEvent e)
/*      */   {
/* 1175 */     return e.getKeyCode() == 35;
/*      */   }
/*      */ 
/*      */   protected boolean isFindPreviousKey(KeyEvent e)
/*      */   {
/* 1185 */     return e.getKeyCode() == 38;
/*      */   }
/*      */ 
/*      */   protected boolean isFindNextKey(KeyEvent e)
/*      */   {
/* 1195 */     return e.getKeyCode() == 40;
/*      */   }
/*      */ 
/*      */   protected boolean isNavigationKey(KeyEvent e)
/*      */   {
/* 1206 */     return (isFindFirstKey(e)) || (isFindLastKey(e)) || (isFindNextKey(e)) || (isFindPreviousKey(e));
/*      */   }
/*      */ 
/*      */   protected boolean isActivateKey(KeyEvent e)
/*      */   {
/* 1216 */     char keyChar = e.getKeyChar();
/* 1217 */     return (e.getID() == 400) && ((Character.isLetterOrDigit(keyChar)) || (keyChar == '*') || (keyChar == '?'));
/*      */   }
/*      */ 
/*      */   protected boolean isDeactivateKey(KeyEvent e)
/*      */   {
/* 1230 */     int keyCode = e.getKeyCode();
/* 1231 */     return (keyCode == 10) || (keyCode == 27) || (keyCode == 33) || (keyCode == 34) || (keyCode == 36) || (keyCode == 35) || (keyCode == 37) || (keyCode == 39) || (keyCode == 38) || (keyCode == 40);
/*      */   }
/*      */ 
/*      */   protected boolean isSelectAllKey(KeyEvent e)
/*      */   {
/* 1245 */     return (JideSwingUtilities.isMenuShortcutKeyDown(e)) && (e.getKeyCode() == 65);
/*      */   }
/*      */ 
/*      */   protected boolean isIncrementalSelectKey(KeyEvent e)
/*      */   {
/* 1256 */     return JideSwingUtilities.isMenuShortcutKeyDown(e);
/*      */   }
/*      */ 
/*      */   public Color getMismatchForeground()
/*      */   {
/* 1266 */     if (this._mismatchForeground == null) {
/* 1267 */       return Color.RED;
/*      */     }
/*      */ 
/* 1270 */     return this._mismatchForeground;
/*      */   }
/*      */ 
/*      */   public void setMismatchForeground(Color mismatchForeground)
/*      */   {
/* 1280 */     this._mismatchForeground = mismatchForeground;
/*      */   }
/*      */ 
/*      */   public boolean isCaseSensitive()
/*      */   {
/* 1289 */     return this._caseSensitive;
/*      */   }
/*      */ 
/*      */   public void setCaseSensitive(boolean caseSensitive)
/*      */   {
/* 1298 */     this._caseSensitive = caseSensitive;
/*      */   }
/*      */ 
/*      */   public int getSearchingDelay()
/*      */   {
/* 1310 */     return this._searchingDelay;
/*      */   }
/*      */ 
/*      */   public void setSearchingDelay(int searchingDelay)
/*      */   {
/* 1322 */     this._searchingDelay = searchingDelay;
/*      */   }
/*      */ 
/*      */   public boolean isRepeats()
/*      */   {
/* 1332 */     return this._repeats;
/*      */   }
/*      */ 
/*      */   public void setRepeats(boolean repeats)
/*      */   {
/* 1342 */     this._repeats = repeats;
/*      */   }
/*      */ 
/*      */   public Color getForeground()
/*      */   {
/* 1351 */     if (this._foreground == null) {
/* 1352 */       return UIDefaultsLookup.getColor("ToolTip.foreground");
/*      */     }
/*      */ 
/* 1355 */     return this._foreground;
/*      */   }
/*      */ 
/*      */   public void setForeground(Color foreground)
/*      */   {
/* 1365 */     this._foreground = foreground;
/*      */   }
/*      */ 
/*      */   public Color getBackground()
/*      */   {
/* 1374 */     if (this._background == null) {
/* 1375 */       return UIDefaultsLookup.getColor("ToolTip.background");
/*      */     }
/*      */ 
/* 1378 */     return this._background;
/*      */   }
/*      */ 
/*      */   public void setBackground(Color background)
/*      */   {
/* 1388 */     this._background = background;
/*      */   }
/*      */ 
/*      */   public boolean isWildcardEnabled()
/*      */   {
/* 1398 */     return this._wildcardEnabled;
/*      */   }
/*      */ 
/*      */   public void setWildcardEnabled(boolean wildcardEnabled)
/*      */   {
/* 1408 */     this._wildcardEnabled = wildcardEnabled;
/*      */   }
/*      */ 
/*      */   public WildcardSupport getWildcardSupport()
/*      */   {
/* 1417 */     if (this._wildcardSupport == null) {
/* 1418 */       this._wildcardSupport = new DefaultWildcardSupport();
/*      */     }
/* 1420 */     return this._wildcardSupport;
/*      */   }
/*      */ 
/*      */   public void setWildcardSupport(WildcardSupport wildcardSupport)
/*      */   {
/* 1430 */     this._wildcardSupport = wildcardSupport;
/*      */   }
/*      */ 
/*      */   public String getSearchLabel()
/*      */   {
/* 1439 */     if (this._searchLabel == null) {
/* 1440 */       return getResourceString("Searchable.searchFor");
/*      */     }
/*      */ 
/* 1443 */     return this._searchLabel;
/*      */   }
/*      */ 
/*      */   public void setSearchLabel(String searchLabel)
/*      */   {
/* 1453 */     this._searchLabel = searchLabel;
/*      */   }
/*      */ 
/*      */   public void addSearchableListener(SearchableListener l)
/*      */   {
/* 1462 */     this.listenerList.add(SearchableListener.class, l);
/*      */   }
/*      */ 
/*      */   public void removeSearchableListener(SearchableListener l)
/*      */   {
/* 1471 */     this.listenerList.remove(SearchableListener.class, l);
/*      */   }
/*      */ 
/*      */   public SearchableListener[] getSearchableListeners()
/*      */   {
/* 1483 */     return (SearchableListener[])this.listenerList.getListeners(SearchableListener.class);
/*      */   }
/*      */ 
/*      */   protected void fireSearchableEvent(SearchableEvent e)
/*      */   {
/* 1492 */     Object[] listeners = this.listenerList.getListenerList();
/* 1493 */     for (int i = listeners.length - 2; i >= 0; i -= 2)
/* 1494 */       if (listeners[i] == SearchableListener.class)
/* 1495 */         ((SearchableListener)listeners[(i + 1)]).searchableEventFired(e);
/*      */   }
/*      */ 
/*      */   public Component getComponent()
/*      */   {
/* 1506 */     return this._component;
/*      */   }
/*      */ 
/*      */   public int getPopupLocation()
/*      */   {
/* 1515 */     return this._popupLocation;
/*      */   }
/*      */ 
/*      */   public void setPopupLocation(int popupLocation)
/*      */   {
/* 1525 */     this._popupLocation = popupLocation;
/*      */   }
/*      */ 
/*      */   public boolean isReverseOrder()
/*      */   {
/* 1626 */     return this._reverseOrder;
/*      */   }
/*      */ 
/*      */   public void setReverseOrder(boolean reverseOrder)
/*      */   {
/* 1636 */     this._reverseOrder = reverseOrder;
/*      */   }
/*      */ 
/*      */   protected String getResourceString(String key)
/*      */   {
/* 1647 */     return Resource.getResourceBundle(this._component != null ? this._component.getLocale() : Locale.getDefault()).getString(key);
/*      */   }
/*      */ 
/*      */   public boolean isPopupVisible()
/*      */   {
/* 1656 */     return this._popup != null;
/*      */   }
/*      */ 
/*      */   public boolean isHeavyweightComponentEnabled() {
/* 1660 */     return this._heavyweightComponentEnabled;
/*      */   }
/*      */ 
/*      */   public void setHeavyweightComponentEnabled(boolean heavyweightComponentEnabled) {
/* 1664 */     this._heavyweightComponentEnabled = heavyweightComponentEnabled;
/*      */   }
/*      */ 
/*      */   public Component getPopupLocationRelativeTo()
/*      */   {
/* 1674 */     return this._popupLocationRelativeTo;
/*      */   }
/*      */ 
/*      */   public void setPopupLocationRelativeTo(Component popupLocationRelativeTo)
/*      */   {
/* 1685 */     this._popupLocationRelativeTo = popupLocationRelativeTo;
/*      */   }
/*      */ 
/*      */   public boolean isFromStart()
/*      */   {
/* 1696 */     return this._fromStart;
/*      */   }
/*      */ 
/*      */   public void setFromStart(boolean fromStart)
/*      */   {
/* 1707 */     hidePopup();
/* 1708 */     this._fromStart = fromStart;
/*      */   }
/*      */ 
/*      */   public static Searchable getSearchable(JComponent component)
/*      */   {
/* 1718 */     Object clientProperty = component.getClientProperty("Searchable");
/* 1719 */     if ((clientProperty instanceof Searchable)) {
/* 1720 */       return (Searchable)clientProperty;
/*      */     }
/*      */ 
/* 1723 */     return null;
/*      */   }
/*      */ 
/*      */   private void updateClientProperty(JComponent component, Searchable searchable)
/*      */   {
/* 1728 */     if (component != null) {
/* 1729 */       Object clientProperty = this._component.getClientProperty("Searchable");
/* 1730 */       if ((clientProperty instanceof Searchable)) {
/* 1731 */         ((Searchable)clientProperty).uninstallListeners();
/*      */       }
/* 1733 */       component.putClientProperty("Searchable", searchable);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isProcessModelChangeEvent()
/*      */   {
/* 1750 */     return this._processModelChangeEvent;
/*      */   }
/*      */ 
/*      */   public void setProcessModelChangeEvent(boolean processModelChangeEvent)
/*      */   {
/* 1763 */     this._processModelChangeEvent = processModelChangeEvent;
/*      */   }
/*      */ 
/*      */   public abstract class SearchPopup extends JidePopup
/*      */   {
/*      */     protected Searchable.SearchField _textField;
/*      */ 
/*      */     public SearchPopup()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void processKeyEvent(KeyEvent e)
/*      */     {
/* 1533 */       this._textField.processKeyEvent(e);
/* 1534 */       if (e.isConsumed()) {
/* 1535 */         String text = getSearchingText();
/* 1536 */         if (text.length() == 0) {
/* 1537 */           return;
/*      */         }
/*      */ 
/* 1540 */         if (Searchable.this.isSelectAllKey(e)) {
/* 1541 */           selectAll(e, text);
/* 1542 */           return;
/*      */         }
/*      */ 
/* 1546 */         if (Searchable.this.isFindPreviousKey(e)) {
/* 1547 */           int found = Searchable.this.findPrevious(text);
/* 1548 */           select(found, e, text);
/*      */         }
/* 1550 */         else if (Searchable.this.isFindNextKey(e)) {
/* 1551 */           int found = Searchable.this.findNext(text);
/* 1552 */           select(found, e, text);
/*      */         }
/* 1554 */         else if (Searchable.this.isFindFirstKey(e)) {
/* 1555 */           int found = Searchable.this.findFirst(text);
/* 1556 */           select(found, e, text);
/*      */         }
/* 1558 */         else if (Searchable.this.isFindLastKey(e)) {
/* 1559 */           int found = Searchable.this.findLast(text);
/* 1560 */           select(found, e, text);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1566 */       if (e.getKeyCode() != 10)
/* 1567 */         e.consume();
/*      */     }
/*      */ 
/*      */     private void selectAll(KeyEvent e, String text)
/*      */     {
/* 1572 */       boolean oldReverseOrder = Searchable.this.isReverseOrder();
/* 1573 */       if (oldReverseOrder) {
/* 1574 */         Searchable.this.setReverseOrder(false);
/*      */       }
/*      */ 
/* 1577 */       int index = Searchable.this.findFirst(text);
/* 1578 */       if (index != -1) {
/* 1579 */         Searchable.this.setSelectedIndex(index, false);
/* 1580 */         Searchable.this.setCursor(index);
/*      */       }
/*      */ 
/* 1584 */       boolean oldRepeats = Searchable.this.isRepeats();
/* 1585 */       if (oldRepeats) {
/* 1586 */         Searchable.this.setRepeats(false);
/*      */       }
/*      */ 
/* 1589 */       while (index != -1) {
/* 1590 */         int newIndex = Searchable.this.findNext(text);
/* 1591 */         if (index == newIndex) {
/* 1592 */           index = -1;
/*      */         }
/*      */         else {
/* 1595 */           index = newIndex;
/*      */         }
/* 1597 */         if (index == -1) {
/*      */           break;
/*      */         }
/* 1600 */         select(index, e, text);
/*      */       }
/*      */ 
/* 1603 */       if (oldRepeats) {
/* 1604 */         Searchable.this.setRepeats(oldRepeats);
/*      */       }
/*      */ 
/* 1607 */       if (oldReverseOrder)
/* 1608 */         Searchable.this.setReverseOrder(oldReverseOrder);
/*      */     }
/*      */ 
/*      */     public String getSearchingText()
/*      */     {
/* 1613 */       return this._textField != null ? this._textField.getText() : "";
/*      */     }
/*      */ 
/*      */     protected abstract void select(int paramInt, KeyEvent paramKeyEvent, String paramString);
/*      */   }
/*      */ 
/*      */   private class DefaultSearchPopup extends Searchable.SearchPopup
/*      */   {
/*      */     private JLabel _label;
/*      */     private JLabel _noMatch;
/*      */ 
/*      */     public DefaultSearchPopup(String text)
/*      */     {
/*  310 */       super();
/*  311 */       initComponents(text);
/*      */     }
/*      */ 
/*      */     private void initComponents(String text) {
/*  315 */       Color foreground = Searchable.this.getForeground();
/*  316 */       Color background = Searchable.this.getBackground();
/*      */ 
/*  319 */       this._label = new JLabel(Searchable.this.getSearchLabel());
/*  320 */       this._label.setForeground(foreground);
/*  321 */       this._label.setVerticalAlignment(3);
/*      */ 
/*  323 */       this._noMatch = new JLabel();
/*  324 */       this._noMatch.setForeground(Searchable.this.getMismatchForeground());
/*  325 */       this._noMatch.setVerticalAlignment(3);
/*      */ 
/*  328 */       this._textField = new Searchable.SearchField(Searchable.this);
/*  329 */       this._textField.setFocusable(false);
/*  330 */       this._textField.setBorder(BorderFactory.createEmptyBorder());
/*  331 */       this._textField.setForeground(foreground);
/*  332 */       this._textField.setCursor(getCursor());
/*  333 */       this._textField.getDocument().addDocumentListener(new DocumentListener(foreground) {
/*  334 */         private Timer timer = new Timer(200, new ActionListener() {
/*      */           public void actionPerformed(ActionEvent e) {
/*  336 */             Searchable.DefaultSearchPopup.1.this.applyText();
/*      */           }
/*      */         });
/*      */ 
/*      */         public void insertUpdate(DocumentEvent e)
/*      */         {
/*  341 */           startTimer();
/*      */         }
/*      */ 
/*      */         public void removeUpdate(DocumentEvent e) {
/*  345 */           startTimer();
/*      */         }
/*      */ 
/*      */         public void changedUpdate(DocumentEvent e) {
/*  349 */           startTimer();
/*      */         }
/*      */ 
/*      */         protected void applyText() {
/*  353 */           String text = Searchable.DefaultSearchPopup.this._textField.getText().trim();
/*  354 */           Searchable.this.firePropertyChangeEvent(text);
/*  355 */           if (text.length() != 0) {
/*  356 */             int found = Searchable.this.findFromCursor(text);
/*  357 */             if (found == -1) {
/*  358 */               Searchable.DefaultSearchPopup.this._textField.setForeground(Searchable.this.getMismatchForeground());
/*      */             }
/*      */             else {
/*  361 */               Searchable.DefaultSearchPopup.this._textField.setForeground(this.val$foreground);
/*      */             }
/*  363 */             Searchable.DefaultSearchPopup.this.select(found, null, text);
/*      */           }
/*      */           else {
/*  366 */             Searchable.DefaultSearchPopup.this._textField.setForeground(this.val$foreground);
/*  367 */             Searchable.DefaultSearchPopup.this._noMatch.setText("");
/*  368 */             Searchable.DefaultSearchPopup.this.updatePopupBounds();
/*  369 */             Searchable.DefaultSearchPopup.this.hidePopup();
/*      */           }
/*      */         }
/*      */ 
/*      */         void startTimer() {
/*  374 */           Searchable.DefaultSearchPopup.this.updatePopupBounds();
/*  375 */           if (Searchable.this.getSearchingDelay() > 0) {
/*  376 */             this.timer.setInitialDelay(Searchable.this.getSearchingDelay());
/*  377 */             if (this.timer.isRunning()) {
/*  378 */               this.timer.restart();
/*      */             }
/*      */             else {
/*  381 */               this.timer.setRepeats(false);
/*  382 */               this.timer.start();
/*      */             }
/*      */           }
/*      */           else {
/*  386 */             applyText();
/*      */           }
/*      */         }
/*      */       });
/*  390 */       this._textField.setText(text);
/*      */ 
/*  392 */       setBackground(background);
/*  393 */       setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIDefaultsLookup.getColor("controlShadow"), 1), BorderFactory.createEmptyBorder(0, 6, 1, 8)));
/*      */ 
/*  395 */       setLayout(new BorderLayout(2, 0));
/*  396 */       Dimension size = this._label.getPreferredSize();
/*  397 */       size.height = this._textField.getPreferredSize().height;
/*  398 */       this._label.setPreferredSize(size);
/*  399 */       add(this._label, "Before");
/*  400 */       add(this._textField, "Center");
/*  401 */       add(this._noMatch, "After");
/*  402 */       setPopupBorder(BorderFactory.createEmptyBorder());
/*      */     }
/*      */ 
/*      */     protected void select(int index, KeyEvent e, String searchingText)
/*      */     {
/*  407 */       if (index != -1) {
/*  408 */         boolean incremental = (e != null) && (Searchable.this.isIncrementalSelectKey(e));
/*  409 */         Searchable.this.setSelectedIndex(index, incremental);
/*  410 */         Searchable.this.setCursor(index, incremental);
/*  411 */         this._textField.setForeground(getForeground());
/*  412 */         this._noMatch.setText("");
/*      */       }
/*      */       else {
/*  415 */         this._textField.setForeground(Searchable.this.getMismatchForeground());
/*  416 */         this._noMatch.setText(Searchable.this.getResourceString("Searchable.noMatch"));
/*      */       }
/*  418 */       updatePopupBounds();
/*  419 */       if (index != -1) {
/*  420 */         Object element = Searchable.this.getElementAt(index);
/*  421 */         Searchable.this.fireSearchableEvent(new SearchableEvent(Searchable.this, 3002, searchingText, element, Searchable.this.convertElementToString(element)));
/*      */       }
/*      */       else {
/*  424 */         Searchable.this.fireSearchableEvent(new SearchableEvent(Searchable.this, 3003, searchingText));
/*      */       }
/*      */     }
/*      */ 
/*      */     private void updatePopupBounds() {
/*  429 */       if (Searchable.this._popup != null) {
/*  430 */         this._textField.invalidate();
/*      */         try {
/*  432 */           if (!Searchable.this.isHeavyweightComponentEnabled()) {
/*  433 */             Dimension size = this._noMatch.getPreferredSize();
/*  434 */             size.width += this._label.getPreferredSize().width;
/*  435 */             size.width += new JLabel(this._textField.getText()).getPreferredSize().width + 24;
/*  436 */             size.height = Searchable.this._popup.getSize().height;
/*  437 */             Searchable.this._popup.setSize(size);
/*  438 */             Searchable.this._popup.validate();
/*      */           }
/*      */           else {
/*  441 */             Searchable.this._popup.packPopup();
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected class SearchField extends JTextField
/*      */   {
/*      */     SearchField()
/*      */     {
/*  270 */       JideSwingUtilities.setTextComponentTransparent(this);
/*      */     }
/*      */ 
/*      */     public Dimension getPreferredSize()
/*      */     {
/*  275 */       Dimension size = super.getPreferredSize();
/*  276 */       size.width = (getFontMetrics(getFont()).stringWidth(getText()) + 4);
/*  277 */       return size;
/*      */     }
/*      */ 
/*      */     public void processKeyEvent(KeyEvent e)
/*      */     {
/*  282 */       int keyCode = e.getKeyCode();
/*  283 */       if ((keyCode == 8) && (getDocument().getLength() == 0)) {
/*  284 */         e.consume();
/*  285 */         return;
/*      */       }
/*  287 */       boolean isNavigationKey = Searchable.this.isNavigationKey(e);
/*  288 */       if ((Searchable.this.isDeactivateKey(e)) && (!isNavigationKey)) {
/*  289 */         Searchable.this.hidePopup();
/*  290 */         if (keyCode == 27)
/*  291 */           e.consume();
/*  292 */         return;
/*      */       }
/*  294 */       super.processKeyEvent(e);
/*  295 */       if ((keyCode == 8) || (isNavigationKey))
/*  296 */         e.consume();
/*  297 */       if (Searchable.this.isSelectAllKey(e))
/*  298 */         e.consume();
/*      */     }
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.Searchable
 * JD-Core Version:    0.6.0
 */
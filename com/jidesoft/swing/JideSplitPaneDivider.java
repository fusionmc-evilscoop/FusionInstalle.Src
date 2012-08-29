/*      */ package com.jidesoft.swing;
/*      */ 
/*      */ import com.jidesoft.plaf.UIDefaultsLookup;
/*      */ import com.jidesoft.plaf.basic.Painter;
/*      */ import com.jidesoft.utils.PortingUtils;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.ComponentOrientation;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.util.Map;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.event.MouseInputAdapter;
/*      */ 
/*      */ public class JideSplitPaneDivider extends JPanel
/*      */   implements PropertyChangeListener
/*      */ {
/*      */   protected DragController _dragger;
/*   38 */   protected int _dividerSize = UIDefaultsLookup.getInt("JideSplitPane.dividerSize");
/*      */   protected JideSplitPane _jideSplitPane;
/*      */   protected MouseHandler _mouseHandler;
/*      */   protected int _orientation;
/*   59 */   static final Cursor HORIZONTAL_CURSOR = JideCursors.getPredefinedCursor(20);
/*      */ 
/*   65 */   static final Cursor VERTICAL_CURSOR = JideCursors.getPredefinedCursor(21);
/*      */ 
/*   71 */   static final Cursor DEFAULT_CURSOR = Cursor.getDefaultCursor();
/*      */   private Painter _gripperPainter;
/*      */   public static final int COLLAPSED_STATE = 0;
/*      */   public static final int DEFAULT_STATE = 1;
/*      */   public static final int EXPANDED_STATE = 2;
/*  719 */   private int _currentState = 1;
/*      */ 
/*  724 */   protected JButton _leftButton = null;
/*      */ 
/*  729 */   protected JButton _rightButton = null;
/*      */ 
/*  734 */   private int _triangleSize = 5;
/*      */ 
/*  739 */   private int _buttonWidth = 5;
/*      */ 
/*  744 */   private int _buttonHeight = 10;
/*      */   private int _lastPosition;
/*      */ 
/*      */   public JideSplitPaneDivider(JideSplitPane splitPane)
/*      */   {
/*   82 */     setJideSplitPane(splitPane);
/*   83 */     this._orientation = this._jideSplitPane.getOrientation();
/*      */ 
/*   86 */     setDividerSize(splitPane.getDividerSize());
/*   87 */     setDefaultResizeCursor();
/*      */ 
/*   89 */     setBackground(UIDefaultsLookup.getColor("JideSplitPaneDivider.background"));
/*   90 */     setBorder(UIDefaultsLookup.getBorder("JideSplitPaneDivider.border"));
/*   91 */     if (this._jideSplitPane.isOneTouchExpandable()) {
/*   92 */       oneTouchExpandableChanged();
/*      */     }
/*   94 */     this._gripperPainter = ((Painter)UIDefaultsLookup.get("JideSplitPaneDivider.gripperPainter"));
/*   95 */     setOpaque(false);
/*   96 */     setLayout(null);
/*      */   }
/*      */ 
/*      */   public void setDefaultResizeCursor() {
/*  100 */     setCursor(this._orientation == 1 ? HORIZONTAL_CURSOR : VERTICAL_CURSOR);
/*      */   }
/*      */ 
/*      */   public JideSplitPane getJideSplitPane()
/*      */   {
/*  110 */     return this._jideSplitPane;
/*      */   }
/*      */ 
/*      */   public void setJideSplitPane(JideSplitPane splitPane)
/*      */   {
/*  119 */     uninstallListeners();
/*  120 */     this._jideSplitPane = splitPane;
/*  121 */     installListeners();
/*      */   }
/*      */ 
/*      */   private void installListeners() {
/*  125 */     if (this._jideSplitPane != null) {
/*  126 */       if (this._mouseHandler == null) {
/*  127 */         this._mouseHandler = createMouseHandler();
/*      */       }
/*  129 */       this._jideSplitPane.addMouseListener(this._mouseHandler);
/*  130 */       this._jideSplitPane.addMouseMotionListener(this._mouseHandler);
/*  131 */       addMouseListener(this._mouseHandler);
/*  132 */       addMouseMotionListener(this._mouseHandler);
/*  133 */       this._jideSplitPane.addPropertyChangeListener(this);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void uninstallListeners() {
/*  138 */     if (this._jideSplitPane != null) {
/*  139 */       this._jideSplitPane.removePropertyChangeListener(this);
/*  140 */       if (this._mouseHandler != null) {
/*  141 */         this._jideSplitPane.removeMouseListener(this._mouseHandler);
/*  142 */         this._jideSplitPane.removeMouseMotionListener(this._mouseHandler);
/*  143 */         removeMouseListener(this._mouseHandler);
/*  144 */         removeMouseMotionListener(this._mouseHandler);
/*  145 */         this._mouseHandler = null;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected MouseHandler createMouseHandler() {
/*  151 */     return new MouseHandler();
/*      */   }
/*      */ 
/*      */   public void setDividerSize(int newSize)
/*      */   {
/*  161 */     this._dividerSize = newSize;
/*      */   }
/*      */ 
/*      */   public int getDividerSize()
/*      */   {
/*  172 */     return this._dividerSize;
/*      */   }
/*      */ 
/*      */   public Dimension getPreferredSize()
/*      */   {
/*  180 */     return new Dimension(getDividerSize(), getDividerSize());
/*      */   }
/*      */ 
/*      */   public Dimension getMinimumSize()
/*      */   {
/*  188 */     return getPreferredSize();
/*      */   }
/*      */ 
/*      */   public void propertyChange(PropertyChangeEvent e)
/*      */   {
/*  196 */     if (e.getSource() == this._jideSplitPane)
/*  197 */       if ("orientation".equals(e.getPropertyName())) {
/*  198 */         this._orientation = this._jideSplitPane.getOrientation();
/*  199 */         setCursor(this._orientation == 1 ? HORIZONTAL_CURSOR : VERTICAL_CURSOR);
/*      */ 
/*  201 */         invalidate();
/*  202 */         validate();
/*      */       }
/*  204 */       else if ("oneTouchExpandable".equals(e.getPropertyName())) {
/*  205 */         setDividerSize(this._jideSplitPane.getDividerSize());
/*  206 */         oneTouchExpandableChanged();
/*      */       }
/*  208 */       else if ("dividerSize".equals(e.getPropertyName())) {
/*  209 */         setDividerSize(this._jideSplitPane.getDividerSize());
/*      */       }
/*  211 */       else if ("gripper".equals(e.getPropertyName())) {
/*  212 */         repaint();
/*      */       }
/*      */   }
/*      */ 
/*      */   public void updateUI()
/*      */   {
/*  233 */     super.updateUI();
/*  234 */     setBackground(UIDefaultsLookup.getColor("JideSplitPaneDivider.background"));
/*  235 */     setBorder(UIDefaultsLookup.getBorder("JideSplitPaneDivider.border"));
/*  236 */     this._gripperPainter = ((Painter)UIDefaultsLookup.get("JideSplitPaneDivider.gripperPainter"));
/*      */   }
/*      */ 
/*      */   public void paintComponent(Graphics g)
/*      */   {
/*  244 */     super.paintComponent(g);
/*      */ 
/*  246 */     Border border = getBorder();
/*      */ 
/*  248 */     Dimension size = getSize();
/*      */ 
/*  250 */     if (isOpaque()) {
/*  251 */       g.setColor(getBackground());
/*  252 */       g.fillRect(0, 0, size.width, size.height);
/*      */     }
/*      */ 
/*  255 */     if (border != null) {
/*  256 */       border.paintBorder(this, g, 0, 0, size.width, size.height);
/*      */     }
/*      */ 
/*  259 */     if (this._jideSplitPane.isShowGripper()) {
/*  260 */       Rectangle rect = new Rectangle(size);
/*  261 */       if (this._gripperPainter != null) {
/*  262 */         if (rect.width > rect.height)
/*      */         {
/*  265 */           this._gripperPainter.paint(this, g, rect, 1, 0);
/*      */         }
/*      */         else
/*      */         {
/*  270 */           this._gripperPainter.paint(this, g, rect, 0, 0);
/*      */         }
/*      */       }
/*      */       else {
/*  274 */         rect.x += 1;
/*  275 */         rect.y += 1;
/*  276 */         JideSwingUtilities.drawGrip(g, rect, 9, UIDefaultsLookup.getInt("JideSplitPane.dividerSize") / 3);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void prepareForDragging()
/*      */   {
/*  285 */     this._jideSplitPane.startDragging(this);
/*      */   }
/*      */ 
/*      */   protected void dragDividerTo(int location) {
/*  289 */     this._jideSplitPane.dragDividerTo(this, location);
/*      */ 
/*  294 */     this._currentState = 1;
/*  295 */     int indexOfDivider = this._jideSplitPane.indexOfDivider(this);
/*  296 */     this._lastPosition = this._jideSplitPane.getDividerLocation(indexOfDivider);
/*      */   }
/*      */ 
/*      */   protected void finishDraggingTo(int location) {
/*  300 */     this._jideSplitPane.finishDraggingTo(this, location);
/*      */   }
/*      */ 
/*      */   protected int getPreviousDividerLocation(boolean ignoreVisibility, boolean reversed) {
/*  304 */     return this._jideSplitPane.getPreviousDividerLocation(this, ignoreVisibility, reversed);
/*      */   }
/*      */ 
/*      */   protected int getNextDividerLocation(boolean ignoreVisibility, boolean reversed) {
/*  308 */     return this._jideSplitPane.getNextDividerLocation(this, ignoreVisibility, reversed);
/*      */   }
/*      */ 
/*      */   public Component getFirstComponent(boolean ignoreVisibility)
/*      */   {
/*  319 */     int index = this._jideSplitPane.indexOf(this);
/*  320 */     if (index - 1 >= 0) {
/*  321 */       for (int i = index - 1; i >= 0; i--) {
/*  322 */         if ((ignoreVisibility) || (this._jideSplitPane.getComponent(i).isVisible())) {
/*  323 */           return this._jideSplitPane.getComponent(i);
/*      */         }
/*      */       }
/*      */ 
/*  327 */       return this._jideSplitPane.getComponent(index - 1);
/*      */     }
/*      */ 
/*  330 */     throw new IndexOutOfBoundsException("There is no component before divider " + index);
/*      */   }
/*      */ 
/*      */   public Component getSecondComponent(boolean ignoreVisibility)
/*      */   {
/*  342 */     int index = this._jideSplitPane.indexOf(this);
/*      */ 
/*  344 */     if (index + 1 < this._jideSplitPane.getComponentCount()) {
/*  345 */       for (int i = index + 1; i >= 0; i++) {
/*  346 */         if ((ignoreVisibility) || (this._jideSplitPane.getComponent(i).isVisible())) {
/*  347 */           return this._jideSplitPane.getComponent(i);
/*      */         }
/*      */       }
/*      */ 
/*  351 */       return this._jideSplitPane.getComponent(index + 1);
/*      */     }
/*      */ 
/*  354 */     throw new IndexOutOfBoundsException("There is no component before divider " + index);
/*      */   }
/*      */ 
/*      */   protected void oneTouchExpandableChanged()
/*      */   {
/*  763 */     if ((this._jideSplitPane.isOneTouchExpandable()) && (this._leftButton == null)) {
/*  764 */       this._leftButton = createLeftOneTouchButton();
/*  765 */       if (this._leftButton != null) {
/*  766 */         this._leftButton.addActionListener(new OneTouchActionHandler(true));
/*  767 */         if (this._orientation == 1) {
/*  768 */           this._leftButton.setBounds(1, 10, this._buttonWidth, this._buttonHeight);
/*      */         }
/*  770 */         else if (this._orientation == 0)
/*      */         {
/*  772 */           this._leftButton.setBounds(10, 1, this._buttonHeight, this._buttonWidth);
/*      */         }
/*  774 */         add(this._leftButton);
/*      */       }
/*      */     }
/*      */ 
/*  778 */     if ((this._jideSplitPane.isOneTouchExpandable()) && (this._rightButton == null)) {
/*  779 */       this._rightButton = createRightOneTouchButton();
/*  780 */       if (this._rightButton != null) {
/*  781 */         this._rightButton.addActionListener(new OneTouchActionHandler(false));
/*  782 */         if (this._orientation == 1) {
/*  783 */           this._rightButton.setBounds(1, 25, this._buttonWidth, this._buttonHeight);
/*      */         }
/*  785 */         else if (this._orientation == 0)
/*      */         {
/*  787 */           this._rightButton.setBounds(25, 1, this._buttonHeight, this._buttonWidth);
/*      */         }
/*  789 */         add(this._rightButton);
/*      */       }
/*      */     }
/*      */ 
/*  793 */     if ((!this._jideSplitPane.isOneTouchExpandable()) && (this._leftButton != null)) {
/*  794 */       remove(this._leftButton);
/*  795 */       this._leftButton = null;
/*      */     }
/*  797 */     if ((!this._jideSplitPane.isOneTouchExpandable()) && (this._rightButton != null)) {
/*  798 */       remove(this._rightButton);
/*  799 */       this._rightButton = null;
/*      */     }
/*      */ 
/*  808 */     int paneCount = this._jideSplitPane.getPaneCount();
/*  809 */     if (this._jideSplitPane.isOneTouchExpandable()) {
/*  810 */       for (int i = 0; i < paneCount; i++) {
/*  811 */         Component component = this._jideSplitPane.getPaneAt(i);
/*  812 */         PortingUtils.setMinimumSize(component, new Dimension(0, 0));
/*      */       }
/*      */     }
/*      */     else
/*  816 */       for (int i = 0; i < paneCount; i++) {
/*  817 */         Component component = this._jideSplitPane.getPaneAt(i);
/*  818 */         PortingUtils.setMinimumSize(component, null);
/*      */       }
/*      */   }
/*      */ 
/*      */   protected JButton createLeftOneTouchButton()
/*      */   {
/*  829 */     JButton b = new JButton()
/*      */     {
/*      */       public void setBorder(Border b)
/*      */       {
/*      */       }
/*      */ 
/*      */       public void paint(Graphics g) {
/*  836 */         if (JideSplitPaneDivider.this._jideSplitPane != null) {
/*  837 */           g.setColor(getBackground());
/*  838 */           if (isOpaque()) {
/*  839 */             g.fillRect(0, 0, getWidth(), getHeight());
/*      */           }
/*      */ 
/*  842 */           if (JideSplitPaneDivider.this._jideSplitPane.getLeftOneTouchButtonImageIcon() != null) {
/*  843 */             JideSplitPaneDivider.this._jideSplitPane.getLeftOneTouchButtonImageIcon().paintIcon(this, g, 0, 0);
/*      */           }
/*  845 */           else if (JideSplitPaneDivider.this._orientation == 1)
/*      */           {
/*  850 */             g.setColor(JideSplitPaneDivider.this.getDarkShadowColor());
/*  851 */             int size = JideSplitPaneDivider.this._triangleSize;
/*  852 */             for (int i = 0; i < size; i++) {
/*  853 */               g.drawLine(i, size - i, i, size + i);
/*      */             }
/*      */           }
/*  856 */           else if (JideSplitPaneDivider.this._orientation == 0)
/*      */           {
/*  861 */             g.setColor(JideSplitPaneDivider.this.getDarkShadowColor());
/*  862 */             int size = JideSplitPaneDivider.this._triangleSize;
/*  863 */             for (int i = 0; i < size; i++)
/*  864 */               g.drawLine(size - i, i, size + i, i);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */       public boolean isFocusTraversable()
/*      */       {
/*  873 */         return false;
/*      */       }
/*      */     };
/*  876 */     b.setMinimumSize(new Dimension(this._buttonWidth, this._buttonHeight));
/*  877 */     b.setCursor(Cursor.getPredefinedCursor(0));
/*  878 */     b.setFocusPainted(false);
/*  879 */     b.setBorderPainted(false);
/*  880 */     b.setRequestFocusEnabled(false);
/*  881 */     return b;
/*      */   }
/*      */ 
/*      */   protected JButton createRightOneTouchButton()
/*      */   {
/*  890 */     JButton b = new JButton()
/*      */     {
/*      */       public void setBorder(Border b)
/*      */       {
/*      */       }
/*      */ 
/*      */       public void paint(Graphics g) {
/*  897 */         if (JideSplitPaneDivider.this._jideSplitPane != null) {
/*  898 */           g.setColor(getBackground());
/*  899 */           if (isOpaque()) {
/*  900 */             g.fillRect(0, 0, getWidth(), getHeight());
/*      */           }
/*      */ 
/*  903 */           if (JideSplitPaneDivider.this._jideSplitPane.getRightOneTouchButtonImageIcon() != null) {
/*  904 */             JideSplitPaneDivider.this._jideSplitPane.getRightOneTouchButtonImageIcon().paintIcon(this, g, 0, 0);
/*      */           }
/*  906 */           else if (JideSplitPaneDivider.this._orientation == 1)
/*      */           {
/*  911 */             g.setColor(JideSplitPaneDivider.this.getDarkShadowColor());
/*  912 */             int size = JideSplitPaneDivider.this._triangleSize;
/*  913 */             int j = 0;
/*  914 */             for (int i = size - 1; i >= 0; i--) {
/*  915 */               g.drawLine(j, size - i, j, size + i);
/*  916 */               j++;
/*      */             }
/*      */           }
/*  919 */           else if (JideSplitPaneDivider.this._orientation == 0)
/*      */           {
/*  924 */             g.setColor(JideSplitPaneDivider.this.getDarkShadowColor());
/*  925 */             int size = JideSplitPaneDivider.this._triangleSize;
/*  926 */             int j = 0;
/*  927 */             for (int i = size - 1; i >= 0; i--) {
/*  928 */               g.drawLine(size - i, j, size + i, j);
/*  929 */               j++;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */       public boolean isFocusTraversable()
/*      */       {
/*  938 */         return false;
/*      */       }
/*      */     };
/*  941 */     b.setMinimumSize(new Dimension(this._buttonWidth, this._buttonHeight));
/*  942 */     b.setCursor(Cursor.getPredefinedCursor(0));
/*  943 */     b.setFocusPainted(false);
/*  944 */     b.setBorderPainted(false);
/*  945 */     b.setRequestFocusEnabled(false);
/*  946 */     return b;
/*      */   }
/*      */ 
/*      */   protected Color getDarkShadowColor()
/*      */   {
/*  956 */     return UIManager.getColor("controlDkShadow");
/*      */   }
/*      */ 
/*      */   public void collapse()
/*      */   {
/* 1046 */     if (this._leftButton != null) {
/* 1047 */       this._leftButton.doClick();
/*      */     }
/*      */     else
/* 1050 */       new OneTouchActionHandler(true).actionPerformed(null);
/*      */   }
/*      */ 
/*      */   public void expand()
/*      */   {
/* 1058 */     if (this._rightButton != null) {
/* 1059 */       this._rightButton.doClick();
/*      */     }
/*      */     else
/* 1062 */       new OneTouchActionHandler(false).actionPerformed(null);
/*      */   }
/*      */ 
/*      */   protected class OneTouchActionHandler
/*      */     implements ActionListener
/*      */   {
/*      */     private boolean _collapse;
/*      */ 
/*      */     public OneTouchActionHandler(boolean collapse)
/*      */     {
/*  977 */       this._collapse = collapse;
/*      */     }
/*      */ 
/*      */     public void actionPerformed(ActionEvent e)
/*      */     {
/*  992 */       if (this._collapse) {
/*  993 */         if (JideSplitPaneDivider.this._currentState == 0) {
/*  994 */           int indexOfDivider = JideSplitPaneDivider.this._jideSplitPane.indexOfDivider(JideSplitPaneDivider.this);
/*  995 */           int dividerPosition = JideSplitPaneDivider.this._jideSplitPane.getDividerLocation(indexOfDivider);
/*  996 */           int previousDividerPosition = JideSplitPaneDivider.this.getPreviousDividerLocation(true, false);
/*  997 */           if (dividerPosition != previousDividerPosition) {
/*  998 */             JideSplitPaneDivider.access$102(JideSplitPaneDivider.this, 1);
/*      */           }
/*      */         }
/*      */ 
/* 1002 */         if (JideSplitPaneDivider.this._currentState == 2) {
/* 1003 */           JideSplitPaneDivider.this._jideSplitPane.setDividerLocation(JideSplitPaneDivider.this, JideSplitPaneDivider.this._lastPosition);
/* 1004 */           JideSplitPaneDivider.access$102(JideSplitPaneDivider.this, 1);
/*      */         }
/* 1006 */         else if (JideSplitPaneDivider.this._currentState == 1) {
/* 1007 */           int indexOfDivider = JideSplitPaneDivider.this._jideSplitPane.indexOfDivider(JideSplitPaneDivider.this);
/* 1008 */           JideSplitPaneDivider.access$202(JideSplitPaneDivider.this, JideSplitPaneDivider.this._jideSplitPane.getDividerLocation(indexOfDivider));
/* 1009 */           int loc = JideSplitPaneDivider.this.getPreviousDividerLocation(true, false);
/* 1010 */           JideSplitPaneDivider.this._jideSplitPane.setDividerLocation(JideSplitPaneDivider.this, loc);
/* 1011 */           JideSplitPaneDivider.access$102(JideSplitPaneDivider.this, 0);
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1018 */         if (JideSplitPaneDivider.this._currentState == 2) {
/* 1019 */           int indexOfDivider = JideSplitPaneDivider.this._jideSplitPane.indexOfDivider(JideSplitPaneDivider.this);
/* 1020 */           int dividerPosition = JideSplitPaneDivider.this._jideSplitPane.getDividerLocation(indexOfDivider);
/* 1021 */           int nextDividerPosition = JideSplitPaneDivider.this.getNextDividerLocation(true, false);
/* 1022 */           if (dividerPosition != nextDividerPosition) {
/* 1023 */             JideSplitPaneDivider.access$102(JideSplitPaneDivider.this, 1);
/*      */           }
/*      */         }
/*      */ 
/* 1027 */         if (JideSplitPaneDivider.this._currentState == 0) {
/* 1028 */           JideSplitPaneDivider.this._jideSplitPane.setDividerLocation(JideSplitPaneDivider.this, JideSplitPaneDivider.this._lastPosition);
/* 1029 */           JideSplitPaneDivider.access$102(JideSplitPaneDivider.this, 1);
/*      */         }
/* 1031 */         else if (JideSplitPaneDivider.this._currentState == 1) {
/* 1032 */           int indexOfDivider = JideSplitPaneDivider.this._jideSplitPane.indexOfDivider(JideSplitPaneDivider.this);
/* 1033 */           JideSplitPaneDivider.access$202(JideSplitPaneDivider.this, JideSplitPaneDivider.this._jideSplitPane.getDividerLocation(indexOfDivider));
/* 1034 */           int loc = JideSplitPaneDivider.this.getNextDividerLocation(true, false);
/* 1035 */           JideSplitPaneDivider.this._jideSplitPane.setDividerLocation(JideSplitPaneDivider.this, loc);
/* 1036 */           JideSplitPaneDivider.access$102(JideSplitPaneDivider.this, 2);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected class VerticalDragController extends JideSplitPaneDivider.DragController
/*      */   {
/*      */     protected VerticalDragController(MouseEvent e)
/*      */     {
/*  602 */       super(e);
/*  603 */       Component leftC = JideSplitPaneDivider.this.getFirstComponent(false);
/*  604 */       Component rightC = JideSplitPaneDivider.this.getSecondComponent(false);
/*      */ 
/*  606 */       this.initialLocation = JideSplitPaneDivider.this.getLocation().y;
/*  607 */       if (e.getSource() == JideSplitPaneDivider.this) {
/*  608 */         this.offset = e.getY();
/*      */       }
/*      */       else {
/*  611 */         this.offset = (e.getY() - this.initialLocation);
/*      */       }
/*  613 */       if ((leftC == null) || (rightC == null) || (this.offset < -1) || (this.offset >= JideSplitPaneDivider.this._jideSplitPane.getSize().height))
/*      */       {
/*  616 */         this.maxLocation = -1;
/*      */       }
/*      */       else {
/*  619 */         int index = JideSplitPaneDivider.this._jideSplitPane.indexOf(JideSplitPaneDivider.this);
/*  620 */         int modelUpHeight = 0;
/*  621 */         int modelDownHeight = 0;
/*  622 */         for (int i = 0; i < index; i++) {
/*  623 */           Component component = JideSplitPaneDivider.this._jideSplitPane.getComponent(i);
/*  624 */           if ((component instanceof JideSplitPaneDivider)) {
/*  625 */             modelUpHeight = (int)(modelUpHeight + component.getSize().getHeight());
/*      */           }
/*  627 */           else if (component.isVisible()) {
/*  628 */             if (((JideBoxLayout)JideSplitPaneDivider.this._jideSplitPane.getLayout()).getConstraintMap().get(component) == "fix") {
/*  629 */               modelUpHeight += component.getHeight();
/*      */             }
/*      */             else {
/*  632 */               modelUpHeight = (int)(modelUpHeight + component.getMinimumSize().getHeight());
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/*  637 */         for (int i = index + 1; i < JideSplitPaneDivider.this._jideSplitPane.getComponentCount(); i++) {
/*  638 */           Component component = JideSplitPaneDivider.this._jideSplitPane.getComponent(i);
/*  639 */           if ((component instanceof JideSplitPaneDivider)) {
/*  640 */             modelDownHeight = (int)(modelDownHeight + component.getSize().getHeight());
/*      */           }
/*  642 */           else if (component.isVisible()) {
/*  643 */             if (((JideBoxLayout)JideSplitPaneDivider.this._jideSplitPane.getLayout()).getConstraintMap().get(component) == "fix") {
/*  644 */               modelDownHeight += component.getHeight();
/*      */             }
/*      */             else {
/*  647 */               modelDownHeight = (int)(modelDownHeight + component.getMinimumSize().getHeight());
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/*  652 */         this.minLocation = modelUpHeight;
/*  653 */         this.maxLocation = (JideSplitPaneDivider.this._jideSplitPane.getHeight() - modelDownHeight - (int)JideSplitPaneDivider.this.getSize().getHeight());
/*  654 */         if (this.maxLocation < this.minLocation) this.minLocation = (this.maxLocation = 0);
/*      */       }
/*      */     }
/*      */ 
/*      */     protected int getNeededLocation(int x, int y)
/*      */     {
/*  665 */       int newY = Math.min(this.maxLocation, Math.max(this.minLocation, y - this.offset));
/*  666 */       if (JideSplitPaneDivider.this._jideSplitPane.getDividerStepSize() != 0) {
/*  667 */         int distanceFromCurrent = newY - JideSplitPaneDivider.this.getY();
/*  668 */         newY -= distanceFromCurrent % JideSplitPaneDivider.this._jideSplitPane.getDividerStepSize();
/*      */       }
/*  670 */       return newY;
/*      */     }
/*      */ 
/*      */     protected int positionForMouseEvent(MouseEvent e)
/*      */     {
/*  679 */       int newY = e.getSource() == JideSplitPaneDivider.this ? e.getY() + JideSplitPaneDivider.this.getLocation().y : e.getY();
/*  680 */       newY = Math.min(this.maxLocation, Math.max(this.minLocation, newY - this.offset));
/*  681 */       if (JideSplitPaneDivider.this._jideSplitPane.getDividerStepSize() != 0) {
/*  682 */         int distanceFromCurrent = newY - JideSplitPaneDivider.this.getY();
/*  683 */         newY -= distanceFromCurrent % JideSplitPaneDivider.this._jideSplitPane.getDividerStepSize();
/*      */       }
/*  685 */       return newY;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected class DragController
/*      */   {
/*      */     int initialLocation;
/*      */     int maxLocation;
/*      */     int minLocation;
/*      */     int offset;
/*      */ 
/*      */     protected DragController(MouseEvent e)
/*      */     {
/*  457 */       ComponentOrientation o = JideSplitPaneDivider.this.getComponentOrientation();
/*  458 */       boolean ltr = o.isLeftToRight();
/*  459 */       boolean reversed = (!ltr) && (JideSplitPaneDivider.this._jideSplitPane.getOrientation() == 1);
/*  460 */       Component leftC = reversed ? JideSplitPaneDivider.this.getSecondComponent(false) : JideSplitPaneDivider.this.getFirstComponent(false);
/*  461 */       Component rightC = reversed ? JideSplitPaneDivider.this.getFirstComponent(false) : JideSplitPaneDivider.this.getSecondComponent(false);
/*      */ 
/*  463 */       this.initialLocation = JideSplitPaneDivider.this.getLocation().x;
/*  464 */       if (e.getSource() == JideSplitPaneDivider.this) {
/*  465 */         this.offset = e.getX();
/*      */       }
/*      */       else {
/*  468 */         this.offset = (e.getX() - this.initialLocation);
/*      */       }
/*  470 */       if ((leftC == null) || (rightC == null) || (this.offset < -1) || (this.offset >= JideSplitPaneDivider.this._jideSplitPane.getSize().width))
/*      */       {
/*  473 */         this.maxLocation = -1;
/*      */       }
/*      */       else {
/*  476 */         int index = JideSplitPaneDivider.this._jideSplitPane.indexOf(JideSplitPaneDivider.this);
/*  477 */         int modelLeftWidth = 0;
/*  478 */         int modelRightWidth = 0;
/*  479 */         for (int i = 0; i < index; i++) {
/*  480 */           Component component = JideSplitPaneDivider.this._jideSplitPane.getComponent(i);
/*  481 */           if ((component instanceof JideSplitPaneDivider)) {
/*  482 */             modelLeftWidth = (int)(modelLeftWidth + component.getSize().getWidth());
/*      */           }
/*  484 */           else if (component.isVisible()) {
/*  485 */             if (((JideBoxLayout)JideSplitPaneDivider.this._jideSplitPane.getLayout()).getConstraintMap().get(component) == "fix") {
/*  486 */               modelLeftWidth += component.getWidth();
/*      */             }
/*      */             else {
/*  489 */               modelLeftWidth = (int)(modelLeftWidth + component.getMinimumSize().getWidth());
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/*  494 */         for (int i = index + 1; i < JideSplitPaneDivider.this._jideSplitPane.getComponentCount(); i++) {
/*  495 */           Component component = JideSplitPaneDivider.this._jideSplitPane.getComponent(i);
/*  496 */           if ((component instanceof JideSplitPaneDivider)) {
/*  497 */             modelRightWidth = (int)(modelRightWidth + component.getSize().getWidth());
/*      */           }
/*  499 */           else if (component.isVisible()) {
/*  500 */             if (((JideBoxLayout)JideSplitPaneDivider.this._jideSplitPane.getLayout()).getConstraintMap().get(component) == "fix") {
/*  501 */               modelRightWidth += component.getWidth();
/*      */             }
/*      */             else {
/*  504 */               modelRightWidth = (int)(modelRightWidth + component.getMinimumSize().getWidth());
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/*  509 */         this.minLocation = (reversed ? modelRightWidth : modelLeftWidth);
/*  510 */         this.maxLocation = (JideSplitPaneDivider.this._jideSplitPane.getWidth() - ((reversed ? modelLeftWidth : modelRightWidth) + (int)JideSplitPaneDivider.this.getSize().getWidth()));
/*  511 */         if (this.maxLocation < this.minLocation) this.minLocation = (this.maxLocation = 0);
/*      */       }
/*      */     }
/*      */ 
/*      */     protected boolean isValid()
/*      */     {
/*  522 */       return this.maxLocation > 0;
/*      */     }
/*      */ 
/*      */     protected int positionForMouseEvent(MouseEvent e)
/*      */     {
/*  533 */       int newX = e.getSource() == JideSplitPaneDivider.this ? e.getX() + JideSplitPaneDivider.this.getLocation().x : e.getX();
/*  534 */       newX = Math.min(this.maxLocation, Math.max(this.minLocation, newX - this.offset));
/*  535 */       if (JideSplitPaneDivider.this._jideSplitPane.getDividerStepSize() != 0) {
/*  536 */         int distanceFromCurrent = newX - JideSplitPaneDivider.this.getX();
/*  537 */         newX -= distanceFromCurrent % JideSplitPaneDivider.this._jideSplitPane.getDividerStepSize();
/*      */       }
/*      */ 
/*  540 */       return newX;
/*      */     }
/*      */ 
/*      */     protected int getNeededLocation(int x, int y)
/*      */     {
/*  553 */       int newX = Math.min(this.maxLocation, Math.max(this.minLocation, x - this.offset));
/*  554 */       if (JideSplitPaneDivider.this._jideSplitPane.getDividerStepSize() != 0) {
/*  555 */         int distanceFromCurrent = newX - JideSplitPaneDivider.this.getX();
/*  556 */         newX -= distanceFromCurrent % JideSplitPaneDivider.this._jideSplitPane.getDividerStepSize();
/*      */       }
/*  558 */       return newX;
/*      */     }
/*      */ 
/*      */     protected void continueDrag(int newX, int newY)
/*      */     {
/*  563 */       JideSplitPaneDivider.this.dragDividerTo(getNeededLocation(newX, newY));
/*      */     }
/*      */ 
/*      */     protected void continueDrag(MouseEvent e)
/*      */     {
/*  573 */       JideSplitPaneDivider.this.dragDividerTo(positionForMouseEvent(e));
/*      */     }
/*      */ 
/*      */     protected void completeDrag(int x, int y)
/*      */     {
/*  578 */       JideSplitPaneDivider.this.finishDraggingTo(getNeededLocation(x, y));
/*      */     }
/*      */ 
/*      */     protected void completeDrag(MouseEvent e)
/*      */     {
/*  588 */       JideSplitPaneDivider.this.finishDraggingTo(positionForMouseEvent(e));
/*      */     }
/*      */   }
/*      */ 
/*      */   protected class MouseHandler extends MouseInputAdapter
/*      */   {
/*      */     protected MouseHandler()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void mousePressed(MouseEvent e)
/*      */     {
/*  369 */       if ((e.getSource() == JideSplitPaneDivider.this) && (JideSplitPaneDivider.this._dragger == null) && (JideSplitPaneDivider.this._jideSplitPane.isEnabled()) && (JideSplitPaneDivider.this._jideSplitPane.isDragResizable()))
/*      */       {
/*  371 */         if ((JideSplitPaneDivider.this.getFirstComponent(true) != null) && (JideSplitPaneDivider.this.getSecondComponent(true) != null))
/*      */         {
/*  373 */           if (JideSplitPaneDivider.this._orientation == 1) {
/*  374 */             JideSplitPaneDivider.this._dragger = new JideSplitPaneDivider.DragController(JideSplitPaneDivider.this, e);
/*      */           }
/*      */           else {
/*  377 */             JideSplitPaneDivider.this._dragger = new JideSplitPaneDivider.VerticalDragController(JideSplitPaneDivider.this, e);
/*      */           }
/*  379 */           if (!JideSplitPaneDivider.this._dragger.isValid()) {
/*  380 */             JideSplitPaneDivider.this._dragger = null;
/*      */           }
/*      */           else {
/*  383 */             JideSplitPaneDivider.this.prepareForDragging();
/*  384 */             JideSplitPaneDivider.this._dragger.continueDrag(e);
/*      */           }
/*      */         }
/*  387 */         e.consume();
/*      */       }
/*      */     }
/*      */ 
/*      */     public void mouseReleased(MouseEvent e)
/*      */     {
/*  397 */       if (JideSplitPaneDivider.this._dragger != null) {
/*  398 */         if (e.getSource() == JideSplitPaneDivider.this._jideSplitPane) {
/*  399 */           JideSplitPaneDivider.this._dragger.completeDrag(e.getX(), e.getY());
/*      */         }
/*  401 */         else if (e.getSource() == JideSplitPaneDivider.this) {
/*  402 */           Point ourLoc = JideSplitPaneDivider.this.getLocation();
/*  403 */           JideSplitPaneDivider.this._dragger.completeDrag(e.getX() + ourLoc.x, e.getY() + ourLoc.y);
/*      */         }
/*  405 */         JideSplitPaneDivider.this._dragger = null;
/*  406 */         e.consume();
/*      */       }
/*      */     }
/*      */ 
/*      */     public void mouseDragged(MouseEvent e)
/*      */     {
/*  419 */       if (JideSplitPaneDivider.this._dragger != null) {
/*  420 */         if (e.getSource() == JideSplitPaneDivider.this._jideSplitPane) {
/*  421 */           JideSplitPaneDivider.this._dragger.continueDrag(e.getX(), e.getY());
/*      */         }
/*  423 */         else if (e.getSource() == JideSplitPaneDivider.this) {
/*  424 */           Point ourLoc = JideSplitPaneDivider.this.getLocation();
/*  425 */           JideSplitPaneDivider.this._dragger.continueDrag(e.getX() + ourLoc.x, e.getY() + ourLoc.y);
/*      */         }
/*  427 */         e.consume();
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.JideSplitPaneDivider
 * JD-Core Version:    0.6.0
 */
/*      */ package com.jidesoft.swing;
/*      */ 
/*      */ import D;
/*      */ import com.jidesoft.plaf.LookAndFeelFactory;
/*      */ import com.jidesoft.plaf.UIDefaultsLookup;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.ComponentEvent;
/*      */ import java.awt.event.ComponentListener;
/*      */ import java.awt.event.ContainerEvent;
/*      */ import java.awt.event.ContainerListener;
/*      */ import java.awt.event.WindowAdapter;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.io.PrintStream;
/*      */ import java.util.Arrays;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleState;
/*      */ import javax.accessibility.AccessibleStateSet;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JComponent.AccessibleJComponent;
/*      */ import javax.swing.JLayeredPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.RootPaneContainer;
/*      */ import javax.swing.SwingUtilities;
/*      */ 
/*      */ public class JideSplitPane extends JPanel
/*      */   implements ContainerListener, ComponentListener, Accessible
/*      */ {
/*      */   protected static final String NON_CONTINUOUS_DIVIDER = "nonContinuousDivider";
/*      */   public static final int VERTICAL_SPLIT = 0;
/*      */   public static final int HORIZONTAL_SPLIT = 1;
/*      */   public static final String ORIENTATION_PROPERTY = "orientation";
/*      */   public static final String DIVIDER_SIZE_PROPERTY = "dividerSize";
/*      */   public static final String PROPERTY_DIVIDER_LOCATION = "dividerLocation";
/*      */   public static final String CONTINUOUS_LAYOUT_PROPERTY = "continuousLayout";
/*      */   public static final String GRIPPER_PROPERTY = "gripper";
/*      */   public static final String PROPORTIONAL_LAYOUT_PROPERTY = "proportionalLayout";
/*      */   public static final String PROPORTIONS_PROPERTY = "proportions";
/*      */   public static final String PROPERTY_HEAVYWEIGHT_COMPONENT_ENABLED = "heavyweightComponentEnabled";
/*      */   private int _orientation;
/*   93 */   private int _dividerSize = UIDefaultsLookup.getInt("JideSplitPane.dividerSize");
/*      */   private HeavyweightWrapper _nonContinuousLayoutDividerWrapper;
/*  105 */   private boolean _continuousLayout = false;
/*      */   private Container _layeredPane;
/*  115 */   private boolean _showGripper = false;
/*      */ 
/*  120 */   private boolean _proportionalLayout = false;
/*      */   private double[] _proportions;
/*  132 */   private boolean _initiallyEven = true;
/*      */ 
/*  134 */   private boolean _heavyweightComponentEnabled = false;
/*      */   public WindowAdapter _windowDeactivatedListener;
/*  136 */   private int _dividerStepSize = 0;
/*  137 */   private boolean _dragResizable = true;
/*      */   public static final String ONE_TOUCH_EXPANDABLE_PROPERTY = "oneTouchExpandable";
/* 1292 */   private boolean _oneTouchExpandable = false;
/*      */ 
/* 1297 */   private int oneTouchExpandableDividerSize = 8;
/*      */ 
/* 1305 */   private ImageIcon _leftOneTouchButtonImageIcon = null;
/*      */ 
/* 1313 */   private ImageIcon _rightOneTouchButtonImageIcon = null;
/*      */ 
/*      */   public JideSplitPane()
/*      */   {
/*  143 */     this(1);
/*      */   }
/*      */ 
/*      */   public JideSplitPane(int newOrientation)
/*      */   {
/*  156 */     this._orientation = newOrientation;
/*      */ 
/*  158 */     if ((this._orientation != 1) && (this._orientation != 0))
/*  159 */       throw new IllegalArgumentException("cannot create JideSplitPane, orientation must be one of JideSplitPane.HORIZONTAL_SPLIT or JideSplitPane.VERTICAL_SPLIT");
/*      */     LayoutManager layoutManager;
/*      */     LayoutManager layoutManager;
/*  166 */     if (this._orientation == 1) {
/*  167 */       layoutManager = new JideSplitPaneLayout(this, 0);
/*      */     }
/*      */     else {
/*  170 */       layoutManager = new JideSplitPaneLayout(this, 1);
/*      */     }
/*  172 */     super.setLayout(layoutManager);
/*      */ 
/*  174 */     setOpaque(false);
/*      */ 
/*  177 */     installListeners();
/*      */   }
/*      */ 
/*      */   public int getDividerStepSize()
/*      */   {
/*  188 */     return this._dividerStepSize;
/*      */   }
/*      */ 
/*      */   public void setDividerStepSize(int dividerStepSize)
/*      */   {
/*  199 */     if (dividerStepSize < 0) {
/*  200 */       return;
/*      */     }
/*  202 */     this._dividerStepSize = dividerStepSize;
/*      */   }
/*      */ 
/*      */   public void updateUI()
/*      */   {
/*  207 */     if (UIDefaultsLookup.get("JideSplitPane.dividerSize") == null) {
/*  208 */       LookAndFeelFactory.installJideExtension();
/*      */     }
/*  210 */     super.updateUI();
/*      */   }
/*      */ 
/*      */   private void installListeners()
/*      */   {
/*  217 */     addContainerListener(this);
/*      */   }
/*      */ 
/*      */   public void setDividerSize(int newSize)
/*      */   {
/*  226 */     int oldSize = this._dividerSize;
/*      */ 
/*  228 */     if (oldSize != newSize) {
/*  229 */       this._dividerSize = newSize;
/*  230 */       firePropertyChange("dividerSize", oldSize, newSize);
/*  231 */       invalidate();
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getDividerSize()
/*      */   {
/*  242 */     return this._dividerSize;
/*      */   }
/*      */ 
/*      */   public Component insertPane(Component pane, int index)
/*      */   {
/*  253 */     return insertPane(pane, null, index);
/*      */   }
/*      */ 
/*      */   public Component insertPane(Component pane, Object constraint, int index)
/*      */   {
/*  265 */     if (index <= 0) {
/*  266 */       addImpl(pane, constraint, 0);
/*      */     }
/*  268 */     else if (index >= getPaneCount()) {
/*  269 */       addImpl(pane, constraint, -1);
/*      */     }
/*      */     else {
/*  272 */       addImpl(pane, constraint, (index << 1) - 1);
/*      */     }
/*      */ 
/*  275 */     return pane;
/*      */   }
/*      */ 
/*      */   public Component addPane(Component pane)
/*      */   {
/*  285 */     if (pane == null) {
/*  286 */       return null;
/*      */     }
/*  288 */     return super.add(pane);
/*      */   }
/*      */ 
/*      */   public void removePane(Component pane)
/*      */   {
/*  297 */     removePane(indexOfPane(pane));
/*      */   }
/*      */ 
/*      */   public void setPaneAt(Component pane, int index)
/*      */   {
/*  307 */     setPaneAt(pane, null, index);
/*      */   }
/*      */ 
/*      */   public void setPaneAt(Component pane, Object constraint, int index)
/*      */   {
/*  318 */     double[] proportions = this._proportions;
/*  319 */     this._proportions = null;
/*  320 */     removePane(index);
/*  321 */     insertPane(pane, constraint, index);
/*  322 */     this._proportions = proportions;
/*      */   }
/*      */ 
/*      */   public void removePane(int index)
/*      */   {
/*  331 */     if (index == 0) {
/*  332 */       super.remove(0);
/*      */     }
/*      */     else
/*  335 */       super.remove(index << 1);
/*      */   }
/*      */ 
/*      */   public void setOrientation(int orientation)
/*      */   {
/*  348 */     if ((orientation != 0) && (orientation != 1))
/*      */     {
/*  350 */       throw new IllegalArgumentException("JideSplitPane: orientation must be one of JideSplitPane.VERTICAL_SPLIT or JideSplitPane.HORIZONTAL_SPLIT");
/*      */     }
/*      */ 
/*  356 */     if (this._orientation == orientation) {
/*  357 */       return;
/*      */     }
/*  359 */     int oldOrientation = this._orientation;
/*  360 */     this._orientation = orientation;
/*      */     LayoutManager layoutManager;
/*      */     LayoutManager layoutManager;
/*  367 */     if (this._orientation == 1) {
/*  368 */       layoutManager = new JideSplitPaneLayout(this, 0);
/*      */     }
/*      */     else {
/*  371 */       layoutManager = new JideSplitPaneLayout(this, 1);
/*      */     }
/*  373 */     super.setLayout(layoutManager);
/*  374 */     doLayout();
/*      */ 
/*  376 */     firePropertyChange("orientation", oldOrientation, orientation);
/*      */   }
/*      */ 
/*      */   public int getOrientation()
/*      */   {
/*  387 */     return this._orientation;
/*      */   }
/*      */ 
/*      */   public void resetToPreferredSizes()
/*      */   {
/*  395 */     doLayout();
/*      */   }
/*      */ 
/*      */   public void setProportionalLayout(boolean proportionalLayout)
/*      */   {
/*  405 */     if (proportionalLayout == this._proportionalLayout)
/*  406 */       return;
/*  407 */     this._proportionalLayout = proportionalLayout;
/*  408 */     revalidate();
/*  409 */     firePropertyChange("proportionalLayout", !proportionalLayout, proportionalLayout);
/*  410 */     if (!proportionalLayout)
/*  411 */       setProportions(null);
/*      */   }
/*      */ 
/*      */   public boolean isProportionalLayout()
/*      */   {
/*  420 */     return this._proportionalLayout;
/*      */   }
/*      */ 
/*      */   void internalSetProportions(double[] proportions) {
/*  424 */     this._proportions = proportions;
/*      */   }
/*      */ 
/*      */   public void setProportions(double[] proportions)
/*      */   {
/*  437 */     if ((!this._proportionalLayout) && (proportions != null))
/*  438 */       throw new IllegalStateException("Can't set proportions on a non-proportional split pane");
/*  439 */     if (Arrays.equals(proportions, this._proportions))
/*  440 */       return;
/*  441 */     if ((proportions != null) && (proportions.length != getPaneCount() - 1)) {
/*  442 */       throw new IllegalArgumentException("Must provide one fewer proportions than there are panes: got " + proportions.length + ", expected " + (getPaneCount() - 1));
/*      */     }
/*      */ 
/*  445 */     if (proportions != null) {
/*  446 */       double sum = 0.0D;
/*  447 */       for (int i = 0; i < proportions.length; i++) {
/*  448 */         if (proportions[i] < 0.0D)
/*  449 */           proportions[i] = 0.0D;
/*  450 */         if (proportions[i] > 1.0D)
/*  451 */           proportions[i] = 1.0D;
/*  452 */         sum += proportions[i];
/*      */       }
/*  454 */       if (sum > 1.0D)
/*  455 */         throw new IllegalArgumentException("Sum of proportions must be no more than 1, got " + sum);
/*      */     }
/*  457 */     double[] oldProportions = this._proportions;
/*  458 */     this._proportions = (proportions == null ? null : (double[])proportions.clone());
/*      */ 
/*  460 */     LayoutManager layoutManager = getLayout();
/*  461 */     boolean reset = false;
/*  462 */     if ((layoutManager instanceof JideBoxLayout)) {
/*  463 */       reset = ((JideBoxLayout)layoutManager).isResetWhenInvalidate();
/*  464 */       ((JideBoxLayout)layoutManager).setResetWhenInvalidate(true);
/*      */     }
/*  466 */     revalidate();
/*  467 */     if (reset) {
/*  468 */       ((JideBoxLayout)layoutManager).setResetWhenInvalidate(reset);
/*      */     }
/*  470 */     firePropertyChange("proportions", oldProportions, proportions);
/*      */   }
/*      */ 
/*      */   public double[] getProportions()
/*      */   {
/*  480 */     double[] answer = this._proportions;
/*  481 */     if (answer != null)
/*  482 */       answer = (double[])answer.clone();
/*  483 */     return answer;
/*      */   }
/*      */ 
/*      */   public void setInitiallyEven(boolean initiallyEven)
/*      */   {
/*  493 */     this._initiallyEven = initiallyEven;
/*      */   }
/*      */ 
/*      */   public boolean isInitiallyEven()
/*      */   {
/*  503 */     return this._initiallyEven;
/*      */   }
/*      */ 
/*      */   public boolean isValidateRoot()
/*      */   {
/*  516 */     return true;
/*      */   }
/*      */ 
/*      */   protected void startDragging(JideSplitPaneDivider divider)
/*      */   {
/*  525 */     if (!isContinuousLayout()) {
/*  526 */       Component topLevelAncestor = getTopLevelAncestor();
/*  527 */       if (this._windowDeactivatedListener == null)
/*      */       {
/*  529 */         this._windowDeactivatedListener = new WindowAdapter()
/*      */         {
/*      */           public void windowDeactivated(WindowEvent e) {
/*  532 */             JideSplitPane.this.stopDragging();
/*  533 */             if (e.getWindow() != null)
/*  534 */               e.getWindow().removeWindowListener(JideSplitPane.this._windowDeactivatedListener);
/*      */           }
/*      */         };
/*      */       }
/*  539 */       if ((topLevelAncestor instanceof Window))
/*  540 */         ((Window)topLevelAncestor).addWindowListener(this._windowDeactivatedListener);
/*  541 */       if ((topLevelAncestor instanceof RootPaneContainer)) {
/*  542 */         this._layeredPane = ((RootPaneContainer)topLevelAncestor).getLayeredPane();
/*      */ 
/*  545 */         if (this._nonContinuousLayoutDividerWrapper == null) {
/*  546 */           Contour nonContinuousLayoutDivider = new JideSplitPaneContour();
/*  547 */           this._nonContinuousLayoutDividerWrapper = new JideSplitPaneHeavyweightWrapper(nonContinuousLayoutDivider);
/*  548 */           this._nonContinuousLayoutDividerWrapper.setHeavyweight(isHeavyweightComponentEnabled());
/*      */         }
/*      */ 
/*  551 */         this._nonContinuousLayoutDividerWrapper.delegateSetCursor(this._orientation == 1 ? JideSplitPaneDivider.HORIZONTAL_CURSOR : JideSplitPaneDivider.VERTICAL_CURSOR);
/*      */ 
/*  553 */         this._nonContinuousLayoutDividerWrapper.delegateSetVisible(false);
/*  554 */         this._nonContinuousLayoutDividerWrapper.delegateAdd(this._layeredPane, JLayeredPane.DRAG_LAYER);
/*      */ 
/*  556 */         Rectangle bounds = getVisibleRect();
/*  557 */         Rectangle layeredPaneBounds = SwingUtilities.convertRectangle(this, bounds, this._layeredPane);
/*  558 */         int dividerThickness = Math.min(4, getDividerSize());
/*  559 */         if (getOrientation() == 1) {
/*  560 */           this._nonContinuousLayoutDividerWrapper.delegateSetBounds(layeredPaneBounds.x, layeredPaneBounds.y, dividerThickness, layeredPaneBounds.height);
/*      */         }
/*      */         else
/*      */         {
/*  564 */           this._nonContinuousLayoutDividerWrapper.delegateSetBounds(layeredPaneBounds.x, layeredPaneBounds.y, layeredPaneBounds.width, dividerThickness);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void stopDragging()
/*      */   {
/*  572 */     if ((!isContinuousLayout()) && (this._layeredPane != null) && (this._nonContinuousLayoutDividerWrapper != null)) {
/*  573 */       this._nonContinuousLayoutDividerWrapper.delegateSetVisible(false);
/*  574 */       this._nonContinuousLayoutDividerWrapper.delegateRemove(this._layeredPane);
/*  575 */       this._nonContinuousLayoutDividerWrapper.delegateSetNull();
/*  576 */       this._nonContinuousLayoutDividerWrapper = null;
/*      */ 
/*  578 */       Component[] childComponents = this._layeredPane.getComponents();
/*  579 */       for (Component component : childComponents)
/*  580 */         if (((component instanceof JideSplitPaneContour)) || ((component instanceof JideSplitPaneHeavyweightWrapper)))
/*  581 */           this._layeredPane.remove(component);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void dragDividerTo(JideSplitPaneDivider divider, int location)
/*      */   {
/*  595 */     if ((this._layeredPane == null) || (isContinuousLayout())) {
/*  596 */       setDividerLocation(divider, location);
/*      */     }
/*  599 */     else if (this._nonContinuousLayoutDividerWrapper != null)
/*      */     {
/*  601 */       Dimension size = new Dimension();
/*  602 */       Rectangle rect = getVisibleRect();
/*  603 */       int dividerThickness = Math.min(4, getDividerSize());
/*  604 */       Rectangle convertedRect = SwingUtilities.convertRectangle(this, rect, this._layeredPane);
/*      */       Point p;
/*  605 */       if (getOrientation() == 1) {
/*  606 */         Point p = SwingUtilities.convertPoint(this, location, rect.y, this._layeredPane);
/*  607 */         p.x += (getDividerSize() - dividerThickness >> 1);
/*  608 */         size.width = dividerThickness;
/*  609 */         size.height = convertedRect.height;
/*      */       }
/*      */       else {
/*  612 */         p = SwingUtilities.convertPoint(this, rect.x, location, this._layeredPane);
/*  613 */         p.y += (getDividerSize() - dividerThickness >> 1);
/*  614 */         size.width = convertedRect.width;
/*  615 */         size.height = dividerThickness;
/*      */       }
/*  617 */       this._nonContinuousLayoutDividerWrapper.delegateSetBounds(new Rectangle(p, size));
/*  618 */       this._nonContinuousLayoutDividerWrapper.delegateSetVisible(true);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void finishDraggingTo(JideSplitPaneDivider divider, int location)
/*      */   {
/*  630 */     if ((isContinuousLayout()) || (this._nonContinuousLayoutDividerWrapper != null)) {
/*  631 */       stopDragging();
/*  632 */       setDividerLocation(divider, location);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int indexOfDivider(JideSplitPaneDivider divider)
/*      */   {
/*  644 */     int index = indexOf(divider);
/*  645 */     if (index == -1) {
/*  646 */       return index;
/*      */     }
/*  648 */     if (index % 2 == 0)
/*      */     {
/*  650 */       System.err.println("Warning: divider's index is even. (index = " + index + ")");
/*  651 */     }return (index - 1) / 2;
/*      */   }
/*      */ 
/*      */   public int indexOfPane(Component pane)
/*      */   {
/*  663 */     int index = indexOf(pane);
/*  664 */     if (index == -1) {
/*  665 */       return -1;
/*      */     }
/*  667 */     if (index % 2 != 0)
/*      */     {
/*  669 */       System.err.println("Warning: pane's index is odd. (index = " + index + ")");
/*  670 */     }return index >> 1;
/*      */   }
/*      */ 
/*      */   public int indexOf(Component comp)
/*      */   {
/*  681 */     for (int i = 0; i < getComponentCount(); i++) {
/*  682 */       if (getComponent(i).equals(comp))
/*  683 */         return i;
/*      */     }
/*  685 */     return -1;
/*      */   }
/*      */ 
/*      */   public JideSplitPaneDivider getDividerAt(int index)
/*      */   {
/*  695 */     if ((index < 0) || (index * 2 + 1 >= getComponentCount()))
/*  696 */       return null;
/*  697 */     return (JideSplitPaneDivider)getComponent(index * 2 + 1);
/*      */   }
/*      */ 
/*      */   public Component getPaneAt(int index)
/*      */   {
/*  707 */     if ((index < 0) || (index << 1 >= getComponentCount()))
/*  708 */       return null;
/*  709 */     return getComponent(index << 1);
/*      */   }
/*      */ 
/*      */   public int getPaneCount()
/*      */   {
/*  718 */     return getComponentCount() + 1 >> 1;
/*      */   }
/*      */ 
/*      */   public void setDividerLocation(JideSplitPaneDivider divider, int location)
/*      */   {
/*  728 */     setDividerLocation(indexOfDivider(divider), location);
/*      */   }
/*      */ 
/*      */   public void setDividerLocation(int dividerIndex, int location)
/*      */   {
/*  739 */     ((JideSplitPaneLayout)getLayout()).setDividerLocation(dividerIndex, location, true);
/*      */   }
/*      */ 
/*      */   public int getDividerLocation(int dividerIndex)
/*      */   {
/*  750 */     return ((JideSplitPaneLayout)getLayout()).getDividerLocation(dividerIndex);
/*      */   }
/*      */ 
/*      */   public void componentAdded(ContainerEvent e)
/*      */   {
/*  760 */     e.getChild().addComponentListener(this);
/*  761 */     if (!(e.getChild() instanceof JideSplitPaneDivider)) {
/*  762 */       addExtraDividers();
/*  763 */       if (isOneTouchExpandable()) {
/*  764 */         e.getChild().setMinimumSize(new Dimension(0, 0));
/*      */       }
/*      */     }
/*  767 */     setDividersVisible();
/*  768 */     resetToPreferredSizes();
/*      */   }
/*      */ 
/*      */   public void componentRemoved(ContainerEvent e)
/*      */   {
/*  778 */     e.getChild().removeComponentListener(this);
/*  779 */     if (!(e.getChild() instanceof JideSplitPaneDivider)) {
/*  780 */       removeExtraDividers();
/*      */     }
/*  782 */     setDividersVisible();
/*  783 */     resetToPreferredSizes();
/*      */   }
/*      */ 
/*      */   public void componentResized(ComponentEvent e)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void componentMoved(ComponentEvent e)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void componentShown(ComponentEvent e)
/*      */   {
/*  821 */     if ((e.getComponent() instanceof JideSplitPaneDivider)) {
/*  822 */       return;
/*      */     }
/*  824 */     setDividersVisible();
/*  825 */     resetToPreferredSizes();
/*      */   }
/*      */ 
/*      */   public void componentHidden(ComponentEvent e) {
/*  829 */     if ((e.getComponent() instanceof JideSplitPaneDivider)) {
/*  830 */       return;
/*      */     }
/*  832 */     setDividersVisible();
/*  833 */     resetToPreferredSizes();
/*      */   }
/*      */ 
/*      */   protected boolean removeExtraDividers()
/*      */   {
/*  842 */     int extra = 0;
/*      */ 
/*  844 */     if (getComponentCount() == 0) {
/*  845 */       if (this._proportions != null)
/*  846 */         setProportions(null);
/*  847 */       return false;
/*      */     }
/*      */ 
/*  850 */     boolean changed = false;
/*      */ 
/*  852 */     if ((getComponent(0) instanceof JideSplitPaneDivider)) {
/*  853 */       remove(0);
/*  854 */       removeProportion(0);
/*  855 */       changed = true;
/*      */     }
/*      */ 
/*  858 */     for (int i = 0; i < getComponentCount(); i++) {
/*  859 */       Component comp = getComponent(i);
/*  860 */       if ((comp instanceof JideSplitPaneDivider)) {
/*  861 */         extra++;
/*  862 */         if (extra == 2) {
/*  863 */           remove(comp);
/*  864 */           if ((this._proportions != null) && (getPaneCount() == this._proportions.length))
/*  865 */             removeProportion(i / 2);
/*  866 */           changed = true;
/*  867 */           extra--;
/*  868 */           i--;
/*      */         }
/*      */       }
/*      */       else {
/*  872 */         extra = 0;
/*      */       }
/*      */     }
/*  875 */     if (extra == 1) {
/*  876 */       remove(getComponentCount() - 1);
/*  877 */       removeProportion((getComponentCount() + 1) / 2);
/*  878 */       changed = true;
/*      */     }
/*      */ 
/*  881 */     return changed;
/*      */   }
/*      */ 
/*      */   protected void removeProportion(int paneIndex)
/*      */   {
/*  891 */     double[] oldProportions = this._proportions;
/*  892 */     if (oldProportions == null)
/*  893 */       return;
/*  894 */     if (oldProportions.length <= 1) {
/*  895 */       setProportions(null);
/*  896 */       return;
/*      */     }
/*  898 */     double[] newProportions = new double[oldProportions.length - 1];
/*      */     double p;
/*      */     double p;
/*  900 */     if (paneIndex < oldProportions.length) {
/*  901 */       p = oldProportions[paneIndex];
/*      */     } else {
/*  903 */       p = 1.0D;
/*  904 */       for (double proportion : oldProportions) p -= proportion;
/*      */     }
/*  906 */     double total = 1.0D - p;
/*  907 */     for (int i = 0; i < newProportions.length; i++) {
/*  908 */       int j = i < paneIndex ? i : i + 1;
/*  909 */       oldProportions[j] /= total;
/*      */     }
/*      */ 
/*  912 */     if (newProportions.length == 1);
/*  916 */     setProportions(newProportions);
/*      */   }
/*      */ 
/*      */   protected void addExtraDividers()
/*      */   {
/*  923 */     int extra = 0;
/*  924 */     for (int i = 0; i < getComponentCount(); i++) {
/*  925 */       Component comp = getComponent(i);
/*  926 */       if (!(comp instanceof JideSplitPaneDivider)) {
/*  927 */         extra++;
/*  928 */         if (extra == 2) {
/*  929 */           add(createSplitPaneDivider(), "fix", i);
/*  930 */           if ((this._proportions != null) && (getPaneCount() == this._proportions.length + 2))
/*  931 */             addProportion((i + 1) / 2);
/*  932 */           extra = 0;
/*      */         }
/*      */       }
/*      */       else {
/*  936 */         extra = 0;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void addProportion(int paneIndex)
/*      */   {
/*  946 */     double[] oldProportions = this._proportions;
/*  947 */     if (oldProportions == null)
/*  948 */       return;
/*  949 */     double[] newProportions = new double[oldProportions.length + 1];
/*  950 */     double p = 1.0D / (newProportions.length + 1);
/*  951 */     double total = 1.0D - p;
/*  952 */     for (int i = 0; i < newProportions.length; i++) {
/*  953 */       if (i == paneIndex) {
/*  954 */         newProportions[i] = p;
/*      */       } else {
/*  956 */         int j = i < paneIndex ? i : i - 1;
/*  957 */         if (j < oldProportions.length)
/*  958 */           oldProportions[j] *= total;
/*      */         else
/*  960 */           newProportions[i] = p;
/*      */       }
/*      */     }
/*  963 */     setProportions(newProportions);
/*      */   }
/*      */ 
/*      */   protected void setDividersVisible()
/*      */   {
/*  970 */     if (getComponentCount() == 1) {
/*  971 */       setVisible(getComponent(0).isVisible());
/*      */     }
/*  973 */     else if (getComponentCount() > 1) {
/*  974 */       boolean anyVisible = false;
/*  975 */       boolean anyPrevVisible = false;
/*  976 */       for (int i = 0; i < getComponentCount(); i++) {
/*  977 */         Component comp = getComponent(i);
/*  978 */         if (!(comp instanceof JideSplitPaneDivider)) {
/*  979 */           if ((comp.isVisible()) && (!anyVisible))
/*  980 */             anyVisible = true;
/*      */         }
/*      */         else
/*      */         {
/*  984 */           boolean visiblePrev = (i - 1 >= 0) && (getComponent(i - 1).isVisible());
/*  985 */           boolean visibleNext = (i + 1 < getComponentCount()) && (getComponent(i + 1).isVisible());
/*  986 */           if ((visiblePrev) && (visibleNext)) {
/*  987 */             comp.setVisible(true);
/*      */           }
/*  989 */           else if ((!visiblePrev) && (!visibleNext)) {
/*  990 */             comp.setVisible(false);
/*      */           }
/*  992 */           else if ((visiblePrev) && (!visibleNext)) {
/*  993 */             comp.setVisible(false);
/*  994 */             anyPrevVisible = true;
/*      */           }
/*  997 */           else if (anyPrevVisible) {
/*  998 */             comp.setVisible(true);
/*  999 */             anyPrevVisible = false;
/*      */           }
/*      */           else {
/* 1002 */             comp.setVisible(false);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 1007 */       setVisible(anyVisible);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected JideSplitPaneDivider createSplitPaneDivider() {
/* 1012 */     return new JideSplitPaneDivider(this);
/*      */   }
/*      */ 
/*      */   protected int getPreviousDividerLocation(JideSplitPaneDivider divider, boolean ignoreVisibility, boolean reversed)
/*      */   {
/* 1024 */     int index = indexOfDivider(divider);
/* 1025 */     int location = -1;
/* 1026 */     if (reversed) {
/* 1027 */       if ((index + 1) * 2 + 1 <= getComponentCount()) {
/* 1028 */         for (int i = index + 1; i * 2 + 1 < getComponentCount(); i++) {
/* 1029 */           if ((ignoreVisibility) || (getDividerAt(i).isVisible())) {
/* 1030 */             if (this._orientation == 1) {
/* 1031 */               location = getDividerAt(i).getBounds().x; break;
/*      */             }
/*      */ 
/* 1034 */             location = getDividerAt(i).getBounds().y;
/*      */ 
/* 1036 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/* 1042 */     else if (index > 0) {
/* 1043 */       for (int i = index - 1; i >= 0; i--) {
/* 1044 */         if ((ignoreVisibility) || (getDividerAt(i).isVisible())) {
/* 1045 */           if (this._orientation == 1) {
/* 1046 */             location = getDividerAt(i).getBounds().x; break;
/*      */           }
/*      */ 
/* 1049 */           location = getDividerAt(i).getBounds().y;
/*      */ 
/* 1051 */           break;
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1057 */     if (location != -1) {
/* 1058 */       return location + getDividerSize();
/*      */     }
/*      */ 
/* 1061 */     return 0;
/*      */   }
/*      */ 
/*      */   public int getNextDividerLocation(JideSplitPaneDivider divider, boolean ignoreVisibility, boolean reversed)
/*      */   {
/* 1073 */     int index = indexOfDivider(divider);
/* 1074 */     int location = -1;
/* 1075 */     if (!reversed) {
/* 1076 */       if ((index + 1) * 2 + 1 <= getComponentCount()) {
/* 1077 */         for (int i = index + 1; i * 2 + 1 < getComponentCount(); i++) {
/* 1078 */           if ((ignoreVisibility) || (getDividerAt(i).isVisible())) {
/* 1079 */             if (this._orientation == 1) {
/* 1080 */               location = getDividerAt(i).getBounds().x; break;
/*      */             }
/*      */ 
/* 1083 */             location = getDividerAt(i).getBounds().y;
/*      */ 
/* 1085 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/* 1091 */     else if (index > 0) {
/* 1092 */       for (int i = index - 1; i >= 0; i--) {
/* 1093 */         if ((ignoreVisibility) || (getDividerAt(i).isVisible())) {
/* 1094 */           if (this._orientation == 1) {
/* 1095 */             location = getDividerAt(i).getBounds().x; break;
/*      */           }
/*      */ 
/* 1098 */           location = getDividerAt(i).getBounds().y;
/*      */ 
/* 1100 */           break;
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1106 */     if (location != -1) {
/* 1107 */       return location - getDividerSize();
/*      */     }
/*      */ 
/* 1110 */     return getOrientation() == 1 ? getWidth() - getDividerSize() : getHeight() - getDividerSize();
/*      */   }
/*      */ 
/*      */   public boolean isShowGripper()
/*      */   {
/* 1119 */     return this._showGripper;
/*      */   }
/*      */ 
/*      */   public void setShowGripper(boolean showGripper)
/*      */   {
/* 1128 */     boolean oldShowGripper = this._showGripper;
/* 1129 */     if (oldShowGripper != showGripper) {
/* 1130 */       this._showGripper = showGripper;
/* 1131 */       firePropertyChange("gripper", oldShowGripper, this._showGripper);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void doLayout()
/*      */   {
/* 1146 */     if (removeExtraDividers()) {
/* 1147 */       ((JideSplitPaneLayout)getLayout()).invalidateLayout(this);
/*      */     }
/* 1149 */     super.doLayout();
/*      */   }
/*      */ 
/*      */   public boolean isContinuousLayout()
/*      */   {
/* 1158 */     return this._continuousLayout;
/*      */   }
/*      */ 
/*      */   public void setContinuousLayout(boolean continuousLayout)
/*      */   {
/* 1167 */     boolean oldCD = this._continuousLayout;
/*      */ 
/* 1169 */     this._continuousLayout = continuousLayout;
/* 1170 */     firePropertyChange("continuousLayout", oldCD, continuousLayout);
/*      */   }
/*      */ 
/*      */   public AccessibleContext getAccessibleContext()
/*      */   {
/* 1181 */     if (this.accessibleContext == null) {
/* 1182 */       this.accessibleContext = new AccessibleJideSplitPane();
/*      */     }
/* 1184 */     return this.accessibleContext;
/*      */   }
/*      */ 
/*      */   public boolean isDragResizable()
/*      */   {
/* 1195 */     return this._dragResizable;
/*      */   }
/*      */ 
/*      */   public void setDragResizable(boolean dragResizable)
/*      */   {
/* 1204 */     this._dragResizable = dragResizable;
/*      */   }
/*      */ 
/*      */   public boolean isHeavyweightComponentEnabled()
/*      */   {
/* 1250 */     return this._heavyweightComponentEnabled;
/*      */   }
/*      */ 
/*      */   public void setHeavyweightComponentEnabled(boolean heavyweightComponentEnabled)
/*      */   {
/* 1260 */     boolean old = this._heavyweightComponentEnabled;
/* 1261 */     if (this._heavyweightComponentEnabled != heavyweightComponentEnabled) {
/* 1262 */       this._heavyweightComponentEnabled = heavyweightComponentEnabled;
/* 1263 */       firePropertyChange("heavyweightComponentEnabled", old, this._heavyweightComponentEnabled);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setOneTouchExpandable(boolean oneTouchExpandable)
/*      */   {
/* 1326 */     boolean oldValue = this._oneTouchExpandable;
/* 1327 */     if (oldValue != oneTouchExpandable) {
/* 1328 */       this._oneTouchExpandable = oneTouchExpandable;
/*      */ 
/* 1333 */       LayoutManager layoutManager = getLayout();
/* 1334 */       if ((layoutManager instanceof JideBoxLayout)) {
/* 1335 */         ((JideBoxLayout)layoutManager).setResetWhenInvalidate(true);
/*      */       }
/* 1337 */       if (oneTouchExpandable) {
/* 1338 */         setDividerSize(this.oneTouchExpandableDividerSize);
/*      */       }
/*      */       else {
/* 1341 */         setDividerSize(UIDefaultsLookup.getInt("JideSplitPane.dividerSize"));
/*      */       }
/*      */ 
/* 1347 */       firePropertyChange("oneTouchExpandable", oldValue, this._oneTouchExpandable);
/* 1348 */       revalidate();
/* 1349 */       repaint();
/* 1350 */       if ((layoutManager instanceof JideBoxLayout))
/* 1351 */         ((JideBoxLayout)layoutManager).setResetWhenInvalidate(false);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isOneTouchExpandable()
/*      */   {
/* 1364 */     return this._oneTouchExpandable;
/*      */   }
/*      */ 
/*      */   public void setLeftOneTouchButtonImageIcon(ImageIcon leftButtonImageIcon)
/*      */   {
/* 1375 */     this._leftOneTouchButtonImageIcon = leftButtonImageIcon;
/*      */   }
/*      */ 
/*      */   public ImageIcon getLeftOneTouchButtonImageIcon()
/*      */   {
/* 1384 */     return this._leftOneTouchButtonImageIcon;
/*      */   }
/*      */ 
/*      */   public void setRightOneTouchButtonImageIcon(ImageIcon rightButtonImageIcon)
/*      */   {
/* 1395 */     this._rightOneTouchButtonImageIcon = rightButtonImageIcon;
/*      */   }
/*      */ 
/*      */   public ImageIcon getRightOneTouchButtonImageIcon()
/*      */   {
/* 1404 */     return this._rightOneTouchButtonImageIcon;
/*      */   }
/*      */ 
/*      */   public void setDividerLocations(int[] locations)
/*      */   {
/* 1413 */     for (int i = 0; i < locations.length; i++) {
/* 1414 */       int location = locations[i];
/* 1415 */       setDividerLocation(i, location);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int[] getDividerLocations()
/*      */   {
/* 1425 */     int count = getPaneCount();
/* 1426 */     if (getPaneCount() == 0) {
/* 1427 */       return new int[0];
/*      */     }
/* 1429 */     int[] locations = new int[count - 1];
/* 1430 */     for (int i = 0; i < count - 1; i++) {
/* 1431 */       locations[i] = getDividerLocation(i);
/*      */     }
/* 1433 */     return locations;
/*      */   }
/*      */ 
/*      */   private class JideSplitPaneHeavyweightWrapper extends HeavyweightWrapper
/*      */   {
/*      */     public JideSplitPaneHeavyweightWrapper(Component component)
/*      */     {
/* 1448 */       super();
/*      */     }
/*      */   }
/*      */ 
/*      */   private class JideSplitPaneContour extends Contour
/*      */   {
/*      */     public JideSplitPaneContour()
/*      */     {
/*      */     }
/*      */ 
/*      */     public JideSplitPaneContour(int tabHeight)
/*      */     {
/* 1442 */       super();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected class AccessibleJideSplitPane extends JComponent.AccessibleJComponent
/*      */   {
/*      */     private static final long serialVersionUID = -6167624875135108683L;
/*      */ 
/*      */     protected AccessibleJideSplitPane()
/*      */     {
/* 1211 */       super();
/*      */     }
/*      */ 
/*      */     public AccessibleStateSet getAccessibleStateSet()
/*      */     {
/* 1223 */       AccessibleStateSet states = super.getAccessibleStateSet();
/* 1224 */       if (JideSplitPane.this.getOrientation() == 0) {
/* 1225 */         states.add(AccessibleState.VERTICAL);
/*      */       }
/*      */       else {
/* 1228 */         states.add(AccessibleState.HORIZONTAL);
/*      */       }
/* 1230 */       return states;
/*      */     }
/*      */ 
/*      */     public AccessibleRole getAccessibleRole()
/*      */     {
/* 1242 */       return AccessibleRole.SPLIT_PANE;
/*      */     }
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.JideSplitPane
 * JD-Core Version:    0.6.0
 */
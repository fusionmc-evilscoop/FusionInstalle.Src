/*     */ package com.jidesoft.dialog;
/*     */ 
/*     */ import com.jidesoft.plaf.basic.BasicJideOptionPaneUI;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Frame;
/*     */ import java.awt.HeadlessException;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ComponentAdapter;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ public class JideOptionPane extends JOptionPane
/*     */ {
/*     */   private Object _title;
/*     */   private Object _details;
/*     */   public static final String DETAILS_PROPERTY = "details";
/*     */   public static final String TITLE_PROPERTY = "title";
/*     */   public static final int CLOSE_OPTION = 3;
/*     */ 
/*     */   public JideOptionPane()
/*     */   {
/*  43 */     initComponents();
/*     */   }
/*     */ 
/*     */   public JideOptionPane(Object message) {
/*  47 */     super(message);
/*  48 */     initComponents();
/*     */   }
/*     */ 
/*     */   public JideOptionPane(Object message, int messageType) {
/*  52 */     super(message, messageType);
/*  53 */     initComponents();
/*     */   }
/*     */ 
/*     */   public JideOptionPane(Object message, int messageType, int optionType) {
/*  57 */     super(message, messageType, optionType);
/*  58 */     initComponents();
/*     */   }
/*     */ 
/*     */   public JideOptionPane(Object message, int messageType, int optionType, Icon icon) {
/*  62 */     super(message, messageType, optionType, icon);
/*  63 */     initComponents();
/*     */   }
/*     */ 
/*     */   public JideOptionPane(Object message, int messageType, int optionType, Icon icon, Object[] options) {
/*  67 */     super(message, messageType, optionType, icon, options);
/*  68 */     initComponents();
/*     */   }
/*     */ 
/*     */   public JideOptionPane(Object message, int messageType, int optionType, Icon icon, Object[] options, Object initialValue) {
/*  72 */     super(message, messageType, optionType, icon, options, initialValue);
/*  73 */     initComponents();
/*     */   }
/*     */ 
/*     */   protected void initComponents() {
/*  77 */     setOpaque(true);
/*     */   }
/*     */ 
/*     */   public void setOptionType(int newType)
/*     */   {
/*  92 */     if ((newType != -1) && (newType != 0) && (newType != 1) && (newType != 2) && (newType != 3))
/*     */     {
/*  95 */       throw new RuntimeException("JOptionPane: option type must be one of JOptionPane.DEFAULT_OPTION, JOptionPane.YES_NO_OPTION, JOptionPane.YES_NO_CANCEL_OPTION or JOptionPane.OK_CANCEL_OPTION");
/*     */     }
/*  97 */     int oldType = this.optionType;
/*     */ 
/*  99 */     this.optionType = newType;
/* 100 */     firePropertyChange("optionType", oldType, this.optionType);
/*     */   }
/*     */ 
/*     */   public void setDetails(Object details)
/*     */   {
/* 111 */     Object oldDetails = this._details;
/* 112 */     this._details = details;
/* 113 */     firePropertyChange("details", oldDetails, this._details);
/*     */   }
/*     */ 
/*     */   public Object getDetails()
/*     */   {
/* 124 */     return this._details;
/*     */   }
/*     */ 
/*     */   public Object getTitle()
/*     */   {
/* 133 */     return this._title;
/*     */   }
/*     */ 
/*     */   public void setTitle(Object title)
/*     */   {
/* 142 */     Object old = this._title;
/* 143 */     this._title = title;
/* 144 */     firePropertyChange("title", old, this._title);
/*     */   }
/*     */ 
/*     */   public void setDetailsVisible(boolean visible)
/*     */   {
/* 155 */     BasicJideOptionPaneUI.setDetailsVisible(visible);
/* 156 */     updateUI();
/*     */   }
/*     */ 
/*     */   public String getResourceString(String key)
/*     */   {
/* 167 */     return ButtonResources.getResourceBundle(getLocale()).getString(key);
/*     */   }
/*     */ 
/*     */   public boolean isDetailsVisible()
/*     */   {
/* 176 */     return BasicJideOptionPaneUI.isDetailsVisible();
/*     */   }
/*     */ 
/*     */   public static String showInputDialog(Object message)
/*     */     throws HeadlessException
/*     */   {
/* 200 */     return showInputDialog(null, message);
/*     */   }
/*     */ 
/*     */   public static String showInputDialog(Object message, Object initialSelectionValue)
/*     */   {
/* 215 */     return showInputDialog(null, message, initialSelectionValue);
/*     */   }
/*     */ 
/*     */   public static String showInputDialog(Component parentComponent, Object message)
/*     */     throws HeadlessException
/*     */   {
/* 234 */     return showInputDialog(parentComponent, message, UIManager.getString("OptionPane.inputDialogTitle"), 3);
/*     */   }
/*     */ 
/*     */   public static String showInputDialog(Component parentComponent, Object message, Object initialSelectionValue)
/*     */   {
/* 253 */     return (String)showInputDialog(parentComponent, message, UIManager.getString("OptionPane.inputDialogTitle"), 3, null, null, initialSelectionValue);
/*     */   }
/*     */ 
/*     */   public static String showInputDialog(Component parentComponent, Object message, String title, int messageType)
/*     */     throws HeadlessException
/*     */   {
/* 282 */     return (String)showInputDialog(parentComponent, message, title, messageType, null, null, null);
/*     */   }
/*     */ 
/*     */   public static Object showInputDialog(Component parentComponent, Object message, String title, int messageType, Icon icon, Object[] selectionValues, Object initialSelectionValue)
/*     */     throws HeadlessException
/*     */   {
/* 326 */     JideOptionPane pane = new JideOptionPane(message, messageType, 2, icon, null, null);
/*     */ 
/* 330 */     pane.setWantsInput(true);
/* 331 */     pane.setSelectionValues(selectionValues);
/* 332 */     pane.setInitialSelectionValue(initialSelectionValue);
/* 333 */     pane.setComponentOrientation((parentComponent == null ? getRootFrame() : parentComponent).getComponentOrientation());
/*     */ 
/* 336 */     int style = styleFromMessageType(messageType);
/* 337 */     JDialog dialog = pane.createDialog(parentComponent, title, style);
/*     */ 
/* 339 */     pane.selectInitialValue();
/* 340 */     dialog.setVisible(true);
/* 341 */     dialog.dispose();
/*     */ 
/* 343 */     Object value = pane.getInputValue();
/*     */ 
/* 345 */     if (value == UNINITIALIZED_VALUE) {
/* 346 */       return null;
/*     */     }
/* 348 */     return value;
/*     */   }
/*     */ 
/*     */   private static int styleFromMessageType(int messageType) {
/* 352 */     switch (messageType) {
/*     */     case 0:
/* 354 */       return 4;
/*     */     case 3:
/* 356 */       return 7;
/*     */     case 2:
/* 358 */       return 8;
/*     */     case 1:
/* 360 */       return 3;
/*     */     case -1:
/*     */     }
/* 363 */     return 2;
/*     */   }
/*     */ 
/*     */   public static void showMessageDialog(Component parentComponent, Object message)
/*     */     throws HeadlessException
/*     */   {
/* 382 */     showMessageDialog(parentComponent, message, UIManager.getString("OptionPane.messageDialogTitle"), 1);
/*     */   }
/*     */ 
/*     */   public static void showMessageDialog(Component parentComponent, Object message, String title, int messageType)
/*     */     throws HeadlessException
/*     */   {
/* 410 */     showMessageDialog(parentComponent, message, title, messageType, null);
/*     */   }
/*     */ 
/*     */   public static void showMessageDialog(Component parentComponent, Object message, String title, int messageType, Icon icon)
/*     */     throws HeadlessException
/*     */   {
/* 439 */     showOptionDialog(parentComponent, message, title, -1, messageType, icon, null, null);
/*     */   }
/*     */ 
/*     */   public static int showConfirmDialog(Component parentComponent, Object message)
/*     */     throws HeadlessException
/*     */   {
/* 462 */     return showConfirmDialog(parentComponent, message, UIManager.getString("OptionPane.titleText"), 1);
/*     */   }
/*     */ 
/*     */   public static int showConfirmDialog(Component parentComponent, Object message, String title, int optionType)
/*     */     throws HeadlessException
/*     */   {
/* 491 */     return showConfirmDialog(parentComponent, message, title, optionType, 3);
/*     */   }
/*     */ 
/*     */   public static int showConfirmDialog(Component parentComponent, Object message, String title, int optionType, int messageType)
/*     */     throws HeadlessException
/*     */   {
/* 530 */     return showConfirmDialog(parentComponent, message, title, optionType, messageType, null);
/*     */   }
/*     */ 
/*     */   public static int showConfirmDialog(Component parentComponent, Object message, String title, int optionType, int messageType, Icon icon)
/*     */     throws HeadlessException
/*     */   {
/* 568 */     return showOptionDialog(parentComponent, message, title, optionType, messageType, icon, null, null);
/*     */   }
/*     */ 
/*     */   public static int showOptionDialog(Component parentComponent, Object message, String title, int optionType, int messageType, Icon icon, Object[] options, Object initialValue)
/*     */     throws HeadlessException
/*     */   {
/* 630 */     JideOptionPane pane = new JideOptionPane(message, messageType, optionType, icon, options, initialValue);
/*     */ 
/* 634 */     pane.setInitialValue(initialValue);
/* 635 */     pane.setComponentOrientation((parentComponent == null ? getRootFrame() : parentComponent).getComponentOrientation());
/*     */ 
/* 638 */     int style = styleFromMessageType(messageType);
/* 639 */     JDialog dialog = pane.createDialog(parentComponent, title, style);
/*     */ 
/* 641 */     pane.selectInitialValue();
/* 642 */     dialog.setVisible(true);
/* 643 */     dialog.dispose();
/*     */ 
/* 645 */     Object selectedValue = pane.getValue();
/*     */ 
/* 647 */     if (selectedValue == null)
/* 648 */       return -1;
/* 649 */     if (options == null) {
/* 650 */       if ((selectedValue instanceof Integer))
/* 651 */         return ((Integer)selectedValue).intValue();
/* 652 */       return -1;
/*     */     }
/* 654 */     int counter = 0; int maxCounter = options.length;
/* 655 */     for (; counter < maxCounter; counter++) {
/* 656 */       if (options[counter].equals(selectedValue))
/* 657 */         return counter;
/*     */     }
/* 659 */     return -1;
/*     */   }
/*     */ 
/*     */   public JDialog createDialog(Component parentComponent, String title)
/*     */     throws HeadlessException
/*     */   {
/* 690 */     int style = styleFromMessageType(getMessageType());
/* 691 */     return createDialog(parentComponent, title, style);
/*     */   }
/*     */ 
/*     */   public JDialog createDialog(String title)
/*     */     throws HeadlessException
/*     */   {
/* 717 */     int style = styleFromMessageType(getMessageType());
/* 718 */     JDialog dialog = new JDialog((Dialog)null, title, true);
/* 719 */     initDialog(dialog, style, null);
/* 720 */     return dialog;
/*     */   }
/*     */ 
/*     */   private JDialog createDialog(Component parentComponent, String title, int style)
/*     */     throws HeadlessException
/*     */   {
/* 729 */     Window window = getWindowForComponent(parentComponent);
/*     */     JDialog dialog;
/*     */     JDialog dialog;
/* 730 */     if ((window instanceof Frame)) {
/* 731 */       dialog = new JDialog((Frame)window, title, true);
/*     */     }
/*     */     else {
/* 734 */       dialog = new JDialog((Dialog)window, title, true);
/*     */     }
/*     */ 
/* 741 */     initDialog(dialog, style, parentComponent);
/* 742 */     return dialog;
/*     */   }
/*     */ 
/*     */   private void initDialog(JDialog dialog, int style, Component parentComponent) {
/* 746 */     dialog.setComponentOrientation(getComponentOrientation());
/* 747 */     Container contentPane = dialog.getContentPane();
/*     */ 
/* 749 */     contentPane.setLayout(new BorderLayout());
/* 750 */     contentPane.add(this, "Center");
/* 751 */     dialog.setResizable(false);
/* 752 */     if (JDialog.isDefaultLookAndFeelDecorated()) {
/* 753 */       boolean supportsWindowDecorations = UIManager.getLookAndFeel().getSupportsWindowDecorations();
/*     */ 
/* 755 */       if (supportsWindowDecorations) {
/* 756 */         dialog.setUndecorated(true);
/* 757 */         getRootPane().setWindowDecorationStyle(style);
/*     */       }
/*     */     }
/* 760 */     dialog.pack();
/* 761 */     dialog.setLocationRelativeTo(parentComponent);
/* 762 */     WindowAdapter adapter = new WindowAdapter() {
/* 763 */       private boolean gotFocus = false;
/*     */ 
/*     */       public void windowClosing(WindowEvent we) {
/* 766 */         JideOptionPane.this.setValue(null);
/*     */       }
/*     */ 
/*     */       public void windowGainedFocus(WindowEvent we)
/*     */       {
/* 771 */         if (!this.gotFocus) {
/* 772 */           JideOptionPane.this.selectInitialValue();
/* 773 */           this.gotFocus = true;
/*     */         }
/*     */       }
/*     */     };
/* 777 */     dialog.addWindowListener(adapter);
/* 778 */     dialog.addWindowFocusListener(adapter);
/* 779 */     dialog.addComponentListener(new ComponentAdapter()
/*     */     {
/*     */       public void componentShown(ComponentEvent ce) {
/* 782 */         JideOptionPane.this.setValue(JOptionPane.UNINITIALIZED_VALUE);
/*     */       }
/*     */     });
/* 785 */     addPropertyChangeListener(new PropertyChangeListener(dialog)
/*     */     {
/*     */       public void propertyChange(PropertyChangeEvent event)
/*     */       {
/* 790 */         if ((this.val$dialog.isVisible()) && (event.getSource() == JideOptionPane.this) && (event.getPropertyName().equals("value")) && (event.getNewValue() != null) && (event.getNewValue() != JOptionPane.UNINITIALIZED_VALUE))
/*     */         {
/* 794 */           this.val$dialog.setVisible(false);
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static Frame getFrameForComponent(Component parentComponent)
/*     */     throws HeadlessException
/*     */   {
/* 817 */     if (parentComponent == null)
/* 818 */       return getRootFrame();
/* 819 */     if ((parentComponent instanceof Frame))
/* 820 */       return (Frame)parentComponent;
/* 821 */     return JOptionPane.getFrameForComponent(parentComponent.getParent());
/*     */   }
/*     */ 
/*     */   static Window getWindowForComponent(Component parentComponent)
/*     */     throws HeadlessException
/*     */   {
/* 842 */     if (parentComponent == null)
/* 843 */       return getRootFrame();
/* 844 */     if (((parentComponent instanceof Frame)) || ((parentComponent instanceof Dialog)))
/* 845 */       return (Window)parentComponent;
/* 846 */     return getWindowForComponent(parentComponent.getParent());
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.dialog.JideOptionPane
 * JD-Core Version:    0.6.0
 */
/*     */ package com.jidesoft.dialog;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.swing.DelegateAction;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.HeadlessException;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.MenuElement;
/*     */ import javax.swing.MenuSelectionManager;
/*     */ 
/*     */ public abstract class StandardDialog extends JDialog
/*     */   implements ButtonNames
/*     */ {
/*  34 */   private boolean _lazyConstructorCalled = false;
/*     */   protected StandardDialogPane _standardDialogPane;
/*     */   public static final int RESULT_CANCELLED = -1;
/*     */   public static final int RESULT_AFFIRMED = 0;
/*  50 */   private int _dialogResult = -1;
/*     */   public StandardDialogPropertyChangeListener _propertyChangeListener;
/*     */ 
/*     */   public StandardDialog()
/*     */     throws HeadlessException
/*     */   {
/*  54 */     this(null);
/*     */   }
/*     */ 
/*     */   public StandardDialog(Frame owner) throws HeadlessException {
/*  58 */     this(owner, true);
/*     */   }
/*     */ 
/*     */   public StandardDialog(Frame owner, boolean modal) throws HeadlessException {
/*  62 */     this(owner, null, modal);
/*     */   }
/*     */ 
/*     */   public StandardDialog(Frame owner, String title) throws HeadlessException {
/*  66 */     this(owner, title, true);
/*     */   }
/*     */ 
/*     */   public StandardDialog(Frame owner, String title, boolean modal) throws HeadlessException {
/*  70 */     super(owner, title, modal);
/*  71 */     initDialog();
/*     */   }
/*     */ 
/*     */   public StandardDialog(Dialog owner, boolean modal) throws HeadlessException {
/*  75 */     this(owner, null, modal);
/*     */   }
/*     */ 
/*     */   public StandardDialog(Dialog owner, String title) throws HeadlessException {
/*  79 */     this(owner, title, true);
/*     */   }
/*     */ 
/*     */   public StandardDialog(Dialog owner, String title, boolean modal) throws HeadlessException {
/*  83 */     super(owner, title, modal);
/*  84 */     initDialog();
/*     */   }
/*     */ 
/*     */   public StandardDialog(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) throws HeadlessException {
/*  88 */     super(owner, title, modal, gc);
/*  89 */     initDialog();
/*     */   }
/*     */ 
/*     */   private void initDialog()
/*     */   {
/* 115 */     this._standardDialogPane = createStandardDialogPane();
/* 116 */     this._propertyChangeListener = new StandardDialogPropertyChangeListener();
/* 117 */     this._standardDialogPane.addPropertyChangeListener(this._propertyChangeListener);
/* 118 */     setDefaultCloseOperation(2);
/*     */   }
/*     */ 
/*     */   public int getDialogResult()
/*     */   {
/* 127 */     return this._dialogResult;
/*     */   }
/*     */ 
/*     */   public void setDialogResult(int dialogResult)
/*     */   {
/* 136 */     this._dialogResult = dialogResult;
/*     */   }
/*     */ 
/*     */   public Action getDefaultCancelAction()
/*     */   {
/* 145 */     return this._standardDialogPane.getDefaultCancelAction();
/*     */   }
/*     */ 
/*     */   public void setDefaultCancelAction(Action defaultCancelAction)
/*     */   {
/* 154 */     this._standardDialogPane.setDefaultCancelAction(defaultCancelAction);
/*     */   }
/*     */ 
/*     */   public Action getDefaultAction()
/*     */   {
/* 163 */     return this._standardDialogPane.getDefaultAction();
/*     */   }
/*     */ 
/*     */   public void setDefaultAction(Action defaultAction)
/*     */   {
/* 172 */     this._standardDialogPane.setDefaultAction(defaultAction);
/*     */   }
/*     */ 
/*     */   public void pack()
/*     */   {
/*     */     try {
/* 178 */       initialize();
/*     */     }
/*     */     catch (Exception e) {
/* 181 */       JideSwingUtilities.throwException(e);
/*     */     }
/* 183 */     super.pack();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void show()
/*     */   {
/*     */     try
/*     */     {
/* 194 */       initialize();
/*     */     }
/*     */     catch (Exception e) {
/* 197 */       JideSwingUtilities.throwException(e);
/*     */     }
/* 199 */     super.show();
/*     */   }
/*     */ 
/*     */   public final synchronized void initialize()
/*     */   {
/* 207 */     if ((!this._lazyConstructorCalled) && (getParent() != null)) {
/* 208 */       initComponents();
/* 209 */       this._lazyConstructorCalled = true;
/* 210 */       validate();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void initComponents()
/*     */   {
/* 221 */     getContentPane().setLayout(new BorderLayout());
/* 222 */     this._standardDialogPane.initComponents();
/* 223 */     getContentPane().add(this._standardDialogPane);
/*     */ 
/* 225 */     if (getInitFocusedComponent() != null)
/* 226 */       addWindowListener(new WindowAdapter()
/*     */       {
/*     */         public void windowActivated(WindowEvent e) {
/* 229 */           StandardDialog.this.getInitFocusedComponent().requestFocus();
/*     */         }
/*     */       });
/*     */   }
/*     */ 
/*     */   public Component getInitFocusedComponent()
/*     */   {
/* 242 */     return this._standardDialogPane.getInitFocusedComponent();
/*     */   }
/*     */ 
/*     */   public void setInitFocusedComponent(Component initFocusedComponent)
/*     */   {
/* 251 */     this._standardDialogPane.setInitFocusedComponent(initFocusedComponent);
/*     */   }
/*     */ 
/*     */   public JComponent getBannerPanel()
/*     */   {
/* 260 */     return this._standardDialogPane.getBannerPanel();
/*     */   }
/*     */ 
/*     */   public JComponent getContentPanel()
/*     */   {
/* 269 */     return this._standardDialogPane.getContentPanel();
/*     */   }
/*     */ 
/*     */   public ButtonPanel getButtonPanel()
/*     */   {
/* 278 */     return this._standardDialogPane.getButtonPanel();
/*     */   }
/*     */ 
/*     */   public StandardDialogPane getStandardDialogPane() {
/* 282 */     return this._standardDialogPane;
/*     */   }
/*     */ 
/*     */   public abstract JComponent createBannerPanel();
/*     */ 
/*     */   public abstract JComponent createContentPanel();
/*     */ 
/*     */   public abstract ButtonPanel createButtonPanel();
/*     */ 
/*     */   public ButtonPanel createOKCancelButtonPanel()
/*     */   {
/* 321 */     ButtonPanel buttonPanel = new ButtonPanel();
/* 322 */     buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 6, 10));
/*     */ 
/* 324 */     AbstractAction okAction = new AbstractAction(UIDefaultsLookup.getString("OptionPane.okButtonText")) {
/*     */       private static final long serialVersionUID = -326622280892936635L;
/*     */ 
/* 328 */       public void actionPerformed(ActionEvent e) { StandardDialog.this.setDialogResult(0);
/* 329 */         StandardDialog.this.setVisible(false);
/*     */       }
/*     */     };
/* 332 */     AbstractAction cancelAction = new AbstractAction(UIDefaultsLookup.getString("OptionPane.cancelButtonText")) {
/*     */       private static final long serialVersionUID = 7131352846873132805L;
/*     */ 
/* 336 */       public void actionPerformed(ActionEvent e) { StandardDialog.this.setDialogResult(-1);
/* 337 */         StandardDialog.this.setVisible(false);
/*     */       }
/*     */     };
/* 340 */     JButton okButton = new JButton(okAction);
/* 341 */     buttonPanel.addButton(okButton, "AFFIRMATIVE");
/* 342 */     buttonPanel.addButton(new JButton(cancelAction), "CANCEL");
/*     */ 
/* 344 */     setDefaultCancelAction(cancelAction);
/* 345 */     setDefaultAction(okAction);
/* 346 */     getRootPane().setDefaultButton(okButton);
/*     */ 
/* 348 */     return buttonPanel;
/*     */   }
/*     */ 
/*     */   protected StandardDialogPane createStandardDialogPane() {
/* 352 */     return new DefaultStandardDialogPane();
/*     */   }
/*     */ 
/*     */   protected class DefaultStandardDialogPane extends StandardDialogPane
/*     */   {
/*     */     protected DefaultStandardDialogPane()
/*     */     {
/*     */     }
/*     */ 
/*     */     public JComponent createBannerPanel()
/*     */     {
/* 394 */       return StandardDialog.this.createBannerPanel();
/*     */     }
/*     */ 
/*     */     public JComponent createContentPanel()
/*     */     {
/* 399 */       return StandardDialog.this.createContentPanel();
/*     */     }
/*     */ 
/*     */     public ButtonPanel createButtonPanel()
/*     */     {
/* 404 */       return StandardDialog.this.createButtonPanel();
/*     */     }
/*     */   }
/*     */ 
/*     */   class StandardDialogPropertyChangeListener
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     StandardDialogPropertyChangeListener()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void propertyChange(PropertyChangeEvent evt)
/*     */     {
/* 357 */       if ("defaultCancelAction".equals(evt.getPropertyName())) {
/* 358 */         DelegateAction delegateAction = new DelegateAction(StandardDialog.this.getDefaultCancelAction()) {
/*     */           private static final long serialVersionUID = -2136676357204671812L;
/*     */ 
/*     */           public boolean delegateActionPerformed(ActionEvent e) {
/* 363 */             if (hasSelectionPath()) {
/* 364 */               MenuSelectionManager.defaultManager().clearSelectedPath();
/* 365 */               return true;
/*     */             }
/* 367 */             return false;
/*     */           }
/*     */ 
/*     */           public boolean isDelegateEnabled()
/*     */           {
/* 372 */             return hasSelectionPath();
/*     */           }
/*     */ 
/*     */           private boolean hasSelectionPath() {
/* 376 */             MenuElement[] selectedPath = MenuSelectionManager.defaultManager().getSelectedPath();
/* 377 */             return (selectedPath != null) && (selectedPath.length > 0);
/*     */           }
/*     */         };
/* 380 */         DelegateAction.replaceAction(StandardDialog.this.getRootPane(), 2, KeyStroke.getKeyStroke(27, 0), delegateAction, false);
/*     */       }
/* 382 */       else if ("defaultAction".equals(evt.getPropertyName())) {
/* 383 */         StandardDialog.this.getRootPane().unregisterKeyboardAction(KeyStroke.getKeyStroke(10, 0));
/* 384 */         StandardDialog.this.getRootPane().registerKeyboardAction(StandardDialog.this.getDefaultAction(), KeyStroke.getKeyStroke(10, 0), 1);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.dialog.StandardDialog
 * JD-Core Version:    0.6.0
 */
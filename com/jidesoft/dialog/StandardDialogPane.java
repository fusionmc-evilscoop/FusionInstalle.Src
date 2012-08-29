/*     */ package com.jidesoft.dialog;
/*     */ 
/*     */ import com.jidesoft.swing.DelegateAction;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.HeadlessException;
/*     */ import java.awt.event.ActionEvent;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.MenuElement;
/*     */ import javax.swing.MenuSelectionManager;
/*     */ 
/*     */ public abstract class StandardDialogPane extends JPanel
/*     */   implements ButtonNames
/*     */ {
/*     */   protected JComponent _bannerPanel;
/*     */   protected JComponent _contentPanel;
/*     */   protected ButtonPanel _buttonPanel;
/*     */   private Action _defaultCancelAction;
/*     */   private Action _defaultAction;
/*     */   private Component _initFocusedComponent;
/*     */   public static final String PROPERTY_CANCEL_ACTION = "defaultCancelAction";
/*     */   public static final String PROPERTY_DEFAULT_ACTION = "defaultAction";
/*     */ 
/*     */   public StandardDialogPane()
/*     */     throws HeadlessException
/*     */   {
/*  59 */     setOpaque(false);
/*     */   }
/*     */ 
/*     */   public Action getDefaultCancelAction()
/*     */   {
/*  68 */     return this._defaultCancelAction;
/*     */   }
/*     */ 
/*     */   public void setDefaultCancelAction(Action defaultCancelAction)
/*     */   {
/*  77 */     Action oldAction = this._defaultCancelAction;
/*  78 */     this._defaultCancelAction = defaultCancelAction;
/*  79 */     firePropertyChange("defaultCancelAction", oldAction, this._defaultCancelAction);
/*     */   }
/*     */ 
/*     */   public Action getDefaultAction()
/*     */   {
/*  88 */     return this._defaultAction;
/*     */   }
/*     */ 
/*     */   public void setDefaultAction(Action defaultAction)
/*     */   {
/*  97 */     Action oldAction = this._defaultAction;
/*  98 */     this._defaultAction = defaultAction;
/*  99 */     firePropertyChange("defaultAction", oldAction, this._defaultAction);
/*     */   }
/*     */ 
/*     */   public void initComponents()
/*     */   {
/* 109 */     this._buttonPanel = createButtonPanel();
/* 110 */     this._bannerPanel = createBannerPanel();
/* 111 */     this._contentPanel = createContentPanel();
/* 112 */     layoutComponents(this._bannerPanel, this._contentPanel, this._buttonPanel);
/* 113 */     if (getRootPane() != null) {
/* 114 */       if (getRootPane().getDefaultButton() != null) {
/* 115 */         getRootPane().getDefaultButton().requestFocus();
/*     */       }
/*     */ 
/* 118 */       if (getDefaultCancelAction() != null) {
/* 119 */         getRootPane().registerKeyboardAction(new DelegateAction(getDefaultCancelAction()) {
/*     */           private static final long serialVersionUID = 7321038745798788445L;
/*     */ 
/*     */           public boolean delegateActionPerformed(ActionEvent e) {
/* 124 */             if (hasSelectionPath()) {
/* 125 */               MenuSelectionManager.defaultManager().clearSelectedPath();
/* 126 */               return true;
/*     */             }
/* 128 */             return false;
/*     */           }
/*     */ 
/*     */           public boolean isDelegateEnabled()
/*     */           {
/* 133 */             return hasSelectionPath();
/*     */           }
/*     */ 
/*     */           private boolean hasSelectionPath() {
/* 137 */             MenuElement[] selectedPath = MenuSelectionManager.defaultManager().getSelectedPath();
/* 138 */             return (selectedPath != null) && (selectedPath.length > 0);
/*     */           }
/*     */         }
/*     */         , KeyStroke.getKeyStroke(27, 0), 2);
/*     */       }
/*     */ 
/* 142 */       if (getDefaultAction() != null)
/* 143 */         getRootPane().registerKeyboardAction(getDefaultAction(), KeyStroke.getKeyStroke(10, 0), 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void layoutComponents(Component bannerPanel, Component contentPanel, ButtonPanel buttonPanel)
/*     */   {
/* 160 */     setLayout(new BorderLayout());
/* 161 */     if (bannerPanel != null) {
/* 162 */       add(bannerPanel, "First");
/*     */     }
/* 164 */     if (contentPanel != null) {
/* 165 */       add(contentPanel, "Center");
/*     */     }
/* 167 */     if (buttonPanel != null)
/* 168 */       if ((buttonPanel.getAlignment() == 1) || (buttonPanel.getAlignment() == 3))
/*     */       {
/* 170 */         add(buttonPanel, "After");
/*     */       }
/*     */       else
/* 173 */         add(buttonPanel, "Last");
/*     */   }
/*     */ 
/*     */   public Component getInitFocusedComponent()
/*     */   {
/* 184 */     return this._initFocusedComponent;
/*     */   }
/*     */ 
/*     */   public void setInitFocusedComponent(Component initFocusedComponent)
/*     */   {
/* 193 */     this._initFocusedComponent = initFocusedComponent;
/*     */   }
/*     */ 
/*     */   public JComponent getBannerPanel()
/*     */   {
/* 202 */     return this._bannerPanel;
/*     */   }
/*     */ 
/*     */   public JComponent getContentPanel()
/*     */   {
/* 211 */     return this._contentPanel;
/*     */   }
/*     */ 
/*     */   public ButtonPanel getButtonPanel()
/*     */   {
/* 220 */     return this._buttonPanel;
/*     */   }
/*     */ 
/*     */   public abstract JComponent createBannerPanel();
/*     */ 
/*     */   public abstract JComponent createContentPanel();
/*     */ 
/*     */   public abstract ButtonPanel createButtonPanel();
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.dialog.StandardDialogPane
 * JD-Core Version:    0.6.0
 */
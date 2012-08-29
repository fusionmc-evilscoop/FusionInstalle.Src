/*     */ package com.jidesoft.dialog;
/*     */ 
/*     */ import com.jidesoft.plaf.LookAndFeelFactory;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.swing.ArrowKeyNavigationSupport;
/*     */ import java.awt.Component;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.SwingUtilities;
/*     */ 
/*     */ public class ButtonPanel extends JPanel
/*     */   implements ButtonListener, ButtonNames
/*     */ {
/*     */   public static final int SAME_SIZE = 0;
/*     */   public static final int NO_LESS_THAN = 1;
/*     */   public static final String KEEP_PREFERRED_WIDTH = "keepPreferredWidth";
/*     */   public static final String AFFIRMATIVE_BUTTON = "AFFIRMATIVE";
/*     */   public static final String CANCEL_BUTTON = "CANCEL";
/*     */   public static final String HELP_BUTTON = "HELP";
/*     */   public static final String OTHER_BUTTON = "ALTERNATIVE";
/*  68 */   private String _defaultOrder = UIDefaultsLookup.getString("ButtonPanel.order");
/*     */ 
/*  70 */   private String _defaultOppositeOrder = UIDefaultsLookup.getString("ButtonPanel.oppositeOrder");
/*     */ 
/*  72 */   private int _defaultButtonGap = UIDefaultsLookup.getInt("ButtonPanel.buttonGap");
/*     */ 
/*  74 */   private int _defaultGroupGap = UIDefaultsLookup.getInt("ButtonPanel.groupGap");
/*     */ 
/*  76 */   private int _defaultButtonWidth = UIDefaultsLookup.getInt("ButtonPanel.minButtonWidth");
/*     */   private int _alignment;
/*     */   private ButtonPanelLayout _layout;
/* 445 */   private boolean _addNotify = false;
/*     */   private JButton _defaultButton;
/*     */ 
/*     */   public ButtonPanel()
/*     */   {
/*  86 */     this(11);
/*     */   }
/*     */ 
/*     */   public ButtonPanel(int alignment)
/*     */   {
/*  97 */     this(alignment, 0);
/*     */   }
/*     */ 
/*     */   public ButtonPanel(int alignment, int sizeContraint)
/*     */   {
/* 111 */     this._alignment = alignment;
/*     */ 
/* 113 */     if ((alignment != 2) && (alignment != 4) && (alignment != 10) && (alignment != 11) && (alignment != 1) && (alignment != 3) && (alignment != 0))
/*     */     {
/* 115 */       throw new IllegalArgumentException("Invalid alignment");
/*     */     }
/*     */ 
/* 118 */     int axis = (this._alignment == 0) || (this._alignment == 2) || (this._alignment == 4) || (this._alignment == 11) || (this._alignment == 10) ? 0 : 1;
/*     */ 
/* 121 */     this._layout = new ButtonPanelLayout(this, axis, this._alignment, sizeContraint, this._defaultOrder, this._defaultOppositeOrder, this._defaultButtonGap, this._defaultGroupGap);
/* 122 */     setLayout(this._layout);
/*     */ 
/* 124 */     new ArrowKeyNavigationSupport(new Class[] { AbstractButton.class }).install(this);
/*     */   }
/*     */ 
/*     */   public void updateUI()
/*     */   {
/* 129 */     if ((UIDefaultsLookup.get("ButtonPanel.buttonGap") == null) && (UIDefaultsLookup.get("ButtonPanel.order") == null) && (UIDefaultsLookup.get("ButtonPanel.groupGap") == null))
/*     */     {
/* 132 */       LookAndFeelFactory.installJideExtension();
/*     */     }
/* 134 */     super.updateUI();
/* 135 */     reinstallDefaults();
/*     */   }
/*     */ 
/*     */   protected void reinstallDefaults() {
/* 139 */     if (this._layout != null) {
/* 140 */       if (this._defaultButtonGap == this._layout.getButtonGap()) {
/* 141 */         this._defaultButtonGap = UIDefaultsLookup.getInt("ButtonPanel.buttonGap");
/* 142 */         this._layout.setButtonGap(this._defaultButtonGap);
/*     */       }
/* 144 */       if (this._defaultGroupGap == this._layout.getGroupGap()) {
/* 145 */         this._defaultGroupGap = UIDefaultsLookup.getInt("ButtonPanel.groupGap");
/* 146 */         this._layout.setGroupGap(this._defaultGroupGap);
/*     */       }
/* 148 */       if (this._defaultOrder.equals(this._layout.getButtonOrder())) {
/* 149 */         this._defaultOrder = UIDefaultsLookup.getString("ButtonPanel.order");
/* 150 */         this._layout.setButtonOrder(this._defaultOrder);
/*     */       }
/* 152 */       if (this._defaultOppositeOrder.equals(this._layout.getOppositeButtonOrder())) {
/* 153 */         this._defaultOppositeOrder = UIDefaultsLookup.getString("ButtonPanel.oppositeOrder");
/* 154 */         this._layout.setOppositeButtonOrder(this._defaultOppositeOrder);
/*     */       }
/* 156 */       if (this._defaultButtonWidth == this._layout.getMinButtonWidth()) {
/* 157 */         this._defaultButtonWidth = UIDefaultsLookup.getInt("ButtonPanel.minButtonWidth");
/* 158 */         this._layout.setMinButtonWidth(this._defaultButtonWidth);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setAlignment(int alignment)
/*     */   {
/* 173 */     this._alignment = alignment;
/* 174 */     int axis = (this._alignment == 0) || (this._alignment == 2) || (this._alignment == 4) || (this._alignment == 10) || (this._alignment == 11) ? 0 : 1;
/*     */ 
/* 176 */     this._layout.setAlignment(this._alignment);
/* 177 */     this._layout.setAxis(axis);
/* 178 */     this._layout.layoutContainer(this);
/*     */   }
/*     */ 
/*     */   public int getAlignment()
/*     */   {
/* 187 */     return this._alignment;
/*     */   }
/*     */ 
/*     */   public void addButton(AbstractButton button)
/*     */   {
/* 196 */     addButton(button, "AFFIRMATIVE");
/*     */   }
/*     */ 
/*     */   public void addButton(AbstractButton button, int index)
/*     */   {
/* 207 */     addButton(button, "AFFIRMATIVE", index);
/*     */   }
/*     */ 
/*     */   public void addButton(AbstractButton button, Object constraint)
/*     */   {
/* 221 */     addButton(button, constraint, -1);
/*     */   }
/*     */ 
/*     */   public void addButton(AbstractButton button, Object constraint, int index)
/*     */   {
/* 233 */     add(button, constraint, index);
/*     */   }
/*     */ 
/*     */   protected void addImpl(Component comp, Object constraints, int index)
/*     */   {
/* 239 */     if (constraints == null) {
/* 240 */       constraints = "AFFIRMATIVE";
/*     */     }
/* 242 */     super.addImpl(comp, constraints, index);
/*     */   }
/*     */ 
/*     */   public void removeButton(AbstractButton button)
/*     */   {
/* 251 */     remove(button);
/*     */   }
/*     */ 
/*     */   public String getButtonOrder()
/*     */   {
/* 260 */     return this._layout.getButtonOrder();
/*     */   }
/*     */ 
/*     */   public void setButtonOrder(String buttonOrder)
/*     */   {
/* 269 */     this._layout.setButtonOrder(buttonOrder);
/*     */   }
/*     */ 
/*     */   public String getOppositeButtonOrder()
/*     */   {
/* 278 */     return this._layout.getOppositeButtonOrder();
/*     */   }
/*     */ 
/*     */   public void setOppositeButtonOrder(String oppositeButtonOrder)
/*     */   {
/* 287 */     this._layout.setOppositeButtonOrder(oppositeButtonOrder);
/*     */   }
/*     */ 
/*     */   public int getSizeConstraint()
/*     */   {
/* 296 */     return this._layout.getSizeConstraint();
/*     */   }
/*     */ 
/*     */   public void setSizeConstraint(int sizeContraint)
/*     */   {
/* 307 */     this._layout.setSizeConstraint(sizeContraint);
/*     */   }
/*     */ 
/*     */   public int getGroupGap()
/*     */   {
/* 316 */     return this._layout.getGroupGap();
/*     */   }
/*     */ 
/*     */   public void setGroupGap(int groupGap)
/*     */   {
/* 325 */     this._layout.setGroupGap(groupGap);
/*     */   }
/*     */ 
/*     */   public int getButtonGap()
/*     */   {
/* 334 */     return this._layout.getButtonGap();
/*     */   }
/*     */ 
/*     */   public void setButtonGap(int buttonGap)
/*     */   {
/* 343 */     this._layout.setButtonGap(buttonGap);
/*     */   }
/*     */ 
/*     */   public int getMinButtonWidth()
/*     */   {
/* 352 */     return this._layout.getMinButtonWidth();
/*     */   }
/*     */ 
/*     */   public void setMinButtonWidth(int minButtonWidth)
/*     */   {
/* 361 */     this._layout.setMinButtonWidth(minButtonWidth);
/*     */   }
/*     */ 
/*     */   public void buttonEventFired(ButtonEvent e) {
/* 365 */     if (e.getID() == 3308) {
/* 366 */       JRootPane rootPane = getRootPane();
/* 367 */       if ((rootPane != null) && (rootPane.getDefaultButton() != null))
/* 368 */         rootPane.setDefaultButton(null);
/*     */     }
/*     */     else
/*     */     {
/* 372 */       for (int i = 0; i < getComponentCount(); i++) {
/* 373 */         Component component = getComponent(i);
/* 374 */         if (e.getButtonName().equals(component.getName())) {
/* 375 */           switch (e.getID()) {
/*     */           case 3301:
/* 377 */             component.setVisible(true);
/* 378 */             if (((component instanceof JButton)) && (((JButton)component).getAction() != null)) {
/* 379 */               ((JButton)component).getAction().setEnabled(true);
/*     */             }
/* 381 */             component.setEnabled(true);
/* 382 */             break;
/*     */           case 3302:
/* 384 */             component.setEnabled(false);
/* 385 */             if (((component instanceof JButton)) && (((JButton)component).getAction() != null)) {
/* 386 */               ((JButton)component).getAction().setEnabled(false);
/*     */             }
/* 388 */             component.setVisible(true);
/* 389 */             break;
/*     */           case 3299:
/* 391 */             component.setVisible(true);
/* 392 */             break;
/*     */           case 3300:
/* 394 */             component.setVisible(false);
/* 395 */             JRootPane rootPane = getRootPane();
/* 396 */             if ((rootPane != null) && (rootPane.getDefaultButton() == component)) {
/* 397 */               rootPane.setDefaultButton(null);
/*     */             }
/*     */ 
/* 400 */             if ((rootPane == null) || (rootPane.getClientProperty("initialDefaultButton") != component)) break;
/* 401 */             rootPane.putClientProperty("initialDefaultButton", null); break;
/*     */           case 3303:
/* 405 */             if (!(component instanceof AbstractButton)) break;
/* 406 */             ((AbstractButton)component).setText(e.getUserObject()); break;
/*     */           case 3304:
/* 410 */             if (!(component instanceof AbstractButton)) break;
/* 411 */             ((AbstractButton)component).setMnemonic(e.getUserObject().charAt(0)); break;
/*     */           case 3305:
/* 415 */             if (!(component instanceof AbstractButton)) break;
/* 416 */             ((AbstractButton)component).setToolTipText(e.getUserObject()); break;
/*     */           case 3306:
/* 420 */             Runnable runnable = new Runnable(component) {
/*     */               public void run() {
/* 422 */                 this.val$component.requestFocus();
/*     */               }
/*     */             };
/* 425 */             SwingUtilities.invokeLater(runnable);
/* 426 */             break;
/*     */           case 3307:
/* 428 */             if (!(component instanceof JButton)) break;
/* 429 */             if (getRootPane() != null) {
/* 430 */               getRootPane().setDefaultButton((JButton)component);
/*     */             }
/*     */             else {
/* 433 */               this._defaultButton = ((JButton)component);
/* 434 */               this._addNotify = true;
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 439 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addNotify()
/*     */   {
/* 450 */     super.addNotify();
/* 451 */     if (this._addNotify) {
/* 452 */       JRootPane pane = getRootPane();
/* 453 */       if ((this._defaultButton != null) && (pane != null)) {
/* 454 */         pane.setDefaultButton(this._defaultButton);
/* 455 */         this._addNotify = false;
/* 456 */         this._defaultButton = null;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Component getButtonByName(String name)
/*     */   {
/* 472 */     if ((name == null) || (name.trim().length() == 0)) {
/* 473 */       throw new IllegalArgumentException("name cannot be null or empty");
/*     */     }
/* 475 */     for (int i = 0; i < getComponentCount(); i++) {
/* 476 */       Component component = getComponent(i);
/* 477 */       if (name.equals(component.getName())) {
/* 478 */         return component;
/*     */       }
/*     */     }
/* 481 */     return null;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.dialog.ButtonPanel
 * JD-Core Version:    0.6.0
 */
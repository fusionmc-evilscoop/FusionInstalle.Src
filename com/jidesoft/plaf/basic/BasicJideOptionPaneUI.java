/*     */ package com.jidesoft.plaf.basic;
/*     */ 
/*     */ import com.jidesoft.dialog.ButtonPanel;
/*     */ import com.jidesoft.dialog.ButtonResources;
/*     */ import com.jidesoft.dialog.JideOptionPane;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.swing.JideBoxLayout;
/*     */ import com.jidesoft.swing.NullPanel;
/*     */ import com.jidesoft.swing.PaintPanel;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JSeparator;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.event.AncestorEvent;
/*     */ import javax.swing.event.AncestorListener;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicOptionPaneUI;
/*     */ 
/*     */ public class BasicJideOptionPaneUI extends BasicOptionPaneUI
/*     */ {
/*     */   private Container _detailsArea;
/*     */   private Container _buttonArea;
/*     */   private Container _bannerArea;
/*     */   private Component _titleComponent;
/*     */   private ThemePainter _painter;
/*     */   private int _detailsPreferredWidth;
/*  58 */   private static boolean _detailsVisible = false;
/*     */ 
/*     */   public BasicJideOptionPaneUI()
/*     */   {
/*  43 */     this._detailsPreferredWidth = -1;
/*     */   }
/*     */ 
/*     */   public static ComponentUI createUI(JComponent x)
/*     */   {
/*  49 */     return new BasicJideOptionPaneUI();
/*     */   }
/*     */ 
/*     */   protected LayoutManager createLayoutManager()
/*     */   {
/*  54 */     return new JideBoxLayout(this.optionPane, 1);
/*     */   }
/*     */ 
/*     */   public static boolean isDetailsVisible()
/*     */   {
/*  66 */     return _detailsVisible;
/*     */   }
/*     */ 
/*     */   public static void setDetailsVisible(boolean detailsVisible)
/*     */   {
/*  75 */     _detailsVisible = detailsVisible;
/*     */   }
/*     */ 
/*     */   protected void installComponents()
/*     */   {
/*  80 */     boolean showBanner = (UIDefaultsLookup.get("OptionPane.showBanner") == null) || (UIDefaultsLookup.getBoolean("OptionPane.showBanner"));
/*  81 */     if (showBanner) {
/*  82 */       this.optionPane.add(this._bannerArea = createBannerArea(), "fix");
/*     */     }
/*     */ 
/*  85 */     Container messageArea = createMessageArea();
/*  86 */     if (!showBanner) {
/*  87 */       addIcon(messageArea);
/*     */     }
/*  89 */     LookAndFeel.installBorder((JComponent)messageArea, "OptionPane.border");
/*  90 */     this.optionPane.add(messageArea);
/*     */ 
/*  92 */     Container separator = createSeparator();
/*  93 */     if (separator != null) {
/*  94 */       this.optionPane.add(separator);
/*     */     }
/*     */ 
/*  97 */     this.optionPane.add(this._buttonArea = createButtonArea(), "fix");
/*  98 */     this.optionPane.applyComponentOrientation(this.optionPane.getComponentOrientation());
/*     */ 
/* 100 */     if (shouldDetailsButtonVisible()) {
/* 101 */       updateDetailsComponent();
/*     */     }
/*     */ 
/* 104 */     this.optionPane.addPropertyChangeListener(new PropertyChangeListener() {
/*     */       public void propertyChange(PropertyChangeEvent evt) {
/* 106 */         if ("details".equals(evt.getPropertyName())) {
/* 107 */           BasicJideOptionPaneUI.this.updateDetailsComponent();
/* 108 */           if ((BasicJideOptionPaneUI.this._buttonArea instanceof ButtonPanel)) {
/* 109 */             Component detailsButton = ((ButtonPanel)BasicJideOptionPaneUI.this._buttonArea).getButtonByName("DETAILS");
/* 110 */             if (detailsButton != null) {
/* 111 */               detailsButton.setVisible(evt.getNewValue() != null);
/*     */             }
/*     */           }
/*     */         }
/* 115 */         else if ("title".equals(evt.getPropertyName())) {
/* 116 */           BasicJideOptionPaneUI.this.updateTitleComponent(BasicJideOptionPaneUI.this._bannerArea);
/*     */         }
/*     */       } } );
/*     */   }
/*     */ 
/*     */   protected void updateDetailsComponent() {
/* 123 */     if (this._detailsArea != null) {
/* 124 */       this.optionPane.remove(this._detailsArea);
/* 125 */       this._detailsArea = null;
/*     */     }
/* 127 */     this._detailsArea = createDetailsComponent();
/* 128 */     if (this._detailsArea != null) {
/* 129 */       this.optionPane.add(this._detailsArea, "vary");
/* 130 */       this._detailsArea.setVisible(isDetailsVisible());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Container createMessageArea()
/*     */   {
/* 136 */     JPanel top = new JPanel();
/* 137 */     Border topBorder = (Border)UIDefaultsLookup.get("OptionPane.messageAreaBorder");
/* 138 */     if (topBorder != null) {
/* 139 */       top.setBorder(topBorder);
/*     */     }
/* 141 */     top.setLayout(new BorderLayout());
/*     */ 
/* 144 */     Container body = new JPanel(new GridBagLayout());
/* 145 */     Container realBody = new JPanel(new BorderLayout());
/*     */ 
/* 147 */     body.setName("OptionPane.body");
/* 148 */     realBody.setName("OptionPane.realBody");
/*     */ 
/* 150 */     if (getIcon() != null) {
/* 151 */       JPanel sep = new JPanel();
/* 152 */       sep.setName("OptionPane.separator");
/* 153 */       sep.setPreferredSize(new Dimension(15, 1));
/* 154 */       realBody.add(sep, "Before");
/*     */     }
/* 156 */     realBody.add(body, "Center");
/*     */ 
/* 158 */     GridBagConstraints cons = new GridBagConstraints();
/* 159 */     cons.gridx = (cons.gridy = 0);
/* 160 */     cons.gridwidth = 0;
/* 161 */     cons.gridheight = 1;
/* 162 */     int anchor = UIDefaultsLookup.getInt("OptionPane.messageAnchor");
/* 163 */     cons.anchor = (anchor == 0 ? 10 : anchor);
/* 164 */     cons.insets = new Insets(0, 0, 3, 0);
/*     */ 
/* 166 */     Object message = getMessage();
/*     */ 
/* 172 */     addMessageComponents(body, cons, message, getMaxCharactersPerLineCount(), false);
/*     */ 
/* 174 */     top.add(realBody, "Center");
/*     */ 
/* 176 */     return top;
/*     */   }
/*     */ 
/*     */   protected Container createSeparator()
/*     */   {
/* 181 */     return new JSeparator();
/*     */   }
/*     */ 
/*     */   protected void installDefaults()
/*     */   {
/* 186 */     super.installDefaults();
/* 187 */     this.optionPane.setBorder(BorderFactory.createEmptyBorder());
/* 188 */     this._painter = ((ThemePainter)UIDefaultsLookup.get("Theme.painter"));
/*     */   }
/*     */ 
/*     */   protected void uninstallDefaults()
/*     */   {
/* 193 */     super.uninstallDefaults();
/* 194 */     this._painter = null;
/*     */   }
/*     */ 
/*     */   protected Container createDetailsComponent() {
/* 198 */     if (!(this.optionPane instanceof JideOptionPane)) {
/* 199 */       return null;
/*     */     }
/* 201 */     JideOptionPane jideOptionPane = (JideOptionPane)this.optionPane;
/* 202 */     Object details = jideOptionPane.getDetails();
/* 203 */     if ((details instanceof Container)) {
/* 204 */       this._detailsPreferredWidth = ((Container)details).getPreferredSize().width;
/* 205 */       return (Container)details;
/*     */     }
/* 207 */     if ((details instanceof Component)) {
/* 208 */       JPanel panel = new JPanel(new BorderLayout());
/* 209 */       panel.add((Component)details);
/* 210 */       this._detailsPreferredWidth = panel.getPreferredSize().width;
/* 211 */       return panel;
/*     */     }
/* 213 */     if ((details instanceof String)) {
/* 214 */       JTextArea area = new JTextArea((String)details);
/* 215 */       area.setEditable(false);
/* 216 */       area.setRows(20);
/* 217 */       area.setColumns(60);
/* 218 */       JPanel panel = new JPanel(new BorderLayout());
/* 219 */       panel.add(new JScrollPane(area));
/* 220 */       panel.setBorder(BorderFactory.createEmptyBorder(10, 6, 10, 6));
/* 221 */       this._detailsPreferredWidth = panel.getPreferredSize().width;
/* 222 */       return panel;
/*     */     }
/*     */ 
/* 225 */     return null;
/*     */   }
/*     */ 
/*     */   protected Container createButtonArea()
/*     */   {
/* 231 */     int orientation = UIDefaultsLookup.getInt("OptionPane.buttonOrientation");
/* 232 */     orientation = orientation == 0 ? 0 : orientation;
/* 233 */     ButtonPanel buttonPanel = new ButtonPanel(orientation);
/* 234 */     Border border = (Border)UIDefaultsLookup.get("OptionPane.buttonAreaBorder");
/* 235 */     buttonPanel.setName("OptionPane.buttonArea");
/* 236 */     if (border != null) {
/* 237 */       buttonPanel.setBorder(border);
/*     */     }
/* 239 */     boolean sameSize = UIDefaultsLookup.getBoolean("OptionPane.sameSizeButtons");
/* 240 */     buttonPanel.setSizeConstraint(sameSize ? 0 : 1);
/* 241 */     int padding = UIDefaultsLookup.getInt("OptionPane.buttonPadding");
/* 242 */     padding = padding == 0 ? 6 : padding;
/* 243 */     buttonPanel.setButtonGap(padding);
/* 244 */     addButtonComponents(buttonPanel, getButtons(), getInitialValueIndex());
/* 245 */     return buttonPanel;
/*     */   }
/*     */ 
/*     */   protected void addButtonComponents(Container container, Object[] buttons, int initialIndex)
/*     */   {
/* 251 */     if ((buttons != null) && (buttons.length > 0)) {
/* 252 */       int numButtons = buttons.length;
/* 253 */       for (int counter = 0; counter < numButtons; counter++) {
/* 254 */         Object button = buttons[counter];
/*     */         Component newComponent;
/* 257 */         if ((button instanceof Component)) {
/* 258 */           Component newComponent = (Component)button;
/* 259 */           container.add(newComponent, "ALTERNATIVE");
/* 260 */           this.hasCustomComponents = true;
/*     */         }
/*     */         else
/*     */         {
/*     */           JButton aButton;
/*     */           JButton aButton;
/* 265 */           if ((button instanceof ButtonFactory)) {
/* 266 */             aButton = ((ButtonFactory)button).createButton();
/*     */           }
/*     */           else
/*     */           {
/*     */             JButton aButton;
/* 268 */             if ((button instanceof Icon))
/* 269 */               aButton = new JButton((Icon)button);
/*     */             else
/* 271 */               aButton = new JButton(button.toString());
/*     */           }
/* 273 */           aButton.setMultiClickThreshhold(UIDefaultsLookup.getInt("OptionPane.buttonClickThreshhold"));
/* 274 */           configureButton(aButton);
/*     */ 
/* 276 */           if (("YES".equals(aButton.getName())) || ("NO".equals(aButton.getName())) || ("OK".equals(aButton.getName())) || ("CLOSE".equals(aButton.getName())) || ("FINISH".equals(aButton.getName())))
/*     */           {
/* 281 */             container.add(aButton, "AFFIRMATIVE");
/*     */           }
/* 283 */           else if ("CANCEL".equals(aButton.getName())) {
/* 284 */             container.add(aButton, "CANCEL");
/*     */           }
/* 286 */           else if ("HELP".equals(aButton.getName())) {
/* 287 */             container.add(aButton, "HELP");
/*     */           }
/*     */           else {
/* 290 */             container.add(aButton, "ALTERNATIVE");
/*     */           }
/*     */ 
/* 293 */           if ("DETAILS".equals(aButton.getName())) {
/* 294 */             aButton.addActionListener(new AbstractAction() {
/*     */               public void actionPerformed(ActionEvent e) {
/* 296 */                 JButton defaultButton = (JButton)e.getSource();
/* 297 */                 Container top = defaultButton.getTopLevelAncestor();
/* 298 */                 ResourceBundle resourceBundle = ButtonResources.getResourceBundle(BasicJideOptionPaneUI.this.optionPane.getLocale());
/* 299 */                 if (BasicJideOptionPaneUI.this._detailsArea.isVisible()) {
/* 300 */                   BasicJideOptionPaneUI.setDetailsVisible(false);
/* 301 */                   BasicJideOptionPaneUI.this._detailsArea.setVisible(false);
/* 302 */                   defaultButton.setText((BasicJideOptionPaneUI.this.optionPane instanceof JideOptionPane) ? ((JideOptionPane)BasicJideOptionPaneUI.this.optionPane).getResourceString("Button.showDetails") : resourceBundle.getString("Button.showDetails"));
/* 303 */                   defaultButton.setMnemonic((BasicJideOptionPaneUI.this.optionPane instanceof JideOptionPane) ? ((JideOptionPane)BasicJideOptionPaneUI.this.optionPane).getResourceString("Button.showDetails.mnemonic").charAt(0) : resourceBundle.getString("Button.showDetails.mnemonic").charAt(0));
/*     */                 }
/*     */                 else {
/* 306 */                   BasicJideOptionPaneUI.setDetailsVisible(true);
/* 307 */                   BasicJideOptionPaneUI.this._detailsArea.setVisible(true);
/* 308 */                   defaultButton.setText((BasicJideOptionPaneUI.this.optionPane instanceof JideOptionPane) ? ((JideOptionPane)BasicJideOptionPaneUI.this.optionPane).getResourceString("Button.hideDetails") : resourceBundle.getString("Button.hideDetails"));
/* 309 */                   defaultButton.setMnemonic((BasicJideOptionPaneUI.this.optionPane instanceof JideOptionPane) ? ((JideOptionPane)BasicJideOptionPaneUI.this.optionPane).getResourceString("Button.hideDetails.mnemonic").charAt(0) : resourceBundle.getString("Button.hideDetails.mnemonic").charAt(0));
/*     */                 }
/* 311 */                 if ((top instanceof Window))
/* 312 */                   ((Window)top).pack();
/*     */               }
/*     */             });
/* 316 */             aButton.setVisible(shouldDetailsButtonVisible());
/*     */           }
/*     */           else {
/* 319 */             ActionListener buttonListener = createButtonActionListener(counter);
/* 320 */             if (buttonListener != null) {
/* 321 */               aButton.addActionListener(buttonListener);
/*     */             }
/*     */           }
/* 324 */           newComponent = aButton;
/*     */         }
/*     */ 
/* 327 */         if (counter == initialIndex) {
/* 328 */           this.initialFocusComponent = newComponent;
/* 329 */           if ((this.initialFocusComponent instanceof JButton)) {
/* 330 */             JButton defaultB = (JButton)this.initialFocusComponent;
/* 331 */             defaultB.addAncestorListener(new AncestorListener() {
/*     */               public void ancestorAdded(AncestorEvent e) {
/* 333 */                 JButton defaultButton = (JButton)e.getComponent();
/* 334 */                 JRootPane root = SwingUtilities.getRootPane(defaultButton);
/* 335 */                 if (root != null)
/* 336 */                   root.setDefaultButton(defaultButton);
/*     */               }
/*     */ 
/*     */               public void ancestorRemoved(AncestorEvent event)
/*     */               {
/*     */               }
/*     */ 
/*     */               public void ancestorMoved(AncestorEvent event)
/*     */               {
/*     */               }
/*     */             });
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Object[] getButtons()
/*     */   {
/* 360 */     if (this.optionPane != null) {
/* 361 */       Object[] suppliedOptions = this.optionPane.getOptions();
/*     */ 
/* 363 */       if (suppliedOptions == null)
/*     */       {
/* 365 */         int type = this.optionPane.getOptionType();
/* 366 */         Locale l = this.optionPane.getLocale();
/*     */         Object[] defaultOptions;
/* 367 */         if (type == 0) {
/* 368 */           Object[] defaultOptions = new ButtonFactory[2];
/* 369 */           defaultOptions[0] = new ButtonFactory("YES", UIDefaultsLookup.getString("OptionPane.yesButtonText", l), getMnemonic("OptionPane.yesButtonMnemonic", l), (Icon)UIDefaultsLookup.get("OptionPane.yesIcon"));
/*     */ 
/* 374 */           defaultOptions[1] = new ButtonFactory("NO", UIDefaultsLookup.getString("OptionPane.noButtonText", l), getMnemonic("OptionPane.noButtonMnemonic", l), (Icon)UIDefaultsLookup.get("OptionPane.noIcon"));
/*     */         }
/* 380 */         else if (type == 1) {
/* 381 */           Object[] defaultOptions = new ButtonFactory[3];
/* 382 */           defaultOptions[0] = new ButtonFactory("YES", UIDefaultsLookup.getString("OptionPane.yesButtonText", l), getMnemonic("OptionPane.yesButtonMnemonic", l), (Icon)UIDefaultsLookup.get("OptionPane.yesIcon"));
/*     */ 
/* 387 */           defaultOptions[1] = new ButtonFactory("NO", UIDefaultsLookup.getString("OptionPane.noButtonText", l), getMnemonic("OptionPane.noButtonMnemonic", l), (Icon)UIDefaultsLookup.get("OptionPane.noIcon"));
/*     */ 
/* 392 */           defaultOptions[2] = new ButtonFactory("CANCEL", UIDefaultsLookup.getString("OptionPane.cancelButtonText", l), getMnemonic("OptionPane.cancelButtonMnemonic", l), (Icon)UIDefaultsLookup.get("OptionPane.cancelIcon"));
/*     */         }
/* 398 */         else if (type == 2) {
/* 399 */           Object[] defaultOptions = new ButtonFactory[2];
/* 400 */           defaultOptions[0] = new ButtonFactory("OK", UIDefaultsLookup.getString("OptionPane.okButtonText", l), getMnemonic("OptionPane.okButtonMnemonic", l), (Icon)UIDefaultsLookup.get("OptionPane.okIcon"));
/*     */ 
/* 405 */           defaultOptions[1] = new ButtonFactory("CANCEL", UIDefaultsLookup.getString("OptionPane.cancelButtonText", l), getMnemonic("OptionPane.cancelButtonMnemonic", l), (Icon)UIDefaultsLookup.get("OptionPane.cancelIcon"));
/*     */         }
/* 411 */         else if (type == 3) {
/* 412 */           Object[] defaultOptions = new ButtonFactory[1];
/* 413 */           ResourceBundle resourceBundle = ButtonResources.getResourceBundle(this.optionPane.getLocale());
/* 414 */           defaultOptions[0] = new ButtonFactory("CLOSE", (this.optionPane instanceof JideOptionPane) ? ((JideOptionPane)this.optionPane).getResourceString("Button.close") : resourceBundle.getString("Button.close"), (this.optionPane instanceof JideOptionPane) ? ((JideOptionPane)this.optionPane).getResourceString("Button.close.mnemonic").charAt(0) : resourceBundle.getString("Button.close.mnemonic").charAt(0), null);
/*     */         }
/*     */         else
/*     */         {
/* 421 */           defaultOptions = new ButtonFactory[1];
/* 422 */           defaultOptions[0] = new ButtonFactory("OK", UIDefaultsLookup.getString("OptionPane.okButtonText", l), getMnemonic("OptionPane.okButtonMnemonic", l), (Icon)UIDefaultsLookup.get("OptionPane.okIcon"));
/*     */         }
/*     */ 
/* 429 */         return addDetailsButton(defaultOptions, true);
/*     */       }
/*     */ 
/* 432 */       return addDetailsButton(suppliedOptions, true);
/*     */     }
/* 434 */     return null;
/*     */   }
/*     */ 
/*     */   protected Object[] addDetailsButton(Object[] options, boolean showDetails) {
/* 438 */     if (showDetails) {
/* 439 */       Object[] newOptions = new Object[options.length + 1];
/* 440 */       for (int i = 0; i < options.length; i++) {
/* 441 */         newOptions[i] = options[i];
/*     */       }
/* 443 */       ResourceBundle resourceBundle = ButtonResources.getResourceBundle(this.optionPane.getLocale());
/* 444 */       newOptions[(newOptions.length - 1)] = new ButtonFactory("DETAILS", (this.optionPane instanceof JideOptionPane) ? ((JideOptionPane)this.optionPane).getResourceString("Button.showDetails") : resourceBundle.getString("Button.showDetails"), (this.optionPane instanceof JideOptionPane) ? ((JideOptionPane)this.optionPane).getResourceString("Button.showDetails.mnemonic").charAt(0) : resourceBundle.getString("Button.showDetails.mnemonic").charAt(0), null);
/*     */ 
/* 448 */       return newOptions;
/*     */     }
/*     */ 
/* 451 */     return options;
/*     */   }
/*     */ 
/*     */   private boolean shouldDetailsButtonVisible()
/*     */   {
/* 457 */     return ((this.optionPane instanceof JideOptionPane)) && (((JideOptionPane)this.optionPane).getDetails() != null);
/*     */   }
/*     */ 
/*     */   protected void configureButton(JButton button)
/*     */   {
/* 465 */     Font buttonFont = (Font)UIDefaultsLookup.get("OptionPane.buttonFont");
/* 466 */     if (buttonFont != null)
/* 467 */       button.setFont(buttonFont);
/*     */   }
/*     */ 
/*     */   protected int getMnemonic(String key, Locale l)
/*     */   {
/* 472 */     String value = (String)UIDefaultsLookup.get(key, l);
/*     */ 
/* 474 */     if (value == null)
/* 475 */       return 0;
/*     */     try
/*     */     {
/* 478 */       return Integer.parseInt(value);
/*     */     }
/*     */     catch (NumberFormatException nfe) {
/*     */     }
/* 482 */     return 0;
/*     */   }
/*     */ 
/*     */   protected void addIcon(Container top)
/*     */   {
/* 519 */     Icon sideIcon = getIcon();
/* 520 */     if (sideIcon != null) {
/* 521 */       JLabel iconLabel = new JLabel(sideIcon);
/* 522 */       iconLabel.setName("OptionPane.iconLabel");
/* 523 */       top.add(iconLabel, "Before");
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Container createBannerArea() {
/* 528 */     PaintPanel bannerPanel = new PaintPanel()
/*     */     {
/*     */       public Dimension getPreferredSize() {
/* 531 */         Dimension preferredSize = super.getPreferredSize();
/* 532 */         if (preferredSize.width < BasicJideOptionPaneUI.this._detailsPreferredWidth) {
/* 533 */           preferredSize.width = BasicJideOptionPaneUI.this._detailsPreferredWidth;
/*     */         }
/* 535 */         return preferredSize;
/*     */       }
/*     */     };
/* 538 */     customizeBannerArea(bannerPanel);
/* 539 */     bannerPanel.setLayout(new BorderLayout(10, 10));
/* 540 */     addIcon(bannerPanel);
/* 541 */     updateTitleComponent(bannerPanel);
/* 542 */     bannerPanel.add(new JLabel(UIDefaultsLookup.getIcon("OptionPane.bannerIcon")), "After");
/* 543 */     return bannerPanel;
/*     */   }
/*     */ 
/*     */   protected void customizeBannerArea(PaintPanel bannerPanel) {
/* 547 */     Paint paint = (Paint)UIDefaultsLookup.get("OptionPane.bannerBackgroundPaint");
/*     */ 
/* 549 */     if (paint != null) {
/* 550 */       bannerPanel.setBackgroundPaint(paint);
/*     */     }
/*     */     else {
/* 553 */       Color dk = UIDefaultsLookup.getColor("OptionPane.bannerBackgroundDk");
/* 554 */       Color lt = UIDefaultsLookup.getColor("OptionPane.bannerBackgroundLt");
/* 555 */       boolean direction = (UIDefaultsLookup.get("OptionPane.bannerBackgroundDirection") == null) || (UIDefaultsLookup.getBoolean("OptionPane.bannerBackgroundDirection"));
/*     */ 
/* 557 */       if ((dk == null) && (lt != null)) {
/* 558 */         dk = lt;
/* 559 */         bannerPanel.setGradientPaint(dk != null ? dk : getPainter().getOptionPaneBannerLt(), lt != null ? lt : getPainter().getOptionPaneBannerDk(), direction);
/*     */       }
/* 564 */       else if ((dk != null) && (lt == null)) {
/* 565 */         lt = dk;
/* 566 */         bannerPanel.setGradientPaint(dk != null ? dk : getPainter().getOptionPaneBannerLt(), lt != null ? lt : getPainter().getOptionPaneBannerDk(), direction);
/*     */       }
/* 571 */       else if ((dk != null) && (lt != null)) {
/* 572 */         bannerPanel.setGradientPaint(dk != null ? dk : getPainter().getOptionPaneBannerLt(), lt != null ? lt : getPainter().getOptionPaneBannerDk(), direction);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 579 */     Border border = UIDefaultsLookup.getBorder("OptionPane.bannerBorder");
/* 580 */     bannerPanel.setBorder(border != null ? border : BorderFactory.createEmptyBorder(0, 10, 0, 0));
/*     */   }
/*     */ 
/*     */   private void updateTitleComponent(Container bannerArea) {
/* 584 */     if (bannerArea == null) {
/* 585 */       return;
/*     */     }
/*     */ 
/* 588 */     if (this._titleComponent != null) {
/* 589 */       bannerArea.remove(this._titleComponent);
/* 590 */       this._titleComponent = null;
/*     */     }
/*     */ 
/* 593 */     Object title = (this.optionPane instanceof JideOptionPane) ? ((JideOptionPane)this.optionPane).getTitle() : null;
/* 594 */     if ((title instanceof String)) {
/* 595 */       if ((((String)title).startsWith("<html>")) || (((String)title).startsWith("<HTML>"))) {
/* 596 */         JLabel titleLabel = new JLabel((String)title);
/* 597 */         this._titleComponent = titleLabel;
/*     */       }
/*     */       else {
/* 600 */         String[] titles = fitInWidth((String)title, UIDefaultsLookup.getInt("OptionPane.bannerMaxCharsPerLine"));
/* 601 */         JPanel titlePanel = new NullPanel();
/* 602 */         titlePanel.setLayout(new BoxLayout(titlePanel, 1));
/* 603 */         titlePanel.setOpaque(false);
/* 604 */         titlePanel.add(Box.createGlue());
/* 605 */         for (String s : titles) {
/* 606 */           JLabel label = new JLabel(s);
/* 607 */           label.setFont(label.getFont().deriveFont(UIDefaultsLookup.getInt("OptionPane.bannerFontStyle"), UIDefaultsLookup.getInt("OptionPane.bannerFontSize")));
/* 608 */           Color color = UIDefaultsLookup.getColor("OptionPane.bannerForeground");
/* 609 */           label.setForeground(color != null ? color : getPainter().getOptionPaneBannerForeground());
/* 610 */           titlePanel.add(label);
/*     */         }
/* 612 */         titlePanel.add(Box.createGlue());
/* 613 */         this._titleComponent = titlePanel;
/*     */       }
/*     */     }
/* 616 */     else if ((title instanceof Component)) {
/* 617 */       this._titleComponent = ((Component)title);
/*     */     }
/* 619 */     if (this._titleComponent != null)
/* 620 */       bannerArea.add(this._titleComponent, "Center");
/*     */   }
/*     */ 
/*     */   private static String[] fitInWidth(String str, int width)
/*     */   {
/* 635 */     if (str == null) str = "";
/*     */ 
/* 637 */     String BLANK_STR = blankString(width, 32);
/* 638 */     str = replaceOccurrences(str, "\n", BLANK_STR);
/* 639 */     str = replaceOccurrences(str, "\t", "    ");
/*     */ 
/* 641 */     ArrayList strArray = new ArrayList();
/* 642 */     str = str.trim();
/* 643 */     while (str.length() > width) {
/* 644 */       int breakPos = width;
/* 645 */       if (Character.isLetterOrDigit(str.charAt(width))) {
/* 646 */         breakPos--;
/* 647 */         char breakChar = str.charAt(breakPos);
/* 648 */         while ((Character.isLetterOrDigit(breakChar)) && (breakPos > 0)) {
/* 649 */           breakPos--;
/* 650 */           breakChar = str.charAt(breakPos);
/*     */         }
/* 652 */         if ((breakPos == 0) && (Character.isLetterOrDigit(breakChar))) {
/* 653 */           breakPos = width;
/*     */         }
/*     */         else {
/* 656 */           breakPos++;
/*     */         }
/*     */       }
/* 659 */       String subStr = str.substring(0, breakPos);
/* 660 */       if (subStr.length() < width) {
/* 661 */         subStr = subStr + blankString(width - subStr.length(), 32);
/*     */       }
/* 663 */       strArray.add(subStr);
/* 664 */       str = str.substring(breakPos).trim();
/*     */     }
/* 666 */     if (str.length() < width) {
/* 667 */       str = str + blankString(width - str.length(), 32);
/*     */     }
/* 669 */     strArray.add(str);
/* 670 */     return (String[])(String[])strArray.toArray(new String[1]);
/*     */   }
/*     */ 
/*     */   private static String blankString(int width, byte b) {
/* 674 */     byte[] bytes = new byte[width];
/* 675 */     Arrays.fill(bytes, b);
/* 676 */     return new String(bytes);
/*     */   }
/*     */ 
/*     */   private static String replaceOccurrences(String string, String target, String dest)
/*     */   {
/* 688 */     StringBuffer b = new StringBuffer(string);
/* 689 */     int lastIndex = 0;
/*     */     while (true) {
/* 691 */       int index = indexOf(b, target, lastIndex);
/* 692 */       if (index < 0) {
/*     */         break;
/*     */       }
/* 695 */       b.replace(index, index + target.length(), dest);
/* 696 */       lastIndex = index + dest.length();
/*     */     }
/* 698 */     return b.toString();
/*     */   }
/*     */ 
/*     */   private static int indexOf(char[] source, int sourceOffset, int sourceCount, char[] target, int targetOffset, int targetCount, int fromIndex)
/*     */   {
/* 718 */     if (fromIndex >= sourceCount) {
/* 719 */       return targetCount == 0 ? sourceCount : -1;
/*     */     }
/* 721 */     if (fromIndex < 0) {
/* 722 */       fromIndex = 0;
/*     */     }
/* 724 */     if (targetCount == 0) {
/* 725 */       return fromIndex;
/*     */     }
/*     */ 
/* 728 */     char first = target[targetOffset];
/* 729 */     int pos = sourceOffset + fromIndex;
/* 730 */     int max = sourceOffset + (sourceCount - targetCount);
/*     */     while (true)
/*     */     {
/* 737 */       if ((pos <= max) && (source[pos] != first)) {
/* 738 */         pos++; continue;
/*     */       }
/*     */ 
/* 742 */       if (pos > max) {
/* 743 */         return -1;
/*     */       }
/*     */ 
/* 747 */       int secondPos = pos + 1;
/* 748 */       int end = secondPos + targetCount - 1;
/* 749 */       int targetPos = targetOffset + 1;
/*     */       do if (secondPos >= end)
/*     */           break; while (source[(secondPos++)] == target[(targetPos++)]);
/* 752 */       pos++;
/*     */     }
/*     */ 
/* 759 */     return pos - sourceOffset;
/*     */   }
/*     */ 
/*     */   private static int indexOf(StringBuffer buf, String findStr, int fromIndex)
/*     */   {
/* 778 */     synchronized (buf) {
/* 779 */       int bufLen = buf.length();
/* 780 */       char[] charArray = new char[bufLen];
/* 781 */       buf.getChars(0, bufLen, charArray, 0);
/*     */ 
/* 784 */       return indexOf(charArray, 0, bufLen, findStr.toCharArray(), 0, findStr.length(), fromIndex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public ThemePainter getPainter()
/*     */   {
/* 790 */     return this._painter;
/*     */   }
/*     */ 
/*     */   protected static class ButtonFactory
/*     */   {
/*     */     private String name;
/*     */     private String text;
/*     */     private int mnemonic;
/*     */     private Icon icon;
/*     */ 
/*     */     ButtonFactory(String name, String text, int mnemonic, Icon icon)
/*     */     {
/* 496 */       this.name = name;
/* 497 */       this.text = text;
/* 498 */       this.mnemonic = mnemonic;
/* 499 */       this.icon = icon;
/*     */     }
/*     */ 
/*     */     JButton createButton() {
/* 503 */       JButton button = new JButton(this.text);
/* 504 */       if (this.name != null) {
/* 505 */         button.setName(this.name);
/*     */       }
/* 507 */       if (this.icon != null) {
/* 508 */         button.setIcon(this.icon);
/*     */       }
/* 510 */       if (this.mnemonic != 0) {
/* 511 */         button.setMnemonic(this.mnemonic);
/*     */       }
/* 513 */       return button;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.BasicJideOptionPaneUI
 * JD-Core Version:    0.6.0
 */
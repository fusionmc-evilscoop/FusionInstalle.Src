/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.popup.JidePopup;
/*     */ import com.jidesoft.popup.JidePopupFactory;
/*     */ import com.jidesoft.swing.event.SearchableEvent;
/*     */ import com.jidesoft.swing.event.SearchableListener;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.FocusAdapter;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.KeyListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.JToggleButton;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.Timer;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import javax.swing.event.DocumentListener;
/*     */ import javax.swing.text.Document;
/*     */ 
/*     */ public class SearchableBar extends JToolBar
/*     */   implements SearchableProvider
/*     */ {
/*     */   private Searchable _searchable;
/*     */   private JLabel _statusLabel;
/*     */   private JTextField _textField;
/*     */   protected AbstractButton _closeButton;
/*     */   protected AbstractButton _findPrevButton;
/*     */   protected AbstractButton _findNextButton;
/*     */   protected AbstractButton _highlightsButton;
/*     */   private AbstractButton _matchCaseCheckBox;
/*     */   private AbstractButton _repeatCheckBox;
/*     */   public static final int SHOW_CLOSE = 1;
/*     */   public static final int SHOW_NAVIGATION = 2;
/*     */   public static final int SHOW_HIGHLIGHTS = 4;
/*     */   public static final int SHOW_MATCHCASE = 8;
/*     */   public static final int SHOW_REPEATS = 16;
/*     */   public static final int SHOW_STATUS = 32;
/*     */   public static final int SHOW_ALL = -1;
/*  88 */   private int _visibleButtons = -17;
/*     */   private boolean _compact;
/*     */   private JidePopup _messagePopup;
/*     */   private MouseMotionListener _mouseMotionListener;
/*     */   private KeyListener _keyListener;
/* 643 */   private static final Color DEFAULT_MISMATCH_BACKGROUND = new Color(255, 85, 85);
/*     */   private Color _mismatchBackground;
/*     */   private Installer _installer;
/*     */ 
/*     */   public SearchableBar(Searchable searchable)
/*     */   {
/* 101 */     this(searchable, "", false);
/*     */   }
/*     */ 
/*     */   public SearchableBar(Searchable searchable, boolean compact)
/*     */   {
/* 111 */     this(searchable, "", compact);
/*     */   }
/*     */ 
/*     */   public SearchableBar(Searchable searchable, String initialText, boolean compact)
/*     */   {
/* 122 */     setFloatable(false);
/* 123 */     setRollover(true);
/* 124 */     this._searchable = searchable;
/* 125 */     this._searchable.addSearchableListener(new SearchableListener() {
/*     */       public void searchableEventFired(SearchableEvent e) {
/* 127 */         if ((e.getID() == 3005) && (SearchableBar.this._searchable.getSearchingText() != null) && (SearchableBar.this._searchable.getSearchingText().length() != 0))
/* 128 */           SearchableBar.this.highlightAllOrNext();
/*     */       }
/*     */     });
/* 132 */     this._searchable.setSearchableProvider(this);
/* 133 */     this._compact = compact;
/* 134 */     initComponents(initialText);
/*     */   }
/*     */ 
/*     */   private void initComponents(String initialText) {
/* 138 */     AbstractAction closeAction = new AbstractAction() {
/*     */       private static final long serialVersionUID = -2245391247321137224L;
/*     */ 
/* 142 */       public void actionPerformed(ActionEvent e) { if (SearchableBar.this.getInstaller() != null)
/* 143 */           SearchableBar.this.getInstaller().closeSearchBar(SearchableBar.this);
/*     */       }
/*     */     };
/* 148 */     AbstractAction findNextAction = new AbstractAction() {
/*     */       private static final long serialVersionUID = -5263488798121831276L;
/*     */ 
/* 152 */       public void actionPerformed(ActionEvent e) { SearchableBar.this._highlightsButton.setSelected(false);
/* 153 */         String text = SearchableBar.this.getSearchingText();
/* 154 */         int cursor = SearchableBar.this._searchable.getSelectedIndex();
/* 155 */         SearchableBar.this._searchable.setCursor(cursor);
/* 156 */         int found = SearchableBar.this._searchable.findNext(text);
/* 157 */         if ((found != -1) && (SearchableBar.this._searchable.isRepeats()) && (found <= cursor)) {
/* 158 */           SearchableBar.this.select(found, text, false);
/* 159 */           SearchableBar.this.setStatus(SearchableBar.this.getResourceString("SearchableBar.reachedBottomRepeat"), SearchableBar.this.getImageIcon("icons/repeat.png"));
/*     */         }
/* 161 */         else if ((!SearchableBar.this._searchable.isRepeats()) && (found == -1)) {
/* 162 */           SearchableBar.this.setStatus(SearchableBar.this.getResourceString("SearchableBar.reachedBottom"), SearchableBar.this.getImageIcon("icons/error.png"));
/*     */         }
/* 164 */         else if (found != -1) {
/* 165 */           SearchableBar.this.select(found, text, false);
/* 166 */           SearchableBar.this.clearStatus();
/*     */         }
/*     */       }
/*     */     };
/* 171 */     AbstractAction findPrevAction = new AbstractAction() {
/*     */       private static final long serialVersionUID = -2534332227053620232L;
/*     */ 
/* 175 */       public void actionPerformed(ActionEvent e) { SearchableBar.this._highlightsButton.setSelected(false);
/* 176 */         String text = SearchableBar.this.getSearchingText();
/* 177 */         int cursor = SearchableBar.this._searchable.getSelectedIndex();
/* 178 */         SearchableBar.this._searchable.setCursor(cursor);
/* 179 */         int found = SearchableBar.this._searchable.findPrevious(text);
/* 180 */         if ((found != -1) && (SearchableBar.this._searchable.isRepeats()) && (found >= cursor)) {
/* 181 */           SearchableBar.this.select(found, text, false);
/* 182 */           SearchableBar.this.setStatus(SearchableBar.this.getResourceString("SearchableBar.reachedTopRepeat"), SearchableBar.this.getImageIcon("icons/repeat.png"));
/*     */         }
/* 184 */         else if ((!SearchableBar.this._searchable.isRepeats()) && (found == -1)) {
/* 185 */           SearchableBar.this.setStatus(SearchableBar.this.getResourceString("SearchableBar.reachedTop"), SearchableBar.this.getImageIcon("icons/error.png"));
/*     */         }
/* 187 */         else if (found != -1) {
/* 188 */           SearchableBar.this.select(found, text, false);
/* 189 */           SearchableBar.this.clearStatus();
/*     */         }
/*     */       }
/*     */     };
/* 194 */     this._mouseMotionListener = new MouseMotionListener() {
/*     */       public void mouseMoved(MouseEvent e) {
/* 196 */         SearchableBar.this.hideMessage();
/*     */       }
/*     */ 
/*     */       public void mouseDragged(MouseEvent e)
/*     */       {
/*     */       }
/*     */     };
/* 203 */     this._keyListener = new KeyListener() {
/*     */       public void keyTyped(KeyEvent e) {
/* 205 */         SearchableBar.this.hideMessage();
/*     */       }
/*     */ 
/*     */       public void keyPressed(KeyEvent e)
/*     */       {
/*     */       }
/*     */ 
/*     */       public void keyReleased(KeyEvent e)
/*     */       {
/*     */       }
/*     */     };
/* 217 */     this._closeButton = createCloseButton(closeAction);
/* 218 */     this._findNextButton = createFindNextButton(findNextAction);
/* 219 */     this._findPrevButton = createFindPrevButton(findPrevAction);
/* 220 */     this._highlightsButton = createHighlightButton();
/* 221 */     this._matchCaseCheckBox = createMatchCaseButton();
/* 222 */     this._repeatCheckBox = createRepeatsButton();
/*     */ 
/* 224 */     this._statusLabel = new JLabel();
/*     */ 
/* 227 */     this._textField = createTextField();
/* 228 */     this._textField.addFocusListener(new FocusAdapter()
/*     */     {
/*     */       public void focusGained(FocusEvent e) {
/* 231 */         SearchableBar.this._textField.selectAll();
/*     */       }
/*     */     });
/* 234 */     this._textField.setColumns(13);
/* 235 */     this._textField.getDocument().addDocumentListener(new DocumentListener() {
/* 236 */       private Timer timer = new Timer(SearchableBar.this._searchable.getSearchingDelay(), new ActionListener() {
/*     */         public void actionPerformed(ActionEvent e) {
/* 238 */           SearchableBar.this.highlightAllOrNext();
/*     */         }
/*     */       });
/*     */ 
/*     */       public void insertUpdate(DocumentEvent e)
/*     */       {
/* 243 */         startTimer();
/*     */       }
/*     */ 
/*     */       public void removeUpdate(DocumentEvent e) {
/* 247 */         startTimer();
/*     */       }
/*     */ 
/*     */       public void changedUpdate(DocumentEvent e) {
/* 251 */         startTimer();
/*     */       }
/*     */ 
/*     */       void startTimer() {
/* 255 */         if (SearchableBar.this._searchable.getSearchingDelay() > 0) {
/* 256 */           if (this.timer.isRunning()) {
/* 257 */             this.timer.restart();
/*     */           }
/*     */           else {
/* 260 */             this.timer.setRepeats(false);
/* 261 */             this.timer.start();
/*     */           }
/*     */         }
/*     */         else
/* 265 */           SearchableBar.this.highlightAllOrNext();
/*     */       }
/*     */     });
/* 269 */     this._textField.setText(initialText);
/*     */ 
/* 271 */     this._textField.registerKeyboardAction(findNextAction, KeyStroke.getKeyStroke(40, 0), 0);
/* 272 */     this._textField.registerKeyboardAction(findNextAction, KeyStroke.getKeyStroke(10, 0), 0);
/* 273 */     this._textField.registerKeyboardAction(findPrevAction, KeyStroke.getKeyStroke(38, 0), 0);
/* 274 */     this._textField.registerKeyboardAction(closeAction, KeyStroke.getKeyStroke(27, 0), 0);
/*     */ 
/* 276 */     installComponents();
/*     */ 
/* 278 */     int found = this._searchable.findFromCursor(getSearchingText());
/* 279 */     if ((initialText.length() != 0) && (found == -1))
/* 280 */       select(found, initialText, false);
/*     */   }
/*     */ 
/*     */   protected JTextField createTextField()
/*     */   {
/* 290 */     return new JTextField();
/*     */   }
/*     */ 
/*     */   public Searchable getSearchable()
/*     */   {
/* 299 */     return this._searchable;
/*     */   }
/*     */ 
/*     */   protected AbstractButton createCloseButton(AbstractAction closeAction)
/*     */   {
/* 309 */     AbstractButton button = new JButton(getImageIcon("icons/close.png"));
/* 310 */     button.addActionListener(closeAction);
/* 311 */     button.setRolloverEnabled(true);
/* 312 */     button.setBorder(BorderFactory.createEmptyBorder());
/* 313 */     button.setOpaque(false);
/* 314 */     button.setRequestFocusEnabled(false);
/* 315 */     button.setFocusable(false);
/* 316 */     button.setRolloverIcon(getImageIcon("icons/closeR.png"));
/* 317 */     return button;
/*     */   }
/*     */ 
/*     */   protected AbstractButton createFindNextButton(AbstractAction findNextAction)
/*     */   {
/* 327 */     AbstractButton button = new JButton(this._compact ? "" : getResourceString("SearchableBar.findNext"), getImageIcon("icons/next.png"));
/*     */ 
/* 329 */     button.setToolTipText(getResourceString("SearchableBar.findNext.tooltip"));
/* 330 */     button.setMnemonic(getResourceString("SearchableBar.findNext.mnemonic").charAt(0));
/* 331 */     button.setRolloverIcon(getImageIcon("icons/nextR.png"));
/* 332 */     button.setDisabledIcon(getImageIcon("icons/nextD.png"));
/* 333 */     button.setRequestFocusEnabled(false);
/* 334 */     button.setFocusable(false);
/* 335 */     button.addActionListener(findNextAction);
/* 336 */     button.setEnabled(false);
/* 337 */     return button;
/*     */   }
/*     */ 
/*     */   protected AbstractButton createFindPrevButton(AbstractAction findPrevAction)
/*     */   {
/* 347 */     AbstractButton button = new JButton(this._compact ? "" : getResourceString("SearchableBar.findPrevious"), getImageIcon("icons/previous.png"));
/*     */ 
/* 349 */     button.setToolTipText(getResourceString("SearchableBar.findPrevious.tooltip"));
/* 350 */     button.setMnemonic(getResourceString("SearchableBar.findPrevious.mnemonic").charAt(0));
/* 351 */     button.setRolloverIcon(getImageIcon("icons/previousR.png"));
/* 352 */     button.setDisabledIcon(getImageIcon("icons/previousD.png"));
/* 353 */     button.setRequestFocusEnabled(false);
/* 354 */     button.setFocusable(false);
/* 355 */     button.addActionListener(findPrevAction);
/* 356 */     button.setEnabled(false);
/* 357 */     return button;
/*     */   }
/*     */ 
/*     */   protected AbstractButton createHighlightButton()
/*     */   {
/* 366 */     AbstractButton button = new JToggleButton(this._compact ? "" : getResourceString("SearchableBar.highlights"), getImageIcon("icons/highlights.png"));
/*     */ 
/* 368 */     button.setToolTipText(getResourceString("SearchableBar.highlights.tooltip"));
/* 369 */     button.setMnemonic(getResourceString("SearchableBar.highlights.mnemonic").charAt(0));
/* 370 */     button.setSelectedIcon(getImageIcon("icons/highlightsS.png"));
/* 371 */     button.setDisabledIcon(getImageIcon("icons/highlightsD.png"));
/* 372 */     button.setRolloverIcon(getImageIcon("icons/highlightsR.png"));
/* 373 */     button.setRolloverSelectedIcon(getImageIcon("icons/highlightsRS.png"));
/* 374 */     button.setRequestFocusEnabled(false);
/* 375 */     button.setFocusable(false);
/*     */ 
/* 377 */     AbstractAction highlightAllAction = new AbstractAction() {
/*     */       private static final long serialVersionUID = 5170786863522331175L;
/*     */ 
/* 381 */       public void actionPerformed(ActionEvent e) { SearchableBar.this.highlightAllOrNext();
/*     */       }
/*     */     };
/* 385 */     button.addActionListener(highlightAllAction);
/* 386 */     button.setEnabled(false);
/* 387 */     return button;
/*     */   }
/*     */ 
/*     */   protected AbstractButton createRepeatsButton()
/*     */   {
/* 398 */     AbstractButton button = new JCheckBox(getResourceString("SearchableBar.repeats"));
/* 399 */     button.setMnemonic(getResourceString("SearchableBar.repeats.mnemonic").charAt(0));
/* 400 */     button.setRequestFocusEnabled(false);
/* 401 */     button.setFocusable(false);
/* 402 */     button.setSelected(getSearchable().isRepeats());
/* 403 */     button.addItemListener(new ItemListener() {
/*     */       public void itemStateChanged(ItemEvent e) {
/* 405 */         if ((e.getSource() instanceof AbstractButton))
/* 406 */           SearchableBar.this.getSearchable().setRepeats(((AbstractButton)e.getSource()).isSelected());
/*     */       }
/*     */     });
/* 410 */     return button;
/*     */   }
/*     */ 
/*     */   protected AbstractButton createMatchCaseButton()
/*     */   {
/* 421 */     JCheckBox checkBox = new JCheckBox(getResourceString("SearchableBar.matchCase"));
/* 422 */     checkBox.setMnemonic(getResourceString("SearchableBar.matchCase.mnemonic").charAt(0));
/* 423 */     checkBox.setRequestFocusEnabled(false);
/* 424 */     checkBox.setFocusable(false);
/* 425 */     checkBox.setSelected(getSearchable().isCaseSensitive());
/* 426 */     checkBox.addItemListener(new ItemListener() {
/*     */       public void itemStateChanged(ItemEvent e) {
/* 428 */         if ((e.getSource() instanceof AbstractButton)) {
/* 429 */           SearchableBar.this.getSearchable().setCaseSensitive(((AbstractButton)e.getSource()).isSelected());
/* 430 */           SearchableBar.this.highlightAllOrNext();
/*     */         }
/*     */       }
/*     */     });
/* 434 */     return checkBox;
/*     */   }
/*     */ 
/*     */   protected void installComponents()
/*     */   {
/* 442 */     setBorder(BorderFactory.createEtchedBorder());
/* 443 */     setLayout(new JideBoxLayout(this, 0));
/* 444 */     add(Box.createHorizontalStrut(4), "fix");
/* 445 */     if ((this._visibleButtons & 0x1) != 0) {
/* 446 */       add(this._closeButton);
/* 447 */       add(Box.createHorizontalStrut(10));
/*     */     }
/*     */ 
/* 450 */     JLabel label = new JLabel(getResourceString("SearchableBar.find"));
/* 451 */     label.setDisplayedMnemonic(getResourceString("SearchableBar.find.mnemonic").charAt(0));
/* 452 */     label.setLabelFor(this._textField);
/* 453 */     add(label);
/* 454 */     add(Box.createHorizontalStrut(2), "fix");
/* 455 */     add(JideSwingUtilities.createCenterPanel(this._textField), "fix");
/* 456 */     add(Box.createHorizontalStrut(2), "fix");
/* 457 */     if ((this._visibleButtons & 0x2) != 0) {
/* 458 */       add(this._findNextButton);
/* 459 */       add(this._findPrevButton);
/*     */     }
/* 461 */     if ((this._visibleButtons & 0x4) != 0) {
/* 462 */       add(this._highlightsButton);
/*     */     }
/* 464 */     if ((this._visibleButtons & 0x8) != 0) {
/* 465 */       add(this._matchCaseCheckBox);
/*     */     }
/* 467 */     if ((this._visibleButtons & 0x10) != 0) {
/* 468 */       add(this._repeatCheckBox);
/*     */     }
/* 470 */     if ((this._visibleButtons & 0x20) != 0) {
/* 471 */       add(Box.createHorizontalStrut(24));
/* 472 */       add(this._statusLabel, "vary");
/*     */     }
/* 474 */     add(Box.createHorizontalStrut(6), "fix");
/*     */   }
/*     */ 
/*     */   public boolean isHighlightAll()
/*     */   {
/* 485 */     return this._highlightsButton.isSelected();
/*     */   }
/*     */ 
/*     */   public void setHighlightAll(boolean highlightAll)
/*     */   {
/* 495 */     this._highlightsButton.setSelected(highlightAll);
/*     */   }
/*     */ 
/*     */   private void highlightAllOrNext() {
/* 499 */     if (this._highlightsButton.isSelected()) {
/* 500 */       highlighAll();
/*     */     }
/*     */     else
/* 503 */       highlightNext();
/*     */   }
/*     */ 
/*     */   private void highlighAll()
/*     */   {
/* 508 */     String text = getSearchingText();
/* 509 */     if ((text == null) || (text.length() == 0)) {
/* 510 */       this._findNextButton.setEnabled(false);
/* 511 */       this._findPrevButton.setEnabled(false);
/* 512 */       this._highlightsButton.setEnabled(false);
/* 513 */       select(-1, "", false);
/* 514 */       clearStatus();
/* 515 */       return;
/*     */     }
/* 517 */     boolean old = this._searchable.isRepeats();
/* 518 */     this._searchable.setRepeats(false);
/* 519 */     int index = this._searchable.findFirst(text);
/* 520 */     if (index != -1) {
/* 521 */       this._searchable.setSelectedIndex(index, false);
/* 522 */       this._searchable.setCursor(index);
/* 523 */       this._findNextButton.setEnabled(true);
/* 524 */       this._findPrevButton.setEnabled(true);
/* 525 */       this._highlightsButton.setEnabled(true);
/* 526 */       clearStatus();
/*     */     }
/*     */     else {
/* 529 */       select(-1, text, false);
/* 530 */       this._findNextButton.setEnabled(false);
/* 531 */       this._findPrevButton.setEnabled(false);
/* 532 */       this._highlightsButton.setEnabled(false);
/* 533 */       setStatus(getResourceString("SearchableBar.notFound"), getImageIcon("icons/error.png"));
/*     */     }
/*     */ 
/* 536 */     this._searchable.highlightAll();
/* 537 */     this._searchable.setRepeats(old);
/* 538 */     this._searchable.setCursor(0);
/*     */   }
/*     */ 
/*     */   private void highlightNext() {
/* 542 */     this._searchable.cancelHighlightAll();
/* 543 */     String text = getSearchingText();
/* 544 */     if ((text == null) || (text.length() == 0)) {
/* 545 */       this._findNextButton.setEnabled(false);
/* 546 */       this._findPrevButton.setEnabled(false);
/* 547 */       this._highlightsButton.setEnabled(false);
/* 548 */       select(-1, "", false);
/* 549 */       clearStatus();
/* 550 */       return;
/*     */     }
/* 552 */     int found = this._searchable.findFromCursor(text);
/* 553 */     if (found == -1) {
/* 554 */       select(-1, "", false);
/* 555 */       this._findNextButton.setEnabled(false);
/* 556 */       this._findPrevButton.setEnabled(false);
/* 557 */       this._highlightsButton.setEnabled(false);
/* 558 */       setStatus(getResourceString("SearchableBar.notFound"), getImageIcon("icons/error.png"));
/*     */     }
/*     */     else {
/* 561 */       select(found, text, false);
/* 562 */       this._findNextButton.setEnabled(true);
/* 563 */       this._findPrevButton.setEnabled(true);
/* 564 */       this._highlightsButton.setEnabled(true);
/* 565 */       clearStatus();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void clearStatus() {
/* 570 */     this._statusLabel.setIcon(null);
/* 571 */     this._statusLabel.setText("");
/* 572 */     this._textField.setBackground(UIDefaultsLookup.getColor("TextField.background"));
/* 573 */     hideMessage();
/*     */   }
/*     */ 
/*     */   private void setStatus(String message, Icon icon) {
/* 577 */     this._statusLabel.setIcon(icon);
/* 578 */     this._statusLabel.setText(message);
/* 579 */     this._statusLabel.setToolTipText(message);
/* 580 */     if ((!this._statusLabel.isShowing()) || (this._statusLabel.getWidth() < 25))
/* 581 */       showMessage(message);
/*     */   }
/*     */ 
/*     */   public void focusSearchField()
/*     */   {
/* 589 */     if (this._textField != null)
/* 590 */       this._textField.requestFocus();
/*     */   }
/*     */ 
/*     */   protected void select(int index, String searchingText, boolean incremental)
/*     */   {
/* 595 */     if (index != -1) {
/* 596 */       this._searchable.setSelectedIndex(index, incremental);
/* 597 */       this._searchable.setCursor(index, incremental);
/* 598 */       this._textField.setBackground(UIDefaultsLookup.getColor("TextField.background"));
/*     */     }
/*     */     else {
/* 601 */       this._searchable.setSelectedIndex(-1, false);
/* 602 */       this._textField.setBackground(getMismatchBackground());
/*     */     }
/* 604 */     this._searchable.firePropertyChangeEvent(searchingText);
/* 605 */     if (index != -1) {
/* 606 */       Object element = this._searchable.getElementAt(index);
/* 607 */       this._searchable.fireSearchableEvent(new SearchableEvent(this._searchable, 3002, searchingText, element, this._searchable.convertElementToString(element)));
/*     */     }
/*     */     else {
/* 610 */       this._searchable.fireSearchableEvent(new SearchableEvent(this._searchable, 3003, searchingText));
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getSearchingText()
/*     */   {
/* 620 */     return this._textField != null ? this._textField.getText() : "";
/*     */   }
/*     */ 
/*     */   public void setSearchingText(String searchingText)
/*     */   {
/* 629 */     if (this._textField != null)
/* 630 */       this._textField.setText(searchingText);
/*     */   }
/*     */ 
/*     */   public boolean isPassive()
/*     */   {
/* 640 */     return false;
/*     */   }
/*     */ 
/*     */   public void setMismatchForeground(Color mismatchBackground)
/*     */   {
/* 652 */     this._mismatchBackground = mismatchBackground;
/*     */   }
/*     */ 
/*     */   public Color getMismatchBackground()
/*     */   {
/* 662 */     if (this._mismatchBackground == null) {
/* 663 */       return DEFAULT_MISMATCH_BACKGROUND;
/*     */     }
/*     */ 
/* 666 */     return this._mismatchBackground;
/*     */   }
/*     */ 
/*     */   public Installer getInstaller()
/*     */   {
/* 696 */     return this._installer;
/*     */   }
/*     */ 
/*     */   public void setInstaller(Installer installer)
/*     */   {
/* 705 */     this._installer = installer;
/*     */   }
/*     */ 
/*     */   public static SearchableBar install(Searchable searchable, KeyStroke keyStroke, Installer installer)
/*     */   {
/* 730 */     SearchableBar searchableBar = new SearchableBar(searchable);
/* 731 */     searchableBar.setInstaller(installer);
/* 732 */     ((JComponent)searchable.getComponent()).registerKeyboardAction(new AbstractAction(searchableBar) {
/*     */       private static final long serialVersionUID = 8328919754409621715L;
/*     */ 
/* 736 */       public void actionPerformed(ActionEvent e) { this.val$searchableBar.getInstaller().openSearchBar(this.val$searchableBar);
/* 737 */         this.val$searchableBar.focusSearchField();
/*     */       }
/*     */     }
/*     */     , keyStroke, 1);
/*     */ 
/* 740 */     return searchableBar;
/*     */   }
/*     */ 
/*     */   public void processKeyEvent(KeyEvent e)
/*     */   {
/*     */   }
/*     */ 
/*     */   public int getVisibleButtons() {
/* 748 */     return this._visibleButtons;
/*     */   }
/*     */ 
/*     */   public void setVisibleButtons(int visibleButtons)
/*     */   {
/* 763 */     this._visibleButtons = visibleButtons;
/* 764 */     removeAll();
/* 765 */     installComponents();
/* 766 */     revalidate();
/* 767 */     repaint();
/*     */   }
/*     */ 
/*     */   public boolean isCompact()
/*     */   {
/* 776 */     return this._compact;
/*     */   }
/*     */ 
/*     */   public void setCompact(boolean compact)
/*     */   {
/* 786 */     this._compact = compact;
/* 787 */     this._findNextButton.setText(this._compact ? "" : getResourceString("SearchableBar.findNext"));
/* 788 */     this._highlightsButton.setText(this._compact ? "" : getResourceString("SearchableBar.highlights"));
/* 789 */     this._findPrevButton.setText(this._compact ? "" : getResourceString("SearchableBar.findPrevious"));
/*     */   }
/*     */ 
/*     */   protected ImageIcon getImageIcon(String name)
/*     */   {
/* 800 */     return SearchableBarIconsFactory.getImageIcon(name);
/*     */   }
/*     */ 
/*     */   protected String getResourceString(String key)
/*     */   {
/* 811 */     return Resource.getResourceBundle(Locale.getDefault()).getString(key);
/*     */   }
/*     */ 
/*     */   private void showMessage(String message) {
/* 815 */     hideMessage();
/*     */ 
/* 817 */     this._messagePopup = JidePopupFactory.getSharedInstance().createPopup();
/* 818 */     JLabel label = new JLabel(message);
/* 819 */     label.setOpaque(true);
/* 820 */     label.setFont(UIDefaultsLookup.getFont("Label.font").deriveFont(1, 11.0F));
/* 821 */     label.setBackground(new Color(253, 254, 226));
/* 822 */     label.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
/* 823 */     label.setForeground(UIDefaultsLookup.getColor("ToolTip.foreground"));
/*     */ 
/* 825 */     this._messagePopup.getContentPane().setLayout(new BorderLayout());
/* 826 */     this._messagePopup.getContentPane().add(label);
/* 827 */     this._messagePopup.setOwner(this._textField);
/*     */ 
/* 829 */     this._messagePopup.setDefaultMoveOperation(0);
/* 830 */     this._messagePopup.setTransient(true);
/* 831 */     this._messagePopup.showPopup();
/*     */ 
/* 833 */     addMouseMotionListener(this._mouseMotionListener);
/* 834 */     this._textField.addKeyListener(this._keyListener);
/*     */   }
/*     */ 
/*     */   private void hideMessage() {
/* 838 */     if (this._messagePopup != null) {
/* 839 */       this._messagePopup.hidePopupImmediately();
/* 840 */       this._messagePopup = null;
/*     */     }
/* 842 */     if (this._mouseMotionListener != null) {
/* 843 */       removeMouseMotionListener(this._mouseMotionListener);
/*     */     }
/* 845 */     if (this._keyListener != null)
/* 846 */       this._textField.removeKeyListener(this._keyListener);
/*     */   }
/*     */ 
/*     */   public static abstract interface Installer
/*     */   {
/*     */     public abstract void openSearchBar(SearchableBar paramSearchableBar);
/*     */ 
/*     */     public abstract void closeSearchBar(SearchableBar paramSearchableBar);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.SearchableBar
 * JD-Core Version:    0.6.0
 */
/*     */ package com.jidesoft.hints;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.popup.JidePopup;
/*     */ import com.jidesoft.popup.JidePopupFactory;
/*     */ import com.jidesoft.swing.DelegateAction;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.KeyListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.ActionMap;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.InputMap;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JEditorPane;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.Timer;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import javax.swing.event.DocumentListener;
/*     */ import javax.swing.event.PopupMenuEvent;
/*     */ import javax.swing.event.PopupMenuListener;
/*     */ import javax.swing.plaf.TextUI;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.Caret;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.JTextComponent;
/*     */ 
/*     */ public abstract class AbstractIntelliHints
/*     */   implements IntelliHints
/*     */ {
/*     */   private JidePopup _popup;
/*     */   private JTextComponent _textComponent;
/*     */   private JComponent _hintsComponent;
/*  37 */   private boolean _followCaret = false;
/*     */ 
/*  40 */   private boolean _keyTyped = false;
/*     */ 
/*  44 */   private boolean _autoPopup = true;
/*  45 */   private int _showHintsDelay = 200;
/*     */ 
/* 443 */   private DelegateAction acceptAction = new DelegateAction() {
/*     */     private static final long serialVersionUID = -2516216121942080133L;
/*     */ 
/*     */     public boolean isDelegateEnabled() {
/* 448 */       return (AbstractIntelliHints.this.isHintsPopupVisible()) && (AbstractIntelliHints.this.getSelectedHint() != null);
/*     */     }
/*     */ 
/*     */     public boolean delegateActionPerformed(ActionEvent e)
/*     */     {
/* 453 */       JComponent tf = (JComponent)e.getSource();
/* 454 */       IntelliHints hints = AbstractIntelliHints.getIntelliHints(tf);
/* 455 */       if ((hints instanceof AbstractIntelliHints)) {
/* 456 */         AbstractIntelliHints aih = (AbstractIntelliHints)hints;
/* 457 */         aih.hideHintsPopup();
/* 458 */         if (aih.getSelectedHint() != null) {
/* 459 */           aih.setHintsEnabled(false);
/* 460 */           aih.acceptHint(hints.getSelectedHint());
/* 461 */           aih.setHintsEnabled(true);
/* 462 */           return true;
/*     */         }
/* 464 */         if (AbstractIntelliHints.this.getTextComponent().getRootPane() != null) {
/* 465 */           JButton button = AbstractIntelliHints.this.getTextComponent().getRootPane().getDefaultButton();
/* 466 */           if (button != null) {
/* 467 */             button.doClick();
/* 468 */             return true;
/*     */           }
/*     */         }
/*     */       }
/* 472 */       return false;
/*     */     }
/* 443 */   };
/*     */ 
/* 476 */   private DelegateAction hideAction = new DelegateAction() {
/*     */     private static final long serialVersionUID = 1921213578011852535L;
/*     */ 
/*     */     public boolean isDelegateEnabled() {
/* 481 */       return (AbstractIntelliHints.this._textComponent.isEnabled()) && (AbstractIntelliHints.this.isHintsPopupVisible());
/*     */     }
/*     */ 
/*     */     public boolean delegateActionPerformed(ActionEvent e)
/*     */     {
/* 486 */       if (isEnabled()) {
/* 487 */         AbstractIntelliHints.this.hideHintsPopup();
/* 488 */         return true;
/*     */       }
/* 490 */       return false;
/*     */     }
/* 476 */   };
/*     */ 
/* 494 */   private DocumentListener documentListener = new DocumentListener() {
/* 495 */     private Timer timer = new Timer(AbstractIntelliHints.this.getShowHintsDelay(), new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 497 */         if (AbstractIntelliHints.this.isKeyTyped()) {
/* 498 */           if ((AbstractIntelliHints.this.isHintsPopupVisible()) || (AbstractIntelliHints.this.isAutoPopup())) {
/* 499 */             AbstractIntelliHints.this.showHintsPopup();
/*     */           }
/* 501 */           AbstractIntelliHints.this.setKeyTyped(false);
/*     */         }
/*     */       }
/*     */     });
/*     */ 
/*     */     public void insertUpdate(DocumentEvent e)
/*     */     {
/* 507 */       startTimer();
/*     */     }
/*     */ 
/*     */     public void removeUpdate(DocumentEvent e) {
/* 511 */       startTimer();
/*     */     }
/*     */ 
/*     */     public void changedUpdate(DocumentEvent e) {
/*     */     }
/*     */ 
/*     */     void startTimer() {
/* 518 */       if (this.timer.isRunning()) {
/* 519 */         this.timer.restart();
/*     */       }
/*     */       else {
/* 522 */         this.timer.setRepeats(false);
/* 523 */         this.timer.start();
/*     */       }
/*     */     }
/* 494 */   };
/*     */ 
/*     */   public AbstractIntelliHints(JTextComponent textComponent)
/*     */   {
/*  53 */     this._textComponent = textComponent;
/*  54 */     getTextComponent().putClientProperty("INTELLI_HINTS", this);
/*     */ 
/*  56 */     this._popup = createPopup();
/*     */ 
/*  58 */     getTextComponent().getDocument().addDocumentListener(this.documentListener);
/*  59 */     getTextComponent().addKeyListener(new KeyListener() {
/*     */       public void keyTyped(KeyEvent e) {
/*     */       }
/*     */ 
/*     */       public void keyPressed(KeyEvent e) {
/*     */       }
/*     */ 
/*     */       public void keyReleased(KeyEvent e) {
/*  67 */         if ((27 != e.getKeyCode()) && (10 != e.getKeyCode()))
/*  68 */           AbstractIntelliHints.this.setKeyTyped(true);
/*     */       }
/*     */     });
/*  72 */     getTextComponent().addFocusListener(new FocusListener() {
/*     */       public void focusGained(FocusEvent e) {
/*     */       }
/*     */ 
/*     */       public void focusLost(FocusEvent e) {
/*  77 */         Container topLevelAncestor = AbstractIntelliHints.this._popup.getTopLevelAncestor();
/*  78 */         if (topLevelAncestor == null) {
/*  79 */           return;
/*     */         }
/*  81 */         Component oppositeComponent = e.getOppositeComponent();
/*  82 */         if ((topLevelAncestor == oppositeComponent) || (topLevelAncestor.isAncestorOf(oppositeComponent))) {
/*  83 */           return;
/*     */         }
/*  85 */         AbstractIntelliHints.this.hideHintsPopup();
/*     */       }
/*     */     });
/*  89 */     DelegateAction.replaceAction(getTextComponent(), 0, getShowHintsKeyStroke(), new DelegateAction() {
/*     */       private static final long serialVersionUID = 2243999895981912016L;
/*     */ 
/*     */       public boolean delegateActionPerformed(ActionEvent e) {
/*  94 */         JComponent tf = (JComponent)e.getSource();
/*  95 */         IntelliHints hints = AbstractIntelliHints.getIntelliHints(tf);
/*  96 */         if ((hints instanceof AbstractIntelliHints)) {
/*  97 */           AbstractIntelliHints aih = (AbstractIntelliHints)hints;
/*  98 */           if ((tf.isEnabled()) && (!aih.isHintsPopupVisible())) {
/*  99 */             aih.showHintsPopup();
/* 100 */             return true;
/*     */           }
/*     */         }
/* 103 */         return false;
/*     */       }
/*     */ 
/*     */       public boolean isDelegateEnabled()
/*     */       {
/* 108 */         return !AbstractIntelliHints.this.isHintsPopupVisible();
/*     */       }
/*     */     });
/* 112 */     KeyStroke[] keyStrokes = getDelegateKeyStrokes();
/* 113 */     for (KeyStroke keyStroke : keyStrokes)
/* 114 */       DelegateAction.replaceAction(getTextComponent(), 0, keyStroke, new LazyDelegateAction(keyStroke));
/*     */   }
/*     */ 
/*     */   protected JidePopup createPopup()
/*     */   {
/* 119 */     JidePopup popup = JidePopupFactory.getSharedInstance().createPopup();
/* 120 */     popup.setLayout(new BorderLayout());
/* 121 */     popup.setResizable(true);
/* 122 */     popup.setPopupBorder(BorderFactory.createLineBorder(UIDefaultsLookup.getColor("controlDkShadow"), 1));
/* 123 */     popup.setMovable(false);
/* 124 */     popup.addPopupMenuListener(new PopupMenuListener() {
/*     */       public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
/*     */       }
/*     */ 
/*     */       public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
/* 129 */         DelegateAction.restoreAction(AbstractIntelliHints.this.getTextComponent(), 0, KeyStroke.getKeyStroke(27, 0), AbstractIntelliHints.this.hideAction);
/* 130 */         DelegateAction.restoreAction(AbstractIntelliHints.this.getTextComponent(), 0, KeyStroke.getKeyStroke(10, 0), AbstractIntelliHints.this.acceptAction);
/*     */       }
/*     */ 
/*     */       public void popupMenuCanceled(PopupMenuEvent e)
/*     */       {
/*     */       }
/*     */     });
/* 136 */     popup.setTransient(true);
/* 137 */     popup.setKeepPreviousSize(false);
/* 138 */     return popup;
/*     */   }
/*     */ 
/*     */   public JTextComponent getTextComponent() {
/* 142 */     return this._textComponent;
/*     */   }
/*     */ 
/*     */   public void acceptHint(Object selected)
/*     */   {
/* 156 */     if (selected == null) {
/* 157 */       return;
/*     */     }
/*     */ 
/* 160 */     int pos = getTextComponent().getCaretPosition();
/*     */     String newText;
/*     */     String newText;
/* 161 */     if (isMultilineTextComponent()) {
/* 162 */       String text = getTextComponent().getText();
/* 163 */       int start = text.lastIndexOf('\n', pos - 1);
/* 164 */       String remain = pos == -1 ? "" : text.substring(pos);
/* 165 */       text = text.substring(0, start + 1);
/* 166 */       text = text + selected;
/* 167 */       text = text + remain;
/* 168 */       newText = text;
/*     */     }
/*     */     else {
/* 171 */       newText = selected.toString();
/*     */     }
/*     */ 
/* 174 */     getTextComponent().setText(newText);
/*     */ 
/* 177 */     String actualText = getTextComponent().getText();
/* 178 */     int separatorIndex = actualText.indexOf('\n', pos);
/* 179 */     getTextComponent().setCaretPosition(separatorIndex == -1 ? actualText.length() : separatorIndex);
/*     */   }
/*     */ 
/*     */   protected boolean isMultilineTextComponent()
/*     */   {
/* 189 */     return ((getTextComponent() instanceof JTextArea)) || ((getTextComponent() instanceof JEditorPane));
/*     */   }
/*     */ 
/*     */   protected void showHintsPopup()
/*     */   {
/* 197 */     if ((!getTextComponent().isEnabled()) || (!getTextComponent().hasFocus())) {
/* 198 */       return;
/*     */     }
/* 200 */     showHints();
/*     */   }
/*     */ 
/*     */   public void showHints()
/*     */   {
/* 208 */     if (this._popup == null) {
/* 209 */       return;
/*     */     }
/* 211 */     if (this._hintsComponent == null) {
/* 212 */       this._hintsComponent = createHintsComponent();
/* 213 */       this._popup.add(this._hintsComponent);
/* 214 */       getDelegateComponent().setRequestFocusEnabled(false);
/* 215 */       getDelegateComponent().addMouseListener(new MouseAdapter()
/*     */       {
/*     */         public void mouseClicked(MouseEvent e) {
/* 218 */           AbstractIntelliHints.this.hideHintsPopup();
/* 219 */           AbstractIntelliHints.this.setHintsEnabled(false);
/* 220 */           AbstractIntelliHints.this.acceptHint(AbstractIntelliHints.this.getSelectedHint());
/* 221 */           AbstractIntelliHints.this.setHintsEnabled(true);
/*     */         } } );
/*     */     }
/* 225 */     if (updateHints(getContext())) {
/* 226 */       DelegateAction.replaceAction(getTextComponent(), 0, KeyStroke.getKeyStroke(27, 0), this.hideAction);
/* 227 */       DelegateAction.replaceAction(getTextComponent(), 0, KeyStroke.getKeyStroke(10, 0), this.acceptAction, true);
/*     */ 
/* 229 */       int x = 0;
/* 230 */       int y = 0;
/* 231 */       int height = 0;
/*     */       try
/*     */       {
/* 234 */         int pos = getCaretPositionForPopup();
/* 235 */         Rectangle position = getCaretRectangleForPopup(pos);
/* 236 */         y = position.y;
/* 237 */         x = position.x;
/* 238 */         height = position.height;
/*     */       }
/*     */       catch (BadLocationException e)
/*     */       {
/*     */       }
/*     */ 
/* 244 */       this._popup.setOwner(getTextComponent());
/* 245 */       this._popup.showPopup(new Insets(y, x, getTextComponent().getHeight() - height - y, 0));
/*     */     }
/*     */     else {
/* 248 */       this._popup.hidePopup();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Rectangle getCaretRectangleForPopup(int caretPosition)
/*     */     throws BadLocationException
/*     */   {
/* 269 */     return getTextComponent().getUI().modelToView(getTextComponent(), caretPosition);
/*     */   }
/*     */ 
/*     */   protected int getCaretPositionForPopup()
/*     */   {
/* 281 */     int caretPosition = Math.min(getTextComponent().getCaret().getDot(), getTextComponent().getCaret().getMark());
/* 282 */     if (isFollowCaret()) {
/* 283 */       return caretPosition;
/*     */     }
/*     */     try
/*     */     {
/* 287 */       Rectangle viewRect = getTextComponent().getUI().modelToView(getTextComponent(), caretPosition);
/* 288 */       viewRect.x = 0;
/* 289 */       return getTextComponent().getUI().viewToModel(getTextComponent(), viewRect.getLocation());
/*     */     } catch (BadLocationException e) {
/*     */     }
/* 292 */     return 0;
/*     */   }
/*     */ 
/*     */   protected Object getContext()
/*     */   {
/* 308 */     if (isMultilineTextComponent()) {
/* 309 */       int pos = getTextComponent().getCaretPosition();
/* 310 */       if (pos == 0) {
/* 311 */         return "";
/*     */       }
/*     */ 
/* 314 */       String text = getTextComponent().getText();
/* 315 */       int start = text.lastIndexOf('\n', pos - 1);
/* 316 */       return text.substring(start + 1, pos);
/*     */     }
/*     */ 
/* 320 */     return getTextComponent().getText();
/*     */   }
/*     */ 
/*     */   protected void hideHintsPopup()
/*     */   {
/* 328 */     if (this._popup != null) {
/* 329 */       this._popup.hidePopup();
/*     */     }
/* 331 */     setKeyTyped(false);
/*     */   }
/*     */ 
/*     */   public void setHintsEnabled(boolean enabled)
/*     */   {
/* 340 */     if (!enabled)
/*     */     {
/* 342 */       getTextComponent().getDocument().removeDocumentListener(this.documentListener);
/*     */     }
/*     */     else
/*     */     {
/* 346 */       getTextComponent().getDocument().addDocumentListener(this.documentListener);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isHintsPopupVisible()
/*     */   {
/* 357 */     return (this._popup != null) && (this._popup.isPopupVisible());
/*     */   }
/*     */ 
/*     */   public boolean isFollowCaret()
/*     */   {
/* 367 */     return this._followCaret;
/*     */   }
/*     */ 
/*     */   public void setFollowCaret(boolean followCaret)
/*     */   {
/* 378 */     this._followCaret = followCaret;
/*     */   }
/*     */ 
/*     */   public boolean isAutoPopup()
/*     */   {
/* 389 */     return this._autoPopup;
/*     */   }
/*     */ 
/*     */   public void setAutoPopup(boolean autoPopup)
/*     */   {
/* 400 */     this._autoPopup = autoPopup;
/*     */   }
/*     */ 
/*     */   protected abstract KeyStroke[] getDelegateKeyStrokes();
/*     */ 
/*     */   protected abstract JComponent getDelegateComponent();
/*     */ 
/*     */   protected KeyStroke getShowHintsKeyStroke()
/*     */   {
/* 435 */     if (isMultilineTextComponent()) {
/* 436 */       return KeyStroke.getKeyStroke(32, 2);
/*     */     }
/*     */ 
/* 439 */     return KeyStroke.getKeyStroke(40, 0);
/*     */   }
/*     */ 
/*     */   private boolean isKeyTyped()
/*     */   {
/* 529 */     return this._keyTyped;
/*     */   }
/*     */ 
/*     */   private void setKeyTyped(boolean keyTyped) {
/* 533 */     this._keyTyped = keyTyped;
/*     */   }
/*     */ 
/*     */   public int getShowHintsDelay()
/*     */   {
/* 543 */     return this._showHintsDelay;
/*     */   }
/*     */ 
/*     */   public void setShowHintsDelay(int showHintsDelay)
/*     */   {
/* 554 */     this._showHintsDelay = showHintsDelay;
/*     */   }
/*     */ 
/*     */   public static IntelliHints getIntelliHints(JComponent component)
/*     */   {
/* 613 */     return (IntelliHints)component.getClientProperty("INTELLI_HINTS");
/*     */   }
/*     */ 
/*     */   private class LazyDelegateAction extends DelegateAction
/*     */   {
/*     */     private KeyStroke _keyStroke;
/*     */     private static final long serialVersionUID = -5799290233797844786L;
/*     */ 
/*     */     public LazyDelegateAction(KeyStroke keyStroke)
/*     */     {
/* 562 */       this._keyStroke = keyStroke;
/*     */     }
/*     */ 
/*     */     public boolean isDelegateEnabled()
/*     */     {
/* 567 */       Action action = getHintsPopupAction();
/* 568 */       return (action != null) && (action.isEnabled());
/*     */     }
/*     */ 
/*     */     private Action getHintsPopupAction() {
/* 572 */       if ((AbstractIntelliHints.this.isHintsPopupVisible()) && (AbstractIntelliHints.this.getDelegateComponent() != null)) {
/* 573 */         Object key = AbstractIntelliHints.this.getDelegateComponent().getInputMap().get(this._keyStroke);
/* 574 */         key = key == null ? AbstractIntelliHints.this.getTextComponent().getInputMap(1).get(this._keyStroke) : key;
/* 575 */         if (key != null) {
/* 576 */           return AbstractIntelliHints.this.getDelegateComponent().getActionMap().get(key);
/*     */         }
/*     */       }
/* 579 */       return null;
/*     */     }
/*     */ 
/*     */     public boolean delegateActionPerformed(ActionEvent e)
/*     */     {
/* 584 */       JComponent tf = (JComponent)e.getSource();
/* 585 */       IntelliHints hints = AbstractIntelliHints.getIntelliHints(tf);
/* 586 */       if ((hints instanceof AbstractIntelliHints)) {
/* 587 */         AbstractIntelliHints aih = (AbstractIntelliHints)hints;
/* 588 */         if ((tf.isEnabled()) && 
/* 589 */           (aih.isHintsPopupVisible())) {
/* 590 */           Object key = aih.getDelegateComponent().getInputMap().get(this._keyStroke);
/* 591 */           key = key == null ? aih.getTextComponent().getInputMap(1).get(this._keyStroke) : key;
/* 592 */           if (key != null) {
/* 593 */             Object action = aih.getDelegateComponent().getActionMap().get(key);
/* 594 */             if ((action instanceof Action)) {
/* 595 */               ((Action)action).actionPerformed(new ActionEvent(aih.getDelegateComponent(), 0, "" + key));
/* 596 */               return true;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 602 */       return false;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.hints.AbstractIntelliHints
 * JD-Core Version:    0.6.0
 */
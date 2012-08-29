/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.utils.PortingUtils;
/*     */ import com.jidesoft.utils.SystemInfo;
/*     */ import java.awt.event.KeyAdapter;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.KeyListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.List;
/*     */ import javax.swing.ComboBoxEditor;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.ListModel;
/*     */ import javax.swing.ListSelectionModel;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ import javax.swing.event.TreeSelectionEvent;
/*     */ import javax.swing.event.TreeSelectionListener;
/*     */ import javax.swing.text.AbstractDocument;
/*     */ import javax.swing.text.AttributeSet;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import javax.swing.text.PlainDocument;
/*     */ import javax.swing.tree.TreePath;
/*     */ import javax.swing.tree.TreeSelectionModel;
/*     */ 
/*     */ public class AutoCompletion
/*     */ {
/*     */   private Searchable _searchable;
/*     */   private JTextComponent _textComponent;
/*     */   private AutoCompletionDocument _document;
/*  55 */   private boolean _selecting = false;
/*     */   private boolean _hidePopupOnFocusLoss;
/*  59 */   private boolean _keyTyped = false;
/*  60 */   private boolean _hitBackspace = false;
/*     */   private boolean _hitBackspaceOnSelection;
/*     */   private KeyListener _editorKeyListener;
/*  66 */   private boolean _strict = true;
/*  67 */   private boolean _strictCompletion = true;
/*     */   private PropertyChangeListener _propertyChangeListener;
/*     */   private JComboBox _comboBox;
/*     */   private Document _oldDocument;
/*     */   public static final String CLIENT_PROPERTY_AUTO_COMPLETION = "AutoCompletion";
/*     */ 
/*     */   public AutoCompletion(JComboBox comboBox)
/*     */   {
/*  79 */     this(comboBox, new ComboBoxSearchable(comboBox));
/*     */   }
/*     */ 
/*     */   public AutoCompletion(JComboBox comboBox, Searchable searchable) {
/*  83 */     this._searchable = searchable;
/*  84 */     this._propertyChangeListener = new PropertyChangeListener() {
/*     */       public void propertyChange(PropertyChangeEvent e) {
/*  86 */         if (("editor".equals(e.getPropertyName())) && 
/*  87 */           (e.getNewValue() != null)) {
/*  88 */           AutoCompletion.access$002(AutoCompletion.this, (JTextComponent)((ComboBoxEditor)e.getNewValue()).getEditorComponent());
/*  89 */           AutoCompletion.this.configureEditor(AutoCompletion.this.getTextComponent());
/*     */         }
/*     */       }
/*     */     };
/*  94 */     this._comboBox = comboBox;
/*  95 */     this._searchable.setWildcardEnabled(false);
/*  96 */     if ((this._searchable instanceof ComboBoxSearchable)) {
/*  97 */       ((ComboBoxSearchable)this._searchable).setShowPopupDuringSearching(false);
/*     */     }
/*  99 */     this._textComponent = ((JTextComponent)comboBox.getEditor().getEditorComponent());
/* 100 */     installListeners();
/*     */   }
/*     */ 
/*     */   public AutoCompletion(JTextComponent textComponent, Searchable searchable) {
/* 104 */     this._searchable = searchable;
/* 105 */     this._searchable.setWildcardEnabled(false);
/* 106 */     this._textComponent = textComponent;
/* 107 */     registerSelectionListener(getSearchable());
/*     */ 
/* 109 */     installListeners();
/*     */   }
/*     */ 
/*     */   public AutoCompletion(JTextComponent textComponent, List list) {
/* 113 */     this(textComponent, new Searchable(list) {
/* 114 */       int _selectIndex = -1;
/*     */ 
/*     */       protected int getSelectedIndex()
/*     */       {
/* 118 */         return this._selectIndex;
/*     */       }
/*     */ 
/*     */       protected void setSelectedIndex(int index, boolean incremental)
/*     */       {
/* 123 */         this._selectIndex = index;
/*     */       }
/*     */ 
/*     */       protected int getElementCount()
/*     */       {
/* 128 */         return this.val$list.size();
/*     */       }
/*     */ 
/*     */       protected Object getElementAt(int index)
/*     */       {
/* 133 */         return this.val$list.get(index);
/*     */       }
/*     */ 
/*     */       protected String convertElementToString(Object element)
/*     */       {
/* 138 */         return "" + element;
/*     */       } } );
/*     */   }
/*     */ 
/*     */   public AutoCompletion(JTextComponent textComponent, Object[] array) {
/* 144 */     this(textComponent, new Searchable(array) {
/* 145 */       int _selectIndex = -1;
/*     */ 
/*     */       protected int getSelectedIndex()
/*     */       {
/* 149 */         return this._selectIndex;
/*     */       }
/*     */ 
/*     */       protected void setSelectedIndex(int index, boolean incremental)
/*     */       {
/* 154 */         this._selectIndex = index;
/*     */       }
/*     */ 
/*     */       protected int getElementCount()
/*     */       {
/* 159 */         return this.val$array.length;
/*     */       }
/*     */ 
/*     */       protected Object getElementAt(int index)
/*     */       {
/* 164 */         return this.val$array[index];
/*     */       }
/*     */ 
/*     */       protected String convertElementToString(Object element)
/*     */       {
/* 169 */         return "" + element;
/*     */       } } );
/*     */   }
/*     */ 
/*     */   private void registerSelectionListener(Searchable searchable) {
/* 175 */     if ((searchable.getComponent() instanceof JList)) {
/* 176 */       JList list = (JList)getSearchable().getComponent();
/* 177 */       list.getSelectionModel().addListSelectionListener(new ListSelectionListener(list) {
/*     */         public void valueChanged(ListSelectionEvent e) {
/* 179 */           int index = this.val$list.getSelectedIndex();
/* 180 */           if (index != -1) {
/* 181 */             AutoCompletion.this.getTextComponent().setText(AutoCompletion.this.getSearchable().convertElementToString(this.val$list.getModel().getElementAt(index)));
/* 182 */             AutoCompletion.this.highlightCompletedText(0);
/*     */           }
/*     */         }
/*     */       });
/* 186 */       DelegateAction.replaceAction(getTextComponent(), 1, list, 0, KeyStroke.getKeyStroke(38, 0));
/* 187 */       DelegateAction.replaceAction(getTextComponent(), 1, list, 0, KeyStroke.getKeyStroke(40, 0));
/* 188 */       DelegateAction.replaceAction(getTextComponent(), 1, list, 0, KeyStroke.getKeyStroke(33, 0));
/* 189 */       DelegateAction.replaceAction(getTextComponent(), 1, list, 0, KeyStroke.getKeyStroke(34, 0));
/*     */     }
/* 191 */     else if ((searchable.getComponent() instanceof JTree)) {
/* 192 */       JTree tree = (JTree)getSearchable().getComponent();
/* 193 */       tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener(tree) {
/*     */         public void valueChanged(TreeSelectionEvent e) {
/* 195 */           TreePath treePath = this.val$tree.getSelectionPath();
/* 196 */           if (treePath != null) {
/* 197 */             AutoCompletion.this.getTextComponent().setText("" + treePath.getLastPathComponent());
/* 198 */             AutoCompletion.this.highlightCompletedText(0);
/*     */           }
/*     */         }
/*     */       });
/* 202 */       DelegateAction.replaceAction(getTextComponent(), 1, tree, 0, KeyStroke.getKeyStroke(38, 0));
/* 203 */       DelegateAction.replaceAction(getTextComponent(), 1, tree, 0, KeyStroke.getKeyStroke(40, 0));
/* 204 */       DelegateAction.replaceAction(getTextComponent(), 1, tree, 0, KeyStroke.getKeyStroke(33, 0));
/* 205 */       DelegateAction.replaceAction(getTextComponent(), 1, tree, 0, KeyStroke.getKeyStroke(34, 0));
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isKeyTyped() {
/* 210 */     return this._keyTyped;
/*     */   }
/*     */ 
/*     */   private void setKeyTyped(boolean keyTyped) {
/* 214 */     this._keyTyped = keyTyped;
/*     */   }
/*     */ 
/*     */   private void setInitValue() {
/* 218 */     int index = getSearchable().getSelectedIndex();
/* 219 */     if (index != -1) {
/* 220 */       Object selected = getSearchable().getElementAt(index);
/* 221 */       if (selected != null)
/* 222 */         this._document.setText(getSearchable().convertElementToString(selected));
/* 223 */       highlightCompletedText(0);
/*     */     }
/*     */     else {
/* 226 */       this._document.setText("");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void uninstallListeners()
/*     */   {
/* 234 */     if ((this._propertyChangeListener != null) && (this._comboBox != null)) {
/* 235 */       this._comboBox.removePropertyChangeListener(this._propertyChangeListener);
/*     */     }
/*     */ 
/* 238 */     if (getTextComponent() != null) {
/* 239 */       getTextComponent().removeKeyListener(this._editorKeyListener);
/*     */ 
/* 241 */       String text = getTextComponent().getText();
/* 242 */       if (this._oldDocument != null) {
/* 243 */         getTextComponent().setDocument(this._oldDocument);
/* 244 */         this._oldDocument = null;
/*     */       }
/* 246 */       getTextComponent().setText(text);
/*     */     }
/* 248 */     getTextComponent().putClientProperty("AutoCompletion", null);
/*     */   }
/*     */ 
/*     */   public void installListeners()
/*     */   {
/* 257 */     if ((this._comboBox != null) && (this._propertyChangeListener != null)) {
/* 258 */       this._comboBox.addPropertyChangeListener(this._propertyChangeListener);
/*     */     }
/*     */ 
/* 261 */     this._editorKeyListener = new KeyAdapter() {
/*     */       private boolean _deletePressed;
/*     */       private String _saveText;
/*     */ 
/* 267 */       public void keyPressed(KeyEvent e) { AutoCompletion.access$302(AutoCompletion.this, false);
/*     */ 
/* 269 */         if (27 != e.getKeyCode()) {
/* 270 */           AutoCompletion.this.setKeyTyped(true);
/*     */         }
/*     */ 
/* 273 */         switch (e.getKeyCode())
/*     */         {
/*     */         case 8:
/* 276 */           if (!AutoCompletion.this.isStrict()) break;
/* 277 */           AutoCompletion.access$302(AutoCompletion.this, true);
/* 278 */           AutoCompletion.access$502(AutoCompletion.this, AutoCompletion.this.getTextComponent().getSelectionStart() != AutoCompletion.this.getTextComponent().getSelectionEnd()); break;
/*     */         case 127:
/* 283 */           if (!AutoCompletion.this.isStrict()) break;
/* 284 */           this._deletePressed = true;
/* 285 */           this._saveText = AutoCompletion.this.getTextComponent().getText();
/*     */         }
/*     */       }
/*     */ 
/*     */       public void keyReleased(KeyEvent e)
/*     */       {
/* 293 */         super.keyReleased(e);
/*     */         try
/*     */         {
/* 296 */           if (this._deletePressed) {
/* 297 */             this._deletePressed = false;
/* 298 */             String text = AutoCompletion.this.getTextComponent().getText();
/* 299 */             int index = AutoCompletion.this.getSearchable().findFirst(text);
/* 300 */             if (index != -1) {
/* 301 */               if (text.length() == 0) {
/* 302 */                 AutoCompletion.this.setSelectedItem(null);
/*     */               }
/*     */               else {
/* 305 */                 Object item = AutoCompletion.this.getSearchable().getElementAt(index);
/* 306 */                 AutoCompletion.this.setSelectedItem(item);
/* 307 */                 AutoCompletion.this.getTextComponent().setText(AutoCompletion.this.getSearchable().convertElementToString(item));
/*     */ 
/* 309 */                 AutoCompletion.this.highlightCompletedText(text.length());
/*     */               }
/*     */ 
/*     */             }
/* 313 */             else if (AutoCompletion.this.isStrict()) {
/* 314 */               AutoCompletion.this.getTextComponent().setText(this._saveText);
/* 315 */               e.consume();
/* 316 */               PortingUtils.notifyUser(AutoCompletion.this._textComponent);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 322 */           if (27 != e.getKeyCode())
/* 323 */             AutoCompletion.this.setKeyTyped(false);
/*     */         }
/*     */         finally
/*     */         {
/* 322 */           if (27 != e.getKeyCode())
/* 323 */             AutoCompletion.this.setKeyTyped(false);
/*     */         }
/*     */       }
/*     */     };
/* 329 */     this._hidePopupOnFocusLoss = SystemInfo.isJdk15Above();
/*     */ 
/* 344 */     this._document = createDocument();
/* 345 */     configureEditor(getTextComponent());
/* 346 */     getTextComponent().putClientProperty("AutoCompletion", this);
/*     */   }
/*     */ 
/*     */   protected AutoCompletionDocument createDocument()
/*     */   {
/* 355 */     return new AutoCompletionDocument();
/*     */   }
/*     */ 
/*     */   private void configureEditor(JTextComponent textComponent) {
/* 359 */     if (getTextComponent() != null) {
/* 360 */       getTextComponent().removeKeyListener(this._editorKeyListener);
/*     */     }
/*     */ 
/* 364 */     if (textComponent != null) {
/* 365 */       this._textComponent = textComponent;
/* 366 */       getTextComponent().addKeyListener(this._editorKeyListener);
/*     */ 
/* 368 */       String text = getTextComponent().getText();
/* 369 */       this._oldDocument = getTextComponent().getDocument();
/* 370 */       if (((this._oldDocument instanceof AbstractDocument)) && (this._document != null)) {
/* 371 */         this._document.setDocumentFilter(((AbstractDocument)this._oldDocument).getDocumentFilter());
/*     */       }
/* 373 */       getTextComponent().setDocument(this._document);
/* 374 */       getTextComponent().setText(text);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void highlightCompletedText(int start)
/*     */   {
/* 478 */     int length = getTextComponent().getDocument().getLength();
/* 479 */     getTextComponent().setCaretPosition(length);
/* 480 */     if (start < 0) {
/* 481 */       start = 0;
/*     */     }
/* 483 */     if (start > length) {
/* 484 */       start = length;
/*     */     }
/* 486 */     getTextComponent().moveCaretPosition(start);
/*     */   }
/*     */ 
/*     */   private void setSelectedItem(Object item) {
/* 490 */     this._selecting = true;
/* 491 */     int i = 0; for (int n = getSearchable().getElementCount(); i < n; i++) {
/* 492 */       Object currentItem = getSearchable().getElementAt(i);
/*     */ 
/* 494 */       if (item == currentItem) {
/* 495 */         getSearchable().setSelectedIndex(i, false);
/*     */       }
/*     */     }
/* 498 */     this._selecting = false;
/*     */   }
/*     */ 
/*     */   public boolean isStrict()
/*     */   {
/* 507 */     return this._strict;
/*     */   }
/*     */ 
/*     */   public void setStrict(boolean strict)
/*     */   {
/* 518 */     this._strict = strict;
/*     */   }
/*     */ 
/*     */   public boolean isStrictCompletion()
/*     */   {
/* 529 */     return this._strictCompletion;
/*     */   }
/*     */ 
/*     */   public void setStrictCompletion(boolean strictCompletion)
/*     */   {
/* 541 */     this._strictCompletion = strictCompletion;
/*     */   }
/*     */ 
/*     */   protected JTextComponent getTextComponent()
/*     */   {
/* 550 */     return this._textComponent;
/*     */   }
/*     */ 
/*     */   public Searchable getSearchable()
/*     */   {
/* 561 */     return this._searchable;
/*     */   }
/*     */ 
/*     */   public static AutoCompletion getAutoCompletion(JComponent component)
/*     */   {
/* 572 */     if (component == null) return null;
/* 573 */     Object clientProperty = component.getClientProperty("AutoCompletion");
/* 574 */     if ((clientProperty instanceof AutoCompletion)) {
/* 575 */       return (AutoCompletion)clientProperty;
/*     */     }
/* 577 */     return null;
/*     */   }
/*     */ 
/*     */   protected class AutoCompletionDocument extends PlainDocument
/*     */   {
/*     */     protected AutoCompletionDocument()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void remove(int offs, int len)
/*     */       throws BadLocationException
/*     */     {
/* 385 */       if (AutoCompletion.this._selecting) {
/* 386 */         return;
/*     */       }
/* 388 */       if (AutoCompletion.this._hitBackspace)
/*     */       {
/* 391 */         if (offs > 0) {
/* 392 */           if (AutoCompletion.this._hitBackspaceOnSelection) offs--;
/*     */         }
/*     */         else
/*     */         {
/* 396 */           PortingUtils.notifyUser(AutoCompletion.this._textComponent);
/*     */         }
/* 398 */         AutoCompletion.this.highlightCompletedText(offs);
/*     */       }
/*     */       else {
/* 401 */         super.remove(offs, len);
/*     */       }
/*     */     }
/*     */ 
/*     */     public void insertString(int offs, String str, AttributeSet a)
/*     */       throws BadLocationException
/*     */     {
/* 408 */       if (AutoCompletion.this._selecting) {
/* 409 */         return;
/*     */       }
/*     */ 
/* 412 */       super.insertString(offs, str, a);
/*     */ 
/* 414 */       if ((AutoCompletion.this.isKeyTyped()) || (AutoCompletion.this.isStrict()))
/*     */       {
/* 416 */         String text = getText(0, getLength());
/* 417 */         int index = AutoCompletion.this.getSearchable().findFromCursor(text);
/*     */ 
/* 419 */         if (index != -1) {
/* 420 */           Object item = AutoCompletion.this.getSearchable().getElementAt(index);
/* 421 */           AutoCompletion.this.setSelectedItem(item);
/* 422 */           setText(AutoCompletion.this.getSearchable().convertElementToString(item));
/*     */ 
/* 424 */           AutoCompletion.this.highlightCompletedText(offs + str.length());
/*     */         }
/* 427 */         else if (AutoCompletion.this.isStrict()) {
/* 428 */           index = AutoCompletion.this.getSearchable().getSelectedIndex();
/* 429 */           if ((index == -1) && 
/* 430 */             (AutoCompletion.this.getSearchable().getElementCount() > 0)) {
/* 431 */             index = 0;
/* 432 */             AutoCompletion.this.getSearchable().setSelectedIndex(0, false);
/*     */           }
/*     */ 
/* 436 */           if (index != -1) {
/* 437 */             Object item = AutoCompletion.this.getSearchable().getElementAt(index);
/* 438 */             offs -= str.length();
/*     */ 
/* 440 */             PortingUtils.notifyUser(AutoCompletion.this._textComponent);
/* 441 */             setText(AutoCompletion.this.getSearchable().convertElementToString(item));
/*     */ 
/* 443 */             AutoCompletion.this.highlightCompletedText(offs + str.length());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     protected void setText(String text)
/*     */     {
/*     */       try
/*     */       {
/* 453 */         if (AutoCompletion.this.isStrictCompletion()) {
/* 454 */           super.remove(0, getLength());
/* 455 */           super.insertString(0, text, null);
/*     */         }
/*     */         else {
/* 458 */           String existingText = super.getText(0, getLength());
/* 459 */           int matchIndex = existingText.length() <= text.length() ? existingText.length() : text.length();
/*     */ 
/* 461 */           for (int i = 0; i < existingText.length(); i++) {
/* 462 */             if (!existingText.substring(0, matchIndex).equalsIgnoreCase(text.substring(0, matchIndex))) {
/* 463 */               matchIndex--;
/*     */             }
/*     */           }
/*     */ 
/* 467 */           super.remove(matchIndex, getLength() - matchIndex);
/* 468 */           super.insertString(matchIndex, text.substring(matchIndex), null);
/*     */         }
/*     */       }
/*     */       catch (BadLocationException e) {
/* 472 */         throw new RuntimeException(e.toString());
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.AutoCompletion
 * JD-Core Version:    0.6.0
 */
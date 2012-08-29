/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.swing.event.SearchableEvent;
/*     */ import java.awt.Color;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import javax.swing.event.DocumentListener;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.Highlighter;
/*     */ import javax.swing.text.Highlighter.HighlightPainter;
/*     */ import javax.swing.text.JTextComponent;
/*     */ 
/*     */ public class TextComponentSearchable extends Searchable
/*     */   implements DocumentListener, PropertyChangeListener
/*     */ {
/*     */   private Highlighter.HighlightPainter _highlightPainter;
/*  60 */   private static final Color DEFAULT_HIGHLIGHT_COLOR = new Color(204, 204, 255);
/*  61 */   private Color _highlightColor = null;
/*  62 */   private int _selectedIndex = -1;
/*     */   private HighlighCache _highlighCache;
/* 342 */   private String _text = null;
/*     */ 
/*     */   public TextComponentSearchable(JTextComponent textComponent)
/*     */   {
/*  66 */     super(textComponent);
/*  67 */     this._highlighCache = new HighlighCache(null);
/*  68 */     installHighlightsRemover();
/*  69 */     setHighlightColor(DEFAULT_HIGHLIGHT_COLOR);
/*     */   }
/*     */ 
/*     */   public void uninstallHighlightsRemover()
/*     */   {
/*  76 */     this._component.unregisterKeyboardAction(KeyStroke.getKeyStroke(27, 0));
/*     */   }
/*     */ 
/*     */   public void installHighlightsRemover()
/*     */   {
/*  83 */     AbstractAction highlightRemover = new AbstractAction() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  85 */         TextComponentSearchable.this.removeAllHighlights();
/*     */       }
/*     */     };
/*  88 */     this._component.registerKeyboardAction(highlightRemover, KeyStroke.getKeyStroke(27, 0), 0);
/*     */   }
/*     */ 
/*     */   public void installListeners()
/*     */   {
/*  93 */     super.installListeners();
/*  94 */     if ((this._component instanceof JTextComponent)) {
/*  95 */       ((JTextComponent)this._component).getDocument().addDocumentListener(this);
/*  96 */       this._component.addPropertyChangeListener("document", this);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void uninstallListeners()
/*     */   {
/* 102 */     super.uninstallListeners();
/* 103 */     if ((this._component instanceof JTextComponent)) {
/* 104 */       ((JTextComponent)this._component).getDocument().removeDocumentListener(this);
/* 105 */       this._component.removePropertyChangeListener("document", this);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void setSelectedIndex(int index, boolean incremental)
/*     */   {
/* 111 */     if ((this._component instanceof JTextComponent)) {
/* 112 */       if (index == -1) {
/* 113 */         removeAllHighlights();
/* 114 */         this._selectedIndex = -1;
/* 115 */         return;
/*     */       }
/*     */ 
/* 118 */       if (!incremental) {
/* 119 */         removeAllHighlights();
/*     */       }
/*     */ 
/* 122 */       String text = getSearchingText();
/*     */       try {
/* 124 */         addHighlight(index, text, incremental);
/*     */       }
/*     */       catch (BadLocationException e) {
/* 127 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void addHighlight(int index, String text, boolean incremental)
/*     */     throws BadLocationException
/*     */   {
/* 141 */     if ((this._component instanceof JTextComponent)) {
/* 142 */       JTextComponent textComponent = (JTextComponent)this._component;
/* 143 */       Object obj = textComponent.getHighlighter().addHighlight(index, index + text.length(), this._highlightPainter);
/* 144 */       this._highlighCache.addHighlight(obj);
/* 145 */       this._selectedIndex = index;
/* 146 */       if (!incremental) {
/* 147 */         Runnable runnable = new Runnable(textComponent, index, text) {
/*     */           public void run() {
/* 149 */             TextComponentSearchable.this.scrollTextVisible(this.val$textComponent, this.val$index, this.val$text.length());
/*     */           }
/*     */         };
/* 152 */         SwingUtilities.invokeLater(runnable);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void scrollTextVisible(JTextComponent textComponent, int index, int length)
/*     */   {
/* 159 */     if (index != -1)
/*     */     {
/*     */       try
/*     */       {
/* 163 */         Rectangle begin = textComponent.modelToView(index);
/* 164 */         if (begin == null) {
/* 165 */           return;
/*     */         }
/* 167 */         Rectangle end = textComponent.modelToView(index + length);
/* 168 */         if (end == null) {
/* 169 */           return;
/*     */         }
/* 171 */         Rectangle bounds = this._component.getVisibleRect();
/* 172 */         if (begin.x <= bounds.width) {
/* 173 */           begin.width = end.x;
/* 174 */           begin.x = 0;
/*     */         }
/*     */         else {
/* 177 */           begin.width = (end.x - begin.x);
/*     */         }
/* 179 */         textComponent.scrollRectToVisible(begin);
/*     */       }
/*     */       catch (BadLocationException ble)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void removeAllHighlights()
/*     */   {
/* 190 */     if ((this._component instanceof JTextComponent)) {
/* 191 */       Iterator itor = this._highlighCache.getAllHighlights();
/* 192 */       while (itor.hasNext()) {
/* 193 */         Object o = itor.next();
/* 194 */         ((JTextComponent)this._component).getHighlighter().removeHighlight(o);
/*     */       }
/* 196 */       this._highlighCache.removeAllHighlights();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected int getSelectedIndex()
/*     */   {
/* 202 */     if ((this._component instanceof JTextComponent)) {
/* 203 */       return this._selectedIndex;
/*     */     }
/* 205 */     return 0;
/*     */   }
/*     */ 
/*     */   protected Object getElementAt(int index)
/*     */   {
/* 210 */     String text = getSearchingText();
/* 211 */     if ((text != null) && 
/* 212 */       ((this._component instanceof JTextComponent))) {
/* 213 */       int endIndex = index + text.length();
/* 214 */       int elementCount = getElementCount();
/* 215 */       if (endIndex > elementCount)
/* 216 */         endIndex = getElementCount();
/*     */       try
/*     */       {
/* 219 */         return ((JTextComponent)this._component).getDocument().getText(index, endIndex - index + 1);
/*     */       }
/*     */       catch (BadLocationException e) {
/* 222 */         return null;
/*     */       }
/*     */     }
/*     */ 
/* 226 */     return "";
/*     */   }
/*     */ 
/*     */   protected int getElementCount()
/*     */   {
/* 231 */     if ((this._component instanceof JTextComponent)) {
/* 232 */       return ((JTextComponent)this._component).getDocument().getLength();
/*     */     }
/* 234 */     return 0;
/*     */   }
/*     */ 
/*     */   protected String convertElementToString(Object object)
/*     */   {
/* 246 */     if (object != null) {
/* 247 */       return object.toString();
/*     */     }
/*     */ 
/* 250 */     return "";
/*     */   }
/*     */ 
/*     */   public void propertyChange(PropertyChangeEvent evt)
/*     */   {
/* 255 */     if (isProcessModelChangeEvent()) {
/* 256 */       hidePopup();
/* 257 */       this._text = null;
/* 258 */       if ((evt.getOldValue() instanceof Document)) {
/* 259 */         ((Document)evt.getNewValue()).removeDocumentListener(this);
/*     */       }
/* 261 */       if ((evt.getNewValue() instanceof Document)) {
/* 262 */         ((Document)evt.getNewValue()).addDocumentListener(this);
/*     */       }
/* 264 */       fireSearchableEvent(new SearchableEvent(this, 3005));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void insertUpdate(DocumentEvent e) {
/* 269 */     if (isProcessModelChangeEvent()) {
/* 270 */       hidePopup();
/* 271 */       this._text = null;
/* 272 */       fireSearchableEvent(new SearchableEvent(this, 3005));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removeUpdate(DocumentEvent e) {
/* 277 */     if (isProcessModelChangeEvent()) {
/* 278 */       hidePopup();
/* 279 */       this._text = null;
/* 280 */       fireSearchableEvent(new SearchableEvent(this, 3005));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void changedUpdate(DocumentEvent e) {
/* 285 */     if (isProcessModelChangeEvent()) {
/* 286 */       hidePopup();
/* 287 */       this._text = null;
/* 288 */       fireSearchableEvent(new SearchableEvent(this, 3005));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean isActivateKey(KeyEvent e)
/*     */   {
/* 294 */     if (((this._component instanceof JTextComponent)) && (((JTextComponent)this._component).isEditable())) {
/* 295 */       return (e.getID() == 401) && (e.getKeyCode() == 70) && ((0x2 & e.getModifiers()) != 0);
/*     */     }
/*     */ 
/* 298 */     return super.isActivateKey(e);
/*     */   }
/*     */ 
/*     */   public Color getHighlightColor()
/*     */   {
/* 308 */     if (this._highlightColor != null) {
/* 309 */       return this._highlightColor;
/*     */     }
/*     */ 
/* 312 */     return DEFAULT_HIGHLIGHT_COLOR;
/*     */   }
/*     */ 
/*     */   public void setHighlightColor(Color highlightColor)
/*     */   {
/* 322 */     this._highlightColor = highlightColor;
/* 323 */     this._highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(this._highlightColor);
/*     */   }
/*     */ 
/*     */   public int findLast(String s)
/*     */   {
/* 328 */     if ((this._component instanceof JTextComponent)) {
/* 329 */       String text = getDocumentText();
/* 330 */       if (isCaseSensitive()) {
/* 331 */         return text.lastIndexOf(s);
/*     */       }
/*     */ 
/* 334 */       return lastIndexOf(text, s, text.length());
/*     */     }
/*     */ 
/* 338 */     return super.findLast(s);
/*     */   }
/*     */ 
/*     */   private String getDocumentText()
/*     */   {
/* 350 */     if (this._text == null) {
/* 351 */       Document document = ((JTextComponent)this._component).getDocument();
/*     */       try {
/* 353 */         String text = document.getText(0, document.getLength());
/* 354 */         this._text = text;
/*     */       }
/*     */       catch (BadLocationException e) {
/* 357 */         return "";
/*     */       }
/*     */     }
/* 360 */     return this._text;
/*     */   }
/*     */ 
/*     */   public int findFirst(String s)
/*     */   {
/* 365 */     if ((this._component instanceof JTextComponent)) {
/* 366 */       String text = getDocumentText();
/* 367 */       if (isCaseSensitive()) {
/* 368 */         return text.indexOf(s);
/*     */       }
/*     */ 
/* 371 */       return indexOf(text, s, 0);
/*     */     }
/*     */ 
/* 375 */     return super.findFirst(s);
/*     */   }
/*     */ 
/*     */   static int lastIndexOf(String source, String target, int fromIndex)
/*     */   {
/* 380 */     int sourceCount = source.length();
/* 381 */     int targetCount = target.length();
/* 382 */     int rightIndex = sourceCount - targetCount;
/* 383 */     if (fromIndex < 0) {
/* 384 */       return -1;
/*     */     }
/* 386 */     if (fromIndex > rightIndex) {
/* 387 */       fromIndex = rightIndex;
/*     */     }
/*     */ 
/* 390 */     if (targetCount == 0) {
/* 391 */       return fromIndex;
/*     */     }
/* 393 */     char[] lowerTarget = target.toLowerCase().toCharArray();
/* 394 */     char[] upperTarget = target.toUpperCase().toCharArray();
/*     */ 
/* 396 */     int strLastIndex = targetCount - 1;
/* 397 */     int min = targetCount - 1;
/* 398 */     int i = min + fromIndex;
/*     */ 
/* 400 */     while (i >= min) {
/* 401 */       while ((i >= min) && (source.charAt(i) != lowerTarget[strLastIndex]) && (source.charAt(i) != upperTarget[strLastIndex])) {
/* 402 */         i--;
/*     */       }
/* 404 */       int j = i - 1;
/* 405 */       int start = j - (targetCount - 1);
/* 406 */       int k = strLastIndex - 1;
/*     */ 
/* 408 */       while (j > start) {
/* 409 */         char ch = source.charAt(j);
/* 410 */         if ((ch != lowerTarget[k]) && (ch != upperTarget[k])) {
/* 411 */           i--;
/* 412 */           break;
/*     */         }
/* 414 */         j--;
/* 415 */         k--;
/*     */       }
/* 417 */       if (j <= start) {
/* 418 */         return start + 1;
/*     */       }
/*     */     }
/* 421 */     return -1;
/*     */   }
/*     */ 
/*     */   private static int indexOf(String source, String target, int fromIndex) {
/* 425 */     int sourceCount = source.length();
/* 426 */     int targetCount = target.length();
/* 427 */     if (fromIndex >= sourceCount) {
/* 428 */       return targetCount == 0 ? sourceCount : -1;
/*     */     }
/* 430 */     if (fromIndex < 0) {
/* 431 */       fromIndex = 0;
/*     */     }
/* 433 */     if (targetCount == 0) {
/* 434 */       return fromIndex;
/*     */     }
/* 436 */     char[] lowerTarget = target.toLowerCase().toCharArray();
/* 437 */     char[] upperTarget = target.toUpperCase().toCharArray();
/*     */ 
/* 439 */     int max = sourceCount - targetCount;
/*     */ 
/* 441 */     for (int i = fromIndex; i <= max; i++)
/*     */     {
/* 443 */       char c = source.charAt(i);
/* 444 */       if ((c != lowerTarget[0]) && (c != upperTarget[0])) {
/* 445 */         i++;
/* 446 */         while ((i <= max) && (source.charAt(i) != lowerTarget[0]) && (source.charAt(i) != upperTarget[0])) {
/* 447 */           i++;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 452 */       if (i <= max) {
/* 453 */         int j = i + 1;
/* 454 */         int end = j + targetCount - 1;
/* 455 */         for (int k = 1; j < end; j++) {
/* 456 */           char ch = source.charAt(j);
/* 457 */           if ((ch != lowerTarget[k]) && (ch != upperTarget[k])) {
/*     */             break;
/*     */           }
/* 460 */           k++;
/*     */         }
/*     */ 
/* 463 */         if (j == end)
/*     */         {
/* 465 */           return i;
/*     */         }
/*     */       }
/*     */     }
/* 469 */     return -1;
/*     */   }
/*     */ 
/*     */   public int findFromCursor(String s)
/*     */   {
/* 474 */     if (isReverseOrder()) {
/* 475 */       return reverseFindFromCursor(s);
/*     */     }
/*     */ 
/* 478 */     if ((this._component instanceof JTextComponent)) {
/* 479 */       String text = getDocumentText();
/* 480 */       int selectedIndex = getCursor() != -1 ? getCursor() : getSelectedIndex();
/* 481 */       if (selectedIndex < 0)
/* 482 */         selectedIndex = 0;
/* 483 */       int count = getElementCount();
/* 484 */       if (count == 0) {
/* 485 */         return s.length() > 0 ? -1 : 0;
/*     */       }
/*     */ 
/* 488 */       int found = isCaseSensitive() ? text.indexOf(s, selectedIndex) : indexOf(text, s, selectedIndex);
/*     */ 
/* 491 */       if (found == -1) {
/* 492 */         found = isCaseSensitive() ? text.indexOf(s, 0) : indexOf(text, s, 0);
/* 493 */         if (found >= selectedIndex) {
/* 494 */           found = -1;
/*     */         }
/*     */       }
/*     */ 
/* 498 */       return found;
/*     */     }
/*     */ 
/* 501 */     return super.findFromCursor(s);
/*     */   }
/*     */ 
/*     */   public int reverseFindFromCursor(String s)
/*     */   {
/* 507 */     if (!isReverseOrder()) {
/* 508 */       return findFromCursor(s);
/*     */     }
/*     */ 
/* 511 */     if ((this._component instanceof JTextComponent)) {
/* 512 */       String text = getDocumentText();
/* 513 */       int selectedIndex = getCursor() != -1 ? getCursor() : getSelectedIndex();
/* 514 */       if (selectedIndex < 0)
/* 515 */         selectedIndex = 0;
/* 516 */       int count = getElementCount();
/* 517 */       if (count == 0) {
/* 518 */         return s.length() > 0 ? -1 : 0;
/*     */       }
/*     */ 
/* 521 */       int found = isCaseSensitive() ? text.lastIndexOf(s, selectedIndex) : lastIndexOf(text, s, selectedIndex);
/*     */ 
/* 524 */       if (found == -1) {
/* 525 */         found = isCaseSensitive() ? text.lastIndexOf(s, text.length() - 1) : lastIndexOf(text, s, text.length() - 1);
/* 526 */         if (found <= selectedIndex) {
/* 527 */           found = -1;
/*     */         }
/*     */       }
/*     */ 
/* 531 */       return found;
/*     */     }
/*     */ 
/* 534 */     return super.findFromCursor(s);
/*     */   }
/*     */ 
/*     */   public int findNext(String s)
/*     */   {
/* 540 */     if ((this._component instanceof JTextComponent)) {
/* 541 */       String text = getDocumentText();
/* 542 */       int selectedIndex = getCursor() != -1 ? getCursor() : getSelectedIndex();
/* 543 */       if (selectedIndex < 0)
/* 544 */         selectedIndex = 0;
/* 545 */       int count = getElementCount();
/* 546 */       if (count == 0) {
/* 547 */         return s.length() > 0 ? -1 : 0;
/*     */       }
/*     */ 
/* 550 */       int found = isCaseSensitive() ? text.indexOf(s, selectedIndex + 1) : indexOf(text, s, selectedIndex + 1);
/*     */ 
/* 553 */       if ((found == -1) && (isRepeats())) {
/* 554 */         found = isCaseSensitive() ? text.indexOf(s, 0) : indexOf(text, s, 0);
/* 555 */         if (found >= selectedIndex) {
/* 556 */           found = -1;
/*     */         }
/*     */       }
/*     */ 
/* 560 */       return found;
/*     */     }
/*     */ 
/* 563 */     return super.findNext(s);
/*     */   }
/*     */ 
/*     */   public int findPrevious(String s)
/*     */   {
/* 569 */     if ((this._component instanceof JTextComponent)) {
/* 570 */       String text = getDocumentText();
/* 571 */       int selectedIndex = getCursor() != -1 ? getCursor() : getSelectedIndex();
/* 572 */       if (selectedIndex < 0)
/* 573 */         selectedIndex = 0;
/* 574 */       int count = getElementCount();
/* 575 */       if (count == 0) {
/* 576 */         return s.length() > 0 ? -1 : 0;
/*     */       }
/*     */ 
/* 579 */       int found = isCaseSensitive() ? text.lastIndexOf(s, selectedIndex - 1) : lastIndexOf(text, s, selectedIndex - 1);
/*     */ 
/* 582 */       if ((found == -1) && (isRepeats())) {
/* 583 */         found = isCaseSensitive() ? text.lastIndexOf(s, count - 1) : lastIndexOf(text, s, count - 1);
/* 584 */         if (found <= selectedIndex) {
/* 585 */           found = -1;
/*     */         }
/*     */       }
/*     */ 
/* 589 */       return found;
/*     */     }
/*     */ 
/* 592 */     return super.findPrevious(s);
/*     */   }
/*     */ 
/*     */   public void hidePopup()
/*     */   {
/* 616 */     super.hidePopup();
/* 617 */     this._selectedIndex = -1;
/*     */   }
/*     */ 
/*     */   private class HighlighCache extends HashMap
/*     */   {
/*     */     private HighlighCache()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void addHighlight(Object obj)
/*     */     {
/* 598 */       put(obj, null);
/*     */     }
/*     */ 
/*     */     public void removeHighlight(Object obj) {
/* 602 */       remove(obj);
/*     */     }
/*     */ 
/*     */     public Iterator getAllHighlights() {
/* 606 */       return keySet().iterator();
/*     */     }
/*     */ 
/*     */     public void removeAllHighlights() {
/* 610 */       clear();
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.TextComponentSearchable
 * JD-Core Version:    0.6.0
 */
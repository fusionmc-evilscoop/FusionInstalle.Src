/*      */ package com.jidesoft.swing;
/*      */ 
/*      */ import com.jidesoft.utils.PortingUtils;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.text.NumberFormat;
/*      */ import java.text.ParseException;
/*      */ import java.util.Locale;
/*      */ import javax.swing.AbstractButton;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.KeyStroke;
/*      */ 
/*      */ public class Calculator extends JPanel
/*      */   implements ActionListener
/*      */ {
/*      */   private double _result;
/*      */   private StringBuffer _op1;
/*      */   private StringBuffer _op2;
/*   72 */   private int _operator = -1;
/*      */   private String _displayText;
/*   74 */   private boolean _overflow = false;
/*   75 */   private boolean _negationOp1 = true;
/*   76 */   private boolean _backspaceOp1 = false;
/*   77 */   private boolean _backspaceOp2 = false;
/*   78 */   private boolean _clearOperatorPending = false;
/*   79 */   private boolean _isFakedEqualPressed = false;
/*   80 */   private boolean _resultCalculated = false;
/*      */   public static final int OPERATOR_NONE = -1;
/*      */   public static final int OPERATOR_ADD = 0;
/*      */   public static final int OPERATOR_MINUS = 1;
/*      */   public static final int OPERATOR_MULTIPLY = 2;
/*      */   public static final int OPERATOR_DIVIDE = 3;
/*      */   private AbstractButton _addButton;
/*      */   private AbstractButton _minusButton;
/*      */   private AbstractButton _multiplyButton;
/*      */   private AbstractButton _divideButton;
/*      */   private AbstractButton _pointButton;
/*      */   private AbstractButton _equalButton;
/*      */   private AbstractButton _backspaceButton;
/*      */   private AbstractButton _clearButton;
/*      */   private AbstractButton _negativeButton;
/*      */   private AbstractButton[] _numberButtons;
/*      */   private char _actualCharPoint;
/*      */   private NumberFormat _displayFormat;
/*      */   public static final char CHAR_CLEAR = 'c';
/*      */   public static final char CHAR_POINT = '.';
/*      */   public static final char CHAR_ADD = '+';
/*      */   public static final char CHAR_MINUS = '-';
/*      */   public static final char CHAR_MULTIPLY = '*';
/*      */   public static final char CHAR_DIVIDE = '/';
/*      */   public static final char CHAR_EQUAL = '=';
/*      */   public static final char CHAR_NEGATIVE = '!';
/*      */   public static final char CHAR_BACKSPACE = '<';
/*      */   public static final char CHAR_0 = '0';
/*      */   public static final char CHAR_1 = '1';
/*      */   public static final char CHAR_2 = '2';
/*      */   public static final char CHAR_3 = '3';
/*      */   public static final char CHAR_4 = '4';
/*      */   public static final char CHAR_5 = '5';
/*      */   public static final char CHAR_6 = '6';
/*      */   public static final char CHAR_7 = '7';
/*      */   public static final char CHAR_8 = '8';
/*      */   public static final char CHAR_9 = '9';
/*      */   public static final String PROPERTY_DISPLAY_TEXT = "displayText";
/*      */   public static final String PROPERTY_OPERATOR = "operator";
/*  125 */   private int _buttonWidth = 24;
/*  126 */   private int _buttonHeight = 24;
/*  127 */   private int _buttonGap = 2;
/*      */ 
/*      */   public Calculator()
/*      */   {
/*  133 */     this._op1 = new StringBuffer();
/*  134 */     this._op2 = new StringBuffer();
/*  135 */     this._displayFormat = NumberFormat.getNumberInstance();
/*  136 */     configureNumberFormat();
/*  137 */     initComponents();
/*  138 */     registerKeyboardActions(this, 1);
/*      */   }
/*      */ 
/*      */   public void setLocale(Locale l)
/*      */   {
/*  143 */     unregisterKeyboardActions(this);
/*  144 */     super.setLocale(l);
/*  145 */     this._op1 = new StringBuffer();
/*  146 */     this._op2 = new StringBuffer();
/*  147 */     this._displayFormat = NumberFormat.getNumberInstance(getLocale());
/*  148 */     configureNumberFormat();
/*  149 */     this._actualCharPoint = getDisplayFormat().format(2.01D).charAt(1);
/*  150 */     this._pointButton.setText("" + this._actualCharPoint);
/*  151 */     registerKeyboardActions(this, 1);
/*      */   }
/*      */ 
/*      */   protected void configureNumberFormat()
/*      */   {
/*  158 */     this._displayFormat.setMaximumFractionDigits(20);
/*  159 */     this._displayFormat.setMinimumFractionDigits(0);
/*  160 */     this._displayFormat.setGroupingUsed(false);
/*      */   }
/*      */ 
/*      */   public static boolean isValidKeyEvent(KeyEvent keyEvent)
/*      */   {
/*  170 */     char c = keyEvent.getKeyChar();
/*  171 */     return ((keyEvent.getModifiers() & 0xFFFFFFFE) != 0) || (Character.isDigit(c)) || (isOperator(keyEvent)) || (isEnterOrEqual(keyEvent)) || (c == '.') || (c == 'c') || (Character.toLowerCase(c) == 'c') || (c == '\033') || (c == '\b');
/*      */   }
/*      */ 
/*      */   public static boolean isOperator(KeyEvent keyEvent)
/*      */   {
/*  187 */     char c = keyEvent.getKeyChar();
/*  188 */     return (c == '+') || (c == '-') || (c == '*') || (c == '/');
/*      */   }
/*      */ 
/*      */   public static boolean isEnterOrEqual(KeyEvent keyEvent)
/*      */   {
/*  199 */     char c = keyEvent.getKeyChar();
/*  200 */     return (c == '\n') || (c == '=');
/*      */   }
/*      */ 
/*      */   public void registerKeyboardActions(JComponent component, int condition)
/*      */   {
/*  210 */     boolean isCellEditor = isCellEditor();
/*  211 */     component.registerKeyboardAction(this, "+", KeyStroke.getKeyStroke('+'), condition);
/*  212 */     component.registerKeyboardAction(this, "-", KeyStroke.getKeyStroke('-'), condition);
/*  213 */     component.registerKeyboardAction(this, "*", KeyStroke.getKeyStroke('*'), condition);
/*  214 */     component.registerKeyboardAction(this, "/", KeyStroke.getKeyStroke('/'), condition);
/*  215 */     component.registerKeyboardAction(this, "=", KeyStroke.getKeyStroke('='), condition);
/*  216 */     if (!isCellEditor)
/*  217 */       component.registerKeyboardAction(this, "=", KeyStroke.getKeyStroke(10, 0), condition);
/*  218 */     component.registerKeyboardAction(this, "0", KeyStroke.getKeyStroke('0'), condition);
/*  219 */     component.registerKeyboardAction(this, "1", KeyStroke.getKeyStroke('1'), condition);
/*  220 */     component.registerKeyboardAction(this, "2", KeyStroke.getKeyStroke('2'), condition);
/*  221 */     component.registerKeyboardAction(this, "3", KeyStroke.getKeyStroke('3'), condition);
/*  222 */     component.registerKeyboardAction(this, "4", KeyStroke.getKeyStroke('4'), condition);
/*  223 */     component.registerKeyboardAction(this, "5", KeyStroke.getKeyStroke('5'), condition);
/*  224 */     component.registerKeyboardAction(this, "6", KeyStroke.getKeyStroke('6'), condition);
/*  225 */     component.registerKeyboardAction(this, "7", KeyStroke.getKeyStroke('7'), condition);
/*  226 */     component.registerKeyboardAction(this, "8", KeyStroke.getKeyStroke('8'), condition);
/*  227 */     component.registerKeyboardAction(this, "9", KeyStroke.getKeyStroke('9'), condition);
/*  228 */     component.registerKeyboardAction(this, "" + this._actualCharPoint, KeyStroke.getKeyStroke(this._actualCharPoint), condition);
/*  229 */     component.registerKeyboardAction(this, "<", KeyStroke.getKeyStroke(8, 0), condition);
/*  230 */     if (!isCellEditor)
/*  231 */       component.registerKeyboardAction(this, "c", KeyStroke.getKeyStroke(27, 0), condition);
/*  232 */     if (!isCellEditor)
/*  233 */       component.registerKeyboardAction(this, "c", KeyStroke.getKeyStroke(27, 0), 1);
/*  234 */     component.registerKeyboardAction(this, "c", KeyStroke.getKeyStroke(Character.toUpperCase('c')), condition);
/*  235 */     component.registerKeyboardAction(this, "c", KeyStroke.getKeyStroke(Character.toLowerCase('c')), condition);
/*      */   }
/*      */ 
/*      */   public void unregisterKeyboardActions(JComponent component)
/*      */   {
/*  244 */     boolean isCellEditor = isCellEditor();
/*  245 */     component.unregisterKeyboardAction(KeyStroke.getKeyStroke('+'));
/*  246 */     component.unregisterKeyboardAction(KeyStroke.getKeyStroke('-'));
/*  247 */     component.unregisterKeyboardAction(KeyStroke.getKeyStroke('*'));
/*  248 */     component.unregisterKeyboardAction(KeyStroke.getKeyStroke('/'));
/*  249 */     component.unregisterKeyboardAction(KeyStroke.getKeyStroke('='));
/*  250 */     if (!isCellEditor) component.unregisterKeyboardAction(KeyStroke.getKeyStroke(10, 0));
/*  251 */     component.unregisterKeyboardAction(KeyStroke.getKeyStroke('0'));
/*  252 */     component.unregisterKeyboardAction(KeyStroke.getKeyStroke('1'));
/*  253 */     component.unregisterKeyboardAction(KeyStroke.getKeyStroke('2'));
/*  254 */     component.unregisterKeyboardAction(KeyStroke.getKeyStroke('3'));
/*  255 */     component.unregisterKeyboardAction(KeyStroke.getKeyStroke('4'));
/*  256 */     component.unregisterKeyboardAction(KeyStroke.getKeyStroke('5'));
/*  257 */     component.unregisterKeyboardAction(KeyStroke.getKeyStroke('6'));
/*  258 */     component.unregisterKeyboardAction(KeyStroke.getKeyStroke('7'));
/*  259 */     component.unregisterKeyboardAction(KeyStroke.getKeyStroke('8'));
/*  260 */     component.unregisterKeyboardAction(KeyStroke.getKeyStroke('9'));
/*  261 */     component.unregisterKeyboardAction(KeyStroke.getKeyStroke(this._actualCharPoint));
/*  262 */     component.unregisterKeyboardAction(KeyStroke.getKeyStroke(8, 0));
/*  263 */     if (!isCellEditor) component.unregisterKeyboardAction(KeyStroke.getKeyStroke(27, 0));
/*  264 */     component.unregisterKeyboardAction(KeyStroke.getKeyStroke(Character.toUpperCase('c')));
/*  265 */     component.unregisterKeyboardAction(KeyStroke.getKeyStroke(Character.toLowerCase('c')));
/*      */   }
/*      */ 
/*      */   protected void initComponents() {
/*  269 */     setLayout(new CalculatorLayoutManager());
/*  270 */     add(this._addButton = createButton("+"));
/*  271 */     add(this._minusButton = createButton("-"));
/*  272 */     add(this._multiplyButton = createButton("*"));
/*  273 */     add(this._divideButton = createButton("/"));
/*  274 */     this._numberButtons = new AbstractButton[10];
/*  275 */     for (int i = 0; i <= 9; i++) {
/*  276 */       add(this._numberButtons[i] =  = createButton("" + i));
/*      */     }
/*  278 */     this._actualCharPoint = getDisplayFormat().format(2.01D).charAt(1);
/*  279 */     add(this._pointButton = createButton("" + this._actualCharPoint));
/*  280 */     add(this._equalButton = createButton("="));
/*  281 */     add(this._backspaceButton = createButton(null, new BackspaceIcon()));
/*  282 */     add(this._negativeButton = createButton(null, new ToggleNegativeIcon()));
/*  283 */     add(this._clearButton = createButton("C"));
/*      */   }
/*      */ 
/*      */   public boolean isResultCalculated()
/*      */   {
/*  292 */     return this._resultCalculated;
/*      */   }
/*      */ 
/*      */   public void setResultCalculated(boolean resultCalculated)
/*      */   {
/*  301 */     this._resultCalculated = resultCalculated;
/*      */   }
/*      */ 
/*      */   protected AbstractButton createButton(String text)
/*      */   {
/*  362 */     return createButton(text, null);
/*      */   }
/*      */ 
/*      */   protected AbstractButton createButton(String text, Icon icon)
/*      */   {
/*  383 */     AbstractButton button = new JideButton(text, icon);
/*  384 */     button.setOpaque(true);
/*  385 */     button.setContentAreaFilled(true);
/*  386 */     button.setRequestFocusEnabled(false);
/*  387 */     button.setFocusable(false);
/*  388 */     button.addActionListener(this);
/*  389 */     return button;
/*      */   }
/*      */ 
/*      */   public boolean isOverflow()
/*      */   {
/*  398 */     return this._overflow;
/*      */   }
/*      */ 
/*      */   public void setOverflow(boolean overflow)
/*      */   {
/*  407 */     this._overflow = overflow;
/*      */   }
/*      */ 
/*      */   public void input(char c)
/*      */   {
/*  417 */     if (('c' == Character.toLowerCase(c)) || ('c' == Character.toUpperCase(c))) {
/*  418 */       clear();
/*  419 */       return;
/*      */     }
/*      */ 
/*  422 */     if (this._overflow) {
/*  423 */       beep();
/*  424 */       return;
/*      */     }
/*      */ 
/*  427 */     if ((Character.isDigit(c)) || (this._actualCharPoint == c)) {
/*  428 */       if (this._clearOperatorPending) {
/*  429 */         setOperator(-1);
/*  430 */         this._op1.setLength(0);
/*  431 */         this._clearOperatorPending = false;
/*      */       }
/*  433 */       if (getOperator() == -1) {
/*  434 */         if ((this._actualCharPoint != c) || (this._op1.indexOf("" + this._actualCharPoint) == -1)) {
/*  435 */           this._op1.append(c);
/*  436 */           this._backspaceOp1 = true;
/*  437 */           this._backspaceOp2 = false;
/*  438 */           setDisplayText(this._op1.toString());
/*      */         }
/*      */         else {
/*  441 */           beep();
/*      */         }
/*      */ 
/*      */       }
/*  445 */       else if ((this._actualCharPoint != c) || (this._op2.indexOf("" + this._actualCharPoint) == -1)) {
/*  446 */         this._op2.append(c);
/*  447 */         this._backspaceOp2 = true;
/*  448 */         this._backspaceOp1 = false;
/*  449 */         setDisplayText(this._op2.toString());
/*      */       }
/*      */       else {
/*  452 */         beep();
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  457 */       switch (c) {
/*      */       case '+':
/*  459 */         operatorPressed(0);
/*  460 */         break;
/*      */       case '-':
/*  462 */         operatorPressed(1);
/*  463 */         break;
/*      */       case '*':
/*  465 */         operatorPressed(2);
/*  466 */         break;
/*      */       case '/':
/*  468 */         operatorPressed(3);
/*  469 */         break;
/*      */       case '=':
/*  471 */         calculateResult(true);
/*  472 */         this._clearOperatorPending = true;
/*  473 */         break;
/*      */       case '!':
/*  475 */         if (this._negationOp1) {
/*  476 */           negativePressed(this._op1);
/*  477 */           setDisplayText(this._op1.toString());
/*      */         }
/*      */         else {
/*  480 */           negativePressed(this._op2);
/*  481 */           setDisplayText(this._op2.toString());
/*      */         }
/*  483 */         break;
/*      */       case '<':
/*  485 */         if (this._backspaceOp1) {
/*  486 */           backspacePressed(this._op1);
/*  487 */           setDisplayText(this._op1.toString());
/*      */         }
/*  489 */         else if (this._backspaceOp2) {
/*  490 */           backspacePressed(this._op2);
/*  491 */           setDisplayText(this._op2.toString());
/*      */         }
/*      */         else {
/*  494 */           beep();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void operatorPressed(int operator)
/*      */   {
/*  502 */     if (this._op1.length() == 0) {
/*  503 */       this._op1.append("0");
/*      */     }
/*  505 */     else if ((getOperator() == -1) || (this._clearOperatorPending)) {
/*  506 */       this._op2.setLength(0);
/*  507 */       calculateResult(false);
/*      */     }
/*      */     else {
/*  510 */       if (this._op2.length() == 0) {
/*  511 */         beep();
/*  512 */         return;
/*      */       }
/*      */ 
/*  515 */       this._isFakedEqualPressed = false;
/*  516 */       calculateResult(true);
/*  517 */       this._op1.setLength(0);
/*  518 */       this._op1.append(Double.valueOf(this._result).toString());
/*  519 */       this._op2.setLength(0);
/*      */     }
/*      */ 
/*  522 */     setOperator(operator);
/*  523 */     this._negationOp1 = false;
/*  524 */     this._clearOperatorPending = false;
/*      */   }
/*      */ 
/*      */   protected void beep() {
/*  528 */     PortingUtils.notifyUser();
/*      */   }
/*      */ 
/*      */   private void negativePressed(StringBuffer buf) {
/*  532 */     if (buf.length() == 0) {
/*  533 */       return;
/*      */     }
/*  535 */     if (buf.charAt(0) == '-') {
/*  536 */       buf.deleteCharAt(0);
/*      */     }
/*      */     else
/*  539 */       buf.insert(0, '-');
/*      */   }
/*      */ 
/*      */   private void backspacePressed(StringBuffer buf)
/*      */   {
/*  544 */     if (buf.length() == 0) {
/*  545 */       return;
/*      */     }
/*  547 */     buf.deleteCharAt(buf.length() - 1);
/*      */   }
/*      */ 
/*      */   public void updateResult()
/*      */   {
/*  555 */     this._isFakedEqualPressed = false;
/*  556 */     calculateResult(true);
/*      */   }
/*      */ 
/*      */   private void calculateResult(boolean equalPressed) {
/*  560 */     if (getOperator() == -1) {
/*  561 */       return;
/*      */     }
/*      */ 
/*  564 */     if (this._op1.length() == 0) {
/*  565 */       beep();
/*  566 */       return;
/*      */     }
/*      */ 
/*  569 */     if (equalPressed) {
/*  570 */       if (this._op2.length() == 0) {
/*  571 */         this._op2.append(this._op1);
/*      */       }
/*      */     }
/*  574 */     else if (this._op2.length() == 0)
/*  575 */       return; Double op1;
/*      */     Double op2;
/*      */     try {
/*  580 */       op1 = Double.valueOf(getDisplayFormat().parse(this._op1.toString()).doubleValue());
/*  581 */       op2 = Double.valueOf(getDisplayFormat().parse(this._op2.toString()).doubleValue());
/*      */     }
/*      */     catch (ParseException e) {
/*  584 */       op1 = Double.valueOf(0.0D);
/*  585 */       op2 = Double.valueOf(0.0D);
/*      */     }
/*  587 */     if (!this._isFakedEqualPressed) {
/*      */       try {
/*  589 */         switch (getOperator()) {
/*      */         case 0:
/*  591 */           this._result = (op1.doubleValue() + op2.doubleValue());
/*  592 */           break;
/*      */         case 1:
/*  594 */           this._result = (op1.doubleValue() - op2.doubleValue());
/*  595 */           break;
/*      */         case 2:
/*  597 */           this._result = (op1.doubleValue() * op2.doubleValue());
/*  598 */           break;
/*      */         case 3:
/*  600 */           if (op2.doubleValue() == 0.0D) {
/*  601 */             this._result = (0.0D / 0.0D);
/*  602 */             this._overflow = true;
/*      */           }
/*      */           else {
/*  605 */             this._result = (op1.doubleValue() / op2.doubleValue());
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  611 */         this._overflow = true;
/*      */       }
/*      */     }
/*      */ 
/*  615 */     if (this._overflow) {
/*  616 */       setDisplayText("E");
/*      */     }
/*      */     else {
/*  619 */       setResultCalculated(true);
/*  620 */       this._op1.setLength(0);
/*  621 */       if (this._displayFormat != null) {
/*  622 */         String displayText = this._displayFormat.format(this._result);
/*  623 */         setDisplayText(displayText);
/*      */       }
/*      */       else {
/*  626 */         setDisplayText("" + this._result);
/*      */       }
/*  628 */       this._op1.append(getDisplayText());
/*  629 */       this._negationOp1 = true;
/*  630 */       this._backspaceOp1 = true;
/*  631 */       this._backspaceOp2 = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void clearOps() {
/*  636 */     setOperator(-1);
/*  637 */     this._op1.setLength(0);
/*  638 */     this._op2.setLength(0);
/*      */   }
/*      */ 
/*      */   public void clear()
/*      */   {
/*  645 */     clearOps();
/*  646 */     this._overflow = false;
/*  647 */     this._clearOperatorPending = false;
/*  648 */     setDisplayText("0");
/*      */   }
/*      */ 
/*      */   public double getResult()
/*      */   {
/*  657 */     return this._result;
/*      */   }
/*      */ 
/*      */   public String getDisplayText()
/*      */   {
/*  666 */     return this._displayText;
/*      */   }
/*      */ 
/*      */   public void setDisplayText(String displayText)
/*      */   {
/*  675 */     String old = this._displayText;
/*  676 */     this._displayText = displayText;
/*  677 */     firePropertyChange("displayText", old, this._displayText);
/*      */   }
/*      */ 
/*      */   public int getOperator()
/*      */   {
/*  686 */     return this._operator;
/*      */   }
/*      */ 
/*      */   public void setOperator(int operator)
/*      */   {
/*  695 */     int old = this._operator;
/*  696 */     if (old != operator) {
/*  697 */       this._operator = operator;
/*  698 */       firePropertyChange("operator", new Integer(old), new Integer(operator));
/*      */     }
/*      */   }
/*      */ 
/*      */   public void actionPerformed(ActionEvent e)
/*      */   {
/*  782 */     Object source = e.getSource();
/*  783 */     if (this._addButton == source) {
/*  784 */       input('+');
/*      */     }
/*  786 */     else if (this._minusButton == source) {
/*  787 */       input('-');
/*      */     }
/*  789 */     else if (this._multiplyButton == source) {
/*  790 */       input('*');
/*      */     }
/*  792 */     else if (this._divideButton == source) {
/*  793 */       input('/');
/*      */     }
/*  795 */     else if (this._equalButton == source) {
/*  796 */       this._isFakedEqualPressed = ((e.getActionCommand() != null) && (e.getActionCommand().equals("Faked")));
/*  797 */       input('=');
/*      */     }
/*  799 */     else if (this._pointButton == source) {
/*  800 */       input(this._actualCharPoint);
/*      */     }
/*  802 */     else if (this._negativeButton == source) {
/*  803 */       input('!');
/*      */     }
/*  805 */     else if (this._backspaceButton == source) {
/*  806 */       input('<');
/*      */     }
/*  808 */     else if (this._clearButton == source) {
/*  809 */       input('c');
/*      */     }
/*      */     else {
/*  812 */       boolean found = false;
/*  813 */       for (int i = 0; i <= 9; i++) {
/*  814 */         if (this._numberButtons[i] == source) {
/*  815 */           input(("" + i).charAt(0));
/*  816 */           found = true;
/*  817 */           break;
/*      */         }
/*      */       }
/*  820 */       if (!found)
/*  821 */         if ((e.getActionCommand() != null) && (e.getActionCommand().length() > 0)) {
/*  822 */           fakePressButton(e.getActionCommand().charAt(0));
/*      */         }
/*      */         else
/*  825 */           fakePressButton('=');
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void fakePressButton(AbstractButton button)
/*      */   {
/*  838 */     actionPerformed(new ActionEvent(button, 0, null));
/*      */   }
/*      */ 
/*      */   private void fakePressButton(char c) {
/*  842 */     if (c == this._actualCharPoint) {
/*  843 */       fakePressButton(this._pointButton);
/*  844 */       return;
/*      */     }
/*  846 */     switch (c) {
/*      */     case 'c':
/*  848 */       fakePressButton(this._clearButton);
/*  849 */       break;
/*      */     case '<':
/*  851 */       fakePressButton(this._backspaceButton);
/*  852 */       break;
/*      */     case '=':
/*  854 */       fakePressButton(this._equalButton);
/*  855 */       break;
/*      */     case '!':
/*  857 */       fakePressButton(this._negativeButton);
/*  858 */       break;
/*      */     case '+':
/*  860 */       fakePressButton(this._addButton);
/*  861 */       break;
/*      */     case '-':
/*  863 */       fakePressButton(this._minusButton);
/*  864 */       break;
/*      */     case '*':
/*  866 */       fakePressButton(this._multiplyButton);
/*  867 */       break;
/*      */     case '/':
/*  869 */       fakePressButton(this._divideButton);
/*  870 */       break;
/*      */     case '0':
/*  872 */       fakePressButton(this._numberButtons[0]);
/*  873 */       break;
/*      */     case '1':
/*  875 */       fakePressButton(this._numberButtons[1]);
/*  876 */       break;
/*      */     case '2':
/*  878 */       fakePressButton(this._numberButtons[2]);
/*  879 */       break;
/*      */     case '3':
/*  881 */       fakePressButton(this._numberButtons[3]);
/*  882 */       break;
/*      */     case '4':
/*  884 */       fakePressButton(this._numberButtons[4]);
/*  885 */       break;
/*      */     case '5':
/*  887 */       fakePressButton(this._numberButtons[5]);
/*  888 */       break;
/*      */     case '6':
/*  890 */       fakePressButton(this._numberButtons[6]);
/*  891 */       break;
/*      */     case '7':
/*  893 */       fakePressButton(this._numberButtons[7]);
/*  894 */       break;
/*      */     case '8':
/*  896 */       fakePressButton(this._numberButtons[8]);
/*  897 */       break;
/*      */     case '9':
/*  899 */       fakePressButton(this._numberButtons[9]);
/*      */     case '"':
/*      */     case '#':
/*      */     case '$':
/*      */     case '%':
/*      */     case '&':
/*      */     case '\'':
/*      */     case '(':
/*      */     case ')':
/*      */     case ',':
/*      */     case '.':
/*      */     case ':':
/*      */     case ';':
/*      */     case '>':
/*      */     case '?':
/*      */     case '@':
/*      */     case 'A':
/*      */     case 'B':
/*      */     case 'C':
/*      */     case 'D':
/*      */     case 'E':
/*      */     case 'F':
/*      */     case 'G':
/*      */     case 'H':
/*      */     case 'I':
/*      */     case 'J':
/*      */     case 'K':
/*      */     case 'L':
/*      */     case 'M':
/*      */     case 'N':
/*      */     case 'O':
/*      */     case 'P':
/*      */     case 'Q':
/*      */     case 'R':
/*      */     case 'S':
/*      */     case 'T':
/*      */     case 'U':
/*      */     case 'V':
/*      */     case 'W':
/*      */     case 'X':
/*      */     case 'Y':
/*      */     case 'Z':
/*      */     case '[':
/*      */     case '\\':
/*      */     case ']':
/*      */     case '^':
/*      */     case '_':
/*      */     case '`':
/*      */     case 'a':
/*      */     case 'b': }  } 
/*  910 */   public NumberFormat getDisplayFormat() { return this._displayFormat;
/*      */   }
/*      */ 
/*      */   public void setDisplayFormat(NumberFormat displayFormat)
/*      */   {
/*  919 */     this._displayFormat = displayFormat;
/*      */   }
/*      */ 
/*      */   public void commit()
/*      */   {
/*  927 */     if (!this._clearOperatorPending)
/*  928 */       actionPerformed(new ActionEvent(this._equalButton, 0, "Faked"));
/*      */   }
/*      */ 
/*      */   public int getButtonWidth()
/*      */   {
/*  938 */     return this._buttonWidth;
/*      */   }
/*      */ 
/*      */   public void setButtonWidth(int buttonWidth)
/*      */   {
/*  947 */     this._buttonWidth = buttonWidth;
/*      */   }
/*      */ 
/*      */   public int getButtonHeight()
/*      */   {
/*  956 */     return this._buttonHeight;
/*      */   }
/*      */ 
/*      */   public void setButtonHeight(int buttonHeight)
/*      */   {
/*  965 */     this._buttonHeight = buttonHeight;
/*      */   }
/*      */ 
/*      */   public int getButtonGap()
/*      */   {
/*  974 */     return this._buttonGap;
/*      */   }
/*      */ 
/*      */   public void setButtonGap(int buttonGap) {
/*  978 */     this._buttonGap = buttonGap;
/*      */   }
/*      */ 
/*      */   protected boolean isCellEditor()
/*      */   {
/*  988 */     return false;
/*      */   }
/*      */ 
/*      */   public void setInitialValue(String value) {
/*  992 */     this._op1.setLength(0);
/*  993 */     this._op1.append(value);
/*  994 */     this._backspaceOp1 = true;
/*  995 */     this._backspaceOp2 = false;
/*  996 */     setDisplayText(this._op1.toString());
/*      */   }
/*      */ 
/*      */   public static void main(String[] args) {
/* 1000 */     Calculator calculator = new Calculator();
/* 1001 */     calculator.input('1');
/* 1002 */     calculator.input('0');
/* 1003 */     calculator.input('*');
/* 1004 */     calculator.input('2');
/* 1005 */     calculator.input('4');
/* 1006 */     calculator.input('=');
/*      */   }
/*      */ 
/*      */   private class CalculatorLayoutManager
/*      */     implements LayoutManager
/*      */   {
/*      */     public CalculatorLayoutManager()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void addLayoutComponent(String name, Component comp)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void removeLayoutComponent(Component comp)
/*      */     {
/*      */     }
/*      */ 
/*      */     public Dimension preferredLayoutSize(Container parent)
/*      */     {
/*  714 */       return minimumLayoutSize(parent);
/*      */     }
/*      */ 
/*      */     public Dimension minimumLayoutSize(Container parent) {
/*  718 */       return new Dimension(Calculator.this.getButtonWidth() * 4 + Calculator.this.getButtonGap() * 3, Calculator.this.getButtonHeight() * 5 + Calculator.this.getButtonGap() * 4);
/*      */     }
/*      */ 
/*      */     public void layoutContainer(Container parent) {
/*  722 */       int x = 0;
/*  723 */       int y = 0;
/*      */ 
/*  725 */       int w = Calculator.this.getButtonWidth();
/*  726 */       int h = Calculator.this.getButtonHeight();
/*  727 */       int gap = Calculator.this.getButtonGap();
/*      */ 
/*  729 */       Calculator.this._numberButtons[7].setBounds(x, y, w, h);
/*  730 */       x += w + gap;
/*  731 */       Calculator.this._numberButtons[8].setBounds(x, y, w, h);
/*  732 */       x += w + gap;
/*  733 */       Calculator.this._numberButtons[9].setBounds(x, y, w, h);
/*  734 */       x += w + gap;
/*  735 */       Calculator.this._divideButton.setBounds(x, y, w, h);
/*      */ 
/*  737 */       x = 0;
/*  738 */       y += h + gap;
/*      */ 
/*  740 */       Calculator.this._numberButtons[4].setBounds(x, y, w, h);
/*  741 */       x += w + gap;
/*  742 */       Calculator.this._numberButtons[5].setBounds(x, y, w, h);
/*  743 */       x += w + gap;
/*  744 */       Calculator.this._numberButtons[6].setBounds(x, y, w, h);
/*  745 */       x += w + gap;
/*  746 */       Calculator.this._multiplyButton.setBounds(x, y, w, h);
/*      */ 
/*  748 */       x = 0;
/*  749 */       y += h + gap;
/*      */ 
/*  751 */       Calculator.this._numberButtons[1].setBounds(x, y, w, h);
/*  752 */       x += w + gap;
/*  753 */       Calculator.this._numberButtons[2].setBounds(x, y, w, h);
/*  754 */       x += w + gap;
/*  755 */       Calculator.this._numberButtons[3].setBounds(x, y, w, h);
/*  756 */       x += w + gap;
/*  757 */       Calculator.this._minusButton.setBounds(x, y, w, h);
/*      */ 
/*  759 */       x = 0;
/*  760 */       y += h + gap;
/*      */ 
/*  762 */       Calculator.this._numberButtons[0].setBounds(x, y, w, h);
/*  763 */       x += w + gap;
/*  764 */       Calculator.this._pointButton.setBounds(x, y, w, h);
/*  765 */       x += w + gap;
/*  766 */       Calculator.this._negativeButton.setBounds(x, y, w, h);
/*  767 */       x += w + gap;
/*  768 */       Calculator.this._addButton.setBounds(x, y, w, h);
/*      */ 
/*  770 */       x = 0;
/*  771 */       y += h + gap;
/*      */ 
/*  773 */       Calculator.this._clearButton.setBounds(x, y, w, h);
/*  774 */       x += w + gap;
/*  775 */       Calculator.this._backspaceButton.setBounds(x, y, w, h);
/*  776 */       x += w + gap;
/*  777 */       Calculator.this._equalButton.setBounds(x, y, w * 2 + gap, h);
/*      */     }
/*      */   }
/*      */ 
/*      */   class ToggleNegativeIcon
/*      */     implements Icon
/*      */   {
/*      */     public ToggleNegativeIcon()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void paintIcon(Component c, Graphics g, int x, int y)
/*      */     {
/*  333 */       Color old = g.getColor();
/*  334 */       Object save = JideSwingUtilities.setupShapeAntialiasing(g);
/*  335 */       g.setColor(c.getForeground());
/*  336 */       g.drawLine(x, y + 2, x + 6, y + 2);
/*  337 */       g.drawLine(x, y + 7, x + 6, y + 7);
/*  338 */       g.drawLine(x + 3, y, x + 3, y + 5);
/*  339 */       g.setColor(old);
/*  340 */       JideSwingUtilities.restoreShapeAntialiasing(g, save);
/*      */     }
/*      */ 
/*      */     public int getIconWidth() {
/*  344 */       return 7;
/*      */     }
/*      */ 
/*      */     public int getIconHeight() {
/*  348 */       return 7;
/*      */     }
/*      */   }
/*      */ 
/*      */   class BackspaceIcon
/*      */     implements Icon
/*      */   {
/*      */     public BackspaceIcon()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void paintIcon(Component c, Graphics g, int x, int y)
/*      */     {
/*  309 */       Object save = JideSwingUtilities.setupShapeAntialiasing(g);
/*  310 */       Color old = g.getColor();
/*  311 */       g.setColor(c.getForeground());
/*  312 */       g.drawLine(x, y + 3, x + 3, y);
/*  313 */       g.drawLine(x, y + 3, x + 3, y + 6);
/*  314 */       g.drawLine(x + 3, y + 3, x + 7, y + 3);
/*  315 */       g.setColor(old);
/*  316 */       JideSwingUtilities.restoreShapeAntialiasing(g, save);
/*      */     }
/*      */ 
/*      */     public int getIconWidth() {
/*  320 */       return 7;
/*      */     }
/*      */ 
/*      */     public int getIconHeight() {
/*  324 */       return 7;
/*      */     }
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.Calculator
 * JD-Core Version:    0.6.0
 */
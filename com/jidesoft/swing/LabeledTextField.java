/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.utils.SystemInfo;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import javax.swing.event.DocumentListener;
/*     */ import javax.swing.text.Document;
/*     */ 
/*     */ public class LabeledTextField extends JPanel
/*     */ {
/*     */   protected JTextField _textField;
/*     */   protected JLabel _label;
/*     */   protected AbstractButton _button;
/*     */   protected String _labelText;
/*     */   protected Icon _icon;
/*     */   protected String _hintText;
/*     */   protected JLabel _hintLabel;
/*     */   protected PopupMenuCustomizer _customizer;
/*     */   protected KeyStroke _contextMenuKeyStroke;
/*     */ 
/*     */   public LabeledTextField()
/*     */   {
/*  46 */     this(null, null);
/*     */   }
/*     */ 
/*     */   public LabeledTextField(Icon icon) {
/*  50 */     this(icon, null);
/*     */   }
/*     */ 
/*     */   public LabeledTextField(Icon icon, String labelText)
/*     */   {
/*  55 */     this._icon = icon;
/*  56 */     this._labelText = labelText;
/*  57 */     initComponent();
/*     */   }
/*     */ 
/*     */   protected void initComponent() {
/*  61 */     this._label = createLabel();
/*  62 */     if (this._label != null)
/*  63 */       this._label.addMouseListener(new MouseAdapter()
/*     */       {
/*     */         public void mouseClicked(MouseEvent e)
/*     */         {
/*     */         }
/*     */ 
/*     */         public void mousePressed(MouseEvent e) {
/*  70 */           LabeledTextField.this.showContextMenu();
/*     */         }
/*     */ 
/*     */         public void mouseReleased(MouseEvent e)
/*     */         {
/*     */         }
/*     */       });
/*  78 */     this._button = createButton();
/*  79 */     this._textField = createTextField();
/*  80 */     initLayout(this._label, this._textField, this._button);
/*  81 */     setContextMenuKeyStroke(KeyStroke.getKeyStroke(40, 512));
/*  82 */     registerContextMenuKeyStroke(getContextMenuKeyStroke());
/*  83 */     updateUI();
/*     */   }
/*     */ 
/*     */   private void registerContextMenuKeyStroke(KeyStroke keyStroke) {
/*  87 */     if (keyStroke != null)
/*  88 */       registerKeyboardAction(new ActionListener() {
/*     */         public void actionPerformed(ActionEvent e) {
/*  90 */           LabeledTextField.this.showContextMenu();
/*     */         }
/*     */       }
/*     */       , keyStroke, 1);
/*     */   }
/*     */ 
/*     */   private void unregisterContextMenuKeyStroke(KeyStroke keyStroke)
/*     */   {
/*  97 */     if (keyStroke != null)
/*  98 */       unregisterKeyboardAction(keyStroke);
/*     */   }
/*     */ 
/*     */   protected void showContextMenu()
/*     */   {
/* 105 */     if (isEnabled()) {
/* 106 */       JPopupMenu menu = createContextMenu();
/* 107 */       PopupMenuCustomizer customizer = getPopupMenuCustomizer();
/* 108 */       if ((customizer != null) && (menu != null)) {
/* 109 */         customizer.customize(this, menu);
/*     */       }
/* 111 */       if ((menu != null) && (menu.getComponentCount() > 0)) {
/* 112 */         Point location = this._label.getLocation();
/* 113 */         menu.show(this, location.x + (this._label.getIcon() == null ? 1 : this._label.getIcon().getIconWidth() / 2), location.y + this._label.getHeight() + 1);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void initLayout(JLabel label, JTextField field, AbstractButton button)
/*     */   {
/* 127 */     setLayout(new BorderLayout(3, 3));
/* 128 */     if (label != null) {
/* 129 */       add(label, "Before");
/*     */     }
/* 131 */     this._hintLabel = new JLabel(getHintText());
/* 132 */     this._hintLabel.setOpaque(false);
/* 133 */     Color foreground = UIDefaultsLookup.getColor("Label.disabledForeground");
/* 134 */     if (foreground == null) {
/* 135 */       foreground = Color.GRAY;
/*     */     }
/* 137 */     this._hintLabel.setForeground(foreground);
/* 138 */     DefaultOverlayable overlayable = new DefaultOverlayable(field, this._hintLabel, 7);
/* 139 */     overlayable.setOpaque(false);
/* 140 */     field.addFocusListener(new FocusListener(field, overlayable) {
/*     */       public void focusLost(FocusEvent e) {
/* 142 */         LabeledTextField.this.adjustOverlay(this.val$field, this.val$overlayable);
/*     */       }
/*     */ 
/*     */       public void focusGained(FocusEvent e) {
/* 146 */         LabeledTextField.this.adjustOverlay(this.val$field, this.val$overlayable);
/*     */       }
/*     */     });
/* 149 */     field.getDocument().addDocumentListener(new DocumentListener(field, overlayable) {
/*     */       public void insertUpdate(DocumentEvent e) {
/* 151 */         LabeledTextField.this.adjustOverlay(this.val$field, this.val$overlayable);
/*     */       }
/*     */ 
/*     */       public void removeUpdate(DocumentEvent e) {
/* 155 */         LabeledTextField.this.adjustOverlay(this.val$field, this.val$overlayable);
/*     */       }
/*     */ 
/*     */       public void changedUpdate(DocumentEvent e) {
/* 159 */         LabeledTextField.this.adjustOverlay(this.val$field, this.val$overlayable);
/*     */       }
/*     */     });
/* 162 */     add(overlayable);
/* 163 */     if (button != null)
/* 164 */       add(button, "After");
/*     */   }
/*     */ 
/*     */   private void adjustOverlay(JTextField field, DefaultOverlayable overlayable)
/*     */   {
/* 169 */     if (field.hasFocus()) {
/* 170 */       overlayable.setOverlayVisible(false);
/*     */     }
/*     */     else {
/* 173 */       String text = field.getText();
/* 174 */       if ((text != null) && (text.length() != 0)) {
/* 175 */         overlayable.setOverlayVisible(false);
/*     */       }
/*     */       else
/* 178 */         overlayable.setOverlayVisible(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected JTextField createTextField()
/*     */   {
/* 190 */     JTextField textField = new OverlayTextField();
/* 191 */     SelectAllUtils.install(textField);
/* 192 */     JideSwingUtilities.setTextComponentTransparent(textField);
/* 193 */     textField.setColumns(20);
/* 194 */     return textField;
/*     */   }
/*     */ 
/*     */   protected JidePopupMenu createContextMenu()
/*     */   {
/* 203 */     return new JidePopupMenu();
/*     */   }
/*     */ 
/*     */   public void updateUI()
/*     */   {
/* 208 */     super.updateUI();
/* 209 */     Border textFieldBorder = UIDefaultsLookup.getBorder("TextField.border");
/* 210 */     if (textFieldBorder != null) {
/* 211 */       boolean big = textFieldBorder.getBorderInsets(this).top >= 2;
/* 212 */       if (big)
/* 213 */         setBorder(textFieldBorder);
/*     */       else
/* 215 */         setBorder(BorderFactory.createCompoundBorder(textFieldBorder, BorderFactory.createEmptyBorder(2, 2, 2, 2)));
/*     */     }
/*     */     else {
/* 218 */       setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(1), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
/*     */     }
/* 220 */     if (isEnabled()) {
/* 221 */       LookAndFeel.installColors(this, "TextField.background", "TextField.foreground");
/*     */     }
/*     */     else {
/* 224 */       LookAndFeel.installColors(this, "TextField.inactiveBackground", "TextField.foreground");
/*     */     }
/* 226 */     if ((textFieldBorder != null) && (this._textField != null))
/* 227 */       this._textField.setBorder(BorderFactory.createEmptyBorder());
/*     */   }
/*     */ 
/*     */   protected AbstractButton createButton()
/*     */   {
/* 239 */     return null;
/*     */   }
/*     */ 
/*     */   protected JLabel createLabel()
/*     */   {
/* 248 */     JLabel label = new JLabel(this._icon);
/* 249 */     label.setText(this._labelText);
/* 250 */     return label;
/*     */   }
/*     */ 
/*     */   public void setLabelText(String text)
/*     */   {
/* 259 */     this._labelText = text;
/* 260 */     if (this._label != null)
/* 261 */       this._label.setText(text);
/*     */   }
/*     */ 
/*     */   public String getLabelText()
/*     */   {
/* 271 */     if (this._label != null) {
/* 272 */       return this._label.getText();
/*     */     }
/*     */ 
/* 275 */     return this._labelText;
/*     */   }
/*     */ 
/*     */   public void setIcon(Icon icon)
/*     */   {
/* 285 */     this._icon = icon;
/* 286 */     if (this._label != null)
/* 287 */       this._label.setIcon(icon);
/*     */   }
/*     */ 
/*     */   public Icon getIcon()
/*     */   {
/* 297 */     if (this._label != null) {
/* 298 */       return this._label.getIcon();
/*     */     }
/*     */ 
/* 301 */     return this._icon;
/*     */   }
/*     */ 
/*     */   public JLabel getLabel()
/*     */   {
/* 311 */     return this._label;
/*     */   }
/*     */ 
/*     */   public AbstractButton getButton()
/*     */   {
/* 320 */     return this._button;
/*     */   }
/*     */ 
/*     */   public void setColumns(int columns)
/*     */   {
/* 329 */     if (getTextField() != null)
/* 330 */       getTextField().setColumns(columns);
/*     */   }
/*     */ 
/*     */   public void setText(String text)
/*     */   {
/* 340 */     if (getTextField() != null)
/* 341 */       getTextField().setText(text);
/*     */   }
/*     */ 
/*     */   public String getText()
/*     */   {
/* 351 */     if (getTextField() != null) {
/* 352 */       return getTextField().getText();
/*     */     }
/*     */ 
/* 355 */     return null;
/*     */   }
/*     */ 
/*     */   public JTextField getTextField()
/*     */   {
/* 365 */     return this._textField;
/*     */   }
/*     */ 
/*     */   public void setEnabled(boolean enabled)
/*     */   {
/* 370 */     super.setEnabled(enabled);
/* 371 */     if (enabled) {
/* 372 */       if (getTextField() != null) {
/* 373 */         getTextField().setEnabled(true);
/*     */       }
/* 375 */       if (getLabel() != null) {
/* 376 */         getLabel().setEnabled(true);
/*     */       }
/* 378 */       if (getButton() != null) {
/* 379 */         getButton().setEnabled(true);
/*     */       }
/* 381 */       setBackground(UIDefaultsLookup.getColor("TextField.background"));
/*     */     }
/*     */     else {
/* 384 */       if (getTextField() != null) {
/* 385 */         getTextField().setEnabled(false);
/*     */       }
/* 387 */       if (getLabel() != null) {
/* 388 */         getLabel().setEnabled(false);
/*     */       }
/* 390 */       if (getButton() != null) {
/* 391 */         getButton().setEnabled(false);
/*     */       }
/* 393 */       setBackground(UIDefaultsLookup.getColor("control"));
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getBaseline(int width, int height) {
/* 398 */     if (SystemInfo.isJdk6Above()) {
/*     */       try {
/* 400 */         Method method = Component.class.getMethod("getBaseline", new Class[] { Integer.TYPE, Integer.TYPE });
/* 401 */         Object value = method.invoke(this._textField, new Object[] { Integer.valueOf(width), Integer.valueOf(height) });
/* 402 */         if ((value instanceof Integer)) {
/* 403 */           return ((Integer)value).intValue();
/*     */         }
/*     */       }
/*     */       catch (NoSuchMethodException e)
/*     */       {
/*     */       }
/*     */       catch (IllegalAccessException e)
/*     */       {
/*     */       }
/*     */       catch (InvocationTargetException e)
/*     */       {
/*     */       }
/*     */     }
/* 416 */     return -1;
/*     */   }
/*     */ 
/*     */   public String getHintText()
/*     */   {
/* 425 */     return this._hintText;
/*     */   }
/*     */ 
/*     */   public void setHintText(String hintText)
/*     */   {
/* 434 */     this._hintText = hintText;
/* 435 */     if (this._hintLabel != null)
/* 436 */       this._hintLabel.setText(this._hintText);
/*     */   }
/*     */ 
/*     */   public PopupMenuCustomizer getPopupMenuCustomizer()
/*     */   {
/* 446 */     return this._customizer;
/*     */   }
/*     */ 
/*     */   public void setPopupMenuCustomizer(PopupMenuCustomizer customizer)
/*     */   {
/* 469 */     this._customizer = customizer;
/*     */   }
/*     */ 
/*     */   public KeyStroke getContextMenuKeyStroke()
/*     */   {
/* 479 */     if (this._contextMenuKeyStroke == null) {
/* 480 */       this._contextMenuKeyStroke = (!SystemInfo.isMacOSX() ? KeyStroke.getKeyStroke(121, 1) : null);
/*     */     }
/* 482 */     return this._contextMenuKeyStroke;
/*     */   }
/*     */ 
/*     */   public void setContextMenuKeyStroke(KeyStroke contextMenuKeyStroke)
/*     */   {
/* 492 */     if (this._contextMenuKeyStroke != null) {
/* 493 */       unregisterContextMenuKeyStroke(this._contextMenuKeyStroke);
/*     */     }
/* 495 */     this._contextMenuKeyStroke = contextMenuKeyStroke;
/* 496 */     if (this._contextMenuKeyStroke != null)
/* 497 */       registerContextMenuKeyStroke(this._contextMenuKeyStroke);
/*     */   }
/*     */ 
/*     */   public static abstract interface PopupMenuCustomizer
/*     */   {
/*     */     public abstract void customize(LabeledTextField paramLabeledTextField, JPopupMenu paramJPopupMenu);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.LabeledTextField
 * JD-Core Version:    0.6.0
 */
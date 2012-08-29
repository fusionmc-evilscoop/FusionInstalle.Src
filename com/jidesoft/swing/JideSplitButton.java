/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.plaf.LookAndFeelFactory;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.utils.SystemInfo;
/*     */ import java.awt.Color;
/*     */ import java.awt.event.ActionEvent;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.ActionMap;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ButtonUI;
/*     */ 
/*     */ public class JideSplitButton extends JideMenu
/*     */   implements ButtonStyle, ComponentStateSupport
/*     */ {
/*     */   private static final String uiClassID = "JideSplitButtonUI";
/*  32 */   private int _buttonStyle = 0;
/*     */ 
/*  34 */   private boolean _alwaysDropdown = false;
/*     */   public static final String PROPERTY_ALWAYS_DROPDOWN = "alwaysDropdown";
/*     */   public static final String ACTION_PROPERTY_SPLIT_BUTTON_ENABLED = "JideSplitButtonEnabled";
/*     */   private Color _defaultForeground;
/*     */   private Color _rolloverBackground;
/*     */   private Color _selectedBackground;
/*     */   private Color _pressedBackground;
/*     */   private Color _rolloverForeground;
/*     */   private Color _selectedForeground;
/*     */   private Color _pressedForeground;
/*     */ 
/*     */   public JideSplitButton()
/*     */   {
/*  40 */     initComponent();
/*     */   }
/*     */ 
/*     */   public JideSplitButton(String s) {
/*  44 */     super(s);
/*  45 */     initComponent();
/*     */   }
/*     */ 
/*     */   public JideSplitButton(String s, Icon icon) {
/*  49 */     super(s);
/*  50 */     setIcon(icon);
/*  51 */     initComponent();
/*     */   }
/*     */ 
/*     */   public JideSplitButton(Icon icon) {
/*  55 */     super("");
/*  56 */     setIcon(icon);
/*  57 */     initComponent();
/*     */   }
/*     */ 
/*     */   public JideSplitButton(Action a) {
/*  61 */     super(a);
/*  62 */     initComponent();
/*     */   }
/*     */ 
/*     */   protected void initComponent() {
/*  66 */     setModel(new DefaultSplitButtonModel());
/*  67 */     if (getAction() != null) {
/*  68 */       configurePropertiesFromAction(getAction());
/*     */     }
/*  70 */     setFocusable(true);
/*  71 */     setRequestFocusEnabled(false);
/*     */   }
/*     */ 
/*     */   public ButtonUI getUI()
/*     */   {
/*  81 */     return (ButtonUI)this.ui;
/*     */   }
/*     */ 
/*     */   public void setUI(ButtonUI ui)
/*     */   {
/*  92 */     super.setUI(ui);
/*     */   }
/*     */ 
/*     */   public void updateUI()
/*     */   {
/* 103 */     if (UIDefaultsLookup.get("JideSplitButtonUI") == null) {
/* 104 */       LookAndFeelFactory.installJideExtension();
/*     */     }
/* 106 */     setUI(UIManager.getUI(this));
/* 107 */     invalidate();
/*     */   }
/*     */ 
/*     */   public String getUIClassID()
/*     */   {
/* 121 */     return "JideSplitButtonUI";
/*     */   }
/*     */ 
/*     */   public boolean isButtonSelected()
/*     */   {
/* 131 */     return ((this.model instanceof SplitButtonModel)) && (((DefaultSplitButtonModel)this.model).isButtonSelected());
/*     */   }
/*     */ 
/*     */   public void setButtonSelected(boolean b)
/*     */   {
/* 141 */     if ((this.model instanceof SplitButtonModel))
/* 142 */       ((DefaultSplitButtonModel)this.model).setButtonSelected(b);
/*     */   }
/*     */ 
/*     */   public boolean isButtonEnabled()
/*     */   {
/* 152 */     return ((this.model instanceof SplitButtonModel)) && (((DefaultSplitButtonModel)this.model).isButtonEnabled());
/*     */   }
/*     */ 
/*     */   public void setButtonEnabled(boolean b)
/*     */   {
/* 161 */     if ((this.model instanceof SplitButtonModel))
/* 162 */       ((DefaultSplitButtonModel)this.model).setButtonEnabled(b);
/*     */   }
/*     */ 
/*     */   public int getButtonStyle()
/*     */   {
/* 172 */     return this._buttonStyle;
/*     */   }
/*     */ 
/*     */   public void setButtonStyle(int buttonStyle)
/*     */   {
/* 181 */     if ((buttonStyle < 0) || (buttonStyle > 2)) {
/* 182 */       throw new IllegalArgumentException("Only TOOLBAR_STYLE, TOOLBOX_STYLE, and FLAT_STYLE are supported");
/*     */     }
/* 184 */     if (buttonStyle == this._buttonStyle) {
/* 185 */       return;
/*     */     }
/* 187 */     int oldStyle = this._buttonStyle;
/* 188 */     this._buttonStyle = buttonStyle;
/*     */ 
/* 190 */     firePropertyChange("buttonStyle", oldStyle, this._buttonStyle);
/*     */   }
/*     */ 
/*     */   public boolean isAlwaysDropdown()
/*     */   {
/* 200 */     return this._alwaysDropdown;
/*     */   }
/*     */ 
/*     */   public void setAlwaysDropdown(boolean alwaysDropdown)
/*     */   {
/* 210 */     if (this._alwaysDropdown != alwaysDropdown) {
/* 211 */       boolean old = this._alwaysDropdown;
/* 212 */       this._alwaysDropdown = alwaysDropdown;
/* 213 */       firePropertyChange("alwaysDropdown", old, alwaysDropdown);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setText(String text)
/*     */   {
/* 220 */     Boolean hide = (Boolean)getClientProperty("hideActionText");
/* 221 */     if ((hide == null) || (Boolean.FALSE.equals(hide)))
/* 222 */       super.setText(text);
/*     */   }
/*     */ 
/*     */   public Color getDefaultForeground()
/*     */   {
/* 235 */     return this._defaultForeground;
/*     */   }
/*     */ 
/*     */   public void setDefaultForeground(Color defaultForeground) {
/* 239 */     this._defaultForeground = defaultForeground;
/*     */   }
/*     */ 
/*     */   private Color getRolloverBackground() {
/* 243 */     return this._rolloverBackground;
/*     */   }
/*     */ 
/*     */   private void setRolloverBackground(Color rolloverBackground) {
/* 247 */     this._rolloverBackground = rolloverBackground;
/*     */   }
/*     */ 
/*     */   private Color getSelectedBackground() {
/* 251 */     return this._selectedBackground;
/*     */   }
/*     */ 
/*     */   private void setSelectedBackground(Color selectedBackground) {
/* 255 */     this._selectedBackground = selectedBackground;
/*     */   }
/*     */ 
/*     */   private Color getPressedBackground() {
/* 259 */     return this._pressedBackground;
/*     */   }
/*     */ 
/*     */   private void setPressedBackground(Color pressedBackground) {
/* 263 */     this._pressedBackground = pressedBackground;
/*     */   }
/*     */ 
/*     */   private Color getRolloverForeground() {
/* 267 */     return this._rolloverForeground;
/*     */   }
/*     */ 
/*     */   private void setRolloverForeground(Color rolloverForeground) {
/* 271 */     this._rolloverForeground = rolloverForeground;
/*     */   }
/*     */ 
/*     */   private Color getSelectedForeground() {
/* 275 */     return this._selectedForeground;
/*     */   }
/*     */ 
/*     */   private void setSelectedForeground(Color selectedForeground) {
/* 279 */     this._selectedForeground = selectedForeground;
/*     */   }
/*     */ 
/*     */   private Color getPressedForeground() {
/* 283 */     return this._pressedForeground;
/*     */   }
/*     */ 
/*     */   private void setPressedForeground(Color pressedForeground) {
/* 287 */     this._pressedForeground = pressedForeground;
/*     */   }
/*     */ 
/*     */   public Color getBackgroundOfState(int state)
/*     */   {
/* 304 */     switch (state) {
/*     */     case 0:
/* 306 */       return getBackground();
/*     */     case 2:
/* 308 */       return getRolloverBackground();
/*     */     case 3:
/* 310 */       return getSelectedBackground();
/*     */     case 1:
/* 312 */       return getPressedBackground();
/*     */     }
/* 314 */     return null;
/*     */   }
/*     */ 
/*     */   public void setBackgroundOfState(int state, Color color)
/*     */   {
/* 331 */     switch (state) {
/*     */     case 0:
/* 333 */       setBackground(color);
/* 334 */       break;
/*     */     case 2:
/* 336 */       setRolloverBackground(color);
/* 337 */       break;
/*     */     case 3:
/* 339 */       setSelectedBackground(color);
/* 340 */       break;
/*     */     case 1:
/* 342 */       setPressedBackground(color);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Color getForegroundOfState(int state)
/*     */   {
/* 361 */     switch (state) {
/*     */     case 0:
/* 363 */       return getDefaultForeground();
/*     */     case 2:
/* 365 */       return getRolloverForeground();
/*     */     case 3:
/* 367 */       return getSelectedForeground();
/*     */     case 1:
/* 369 */       return getPressedForeground();
/*     */     }
/* 371 */     return null;
/*     */   }
/*     */ 
/*     */   public void setForegroundOfState(int state, Color color)
/*     */   {
/* 389 */     switch (state) {
/*     */     case 0:
/* 391 */       setDefaultForeground(color);
/* 392 */       break;
/*     */     case 2:
/* 394 */       setRolloverForeground(color);
/* 395 */       break;
/*     */     case 3:
/* 397 */       setSelectedForeground(color);
/* 398 */       break;
/*     */     case 1:
/* 400 */       setPressedForeground(color);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doClick()
/*     */   {
/* 410 */     Action action = getActionMap().get("pressed");
/* 411 */     if (action != null)
/* 412 */       action.actionPerformed(new ActionEvent(this, 0, ""));
/*     */   }
/*     */ 
/*     */   public void doClickOnMenu()
/*     */   {
/* 420 */     Action action = getActionMap().get("downPressed");
/* 421 */     if (action != null)
/* 422 */       action.actionPerformed(new ActionEvent(this, 0, ""));
/*     */   }
/*     */ 
/*     */   protected void configurePropertiesFromAction(Action action)
/*     */   {
/* 428 */     super.configurePropertiesFromAction(action);
/* 429 */     setButtonEnabled(isSplitButtonEnabled(action));
/* 430 */     setIconFromAction(action);
/*     */   }
/*     */ 
/*     */   protected void setIconFromAction(Action action)
/*     */   {
/* 440 */     Icon icon = null;
/* 441 */     if (action != null) {
/* 442 */       icon = SystemInfo.isJdk6Above() ? (Icon)action.getValue("SwingLargeIconKey") : null;
/* 443 */       if (icon == null) {
/* 444 */         icon = (Icon)action.getValue("SmallIcon");
/*     */       }
/*     */     }
/* 447 */     setIcon(icon);
/*     */   }
/*     */ 
/*     */   protected void actionPropertyChanged(Action action, String propertyName)
/*     */   {
/* 452 */     super.actionPropertyChanged(action, propertyName);
/*     */ 
/* 454 */     if (("JideSplitButtonEnabled".equals(propertyName)) || ("enabled".equals(propertyName))) {
/* 455 */       setButtonEnabled(isSplitButtonEnabled(action));
/*     */     }
/* 457 */     else if ("SmallIcon".equals(propertyName)) {
/* 458 */       if ((!SystemInfo.isJdk6Above()) || (action.getValue("SwingLargeIconKey") == null)) {
/* 459 */         setIconFromAction(action);
/*     */       }
/*     */     }
/* 462 */     else if ((SystemInfo.isJdk6Above()) && ("SwingLargeIconKey".equals(propertyName)))
/* 463 */       setIconFromAction(action);
/*     */   }
/*     */ 
/*     */   public static boolean isSplitButtonEnabled(Action action)
/*     */   {
/* 474 */     if (action == null) {
/* 475 */       return true;
/*     */     }
/*     */ 
/* 478 */     Object value = action.getValue("JideSplitButtonEnabled");
/* 479 */     if ((value instanceof Boolean)) {
/* 480 */       return ((Boolean)value).booleanValue();
/*     */     }
/*     */ 
/* 483 */     return action.isEnabled();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.JideSplitButton
 * JD-Core Version:    0.6.0
 */
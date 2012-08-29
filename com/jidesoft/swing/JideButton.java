/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.plaf.LookAndFeelFactory;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import java.awt.Color;
/*     */ import java.awt.Cursor;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.DefaultButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ public class JideButton extends JButton
/*     */   implements Alignable, ButtonStyle, ComponentStateSupport, AlignmentSupport
/*     */ {
/*     */   private static final String uiClassID = "JideButtonUI";
/*     */   public static final String PROPERTY_ALWAYS_SHOW_HYPERLINK = "alwaysShowHyperlink";
/*  28 */   private boolean _alwaysShowHyperlink = false;
/*     */ 
/*  30 */   private int _buttonStyle = 0;
/*     */   public static final String CLIENT_PROPERTY_HIDE_POPUPMENU = "JideButton.hidePopupMenu";
/*     */   private int _orientation;
/*     */   private Color _defaultForeground;
/*     */   private Color _rolloverBackground;
/*     */   private Color _selectedBackground;
/*     */   private Color _pressedBackground;
/*     */   private Color _rolloverForeground;
/*     */   private Color _selectedForeground;
/*     */   private Color _pressedForeground;
/*     */ 
/*     */   public JideButton()
/*     */   {
/*  42 */     this(null, null);
/*     */   }
/*     */ 
/*     */   public JideButton(Icon icon)
/*     */   {
/*  51 */     this(null, icon);
/*     */   }
/*     */ 
/*     */   public JideButton(String text)
/*     */   {
/*  60 */     this(text, null);
/*     */   }
/*     */ 
/*     */   public JideButton(Action a)
/*     */   {
/*  70 */     this();
/*  71 */     setAction(a);
/*     */   }
/*     */ 
/*     */   public JideButton(String text, Icon icon)
/*     */   {
/*  82 */     setModel(new DefaultButtonModel());
/*     */ 
/*  84 */     init(text, icon);
/*  85 */     setRolloverEnabled(true);
/*  86 */     setFocusable(true);
/*  87 */     setRequestFocusEnabled(false);
/*     */   }
/*     */ 
/*     */   public void updateUI()
/*     */   {
/*  97 */     if (UIDefaultsLookup.get("JideButtonUI") == null) {
/*  98 */       LookAndFeelFactory.installJideExtension();
/*     */     }
/* 100 */     setUI(UIManager.getUI(this));
/*     */   }
/*     */ 
/*     */   public String getUIClassID()
/*     */   {
/* 114 */     return "JideButtonUI";
/*     */   }
/*     */ 
/*     */   public int getOrientation()
/*     */   {
/* 125 */     return this._orientation;
/*     */   }
/*     */ 
/*     */   public void setOrientation(int orientation) {
/* 129 */     if (this._orientation != orientation) {
/* 130 */       int old = this._orientation;
/* 131 */       this._orientation = orientation;
/* 132 */       firePropertyChange("orientation", old, orientation);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean supportVerticalOrientation()
/*     */   {
/* 142 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean supportHorizontalOrientation()
/*     */   {
/* 151 */     return true;
/*     */   }
/*     */ 
/*     */   public int getButtonStyle()
/*     */   {
/* 160 */     return this._buttonStyle;
/*     */   }
/*     */ 
/*     */   public void setButtonStyle(int buttonStyle)
/*     */   {
/* 170 */     if ((buttonStyle < 0) || (buttonStyle > 3)) {
/* 171 */       throw new IllegalArgumentException("Only TOOLBAR_STYLE, TOOLBOX_STYLE, FLAT_STYLE and HYPERLINK_STYLE are supported");
/*     */     }
/* 173 */     if (buttonStyle == this._buttonStyle) {
/* 174 */       return;
/*     */     }
/* 176 */     int oldStyle = this._buttonStyle;
/* 177 */     this._buttonStyle = buttonStyle;
/*     */ 
/* 179 */     firePropertyChange("buttonStyle", oldStyle, this._buttonStyle);
/*     */   }
/*     */ 
/*     */   public boolean isAlwaysShowHyperlink()
/*     */   {
/* 189 */     return this._alwaysShowHyperlink;
/*     */   }
/*     */ 
/*     */   public void setAlwaysShowHyperlink(boolean alwaysShowHyperlink)
/*     */   {
/* 202 */     if (this._alwaysShowHyperlink != alwaysShowHyperlink) {
/* 203 */       boolean old = this._alwaysShowHyperlink;
/* 204 */       this._alwaysShowHyperlink = alwaysShowHyperlink;
/* 205 */       firePropertyChange("alwaysShowHyperlink", old, alwaysShowHyperlink);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Cursor getCursor()
/*     */   {
/* 211 */     if ((getButtonStyle() == 3) && (isRolloverEnabled()) && (getModel().isRollover()) && (((getText() != null) && (getText().length() > 0)) || (getIcon() != null)))
/*     */     {
/* 214 */       return Cursor.getPredefinedCursor(12);
/*     */     }
/*     */ 
/* 217 */     return super.getCursor();
/*     */   }
/*     */ 
/*     */   public Color getDefaultForeground()
/*     */   {
/* 230 */     return this._defaultForeground;
/*     */   }
/*     */ 
/*     */   public void setDefaultForeground(Color defaultForeground) {
/* 234 */     this._defaultForeground = defaultForeground;
/*     */   }
/*     */ 
/*     */   private Color getRolloverBackground() {
/* 238 */     return this._rolloverBackground;
/*     */   }
/*     */ 
/*     */   private void setRolloverBackground(Color rolloverBackground) {
/* 242 */     this._rolloverBackground = rolloverBackground;
/*     */   }
/*     */ 
/*     */   private Color getSelectedBackground() {
/* 246 */     return this._selectedBackground;
/*     */   }
/*     */ 
/*     */   private void setSelectedBackground(Color selectedBackground) {
/* 250 */     this._selectedBackground = selectedBackground;
/*     */   }
/*     */ 
/*     */   private Color getPressedBackground() {
/* 254 */     return this._pressedBackground;
/*     */   }
/*     */ 
/*     */   private void setPressedBackground(Color pressedBackground) {
/* 258 */     this._pressedBackground = pressedBackground;
/*     */   }
/*     */ 
/*     */   private Color getRolloverForeground() {
/* 262 */     return this._rolloverForeground;
/*     */   }
/*     */ 
/*     */   private void setRolloverForeground(Color rolloverForeground) {
/* 266 */     this._rolloverForeground = rolloverForeground;
/*     */   }
/*     */ 
/*     */   private Color getSelectedForeground() {
/* 270 */     return this._selectedForeground;
/*     */   }
/*     */ 
/*     */   private void setSelectedForeground(Color selectedForeground) {
/* 274 */     this._selectedForeground = selectedForeground;
/*     */   }
/*     */ 
/*     */   private Color getPressedForeground() {
/* 278 */     return this._pressedForeground;
/*     */   }
/*     */ 
/*     */   private void setPressedForeground(Color pressedForeground) {
/* 282 */     this._pressedForeground = pressedForeground;
/*     */   }
/*     */ 
/*     */   public Color getBackgroundOfState(int state)
/*     */   {
/* 299 */     switch (state) {
/*     */     case 0:
/* 301 */       return getBackground();
/*     */     case 2:
/* 303 */       return getRolloverBackground();
/*     */     case 3:
/* 305 */       return getSelectedBackground();
/*     */     case 1:
/* 307 */       return getPressedBackground();
/*     */     }
/* 309 */     return null;
/*     */   }
/*     */ 
/*     */   public void setBackgroundOfState(int state, Color color)
/*     */   {
/* 326 */     switch (state) {
/*     */     case 0:
/* 328 */       setBackground(color);
/* 329 */       break;
/*     */     case 2:
/* 331 */       setRolloverBackground(color);
/* 332 */       break;
/*     */     case 3:
/* 334 */       setSelectedBackground(color);
/* 335 */       break;
/*     */     case 1:
/* 337 */       setPressedBackground(color);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Color getForegroundOfState(int state)
/*     */   {
/* 356 */     switch (state) {
/*     */     case 0:
/* 358 */       return getDefaultForeground();
/*     */     case 2:
/* 360 */       return getRolloverForeground();
/*     */     case 3:
/* 362 */       return getSelectedForeground();
/*     */     case 1:
/* 364 */       return getPressedForeground();
/*     */     }
/* 366 */     return null;
/*     */   }
/*     */ 
/*     */   public void setForegroundOfState(int state, Color color)
/*     */   {
/* 384 */     switch (state) {
/*     */     case 0:
/* 386 */       setDefaultForeground(color);
/* 387 */       break;
/*     */     case 2:
/* 389 */       setRolloverForeground(color);
/* 390 */       break;
/*     */     case 3:
/* 392 */       setSelectedForeground(color);
/* 393 */       break;
/*     */     case 1:
/* 395 */       setPressedForeground(color);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.JideButton
 * JD-Core Version:    0.6.0
 */
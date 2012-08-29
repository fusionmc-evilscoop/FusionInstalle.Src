/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.icons.JideIconsFactory;
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.MouseWheelEvent;
/*     */ import java.awt.event.MouseWheelListener;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.JScrollBar;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JViewport;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.Scrollable;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.Timer;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import javax.swing.plaf.UIResource;
/*     */ 
/*     */ public class SimpleScrollPane extends JScrollPane
/*     */   implements ChangeListener, MouseWheelListener
/*     */ {
/*     */   private AbstractButton _scrollUpButton;
/*     */   private AbstractButton _scrollDownButton;
/*     */   private AbstractButton _scrollLeftButton;
/*     */   private AbstractButton _scrollRightButton;
/*  30 */   private int _horizontalUnitIncrement = 10;
/*  31 */   private boolean _horizontalUnitIncrementSet = false;
/*     */ 
/*  33 */   private int _verticalUnitIncrement = 10;
/*  34 */   private boolean _verticalUnitIncrementSet = false;
/*     */ 
/*  36 */   private int _repeatDelay = 50;
/*  37 */   private boolean _scrollOnRollover = true;
/*     */   public static final String SCROLL_UP_BUTTON = "SCROLL_UP_BUTTON";
/*     */   public static final String SCROLL_DOWN_BUTTON = "SCROLL_DOWN_BUTTON";
/*     */   public static final String SCROLL_LEFT_BUTTON = "SCROLL_LEFT_BUTTON";
/*     */   public static final String SCROLL_RIGHT_BUTTON = "SCROLL_RIGHT_BUTTON";
/*     */ 
/*     */   public SimpleScrollPane(Component view, int vsbPolicy, int hsbPolicy)
/*     */   {
/*  57 */     setLayout(new SimpleScrollPaneLayout.UIResource());
/*  58 */     setVerticalScrollBarPolicy(vsbPolicy);
/*  59 */     setHorizontalScrollBarPolicy(hsbPolicy);
/*  60 */     setViewport(createViewport());
/*  61 */     setScrollUpButton(createScrollButton(1));
/*  62 */     setScrollDownButton(createScrollButton(5));
/*  63 */     setScrollLeftButton(createScrollButton(7));
/*  64 */     setScrollRightButton(createScrollButton(3));
/*  65 */     if (null != view) {
/*  66 */       setViewportView(view);
/*     */     }
/*  68 */     updateButtonState();
/*  69 */     setOpaque(true);
/*  70 */     setFocusable(false);
/*  71 */     if (getHorizontalScrollBar() != null) {
/*  72 */       getHorizontalScrollBar().setVisible(false);
/*  73 */       getHorizontalScrollBar().setFocusable(false);
/*     */     }
/*  75 */     if (getVerticalScrollBar() != null) {
/*  76 */       getVerticalScrollBar().setVisible(false);
/*  77 */       getVerticalScrollBar().setFocusable(false);
/*     */     }
/*  79 */     updateUI();
/*     */ 
/*  81 */     if (!getComponentOrientation().isLeftToRight()) {
/*  82 */       this.viewport.setViewPosition(new Point(2147483647, 0));
/*     */     }
/*     */ 
/*  85 */     if (isWheelScrollingEnabled())
/*  86 */       addMouseWheelListener(this);
/*     */   }
/*     */ 
/*     */   public SimpleScrollPane(Component view)
/*     */   {
/*  98 */     this(view, 20, 30);
/*     */   }
/*     */ 
/*     */   public SimpleScrollPane(int vsbPolicy, int hsbPolicy)
/*     */   {
/* 111 */     this(null, vsbPolicy, hsbPolicy);
/*     */   }
/*     */ 
/*     */   public SimpleScrollPane()
/*     */   {
/* 120 */     this(null, 20, 30);
/*     */   }
/*     */ 
/*     */   public void updateUI()
/*     */   {
/* 125 */     super.updateUI();
/* 126 */     setLayout(new SimpleScrollPaneLayout.UIResource());
/* 127 */     LookAndFeel.installBorder(this, "JideScrollPane.border");
/* 128 */     getViewport().addChangeListener(this);
/*     */   }
/*     */ 
/*     */   public void stateChanged(ChangeEvent e) {
/* 132 */     if (e.getSource() == getViewport())
/* 133 */       updateButtonState();
/*     */   }
/*     */ 
/*     */   public AbstractButton getScrollUpButton()
/*     */   {
/* 138 */     return this._scrollUpButton;
/*     */   }
/*     */ 
/*     */   public void setScrollUpButton(AbstractButton scrollUpButton) {
/* 142 */     AbstractButton old = getScrollUpButton();
/* 143 */     this._scrollUpButton = scrollUpButton;
/* 144 */     add(this._scrollUpButton, "SCROLL_UP_BUTTON");
/* 145 */     firePropertyChange("scrollUpButton", old, this._scrollUpButton);
/*     */ 
/* 147 */     revalidate();
/* 148 */     repaint();
/*     */   }
/*     */ 
/*     */   public AbstractButton getScrollDownButton() {
/* 152 */     return this._scrollDownButton;
/*     */   }
/*     */ 
/*     */   public void setScrollDownButton(AbstractButton scrollDownButton) {
/* 156 */     AbstractButton old = getScrollDownButton();
/* 157 */     this._scrollDownButton = scrollDownButton;
/* 158 */     add(this._scrollDownButton, "SCROLL_DOWN_BUTTON");
/* 159 */     firePropertyChange("scrollDownButton", old, this._scrollDownButton);
/*     */ 
/* 161 */     revalidate();
/* 162 */     repaint();
/*     */   }
/*     */ 
/*     */   public AbstractButton getScrollLeftButton() {
/* 166 */     return this._scrollLeftButton;
/*     */   }
/*     */ 
/*     */   public void setScrollLeftButton(AbstractButton scrollLeftButton) {
/* 170 */     AbstractButton old = getScrollLeftButton();
/* 171 */     this._scrollLeftButton = scrollLeftButton;
/* 172 */     add(this._scrollLeftButton, "SCROLL_LEFT_BUTTON");
/* 173 */     firePropertyChange("scrollLeftButton", old, this._scrollLeftButton);
/*     */ 
/* 175 */     revalidate();
/* 176 */     repaint();
/*     */   }
/*     */ 
/*     */   public AbstractButton getScrollRightButton() {
/* 180 */     return this._scrollRightButton;
/*     */   }
/*     */ 
/*     */   public void setScrollRightButton(AbstractButton scrollRightButton) {
/* 184 */     AbstractButton old = getScrollRightButton();
/* 185 */     this._scrollRightButton = scrollRightButton;
/* 186 */     add(this._scrollRightButton, "SCROLL_RIGHT_BUTTON");
/* 187 */     firePropertyChange("scrollRightButton", old, this._scrollRightButton);
/*     */ 
/* 189 */     revalidate();
/* 190 */     repaint();
/*     */   }
/*     */ 
/*     */   protected AbstractButton createScrollButton(int type)
/*     */   {
/* 314 */     return new ScrollButton(type);
/*     */   }
/*     */ 
/*     */   protected void updateButtonState() {
/* 318 */     Point p = this.viewport.getViewPosition();
/* 319 */     this._scrollUpButton.setEnabled(p.y != 0);
/* 320 */     this._scrollDownButton.setEnabled(p.y != this.viewport.getViewSize().height - this.viewport.getViewRect().height);
/* 321 */     this._scrollLeftButton.setEnabled(p.x != 0);
/* 322 */     this._scrollRightButton.setEnabled(p.x != this.viewport.getViewSize().width - this.viewport.getViewRect().width);
/* 323 */     revalidate();
/* 324 */     repaint();
/*     */   }
/*     */ 
/*     */   public void scroll(JViewport viewport, int type) {
/* 328 */     Point p = viewport.getViewPosition();
/*     */ 
/* 330 */     JViewport vp = getViewport();
/* 331 */     switch (type) {
/*     */     case 1:
/* 333 */       if ((!this._verticalUnitIncrementSet) && (vp != null) && ((vp.getView() instanceof Scrollable)))
/*     */       {
/* 335 */         Scrollable view = (Scrollable)(Scrollable)vp.getView();
/* 336 */         Rectangle vr = vp.getViewRect();
/* 337 */         p.y -= view.getScrollableUnitIncrement(vr, 1, -1);
/*     */       }
/*     */       else {
/* 340 */         p.y -= getVerticalUnitIncrement();
/*     */       }
/* 342 */       if (p.y >= 0) break;
/* 343 */       p.y = 0; break;
/*     */     case 5:
/* 347 */       if ((!this._verticalUnitIncrementSet) && (vp != null) && ((vp.getView() instanceof Scrollable)))
/*     */       {
/* 349 */         Scrollable view = (Scrollable)(Scrollable)vp.getView();
/* 350 */         Rectangle vr = vp.getViewRect();
/* 351 */         p.y += view.getScrollableUnitIncrement(vr, 1, 1);
/*     */       }
/*     */       else {
/* 354 */         p.y += getVerticalUnitIncrement();
/*     */       }
/* 356 */       if (p.y + viewport.getViewRect().height <= viewport.getViewSize().height) break;
/* 357 */       p.y = (viewport.getViewSize().height - viewport.getViewRect().height); break;
/*     */     case 7:
/* 361 */       if ((!this._horizontalUnitIncrementSet) && (vp != null) && ((vp.getView() instanceof Scrollable)))
/*     */       {
/* 363 */         Scrollable view = (Scrollable)(Scrollable)vp.getView();
/* 364 */         Rectangle vr = vp.getViewRect();
/* 365 */         p.x -= view.getScrollableUnitIncrement(vr, 0, -1);
/*     */       }
/*     */       else {
/* 368 */         p.x -= getHorizontalUnitIncrement();
/*     */       }
/* 370 */       if (p.x >= 0) break;
/* 371 */       p.x = 0; break;
/*     */     case 3:
/* 375 */       if ((!this._horizontalUnitIncrementSet) && (vp != null) && ((vp.getView() instanceof Scrollable)))
/*     */       {
/* 377 */         Scrollable view = (Scrollable)(Scrollable)vp.getView();
/* 378 */         Rectangle vr = vp.getViewRect();
/* 379 */         p.x += view.getScrollableUnitIncrement(vr, 0, 1);
/*     */       }
/*     */       else {
/* 382 */         p.x += getHorizontalUnitIncrement();
/*     */       }
/* 384 */       if (p.x + viewport.getViewRect().width <= viewport.getViewSize().width) break;
/* 385 */       p.x = (viewport.getViewSize().width - viewport.getViewRect().width);
/*     */     case 2:
/*     */     case 4:
/*     */     case 6:
/*     */     }
/* 390 */     viewport.setViewPosition(p);
/*     */   }
/*     */ 
/*     */   public Rectangle getViewportBorderBounds()
/*     */   {
/* 395 */     Rectangle borderR = new Rectangle(getSize());
/*     */ 
/* 397 */     Insets insets = getInsets();
/* 398 */     borderR.x = insets.left;
/* 399 */     borderR.y = insets.top;
/* 400 */     borderR.width -= insets.left + insets.right;
/* 401 */     borderR.height -= insets.top + insets.bottom;
/*     */ 
/* 403 */     if ((this._scrollUpButton != null) && (this._scrollUpButton.isVisible())) {
/* 404 */       borderR.y += this._scrollUpButton.getHeight();
/* 405 */       borderR.height -= this._scrollUpButton.getHeight();
/*     */     }
/*     */ 
/* 408 */     if ((this._scrollLeftButton != null) && (this._scrollLeftButton.isVisible())) {
/* 409 */       borderR.x += this._scrollLeftButton.getWidth();
/* 410 */       borderR.width -= this._scrollLeftButton.getWidth();
/*     */     }
/*     */ 
/* 413 */     if ((this._scrollDownButton != null) && (this._scrollDownButton.isVisible())) {
/* 414 */       borderR.height -= this._scrollDownButton.getHeight();
/*     */     }
/*     */ 
/* 417 */     if ((this._scrollRightButton != null) && (this._scrollRightButton.isVisible())) {
/* 418 */       borderR.width -= this._scrollRightButton.getWidth();
/*     */     }
/*     */ 
/* 421 */     return borderR;
/*     */   }
/*     */ 
/*     */   public int getHorizontalUnitIncrement() {
/* 425 */     return this._horizontalUnitIncrement;
/*     */   }
/*     */ 
/*     */   public void setHorizontalUnitIncrement(int horizontalUnitIncrement) {
/* 429 */     this._horizontalUnitIncrementSet = true;
/* 430 */     if (horizontalUnitIncrement != this._horizontalUnitIncrement) {
/* 431 */       int old = this._horizontalUnitIncrement;
/* 432 */       this._horizontalUnitIncrement = horizontalUnitIncrement;
/* 433 */       firePropertyChange("horizontalUnitIncrement", old, this._horizontalUnitIncrement);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getVerticalUnitIncrement() {
/* 438 */     return this._verticalUnitIncrement;
/*     */   }
/*     */ 
/*     */   public void setVerticalUnitIncrement(int verticalUnitIncrement) {
/* 442 */     this._verticalUnitIncrementSet = true;
/* 443 */     if (verticalUnitIncrement != this._verticalUnitIncrement) {
/* 444 */       int old = this._verticalUnitIncrement;
/* 445 */       this._verticalUnitIncrement = verticalUnitIncrement;
/* 446 */       firePropertyChange("verticalUnitIncrement", old, this._verticalUnitIncrement);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isScrollOnRollover()
/*     */   {
/* 456 */     return this._scrollOnRollover;
/*     */   }
/*     */ 
/*     */   public void setScrollOnRollover(boolean scrollOnRollover)
/*     */   {
/* 466 */     if (this._scrollOnRollover != scrollOnRollover) {
/* 467 */       boolean old = this._scrollOnRollover;
/* 468 */       this._scrollOnRollover = scrollOnRollover;
/* 469 */       firePropertyChange("scrollOnRollover", old, this._scrollOnRollover);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getRepeatDelay()
/*     */   {
/* 479 */     return this._repeatDelay;
/*     */   }
/*     */ 
/*     */   public void setRepeatDelay(int repeatDelay)
/*     */   {
/* 489 */     if (repeatDelay != this._repeatDelay) {
/* 490 */       int old = this._repeatDelay;
/* 491 */       this._repeatDelay = repeatDelay;
/* 492 */       firePropertyChange("repeatDelay", old, this._repeatDelay);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void mouseWheelMoved(MouseWheelEvent e)
/*     */   {
/* 498 */     if ((isWheelScrollingEnabled()) && (e.getScrollAmount() != 0)) {
/* 499 */       boolean scrollingUp = e.getWheelRotation() >= 0;
/* 500 */       int direction = 0;
/*     */ 
/* 502 */       if (!isButtonVisible(scrollingUp)) {
/* 503 */         return;
/*     */       }
/* 505 */       direction = getScrollDirection(scrollingUp);
/* 506 */       if (direction != 0)
/* 507 */         scroll(getViewport(), direction);
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isButtonVisible(boolean scrollingUp) {
/* 512 */     if (scrollingUp) {
/* 513 */       return ((this._scrollUpButton != null) && (this._scrollUpButton.isVisible())) || ((this._scrollLeftButton != null) && (this._scrollLeftButton.isVisible()));
/*     */     }
/*     */ 
/* 516 */     return ((this._scrollDownButton != null) && (this._scrollDownButton.isVisible())) || ((this._scrollRightButton != null) && (this._scrollRightButton.isVisible()));
/*     */   }
/*     */ 
/*     */   private int getScrollDirection(boolean scrollingUp)
/*     */   {
/* 521 */     if (scrollingUp) {
/* 522 */       if ((this._scrollUpButton != null) && (this._scrollUpButton.isVisible())) return 5;
/* 523 */       if ((this._scrollLeftButton != null) && (this._scrollLeftButton.isVisible())) return 3; 
/*     */     }
/*     */     else
/*     */     {
/* 526 */       if ((this._scrollDownButton != null) && (this._scrollDownButton.isVisible())) return 1;
/* 527 */       if ((this._scrollRightButton != null) && (this._scrollRightButton.isVisible())) return 7;
/*     */     }
/*     */ 
/* 530 */     return 0;
/*     */   }
/*     */ 
/*     */   public void setWheelScrollingEnabled(boolean handleWheel)
/*     */   {
/* 535 */     if ((handleWheel) && (!isWheelScrollingEnabled()))
/* 536 */       addMouseWheelListener(this);
/* 537 */     if ((!handleWheel) && (isWheelScrollingEnabled()))
/* 538 */       removeMouseWheelListener(this);
/* 539 */     super.setWheelScrollingEnabled(handleWheel);
/*     */   }
/*     */ 
/*     */   public class ScrollButton extends JideButton
/*     */     implements MouseListener, ActionListener, UIResource
/*     */   {
/*     */     private int _type;
/*     */     private Timer _timer;
/*     */ 
/*     */     public ScrollButton(int type)
/*     */     {
/* 206 */       this._type = type;
/* 207 */       switch (type) {
/*     */       case 1:
/* 209 */         setIcon(JideIconsFactory.getImageIcon("jide/direction_up.gif"));
/* 210 */         break;
/*     */       case 5:
/* 212 */         setIcon(JideIconsFactory.getImageIcon("jide/direction_down.gif"));
/* 213 */         break;
/*     */       case 7:
/* 215 */         setIcon(JideIconsFactory.getImageIcon("jide/direction_left.gif"));
/* 216 */         break;
/*     */       case 3:
/* 218 */         setIcon(JideIconsFactory.getImageIcon("jide/direction_right.gif"));
/*     */       case 2:
/*     */       case 4:
/* 221 */       case 6: } addActionListener(this);
/* 222 */       addMouseListener(this);
/* 223 */       setPreferredSize(new Dimension(10, 10));
/* 224 */       setMinimumSize(new Dimension(10, 10));
/*     */     }
/*     */ 
/*     */     public void actionPerformed(ActionEvent e) {
/* 228 */       SimpleScrollPane.this.scroll(SimpleScrollPane.this.getViewport(), this._type);
/* 229 */       SimpleScrollPane.this.updateButtonState();
/*     */     }
/*     */ 
/*     */     public void mouseClicked(MouseEvent e) {
/*     */     }
/*     */ 
/*     */     public void mousePressed(MouseEvent e) {
/* 236 */       if (!SimpleScrollPane.this.isScrollOnRollover()) {
/* 237 */         startTimer(e, 500);
/*     */       }
/*     */       else
/* 240 */         updateTimer(e);
/*     */     }
/*     */ 
/*     */     public void mouseReleased(MouseEvent e)
/*     */     {
/* 245 */       if (!SimpleScrollPane.this.isScrollOnRollover()) {
/* 246 */         stopTimer();
/*     */       }
/*     */       else
/* 249 */         updateTimer(e);
/*     */     }
/*     */ 
/*     */     public void mouseEntered(MouseEvent e)
/*     */     {
/* 254 */       if (SimpleScrollPane.this.isScrollOnRollover())
/* 255 */         startTimer(e, 500);
/*     */     }
/*     */ 
/*     */     private void updateTimer(MouseEvent e)
/*     */     {
/* 260 */       if (this._timer != null)
/* 261 */         this._timer.setDelay(getDelay(e));
/*     */     }
/*     */ 
/*     */     private void startTimer(MouseEvent e, int initDelay)
/*     */     {
/* 266 */       stopTimer();
/* 267 */       this._timer = new Timer(getDelay(e), this);
/* 268 */       this._timer.setInitialDelay(initDelay);
/* 269 */       this._timer.start();
/*     */     }
/*     */ 
/*     */     private void stopTimer() {
/* 273 */       if (this._timer != null) {
/* 274 */         this._timer.stop();
/* 275 */         this._timer = null;
/*     */       }
/*     */     }
/*     */ 
/*     */     private int getDelay(MouseEvent e) {
/* 280 */       if (SimpleScrollPane.this.isScrollOnRollover()) {
/* 281 */         return SwingUtilities.isLeftMouseButton(e) ? SimpleScrollPane.this.getRepeatDelay() : SimpleScrollPane.this.getRepeatDelay() * 2;
/*     */       }
/*     */ 
/* 284 */       return SimpleScrollPane.this.getRepeatDelay();
/*     */     }
/*     */ 
/*     */     public void mouseExited(MouseEvent e)
/*     */     {
/* 289 */       if (SimpleScrollPane.this.isScrollOnRollover())
/* 290 */         stopTimer();
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.SimpleScrollPane
 * JD-Core Version:    0.6.0
 */
/*     */ package com.jidesoft.plaf.windows;
/*     */ 
/*     */ import com.jidesoft.swing.RangeSlider;
/*     */ import com.sun.java.swing.plaf.windows.WindowsSliderUI;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.lang.reflect.Field;
/*     */ import javax.swing.BoundedRangeModel;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JSlider;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicSliderUI.TrackListener;
/*     */ 
/*     */ public class WindowsRangeSliderUI extends WindowsSliderUI
/*     */ {
/*     */   protected static final int MOUSE_HANDLE_NONE = 0;
/*     */   protected static final int MOUSE_HANDLE_MIN = 1;
/*     */   protected static final int MOUSE_HANDLE_MAX = 2;
/*     */   protected static final int MOUSE_HANDLE_MIDDLE = 4;
/*     */   protected static final int MOUSE_HANDLE_LOWER = 5;
/*     */   protected static final int MOUSE_HANDLE_UPPER = 6;
/*     */   protected boolean hover;
/*     */   protected boolean second;
/*     */   protected boolean rollover1;
/*     */   protected boolean pressed1;
/*     */   protected boolean rollover2;
/*     */   protected boolean pressed2;
/*     */ 
/*     */   public WindowsRangeSliderUI(JSlider slider)
/*     */   {
/*  24 */     super(slider);
/*     */   }
/*     */ 
/*     */   public static ComponentUI createUI(JComponent slider)
/*     */   {
/*  32 */     return new WindowsRangeSliderUI((JSlider)slider);
/*     */   }
/*     */ 
/*     */   public void paint(Graphics g, JComponent c)
/*     */   {
/*  37 */     this.second = false;
/*  38 */     super.paint(g, c);
/*     */ 
/*  40 */     Rectangle clip = g.getClipBounds();
/*     */ 
/*  42 */     this.second = true;
/*  43 */     Point p = adjustThumbForHighValue();
/*     */ 
/*  45 */     if (clip.intersects(this.thumbRect)) {
/*  46 */       paintThumb(g);
/*     */     }
/*     */ 
/*  49 */     restoreThumbForLowValue(p);
/*  50 */     this.second = false;
/*     */   }
/*     */ 
/*     */   protected void restoreThumbForLowValue(Point p) {
/*  54 */     this.thumbRect.x = p.x;
/*  55 */     this.thumbRect.y = p.y;
/*     */   }
/*     */ 
/*     */   protected Point adjustThumbForHighValue() {
/*  59 */     Point p = this.thumbRect.getLocation();
/*  60 */     if (this.slider.getOrientation() == 0) {
/*  61 */       int valuePosition = xPositionForValue(((RangeSlider)this.slider).getHighValue());
/*  62 */       this.thumbRect.x = (valuePosition - this.thumbRect.width / 2);
/*     */     }
/*     */     else {
/*  65 */       int valuePosition = yPositionForValue(((RangeSlider)this.slider).getHighValue());
/*  66 */       this.thumbRect.y = (valuePosition - this.thumbRect.height / 2);
/*     */     }
/*  68 */     return p;
/*     */   }
/*     */ 
/*     */   protected void adjustSnapHighValue() {
/*  72 */     int sliderValue = ((RangeSlider)this.slider).getHighValue();
/*  73 */     int snappedValue = sliderValue;
/*  74 */     int majorTickSpacing = this.slider.getMajorTickSpacing();
/*  75 */     int minorTickSpacing = this.slider.getMinorTickSpacing();
/*  76 */     int tickSpacing = 0;
/*     */ 
/*  78 */     if (minorTickSpacing > 0) {
/*  79 */       tickSpacing = minorTickSpacing;
/*     */     }
/*  81 */     else if (majorTickSpacing > 0) {
/*  82 */       tickSpacing = majorTickSpacing;
/*     */     }
/*     */ 
/*  85 */     if (tickSpacing != 0)
/*     */     {
/*  87 */       if ((sliderValue - this.slider.getMinimum()) % tickSpacing != 0) {
/*  88 */         float temp = (sliderValue - this.slider.getMinimum()) / tickSpacing;
/*     */ 
/*  90 */         int whichTick = Math.round(temp);
/*  91 */         snappedValue = this.slider.getMinimum() + whichTick * tickSpacing;
/*     */       }
/*     */ 
/*  95 */       if (snappedValue != sliderValue)
/*  96 */         ((RangeSlider)this.slider).setHighValue(snappedValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void calculateThumbLocation()
/*     */   {
/* 103 */     if (this.slider.getSnapToTicks()) {
/* 104 */       adjustSnapHighValue();
/*     */     }
/* 106 */     super.calculateThumbLocation();
/*     */   }
/*     */ 
/*     */   protected BasicSliderUI.TrackListener createTrackListener(JSlider slider)
/*     */   {
/* 111 */     return new RangeTrackListener(super.createTrackListener(slider));
/*     */   }
/*     */ 
/*     */   private void offset(int delta)
/*     */   {
/* 301 */     this.slider.getModel().setValue(((RangeSlider)this.slider).getLowValue() + delta);
/*     */   }
/*     */ 
/*     */   protected int getMouseHandle(int x, int y)
/*     */   {
/* 317 */     Rectangle rect = this.trackRect;
/*     */ 
/* 319 */     this.slider.putClientProperty("RangeSlider.mousePosition", null);
/*     */ 
/* 321 */     if (this.thumbRect.contains(x, y)) {
/* 322 */       return 1;
/*     */     }
/* 324 */     Point p = adjustThumbForHighValue();
/* 325 */     if (this.thumbRect.contains(x, y)) {
/* 326 */       restoreThumbForLowValue(p);
/* 327 */       return 2;
/*     */     }
/* 329 */     restoreThumbForLowValue(p);
/*     */ 
/* 331 */     if (this.slider.getOrientation() == 1) {
/* 332 */       int minY = yPositionForValue(((RangeSlider)this.slider).getLowValue());
/* 333 */       int maxY = yPositionForValue(((RangeSlider)this.slider).getHighValue());
/* 334 */       Rectangle midRect = new Rectangle(rect.x, Math.min(minY, maxY) + this.thumbRect.height / 2, rect.width, Math.abs(maxY - minY) - this.thumbRect.height);
/* 335 */       if (midRect.contains(x, y)) {
/* 336 */         return 4;
/*     */       }
/* 338 */       int sy = rect.y + Math.max(minY, maxY) + this.thumbRect.height / 2;
/* 339 */       Rectangle lowerRect = new Rectangle(rect.x, sy, rect.width, rect.y + rect.height - sy);
/* 340 */       if (lowerRect.contains(x, y)) {
/* 341 */         this.slider.putClientProperty("RangeSlider.mousePosition", Boolean.valueOf(true));
/* 342 */         return 5;
/*     */       }
/* 344 */       Rectangle upperRect = new Rectangle(rect.x, rect.y, rect.width, Math.min(maxY, minY) - this.thumbRect.height / 2);
/* 345 */       if (upperRect.contains(x, y)) {
/* 346 */         this.slider.putClientProperty("RangeSlider.mousePosition", Boolean.valueOf(false));
/* 347 */         return 6;
/*     */       }
/*     */ 
/* 350 */       return 0;
/*     */     }
/*     */ 
/* 353 */     int minX = xPositionForValue(((RangeSlider)this.slider).getLowValue());
/* 354 */     int maxX = xPositionForValue(((RangeSlider)this.slider).getHighValue());
/*     */ 
/* 356 */     Rectangle midRect = new Rectangle(Math.min(minX, maxX) + this.thumbRect.width / 2, rect.y, Math.abs(maxX - minX) - this.thumbRect.width, rect.height);
/* 357 */     if (midRect.contains(x, y)) {
/* 358 */       return 4;
/*     */     }
/* 360 */     Rectangle lowerRect = new Rectangle(rect.x, rect.y, Math.min(minX, maxX) - this.thumbRect.width / 2 - rect.x, rect.height);
/* 361 */     if (lowerRect.contains(x, y)) {
/* 362 */       this.slider.putClientProperty("RangeSlider.mousePosition", Boolean.valueOf(true));
/* 363 */       return 5;
/*     */     }
/* 365 */     int sx = rect.x + Math.abs(maxX - minX) + this.thumbRect.width / 2;
/* 366 */     Rectangle upperRect = new Rectangle(sx, rect.y, rect.width - sx, rect.height);
/* 367 */     if (upperRect.contains(x, y)) {
/* 368 */       this.slider.putClientProperty("RangeSlider.mousePosition", Boolean.valueOf(false));
/* 369 */       return 6;
/*     */     }
/* 371 */     return 0;
/*     */   }
/*     */ 
/*     */   public void paintThumb(Graphics g)
/*     */   {
/*     */     try
/*     */     {
/* 385 */       Field field = getClass().getSuperclass().getDeclaredField("rollover");
/* 386 */       field.setAccessible(true);
/* 387 */       field.set(this, Boolean.valueOf(this.second ? this.rollover2 : this.rollover1));
/*     */ 
/* 389 */       field = getClass().getSuperclass().getDeclaredField("pressed");
/* 390 */       field.setAccessible(true);
/* 391 */       field.set(this, Boolean.valueOf(this.second ? this.pressed2 : this.pressed1));
/*     */     }
/*     */     catch (NoSuchFieldException e)
/*     */     {
/*     */     }
/*     */     catch (IllegalAccessException e)
/*     */     {
/*     */     }
/*     */ 
/* 400 */     super.paintThumb(g);
/*     */   }
/*     */ 
/*     */   protected void setMouseRollover(int handle) {
/* 404 */     switch (handle) {
/*     */     case 1:
/* 406 */       this.rollover1 = true;
/* 407 */       this.rollover2 = false;
/*     */ 
/* 409 */       break;
/*     */     case 2:
/* 411 */       this.rollover2 = true;
/* 412 */       this.rollover1 = false;
/*     */ 
/* 414 */       break;
/*     */     case 4:
/* 416 */       this.rollover1 = true;
/* 417 */       this.rollover2 = true;
/*     */ 
/* 419 */       break;
/*     */     case 0:
/* 421 */       this.rollover1 = false;
/* 422 */       this.rollover2 = false;
/*     */     case 3:
/*     */     }
/* 425 */     this.slider.repaint(this.thumbRect);
/* 426 */     Point p = adjustThumbForHighValue();
/* 427 */     this.slider.repaint(this.thumbRect);
/* 428 */     restoreThumbForLowValue(p);
/*     */   }
/*     */ 
/*     */   protected void setMousePressed(int handle) {
/* 432 */     switch (handle) {
/*     */     case 1:
/* 434 */       this.pressed1 = true;
/* 435 */       this.pressed2 = false;
/*     */ 
/* 437 */       break;
/*     */     case 2:
/* 439 */       this.pressed2 = true;
/* 440 */       this.pressed1 = false;
/*     */ 
/* 442 */       break;
/*     */     case 4:
/* 444 */       this.pressed1 = true;
/* 445 */       this.pressed2 = true;
/*     */ 
/* 447 */       break;
/*     */     case 0:
/* 449 */       this.pressed1 = false;
/* 450 */       this.pressed2 = false;
/*     */     case 3:
/*     */     }
/* 453 */     this.slider.repaint(this.thumbRect);
/* 454 */     Point p = adjustThumbForHighValue();
/* 455 */     this.slider.repaint(this.thumbRect);
/* 456 */     restoreThumbForLowValue(p);
/*     */   }
/*     */ 
/*     */   protected void setMouseReleased(int handle)
/*     */   {
/* 461 */     this.pressed1 = false;
/* 462 */     this.pressed2 = false;
/* 463 */     this.slider.repaint(this.thumbRect);
/* 464 */     Point p = adjustThumbForHighValue();
/* 465 */     this.slider.repaint(this.thumbRect);
/* 466 */     restoreThumbForLowValue(p);
/*     */   }
/*     */ 
/*     */   public void scrollByBlock(int direction)
/*     */   {
/* 471 */     synchronized (this.slider)
/*     */     {
/* 474 */       Object clientProperty = this.slider.getClientProperty("RangeSlider.mousePosition");
/*     */       int oldValue;
/*     */       int oldValue;
/* 475 */       if (clientProperty == null) {
/* 476 */         oldValue = this.slider.getValue();
/*     */       }
/*     */       else
/*     */       {
/*     */         int oldValue;
/* 478 */         if (Boolean.TRUE.equals(clientProperty)) {
/* 479 */           oldValue = ((RangeSlider)this.slider).getLowValue();
/*     */         }
/*     */         else
/* 482 */           oldValue = ((RangeSlider)this.slider).getHighValue();
/*     */       }
/* 484 */       int blockIncrement = (this.slider.getMaximum() - this.slider.getMinimum()) / 10;
/*     */ 
/* 486 */       if ((blockIncrement <= 0) && (this.slider.getMaximum() > this.slider.getMinimum()))
/*     */       {
/* 489 */         blockIncrement = 1;
/*     */       }
/*     */ 
/* 492 */       int delta = blockIncrement * (direction > 0 ? 1 : -1);
/* 493 */       if (clientProperty == null) {
/* 494 */         this.slider.setValue(oldValue + delta);
/*     */       }
/* 496 */       else if (Boolean.TRUE.equals(clientProperty)) {
/* 497 */         ((RangeSlider)this.slider).setLowValue(oldValue + delta);
/*     */       }
/*     */       else
/* 500 */         ((RangeSlider)this.slider).setHighValue(oldValue + delta);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void scrollByUnit(int direction)
/*     */   {
/* 507 */     synchronized (this.slider)
/*     */     {
/* 510 */       Object clientProperty = this.slider.getClientProperty("RangeSlider.mousePosition");
/*     */       int oldValue;
/*     */       int oldValue;
/* 511 */       if (clientProperty == null) {
/* 512 */         oldValue = this.slider.getValue();
/*     */       }
/*     */       else
/*     */       {
/*     */         int oldValue;
/* 514 */         if (Boolean.TRUE.equals(clientProperty)) {
/* 515 */           oldValue = ((RangeSlider)this.slider).getLowValue();
/*     */         }
/*     */         else
/* 518 */           oldValue = ((RangeSlider)this.slider).getHighValue();
/*     */       }
/* 520 */       int delta = 1 * (direction > 0 ? 1 : -1);
/*     */ 
/* 522 */       if (clientProperty == null) {
/* 523 */         this.slider.setValue(oldValue + delta);
/*     */       }
/* 525 */       else if (Boolean.TRUE.equals(clientProperty)) {
/* 526 */         ((RangeSlider)this.slider).setLowValue(oldValue + delta);
/*     */       }
/*     */       else
/* 529 */         ((RangeSlider)this.slider).setHighValue(oldValue + delta);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected class RangeTrackListener extends BasicSliderUI.TrackListener
/*     */   {
/*     */     int handle;
/*     */     int handleOffset;
/*     */     int mouseStartLocation;
/*     */     BasicSliderUI.TrackListener _listener;
/*     */ 
/*     */     public RangeTrackListener(BasicSliderUI.TrackListener listener)
/*     */     {
/* 120 */       super();
/* 121 */       this._listener = listener;
/*     */     }
/*     */ 
/*     */     public void mousePressed(MouseEvent e)
/*     */     {
/* 129 */       if (!WindowsRangeSliderUI.this.slider.isEnabled()) {
/* 130 */         return;
/*     */       }
/*     */ 
/* 133 */       if (WindowsRangeSliderUI.this.slider.isRequestFocusEnabled()) {
/* 134 */         WindowsRangeSliderUI.this.slider.requestFocus();
/*     */       }
/*     */ 
/* 137 */       this.handle = WindowsRangeSliderUI.this.getMouseHandle(e.getX(), e.getY());
/* 138 */       WindowsRangeSliderUI.this.setMousePressed(this.handle);
/*     */ 
/* 140 */       if ((this.handle == 2) || (this.handle == 1) || (this.handle == 4)) {
/* 141 */         this.handleOffset = (WindowsRangeSliderUI.this.slider.getOrientation() == 1 ? e.getY() - WindowsRangeSliderUI.this.yPositionForValue(((RangeSlider)WindowsRangeSliderUI.this.slider).getLowValue()) : e.getX() - WindowsRangeSliderUI.this.xPositionForValue(((RangeSlider)WindowsRangeSliderUI.this.slider).getLowValue()));
/*     */ 
/* 145 */         this.mouseStartLocation = (WindowsRangeSliderUI.this.slider.getOrientation() == 1 ? e.getY() : e.getX());
/*     */ 
/* 147 */         WindowsRangeSliderUI.this.slider.getModel().setValueIsAdjusting(true);
/*     */       }
/* 149 */       else if ((this.handle == 5) || (this.handle == 6)) {
/* 150 */         this._listener.mousePressed(e);
/* 151 */         WindowsRangeSliderUI.this.slider.putClientProperty("RangeSlider.mousePosition", null);
/*     */       }
/*     */     }
/*     */ 
/*     */     public void mouseDragged(MouseEvent e)
/*     */     {
/* 160 */       if (!WindowsRangeSliderUI.this.slider.isEnabled()) {
/* 161 */         return;
/*     */       }
/*     */ 
/* 164 */       int newLocation = WindowsRangeSliderUI.this.slider.getOrientation() == 1 ? e.getY() : e.getX();
/*     */ 
/* 166 */       int newValue = WindowsRangeSliderUI.this.slider.getOrientation() == 1 ? WindowsRangeSliderUI.this.valueForYPosition(newLocation) : WindowsRangeSliderUI.this.valueForXPosition(newLocation);
/*     */ 
/* 168 */       if (newValue < WindowsRangeSliderUI.this.slider.getModel().getMinimum()) {
/* 169 */         newValue = WindowsRangeSliderUI.this.slider.getModel().getMinimum();
/*     */       }
/*     */ 
/* 172 */       if (newValue > WindowsRangeSliderUI.this.slider.getModel().getMaximum()) {
/* 173 */         newValue = WindowsRangeSliderUI.this.slider.getModel().getMaximum();
/*     */       }
/*     */ 
/* 176 */       if (this.handle == 3) {
/* 177 */         if (newLocation - this.mouseStartLocation > 2) {
/* 178 */           this.handle = 2;
/*     */         }
/* 180 */         else if (newLocation - this.mouseStartLocation < -2) {
/* 181 */           this.handle = 1;
/*     */         }
/*     */         else {
/* 184 */           return;
/*     */         }
/*     */       }
/*     */ 
/* 188 */       RangeSlider rangeSlider = (RangeSlider)WindowsRangeSliderUI.this.slider;
/* 189 */       switch (this.handle) {
/*     */       case 1:
/* 191 */         rangeSlider.setLowValue(Math.min(newValue, rangeSlider.getHighValue()));
/* 192 */         break;
/*     */       case 2:
/* 194 */         rangeSlider.setHighValue(Math.max(rangeSlider.getLowValue(), newValue));
/* 195 */         break;
/*     */       case 4:
/* 197 */         if (!((RangeSlider)WindowsRangeSliderUI.this.slider).isRangeDraggable()) break;
/* 198 */         int delta = WindowsRangeSliderUI.this.slider.getOrientation() == 1 ? WindowsRangeSliderUI.this.valueForYPosition(newLocation - this.handleOffset) - rangeSlider.getLowValue() : WindowsRangeSliderUI.this.valueForXPosition(newLocation - this.handleOffset) - rangeSlider.getLowValue();
/*     */ 
/* 201 */         if ((delta < 0) && (rangeSlider.getLowValue() + delta < rangeSlider.getMinimum())) {
/* 202 */           delta = rangeSlider.getMinimum() - rangeSlider.getLowValue();
/*     */         }
/*     */ 
/* 205 */         if ((delta > 0) && (rangeSlider.getHighValue() + delta > rangeSlider.getMaximum())) {
/* 206 */           delta = rangeSlider.getMaximum() - rangeSlider.getHighValue();
/*     */         }
/*     */ 
/* 209 */         if (delta == 0) break;
/* 210 */         WindowsRangeSliderUI.this.offset(delta);
/*     */       case 3:
/*     */       }
/*     */     }
/*     */ 
/*     */     public void mouseReleased(MouseEvent e)
/*     */     {
/* 222 */       WindowsRangeSliderUI.this.slider.getModel().setValueIsAdjusting(false);
/* 223 */       WindowsRangeSliderUI.this.setMouseReleased(this.handle);
/* 224 */       this._listener.mouseReleased(e);
/*     */     }
/*     */ 
/*     */     private void setCursor(int c) {
/* 228 */       Cursor cursor = Cursor.getPredefinedCursor(c);
/*     */ 
/* 230 */       if (WindowsRangeSliderUI.this.slider.getCursor() != cursor)
/* 231 */         WindowsRangeSliderUI.this.slider.setCursor(cursor);
/*     */     }
/*     */ 
/*     */     public void mouseMoved(MouseEvent e)
/*     */     {
/* 240 */       if (!WindowsRangeSliderUI.this.slider.isEnabled()) {
/* 241 */         return;
/*     */       }
/*     */ 
/* 244 */       int handle = WindowsRangeSliderUI.this.getMouseHandle(e.getX(), e.getY());
/* 245 */       WindowsRangeSliderUI.this.setMouseRollover(handle);
/* 246 */       switch (handle) {
/*     */       case 1:
/* 248 */         setCursor(0);
/* 249 */         break;
/*     */       case 2:
/* 251 */         setCursor(0);
/* 252 */         break;
/*     */       case 4:
/* 254 */         if (((WindowsRangeSliderUI.this.slider instanceof RangeSlider)) && (((RangeSlider)WindowsRangeSliderUI.this.slider).isRangeDraggable())) {
/* 255 */           setCursor(13);
/*     */         }
/*     */         else {
/* 258 */           setCursor(0);
/*     */         }
/* 260 */         break;
/*     */       case 0:
/*     */       case 3:
/*     */       default:
/* 263 */         setCursor(0);
/*     */       }
/*     */     }
/*     */ 
/*     */     public void mouseClicked(MouseEvent e)
/*     */     {
/* 273 */       if (e.getClickCount() == 2) {
/* 274 */         WindowsRangeSliderUI.this.slider.getModel().setValue(WindowsRangeSliderUI.this.slider.getModel().getMinimum());
/* 275 */         WindowsRangeSliderUI.this.slider.getModel().setExtent(WindowsRangeSliderUI.this.slider.getModel().getMaximum() - WindowsRangeSliderUI.this.slider.getModel().getMinimum());
/* 276 */         WindowsRangeSliderUI.this.slider.repaint();
/*     */       }
/*     */     }
/*     */ 
/*     */     public void mouseEntered(MouseEvent e)
/*     */     {
/* 285 */       WindowsRangeSliderUI.this.hover = true;
/* 286 */       WindowsRangeSliderUI.this.slider.repaint();
/*     */     }
/*     */ 
/*     */     public void mouseExited(MouseEvent e)
/*     */     {
/* 294 */       WindowsRangeSliderUI.this.hover = false;
/* 295 */       WindowsRangeSliderUI.this.slider.repaint();
/* 296 */       setCursor(0);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.windows.WindowsRangeSliderUI
 * JD-Core Version:    0.6.0
 */
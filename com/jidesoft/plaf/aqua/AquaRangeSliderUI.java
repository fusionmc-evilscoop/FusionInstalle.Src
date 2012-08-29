/*     */ package com.jidesoft.plaf.aqua;
/*     */ 
/*     */ import apple.laf.AquaSliderUI;
/*     */ import apple.laf.CoreUIConstants.Orientation;
/*     */ import apple.laf.CoreUIConstants.State;
/*     */ import com.jidesoft.swing.RangeSlider;
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
/*     */ public class AquaRangeSliderUI extends AquaSliderUI
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
/*     */   public AquaRangeSliderUI(JSlider jSlider)
/*     */   {
/*  21 */     super(jSlider);
/*     */   }
/*     */ 
/*     */   public static ComponentUI createUI(JComponent c) {
/*  25 */     return new AquaRangeSliderUI((JSlider)c);
/*     */   }
/*     */ 
/*     */   public void paint(Graphics g, JComponent c)
/*     */   {
/*  30 */     this.second = false;
/*  31 */     super.paint(g, c);
/*     */ 
/*  33 */     Rectangle clip = g.getClipBounds();
/*     */ 
/*  35 */     this.second = true;
/*  36 */     Point p = adjustThumbForHighValue();
/*     */ 
/*  38 */     if (clip.intersects(this.thumbRect)) {
/*  39 */       CoreUIConstants.Orientation orientation = this.slider.getOrientation() != 0 ? CoreUIConstants.Orientation.VERTICAL : CoreUIConstants.Orientation.HORIZONTAL;
/*  40 */       CoreUIConstants.State state = getState();
/*  41 */       paintThumb(g, c, orientation, state);
/*     */     }
/*     */ 
/*  44 */     restoreThumbForLowValue(p);
/*  45 */     this.second = false;
/*     */   }
/*     */ 
/*     */   protected static boolean isActive(JComponent jcomponent) {
/*  49 */     if (jcomponent == null)
/*  50 */       return true;
/*  51 */     Object obj = jcomponent.getClientProperty("Frame.active");
/*  52 */     return !Boolean.FALSE.equals(obj);
/*     */   }
/*     */ 
/*     */   CoreUIConstants.State getState()
/*     */   {
/*  57 */     if (!this.slider.isEnabled())
/*  58 */       return CoreUIConstants.State.DISABLED;
/*  59 */     if (this.fIsDragging)
/*  60 */       return CoreUIConstants.State.PRESSED;
/*  61 */     if (!isActive(this.slider)) {
/*  62 */       return CoreUIConstants.State.INACTIVE;
/*     */     }
/*  64 */     return CoreUIConstants.State.ACTIVE;
/*     */   }
/*     */ 
/*     */   protected void restoreThumbForLowValue(Point p) {
/*  68 */     this.thumbRect.x = p.x;
/*  69 */     this.thumbRect.y = p.y;
/*     */   }
/*     */ 
/*     */   protected Point adjustThumbForHighValue() {
/*  73 */     Point p = this.thumbRect.getLocation();
/*  74 */     if (this.slider.getOrientation() == 0) {
/*  75 */       int valuePosition = xPositionForValue(((RangeSlider)this.slider).getHighValue());
/*  76 */       this.thumbRect.x = (valuePosition - this.thumbRect.width / 2);
/*     */     }
/*     */     else {
/*  79 */       int valuePosition = yPositionForValue(((RangeSlider)this.slider).getHighValue());
/*  80 */       this.thumbRect.y = (valuePosition - this.thumbRect.height / 2);
/*     */     }
/*  82 */     return p;
/*     */   }
/*     */ 
/*     */   protected void adjustSnapHighValue() {
/*  86 */     int sliderValue = ((RangeSlider)this.slider).getHighValue();
/*  87 */     int snappedValue = sliderValue;
/*  88 */     int majorTickSpacing = this.slider.getMajorTickSpacing();
/*  89 */     int minorTickSpacing = this.slider.getMinorTickSpacing();
/*  90 */     int tickSpacing = 0;
/*     */ 
/*  92 */     if (minorTickSpacing > 0) {
/*  93 */       tickSpacing = minorTickSpacing;
/*     */     }
/*  95 */     else if (majorTickSpacing > 0) {
/*  96 */       tickSpacing = majorTickSpacing;
/*     */     }
/*     */ 
/*  99 */     if (tickSpacing != 0)
/*     */     {
/* 101 */       if ((sliderValue - this.slider.getMinimum()) % tickSpacing != 0) {
/* 102 */         float temp = (sliderValue - this.slider.getMinimum()) / tickSpacing;
/*     */ 
/* 104 */         int whichTick = Math.round(temp);
/* 105 */         snappedValue = this.slider.getMinimum() + whichTick * tickSpacing;
/*     */       }
/*     */ 
/* 109 */       if (snappedValue != sliderValue)
/* 110 */         ((RangeSlider)this.slider).setHighValue(snappedValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void calculateThumbLocation()
/*     */   {
/* 117 */     if (this.slider.getSnapToTicks()) {
/* 118 */       adjustSnapHighValue();
/*     */     }
/* 120 */     super.calculateThumbLocation();
/*     */   }
/*     */ 
/*     */   protected BasicSliderUI.TrackListener createTrackListener(JSlider slider)
/*     */   {
/* 125 */     return new RangeTrackListener(super.createTrackListener(slider));
/*     */   }
/*     */ 
/*     */   private void offset(int delta)
/*     */   {
/* 315 */     this.slider.getModel().setValue(((RangeSlider)this.slider).getLowValue() + delta);
/*     */   }
/*     */ 
/*     */   protected int getMouseHandle(int x, int y)
/*     */   {
/* 331 */     Rectangle rect = this.trackRect;
/*     */ 
/* 333 */     this.slider.putClientProperty("RangeSlider.mousePosition", null);
/*     */ 
/* 335 */     if (this.thumbRect.contains(x, y)) {
/* 336 */       return 1;
/*     */     }
/* 338 */     Point p = adjustThumbForHighValue();
/* 339 */     if (this.thumbRect.contains(x, y)) {
/* 340 */       restoreThumbForLowValue(p);
/* 341 */       return 2;
/*     */     }
/* 343 */     restoreThumbForLowValue(p);
/*     */ 
/* 345 */     if (this.slider.getOrientation() == 1) {
/* 346 */       int minY = yPositionForValue(((RangeSlider)this.slider).getLowValue());
/* 347 */       int maxY = yPositionForValue(((RangeSlider)this.slider).getHighValue());
/* 348 */       Rectangle midRect = new Rectangle(rect.x, Math.min(minY, maxY) + this.thumbRect.height / 2, rect.width, Math.abs(maxY - minY) - this.thumbRect.height);
/* 349 */       if (midRect.contains(x, y)) {
/* 350 */         return 4;
/*     */       }
/* 352 */       int sy = rect.y + Math.max(minY, maxY) + this.thumbRect.height / 2;
/* 353 */       Rectangle lowerRect = new Rectangle(rect.x, sy, rect.width, rect.y + rect.height - sy);
/* 354 */       if (lowerRect.contains(x, y)) {
/* 355 */         this.slider.putClientProperty("RangeSlider.mousePosition", Boolean.valueOf(true));
/* 356 */         return 5;
/*     */       }
/* 358 */       Rectangle upperRect = new Rectangle(rect.x, rect.y, rect.width, Math.min(maxY, minY) - this.thumbRect.height / 2);
/* 359 */       if (upperRect.contains(x, y)) {
/* 360 */         this.slider.putClientProperty("RangeSlider.mousePosition", Boolean.valueOf(false));
/* 361 */         return 6;
/*     */       }
/*     */ 
/* 364 */       return 0;
/*     */     }
/*     */ 
/* 367 */     int minX = xPositionForValue(((RangeSlider)this.slider).getLowValue());
/* 368 */     int maxX = xPositionForValue(((RangeSlider)this.slider).getHighValue());
/*     */ 
/* 370 */     Rectangle midRect = new Rectangle(Math.min(minX, maxX) + this.thumbRect.width / 2, rect.y, Math.abs(maxX - minX) - this.thumbRect.width, rect.height);
/* 371 */     if (midRect.contains(x, y)) {
/* 372 */       return 4;
/*     */     }
/* 374 */     Rectangle lowerRect = new Rectangle(rect.x, rect.y, Math.min(minX, maxX) - this.thumbRect.width / 2 - rect.x, rect.height);
/* 375 */     if (lowerRect.contains(x, y)) {
/* 376 */       this.slider.putClientProperty("RangeSlider.mousePosition", Boolean.valueOf(true));
/* 377 */       return 5;
/*     */     }
/* 379 */     int sx = rect.x + Math.abs(maxX - minX) + this.thumbRect.width / 2;
/* 380 */     Rectangle upperRect = new Rectangle(sx, rect.y, rect.width - sx, rect.height);
/* 381 */     if (upperRect.contains(x, y)) {
/* 382 */       this.slider.putClientProperty("RangeSlider.mousePosition", Boolean.valueOf(false));
/* 383 */       return 6;
/*     */     }
/* 385 */     return 0;
/*     */   }
/*     */ 
/*     */   public void paintThumb(Graphics g)
/*     */   {
/*     */     try
/*     */     {
/* 399 */       Field field = getClass().getSuperclass().getDeclaredField("rollover");
/* 400 */       field.setAccessible(true);
/* 401 */       field.set(this, Boolean.valueOf(this.second ? this.rollover2 : this.rollover1));
/*     */ 
/* 403 */       field = getClass().getSuperclass().getDeclaredField("pressed");
/* 404 */       field.setAccessible(true);
/* 405 */       field.set(this, Boolean.valueOf(this.second ? this.pressed2 : this.pressed1));
/*     */     }
/*     */     catch (NoSuchFieldException e)
/*     */     {
/*     */     }
/*     */     catch (IllegalAccessException e)
/*     */     {
/*     */     }
/*     */ 
/* 414 */     super.paintThumb(g);
/*     */   }
/*     */ 
/*     */   protected void setMouseRollover(int handle) {
/* 418 */     switch (handle) {
/*     */     case 1:
/* 420 */       this.rollover1 = true;
/* 421 */       this.rollover2 = false;
/*     */ 
/* 423 */       break;
/*     */     case 2:
/* 425 */       this.rollover2 = true;
/* 426 */       this.rollover1 = false;
/*     */ 
/* 428 */       break;
/*     */     case 4:
/* 430 */       this.rollover1 = true;
/* 431 */       this.rollover2 = true;
/*     */ 
/* 433 */       break;
/*     */     case 0:
/* 435 */       this.rollover1 = false;
/* 436 */       this.rollover2 = false;
/*     */     case 3:
/*     */     }
/* 439 */     this.slider.repaint(this.thumbRect);
/* 440 */     Point p = adjustThumbForHighValue();
/* 441 */     this.slider.repaint(this.thumbRect);
/* 442 */     restoreThumbForLowValue(p);
/*     */   }
/*     */ 
/*     */   protected void setMousePressed(int handle) {
/* 446 */     switch (handle) {
/*     */     case 1:
/* 448 */       this.pressed1 = true;
/* 449 */       this.pressed2 = false;
/*     */ 
/* 451 */       break;
/*     */     case 2:
/* 453 */       this.pressed2 = true;
/* 454 */       this.pressed1 = false;
/*     */ 
/* 456 */       break;
/*     */     case 4:
/* 458 */       this.pressed1 = true;
/* 459 */       this.pressed2 = true;
/*     */ 
/* 461 */       break;
/*     */     case 0:
/* 463 */       this.pressed1 = false;
/* 464 */       this.pressed2 = false;
/*     */     case 3:
/*     */     }
/* 467 */     this.slider.repaint(this.thumbRect);
/* 468 */     Point p = adjustThumbForHighValue();
/* 469 */     this.slider.repaint(this.thumbRect);
/* 470 */     restoreThumbForLowValue(p);
/*     */   }
/*     */ 
/*     */   protected void setMouseReleased(int handle)
/*     */   {
/* 475 */     this.pressed1 = false;
/* 476 */     this.pressed2 = false;
/* 477 */     this.slider.repaint(this.thumbRect);
/* 478 */     Point p = adjustThumbForHighValue();
/* 479 */     this.slider.repaint(this.thumbRect);
/* 480 */     restoreThumbForLowValue(p);
/*     */   }
/*     */ 
/*     */   public void scrollByBlock(int direction)
/*     */   {
/* 485 */     synchronized (this.slider)
/*     */     {
/* 488 */       Object clientProperty = this.slider.getClientProperty("RangeSlider.mousePosition");
/*     */       int oldValue;
/*     */       int oldValue;
/* 489 */       if (clientProperty == null) {
/* 490 */         oldValue = this.slider.getValue();
/*     */       }
/*     */       else
/*     */       {
/*     */         int oldValue;
/* 492 */         if (Boolean.TRUE.equals(clientProperty)) {
/* 493 */           oldValue = ((RangeSlider)this.slider).getLowValue();
/*     */         }
/*     */         else
/* 496 */           oldValue = ((RangeSlider)this.slider).getHighValue();
/*     */       }
/* 498 */       int blockIncrement = (this.slider.getMaximum() - this.slider.getMinimum()) / 10;
/*     */ 
/* 500 */       if ((blockIncrement <= 0) && (this.slider.getMaximum() > this.slider.getMinimum()))
/*     */       {
/* 503 */         blockIncrement = 1;
/*     */       }
/*     */ 
/* 506 */       int delta = blockIncrement * (direction > 0 ? 1 : -1);
/* 507 */       if (clientProperty == null) {
/* 508 */         this.slider.setValue(oldValue + delta);
/*     */       }
/* 510 */       else if (Boolean.TRUE.equals(clientProperty)) {
/* 511 */         ((RangeSlider)this.slider).setLowValue(oldValue + delta);
/*     */       }
/*     */       else
/* 514 */         ((RangeSlider)this.slider).setHighValue(oldValue + delta);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void scrollByUnit(int direction)
/*     */   {
/* 521 */     synchronized (this.slider)
/*     */     {
/* 524 */       Object clientProperty = this.slider.getClientProperty("RangeSlider.mousePosition");
/*     */       int oldValue;
/*     */       int oldValue;
/* 525 */       if (clientProperty == null) {
/* 526 */         oldValue = this.slider.getValue();
/*     */       }
/*     */       else
/*     */       {
/*     */         int oldValue;
/* 528 */         if (Boolean.TRUE.equals(clientProperty)) {
/* 529 */           oldValue = ((RangeSlider)this.slider).getLowValue();
/*     */         }
/*     */         else
/* 532 */           oldValue = ((RangeSlider)this.slider).getHighValue();
/*     */       }
/* 534 */       int delta = 1 * (direction > 0 ? 1 : -1);
/*     */ 
/* 536 */       if (clientProperty == null) {
/* 537 */         this.slider.setValue(oldValue + delta);
/*     */       }
/* 539 */       else if (Boolean.TRUE.equals(clientProperty)) {
/* 540 */         ((RangeSlider)this.slider).setLowValue(oldValue + delta);
/*     */       }
/*     */       else
/* 543 */         ((RangeSlider)this.slider).setHighValue(oldValue + delta);
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
/* 134 */       super();
/* 135 */       this._listener = listener;
/*     */     }
/*     */ 
/*     */     public void mousePressed(MouseEvent e)
/*     */     {
/* 143 */       if (!AquaRangeSliderUI.this.slider.isEnabled()) {
/* 144 */         return;
/*     */       }
/*     */ 
/* 147 */       if (AquaRangeSliderUI.this.slider.isRequestFocusEnabled()) {
/* 148 */         AquaRangeSliderUI.this.slider.requestFocus();
/*     */       }
/*     */ 
/* 151 */       this.handle = AquaRangeSliderUI.this.getMouseHandle(e.getX(), e.getY());
/* 152 */       AquaRangeSliderUI.this.setMousePressed(this.handle);
/*     */ 
/* 154 */       if ((this.handle == 2) || (this.handle == 1) || (this.handle == 4)) {
/* 155 */         this.handleOffset = (AquaRangeSliderUI.this.slider.getOrientation() == 1 ? e.getY() - AquaRangeSliderUI.this.yPositionForValue(((RangeSlider)AquaRangeSliderUI.this.slider).getLowValue()) : e.getX() - AquaRangeSliderUI.this.xPositionForValue(((RangeSlider)AquaRangeSliderUI.this.slider).getLowValue()));
/*     */ 
/* 159 */         this.mouseStartLocation = (AquaRangeSliderUI.this.slider.getOrientation() == 1 ? e.getY() : e.getX());
/*     */ 
/* 161 */         AquaRangeSliderUI.this.slider.getModel().setValueIsAdjusting(true);
/*     */       }
/* 163 */       else if ((this.handle == 5) || (this.handle == 6)) {
/* 164 */         this._listener.mousePressed(e);
/* 165 */         AquaRangeSliderUI.this.slider.putClientProperty("RangeSlider.mousePosition", null);
/*     */       }
/*     */     }
/*     */ 
/*     */     public void mouseDragged(MouseEvent e)
/*     */     {
/* 174 */       if (!AquaRangeSliderUI.this.slider.isEnabled()) {
/* 175 */         return;
/*     */       }
/*     */ 
/* 178 */       int newLocation = AquaRangeSliderUI.this.slider.getOrientation() == 1 ? e.getY() : e.getX();
/*     */ 
/* 180 */       int newValue = AquaRangeSliderUI.this.slider.getOrientation() == 1 ? AquaRangeSliderUI.this.valueForYPosition(newLocation) : AquaRangeSliderUI.this.valueForXPosition(newLocation);
/*     */ 
/* 182 */       if (newValue < AquaRangeSliderUI.this.slider.getModel().getMinimum()) {
/* 183 */         newValue = AquaRangeSliderUI.this.slider.getModel().getMinimum();
/*     */       }
/*     */ 
/* 186 */       if (newValue > AquaRangeSliderUI.this.slider.getModel().getMaximum()) {
/* 187 */         newValue = AquaRangeSliderUI.this.slider.getModel().getMaximum();
/*     */       }
/*     */ 
/* 190 */       if (this.handle == 3) {
/* 191 */         if (newLocation - this.mouseStartLocation > 2) {
/* 192 */           this.handle = 2;
/*     */         }
/* 194 */         else if (newLocation - this.mouseStartLocation < -2) {
/* 195 */           this.handle = 1;
/*     */         }
/*     */         else {
/* 198 */           return;
/*     */         }
/*     */       }
/*     */ 
/* 202 */       RangeSlider rangeSlider = (RangeSlider)AquaRangeSliderUI.this.slider;
/* 203 */       switch (this.handle) {
/*     */       case 1:
/* 205 */         rangeSlider.setLowValue(Math.min(newValue, rangeSlider.getHighValue()));
/* 206 */         break;
/*     */       case 2:
/* 208 */         rangeSlider.setHighValue(Math.max(rangeSlider.getLowValue(), newValue));
/* 209 */         break;
/*     */       case 4:
/* 211 */         if (!((RangeSlider)AquaRangeSliderUI.this.slider).isRangeDraggable()) break;
/* 212 */         int delta = AquaRangeSliderUI.this.slider.getOrientation() == 1 ? AquaRangeSliderUI.this.valueForYPosition(newLocation - this.handleOffset) - rangeSlider.getLowValue() : AquaRangeSliderUI.this.valueForXPosition(newLocation - this.handleOffset) - rangeSlider.getLowValue();
/*     */ 
/* 215 */         if ((delta < 0) && (rangeSlider.getLowValue() + delta < rangeSlider.getMinimum())) {
/* 216 */           delta = rangeSlider.getMinimum() - rangeSlider.getLowValue();
/*     */         }
/*     */ 
/* 219 */         if ((delta > 0) && (rangeSlider.getHighValue() + delta > rangeSlider.getMaximum())) {
/* 220 */           delta = rangeSlider.getMaximum() - rangeSlider.getHighValue();
/*     */         }
/*     */ 
/* 223 */         if (delta == 0) break;
/* 224 */         AquaRangeSliderUI.this.offset(delta);
/*     */       case 3:
/*     */       }
/*     */     }
/*     */ 
/*     */     public void mouseReleased(MouseEvent e)
/*     */     {
/* 236 */       AquaRangeSliderUI.this.slider.getModel().setValueIsAdjusting(false);
/* 237 */       AquaRangeSliderUI.this.setMouseReleased(this.handle);
/* 238 */       this._listener.mouseReleased(e);
/*     */     }
/*     */ 
/*     */     private void setCursor(int c) {
/* 242 */       Cursor cursor = Cursor.getPredefinedCursor(c);
/*     */ 
/* 244 */       if (AquaRangeSliderUI.this.slider.getCursor() != cursor)
/* 245 */         AquaRangeSliderUI.this.slider.setCursor(cursor);
/*     */     }
/*     */ 
/*     */     public void mouseMoved(MouseEvent e)
/*     */     {
/* 254 */       if (!AquaRangeSliderUI.this.slider.isEnabled()) {
/* 255 */         return;
/*     */       }
/*     */ 
/* 258 */       int handle = AquaRangeSliderUI.this.getMouseHandle(e.getX(), e.getY());
/* 259 */       AquaRangeSliderUI.this.setMouseRollover(handle);
/* 260 */       switch (handle) {
/*     */       case 1:
/* 262 */         setCursor(0);
/* 263 */         break;
/*     */       case 2:
/* 265 */         setCursor(0);
/* 266 */         break;
/*     */       case 4:
/* 268 */         if (((AquaRangeSliderUI.this.slider instanceof RangeSlider)) && (((RangeSlider)AquaRangeSliderUI.this.slider).isRangeDraggable())) {
/* 269 */           setCursor(13);
/*     */         }
/*     */         else {
/* 272 */           setCursor(0);
/*     */         }
/* 274 */         break;
/*     */       case 0:
/*     */       case 3:
/*     */       default:
/* 277 */         setCursor(0);
/*     */       }
/*     */     }
/*     */ 
/*     */     public void mouseClicked(MouseEvent e)
/*     */     {
/* 287 */       if (e.getClickCount() == 2) {
/* 288 */         AquaRangeSliderUI.this.slider.getModel().setValue(AquaRangeSliderUI.this.slider.getModel().getMinimum());
/* 289 */         AquaRangeSliderUI.this.slider.getModel().setExtent(AquaRangeSliderUI.this.slider.getModel().getMaximum() - AquaRangeSliderUI.this.slider.getModel().getMinimum());
/* 290 */         AquaRangeSliderUI.this.slider.repaint();
/*     */       }
/*     */     }
/*     */ 
/*     */     public void mouseEntered(MouseEvent e)
/*     */     {
/* 299 */       AquaRangeSliderUI.this.hover = true;
/* 300 */       AquaRangeSliderUI.this.slider.repaint();
/*     */     }
/*     */ 
/*     */     public void mouseExited(MouseEvent e)
/*     */     {
/* 308 */       AquaRangeSliderUI.this.hover = false;
/* 309 */       AquaRangeSliderUI.this.slider.repaint();
/* 310 */       setCursor(0);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.aqua.AquaRangeSliderUI
 * JD-Core Version:    0.6.0
 */
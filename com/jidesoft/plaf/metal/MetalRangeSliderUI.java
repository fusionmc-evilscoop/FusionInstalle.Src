/*     */ package com.jidesoft.plaf.metal;
/*     */ 
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
/*     */ import javax.swing.plaf.metal.MetalSliderUI;
/*     */ 
/*     */ public class MetalRangeSliderUI extends MetalSliderUI
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
/*     */   public MetalRangeSliderUI(JSlider slider)
/*     */   {
/*     */   }
/*     */ 
/*     */   public static ComponentUI createUI(JComponent slider)
/*     */   {
/*  31 */     return new MetalRangeSliderUI((JSlider)slider);
/*     */   }
/*     */ 
/*     */   public void paint(Graphics g, JComponent c)
/*     */   {
/*  36 */     this.second = false;
/*  37 */     super.paint(g, c);
/*     */ 
/*  39 */     Rectangle clip = g.getClipBounds();
/*     */ 
/*  41 */     this.second = true;
/*  42 */     Point p = adjustThumbForHighValue();
/*     */ 
/*  44 */     if (clip.intersects(this.thumbRect)) {
/*  45 */       paintThumb(g);
/*     */     }
/*     */ 
/*  48 */     restoreThumbForLowValue(p);
/*  49 */     this.second = false;
/*     */   }
/*     */ 
/*     */   protected void restoreThumbForLowValue(Point p) {
/*  53 */     this.thumbRect.x = p.x;
/*  54 */     this.thumbRect.y = p.y;
/*     */   }
/*     */ 
/*     */   protected Point adjustThumbForHighValue() {
/*  58 */     Point p = this.thumbRect.getLocation();
/*  59 */     if (this.slider.getOrientation() == 0) {
/*  60 */       int valuePosition = xPositionForValue(((RangeSlider)this.slider).getHighValue());
/*  61 */       this.thumbRect.x = (valuePosition - this.thumbRect.width / 2);
/*     */     }
/*     */     else {
/*  64 */       int valuePosition = yPositionForValue(((RangeSlider)this.slider).getHighValue());
/*  65 */       this.thumbRect.y = (valuePosition - this.thumbRect.height / 2);
/*     */     }
/*  67 */     return p;
/*     */   }
/*     */ 
/*     */   protected void adjustSnapHighValue() {
/*  71 */     int sliderValue = ((RangeSlider)this.slider).getHighValue();
/*  72 */     int snappedValue = sliderValue;
/*  73 */     int majorTickSpacing = this.slider.getMajorTickSpacing();
/*  74 */     int minorTickSpacing = this.slider.getMinorTickSpacing();
/*  75 */     int tickSpacing = 0;
/*     */ 
/*  77 */     if (minorTickSpacing > 0) {
/*  78 */       tickSpacing = minorTickSpacing;
/*     */     }
/*  80 */     else if (majorTickSpacing > 0) {
/*  81 */       tickSpacing = majorTickSpacing;
/*     */     }
/*     */ 
/*  84 */     if (tickSpacing != 0)
/*     */     {
/*  86 */       if ((sliderValue - this.slider.getMinimum()) % tickSpacing != 0) {
/*  87 */         float temp = (sliderValue - this.slider.getMinimum()) / tickSpacing;
/*     */ 
/*  89 */         int whichTick = Math.round(temp);
/*  90 */         snappedValue = this.slider.getMinimum() + whichTick * tickSpacing;
/*     */       }
/*     */ 
/*  94 */       if (snappedValue != sliderValue)
/*  95 */         ((RangeSlider)this.slider).setHighValue(snappedValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void calculateThumbLocation()
/*     */   {
/* 102 */     if (this.slider.getSnapToTicks()) {
/* 103 */       adjustSnapHighValue();
/*     */     }
/* 105 */     super.calculateThumbLocation();
/*     */   }
/*     */ 
/*     */   protected BasicSliderUI.TrackListener createTrackListener(JSlider slider)
/*     */   {
/* 110 */     return new RangeTrackListener(super.createTrackListener(slider));
/*     */   }
/*     */ 
/*     */   private void offset(int delta)
/*     */   {
/* 300 */     this.slider.getModel().setValue(((RangeSlider)this.slider).getLowValue() + delta);
/*     */   }
/*     */ 
/*     */   protected int getMouseHandle(int x, int y)
/*     */   {
/* 316 */     Rectangle rect = this.trackRect;
/*     */ 
/* 318 */     this.slider.putClientProperty("RangeSlider.mousePosition", null);
/*     */ 
/* 320 */     if (this.thumbRect.contains(x, y)) {
/* 321 */       return 1;
/*     */     }
/* 323 */     Point p = adjustThumbForHighValue();
/* 324 */     if (this.thumbRect.contains(x, y)) {
/* 325 */       restoreThumbForLowValue(p);
/* 326 */       return 2;
/*     */     }
/* 328 */     restoreThumbForLowValue(p);
/*     */ 
/* 330 */     if (this.slider.getOrientation() == 1) {
/* 331 */       int minY = yPositionForValue(((RangeSlider)this.slider).getLowValue());
/* 332 */       int maxY = yPositionForValue(((RangeSlider)this.slider).getHighValue());
/* 333 */       Rectangle midRect = new Rectangle(rect.x, Math.min(minY, maxY) + this.thumbRect.height / 2, rect.width, Math.abs(maxY - minY) - this.thumbRect.height);
/* 334 */       if (midRect.contains(x, y)) {
/* 335 */         return 4;
/*     */       }
/* 337 */       int sy = rect.y + Math.max(minY, maxY) + this.thumbRect.height / 2;
/* 338 */       Rectangle lowerRect = new Rectangle(rect.x, sy, rect.width, rect.y + rect.height - sy);
/* 339 */       if (lowerRect.contains(x, y)) {
/* 340 */         this.slider.putClientProperty("RangeSlider.mousePosition", Boolean.valueOf(true));
/* 341 */         return 5;
/*     */       }
/* 343 */       Rectangle upperRect = new Rectangle(rect.x, rect.y, rect.width, Math.min(maxY, minY) - this.thumbRect.height / 2);
/* 344 */       if (upperRect.contains(x, y)) {
/* 345 */         this.slider.putClientProperty("RangeSlider.mousePosition", Boolean.valueOf(false));
/* 346 */         return 6;
/*     */       }
/*     */ 
/* 349 */       return 0;
/*     */     }
/*     */ 
/* 352 */     int minX = xPositionForValue(((RangeSlider)this.slider).getLowValue());
/* 353 */     int maxX = xPositionForValue(((RangeSlider)this.slider).getHighValue());
/*     */ 
/* 355 */     Rectangle midRect = new Rectangle(Math.min(minX, maxX) + this.thumbRect.width / 2, rect.y, Math.abs(maxX - minX) - this.thumbRect.width, rect.height);
/* 356 */     if (midRect.contains(x, y)) {
/* 357 */       return 4;
/*     */     }
/* 359 */     Rectangle lowerRect = new Rectangle(rect.x, rect.y, Math.min(minX, maxX) - this.thumbRect.width / 2 - rect.x, rect.height);
/* 360 */     if (lowerRect.contains(x, y)) {
/* 361 */       this.slider.putClientProperty("RangeSlider.mousePosition", Boolean.valueOf(true));
/* 362 */       return 5;
/*     */     }
/* 364 */     int sx = rect.x + Math.abs(maxX - minX) + this.thumbRect.width / 2;
/* 365 */     Rectangle upperRect = new Rectangle(sx, rect.y, rect.width - sx, rect.height);
/* 366 */     if (upperRect.contains(x, y)) {
/* 367 */       this.slider.putClientProperty("RangeSlider.mousePosition", Boolean.valueOf(false));
/* 368 */       return 6;
/*     */     }
/* 370 */     return 0;
/*     */   }
/*     */ 
/*     */   public void paintThumb(Graphics g)
/*     */   {
/*     */     try
/*     */     {
/* 384 */       Field field = getClass().getSuperclass().getDeclaredField("rollover");
/* 385 */       field.setAccessible(true);
/* 386 */       field.set(this, Boolean.valueOf(this.second ? this.rollover2 : this.rollover1));
/*     */ 
/* 388 */       field = getClass().getSuperclass().getDeclaredField("pressed");
/* 389 */       field.setAccessible(true);
/* 390 */       field.set(this, Boolean.valueOf(this.second ? this.pressed2 : this.pressed1));
/*     */     }
/*     */     catch (NoSuchFieldException e)
/*     */     {
/*     */     }
/*     */     catch (IllegalAccessException e)
/*     */     {
/*     */     }
/*     */ 
/* 399 */     super.paintThumb(g);
/*     */   }
/*     */ 
/*     */   protected void setMouseRollover(int handle) {
/* 403 */     switch (handle) {
/*     */     case 1:
/* 405 */       this.rollover1 = true;
/* 406 */       this.rollover2 = false;
/*     */ 
/* 408 */       break;
/*     */     case 2:
/* 410 */       this.rollover2 = true;
/* 411 */       this.rollover1 = false;
/*     */ 
/* 413 */       break;
/*     */     case 4:
/* 415 */       this.rollover1 = true;
/* 416 */       this.rollover2 = true;
/*     */ 
/* 418 */       break;
/*     */     case 0:
/* 420 */       this.rollover1 = false;
/* 421 */       this.rollover2 = false;
/*     */     case 3:
/*     */     }
/* 424 */     this.slider.repaint(this.thumbRect);
/* 425 */     Point p = adjustThumbForHighValue();
/* 426 */     this.slider.repaint(this.thumbRect);
/* 427 */     restoreThumbForLowValue(p);
/*     */   }
/*     */ 
/*     */   protected void setMousePressed(int handle) {
/* 431 */     switch (handle) {
/*     */     case 1:
/* 433 */       this.pressed1 = true;
/* 434 */       this.pressed2 = false;
/*     */ 
/* 436 */       break;
/*     */     case 2:
/* 438 */       this.pressed2 = true;
/* 439 */       this.pressed1 = false;
/*     */ 
/* 441 */       break;
/*     */     case 4:
/* 443 */       this.pressed1 = true;
/* 444 */       this.pressed2 = true;
/*     */ 
/* 446 */       break;
/*     */     case 0:
/* 448 */       this.pressed1 = false;
/* 449 */       this.pressed2 = false;
/*     */     case 3:
/*     */     }
/* 452 */     this.slider.repaint(this.thumbRect);
/* 453 */     Point p = adjustThumbForHighValue();
/* 454 */     this.slider.repaint(this.thumbRect);
/* 455 */     restoreThumbForLowValue(p);
/*     */   }
/*     */ 
/*     */   protected void setMouseReleased(int handle)
/*     */   {
/* 460 */     this.pressed1 = false;
/* 461 */     this.pressed2 = false;
/* 462 */     this.slider.repaint(this.thumbRect);
/* 463 */     Point p = adjustThumbForHighValue();
/* 464 */     this.slider.repaint(this.thumbRect);
/* 465 */     restoreThumbForLowValue(p);
/*     */   }
/*     */ 
/*     */   public void scrollByBlock(int direction)
/*     */   {
/* 470 */     synchronized (this.slider)
/*     */     {
/* 473 */       Object clientProperty = this.slider.getClientProperty("RangeSlider.mousePosition");
/*     */       int oldValue;
/*     */       int oldValue;
/* 474 */       if (clientProperty == null) {
/* 475 */         oldValue = this.slider.getValue();
/*     */       }
/*     */       else
/*     */       {
/*     */         int oldValue;
/* 477 */         if (Boolean.TRUE.equals(clientProperty)) {
/* 478 */           oldValue = ((RangeSlider)this.slider).getLowValue();
/*     */         }
/*     */         else
/* 481 */           oldValue = ((RangeSlider)this.slider).getHighValue();
/*     */       }
/* 483 */       int blockIncrement = (this.slider.getMaximum() - this.slider.getMinimum()) / 10;
/*     */ 
/* 485 */       if ((blockIncrement <= 0) && (this.slider.getMaximum() > this.slider.getMinimum()))
/*     */       {
/* 488 */         blockIncrement = 1;
/*     */       }
/*     */ 
/* 491 */       int delta = blockIncrement * (direction > 0 ? 1 : -1);
/* 492 */       if (clientProperty == null) {
/* 493 */         this.slider.setValue(oldValue + delta);
/*     */       }
/* 495 */       else if (Boolean.TRUE.equals(clientProperty)) {
/* 496 */         ((RangeSlider)this.slider).setLowValue(oldValue + delta);
/*     */       }
/*     */       else
/* 499 */         ((RangeSlider)this.slider).setHighValue(oldValue + delta);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void scrollByUnit(int direction)
/*     */   {
/* 506 */     synchronized (this.slider)
/*     */     {
/* 509 */       Object clientProperty = this.slider.getClientProperty("RangeSlider.mousePosition");
/*     */       int oldValue;
/*     */       int oldValue;
/* 510 */       if (clientProperty == null) {
/* 511 */         oldValue = this.slider.getValue();
/*     */       }
/*     */       else
/*     */       {
/*     */         int oldValue;
/* 513 */         if (Boolean.TRUE.equals(clientProperty)) {
/* 514 */           oldValue = ((RangeSlider)this.slider).getLowValue();
/*     */         }
/*     */         else
/* 517 */           oldValue = ((RangeSlider)this.slider).getHighValue();
/*     */       }
/* 519 */       int delta = 1 * (direction > 0 ? 1 : -1);
/*     */ 
/* 521 */       if (clientProperty == null) {
/* 522 */         this.slider.setValue(oldValue + delta);
/*     */       }
/* 524 */       else if (Boolean.TRUE.equals(clientProperty)) {
/* 525 */         ((RangeSlider)this.slider).setLowValue(oldValue + delta);
/*     */       }
/*     */       else
/* 528 */         ((RangeSlider)this.slider).setHighValue(oldValue + delta);
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
/* 119 */       super();
/* 120 */       this._listener = listener;
/*     */     }
/*     */ 
/*     */     public void mousePressed(MouseEvent e)
/*     */     {
/* 128 */       if (!MetalRangeSliderUI.this.slider.isEnabled()) {
/* 129 */         return;
/*     */       }
/*     */ 
/* 132 */       if (MetalRangeSliderUI.this.slider.isRequestFocusEnabled()) {
/* 133 */         MetalRangeSliderUI.this.slider.requestFocus();
/*     */       }
/*     */ 
/* 136 */       this.handle = MetalRangeSliderUI.this.getMouseHandle(e.getX(), e.getY());
/* 137 */       MetalRangeSliderUI.this.setMousePressed(this.handle);
/*     */ 
/* 139 */       if ((this.handle == 2) || (this.handle == 1) || (this.handle == 4)) {
/* 140 */         this.handleOffset = (MetalRangeSliderUI.this.slider.getOrientation() == 1 ? e.getY() - MetalRangeSliderUI.this.yPositionForValue(((RangeSlider)MetalRangeSliderUI.this.slider).getLowValue()) : e.getX() - MetalRangeSliderUI.this.xPositionForValue(((RangeSlider)MetalRangeSliderUI.this.slider).getLowValue()));
/*     */ 
/* 144 */         this.mouseStartLocation = (MetalRangeSliderUI.this.slider.getOrientation() == 1 ? e.getY() : e.getX());
/*     */ 
/* 146 */         MetalRangeSliderUI.this.slider.getModel().setValueIsAdjusting(true);
/*     */       }
/* 148 */       else if ((this.handle == 5) || (this.handle == 6)) {
/* 149 */         this._listener.mousePressed(e);
/* 150 */         MetalRangeSliderUI.this.slider.putClientProperty("RangeSlider.mousePosition", null);
/*     */       }
/*     */     }
/*     */ 
/*     */     public void mouseDragged(MouseEvent e)
/*     */     {
/* 159 */       if (!MetalRangeSliderUI.this.slider.isEnabled()) {
/* 160 */         return;
/*     */       }
/*     */ 
/* 163 */       int newLocation = MetalRangeSliderUI.this.slider.getOrientation() == 1 ? e.getY() : e.getX();
/*     */ 
/* 165 */       int newValue = MetalRangeSliderUI.this.slider.getOrientation() == 1 ? MetalRangeSliderUI.this.valueForYPosition(newLocation) : MetalRangeSliderUI.this.valueForXPosition(newLocation);
/*     */ 
/* 167 */       if (newValue < MetalRangeSliderUI.this.slider.getModel().getMinimum()) {
/* 168 */         newValue = MetalRangeSliderUI.this.slider.getModel().getMinimum();
/*     */       }
/*     */ 
/* 171 */       if (newValue > MetalRangeSliderUI.this.slider.getModel().getMaximum()) {
/* 172 */         newValue = MetalRangeSliderUI.this.slider.getModel().getMaximum();
/*     */       }
/*     */ 
/* 175 */       if (this.handle == 3) {
/* 176 */         if (newLocation - this.mouseStartLocation > 2) {
/* 177 */           this.handle = 2;
/*     */         }
/* 179 */         else if (newLocation - this.mouseStartLocation < -2) {
/* 180 */           this.handle = 1;
/*     */         }
/*     */         else {
/* 183 */           return;
/*     */         }
/*     */       }
/*     */ 
/* 187 */       RangeSlider rangeSlider = (RangeSlider)MetalRangeSliderUI.this.slider;
/* 188 */       switch (this.handle) {
/*     */       case 1:
/* 190 */         rangeSlider.setLowValue(Math.min(newValue, rangeSlider.getHighValue()));
/* 191 */         break;
/*     */       case 2:
/* 193 */         rangeSlider.setHighValue(Math.max(rangeSlider.getLowValue(), newValue));
/* 194 */         break;
/*     */       case 4:
/* 196 */         if (!((RangeSlider)MetalRangeSliderUI.this.slider).isRangeDraggable()) break;
/* 197 */         int delta = MetalRangeSliderUI.this.slider.getOrientation() == 1 ? MetalRangeSliderUI.this.valueForYPosition(newLocation - this.handleOffset) - rangeSlider.getLowValue() : MetalRangeSliderUI.this.valueForXPosition(newLocation - this.handleOffset) - rangeSlider.getLowValue();
/*     */ 
/* 200 */         if ((delta < 0) && (rangeSlider.getLowValue() + delta < rangeSlider.getMinimum())) {
/* 201 */           delta = rangeSlider.getMinimum() - rangeSlider.getLowValue();
/*     */         }
/*     */ 
/* 204 */         if ((delta > 0) && (rangeSlider.getHighValue() + delta > rangeSlider.getMaximum())) {
/* 205 */           delta = rangeSlider.getMaximum() - rangeSlider.getHighValue();
/*     */         }
/*     */ 
/* 208 */         if (delta == 0) break;
/* 209 */         MetalRangeSliderUI.this.offset(delta);
/*     */       case 3:
/*     */       }
/*     */     }
/*     */ 
/*     */     public void mouseReleased(MouseEvent e)
/*     */     {
/* 221 */       MetalRangeSliderUI.this.slider.getModel().setValueIsAdjusting(false);
/* 222 */       MetalRangeSliderUI.this.setMouseReleased(this.handle);
/* 223 */       this._listener.mouseReleased(e);
/*     */     }
/*     */ 
/*     */     private void setCursor(int c) {
/* 227 */       Cursor cursor = Cursor.getPredefinedCursor(c);
/*     */ 
/* 229 */       if (MetalRangeSliderUI.this.slider.getCursor() != cursor)
/* 230 */         MetalRangeSliderUI.this.slider.setCursor(cursor);
/*     */     }
/*     */ 
/*     */     public void mouseMoved(MouseEvent e)
/*     */     {
/* 239 */       if (!MetalRangeSliderUI.this.slider.isEnabled()) {
/* 240 */         return;
/*     */       }
/*     */ 
/* 243 */       int handle = MetalRangeSliderUI.this.getMouseHandle(e.getX(), e.getY());
/* 244 */       MetalRangeSliderUI.this.setMouseRollover(handle);
/* 245 */       switch (handle) {
/*     */       case 1:
/* 247 */         setCursor(0);
/* 248 */         break;
/*     */       case 2:
/* 250 */         setCursor(0);
/* 251 */         break;
/*     */       case 4:
/* 253 */         if (((MetalRangeSliderUI.this.slider instanceof RangeSlider)) && (((RangeSlider)MetalRangeSliderUI.this.slider).isRangeDraggable())) {
/* 254 */           setCursor(13);
/*     */         }
/*     */         else {
/* 257 */           setCursor(0);
/*     */         }
/* 259 */         break;
/*     */       case 0:
/*     */       case 3:
/*     */       default:
/* 262 */         setCursor(0);
/*     */       }
/*     */     }
/*     */ 
/*     */     public void mouseClicked(MouseEvent e)
/*     */     {
/* 272 */       if (e.getClickCount() == 2) {
/* 273 */         MetalRangeSliderUI.this.slider.getModel().setValue(MetalRangeSliderUI.this.slider.getModel().getMinimum());
/* 274 */         MetalRangeSliderUI.this.slider.getModel().setExtent(MetalRangeSliderUI.this.slider.getModel().getMaximum() - MetalRangeSliderUI.this.slider.getModel().getMinimum());
/* 275 */         MetalRangeSliderUI.this.slider.repaint();
/*     */       }
/*     */     }
/*     */ 
/*     */     public void mouseEntered(MouseEvent e)
/*     */     {
/* 284 */       MetalRangeSliderUI.this.hover = true;
/* 285 */       MetalRangeSliderUI.this.slider.repaint();
/*     */     }
/*     */ 
/*     */     public void mouseExited(MouseEvent e)
/*     */     {
/* 293 */       MetalRangeSliderUI.this.hover = false;
/* 294 */       MetalRangeSliderUI.this.slider.repaint();
/* 295 */       setCursor(0);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.metal.MetalRangeSliderUI
 * JD-Core Version:    0.6.0
 */
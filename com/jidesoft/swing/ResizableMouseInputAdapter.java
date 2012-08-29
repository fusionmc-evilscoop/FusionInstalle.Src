/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Container;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.JApplet;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JWindow;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.event.MouseInputAdapter;
/*     */ 
/*     */ public class ResizableMouseInputAdapter extends MouseInputAdapter
/*     */ {
/*     */   private static Rectangle _startingBounds;
/*     */   private int _eventMouseScreenX;
/*     */   private int _eventMouseScreenY;
/*     */   private int _resizeCorner;
/*     */   protected static final int RESIZE_NONE = 0;
/*  39 */   private boolean _discardRelease = false;
/*     */   private Resizable _resizable;
/*     */ 
/*     */   public ResizableMouseInputAdapter(Resizable resizable)
/*     */   {
/*  44 */     this._resizable = resizable;
/*     */   }
/*     */ 
/*     */   private boolean isResizable(int resizeDir) {
/*  48 */     return (this._resizable != null) && ((this._resizable.getResizableCorners() & resizeDir) != 0);
/*     */   }
/*     */ 
/*     */   public void mousePressed(MouseEvent e)
/*     */   {
/*  53 */     this._resizeCorner = 0;
/*     */ 
/*  55 */     _startingBounds = this._resizable.getComponent().getBounds();
/*  56 */     if (this._resizable.isTopLevel()) {
/*  57 */       Point location = new Point(_startingBounds.x, _startingBounds.y);
/*  58 */       SwingUtilities.convertPointToScreen(location, this._resizable.getComponent());
/*  59 */       _startingBounds.x = location.x;
/*  60 */       _startingBounds.y = location.y;
/*     */     }
/*     */ 
/*  63 */     Point p = new Point(e.getX(), e.getY());
/*  64 */     SwingUtilities.convertPointToScreen(p, (Component)e.getSource());
/*  65 */     this._eventMouseScreenX = p.x;
/*  66 */     this._eventMouseScreenY = p.y;
/*     */ 
/*  68 */     if ((e.getSource() instanceof Resizable.ResizeCorner)) {
/*  69 */       Resizable.ResizeCorner corner = (Resizable.ResizeCorner)e.getSource();
/*  70 */       this._resizeCorner = corner.getCorner();
/*     */     }
/*  73 */     else if (e.getSource() == this._resizable.getComponent()) {
/*  74 */       Insets i = this._resizable.getResizeInsets();
/*  75 */       if (e.getX() <= i.left) {
/*  76 */         if ((i.top > 0) && (e.getY() < this._resizable.getResizeCornerSize() + i.top)) {
/*  77 */           this._resizeCorner = 1;
/*     */         }
/*  79 */         else if ((i.bottom > 0) && (e.getY() > this._resizable.getComponent().getHeight() - this._resizable.getResizeCornerSize() - i.bottom))
/*     */         {
/*  81 */           this._resizeCorner = 64;
/*     */         }
/*     */         else {
/*  84 */           this._resizeCorner = 128;
/*     */         }
/*     */       }
/*  87 */       else if ((i.right > 0) && (e.getX() >= this._resizable.getComponent().getWidth() - i.right)) {
/*  88 */         if ((i.top > 0) && (e.getY() < this._resizable.getResizeCornerSize() + i.top)) {
/*  89 */           this._resizeCorner = 4;
/*     */         }
/*  91 */         else if ((i.bottom > 0) && (e.getY() > this._resizable.getComponent().getHeight() - this._resizable.getResizeCornerSize() - i.bottom))
/*     */         {
/*  93 */           this._resizeCorner = 16;
/*     */         }
/*     */         else {
/*  96 */           this._resizeCorner = 8;
/*     */         }
/*     */       }
/*  99 */       else if ((i.top > 0) && (e.getY() <= i.top)) {
/* 100 */         if ((i.left > 0) && (e.getX() < this._resizable.getResizeCornerSize() + i.left)) {
/* 101 */           this._resizeCorner = 1;
/*     */         }
/* 103 */         else if ((i.right > 0) && (e.getX() > this._resizable.getComponent().getWidth() - this._resizable.getResizeCornerSize() - i.right))
/*     */         {
/* 105 */           this._resizeCorner = 4;
/*     */         }
/*     */         else {
/* 108 */           this._resizeCorner = 2;
/*     */         }
/*     */       }
/* 111 */       else if ((i.bottom > 0) && (e.getY() >= this._resizable.getComponent().getHeight() - i.bottom)) {
/* 112 */         if ((i.left > 0) && (e.getX() < this._resizable.getResizeCornerSize() + i.left)) {
/* 113 */           this._resizeCorner = 64;
/*     */         }
/* 115 */         else if ((i.right > 0) && (e.getX() > this._resizable.getComponent().getWidth() - this._resizable.getResizeCornerSize() - i.right))
/*     */         {
/* 117 */           this._resizeCorner = 16;
/*     */         }
/*     */         else {
/* 120 */           this._resizeCorner = 32;
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 126 */         this._discardRelease = true;
/* 127 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 131 */     Cursor s = Cursor.getDefaultCursor();
/* 132 */     if (isResizable(this._resizeCorner)) {
/* 133 */       boolean ltr = this._resizable.getComponent().getComponentOrientation().isLeftToRight();
/* 134 */       switch (this._resizeCorner) {
/*     */       case 32:
/* 136 */         s = Cursor.getPredefinedCursor(9);
/* 137 */         break;
/*     */       case 2:
/* 139 */         s = Cursor.getPredefinedCursor(8);
/* 140 */         break;
/*     */       case 128:
/* 142 */         s = Cursor.getPredefinedCursor(ltr ? 10 : 11);
/* 143 */         break;
/*     */       case 8:
/* 145 */         s = Cursor.getPredefinedCursor(ltr ? 11 : 10);
/* 146 */         break;
/*     */       case 16:
/* 148 */         s = Cursor.getPredefinedCursor(ltr ? 5 : 4);
/* 149 */         break;
/*     */       case 64:
/* 151 */         s = Cursor.getPredefinedCursor(ltr ? 4 : 5);
/* 152 */         break;
/*     */       case 1:
/* 154 */         s = Cursor.getPredefinedCursor(ltr ? 6 : 7);
/* 155 */         break;
/*     */       case 4:
/* 157 */         s = Cursor.getPredefinedCursor(ltr ? 7 : 6);
/*     */       }
/*     */ 
/* 161 */       Container c = this._resizable.getComponent().getTopLevelAncestor();
/*     */ 
/* 163 */       if ((c instanceof JFrame)) {
/* 164 */         ((JFrame)c).getGlassPane().setVisible(true);
/* 165 */         ((JFrame)c).getGlassPane().setCursor(s);
/*     */       }
/* 167 */       else if ((c instanceof JApplet)) {
/* 168 */         ((JApplet)c).getGlassPane().setVisible(true);
/* 169 */         ((JApplet)c).getGlassPane().setCursor(s);
/*     */       }
/* 171 */       else if ((c instanceof JWindow)) {
/* 172 */         ((JWindow)c).getGlassPane().setVisible(true);
/* 173 */         ((JWindow)c).getGlassPane().setCursor(s);
/*     */       }
/* 175 */       else if ((c instanceof JDialog)) {
/* 176 */         ((JDialog)c).getGlassPane().setVisible(true);
/* 177 */         ((JDialog)c).getGlassPane().setCursor(s);
/*     */       }
/*     */ 
/* 180 */       this._resizable.beginResizing(this._resizeCorner);
/*     */     }
/*     */     else {
/* 183 */       this._resizeCorner = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void mouseDragged(MouseEvent e)
/*     */   {
/* 189 */     if (_startingBounds == null) {
/* 190 */       return;
/*     */     }
/*     */ 
/* 193 */     Point p = new Point(e.getX(), e.getY());
/* 194 */     Component c = (Component)e.getSource();
/* 195 */     SwingUtilities.convertPointToScreen(p, c);
/*     */ 
/* 197 */     int deltaX = this._eventMouseScreenX - p.x;
/* 198 */     int deltaY = this._eventMouseScreenY - p.y;
/*     */ 
/* 200 */     Dimension min = this._resizable.getComponent().getMinimumSize();
/* 201 */     Dimension max = this._resizable.getComponent().getMaximumSize();
/*     */ 
/* 203 */     Point point = new Point(this._resizable.getComponent().getX(), this._resizable.getComponent().getY());
/*     */ 
/* 205 */     if (this._resizable.isTopLevel()) {
/* 206 */       SwingUtilities.convertPointToScreen(point, this._resizable.getComponent());
/*     */     }
/*     */ 
/* 209 */     int newX = point.x;
/* 210 */     int newY = point.y;
/*     */ 
/* 214 */     Rectangle parentBounds = this._resizable.isTopLevel() ? new Rectangle(-2147483648, -2147483648, 2147483647, 2147483647) : this._resizable.getComponent().getParent().getBounds();
/*     */ 
/* 217 */     int actualResizeCorner = this._resizeCorner;
/* 218 */     boolean ltr = this._resizable.getComponent().getComponentOrientation().isLeftToRight();
/* 219 */     if (!ltr)
/* 220 */       switch (this._resizeCorner) {
/*     */       case 1:
/* 222 */         actualResizeCorner = 4;
/*     */       case 4:
/* 224 */         actualResizeCorner = 1;
/*     */       case 64:
/* 226 */         actualResizeCorner = 16;
/*     */       case 16:
/* 228 */         actualResizeCorner = 64;
/*     */       case 128:
/* 230 */         actualResizeCorner = 8;
/*     */       case 8:
/* 232 */         actualResizeCorner = 128;
/*     */       }
/*     */     int newW;
/*     */     int newH;
/* 235 */     switch (actualResizeCorner) {
/*     */     case 0:
/* 237 */       return;
/*     */     case 2:
/* 239 */       if (_startingBounds.height + deltaY < min.height)
/* 240 */         deltaY = -(_startingBounds.height - min.height);
/* 241 */       else if (_startingBounds.height + deltaY > max.height) {
/* 242 */         deltaY = max.height - _startingBounds.height;
/*     */       }
/*     */ 
/* 246 */       newX = _startingBounds.x;
/* 247 */       newY = _startingBounds.y - deltaY;
/* 248 */       newW = _startingBounds.width;
/* 249 */       newH = _startingBounds.height + deltaY;
/*     */ 
/* 251 */       break;
/*     */     case 4:
/* 253 */       if (_startingBounds.height + deltaY < min.height)
/* 254 */         deltaY = -(_startingBounds.height - min.height);
/* 255 */       else if (_startingBounds.height + deltaY > max.height) {
/* 256 */         deltaY = max.height - _startingBounds.height;
/*     */       }
/*     */ 
/* 260 */       if (_startingBounds.width - deltaX < min.width)
/* 261 */         deltaX = _startingBounds.width - min.width;
/* 262 */       else if (_startingBounds.width - deltaX > max.width)
/* 263 */         deltaX = -(max.width - _startingBounds.width);
/* 264 */       if (_startingBounds.x + _startingBounds.width - deltaX > parentBounds.width) {
/* 265 */         deltaX = _startingBounds.x + _startingBounds.width - parentBounds.width;
/*     */       }
/* 267 */       newX = _startingBounds.x;
/* 268 */       newY = _startingBounds.y - deltaY;
/* 269 */       newW = _startingBounds.width - deltaX;
/* 270 */       newH = _startingBounds.height + deltaY;
/* 271 */       break;
/*     */     case 8:
/* 273 */       if (_startingBounds.width - deltaX < min.width)
/* 274 */         deltaX = _startingBounds.width - min.width;
/* 275 */       else if (_startingBounds.width - deltaX > max.width)
/* 276 */         deltaX = -(max.width - _startingBounds.width);
/* 277 */       if (_startingBounds.x + _startingBounds.width - deltaX > parentBounds.width) {
/* 278 */         deltaX = _startingBounds.x + _startingBounds.width - parentBounds.width;
/*     */       }
/* 280 */       newW = _startingBounds.width - deltaX;
/* 281 */       newH = _startingBounds.height;
/* 282 */       break;
/*     */     case 16:
/* 284 */       if (_startingBounds.width - deltaX < min.width)
/* 285 */         deltaX = _startingBounds.width - min.width;
/* 286 */       else if (_startingBounds.width - deltaX > max.width)
/* 287 */         deltaX = -(max.width - _startingBounds.width);
/* 288 */       if (_startingBounds.x + _startingBounds.width - deltaX > parentBounds.width) {
/* 289 */         deltaX = _startingBounds.x + _startingBounds.width - parentBounds.width;
/*     */       }
/* 291 */       if (_startingBounds.height - deltaY < min.height)
/* 292 */         deltaY = _startingBounds.height - min.height;
/* 293 */       else if (_startingBounds.height - deltaY > max.height)
/* 294 */         deltaY = -(max.height - _startingBounds.height);
/* 295 */       if (_startingBounds.y + _startingBounds.height - deltaY > parentBounds.height) {
/* 296 */         deltaY = _startingBounds.y + _startingBounds.height - parentBounds.height;
/*     */       }
/*     */ 
/* 299 */       newW = _startingBounds.width - deltaX;
/* 300 */       newH = _startingBounds.height - deltaY;
/* 301 */       break;
/*     */     case 32:
/* 303 */       if (_startingBounds.height - deltaY < min.height)
/* 304 */         deltaY = _startingBounds.height - min.height;
/* 305 */       else if (_startingBounds.height - deltaY > max.height)
/* 306 */         deltaY = -(max.height - _startingBounds.height);
/* 307 */       if (_startingBounds.y + _startingBounds.height - deltaY > parentBounds.height) {
/* 308 */         deltaY = _startingBounds.y + _startingBounds.height - parentBounds.height;
/*     */       }
/*     */ 
/* 311 */       newW = _startingBounds.width;
/* 312 */       newH = _startingBounds.height - deltaY;
/* 313 */       break;
/*     */     case 64:
/* 315 */       if (_startingBounds.height - deltaY < min.height)
/* 316 */         deltaY = _startingBounds.height - min.height;
/* 317 */       else if (_startingBounds.height - deltaY > max.height)
/* 318 */         deltaY = -(max.height - _startingBounds.height);
/* 319 */       if (_startingBounds.y + _startingBounds.height - deltaY > parentBounds.height) {
/* 320 */         deltaY = _startingBounds.y + _startingBounds.height - parentBounds.height;
/*     */       }
/* 322 */       if (_startingBounds.width + deltaX < min.width)
/* 323 */         deltaX = -(_startingBounds.width - min.width);
/* 324 */       else if (_startingBounds.width + deltaX > max.width) {
/* 325 */         deltaX = max.width - _startingBounds.width;
/*     */       }
/*     */ 
/* 330 */       newX = _startingBounds.x - deltaX;
/* 331 */       newY = _startingBounds.y;
/* 332 */       newW = _startingBounds.width + deltaX;
/* 333 */       newH = _startingBounds.height - deltaY;
/* 334 */       break;
/*     */     case 128:
/* 336 */       if (_startingBounds.width + deltaX < min.width)
/* 337 */         deltaX = -(_startingBounds.width - min.width);
/* 338 */       else if (_startingBounds.width + deltaX > max.width) {
/* 339 */         deltaX = max.width - _startingBounds.width;
/*     */       }
/*     */ 
/* 343 */       newX = _startingBounds.x - deltaX;
/* 344 */       newY = _startingBounds.y;
/* 345 */       newW = _startingBounds.width + deltaX;
/* 346 */       newH = _startingBounds.height;
/*     */ 
/* 348 */       break;
/*     */     case 1:
/* 350 */       if (_startingBounds.width + deltaX < min.width)
/* 351 */         deltaX = -(_startingBounds.width - min.width);
/* 352 */       else if (_startingBounds.width + deltaX > max.width) {
/* 353 */         deltaX = max.width - _startingBounds.width;
/*     */       }
/*     */ 
/* 357 */       if (_startingBounds.height + deltaY < min.height)
/* 358 */         deltaY = -(_startingBounds.height - min.height);
/* 359 */       else if (_startingBounds.height + deltaY > max.height) {
/* 360 */         deltaY = max.height - _startingBounds.height;
/*     */       }
/*     */ 
/* 365 */       newX = _startingBounds.x - deltaX;
/* 366 */       newY = _startingBounds.y - deltaY;
/* 367 */       newW = _startingBounds.width + deltaX;
/* 368 */       newH = _startingBounds.height + deltaY;
/* 369 */       break;
/*     */     default:
/* 371 */       return;
/*     */     }
/*     */ 
/* 374 */     this._resizable.resizing(this._resizeCorner, newX, newY, newW, newH);
/*     */   }
/*     */ 
/*     */   public void mouseReleased(MouseEvent e)
/*     */   {
/* 379 */     _startingBounds = null;
/*     */ 
/* 381 */     if (this._discardRelease) {
/* 382 */       this._discardRelease = false;
/* 383 */       return;
/*     */     }
/*     */ 
/* 386 */     if (this._resizeCorner != 0) {
/* 387 */       Container c = this._resizable.getComponent().getTopLevelAncestor();
/* 388 */       if ((c instanceof JFrame)) {
/* 389 */         ((JFrame)this._resizable.getComponent().getTopLevelAncestor()).getGlassPane().setCursor(Cursor.getDefaultCursor());
/*     */ 
/* 391 */         ((JFrame)this._resizable.getComponent().getTopLevelAncestor()).getGlassPane().setVisible(false);
/*     */       }
/* 393 */       else if ((c instanceof JApplet)) {
/* 394 */         ((JApplet)c).getGlassPane().setCursor(Cursor.getDefaultCursor());
/* 395 */         ((JApplet)c).getGlassPane().setVisible(false);
/*     */       }
/* 397 */       else if ((c instanceof JWindow)) {
/* 398 */         ((JWindow)c).getGlassPane().setCursor(Cursor.getDefaultCursor());
/* 399 */         ((JWindow)c).getGlassPane().setVisible(false);
/*     */       }
/* 401 */       else if ((c instanceof JDialog)) {
/* 402 */         ((JDialog)c).getGlassPane().setCursor(Cursor.getDefaultCursor());
/* 403 */         ((JDialog)c).getGlassPane().setVisible(false);
/*     */       }
/*     */ 
/* 406 */       this._resizable.endResizing(this._resizeCorner);
/*     */ 
/* 408 */       this._eventMouseScreenX = 0;
/* 409 */       this._eventMouseScreenY = 0;
/* 410 */       _startingBounds = null;
/* 411 */       this._resizeCorner = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void mouseMoved(MouseEvent e)
/*     */   {
/* 423 */     if ((e.getSource() instanceof Resizable.ResizeCorner)) {
/* 424 */       Resizable.ResizeCorner corner = (Resizable.ResizeCorner)e.getSource();
/* 425 */       boolean ltr = corner.getComponentOrientation().isLeftToRight();
/* 426 */       switch (corner.getCorner()) {
/*     */       case 16:
/* 428 */         corner.setCursor(Cursor.getPredefinedCursor(ltr ? 5 : 4));
/* 429 */         return;
/*     */       case 4:
/* 431 */         corner.setCursor(Cursor.getPredefinedCursor(ltr ? 7 : 6));
/* 432 */         return;
/*     */       case 64:
/* 434 */         corner.setCursor(Cursor.getPredefinedCursor(ltr ? 4 : 5));
/* 435 */         return;
/*     */       case 1:
/* 437 */         corner.setCursor(Cursor.getPredefinedCursor(ltr ? 6 : 7));
/* 438 */         return;
/*     */       }
/*     */     }
/* 441 */     else if (e.getSource() == this._resizable.getComponent()) {
/* 442 */       Insets i = this._resizable.getResizeInsets();
/* 443 */       boolean ltr = this._resizable.getComponent().getComponentOrientation().isLeftToRight();
/* 444 */       if (e.getX() <= i.left) {
/* 445 */         if ((isResizable(1)) && (i.top > 0) && (e.getY() < this._resizable.getResizeCornerSize() + i.top))
/* 446 */           this._resizable.getComponent().setCursor(Cursor.getPredefinedCursor(ltr ? 6 : 7));
/* 447 */         else if ((isResizable(64)) && (i.bottom > 0) && (e.getY() > this._resizable.getComponent().getHeight() - this._resizable.getResizeCornerSize() - i.bottom))
/* 448 */           this._resizable.getComponent().setCursor(Cursor.getPredefinedCursor(ltr ? 4 : 5));
/* 449 */         else if (isResizable(128))
/* 450 */           this._resizable.getComponent().setCursor(Cursor.getPredefinedCursor(ltr ? 10 : 11));
/*     */         else
/* 452 */           this._resizable.getComponent().setCursor(Cursor.getDefaultCursor());
/*     */       }
/* 454 */       else if (e.getX() >= this._resizable.getComponent().getWidth() - i.right) {
/* 455 */         if ((isResizable(4)) && (i.top > 0) && (e.getY() < this._resizable.getResizeCornerSize() + i.top))
/* 456 */           this._resizable.getComponent().setCursor(Cursor.getPredefinedCursor(ltr ? 7 : 6));
/* 457 */         else if ((isResizable(64)) && (i.bottom > 0) && (e.getY() > this._resizable.getComponent().getHeight() - this._resizable.getResizeCornerSize() - i.bottom))
/* 458 */           this._resizable.getComponent().setCursor(Cursor.getPredefinedCursor(ltr ? 5 : 4));
/* 459 */         else if (isResizable(8))
/* 460 */           this._resizable.getComponent().setCursor(Cursor.getPredefinedCursor(ltr ? 11 : 10));
/*     */         else
/* 462 */           this._resizable.getComponent().setCursor(Cursor.getDefaultCursor());
/*     */       }
/* 464 */       else if (e.getY() <= i.top) {
/* 465 */         if ((isResizable(1)) && (i.left > 0) && (e.getX() < this._resizable.getResizeCornerSize() + i.left))
/* 466 */           this._resizable.getComponent().setCursor(Cursor.getPredefinedCursor(ltr ? 6 : 7));
/* 467 */         else if ((isResizable(4)) && (i.right > 0) && (e.getX() > this._resizable.getComponent().getWidth() - this._resizable.getResizeCornerSize() - i.right))
/* 468 */           this._resizable.getComponent().setCursor(Cursor.getPredefinedCursor(ltr ? 7 : 6));
/* 469 */         else if (isResizable(2))
/* 470 */           this._resizable.getComponent().setCursor(Cursor.getPredefinedCursor(8));
/*     */         else
/* 472 */           this._resizable.getComponent().setCursor(Cursor.getDefaultCursor());
/*     */       }
/* 474 */       else if (e.getY() >= this._resizable.getComponent().getHeight() - i.bottom) {
/* 475 */         if ((isResizable(64)) && (i.left > 0) && (e.getX() < this._resizable.getResizeCornerSize() + i.left))
/* 476 */           this._resizable.getComponent().setCursor(Cursor.getPredefinedCursor(ltr ? 4 : 5));
/* 477 */         else if ((isResizable(16)) && (i.right > 0) && (e.getX() > this._resizable.getComponent().getWidth() - this._resizable.getResizeCornerSize() - i.right))
/* 478 */           this._resizable.getComponent().setCursor(Cursor.getPredefinedCursor(ltr ? 5 : 4));
/* 479 */         else if (isResizable(32))
/* 480 */           this._resizable.getComponent().setCursor(Cursor.getPredefinedCursor(9));
/*     */         else
/* 482 */           this._resizable.getComponent().setCursor(Cursor.getDefaultCursor());
/*     */       }
/*     */       else
/* 485 */         this._resizable.getComponent().setCursor(Cursor.getDefaultCursor());
/* 486 */       return;
/*     */     }
/*     */ 
/* 489 */     this._resizable.getComponent().setCursor(Cursor.getDefaultCursor());
/*     */   }
/*     */ 
/*     */   public void mouseExited(MouseEvent e)
/*     */   {
/* 495 */     if ((e.getSource() instanceof Resizable.ResizeCorner)) {
/* 496 */       Resizable.ResizeCorner corner = (Resizable.ResizeCorner)e.getSource();
/* 497 */       corner.setCursor(Cursor.getDefaultCursor());
/*     */     }
/*     */     else {
/* 500 */       this._resizable.getComponent().setCursor(Cursor.getDefaultCursor());
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.ResizableMouseInputAdapter
 * JD-Core Version:    0.6.0
 */
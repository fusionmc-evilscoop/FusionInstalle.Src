/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.border.AbstractBorder;
/*     */ import javax.swing.border.Border;
/*     */ 
/*     */ public class JideTitledBorder extends AbstractBorder
/*     */ {
/*     */   protected String title;
/*     */   protected Border border;
/*     */   protected int titlePosition;
/*     */   protected int titleJustification;
/*     */   protected Font titleFont;
/*     */   protected Color titleColor;
/*  31 */   private Point textLoc = new Point();
/*     */   public static final int DEFAULT_POSITION = 0;
/*     */   public static final int ABOVE_TOP = 1;
/*     */   public static final int TOP = 2;
/*     */   public static final int BELOW_TOP = 3;
/*     */   public static final int ABOVE_BOTTOM = 4;
/*     */   public static final int BOTTOM = 5;
/*     */   public static final int BELOW_BOTTOM = 6;
/*     */   public static final int DEFAULT_JUSTIFICATION = 0;
/*     */   public static final int LEFT = 1;
/*     */   public static final int CENTER = 2;
/*     */   public static final int RIGHT = 3;
/*     */   public static final int LEADING = 4;
/*     */   public static final int TRAILING = 5;
/*     */   protected static final int EDGE_SPACING = 2;
/*     */   protected static final int TITLE_MARGIN = 5;
/*     */   protected static final int TEXT_SPACING = 2;
/*     */   protected static final int TEXT_INSET_H = 0;
/*     */ 
/*     */   public JideTitledBorder(String title)
/*     */   {
/* 106 */     this(null, title, 4, 2, null, null);
/*     */   }
/*     */ 
/*     */   public JideTitledBorder(Border border)
/*     */   {
/* 115 */     this(border, "", 4, 2, null, null);
/*     */   }
/*     */ 
/*     */   public JideTitledBorder(Border border, String title)
/*     */   {
/* 125 */     this(border, title, 4, 2, null, null);
/*     */   }
/*     */ 
/*     */   public JideTitledBorder(Border border, String title, int titleJustification, int titlePosition)
/*     */   {
/* 141 */     this(border, title, titleJustification, titlePosition, null, null);
/*     */   }
/*     */ 
/*     */   public JideTitledBorder(Border border, String title, int titleJustification, int titlePosition, Font titleFont)
/*     */   {
/* 160 */     this(border, title, titleJustification, titlePosition, titleFont, null);
/*     */   }
/*     */ 
/*     */   public JideTitledBorder(Border border, String title, int titleJustification, int titlePosition, Font titleFont, Color titleColor)
/*     */   {
/* 181 */     this.title = title;
/* 182 */     this.border = border;
/* 183 */     this.titleFont = titleFont;
/* 184 */     this.titleColor = titleColor;
/*     */ 
/* 186 */     setTitleJustification(titleJustification);
/* 187 */     setTitlePosition(titlePosition);
/*     */   }
/*     */ 
/*     */   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
/*     */   {
/* 203 */     Border border = getBorder();
/*     */ 
/* 205 */     if ((getTitle() == null) || (getTitle().equals(""))) {
/* 206 */       if (border != null) {
/* 207 */         border.paintBorder(c, g, x, y, width, height);
/*     */       }
/* 209 */       return;
/*     */     }
/*     */ 
/* 212 */     Rectangle grooveRect = new Rectangle(x + 2, y + 2, width - 4, height - 4);
/*     */ 
/* 215 */     Font font = g.getFont();
/* 216 */     Color color = g.getColor();
/*     */ 
/* 218 */     g.setFont(getFont(c));
/*     */ 
/* 220 */     FontMetrics fm = g.getFontMetrics();
/* 221 */     int fontHeight = fm.getHeight();
/* 222 */     int descent = fm.getDescent();
/* 223 */     int ascent = fm.getAscent();
/*     */ 
/* 225 */     int stringWidth = fm.stringWidth(getTitle());
/*     */     Insets insets;
/*     */     Insets insets;
/* 228 */     if (border != null) {
/* 229 */       insets = border.getBorderInsets(c);
/*     */     }
/*     */     else {
/* 232 */       insets = new Insets(0, 0, 0, 0);
/*     */     }
/*     */ 
/* 235 */     int titlePos = getTitlePosition();
/*     */     int diff;
/* 236 */     switch (titlePos) {
/*     */     case 1:
/* 238 */       diff = ascent + descent + (Math.max(2, 4) - 2);
/*     */ 
/* 240 */       grooveRect.y += diff;
/* 241 */       grooveRect.height -= diff;
/* 242 */       this.textLoc.y = (grooveRect.y - (descent + 2));
/* 243 */       break;
/*     */     case 0:
/*     */     case 2:
/* 246 */       diff = Math.max(0, (ascent >> 1) + 2 - 2);
/* 247 */       grooveRect.y += diff;
/* 248 */       grooveRect.height -= diff;
/* 249 */       this.textLoc.y = (grooveRect.y - descent + (insets.top + ascent + descent >> 1));
/*     */ 
/* 251 */       break;
/*     */     case 3:
/* 253 */       this.textLoc.y = (grooveRect.y + insets.top + ascent + 2);
/* 254 */       break;
/*     */     case 4:
/* 256 */       this.textLoc.y = (grooveRect.y + grooveRect.height - (insets.bottom + descent + 2));
/*     */ 
/* 258 */       break;
/*     */     case 5:
/* 260 */       grooveRect.height -= (fontHeight >> 1);
/* 261 */       this.textLoc.y = (grooveRect.y + grooveRect.height - descent + (ascent + descent - insets.bottom >> 1));
/*     */ 
/* 263 */       break;
/*     */     case 6:
/* 265 */       grooveRect.height -= fontHeight;
/* 266 */       this.textLoc.y = (grooveRect.y + grooveRect.height + ascent + 2);
/*     */     }
/*     */ 
/* 271 */     int justification = getTitleJustification();
/* 272 */     if (c.getComponentOrientation().isLeftToRight()) {
/* 273 */       if ((justification == 4) || (justification == 0))
/*     */       {
/* 275 */         justification = 1;
/*     */       }
/* 277 */       else if (justification == 5) {
/* 278 */         justification = 3;
/*     */       }
/*     */ 
/*     */     }
/* 282 */     else if ((justification == 4) || (justification == 0))
/*     */     {
/* 284 */       justification = 3;
/*     */     }
/* 286 */     else if (justification == 5) {
/* 287 */       justification = 1;
/*     */     }
/*     */ 
/* 291 */     switch (justification) {
/*     */     case 1:
/* 293 */       this.textLoc.x = (grooveRect.x + 0 + insets.left);
/* 294 */       break;
/*     */     case 3:
/* 296 */       this.textLoc.x = (grooveRect.x + grooveRect.width - (stringWidth + 0 + insets.right));
/*     */ 
/* 298 */       break;
/*     */     case 2:
/* 300 */       this.textLoc.x = (grooveRect.x + (grooveRect.width - stringWidth >> 1));
/*     */     }
/*     */ 
/* 310 */     if (border != null) {
/* 311 */       if (((titlePos != 2) && (titlePos != 0)) || ((grooveRect.y > this.textLoc.y - ascent) || ((titlePos == 5) && (grooveRect.y + grooveRect.height < this.textLoc.y + descent))))
/*     */       {
/* 314 */         Rectangle clipRect = new Rectangle();
/*     */ 
/* 317 */         Rectangle saveClip = g.getClipBounds();
/*     */ 
/* 320 */         clipRect.setBounds(saveClip);
/* 321 */         if (computeIntersection(clipRect, x, y, this.textLoc.x - 5 - x, height)) {
/* 322 */           g.setClip(clipRect);
/* 323 */           border.paintBorder(c, g, grooveRect.x, grooveRect.y, grooveRect.width, grooveRect.height);
/*     */         }
/*     */ 
/* 328 */         clipRect.setBounds(saveClip);
/* 329 */         if (computeIntersection(clipRect, this.textLoc.x + stringWidth + 5, y, x + width - (this.textLoc.x + stringWidth + 5), height))
/*     */         {
/* 331 */           g.setClip(clipRect);
/* 332 */           border.paintBorder(c, g, grooveRect.x, grooveRect.y, grooveRect.width, grooveRect.height);
/*     */         }
/*     */ 
/* 336 */         if ((titlePos == 2) || (titlePos == 0))
/*     */         {
/* 338 */           clipRect.setBounds(saveClip);
/* 339 */           if (computeIntersection(clipRect, this.textLoc.x - 5, this.textLoc.y + descent, stringWidth + 10, y + height - this.textLoc.y - descent))
/*     */           {
/* 341 */             g.setClip(clipRect);
/* 342 */             border.paintBorder(c, g, grooveRect.x, grooveRect.y, grooveRect.width, grooveRect.height);
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 349 */           clipRect.setBounds(saveClip);
/* 350 */           if (computeIntersection(clipRect, this.textLoc.x - 5, y, stringWidth + 10, this.textLoc.y - ascent - y))
/*     */           {
/* 352 */             g.setClip(clipRect);
/* 353 */             border.paintBorder(c, g, grooveRect.x, grooveRect.y, grooveRect.width, grooveRect.height);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 359 */         g.setClip(saveClip);
/*     */       }
/*     */       else
/*     */       {
/* 363 */         border.paintBorder(c, g, grooveRect.x, grooveRect.y, grooveRect.width, grooveRect.height);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 368 */     g.setColor(getTitleColor());
/* 369 */     JideSwingUtilities.drawString((JComponent)c, g, getTitle(), this.textLoc.x, this.textLoc.y);
/*     */ 
/* 371 */     g.setFont(font);
/* 372 */     g.setColor(color);
/*     */   }
/*     */ 
/*     */   public Insets getBorderInsets(Component c)
/*     */   {
/* 382 */     return getBorderInsets(c, new Insets(0, 0, 0, 0));
/*     */   }
/*     */ 
/*     */   public Insets getBorderInsets(Component c, Insets insets)
/*     */   {
/* 394 */     int descent = 0;
/* 395 */     int ascent = 16;
/* 396 */     int height = 16;
/*     */ 
/* 398 */     Border border = getBorder();
/* 399 */     if (border != null) {
/* 400 */       if ((border instanceof AbstractBorder)) {
/* 401 */         ((AbstractBorder)border).getBorderInsets(c, insets);
/*     */       }
/*     */       else
/*     */       {
/* 406 */         Insets i = border.getBorderInsets(c);
/* 407 */         insets.top = i.top;
/* 408 */         insets.right = i.right;
/* 409 */         insets.bottom = i.bottom;
/* 410 */         insets.left = i.left;
/*     */       }
/*     */     }
/*     */     else {
/* 414 */       insets.left = (insets.top = insets.right = insets.bottom = 0);
/*     */     }
/*     */ 
/* 417 */     insets.left += 2 + (insets.left > 0 ? 2 : 0);
/* 418 */     insets.right += 2 + (insets.right > 0 ? 2 : 0);
/* 419 */     insets.top += 2 + (insets.top > 0 ? 2 : 0);
/* 420 */     insets.bottom += 2 + (insets.bottom > 0 ? 2 : 0);
/*     */ 
/* 422 */     if ((c == null) || (getTitle() == null) || (getTitle().equals(""))) {
/* 423 */       return insets;
/*     */     }
/*     */ 
/* 426 */     Font font = getFont(c);
/*     */ 
/* 428 */     FontMetrics fm = c.getFontMetrics(font);
/*     */ 
/* 430 */     if (fm != null) {
/* 431 */       descent = fm.getDescent();
/* 432 */       ascent = fm.getAscent();
/* 433 */       height = fm.getHeight();
/*     */     }
/*     */ 
/* 436 */     switch (getTitlePosition()) {
/*     */     case 1:
/* 438 */       insets.top += ascent + descent + (Math.max(2, 4) - 2);
/*     */ 
/* 441 */       break;
/*     */     case 0:
/*     */     case 2:
/* 444 */       insets.top += ascent + descent;
/* 445 */       break;
/*     */     case 3:
/* 447 */       insets.top += ascent + descent + 2;
/* 448 */       break;
/*     */     case 4:
/* 450 */       insets.bottom += ascent + descent + 2;
/* 451 */       break;
/*     */     case 5:
/* 453 */       insets.bottom += ascent + descent;
/* 454 */       break;
/*     */     case 6:
/* 456 */       insets.bottom += height;
/*     */     }
/*     */ 
/* 459 */     return insets;
/*     */   }
/*     */ 
/*     */   public boolean isBorderOpaque()
/*     */   {
/* 467 */     return false;
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 474 */     return this.title;
/*     */   }
/*     */ 
/*     */   public Border getBorder()
/*     */   {
/* 481 */     Border b = this.border;
/* 482 */     if (b == null)
/* 483 */       b = UIDefaultsLookup.getBorder("TitledBorder.border");
/* 484 */     return b;
/*     */   }
/*     */ 
/*     */   public int getTitlePosition()
/*     */   {
/* 491 */     return this.titlePosition;
/*     */   }
/*     */ 
/*     */   public int getTitleJustification()
/*     */   {
/* 498 */     return this.titleJustification;
/*     */   }
/*     */ 
/*     */   public Font getTitleFont()
/*     */   {
/* 505 */     Font f = this.titleFont;
/* 506 */     if (f == null)
/* 507 */       f = UIDefaultsLookup.getFont("TitledBorder.font");
/* 508 */     return f;
/*     */   }
/*     */ 
/*     */   public Color getTitleColor()
/*     */   {
/* 515 */     Color c = this.titleColor;
/* 516 */     if (c == null)
/* 517 */       c = UIDefaultsLookup.getColor("TitledBorder.titleColor");
/* 518 */     return c;
/*     */   }
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/* 527 */     this.title = title;
/*     */   }
/*     */ 
/*     */   public void setBorder(Border border)
/*     */   {
/* 536 */     this.border = border;
/*     */   }
/*     */ 
/*     */   public void setTitlePosition(int titlePosition)
/*     */   {
/* 545 */     switch (titlePosition) {
/*     */     case 0:
/*     */     case 1:
/*     */     case 2:
/*     */     case 3:
/*     */     case 4:
/*     */     case 5:
/*     */     case 6:
/* 553 */       this.titlePosition = titlePosition;
/* 554 */       break;
/*     */     default:
/* 556 */       throw new IllegalArgumentException(titlePosition + " is not a valid title position.");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setTitleJustification(int titleJustification)
/*     */   {
/* 567 */     switch (titleJustification) {
/*     */     case 0:
/*     */     case 1:
/*     */     case 2:
/*     */     case 3:
/*     */     case 4:
/*     */     case 5:
/* 574 */       this.titleJustification = titleJustification;
/* 575 */       break;
/*     */     default:
/* 577 */       throw new IllegalArgumentException(titleJustification + " is not a valid title justification.");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setTitleFont(Font titleFont)
/*     */   {
/* 588 */     this.titleFont = titleFont;
/*     */   }
/*     */ 
/*     */   public void setTitleColor(Color titleColor)
/*     */   {
/* 597 */     this.titleColor = titleColor;
/*     */   }
/*     */ 
/*     */   public Dimension getMinimumSize(Component c)
/*     */   {
/* 607 */     Insets insets = getBorderInsets(c);
/* 608 */     Dimension minSize = new Dimension(insets.right + insets.left, insets.top + insets.bottom);
/*     */ 
/* 610 */     Font font = getFont(c);
/* 611 */     FontMetrics fm = c.getFontMetrics(font);
/* 612 */     switch (this.titlePosition) {
/*     */     case 1:
/*     */     case 6:
/* 615 */       minSize.width = Math.max(fm.stringWidth(getTitle()), minSize.width);
/* 616 */       break;
/*     */     case 0:
/*     */     case 2:
/*     */     case 3:
/*     */     case 4:
/*     */     case 5:
/*     */     default:
/* 623 */       minSize.width += fm.stringWidth(getTitle());
/*     */     }
/* 625 */     return minSize;
/*     */   }
/*     */ 
/*     */   protected Font getFont(Component c)
/*     */   {
/*     */     Font font;
/* 630 */     if ((font = getTitleFont()) != null) {
/* 631 */       return font;
/*     */     }
/* 633 */     if ((c != null) && ((font = c.getFont()) != null)) {
/* 634 */       return font;
/*     */     }
/* 636 */     return new Font("Dialog", 0, 12);
/*     */   }
/*     */ 
/*     */   private static boolean computeIntersection(Rectangle dest, int rx, int ry, int rw, int rh)
/*     */   {
/* 641 */     int x1 = Math.max(rx, dest.x);
/* 642 */     int x2 = Math.min(rx + rw, dest.x + dest.width);
/* 643 */     int y1 = Math.max(ry, dest.y);
/* 644 */     int y2 = Math.min(ry + rh, dest.y + dest.height);
/* 645 */     dest.x = x1;
/* 646 */     dest.y = y1;
/* 647 */     dest.width = (x2 - x1);
/* 648 */     dest.height = (y2 - y1);
/*     */ 
/* 651 */     return (dest.width > 0) && (dest.height > 0);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.JideTitledBorder
 * JD-Core Version:    0.6.0
 */
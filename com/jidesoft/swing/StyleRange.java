/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Stroke;
/*     */ 
/*     */ public class StyleRange
/*     */ {
/*     */   public static final int STYLE_STRIKE_THROUGH = 1;
/*     */   public static final int STYLE_DOUBLE_STRIKE_THROUGH = 2;
/*     */   public static final int STYLE_WAVED = 4;
/*     */   public static final int STYLE_UNDERLINED = 8;
/*     */   public static final int STYLE_DOTTED = 16;
/*     */   public static final int STYLE_SUPERSCRIPT = 32;
/*     */   public static final int STYLE_SUBSCRIPT = 64;
/*     */   private final int _fontStyle;
/*     */   private final Color _fontColor;
/*     */   private final Color _backgroundColor;
/*     */   private final Color _lineColor;
/*     */   private final Stroke _lineStroke;
/*     */   private final int _additionalStyle;
/*     */   private final int _start;
/*     */   private final int _length;
/*  41 */   private float _fontShrinkRatio = 1.5F;
/*     */ 
/*     */   public StyleRange(int fontStyle)
/*     */   {
/*  49 */     this(0, -1, fontStyle, null, 0, null, null);
/*     */   }
/*     */ 
/*     */   public StyleRange(Color fontColor)
/*     */   {
/*  58 */     this(0, -1, -1, fontColor, 0, null, null);
/*     */   }
/*     */ 
/*     */   public StyleRange(int fontStyle, Color fontColor)
/*     */   {
/*  68 */     this(0, -1, fontStyle, fontColor, 0, null, null);
/*     */   }
/*     */ 
/*     */   public StyleRange(int fontStyle, int additionalStyle)
/*     */   {
/*  80 */     this(0, -1, fontStyle, null, additionalStyle, null, null);
/*     */   }
/*     */ 
/*     */   public StyleRange(int fontStyle, int additionalStyle, float fontShrinkRatio)
/*     */   {
/*  93 */     this(0, -1, fontStyle, null, additionalStyle, null, null, fontShrinkRatio);
/*     */   }
/*     */ 
/*     */   public StyleRange(int start, int length, int fontStyle)
/*     */   {
/* 104 */     this(start, length, fontStyle, null, 0, null, null);
/*     */   }
/*     */ 
/*     */   public StyleRange(int start, int length, int fontStyle, Color fontColor)
/*     */   {
/* 116 */     this(start, length, fontStyle, fontColor, 0, null, null);
/*     */   }
/*     */ 
/*     */   public StyleRange(int start, int length, Color fontColor)
/*     */   {
/* 127 */     this(start, length, 0, fontColor, 0, null, null);
/*     */   }
/*     */ 
/*     */   public StyleRange(int start, int length, int fontStyle, int additionalStyle)
/*     */   {
/* 141 */     this(start, length, fontStyle, null, additionalStyle, null, null);
/*     */   }
/*     */ 
/*     */   public StyleRange(int start, int length, int fontStyle, int additionalStyle, float fontShrinkRatio)
/*     */   {
/* 156 */     this(start, length, fontStyle, null, additionalStyle, null, null, fontShrinkRatio);
/*     */   }
/*     */ 
/*     */   public StyleRange(int fontStyle, Color fontColor, int additionalStyle, Color lineColor)
/*     */   {
/* 169 */     this(0, -1, fontStyle, fontColor, additionalStyle, lineColor, null);
/*     */   }
/*     */ 
/*     */   public StyleRange(int fontStyle, Color fontColor, Color backgroundColor, int additionalStyle, Color lineColor)
/*     */   {
/* 183 */     this(0, -1, fontStyle, fontColor, backgroundColor, additionalStyle, lineColor, null);
/*     */   }
/*     */ 
/*     */   public StyleRange(int start, int length, int fontStyle, Color fontColor, int additionalStyle)
/*     */   {
/* 198 */     this(start, length, fontStyle, fontColor, additionalStyle, null, null);
/*     */   }
/*     */ 
/*     */   public StyleRange(int start, int length, int fontStyle, Color fontColor, Color backgroundColor, int additionalStyle)
/*     */   {
/* 214 */     this(start, length, fontStyle, fontColor, backgroundColor, additionalStyle, null, null);
/*     */   }
/*     */ 
/*     */   public StyleRange(int fontStyle, Color fontColor, int additionalStyle, Color lineColor, Stroke lineStroke)
/*     */   {
/* 228 */     this(0, -1, fontStyle, fontColor, additionalStyle, lineColor, lineStroke);
/*     */   }
/*     */ 
/*     */   public StyleRange(int start, int length, int fontStyle, Color fontColor, int additionalStyle, Color lineColor)
/*     */   {
/* 244 */     this(start, length, fontStyle, fontColor, additionalStyle, lineColor, null);
/*     */   }
/*     */ 
/*     */   public StyleRange(int start, int length, int fontStyle, Color fontColor, Color backgroundColor, int additionalStyle, Color lineColor)
/*     */   {
/* 261 */     this(start, length, fontStyle, fontColor, backgroundColor, additionalStyle, lineColor, null);
/*     */   }
/*     */ 
/*     */   public StyleRange(int start, int length, int fontStyle, Color fontColor, int additionalStyle, Color lineColor, Stroke lineStroke)
/*     */   {
/* 279 */     this(start, length, fontStyle, fontColor, additionalStyle, lineColor, lineStroke, 1.5F);
/*     */   }
/*     */ 
/*     */   public StyleRange(int start, int length, int fontStyle, Color fontColor, Color backgroundColor, int additionalStyle, Color lineColor, Stroke lineStroke)
/*     */   {
/* 298 */     this(start, length, fontStyle, fontColor, backgroundColor, additionalStyle, lineColor, lineStroke, 1.5F);
/*     */   }
/*     */ 
/*     */   public StyleRange(int start, int length, int fontStyle, Color fontColor, int additionalStyle, Color lineColor, Stroke lineStroke, float fontShrinkRatio)
/*     */   {
/* 317 */     this(start, length, fontStyle, fontColor, null, additionalStyle, lineColor, lineStroke, fontShrinkRatio);
/*     */   }
/*     */ 
/*     */   public StyleRange(int start, int length, int fontStyle, Color fontColor, Color backgroundColor, int additionalStyle, Color lineColor, Stroke lineStroke, float fontShrinkRatio)
/*     */   {
/* 337 */     if (length == 0) {
/* 338 */       throw new IllegalArgumentException("The length of StyleRange cannot be 0.");
/*     */     }
/*     */ 
/* 341 */     this._start = start;
/* 342 */     this._length = length;
/* 343 */     this._fontColor = fontColor;
/* 344 */     this._fontStyle = fontStyle;
/* 345 */     this._backgroundColor = backgroundColor;
/* 346 */     this._lineColor = lineColor;
/* 347 */     this._lineStroke = lineStroke;
/* 348 */     this._additionalStyle = additionalStyle;
/* 349 */     this._fontShrinkRatio = fontShrinkRatio;
/*     */   }
/*     */ 
/*     */   public int getStart()
/*     */   {
/* 358 */     return this._start;
/*     */   }
/*     */ 
/*     */   public int getLength()
/*     */   {
/* 367 */     return this._length;
/*     */   }
/*     */ 
/*     */   public int getFontStyle()
/*     */   {
/* 376 */     return this._fontStyle;
/*     */   }
/*     */ 
/*     */   public Color getFontColor()
/*     */   {
/* 385 */     return this._fontColor;
/*     */   }
/*     */ 
/*     */   public Color getBackgroundColor()
/*     */   {
/* 394 */     return this._backgroundColor;
/*     */   }
/*     */ 
/*     */   public int getAdditionalStyle()
/*     */   {
/* 407 */     return this._additionalStyle;
/*     */   }
/*     */ 
/*     */   public Color getLineColor()
/*     */   {
/* 416 */     return this._lineColor;
/*     */   }
/*     */ 
/*     */   public Stroke getLineStroke()
/*     */   {
/* 425 */     return this._lineStroke;
/*     */   }
/*     */ 
/*     */   public boolean isStrikethrough()
/*     */   {
/* 434 */     return (this._additionalStyle & 0x1) != 0;
/*     */   }
/*     */ 
/*     */   public boolean isDoublestrikethrough()
/*     */   {
/* 443 */     return (this._additionalStyle & 0x2) != 0;
/*     */   }
/*     */ 
/*     */   public boolean isWaved()
/*     */   {
/* 452 */     return (this._additionalStyle & 0x4) != 0;
/*     */   }
/*     */ 
/*     */   public boolean isUnderlined()
/*     */   {
/* 461 */     return (this._additionalStyle & 0x8) != 0;
/*     */   }
/*     */ 
/*     */   public boolean isDotted()
/*     */   {
/* 470 */     return (this._additionalStyle & 0x10) != 0;
/*     */   }
/*     */ 
/*     */   public boolean isSuperscript()
/*     */   {
/* 479 */     return (this._additionalStyle & 0x20) != 0;
/*     */   }
/*     */ 
/*     */   public boolean isSubscript()
/*     */   {
/* 488 */     return (this._additionalStyle & 0x40) != 0;
/*     */   }
/*     */ 
/*     */   public float getFontShrinkRatio()
/*     */   {
/* 497 */     return this._fontShrinkRatio;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.StyleRange
 * JD-Core Version:    0.6.0
 */
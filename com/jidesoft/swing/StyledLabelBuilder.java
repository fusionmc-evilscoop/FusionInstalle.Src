/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Stroke;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ public class StyledLabelBuilder
/*     */ {
/*     */   private StringBuffer buffer;
/*     */   private List ranges;
/*     */   private int start;
/*     */   private Map styles;
/* 523 */   private static int[] colorShorthandTable = { 0, 17, 34, 51, 68, 85, 102, 119, 136, 153, 170, 187, 204, 221, 238, 255 };
/*     */ 
/* 532 */   private static Map colorNamesMap = new TreeMap();
/*     */ 
/*     */   public StyledLabelBuilder()
/*     */   {
/* 107 */     this.buffer = new StringBuffer();
/* 108 */     this.ranges = new ArrayList();
/* 109 */     this.styles = new HashMap();
/* 110 */     this.start = 0;
/*     */   }
/*     */ 
/*     */   public void clear() {
/* 114 */     this.buffer.delete(0, this.buffer.length());
/* 115 */     this.ranges.clear();
/* 116 */     this.start = 0;
/*     */   }
/*     */ 
/*     */   public StyledLabelBuilder register(String text, Color fontColor) {
/* 120 */     this.styles.put(text, new StyleRange(fontColor));
/* 121 */     return this;
/*     */   }
/*     */ 
/*     */   public StyledLabelBuilder register(String text, int fontStyle) {
/* 125 */     this.styles.put(text, new StyleRange(fontStyle));
/* 126 */     return this;
/*     */   }
/*     */ 
/*     */   public StyledLabelBuilder register(String text, int fontStyle, Color fontColor) {
/* 130 */     this.styles.put(text, new StyleRange(fontStyle, fontColor));
/* 131 */     return this;
/*     */   }
/*     */ 
/*     */   public StyledLabelBuilder register(String text, int fontStyle, Color fontColor, int additionalStyle) {
/* 135 */     this.styles.put(text, new StyleRange(this.start, text.length(), fontStyle, fontColor, additionalStyle));
/* 136 */     return this;
/*     */   }
/*     */ 
/*     */   public StyledLabelBuilder register(String text, int fontStyle, Color fontColor, int additionalStyle, Color lineColor) {
/* 140 */     this.styles.put(text, new StyleRange(this.start, text.length(), fontStyle, fontColor, additionalStyle, lineColor));
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */   public StyledLabelBuilder register(String text, int fontStyle, Color fontColor, int additionalStyle, Color lineColor, Stroke lineStroke) {
/* 145 */     this.styles.put(text, new StyleRange(this.start, text.length(), fontStyle, fontColor, additionalStyle, lineColor, lineStroke));
/* 146 */     return this;
/*     */   }
/*     */ 
/*     */   public StyledLabelBuilder register(String text, int fontStyle, Color fontColor, int additionalStyle, Color lineColor, Stroke lineStroke, float fontShrinkRatio) {
/* 150 */     this.styles.put(text, new StyleRange(this.start, text.length(), fontStyle, fontColor, additionalStyle, lineColor, lineStroke, fontShrinkRatio));
/* 151 */     return this;
/*     */   }
/*     */ 
/*     */   public StyledLabelBuilder register(String text, int fontStyle, int additionalStyle) {
/* 155 */     this.styles.put(text, new StyleRange(this.start, text.length(), fontStyle, additionalStyle));
/* 156 */     return this;
/*     */   }
/*     */ 
/*     */   public StyledLabelBuilder register(String text, int fontStyle, int additionalStyle, float fontShrinkRatio) {
/* 160 */     this.styles.put(text, new StyleRange(this.start, text.length(), fontStyle, additionalStyle, fontShrinkRatio));
/* 161 */     return this;
/*     */   }
/*     */ 
/*     */   public StyledLabelBuilder register(String text, String format) {
/* 165 */     ParsedStyleResult result = parseStyleAnnotation(format.toCharArray(), 0, this);
/* 166 */     this.styles.put(text, new StyleRange(result.fontStyle, result.fontColor, result.backgroundColor, result.additionalStyle, result.lineColor));
/*     */ 
/* 168 */     return this;
/*     */   }
/*     */ 
/*     */   public StyledLabelBuilder add(String text)
/*     */   {
/* 174 */     this.buffer.append(text);
/* 175 */     this.start += text.length();
/* 176 */     return this;
/*     */   }
/*     */ 
/*     */   public StyledLabelBuilder add(String text, Color fontColor) {
/* 180 */     this.ranges.add(new StyleRange(this.start, text.length(), fontColor));
/* 181 */     return add(text);
/*     */   }
/*     */ 
/*     */   public StyledLabelBuilder add(String text, int fontStyle) {
/* 185 */     this.ranges.add(new StyleRange(this.start, text.length(), fontStyle));
/* 186 */     return add(text);
/*     */   }
/*     */ 
/*     */   public StyledLabelBuilder add(String text, int fontStyle, Color fontColor) {
/* 190 */     this.ranges.add(new StyleRange(this.start, text.length(), fontStyle, fontColor));
/* 191 */     return add(text);
/*     */   }
/*     */ 
/*     */   public StyledLabelBuilder add(String text, int fontStyle, Color fontColor, int additionalStyle) {
/* 195 */     this.ranges.add(new StyleRange(this.start, text.length(), fontStyle, fontColor, additionalStyle));
/* 196 */     return add(text);
/*     */   }
/*     */ 
/*     */   public StyledLabelBuilder add(String text, int fontStyle, Color fontColor, int additionalStyle, Color lineColor) {
/* 200 */     this.ranges.add(new StyleRange(this.start, text.length(), fontStyle, fontColor, additionalStyle, lineColor));
/* 201 */     return add(text);
/*     */   }
/*     */ 
/*     */   public StyledLabelBuilder add(String text, int fontStyle, Color fontColor, Color backgroundColor, int additionalStyle, Color lineColor) {
/* 205 */     this.ranges.add(new StyleRange(this.start, text.length(), fontStyle, fontColor, backgroundColor, additionalStyle, lineColor));
/* 206 */     return add(text);
/*     */   }
/*     */ 
/*     */   public StyledLabelBuilder add(String text, int fontStyle, Color fontColor, Color backgroundColor, int additionalStyle, Color lineColor, Stroke lineStroke) {
/* 210 */     this.ranges.add(new StyleRange(this.start, text.length(), fontStyle, fontColor, backgroundColor, additionalStyle, lineColor, lineStroke));
/* 211 */     return add(text);
/*     */   }
/*     */ 
/*     */   public StyledLabelBuilder add(String text, int fontStyle, Color fontColor, int additionalStyle, Color lineColor, Stroke lineStroke, float fontShrinkRatio) {
/* 215 */     this.ranges.add(new StyleRange(this.start, text.length(), fontStyle, fontColor, additionalStyle, lineColor, lineStroke, fontShrinkRatio));
/* 216 */     return add(text);
/*     */   }
/*     */ 
/*     */   public StyledLabelBuilder add(String text, String style) {
/* 220 */     StyleRange range = (StyleRange)this.styles.get(style);
/*     */ 
/* 222 */     if (range == null) {
/* 223 */       ParsedStyleResult result = parseStyleAnnotation(style.toCharArray(), 0, this);
/* 224 */       return add(text, result.fontStyle, result.fontColor, result.backgroundColor, result.additionalStyle, result.lineColor);
/*     */     }
/* 226 */     return add(text, range.getFontStyle(), range.getFontColor(), range.getAdditionalStyle(), range.getLineColor(), range.getLineStroke(), range.getFontShrinkRatio());
/*     */   }
/*     */ 
/*     */   public StyledLabelBuilder add(String text, int fontStyle, int additionalStyle) {
/* 230 */     this.ranges.add(new StyleRange(this.start, text.length(), fontStyle, additionalStyle));
/* 231 */     return add(text);
/*     */   }
/*     */ 
/*     */   public StyledLabelBuilder add(String text, int fontStyle, int additionalStyle, float fontShrinkRatio) {
/* 235 */     this.ranges.add(new StyleRange(this.start, text.length(), fontStyle, additionalStyle, fontShrinkRatio));
/* 236 */     return add(text);
/*     */   }
/*     */ 
/*     */   public StyledLabel configure(StyledLabel label, String style) {
/* 240 */     setStyledText(label, style, this);
/* 241 */     return label;
/*     */   }
/*     */ 
/*     */   public StyledLabel configure(StyledLabel label) {
/* 245 */     label.setText(this.buffer.toString());
/* 246 */     int size = this.ranges.size();
/* 247 */     for (int i = 0; i < size; i++) {
/* 248 */       label.addStyleRange((StyleRange)this.ranges.get(i));
/*     */     }
/* 250 */     return label;
/*     */   }
/*     */ 
/*     */   public StyledLabel createLabel() {
/* 254 */     return configure(new StyledLabel());
/*     */   }
/*     */ 
/*     */   public static StyledLabel createStyledLabel(String text)
/*     */   {
/* 259 */     StyledLabel label = new StyledLabel();
/* 260 */     setStyledText(label, text);
/* 261 */     return label;
/*     */   }
/*     */ 
/*     */   public static void setStyledText(StyledLabel label, String text)
/*     */   {
/* 272 */     setStyledText(label, text.toCharArray());
/*     */   }
/*     */ 
/*     */   private static void setStyledText(StyledLabel label, String text, StyledLabelBuilder builder) {
/* 276 */     setStyledText(label, text.toCharArray(), builder);
/*     */   }
/*     */ 
/*     */   public static void setStyledText(StyledLabel label, char[] text)
/*     */   {
/* 287 */     setStyledText(label, text, null);
/*     */   }
/*     */ 
/*     */   private static void setStyledText(StyledLabel label, char[] text, StyledLabelBuilder builder) {
/* 291 */     StringBuffer labelText = new StringBuffer(text.length);
/* 292 */     boolean escaped = false;
/* 293 */     label.clearStyleRanges();
/* 294 */     for (int i = 0; i < text.length; i++) {
/* 295 */       if (escaped) {
/* 296 */         labelText.append(text[i]);
/* 297 */         escaped = false;
/*     */       }
/*     */       else {
/* 300 */         switch (text[i]) {
/*     */         case '{':
/* 302 */           ParsedStyleResult result = parseStylePart(text, i + 1, builder);
/* 303 */           int realIndex = labelText.length();
/* 304 */           labelText.append(result.text);
/* 305 */           if (result.text.length() > 0) {
/* 306 */             label.addStyleRange(new StyleRange(realIndex, result.text.length(), result.fontStyle, result.fontColor, result.backgroundColor, result.additionalStyle, result.lineColor));
/*     */           }
/*     */ 
/* 311 */           i = result.endOffset;
/* 312 */           break;
/*     */         case '\\':
/* 314 */           escaped = true;
/* 315 */           break;
/*     */         default:
/* 317 */           labelText.append(text[i]);
/*     */         }
/*     */       }
/*     */     }
/* 321 */     label.setText(labelText.toString());
/*     */   }
/*     */ 
/*     */   public static String parseToVoidStyledTextConfusion(String originalString)
/*     */   {
/* 332 */     String destString = originalString.replaceAll("\\\\", "\\\\\\\\");
/* 333 */     destString = destString.replaceAll("\\{", "\\\\{");
/* 334 */     return destString;
/*     */   }
/*     */ 
/*     */   private static ParsedStyleResult parseStylePart(char[] text, int start, StyledLabelBuilder builder) {
/* 338 */     ParsedStyleResult result = new ParsedStyleResult(null);
/* 339 */     int i = start;
/*     */ 
/* 341 */     int findIndex = findNext(text, ':', i);
/* 342 */     result.text = createTrimmedString(text, i, findIndex - 1);
/* 343 */     return parseStyleAnnotation(text, findIndex + 1, builder, result);
/*     */   }
/*     */ 
/*     */   private static ParsedStyleResult parseStyleAnnotation(char[] text, int start, StyledLabelBuilder builder) {
/* 347 */     ParsedStyleResult result = new ParsedStyleResult(null);
/* 348 */     return parseStyleAnnotation(text, start, builder, result);
/*     */   }
/*     */ 
/*     */   private static ParsedStyleResult parseStyleAnnotation(char[] text, int findIndex, StyledLabelBuilder builder, ParsedStyleResult result) {
/* 352 */     int i = findIndex;
/* 353 */     char[] importantChars = { ',', '}' };
/* 354 */     boolean endOfTag = false;
/* 355 */     while ((i < text.length) && (!endOfTag)) {
/* 356 */       findIndex = findNextOf(text, importantChars, i);
/*     */ 
/* 358 */       if ((findIndex == -1) || (text[findIndex] == '}')) {
/* 359 */         endOfTag = true;
/*     */       }
/* 361 */       String style = createTrimmedString(text, i, findIndex == -1 ? text.length - 1 : findIndex - 1);
/*     */ 
/* 363 */       int colonIndex = style.indexOf(':');
/* 364 */       if (colonIndex != -1) {
/* 365 */         String color = style.substring(colonIndex + 1);
/*     */ 
/* 367 */         if (color.length() > 1) {
/* 368 */           if (color.charAt(0) == '(') {
/* 369 */             findIndex = findNext(text, ')', i + colonIndex + 1);
/* 370 */             style = createTrimmedString(text, i, findIndex + 1);
/* 371 */             color = style.substring(colonIndex + 1);
/*     */ 
/* 373 */             if (text[(findIndex + 1)] == '}') {
/* 374 */               endOfTag = true;
/*     */             }
/*     */ 
/* 377 */             findIndex++;
/*     */           }
/* 379 */           if (style.charAt(0) == 'f') {
/* 380 */             result.fontColor = toColor(color);
/*     */           }
/* 382 */           else if (style.charAt(0) == 'b') {
/* 383 */             result.backgroundColor = toColor(color);
/*     */           }
/*     */           else {
/* 386 */             result.lineColor = toColor(color);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/* 392 */       else if ((style.equals("plain")) || (style.equals("p"))) {
/* 393 */         result.fontStyle = 0;
/*     */       }
/* 396 */       else if ((style.equals("bold")) || (style.equals("b"))) {
/* 397 */         result.fontStyle = 1;
/*     */       }
/* 400 */       else if ((style.equals("italic")) || (style.equals("i"))) {
/* 401 */         result.fontStyle = 2;
/*     */       }
/* 404 */       else if ((style.equals("bolditalic")) || (style.equals("bi"))) {
/* 405 */         result.fontStyle = 3;
/*     */       }
/* 408 */       else if ((style.equals("strike")) || (style.equals("s"))) {
/* 409 */         result.additionalStyle |= 1;
/*     */       }
/* 412 */       else if ((style.equals("doublestrike")) || (style.equals("ds"))) {
/* 413 */         result.additionalStyle |= 2;
/*     */       }
/* 416 */       else if ((style.equals("waved")) || (style.equals("w"))) {
/* 417 */         result.additionalStyle |= 4;
/*     */       }
/* 420 */       else if ((style.equals("underlined")) || (style.equals("u"))) {
/* 421 */         result.additionalStyle |= 8;
/*     */       }
/* 424 */       else if ((style.equals("dotted")) || (style.equals("d"))) {
/* 425 */         result.additionalStyle |= 16;
/*     */       }
/* 428 */       else if ((style.equals("superscript")) || (style.equals("sp"))) {
/* 429 */         result.additionalStyle |= 32;
/*     */       }
/* 432 */       else if ((style.equals("subscipt")) || (style.equals("sb"))) {
/* 433 */         result.additionalStyle |= 64;
/*     */       }
/* 435 */       else if ((builder != null) && (builder.styles.containsKey(style))) {
/* 436 */         StyleRange range = (StyleRange)builder.styles.get(style);
/* 437 */         result.fontStyle = range.getFontStyle();
/* 438 */         result.fontColor = range.getFontColor();
/* 439 */         result.backgroundColor = range.getBackgroundColor();
/* 440 */         result.additionalStyle = range.getAdditionalStyle();
/* 441 */         result.lineColor = range.getLineColor();
/*     */       }
/* 443 */       else if (style.length() > 0) {
/* 444 */         System.err.println("Unknown style '" + style + "'");
/*     */       }
/*     */ 
/* 447 */       i = findIndex + 1;
/*     */     }
/* 449 */     result.endOffset = (i - 1);
/*     */ 
/* 451 */     return result;
/*     */   }
/*     */ 
/*     */   private static Color toColor(String str)
/*     */   {
/* 458 */     switch (str.charAt(0))
/*     */     {
/*     */     case '(':
/* 463 */       int red = nextColorInt(str, 1);
/*     */ 
/* 465 */       int index = str.indexOf(',');
/* 466 */       int green = nextColorInt(str, index + 1);
/*     */ 
/* 468 */       index = str.indexOf(',', index + 1);
/* 469 */       int blue = nextColorInt(str, index + 1);
/*     */ 
/* 471 */       return new Color(red, green, blue);
/*     */     case '#':
/* 474 */       if (str.length() == 4) {
/* 475 */         return new Color(getShorthandValue(str.charAt(1)), getShorthandValue(str.charAt(2)), getShorthandValue(str.charAt(3)));
/*     */       }
/*     */ 
/* 482 */       return new Color(Integer.parseInt(str.substring(1), 16));
/*     */     case '0':
/* 485 */       return new Color(Integer.parseInt(str.substring(2), 16));
/*     */     }
/* 487 */     return (Color)colorNamesMap.get(str);
/*     */   }
/*     */ 
/*     */   private static int nextColorInt(String str, int index)
/*     */   {
/* 493 */     while (index < str.length()) {
/* 494 */       char c = str.charAt(index);
/*     */ 
/* 496 */       if (('0' <= c) && (c <= '9'))
/*     */       {
/*     */         break;
/*     */       }
/* 500 */       index++;
/*     */     }
/*     */ 
/* 504 */     int colorLength = index;
/* 505 */     for (; colorLength < index + 3; colorLength++) {
/* 506 */       char c = str.charAt(colorLength);
/*     */ 
/* 508 */       if ((c < '0') || ('9' < c)) {
/*     */         break;
/*     */       }
/*     */     }
/* 512 */     return Integer.parseInt(str.substring(index, colorLength));
/*     */   }
/*     */ 
/*     */   private static int getShorthandValue(char c) {
/* 516 */     c = Character.toUpperCase(c);
/* 517 */     if (('A' <= c) && (c <= 'F')) {
/* 518 */       return colorShorthandTable[(c - 'A' + 10)];
/*     */     }
/* 520 */     return colorShorthandTable[(c - '0')];
/*     */   }
/*     */ 
/*     */   public static Map getColorNamesMap()
/*     */   {
/* 549 */     return colorNamesMap;
/*     */   }
/*     */ 
/*     */   private static String createTrimmedString(char[] text, int start, int end) {
/* 553 */     while (((text[start] == ' ') || (text[start] == '\t')) && (start < text.length)) start++;
/* 554 */     while (((text[end] == ' ') || (text[end] == '\t')) && (start < end)) end--;
/*     */ 
/* 556 */     if (end >= start) {
/* 557 */       StringBuffer buffer = new StringBuffer(end - start);
/* 558 */       boolean escaped = false;
/* 559 */       for (int i = start; i <= end; i++) {
/* 560 */         if ((text[i] == '\\') && (!escaped)) {
/* 561 */           escaped = true;
/*     */         }
/*     */         else {
/* 564 */           buffer.append(text[i]);
/* 565 */           if (escaped) {
/* 566 */             escaped = false;
/*     */           }
/*     */         }
/*     */       }
/* 570 */       return buffer.toString();
/*     */     }
/*     */ 
/* 573 */     return "";
/*     */   }
/*     */ 
/*     */   private static int findNextOf(char[] text, char[] chars, int start)
/*     */   {
/* 578 */     boolean escaped = false;
/* 579 */     for (int i = start; i < text.length; i++) {
/* 580 */       if (escaped) {
/* 581 */         escaped = false;
/*     */       }
/* 584 */       else if (text[i] == '\\') {
/* 585 */         escaped = true;
/*     */       }
/*     */       else {
/* 588 */         for (char c : chars) {
/* 589 */           if (text[i] == c) {
/* 590 */             return i;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 595 */     return -1;
/*     */   }
/*     */ 
/*     */   private static int findNext(char[] text, char c, int start) {
/* 599 */     boolean escaped = false;
/* 600 */     for (int i = start; i < text.length; i++) {
/* 601 */       if (escaped) {
/* 602 */         escaped = false;
/*     */       }
/* 605 */       else if (text[i] == '\\') {
/* 606 */         escaped = true;
/*     */       }
/* 608 */       else if (text[i] == c) {
/* 609 */         return i;
/*     */       }
/*     */     }
/* 612 */     return -1;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 533 */     colorNamesMap.put("white", new Color(16777215));
/* 534 */     colorNamesMap.put("lightGray", new Color(12632256));
/* 535 */     colorNamesMap.put("gray", new Color(8421504));
/* 536 */     colorNamesMap.put("darkGray", new Color(4210752));
/* 537 */     colorNamesMap.put("black", new Color(0));
/* 538 */     colorNamesMap.put("red", new Color(16711680));
/* 539 */     colorNamesMap.put("pink", new Color(16756655));
/* 540 */     colorNamesMap.put("orange", new Color(16762880));
/* 541 */     colorNamesMap.put("yellow", new Color(16776960));
/* 542 */     colorNamesMap.put("green", new Color(65280));
/* 543 */     colorNamesMap.put("magenta", new Color(16711935));
/* 544 */     colorNamesMap.put("cyan", new Color(65535));
/* 545 */     colorNamesMap.put("blue", new Color(255));
/*     */   }
/*     */ 
/*     */   private static class ParsedStyleResult
/*     */   {
/*     */     String text;
/*     */     int endOffset;
/* 618 */     int fontStyle = 0;
/* 619 */     Color fontColor = null; Color lineColor = null; Color backgroundColor = null;
/* 620 */     int additionalStyle = 0;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.StyledLabelBuilder
 * JD-Core Version:    0.6.0
 */
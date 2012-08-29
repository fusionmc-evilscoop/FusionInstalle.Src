/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.dialog.JideOptionPane;
/*     */ import com.jidesoft.utils.SecurityUtils;
/*     */ import com.jidesoft.utils.SystemInfo;
/*     */ import java.awt.AWTError;
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager2;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public class JideBoxLayout
/*     */   implements LayoutManager2, Serializable
/*     */ {
/*  29 */   private static final Logger LOGGER = Logger.getLogger(JideBoxLayout.class.getName());
/*     */ 
/*  34 */   private boolean doReset = true;
/*     */   protected int _axis;
/*     */   protected Container _target;
/*  41 */   private int _gap = 0;
/*     */   protected int[] _componentSizes;
/*     */   public static final String FIX = "fix";
/*     */   public static final String FLEXIBLE = "flexible";
/*     */   public static final String VARY = "vary";
/*  62 */   private final HashMap<Component, Object> _constraintMap = new HashMap();
/*     */   public static final int X_AXIS = 0;
/*     */   public static final int Y_AXIS = 1;
/*     */   public static final int LINE_AXIS = 2;
/*     */   public static final int PAGE_AXIS = 3;
/*  86 */   private boolean _resetWhenInvalidate = true;
/*  87 */   private boolean _alwaysLayout = true;
/*     */   private static final long serialVersionUID = -183922972679053590L;
/*     */ 
/*     */   public JideBoxLayout(Container target)
/*     */   {
/*  97 */     this(target, 0);
/*     */   }
/*     */ 
/*     */   public JideBoxLayout(Container target, int axis)
/*     */   {
/* 107 */     this(target, axis, 0);
/*     */   }
/*     */ 
/*     */   public JideBoxLayout(Container target, int axis, int gap)
/*     */   {
/* 119 */     if ((axis != 0) && (axis != 1) && (axis != 2) && (axis != 3))
/*     */     {
/* 121 */       throw new AWTError("Invalid axis");
/*     */     }
/* 123 */     this._axis = axis;
/* 124 */     this._target = target;
/* 125 */     this._gap = gap;
/*     */   }
/*     */ 
/*     */   public void layoutContainer(Container container)
/*     */   {
/* 134 */     synchronized (container.getTreeLock()) {
/* 135 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 136 */         LOGGER.fine(this + " layoutContainer started");
/*     */       }
/* 138 */       Dimension containerSize = container.getSize();
/* 139 */       if ((containerSize.height <= 0) || (containerSize.width <= 0)) {
/* 140 */         return;
/*     */       }
/*     */ 
/* 143 */       Insets insets = this._target.getInsets();
/*     */ 
/* 145 */       if (this.doReset) {
/* 146 */         this._componentSizes = new int[this._target.getComponentCount()];
/* 147 */         int availableSize = getAvailableSize(containerSize, insets);
/* 148 */         availableSize -= getGapSize();
/* 149 */         if (availableSize <= 0) {
/* 150 */           return;
/*     */         }
/* 152 */         boolean success = calculateComponentSizes(availableSize, 0, this._target.getComponentCount());
/* 153 */         if (!success) {
/* 154 */           if ((!isAlwaysLayout()) && ("false".equals(SecurityUtils.getProperty("JideBoxLayout.alwaysLayout", "false")))) {
/* 155 */             for (int i = 0; i < this._target.getComponentCount(); i++) {
/* 156 */               Component comp = this._target.getComponent(i);
/* 157 */               setComponentToSize(comp, 0, 0, insets, containerSize);
/*     */             }
/* 159 */             redoLayout(container);
/*     */           }
/* 161 */           return;
/*     */         }
/* 163 */         this.doReset = false;
/* 164 */         if (this._componentSizes.length == 0)
/* 165 */           container.repaint();
/*     */       }
/*     */       else
/*     */       {
/* 169 */         int totalSize = 0;
/* 170 */         for (int componentSize : this._componentSizes) {
/* 171 */           totalSize += componentSize;
/*     */         }
/* 173 */         boolean containerResized = totalSize + getGapSize() != getSizeForPrimaryAxis(containerSize);
/* 174 */         if (containerResized) {
/* 175 */           int availableSize = getAvailableSize(containerSize, insets);
/* 176 */           availableSize -= getGapSize();
/* 177 */           if (availableSize <= 0) {
/* 178 */             return;
/*     */           }
/* 180 */           boolean success = calculateComponentSizes(availableSize, 0, this._target.getComponentCount());
/* 181 */           if (!success) {
/* 182 */             if ((!isAlwaysLayout()) && ("false".equals(SecurityUtils.getProperty("JideBoxLayout.alwaysLayout", "false")))) {
/* 183 */               for (int i = 0; i < this._target.getComponentCount(); i++) {
/* 184 */                 Component comp = this._target.getComponent(i);
/* 185 */                 setComponentToSize(comp, 0, 0, insets, containerSize);
/*     */               }
/* 187 */               redoLayout(container);
/*     */             }
/* 189 */             return;
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 194 */       ComponentOrientation o = this._target.getComponentOrientation();
/* 195 */       boolean ltr = o.isLeftToRight();
/* 196 */       int location = getSizeForPrimaryAxis(insets, true);
/* 197 */       boolean needRedoLayout = false;
/* 198 */       if ((!ltr) && (resolveAxis(this._axis, o) == 0)) {
/* 199 */         location = containerSize.width - location;
/*     */       }
/* 201 */       for (int i = 0; i < this._target.getComponentCount(); i++) {
/* 202 */         Component comp = this._target.getComponent(i);
/* 203 */         int oldSize = getPreferredSizeOfComponent(comp);
/* 204 */         if ((!ltr) && (resolveAxis(this._axis, o) == 0)) {
/* 205 */           location -= this._componentSizes[i];
/* 206 */           setComponentToSize(comp, this._componentSizes[i], location, insets, containerSize);
/* 207 */           if (LOGGER.isLoggable(Level.FINE)) {
/* 208 */             LOGGER.fine("layoutContainer index: " + i + " size: " + this._componentSizes[i]);
/*     */           }
/* 210 */           if (this._componentSizes[i] != 0)
/* 211 */             location -= this._gap;
/*     */         }
/*     */         else {
/* 214 */           setComponentToSize(comp, this._componentSizes[i], location, insets, containerSize);
/* 215 */           if (LOGGER.isLoggable(Level.FINE)) {
/* 216 */             LOGGER.fine("layoutContainer index: " + i + " size: " + this._componentSizes[i]);
/*     */           }
/* 218 */           location += this._componentSizes[i];
/* 219 */           if (this._componentSizes[i] != 0)
/* 220 */             location += this._gap;
/*     */         }
/* 222 */         int newSize = getPreferredSizeOfComponent(comp);
/* 223 */         if (newSize != oldSize) {
/* 224 */           needRedoLayout = true;
/*     */         }
/*     */       }
/* 227 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 228 */         LOGGER.fine("layoutContainer ended");
/*     */       }
/* 230 */       if ((this._target instanceof JideOptionPane)) {
/* 231 */         for (int i = 0; i < container.getComponentCount(); i++) {
/* 232 */           container.getComponent(i).invalidate();
/*     */         }
/* 234 */         if (needRedoLayout)
/* 235 */           redoLayout(container);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void redoLayout(Container container)
/*     */   {
/* 242 */     Component parent = container.getParent();
/* 243 */     while ((parent != null) && 
/* 244 */       (!(parent instanceof Dialog)))
/*     */     {
/* 247 */       parent = parent.getParent();
/*     */     }
/* 249 */     if (parent != null)
/* 250 */       ((Dialog)parent).pack();
/*     */   }
/*     */ 
/*     */   protected boolean calculateComponentSizes(int availableSize, int startIndex, int endIndex)
/*     */   {
/* 255 */     int availableSizeExcludeFixed = availableSize;
/* 256 */     int varMinSize = 0;
/* 257 */     int flexMinSize = 0;
/* 258 */     int varIndex = -1;
/* 259 */     int totalFlexSize = 0;
/* 260 */     int totalFlexSizeMinusMin = 0;
/* 261 */     int lastFlexIndex = -1;
/* 262 */     int lastNoneZeroFlexIndex = -1;
/* 263 */     for (int i = startIndex; i < endIndex; i++) {
/* 264 */       Component comp = this._target.getComponent(i);
/* 265 */       if (!comp.isVisible()) {
/*     */         continue;
/*     */       }
/* 268 */       Object constraint = this._constraintMap.get(comp);
/* 269 */       int minimumSize = getSizeForPrimaryAxis(comp.getMinimumSize());
/* 270 */       int preferredSize = getSizeForPrimaryAxis(getPreferredSizeOf(comp, i));
/* 271 */       if ("fix".equals(constraint)) {
/* 272 */         availableSizeExcludeFixed -= Math.max(preferredSize, minimumSize);
/*     */       }
/* 274 */       else if ("vary".equals(constraint)) {
/* 275 */         varIndex = i;
/* 276 */         getPreferredSizeOf(comp, i);
/* 277 */         varMinSize = minimumSize;
/*     */       }
/*     */       else {
/* 280 */         if (preferredSize > minimumSize) {
/* 281 */           totalFlexSizeMinusMin += preferredSize - minimumSize;
/*     */         }
/* 283 */         totalFlexSize += preferredSize;
/* 284 */         flexMinSize += minimumSize;
/* 285 */         lastFlexIndex = i;
/*     */ 
/* 287 */         if (preferredSize != 0) {
/* 288 */           lastNoneZeroFlexIndex = i;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 293 */     if ((!isAlwaysLayout()) && ("false".equals(SecurityUtils.getProperty("JideBoxLayout.alwaysLayout", "false"))) && (availableSizeExcludeFixed - varMinSize < 0)) {
/* 294 */       return false;
/*     */     }
/*     */ 
/* 297 */     boolean hasVary = varIndex != -1;
/* 298 */     boolean expand = availableSizeExcludeFixed - varMinSize >= totalFlexSize;
/*     */ 
/* 300 */     if ((!hasVary) || ((hasVary) && (!expand)))
/*     */     {
/*     */       double resizeRatio;
/*     */       double resizeRatio;
/* 302 */       if (expand) {
/* 303 */         resizeRatio = totalFlexSize == 0 ? 0.0D : (availableSizeExcludeFixed - varMinSize) / totalFlexSize;
/*     */       }
/*     */       else {
/* 306 */         resizeRatio = totalFlexSizeMinusMin == 0 ? 0.0D : (availableSizeExcludeFixed - varMinSize - flexMinSize) / totalFlexSizeMinusMin;
/*     */       }
/*     */ 
/* 309 */       for (int i = startIndex; i < endIndex; i++) {
/* 310 */         Component comp = this._target.getComponent(i);
/* 311 */         if (!comp.isVisible()) {
/* 312 */           setComponentSize(i, 0);
/*     */         }
/*     */         else {
/* 315 */           Object constraint = this._constraintMap.get(comp);
/* 316 */           int minimumSize = getSizeForPrimaryAxis(comp.getMinimumSize());
/* 317 */           int preferredSize = getSizeForPrimaryAxis(getPreferredSizeOf(comp, i));
/* 318 */           if ("fix".equals(constraint)) {
/* 319 */             setComponentSize(i, Math.max(preferredSize, minimumSize));
/*     */           }
/* 321 */           else if ("vary".equals(constraint)) {
/* 322 */             setComponentSize(i, varMinSize);
/*     */           }
/* 325 */           else if (expand) {
/* 326 */             setComponentSize(i, (int)(preferredSize * resizeRatio));
/*     */           }
/*     */           else {
/* 329 */             setComponentSize(i, minimumSize + (int)((preferredSize - minimumSize) * resizeRatio));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 336 */       for (int i = startIndex; i < endIndex; i++) {
/* 337 */         Component comp = this._target.getComponent(i);
/* 338 */         if (!comp.isVisible()) {
/* 339 */           setComponentSize(i, 0);
/*     */         }
/*     */         else {
/* 342 */           Object constraint = this._constraintMap.get(comp);
/* 343 */           int minimumSize = getSizeForPrimaryAxis(comp.getMinimumSize());
/* 344 */           int preferredSize = getSizeForPrimaryAxis(getPreferredSizeOf(comp, i));
/* 345 */           if ("fix".equals(constraint)) {
/* 346 */             setComponentSize(i, Math.max(preferredSize, minimumSize));
/*     */           }
/* 348 */           else if ("vary".equals(constraint)) {
/* 349 */             setComponentSize(i, availableSizeExcludeFixed - totalFlexSize);
/*     */           }
/*     */           else {
/* 352 */             setComponentSize(i, Math.max(preferredSize, minimumSize));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 358 */     int totalActualSize = 0;
/* 359 */     for (int i = startIndex; i < endIndex; i++) {
/* 360 */       totalActualSize += this._componentSizes[i];
/*     */     }
/*     */ 
/* 363 */     if (totalActualSize != availableSize) {
/* 364 */       if (varIndex != -1) {
/* 365 */         setComponentSizeByGap(varIndex, availableSize - totalActualSize);
/*     */       }
/* 367 */       else if (lastNoneZeroFlexIndex != -1) {
/* 368 */         setComponentSizeByGap(lastNoneZeroFlexIndex, availableSize - totalActualSize);
/*     */       }
/* 370 */       else if (lastFlexIndex != -1) {
/* 371 */         setComponentSizeByGap(lastFlexIndex, availableSize - totalActualSize);
/*     */       }
/*     */     }
/*     */ 
/* 375 */     return true;
/*     */   }
/*     */ 
/*     */   private void setComponentSizeByGap(int index, int gap) {
/* 379 */     if ((SystemInfo.isJdk15Above()) && (this._target.getComponent(index).isMinimumSizeSet())) {
/* 380 */       setComponentSize(index, Math.max(this._componentSizes[index] + gap, getSizeForPrimaryAxis(this._target.getComponent(index).getMinimumSize())));
/*     */     }
/*     */     else
/* 383 */       setComponentSize(index, this._componentSizes[index] + gap);
/*     */   }
/*     */ 
/*     */   private void setComponentSize(int index, int size)
/*     */   {
/* 388 */     if (LOGGER.isLoggable(Level.FINE)) {
/* 389 */       LOGGER.fine("setComponentSize index: " + index + " size: " + size);
/*     */     }
/* 391 */     this._componentSizes[index] = size;
/*     */   }
/*     */ 
/*     */   public void addLayoutComponent(String name, Component component)
/*     */   {
/* 402 */     layoutReset();
/*     */   }
/*     */ 
/*     */   public Dimension minimumLayoutSize(Container container)
/*     */   {
/* 410 */     int minPrimary = 0;
/* 411 */     int minSecondary = 0;
/* 412 */     Insets insets = this._target.getInsets();
/*     */ 
/* 414 */     synchronized (container.getTreeLock()) {
/* 415 */       for (int i = 0; i < this._target.getComponentCount(); i++) {
/* 416 */         Component comp = this._target.getComponent(i);
/* 417 */         if (!comp.isVisible()) {
/*     */           continue;
/*     */         }
/* 420 */         Object constraint = this._constraintMap.get(comp);
/* 421 */         Dimension minimumSize = comp.getMinimumSize();
/* 422 */         if ("fix".equals(constraint)) {
/* 423 */           minPrimary += getPreferredSizeOfComponent(comp);
/*     */         }
/*     */         else {
/* 426 */           minPrimary += getSizeForPrimaryAxis(minimumSize);
/*     */         }
/* 428 */         int secSize = getSizeForSecondaryAxis(minimumSize);
/* 429 */         if (secSize > minSecondary) {
/* 430 */           minSecondary = secSize;
/*     */         }
/*     */       }
/* 433 */       if (insets != null) {
/* 434 */         minPrimary += getSizeForPrimaryAxis(insets, true) + getSizeForPrimaryAxis(insets, false);
/*     */ 
/* 436 */         minSecondary += getSizeForSecondaryAxis(insets, true) + getSizeForSecondaryAxis(insets, false);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 441 */     ComponentOrientation o = this._target.getComponentOrientation();
/* 442 */     if (resolveAxis(this._axis, o) == 0) {
/* 443 */       return new Dimension(minPrimary + getGapSize(), minSecondary);
/*     */     }
/*     */ 
/* 446 */     return new Dimension(minSecondary, minPrimary + getGapSize());
/*     */   }
/*     */ 
/*     */   public Dimension preferredLayoutSize(Container container)
/*     */   {
/* 456 */     int prePrimary = 0;
/* 457 */     int preSecondary = 0;
/* 458 */     Insets insets = this._target.getInsets();
/*     */ 
/* 460 */     synchronized (container.getTreeLock()) {
/* 461 */       for (int i = 0; i < this._target.getComponentCount(); i++) {
/* 462 */         Component comp = this._target.getComponent(i);
/* 463 */         if (!comp.isVisible()) {
/*     */           continue;
/*     */         }
/* 466 */         Dimension preferredSize = getPreferredSizeOf(comp, i);
/* 467 */         prePrimary += getSizeForPrimaryAxis(preferredSize);
/* 468 */         int secSize = getSizeForSecondaryAxis(preferredSize);
/* 469 */         if (secSize > preSecondary) {
/* 470 */           preSecondary = secSize;
/*     */         }
/*     */       }
/* 473 */       if (insets != null) {
/* 474 */         prePrimary += getSizeForPrimaryAxis(insets, true) + getSizeForPrimaryAxis(insets, false);
/*     */ 
/* 476 */         preSecondary += getSizeForSecondaryAxis(insets, true) + getSizeForSecondaryAxis(insets, false);
/*     */       }
/*     */     }
/*     */ 
/* 480 */     if (this._axis == 0) {
/* 481 */       return new Dimension(prePrimary + getGapSize(), preSecondary);
/*     */     }
/*     */ 
/* 484 */     return new Dimension(preSecondary, prePrimary + getGapSize());
/*     */   }
/*     */ 
/*     */   private int getGapSize()
/*     */   {
/* 489 */     if (this._gap == 0) {
/* 490 */       return 0;
/*     */     }
/*     */ 
/* 493 */     int count = 0;
/* 494 */     for (int i = 0; i < this._target.getComponentCount(); i++) {
/* 495 */       if (this._target.getComponent(i).isVisible()) {
/* 496 */         count++;
/*     */       }
/*     */     }
/* 499 */     return Math.max(0, count - 1) * this._gap;
/*     */   }
/*     */ 
/*     */   public void removeLayoutComponent(Component comp)
/*     */   {
/* 510 */     this._constraintMap.remove(comp);
/*     */ 
/* 512 */     if ((comp instanceof JideSplitPaneDivider))
/* 513 */       layoutReset();
/*     */   }
/*     */ 
/*     */   public void addLayoutComponent(Component comp, Object constraints)
/*     */   {
/* 528 */     if (constraints == null)
/* 529 */       this._constraintMap.put(comp, "flexible");
/*     */     else
/* 531 */       this._constraintMap.put(comp, constraints);
/* 532 */     layoutReset();
/*     */   }
/*     */ 
/*     */   private void layoutReset() {
/* 536 */     this.doReset = true;
/* 537 */     if (LOGGER.isLoggable(Level.FINE))
/* 538 */       LOGGER.fine(this + " layoutReset");
/*     */   }
/*     */ 
/*     */   public synchronized float getLayoutAlignmentX(Container target)
/*     */   {
/* 548 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public synchronized float getLayoutAlignmentY(Container target)
/*     */   {
/* 558 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public synchronized void invalidateLayout(Container c)
/*     */   {
/* 566 */     if ((isResetWhenInvalidate()) || (componentCountChanged(c)))
/* 567 */       layoutReset();
/*     */   }
/*     */ 
/*     */   protected boolean componentCountChanged(Container c)
/*     */   {
/* 572 */     if (this._componentSizes == null) {
/* 573 */       return true;
/*     */     }
/* 575 */     int oldLength = 0;
/* 576 */     for (int _componentSize : this._componentSizes) {
/* 577 */       if (_componentSize > 0) {
/* 578 */         oldLength++;
/*     */       }
/*     */     }
/* 581 */     int newLength = 0;
/* 582 */     for (int i = 0; i < c.getComponentCount(); i++) {
/* 583 */       if (c.getComponent(i).isVisible()) {
/* 584 */         newLength++;
/*     */       }
/*     */     }
/* 587 */     return newLength != oldLength;
/*     */   }
/*     */ 
/*     */   public Dimension maximumLayoutSize(Container target)
/*     */   {
/* 594 */     return new Dimension(2147483647, 2147483647);
/*     */   }
/*     */ 
/*     */   protected int getPreferredSizeOfComponent(Component c)
/*     */   {
/* 604 */     return getSizeForPrimaryAxis(c.getPreferredSize());
/*     */   }
/*     */ 
/*     */   int getMinimumSizeOfComponent(Component c)
/*     */   {
/* 615 */     return getSizeForPrimaryAxis(c.getMinimumSize());
/*     */   }
/*     */ 
/*     */   protected int getSizeOfComponent(Component c)
/*     */   {
/* 626 */     return getSizeForPrimaryAxis(c.getSize());
/*     */   }
/*     */ 
/*     */   protected int getAvailableSize(Dimension containerSize, Insets insets)
/*     */   {
/* 638 */     if (insets == null)
/* 639 */       return getSizeForPrimaryAxis(containerSize);
/* 640 */     return getSizeForPrimaryAxis(containerSize) - (getSizeForPrimaryAxis(insets, true) + getSizeForPrimaryAxis(insets, false));
/*     */   }
/*     */ 
/*     */   protected int getInitialLocation(Insets insets)
/*     */   {
/* 653 */     if (insets != null)
/* 654 */       return getSizeForPrimaryAxis(insets, true);
/* 655 */     return 0;
/*     */   }
/*     */ 
/*     */   protected void setComponentToSize(Component c, int size, int location, Insets insets, Dimension containerSize)
/*     */   {
/* 671 */     if (insets != null) {
/* 672 */       ComponentOrientation o = this._target.getComponentOrientation();
/* 673 */       if (resolveAxis(this._axis, o) == 0) {
/* 674 */         c.setBounds(Math.max(location, 0), Math.max(insets.top, 0), Math.max(size, 0), Math.max(containerSize.height - (insets.top + insets.bottom), 0));
/*     */       }
/*     */       else
/*     */       {
/* 680 */         c.setBounds(Math.max(insets.left, 0), Math.max(location, 0), Math.max(containerSize.width - (insets.left + insets.right), 0), Math.max(size, 0));
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 687 */       ComponentOrientation o = this._target.getComponentOrientation();
/* 688 */       if (resolveAxis(this._axis, o) == 0) {
/* 689 */         c.setBounds(Math.max(location, 0), 0, Math.max(size, 0), Math.max(containerSize.height, 0));
/*     */       }
/*     */       else
/*     */       {
/* 695 */         c.setBounds(0, Math.max(location, 0), Math.max(containerSize.width, 0), Math.max(size, 0));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   int getSizeForPrimaryAxis(Dimension size)
/*     */   {
/* 707 */     ComponentOrientation o = this._target.getComponentOrientation();
/* 708 */     if (resolveAxis(this._axis, o) == 0) {
/* 709 */       return size.width;
/*     */     }
/*     */ 
/* 712 */     return size.height;
/*     */   }
/*     */ 
/*     */   int getSizeForSecondaryAxis(Dimension size)
/*     */   {
/* 720 */     ComponentOrientation o = this._target.getComponentOrientation();
/* 721 */     if (resolveAxis(this._axis, o) == 0) {
/* 722 */       return size.height;
/*     */     }
/*     */ 
/* 725 */     return size.width;
/*     */   }
/*     */ 
/*     */   int getSizeForPrimaryAxis(Insets insets, boolean isTop)
/*     */   {
/* 734 */     ComponentOrientation o = this._target.getComponentOrientation();
/* 735 */     if (resolveAxis(this._axis, o) == 0) {
/* 736 */       if (isTop) {
/* 737 */         return insets.left;
/*     */       }
/*     */ 
/* 740 */       return insets.right;
/*     */     }
/*     */ 
/* 744 */     if (isTop) {
/* 745 */       return insets.top;
/*     */     }
/*     */ 
/* 748 */     return insets.bottom;
/*     */   }
/*     */ 
/*     */   int getSizeForSecondaryAxis(Insets insets, boolean isTop)
/*     */   {
/* 758 */     ComponentOrientation o = this._target.getComponentOrientation();
/* 759 */     if (resolveAxis(this._axis, o) == 0) {
/* 760 */       if (isTop) {
/* 761 */         return insets.top;
/*     */       }
/*     */ 
/* 764 */       return insets.bottom;
/*     */     }
/*     */ 
/* 768 */     if (isTop) {
/* 769 */       return insets.left;
/*     */     }
/*     */ 
/* 772 */     return insets.right;
/*     */   }
/*     */ 
/*     */   public Map<Component, Object> getConstraintMap()
/*     */   {
/* 783 */     return this._constraintMap;
/*     */   }
/*     */ 
/*     */   protected static int resolveAxis(int axis, ComponentOrientation o)
/*     */   {
/*     */     int absoluteAxis;
/*     */     int absoluteAxis;
/* 797 */     if (axis == 2) {
/* 798 */       absoluteAxis = o.isHorizontal() ? 0 : 1;
/*     */     }
/*     */     else
/*     */     {
/*     */       int absoluteAxis;
/* 800 */       if (axis == 3) {
/* 801 */         absoluteAxis = o.isHorizontal() ? 1 : 0;
/*     */       }
/*     */       else
/* 804 */         absoluteAxis = axis;
/*     */     }
/* 806 */     return absoluteAxis;
/*     */   }
/*     */ 
/*     */   public int getGap()
/*     */   {
/* 815 */     return this._gap;
/*     */   }
/*     */ 
/*     */   public void setGap(int gap)
/*     */   {
/* 824 */     this._gap = gap;
/*     */   }
/*     */ 
/*     */   protected Dimension getPreferredSizeOf(Component comp, int atIndex) {
/* 828 */     Dimension preferredSize = comp.getPreferredSize();
/* 829 */     Dimension minimumSize = comp.getMinimumSize();
/* 830 */     if (preferredSize.height < minimumSize.height) {
/* 831 */       preferredSize.height = minimumSize.height;
/*     */     }
/* 833 */     if (preferredSize.width < minimumSize.width) {
/* 834 */       preferredSize.width = minimumSize.width;
/*     */     }
/* 836 */     Dimension maximumSize = comp.getMaximumSize();
/* 837 */     if (preferredSize.height > maximumSize.height) {
/* 838 */       preferredSize.height = maximumSize.height;
/*     */     }
/* 840 */     if (preferredSize.width > maximumSize.width) {
/* 841 */       preferredSize.width = maximumSize.width;
/*     */     }
/* 843 */     return preferredSize;
/*     */   }
/*     */ 
/*     */   public boolean isResetWhenInvalidate()
/*     */   {
/* 852 */     return this._resetWhenInvalidate;
/*     */   }
/*     */ 
/*     */   public void setResetWhenInvalidate(boolean resetWhenInvalidate)
/*     */   {
/* 861 */     this._resetWhenInvalidate = resetWhenInvalidate;
/*     */   }
/*     */ 
/*     */   public int getAxis()
/*     */   {
/* 870 */     return this._axis;
/*     */   }
/*     */ 
/*     */   public void setAxis(int axis)
/*     */   {
/* 879 */     this._axis = axis;
/*     */   }
/*     */ 
/*     */   public boolean isAlwaysLayout()
/*     */   {
/* 889 */     return this._alwaysLayout;
/*     */   }
/*     */ 
/*     */   public void setAlwaysLayout(boolean alwaysLayout)
/*     */   {
/* 899 */     this._alwaysLayout = alwaysLayout;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.JideBoxLayout
 * JD-Core Version:    0.6.0
 */
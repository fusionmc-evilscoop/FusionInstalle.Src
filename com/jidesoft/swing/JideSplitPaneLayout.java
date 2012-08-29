/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.utils.SystemInfo;
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.swing.JComponent;
/*     */ 
/*     */ public class JideSplitPaneLayout extends JideBoxLayout
/*     */ {
/*     */   private static final long serialVersionUID = -1826651835409198865L;
/*     */ 
/*     */   public JideSplitPaneLayout(Container target)
/*     */   {
/*  23 */     super(target);
/*  24 */     setResetWhenInvalidate(false);
/*     */   }
/*     */ 
/*     */   public JideSplitPaneLayout(Container target, int axis) {
/*  28 */     super(target, axis);
/*  29 */     setResetWhenInvalidate(false);
/*     */   }
/*     */ 
/*     */   public JideSplitPaneLayout(Container target, int axis, int gap) {
/*  33 */     super(target, axis, gap);
/*  34 */     setResetWhenInvalidate(false);
/*     */   }
/*     */ 
/*     */   int getDividerLocation(int index) {
/*  38 */     if (this._componentSizes == null) {
/*  39 */       return -1;
/*     */     }
/*  41 */     if ((index < 0) || (index + 1 << 1 >= this._componentSizes.length)) {
/*  42 */       return -1;
/*     */     }
/*  44 */     boolean ltr = this._target.getComponentOrientation().isLeftToRight();
/*  45 */     boolean reversed = (!ltr) && (((JideSplitPane)this._target).getOrientation() == 1);
/*     */ 
/*  47 */     int location = 0;
/*  48 */     if (reversed) {
/*  49 */       for (int i = this._componentSizes.length - 1; i >= index * 2 + 1; i--)
/*  50 */         location += this._componentSizes[i];
/*     */     }
/*     */     else {
/*  53 */       for (int i = 0; i < index * 2 + 1; i++) {
/*  54 */         location += this._componentSizes[i];
/*     */       }
/*     */     }
/*  57 */     Insets insets = this._target.getInsets();
/*  58 */     if (insets != null) {
/*  59 */       if (((JideSplitPane)this._target).getOrientation() == 1) {
/*  60 */         location += (reversed ? insets.right : insets.left);
/*     */       }
/*     */       else {
/*  63 */         location += insets.top;
/*     */       }
/*     */     }
/*  66 */     return location;
/*     */   }
/*     */ 
/*     */   int setDividerLocation(int index, int location, boolean isOriginator)
/*     */   {
/*  71 */     int oldLocation = getDividerLocation(index);
/*  72 */     if ((oldLocation == -1) || (oldLocation == location))
/*  73 */       return -1;
/*  74 */     boolean ltr = this._target.getComponentOrientation().isLeftToRight();
/*  75 */     boolean reversed = (!ltr) && (((JideSplitPane)this._target).getOrientation() == 1);
/*  76 */     int prevIndex = reversed ? 2 * index + 2 : 2 * index;
/*  77 */     int nextIndex = reversed ? 2 * index : 2 * index + 2;
/*  78 */     int nextDividerIndex = reversed ? index - 1 : index + 1;
/*  79 */     int prevDividerIndex = reversed ? index + 1 : index - 1;
/*     */ 
/*  82 */     List componentIndexChanged = new ArrayList();
/*     */     int flexibleNextIndex;
/*     */     int flexiblePrevIndex;
/*  84 */     if (reversed) {
/*  85 */       while ((nextIndex >= 0) && (!isPaneVisible(nextIndex))) {
/*  86 */         nextIndex -= 2;
/*  87 */         nextDividerIndex--;
/*     */       }
/*     */ 
/*  90 */       while ((prevIndex < this._componentSizes.length) && (!isPaneVisible(prevIndex))) {
/*  91 */         prevIndex += 2;
/*  92 */         prevDividerIndex++;
/*     */       }
/*     */ 
/*  95 */       int flexibleNextIndex = nextIndex;
/*  96 */       while ((flexibleNextIndex >= 0) && ((getConstraintMap().get(this._target.getComponent(flexibleNextIndex)) == "fix") || (!isPaneVisible(flexibleNextIndex))))
/*     */       {
/*  98 */         flexibleNextIndex -= 2;
/*     */       }
/* 100 */       if (flexibleNextIndex < 0) {
/* 101 */         return -1;
/*     */       }
/*     */ 
/* 104 */       int flexiblePrevIndex = prevIndex;
/* 105 */       while ((flexiblePrevIndex < this._componentSizes.length) && ((getConstraintMap().get(this._target.getComponent(flexiblePrevIndex)) == "fix") || (!isPaneVisible(flexiblePrevIndex))))
/*     */       {
/* 107 */         flexiblePrevIndex += 2;
/*     */       }
/* 109 */       if (flexiblePrevIndex >= this._componentSizes.length)
/* 110 */         return -1;
/*     */     }
/*     */     else
/*     */     {
/* 114 */       while ((nextIndex < this._componentSizes.length) && (!isPaneVisible(nextIndex))) {
/* 115 */         nextIndex += 2;
/* 116 */         nextDividerIndex++;
/*     */       }
/*     */ 
/* 119 */       while ((prevIndex >= 0) && (!isPaneVisible(prevIndex))) {
/* 120 */         prevIndex -= 2;
/* 121 */         prevDividerIndex--;
/*     */       }
/*     */ 
/* 124 */       flexibleNextIndex = nextIndex;
/* 125 */       while ((flexibleNextIndex < this._componentSizes.length) && ((getConstraintMap().get(this._target.getComponent(flexibleNextIndex)) == "fix") || (!isPaneVisible(flexibleNextIndex))))
/*     */       {
/* 127 */         flexibleNextIndex += 2;
/*     */       }
/* 129 */       if (flexibleNextIndex >= this._componentSizes.length) {
/* 130 */         return -1;
/*     */       }
/*     */ 
/* 133 */       flexiblePrevIndex = prevIndex;
/* 134 */       while ((flexiblePrevIndex >= 0) && ((getConstraintMap().get(this._target.getComponent(flexiblePrevIndex)) == "fix") || (!isPaneVisible(flexiblePrevIndex))))
/*     */       {
/* 136 */         flexiblePrevIndex -= 2;
/*     */       }
/* 138 */       if (flexiblePrevIndex < 0) {
/* 139 */         return -1;
/*     */       }
/*     */     }
/*     */ 
/* 143 */     if ((isOriginator) && (getConstraintMap().get(this._target.getComponent(nextIndex)) == "fix") && (getConstraintMap().get(this._target.getComponent(prevIndex)) == "fix"))
/*     */     {
/* 146 */       return -1;
/*     */     }
/*     */ 
/* 149 */     if (location > oldLocation) {
/* 150 */       int size = this._componentSizes[(2 * index + 1)];
/* 151 */       if (getConstraintMap().get(this._target.getComponent(nextIndex)) == "fix") {
/* 152 */         size += this._componentSizes[nextIndex];
/*     */       }
/* 155 */       else if (((JideSplitPane)this._target).getOrientation() == 1) {
/* 156 */         size = (int)(size + this._target.getComponent(nextIndex).getMinimumSize().getWidth());
/*     */       }
/*     */       else {
/* 159 */         size = (int)(size + this._target.getComponent(nextIndex).getMinimumSize().getHeight());
/*     */       }
/*     */ 
/* 163 */       int nextDividerLocation = getDividerLocation(nextDividerIndex);
/* 164 */       if (nextDividerLocation < 0) {
/* 165 */         if (((JideSplitPane)this._target).getOrientation() == 1) {
/* 166 */           location = Math.min(location, this._target.getWidth() - size);
/*     */         }
/*     */         else {
/* 169 */           location = Math.min(location, this._target.getHeight() - size);
/*     */         }
/*     */       }
/* 172 */       else if (location + size > nextDividerLocation) {
/* 173 */         int actualLocation = setDividerLocation(nextDividerIndex, location + size, false);
/* 174 */         if (actualLocation == -1) {
/* 175 */           return -1;
/*     */         }
/* 177 */         location = actualLocation - size;
/*     */       }
/* 179 */       if (getConstraintMap().get(this._target.getComponent(nextIndex)) != "fix") {
/* 180 */         this._componentSizes[nextIndex] -= location - oldLocation;
/* 181 */         componentIndexChanged.add(Integer.valueOf(nextIndex));
/*     */       }
/* 183 */       if (isOriginator) {
/* 184 */         this._componentSizes[flexiblePrevIndex] += location - oldLocation;
/* 185 */         componentIndexChanged.add(Integer.valueOf(flexiblePrevIndex));
/*     */       }
/* 187 */       else if (getConstraintMap().get(this._target.getComponent(prevIndex)) != "fix") {
/* 188 */         this._componentSizes[prevIndex] += location - oldLocation;
/* 189 */         componentIndexChanged.add(Integer.valueOf(prevIndex));
/*     */       }
/*     */     }
/* 192 */     else if (location < oldLocation) {
/* 193 */       int size = 0;
/* 194 */       if (prevDividerIndex >= 0) {
/* 195 */         size = this._componentSizes[(prevIndex - 1)];
/*     */       }
/* 197 */       if (getConstraintMap().get(this._target.getComponent(prevIndex)) == "fix") {
/* 198 */         size += this._componentSizes[prevIndex];
/*     */       }
/* 201 */       else if (((JideSplitPane)this._target).getOrientation() == 1) {
/* 202 */         size = (int)(size + this._target.getComponent(prevIndex).getMinimumSize().getWidth());
/*     */       }
/*     */       else {
/* 205 */         size = (int)(size + this._target.getComponent(prevIndex).getMinimumSize().getHeight());
/*     */       }
/*     */ 
/* 209 */       int prevDividerLocation = getDividerLocation(prevDividerIndex);
/* 210 */       if (prevDividerLocation < 0) {
/* 211 */         location = Math.max(location, size);
/*     */       }
/* 213 */       else if (location - size < prevDividerLocation) {
/* 214 */         int actualLocation = setDividerLocation(prevDividerIndex, location - size, false);
/* 215 */         if (actualLocation == -1) {
/* 216 */           return -1;
/*     */         }
/* 218 */         location = actualLocation + size;
/*     */       }
/* 220 */       if (getConstraintMap().get(this._target.getComponent(prevIndex)) != "fix") {
/* 221 */         this._componentSizes[prevIndex] -= oldLocation - location;
/* 222 */         componentIndexChanged.add(Integer.valueOf(prevIndex));
/*     */       }
/* 224 */       if (isOriginator) {
/* 225 */         this._componentSizes[flexibleNextIndex] += oldLocation - location;
/* 226 */         componentIndexChanged.add(Integer.valueOf(flexibleNextIndex));
/*     */       }
/* 228 */       else if (getConstraintMap().get(this._target.getComponent(nextIndex)) != "fix") {
/* 229 */         this._componentSizes[nextIndex] += oldLocation - location;
/* 230 */         componentIndexChanged.add(Integer.valueOf(nextIndex));
/*     */       }
/*     */     }
/*     */ 
/* 234 */     if (SystemInfo.isJdk15Above()) {
/* 235 */       if ((this._target instanceof JideSplitPane)) {
/* 236 */         ((JideSplitPane)this._target).firePropertyChange("dividerLocation", oldLocation, location);
/*     */       }
/*     */       else {
/* 239 */         this._target.firePropertyChange("dividerLocation", oldLocation, location);
/*     */       }
/*     */     }
/* 242 */     ((JideSplitPane)this._target).revalidate();
/*     */ 
/* 244 */     if (((JideSplitPane)this._target).isProportionalLayout()) {
/* 245 */       replaceProportions();
/* 246 */       return location;
/*     */     }
/*     */ 
/* 249 */     if (((JideSplitPane)this._target).getOrientation() == 1) {
/* 250 */       for (int changedIndex = 0; changedIndex < this._target.getComponentCount(); changedIndex++) {
/* 251 */         Component component = this._target.getComponent(changedIndex);
/* 252 */         if (((component instanceof JComponent)) && (!(component instanceof JideSplitPaneDivider))) {
/* 253 */           ((JComponent)component).setPreferredSize(new Dimension(this._componentSizes[changedIndex], component.getPreferredSize().height));
/*     */         }
/*     */       }
/*     */     }
/*     */     else {
/* 258 */       for (int changedIndex = 0; changedIndex < this._target.getComponentCount(); changedIndex++) {
/* 259 */         Component component = this._target.getComponent(changedIndex);
/* 260 */         if (((component instanceof JComponent)) && (!(component instanceof JideSplitPaneDivider))) {
/* 261 */           ((JComponent)component).setPreferredSize(new Dimension(component.getPreferredSize().width, this._componentSizes[changedIndex]));
/*     */         }
/*     */       }
/*     */     }
/* 265 */     return location;
/*     */   }
/*     */ 
/*     */   private boolean isPaneVisible(int index)
/*     */   {
/* 270 */     return (index < this._componentSizes.length) && ((this._componentSizes[index] != 0) || (index - 1 < 0) || (this._componentSizes[(index - 1)] != 0));
/*     */   }
/*     */ 
/*     */   private void replaceProportions()
/*     */   {
/* 277 */     ((JideSplitPane)this._target).setProportions(deduceProportions());
/*     */   }
/*     */ 
/*     */   private double[] deduceProportions()
/*     */   {
/* 286 */     double total = 0.0D;
/* 287 */     for (int i = 0; i < this._componentSizes.length; i += 2)
/* 288 */       total += this._componentSizes[i];
/*     */     double[] newProportions;
/*     */     double[] newProportions;
/* 290 */     if (total == 0.0D) {
/* 291 */       newProportions = null;
/*     */     } else {
/* 293 */       newProportions = new double[(this._componentSizes.length - 1) / 2];
/* 294 */       for (int i = 0; i < newProportions.length; i++)
/* 295 */         newProportions[i] = (this._componentSizes[(i * 2)] / total);
/*     */     }
/* 297 */     return newProportions;
/*     */   }
/*     */ 
/*     */   protected boolean calculateComponentSizes(int availableSize, int startIndex, int endIndex)
/*     */   {
/* 305 */     if ((!((JideSplitPane)this._target).isProportionalLayout()) || (this._target.getComponentCount() <= 1))
/*     */     {
/* 307 */       return super.calculateComponentSizes(availableSize, startIndex, endIndex);
/*     */     }
/*     */ 
/* 311 */     if (((JideSplitPane)this._target).getProportions() == null) {
/* 312 */       if (!((JideSplitPane)this._target).isInitiallyEven())
/*     */       {
/* 314 */         return super.calculateComponentSizes(availableSize, startIndex, endIndex);
/*     */       }
/*     */ 
/* 324 */       int c = ((JideSplitPane)this._target).getPaneCount();
/* 325 */       double[] p = new double[c - 1];
/* 326 */       for (int i = 0; i < p.length; i++)
/* 327 */         p[i] = (1.0D / c);
/* 328 */       ((JideSplitPane)this._target).internalSetProportions(p);
/*     */     }
/*     */ 
/* 332 */     for (int i = 1; i < this._target.getComponentCount(); i += 2) {
/* 333 */       if (this._target.getComponent(i).isVisible())
/* 334 */         this._componentSizes[i] = ((JideSplitPane)this._target).getDividerSize();
/* 335 */       availableSize -= this._componentSizes[i];
/*     */     }
/*     */ 
/* 338 */     if (availableSize < 0) {
/* 339 */       return false;
/*     */     }
/*     */ 
/* 343 */     double[] proportions = ((JideSplitPane)this._target).getProportions();
/*     */ 
/* 352 */     int size = availableSize;
/* 353 */     for (int i = 0; i < proportions.length; i++) {
/* 354 */       int j = i * 2;
/* 355 */       if (this._target.getComponent(j).isVisible()) {
/* 356 */         double d = proportions[i];
/* 357 */         if (d <= 1.0D)
/* 358 */           this._componentSizes[j] = (int)(0.5D + size * d);
/*     */       }
/* 360 */       availableSize -= this._componentSizes[j];
/*     */     }
/*     */ 
/* 363 */     if (availableSize < 0) {
/* 364 */       return false;
/*     */     }
/* 366 */     this._componentSizes[(this._componentSizes.length - 1)] = availableSize;
/* 367 */     return true;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.JideSplitPaneLayout
 * JD-Core Version:    0.6.0
 */
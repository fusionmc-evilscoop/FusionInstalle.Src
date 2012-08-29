/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.ConvolveOp;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.Kernel;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyChangeSupport;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class ShadowFactory
/*     */ {
/*     */   public static final String KEY_BLUR_QUALITY = "blur_quality";
/*     */   public static final String VALUE_BLUR_QUALITY_FAST = "fast";
/*     */   public static final String VALUE_BLUR_QUALITY_HIGH = "high";
/*     */   public static final String SIZE_CHANGED_PROPERTY = "shadow_size";
/*     */   public static final String OPACITY_CHANGED_PROPERTY = "shadow_opacity";
/*     */   public static final String COLOR_CHANGED_PROPERTY = "shadow_color";
/* 100 */   private int size = 5;
/*     */ 
/* 103 */   private float opacity = 0.5F;
/*     */ 
/* 106 */   private Color color = Color.BLACK;
/*     */   private HashMap hints;
/*     */   private PropertyChangeSupport changeSupport;
/*     */ 
/*     */   public ShadowFactory()
/*     */   {
/* 121 */     this(5, 0.5F, Color.BLACK);
/*     */   }
/*     */ 
/*     */   public ShadowFactory(int size, float opacity, Color color)
/*     */   {
/* 137 */     this.hints = new HashMap();
/* 138 */     this.hints.put("blur_quality", "fast");
/*     */ 
/* 140 */     this.changeSupport = new PropertyChangeSupport(this);
/*     */ 
/* 142 */     setSize(size);
/* 143 */     setOpacity(opacity);
/* 144 */     setColor(color);
/*     */   }
/*     */ 
/*     */   public void addPropertyChangeListener(PropertyChangeListener listener)
/*     */   {
/* 155 */     this.changeSupport.addPropertyChangeListener(listener);
/*     */   }
/*     */ 
/*     */   public void removePropertyChangeListener(PropertyChangeListener listener)
/*     */   {
/* 167 */     this.changeSupport.removePropertyChangeListener(listener);
/*     */   }
/*     */ 
/*     */   public void setRenderingHint(Object key, Object value)
/*     */   {
/* 178 */     this.hints.put(key, value);
/*     */   }
/*     */ 
/*     */   public Color getColor()
/*     */   {
/* 187 */     return this.color;
/*     */   }
/*     */ 
/*     */   public void setColor(Color shadowColor)
/*     */   {
/* 198 */     if (shadowColor != null) {
/* 199 */       Color oldColor = this.color;
/* 200 */       this.color = shadowColor;
/* 201 */       this.changeSupport.firePropertyChange("shadow_color", oldColor, this.color);
/*     */     }
/*     */   }
/*     */ 
/*     */   public float getOpacity()
/*     */   {
/* 214 */     return this.opacity;
/*     */   }
/*     */ 
/*     */   public void setOpacity(float shadowOpacity)
/*     */   {
/* 226 */     float oldOpacity = this.opacity;
/*     */ 
/* 228 */     if (shadowOpacity < 0.0D) {
/* 229 */       this.opacity = 0.0F;
/*     */     }
/* 231 */     else if (shadowOpacity > 1.0F) {
/* 232 */       this.opacity = 1.0F;
/*     */     }
/*     */     else {
/* 235 */       this.opacity = shadowOpacity;
/*     */     }
/*     */ 
/* 238 */     this.changeSupport.firePropertyChange("shadow_opacity", Float.valueOf(oldOpacity), Float.valueOf(this.opacity));
/*     */   }
/*     */ 
/*     */   public int getSize()
/*     */   {
/* 249 */     return this.size;
/*     */   }
/*     */ 
/*     */   public void setSize(int shadowSize)
/*     */   {
/* 263 */     int oldSize = this.size;
/*     */ 
/* 265 */     if (shadowSize < 0) {
/* 266 */       this.size = 0;
/*     */     }
/*     */     else {
/* 269 */       this.size = shadowSize;
/*     */     }
/*     */ 
/* 272 */     this.changeSupport.firePropertyChange("shadow_size", new Integer(oldSize), new Integer(this.size));
/*     */   }
/*     */ 
/*     */   public BufferedImage createShadow(BufferedImage image)
/*     */   {
/* 287 */     if (this.hints.get("blur_quality") == "high")
/*     */     {
/* 292 */       BufferedImage subject = prepareImage(image);
/* 293 */       BufferedImage shadow = new BufferedImage(subject.getWidth(), subject.getHeight(), 2);
/*     */ 
/* 296 */       BufferedImage shadowMask = createShadowMask(subject);
/* 297 */       getLinearBlurOp(this.size).filter(shadowMask, shadow);
/* 298 */       return shadow;
/*     */     }
/*     */ 
/* 302 */     return createShadowFast(image);
/*     */   }
/*     */ 
/*     */   private BufferedImage prepareImage(BufferedImage image)
/*     */   {
/* 307 */     BufferedImage subject = new BufferedImage(image.getWidth() + this.size * 2, image.getHeight() + this.size * 2, 2);
/*     */ 
/* 311 */     Graphics2D g2 = subject.createGraphics();
/* 312 */     g2.drawImage(image, null, this.size, this.size);
/* 313 */     g2.dispose();
/*     */ 
/* 315 */     return subject;
/*     */   }
/*     */ 
/*     */   private BufferedImage createShadowFast(BufferedImage src)
/*     */   {
/* 324 */     int shadowSize = this.size;
/*     */ 
/* 326 */     int srcWidth = src.getWidth();
/* 327 */     int srcHeight = src.getHeight();
/*     */ 
/* 329 */     int dstWidth = srcWidth + this.size;
/* 330 */     int dstHeight = srcHeight + this.size;
/*     */ 
/* 332 */     int left = shadowSize - 1 >> 1;
/* 333 */     int right = shadowSize - left;
/*     */ 
/* 335 */     int yStop = dstHeight - right;
/*     */ 
/* 337 */     BufferedImage dst = new BufferedImage(dstWidth, dstHeight, 2);
/*     */ 
/* 340 */     int shadowRgb = this.color.getRGB() & 0xFFFFFF;
/*     */ 
/* 342 */     int[] aHistory = new int[shadowSize];
/*     */ 
/* 347 */     ColorModel srcColorModel = src.getColorModel();
/* 348 */     WritableRaster srcRaster = src.getRaster();
/* 349 */     int[] dstBuffer = ((DataBufferInt)dst.getRaster().getDataBuffer()).getData();
/*     */ 
/* 351 */     int lastPixelOffset = right * dstWidth;
/* 352 */     float hSumDivider = 1.0F / this.size;
/* 353 */     float vSumDivider = this.opacity / this.size;
/*     */ 
/* 357 */     int srcY = 0; for (int dstOffset = left * dstWidth; srcY < srcHeight; srcY++)
/*     */     {
/* 360 */       for (int historyIdx = 0; historyIdx < shadowSize; ) {
/* 361 */         aHistory[(historyIdx++)] = 0;
/*     */       }
/*     */ 
/* 364 */       int aSum = 0;
/* 365 */       historyIdx = 0;
/*     */ 
/* 368 */       for (int srcX = 0; srcX < srcWidth; srcX++)
/*     */       {
/* 370 */         int a = (int)(aSum * hSumDivider);
/* 371 */         dstBuffer[(dstOffset++)] = (a << 24);
/*     */ 
/* 374 */         aSum -= aHistory[historyIdx];
/*     */ 
/* 377 */         a = srcColorModel.getAlpha(srcRaster.getDataElements(srcX, srcY, null));
/* 378 */         aHistory[historyIdx] = a;
/* 379 */         aSum += a;
/*     */ 
/* 381 */         historyIdx++; if (historyIdx >= shadowSize) {
/* 382 */           historyIdx -= shadowSize;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 387 */       for (int i = 0; i < shadowSize; i++)
/*     */       {
/* 389 */         int a = (int)(aSum * hSumDivider);
/* 390 */         dstBuffer[(dstOffset++)] = (a << 24);
/*     */ 
/* 393 */         aSum -= aHistory[historyIdx];
/*     */ 
/* 395 */         historyIdx++; if (historyIdx >= shadowSize) {
/* 396 */           historyIdx -= shadowSize;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 402 */     int x = 0; for (int bufferOffset = 0; x < dstWidth; bufferOffset = x)
/*     */     {
/* 404 */       int aSum = 0;
/*     */ 
/* 407 */       for (int historyIdx = 0; historyIdx < left; ) {
/* 408 */         aHistory[(historyIdx++)] = 0;
/*     */       }
/*     */ 
/* 412 */       for (int y = 0; y < right; bufferOffset += dstWidth) {
/* 413 */         int a = dstBuffer[bufferOffset] >>> 24;
/* 414 */         aHistory[(historyIdx++)] = a;
/* 415 */         aSum += a;
/*     */ 
/* 412 */         y++;
/*     */       }
/*     */ 
/* 418 */       bufferOffset = x;
/* 419 */       historyIdx = 0;
/*     */ 
/* 422 */       for (int y = 0; y < yStop; bufferOffset += dstWidth)
/*     */       {
/* 424 */         int a = (int)(aSum * vSumDivider);
/* 425 */         dstBuffer[bufferOffset] = (a << 24 | shadowRgb);
/*     */ 
/* 427 */         aSum -= aHistory[historyIdx];
/*     */ 
/* 429 */         a = dstBuffer[(bufferOffset + lastPixelOffset)] >>> 24;
/* 430 */         aHistory[historyIdx] = a;
/* 431 */         aSum += a;
/*     */ 
/* 433 */         historyIdx++; if (historyIdx >= shadowSize)
/* 434 */           historyIdx -= shadowSize;
/* 422 */         y++;
/*     */       }
/*     */ 
/* 439 */       for (int y = yStop; y < dstHeight; bufferOffset += dstWidth)
/*     */       {
/* 441 */         int a = (int)(aSum * vSumDivider);
/* 442 */         dstBuffer[bufferOffset] = (a << 24 | shadowRgb);
/*     */ 
/* 444 */         aSum -= aHistory[historyIdx];
/*     */ 
/* 446 */         historyIdx++; if (historyIdx >= shadowSize)
/* 447 */           historyIdx -= shadowSize;
/* 439 */         y++;
/*     */       }
/* 402 */       x++;
/*     */     }
/*     */ 
/* 452 */     return dst;
/*     */   }
/*     */ 
/*     */   private BufferedImage createShadowMask(BufferedImage image)
/*     */   {
/* 459 */     BufferedImage mask = new BufferedImage(image.getWidth(), image.getHeight(), 2);
/*     */ 
/* 463 */     Graphics2D g2d = mask.createGraphics();
/* 464 */     g2d.drawImage(image, 0, 0, null);
/* 465 */     g2d.setComposite(AlphaComposite.getInstance(5, this.opacity));
/*     */ 
/* 467 */     g2d.setColor(this.color);
/* 468 */     g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
/* 469 */     g2d.dispose();
/*     */ 
/* 471 */     return mask;
/*     */   }
/*     */ 
/*     */   private ConvolveOp getLinearBlurOp(int size)
/*     */   {
/* 477 */     float[] data = new float[size * size];
/* 478 */     float value = 1.0F / (size * size);
/* 479 */     for (int i = 0; i < data.length; i++) {
/* 480 */       data[i] = value;
/*     */     }
/* 482 */     return new ConvolveOp(new Kernel(size, size, data));
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.ShadowFactory
 * JD-Core Version:    0.6.0
 */
/*     */ package com.jidesoft.icons;
/*     */ 
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import com.jidesoft.utils.SecurityUtils;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Image;
/*     */ import java.awt.Insets;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.swing.GrayFilter;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.ImageIcon;
/*     */ 
/*     */ public class IconsFactory
/*     */ {
/* 115 */   static Map<String, ImageIcon> icons = new HashMap();
/* 116 */   static Map<String, ImageIcon> disableIcons = new HashMap();
/* 117 */   static Map<String, ImageIcon> enhancedIcons = new HashMap();
/*     */ 
/* 119 */   public static ImageIcon EMPTY_ICON = new ImageIcon() {
/*     */     private static final long serialVersionUID = 5081581607741629368L;
/*     */ 
/*     */     public int getIconHeight() {
/* 124 */       return 16;
/*     */     }
/*     */ 
/*     */     public int getIconWidth()
/*     */     {
/* 129 */       return 16;
/*     */     }
/*     */ 
/*     */     public synchronized void paintIcon(Component c, Graphics g, int x, int y)
/*     */     {
/*     */     }
/* 119 */   };
/*     */   static final double DEGREE_90 = 1.570796326794897D;
/*     */ 
/*     */   public static ImageIcon getImageIcon(Class<?> clazz, String fileName)
/*     */   {
/* 151 */     String id = clazz.getName() + ":" + fileName;
/* 152 */     Icon saved = (Icon)icons.get(id);
/* 153 */     if (saved != null) {
/* 154 */       return (ImageIcon)saved;
/*     */     }
/* 156 */     ImageIcon icon = createImageIcon(clazz, fileName);
/* 157 */     icons.put(id, icon);
/* 158 */     return icon;
/*     */   }
/*     */ 
/*     */   public static ImageIcon findImageIcon(Class<?> clazz, String fileName)
/*     */     throws IOException
/*     */   {
/* 172 */     String id = clazz.getName() + ":" + fileName;
/* 173 */     ImageIcon saved = (ImageIcon)icons.get(id);
/* 174 */     if (saved != null) {
/* 175 */       return saved;
/*     */     }
/* 177 */     ImageIcon icon = createImageIconWithException(clazz, fileName);
/* 178 */     icons.put(id, icon);
/* 179 */     return icon;
/*     */   }
/*     */ 
/*     */   public static ImageIcon getDisabledImageIcon(Class<?> clazz, String fileName)
/*     */   {
/* 191 */     String id = clazz.getName() + ":" + fileName;
/* 192 */     ImageIcon saved = (ImageIcon)disableIcons.get(id);
/* 193 */     if (saved != null) {
/* 194 */       return saved;
/*     */     }
/* 196 */     ImageIcon icon = createGrayImage(getImageIcon(clazz, fileName));
/* 197 */     disableIcons.put(id, icon);
/* 198 */     return icon;
/*     */   }
/*     */ 
/*     */   public static ImageIcon getBrighterImageIcon(Class<?> clazz, String fileName)
/*     */   {
/* 210 */     String id = clazz.getName() + ":" + fileName;
/* 211 */     ImageIcon saved = (ImageIcon)enhancedIcons.get(id);
/* 212 */     if (saved != null) {
/* 213 */       return saved;
/*     */     }
/* 215 */     ImageIcon icon = createBrighterImage(getImageIcon(clazz, fileName));
/* 216 */     enhancedIcons.put(id, icon);
/* 217 */     return icon;
/*     */   }
/*     */ 
/*     */   public static ImageIcon getBrighterImageIcon(Class<?> clazz, String fileName, int percent)
/*     */   {
/* 230 */     String id = clazz.getName() + ":" + fileName;
/* 231 */     ImageIcon saved = (ImageIcon)enhancedIcons.get(id);
/* 232 */     if (saved != null) {
/* 233 */       return saved;
/*     */     }
/* 235 */     ImageIcon icon = createBrighterImage(getImageIcon(clazz, fileName), percent);
/* 236 */     enhancedIcons.put(id, icon);
/* 237 */     return icon;
/*     */   }
/*     */ 
/*     */   public static ImageIcon createGrayImage(Image image)
/*     */   {
/* 250 */     if (image == null)
/* 251 */       return EMPTY_ICON;
/* 252 */     return new ImageIcon(GrayFilter.createDisabledImage(image));
/*     */   }
/*     */ 
/*     */   private static ImageIcon createGrayImage(ImageIcon icon)
/*     */   {
/* 263 */     if (icon == null)
/* 264 */       return EMPTY_ICON;
/* 265 */     return new ImageIcon(GrayFilter.createDisabledImage(icon.getImage()));
/*     */   }
/*     */ 
/*     */   public static ImageIcon createGrayImage(Component c, Icon icon)
/*     */   {
/* 277 */     if (icon == null) {
/* 278 */       return EMPTY_ICON;
/*     */     }
/* 280 */     int w = icon.getIconWidth(); int h = icon.getIconHeight();
/* 281 */     if ((w == 0) || (h == 0)) {
/* 282 */       return EMPTY_ICON;
/*     */     }
/* 284 */     BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), 2);
/* 285 */     icon.paintIcon(c, image.getGraphics(), 0, 0);
/* 286 */     return new ImageIcon(GrayFilter.createDisabledImage(image));
/*     */   }
/*     */ 
/*     */   public static ImageIcon createBrighterImage(Image image)
/*     */   {
/* 296 */     if (image == null)
/* 297 */       return EMPTY_ICON;
/* 298 */     return new ImageIcon(ColorFilter.createBrighterImage(image));
/*     */   }
/*     */ 
/*     */   public static ImageIcon createBrighterImage(Image image, int percent)
/*     */   {
/* 310 */     if (image == null)
/* 311 */       return EMPTY_ICON;
/* 312 */     return new ImageIcon(ColorFilter.createBrighterImage(image, percent));
/*     */   }
/*     */ 
/*     */   public static ImageIcon createBrighterImage(Component c, Icon icon)
/*     */   {
/* 325 */     if (icon == null)
/* 326 */       return EMPTY_ICON;
/* 327 */     BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), 2);
/* 328 */     icon.paintIcon(c, image.getGraphics(), 0, 0);
/* 329 */     return new ImageIcon(ColorFilter.createBrighterImage(image));
/*     */   }
/*     */ 
/*     */   public static ImageIcon createBrighterImage(Component c, Icon icon, int percent)
/*     */   {
/* 342 */     if (icon == null)
/* 343 */       return EMPTY_ICON;
/* 344 */     BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), 2);
/* 345 */     icon.paintIcon(c, image.getGraphics(), 0, 0);
/* 346 */     return new ImageIcon(ColorFilter.createBrighterImage(image, percent));
/*     */   }
/*     */ 
/*     */   public static ImageIcon createBrighterImage(ImageIcon icon)
/*     */   {
/* 356 */     if (icon == null)
/* 357 */       return EMPTY_ICON;
/* 358 */     return new ImageIcon(ColorFilter.createBrighterImage(icon.getImage()));
/*     */   }
/*     */ 
/*     */   public static ImageIcon createBrighterImage(ImageIcon icon, int percent)
/*     */   {
/* 370 */     if (icon == null)
/* 371 */       return EMPTY_ICON;
/* 372 */     return new ImageIcon(ColorFilter.createBrighterImage(icon.getImage(), percent));
/*     */   }
/*     */ 
/*     */   public static ImageIcon createNegativeImage(Image image)
/*     */   {
/* 384 */     if (image == null)
/* 385 */       return EMPTY_ICON;
/* 386 */     return new ImageIcon(MaskFilter.createNegativeImage(image));
/*     */   }
/*     */ 
/*     */   public static ImageIcon createMaskImage(Component c, Icon icon, Color oldColor, Color newColor)
/*     */   {
/* 399 */     BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), 2);
/* 400 */     icon.paintIcon(c, image.getGraphics(), 0, 0);
/* 401 */     return new ImageIcon(MaskFilter.createImage(image, oldColor, newColor));
/*     */   }
/*     */ 
/*     */   public static ImageIcon createRotatedImage(Component c, Icon icon, double rotatedAngle)
/*     */   {
/* 418 */     double originalAngle = rotatedAngle % 360.0D;
/* 419 */     if ((rotatedAngle != 0.0D) && (originalAngle == 0.0D)) {
/* 420 */       originalAngle = 360.0D;
/*     */     }
/*     */ 
/* 424 */     double angle = originalAngle % 90.0D;
/* 425 */     if ((originalAngle != 0.0D) && (angle == 0.0D)) {
/* 426 */       angle = 90.0D;
/*     */     }
/*     */ 
/* 429 */     double radian = Math.toRadians(angle);
/*     */ 
/* 431 */     int iw = icon.getIconWidth();
/* 432 */     int ih = icon.getIconHeight();
/*     */     int h;
/*     */     int w;
/*     */     int h;
/* 436 */     if (((originalAngle >= 0.0D) && (originalAngle <= 90.0D)) || ((originalAngle > 180.0D) && (originalAngle <= 270.0D))) {
/* 437 */       int w = (int)Math.round(iw * Math.sin(1.570796326794897D - radian) + ih * Math.sin(radian));
/* 438 */       h = (int)Math.round(iw * Math.sin(radian) + ih * Math.sin(1.570796326794897D - radian));
/*     */     }
/*     */     else {
/* 441 */       w = (int)(ih * Math.sin(1.570796326794897D - radian) + iw * Math.sin(radian));
/* 442 */       h = (int)(ih * Math.sin(radian) + iw * Math.sin(1.570796326794897D - radian));
/*     */     }
/*     */ 
/* 445 */     BufferedImage image = new BufferedImage(w, h, 2);
/* 446 */     Graphics g = image.getGraphics();
/* 447 */     Graphics2D g2d = (Graphics2D)g.create();
/*     */ 
/* 450 */     int cx = iw / 2;
/* 451 */     int cy = ih / 2;
/*     */ 
/* 456 */     int xOffset = (iw % 2 != 0) && (originalAngle >= 90.0D) && (originalAngle <= 180.0D) ? 1 : 0;
/*     */ 
/* 458 */     int yOffset = (iw % 2 != 0) && (originalAngle >= 180.0D) && (originalAngle < 360.0D) ? 1 : 0;
/*     */ 
/* 462 */     g2d.translate(w / 2 + xOffset, h / 2 + yOffset);
/*     */ 
/* 465 */     g2d.rotate(Math.toRadians(originalAngle));
/*     */ 
/* 467 */     g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
/*     */ 
/* 470 */     icon.paintIcon(c, g2d, -cx, -cy);
/*     */ 
/* 472 */     g2d.dispose();
/* 473 */     return new ImageIcon(image);
/*     */   }
/*     */ 
/*     */   public static ImageIcon createNegativeImage(Component c, Icon icon)
/*     */   {
/* 484 */     BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), 2);
/* 485 */     icon.paintIcon(c, image.getGraphics(), 0, 0);
/* 486 */     return new ImageIcon(MaskFilter.createNegativeImage(image));
/*     */   }
/*     */ 
/*     */   private static ImageIcon createImageIcon(Class<?> baseClass, String file) {
/*     */     try {
/* 491 */       return createImageIconWithException(baseClass, file);
/*     */     }
/*     */     catch (IOException e) {
/* 494 */       System.err.println(e.getLocalizedMessage());
/* 495 */     }return null;
/*     */   }
/*     */ 
/*     */   private static ImageIcon createImageIconWithException(Class<?> baseClass, String file) throws IOException
/*     */   {
/* 500 */     InputStream resource = baseClass.getResourceAsStream(file);
/* 501 */     if (resource == null)
/* 502 */       throw new FileNotFoundException(file);
/*     */     Image image;
/*     */     Image image;
/* 506 */     if ("true".equals(SecurityUtils.getProperty("jide.useImageIO", "true"))) {
/* 507 */       image = ImageIO.read(resource);
/*     */     }
/*     */     else {
/* 510 */       image = readImageIcon(baseClass, file, resource);
/*     */     }
/* 512 */     resource.close();
/* 513 */     return new ImageIcon(image);
/*     */   }
/*     */ 
/*     */   private static Image readImageIcon(Class clazz, String file, InputStream resource)
/*     */     throws IOException
/*     */   {
/* 519 */     byte[][] buffer = new byte[1][];
/*     */     try {
/* 521 */       BufferedInputStream in = new BufferedInputStream(resource);
/* 522 */       ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
/*     */ 
/* 524 */       buffer[0] = new byte[1024];
/*     */       int n;
/* 526 */       while ((n = in.read(buffer[0])) > 0)
/*     */       {
/* 528 */         out.write(buffer[0], 0, n);
/*     */       }
/* 530 */       in.close();
/* 531 */       out.flush();
/* 532 */       buffer[0] = out.toByteArray();
/*     */     }
/*     */     catch (IOException ioe) {
/* 535 */       throw ioe;
/*     */     }
/*     */ 
/* 538 */     if ((buffer[0] == null) || (buffer[0].length == 0)) {
/* 539 */       Package pkg = clazz.getPackage();
/* 540 */       String pkgName = "";
/* 541 */       if (pkg != null) {
/* 542 */         pkgName = pkg.getName().replace('.', '/');
/*     */       }
/* 544 */       if (buffer[0] == null) {
/* 545 */         throw new IOException("Warning: Resource " + pkgName + "/" + file + " not found.");
/*     */       }
/* 547 */       if (buffer[0].length == 0) {
/* 548 */         throw new IOException("Warning: Resource " + pkgName + "/" + file + " is zero-length");
/*     */       }
/*     */     }
/*     */ 
/* 552 */     return Toolkit.getDefaultToolkit().createImage(buffer[0]);
/*     */   }
/*     */ 
/*     */   public static void generateHTML(Class<?> clazz)
/*     */   {
/* 561 */     String fullClassName = clazz.getName();
/* 562 */     String className = getClassName(fullClassName);
/* 563 */     File file = new File(fullClassName + ".html");
/*     */     try
/*     */     {
/* 566 */       FileWriter writer = new FileWriter(file);
/*     */       try {
/* 568 */         writer.write("<html>\n<body>\n<p><b><font size=\"5\" face=\"Verdana\">Icons in " + fullClassName + "</font></b></p>");
/*     */ 
/* 570 */         writer.write("<p><b><font size=\"3\" face=\"Verdana\">Generated by JIDE Icons</font></b></p>");
/* 571 */         writer.write("<p><b><font size=\"3\" color=\"#AAAAAA\" face=\"Verdana\">1. If you cannot view the images in this page, make sure the file is at the same directory as " + className + ".java</font></b></p>");
/*     */ 
/* 573 */         writer.write("<p><b><font size=\"3\" color=\"#AAAAAA\" face=\"Verdana\">2. To get a particular icon in your code, call " + className + ".getImageIcon(FULL_CONSTANT_NAME). Replace FULL_CONSTANT_NAME with the actual " + "full constant name as in the table below" + "</font></b></p>");
/*     */ 
/* 576 */         generate(clazz, writer, className);
/* 577 */         writer.write("\n</body>\n</html>");
/*     */       }
/*     */       catch (IOException e) {
/* 580 */         System.err.println(e);
/*     */       }
/*     */       finally {
/* 583 */         writer.close();
/*     */       }
/* 585 */       System.out.println("File is generated at \"" + file.getAbsolutePath() + "\". Please copy it to the same directory as " + className + ".java");
/*     */     }
/*     */     catch (IOException e) {
/* 588 */       System.err.println(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String getClassName(String fullName) {
/* 593 */     int last = fullName.lastIndexOf(".");
/* 594 */     if (last != -1) {
/* 595 */       fullName = fullName.substring(last + 1);
/*     */     }
/* 597 */     StringTokenizer tokenizer = new StringTokenizer(fullName, "$");
/* 598 */     StringBuffer buffer = new StringBuffer();
/* 599 */     while (tokenizer.hasMoreTokens()) {
/* 600 */       buffer.append(tokenizer.nextToken());
/* 601 */       buffer.append(".");
/*     */     }
/* 603 */     return buffer.substring(0, buffer.length() - 1);
/*     */   }
/*     */ 
/*     */   private static void generate(Class<?> aClass, FileWriter writer, String prefix) throws IOException {
/* 607 */     Class[] classes = aClass.getDeclaredClasses();
/*     */ 
/* 609 */     for (int i = classes.length - 1; i >= 0; i--) {
/* 610 */       Class clazz = classes[i];
/* 611 */       generate(clazz, writer, getClassName(clazz.getName()));
/*     */     }
/*     */ 
/* 614 */     Field[] fields = aClass.getFields();
/* 615 */     writer.write("<p><font face=\"Verdana\"><b>" + prefix + "</b></font></p>");
/* 616 */     writer.write("<table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"#CCCCCC\" width=\"66%\">");
/* 617 */     writer.write("<tr>\n");
/* 618 */     writer.write("<td width=\"24%\" align=\"center\"><b><font face=\"Verdana\" color=\"#003399\">Name</font></b></td>\n");
/* 619 */     writer.write("<td width=\"13%\" align=\"center\"><b><font face=\"Verdana\" color=\"#003399\">Image</font></b></td>\n");
/* 620 */     writer.write("<td width=\"32%\" align=\"center\"><b><font face=\"Verdana\" color=\"#003399\">File Name</font></b></td>\n");
/* 621 */     writer.write("<td width=\"31%\" align=\"center\"><b><font face=\"Verdana\" color=\"#003399\">Full Constant Name</font></b></td>\n");
/* 622 */     writer.write("</tr>\n");
/* 623 */     for (Field field : fields) {
/*     */       try {
/* 625 */         Object name = field.getName();
/* 626 */         Object value = field.get(aClass);
/* 627 */         writer.write("<tr>\n");
/* 628 */         writer.write("<td align=\"left\"><font face=\"Verdana\">" + name + "</font></td>\n");
/* 629 */         writer.write("<td align=\"center\"><font face=\"Verdana\"><img border=\"0\" src=\"" + value + "\"></font></td>\n");
/* 630 */         writer.write("<td align=\"left\"><font face=\"Verdana\">" + value + "</font></td>\n");
/* 631 */         writer.write("<td align=\"left\"><font face=\"Verdana\">" + prefix + "." + name + "</font></td>\n");
/* 632 */         writer.write("</tr>\n");
/*     */       }
/*     */       catch (IllegalArgumentException e) {
/* 635 */         e.printStackTrace();
/*     */       }
/*     */       catch (IllegalAccessException e) {
/* 638 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 641 */     writer.write("</table><br><p>\n");
/*     */   }
/*     */ 
/*     */   public static ImageIcon getIcon(Component c, ImageIcon icon, int x, int y, int width, int height)
/*     */   {
/* 658 */     return getIcon(c, icon, x, y, width, height, width, height);
/*     */   }
/*     */ 
/*     */   public static ImageIcon getIcon(Component c, ImageIcon icon, int x, int y, int width, int height, int destWidth, int destHeight)
/*     */   {
/* 680 */     return getIcon(c, icon, x, y, width, height, 2, destWidth, destHeight);
/*     */   }
/*     */ 
/*     */   public static ImageIcon getIcon(Component c, ImageIcon icon, int x, int y, int width, int height, int imageType)
/*     */   {
/* 700 */     return getIcon(c, icon, x, y, width, height, imageType, width, height);
/*     */   }
/*     */ 
/*     */   public static ImageIcon getIcon(Component c, ImageIcon icon, int x, int y, int width, int height, int imageType, int destWidth, int destHeight)
/*     */   {
/* 725 */     if ((icon == null) || (x < 0) || (x + width > icon.getIconWidth()) || (y < 0) || (y + height > icon.getIconHeight())) {
/* 726 */       return EMPTY_ICON;
/*     */     }
/* 728 */     BufferedImage image = new BufferedImage(destWidth, destHeight, imageType);
/* 729 */     image.getGraphics().drawImage(icon.getImage(), 0, 0, destWidth, destHeight, x, y, x + width, y + height, c);
/* 730 */     return new ImageIcon(image);
/*     */   }
/*     */ 
/*     */   public static ImageIcon getOverlayIcon(Component c, ImageIcon icon, ImageIcon overlayIcon, int location)
/*     */   {
/* 745 */     return getOverlayIcon(c, icon, overlayIcon, location, new Insets(0, 0, 0, 0));
/*     */   }
/*     */ 
/*     */   public static ImageIcon getOverlayIcon(Component c, ImageIcon icon, ImageIcon overlayIcon, int location, Insets insets)
/*     */   {
/* 763 */     int x = -1; int y = -1;
/* 764 */     int w = icon.getIconWidth();
/* 765 */     int h = icon.getIconHeight();
/* 766 */     int sw = overlayIcon.getIconWidth();
/* 767 */     int sh = overlayIcon.getIconHeight();
/* 768 */     switch (location) {
/*     */     case 0:
/* 770 */       x = (w - sw) / 2;
/* 771 */       y = (h - sh) / 2;
/* 772 */       break;
/*     */     case 1:
/* 774 */       x = (w - sw) / 2;
/* 775 */       y = insets.top;
/* 776 */       break;
/*     */     case 5:
/* 778 */       x = (w - sw) / 2;
/* 779 */       y = h - insets.bottom - sh;
/* 780 */       break;
/*     */     case 7:
/* 782 */       x = insets.left;
/* 783 */       y = (h - sh) / 2;
/* 784 */       break;
/*     */     case 3:
/* 786 */       x = w - insets.right - sw;
/* 787 */       y = (h - sh) / 2;
/* 788 */       break;
/*     */     case 2:
/* 790 */       x = w - insets.right - sw;
/* 791 */       y = insets.top;
/* 792 */       break;
/*     */     case 8:
/* 794 */       x = insets.left;
/* 795 */       y = insets.top;
/* 796 */       break;
/*     */     case 6:
/* 798 */       x = insets.left;
/* 799 */       y = h - insets.bottom - sh;
/* 800 */       break;
/*     */     case 4:
/* 802 */       x = w - insets.right - sw;
/* 803 */       y = h - insets.bottom - sh;
/*     */     }
/*     */ 
/* 806 */     return getOverlayIcon(c, icon, overlayIcon, x, y);
/*     */   }
/*     */ 
/*     */   public static ImageIcon getOverlayIcon(Component c, ImageIcon icon, ImageIcon overlayIcon, int x, int y)
/*     */   {
/* 821 */     int w = icon == null ? overlayIcon.getIconWidth() : icon.getIconWidth();
/* 822 */     int h = icon == null ? overlayIcon.getIconHeight() : icon.getIconHeight();
/* 823 */     int sw = overlayIcon.getIconWidth();
/* 824 */     int sh = overlayIcon.getIconHeight();
/* 825 */     if ((x != -1) && (y != -1)) {
/* 826 */       BufferedImage image = new BufferedImage(w, h, 2);
/* 827 */       if (icon != null) {
/* 828 */         image.getGraphics().drawImage(icon.getImage(), 0, 0, w, h, c);
/*     */       }
/* 830 */       image.getGraphics().drawImage(overlayIcon.getImage(), x, y, sw, sh, c);
/* 831 */       return new ImageIcon(image);
/*     */     }
/*     */ 
/* 834 */     return icon;
/*     */   }
/*     */ 
/*     */   public static ImageIcon getCombinedIcon(Component c, ImageIcon icon1, ImageIcon icon2, int orientation, int gap)
/*     */   {
/* 850 */     if (icon1 == null) {
/* 851 */       return icon2;
/*     */     }
/* 853 */     if (icon2 == null) {
/* 854 */       return icon1;
/*     */     }
/*     */ 
/* 857 */     int w1 = icon1.getIconWidth();
/* 858 */     int h1 = icon1.getIconHeight();
/* 859 */     int w2 = icon2.getIconWidth();
/* 860 */     int h2 = icon2.getIconHeight();
/*     */     int y2;
/*     */     int width;
/*     */     int height;
/*     */     int x1;
/*     */     int x2;
/*     */     int y1;
/*     */     int y2;
/* 862 */     if (orientation == 0) {
/* 863 */       int width = w1 + w2 + gap;
/* 864 */       int height = Math.max(h1, h2);
/* 865 */       int x1 = 0;
/* 866 */       int x2 = w1 + gap;
/* 867 */       int y1 = h1 > h2 ? 0 : (h2 - h1) / 2;
/* 868 */       y2 = h1 < h2 ? 0 : (h1 - h2) / 2;
/*     */     }
/*     */     else {
/* 871 */       width = Math.max(w1, w2);
/* 872 */       height = h1 + h2 + gap;
/* 873 */       x1 = w1 > w2 ? 0 : (w2 - w1) / 2;
/* 874 */       x2 = w1 < w2 ? 0 : (w1 - w2) / 2;
/* 875 */       y1 = 0;
/* 876 */       y2 = h1 + gap;
/*     */     }
/*     */ 
/* 879 */     BufferedImage image = new BufferedImage(width, height, 2);
/* 880 */     image.getGraphics().drawImage(icon1.getImage(), x1, y1, w1, h1, c);
/* 881 */     image.getGraphics().drawImage(icon2.getImage(), x2, y2, w2, h2, c);
/* 882 */     return new ImageIcon(image);
/*     */   }
/*     */ 
/*     */   public static ImageIcon getScaledImage(Component c, ImageIcon icon, int w, int h)
/*     */   {
/* 896 */     if (w >= icon.getIconWidth() / 2) {
/* 897 */       BufferedImage temp = new BufferedImage(w, h, 2);
/* 898 */       Graphics2D g2 = temp.createGraphics();
/* 899 */       g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
/* 900 */       g2.drawImage(icon.getImage(), 0, 0, temp.getWidth(), temp.getHeight(), c);
/* 901 */       g2.dispose();
/* 902 */       return new ImageIcon(temp);
/*     */     }
/*     */ 
/* 905 */     BufferedImage temp = new BufferedImage(w, h, 2);
/* 906 */     Graphics2D g2 = temp.createGraphics();
/* 907 */     g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/* 908 */     g2.drawImage(icon.getImage(), 0, 0, temp.getWidth(), temp.getHeight(), c);
/* 909 */     g2.dispose();
/* 910 */     return new ImageIcon(JideSwingUtilities.getFasterScaledInstance(temp, w, h, RenderingHints.VALUE_INTERPOLATION_BILINEAR, true));
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.icons.IconsFactory
 * JD-Core Version:    0.6.0
 */
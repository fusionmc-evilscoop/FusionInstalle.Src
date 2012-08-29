/*     */ package com.jidesoft.icons;
/*     */ 
/*     */ import java.awt.Insets;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.swing.ImageIcon;
/*     */ 
/*     */ public class IconSetManager
/*     */ {
/*     */   private Map<String, IconSet> _availableStyles;
/*  29 */   private String _activeIconSetName = null;
/*  30 */   private IconSet _activeIconSet = null;
/*     */ 
/*     */   public IconSet findIconSet(String iconSetName)
/*     */   {
/*  39 */     if (this._availableStyles == null) {
/*  40 */       return null;
/*     */     }
/*  42 */     return (IconSet)this._availableStyles.get(iconSetName);
/*     */   }
/*     */ 
/*     */   public String getActiveIconSetName()
/*     */   {
/*  51 */     return this._activeIconSetName;
/*     */   }
/*     */ 
/*     */   public IconSet getActiveIconSet()
/*     */   {
/*  60 */     return this._activeIconSet;
/*     */   }
/*     */ 
/*     */   public void add(String name, int[] sizes, String packageName) {
/*  64 */     if (this._availableStyles == null) {
/*  65 */       this._availableStyles = new HashMap();
/*     */     }
/*  67 */     this._availableStyles.put(name, new IconSet(name, sizes, packageName));
/*  68 */     if (this._availableStyles.size() == 1)
/*  69 */       setActiveIconSetName(name);
/*     */   }
/*     */ 
/*     */   public void remove(String name)
/*     */   {
/*  74 */     if (this._availableStyles != null)
/*  75 */       this._availableStyles.remove(name);
/*     */   }
/*     */ 
/*     */   public void setActiveIconSetName(String activeIconSetName)
/*     */   {
/*  86 */     IconSet iconSet = findIconSet(activeIconSetName);
/*  87 */     if (iconSet == null) {
/*  88 */       throw new IllegalArgumentException("Icon set \"" + activeIconSetName + "\" not found");
/*     */     }
/*  90 */     this._activeIconSet = iconSet;
/*  91 */     this._activeIconSetName = activeIconSetName;
/*     */   }
/*     */ 
/*     */   public ImageIcon getImageIcon(String iconName)
/*     */   {
/* 101 */     return getImageIcon(iconName, 16);
/*     */   }
/*     */ 
/*     */   public ImageIcon getImageIcon(String iconName, int size)
/*     */   {
/* 112 */     String packageName = this._activeIconSet.getPackageName();
/* 113 */     int actualSize = this._activeIconSet.getNextAvailableSize(size);
/* 114 */     String fullIconName = packageName + "/png/" + actualSize + "x" + actualSize + "/" + iconName;
/* 115 */     ImageIcon icon = IconsFactory.getImageIcon(IconSetManager.class, fullIconName);
/* 116 */     if (actualSize == size) {
/* 117 */       return icon;
/*     */     }
/*     */ 
/* 120 */     return IconsFactory.getScaledImage(null, icon, size, size);
/*     */   }
/*     */ 
/*     */   public ImageIcon getOverlayImageIcon(String iconName, int size, String overlayIconName, int location)
/*     */   {
/* 134 */     return getOverlayImageIcon(iconName, size, overlayIconName, location, new Insets(0, 0, 0, 0));
/*     */   }
/*     */ 
/*     */   public ImageIcon getOverlayImageIcon(String iconName, int size, String overlayIconName, int location, Insets insets)
/*     */   {
/* 148 */     ImageIcon icon = getImageIcon(iconName, size);
/* 149 */     if (icon == null) {
/* 150 */       return null;
/*     */     }
/* 152 */     ImageIcon overlay = getImageIcon(overlayIconName, size);
/* 153 */     if (overlay == null) {
/* 154 */       return icon;
/*     */     }
/*     */ 
/* 158 */     overlay = IconsFactory.getIcon(null, overlay, size / 4, size / 4, size / 2, size / 2);
/*     */ 
/* 160 */     return IconsFactory.getOverlayIcon(null, icon, overlay, location, insets);
/*     */   }
/*     */ 
/*     */   public ImageIcon getOverlayImageIcon(String iconName, int size, String overlayIconName, int overlayIconSize, int location, Insets insets)
/*     */   {
/* 175 */     ImageIcon icon = getImageIcon(iconName, size);
/* 176 */     if (icon == null) {
/* 177 */       return null;
/*     */     }
/* 179 */     ImageIcon overlay = getImageIcon(overlayIconName, overlayIconSize);
/* 180 */     if (overlay == null) {
/* 181 */       return icon;
/*     */     }
/*     */ 
/* 184 */     return IconsFactory.getOverlayIcon(null, icon, overlay, location, insets);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.icons.IconSetManager
 * JD-Core Version:    0.6.0
 */
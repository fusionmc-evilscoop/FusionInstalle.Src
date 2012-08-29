/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.plaf.LookAndFeelFactory;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.filechooser.FileSystemView;
/*     */ 
/*     */ public class FolderChooser extends JFileChooser
/*     */ {
/*     */   private static final String uiClassID = "FolderChooserUI";
/*     */   private List<String> _recentList;
/*     */   public static final String PROPERTY_RECENTLIST = "recentList";
/*     */   public static final String PROPERTY_RECENTLIST_VISIBLE = "recentListVisible";
/*     */   public static final int BUTTON_ALL = -1;
/*     */   public static final int BUTTON_DELETE = 1;
/*     */   public static final int BUTTON_NEW = 2;
/*     */   public static final int BUTTON_REFRESH = 4;
/*     */   public static final int BUTTON_DESKTOP = 8;
/*     */   public static final int BUTTON_MY_DOCUMENTS = 16;
/*     */   public static final String PROPERTY_AVAILABLE_BUTTONS = "availableButtons";
/*     */   private int _availableButtons;
/*     */   private boolean _recentListVisible;
/*     */   private File _selectedFolder;
/*     */   public static final String PROPERTY_NAVIGATION_FIELD_VISIBLE = "navigationFieldVisible";
/*     */   private boolean _navigationFieldVisible;
/*     */ 
/*     */   public FolderChooser()
/*     */   {
/*  60 */     configurationDefaultFlags();
/*     */   }
/*     */ 
/*     */   public FolderChooser(String currentDirectoryPath) {
/*  64 */     super(currentDirectoryPath);
/*  65 */     configurationDefaultFlags();
/*     */   }
/*     */ 
/*     */   public FolderChooser(File currentDirectory) {
/*  69 */     super(currentDirectory);
/*  70 */     configurationDefaultFlags();
/*     */   }
/*     */ 
/*     */   public FolderChooser(FileSystemView fsv) {
/*  74 */     super(fsv);
/*  75 */     configurationDefaultFlags();
/*     */   }
/*     */ 
/*     */   public FolderChooser(File currentDirectory, FileSystemView fsv) {
/*  79 */     super(currentDirectory, fsv);
/*  80 */     configurationDefaultFlags();
/*     */   }
/*     */ 
/*     */   public FolderChooser(String currentDirectoryPath, FileSystemView fsv) {
/*  84 */     super(currentDirectoryPath, fsv);
/*  85 */     configurationDefaultFlags();
/*     */   }
/*     */ 
/*     */   private void configurationDefaultFlags() {
/*  89 */     setAvailableButtons(-1);
/*  90 */     setRecentListVisible(true);
/*  91 */     setNavigationFieldVisible(false);
/*     */   }
/*     */ 
/*     */   public List<String> getRecentList()
/*     */   {
/* 100 */     return this._recentList;
/*     */   }
/*     */ 
/*     */   public void setRecentList(List<String> recentList)
/*     */   {
/* 110 */     List old = this._recentList;
/* 111 */     this._recentList = new ArrayList();
/* 112 */     this._recentList.addAll(recentList);
/* 113 */     firePropertyChange("recentList", old, this._recentList);
/*     */   }
/*     */ 
/*     */   public void updateUI()
/*     */   {
/* 123 */     if (UIDefaultsLookup.get("FolderChooserUI") == null) {
/* 124 */       LookAndFeelFactory.installJideExtension();
/*     */     }
/* 126 */     JComponent c = getAccessory();
/* 127 */     if (c != null) {
/* 128 */       setAccessory(null);
/*     */     }
/* 130 */     setUI(UIManager.getUI(this));
/* 131 */     if (c != null)
/* 132 */       setAccessory(c);
/*     */   }
/*     */ 
/*     */   public String getUIClassID()
/*     */   {
/* 146 */     return "FolderChooserUI";
/*     */   }
/*     */ 
/*     */   public File getSelectedFolder()
/*     */   {
/* 199 */     return this._selectedFolder;
/*     */   }
/*     */ 
/*     */   public void setSelectedFolder(File selectedFolder)
/*     */   {
/* 210 */     File old = this._selectedFolder;
/* 211 */     if (!JideSwingUtilities.equals(old, selectedFolder)) {
/* 212 */       this._selectedFolder = selectedFolder;
/* 213 */       firePropertyChange("SelectedFileChangedProperty", old, this._selectedFolder);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setNavigationFieldVisible(boolean navigationFieldVisible)
/*     */   {
/* 252 */     boolean oldValue = this._navigationFieldVisible;
/* 253 */     if (!JideSwingUtilities.equals(Boolean.valueOf(oldValue), Boolean.valueOf(navigationFieldVisible))) {
/* 254 */       this._navigationFieldVisible = navigationFieldVisible;
/* 255 */       firePropertyChange("navigationFieldVisible", oldValue, this._navigationFieldVisible);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isNavigationFieldVisible()
/*     */   {
/* 265 */     return this._navigationFieldVisible;
/*     */   }
/*     */ 
/*     */   public int getAvailableButtons()
/*     */   {
/* 274 */     return this._availableButtons;
/*     */   }
/*     */ 
/*     */   public void setAvailableButtons(int availableButtons)
/*     */   {
/* 283 */     if (getAvailableButtons() == availableButtons) {
/* 284 */       return;
/*     */     }
/* 286 */     int oldValue = getAvailableButtons();
/* 287 */     this._availableButtons = availableButtons;
/* 288 */     firePropertyChange("availableButtons", oldValue, availableButtons);
/*     */   }
/*     */ 
/*     */   public boolean isRecentListVisible()
/*     */   {
/* 303 */     return this._recentListVisible;
/*     */   }
/*     */ 
/*     */   public void setRecentListVisible(boolean recentListVisible)
/*     */   {
/* 312 */     if (this._recentListVisible == recentListVisible) {
/* 313 */       return;
/*     */     }
/* 315 */     boolean oldValue = isRecentListVisible();
/* 316 */     this._recentListVisible = recentListVisible;
/* 317 */     firePropertyChange("recentListVisible", oldValue, recentListVisible);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.FolderChooser
 * JD-Core Version:    0.6.0
 */
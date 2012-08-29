/*     */ package com.jidesoft.plaf.basic;
/*     */ 
/*     */ import com.jidesoft.utils.SystemInfo;
/*     */ import java.awt.Component;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.ComboBoxModel;
/*     */ import javax.swing.DefaultComboBoxModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.ListCellRenderer;
/*     */ import javax.swing.event.PopupMenuEvent;
/*     */ import javax.swing.event.PopupMenuListener;
/*     */ import javax.swing.filechooser.FileSystemView;
/*     */ 
/*     */ public class FolderToolBar extends JToolBar
/*     */ {
/*  27 */   private static final FileSystemView _fsv = FileSystemView.getFileSystemView();
/*     */   private JButton _deleteFolderBtn;
/*     */   private JButton _newFolderBtn;
/*     */   private JButton _refreshBtn;
/*     */   private JButton _desktopBtn;
/*     */   private JButton _myDocumentsBtn;
/*     */   private JComboBox _recentFoldersList;
/*  36 */   private List<FolderToolBarListener> _listeners = new ArrayList(1);
/*     */   public static final String DELETE_BUTTON_NAME = "FolderChooser.toolbar.delete";
/*     */   public static final String NEW_BUTTON_NAME = "FolderChooser.toolbar.new";
/*     */   public static final String REFRESH_BUTTON_NAME = "FolderChooser.toolbar.refresh";
/*     */   public static final String DESKTOP_BUTTON_NAME = "FolderChooser.toolbar.desktop";
/*     */   public static final String MY_DOCUMENTS_BUTTON_NAME = "FolderChooser.toolbar.mydocuments";
/*     */ 
/*     */   public FolderToolBar(boolean showRecentFolders, List<String> recentFoldersList)
/*     */   {
/*  51 */     setFloatable(false);
/*  52 */     setupToolBar(showRecentFolders, recentFoldersList);
/*     */   }
/*     */ 
/*     */   public void enableDelete()
/*     */   {
/*  59 */     this._deleteFolderBtn.setEnabled(true);
/*     */   }
/*     */ 
/*     */   public void disableDelete()
/*     */   {
/*  66 */     this._deleteFolderBtn.setEnabled(false);
/*     */   }
/*     */ 
/*     */   public void enableNewFolder()
/*     */   {
/*  73 */     this._newFolderBtn.setEnabled(true);
/*     */   }
/*     */ 
/*     */   public void disableNewFolder()
/*     */   {
/*  80 */     this._newFolderBtn.setEnabled(false);
/*     */   }
/*     */ 
/*     */   public JButton getButton(String buttonName)
/*     */   {
/*  90 */     if (buttonName == null) {
/*  91 */       return null;
/*     */     }
/*  93 */     if (buttonName.equals("FolderChooser.toolbar.delete")) {
/*  94 */       return this._deleteFolderBtn;
/*     */     }
/*  96 */     if (buttonName.equals("FolderChooser.toolbar.new")) {
/*  97 */       return this._newFolderBtn;
/*     */     }
/*  99 */     if (buttonName.equals("FolderChooser.toolbar.refresh")) {
/* 100 */       return this._refreshBtn;
/*     */     }
/* 102 */     if (buttonName.equals("FolderChooser.toolbar.desktop")) {
/* 103 */       return this._desktopBtn;
/*     */     }
/* 105 */     if (buttonName.equals("FolderChooser.toolbar.mydocuments")) {
/* 106 */       return this._myDocumentsBtn;
/*     */     }
/* 108 */     return null;
/*     */   }
/*     */ 
/*     */   private void setupToolBar(boolean showRecentFolders, List<String> recentFoldersList)
/*     */   {
/* 121 */     if (showRecentFolders) {
/* 122 */       this._recentFoldersList = new JComboBox(new DefaultComboBoxModel());
/* 123 */       if ((recentFoldersList != null) && (recentFoldersList.size() > 0)) {
/* 124 */         this._recentFoldersList.setModel(new DefaultComboBoxModel(recentFoldersList.toArray()));
/*     */       }
/* 126 */       this._recentFoldersList.setEditable(false);
/* 127 */       this._recentFoldersList.setRenderer(new FileListCellRenderer(this._recentFoldersList.getRenderer(), null));
/* 128 */       this._recentFoldersList.addPopupMenuListener(new PopupMenuListener() {
/* 129 */         private boolean m_wasCancelled = false;
/*     */ 
/*     */         public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
/* 132 */           this.m_wasCancelled = false;
/*     */         }
/*     */ 
/*     */         public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
/* 136 */           if ((e.getSource() instanceof JComboBox)) {
/* 137 */             JComboBox box = (JComboBox)e.getSource();
/*     */ 
/* 139 */             Object selectedFile = box.getModel().getSelectedItem();
/*     */ 
/* 141 */             if ((!this.m_wasCancelled) && (selectedFile != null))
/*     */             {
/* 143 */               if ((selectedFile instanceof File)) {
/* 144 */                 FolderToolBar.this.recentFolderSelected((File)selectedFile);
/*     */               }
/*     */               else
/* 147 */                 FolderToolBar.this.recentFolderSelected(new File("" + selectedFile));
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/*     */         public void popupMenuCanceled(PopupMenuEvent e)
/*     */         {
/* 154 */           this.m_wasCancelled = true;
/*     */         }
/*     */       });
/* 157 */       this._recentFoldersList.setPrototypeDisplayValue("AAAAAAAAAAAAAAAAAA");
/* 158 */       ResourceBundle resourceBundle = FolderChooserResource.getResourceBundle(Locale.getDefault());
/* 159 */       add(new JLabel(resourceBundle.getString("FolderChooser.toolbar.recent")));
/* 160 */       add(this._recentFoldersList);
/*     */     }
/*     */     else {
/* 163 */       add(Box.createHorizontalGlue());
/*     */     }
/*     */ 
/* 166 */     this._desktopBtn = new NoFocusButton(new ToolBarAction(null, SystemInfo.isWindows() ? _fsv.getSystemIcon(_fsv.getHomeDirectory()) : BasicFolderChooserIconsFactory.getImageIcon("icons/home.png"))
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/* 169 */         FolderToolBar.this.desktopButtonClicked();
/*     */       }
/*     */     });
/* 173 */     ResourceBundle resourceBundle = FolderChooserResource.getResourceBundle(Locale.getDefault());
/* 174 */     this._desktopBtn.setToolTipText(SystemInfo.isWindows() ? resourceBundle.getString("FolderChooser.toolbar.desktop") : resourceBundle.getString("FolderChooser.toolbar.home"));
/* 175 */     this._desktopBtn.setName("FolderChooser.toolbar.desktop");
/* 176 */     add(this._desktopBtn);
/*     */ 
/* 178 */     if (SystemInfo.isWindows()) {
/* 179 */       this._myDocumentsBtn = new NoFocusButton(new ToolBarAction(null, _fsv.getSystemIcon(_fsv.getDefaultDirectory())) {
/*     */         public void actionPerformed(ActionEvent e) {
/* 181 */           FolderToolBar.this.myDocumentsButtonClicked();
/*     */         }
/*     */       });
/* 184 */       this._myDocumentsBtn.setToolTipText(resourceBundle.getString("FolderChooser.toolbar.mydocuments"));
/* 185 */       this._myDocumentsBtn.setName("FolderChooser.toolbar.mydocuments");
/* 186 */       add(this._myDocumentsBtn);
/*     */     }
/*     */ 
/* 189 */     Icon deleteIcon = BasicFolderChooserIconsFactory.getImageIcon("icons/delete.png");
/*     */ 
/* 191 */     this._deleteFolderBtn = new NoFocusButton(new ToolBarAction(null, deleteIcon) {
/*     */       public void actionPerformed(ActionEvent e) {
/* 193 */         FolderToolBar.this.deleteFolderButtonClicked();
/*     */       }
/*     */     });
/* 197 */     this._deleteFolderBtn.setToolTipText(resourceBundle.getString("FolderChooser.toolbar.delete"));
/* 198 */     this._deleteFolderBtn.setName("FolderChooser.toolbar.delete");
/*     */ 
/* 200 */     Icon newFolderIcon = BasicFolderChooserIconsFactory.getImageIcon("icons/new.png");
/* 201 */     this._newFolderBtn = new NoFocusButton(new ToolBarAction(null, newFolderIcon) {
/*     */       public void actionPerformed(ActionEvent e) {
/* 203 */         FolderToolBar.this.newFolderButtonClicked();
/*     */       }
/*     */     });
/* 207 */     this._newFolderBtn.setToolTipText(resourceBundle.getString("FolderChooser.toolbar.new"));
/* 208 */     this._newFolderBtn.setName("FolderChooser.toolbar.new");
/*     */ 
/* 210 */     Icon refreshIcon = BasicFolderChooserIconsFactory.getImageIcon("icons/refresh.png");
/* 211 */     this._refreshBtn = new NoFocusButton(new ToolBarAction(null, refreshIcon) {
/*     */       public void actionPerformed(ActionEvent e) {
/* 213 */         FolderToolBar.this.refreshButtonClicked();
/*     */       }
/*     */     });
/* 217 */     this._refreshBtn.setToolTipText(resourceBundle.getString("FolderChooser.toolbar.refresh"));
/* 218 */     this._refreshBtn.setName("FolderChooser.toolbar.refresh");
/*     */ 
/* 220 */     add(this._deleteFolderBtn);
/* 221 */     add(this._newFolderBtn);
/* 222 */     add(this._refreshBtn);
/*     */   }
/*     */ 
/*     */   boolean isButtonVisible(String buttonName, int availableButtons) {
/* 226 */     if ("FolderChooser.toolbar.delete".equals(buttonName)) {
/* 227 */       return (availableButtons & 0x1) != 0;
/*     */     }
/* 229 */     if ("FolderChooser.toolbar.new".equals(buttonName)) {
/* 230 */       return (availableButtons & 0x2) != 0;
/*     */     }
/* 232 */     if ("FolderChooser.toolbar.refresh".equals(buttonName)) {
/* 233 */       return (availableButtons & 0x4) != 0;
/*     */     }
/* 235 */     if ("FolderChooser.toolbar.desktop".equals(buttonName)) {
/* 236 */       return (availableButtons & 0x8) != 0;
/*     */     }
/* 238 */     if ("FolderChooser.toolbar.mydocuments".equals(buttonName)) {
/* 239 */       return (availableButtons & 0x10) != 0;
/*     */     }
/* 241 */     return true;
/*     */   }
/*     */ 
/*     */   public void addListener(FolderToolBarListener listener)
/*     */   {
/* 288 */     this._listeners.add(listener);
/*     */   }
/*     */ 
/*     */   public void removeListener(FolderToolBarListener listener)
/*     */   {
/* 297 */     this._listeners.remove(listener);
/*     */   }
/*     */ 
/*     */   public void clearListeners()
/*     */   {
/* 304 */     this._listeners.clear();
/*     */   }
/*     */ 
/*     */   private void deleteFolderButtonClicked() {
/* 308 */     for (FolderToolBarListener listener : this._listeners)
/* 309 */       listener.deleteFolderButtonClicked();
/*     */   }
/*     */ 
/*     */   private void newFolderButtonClicked()
/*     */   {
/* 314 */     for (FolderToolBarListener listener : this._listeners)
/* 315 */       listener.newFolderButtonClicked();
/*     */   }
/*     */ 
/*     */   private void refreshButtonClicked()
/*     */   {
/* 320 */     for (FolderToolBarListener listener : this._listeners)
/* 321 */       listener.refreshButtonClicked();
/*     */   }
/*     */ 
/*     */   private void myDocumentsButtonClicked()
/*     */   {
/* 326 */     for (FolderToolBarListener listener : this._listeners)
/* 327 */       listener.myDocumentsButtonClicked();
/*     */   }
/*     */ 
/*     */   private void desktopButtonClicked()
/*     */   {
/* 332 */     for (FolderToolBarListener listener : this._listeners)
/* 333 */       listener.desktopButtonClicked();
/*     */   }
/*     */ 
/*     */   private void recentFolderSelected(File recentFolder)
/*     */   {
/* 338 */     for (FolderToolBarListener listener : this._listeners)
/* 339 */       listener.recentFolderSelected(recentFolder);
/*     */   }
/*     */ 
/*     */   public void setRecentList(List<String> recentFoldersList)
/*     */   {
/* 349 */     if (recentFoldersList != null)
/* 350 */       this._recentFoldersList.setModel(new DefaultComboBoxModel(recentFoldersList.toArray()));
/*     */   }
/*     */ 
/*     */   static class NoFocusButton extends JButton
/*     */   {
/*     */     public NoFocusButton(Action a)
/*     */     {
/* 362 */       super();
/* 363 */       setRequestFocusEnabled(false);
/* 364 */       setFocusable(false);
/*     */ 
/* 367 */       Insets margin = getMargin();
/* 368 */       margin.left = margin.top;
/* 369 */       margin.right = margin.bottom;
/* 370 */       setMargin(margin);
/*     */     }
/*     */   }
/*     */ 
/*     */   private abstract class ToolBarAction extends AbstractAction
/*     */   {
/*     */     public ToolBarAction(String name, Icon icon)
/*     */     {
/* 356 */       super(icon);
/*     */     }
/*     */   }
/*     */ 
/*     */   private class FileListCellRenderer
/*     */     implements ListCellRenderer
/*     */   {
/*     */     protected ListCellRenderer _delegateRenderer;
/*     */ 
/*     */     private FileListCellRenderer(ListCellRenderer delegateRenderer)
/*     */     {
/* 249 */       this._delegateRenderer = delegateRenderer;
/*     */     }
/*     */ 
/*     */     public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
/* 253 */       JLabel renderer = (JLabel)this._delegateRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
/* 254 */       File f = null;
/* 255 */       if ((value instanceof File)) {
/* 256 */         f = (File)value;
/*     */       }
/* 258 */       else if (value != null) {
/* 259 */         f = new File(value.toString());
/*     */       }
/*     */ 
/* 262 */       if ((f != null) && (f.exists())) {
/* 263 */         String text = FolderToolBar._fsv.getSystemDisplayName(f);
/* 264 */         Icon icon = FolderToolBar._fsv.getSystemIcon(f);
/* 265 */         renderer.setIcon(icon);
/* 266 */         renderer.setText(text);
/* 267 */         renderer.setToolTipText(f.getAbsolutePath());
/*     */       }
/*     */       else {
/* 270 */         String filePath = value == null ? "" : value.toString();
/* 271 */         renderer.setText(filePath);
/* 272 */         renderer.setToolTipText(filePath);
/*     */       }
/* 274 */       return renderer;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.FolderToolBar
 * JD-Core Version:    0.6.0
 */
/*     */ package com.jidesoft.plaf.basic;
/*     */ 
/*     */ import com.jidesoft.dialog.ButtonPanel;
/*     */ import com.jidesoft.hints.FileIntelliHints;
/*     */ import com.jidesoft.plaf.FolderChooserUI;
/*     */ import com.jidesoft.swing.FolderChooser;
/*     */ import com.jidesoft.swing.JideBoxLayout;
/*     */ import com.jidesoft.swing.SelectAllUtils;
/*     */ import com.jidesoft.utils.SystemInfo;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.HierarchyEvent;
/*     */ import java.awt.event.HierarchyListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.File;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Stack;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.event.TreeSelectionEvent;
/*     */ import javax.swing.event.TreeSelectionListener;
/*     */ import javax.swing.filechooser.FileSystemView;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.FileChooserUI;
/*     */ import javax.swing.plaf.basic.BasicFileChooserUI;
/*     */ import javax.swing.tree.MutableTreeNode;
/*     */ import javax.swing.tree.TreeModel;
/*     */ import javax.swing.tree.TreePath;
/*     */ import javax.swing.tree.TreeSelectionModel;
/*     */ import sun.awt.shell.ShellFolder;
/*     */ 
/*     */ public class BasicFolderChooserUI extends BasicFileChooserUI
/*     */   implements FolderChooserUI
/*     */ {
/*     */   private FolderChooser _folderChooser;
/*     */   protected FolderToolBar _toolbar;
/*     */   protected JTree _fileSystemTree;
/*     */   protected JScrollPane _treeScrollPane;
/*     */   protected JButton _approveButton;
/*     */   protected JButton _cancelButton;
/*     */   protected JTextField _navigationTextField;
/*     */   protected JPanel _buttonPanel;
/*     */   protected JPanel _navigationPanel;
/*  48 */   private Action _approveSelectionAction = new ApproveSelectionAction();
/*     */   public FolderChooserSelectionListener _selectionListener;
/*     */   private FolderToolBarListener _folderToolbarListener;
/*     */ 
/*     */   public BasicFolderChooserUI(FolderChooser chooser)
/*     */   {
/*  53 */     super(chooser);
/*  54 */     BasicFileSystemTreeNode.clearCache();
/*     */   }
/*     */ 
/*     */   public static ComponentUI createUI(JComponent c)
/*     */   {
/*  59 */     return new BasicFolderChooserUI((FolderChooser)c);
/*     */   }
/*     */ 
/*     */   public void installComponents(JFileChooser chooser)
/*     */   {
/*  64 */     this._folderChooser = ((FolderChooser)chooser);
/*     */ 
/*  66 */     JPanel toolBarPanel = new JPanel(new BorderLayout(6, 6));
/*  67 */     toolBarPanel.add(createToolbar(), "First");
/*  68 */     toolBarPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
/*     */ 
/*  70 */     JPanel holdingPanel = new JPanel();
/*  71 */     BorderLayout borderLayout = new BorderLayout();
/*  72 */     borderLayout.setHgap(7);
/*  73 */     holdingPanel.setLayout(borderLayout);
/*  74 */     holdingPanel.setBorder(BorderFactory.createEmptyBorder(0, 6, 6, 6));
/*  75 */     holdingPanel.add(this._navigationPanel = createNavigationPanel(), "North");
/*  76 */     holdingPanel.add(createFileSystemTreePanel(), "Center");
/*  77 */     holdingPanel.add(this._buttonPanel = createButtonPanel(), "South");
/*     */ 
/*  79 */     Component accessory = chooser.getAccessory();
/*  80 */     if (accessory != null) {
/*  81 */       chooser.add(chooser.getAccessory(), "First");
/*     */     }
/*     */ 
/*  84 */     chooser.setLayout(new JideBoxLayout(chooser, 1));
/*  85 */     chooser.add(toolBarPanel, "fix");
/*  86 */     chooser.add(holdingPanel, "vary");
/*  87 */     chooser.setFileSelectionMode(1);
/*     */ 
/*  89 */     if (this._folderChooser.isNavigationFieldVisible()) {
/*  90 */       setNavigationFieldVisible(true);
/*     */     }
/*     */     else {
/*  93 */       setNavigationFieldVisible(false);
/*     */     }
/*     */ 
/*  96 */     updateView(chooser);
/*     */ 
/*  98 */     Runnable runnable = new Runnable() {
/*     */       public void run() {
/* 100 */         BasicFolderChooserUI.this._fileSystemTree.requestFocusInWindow();
/*     */       }
/*     */     };
/* 103 */     SwingUtilities.invokeLater(runnable);
/*     */ 
/* 109 */     this._folderChooser.addHierarchyListener(new HierarchyListener() {
/*     */       public void hierarchyChanged(HierarchyEvent e) {
/* 111 */         if (BasicFolderChooserUI.this._folderChooser.getRootPane() != null)
/* 112 */           BasicFolderChooserUI.this._folderChooser.getRootPane().setDefaultButton(BasicFolderChooserUI.this._approveButton);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public Component getDefaultFocusComponent()
/*     */   {
/* 126 */     return this._fileSystemTree;
/*     */   }
/*     */ 
/*     */   protected JPanel createButtonPanel() {
/* 130 */     this._approveButton = new JButton();
/* 131 */     this._approveButton.setAction(getApproveSelectionAction());
/*     */ 
/* 133 */     this._cancelButton = new JButton();
/* 134 */     this._cancelButton.addActionListener(getCancelSelectionAction());
/*     */ 
/* 136 */     ButtonPanel buttonPanel = new ButtonPanel();
/* 137 */     buttonPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 0, 0));
/* 138 */     buttonPanel.addButton(this._approveButton, "AFFIRMATIVE");
/* 139 */     buttonPanel.addButton(this._cancelButton, "CANCEL");
/* 140 */     return buttonPanel;
/*     */   }
/*     */ 
/*     */   protected JPanel createNavigationPanel() {
/* 144 */     NavigationTextFieldListener navigationTextFieldListener = new NavigationTextFieldListener();
/* 145 */     this._navigationTextField = new JTextField(24);
/* 146 */     SelectAllUtils.install(this._navigationTextField);
/* 147 */     FileIntelliHints fileIntelliHints = new FileIntelliHints(this._navigationTextField);
/* 148 */     fileIntelliHints.setFolderOnly(true);
/* 149 */     fileIntelliHints.setShowFullPath(false);
/* 150 */     fileIntelliHints.setFollowCaret(true);
/* 151 */     this._navigationTextField.addActionListener(navigationTextFieldListener);
/*     */ 
/* 153 */     JPanel panel = new JPanel();
/* 154 */     BorderLayout borderLayout = new BorderLayout();
/* 155 */     panel.setLayout(borderLayout);
/* 156 */     panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
/* 157 */     panel.add(this._navigationTextField, "Center");
/*     */ 
/* 159 */     return panel;
/*     */   }
/*     */ 
/*     */   public void setNavigationFieldVisible(boolean navigationFieldVisible) {
/* 163 */     this._navigationPanel.setVisible(navigationFieldVisible);
/*     */   }
/*     */ 
/*     */   public void rescanCurrentDirectory(JFileChooser fc)
/*     */   {
/* 201 */     super.rescanCurrentDirectory(fc);
/*     */   }
/*     */ 
/*     */   public void ensureFileIsVisible(JFileChooser fc, File f)
/*     */   {
/* 206 */     super.ensureFileIsVisible(fc, f);
/* 207 */     ensureFileIsVisible(f, true);
/*     */   }
/*     */ 
/*     */   protected JComponent createToolbar() {
/* 211 */     this._toolbar = new FolderToolBar(true, this._folderChooser.getRecentList());
/* 212 */     this._folderToolbarListener = new FolderToolBarListener()
/*     */     {
/*     */       private Cursor m_oldCursor;
/*     */ 
/*     */       public void deleteFolderButtonClicked()
/*     */       {
/* 220 */         TreePath path = BasicFolderChooserUI.this._fileSystemTree.getSelectionPaths()[0];
/* 221 */         List selection = getSelectedFolders(new TreePath[] { path });
/*     */ 
/* 223 */         ResourceBundle resourceBundle = FolderChooserResource.getResourceBundle(Locale.getDefault());
/*     */         String text;
/*     */         String text;
/* 224 */         if (selection.size() > 1) {
/* 225 */           text = MessageFormat.format(resourceBundle.getString("FolderChooser.delete.message2"), new Object[] { Integer.valueOf(selection.size()) });
/*     */         }
/*     */         else
/*     */         {
/* 229 */           text = resourceBundle.getString("FolderChooser.delete.message1");
/*     */         }
/* 231 */         String title = resourceBundle.getString("FolderChooser.delete.title");
/*     */ 
/* 233 */         int result = JOptionPane.showConfirmDialog(BasicFolderChooserUI.this._folderChooser, text, title, 2, 2);
/* 234 */         if (result == 0) {
/* 235 */           TreePath parentPath = path.getParentPath();
/* 236 */           Object parentObject = parentPath.getLastPathComponent();
/* 237 */           Object deletedObject = path.getLastPathComponent();
/* 238 */           int index = BasicFolderChooserUI.this._fileSystemTree.getModel().getIndexOfChild(parentObject, deletedObject);
/* 239 */           for (Iterator i$ = selection.iterator(); i$.hasNext(); ) { Object s = i$.next();
/* 240 */             File f = (File)s;
/* 241 */             recursiveDelete(f);
/*     */           }
/* 243 */           ((BasicFileSystemTreeModel)BasicFolderChooserUI.this._fileSystemTree.getModel()).removePath(path, index, deletedObject);
/* 244 */           TreePath pathToSelect = parentPath;
/* 245 */           if (index >= ((MutableTreeNode)parentObject).getChildCount()) {
/* 246 */             index = ((MutableTreeNode)parentObject).getChildCount() - 1;
/*     */           }
/* 248 */           if (index > 0) {
/* 249 */             pathToSelect = parentPath.pathByAddingChild(((MutableTreeNode)parentObject).getChildAt(index));
/*     */           }
/* 251 */           BasicFolderChooserUI.this._fileSystemTree.setSelectionPath(pathToSelect);
/* 252 */           BasicFolderChooserUI.this._fileSystemTree.scrollPathToVisible(pathToSelect);
/*     */         }
/*     */       }
/*     */ 
/*     */       public final boolean recursiveDelete(File file)
/*     */       {
/* 264 */         if (BasicFolderChooserUI.isFileSystem(file)) {
/* 265 */           if (file.isDirectory())
/*     */           {
/* 267 */             File[] children = FileSystemView.getFileSystemView().getFiles(file, false);
/* 268 */             for (File f : children) {
/* 269 */               if (!recursiveDelete(f)) {
/* 270 */                 return false;
/*     */               }
/*     */             }
/*     */ 
/* 274 */             return file.delete();
/*     */           }
/*     */ 
/* 277 */           return file.delete();
/*     */         }
/*     */ 
/* 281 */         return false;
/*     */       }
/*     */ 
/*     */       public void newFolderButtonClicked()
/*     */       {
/* 287 */         TreePath[] paths = BasicFolderChooserUI.this._fileSystemTree.getSelectionPaths();
/* 288 */         List selection = getSelectedFolders(paths);
/* 289 */         if ((selection.size() > 1) || (selection.size() == 0)) {
/* 290 */           return;
/*     */         }
/* 292 */         File parent = (File)selection.get(0);
/*     */ 
/* 294 */         ResourceBundle resourceBundle = FolderChooserResource.getResourceBundle(Locale.getDefault());
/* 295 */         String folderName = JOptionPane.showInputDialog(BasicFolderChooserUI.this._folderChooser, resourceBundle.getString("FolderChooser.new.folderName"), resourceBundle.getString("FolderChooser.new.title"), 3);
/*     */ 
/* 298 */         if (folderName != null) {
/* 299 */           folderName = eraseBlankInTheEnd(folderName);
/* 300 */           File newFolder = new File(parent, folderName);
/* 301 */           boolean success = newFolder.mkdir();
/*     */ 
/* 303 */           TreePath parentPath = paths[0];
/* 304 */           boolean isExpanded = BasicFolderChooserUI.this._fileSystemTree.isExpanded(parentPath);
/* 305 */           if (!isExpanded) {
/* 306 */             BasicFolderChooserUI.this._fileSystemTree.expandPath(parentPath);
/*     */           }
/*     */ 
/* 309 */           LazyMutableTreeNode parentTreeNode = (LazyMutableTreeNode)parentPath.getLastPathComponent();
/* 310 */           BasicFileSystemTreeNode child = BasicFileSystemTreeNode.createFileSystemTreeNode(newFolder, BasicFolderChooserUI.this._folderChooser);
/*     */ 
/* 312 */           if (success) {
/* 313 */             parentTreeNode.clear();
/* 314 */             int insertIndex = BasicFolderChooserUI.this._fileSystemTree.getModel().getIndexOfChild(parentTreeNode, child);
/* 315 */             if (insertIndex != -1)
/*     */             {
/* 317 */               ((BasicFileSystemTreeModel)BasicFolderChooserUI.this._fileSystemTree.getModel()).nodeStructureChanged(parentTreeNode);
/*     */             }
/*     */           }
/*     */ 
/* 321 */           TreePath newPath = parentPath.pathByAddingChild(child);
/* 322 */           BasicFolderChooserUI.this._fileSystemTree.setSelectionPath(newPath);
/* 323 */           BasicFolderChooserUI.this._fileSystemTree.scrollPathToVisible(newPath);
/*     */         }
/*     */       }
/*     */ 
/*     */       private String eraseBlankInTheEnd(String folderName) {
/* 328 */         int i = folderName.length() - 1;
/* 329 */         for (; i >= 0; i--) {
/* 330 */           char c = folderName.charAt(i);
/* 331 */           if ((c != ' ') && (c != '\t')) {
/*     */             break;
/*     */           }
/*     */         }
/* 335 */         if (i < 0) {
/* 336 */           return null;
/*     */         }
/* 338 */         return folderName.substring(0, i + 1);
/*     */       }
/*     */ 
/*     */       public void myDocumentsButtonClicked() {
/* 342 */         File myDocuments = FileSystemView.getFileSystemView().getDefaultDirectory();
/* 343 */         BasicFolderChooserUI.this.ensureFileIsVisible(myDocuments, true);
/*     */       }
/*     */ 
/*     */       public void desktopButtonClicked() {
/* 347 */         File desktop = FileSystemView.getFileSystemView().getHomeDirectory();
/* 348 */         BasicFolderChooserUI.this.ensureFileIsVisible(desktop, true);
/*     */       }
/*     */ 
/*     */       public void refreshButtonClicked() {
/* 352 */         File folder = BasicFolderChooserUI.this._folderChooser.getSelectedFolder();
/* 353 */         BasicFolderChooserUI.this._folderChooser.updateUI();
/* 354 */         while (folder != null)
/* 355 */           if (folder.exists()) {
/* 356 */             BasicFolderChooserUI.this._folderChooser.getUI().ensureFileIsVisible(BasicFolderChooserUI.this._folderChooser, folder);
/*     */           }
/*     */           else
/*     */           {
/* 360 */             folder = folder.getParentFile();
/* 361 */             if (folder == null)
/* 362 */               break;
/*     */           }
/*     */       }
/*     */ 
/*     */       public void recentFolderSelected(File file)
/*     */       {
/* 369 */         new Thread(new Runnable(file) {
/*     */           public void run() {
/* 371 */             BasicFolderChooserUI.3.this.setWaitCursor(true);
/*     */             try {
/* 373 */               BasicFolderChooserUI.this.ensureFileIsVisible(this.val$file, true);
/*     */             }
/*     */             finally {
/* 376 */               BasicFolderChooserUI.3.this.setWaitCursor(false);
/*     */             }
/*     */           }
/*     */         }).start();
/*     */       }
/*     */ 
/*     */       private void setWaitCursor(boolean isWait)
/*     */       {
/* 385 */         Window parentWindow = SwingUtilities.getWindowAncestor(BasicFolderChooserUI.this._folderChooser);
/* 386 */         if (isWait) {
/* 387 */           Cursor hourglassCursor = new Cursor(3);
/* 388 */           this.m_oldCursor = parentWindow.getCursor();
/* 389 */           parentWindow.setCursor(hourglassCursor);
/*     */         }
/* 392 */         else if (this.m_oldCursor != null) {
/* 393 */           parentWindow.setCursor(this.m_oldCursor);
/* 394 */           this.m_oldCursor = null;
/*     */         }
/*     */       }
/*     */ 
/*     */       public List getSelectedFolders()
/*     */       {
/* 401 */         TreePath[] paths = BasicFolderChooserUI.this._fileSystemTree.getSelectionPaths();
/* 402 */         return getSelectedFolders(paths);
/*     */       }
/*     */ 
/*     */       public List getSelectedFolders(TreePath[] paths) {
/* 406 */         if ((paths == null) || (paths.length == 0)) {
/* 407 */           return new ArrayList();
/*     */         }
/* 409 */         List folders = new ArrayList(paths.length);
/* 410 */         for (TreePath path : paths) {
/* 411 */           BasicFileSystemTreeNode f = (BasicFileSystemTreeNode)path.getLastPathComponent();
/* 412 */           folders.add(f.getFile());
/*     */         }
/* 414 */         return folders;
/*     */       }
/*     */     };
/* 418 */     this._toolbar.addListener(this._folderToolbarListener);
/* 419 */     updateToolbarButtons();
/* 420 */     return this._toolbar;
/*     */   }
/*     */ 
/*     */   protected void updateToolbarButtons()
/*     */   {
/* 428 */     TreePath[] selectedFiles = this._fileSystemTree == null ? new TreePath[0] : this._fileSystemTree.getSelectionPaths();
/*     */ 
/* 430 */     if ((selectedFiles != null) && (selectedFiles.length > 0)) {
/* 431 */       this._toolbar.enableDelete();
/*     */     }
/*     */     else {
/* 434 */       this._toolbar.disableDelete();
/*     */     }
/*     */ 
/* 438 */     if ((selectedFiles != null) && (selectedFiles.length == 1)) {
/* 439 */       this._toolbar.enableNewFolder();
/*     */     }
/*     */     else
/* 442 */       this._toolbar.disableNewFolder();
/*     */   }
/*     */ 
/*     */   private JComponent createFileSystemTreePanel()
/*     */   {
/* 447 */     JPanel panel = new JPanel(new BorderLayout());
/* 448 */     this._fileSystemTree = new BasicFileSystemTree(this._folderChooser);
/* 449 */     updateMultiSelectionEnabled();
/* 450 */     this._treeScrollPane = new JScrollPane(this._fileSystemTree);
/* 451 */     panel.add(this._treeScrollPane);
/* 452 */     return panel;
/*     */   }
/*     */ 
/*     */   private void updateMultiSelectionEnabled() {
/* 456 */     if (this._folderChooser.isMultiSelectionEnabled()) {
/* 457 */       this._fileSystemTree.getSelectionModel().setSelectionMode(4);
/*     */     }
/*     */     else
/*     */     {
/* 461 */       this._fileSystemTree.getSelectionModel().setSelectionMode(1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void uninstallComponents(JFileChooser chooser)
/*     */   {
/* 468 */     chooser.remove(this._treeScrollPane);
/* 469 */     chooser.remove(this._buttonPanel);
/*     */   }
/*     */ 
/*     */   protected void installListeners(JFileChooser fc)
/*     */   {
/* 474 */     super.installListeners(fc);
/* 475 */     this._selectionListener = new FolderChooserSelectionListener(null);
/* 476 */     this._fileSystemTree.addTreeSelectionListener(this._selectionListener);
/* 477 */     this._fileSystemTree.registerKeyboardAction(new AbstractAction() {
/*     */       private static final long serialVersionUID = -2758050378982771174L;
/*     */ 
/* 481 */       public void actionPerformed(ActionEvent e) { if (BasicFolderChooserUI.this._folderToolbarListener != null)
/* 482 */           BasicFolderChooserUI.this._folderToolbarListener.refreshButtonClicked();
/*     */       }
/*     */     }
/*     */     , KeyStroke.getKeyStroke(116, 0), 0);
/*     */   }
/*     */ 
/*     */   protected void uninstallListeners(JFileChooser fc)
/*     */   {
/* 490 */     super.uninstallListeners(fc);
/* 491 */     this._fileSystemTree.removeTreeSelectionListener(this._selectionListener);
/*     */   }
/*     */ 
/*     */   public PropertyChangeListener createPropertyChangeListener(JFileChooser fc)
/*     */   {
/* 496 */     return new FolderChooserPropertyChangeListener(null);
/*     */   }
/*     */ 
/*     */   private void updateView(JFileChooser chooser) {
/* 500 */     if (chooser.getApproveButtonText() != null) {
/* 501 */       this._approveButton.setText(chooser.getApproveButtonText());
/* 502 */       this._approveButton.setMnemonic(chooser.getApproveButtonMnemonic());
/*     */     }
/* 505 */     else if (0 == chooser.getDialogType()) {
/* 506 */       this._approveButton.setText(this.openButtonText);
/* 507 */       this._approveButton.setToolTipText(this.openButtonToolTipText);
/* 508 */       this._approveButton.setMnemonic(this.openButtonMnemonic);
/*     */     }
/*     */     else {
/* 511 */       this._approveButton.setText(this.saveButtonText);
/* 512 */       this._approveButton.setToolTipText(this.saveButtonToolTipText);
/* 513 */       this._approveButton.setMnemonic(this.saveButtonMnemonic);
/*     */     }
/*     */ 
/* 517 */     this._cancelButton.setText(this.cancelButtonText);
/* 518 */     this._cancelButton.setMnemonic(this.cancelButtonMnemonic);
/*     */ 
/* 520 */     this._buttonPanel.setVisible(chooser.getControlButtonsAreShown());
/* 521 */     if (this._toolbar != null) {
/* 522 */       Component[] components = this._toolbar.getComponents();
/* 523 */       for (Component component : components)
/* 524 */         if ((component instanceof JButton)) {
/* 525 */           String name = component.getName();
/* 526 */           int buttons = this._folderChooser.getAvailableButtons();
/* 527 */           boolean visible = this._toolbar.isButtonVisible(name, buttons);
/* 528 */           component.setVisible(visible);
/*     */         }
/* 530 */         else if (((component instanceof JComboBox)) || ((component instanceof JLabel))) {
/* 531 */           component.setVisible(this._folderChooser.isRecentListVisible());
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean isFileSystem(File f)
/*     */   {
/* 545 */     if ((f instanceof ShellFolder)) {
/* 546 */       ShellFolder sf = (ShellFolder)f;
/*     */ 
/* 549 */       return (sf.isFileSystem()) && ((!sf.isLink()) || (!sf.isDirectory()));
/*     */     }
/*     */ 
/* 552 */     return true;
/*     */   }
/*     */ 
/*     */   private TreePath getTreePathForFile(File file)
/*     */   {
/* 557 */     if (!file.isDirectory()) {
/* 558 */       return null;
/*     */     }
/* 560 */     Stack stack = new Stack();
/* 561 */     List list = new ArrayList();
/* 562 */     list.add(this._fileSystemTree.getModel().getRoot());
/* 563 */     FileSystemView view = this._folderChooser.getFileSystemView();
/* 564 */     File[] alternativeRoots = null;
/* 565 */     File root = null;
/* 566 */     if (SystemInfo.isWindows()) {
/* 567 */       File[] roots = view.getRoots();
/* 568 */       root = roots[0];
/* 569 */       if ((isFileSystem(root)) && (root.isDirectory())) {
/* 570 */         alternativeRoots = root.listFiles();
/*     */       }
/*     */     }
/* 573 */     File parent = file;
/*     */     do
/*     */     {
/* 576 */       stack.push(parent);
/* 577 */       if (alternativeRoots != null) {
/* 578 */         for (File r : alternativeRoots) {
/* 579 */           if (r.equals(parent)) {
/* 580 */             stack.push(root);
/* 581 */             break label187;
/*     */           }
/*     */         }
/*     */       }
/* 585 */       parent = this._folderChooser.getFileSystemView().getParentDirectory(parent);
/*     */     }
/* 587 */     while (parent != null);
/*     */ 
/* 589 */     label187: while (!stack.empty()) {
/* 590 */       list.add(BasicFileSystemTreeNode.createFileSystemTreeNode((File)stack.pop(), this._folderChooser));
/*     */     }
/* 592 */     return new TreePath(list.toArray());
/*     */   }
/*     */ 
/*     */   private void ensureFileIsVisible(File file, boolean scroll) {
/* 596 */     TreePath path = file == null ? new TreePath(this._fileSystemTree.getModel().getRoot()) : getTreePathForFile(file);
/* 597 */     if (path != null) {
/* 598 */       this._fileSystemTree.setSelectionPath(path);
/* 599 */       this._fileSystemTree.expandPath(path);
/* 600 */       if (scroll) {
/* 601 */         Runnable runnable = new Runnable(path) {
/*     */           public void run() {
/* 603 */             BasicFolderChooserUI.this._fileSystemTree.scrollPathToVisible(this.val$path);
/*     */           }
/*     */         };
/* 606 */         SwingUtilities.invokeLater(runnable);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setSelectedFiles()
/*     */   {
/* 717 */     TreePath[] selectedPaths = this._fileSystemTree.getSelectionPaths();
/* 718 */     if ((selectedPaths == null) || (selectedPaths.length == 0)) {
/* 719 */       this._folderChooser.setSelectedFile(null);
/* 720 */       return;
/*     */     }
/*     */ 
/* 723 */     List files = new ArrayList();
/* 724 */     int i = 0; for (int c = selectedPaths.length; i < c; i++) {
/* 725 */       File f = ((BasicFileSystemTreeNode)selectedPaths[i].getLastPathComponent()).getFile();
/* 726 */       files.add(f);
/*     */     }
/*     */ 
/* 729 */     this._folderChooser.setSelectedFiles((File[])files.toArray(new File[files.size()]));
/*     */   }
/*     */ 
/*     */   public Action getApproveSelectionAction()
/*     */   {
/* 734 */     return this._approveSelectionAction;
/*     */   }
/*     */   private class ApproveSelectionAction extends AbstractAction {
/*     */     private static final long serialVersionUID = -3465282473768757260L;
/*     */ 
/*     */     public ApproveSelectionAction() {
/* 741 */       setEnabled(false);
/*     */     }
/*     */ 
/*     */     public void actionPerformed(ActionEvent e) {
/* 745 */       BasicFolderChooserUI.this.setSelectedFiles();
/* 746 */       BasicFolderChooserUI.this._folderChooser.approveSelection();
/*     */     }
/*     */   }
/*     */ 
/*     */   private class FolderChooserSelectionListener
/*     */     implements TreeSelectionListener
/*     */   {
/*     */     private FolderChooserSelectionListener()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void valueChanged(TreeSelectionEvent e)
/*     */     {
/* 676 */       BasicFolderChooserUI.this.getApproveSelectionAction().setEnabled(BasicFolderChooserUI.this._fileSystemTree.getSelectionCount() > 0);
/* 677 */       if (BasicFolderChooserUI.this._toolbar != null) {
/* 678 */         BasicFolderChooserUI.this.updateToolbarButtons();
/*     */       }
/*     */ 
/* 690 */       if (BasicFolderChooserUI.this._fileSystemTree.getSelectionCount() > 0) {
/* 691 */         TreePath path = e.getNewLeadSelectionPath();
/* 692 */         if (path != null) {
/* 693 */           String folderPath = path.getLastPathComponent().toString();
/* 694 */           File folder = new File(folderPath);
/* 695 */           BasicFolderChooserUI.this._folderChooser.setSelectedFolder(folder);
/*     */ 
/* 701 */           TreePath treePath = BasicFolderChooserUI.this._fileSystemTree.getSelectionPath();
/* 702 */           if (treePath != null)
/* 703 */             BasicFolderChooserUI.this._navigationTextField.setText("" + treePath.getLastPathComponent());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private class FolderChooserPropertyChangeListener
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     private FolderChooserPropertyChangeListener()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void propertyChange(PropertyChangeEvent evt)
/*     */     {
/* 614 */       if ("recentList".equals(evt.getPropertyName())) {
/* 615 */         BasicFolderChooserUI.this._toolbar.setRecentList((List)evt.getNewValue());
/*     */       }
/* 617 */       else if ("ApproveButtonTextChangedProperty".equals(evt.getPropertyName())) {
/* 618 */         BasicFolderChooserUI.this.updateView(BasicFolderChooserUI.this._folderChooser);
/*     */       }
/* 620 */       else if ("DialogTypeChangedProperty".equals(evt.getPropertyName())) {
/* 621 */         BasicFolderChooserUI.this.updateView(BasicFolderChooserUI.this._folderChooser);
/*     */       }
/* 623 */       else if ("MultiSelectionEnabledChangedProperty".equals(evt.getPropertyName())) {
/* 624 */         BasicFolderChooserUI.this.updateMultiSelectionEnabled();
/*     */       }
/* 626 */       else if ("directoryChanged".equals(evt.getPropertyName())) {
/* 627 */         BasicFolderChooserUI.this.ensureFileIsVisible(BasicFolderChooserUI.this._folderChooser.getCurrentDirectory(), true);
/*     */       }
/* 629 */       else if ("AccessoryChangedProperty".equals(evt.getPropertyName())) {
/* 630 */         Component oldValue = (Component)evt.getOldValue();
/* 631 */         Component newValue = (Component)evt.getNewValue();
/* 632 */         if (oldValue != null) {
/* 633 */           BasicFolderChooserUI.this._folderChooser.remove(oldValue);
/*     */         }
/* 635 */         if (newValue != null) {
/* 636 */           BasicFolderChooserUI.this._folderChooser.add(newValue, "First");
/*     */         }
/* 638 */         BasicFolderChooserUI.this._folderChooser.revalidate();
/* 639 */         BasicFolderChooserUI.this._folderChooser.repaint();
/*     */       }
/* 641 */       else if ("ControlButtonsAreShownChangedProperty".equals(evt.getPropertyName())) {
/* 642 */         BasicFolderChooserUI.this.updateView(BasicFolderChooserUI.this._folderChooser);
/*     */       }
/* 644 */       else if ("navigationFieldVisible".equals(evt.getPropertyName())) {
/* 645 */         if (BasicFolderChooserUI.this._folderChooser.isNavigationFieldVisible()) {
/* 646 */           BasicFolderChooserUI.this.setNavigationFieldVisible(true);
/*     */         }
/*     */         else {
/* 649 */           BasicFolderChooserUI.this.setNavigationFieldVisible(false);
/*     */         }
/*     */       }
/* 652 */       else if ("availableButtons".equals(evt.getPropertyName())) {
/* 653 */         Component[] components = BasicFolderChooserUI.this._toolbar.getComponents();
/* 654 */         for (Component component : components) {
/* 655 */           if ((component instanceof JButton)) {
/* 656 */             String name = component.getName();
/* 657 */             int buttons = BasicFolderChooserUI.this._folderChooser.getAvailableButtons();
/* 658 */             boolean visible = BasicFolderChooserUI.this._toolbar.isButtonVisible(name, buttons);
/* 659 */             component.setVisible(visible);
/*     */           }
/*     */         }
/*     */       }
/* 663 */       else if ("recentListVisible".equals(evt.getPropertyName())) {
/* 664 */         Component[] components = BasicFolderChooserUI.this._toolbar.getComponents();
/* 665 */         for (Component component : components)
/* 666 */           if (((component instanceof JComboBox)) || ((component instanceof JLabel)))
/* 667 */             component.setVisible(BasicFolderChooserUI.this._folderChooser.isRecentListVisible());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public class NavigationTextFieldListener
/*     */     implements ActionListener
/*     */   {
/*     */     public NavigationTextFieldListener()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void actionPerformed(ActionEvent e)
/*     */     {
/* 169 */       String text = BasicFolderChooserUI.this._navigationTextField.getText();
/* 170 */       if ((text == null) || (text.equals(""))) {
/* 171 */         return;
/*     */       }
/*     */ 
/* 184 */       TreePath treePath = BasicFolderChooserUI.this._fileSystemTree.getSelectionPath();
/* 185 */       if ((treePath != null) && 
/* 186 */         (text.equals("" + treePath.getLastPathComponent()))) {
/* 187 */         BasicFolderChooserUI.this._approveButton.doClick(200);
/*     */       }
/*     */ 
/* 191 */       File file = new File(text);
/* 192 */       if (file.exists()) {
/* 193 */         BasicFolderChooserUI.this.ensureFileIsVisible(file, true);
/* 194 */         BasicFolderChooserUI.this._folderChooser.setSelectedFolder(file);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.BasicFolderChooserUI
 * JD-Core Version:    0.6.0
 */
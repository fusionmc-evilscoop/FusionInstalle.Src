/*      */ package com.jidesoft.dialog;
/*      */ 
/*      */ import com.jidesoft.plaf.UIDefaultsLookup;
/*      */ import com.jidesoft.swing.JideButton;
/*      */ import com.jidesoft.swing.JideScrollPane;
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.CardLayout;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.HeadlessException;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.PrintStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import javax.swing.AbstractAction;
/*      */ import javax.swing.AbstractButton;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.ButtonGroup;
/*      */ import javax.swing.DefaultListModel;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JScrollBar;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTabbedPane;
/*      */ import javax.swing.JTree;
/*      */ import javax.swing.JViewport;
/*      */ import javax.swing.ListCellRenderer;
/*      */ import javax.swing.ListModel;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.event.ListDataEvent;
/*      */ import javax.swing.event.ListDataListener;
/*      */ import javax.swing.event.ListSelectionEvent;
/*      */ import javax.swing.event.ListSelectionListener;
/*      */ import javax.swing.event.TreeSelectionEvent;
/*      */ import javax.swing.event.TreeSelectionListener;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import javax.swing.tree.DefaultTreeModel;
/*      */ import javax.swing.tree.MutableTreeNode;
/*      */ import javax.swing.tree.TreeCellRenderer;
/*      */ import javax.swing.tree.TreeModel;
/*      */ import javax.swing.tree.TreeNode;
/*      */ import javax.swing.tree.TreePath;
/*      */ import javax.swing.tree.TreeSelectionModel;
/*      */ 
/*      */ public class MultiplePageDialogPane extends StandardDialogPane
/*      */ {
/*      */   public static final int TAB_STYLE = 0;
/*      */   public static final int TREE_STYLE = 1;
/*      */   public static final int LIST_STYLE = 2;
/*      */   public static final int ICON_STYLE = 3;
/*      */   private int _style;
/*      */   private PageList _pageList;
/*      */   private JComponent _indexPanel;
/*      */   private JComponent _pagesPanel;
/*      */   private CardLayout _cardLayout;
/*      */   private Map _titleNodeMap;
/*      */   private JButton _okButton;
/*      */   private JButton _cancelButton;
/*      */   private JButton _applyButton;
/*      */   private AbstractAction _okAction;
/*      */   private AbstractAction _cancelAction;
/*      */   private TreeCellRenderer _treeCellRenderer;
/*      */   private ListCellRenderer _listCellRenderer;
/*      */   private JTabbedPane _tabbedPane;
/*      */   private String _initialPageTitle;
/*      */   public JTree _tree;
/*      */ 
/*      */   public MultiplePageDialogPane()
/*      */     throws HeadlessException
/*      */   {
/*  110 */     this(0);
/*      */   }
/*      */ 
/*      */   public MultiplePageDialogPane(int style)
/*      */   {
/*  124 */     setStyle(style);
/*      */   }
/*      */ 
/*      */   public JComponent createBannerPanel()
/*      */   {
/*  134 */     return null;
/*      */   }
/*      */ 
/*      */   public JComponent createContentPanel()
/*      */   {
/*  145 */     this._indexPanel = createIndexPanel();
/*  146 */     this._pagesPanel = createPagesPanel();
/*  147 */     if (this._pageList.getPageCount() > 0) {
/*  148 */       if (getInitialPageTitle() != null) {
/*  149 */         setCurrentPage(getInitialPageTitle());
/*      */       }
/*      */       else {
/*  152 */         setCurrentPage(this._pageList.getPage(0));
/*      */       }
/*      */     }
/*  155 */     return setupContentPanel(this._indexPanel, this._pagesPanel);
/*      */   }
/*      */ 
/*      */   protected JComponent setupContentPanel(JComponent indexPanel, JComponent pagesPanel)
/*      */   {
/*  167 */     JPanel middlePanel = new JPanel(new BorderLayout());
/*  168 */     if (indexPanel != null) {
/*  169 */       middlePanel.add(indexPanel, "Before");
/*      */     }
/*  171 */     if (pagesPanel != null) {
/*  172 */       middlePanel.add(pagesPanel, "Center");
/*      */     }
/*  174 */     return middlePanel;
/*      */   }
/*      */ 
/*      */   public ButtonPanel createButtonPanel()
/*      */   {
/*  185 */     ButtonPanel buttonPanel = new ButtonPanel();
/*  186 */     Locale l = getLocale();
/*  187 */     this._okButton = new JButton(UIDefaultsLookup.getString("OptionPane.okButtonText", l));
/*  188 */     this._cancelButton = new JButton(UIDefaultsLookup.getString("OptionPane.cancelButtonText", l));
/*  189 */     this._applyButton = new JButton();
/*  190 */     this._okButton.setName("OK");
/*  191 */     this._cancelButton.setName("CANCEL");
/*  192 */     this._applyButton.setName("APPLY");
/*  193 */     buttonPanel.addButton(this._okButton, "AFFIRMATIVE");
/*  194 */     buttonPanel.addButton(this._cancelButton, "CANCEL");
/*  195 */     buttonPanel.addButton(this._applyButton, "ALTERNATIVE");
/*      */ 
/*  197 */     this._okButton.setAction(getOKAction());
/*  198 */     this._cancelButton.setAction(getCancelAction());
/*  199 */     this._applyButton.setAction(new AbstractAction(ButtonResources.getResourceBundle(Locale.getDefault()).getString("Button.apply")) {
/*      */       public void actionPerformed(ActionEvent e) {
/*  201 */         if (MultiplePageDialogPane.this.getCurrentPage() != null)
/*  202 */           MultiplePageDialogPane.this.getCurrentPage().fireButtonEvent(3302, "APPLY");
/*      */       }
/*      */     });
/*  206 */     this._applyButton.setMnemonic(ButtonResources.getResourceBundle(Locale.getDefault()).getString("Button.apply.mnemonic").charAt(0));
/*  207 */     this._applyButton.setEnabled(false);
/*      */ 
/*  209 */     setDefaultCancelAction(this._cancelButton.getAction());
/*  210 */     setDefaultAction(this._okButton.getAction());
/*      */ 
/*  212 */     return buttonPanel;
/*      */   }
/*      */ 
/*      */   public JButton getOkButton()
/*      */   {
/*  221 */     return this._okButton;
/*      */   }
/*      */ 
/*      */   public JButton getCancelButton()
/*      */   {
/*  230 */     return this._cancelButton;
/*      */   }
/*      */ 
/*      */   public JButton getApplyButton()
/*      */   {
/*  239 */     return this._applyButton;
/*      */   }
/*      */ 
/*      */   public void setCancelAction(AbstractAction cancelAction)
/*      */   {
/*  248 */     if (cancelAction == null) {
/*  249 */       throw new IllegalArgumentException("cancelAction cannot be null");
/*      */     }
/*      */ 
/*  252 */     this._cancelAction = cancelAction;
/*  253 */     setDefaultCancelAction(cancelAction);
/*  254 */     if (this._cancelButton != null)
/*  255 */       this._cancelButton.setAction(cancelAction);
/*      */   }
/*      */ 
/*      */   public AbstractAction getCancelAction()
/*      */   {
/*  266 */     return this._cancelAction;
/*      */   }
/*      */ 
/*      */   public void setOKAction(AbstractAction okAction)
/*      */   {
/*  275 */     if (okAction == null) {
/*  276 */       throw new IllegalArgumentException("cancelAction cannot be null");
/*      */     }
/*      */ 
/*  279 */     this._okAction = okAction;
/*  280 */     setDefaultAction(okAction);
/*  281 */     if (this._okAction != null)
/*  282 */       this._okButton.setAction(okAction);
/*      */   }
/*      */ 
/*      */   public AbstractAction getOKAction()
/*      */   {
/*  293 */     return this._okAction;
/*      */   }
/*      */ 
/*      */   protected JComponent createPagesPanel()
/*      */   {
/*  303 */     if (this._style == 0) {
/*  304 */       this._tabbedPane = createTabbedPane();
/*  305 */       this._tabbedPane.addChangeListener(new ChangeListener() {
/*      */         public void stateChanged(ChangeEvent e) {
/*  307 */           Component selectedComponent = MultiplePageDialogPane.this._tabbedPane.getSelectedComponent();
/*  308 */           if ((selectedComponent instanceof AbstractDialogPage))
/*  309 */             MultiplePageDialogPane.this.setCurrentPage((AbstractDialogPage)selectedComponent, MultiplePageDialogPane.this._tabbedPane);
/*      */         }
/*      */       });
/*  313 */       for (int i = 0; i < this._pageList.getPageCount(); i++) {
/*  314 */         AbstractDialogPage page = this._pageList.getPage(i);
/*  315 */         page.addButtonListener(getButtonPanel());
/*  316 */         this._tabbedPane.addTab(page.getTitle(), page.getIcon(), page, page.getDescription());
/*  317 */         this._tabbedPane.setEnabledAt(i, page.isPageEnabled());
/*  318 */         int index = i;
/*  319 */         page.addPropertyChangeListener(new PropertyChangeListener(index) {
/*      */           public void propertyChange(PropertyChangeEvent evt) {
/*  321 */             if ("enabled".equals(evt.getPropertyName())) {
/*  322 */               MultiplePageDialogPane.this._tabbedPane.setEnabledAt(this.val$index, Boolean.TRUE.equals(evt.getNewValue()));
/*      */             }
/*  324 */             else if ("icon".equals(evt.getPropertyName())) {
/*  325 */               MultiplePageDialogPane.this._tabbedPane.setIconAt(this.val$index, (Icon)evt.getNewValue());
/*      */             }
/*  327 */             else if ("title".equals(evt.getPropertyName())) {
/*  328 */               MultiplePageDialogPane.this._tabbedPane.setTitleAt(this.val$index, (String)evt.getNewValue());
/*      */             }
/*  330 */             else if ("description".equals(evt.getPropertyName()))
/*  331 */               MultiplePageDialogPane.this._tabbedPane.setToolTipTextAt(this.val$index, (String)evt.getNewValue());
/*      */           }
/*      */         });
/*      */       }
/*  336 */       this._pageList.addListDataListener(new ListDataListener() {
/*      */         public void intervalAdded(ListDataEvent e) {
/*  338 */           for (int i = e.getIndex0(); i <= e.getIndex1(); i++) {
/*  339 */             AbstractDialogPage page = MultiplePageDialogPane.this._pageList.getPage(i);
/*  340 */             MultiplePageDialogPane.this._tabbedPane.insertTab(page.getTitle(), page.getIcon(), page, page.getDescription(), i);
/*      */           }
/*      */         }
/*      */ 
/*      */         public void intervalRemoved(ListDataEvent e) {
/*  345 */           for (int i = e.getIndex1(); i >= e.getIndex0(); i--)
/*  346 */             MultiplePageDialogPane.this._tabbedPane.removeTabAt(i);
/*      */         }
/*      */ 
/*      */         public void contentsChanged(ListDataEvent e)
/*      */         {
/*      */         }
/*      */       });
/*  353 */       return this._tabbedPane;
/*      */     }
/*      */ 
/*  356 */     JPanel pagesPanel = new JPanel();
/*  357 */     this._cardLayout = new CardLayout();
/*  358 */     pagesPanel.setLayout(this._cardLayout);
/*      */ 
/*  360 */     for (int i = 0; i < this._pageList.getPageCount(); i++) {
/*  361 */       AbstractDialogPage page = this._pageList.getPage(i);
/*  362 */       page.addButtonListener(getButtonPanel());
/*  363 */       page.setName(page.getFullTitle());
/*  364 */       page.addPropertyChangeListener(new PropertyChangeListener(pagesPanel) {
/*      */         public void propertyChange(PropertyChangeEvent evt) {
/*  366 */           if ("title".equals(evt.getPropertyName()))
/*  367 */             for (int j = 0; j < this.val$pagesPanel.getComponentCount(); j++) {
/*  368 */               Component c = this.val$pagesPanel.getComponent(j);
/*  369 */               Object source = evt.getSource();
/*  370 */               if (((source instanceof AbstractDialogPage)) && (c == source)) {
/*  371 */                 this.val$pagesPanel.remove(j);
/*  372 */                 String fullTitle = ((AbstractDialogPage)source).getFullTitle();
/*  373 */                 this.val$pagesPanel.add((AbstractDialogPage)source, fullTitle, j);
/*  374 */                 ((AbstractDialogPage)source).setName(fullTitle);
/*  375 */                 MultiplePageDialogPane.this.getIndexPanel().repaint();
/*  376 */                 break;
/*      */               }
/*      */             }
/*      */         }
/*      */       });
/*  383 */       pagesPanel.add(page, page.getFullTitle());
/*      */     }
/*      */ 
/*  386 */     this._pageList.addListDataListener(new ListDataListener(pagesPanel) {
/*      */       public void intervalAdded(ListDataEvent e) {
/*  388 */         for (int i = e.getIndex0(); i <= e.getIndex1(); i++) {
/*  389 */           AbstractDialogPage page = MultiplePageDialogPane.this._pageList.getPage(i);
/*  390 */           page.setName(page.getFullTitle());
/*  391 */           this.val$pagesPanel.add(page, page.getFullTitle(), i);
/*      */         }
/*      */       }
/*      */ 
/*      */       public void intervalRemoved(ListDataEvent e) {
/*  396 */         for (int i = e.getIndex1(); i >= e.getIndex0(); i--)
/*  397 */           this.val$pagesPanel.remove(i);
/*      */       }
/*      */ 
/*      */       private void dumpPagesPanel()
/*      */       {
/*  402 */         for (int i = 0; i < this.val$pagesPanel.getComponentCount(); i++)
/*  403 */           System.out.println("" + i + ": " + this.val$pagesPanel.getComponent(i).getName());
/*      */       }
/*      */ 
/*      */       public void contentsChanged(ListDataEvent e)
/*      */       {
/*  408 */         if ((e.getSource() instanceof PageList)) {
/*  409 */           Object o = ((PageList)e.getSource()).getSelectedItem();
/*  410 */           if (((o instanceof AbstractDialogPage)) && (((AbstractDialogPage)o).isPageEnabled()))
/*  411 */             MultiplePageDialogPane.this.setCurrentPage((AbstractDialogPage)o);
/*      */         }
/*      */       }
/*      */     });
/*  416 */     return pagesPanel;
/*      */   }
/*      */ 
/*      */   protected JTabbedPane createTabbedPane()
/*      */   {
/*  426 */     return new JTabbedPane(1);
/*      */   }
/*      */ 
/*      */   public JComponent createIndexPanel()
/*      */   {
/*  435 */     switch (this._style) {
/*      */     case 3:
/*  437 */       return createIconPanel();
/*      */     case 2:
/*  439 */       return createListPanel();
/*      */     case 1:
/*  441 */       return createTreePanel();
/*      */     case 0:
/*      */     }
/*  444 */     return null;
/*      */   }
/*      */ 
/*      */   public void setPageList(PageList pageList)
/*      */   {
/*  454 */     this._pageList = pageList;
/*      */   }
/*      */ 
/*      */   public PageList getPageList()
/*      */   {
/*  461 */     return this._pageList;
/*      */   }
/*      */ 
/*      */   public AbstractDialogPage getCurrentPage()
/*      */   {
/*  470 */     return this._pageList.getCurrentPage();
/*      */   }
/*      */ 
/*      */   protected void setCurrentPage(String pageTitle) {
/*  474 */     if (this._pageList != null)
/*  475 */       setCurrentPage(this._pageList.getPageByFullTitle(pageTitle));
/*      */   }
/*      */ 
/*      */   protected void setCurrentPage(AbstractDialogPage currentPage)
/*      */   {
/*  480 */     setCurrentPage(currentPage, null);
/*      */   }
/*      */ 
/*      */   protected void setCurrentPage(AbstractDialogPage currentPage, Object source) {
/*  484 */     if (!this._pageList.setCurrentPage(currentPage, source)) {
/*  485 */       return;
/*      */     }
/*      */ 
/*  488 */     if (currentPage != null)
/*  489 */       showCurrentPage(currentPage);
/*      */   }
/*      */ 
/*      */   protected void showCurrentPage(AbstractDialogPage currentPage)
/*      */   {
/*  501 */     if (currentPage != null) {
/*  502 */       if (getStyle() == 0) {
/*  503 */         this._tabbedPane.setSelectedComponent(currentPage);
/*      */       }
/*      */       else {
/*  506 */         this._cardLayout.show(this._pagesPanel, currentPage.getFullTitle());
/*      */       }
/*  508 */       currentPage.focusDefaultFocusComponent();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected MutableTreeNode createTreeNode(AbstractDialogPage dialogPage)
/*      */   {
/*  519 */     return new MutableTreeNodeEx(dialogPage);
/*      */   }
/*      */ 
/*      */   private JComponent createTreePanel() {
/*  523 */     DefaultMutableTreeNode root = new DefaultMutableTreeNode("", true);
/*      */ 
/*  525 */     this._titleNodeMap = new HashMap((int)(this._pageList.getPageCount() * 0.75D));
/*  526 */     for (int i = 0; i < this._pageList.getPageCount(); i++) {
/*  527 */       AbstractDialogPage dialogPage = this._pageList.getPage(i);
/*  528 */       addPage(dialogPage, root, false);
/*      */     }
/*      */ 
/*  531 */     this._tree = createTree(root);
/*  532 */     configureTree(this._tree);
/*  533 */     this._pageList.addListDataListener(new ListDataListener() {
/*      */       public void intervalAdded(ListDataEvent e) {
/*  535 */         for (int i = e.getIndex0(); i <= e.getIndex1(); i++) {
/*  536 */           AbstractDialogPage dialogPage = MultiplePageDialogPane.this._pageList.getPage(i);
/*  537 */           MultiplePageDialogPane.this.addPage(dialogPage, (DefaultMutableTreeNode)MultiplePageDialogPane.this._tree.getModel().getRoot(), true);
/*      */         }
/*      */       }
/*      */ 
/*      */       public void intervalRemoved(ListDataEvent e)
/*      */       {
/*  543 */         Set set = MultiplePageDialogPane.this._titleNodeMap.keySet();
/*  544 */         Vector toBeRemoved = new Vector();
/*  545 */         for (Iterator i$ = set.iterator(); i$.hasNext(); ) { Object o = i$.next();
/*  546 */           String title = (String)o;
/*  547 */           if (MultiplePageDialogPane.this._pageList.getPageByFullTitle(title) == null) {
/*  548 */             DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)MultiplePageDialogPane.this._titleNodeMap.get(title);
/*  549 */             if (treeNode != null) {
/*  550 */               toBeRemoved.add(title);
/*  551 */               DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)treeNode.getParent();
/*  552 */               if (parentNode != null) {
/*  553 */                 int index = parentNode.getIndex(treeNode);
/*  554 */                 parentNode.remove(treeNode);
/*  555 */                 ((DefaultTreeModel)MultiplePageDialogPane.this._tree.getModel()).nodesWereRemoved(parentNode, new int[] { index }, new Object[] { treeNode });
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*  560 */         for (Iterator i$ = toBeRemoved.iterator(); i$.hasNext(); ) { Object o = i$.next();
/*  561 */           MultiplePageDialogPane.this._titleNodeMap.remove(o); }
/*      */       }
/*      */ 
/*      */       public void contentsChanged(ListDataEvent e)
/*      */       {
/*  566 */         if ((e.getIndex0() == -1) && (e.getIndex1() == -1) && (e.getType() == 0) && 
/*  567 */           (MultiplePageDialogPane.this._titleNodeMap != null) && (MultiplePageDialogPane.this._pageList.getCurrentPage() != null)) {
/*  568 */           TreeNode node = (TreeNode)MultiplePageDialogPane.this._titleNodeMap.get(MultiplePageDialogPane.this._pageList.getCurrentPage().getFullTitle());
/*  569 */           if (node != null) {
/*  570 */             ArrayList list = new ArrayList();
/*  571 */             while (node != null) {
/*  572 */               list.add(0, node);
/*  573 */               node = node.getParent();
/*      */             }
/*  575 */             TreePath treePath = new TreePath(list.toArray(new TreeNode[list.size()]));
/*  576 */             MultiplePageDialogPane.this._tree.getSelectionModel().setSelectionPath(treePath);
/*      */           }
/*      */         }
/*      */       }
/*      */     });
/*  583 */     JComponent indexPanel = new JPanel(new BorderLayout());
/*  584 */     indexPanel.add(new JScrollPane(this._tree), "Center");
/*  585 */     return indexPanel;
/*      */   }
/*      */ 
/*      */   protected JTree createTree(DefaultMutableTreeNode root)
/*      */   {
/*  601 */     UIManager.put("Tree.hash", Color.white);
/*  602 */     return new JTree(root);
/*      */   }
/*      */ 
/*      */   protected void configureTree(JTree tree)
/*      */   {
/*  648 */     tree.setToggleClickCount(1);
/*  649 */     tree.setCellRenderer(createTreeCellRenderer());
/*  650 */     tree.setRootVisible(false);
/*  651 */     tree.setShowsRootHandles(false);
/*  652 */     tree.addTreeSelectionListener(new TreeSelectionListener(tree) {
/*      */       public void valueChanged(TreeSelectionEvent e) {
/*  654 */         if (this.val$tree.getSelectionPath() == null) {
/*  655 */           return;
/*      */         }
/*      */ 
/*  658 */         DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)this.val$tree.getSelectionPath().getLastPathComponent();
/*      */ 
/*  661 */         while (!treeNode.isLeaf()) {
/*  662 */           DefaultMutableTreeNode tn = treeNode;
/*  663 */           Runnable runnable = new Runnable(tn) {
/*      */             public void run() {
/*  665 */               MultiplePageDialogPane.8.this.val$tree.expandPath(new TreePath(this.val$tn.getPath()));
/*      */             }
/*      */           };
/*  668 */           SwingUtilities.invokeLater(runnable);
/*  669 */           treeNode = (DefaultMutableTreeNode)treeNode.getChildAt(0);
/*      */         }
/*      */ 
/*  672 */         Object userObject = treeNode.getUserObject();
/*  673 */         if (((userObject instanceof AbstractDialogPage)) && (!userObject.equals(MultiplePageDialogPane.this.getCurrentPage())) && (((AbstractDialogPage)userObject).isPageEnabled())) { MultiplePageDialogPane.this.setCurrentPage((AbstractDialogPage)userObject, this.val$tree);
/*  675 */           if (MultiplePageDialogPane.this.getCurrentPage() == userObject);
/*      */         }
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   private void addPage(AbstractDialogPage dialogPage, DefaultMutableTreeNode root, boolean fireEvent) {
/*  684 */     if (dialogPage == null) {
/*  685 */       return;
/*      */     }
/*      */ 
/*  688 */     MutableTreeNode treeNode = createTreeNode(dialogPage);
/*  689 */     if ((treeNode instanceof MutableTreeNodeEx)) {
/*  690 */       ((MutableTreeNodeEx)treeNode).setEnabled(dialogPage.isPageEnabled());
/*      */     }
/*  692 */     if (dialogPage.getParentPage() == null) {
/*  693 */       this._titleNodeMap.put(dialogPage.getFullTitle(), treeNode);
/*  694 */       root.add(treeNode);
/*  695 */       if (fireEvent)
/*  696 */         ((DefaultTreeModel)this._tree.getModel()).nodesWereInserted(root, new int[] { root.getIndex(treeNode) });
/*      */     }
/*      */     else
/*      */     {
/*  700 */       this._titleNodeMap.put(dialogPage.getFullTitle(), treeNode);
/*  701 */       DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)this._titleNodeMap.get(dialogPage.getParentPage().getFullTitle());
/*  702 */       if (parentNode != null) {
/*  703 */         parentNode.add(treeNode);
/*  704 */         if (fireEvent) {
/*  705 */           ((DefaultTreeModel)this._tree.getModel()).nodesWereInserted(parentNode, new int[] { parentNode.getIndex(treeNode) });
/*      */         }
/*      */       }
/*      */     }
/*  709 */     dialogPage.addPropertyChangeListener(new PropertyChangeListener(treeNode, dialogPage) {
/*      */       public void propertyChange(PropertyChangeEvent evt) {
/*  711 */         if (("icon".equals(evt.getPropertyName())) && ((this.val$treeNode instanceof MutableTreeNodeEx)))
/*  712 */           ((MutableTreeNodeEx)this.val$treeNode).setEnabled(this.val$dialogPage.isPageEnabled());
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   private void removePage(AbstractDialogPage dialogPage, DefaultMutableTreeNode root, boolean fireEvent) {
/*  719 */     if (dialogPage == null) {
/*  720 */       return;
/*      */     }
/*      */ 
/*  723 */     DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)this._titleNodeMap.get(dialogPage.getFullTitle());
/*      */ 
/*  725 */     if (treeNode == null) {
/*  726 */       return;
/*      */     }
/*      */ 
/*  729 */     if (treeNode.getChildCount() > 0) {
/*  730 */       throw new IllegalArgumentException("Please remove all children pages before removing parent page \"" + dialogPage.getFullTitle() + "\"");
/*      */     }
/*  732 */     this._titleNodeMap.remove(dialogPage.getFullTitle());
/*  733 */     if (dialogPage.getParentPage() == null) {
/*  734 */       int index = root.getIndex(treeNode);
/*  735 */       root.remove(treeNode);
/*  736 */       if (fireEvent)
/*  737 */         ((DefaultTreeModel)this._tree.getModel()).nodesWereRemoved(root, new int[] { index }, new Object[] { treeNode });
/*      */     }
/*      */     else
/*      */     {
/*  741 */       DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)this._titleNodeMap.get(dialogPage.getParentPage().getFullTitle());
/*  742 */       if (parentNode != null) {
/*  743 */         int index = parentNode.getIndex(treeNode);
/*  744 */         parentNode.remove(treeNode);
/*  745 */         if (fireEvent)
/*  746 */           ((DefaultTreeModel)this._tree.getModel()).nodesWereRemoved(parentNode, new int[] { index }, new Object[] { treeNode });
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private JComponent createListPanel()
/*      */   {
/*  753 */     DefaultListModel listModel = new DefaultListModel();
/*  754 */     for (int i = 0; i < this._pageList.getPageCount(); i++) {
/*  755 */       AbstractDialogPage optionsPanel = this._pageList.getPage(i);
/*  756 */       listModel.addElement(optionsPanel);
/*      */     }
/*      */ 
/*  759 */     JList list = createList(listModel);
/*  760 */     if (list.getModel().getSize() > 0) {
/*  761 */       list.setSelectedIndex(0);
/*      */     }
/*  763 */     list.addListSelectionListener(new ListSelectionListener(list) {
/*      */       public void valueChanged(ListSelectionEvent e) {
/*  765 */         if (this.val$list.getSelectedValue() == MultiplePageDialogPane.this.getCurrentPage()) {
/*  766 */           return;
/*      */         }
/*  768 */         if (!e.getValueIsAdjusting()) {
/*  769 */           AbstractDialogPage page = (AbstractDialogPage)this.val$list.getSelectedValue();
/*  770 */           if (page != null) {
/*  771 */             MultiplePageDialogPane.this.setCurrentPage(page, this.val$list);
/*  772 */             if (MultiplePageDialogPane.this.getCurrentPage() != page)
/*  773 */               this.val$list.setSelectedValue(MultiplePageDialogPane.this.getCurrentPage(), true);
/*      */           }
/*      */           else
/*      */           {
/*  777 */             this.val$list.setSelectedIndex(e.getLastIndex());
/*      */           }
/*      */         }
/*      */       }
/*      */     });
/*  782 */     list.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 10));
/*      */ 
/*  784 */     this._pageList.addListDataListener(new ListDataListener(listModel, list) {
/*      */       public void intervalAdded(ListDataEvent e) {
/*  786 */         for (int i = e.getIndex0(); i <= e.getIndex1(); i++) {
/*  787 */           AbstractDialogPage optionsPanel = MultiplePageDialogPane.this._pageList.getPage(i);
/*  788 */           this.val$listModel.add(i, optionsPanel);
/*      */         }
/*      */       }
/*      */ 
/*      */       public void intervalRemoved(ListDataEvent e) {
/*  793 */         for (int i = e.getIndex1(); i >= e.getIndex0(); i--)
/*  794 */           this.val$listModel.remove(i);
/*      */       }
/*      */ 
/*      */       public void contentsChanged(ListDataEvent e)
/*      */       {
/*  799 */         if ((e.getIndex0() == -1) && (e.getIndex1() == -1) && (e.getType() == 0)) {
/*  800 */           int index = MultiplePageDialogPane.this._pageList.getPageIndexByFullTitle(MultiplePageDialogPane.this._pageList.getCurrentPage().getFullTitle());
/*  801 */           this.val$list.setSelectedIndex(index);
/*      */         }
/*      */       }
/*      */     });
/*  806 */     JComponent indexPanel = new JPanel(new BorderLayout(4, 4));
/*  807 */     indexPanel.add(new JideScrollPane(list), "Center");
/*  808 */     indexPanel.setOpaque(false);
/*  809 */     return indexPanel;
/*      */   }
/*      */ 
/*      */   protected JList createList(DefaultListModel listModel)
/*      */   {
/*  826 */     JList list = new JList(listModel);
/*  827 */     list.setCellRenderer(createListCellRenderer());
/*  828 */     return list;
/*      */   }
/*      */ 
/*      */   protected JComponent createIconPanel()
/*      */   {
/*  838 */     ButtonPanel buttonsPanel = createIconButtonPanel();
/*  839 */     buttonsPanel.setGroupGap(0);
/*  840 */     buttonsPanel.setButtonGap(0);
/*      */ 
/*  842 */     ButtonGroup group = new ButtonGroup();
/*  843 */     for (int i = 0; i < this._pageList.getPageCount(); i++) {
/*  844 */       AbstractDialogPage optionsPanel = this._pageList.getPage(i);
/*  845 */       JideButton button = createIconButton(optionsPanel.getTitle(), optionsPanel.getIcon());
/*  846 */       button.setToolTipText(optionsPanel.getDescription());
/*  847 */       button.setEnabled(optionsPanel.isPageEnabled());
/*  848 */       button.addActionListener(new AbstractAction(optionsPanel, buttonsPanel, group, button) {
/*      */         public void actionPerformed(ActionEvent e) {
/*  850 */           MultiplePageDialogPane.this.setCurrentPage(this.val$optionsPanel, this.val$buttonsPanel);
/*  851 */           if (MultiplePageDialogPane.this.getCurrentPage() == this.val$optionsPanel)
/*  852 */             this.val$group.setSelected(this.val$button.getModel(), true);
/*      */         }
/*      */       });
/*  856 */       optionsPanel.addPropertyChangeListener(new PropertyChangeListener(button) {
/*      */         public void propertyChange(PropertyChangeEvent evt) {
/*  858 */           if ("enabled".equals(evt.getPropertyName())) {
/*  859 */             this.val$button.setEnabled(Boolean.TRUE.equals(evt.getNewValue()));
/*      */           }
/*  861 */           else if ("icon".equals(evt.getPropertyName())) {
/*  862 */             this.val$button.setIcon((Icon)evt.getNewValue());
/*      */           }
/*  864 */           else if ("title".equals(evt.getPropertyName())) {
/*  865 */             this.val$button.setText((String)evt.getNewValue());
/*      */           }
/*  867 */           else if ("description".equals(evt.getPropertyName()))
/*  868 */             this.val$button.setToolTipText((String)evt.getNewValue());
/*      */         }
/*      */       });
/*  872 */       buttonsPanel.addButton(button);
/*  873 */       group.add(button);
/*  874 */       if (i == 0) {
/*  875 */         group.setSelected(button.getModel(), true);
/*      */       }
/*      */     }
/*      */ 
/*  879 */     buttonsPanel.setOpaque(false);
/*  880 */     buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
/*      */ 
/*  882 */     JScrollPane pane = new JScrollPane(buttonsPanel, buttonsPanel)
/*      */     {
/*      */       public Dimension getPreferredSize() {
/*  885 */         if ((this.val$buttonsPanel.getAlignment() == 1) || (this.val$buttonsPanel.getAlignment() == 3)) {
/*  886 */           return new Dimension(this.val$buttonsPanel.getPreferredSize().width + getVerticalScrollBar().getPreferredSize().width, 5);
/*      */         }
/*  888 */         return new Dimension(5, this.val$buttonsPanel.getPreferredSize().height + getHorizontalScrollBar().getPreferredSize().height);
/*      */       }
/*      */ 
/*      */       public Dimension getMinimumSize()
/*      */       {
/*  893 */         return getPreferredSize();
/*      */       }
/*      */     };
/*  897 */     pane.setHorizontalScrollBarPolicy(31);
/*      */ 
/*  899 */     buttonsPanel.setOpaque(false);
/*      */ 
/*  901 */     this._pageList.addListDataListener(new ListDataListener(group, buttonsPanel) {
/*      */       public void intervalAdded(ListDataEvent e) {
/*  903 */         for (int i = e.getIndex0(); i <= e.getIndex1(); i++) {
/*  904 */           MultiplePageDialogPane.this.addPage(i, this.val$group, this.val$buttonsPanel);
/*      */         }
/*  906 */         this.val$buttonsPanel.invalidate();
/*  907 */         this.val$buttonsPanel.doLayout();
/*      */       }
/*      */ 
/*      */       public void intervalRemoved(ListDataEvent e) {
/*  911 */         for (int i = e.getIndex1(); i >= e.getIndex0(); i--) {
/*  912 */           AbstractButton button = (AbstractButton)this.val$buttonsPanel.getComponent(i);
/*  913 */           this.val$buttonsPanel.remove(button);
/*  914 */           this.val$group.remove(button);
/*      */         }
/*  916 */         this.val$buttonsPanel.invalidate();
/*  917 */         this.val$buttonsPanel.doLayout();
/*      */       }
/*      */ 
/*      */       public void contentsChanged(ListDataEvent e) {
/*  921 */         if ((e.getIndex0() == -1) && (e.getIndex1() == -1) && (e.getType() == 0)) {
/*  922 */           AbstractButton button = (AbstractButton)this.val$buttonsPanel.getButtonByName(MultiplePageDialogPane.this._pageList.getCurrentPage().getTitle());
/*  923 */           if (button != null)
/*  924 */             this.val$group.setSelected(button.getModel(), true);
/*      */         }
/*      */       }
/*      */     });
/*  930 */     pane.getViewport().setOpaque(false);
/*  931 */     return pane;
/*      */   }
/*      */ 
/*      */   protected ButtonPanel createIconButtonPanel()
/*      */   {
/*  941 */     return new ScrollableButtonPanel(1, 0);
/*      */   }
/*      */ 
/*      */   private JideButton addPage(int i, ButtonGroup group, ButtonPanel buttonsPanel) {
/*  945 */     AbstractDialogPage optionsPanel = this._pageList.getPage(i);
/*  946 */     JideButton button = createIconButton(optionsPanel.getTitle(), optionsPanel.getIcon());
/*  947 */     button.addActionListener(new AbstractAction(optionsPanel.getTitle(), optionsPanel.getIcon(), group, button, buttonsPanel) {
/*      */       public void actionPerformed(ActionEvent e) {
/*  949 */         this.val$group.setSelected(this.val$button.getModel(), true);
/*  950 */         MultiplePageDialogPane.this.setCurrentPage(MultiplePageDialogPane.this._pageList.getPageByFullTitle(e.getActionCommand()), this.val$buttonsPanel);
/*      */       }
/*      */     });
/*  953 */     buttonsPanel.addButton(button, i);
/*  954 */     group.add(button);
/*  955 */     return button;
/*      */   }
/*      */ 
/*      */   protected JideButton createIconButton(String title, Icon icon)
/*      */   {
/*  966 */     JideButton button = new JideButton(title, icon);
/*  967 */     button.setName(title);
/*  968 */     button.setHorizontalAlignment(0);
/*  969 */     button.setVerticalTextPosition(3);
/*  970 */     button.setHorizontalTextPosition(0);
/*  971 */     button.setRequestFocusEnabled(false);
/*  972 */     button.setFocusable(false);
/*  973 */     return button;
/*      */   }
/*      */ 
/*      */   public int getStyle()
/*      */   {
/*  982 */     return this._style;
/*      */   }
/*      */ 
/*      */   public void setStyle(int style)
/*      */   {
/*  992 */     if ((style == 0) || (style == 2) || (style == 3) || (style == 1)) {
/*  993 */       this._style = style;
/*      */     }
/*      */     else
/*  996 */       throw new IllegalArgumentException("The value of style must be one of the following - TAB_STYLE, ICON_STYLE, LIST_STYLE or TREE_STYLE");
/*      */   }
/*      */ 
/*      */   public JComponent getIndexPanel()
/*      */   {
/* 1006 */     return this._indexPanel;
/*      */   }
/*      */ 
/*      */   public JComponent getPagesPanel()
/*      */   {
/* 1015 */     return this._pagesPanel;
/*      */   }
/*      */ 
/*      */   protected TreeCellRenderer getTreeCellRenderer()
/*      */   {
/* 1024 */     return this._treeCellRenderer;
/*      */   }
/*      */ 
/*      */   public void setTreeCellRenderer(TreeCellRenderer treeCellRenderer)
/*      */   {
/* 1033 */     this._treeCellRenderer = treeCellRenderer;
/*      */   }
/*      */ 
/*      */   protected ListCellRenderer getListCellRenderer()
/*      */   {
/* 1042 */     return this._listCellRenderer;
/*      */   }
/*      */ 
/*      */   public void setListCellRenderer(ListCellRenderer listCellRenderer)
/*      */   {
/* 1051 */     this._listCellRenderer = listCellRenderer;
/*      */   }
/*      */ 
/*      */   protected ListCellRenderer createListCellRenderer()
/*      */   {
/* 1060 */     if (getListCellRenderer() == null) {
/* 1061 */       setListCellRenderer(new DialogPageListCellRenderer());
/*      */     }
/* 1063 */     return getListCellRenderer();
/*      */   }
/*      */ 
/*      */   protected TreeCellRenderer createTreeCellRenderer()
/*      */   {
/* 1072 */     if (getTreeCellRenderer() == null) {
/* 1073 */       setTreeCellRenderer(new DialogPageTreeCellRenderer());
/*      */     }
/* 1075 */     return getTreeCellRenderer();
/*      */   }
/*      */ 
/*      */   public String getInitialPageTitle()
/*      */   {
/* 1086 */     return this._initialPageTitle;
/*      */   }
/*      */ 
/*      */   public void setInitialPageTitle(String initialPageTitle)
/*      */   {
/* 1095 */     this._initialPageTitle = initialPageTitle;
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.dialog.MultiplePageDialogPane
 * JD-Core Version:    0.6.0
 */
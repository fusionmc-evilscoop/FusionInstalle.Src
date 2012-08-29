/*      */ package com.jidesoft.dialog;
/*      */ 
/*      */ import com.jidesoft.plaf.UIDefaultsLookup;
/*      */ import com.jidesoft.swing.JideButton;
/*      */ import com.jidesoft.swing.JideScrollPane;
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.CardLayout;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Dialog;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Frame;
/*      */ import java.awt.HeadlessException;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
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
/*      */ import javax.swing.JRootPane;
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
/*      */ public class MultiplePageDialog extends StandardDialog
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
/*      */   private Map<String, MutableTreeNode> _titleNodeMap;
/*      */   private JButton _okButton;
/*      */   private JButton _cancelButton;
/*      */   private JButton _applyButton;
/*      */   private TreeCellRenderer _treeCellRenderer;
/*      */   private ListCellRenderer _listCellRenderer;
/*      */   private JTabbedPane _tabbedPane;
/*      */   private String _initialPageTitle;
/*      */   public JTree _tree;
/*      */ 
/*      */   public MultiplePageDialog()
/*      */     throws HeadlessException
/*      */   {
/*  103 */     this((Frame)null);
/*      */   }
/*      */ 
/*      */   public MultiplePageDialog(Frame owner)
/*      */     throws HeadlessException
/*      */   {
/*  115 */     this(owner, false);
/*      */   }
/*      */ 
/*      */   public MultiplePageDialog(Frame owner, boolean modal)
/*      */     throws HeadlessException
/*      */   {
/*  128 */     this(owner, "", modal);
/*      */   }
/*      */ 
/*      */   public MultiplePageDialog(Frame owner, String title)
/*      */     throws HeadlessException
/*      */   {
/*  142 */     this(owner, title, true);
/*      */   }
/*      */ 
/*      */   public MultiplePageDialog(Frame owner, String title, boolean modal)
/*      */     throws HeadlessException
/*      */   {
/*  157 */     this(owner, title, modal, 0);
/*      */   }
/*      */ 
/*      */   public MultiplePageDialog(Frame owner, String title, boolean modal, int style)
/*      */     throws HeadlessException
/*      */   {
/*  174 */     super(owner, title, modal);
/*  175 */     setStyle(style);
/*      */   }
/*      */ 
/*      */   public MultiplePageDialog(Dialog owner)
/*      */     throws HeadlessException
/*      */   {
/*  187 */     this(owner, false);
/*      */   }
/*      */ 
/*      */   public MultiplePageDialog(Dialog owner, boolean modal)
/*      */     throws HeadlessException
/*      */   {
/*  200 */     this(owner, "", modal);
/*      */   }
/*      */ 
/*      */   public MultiplePageDialog(Dialog owner, String title)
/*      */     throws HeadlessException
/*      */   {
/*  214 */     this(owner, title, true);
/*      */   }
/*      */ 
/*      */   public MultiplePageDialog(Dialog owner, String title, boolean modal)
/*      */     throws HeadlessException
/*      */   {
/*  229 */     this(owner, title, modal, 0);
/*      */   }
/*      */ 
/*      */   public MultiplePageDialog(Dialog owner, String title, boolean modal, int style)
/*      */     throws HeadlessException
/*      */   {
/*  246 */     super(owner, title, modal);
/*  247 */     setStyle(style);
/*      */   }
/*      */ 
/*      */   public JComponent createBannerPanel()
/*      */   {
/*  257 */     return null;
/*      */   }
/*      */ 
/*      */   public JComponent createContentPanel()
/*      */   {
/*  268 */     this._indexPanel = createIndexPanel();
/*  269 */     this._pagesPanel = createPagesPanel();
/*  270 */     if (this._pageList.getPageCount() > 0) {
/*  271 */       if (getInitialPageTitle() != null) {
/*  272 */         setCurrentPage(getInitialPageTitle());
/*      */       }
/*      */       else {
/*  275 */         setCurrentPage(this._pageList.getPage(0));
/*      */       }
/*      */     }
/*  278 */     return setupContentPanel(this._indexPanel, this._pagesPanel);
/*      */   }
/*      */ 
/*      */   protected JComponent setupContentPanel(JComponent indexPanel, JComponent pagesPanel)
/*      */   {
/*  290 */     JPanel middlePanel = new JPanel(new BorderLayout(10, 10));
/*  291 */     if (indexPanel != null) {
/*  292 */       middlePanel.add(indexPanel, "Before");
/*      */     }
/*  294 */     if (pagesPanel != null) {
/*  295 */       middlePanel.add(pagesPanel, "Center");
/*      */     }
/*  297 */     return middlePanel;
/*      */   }
/*      */ 
/*      */   public ButtonPanel createButtonPanel()
/*      */   {
/*  308 */     ButtonPanel buttonPanel = new ButtonPanel();
/*  309 */     this._okButton = new JButton();
/*  310 */     this._cancelButton = new JButton();
/*  311 */     this._applyButton = new JButton();
/*  312 */     this._okButton.setName("OK");
/*  313 */     this._cancelButton.setName("CANCEL");
/*  314 */     this._applyButton.setName("APPLY");
/*  315 */     buttonPanel.addButton(this._okButton, "AFFIRMATIVE");
/*  316 */     buttonPanel.addButton(this._cancelButton, "CANCEL");
/*  317 */     buttonPanel.addButton(this._applyButton, "ALTERNATIVE");
/*      */ 
/*  319 */     Locale l = getLocale();
/*  320 */     this._okButton.setAction(new AbstractAction(UIDefaultsLookup.getString("OptionPane.okButtonText", l)) {
/*      */       private static final long serialVersionUID = 7761238902525319363L;
/*      */ 
/*  324 */       public void actionPerformed(ActionEvent e) { MultiplePageDialog.this.setDialogResult(0);
/*  325 */         MultiplePageDialog.this.setVisible(false);
/*  326 */         MultiplePageDialog.this.dispose();
/*      */       }
/*      */     });
/*  329 */     this._cancelButton.setAction(new AbstractAction(UIDefaultsLookup.getString("OptionPane.cancelButtonText", l)) {
/*      */       private static final long serialVersionUID = 2671605366801733356L;
/*      */ 
/*  333 */       public void actionPerformed(ActionEvent e) { MultiplePageDialog.this.setDialogResult(-1);
/*  334 */         MultiplePageDialog.this.setVisible(false);
/*  335 */         MultiplePageDialog.this.dispose();
/*      */       }
/*      */     });
/*  338 */     this._applyButton.setAction(new AbstractAction(ButtonResources.getResourceBundle(Locale.getDefault()).getString("Button.apply")) {
/*      */       private static final long serialVersionUID = -7553895212164069062L;
/*      */ 
/*  342 */       public void actionPerformed(ActionEvent e) { if (MultiplePageDialog.this.getCurrentPage() != null)
/*  343 */           MultiplePageDialog.this.getCurrentPage().fireButtonEvent(3302, "APPLY");
/*      */       }
/*      */     });
/*  347 */     this._applyButton.setMnemonic(ButtonResources.getResourceBundle(Locale.getDefault()).getString("Button.apply.mnemonic").charAt(0));
/*  348 */     this._applyButton.setEnabled(false);
/*      */ 
/*  350 */     setDefaultCancelAction(this._cancelButton.getAction());
/*  351 */     setDefaultAction(this._okButton.getAction());
/*  352 */     getRootPane().setDefaultButton(this._okButton);
/*  353 */     return buttonPanel;
/*      */   }
/*      */ 
/*      */   public JButton getOkButton()
/*      */   {
/*  362 */     return this._okButton;
/*      */   }
/*      */ 
/*      */   public JButton getCancelButton()
/*      */   {
/*  371 */     return this._cancelButton;
/*      */   }
/*      */ 
/*      */   public JButton getApplyButton()
/*      */   {
/*  380 */     return this._applyButton;
/*      */   }
/*      */ 
/*      */   protected JComponent createPagesPanel()
/*      */   {
/*  390 */     if (this._style == 0) {
/*  391 */       this._tabbedPane = createTabbedPane();
/*  392 */       this._tabbedPane.addChangeListener(new ChangeListener() {
/*      */         public void stateChanged(ChangeEvent e) {
/*  394 */           Component selectedComponent = MultiplePageDialog.this._tabbedPane.getSelectedComponent();
/*  395 */           if ((selectedComponent instanceof AbstractDialogPage))
/*  396 */             MultiplePageDialog.this.setCurrentPage((AbstractDialogPage)selectedComponent, MultiplePageDialog.this._tabbedPane);
/*      */         }
/*      */       });
/*  400 */       for (int i = 0; i < this._pageList.getPageCount(); i++) {
/*  401 */         AbstractDialogPage page = this._pageList.getPage(i);
/*  402 */         page.addButtonListener(getButtonPanel());
/*  403 */         this._tabbedPane.addTab(page.getTitle(), page.getIcon(), page, page.getDescription());
/*  404 */         this._tabbedPane.setEnabledAt(i, page.isPageEnabled());
/*  405 */         int index = i;
/*  406 */         page.addPropertyChangeListener(new PropertyChangeListener(index) {
/*      */           public void propertyChange(PropertyChangeEvent evt) {
/*  408 */             if ("enabled".equals(evt.getPropertyName())) {
/*  409 */               MultiplePageDialog.this._tabbedPane.setEnabledAt(this.val$index, Boolean.TRUE.equals(evt.getNewValue()));
/*      */             }
/*  411 */             else if ("icon".equals(evt.getPropertyName())) {
/*  412 */               MultiplePageDialog.this._tabbedPane.setIconAt(this.val$index, (Icon)evt.getNewValue());
/*      */             }
/*  414 */             else if ("title".equals(evt.getPropertyName())) {
/*  415 */               MultiplePageDialog.this._tabbedPane.setTitleAt(this.val$index, (String)evt.getNewValue());
/*      */             }
/*  417 */             else if ("description".equals(evt.getPropertyName()))
/*  418 */               MultiplePageDialog.this._tabbedPane.setToolTipTextAt(this.val$index, (String)evt.getNewValue());
/*      */           }
/*      */         });
/*      */       }
/*  423 */       this._pageList.addListDataListener(new ListDataListener() {
/*      */         public void intervalAdded(ListDataEvent e) {
/*  425 */           for (int i = e.getIndex0(); i <= e.getIndex1(); i++) {
/*  426 */             AbstractDialogPage page = MultiplePageDialog.this._pageList.getPage(i);
/*  427 */             MultiplePageDialog.this._tabbedPane.insertTab(page.getTitle(), page.getIcon(), page, page.getDescription(), i);
/*      */           }
/*      */         }
/*      */ 
/*      */         public void intervalRemoved(ListDataEvent e) {
/*  432 */           for (int i = e.getIndex1(); i >= e.getIndex0(); i--)
/*  433 */             MultiplePageDialog.this._tabbedPane.removeTabAt(i);
/*      */         }
/*      */ 
/*      */         public void contentsChanged(ListDataEvent e)
/*      */         {
/*      */         }
/*      */       });
/*  440 */       return this._tabbedPane;
/*      */     }
/*      */ 
/*  443 */     JPanel pagesPanel = new JPanel();
/*  444 */     this._cardLayout = new CardLayout();
/*  445 */     pagesPanel.setLayout(this._cardLayout);
/*      */ 
/*  447 */     for (int i = 0; i < this._pageList.getPageCount(); i++) {
/*  448 */       AbstractDialogPage page = this._pageList.getPage(i);
/*  449 */       page.addButtonListener(getButtonPanel());
/*  450 */       page.setName(page.getFullTitle());
/*  451 */       page.addPropertyChangeListener(new PropertyChangeListener(pagesPanel) {
/*      */         public void propertyChange(PropertyChangeEvent evt) {
/*  453 */           if ("title".equals(evt.getPropertyName()))
/*  454 */             for (int j = 0; j < this.val$pagesPanel.getComponentCount(); j++) {
/*  455 */               Component c = this.val$pagesPanel.getComponent(j);
/*  456 */               boolean wasVisible = c.isVisible();
/*  457 */               Object source = evt.getSource();
/*  458 */               if (((source instanceof AbstractDialogPage)) && (c == source)) {
/*  459 */                 this.val$pagesPanel.remove(j);
/*  460 */                 String fullTitle = ((AbstractDialogPage)source).getFullTitle();
/*  461 */                 this.val$pagesPanel.add((AbstractDialogPage)source, fullTitle, j);
/*  462 */                 ((AbstractDialogPage)source).setName(fullTitle);
/*  463 */                 MultiplePageDialog.this.getIndexPanel().repaint();
/*  464 */                 if (!wasVisible) break;
/*  465 */                 MultiplePageDialog.this._cardLayout.show(this.val$pagesPanel, fullTitle); break;
/*      */               }
/*      */             }
/*      */         }
/*      */       });
/*  474 */       pagesPanel.add(page, page.getFullTitle());
/*      */     }
/*      */ 
/*  477 */     this._pageList.addListDataListener(new ListDataListener(pagesPanel) {
/*      */       public void intervalAdded(ListDataEvent e) {
/*  479 */         for (int i = e.getIndex0(); i <= e.getIndex1(); i++) {
/*  480 */           AbstractDialogPage page = MultiplePageDialog.this._pageList.getPage(i);
/*  481 */           page.setName(page.getFullTitle());
/*  482 */           this.val$pagesPanel.add(page, page.getFullTitle(), i);
/*      */         }
/*      */       }
/*      */ 
/*      */       public void intervalRemoved(ListDataEvent e) {
/*  487 */         for (int i = e.getIndex1(); i >= e.getIndex0(); i--)
/*  488 */           this.val$pagesPanel.remove(i);
/*      */       }
/*      */ 
/*      */       public void contentsChanged(ListDataEvent e)
/*      */       {
/*  501 */         if ((e.getSource() instanceof PageList)) {
/*  502 */           Object o = ((PageList)e.getSource()).getSelectedItem();
/*  503 */           if (((o instanceof AbstractDialogPage)) && (((AbstractDialogPage)o).isPageEnabled()))
/*  504 */             MultiplePageDialog.this.setCurrentPage((AbstractDialogPage)o);
/*      */         }
/*      */       }
/*      */     });
/*  509 */     return pagesPanel;
/*      */   }
/*      */ 
/*      */   protected JTabbedPane createTabbedPane()
/*      */   {
/*  519 */     return new JTabbedPane(1);
/*      */   }
/*      */ 
/*      */   public JComponent createIndexPanel()
/*      */   {
/*  528 */     switch (this._style) {
/*      */     case 3:
/*  530 */       return createIconPanel();
/*      */     case 2:
/*  532 */       return createListPanel();
/*      */     case 1:
/*  534 */       return createTreePanel();
/*      */     case 0:
/*      */     }
/*  537 */     return null;
/*      */   }
/*      */ 
/*      */   public void setPageList(PageList pageList)
/*      */   {
/*  547 */     this._pageList = pageList;
/*      */   }
/*      */ 
/*      */   public PageList getPageList()
/*      */   {
/*  556 */     return this._pageList;
/*      */   }
/*      */ 
/*      */   public AbstractDialogPage getCurrentPage()
/*      */   {
/*  565 */     return this._pageList.getCurrentPage();
/*      */   }
/*      */ 
/*      */   protected void setCurrentPage(String pageTitle) {
/*  569 */     if (this._pageList != null)
/*  570 */       setCurrentPage(this._pageList.getPageByFullTitle(pageTitle));
/*      */   }
/*      */ 
/*      */   protected void setCurrentPage(AbstractDialogPage currentPage)
/*      */   {
/*  575 */     setCurrentPage(currentPage, null);
/*      */   }
/*      */ 
/*      */   protected void setCurrentPage(AbstractDialogPage currentPage, Object source) {
/*  579 */     if (!this._pageList.setCurrentPage(currentPage, source)) {
/*  580 */       return;
/*      */     }
/*      */ 
/*  583 */     if (currentPage != null)
/*  584 */       showCurrentPage(currentPage);
/*      */   }
/*      */ 
/*      */   protected void showCurrentPage(AbstractDialogPage currentPage)
/*      */   {
/*  596 */     if (currentPage != null) {
/*  597 */       if (getStyle() == 0) {
/*  598 */         this._tabbedPane.setSelectedComponent(currentPage);
/*      */       }
/*      */       else {
/*  601 */         this._cardLayout.show(this._pagesPanel, currentPage.getFullTitle());
/*      */       }
/*  603 */       currentPage.focusDefaultFocusComponent();
/*      */     }
/*      */   }
/*      */ 
/*      */   private JComponent createTreePanel() {
/*  608 */     DefaultMutableTreeNode root = new DefaultMutableTreeNode("", true);
/*      */ 
/*  610 */     this._titleNodeMap = new HashMap((int)(this._pageList.getPageCount() * 0.75D));
/*  611 */     for (int i = 0; i < this._pageList.getPageCount(); i++) {
/*  612 */       AbstractDialogPage dialogPage = this._pageList.getPage(i);
/*  613 */       addPage(dialogPage, root, false);
/*      */     }
/*      */ 
/*  616 */     this._tree = createTree(root);
/*  617 */     configureTree(this._tree);
/*  618 */     this._pageList.addListDataListener(new ListDataListener() {
/*      */       public void intervalAdded(ListDataEvent e) {
/*  620 */         for (int i = e.getIndex0(); i <= e.getIndex1(); i++) {
/*  621 */           AbstractDialogPage dialogPage = MultiplePageDialog.this._pageList.getPage(i);
/*  622 */           MultiplePageDialog.this.addPage(dialogPage, (DefaultMutableTreeNode)MultiplePageDialog.this._tree.getModel().getRoot(), true);
/*      */         }
/*      */       }
/*      */ 
/*      */       public void intervalRemoved(ListDataEvent e)
/*      */       {
/*  628 */         Set set = MultiplePageDialog.this._titleNodeMap.keySet();
/*  629 */         Vector toBeRemoved = new Vector();
/*  630 */         for (String title : set) {
/*  631 */           if (MultiplePageDialog.this._pageList.getPageByFullTitle(title) == null) {
/*  632 */             DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)MultiplePageDialog.this._titleNodeMap.get(title);
/*  633 */             if (treeNode != null) {
/*  634 */               toBeRemoved.add(title);
/*  635 */               DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)treeNode.getParent();
/*  636 */               if (parentNode != null) {
/*  637 */                 int index = parentNode.getIndex(treeNode);
/*  638 */                 parentNode.remove(treeNode);
/*  639 */                 ((DefaultTreeModel)MultiplePageDialog.this._tree.getModel()).nodesWereRemoved(parentNode, new int[] { index }, new Object[] { treeNode });
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*  644 */         for (String o : toBeRemoved)
/*  645 */           MultiplePageDialog.this._titleNodeMap.remove(o);
/*      */       }
/*      */ 
/*      */       public void contentsChanged(ListDataEvent e)
/*      */       {
/*  650 */         if ((e.getIndex0() == -1) && (e.getIndex1() == -1) && (e.getType() == 0) && 
/*  651 */           (MultiplePageDialog.this._titleNodeMap != null) && (MultiplePageDialog.this._pageList.getCurrentPage() != null)) {
/*  652 */           TreeNode node = (TreeNode)MultiplePageDialog.this._titleNodeMap.get(MultiplePageDialog.this._pageList.getCurrentPage().getFullTitle());
/*  653 */           if (node != null) {
/*  654 */             ArrayList list = new ArrayList();
/*  655 */             while (node != null) {
/*  656 */               list.add(0, node);
/*  657 */               node = node.getParent();
/*      */             }
/*  659 */             TreePath treePath = new TreePath(list.toArray(new TreeNode[list.size()]));
/*  660 */             MultiplePageDialog.this._tree.getSelectionModel().setSelectionPath(treePath);
/*      */           }
/*      */         }
/*      */       }
/*      */     });
/*  667 */     JComponent indexPanel = new JPanel(new BorderLayout());
/*  668 */     indexPanel.add(new JScrollPane(this._tree), "Center");
/*  669 */     return indexPanel;
/*      */   }
/*      */ 
/*      */   protected JTree createTree(DefaultMutableTreeNode root)
/*      */   {
/*  685 */     UIManager.put("Tree.hash", Color.white);
/*  686 */     return new JTree(root);
/*      */   }
/*      */ 
/*      */   protected void configureTree(JTree tree)
/*      */   {
/*  732 */     tree.setToggleClickCount(1);
/*  733 */     tree.setCellRenderer(createTreeCellRenderer());
/*  734 */     tree.setRootVisible(false);
/*  735 */     tree.setShowsRootHandles(false);
/*  736 */     tree.addTreeSelectionListener(new TreeSelectionListener(tree) {
/*      */       public void valueChanged(TreeSelectionEvent e) {
/*  738 */         if (this.val$tree.getSelectionPath() == null) {
/*  739 */           return;
/*      */         }
/*      */ 
/*  742 */         DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)this.val$tree.getSelectionPath().getLastPathComponent();
/*      */ 
/*  745 */         while (!treeNode.isLeaf()) {
/*  746 */           DefaultMutableTreeNode tn = treeNode;
/*  747 */           Runnable runnable = new Runnable(tn) {
/*      */             public void run() {
/*  749 */               MultiplePageDialog.10.this.val$tree.expandPath(new TreePath(this.val$tn.getPath()));
/*      */             }
/*      */           };
/*  752 */           SwingUtilities.invokeLater(runnable);
/*  753 */           treeNode = (DefaultMutableTreeNode)treeNode.getChildAt(0);
/*      */         }
/*      */ 
/*  756 */         Object userObject = treeNode.getUserObject();
/*  757 */         if (((userObject instanceof AbstractDialogPage)) && (!userObject.equals(MultiplePageDialog.this.getCurrentPage())) && (((AbstractDialogPage)userObject).isPageEnabled())) { MultiplePageDialog.this.setCurrentPage((AbstractDialogPage)userObject, this.val$tree);
/*  759 */           if (MultiplePageDialog.this.getCurrentPage() == userObject);
/*      */         }
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   private void addPage(AbstractDialogPage dialogPage, DefaultMutableTreeNode root, boolean fireEvent) {
/*  768 */     if (dialogPage == null) {
/*  769 */       return;
/*      */     }
/*      */ 
/*  772 */     MutableTreeNode treeNode = createTreeNode(dialogPage);
/*  773 */     if ((treeNode instanceof MutableTreeNodeEx)) {
/*  774 */       ((MutableTreeNodeEx)treeNode).setEnabled(dialogPage.isPageEnabled());
/*      */     }
/*  776 */     if (dialogPage.getParentPage() == null) {
/*  777 */       this._titleNodeMap.put(dialogPage.getFullTitle(), treeNode);
/*  778 */       root.add(treeNode);
/*  779 */       if (fireEvent)
/*  780 */         ((DefaultTreeModel)this._tree.getModel()).nodesWereInserted(root, new int[] { root.getIndex(treeNode) });
/*      */     }
/*      */     else
/*      */     {
/*  784 */       this._titleNodeMap.put(dialogPage.getFullTitle(), treeNode);
/*  785 */       DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)this._titleNodeMap.get(dialogPage.getParentPage().getFullTitle());
/*  786 */       if (parentNode != null) {
/*  787 */         parentNode.add(treeNode);
/*  788 */         if (fireEvent) {
/*  789 */           ((DefaultTreeModel)this._tree.getModel()).nodesWereInserted(parentNode, new int[] { parentNode.getIndex(treeNode) });
/*      */         }
/*      */       }
/*      */     }
/*  793 */     dialogPage.addPropertyChangeListener(new PropertyChangeListener(treeNode, dialogPage) {
/*      */       public void propertyChange(PropertyChangeEvent evt) {
/*  795 */         if (("icon".equals(evt.getPropertyName())) && ((this.val$treeNode instanceof MutableTreeNodeEx)))
/*  796 */           ((MutableTreeNodeEx)this.val$treeNode).setEnabled(this.val$dialogPage.isPageEnabled());
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   protected MutableTreeNode createTreeNode(AbstractDialogPage dialogPage)
/*      */   {
/*  809 */     return new MutableTreeNodeEx(dialogPage);
/*      */   }
/*      */ 
/*      */   private JComponent createListPanel()
/*      */   {
/*  849 */     DefaultListModel listModel = new DefaultListModel();
/*  850 */     for (int i = 0; i < this._pageList.getPageCount(); i++) {
/*  851 */       AbstractDialogPage optionsPanel = this._pageList.getPage(i);
/*  852 */       listModel.addElement(optionsPanel);
/*      */     }
/*      */ 
/*  855 */     JList list = createList(listModel);
/*  856 */     if (list.getModel().getSize() > 0) {
/*  857 */       list.setSelectedIndex(0);
/*      */     }
/*  859 */     list.addListSelectionListener(new ListSelectionListener(list) {
/*      */       public void valueChanged(ListSelectionEvent e) {
/*  861 */         if (this.val$list.getSelectedValue() == MultiplePageDialog.this.getCurrentPage()) {
/*  862 */           return;
/*      */         }
/*  864 */         if (!e.getValueIsAdjusting()) {
/*  865 */           AbstractDialogPage page = (AbstractDialogPage)this.val$list.getSelectedValue();
/*  866 */           if (page != null) {
/*  867 */             MultiplePageDialog.this.setCurrentPage(page, this.val$list);
/*  868 */             if (MultiplePageDialog.this.getCurrentPage() != page)
/*  869 */               this.val$list.setSelectedValue(MultiplePageDialog.this.getCurrentPage(), true);
/*      */           }
/*      */           else
/*      */           {
/*  873 */             this.val$list.setSelectedIndex(e.getLastIndex());
/*      */           }
/*      */         }
/*      */       }
/*      */     });
/*  878 */     list.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 10));
/*      */ 
/*  880 */     this._pageList.addListDataListener(new ListDataListener(listModel, list) {
/*      */       public void intervalAdded(ListDataEvent e) {
/*  882 */         for (int i = e.getIndex0(); i <= e.getIndex1(); i++) {
/*  883 */           AbstractDialogPage optionsPanel = MultiplePageDialog.this._pageList.getPage(i);
/*  884 */           this.val$listModel.add(i, optionsPanel);
/*      */         }
/*      */       }
/*      */ 
/*      */       public void intervalRemoved(ListDataEvent e) {
/*  889 */         for (int i = e.getIndex1(); i >= e.getIndex0(); i--)
/*  890 */           this.val$listModel.remove(i);
/*      */       }
/*      */ 
/*      */       public void contentsChanged(ListDataEvent e)
/*      */       {
/*  895 */         if ((e.getIndex0() == -1) && (e.getIndex1() == -1) && (e.getType() == 0) && 
/*  896 */           (MultiplePageDialog.this._pageList.getCurrentPage() != null)) {
/*  897 */           int index = MultiplePageDialog.this._pageList.getPageIndexByFullTitle(MultiplePageDialog.this._pageList.getCurrentPage().getFullTitle());
/*  898 */           this.val$list.setSelectedIndex(index);
/*      */         }
/*      */       }
/*      */     });
/*  904 */     JComponent indexPanel = new JPanel(new BorderLayout(4, 4));
/*  905 */     indexPanel.add(new JideScrollPane(list), "Center");
/*  906 */     indexPanel.setOpaque(false);
/*  907 */     return indexPanel;
/*      */   }
/*      */ 
/*      */   protected JList createList(DefaultListModel listModel)
/*      */   {
/*  924 */     JList list = new JList(listModel);
/*  925 */     list.setCellRenderer(createListCellRenderer());
/*  926 */     list.setSelectionMode(0);
/*  927 */     return list;
/*      */   }
/*      */ 
/*      */   protected JComponent createIconPanel()
/*      */   {
/*  937 */     ButtonPanel buttonsPanel = createIconButtonPanel();
/*  938 */     buttonsPanel.setGroupGap(0);
/*  939 */     buttonsPanel.setButtonGap(0);
/*      */ 
/*  941 */     ButtonGroup group = new ButtonGroup();
/*  942 */     for (int i = 0; i < this._pageList.getPageCount(); i++) {
/*  943 */       AbstractDialogPage optionsPanel = this._pageList.getPage(i);
/*  944 */       JideButton button = createIconButton(optionsPanel.getTitle(), optionsPanel.getIcon());
/*  945 */       button.setToolTipText(optionsPanel.getDescription());
/*  946 */       button.setEnabled(optionsPanel.isPageEnabled());
/*  947 */       button.addActionListener(new AbstractAction(optionsPanel, buttonsPanel, group, button) {
/*      */         private static final long serialVersionUID = 4451059166068761678L;
/*      */ 
/*  951 */         public void actionPerformed(ActionEvent e) { MultiplePageDialog.this.setCurrentPage(this.val$optionsPanel, this.val$buttonsPanel);
/*  952 */           if (MultiplePageDialog.this.getCurrentPage() == this.val$optionsPanel)
/*  953 */             this.val$group.setSelected(this.val$button.getModel(), true);
/*      */         }
/*      */       });
/*  957 */       optionsPanel.addPropertyChangeListener(new PropertyChangeListener(button) {
/*      */         public void propertyChange(PropertyChangeEvent evt) {
/*  959 */           if ("enabled".equals(evt.getPropertyName())) {
/*  960 */             this.val$button.setEnabled(Boolean.TRUE.equals(evt.getNewValue()));
/*      */           }
/*  962 */           else if ("icon".equals(evt.getPropertyName())) {
/*  963 */             this.val$button.setIcon((Icon)evt.getNewValue());
/*      */           }
/*  965 */           else if ("title".equals(evt.getPropertyName())) {
/*  966 */             this.val$button.setText((String)evt.getNewValue());
/*      */           }
/*  968 */           else if ("description".equals(evt.getPropertyName()))
/*  969 */             this.val$button.setToolTipText((String)evt.getNewValue());
/*      */         }
/*      */       });
/*  973 */       buttonsPanel.addButton(button);
/*  974 */       group.add(button);
/*  975 */       if (this._pageList.getPageCount() > 0) {
/*  976 */         if ((getInitialPageTitle() != null) && (getInitialPageTitle().equals(optionsPanel.getFullTitle()))) {
/*  977 */           group.setSelected(button.getModel(), true);
/*      */         }
/*  979 */         else if ((getInitialPageTitle() == null) && (i == 0)) {
/*  980 */           group.setSelected(button.getModel(), true);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  985 */     buttonsPanel.setOpaque(false);
/*  986 */     buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
/*      */ 
/*  988 */     JScrollPane pane = new JScrollPane(buttonsPanel, buttonsPanel)
/*      */     {
/*      */       public Dimension getPreferredSize() {
/*  991 */         if ((this.val$buttonsPanel.getAlignment() == 1) || (this.val$buttonsPanel.getAlignment() == 3)) {
/*  992 */           return new Dimension(this.val$buttonsPanel.getPreferredSize().width + getVerticalScrollBar().getPreferredSize().width, 5);
/*      */         }
/*  994 */         return new Dimension(5, this.val$buttonsPanel.getPreferredSize().height + getHorizontalScrollBar().getPreferredSize().height);
/*      */       }
/*      */ 
/*      */       public Dimension getMinimumSize()
/*      */       {
/*  999 */         return getPreferredSize();
/*      */       }
/*      */     };
/* 1003 */     if ((buttonsPanel.getAlignment() == 1) || (buttonsPanel.getAlignment() == 3))
/* 1004 */       pane.setHorizontalScrollBarPolicy(31);
/*      */     else {
/* 1006 */       pane.setVerticalScrollBarPolicy(21);
/*      */     }
/* 1008 */     buttonsPanel.setOpaque(false);
/*      */ 
/* 1010 */     this._pageList.addListDataListener(new ListDataListener(group, buttonsPanel) {
/*      */       public void intervalAdded(ListDataEvent e) {
/* 1012 */         for (int i = e.getIndex0(); i <= e.getIndex1(); i++) {
/* 1013 */           MultiplePageDialog.this.addPage(i, this.val$group, this.val$buttonsPanel);
/*      */         }
/* 1015 */         this.val$buttonsPanel.invalidate();
/* 1016 */         this.val$buttonsPanel.doLayout();
/*      */       }
/*      */ 
/*      */       public void intervalRemoved(ListDataEvent e) {
/* 1020 */         for (int i = e.getIndex1(); i >= e.getIndex0(); i--) {
/* 1021 */           AbstractButton button = (AbstractButton)this.val$buttonsPanel.getComponent(i);
/* 1022 */           this.val$buttonsPanel.remove(button);
/* 1023 */           this.val$group.remove(button);
/*      */         }
/* 1025 */         this.val$buttonsPanel.invalidate();
/* 1026 */         this.val$buttonsPanel.doLayout();
/*      */       }
/*      */ 
/*      */       public void contentsChanged(ListDataEvent e) {
/* 1030 */         if ((e.getIndex0() == -1) && (e.getIndex1() == -1) && (e.getType() == 0)) {
/* 1031 */           AbstractButton button = (AbstractButton)this.val$buttonsPanel.getButtonByName(MultiplePageDialog.this._pageList.getCurrentPage().getTitle());
/* 1032 */           if (button != null)
/* 1033 */             this.val$group.setSelected(button.getModel(), true);
/*      */         }
/*      */       }
/*      */     });
/* 1039 */     pane.getViewport().setOpaque(false);
/* 1040 */     return pane;
/*      */   }
/*      */ 
/*      */   protected ButtonPanel createIconButtonPanel()
/*      */   {
/* 1050 */     return new ScrollableButtonPanel(1, 0);
/*      */   }
/*      */ 
/*      */   private JideButton addPage(int i, ButtonGroup group, ButtonPanel buttonsPanel) {
/* 1054 */     AbstractDialogPage optionsPanel = this._pageList.getPage(i);
/* 1055 */     JideButton button = createIconButton(optionsPanel.getTitle(), optionsPanel.getIcon());
/* 1056 */     button.addActionListener(new AbstractAction(optionsPanel.getTitle(), optionsPanel.getIcon(), group, button, buttonsPanel) {
/*      */       private static final long serialVersionUID = 5987367362274303556L;
/*      */ 
/* 1060 */       public void actionPerformed(ActionEvent e) { this.val$group.setSelected(this.val$button.getModel(), true);
/* 1061 */         MultiplePageDialog.this.setCurrentPage(MultiplePageDialog.this._pageList.getPageByFullTitle(e.getActionCommand()), this.val$buttonsPanel);
/*      */       }
/*      */     });
/* 1064 */     buttonsPanel.addButton(button, i);
/* 1065 */     group.add(button);
/* 1066 */     return button;
/*      */   }
/*      */ 
/*      */   protected JideButton createIconButton(String title, Icon icon)
/*      */   {
/* 1077 */     JideButton button = new JideButton(title, icon);
/* 1078 */     button.setName(title);
/* 1079 */     button.setHorizontalAlignment(0);
/* 1080 */     button.setVerticalTextPosition(3);
/* 1081 */     button.setHorizontalTextPosition(0);
/* 1082 */     button.setRequestFocusEnabled(false);
/* 1083 */     button.setFocusable(false);
/* 1084 */     return button;
/*      */   }
/*      */ 
/*      */   public int getStyle()
/*      */   {
/* 1093 */     return this._style;
/*      */   }
/*      */ 
/*      */   public void setStyle(int style)
/*      */   {
/* 1103 */     if ((style == 0) || (style == 2) || (style == 3) || (style == 1)) {
/* 1104 */       this._style = style;
/*      */     }
/*      */     else
/* 1107 */       throw new IllegalArgumentException("The value of style must be one of the following - TAB_STYLE, ICON_STYLE, LIST_STYLE or TREE_STYLE");
/*      */   }
/*      */ 
/*      */   public JComponent getIndexPanel()
/*      */   {
/* 1117 */     return this._indexPanel;
/*      */   }
/*      */ 
/*      */   public JComponent getPagesPanel()
/*      */   {
/* 1126 */     return this._pagesPanel;
/*      */   }
/*      */ 
/*      */   protected TreeCellRenderer getTreeCellRenderer()
/*      */   {
/* 1135 */     return this._treeCellRenderer;
/*      */   }
/*      */ 
/*      */   public void setTreeCellRenderer(TreeCellRenderer treeCellRenderer)
/*      */   {
/* 1144 */     this._treeCellRenderer = treeCellRenderer;
/*      */   }
/*      */ 
/*      */   protected ListCellRenderer getListCellRenderer()
/*      */   {
/* 1153 */     return this._listCellRenderer;
/*      */   }
/*      */ 
/*      */   public void setListCellRenderer(ListCellRenderer listCellRenderer)
/*      */   {
/* 1162 */     this._listCellRenderer = listCellRenderer;
/*      */   }
/*      */ 
/*      */   protected ListCellRenderer createListCellRenderer()
/*      */   {
/* 1171 */     if (getListCellRenderer() == null) {
/* 1172 */       setListCellRenderer(new DialogPageListCellRenderer());
/*      */     }
/* 1174 */     return getListCellRenderer();
/*      */   }
/*      */ 
/*      */   protected TreeCellRenderer createTreeCellRenderer()
/*      */   {
/* 1183 */     if (getTreeCellRenderer() == null) {
/* 1184 */       setTreeCellRenderer(new DialogPageTreeCellRenderer());
/*      */     }
/* 1186 */     return getTreeCellRenderer();
/*      */   }
/*      */ 
/*      */   public String getInitialPageTitle()
/*      */   {
/* 1197 */     return this._initialPageTitle;
/*      */   }
/*      */ 
/*      */   public void setInitialPageTitle(String initialPageTitle)
/*      */   {
/* 1206 */     this._initialPageTitle = initialPageTitle;
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.dialog.MultiplePageDialog
 * JD-Core Version:    0.6.0
 */
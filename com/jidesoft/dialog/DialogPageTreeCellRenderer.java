/*     */ package com.jidesoft.dialog;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.tree.DefaultMutableTreeNode;
/*     */ import javax.swing.tree.TreeCellRenderer;
/*     */ 
/*     */ public class DialogPageTreeCellRenderer extends JLabel
/*     */   implements TreeCellRenderer
/*     */ {
/*     */   private Color _selectedForeground;
/*     */   private Color _nonSelectedForeground;
/*     */   private Color _selectedBackground;
/*     */   private Color _nonSelectedBackground;
/*     */   private Color _selectedBorderColor;
/*     */   private Icon _selectedIcon;
/*     */   private Icon _blankIcon;
/*     */   private Icon _openIcon;
/*     */   private Icon _closedIcon;
/*     */   private Color _defaultTextSelectionColor;
/*     */   private Color _defaultTextNonSelectionColor;
/*     */   private Color _defaultBkSelectionColor;
/*     */   private Color _defaultBkNonSelectionColor;
/*     */   private Color _defaultBorderSelectionColor;
/*     */   private Icon _defaultOpenIcon;
/*     */   private Icon _defaultClosedIcon;
/*     */   private boolean m_selected;
/*  43 */   private static final Icon SELECTED = TreeIconsFactory.getImageIcon("icons/selected-b16.gif");
/*  44 */   private static final Icon BLANK = TreeIconsFactory.getImageIcon("icons/blank-16.gif");
/*     */ 
/*     */   public DialogPageTreeCellRenderer()
/*     */   {
/*  51 */     this._defaultTextSelectionColor = UIDefaultsLookup.getColor("Tree.selectionForeground");
/*  52 */     this._defaultTextNonSelectionColor = UIDefaultsLookup.getColor("Tree.textForeground");
/*  53 */     this._defaultBkSelectionColor = UIDefaultsLookup.getColor("Tree.selectionBackground");
/*  54 */     this._defaultBkNonSelectionColor = UIDefaultsLookup.getColor("Tree.textBackground");
/*  55 */     this._defaultBorderSelectionColor = UIDefaultsLookup.getColor("Tree.selectionBorderColor");
/*  56 */     this._defaultOpenIcon = UIDefaultsLookup.getIcon("Tree.openIcon");
/*  57 */     this._defaultClosedIcon = UIDefaultsLookup.getIcon("Tree.closedIcon");
/*  58 */     setOpaque(false);
/*     */   }
/*     */ 
/*     */   public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
/*     */   {
/*  64 */     DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
/*  65 */     Object obj = node.getUserObject();
/*     */ 
/*  67 */     if ((obj instanceof Boolean)) {
/*  68 */       setText("Retrieving data...");
/*     */     }
/*  70 */     if ((obj instanceof AbstractDialogPage)) {
/*  71 */       AbstractDialogPage idata = (AbstractDialogPage)obj;
/*  72 */       setText(idata.getTitle());
/*     */     }
/*     */     else {
/*  75 */       setText(obj.toString());
/*  76 */       setIcon(null);
/*     */     }
/*     */ 
/*  79 */     setFont(tree.getFont());
/*     */ 
/*  81 */     boolean treeIsEnabled = tree.isEnabled();
/*  82 */     boolean nodeIsEnabled = (!(value instanceof MutableTreeNodeEx)) || (((MutableTreeNodeEx)value).isEnabled());
/*  83 */     boolean isEnabled = (treeIsEnabled) && (nodeIsEnabled);
/*  84 */     setEnabled(isEnabled);
/*  85 */     if (!isEnabled) {
/*  86 */       sel = false;
/*     */     }
/*  88 */     setForeground(sel ? getSelectedForeground() : getNonSelectedForeground());
/*  89 */     setBackground(sel ? getSelectedBackground() : getNonSelectedBackground());
/*     */ 
/*  91 */     if (leaf) {
/*  92 */       if (sel) {
/*  93 */         setIcon(SELECTED);
/*     */       }
/*     */       else {
/*  96 */         setIcon(BLANK);
/*     */       }
/*     */ 
/*     */     }
/* 100 */     else if (expanded) {
/* 101 */       setIcon(getOpenIcon());
/*     */     }
/*     */     else {
/* 104 */       setIcon(getClosedIcon());
/*     */     }
/*     */ 
/* 107 */     this.m_selected = sel;
/* 108 */     setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */   public void paintComponent(Graphics g)
/*     */   {
/* 114 */     Color bColor = getBackground();
/* 115 */     Icon icon = getIcon();
/*     */ 
/* 117 */     g.setColor(bColor);
/* 118 */     int offset = 0;
/* 119 */     if ((icon != null) && (getText() != null))
/* 120 */       offset = icon.getIconWidth() + getIconTextGap() - 1;
/* 121 */     g.fillRect(offset, 0, getWidth() - 1 - offset, getHeight() - 1);
/*     */ 
/* 124 */     if (this.m_selected) {
/* 125 */       g.setColor(getSelectedBorderColor());
/* 126 */       g.drawRect(offset, 0, getWidth() - offset - 1, getHeight() - 1);
/*     */     }
/* 128 */     super.paintComponent(g);
/*     */   }
/*     */ 
/*     */   public Color getSelectedForeground()
/*     */   {
/* 137 */     if (this._selectedForeground == null) {
/* 138 */       if (this._defaultTextSelectionColor == null) {
/* 139 */         this._defaultTextSelectionColor = UIDefaultsLookup.getColor("Tree.selectionForeground");
/*     */       }
/* 141 */       return this._defaultTextSelectionColor;
/*     */     }
/* 143 */     return this._selectedForeground;
/*     */   }
/*     */ 
/*     */   public void setSelectedForeground(Color selectedForeground)
/*     */   {
/* 153 */     this._selectedForeground = selectedForeground;
/*     */   }
/*     */ 
/*     */   public Color getNonSelectedForeground()
/*     */   {
/* 162 */     if (this._nonSelectedForeground == null) {
/* 163 */       if (this._defaultTextNonSelectionColor == null) {
/* 164 */         this._defaultTextNonSelectionColor = UIDefaultsLookup.getColor("Tree.textForeground");
/*     */       }
/* 166 */       return this._defaultTextNonSelectionColor;
/*     */     }
/* 168 */     return this._nonSelectedForeground;
/*     */   }
/*     */ 
/*     */   public void setNonSelectedForeground(Color nonSelectedForeground)
/*     */   {
/* 178 */     this._nonSelectedForeground = nonSelectedForeground;
/*     */   }
/*     */ 
/*     */   public Color getSelectedBackground()
/*     */   {
/* 187 */     if (this._selectedBackground == null) {
/* 188 */       if (this._defaultBkSelectionColor == null) {
/* 189 */         this._defaultBkSelectionColor = UIDefaultsLookup.getColor("Tree.selectionBackground");
/*     */       }
/* 191 */       return this._defaultBkSelectionColor;
/*     */     }
/* 193 */     return this._selectedBackground;
/*     */   }
/*     */ 
/*     */   public void setSelectedBackground(Color selectedBackground)
/*     */   {
/* 203 */     this._selectedBackground = selectedBackground;
/*     */   }
/*     */ 
/*     */   public Color getNonSelectedBackground()
/*     */   {
/* 212 */     if (this._nonSelectedBackground == null) {
/* 213 */       if (this._defaultBkNonSelectionColor == null) {
/* 214 */         this._defaultBkNonSelectionColor = UIDefaultsLookup.getColor("Tree.textBackground");
/*     */       }
/* 216 */       return this._defaultBkNonSelectionColor;
/*     */     }
/* 218 */     return this._nonSelectedBackground;
/*     */   }
/*     */ 
/*     */   public void setNonSelectedBackground(Color nonSelectedBackground)
/*     */   {
/* 228 */     this._nonSelectedBackground = nonSelectedBackground;
/*     */   }
/*     */ 
/*     */   public Color getSelectedBorderColor()
/*     */   {
/* 237 */     if (this._selectedBorderColor == null) {
/* 238 */       if (this._defaultBorderSelectionColor == null) {
/* 239 */         this._defaultBorderSelectionColor = UIDefaultsLookup.getColor("Tree.selectionBorderColor");
/*     */       }
/* 241 */       return this._defaultBorderSelectionColor;
/*     */     }
/* 243 */     return this._selectedBorderColor;
/*     */   }
/*     */ 
/*     */   public void setSelectedBorderColor(Color selectedBorderColor)
/*     */   {
/* 253 */     this._selectedBorderColor = selectedBorderColor;
/*     */   }
/*     */ 
/*     */   public Icon getSelectedIcon()
/*     */   {
/* 262 */     if (this._selectedIcon == null) {
/* 263 */       return SELECTED;
/*     */     }
/* 265 */     return this._selectedIcon;
/*     */   }
/*     */ 
/*     */   public void setSelectedIcon(Icon selectedIcon)
/*     */   {
/* 275 */     this._selectedIcon = selectedIcon;
/*     */   }
/*     */ 
/*     */   public Icon getBlankIcon()
/*     */   {
/* 284 */     if (this._blankIcon == null) {
/* 285 */       return BLANK;
/*     */     }
/* 287 */     return this._blankIcon;
/*     */   }
/*     */ 
/*     */   public void setBlankIcon(Icon blankIcon)
/*     */   {
/* 297 */     this._blankIcon = blankIcon;
/*     */   }
/*     */ 
/*     */   public Icon getOpenIcon()
/*     */   {
/* 306 */     if (this._openIcon == null) {
/* 307 */       if (this._defaultOpenIcon == null) {
/* 308 */         this._defaultOpenIcon = UIDefaultsLookup.getIcon("Tree.openIcon");
/*     */       }
/* 310 */       return this._defaultOpenIcon;
/*     */     }
/* 312 */     return this._openIcon;
/*     */   }
/*     */ 
/*     */   public void setOpenIcon(Icon openIcon)
/*     */   {
/* 322 */     this._openIcon = openIcon;
/*     */   }
/*     */ 
/*     */   public Icon getClosedIcon()
/*     */   {
/* 331 */     if (this._closedIcon == null) {
/* 332 */       if (this._defaultClosedIcon == null) {
/* 333 */         this._defaultClosedIcon = UIDefaultsLookup.getIcon("Tree.closedIcon");
/*     */       }
/* 335 */       return this._defaultClosedIcon;
/*     */     }
/* 337 */     return this._closedIcon;
/*     */   }
/*     */ 
/*     */   public void setClosedIcon(Icon closedIcon)
/*     */   {
/* 347 */     this._closedIcon = closedIcon;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.dialog.DialogPageTreeCellRenderer
 * JD-Core Version:    0.6.0
 */
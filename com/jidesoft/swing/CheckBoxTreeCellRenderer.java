/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.tree.TreeCellRenderer;
/*     */ import javax.swing.tree.TreePath;
/*     */ 
/*     */ public class CheckBoxTreeCellRenderer extends JPanel
/*     */   implements TreeCellRenderer, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 30207434500313004L;
/*  27 */   protected TristateCheckBox _checkBox = null;
/*  28 */   protected JComponent _emptyBox = null;
/*     */   protected JCheckBox _protoType;
/*     */   protected TreeCellRenderer _actualTreeRenderer;
/*     */ 
/*     */   public CheckBoxTreeCellRenderer()
/*     */   {
/*  40 */     this(null);
/*     */   }
/*     */ 
/*     */   public CheckBoxTreeCellRenderer(TreeCellRenderer renderer) {
/*  44 */     this(renderer, null);
/*     */   }
/*     */ 
/*     */   public CheckBoxTreeCellRenderer(TreeCellRenderer renderer, TristateCheckBox checkBox) {
/*  48 */     this._protoType = new TristateCheckBox();
/*  49 */     if (checkBox == null) {
/*  50 */       this._checkBox = createCheckBox();
/*     */     }
/*     */     else {
/*  53 */       this._checkBox = checkBox;
/*     */     }
/*  55 */     this._emptyBox = ((JComponent)Box.createHorizontalStrut(this._protoType.getPreferredSize().width));
/*  56 */     setLayout(new BorderLayout(0, 0));
/*  57 */     setOpaque(false);
/*  58 */     this._actualTreeRenderer = renderer;
/*     */   }
/*     */ 
/*     */   protected TristateCheckBox createCheckBox()
/*     */   {
/*  69 */     TristateCheckBox checkBox = new TristateCheckBox();
/*  70 */     checkBox.setOpaque(false);
/*  71 */     return checkBox;
/*     */   }
/*     */ 
/*     */   public TreeCellRenderer getActualTreeRenderer() {
/*  75 */     return this._actualTreeRenderer;
/*     */   }
/*     */ 
/*     */   public void setActualTreeRenderer(TreeCellRenderer actualTreeRenderer) {
/*  79 */     this._actualTreeRenderer = actualTreeRenderer;
/*     */   }
/*     */ 
/*     */   public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
/*  83 */     this._checkBox.setPreferredSize(new Dimension(this._protoType.getPreferredSize().width, 0));
/*  84 */     this._emptyBox.setPreferredSize(new Dimension(this._protoType.getPreferredSize().width, 0));
/*  85 */     applyComponentOrientation(tree.getComponentOrientation());
/*     */ 
/*  87 */     TreePath path = tree.getPathForRow(row);
/*  88 */     if ((path != null) && ((tree instanceof CheckBoxTree))) {
/*  89 */       CheckBoxTreeSelectionModel selectionModel = ((CheckBoxTree)tree).getCheckBoxTreeSelectionModel();
/*  90 */       if (selectionModel != null) {
/*  91 */         boolean enabled = (tree.isEnabled()) && (((CheckBoxTree)tree).isCheckBoxEnabled()) && (((CheckBoxTree)tree).isCheckBoxEnabled(path));
/*  92 */         if ((!enabled) && (!selected) && 
/*  93 */           (getBackground() != null)) {
/*  94 */           setForeground(getBackground().darker());
/*     */         }
/*     */ 
/*  97 */         this._checkBox.setEnabled(enabled);
/*  98 */         updateCheckBoxState(this._checkBox, path, selectionModel);
/*     */       }
/*     */     }
/*     */ 
/* 102 */     if (this._actualTreeRenderer != null) {
/* 103 */       JComponent treeCellRendererComponent = (JComponent)this._actualTreeRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
/* 104 */       Border border = treeCellRendererComponent.getBorder();
/* 105 */       setBorder(border);
/* 106 */       treeCellRendererComponent.setBorder(BorderFactory.createEmptyBorder());
/* 107 */       if ((path == null) || (!(tree instanceof CheckBoxTree)) || (((CheckBoxTree)tree).isCheckBoxVisible(path))) {
/* 108 */         remove(this._emptyBox);
/* 109 */         add(this._checkBox, "Before");
/*     */       }
/*     */       else {
/* 112 */         remove(this._checkBox);
/* 113 */         add(this._emptyBox, "After");
/*     */       }
/* 115 */       add(treeCellRendererComponent);
/*     */     }
/*     */ 
/* 118 */     return this;
/*     */   }
/*     */ 
/*     */   protected void updateCheckBoxState(TristateCheckBox checkBox, TreePath path, CheckBoxTreeSelectionModel selectionModel)
/*     */   {
/* 132 */     if (selectionModel.isPathSelected(path, selectionModel.isDigIn()))
/* 133 */       checkBox.setState(TristateCheckBox.SELECTED);
/*     */     else
/* 135 */       checkBox.setState((selectionModel.isDigIn()) && (selectionModel.isPartiallySelected(path)) ? null : TristateCheckBox.NOT_SELECTED);
/*     */   }
/*     */ 
/*     */   public String getToolTipText(MouseEvent event)
/*     */   {
/* 140 */     if ((this._actualTreeRenderer instanceof JComponent)) {
/* 141 */       Point p = event.getPoint();
/* 142 */       p.translate(-this._checkBox.getWidth(), 0);
/* 143 */       MouseEvent newEvent = new MouseEvent((JComponent)this._actualTreeRenderer, event.getID(), event.getWhen(), event.getModifiers(), p.x, p.y, event.getClickCount(), event.isPopupTrigger());
/*     */ 
/* 149 */       String tip = ((JComponent)this._actualTreeRenderer).getToolTipText(newEvent);
/*     */ 
/* 152 */       if (tip != null) {
/* 153 */         return tip;
/*     */       }
/*     */     }
/* 156 */     return super.getToolTipText(event);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.CheckBoxTreeCellRenderer
 * JD-Core Version:    0.6.0
 */
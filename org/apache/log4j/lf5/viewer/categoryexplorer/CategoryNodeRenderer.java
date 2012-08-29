/*     */ package org.apache.log4j.lf5.viewer.categoryexplorer;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FlowLayout;
/*     */ import java.net.URL;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.tree.DefaultTreeCellRenderer;
/*     */ 
/*     */ public class CategoryNodeRenderer extends DefaultTreeCellRenderer
/*     */ {
/*  37 */   public static final Color FATAL_CHILDREN = new Color(189, 113, 0);
/*     */ 
/*  42 */   protected JCheckBox _checkBox = new JCheckBox();
/*  43 */   protected JPanel _panel = new JPanel();
/*  44 */   protected static ImageIcon _sat = null;
/*     */ 
/*     */   public CategoryNodeRenderer()
/*     */   {
/*  55 */     this._panel.setBackground(UIManager.getColor("Tree.textBackground"));
/*     */ 
/*  57 */     if (_sat == null)
/*     */     {
/*  59 */       String resource = "/org/apache/log4j/lf5/viewer/images/channelexplorer_satellite.gif";
/*     */ 
/*  61 */       URL satURL = getClass().getResource(resource);
/*     */ 
/*  63 */       _sat = new ImageIcon(satURL);
/*     */     }
/*     */ 
/*  66 */     setOpaque(false);
/*  67 */     this._checkBox.setOpaque(false);
/*  68 */     this._panel.setOpaque(false);
/*     */ 
/*  72 */     this._panel.setLayout(new FlowLayout(0, 0, 0));
/*  73 */     this._panel.add(this._checkBox);
/*  74 */     this._panel.add(this);
/*     */ 
/*  76 */     setOpenIcon(_sat);
/*  77 */     setClosedIcon(_sat);
/*  78 */     setLeafIcon(_sat);
/*     */   }
/*     */ 
/*     */   public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
/*     */   {
/*  90 */     CategoryNode node = (CategoryNode)value;
/*     */ 
/*  95 */     super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
/*     */ 
/*  99 */     if (row == 0)
/*     */     {
/* 101 */       this._checkBox.setVisible(false);
/*     */     } else {
/* 103 */       this._checkBox.setVisible(true);
/* 104 */       this._checkBox.setSelected(node.isSelected());
/*     */     }
/* 106 */     String toolTip = buildToolTip(node);
/* 107 */     this._panel.setToolTipText(toolTip);
/* 108 */     if (node.hasFatalChildren()) {
/* 109 */       setForeground(FATAL_CHILDREN);
/*     */     }
/* 111 */     if (node.hasFatalRecords()) {
/* 112 */       setForeground(Color.red);
/*     */     }
/*     */ 
/* 115 */     return this._panel;
/*     */   }
/*     */ 
/*     */   public Dimension getCheckBoxOffset() {
/* 119 */     return new Dimension(0, 0);
/*     */   }
/*     */ 
/*     */   protected String buildToolTip(CategoryNode node)
/*     */   {
/* 127 */     StringBuffer result = new StringBuffer();
/* 128 */     result.append(node.getTitle()).append(" contains a total of ");
/* 129 */     result.append(node.getTotalNumberOfRecords());
/* 130 */     result.append(" LogRecords.");
/* 131 */     result.append(" Right-click for more info.");
/* 132 */     return result.toString();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.categoryexplorer.CategoryNodeRenderer
 * JD-Core Version:    0.6.0
 */
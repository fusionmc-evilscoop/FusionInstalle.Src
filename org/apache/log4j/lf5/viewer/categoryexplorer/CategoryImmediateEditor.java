/*     */ package org.apache.log4j.lf5.viewer.categoryexplorer;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.EventObject;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.tree.DefaultMutableTreeNode;
/*     */ import javax.swing.tree.DefaultTreeCellEditor;
/*     */ import javax.swing.tree.DefaultTreeCellRenderer;
/*     */ import javax.swing.tree.TreePath;
/*     */ 
/*     */ public class CategoryImmediateEditor extends DefaultTreeCellEditor
/*     */ {
/*     */   private CategoryNodeRenderer renderer;
/*  43 */   protected Icon editingIcon = null;
/*     */ 
/*     */   public CategoryImmediateEditor(JTree tree, CategoryNodeRenderer renderer, CategoryNodeEditor editor)
/*     */   {
/*  55 */     super(tree, renderer, editor);
/*  56 */     this.renderer = renderer;
/*  57 */     renderer.setIcon(null);
/*  58 */     renderer.setLeafIcon(null);
/*  59 */     renderer.setOpenIcon(null);
/*  60 */     renderer.setClosedIcon(null);
/*     */ 
/*  62 */     this.editingIcon = null;
/*     */   }
/*     */ 
/*     */   public boolean shouldSelectCell(EventObject e)
/*     */   {
/*  69 */     boolean rv = false;
/*     */ 
/*  71 */     if ((e instanceof MouseEvent)) {
/*  72 */       MouseEvent me = (MouseEvent)e;
/*  73 */       TreePath path = this.tree.getPathForLocation(me.getX(), me.getY());
/*     */ 
/*  75 */       CategoryNode node = (CategoryNode)path.getLastPathComponent();
/*     */ 
/*  78 */       rv = node.isLeaf();
/*     */     }
/*  80 */     return rv;
/*     */   }
/*     */ 
/*     */   public boolean inCheckBoxHitRegion(MouseEvent e) {
/*  84 */     TreePath path = this.tree.getPathForLocation(e.getX(), e.getY());
/*     */ 
/*  86 */     if (path == null) {
/*  87 */       return false;
/*     */     }
/*  89 */     CategoryNode node = (CategoryNode)path.getLastPathComponent();
/*  90 */     boolean rv = false;
/*     */ 
/*  96 */     Rectangle bounds = this.tree.getRowBounds(this.lastRow);
/*  97 */     Dimension checkBoxOffset = this.renderer.getCheckBoxOffset();
/*     */ 
/* 100 */     bounds.translate(this.offset + checkBoxOffset.width, checkBoxOffset.height);
/*     */ 
/* 103 */     rv = bounds.contains(e.getPoint());
/*     */ 
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */   protected boolean canEditImmediately(EventObject e)
/*     */   {
/* 113 */     boolean rv = false;
/*     */ 
/* 115 */     if ((e instanceof MouseEvent)) {
/* 116 */       MouseEvent me = (MouseEvent)e;
/* 117 */       rv = inCheckBoxHitRegion(me);
/*     */     }
/*     */ 
/* 120 */     return rv;
/*     */   }
/*     */ 
/*     */   protected void determineOffset(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row)
/*     */   {
/* 127 */     this.offset = 0;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.categoryexplorer.CategoryImmediateEditor
 * JD-Core Version:    0.6.0
 */
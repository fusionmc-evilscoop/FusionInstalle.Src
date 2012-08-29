/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseMotionAdapter;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.ListModel;
/*     */ import javax.swing.ListSelectionModel;
/*     */ import javax.swing.table.TableModel;
/*     */ import javax.swing.tree.TreePath;
/*     */ 
/*     */ public class Sticky
/*     */ {
/*     */   private JComponent _target;
/*  29 */   private static final StrickyMouseMotionListener STICKY_MOUSE_MOTION_LISTENER = new StrickyMouseMotionListener(null);
/*     */ 
/*     */   public Sticky(JList list) {
/*  32 */     this._target = list;
/*  33 */     install();
/*     */   }
/*     */ 
/*     */   public Sticky(JTree tree) {
/*  37 */     this._target = tree;
/*  38 */     install();
/*     */   }
/*     */ 
/*     */   public Sticky(JTable table) {
/*  42 */     this._target = table;
/*  43 */     install();
/*     */   }
/*     */ 
/*     */   public void install()
/*     */   {
/*  51 */     this._target.addMouseMotionListener(STICKY_MOUSE_MOTION_LISTENER);
/*     */   }
/*     */ 
/*     */   public void uninstall()
/*     */   {
/*  58 */     this._target.removeMouseMotionListener(STICKY_MOUSE_MOTION_LISTENER);
/*     */   }
/*     */ 
/*     */   private static void updateListSelectionForEvent(MouseEvent anEvent, JList list, boolean shouldScroll)
/*     */   {
/* 105 */     Point location = anEvent.getPoint();
/* 106 */     if (list == null)
/* 107 */       return;
/* 108 */     int index = list.locationToIndex(location);
/* 109 */     if (index == -1) {
/* 110 */       if (location.y < 0)
/* 111 */         index = 0;
/*     */       else
/* 113 */         index = list.getModel() == null ? 0 : list.getModel().getSize() - 1;
/*     */     }
/* 115 */     if ((list.getSelectedIndex() != index) && (index >= 0) && (index < list.getModel().getSize())) {
/* 116 */       list.setSelectedIndex(index);
/* 117 */       if (shouldScroll)
/* 118 */         list.ensureIndexIsVisible(index);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void updateTreeSelectionForEvent(MouseEvent anEvent, JTree tree, boolean shouldScroll)
/*     */   {
/* 127 */     Point location = anEvent.getPoint();
/* 128 */     if (tree == null)
/* 129 */       return;
/* 130 */     int index = tree.getRowForLocation(location.x, location.y);
/* 131 */     if (index != -1) {
/* 132 */       TreePath pathForRow = tree.getPathForRow(index);
/* 133 */       if (tree.getSelectionPath() != pathForRow) {
/* 134 */         tree.setSelectionRow(index);
/* 135 */         if (shouldScroll)
/* 136 */           tree.makeVisible(pathForRow);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void updateTableSelectionForEvent(MouseEvent anEvent, JTable table, boolean shouldScroll)
/*     */   {
/* 148 */     Point location = anEvent.getPoint();
/* 149 */     if (table == null)
/* 150 */       return;
/* 151 */     int index = table.rowAtPoint(location);
/* 152 */     if (index == -1) {
/* 153 */       if (location.y < 0)
/* 154 */         index = 0;
/*     */       else
/* 156 */         index = table.getModel() == null ? 0 : table.getModel().getRowCount() - 1;
/*     */     }
/* 158 */     if (table.getSelectedRow() != index) {
/* 159 */       table.getSelectionModel().setSelectionInterval(index, index);
/* 160 */       if (shouldScroll)
/* 161 */         JideSwingUtilities.ensureRowVisible(table, index);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class StrickyMouseMotionListener extends MouseMotionAdapter
/*     */   {
/*     */     public void mouseMoved(MouseEvent anEvent)
/*     */     {
/*  68 */       if ((anEvent.getSource() instanceof JList)) {
/*  69 */         JList list = (JList)anEvent.getSource();
/*  70 */         Point location = anEvent.getPoint();
/*  71 */         Rectangle r = new Rectangle();
/*  72 */         list.computeVisibleRect(r);
/*  73 */         if (r.contains(location)) {
/*  74 */           Sticky.access$100(anEvent, list, false);
/*     */         }
/*     */       }
/*  77 */       else if ((anEvent.getSource() instanceof JTree)) {
/*  78 */         JTree tree = (JTree)anEvent.getSource();
/*  79 */         Point location = anEvent.getPoint();
/*  80 */         Rectangle r = new Rectangle();
/*  81 */         tree.computeVisibleRect(r);
/*  82 */         if (r.contains(location)) {
/*  83 */           Sticky.access$200(anEvent, tree, false);
/*     */         }
/*     */       }
/*  86 */       else if ((anEvent.getSource() instanceof JTable)) {
/*  87 */         JTable table = (JTable)anEvent.getSource();
/*  88 */         Point location = anEvent.getPoint();
/*  89 */         Rectangle r = new Rectangle();
/*  90 */         table.computeVisibleRect(r);
/*  91 */         if (r.contains(location))
/*  92 */           Sticky.access$300(anEvent, table, false);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.Sticky
 * JD-Core Version:    0.6.0
 */
/*     */ package org.apache.log4j.lf5.viewer;
/*     */ 
/*     */ import java.awt.Adjustable;
/*     */ import java.awt.Component;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.ListSelectionModel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.table.TableModel;
/*     */ 
/*     */ public class LF5SwingUtils
/*     */ {
/*     */   public static void selectRow(int row, JTable table, JScrollPane pane)
/*     */   {
/*  61 */     if ((table == null) || (pane == null)) {
/*  62 */       return;
/*     */     }
/*  64 */     if (!contains(row, table.getModel())) {
/*  65 */       return;
/*     */     }
/*  67 */     moveAdjustable(row * table.getRowHeight(), pane.getVerticalScrollBar());
/*  68 */     selectRow(row, table.getSelectionModel());
/*     */ 
/*  72 */     repaintLater(table);
/*     */   }
/*     */ 
/*     */   public static void makeScrollBarTrack(Adjustable scrollBar)
/*     */   {
/*  80 */     if (scrollBar == null) {
/*  81 */       return;
/*     */     }
/*  83 */     scrollBar.addAdjustmentListener(new TrackingAdjustmentListener());
/*     */   }
/*     */ 
/*     */   public static void makeVerticalScrollBarTrack(JScrollPane pane)
/*     */   {
/*  92 */     if (pane == null) {
/*  93 */       return;
/*     */     }
/*  95 */     makeScrollBarTrack(pane.getVerticalScrollBar());
/*     */   }
/*     */ 
/*     */   protected static boolean contains(int row, TableModel model)
/*     */   {
/* 102 */     if (model == null) {
/* 103 */       return false;
/*     */     }
/* 105 */     if (row < 0) {
/* 106 */       return false;
/*     */     }
/*     */ 
/* 109 */     return row < model.getRowCount();
/*     */   }
/*     */ 
/*     */   protected static void selectRow(int row, ListSelectionModel model)
/*     */   {
/* 115 */     if (model == null) {
/* 116 */       return;
/*     */     }
/* 118 */     model.setSelectionInterval(row, row);
/*     */   }
/*     */ 
/*     */   protected static void moveAdjustable(int location, Adjustable scrollBar) {
/* 122 */     if (scrollBar == null) {
/* 123 */       return;
/*     */     }
/* 125 */     scrollBar.setValue(location);
/*     */   }
/*     */ 
/*     */   protected static void repaintLater(JComponent component)
/*     */   {
/* 133 */     SwingUtilities.invokeLater(new Runnable(component) { private final JComponent val$component;
/*     */ 
/* 135 */       public void run() { this.val$component.repaint();
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.LF5SwingUtils
 * JD-Core Version:    0.6.0
 */
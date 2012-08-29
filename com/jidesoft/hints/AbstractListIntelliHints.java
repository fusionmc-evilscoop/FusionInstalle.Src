/*     */ package com.jidesoft.hints;
/*     */ 
/*     */ import com.jidesoft.swing.JideScrollPane;
/*     */ import com.jidesoft.swing.Sticky;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollBar;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.ListModel;
/*     */ import javax.swing.ListSelectionModel;
/*     */ import javax.swing.text.JTextComponent;
/*     */ 
/*     */ public abstract class AbstractListIntelliHints extends AbstractIntelliHints
/*     */ {
/*     */   private JList _list;
/*     */   protected KeyStroke[] _keyStrokes;
/*     */   private JideScrollPane _scroll;
/*     */ 
/*     */   public AbstractListIntelliHints(JTextComponent textComponent)
/*     */   {
/*  37 */     super(textComponent);
/*     */   }
/*     */ 
/*     */   public JComponent createHintsComponent() {
/*  41 */     JPanel panel = new JPanel(new BorderLayout());
/*     */ 
/*  43 */     this._list = createList();
/*  44 */     new Sticky(this._list);
/*  45 */     this._scroll = new JideScrollPane(getList());
/*  46 */     getList().setFocusable(false);
/*     */ 
/*  48 */     this._scroll.setHorizontalScrollBarPolicy(31);
/*  49 */     this._scroll.setBorder(BorderFactory.createEmptyBorder());
/*  50 */     this._scroll.getVerticalScrollBar().setFocusable(false);
/*  51 */     this._scroll.getHorizontalScrollBar().setFocusable(false);
/*     */ 
/*  53 */     panel.add(this._scroll, "Center");
/*  54 */     return panel;
/*     */   }
/*     */ 
/*     */   protected JList createList()
/*     */   {
/*  80 */     return new JList()
/*     */     {
/*     */       public int getVisibleRowCount() {
/*  83 */         int size = getModel().getSize();
/*  84 */         return size < super.getVisibleRowCount() ? size : super.getVisibleRowCount();
/*     */       }
/*     */ 
/*     */       public Dimension getPreferredScrollableViewportSize()
/*     */       {
/*  89 */         if (getModel().getSize() == 0) {
/*  90 */           return new Dimension(0, 0);
/*     */         }
/*     */ 
/*  93 */         return super.getPreferredScrollableViewportSize();
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   protected JList getList()
/*     */   {
/* 105 */     return this._list;
/*     */   }
/*     */ 
/*     */   protected void setListData(Object[] objects)
/*     */   {
/* 114 */     resetSelection();
/* 115 */     getList().setListData(objects);
/*     */ 
/* 118 */     if (this._scroll != null)
/* 119 */       this._scroll.setViewportView(getList());
/*     */   }
/*     */ 
/*     */   protected void setListData(Vector<?> objects)
/*     */   {
/* 130 */     resetSelection();
/* 131 */     getList().setListData(objects);
/*     */ 
/* 133 */     if (this._scroll != null)
/* 134 */       this._scroll.setViewportView(getList());
/*     */   }
/*     */ 
/*     */   private void resetSelection()
/*     */   {
/* 139 */     getList().getSelectionModel().setAnchorSelectionIndex(-1);
/* 140 */     getList().getSelectionModel().setLeadSelectionIndex(-1);
/* 141 */     getList().getSelectionModel().clearSelection();
/*     */   }
/*     */ 
/*     */   public Object getSelectedHint() {
/* 145 */     return getList().getSelectedValue();
/*     */   }
/*     */ 
/*     */   public JComponent getDelegateComponent()
/*     */   {
/* 150 */     return getList();
/*     */   }
/*     */ 
/*     */   public KeyStroke[] getDelegateKeyStrokes()
/*     */   {
/* 161 */     if (this._keyStrokes == null) {
/* 162 */       this._keyStrokes = new KeyStroke[8];
/* 163 */       this._keyStrokes[0] = KeyStroke.getKeyStroke(40, 0);
/* 164 */       this._keyStrokes[1] = KeyStroke.getKeyStroke(38, 0);
/* 165 */       this._keyStrokes[2] = KeyStroke.getKeyStroke(34, 0);
/* 166 */       this._keyStrokes[3] = KeyStroke.getKeyStroke(33, 0);
/* 167 */       this._keyStrokes[4] = KeyStroke.getKeyStroke(34, 128);
/* 168 */       this._keyStrokes[5] = KeyStroke.getKeyStroke(33, 128);
/* 169 */       this._keyStrokes[6] = KeyStroke.getKeyStroke(36, 128);
/* 170 */       this._keyStrokes[7] = KeyStroke.getKeyStroke(35, 128);
/*     */     }
/* 172 */     return this._keyStrokes;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.hints.AbstractListIntelliHints
 * JD-Core Version:    0.6.0
 */
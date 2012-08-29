/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.text.JTextComponent;
/*     */ 
/*     */ public class SearchableUtils
/*     */ {
/*     */   public static TreeSearchable installSearchable(JTree tree)
/*     */   {
/*  60 */     return new TreeSearchable(tree);
/*     */   }
/*     */ 
/*     */   public static TableSearchable installSearchable(JTable table)
/*     */   {
/*  70 */     return new TableSearchable(table);
/*     */   }
/*     */ 
/*     */   public static ListSearchable installSearchable(JList list)
/*     */   {
/*  80 */     return new ListSearchable(list);
/*     */   }
/*     */ 
/*     */   public static ComboBoxSearchable installSearchable(JComboBox combobox)
/*     */   {
/*  90 */     return new ComboBoxSearchable(combobox);
/*     */   }
/*     */ 
/*     */   public static TextComponentSearchable installSearchable(JTextComponent textComponent)
/*     */   {
/* 100 */     return new TextComponentSearchable(textComponent);
/*     */   }
/*     */ 
/*     */   public static void uninstallSearchable(Searchable searchable) {
/* 104 */     searchable.uninstallListeners();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.SearchableUtils
 * JD-Core Version:    0.6.0
 */
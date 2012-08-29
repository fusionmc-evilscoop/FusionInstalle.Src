/*     */ package com.jidesoft.list;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.swing.StyledLabel;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.ListCellRenderer;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.plaf.UIResource;
/*     */ 
/*     */ public class StyledListCellRenderer extends StyledLabel
/*     */   implements ListCellRenderer, Serializable
/*     */ {
/*     */   protected static Border noFocusBorder;
/*     */ 
/*     */   public StyledListCellRenderer()
/*     */   {
/*  34 */     if (noFocusBorder == null) {
/*  35 */       noFocusBorder = new EmptyBorder(1, 1, 1, 1);
/*     */     }
/*  37 */     setOpaque(true);
/*  38 */     setBorder(noFocusBorder);
/*     */   }
/*     */ 
/*     */   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
/*     */   {
/*  43 */     applyComponentOrientation(list.getComponentOrientation());
/*  44 */     if (isSelected) {
/*  45 */       setBackground(list.getSelectionBackground());
/*  46 */       setForeground(list.getSelectionForeground());
/*     */     }
/*     */     else {
/*  49 */       setBackground(list.getBackground());
/*  50 */       setForeground(list.getForeground());
/*     */     }
/*     */ 
/*  53 */     setIgnoreColorSettings(isSelected);
/*  54 */     customizeStyledLabel(list, value, index, isSelected, cellHasFocus);
/*     */ 
/*  56 */     setEnabled(list.isEnabled());
/*  57 */     setFont(list.getFont());
/*     */ 
/*  59 */     Border border = null;
/*  60 */     if (cellHasFocus) {
/*  61 */       if (isSelected) {
/*  62 */         border = UIDefaultsLookup.getBorder("List.focusSelectedCellHighlightBorder");
/*     */       }
/*  64 */       if (border == null)
/*  65 */         border = UIDefaultsLookup.getBorder("List.focusCellHighlightBorder");
/*     */     }
/*     */     else
/*     */     {
/*  69 */       border = noFocusBorder;
/*     */     }
/*  71 */     setBorder(border);
/*     */ 
/*  73 */     return this;
/*     */   }
/*     */ 
/*     */   protected void customizeStyledLabel(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
/*     */   {
/*  86 */     clearStyleRanges();
/*  87 */     if ((value instanceof Icon)) {
/*  88 */       setIcon((Icon)value);
/*  89 */       setText("");
/*     */     }
/*     */     else {
/*  92 */       setIcon(null);
/*  93 */       setText(value == null ? "" : value.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isOpaque()
/*     */   {
/* 107 */     Color back = getBackground();
/* 108 */     Component p = getParent();
/* 109 */     if (p != null) {
/* 110 */       p = p.getParent();
/*     */     }
/*     */ 
/* 113 */     boolean colorMatch = (back != null) && (p != null) && (back.equals(p.getBackground())) && (p.isOpaque());
/*     */ 
/* 116 */     return (!colorMatch) && (super.isOpaque());
/*     */   }
/*     */ 
/*     */   public void validate()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void invalidate()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void repaint()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void revalidate()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void repaint(long tm, int x, int y, int width, int height)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void repaint(Rectangle r)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void firePropertyChange(String propertyName, Object oldValue, Object newValue)
/*     */   {
/* 171 */     if (propertyName.equals("text"))
/* 172 */       super.firePropertyChange(propertyName, oldValue, newValue);
/*     */   }
/*     */ 
/*     */   public void firePropertyChange(String propertyName, byte oldValue, byte newValue)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void firePropertyChange(String propertyName, char oldValue, char newValue)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void firePropertyChange(String propertyName, short oldValue, short newValue)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void firePropertyChange(String propertyName, int oldValue, int newValue)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void firePropertyChange(String propertyName, long oldValue, long newValue)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void firePropertyChange(String propertyName, float oldValue, float newValue)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void firePropertyChange(String propertyName, double oldValue, double newValue)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue)
/*     */   {
/*     */   }
/*     */ 
/*     */   public static class UIResource extends StyledListCellRenderer
/*     */     implements UIResource
/*     */   {
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.list.StyledListCellRenderer
 * JD-Core Version:    0.6.0
 */
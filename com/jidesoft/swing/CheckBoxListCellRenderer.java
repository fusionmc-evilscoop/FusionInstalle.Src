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
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.ListCellRenderer;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.UIResource;
/*     */ 
/*     */ public class CheckBoxListCellRenderer extends JPanel
/*     */   implements ListCellRenderer, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2003073492549917883L;
/*  25 */   protected JCheckBox _checkBox = new NullCheckBox();
/*  26 */   protected JLabel _label = new NullLabel();
/*     */   protected ListCellRenderer _actualListRenderer;
/*     */ 
/*     */   public CheckBoxListCellRenderer(ListCellRenderer renderer)
/*     */   {
/*  34 */     this._checkBox.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
/*  35 */     setOpaque(true);
/*  36 */     setLayout(new BorderLayout(0, 0));
/*  37 */     add(this._checkBox, "Before");
/*  38 */     this._actualListRenderer = renderer;
/*     */   }
/*     */ 
/*     */   public CheckBoxListCellRenderer()
/*     */   {
/*  45 */     this(null);
/*     */   }
/*     */ 
/*     */   public ListCellRenderer getActualListRenderer() {
/*  49 */     return this._actualListRenderer;
/*     */   }
/*     */ 
/*     */   public void setActualListRenderer(ListCellRenderer actualListRenderer) {
/*  53 */     this._actualListRenderer = actualListRenderer;
/*     */   }
/*     */ 
/*     */   public String getToolTipText(MouseEvent event)
/*     */   {
/*  58 */     if ((this._actualListRenderer instanceof JComponent)) {
/*  59 */       Point p = event.getPoint();
/*  60 */       p.translate(-this._checkBox.getWidth(), 0);
/*  61 */       MouseEvent newEvent = new MouseEvent((JComponent)this._actualListRenderer, event.getID(), event.getWhen(), event.getModifiers(), p.x, p.y, event.getClickCount(), event.isPopupTrigger());
/*     */ 
/*  67 */       String tip = ((JComponent)this._actualListRenderer).getToolTipText(newEvent);
/*     */ 
/*  70 */       if (tip != null) {
/*  71 */         return tip;
/*     */       }
/*     */     }
/*  74 */     return super.getToolTipText(event);
/*     */   }
/*     */ 
/*     */   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
/*     */   {
/*  83 */     this._checkBox.setPreferredSize(new Dimension(this._checkBox.getPreferredSize().width, 0));
/*  84 */     applyComponentOrientation(list.getComponentOrientation());
/*     */     Object actualValue;
/*  87 */     if ((list instanceof CheckBoxList)) {
/*  88 */       CheckBoxListSelectionModel selectionModel = ((CheckBoxList)list).getCheckBoxListSelectionModel();
/*  89 */       if (selectionModel != null) {
/*  90 */         boolean enabled = (list.isEnabled()) && (((CheckBoxList)list).isCheckBoxEnabled()) && (((CheckBoxList)list).isCheckBoxEnabled(index));
/*     */ 
/*  93 */         if ((!enabled) && (!isSelected) && 
/*  94 */           (getBackground() != null)) {
/*  95 */           setForeground(getBackground().darker());
/*     */         }
/*     */ 
/*  98 */         this._checkBox.setEnabled(enabled);
/*  99 */         this._checkBox.setSelected(selectionModel.isSelectedIndex(index));
/*     */       }
/* 101 */       actualValue = value;
/*     */     }
/*     */     else
/*     */     {
/*     */       Object actualValue;
/* 103 */       if ((list instanceof CheckBoxListWithSelectable)) {
/* 104 */         if ((value instanceof Selectable)) {
/* 105 */           this._checkBox.setSelected(((Selectable)value).isSelected());
/* 106 */           boolean enabled = (list.isEnabled()) && (((Selectable)value).isEnabled()) && (((CheckBoxListWithSelectable)list).isCheckBoxEnabled());
/* 107 */           if ((!enabled) && (!isSelected)) {
/* 108 */             setForeground(getBackground().darker());
/*     */           }
/* 110 */           this._checkBox.setEnabled(enabled);
/*     */         }
/*     */         else {
/* 113 */           boolean enabled = list.isEnabled();
/* 114 */           if ((!enabled) && (!isSelected)) {
/* 115 */             setForeground(getBackground().darker());
/*     */           }
/* 117 */           this._checkBox.setEnabled(enabled);
/*     */         }
/*     */         Object actualValue;
/* 120 */         if ((value instanceof DefaultSelectable)) {
/* 121 */           actualValue = ((DefaultSelectable)value).getObject();
/*     */         }
/*     */         else
/* 124 */           actualValue = value;
/*     */       }
/*     */       else
/*     */       {
/* 128 */         throw new IllegalArgumentException("CheckBoxListCellRenderer should only be used for CheckBoxList.");
/*     */       }
/*     */     }
/*     */     Object actualValue;
/* 131 */     if (this._actualListRenderer != null) {
/* 132 */       JComponent listCellRendererComponent = (JComponent)this._actualListRenderer.getListCellRendererComponent(list, actualValue, index, isSelected, cellHasFocus);
/* 133 */       if (((list instanceof CheckBoxListWithSelectable)) && 
/* 134 */         (!((CheckBoxListWithSelectable)list).isCheckBoxVisible(index))) {
/* 135 */         return listCellRendererComponent;
/*     */       }
/*     */ 
/* 138 */       if (((list instanceof CheckBoxList)) && 
/* 139 */         (!((CheckBoxList)list).isCheckBoxVisible(index))) {
/* 140 */         return listCellRendererComponent;
/*     */       }
/*     */ 
/* 143 */       Border border = listCellRendererComponent.getBorder();
/* 144 */       setBorder(border);
/* 145 */       listCellRendererComponent.setBorder(BorderFactory.createEmptyBorder());
/* 146 */       if (getComponentCount() == 2) {
/* 147 */         remove(1);
/*     */       }
/* 149 */       add(listCellRendererComponent);
/* 150 */       setBackground(listCellRendererComponent.getBackground());
/* 151 */       setForeground(listCellRendererComponent.getForeground());
/*     */     }
/*     */     else {
/* 154 */       if (isSelected) {
/* 155 */         setBackground(list.getSelectionBackground());
/* 156 */         setForeground(list.getSelectionForeground());
/*     */       }
/*     */       else {
/* 159 */         setBackground(list.getBackground());
/* 160 */         setForeground(list.getForeground());
/*     */       }
/* 162 */       if (getComponentCount() == 2) {
/* 163 */         remove(1);
/*     */       }
/* 165 */       add(this._label);
/* 166 */       customizeDefaultCellRenderer(actualValue);
/* 167 */       setFont(list.getFont());
/*     */     }
/*     */ 
/* 170 */     return this;
/*     */   }
/*     */ 
/*     */   protected void customizeDefaultCellRenderer(Object value)
/*     */   {
/* 181 */     if ((value instanceof Icon)) {
/* 182 */       this._label.setIcon((Icon)value);
/* 183 */       this._label.setText("");
/*     */     }
/*     */     else {
/* 186 */       this._label.setIcon(null);
/* 187 */       this._label.setText(value == null ? "" : value.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class UIResource extends CheckBoxListCellRenderer
/*     */     implements UIResource
/*     */   {
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.CheckBoxListCellRenderer
 * JD-Core Version:    0.6.0
 */
/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.InputEvent;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.accessibility.AccessibleState;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.AbstractButton.AccessibleAbstractButton;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.Icon;
/*     */ 
/*     */ public class JideToggleSplitButton extends JideSplitButton
/*     */   implements Accessible
/*     */ {
/*     */   public JideToggleSplitButton()
/*     */   {
/*  31 */     this(null, null, false);
/*     */   }
/*     */ 
/*     */   public JideToggleSplitButton(Icon icon)
/*     */   {
/*  40 */     this(null, icon, false);
/*     */   }
/*     */ 
/*     */   public JideToggleSplitButton(Icon icon, boolean selected)
/*     */   {
/*  50 */     this(null, icon, selected);
/*     */   }
/*     */ 
/*     */   public JideToggleSplitButton(String text)
/*     */   {
/*  59 */     this(text, null, false);
/*     */   }
/*     */ 
/*     */   public JideToggleSplitButton(String text, boolean selected)
/*     */   {
/*  69 */     this(text, null, selected);
/*     */   }
/*     */ 
/*     */   public JideToggleSplitButton(Action a)
/*     */   {
/*  78 */     this();
/*  79 */     setAction(a);
/*     */   }
/*     */ 
/*     */   public JideToggleSplitButton(String text, Icon icon)
/*     */   {
/*  89 */     this(text, icon, false);
/*     */   }
/*     */ 
/*     */   public JideToggleSplitButton(String text, Icon icon, boolean selected)
/*     */   {
/* 101 */     setModel(new ToggleSplitButtonModel());
/*     */ 
/* 103 */     ((SplitButtonModel)this.model).setButtonSelected(selected);
/*     */ 
/* 106 */     init(text, icon);
/*     */   }
/*     */ 
/*     */   public AccessibleContext getAccessibleContext()
/*     */   {
/* 228 */     if (this.accessibleContext == null) {
/* 229 */       this.accessibleContext = new AccessibleJToggleButton();
/*     */     }
/* 231 */     return this.accessibleContext;
/*     */   }
/*     */ 
/*     */   protected class AccessibleJToggleButton extends AbstractButton.AccessibleAbstractButton
/*     */     implements ItemListener
/*     */   {
/*     */     public AccessibleJToggleButton()
/*     */     {
/* 247 */       super();
/* 248 */       JideToggleSplitButton.this.addItemListener(this);
/*     */     }
/*     */ 
/*     */     public void itemStateChanged(ItemEvent e)
/*     */     {
/* 255 */       JideToggleSplitButton tb = (JideToggleSplitButton)e.getSource();
/* 256 */       if (JideToggleSplitButton.this.accessibleContext != null)
/* 257 */         if (tb.isSelected()) {
/* 258 */           JideToggleSplitButton.this.accessibleContext.firePropertyChange("AccessibleState", null, AccessibleState.CHECKED);
/*     */         }
/*     */         else
/*     */         {
/* 262 */           JideToggleSplitButton.this.accessibleContext.firePropertyChange("AccessibleState", AccessibleState.CHECKED, null);
/*     */         }
/*     */     }
/*     */ 
/*     */     public AccessibleRole getAccessibleRole()
/*     */     {
/* 275 */       return AccessibleRole.TOGGLE_BUTTON;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class ToggleSplitButtonModel extends DefaultSplitButtonModel
/*     */   {
/*     */     public boolean isButtonSelected()
/*     */     {
/* 135 */       return (this.stateMask & 0x40) != 0;
/*     */     }
/*     */ 
/*     */     public void setButtonSelected(boolean b)
/*     */     {
/* 147 */       ButtonGroup group = getGroup();
/* 148 */       if (group != null)
/*     */       {
/* 150 */         group.setSelected(this, b);
/* 151 */         b = group.isSelected(this);
/*     */       }
/*     */ 
/* 154 */       if (isButtonSelected() == b) {
/* 155 */         return;
/*     */       }
/*     */ 
/* 158 */       if (b) {
/* 159 */         this.stateMask |= 64;
/*     */       }
/*     */       else {
/* 162 */         this.stateMask &= -65;
/*     */       }
/*     */ 
/* 166 */       fireStateChanged();
/*     */ 
/* 169 */       fireItemStateChanged(new ItemEvent(this, 701, this, isSelected() ? 1 : 2));
/*     */     }
/*     */ 
/*     */     public void setPressed(boolean b)
/*     */     {
/* 181 */       if ((isPressed() == b) || (!isEnabled())) {
/* 182 */         return;
/*     */       }
/*     */ 
/* 185 */       if ((!b) && (isArmed())) {
/* 186 */         setButtonSelected(!isButtonSelected());
/*     */       }
/*     */ 
/* 189 */       if (b) {
/* 190 */         this.stateMask |= 4;
/*     */       }
/*     */       else {
/* 193 */         this.stateMask &= -5;
/*     */       }
/*     */ 
/* 196 */       fireStateChanged();
/*     */ 
/* 198 */       if ((!isPressed()) && (isArmed())) {
/* 199 */         int modifiers = 0;
/* 200 */         AWTEvent currentEvent = EventQueue.getCurrentEvent();
/* 201 */         if ((currentEvent instanceof InputEvent)) {
/* 202 */           modifiers = ((InputEvent)currentEvent).getModifiers();
/*     */         }
/* 204 */         else if ((currentEvent instanceof ActionEvent)) {
/* 205 */           modifiers = ((ActionEvent)currentEvent).getModifiers();
/*     */         }
/* 207 */         fireActionPerformed(new ActionEvent(this, 1001, getActionCommand(), EventQueue.getMostRecentEventTime(), modifiers));
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.JideToggleSplitButton
 * JD-Core Version:    0.6.0
 */
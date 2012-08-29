/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.plaf.LookAndFeelFactory;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.ActionMap;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import javax.swing.plaf.ActionMapUIResource;
/*     */ 
/*     */ public class TristateCheckBox extends JCheckBox
/*     */ {
/*  28 */   public static final State NOT_SELECTED = new State(null);
/*  29 */   public static final State SELECTED = new State(null);
/*  30 */   public static final State DONT_CARE = new State(null);
/*     */   private final TristateDecorator model;
/*     */ 
/*     */   public TristateCheckBox(String text, Icon icon, State initial)
/*     */   {
/*  35 */     super(text, icon);
/*     */ 
/*  37 */     super.addMouseListener(new MouseAdapter()
/*     */     {
/*     */       public void mousePressed(MouseEvent e) {
/*  40 */         TristateCheckBox.this.grabFocus();
/*  41 */         TristateCheckBox.this.model.setState(TristateCheckBox.this.getNextState(TristateCheckBox.TristateDecorator.access$200(TristateCheckBox.this.model)));
/*     */       }
/*     */     });
/*  45 */     ActionMap map = new ActionMapUIResource();
/*  46 */     map.put("pressed", new AbstractAction() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  48 */         TristateCheckBox.this.grabFocus();
/*  49 */         TristateCheckBox.this.model.setState(TristateCheckBox.this.getNextState(TristateCheckBox.TristateDecorator.access$200(TristateCheckBox.this.model)));
/*     */       }
/*     */     });
/*  52 */     map.put("released", null);
/*  53 */     SwingUtilities.replaceUIActionMap(this, map);
/*     */ 
/*  55 */     this.model = new TristateDecorator(getModel(), null);
/*  56 */     setModel(this.model);
/*  57 */     setState(initial);
/*     */   }
/*     */ 
/*     */   public TristateCheckBox(String text, State initial) {
/*  61 */     this(text, null, initial);
/*     */   }
/*     */ 
/*     */   public TristateCheckBox(String text) {
/*  65 */     this(text, DONT_CARE);
/*     */   }
/*     */ 
/*     */   public TristateCheckBox() {
/*  69 */     this(null);
/*     */   }
/*     */ 
/*     */   public void addMouseListener(MouseListener l)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setState(State state)
/*     */   {
/*  83 */     this.model.setState(state);
/*     */ 
/*  87 */     if ((LookAndFeelFactory.isSyntheticaLnfInstalled()) && ((UIManager.getLookAndFeel().getClass().getName().startsWith("de.javasoft.plaf.synthetica.Synthetica")) || (LookAndFeelFactory.isLnfInUse("de.javasoft.plaf.synthetica.SyntheticaLookAndFeel"))))
/*     */     {
/*  89 */       if (state == DONT_CARE) {
/*  90 */         setName("HalfSelected");
/*     */       }
/*     */       else
/*  93 */         setName("");
/*     */     }
/*     */   }
/*     */ 
/*     */   public State getState()
/*     */   {
/* 102 */     return this.model.getState();
/*     */   }
/*     */ 
/*     */   public void setSelected(boolean b)
/*     */   {
/* 107 */     if (b) {
/* 108 */       setState(SELECTED);
/*     */     }
/*     */     else
/* 111 */       setState(NOT_SELECTED);
/*     */   }
/*     */ 
/*     */   protected State getNextState(State current)
/*     */   {
/* 281 */     if (current == NOT_SELECTED) {
/* 282 */       return SELECTED;
/*     */     }
/* 284 */     if (current == SELECTED) {
/* 285 */       return DONT_CARE;
/*     */     }
/*     */ 
/* 288 */     return NOT_SELECTED;
/*     */   }
/*     */ 
/*     */   private class TristateDecorator
/*     */     implements ButtonModel
/*     */   {
/*     */     private final ButtonModel other;
/*     */     private TristateCheckBox.State _state;
/*     */ 
/*     */     private TristateDecorator(ButtonModel other)
/*     */     {
/* 125 */       this.other = other;
/*     */     }
/*     */ 
/*     */     private void setState(TristateCheckBox.State state) {
/* 129 */       if (state == TristateCheckBox.NOT_SELECTED) {
/* 130 */         this.other.setArmed(false);
/* 131 */         setPressed(false);
/* 132 */         setSelected(false);
/*     */       }
/* 134 */       else if (state == TristateCheckBox.SELECTED) {
/* 135 */         this.other.setArmed(false);
/* 136 */         setPressed(false);
/* 137 */         setSelected(true);
/*     */       }
/*     */       else {
/* 140 */         this.other.setArmed(true);
/* 141 */         setPressed(true);
/* 142 */         setSelected(true);
/*     */       }
/* 144 */       this._state = state;
/*     */     }
/*     */ 
/*     */     private TristateCheckBox.State getState()
/*     */     {
/* 153 */       return this._state == null ? TristateCheckBox.DONT_CARE : this._state;
/*     */     }
/*     */ 
/*     */     public void setArmed(boolean b)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void setEnabled(boolean b)
/*     */     {
/* 178 */       TristateCheckBox.this.setFocusable(b);
/* 179 */       this.other.setEnabled(b);
/*     */     }
/*     */ 
/*     */     public boolean isArmed()
/*     */     {
/* 186 */       return this.other.isArmed();
/*     */     }
/*     */ 
/*     */     public boolean isSelected() {
/* 190 */       return this.other.isSelected();
/*     */     }
/*     */ 
/*     */     public boolean isEnabled() {
/* 194 */       return this.other.isEnabled();
/*     */     }
/*     */ 
/*     */     public boolean isPressed() {
/* 198 */       return this.other.isPressed();
/*     */     }
/*     */ 
/*     */     public boolean isRollover() {
/* 202 */       return this.other.isRollover();
/*     */     }
/*     */ 
/*     */     public void setSelected(boolean b) {
/* 206 */       this.other.setSelected(b);
/*     */     }
/*     */ 
/*     */     public void setPressed(boolean b) {
/* 210 */       this.other.setPressed(b);
/*     */     }
/*     */ 
/*     */     public void setRollover(boolean b) {
/* 214 */       this.other.setRollover(b);
/*     */     }
/*     */ 
/*     */     public void setMnemonic(int key) {
/* 218 */       this.other.setMnemonic(key);
/*     */     }
/*     */ 
/*     */     public int getMnemonic() {
/* 222 */       return this.other.getMnemonic();
/*     */     }
/*     */ 
/*     */     public void setActionCommand(String s) {
/* 226 */       this.other.setActionCommand(s);
/*     */     }
/*     */ 
/*     */     public String getActionCommand() {
/* 230 */       return this.other.getActionCommand();
/*     */     }
/*     */ 
/*     */     public void setGroup(ButtonGroup group) {
/* 234 */       this.other.setGroup(group);
/*     */     }
/*     */ 
/*     */     public void addActionListener(ActionListener l) {
/* 238 */       this.other.addActionListener(l);
/*     */     }
/*     */ 
/*     */     public void removeActionListener(ActionListener l) {
/* 242 */       this.other.removeActionListener(l);
/*     */     }
/*     */ 
/*     */     public void addItemListener(ItemListener l) {
/* 246 */       this.other.addItemListener(l);
/*     */     }
/*     */ 
/*     */     public void removeItemListener(ItemListener l) {
/* 250 */       this.other.removeItemListener(l);
/*     */     }
/*     */ 
/*     */     public void addChangeListener(ChangeListener l) {
/* 254 */       this.other.addChangeListener(l);
/*     */     }
/*     */ 
/*     */     public void removeChangeListener(ChangeListener l) {
/* 258 */       this.other.removeChangeListener(l);
/*     */     }
/*     */ 
/*     */     public Object[] getSelectedObjects() {
/* 262 */       return this.other.getSelectedObjects();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class State
/*     */   {
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.TristateCheckBox
 * JD-Core Version:    0.6.0
 */
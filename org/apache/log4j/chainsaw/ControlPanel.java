/*     */ package org.apache.log4j.chainsaw;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import javax.swing.event.DocumentListener;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ class ControlPanel extends JPanel
/*     */ {
/*  41 */   private static final Logger LOG = Logger.getLogger(ControlPanel.class);
/*     */ 
/*     */   ControlPanel(MyTableModel aModel)
/*     */   {
/*  50 */     setBorder(BorderFactory.createTitledBorder("Controls: "));
/*  51 */     GridBagLayout gridbag = new GridBagLayout();
/*  52 */     GridBagConstraints c = new GridBagConstraints();
/*  53 */     setLayout(gridbag);
/*     */ 
/*  56 */     c.ipadx = 5;
/*  57 */     c.ipady = 5;
/*     */ 
/*  60 */     c.gridx = 0;
/*  61 */     c.anchor = 13;
/*     */ 
/*  63 */     c.gridy = 0;
/*  64 */     JLabel label = new JLabel("Filter Level:");
/*  65 */     gridbag.setConstraints(label, c);
/*  66 */     add(label);
/*     */ 
/*  68 */     c.gridy += 1;
/*  69 */     label = new JLabel("Filter Thread:");
/*  70 */     gridbag.setConstraints(label, c);
/*  71 */     add(label);
/*     */ 
/*  73 */     c.gridy += 1;
/*  74 */     label = new JLabel("Filter Logger:");
/*  75 */     gridbag.setConstraints(label, c);
/*  76 */     add(label);
/*     */ 
/*  78 */     c.gridy += 1;
/*  79 */     label = new JLabel("Filter NDC:");
/*  80 */     gridbag.setConstraints(label, c);
/*  81 */     add(label);
/*     */ 
/*  83 */     c.gridy += 1;
/*  84 */     label = new JLabel("Filter Message:");
/*  85 */     gridbag.setConstraints(label, c);
/*  86 */     add(label);
/*     */ 
/*  89 */     c.weightx = 1.0D;
/*     */ 
/*  91 */     c.gridx = 1;
/*  92 */     c.anchor = 17;
/*     */ 
/*  94 */     c.gridy = 0;
/*  95 */     Level[] allPriorities = { Level.FATAL, Level.ERROR, Level.WARN, Level.INFO, Level.DEBUG, Level.TRACE };
/*     */ 
/* 102 */     JComboBox priorities = new JComboBox(allPriorities);
/* 103 */     Level lowest = allPriorities[(allPriorities.length - 1)];
/* 104 */     priorities.setSelectedItem(lowest);
/* 105 */     aModel.setPriorityFilter(lowest);
/* 106 */     gridbag.setConstraints(priorities, c);
/* 107 */     add(priorities);
/* 108 */     priorities.setEditable(false);
/* 109 */     priorities.addActionListener(new ActionListener(aModel, priorities) { private final MyTableModel val$aModel;
/*     */       private final JComboBox val$priorities;
/*     */ 
/* 111 */       public void actionPerformed(ActionEvent aEvent) { this.val$aModel.setPriorityFilter((Priority)this.val$priorities.getSelectedItem());
/*     */       }
/*     */     });
/* 117 */     c.fill = 2;
/* 118 */     c.gridy += 1;
/* 119 */     JTextField threadField = new JTextField("");
/* 120 */     threadField.getDocument().addDocumentListener(new DocumentListener(aModel, threadField) { private final MyTableModel val$aModel;
/*     */       private final JTextField val$threadField;
/*     */ 
/* 122 */       public void insertUpdate(DocumentEvent aEvent) { this.val$aModel.setThreadFilter(this.val$threadField.getText()); }
/*     */ 
/*     */       public void removeUpdate(DocumentEvent aEvente) {
/* 125 */         this.val$aModel.setThreadFilter(this.val$threadField.getText());
/*     */       }
/*     */       public void changedUpdate(DocumentEvent aEvent) {
/* 128 */         this.val$aModel.setThreadFilter(this.val$threadField.getText());
/*     */       }
/*     */     });
/* 131 */     gridbag.setConstraints(threadField, c);
/* 132 */     add(threadField);
/*     */ 
/* 134 */     c.gridy += 1;
/* 135 */     JTextField catField = new JTextField("");
/* 136 */     catField.getDocument().addDocumentListener(new DocumentListener(aModel, catField) { private final MyTableModel val$aModel;
/*     */       private final JTextField val$catField;
/*     */ 
/* 138 */       public void insertUpdate(DocumentEvent aEvent) { this.val$aModel.setCategoryFilter(this.val$catField.getText()); }
/*     */ 
/*     */       public void removeUpdate(DocumentEvent aEvent) {
/* 141 */         this.val$aModel.setCategoryFilter(this.val$catField.getText());
/*     */       }
/*     */       public void changedUpdate(DocumentEvent aEvent) {
/* 144 */         this.val$aModel.setCategoryFilter(this.val$catField.getText());
/*     */       }
/*     */     });
/* 147 */     gridbag.setConstraints(catField, c);
/* 148 */     add(catField);
/*     */ 
/* 150 */     c.gridy += 1;
/* 151 */     JTextField ndcField = new JTextField("");
/* 152 */     ndcField.getDocument().addDocumentListener(new DocumentListener(aModel, ndcField) { private final MyTableModel val$aModel;
/*     */       private final JTextField val$ndcField;
/*     */ 
/* 154 */       public void insertUpdate(DocumentEvent aEvent) { this.val$aModel.setNDCFilter(this.val$ndcField.getText()); }
/*     */ 
/*     */       public void removeUpdate(DocumentEvent aEvent) {
/* 157 */         this.val$aModel.setNDCFilter(this.val$ndcField.getText());
/*     */       }
/*     */       public void changedUpdate(DocumentEvent aEvent) {
/* 160 */         this.val$aModel.setNDCFilter(this.val$ndcField.getText());
/*     */       }
/*     */     });
/* 163 */     gridbag.setConstraints(ndcField, c);
/* 164 */     add(ndcField);
/*     */ 
/* 166 */     c.gridy += 1;
/* 167 */     JTextField msgField = new JTextField("");
/* 168 */     msgField.getDocument().addDocumentListener(new DocumentListener(aModel, msgField) { private final MyTableModel val$aModel;
/*     */       private final JTextField val$msgField;
/*     */ 
/* 170 */       public void insertUpdate(DocumentEvent aEvent) { this.val$aModel.setMessageFilter(this.val$msgField.getText()); }
/*     */ 
/*     */       public void removeUpdate(DocumentEvent aEvent) {
/* 173 */         this.val$aModel.setMessageFilter(this.val$msgField.getText());
/*     */       }
/*     */       public void changedUpdate(DocumentEvent aEvent) {
/* 176 */         this.val$aModel.setMessageFilter(this.val$msgField.getText());
/*     */       }
/*     */     });
/* 181 */     gridbag.setConstraints(msgField, c);
/* 182 */     add(msgField);
/*     */ 
/* 185 */     c.weightx = 0.0D;
/* 186 */     c.fill = 2;
/* 187 */     c.anchor = 13;
/* 188 */     c.gridx = 2;
/*     */ 
/* 190 */     c.gridy = 0;
/* 191 */     JButton exitButton = new JButton("Exit");
/* 192 */     exitButton.setMnemonic('x');
/* 193 */     exitButton.addActionListener(ExitAction.INSTANCE);
/* 194 */     gridbag.setConstraints(exitButton, c);
/* 195 */     add(exitButton);
/*     */ 
/* 197 */     c.gridy += 1;
/* 198 */     JButton clearButton = new JButton("Clear");
/* 199 */     clearButton.setMnemonic('c');
/* 200 */     clearButton.addActionListener(new ActionListener(aModel) { private final MyTableModel val$aModel;
/*     */ 
/* 202 */       public void actionPerformed(ActionEvent aEvent) { this.val$aModel.clear();
/*     */       }
/*     */     });
/* 205 */     gridbag.setConstraints(clearButton, c);
/* 206 */     add(clearButton);
/*     */ 
/* 208 */     c.gridy += 1;
/* 209 */     JButton toggleButton = new JButton("Pause");
/* 210 */     toggleButton.setMnemonic('p');
/* 211 */     toggleButton.addActionListener(new ActionListener(aModel, toggleButton) { private final MyTableModel val$aModel;
/*     */       private final JButton val$toggleButton;
/*     */ 
/* 213 */       public void actionPerformed(ActionEvent aEvent) { this.val$aModel.toggle();
/* 214 */         this.val$toggleButton.setText(this.val$aModel.isPaused() ? "Resume" : "Pause");
/*     */       }
/*     */     });
/* 218 */     gridbag.setConstraints(toggleButton, c);
/* 219 */     add(toggleButton);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.chainsaw.ControlPanel
 * JD-Core Version:    0.6.0
 */
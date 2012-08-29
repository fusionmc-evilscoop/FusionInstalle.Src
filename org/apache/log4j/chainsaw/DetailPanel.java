/*     */ package org.apache.log4j.chainsaw;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Container;
/*     */ import java.text.Format;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Date;
/*     */ import java.util.EventObject;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JEditorPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.ListSelectionModel;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ class DetailPanel extends JPanel
/*     */   implements ListSelectionListener
/*     */ {
/*  41 */   private static final Logger LOG = Logger.getLogger(DetailPanel.class);
/*     */ 
/*  45 */   private static final MessageFormat FORMATTER = new MessageFormat("<b>Time:</b> <code>{0,time,medium}</code>&nbsp;&nbsp;<b>Priority:</b> <code>{1}</code>&nbsp;&nbsp;<b>Thread:</b> <code>{2}</code>&nbsp;&nbsp;<b>NDC:</b> <code>{3}</code><br><b>Logger:</b> <code>{4}</code><br><b>Location:</b> <code>{5}</code><br><b>Message:</b><pre>{6}</pre><b>Throwable:</b><pre>{7}</pre>");
/*     */   private final MyTableModel mModel;
/*     */   private final JEditorPane mDetails;
/*     */ 
/*     */   DetailPanel(JTable aTable, MyTableModel aModel)
/*     */   {
/*  69 */     this.mModel = aModel;
/*  70 */     setLayout(new BorderLayout());
/*  71 */     setBorder(BorderFactory.createTitledBorder("Details: "));
/*     */ 
/*  73 */     this.mDetails = new JEditorPane();
/*  74 */     this.mDetails.setEditable(false);
/*  75 */     this.mDetails.setContentType("text/html");
/*  76 */     add(new JScrollPane(this.mDetails), "Center");
/*     */ 
/*  78 */     ListSelectionModel rowSM = aTable.getSelectionModel();
/*  79 */     rowSM.addListSelectionListener(this);
/*     */   }
/*     */ 
/*     */   public void valueChanged(ListSelectionEvent aEvent)
/*     */   {
/*  85 */     if (aEvent.getValueIsAdjusting()) {
/*  86 */       return;
/*     */     }
/*     */ 
/*  89 */     ListSelectionModel lsm = (ListSelectionModel)aEvent.getSource();
/*  90 */     if (lsm.isSelectionEmpty()) {
/*  91 */       this.mDetails.setText("Nothing selected");
/*     */     } else {
/*  93 */       int selectedRow = lsm.getMinSelectionIndex();
/*  94 */       EventDetails e = this.mModel.getEventDetails(selectedRow);
/*  95 */       Object[] args = { new Date(e.getTimeStamp()), e.getPriority(), escape(e.getThreadName()), escape(e.getNDC()), escape(e.getCategoryName()), escape(e.getLocationDetails()), escape(e.getMessage()), escape(getThrowableStrRep(e)) };
/*     */ 
/* 106 */       this.mDetails.setText(FORMATTER.format(args));
/* 107 */       this.mDetails.setCaretPosition(0);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String getThrowableStrRep(EventDetails aEvent)
/*     */   {
/* 122 */     String[] strs = aEvent.getThrowableStrRep();
/* 123 */     if (strs == null) {
/* 124 */       return null;
/*     */     }
/*     */ 
/* 127 */     StringBuffer sb = new StringBuffer();
/* 128 */     for (int i = 0; i < strs.length; i++) {
/* 129 */       sb.append(strs[i]).append("\n");
/*     */     }
/*     */ 
/* 132 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   private String escape(String aStr)
/*     */   {
/* 142 */     if (aStr == null) {
/* 143 */       return null;
/*     */     }
/*     */ 
/* 146 */     StringBuffer buf = new StringBuffer();
/* 147 */     for (int i = 0; i < aStr.length(); i++) {
/* 148 */       char c = aStr.charAt(i);
/* 149 */       switch (c) {
/*     */       case '<':
/* 151 */         buf.append("&lt;");
/* 152 */         break;
/*     */       case '>':
/* 154 */         buf.append("&gt;");
/* 155 */         break;
/*     */       case '"':
/* 157 */         buf.append("&quot;");
/* 158 */         break;
/*     */       case '&':
/* 160 */         buf.append("&amp;");
/* 161 */         break;
/*     */       default:
/* 163 */         buf.append(c);
/*     */       }
/*     */     }
/*     */ 
/* 167 */     return buf.toString();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.chainsaw.DetailPanel
 * JD-Core Version:    0.6.0
 */
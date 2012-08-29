/*     */ package org.apache.log4j.chainsaw;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.StringReader;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import org.apache.log4j.Category;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.XMLReader;
/*     */ 
/*     */ class LoadXMLAction extends AbstractAction
/*     */ {
/*  43 */   private static final Logger LOG = Logger.getLogger(LoadXMLAction.class);
/*     */   private final JFrame mParent;
/*  52 */   private final JFileChooser mChooser = new JFileChooser();
/*     */   private final XMLReader mParser;
/*     */   private final XMLFileHandler mHandler;
/*     */ 
/*     */   LoadXMLAction(JFrame aParent, MyTableModel aModel)
/*     */     throws SAXException, ParserConfigurationException
/*     */   {
/*  54 */     this.mChooser.setMultiSelectionEnabled(false);
/*  55 */     this.mChooser.setFileSelectionMode(0);
/*     */ 
/*  75 */     this.mParent = aParent;
/*  76 */     this.mHandler = new XMLFileHandler(aModel);
/*  77 */     this.mParser = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
/*  78 */     this.mParser.setContentHandler(this.mHandler);
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent aIgnore)
/*     */   {
/*  86 */     LOG.info("load file called");
/*  87 */     if (this.mChooser.showOpenDialog(this.mParent) == 0) {
/*  88 */       LOG.info("Need to load a file");
/*  89 */       File chosen = this.mChooser.getSelectedFile();
/*  90 */       LOG.info("loading the contents of " + chosen.getAbsolutePath());
/*     */       try {
/*  92 */         int num = loadFile(chosen.getAbsolutePath());
/*  93 */         JOptionPane.showMessageDialog(this.mParent, "Loaded " + num + " events.", "CHAINSAW", 1);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*  99 */         LOG.warn("caught an exception loading the file", e);
/* 100 */         JOptionPane.showMessageDialog(this.mParent, "Error parsing file - " + e.getMessage(), "CHAINSAW", 0);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private int loadFile(String aFile)
/*     */     throws SAXException, IOException
/*     */   {
/* 120 */     synchronized (this.mParser)
/*     */     {
/* 122 */       StringBuffer buf = new StringBuffer();
/* 123 */       buf.append("<?xml version=\"1.0\" standalone=\"yes\"?>\n");
/* 124 */       buf.append("<!DOCTYPE log4j:eventSet ");
/* 125 */       buf.append("[<!ENTITY data SYSTEM \"file:///");
/* 126 */       buf.append(aFile);
/* 127 */       buf.append("\">]>\n");
/* 128 */       buf.append("<log4j:eventSet xmlns:log4j=\"Claira\">\n");
/* 129 */       buf.append("&data;\n");
/* 130 */       buf.append("</log4j:eventSet>\n");
/*     */ 
/* 132 */       InputSource is = new InputSource(new StringReader(buf.toString()));
/*     */ 
/* 134 */       this.mParser.parse(is);
/* 135 */       return this.mHandler.getNumEvents();
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.chainsaw.LoadXMLAction
 * JD-Core Version:    0.6.0
 */
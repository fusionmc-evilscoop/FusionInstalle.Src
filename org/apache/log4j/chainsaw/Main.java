/*     */ package org.apache.log4j.chainsaw;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.IOException;
/*     */ import java.util.Properties;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JSplitPane;
/*     */ import javax.swing.JTable;
/*     */ import org.apache.log4j.Category;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.PropertyConfigurator;
/*     */ 
/*     */ public class Main extends JFrame
/*     */ {
/*     */   private static final int DEFAULT_PORT = 4445;
/*     */   public static final String PORT_PROP_NAME = "chainsaw.port";
/*  53 */   private static final Logger LOG = Logger.getLogger(Main.class);
/*     */ 
/*     */   private Main()
/*     */   {
/*  60 */     super("CHAINSAW - Log4J Log Viewer");
/*     */ 
/*  62 */     MyTableModel model = new MyTableModel();
/*     */ 
/*  65 */     JMenuBar menuBar = new JMenuBar();
/*  66 */     setJMenuBar(menuBar);
/*  67 */     JMenu menu = new JMenu("File");
/*  68 */     menuBar.add(menu);
/*     */     try
/*     */     {
/*  71 */       LoadXMLAction lxa = new LoadXMLAction(this, model);
/*  72 */       JMenuItem loadMenuItem = new JMenuItem("Load file...");
/*  73 */       menu.add(loadMenuItem);
/*  74 */       loadMenuItem.addActionListener(lxa);
/*     */     } catch (NoClassDefFoundError e) {
/*  76 */       LOG.info("Missing classes for XML parser", e);
/*  77 */       JOptionPane.showMessageDialog(this, "XML parser not in classpath - unable to load XML events.", "CHAINSAW", 0);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  83 */       LOG.info("Unable to create the action to load XML files", e);
/*  84 */       JOptionPane.showMessageDialog(this, "Unable to create a XML parser - unable to load XML events.", "CHAINSAW", 0);
/*     */     }
/*     */ 
/*  91 */     JMenuItem exitMenuItem = new JMenuItem("Exit");
/*  92 */     menu.add(exitMenuItem);
/*  93 */     exitMenuItem.addActionListener(ExitAction.INSTANCE);
/*     */ 
/*  96 */     ControlPanel cp = new ControlPanel(model);
/*  97 */     getContentPane().add(cp, "North");
/*     */ 
/* 100 */     JTable table = new JTable(model);
/* 101 */     table.setSelectionMode(0);
/* 102 */     JScrollPane scrollPane = new JScrollPane(table);
/* 103 */     scrollPane.setBorder(BorderFactory.createTitledBorder("Events: "));
/* 104 */     scrollPane.setPreferredSize(new Dimension(900, 300));
/*     */ 
/* 107 */     JPanel details = new DetailPanel(table, model);
/* 108 */     details.setPreferredSize(new Dimension(900, 300));
/*     */ 
/* 111 */     JSplitPane jsp = new JSplitPane(0, scrollPane, details);
/*     */ 
/* 113 */     getContentPane().add(jsp, "Center");
/*     */ 
/* 115 */     addWindowListener(new WindowAdapter() {
/*     */       public void windowClosing(WindowEvent aEvent) {
/* 117 */         ExitAction.INSTANCE.actionPerformed(null);
/*     */       }
/*     */     });
/* 121 */     pack();
/* 122 */     setVisible(true);
/*     */ 
/* 124 */     setupReceiver(model);
/*     */   }
/*     */ 
/*     */   private void setupReceiver(MyTableModel aModel)
/*     */   {
/* 133 */     int port = 4445;
/* 134 */     String strRep = System.getProperty("chainsaw.port");
/* 135 */     if (strRep != null) {
/*     */       try {
/* 137 */         port = Integer.parseInt(strRep);
/*     */       } catch (NumberFormatException nfe) {
/* 139 */         LOG.fatal("Unable to parse chainsaw.port property with value " + strRep + ".");
/*     */ 
/* 141 */         JOptionPane.showMessageDialog(this, "Unable to parse port number from '" + strRep + "', quitting.", "CHAINSAW", 0);
/*     */ 
/* 147 */         System.exit(1);
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/* 152 */       LoggingReceiver lr = new LoggingReceiver(aModel, port);
/* 153 */       lr.start();
/*     */     } catch (IOException e) {
/* 155 */       LOG.fatal("Unable to connect to socket server, quiting", e);
/* 156 */       JOptionPane.showMessageDialog(this, "Unable to create socket on port " + port + ", quitting.", "CHAINSAW", 0);
/*     */ 
/* 161 */       System.exit(1);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void initLog4J()
/*     */   {
/* 173 */     Properties props = new Properties();
/* 174 */     props.setProperty("log4j.rootLogger", "DEBUG, A1");
/* 175 */     props.setProperty("log4j.appender.A1", "org.apache.log4j.ConsoleAppender");
/*     */ 
/* 177 */     props.setProperty("log4j.appender.A1.layout", "org.apache.log4j.TTCCLayout");
/*     */ 
/* 179 */     PropertyConfigurator.configure(props);
/*     */   }
/*     */ 
/*     */   public static void main(String[] aArgs)
/*     */   {
/* 188 */     initLog4J();
/* 189 */     new Main();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.chainsaw.Main
 * JD-Core Version:    0.6.0
 */
/*      */ package org.apache.log4j.lf5.viewer;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FlowLayout;
/*      */ import java.awt.Font;
/*      */ import java.awt.Frame;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.WindowAdapter;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.util.ArrayList;
/*      */ import java.util.EventObject;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.swing.AbstractButton;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JCheckBoxMenuItem;
/*      */ import javax.swing.JColorChooser;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JMenu;
/*      */ import javax.swing.JMenuBar;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JRootPane;
/*      */ import javax.swing.JScrollBar;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JSplitPane;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.JTextArea;
/*      */ import javax.swing.JToolBar;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.ListSelectionModel;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.text.JTextComponent;
/*      */ import org.apache.log4j.lf5.LogLevel;
/*      */ import org.apache.log4j.lf5.LogRecord;
/*      */ import org.apache.log4j.lf5.LogRecordFilter;
/*      */ import org.apache.log4j.lf5.util.DateFormatManager;
/*      */ import org.apache.log4j.lf5.util.LogFileParser;
/*      */ import org.apache.log4j.lf5.viewer.categoryexplorer.CategoryExplorerModel;
/*      */ import org.apache.log4j.lf5.viewer.categoryexplorer.CategoryExplorerTree;
/*      */ import org.apache.log4j.lf5.viewer.categoryexplorer.CategoryPath;
/*      */ import org.apache.log4j.lf5.viewer.configure.ConfigurationManager;
/*      */ import org.apache.log4j.lf5.viewer.configure.MRUFileManager;
/*      */ 
/*      */ public class LogBrokerMonitor
/*      */ {
/*      */   public static final String DETAILED_VIEW = "Detailed";
/*      */   protected JFrame _logMonitorFrame;
/*   68 */   protected int _logMonitorFrameWidth = 550;
/*   69 */   protected int _logMonitorFrameHeight = 500;
/*      */   protected LogTable _table;
/*      */   protected CategoryExplorerTree _categoryExplorerTree;
/*      */   protected String _searchText;
/*   73 */   protected String _NDCTextFilter = "";
/*   74 */   protected LogLevel _leastSevereDisplayedLogLevel = LogLevel.DEBUG;
/*      */   protected JScrollPane _logTableScrollPane;
/*      */   protected JLabel _statusLabel;
/*   78 */   protected Object _lock = new Object();
/*      */   protected JComboBox _fontSizeCombo;
/*   81 */   protected int _fontSize = 10;
/*   82 */   protected String _fontName = "Dialog";
/*   83 */   protected String _currentView = "Detailed";
/*      */ 
/*   85 */   protected boolean _loadSystemFonts = false;
/*   86 */   protected boolean _trackTableScrollPane = true;
/*      */   protected Dimension _lastTableViewportSize;
/*   88 */   protected boolean _callSystemExitOnClose = false;
/*   89 */   protected List _displayedLogBrokerProperties = new Vector();
/*      */ 
/*   91 */   protected Map _logLevelMenuItems = new HashMap();
/*   92 */   protected Map _logTableColumnMenuItems = new HashMap();
/*      */ 
/*   94 */   protected List _levels = null;
/*   95 */   protected List _columns = null;
/*   96 */   protected boolean _isDisposed = false;
/*      */ 
/*   98 */   protected ConfigurationManager _configurationManager = null;
/*   99 */   protected MRUFileManager _mruFileManager = null;
/*  100 */   protected File _fileLocation = null;
/*      */ 
/*      */   public LogBrokerMonitor(List logLevels)
/*      */   {
/*  115 */     this._levels = logLevels;
/*  116 */     this._columns = LogTableColumn.getLogTableColumns();
/*      */ 
/*  120 */     String callSystemExitOnClose = System.getProperty("monitor.exit");
/*      */ 
/*  122 */     if (callSystemExitOnClose == null) {
/*  123 */       callSystemExitOnClose = "false";
/*      */     }
/*  125 */     callSystemExitOnClose = callSystemExitOnClose.trim().toLowerCase();
/*      */ 
/*  127 */     if (callSystemExitOnClose.equals("true")) {
/*  128 */       this._callSystemExitOnClose = true;
/*      */     }
/*      */ 
/*  131 */     initComponents();
/*      */ 
/*  134 */     this._logMonitorFrame.addWindowListener(new LogBrokerMonitorWindowAdaptor(this));
/*      */   }
/*      */ 
/*      */   public void show(int delay)
/*      */   {
/*  148 */     if (this._logMonitorFrame.isVisible()) {
/*  149 */       return;
/*      */     }
/*      */ 
/*  152 */     SwingUtilities.invokeLater(new Runnable(delay) { private final int val$delay;
/*      */ 
/*  154 */       public void run() { Thread.yield();
/*  155 */         LogBrokerMonitor.this.pause(this.val$delay);
/*  156 */         LogBrokerMonitor.this._logMonitorFrame.setVisible(true); } } );
/*      */   }
/*      */ 
/*      */   public void show()
/*      */   {
/*  162 */     show(0);
/*      */   }
/*      */ 
/*      */   public void dispose()
/*      */   {
/*  169 */     this._logMonitorFrame.dispose();
/*  170 */     this._isDisposed = true;
/*      */ 
/*  172 */     if (this._callSystemExitOnClose == true)
/*  173 */       System.exit(0);
/*      */   }
/*      */ 
/*      */   public void hide()
/*      */   {
/*  181 */     this._logMonitorFrame.setVisible(false);
/*      */   }
/*      */ 
/*      */   public DateFormatManager getDateFormatManager()
/*      */   {
/*  188 */     return this._table.getDateFormatManager();
/*      */   }
/*      */ 
/*      */   public void setDateFormatManager(DateFormatManager dfm)
/*      */   {
/*  195 */     this._table.setDateFormatManager(dfm);
/*      */   }
/*      */ 
/*      */   public boolean getCallSystemExitOnClose()
/*      */   {
/*  203 */     return this._callSystemExitOnClose;
/*      */   }
/*      */ 
/*      */   public void setCallSystemExitOnClose(boolean callSystemExitOnClose)
/*      */   {
/*  211 */     this._callSystemExitOnClose = callSystemExitOnClose;
/*      */   }
/*      */ 
/*      */   public void addMessage(LogRecord lr)
/*      */   {
/*  220 */     if (this._isDisposed == true)
/*      */     {
/*  223 */       return;
/*      */     }
/*      */ 
/*  226 */     SwingUtilities.invokeLater(new Runnable(lr) { private final LogRecord val$lr;
/*      */ 
/*  228 */       public void run() { LogBrokerMonitor.this._categoryExplorerTree.getExplorerModel().addLogRecord(this.val$lr);
/*  229 */         LogBrokerMonitor.this._table.getFilteredLogTableModel().addLogRecord(this.val$lr);
/*  230 */         LogBrokerMonitor.this.updateStatusLabel(); } } );
/*      */   }
/*      */ 
/*      */   public void setMaxNumberOfLogRecords(int maxNumberOfLogRecords)
/*      */   {
/*  236 */     this._table.getFilteredLogTableModel().setMaxNumberOfLogRecords(maxNumberOfLogRecords);
/*      */   }
/*      */ 
/*      */   public JFrame getBaseFrame() {
/*  240 */     return this._logMonitorFrame;
/*      */   }
/*      */ 
/*      */   public void setTitle(String title) {
/*  244 */     this._logMonitorFrame.setTitle(title + " - LogFactor5");
/*      */   }
/*      */ 
/*      */   public void setFrameSize(int width, int height) {
/*  248 */     Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
/*  249 */     if ((0 < width) && (width < screen.width)) {
/*  250 */       this._logMonitorFrameWidth = width;
/*      */     }
/*  252 */     if ((0 < height) && (height < screen.height)) {
/*  253 */       this._logMonitorFrameHeight = height;
/*      */     }
/*  255 */     updateFrameSize();
/*      */   }
/*      */ 
/*      */   public void setFontSize(int fontSize) {
/*  259 */     changeFontSizeCombo(this._fontSizeCombo, fontSize);
/*      */   }
/*      */ 
/*      */   public void addDisplayedProperty(Object messageLine)
/*      */   {
/*  265 */     this._displayedLogBrokerProperties.add(messageLine);
/*      */   }
/*      */ 
/*      */   public Map getLogLevelMenuItems() {
/*  269 */     return this._logLevelMenuItems;
/*      */   }
/*      */ 
/*      */   public Map getLogTableColumnMenuItems() {
/*  273 */     return this._logTableColumnMenuItems;
/*      */   }
/*      */ 
/*      */   public JCheckBoxMenuItem getTableColumnMenuItem(LogTableColumn column) {
/*  277 */     return getLogTableColumnMenuItem(column);
/*      */   }
/*      */ 
/*      */   public CategoryExplorerTree getCategoryExplorerTree() {
/*  281 */     return this._categoryExplorerTree;
/*      */   }
/*      */ 
/*      */   public String getNDCTextFilter()
/*      */   {
/*  287 */     return this._NDCTextFilter;
/*      */   }
/*      */ 
/*      */   public void setNDCLogRecordFilter(String textFilter)
/*      */   {
/*  294 */     this._table.getFilteredLogTableModel().setLogRecordFilter(createNDCLogRecordFilter(textFilter));
/*      */   }
/*      */ 
/*      */   protected void setSearchText(String text)
/*      */   {
/*  302 */     this._searchText = text;
/*      */   }
/*      */ 
/*      */   protected void setNDCTextFilter(String text)
/*      */   {
/*  309 */     if (text == null)
/*  310 */       this._NDCTextFilter = "";
/*      */     else
/*  312 */       this._NDCTextFilter = text;
/*      */   }
/*      */ 
/*      */   protected void sortByNDC()
/*      */   {
/*  320 */     String text = this._NDCTextFilter;
/*  321 */     if ((text == null) || (text.length() == 0)) {
/*  322 */       return;
/*      */     }
/*      */ 
/*  326 */     this._table.getFilteredLogTableModel().setLogRecordFilter(createNDCLogRecordFilter(text));
/*      */   }
/*      */ 
/*      */   protected void findSearchText()
/*      */   {
/*  331 */     String text = this._searchText;
/*  332 */     if ((text == null) || (text.length() == 0)) {
/*  333 */       return;
/*      */     }
/*  335 */     int startRow = getFirstSelectedRow();
/*  336 */     int foundRow = findRecord(startRow, text, this._table.getFilteredLogTableModel().getFilteredRecords());
/*      */ 
/*  341 */     selectRow(foundRow);
/*      */   }
/*      */ 
/*      */   protected int getFirstSelectedRow() {
/*  345 */     return this._table.getSelectionModel().getMinSelectionIndex();
/*      */   }
/*      */ 
/*      */   protected void selectRow(int foundRow) {
/*  349 */     if (foundRow == -1) {
/*  350 */       String message = this._searchText + " not found.";
/*  351 */       JOptionPane.showMessageDialog(this._logMonitorFrame, message, "Text not found", 1);
/*      */ 
/*  357 */       return;
/*      */     }
/*  359 */     LF5SwingUtils.selectRow(foundRow, this._table, this._logTableScrollPane);
/*      */   }
/*      */ 
/*      */   protected int findRecord(int startRow, String searchText, List records)
/*      */   {
/*  367 */     if (startRow < 0)
/*  368 */       startRow = 0;
/*      */     else {
/*  370 */       startRow++;
/*      */     }
/*  372 */     int len = records.size();
/*      */ 
/*  374 */     for (int i = startRow; i < len; i++) {
/*  375 */       if (matches((LogRecord)records.get(i), searchText)) {
/*  376 */         return i;
/*      */       }
/*      */     }
/*      */ 
/*  380 */     len = startRow;
/*  381 */     for (int i = 0; i < len; i++) {
/*  382 */       if (matches((LogRecord)records.get(i), searchText)) {
/*  383 */         return i;
/*      */       }
/*      */     }
/*      */ 
/*  387 */     return -1;
/*      */   }
/*      */ 
/*      */   protected boolean matches(LogRecord record, String text)
/*      */   {
/*  395 */     String message = record.getMessage();
/*  396 */     String NDC = record.getNDC();
/*      */ 
/*  398 */     if (((message == null) && (NDC == null)) || (text == null)) {
/*  399 */       return false;
/*      */     }
/*      */ 
/*  403 */     return (message.toLowerCase().indexOf(text.toLowerCase()) != -1) || (NDC.toLowerCase().indexOf(text.toLowerCase()) != -1);
/*      */   }
/*      */ 
/*      */   protected void refresh(JTextArea textArea)
/*      */   {
/*  415 */     String text = textArea.getText();
/*  416 */     textArea.setText("");
/*  417 */     textArea.setText(text);
/*      */   }
/*      */ 
/*      */   protected void refreshDetailTextArea() {
/*  421 */     refresh(this._table._detailTextArea);
/*      */   }
/*      */ 
/*      */   protected void clearDetailTextArea() {
/*  425 */     this._table._detailTextArea.setText("");
/*      */   }
/*      */ 
/*      */   protected int changeFontSizeCombo(JComboBox box, int requestedSize)
/*      */   {
/*  434 */     int len = box.getItemCount();
/*      */ 
/*  437 */     Object selectedObject = box.getItemAt(0);
/*  438 */     int selectedValue = Integer.parseInt(String.valueOf(selectedObject));
/*  439 */     for (int i = 0; i < len; i++) {
/*  440 */       Object currentObject = box.getItemAt(i);
/*  441 */       int currentValue = Integer.parseInt(String.valueOf(currentObject));
/*  442 */       if ((selectedValue < currentValue) && (currentValue <= requestedSize)) {
/*  443 */         selectedValue = currentValue;
/*  444 */         selectedObject = currentObject;
/*      */       }
/*      */     }
/*  447 */     box.setSelectedItem(selectedObject);
/*  448 */     return selectedValue;
/*      */   }
/*      */ 
/*      */   protected void setFontSizeSilently(int fontSize)
/*      */   {
/*  455 */     this._fontSize = fontSize;
/*  456 */     setFontSize(this._table._detailTextArea, fontSize);
/*  457 */     selectRow(0);
/*  458 */     setFontSize(this._table, fontSize);
/*      */   }
/*      */ 
/*      */   protected void setFontSize(Component component, int fontSize) {
/*  462 */     Font oldFont = component.getFont();
/*  463 */     Font newFont = new Font(oldFont.getFontName(), oldFont.getStyle(), fontSize);
/*      */ 
/*  465 */     component.setFont(newFont);
/*      */   }
/*      */ 
/*      */   protected void updateFrameSize() {
/*  469 */     this._logMonitorFrame.setSize(this._logMonitorFrameWidth, this._logMonitorFrameHeight);
/*  470 */     centerFrame(this._logMonitorFrame);
/*      */   }
/*      */ 
/*      */   protected void pause(int millis) {
/*      */     try {
/*  475 */       Thread.sleep(millis);
/*      */     }
/*      */     catch (InterruptedException e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void initComponents()
/*      */   {
/*  485 */     this._logMonitorFrame = new JFrame("LogFactor5");
/*      */ 
/*  487 */     this._logMonitorFrame.setDefaultCloseOperation(0);
/*      */ 
/*  489 */     String resource = "/org/apache/log4j/lf5/viewer/images/lf5_small_icon.gif";
/*      */ 
/*  491 */     URL lf5IconURL = getClass().getResource(resource);
/*      */ 
/*  493 */     if (lf5IconURL != null) {
/*  494 */       this._logMonitorFrame.setIconImage(new ImageIcon(lf5IconURL).getImage());
/*      */     }
/*  496 */     updateFrameSize();
/*      */ 
/*  501 */     JTextArea detailTA = createDetailTextArea();
/*  502 */     JScrollPane detailTAScrollPane = new JScrollPane(detailTA);
/*  503 */     this._table = new LogTable(detailTA);
/*  504 */     setView(this._currentView, this._table);
/*  505 */     this._table.setFont(new Font(this._fontName, 0, this._fontSize));
/*  506 */     this._logTableScrollPane = new JScrollPane(this._table);
/*      */ 
/*  508 */     if (this._trackTableScrollPane) {
/*  509 */       this._logTableScrollPane.getVerticalScrollBar().addAdjustmentListener(new TrackingAdjustmentListener());
/*      */     }
/*      */ 
/*  518 */     JSplitPane tableViewerSplitPane = new JSplitPane();
/*  519 */     tableViewerSplitPane.setOneTouchExpandable(true);
/*  520 */     tableViewerSplitPane.setOrientation(0);
/*  521 */     tableViewerSplitPane.setLeftComponent(this._logTableScrollPane);
/*  522 */     tableViewerSplitPane.setRightComponent(detailTAScrollPane);
/*      */ 
/*  530 */     tableViewerSplitPane.setDividerLocation(350);
/*      */ 
/*  536 */     this._categoryExplorerTree = new CategoryExplorerTree();
/*      */ 
/*  538 */     this._table.getFilteredLogTableModel().setLogRecordFilter(createLogRecordFilter());
/*      */ 
/*  540 */     JScrollPane categoryExplorerTreeScrollPane = new JScrollPane(this._categoryExplorerTree);
/*      */ 
/*  542 */     categoryExplorerTreeScrollPane.setPreferredSize(new Dimension(130, 400));
/*      */ 
/*  545 */     this._mruFileManager = new MRUFileManager();
/*      */ 
/*  551 */     JSplitPane splitPane = new JSplitPane();
/*  552 */     splitPane.setOneTouchExpandable(true);
/*  553 */     splitPane.setRightComponent(tableViewerSplitPane);
/*  554 */     splitPane.setLeftComponent(categoryExplorerTreeScrollPane);
/*      */ 
/*  556 */     splitPane.setDividerLocation(130);
/*      */ 
/*  561 */     this._logMonitorFrame.getRootPane().setJMenuBar(createMenuBar());
/*  562 */     this._logMonitorFrame.getContentPane().add(splitPane, "Center");
/*  563 */     this._logMonitorFrame.getContentPane().add(createToolBar(), "North");
/*      */ 
/*  565 */     this._logMonitorFrame.getContentPane().add(createStatusArea(), "South");
/*      */ 
/*  568 */     makeLogTableListenToCategoryExplorer();
/*  569 */     addTableModelProperties();
/*      */ 
/*  574 */     this._configurationManager = new ConfigurationManager(this, this._table);
/*      */   }
/*      */ 
/*      */   protected LogRecordFilter createLogRecordFilter()
/*      */   {
/*  579 */     LogRecordFilter result = new LogRecordFilter() {
/*      */       public boolean passes(LogRecord record) {
/*  581 */         CategoryPath path = new CategoryPath(record.getCategory());
/*  582 */         return (LogBrokerMonitor.this.getMenuItem(record.getLevel()).isSelected()) && (LogBrokerMonitor.this._categoryExplorerTree.getExplorerModel().isCategoryPathActive(path));
/*      */       }
/*      */     };
/*  587 */     return result;
/*      */   }
/*      */ 
/*      */   protected LogRecordFilter createNDCLogRecordFilter(String text)
/*      */   {
/*  593 */     this._NDCTextFilter = text;
/*  594 */     LogRecordFilter result = new LogRecordFilter() {
/*      */       public boolean passes(LogRecord record) {
/*  596 */         String NDC = record.getNDC();
/*  597 */         CategoryPath path = new CategoryPath(record.getCategory());
/*  598 */         if ((NDC == null) || (LogBrokerMonitor.this._NDCTextFilter == null))
/*  599 */           return false;
/*  600 */         if (NDC.toLowerCase().indexOf(LogBrokerMonitor.this._NDCTextFilter.toLowerCase()) == -1) {
/*  601 */           return false;
/*      */         }
/*  603 */         return (LogBrokerMonitor.this.getMenuItem(record.getLevel()).isSelected()) && (LogBrokerMonitor.this._categoryExplorerTree.getExplorerModel().isCategoryPathActive(path));
/*      */       }
/*      */     };
/*  609 */     return result;
/*      */   }
/*      */ 
/*      */   protected void updateStatusLabel()
/*      */   {
/*  614 */     this._statusLabel.setText(getRecordsDisplayedMessage());
/*      */   }
/*      */ 
/*      */   protected String getRecordsDisplayedMessage() {
/*  618 */     FilteredLogTableModel model = this._table.getFilteredLogTableModel();
/*  619 */     return getStatusText(model.getRowCount(), model.getTotalRowCount());
/*      */   }
/*      */ 
/*      */   protected void addTableModelProperties() {
/*  623 */     FilteredLogTableModel model = this._table.getFilteredLogTableModel();
/*      */ 
/*  625 */     addDisplayedProperty(new Object() {
/*      */       public String toString() {
/*  627 */         return LogBrokerMonitor.this.getRecordsDisplayedMessage();
/*      */       }
/*      */     });
/*  630 */     addDisplayedProperty(new Object(model) { private final FilteredLogTableModel val$model;
/*      */ 
/*  632 */       public String toString() { return "Maximum number of displayed LogRecords: " + this.val$model._maxNumberOfLogRecords; }
/*      */     });
/*      */   }
/*      */ 
/*      */   protected String getStatusText(int displayedRows, int totalRows)
/*      */   {
/*  639 */     StringBuffer result = new StringBuffer();
/*  640 */     result.append("Displaying: ");
/*  641 */     result.append(displayedRows);
/*  642 */     result.append(" records out of a total of: ");
/*  643 */     result.append(totalRows);
/*  644 */     result.append(" records.");
/*  645 */     return result.toString();
/*      */   }
/*      */ 
/*      */   protected void makeLogTableListenToCategoryExplorer() {
/*  649 */     ActionListener listener = new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  651 */         LogBrokerMonitor.this._table.getFilteredLogTableModel().refresh();
/*  652 */         LogBrokerMonitor.this.updateStatusLabel();
/*      */       }
/*      */     };
/*  655 */     this._categoryExplorerTree.getExplorerModel().addActionListener(listener);
/*      */   }
/*      */ 
/*      */   protected JPanel createStatusArea() {
/*  659 */     JPanel statusArea = new JPanel();
/*  660 */     JLabel status = new JLabel("No log records to display.");
/*      */ 
/*  662 */     this._statusLabel = status;
/*  663 */     status.setHorizontalAlignment(2);
/*      */ 
/*  665 */     statusArea.setBorder(BorderFactory.createEtchedBorder());
/*  666 */     statusArea.setLayout(new FlowLayout(0, 0, 0));
/*  667 */     statusArea.add(status);
/*      */ 
/*  669 */     return statusArea;
/*      */   }
/*      */ 
/*      */   protected JTextArea createDetailTextArea() {
/*  673 */     JTextArea detailTA = new JTextArea();
/*  674 */     detailTA.setFont(new Font("Monospaced", 0, 14));
/*  675 */     detailTA.setTabSize(3);
/*  676 */     detailTA.setLineWrap(true);
/*  677 */     detailTA.setWrapStyleWord(false);
/*  678 */     return detailTA;
/*      */   }
/*      */ 
/*      */   protected JMenuBar createMenuBar() {
/*  682 */     JMenuBar menuBar = new JMenuBar();
/*  683 */     menuBar.add(createFileMenu());
/*  684 */     menuBar.add(createEditMenu());
/*  685 */     menuBar.add(createLogLevelMenu());
/*  686 */     menuBar.add(createViewMenu());
/*  687 */     menuBar.add(createConfigureMenu());
/*  688 */     menuBar.add(createHelpMenu());
/*      */ 
/*  690 */     return menuBar;
/*      */   }
/*      */ 
/*      */   protected JMenu createLogLevelMenu() {
/*  694 */     JMenu result = new JMenu("Log Level");
/*  695 */     result.setMnemonic('l');
/*  696 */     Iterator levels = getLogLevels();
/*  697 */     while (levels.hasNext()) {
/*  698 */       result.add(getMenuItem((LogLevel)levels.next()));
/*      */     }
/*      */ 
/*  701 */     result.addSeparator();
/*  702 */     result.add(createAllLogLevelsMenuItem());
/*  703 */     result.add(createNoLogLevelsMenuItem());
/*  704 */     result.addSeparator();
/*  705 */     result.add(createLogLevelColorMenu());
/*  706 */     result.add(createResetLogLevelColorMenuItem());
/*      */ 
/*  708 */     return result;
/*      */   }
/*      */ 
/*      */   protected JMenuItem createAllLogLevelsMenuItem() {
/*  712 */     JMenuItem result = new JMenuItem("Show all LogLevels");
/*  713 */     result.setMnemonic('s');
/*  714 */     result.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  716 */         LogBrokerMonitor.this.selectAllLogLevels(true);
/*  717 */         LogBrokerMonitor.this._table.getFilteredLogTableModel().refresh();
/*  718 */         LogBrokerMonitor.this.updateStatusLabel();
/*      */       }
/*      */     });
/*  721 */     return result;
/*      */   }
/*      */ 
/*      */   protected JMenuItem createNoLogLevelsMenuItem() {
/*  725 */     JMenuItem result = new JMenuItem("Hide all LogLevels");
/*  726 */     result.setMnemonic('h');
/*  727 */     result.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  729 */         LogBrokerMonitor.this.selectAllLogLevels(false);
/*  730 */         LogBrokerMonitor.this._table.getFilteredLogTableModel().refresh();
/*  731 */         LogBrokerMonitor.this.updateStatusLabel();
/*      */       }
/*      */     });
/*  734 */     return result;
/*      */   }
/*      */ 
/*      */   protected JMenu createLogLevelColorMenu() {
/*  738 */     JMenu colorMenu = new JMenu("Configure LogLevel Colors");
/*  739 */     colorMenu.setMnemonic('c');
/*  740 */     Iterator levels = getLogLevels();
/*  741 */     while (levels.hasNext()) {
/*  742 */       colorMenu.add(createSubMenuItem((LogLevel)levels.next()));
/*      */     }
/*      */ 
/*  745 */     return colorMenu;
/*      */   }
/*      */ 
/*      */   protected JMenuItem createResetLogLevelColorMenuItem() {
/*  749 */     JMenuItem result = new JMenuItem("Reset LogLevel Colors");
/*  750 */     result.setMnemonic('r');
/*  751 */     result.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e) {
/*  754 */         LogLevel.resetLogLevelColorMap();
/*      */ 
/*  757 */         LogBrokerMonitor.this._table.getFilteredLogTableModel().refresh();
/*      */       }
/*      */     });
/*  760 */     return result;
/*      */   }
/*      */ 
/*      */   protected void selectAllLogLevels(boolean selected) {
/*  764 */     Iterator levels = getLogLevels();
/*  765 */     while (levels.hasNext())
/*  766 */       getMenuItem((LogLevel)levels.next()).setSelected(selected);
/*      */   }
/*      */ 
/*      */   protected JCheckBoxMenuItem getMenuItem(LogLevel level)
/*      */   {
/*  771 */     JCheckBoxMenuItem result = (JCheckBoxMenuItem)this._logLevelMenuItems.get(level);
/*  772 */     if (result == null) {
/*  773 */       result = createMenuItem(level);
/*  774 */       this._logLevelMenuItems.put(level, result);
/*      */     }
/*  776 */     return result;
/*      */   }
/*      */ 
/*      */   protected JMenuItem createSubMenuItem(LogLevel level) {
/*  780 */     JMenuItem result = new JMenuItem(level.toString());
/*  781 */     LogLevel logLevel = level;
/*  782 */     result.setMnemonic(level.toString().charAt(0));
/*  783 */     result.addActionListener(new ActionListener(result, logLevel) { private final JMenuItem val$result;
/*      */       private final LogLevel val$logLevel;
/*      */ 
/*  785 */       public void actionPerformed(ActionEvent e) { LogBrokerMonitor.this.showLogLevelColorChangeDialog(this.val$result, this.val$logLevel);
/*      */       }
/*      */     });
/*  789 */     return result;
/*      */   }
/*      */ 
/*      */   protected void showLogLevelColorChangeDialog(JMenuItem result, LogLevel level)
/*      */   {
/*  794 */     JMenuItem menuItem = result;
/*  795 */     Color newColor = JColorChooser.showDialog(this._logMonitorFrame, "Choose LogLevel Color", result.getForeground());
/*      */ 
/*  800 */     if (newColor != null)
/*      */     {
/*  802 */       level.setLogLevelColorMap(level, newColor);
/*  803 */       this._table.getFilteredLogTableModel().refresh();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected JCheckBoxMenuItem createMenuItem(LogLevel level)
/*      */   {
/*  809 */     JCheckBoxMenuItem result = new JCheckBoxMenuItem(level.toString());
/*  810 */     result.setSelected(true);
/*  811 */     result.setMnemonic(level.toString().charAt(0));
/*  812 */     result.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  814 */         LogBrokerMonitor.this._table.getFilteredLogTableModel().refresh();
/*  815 */         LogBrokerMonitor.this.updateStatusLabel();
/*      */       }
/*      */     });
/*  818 */     return result;
/*      */   }
/*      */ 
/*      */   protected JMenu createViewMenu()
/*      */   {
/*  823 */     JMenu result = new JMenu("View");
/*  824 */     result.setMnemonic('v');
/*  825 */     Iterator columns = getLogTableColumns();
/*  826 */     while (columns.hasNext()) {
/*  827 */       result.add(getLogTableColumnMenuItem((LogTableColumn)columns.next()));
/*      */     }
/*      */ 
/*  830 */     result.addSeparator();
/*  831 */     result.add(createAllLogTableColumnsMenuItem());
/*  832 */     result.add(createNoLogTableColumnsMenuItem());
/*  833 */     return result;
/*      */   }
/*      */ 
/*      */   protected JCheckBoxMenuItem getLogTableColumnMenuItem(LogTableColumn column) {
/*  837 */     JCheckBoxMenuItem result = (JCheckBoxMenuItem)this._logTableColumnMenuItems.get(column);
/*  838 */     if (result == null) {
/*  839 */       result = createLogTableColumnMenuItem(column);
/*  840 */       this._logTableColumnMenuItems.put(column, result);
/*      */     }
/*  842 */     return result;
/*      */   }
/*      */ 
/*      */   protected JCheckBoxMenuItem createLogTableColumnMenuItem(LogTableColumn column) {
/*  846 */     JCheckBoxMenuItem result = new JCheckBoxMenuItem(column.toString());
/*      */ 
/*  848 */     result.setSelected(true);
/*  849 */     result.setMnemonic(column.toString().charAt(0));
/*  850 */     result.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e) {
/*  853 */         List selectedColumns = LogBrokerMonitor.this.updateView();
/*  854 */         LogBrokerMonitor.this._table.setView(selectedColumns);
/*      */       }
/*      */     });
/*  857 */     return result;
/*      */   }
/*      */ 
/*      */   protected List updateView() {
/*  861 */     ArrayList updatedList = new ArrayList();
/*  862 */     Iterator columnIterator = this._columns.iterator();
/*  863 */     while (columnIterator.hasNext()) {
/*  864 */       LogTableColumn column = (LogTableColumn)columnIterator.next();
/*  865 */       JCheckBoxMenuItem result = getLogTableColumnMenuItem(column);
/*      */ 
/*  867 */       if (result.isSelected()) {
/*  868 */         updatedList.add(column);
/*      */       }
/*      */     }
/*      */ 
/*  872 */     return updatedList;
/*      */   }
/*      */ 
/*      */   protected JMenuItem createAllLogTableColumnsMenuItem() {
/*  876 */     JMenuItem result = new JMenuItem("Show all Columns");
/*  877 */     result.setMnemonic('s');
/*  878 */     result.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  880 */         LogBrokerMonitor.this.selectAllLogTableColumns(true);
/*      */ 
/*  882 */         List selectedColumns = LogBrokerMonitor.this.updateView();
/*  883 */         LogBrokerMonitor.this._table.setView(selectedColumns);
/*      */       }
/*      */     });
/*  886 */     return result;
/*      */   }
/*      */ 
/*      */   protected JMenuItem createNoLogTableColumnsMenuItem() {
/*  890 */     JMenuItem result = new JMenuItem("Hide all Columns");
/*  891 */     result.setMnemonic('h');
/*  892 */     result.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  894 */         LogBrokerMonitor.this.selectAllLogTableColumns(false);
/*      */ 
/*  896 */         List selectedColumns = LogBrokerMonitor.this.updateView();
/*  897 */         LogBrokerMonitor.this._table.setView(selectedColumns);
/*      */       }
/*      */     });
/*  900 */     return result;
/*      */   }
/*      */ 
/*      */   protected void selectAllLogTableColumns(boolean selected) {
/*  904 */     Iterator columns = getLogTableColumns();
/*  905 */     while (columns.hasNext())
/*  906 */       getLogTableColumnMenuItem((LogTableColumn)columns.next()).setSelected(selected);
/*      */   }
/*      */ 
/*      */   protected JMenu createFileMenu()
/*      */   {
/*  911 */     JMenu fileMenu = new JMenu("File");
/*  912 */     fileMenu.setMnemonic('f');
/*      */ 
/*  914 */     fileMenu.add(createOpenMI());
/*  915 */     fileMenu.add(createOpenURLMI());
/*  916 */     fileMenu.addSeparator();
/*  917 */     fileMenu.add(createCloseMI());
/*  918 */     createMRUFileListMI(fileMenu);
/*  919 */     fileMenu.addSeparator();
/*  920 */     fileMenu.add(createExitMI());
/*  921 */     return fileMenu;
/*      */   }
/*      */ 
/*      */   protected JMenuItem createOpenMI()
/*      */   {
/*  929 */     JMenuItem result = new JMenuItem("Open...");
/*  930 */     result.setMnemonic('o');
/*  931 */     result.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  933 */         LogBrokerMonitor.this.requestOpen();
/*      */       }
/*      */     });
/*  936 */     return result;
/*      */   }
/*      */ 
/*      */   protected JMenuItem createOpenURLMI()
/*      */   {
/*  944 */     JMenuItem result = new JMenuItem("Open URL...");
/*  945 */     result.setMnemonic('u');
/*  946 */     result.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  948 */         LogBrokerMonitor.this.requestOpenURL();
/*      */       }
/*      */     });
/*  951 */     return result;
/*      */   }
/*      */ 
/*      */   protected JMenuItem createCloseMI() {
/*  955 */     JMenuItem result = new JMenuItem("Close");
/*  956 */     result.setMnemonic('c');
/*  957 */     result.setAccelerator(KeyStroke.getKeyStroke("control Q"));
/*  958 */     result.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  960 */         LogBrokerMonitor.this.requestClose();
/*      */       }
/*      */     });
/*  963 */     return result;
/*      */   }
/*      */ 
/*      */   protected void createMRUFileListMI(JMenu menu)
/*      */   {
/*  972 */     String[] files = this._mruFileManager.getMRUFileList();
/*      */ 
/*  974 */     if (files != null) {
/*  975 */       menu.addSeparator();
/*  976 */       for (int i = 0; i < files.length; i++) {
/*  977 */         JMenuItem result = new JMenuItem(i + 1 + " " + files[i]);
/*  978 */         result.setMnemonic(i + 1);
/*  979 */         result.addActionListener(new ActionListener() {
/*      */           public void actionPerformed(ActionEvent e) {
/*  981 */             LogBrokerMonitor.this.requestOpenMRU(e);
/*      */           }
/*      */         });
/*  984 */         menu.add(result);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected JMenuItem createExitMI() {
/*  990 */     JMenuItem result = new JMenuItem("Exit");
/*  991 */     result.setMnemonic('x');
/*  992 */     result.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  994 */         LogBrokerMonitor.this.requestExit();
/*      */       }
/*      */     });
/*  997 */     return result;
/*      */   }
/*      */ 
/*      */   protected JMenu createConfigureMenu() {
/* 1001 */     JMenu configureMenu = new JMenu("Configure");
/* 1002 */     configureMenu.setMnemonic('c');
/* 1003 */     configureMenu.add(createConfigureSave());
/* 1004 */     configureMenu.add(createConfigureReset());
/* 1005 */     configureMenu.add(createConfigureMaxRecords());
/*      */ 
/* 1007 */     return configureMenu;
/*      */   }
/*      */ 
/*      */   protected JMenuItem createConfigureSave() {
/* 1011 */     JMenuItem result = new JMenuItem("Save");
/* 1012 */     result.setMnemonic('s');
/* 1013 */     result.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/* 1015 */         LogBrokerMonitor.this.saveConfiguration();
/*      */       }
/*      */     });
/* 1019 */     return result;
/*      */   }
/*      */ 
/*      */   protected JMenuItem createConfigureReset() {
/* 1023 */     JMenuItem result = new JMenuItem("Reset");
/* 1024 */     result.setMnemonic('r');
/* 1025 */     result.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/* 1027 */         LogBrokerMonitor.this.resetConfiguration();
/*      */       }
/*      */     });
/* 1031 */     return result;
/*      */   }
/*      */ 
/*      */   protected JMenuItem createConfigureMaxRecords() {
/* 1035 */     JMenuItem result = new JMenuItem("Set Max Number of Records");
/* 1036 */     result.setMnemonic('m');
/* 1037 */     result.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/* 1039 */         LogBrokerMonitor.this.setMaxRecordConfiguration();
/*      */       }
/*      */     });
/* 1043 */     return result;
/*      */   }
/*      */ 
/*      */   protected void saveConfiguration()
/*      */   {
/* 1048 */     this._configurationManager.save();
/*      */   }
/*      */ 
/*      */   protected void resetConfiguration() {
/* 1052 */     this._configurationManager.reset();
/*      */   }
/*      */ 
/*      */   protected void setMaxRecordConfiguration() {
/* 1056 */     LogFactor5InputDialog inputDialog = new LogFactor5InputDialog(getBaseFrame(), "Set Max Number of Records", "", 10);
/*      */ 
/* 1059 */     String temp = inputDialog.getText();
/*      */ 
/* 1061 */     if (temp != null)
/*      */       try {
/* 1063 */         setMaxNumberOfLogRecords(Integer.parseInt(temp));
/*      */       } catch (NumberFormatException e) {
/* 1065 */         LogFactor5ErrorDialog error = new LogFactor5ErrorDialog(getBaseFrame(), "'" + temp + "' is an invalid parameter.\nPlease try again.");
/*      */ 
/* 1068 */         setMaxRecordConfiguration();
/*      */       }
/*      */   }
/*      */ 
/*      */   protected JMenu createHelpMenu()
/*      */   {
/* 1075 */     JMenu helpMenu = new JMenu("Help");
/* 1076 */     helpMenu.setMnemonic('h');
/* 1077 */     helpMenu.add(createHelpProperties());
/* 1078 */     return helpMenu;
/*      */   }
/*      */ 
/*      */   protected JMenuItem createHelpProperties() {
/* 1082 */     String title = "LogFactor5 Properties";
/* 1083 */     JMenuItem result = new JMenuItem("LogFactor5 Properties");
/* 1084 */     result.setMnemonic('l');
/* 1085 */     result.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/* 1087 */         LogBrokerMonitor.this.showPropertiesDialog("LogFactor5 Properties");
/*      */       }
/*      */     });
/* 1090 */     return result;
/*      */   }
/*      */ 
/*      */   protected void showPropertiesDialog(String title) {
/* 1094 */     JOptionPane.showMessageDialog(this._logMonitorFrame, this._displayedLogBrokerProperties.toArray(), title, -1);
/*      */   }
/*      */ 
/*      */   protected JMenu createEditMenu()
/*      */   {
/* 1103 */     JMenu editMenu = new JMenu("Edit");
/* 1104 */     editMenu.setMnemonic('e');
/* 1105 */     editMenu.add(createEditFindMI());
/* 1106 */     editMenu.add(createEditFindNextMI());
/* 1107 */     editMenu.addSeparator();
/* 1108 */     editMenu.add(createEditSortNDCMI());
/* 1109 */     editMenu.add(createEditRestoreAllNDCMI());
/* 1110 */     return editMenu;
/*      */   }
/*      */ 
/*      */   protected JMenuItem createEditFindNextMI() {
/* 1114 */     JMenuItem editFindNextMI = new JMenuItem("Find Next");
/* 1115 */     editFindNextMI.setMnemonic('n');
/* 1116 */     editFindNextMI.setAccelerator(KeyStroke.getKeyStroke("F3"));
/* 1117 */     editFindNextMI.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/* 1119 */         LogBrokerMonitor.this.findSearchText();
/*      */       }
/*      */     });
/* 1122 */     return editFindNextMI;
/*      */   }
/*      */ 
/*      */   protected JMenuItem createEditFindMI() {
/* 1126 */     JMenuItem editFindMI = new JMenuItem("Find");
/* 1127 */     editFindMI.setMnemonic('f');
/* 1128 */     editFindMI.setAccelerator(KeyStroke.getKeyStroke("control F"));
/*      */ 
/* 1130 */     editFindMI.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e) {
/* 1133 */         String inputValue = JOptionPane.showInputDialog(LogBrokerMonitor.this._logMonitorFrame, "Find text: ", "Search Record Messages", 3);
/*      */ 
/* 1140 */         LogBrokerMonitor.this.setSearchText(inputValue);
/* 1141 */         LogBrokerMonitor.this.findSearchText();
/*      */       }
/*      */     });
/* 1146 */     return editFindMI;
/*      */   }
/*      */ 
/*      */   protected JMenuItem createEditSortNDCMI()
/*      */   {
/* 1153 */     JMenuItem editSortNDCMI = new JMenuItem("Sort by NDC");
/* 1154 */     editSortNDCMI.setMnemonic('s');
/* 1155 */     editSortNDCMI.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e) {
/* 1158 */         String inputValue = JOptionPane.showInputDialog(LogBrokerMonitor.this._logMonitorFrame, "Sort by this NDC: ", "Sort Log Records by NDC", 3);
/*      */ 
/* 1165 */         LogBrokerMonitor.this.setNDCTextFilter(inputValue);
/* 1166 */         LogBrokerMonitor.this.sortByNDC();
/* 1167 */         LogBrokerMonitor.this._table.getFilteredLogTableModel().refresh();
/* 1168 */         LogBrokerMonitor.this.updateStatusLabel();
/*      */       }
/*      */     });
/* 1173 */     return editSortNDCMI;
/*      */   }
/*      */ 
/*      */   protected JMenuItem createEditRestoreAllNDCMI()
/*      */   {
/* 1179 */     JMenuItem editRestoreAllNDCMI = new JMenuItem("Restore all NDCs");
/* 1180 */     editRestoreAllNDCMI.setMnemonic('r');
/* 1181 */     editRestoreAllNDCMI.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e) {
/* 1184 */         LogBrokerMonitor.this._table.getFilteredLogTableModel().setLogRecordFilter(LogBrokerMonitor.this.createLogRecordFilter());
/*      */ 
/* 1186 */         LogBrokerMonitor.this.setNDCTextFilter("");
/* 1187 */         LogBrokerMonitor.this._table.getFilteredLogTableModel().refresh();
/* 1188 */         LogBrokerMonitor.this.updateStatusLabel();
/*      */       }
/*      */     });
/* 1192 */     return editRestoreAllNDCMI;
/*      */   }
/*      */ 
/*      */   protected JToolBar createToolBar() {
/* 1196 */     JToolBar tb = new JToolBar();
/* 1197 */     tb.putClientProperty("JToolBar.isRollover", Boolean.TRUE);
/* 1198 */     JComboBox fontCombo = new JComboBox();
/* 1199 */     JComboBox fontSizeCombo = new JComboBox();
/* 1200 */     this._fontSizeCombo = fontSizeCombo;
/*      */ 
/* 1202 */     ClassLoader cl = getClass().getClassLoader();
/* 1203 */     if (cl == null) {
/* 1204 */       cl = ClassLoader.getSystemClassLoader();
/*      */     }
/* 1206 */     URL newIconURL = cl.getResource("org/apache/log4j/lf5/viewer/images/channelexplorer_new.gif");
/*      */ 
/* 1209 */     ImageIcon newIcon = null;
/*      */ 
/* 1211 */     if (newIconURL != null) {
/* 1212 */       newIcon = new ImageIcon(newIconURL);
/*      */     }
/*      */ 
/* 1215 */     JButton newButton = new JButton("Clear Log Table");
/*      */ 
/* 1217 */     if (newIcon != null) {
/* 1218 */       newButton.setIcon(newIcon);
/*      */     }
/*      */ 
/* 1221 */     newButton.setToolTipText("Clear Log Table.");
/*      */ 
/* 1224 */     newButton.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e) {
/* 1227 */         LogBrokerMonitor.this._table.clearLogRecords();
/* 1228 */         LogBrokerMonitor.this._categoryExplorerTree.getExplorerModel().resetAllNodeCounts();
/* 1229 */         LogBrokerMonitor.this.updateStatusLabel();
/* 1230 */         LogBrokerMonitor.this.clearDetailTextArea();
/* 1231 */         LogRecord.resetSequenceNumber();
/*      */       }
/*      */     });
/* 1236 */     Toolkit tk = Toolkit.getDefaultToolkit();
/*      */     String[] fonts;
/* 1241 */     if (this._loadSystemFonts) {
/* 1242 */       fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
/*      */     }
/*      */     else {
/* 1245 */       fonts = tk.getFontList();
/*      */     }
/*      */ 
/* 1248 */     for (int j = 0; j < fonts.length; j++) {
/* 1249 */       fontCombo.addItem(fonts[j]);
/*      */     }
/*      */ 
/* 1252 */     fontCombo.setSelectedItem(this._fontName);
/*      */ 
/* 1254 */     fontCombo.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 1258 */         JComboBox box = (JComboBox)e.getSource();
/* 1259 */         String font = (String)box.getSelectedItem();
/* 1260 */         LogBrokerMonitor.this._table.setFont(new Font(font, 0, LogBrokerMonitor.this._fontSize));
/* 1261 */         LogBrokerMonitor.this._fontName = font;
/*      */       }
/*      */     });
/* 1266 */     fontSizeCombo.addItem("8");
/* 1267 */     fontSizeCombo.addItem("9");
/* 1268 */     fontSizeCombo.addItem("10");
/* 1269 */     fontSizeCombo.addItem("12");
/* 1270 */     fontSizeCombo.addItem("14");
/* 1271 */     fontSizeCombo.addItem("16");
/* 1272 */     fontSizeCombo.addItem("18");
/* 1273 */     fontSizeCombo.addItem("24");
/*      */ 
/* 1275 */     fontSizeCombo.setSelectedItem(String.valueOf(this._fontSize));
/* 1276 */     fontSizeCombo.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e) {
/* 1279 */         JComboBox box = (JComboBox)e.getSource();
/* 1280 */         String size = (String)box.getSelectedItem();
/* 1281 */         int s = Integer.valueOf(size).intValue();
/*      */ 
/* 1283 */         LogBrokerMonitor.this.setFontSizeSilently(s);
/* 1284 */         LogBrokerMonitor.this.refreshDetailTextArea();
/* 1285 */         LogBrokerMonitor.this._fontSize = s;
/*      */       }
/*      */     });
/* 1290 */     tb.add(new JLabel(" Font: "));
/* 1291 */     tb.add(fontCombo);
/* 1292 */     tb.add(fontSizeCombo);
/* 1293 */     tb.addSeparator();
/* 1294 */     tb.addSeparator();
/* 1295 */     tb.add(newButton);
/*      */ 
/* 1297 */     newButton.setAlignmentY(0.5F);
/* 1298 */     newButton.setAlignmentX(0.5F);
/*      */ 
/* 1300 */     fontCombo.setMaximumSize(fontCombo.getPreferredSize());
/* 1301 */     fontSizeCombo.setMaximumSize(fontSizeCombo.getPreferredSize());
/*      */ 
/* 1304 */     return tb;
/*      */   }
/*      */ 
/*      */   protected void setView(String viewString, LogTable table)
/*      */   {
/* 1322 */     if ("Detailed".equals(viewString)) {
/* 1323 */       table.setDetailedView();
/*      */     } else {
/* 1325 */       String message = viewString + "does not match a supported view.";
/* 1326 */       throw new IllegalArgumentException(message);
/*      */     }
/* 1328 */     this._currentView = viewString;
/*      */   }
/*      */ 
/*      */   protected JComboBox createLogLevelCombo() {
/* 1332 */     JComboBox result = new JComboBox();
/* 1333 */     Iterator levels = getLogLevels();
/* 1334 */     while (levels.hasNext()) {
/* 1335 */       result.addItem(levels.next());
/*      */     }
/* 1337 */     result.setSelectedItem(this._leastSevereDisplayedLogLevel);
/*      */ 
/* 1339 */     result.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/* 1341 */         JComboBox box = (JComboBox)e.getSource();
/* 1342 */         LogLevel level = (LogLevel)box.getSelectedItem();
/* 1343 */         LogBrokerMonitor.this.setLeastSevereDisplayedLogLevel(level);
/*      */       }
/*      */     });
/* 1346 */     result.setMaximumSize(result.getPreferredSize());
/* 1347 */     return result;
/*      */   }
/*      */ 
/*      */   protected void setLeastSevereDisplayedLogLevel(LogLevel level) {
/* 1351 */     if ((level == null) || (this._leastSevereDisplayedLogLevel == level)) {
/* 1352 */       return;
/*      */     }
/* 1354 */     this._leastSevereDisplayedLogLevel = level;
/* 1355 */     this._table.getFilteredLogTableModel().refresh();
/* 1356 */     updateStatusLabel();
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   protected void trackTableScrollPane()
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void centerFrame(JFrame frame)
/*      */   {
/* 1374 */     Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
/* 1375 */     Dimension comp = frame.getSize();
/*      */ 
/* 1377 */     frame.setLocation((screen.width - comp.width) / 2, (screen.height - comp.height) / 2);
/*      */   }
/*      */ 
/*      */   protected void requestOpen()
/*      */   {
/*      */     JFileChooser chooser;
/* 1389 */     if (this._fileLocation == null)
/* 1390 */       chooser = new JFileChooser();
/*      */     else {
/* 1392 */       chooser = new JFileChooser(this._fileLocation);
/*      */     }
/*      */ 
/* 1395 */     int returnVal = chooser.showOpenDialog(this._logMonitorFrame);
/* 1396 */     if (returnVal == 0) {
/* 1397 */       File f = chooser.getSelectedFile();
/* 1398 */       if (loadLogFile(f)) {
/* 1399 */         this._fileLocation = chooser.getSelectedFile();
/* 1400 */         this._mruFileManager.set(f);
/* 1401 */         updateMRUList();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void requestOpenURL()
/*      */   {
/* 1411 */     LogFactor5InputDialog inputDialog = new LogFactor5InputDialog(getBaseFrame(), "Open URL", "URL:");
/*      */ 
/* 1413 */     String temp = inputDialog.getText();
/*      */     LogFactor5ErrorDialog error;
/* 1415 */     if (temp != null) {
/* 1416 */       if (temp.indexOf("://") == -1) {
/* 1417 */         temp = "http://" + temp;
/*      */       }
/*      */       try
/*      */       {
/* 1421 */         URL url = new URL(temp);
/* 1422 */         if (loadLogFile(url)) {
/* 1423 */           this._mruFileManager.set(url);
/* 1424 */           updateMRUList();
/*      */         }
/*      */       } catch (MalformedURLException e) {
/* 1427 */         error = new LogFactor5ErrorDialog(getBaseFrame(), "Error reading URL.");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void updateMRUList()
/*      */   {
/* 1438 */     JMenu menu = this._logMonitorFrame.getJMenuBar().getMenu(0);
/* 1439 */     menu.removeAll();
/* 1440 */     menu.add(createOpenMI());
/* 1441 */     menu.add(createOpenURLMI());
/* 1442 */     menu.addSeparator();
/* 1443 */     menu.add(createCloseMI());
/* 1444 */     createMRUFileListMI(menu);
/* 1445 */     menu.addSeparator();
/* 1446 */     menu.add(createExitMI());
/*      */   }
/*      */ 
/*      */   protected void requestClose() {
/* 1450 */     setCallSystemExitOnClose(false);
/* 1451 */     closeAfterConfirm(); } 
/* 1458 */   protected void requestOpenMRU(ActionEvent e) { String file = e.getActionCommand();
/* 1459 */     StringTokenizer st = new StringTokenizer(file);
/* 1460 */     String num = st.nextToken().trim();
/* 1461 */     file = st.nextToken("\n");
/*      */     LogFactor5ErrorDialog error;
/*      */     try { int index = Integer.parseInt(num) - 1;
/*      */ 
/* 1466 */       InputStream in = this._mruFileManager.getInputStream(index);
/* 1467 */       LogFileParser lfp = new LogFileParser(in);
/* 1468 */       lfp.parse(this);
/*      */ 
/* 1470 */       this._mruFileManager.moveToTop(index);
/* 1471 */       updateMRUList();
/*      */     } catch (Exception me)
/*      */     {
/* 1474 */       error = new LogFactor5ErrorDialog(getBaseFrame(), "Unable to load file " + file);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void requestExit()
/*      */   {
/* 1481 */     this._mruFileManager.save();
/* 1482 */     setCallSystemExitOnClose(true);
/* 1483 */     closeAfterConfirm();
/*      */   }
/*      */ 
/*      */   protected void closeAfterConfirm() {
/* 1487 */     StringBuffer message = new StringBuffer();
/*      */ 
/* 1489 */     if (!this._callSystemExitOnClose) {
/* 1490 */       message.append("Are you sure you want to close the logging ");
/* 1491 */       message.append("console?\n");
/* 1492 */       message.append("(Note: This will not shut down the Virtual Machine,\n");
/* 1493 */       message.append("or the Swing event thread.)");
/*      */     } else {
/* 1495 */       message.append("Are you sure you want to exit?\n");
/* 1496 */       message.append("This will shut down the Virtual Machine.\n");
/*      */     }
/*      */ 
/* 1499 */     String title = "Are you sure you want to dispose of the Logging Console?";
/*      */ 
/* 1502 */     if (this._callSystemExitOnClose == true) {
/* 1503 */       title = "Are you sure you want to exit?";
/*      */     }
/* 1505 */     int value = JOptionPane.showConfirmDialog(this._logMonitorFrame, message.toString(), title, 2, 3, null);
/*      */ 
/* 1514 */     if (value == 0)
/* 1515 */       dispose();
/*      */   }
/*      */ 
/*      */   protected Iterator getLogLevels()
/*      */   {
/* 1520 */     return this._levels.iterator();
/*      */   }
/*      */ 
/*      */   protected Iterator getLogTableColumns() {
/* 1524 */     return this._columns.iterator();
/*      */   }
/*      */   protected boolean loadLogFile(File file) {
/* 1531 */     boolean ok = false;
/*      */     LogFactor5ErrorDialog error;
/*      */     try { LogFileParser lfp = new LogFileParser(file);
/* 1534 */       lfp.parse(this);
/* 1535 */       ok = true;
/*      */     } catch (IOException e) {
/* 1537 */       error = new LogFactor5ErrorDialog(getBaseFrame(), "Error reading " + file.getName());
/*      */     }
/*      */ 
/* 1541 */     return ok;
/*      */   }
/*      */   protected boolean loadLogFile(URL url) {
/* 1548 */     boolean ok = false;
/*      */     LogFactor5ErrorDialog error;
/*      */     try { LogFileParser lfp = new LogFileParser(url.openStream());
/* 1551 */       lfp.parse(this);
/* 1552 */       ok = true;
/*      */     } catch (IOException e) {
/* 1554 */       error = new LogFactor5ErrorDialog(getBaseFrame(), "Error reading URL:" + url.getFile());
/*      */     }
/*      */ 
/* 1557 */     return ok;
/*      */   }
/*      */ 
/*      */   class LogBrokerMonitorWindowAdaptor extends WindowAdapter
/*      */   {
/*      */     protected LogBrokerMonitor _monitor;
/*      */ 
/*      */     public LogBrokerMonitorWindowAdaptor(LogBrokerMonitor monitor)
/*      */     {
/* 1571 */       this._monitor = monitor;
/*      */     }
/*      */ 
/*      */     public void windowClosing(WindowEvent ev) {
/* 1575 */       this._monitor.requestClose();
/*      */     }
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.LogBrokerMonitor
 * JD-Core Version:    0.6.0
 */
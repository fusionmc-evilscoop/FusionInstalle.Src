/*     */ package mc.now.ui;
/*     */ 
/*     */ import com.jidesoft.swing.CheckBoxTree;
/*     */ import com.jidesoft.swing.CheckBoxTreeSelectionModel;
/*     */ import java.awt.Desktop;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JEditorPane;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JProgressBar;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.SwingWorker;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.event.HyperlinkEvent;
/*     */ import javax.swing.event.HyperlinkEvent.EventType;
/*     */ import javax.swing.event.HyperlinkListener;
/*     */ import javax.swing.event.TreeSelectionEvent;
/*     */ import javax.swing.event.TreeSelectionListener;
/*     */ import javax.swing.tree.DefaultMutableTreeNode;
/*     */ import javax.swing.tree.TreePath;
/*     */ import javax.swing.tree.TreeSelectionModel;
/*     */ import mc.now.util.InstallScript;
/*     */ import mc.now.util.InstallerProperties;
/*     */ import mc.now.util.PlatformUtil;
/*     */ import net.miginfocom.layout.AC;
/*     */ import net.miginfocom.layout.CC;
/*     */ import net.miginfocom.layout.LC;
/*     */ import net.miginfocom.swing.MigLayout;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class Installer extends JFrame
/*     */   implements ActionListener, TreeSelectionListener, HyperlinkListener
/*     */ {
/*  64 */   private static final Logger LOGGER = Logger.getLogger(Installer.class);
/*     */   private static final String PRESET_NONE = "Minimum Install";
/*     */   private static final String PRESET_ALL = "Full Install";
/*     */   private static final String PRESET_CUSTOM = "Custom";
/*     */   private JButton nextButton;
/*     */   private JButton cancelButton;
/*     */   private JProgressBar progressBar;
/*  74 */   private int step = 0;
/*     */   private JPanel contentPane;
/*     */   private CheckBoxTree modTree;
/*     */   private JComboBox presetDropdown;
/*     */   private JPanel modDescrPane;
/*     */   private DefaultMutableTreeNode modTreeRoot;
/*     */   private JButton targetButton;
/*     */   private Map<String, List<String>> presetMap;
/*     */   private Map<String, DefaultMutableTreeNode> treeNodeMap;
/*     */ 
/*     */   public Installer()
/*     */   {
/*  88 */     super(InstallerProperties.getFrameTitle());
/*  89 */     setDefaultCloseOperation(2);
/*  90 */     setMinimumSize(new Dimension(800, 600));
/*  91 */     init();
/*  92 */     pack();
/*     */   }
/*     */ 
/*     */   private void init() {
/*  96 */     JPanel p = new JPanel();
/*  97 */     p.setLayout(new MigLayout("fill", "[fill,grow|]", "[fill,grow||]"));
/*  98 */     p.add(getMainPane(), new CC().spanX().grow());
/*  99 */     p.add(getProgressBar(), new CC().growX());
/* 100 */     p.add(getCancelButton(), new CC().alignX("right"));
/* 101 */     p.add(getNextButton(), new CC().alignX("right"));
/* 102 */     setContentPane(p);
/*     */   }
/*     */ 
/*     */   private JButton getNextButton() {
/* 106 */     if (this.nextButton == null) {
/* 107 */       this.nextButton = new JButton("Next");
/* 108 */       this.nextButton.addActionListener(this);
/*     */     }
/* 110 */     return this.nextButton;
/*     */   }
/*     */ 
/*     */   private JButton getCancelButton() {
/* 114 */     if (this.cancelButton == null) {
/* 115 */       this.cancelButton = new JButton("Cancel");
/* 116 */       this.cancelButton.addActionListener(this);
/*     */     }
/* 118 */     return this.cancelButton;
/*     */   }
/*     */ 
/*     */   private JProgressBar getProgressBar() {
/* 122 */     if (this.progressBar == null) {
/* 123 */       this.progressBar = new JProgressBar();
/*     */     }
/* 125 */     return this.progressBar;
/*     */   }
/*     */ 
/*     */   private JPanel getMainPane() {
/* 129 */     if (this.contentPane == null) {
/* 130 */       this.contentPane = new JPanel();
/* 131 */       initialPanel(this.contentPane);
/*     */     }
/* 133 */     return this.contentPane;
/*     */   }
/*     */ 
/*     */   protected void initialPanel(JPanel contentPane) {
/* 137 */     JLabel text = new JLabel();
/*     */     try {
/* 139 */       ImageIcon icon = new ImageIcon(ImageIO.read(new FileInputStream(InstallerProperties.getLogoFile())));
/* 140 */       text.setIcon(icon);
/*     */     } catch (IOException e) {
/* 142 */       LOGGER.error("IO error on logo.png", e);
/*     */     }
/* 144 */     StringBuffer textBuffer = new StringBuffer();
/*     */     try {
/* 146 */       BufferedReader r = new BufferedReader(new FileReader(InstallerProperties.getInitTextFile()));
/* 147 */       String line = null;
/* 148 */       while ((line = r.readLine()) != null)
/* 149 */         textBuffer.append(line + "\n");
/*     */     }
/*     */     catch (IOException ioe) {
/* 152 */       LOGGER.error("IO error on logo.png", ioe);
/*     */     }
/* 154 */     text.setText(textBuffer.toString());
/* 155 */     text.setVerticalTextPosition(3);
/* 156 */     text.setHorizontalTextPosition(0);
/* 157 */     contentPane.setLayout(new MigLayout(new LC().fill()));
/* 158 */     contentPane.add(text, new CC().alignX("center").wrap());
/* 159 */     contentPane.add(getTargetButton(), new CC().alignX("center").wrap());
/*     */   }
/*     */ 
/*     */   private JButton getTargetButton() {
/* 163 */     if (this.targetButton == null) {
/* 164 */       this.targetButton = new JButton("Set Target Minecraft Folder...");
/* 165 */       this.targetButton.addActionListener(this);
/*     */     }
/* 167 */     return this.targetButton;
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 172 */     if (e.getSource() == getNextButton()) {
/* 173 */       advanceStep();
/* 174 */     } else if (e.getSource() == getCancelButton()) {
/* 175 */       setVisible(false);
/* 176 */       dispose();
/* 177 */     } else if (e.getSource() == getPresetDropdown()) {
/* 178 */       loadPreset(getPresetDropdown().getSelectedItem().toString());
/* 179 */     } else if (e.getSource() == getTargetButton()) {
/* 180 */       setTargetMinecraftFolder();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setTargetMinecraftFolder() {
/* 185 */     JFileChooser chooser = new JFileChooser();
/* 186 */     chooser.setFileSelectionMode(1);
/* 187 */     chooser.setMultiSelectionEnabled(false);
/* 188 */     int opt = chooser.showOpenDialog(getMainPane());
/* 189 */     if (opt == 0) {
/* 190 */       File dir = chooser.getSelectedFile();
/*     */ 
/* 192 */       String oldDir = PlatformUtil.getMinecraftFolder();
/* 193 */       PlatformUtil.setMinecraftFolder(dir.getAbsolutePath());
/* 194 */       File mcjar = new File(PlatformUtil.getMinecraftJar());
/* 195 */       if (!mcjar.exists()) {
/* 196 */         JOptionPane.showMessageDialog(getMainPane(), "The installer couldn't find a minecraft installation in the specified folder.\nRestoring minecraft folder to " + 
/* 197 */           oldDir, 
/* 198 */           "Error setting target Minecraft installation", 0);
/* 199 */         PlatformUtil.setMinecraftFolder(oldDir);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void valueChanged(TreeSelectionEvent e)
/*     */   {
/* 207 */     if (!e.isAddedPath()) {
/* 208 */       return;
/*     */     }
/* 210 */     TreePath path = e.getPath();
/* 211 */     CheckBoxTree tree = getModTree();
/* 212 */     if (e.getSource() == tree.getSelectionModel()) {
/* 213 */       DefaultMutableTreeNode last = (DefaultMutableTreeNode)path.getLastPathComponent();
/* 214 */       loadModDescription(last.getUserObject().toString());
/* 215 */     } else if (e.getSource() == tree.getCheckBoxTreeSelectionModel()) {
/* 216 */       getPresetDropdown().setSelectedItem("Custom");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void advanceStep() {
/* 221 */     this.step += 1;
/* 222 */     switch (this.step) {
/*     */     case 1:
/* 224 */       String msg = InstallScript.preInstallCheck();
/* 225 */       if (msg != null)
/*     */       {
/* 228 */         msg = msg + "\nThis modpack is meant to be installed on a fresh minecraft.jar and mods folder.\n" + 
/* 229 */           "Do you wish to continue, and attempt to install on top of the current minecraft?";
/* 230 */         int opt = JOptionPane.showConfirmDialog(this, msg, "Installation Warning", 0, 2);
/* 231 */         if (opt == 1) {
/* 232 */           setVisible(false);
/* 233 */           dispose();
/*     */         }
/*     */       }
/* 236 */       File extraFolder = new File(InstallScript.EXTRA_MODS_FOLDER);
/*     */ 
/* 238 */       if (extraFolder.exists()) {
/* 239 */         File[] children = extraFolder.listFiles();
/* 240 */         if (children != null) {
/* 241 */           for (File child : children) {
/* 242 */             if (child.isDirectory()) {
/* 243 */               buildOptionsPane();
/* 244 */               return;
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 249 */         advanceStep();
/*     */       } else {
/* 251 */         advanceStep();
/*     */       }
/* 253 */       return;
/*     */     case 2:
/* 255 */       buildInstallingPane(); return;
/* 256 */     }LOGGER.error("Advanced too far");
/*     */   }
/*     */ 
/*     */   private void buildInstallingPane()
/*     */   {
/* 262 */     getNextButton().setEnabled(false);
/* 263 */     getCancelButton().setEnabled(false);
/* 264 */     getMainPane().removeAll();
/* 265 */     getMainPane().setLayout(new MigLayout(new LC().fill()));
/* 266 */     JTextArea text = new JTextArea();
/* 267 */     text.setEditable(false);
/* 268 */     getMainPane().add(new JScrollPane(text), new CC().grow().spanY().wrap());
/* 269 */     getMainPane().validate();
/* 270 */     getMainPane().repaint();
/* 271 */     SwingWorker worker = new SwingWorker(text)
/*     */     {
/*     */       protected Object doInBackground() throws Exception
/*     */       {
/*     */         try {
/* 276 */           List mods = new ArrayList();
/* 277 */           CheckBoxTreeSelectionModel select = Installer.this.getModTree().getCheckBoxTreeSelectionModel();
/* 278 */           TreePath[] paths = select.getSelectionPaths();
/* 279 */           if ((paths != null) && (paths.length > 0)) {
/* 280 */             for (TreePath path : paths) {
/* 281 */               DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
/* 282 */               String mod = (String)node.getUserObject();
/* 283 */               if (mod == null)
/* 284 */                 for (int i = 0; i < node.getChildCount(); i++) {
/* 285 */                   DefaultMutableTreeNode child = (DefaultMutableTreeNode)node.getChildAt(i);
/* 286 */                   mods.add((String)child.getUserObject());
/*     */                 }
/*     */               else {
/* 289 */                 mods.add(mod);
/*     */               }
/*     */             }
/*     */           }
/* 293 */           InstallScript.guiInstall(mods, this.val$text, Installer.this.getProgressBar());
/*     */         } catch (Exception e) {
/* 295 */           Installer.LOGGER.error("Error while trying to install mods", e);
/* 296 */           JOptionPane.showMessageDialog(Installer.this, "Unexpected error while installing mods:\n" + e.getMessage(), "Error", 0);
/* 297 */           Installer.this.setVisible(false);
/* 298 */           Installer.this.dispose();
/*     */         }
/* 300 */         return null;
/*     */       }
/*     */ 
/*     */       public void done()
/*     */       {
/* 305 */         Installer.this.getNextButton().removeActionListener(Installer.this);
/* 306 */         Installer.this.getNextButton().setText("Done");
/* 307 */         Installer.this.getNextButton().addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 311 */             Installer.this.setVisible(false);
/* 312 */             Installer.this.dispose();
/*     */           }
/*     */         });
/* 315 */         Installer.this.getNextButton().setEnabled(true);
/*     */       }
/*     */     };
/* 320 */     worker.execute();
/*     */   }
/*     */ 
/*     */   private CheckBoxTree getModTree()
/*     */   {
/* 325 */     if (this.modTree == null) {
/* 326 */       this.modTreeRoot = new DefaultMutableTreeNode();
/* 327 */       this.treeNodeMap = new HashMap();
/*     */       try {
/* 329 */         File opt = new File(InstallScript.EXTRA_MODS_FOLDER);
/* 330 */         for (File mod : opt.listFiles()) {
/* 331 */           if (!mod.isDirectory()) {
/*     */             continue;
/*     */           }
/* 334 */           String modName = mod.getName();
/* 335 */           DefaultMutableTreeNode child = new DefaultMutableTreeNode(modName);
/* 336 */           this.treeNodeMap.put(modName, child);
/* 337 */           this.modTreeRoot.add(child);
/*     */         }
/* 339 */         this.modTree = new CheckBoxTree(this.modTreeRoot);
/* 340 */         this.modTree.setRootVisible(false);
/* 341 */         this.modTree.getSelectionModel().addTreeSelectionListener(this);
/* 342 */         this.modTree.getCheckBoxTreeSelectionModel().addTreeSelectionListener(this);
/*     */       } catch (Exception e) {
/* 344 */         LOGGER.error("Error scanning and building optional mod tree.", e);
/* 345 */         JOptionPane.showMessageDialog(this, "Error scanning mods folder. It may not exists.", "Error", 0);
/* 346 */         setVisible(false);
/* 347 */         dispose();
/* 348 */         return null;
/*     */       }
/*     */     }
/* 351 */     return this.modTree;
/*     */   }
/*     */ 
/*     */   private void buildOptionsPane()
/*     */   {
/* 356 */     JPanel p = getMainPane();
/* 357 */     p.removeAll();
/*     */ 
/* 359 */     JPanel leftPane = new JPanel(
/* 361 */       new MigLayout(new LC().fill(), 
/* 360 */       new AC().index(1).fill().grow(), 
/* 361 */       new AC().index(0).fill().grow().index(1).fill().grow()));
/*     */ 
/* 363 */     leftPane.add(new JLabel("Please select which optional mods you would like to install."), new CC().spanX().wrap());
/* 364 */     leftPane.add(new JLabel("Presets: "), new CC());
/* 365 */     leftPane.add(getPresetDropdown(), new CC().grow().wrap());
/* 366 */     leftPane.add(new JScrollPane(getModTree()), new CC().spanX().spanY().grow().wrap());
/*     */ 
/* 368 */     p.setLayout(
/* 370 */       new MigLayout(new LC().fill(), 
/* 369 */       new AC().index(0).fill().grow().index(1).fill().grow(), 
/* 370 */       new AC().fill()));
/* 371 */     p.add(leftPane, new CC().grow().width(":300:"));
/* 372 */     p.add(getModDescriptionPane(), new CC().grow().width(":300:300").wrap());
/*     */ 
/* 374 */     p.validate();
/* 375 */     p.repaint();
/*     */   }
/*     */ 
/*     */   private JComboBox getPresetDropdown() {
/* 379 */     if (this.presetDropdown == null) {
/* 380 */       this.presetDropdown = new JComboBox();
/* 381 */       this.presetDropdown.addItem("Minimum Install");
/* 382 */       this.presetDropdown.addItem("Full Install");
/* 383 */       this.presetDropdown.addItem("Custom");
/* 384 */       this.presetDropdown.addActionListener(this);
/* 385 */       File presetDir = new File(FilenameUtils.concat(InstallerProperties.getInstallerDir(), "config/presets"));
/* 386 */       if ((!presetDir.exists()) || (!presetDir.isDirectory())) {
/* 387 */         LOGGER.warn("presets does not exist or is not a directory");
/*     */       } else {
/* 389 */         this.presetMap = new HashMap();
/* 390 */         File[] children = presetDir.listFiles();
/* 391 */         for (File child : children) {
/* 392 */           if (!child.isFile()) {
/*     */             continue;
/*     */           }
/* 395 */           String name = FilenameUtils.getBaseName(child.getName());
/*     */           try {
/* 397 */             BufferedReader r = new BufferedReader(new FileReader(child));
/* 398 */             String l = null;
/* 399 */             List mods = new ArrayList();
/* 400 */             while ((l = r.readLine()) != null) {
/* 401 */               mods.add(l);
/*     */             }
/* 403 */             this.presetMap.put(name, mods);
/* 404 */             this.presetDropdown.addItem(name);
/*     */           } catch (IOException e) {
/* 406 */             LOGGER.warn("Error reading " + child.getName(), e);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 411 */     return this.presetDropdown;
/*     */   }
/*     */ 
/*     */   private JPanel getModDescriptionPane() {
/* 415 */     if (this.modDescrPane == null) {
/* 416 */       this.modDescrPane = new JPanel();
/* 417 */       this.modDescrPane.add(new JLabel("<html>This is the mod description panel.<br>Select an optional mod to the left<br>to view a description of it.</html>"));
/*     */     }
/*     */ 
/* 421 */     return this.modDescrPane;
/*     */   }
/*     */ 
/*     */   private void loadModDescription(String modName) {
/* 425 */     JPanel p = getModDescriptionPane();
/* 426 */     p.removeAll();
/* 427 */     p.setLayout(new BoxLayout(p, 1));
/*     */ 
/* 429 */     String extras = InstallScript.EXTRA_MODS_FOLDER;
/* 430 */     String modFolderName = FilenameUtils.concat(extras, modName);
/* 431 */     File modFolder = new File(modFolderName);
/* 432 */     if (!modFolder.exists()) {
/* 433 */       LOGGER.error("Mod folder for " + modName + " does not exist.");
/*     */     }
/* 435 */     File descrFile = new File(FilenameUtils.concat(modFolderName, "description.txt"));
/* 436 */     File imgFile = new File(FilenameUtils.concat(modFolderName, "image.png"));
/* 437 */     if ((!descrFile.exists()) && (!imgFile.exists())) {
/* 438 */       p.add(new JLabel("<html>No description for:<br>" + modName + "</html>"));
/*     */     }
/*     */     else {
/* 441 */       if (imgFile.exists()) {
/*     */         try {
/* 443 */           JLabel label = new JLabel();
/* 444 */           BufferedImage img = ImageIO.read(imgFile);
/* 445 */           label.setIcon(new ImageIcon(img));
/* 446 */           p.add(label);
/*     */         } catch (IOException e) {
/* 448 */           LOGGER.error("Error reading image file: " + imgFile.getPath(), e);
/*     */         }
/*     */       }
/* 451 */       if (descrFile.exists()) {
/* 452 */         StringBuffer buffer = new StringBuffer();
/*     */         try {
/* 454 */           BufferedReader r = new BufferedReader(new FileReader(descrFile));
/* 455 */           String l = null;
/* 456 */           while ((l = r.readLine()) != null) {
/* 457 */             buffer.append(l + "\n");
/*     */           }
/* 459 */           r.close();
/* 460 */           JEditorPane area = new JEditorPane();
/* 461 */           area.setContentType("text/html");
/* 462 */           area.setText(buffer.toString());
/* 463 */           area.setEditable(false);
/* 464 */           area.addHyperlinkListener(this);
/* 465 */           p.add(new JScrollPane(area));
/*     */         } catch (IOException e) {
/* 467 */           LOGGER.error("Error reading description file: " + descrFile.getPath(), e);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 472 */     p.validate();
/* 473 */     p.repaint();
/*     */   }
/*     */ 
/*     */   private void loadPreset(String presetName) {
/* 477 */     if (presetName.equals("Custom"))
/* 478 */       return;
/* 479 */     if (presetName.equals("Minimum Install")) {
/* 480 */       getModTree().getCheckBoxTreeSelectionModel().clearSelection();
/* 481 */       return;
/* 482 */     }if (presetName.equals("Full Install")) {
/* 483 */       CheckBoxTreeSelectionModel selectModel = getModTree().getCheckBoxTreeSelectionModel();
/* 484 */       selectModel.setSelectionPath(new TreePath(this.modTreeRoot));
/*     */     } else {
/* 486 */       loadPresetFromFile(presetName);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void loadPresetFromFile(String presetName) {
/* 491 */     List mods = (List)this.presetMap.get(presetName);
/* 492 */     CheckBoxTreeSelectionModel select = getModTree().getCheckBoxTreeSelectionModel();
/* 493 */     select.clearSelection();
/* 494 */     TreePath[] paths = new TreePath[mods.size()];
/* 495 */     for (int i = 0; i < mods.size(); i++) {
/* 496 */       String mod = (String)mods.get(i);
/* 497 */       DefaultMutableTreeNode modNode = (DefaultMutableTreeNode)this.treeNodeMap.get(mod);
/* 498 */       paths[i] = new TreePath(new Object[] { this.modTreeRoot, modNode });
/*     */     }
/* 500 */     select.setSelectionPaths(paths);
/* 501 */     this.presetDropdown.setSelectedItem(presetName);
/*     */   }
/*     */ 
/*     */   public static boolean sanityCheck()
/*     */   {
/* 506 */     File mcFolder = new File(PlatformUtil.getMinecraftFolder());
/* 507 */     String errmsg = "";
/* 508 */     if (!mcFolder.exists()) {
/* 509 */       errmsg = errmsg + PlatformUtil.getMinecraftFolder() + " doesn't exist.\n";
/*     */     }
/* 511 */     File modsFolder = new File(InstallScript.MODS_FOLDER);
/* 512 */     if (!modsFolder.exists()) {
/* 513 */       errmsg = errmsg + InstallScript.MODS_FOLDER + " doesn't exist.\n";
/*     */     }
/* 515 */     File logofile = new File(InstallerProperties.getInitTextFile());
/* 516 */     if (!logofile.exists()) {
/* 517 */       errmsg = errmsg + InstallerProperties.getInitTextFile() + " doesn't exist.\n";
/*     */     }
/* 519 */     File textfile = new File(InstallerProperties.getLogoFile());
/* 520 */     if (!textfile.exists()) {
/* 521 */       errmsg = errmsg + InstallerProperties.getLogoFile() + " doesn't exist.\n";
/*     */     }
/*     */ 
/* 524 */     errmsg = errmsg.trim();
/* 525 */     if (!errmsg.isEmpty()) {
/* 526 */       LOGGER.error(errmsg);
/* 527 */       JOptionPane.showMessageDialog(null, errmsg, "Installer Error", 0);
/*     */     }
/* 529 */     return errmsg.isEmpty();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) throws IOException
/*     */   {
/* 534 */     LOGGER.debug("Starting Modpack Installer");
/* 535 */     LOGGER.debug("Current OS: " + PlatformUtil.currentOS);
/*     */ 
/* 537 */     if (!sanityCheck()) {
/* 538 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 542 */       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/*     */     } catch (Exception e) {
/* 544 */       LOGGER.warn("Couldn't set the look and feel", e);
/*     */     }
/*     */ 
/* 547 */     Installer installer = new Installer();
/* 548 */     installer.setVisible(true);
/*     */   }
/*     */ 
/*     */   public void hyperlinkUpdate(HyperlinkEvent e)
/*     */   {
/* 553 */     if (e.getEventType() != HyperlinkEvent.EventType.ACTIVATED) {
/* 554 */       return;
/*     */     }
/* 556 */     SwingWorker worker = new SwingWorker(e)
/*     */     {
/*     */       protected Object doInBackground() throws Exception
/*     */       {
/*     */         try {
/* 561 */           String url = this.val$e.getURL().toExternalForm();
/* 562 */           Desktop.getDesktop().browse(URI.create(url));
/*     */         } catch (Exception e) {
/* 564 */           e.printStackTrace();
/*     */         }
/* 566 */         return null;
/*     */       }
/*     */     };
/* 570 */     worker.execute();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     mc.now.ui.Installer
 * JD-Core Version:    0.6.0
 */
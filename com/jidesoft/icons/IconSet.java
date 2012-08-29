/*     */ package com.jidesoft.icons;
/*     */ 
/*     */ public class IconSet
/*     */ {
/*     */   private String _iconSetName;
/*     */   private int[] _availableSizes;
/*     */   private String _packageName;
/*     */ 
/*     */   public IconSet(String iconSetName, int[] availableSizes, String packageName)
/*     */   {
/* 202 */     this._iconSetName = iconSetName;
/* 203 */     this._availableSizes = availableSizes;
/* 204 */     this._packageName = packageName;
/*     */   }
/*     */ 
/*     */   public String getIconSetName()
/*     */   {
/* 213 */     return this._iconSetName;
/*     */   }
/*     */ 
/*     */   public int getNextAvailableSize(int size)
/*     */   {
/* 227 */     int[] sizes = getAvailableSizes();
/* 228 */     int nextSize = sizes[(sizes.length - 1)];
/* 229 */     for (int i : sizes) {
/* 230 */       if (i == size) {
/* 231 */         return i;
/*     */       }
/* 233 */       if ((i > size) && (i < nextSize)) {
/* 234 */         nextSize = i;
/*     */       }
/*     */     }
/* 237 */     return nextSize;
/*     */   }
/*     */ 
/*     */   public int[] getAvailableSizes()
/*     */   {
/* 246 */     return this._availableSizes;
/*     */   }
/*     */ 
/*     */   public String getPackageName()
/*     */   {
/* 255 */     return this._packageName;
/*     */   }
/*     */ 
/*     */   public static class Movement
/*     */   {
/*     */     public static final String MOVE_LEFT = "Movement-MoveLeft.png";
/*     */     public static final String MOVE_RIGHT = "Movement-MoveRight.png";
/*     */     public static final String MOVE_UP = "Movement-MoveUp.png";
/*     */     public static final String MOVE_DOWN = "Movement-MoveDown.png";
/*     */     public static final String MOVE_TO_TOP = "Movement-MoveToTop.png";
/*     */     public static final String MOVE_TO_BOTTOM = "Movement-MoveToBottom.png";
/*     */     public static final String MOVE_TO_BEGINNING = "Movement-MoveToBeginning.png";
/*     */     public static final String MOVE_TO_END = "Movement-MoveToEnd.png";
/*     */   }
/*     */ 
/*     */   public static class MediaControl
/*     */   {
/*     */     public static final String BEGINNING = "MediaControl-Beginning.png";
/*     */     public static final String END = "MediaControl-End.png";
/*     */     public static final String FAST_FORWARD = "MediaControl-FastForward.png";
/*     */     public static final String PASUE = "MediaControl-Pasue.png";
/*     */     public static final String PLAY = "MediaControl-Play.png";
/*     */     public static final String REWIND = "MediaControl-Rewind.png";
/*     */     public static final String STEP_BACK = "MediaControl-StepBack.png";
/*     */     public static final String STEP_FORWARD = "MediaControl-StepForward.png";
/*     */     public static final String STOP = "MediaControl-Stop.png";
/*     */     public static final String RECORD = "MediaControl-Record.png";
/*     */     public static final String EJECT = "MediaControl-Eject.png";
/*     */     public static final String SHUTDOWN = "MediaControl-Shutdown.png";
/*     */     public static final String STANDBY = "MediaControl-Standby.png";
/*     */   }
/*     */ 
/*     */   public static class Direction
/*     */   {
/*     */     public static final String LEFT = "Direction-Left.png";
/*     */     public static final String RIGHT = "Direction-Right.png";
/*     */     public static final String UP = "Direction-Up.png";
/*     */     public static final String DOWN = "Direction-Down.png";
/*     */     public static final String TOP = "Direction-Top.png";
/*     */     public static final String BOTTOM = "Direction-Bottom.png";
/*     */     public static final String FIRST = "Direction-First.png";
/*     */     public static final String LAST = "Direction-Last.png";
/*     */     public static final String BOTTOM_LEFT = "Direction-BottomLeft.png";
/*     */     public static final String BOTTOM_RIGHT = "Direction-BottomRight.png";
/*     */     public static final String TOP_LEFT = "Direction-TopLeft.png";
/*     */     public static final String TOP_RIGHT = "Direction-TopRight.png";
/*     */   }
/*     */ 
/*     */   public static class Hardware
/*     */   {
/*     */     public static final String NETWORK = "Hardware-Network.png";
/*     */     public static final String COMPUTER = "Hardware-Computer.png";
/*     */     public static final String SERVER = "Hardware-Server.png";
/*     */     public static final String HARD_DRIVE = "Hardware-HardDrive.png";
/*     */     public static final String MONITOR = "Hardware-Monitor.png";
/*     */     public static final String FLOPPY = "Hardware-Floppy.png";
/*     */     public static final String MOUSE = "Hardware-Mouse.png";
/*     */     public static final String KEYBOARD = "Hardware-Keyboard.png";
/*     */     public static final String CLOCK = "Hardware-Clock.png";
/*     */     public static final String CALCULATOR = "Hardware-Calculator.png";
/*     */     public static final String CAMERA = "Hardware-Camera.png";
/*     */     public static final String PRINTER = "Hardware-Printer.png";
/*     */     public static final String SCANNER = "Hardware-Scanner.png";
/*     */     public static final String PHONE = "Hardware-Phone.png";
/*     */     public static final String SPEAKER = "Hardware-Speaker.png";
/*     */     public static final String CD = "Hardware-CD.png";
/*     */     public static final String GAME_CONTROLLER = "Hardware-GameController.png";
/*     */     public static final String PDA = "Hardware-PDA.png";
/*     */   }
/*     */ 
/*     */   public static class Software
/*     */   {
/*     */     public static final String FILE = "Software-File.png";
/*     */     public static final String DOCUMENT = "Software-Document.png";
/*     */     public static final String DATABASE = "Software-Database.png";
/*     */     public static final String INTERNET = "Software-Internet.png";
/*     */     public static final String TEXT = "Software-Text.png";
/*     */     public static final String HOME = "Software-Home.png";
/*     */     public static final String TRASH = "Software-Trash.png";
/*     */     public static final String TRASH_FULL = "Software-TrashFull.png";
/*     */     public static final String USER = "Software-User.png";
/*     */     public static final String BOOKMARK = "Software-Bookmark.png";
/*     */     public static final String FOLDER = "Software-Folder.png";
/*     */     public static final String PICTURE = "Software-Picture.png";
/*     */     public static final String FRAME = "Software-Frame.png";
/*     */     public static final String MAIL = "Software-Mail.png";
/*     */     public static final String APPLICATION = "Software-Application.png";
/*     */     public static final String REPORT = "Software-Report.png";
/*     */     public static final String CHART = "Software-Chart.png";
/*     */     public static final String CALENDAR = "Software-Calendar.png";
/*     */   }
/*     */ 
/*     */   public static class Overlay
/*     */   {
/*     */     public static final String ADD = "Overlay-Add.png";
/*     */     public static final String REMOVE = "Overlay-Remove.png";
/*     */     public static final String DELETE = "Overlay-Delete.png";
/*     */     public static final String NEW = "Overlay-New.png";
/*     */     public static final String DIRTY = "Overlay-Dirty.png";
/*     */     public static final String WARNING = "Overlay-Warning.png";
/*     */     public static final String ERROR = "Overlay-Error.png";
/*     */     public static final String VALID = "Overlay-Valid.png";
/*     */     public static final String INFO = "Overlay-Info.png";
/*     */     public static final String QUESTION = "Overlay-Question.png";
/*     */     public static final String LOCK = "Overlay-Lock.png";
/*     */     public static final String UNLOCK = "Overlay-Unlock.png";
/*     */     public static final String FLAG = "Overlay-Flag.png";
/*     */     public static final String SHARE = "Overlay-Share.png";
/*     */     public static final String EDIT = "Overlay-Edit.png";
/*     */   }
/*     */ 
/*     */   public static class Help
/*     */   {
/*     */     public static final String HELP = "Help-Help.png";
/*     */     public static final String CONTENT = "Help-Content.png";
/*     */     public static final String INDEX = "Help-Index.png";
/*     */   }
/*     */ 
/*     */   public static class View
/*     */   {
/*     */     public static final String ZOOM = "View-Zoom.png";
/*     */     public static final String ZOOM_IN = "View-ZoomIn.png";
/*     */     public static final String ZOOM_OUT = "View-ZoomOut.png";
/*     */     public static final String FIT_SIZE = "View-FitSize.png";
/*     */     public static final String ACTUAL_SIZE = "View-ActualSize.png";
/*     */     public static final String VIEW_FULL_SCREEN = "View-ViewFullScreen.png";
/*     */     public static final String LEAVE_FULL_SCREEN = "View-LeaveFullScreen.png";
/*     */     public static final String SORT_ASCENDING = "View-SortAscending.png";
/*     */     public static final String SORT_DESCENDING = "View-SortDescending.png";
/*     */     public static final String TREE_EXPAND = "View-TreeExpand.png";
/*     */     public static final String TREE_COLLAPSE = "View-TreeCollapse.png";
/*     */     public static final String TREE_EXPAND_ALL = "View-TreeExpandAll.png";
/*     */     public static final String TREE_COLLAPSE_ALL = "View-TreeCollapseAll.png";
/*     */     public static final String EXPAND = "View-Expand.png";
/*     */     public static final String COLLAPSE = "View-Collapse.png";
/*     */     public static final String TILT_HORIZONTALLY = "View-TiltHorizontally.png";
/*     */     public static final String TILT_VERTICALLY = "View-TiltVertically.png";
/*     */     public static final String CASCADE_WINDOW = "View-CascadeWindow.png";
/*     */   }
/*     */ 
/*     */   public static class Text
/*     */   {
/*     */     public static final String ALIGN_CENTER = "Text-AlignCenter.png";
/*     */     public static final String ALIGN_RIGHT = "Text-AlignRight.png";
/*     */     public static final String ALIGH_LEFT = "Text-AlighLeft.png";
/*     */     public static final String JUSTIFIED = "Text-Justified.png";
/*     */     public static final String BOLD = "Text-Bold.png";
/*     */     public static final String ITALIC = "Text-Italic.png";
/*     */     public static final String UNDERLINED = "Text-Underlined.png";
/*     */     public static final String SUPERSCRIPT = "Text-Superscript.png";
/*     */     public static final String SUBSCRIPT = "Text-Subscript.png";
/*     */     public static final String STRIKETHROUGH = "Text-Strikethrough.png";
/*     */   }
/*     */ 
/*     */   public static class FindReplace
/*     */   {
/*     */     public static final String FIND = "FindReplace-Find.png";
/*     */     public static final String FIND_AGAIN = "FindReplace-FindAgain.png";
/*     */     public static final String FIND_NEXT = "FindReplace-FindNext.png";
/*     */     public static final String FIND_PREVIOUS = "FindReplace-FindPrevious.png";
/*     */     public static final String REPLACE = "FindReplace-Replace.png";
/*     */   }
/*     */ 
/*     */   public static class Edit
/*     */   {
/*     */     public static final String CUT = "Edit-Cut.png";
/*     */     public static final String COPY = "Edit-Copy.png";
/*     */     public static final String PASTE = "Edit-Paste.png";
/*     */     public static final String DELETE = "Edit-Delete.png";
/*     */     public static final String UNDO = "Edit-Undo.png";
/*     */     public static final String REDO = "Edit-Redo.png";
/*     */     public static final String REFRESH = "Edit-Refresh.png";
/*     */     public static final String HISTORY = "Edit-History.png";
/*     */   }
/*     */ 
/*     */   public static class File
/*     */   {
/*     */     public static final String NEW = "File-New.png";
/*     */     public static final String OPEN = "File-Open.png";
/*     */     public static final String SAVE = "File-Save.png";
/*     */     public static final String SAVE_AS = "File-SaveAs.png";
/*     */     public static final String SAVE_ALL = "File-SaveAll.png";
/*     */     public static final String IMPORT = "File-Import.png";
/*     */     public static final String EXPORT = "File-Export.png";
/*     */     public static final String PRINT = "File-Print.png";
/*     */     public static final String PRINT_PREVIEW = "File-PrintPreview.png";
/*     */     public static final String PAGE_SETUP = "File-PageSetup.png";
/*     */     public static final String PROPERTY = "File-Property.png";
/*     */     public static final String EXIT = "File-Exit.png";
/*     */     public static final String CLOSE = "File-Close.png";
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.icons.IconSet
 * JD-Core Version:    0.6.0
 */
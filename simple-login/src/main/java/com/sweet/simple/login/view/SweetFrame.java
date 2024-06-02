package com.sweet.simple.login.view;

import com.formdev.flatlaf.demo.FlatLafDemo;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.FlatSVGUtils;
import com.formdev.flatlaf.extras.FlatUIDefaultsInspector;
import com.formdev.flatlaf.util.FontUtils;
import com.sweet.simple.login.ViewStart;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class SweetFrame extends JFrame {

    /**
     * 应用名称
     */
    private final static String TITLE = "大师兄单机登录器";

    /**
     * 可用字体族名称的数组
     */
    private final String[] availableFontFamilyNames;

    /**
     * 菜单
     */
    private JMenuBar menuBar;

    /**
     * 退出菜单项
     */
    private JMenuItem exitMenuItem;

    /**
     * 工具栏
     */
    private JToolBar toolBar;

    /**
     * 选项卡
     */
    private JTabbedPane tabbedPane;

    // private ControlBar controlBar;

    public SweetFrame() {
        // 从 DemoPrefs 中获取之前保存的选项卡索引，如果没有则默认为0
        int tabIndex = SweetPreferences.getState().getInt(ViewStart.KEY_TAB, 0);
        // 获取可用字名称数组
        availableFontFamilyNames = FontUtils.getAvailableFontFamilyNames().clone();
        // 字体排序
        Arrays.sort(availableFontFamilyNames);
        // 初始化
        initComponents();
        // updateFontMenuItems();
        // initAccentColors();
        // initFullWindowContent();
        // controlBar.initialize(this, tabbedPane);

        setIconImages(FlatSVGUtils.createWindowIconImages("/com/formdev/flatlaf/demo/FlatLaf.svg"));

        // 检查选项卡索引是否在有效范围内，并且不等于当前选定的选项卡索引
        if (tabIndex >= 0 && tabIndex < tabbedPane.getTabCount() && tabIndex != tabbedPane.getSelectedIndex())
            tabbedPane.setSelectedIndex(tabIndex);

        // integrate into macOS screen menu
        /*FlatDesktop.setAboutHandler(this::aboutActionPerformed);
        FlatDesktop.setPreferencesHandler(this::showPreferences);
        FlatDesktop.setQuitHandler(FlatDesktop.QuitResponse::performQuit);
        SwingUtilities.invokeLater(this::showHints);*/
    }

    private void initComponents() {
        // 设置标题
        setTitle(TITLE);
        // 设置窗口关闭时的默认操作为在关闭窗口时终止应用程序
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        // 获取窗口的内容面板
        Container contentPane = getContentPane();
        // 设置内容面板 的布局管理器为边界布局
        contentPane.setLayout(new BorderLayout());
        buildFileMenu();
        buildEditMenu();
        setJMenuBar(menuBar);

        buildToolBarPanel(contentPane);
        buildTabs(contentPane);
    }


    /**
     * 构建【文件】菜单项
     */
    public void buildFileMenu() {
        menuBar = new JMenuBar();
        // 文件菜单项
        JMenu fileMenu = new JMenu();
        fileMenu.setText("文件");
        // 快捷键 Alt + N
        fileMenu.setMnemonic('F');

        // 新建 菜单项
        JMenuItem newMenuItem = new JMenuItem();
        newMenuItem.setText("新建");
        // 快捷键 Ctrl + N
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        newMenuItem.setMnemonic('N');
        // 操作
        newMenuItem.addActionListener(e -> newMenuItemAction());
        fileMenu.add(newMenuItem);

        // 打开菜单项
        JMenuItem openMenuItem = new JMenuItem();
        // 快捷键 Ctrl + O
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        openMenuItem.setMnemonic('O');
        openMenuItem.addActionListener(e -> openMenuItemAction());
        fileMenu.add(openMenuItem);

        // 保存菜单项
        JMenuItem saveAsMenuItem = new JMenuItem();
        saveAsMenuItem.setText("保存");
        // 快捷键 Ctrl + S
        saveAsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        saveAsMenuItem.setMnemonic('S');
        saveAsMenuItem.addActionListener(e -> saveAsMenuItemAction());
        fileMenu.add(saveAsMenuItem);
        // 添加分隔线
        fileMenu.addSeparator();

        // 关闭 菜单项
        JMenuItem closeMenuItem = new JMenuItem();
        closeMenuItem.setText("关闭");
        // 快捷键 Ctrl + C
        closeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        closeMenuItem.setMnemonic('C');
        closeMenuItem.addActionListener(this::closeMenuItemAction);
        fileMenu.add(closeMenuItem);
        fileMenu.addSeparator();

        // 退出 菜单项
        exitMenuItem = new JMenuItem();
        exitMenuItem.setText("退出");
        // 快捷键 Ctrl + X
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        exitMenuItem.setMnemonic('X');
        exitMenuItem.addActionListener(e -> exitMenuItemAction());
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
    }

    /**
     * 构建【编辑】菜单项
     */
    public void buildEditMenu() {
        JMenu editMenu = new JMenu();
        JMenuItem undoMenuItem = new JMenuItem();
        JMenuItem redoMenuItem = new JMenuItem();
        JMenuItem cutMenuItem = new JMenuItem();
        JMenuItem copyMenuItem = new JMenuItem();
        JMenuItem pasteMenuItem = new JMenuItem();
        JMenuItem deleteMenuItem = new JMenuItem();
        editMenu.setText("编辑");
        // 快捷键 Alt + E
        editMenu.setMnemonic('E');

        // 撤销 菜单项
        undoMenuItem.setText("撤销");
        // 快捷键 Ctrl + U
        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        undoMenuItem.setMnemonic('U');
        // 设置图标
        undoMenuItem.setIcon(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/undo.svg"));
        undoMenuItem.addActionListener(e -> undoMenuItemAction(e));
        editMenu.add(undoMenuItem);

        // 重做 菜单项
        redoMenuItem.setText("重做");
        // 快捷键 Ctrl + R
        redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        redoMenuItem.setMnemonic('R');
        redoMenuItem.setIcon(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/redo.svg"));
        redoMenuItem.addActionListener(e -> redoMenuItemAction(e));
        editMenu.add(redoMenuItem);
        editMenu.addSeparator();

        // 剪切 菜单项
        cutMenuItem.setText("剪切");
        // 快捷键 Ctrl + C
        cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        cutMenuItem.setMnemonic('C');
        cutMenuItem.setIcon(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/menu-cut.svg"));
        editMenu.add(cutMenuItem);

        // 复制 菜单项
        copyMenuItem.setText("复制");
        // 快捷键 Ctrl + O
        copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        copyMenuItem.setMnemonic('O');
        copyMenuItem.setIcon(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/copy.svg"));
        editMenu.add(copyMenuItem);

        // 黏贴 菜单项
        pasteMenuItem.setText("黏贴");
        // 快捷键 Ctrl + P
        pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        pasteMenuItem.setMnemonic('P');
        pasteMenuItem.setIcon(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/menu-paste.svg"));
        editMenu.add(pasteMenuItem);
        editMenu.addSeparator();

        // 删除 菜单项
        deleteMenuItem.setText("删除");
        // 快捷键 Ctrl + D
        deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        deleteMenuItem.setMnemonic('D');
        deleteMenuItem.addActionListener(e -> deleteMenuItemAction(e));
        editMenu.add(deleteMenuItem);
        menuBar.add(editMenu);
    }

    /**
     * 构建工具栏
     *
     * @param contentPane 窗口的内容面板
     */
    public void buildToolBarPanel(Container contentPane) {
        toolBar = new JToolBar();
        JPanel toolBarPanel = new JPanel();
        JPanel macFullWindowContentButtonsPlaceholder = new JPanel();
        JButton forwardButton = new JButton();
        JButton cutButton = new JButton();
        JButton copyButton = new JButton();
        JButton pasteButton = new JButton();
        JButton refreshButton = new JButton();
        JButton backButton = new JButton();

        JToggleButton showToggleButton = new JToggleButton();

        toolBarPanel.setLayout(new BorderLayout());
        //======== macFullWindowContentButtonsPlaceholder ========
        macFullWindowContentButtonsPlaceholder.setLayout(new FlowLayout());
        toolBarPanel.add(macFullWindowContentButtonsPlaceholder, BorderLayout.WEST);

        toolBar.setMargin(new Insets(3, 3, 3, 3));

        //---- backButton ----
        backButton.setToolTipText("Back");
        backButton.setIcon(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/back.svg"));
        toolBar.add(backButton);

        //---- forwardButton ----
        forwardButton.setToolTipText("Forward");
        forwardButton.setIcon(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/forward.svg"));
        toolBar.add(forwardButton);
        toolBar.addSeparator();

        //---- cutButton ----
        cutButton.setToolTipText("Cut");
        cutButton.setIcon(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/menu-cut.svg"));
        toolBar.add(cutButton);

        //---- copyButton ----
        copyButton.setToolTipText("Copy");
        copyButton.setIcon(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/copy.svg"));
        toolBar.add(copyButton);

        //---- pasteButton ----
        pasteButton.setToolTipText("Paste");
        pasteButton.setIcon(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/menu-paste.svg"));
        toolBar.add(pasteButton);
        toolBar.addSeparator();

        //---- refreshButton ----
        refreshButton.setToolTipText("Refresh");
        refreshButton.setIcon(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/refresh.svg"));
        toolBar.add(refreshButton);
        toolBar.addSeparator();

        //---- showToggleButton ----
        showToggleButton.setSelected(true);
        showToggleButton.setToolTipText("Show Details");
        showToggleButton.setIcon(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/show.svg"));
        toolBar.add(showToggleButton);

        toolBarPanel.add(toolBar, BorderLayout.CENTER);
        contentPane.add(toolBarPanel, BorderLayout.PAGE_START);
    }

    /**
     * 构建主页选项卡
     *
     * @param contentPane 窗口的内容面板
     */
    public void buildTabs(Container contentPane) {
        tabbedPane = new JTabbedPane();
        JPanel contentPanel = new JPanel();
        LoginComponentsPanel loginComponentsPanel = new LoginComponentsPanel();

        contentPanel.setLayout(new MigLayout("insets dialog,hidemode 3", "[grow,fill]", "[grow,fill]"));
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.addChangeListener(e -> selectedTabChanged());
        tabbedPane.addTab("Basic Components", loginComponentsPanel);
        // tabbedPane.addTab("More Components", moreComponentsPanel);
        // tabbedPane.addTab("Data Components", dataComponentsPanel);
        // tabbedPane.addTab("Tabs", tabsPanel);
        // tabbedPane.addTab("Option Pane", optionPanePanel);
        // tabbedPane.addTab("Extras", extrasPanel);
        contentPanel.add(tabbedPane, "cell 0 0");
        contentPane.add(contentPanel, BorderLayout.CENTER);
        // contentPane.add(controlBar, BorderLayout.PAGE_END);
    }

    /**
     * 在选项卡被改变时更新用户偏好设置中保存的选项卡索引
     * <p>
     * 具体解释如下：
     * SweetPreferences.getState().putInt(FlatLafDemo.KEY_TAB, tabbedPane.getSelectedIndex()); - 使用DemoPrefs类来获取保存应用程序状态的对象，并使用putInt()方法将当前选中的选项卡索引保存到状态中。这里使用了FlatLafDemo类中定义的一个常量KEY_TAB作为键来保存选项卡索引。
     * 通常，此方法将在选项卡的选择发生变化时被调用，以确保用户下次打开应用程序时能够回到上次选择的选项卡。
     */
    private void selectedTabChanged() {
        SweetPreferences.getState().putInt(FlatLafDemo.KEY_TAB, tabbedPane.getSelectedIndex());
    }

    /**
     * 【新建】菜单项操作方法
     */
    private void newMenuItemAction() {

    }

    /**
     * 【打开】菜单项操作方法
     */
    private void openMenuItemAction() {

    }

    /**
     * 【保存】菜单项操作方法
     */
    private void saveAsMenuItemAction() {

    }

    /**
     * 【关闭】菜单项操作方法
     */
    private void closeMenuItemAction(ActionEvent e) {
        // SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, e.getActionCommand(), "Menu Item", JOptionPane.PLAIN_MESSAGE));
    }

    /**
     * 【退出】菜单项操作方法
     */
    private void exitMenuItemAction() {
        dispose();
    }

    /**
     * 释放窗口及其子组件所使用的本机资源
     */
    @Override
    public void dispose() {
        super.dispose();
        FlatUIDefaultsInspector.hide();
    }

    /**
     * 【撤销】菜单项操作方法
     */
    public void undoMenuItemAction(ActionEvent e) {

    }

    /**
     * 【重做】菜单项操作方法
     */
    public void redoMenuItemAction(ActionEvent e) {

    }

    /**
     * 【删除】菜单项操作方法
     */
    public void deleteMenuItemAction(ActionEvent e) {

    }
}

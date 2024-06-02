package com.sweet.simple.login.view;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Tuple;
import cn.hutool.core.thread.ThreadUtil;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.demo.FlatLafDemo;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.FlatSVGUtils;
import com.formdev.flatlaf.extras.FlatUIDefaultsInspector;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.util.FontUtils;
import com.formdev.flatlaf.util.SystemInfo;
import com.sweet.simple.login.ViewStart;
import com.sweet.simple.login.util.RsaUtil;
import com.sweet.simple.login.view.HintManager.Hint;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import net.miginfocom.swing.MigLayout;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.prefs.Preferences;

/**
 * 主窗口
 *
 * @author 大师兄
 */
@Slf4j
public class MainFrame extends JFrame {

    private int initialFontMenuItemCount = -1;

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

    private ControlBar controlBar;

    private JCheckBoxMenuItem windowDecorationsCheckBoxMenuItem;
    private JCheckBoxMenuItem menuBarEmbeddedCheckBoxMenuItem;
    private JCheckBoxMenuItem unifiedTitleBarMenuItem;
    private JCheckBoxMenuItem showTitleBarIconMenuItem;
    private JCheckBoxMenuItem underlineMenuSelectionMenuItem;
    private JCheckBoxMenuItem alwaysShowMnemonicsMenuItem;
    private JCheckBoxMenuItem animatedLafChangeMenuItem;

    /**
     * 字体选项
     */
    private JMenu fontMenu;

    /**
     * 菜单选项
     */
    private JMenu optionsMenu;

    /**
     * 主题面板
     */
    public IJThemesPanel themesPanel;

    /**
     * 滚动长菜单选项
     */
    private JMenu scrollingPopupMenu;

    /**
     * html菜单选项
     */
    private JMenuItem htmlMenuItem;

    /**
     * 关于菜单项
     */
    private JMenuItem aboutMenuItem;

    public MainFrame() {
        // 从 DemoPrefs 中获取之前保存的选项卡索引，如果没有则默认为0
        int tabIndex = SweetPreferences.getState().getInt(ViewStart.KEY_TAB, 0);
        // 获取可用字体名称数组
        availableFontFamilyNames = FontUtils.getAvailableFontFamilyNames().clone();
        // 字体排序
        Arrays.sort(availableFontFamilyNames);
        // 初始化
        initComponents();
        // updateFontMenuItems();
        // initAccentColors();
        // initFullWindowContent();
        controlBar.initialize(this, tabbedPane);

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
        buildViewMenu();
        buildFontMenu();
        buildOptionsMenu();
        buildHelpMenu();

        setJMenuBar(menuBar);
        // 工具栏目
        buildToolBarPanel(contentPane);
        // 主界面选项卡
        buildTabs(contentPane);
        // 构建主题面板
        buildThemePanel(contentPane);
        // 用户图标
        buildUsersButton();

        if (supportsFlatLafWindowDecorations()) {
            if (SystemInfo.isLinux)
                unsupported(windowDecorationsCheckBoxMenuItem);
            else
                windowDecorationsCheckBoxMenuItem.setSelected(FlatLaf.isUseNativeWindowDecorations());

            menuBarEmbeddedCheckBoxMenuItem.setSelected(UIManager.getBoolean("TitlePane.menuBarEmbedded"));
            unifiedTitleBarMenuItem.setSelected(UIManager.getBoolean("TitlePane.unifiedBackground"));
            showTitleBarIconMenuItem.setSelected(UIManager.getBoolean("TitlePane.showIcon"));
        }
        else {
            unsupported(windowDecorationsCheckBoxMenuItem);
            unsupported(menuBarEmbeddedCheckBoxMenuItem);
            unsupported(unifiedTitleBarMenuItem);
            unsupported(showTitleBarIconMenuItem);
        }
        if (SystemInfo.isMacOS)
            unsupported(underlineMenuSelectionMenuItem);

        if ("false".equals(System.getProperty("flatlaf.animatedLafChange")))
            animatedLafChangeMenuItem.setSelected(false);
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
        openMenuItem.setText("打开");
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
     * 构建【视图】菜单选项
     */
    private void buildViewMenu() {
        JMenu viewMenu = new JMenu();
        JCheckBoxMenuItem checkBoxMenuItem1 = new JCheckBoxMenuItem();
        JMenu menu1 = new JMenu();
        JMenu subViewsMenu = new JMenu();
        JMenu subSubViewsMenu = new JMenu();
        JMenuItem errorLogViewMenuItem = new JMenuItem();
        JMenuItem searchViewMenuItem = new JMenuItem();
        JMenuItem projectViewMenuItem = new JMenuItem();
        JMenuItem structureViewMenuItem = new JMenuItem();
        JMenuItem propertiesViewMenuItem = new JMenuItem();
        JMenuItem menuItem2 = new JMenuItem();
        htmlMenuItem = new JMenuItem();
        JRadioButtonMenuItem radioButtonMenuItem1 = new JRadioButtonMenuItem();
        JRadioButtonMenuItem radioButtonMenuItem2 = new JRadioButtonMenuItem();
        JRadioButtonMenuItem radioButtonMenuItem3 = new JRadioButtonMenuItem();

        viewMenu.setText("视图");
        viewMenu.setMnemonic('V');
        //---- checkBoxMenuItem1 ----
        checkBoxMenuItem1.setText("显示工具栏");
        checkBoxMenuItem1.setSelected(true);
        checkBoxMenuItem1.setMnemonic('T');
        checkBoxMenuItem1.addActionListener(e -> menuItemActionPerformed(e));
        viewMenu.add(checkBoxMenuItem1);
        //======== menu1 ========
        menu1.setText("显示视图");
        menu1.setMnemonic('V');
        //======== subViewsMenu ========
        subViewsMenu.setText("二级视图");
        subViewsMenu.setMnemonic('S');
        //======== subSubViewsMenu ========
        subSubViewsMenu.setText("三级视图");
        subSubViewsMenu.setMnemonic('U');
        //---- errorLogViewMenuItem ----
        errorLogViewMenuItem.setText("错误日志");
        errorLogViewMenuItem.setMnemonic('E');
        errorLogViewMenuItem.addActionListener(e -> menuItemActionPerformed(e));
        subSubViewsMenu.add(errorLogViewMenuItem);
        subViewsMenu.add(subSubViewsMenu);
        //---- searchViewMenuItem ----
        searchViewMenuItem.setText("搜索");
        searchViewMenuItem.setMnemonic('S');
        searchViewMenuItem.addActionListener(e -> menuItemActionPerformed(e));
        subViewsMenu.add(searchViewMenuItem);
        menu1.add(subViewsMenu);
        //---- projectViewMenuItem ----
        projectViewMenuItem.setText("项目");
        projectViewMenuItem.setMnemonic('P');
        projectViewMenuItem.addActionListener(e -> menuItemActionPerformed(e));
        menu1.add(projectViewMenuItem);
        //---- structureViewMenuItem ----
        structureViewMenuItem.setText("结构");
        structureViewMenuItem.setMnemonic('T');
        structureViewMenuItem.addActionListener(e -> menuItemActionPerformed(e));
        menu1.add(structureViewMenuItem);
        //---- propertiesViewMenuItem ----
        propertiesViewMenuItem.setText("属性");
        propertiesViewMenuItem.setMnemonic('O');
        propertiesViewMenuItem.addActionListener(e -> menuItemActionPerformed(e));
        menu1.add(propertiesViewMenuItem);
        viewMenu.add(menu1);
        //======== scrollingPopupMenu ========
        scrollingPopupMenu = new JMenu();
        scrollingPopupMenu.setText("滚动弹出菜单");
        scrollingPopupMenu.add("大型菜单可以滚动");
        scrollingPopupMenu.add("使用鼠标滚轮滚动");
        scrollingPopupMenu.add("或者使用顶部/底部的上下箭头");
        for (int i = 1; i <= 100; i++)
            scrollingPopupMenu.add("Item " + i);

        viewMenu.add(scrollingPopupMenu);
        //---- menuItem2 ----
        menuItem2.setText("Disabled Item");
        menuItem2.setEnabled(false);
        viewMenu.add(menuItem2);
        //---- htmlMenuItem ----
        htmlMenuItem.setText("<html>some <b color=\"red\">HTML</b> <i color=\"blue\">text</i></html>");
        viewMenu.add(htmlMenuItem);
        viewMenu.addSeparator();
        //---- radioButtonMenuItem1 ----
        radioButtonMenuItem1.setText("Details");
        radioButtonMenuItem1.setSelected(true);
        radioButtonMenuItem1.setMnemonic('D');
        radioButtonMenuItem1.addActionListener(e -> menuItemActionPerformed(e));
        viewMenu.add(radioButtonMenuItem1);
        //---- radioButtonMenuItem2 ----
        radioButtonMenuItem2.setText("Small Icons");
        radioButtonMenuItem2.setMnemonic('S');
        radioButtonMenuItem2.addActionListener(e -> menuItemActionPerformed(e));
        viewMenu.add(radioButtonMenuItem2);
        //---- radioButtonMenuItem3 ----
        radioButtonMenuItem3.setText("Large Icons");
        radioButtonMenuItem3.setMnemonic('L');
        radioButtonMenuItem3.addActionListener(e -> menuItemActionPerformed(e));
        viewMenu.add(radioButtonMenuItem3);

        //---- buttonGroup1 ----
        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(radioButtonMenuItem1);
        buttonGroup1.add(radioButtonMenuItem2);
        buttonGroup1.add(radioButtonMenuItem3);
        menuBar.add(viewMenu);
    }

    /**
     * 构建【选项】菜单选项
     */
    private void buildOptionsMenu() {
        optionsMenu = new JMenu();
        windowDecorationsCheckBoxMenuItem = new JCheckBoxMenuItem();
        menuBarEmbeddedCheckBoxMenuItem = new JCheckBoxMenuItem();
        unifiedTitleBarMenuItem = new JCheckBoxMenuItem();
        showTitleBarIconMenuItem = new JCheckBoxMenuItem();
        underlineMenuSelectionMenuItem = new JCheckBoxMenuItem();
        alwaysShowMnemonicsMenuItem = new JCheckBoxMenuItem();
        animatedLafChangeMenuItem = new JCheckBoxMenuItem();
        JMenuItem showHintsMenuItem = new JMenuItem();
        JMenuItem showUIDefaultsInspectorMenuItem = new JMenuItem();
        //======== optionsMenu ========
        optionsMenu.setText("选项");
        //---- windowDecorationsCheckBoxMenuItem ----
        windowDecorationsCheckBoxMenuItem.setText("Window decorations");
        windowDecorationsCheckBoxMenuItem.addActionListener(e -> windowDecorationsChanged());
        optionsMenu.add(windowDecorationsCheckBoxMenuItem);

        //---- menuBarEmbeddedCheckBoxMenuItem ----
        menuBarEmbeddedCheckBoxMenuItem.setText("Embedded menu bar");
        menuBarEmbeddedCheckBoxMenuItem.addActionListener(e -> menuBarEmbeddedChanged());
        optionsMenu.add(menuBarEmbeddedCheckBoxMenuItem);

        //---- unifiedTitleBarMenuItem ----
        unifiedTitleBarMenuItem.setText("Unified window title bar");
        unifiedTitleBarMenuItem.addActionListener(e -> unifiedTitleBar());
        optionsMenu.add(unifiedTitleBarMenuItem);

        //---- showTitleBarIconMenuItem ----
        showTitleBarIconMenuItem.setText("Show window title bar icon");
        showTitleBarIconMenuItem.addActionListener(e -> showTitleBarIcon());
        optionsMenu.add(showTitleBarIconMenuItem);

        //---- underlineMenuSelectionMenuItem ----
        underlineMenuSelectionMenuItem.setText("Use underline menu selection");
        underlineMenuSelectionMenuItem.addActionListener(e -> underlineMenuSelection());
        optionsMenu.add(underlineMenuSelectionMenuItem);

        //---- alwaysShowMnemonicsMenuItem ----
        alwaysShowMnemonicsMenuItem.setText("Always show mnemonics");
        alwaysShowMnemonicsMenuItem.addActionListener(e -> alwaysShowMnemonics());
        optionsMenu.add(alwaysShowMnemonicsMenuItem);

        //---- animatedLafChangeMenuItem ----
        animatedLafChangeMenuItem.setText("Animated Laf Change");
        animatedLafChangeMenuItem.setSelected(true);
        animatedLafChangeMenuItem.addActionListener(e -> animatedLafChangeChanged());
        optionsMenu.add(animatedLafChangeMenuItem);

        //---- showHintsMenuItem ----
        showHintsMenuItem.setText("Show hints");
        showHintsMenuItem.addActionListener(e -> showHintsChanged());
        optionsMenu.add(showHintsMenuItem);

        //---- showUIDefaultsInspectorMenuItem ----
        showUIDefaultsInspectorMenuItem.setText("Show UI Defaults Inspector");
        showUIDefaultsInspectorMenuItem.addActionListener(e -> showUIDefaultsInspector());
        optionsMenu.add(showUIDefaultsInspectorMenuItem);
        menuBar.add(optionsMenu);
    }

    /**
     * 构建 Font 菜单选项
     */
    private void buildFontMenu() {
        fontMenu = new JMenu();
        JMenuItem restoreFontMenuItem = new JMenuItem();
        JMenuItem incrFontMenuItem = new JMenuItem();
        JMenuItem decrFontMenuItem = new JMenuItem();
        fontMenu.setText("字体");

        // 恢复字体 菜单选项
        restoreFontMenuItem.setText("恢复字体");
        restoreFontMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        restoreFontMenuItem.addActionListener(e -> restoreFont());
        fontMenu.add(restoreFontMenuItem);
        // 增加字体大小 菜单选项
        incrFontMenuItem.setText("增加字体大小");
        incrFontMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        incrFontMenuItem.addActionListener(e -> incrFont());
        fontMenu.add(incrFontMenuItem);
        // 减小字体大小 菜单选项
        decrFontMenuItem.setText("减小字体大小");
        decrFontMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        decrFontMenuItem.addActionListener(e -> decrFont());
        fontMenu.add(decrFontMenuItem);
        menuBar.add(fontMenu);
    }

    /**
     * 构建 Help 菜单选项
     */
    private void buildHelpMenu() {
        JMenu helpMenu = new JMenu();
        helpMenu.setText("关于");
        helpMenu.setMnemonic('H');

        aboutMenuItem = new JMenuItem();
        aboutMenuItem.setText("帮助");
        aboutMenuItem.setMnemonic('A');
        aboutMenuItem.addActionListener(e -> aboutActionPerformed());
        helpMenu.add(aboutMenuItem);

        // 生成秘钥对，暂时放在帮助菜单下 TODO
        JMenuItem createSecretKeyMenuItem = new JMenuItem();
        createSecretKeyMenuItem.setText("生成密钥对");
        createSecretKeyMenuItem.addActionListener(e -> createSecretKeyMenuItemAction());
        helpMenu.add(createSecretKeyMenuItem);

        menuBar.add(helpMenu);
    }

    /**
     * 构建主题面板
     */
    private void buildThemePanel(Container contentPane) {
        themesPanel = new IJThemesPanel();
        JPanel themesPanelPanel = new JPanel();
        JPanel winFullWindowContentButtonsPlaceholder = new JPanel();
        themesPanelPanel.setLayout(new BorderLayout());
        // ======== winFullWindowContentButtonsPlaceholder ========
        winFullWindowContentButtonsPlaceholder.setLayout(new FlowLayout());
        themesPanelPanel.add(winFullWindowContentButtonsPlaceholder, BorderLayout.NORTH);
        themesPanelPanel.add(themesPanel, BorderLayout.CENTER);
        contentPane.add(themesPanelPanel, BorderLayout.LINE_END);
        // on Windows/Linux, panel above themesPanel is a placeholder for title bar buttons in fullWindowContent mode
        winFullWindowContentButtonsPlaceholder.putClientProperty(FlatClientProperties.FULL_WINDOW_CONTENT_BUTTONS_PLACEHOLDER, "win");
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
        controlBar = new ControlBar();
        JPanel contentPanel = new JPanel();
        LoginComponentsPanel loginComponentsPanel = new LoginComponentsPanel();
        RegisterLoginPanel registerLoginPanel = new RegisterLoginPanel();
        contentPanel.setLayout(new MigLayout("insets dialog,hidemode 3", "[grow,fill]", "[grow,fill]"));

        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.addChangeListener(e -> selectedTabChanged());
        tabbedPane.addTab("Basic Components", loginComponentsPanel);
        tabbedPane.addTab("注册登录", registerLoginPanel);
        // tabbedPane.addTab("More Components", moreComponentsPanel);
        // tabbedPane.addTab("Data Components", dataComponentsPanel);
        // tabbedPane.addTab("Tabs", tabsPanel);
        // tabbedPane.addTab("Option Pane", optionPanePanel);
        // tabbedPane.addTab("Extras", extrasPanel);
        contentPanel.add(tabbedPane, "cell 0 0");
        contentPane.add(contentPanel, BorderLayout.CENTER);
        contentPane.add(controlBar, BorderLayout.PAGE_END);
    }

    /**
     * 构建用户图标
     */
    private void buildUsersButton() {
        // 创建按钮
        FlatButton usersButton = new FlatButton();
        // 设置图标
        usersButton.setIcon(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/users.svg"));
        // 将按钮的类型设置为工具栏按钮
        usersButton.setButtonType(FlatButton.ButtonType.toolBarButton);
        // 按钮的焦点设置为 false，这样在菜单栏中不会显示焦点框
        usersButton.setFocusable(false);
        // 为按钮添加了一个事件监听器
        usersButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Hello User! How are you?", "User", JOptionPane.INFORMATION_MESSAGE));
        // 将一个占位组件和按钮添加到菜单栏中
        menuBar.add(Box.createGlue());
        menuBar.add(usersButton);
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
     * 选择文件界面比较丑，后面优化 TODO
     */
    private void openMenuItemAction() {
        // 使用javafx的选择文件窗口，软件应用窗口会变小，无法解决 TODO
        /*
        new JFXPanel();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Platform.runLater(() -> {
            File selectedDirectory = directoryChooser.showDialog(null);
        });
        */
        // 默认首先搜寻路径,可根据自己的需要进行修改
        JFileChooser chooser = new JFileChooser("C:\\Users\\76789\\Desktop");
        // 显示文件选择对话框并等待用户操作
        int result = chooser.showOpenDialog(this);
        // 判断用户是否点击了打开按钮
        if (result == JFileChooser.APPROVE_OPTION) {
            // 获取用户选择的文件对象
            File selectedFile = chooser.getSelectedFile();
            // 进一步处理文件，例如获取文件名、路径等信息
            String fileName = selectedFile.getName();
            String filePath = selectedFile.getAbsolutePath();
            // 在这里可以对文件进行进一步操作，比如读取文件内容、显示文件信息等
            log.info("选择的文件名：{}", fileName);
            log.info("选择的文件路径：{}", filePath);
        }
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

    /**
     * 是否支持 FlatLaf 的窗口装饰
     *
     * @return boolean
     */
    private boolean supportsFlatLafWindowDecorations() {
        return FlatLaf.supportsNativeWindowDecorations() || (SystemInfo.isLinux && JFrame.isDefaultLookAndFeelDecorated());
    }

    /**
     * 处理在当前系统上不支持的选项，通过禁用选项、取消选中并提供相应的提示，让用户知道该选项无法在当前环境下生效
     */
    private void unsupported(JCheckBoxMenuItem menuItem) {
        menuItem.setEnabled(false);
        menuItem.setSelected(false);
        menuItem.setToolTipText("该选项在当前系统上不受支持");
    }

    /**
     * 窗口装饰选项改变时的操作
     * 用户改变窗口装饰选项时被调用，根据用户的选择来改变窗口装饰的设置，并相应地更新其他相关的菜单项的可用状态
     */
    private void windowDecorationsChanged() {
        // 获取名为 windowDecorationsCheckBoxMenuItem 的复选框菜单项的选中状态，以确定用户是否选择了窗口装饰
        boolean windowDecorations = windowDecorationsCheckBoxMenuItem.isSelected();
        // change window decoration of all frames and dialogs
        // 根据用户的选择，使用 FlatLaf 提供的方法设置是否使用本地窗口装饰。如果 windowDecorations 为 true，则使用本地窗口装饰；如果为 false，则不使用本地窗口装饰
        FlatLaf.setUseNativeWindowDecorations(windowDecorations);
        // 根据用户的选择，设置名为 menuBarEmbeddedCheckBoxMenuItem 的复选框菜单项的可用状态。如果 windowDecorations 为 true，则启用该选项；如果为 false，则禁用该选项
        menuBarEmbeddedCheckBoxMenuItem.setEnabled(windowDecorations);
        // 同样地，根据用户的选择，设置名为 unifiedTitleBarMenuItem 的菜单项的可用状态
        unifiedTitleBarMenuItem.setEnabled(windowDecorations);
        // 同样地，根据用户的选择，设置名为 showTitleBarIconMenuItem 的菜单项的可用状态
        showTitleBarIconMenuItem.setEnabled(windowDecorations);
    }

    /**
     * 处理菜单栏嵌入选项改变时的操作
     */
    private void menuBarEmbeddedChanged() {
        UIManager.put("TitlePane.menuBarEmbedded", menuBarEmbeddedCheckBoxMenuItem.isSelected());
        FlatLaf.revalidateAndRepaintAllFramesAndDialogs();
    }

    /**
     * 处理统一标题栏选项改变时的操作
     */
    private void unifiedTitleBar() {
        UIManager.put("TitlePane.unifiedBackground", unifiedTitleBarMenuItem.isSelected());
        FlatLaf.repaintAllFramesAndDialogs();
    }

    /**
     * 处理显示标题栏图标选项改变时的操作
     */
    private void showTitleBarIcon() {
        boolean showIcon = showTitleBarIconMenuItem.isSelected();

        // for main frame (because already created)
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_SHOW_ICON, showIcon);

        // for other not yet created frames/dialogs
        UIManager.put("TitlePane.showIcon", showIcon);
    }

    /**
     * 处理菜单选择项下划线选项改变时的操作
     */
    private void underlineMenuSelection() {
        UIManager.put("MenuItem.selectionType", underlineMenuSelectionMenuItem.isSelected() ? "underline" : null);
    }

    /**
     * 处理始终显示助记符选项改变时的操作
     */
    private void alwaysShowMnemonics() {
        UIManager.put("Component.hideMnemonics", !alwaysShowMnemonicsMenuItem.isSelected());
        repaint();
    }

    /**
     * 处理动画式外观更改选项改变时的操作
     */
    private void animatedLafChangeChanged() {
        System.setProperty("flatlaf.animatedLafChange", String.valueOf(animatedLafChangeMenuItem.isSelected()));
    }

    /**
     * 处理显示提示选项改变时的操作
     */
    private void showHintsChanged() {
        clearHints();
        showHints();
    }

    /**
     * 显示用户界面（UI）默认值检查器
     */
    private void showUIDefaultsInspector() {
        FlatUIDefaultsInspector.show();
    }

    /**
     * 清除所有提示
     */
    private void clearHints() {
        HintManager.hideAllHints();
        Preferences state = SweetPreferences.getState();
        state.remove("hint.fontMenu");
        state.remove("hint.optionsMenu");
        state.remove("hint.themesPanel");
    }

    /**
     * 显示所有提示
     */
    private void showHints() {
        Hint fontMenuHint = new Hint("如何使用字体菜单的信息", fontMenu, SwingConstants.BOTTOM, "hint.fontMenu", null);
        Hint optionsMenuHint = new HintManager.Hint("如何使用选项菜单的信息", optionsMenu, SwingConstants.BOTTOM, "hint.optionsMenu", fontMenuHint);
        Hint themesHint = new Hint("使用主题列表的信息", themesPanel, SwingConstants.LEFT, "hint.themesPanel", optionsMenuHint);
        HintManager.showHint(themesHint);
    }

    /**
     * 恢复默认字体设置
     */
    private void restoreFont() {
        UIManager.put("defaultFont", null);
        updateFontMenuItems();
        FlatLaf.updateUI();
    }

    /**
     * 增加默认字体的大小
     */
    private void incrFont() {
        Font font = UIManager.getFont("defaultFont");
        Font newFont = font.deriveFont((float) (font.getSize() + 1));
        UIManager.put("defaultFont", newFont);

        updateFontMenuItems();
        FlatLaf.updateUI();
    }

    /**
     * 减小默认字体的大小
     */
    private void decrFont() {
        Font font = UIManager.getFont("defaultFont");
        Font newFont = font.deriveFont((float) Math.max(font.getSize() - 1, 10));
        UIManager.put("defaultFont", newFont);

        updateFontMenuItems();
        FlatLaf.updateUI();
    }

    /**
     * 更新字体菜单项
     */
    public void updateFontMenuItems() {
        // 是否是首次调用该方法。如果是首次调用，则记录当前字体菜单项的数量
        if (initialFontMenuItemCount < 0)
            initialFontMenuItemCount = fontMenu.getItemCount();
        else {
            // remove old font items
            // 不是首次调用，则移除旧的字体菜单项，保留初始的字体菜单项数量
            for (int i = fontMenu.getItemCount() - 1; i >= initialFontMenuItemCount; i--)
                fontMenu.remove(i);
        }
        // get current font
        // 获取当前的字体设置，并从中提取当前的字体样式和字体大小
        Font currentFont = UIManager.getFont("Label.font");
        String currentFamily = currentFont.getFamily();
        String currentSize = Integer.toString(currentFont.getSize());

        // add font families
        // 添加字体样式的菜单项。首先添加一个分隔线，然后列出一些常见的字体样式，如 Arial、Comic Sans MS、Verdana 等。如果当前的字体样式不在这个列表中，则将其添加到列表中。然后按字母顺序对字体样式进行排序。对于每个字体样式，创建一个复选框菜单项，并根据当前字体设置将其选中状态设置为相应的值。同时为每个菜单项添加一个动作监听器，以在选择不同的字体样式时触发相应的动作
        fontMenu.addSeparator();
        ArrayList<String> families = new ArrayList<>(Arrays.asList(
                "Arial", "Cantarell", "Comic Sans MS", "DejaVu Sans",
                "Dialog", "Inter", "Liberation Sans", "Noto Sans", "Open Sans", "Roboto",
                "SansSerif", "Segoe UI", "Serif", "Tahoma", "Ubuntu", "Verdana"));
        if (!families.contains(currentFamily))
            families.add(currentFamily);
        families.sort(String.CASE_INSENSITIVE_ORDER);

        ButtonGroup familiesGroup = new ButtonGroup();
        for (String family : families) {
            if (Arrays.binarySearch(availableFontFamilyNames, family) < 0)
                continue; // not available

            JCheckBoxMenuItem item = new JCheckBoxMenuItem(family);
            item.setSelected(family.equals(currentFamily));
            item.addActionListener(this::fontFamilyChanged);
            fontMenu.add(item);

            familiesGroup.add(item);
        }

        // add font sizes
        // 添加字体大小的菜单项。同样，首先添加一个分隔线，然后列出一些常见的字体大小，如 10、12、14 等。如果当前的字体大小不在这个列表中，则将其添加到列表中。然后按数字顺序对字体大小进行排序。对于每个字体大小，创建一个复选框菜单项，并根据当前字体设置将其选中状态设置为相应的值。同时为每个菜单项添加一个动作监听器，以在选择不同的字体大小时触发相应的动作
        fontMenu.addSeparator();
        ArrayList<String> sizes = new ArrayList<>(Arrays.asList(
                "10", "11", "12", "14", "16", "18", "20", "24", "28"));
        if (!sizes.contains(currentSize))
            sizes.add(currentSize);
        sizes.sort(String.CASE_INSENSITIVE_ORDER);

        ButtonGroup sizesGroup = new ButtonGroup();
        for (String size : sizes) {
            JCheckBoxMenuItem item = new JCheckBoxMenuItem(size);
            item.setSelected(size.equals(currentSize));
            item.addActionListener(this::fontSizeChanged);
            fontMenu.add(item);

            sizesGroup.add(item);
        }
        // enabled/disable items
        // 根据当前的外观，启用或禁用所有菜单项。如果当前的外观是 FlatLaf，则启用所有菜单项；否则，禁用所有菜单项
        boolean enabled = UIManager.getLookAndFeel() instanceof FlatLaf;
        for (Component item : fontMenu.getMenuComponents())
            item.setEnabled(enabled);
    }

    /**
     * 处理字体样式改变的事件
     */
    private void fontFamilyChanged(ActionEvent e) {
        String fontFamily = e.getActionCommand();
        FlatAnimatedLafChange.showSnapshot();
        Font font = UIManager.getFont("defaultFont");
        Font newFont = FontUtils.getCompositeFont(fontFamily, font.getStyle(), font.getSize());
        UIManager.put("defaultFont", newFont);

        FlatLaf.updateUI();
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    /**
     * 处理字体大小改变的事件
     */
    private void fontSizeChanged(ActionEvent e) {
        String fontSizeStr = e.getActionCommand();
        Font font = UIManager.getFont("defaultFont");
        Font newFont = font.deriveFont((float) Integer.parseInt(fontSizeStr));
        UIManager.put("defaultFont", newFont);
        FlatLaf.updateUI();
    }

    private void menuItemActionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, e.getActionCommand(), "Menu Item", JOptionPane.PLAIN_MESSAGE));
    }

    /**
     * 关于 菜单项操作
     */
    private void aboutActionPerformed() {
        JLabel titleLabel = new JLabel("大师兄登录器");
        titleLabel.putClientProperty(FlatClientProperties.STYLE_CLASS, "h1");

        String link = "http://www.sweetdnf.com";
        JLabel linkLabel = new JLabel("<html><a href=\"#\">" + link + "</a></html>");
        linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        linkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(link));
                }
                catch (IOException | URISyntaxException ex) {
                    JOptionPane.showMessageDialog(linkLabel,
                            "Failed to open '" + link + "' in browser.",
                            "关于", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
        JOptionPane.showMessageDialog(this,
                new Object[]{
                        titleLabel,
                        "Demonstrates FlatLaf Swing look and feel",
                        " ",
                        "Copyright 2019-" + Year.now() + " FormDev Software GmbH",
                        linkLabel,
                },
                "关于", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * 生成秘钥对操作方法，文件生成以后放到桌面
     * 格式化秘钥 TODO
     */
    private void createSecretKeyMenuItemAction() {
        Tuple tuple = RsaUtil.createSecretKeyPair(true, true);
        File userHOmeDir = FileUtil.getUserHomeDir();
        // 用户桌面路径
        String desktopPath = userHOmeDir.getAbsolutePath() + "\\Desktop\\";
        FileUtil.writeUtf8String(tuple.get(0), desktopPath + "privatekey.pem");
        FileUtil.writeUtf8String(tuple.get(1), desktopPath + "publickey.pem");
        // 成功提示
        showDialog("密钥对生成成功");
    }

    /**
     * 简单的信息提示弹窗，后面需要进行抽象封装 TODO
     *
     * @param message 提示信息
     */
    private void showDialog(String message) {
        Window window = SwingUtilities.windowForComponent(this);
        JOptionPane optionPane = new JOptionPane();
        optionPane.setMessage(message);
        JOptionPane.showOptionDialog(window, optionPane.getMessage(), "提示", optionPane.getOptionType(), optionPane.getMessageType(), optionPane.getIcon(), optionPane.getOptions(), optionPane.getInitialValue());
    }
}

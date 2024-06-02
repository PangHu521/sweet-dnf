package com.sweet.simple.login.view;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.ui.FlatDropShadowBorder;
import com.formdev.flatlaf.ui.FlatEmptyBorder;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.UIScale;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 提示框
 *
 * @author 大师兄
 */
class HintManager {

    /**
     * 提示框面板集合
     */
    private static final List<HintPanel> hintPanels = new ArrayList<>();

    /**
     * 显示提示信息
     *
     * @param hint 提示信息对象
     */
    public static void showHint(Hint hint) {
        // 检查用户是否已经关闭提示
        if (SweetPreferences.getState().getBoolean(hint.prefsKey, false)) {
            if (hint.nextHint != null)
                showHint(hint.nextHint);
            return;
        }
        // 传入提示信息创建一个提示面板
        HintPanel hintPanel = new HintPanel(hint);
        // 显示提示信息
        hintPanel.showHint();
        // 添加到面板集合中
        hintPanels.add(hintPanel);
    }

    /**
     * 隐藏所有提示信息
     */
    public static void hideAllHints() {
        // 在迭代期间保持 hintPanels 集合的静态快照，以防止在迭代过程中发生并发修改异常，所以创建一个新的数组 hintPanels2
        HintPanel[] hintPanels2 = hintPanels.toArray(new HintPanel[hintPanels.size()]);
        Arrays.stream(hintPanels2).forEach(HintPanel::hideHint);
    }

    //---- class HintPanel ----------------------------------------------------
    /**
     * 定义一个提示的具体信息
     */
    static class Hint {

        /**
         * 提示消息
         */
        private final String message;

        /**
         * 拥有者组件
         */
        private final Component owner;

        /**
         * 位置
         */
        private final int position;

        /**
         * 偏好键
         */
        private final String prefsKey;

        /**
         * 下一个提示
         */
        private final Hint nextHint;

        Hint(String message, Component owner, int position, String prefsKey, Hint nextHint) {
            this.message = message;
            this.owner = owner;
            this.position = position;
            this.prefsKey = prefsKey;
            this.nextHint = nextHint;
        }
    }

    //---- class HintPanel ----------------------------------------------------
    /**
     * 代表一个具体的提示框面板，它继承自 JPanel。这个类负责创建提示框的UI，并处理显示和隐藏逻辑
     */
    private static class HintPanel extends JPanel {

        private final Hint hint;

        private JPanel popup;

        private HintPanel(Hint hint) {
            this.hint = hint;
            initComponents();
            setOpaque(false);
            updateBalloonBorder();
            hintLabel.setText("<html>" + hint.message + "</html>");
            // grab all mouse events to avoid that components overlapped
            // by the hint panel receive them
            addMouseListener(new MouseAdapter() {});
        }

        /**
         * 重写更新面板方法
         * 当应用程序的外观和感觉发生变化时，调用该方法可以使面板及其所有子组件适应新的外观和感觉
         */
        @Override
        public void updateUI() {
            super.updateUI();

            if (UIManager.getLookAndFeel() instanceof FlatLaf)
                setBackground(UIManager.getColor("HintPanel.backgroundColor"));
            else {
                // using nonUIResource() because otherwise Nimbus does not fill the background
                setBackground(FlatUIUtils.nonUIResource(UIManager.getColor("info")));
            }

            if (hint != null)
                updateBalloonBorder();
        }

        /**
         * 更新提示框的气泡边框，根据提示框的位置设置边框的方向和大小
         */
        private void updateBalloonBorder() {
            int direction;
            switch (hint.position) {
                case SwingConstants.LEFT:
                    direction = SwingConstants.RIGHT;
                    break;
                case SwingConstants.TOP:
                    direction = SwingConstants.BOTTOM;
                    break;
                case SwingConstants.RIGHT:
                    direction = SwingConstants.LEFT;
                    break;
                case SwingConstants.BOTTOM:
                    direction = SwingConstants.TOP;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            // 根据确定的边框方向和指定的边框颜色，创建一个新的气泡边框对象，并将其设置为面板的边框
            setBorder(new BalloonBorder(direction, FlatUIUtils.getUIColor("PopupMenu.borderColor", Color.gray)));
        }

        /**
         * 显示提示框
         */
        void showHint() {
            // 获取提示框所属组件的根窗格（JRootPane）。如果根窗格不存在（即为 null），则直接返回
            JRootPane rootPane = SwingUtilities.getRootPane(hint.owner);
            if (rootPane == null)
                return;

            // 通过根窗格获取层次窗格，用于在层次结构中添加提示框
            JLayeredPane layeredPane = rootPane.getLayeredPane();

            // 创建一个具有边框布局的面板
            popup = new JPanel(new BorderLayout()) {
                @Override
                public void updateUI() {
                    super.updateUI();

                    // use invokeLater because at this time the UI delegates
                    // of child components are not yet updated
                    EventQueue.invokeLater(() -> {
                        validate();
                        setSize(getPreferredSize());
                    });
                }
            };
            // 设置为透明
            popup.setOpaque(false);
            // 将当前组件（即提示框的内容）添加到 popup 面板中
            popup.add(this);

            // 根据提示框的位置，计算提示框的显示位置（x 和 y 坐标）
            // calculate x/y location for hint popup
            Point pt = SwingUtilities.convertPoint(hint.owner, 0, 0, layeredPane);
            int x = pt.x;
            int y = pt.y;
            Dimension size = popup.getPreferredSize();
            int gap = UIScale.scale(6);

            switch (hint.position) {
                case SwingConstants.LEFT:
                    x -= size.width + gap;
                    break;

                case SwingConstants.TOP:
                    y -= size.height + gap;
                    break;

                case SwingConstants.RIGHT:
                    x += hint.owner.getWidth() + gap;
                    break;

                case SwingConstants.BOTTOM:
                    y += hint.owner.getHeight() + gap;
                    break;
            }

            // 设置提示框的位置和大小，并将其添加到层次窗格的弹出层级（JLayeredPane.POPUP_LAYER）上，以确保在其他组件之上显示
            popup.setBounds(x, y, size.width, size.height);
            layeredPane.add(popup, JLayeredPane.POPUP_LAYER);
        }

        /**
         * 隐藏提示框
         */
        void hideHint() {
            if (popup != null) {
                // 获取 popup 的父容器（即包含它的容器），如果父容器不为 null，则从父容器中移除 popup。然后，调用 repaint() 方法来重新绘制父容器中被移除的区域，以确保界面更新
                Container parent = popup.getParent();
                if (parent != null) {
                    parent.remove(popup);
                    parent.repaint(popup.getX(), popup.getY(), popup.getWidth(), popup.getHeight());
                }
            }
            // 从 hintPanels 集合中移除当前提示框对象，以便后续清理和管理
            hintPanels.remove(this);
        }

        /**
         * 处理用户关闭提示框的操作
         * 用户关闭当前提示框后，更新用户的偏好设置，并且如果有下一个提示框的话，显示下一个提示框
         */
        private void gotIt() {
            // 隐藏当前的提示框
            hideHint();

            // 使用 SweetPreferences.getState().putBoolean(hint.prefsKey, true) 语句，将一个布尔值标记为 true，以表示用户已经关闭了此提示框。这个布尔值通常用于保存用户的偏好设置，以便在下次打开应用程序时能够记住用户的操作
            SweetPreferences.getState().putBoolean(hint.prefsKey, true);

            // 如果当前提示框有关联的下一个提示框（即 hint.nextHint 不为 null），则调用 HintManager.showHint(hint.nextHint) 方法来显示下一个提示框
            if (hint.nextHint != null)
                HintManager.showHint(hint.nextHint);
        }

        private JLabel hintLabel;
        private JButton gotItButton;


        /**
         * 初始化
         */
        private void initComponents() {
            // 创建了一个 JLabel 对象 hintLabel，并设置其文本为 "hint"
            hintLabel = new JLabel();
            // 创建了一个 JButton 对象 gotItButton，并设置其文本为 "Got it!"。此按钮用于用户关闭提示框
            gotItButton = new JButton();
            // 设置当前容器布局管理器为 MigLayout，MigLayout 是一种灵活的布局管理器，可以根据设定的约束自动调整组件的位置和大小
            setLayout(new MigLayout("insets dialog,hidemode 3", "[::200,fill]", "[]para" + "[]"));
            hintLabel.setText("hint");
            // 将 hintLabel 添加到当前容器中，并使用约束 "cell 0 0" 将其放置在第一行第一列的位置
            add(hintLabel, "cell 0 0");

            gotItButton.setText("Got it!");
            // 焦点设置为不可获取
            gotItButton.setFocusable(false);
            // 添加了一个动作监听器，当用户点击 gotItButton 按钮时，调用 gotIt()方法
            gotItButton.addActionListener(e -> gotIt());
            // 将 gotItButton 添加到当前容器中，并使用约束 "cell 0 1,alignx right,growx 0" 将其放置在第一行第二列的位置。alignx right 指定了按钮在水平方向上右对齐，growx 0 表示按钮在水平方向上不会拉伸
            add(gotItButton, "cell 0 1,alignx right,growx 0");
        }
    }

    //---- class BalloonBorder ------------------------------------------------

    /**
     * 具有气球效果的边框
     */
    private static class BalloonBorder extends FlatEmptyBorder {

        /**
         * 定义边框圆角的大小
         */
        private static final int ARC = 8;

        /**
         * 定义箭头的中心点到边框的距离
         */
        private static final int ARROW_XY = 16;

        /**
         * 定义箭头的大小
         */
        private static final int ARROW_SIZE = 8;

        /**
         * 定义阴影的大小
         */
        private static final int SHADOW_SIZE = 6;

        /**
         * 定义阴影顶部的额外大小
         */
        private static final int SHADOW_TOP_SIZE = 3;

        /**
         * 定义阴影大小加上额外2像素，用于边框的内边距
         */
        private static final int SHADOW_SIZE2 = SHADOW_SIZE + 2;

        /**
         * 指示边框的方向（左、上、右、下）
         */
        private final int direction;

        /**
         * 边框的颜色
         */
        private final Color borderColor;

        /**
         * 如果使用 FlatLaf 外观，则创建一个阴影边框
         */
        private final Border shadowBorder;

        public BalloonBorder(int direction, Color borderColor) {
            // 调用父类 FlatEmptyBorder 的构造函数，设置边框的大小
            super(1 + SHADOW_TOP_SIZE, 1 + SHADOW_SIZE, 1 + SHADOW_SIZE, 1 + SHADOW_SIZE);

            this.direction = direction;
            this.borderColor = borderColor;

            // 根据 direction 调整边框的左右上下边距，以适应箭头的大小
            switch (direction) {
                case SwingConstants.LEFT:
                    left += ARROW_SIZE;
                    break;
                case SwingConstants.TOP:
                    top += ARROW_SIZE;
                    break;
                case SwingConstants.RIGHT:
                    right += ARROW_SIZE;
                    break;
                case SwingConstants.BOTTOM:
                    bottom += ARROW_SIZE;
                    break;
            }

            shadowBorder = UIManager.getLookAndFeel() instanceof FlatLaf
                    ? new FlatDropShadowBorder(
                    UIManager.getColor("Popup.dropShadowColor"),
                    new Insets(SHADOW_SIZE2, SHADOW_SIZE2, SHADOW_SIZE2, SHADOW_SIZE2),
                    FlatUIUtils.getUIFloat("Popup.dropShadowOpacity", 0.5f))
                    : null;
        }

        /**
         * 绘制一个气球状的边框
         */
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                FlatUIUtils.setRenderingHints(g2);
                g2.translate(x, y);

                // shadow coordinates
                int sx = 0;
                int sy = 0;
                int sw = width;
                int sh = height;
                int arrowSize = UIScale.scale(ARROW_SIZE);
                switch (direction) {
                    case SwingConstants.LEFT:
                        sx += arrowSize;
                        sw -= arrowSize;
                        break;
                    case SwingConstants.TOP:
                        sy += arrowSize;
                        sh -= arrowSize;
                        break;
                    case SwingConstants.RIGHT:
                        sw -= arrowSize;
                        break;
                    case SwingConstants.BOTTOM:
                        sh -= arrowSize;
                        break;
                }

                // paint shadow
                if (shadowBorder != null)
                    shadowBorder.paintBorder(c, g2, sx, sy, sw, sh);

                // create balloon shape
                int bx = UIScale.scale(SHADOW_SIZE);
                int by = UIScale.scale(SHADOW_TOP_SIZE);
                int bw = width - UIScale.scale(SHADOW_SIZE + SHADOW_SIZE);
                int bh = height - UIScale.scale(SHADOW_TOP_SIZE + SHADOW_SIZE);
                g2.translate(bx, by);
                Shape shape = createBalloonShape(bw, bh);

                // fill balloon background
                g2.setColor(c.getBackground());
                g2.fill(shape);

                // paint balloon border
                g2.setColor(borderColor);
                g2.setStroke(new BasicStroke(UIScale.scale(1f)));
                g2.draw(shape);
            } finally {
                g2.dispose();
            }
        }

        /**
         * 根据 direction 创建一个箭头形状和圆角矩形形状，将箭头形状添加到圆角矩形形状中，形成一个完整的气球形状，返回创建的气球形状
         *
         * @param width  宽
         * @param height 高
         * @return 气泡形状
         */
        private Shape createBalloonShape(int width, int height) {
            int arc = UIScale.scale(ARC);
            int xy = UIScale.scale(ARROW_XY);
            int awh = UIScale.scale(ARROW_SIZE);

            Shape rect;
            Shape arrow;
            switch (direction) {
                case SwingConstants.LEFT:
                    rect = new RoundRectangle2D.Float(awh, 0, width - 1 - awh, height - 1, arc, arc);
                    arrow = FlatUIUtils.createPath(awh, xy, 0, xy + awh, awh, xy + awh + awh);
                    break;

                case SwingConstants.TOP:
                    rect = new RoundRectangle2D.Float(0, awh, width - 1, height - 1 - awh, arc, arc);
                    arrow = FlatUIUtils.createPath(xy, awh, xy + awh, 0, xy + awh + awh, awh);
                    break;

                case SwingConstants.RIGHT:
                    rect = new RoundRectangle2D.Float(0, 0, width - 1 - awh, height - 1, arc, arc);
                    int x = width - 1 - awh;
                    arrow = FlatUIUtils.createPath(x, xy, x + awh, xy + awh, x, xy + awh + awh);
                    break;

                case SwingConstants.BOTTOM:
                    rect = new RoundRectangle2D.Float(0, 0, width - 1, height - 1 - awh, arc, arc);
                    int y = height - 1 - awh;
                    arrow = FlatUIUtils.createPath(xy, y, xy + awh, y + awh, xy + awh + awh, y);
                    break;

                default:
                    throw new RuntimeException();
            }

            Area area = new Area(rect);
            area.add(new Area(arrow));
            return area;
        }
    }
}

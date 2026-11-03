
import javax.swing.*;
import java.awt.*;

public class TicTacToe extends JFrame {
    JPanel main = new JPanel(new CardLayout());
    JPanel menu = new JPanel(new GridLayout(4, 1, 10, 15));
    JPanel game = new JPanel(new BorderLayout(8, 8));
    JPanel board = new JPanel(new GridLayout(3, 3, 8, 8));

    JButton[] b = new JButton[9];
    JLabel status = new JLabel("X's Turn", JLabel.CENTER);
    CardLayout cards = (CardLayout) main.getLayout();

    boolean xTurn = true;
    Color bg = new Color(20, 20, 35);

    TicTacToe() {
        setTitle("Neon Tic Tac Toe");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // MENU PAGE
        menu.setBackground(bg);

        JLabel title = new JLabel("TIC TAC TOE", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(Color.CYAN);
        menu.add(title);

        JButton start = button("START GAME");
        JButton exit = button("EXIT");

        menu.add(start);
        menu.add(exit);
        menu.add(new JLabel("   X  vs  O", JLabel.CENTER));

        start.addActionListener(e -> cards.show(main, "game"));
        exit.addActionListener(e -> System.exit(0));

        // GAME PAGE
        game.setBackground(bg);
        status.setFont(new Font("Arial", Font.BOLD, 25));
        status.setForeground(Color.CYAN);
        game.add(status, BorderLayout.NORTH);

        board.setBackground(bg);

        for (int i = 0; i < 9; i++) {
            b[i] = button("");
            b[i].setFont(new Font("Arial", Font.BOLD, 40));
            board.add(b[i]);

            b[i].addActionListener(e -> {
                JButton btn = (JButton) e.getSource();
                if (!btn.isEnabled() || !btn.getText().isEmpty())
                    return;

                String mark = xTurn ? "X" : "O";
                btn.setText(mark);
                btn.setForeground(xTurn ? Color.CYAN : Color.MAGENTA);

                // POP ANIMATION
                btn.setFont(new Font("Arial", Font.BOLD, 10));
                final int[] size = {10};
                Timer t = new Timer(15, null);
                t.addActionListener(a -> {
                    size[0] += 5;
                    btn.setFont(new Font("Arial", Font.BOLD, size[0]));
                    if (size[0] >= 40) t.stop();
                });
                t.start();

                if (win()) {
                    status.setText(mark + " WINS!");
                    status.setForeground(Color.GREEN);
                    lockBoard();
                } else if (draw()) {
                    status.setText("IT'S A DRAW!");
                    lockBoard();
                } else {
                    xTurn = !xTurn;
                    status.setText(xTurn ? "X's Turn" : "O's Turn");
                    status.setForeground(
                        xTurn ? Color.CYAN : Color.MAGENTA);
                }
            });
        }

        game.add(board, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new GridLayout(1, 2, 8, 8));
        buttons.setBackground(bg);

        JButton restart = button("NEW GAME");
        JButton back = button("MENU");

        restart.addActionListener(e -> reset());
        back.addActionListener(e -> {
            reset();
            cards.show(main, "menu");
        });

        buttons.add(restart);
        buttons.add(back);
        game.add(buttons, BorderLayout.SOUTH);

        main.add(menu, "menu");
        main.add(game, "game");
        add(main);

        cards.show(main, "menu");
        setVisible(true);
    }

    JButton button(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setBackground(new Color(40, 40, 70));
        btn.setForeground(Color.CYAN);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        return btn;
    }

    boolean win() {
        int[][] lines = {
            {0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8}, {2,4,6}
        };

        for (int[] l : lines) {
            String s = b[l[0]].getText();
            if (!s.isEmpty() && s.equals(b[l[1]].getText())
                && s.equals(b[l[2]].getText()))
                return true;
        }
        return false;
    }

    boolean draw() {
        for (JButton btn : b)
            if (btn.getText().isEmpty()) return false;
        return true;
    }

    void lockBoard() {
        for (JButton btn : b) btn.setEnabled(false);
    }

    void reset() {
        for (JButton btn : b) {
            btn.setText("");
            btn.setEnabled(true);
            btn.setFont(new Font("Arial", Font.BOLD, 40));
        }
        xTurn = true;
        status.setText("X's Turn");
        status.setForeground(Color.CYAN);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}

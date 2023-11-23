package task2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class InvalidDataFormatException extends ArithmeticException {
    public InvalidDataFormatException() {
        super("Неправильний формат даних, дані мають бути не від'ємними та цілими !!!");
    }
}

public class SumWindow extends JFrame {
    private JTextField fileField;
    JTable table1;
    JTable table2;

    public SumWindow() {
        super("Сумма чисел в файле");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 480);
        setLayout(new BorderLayout());

        fileField = new JTextField(25);
        JButton loadButton = new JButton("Початковий файл");
        loadButton.addActionListener(new LoadButtonListener());

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Шлях:"));
        topPanel.add(fileField);
        topPanel.add(loadButton);

        add(topPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    private class LoadButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(SumWindow.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                fileField.setText(file.getAbsolutePath());
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line = null;
                    int sizeN = 0;
                    int row = 0;

                    int[][] matrix;
                    int[][] out;
                    if ((line = reader.readLine()) != null) {
                        String[] lineValues = line.split(" ");

                        sizeN = Integer.parseInt(lineValues[0]);

                        if (lineValues.length != 1 || sizeN < 1) {
                            throw new InvalidDataFormatException();
                        }

                        System.out.println("" + sizeN + " " + sizeN);
                        matrix = new int[sizeN][sizeN];

                        JPanel matrices = new JPanel();
                        matrices.setLayout(null); // set null layout manager
                        table1 = new JTable(sizeN, sizeN);
                        table1.setBounds(5, 20 + 5, 200, 63);

                        JLabel label1 = new JLabel("Початкова матриця:" + sizeN);
                        label1.setBounds(5, 5, 200, 15);
                        matrices.add(label1);

                        matrices.add(table1);
                        add(matrices);
                        setVisible(true);

                        while ((line = reader.readLine()) != null) {
                            try {
                                lineValues = line.split(" ");
                                for (int i = 0; i < sizeN; i++) {
                                    matrix[row][i] = Integer.parseInt(lineValues[i]);
                                    System.out.print(matrix[row][i] + " ");
                                    table1.setValueAt(matrix[row][i], row, i);
                                }
                                row++;
                                System.out.println();
                            } catch (NumberFormatException ex) {
                                // ignore non-numeric lines
                            }
                        }
                        System.out.println();

                        double sumOfMainDiagonal = 0;
                        for (int i = 0; i < sizeN; i++) {
                            sumOfMainDiagonal += matrix[i][i];
                        }

                        double sumOfUpperDiagonal = 0;
                        for (int i = 0; i < sizeN; i++) {
                            for (int j = 0; j < i; j++) {
                                if (matrix[i][j] > matrix[j][i]) {
                                    sumOfUpperDiagonal += matrix[i][j];
                                }
                            }
                        }

                        JLabel label2 = new JLabel(
                                "Сума на головній діагоналі: " + sumOfMainDiagonal + ";" +" Сума під головною діагоналлю: " + (sumOfUpperDiagonal));
                        label2.setBounds(5, 120, 500, 15);
                        matrices.add(label2);

                        reader.close();

                        JFileChooser saveChooser = new JFileChooser();
                        result = saveChooser.showSaveDialog(SumWindow.this);
                        if (result == JFileChooser.APPROVE_OPTION) {
                            File saveFile = saveChooser.getSelectedFile();
                            BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));

                            writer.write("Сума на головній діагоналі:" + sumOfMainDiagonal);
                            writer.write("Сума елементів матриці над головною діагоналлю: " + sumOfUpperDiagonal);
                            writer.close();
                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(SumWindow.this,
                            "Помилка при читанні файла: " + ex.getMessage(),
                            "Помилка", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SumWindow.this,
                            "Число не ціле !!! : " + ex.getMessage(),
                            "Помилка", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(SumWindow.this,
                            "Неправильний формат вхідних даних: " + ex.getMessage(),
                            "Помилка", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        new SumWindow();
    }
}
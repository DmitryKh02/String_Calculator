import java.util.Scanner;

/**
 * Основной класс
 * @author Khokhlov Dmitrii
 * @version 1.3
 */
class secon_lab {
    public static void main(String[] args) {
        System.out.println("Enter your expression: ");
        Scanner in = new Scanner(System.in);

        String exp = in.nextLine();
        Tree example = new Tree();
        example.task(exp);

        in.close();
        System.exit(0);

    }
}

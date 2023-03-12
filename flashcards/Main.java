package flashcards;

import java.io.*;
import java.util.*;

public class Main {
    static Scanner scan = new Scanner(System.in);

    static HashSet<Card> cards = new HashSet<>();

    public static void main(String[] args) {
        startProgram();
    }

    private static void startProgram() {
        String userAction;
        do {
            System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            userAction = scan.nextLine();
            switch (userAction) {
                case "add" -> addCard();
                case "remove" -> removeCard();
                case "import" -> importCards();
                case "export" -> exportCards();
                case "ask" -> askQuestions();
                default -> System.out.println("Bye bye!");
            }
            System.out.println();
        }
        while (!"exit".equals(userAction));
    }

    private static void askQuestions() {
        System.out.println("How many times to ask?");
        int quantityOfQuestions = Integer.parseInt(scan.nextLine());
        List<Card> cardList = new ArrayList<>(cards);
        for (int i = 0; i < quantityOfQuestions; i++) {
            int listIndex = new Random().nextInt(cardList.size());

            String termToAsk = cardList.get(listIndex).getTerm();
            System.out.printf("Print the definition of \"%s\":%n", termToAsk);
            String userDefinition = scan.nextLine();

            if (Objects.equals(cardList.get(listIndex).getDefinition(), userDefinition)) {
                System.out.println("Correct!");
            } else {
                StringBuilder wrongMessage = new StringBuilder("Wrong. The right answer is \"");
                wrongMessage.append(cardList.get(listIndex).getDefinition()).append("\"");

                if (hasDefinition(userDefinition)) {
                    wrongMessage.append(", but your definition is correct for \"");
                    wrongMessage.append(getTermFromDefinition(userDefinition)).append("\".");
                } else {
                    wrongMessage.append(".");
                }
                System.out.println(wrongMessage);
            }
        }
    }

    private static String getTermFromDefinition(String userDefinition) {
        for (Card card: cards) {
            if (card.getDefinition().equals(userDefinition)) {
                return card.getTerm();
            }
        }
        return "";
    }

    private static void exportCards() {
        System.out.println("File name:");
        String exportPath = scan.nextLine();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(exportPath))) {
            for (Card card: cards) {
                bw.write(card.getTerm());
                bw.write(",");
                bw.write(card.getDefinition());
                bw.newLine();
            }
            System.out.printf("%s cards have been saved.%n", cards.size());
        } catch (IOException ex) {
            System.out.println("Error while exporting");
        }
    }

    private static void importCards() {
        System.out.println("File name:");
        String filePath = scan.nextLine();
        try (BufferedReader bf = new BufferedReader(new FileReader(filePath))) {
            System.out.printf("%s cards have been loaded.%n", getCardsFromFile(bf));
        } catch (IOException ex) {
            System.out.println("File not found.");
        }
    }

    private static int getCardsFromFile(BufferedReader bf) throws IOException {
        String[] lines = bf.lines().toArray(String[]::new);
        for (String line: lines) {
            String[] fields = line.split(",");
            cards.add(new Card(fields[0], fields[1]));
        }
        return lines.length;
    }

    private static void removeCard() {
        System.out.println("Which card?");
        String cardToDelete = scan.nextLine();
        if (hasTerm(cardToDelete)) {
            cards.removeIf(card -> card.getTerm().equals(cardToDelete));
            System.out.println("The card has been removed.");
        } else {
            System.out.printf("Can't remove \"%s\": there is no such card.%n", cardToDelete);
        }
    }

    private static void addCard() {
        System.out.println("The card:");
        String term = scan.nextLine();
        if (hasTerm(term)) {
            System.out.printf("The card \"%s\" already exists.%n", term);
        } else {
            System.out.println("The definition of the card:");
            String definition = scan.nextLine();
            if (hasDefinition(definition)) {
                System.out.printf("The definition \"%s\" already exists.%n", definition);
            } else {
                cards.add(new Card(term, definition));
                System.out.printf("The pair (\"%s\":\"%s\") has been added.%n", term, definition);
            }
        }
    }

    private static boolean hasTerm(String term) {
        for (Card card: cards) {
            if (card.getTerm().equals(term)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasDefinition(String definition) {
        for (Card card: cards) {
            if (card.getDefinition().equals(definition)) {
                return true;
            }
        }
        return false;
    }
}

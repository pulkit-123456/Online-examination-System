import java.util.*;

class Question {
    String question, optionA, optionB, optionC, optionD, correctAnswer;

    public Question(String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
    }
}

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final List<Question> questions = new ArrayList<>();
    private static int examDurationSeconds = 60; // default duration 1 minute

    private static void adminSetDuration() {
        try {
            System.out.print("Enter exam duration in seconds: ");
            int duration = Integer.parseInt(sc.nextLine().trim());
            if (duration > 0) {
                examDurationSeconds = duration;
                System.out.println("Exam duration updated to " + examDurationSeconds + " seconds.");
            } else {
                System.out.println("Invalid duration. Must be greater than 0.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Duration unchanged.");
        }
    }

    private static void studentTakeExam() {
        if (questions.isEmpty()) {
            System.out.println("❌ No questions available. Ask admin to add questions first.");
            return;
        }

        System.out.println("\n===== Student Exam =====");
        System.out.print("Enter student name: ");
        String studentName = sc.nextLine().trim();

        int score = 0;
        int total = questions.size();

        System.out.println("\nExam will start now. Duration: " + examDurationSeconds + " seconds.");
        System.out.println("Please answer using A/B/C/D (press Enter after each answer).");
        System.out.println("Press Enter to begin...");
        sc.nextLine(); // wait for user to press Enter

        long startTime = System.currentTimeMillis();
        long endTime = startTime + examDurationSeconds * 1000L;

        for (int i = 0; i < total; i++) {
            long now = System.currentTimeMillis();
            if (now > endTime) {
                System.out.println("\n⏰ Time's up! Remaining questions will be auto-submitted as blank.");
                break;
            }

            Question q = questions.get(i);
            System.out.println("\nQ" + (i + 1) + ": " + q.question);
            System.out.println("A. " + q.optionA);
            System.out.println("B. " + q.optionB);
            System.out.println("C. " + q.optionC);
            System.out.println("D. " + q.optionD);

            System.out.print("Your answer (A/B/C/D) — time remaining: " + ((endTime - now) / 1000) + " s : ");
            String ans = sc.nextLine().trim().toUpperCase();

            if (ans.matches("[ABCD]")) {
                if (ans.equals(q.correctAnswer)) score++;
            } else {
                System.out.println("Recorded as no/invalid answer.");
            }
        }

        int percentage = (int) Math.round((score * 100.0) / total);
        System.out.println("\n----- Exam Completed -----");
        System.out.println("Student        : " + studentName);
        System.out.println("Total Qs       : " + total);
        System.out.println("Correct        : " + score);
        System.out.println("Score (%)      : " + percentage + "%");
        System.out.println("Result         : " + (percentage >= 50 ? "PASS" : "FAIL"));
    }

    private static void adminAddQuestions() {
        System.out.print("Enter question: ");
        String question = sc.nextLine();
        System.out.print("Enter option A: ");
        String optionA = sc.nextLine();
        System.out.print("Enter option B: ");
        String optionB = sc.nextLine();
        System.out.print("Enter option C: ");
        String optionC = sc.nextLine();
        System.out.print("Enter option D: ");
        String optionD = sc.nextLine();
        System.out.print("Enter correct answer (A/B/C/D): ");
        String correctAnswer = sc.nextLine().trim().toUpperCase();

        if (!correctAnswer.matches("[ABCD]")) {
            System.out.println("Invalid correct answer. Must be A, B, C, or D.");
            return;
        }

        questions.add(new Question(question, optionA, optionB, optionC, optionD, correctAnswer));
        System.out.println("✅ Question added successfully!");
    }

    private static void showAllQuestions() {
        if (questions.isEmpty()) {
            System.out.println("No questions have been added yet.");
            return;
        }

        System.out.println("\n--- Stored Questions ---");
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println((i + 1) + ". " + q.question);
            System.out.println("   A: " + q.optionA);
            System.out.println("   B: " + q.optionB);
            System.out.println("   C: " + q.optionC);
            System.out.println("   D: " + q.optionD);
            System.out.println("   Correct: " + q.correctAnswer);
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== ONLINE EXAMINATION SYSTEM =====");
            System.out.println("1. Admin - Add Questions");
            System.out.println("2. Admin - Set Exam Duration (seconds)");
            System.out.println("3. Student - Take Exam");
            System.out.println("4. Show All Questions (Admin)");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    adminAddQuestions();
                    break;
                case "2":
                    adminSetDuration();
                    break;
                case "3":
                    studentTakeExam();
                    break;
                case "4":
                    showAllQuestions();
                    break;
                case "5":
                    System.out.println("Exiting. Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }
}

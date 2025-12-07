
import java.io.*;
import java.util.*;

public class LibraryManager {

    Map<Integer, Book> books = new HashMap<>();
    Map<Integer, Member> members = new HashMap<>();

    Scanner sc = new Scanner(System.in);

    File bookFile = new File("books.txt");
    File memberFile = new File("members.txt");

    public LibraryManager() {
        loadFromFile();
    }

    // ---------------- FILE HANDLING ----------------

    public void saveToFile() {
        try {
            BufferedWriter bw1 = new BufferedWriter(new FileWriter(bookFile));
            for (Book b : books.values()) {
                bw1.write(b.getBookId() + "," + b.getTitle() + "," +
                          b.getAuthor() + "," + b.getCategory() + "," +
                          b.isIssued() + "\n");
            }
            bw1.close();

            BufferedWriter bw2 = new BufferedWriter(new FileWriter(memberFile));
            for (Member m : members.values()) {
                bw2.write(m.getMemberId() + "," + m.getName() + "," +
                          m.getEmail() + "," + m.getIssuedBooks() + "\n");
            }
            bw2.close();

        } catch (Exception e) {
            System.out.println("Error saving files: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        try {
            if (!bookFile.exists()) bookFile.createNewFile();
            if (!memberFile.exists()) memberFile.createNewFile();

            BufferedReader br1 = new BufferedReader(new FileReader(bookFile));
            String line;
            while ((line = br1.readLine()) != null) {
                String[] data = line.split(",");
                Book b = new Book(
                    Integer.parseInt(data[0]),
                    data[1], data[2], data[3]
                );
                if (Boolean.parseBoolean(data[4])) b.markAsIssued();
                books.put(b.getBookId(), b);
            }
            br1.close();

            BufferedReader br2 = new BufferedReader(new FileReader(memberFile));
            while ((line = br2.readLine()) != null) {
                String[] data = line.split(",");
                Member m = new Member(
                    Integer.parseInt(data[0]),
                    data[1], data[2]
                );
                members.put(m.getMemberId(), m);
            }
            br2.close();

        } catch (Exception e) {
            System.out.println("Error loading files: " + e.getMessage());
        }
    }

    // ---------------- OPERATIONS ----------------

    public void addBook() {
        System.out.print("Enter Book Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Author: ");
        String author = sc.nextLine();
        System.out.print("Enter Category: ");
        String category = sc.nextLine();

        int id = 100 + books.size() + 1;

        Book b = new Book(id, title, author, category);
        books.put(id, b);

        saveToFile();
        System.out.println("Book added with ID: " + id);
    }

    public void addMember() {
        System.out.print("Enter Member Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        int id = 200 + members.size() + 1;

        members.put(id, new Member(id, name, email));

        saveToFile();
        System.out.println("Member added with ID: " + id);
    }

    public void issueBook() {
        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();
        System.out.print("Enter Member ID: ");
        int memberId = sc.nextInt();
        sc.nextLine();

        if (!books.containsKey(bookId) || !members.containsKey(memberId)) {
            System.out.println("Invalid IDs!");
            return;
        }

        Book b = books.get(bookId);
        Member m = members.get(memberId);

        if (b.isIssued()) {
            System.out.println("Book already issued!");
            return;
        }

        b.markAsIssued();
        m.addIssuedBook(bookId);

        saveToFile();
        System.out.println("Book issued successfully.");
    }

    public void returnBook() {
        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();
        System.out.print("Enter Member ID: ");
        int memberId = sc.nextInt();
        sc.nextLine();

        Book b = books.get(bookId);
        Member m = members.get(memberId);

        b.markAsReturned();
        m.returnIssuedBook(bookId);

        saveToFile();
        System.out.println("Book returned successfully.");
    }

    public void searchBooks() {
        System.out.print("Search by (title/author/category): ");
        String keyword = sc.nextLine().toLowerCase();

        for (Book b : books.values()) {
            if (b.getTitle().toLowerCase().contains(keyword) ||
                b.getAuthor().toLowerCase().contains(keyword) ||
                b.getCategory().toLowerCase().contains(keyword)) {
                b.displayBookDetails();
            }
        }
    }

    public void sortBooks() {
        System.out.println("1. Sort by Title");
        System.out.println("2. Sort by Author");

        int ch = sc.nextInt();
        sc.nextLine();

        List<Book> list = new ArrayList<>(books.values());

        if (ch == 1) {
            Collections.sort(list);
        } else if (ch == 2) {
            list.sort(Comparator.comparing(Book::getAuthor));
        }

        for (Book b : list) b.displayBookDetails();
    }
}

/*
 * Member.java
 * Represents a library member.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Member {
    private int memberId;
    private String name;
    private String email;
    private List<Integer> issuedBooks; // list of book IDs

    public Member(int memberId, String name, String email, List<Integer> issuedBooks) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.issuedBooks = issuedBooks == null ? new ArrayList<>() : issuedBooks;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Integer> getIssuedBooks() {
        return issuedBooks;
    }

    public void addIssuedBook(int bookId) {
        if (!issuedBooks.contains(bookId)) {
            issuedBooks.add(bookId);
        }
    }

    public boolean returnIssuedBook(int bookId) {
        return issuedBooks.remove((Integer) bookId);
    }

    public void displayMemberDetails() {
        System.out.println("ID: " + memberId + ", Name: " + name + ", Email: " + email + ", IssuedBooks: " + issuedBooks);
    }

    @Override
    public String toString() {
        // Format: id|name|email|book1,book2,...
        StringJoiner sj = new StringJoiner(",");
        for (Integer b : issuedBooks) sj.add(String.valueOf(b));
        return memberId + "|" + name + "|" + email + "|" + sj.toString();
    }

    public static Member fromString(String line) {
        // Expected: id|name|email|b1,b2
        String[] parts = line.split("\\|", -1);
        if (parts.length < 4) return null;
        try {
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            String email = parts[2];
            List<Integer> list = new ArrayList<>();
            if (!parts[3].isEmpty()) {
                String[] ids = parts[3].split(",");
                for (String s : ids) {
                    try {
                        list.add(Integer.parseInt(s));
                    } catch (NumberFormatException ignored) {}
                }
            }
            return new Member(id, name, email, list);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

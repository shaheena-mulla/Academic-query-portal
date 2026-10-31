import java.util.*;

public class Main {
    private static TicketManager ticketManager = new TicketManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Academic Query Resolution Portal ===");
        System.out.println("Java Backend Demonstration");
        
        // Sample data for demonstration
        sampleData();
        
        while (true) {
            showMainMenu();
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    createNewTicket();
                    break;
                case 2:
                    viewAllTickets();
                    break;
                case 3:
                    viewTicketDetails();
                    break;
                case 4:
                    updateTicketStatus();
                    break;
                case 5:
                    addCommentToTicket();
                    break;
                case 6:
                    escalateTicket();
                    break;
                case 7:
                    showDashboard();
                    break;
                case 8:
                    showDepartmentSummary();
                    break;
                case 0:
                    System.out.println("Thank you for using the Academic Query Portal!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
    
    private static void showMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Create New Ticket");
        System.out.println("2. View All Tickets");
        System.out.println("3. View Ticket Details");
        System.out.println("4. Update Ticket Status");
        System.out.println("5. Add Comment to Ticket");
        System.out.println("6. Escalate Ticket");
        System.out.println("7. Show Dashboard");
        System.out.println("8. Show Department Summary");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }
    
    private static void sampleData() {
        // Create some sample tickets for demonstration
        ticketManager.createTicket(
            "Course Registration Issue", 
            "Course Registration", 
            "Registrar's Office", 
            "Unable to register for COMP303 due to prerequisites error", 
            "screenshot.png"
        );
        
        ticketManager.createTicket(
            "Grade Discrepancy", 
            "Grades and Transcripts", 
            "Examination Office", 
            "My final grade in MATH201 doesn't match my expected score", 
            "transcript.pdf"
        );
        
        ticketManager.createTicket(
            "Scholarship Application", 
            "Fees and Scholarships", 
            "Finance Office", 
            "Need assistance with scholarship application process", 
            null
        );
    }
    
    private static void createNewTicket() {
        System.out.println("\n=== CREATE NEW TICKET ===");
        
        System.out.print("Enter ticket title: ");
        String title = scanner.nextLine();
        
        System.out.println("Categories:");
        String[] categories = {"Course Registration", "Examinations", "Grades and Transcripts", "Fees and Scholarships", "Other"};
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
        System.out.print("Choose category (1-5): ");
        int catChoice = getIntInput();
        String category = (catChoice >= 1 && catChoice <= categories.length) ? categories[catChoice - 1] : "Other";
        
        System.out.println("Departments:");
        String[] departments = {"Registrar's Office", "Examination Office", "Finance Office", "Academic Advising"};
        for (int i = 0; i < departments.length; i++) {
            System.out.println((i + 1) + ". " + departments[i]);
        }
        System.out.print("Choose department (1-4): ");
        int deptChoice = getIntInput();
        String department = (deptChoice >= 1 && deptChoice <= departments.length) ? departments[deptChoice - 1] : departments[0];
        
        System.out.print("Enter query details: ");
        String description = scanner.nextLine();
        
        System.out.print("Enter attachment filename (or press Enter to skip): ");
        String attachment = scanner.nextLine();
        if (attachment.trim().isEmpty()) {
            attachment = null;
        }
        
        Ticket ticket = ticketManager.createTicket(title, category, department, description, attachment);
        System.out.println("Ticket created successfully! ID: " + ticket.getId());
    }
    
    private static void viewAllTickets() {
        System.out.println("\n=== ALL TICKETS ===");
        List<Ticket> tickets = ticketManager.getAllTickets();
        
        if (tickets.isEmpty()) {
            System.out.println("No tickets found.");
            return;
        }
        
        System.out.printf("%-15s %-30s %-20s %-20s %-15s%n", "ID", "Title", "Category", "Department", "Status");
        System.out.println("-".repeat(105));
        
        for (Ticket ticket : tickets) {
            System.out.printf("%-15s %-30s %-20s %-20s %-15s%n", 
                ticket.getId(),
                truncate(ticket.getTitle(), 30),
                truncate(ticket.getCategory(), 20),
                truncate(ticket.getDepartment(), 20),
                ticket.getStatus()
            );
        }
    }
    
    private static void viewTicketDetails() {
        System.out.println("\n=== VIEW TICKET DETAILS ===");
        System.out.print("Enter ticket ID: ");
        String id = scanner.nextLine();
        
        Ticket ticket = ticketManager.getTicketById(id);
        if (ticket == null) {
            System.out.println("Ticket not found.");
            return;
        }
        
        System.out.println("\n=== TICKET DETAILS ===");
        System.out.println("ID: " + ticket.getId());
        System.out.println("Title: " + ticket.getTitle());
        System.out.println("Category: " + ticket.getCategory());
        System.out.println("Department: " + ticket.getDepartment());
        System.out.println("Status: " + ticket.getStatus());
        System.out.println("Description: " + ticket.getDescription());
        System.out.println("Attachment: " + ticket.getAttachment());
        System.out.println("Created: " + ticket.getCreatedAt());
        System.out.println("Last Updated: " + ticket.getUpdatedAt());
        
        System.out.println("\nComments:");
        List<String> comments = ticket.getComments();
        if (comments.isEmpty()) {
            System.out.println("No comments yet.");
        } else {
            for (int i = 0; i < comments.size(); i++) {
                System.out.println((i + 1) + ". " + comments.get(i));
            }
        }
        
        System.out.println("\nHistory:");
        List<String> history = ticket.getHistory();
        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + ". " + history.get(i));
        }
    }
    
    private static void updateTicketStatus() {
        System.out.println("\n=== UPDATE TICKET STATUS ===");
        System.out.print("Enter ticket ID: ");
        String id = scanner.nextLine();
        
        Ticket ticket = ticketManager.getTicketById(id);
        if (ticket == null) {
            System.out.println("Ticket not found.");
            return;
        }
        
        System.out.println("Current status: " + ticket.getStatus());
        System.out.println("Available statuses:");
        String[] statuses = {"Submitted", "In Progress", "Waiting for Student", "Resolved", "Escalated"};
        for (int i = 0; i < statuses.length; i++) {
            System.out.println((i + 1) + ". " + statuses[i]);
        }
        System.out.print("Choose new status (1-5): ");
        int statusChoice = getIntInput();
        String newStatus = (statusChoice >= 1 && statusChoice <= statuses.length) ? statuses[statusChoice - 1] : ticket.getStatus();
        
        if (ticketManager.updateTicketStatus(id, newStatus)) {
            System.out.println("Status updated successfully!");
        } else {
            System.out.println("Failed to update status.");
        }
    }
    
    private static void addCommentToTicket() {
        System.out.println("\n=== ADD COMMENT TO TICKET ===");
        System.out.print("Enter ticket ID: ");
        String id = scanner.nextLine();
        
        Ticket ticket = ticketManager.getTicketById(id);
        if (ticket == null) {
            System.out.println("Ticket not found.");
            return;
        }
        
        System.out.print("Enter your comment: ");
        String comment = scanner.nextLine();
        
        if (ticketManager.addCommentToTicket(id, comment)) {
            System.out.println("Comment added successfully!");
        } else {
            System.out.println("Failed to add comment.");
        }
    }
    
    private static void escalateTicket() {
        System.out.println("\n=== ESCALATE TICKET ===");
        System.out.print("Enter ticket ID: ");
        String id = scanner.nextLine();
        
        Ticket ticket = ticketManager.getTicketById(id);
        if (ticket == null) {
            System.out.println("Ticket not found.");
            return;
        }
        
        System.out.print("Are you sure you want to escalate this ticket? (y/N): ");
        String confirm = scanner.nextLine();
        
        if (confirm.toLowerCase().startsWith("y")) {
            if (ticketManager.escalateTicket(id)) {
                System.out.println("Ticket escalated successfully!");
            } else {
                System.out.println("Failed to escalate ticket.");
            }
        } else {
            System.out.println("Escalation cancelled.");
        }
    }
    
    private static void showDashboard() {
        System.out.println("\n=== DASHBOARD ===");
        Map<String, Integer> stats = ticketManager.getTicketStatistics();
        
        System.out.println("Total Tickets: " + stats.get("total"));
        System.out.println("Open Tickets: " + stats.get("open"));
        System.out.println("Resolved Tickets: " + stats.get("resolved"));
        System.out.println("Escalated Tickets: " + stats.get("escalated"));
        
        System.out.println("\nRecent Tickets:");
        List<Ticket> tickets = ticketManager.getAllTickets();
        int count = 0;
        for (int i = tickets.size() - 1; i >= 0 && count < 5; i--) {
            Ticket ticket = tickets.get(i);
            System.out.printf("%-15s %-30s %-20s %-15s%n", 
                ticket.getId(),
                truncate(ticket.getTitle(), 30),
                truncate(ticket.getDepartment(), 20),
                ticket.getStatus()
            );
            count++;
        }
    }
    
    private static void showDepartmentSummary() {
        System.out.println("\n=== DEPARTMENT SUMMARY ===");
        Map<String, Integer> summary = ticketManager.getDepartmentSummary();
        
        for (Map.Entry<String, Integer> entry : summary.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " ticket(s)");
        }
    }
    
    private static int getIntInput() {
        try {
            int value = Integer.parseInt(scanner.nextLine());
            return value;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static String truncate(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }
}
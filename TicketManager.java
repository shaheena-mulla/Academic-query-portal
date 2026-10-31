import java.util.*;
import java.io.*;

public class TicketManager {
    private List<Ticket> tickets;
    private static final String DATA_FILE = "tickets.dat";

    public TicketManager() {
        this.tickets = new ArrayList<>();
        loadTickets();
    }

    // Create a new ticket
    public Ticket createTicket(String title, String category, String department, String description, String attachment) {
        Ticket ticket = new Ticket(title, category, department, description, attachment);
        tickets.add(ticket);
        saveTickets();
        return ticket;
    }

    // Get all tickets
    public List<Ticket> getAllTickets() {
        return new ArrayList<>(tickets); // Return a copy to prevent external modification
    }

    // Get ticket by ID
    public Ticket getTicketById(String id) {
        for (Ticket ticket : tickets) {
            if (ticket.getId().equals(id)) {
                return ticket;
            }
        }
        return null;
    }

    // Get tickets by department
    public List<Ticket> getTicketsByDepartment(String department) {
        List<Ticket> result = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getDepartment().equals(department)) {
                result.add(ticket);
            }
        }
        return result;
    }

    // Get tickets by status
    public List<Ticket> getTicketsByStatus(String status) {
        List<Ticket> result = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getStatus().equals(status)) {
                result.add(ticket);
            }
        }
        return result;
    }

    // Update ticket status
    public boolean updateTicketStatus(String ticketId, String newStatus) {
        Ticket ticket = getTicketById(ticketId);
        if (ticket != null) {
            ticket.setStatus(newStatus);
            saveTickets();
            return true;
        }
        return false;
    }

    // Reassign ticket to a different department
    public boolean reassignTicket(String ticketId, String newDepartment) {
        Ticket ticket = getTicketById(ticketId);
        if (ticket != null) {
            ticket.setDepartment(newDepartment);
            saveTickets();
            return true;
        }
        return false;
    }

    // Add comment to ticket
    public boolean addCommentToTicket(String ticketId, String comment) {
        Ticket ticket = getTicketById(ticketId);
        if (ticket != null) {
            ticket.addComment(comment);
            saveTickets();
            return true;
        }
        return false;
    }

    // Escalate ticket
    public boolean escalateTicket(String ticketId) {
        Ticket ticket = getTicketById(ticketId);
        if (ticket != null) {
            ticket.escalate();
            saveTickets();
            return true;
        }
        return false;
    }

    // Get ticket statistics
    public Map<String, Integer> getTicketStatistics() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("total", tickets.size());
        
        int open = 0, resolved = 0, escalated = 0;
        for (Ticket ticket : tickets) {
            switch (ticket.getStatus()) {
                case "Submitted":
                case "In Progress":
                case "Waiting for Student":
                    open++;
                    break;
                case "Resolved":
                    resolved++;
                    break;
                case "Escalated":
                    escalated++;
                    break;
            }
        }
        
        stats.put("open", open);
        stats.put("resolved", resolved);
        stats.put("escalated", escalated);
        
        return stats;
    }

    // Get department summary
    public Map<String, Integer> getDepartmentSummary() {
        Map<String, Integer> summary = new HashMap<>();
        String[] departments = {
            "Registrar's Office",
            "Examination Office", 
            "Finance Office", 
            "Academic Advising"
        };
        
        for (String department : departments) {
            int count = 0;
            for (Ticket ticket : tickets) {
                if (ticket.getDepartment().equals(department)) {
                    count++;
                }
            }
            summary.put(department, count);
        }
        
        return summary;
    }

    // Save tickets to file
    private void saveTickets() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(tickets);
        } catch (IOException e) {
            System.err.println("Error saving tickets: " + e.getMessage());
        }
    }

    // Load tickets from file
    @SuppressWarnings("unchecked")
    private void loadTickets() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            tickets = (List<Ticket>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, that's okay
            tickets = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading tickets: " + e.getMessage());
            tickets = new ArrayList<>();
        }
    }
}
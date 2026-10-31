import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ticket {
    private String id;
    private String title;
    private String category;
    private String department;
    private String description;
    private String attachment;
    private String status;
    private List<String> comments;
    private List<String> history;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Ticket(String title, String category, String department, String description, String attachment) {
        this.id = generateId();
        this.title = title;
        this.category = category;
        this.department = department;
        this.description = description;
        this.attachment = attachment != null ? attachment : "None";
        this.status = "Submitted";
        this.comments = new ArrayList<>();
        this.history = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        
        // Add initial history entry
        this.history.add("Ticket submitted at " + formatDateTime(createdAt));
    }

    // Generate a unique ticket ID
    private String generateId() {
        return "AQ-" + System.currentTimeMillis();
    }

    // Format datetime for display
    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        this.updatedAt = LocalDateTime.now();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        String oldDepartment = this.department;
        this.department = department;
        this.updatedAt = LocalDateTime.now();
        this.history.add("Reassigned from " + oldDepartment + " to " + department + " at " + formatDateTime(updatedAt));
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
        this.updatedAt = LocalDateTime.now();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        String oldStatus = this.status;
        this.status = status;
        this.updatedAt = LocalDateTime.now();
        this.history.add("Status changed from " + oldStatus + " to " + status + " at " + formatDateTime(updatedAt));
    }

    public List<String> getComments() {
        return new ArrayList<>(comments); // Return a copy to prevent external modification
    }

    public List<String> getHistory() {
        return new ArrayList<>(history); // Return a copy to prevent external modification
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Add a comment to the ticket
    public void addComment(String comment) {
        if (comment != null && !comment.trim().isEmpty()) {
            this.comments.add(comment);
            this.updatedAt = LocalDateTime.now();
            this.history.add("Comment added at " + formatDateTime(updatedAt) + ": " + comment);
        }
    }

    // Escalate the ticket
    public void escalate() {
        this.setStatus("Escalated");
        this.history.add("Ticket escalated at " + formatDateTime(updatedAt));
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", department='" + department + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
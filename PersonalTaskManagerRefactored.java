import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PersonalTaskManagerRefactored {

    private static final String DB_FILE_PATH = "tasks_database.json";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public JSONObject addNewTask(String title, String description,
                                 String dueDateStr, String priorityLevel,
                                 boolean isRecurring) {

        if (!isValidTitle(title) || !isValidDate(dueDateStr) || !isValidPriority(priorityLevel)) {
            return null;
        }

        LocalDate dueDate = LocalDate.parse(dueDateStr, DATE_FORMATTER);
        JSONArray tasks = loadTasksFromDb();

        if (isDuplicateTask(tasks, title, dueDate)) {
            return null;
        }

        JSONObject newTask = createTask(title, description, dueDate, priorityLevel, isRecurring);
        tasks.add(newTask);
        saveTasksToDb(tasks);

        System.out.println(String.format("Đã thêm nhiệm vụ mới thành công với ID: %s", newTask.get("id")));
        return newTask;
    }

    private boolean isValidTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            System.out.println("Lỗi: Tiêu đề không được để trống.");
            return false;
        }
        return true;
    }

    private boolean isValidDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            System.out.println("Lỗi: Ngày đến hạn không được để trống.");
            return false;
        }
        try {
            LocalDate.parse(dateStr, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("Lỗi: Ngày đến hạn không hợp lệ. Vui lòng sử dụng định dạng YYYY-MM-DD.");
            return false;
        }
    }

    private boolean isValidPriority(String priority) {
        String[] validPriorities = {"Thấp", "Trung bình", "Cao"};
        for (String valid : validPriorities) {
            if (valid.equals(priority)) {
                return true;
            }
        }
        System.out.println("Lỗi: Mức độ ưu tiên không hợp lệ. Vui lòng chọn từ: Thấp, Trung bình, Cao.");
        return false;
    }

    private boolean isDuplicateTask(JSONArray tasks, String title, LocalDate dueDate) {
        for (Object obj : tasks) {
            JSONObject existingTask = (JSONObject) obj;
            String existingTitle = (String) existingTask.get("title");
            String existingDueDate = (String) existingTask.get("due_date");

            if (existingTitle.equalsIgnoreCase(title) &&
                existingDueDate.equals(dueDate.format(DATE_FORMATTER))) {
                System.out.println(String.format("Lỗi: Nhiệm vụ '%s' đã tồn tại với cùng ngày đến hạn.", title));
                return true;
            }
        }
        return false;
    }

    private JSONObject createTask(String title, String description,
                                  LocalDate dueDate, String priority,
                                  boolean isRecurring) {
        JSONObject task = new JSONObject();
        task.put("id", UUID.randomUUID().toString());
        task.put("title", title);
        task.put("description", description);
        task.put("due_date", dueDate.format(DATE_FORMATTER));
        task.put("priority", priority);
        task.put("status", "Chưa hoàn thành");

        String now = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        task.put("created_at", now);
        task.put("last_updated_at", now);
        task.put("is_recurring", isRecurring);

        if (isRecurring) {
            task.put("recurrence_pattern", "Chưa xác định");
        }

        return task;
    }

    private JSONArray loadTasksFromDb() {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(DB_FILE_PATH)) {
            Object obj = parser.parse(reader);
            if (obj instanceof JSONArray) {
                return (JSONArray) obj;
            }
        } catch (IOException | ParseException e) {
            System.err.println("Lỗi khi đọc file database: " + e.getMessage());
        }
        return new JSONArray();
    }

    private void saveTasksToDb(JSONArray tasksData) {
        try (FileWriter file = new FileWriter(DB_FILE_PATH)) {
            file.write(tasksData.toJSONString());
            file.flush();
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi vào file database: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        PersonalTaskManagerRefactored manager = new PersonalTaskManagerRefactored();

        System.out.println("\nThêm nhiệm vụ hợp lệ:");
        manager.addNewTask(
            "Mua sách",
            "Sách Công nghệ phần mềm.",
            "2025-07-20",
            "Cao",
            false
        );

        System.out.println("\nThêm nhiệm vụ trùng lặp:");
        manager.addNewTask(
            "Mua sách",
            "Sách Công nghệ phần mềm.",
            "2025-07-20",
            "Cao",
            false
        );

        System.out.println("\nThêm nhiệm vụ lặp lại:");
        manager.addNewTask(
            "Tập thể dục",
            "Tập gym 1 tiếng.",
            "2025-07-21",
            "Trung bình",
            true 
        );

        System.out.println("\nThêm nhiệm vụ với tiêu đề rỗng:");
        manager.addNewTask(
            "",
            "Nhiệm vụ không có tiêu đề.",
            "2025-07-22",
            "Thấp",
            false
        );
    }
}

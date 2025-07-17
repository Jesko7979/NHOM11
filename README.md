# Personal Task Manager - Refactored Version

##  Mục tiêu refactor

Nhằm cải thiện chất lượng mã nguồn và đảm bảo phần mềm dễ mở rộng, nhóm đã tiến hành refactor đoạn mã ban đầu bằng cách áp dụng các nguyên tắc thiết kế phần mềm:

- **KISS (Keep It Simple, Stupid)**: Giữ cho mã đơn giản, dễ hiểu.
- **DRY (Don't Repeat Yourself)**: Tránh lặp lại mã bằng cách tái sử dụng phương thức.
- **YAGNI (You Aren’t Gonna Need It)**: Loại bỏ những phần chưa cần thiết.

---

##  Các bước thực hiện

1. **Phân tích code gốc** (`PersonalTaskManagerViolations.java`)
   - Gồm các đoạn mã lặp lại, xử lý logic chưa tối ưu, chứa các thành phần dư thừa.

2. **Áp dụng refactor**
   - Tách các logic trùng lặp thành phương thức riêng.
   - Đơn giản hóa câu lệnh điều kiện, loại bỏ biến không dùng.
   - Bỏ các chức năng chưa được sử dụng hoặc chưa cần thiết.

3. **Kiểm thử**
   - Đảm bảo chương trình sau khi refactor vẫn chạy đúng chức năng gốc.

---

##  Cấu trúc file

| File                                | Mô tả                                                                 |
|-------------------------------------|------------------------------------------------------------------------|
| `PersonalTaskManagerViolations.java` | Phiên bản chưa tối ưu, vi phạm các nguyên tắc KISS, DRY, YAGNI         |
| `PersonalTaskManagerRefactored.java` | Phiên bản đã được refactor, mã rõ ràng, dễ bảo trì, có tính mở rộng tốt |

---

##  Nhánh Git

- **main**: chứa mã nguồn ban đầu, chưa được refactor.
- **feature/refactor-code**: chứa mã đã được tối ưu, dùng để đánh giá và so sánh.

---

## 👨‍💻 Tác giả & Đóng góp

- Nhóm 11 - Dự án môn học: Nhập môn Công nghệ phần mềm
- Liên hệ: [GitHub - Jesko7979](https://github.com/Jesko7979/NHOM11)

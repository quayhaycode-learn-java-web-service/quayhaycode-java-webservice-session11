# Báo cáo Phân tích Chiến lược Kiểm thử: Tối ưu hóa Coverage

## 1. Tại sao Line Coverage cao vẫn tiềm ẩn lỗi logic?
Line Coverage chỉ đo lường xem dòng mã nào đã được trình biên dịch/thông dịch đi qua trong quá trình chạy test. Nó không quan tâm đến:

* **Trạng thái của các biểu thức điều kiện:** Một dòng chứa `if (A && B)` chỉ cần thực hiện sao cho câu lệnh bên trong được chạy, nhưng có thể test chưa bao giờ kiểm tra trường hợp A đúng nhưng B sai, hoặc ngược lại.
* **Sự kết hợp của các luồng:** Một hàm có thể thực hiện 10 dòng mã, nhưng nếu nó có 4 nhánh rẽ, bộ test chỉ cần đi qua 1 nhánh cũng đã đạt 100% Line Coverage cho các dòng đó.
* **Các kịch bản biên (Edge cases):** Các giá trị đầu vào đặc biệt hoặc các tình huống ngoại lệ (Exception) thường không làm thay đổi số dòng thực thi nhưng lại thay đổi hoàn toàn kết quả logic.

## 2. Phân biệt Line Coverage và Branch Coverage

| Đặc điểm | Line Coverage | Branch Coverage |
| :--- | :--- | :--- |
| **Định nghĩa** | Tỷ lệ phần trăm các dòng code đã được thực thi. | Tỷ lệ phần trăm các nhánh (true/false) của cấu trúc điều khiển đã được thực thi. |
| **Trọng tâm** | Đo lường tính bao phủ (cái gì đã chạy). | Đo lường tính đúng đắn của logic (các con đường nào đã đi qua). |
| **Độ tin cậy** | Thấp, dễ bị đánh lừa bởi mã nguồn đơn giản. | Cao, đảm bảo các điểm quyết định đều được kiểm soát. |

## 3. Tại sao Branch Coverage lại quan trọng hơn?
Trong hệ thống quản lý đơn hàng, các lỗi "nghiệp vụ" thường nằm ở điều kiện quyết định (ví dụ: *"Nếu đơn hàng > 1 triệu VÀ là khách hàng thân thiết thì giảm giá 10%"*).

Line Coverage có thể xác nhận bạn đã test tính năng giảm giá, nhưng Branch Coverage mới là thứ đảm bảo bạn đã test cả trường hợp khách không thân thiết hoặc đơn hàng không đủ giá trị. Việc tập trung vào Branch Coverage ép đội ngũ test phải tư duy về "lối rẽ", từ đó phát hiện các bug logic tại các điểm ra quyết định.

## 4. Ví dụ minh họa

### Ví dụ 1: Câu lệnh điều kiện phức hợp (Java)
```java
// Logic: Nếu là Admin hoặc Đơn hàng có giá trị > 100 thì được duyệt
public void approveOrder(boolean isAdmin, int orderValue) {
    if (isAdmin || orderValue > 100) { // Dòng A
        System.out.println("Đơn hàng được duyệt"); // Dòng B
    }
}
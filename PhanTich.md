# Phân Tích Hệ Thống Quản Lý Tour Du Lịch

## 1. Thiết kế Kiến trúc & Logic

### Liệt kê công cụ/Annotation
- **Mã Tour (tourCode):** Bắt buộc bắt đầu bằng `VN_` hoặc `INT_` theo sau là 5 chữ số. 
  - Sử dụng custom field-level annotation (`@ValidTourCode`). (Có thể dùng `@Pattern` chuẩn, nhưng tạo Custom sẽ đúng tinh thần yêu cầu hơn).
- **Giá tiền (adultPrice, childPrice):** Lớn hơn 0, giá trẻ em phải <= giá người lớn.
  - `@Min(1)` hoặc `@Positive` của Jakarta Validation cho từng trường.
  - Sử dụng custom class-level annotation (`@ValidTourPrices`) gắn trên `TourDto` để so sánh `adultPrice` và `childPrice`.
- **Lịch trình (startDate, endDate):** Ngày khởi hành >= hôm nay, ngày kết thúc > khởi hành.
  - `@FutureOrPresent` cho `startDate`.
  - Sử dụng custom class-level annotation (`@ValidTourDates`) gắn trên `TourDto` để kiểm tra logic `endDate > startDate`.

### Luồng dữ liệu (Flow)
1. **User ấn "Submit Form" trên trình duyệt:** Dữ liệu form được submit dưới dạng POST request lên server.
2. **Qua Controller:** `TourController` nhận request vào phương thức xử lý POST với tham số `@ModelAttribute("tour") @Valid TourDto tourDto`.
3. **Bị chặn bởi Validator:** Các Annotation trên `TourDto` (bao gồm cả chuẩn và custom) sẽ được kích hoạt để kiểm tra giá trị. Nếu có bất kỳ điều kiện nào bị vi phạm, một đổi tượng lỗi sẽ được thêm vào.
4. **Trả về Thymeleaf kèm BindingResult:** Trong Controller, kiểm tra `bindingResult.hasErrors()`. Nếu `true`, trả về lại tên view `create-tour` (không redirect). Trang Thymeleaf lấy dữ liệu lỗi từ đối tượng `tour` (thông qua `th:errors`) và hiển thị thông báo đỏ lên UI, đồng thời giữ nguyên các dữ liệu người dùng đã nhập bằng `th:field`.

## 3. Kịch bản Test rủi ro (Test Cases)

1. **Test Case 1: Vượt quá giới hạn kiểu dữ liệu giá tiền**
   - **Đầu vào độc hại:** `adultPrice` = `999999999999999999` (vượt kích cỡ int/double), `childPrice` = `-1`.
   - **Cách chống chịu:** Server sử dụng kiểu dữ liệu `Long` hoặc `BigDecimal` (tránh overflow cơ bản). `@Positive` chặn các số âm mà không cần logic so sánh. Spring framework tự handle exception định dạng số (TypeMismatch) khi bind dữ liệu và ném lỗi `BindingResult` thay vì Crash 500.

2. **Test Case 2: Bỏ qua / Gửi tham số null**
   - **Đầu vào độc hại:** Xóa sạch các field trong request payload bằng các công cụ Postman/Burp Suite (không gửi `startDate`, `endDate`, `tourCode`).
   - **Cách chống chịu:** Các annotation `@NotNull` hoặc `@NotBlank` ở mức field sẽ chặn ngay lập tức. Dữ liệu null sẽ không đi vào các Custom Class-Level Validator (vì có điều kiện check null trước khi so sánh), tránh gây gián đoạn thuật toán hoặc quăng NullPointerException làm ứng dụng chết (500).

3. **Test Case 3: Ngày tháng không hợp lệ (Leap year fake, Format sai)**
   - **Đầu vào độc hại:** `startDate` = `2024-02-30` (ngày không tồn tại).
   - **Cách chống chịu:** Framework binding của Spring (`@DateTimeFormat`) tự động phát hiện chuỗi thời gian không thể chuyển sang `LocalDate` và ghi nhận một `FieldError` (typeMismatch) trong `BindingResult` ngay trước khi vào Custom Validation. Server trả về cảnh báo định dạng ngày trên UI thay vì bị Crash Server do lỗi parse time.


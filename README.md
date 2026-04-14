# Demo Student Form (Spring MVC + Thymeleaf)

## Chuc nang
- Hien thi form them sinh vien tai `/students/add`
- Validate du lieu bang Jakarta Validation (`@Valid`)
- Hien thi loi ngay tren form neu du lieu khong hop le
- Hien thi thong bao thanh cong sau khi submit hop le

## Cau truc chinh
- `src/main/java/ra/edu/demo/config/WebConfig.java`: Cau hinh Spring MVC + Thymeleaf + Validator
- `src/main/java/ra/edu/demo/config/WebInit.java`: Khoi tao `DispatcherServlet`
- `src/main/java/ra/edu/demo/controller/StudentController.java`: Xu ly GET/POST form
- `src/main/java/ra/edu/demo/model/dto/Student.java`: DTO va rule validate
- `src/main/webapp/WEB-INF/views/insertStudent.html`: Giao dien form

## Chay nhanh
Yeu cau Java 21.

```powershell
.\gradlew.bat clean test
.\gradlew.bat clean war
```

Sau khi build, file WAR nam o `build/libs/`.
Deploy WAR len Tomcat 10.1+ va mo URL:
- `http://localhost:8080/<ten-war>/students/add`


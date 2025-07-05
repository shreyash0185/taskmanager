# 📝 Task Manager API Documentation

**Base URL:** `/api/tasks`  
**Content-Type:** `application/json`  
**Status Codes:** `200`, `204`, `400`, `404`

---

## 📌 Endpoints

---

### 1. Create a Task

- **Method:** `POST`
- **URL:** `/api/tasks/create`
- **Request Body:**

```json
{
  "title": "Complete Unit Tests",
  "description": "Write unit tests for all layers",
  "priority": "HIGH",
  "status": "PENDING",
  "dueDate": "2025-12-01"
}
```

- **Response:**
```json
"Task created successfully"
```

---

### 2. Update a Task

- **Method:** `POST`
- **URL:** `/api/tasks/update/{id}`
- **Path Variable:** `id` – Long
- **Request Body:**

```json
{
  "title": "Update Task Title",
  "description": "Updated task details",
  "priority": "MEDIUM",
  "status": "IN_PROGRESS",
  "dueDate": "2025-12-15"
}
```

- **Response:**
```json
"Task updated successfully"
```

---

### 3. Delete a Task

- **Method:** `POST`
- **URL:** `/api/tasks/delete/{id}`
- **Path Variable:** `id` – Long

- **Response:**
```json
"Task deleted successfully"
```

---

### 4. Get Task by ID

- **Method:** `POST`
- **URL:** `/api/tasks/get/{id}`
- **Path Variable:** `id` – Long

- **Response:**
```json
{
  "id": 1,
  "title": "Complete Spring Boot API",
  "description": "Implement controller and service logic",
  "priority": "HIGH",
  "status": "PENDING",
  "dueDate": "2025-12-01"
}
```

---

### 5. Get All Tasks

- **Method:** `POST`
- **URL:** `/api/tasks/getAll`

- **Response:**
```json
[
  {
    "id": 1,
    "title": "Complete Spring Boot API",
    "description": "Implement controller and service logic",
    "priority": "HIGH",
    "status": "PENDING",
    "dueDate": "2025-12-01"
  },
  {
    "id": 2,
    "title": "Fix Bugs",
    "description": "Fix all critical bugs",
    "priority": "LOW",
    "status": "COMPLETED",
    "dueDate": "2025-11-15"
  }
]
```

---

## 🧾 Enums

### TaskPriority
- `LOW`
- `MEDIUM`
- `HIGH`

### TaskStatus
- `PENDING`
- `IN_PROGRESS`
- `COMPLETED`

---

## ✅ Status Code Reference

| Code | Meaning             |
|------|---------------------|
| 200  | OK                  |
| 204  | No Content          |
| 400  | Bad Request         |
| 404  | Not Found           |

---

## 📎 Notes

- All date fields must be in `YYYY-MM-DD` format.
- Make sure to validate input fields. Missing or invalid fields will result in a `400 Bad Request`.

---

## 🧪 Author

**Shreyash**  
_Task Manager API – Spring Boot Project (2025)_

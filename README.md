# Waste Sorting Application

## Project Overview

The Waste Sorting Application is a Spring Boot-based REST API that facilitates waste management by providing functionalities such as waste category lookup, disposal guidelines retrieval, and recycling tips display.
The application aims to help users make environmentally conscious decisions by providing accurate and easy-to-access information about waste disposal and recycling.

---

## Basic Functionality

The application exposes several RESTful API endpoints, e.g. `/api/waste-categories` endpoint allows user to:
- Retrieve all waste categories
- Get details of a specific waste category by ID
- Add a new waste category
- Update an existing waste category
- Delete a waste category

Similar functionalities are available for disposal guidelines and recycling tips.

---

## Dependencies

The following dependencies are used in this project:

- **Spring Boot Starter Web**: Provides the core dependencies for building web applications, including RESTful services.
- **Spring Boot Starter Data JPA**: Simplifies the implementation of JPA-based repositories.
- **Spring Boot Starter Validation**: Provides validation support for Java Bean validation.
- **Spring Boot Starter Test**: Includes testing libraries such as JUnit, Mockito, and Spring TestContext Framework.
- **H2 Database**: A lightweight in-memory database used for testing and development.
- **Spring Boot Maven Plugin**: Supports building and running Spring Boot applications as executable JARs or WARs.

---

## API Endpoints

### Waste Categories
- **GET `/api/waste-categories`**: Retrieve all waste categories.
- **GET `/api/waste-categories/{id}`**: Retrieve a specific waste category by ID.
- **POST `/api/waste-categories`**: Add a new waste category.
    - **Request Body**:
      ```json
      {
          "category": "string"
      }
      ```
- **PUT `/api/waste-categories/{id}`**: Update an existing waste category.
    - **Request Body**:
      ```json
      {
          "category": "string"
      }
      ```
- **DELETE `/api/waste-categories/{id}`**: Delete a waste category.

### Disposal Guidelines
- **GET `/api/disposal-guidelines`**: Retrieve all disposal guidelines.
- **GET `/api/disposal-guidelines/{id}`**: Retrieve a specific disposal guideline by ID.
- **POST `/api/disposal-guidelines`**: Add a new disposal guideline.
    - **Request Body**:
      ```json
      {
          "guideline": "string"
      }
      ```
- **PUT `/api/disposal-guidelines/{id}`**: Update an existing disposal guideline.
    - **Request Body**:
      ```json
      {
          "guideline": "string"
      }
      ```
- **DELETE `/api/disposal-guidelines/{id}`**: Delete a disposal guideline.

### Recycling Tips
- **GET `/api/recycling-tips`**: Retrieve all recycling tips.
- **GET `/api/recycling-tips/{id}`**: Retrieve a specific recycling tip by ID.
- **POST `/api/recycling-tips`**: Add a new recycling tip.
    - **Request Body**:
      ```json
      {
          "tip": "string"
      }
      ```
- **PUT `/api/recycling-tips/{id}`**: Update an existing recycling tip.
    - **Request Body**:
      ```json
      {
          "tip": "string"
      }
      ```
- **DELETE `/api/recycling-tips/{id}`**: Delete a recycling tip.

---

## Error Handling

The application uses a global exception handler to manage and format error responses. 
For example, if a resource is not found, the application will return a JSON response with a 404 status code and an error message.

### Example Error Response
```json
{
    "message": "Error 404: ____ Not Found"
}
```

---

## Running the Application

1. Clone the repository.
2. Navigate to the project directory.
3. Build the project using Maven:
   ```sh
   mvn clean install
4. Run the Application
   ```sh
   mvn spring-boot:run
5. The application will be accessible at [http://localhost:8080](http://localhost:8080).

---

## Running API Calls

To facilitate testing and executing API calls, sample curl commands are provided in Markdown format. 
These examples can be found in the `api.calls` folder located within the project's `resources` directory.

### Accessing API Call Examples

1. Navigate to the `api.calls` folder in the `resources` directory of the project.
2. Locate the Markdown files (`*.md`) containing the sample curl commands.
3. Copy and paste the curl commands into your terminal or preferred API testing tool to interact with the endpoints.

Using these examples, you can explore and interact with the Waste Sorting Application's API endpoints conveniently.

---

## Testing

Unit tests for the service layer are provided to ensure the correct functionality of the application logic. 
These tests use Mockito to mock dependencies and JUnit for assertions.

To run the tests, use the following Maven command:
```sh
mvn test
```

---

## Conclusion

The Waste Sorting Application provides a robust platform for managing waste through its REST API endpoints, encompassing waste categories, disposal guidelines, and recycling tips. 
By leveraging Spring Boot and various dependencies like Spring Data JPA, validation tools, and testing frameworks, the application ensures efficient data management and reliable functionality.
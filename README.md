#ONEAMZ Inventory Management System
Overview:

ONEAMZ is an inventory management system

The system allows users to manage products, including creating, updating, and deleting them.

Technologies Used:

Spring Boot
REST API
Spring Data JPA
PostgreSQL 15.5 RDBMS
Maven
Git
Lombok
Getting Started:

Clone the project from GitHub: https://github.com/nsurucu/oneamz.git
Install PostgreSQL 15.5 from: https://sbp.enterprisedb.com/getfile.jsp?fileid=1258790
Set up PostgreSQL with username postgres and password password.
Run mvn clean install to build the project.
Run mvn spring-boot:run to start the application.
The application will be available on port localhost:8080.
Testing:

You can use Postman to test the API:

**Create a token:** --->
**GET**
localhost:8080/api/generate-token


**Create a product:** --->
**POST**
localhost:8080/api/inventory/products

    `{
    "id": 1,
    "name": "T-Shirt",
    "price": 20.9000009,
    "description": "A comfortable cotton XXXXXXX",
    "quantity": 1099999,
    "category": {
    "name": "Clothing-x",
    "description": "A variety of apparel items******"
    }
    }`


**Update** a product: --->
**PUT**
localhost:8080/api/inventory/products/1

    `{
    "id": 1,
    "name": "T-Shirt",
    "price": 20.9000009,
    "description": "A comfortable cotton XXXXXXX",
    "quantity": 1099999,
    "category": {
    "name": "Clothing-x",
    "description": "A variety of apparel items******"
    }
    }`


**Delete a product:** --->
**DELETE**
localhost:8080/api/inventory/products/1# oneamz
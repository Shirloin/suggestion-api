## Documentation


## API Reference

#### Get Suggestions
The /suggestions endpoint provides auto-complete suggestions for large cities based on a search term. It returns a list of cities that match the given search query, optionally factoring in the caller's location to improve relevance. Each suggestion includes a confidence score, a city name, and geographical coordinates.


```http
GET /suggestions?q=Londo&latitude=43.70011&longitude=-79.4163
```


| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `q`      | `String` | **Required**|
| `latitude`      | `Double` | **Optional**|
| `longitude`      | `Double` | **Optional**|

#### Responses
This response will be a JSON array of suggestions, each sorted by confidence score in descending order. Each suggestion will contain the following fields:

```http
{
  "name": string,
  "latitude": double,
  "longitude": double,
  "score": double 
}
```


## Demo

[API Demo](https://codexchange.my.id/suggestions?q=Londo&latitude=43.70011&longitude=-79.4163)

### Freatures of the project
- Redis Caching: Utilizes Redis to cache frequently accessed data, enhancing performance by reducing the need for repeated calculating score, filtering, and sorting.
- Containeriztion: The project is containerized with Docker, making it easy to deploy and scale accross various environments.
- CI/CD Pipeline: Integrates a Continuous Integration/Continuous Deployment (CI/CD) pipeline to automate testing, building, and deployment. This ensures that all code changes are validated and delivered efficiently.
## How to run
Before running this project, please ensure you have the following dependencies installed:

#### Prequisites
- Java 17
- Redis
- Docker
- Docker Compose

### Steps to run the project manually
- Clone the repository
```
git clone https://github.com/shirloin/suggestion-api
```

- Navigate to the project directory
```
cd <project-directory>
```

- Build the project
``` 
mvn spring-boot:run
```

- Start Redis
On Linux. You need to install redis in linux before start it.
```
sudo systemctl start redis-server
```

- Access the application
Once the application is running, you can access it in you browser or thrugh API clients like Potman at http://localhost:8080

### Steps to run the project using docker
- Clone the repository
```
git clone https://github.com/shirloin/suggestion-api
```

- Navigate to the project directory
```
cd <project-directory>
```

- Build and run the docker container
```
docker compose up -d --build
```

### Unit Testing
This project includes a test suite for the SuggestionController endpoint using JUnit 5, Spring Boot Test, and MockMvc. The unit tests are designed to verify the behaviour of the /suggestions endpoint, which provides city suggestions based on partial search terms, with optional geographical parameters(latitude and longitude).

#### Dependencies
The necessary dependencies are already included in the pom.xml file:
```
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-test</artifactId>
  <version>3.1.2</version>
  <scope>test</scope>
</dependency>
```

#### Test Case: ```testGetSuggestion```

The testGetSuggestions() test case verifies that the /suggestions endpoint works as expected. It:

    1. Send a GET request to /suggestions with a query parameter q (search term) and optional latitude and longitude parameters.
    2. Compares the returned response with the expected list of city suggestions based on the query term.
    3. Asserts the response for each city suggestion by checking the name, latitude, longitude and score fields.

#### Test Assertions:
- Status: The http status code should be 200 Ok.
- Response Body: The response body should contain a list of city suggestions, each containing the following fields:
      
      1. name: The name of the city.
      2. latitude: The latitude of the city.
      3. longitude: The longitude of the city.
      4. score: The score indicating the confidence of the match (from 0 to 1)

#### Test Execution:
To run the tests, use the following Maven command:
```
mvn test
```

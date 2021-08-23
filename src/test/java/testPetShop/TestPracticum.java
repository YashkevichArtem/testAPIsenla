package testPetShop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParser;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.Category;
import model.Pet;
import model.TagPet;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collections;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestPracticum {

    String baseUri = "https://petstore.swagger.io/v2";
    String status = "/pet/findByStatus";
    String petId = "/pet/7395";
    String pet = "/pet";

    //Cпецификации, это некоторый набор настроек, чтобы выполнить запрос
    RequestSpecification rs = new RequestSpecBuilder()
    //Задаём данные, которые будем передавать.
            .setBaseUri(baseUri)
            .setContentType(ContentType.JSON)
    //Уровни логирования для детальных логов
            .log(LogDetail.METHOD)
            .log(LogDetail.BODY)
            .log(LogDetail.HEADERS)
            .log(LogDetail.BODY)
            .build();

    @Order(1)
    @Test
    public void petTestFindByStatus(){
        Response response = given()
                .spec(rs)
                .when()
                .get(status + "?status=available");

        response.prettyPrint();

       String responseBody =  response.getBody().asString();
        JsonParser parser = new JsonParser();
       Long id = parser.parse(responseBody).getAsJsonArray().get(0).getAsJsonObject().get("id").getAsLong();

        System.out.println(id);
    }

    @Order(2)
    @Test
    public void petTestPost(){
        TagPet tag = new TagPet(1234,"Barbos");
        Category dog = new Category(4321,"Dog");
//        String imageURL = "string";
        Pet pet = new Pet(7395, dog, "Barbar", new ArrayList<>(), new ArrayList<>(Collections.singletonList(tag)),"AVAILABLE");

        given().spec(rs)
                .when()
                .body(pet)
                .post("/pet");
    }

    @Order(3)
    @Test
    public void petTestFindById(){
        Response response = given()
                .spec(rs)
                .when()
                .get(petId);

        response.prettyPrint();
    }

    @Order(4)
    @Test
    public void petTestPutUpd(){
        TagPet tag = new TagPet(1234,"Opos");
        Category dog = new Category(4321,"Dog");
        Pet dogs = new Pet(7395, dog, "Perper", new ArrayList<>(), new ArrayList<>(Collections.singletonList(tag)),"SOLD");

        given().spec(rs)
                .when()
                .body(dogs)
                .put(pet);
    }

    @Order(5)
    @Test
    public void petTestDelete(){
        TagPet tag = new TagPet(1234,"Opos");
        Category dog = new Category(4321,"Dog");
        Pet dogs = new Pet(7395, dog, "Perper", new ArrayList<>(), new ArrayList<>(Collections.singletonList(tag)),"SOLD");

        given().spec(rs)
                .when()
                .delete(pet);

    }


}

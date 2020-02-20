/***************************************
 *
 * Author       : Askar Kuvanychbekov
 * Assignment   : II - mini app
 * Class        : CSI 5354
 *
 ***************************************/

package baylor;

import java.util.List;
import java.util.Map;

import edu.baylor.model.Person;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.wildfly.swarm.arquillian.DefaultDeployment;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.fest.assertions.Assertions.assertThat;

@RunWith(Arquillian.class)
@DefaultDeployment
@RunAsClient
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonResourceTest {

    @BeforeClass
    public static void setup() throws Exception {
        RestAssured.baseURI = "http://localhost:8080";
    }
    
    @Test
    public void bRetrievePerson() throws Exception {
        Response response =
                given()
                        .pathParam("personId", 1)
                        .when()
                        .get("/person/{personId}")
                        .then()
                        .extract().response();

//        String jsonAsString = response.asString();
//
//        Person person = JsonPath.from(jsonAsString).getObject("", Person.class);
//
//        assertThat(person.getId()).isEqualTo(1);
//        assertThat(person.getLedTeam().getId()).isEqualTo(1);
//        assertThat(person.getTeam()).isNull();
    }

//    @Test
//    public void cCreatePerson() throws Exception {
//        Person bmwPerson = new Person();
//        bmwPerson.setName("BMW");
//        bmwPerson.setVisible(Boolean.TRUE);
//        bmwPerson.setHeader("header");
//        bmwPerson.setImagePath("n/a");
//        bmwPerson.setParent(new TestPersonObject(1009));
//
//        Response response =
//                given()
//                        .contentType(ContentType.JSON)
//                        .body(bmwPerson)
//                        .when()
//                        .post("/admin/person");
//
//        assertThat(response).isNotNull();
//        assertThat(response.getStatusCode()).isEqualTo(201);
//        String locationUrl = response.getHeader("Location");
//        Integer personId = Integer.valueOf(locationUrl.substring(locationUrl.lastIndexOf('/') + 1));
//
//        response =
//                when()
//                        .get("/admin/person")
//                        .then()
//                        .extract().response();
//
//        String jsonAsString = response.asString();
//        List<Map<String, ?>> jsonAsList = JsonPath.from(jsonAsString).getList("");
//
//        assertThat(jsonAsList.size()).isEqualTo(22);
//
//        response =
//                given()
//                        .pathParam("personId", personId)
//                        .when()
//                        .get("/admin/person/{personId}")
//                        .then()
//                        .extract().response();
//
//        jsonAsString = response.asString();
//
//        Person person = JsonPath.from(jsonAsString).getObject("", Person.class);
//
//        assertThat(person.getId()).isEqualTo(personId);
//        assertThat(person.getParent().getId()).isEqualTo(1009);
//        assertThat(person.getName()).isEqualTo("BMW");
//        assertThat(person.isVisible()).isEqualTo(Boolean.TRUE);
//    }
//
//    @Test
//    public void dFailToCreatePersonFromNullName() throws Exception {
//        Person badPerson = new Person();
//        badPerson.setVisible(Boolean.TRUE);
//        badPerson.setHeader("header");
//        badPerson.setImagePath("n/a");
//        badPerson.setParent(new TestPersonObject(1009));
//
//        Response response =
//                given()
//                        .contentType(ContentType.JSON)
//                        .body(badPerson)
//                        .when()
//                        .post("/admin/person");
//
//        assertThat(response).isNotNull();
//        assertThat(response.getStatusCode()).isEqualTo(400);
//        assertThat(response.getBody().asString()).contains("Validation failed for classes [ejm.admin.model.Person] during persist time for groups [javax.validation.groups.Default, ]\n" +
//                "List of constraint violations:[\n" +
//                "\tConstraintViolationImpl{interpolatedMessage='must not be null', propertyPath=name, rootBeanClass=class ejm.admin.model.Person, messageTemplate='{javax.validation.constraints.NotNull.message}'}\n" +
//                "]");
//
//        response =
//                when()
//                        .get("/admin/person")
//                        .then()
//                        .extract().response();
//
//        String jsonAsString = response.asString();
//        List<Map<String, ?>> jsonAsList = JsonPath.from(jsonAsString).getList("");
//
//        assertThat(jsonAsList.size()).isEqualTo(22);
//    }
//
//    @Test
//    public void eUpdatePerson() throws Exception {
//
//        // first, check that there are 22 categories: (after adding one in Test "c" )
//        Response response =
//                when()
//                        .get("/admin/person")
//                        .then()
//                        .extract().response();
//        String jsonAsString = response.asString();
//        List<Map<String, ?>> jsonAsList = JsonPath.from(jsonAsString).getList("");
//        assertThat(response).isNotNull();
//        assertThat(jsonAsList.size()).isEqualTo(22);
//
//        // get the person Id of the lastly added entity:
//        Map<String, ?> lastRecord = jsonAsList.get(jsonAsList.size()-1);
//        Integer personId = (Integer) lastRecord.get("id"); //Integer personId = 1020;
//
//        // retrieve lastly added person
//        response =
//                given()
//                        .pathParam("personId", personId)
//                        .when()
//                        .get("/admin/person/{personId}")
//                        .then()
//                        .extract().response();
//
//        jsonAsString = response.asString();
//        Person person = JsonPath.from(jsonAsString).getObject("", Person.class);
//        // verify the values of retrieved person: (must be the same as in Test "c")
//        assertThat(person.getId()).isEqualTo(personId);
//        assertThat(person.getParent().getId()).isEqualTo(1009);
//        assertThat(person.getName()).isEqualTo("BMW");
//        assertThat(person.isVisible()).isEqualTo(Boolean.TRUE);
//
//        // make changes to the existing person
//        person.setName("Mercedes Trucks");
//        person.setParent(new TestPersonObject(1010)); //"Trucks"
//
//        // make HTTP PUT request and verify a response 200(OK):
//        response =
//                given()
//                        .contentType(ContentType.JSON)
//                        .body(person)
//                        .pathParam("personId", personId)
//                        .when()
//                        .put("/admin/person/{personId}");
//        assertThat(response).isNotNull();
//        assertThat(response.getStatusCode()).isEqualTo(200);
//
//        // check that there are still 22 categories in total:
//        response =
//                when()
//                        .get("/admin/person")
//                        .then()
//                        .extract().response();
//        jsonAsString = response.asString();
//        jsonAsList = JsonPath.from(jsonAsString).getList("");
//        assertThat(response).isNotNull();
//        assertThat(jsonAsList.size()).isEqualTo(22);
//
//        // retrieve person with personId and verify its new values
//        response =
//                given()
//                        .pathParam("personId", personId)
//                        .when()
//                        .get("/admin/person/{personId}")
//                        .then()
//                        .extract().response();
//        jsonAsString = response.asString();
//        Person updatedPerson = JsonPath.from(jsonAsString).getObject("", Person.class);
//        assertThat(updatedPerson.getId()).isEqualTo(personId);
//        assertThat(updatedPerson.getParent().getId()).isEqualTo(1010);
//        assertThat(updatedPerson.getName()).isEqualTo("Mercedes Trucks");
//        assertThat(updatedPerson.isVisible()).isEqualTo(Boolean.TRUE);
//    }
//
//    @Test
//    public void fDeletePerson() throws Exception {
//        // first, check that there are 22 categories: (after adding one in Test "c" )
//        Response response =
//                when()
//                        .get("/admin/person")
//                        .then()
//                        .extract().response();
//        String jsonAsString = response.asString();
//        List<Map<String, ?>> jsonAsList = JsonPath.from(jsonAsString).getList("");
//        assertThat(response).isNotNull();
//        assertThat(jsonAsList.size()).isEqualTo(22);
//
//        // get the person Id of the lastly added entity:
//        Map<String, ?> lastRecord = jsonAsList.get(jsonAsList.size()-1);
//        Integer personId = (Integer) lastRecord.get("id"); //Integer personId = 1020;
//
//        // make HTTP DELETE request and verify a response 204(NO_CONTENT)
//        response =
//                given()
//                        .pathParam("personId", personId)
//                        .when()
//                        .delete("/admin/person/{personId}");
//        assertThat(response).isNotNull();
//        assertThat(response.getStatusCode()).isEqualTo(204);
//
//        // check that there are 21 categories now:
//        response =
//                when()
//                        .get("/admin/person")
//                        .then()
//                        .extract().response();
//        jsonAsString = response.asString();
//        jsonAsList = JsonPath.from(jsonAsString).getList("");
//        assertThat(response).isNotNull();
//        assertThat(jsonAsList.size()).isEqualTo(21);
//
//        // try to retrieve person with personId and verify response 204(NO_CONTENT)
//        response =
//                given()
//                        .pathParam("personId", personId)
//                        .when()
//                        .get("/admin/person/{personId}")
//                        .then()
//                        .extract().response();
//        assertThat(response).isNotNull();
//        assertThat(response.getStatusCode()).isEqualTo(204);
//    }
}

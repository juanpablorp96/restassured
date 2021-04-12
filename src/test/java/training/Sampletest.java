package training;

import org.junit.Test;
import training.pojos.Postbody;


import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.lessThan;


public class Sampletest {

    @Test
    public void pathParamTest() {
        given().
                get("https://jsonplaceholder.typicode.com/todos/1").
        then().
                statusCode(200);
    }

    @Test
    public void idVerification() {
        given().
                get("https://jsonplaceholder.typicode.com/todos/1").
        then().
                body("id", equalTo(1));
    }

    @Test
    public void validateResponseTime() {
        given().
        when()
                .get("https://jsonplaceholder.typicode.com/todos/1").then().time(lessThan(1500L), MILLISECONDS);
    }

    @Test
    public void verifyByFilter() {
        when()
                .get("https://jsonplaceholder.typicode.com/albums").
        then()
                .body("findAll { it.userId == 1 }.title", hasItems("quidem molestiae enim", "sunt qui excepturi placeat culpa",
                        "omnis laborum odio", "non esse culpa molestiae omnis sed optio",
                        "eaque aut omnis a", "natus impedit quibusdam illo est",
                        "quibusdam autem aliquid et et quia", "qui fuga est a eum", "saepe unde necessitatibus rem",
                        "distinctio laborum qui"));
    }

    @Test
    public void testWithParameters() {
        given().
                pathParam("resource", "albums").
                pathParam("id", 1).
        when().
                get("https://jsonplaceholder.typicode.com/{resource}/{id}").
        then().
                assertThat().
                body("title", equalTo("quidem molestiae enim"));
    }

    @Test
    public void postWithBody() {
        Postbody post = new Postbody();
        post.setTitle("a");
        post.setBody("b");
        post.setUserId(1);
        given().
                contentType("application/json").
                body(post).
        when().
                post("https://jsonplaceholder.typicode.com/posts").
        then().
                statusCode(201);
    }

}

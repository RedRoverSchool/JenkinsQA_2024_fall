package school.redrover.controllers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import school.redrover.models.user.User;

import static io.restassured.RestAssured.given;
import static school.redrover.runner.TestApiUtils.requestSpec;

public class UserController {
    private static final String SCRIPT_ENDPOINT = "/scriptText";

    private String createUserScript(String username, String password, String email) {
        return String.format(
                """
                        import hudson.security.*
                        import jenkins.model.*
                        import groovy.json.JsonOutput
                        def instance = Jenkins.getInstance()
                        def realm = instance.getSecurityRealm()
                        def username = '%s'
                        def password = '%s'
                        def email = '%s'
                        def user = realm.createAccount(username, password)
                        def userProperty = new hudson.tasks.Mailer.UserProperty(email)
                        user.addProperty(userProperty)
                        instance.save()
                        def result = [message: 'User created successfully', user: [id: username, email: email]]
                        println JsonOutput.prettyPrint(JsonOutput.toJson(result))""",
                username, password, email
        );

    }

    private String getAllUsersScript() {
        return """
                import hudson.model.User
                import hudson.tasks.Mailer
                import groovy.json.JsonOutput
                def users = User.getAll()
                def userList = users.collect { user ->
                    def email = user.getProperty(Mailer.UserProperty)?.getAddress() ?: "No email"
                    [id: user.id, name: user.fullName, email: email]
                }
                def result = [users: userList]
                println JsonOutput.prettyPrint(JsonOutput.toJson(result))
                """;
    }

    public Response createUser(User user) {
        return given()
                .spec(requestSpec())
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("script", createUserScript(user.getUsername(), user.getPassword(), user.getEmail()))
                .when()
                .post(SCRIPT_ENDPOINT)
                .andReturn();
    }

    public Response getAllUsers() {
        return given()
                .spec(requestSpec())
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("script", getAllUsersScript())
                .when()
                .post(SCRIPT_ENDPOINT)
                .andReturn();
    }

    public void deleteUser(String userID) {
        given()
                .spec(requestSpec())
                .when()
                .post("securityRealm/user/%s/doDelete".formatted(userID));
    }
}

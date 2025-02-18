package school.redrover.api.restassured;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import school.redrover.models.PluginManagerResponse;
import school.redrover.runner.BaseApiTest;
import school.redrover.runner.WireMockStubs;

import static io.restassured.RestAssured.given;
import static school.redrover.runner.TestApiUtils.requestSpec;
import static school.redrover.runner.TestApiUtils.responseSpec;

@Epic("API")
@Feature("Manage Jenkins")
public class PluginsAPITest extends BaseApiTest {

    private static final String MINIMUM_REQUIRED_CORE_VERSION = "2.330.1";

    @BeforeClass
    private void stubs() {
        stubEndpoints(WireMockStubs::stubVerifyPluginsInstalled);
    }

    private boolean isCoreVersionGreaterThanMinimum(String version) {
        String[] requiredVersionParts = MINIMUM_REQUIRED_CORE_VERSION.split("\\.");
        String[] pluginVersionParts = version.split("\\.");

        for (int i = 0; i < Math.min(requiredVersionParts.length, pluginVersionParts.length); i++) {
            int requiredPart = Integer.parseInt(requiredVersionParts[i]);
            int pluginPart = Integer.parseInt(pluginVersionParts[i]);

            if (pluginPart > requiredPart) {
                return true;
            } else if (pluginPart < requiredPart) {
                return false;
            }
        }

        return pluginVersionParts.length >= requiredVersionParts.length;
    }

    @Test
    @Description("09.006.04 Verify installed plugins and version")
    public void testVerifyPluginsInstalled() {
        Response response = given()
                .spec(requestSpec())
                .when()
                .post("/pluginManager/api/json?depth=1")
                .then()
                .spec(responseSpec(200, 1000L))
                .extract().response();

        PluginManagerResponse pluginManagerResponse = response.as(PluginManagerResponse.class);

        boolean allPluginsHaveMinimumRequiredCoreVersion = pluginManagerResponse.getPlugins().stream()
                .allMatch(plugin -> {
                    String requiredCoreVersion = plugin.getRequiredCoreVersion();
                    return requiredCoreVersion != null && isCoreVersionGreaterThanMinimum(requiredCoreVersion);
                });

        Assert.assertTrue(pluginManagerResponse.getPlugins().stream()
                        .allMatch(plugin -> plugin.getLongName() != null && !plugin.getLongName().isEmpty()),
                "Plugin long name should not be null or empty.");
        Assert.assertTrue(allPluginsHaveMinimumRequiredCoreVersion, "Not all plugins have the minimum required core version.");
        Assert.assertEquals(pluginManagerResponse.getClassField(), "hudson.LocalPluginManager");
        Assert.assertTrue(pluginManagerResponse.getPlugins().stream()
                .allMatch(PluginManagerResponse.Plugin::isActive));
        Assert.assertFalse(pluginManagerResponse.getPlugins().stream()
                .allMatch(PluginManagerResponse.Plugin::isDowngradable));
        Assert.assertTrue(pluginManagerResponse.getPlugins().stream()
                .allMatch(plugin -> plugin.getUrl().startsWith("https://plugins.jenkins.io/")));
    }
}
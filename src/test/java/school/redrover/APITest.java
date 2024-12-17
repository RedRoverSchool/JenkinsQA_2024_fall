package school.redrover;


import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;

@Ignore
public class APITest {

    private static final class Pokemon {
        private final String name;
        private final String url;

        Pokemon(String name, String url) {
            this.name = name;
            this.url = url;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Pokemon pokemon = (Pokemon) o;
            return Objects.equals(name, pokemon.name) && Objects.equals(url, pokemon.url);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, url);
        }
    }

    private static final class Pokemons {
        private int count;
        private String previous;
        private String next;
        private List<Pokemon> results;
    }

    @Test
    public void httpTest() throws IOException, URISyntaxException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI("https://pokeapi.co/api/v2/pokemon"))
                .headers(HttpHeaders.USER_AGENT, "Googlebot")
                .GET()
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(httpResponse.statusCode(), 200);

        String body = httpResponse.body();
        Assert.assertNotNull(body);

        // simple check
        Assert.assertTrue(body.startsWith("{\"count\":1302"));

        // regular check
        Pokemons pokemons = new Gson().fromJson(body, Pokemons.class);
        Assert.assertEquals(pokemons.count, 1302);
        Assert.assertNull(pokemons.previous);
        Assert.assertEquals(pokemons.next, "https://pokeapi.co/api/v2/pokemon?offset=20&limit=20");
        Assert.assertEquals(pokemons.results.size(), 20);
        Assert.assertEquals(
                pokemons.results.get(0),
                new Pokemon("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"));
    }

    @Test
    public void restAssuredTest() {
        RestAssured.when().get("https://pokeapi.co/api/v2/pokemon")
                .then()
                .statusCode(200)
                .body("count", Matchers.equalTo(1302),
                        "results.name", Matchers.hasItems("bulbasaur", "ivysaur"));
    }
}

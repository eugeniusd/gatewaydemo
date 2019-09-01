package com.adidas.gatewaydemo;

import com.adidas.gatewaydemo.route.filter.XRequestFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.xml.sax.XMLFilter;

import java.time.Duration;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "httpbin")
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class GatewayDemoApplicationTests {

    @LocalServerPort
    protected int port = 0;

    private WebTestClient webClient;

    @Before
    public void setup() {
        String baseUri = "http://localhost:" + port;
        this.webClient = WebTestClient.bindToServer()
                .responseTimeout(Duration.ofSeconds(1000)).baseUrl(baseUri).build();
    }

    @Test
    public void testHttpbinOk() {
        webClient.get().uri("/httpbin/anything").exchange().expectStatus().isOk();
    }

	@Test
	public void testJph4xxError() {
		webClient.get().uri("/jph/todos").exchange().expectStatus().is4xxClientError();
	}


    @Test
    public void testHeaderAdded() {
        String response = new String(webClient.get().uri("/httpbin/headers").exchange().expectStatus().isOk()
                .expectBody().returnResult().getResponseBody());
        assert(response.contains("\"X-Test-Header\": \"TEST_HEADER\""));
    }

    @Test
    public void testHeaderNotOverriden() {
        String response = new String(webClient.get().uri("/httpbin/headers").header(XRequestFilter.TEST_HEADER, "TEST_HEADER2").exchange().expectStatus().isOk()
                .expectBody().returnResult().getResponseBody());
        assert(response.contains("\"X-Test-Header\": \"TEST_HEADER2\""));
    }

}

package de.leanovate.routeegenerator;

import static de.leanovate.routeegenerator.RouteRulesMatchers.hasPackage;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import de.leanovate.routeegenerator.builder.JavaFileBuilder;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class RouteParserTest {
    @Test
    public void testParseSuccess() throws Exception {

        String correctRoutes = readResource("correct.routes");

        RouteRules routeRules = RouteParser.parse(correctRoutes);

        assertThat(routeRules, hasPackage(equalTo("de.leonovate.testing.testy")));

        try (JavaFileBuilder javaFileBuilder =
                     new JavaFileBuilder(new File("./target/testout"), "test", "TestParseSuccess")) {
            routeRules.build(javaFileBuilder);
        }

    }

    private String readResource(String resource) throws IOException {

        try (Reader reader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(resource),
                "UTF-8")) {
            char[] buffer = new char[8192];
            int readed = 0;
            StringBuilder result = new StringBuilder();

            while ((readed = reader.read(buffer)) > 0) {
                result.append(buffer, 0, readed);
            }

            return result.toString();
        }
    }
}

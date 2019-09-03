package io.github.sskorol.reflection.testcases;

import io.vavr.Tuple;
import one.util.streamex.StreamEx;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.function.UnaryOperator;

import static java.util.Arrays.stream;
import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.joor.Reflect.on;

public class ReflectionTests {

    @Test
    public void shouldAccessAnnotationValues() {
        var values = StreamEx.iterate(LoginPage.class, (UnaryOperator<Class<?>>) Class::getSuperclass)
                .takeWhile(page -> !page.equals(Object.class))
                .flatMap(page -> stream(page.getDeclaredFields()))
                .filter(field -> field.isAnnotationPresent(Find.class))
                .map(field -> field.getAnnotation(Find.class))
                .map(annotation -> Tuple.of(annotation, annotation.annotationType()))
                .flatMap(container -> stream(container._2.getDeclaredMethods())
                        .sorted(comparing(Method::getName))
                        .map(method -> on(container._1).call(method.getName()).get()))
                .toList();

        assertThat(values).hasSize(3);
    }
}

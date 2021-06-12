package com.github.schuettec.jbeanviews.features.interCollectionMapping.nestedMapListsMixed;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.github.schuettec.jbeanviews.api.BeanView;
import com.github.schuettec.jbeanviews.api.BeanViews;

public class MapsTest {

  @Test
  public void shouldMapNestedKeyValues() {
    A1 a1 = new A1("key1");
    A2 key1 = new A2("value-key1");
    A3 value1 = new A3("value-value1");

    A1 a2 = new A1("key2");
    A2 key2 = new A2("value-key2");
    A3 value2 = new A3("value-value2");

    BeanView<A, AMapped> beanView = BeanViews.of(A.class)
        .toView(AMapped.class)
        .get();

    A a = new A();
    a.add(asList(a1), asMap(key1, value1));
    a.add(asList(a2), asMap(key2, value2));

    AMapped mapped = beanView.toView(a);

    assertThat(mapped.getMap()).isInstanceOf(Map.class);
    assertThat(mapped.getMap()
        .entrySet()
        .iterator()
        .next()
        .getKey()).isInstanceOf(List.class);
    assertThat(mapped.getMap()
        .entrySet()
        .iterator()
        .next()
        .getKey()
        .iterator()
        .next()).isInstanceOf(A1Mapped.class);
    assertThat(mapped.getMap()
        .entrySet()
        .iterator()
        .next()
        .getValue()).isInstanceOf(Map.class);
    assertThat(mapped.getMap()
        .entrySet()
        .iterator()
        .next()
        .getValue()
        .entrySet()
        .iterator()
        .next()
        .getKey()).isInstanceOf(A2Mapped.class);
    assertThat(mapped.getMap()
        .entrySet()
        .iterator()
        .next()
        .getValue()
        .entrySet()
        .iterator()
        .next()
        .getValue()).isInstanceOf(A3Mapped.class);

  }

  private Map<A2, A3> asMap(A2 key, A3 value) {
    Map<A2, A3> map = new Hashtable<A2, A3>();
    map.put(key, value);
    return map;
  }

}

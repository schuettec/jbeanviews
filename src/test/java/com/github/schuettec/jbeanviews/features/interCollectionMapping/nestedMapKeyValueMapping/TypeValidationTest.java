package com.github.schuettec.jbeanviews.features.interCollectionMapping.nestedMapKeyValueMapping;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import com.github.schuettec.jbeanviews.api.BeanView;
import com.github.schuettec.jbeanviews.api.BeanViews;

public class TypeValidationTest {

  @Test
  public void shouldMapKeyAndValueOfMap() {
    BeanView<A, AMapped> beanView = BeanViews.of(A.class)
        .toView(AMapped.class)
        .get();
    A source = new A(asList(asSet(asMap(new B("Bstring1"), new C("Cstring1")))));
    AMapped view = beanView.toView(source);
    assertEquals(1, view.getList()
        .size());
    Set<Map<BMapped, CMapped>> set = view.getList()
        .get(0);
    assertEquals(1, set.size());
    Map<BMapped, CMapped> map = set.iterator()
        .next();
    assertEquals(1, map.size());
    Entry<BMapped, CMapped> entry = map.entrySet()
        .iterator()
        .next();
    BMapped key = entry.getKey();
    CMapped value = entry.getValue();
    assertTrue(key instanceof BMapped);
    assertTrue(value instanceof CMapped);
    assertEquals(key.getString(), "Bstring1");
    assertEquals(value.getString(), "Cstring1");
  }

  private <E> Set<E> asSet(E... e) {
    return new HashSet<>(Arrays.asList(e));
  }

  private <K, V> Map<K, V> asMap(K k, V v) {
    Map<K, V> map = new Hashtable<>();
    map.put(k, v);
    return map;
  }
}

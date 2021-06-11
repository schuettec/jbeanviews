package com.remondis.jbeanviews.features.conversion.pluralAttribute.unidirectional.autoConversion;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.remondis.jbeanviews.api.BeanView;
import com.remondis.jbeanviews.api.BeanViews;
import com.remondis.jbeanviews.features.conversion.pluralAttribute.unidirectional.types.PluralSource;
import com.remondis.jbeanviews.features.conversion.pluralAttribute.unidirectional.types.PluralView;
import com.remondis.jbeanviews.test.data.leafTypes.Human;
import com.remondis.jbeanviews.test.data.leafTypes.Person;

public class PluralAutoConversionTest {

  @Test
  public void shouldAutoConvertList() {
    BeanView<PluralSource, PluralView> beanView = BeanViews.of(PluralSource.class)
        .toView(PluralView.class)
        .bindCollection(PluralView::getPersons)
        .to(PluralSource::getHumans)
        .get();

    PluralSource source = new PluralSource(asList(new Human("forename1", "name1"), new Human("forename2", "name2")));

    PluralView view = beanView.toView(source);

    assertEquals(2, view.getPersons()
        .size());

    List<Person> personList = asList(new Person("forename1", "name1"), new Person("forename2", "name2"));
    assertThat(view.getPersons()).containsExactlyInAnyOrderElementsOf(personList);
  }

}

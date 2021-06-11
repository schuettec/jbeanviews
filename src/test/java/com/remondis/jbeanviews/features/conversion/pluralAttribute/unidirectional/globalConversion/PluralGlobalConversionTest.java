package com.remondis.jbeanviews.features.conversion.pluralAttribute.unidirectional.globalConversion;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.remondis.jbeanviews.api.BeanView;
import com.remondis.jbeanviews.api.BeanViews;
import com.remondis.jbeanviews.features.conversion.pluralAttribute.unidirectional.types.PersonConversion;
import com.remondis.jbeanviews.features.conversion.pluralAttribute.unidirectional.types.PluralSource;
import com.remondis.jbeanviews.features.conversion.pluralAttribute.unidirectional.types.PluralView;
import com.remondis.jbeanviews.test.data.leafTypes.Human;
import com.remondis.jbeanviews.test.data.leafTypes.Person;

@RunWith(MockitoJUnitRunner.class)
public class PluralGlobalConversionTest {

  @Spy
  private PersonConversion conversionFunction;

  @Test
  public void shouldConvertListUsingGlobalConversion() {
    doCallRealMethod().when(conversionFunction)
        .apply(any());

    BeanView<PluralSource, PluralView> beanView = BeanViews.of(PluralSource.class)
        .toView(PluralView.class)
        .typeConversion(builder -> builder.fromSource(Human.class)
            .toView(Person.class)
            .applying(conversionFunction)
            .unidirectional())
        .bindCollection(PluralView::getPersons)
        .to(PluralSource::getHumans)
        .get();

    PluralSource source = new PluralSource(asList(new Human("forename1", "name1"), new Human("forename2", "name2")));

    PluralView view = beanView.toView(source);

    // Must be called 2 times (for every list item)
    verify(conversionFunction, times(2)).apply(any());

    assertEquals(2, view.getPersons()
        .size());

    List<Person> personList = asList(new Person("forename1", "name1"), new Person("forename2", "name2"));
    assertThat(view.getPersons()).containsExactlyInAnyOrderElementsOf(personList);
  }

}

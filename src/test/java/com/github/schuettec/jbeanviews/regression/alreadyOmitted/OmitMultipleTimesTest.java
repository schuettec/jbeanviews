package com.github.schuettec.jbeanviews.regression.alreadyOmitted;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

import com.github.schuettec.jbeanviews.api.BeanViews;
import com.github.schuettec.jbeanviews.impl.BeanViewException;
import com.github.schuettec.jbeanviews.test.data.leafTypes.Human;
import com.github.schuettec.jbeanviews.test.data.leafTypes.Person;

public class OmitMultipleTimesTest {

  @Test
  public void omitMultipleTimes() {
    assertThatThrownBy(() -> BeanViews.of(Human.class)
        .toView(Person.class)
        .omit(Person::getName)
        .omit(Person::getName)
        .get()).isInstanceOf(BeanViewException.class)
            .hasMessageContaining(
                "The view property is already omitted: Property 'name' with type java.lang.String accessed by Person.getName()");
  }

}

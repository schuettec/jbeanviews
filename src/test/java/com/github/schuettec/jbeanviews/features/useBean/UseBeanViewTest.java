package com.github.schuettec.jbeanviews.features.useBean;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.github.schuettec.jbeanviews.api.BeanView;
import com.github.schuettec.jbeanviews.api.BeanViews;
import com.github.schuettec.jbeanviews.test.data.leafTypes.Person;
import com.github.schuettec.jbeanviews.test.views.leafTypes.PersonView;

public class UseBeanViewTest {

  @Test
  public void shouldUseBeanView() {
    BeanView<Person, PersonView> personView = BeanViews.of(Person.class)
        .toView(PersonView.class)
        .omit(PersonView::getForenameView)
        .omit(PersonView::getNameView)
        .omit(PersonView::getZahl)
        .get();
    BeanView<Source, View> beanView = BeanViews.of(Source.class)
        .toView(View.class)
        .useBeanView(personView)
        .get();

    Source source = new Source(asList(new Person("forename", "name")));
    View view = beanView.toView(source);
    assertNull(view.getPersons()
        .get(0)
        .getForenameView());
    assertNull(view.getPersons()
        .get(0)
        .getNameView());
    assertEquals(0, view.getPersons()
        .get(0)
        .getZahl());
  }

}

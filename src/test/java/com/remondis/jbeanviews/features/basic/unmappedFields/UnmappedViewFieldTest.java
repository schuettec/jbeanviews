package com.remondis.jbeanviews.features.basic.unmappedFields;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

import com.remondis.jbeanviews.api.BeanViews;
import com.remondis.jbeanviews.test.data.leafTypes.Address;

public class UnmappedViewFieldTest {

  @Test
  public void shouldComplainAboutUnmappedViewField() {
    assertThatThrownBy(() -> BeanViews.of(Address.class)
        .toView(MyAddress.class)
        .get()).hasMessageContaining("Cannot find a matching source property for")
            .hasMessageContaining(
                "Property 'additionalField' with type java.lang.String accessed by MyAddress.getAdditionalField()")
            .hasMessageContaining("Did not find any candidates as source property");
  }

}

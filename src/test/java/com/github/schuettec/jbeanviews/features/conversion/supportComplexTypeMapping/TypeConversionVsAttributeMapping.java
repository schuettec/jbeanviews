package com.github.schuettec.jbeanviews.features.conversion.supportComplexTypeMapping;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.schuettec.jbeanviews.api.BeanView;
import com.github.schuettec.jbeanviews.api.BeanViews;

@RunWith(MockitoJUnitRunner.class)
public class TypeConversionVsAttributeMapping {

  @Spy
  private ComplexTypeConversion conversionFunction;

  @Test
  public void shouldUseTypeConversionOverAttributeMapping() {
    doCallRealMethod().when(conversionFunction)
        .apply(any());

    BeanView<Source, View> beanView = BeanViews.of(Source.class)
        .toView(View.class)
        .typeConversion(builder -> builder.fromSource(ComplexType.class)
            .toView(ComplexTypeView.class)
            .applying(conversionFunction)
            .unidirectional())
        .get();

    System.out.println(beanView);

    Source source = new Source(new ComplexType("content"));
    View view = beanView.toView(source);

    assertEquals(source.getReference()
        .getContent()
        .length(),
        view.getReference()
            .getLength());

    verify(conversionFunction, times(1)).apply(any());
  }

}

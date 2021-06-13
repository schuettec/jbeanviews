package com.github.schuettec.jbeanviews.features.conversion.supportComplexTypeMapping;

import java.util.function.Function;

public class ComplexTypeConversion implements Function<ComplexType, ComplexTypeView> {

  @Override
  public ComplexTypeView apply(ComplexType src) {
    return new ComplexTypeView(src.getContent()
        .length());
  }

}

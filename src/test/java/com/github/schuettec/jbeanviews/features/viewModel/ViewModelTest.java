package com.github.schuettec.jbeanviews.features.viewModel;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.github.schuettec.jbeanviews.api.BeanView;
import com.github.schuettec.jbeanviews.api.BeanViews;
import com.github.schuettec.jbeanviews.api.ViewBinding;
import com.github.schuettec.jbeanviews.api.ViewModel;
import com.github.schuettec.jbeanviews.test.data.Customer;
import com.github.schuettec.jbeanviews.test.data.leafTypes.Type;
import com.github.schuettec.jbeanviews.test.views.CustomerView;

public class ViewModelTest {

  @Test
  public void viewModelShouldBeCorrect() {
    BeanView<Customer, CustomerView> beanView = getBeanView();
    ViewModel<Customer, CustomerView> viewModel = beanView.getViewModel();
    ViewBinding forenameViewBinding = viewModel.getViewBindingFor(CustomerView::getForename);
    assertNotNull(forenameViewBinding);
    assertFalse(forenameViewBinding.isCollectionAttribute());
    assertFalse(forenameViewBinding.isThisBinding());
    assertFalse(forenameViewBinding.hasFieldConversion());
    assertEquals("person.forename", forenameViewBinding.getSourcePath());
  }

  @Test
  public void viewModelShouldComplainAboutUnknownBinding_viewBinding() {
    BeanView<Customer, CustomerView> beanView = getBeanView();
    ViewModel<Customer, CustomerView> viewModel = beanView.getViewModel();
    assertThatThrownBy(() -> viewModel.getViewBindingFor("tritratrullala"))
        .hasMessageContaining("No binding found for view.");
  }

  @Test
  public void viewModelShouldComplainAboutUnknownBinding_sourceBinding() {
    BeanView<Customer, CustomerView> beanView = getBeanView();
    ViewModel<Customer, CustomerView> viewModel = beanView.getViewModel();
    assertThatThrownBy(() -> viewModel.getSourceBindingFor("tritratrullala"))
        .hasMessageContaining("No binding found for source.");
  }

  private BeanView<Customer, CustomerView> getBeanView() {
    return BeanViews.of(Customer.class)
        .toView(CustomerView.class)
        .typeConversion(conversion -> conversion.fromSource(Type.class)
            .toView(String.class)
            .applying(Type::getKey)
            .andReverse(Type::byKey))
        .get();
  }
}

package com.github.schuettec.jbeanviews.features.viewModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

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
    Optional<ViewBinding> forenameViewBindingOpt = viewModel.getViewBindingFor(CustomerView::getForename);
    assertNotNull(forenameViewBindingOpt);
    assertTrue(forenameViewBindingOpt.isPresent());
    ViewBinding forenameViewBinding = forenameViewBindingOpt.get();
    assertFalse(forenameViewBinding.isCollectionAttribute());
    assertFalse(forenameViewBinding.isThisBinding());
    assertFalse(forenameViewBinding.hasFieldConversion());
    assertEquals("person.forename", forenameViewBinding.getSourcePath());
    assertTrue(viewModel.isViewSubPath("metaData"));
    assertFalse(viewModel.isViewSubPath("forename"));
    assertFalse(viewModel.isViewSubPath("metaData.deleted"));
    assertFalse(viewModel.isViewPropertyPath("metaData"));
    assertTrue(viewModel.isViewPropertyPath("forename"));
    assertTrue(viewModel.isViewPropertyPath("metaData.deleted"));
  }

  @Test
  public void viewModelShouldComplainAboutUnknownBinding_viewBinding() {
    BeanView<Customer, CustomerView> beanView = getBeanView();
    ViewModel<Customer, CustomerView> viewModel = beanView.getViewModel();
    Optional<ViewBinding> viewBinding = viewModel.getViewBindingFor("tritratrullala");
    assertTrue(viewBinding.isEmpty());
  }

  @Test
  public void viewModelShouldComplainAboutUnknownBinding_sourceBinding() {
    BeanView<Customer, CustomerView> beanView = getBeanView();
    ViewModel<Customer, CustomerView> viewModel = beanView.getViewModel();
    List<ViewBinding> sourceBindingFor = viewModel.getSourceBindingFor("tritratrullala");
    assertTrue(sourceBindingFor.isEmpty());
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

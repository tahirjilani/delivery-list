package com.tj.deliverylist.di.moduels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.tj.deliverylist.vm.DeliveriesViewModel;
import com.tj.deliverylist.vm.ViewModelFactory;
import com.tj.deliverylist.vm.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory viewModelFactory);
    //You are able to declare ViewModelProvider.Factory dependency in another module. For example in ApplicationModule.

    @Binds
    @IntoMap
    @ViewModelKey(DeliveriesViewModel.class)
    abstract ViewModel userViewModel(DeliveriesViewModel userViewModel);

}

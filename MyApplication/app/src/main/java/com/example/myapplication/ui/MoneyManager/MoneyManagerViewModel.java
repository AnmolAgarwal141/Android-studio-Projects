package com.example.myapplication.ui.MoneyManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MoneyManagerViewModel extends ViewModel {
        private MutableLiveData<String> mText;

        public MoneyManagerViewModel() {
            mText = new MutableLiveData<>();
            mText.setValue("This is Money Manager fragment");
        }

        public LiveData<String> getText() {
            return mText;
        }
    }


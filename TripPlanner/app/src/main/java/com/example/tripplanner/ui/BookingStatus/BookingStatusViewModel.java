package com.example.tripplanner.ui.BookingStatus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BookingStatusViewModel extends  ViewModel {
        private MutableLiveData<String> mText;

        public BookingStatusViewModel() {
            mText = new MutableLiveData<>();
            mText.setValue("This is Bookingstatus fragment");
        }

        public LiveData<String> getText() {
            return mText;
        }
    }

